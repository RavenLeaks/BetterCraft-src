/*     */ package shadersmod.client;
/*     */ 
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.imageio.ImageIO;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.AbstractTexture;
/*     */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.LayeredTexture;
/*     */ import net.minecraft.client.renderer.texture.Stitcher;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.renderer.texture.TextureUtil;
/*     */ import net.minecraft.client.resources.IResource;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import optifine.Config;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import shadersmod.common.SMCLog;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShadersTex
/*     */ {
/*     */   public static final int initialBufferSize = 1048576;
/*  37 */   public static ByteBuffer byteBuffer = BufferUtils.createByteBuffer(4194304);
/*  38 */   public static IntBuffer intBuffer = byteBuffer.asIntBuffer();
/*  39 */   public static int[] intArray = new int[1048576];
/*     */   public static final int defBaseTexColor = 0;
/*     */   public static final int defNormTexColor = -8421377;
/*     */   public static final int defSpecTexColor = 0;
/*  43 */   public static Map<Integer, MultiTexID> multiTexMap = new HashMap<>();
/*  44 */   public static TextureMap updatingTextureMap = null;
/*  45 */   public static TextureAtlasSprite updatingSprite = null;
/*  46 */   public static MultiTexID updatingTex = null;
/*  47 */   public static MultiTexID boundTex = null;
/*  48 */   public static int updatingPage = 0;
/*  49 */   public static String iconName = null;
/*  50 */   public static IResourceManager resManager = null;
/*  51 */   static ResourceLocation resLocation = null;
/*  52 */   static int imageSize = 0;
/*     */ 
/*     */   
/*     */   public static IntBuffer getIntBuffer(int size) {
/*  56 */     if (intBuffer.capacity() < size) {
/*     */       
/*  58 */       int i = roundUpPOT(size);
/*  59 */       byteBuffer = BufferUtils.createByteBuffer(i * 4);
/*  60 */       intBuffer = byteBuffer.asIntBuffer();
/*     */     } 
/*     */     
/*  63 */     return intBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] getIntArray(int size) {
/*  68 */     if (intArray == null)
/*     */     {
/*  70 */       intArray = new int[1048576];
/*     */     }
/*     */     
/*  73 */     if (intArray.length < size)
/*     */     {
/*  75 */       intArray = new int[roundUpPOT(size)];
/*     */     }
/*     */     
/*  78 */     return intArray;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int roundUpPOT(int x) {
/*  83 */     int i = x - 1;
/*  84 */     i |= i >> 1;
/*  85 */     i |= i >> 2;
/*  86 */     i |= i >> 4;
/*  87 */     i |= i >> 8;
/*  88 */     i |= i >> 16;
/*  89 */     return i + 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int log2(int x) {
/*  94 */     int i = 0;
/*     */     
/*  96 */     if ((x & 0xFFFF0000) != 0) {
/*     */       
/*  98 */       i += 16;
/*  99 */       x >>= 16;
/*     */     } 
/*     */     
/* 102 */     if ((x & 0xFF00) != 0) {
/*     */       
/* 104 */       i += 8;
/* 105 */       x >>= 8;
/*     */     } 
/*     */     
/* 108 */     if ((x & 0xF0) != 0) {
/*     */       
/* 110 */       i += 4;
/* 111 */       x >>= 4;
/*     */     } 
/*     */     
/* 114 */     if ((x & 0x6) != 0) {
/*     */       
/* 116 */       i += 2;
/* 117 */       x >>= 2;
/*     */     } 
/*     */     
/* 120 */     if ((x & 0x2) != 0)
/*     */     {
/* 122 */       i++;
/*     */     }
/*     */     
/* 125 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IntBuffer fillIntBuffer(int size, int value) {
/* 130 */     int[] aint = getIntArray(size);
/* 131 */     IntBuffer intbuffer = getIntBuffer(size);
/* 132 */     Arrays.fill(intArray, 0, size, value);
/* 133 */     intBuffer.put(intArray, 0, size);
/* 134 */     return intBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] createAIntImage(int size) {
/* 139 */     int[] aint = new int[size * 3];
/* 140 */     Arrays.fill(aint, 0, size, 0);
/* 141 */     Arrays.fill(aint, size, size * 2, -8421377);
/* 142 */     Arrays.fill(aint, size * 2, size * 3, 0);
/* 143 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] createAIntImage(int size, int color) {
/* 148 */     int[] aint = new int[size * 3];
/* 149 */     Arrays.fill(aint, 0, size, color);
/* 150 */     Arrays.fill(aint, size, size * 2, -8421377);
/* 151 */     Arrays.fill(aint, size * 2, size * 3, 0);
/* 152 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static MultiTexID getMultiTexID(AbstractTexture tex) {
/* 157 */     MultiTexID multitexid = tex.multiTex;
/*     */     
/* 159 */     if (multitexid == null) {
/*     */       
/* 161 */       int i = tex.getGlTextureId();
/* 162 */       multitexid = multiTexMap.get(Integer.valueOf(i));
/*     */       
/* 164 */       if (multitexid == null) {
/*     */         
/* 166 */         multitexid = new MultiTexID(i, GL11.glGenTextures(), GL11.glGenTextures());
/* 167 */         multiTexMap.put(Integer.valueOf(i), multitexid);
/*     */       } 
/*     */       
/* 170 */       tex.multiTex = multitexid;
/*     */     } 
/*     */     
/* 173 */     return multitexid;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void deleteTextures(AbstractTexture atex, int texid) {
/* 178 */     MultiTexID multitexid = atex.multiTex;
/*     */     
/* 180 */     if (multitexid != null) {
/*     */       
/* 182 */       atex.multiTex = null;
/* 183 */       multiTexMap.remove(Integer.valueOf(multitexid.base));
/* 184 */       GlStateManager.deleteTexture(multitexid.norm);
/* 185 */       GlStateManager.deleteTexture(multitexid.spec);
/*     */       
/* 187 */       if (multitexid.base != texid) {
/*     */         
/* 189 */         SMCLog.warning("Error : MultiTexID.base mismatch: " + multitexid.base + ", texid: " + texid);
/* 190 */         GlStateManager.deleteTexture(multitexid.base);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindNSTextures(int normTex, int specTex) {
/* 197 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/*     */       
/* 199 */       GlStateManager.setActiveTexture(33986);
/* 200 */       GlStateManager.bindTexture(normTex);
/* 201 */       GlStateManager.setActiveTexture(33987);
/* 202 */       GlStateManager.bindTexture(specTex);
/* 203 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindNSTextures(MultiTexID multiTex) {
/* 209 */     bindNSTextures(multiTex.norm, multiTex.spec);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTextures(int baseTex, int normTex, int specTex) {
/* 214 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/*     */       
/* 216 */       GlStateManager.setActiveTexture(33986);
/* 217 */       GlStateManager.bindTexture(normTex);
/* 218 */       GlStateManager.setActiveTexture(33987);
/* 219 */       GlStateManager.bindTexture(specTex);
/* 220 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */     
/* 223 */     GlStateManager.bindTexture(baseTex);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTextures(MultiTexID multiTex) {
/* 228 */     boundTex = multiTex;
/*     */     
/* 230 */     if (Shaders.isRenderingWorld && GlStateManager.getActiveTextureUnit() == 33984) {
/*     */       
/* 232 */       if (Shaders.configNormalMap) {
/*     */         
/* 234 */         GlStateManager.setActiveTexture(33986);
/* 235 */         GlStateManager.bindTexture(multiTex.norm);
/*     */       } 
/*     */       
/* 238 */       if (Shaders.configSpecularMap) {
/*     */         
/* 240 */         GlStateManager.setActiveTexture(33987);
/* 241 */         GlStateManager.bindTexture(multiTex.spec);
/*     */       } 
/*     */       
/* 244 */       GlStateManager.setActiveTexture(33984);
/*     */     } 
/*     */     
/* 247 */     GlStateManager.bindTexture(multiTex.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTexture(ITextureObject tex) {
/* 252 */     int i = tex.getGlTextureId();
/*     */     
/* 254 */     if (tex instanceof TextureMap) {
/*     */       
/* 256 */       Shaders.atlasSizeX = ((TextureMap)tex).atlasWidth;
/* 257 */       Shaders.atlasSizeY = ((TextureMap)tex).atlasHeight;
/* 258 */       bindTextures(tex.getMultiTexID());
/*     */     }
/*     */     else {
/*     */       
/* 262 */       Shaders.atlasSizeX = 0;
/* 263 */       Shaders.atlasSizeY = 0;
/* 264 */       bindTextures(tex.getMultiTexID());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTextureMapForUpdateAndRender(TextureManager tm, ResourceLocation resLoc) {
/* 270 */     TextureMap texturemap = (TextureMap)tm.getTexture(resLoc);
/* 271 */     Shaders.atlasSizeX = texturemap.atlasWidth;
/* 272 */     Shaders.atlasSizeY = texturemap.atlasHeight;
/* 273 */     bindTextures(updatingTex = texturemap.getMultiTexID());
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTextures(int baseTex) {
/* 278 */     MultiTexID multitexid = multiTexMap.get(Integer.valueOf(baseTex));
/* 279 */     bindTextures(multitexid);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void initDynamicTexture(int texID, int width, int height, DynamicTexture tex) {
/* 284 */     MultiTexID multitexid = tex.getMultiTexID();
/* 285 */     int[] aint = tex.getTextureData();
/* 286 */     int i = width * height;
/* 287 */     Arrays.fill(aint, i, i * 2, -8421377);
/* 288 */     Arrays.fill(aint, i * 2, i * 3, 0);
/* 289 */     TextureUtil.allocateTexture(multitexid.base, width, height);
/* 290 */     TextureUtil.setTextureBlurMipmap(false, false);
/* 291 */     TextureUtil.setTextureClamped(false);
/* 292 */     TextureUtil.allocateTexture(multitexid.norm, width, height);
/* 293 */     TextureUtil.setTextureBlurMipmap(false, false);
/* 294 */     TextureUtil.setTextureClamped(false);
/* 295 */     TextureUtil.allocateTexture(multitexid.spec, width, height);
/* 296 */     TextureUtil.setTextureBlurMipmap(false, false);
/* 297 */     TextureUtil.setTextureClamped(false);
/* 298 */     GlStateManager.bindTexture(multitexid.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateDynamicTexture(int texID, int[] src, int width, int height, DynamicTexture tex) {
/* 303 */     MultiTexID multitexid = tex.getMultiTexID();
/* 304 */     GlStateManager.bindTexture(multitexid.base);
/* 305 */     updateDynTexSubImage1(src, width, height, 0, 0, 0);
/* 306 */     GlStateManager.bindTexture(multitexid.norm);
/* 307 */     updateDynTexSubImage1(src, width, height, 0, 0, 1);
/* 308 */     GlStateManager.bindTexture(multitexid.spec);
/* 309 */     updateDynTexSubImage1(src, width, height, 0, 0, 2);
/* 310 */     GlStateManager.bindTexture(multitexid.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateDynTexSubImage1(int[] src, int width, int height, int posX, int posY, int page) {
/* 315 */     int i = width * height;
/* 316 */     IntBuffer intbuffer = getIntBuffer(i);
/* 317 */     intbuffer.clear();
/* 318 */     int j = page * i;
/*     */     
/* 320 */     if (src.length >= j + i) {
/*     */       
/* 322 */       intbuffer.put(src, j, i).position(0).limit(i);
/* 323 */       GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/* 324 */       intbuffer.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static ITextureObject createDefaultTexture() {
/* 330 */     DynamicTexture dynamictexture = new DynamicTexture(1, 1);
/* 331 */     dynamictexture.getTextureData()[0] = -1;
/* 332 */     dynamictexture.updateDynamicTexture();
/* 333 */     return (ITextureObject)dynamictexture;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void allocateTextureMap(int texID, int mipmapLevels, int width, int height, Stitcher stitcher, TextureMap tex) {
/* 338 */     SMCLog.info("allocateTextureMap " + mipmapLevels + " " + width + " " + height + " ");
/* 339 */     updatingTextureMap = tex;
/* 340 */     tex.atlasWidth = width;
/* 341 */     tex.atlasHeight = height;
/* 342 */     MultiTexID multitexid = getMultiTexID((AbstractTexture)tex);
/* 343 */     updatingTex = multitexid;
/* 344 */     TextureUtil.allocateTextureImpl(multitexid.base, mipmapLevels, width, height);
/*     */     
/* 346 */     if (Shaders.configNormalMap)
/*     */     {
/* 348 */       TextureUtil.allocateTextureImpl(multitexid.norm, mipmapLevels, width, height);
/*     */     }
/*     */     
/* 351 */     if (Shaders.configSpecularMap)
/*     */     {
/* 353 */       TextureUtil.allocateTextureImpl(multitexid.spec, mipmapLevels, width, height);
/*     */     }
/*     */     
/* 356 */     GlStateManager.bindTexture(texID);
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureAtlasSprite setSprite(TextureAtlasSprite tas) {
/* 361 */     updatingSprite = tas;
/* 362 */     return tas;
/*     */   }
/*     */ 
/*     */   
/*     */   public static String setIconName(String name) {
/* 367 */     iconName = name;
/* 368 */     return name;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void uploadTexSubForLoadAtlas(int[][] data, int width, int height, int xoffset, int yoffset, boolean linear, boolean clamp) {
/* 373 */     TextureUtil.uploadTextureMipmap(data, width, height, xoffset, yoffset, linear, clamp);
/* 374 */     boolean flag = false;
/*     */     
/* 376 */     if (Shaders.configNormalMap) {
/*     */       
/* 378 */       int[][] aint = readImageAndMipmaps(String.valueOf(iconName) + "_n", width, height, data.length, flag, -8421377);
/* 379 */       GlStateManager.bindTexture(updatingTex.norm);
/* 380 */       TextureUtil.uploadTextureMipmap(aint, width, height, xoffset, yoffset, linear, clamp);
/*     */     } 
/*     */     
/* 383 */     if (Shaders.configSpecularMap) {
/*     */       
/* 385 */       int[][] aint1 = readImageAndMipmaps(String.valueOf(iconName) + "_s", width, height, data.length, flag, 0);
/* 386 */       GlStateManager.bindTexture(updatingTex.spec);
/* 387 */       TextureUtil.uploadTextureMipmap(aint1, width, height, xoffset, yoffset, linear, clamp);
/*     */     } 
/*     */     
/* 390 */     GlStateManager.bindTexture(updatingTex.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] readImageAndMipmaps(String name, int width, int height, int numLevels, boolean border, int defColor) {
/* 395 */     int[][] aint = new int[numLevels][];
/*     */     
/* 397 */     int[] aint1 = new int[width * height];
/* 398 */     boolean flag = false;
/* 399 */     BufferedImage bufferedimage = readImage(updatingTextureMap.completeResourceLocation(new ResourceLocation(name)));
/*     */     
/* 401 */     if (bufferedimage != null) {
/*     */       
/* 403 */       int i = bufferedimage.getWidth();
/* 404 */       int j = bufferedimage.getHeight();
/*     */       
/* 406 */       if (i + (border ? 16 : 0) == width) {
/*     */         
/* 408 */         flag = true;
/* 409 */         bufferedimage.getRGB(0, 0, i, i, aint1, 0, i);
/*     */       } 
/*     */     } 
/*     */     
/* 413 */     if (!flag)
/*     */     {
/* 415 */       Arrays.fill(aint1, defColor);
/*     */     }
/*     */     
/* 418 */     GlStateManager.bindTexture(updatingTex.spec);
/* 419 */     aint = genMipmapsSimple(aint.length - 1, width, aint);
/* 420 */     return aint;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BufferedImage readImage(ResourceLocation resLoc) {
/*     */     try {
/* 427 */       if (!Config.hasResource(resLoc))
/*     */       {
/* 429 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 433 */       InputStream inputstream = Config.getResourceStream(resLoc);
/*     */       
/* 435 */       if (inputstream == null)
/*     */       {
/* 437 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 441 */       BufferedImage bufferedimage = ImageIO.read(inputstream);
/* 442 */       inputstream.close();
/* 443 */       return bufferedimage;
/*     */ 
/*     */     
/*     */     }
/* 447 */     catch (IOException var3) {
/*     */       
/* 449 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] genMipmapsSimple(int maxLevel, int width, int[][] data) {
/* 455 */     for (int i = 1; i <= maxLevel; i++) {
/*     */       
/* 457 */       if (data[i] == null) {
/*     */         
/* 459 */         int j = width >> i;
/* 460 */         int k = j * 2;
/* 461 */         int[] aint = data[i - 1];
/* 462 */         int[] aint1 = data[i] = new int[j * j];
/*     */         
/* 464 */         for (int i1 = 0; i1 < j; i1++) {
/*     */           
/* 466 */           for (int l = 0; l < j; l++) {
/*     */             
/* 468 */             int j1 = i1 * 2 * k + l * 2;
/* 469 */             aint1[i1 * j + l] = blend4Simple(aint[j1], aint[j1 + 1], aint[j1 + k], aint[j1 + k + 1]);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 475 */     return data;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void uploadTexSub(int[][] data, int width, int height, int xoffset, int yoffset, boolean linear, boolean clamp) {
/* 480 */     TextureUtil.uploadTextureMipmap(data, width, height, xoffset, yoffset, linear, clamp);
/*     */     
/* 482 */     if (Shaders.configNormalMap || Shaders.configSpecularMap) {
/*     */       
/* 484 */       if (Shaders.configNormalMap) {
/*     */         
/* 486 */         GlStateManager.bindTexture(updatingTex.norm);
/* 487 */         uploadTexSub1(data, width, height, xoffset, yoffset, 1);
/*     */       } 
/*     */       
/* 490 */       if (Shaders.configSpecularMap) {
/*     */         
/* 492 */         GlStateManager.bindTexture(updatingTex.spec);
/* 493 */         uploadTexSub1(data, width, height, xoffset, yoffset, 2);
/*     */       } 
/*     */       
/* 496 */       GlStateManager.bindTexture(updatingTex.base);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void uploadTexSub1(int[][] src, int width, int height, int posX, int posY, int page) {
/* 502 */     int i = width * height;
/* 503 */     IntBuffer intbuffer = getIntBuffer(i);
/* 504 */     int j = src.length;
/* 505 */     int k = 0;
/* 506 */     int l = width;
/* 507 */     int i1 = height;
/* 508 */     int j1 = posX;
/*     */     
/* 510 */     for (int k1 = posY; l > 0 && i1 > 0 && k < j; k++) {
/*     */       
/* 512 */       int l1 = l * i1;
/* 513 */       int[] aint = src[k];
/* 514 */       intbuffer.clear();
/*     */       
/* 516 */       if (aint.length >= l1 * (page + 1)) {
/*     */         
/* 518 */         intbuffer.put(aint, l1 * page, l1).position(0).limit(l1);
/* 519 */         GL11.glTexSubImage2D(3553, k, j1, k1, l, i1, 32993, 33639, intbuffer);
/*     */       } 
/*     */       
/* 522 */       l >>= 1;
/* 523 */       i1 >>= 1;
/* 524 */       j1 >>= 1;
/* 525 */       k1 >>= 1;
/*     */     } 
/*     */     
/* 528 */     intbuffer.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public static int blend4Alpha(int c0, int c1, int c2, int c3) {
/* 533 */     int k1, i = c0 >>> 24 & 0xFF;
/* 534 */     int j = c1 >>> 24 & 0xFF;
/* 535 */     int k = c2 >>> 24 & 0xFF;
/* 536 */     int l = c3 >>> 24 & 0xFF;
/* 537 */     int i1 = i + j + k + l;
/* 538 */     int j1 = (i1 + 2) / 4;
/*     */ 
/*     */     
/* 541 */     if (i1 != 0) {
/*     */       
/* 543 */       k1 = i1;
/*     */     }
/*     */     else {
/*     */       
/* 547 */       k1 = 4;
/* 548 */       i = 1;
/* 549 */       j = 1;
/* 550 */       k = 1;
/* 551 */       l = 1;
/*     */     } 
/*     */     
/* 554 */     int l1 = (k1 + 1) / 2;
/* 555 */     int i2 = j1 << 24 | ((c0 >>> 16 & 0xFF) * i + (c1 >>> 16 & 0xFF) * j + (c2 >>> 16 & 0xFF) * k + (c3 >>> 16 & 0xFF) * l + l1) / k1 << 16 | ((c0 >>> 8 & 0xFF) * i + (c1 >>> 8 & 0xFF) * j + (c2 >>> 8 & 0xFF) * k + (c3 >>> 8 & 0xFF) * l + l1) / k1 << 8 | ((c0 >>> 0 & 0xFF) * i + (c1 >>> 0 & 0xFF) * j + (c2 >>> 0 & 0xFF) * k + (c3 >>> 0 & 0xFF) * l + l1) / k1 << 0;
/* 556 */     return i2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int blend4Simple(int c0, int c1, int c2, int c3) {
/* 561 */     int i = ((c0 >>> 24 & 0xFF) + (c1 >>> 24 & 0xFF) + (c2 >>> 24 & 0xFF) + (c3 >>> 24 & 0xFF) + 2) / 4 << 24 | ((c0 >>> 16 & 0xFF) + (c1 >>> 16 & 0xFF) + (c2 >>> 16 & 0xFF) + (c3 >>> 16 & 0xFF) + 2) / 4 << 16 | ((c0 >>> 8 & 0xFF) + (c1 >>> 8 & 0xFF) + (c2 >>> 8 & 0xFF) + (c3 >>> 8 & 0xFF) + 2) / 4 << 8 | ((c0 >>> 0 & 0xFF) + (c1 >>> 0 & 0xFF) + (c2 >>> 0 & 0xFF) + (c3 >>> 0 & 0xFF) + 2) / 4 << 0;
/* 562 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void genMipmapAlpha(int[] aint, int offset, int width, int height) {
/* 567 */     Math.min(width, height);
/* 568 */     int o2 = offset;
/* 569 */     int w2 = width;
/* 570 */     int h2 = height;
/* 571 */     int o1 = 0;
/* 572 */     int w1 = 0;
/* 573 */     int h1 = 0;
/*     */     
/*     */     int i;
/* 576 */     for (i = 0; w2 > 1 && h2 > 1; o2 = o1) {
/*     */       
/* 578 */       o1 = o2 + w2 * h2;
/* 579 */       w1 = w2 / 2;
/* 580 */       h1 = h2 / 2;
/*     */       
/* 582 */       for (int l1 = 0; l1 < h1; l1++) {
/*     */         
/* 584 */         int i2 = o1 + l1 * w1;
/* 585 */         int j2 = o2 + l1 * 2 * w2;
/*     */         
/* 587 */         for (int k2 = 0; k2 < w1; k2++)
/*     */         {
/* 589 */           aint[i2 + k2] = blend4Alpha(aint[j2 + k2 * 2], aint[j2 + k2 * 2 + 1], aint[j2 + w2 + k2 * 2], aint[j2 + w2 + k2 * 2 + 1]);
/*     */         }
/*     */       } 
/*     */       
/* 593 */       i++;
/* 594 */       w2 = w1;
/* 595 */       h2 = h1;
/*     */     } 
/*     */     
/* 598 */     while (i > 0) {
/*     */       
/* 600 */       i--;
/* 601 */       w2 = width >> i;
/* 602 */       h2 = height >> i;
/* 603 */       o2 = o1 - w2 * h2;
/* 604 */       int l2 = o2;
/*     */       
/* 606 */       for (int i3 = 0; i3 < h2; i3++) {
/*     */         
/* 608 */         for (int j3 = 0; j3 < w2; j3++) {
/*     */           
/* 610 */           if (aint[l2] == 0)
/*     */           {
/* 612 */             aint[l2] = aint[o1 + i3 / 2 * w1 + j3 / 2] & 0xFFFFFF;
/*     */           }
/*     */           
/* 615 */           l2++;
/*     */         } 
/*     */       } 
/*     */       
/* 619 */       o1 = o2;
/* 620 */       w1 = w2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void genMipmapSimple(int[] aint, int offset, int width, int height) {
/* 626 */     Math.min(width, height);
/* 627 */     int o2 = offset;
/* 628 */     int w2 = width;
/* 629 */     int h2 = height;
/* 630 */     int o1 = 0;
/* 631 */     int w1 = 0;
/* 632 */     int h1 = 0;
/*     */     
/*     */     int i;
/* 635 */     for (i = 0; w2 > 1 && h2 > 1; o2 = o1) {
/*     */       
/* 637 */       o1 = o2 + w2 * h2;
/* 638 */       w1 = w2 / 2;
/* 639 */       h1 = h2 / 2;
/*     */       
/* 641 */       for (int l1 = 0; l1 < h1; l1++) {
/*     */         
/* 643 */         int i2 = o1 + l1 * w1;
/* 644 */         int j2 = o2 + l1 * 2 * w2;
/*     */         
/* 646 */         for (int k2 = 0; k2 < w1; k2++)
/*     */         {
/* 648 */           aint[i2 + k2] = blend4Simple(aint[j2 + k2 * 2], aint[j2 + k2 * 2 + 1], aint[j2 + w2 + k2 * 2], aint[j2 + w2 + k2 * 2 + 1]);
/*     */         }
/*     */       } 
/*     */       
/* 652 */       i++;
/* 653 */       w2 = w1;
/* 654 */       h2 = h1;
/*     */     } 
/*     */     
/* 657 */     while (i > 0) {
/*     */       
/* 659 */       i--;
/* 660 */       w2 = width >> i;
/* 661 */       h2 = height >> i;
/* 662 */       o2 = o1 - w2 * h2;
/* 663 */       int l2 = o2;
/*     */       
/* 665 */       for (int i3 = 0; i3 < h2; i3++) {
/*     */         
/* 667 */         for (int j3 = 0; j3 < w2; j3++) {
/*     */           
/* 669 */           if (aint[l2] == 0)
/*     */           {
/* 671 */             aint[l2] = aint[o1 + i3 / 2 * w1 + j3 / 2] & 0xFFFFFF;
/*     */           }
/*     */           
/* 674 */           l2++;
/*     */         } 
/*     */       } 
/*     */       
/* 678 */       o1 = o2;
/* 679 */       w1 = w2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isSemiTransparent(int[] aint, int width, int height) {
/* 685 */     int i = width * height;
/*     */     
/* 687 */     if (aint[0] >>> 24 == 255 && aint[i - 1] == 0)
/*     */     {
/* 689 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 693 */     for (int j = 0; j < i; j++) {
/*     */       
/* 695 */       int k = aint[j] >>> 24;
/*     */       
/* 697 */       if (k != 0 && k != 255)
/*     */       {
/* 699 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 703 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void updateSubTex1(int[] src, int width, int height, int posX, int posY) {
/* 709 */     int i = 0;
/* 710 */     int j = width;
/* 711 */     int k = height;
/* 712 */     int l = posX;
/*     */     
/* 714 */     for (int i1 = posY; j > 0 && k > 0; i1 /= 2) {
/*     */       
/* 716 */       GL11.glCopyTexSubImage2D(3553, i, l, i1, 0, 0, j, k);
/* 717 */       i++;
/* 718 */       j /= 2;
/* 719 */       k /= 2;
/* 720 */       l /= 2;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void setupTexture(MultiTexID multiTex, int[] src, int width, int height, boolean linear, boolean clamp) {
/* 726 */     int i = linear ? 9729 : 9728;
/* 727 */     int j = clamp ? 10496 : 10497;
/* 728 */     int k = width * height;
/* 729 */     IntBuffer intbuffer = getIntBuffer(k);
/* 730 */     intbuffer.clear();
/* 731 */     intbuffer.put(src, 0, k).position(0).limit(k);
/* 732 */     GlStateManager.bindTexture(multiTex.base);
/* 733 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intbuffer);
/* 734 */     GL11.glTexParameteri(3553, 10241, i);
/* 735 */     GL11.glTexParameteri(3553, 10240, i);
/* 736 */     GL11.glTexParameteri(3553, 10242, j);
/* 737 */     GL11.glTexParameteri(3553, 10243, j);
/* 738 */     intbuffer.put(src, k, k).position(0).limit(k);
/* 739 */     GlStateManager.bindTexture(multiTex.norm);
/* 740 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intbuffer);
/* 741 */     GL11.glTexParameteri(3553, 10241, i);
/* 742 */     GL11.glTexParameteri(3553, 10240, i);
/* 743 */     GL11.glTexParameteri(3553, 10242, j);
/* 744 */     GL11.glTexParameteri(3553, 10243, j);
/* 745 */     intbuffer.put(src, k * 2, k).position(0).limit(k);
/* 746 */     GlStateManager.bindTexture(multiTex.spec);
/* 747 */     GL11.glTexImage2D(3553, 0, 6408, width, height, 0, 32993, 33639, intbuffer);
/* 748 */     GL11.glTexParameteri(3553, 10241, i);
/* 749 */     GL11.glTexParameteri(3553, 10240, i);
/* 750 */     GL11.glTexParameteri(3553, 10242, j);
/* 751 */     GL11.glTexParameteri(3553, 10243, j);
/* 752 */     GlStateManager.bindTexture(multiTex.base);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void updateSubImage(MultiTexID multiTex, int[] src, int width, int height, int posX, int posY, boolean linear, boolean clamp) {
/* 757 */     int i = width * height;
/* 758 */     IntBuffer intbuffer = getIntBuffer(i);
/* 759 */     intbuffer.clear();
/* 760 */     intbuffer.put(src, 0, i);
/* 761 */     intbuffer.position(0).limit(i);
/* 762 */     GlStateManager.bindTexture(multiTex.base);
/* 763 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 764 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 765 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 766 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 767 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/*     */     
/* 769 */     if (src.length == i * 3) {
/*     */       
/* 771 */       intbuffer.clear();
/* 772 */       intbuffer.put(src, i, i).position(0);
/* 773 */       intbuffer.position(0).limit(i);
/*     */     } 
/*     */     
/* 776 */     GlStateManager.bindTexture(multiTex.norm);
/* 777 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 778 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 779 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 780 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 781 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/*     */     
/* 783 */     if (src.length == i * 3) {
/*     */       
/* 785 */       intbuffer.clear();
/* 786 */       intbuffer.put(src, i * 2, i);
/* 787 */       intbuffer.position(0).limit(i);
/*     */     } 
/*     */     
/* 790 */     GlStateManager.bindTexture(multiTex.spec);
/* 791 */     GL11.glTexParameteri(3553, 10241, 9728);
/* 792 */     GL11.glTexParameteri(3553, 10240, 9728);
/* 793 */     GL11.glTexParameteri(3553, 10242, 10497);
/* 794 */     GL11.glTexParameteri(3553, 10243, 10497);
/* 795 */     GL11.glTexSubImage2D(3553, 0, posX, posY, width, height, 32993, 33639, intbuffer);
/* 796 */     GlStateManager.setActiveTexture(33984);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ResourceLocation getNSMapLocation(ResourceLocation location, String mapName) {
/* 801 */     String s = location.getResourcePath();
/* 802 */     String[] astring = s.split(".png");
/* 803 */     String s1 = astring[0];
/* 804 */     return new ResourceLocation(location.getResourceDomain(), String.valueOf(s1) + "_" + mapName + ".png");
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadNSMap(IResourceManager manager, ResourceLocation location, int width, int height, int[] aint) {
/* 809 */     if (Shaders.configNormalMap)
/*     */     {
/* 811 */       loadNSMap1(manager, getNSMapLocation(location, "n"), width, height, aint, width * height, -8421377);
/*     */     }
/*     */     
/* 814 */     if (Shaders.configSpecularMap)
/*     */     {
/* 816 */       loadNSMap1(manager, getNSMapLocation(location, "s"), width, height, aint, width * height * 2, 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadNSMap1(IResourceManager manager, ResourceLocation location, int width, int height, int[] aint, int offset, int defaultColor) {
/* 822 */     boolean flag = false;
/*     */ 
/*     */     
/*     */     try {
/* 826 */       IResource iresource = manager.getResource(location);
/* 827 */       BufferedImage bufferedimage = ImageIO.read(iresource.getInputStream());
/*     */       
/* 829 */       if (bufferedimage != null && bufferedimage.getWidth() == width && bufferedimage.getHeight() == height)
/*     */       {
/* 831 */         bufferedimage.getRGB(0, 0, width, height, aint, offset, width);
/* 832 */         flag = true;
/*     */       }
/*     */     
/* 835 */     } catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 840 */     if (!flag)
/*     */     {
/* 842 */       Arrays.fill(aint, offset, offset + width * height, defaultColor);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static int loadSimpleTexture(int textureID, BufferedImage bufferedimage, boolean linear, boolean clamp, IResourceManager resourceManager, ResourceLocation location, MultiTexID multiTex) {
/* 848 */     int i = bufferedimage.getWidth();
/* 849 */     int j = bufferedimage.getHeight();
/* 850 */     int k = i * j;
/* 851 */     int[] aint = getIntArray(k * 3);
/* 852 */     bufferedimage.getRGB(0, 0, i, j, aint, 0, i);
/* 853 */     loadNSMap(resourceManager, location, i, j, aint);
/* 854 */     setupTexture(multiTex, aint, i, j, linear, clamp);
/* 855 */     return textureID;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void mergeImage(int[] aint, int dstoff, int srcoff, int size) {}
/*     */ 
/*     */   
/*     */   public static int blendColor(int color1, int color2, int factor1) {
/* 864 */     int i = 255 - factor1;
/* 865 */     return ((color1 >>> 24 & 0xFF) * factor1 + (color2 >>> 24 & 0xFF) * i) / 255 << 24 | ((color1 >>> 16 & 0xFF) * factor1 + (color2 >>> 16 & 0xFF) * i) / 255 << 16 | ((color1 >>> 8 & 0xFF) * factor1 + (color2 >>> 8 & 0xFF) * i) / 255 << 8 | ((color1 >>> 0 & 0xFF) * factor1 + (color2 >>> 0 & 0xFF) * i) / 255 << 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void loadLayeredTexture(LayeredTexture tex, IResourceManager manager, List list) {
/* 870 */     int i = 0;
/* 871 */     int j = 0;
/* 872 */     int k = 0;
/* 873 */     int[] aint = null;
/*     */     
/* 875 */     for (Object s : list) {
/*     */       
/* 877 */       if (s != null) {
/*     */         
/*     */         try {
/*     */           
/* 881 */           ResourceLocation resourcelocation = new ResourceLocation((String)s);
/* 882 */           InputStream inputstream = manager.getResource(resourcelocation).getInputStream();
/* 883 */           BufferedImage bufferedimage = ImageIO.read(inputstream);
/*     */           
/* 885 */           if (k == 0) {
/*     */             
/* 887 */             i = bufferedimage.getWidth();
/* 888 */             j = bufferedimage.getHeight();
/* 889 */             k = i * j;
/* 890 */             aint = createAIntImage(k, 0);
/*     */           } 
/*     */           
/* 893 */           int[] aint1 = getIntArray(k * 3);
/* 894 */           bufferedimage.getRGB(0, 0, i, j, aint1, 0, i);
/* 895 */           loadNSMap(manager, resourcelocation, i, j, aint1);
/*     */           
/* 897 */           for (int l = 0; l < k; l++)
/*     */           {
/* 899 */             int i1 = aint1[l] >>> 24 & 0xFF;
/* 900 */             aint[k * 0 + l] = blendColor(aint1[k * 0 + l], aint[k * 0 + l], i1);
/* 901 */             aint[k * 1 + l] = blendColor(aint1[k * 1 + l], aint[k * 1 + l], i1);
/* 902 */             aint[k * 2 + l] = blendColor(aint1[k * 2 + l], aint[k * 2 + l], i1);
/*     */           }
/*     */         
/* 905 */         } catch (IOException ioexception) {
/*     */           
/* 907 */           ioexception.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 912 */     setupTexture(tex.getMultiTexID(), aint, i, j, false, false);
/*     */   }
/*     */ 
/*     */   
/*     */   static void updateTextureMinMagFilter() {
/* 917 */     TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
/* 918 */     ITextureObject itextureobject = texturemanager.getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*     */     
/* 920 */     if (itextureobject != null) {
/*     */       
/* 922 */       MultiTexID multitexid = itextureobject.getMultiTexID();
/* 923 */       GlStateManager.bindTexture(multitexid.base);
/* 924 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilB]);
/* 925 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilB]);
/* 926 */       GlStateManager.bindTexture(multitexid.norm);
/* 927 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilN]);
/* 928 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilN]);
/* 929 */       GlStateManager.bindTexture(multitexid.spec);
/* 930 */       GL11.glTexParameteri(3553, 10241, Shaders.texMinFilValue[Shaders.configTexMinFilS]);
/* 931 */       GL11.glTexParameteri(3553, 10240, Shaders.texMagFilValue[Shaders.configTexMagFilS]);
/* 932 */       GlStateManager.bindTexture(0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static IResource loadResource(IResourceManager manager, ResourceLocation location) throws IOException {
/* 938 */     resManager = manager;
/* 939 */     resLocation = location;
/* 940 */     return manager.getResource(location);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] loadAtlasSprite(BufferedImage bufferedimage, int startX, int startY, int w, int h, int[] aint, int offset, int scansize) {
/* 945 */     imageSize = w * h;
/* 946 */     bufferedimage.getRGB(startX, startY, w, h, aint, offset, scansize);
/* 947 */     loadNSMap(resManager, resLocation, w, h, aint);
/* 948 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] getFrameTexData(int[][] src, int width, int height, int frameIndex) {
/* 953 */     int i = src.length;
/* 954 */     int[][] aint = new int[i][];
/*     */     
/* 956 */     for (int j = 0; j < i; j++) {
/*     */       
/* 958 */       int[] aint1 = src[j];
/*     */       
/* 960 */       if (aint1 != null) {
/*     */         
/* 962 */         int k = (width >> j) * (height >> j);
/* 963 */         int[] aint2 = new int[k * 3];
/* 964 */         aint[j] = aint2;
/* 965 */         int l = aint1.length / 3;
/* 966 */         int i1 = k * frameIndex;
/* 967 */         int j1 = 0;
/* 968 */         System.arraycopy(aint1, i1, aint2, j1, k);
/* 969 */         i1 += l;
/* 970 */         j1 += k;
/* 971 */         System.arraycopy(aint1, i1, aint2, j1, k);
/* 972 */         i1 += l;
/* 973 */         j1 += k;
/* 974 */         System.arraycopy(aint1, i1, aint2, j1, k);
/*     */       } 
/*     */     } 
/*     */     
/* 978 */     return aint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[][] prepareAF(TextureAtlasSprite tas, int[][] src, int width, int height) {
/* 983 */     boolean flag = true;
/* 984 */     return src;
/*     */   }
/*     */   
/*     */   public static void fixTransparentColor(TextureAtlasSprite tas, int[] aint) {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\ShadersTex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */