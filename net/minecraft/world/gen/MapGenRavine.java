/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ 
/*     */ public class MapGenRavine
/*     */   extends MapGenBase {
/*  13 */   protected static final IBlockState FLOWING_LAVA = Blocks.FLOWING_LAVA.getDefaultState();
/*  14 */   protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
/*  15 */   private final float[] rs = new float[1024];
/*     */ 
/*     */   
/*     */   protected void addTunnel(long p_180707_1_, int p_180707_3_, int p_180707_4_, ChunkPrimer p_180707_5_, double p_180707_6_, double p_180707_8_, double p_180707_10_, float p_180707_12_, float p_180707_13_, float p_180707_14_, int p_180707_15_, int p_180707_16_, double p_180707_17_) {
/*  19 */     Random random = new Random(p_180707_1_);
/*  20 */     double d0 = (p_180707_3_ * 16 + 8);
/*  21 */     double d1 = (p_180707_4_ * 16 + 8);
/*  22 */     float f = 0.0F;
/*  23 */     float f1 = 0.0F;
/*     */     
/*  25 */     if (p_180707_16_ <= 0) {
/*     */       
/*  27 */       int i = this.range * 16 - 16;
/*  28 */       p_180707_16_ = i - random.nextInt(i / 4);
/*     */     } 
/*     */     
/*  31 */     boolean flag1 = false;
/*     */     
/*  33 */     if (p_180707_15_ == -1) {
/*     */       
/*  35 */       p_180707_15_ = p_180707_16_ / 2;
/*  36 */       flag1 = true;
/*     */     } 
/*     */     
/*  39 */     float f2 = 1.0F;
/*     */     
/*  41 */     for (int j = 0; j < 256; j++) {
/*     */       
/*  43 */       if (j == 0 || random.nextInt(3) == 0)
/*     */       {
/*  45 */         f2 = 1.0F + random.nextFloat() * random.nextFloat();
/*     */       }
/*     */       
/*  48 */       this.rs[j] = f2 * f2;
/*     */     } 
/*     */     
/*  51 */     for (; p_180707_15_ < p_180707_16_; p_180707_15_++) {
/*     */       
/*  53 */       double d9 = 1.5D + (MathHelper.sin(p_180707_15_ * 3.1415927F / p_180707_16_) * p_180707_12_);
/*  54 */       double d2 = d9 * p_180707_17_;
/*  55 */       d9 *= random.nextFloat() * 0.25D + 0.75D;
/*  56 */       d2 *= random.nextFloat() * 0.25D + 0.75D;
/*  57 */       float f3 = MathHelper.cos(p_180707_14_);
/*  58 */       float f4 = MathHelper.sin(p_180707_14_);
/*  59 */       p_180707_6_ += (MathHelper.cos(p_180707_13_) * f3);
/*  60 */       p_180707_8_ += f4;
/*  61 */       p_180707_10_ += (MathHelper.sin(p_180707_13_) * f3);
/*  62 */       p_180707_14_ *= 0.7F;
/*  63 */       p_180707_14_ += f1 * 0.05F;
/*  64 */       p_180707_13_ += f * 0.05F;
/*  65 */       f1 *= 0.8F;
/*  66 */       f *= 0.5F;
/*  67 */       f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
/*  68 */       f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
/*     */       
/*  70 */       if (flag1 || random.nextInt(4) != 0) {
/*     */         
/*  72 */         double d3 = p_180707_6_ - d0;
/*  73 */         double d4 = p_180707_10_ - d1;
/*  74 */         double d5 = (p_180707_16_ - p_180707_15_);
/*  75 */         double d6 = (p_180707_12_ + 2.0F + 16.0F);
/*     */         
/*  77 */         if (d3 * d3 + d4 * d4 - d5 * d5 > d6 * d6) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  82 */         if (p_180707_6_ >= d0 - 16.0D - d9 * 2.0D && p_180707_10_ >= d1 - 16.0D - d9 * 2.0D && p_180707_6_ <= d0 + 16.0D + d9 * 2.0D && p_180707_10_ <= d1 + 16.0D + d9 * 2.0D) {
/*     */           
/*  84 */           int k2 = MathHelper.floor(p_180707_6_ - d9) - p_180707_3_ * 16 - 1;
/*  85 */           int k = MathHelper.floor(p_180707_6_ + d9) - p_180707_3_ * 16 + 1;
/*  86 */           int l2 = MathHelper.floor(p_180707_8_ - d2) - 1;
/*  87 */           int l = MathHelper.floor(p_180707_8_ + d2) + 1;
/*  88 */           int i3 = MathHelper.floor(p_180707_10_ - d9) - p_180707_4_ * 16 - 1;
/*  89 */           int i1 = MathHelper.floor(p_180707_10_ + d9) - p_180707_4_ * 16 + 1;
/*     */           
/*  91 */           if (k2 < 0)
/*     */           {
/*  93 */             k2 = 0;
/*     */           }
/*     */           
/*  96 */           if (k > 16)
/*     */           {
/*  98 */             k = 16;
/*     */           }
/*     */           
/* 101 */           if (l2 < 1)
/*     */           {
/* 103 */             l2 = 1;
/*     */           }
/*     */           
/* 106 */           if (l > 248)
/*     */           {
/* 108 */             l = 248;
/*     */           }
/*     */           
/* 111 */           if (i3 < 0)
/*     */           {
/* 113 */             i3 = 0;
/*     */           }
/*     */           
/* 116 */           if (i1 > 16)
/*     */           {
/* 118 */             i1 = 16;
/*     */           }
/*     */           
/* 121 */           boolean flag2 = false;
/*     */           
/* 123 */           for (int j1 = k2; !flag2 && j1 < k; j1++) {
/*     */             
/* 125 */             for (int k1 = i3; !flag2 && k1 < i1; k1++) {
/*     */               
/* 127 */               for (int l1 = l + 1; !flag2 && l1 >= l2 - 1; l1--) {
/*     */                 
/* 129 */                 if (l1 >= 0 && l1 < 256) {
/*     */                   
/* 131 */                   IBlockState iblockstate = p_180707_5_.getBlockState(j1, l1, k1);
/*     */                   
/* 133 */                   if (iblockstate.getBlock() == Blocks.FLOWING_WATER || iblockstate.getBlock() == Blocks.WATER)
/*     */                   {
/* 135 */                     flag2 = true;
/*     */                   }
/*     */                   
/* 138 */                   if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1)
/*     */                   {
/* 140 */                     l1 = l2;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 147 */           if (!flag2) {
/*     */             
/* 149 */             BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */             
/* 151 */             for (int j3 = k2; j3 < k; j3++) {
/*     */               
/* 153 */               double d10 = ((j3 + p_180707_3_ * 16) + 0.5D - p_180707_6_) / d9;
/*     */               
/* 155 */               for (int i2 = i3; i2 < i1; i2++) {
/*     */                 
/* 157 */                 double d7 = ((i2 + p_180707_4_ * 16) + 0.5D - p_180707_10_) / d9;
/* 158 */                 boolean flag = false;
/*     */                 
/* 160 */                 if (d10 * d10 + d7 * d7 < 1.0D)
/*     */                 {
/* 162 */                   for (int j2 = l; j2 > l2; j2--) {
/*     */                     
/* 164 */                     double d8 = ((j2 - 1) + 0.5D - p_180707_8_) / d2;
/*     */                     
/* 166 */                     if ((d10 * d10 + d7 * d7) * this.rs[j2 - 1] + d8 * d8 / 6.0D < 1.0D) {
/*     */                       
/* 168 */                       IBlockState iblockstate1 = p_180707_5_.getBlockState(j3, j2, i2);
/*     */                       
/* 170 */                       if (iblockstate1.getBlock() == Blocks.GRASS)
/*     */                       {
/* 172 */                         flag = true;
/*     */                       }
/*     */                       
/* 175 */                       if (iblockstate1.getBlock() == Blocks.STONE || iblockstate1.getBlock() == Blocks.DIRT || iblockstate1.getBlock() == Blocks.GRASS)
/*     */                       {
/* 177 */                         if (j2 - 1 < 10) {
/*     */                           
/* 179 */                           p_180707_5_.setBlockState(j3, j2, i2, FLOWING_LAVA);
/*     */                         }
/*     */                         else {
/*     */                           
/* 183 */                           p_180707_5_.setBlockState(j3, j2, i2, AIR);
/*     */                           
/* 185 */                           if (flag && p_180707_5_.getBlockState(j3, j2 - 1, i2).getBlock() == Blocks.DIRT) {
/*     */                             
/* 187 */                             blockpos$mutableblockpos.setPos(j3 + p_180707_3_ * 16, 0, i2 + p_180707_4_ * 16);
/* 188 */                             p_180707_5_.setBlockState(j3, j2 - 1, i2, (this.worldObj.getBiome((BlockPos)blockpos$mutableblockpos)).topBlock);
/*     */                           } 
/*     */                         } 
/*     */                       }
/*     */                     } 
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 198 */             if (flag1) {
/*     */               break;
/*     */             }
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
/*     */   protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn) {
/* 213 */     if (this.rand.nextInt(50) == 0) {
/*     */       
/* 215 */       double d0 = (chunkX * 16 + this.rand.nextInt(16));
/* 216 */       double d1 = (this.rand.nextInt(this.rand.nextInt(40) + 8) + 20);
/* 217 */       double d2 = (chunkZ * 16 + this.rand.nextInt(16));
/* 218 */       int i = 1;
/*     */       
/* 220 */       for (int j = 0; j < 1; j++) {
/*     */         
/* 222 */         float f = this.rand.nextFloat() * 6.2831855F;
/* 223 */         float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 224 */         float f2 = (this.rand.nextFloat() * 2.0F + this.rand.nextFloat()) * 2.0F;
/* 225 */         addTunnel(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2, f2, f, f1, 0, 0, 3.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\MapGenRavine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */