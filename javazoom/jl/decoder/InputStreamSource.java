/*    */ package javazoom.jl.decoder;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
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
/*    */ public class InputStreamSource
/*    */   implements Source
/*    */ {
/*    */   private final InputStream in;
/*    */   
/*    */   public InputStreamSource(InputStream in) {
/* 41 */     if (in == null) {
/* 42 */       throw new NullPointerException("in");
/*    */     }
/* 44 */     this.in = in;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int read(byte[] b, int offs, int len) throws IOException {
/* 50 */     int read = this.in.read(b, offs, len);
/* 51 */     return read;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean willReadBlock() {
/* 56 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSeekable() {
/* 63 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public long tell() {
/* 68 */     return -1L;
/*    */   }
/*    */ 
/*    */   
/*    */   public long seek(long to) {
/* 73 */     return -1L;
/*    */   }
/*    */ 
/*    */   
/*    */   public long length() {
/* 78 */     return -1L;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javazoom\jl\decoder\InputStreamSource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */