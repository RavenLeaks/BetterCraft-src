/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockOldLog;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenMegaPineTree extends WorldGenHugeTrees {
/*  19 */   private static final IBlockState TRUNK = Blocks.LOG.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE);
/*  20 */   private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*  21 */   private static final IBlockState PODZOL = Blocks.DIRT.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.PODZOL);
/*     */   
/*     */   private final boolean useBaseHeight;
/*     */   
/*     */   public WorldGenMegaPineTree(boolean notify, boolean p_i45457_2_) {
/*  26 */     super(notify, 13, 15, TRUNK, LEAF);
/*  27 */     this.useBaseHeight = p_i45457_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  32 */     int i = getHeight(rand);
/*     */     
/*  34 */     if (!ensureGrowable(worldIn, rand, position, i))
/*     */     {
/*  36 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  40 */     createCrown(worldIn, position.getX(), position.getZ(), position.getY() + i, 0, rand);
/*     */     
/*  42 */     for (int j = 0; j < i; j++) {
/*     */       
/*  44 */       IBlockState iblockstate = worldIn.getBlockState(position.up(j));
/*     */       
/*  46 */       if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
/*     */       {
/*  48 */         setBlockAndNotifyAdequately(worldIn, position.up(j), this.woodMetadata);
/*     */       }
/*     */       
/*  51 */       if (j < i - 1) {
/*     */         
/*  53 */         iblockstate = worldIn.getBlockState(position.add(1, j, 0));
/*     */         
/*  55 */         if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
/*     */         {
/*  57 */           setBlockAndNotifyAdequately(worldIn, position.add(1, j, 0), this.woodMetadata);
/*     */         }
/*     */         
/*  60 */         iblockstate = worldIn.getBlockState(position.add(1, j, 1));
/*     */         
/*  62 */         if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
/*     */         {
/*  64 */           setBlockAndNotifyAdequately(worldIn, position.add(1, j, 1), this.woodMetadata);
/*     */         }
/*     */         
/*  67 */         iblockstate = worldIn.getBlockState(position.add(0, j, 1));
/*     */         
/*  69 */         if (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES)
/*     */         {
/*  71 */           setBlockAndNotifyAdequately(worldIn, position.add(0, j, 1), this.woodMetadata);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void createCrown(World worldIn, int x, int z, int y, int p_150541_5_, Random rand) {
/*  82 */     int i = rand.nextInt(5) + (this.useBaseHeight ? this.baseHeight : 3);
/*  83 */     int j = 0;
/*     */     
/*  85 */     for (int k = y - i; k <= y; k++) {
/*     */       
/*  87 */       int l = y - k;
/*  88 */       int i1 = p_150541_5_ + MathHelper.floor(l / i * 3.5F);
/*  89 */       growLeavesLayerStrict(worldIn, new BlockPos(x, k, z), i1 + ((l > 0 && i1 == j && (k & 0x1) == 0) ? 1 : 0));
/*  90 */       j = i1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void generateSaplings(World worldIn, Random random, BlockPos pos) {
/*  96 */     placePodzolCircle(worldIn, pos.west().north());
/*  97 */     placePodzolCircle(worldIn, pos.east(2).north());
/*  98 */     placePodzolCircle(worldIn, pos.west().south(2));
/*  99 */     placePodzolCircle(worldIn, pos.east(2).south(2));
/*     */     
/* 101 */     for (int i = 0; i < 5; i++) {
/*     */       
/* 103 */       int j = random.nextInt(64);
/* 104 */       int k = j % 8;
/* 105 */       int l = j / 8;
/*     */       
/* 107 */       if (k == 0 || k == 7 || l == 0 || l == 7)
/*     */       {
/* 109 */         placePodzolCircle(worldIn, pos.add(-3 + k, 0, -3 + l));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void placePodzolCircle(World worldIn, BlockPos center) {
/* 116 */     for (int i = -2; i <= 2; i++) {
/*     */       
/* 118 */       for (int j = -2; j <= 2; j++) {
/*     */         
/* 120 */         if (Math.abs(i) != 2 || Math.abs(j) != 2)
/*     */         {
/* 122 */           placePodzolAt(worldIn, center.add(i, 0, j));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void placePodzolAt(World worldIn, BlockPos pos) {
/* 130 */     for (int i = 2; i >= -3; i--) {
/*     */       
/* 132 */       BlockPos blockpos = pos.up(i);
/* 133 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 134 */       Block block = iblockstate.getBlock();
/*     */       
/* 136 */       if (block == Blocks.GRASS || block == Blocks.DIRT) {
/*     */         
/* 138 */         setBlockAndNotifyAdequately(worldIn, blockpos, PODZOL);
/*     */         
/*     */         break;
/*     */       } 
/* 142 */       if (iblockstate.getMaterial() != Material.AIR && i < 0)
/*     */         break; 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenMegaPineTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */