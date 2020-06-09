/*     */ package optifine;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.Proxy;
/*     */ import java.net.Socket;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.regex.Pattern;
/*     */ 
/*     */ public class HttpPipelineConnection
/*     */ {
/*     */   private String host;
/*     */   private int port;
/*     */   private Proxy proxy;
/*     */   private List<HttpPipelineRequest> listRequests;
/*     */   private List<HttpPipelineRequest> listRequestsSend;
/*     */   private Socket socket;
/*     */   private InputStream inputStream;
/*     */   private OutputStream outputStream;
/*     */   private HttpPipelineSender httpPipelineSender;
/*     */   private HttpPipelineReceiver httpPipelineReceiver;
/*     */   private int countRequests;
/*     */   private boolean responseReceived;
/*     */   private long keepaliveTimeoutMs;
/*     */   private int keepaliveMaxCount;
/*     */   private long timeLastActivityMs;
/*     */   private boolean terminated;
/*     */   private static final String LF = "\n";
/*     */   public static final int TIMEOUT_CONNECT_MS = 5000;
/*     */   public static final int TIMEOUT_READ_MS = 5000;
/*  35 */   private static final Pattern patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*");
/*     */ 
/*     */   
/*     */   public HttpPipelineConnection(String p_i55_1_, int p_i55_2_) {
/*  39 */     this(p_i55_1_, p_i55_2_, Proxy.NO_PROXY);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpPipelineConnection(String p_i56_1_, int p_i56_2_, Proxy p_i56_3_) {
/*  44 */     this.host = null;
/*  45 */     this.port = 0;
/*  46 */     this.proxy = Proxy.NO_PROXY;
/*  47 */     this.listRequests = new LinkedList<>();
/*  48 */     this.listRequestsSend = new LinkedList<>();
/*  49 */     this.socket = null;
/*  50 */     this.inputStream = null;
/*  51 */     this.outputStream = null;
/*  52 */     this.httpPipelineSender = null;
/*  53 */     this.httpPipelineReceiver = null;
/*  54 */     this.countRequests = 0;
/*  55 */     this.responseReceived = false;
/*  56 */     this.keepaliveTimeoutMs = 5000L;
/*  57 */     this.keepaliveMaxCount = 1000;
/*  58 */     this.timeLastActivityMs = System.currentTimeMillis();
/*  59 */     this.terminated = false;
/*  60 */     this.host = p_i56_1_;
/*  61 */     this.port = p_i56_2_;
/*  62 */     this.proxy = p_i56_3_;
/*  63 */     this.httpPipelineSender = new HttpPipelineSender(this);
/*  64 */     this.httpPipelineSender.start();
/*  65 */     this.httpPipelineReceiver = new HttpPipelineReceiver(this);
/*  66 */     this.httpPipelineReceiver.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean addRequest(HttpPipelineRequest p_addRequest_1_) {
/*  71 */     if (isClosed())
/*     */     {
/*  73 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  77 */     addRequest(p_addRequest_1_, this.listRequests);
/*  78 */     addRequest(p_addRequest_1_, this.listRequestsSend);
/*  79 */     this.countRequests++;
/*  80 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addRequest(HttpPipelineRequest p_addRequest_1_, List<HttpPipelineRequest> p_addRequest_2_) {
/*  86 */     p_addRequest_2_.add(p_addRequest_1_);
/*  87 */     notifyAll();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void setSocket(Socket p_setSocket_1_) throws IOException {
/*  92 */     if (!this.terminated) {
/*     */       
/*  94 */       if (this.socket != null)
/*     */       {
/*  96 */         throw new IllegalArgumentException("Already connected");
/*     */       }
/*     */ 
/*     */       
/* 100 */       this.socket = p_setSocket_1_;
/* 101 */       this.socket.setTcpNoDelay(true);
/* 102 */       this.inputStream = this.socket.getInputStream();
/* 103 */       this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
/* 104 */       onActivity();
/* 105 */       notifyAll();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized OutputStream getOutputStream() throws IOException, InterruptedException {
/* 112 */     while (this.outputStream == null) {
/*     */       
/* 114 */       checkTimeout();
/* 115 */       wait(1000L);
/*     */     } 
/*     */     
/* 118 */     return this.outputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized InputStream getInputStream() throws IOException, InterruptedException {
/* 123 */     while (this.inputStream == null) {
/*     */       
/* 125 */       checkTimeout();
/* 126 */       wait(1000L);
/*     */     } 
/*     */     
/* 129 */     return this.inputStream;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized HttpPipelineRequest getNextRequestSend() throws InterruptedException, IOException {
/* 134 */     if (this.listRequestsSend.size() <= 0 && this.outputStream != null)
/*     */     {
/* 136 */       this.outputStream.flush();
/*     */     }
/*     */     
/* 139 */     return getNextRequest(this.listRequestsSend, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException {
/* 144 */     return getNextRequest(this.listRequests, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private HttpPipelineRequest getNextRequest(List<HttpPipelineRequest> p_getNextRequest_1_, boolean p_getNextRequest_2_) throws InterruptedException {
/* 149 */     while (p_getNextRequest_1_.size() <= 0) {
/*     */       
/* 151 */       checkTimeout();
/* 152 */       wait(1000L);
/*     */     } 
/*     */     
/* 155 */     onActivity();
/*     */     
/* 157 */     if (p_getNextRequest_2_)
/*     */     {
/* 159 */       return p_getNextRequest_1_.remove(0);
/*     */     }
/*     */ 
/*     */     
/* 163 */     return p_getNextRequest_1_.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkTimeout() {
/* 169 */     if (this.socket != null) {
/*     */       
/* 171 */       long i = this.keepaliveTimeoutMs;
/*     */       
/* 173 */       if (this.listRequests.size() > 0)
/*     */       {
/* 175 */         i = 5000L;
/*     */       }
/*     */       
/* 178 */       long j = System.currentTimeMillis();
/*     */       
/* 180 */       if (j > this.timeLastActivityMs + i)
/*     */       {
/* 182 */         terminate(new InterruptedException("Timeout " + i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void onActivity() {
/* 189 */     this.timeLastActivityMs = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void onRequestSent(HttpPipelineRequest p_onRequestSent_1_) {
/* 194 */     if (!this.terminated)
/*     */     {
/* 196 */       onActivity();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void onResponseReceived(HttpPipelineRequest p_onResponseReceived_1_, HttpResponse p_onResponseReceived_2_) {
/* 202 */     if (!this.terminated) {
/*     */       
/* 204 */       this.responseReceived = true;
/* 205 */       onActivity();
/*     */       
/* 207 */       if (this.listRequests.size() > 0 && this.listRequests.get(0) == p_onResponseReceived_1_) {
/*     */         
/* 209 */         this.listRequests.remove(0);
/* 210 */         p_onResponseReceived_1_.setClosed(true);
/* 211 */         String s = p_onResponseReceived_2_.getHeader("Location");
/*     */         
/* 213 */         if (p_onResponseReceived_2_.getStatus() / 100 == 3 && s != null && p_onResponseReceived_1_.getHttpRequest().getRedirects() < 5) {
/*     */           
/*     */           try
/*     */           {
/* 217 */             s = normalizeUrl(s, p_onResponseReceived_1_.getHttpRequest());
/* 218 */             HttpRequest httprequest = HttpPipeline.makeRequest(s, p_onResponseReceived_1_.getHttpRequest().getProxy());
/* 219 */             httprequest.setRedirects(p_onResponseReceived_1_.getHttpRequest().getRedirects() + 1);
/* 220 */             HttpPipelineRequest httppipelinerequest = new HttpPipelineRequest(httprequest, p_onResponseReceived_1_.getHttpListener());
/* 221 */             HttpPipeline.addRequest(httppipelinerequest);
/*     */           }
/* 223 */           catch (IOException ioexception)
/*     */           {
/* 225 */             p_onResponseReceived_1_.getHttpListener().failed(p_onResponseReceived_1_.getHttpRequest(), ioexception);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 230 */           HttpListener httplistener = p_onResponseReceived_1_.getHttpListener();
/* 231 */           httplistener.finished(p_onResponseReceived_1_.getHttpRequest(), p_onResponseReceived_2_);
/*     */         } 
/*     */         
/* 234 */         checkResponseHeader(p_onResponseReceived_2_);
/*     */       }
/*     */       else {
/*     */         
/* 238 */         throw new IllegalArgumentException("Response out of order: " + p_onResponseReceived_1_);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String normalizeUrl(String p_normalizeUrl_1_, HttpRequest p_normalizeUrl_2_) {
/* 245 */     if (patternFullUrl.matcher(p_normalizeUrl_1_).matches())
/*     */     {
/* 247 */       return p_normalizeUrl_1_;
/*     */     }
/* 249 */     if (p_normalizeUrl_1_.startsWith("//"))
/*     */     {
/* 251 */       return "http:" + p_normalizeUrl_1_;
/*     */     }
/*     */ 
/*     */     
/* 255 */     String s = p_normalizeUrl_2_.getHost();
/*     */     
/* 257 */     if (p_normalizeUrl_2_.getPort() != 80)
/*     */     {
/* 259 */       s = String.valueOf(s) + ":" + p_normalizeUrl_2_.getPort();
/*     */     }
/*     */     
/* 262 */     if (p_normalizeUrl_1_.startsWith("/"))
/*     */     {
/* 264 */       return "http://" + s + p_normalizeUrl_1_;
/*     */     }
/*     */ 
/*     */     
/* 268 */     String s1 = p_normalizeUrl_2_.getFile();
/* 269 */     int i = s1.lastIndexOf("/");
/* 270 */     return (i >= 0) ? ("http://" + s + s1.substring(0, i + 1) + p_normalizeUrl_1_) : ("http://" + s + "/" + p_normalizeUrl_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkResponseHeader(HttpResponse p_checkResponseHeader_1_) {
/* 277 */     String s = p_checkResponseHeader_1_.getHeader("Connection");
/*     */     
/* 279 */     if (s != null && !s.toLowerCase().equals("keep-alive"))
/*     */     {
/* 281 */       terminate(new EOFException("Connection not keep-alive"));
/*     */     }
/*     */     
/* 284 */     String s1 = p_checkResponseHeader_1_.getHeader("Keep-Alive");
/*     */     
/* 286 */     if (s1 != null) {
/*     */       
/* 288 */       String[] astring = Config.tokenize(s1, ",;");
/*     */       
/* 290 */       for (int i = 0; i < astring.length; i++) {
/*     */         
/* 292 */         String s2 = astring[i];
/* 293 */         String[] astring1 = split(s2, '=');
/*     */         
/* 295 */         if (astring1.length >= 2) {
/*     */           
/* 297 */           if (astring1[0].equals("timeout")) {
/*     */             
/* 299 */             int j = Config.parseInt(astring1[1], -1);
/*     */             
/* 301 */             if (j > 0)
/*     */             {
/* 303 */               this.keepaliveTimeoutMs = (j * 1000);
/*     */             }
/*     */           } 
/*     */           
/* 307 */           if (astring1[0].equals("max")) {
/*     */             
/* 309 */             int k = Config.parseInt(astring1[1], -1);
/*     */             
/* 311 */             if (k > 0)
/*     */             {
/* 313 */               this.keepaliveMaxCount = k;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String[] split(String p_split_1_, char p_split_2_) {
/* 323 */     int i = p_split_1_.indexOf(p_split_2_);
/*     */     
/* 325 */     if (i < 0)
/*     */     {
/* 327 */       return new String[] { p_split_1_ };
/*     */     }
/*     */ 
/*     */     
/* 331 */     String s = p_split_1_.substring(0, i);
/* 332 */     String s1 = p_split_1_.substring(i + 1);
/* 333 */     return new String[] { s, s1 };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void onExceptionSend(HttpPipelineRequest p_onExceptionSend_1_, Exception p_onExceptionSend_2_) {
/* 339 */     terminate(p_onExceptionSend_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void onExceptionReceive(HttpPipelineRequest p_onExceptionReceive_1_, Exception p_onExceptionReceive_2_) {
/* 344 */     terminate(p_onExceptionReceive_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized void terminate(Exception p_terminate_1_) {
/* 349 */     if (!this.terminated) {
/*     */       
/* 351 */       this.terminated = true;
/* 352 */       terminateRequests(p_terminate_1_);
/*     */       
/* 354 */       if (this.httpPipelineSender != null)
/*     */       {
/* 356 */         this.httpPipelineSender.interrupt();
/*     */       }
/*     */       
/* 359 */       if (this.httpPipelineReceiver != null)
/*     */       {
/* 361 */         this.httpPipelineReceiver.interrupt();
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 366 */         if (this.socket != null)
/*     */         {
/* 368 */           this.socket.close();
/*     */         }
/*     */       }
/* 371 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 376 */       this.socket = null;
/* 377 */       this.inputStream = null;
/* 378 */       this.outputStream = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void terminateRequests(Exception p_terminateRequests_1_) {
/* 384 */     if (this.listRequests.size() > 0) {
/*     */       
/* 386 */       if (!this.responseReceived) {
/*     */         
/* 388 */         HttpPipelineRequest httppipelinerequest = this.listRequests.remove(0);
/* 389 */         httppipelinerequest.getHttpListener().failed(httppipelinerequest.getHttpRequest(), p_terminateRequests_1_);
/* 390 */         httppipelinerequest.setClosed(true);
/*     */       } 
/*     */       
/* 393 */       while (this.listRequests.size() > 0) {
/*     */         
/* 395 */         HttpPipelineRequest httppipelinerequest1 = this.listRequests.remove(0);
/* 396 */         HttpPipeline.addRequest(httppipelinerequest1);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean isClosed() {
/* 403 */     if (this.terminated)
/*     */     {
/* 405 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 409 */     return (this.countRequests >= this.keepaliveMaxCount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCountRequests() {
/* 415 */     return this.countRequests;
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean hasActiveRequests() {
/* 420 */     return (this.listRequests.size() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHost() {
/* 425 */     return this.host;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPort() {
/* 430 */     return this.port;
/*     */   }
/*     */ 
/*     */   
/*     */   public Proxy getProxy() {
/* 435 */     return this.proxy;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\HttpPipelineConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */