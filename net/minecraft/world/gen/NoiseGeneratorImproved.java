/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NoiseGeneratorImproved
/*     */   extends NoiseGenerator
/*     */ {
/*     */   private final int[] permutations;
/*     */   public double xCoord;
/*     */   public double yCoord;
/*     */   public double zCoord;
/*  17 */   private static final double[] GRAD_X = new double[] { 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D };
/*  18 */   private static final double[] GRAD_Y = new double[] { 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D };
/*  19 */   private static final double[] GRAD_Z = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D };
/*  20 */   private static final double[] GRAD_2X = new double[] { 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 1.0D, -1.0D, 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 0.0D, -1.0D, 0.0D };
/*  21 */   private static final double[] GRAD_2Z = new double[] { 0.0D, 0.0D, 0.0D, 0.0D, 1.0D, 1.0D, -1.0D, -1.0D, 1.0D, 1.0D, -1.0D, -1.0D, 0.0D, 1.0D, 0.0D, -1.0D };
/*     */ 
/*     */   
/*     */   public NoiseGeneratorImproved() {
/*  25 */     this(new Random());
/*     */   }
/*     */ 
/*     */   
/*     */   public NoiseGeneratorImproved(Random p_i45469_1_) {
/*  30 */     this.permutations = new int[512];
/*  31 */     this.xCoord = p_i45469_1_.nextDouble() * 256.0D;
/*  32 */     this.yCoord = p_i45469_1_.nextDouble() * 256.0D;
/*  33 */     this.zCoord = p_i45469_1_.nextDouble() * 256.0D;
/*     */     
/*  35 */     for (int i = 0; i < 256; this.permutations[i] = i++);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  40 */     for (int l = 0; l < 256; l++) {
/*     */       
/*  42 */       int j = p_i45469_1_.nextInt(256 - l) + l;
/*  43 */       int k = this.permutations[l];
/*  44 */       this.permutations[l] = this.permutations[j];
/*  45 */       this.permutations[j] = k;
/*  46 */       this.permutations[l + 256] = this.permutations[l];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final double lerp(double p_76311_1_, double p_76311_3_, double p_76311_5_) {
/*  52 */     return p_76311_3_ + p_76311_1_ * (p_76311_5_ - p_76311_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   public final double grad2(int p_76309_1_, double p_76309_2_, double p_76309_4_) {
/*  57 */     int i = p_76309_1_ & 0xF;
/*  58 */     return GRAD_2X[i] * p_76309_2_ + GRAD_2Z[i] * p_76309_4_;
/*     */   }
/*     */ 
/*     */   
/*     */   public final double grad(int p_76310_1_, double p_76310_2_, double p_76310_4_, double p_76310_6_) {
/*  63 */     int i = p_76310_1_ & 0xF;
/*  64 */     return GRAD_X[i] * p_76310_2_ + GRAD_Y[i] * p_76310_4_ + GRAD_Z[i] * p_76310_6_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void populateNoiseArray(double[] noiseArray, double xOffset, double yOffset, double zOffset, int xSize, int ySize, int zSize, double xScale, double yScale, double zScale, double noiseScale) {
/*  72 */     if (ySize == 1) {
/*     */       
/*  74 */       int i5 = 0;
/*  75 */       int j5 = 0;
/*  76 */       int j = 0;
/*  77 */       int k5 = 0;
/*  78 */       double d14 = 0.0D;
/*  79 */       double d15 = 0.0D;
/*  80 */       int l5 = 0;
/*  81 */       double d16 = 1.0D / noiseScale;
/*     */       
/*  83 */       for (int j2 = 0; j2 < xSize; j2++) {
/*     */         
/*  85 */         double d17 = xOffset + j2 * xScale + this.xCoord;
/*  86 */         int i6 = (int)d17;
/*     */         
/*  88 */         if (d17 < i6)
/*     */         {
/*  90 */           i6--;
/*     */         }
/*     */         
/*  93 */         int k2 = i6 & 0xFF;
/*  94 */         d17 -= i6;
/*  95 */         double d18 = d17 * d17 * d17 * (d17 * (d17 * 6.0D - 15.0D) + 10.0D);
/*     */         
/*  97 */         for (int j6 = 0; j6 < zSize; j6++)
/*     */         {
/*  99 */           double d19 = zOffset + j6 * zScale + this.zCoord;
/* 100 */           int k6 = (int)d19;
/*     */           
/* 102 */           if (d19 < k6)
/*     */           {
/* 104 */             k6--;
/*     */           }
/*     */           
/* 107 */           int l6 = k6 & 0xFF;
/* 108 */           d19 -= k6;
/* 109 */           double d20 = d19 * d19 * d19 * (d19 * (d19 * 6.0D - 15.0D) + 10.0D);
/* 110 */           i5 = this.permutations[k2] + 0;
/* 111 */           j5 = this.permutations[i5] + l6;
/* 112 */           j = this.permutations[k2 + 1] + 0;
/* 113 */           k5 = this.permutations[j] + l6;
/* 114 */           d14 = lerp(d18, grad2(this.permutations[j5], d17, d19), grad(this.permutations[k5], d17 - 1.0D, 0.0D, d19));
/* 115 */           d15 = lerp(d18, grad(this.permutations[j5 + 1], d17, 0.0D, d19 - 1.0D), grad(this.permutations[k5 + 1], d17 - 1.0D, 0.0D, d19 - 1.0D));
/* 116 */           double d21 = lerp(d20, d14, d15);
/* 117 */           int i7 = l5++;
/* 118 */           noiseArray[i7] = noiseArray[i7] + d21 * d16;
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 124 */       int i = 0;
/* 125 */       double d0 = 1.0D / noiseScale;
/* 126 */       int k = -1;
/* 127 */       int l = 0;
/* 128 */       int i1 = 0;
/* 129 */       int j1 = 0;
/* 130 */       int k1 = 0;
/* 131 */       int l1 = 0;
/* 132 */       int i2 = 0;
/* 133 */       double d1 = 0.0D;
/* 134 */       double d2 = 0.0D;
/* 135 */       double d3 = 0.0D;
/* 136 */       double d4 = 0.0D;
/*     */       
/* 138 */       for (int l2 = 0; l2 < xSize; l2++) {
/*     */         
/* 140 */         double d5 = xOffset + l2 * xScale + this.xCoord;
/* 141 */         int i3 = (int)d5;
/*     */         
/* 143 */         if (d5 < i3)
/*     */         {
/* 145 */           i3--;
/*     */         }
/*     */         
/* 148 */         int j3 = i3 & 0xFF;
/* 149 */         d5 -= i3;
/* 150 */         double d6 = d5 * d5 * d5 * (d5 * (d5 * 6.0D - 15.0D) + 10.0D);
/*     */         
/* 152 */         for (int k3 = 0; k3 < zSize; k3++) {
/*     */           
/* 154 */           double d7 = zOffset + k3 * zScale + this.zCoord;
/* 155 */           int l3 = (int)d7;
/*     */           
/* 157 */           if (d7 < l3)
/*     */           {
/* 159 */             l3--;
/*     */           }
/*     */           
/* 162 */           int i4 = l3 & 0xFF;
/* 163 */           d7 -= l3;
/* 164 */           double d8 = d7 * d7 * d7 * (d7 * (d7 * 6.0D - 15.0D) + 10.0D);
/*     */           
/* 166 */           for (int j4 = 0; j4 < ySize; j4++) {
/*     */             
/* 168 */             double d9 = yOffset + j4 * yScale + this.yCoord;
/* 169 */             int k4 = (int)d9;
/*     */             
/* 171 */             if (d9 < k4)
/*     */             {
/* 173 */               k4--;
/*     */             }
/*     */             
/* 176 */             int l4 = k4 & 0xFF;
/* 177 */             d9 -= k4;
/* 178 */             double d10 = d9 * d9 * d9 * (d9 * (d9 * 6.0D - 15.0D) + 10.0D);
/*     */             
/* 180 */             if (j4 == 0 || l4 != k) {
/*     */               
/* 182 */               k = l4;
/* 183 */               l = this.permutations[j3] + l4;
/* 184 */               i1 = this.permutations[l] + i4;
/* 185 */               j1 = this.permutations[l + 1] + i4;
/* 186 */               k1 = this.permutations[j3 + 1] + l4;
/* 187 */               l1 = this.permutations[k1] + i4;
/* 188 */               i2 = this.permutations[k1 + 1] + i4;
/* 189 */               d1 = lerp(d6, grad(this.permutations[i1], d5, d9, d7), grad(this.permutations[l1], d5 - 1.0D, d9, d7));
/* 190 */               d2 = lerp(d6, grad(this.permutations[j1], d5, d9 - 1.0D, d7), grad(this.permutations[i2], d5 - 1.0D, d9 - 1.0D, d7));
/* 191 */               d3 = lerp(d6, grad(this.permutations[i1 + 1], d5, d9, d7 - 1.0D), grad(this.permutations[l1 + 1], d5 - 1.0D, d9, d7 - 1.0D));
/* 192 */               d4 = lerp(d6, grad(this.permutations[j1 + 1], d5, d9 - 1.0D, d7 - 1.0D), grad(this.permutations[i2 + 1], d5 - 1.0D, d9 - 1.0D, d7 - 1.0D));
/*     */             } 
/*     */             
/* 195 */             double d11 = lerp(d10, d1, d2);
/* 196 */             double d12 = lerp(d10, d3, d4);
/* 197 */             double d13 = lerp(d8, d11, d12);
/* 198 */             int j7 = i++;
/* 199 */             noiseArray[j7] = noiseArray[j7] + d13 * d0;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\NoiseGeneratorImproved.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */