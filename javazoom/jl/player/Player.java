/*     */ package javazoom.jl.player;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import javazoom.jl.decoder.Bitstream;
/*     */ import javazoom.jl.decoder.BitstreamException;
/*     */ import javazoom.jl.decoder.Decoder;
/*     */ import javazoom.jl.decoder.Header;
/*     */ import javazoom.jl.decoder.JavaLayerException;
/*     */ import javazoom.jl.decoder.SampleBuffer;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Player
/*     */ {
/*  47 */   private int frame = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Bitstream bitstream;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Decoder decoder;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private AudioDevice audio;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean closed = false;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean complete = false;
/*     */ 
/*     */ 
/*     */   
/*  75 */   private int lastPosition = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player(InputStream stream) throws JavaLayerException {
/*  82 */     this(stream, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Player(InputStream stream, AudioDevice device) throws JavaLayerException {
/*  87 */     this.bitstream = new Bitstream(stream);
/*  88 */     this.decoder = new Decoder();
/*     */     
/*  90 */     if (device != null) {
/*     */       
/*  92 */       this.audio = device;
/*     */     }
/*     */     else {
/*     */       
/*  96 */       FactoryRegistry r = FactoryRegistry.systemRegistry();
/*  97 */       this.audio = r.createAudioDevice();
/*     */     } 
/*  99 */     this.audio.open(this.decoder);
/*     */   }
/*     */ 
/*     */   
/*     */   public void play() throws JavaLayerException {
/* 104 */     play(2147483647);
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
/*     */   public boolean play(int frames) throws JavaLayerException {
/* 116 */     boolean ret = true;
/*     */     
/* 118 */     while (frames-- > 0 && ret)
/*     */     {
/* 120 */       ret = decodeFrame();
/*     */     }
/*     */     
/* 123 */     if (!ret) {
/*     */ 
/*     */       
/* 126 */       AudioDevice out = this.audio;
/* 127 */       if (out != null) {
/*     */         
/* 129 */         out.flush();
/* 130 */         synchronized (this) {
/*     */           
/* 132 */           this.complete = !this.closed;
/* 133 */           close();
/*     */         } 
/*     */       } 
/*     */     } 
/* 137 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void close() {
/* 146 */     AudioDevice out = this.audio;
/* 147 */     if (out != null) {
/*     */       
/* 149 */       this.closed = true;
/* 150 */       this.audio = null;
/*     */ 
/*     */       
/* 153 */       out.close();
/* 154 */       this.lastPosition = out.getPosition();
/*     */       
/*     */       try {
/* 157 */         this.bitstream.close();
/*     */       }
/* 159 */       catch (BitstreamException bitstreamException) {}
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
/*     */   public synchronized boolean isComplete() {
/* 173 */     return this.complete;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPosition() {
/* 184 */     int position = this.lastPosition;
/*     */     
/* 186 */     AudioDevice out = this.audio;
/* 187 */     if (out != null)
/*     */     {
/* 189 */       position = out.getPosition();
/*     */     }
/* 191 */     return position;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean decodeFrame() throws JavaLayerException {
/*     */     try {
/* 203 */       AudioDevice out = this.audio;
/* 204 */       if (out == null) {
/* 205 */         return false;
/*     */       }
/* 207 */       Header h = this.bitstream.readFrame();
/*     */       
/* 209 */       if (h == null) {
/* 210 */         return false;
/*     */       }
/*     */       
/* 213 */       SampleBuffer output = (SampleBuffer)this.decoder.decodeFrame(h, this.bitstream);
/*     */       
/* 215 */       synchronized (this) {
/*     */         
/* 217 */         out = this.audio;
/* 218 */         if (out != null)
/*     */         {
/* 220 */           out.write(output.getBuffer(), 0, output.getBufferLength());
/*     */         }
/*     */       } 
/*     */       
/* 224 */       this.bitstream.closeFrame();
/*     */     }
/* 226 */     catch (RuntimeException ex) {
/*     */       
/* 228 */       throw new JavaLayerException("Exception decoding audio frame", ex);
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
/*     */     
/* 247 */     return true;
/*     */   }
/*     */   
/*     */   public boolean setGain(float newGain) {
/* 251 */     if (this.audio instanceof JavaSoundAudioDevice) {
/* 252 */       JavaSoundAudioDevice jsAudio = (JavaSoundAudioDevice)this.audio;
/*     */       try {
/* 254 */         jsAudio.write(null, 0, 0);
/* 255 */       } catch (JavaLayerException ex) {
/* 256 */         ex.printStackTrace();
/*     */       } 
/* 258 */       return jsAudio.setLineGain(newGain);
/*     */     } 
/* 260 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\player\Player.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */