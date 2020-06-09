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
/*    */ public class DecoderException
/*    */   extends JavaLayerException
/*    */   implements DecoderErrors
/*    */ {
/* 32 */   private int errorcode = 512;
/*    */ 
/*    */   
/*    */   public DecoderException(String msg, Throwable t) {
/* 36 */     super(msg, t);
/*    */   }
/*    */ 
/*    */   
/*    */   public DecoderException(int errorcode, Throwable t) {
/* 41 */     this(getErrorString(errorcode), t);
/* 42 */     this.errorcode = errorcode;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getErrorCode() {
/* 47 */     return this.errorcode;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getErrorString(int errorcode) {
/* 56 */     return "Decoder errorcode " + Integer.toHexString(errorcode);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\DecoderException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */