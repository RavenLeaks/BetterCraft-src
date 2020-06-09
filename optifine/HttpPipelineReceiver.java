/*     */ package optifine;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class HttpPipelineReceiver
/*     */   extends Thread {
/*  13 */   private HttpPipelineConnection httpPipelineConnection = null;
/*  14 */   private static final Charset ASCII = Charset.forName("ASCII");
/*     */   
/*     */   private static final String HEADER_CONTENT_LENGTH = "Content-Length";
/*     */   private static final char CR = '\r';
/*     */   private static final char LF = '\n';
/*     */   
/*     */   public HttpPipelineReceiver(HttpPipelineConnection p_i57_1_) {
/*  21 */     super("HttpPipelineReceiver");
/*  22 */     this.httpPipelineConnection = p_i57_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  27 */     while (!Thread.interrupted()) {
/*     */       
/*  29 */       HttpPipelineRequest httppipelinerequest = null;
/*     */ 
/*     */       
/*     */       try {
/*  33 */         httppipelinerequest = this.httpPipelineConnection.getNextRequestReceive();
/*  34 */         InputStream inputstream = this.httpPipelineConnection.getInputStream();
/*  35 */         HttpResponse httpresponse = readResponse(inputstream);
/*  36 */         this.httpPipelineConnection.onResponseReceived(httppipelinerequest, httpresponse);
/*     */       }
/*  38 */       catch (InterruptedException var4) {
/*     */         
/*     */         return;
/*     */       }
/*  42 */       catch (Exception exception) {
/*     */         
/*  44 */         this.httpPipelineConnection.onExceptionReceive(httppipelinerequest, exception);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private HttpResponse readResponse(InputStream p_readResponse_1_) throws IOException {
/*  51 */     String s = readLine(p_readResponse_1_);
/*  52 */     String[] astring = Config.tokenize(s, " ");
/*     */     
/*  54 */     if (astring.length < 3)
/*     */     {
/*  56 */       throw new IOException("Invalid status line: " + s);
/*     */     }
/*     */ 
/*     */     
/*  60 */     String s1 = astring[0];
/*  61 */     int i = Config.parseInt(astring[1], 0);
/*  62 */     String s2 = astring[2];
/*  63 */     Map<String, String> map = new LinkedHashMap<>();
/*     */ 
/*     */     
/*     */     while (true) {
/*  67 */       String s3 = readLine(p_readResponse_1_);
/*     */       
/*  69 */       if (s3.length() <= 0) {
/*     */         
/*  71 */         byte[] abyte = null;
/*  72 */         String s6 = map.get("Content-Length");
/*     */         
/*  74 */         if (s6 != null) {
/*     */           
/*  76 */           int k = Config.parseInt(s6, -1);
/*     */           
/*  78 */           if (k > 0)
/*     */           {
/*  80 */             abyte = new byte[k];
/*  81 */             readFull(abyte, p_readResponse_1_);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/*  86 */           String s7 = map.get("Transfer-Encoding");
/*     */           
/*  88 */           if (Config.equals(s7, "chunked"))
/*     */           {
/*  90 */             abyte = readContentChunked(p_readResponse_1_);
/*     */           }
/*     */         } 
/*     */         
/*  94 */         return new HttpResponse(i, s, map, abyte);
/*     */       } 
/*     */       
/*  97 */       int j = s3.indexOf(":");
/*     */       
/*  99 */       if (j > 0) {
/*     */         
/* 101 */         String s4 = s3.substring(0, j).trim();
/* 102 */         String s5 = s3.substring(j + 1).trim();
/* 103 */         map.put(s4, s5);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private byte[] readContentChunked(InputStream p_readContentChunked_1_) throws IOException {
/*     */     int i;
/* 111 */     ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/*     */ 
/*     */     
/*     */     do {
/* 115 */       String s = readLine(p_readContentChunked_1_);
/* 116 */       String[] astring = Config.tokenize(s, "; ");
/* 117 */       i = Integer.parseInt(astring[0], 16);
/* 118 */       byte[] abyte = new byte[i];
/* 119 */       readFull(abyte, p_readContentChunked_1_);
/* 120 */       bytearrayoutputstream.write(abyte);
/* 121 */       readLine(p_readContentChunked_1_);
/*     */     }
/* 123 */     while (i != 0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 129 */     return bytearrayoutputstream.toByteArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readFull(byte[] p_readFull_1_, InputStream p_readFull_2_) throws IOException {
/* 136 */     for (int i = 0; i < p_readFull_1_.length; i += j) {
/*     */       
/* 138 */       int j = p_readFull_2_.read(p_readFull_1_, i, p_readFull_1_.length - i);
/*     */       
/* 140 */       if (j < 0)
/*     */       {
/* 142 */         throw new EOFException();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String readLine(InputStream p_readLine_1_) throws IOException {
/* 149 */     ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/* 150 */     int i = -1;
/* 151 */     boolean flag = false;
/*     */ 
/*     */     
/*     */     while (true) {
/* 155 */       int j = p_readLine_1_.read();
/*     */       
/* 157 */       if (j < 0) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 162 */       bytearrayoutputstream.write(j);
/*     */       
/* 164 */       if (i == 13 && j == 10) {
/*     */         
/* 166 */         flag = true;
/*     */         
/*     */         break;
/*     */       } 
/* 170 */       i = j;
/*     */     } 
/*     */     
/* 173 */     byte[] abyte = bytearrayoutputstream.toByteArray();
/* 174 */     String s = new String(abyte, ASCII);
/*     */     
/* 176 */     if (flag)
/*     */     {
/* 178 */       s = s.substring(0, s.length() - 2);
/*     */     }
/*     */     
/* 181 */     return s;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\HttpPipelineReceiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */