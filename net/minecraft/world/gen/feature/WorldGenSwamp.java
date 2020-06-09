/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockOldLeaf;
/*     */ import net.minecraft.block.BlockOldLog;
/*     */ import net.minecraft.block.BlockPlanks;
/*     */ import net.minecraft.block.BlockVine;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenSwamp extends WorldGenAbstractTree {
/*  18 */   private static final IBlockState TRUNK = Blocks.LOG.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.OAK);
/*  19 */   private static final IBlockState LEAF = Blocks.LEAVES.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.OAK).withProperty((IProperty)BlockOldLeaf.CHECK_DECAY, Boolean.valueOf(false));
/*     */ 
/*     */   
/*     */   public WorldGenSwamp() {
/*  23 */     super(false);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*     */     int i;
/*  30 */     for (i = rand.nextInt(4) + 5; worldIn.getBlockState(position.down()).getMaterial() == Material.WATER; position = position.down());
/*     */ 
/*     */ 
/*     */ 
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
/*  50 */           k = 3;
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
/*  61 */               IBlockState iblockstate = worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(l, j, i1));
/*  62 */               Block block = iblockstate.getBlock();
/*     */               
/*  64 */               if (iblockstate.getMaterial() != Material.AIR && iblockstate.getMaterial() != Material.LEAVES)
/*     */               {
/*  66 */                 if (block != Blocks.WATER && block != Blocks.FLOWING_WATER)
/*     */                 {
/*  68 */                   flag = false;
/*     */                 }
/*  70 */                 else if (j > position.getY())
/*     */                 {
/*  72 */                   flag = false;
/*     */                 }
/*     */               
/*     */               }
/*     */             } else {
/*     */               
/*  78 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  84 */       if (!flag)
/*     */       {
/*  86 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  90 */       Block block1 = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  92 */       if ((block1 == Blocks.GRASS || block1 == Blocks.DIRT) && position.getY() < 256 - i - 1) {
/*     */         
/*  94 */         setDirtAt(worldIn, position.down());
/*     */         
/*  96 */         for (int k1 = position.getY() - 3 + i; k1 <= position.getY() + i; k1++) {
/*     */           
/*  98 */           int j2 = k1 - position.getY() + i;
/*  99 */           int l2 = 2 - j2 / 2;
/*     */           
/* 101 */           for (int j3 = position.getX() - l2; j3 <= position.getX() + l2; j3++) {
/*     */             
/* 103 */             int k3 = j3 - position.getX();
/*     */             
/* 105 */             for (int i4 = position.getZ() - l2; i4 <= position.getZ() + l2; i4++) {
/*     */               
/* 107 */               int j1 = i4 - position.getZ();
/*     */               
/* 109 */               if (Math.abs(k3) != l2 || Math.abs(j1) != l2 || (rand.nextInt(2) != 0 && j2 != 0)) {
/*     */                 
/* 111 */                 BlockPos blockpos = new BlockPos(j3, k1, i4);
/*     */                 
/* 113 */                 if (!worldIn.getBlockState(blockpos).isFullBlock())
/*     */                 {
/* 115 */                   setBlockAndNotifyAdequately(worldIn, blockpos, LEAF);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 122 */         for (int l1 = 0; l1 < i; l1++) {
/*     */           
/* 124 */           IBlockState iblockstate1 = worldIn.getBlockState(position.up(l1));
/* 125 */           Block block2 = iblockstate1.getBlock();
/*     */           
/* 127 */           if (iblockstate1.getMaterial() == Material.AIR || iblockstate1.getMaterial() == Material.LEAVES || block2 == Blocks.FLOWING_WATER || block2 == Blocks.WATER)
/*     */           {
/* 129 */             setBlockAndNotifyAdequately(worldIn, position.up(l1), TRUNK);
/*     */           }
/*     */         } 
/*     */         
/* 133 */         for (int i2 = position.getY() - 3 + i; i2 <= position.getY() + i; i2++) {
/*     */           
/* 135 */           int k2 = i2 - position.getY() + i;
/* 136 */           int i3 = 2 - k2 / 2;
/* 137 */           BlockPos.MutableBlockPos blockpos$mutableblockpos1 = new BlockPos.MutableBlockPos();
/*     */           
/* 139 */           for (int l3 = position.getX() - i3; l3 <= position.getX() + i3; l3++) {
/*     */             
/* 141 */             for (int j4 = position.getZ() - i3; j4 <= position.getZ() + i3; j4++) {
/*     */               
/* 143 */               blockpos$mutableblockpos1.setPos(l3, i2, j4);
/*     */               
/* 145 */               if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos1).getMaterial() == Material.LEAVES) {
/*     */                 
/* 147 */                 BlockPos blockpos3 = blockpos$mutableblockpos1.west();
/* 148 */                 BlockPos blockpos4 = blockpos$mutableblockpos1.east();
/* 149 */                 BlockPos blockpos1 = blockpos$mutableblockpos1.north();
/* 150 */                 BlockPos blockpos2 = blockpos$mutableblockpos1.south();
/*     */                 
/* 152 */                 if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos3).getMaterial() == Material.AIR)
/*     */                 {
/* 154 */                   addVine(worldIn, blockpos3, BlockVine.EAST);
/*     */                 }
/*     */                 
/* 157 */                 if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos4).getMaterial() == Material.AIR)
/*     */                 {
/* 159 */                   addVine(worldIn, blockpos4, BlockVine.WEST);
/*     */                 }
/*     */                 
/* 162 */                 if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos1).getMaterial() == Material.AIR)
/*     */                 {
/* 164 */                   addVine(worldIn, blockpos1, BlockVine.SOUTH);
/*     */                 }
/*     */                 
/* 167 */                 if (rand.nextInt(4) == 0 && worldIn.getBlockState(blockpos2).getMaterial() == Material.AIR)
/*     */                 {
/* 169 */                   addVine(worldIn, blockpos2, BlockVine.NORTH);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 176 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 180 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 186 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addVine(World worldIn, BlockPos pos, PropertyBool prop) {
/* 192 */     IBlockState iblockstate = Blocks.VINE.getDefaultState().withProperty((IProperty)prop, Boolean.valueOf(true));
/* 193 */     setBlockAndNotifyAdequately(worldIn, pos, iblockstate);
/* 194 */     int i = 4;
/*     */     
/* 196 */     for (BlockPos blockpos = pos.down(); worldIn.getBlockState(blockpos).getMaterial() == Material.AIR && i > 0; i--) {
/*     */       
/* 198 */       setBlockAndNotifyAdequately(worldIn, blockpos, iblockstate);
/* 199 */       blockpos = blockpos.down();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenSwamp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */