/*     */ package net.minecraft.client.renderer.texture;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.IntBuffer;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import optifine.Config;
/*     */ import optifine.Mipmaps;
/*     */ import optifine.Reflector;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class TextureUtil
/*     */ {
/*  25 */   private static final Logger LOGGER = LogManager.getLogger();
/*  26 */   private static final IntBuffer DATA_BUFFER = GLAllocation.createDirectIntBuffer(4194304);
/*  27 */   public static final DynamicTexture MISSING_TEXTURE = new DynamicTexture(16, 16);
/*  28 */   public static final int[] MISSING_TEXTURE_DATA = MISSING_TEXTURE.getTextureData();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static float getColorGamma(int p_188543_0_) {
/*  34 */     return COLOR_GAMMAS[p_188543_0_ & 0xFF];
/*     */   }
/*     */ 
/*     */   
/*     */   public static int glGenTextures() {
/*  39 */     return GlStateManager.generateTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void deleteTexture(int textureId) {
/*  44 */     GlStateManager.deleteTexture(textureId);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int uploadTextureImage(int textureId, BufferedImage texture) {
/*  49 */     return uploadTextureImageAllocate(textureId, texture, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void uploadTexture(int textureId, int[] p_110988_1_, int p_110988_2_, int p_110988_3_) {
/*  54 */     bindTexture(textureId);
/*  55 */     uploadTextureSub(0, p_110988_1_, p_110988_2_, p_110988_3_, 0, 0, false, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] generateMipmapData(int p_147949_0_, int p_147949_1_, int[][] p_147949_2_) {
/*  60 */     int[][] aint = new int[p_147949_0_ + 1][];
/*  61 */     aint[0] = p_147949_2_[0];
/*     */     
/*  63 */     if (p_147949_0_ > 0) {
/*     */       
/*  65 */       boolean flag = false;
/*     */       
/*  67 */       for (int i = 0; i < p_147949_2_.length; i++) {
/*     */         
/*  69 */         if (p_147949_2_[0][i] >> 24 == 0) {
/*     */           
/*  71 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  76 */       for (int l1 = 1; l1 <= p_147949_0_; l1++) {
/*     */         
/*  78 */         if (p_147949_2_[l1] != null) {
/*     */           
/*  80 */           aint[l1] = p_147949_2_[l1];
/*     */         }
/*     */         else {
/*     */           
/*  84 */           int[] aint1 = aint[l1 - 1];
/*  85 */           int[] aint2 = new int[aint1.length >> 2];
/*  86 */           int j = p_147949_1_ >> l1;
/*  87 */           int k = aint2.length / j;
/*  88 */           int l = j << 1;
/*     */           
/*  90 */           for (int i1 = 0; i1 < j; i1++) {
/*     */             
/*  92 */             for (int j1 = 0; j1 < k; j1++) {
/*     */               
/*  94 */               int k1 = 2 * (i1 + j1 * l);
/*  95 */               aint2[i1 + j1 * j] = blendColors(aint1[k1 + 0], aint1[k1 + 1], aint1[k1 + 0 + l], aint1[k1 + 1 + l], flag);
/*     */             } 
/*     */           } 
/*     */           
/*  99 */           aint[l1] = aint2;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   private static int blendColors(int p_147943_0_, int p_147943_1_, int p_147943_2_, int p_147943_3_, boolean p_147943_4_) {
/* 109 */     return Mipmaps.alphaBlend(p_147943_0_, p_147943_1_, p_147943_2_, p_147943_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int blendColorComponent(int p_147944_0_, int p_147944_1_, int p_147944_2_, int p_147944_3_, int p_147944_4_) {
/* 114 */     float f = getColorGamma(p_147944_0_ >> p_147944_4_);
/* 115 */     float f1 = getColorGamma(p_147944_1_ >> p_147944_4_);
/* 116 */     float f2 = getColorGamma(p_147944_2_ >> p_147944_4_);
/* 117 */     float f3 = getColorGamma(p_147944_3_ >> p_147944_4_);
/* 118 */     float f4 = (float)(float)Math.pow((f + f1 + f2 + f3) * 0.25D, 0.45454545454545453D);
/* 119 */     return (int)(f4 * 255.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void uploadTextureMipmap(int[][] p_147955_0_, int p_147955_1_, int p_147955_2_, int p_147955_3_, int p_147955_4_, boolean p_147955_5_, boolean p_147955_6_) {
/* 124 */     for (int i = 0; i < p_147955_0_.length; i++) {
/*     */       
/* 126 */       int[] aint = p_147955_0_[i];
/* 127 */       uploadTextureSub(i, aint, p_147955_1_ >> i, p_147955_2_ >> i, p_147955_3_ >> i, p_147955_4_ >> i, p_147955_5_, p_147955_6_, (p_147955_0_.length > 1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void uploadTextureSub(int p_147947_0_, int[] p_147947_1_, int p_147947_2_, int p_147947_3_, int p_147947_4_, int p_147947_5_, boolean p_147947_6_, boolean p_147947_7_, boolean p_147947_8_) {
/* 133 */     int i = 4194304 / p_147947_2_;
/* 134 */     setTextureBlurMipmap(p_147947_6_, p_147947_8_);
/* 135 */     setTextureClamped(p_147947_7_);
/*     */ 
/*     */     
/* 138 */     for (int k = 0; k < p_147947_2_ * p_147947_3_; k += p_147947_2_ * j) {
/*     */       
/* 140 */       int l = k / p_147947_2_;
/* 141 */       int j = Math.min(i, p_147947_3_ - l);
/* 142 */       int i1 = p_147947_2_ * j;
/* 143 */       copyToBufferPos(p_147947_1_, k, i1);
/* 144 */       GlStateManager.glTexSubImage2D(3553, p_147947_0_, p_147947_4_, p_147947_5_ + l, p_147947_2_, j, 32993, 33639, DATA_BUFFER);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int uploadTextureImageAllocate(int textureId, BufferedImage texture, boolean blur, boolean clamp) {
/* 150 */     allocateTexture(textureId, texture.getWidth(), texture.getHeight());
/* 151 */     return uploadTextureImageSub(textureId, texture, 0, 0, blur, clamp);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void allocateTexture(int textureId, int width, int height) {
/* 156 */     allocateTextureImpl(textureId, 0, width, height);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void allocateTextureImpl(int glTextureId, int mipmapLevels, int width, int height) {
/* 161 */     Object<TextureUtil> object = (Object<TextureUtil>)TextureUtil.class;
/*     */     
/* 163 */     if (Reflector.SplashScreen.exists())
/*     */     {
/* 165 */       object = (Object<TextureUtil>)Reflector.SplashScreen.getTargetClass();
/*     */     }
/*     */     
/* 168 */     synchronized (object) {
/*     */       
/* 170 */       deleteTexture(glTextureId);
/* 171 */       bindTexture(glTextureId);
/*     */     } 
/*     */     
/* 174 */     if (mipmapLevels >= 0) {
/*     */       
/* 176 */       GlStateManager.glTexParameteri(3553, 33085, mipmapLevels);
/* 177 */       GlStateManager.glTexParameteri(3553, 33082, 0);
/* 178 */       GlStateManager.glTexParameteri(3553, 33083, mipmapLevels);
/* 179 */       GlStateManager.glTexParameterf(3553, 34049, 0.0F);
/*     */     } 
/*     */     
/* 182 */     for (int i = 0; i <= mipmapLevels; i++)
/*     */     {
/* 184 */       GlStateManager.glTexImage2D(3553, i, 6408, width >> i, height >> i, 0, 32993, 33639, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static int uploadTextureImageSub(int textureId, BufferedImage p_110995_1_, int p_110995_2_, int p_110995_3_, boolean p_110995_4_, boolean p_110995_5_) {
/* 190 */     bindTexture(textureId);
/* 191 */     uploadTextureImageSubImpl(p_110995_1_, p_110995_2_, p_110995_3_, p_110995_4_, p_110995_5_);
/* 192 */     return textureId;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void uploadTextureImageSubImpl(BufferedImage p_110993_0_, int p_110993_1_, int p_110993_2_, boolean p_110993_3_, boolean p_110993_4_) {
/* 197 */     int i = p_110993_0_.getWidth();
/* 198 */     int j = p_110993_0_.getHeight();
/* 199 */     int k = 4194304 / i;
/* 200 */     int[] aint = new int[k * i];
/* 201 */     setTextureBlurred(p_110993_3_);
/* 202 */     setTextureClamped(p_110993_4_);
/*     */     
/* 204 */     for (int l = 0; l < i * j; l += i * k) {
/*     */       
/* 206 */       int i1 = l / i;
/* 207 */       int j1 = Math.min(k, j - i1);
/* 208 */       int k1 = i * j1;
/* 209 */       p_110993_0_.getRGB(0, i1, i, j1, aint, 0, i);
/* 210 */       copyToBuffer(aint, k1);
/* 211 */       GlStateManager.glTexSubImage2D(3553, 0, p_110993_1_, p_110993_2_ + i1, i, j1, 32993, 33639, DATA_BUFFER);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setTextureClamped(boolean p_110997_0_) {
/* 217 */     if (p_110997_0_) {
/*     */       
/* 219 */       GlStateManager.glTexParameteri(3553, 10242, 33071);
/* 220 */       GlStateManager.glTexParameteri(3553, 10243, 33071);
/*     */     }
/*     */     else {
/*     */       
/* 224 */       GlStateManager.glTexParameteri(3553, 10242, 10497);
/* 225 */       GlStateManager.glTexParameteri(3553, 10243, 10497);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void setTextureBlurred(boolean p_147951_0_) {
/* 231 */     setTextureBlurMipmap(p_147951_0_, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setTextureBlurMipmap(boolean p_147954_0_, boolean p_147954_1_) {
/* 236 */     if (p_147954_0_) {
/*     */       
/* 238 */       GlStateManager.glTexParameteri(3553, 10241, p_147954_1_ ? 9987 : 9729);
/* 239 */       GlStateManager.glTexParameteri(3553, 10240, 9729);
/*     */     }
/*     */     else {
/*     */       
/* 243 */       int i = Config.getMipmapType();
/* 244 */       GlStateManager.glTexParameteri(3553, 10241, p_147954_1_ ? i : 9728);
/* 245 */       GlStateManager.glTexParameteri(3553, 10240, 9728);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void copyToBuffer(int[] p_110990_0_, int p_110990_1_) {
/* 251 */     copyToBufferPos(p_110990_0_, 0, p_110990_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static void copyToBufferPos(int[] p_110994_0_, int p_110994_1_, int p_110994_2_) {
/* 256 */     int[] aint = p_110994_0_;
/*     */     
/* 258 */     if ((Minecraft.getMinecraft()).gameSettings.anaglyph)
/*     */     {
/* 260 */       aint = updateAnaglyph(p_110994_0_);
/*     */     }
/*     */     
/* 263 */     DATA_BUFFER.clear();
/* 264 */     DATA_BUFFER.put(aint, p_110994_1_, p_110994_2_);
/* 265 */     DATA_BUFFER.position(0).limit(p_110994_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   static void bindTexture(int p_94277_0_) {
/* 270 */     GlStateManager.bindTexture(p_94277_0_);
/*     */   }
/*     */   
/*     */   public static int[] readImageData(IResourceManager resourceManager, ResourceLocation imageLocation) throws IOException {
/*     */     Object i;
/* 275 */     IResource iresource = null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 280 */     try { iresource = resourceManager.getResource(imageLocation);
/* 281 */       BufferedImage bufferedimage = readBufferedImage(iresource.getInputStream());
/*     */       
/* 283 */       if (bufferedimage != null)
/*     */       {
/* 285 */         int j = bufferedimage.getWidth();
/* 286 */         int i1 = bufferedimage.getHeight();
/* 287 */         int[] aint1 = new int[j * i1];
/* 288 */         bufferedimage.getRGB(0, 0, j, i1, aint1, 0, j);
/* 289 */         int[] aint = aint1;
/* 290 */         return aint;
/*     */       
/*     */       }
/*     */        }
/*     */     
/*     */     finally
/*     */     
/* 297 */     { IOUtils.closeQuietly((Closeable)iresource); }  IOUtils.closeQuietly((Closeable)iresource);
/*     */ 
/*     */     
/* 300 */     return (int[])i;
/*     */   }
/*     */   
/*     */   public static BufferedImage readBufferedImage(InputStream imageStream) throws IOException {
/*     */     BufferedImage bufferedimage;
/* 305 */     if (imageStream == null)
/*     */     {
/* 307 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 315 */       bufferedimage = ImageIO.read(imageStream);
/*     */     }
/*     */     finally {
/*     */       
/* 319 */       IOUtils.closeQuietly(imageStream);
/*     */     } 
/*     */     
/* 322 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int[] updateAnaglyph(int[] p_110985_0_) {
/* 328 */     int[] aint = new int[p_110985_0_.length];
/*     */     
/* 330 */     for (int i = 0; i < p_110985_0_.length; i++)
/*     */     {
/* 332 */       aint[i] = anaglyphColor(p_110985_0_[i]);
/*     */     }
/*     */     
/* 335 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int anaglyphColor(int p_177054_0_) {
/* 340 */     int i = p_177054_0_ >> 24 & 0xFF;
/* 341 */     int j = p_177054_0_ >> 16 & 0xFF;
/* 342 */     int k = p_177054_0_ >> 8 & 0xFF;
/* 343 */     int l = p_177054_0_ & 0xFF;
/* 344 */     int i1 = (j * 30 + k * 59 + l * 11) / 100;
/* 345 */     int j1 = (j * 30 + k * 70) / 100;
/* 346 */     int k1 = (j * 30 + l * 70) / 100;
/* 347 */     return i << 24 | i1 << 16 | j1 << 8 | k1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void processPixelValues(int[] p_147953_0_, int p_147953_1_, int p_147953_2_) {
/* 352 */     int[] aint = new int[p_147953_1_];
/* 353 */     int i = p_147953_2_ / 2;
/*     */     
/* 355 */     for (int j = 0; j < i; j++) {
/*     */       
/* 357 */       System.arraycopy(p_147953_0_, j * p_147953_1_, aint, 0, p_147953_1_);
/* 358 */       System.arraycopy(p_147953_0_, (p_147953_2_ - 1 - j) * p_147953_1_, p_147953_0_, j * p_147953_1_, p_147953_1_);
/* 359 */       System.arraycopy(aint, 0, p_147953_0_, (p_147953_2_ - 1 - j) * p_147953_1_, p_147953_1_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 365 */     int i = -16777216;
/* 366 */     int j = -524040;
/* 367 */     int[] aint = { -524040, -524040, -524040, -524040, -524040, -524040, -524040, -524040 };
/* 368 */     int[] aint1 = { -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216, -16777216 };
/* 369 */     int k = aint.length;
/*     */     
/* 371 */     for (int l = 0; l < 16; l++) {
/*     */       
/* 373 */       System.arraycopy((l < k) ? aint : aint1, 0, MISSING_TEXTURE_DATA, 16 * l, k);
/* 374 */       System.arraycopy((l < k) ? aint1 : aint, 0, MISSING_TEXTURE_DATA, 16 * l + k, k);
/*     */     } 
/*     */     
/* 377 */     MISSING_TEXTURE.updateDynamicTexture();
/* 378 */   } private static final float[] COLOR_GAMMAS = new float[256];
/*     */   static {
/* 380 */     for (int i1 = 0; i1 < COLOR_GAMMAS.length; i1++)
/*     */     {
/* 382 */       COLOR_GAMMAS[i1] = (float)Math.pow((i1 / 255.0F), 2.2D); } 
/*     */   }
/*     */   
/* 385 */   private static final int[] MIPMAP_BUFFER = new int[4];
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\texture\TextureUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */