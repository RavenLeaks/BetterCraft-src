/*     */ package javazoom.jl.converter;
/*     */ 
/*     */ import javazoom.jl.decoder.Obuffer;
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
/*     */ public class WaveFileObuffer
/*     */   extends Obuffer
/*     */ {
/*     */   private short[] buffer;
/*     */   private short[] bufferp;
/*     */   private int channels;
/*     */   private WaveFile outWave;
/*     */   
/*     */   public WaveFileObuffer(int number_of_channels, int freq, String FileName) {
/*  58 */     if (FileName == null) {
/*  59 */       throw new NullPointerException("FileName");
/*     */     }
/*  61 */     this.buffer = new short[2304];
/*  62 */     this.bufferp = new short[2];
/*  63 */     this.channels = number_of_channels;
/*     */     
/*  65 */     for (int i = 0; i < number_of_channels; i++) {
/*  66 */       this.bufferp[i] = (short)i;
/*     */     }
/*  68 */     this.outWave = new WaveFile();
/*     */     
/*  70 */     int rc = this.outWave.OpenForWrite(FileName, freq, (short)16, (short)this.channels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void append(int channel, short value) {
/*  78 */     this.buffer[this.bufferp[channel]] = value;
/*  79 */     this.bufferp[channel] = (short)(this.bufferp[channel] + this.channels);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   short[] myBuffer = new short[2];
/*     */ 
/*     */   
/*     */   public void write_buffer(int val) {
/*  89 */     int k = 0;
/*  90 */     int rc = 0;
/*     */     
/*  92 */     rc = this.outWave.WriteData(this.buffer, this.bufferp[0]);
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
/* 105 */     for (int i = 0; i < this.channels; ) { this.bufferp[i] = (short)i; i++; }
/*     */   
/*     */   }
/*     */   
/*     */   public void close() {
/* 110 */     this.outWave.Close();
/*     */   }
/*     */   
/*     */   public void clear_buffer() {}
/*     */   
/*     */   public void set_stop_flag() {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\converter\WaveFileObuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */