/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.ListeningExecutorService;
/*     */ import com.google.common.util.concurrent.MoreExecutors;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.Proxy;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class HttpUtil
/*     */ {
/*  34 */   public static final ListeningExecutorService DOWNLOADER_EXECUTOR = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setDaemon(true).setNameFormat("Downloader %d").build()));
/*     */ 
/*     */   
/*  37 */   private static final AtomicInteger DOWNLOAD_THREADS_STARTED = new AtomicInteger(0);
/*  38 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String buildPostString(Map<String, Object> data) {
/*  45 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/*  47 */     for (Map.Entry<String, Object> entry : data.entrySet()) {
/*     */       
/*  49 */       if (stringbuilder.length() > 0)
/*     */       {
/*  51 */         stringbuilder.append('&');
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/*  56 */         stringbuilder.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
/*     */       }
/*  58 */       catch (UnsupportedEncodingException unsupportedencodingexception1) {
/*     */         
/*  60 */         unsupportedencodingexception1.printStackTrace();
/*     */       } 
/*     */       
/*  63 */       if (entry.getValue() != null) {
/*     */         
/*  65 */         stringbuilder.append('=');
/*     */ 
/*     */         
/*     */         try {
/*  69 */           stringbuilder.append(URLEncoder.encode(entry.getValue().toString(), "UTF-8"));
/*     */         }
/*  71 */         catch (UnsupportedEncodingException unsupportedencodingexception) {
/*     */           
/*  73 */           unsupportedencodingexception.printStackTrace();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  78 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String postMap(URL url, Map<String, Object> data, boolean skipLoggingErrors, @Nullable Proxy p_151226_3_) {
/*  86 */     return post(url, buildPostString(data), skipLoggingErrors, p_151226_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String post(URL url, String content, boolean skipLoggingErrors, @Nullable Proxy p_151225_3_) {
/*     */     try {
/*  96 */       if (p_151225_3_ == null)
/*     */       {
/*  98 */         p_151225_3_ = Proxy.NO_PROXY;
/*     */       }
/*     */       
/* 101 */       HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection(p_151225_3_);
/* 102 */       httpurlconnection.setRequestMethod("POST");
/* 103 */       httpurlconnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
/* 104 */       httpurlconnection.setRequestProperty("Content-Length", (content.getBytes()).length);
/* 105 */       httpurlconnection.setRequestProperty("Content-Language", "en-US");
/* 106 */       httpurlconnection.setUseCaches(false);
/* 107 */       httpurlconnection.setDoInput(true);
/* 108 */       httpurlconnection.setDoOutput(true);
/* 109 */       DataOutputStream dataoutputstream = new DataOutputStream(httpurlconnection.getOutputStream());
/* 110 */       dataoutputstream.writeBytes(content);
/* 111 */       dataoutputstream.flush();
/* 112 */       dataoutputstream.close();
/* 113 */       BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(httpurlconnection.getInputStream()));
/* 114 */       StringBuffer stringbuffer = new StringBuffer();
/*     */       
/*     */       String s;
/* 117 */       while ((s = bufferedreader.readLine()) != null) {
/*     */         
/* 119 */         stringbuffer.append(s);
/* 120 */         stringbuffer.append('\r');
/*     */       } 
/*     */       
/* 123 */       bufferedreader.close();
/* 124 */       return stringbuffer.toString();
/*     */     }
/* 126 */     catch (Exception exception) {
/*     */       
/* 128 */       if (!skipLoggingErrors)
/*     */       {
/* 130 */         LOGGER.error("Could not post to {}", url, exception);
/*     */       }
/*     */       
/* 133 */       return "";
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ListenableFuture<Object> downloadResourcePack(final File saveFile, final String packUrl, final Map<String, String> p_180192_2_, final int maxSize, @Nullable final IProgressUpdate p_180192_4_, final Proxy p_180192_5_) {
/* 139 */     ListenableFuture<?> listenablefuture = DOWNLOADER_EXECUTOR.submit(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 143 */             HttpURLConnection httpurlconnection = null;
/* 144 */             InputStream inputstream = null;
/* 145 */             OutputStream outputstream = null;
/*     */             
/* 147 */             if (p_180192_4_ != null) {
/*     */               
/* 149 */               p_180192_4_.resetProgressAndMessage(I18n.translateToLocal("resourcepack.downloading"));
/* 150 */               p_180192_4_.displayLoadingString(I18n.translateToLocal("resourcepack.requesting"));
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/* 157 */               byte[] abyte = new byte[4096];
/* 158 */               URL url = new URL(packUrl);
/* 159 */               httpurlconnection = (HttpURLConnection)url.openConnection(p_180192_5_);
/* 160 */               httpurlconnection.setInstanceFollowRedirects(true);
/* 161 */               float f = 0.0F;
/* 162 */               float f1 = p_180192_2_.entrySet().size();
/*     */               
/* 164 */               for (Map.Entry<String, String> entry : (Iterable<Map.Entry<String, String>>)p_180192_2_.entrySet()) {
/*     */                 
/* 166 */                 httpurlconnection.setRequestProperty(entry.getKey(), entry.getValue());
/*     */                 
/* 168 */                 if (p_180192_4_ != null)
/*     */                 {
/* 170 */                   p_180192_4_.setLoadingProgress((int)(++f / f1 * 100.0F));
/*     */                 }
/*     */               } 
/*     */               
/* 174 */               inputstream = httpurlconnection.getInputStream();
/* 175 */               f1 = httpurlconnection.getContentLength();
/* 176 */               int i = httpurlconnection.getContentLength();
/*     */               
/* 178 */               if (p_180192_4_ != null)
/*     */               {
/* 180 */                 p_180192_4_.displayLoadingString(I18n.translateToLocalFormatted("resourcepack.progress", new Object[] { String.format("%.2f", new Object[] { Float.valueOf(f1 / 1000.0F / 1000.0F) }) }));
/*     */               }
/*     */               
/* 183 */               if (saveFile.exists()) {
/*     */                 
/* 185 */                 long j = saveFile.length();
/*     */                 
/* 187 */                 if (j == i) {
/*     */                   
/* 189 */                   if (p_180192_4_ != null)
/*     */                   {
/* 191 */                     p_180192_4_.setDoneWorking();
/*     */                   }
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */                 
/* 197 */                 HttpUtil.LOGGER.warn("Deleting {} as it does not match what we currently have ({} vs our {}).", saveFile, Integer.valueOf(i), Long.valueOf(j));
/* 198 */                 FileUtils.deleteQuietly(saveFile);
/*     */               }
/* 200 */               else if (saveFile.getParentFile() != null) {
/*     */                 
/* 202 */                 saveFile.getParentFile().mkdirs();
/*     */               } 
/*     */               
/* 205 */               outputstream = new DataOutputStream(new FileOutputStream(saveFile));
/*     */               
/* 207 */               if (maxSize > 0 && f1 > maxSize) {
/*     */                 
/* 209 */                 if (p_180192_4_ != null)
/*     */                 {
/* 211 */                   p_180192_4_.setDoneWorking();
/*     */                 }
/*     */                 
/* 214 */                 throw new IOException("Filesize is bigger than maximum allowed (file is " + f + ", limit is " + maxSize + ")");
/*     */               } 
/*     */               
/*     */               int k;
/*     */               
/* 219 */               while ((k = inputstream.read(abyte)) >= 0) {
/*     */                 
/* 221 */                 f += k;
/*     */                 
/* 223 */                 if (p_180192_4_ != null)
/*     */                 {
/* 225 */                   p_180192_4_.setLoadingProgress((int)(f / f1 * 100.0F));
/*     */                 }
/*     */                 
/* 228 */                 if (maxSize > 0 && f > maxSize) {
/*     */                   
/* 230 */                   if (p_180192_4_ != null)
/*     */                   {
/* 232 */                     p_180192_4_.setDoneWorking();
/*     */                   }
/*     */                   
/* 235 */                   throw new IOException("Filesize was bigger than maximum allowed (got >= " + f + ", limit was " + maxSize + ")");
/*     */                 } 
/*     */                 
/* 238 */                 if (Thread.interrupted()) {
/*     */                   
/* 240 */                   HttpUtil.LOGGER.error("INTERRUPTED");
/*     */                   
/* 242 */                   if (p_180192_4_ != null)
/*     */                   {
/* 244 */                     p_180192_4_.setDoneWorking();
/*     */                   }
/*     */                   
/*     */                   return;
/*     */                 } 
/*     */                 
/* 250 */                 outputstream.write(abyte, 0, k);
/*     */               } 
/*     */               
/* 253 */               if (p_180192_4_ != null) {
/*     */                 
/* 255 */                 p_180192_4_.setDoneWorking();
/*     */                 
/*     */                 return;
/*     */               } 
/* 259 */             } catch (Throwable throwable) {
/*     */               
/* 261 */               throwable.printStackTrace();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             }
/*     */             finally {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 286 */               IOUtils.closeQuietly(inputstream);
/* 287 */               IOUtils.closeQuietly(outputstream);
/*     */             } 
/*     */           }
/*     */         });
/* 291 */     return (ListenableFuture)listenablefuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getSuitableLanPort() throws IOException {
/* 296 */     ServerSocket serversocket = null;
/* 297 */     int i = -1;
/*     */ 
/*     */     
/*     */     try {
/* 301 */       serversocket = new ServerSocket(0);
/* 302 */       i = serversocket.getLocalPort();
/*     */     } finally {
/*     */ 
/*     */       
/*     */       try {
/*     */         
/* 308 */         if (serversocket != null)
/*     */         {
/* 310 */           serversocket.close();
/*     */         }
/*     */       }
/* 313 */       catch (IOException iOException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 319 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\HttpUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */