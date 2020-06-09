/*      */ package optifine;
/*      */ 
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.EnumMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockPane;
/*      */ import net.minecraft.block.BlockStainedGlassPane;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.BlockStateBase;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.renderer.block.model.BakedQuad;
/*      */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.biome.Biome;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ConnectedTextures
/*      */ {
/*   38 */   private static Map[] spriteQuadMaps = null;
/*   39 */   private static Map[] spriteQuadFullMaps = null;
/*   40 */   private static Map[][] spriteQuadCompactMaps = null;
/*   41 */   private static ConnectedProperties[][] blockProperties = null;
/*   42 */   private static ConnectedProperties[][] tileProperties = null;
/*      */   private static boolean multipass = false;
/*      */   protected static final int UNKNOWN = -1;
/*      */   protected static final int Y_NEG_DOWN = 0;
/*      */   protected static final int Y_POS_UP = 1;
/*      */   protected static final int Z_NEG_NORTH = 2;
/*      */   protected static final int Z_POS_SOUTH = 3;
/*      */   protected static final int X_NEG_WEST = 4;
/*      */   protected static final int X_POS_EAST = 5;
/*      */   private static final int Y_AXIS = 0;
/*      */   private static final int Z_AXIS = 1;
/*      */   private static final int X_AXIS = 2;
/*   54 */   public static final IBlockState AIR_DEFAULT_STATE = Blocks.AIR.getDefaultState();
/*   55 */   private static TextureAtlasSprite emptySprite = null;
/*   56 */   private static final BlockDir[] SIDES_Y_NEG_DOWN = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.NORTH, BlockDir.SOUTH };
/*   57 */   private static final BlockDir[] SIDES_Y_POS_UP = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.SOUTH, BlockDir.NORTH };
/*   58 */   private static final BlockDir[] SIDES_Z_NEG_NORTH = new BlockDir[] { BlockDir.EAST, BlockDir.WEST, BlockDir.DOWN, BlockDir.UP };
/*   59 */   private static final BlockDir[] SIDES_Z_POS_SOUTH = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.DOWN, BlockDir.UP };
/*   60 */   private static final BlockDir[] SIDES_X_NEG_WEST = new BlockDir[] { BlockDir.NORTH, BlockDir.SOUTH, BlockDir.DOWN, BlockDir.UP };
/*   61 */   private static final BlockDir[] SIDES_X_POS_EAST = new BlockDir[] { BlockDir.SOUTH, BlockDir.NORTH, BlockDir.DOWN, BlockDir.UP };
/*   62 */   private static final BlockDir[] SIDES_Z_NEG_NORTH_Z_AXIS = new BlockDir[] { BlockDir.WEST, BlockDir.EAST, BlockDir.UP, BlockDir.DOWN };
/*   63 */   private static final BlockDir[] SIDES_X_POS_EAST_X_AXIS = new BlockDir[] { BlockDir.NORTH, BlockDir.SOUTH, BlockDir.UP, BlockDir.DOWN };
/*   64 */   private static final BlockDir[] EDGES_Y_NEG_DOWN = new BlockDir[] { BlockDir.NORTH_EAST, BlockDir.NORTH_WEST, BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST };
/*   65 */   private static final BlockDir[] EDGES_Y_POS_UP = new BlockDir[] { BlockDir.SOUTH_EAST, BlockDir.SOUTH_WEST, BlockDir.NORTH_EAST, BlockDir.NORTH_WEST };
/*   66 */   private static final BlockDir[] EDGES_Z_NEG_NORTH = new BlockDir[] { BlockDir.DOWN_WEST, BlockDir.DOWN_EAST, BlockDir.UP_WEST, BlockDir.UP_EAST };
/*   67 */   private static final BlockDir[] EDGES_Z_POS_SOUTH = new BlockDir[] { BlockDir.DOWN_EAST, BlockDir.DOWN_WEST, BlockDir.UP_EAST, BlockDir.UP_WEST };
/*   68 */   private static final BlockDir[] EDGES_X_NEG_WEST = new BlockDir[] { BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH, BlockDir.UP_SOUTH, BlockDir.UP_NORTH };
/*   69 */   private static final BlockDir[] EDGES_X_POS_EAST = new BlockDir[] { BlockDir.DOWN_NORTH, BlockDir.DOWN_SOUTH, BlockDir.UP_NORTH, BlockDir.UP_SOUTH };
/*   70 */   private static final BlockDir[] EDGES_Z_NEG_NORTH_Z_AXIS = new BlockDir[] { BlockDir.UP_EAST, BlockDir.UP_WEST, BlockDir.DOWN_EAST, BlockDir.DOWN_WEST };
/*   71 */   private static final BlockDir[] EDGES_X_POS_EAST_X_AXIS = new BlockDir[] { BlockDir.UP_SOUTH, BlockDir.UP_NORTH, BlockDir.DOWN_SOUTH, BlockDir.DOWN_NORTH };
/*      */ 
/*      */   
/*      */   public static synchronized BakedQuad[] getConnectedTexture(IBlockAccess p_getConnectedTexture_0_, IBlockState p_getConnectedTexture_1_, BlockPos p_getConnectedTexture_2_, BakedQuad p_getConnectedTexture_3_, RenderEnv p_getConnectedTexture_4_) {
/*   75 */     TextureAtlasSprite textureatlassprite = p_getConnectedTexture_3_.getSprite();
/*      */     
/*   77 */     if (textureatlassprite == null)
/*      */     {
/*   79 */       return p_getConnectedTexture_4_.getArrayQuadsCtm(p_getConnectedTexture_3_);
/*      */     }
/*      */ 
/*      */     
/*   83 */     Block block = p_getConnectedTexture_1_.getBlock();
/*      */     
/*   85 */     if (skipConnectedTexture(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, p_getConnectedTexture_3_, p_getConnectedTexture_4_)) {
/*      */       
/*   87 */       p_getConnectedTexture_3_ = getQuad(emptySprite, p_getConnectedTexture_3_);
/*   88 */       return p_getConnectedTexture_4_.getArrayQuadsCtm(p_getConnectedTexture_3_);
/*      */     } 
/*      */ 
/*      */     
/*   92 */     EnumFacing enumfacing = p_getConnectedTexture_3_.getFace();
/*   93 */     BakedQuad[] abakedquad = getConnectedTextureMultiPass(p_getConnectedTexture_0_, p_getConnectedTexture_1_, p_getConnectedTexture_2_, enumfacing, p_getConnectedTexture_3_, p_getConnectedTexture_4_);
/*   94 */     return abakedquad;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean skipConnectedTexture(IBlockAccess p_skipConnectedTexture_0_, IBlockState p_skipConnectedTexture_1_, BlockPos p_skipConnectedTexture_2_, BakedQuad p_skipConnectedTexture_3_, RenderEnv p_skipConnectedTexture_4_) {
/*  101 */     Block block = p_skipConnectedTexture_1_.getBlock();
/*      */     
/*  103 */     if (block instanceof BlockPane) {
/*      */       
/*  105 */       EnumFacing enumfacing = p_skipConnectedTexture_3_.getFace();
/*      */       
/*  107 */       if (enumfacing != EnumFacing.UP && enumfacing != EnumFacing.DOWN)
/*      */       {
/*  109 */         return false;
/*      */       }
/*      */       
/*  112 */       if (!p_skipConnectedTexture_3_.isFaceQuad())
/*      */       {
/*  114 */         return false;
/*      */       }
/*      */       
/*  117 */       BlockPos blockpos = p_skipConnectedTexture_2_.offset(p_skipConnectedTexture_3_.getFace());
/*  118 */       IBlockState iblockstate = p_skipConnectedTexture_0_.getBlockState(blockpos);
/*      */       
/*  120 */       if (iblockstate.getBlock() != block)
/*      */       {
/*  122 */         return false;
/*      */       }
/*      */       
/*  125 */       if (block == Blocks.STAINED_GLASS_PANE && iblockstate.getValue((IProperty)BlockStainedGlassPane.COLOR) != p_skipConnectedTexture_1_.getValue((IProperty)BlockStainedGlassPane.COLOR))
/*      */       {
/*  127 */         return false;
/*      */       }
/*      */       
/*  130 */       iblockstate = iblockstate.getActualState(p_skipConnectedTexture_0_, blockpos);
/*  131 */       double d0 = p_skipConnectedTexture_3_.getMidX();
/*      */       
/*  133 */       if (d0 < 0.4D) {
/*      */         
/*  135 */         if (((Boolean)iblockstate.getValue((IProperty)BlockPane.WEST)).booleanValue())
/*      */         {
/*  137 */           return true;
/*      */         }
/*      */       }
/*  140 */       else if (d0 > 0.6D) {
/*      */         
/*  142 */         if (((Boolean)iblockstate.getValue((IProperty)BlockPane.EAST)).booleanValue())
/*      */         {
/*  144 */           return true;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  149 */         double d1 = p_skipConnectedTexture_3_.getMidZ();
/*      */         
/*  151 */         if (d1 < 0.4D) {
/*      */           
/*  153 */           if (((Boolean)iblockstate.getValue((IProperty)BlockPane.NORTH)).booleanValue())
/*      */           {
/*  155 */             return true;
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  160 */           if (d1 <= 0.6D)
/*      */           {
/*  162 */             return true;
/*      */           }
/*      */           
/*  165 */           if (((Boolean)iblockstate.getValue((IProperty)BlockPane.SOUTH)).booleanValue())
/*      */           {
/*  167 */             return true;
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  173 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected static BakedQuad[] getQuads(TextureAtlasSprite p_getQuads_0_, BakedQuad p_getQuads_1_, RenderEnv p_getQuads_2_) {
/*  178 */     if (p_getQuads_0_ == null)
/*      */     {
/*  180 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  184 */     BakedQuad bakedquad = getQuad(p_getQuads_0_, p_getQuads_1_);
/*  185 */     BakedQuad[] abakedquad = p_getQuads_2_.getArrayQuadsCtm(bakedquad);
/*  186 */     return abakedquad;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static BakedQuad getQuad(TextureAtlasSprite p_getQuad_0_, BakedQuad p_getQuad_1_) {
/*  192 */     if (spriteQuadMaps == null)
/*      */     {
/*  194 */       return p_getQuad_1_;
/*      */     }
/*      */ 
/*      */     
/*  198 */     int i = p_getQuad_0_.getIndexInMap();
/*      */     
/*  200 */     if (i >= 0 && i < spriteQuadMaps.length) {
/*      */       
/*  202 */       Map<Object, Object> map = spriteQuadMaps[i];
/*      */       
/*  204 */       if (map == null) {
/*      */         
/*  206 */         map = new IdentityHashMap<>(1);
/*  207 */         spriteQuadMaps[i] = map;
/*      */       } 
/*      */       
/*  210 */       BakedQuad bakedquad = (BakedQuad)map.get(p_getQuad_1_);
/*      */       
/*  212 */       if (bakedquad == null) {
/*      */         
/*  214 */         bakedquad = makeSpriteQuad(p_getQuad_1_, p_getQuad_0_);
/*  215 */         map.put(p_getQuad_1_, bakedquad);
/*      */       } 
/*      */       
/*  218 */       return bakedquad;
/*      */     } 
/*      */ 
/*      */     
/*  222 */     return p_getQuad_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BakedQuad getQuadFull(TextureAtlasSprite p_getQuadFull_0_, BakedQuad p_getQuadFull_1_, int p_getQuadFull_2_) {
/*  229 */     if (spriteQuadFullMaps == null)
/*      */     {
/*  231 */       return p_getQuadFull_1_;
/*      */     }
/*      */ 
/*      */     
/*  235 */     int i = p_getQuadFull_0_.getIndexInMap();
/*      */     
/*  237 */     if (i >= 0 && i < spriteQuadFullMaps.length) {
/*      */       
/*  239 */       Map<EnumFacing, Object> map = spriteQuadFullMaps[i];
/*      */       
/*  241 */       if (map == null) {
/*      */         
/*  243 */         map = new EnumMap<>(EnumFacing.class);
/*  244 */         spriteQuadFullMaps[i] = map;
/*      */       } 
/*      */       
/*  247 */       EnumFacing enumfacing = p_getQuadFull_1_.getFace();
/*  248 */       BakedQuad bakedquad = (BakedQuad)map.get(enumfacing);
/*      */       
/*  250 */       if (bakedquad == null) {
/*      */         
/*  252 */         bakedquad = BlockModelUtils.makeBakedQuad(enumfacing, p_getQuadFull_0_, p_getQuadFull_2_);
/*  253 */         map.put(enumfacing, bakedquad);
/*      */       } 
/*      */       
/*  256 */       return bakedquad;
/*      */     } 
/*      */ 
/*      */     
/*  260 */     return p_getQuadFull_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static BakedQuad makeSpriteQuad(BakedQuad p_makeSpriteQuad_0_, TextureAtlasSprite p_makeSpriteQuad_1_) {
/*  267 */     int[] aint = (int[])p_makeSpriteQuad_0_.getVertexData().clone();
/*  268 */     TextureAtlasSprite textureatlassprite = p_makeSpriteQuad_0_.getSprite();
/*      */     
/*  270 */     for (int i = 0; i < 4; i++)
/*      */     {
/*  272 */       fixVertex(aint, i, textureatlassprite, p_makeSpriteQuad_1_);
/*      */     }
/*      */     
/*  275 */     BakedQuad bakedquad = new BakedQuad(aint, p_makeSpriteQuad_0_.getTintIndex(), p_makeSpriteQuad_0_.getFace(), p_makeSpriteQuad_1_);
/*  276 */     return bakedquad;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void fixVertex(int[] p_fixVertex_0_, int p_fixVertex_1_, TextureAtlasSprite p_fixVertex_2_, TextureAtlasSprite p_fixVertex_3_) {
/*  281 */     int i = p_fixVertex_0_.length / 4;
/*  282 */     int j = i * p_fixVertex_1_;
/*  283 */     float f = Float.intBitsToFloat(p_fixVertex_0_[j + 4]);
/*  284 */     float f1 = Float.intBitsToFloat(p_fixVertex_0_[j + 4 + 1]);
/*  285 */     double d0 = p_fixVertex_2_.getSpriteU16(f);
/*  286 */     double d1 = p_fixVertex_2_.getSpriteV16(f1);
/*  287 */     p_fixVertex_0_[j + 4] = Float.floatToRawIntBits(p_fixVertex_3_.getInterpolatedU(d0));
/*  288 */     p_fixVertex_0_[j + 4 + 1] = Float.floatToRawIntBits(p_fixVertex_3_.getInterpolatedV(d1));
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureMultiPass(IBlockAccess p_getConnectedTextureMultiPass_0_, IBlockState p_getConnectedTextureMultiPass_1_, BlockPos p_getConnectedTextureMultiPass_2_, EnumFacing p_getConnectedTextureMultiPass_3_, BakedQuad p_getConnectedTextureMultiPass_4_, RenderEnv p_getConnectedTextureMultiPass_5_) {
/*  293 */     BakedQuad[] abakedquad = getConnectedTextureSingle(p_getConnectedTextureMultiPass_0_, p_getConnectedTextureMultiPass_1_, p_getConnectedTextureMultiPass_2_, p_getConnectedTextureMultiPass_3_, p_getConnectedTextureMultiPass_4_, true, 0, p_getConnectedTextureMultiPass_5_);
/*      */     
/*  295 */     if (!multipass)
/*      */     {
/*  297 */       return abakedquad;
/*      */     }
/*  299 */     if (abakedquad.length == 1 && abakedquad[0] == p_getConnectedTextureMultiPass_4_)
/*      */     {
/*  301 */       return abakedquad;
/*      */     }
/*      */ 
/*      */     
/*  305 */     List<BakedQuad> list = p_getConnectedTextureMultiPass_5_.getListQuadsCtmMultipass(abakedquad);
/*      */     
/*  307 */     for (int i = 0; i < list.size(); i++) {
/*      */       
/*  309 */       BakedQuad bakedquad = list.get(i);
/*  310 */       BakedQuad bakedquad1 = bakedquad;
/*      */       
/*  312 */       for (int j = 0; j < 3; j++) {
/*      */         
/*  314 */         BakedQuad[] abakedquad1 = getConnectedTextureSingle(p_getConnectedTextureMultiPass_0_, p_getConnectedTextureMultiPass_1_, p_getConnectedTextureMultiPass_2_, p_getConnectedTextureMultiPass_3_, bakedquad1, false, j + 1, p_getConnectedTextureMultiPass_5_);
/*      */         
/*  316 */         if (abakedquad1.length != 1 || abakedquad1[0] == bakedquad1) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/*  321 */         bakedquad1 = abakedquad1[0];
/*      */       } 
/*      */       
/*  324 */       list.set(i, bakedquad1);
/*      */     } 
/*      */     
/*  327 */     for (int k = 0; k < abakedquad.length; k++)
/*      */     {
/*  329 */       abakedquad[k] = list.get(k);
/*      */     }
/*      */     
/*  332 */     return abakedquad;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static BakedQuad[] getConnectedTextureSingle(IBlockAccess p_getConnectedTextureSingle_0_, IBlockState p_getConnectedTextureSingle_1_, BlockPos p_getConnectedTextureSingle_2_, EnumFacing p_getConnectedTextureSingle_3_, BakedQuad p_getConnectedTextureSingle_4_, boolean p_getConnectedTextureSingle_5_, int p_getConnectedTextureSingle_6_, RenderEnv p_getConnectedTextureSingle_7_) {
/*  338 */     Block block = p_getConnectedTextureSingle_1_.getBlock();
/*      */     
/*  340 */     if (!(p_getConnectedTextureSingle_1_ instanceof BlockStateBase))
/*      */     {
/*  342 */       return p_getConnectedTextureSingle_7_.getArrayQuadsCtm(p_getConnectedTextureSingle_4_);
/*      */     }
/*      */ 
/*      */     
/*  346 */     BlockStateBase blockstatebase = (BlockStateBase)p_getConnectedTextureSingle_1_;
/*  347 */     TextureAtlasSprite textureatlassprite = p_getConnectedTextureSingle_4_.getSprite();
/*      */     
/*  349 */     if (tileProperties != null) {
/*      */       
/*  351 */       int i = textureatlassprite.getIndexInMap();
/*      */       
/*  353 */       if (i >= 0 && i < tileProperties.length) {
/*      */         
/*  355 */         ConnectedProperties[] aconnectedproperties = tileProperties[i];
/*      */         
/*  357 */         if (aconnectedproperties != null) {
/*      */           
/*  359 */           int j = getSide(p_getConnectedTextureSingle_3_);
/*      */           
/*  361 */           for (int k = 0; k < aconnectedproperties.length; k++) {
/*      */             
/*  363 */             ConnectedProperties connectedproperties = aconnectedproperties[k];
/*      */             
/*  365 */             if (connectedproperties != null && connectedproperties.matchesBlockId(blockstatebase.getBlockId())) {
/*      */               
/*  367 */               BakedQuad[] abakedquad = getConnectedTexture(connectedproperties, p_getConnectedTextureSingle_0_, blockstatebase, p_getConnectedTextureSingle_2_, j, p_getConnectedTextureSingle_4_, p_getConnectedTextureSingle_6_, p_getConnectedTextureSingle_7_);
/*      */               
/*  369 */               if (abakedquad != null)
/*      */               {
/*  371 */                 return abakedquad;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  379 */     if (blockProperties != null && p_getConnectedTextureSingle_5_) {
/*      */       
/*  381 */       int l = p_getConnectedTextureSingle_7_.getBlockId();
/*      */       
/*  383 */       if (l >= 0 && l < blockProperties.length) {
/*      */         
/*  385 */         ConnectedProperties[] aconnectedproperties1 = blockProperties[l];
/*      */         
/*  387 */         if (aconnectedproperties1 != null) {
/*      */           
/*  389 */           int i1 = getSide(p_getConnectedTextureSingle_3_);
/*      */           
/*  391 */           for (int j1 = 0; j1 < aconnectedproperties1.length; j1++) {
/*      */             
/*  393 */             ConnectedProperties connectedproperties1 = aconnectedproperties1[j1];
/*      */             
/*  395 */             if (connectedproperties1 != null && connectedproperties1.matchesIcon(textureatlassprite)) {
/*      */               
/*  397 */               BakedQuad[] abakedquad1 = getConnectedTexture(connectedproperties1, p_getConnectedTextureSingle_0_, blockstatebase, p_getConnectedTextureSingle_2_, i1, p_getConnectedTextureSingle_4_, p_getConnectedTextureSingle_6_, p_getConnectedTextureSingle_7_);
/*      */               
/*  399 */               if (abakedquad1 != null)
/*      */               {
/*  401 */                 return abakedquad1;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  409 */     return p_getConnectedTextureSingle_7_.getArrayQuadsCtm(p_getConnectedTextureSingle_4_);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getSide(EnumFacing p_getSide_0_) {
/*  415 */     if (p_getSide_0_ == null)
/*      */     {
/*  417 */       return -1;
/*      */     }
/*      */ 
/*      */     
/*  421 */     switch (p_getSide_0_) {
/*      */       
/*      */       case null:
/*  424 */         return 0;
/*      */       
/*      */       case UP:
/*  427 */         return 1;
/*      */       
/*      */       case EAST:
/*  430 */         return 5;
/*      */       
/*      */       case WEST:
/*  433 */         return 4;
/*      */       
/*      */       case NORTH:
/*  436 */         return 2;
/*      */       
/*      */       case SOUTH:
/*  439 */         return 3;
/*      */     } 
/*      */     
/*  442 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static EnumFacing getFacing(int p_getFacing_0_) {
/*  449 */     switch (p_getFacing_0_) {
/*      */       
/*      */       case 0:
/*  452 */         return EnumFacing.DOWN;
/*      */       
/*      */       case 1:
/*  455 */         return EnumFacing.UP;
/*      */       
/*      */       case 2:
/*  458 */         return EnumFacing.NORTH;
/*      */       
/*      */       case 3:
/*  461 */         return EnumFacing.SOUTH;
/*      */       
/*      */       case 4:
/*  464 */         return EnumFacing.WEST;
/*      */       
/*      */       case 5:
/*  467 */         return EnumFacing.EAST;
/*      */     } 
/*      */     
/*  470 */     return EnumFacing.UP;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTexture(ConnectedProperties p_getConnectedTexture_0_, IBlockAccess p_getConnectedTexture_1_, BlockStateBase p_getConnectedTexture_2_, BlockPos p_getConnectedTexture_3_, int p_getConnectedTexture_4_, BakedQuad p_getConnectedTexture_5_, int p_getConnectedTexture_6_, RenderEnv p_getConnectedTexture_7_) {
/*  476 */     int i = 0;
/*  477 */     int j = p_getConnectedTexture_2_.getMetadata();
/*  478 */     int k = j;
/*  479 */     Block block = p_getConnectedTexture_2_.getBlock();
/*      */     
/*  481 */     if (block instanceof net.minecraft.block.BlockRotatedPillar) {
/*      */       
/*  483 */       i = getWoodAxis(p_getConnectedTexture_4_, j);
/*      */       
/*  485 */       if (p_getConnectedTexture_0_.getMetadataMax() <= 3)
/*      */       {
/*  487 */         k = j & 0x3;
/*      */       }
/*      */     } 
/*      */     
/*  491 */     if (block instanceof net.minecraft.block.BlockQuartz) {
/*      */       
/*  493 */       i = getQuartzAxis(p_getConnectedTexture_4_, j);
/*      */       
/*  495 */       if (p_getConnectedTexture_0_.getMetadataMax() <= 2 && k > 2)
/*      */       {
/*  497 */         k = 2;
/*      */       }
/*      */     } 
/*      */     
/*  501 */     if (!p_getConnectedTexture_0_.matchesBlock(p_getConnectedTexture_2_.getBlockId(), k))
/*      */     {
/*  503 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  507 */     if (p_getConnectedTexture_4_ >= 0 && p_getConnectedTexture_0_.faces != 63) {
/*      */       
/*  509 */       int l = p_getConnectedTexture_4_;
/*      */       
/*  511 */       if (i != 0)
/*      */       {
/*  513 */         l = fixSideByAxis(p_getConnectedTexture_4_, i);
/*      */       }
/*      */       
/*  516 */       if ((1 << l & p_getConnectedTexture_0_.faces) == 0)
/*      */       {
/*  518 */         return null;
/*      */       }
/*      */     } 
/*      */     
/*  522 */     int i1 = p_getConnectedTexture_3_.getY();
/*      */     
/*  524 */     if (i1 >= p_getConnectedTexture_0_.minHeight && i1 <= p_getConnectedTexture_0_.maxHeight) {
/*      */       
/*  526 */       if (p_getConnectedTexture_0_.biomes != null) {
/*      */         
/*  528 */         Biome biome = p_getConnectedTexture_1_.getBiome(p_getConnectedTexture_3_);
/*      */         
/*  530 */         if (!p_getConnectedTexture_0_.matchesBiome(biome))
/*      */         {
/*  532 */           return null;
/*      */         }
/*      */       } 
/*      */       
/*  536 */       TextureAtlasSprite textureatlassprite = p_getConnectedTexture_5_.getSprite();
/*      */       
/*  538 */       switch (p_getConnectedTexture_0_.method) {
/*      */         
/*      */         case 1:
/*  541 */           return getQuads(getConnectedTextureCtm(p_getConnectedTexture_0_, p_getConnectedTexture_1_, (IBlockState)p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, textureatlassprite, j, p_getConnectedTexture_7_), p_getConnectedTexture_5_, p_getConnectedTexture_7_);
/*      */         
/*      */         case 2:
/*  544 */           return getQuads(getConnectedTextureHorizontal(p_getConnectedTexture_0_, p_getConnectedTexture_1_, (IBlockState)p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, textureatlassprite, j), p_getConnectedTexture_5_, p_getConnectedTexture_7_);
/*      */         
/*      */         case 3:
/*  547 */           return getQuads(getConnectedTextureTop(p_getConnectedTexture_0_, p_getConnectedTexture_1_, (IBlockState)p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, textureatlassprite, j), p_getConnectedTexture_5_, p_getConnectedTexture_7_);
/*      */         
/*      */         case 4:
/*  550 */           return getQuads(getConnectedTextureRandom(p_getConnectedTexture_0_, p_getConnectedTexture_3_, p_getConnectedTexture_4_), p_getConnectedTexture_5_, p_getConnectedTexture_7_);
/*      */         
/*      */         case 5:
/*  553 */           return getQuads(getConnectedTextureRepeat(p_getConnectedTexture_0_, p_getConnectedTexture_3_, p_getConnectedTexture_4_), p_getConnectedTexture_5_, p_getConnectedTexture_7_);
/*      */         
/*      */         case 6:
/*  556 */           return getQuads(getConnectedTextureVertical(p_getConnectedTexture_0_, p_getConnectedTexture_1_, (IBlockState)p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, textureatlassprite, j), p_getConnectedTexture_5_, p_getConnectedTexture_7_);
/*      */         
/*      */         case 7:
/*  559 */           return getQuads(getConnectedTextureFixed(p_getConnectedTexture_0_), p_getConnectedTexture_5_, p_getConnectedTexture_7_);
/*      */         
/*      */         case 8:
/*  562 */           return getQuads(getConnectedTextureHorizontalVertical(p_getConnectedTexture_0_, p_getConnectedTexture_1_, (IBlockState)p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, textureatlassprite, j), p_getConnectedTexture_5_, p_getConnectedTexture_7_);
/*      */         
/*      */         case 9:
/*  565 */           return getQuads(getConnectedTextureVerticalHorizontal(p_getConnectedTexture_0_, p_getConnectedTexture_1_, (IBlockState)p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, textureatlassprite, j), p_getConnectedTexture_5_, p_getConnectedTexture_7_);
/*      */         
/*      */         case 10:
/*  568 */           if (p_getConnectedTexture_6_ == 0)
/*      */           {
/*  570 */             return getConnectedTextureCtmCompact(p_getConnectedTexture_0_, p_getConnectedTexture_1_, (IBlockState)p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, p_getConnectedTexture_5_, j, p_getConnectedTexture_7_);
/*      */           }
/*      */         
/*      */         default:
/*  574 */           return null;
/*      */         case 11:
/*      */           break;
/*  577 */       }  return getConnectedTextureOverlay(p_getConnectedTexture_0_, p_getConnectedTexture_1_, (IBlockState)p_getConnectedTexture_2_, p_getConnectedTexture_3_, i, p_getConnectedTexture_4_, p_getConnectedTexture_5_, j, p_getConnectedTexture_7_);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  582 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static int fixSideByAxis(int p_fixSideByAxis_0_, int p_fixSideByAxis_1_) {
/*  589 */     switch (p_fixSideByAxis_1_) {
/*      */       
/*      */       case 0:
/*  592 */         return p_fixSideByAxis_0_;
/*      */       
/*      */       case 1:
/*  595 */         switch (p_fixSideByAxis_0_) {
/*      */           
/*      */           case 0:
/*  598 */             return 2;
/*      */           
/*      */           case 1:
/*  601 */             return 3;
/*      */           
/*      */           case 2:
/*  604 */             return 1;
/*      */           
/*      */           case 3:
/*  607 */             return 0;
/*      */         } 
/*      */         
/*  610 */         return p_fixSideByAxis_0_;
/*      */ 
/*      */       
/*      */       case 2:
/*  614 */         switch (p_fixSideByAxis_0_) {
/*      */           
/*      */           case 0:
/*  617 */             return 4;
/*      */           
/*      */           case 1:
/*  620 */             return 5;
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/*  625 */             return p_fixSideByAxis_0_;
/*      */           
/*      */           case 4:
/*  628 */             return 1;
/*      */           case 5:
/*      */             break;
/*  631 */         }  return 0;
/*      */     } 
/*      */ 
/*      */     
/*  635 */     return p_fixSideByAxis_0_;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getWoodAxis(int p_getWoodAxis_0_, int p_getWoodAxis_1_) {
/*  641 */     int i = (p_getWoodAxis_1_ & 0xC) >> 2;
/*      */     
/*  643 */     switch (i) {
/*      */       
/*      */       case 1:
/*  646 */         return 2;
/*      */       
/*      */       case 2:
/*  649 */         return 1;
/*      */     } 
/*      */     
/*  652 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getQuartzAxis(int p_getQuartzAxis_0_, int p_getQuartzAxis_1_) {
/*  658 */     switch (p_getQuartzAxis_1_) {
/*      */       
/*      */       case 3:
/*  661 */         return 2;
/*      */       
/*      */       case 4:
/*  664 */         return 1;
/*      */     } 
/*      */     
/*  667 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureRandom(ConnectedProperties p_getConnectedTextureRandom_0_, BlockPos p_getConnectedTextureRandom_1_, int p_getConnectedTextureRandom_2_) {
/*  673 */     if (p_getConnectedTextureRandom_0_.tileIcons.length == 1)
/*      */     {
/*  675 */       return p_getConnectedTextureRandom_0_.tileIcons[0];
/*      */     }
/*      */ 
/*      */     
/*  679 */     int i = p_getConnectedTextureRandom_2_ / p_getConnectedTextureRandom_0_.symmetry * p_getConnectedTextureRandom_0_.symmetry;
/*  680 */     int j = Config.getRandom(p_getConnectedTextureRandom_1_, i) & Integer.MAX_VALUE;
/*  681 */     int k = 0;
/*      */     
/*  683 */     if (p_getConnectedTextureRandom_0_.weights == null) {
/*      */       
/*  685 */       k = j % p_getConnectedTextureRandom_0_.tileIcons.length;
/*      */     }
/*      */     else {
/*      */       
/*  689 */       int l = j % p_getConnectedTextureRandom_0_.sumAllWeights;
/*  690 */       int[] aint = p_getConnectedTextureRandom_0_.sumWeights;
/*      */       
/*  692 */       for (int i1 = 0; i1 < aint.length; i1++) {
/*      */         
/*  694 */         if (l < aint[i1]) {
/*      */           
/*  696 */           k = i1;
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     } 
/*  702 */     return p_getConnectedTextureRandom_0_.tileIcons[k];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureFixed(ConnectedProperties p_getConnectedTextureFixed_0_) {
/*  708 */     return p_getConnectedTextureFixed_0_.tileIcons[0];
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureRepeat(ConnectedProperties p_getConnectedTextureRepeat_0_, BlockPos p_getConnectedTextureRepeat_1_, int p_getConnectedTextureRepeat_2_) {
/*  713 */     if (p_getConnectedTextureRepeat_0_.tileIcons.length == 1)
/*      */     {
/*  715 */       return p_getConnectedTextureRepeat_0_.tileIcons[0];
/*      */     }
/*      */ 
/*      */     
/*  719 */     int i = p_getConnectedTextureRepeat_1_.getX();
/*  720 */     int j = p_getConnectedTextureRepeat_1_.getY();
/*  721 */     int k = p_getConnectedTextureRepeat_1_.getZ();
/*  722 */     int l = 0;
/*  723 */     int i1 = 0;
/*      */     
/*  725 */     switch (p_getConnectedTextureRepeat_2_) {
/*      */       
/*      */       case 0:
/*  728 */         l = i;
/*  729 */         i1 = k;
/*      */         break;
/*      */       
/*      */       case 1:
/*  733 */         l = i;
/*  734 */         i1 = k;
/*      */         break;
/*      */       
/*      */       case 2:
/*  738 */         l = -i - 1;
/*  739 */         i1 = -j;
/*      */         break;
/*      */       
/*      */       case 3:
/*  743 */         l = i;
/*  744 */         i1 = -j;
/*      */         break;
/*      */       
/*      */       case 4:
/*  748 */         l = k;
/*  749 */         i1 = -j;
/*      */         break;
/*      */       
/*      */       case 5:
/*  753 */         l = -k - 1;
/*  754 */         i1 = -j; break;
/*  755 */     }  l %= 
/*      */       
/*  757 */       p_getConnectedTextureRepeat_0_.width;
/*  758 */     i1 %= p_getConnectedTextureRepeat_0_.height;
/*      */     
/*  760 */     if (l < 0)
/*      */     {
/*  762 */       l += p_getConnectedTextureRepeat_0_.width;
/*      */     }
/*      */     
/*  765 */     if (i1 < 0)
/*      */     {
/*  767 */       i1 += p_getConnectedTextureRepeat_0_.height;
/*      */     }
/*      */     
/*  770 */     int j1 = i1 * p_getConnectedTextureRepeat_0_.width + l;
/*  771 */     return p_getConnectedTextureRepeat_0_.tileIcons[j1];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureCtm(ConnectedProperties p_getConnectedTextureCtm_0_, IBlockAccess p_getConnectedTextureCtm_1_, IBlockState p_getConnectedTextureCtm_2_, BlockPos p_getConnectedTextureCtm_3_, int p_getConnectedTextureCtm_4_, int p_getConnectedTextureCtm_5_, TextureAtlasSprite p_getConnectedTextureCtm_6_, int p_getConnectedTextureCtm_7_, RenderEnv p_getConnectedTextureCtm_8_) {
/*  777 */     int i = getConnectedTextureCtmIndex(p_getConnectedTextureCtm_0_, p_getConnectedTextureCtm_1_, p_getConnectedTextureCtm_2_, p_getConnectedTextureCtm_3_, p_getConnectedTextureCtm_4_, p_getConnectedTextureCtm_5_, p_getConnectedTextureCtm_6_, p_getConnectedTextureCtm_7_, p_getConnectedTextureCtm_8_);
/*  778 */     return p_getConnectedTextureCtm_0_.tileIcons[i];
/*      */   }
/*      */ 
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureCtmCompact(ConnectedProperties p_getConnectedTextureCtmCompact_0_, IBlockAccess p_getConnectedTextureCtmCompact_1_, IBlockState p_getConnectedTextureCtmCompact_2_, BlockPos p_getConnectedTextureCtmCompact_3_, int p_getConnectedTextureCtmCompact_4_, int p_getConnectedTextureCtmCompact_5_, BakedQuad p_getConnectedTextureCtmCompact_6_, int p_getConnectedTextureCtmCompact_7_, RenderEnv p_getConnectedTextureCtmCompact_8_) {
/*  783 */     TextureAtlasSprite textureatlassprite = p_getConnectedTextureCtmCompact_6_.getSprite();
/*  784 */     int i = getConnectedTextureCtmIndex(p_getConnectedTextureCtmCompact_0_, p_getConnectedTextureCtmCompact_1_, p_getConnectedTextureCtmCompact_2_, p_getConnectedTextureCtmCompact_3_, p_getConnectedTextureCtmCompact_4_, p_getConnectedTextureCtmCompact_5_, textureatlassprite, p_getConnectedTextureCtmCompact_7_, p_getConnectedTextureCtmCompact_8_);
/*  785 */     return ConnectedTexturesCompact.getConnectedTextureCtmCompact(i, p_getConnectedTextureCtmCompact_0_, p_getConnectedTextureCtmCompact_5_, p_getConnectedTextureCtmCompact_6_, p_getConnectedTextureCtmCompact_8_);
/*      */   }
/*      */   
/*      */   private static BakedQuad[] getConnectedTextureOverlay(ConnectedProperties p_getConnectedTextureOverlay_0_, IBlockAccess p_getConnectedTextureOverlay_1_, IBlockState p_getConnectedTextureOverlay_2_, BlockPos p_getConnectedTextureOverlay_3_, int p_getConnectedTextureOverlay_4_, int p_getConnectedTextureOverlay_5_, BakedQuad p_getConnectedTextureOverlay_6_, int p_getConnectedTextureOverlay_7_, RenderEnv p_getConnectedTextureOverlay_8_) {
/*      */     Object dirEdges;
/*  790 */     if (!p_getConnectedTextureOverlay_6_.isFullQuad())
/*      */     {
/*  792 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  796 */     TextureAtlasSprite textureatlassprite = p_getConnectedTextureOverlay_6_.getSprite();
/*  797 */     BlockDir[] ablockdir = getSideDirections(p_getConnectedTextureOverlay_5_, p_getConnectedTextureOverlay_4_);
/*  798 */     boolean[] aboolean = p_getConnectedTextureOverlay_8_.getBorderFlags();
/*      */     
/*  800 */     for (int i = 0; i < 4; i++)
/*      */     {
/*  802 */       aboolean[i] = isNeighbourOverlay(p_getConnectedTextureOverlay_0_, p_getConnectedTextureOverlay_1_, p_getConnectedTextureOverlay_2_, ablockdir[i].offset(p_getConnectedTextureOverlay_3_), p_getConnectedTextureOverlay_5_, textureatlassprite, p_getConnectedTextureOverlay_7_);
/*      */     }
/*      */     
/*  805 */     ListQuadsOverlay listquadsoverlay = p_getConnectedTextureOverlay_8_.getListQuadsOverlay(p_getConnectedTextureOverlay_0_.layer);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  810 */     try { if (!aboolean[0] || !aboolean[1] || !aboolean[2] || !aboolean[3]) {
/*      */         
/*  812 */         if (aboolean[0] && aboolean[1] && aboolean[2]) {
/*      */           
/*  814 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[5], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*  815 */           Object object = null;
/*  816 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  819 */         if (aboolean[0] && aboolean[2] && aboolean[3]) {
/*      */           
/*  821 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[6], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*  822 */           Object object = null;
/*  823 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  826 */         if (aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */           
/*  828 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[12], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*  829 */           Object object = null;
/*  830 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  833 */         if (aboolean[0] && aboolean[1] && aboolean[3]) {
/*      */           
/*  835 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[13], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*  836 */           Object object = null;
/*  837 */           return (BakedQuad[])object;
/*      */         } 
/*      */         
/*  840 */         BlockDir[] ablockdir1 = getEdgeDirections(p_getConnectedTextureOverlay_5_, p_getConnectedTextureOverlay_4_);
/*  841 */         boolean[] aboolean1 = p_getConnectedTextureOverlay_8_.getBorderFlags2();
/*      */         
/*  843 */         for (int j = 0; j < 4; j++)
/*      */         {
/*  845 */           aboolean1[j] = isNeighbourOverlay(p_getConnectedTextureOverlay_0_, p_getConnectedTextureOverlay_1_, p_getConnectedTextureOverlay_2_, ablockdir1[j].offset(p_getConnectedTextureOverlay_3_), p_getConnectedTextureOverlay_5_, textureatlassprite, p_getConnectedTextureOverlay_7_);
/*      */         }
/*      */         
/*  848 */         if (aboolean[1] && aboolean[2]) {
/*      */           
/*  850 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[3], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */           
/*  852 */           if (aboolean1[3])
/*      */           {
/*  854 */             listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[16], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */           }
/*      */           
/*  857 */           Object object4 = null;
/*  858 */           return (BakedQuad[])object4;
/*      */         } 
/*      */         
/*  861 */         if (aboolean[0] && aboolean[2]) {
/*      */           
/*  863 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[4], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */           
/*  865 */           if (aboolean1[2])
/*      */           {
/*  867 */             listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[14], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */           }
/*      */           
/*  870 */           Object object3 = null;
/*  871 */           return (BakedQuad[])object3;
/*      */         } 
/*      */         
/*  874 */         if (aboolean[1] && aboolean[3]) {
/*      */           
/*  876 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[10], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */           
/*  878 */           if (aboolean1[1])
/*      */           {
/*  880 */             listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[2], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */           }
/*      */           
/*  883 */           Object object2 = null;
/*  884 */           return (BakedQuad[])object2;
/*      */         } 
/*      */         
/*  887 */         if (aboolean[0] && aboolean[3]) {
/*      */           
/*  889 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[11], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */           
/*  891 */           if (aboolean1[0])
/*      */           {
/*  893 */             listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[0], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */           }
/*      */           
/*  896 */           Object object1 = null;
/*  897 */           return (BakedQuad[])object1;
/*      */         } 
/*      */         
/*  900 */         boolean[] aboolean2 = p_getConnectedTextureOverlay_8_.getBorderFlags3();
/*      */         
/*  902 */         for (int k = 0; k < 4; k++)
/*      */         {
/*  904 */           aboolean2[k] = isNeighbourMatching(p_getConnectedTextureOverlay_0_, p_getConnectedTextureOverlay_1_, p_getConnectedTextureOverlay_2_, ablockdir[k].offset(p_getConnectedTextureOverlay_3_), p_getConnectedTextureOverlay_5_, textureatlassprite, p_getConnectedTextureOverlay_7_);
/*      */         }
/*      */         
/*  907 */         if (aboolean[0])
/*      */         {
/*  909 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[9], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */         }
/*      */         
/*  912 */         if (aboolean[1])
/*      */         {
/*  914 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[7], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */         }
/*      */         
/*  917 */         if (aboolean[2])
/*      */         {
/*  919 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[1], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */         }
/*      */         
/*  922 */         if (aboolean[3])
/*      */         {
/*  924 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[15], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */         }
/*      */         
/*  927 */         if (aboolean1[0] && (aboolean2[1] || aboolean2[2]) && !aboolean[1] && !aboolean[2])
/*      */         {
/*  929 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[0], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */         }
/*      */         
/*  932 */         if (aboolean1[1] && (aboolean2[0] || aboolean2[2]) && !aboolean[0] && !aboolean[2])
/*      */         {
/*  934 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[2], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */         }
/*      */         
/*  937 */         if (aboolean1[2] && (aboolean2[1] || aboolean2[3]) && !aboolean[1] && !aboolean[3])
/*      */         {
/*  939 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[14], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */         }
/*      */         
/*  942 */         if (aboolean1[3] && (aboolean2[0] || aboolean2[3]) && !aboolean[0] && !aboolean[3])
/*      */         {
/*  944 */           listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[16], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */         }
/*      */         
/*  947 */         Object object5 = null;
/*  948 */         return (BakedQuad[])object5;
/*      */       } 
/*      */       
/*  951 */       listquadsoverlay.addQuad(getQuadFull(p_getConnectedTextureOverlay_0_.tileIcons[8], p_getConnectedTextureOverlay_6_, p_getConnectedTextureOverlay_0_.tintIndex), p_getConnectedTextureOverlay_0_.tintBlockState);
/*      */        }
/*      */     
/*      */     finally
/*      */     
/*  956 */     { if (listquadsoverlay.size() > 0)
/*      */       {
/*  958 */         p_getConnectedTextureOverlay_8_.setOverlaysRendered(true); }  }  if (listquadsoverlay.size() > 0) p_getConnectedTextureOverlay_8_.setOverlaysRendered(true);
/*      */ 
/*      */ 
/*      */     
/*  962 */     return (BakedQuad[])dirEdges;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static BlockDir[] getSideDirections(int p_getSideDirections_0_, int p_getSideDirections_1_) {
/*  968 */     switch (p_getSideDirections_0_) {
/*      */       
/*      */       case 0:
/*  971 */         return SIDES_Y_NEG_DOWN;
/*      */       
/*      */       case 1:
/*  974 */         return SIDES_Y_POS_UP;
/*      */       
/*      */       case 2:
/*  977 */         if (p_getSideDirections_1_ == 1)
/*      */         {
/*  979 */           return SIDES_Z_NEG_NORTH_Z_AXIS;
/*      */         }
/*      */         
/*  982 */         return SIDES_Z_NEG_NORTH;
/*      */       
/*      */       case 3:
/*  985 */         return SIDES_Z_POS_SOUTH;
/*      */       
/*      */       case 4:
/*  988 */         return SIDES_X_NEG_WEST;
/*      */       
/*      */       case 5:
/*  991 */         if (p_getSideDirections_1_ == 2)
/*      */         {
/*  993 */           return SIDES_X_POS_EAST_X_AXIS;
/*      */         }
/*      */         
/*  996 */         return SIDES_X_POS_EAST;
/*      */     } 
/*      */     
/*  999 */     throw new IllegalArgumentException("Unknown side: " + p_getSideDirections_0_);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static BlockDir[] getEdgeDirections(int p_getEdgeDirections_0_, int p_getEdgeDirections_1_) {
/* 1005 */     switch (p_getEdgeDirections_0_) {
/*      */       
/*      */       case 0:
/* 1008 */         return EDGES_Y_NEG_DOWN;
/*      */       
/*      */       case 1:
/* 1011 */         return EDGES_Y_POS_UP;
/*      */       
/*      */       case 2:
/* 1014 */         if (p_getEdgeDirections_1_ == 1)
/*      */         {
/* 1016 */           return EDGES_Z_NEG_NORTH_Z_AXIS;
/*      */         }
/*      */         
/* 1019 */         return EDGES_Z_NEG_NORTH;
/*      */       
/*      */       case 3:
/* 1022 */         return EDGES_Z_POS_SOUTH;
/*      */       
/*      */       case 4:
/* 1025 */         return EDGES_X_NEG_WEST;
/*      */       
/*      */       case 5:
/* 1028 */         if (p_getEdgeDirections_1_ == 2)
/*      */         {
/* 1030 */           return EDGES_X_POS_EAST_X_AXIS;
/*      */         }
/*      */         
/* 1033 */         return EDGES_X_POS_EAST;
/*      */     } 
/*      */     
/* 1036 */     throw new IllegalArgumentException("Unknown side: " + p_getEdgeDirections_0_);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected static Map[][] getSpriteQuadCompactMaps() {
/* 1042 */     return spriteQuadCompactMaps;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getConnectedTextureCtmIndex(ConnectedProperties p_getConnectedTextureCtmIndex_0_, IBlockAccess p_getConnectedTextureCtmIndex_1_, IBlockState p_getConnectedTextureCtmIndex_2_, BlockPos p_getConnectedTextureCtmIndex_3_, int p_getConnectedTextureCtmIndex_4_, int p_getConnectedTextureCtmIndex_5_, TextureAtlasSprite p_getConnectedTextureCtmIndex_6_, int p_getConnectedTextureCtmIndex_7_, RenderEnv p_getConnectedTextureCtmIndex_8_) {
/* 1047 */     boolean[] aboolean = p_getConnectedTextureCtmIndex_8_.getBorderFlags();
/*      */     
/* 1049 */     switch (p_getConnectedTextureCtmIndex_5_) {
/*      */       
/*      */       case 0:
/* 1052 */         aboolean[0] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1053 */         aboolean[1] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1054 */         aboolean[2] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1055 */         aboolean[3] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/*      */         break;
/*      */       
/*      */       case 1:
/* 1059 */         aboolean[0] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1060 */         aboolean[1] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1061 */         aboolean[2] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1062 */         aboolean[3] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/*      */         break;
/*      */       
/*      */       case 2:
/* 1066 */         aboolean[0] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1067 */         aboolean[1] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1068 */         aboolean[2] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1069 */         aboolean[3] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/*      */         
/* 1071 */         if (p_getConnectedTextureCtmIndex_4_ == 1) {
/*      */           
/* 1073 */           switchValues(0, 1, aboolean);
/* 1074 */           switchValues(2, 3, aboolean);
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/* 1080 */         aboolean[0] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1081 */         aboolean[1] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1082 */         aboolean[2] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1083 */         aboolean[3] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/*      */         break;
/*      */       
/*      */       case 4:
/* 1087 */         aboolean[0] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1088 */         aboolean[1] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1089 */         aboolean[2] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1090 */         aboolean[3] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/*      */         break;
/*      */       
/*      */       case 5:
/* 1094 */         aboolean[0] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1095 */         aboolean[1] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1096 */         aboolean[2] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1097 */         aboolean[3] = isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/*      */         
/* 1099 */         if (p_getConnectedTextureCtmIndex_4_ == 2) {
/*      */           
/* 1101 */           switchValues(0, 1, aboolean);
/* 1102 */           switchValues(2, 3, aboolean);
/*      */         } 
/*      */         break;
/*      */     } 
/* 1106 */     int i = 0;
/*      */     
/* 1108 */     if ((aboolean[0] & (aboolean[1] ? 0 : 1) & (aboolean[2] ? 0 : 1) & (aboolean[3] ? 0 : 1)) != 0) {
/*      */       
/* 1110 */       i = 3;
/*      */     }
/* 1112 */     else if (((aboolean[0] ? 0 : 1) & aboolean[1] & (aboolean[2] ? 0 : 1) & (aboolean[3] ? 0 : 1)) != 0) {
/*      */       
/* 1114 */       i = 1;
/*      */     }
/* 1116 */     else if (((aboolean[0] ? 0 : 1) & (aboolean[1] ? 0 : 1) & aboolean[2] & (aboolean[3] ? 0 : 1)) != 0) {
/*      */       
/* 1118 */       i = 12;
/*      */     }
/* 1120 */     else if (((aboolean[0] ? 0 : 1) & (aboolean[1] ? 0 : 1) & (aboolean[2] ? 0 : 1) & aboolean[3]) != 0) {
/*      */       
/* 1122 */       i = 36;
/*      */     }
/* 1124 */     else if ((aboolean[0] & aboolean[1] & (aboolean[2] ? 0 : 1) & (aboolean[3] ? 0 : 1)) != 0) {
/*      */       
/* 1126 */       i = 2;
/*      */     }
/* 1128 */     else if (((aboolean[0] ? 0 : 1) & (aboolean[1] ? 0 : 1) & aboolean[2] & aboolean[3]) != 0) {
/*      */       
/* 1130 */       i = 24;
/*      */     }
/* 1132 */     else if ((aboolean[0] & (aboolean[1] ? 0 : 1) & aboolean[2] & (aboolean[3] ? 0 : 1)) != 0) {
/*      */       
/* 1134 */       i = 15;
/*      */     }
/* 1136 */     else if ((aboolean[0] & (aboolean[1] ? 0 : 1) & (aboolean[2] ? 0 : 1) & aboolean[3]) != 0) {
/*      */       
/* 1138 */       i = 39;
/*      */     }
/* 1140 */     else if (((aboolean[0] ? 0 : 1) & aboolean[1] & aboolean[2] & (aboolean[3] ? 0 : 1)) != 0) {
/*      */       
/* 1142 */       i = 13;
/*      */     }
/* 1144 */     else if (((aboolean[0] ? 0 : 1) & aboolean[1] & (aboolean[2] ? 0 : 1) & aboolean[3]) != 0) {
/*      */       
/* 1146 */       i = 37;
/*      */     }
/* 1148 */     else if (((aboolean[0] ? 0 : 1) & aboolean[1] & aboolean[2] & aboolean[3]) != 0) {
/*      */       
/* 1150 */       i = 25;
/*      */     }
/* 1152 */     else if ((aboolean[0] & (aboolean[1] ? 0 : 1) & aboolean[2] & aboolean[3]) != 0) {
/*      */       
/* 1154 */       i = 27;
/*      */     }
/* 1156 */     else if ((aboolean[0] & aboolean[1] & (aboolean[2] ? 0 : 1) & aboolean[3]) != 0) {
/*      */       
/* 1158 */       i = 38;
/*      */     }
/* 1160 */     else if ((aboolean[0] & aboolean[1] & aboolean[2] & (aboolean[3] ? 0 : 1)) != 0) {
/*      */       
/* 1162 */       i = 14;
/*      */     }
/* 1164 */     else if ((aboolean[0] & aboolean[1] & aboolean[2] & aboolean[3]) != 0) {
/*      */       
/* 1166 */       i = 26;
/*      */     } 
/*      */     
/* 1169 */     if (i == 0)
/*      */     {
/* 1171 */       return i;
/*      */     }
/* 1173 */     if (!Config.isConnectedTexturesFancy())
/*      */     {
/* 1175 */       return i;
/*      */     }
/*      */ 
/*      */     
/* 1179 */     switch (p_getConnectedTextureCtmIndex_5_) {
/*      */       
/*      */       case 0:
/* 1182 */         aboolean[0] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1183 */         aboolean[1] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1184 */         aboolean[2] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1185 */         aboolean[3] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/*      */         break;
/*      */       
/*      */       case 1:
/* 1189 */         aboolean[0] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1190 */         aboolean[1] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1191 */         aboolean[2] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1192 */         aboolean[3] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/*      */         break;
/*      */       
/*      */       case 2:
/* 1196 */         aboolean[0] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1197 */         aboolean[1] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1198 */         aboolean[2] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1199 */         aboolean[3] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/*      */         
/* 1201 */         if (p_getConnectedTextureCtmIndex_4_ == 1) {
/*      */           
/* 1203 */           switchValues(0, 3, aboolean);
/* 1204 */           switchValues(1, 2, aboolean);
/*      */         } 
/*      */         break;
/*      */ 
/*      */       
/*      */       case 3:
/* 1210 */         aboolean[0] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1211 */         aboolean[1] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().down(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1212 */         aboolean[2] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.east().up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1213 */         aboolean[3] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.west().up(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/*      */         break;
/*      */       
/*      */       case 4:
/* 1217 */         aboolean[0] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1218 */         aboolean[1] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1219 */         aboolean[2] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1220 */         aboolean[3] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/*      */         break;
/*      */       
/*      */       case 5:
/* 1224 */         aboolean[0] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1225 */         aboolean[1] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.down().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1226 */         aboolean[2] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up().north(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/* 1227 */         aboolean[3] = !isNeighbour(p_getConnectedTextureCtmIndex_0_, p_getConnectedTextureCtmIndex_1_, p_getConnectedTextureCtmIndex_2_, p_getConnectedTextureCtmIndex_3_.up().south(), p_getConnectedTextureCtmIndex_5_, p_getConnectedTextureCtmIndex_6_, p_getConnectedTextureCtmIndex_7_);
/*      */         
/* 1229 */         if (p_getConnectedTextureCtmIndex_4_ == 2) {
/*      */           
/* 1231 */           switchValues(0, 3, aboolean);
/* 1232 */           switchValues(1, 2, aboolean);
/*      */         } 
/*      */         break;
/*      */     } 
/* 1236 */     if (i == 13 && aboolean[0]) {
/*      */       
/* 1238 */       i = 4;
/*      */     }
/* 1240 */     else if (i == 15 && aboolean[1]) {
/*      */       
/* 1242 */       i = 5;
/*      */     }
/* 1244 */     else if (i == 37 && aboolean[2]) {
/*      */       
/* 1246 */       i = 16;
/*      */     }
/* 1248 */     else if (i == 39 && aboolean[3]) {
/*      */       
/* 1250 */       i = 17;
/*      */     }
/* 1252 */     else if (i == 14 && aboolean[0] && aboolean[1]) {
/*      */       
/* 1254 */       i = 7;
/*      */     }
/* 1256 */     else if (i == 25 && aboolean[0] && aboolean[2]) {
/*      */       
/* 1258 */       i = 6;
/*      */     }
/* 1260 */     else if (i == 27 && aboolean[3] && aboolean[1]) {
/*      */       
/* 1262 */       i = 19;
/*      */     }
/* 1264 */     else if (i == 38 && aboolean[3] && aboolean[2]) {
/*      */       
/* 1266 */       i = 18;
/*      */     }
/* 1268 */     else if (i == 14 && !aboolean[0] && aboolean[1]) {
/*      */       
/* 1270 */       i = 31;
/*      */     }
/* 1272 */     else if (i == 25 && aboolean[0] && !aboolean[2]) {
/*      */       
/* 1274 */       i = 30;
/*      */     }
/* 1276 */     else if (i == 27 && !aboolean[3] && aboolean[1]) {
/*      */       
/* 1278 */       i = 41;
/*      */     }
/* 1280 */     else if (i == 38 && aboolean[3] && !aboolean[2]) {
/*      */       
/* 1282 */       i = 40;
/*      */     }
/* 1284 */     else if (i == 14 && aboolean[0] && !aboolean[1]) {
/*      */       
/* 1286 */       i = 29;
/*      */     }
/* 1288 */     else if (i == 25 && !aboolean[0] && aboolean[2]) {
/*      */       
/* 1290 */       i = 28;
/*      */     }
/* 1292 */     else if (i == 27 && aboolean[3] && !aboolean[1]) {
/*      */       
/* 1294 */       i = 43;
/*      */     }
/* 1296 */     else if (i == 38 && !aboolean[3] && aboolean[2]) {
/*      */       
/* 1298 */       i = 42;
/*      */     }
/* 1300 */     else if (i == 26 && aboolean[0] && aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */       
/* 1302 */       i = 46;
/*      */     }
/* 1304 */     else if (i == 26 && !aboolean[0] && aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */       
/* 1306 */       i = 9;
/*      */     }
/* 1308 */     else if (i == 26 && aboolean[0] && !aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */       
/* 1310 */       i = 21;
/*      */     }
/* 1312 */     else if (i == 26 && aboolean[0] && aboolean[1] && !aboolean[2] && aboolean[3]) {
/*      */       
/* 1314 */       i = 8;
/*      */     }
/* 1316 */     else if (i == 26 && aboolean[0] && aboolean[1] && aboolean[2] && !aboolean[3]) {
/*      */       
/* 1318 */       i = 20;
/*      */     }
/* 1320 */     else if (i == 26 && aboolean[0] && aboolean[1] && !aboolean[2] && !aboolean[3]) {
/*      */       
/* 1322 */       i = 11;
/*      */     }
/* 1324 */     else if (i == 26 && !aboolean[0] && !aboolean[1] && aboolean[2] && aboolean[3]) {
/*      */       
/* 1326 */       i = 22;
/*      */     }
/* 1328 */     else if (i == 26 && !aboolean[0] && aboolean[1] && !aboolean[2] && aboolean[3]) {
/*      */       
/* 1330 */       i = 23;
/*      */     }
/* 1332 */     else if (i == 26 && aboolean[0] && !aboolean[1] && aboolean[2] && !aboolean[3]) {
/*      */       
/* 1334 */       i = 10;
/*      */     }
/* 1336 */     else if (i == 26 && aboolean[0] && !aboolean[1] && !aboolean[2] && aboolean[3]) {
/*      */       
/* 1338 */       i = 34;
/*      */     }
/* 1340 */     else if (i == 26 && !aboolean[0] && aboolean[1] && aboolean[2] && !aboolean[3]) {
/*      */       
/* 1342 */       i = 35;
/*      */     }
/* 1344 */     else if (i == 26 && aboolean[0] && !aboolean[1] && !aboolean[2] && !aboolean[3]) {
/*      */       
/* 1346 */       i = 32;
/*      */     }
/* 1348 */     else if (i == 26 && !aboolean[0] && aboolean[1] && !aboolean[2] && !aboolean[3]) {
/*      */       
/* 1350 */       i = 33;
/*      */     }
/* 1352 */     else if (i == 26 && !aboolean[0] && !aboolean[1] && aboolean[2] && !aboolean[3]) {
/*      */       
/* 1354 */       i = 44;
/*      */     }
/* 1356 */     else if (i == 26 && !aboolean[0] && !aboolean[1] && !aboolean[2] && aboolean[3]) {
/*      */       
/* 1358 */       i = 45;
/*      */     } 
/*      */     
/* 1361 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void switchValues(int p_switchValues_0_, int p_switchValues_1_, boolean[] p_switchValues_2_) {
/* 1367 */     boolean flag = p_switchValues_2_[p_switchValues_0_];
/* 1368 */     p_switchValues_2_[p_switchValues_0_] = p_switchValues_2_[p_switchValues_1_];
/* 1369 */     p_switchValues_2_[p_switchValues_1_] = flag;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isNeighbourOverlay(ConnectedProperties p_isNeighbourOverlay_0_, IBlockAccess p_isNeighbourOverlay_1_, IBlockState p_isNeighbourOverlay_2_, BlockPos p_isNeighbourOverlay_3_, int p_isNeighbourOverlay_4_, TextureAtlasSprite p_isNeighbourOverlay_5_, int p_isNeighbourOverlay_6_) {
/* 1374 */     IBlockState iblockstate = p_isNeighbourOverlay_1_.getBlockState(p_isNeighbourOverlay_3_);
/*      */     
/* 1376 */     if (!isFullCubeModel(iblockstate))
/*      */     {
/* 1378 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1382 */     if (p_isNeighbourOverlay_0_.connectBlocks != null) {
/*      */       
/* 1384 */       BlockStateBase blockstatebase = (BlockStateBase)iblockstate;
/*      */       
/* 1386 */       if (!Matches.block(blockstatebase.getBlockId(), blockstatebase.getMetadata(), p_isNeighbourOverlay_0_.connectBlocks))
/*      */       {
/* 1388 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1392 */     if (p_isNeighbourOverlay_0_.connectTileIcons != null) {
/*      */       
/* 1394 */       TextureAtlasSprite textureatlassprite = getNeighbourIcon(p_isNeighbourOverlay_1_, p_isNeighbourOverlay_2_, p_isNeighbourOverlay_3_, iblockstate, p_isNeighbourOverlay_4_);
/*      */       
/* 1396 */       if (!Config.isSameOne(textureatlassprite, (Object[])p_isNeighbourOverlay_0_.connectTileIcons))
/*      */       {
/* 1398 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1402 */     IBlockState iblockstate1 = p_isNeighbourOverlay_1_.getBlockState(p_isNeighbourOverlay_3_.offset(getFacing(p_isNeighbourOverlay_4_)));
/*      */     
/* 1404 */     if (iblockstate1.isOpaqueCube())
/*      */     {
/* 1406 */       return false;
/*      */     }
/* 1408 */     if (p_isNeighbourOverlay_4_ == 1 && iblockstate1.getBlock() == Blocks.SNOW_LAYER)
/*      */     {
/* 1410 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1414 */     return !isNeighbour(p_isNeighbourOverlay_0_, p_isNeighbourOverlay_1_, p_isNeighbourOverlay_2_, p_isNeighbourOverlay_3_, iblockstate, p_isNeighbourOverlay_4_, p_isNeighbourOverlay_5_, p_isNeighbourOverlay_6_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isFullCubeModel(IBlockState p_isFullCubeModel_0_) {
/* 1421 */     if (p_isFullCubeModel_0_.isFullCube())
/*      */     {
/* 1423 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1427 */     Block block = p_isFullCubeModel_0_.getBlock();
/*      */     
/* 1429 */     if (block instanceof net.minecraft.block.BlockGlass)
/*      */     {
/* 1431 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1435 */     return block instanceof net.minecraft.block.BlockStainedGlass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isNeighbourMatching(ConnectedProperties p_isNeighbourMatching_0_, IBlockAccess p_isNeighbourMatching_1_, IBlockState p_isNeighbourMatching_2_, BlockPos p_isNeighbourMatching_3_, int p_isNeighbourMatching_4_, TextureAtlasSprite p_isNeighbourMatching_5_, int p_isNeighbourMatching_6_) {
/* 1442 */     IBlockState iblockstate = p_isNeighbourMatching_1_.getBlockState(p_isNeighbourMatching_3_);
/*      */     
/* 1444 */     if (iblockstate == AIR_DEFAULT_STATE)
/*      */     {
/* 1446 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1450 */     if (p_isNeighbourMatching_0_.matchBlocks != null && iblockstate instanceof BlockStateBase) {
/*      */       
/* 1452 */       BlockStateBase blockstatebase = (BlockStateBase)iblockstate;
/*      */       
/* 1454 */       if (!p_isNeighbourMatching_0_.matchesBlock(blockstatebase.getBlockId(), blockstatebase.getMetadata()))
/*      */       {
/* 1456 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1460 */     if (p_isNeighbourMatching_0_.matchTileIcons != null) {
/*      */       
/* 1462 */       TextureAtlasSprite textureatlassprite = getNeighbourIcon(p_isNeighbourMatching_1_, p_isNeighbourMatching_2_, p_isNeighbourMatching_3_, iblockstate, p_isNeighbourMatching_4_);
/*      */       
/* 1464 */       if (textureatlassprite != p_isNeighbourMatching_5_)
/*      */       {
/* 1466 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1470 */     IBlockState iblockstate1 = p_isNeighbourMatching_1_.getBlockState(p_isNeighbourMatching_3_.offset(getFacing(p_isNeighbourMatching_4_)));
/*      */     
/* 1472 */     if (iblockstate1.isOpaqueCube())
/*      */     {
/* 1474 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1478 */     return !(p_isNeighbourMatching_4_ == 1 && iblockstate1.getBlock() == Blocks.SNOW_LAYER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isNeighbour(ConnectedProperties p_isNeighbour_0_, IBlockAccess p_isNeighbour_1_, IBlockState p_isNeighbour_2_, BlockPos p_isNeighbour_3_, int p_isNeighbour_4_, TextureAtlasSprite p_isNeighbour_5_, int p_isNeighbour_6_) {
/* 1485 */     IBlockState iblockstate = p_isNeighbour_1_.getBlockState(p_isNeighbour_3_);
/* 1486 */     return isNeighbour(p_isNeighbour_0_, p_isNeighbour_1_, p_isNeighbour_2_, p_isNeighbour_3_, iblockstate, p_isNeighbour_4_, p_isNeighbour_5_, p_isNeighbour_6_);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isNeighbour(ConnectedProperties p_isNeighbour_0_, IBlockAccess p_isNeighbour_1_, IBlockState p_isNeighbour_2_, BlockPos p_isNeighbour_3_, IBlockState p_isNeighbour_4_, int p_isNeighbour_5_, TextureAtlasSprite p_isNeighbour_6_, int p_isNeighbour_7_) {
/* 1491 */     if (p_isNeighbour_2_ == p_isNeighbour_4_)
/*      */     {
/* 1493 */       return true;
/*      */     }
/* 1495 */     if (p_isNeighbour_0_.connect == 2) {
/*      */       
/* 1497 */       if (p_isNeighbour_4_ == null)
/*      */       {
/* 1499 */         return false;
/*      */       }
/* 1501 */       if (p_isNeighbour_4_ == AIR_DEFAULT_STATE)
/*      */       {
/* 1503 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1507 */       TextureAtlasSprite textureatlassprite = getNeighbourIcon(p_isNeighbour_1_, p_isNeighbour_2_, p_isNeighbour_3_, p_isNeighbour_4_, p_isNeighbour_5_);
/* 1508 */       return (textureatlassprite == p_isNeighbour_6_);
/*      */     } 
/*      */     
/* 1511 */     if (p_isNeighbour_0_.connect == 3) {
/*      */       
/* 1513 */       if (p_isNeighbour_4_ == null)
/*      */       {
/* 1515 */         return false;
/*      */       }
/* 1517 */       if (p_isNeighbour_4_ == AIR_DEFAULT_STATE)
/*      */       {
/* 1519 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1523 */       return (p_isNeighbour_4_.getMaterial() == p_isNeighbour_2_.getMaterial());
/*      */     } 
/*      */     
/* 1526 */     if (!(p_isNeighbour_4_ instanceof BlockStateBase))
/*      */     {
/* 1528 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1532 */     BlockStateBase blockstatebase = (BlockStateBase)p_isNeighbour_4_;
/* 1533 */     Block block = blockstatebase.getBlock();
/* 1534 */     int i = blockstatebase.getMetadata();
/* 1535 */     return (block == p_isNeighbour_2_.getBlock() && i == p_isNeighbour_7_);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getNeighbourIcon(IBlockAccess p_getNeighbourIcon_0_, IBlockState p_getNeighbourIcon_1_, BlockPos p_getNeighbourIcon_2_, IBlockState p_getNeighbourIcon_3_, int p_getNeighbourIcon_4_) {
/* 1541 */     p_getNeighbourIcon_3_ = p_getNeighbourIcon_3_.getBlock().getActualState(p_getNeighbourIcon_3_, p_getNeighbourIcon_0_, p_getNeighbourIcon_2_);
/* 1542 */     IBakedModel ibakedmodel = Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getModelForState(p_getNeighbourIcon_3_);
/*      */     
/* 1544 */     if (ibakedmodel == null)
/*      */     {
/* 1546 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1550 */     EnumFacing enumfacing = getFacing(p_getNeighbourIcon_4_);
/* 1551 */     List<BakedQuad> list = ibakedmodel.getQuads(p_getNeighbourIcon_3_, enumfacing, 0L);
/*      */     
/* 1553 */     if (Config.isBetterGrass())
/*      */     {
/* 1555 */       list = BetterGrass.getFaceQuads(p_getNeighbourIcon_0_, p_getNeighbourIcon_3_, p_getNeighbourIcon_2_, enumfacing, list);
/*      */     }
/*      */     
/* 1558 */     if (list.size() > 0) {
/*      */       
/* 1560 */       BakedQuad bakedquad1 = list.get(0);
/* 1561 */       return bakedquad1.getSprite();
/*      */     } 
/*      */ 
/*      */     
/* 1565 */     List<BakedQuad> list1 = ibakedmodel.getQuads(p_getNeighbourIcon_3_, null, 0L);
/*      */     
/* 1567 */     for (int i = 0; i < list1.size(); i++) {
/*      */       
/* 1569 */       BakedQuad bakedquad = list1.get(i);
/*      */       
/* 1571 */       if (bakedquad.getFace() == enumfacing)
/*      */       {
/* 1573 */         return bakedquad.getSprite();
/*      */       }
/*      */     } 
/*      */     
/* 1577 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureHorizontal(ConnectedProperties p_getConnectedTextureHorizontal_0_, IBlockAccess p_getConnectedTextureHorizontal_1_, IBlockState p_getConnectedTextureHorizontal_2_, BlockPos p_getConnectedTextureHorizontal_3_, int p_getConnectedTextureHorizontal_4_, int p_getConnectedTextureHorizontal_5_, TextureAtlasSprite p_getConnectedTextureHorizontal_6_, int p_getConnectedTextureHorizontal_7_) {
/* 1586 */     boolean flag = false;
/* 1587 */     boolean flag1 = false;
/*      */ 
/*      */     
/* 1590 */     switch (p_getConnectedTextureHorizontal_4_) {
/*      */       
/*      */       case 0:
/* 1593 */         switch (p_getConnectedTextureHorizontal_5_) {
/*      */           
/*      */           case 0:
/* 1596 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1597 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 1:
/* 1601 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1602 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 2:
/* 1606 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1607 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 3:
/* 1611 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1612 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 4:
/* 1616 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1617 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 5:
/* 1621 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1622 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/* 1629 */         switch (p_getConnectedTextureHorizontal_5_) {
/*      */           
/*      */           case 0:
/* 1632 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1633 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 1:
/* 1637 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1638 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 2:
/* 1642 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1643 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 3:
/* 1647 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.west(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1648 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.east(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 4:
/* 1652 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.down(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1653 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.up(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 5:
/* 1657 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.up(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1658 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.down(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/* 1665 */         switch (p_getConnectedTextureHorizontal_5_) {
/*      */           
/*      */           case 0:
/* 1668 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1669 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 1:
/* 1673 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1674 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 2:
/* 1678 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.down(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1679 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.up(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 3:
/* 1683 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.up(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1684 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.down(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 4:
/* 1688 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1689 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/*      */             break;
/*      */           
/*      */           case 5:
/* 1693 */             flag = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.north(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_);
/* 1694 */             flag1 = isNeighbour(p_getConnectedTextureHorizontal_0_, p_getConnectedTextureHorizontal_1_, p_getConnectedTextureHorizontal_2_, p_getConnectedTextureHorizontal_3_.south(), p_getConnectedTextureHorizontal_5_, p_getConnectedTextureHorizontal_6_, p_getConnectedTextureHorizontal_7_); break;
/*      */         } 
/*      */         break;
/*      */     } 
/* 1698 */     int i = 3;
/*      */     
/* 1700 */     if (flag) {
/*      */       
/* 1702 */       if (flag1)
/*      */       {
/* 1704 */         i = 1;
/*      */       }
/*      */       else
/*      */       {
/* 1708 */         i = 2;
/*      */       }
/*      */     
/* 1711 */     } else if (flag1) {
/*      */       
/* 1713 */       i = 0;
/*      */     }
/*      */     else {
/*      */       
/* 1717 */       i = 3;
/*      */     } 
/*      */     
/* 1720 */     return p_getConnectedTextureHorizontal_0_.tileIcons[i];
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureVertical(ConnectedProperties p_getConnectedTextureVertical_0_, IBlockAccess p_getConnectedTextureVertical_1_, IBlockState p_getConnectedTextureVertical_2_, BlockPos p_getConnectedTextureVertical_3_, int p_getConnectedTextureVertical_4_, int p_getConnectedTextureVertical_5_, TextureAtlasSprite p_getConnectedTextureVertical_6_, int p_getConnectedTextureVertical_7_) {
/* 1725 */     boolean flag = false;
/* 1726 */     boolean flag1 = false;
/*      */     
/* 1728 */     switch (p_getConnectedTextureVertical_4_) {
/*      */       
/*      */       case 0:
/* 1731 */         if (p_getConnectedTextureVertical_5_ == 1) {
/*      */           
/* 1733 */           flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.south(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/* 1734 */           flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.north(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_); break;
/*      */         } 
/* 1736 */         if (p_getConnectedTextureVertical_5_ == 0) {
/*      */           
/* 1738 */           flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.north(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/* 1739 */           flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.south(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/*      */           
/*      */           break;
/*      */         } 
/* 1743 */         flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.down(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/* 1744 */         flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.up(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/* 1750 */         if (p_getConnectedTextureVertical_5_ == 3) {
/*      */           
/* 1752 */           flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.down(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/* 1753 */           flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.up(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_); break;
/*      */         } 
/* 1755 */         if (p_getConnectedTextureVertical_5_ == 2) {
/*      */           
/* 1757 */           flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.up(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/* 1758 */           flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.down(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/*      */           
/*      */           break;
/*      */         } 
/* 1762 */         flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.south(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/* 1763 */         flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.north(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/* 1769 */         if (p_getConnectedTextureVertical_5_ == 5) {
/*      */           
/* 1771 */           flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.up(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/* 1772 */           flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.down(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_); break;
/*      */         } 
/* 1774 */         if (p_getConnectedTextureVertical_5_ == 4) {
/*      */           
/* 1776 */           flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.down(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/* 1777 */           flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.up(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/*      */           
/*      */           break;
/*      */         } 
/* 1781 */         flag = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.west(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/* 1782 */         flag1 = isNeighbour(p_getConnectedTextureVertical_0_, p_getConnectedTextureVertical_1_, p_getConnectedTextureVertical_2_, p_getConnectedTextureVertical_3_.east(), p_getConnectedTextureVertical_5_, p_getConnectedTextureVertical_6_, p_getConnectedTextureVertical_7_);
/*      */         break;
/*      */     } 
/*      */     
/* 1786 */     int i = 3;
/*      */     
/* 1788 */     if (flag) {
/*      */       
/* 1790 */       if (flag1)
/*      */       {
/* 1792 */         i = 1;
/*      */       }
/*      */       else
/*      */       {
/* 1796 */         i = 2;
/*      */       }
/*      */     
/* 1799 */     } else if (flag1) {
/*      */       
/* 1801 */       i = 0;
/*      */     }
/*      */     else {
/*      */       
/* 1805 */       i = 3;
/*      */     } 
/*      */     
/* 1808 */     return p_getConnectedTextureVertical_0_.tileIcons[i];
/*      */   }
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureHorizontalVertical(ConnectedProperties p_getConnectedTextureHorizontalVertical_0_, IBlockAccess p_getConnectedTextureHorizontalVertical_1_, IBlockState p_getConnectedTextureHorizontalVertical_2_, BlockPos p_getConnectedTextureHorizontalVertical_3_, int p_getConnectedTextureHorizontalVertical_4_, int p_getConnectedTextureHorizontalVertical_5_, TextureAtlasSprite p_getConnectedTextureHorizontalVertical_6_, int p_getConnectedTextureHorizontalVertical_7_) {
/* 1813 */     TextureAtlasSprite[] atextureatlassprite = p_getConnectedTextureHorizontalVertical_0_.tileIcons;
/* 1814 */     TextureAtlasSprite textureatlassprite = getConnectedTextureHorizontal(p_getConnectedTextureHorizontalVertical_0_, p_getConnectedTextureHorizontalVertical_1_, p_getConnectedTextureHorizontalVertical_2_, p_getConnectedTextureHorizontalVertical_3_, p_getConnectedTextureHorizontalVertical_4_, p_getConnectedTextureHorizontalVertical_5_, p_getConnectedTextureHorizontalVertical_6_, p_getConnectedTextureHorizontalVertical_7_);
/*      */     
/* 1816 */     if (textureatlassprite != null && textureatlassprite != p_getConnectedTextureHorizontalVertical_6_ && textureatlassprite != atextureatlassprite[3])
/*      */     {
/* 1818 */       return textureatlassprite;
/*      */     }
/*      */ 
/*      */     
/* 1822 */     TextureAtlasSprite textureatlassprite1 = getConnectedTextureVertical(p_getConnectedTextureHorizontalVertical_0_, p_getConnectedTextureHorizontalVertical_1_, p_getConnectedTextureHorizontalVertical_2_, p_getConnectedTextureHorizontalVertical_3_, p_getConnectedTextureHorizontalVertical_4_, p_getConnectedTextureHorizontalVertical_5_, p_getConnectedTextureHorizontalVertical_6_, p_getConnectedTextureHorizontalVertical_7_);
/*      */     
/* 1824 */     if (textureatlassprite1 == atextureatlassprite[0])
/*      */     {
/* 1826 */       return atextureatlassprite[4];
/*      */     }
/* 1828 */     if (textureatlassprite1 == atextureatlassprite[1])
/*      */     {
/* 1830 */       return atextureatlassprite[5];
/*      */     }
/*      */ 
/*      */     
/* 1834 */     return (textureatlassprite1 == atextureatlassprite[2]) ? atextureatlassprite[6] : textureatlassprite1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureVerticalHorizontal(ConnectedProperties p_getConnectedTextureVerticalHorizontal_0_, IBlockAccess p_getConnectedTextureVerticalHorizontal_1_, IBlockState p_getConnectedTextureVerticalHorizontal_2_, BlockPos p_getConnectedTextureVerticalHorizontal_3_, int p_getConnectedTextureVerticalHorizontal_4_, int p_getConnectedTextureVerticalHorizontal_5_, TextureAtlasSprite p_getConnectedTextureVerticalHorizontal_6_, int p_getConnectedTextureVerticalHorizontal_7_) {
/* 1841 */     TextureAtlasSprite[] atextureatlassprite = p_getConnectedTextureVerticalHorizontal_0_.tileIcons;
/* 1842 */     TextureAtlasSprite textureatlassprite = getConnectedTextureVertical(p_getConnectedTextureVerticalHorizontal_0_, p_getConnectedTextureVerticalHorizontal_1_, p_getConnectedTextureVerticalHorizontal_2_, p_getConnectedTextureVerticalHorizontal_3_, p_getConnectedTextureVerticalHorizontal_4_, p_getConnectedTextureVerticalHorizontal_5_, p_getConnectedTextureVerticalHorizontal_6_, p_getConnectedTextureVerticalHorizontal_7_);
/*      */     
/* 1844 */     if (textureatlassprite != null && textureatlassprite != p_getConnectedTextureVerticalHorizontal_6_ && textureatlassprite != atextureatlassprite[3])
/*      */     {
/* 1846 */       return textureatlassprite;
/*      */     }
/*      */ 
/*      */     
/* 1850 */     TextureAtlasSprite textureatlassprite1 = getConnectedTextureHorizontal(p_getConnectedTextureVerticalHorizontal_0_, p_getConnectedTextureVerticalHorizontal_1_, p_getConnectedTextureVerticalHorizontal_2_, p_getConnectedTextureVerticalHorizontal_3_, p_getConnectedTextureVerticalHorizontal_4_, p_getConnectedTextureVerticalHorizontal_5_, p_getConnectedTextureVerticalHorizontal_6_, p_getConnectedTextureVerticalHorizontal_7_);
/*      */     
/* 1852 */     if (textureatlassprite1 == atextureatlassprite[0])
/*      */     {
/* 1854 */       return atextureatlassprite[4];
/*      */     }
/* 1856 */     if (textureatlassprite1 == atextureatlassprite[1])
/*      */     {
/* 1858 */       return atextureatlassprite[5];
/*      */     }
/*      */ 
/*      */     
/* 1862 */     return (textureatlassprite1 == atextureatlassprite[2]) ? atextureatlassprite[6] : textureatlassprite1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static TextureAtlasSprite getConnectedTextureTop(ConnectedProperties p_getConnectedTextureTop_0_, IBlockAccess p_getConnectedTextureTop_1_, IBlockState p_getConnectedTextureTop_2_, BlockPos p_getConnectedTextureTop_3_, int p_getConnectedTextureTop_4_, int p_getConnectedTextureTop_5_, TextureAtlasSprite p_getConnectedTextureTop_6_, int p_getConnectedTextureTop_7_) {
/* 1869 */     boolean flag = false;
/*      */     
/* 1871 */     switch (p_getConnectedTextureTop_4_) {
/*      */       
/*      */       case 0:
/* 1874 */         if (p_getConnectedTextureTop_5_ == 1 || p_getConnectedTextureTop_5_ == 0)
/*      */         {
/* 1876 */           return null;
/*      */         }
/*      */         
/* 1879 */         flag = isNeighbour(p_getConnectedTextureTop_0_, p_getConnectedTextureTop_1_, p_getConnectedTextureTop_2_, p_getConnectedTextureTop_3_.up(), p_getConnectedTextureTop_5_, p_getConnectedTextureTop_6_, p_getConnectedTextureTop_7_);
/*      */         break;
/*      */       
/*      */       case 1:
/* 1883 */         if (p_getConnectedTextureTop_5_ == 3 || p_getConnectedTextureTop_5_ == 2)
/*      */         {
/* 1885 */           return null;
/*      */         }
/*      */         
/* 1888 */         flag = isNeighbour(p_getConnectedTextureTop_0_, p_getConnectedTextureTop_1_, p_getConnectedTextureTop_2_, p_getConnectedTextureTop_3_.south(), p_getConnectedTextureTop_5_, p_getConnectedTextureTop_6_, p_getConnectedTextureTop_7_);
/*      */         break;
/*      */       
/*      */       case 2:
/* 1892 */         if (p_getConnectedTextureTop_5_ == 5 || p_getConnectedTextureTop_5_ == 4)
/*      */         {
/* 1894 */           return null;
/*      */         }
/*      */         
/* 1897 */         flag = isNeighbour(p_getConnectedTextureTop_0_, p_getConnectedTextureTop_1_, p_getConnectedTextureTop_2_, p_getConnectedTextureTop_3_.east(), p_getConnectedTextureTop_5_, p_getConnectedTextureTop_6_, p_getConnectedTextureTop_7_);
/*      */         break;
/*      */     } 
/* 1900 */     if (flag)
/*      */     {
/* 1902 */       return p_getConnectedTextureTop_0_.tileIcons[0];
/*      */     }
/*      */ 
/*      */     
/* 1906 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateIcons(TextureMap p_updateIcons_0_) {
/* 1912 */     blockProperties = null;
/* 1913 */     tileProperties = null;
/* 1914 */     spriteQuadMaps = null;
/* 1915 */     spriteQuadCompactMaps = null;
/*      */     
/* 1917 */     if (Config.isConnectedTextures()) {
/*      */       
/* 1919 */       IResourcePack[] airesourcepack = Config.getResourcePacks();
/*      */       
/* 1921 */       for (int i = airesourcepack.length - 1; i >= 0; i--) {
/*      */         
/* 1923 */         IResourcePack iresourcepack = airesourcepack[i];
/* 1924 */         updateIcons(p_updateIcons_0_, iresourcepack);
/*      */       } 
/*      */       
/* 1927 */       updateIcons(p_updateIcons_0_, (IResourcePack)Config.getDefaultResourcePack());
/* 1928 */       ResourceLocation resourcelocation = new ResourceLocation("mcpatcher/ctm/default/empty");
/* 1929 */       emptySprite = p_updateIcons_0_.registerSprite(resourcelocation);
/* 1930 */       spriteQuadMaps = new Map[p_updateIcons_0_.getCountRegisteredSprites() + 1];
/* 1931 */       spriteQuadFullMaps = new Map[p_updateIcons_0_.getCountRegisteredSprites() + 1];
/* 1932 */       spriteQuadCompactMaps = new Map[p_updateIcons_0_.getCountRegisteredSprites() + 1][];
/*      */       
/* 1934 */       if (blockProperties.length <= 0)
/*      */       {
/* 1936 */         blockProperties = null;
/*      */       }
/*      */       
/* 1939 */       if (tileProperties.length <= 0)
/*      */       {
/* 1941 */         tileProperties = null;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void updateIconEmpty(TextureMap p_updateIconEmpty_0_) {}
/*      */ 
/*      */   
/*      */   public static void updateIcons(TextureMap p_updateIcons_0_, IResourcePack p_updateIcons_1_) {
/* 1952 */     String[] astring = ResUtils.collectFiles(p_updateIcons_1_, "mcpatcher/ctm/", ".properties", getDefaultCtmPaths());
/* 1953 */     Arrays.sort((Object[])astring);
/* 1954 */     List list = makePropertyList(tileProperties);
/* 1955 */     List list1 = makePropertyList(blockProperties);
/*      */     
/* 1957 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 1959 */       String s = astring[i];
/* 1960 */       Config.dbg("ConnectedTextures: " + s);
/*      */ 
/*      */       
/*      */       try {
/* 1964 */         ResourceLocation resourcelocation = new ResourceLocation(s);
/* 1965 */         InputStream inputstream = p_updateIcons_1_.getInputStream(resourcelocation);
/*      */         
/* 1967 */         if (inputstream == null) {
/*      */           
/* 1969 */           Config.warn("ConnectedTextures file not found: " + s);
/*      */         }
/*      */         else {
/*      */           
/* 1973 */           Properties properties = new Properties();
/* 1974 */           properties.load(inputstream);
/* 1975 */           ConnectedProperties connectedproperties = new ConnectedProperties(properties, s);
/*      */           
/* 1977 */           if (connectedproperties.isValid(s))
/*      */           {
/* 1979 */             connectedproperties.updateIcons(p_updateIcons_0_);
/* 1980 */             addToTileList(connectedproperties, list);
/* 1981 */             addToBlockList(connectedproperties, list1);
/*      */           }
/*      */         
/*      */         } 
/* 1985 */       } catch (FileNotFoundException var11) {
/*      */         
/* 1987 */         Config.warn("ConnectedTextures file not found: " + s);
/*      */       }
/* 1989 */       catch (Exception exception) {
/*      */         
/* 1991 */         exception.printStackTrace();
/*      */       } 
/*      */     } 
/*      */     
/* 1995 */     blockProperties = propertyListToArray(list1);
/* 1996 */     tileProperties = propertyListToArray(list);
/* 1997 */     multipass = detectMultipass();
/* 1998 */     Config.dbg("Multipass connected textures: " + multipass);
/*      */   }
/*      */ 
/*      */   
/*      */   private static List makePropertyList(ConnectedProperties[][] p_makePropertyList_0_) {
/* 2003 */     List<List> list = new ArrayList();
/*      */     
/* 2005 */     if (p_makePropertyList_0_ != null)
/*      */     {
/* 2007 */       for (int i = 0; i < p_makePropertyList_0_.length; i++) {
/*      */         
/* 2009 */         ConnectedProperties[] aconnectedproperties = p_makePropertyList_0_[i];
/* 2010 */         List list1 = null;
/*      */         
/* 2012 */         if (aconnectedproperties != null)
/*      */         {
/* 2014 */           list1 = new ArrayList(Arrays.asList((Object[])aconnectedproperties));
/*      */         }
/*      */         
/* 2017 */         list.add(list1);
/*      */       } 
/*      */     }
/*      */     
/* 2021 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean detectMultipass() {
/* 2026 */     List list = new ArrayList();
/*      */     
/* 2028 */     for (int i = 0; i < tileProperties.length; i++) {
/*      */       
/* 2030 */       ConnectedProperties[] aconnectedproperties = tileProperties[i];
/*      */       
/* 2032 */       if (aconnectedproperties != null)
/*      */       {
/* 2034 */         list.addAll(Arrays.asList(aconnectedproperties));
/*      */       }
/*      */     } 
/*      */     
/* 2038 */     for (int k = 0; k < blockProperties.length; k++) {
/*      */       
/* 2040 */       ConnectedProperties[] aconnectedproperties2 = blockProperties[k];
/*      */       
/* 2042 */       if (aconnectedproperties2 != null)
/*      */       {
/* 2044 */         list.addAll(Arrays.asList(aconnectedproperties2));
/*      */       }
/*      */     } 
/*      */     
/* 2048 */     ConnectedProperties[] aconnectedproperties1 = (ConnectedProperties[])list.toArray((Object[])new ConnectedProperties[list.size()]);
/* 2049 */     Set set1 = new HashSet();
/* 2050 */     Set<?> set = new HashSet();
/*      */     
/* 2052 */     for (int j = 0; j < aconnectedproperties1.length; j++) {
/*      */       
/* 2054 */       ConnectedProperties connectedproperties = aconnectedproperties1[j];
/*      */       
/* 2056 */       if (connectedproperties.matchTileIcons != null)
/*      */       {
/* 2058 */         set1.addAll(Arrays.asList(connectedproperties.matchTileIcons));
/*      */       }
/*      */       
/* 2061 */       if (connectedproperties.tileIcons != null)
/*      */       {
/* 2063 */         set.addAll(Arrays.asList(connectedproperties.tileIcons));
/*      */       }
/*      */     } 
/*      */     
/* 2067 */     set1.retainAll(set);
/* 2068 */     return !set1.isEmpty();
/*      */   }
/*      */ 
/*      */   
/*      */   private static ConnectedProperties[][] propertyListToArray(List<List> p_propertyListToArray_0_) {
/* 2073 */     ConnectedProperties[][] aconnectedproperties = new ConnectedProperties[p_propertyListToArray_0_.size()][];
/*      */     
/* 2075 */     for (int i = 0; i < p_propertyListToArray_0_.size(); i++) {
/*      */       
/* 2077 */       List list = p_propertyListToArray_0_.get(i);
/*      */       
/* 2079 */       if (list != null) {
/*      */         
/* 2081 */         ConnectedProperties[] aconnectedproperties1 = (ConnectedProperties[])list.toArray((Object[])new ConnectedProperties[list.size()]);
/* 2082 */         aconnectedproperties[i] = aconnectedproperties1;
/*      */       } 
/*      */     } 
/*      */     
/* 2086 */     return aconnectedproperties;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToTileList(ConnectedProperties p_addToTileList_0_, List p_addToTileList_1_) {
/* 2091 */     if (p_addToTileList_0_.matchTileIcons != null)
/*      */     {
/* 2093 */       for (int i = 0; i < p_addToTileList_0_.matchTileIcons.length; i++) {
/*      */         
/* 2095 */         TextureAtlasSprite textureatlassprite = p_addToTileList_0_.matchTileIcons[i];
/*      */         
/* 2097 */         if (!(textureatlassprite instanceof TextureAtlasSprite)) {
/*      */           
/* 2099 */           Config.warn("TextureAtlasSprite is not TextureAtlasSprite: " + textureatlassprite + ", name: " + textureatlassprite.getIconName());
/*      */         }
/*      */         else {
/*      */           
/* 2103 */           int j = textureatlassprite.getIndexInMap();
/*      */           
/* 2105 */           if (j < 0) {
/*      */             
/* 2107 */             Config.warn("Invalid tile ID: " + j + ", icon: " + textureatlassprite.getIconName());
/*      */           }
/*      */           else {
/*      */             
/* 2111 */             addToList(p_addToTileList_0_, p_addToTileList_1_, j);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToBlockList(ConnectedProperties p_addToBlockList_0_, List p_addToBlockList_1_) {
/* 2120 */     if (p_addToBlockList_0_.matchBlocks != null)
/*      */     {
/* 2122 */       for (int i = 0; i < p_addToBlockList_0_.matchBlocks.length; i++) {
/*      */         
/* 2124 */         int j = p_addToBlockList_0_.matchBlocks[i].getBlockId();
/*      */         
/* 2126 */         if (j < 0) {
/*      */           
/* 2128 */           Config.warn("Invalid block ID: " + j);
/*      */         }
/*      */         else {
/*      */           
/* 2132 */           addToList(p_addToBlockList_0_, p_addToBlockList_1_, j);
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void addToList(ConnectedProperties p_addToList_0_, List<List> p_addToList_1_, int p_addToList_2_) {
/* 2140 */     while (p_addToList_2_ >= p_addToList_1_.size())
/*      */     {
/* 2142 */       p_addToList_1_.add(null);
/*      */     }
/*      */     
/* 2145 */     List<ConnectedProperties> list = p_addToList_1_.get(p_addToList_2_);
/*      */     
/* 2147 */     if (list == null) {
/*      */       
/* 2149 */       list = new ArrayList();
/* 2150 */       p_addToList_1_.set(p_addToList_2_, list);
/*      */     } 
/*      */     
/* 2153 */     list.add(p_addToList_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   private static String[] getDefaultCtmPaths() {
/* 2158 */     List<String> list = new ArrayList();
/* 2159 */     String s = "mcpatcher/ctm/default/";
/*      */     
/* 2161 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass.png"))) {
/*      */       
/* 2163 */       list.add(String.valueOf(s) + "glass.properties");
/* 2164 */       list.add(String.valueOf(s) + "glasspane.properties");
/*      */     } 
/*      */     
/* 2167 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/bookshelf.png")))
/*      */     {
/* 2169 */       list.add(String.valueOf(s) + "bookshelf.properties");
/*      */     }
/*      */     
/* 2172 */     if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/sandstone_normal.png")))
/*      */     {
/* 2174 */       list.add(String.valueOf(s) + "sandstone.properties");
/*      */     }
/*      */     
/* 2177 */     String[] astring = { "white", "orange", "magenta", "light_blue", "yellow", "lime", "pink", "gray", "silver", "cyan", "purple", "blue", "brown", "green", "red", "black" };
/*      */     
/* 2179 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 2181 */       String s1 = astring[i];
/*      */       
/* 2183 */       if (Config.isFromDefaultResourcePack(new ResourceLocation("textures/blocks/glass_" + s1 + ".png"))) {
/*      */         
/* 2185 */         list.add(String.valueOf(s) + i + "_glass_" + s1 + "/glass_" + s1 + ".properties");
/* 2186 */         list.add(String.valueOf(s) + i + "_glass_" + s1 + "/glass_pane_" + s1 + ".properties");
/*      */       } 
/*      */     } 
/*      */     
/* 2190 */     String[] astring1 = list.<String>toArray(new String[list.size()]);
/* 2191 */     return astring1;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ConnectedTextures.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */