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
/*     */ 
/*     */ class LayerIDecoder
/*     */   implements FrameDecoder
/*     */ {
/*     */   protected Bitstream stream;
/*     */   protected Header header;
/*     */   protected SynthesisFilter filter1;
/*     */   protected SynthesisFilter filter2;
/*     */   protected Obuffer buffer;
/*     */   protected int which_channels;
/*     */   protected int mode;
/*     */   protected int num_subbands;
/*     */   protected Subband[] subbands;
/*  43 */   protected Crc16 crc = null;
/*     */ 
/*     */   
/*     */   public LayerIDecoder() {
/*  47 */     this.crc = new Crc16();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void create(Bitstream stream0, Header header0, SynthesisFilter filtera, SynthesisFilter filterb, Obuffer buffer0, int which_ch0) {
/*  54 */     this.stream = stream0;
/*  55 */     this.header = header0;
/*  56 */     this.filter1 = filtera;
/*  57 */     this.filter2 = filterb;
/*  58 */     this.buffer = buffer0;
/*  59 */     this.which_channels = which_ch0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decodeFrame() throws DecoderException {
/*  66 */     this.num_subbands = this.header.number_of_subbands();
/*  67 */     this.subbands = new Subband[32];
/*  68 */     this.mode = this.header.mode();
/*     */     
/*  70 */     createSubbands();
/*     */     
/*  72 */     readAllocation();
/*  73 */     readScaleFactorSelection();
/*     */     
/*  75 */     if (this.crc != null || this.header.checksum_ok()) {
/*     */       
/*  77 */       readScaleFactors();
/*     */       
/*  79 */       readSampleData();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createSubbands() {
/*  87 */     if (this.mode == 3) {
/*  88 */       for (int i = 0; i < this.num_subbands; i++)
/*  89 */         this.subbands[i] = new SubbandLayer1(i); 
/*  90 */     } else if (this.mode == 1) {
/*     */       int i;
/*  92 */       for (i = 0; i < this.header.intensity_stereo_bound(); i++)
/*  93 */         this.subbands[i] = new SubbandLayer1Stereo(i); 
/*  94 */       for (; i < this.num_subbands; i++) {
/*  95 */         this.subbands[i] = new SubbandLayer1IntensityStereo(i);
/*     */       }
/*     */     } else {
/*     */       
/*  99 */       for (int i = 0; i < this.num_subbands; i++) {
/* 100 */         this.subbands[i] = new SubbandLayer1Stereo(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readAllocation() throws DecoderException {
/* 107 */     for (int i = 0; i < this.num_subbands; i++) {
/* 108 */       this.subbands[i].read_allocation(this.stream, this.header, this.crc);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readScaleFactorSelection() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readScaleFactors() {
/* 119 */     for (int i = 0; i < this.num_subbands; i++) {
/* 120 */       this.subbands[i].read_scalefactor(this.stream, this.header);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void readSampleData() {
/* 125 */     boolean read_ready = false;
/* 126 */     boolean write_ready = false;
/* 127 */     int mode = this.header.mode();
/*     */     
/*     */     while (true) {
/*     */       int i;
/* 131 */       for (i = 0; i < this.num_subbands; i++) {
/* 132 */         read_ready = this.subbands[i].read_sampledata(this.stream);
/*     */       }
/*     */       do {
/* 135 */         for (i = 0; i < this.num_subbands; i++) {
/* 136 */           write_ready = this.subbands[i].put_next_sample(this.which_channels, this.filter1, this.filter2);
/*     */         }
/* 138 */         this.filter1.calculate_pcm_samples(this.buffer);
/* 139 */         if (this.which_channels != 0 || mode == 3)
/* 140 */           continue;  this.filter2.calculate_pcm_samples(this.buffer);
/* 141 */       } while (!write_ready || 
/* 142 */         !read_ready);
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
/*     */   
/*     */   static abstract class Subband
/*     */   {
/* 158 */     public static final float[] scalefactors = new float[] { 
/* 159 */         2.0F, 1.587401F, 1.2599211F, 1.0F, 
/* 160 */         0.7937005F, 0.62996054F, 0.5F, 0.39685026F, 
/* 161 */         0.31498027F, 0.25F, 0.19842513F, 0.15749013F, 
/* 162 */         0.125F, 0.099212565F, 0.07874507F, 0.0625F, 
/* 163 */         0.049606282F, 0.039372534F, 0.03125F, 0.024803141F, 
/* 164 */         0.019686267F, 0.015625F, 0.012401571F, 0.009843133F, 
/* 165 */         0.0078125F, 0.0062007853F, 0.0049215667F, 0.00390625F, 
/* 166 */         0.0031003926F, 0.0024607833F, 0.001953125F, 0.0015501963F, 
/* 167 */         0.0012303917F, 9.765625E-4F, 7.7509816E-4F, 6.1519584E-4F, 
/* 168 */         4.8828125E-4F, 3.8754908E-4F, 3.0759792E-4F, 2.4414062E-4F, 
/* 169 */         1.9377454E-4F, 1.5379896E-4F, 1.2207031E-4F, 9.688727E-5F, 
/* 170 */         7.689948E-5F, 6.1035156E-5F, 4.8443635E-5F, 3.844974E-5F, 
/* 171 */         3.0517578E-5F, 2.4221818E-5F, 1.922487E-5F, 1.5258789E-5F, 
/* 172 */         1.2110909E-5F, 9.612435E-6F, 7.6293945E-6F, 6.0554544E-6F, 
/* 173 */         4.8062175E-6F, 3.8146973E-6F, 3.0277272E-6F, 2.4031087E-6F, 
/* 174 */         1.9073486E-6F, 1.5138636E-6F, 1.2015544E-6F, 0.0F };
/*     */ 
/*     */     
/*     */     public abstract void read_allocation(Bitstream param1Bitstream, Header param1Header, Crc16 param1Crc16) throws DecoderException;
/*     */ 
/*     */     
/*     */     public abstract void read_scalefactor(Bitstream param1Bitstream, Header param1Header);
/*     */ 
/*     */     
/*     */     public abstract boolean read_sampledata(Bitstream param1Bitstream);
/*     */ 
/*     */     
/*     */     public abstract boolean put_next_sample(int param1Int, SynthesisFilter param1SynthesisFilter1, SynthesisFilter param1SynthesisFilter2);
/*     */   }
/*     */   
/*     */   static class SubbandLayer1
/*     */     extends Subband
/*     */   {
/* 192 */     public static final float[] table_factor = new float[] { 
/* 193 */         0.0F, 0.6666667F, 0.2857143F, 0.13333334F, 
/* 194 */         0.06451613F, 0.031746034F, 0.015748031F, 
/* 195 */         0.007843138F, 0.0039138943F, 
/* 196 */         0.0019550342F, 9.770396E-4F, 
/* 197 */         4.884005E-4F, 2.4417043E-4F, 
/* 198 */         1.2207776E-4F, 6.103702E-5F };
/*     */ 
/*     */     
/* 201 */     public static final float[] table_offset = new float[] { 
/* 202 */         0.0F, -0.6666667F, -0.8571429F, -0.9333334F, 
/* 203 */         -0.9677419F, -0.98412704F, -0.992126F, 
/* 204 */         -0.9960785F, -0.99804306F, 
/* 205 */         -0.9990225F, -0.9995115F, 
/* 206 */         -0.99975586F, -0.9998779F, 
/* 207 */         -0.99993896F, -0.9999695F };
/*     */     
/*     */     protected int subbandnumber;
/*     */     
/*     */     protected int samplenumber;
/*     */     
/*     */     protected int allocation;
/*     */     
/*     */     protected float scalefactor;
/*     */     
/*     */     protected int samplelength;
/*     */     protected float sample;
/*     */     protected float factor;
/*     */     protected float offset;
/*     */     
/*     */     public SubbandLayer1(int subbandnumber) {
/* 223 */       this.subbandnumber = subbandnumber;
/* 224 */       this.samplenumber = 0;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void read_allocation(Bitstream stream, Header header, Crc16 crc) throws DecoderException {
/* 232 */       if ((this.allocation = stream.get_bits(4)) == 15)
/*     */       {
/*     */         
/* 235 */         throw new DecoderException(514, null);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 240 */       if (crc != null) crc.add_bits(this.allocation, 4); 
/* 241 */       if (this.allocation != 0) {
/*     */         
/* 243 */         this.samplelength = this.allocation + 1;
/* 244 */         this.factor = table_factor[this.allocation];
/* 245 */         this.offset = table_offset[this.allocation];
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void read_scalefactor(Bitstream stream, Header header) {
/* 254 */       if (this.allocation != 0) this.scalefactor = scalefactors[stream.get_bits(6)];
/*     */     
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean read_sampledata(Bitstream stream) {
/* 262 */       if (this.allocation != 0)
/*     */       {
/* 264 */         this.sample = stream.get_bits(this.samplelength);
/*     */       }
/* 266 */       if (++this.samplenumber == 12) {
/*     */         
/* 268 */         this.samplenumber = 0;
/* 269 */         return true;
/*     */       } 
/* 271 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean put_next_sample(int channels, SynthesisFilter filter1, SynthesisFilter filter2) {
/* 279 */       if (this.allocation != 0 && channels != 2) {
/*     */         
/* 281 */         float scaled_sample = (this.sample * this.factor + this.offset) * this.scalefactor;
/* 282 */         filter1.input_sample(scaled_sample, this.subbandnumber);
/*     */       } 
/* 284 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static class SubbandLayer1IntensityStereo
/*     */     extends SubbandLayer1
/*     */   {
/*     */     protected float channel2_scalefactor;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public SubbandLayer1IntensityStereo(int subbandnumber) {
/* 300 */       super(subbandnumber);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void read_allocation(Bitstream stream, Header header, Crc16 crc) throws DecoderException {
/* 308 */       super.read_allocation(stream, header, crc);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void read_scalefactor(Bitstream stream, Header header) {
/* 316 */       if (this.allocation != 0) {
/*     */         
/* 318 */         this.scalefactor = scalefactors[stream.get_bits(6)];
/* 319 */         this.channel2_scalefactor = scalefactors[stream.get_bits(6)];
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean read_sampledata(Bitstream stream) {
/* 328 */       return super.read_sampledata(stream);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean put_next_sample(int channels, SynthesisFilter filter1, SynthesisFilter filter2) {
/* 336 */       if (this.allocation != 0) {
/*     */         
/* 338 */         this.sample = this.sample * this.factor + this.offset;
/* 339 */         if (channels == 0) {
/*     */           
/* 341 */           float sample1 = this.sample * this.scalefactor;
/* 342 */           float sample2 = this.sample * this.channel2_scalefactor;
/* 343 */           filter1.input_sample(sample1, this.subbandnumber);
/* 344 */           filter2.input_sample(sample2, this.subbandnumber);
/*     */         }
/* 346 */         else if (channels == 1) {
/*     */           
/* 348 */           float sample1 = this.sample * this.scalefactor;
/* 349 */           filter1.input_sample(sample1, this.subbandnumber);
/*     */         }
/*     */         else {
/*     */           
/* 353 */           float sample2 = this.sample * this.channel2_scalefactor;
/* 354 */           filter1.input_sample(sample2, this.subbandnumber);
/*     */         } 
/*     */       } 
/* 357 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class SubbandLayer1Stereo
/*     */     extends SubbandLayer1
/*     */   {
/*     */     protected int channel2_allocation;
/*     */     
/*     */     protected float channel2_scalefactor;
/*     */     
/*     */     protected int channel2_samplelength;
/*     */     
/*     */     protected float channel2_sample;
/*     */     
/*     */     protected float channel2_factor;
/*     */     
/*     */     protected float channel2_offset;
/*     */     
/*     */     public SubbandLayer1Stereo(int subbandnumber) {
/* 378 */       super(subbandnumber);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void read_allocation(Bitstream stream, Header header, Crc16 crc) throws DecoderException {
/* 386 */       this.allocation = stream.get_bits(4);
/* 387 */       this.channel2_allocation = stream.get_bits(4);
/* 388 */       if (crc != null) {
/*     */         
/* 390 */         crc.add_bits(this.allocation, 4);
/* 391 */         crc.add_bits(this.channel2_allocation, 4);
/*     */       } 
/* 393 */       if (this.allocation != 0) {
/*     */         
/* 395 */         this.samplelength = this.allocation + 1;
/* 396 */         this.factor = table_factor[this.allocation];
/* 397 */         this.offset = table_offset[this.allocation];
/*     */       } 
/* 399 */       if (this.channel2_allocation != 0) {
/*     */         
/* 401 */         this.channel2_samplelength = this.channel2_allocation + 1;
/* 402 */         this.channel2_factor = table_factor[this.channel2_allocation];
/* 403 */         this.channel2_offset = table_offset[this.channel2_allocation];
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void read_scalefactor(Bitstream stream, Header header) {
/* 412 */       if (this.allocation != 0) this.scalefactor = scalefactors[stream.get_bits(6)]; 
/* 413 */       if (this.channel2_allocation != 0) this.channel2_scalefactor = scalefactors[stream.get_bits(6)];
/*     */     
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean read_sampledata(Bitstream stream) {
/* 421 */       boolean returnvalue = super.read_sampledata(stream);
/* 422 */       if (this.channel2_allocation != 0)
/*     */       {
/* 424 */         this.channel2_sample = stream.get_bits(this.channel2_samplelength);
/*     */       }
/* 426 */       return returnvalue;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean put_next_sample(int channels, SynthesisFilter filter1, SynthesisFilter filter2) {
/* 434 */       super.put_next_sample(channels, filter1, filter2);
/* 435 */       if (this.channel2_allocation != 0 && channels != 1) {
/*     */         
/* 437 */         float sample2 = (this.channel2_sample * this.channel2_factor + this.channel2_offset) * 
/* 438 */           this.channel2_scalefactor;
/* 439 */         if (channels == 0) {
/* 440 */           filter2.input_sample(sample2, this.subbandnumber);
/*     */         } else {
/* 442 */           filter1.input_sample(sample2, this.subbandnumber);
/*     */         } 
/* 444 */       }  return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\LayerIDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */