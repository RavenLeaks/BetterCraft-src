/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class WorldGenHugeTrees
/*     */   extends WorldGenAbstractTree
/*     */ {
/*     */   protected final int baseHeight;
/*     */   protected final IBlockState woodMetadata;
/*     */   protected final IBlockState leavesMetadata;
/*     */   protected int extraRandomHeight;
/*     */   
/*     */   public WorldGenHugeTrees(boolean notify, int baseHeightIn, int extraRandomHeightIn, IBlockState woodMetadataIn, IBlockState leavesMetadataIn) {
/*  25 */     super(notify);
/*  26 */     this.baseHeight = baseHeightIn;
/*  27 */     this.extraRandomHeight = extraRandomHeightIn;
/*  28 */     this.woodMetadata = woodMetadataIn;
/*  29 */     this.leavesMetadata = leavesMetadataIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getHeight(Random rand) {
/*  37 */     int i = rand.nextInt(3) + this.baseHeight;
/*     */     
/*  39 */     if (this.extraRandomHeight > 1)
/*     */     {
/*  41 */       i += rand.nextInt(this.extraRandomHeight);
/*     */     }
/*     */     
/*  44 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isSpaceAt(World worldIn, BlockPos leavesPos, int height) {
/*  52 */     boolean flag = true;
/*     */     
/*  54 */     if (leavesPos.getY() >= 1 && leavesPos.getY() + height + 1 <= 256) {
/*     */       
/*  56 */       for (int i = 0; i <= 1 + height; i++) {
/*     */         
/*  58 */         int j = 2;
/*     */         
/*  60 */         if (i == 0) {
/*     */           
/*  62 */           j = 1;
/*     */         }
/*  64 */         else if (i >= 1 + height - 2) {
/*     */           
/*  66 */           j = 2;
/*     */         } 
/*     */         
/*  69 */         for (int k = -j; k <= j && flag; k++) {
/*     */           
/*  71 */           for (int l = -j; l <= j && flag; l++) {
/*     */             
/*  73 */             if (leavesPos.getY() + i < 0 || leavesPos.getY() + i >= 256 || !canGrowInto(worldIn.getBlockState(leavesPos.add(k, i, l)).getBlock()))
/*     */             {
/*  75 */               flag = false;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  81 */       return flag;
/*     */     } 
/*     */ 
/*     */     
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean ensureDirtsUnderneath(BlockPos pos, World worldIn) {
/*  95 */     BlockPos blockpos = pos.down();
/*  96 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */     
/*  98 */     if ((block == Blocks.GRASS || block == Blocks.DIRT) && pos.getY() >= 2) {
/*     */       
/* 100 */       setDirtAt(worldIn, blockpos);
/* 101 */       setDirtAt(worldIn, blockpos.east());
/* 102 */       setDirtAt(worldIn, blockpos.south());
/* 103 */       setDirtAt(worldIn, blockpos.south().east());
/* 104 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 108 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean ensureGrowable(World worldIn, Random rand, BlockPos treePos, int p_175929_4_) {
/* 118 */     return (isSpaceAt(worldIn, treePos, p_175929_4_) && ensureDirtsUnderneath(treePos, worldIn));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void growLeavesLayerStrict(World worldIn, BlockPos layerCenter, int width) {
/* 126 */     int i = width * width;
/*     */     
/* 128 */     for (int j = -width; j <= width + 1; j++) {
/*     */       
/* 130 */       for (int k = -width; k <= width + 1; k++) {
/*     */         
/* 132 */         int l = j - 1;
/* 133 */         int i1 = k - 1;
/*     */         
/* 135 */         if (j * j + k * k <= i || l * l + i1 * i1 <= i || j * j + i1 * i1 <= i || l * l + k * k <= i) {
/*     */           
/* 137 */           BlockPos blockpos = layerCenter.add(j, 0, k);
/* 138 */           Material material = worldIn.getBlockState(blockpos).getMaterial();
/*     */           
/* 140 */           if (material == Material.AIR || material == Material.LEAVES)
/*     */           {
/* 142 */             setBlockAndNotifyAdequately(worldIn, blockpos, this.leavesMetadata);
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
/*     */   protected void growLeavesLayer(World worldIn, BlockPos layerCenter, int width) {
/* 154 */     int i = width * width;
/*     */     
/* 156 */     for (int j = -width; j <= width; j++) {
/*     */       
/* 158 */       for (int k = -width; k <= width; k++) {
/*     */         
/* 160 */         if (j * j + k * k <= i) {
/*     */           
/* 162 */           BlockPos blockpos = layerCenter.add(j, 0, k);
/* 163 */           Material material = worldIn.getBlockState(blockpos).getMaterial();
/*     */           
/* 165 */           if (material == Material.AIR || material == Material.LEAVES)
/*     */           {
/* 167 */             setBlockAndNotifyAdequately(worldIn, blockpos, this.leavesMetadata);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenHugeTrees.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */