/*     */ package javazoom.jl.decoder;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Decoder
/*     */   implements DecoderErrors
/*     */ {
/*  33 */   private static final Params DEFAULT_PARAMS = new Params();
/*     */ 
/*     */ 
/*     */   
/*     */   private Obuffer output;
/*     */ 
/*     */ 
/*     */   
/*     */   private SynthesisFilter filter1;
/*     */ 
/*     */ 
/*     */   
/*     */   private SynthesisFilter filter2;
/*     */ 
/*     */ 
/*     */   
/*     */   private LayerIIIDecoder l3decoder;
/*     */ 
/*     */ 
/*     */   
/*     */   private LayerIIDecoder l2decoder;
/*     */ 
/*     */ 
/*     */   
/*     */   private LayerIDecoder l1decoder;
/*     */ 
/*     */   
/*     */   private int outputFrequency;
/*     */ 
/*     */   
/*     */   private int outputChannels;
/*     */ 
/*     */   
/*  66 */   private Equalizer equalizer = new Equalizer();
/*     */ 
/*     */ 
/*     */   
/*     */   private Params params;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean initialized;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decoder() {
/*  80 */     this(null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Decoder(Params params0) {
/*  92 */     if (params0 == null) {
/*  93 */       params0 = DEFAULT_PARAMS;
/*     */     }
/*  95 */     this.params = params0;
/*     */     
/*  97 */     Equalizer eq = this.params.getInitialEqualizerSettings();
/*  98 */     if (eq != null)
/*     */     {
/* 100 */       this.equalizer.setFrom(eq);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static Params getDefaultParams() {
/* 106 */     return (Params)DEFAULT_PARAMS.clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEqualizer(Equalizer eq) {
/* 111 */     if (eq == null) {
/* 112 */       eq = Equalizer.PASS_THRU_EQ;
/*     */     }
/* 114 */     this.equalizer.setFrom(eq);
/*     */     
/* 116 */     float[] factors = this.equalizer.getBandFactors();
/*     */     
/* 118 */     if (this.filter1 != null) {
/* 119 */       this.filter1.setEQ(factors);
/*     */     }
/* 121 */     if (this.filter2 != null) {
/* 122 */       this.filter2.setEQ(factors);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Obuffer decodeFrame(Header header, Bitstream stream) throws DecoderException {
/* 136 */     if (!this.initialized)
/*     */     {
/* 138 */       initialize(header);
/*     */     }
/*     */     
/* 141 */     int layer = header.layer();
/*     */     
/* 143 */     this.output.clear_buffer();
/*     */     
/* 145 */     FrameDecoder decoder = retrieveDecoder(header, stream, layer);
/*     */     
/* 147 */     decoder.decodeFrame();
/*     */     
/* 149 */     this.output.write_buffer(1);
/*     */     
/* 151 */     return this.output;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOutputBuffer(Obuffer out) {
/* 160 */     this.output = out;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOutputFrequency() {
/* 173 */     return this.outputFrequency;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOutputChannels() {
/* 187 */     return this.outputChannels;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOutputBlockSize() {
/* 203 */     return 2304;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected DecoderException newDecoderException(int errorcode) {
/* 209 */     return new DecoderException(errorcode, null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected DecoderException newDecoderException(int errorcode, Throwable throwable) {
/* 214 */     return new DecoderException(errorcode, throwable);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected FrameDecoder retrieveDecoder(Header header, Bitstream stream, int layer) throws DecoderException {
/* 220 */     FrameDecoder decoder = null;
/*     */ 
/*     */ 
/*     */     
/* 224 */     switch (layer) {
/*     */       
/*     */       case 3:
/* 227 */         if (this.l3decoder == null)
/*     */         {
/* 229 */           this.l3decoder = new LayerIIIDecoder(stream, 
/* 230 */               header, this.filter1, this.filter2, 
/* 231 */               this.output, 0);
/*     */         }
/*     */         
/* 234 */         decoder = this.l3decoder;
/*     */         break;
/*     */       case 2:
/* 237 */         if (this.l2decoder == null) {
/*     */           
/* 239 */           this.l2decoder = new LayerIIDecoder();
/* 240 */           this.l2decoder.create(stream, 
/* 241 */               header, this.filter1, this.filter2, 
/* 242 */               this.output, 0);
/*     */         } 
/* 244 */         decoder = this.l2decoder;
/*     */         break;
/*     */       case 1:
/* 247 */         if (this.l1decoder == null) {
/*     */           
/* 249 */           this.l1decoder = new LayerIDecoder();
/* 250 */           this.l1decoder.create(stream, 
/* 251 */               header, this.filter1, this.filter2, 
/* 252 */               this.output, 0);
/*     */         } 
/* 254 */         decoder = this.l1decoder;
/*     */         break;
/*     */     } 
/*     */     
/* 258 */     if (decoder == null)
/*     */     {
/* 260 */       throw newDecoderException(513, null);
/*     */     }
/*     */     
/* 263 */     return decoder;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initialize(Header header) throws DecoderException {
/* 271 */     float scalefactor = 32700.0F;
/*     */     
/* 273 */     int mode = header.mode();
/* 274 */     int layer = header.layer();
/* 275 */     int channels = (mode == 3) ? 1 : 2;
/*     */ 
/*     */ 
/*     */     
/* 279 */     if (this.output == null) {
/* 280 */       this.output = new SampleBuffer(header.frequency(), channels);
/*     */     }
/* 282 */     float[] factors = this.equalizer.getBandFactors();
/* 283 */     this.filter1 = new SynthesisFilter(0, scalefactor, factors);
/*     */ 
/*     */     
/* 286 */     if (channels == 2) {
/* 287 */       this.filter2 = new SynthesisFilter(1, scalefactor, factors);
/*     */     }
/* 289 */     this.outputChannels = channels;
/* 290 */     this.outputFrequency = header.frequency();
/*     */     
/* 292 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static class Params
/*     */     implements Cloneable
/*     */   {
/* 303 */     private OutputChannels outputChannels = OutputChannels.BOTH;
/*     */     
/* 305 */     private Equalizer equalizer = new Equalizer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Object clone() {
/*     */       try {
/* 315 */         return super.clone();
/*     */       }
/* 317 */       catch (CloneNotSupportedException ex) {
/*     */         
/* 319 */         throw new InternalError(this + ": " + ex);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void setOutputChannels(OutputChannels out) {
/* 325 */       if (out == null) {
/* 326 */         throw new NullPointerException("out");
/*     */       }
/* 328 */       this.outputChannels = out;
/*     */     }
/*     */ 
/*     */     
/*     */     public OutputChannels getOutputChannels() {
/* 333 */       return this.outputChannels;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public Equalizer getInitialEqualizerSettings() {
/* 352 */       return this.equalizer;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\Decoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */