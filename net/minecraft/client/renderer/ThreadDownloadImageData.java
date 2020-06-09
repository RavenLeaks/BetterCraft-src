/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import javax.annotation.Nullable;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import optifine.CapeImageBuffer;
/*     */ import optifine.Config;
/*     */ import optifine.HttpPipeline;
/*     */ import optifine.HttpRequest;
/*     */ import optifine.HttpResponse;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class ThreadDownloadImageData
/*     */   extends SimpleTexture
/*     */ {
/*  31 */   private static final Logger LOGGER = LogManager.getLogger();
/*  32 */   private static final AtomicInteger TEXTURE_DOWNLOADER_THREAD_ID = new AtomicInteger(0);
/*     */   @Nullable
/*     */   private final File cacheFile;
/*     */   private final String imageUrl;
/*     */   @Nullable
/*     */   private final IImageBuffer imageBuffer;
/*     */   @Nullable
/*     */   private BufferedImage bufferedImage;
/*     */   @Nullable
/*     */   private Thread imageThread;
/*     */   private boolean textureUploaded;
/*  43 */   public Boolean imageFound = null;
/*     */   
/*     */   public boolean pipeline = false;
/*     */   
/*     */   public ThreadDownloadImageData(@Nullable File cacheFileIn, String imageUrlIn, ResourceLocation textureResourceLocation, @Nullable IImageBuffer imageBufferIn) {
/*  48 */     super(textureResourceLocation);
/*  49 */     this.cacheFile = cacheFileIn;
/*  50 */     this.imageUrl = imageUrlIn;
/*  51 */     this.imageBuffer = imageBufferIn;
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkTextureUploaded() {
/*  56 */     if (!this.textureUploaded && this.bufferedImage != null) {
/*     */       
/*  58 */       this.textureUploaded = true;
/*     */       
/*  60 */       if (this.textureLocation != null)
/*     */       {
/*  62 */         deleteGlTexture();
/*     */       }
/*     */       
/*  65 */       TextureUtil.uploadTextureImage(super.getGlTextureId(), this.bufferedImage);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGlTextureId() {
/*  71 */     checkTextureUploaded();
/*  72 */     return super.getGlTextureId();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBufferedImage(BufferedImage bufferedImageIn) {
/*  77 */     this.bufferedImage = bufferedImageIn;
/*     */     
/*  79 */     if (this.imageBuffer != null)
/*     */     {
/*  81 */       this.imageBuffer.skinAvailable();
/*     */     }
/*     */     
/*  84 */     this.imageFound = Boolean.valueOf((this.bufferedImage != null));
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/*  89 */     if (this.bufferedImage == null && this.textureLocation != null)
/*     */     {
/*  91 */       super.loadTexture(resourceManager);
/*     */     }
/*     */     
/*  94 */     if (this.imageThread == null)
/*     */     {
/*  96 */       if (this.cacheFile != null && this.cacheFile.isFile()) {
/*     */         
/*  98 */         LOGGER.debug("Loading http texture from local cache ({})", this.cacheFile);
/*     */ 
/*     */         
/*     */         try {
/* 102 */           this.bufferedImage = ImageIO.read(this.cacheFile);
/*     */           
/* 104 */           if (this.imageBuffer != null)
/*     */           {
/* 106 */             setBufferedImage(this.imageBuffer.parseUserSkin(this.bufferedImage));
/*     */           }
/*     */           
/* 109 */           loadingFinished();
/*     */         }
/* 111 */         catch (IOException ioexception) {
/*     */           
/* 113 */           LOGGER.error("Couldn't load skin {}", this.cacheFile, ioexception);
/* 114 */           loadTextureFromServer();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 119 */         loadTextureFromServer();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadTextureFromServer() {
/* 126 */     this.imageThread = new Thread("Texture Downloader #" + TEXTURE_DOWNLOADER_THREAD_ID.incrementAndGet())
/*     */       {
/*     */         public void run()
/*     */         {
/* 130 */           HttpURLConnection httpurlconnection = null;
/* 131 */           ThreadDownloadImageData.LOGGER.debug("Downloading http texture from {} to {}", ThreadDownloadImageData.this.imageUrl, ThreadDownloadImageData.this.cacheFile);
/*     */           
/* 133 */           if (ThreadDownloadImageData.this.shouldPipeline()) {
/*     */             
/* 135 */             ThreadDownloadImageData.this.loadPipelined();
/*     */           } else {
/*     */             
/*     */             try { BufferedImage bufferedimage;
/*     */ 
/*     */               
/* 141 */               httpurlconnection = (HttpURLConnection)(new URL(ThreadDownloadImageData.this.imageUrl)).openConnection(Minecraft.getMinecraft().getProxy());
/* 142 */               httpurlconnection.setDoInput(true);
/* 143 */               httpurlconnection.setDoOutput(false);
/* 144 */               httpurlconnection.connect();
/*     */               
/* 146 */               if (httpurlconnection.getResponseCode() / 100 != 2) {
/*     */                 
/* 148 */                 if (httpurlconnection.getErrorStream() != null)
/*     */                 {
/* 150 */                   Config.readAll(httpurlconnection.getErrorStream());
/*     */                 }
/*     */ 
/*     */                 
/*     */                 return;
/*     */               } 
/*     */ 
/*     */               
/* 158 */               if (ThreadDownloadImageData.this.cacheFile != null)
/*     */               {
/* 160 */                 FileUtils.copyInputStreamToFile(httpurlconnection.getInputStream(), ThreadDownloadImageData.this.cacheFile);
/* 161 */                 bufferedimage = ImageIO.read(ThreadDownloadImageData.this.cacheFile);
/*     */               }
/*     */               else
/*     */               {
/* 165 */                 bufferedimage = TextureUtil.readBufferedImage(httpurlconnection.getInputStream());
/*     */ 
/*     */ 
/*     */               
/*     */               }
/*     */ 
/*     */ 
/*     */               
/*     */                }
/*     */             
/* 175 */             catch (Exception exception1)
/*     */             
/* 177 */             { ThreadDownloadImageData.LOGGER.error("Couldn't download http texture: " + exception1.getMessage());
/*     */ 
/*     */               
/*     */               return; }
/*     */             finally
/* 182 */             { if (httpurlconnection != null)
/*     */               {
/* 184 */                 httpurlconnection.disconnect();
/*     */               }
/*     */               
/* 187 */               ThreadDownloadImageData.this.loadingFinished(); }  if (httpurlconnection != null) httpurlconnection.disconnect();  ThreadDownloadImageData.this.loadingFinished();
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 192 */     this.imageThread.setDaemon(true);
/* 193 */     this.imageThread.start();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean shouldPipeline() {
/* 198 */     if (!this.pipeline)
/*     */     {
/* 200 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 204 */     Proxy proxy = Minecraft.getMinecraft().getProxy();
/*     */     
/* 206 */     if (proxy.type() != Proxy.Type.DIRECT && proxy.type() != Proxy.Type.SOCKS)
/*     */     {
/* 208 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 212 */     return this.imageUrl.startsWith("http://");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadPipelined() {
/*     */     try {
/*     */       BufferedImage bufferedimage;
/* 221 */       HttpRequest httprequest = HttpPipeline.makeRequest(this.imageUrl, Minecraft.getMinecraft().getProxy());
/* 222 */       HttpResponse httpresponse = HttpPipeline.executeRequest(httprequest);
/*     */       
/* 224 */       if (httpresponse.getStatus() / 100 != 2) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 229 */       byte[] abyte = httpresponse.getBody();
/* 230 */       ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(abyte);
/*     */ 
/*     */       
/* 233 */       if (this.cacheFile != null) {
/*     */         
/* 235 */         FileUtils.copyInputStreamToFile(bytearrayinputstream, this.cacheFile);
/* 236 */         bufferedimage = ImageIO.read(this.cacheFile);
/*     */       }
/*     */       else {
/*     */         
/* 240 */         bufferedimage = TextureUtil.readBufferedImage(bytearrayinputstream);
/*     */       } 
/*     */       
/* 243 */       if (this.imageBuffer != null)
/*     */       {
/* 245 */         bufferedimage = this.imageBuffer.parseUserSkin(bufferedimage);
/*     */       }
/*     */       
/* 248 */       setBufferedImage(bufferedimage);
/*     */     }
/* 250 */     catch (Exception exception) {
/*     */       
/* 252 */       LOGGER.error("Couldn't download http texture: " + exception.getClass().getName() + ": " + exception.getMessage());
/*     */ 
/*     */       
/*     */       return;
/*     */     } finally {
/* 257 */       loadingFinished();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadingFinished() {
/* 263 */     this.imageFound = Boolean.valueOf((this.bufferedImage != null));
/*     */     
/* 265 */     if (this.imageBuffer instanceof CapeImageBuffer) {
/*     */       
/* 267 */       CapeImageBuffer capeimagebuffer = (CapeImageBuffer)this.imageBuffer;
/* 268 */       capeimagebuffer.cleanup();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\ThreadDownloadImageData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */