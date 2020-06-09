/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
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
/*     */ public class WorldGenBirchTree extends WorldGenAbstractTree {
/*  16 */   private static final IBlockState LOG = Blocks.LOG.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.BIRCH);
/*  17 */   private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.BIRCH).withProperty((IProperty)BlockOldLeaf.CHECK_DECAY, Boolean.valueOf(false));
/*     */   
/*     */   private final boolean useExtraRandomHeight;
/*     */   
/*     */   public WorldGenBirchTree(boolean notify, boolean useExtraRandomHeightIn) {
/*  22 */     super(notify);
/*  23 */     this.useExtraRandomHeight = useExtraRandomHeightIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  28 */     int i = rand.nextInt(3) + 5;
/*     */     
/*  30 */     if (this.useExtraRandomHeight)
/*     */     {
/*  32 */       i += rand.nextInt(7);
/*     */     }
/*     */     
/*  35 */     boolean flag = true;
/*     */     
/*  37 */     if (position.getY() >= 1 && position.getY() + i + 1 <= 256) {
/*     */       
/*  39 */       for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
/*     */         
/*  41 */         int k = 1;
/*     */         
/*  43 */         if (j == position.getY())
/*     */         {
/*  45 */           k = 0;
/*     */         }
/*     */         
/*  48 */         if (j >= position.getY() + 1 + i - 2)
/*     */         {
/*  50 */           k = 2;
/*     */         }
/*     */         
/*  53 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  55 */         for (int l = position.getX() - k; l <= position.getX() + k && flag; l++) {
/*     */           
/*  57 */           for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; i1++) {
/*     */             
/*  59 */             if (j >= 0 && j < 256) {
/*     */               
/*  61 */               if (!canGrowInto(worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(l, j, i1)).getBlock()))
/*     */               {
/*  63 */                 flag = false;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/*  68 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  74 */       if (!flag)
/*     */       {
/*  76 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  80 */       Block block = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  82 */       if ((block == Blocks.GRASS || block == Blocks.DIRT || block == Blocks.FARMLAND) && position.getY() < 256 - i - 1) {
/*     */         
/*  84 */         setDirtAt(worldIn, position.down());
/*     */         
/*  86 */         for (int i2 = position.getY() - 3 + i; i2 <= position.getY() + i; i2++) {
/*     */           
/*  88 */           int k2 = i2 - position.getY() + i;
/*  89 */           int l2 = 1 - k2 / 2;
/*     */           
/*  91 */           for (int i3 = position.getX() - l2; i3 <= position.getX() + l2; i3++) {
/*     */             
/*  93 */             int j1 = i3 - position.getX();
/*     */             
/*  95 */             for (int k1 = position.getZ() - l2; k1 <= position.getZ() + l2; k1++) {
/*     */               
/*  97 */               int l1 = k1 - position.getZ();
/*     */               
/*  99 */               if (Math.abs(j1) != l2 || Math.abs(l1) != l2 || (rand.nextInt(2) != 0 && k2 != 0)) {
/*     */                 
/* 101 */                 BlockPos blockpos = new BlockPos(i3, i2, k1);
/* 102 */                 Material material = worldIn.getBlockState(blockpos).getMaterial();
/*     */                 
/* 104 */                 if (material == Material.AIR || material == Material.LEAVES)
/*     */                 {
/* 106 */                   setBlockAndNotifyAdequately(worldIn, blockpos, LEAF);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 113 */         for (int j2 = 0; j2 < i; j2++) {
/*     */           
/* 115 */           Material material1 = worldIn.getBlockState(position.up(j2)).getMaterial();
/*     */           
/* 117 */           if (material1 == Material.AIR || material1 == Material.LEAVES)
/*     */           {
/* 119 */             setBlockAndNotifyAdequately(worldIn, position.up(j2), LOG);
/*     */           }
/*     */         } 
/*     */         
/* 123 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 127 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 133 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenBirchTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */