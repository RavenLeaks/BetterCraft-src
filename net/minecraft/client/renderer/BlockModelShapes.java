/*     */ package net.minecraft.client.renderer;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockCactus;
/*     */ import net.minecraft.block.BlockColored;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.BlockDispenser;
/*     */ import net.minecraft.block.BlockDoor;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.block.BlockDropper;
/*     */ import net.minecraft.block.BlockFenceGate;
/*     */ import net.minecraft.block.BlockFire;
/*     */ import net.minecraft.block.BlockFlowerPot;
/*     */ import net.minecraft.block.BlockHopper;
/*     */ import net.minecraft.block.BlockJukebox;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockNewLeaf;
/*     */ import net.minecraft.block.BlockNewLog;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockOldLog;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.BlockPrismarine;
/*     */ import net.minecraft.block.BlockQuartz;
/*     */ import net.minecraft.block.BlockRedSandstone;
/*     */ import net.minecraft.block.BlockRedstoneWire;
/*     */ import net.minecraft.block.BlockReed;
/*     */ import net.minecraft.block.BlockSand;
/*     */ import net.minecraft.block.BlockSandStone;
/*     */ import net.minecraft.block.BlockSapling;
/*     */ import net.minecraft.block.BlockSilverfish;
/*     */ import net.minecraft.block.BlockStem;
/*     */ import net.minecraft.block.BlockStone;
/*     */ import net.minecraft.block.BlockStoneBrick;
/*     */ import net.minecraft.block.BlockStoneSlab;
/*     */ import net.minecraft.block.BlockStoneSlabNew;
/*     */ import net.minecraft.block.BlockTNT;
/*     */ import net.minecraft.block.BlockTallGrass;
/*     */ import net.minecraft.block.BlockTripWire;
/*     */ import net.minecraft.block.BlockWall;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.renderer.block.model.IBakedModel;
/*     */ import net.minecraft.client.renderer.block.model.ModelManager;
/*     */ import net.minecraft.client.renderer.block.model.ModelResourceLocation;
/*     */ import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
/*     */ import net.minecraft.client.renderer.block.statemap.IStateMapper;
/*     */ import net.minecraft.client.renderer.block.statemap.StateMap;
/*     */ import net.minecraft.client.renderer.block.statemap.StateMapperBase;
/*     */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class BlockModelShapes
/*     */ {
/*  58 */   private final Map<IBlockState, IBakedModel> bakedModelStore = Maps.newIdentityHashMap();
/*  59 */   private final BlockStateMapper blockStateMapper = new BlockStateMapper();
/*     */   
/*     */   private final ModelManager modelManager;
/*     */   
/*     */   public BlockModelShapes(ModelManager manager) {
/*  64 */     this.modelManager = manager;
/*  65 */     registerAllBlocks();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockStateMapper getBlockStateMapper() {
/*  70 */     return this.blockStateMapper;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasSprite getTexture(IBlockState state) {
/*  75 */     Block block = state.getBlock();
/*  76 */     IBakedModel ibakedmodel = getModelForState(state);
/*     */     
/*  78 */     if (ibakedmodel == null || ibakedmodel == this.modelManager.getMissingModel()) {
/*     */       
/*  80 */       if (block == Blocks.WALL_SIGN || block == Blocks.STANDING_SIGN || block == Blocks.CHEST || block == Blocks.TRAPPED_CHEST || block == Blocks.STANDING_BANNER || block == Blocks.WALL_BANNER || block == Blocks.BED)
/*     */       {
/*  82 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/planks_oak");
/*     */       }
/*     */       
/*  85 */       if (block == Blocks.ENDER_CHEST)
/*     */       {
/*  87 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/obsidian");
/*     */       }
/*     */       
/*  90 */       if (block == Blocks.FLOWING_LAVA || block == Blocks.LAVA)
/*     */       {
/*  92 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/lava_still");
/*     */       }
/*     */       
/*  95 */       if (block == Blocks.FLOWING_WATER || block == Blocks.WATER)
/*     */       {
/*  97 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/water_still");
/*     */       }
/*     */       
/* 100 */       if (block == Blocks.SKULL)
/*     */       {
/* 102 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/soul_sand");
/*     */       }
/*     */       
/* 105 */       if (block == Blocks.BARRIER)
/*     */       {
/* 107 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:items/barrier");
/*     */       }
/*     */       
/* 110 */       if (block == Blocks.STRUCTURE_VOID)
/*     */       {
/* 112 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:items/structure_void");
/*     */       }
/*     */       
/* 115 */       if (block == Blocks.field_190977_dl)
/*     */       {
/* 117 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_white");
/*     */       }
/*     */       
/* 120 */       if (block == Blocks.field_190978_dm)
/*     */       {
/* 122 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_orange");
/*     */       }
/*     */       
/* 125 */       if (block == Blocks.field_190979_dn)
/*     */       {
/* 127 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_magenta");
/*     */       }
/*     */       
/* 130 */       if (block == Blocks.field_190980_do)
/*     */       {
/* 132 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_light_blue");
/*     */       }
/*     */       
/* 135 */       if (block == Blocks.field_190981_dp)
/*     */       {
/* 137 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_yellow");
/*     */       }
/*     */       
/* 140 */       if (block == Blocks.field_190982_dq)
/*     */       {
/* 142 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_lime");
/*     */       }
/*     */       
/* 145 */       if (block == Blocks.field_190983_dr)
/*     */       {
/* 147 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_pink");
/*     */       }
/*     */       
/* 150 */       if (block == Blocks.field_190984_ds)
/*     */       {
/* 152 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_gray");
/*     */       }
/*     */       
/* 155 */       if (block == Blocks.field_190985_dt)
/*     */       {
/* 157 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_silver");
/*     */       }
/*     */       
/* 160 */       if (block == Blocks.field_190986_du)
/*     */       {
/* 162 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_cyan");
/*     */       }
/*     */       
/* 165 */       if (block == Blocks.field_190987_dv)
/*     */       {
/* 167 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_purple");
/*     */       }
/*     */       
/* 170 */       if (block == Blocks.field_190988_dw)
/*     */       {
/* 172 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_blue");
/*     */       }
/*     */       
/* 175 */       if (block == Blocks.field_190989_dx)
/*     */       {
/* 177 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_brown");
/*     */       }
/*     */       
/* 180 */       if (block == Blocks.field_190990_dy)
/*     */       {
/* 182 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_green");
/*     */       }
/*     */       
/* 185 */       if (block == Blocks.field_190991_dz)
/*     */       {
/* 187 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_red");
/*     */       }
/*     */       
/* 190 */       if (block == Blocks.field_190975_dA)
/*     */       {
/* 192 */         return this.modelManager.getTextureMap().getAtlasSprite("minecraft:blocks/shulker_top_black");
/*     */       }
/*     */     } 
/*     */     
/* 196 */     if (ibakedmodel == null)
/*     */     {
/* 198 */       ibakedmodel = this.modelManager.getMissingModel();
/*     */     }
/*     */     
/* 201 */     return ibakedmodel.getParticleTexture();
/*     */   }
/*     */ 
/*     */   
/*     */   public IBakedModel getModelForState(IBlockState state) {
/* 206 */     IBakedModel ibakedmodel = this.bakedModelStore.get(state);
/*     */     
/* 208 */     if (ibakedmodel == null)
/*     */     {
/* 210 */       ibakedmodel = this.modelManager.getMissingModel();
/*     */     }
/*     */     
/* 213 */     return ibakedmodel;
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelManager getModelManager() {
/* 218 */     return this.modelManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void reloadModels() {
/* 223 */     this.bakedModelStore.clear();
/*     */     
/* 225 */     for (Map.Entry<IBlockState, ModelResourceLocation> entry : (Iterable<Map.Entry<IBlockState, ModelResourceLocation>>)this.blockStateMapper.putAllStateModelLocations().entrySet())
/*     */     {
/* 227 */       this.bakedModelStore.put(entry.getKey(), this.modelManager.getModel(entry.getValue()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerBlockWithStateMapper(Block assoc, IStateMapper stateMapper) {
/* 233 */     this.blockStateMapper.registerBlockStateMapper(assoc, stateMapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerBuiltInBlocks(Block... builtIns) {
/* 238 */     this.blockStateMapper.registerBuiltInBlocks(builtIns);
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerAllBlocks() {
/* 243 */     registerBuiltInBlocks(new Block[] { Blocks.AIR, (Block)Blocks.FLOWING_WATER, (Block)Blocks.WATER, (Block)Blocks.FLOWING_LAVA, (Block)Blocks.LAVA, (Block)Blocks.PISTON_EXTENSION, (Block)Blocks.CHEST, Blocks.ENDER_CHEST, Blocks.TRAPPED_CHEST, Blocks.STANDING_SIGN, (Block)Blocks.SKULL, Blocks.END_PORTAL, Blocks.BARRIER, Blocks.WALL_SIGN, Blocks.WALL_BANNER, Blocks.STANDING_BANNER, Blocks.END_GATEWAY, Blocks.STRUCTURE_VOID, Blocks.field_190977_dl, Blocks.field_190978_dm, Blocks.field_190979_dn, Blocks.field_190980_do, Blocks.field_190981_dp, Blocks.field_190982_dq, Blocks.field_190983_dr, Blocks.field_190984_ds, Blocks.field_190985_dt, Blocks.field_190986_du, Blocks.field_190987_dv, Blocks.field_190988_dw, Blocks.field_190989_dx, Blocks.field_190990_dy, Blocks.field_190991_dz, Blocks.field_190975_dA, Blocks.BED });
/* 244 */     registerBlockWithStateMapper(Blocks.STONE, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockStone.VARIANT).build());
/* 245 */     registerBlockWithStateMapper(Blocks.PRISMARINE, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockPrismarine.VARIANT).build());
/* 246 */     registerBlockWithStateMapper((Block)Blocks.LEAVES, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockOldLeaf.VARIANT).withSuffix("_leaves").ignore(new IProperty[] { (IProperty)BlockLeaves.CHECK_DECAY, (IProperty)BlockLeaves.DECAYABLE }).build());
/* 247 */     registerBlockWithStateMapper((Block)Blocks.LEAVES2, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockNewLeaf.VARIANT).withSuffix("_leaves").ignore(new IProperty[] { (IProperty)BlockLeaves.CHECK_DECAY, (IProperty)BlockLeaves.DECAYABLE }).build());
/* 248 */     registerBlockWithStateMapper((Block)Blocks.CACTUS, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockCactus.AGE }).build());
/* 249 */     registerBlockWithStateMapper((Block)Blocks.REEDS, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockReed.AGE }).build());
/* 250 */     registerBlockWithStateMapper(Blocks.JUKEBOX, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockJukebox.HAS_RECORD }).build());
/* 251 */     registerBlockWithStateMapper(Blocks.COBBLESTONE_WALL, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockWall.VARIANT).withSuffix("_wall").build());
/* 252 */     registerBlockWithStateMapper((Block)Blocks.DOUBLE_PLANT, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockDoublePlant.VARIANT).ignore(new IProperty[] { (IProperty)BlockDoublePlant.FACING }).build());
/* 253 */     registerBlockWithStateMapper(Blocks.OAK_FENCE_GATE, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 254 */     registerBlockWithStateMapper(Blocks.SPRUCE_FENCE_GATE, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 255 */     registerBlockWithStateMapper(Blocks.BIRCH_FENCE_GATE, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 256 */     registerBlockWithStateMapper(Blocks.JUNGLE_FENCE_GATE, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 257 */     registerBlockWithStateMapper(Blocks.DARK_OAK_FENCE_GATE, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 258 */     registerBlockWithStateMapper(Blocks.ACACIA_FENCE_GATE, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFenceGate.POWERED }).build());
/* 259 */     registerBlockWithStateMapper(Blocks.TRIPWIRE, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockTripWire.DISARMED, (IProperty)BlockTripWire.POWERED }).build());
/* 260 */     registerBlockWithStateMapper((Block)Blocks.DOUBLE_WOODEN_SLAB, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockPlanks.VARIANT).withSuffix("_double_slab").build());
/* 261 */     registerBlockWithStateMapper((Block)Blocks.WOODEN_SLAB, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockPlanks.VARIANT).withSuffix("_slab").build());
/* 262 */     registerBlockWithStateMapper(Blocks.TNT, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockTNT.EXPLODE }).build());
/* 263 */     registerBlockWithStateMapper((Block)Blocks.FIRE, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFire.AGE }).build());
/* 264 */     registerBlockWithStateMapper((Block)Blocks.REDSTONE_WIRE, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockRedstoneWire.POWER }).build());
/* 265 */     registerBlockWithStateMapper((Block)Blocks.OAK_DOOR, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 266 */     registerBlockWithStateMapper((Block)Blocks.SPRUCE_DOOR, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 267 */     registerBlockWithStateMapper((Block)Blocks.BIRCH_DOOR, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 268 */     registerBlockWithStateMapper((Block)Blocks.JUNGLE_DOOR, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 269 */     registerBlockWithStateMapper((Block)Blocks.ACACIA_DOOR, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 270 */     registerBlockWithStateMapper((Block)Blocks.DARK_OAK_DOOR, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 271 */     registerBlockWithStateMapper((Block)Blocks.IRON_DOOR, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDoor.POWERED }).build());
/* 272 */     registerBlockWithStateMapper(Blocks.WOOL, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_wool").build());
/* 273 */     registerBlockWithStateMapper(Blocks.CARPET, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_carpet").build());
/* 274 */     registerBlockWithStateMapper(Blocks.STAINED_HARDENED_CLAY, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_stained_hardened_clay").build());
/* 275 */     registerBlockWithStateMapper((Block)Blocks.STAINED_GLASS_PANE, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_stained_glass_pane").build());
/* 276 */     registerBlockWithStateMapper((Block)Blocks.STAINED_GLASS, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_stained_glass").build());
/* 277 */     registerBlockWithStateMapper(Blocks.SANDSTONE, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockSandStone.TYPE).build());
/* 278 */     registerBlockWithStateMapper(Blocks.RED_SANDSTONE, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockRedSandstone.TYPE).build());
/* 279 */     registerBlockWithStateMapper((Block)Blocks.TALLGRASS, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockTallGrass.TYPE).build());
/* 280 */     registerBlockWithStateMapper((Block)Blocks.YELLOW_FLOWER, (IStateMapper)(new StateMap.Builder()).withName(Blocks.YELLOW_FLOWER.getTypeProperty()).build());
/* 281 */     registerBlockWithStateMapper((Block)Blocks.RED_FLOWER, (IStateMapper)(new StateMap.Builder()).withName(Blocks.RED_FLOWER.getTypeProperty()).build());
/* 282 */     registerBlockWithStateMapper((Block)Blocks.STONE_SLAB, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockStoneSlab.VARIANT).withSuffix("_slab").build());
/* 283 */     registerBlockWithStateMapper((Block)Blocks.STONE_SLAB2, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockStoneSlabNew.VARIANT).withSuffix("_slab").build());
/* 284 */     registerBlockWithStateMapper(Blocks.MONSTER_EGG, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockSilverfish.VARIANT).withSuffix("_monster_egg").build());
/* 285 */     registerBlockWithStateMapper(Blocks.STONEBRICK, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockStoneBrick.VARIANT).build());
/* 286 */     registerBlockWithStateMapper(Blocks.DISPENSER, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDispenser.TRIGGERED }).build());
/* 287 */     registerBlockWithStateMapper(Blocks.DROPPER, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockDropper.TRIGGERED }).build());
/* 288 */     registerBlockWithStateMapper(Blocks.LOG, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockOldLog.VARIANT).withSuffix("_log").build());
/* 289 */     registerBlockWithStateMapper(Blocks.LOG2, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockNewLog.VARIANT).withSuffix("_log").build());
/* 290 */     registerBlockWithStateMapper(Blocks.PLANKS, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockPlanks.VARIANT).withSuffix("_planks").build());
/* 291 */     registerBlockWithStateMapper(Blocks.SAPLING, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockSapling.TYPE).withSuffix("_sapling").build());
/* 292 */     registerBlockWithStateMapper((Block)Blocks.SAND, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockSand.VARIANT).build());
/* 293 */     registerBlockWithStateMapper((Block)Blocks.HOPPER, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockHopper.ENABLED }).build());
/* 294 */     registerBlockWithStateMapper(Blocks.FLOWER_POT, (IStateMapper)(new StateMap.Builder()).ignore(new IProperty[] { (IProperty)BlockFlowerPot.LEGACY_DATA }).build());
/* 295 */     registerBlockWithStateMapper(Blocks.field_192443_dR, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_concrete").build());
/* 296 */     registerBlockWithStateMapper(Blocks.field_192444_dS, (IStateMapper)(new StateMap.Builder()).withName((IProperty)BlockColored.COLOR).withSuffix("_concrete_powder").build());
/* 297 */     registerBlockWithStateMapper(Blocks.QUARTZ_BLOCK, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 301 */             BlockQuartz.EnumType blockquartz$enumtype = (BlockQuartz.EnumType)state.getValue((IProperty)BlockQuartz.VARIANT);
/*     */             
/* 303 */             switch (blockquartz$enumtype) {
/*     */ 
/*     */               
/*     */               default:
/* 307 */                 return new ModelResourceLocation("quartz_block", "normal");
/*     */               
/*     */               case null:
/* 310 */                 return new ModelResourceLocation("chiseled_quartz_block", "normal");
/*     */               
/*     */               case LINES_Y:
/* 313 */                 return new ModelResourceLocation("quartz_column", "axis=y");
/*     */               
/*     */               case LINES_X:
/* 316 */                 return new ModelResourceLocation("quartz_column", "axis=x");
/*     */               case LINES_Z:
/*     */                 break;
/* 319 */             }  return new ModelResourceLocation("quartz_column", "axis=z");
/*     */           }
/*     */         });
/*     */     
/* 323 */     registerBlockWithStateMapper((Block)Blocks.DEADBUSH, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 327 */             return new ModelResourceLocation("dead_bush", "normal");
/*     */           }
/*     */         });
/* 330 */     registerBlockWithStateMapper(Blocks.PUMPKIN_STEM, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 334 */             Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap((Map)state.getProperties());
/*     */             
/* 336 */             if (state.getValue((IProperty)BlockStem.FACING) != EnumFacing.UP)
/*     */             {
/* 338 */               map.remove(BlockStem.AGE);
/*     */             }
/*     */             
/* 341 */             return new ModelResourceLocation((ResourceLocation)Block.REGISTRY.getNameForObject(state.getBlock()), getPropertyString(map));
/*     */           }
/*     */         });
/* 344 */     registerBlockWithStateMapper(Blocks.MELON_STEM, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 348 */             Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap((Map)state.getProperties());
/*     */             
/* 350 */             if (state.getValue((IProperty)BlockStem.FACING) != EnumFacing.UP)
/*     */             {
/* 352 */               map.remove(BlockStem.AGE);
/*     */             }
/*     */             
/* 355 */             return new ModelResourceLocation((ResourceLocation)Block.REGISTRY.getNameForObject(state.getBlock()), getPropertyString(map));
/*     */           }
/*     */         });
/* 358 */     registerBlockWithStateMapper(Blocks.DIRT, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 362 */             Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap((Map)state.getProperties());
/* 363 */             String s = BlockDirt.VARIANT.getName((Enum)map.remove(BlockDirt.VARIANT));
/*     */             
/* 365 */             if (BlockDirt.DirtType.PODZOL != state.getValue((IProperty)BlockDirt.VARIANT))
/*     */             {
/* 367 */               map.remove(BlockDirt.SNOWY);
/*     */             }
/*     */             
/* 370 */             return new ModelResourceLocation(s, getPropertyString(map));
/*     */           }
/*     */         });
/* 373 */     registerBlockWithStateMapper((Block)Blocks.DOUBLE_STONE_SLAB, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 377 */             Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap((Map)state.getProperties());
/* 378 */             String s = BlockStoneSlab.VARIANT.getName((Enum)map.remove(BlockStoneSlab.VARIANT));
/* 379 */             map.remove(BlockStoneSlab.SEAMLESS);
/* 380 */             String s1 = ((Boolean)state.getValue((IProperty)BlockStoneSlab.SEAMLESS)).booleanValue() ? "all" : "normal";
/* 381 */             return new ModelResourceLocation(String.valueOf(s) + "_double_slab", s1);
/*     */           }
/*     */         });
/* 384 */     registerBlockWithStateMapper((Block)Blocks.DOUBLE_STONE_SLAB2, (IStateMapper)new StateMapperBase()
/*     */         {
/*     */           protected ModelResourceLocation getModelResourceLocation(IBlockState state)
/*     */           {
/* 388 */             Map<IProperty<?>, Comparable<?>> map = Maps.newLinkedHashMap((Map)state.getProperties());
/* 389 */             String s = BlockStoneSlabNew.VARIANT.getName((Enum)map.remove(BlockStoneSlabNew.VARIANT));
/* 390 */             map.remove(BlockStoneSlab.SEAMLESS);
/* 391 */             String s1 = ((Boolean)state.getValue((IProperty)BlockStoneSlabNew.SEAMLESS)).booleanValue() ? "all" : "normal";
/* 392 */             return new ModelResourceLocation(String.valueOf(s) + "_double_slab", s1);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\BlockModelShapes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */