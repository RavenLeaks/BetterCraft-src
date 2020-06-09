/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.Random;
/*     */ 
/*     */ public class NoiseGeneratorSimplex
/*     */ {
/*   7 */   private static final int[][] grad3 = new int[][] { { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 }, { 1, 1 }, { -1, 1 }, { 1, -1 }, { -1, -1 }, { 0, 1, 1 }, { 0, -1, 1 }, { 0, 1, -1 }, { 0, -1, -1 } };
/*   8 */   public static final double SQRT_3 = Math.sqrt(3.0D);
/*     */   private final int[] p;
/*     */   public double xo;
/*     */   public double yo;
/*     */   public double zo;
/*  13 */   private static final double F2 = 0.5D * (SQRT_3 - 1.0D);
/*  14 */   private static final double G2 = (3.0D - SQRT_3) / 6.0D;
/*     */ 
/*     */   
/*     */   public NoiseGeneratorSimplex() {
/*  18 */     this(new Random());
/*     */   }
/*     */ 
/*     */   
/*     */   public NoiseGeneratorSimplex(Random p_i45471_1_) {
/*  23 */     this.p = new int[512];
/*  24 */     this.xo = p_i45471_1_.nextDouble() * 256.0D;
/*  25 */     this.yo = p_i45471_1_.nextDouble() * 256.0D;
/*  26 */     this.zo = p_i45471_1_.nextDouble() * 256.0D;
/*     */     
/*  28 */     for (int i = 0; i < 256; this.p[i] = i++);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  33 */     for (int l = 0; l < 256; l++) {
/*     */       
/*  35 */       int j = p_i45471_1_.nextInt(256 - l) + l;
/*  36 */       int k = this.p[l];
/*  37 */       this.p[l] = this.p[j];
/*  38 */       this.p[j] = k;
/*  39 */       this.p[l + 256] = this.p[l];
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static int fastFloor(double value) {
/*  45 */     return (value > 0.0D) ? (int)value : ((int)value - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   private static double dot(int[] p_151604_0_, double p_151604_1_, double p_151604_3_) {
/*  50 */     return p_151604_0_[0] * p_151604_1_ + p_151604_0_[1] * p_151604_3_;
/*     */   }
/*     */   
/*     */   public double getValue(double p_151605_1_, double p_151605_3_) {
/*     */     int k, l;
/*  55 */     double d0, d1, d2, d3 = 0.5D * (SQRT_3 - 1.0D);
/*  56 */     double d4 = (p_151605_1_ + p_151605_3_) * d3;
/*  57 */     int i = fastFloor(p_151605_1_ + d4);
/*  58 */     int j = fastFloor(p_151605_3_ + d4);
/*  59 */     double d5 = (3.0D - SQRT_3) / 6.0D;
/*  60 */     double d6 = (i + j) * d5;
/*  61 */     double d7 = i - d6;
/*  62 */     double d8 = j - d6;
/*  63 */     double d9 = p_151605_1_ - d7;
/*  64 */     double d10 = p_151605_3_ - d8;
/*     */ 
/*     */ 
/*     */     
/*  68 */     if (d9 > d10) {
/*     */       
/*  70 */       k = 1;
/*  71 */       l = 0;
/*     */     }
/*     */     else {
/*     */       
/*  75 */       k = 0;
/*  76 */       l = 1;
/*     */     } 
/*     */     
/*  79 */     double d11 = d9 - k + d5;
/*  80 */     double d12 = d10 - l + d5;
/*  81 */     double d13 = d9 - 1.0D + 2.0D * d5;
/*  82 */     double d14 = d10 - 1.0D + 2.0D * d5;
/*  83 */     int i1 = i & 0xFF;
/*  84 */     int j1 = j & 0xFF;
/*  85 */     int k1 = this.p[i1 + this.p[j1]] % 12;
/*  86 */     int l1 = this.p[i1 + k + this.p[j1 + l]] % 12;
/*  87 */     int i2 = this.p[i1 + 1 + this.p[j1 + 1]] % 12;
/*  88 */     double d15 = 0.5D - d9 * d9 - d10 * d10;
/*     */ 
/*     */     
/*  91 */     if (d15 < 0.0D) {
/*     */       
/*  93 */       d0 = 0.0D;
/*     */     }
/*     */     else {
/*     */       
/*  97 */       d15 *= d15;
/*  98 */       d0 = d15 * d15 * dot(grad3[k1], d9, d10);
/*     */     } 
/*     */     
/* 101 */     double d16 = 0.5D - d11 * d11 - d12 * d12;
/*     */ 
/*     */     
/* 104 */     if (d16 < 0.0D) {
/*     */       
/* 106 */       d1 = 0.0D;
/*     */     }
/*     */     else {
/*     */       
/* 110 */       d16 *= d16;
/* 111 */       d1 = d16 * d16 * dot(grad3[l1], d11, d12);
/*     */     } 
/*     */     
/* 114 */     double d17 = 0.5D - d13 * d13 - d14 * d14;
/*     */ 
/*     */     
/* 117 */     if (d17 < 0.0D) {
/*     */       
/* 119 */       d2 = 0.0D;
/*     */     }
/*     */     else {
/*     */       
/* 123 */       d17 *= d17;
/* 124 */       d2 = d17 * d17 * dot(grad3[i2], d13, d14);
/*     */     } 
/*     */     
/* 127 */     return 70.0D * (d0 + d1 + d2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void add(double[] p_151606_1_, double p_151606_2_, double p_151606_4_, int p_151606_6_, int p_151606_7_, double p_151606_8_, double p_151606_10_, double p_151606_12_) {
/* 132 */     int i = 0;
/*     */     
/* 134 */     for (int j = 0; j < p_151606_7_; j++) {
/*     */       
/* 136 */       double d0 = (p_151606_4_ + j) * p_151606_10_ + this.yo;
/*     */       
/* 138 */       for (int k = 0; k < p_151606_6_; k++) {
/*     */         int j1, k1;
/* 140 */         double d2, d3, d4, d1 = (p_151606_2_ + k) * p_151606_8_ + this.xo;
/* 141 */         double d5 = (d1 + d0) * F2;
/* 142 */         int l = fastFloor(d1 + d5);
/* 143 */         int i1 = fastFloor(d0 + d5);
/* 144 */         double d6 = (l + i1) * G2;
/* 145 */         double d7 = l - d6;
/* 146 */         double d8 = i1 - d6;
/* 147 */         double d9 = d1 - d7;
/* 148 */         double d10 = d0 - d8;
/*     */ 
/*     */ 
/*     */         
/* 152 */         if (d9 > d10) {
/*     */           
/* 154 */           j1 = 1;
/* 155 */           k1 = 0;
/*     */         }
/*     */         else {
/*     */           
/* 159 */           j1 = 0;
/* 160 */           k1 = 1;
/*     */         } 
/*     */         
/* 163 */         double d11 = d9 - j1 + G2;
/* 164 */         double d12 = d10 - k1 + G2;
/* 165 */         double d13 = d9 - 1.0D + 2.0D * G2;
/* 166 */         double d14 = d10 - 1.0D + 2.0D * G2;
/* 167 */         int l1 = l & 0xFF;
/* 168 */         int i2 = i1 & 0xFF;
/* 169 */         int j2 = this.p[l1 + this.p[i2]] % 12;
/* 170 */         int k2 = this.p[l1 + j1 + this.p[i2 + k1]] % 12;
/* 171 */         int l2 = this.p[l1 + 1 + this.p[i2 + 1]] % 12;
/* 172 */         double d15 = 0.5D - d9 * d9 - d10 * d10;
/*     */ 
/*     */         
/* 175 */         if (d15 < 0.0D) {
/*     */           
/* 177 */           d2 = 0.0D;
/*     */         }
/*     */         else {
/*     */           
/* 181 */           d15 *= d15;
/* 182 */           d2 = d15 * d15 * dot(grad3[j2], d9, d10);
/*     */         } 
/*     */         
/* 185 */         double d16 = 0.5D - d11 * d11 - d12 * d12;
/*     */ 
/*     */         
/* 188 */         if (d16 < 0.0D) {
/*     */           
/* 190 */           d3 = 0.0D;
/*     */         }
/*     */         else {
/*     */           
/* 194 */           d16 *= d16;
/* 195 */           d3 = d16 * d16 * dot(grad3[k2], d11, d12);
/*     */         } 
/*     */         
/* 198 */         double d17 = 0.5D - d13 * d13 - d14 * d14;
/*     */ 
/*     */         
/* 201 */         if (d17 < 0.0D) {
/*     */           
/* 203 */           d4 = 0.0D;
/*     */         }
/*     */         else {
/*     */           
/* 207 */           d17 *= d17;
/* 208 */           d4 = d17 * d17 * dot(grad3[l2], d13, d14);
/*     */         } 
/*     */         
/* 211 */         int i3 = i++;
/* 212 */         p_151606_1_[i3] = p_151606_1_[i3] + 70.0D * (d2 + d3 + d4) * p_151606_12_;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\NoiseGeneratorSimplex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */