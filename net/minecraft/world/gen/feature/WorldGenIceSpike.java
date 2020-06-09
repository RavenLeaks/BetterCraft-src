/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenIceSpike
/*     */   extends WorldGenerator
/*     */ {
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  16 */     while (worldIn.isAirBlock(position) && position.getY() > 2)
/*     */     {
/*  18 */       position = position.down();
/*     */     }
/*     */     
/*  21 */     if (worldIn.getBlockState(position).getBlock() != Blocks.SNOW)
/*     */     {
/*  23 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  27 */     position = position.up(rand.nextInt(4));
/*  28 */     int i = rand.nextInt(4) + 7;
/*  29 */     int j = i / 4 + rand.nextInt(2);
/*     */     
/*  31 */     if (j > 1 && rand.nextInt(60) == 0)
/*     */     {
/*  33 */       position = position.up(10 + rand.nextInt(30));
/*     */     }
/*     */     
/*  36 */     for (int k = 0; k < i; k++) {
/*     */       
/*  38 */       float f = (1.0F - k / i) * j;
/*  39 */       int l = MathHelper.ceil(f);
/*     */       
/*  41 */       for (int i1 = -l; i1 <= l; i1++) {
/*     */         
/*  43 */         float f1 = MathHelper.abs(i1) - 0.25F;
/*     */         
/*  45 */         for (int j1 = -l; j1 <= l; j1++) {
/*     */           
/*  47 */           float f2 = MathHelper.abs(j1) - 0.25F;
/*     */           
/*  49 */           if (((i1 == 0 && j1 == 0) || f1 * f1 + f2 * f2 <= f * f) && ((i1 != -l && i1 != l && j1 != -l && j1 != l) || rand.nextFloat() <= 0.75F)) {
/*     */             
/*  51 */             IBlockState iblockstate = worldIn.getBlockState(position.add(i1, k, j1));
/*  52 */             Block block = iblockstate.getBlock();
/*     */             
/*  54 */             if (iblockstate.getMaterial() == Material.AIR || block == Blocks.DIRT || block == Blocks.SNOW || block == Blocks.ICE)
/*     */             {
/*  56 */               setBlockAndNotifyAdequately(worldIn, position.add(i1, k, j1), Blocks.PACKED_ICE.getDefaultState());
/*     */             }
/*     */             
/*  59 */             if (k != 0 && l > 1) {
/*     */               
/*  61 */               iblockstate = worldIn.getBlockState(position.add(i1, -k, j1));
/*  62 */               block = iblockstate.getBlock();
/*     */               
/*  64 */               if (iblockstate.getMaterial() == Material.AIR || block == Blocks.DIRT || block == Blocks.SNOW || block == Blocks.ICE)
/*     */               {
/*  66 */                 setBlockAndNotifyAdequately(worldIn, position.add(i1, -k, j1), Blocks.PACKED_ICE.getDefaultState());
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  74 */     int k1 = j - 1;
/*     */     
/*  76 */     if (k1 < 0) {
/*     */       
/*  78 */       k1 = 0;
/*     */     }
/*  80 */     else if (k1 > 1) {
/*     */       
/*  82 */       k1 = 1;
/*     */     } 
/*     */     
/*  85 */     for (int l1 = -k1; l1 <= k1; l1++) {
/*     */       
/*  87 */       for (int i2 = -k1; i2 <= k1; i2++) {
/*     */         
/*  89 */         BlockPos blockpos = position.add(l1, -1, i2);
/*  90 */         int j2 = 50;
/*     */         
/*  92 */         if (Math.abs(l1) == 1 && Math.abs(i2) == 1)
/*     */         {
/*  94 */           j2 = rand.nextInt(5);
/*     */         }
/*     */         
/*  97 */         while (blockpos.getY() > 50) {
/*     */           
/*  99 */           IBlockState iblockstate1 = worldIn.getBlockState(blockpos);
/* 100 */           Block block1 = iblockstate1.getBlock();
/*     */           
/* 102 */           if (iblockstate1.getMaterial() != Material.AIR && block1 != Blocks.DIRT && block1 != Blocks.SNOW && block1 != Blocks.ICE && block1 != Blocks.PACKED_ICE) {
/*     */             break;
/*     */           }
/*     */ 
/*     */           
/* 107 */           setBlockAndNotifyAdequately(worldIn, blockpos, Blocks.PACKED_ICE.getDefaultState());
/* 108 */           blockpos = blockpos.down();
/* 109 */           j2--;
/*     */           
/* 111 */           if (j2 <= 0) {
/*     */             
/* 113 */             blockpos = blockpos.down(rand.nextInt(5) + 1);
/* 114 */             j2 = rand.nextInt(5);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 120 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenIceSpike.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */