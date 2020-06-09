/*    */ package optifine;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class FileUploadThread
/*    */   extends Thread
/*    */ {
/*    */   private String urlString;
/*    */   private Map headers;
/*    */   private byte[] content;
/*    */   private IFileUploadListener listener;
/*    */   
/*    */   public FileUploadThread(String p_i42_1_, Map p_i42_2_, byte[] p_i42_3_, IFileUploadListener p_i42_4_) {
/* 14 */     this.urlString = p_i42_1_;
/* 15 */     this.headers = p_i42_2_;
/* 16 */     this.content = p_i42_3_;
/* 17 */     this.listener = p_i42_4_;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     try {
/* 24 */       HttpUtils.post(this.urlString, this.headers, this.content);
/* 25 */       this.listener.fileUploadFinished(this.urlString, this.content, null);
/*    */     }
/* 27 */     catch (Exception exception) {
/*    */       
/* 29 */       this.listener.fileUploadFinished(this.urlString, this.content, exception);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getUrlString() {
/* 35 */     return this.urlString;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getContent() {
/* 40 */     return this.content;
/*    */   }
/*    */ 
/*    */   
/*    */   public IFileUploadListener getListener() {
/* 45 */     return this.listener;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\FileUploadThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */