/*    */ package optifine;
/*    */ 
/*    */ import java.net.Proxy;
/*    */ import java.util.LinkedHashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class HttpRequest
/*    */ {
/*  9 */   private String host = null;
/* 10 */   private int port = 0;
/* 11 */   private Proxy proxy = Proxy.NO_PROXY;
/* 12 */   private String method = null;
/* 13 */   private String file = null;
/* 14 */   private String http = null;
/* 15 */   private Map<String, String> headers = new LinkedHashMap<>();
/* 16 */   private byte[] body = null;
/* 17 */   private int redirects = 0;
/*    */   
/*    */   public static final String METHOD_GET = "GET";
/*    */   public static final String METHOD_HEAD = "HEAD";
/*    */   public static final String METHOD_POST = "POST";
/*    */   public static final String HTTP_1_0 = "HTTP/1.0";
/*    */   public static final String HTTP_1_1 = "HTTP/1.1";
/*    */   
/*    */   public HttpRequest(String p_i60_1_, int p_i60_2_, Proxy p_i60_3_, String p_i60_4_, String p_i60_5_, String p_i60_6_, Map<String, String> p_i60_7_, byte[] p_i60_8_) {
/* 26 */     this.host = p_i60_1_;
/* 27 */     this.port = p_i60_2_;
/* 28 */     this.proxy = p_i60_3_;
/* 29 */     this.method = p_i60_4_;
/* 30 */     this.file = p_i60_5_;
/* 31 */     this.http = p_i60_6_;
/* 32 */     this.headers = p_i60_7_;
/* 33 */     this.body = p_i60_8_;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHost() {
/* 38 */     return this.host;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPort() {
/* 43 */     return this.port;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getMethod() {
/* 48 */     return this.method;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFile() {
/* 53 */     return this.file;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHttp() {
/* 58 */     return this.http;
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<String, String> getHeaders() {
/* 63 */     return this.headers;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getBody() {
/* 68 */     return this.body;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getRedirects() {
/* 73 */     return this.redirects;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRedirects(int p_setRedirects_1_) {
/* 78 */     this.redirects = p_setRedirects_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public Proxy getProxy() {
/* 83 */     return this.proxy;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\HttpRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */