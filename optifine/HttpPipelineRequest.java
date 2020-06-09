/*    */ package optifine;
/*    */ 
/*    */ public class HttpPipelineRequest
/*    */ {
/*  5 */   private HttpRequest httpRequest = null;
/*  6 */   private HttpListener httpListener = null;
/*    */   
/*    */   private boolean closed = false;
/*    */   
/*    */   public HttpPipelineRequest(HttpRequest p_i58_1_, HttpListener p_i58_2_) {
/* 11 */     this.httpRequest = p_i58_1_;
/* 12 */     this.httpListener = p_i58_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpRequest getHttpRequest() {
/* 17 */     return this.httpRequest;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpListener getHttpListener() {
/* 22 */     return this.httpListener;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isClosed() {
/* 27 */     return this.closed;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setClosed(boolean p_setClosed_1_) {
/* 32 */     this.closed = p_setClosed_1_;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\HttpPipelineRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */