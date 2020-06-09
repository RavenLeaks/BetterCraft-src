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
/*     */ public class WorldGenTaiga1 extends WorldGenAbstractTree {
/*  17 */   private static final IBlockState TRUNK = Blocks.LOG.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE);
/*  18 */   private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.SPRUCE).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */   
/*     */   public WorldGenTaiga1() {
/*  22 */     super(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  27 */     int i = rand.nextInt(5) + 7;
/*  28 */     int j = i - rand.nextInt(2) - 3;
/*  29 */     int k = i - j;
/*  30 */     int l = 1 + rand.nextInt(k + 1);
/*     */     
/*  32 */     if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
/*     */       
/*  34 */       boolean flag = true;
/*     */       
/*  36 */       for (int i1 = position.getY(); i1 <= position.getY() + 1 + i && flag; i1++) {
/*     */         
/*  38 */         int j1 = 1;
/*     */         
/*  40 */         if (i1 - position.getY() < j) {
/*     */           
/*  42 */           j1 = 0;
/*     */         }
/*     */         else {
/*     */           
/*  46 */           j1 = l;
/*     */         } 
/*     */         
/*  49 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  51 */         for (int k1 = position.getX() - j1; k1 <= position.getX() + j1 && flag; k1++) {
/*     */           
/*  53 */           for (int l1 = position.getZ() - j1; l1 <= position.getZ() + j1 && flag; l1++) {
/*     */             
/*  55 */             if (i1 >= 0 && i1 < 256) {
/*     */               
/*  57 */               if (!canGrowInto(worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(k1, i1, l1)).getBlock()))
/*     */               {
/*  59 */                 flag = false;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/*  64 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  70 */       if (!flag)
/*     */       {
/*  72 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  76 */       Block block = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  78 */       if ((block == Blocks.GRASS || block == Blocks.DIRT) && position.getY() < 256 - i - 1) {
/*     */         
/*  80 */         setDirtAt(worldIn, position.down());
/*  81 */         int k2 = 0;
/*     */         
/*  83 */         for (int l2 = position.getY() + i; l2 >= position.getY() + j; l2--) {
/*     */           
/*  85 */           for (int j3 = position.getX() - k2; j3 <= position.getX() + k2; j3++) {
/*     */             
/*  87 */             int k3 = j3 - position.getX();
/*     */             
/*  89 */             for (int i2 = position.getZ() - k2; i2 <= position.getZ() + k2; i2++) {
/*     */               
/*  91 */               int j2 = i2 - position.getZ();
/*     */               
/*  93 */               if (Math.abs(k3) != k2 || Math.abs(j2) != k2 || k2 <= 0) {
/*     */                 
/*  95 */                 BlockPos blockpos = new BlockPos(j3, l2, i2);
/*     */                 
/*  97 */                 if (!worldIn.getBlockState(blockpos).isFullBlock())
/*     */                 {
/*  99 */                   setBlockAndNotifyAdequately(worldIn, blockpos, LEAF);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 105 */           if (k2 >= 1 && l2 == position.getY() + j + 1) {
/*     */             
/* 107 */             k2--;
/*     */           }
/* 109 */           else if (k2 < l) {
/*     */             
/* 111 */             k2++;
/*     */           } 
/*     */         } 
/*     */         
/* 115 */         for (int i3 = 0; i3 < i - 1; i3++) {
/*     */           
/* 117 */           Material material = worldIn.getBlockState(position.up(i3)).getMaterial();
/*     */           
/* 119 */           if (material == Material.AIR || material == Material.LEAVES)
/*     */           {
/* 121 */             setBlockAndNotifyAdequately(worldIn, position.up(i3), TRUNK);
/*     */           }
/*     */         } 
/*     */         
/* 125 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 129 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenTaiga1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */