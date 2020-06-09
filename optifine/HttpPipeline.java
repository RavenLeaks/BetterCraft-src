/*     */ package optifine;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InterruptedIOException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class HttpPipeline
/*     */ {
/*  14 */   private static Map mapConnections = new HashMap<>();
/*     */   
/*     */   public static final String HEADER_USER_AGENT = "User-Agent";
/*     */   public static final String HEADER_HOST = "Host";
/*     */   public static final String HEADER_ACCEPT = "Accept";
/*     */   public static final String HEADER_LOCATION = "Location";
/*     */   public static final String HEADER_KEEP_ALIVE = "Keep-Alive";
/*     */   public static final String HEADER_CONNECTION = "Connection";
/*     */   public static final String HEADER_VALUE_KEEP_ALIVE = "keep-alive";
/*     */   public static final String HEADER_TRANSFER_ENCODING = "Transfer-Encoding";
/*     */   public static final String HEADER_VALUE_CHUNKED = "chunked";
/*     */   
/*     */   public static void addRequest(String p_addRequest_0_, HttpListener p_addRequest_1_) throws IOException {
/*  27 */     addRequest(p_addRequest_0_, p_addRequest_1_, Proxy.NO_PROXY);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void addRequest(String p_addRequest_0_, HttpListener p_addRequest_1_, Proxy p_addRequest_2_) throws IOException {
/*  32 */     HttpRequest httprequest = makeRequest(p_addRequest_0_, p_addRequest_2_);
/*  33 */     HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(httprequest, p_addRequest_1_);
/*  34 */     addRequest(httppipelinerequest);
/*     */   }
/*     */ 
/*     */   
/*     */   public static HttpRequest makeRequest(String p_makeRequest_0_, Proxy p_makeRequest_1_) throws IOException {
/*  39 */     URL url = new URL(p_makeRequest_0_);
/*     */     
/*  41 */     if (!url.getProtocol().equals("http"))
/*     */     {
/*  43 */       throw new IOException("Only protocol http is supported: " + url);
/*     */     }
/*     */ 
/*     */     
/*  47 */     String s = url.getFile();
/*  48 */     String s1 = url.getHost();
/*  49 */     int i = url.getPort();
/*     */     
/*  51 */     if (i <= 0)
/*     */     {
/*  53 */       i = 80;
/*     */     }
/*     */     
/*  56 */     String s2 = "GET";
/*  57 */     String s3 = "HTTP/1.1";
/*  58 */     Map<String, String> map = new LinkedHashMap<>();
/*  59 */     map.put("User-Agent", "Java/" + System.getProperty("java.version"));
/*  60 */     map.put("Host", s1);
/*  61 */     map.put("Accept", "text/html, image/gif, image/png");
/*  62 */     map.put("Connection", "keep-alive");
/*  63 */     byte[] abyte = new byte[0];
/*  64 */     HttpRequest httprequest = new HttpRequest(s1, i, p_makeRequest_1_, s2, s, s3, map, abyte);
/*  65 */     return httprequest;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void addRequest(HttpPipelineRequest p_addRequest_0_) {
/*  71 */     HttpRequest httprequest = p_addRequest_0_.getHttpRequest();
/*     */     
/*  73 */     for (HttpPipelineConnection httppipelineconnection = getConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy()); !httppipelineconnection.addRequest(p_addRequest_0_); httppipelineconnection = getConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy()))
/*     */     {
/*  75 */       removeConnection(httprequest.getHost(), httprequest.getPort(), httprequest.getProxy(), httppipelineconnection);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static synchronized HttpPipelineConnection getConnection(String p_getConnection_0_, int p_getConnection_1_, Proxy p_getConnection_2_) {
/*  81 */     String s = makeConnectionKey(p_getConnection_0_, p_getConnection_1_, p_getConnection_2_);
/*  82 */     HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)mapConnections.get(s);
/*     */     
/*  84 */     if (httppipelineconnection == null) {
/*     */       
/*  86 */       httppipelineconnection = new HttpPipelineConnection(p_getConnection_0_, p_getConnection_1_, p_getConnection_2_);
/*  87 */       mapConnections.put(s, httppipelineconnection);
/*     */     } 
/*     */     
/*  90 */     return httppipelineconnection;
/*     */   }
/*     */ 
/*     */   
/*     */   private static synchronized void removeConnection(String p_removeConnection_0_, int p_removeConnection_1_, Proxy p_removeConnection_2_, HttpPipelineConnection p_removeConnection_3_) {
/*  95 */     String s = makeConnectionKey(p_removeConnection_0_, p_removeConnection_1_, p_removeConnection_2_);
/*  96 */     HttpPipelineConnection httppipelineconnection = (HttpPipelineConnection)mapConnections.get(s);
/*     */     
/*  98 */     if (httppipelineconnection == p_removeConnection_3_)
/*     */     {
/* 100 */       mapConnections.remove(s);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static String makeConnectionKey(String p_makeConnectionKey_0_, int p_makeConnectionKey_1_, Proxy p_makeConnectionKey_2_) {
/* 106 */     String s = String.valueOf(p_makeConnectionKey_0_) + ":" + p_makeConnectionKey_1_ + "-" + p_makeConnectionKey_2_;
/* 107 */     return s;
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] get(String p_get_0_) throws IOException {
/* 112 */     return get(p_get_0_, Proxy.NO_PROXY);
/*     */   }
/*     */ 
/*     */   
/*     */   public static byte[] get(String p_get_0_, Proxy p_get_1_) throws IOException {
/* 117 */     if (p_get_0_.startsWith("file:")) {
/*     */       
/* 119 */       URL url = new URL(p_get_0_);
/* 120 */       InputStream inputstream = url.openStream();
/* 121 */       byte[] abyte = Config.readAll(inputstream);
/* 122 */       return abyte;
/*     */     } 
/*     */ 
/*     */     
/* 126 */     HttpRequest httprequest = makeRequest(p_get_0_, p_get_1_);
/* 127 */     HttpResponse httpresponse = executeRequest(httprequest);
/*     */     
/* 129 */     if (httpresponse.getStatus() / 100 != 2)
/*     */     {
/* 131 */       throw new IOException("HTTP response: " + httpresponse.getStatus());
/*     */     }
/*     */ 
/*     */     
/* 135 */     return httpresponse.getBody();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static HttpResponse executeRequest(HttpRequest p_executeRequest_0_) throws IOException {
/* 142 */     final Map<String, Object> map = new HashMap<>();
/* 143 */     String s = "Response";
/* 144 */     String s1 = "Exception";
/* 145 */     HttpListener httplistener = new HttpListener()
/*     */       {
/*     */         public void finished(HttpRequest p_finished_1_, HttpResponse p_finished_2_)
/*     */         {
/* 149 */           synchronized (map) {
/*     */             
/* 151 */             map.put("Response", p_finished_2_);
/* 152 */             map.notifyAll();
/*     */           } 
/*     */         }
/*     */         
/*     */         public void failed(HttpRequest p_failed_1_, Exception p_failed_2_) {
/* 157 */           synchronized (map) {
/*     */             
/* 159 */             map.put("Exception", p_failed_2_);
/* 160 */             map.notifyAll();
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 165 */     synchronized (map) {
/*     */       
/* 167 */       HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(p_executeRequest_0_, httplistener);
/* 168 */       addRequest(httppipelinerequest);
/*     */ 
/*     */       
/*     */       try {
/* 172 */         map.wait();
/*     */       }
/* 174 */       catch (InterruptedException var10) {
/*     */         
/* 176 */         throw new InterruptedIOException("Interrupted");
/*     */       } 
/*     */       
/* 179 */       Exception exception = (Exception)map.get("Exception");
/*     */       
/* 181 */       if (exception != null) {
/*     */         
/* 183 */         if (exception instanceof IOException)
/*     */         {
/* 185 */           throw (IOException)exception;
/*     */         }
/* 187 */         if (exception instanceof RuntimeException)
/*     */         {
/* 189 */           throw (RuntimeException)exception;
/*     */         }
/*     */ 
/*     */         
/* 193 */         throw new RuntimeException(exception.getMessage(), exception);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 198 */       HttpResponse httpresponse = (HttpResponse)map.get("Response");
/*     */       
/* 200 */       if (httpresponse == null)
/*     */       {
/* 202 */         throw new IOException("Response is null");
/*     */       }
/*     */ 
/*     */       
/* 206 */       return httpresponse;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean hasActiveRequests() {
/* 214 */     for (Object httppipelineconnection : mapConnections.values()) {
/*     */       
/* 216 */       if (((HttpPipelineConnection)httppipelineconnection).hasActiveRequests())
/*     */       {
/* 218 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 222 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\HttpPipeline.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */