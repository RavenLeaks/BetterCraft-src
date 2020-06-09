/*    */ package javazoom.jl.decoder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Obuffer
/*    */ {
/*    */   public static final int OBUFFERSIZE = 2304;
/*    */   public static final int MAXCHANNELS = 2;
/*    */   
/*    */   public abstract void append(int paramInt, short paramShort);
/*    */   
/*    */   public void appendSamples(int channel, float[] f) {
/* 56 */     for (int i = 0; i < 32; ) {
/*    */       
/* 58 */       short s = clip(f[i++]);
/* 59 */       append(channel, s);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private final short clip(float sample) {
/* 68 */     return (sample > 32767.0F) ? Short.MAX_VALUE : (
/* 69 */       (sample < -32768.0F) ? Short.MIN_VALUE : 
/* 70 */       (short)(int)sample);
/*    */   }
/*    */   
/*    */   public abstract void write_buffer(int paramInt);
/*    */   
/*    */   public abstract void close();
/*    */   
/*    */   public abstract void clear_buffer();
/*    */   
/*    */   public abstract void set_stop_flag();
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\Obuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */