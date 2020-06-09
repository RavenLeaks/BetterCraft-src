/*    */ package optifine;
/*    */ 
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class HttpResponse
/*    */ {
/*  8 */   private int status = 0;
/*  9 */   private String statusLine = null;
/* 10 */   private Map<String, String> headers = new LinkedHashMap<>();
/* 11 */   private byte[] body = null;
/*    */ 
/*    */   
/*    */   public HttpResponse(int p_i61_1_, String p_i61_2_, Map<String, String> p_i61_3_, byte[] p_i61_4_) {
/* 15 */     this.status = p_i61_1_;
/* 16 */     this.statusLine = p_i61_2_;
/* 17 */     this.headers = p_i61_3_;
/* 18 */     this.body = p_i61_4_;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getStatus() {
/* 23 */     return this.status;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getStatusLine() {
/* 28 */     return this.statusLine;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map getHeaders() {
/* 33 */     return this.headers;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHeader(String p_getHeader_1_) {
/* 38 */     return this.headers.get(p_getHeader_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getBody() {
/* 43 */     return this.body;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\HttpResponse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */