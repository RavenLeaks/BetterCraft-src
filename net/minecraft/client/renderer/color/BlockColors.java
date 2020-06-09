/*     */ package net.minecraft.client.renderer.color;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.BlockRedstoneWire;
/*     */ import net.minecraft.block.BlockStem;
/*     */ import net.minecraft.block.BlockTallGrass;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityFlowerPot;
/*     */ import net.minecraft.util.ObjectIntIdentityMap;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.ColorizerFoliage;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.BiomeColorHelper;
/*     */ 
/*     */ public class BlockColors {
/*  27 */   private final ObjectIntIdentityMap<IBlockColor> mapBlockColors = new ObjectIntIdentityMap(32);
/*     */ 
/*     */   
/*     */   public static BlockColors init() {
/*  31 */     final BlockColors blockcolors = new BlockColors();
/*  32 */     blockcolors.registerBlockColorHandler(new IBlockColor()
/*     */         {
/*     */           public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
/*     */           {
/*  36 */             BlockDoublePlant.EnumPlantType blockdoubleplant$enumplanttype = (BlockDoublePlant.EnumPlantType)state.getValue((IProperty)BlockDoublePlant.VARIANT);
/*  37 */             return (worldIn != null && pos != null && (blockdoubleplant$enumplanttype == BlockDoublePlant.EnumPlantType.GRASS || blockdoubleplant$enumplanttype == BlockDoublePlant.EnumPlantType.FERN)) ? BiomeColorHelper.getGrassColorAtPos(worldIn, (state.getValue((IProperty)BlockDoublePlant.HALF) == BlockDoublePlant.EnumBlockHalf.UPPER) ? pos.down() : pos) : -1;
/*     */           }
/*  39 */         }new Block[] { (Block)Blocks.DOUBLE_PLANT });
/*  40 */     blockcolors.registerBlockColorHandler(new IBlockColor()
/*     */         {
/*     */           public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
/*     */           {
/*  44 */             if (worldIn != null && pos != null) {
/*     */               
/*  46 */               TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */               
/*  48 */               if (tileentity instanceof TileEntityFlowerPot) {
/*     */                 
/*  50 */                 Item item = ((TileEntityFlowerPot)tileentity).getFlowerPotItem();
/*  51 */                 IBlockState iblockstate = Block.getBlockFromItem(item).getDefaultState();
/*  52 */                 return blockcolors.colorMultiplier(iblockstate, worldIn, pos, tintIndex);
/*     */               } 
/*     */ 
/*     */               
/*  56 */               return -1;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/*  61 */             return -1;
/*     */           }
/*     */         }new Block[] {
/*  64 */           Blocks.FLOWER_POT });
/*  65 */     blockcolors.registerBlockColorHandler(new IBlockColor()
/*     */         {
/*     */           public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
/*     */           {
/*  69 */             return (worldIn != null && pos != null) ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos) : ColorizerGrass.getGrassColor(0.5D, 1.0D);
/*     */           }
/*  71 */         }new Block[] { (Block)Blocks.GRASS });
/*  72 */     blockcolors.registerBlockColorHandler(new IBlockColor()
/*     */         {
/*     */           public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
/*     */           {
/*  76 */             BlockPlanks.EnumType blockplanks$enumtype = (BlockPlanks.EnumType)state.getValue((IProperty)BlockOldLeaf.VARIANT);
/*     */             
/*  78 */             if (blockplanks$enumtype == BlockPlanks.EnumType.SPRUCE)
/*     */             {
/*  80 */               return ColorizerFoliage.getFoliageColorPine();
/*     */             }
/*  82 */             if (blockplanks$enumtype == BlockPlanks.EnumType.BIRCH)
/*     */             {
/*  84 */               return ColorizerFoliage.getFoliageColorBirch();
/*     */             }
/*     */ 
/*     */             
/*  88 */             return (worldIn != null && pos != null) ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic();
/*     */           }
/*     */         }new Block[] {
/*  91 */           (Block)Blocks.LEAVES });
/*  92 */     blockcolors.registerBlockColorHandler(new IBlockColor()
/*     */         {
/*     */           public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
/*     */           {
/*  96 */             return (worldIn != null && pos != null) ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic();
/*     */           }
/*  98 */         }new Block[] { (Block)Blocks.LEAVES2 });
/*  99 */     blockcolors.registerBlockColorHandler(new IBlockColor()
/*     */         {
/*     */           public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
/*     */           {
/* 103 */             return (worldIn != null && pos != null) ? BiomeColorHelper.getWaterColorAtPos(worldIn, pos) : -1;
/*     */           }
/* 105 */         }new Block[] { (Block)Blocks.WATER, (Block)Blocks.FLOWING_WATER });
/* 106 */     blockcolors.registerBlockColorHandler(new IBlockColor()
/*     */         {
/*     */           public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
/*     */           {
/* 110 */             return BlockRedstoneWire.colorMultiplier(((Integer)state.getValue((IProperty)BlockRedstoneWire.POWER)).intValue());
/*     */           }
/* 112 */         },  new Block[] { (Block)Blocks.REDSTONE_WIRE });
/* 113 */     blockcolors.registerBlockColorHandler(new IBlockColor()
/*     */         {
/*     */           public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
/*     */           {
/* 117 */             return (worldIn != null && pos != null) ? BiomeColorHelper.getGrassColorAtPos(worldIn, pos) : -1;
/*     */           }
/* 119 */         }new Block[] { (Block)Blocks.REEDS });
/* 120 */     blockcolors.registerBlockColorHandler(new IBlockColor()
/*     */         {
/*     */           public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
/*     */           {
/* 124 */             int i = ((Integer)state.getValue((IProperty)BlockStem.AGE)).intValue();
/* 125 */             int j = i * 32;
/* 126 */             int k = 255 - i * 8;
/* 127 */             int l = i * 4;
/* 128 */             return j << 16 | k << 8 | l;
/*     */           }
/* 130 */         },  new Block[] { Blocks.MELON_STEM, Blocks.PUMPKIN_STEM });
/* 131 */     blockcolors.registerBlockColorHandler(new IBlockColor()
/*     */         {
/*     */           public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
/*     */           {
/* 135 */             if (worldIn != null && pos != null)
/*     */             {
/* 137 */               return BiomeColorHelper.getGrassColorAtPos(worldIn, pos);
/*     */             }
/*     */ 
/*     */             
/* 141 */             return (state.getValue((IProperty)BlockTallGrass.TYPE) == BlockTallGrass.EnumType.DEAD_BUSH) ? 16777215 : ColorizerGrass.getGrassColor(0.5D, 1.0D);
/*     */           }
/*     */         }new Block[] {
/* 144 */           (Block)Blocks.TALLGRASS });
/* 145 */     blockcolors.registerBlockColorHandler(new IBlockColor()
/*     */         {
/*     */           public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
/*     */           {
/* 149 */             return (worldIn != null && pos != null) ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic();
/*     */           }
/* 151 */         }new Block[] { Blocks.VINE });
/* 152 */     blockcolors.registerBlockColorHandler(new IBlockColor()
/*     */         {
/*     */           public int colorMultiplier(IBlockState state, @Nullable IBlockAccess worldIn, @Nullable BlockPos pos, int tintIndex)
/*     */           {
/* 156 */             return (worldIn != null && pos != null) ? 2129968 : 7455580;
/*     */           }
/* 158 */         },  new Block[] { Blocks.WATERLILY });
/* 159 */     return blockcolors;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor(IBlockState p_189991_1_, World p_189991_2_, BlockPos p_189991_3_) {
/* 164 */     IBlockColor iblockcolor = (IBlockColor)this.mapBlockColors.getByValue(Block.getIdFromBlock(p_189991_1_.getBlock()));
/*     */     
/* 166 */     if (iblockcolor != null)
/*     */     {
/* 168 */       return iblockcolor.colorMultiplier(p_189991_1_, null, null, 0);
/*     */     }
/*     */ 
/*     */     
/* 172 */     MapColor mapcolor = p_189991_1_.getMapColor((IBlockAccess)p_189991_2_, p_189991_3_);
/* 173 */     return (mapcolor != null) ? mapcolor.colorValue : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int colorMultiplier(IBlockState state, @Nullable IBlockAccess blockAccess, @Nullable BlockPos pos, int renderPass) {
/* 179 */     IBlockColor iblockcolor = (IBlockColor)this.mapBlockColors.getByValue(Block.getIdFromBlock(state.getBlock()));
/* 180 */     return (iblockcolor == null) ? -1 : iblockcolor.colorMultiplier(state, blockAccess, pos, renderPass);
/*     */   } public void registerBlockColorHandler(IBlockColor blockColor, Block... blocksIn) {
/*     */     byte b;
/*     */     int i;
/*     */     Block[] arrayOfBlock;
/* 185 */     for (i = (arrayOfBlock = blocksIn).length, b = 0; b < i; ) { Block block = arrayOfBlock[b];
/*     */       
/* 187 */       this.mapBlockColors.put(blockColor, Block.getIdFromBlock(block));
/*     */       b++; }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\color\BlockColors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */