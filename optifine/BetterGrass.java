/*     */ package optifine;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.client.renderer.texture.TextureMap;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BetterGrass
/*     */ {
/*     */   private static boolean betterGrass = true;
/*     */   private static boolean betterGrassPath = true;
/*     */   private static boolean betterMycelium = true;
/*     */   private static boolean betterPodzol = true;
/*     */   private static boolean betterGrassSnow = true;
/*     */   private static boolean betterMyceliumSnow = true;
/*     */   private static boolean betterPodzolSnow = true;
/*     */   private static boolean grassMultilayer = false;
/*  32 */   private static TextureAtlasSprite spriteGrass = null;
/*  33 */   private static TextureAtlasSprite spriteGrassSide = null;
/*  34 */   private static TextureAtlasSprite spriteGrassPath = null;
/*  35 */   private static TextureAtlasSprite spriteMycelium = null;
/*  36 */   private static TextureAtlasSprite spritePodzol = null;
/*  37 */   private static TextureAtlasSprite spriteSnow = null;
/*     */   private static boolean spritesLoaded = false;
/*  39 */   private static IBakedModel modelCubeGrass = null;
/*  40 */   private static IBakedModel modelGrassPath = null;
/*  41 */   private static IBakedModel modelCubeGrassPath = null;
/*  42 */   private static IBakedModel modelCubeMycelium = null;
/*  43 */   private static IBakedModel modelCubePodzol = null;
/*  44 */   private static IBakedModel modelCubeSnow = null;
/*     */   
/*     */   private static boolean modelsLoaded = false;
/*     */   private static final String TEXTURE_GRASS_DEFAULT = "blocks/grass_top";
/*     */   private static final String TEXTURE_GRASS_SIDE_DEFAULT = "blocks/grass_side";
/*     */   private static final String TEXTURE_GRASS_PATH_DEFAULT = "blocks/grass_path_top";
/*     */   private static final String TEXTURE_MYCELIUM_DEFAULT = "blocks/mycelium_top";
/*     */   private static final String TEXTURE_PODZOL_DEFAULT = "blocks/dirt_podzol_top";
/*     */   private static final String TEXTURE_SNOW_DEFAULT = "blocks/snow";
/*     */   
/*     */   public static void updateIcons(TextureMap p_updateIcons_0_) {
/*  55 */     spritesLoaded = false;
/*  56 */     modelsLoaded = false;
/*  57 */     loadProperties(p_updateIcons_0_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void update() {
/*  62 */     if (spritesLoaded) {
/*     */       
/*  64 */       modelCubeGrass = BlockModelUtils.makeModelCube(spriteGrass, 0);
/*     */       
/*  66 */       if (grassMultilayer) {
/*     */         
/*  68 */         IBakedModel ibakedmodel = BlockModelUtils.makeModelCube(spriteGrassSide, -1);
/*  69 */         modelCubeGrass = BlockModelUtils.joinModelsCube(ibakedmodel, modelCubeGrass);
/*     */       } 
/*     */       
/*  72 */       TextureAtlasSprite textureatlassprite = Config.getTextureMap().registerSprite(new ResourceLocation("blocks/grass_path_side"));
/*  73 */       modelGrassPath = BlockModelUtils.makeModel("grass_path", textureatlassprite, spriteGrassPath);
/*  74 */       modelCubeGrassPath = BlockModelUtils.makeModelCube(spriteGrassPath, -1);
/*  75 */       modelCubeMycelium = BlockModelUtils.makeModelCube(spriteMycelium, -1);
/*  76 */       modelCubePodzol = BlockModelUtils.makeModelCube(spritePodzol, 0);
/*  77 */       modelCubeSnow = BlockModelUtils.makeModelCube(spriteSnow, -1);
/*  78 */       modelsLoaded = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void loadProperties(TextureMap p_loadProperties_0_) {
/*  84 */     betterGrass = true;
/*  85 */     betterGrassPath = true;
/*  86 */     betterMycelium = true;
/*  87 */     betterPodzol = true;
/*  88 */     betterGrassSnow = true;
/*  89 */     betterMyceliumSnow = true;
/*  90 */     betterPodzolSnow = true;
/*  91 */     spriteGrass = p_loadProperties_0_.registerSprite(new ResourceLocation("blocks/grass_top"));
/*  92 */     spriteGrassSide = p_loadProperties_0_.registerSprite(new ResourceLocation("blocks/grass_side"));
/*  93 */     spriteGrassPath = p_loadProperties_0_.registerSprite(new ResourceLocation("blocks/grass_path_top"));
/*  94 */     spriteMycelium = p_loadProperties_0_.registerSprite(new ResourceLocation("blocks/mycelium_top"));
/*  95 */     spritePodzol = p_loadProperties_0_.registerSprite(new ResourceLocation("blocks/dirt_podzol_top"));
/*  96 */     spriteSnow = p_loadProperties_0_.registerSprite(new ResourceLocation("blocks/snow"));
/*  97 */     spritesLoaded = true;
/*  98 */     String s = "optifine/bettergrass.properties";
/*     */ 
/*     */     
/*     */     try {
/* 102 */       ResourceLocation resourcelocation = new ResourceLocation(s);
/*     */       
/* 104 */       if (!Config.hasResource(resourcelocation)) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 109 */       InputStream inputstream = Config.getResourceStream(resourcelocation);
/*     */       
/* 111 */       if (inputstream == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 116 */       boolean flag = Config.isFromDefaultResourcePack(resourcelocation);
/*     */       
/* 118 */       if (flag) {
/*     */         
/* 120 */         Config.dbg("BetterGrass: Parsing default configuration " + s);
/*     */       }
/*     */       else {
/*     */         
/* 124 */         Config.dbg("BetterGrass: Parsing configuration " + s);
/*     */       } 
/*     */       
/* 127 */       Properties properties = new Properties();
/* 128 */       properties.load(inputstream);
/* 129 */       betterGrass = getBoolean(properties, "grass", true);
/* 130 */       betterGrassPath = getBoolean(properties, "grass_path", true);
/* 131 */       betterMycelium = getBoolean(properties, "mycelium", true);
/* 132 */       betterPodzol = getBoolean(properties, "podzol", true);
/* 133 */       betterGrassSnow = getBoolean(properties, "grass.snow", true);
/* 134 */       betterMyceliumSnow = getBoolean(properties, "mycelium.snow", true);
/* 135 */       betterPodzolSnow = getBoolean(properties, "podzol.snow", true);
/* 136 */       grassMultilayer = getBoolean(properties, "grass.multilayer", false);
/* 137 */       spriteGrass = registerSprite(properties, "texture.grass", "blocks/grass_top", p_loadProperties_0_);
/* 138 */       spriteGrassSide = registerSprite(properties, "texture.grass_side", "blocks/grass_side", p_loadProperties_0_);
/* 139 */       spriteGrassPath = registerSprite(properties, "texture.grass_path", "blocks/grass_path_top", p_loadProperties_0_);
/* 140 */       spriteMycelium = registerSprite(properties, "texture.mycelium", "blocks/mycelium_top", p_loadProperties_0_);
/* 141 */       spritePodzol = registerSprite(properties, "texture.podzol", "blocks/dirt_podzol_top", p_loadProperties_0_);
/* 142 */       spriteSnow = registerSprite(properties, "texture.snow", "blocks/snow", p_loadProperties_0_);
/*     */     }
/* 144 */     catch (IOException ioexception) {
/*     */       
/* 146 */       Config.warn("Error reading: " + s + ", " + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static TextureAtlasSprite registerSprite(Properties p_registerSprite_0_, String p_registerSprite_1_, String p_registerSprite_2_, TextureMap p_registerSprite_3_) {
/* 152 */     String s = p_registerSprite_0_.getProperty(p_registerSprite_1_);
/*     */     
/* 154 */     if (s == null)
/*     */     {
/* 156 */       s = p_registerSprite_2_;
/*     */     }
/*     */     
/* 159 */     ResourceLocation resourcelocation = new ResourceLocation("textures/" + s + ".png");
/*     */     
/* 161 */     if (!Config.hasResource(resourcelocation)) {
/*     */       
/* 163 */       Config.warn("BetterGrass texture not found: " + resourcelocation);
/* 164 */       s = p_registerSprite_2_;
/*     */     } 
/*     */     
/* 167 */     ResourceLocation resourcelocation1 = new ResourceLocation(s);
/* 168 */     TextureAtlasSprite textureatlassprite = p_registerSprite_3_.registerSprite(resourcelocation1);
/* 169 */     return textureatlassprite;
/*     */   }
/*     */ 
/*     */   
/*     */   public static List getFaceQuads(IBlockAccess p_getFaceQuads_0_, IBlockState p_getFaceQuads_1_, BlockPos p_getFaceQuads_2_, EnumFacing p_getFaceQuads_3_, List p_getFaceQuads_4_) {
/* 174 */     if (p_getFaceQuads_3_ != EnumFacing.UP && p_getFaceQuads_3_ != EnumFacing.DOWN) {
/*     */       
/* 176 */       if (!modelsLoaded)
/*     */       {
/* 178 */         return p_getFaceQuads_4_;
/*     */       }
/*     */ 
/*     */       
/* 182 */       Block block = p_getFaceQuads_1_.getBlock();
/*     */       
/* 184 */       if (block instanceof net.minecraft.block.BlockMycelium)
/*     */       {
/* 186 */         return getFaceQuadsMycelium(p_getFaceQuads_0_, p_getFaceQuads_1_, p_getFaceQuads_2_, p_getFaceQuads_3_, p_getFaceQuads_4_);
/*     */       }
/* 188 */       if (block instanceof net.minecraft.block.BlockGrassPath)
/*     */       {
/* 190 */         return getFaceQuadsGrassPath(p_getFaceQuads_0_, p_getFaceQuads_1_, p_getFaceQuads_2_, p_getFaceQuads_3_, p_getFaceQuads_4_);
/*     */       }
/* 192 */       if (block instanceof BlockDirt)
/*     */       {
/* 194 */         return getFaceQuadsDirt(p_getFaceQuads_0_, p_getFaceQuads_1_, p_getFaceQuads_2_, p_getFaceQuads_3_, p_getFaceQuads_4_);
/*     */       }
/*     */ 
/*     */       
/* 198 */       return (block instanceof net.minecraft.block.BlockGrass) ? getFaceQuadsGrass(p_getFaceQuads_0_, p_getFaceQuads_1_, p_getFaceQuads_2_, p_getFaceQuads_3_, p_getFaceQuads_4_) : p_getFaceQuads_4_;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     return p_getFaceQuads_4_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List getFaceQuadsMycelium(IBlockAccess p_getFaceQuadsMycelium_0_, IBlockState p_getFaceQuadsMycelium_1_, BlockPos p_getFaceQuadsMycelium_2_, EnumFacing p_getFaceQuadsMycelium_3_, List p_getFaceQuadsMycelium_4_) {
/* 210 */     Block block = p_getFaceQuadsMycelium_0_.getBlockState(p_getFaceQuadsMycelium_2_.up()).getBlock();
/* 211 */     boolean flag = !(block != Blocks.SNOW && block != Blocks.SNOW_LAYER);
/*     */     
/* 213 */     if (Config.isBetterGrassFancy()) {
/*     */       
/* 215 */       if (flag)
/*     */       {
/* 217 */         if (betterMyceliumSnow && getBlockAt(p_getFaceQuadsMycelium_2_, p_getFaceQuadsMycelium_3_, p_getFaceQuadsMycelium_0_) == Blocks.SNOW_LAYER)
/*     */         {
/* 219 */           return modelCubeSnow.getQuads(p_getFaceQuadsMycelium_1_, p_getFaceQuadsMycelium_3_, 0L);
/*     */         }
/*     */       }
/* 222 */       else if (betterMycelium && getBlockAt(p_getFaceQuadsMycelium_2_.down(), p_getFaceQuadsMycelium_3_, p_getFaceQuadsMycelium_0_) == Blocks.MYCELIUM)
/*     */       {
/* 224 */         return modelCubeMycelium.getQuads(p_getFaceQuadsMycelium_1_, p_getFaceQuadsMycelium_3_, 0L);
/*     */       }
/*     */     
/* 227 */     } else if (flag) {
/*     */       
/* 229 */       if (betterMyceliumSnow)
/*     */       {
/* 231 */         return modelCubeSnow.getQuads(p_getFaceQuadsMycelium_1_, p_getFaceQuadsMycelium_3_, 0L);
/*     */       }
/*     */     }
/* 234 */     else if (betterMycelium) {
/*     */       
/* 236 */       return modelCubeMycelium.getQuads(p_getFaceQuadsMycelium_1_, p_getFaceQuadsMycelium_3_, 0L);
/*     */     } 
/*     */     
/* 239 */     return p_getFaceQuadsMycelium_4_;
/*     */   }
/*     */ 
/*     */   
/*     */   private static List getFaceQuadsGrassPath(IBlockAccess p_getFaceQuadsGrassPath_0_, IBlockState p_getFaceQuadsGrassPath_1_, BlockPos p_getFaceQuadsGrassPath_2_, EnumFacing p_getFaceQuadsGrassPath_3_, List p_getFaceQuadsGrassPath_4_) {
/* 244 */     if (!betterGrassPath)
/*     */     {
/* 246 */       return p_getFaceQuadsGrassPath_4_;
/*     */     }
/* 248 */     if (Config.isBetterGrassFancy())
/*     */     {
/* 250 */       return (getBlockAt(p_getFaceQuadsGrassPath_2_.down(), p_getFaceQuadsGrassPath_3_, p_getFaceQuadsGrassPath_0_) == Blocks.GRASS_PATH) ? modelGrassPath.getQuads(p_getFaceQuadsGrassPath_1_, p_getFaceQuadsGrassPath_3_, 0L) : p_getFaceQuadsGrassPath_4_;
/*     */     }
/*     */ 
/*     */     
/* 254 */     return modelGrassPath.getQuads(p_getFaceQuadsGrassPath_1_, p_getFaceQuadsGrassPath_3_, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List getFaceQuadsDirt(IBlockAccess p_getFaceQuadsDirt_0_, IBlockState p_getFaceQuadsDirt_1_, BlockPos p_getFaceQuadsDirt_2_, EnumFacing p_getFaceQuadsDirt_3_, List p_getFaceQuadsDirt_4_) {
/* 260 */     Block block = getBlockAt(p_getFaceQuadsDirt_2_, EnumFacing.UP, p_getFaceQuadsDirt_0_);
/*     */     
/* 262 */     if (p_getFaceQuadsDirt_1_.getValue((IProperty)BlockDirt.VARIANT) != BlockDirt.DirtType.PODZOL) {
/*     */       
/* 264 */       if (block == Blocks.GRASS_PATH)
/*     */       {
/* 266 */         return (betterGrassPath && getBlockAt(p_getFaceQuadsDirt_2_, p_getFaceQuadsDirt_3_, p_getFaceQuadsDirt_0_) == Blocks.GRASS_PATH) ? modelCubeGrassPath.getQuads(p_getFaceQuadsDirt_1_, p_getFaceQuadsDirt_3_, 0L) : p_getFaceQuadsDirt_4_;
/*     */       }
/*     */ 
/*     */       
/* 270 */       return p_getFaceQuadsDirt_4_;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 275 */     boolean flag = !(block != Blocks.SNOW && block != Blocks.SNOW_LAYER);
/*     */     
/* 277 */     if (Config.isBetterGrassFancy()) {
/*     */       
/* 279 */       if (flag)
/*     */       {
/* 281 */         if (betterPodzolSnow && getBlockAt(p_getFaceQuadsDirt_2_, p_getFaceQuadsDirt_3_, p_getFaceQuadsDirt_0_) == Blocks.SNOW_LAYER)
/*     */         {
/* 283 */           return modelCubeSnow.getQuads(p_getFaceQuadsDirt_1_, p_getFaceQuadsDirt_3_, 0L);
/*     */         }
/*     */       }
/* 286 */       else if (betterPodzol)
/*     */       {
/* 288 */         BlockPos blockpos = p_getFaceQuadsDirt_2_.down().offset(p_getFaceQuadsDirt_3_);
/* 289 */         IBlockState iblockstate = p_getFaceQuadsDirt_0_.getBlockState(blockpos);
/*     */         
/* 291 */         if (iblockstate.getBlock() == Blocks.DIRT && iblockstate.getValue((IProperty)BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL)
/*     */         {
/* 293 */           return modelCubePodzol.getQuads(p_getFaceQuadsDirt_1_, p_getFaceQuadsDirt_3_, 0L);
/*     */         }
/*     */       }
/*     */     
/* 297 */     } else if (flag) {
/*     */       
/* 299 */       if (betterPodzolSnow)
/*     */       {
/* 301 */         return modelCubeSnow.getQuads(p_getFaceQuadsDirt_1_, p_getFaceQuadsDirt_3_, 0L);
/*     */       }
/*     */     }
/* 304 */     else if (betterPodzol) {
/*     */       
/* 306 */       return modelCubePodzol.getQuads(p_getFaceQuadsDirt_1_, p_getFaceQuadsDirt_3_, 0L);
/*     */     } 
/*     */     
/* 309 */     return p_getFaceQuadsDirt_4_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static List getFaceQuadsGrass(IBlockAccess p_getFaceQuadsGrass_0_, IBlockState p_getFaceQuadsGrass_1_, BlockPos p_getFaceQuadsGrass_2_, EnumFacing p_getFaceQuadsGrass_3_, List p_getFaceQuadsGrass_4_) {
/* 315 */     Block block = p_getFaceQuadsGrass_0_.getBlockState(p_getFaceQuadsGrass_2_.up()).getBlock();
/* 316 */     boolean flag = !(block != Blocks.SNOW && block != Blocks.SNOW_LAYER);
/*     */     
/* 318 */     if (Config.isBetterGrassFancy()) {
/*     */       
/* 320 */       if (flag)
/*     */       {
/* 322 */         if (betterGrassSnow && getBlockAt(p_getFaceQuadsGrass_2_, p_getFaceQuadsGrass_3_, p_getFaceQuadsGrass_0_) == Blocks.SNOW_LAYER)
/*     */         {
/* 324 */           return modelCubeSnow.getQuads(p_getFaceQuadsGrass_1_, p_getFaceQuadsGrass_3_, 0L);
/*     */         }
/*     */       }
/* 327 */       else if (betterGrass && getBlockAt(p_getFaceQuadsGrass_2_.down(), p_getFaceQuadsGrass_3_, p_getFaceQuadsGrass_0_) == Blocks.GRASS)
/*     */       {
/* 329 */         return modelCubeGrass.getQuads(p_getFaceQuadsGrass_1_, p_getFaceQuadsGrass_3_, 0L);
/*     */       }
/*     */     
/* 332 */     } else if (flag) {
/*     */       
/* 334 */       if (betterGrassSnow)
/*     */       {
/* 336 */         return modelCubeSnow.getQuads(p_getFaceQuadsGrass_1_, p_getFaceQuadsGrass_3_, 0L);
/*     */       }
/*     */     }
/* 339 */     else if (betterGrass) {
/*     */       
/* 341 */       return modelCubeGrass.getQuads(p_getFaceQuadsGrass_1_, p_getFaceQuadsGrass_3_, 0L);
/*     */     } 
/*     */     
/* 344 */     return p_getFaceQuadsGrass_4_;
/*     */   }
/*     */ 
/*     */   
/*     */   private static Block getBlockAt(BlockPos p_getBlockAt_0_, EnumFacing p_getBlockAt_1_, IBlockAccess p_getBlockAt_2_) {
/* 349 */     BlockPos blockpos = p_getBlockAt_0_.offset(p_getBlockAt_1_);
/* 350 */     Block block = p_getBlockAt_2_.getBlockState(blockpos).getBlock();
/* 351 */     return block;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean getBoolean(Properties p_getBoolean_0_, String p_getBoolean_1_, boolean p_getBoolean_2_) {
/* 356 */     String s = p_getBoolean_0_.getProperty(p_getBoolean_1_);
/* 357 */     return (s == null) ? p_getBoolean_2_ : Boolean.parseBoolean(s);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\BetterGrass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */