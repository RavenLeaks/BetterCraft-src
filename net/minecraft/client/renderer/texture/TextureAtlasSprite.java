/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.data.AnimationFrame;
/*     */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import optifine.Config;
/*     */ import optifine.TextureUtils;
/*     */ import shadersmod.client.Shaders;
/*     */ 
/*     */ public class TextureAtlasSprite
/*     */ {
/*     */   private final String iconName;
/*  28 */   protected List<int[][]> framesTextureData = Lists.newArrayList();
/*     */   protected int[][] interpolatedFrameData;
/*     */   private AnimationMetadataSection animationMetadata;
/*     */   protected boolean rotated;
/*     */   protected int originX;
/*     */   protected int originY;
/*     */   protected int width;
/*     */   protected int height;
/*     */   private float minU;
/*     */   private float maxU;
/*     */   private float minV;
/*     */   private float maxV;
/*     */   protected int frameCounter;
/*     */   protected int tickCounter;
/*  42 */   private int indexInMap = -1;
/*     */   public float baseU;
/*     */   public float baseV;
/*     */   public int sheetWidth;
/*     */   public int sheetHeight;
/*  47 */   public int glSpriteTextureId = -1;
/*  48 */   public TextureAtlasSprite spriteSingle = null;
/*     */   public boolean isSpriteSingle = false;
/*  50 */   public int mipmapLevels = 0;
/*  51 */   public TextureAtlasSprite spriteNormal = null;
/*  52 */   public TextureAtlasSprite spriteSpecular = null;
/*     */   
/*     */   public boolean isShadersSprite = false;
/*     */   public boolean isDependencyParent = false;
/*     */   
/*     */   private TextureAtlasSprite(TextureAtlasSprite p_i2_1_) {
/*  58 */     this.iconName = p_i2_1_.iconName;
/*  59 */     this.isSpriteSingle = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected TextureAtlasSprite(String spriteName) {
/*  64 */     this.iconName = spriteName;
/*     */     
/*  66 */     if (Config.isMultiTexture())
/*     */     {
/*  68 */       this.spriteSingle = new TextureAtlasSprite(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected static TextureAtlasSprite makeAtlasSprite(ResourceLocation spriteResourceLocation) {
/*  74 */     return new TextureAtlasSprite(spriteResourceLocation.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public void initSprite(int inX, int inY, int originInX, int originInY, boolean rotatedIn) {
/*  79 */     this.originX = originInX;
/*  80 */     this.originY = originInY;
/*  81 */     this.rotated = rotatedIn;
/*  82 */     float f = (float)(0.009999999776482582D / inX);
/*  83 */     float f1 = (float)(0.009999999776482582D / inY);
/*  84 */     this.minU = originInX / (float)inX + f;
/*  85 */     this.maxU = (originInX + this.width) / (float)inX - f;
/*  86 */     this.minV = originInY / inY + f1;
/*  87 */     this.maxV = (originInY + this.height) / inY - f1;
/*  88 */     this.baseU = Math.min(this.minU, this.maxU);
/*  89 */     this.baseV = Math.min(this.minV, this.maxV);
/*     */     
/*  91 */     if (this.spriteSingle != null)
/*     */     {
/*  93 */       this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void copyFrom(TextureAtlasSprite atlasSpirit) {
/*  99 */     this.originX = atlasSpirit.originX;
/* 100 */     this.originY = atlasSpirit.originY;
/* 101 */     this.width = atlasSpirit.width;
/* 102 */     this.height = atlasSpirit.height;
/* 103 */     this.rotated = atlasSpirit.rotated;
/* 104 */     this.minU = atlasSpirit.minU;
/* 105 */     this.maxU = atlasSpirit.maxU;
/* 106 */     this.minV = atlasSpirit.minV;
/* 107 */     this.maxV = atlasSpirit.maxV;
/*     */     
/* 109 */     if (this.spriteSingle != null)
/*     */     {
/* 111 */       this.spriteSingle.initSprite(this.width, this.height, 0, 0, false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginX() {
/* 120 */     return this.originX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getOriginY() {
/* 128 */     return this.originY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconWidth() {
/* 136 */     return this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIconHeight() {
/* 144 */     return this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinU() {
/* 152 */     return this.minU;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxU() {
/* 160 */     return this.maxU;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInterpolatedU(double u) {
/* 168 */     float f = this.maxU - this.minU;
/* 169 */     return this.minU + f * (float)u / 16.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getUnInterpolatedU(float p_188537_1_) {
/* 177 */     float f = this.maxU - this.minU;
/* 178 */     return (p_188537_1_ - this.minU) / f * 16.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMinV() {
/* 186 */     return this.minV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getMaxV() {
/* 194 */     return this.maxV;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getInterpolatedV(double v) {
/* 202 */     float f = this.maxV - this.minV;
/* 203 */     return this.minV + f * (float)v / 16.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getUnInterpolatedV(float p_188536_1_) {
/* 211 */     float f = this.maxV - this.minV;
/* 212 */     return (p_188536_1_ - this.minV) / f * 16.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIconName() {
/* 217 */     return this.iconName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAnimation() {
/* 222 */     if (this.animationMetadata != null) {
/*     */       
/* 224 */       this.tickCounter++;
/*     */       
/* 226 */       if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter)) {
/*     */         
/* 228 */         int i = this.animationMetadata.getFrameIndex(this.frameCounter);
/* 229 */         int j = (this.animationMetadata.getFrameCount() == 0) ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
/* 230 */         this.frameCounter = (this.frameCounter + 1) % j;
/* 231 */         this.tickCounter = 0;
/* 232 */         int k = this.animationMetadata.getFrameIndex(this.frameCounter);
/* 233 */         boolean flag = false;
/* 234 */         boolean flag1 = this.isSpriteSingle;
/*     */         
/* 236 */         if (i != k && k >= 0 && k < this.framesTextureData.size())
/*     */         {
/* 238 */           TextureUtil.uploadTextureMipmap(this.framesTextureData.get(k), this.width, this.height, this.originX, this.originY, flag, flag1);
/*     */         }
/*     */       }
/* 241 */       else if (this.animationMetadata.isInterpolate()) {
/*     */         
/* 243 */         updateAnimationInterpolated();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateAnimationInterpolated() {
/* 250 */     double d0 = 1.0D - this.tickCounter / this.animationMetadata.getFrameTimeSingle(this.frameCounter);
/* 251 */     int i = this.animationMetadata.getFrameIndex(this.frameCounter);
/* 252 */     int j = (this.animationMetadata.getFrameCount() == 0) ? this.framesTextureData.size() : this.animationMetadata.getFrameCount();
/* 253 */     int k = this.animationMetadata.getFrameIndex((this.frameCounter + 1) % j);
/*     */     
/* 255 */     if (i != k && k >= 0 && k < this.framesTextureData.size()) {
/*     */       
/* 257 */       int[][] aint = this.framesTextureData.get(i);
/* 258 */       int[][] aint1 = this.framesTextureData.get(k);
/*     */       
/* 260 */       if (this.interpolatedFrameData == null || this.interpolatedFrameData.length != aint.length)
/*     */       {
/* 262 */         this.interpolatedFrameData = new int[aint.length][];
/*     */       }
/*     */       
/* 265 */       for (int l = 0; l < aint.length; l++) {
/*     */         
/* 267 */         if (this.interpolatedFrameData[l] == null)
/*     */         {
/* 269 */           this.interpolatedFrameData[l] = new int[(aint[l]).length];
/*     */         }
/*     */         
/* 272 */         if (l < aint1.length && (aint1[l]).length == (aint[l]).length)
/*     */         {
/* 274 */           for (int i1 = 0; i1 < (aint[l]).length; i1++) {
/*     */             
/* 276 */             int j1 = aint[l][i1];
/* 277 */             int k1 = aint1[l][i1];
/* 278 */             int l1 = interpolateColor(d0, j1 >> 16 & 0xFF, k1 >> 16 & 0xFF);
/* 279 */             int i2 = interpolateColor(d0, j1 >> 8 & 0xFF, k1 >> 8 & 0xFF);
/* 280 */             int j2 = interpolateColor(d0, j1 & 0xFF, k1 & 0xFF);
/* 281 */             this.interpolatedFrameData[l][i1] = j1 & 0xFF000000 | l1 << 16 | i2 << 8 | j2;
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 286 */       TextureUtil.uploadTextureMipmap(this.interpolatedFrameData, this.width, this.height, this.originX, this.originY, false, false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int interpolateColor(double p_188535_1_, int p_188535_3_, int p_188535_4_) {
/* 292 */     return (int)(p_188535_1_ * p_188535_3_ + (1.0D - p_188535_1_) * p_188535_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int[][] getFrameTextureData(int index) {
/* 297 */     return this.framesTextureData.get(index);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFrameCount() {
/* 302 */     return this.framesTextureData.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIconWidth(int newWidth) {
/* 307 */     this.width = newWidth;
/*     */     
/* 309 */     if (this.spriteSingle != null)
/*     */     {
/* 311 */       this.spriteSingle.setIconWidth(this.width);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIconHeight(int newHeight) {
/* 317 */     this.height = newHeight;
/*     */     
/* 319 */     if (this.spriteSingle != null)
/*     */     {
/* 321 */       this.spriteSingle.setIconHeight(this.height);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSprite(PngSizeInfo sizeInfo, boolean p_188538_2_) throws IOException {
/* 327 */     resetSprite();
/* 328 */     this.width = sizeInfo.pngWidth;
/* 329 */     this.height = sizeInfo.pngHeight;
/*     */     
/* 331 */     if (p_188538_2_) {
/*     */       
/* 333 */       this.height = this.width;
/*     */     }
/* 335 */     else if (sizeInfo.pngHeight != sizeInfo.pngWidth) {
/*     */       
/* 337 */       throw new RuntimeException("broken aspect ratio and not an animation");
/*     */     } 
/*     */     
/* 340 */     if (this.spriteSingle != null) {
/*     */       
/* 342 */       this.spriteSingle.width = this.width;
/* 343 */       this.spriteSingle.height = this.height;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadSpriteFrames(IResource resource, int mipmaplevels) throws IOException {
/* 349 */     BufferedImage bufferedimage = TextureUtil.readBufferedImage(resource.getInputStream());
/*     */     
/* 351 */     if (this.width != bufferedimage.getWidth())
/*     */     {
/* 353 */       bufferedimage = TextureUtils.scaleImage(bufferedimage, this.width);
/*     */     }
/*     */     
/* 356 */     AnimationMetadataSection animationmetadatasection = (AnimationMetadataSection)resource.getMetadata("animation");
/* 357 */     int[][] aint = new int[mipmaplevels][];
/* 358 */     aint[0] = new int[bufferedimage.getWidth() * bufferedimage.getHeight()];
/* 359 */     bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), aint[0], 0, bufferedimage.getWidth());
/*     */     
/* 361 */     if (animationmetadatasection == null) {
/*     */       
/* 363 */       this.framesTextureData.add(aint);
/*     */     }
/*     */     else {
/*     */       
/* 367 */       int i = bufferedimage.getHeight() / this.width;
/*     */       
/* 369 */       if (animationmetadatasection.getFrameCount() > 0) {
/*     */         
/* 371 */         Iterator<Integer> iterator = animationmetadatasection.getFrameIndexSet().iterator();
/*     */         
/* 373 */         while (iterator.hasNext()) {
/*     */           
/* 375 */           int j = ((Integer)iterator.next()).intValue();
/*     */           
/* 377 */           if (j >= i)
/*     */           {
/* 379 */             throw new RuntimeException("invalid frameindex " + j);
/*     */           }
/*     */           
/* 382 */           allocateFrameTextureData(j);
/* 383 */           this.framesTextureData.set(j, getFrameTextureData(aint, this.width, this.width, j));
/*     */         } 
/*     */         
/* 386 */         this.animationMetadata = animationmetadatasection;
/*     */       }
/*     */       else {
/*     */         
/* 390 */         List<AnimationFrame> list = Lists.newArrayList();
/*     */         
/* 392 */         for (int l = 0; l < i; l++) {
/*     */           
/* 394 */           this.framesTextureData.add(getFrameTextureData(aint, this.width, this.width, l));
/* 395 */           list.add(new AnimationFrame(l, -1));
/*     */         } 
/*     */         
/* 398 */         this.animationMetadata = new AnimationMetadataSection(list, this.width, this.height, animationmetadatasection.getFrameTime(), animationmetadatasection.isInterpolate());
/*     */       } 
/*     */     } 
/*     */     
/* 402 */     if (!this.isShadersSprite) {
/*     */       
/* 404 */       if (Config.isShaders())
/*     */       {
/* 406 */         loadShadersSprites();
/*     */       }
/*     */       
/* 409 */       for (int k = 0; k < this.framesTextureData.size(); k++) {
/*     */         
/* 411 */         int[][] aint2 = this.framesTextureData.get(k);
/*     */         
/* 413 */         if (aint2 != null && !this.iconName.startsWith("minecraft:blocks/leaves_"))
/*     */         {
/* 415 */           for (int i1 = 0; i1 < aint2.length; i1++) {
/*     */             
/* 417 */             int[] aint1 = aint2[i1];
/* 418 */             fixTransparentColor(aint1);
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 423 */       if (this.spriteSingle != null) {
/*     */         
/* 425 */         IResource iresource = Config.getResourceManager().getResource(resource.getResourceLocation());
/* 426 */         this.spriteSingle.loadSpriteFrames(iresource, mipmaplevels);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateMipmaps(int level) {
/* 433 */     List<int[][]> list = Lists.newArrayList();
/*     */     
/* 435 */     for (int i = 0; i < this.framesTextureData.size(); i++) {
/*     */       
/* 437 */       final int[][] aint = this.framesTextureData.get(i);
/*     */       
/* 439 */       if (aint != null) {
/*     */         
/*     */         try {
/*     */           
/* 443 */           list.add(TextureUtil.generateMipmapData(level, this.width, aint));
/*     */         }
/* 445 */         catch (Throwable throwable) {
/*     */           
/* 447 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Generating mipmaps for frame");
/* 448 */           CrashReportCategory crashreportcategory = crashreport.makeCategory("Frame being iterated");
/* 449 */           crashreportcategory.addCrashSection("Frame index", Integer.valueOf(i));
/* 450 */           crashreportcategory.setDetail("Frame sizes", new ICrashReportDetail<String>()
/*     */               {
/*     */                 public String call() throws Exception
/*     */                 {
/* 454 */                   StringBuilder stringbuilder = new StringBuilder(); byte b;
/*     */                   int i, arrayOfInt[][];
/* 456 */                   for (i = (arrayOfInt = aint).length, b = 0; b < i; ) { int[] aint1 = arrayOfInt[b];
/*     */                     
/* 458 */                     if (stringbuilder.length() > 0)
/*     */                     {
/* 460 */                       stringbuilder.append(", ");
/*     */                     }
/*     */                     
/* 463 */                     stringbuilder.append((aint1 == null) ? "null" : Integer.valueOf(aint1.length));
/*     */                     b++; }
/*     */                   
/* 466 */                   return stringbuilder.toString();
/*     */                 }
/*     */               });
/* 469 */           throw new ReportedException(crashreport);
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 474 */     setFramesTextureData(list);
/*     */     
/* 476 */     if (this.spriteSingle != null)
/*     */     {
/* 478 */       this.spriteSingle.generateMipmaps(level);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void allocateFrameTextureData(int index) {
/* 484 */     if (this.framesTextureData.size() <= index)
/*     */     {
/* 486 */       for (int i = this.framesTextureData.size(); i <= index; i++)
/*     */       {
/* 488 */         this.framesTextureData.add(null);
/*     */       }
/*     */     }
/*     */     
/* 492 */     if (this.spriteSingle != null)
/*     */     {
/* 494 */       this.spriteSingle.allocateFrameTextureData(index);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static int[][] getFrameTextureData(int[][] data, int rows, int columns, int p_147962_3_) {
/* 500 */     int[][] aint = new int[data.length][];
/*     */     
/* 502 */     for (int i = 0; i < data.length; i++) {
/*     */       
/* 504 */       int[] aint1 = data[i];
/*     */       
/* 506 */       if (aint1 != null) {
/*     */         
/* 508 */         aint[i] = new int[(rows >> i) * (columns >> i)];
/* 509 */         System.arraycopy(aint1, p_147962_3_ * (aint[i]).length, aint[i], 0, (aint[i]).length);
/*     */       } 
/*     */     } 
/*     */     
/* 513 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearFramesTextureData() {
/* 518 */     this.framesTextureData.clear();
/*     */     
/* 520 */     if (this.spriteSingle != null)
/*     */     {
/* 522 */       this.spriteSingle.clearFramesTextureData();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasAnimationMetadata() {
/* 528 */     return (this.animationMetadata != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFramesTextureData(List<int[][]> arrayList) {
/* 533 */     this.framesTextureData = arrayList;
/*     */     
/* 535 */     if (this.spriteSingle != null)
/*     */     {
/* 537 */       this.spriteSingle.setFramesTextureData(arrayList);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void resetSprite() {
/* 543 */     this.animationMetadata = null;
/* 544 */     setFramesTextureData(Lists.newArrayList());
/* 545 */     this.frameCounter = 0;
/* 546 */     this.tickCounter = 0;
/*     */     
/* 548 */     if (this.spriteSingle != null)
/*     */     {
/* 550 */       this.spriteSingle.resetSprite();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 556 */     return "TextureAtlasSprite{name='" + this.iconName + '\'' + ", frameCount=" + this.framesTextureData.size() + ", rotated=" + this.rotated + ", x=" + this.originX + ", y=" + this.originY + ", height=" + this.height + ", width=" + this.width + ", u0=" + this.minU + ", u1=" + this.maxU + ", v0=" + this.minV + ", v1=" + this.maxV + '}';
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasCustomLoader(IResourceManager p_hasCustomLoader_1_, ResourceLocation p_hasCustomLoader_2_) {
/* 561 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean load(IResourceManager p_load_1_, ResourceLocation p_load_2_, Function<ResourceLocation, TextureAtlasSprite> p_load_3_) {
/* 566 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ResourceLocation> getDependencies() {
/* 571 */     return (Collection<ResourceLocation>)ImmutableList.of();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIndexInMap() {
/* 576 */     return this.indexInMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setIndexInMap(int p_setIndexInMap_1_) {
/* 581 */     this.indexInMap = p_setIndexInMap_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   private void fixTransparentColor(int[] p_fixTransparentColor_1_) {
/* 586 */     if (p_fixTransparentColor_1_ != null) {
/*     */       
/* 588 */       long i = 0L;
/* 589 */       long j = 0L;
/* 590 */       long k = 0L;
/* 591 */       long l = 0L;
/*     */       
/* 593 */       for (int i1 = 0; i1 < p_fixTransparentColor_1_.length; i1++) {
/*     */         
/* 595 */         int j1 = p_fixTransparentColor_1_[i1];
/* 596 */         int k1 = j1 >> 24 & 0xFF;
/*     */         
/* 598 */         if (k1 >= 16) {
/*     */           
/* 600 */           int l1 = j1 >> 16 & 0xFF;
/* 601 */           int i2 = j1 >> 8 & 0xFF;
/* 602 */           int j2 = j1 & 0xFF;
/* 603 */           i += l1;
/* 604 */           j += i2;
/* 605 */           k += j2;
/* 606 */           l++;
/*     */         } 
/*     */       } 
/*     */       
/* 610 */       if (l > 0L) {
/*     */         
/* 612 */         int l2 = (int)(i / l);
/* 613 */         int i3 = (int)(j / l);
/* 614 */         int j3 = (int)(k / l);
/* 615 */         int k3 = l2 << 16 | i3 << 8 | j3;
/*     */         
/* 617 */         for (int l3 = 0; l3 < p_fixTransparentColor_1_.length; l3++) {
/*     */           
/* 619 */           int i4 = p_fixTransparentColor_1_[l3];
/* 620 */           int k2 = i4 >> 24 & 0xFF;
/*     */           
/* 622 */           if (k2 <= 16)
/*     */           {
/* 624 */             p_fixTransparentColor_1_[l3] = k3;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSpriteU16(float p_getSpriteU16_1_) {
/* 633 */     float f = this.maxU - this.minU;
/* 634 */     return ((p_getSpriteU16_1_ - this.minU) / f * 16.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getSpriteV16(float p_getSpriteV16_1_) {
/* 639 */     float f = this.maxV - this.minV;
/* 640 */     return ((p_getSpriteV16_1_ - this.minV) / f * 16.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public void bindSpriteTexture() {
/* 645 */     if (this.glSpriteTextureId < 0) {
/*     */       
/* 647 */       this.glSpriteTextureId = TextureUtil.glGenTextures();
/* 648 */       TextureUtil.allocateTextureImpl(this.glSpriteTextureId, this.mipmapLevels, this.width, this.height);
/* 649 */       TextureUtils.applyAnisotropicLevel();
/*     */     } 
/*     */     
/* 652 */     TextureUtils.bindTexture(this.glSpriteTextureId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteSpriteTexture() {
/* 657 */     if (this.glSpriteTextureId >= 0) {
/*     */       
/* 659 */       TextureUtil.deleteTexture(this.glSpriteTextureId);
/* 660 */       this.glSpriteTextureId = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float toSingleU(float p_toSingleU_1_) {
/* 666 */     p_toSingleU_1_ -= this.baseU;
/* 667 */     float f = this.sheetWidth / this.width;
/* 668 */     p_toSingleU_1_ *= f;
/* 669 */     return p_toSingleU_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public float toSingleV(float p_toSingleV_1_) {
/* 674 */     p_toSingleV_1_ -= this.baseV;
/* 675 */     float f = this.sheetHeight / this.height;
/* 676 */     p_toSingleV_1_ *= f;
/* 677 */     return p_toSingleV_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<int[][]> getFramesTextureData() {
/* 682 */     List<int[][]> list = (List)new ArrayList<>();
/* 683 */     list.addAll(this.framesTextureData);
/* 684 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public AnimationMetadataSection getAnimationMetadata() {
/* 689 */     return this.animationMetadata;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAnimationMetadata(AnimationMetadataSection p_setAnimationMetadata_1_) {
/* 694 */     this.animationMetadata = p_setAnimationMetadata_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadShadersSprites() {
/* 699 */     if (Shaders.configNormalMap) {
/*     */       
/* 701 */       String s = String.valueOf(this.iconName) + "_n";
/* 702 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/* 703 */       resourcelocation = Config.getTextureMap().completeResourceLocation(resourcelocation);
/*     */       
/* 705 */       if (Config.hasResource(resourcelocation)) {
/*     */         
/* 707 */         this.spriteNormal = new TextureAtlasSprite(s);
/* 708 */         this.spriteNormal.isShadersSprite = true;
/* 709 */         this.spriteNormal.copyFrom(this);
/* 710 */         Config.getTextureMap().generateMipmaps(Config.getResourceManager(), this.spriteNormal);
/*     */       } 
/*     */     } 
/*     */     
/* 714 */     if (Shaders.configSpecularMap) {
/*     */       
/* 716 */       String s1 = String.valueOf(this.iconName) + "_s";
/* 717 */       ResourceLocation resourcelocation1 = new ResourceLocation(s1);
/* 718 */       resourcelocation1 = Config.getTextureMap().completeResourceLocation(resourcelocation1);
/*     */       
/* 720 */       if (Config.hasResource(resourcelocation1)) {
/*     */         
/* 722 */         this.spriteSpecular = new TextureAtlasSprite(s1);
/* 723 */         this.spriteSpecular.isShadersSprite = true;
/* 724 */         this.spriteSpecular.copyFrom(this);
/* 725 */         Config.getTextureMap().generateMipmaps(Config.getResourceManager(), this.spriteSpecular);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\texture\TextureAtlasSprite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */