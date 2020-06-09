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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SampleBuffer
/*     */   extends Obuffer
/*     */ {
/*     */   private short[] buffer;
/*     */   private int[] bufferp;
/*     */   private int channels;
/*     */   private int frequency;
/*     */   
/*     */   public SampleBuffer(int sample_frequency, int number_of_channels) {
/*  44 */     this.buffer = new short[2304];
/*  45 */     this.bufferp = new int[2];
/*  46 */     this.channels = number_of_channels;
/*  47 */     this.frequency = sample_frequency;
/*     */     
/*  49 */     for (int i = 0; i < number_of_channels; i++) {
/*  50 */       this.bufferp[i] = (short)i;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getChannelCount() {
/*  56 */     return this.channels;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSampleFrequency() {
/*  61 */     return this.frequency;
/*     */   }
/*     */ 
/*     */   
/*     */   public short[] getBuffer() {
/*  66 */     return this.buffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBufferLength() {
/*  71 */     return this.bufferp[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(int channel, short value) {
/*  79 */     this.buffer[this.bufferp[channel]] = value;
/*  80 */     this.bufferp[channel] = this.bufferp[channel] + this.channels;
/*     */   }
/*     */ 
/*     */   
/*     */   public void appendSamples(int channel, float[] f) {
/*  85 */     int pos = this.bufferp[channel];
/*     */ 
/*     */ 
/*     */     
/*  89 */     for (int i = 0; i < 32; ) {
/*     */       
/*  91 */       float fs = f[i++];
/*  92 */       fs = (fs > 32767.0F) ? 32767.0F : (
/*  93 */         (fs < -32767.0F) ? -32767.0F : fs);
/*     */       
/*  95 */       short s = (short)(int)fs;
/*  96 */       this.buffer[pos] = s;
/*  97 */       pos += this.channels;
/*     */     } 
/*     */     
/* 100 */     this.bufferp[channel] = pos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void write_buffer(int val) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clear_buffer() {
/* 123 */     for (int i = 0; i < this.channels; i++)
/* 124 */       this.bufferp[i] = (short)i; 
/*     */   }
/*     */   
/*     */   public void set_stop_flag() {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\SampleBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */