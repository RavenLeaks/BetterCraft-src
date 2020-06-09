/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockGrass
/*     */   extends Block implements IGrowable {
/*  19 */   public static final PropertyBool SNOWY = PropertyBool.create("snowy");
/*     */ 
/*     */   
/*     */   protected BlockGrass() {
/*  23 */     super(Material.GRASS);
/*  24 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)SNOWY, Boolean.valueOf(false)));
/*  25 */     setTickRandomly(true);
/*  26 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  35 */     Block block = worldIn.getBlockState(pos.up()).getBlock();
/*  36 */     return state.withProperty((IProperty)SNOWY, Boolean.valueOf(!(block != Blocks.SNOW && block != Blocks.SNOW_LAYER)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  41 */     if (!worldIn.isRemote)
/*     */     {
/*  43 */       if (worldIn.getLightFromNeighbors(pos.up()) < 4 && worldIn.getBlockState(pos.up()).getLightOpacity() > 2) {
/*     */         
/*  45 */         worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState());
/*     */ 
/*     */       
/*     */       }
/*  49 */       else if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
/*     */         
/*  51 */         for (int i = 0; i < 4; i++) {
/*     */           
/*  53 */           BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
/*     */           
/*  55 */           if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/*  60 */           IBlockState iblockstate = worldIn.getBlockState(blockpos.up());
/*  61 */           IBlockState iblockstate1 = worldIn.getBlockState(blockpos);
/*     */           
/*  63 */           if (iblockstate1.getBlock() == Blocks.DIRT && iblockstate1.getValue((IProperty)BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate.getLightOpacity() <= 2)
/*     */           {
/*  65 */             worldIn.setBlockState(blockpos, Blocks.GRASS.getDefaultState());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  78 */     return Blocks.DIRT.getItemDropped(Blocks.DIRT.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/*  91 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/*  96 */     BlockPos blockpos = pos.up();
/*     */     
/*  98 */     for (int i = 0; i < 128; i++) {
/*     */       
/* 100 */       BlockPos blockpos1 = blockpos;
/* 101 */       int j = 0;
/*     */ 
/*     */       
/*     */       while (true) {
/* 105 */         if (j >= i / 16) {
/*     */           
/* 107 */           if ((worldIn.getBlockState(blockpos1).getBlock()).blockMaterial == Material.AIR) {
/*     */             
/* 109 */             if (rand.nextInt(8) == 0) {
/*     */               
/* 111 */               BlockFlower.EnumFlowerType blockflower$enumflowertype = worldIn.getBiome(blockpos1).pickRandomFlower(rand, blockpos1);
/* 112 */               BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();
/* 113 */               IBlockState iblockstate = blockflower.getDefaultState().withProperty(blockflower.getTypeProperty(), blockflower$enumflowertype);
/*     */               
/* 115 */               if (blockflower.canBlockStay(worldIn, blockpos1, iblockstate))
/*     */               {
/* 117 */                 worldIn.setBlockState(blockpos1, iblockstate, 3);
/*     */               }
/*     */               
/*     */               break;
/*     */             } 
/* 122 */             IBlockState iblockstate1 = Blocks.TALLGRASS.getDefaultState().withProperty((IProperty)BlockTallGrass.TYPE, BlockTallGrass.EnumType.GRASS);
/*     */             
/* 124 */             if (Blocks.TALLGRASS.canBlockStay(worldIn, blockpos1, iblockstate1))
/*     */             {
/* 126 */               worldIn.setBlockState(blockpos1, iblockstate1, 3);
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/*     */           break;
/*     */         } 
/*     */         
/* 134 */         blockpos1 = blockpos1.add(rand.nextInt(3) - 1, (rand.nextInt(3) - 1) * rand.nextInt(3) / 2, rand.nextInt(3) - 1);
/*     */         
/* 136 */         if (worldIn.getBlockState(blockpos1.down()).getBlock() != Blocks.GRASS || worldIn.getBlockState(blockpos1).isNormalCube()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 141 */         j++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 148 */     return BlockRenderLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 156 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 161 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)SNOWY });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockGrass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */