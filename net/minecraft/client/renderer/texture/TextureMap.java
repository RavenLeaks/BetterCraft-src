/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.TreeSet;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.StitcherException;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import optifine.BetterGrass;
/*     */ import optifine.Config;
/*     */ import optifine.ConnectedTextures;
/*     */ import optifine.CustomItems;
/*     */ import optifine.Reflector;
/*     */ import optifine.ReflectorForge;
/*     */ import optifine.SpriteDependencies;
/*     */ import optifine.TextureUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import shadersmod.client.ShadersTex;
/*     */ 
/*     */ 
/*     */ public class TextureMap
/*     */   extends AbstractTexture
/*     */   implements ITickableTextureObject
/*     */ {
/*  46 */   private static final Logger LOGGER = LogManager.getLogger();
/*  47 */   public static final ResourceLocation LOCATION_MISSING_TEXTURE = new ResourceLocation("missingno");
/*  48 */   public static final ResourceLocation LOCATION_BLOCKS_TEXTURE = new ResourceLocation("textures/atlas/blocks.png");
/*     */   
/*     */   private final List<TextureAtlasSprite> listAnimatedSprites;
/*     */   private final Map<String, TextureAtlasSprite> mapRegisteredSprites;
/*     */   private final Map<String, TextureAtlasSprite> mapUploadedSprites;
/*     */   private final String basePath;
/*     */   private final ITextureMapPopulator iconCreator;
/*     */   private int mipmapLevels;
/*     */   private final TextureAtlasSprite missingImage;
/*     */   private TextureAtlasSprite[] iconGrid;
/*     */   private int iconGridSize;
/*     */   private int iconGridCountX;
/*     */   private int iconGridCountY;
/*     */   private double iconGridSizeU;
/*     */   private double iconGridSizeV;
/*     */   private int counterIndexInMap;
/*     */   public int atlasWidth;
/*     */   public int atlasHeight;
/*     */   
/*     */   public TextureMap(String basePathIn) {
/*  68 */     this(basePathIn, (ITextureMapPopulator)null);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureMap(String p_i3_1_, boolean p_i3_2_) {
/*  73 */     this(p_i3_1_, (ITextureMapPopulator)null, p_i3_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureMap(String basePathIn, @Nullable ITextureMapPopulator iconCreatorIn) {
/*  78 */     this(basePathIn, iconCreatorIn, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureMap(String p_i4_1_, ITextureMapPopulator p_i4_2_, boolean p_i4_3_) {
/*  83 */     this.iconGrid = null;
/*  84 */     this.iconGridSize = -1;
/*  85 */     this.iconGridCountX = -1;
/*  86 */     this.iconGridCountY = -1;
/*  87 */     this.iconGridSizeU = -1.0D;
/*  88 */     this.iconGridSizeV = -1.0D;
/*  89 */     this.counterIndexInMap = 0;
/*  90 */     this.atlasWidth = 0;
/*  91 */     this.atlasHeight = 0;
/*  92 */     this.listAnimatedSprites = Lists.newArrayList();
/*  93 */     this.mapRegisteredSprites = Maps.newHashMap();
/*  94 */     this.mapUploadedSprites = Maps.newHashMap();
/*  95 */     this.missingImage = new TextureAtlasSprite("missingno");
/*  96 */     this.basePath = p_i4_1_;
/*  97 */     this.iconCreator = p_i4_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   private void initMissingImage() {
/* 102 */     int i = getMinSpriteSize();
/* 103 */     int[] aint = getMissingImageData(i);
/* 104 */     this.missingImage.setIconWidth(i);
/* 105 */     this.missingImage.setIconHeight(i);
/* 106 */     int[][] aint1 = new int[this.mipmapLevels + 1][];
/* 107 */     aint1[0] = aint;
/* 108 */     this.missingImage.setFramesTextureData(Lists.newArrayList((Object[])new int[][][] { aint1 }));
/* 109 */     this.missingImage.setIndexInMap(this.counterIndexInMap++);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadTexture(IResourceManager resourceManager) throws IOException {
/* 114 */     ShadersTex.resManager = resourceManager;
/*     */     
/* 116 */     if (this.iconCreator != null)
/*     */     {
/* 118 */       loadSprites(resourceManager, this.iconCreator);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSprites(IResourceManager resourceManager, ITextureMapPopulator iconCreatorIn) {
/* 124 */     this.mapRegisteredSprites.clear();
/* 125 */     this.counterIndexInMap = 0;
/* 126 */     Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPre, new Object[] { this });
/* 127 */     iconCreatorIn.registerSprites(this);
/*     */     
/* 129 */     if (this.mipmapLevels >= 4) {
/*     */       
/* 131 */       this.mipmapLevels = detectMaxMipmapLevel(this.mapRegisteredSprites, resourceManager);
/* 132 */       Config.log("Mipmap levels: " + this.mipmapLevels);
/*     */     } 
/*     */     
/* 135 */     initMissingImage();
/* 136 */     deleteGlTexture();
/* 137 */     loadTextureAtlas(resourceManager);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadTextureAtlas(IResourceManager resourceManager) {
/* 142 */     ShadersTex.resManager = resourceManager;
/* 143 */     Config.dbg("Multitexture: " + Config.isMultiTexture());
/*     */     
/* 145 */     if (Config.isMultiTexture())
/*     */     {
/* 147 */       for (TextureAtlasSprite textureatlassprite : this.mapUploadedSprites.values())
/*     */       {
/* 149 */         textureatlassprite.deleteSpriteTexture();
/*     */       }
/*     */     }
/*     */     
/* 153 */     ConnectedTextures.updateIcons(this);
/* 154 */     CustomItems.updateIcons(this);
/* 155 */     BetterGrass.updateIcons(this);
/* 156 */     int k1 = TextureUtils.getGLMaximumTextureSize();
/* 157 */     Stitcher stitcher = new Stitcher(k1, k1, 0, this.mipmapLevels);
/* 158 */     this.mapUploadedSprites.clear();
/* 159 */     this.listAnimatedSprites.clear();
/* 160 */     int i = Integer.MAX_VALUE;
/* 161 */     int j = getMinSpriteSize();
/* 162 */     this.iconGridSize = j;
/* 163 */     int k = 1 << this.mipmapLevels;
/* 164 */     List<TextureAtlasSprite> list = new ArrayList<>(this.mapRegisteredSprites.values());
/*     */     
/* 166 */     for (int l = 0; l < list.size(); l++) {
/*     */       
/* 168 */       TextureAtlasSprite textureatlassprite1 = SpriteDependencies.resolveDependencies(list, l, this);
/* 169 */       ResourceLocation resourcelocation = getResourceLocation(textureatlassprite1);
/* 170 */       IResource iresource = null;
/*     */       
/* 172 */       if (textureatlassprite1.getIndexInMap() < 0)
/*     */       {
/* 174 */         textureatlassprite1.setIndexInMap(this.counterIndexInMap++);
/*     */       }
/*     */       
/* 177 */       if (textureatlassprite1.hasCustomLoader(resourceManager, resourcelocation))
/*     */       
/* 179 */       { if (textureatlassprite1.load(resourceManager, resourcelocation, p_lambda$loadTextureAtlas$0_1_ -> (TextureAtlasSprite)this.mapRegisteredSprites.get(p_lambda$loadTextureAtlas$0_1_.toString())))
/*     */         
/*     */         { 
/*     */ 
/*     */           
/* 184 */           Config.dbg("Custom loader (skipped): " + textureatlassprite1); }
/*     */         else
/*     */         
/* 187 */         { Config.dbg("Custom loader: " + textureatlassprite1);
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
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 225 */           int k2 = textureatlassprite1.getIconWidth();
/* 226 */           int i3 = textureatlassprite1.getIconHeight(); }  } else { try { PngSizeInfo pngsizeinfo = PngSizeInfo.makeFromResource(resourceManager.getResource(resourcelocation)); if (Config.isShaders()) { iresource = ShadersTex.loadResource(resourceManager, resourcelocation); } else { iresource = resourceManager.getResource(resourcelocation); }  boolean flag = (iresource.getMetadata("animation") != null); textureatlassprite1.loadSprite(pngsizeinfo, flag); } catch (RuntimeException runtimeexception) { LOGGER.error("Unable to parse metadata from {}", resourcelocation, runtimeexception); ReflectorForge.FMLClientHandler_trackBrokenTexture(resourcelocation, runtimeexception.getMessage()); } catch (IOException ioexception) { LOGGER.error("Using missing texture, unable to load " + resourcelocation + ", " + ioexception.getClass().getName()); ReflectorForge.FMLClientHandler_trackMissingTexture(resourcelocation); } finally { IOUtils.closeQuietly((Closeable)iresource); }  int m = textureatlassprite1.getIconWidth(); int n = textureatlassprite1.getIconHeight(); }
/*     */     
/*     */     } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 271 */     int l1 = Math.min(i, k);
/* 272 */     int i2 = MathHelper.log2(l1);
/*     */     
/* 274 */     if (i2 < 0)
/*     */     {
/* 276 */       i2 = 0;
/*     */     }
/*     */     
/* 279 */     if (i2 < this.mipmapLevels) {
/*     */       
/* 281 */       LOGGER.warn("{}: dropping miplevel from {} to {}, because of minimum power of two: {}", this.basePath, Integer.valueOf(this.mipmapLevels), Integer.valueOf(i2), Integer.valueOf(l1));
/* 282 */       this.mipmapLevels = i2;
/*     */     } 
/*     */     
/* 285 */     this.missingImage.generateMipmaps(this.mipmapLevels);
/* 286 */     stitcher.addSprite(this.missingImage);
/*     */ 
/*     */     
/*     */     try {
/* 290 */       stitcher.doStitch();
/*     */     }
/* 292 */     catch (StitcherException stitcherexception) {
/*     */       
/* 294 */       throw stitcherexception;
/*     */     } 
/*     */     
/* 297 */     LOGGER.info("Created: {}x{} {}-atlas", Integer.valueOf(stitcher.getCurrentWidth()), Integer.valueOf(stitcher.getCurrentHeight()), this.basePath);
/*     */     
/* 299 */     if (Config.isShaders()) {
/*     */       
/* 301 */       ShadersTex.allocateTextureMap(getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight(), stitcher, this);
/*     */     }
/*     */     else {
/*     */       
/* 305 */       TextureUtil.allocateTextureImpl(getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
/*     */     } 
/*     */     
/* 308 */     Map<String, TextureAtlasSprite> map = Maps.newHashMap(this.mapRegisteredSprites);
/*     */     
/* 310 */     for (TextureAtlasSprite textureatlassprite2 : stitcher.getStichSlots()) {
/*     */       
/* 312 */       if (Config.isShaders())
/*     */       {
/* 314 */         ShadersTex.setIconName(ShadersTex.setSprite(textureatlassprite2).getIconName());
/*     */       }
/*     */       
/* 317 */       String s = textureatlassprite2.getIconName();
/* 318 */       map.remove(s);
/* 319 */       this.mapUploadedSprites.put(s, textureatlassprite2);
/*     */ 
/*     */       
/*     */       try {
/* 323 */         if (Config.isShaders())
/*     */         {
/* 325 */           ShadersTex.uploadTexSubForLoadAtlas(textureatlassprite2.getFrameTextureData(0), textureatlassprite2.getIconWidth(), textureatlassprite2.getIconHeight(), textureatlassprite2.getOriginX(), textureatlassprite2.getOriginY(), false, false);
/*     */         }
/*     */         else
/*     */         {
/* 329 */           TextureUtil.uploadTextureMipmap(textureatlassprite2.getFrameTextureData(0), textureatlassprite2.getIconWidth(), textureatlassprite2.getIconHeight(), textureatlassprite2.getOriginX(), textureatlassprite2.getOriginY(), false, false);
/*     */         }
/*     */       
/* 332 */       } catch (Throwable throwable) {
/*     */         Throwable throwable1;
/* 334 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Stitching texture atlas");
/* 335 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Texture being stitched together");
/* 336 */         crashreportcategory.addCrashSection("Atlas path", this.basePath);
/* 337 */         crashreportcategory.addCrashSection("Sprite", textureatlassprite2);
/* 338 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */       
/* 341 */       if (textureatlassprite2.hasAnimationMetadata())
/*     */       {
/* 343 */         this.listAnimatedSprites.add(textureatlassprite2);
/*     */       }
/*     */     } 
/*     */     
/* 347 */     for (TextureAtlasSprite textureatlassprite3 : map.values())
/*     */     {
/* 349 */       textureatlassprite3.copyFrom(this.missingImage);
/*     */     }
/*     */     
/* 352 */     if (Config.isMultiTexture()) {
/*     */       
/* 354 */       int j2 = stitcher.getCurrentWidth();
/* 355 */       int l2 = stitcher.getCurrentHeight();
/*     */       
/* 357 */       for (TextureAtlasSprite textureatlassprite4 : stitcher.getStichSlots()) {
/*     */         
/* 359 */         textureatlassprite4.sheetWidth = j2;
/* 360 */         textureatlassprite4.sheetHeight = l2;
/* 361 */         textureatlassprite4.mipmapLevels = this.mipmapLevels;
/* 362 */         TextureAtlasSprite textureatlassprite5 = textureatlassprite4.spriteSingle;
/*     */         
/* 364 */         if (textureatlassprite5 != null) {
/*     */           
/* 366 */           if (textureatlassprite5.getIconWidth() <= 0) {
/*     */             
/* 368 */             textureatlassprite5.setIconWidth(textureatlassprite4.getIconWidth());
/* 369 */             textureatlassprite5.setIconHeight(textureatlassprite4.getIconHeight());
/* 370 */             textureatlassprite5.initSprite(textureatlassprite4.getIconWidth(), textureatlassprite4.getIconHeight(), 0, 0, false);
/* 371 */             textureatlassprite5.clearFramesTextureData();
/* 372 */             List<int[][]> list1 = textureatlassprite4.getFramesTextureData();
/* 373 */             textureatlassprite5.setFramesTextureData(list1);
/* 374 */             textureatlassprite5.setAnimationMetadata(textureatlassprite4.getAnimationMetadata());
/*     */           } 
/*     */           
/* 377 */           textureatlassprite5.sheetWidth = j2;
/* 378 */           textureatlassprite5.sheetHeight = l2;
/* 379 */           textureatlassprite5.mipmapLevels = this.mipmapLevels;
/* 380 */           textureatlassprite4.bindSpriteTexture();
/* 381 */           boolean flag2 = false;
/* 382 */           boolean flag1 = true;
/*     */ 
/*     */           
/*     */           try {
/* 386 */             TextureUtil.uploadTextureMipmap(textureatlassprite5.getFrameTextureData(0), textureatlassprite5.getIconWidth(), textureatlassprite5.getIconHeight(), textureatlassprite5.getOriginX(), textureatlassprite5.getOriginY(), flag2, flag1);
/*     */           }
/* 388 */           catch (Exception exception) {
/*     */             
/* 390 */             Config.dbg("Error uploading sprite single: " + textureatlassprite5 + ", parent: " + textureatlassprite4);
/* 391 */             exception.printStackTrace();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 396 */       Config.getMinecraft().getTextureManager().bindTexture(LOCATION_BLOCKS_TEXTURE);
/*     */     } 
/*     */     
/* 399 */     Reflector.callVoid(Reflector.ForgeHooksClient_onTextureStitchedPost, new Object[] { this });
/* 400 */     updateIconGrid(stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
/*     */     
/* 402 */     if (Config.equals(System.getProperty("saveTextureMap"), "true")) {
/*     */       
/* 404 */       Config.dbg("Exporting texture map: " + this.basePath);
/* 405 */       TextureUtils.saveGlTexture("debug/" + this.basePath.replaceAll("/", "_"), getGlTextureId(), this.mipmapLevels, stitcher.getCurrentWidth(), stitcher.getCurrentHeight());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generateMipmaps(IResourceManager resourceManager, final TextureAtlasSprite texture) {
/* 411 */     ResourceLocation resourcelocation1 = getResourceLocation(texture);
/* 412 */     IResource iresource1 = null;
/*     */     
/* 414 */     if (texture.hasCustomLoader(resourceManager, resourcelocation1)) {
/*     */       
/* 416 */       TextureUtils.generateCustomMipmaps(texture, this.mipmapLevels);
/*     */     } else {
/*     */ 
/*     */       
/*     */       try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 426 */         iresource1 = resourceManager.getResource(resourcelocation1);
/* 427 */         texture.loadSpriteFrames(iresource1, this.mipmapLevels + 1);
/*     */       
/*     */       }
/* 430 */       catch (RuntimeException runtimeexception1) {
/*     */         
/* 432 */         LOGGER.error("Unable to parse metadata from {}", resourcelocation1, runtimeexception1);
/* 433 */         boolean flag4 = false;
/*     */       }
/* 435 */       catch (IOException ioexception1) {
/*     */         
/* 437 */         LOGGER.error("Using missing texture, unable to load {}", resourcelocation1, ioexception1);
/* 438 */         boolean flag4 = false;
/* 439 */         boolean crashreportcategory = flag4;
/* 440 */         return crashreportcategory;
/*     */       }
/*     */       finally {
/*     */         
/* 444 */         IOUtils.closeQuietly((Closeable)iresource1);
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 453 */       texture.generateMipmaps(this.mipmapLevels);
/* 454 */       return true;
/*     */     }
/* 456 */     catch (Throwable throwable1) {
/*     */       Throwable throwable2;
/* 458 */       CrashReport crashreport1 = CrashReport.makeCrashReport(throwable2, "Applying mipmap");
/* 459 */       CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Sprite being mipmapped");
/* 460 */       crashreportcategory1.setDetail("Sprite name", new ICrashReportDetail<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 464 */               return texture.getIconName();
/*     */             }
/*     */           });
/* 467 */       crashreportcategory1.setDetail("Sprite size", new ICrashReportDetail<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 471 */               return String.valueOf(texture.getIconWidth()) + " x " + texture.getIconHeight();
/*     */             }
/*     */           });
/* 474 */       crashreportcategory1.setDetail("Sprite frames", new ICrashReportDetail<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 478 */               return String.valueOf(texture.getFrameCount()) + " frames";
/*     */             }
/*     */           });
/* 481 */       crashreportcategory1.addCrashSection("Mipmap levels", Integer.valueOf(this.mipmapLevels));
/* 482 */       throw new ReportedException(crashreport1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getResourceLocation(TextureAtlasSprite p_184396_1_) {
/* 488 */     ResourceLocation resourcelocation1 = new ResourceLocation(p_184396_1_.getIconName());
/* 489 */     return completeResourceLocation(resourcelocation1);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation completeResourceLocation(ResourceLocation p_completeResourceLocation_1_) {
/* 494 */     return isAbsoluteLocation(p_completeResourceLocation_1_) ? new ResourceLocation(p_completeResourceLocation_1_.getResourceDomain(), String.valueOf(p_completeResourceLocation_1_.getResourcePath()) + ".png") : new ResourceLocation(p_completeResourceLocation_1_.getResourceDomain(), String.format("%s/%s%s", new Object[] { this.basePath, p_completeResourceLocation_1_.getResourcePath(), ".png" }));
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getAtlasSprite(String iconName) {
/* 499 */     TextureAtlasSprite textureatlassprite6 = this.mapUploadedSprites.get(iconName);
/*     */     
/* 501 */     if (textureatlassprite6 == null)
/*     */     {
/* 503 */       textureatlassprite6 = this.missingImage;
/*     */     }
/*     */     
/* 506 */     return textureatlassprite6;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAnimations() {
/* 511 */     if (Config.isShaders())
/*     */     {
/* 513 */       ShadersTex.updatingTex = getMultiTexID();
/*     */     }
/*     */     
/* 516 */     boolean flag3 = false;
/* 517 */     boolean flag4 = false;
/* 518 */     TextureUtil.bindTexture(getGlTextureId());
/*     */     
/* 520 */     for (TextureAtlasSprite textureatlassprite6 : this.listAnimatedSprites) {
/*     */       
/* 522 */       if (isTerrainAnimationActive(textureatlassprite6)) {
/*     */         
/* 524 */         textureatlassprite6.updateAnimation();
/*     */         
/* 526 */         if (textureatlassprite6.spriteNormal != null)
/*     */         {
/* 528 */           flag3 = true;
/*     */         }
/*     */         
/* 531 */         if (textureatlassprite6.spriteSpecular != null)
/*     */         {
/* 533 */           flag4 = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 538 */     if (Config.isMultiTexture()) {
/*     */       
/* 540 */       for (TextureAtlasSprite textureatlassprite8 : this.listAnimatedSprites) {
/*     */         
/* 542 */         if (isTerrainAnimationActive(textureatlassprite8)) {
/*     */           
/* 544 */           TextureAtlasSprite textureatlassprite7 = textureatlassprite8.spriteSingle;
/*     */           
/* 546 */           if (textureatlassprite7 != null) {
/*     */             
/* 548 */             if (textureatlassprite8 == TextureUtils.iconClock || textureatlassprite8 == TextureUtils.iconCompass)
/*     */             {
/* 550 */               textureatlassprite7.frameCounter = textureatlassprite8.frameCounter;
/*     */             }
/*     */             
/* 553 */             textureatlassprite8.bindSpriteTexture();
/* 554 */             textureatlassprite7.updateAnimation();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 559 */       TextureUtil.bindTexture(getGlTextureId());
/*     */     } 
/*     */     
/* 562 */     if (Config.isShaders()) {
/*     */       
/* 564 */       if (flag3) {
/*     */         
/* 566 */         TextureUtil.bindTexture((getMultiTexID()).norm);
/*     */         
/* 568 */         for (TextureAtlasSprite textureatlassprite9 : this.listAnimatedSprites) {
/*     */           
/* 570 */           if (textureatlassprite9.spriteNormal != null && isTerrainAnimationActive(textureatlassprite9)) {
/*     */             
/* 572 */             if (textureatlassprite9 == TextureUtils.iconClock || textureatlassprite9 == TextureUtils.iconCompass)
/*     */             {
/* 574 */               textureatlassprite9.spriteNormal.frameCounter = textureatlassprite9.frameCounter;
/*     */             }
/*     */             
/* 577 */             textureatlassprite9.spriteNormal.updateAnimation();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 582 */       if (flag4) {
/*     */         
/* 584 */         TextureUtil.bindTexture((getMultiTexID()).spec);
/*     */         
/* 586 */         for (TextureAtlasSprite textureatlassprite10 : this.listAnimatedSprites) {
/*     */           
/* 588 */           if (textureatlassprite10.spriteSpecular != null && isTerrainAnimationActive(textureatlassprite10)) {
/*     */             
/* 590 */             if (textureatlassprite10 == TextureUtils.iconClock || textureatlassprite10 == TextureUtils.iconCompass)
/*     */             {
/* 592 */               textureatlassprite10.spriteNormal.frameCounter = textureatlassprite10.frameCounter;
/*     */             }
/*     */             
/* 595 */             textureatlassprite10.spriteSpecular.updateAnimation();
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 600 */       if (flag3 || flag4)
/*     */       {
/* 602 */         TextureUtil.bindTexture(getGlTextureId());
/*     */       }
/*     */     } 
/*     */     
/* 606 */     if (Config.isShaders())
/*     */     {
/* 608 */       ShadersTex.updatingTex = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite registerSprite(ResourceLocation location) {
/* 614 */     if (location == null)
/*     */     {
/* 616 */       throw new IllegalArgumentException("Location cannot be null!");
/*     */     }
/*     */ 
/*     */     
/* 620 */     TextureAtlasSprite textureatlassprite6 = this.mapRegisteredSprites.get(location.toString());
/*     */     
/* 622 */     if (textureatlassprite6 == null) {
/*     */       
/* 624 */       textureatlassprite6 = TextureAtlasSprite.makeAtlasSprite(location);
/* 625 */       this.mapRegisteredSprites.put(location.toString(), textureatlassprite6);
/*     */       
/* 627 */       if (textureatlassprite6.getIndexInMap() < 0)
/*     */       {
/* 629 */         textureatlassprite6.setIndexInMap(this.counterIndexInMap++);
/*     */       }
/*     */     } 
/*     */     
/* 633 */     return textureatlassprite6;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 639 */     updateAnimations();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMipmapLevels(int mipmapLevelsIn) {
/* 644 */     this.mipmapLevels = mipmapLevelsIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getMissingSprite() {
/* 649 */     return this.missingImage;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public TextureAtlasSprite getTextureExtry(String p_getTextureExtry_1_) {
/* 655 */     return this.mapRegisteredSprites.get(p_getTextureExtry_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setTextureEntry(TextureAtlasSprite p_setTextureEntry_1_) {
/* 660 */     String s1 = p_setTextureEntry_1_.getIconName();
/*     */     
/* 662 */     if (!this.mapRegisteredSprites.containsKey(s1)) {
/*     */       
/* 664 */       this.mapRegisteredSprites.put(s1, p_setTextureEntry_1_);
/*     */       
/* 666 */       if (p_setTextureEntry_1_.getIndexInMap() < 0)
/*     */       {
/* 668 */         p_setTextureEntry_1_.setIndexInMap(this.counterIndexInMap++);
/*     */       }
/*     */       
/* 671 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 675 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBasePath() {
/* 681 */     return this.basePath;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMipmapLevels() {
/* 686 */     return this.mipmapLevels;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isAbsoluteLocation(ResourceLocation p_isAbsoluteLocation_1_) {
/* 691 */     String s1 = p_isAbsoluteLocation_1_.getResourcePath();
/* 692 */     return isAbsoluteLocationPath(s1);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isAbsoluteLocationPath(String p_isAbsoluteLocationPath_1_) {
/* 697 */     String s1 = p_isAbsoluteLocationPath_1_.toLowerCase();
/* 698 */     return !(!s1.startsWith("mcpatcher/") && !s1.startsWith("optifine/"));
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getSpriteSafe(String p_getSpriteSafe_1_) {
/* 703 */     ResourceLocation resourcelocation1 = new ResourceLocation(p_getSpriteSafe_1_);
/* 704 */     return this.mapRegisteredSprites.get(resourcelocation1.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getRegisteredSprite(ResourceLocation p_getRegisteredSprite_1_) {
/* 709 */     return this.mapRegisteredSprites.get(p_getRegisteredSprite_1_.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isTerrainAnimationActive(TextureAtlasSprite p_isTerrainAnimationActive_1_) {
/* 714 */     if (p_isTerrainAnimationActive_1_ != TextureUtils.iconWaterStill && p_isTerrainAnimationActive_1_ != TextureUtils.iconWaterFlow) {
/*     */       
/* 716 */       if (p_isTerrainAnimationActive_1_ != TextureUtils.iconLavaStill && p_isTerrainAnimationActive_1_ != TextureUtils.iconLavaFlow) {
/*     */         
/* 718 */         if (p_isTerrainAnimationActive_1_ != TextureUtils.iconFireLayer0 && p_isTerrainAnimationActive_1_ != TextureUtils.iconFireLayer1) {
/*     */           
/* 720 */           if (p_isTerrainAnimationActive_1_ == TextureUtils.iconPortal)
/*     */           {
/* 722 */             return Config.isAnimatedPortal();
/*     */           }
/*     */ 
/*     */           
/* 726 */           return (p_isTerrainAnimationActive_1_ != TextureUtils.iconClock && p_isTerrainAnimationActive_1_ != TextureUtils.iconCompass) ? Config.isAnimatedTerrain() : true;
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 731 */         return Config.isAnimatedFire();
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 736 */       return Config.isAnimatedLava();
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 741 */     return Config.isAnimatedWater();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCountRegisteredSprites() {
/* 747 */     return this.counterIndexInMap;
/*     */   }
/*     */ 
/*     */   
/*     */   private int detectMaxMipmapLevel(Map p_detectMaxMipmapLevel_1_, IResourceManager p_detectMaxMipmapLevel_2_) {
/* 752 */     int k3 = detectMinimumSpriteSize(p_detectMaxMipmapLevel_1_, p_detectMaxMipmapLevel_2_, 20);
/*     */     
/* 754 */     if (k3 < 16)
/*     */     {
/* 756 */       k3 = 16;
/*     */     }
/*     */     
/* 759 */     k3 = MathHelper.smallestEncompassingPowerOfTwo(k3);
/*     */     
/* 761 */     if (k3 > 16)
/*     */     {
/* 763 */       Config.log("Sprite size: " + k3);
/*     */     }
/*     */     
/* 766 */     int l3 = MathHelper.log2(k3);
/*     */     
/* 768 */     if (l3 < 4)
/*     */     {
/* 770 */       l3 = 4;
/*     */     }
/*     */     
/* 773 */     return l3;
/*     */   }
/*     */ 
/*     */   
/*     */   private int detectMinimumSpriteSize(Map p_detectMinimumSpriteSize_1_, IResourceManager p_detectMinimumSpriteSize_2_, int p_detectMinimumSpriteSize_3_) {
/* 778 */     Map<Object, Object> map1 = new HashMap<>();
/*     */     
/* 780 */     for (Object entry : p_detectMinimumSpriteSize_1_.entrySet()) {
/*     */       
/* 782 */       TextureAtlasSprite textureatlassprite6 = (TextureAtlasSprite)((Map.Entry)entry).getValue();
/* 783 */       ResourceLocation resourcelocation1 = new ResourceLocation(textureatlassprite6.getIconName());
/* 784 */       ResourceLocation resourcelocation2 = completeResourceLocation(resourcelocation1);
/*     */       
/* 786 */       if (!textureatlassprite6.hasCustomLoader(p_detectMinimumSpriteSize_2_, resourcelocation1)) {
/*     */         
/*     */         try {
/*     */           
/* 790 */           IResource iresource1 = p_detectMinimumSpriteSize_2_.getResource(resourcelocation2);
/*     */           
/* 792 */           if (iresource1 != null) {
/*     */             
/* 794 */             InputStream inputstream = iresource1.getInputStream();
/*     */             
/* 796 */             if (inputstream != null)
/*     */             {
/* 798 */               Dimension dimension = TextureUtils.getImageSize(inputstream, "png");
/*     */               
/* 800 */               if (dimension != null)
/*     */               {
/* 802 */                 int k3 = dimension.width;
/* 803 */                 int l3 = MathHelper.smallestEncompassingPowerOfTwo(k3);
/*     */                 
/* 805 */                 if (!map1.containsKey(Integer.valueOf(l3))) {
/*     */                   
/* 807 */                   map1.put(Integer.valueOf(l3), Integer.valueOf(1));
/*     */                   
/*     */                   continue;
/*     */                 } 
/* 811 */                 int i4 = ((Integer)map1.get(Integer.valueOf(l3))).intValue();
/* 812 */                 map1.put(Integer.valueOf(l3), Integer.valueOf(i4 + 1));
/*     */               }
/*     */             
/*     */             }
/*     */           
/*     */           } 
/* 818 */         } catch (Exception exception) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 825 */     int j4 = 0;
/* 826 */     Set<?> set = map1.keySet();
/* 827 */     Set set1 = new TreeSet(set);
/*     */ 
/*     */     
/* 830 */     for (Iterator<Integer> iterator = set1.iterator(); iterator.hasNext(); j4 += i) {
/*     */       
/* 832 */       int l4 = ((Integer)iterator.next()).intValue();
/* 833 */       int i = ((Integer)map1.get(Integer.valueOf(l4))).intValue();
/*     */     } 
/*     */     
/* 836 */     int k4 = 16;
/* 837 */     int i5 = 0;
/* 838 */     int j5 = j4 * p_detectMinimumSpriteSize_3_ / 100;
/* 839 */     Iterator<Integer> iterator1 = set1.iterator();
/*     */     
/* 841 */     while (iterator1.hasNext()) {
/*     */       
/* 843 */       int k5 = ((Integer)iterator1.next()).intValue();
/* 844 */       int l5 = ((Integer)map1.get(Integer.valueOf(k5))).intValue();
/* 845 */       i5 += l5;
/*     */       
/* 847 */       if (k5 > k4)
/*     */       {
/* 849 */         k4 = k5;
/*     */       }
/*     */       
/* 852 */       if (i5 > j5)
/*     */       {
/* 854 */         return k4;
/*     */       }
/*     */     } 
/*     */     
/* 858 */     return k4;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getMinSpriteSize() {
/* 863 */     int k3 = 1 << this.mipmapLevels;
/*     */     
/* 865 */     if (k3 < 8)
/*     */     {
/* 867 */       k3 = 8;
/*     */     }
/*     */     
/* 870 */     return k3;
/*     */   }
/*     */ 
/*     */   
/*     */   private int[] getMissingImageData(int p_getMissingImageData_1_) {
/* 875 */     BufferedImage bufferedimage = new BufferedImage(16, 16, 2);
/* 876 */     bufferedimage.setRGB(0, 0, 16, 16, TextureUtil.MISSING_TEXTURE_DATA, 0, 16);
/* 877 */     BufferedImage bufferedimage1 = TextureUtils.scaleToPowerOfTwo(bufferedimage, p_getMissingImageData_1_);
/* 878 */     int[] aint = new int[p_getMissingImageData_1_ * p_getMissingImageData_1_];
/* 879 */     bufferedimage1.getRGB(0, 0, p_getMissingImageData_1_, p_getMissingImageData_1_, aint, 0, p_getMissingImageData_1_);
/* 880 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTextureBound() {
/* 885 */     int k3 = GlStateManager.getBoundTexture();
/* 886 */     int l3 = getGlTextureId();
/* 887 */     return (k3 == l3);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateIconGrid(int p_updateIconGrid_1_, int p_updateIconGrid_2_) {
/* 892 */     this.iconGridCountX = -1;
/* 893 */     this.iconGridCountY = -1;
/* 894 */     this.iconGrid = null;
/*     */     
/* 896 */     if (this.iconGridSize > 0) {
/*     */       
/* 898 */       this.iconGridCountX = p_updateIconGrid_1_ / this.iconGridSize;
/* 899 */       this.iconGridCountY = p_updateIconGrid_2_ / this.iconGridSize;
/* 900 */       this.iconGrid = new TextureAtlasSprite[this.iconGridCountX * this.iconGridCountY];
/* 901 */       this.iconGridSizeU = 1.0D / this.iconGridCountX;
/* 902 */       this.iconGridSizeV = 1.0D / this.iconGridCountY;
/*     */       
/* 904 */       for (TextureAtlasSprite textureatlassprite6 : this.mapUploadedSprites.values()) {
/*     */         
/* 906 */         double d0 = 0.5D / p_updateIconGrid_1_;
/* 907 */         double d1 = 0.5D / p_updateIconGrid_2_;
/* 908 */         double d2 = Math.min(textureatlassprite6.getMinU(), textureatlassprite6.getMaxU()) + d0;
/* 909 */         double d3 = Math.min(textureatlassprite6.getMinV(), textureatlassprite6.getMaxV()) + d1;
/* 910 */         double d4 = Math.max(textureatlassprite6.getMinU(), textureatlassprite6.getMaxU()) - d0;
/* 911 */         double d5 = Math.max(textureatlassprite6.getMinV(), textureatlassprite6.getMaxV()) - d1;
/* 912 */         int k3 = (int)(d2 / this.iconGridSizeU);
/* 913 */         int l3 = (int)(d3 / this.iconGridSizeV);
/* 914 */         int i4 = (int)(d4 / this.iconGridSizeU);
/* 915 */         int j4 = (int)(d5 / this.iconGridSizeV);
/*     */         
/* 917 */         for (int k4 = k3; k4 <= i4; k4++) {
/*     */           
/* 919 */           if (k4 >= 0 && k4 < this.iconGridCountX) {
/*     */             
/* 921 */             for (int l4 = l3; l4 <= j4; l4++) {
/*     */               
/* 923 */               if (l4 >= 0 && l4 < this.iconGridCountX)
/*     */               {
/* 925 */                 int i5 = l4 * this.iconGridCountX + k4;
/* 926 */                 this.iconGrid[i5] = textureatlassprite6;
/*     */               }
/*     */               else
/*     */               {
/* 930 */                 Config.warn("Invalid grid V: " + l4 + ", icon: " + textureatlassprite6.getIconName());
/*     */               }
/*     */             
/*     */             } 
/*     */           } else {
/*     */             
/* 936 */             Config.warn("Invalid grid U: " + k4 + ", icon: " + textureatlassprite6.getIconName());
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getIconByUV(double p_getIconByUV_1_, double p_getIconByUV_3_) {
/* 945 */     if (this.iconGrid == null)
/*     */     {
/* 947 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 951 */     int k3 = (int)(p_getIconByUV_1_ / this.iconGridSizeU);
/* 952 */     int l3 = (int)(p_getIconByUV_3_ / this.iconGridSizeV);
/* 953 */     int i4 = l3 * this.iconGridCountX + k3;
/* 954 */     return (i4 >= 0 && i4 <= this.iconGrid.length) ? this.iconGrid[i4] : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\texture\TextureMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */