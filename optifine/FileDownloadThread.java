/*    */ package optifine;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ 
/*    */ public class FileDownloadThread
/*    */   extends Thread {
/*  7 */   private String urlString = null;
/*  8 */   private IFileDownloadListener listener = null;
/*    */ 
/*    */   
/*    */   public FileDownloadThread(String p_i41_1_, IFileDownloadListener p_i41_2_) {
/* 12 */     this.urlString = p_i41_1_;
/* 13 */     this.listener = p_i41_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 20 */       byte[] abyte = HttpPipeline.get(this.urlString, Minecraft.getMinecraft().getProxy());
/* 21 */       this.listener.fileDownloadFinished(this.urlString, abyte, null);
/*    */     }
/* 23 */     catch (Exception exception) {
/*    */       
/* 25 */       this.listener.fileDownloadFinished(this.urlString, null, exception);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUrlString() {
/* 31 */     return this.urlString;
/*    */   }
/*    */ 
/*    */   
/*    */   public IFileDownloadListener getListener() {
/* 36 */     return this.listener;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\FileDownloadThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */