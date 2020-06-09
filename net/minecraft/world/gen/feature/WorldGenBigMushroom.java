/*     */ package net.minecraft.world.gen.feature;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockHugeMushroom;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class WorldGenBigMushroom
/*     */   extends WorldGenerator
/*     */ {
/*     */   private final Block mushroomType;
/*     */   
/*     */   public WorldGenBigMushroom(Block p_i46449_1_) {
/*  19 */     super(true);
/*  20 */     this.mushroomType = p_i46449_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenBigMushroom() {
/*  25 */     super(false);
/*  26 */     this.mushroomType = null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/*  31 */     Block block = this.mushroomType;
/*     */     
/*  33 */     if (block == null)
/*     */     {
/*  35 */       block = rand.nextBoolean() ? Blocks.BROWN_MUSHROOM_BLOCK : Blocks.RED_MUSHROOM_BLOCK;
/*     */     }
/*     */     
/*  38 */     int i = rand.nextInt(3) + 4;
/*     */     
/*  40 */     if (rand.nextInt(12) == 0)
/*     */     {
/*  42 */       i *= 2;
/*     */     }
/*     */     
/*  45 */     boolean flag = true;
/*     */     
/*  47 */     if (position.getY() >= 1 && position.getY() + i + 1 < 256) {
/*     */       
/*  49 */       for (int j = position.getY(); j <= position.getY() + 1 + i; j++) {
/*     */         
/*  51 */         int k = 3;
/*     */         
/*  53 */         if (j <= position.getY() + 3)
/*     */         {
/*  55 */           k = 0;
/*     */         }
/*     */         
/*  58 */         BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */         
/*  60 */         for (int l = position.getX() - k; l <= position.getX() + k && flag; l++) {
/*     */           
/*  62 */           for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; i1++) {
/*     */             
/*  64 */             if (j >= 0 && j < 256) {
/*     */               
/*  66 */               Material material = worldIn.getBlockState((BlockPos)blockpos$mutableblockpos.setPos(l, j, i1)).getMaterial();
/*     */               
/*  68 */               if (material != Material.AIR && material != Material.LEAVES)
/*     */               {
/*  70 */                 flag = false;
/*     */               }
/*     */             }
/*     */             else {
/*     */               
/*  75 */               flag = false;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/*  81 */       if (!flag)
/*     */       {
/*  83 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  87 */       Block block1 = worldIn.getBlockState(position.down()).getBlock();
/*     */       
/*  89 */       if (block1 != Blocks.DIRT && block1 != Blocks.GRASS && block1 != Blocks.MYCELIUM)
/*     */       {
/*  91 */         return false;
/*     */       }
/*     */ 
/*     */       
/*  95 */       int k2 = position.getY() + i;
/*     */       
/*  97 */       if (block == Blocks.RED_MUSHROOM_BLOCK)
/*     */       {
/*  99 */         k2 = position.getY() + i - 3;
/*     */       }
/*     */       
/* 102 */       for (int l2 = k2; l2 <= position.getY() + i; l2++) {
/*     */         
/* 104 */         int j3 = 1;
/*     */         
/* 106 */         if (l2 < position.getY() + i)
/*     */         {
/* 108 */           j3++;
/*     */         }
/*     */         
/* 111 */         if (block == Blocks.BROWN_MUSHROOM_BLOCK)
/*     */         {
/* 113 */           j3 = 3;
/*     */         }
/*     */         
/* 116 */         int k3 = position.getX() - j3;
/* 117 */         int l3 = position.getX() + j3;
/* 118 */         int j1 = position.getZ() - j3;
/* 119 */         int k1 = position.getZ() + j3;
/*     */         
/* 121 */         for (int l1 = k3; l1 <= l3; l1++) {
/*     */           
/* 123 */           for (int i2 = j1; i2 <= k1; i2++) {
/*     */             
/* 125 */             int j2 = 5;
/*     */             
/* 127 */             if (l1 == k3) {
/*     */               
/* 129 */               j2--;
/*     */             }
/* 131 */             else if (l1 == l3) {
/*     */               
/* 133 */               j2++;
/*     */             } 
/*     */             
/* 136 */             if (i2 == j1) {
/*     */               
/* 138 */               j2 -= 3;
/*     */             }
/* 140 */             else if (i2 == k1) {
/*     */               
/* 142 */               j2 += 3;
/*     */             } 
/*     */             
/* 145 */             BlockHugeMushroom.EnumType blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.byMetadata(j2);
/*     */             
/* 147 */             if (block == Blocks.BROWN_MUSHROOM_BLOCK || l2 < position.getY() + i) {
/*     */               
/* 149 */               if ((l1 == k3 || l1 == l3) && (i2 == j1 || i2 == k1)) {
/*     */                 continue;
/*     */               }
/*     */ 
/*     */               
/* 154 */               if (l1 == position.getX() - j3 - 1 && i2 == j1)
/*     */               {
/* 156 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_WEST;
/*     */               }
/*     */               
/* 159 */               if (l1 == k3 && i2 == position.getZ() - j3 - 1)
/*     */               {
/* 161 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_WEST;
/*     */               }
/*     */               
/* 164 */               if (l1 == position.getX() + j3 - 1 && i2 == j1)
/*     */               {
/* 166 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_EAST;
/*     */               }
/*     */               
/* 169 */               if (l1 == l3 && i2 == position.getZ() - j3 - 1)
/*     */               {
/* 171 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.NORTH_EAST;
/*     */               }
/*     */               
/* 174 */               if (l1 == position.getX() - j3 - 1 && i2 == k1)
/*     */               {
/* 176 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_WEST;
/*     */               }
/*     */               
/* 179 */               if (l1 == k3 && i2 == position.getZ() + j3 - 1)
/*     */               {
/* 181 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_WEST;
/*     */               }
/*     */               
/* 184 */               if (l1 == position.getX() + j3 - 1 && i2 == k1)
/*     */               {
/* 186 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_EAST;
/*     */               }
/*     */               
/* 189 */               if (l1 == l3 && i2 == position.getZ() + j3 - 1)
/*     */               {
/* 191 */                 blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.SOUTH_EAST;
/*     */               }
/*     */             } 
/*     */             
/* 195 */             if (blockhugemushroom$enumtype == BlockHugeMushroom.EnumType.CENTER && l2 < position.getY() + i)
/*     */             {
/* 197 */               blockhugemushroom$enumtype = BlockHugeMushroom.EnumType.ALL_INSIDE;
/*     */             }
/*     */             
/* 200 */             if (position.getY() >= position.getY() + i - 1 || blockhugemushroom$enumtype != BlockHugeMushroom.EnumType.ALL_INSIDE) {
/*     */               
/* 202 */               BlockPos blockpos = new BlockPos(l1, l2, i2);
/*     */               
/* 204 */               if (!worldIn.getBlockState(blockpos).isFullBlock())
/*     */               {
/* 206 */                 setBlockAndNotifyAdequately(worldIn, blockpos, block.getDefaultState().withProperty((IProperty)BlockHugeMushroom.VARIANT, (Comparable)blockhugemushroom$enumtype));
/*     */               }
/*     */             } 
/*     */             continue;
/*     */           } 
/*     */         } 
/*     */       } 
/* 213 */       for (int i3 = 0; i3 < i; i3++) {
/*     */         
/* 215 */         IBlockState iblockstate = worldIn.getBlockState(position.up(i3));
/*     */         
/* 217 */         if (!iblockstate.isFullBlock())
/*     */         {
/* 219 */           setBlockAndNotifyAdequately(worldIn, position.up(i3), block.getDefaultState().withProperty((IProperty)BlockHugeMushroom.VARIANT, (Comparable)BlockHugeMushroom.EnumType.STEM));
/*     */         }
/*     */       } 
/*     */       
/* 223 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 229 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenBigMushroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */