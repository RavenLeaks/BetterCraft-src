/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLeaves;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockOldLog;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenTaiga2 extends WorldGenAbstractTree {
/*  17 */   private static final IBlockState TRUNK = Blocks.LOG.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE);
/*  18 */   private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */   
/*     */   public WorldGenTaiga2(boolean p_i2025_1_) {
/*  22 */     super(p_i2025_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  27 */     int i = rand.nextInt(4) + 6;
/*  28 */     int j = 1 + rand.nextInt(2);
/*  29 */     int k = i - j;
/*  30 */     int l = 2 + rand.nextInt(2);
/*  31 */     boolean flag = true;
/*     */     
/*  33 */     if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
/*     */       
/*  35 */       for (int i1 = position.getY(); i1 <= position.getY() + 1 + i && flag; i1++) {
/*     */         int j1;
/*     */ 
/*     */         
/*  39 */         if (i1 - position.getY() < j) {
/*     */           
/*  41 */           j1 = 0;
/*     */         }
/*     */         else {
/*     */           
/*  45 */           j1 = l;
/*     */         } 
/*     */         
/*  48 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  50 */         for (int k1 = position.getX() - j1; k1 <= position.getX() + j1 && flag; k1++) {
/*     */           
/*  52 */           for (int l1 = position.getZ() - j1; l1 <= position.getZ() + j1 && flag; l1++) {
/*     */             
/*  54 */             if (i1 >= 0 && i1 < 256) {
/*     */               
/*  56 */               Material material = worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(k1, i1, l1)).getMaterial();
/*     */               
/*  58 */               if (material != Material.AIR && material != Material.LEAVES)
/*     */               {
/*  60 */                 flag = false;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/*  65 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  71 */       if (!flag)
/*     */       {
/*  73 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  77 */       Block block = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  79 */       if ((block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.FARMLAND) && position.getY() < 256 - i - 1) {
/*     */         
/*  81 */         setDirtAt(worldIn, position.down());
/*  82 */         int i3 = rand.nextInt(2);
/*  83 */         int j3 = 1;
/*  84 */         int k3 = 0;
/*     */         
/*  86 */         for (int l3 = 0; l3 <= k; l3++) {
/*     */           
/*  88 */           int j4 = position.getY() + i - l3;
/*     */           
/*  90 */           for (int i2 = position.getX() - i3; i2 <= position.getX() + i3; i2++) {
/*     */             
/*  92 */             int j2 = i2 - position.getX();
/*     */             
/*  94 */             for (int k2 = position.getZ() - i3; k2 <= position.getZ() + i3; k2++) {
/*     */               
/*  96 */               int l2 = k2 - position.getZ();
/*     */               
/*  98 */               if (Math.abs(j2) != i3 || Math.abs(l2) != i3 || i3 <= 0) {
/*     */                 
/* 100 */                 BlockPos blockpos = new BlockPos(i2, j4, k2);
/*     */                 
/* 102 */                 if (!worldIn.getBlockState(blockpos).isFullBlock())
/*     */                 {
/* 104 */                   setBlockAndNotifyAdequately(worldIn, blockpos, LEAF);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 110 */           if (i3 >= j3) {
/*     */             
/* 112 */             i3 = k3;
/* 113 */             k3 = 1;
/* 114 */             j3++;
/*     */             
/* 116 */             if (j3 > l)
/*     */             {
/* 118 */               j3 = l;
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 123 */             i3++;
/*     */           } 
/*     */         } 
/*     */         
/* 127 */         int i4 = rand.nextInt(3);
/*     */         
/* 129 */         for (int k4 = 0; k4 < i - i4; k4++) {
/*     */           
/* 131 */           Material material1 = worldIn.getBlockState(position.up(k4)).getMaterial();
/*     */           
/* 133 */           if (material1 == Material.AIR || material1 == Material.LEAVES)
/*     */           {
/* 135 */             setBlockAndNotifyAdequately(worldIn, position.up(k4), TRUNK);
/*     */           }
/*     */         } 
/*     */         
/* 139 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 143 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenTaiga2.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */