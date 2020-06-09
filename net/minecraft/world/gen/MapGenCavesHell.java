/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ 
/*     */ public class MapGenCavesHell
/*     */   extends MapGenBase {
/*  12 */   protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
/*     */ 
/*     */   
/*     */   protected void addRoom(long p_180705_1_, int p_180705_3_, int p_180705_4_, ChunkPrimer p_180705_5_, double p_180705_6_, double p_180705_8_, double p_180705_10_) {
/*  16 */     addTunnel(p_180705_1_, p_180705_3_, p_180705_4_, p_180705_5_, p_180705_6_, p_180705_8_, p_180705_10_, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addTunnel(long p_180704_1_, int p_180704_3_, int p_180704_4_, ChunkPrimer p_180704_5_, double p_180704_6_, double p_180704_8_, double p_180704_10_, float p_180704_12_, float p_180704_13_, float p_180704_14_, int p_180704_15_, int p_180704_16_, double p_180704_17_) {
/*  21 */     double d0 = (p_180704_3_ * 16 + 8);
/*  22 */     double d1 = (p_180704_4_ * 16 + 8);
/*  23 */     float f = 0.0F;
/*  24 */     float f1 = 0.0F;
/*  25 */     Random random = new Random(p_180704_1_);
/*     */     
/*  27 */     if (p_180704_16_ <= 0) {
/*     */       
/*  29 */       int i = this.range * 16 - 16;
/*  30 */       p_180704_16_ = i - random.nextInt(i / 4);
/*     */     } 
/*     */     
/*  33 */     boolean flag1 = false;
/*     */     
/*  35 */     if (p_180704_15_ == -1) {
/*     */       
/*  37 */       p_180704_15_ = p_180704_16_ / 2;
/*  38 */       flag1 = true;
/*     */     } 
/*     */     
/*  41 */     int j = random.nextInt(p_180704_16_ / 2) + p_180704_16_ / 4;
/*     */     
/*  43 */     for (boolean flag = (random.nextInt(6) == 0); p_180704_15_ < p_180704_16_; p_180704_15_++) {
/*     */       
/*  45 */       double d2 = 1.5D + (MathHelper.sin(p_180704_15_ * 3.1415927F / p_180704_16_) * p_180704_12_);
/*  46 */       double d3 = d2 * p_180704_17_;
/*  47 */       float f2 = MathHelper.cos(p_180704_14_);
/*  48 */       float f3 = MathHelper.sin(p_180704_14_);
/*  49 */       p_180704_6_ += (MathHelper.cos(p_180704_13_) * f2);
/*  50 */       p_180704_8_ += f3;
/*  51 */       p_180704_10_ += (MathHelper.sin(p_180704_13_) * f2);
/*     */       
/*  53 */       if (flag) {
/*     */         
/*  55 */         p_180704_14_ *= 0.92F;
/*     */       }
/*     */       else {
/*     */         
/*  59 */         p_180704_14_ *= 0.7F;
/*     */       } 
/*     */       
/*  62 */       p_180704_14_ += f1 * 0.1F;
/*  63 */       p_180704_13_ += f * 0.1F;
/*  64 */       f1 *= 0.9F;
/*  65 */       f *= 0.75F;
/*  66 */       f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
/*  67 */       f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
/*     */       
/*  69 */       if (!flag1 && p_180704_15_ == j && p_180704_12_ > 1.0F) {
/*     */         
/*  71 */         addTunnel(random.nextLong(), p_180704_3_, p_180704_4_, p_180704_5_, p_180704_6_, p_180704_8_, p_180704_10_, random.nextFloat() * 0.5F + 0.5F, p_180704_13_ - 1.5707964F, p_180704_14_ / 3.0F, p_180704_15_, p_180704_16_, 1.0D);
/*  72 */         addTunnel(random.nextLong(), p_180704_3_, p_180704_4_, p_180704_5_, p_180704_6_, p_180704_8_, p_180704_10_, random.nextFloat() * 0.5F + 0.5F, p_180704_13_ + 1.5707964F, p_180704_14_ / 3.0F, p_180704_15_, p_180704_16_, 1.0D);
/*     */         
/*     */         return;
/*     */       } 
/*  76 */       if (flag1 || random.nextInt(4) != 0) {
/*     */         
/*  78 */         double d4 = p_180704_6_ - d0;
/*  79 */         double d5 = p_180704_10_ - d1;
/*  80 */         double d6 = (p_180704_16_ - p_180704_15_);
/*  81 */         double d7 = (p_180704_12_ + 2.0F + 16.0F);
/*     */         
/*  83 */         if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  88 */         if (p_180704_6_ >= d0 - 16.0D - d2 * 2.0D && p_180704_10_ >= d1 - 16.0D - d2 * 2.0D && p_180704_6_ <= d0 + 16.0D + d2 * 2.0D && p_180704_10_ <= d1 + 16.0D + d2 * 2.0D) {
/*     */           
/*  90 */           int j2 = MathHelper.floor(p_180704_6_ - d2) - p_180704_3_ * 16 - 1;
/*  91 */           int k = MathHelper.floor(p_180704_6_ + d2) - p_180704_3_ * 16 + 1;
/*  92 */           int k2 = MathHelper.floor(p_180704_8_ - d3) - 1;
/*  93 */           int l = MathHelper.floor(p_180704_8_ + d3) + 1;
/*  94 */           int l2 = MathHelper.floor(p_180704_10_ - d2) - p_180704_4_ * 16 - 1;
/*  95 */           int i1 = MathHelper.floor(p_180704_10_ + d2) - p_180704_4_ * 16 + 1;
/*     */           
/*  97 */           if (j2 < 0)
/*     */           {
/*  99 */             j2 = 0;
/*     */           }
/*     */           
/* 102 */           if (k > 16)
/*     */           {
/* 104 */             k = 16;
/*     */           }
/*     */           
/* 107 */           if (k2 < 1)
/*     */           {
/* 109 */             k2 = 1;
/*     */           }
/*     */           
/* 112 */           if (l > 120)
/*     */           {
/* 114 */             l = 120;
/*     */           }
/*     */           
/* 117 */           if (l2 < 0)
/*     */           {
/* 119 */             l2 = 0;
/*     */           }
/*     */           
/* 122 */           if (i1 > 16)
/*     */           {
/* 124 */             i1 = 16;
/*     */           }
/*     */           
/* 127 */           boolean flag2 = false;
/*     */           
/* 129 */           for (int j1 = j2; !flag2 && j1 < k; j1++) {
/*     */             
/* 131 */             for (int k1 = l2; !flag2 && k1 < i1; k1++) {
/*     */               
/* 133 */               for (int l1 = l + 1; !flag2 && l1 >= k2 - 1; l1--) {
/*     */                 
/* 135 */                 if (l1 >= 0 && l1 < 128) {
/*     */                   
/* 137 */                   IBlockState iblockstate = p_180704_5_.getBlockState(j1, l1, k1);
/*     */                   
/* 139 */                   if (iblockstate.getBlock() == Blocks.FLOWING_LAVA || iblockstate.getBlock() == Blocks.LAVA)
/*     */                   {
/* 141 */                     flag2 = true;
/*     */                   }
/*     */                   
/* 144 */                   if (l1 != k2 - 1 && j1 != j2 && j1 != k - 1 && k1 != l2 && k1 != i1 - 1)
/*     */                   {
/* 146 */                     l1 = k2;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 153 */           if (!flag2) {
/*     */             
/* 155 */             for (int i3 = j2; i3 < k; i3++) {
/*     */               
/* 157 */               double d10 = ((i3 + p_180704_3_ * 16) + 0.5D - p_180704_6_) / d2;
/*     */               
/* 159 */               for (int j3 = l2; j3 < i1; j3++) {
/*     */                 
/* 161 */                 double d8 = ((j3 + p_180704_4_ * 16) + 0.5D - p_180704_10_) / d2;
/*     */                 
/* 163 */                 for (int i2 = l; i2 > k2; i2--) {
/*     */                   
/* 165 */                   double d9 = ((i2 - 1) + 0.5D - p_180704_8_) / d3;
/*     */                   
/* 167 */                   if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D) {
/*     */                     
/* 169 */                     IBlockState iblockstate1 = p_180704_5_.getBlockState(i3, i2, j3);
/*     */                     
/* 171 */                     if (iblockstate1.getBlock() == Blocks.NETHERRACK || iblockstate1.getBlock() == Blocks.DIRT || iblockstate1.getBlock() == Blocks.GRASS)
/*     */                     {
/* 173 */                       p_180704_5_.setBlockState(i3, i2, j3, AIR);
/*     */                     }
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */             
/* 180 */             if (flag1) {
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
/* 195 */     int i = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(10) + 1) + 1);
/*     */     
/* 197 */     if (this.rand.nextInt(5) != 0)
/*     */     {
/* 199 */       i = 0;
/*     */     }
/*     */     
/* 202 */     for (int j = 0; j < i; j++) {
/*     */       
/* 204 */       double d0 = (chunkX * 16 + this.rand.nextInt(16));
/* 205 */       double d1 = this.rand.nextInt(128);
/* 206 */       double d2 = (chunkZ * 16 + this.rand.nextInt(16));
/* 207 */       int k = 1;
/*     */       
/* 209 */       if (this.rand.nextInt(4) == 0) {
/*     */         
/* 211 */         addRoom(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2);
/* 212 */         k += this.rand.nextInt(4);
/*     */       } 
/*     */       
/* 215 */       for (int l = 0; l < k; l++) {
/*     */         
/* 217 */         float f = this.rand.nextFloat() * 6.2831855F;
/* 218 */         float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 219 */         float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
/* 220 */         addTunnel(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2, f2 * 2.0F, f, f1, 0, 0, 0.5D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\MapGenCavesHell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */