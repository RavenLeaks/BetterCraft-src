/*     */ package optifine;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.util.Map;
/*     */ import net.minecraft.client.Minecraft;
/*     */ 
/*     */ public class HttpUtils
/*     */ {
/*  16 */   private static String playerItemsUrl = null;
/*     */   public static final String SERVER_URL = "http://s.optifine.net";
/*     */   public static final String POST_URL = "http://optifine.net";
/*     */   
/*     */   public static byte[] get(String p_get_0_) throws IOException {
/*     */     byte[] abyte1;
/*  22 */     HttpURLConnection httpurlconnection = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  27 */       URL url = new URL(p_get_0_);
/*  28 */       httpurlconnection = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
/*  29 */       httpurlconnection.setDoInput(true);
/*  30 */       httpurlconnection.setDoOutput(false);
/*  31 */       httpurlconnection.connect();
/*     */       
/*  33 */       if (httpurlconnection.getResponseCode() / 100 != 2) {
/*     */         
/*  35 */         if (httpurlconnection.getErrorStream() != null)
/*     */         {
/*  37 */           Config.readAll(httpurlconnection.getErrorStream());
/*     */         }
/*     */         
/*  40 */         throw new IOException("HTTP response: " + httpurlconnection.getResponseCode());
/*     */       } 
/*     */       
/*  43 */       InputStream inputstream = httpurlconnection.getInputStream();
/*  44 */       byte[] abyte = new byte[httpurlconnection.getContentLength()];
/*  45 */       int i = 0;
/*     */ 
/*     */       
/*     */       do {
/*  49 */         int j = inputstream.read(abyte, i, abyte.length - i);
/*     */         
/*  51 */         if (j < 0)
/*     */         {
/*  53 */           throw new IOException("Input stream closed: " + p_get_0_);
/*     */         }
/*     */         
/*  56 */         i += j;
/*     */       }
/*  58 */       while (i < abyte.length);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  64 */       abyte1 = abyte;
/*     */     }
/*     */     finally {
/*     */       
/*  68 */       if (httpurlconnection != null)
/*     */       {
/*  70 */         httpurlconnection.disconnect();
/*     */       }
/*     */     } 
/*     */     
/*  74 */     return abyte1;
/*     */   }
/*     */   
/*     */   public static String post(String p_post_0_, Map p_post_1_, byte[] p_post_2_) throws IOException {
/*     */     String s3;
/*  79 */     HttpURLConnection httpurlconnection = null;
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  84 */       URL url = new URL(p_post_0_);
/*  85 */       httpurlconnection = (HttpURLConnection)url.openConnection(Minecraft.getMinecraft().getProxy());
/*  86 */       httpurlconnection.setRequestMethod("POST");
/*     */       
/*  88 */       if (p_post_1_ != null)
/*     */       {
/*  90 */         for (Object s : p_post_1_.keySet()) {
/*     */           
/*  92 */           String s1 = (String)p_post_1_.get(s);
/*  93 */           httpurlconnection.setRequestProperty((String)s, s1);
/*     */         } 
/*     */       }
/*     */       
/*  97 */       httpurlconnection.setRequestProperty("Content-Type", "text/plain");
/*  98 */       httpurlconnection.setRequestProperty("Content-Length", p_post_2_.length);
/*  99 */       httpurlconnection.setRequestProperty("Content-Language", "en-US");
/* 100 */       httpurlconnection.setUseCaches(false);
/* 101 */       httpurlconnection.setDoInput(true);
/* 102 */       httpurlconnection.setDoOutput(true);
/* 103 */       OutputStream outputstream = httpurlconnection.getOutputStream();
/* 104 */       outputstream.write(p_post_2_);
/* 105 */       outputstream.flush();
/* 106 */       outputstream.close();
/* 107 */       InputStream inputstream = httpurlconnection.getInputStream();
/* 108 */       InputStreamReader inputstreamreader = new InputStreamReader(inputstream, "ASCII");
/* 109 */       BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/* 110 */       StringBuffer stringbuffer = new StringBuffer();
/*     */       
/*     */       String s2;
/* 113 */       while ((s2 = bufferedreader.readLine()) != null) {
/*     */         
/* 115 */         stringbuffer.append(s2);
/* 116 */         stringbuffer.append('\r');
/*     */       } 
/*     */       
/* 119 */       bufferedreader.close();
/* 120 */       s3 = stringbuffer.toString();
/*     */     }
/*     */     finally {
/*     */       
/* 124 */       if (httpurlconnection != null)
/*     */       {
/* 126 */         httpurlconnection.disconnect();
/*     */       }
/*     */     } 
/*     */     
/* 130 */     return s3;
/*     */   }
/*     */ 
/*     */   
/*     */   public static synchronized String getPlayerItemsUrl() {
/* 135 */     if (playerItemsUrl == null) {
/*     */ 
/*     */       
/*     */       try {
/* 139 */         boolean flag = Config.parseBoolean(System.getProperty("player.models.local"), false);
/*     */         
/* 141 */         if (flag)
/*     */         {
/* 143 */           File file1 = (Minecraft.getMinecraft()).mcDataDir;
/* 144 */           File file2 = new File(file1, "playermodels");
/* 145 */           playerItemsUrl = file2.toURI().toURL().toExternalForm();
/*     */         }
/*     */       
/* 148 */       } catch (Exception exception) {
/*     */         
/* 150 */         Config.warn(exception.getClass().getName() + ": " + exception.getMessage());
/*     */       } 
/*     */       
/* 153 */       if (playerItemsUrl == null)
/*     */       {
/* 155 */         playerItemsUrl = "http://s.optifine.net";
/*     */       }
/*     */     } 
/*     */     
/* 159 */     return playerItemsUrl;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\HttpUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */