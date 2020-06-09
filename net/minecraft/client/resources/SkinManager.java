/*     */ package net.minecraft.client.resources;
/*     */ 
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.cache.CacheLoader;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.minecraft.InsecureTextureException;
/*     */ import com.mojang.authlib.minecraft.MinecraftProfileTexture;
/*     */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.IImageBuffer;
/*     */ import net.minecraft.client.renderer.ImageBufferDownload;
/*     */ import net.minecraft.client.renderer.ThreadDownloadImageData;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class SkinManager
/*     */ {
/*  30 */   private static final ExecutorService THREAD_POOL = new ThreadPoolExecutor(0, 2, 1L, TimeUnit.MINUTES, new LinkedBlockingQueue<>());
/*     */   
/*     */   private final TextureManager textureManager;
/*     */   private final File skinCacheDir;
/*     */   private final MinecraftSessionService sessionService;
/*     */   private final LoadingCache<GameProfile, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>> skinCacheLoader;
/*     */   
/*     */   public SkinManager(TextureManager textureManagerInstance, File skinCacheDirectory, MinecraftSessionService sessionService) {
/*  38 */     this.textureManager = textureManagerInstance;
/*  39 */     this.skinCacheDir = skinCacheDirectory;
/*  40 */     this.sessionService = sessionService;
/*  41 */     this.skinCacheLoader = CacheBuilder.newBuilder().expireAfterAccess(15L, TimeUnit.SECONDS).build(new CacheLoader<GameProfile, Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>>()
/*     */         {
/*     */           
/*     */           public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> load(GameProfile p_load_1_) throws Exception
/*     */           {
/*     */             try {
/*  47 */               return Minecraft.getMinecraft().getSessionService().getTextures(p_load_1_, false);
/*     */             }
/*  49 */             catch (Throwable var3) {
/*     */               
/*  51 */               return Maps.newHashMap();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation loadSkin(MinecraftProfileTexture profileTexture, MinecraftProfileTexture.Type textureType) {
/*  62 */     return loadSkin(profileTexture, textureType, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResourceLocation loadSkin(final MinecraftProfileTexture profileTexture, final MinecraftProfileTexture.Type textureType, @Nullable final SkinAvailableCallback skinAvailableCallback) {
/*  70 */     final ResourceLocation resourcelocation = new ResourceLocation("skins/" + profileTexture.getHash());
/*  71 */     ITextureObject itextureobject = this.textureManager.getTexture(resourcelocation);
/*     */     
/*  73 */     if (itextureobject != null) {
/*     */       
/*  75 */       if (skinAvailableCallback != null)
/*     */       {
/*  77 */         skinAvailableCallback.skinAvailable(textureType, resourcelocation, profileTexture);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/*  82 */       File file1 = new File(this.skinCacheDir, (profileTexture.getHash().length() > 2) ? profileTexture.getHash().substring(0, 2) : "xx");
/*  83 */       File file2 = new File(file1, profileTexture.getHash());
/*  84 */       final ImageBufferDownload iimagebuffer = (textureType == MinecraftProfileTexture.Type.SKIN) ? new ImageBufferDownload() : null;
/*  85 */       ThreadDownloadImageData threaddownloadimagedata = new ThreadDownloadImageData(file2, profileTexture.getUrl(), DefaultPlayerSkin.getDefaultSkinLegacy(), new IImageBuffer()
/*     */           {
/*     */             public BufferedImage parseUserSkin(BufferedImage image)
/*     */             {
/*  89 */               if (iimagebuffer != null)
/*     */               {
/*  91 */                 image = iimagebuffer.parseUserSkin(image);
/*     */               }
/*     */               
/*  94 */               return image;
/*     */             }
/*     */             
/*     */             public void skinAvailable() {
/*  98 */               if (iimagebuffer != null)
/*     */               {
/* 100 */                 iimagebuffer.skinAvailable();
/*     */               }
/*     */               
/* 103 */               if (skinAvailableCallback != null)
/*     */               {
/* 105 */                 skinAvailableCallback.skinAvailable(textureType, resourcelocation, profileTexture);
/*     */               }
/*     */             }
/*     */           });
/* 109 */       this.textureManager.loadTexture(resourcelocation, (ITextureObject)threaddownloadimagedata);
/*     */     } 
/*     */     
/* 112 */     return resourcelocation;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadProfileTextures(final GameProfile profile, final SkinAvailableCallback skinAvailableCallback, final boolean requireSecure) {
/* 117 */     THREAD_POOL.submit(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 121 */             final Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> map = Maps.newHashMap();
/*     */ 
/*     */             
/*     */             try {
/* 125 */               map.putAll(SkinManager.this.sessionService.getTextures(profile, requireSecure));
/*     */             }
/* 127 */             catch (InsecureTextureException insecureTextureException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 132 */             Minecraft.getMinecraft(); if (map.isEmpty() && profile.getId().equals(Minecraft.getSession().getProfile().getId())) {
/*     */               
/* 134 */               profile.getProperties().clear();
/* 135 */               profile.getProperties().putAll((Multimap)Minecraft.getMinecraft().getProfileProperties());
/* 136 */               map.putAll(SkinManager.this.sessionService.getTextures(profile, false));
/*     */             } 
/*     */             
/* 139 */             Minecraft.getMinecraft().addScheduledTask(new Runnable()
/*     */                 {
/*     */                   public void run()
/*     */                   {
/* 143 */                     if (map.containsKey(MinecraftProfileTexture.Type.SKIN))
/*     */                     {
/* 145 */                       SkinManager.null.access$0(SkinManager.null.this).loadSkin((MinecraftProfileTexture)map.get(MinecraftProfileTexture.Type.SKIN), MinecraftProfileTexture.Type.SKIN, skinAvailableCallback);
/*     */                     }
/*     */                     
/* 148 */                     if (map.containsKey(MinecraftProfileTexture.Type.CAPE))
/*     */                     {
/* 150 */                       SkinManager.null.access$0(SkinManager.null.this).loadSkin((MinecraftProfileTexture)map.get(MinecraftProfileTexture.Type.CAPE), MinecraftProfileTexture.Type.CAPE, skinAvailableCallback);
/*     */                     }
/*     */                   }
/*     */                 });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<MinecraftProfileTexture.Type, MinecraftProfileTexture> loadSkinFromCache(GameProfile profile) {
/* 160 */     return (Map<MinecraftProfileTexture.Type, MinecraftProfileTexture>)this.skinCacheLoader.getUnchecked(profile);
/*     */   }
/*     */   
/*     */   public static interface SkinAvailableCallback {
/*     */     void skinAvailable(MinecraftProfileTexture.Type param1Type, ResourceLocation param1ResourceLocation, MinecraftProfileTexture param1MinecraftProfileTexture);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\SkinManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */