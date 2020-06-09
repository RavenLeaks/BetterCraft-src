/*     */ package optifine;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics2D;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.imageio.ImageIO;
/*     */ import javax.imageio.ImageReader;
/*     */ import javax.imageio.stream.ImageInputStream;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.texture.ITextureObject;
/*     */ import net.minecraft.client.renderer.texture.ITickableTextureObject;
/*     */ import net.minecraft.client.renderer.texture.SimpleTexture;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.client.resources.IReloadableResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManager;
/*     */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.optifine.entity.model.CustomEntityModels;
/*     */ import org.lwjgl.BufferUtils;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ import shadersmod.client.MultiTexID;
/*     */ import shadersmod.client.Shaders;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TextureUtils
/*     */ {
/*     */   public static final String texGrassTop = "grass_top";
/*     */   public static final String texStone = "stone";
/*     */   public static final String texDirt = "dirt";
/*     */   public static final String texCoarseDirt = "coarse_dirt";
/*     */   public static final String texGrassSide = "grass_side";
/*     */   public static final String texStoneslabSide = "stone_slab_side";
/*     */   public static final String texStoneslabTop = "stone_slab_top";
/*     */   public static final String texBedrock = "bedrock";
/*     */   public static final String texSand = "sand";
/*     */   public static final String texGravel = "gravel";
/*     */   public static final String texLogOak = "log_oak";
/*     */   public static final String texLogBigOak = "log_big_oak";
/*     */   public static final String texLogAcacia = "log_acacia";
/*     */   public static final String texLogSpruce = "log_spruce";
/*     */   public static final String texLogBirch = "log_birch";
/*     */   public static final String texLogJungle = "log_jungle";
/*     */   public static final String texLogOakTop = "log_oak_top";
/*     */   public static final String texLogBigOakTop = "log_big_oak_top";
/*     */   public static final String texLogAcaciaTop = "log_acacia_top";
/*     */   public static final String texLogSpruceTop = "log_spruce_top";
/*     */   public static final String texLogBirchTop = "log_birch_top";
/*     */   public static final String texLogJungleTop = "log_jungle_top";
/*     */   public static final String texLeavesOak = "leaves_oak";
/*     */   public static final String texLeavesBigOak = "leaves_big_oak";
/*     */   public static final String texLeavesAcacia = "leaves_acacia";
/*     */   public static final String texLeavesBirch = "leaves_birch";
/*     */   public static final String texLeavesSpuce = "leaves_spruce";
/*     */   public static final String texLeavesJungle = "leaves_jungle";
/*     */   public static final String texGoldOre = "gold_ore";
/*     */   public static final String texIronOre = "iron_ore";
/*     */   public static final String texCoalOre = "coal_ore";
/*     */   public static final String texObsidian = "obsidian";
/*     */   public static final String texGrassSideOverlay = "grass_side_overlay";
/*     */   public static final String texSnow = "snow";
/*     */   public static final String texGrassSideSnowed = "grass_side_snowed";
/*     */   public static final String texMyceliumSide = "mycelium_side";
/*     */   public static final String texMyceliumTop = "mycelium_top";
/*     */   public static final String texDiamondOre = "diamond_ore";
/*     */   public static final String texRedstoneOre = "redstone_ore";
/*     */   public static final String texLapisOre = "lapis_ore";
/*     */   public static final String texCactusSide = "cactus_side";
/*     */   public static final String texClay = "clay";
/*     */   public static final String texFarmlandWet = "farmland_wet";
/*     */   public static final String texFarmlandDry = "farmland_dry";
/*     */   public static final String texNetherrack = "netherrack";
/*     */   public static final String texSoulSand = "soul_sand";
/*     */   public static final String texGlowstone = "glowstone";
/*     */   public static final String texLeavesSpruce = "leaves_spruce";
/*     */   public static final String texLeavesSpruceOpaque = "leaves_spruce_opaque";
/*     */   public static final String texEndStone = "end_stone";
/*     */   public static final String texSandstoneTop = "sandstone_top";
/*     */   public static final String texSandstoneBottom = "sandstone_bottom";
/*     */   public static final String texRedstoneLampOff = "redstone_lamp_off";
/*     */   public static final String texRedstoneLampOn = "redstone_lamp_on";
/*     */   public static final String texWaterStill = "water_still";
/*     */   public static final String texWaterFlow = "water_flow";
/*     */   public static final String texLavaStill = "lava_still";
/*     */   public static final String texLavaFlow = "lava_flow";
/*     */   public static final String texFireLayer0 = "fire_layer_0";
/*     */   public static final String texFireLayer1 = "fire_layer_1";
/*     */   public static final String texPortal = "portal";
/*     */   public static final String texGlass = "glass";
/*     */   public static final String texGlassPaneTop = "glass_pane_top";
/*     */   public static final String texCompass = "compass";
/*     */   public static final String texClock = "clock";
/*     */   public static TextureAtlasSprite iconGrassTop;
/*     */   public static TextureAtlasSprite iconGrassSide;
/*     */   public static TextureAtlasSprite iconGrassSideOverlay;
/*     */   public static TextureAtlasSprite iconSnow;
/*     */   public static TextureAtlasSprite iconGrassSideSnowed;
/*     */   public static TextureAtlasSprite iconMyceliumSide;
/*     */   public static TextureAtlasSprite iconMyceliumTop;
/*     */   public static TextureAtlasSprite iconWaterStill;
/*     */   public static TextureAtlasSprite iconWaterFlow;
/*     */   public static TextureAtlasSprite iconLavaStill;
/*     */   public static TextureAtlasSprite iconLavaFlow;
/*     */   public static TextureAtlasSprite iconPortal;
/*     */   public static TextureAtlasSprite iconFireLayer0;
/*     */   public static TextureAtlasSprite iconFireLayer1;
/*     */   public static TextureAtlasSprite iconGlass;
/*     */   public static TextureAtlasSprite iconGlassPaneTop;
/*     */   public static TextureAtlasSprite iconCompass;
/*     */   public static TextureAtlasSprite iconClock;
/*     */   public static final String SPRITE_PREFIX_BLOCKS = "minecraft:blocks/";
/*     */   public static final String SPRITE_PREFIX_ITEMS = "minecraft:items/";
/* 126 */   private static IntBuffer staticBuffer = GLAllocation.createDirectIntBuffer(256);
/*     */ 
/*     */   
/*     */   public static void update() {
/* 130 */     TextureMap texturemap = getTextureMapBlocks();
/*     */     
/* 132 */     if (texturemap != null) {
/*     */       
/* 134 */       String s = "minecraft:blocks/";
/* 135 */       iconGrassTop = texturemap.getSpriteSafe(String.valueOf(s) + "grass_top");
/* 136 */       iconGrassSide = texturemap.getSpriteSafe(String.valueOf(s) + "grass_side");
/* 137 */       iconGrassSideOverlay = texturemap.getSpriteSafe(String.valueOf(s) + "grass_side_overlay");
/* 138 */       iconSnow = texturemap.getSpriteSafe(String.valueOf(s) + "snow");
/* 139 */       iconGrassSideSnowed = texturemap.getSpriteSafe(String.valueOf(s) + "grass_side_snowed");
/* 140 */       iconMyceliumSide = texturemap.getSpriteSafe(String.valueOf(s) + "mycelium_side");
/* 141 */       iconMyceliumTop = texturemap.getSpriteSafe(String.valueOf(s) + "mycelium_top");
/* 142 */       iconWaterStill = texturemap.getSpriteSafe(String.valueOf(s) + "water_still");
/* 143 */       iconWaterFlow = texturemap.getSpriteSafe(String.valueOf(s) + "water_flow");
/* 144 */       iconLavaStill = texturemap.getSpriteSafe(String.valueOf(s) + "lava_still");
/* 145 */       iconLavaFlow = texturemap.getSpriteSafe(String.valueOf(s) + "lava_flow");
/* 146 */       iconFireLayer0 = texturemap.getSpriteSafe(String.valueOf(s) + "fire_layer_0");
/* 147 */       iconFireLayer1 = texturemap.getSpriteSafe(String.valueOf(s) + "fire_layer_1");
/* 148 */       iconPortal = texturemap.getSpriteSafe(String.valueOf(s) + "portal");
/* 149 */       iconGlass = texturemap.getSpriteSafe(String.valueOf(s) + "glass");
/* 150 */       iconGlassPaneTop = texturemap.getSpriteSafe(String.valueOf(s) + "glass_pane_top");
/* 151 */       String s1 = "minecraft:items/";
/* 152 */       iconCompass = texturemap.getSpriteSafe(String.valueOf(s1) + "compass");
/* 153 */       iconClock = texturemap.getSpriteSafe(String.valueOf(s1) + "clock");
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static BufferedImage fixTextureDimensions(String p_fixTextureDimensions_0_, BufferedImage p_fixTextureDimensions_1_) {
/* 159 */     if (p_fixTextureDimensions_0_.startsWith("/mob/zombie") || p_fixTextureDimensions_0_.startsWith("/mob/pigzombie")) {
/*     */       
/* 161 */       int i = p_fixTextureDimensions_1_.getWidth();
/* 162 */       int j = p_fixTextureDimensions_1_.getHeight();
/*     */       
/* 164 */       if (i == j * 2) {
/*     */         
/* 166 */         BufferedImage bufferedimage = new BufferedImage(i, j * 2, 2);
/* 167 */         Graphics2D graphics2d = bufferedimage.createGraphics();
/* 168 */         graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
/* 169 */         graphics2d.drawImage(p_fixTextureDimensions_1_, 0, 0, i, j, null);
/* 170 */         return bufferedimage;
/*     */       } 
/*     */     } 
/*     */     
/* 174 */     return p_fixTextureDimensions_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int ceilPowerOfTwo(int p_ceilPowerOfTwo_0_) {
/* 181 */     for (int i = 1; i < p_ceilPowerOfTwo_0_; i *= 2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 186 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getPowerOfTwo(int p_getPowerOfTwo_0_) {
/* 191 */     int i = 1;
/*     */ 
/*     */     
/* 194 */     for (int j = 0; i < p_getPowerOfTwo_0_; j++)
/*     */     {
/* 196 */       i *= 2;
/*     */     }
/*     */     
/* 199 */     return j;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int twoToPower(int p_twoToPower_0_) {
/* 204 */     int i = 1;
/*     */     
/* 206 */     for (int j = 0; j < p_twoToPower_0_; j++)
/*     */     {
/* 208 */       i *= 2;
/*     */     }
/*     */     
/* 211 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ITextureObject getTexture(ResourceLocation p_getTexture_0_) {
/* 216 */     ITextureObject itextureobject = Config.getTextureManager().getTexture(p_getTexture_0_);
/*     */     
/* 218 */     if (itextureobject != null)
/*     */     {
/* 220 */       return itextureobject;
/*     */     }
/* 222 */     if (!Config.hasResource(p_getTexture_0_))
/*     */     {
/* 224 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 228 */     SimpleTexture simpleTexture = new SimpleTexture(p_getTexture_0_);
/* 229 */     Config.getTextureManager().loadTexture(p_getTexture_0_, (ITextureObject)simpleTexture);
/* 230 */     return (ITextureObject)simpleTexture;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void resourcesReloaded(IResourceManager p_resourcesReloaded_0_) {
/* 236 */     if (getTextureMapBlocks() != null) {
/*     */       
/* 238 */       Config.dbg("*** Reloading custom textures ***");
/* 239 */       CustomSky.reset();
/* 240 */       TextureAnimations.reset();
/* 241 */       update();
/* 242 */       NaturalTextures.update();
/* 243 */       BetterGrass.update();
/* 244 */       BetterSnow.update();
/* 245 */       TextureAnimations.update();
/* 246 */       CustomColors.update();
/* 247 */       CustomSky.update();
/* 248 */       RandomMobs.resetTextures();
/* 249 */       CustomItems.updateModels();
/* 250 */       CustomEntityModels.update();
/* 251 */       Shaders.resourcesReloaded();
/* 252 */       Lang.resourcesReloaded();
/* 253 */       Config.updateTexturePackClouds();
/* 254 */       SmartLeaves.updateLeavesModels();
/* 255 */       CustomPanorama.update();
/* 256 */       CustomGuis.update();
/* 257 */       Config.getTextureManager().tick();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static TextureMap getTextureMapBlocks() {
/* 263 */     return Minecraft.getMinecraft().getTextureMapBlocks();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerResourceListener() {
/* 268 */     IResourceManager iresourcemanager = Config.getResourceManager();
/*     */     
/* 270 */     if (iresourcemanager instanceof IReloadableResourceManager) {
/*     */       
/* 272 */       IReloadableResourceManager ireloadableresourcemanager = (IReloadableResourceManager)iresourcemanager;
/* 273 */       IResourceManagerReloadListener iresourcemanagerreloadlistener = new IResourceManagerReloadListener()
/*     */         {
/*     */           public void onResourceManagerReload(IResourceManager resourceManager)
/*     */           {
/* 277 */             TextureUtils.resourcesReloaded(resourceManager);
/*     */           }
/*     */         };
/* 280 */       ireloadableresourcemanager.registerReloadListener(iresourcemanagerreloadlistener);
/*     */     } 
/*     */     
/* 283 */     ITickableTextureObject itickabletextureobject = new ITickableTextureObject()
/*     */       {
/*     */         public void tick()
/*     */         {
/* 287 */           TextureAnimations.updateCustomAnimations();
/*     */         }
/*     */ 
/*     */         
/*     */         public void loadTexture(IResourceManager resourceManager) throws IOException {}
/*     */         
/*     */         public int getGlTextureId() {
/* 294 */           return 0;
/*     */         }
/*     */ 
/*     */         
/*     */         public void setBlurMipmap(boolean blurIn, boolean mipmapIn) {}
/*     */ 
/*     */         
/*     */         public void restoreLastBlurMipmap() {}
/*     */         
/*     */         public MultiTexID getMultiTexID() {
/* 304 */           return null;
/*     */         }
/*     */       };
/* 307 */     ResourceLocation resourcelocation = new ResourceLocation("optifine/TickableTextures");
/* 308 */     Config.getTextureManager().loadTickableTexture(resourcelocation, itickabletextureobject);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ResourceLocation fixResourceLocation(ResourceLocation p_fixResourceLocation_0_, String p_fixResourceLocation_1_) {
/* 313 */     if (!p_fixResourceLocation_0_.getResourceDomain().equals("minecraft"))
/*     */     {
/* 315 */       return p_fixResourceLocation_0_;
/*     */     }
/*     */ 
/*     */     
/* 319 */     String s = p_fixResourceLocation_0_.getResourcePath();
/* 320 */     String s1 = fixResourcePath(s, p_fixResourceLocation_1_);
/*     */     
/* 322 */     if (s1 != s)
/*     */     {
/* 324 */       p_fixResourceLocation_0_ = new ResourceLocation(p_fixResourceLocation_0_.getResourceDomain(), s1);
/*     */     }
/*     */     
/* 327 */     return p_fixResourceLocation_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String fixResourcePath(String p_fixResourcePath_0_, String p_fixResourcePath_1_) {
/* 333 */     String s = "assets/minecraft/";
/*     */     
/* 335 */     if (p_fixResourcePath_0_.startsWith(s)) {
/*     */       
/* 337 */       p_fixResourcePath_0_ = p_fixResourcePath_0_.substring(s.length());
/* 338 */       return p_fixResourcePath_0_;
/*     */     } 
/* 340 */     if (p_fixResourcePath_0_.startsWith("./")) {
/*     */       
/* 342 */       p_fixResourcePath_0_ = p_fixResourcePath_0_.substring(2);
/*     */       
/* 344 */       if (!p_fixResourcePath_1_.endsWith("/"))
/*     */       {
/* 346 */         p_fixResourcePath_1_ = String.valueOf(p_fixResourcePath_1_) + "/";
/*     */       }
/*     */       
/* 349 */       p_fixResourcePath_0_ = String.valueOf(p_fixResourcePath_1_) + p_fixResourcePath_0_;
/* 350 */       return p_fixResourcePath_0_;
/*     */     } 
/*     */ 
/*     */     
/* 354 */     if (p_fixResourcePath_0_.startsWith("/~"))
/*     */     {
/* 356 */       p_fixResourcePath_0_ = p_fixResourcePath_0_.substring(1);
/*     */     }
/*     */     
/* 359 */     String s1 = "mcpatcher/";
/*     */     
/* 361 */     if (p_fixResourcePath_0_.startsWith("~/")) {
/*     */       
/* 363 */       p_fixResourcePath_0_ = p_fixResourcePath_0_.substring(2);
/* 364 */       p_fixResourcePath_0_ = String.valueOf(s1) + p_fixResourcePath_0_;
/* 365 */       return p_fixResourcePath_0_;
/*     */     } 
/* 367 */     if (p_fixResourcePath_0_.startsWith("/")) {
/*     */       
/* 369 */       p_fixResourcePath_0_ = String.valueOf(s1) + p_fixResourcePath_0_.substring(1);
/* 370 */       return p_fixResourcePath_0_;
/*     */     } 
/*     */ 
/*     */     
/* 374 */     return p_fixResourcePath_0_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getBasePath(String p_getBasePath_0_) {
/* 381 */     int i = p_getBasePath_0_.lastIndexOf('/');
/* 382 */     return (i < 0) ? "" : p_getBasePath_0_.substring(0, i);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void applyAnisotropicLevel() {
/* 387 */     if ((GLContext.getCapabilities()).GL_EXT_texture_filter_anisotropic) {
/*     */       
/* 389 */       float f = GL11.glGetFloat(34047);
/* 390 */       float f1 = Config.getAnisotropicFilterLevel();
/* 391 */       f1 = Math.min(f1, f);
/* 392 */       GL11.glTexParameterf(3553, 34046, f1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void bindTexture(int p_bindTexture_0_) {
/* 398 */     GlStateManager.bindTexture(p_bindTexture_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isPowerOfTwo(int p_isPowerOfTwo_0_) {
/* 403 */     int i = MathHelper.smallestEncompassingPowerOfTwo(p_isPowerOfTwo_0_);
/* 404 */     return (i == p_isPowerOfTwo_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static BufferedImage scaleImage(BufferedImage p_scaleImage_0_, int p_scaleImage_1_) {
/* 409 */     int i = p_scaleImage_0_.getWidth();
/* 410 */     int j = p_scaleImage_0_.getHeight();
/* 411 */     int k = j * p_scaleImage_1_ / i;
/* 412 */     BufferedImage bufferedimage = new BufferedImage(p_scaleImage_1_, k, 2);
/* 413 */     Graphics2D graphics2d = bufferedimage.createGraphics();
/* 414 */     Object object = RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR;
/*     */     
/* 416 */     if (p_scaleImage_1_ < i || p_scaleImage_1_ % i != 0)
/*     */     {
/* 418 */       object = RenderingHints.VALUE_INTERPOLATION_BILINEAR;
/*     */     }
/*     */     
/* 421 */     graphics2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, object);
/* 422 */     graphics2d.drawImage(p_scaleImage_0_, 0, 0, p_scaleImage_1_, k, null);
/* 423 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BufferedImage scaleToPowerOfTwo(BufferedImage p_scaleToPowerOfTwo_0_, int p_scaleToPowerOfTwo_1_) {
/* 428 */     if (p_scaleToPowerOfTwo_0_ == null)
/*     */     {
/* 430 */       return p_scaleToPowerOfTwo_0_;
/*     */     }
/*     */ 
/*     */     
/* 434 */     int i = p_scaleToPowerOfTwo_0_.getWidth();
/* 435 */     int j = scaleToPowerOfTwo(i, p_scaleToPowerOfTwo_1_);
/*     */     
/* 437 */     if (j == i)
/*     */     {
/* 439 */       return p_scaleToPowerOfTwo_0_;
/*     */     }
/*     */ 
/*     */     
/* 443 */     BufferedImage bufferedimage = scaleImage(p_scaleToPowerOfTwo_0_, j);
/* 444 */     return bufferedimage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int scaleToPowerOfTwo(int p_scaleToPowerOfTwo_0_, int p_scaleToPowerOfTwo_1_) {
/* 451 */     int i = Math.max(p_scaleToPowerOfTwo_0_, p_scaleToPowerOfTwo_1_);
/* 452 */     i = MathHelper.smallestEncompassingPowerOfTwo(i);
/* 453 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int scaleMinTo(int p_scaleMinTo_0_, int p_scaleMinTo_1_) {
/* 458 */     if (p_scaleMinTo_0_ >= p_scaleMinTo_1_)
/*     */     {
/* 460 */       return p_scaleMinTo_0_;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 466 */     for (int i = p_scaleMinTo_0_; i < p_scaleMinTo_1_; i *= 2);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 471 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Dimension getImageSize(InputStream p_getImageSize_0_, String p_getImageSize_1_) {
/* 477 */     Iterator<ImageReader> iterator = ImageIO.getImageReadersBySuffix(p_getImageSize_1_);
/*     */ 
/*     */ 
/*     */     
/* 481 */     while (iterator.hasNext()) {
/*     */       Dimension dimension;
/* 483 */       ImageReader imagereader = iterator.next();
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 488 */         ImageInputStream imageinputstream = ImageIO.createImageInputStream(p_getImageSize_0_);
/* 489 */         imagereader.setInput(imageinputstream);
/* 490 */         int i = imagereader.getWidth(imagereader.getMinIndex());
/* 491 */         int j = imagereader.getHeight(imagereader.getMinIndex());
/* 492 */         dimension = new Dimension(i, j);
/*     */       }
/* 494 */       catch (IOException var11) {
/*     */         
/*     */         continue;
/*     */       }
/*     */       finally {
/*     */         
/* 500 */         imagereader.dispose();
/*     */       } 
/*     */       
/* 503 */       return dimension;
/*     */     } 
/*     */     
/* 506 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void dbgMipmaps(TextureAtlasSprite p_dbgMipmaps_0_) {
/* 512 */     int[][] aint = p_dbgMipmaps_0_.getFrameTextureData(0);
/*     */     
/* 514 */     for (int i = 0; i < aint.length; i++) {
/*     */       
/* 516 */       int[] aint1 = aint[i];
/*     */       
/* 518 */       if (aint1 == null) {
/*     */         
/* 520 */         Config.dbg(i + ": " + aint1);
/*     */       }
/*     */       else {
/*     */         
/* 524 */         Config.dbg(i + ": " + aint1.length);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void saveGlTexture(String p_saveGlTexture_0_, int p_saveGlTexture_1_, int p_saveGlTexture_2_, int p_saveGlTexture_3_, int p_saveGlTexture_4_) {
/* 531 */     bindTexture(p_saveGlTexture_1_);
/* 532 */     GL11.glPixelStorei(3333, 1);
/* 533 */     GL11.glPixelStorei(3317, 1);
/* 534 */     File file1 = new File(p_saveGlTexture_0_);
/* 535 */     File file2 = file1.getParentFile();
/*     */     
/* 537 */     if (file2 != null)
/*     */     {
/* 539 */       file2.mkdirs();
/*     */     }
/*     */     
/* 542 */     for (int i = 0; i < 16; i++) {
/*     */       
/* 544 */       File file3 = new File(String.valueOf(p_saveGlTexture_0_) + "_" + i + ".png");
/* 545 */       file3.delete();
/*     */     } 
/*     */     
/* 548 */     for (int i1 = 0; i1 <= p_saveGlTexture_2_; i1++) {
/*     */       
/* 550 */       File file4 = new File(String.valueOf(p_saveGlTexture_0_) + "_" + i1 + ".png");
/* 551 */       int j = p_saveGlTexture_3_ >> i1;
/* 552 */       int k = p_saveGlTexture_4_ >> i1;
/* 553 */       int l = j * k;
/* 554 */       IntBuffer intbuffer = BufferUtils.createIntBuffer(l);
/* 555 */       int[] aint = new int[l];
/* 556 */       GL11.glGetTexImage(3553, i1, 32993, 33639, intbuffer);
/* 557 */       intbuffer.get(aint);
/* 558 */       BufferedImage bufferedimage = new BufferedImage(j, k, 2);
/* 559 */       bufferedimage.setRGB(0, 0, j, k, aint, 0, j);
/*     */ 
/*     */       
/*     */       try {
/* 563 */         ImageIO.write(bufferedimage, "png", file4);
/* 564 */         Config.dbg("Exported: " + file4);
/*     */       }
/* 566 */       catch (Exception exception) {
/*     */         
/* 568 */         Config.warn("Error writing: " + file4);
/* 569 */         Config.warn(exception.getClass().getName() + ": " + exception.getMessage());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void generateCustomMipmaps(TextureAtlasSprite p_generateCustomMipmaps_0_, int p_generateCustomMipmaps_1_) {
/* 576 */     int i = p_generateCustomMipmaps_0_.getIconWidth();
/* 577 */     int j = p_generateCustomMipmaps_0_.getIconHeight();
/*     */     
/* 579 */     if (p_generateCustomMipmaps_0_.getFrameCount() < 1) {
/*     */       
/* 581 */       List<int[][]> list = (List)new ArrayList<>();
/* 582 */       int[][] aint = new int[p_generateCustomMipmaps_1_ + 1][];
/* 583 */       int[] aint1 = new int[i * j];
/* 584 */       aint[0] = aint1;
/* 585 */       list.add(aint);
/* 586 */       p_generateCustomMipmaps_0_.setFramesTextureData(list);
/*     */     } 
/*     */     
/* 589 */     List<int[][]> list1 = (List)new ArrayList<>();
/* 590 */     int l = p_generateCustomMipmaps_0_.getFrameCount();
/*     */     
/* 592 */     for (int i1 = 0; i1 < l; i1++) {
/*     */       
/* 594 */       int[] aint2 = getFrameData(p_generateCustomMipmaps_0_, i1, 0);
/*     */       
/* 596 */       if (aint2 == null || aint2.length < 1)
/*     */       {
/* 598 */         aint2 = new int[i * j];
/*     */       }
/*     */       
/* 601 */       if (aint2.length != i * j) {
/*     */         
/* 603 */         int k = (int)Math.round(Math.sqrt(aint2.length));
/*     */         
/* 605 */         if (k * k != aint2.length) {
/*     */           
/* 607 */           aint2 = new int[1];
/* 608 */           k = 1;
/*     */         } 
/*     */         
/* 611 */         BufferedImage bufferedimage = new BufferedImage(k, k, 2);
/* 612 */         bufferedimage.setRGB(0, 0, k, k, aint2, 0, k);
/* 613 */         BufferedImage bufferedimage1 = scaleImage(bufferedimage, i);
/* 614 */         int[] aint3 = new int[i * j];
/* 615 */         bufferedimage1.getRGB(0, 0, i, j, aint3, 0, i);
/* 616 */         aint2 = aint3;
/*     */       } 
/*     */       
/* 619 */       int[][] aint4 = new int[p_generateCustomMipmaps_1_ + 1][];
/* 620 */       aint4[0] = aint2;
/* 621 */       list1.add(aint4);
/*     */     } 
/*     */     
/* 624 */     p_generateCustomMipmaps_0_.setFramesTextureData(list1);
/* 625 */     p_generateCustomMipmaps_0_.generateMipmaps(p_generateCustomMipmaps_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int[] getFrameData(TextureAtlasSprite p_getFrameData_0_, int p_getFrameData_1_, int p_getFrameData_2_) {
/* 630 */     List<int[][]> list = p_getFrameData_0_.getFramesTextureData();
/*     */     
/* 632 */     if (list.size() <= p_getFrameData_1_)
/*     */     {
/* 634 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 638 */     int[][] aint = list.get(p_getFrameData_1_);
/*     */     
/* 640 */     if (aint != null && aint.length > p_getFrameData_2_) {
/*     */       
/* 642 */       int[] aint1 = aint[p_getFrameData_2_];
/* 643 */       return aint1;
/*     */     } 
/*     */ 
/*     */     
/* 647 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getGLMaximumTextureSize() {
/* 654 */     for (int i = 65536; i > 0; i >>= 1) {
/*     */       
/* 656 */       GlStateManager.glTexImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, null);
/* 657 */       int j = GL11.glGetError();
/* 658 */       int k = GlStateManager.glGetTexLevelParameteri(32868, 0, 4096);
/*     */       
/* 660 */       if (k != 0)
/*     */       {
/* 662 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 666 */     return -1;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\TextureUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */