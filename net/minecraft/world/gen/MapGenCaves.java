/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ 
/*     */ public class MapGenCaves
/*     */   extends MapGenBase {
/*  15 */   protected static final IBlockState BLK_LAVA = Blocks.LAVA.getDefaultState();
/*  16 */   protected static final IBlockState BLK_AIR = Blocks.AIR.getDefaultState();
/*  17 */   protected static final IBlockState BLK_SANDSTONE = Blocks.SANDSTONE.getDefaultState();
/*  18 */   protected static final IBlockState BLK_RED_SANDSTONE = Blocks.RED_SANDSTONE.getDefaultState();
/*     */ 
/*     */   
/*     */   protected void addRoom(long p_180703_1_, int p_180703_3_, int p_180703_4_, ChunkPrimer p_180703_5_, double p_180703_6_, double p_180703_8_, double p_180703_10_) {
/*  22 */     addTunnel(p_180703_1_, p_180703_3_, p_180703_4_, p_180703_5_, p_180703_6_, p_180703_8_, p_180703_10_, 1.0F + this.rand.nextFloat() * 6.0F, 0.0F, 0.0F, -1, -1, 0.5D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addTunnel(long p_180702_1_, int p_180702_3_, int p_180702_4_, ChunkPrimer p_180702_5_, double p_180702_6_, double p_180702_8_, double p_180702_10_, float p_180702_12_, float p_180702_13_, float p_180702_14_, int p_180702_15_, int p_180702_16_, double p_180702_17_) {
/*  27 */     double d0 = (p_180702_3_ * 16 + 8);
/*  28 */     double d1 = (p_180702_4_ * 16 + 8);
/*  29 */     float f = 0.0F;
/*  30 */     float f1 = 0.0F;
/*  31 */     Random random = new Random(p_180702_1_);
/*     */     
/*  33 */     if (p_180702_16_ <= 0) {
/*     */       
/*  35 */       int i = this.range * 16 - 16;
/*  36 */       p_180702_16_ = i - random.nextInt(i / 4);
/*     */     } 
/*     */     
/*  39 */     boolean flag2 = false;
/*     */     
/*  41 */     if (p_180702_15_ == -1) {
/*     */       
/*  43 */       p_180702_15_ = p_180702_16_ / 2;
/*  44 */       flag2 = true;
/*     */     } 
/*     */     
/*  47 */     int j = random.nextInt(p_180702_16_ / 2) + p_180702_16_ / 4;
/*     */     
/*  49 */     for (boolean flag = (random.nextInt(6) == 0); p_180702_15_ < p_180702_16_; p_180702_15_++) {
/*     */       
/*  51 */       double d2 = 1.5D + (MathHelper.sin(p_180702_15_ * 3.1415927F / p_180702_16_) * p_180702_12_);
/*  52 */       double d3 = d2 * p_180702_17_;
/*  53 */       float f2 = MathHelper.cos(p_180702_14_);
/*  54 */       float f3 = MathHelper.sin(p_180702_14_);
/*  55 */       p_180702_6_ += (MathHelper.cos(p_180702_13_) * f2);
/*  56 */       p_180702_8_ += f3;
/*  57 */       p_180702_10_ += (MathHelper.sin(p_180702_13_) * f2);
/*     */       
/*  59 */       if (flag) {
/*     */         
/*  61 */         p_180702_14_ *= 0.92F;
/*     */       }
/*     */       else {
/*     */         
/*  65 */         p_180702_14_ *= 0.7F;
/*     */       } 
/*     */       
/*  68 */       p_180702_14_ += f1 * 0.1F;
/*  69 */       p_180702_13_ += f * 0.1F;
/*  70 */       f1 *= 0.9F;
/*  71 */       f *= 0.75F;
/*  72 */       f1 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
/*  73 */       f += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
/*     */       
/*  75 */       if (!flag2 && p_180702_15_ == j && p_180702_12_ > 1.0F && p_180702_16_ > 0) {
/*     */         
/*  77 */         addTunnel(random.nextLong(), p_180702_3_, p_180702_4_, p_180702_5_, p_180702_6_, p_180702_8_, p_180702_10_, random.nextFloat() * 0.5F + 0.5F, p_180702_13_ - 1.5707964F, p_180702_14_ / 3.0F, p_180702_15_, p_180702_16_, 1.0D);
/*  78 */         addTunnel(random.nextLong(), p_180702_3_, p_180702_4_, p_180702_5_, p_180702_6_, p_180702_8_, p_180702_10_, random.nextFloat() * 0.5F + 0.5F, p_180702_13_ + 1.5707964F, p_180702_14_ / 3.0F, p_180702_15_, p_180702_16_, 1.0D);
/*     */         
/*     */         return;
/*     */       } 
/*  82 */       if (flag2 || random.nextInt(4) != 0) {
/*     */         
/*  84 */         double d4 = p_180702_6_ - d0;
/*  85 */         double d5 = p_180702_10_ - d1;
/*  86 */         double d6 = (p_180702_16_ - p_180702_15_);
/*  87 */         double d7 = (p_180702_12_ + 2.0F + 16.0F);
/*     */         
/*  89 */         if (d4 * d4 + d5 * d5 - d6 * d6 > d7 * d7) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/*  94 */         if (p_180702_6_ >= d0 - 16.0D - d2 * 2.0D && p_180702_10_ >= d1 - 16.0D - d2 * 2.0D && p_180702_6_ <= d0 + 16.0D + d2 * 2.0D && p_180702_10_ <= d1 + 16.0D + d2 * 2.0D) {
/*     */           
/*  96 */           int k2 = MathHelper.floor(p_180702_6_ - d2) - p_180702_3_ * 16 - 1;
/*  97 */           int k = MathHelper.floor(p_180702_6_ + d2) - p_180702_3_ * 16 + 1;
/*  98 */           int l2 = MathHelper.floor(p_180702_8_ - d3) - 1;
/*  99 */           int l = MathHelper.floor(p_180702_8_ + d3) + 1;
/* 100 */           int i3 = MathHelper.floor(p_180702_10_ - d2) - p_180702_4_ * 16 - 1;
/* 101 */           int i1 = MathHelper.floor(p_180702_10_ + d2) - p_180702_4_ * 16 + 1;
/*     */           
/* 103 */           if (k2 < 0)
/*     */           {
/* 105 */             k2 = 0;
/*     */           }
/*     */           
/* 108 */           if (k > 16)
/*     */           {
/* 110 */             k = 16;
/*     */           }
/*     */           
/* 113 */           if (l2 < 1)
/*     */           {
/* 115 */             l2 = 1;
/*     */           }
/*     */           
/* 118 */           if (l > 248)
/*     */           {
/* 120 */             l = 248;
/*     */           }
/*     */           
/* 123 */           if (i3 < 0)
/*     */           {
/* 125 */             i3 = 0;
/*     */           }
/*     */           
/* 128 */           if (i1 > 16)
/*     */           {
/* 130 */             i1 = 16;
/*     */           }
/*     */           
/* 133 */           boolean flag3 = false;
/*     */           
/* 135 */           for (int j1 = k2; !flag3 && j1 < k; j1++) {
/*     */             
/* 137 */             for (int k1 = i3; !flag3 && k1 < i1; k1++) {
/*     */               
/* 139 */               for (int l1 = l + 1; !flag3 && l1 >= l2 - 1; l1--) {
/*     */                 
/* 141 */                 if (l1 >= 0 && l1 < 256) {
/*     */                   
/* 143 */                   IBlockState iblockstate = p_180702_5_.getBlockState(j1, l1, k1);
/*     */                   
/* 145 */                   if (iblockstate.getBlock() == Blocks.FLOWING_WATER || iblockstate.getBlock() == Blocks.WATER)
/*     */                   {
/* 147 */                     flag3 = true;
/*     */                   }
/*     */                   
/* 150 */                   if (l1 != l2 - 1 && j1 != k2 && j1 != k - 1 && k1 != i3 && k1 != i1 - 1)
/*     */                   {
/* 152 */                     l1 = l2;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 159 */           if (!flag3) {
/*     */             
/* 161 */             BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */             
/* 163 */             for (int j3 = k2; j3 < k; j3++) {
/*     */               
/* 165 */               double d10 = ((j3 + p_180702_3_ * 16) + 0.5D - p_180702_6_) / d2;
/*     */               
/* 167 */               for (int i2 = i3; i2 < i1; i2++) {
/*     */                 
/* 169 */                 double d8 = ((i2 + p_180702_4_ * 16) + 0.5D - p_180702_10_) / d2;
/* 170 */                 boolean flag1 = false;
/*     */                 
/* 172 */                 if (d10 * d10 + d8 * d8 < 1.0D)
/*     */                 {
/* 174 */                   for (int j2 = l; j2 > l2; j2--) {
/*     */                     
/* 176 */                     double d9 = ((j2 - 1) + 0.5D - p_180702_8_) / d3;
/*     */                     
/* 178 */                     if (d9 > -0.7D && d10 * d10 + d9 * d9 + d8 * d8 < 1.0D) {
/*     */                       
/* 180 */                       IBlockState iblockstate1 = p_180702_5_.getBlockState(j3, j2, i2);
/* 181 */                       IBlockState iblockstate2 = (IBlockState)MoreObjects.firstNonNull(p_180702_5_.getBlockState(j3, j2 + 1, i2), BLK_AIR);
/*     */                       
/* 183 */                       if (iblockstate1.getBlock() == Blocks.GRASS || iblockstate1.getBlock() == Blocks.MYCELIUM)
/*     */                       {
/* 185 */                         flag1 = true;
/*     */                       }
/*     */                       
/* 188 */                       if (canReplaceBlock(iblockstate1, iblockstate2))
/*     */                       {
/* 190 */                         if (j2 - 1 < 10) {
/*     */                           
/* 192 */                           p_180702_5_.setBlockState(j3, j2, i2, BLK_LAVA);
/*     */                         }
/*     */                         else {
/*     */                           
/* 196 */                           p_180702_5_.setBlockState(j3, j2, i2, BLK_AIR);
/*     */                           
/* 198 */                           if (flag1 && p_180702_5_.getBlockState(j3, j2 - 1, i2).getBlock() == Blocks.DIRT) {
/*     */                             
/* 200 */                             blockpos$mutableblockpos.setPos(j3 + p_180702_3_ * 16, 0, i2 + p_180702_4_ * 16);
/* 201 */                             p_180702_5_.setBlockState(j3, j2 - 1, i2, (this.worldObj.getBiome((BlockPos)blockpos$mutableblockpos)).topBlock.getBlock().getDefaultState());
/*     */                           } 
/*     */                         } 
/*     */                       }
/*     */                     } 
/*     */                   } 
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 211 */             if (flag2) {
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
/*     */   protected boolean canReplaceBlock(IBlockState p_175793_1_, IBlockState p_175793_2_) {
/* 223 */     if (p_175793_1_.getBlock() == Blocks.STONE)
/*     */     {
/* 225 */       return true;
/*     */     }
/* 227 */     if (p_175793_1_.getBlock() == Blocks.DIRT)
/*     */     {
/* 229 */       return true;
/*     */     }
/* 231 */     if (p_175793_1_.getBlock() == Blocks.GRASS)
/*     */     {
/* 233 */       return true;
/*     */     }
/* 235 */     if (p_175793_1_.getBlock() == Blocks.HARDENED_CLAY)
/*     */     {
/* 237 */       return true;
/*     */     }
/* 239 */     if (p_175793_1_.getBlock() == Blocks.STAINED_HARDENED_CLAY)
/*     */     {
/* 241 */       return true;
/*     */     }
/* 243 */     if (p_175793_1_.getBlock() == Blocks.SANDSTONE)
/*     */     {
/* 245 */       return true;
/*     */     }
/* 247 */     if (p_175793_1_.getBlock() == Blocks.RED_SANDSTONE)
/*     */     {
/* 249 */       return true;
/*     */     }
/* 251 */     if (p_175793_1_.getBlock() == Blocks.MYCELIUM)
/*     */     {
/* 253 */       return true;
/*     */     }
/* 255 */     if (p_175793_1_.getBlock() == Blocks.SNOW_LAYER)
/*     */     {
/* 257 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 261 */     return ((p_175793_1_.getBlock() == Blocks.SAND || p_175793_1_.getBlock() == Blocks.GRAVEL) && p_175793_2_.getMaterial() != Material.WATER);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void recursiveGenerate(World worldIn, int chunkX, int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn) {
/* 270 */     int i = this.rand.nextInt(this.rand.nextInt(this.rand.nextInt(15) + 1) + 1);
/*     */     
/* 272 */     if (this.rand.nextInt(7) != 0)
/*     */     {
/* 274 */       i = 0;
/*     */     }
/*     */     
/* 277 */     for (int j = 0; j < i; j++) {
/*     */       
/* 279 */       double d0 = (chunkX * 16 + this.rand.nextInt(16));
/* 280 */       double d1 = this.rand.nextInt(this.rand.nextInt(120) + 8);
/* 281 */       double d2 = (chunkZ * 16 + this.rand.nextInt(16));
/* 282 */       int k = 1;
/*     */       
/* 284 */       if (this.rand.nextInt(4) == 0) {
/*     */         
/* 286 */         addRoom(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2);
/* 287 */         k += this.rand.nextInt(4);
/*     */       } 
/*     */       
/* 290 */       for (int l = 0; l < k; l++) {
/*     */         
/* 292 */         float f = this.rand.nextFloat() * 6.2831855F;
/* 293 */         float f1 = (this.rand.nextFloat() - 0.5F) * 2.0F / 8.0F;
/* 294 */         float f2 = this.rand.nextFloat() * 2.0F + this.rand.nextFloat();
/*     */         
/* 296 */         if (this.rand.nextInt(10) == 0)
/*     */         {
/* 298 */           f2 *= this.rand.nextFloat() * this.rand.nextFloat() * 3.0F + 1.0F;
/*     */         }
/*     */         
/* 301 */         addTunnel(this.rand.nextLong(), p_180701_4_, p_180701_5_, chunkPrimerIn, d0, d1, d2, f2, f, f1, 0, 0, 1.0D);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\MapGenCaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */