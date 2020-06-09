/*     */ package net.minecraft.util.math;
/*     */ 
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public class MathHelper
/*     */ {
/*   8 */   public static final float SQRT_2 = sqrt(2.0F);
/*     */   private static final int SIN_BITS = 12;
/*     */   private static final int SIN_MASK = 4095;
/*     */   private static final int SIN_COUNT = 4096;
/*     */   public static final float PI = 3.1415927F;
/*     */   public static final float PI2 = 6.2831855F;
/*     */   public static final float PId2 = 1.5707964F;
/*     */   private static final float radFull = 6.2831855F;
/*     */   private static final float degFull = 360.0F;
/*     */   private static final float radToIndex = 651.8986F;
/*     */   private static final float degToIndex = 11.377778F;
/*     */   public static final float deg2Rad = 0.017453292F;
/*  20 */   private static final float[] SIN_TABLE_FAST = new float[4096];
/*     */ 
/*     */   
/*     */   public static boolean fastMath = false;
/*     */ 
/*     */   
/*  26 */   private static final float[] SIN_TABLE = new float[65536];
/*  27 */   private static final Random RANDOM = new Random();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float sin(float value) {
/*  46 */     return fastMath ? SIN_TABLE_FAST[(int)(value * 651.8986F) & 0xFFF] : SIN_TABLE[(int)(value * 10430.378F) & 0xFFFF];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float cos(float value) {
/*  54 */     return fastMath ? SIN_TABLE_FAST[(int)((value + 1.5707964F) * 651.8986F) & 0xFFF] : SIN_TABLE[(int)(value * 10430.378F + 16384.0F) & 0xFFFF];
/*     */   }
/*     */ 
/*     */   
/*     */   public static float sqrt(float value) {
/*  59 */     return (float)Math.sqrt(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float sqrt(double value) {
/*  64 */     return (float)Math.sqrt(value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int floor(float value) {
/*  72 */     int i = (int)value;
/*  73 */     return (value < i) ? (i - 1) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int fastFloor(double value) {
/*  81 */     return (int)(value + 1024.0D) - 1024;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int floor(double value) {
/*  89 */     int i = (int)value;
/*  90 */     return (value < i) ? (i - 1) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static long lFloor(double value) {
/*  98 */     long i = (long)value;
/*  99 */     return (value < i) ? (i - 1L) : i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int absFloor(double value) {
/* 104 */     return (int)((value >= 0.0D) ? value : (-value + 1.0D));
/*     */   }
/*     */ 
/*     */   
/*     */   public static float abs(float value) {
/* 109 */     return (value >= 0.0F) ? value : -value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int abs(int value) {
/* 117 */     return (value >= 0) ? value : -value;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int ceil(float value) {
/* 122 */     int i = (int)value;
/* 123 */     return (value > i) ? (i + 1) : i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int ceil(double value) {
/* 128 */     int i = (int)value;
/* 129 */     return (value > i) ? (i + 1) : i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int clamp(int num, int min, int max) {
/* 138 */     if (num < min)
/*     */     {
/* 140 */       return min;
/*     */     }
/*     */ 
/*     */     
/* 144 */     return (num > max) ? max : num;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float clamp(float num, float min, float max) {
/* 154 */     if (num < min)
/*     */     {
/* 156 */       return min;
/*     */     }
/*     */ 
/*     */     
/* 160 */     return (num > max) ? max : num;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double clamp(double num, double min, double max) {
/* 166 */     if (num < min)
/*     */     {
/* 168 */       return min;
/*     */     }
/*     */ 
/*     */     
/* 172 */     return (num > max) ? max : num;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double clampedLerp(double lowerBnd, double upperBnd, double slide) {
/* 178 */     if (slide < 0.0D)
/*     */     {
/* 180 */       return lowerBnd;
/*     */     }
/*     */ 
/*     */     
/* 184 */     return (slide > 1.0D) ? upperBnd : (lowerBnd + (upperBnd - lowerBnd) * slide);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double absMax(double p_76132_0_, double p_76132_2_) {
/* 193 */     if (p_76132_0_ < 0.0D)
/*     */     {
/* 195 */       p_76132_0_ = -p_76132_0_;
/*     */     }
/*     */     
/* 198 */     if (p_76132_2_ < 0.0D)
/*     */     {
/* 200 */       p_76132_2_ = -p_76132_2_;
/*     */     }
/*     */     
/* 203 */     return (p_76132_0_ > p_76132_2_) ? p_76132_0_ : p_76132_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int intFloorDiv(int p_76137_0_, int p_76137_1_) {
/* 211 */     return (p_76137_0_ < 0) ? (-((-p_76137_0_ - 1) / p_76137_1_) - 1) : (p_76137_0_ / p_76137_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getInt(Random random, int minimum, int maximum) {
/* 216 */     return (minimum >= maximum) ? minimum : (random.nextInt(maximum - minimum + 1) + minimum);
/*     */   }
/*     */ 
/*     */   
/*     */   public static float nextFloat(Random random, float minimum, float maximum) {
/* 221 */     return (minimum >= maximum) ? minimum : (random.nextFloat() * (maximum - minimum) + minimum);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double nextDouble(Random random, double minimum, double maximum) {
/* 226 */     return (minimum >= maximum) ? minimum : (random.nextDouble() * (maximum - minimum) + minimum);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double average(long[] values) {
/* 231 */     long i = 0L; byte b; int j;
/*     */     long[] arrayOfLong;
/* 233 */     for (j = (arrayOfLong = values).length, b = 0; b < j; ) { long l = arrayOfLong[b];
/*     */       
/* 235 */       i += l;
/*     */       b++; }
/*     */     
/* 238 */     return i / values.length;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean epsilonEquals(float p_180185_0_, float p_180185_1_) {
/* 243 */     return (abs(p_180185_1_ - p_180185_0_) < 1.0E-5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static int normalizeAngle(int p_180184_0_, int p_180184_1_) {
/* 248 */     return (p_180184_0_ % p_180184_1_ + p_180184_1_) % p_180184_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static float positiveModulo(float numerator, float denominator) {
/* 253 */     return (numerator % denominator + denominator) % denominator;
/*     */   }
/*     */ 
/*     */   
/*     */   public static double func_191273_b(double p_191273_0_, double p_191273_2_) {
/* 258 */     return (p_191273_0_ % p_191273_2_ + p_191273_2_) % p_191273_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float wrapDegrees(float value) {
/* 266 */     value %= 360.0F;
/*     */     
/* 268 */     if (value >= 180.0F)
/*     */     {
/* 270 */       value -= 360.0F;
/*     */     }
/*     */     
/* 273 */     if (value < -180.0F)
/*     */     {
/* 275 */       value += 360.0F;
/*     */     }
/*     */     
/* 278 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double wrapDegrees(double value) {
/* 286 */     value %= 360.0D;
/*     */     
/* 288 */     if (value >= 180.0D)
/*     */     {
/* 290 */       value -= 360.0D;
/*     */     }
/*     */     
/* 293 */     if (value < -180.0D)
/*     */     {
/* 295 */       value += 360.0D;
/*     */     }
/*     */     
/* 298 */     return value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int clampAngle(int angle) {
/* 306 */     angle %= 360;
/*     */     
/* 308 */     if (angle >= 180)
/*     */     {
/* 310 */       angle -= 360;
/*     */     }
/*     */     
/* 313 */     if (angle < -180)
/*     */     {
/* 315 */       angle += 360;
/*     */     }
/*     */     
/* 318 */     return angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getInt(String value, int defaultValue) {
/*     */     try {
/* 328 */       return Integer.parseInt(value);
/*     */     }
/* 330 */     catch (Throwable var3) {
/*     */       
/* 332 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getInt(String value, int defaultValue, int max) {
/* 341 */     return Math.max(max, getInt(value, defaultValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double getDouble(String value, double defaultValue) {
/*     */     try {
/* 351 */       return Double.parseDouble(value);
/*     */     }
/* 353 */     catch (Throwable var4) {
/*     */       
/* 355 */       return defaultValue;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static double getDouble(String value, double defaultValue, double max) {
/* 361 */     return Math.max(max, getDouble(value, defaultValue));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int smallestEncompassingPowerOfTwo(int value) {
/* 369 */     int i = value - 1;
/* 370 */     i |= i >> 1;
/* 371 */     i |= i >> 2;
/* 372 */     i |= i >> 4;
/* 373 */     i |= i >> 8;
/* 374 */     i |= i >> 16;
/* 375 */     return i + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isPowerOfTwo(int value) {
/* 383 */     return (value != 0 && (value & value - 1) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int log2DeBruijn(int value) {
/* 393 */     value = isPowerOfTwo(value) ? value : smallestEncompassingPowerOfTwo(value);
/* 394 */     return MULTIPLY_DE_BRUIJN_BIT_POSITION[(int)(value * 125613361L >> 27L) & 0x1F];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int log2(int value) {
/* 403 */     return log2DeBruijn(value) - (isPowerOfTwo(value) ? 0 : 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int roundUp(int number, int interval) {
/* 414 */     if (interval == 0)
/*     */     {
/* 416 */       return 0;
/*     */     }
/* 418 */     if (number == 0)
/*     */     {
/* 420 */       return interval;
/*     */     }
/*     */ 
/*     */     
/* 424 */     if (number < 0)
/*     */     {
/* 426 */       interval *= -1;
/*     */     }
/*     */     
/* 429 */     int i = number % interval;
/* 430 */     return (i == 0) ? number : (number + interval - i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int rgb(float rIn, float gIn, float bIn) {
/* 439 */     return rgb(floor(rIn * 255.0F), floor(gIn * 255.0F), floor(bIn * 255.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int rgb(int rIn, int gIn, int bIn) {
/* 447 */     int i = (rIn << 8) + gIn;
/* 448 */     i = (i << 8) + bIn;
/* 449 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int multiplyColor(int p_180188_0_, int p_180188_1_) {
/* 454 */     int i = (p_180188_0_ & 0xFF0000) >> 16;
/* 455 */     int j = (p_180188_1_ & 0xFF0000) >> 16;
/* 456 */     int k = (p_180188_0_ & 0xFF00) >> 8;
/* 457 */     int l = (p_180188_1_ & 0xFF00) >> 8;
/* 458 */     int i1 = (p_180188_0_ & 0xFF) >> 0;
/* 459 */     int j1 = (p_180188_1_ & 0xFF) >> 0;
/* 460 */     int k1 = (int)(i * j / 255.0F);
/* 461 */     int l1 = (int)(k * l / 255.0F);
/* 462 */     int i2 = (int)(i1 * j1 / 255.0F);
/* 463 */     return p_180188_0_ & 0xFF000000 | k1 << 16 | l1 << 8 | i2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static double frac(double number) {
/* 471 */     return number - Math.floor(number);
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getPositionRandom(Vec3i pos) {
/* 476 */     return getCoordinateRandom(pos.getX(), pos.getY(), pos.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public static long getCoordinateRandom(int x, int y, int z) {
/* 481 */     long i = (x * 3129871) ^ z * 116129781L ^ y;
/* 482 */     i = i * i * 42317861L + i * 11L;
/* 483 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public static UUID getRandomUUID(Random rand) {
/* 488 */     long i = rand.nextLong() & 0xFFFFFFFFFFFF0FFFL | 0x4000L;
/* 489 */     long j = rand.nextLong() & 0x3FFFFFFFFFFFFFFFL | Long.MIN_VALUE;
/* 490 */     return new UUID(i, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static UUID getRandomUUID() {
/* 498 */     return getRandomUUID(RANDOM);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double pct(double p_181160_0_, double p_181160_2_, double p_181160_4_) {
/* 503 */     return (p_181160_0_ - p_181160_2_) / (p_181160_4_ - p_181160_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public static double atan2(double p_181159_0_, double p_181159_2_) {
/* 508 */     double d0 = p_181159_2_ * p_181159_2_ + p_181159_0_ * p_181159_0_;
/*     */     
/* 510 */     if (Double.isNaN(d0))
/*     */     {
/* 512 */       return Double.NaN;
/*     */     }
/*     */ 
/*     */     
/* 516 */     boolean flag = (p_181159_0_ < 0.0D);
/*     */     
/* 518 */     if (flag)
/*     */     {
/* 520 */       p_181159_0_ = -p_181159_0_;
/*     */     }
/*     */     
/* 523 */     boolean flag1 = (p_181159_2_ < 0.0D);
/*     */     
/* 525 */     if (flag1)
/*     */     {
/* 527 */       p_181159_2_ = -p_181159_2_;
/*     */     }
/*     */     
/* 530 */     boolean flag2 = (p_181159_0_ > p_181159_2_);
/*     */     
/* 532 */     if (flag2) {
/*     */       
/* 534 */       double d1 = p_181159_2_;
/* 535 */       p_181159_2_ = p_181159_0_;
/* 536 */       p_181159_0_ = d1;
/*     */     } 
/*     */     
/* 539 */     double d9 = fastInvSqrt(d0);
/* 540 */     p_181159_2_ *= d9;
/* 541 */     p_181159_0_ *= d9;
/* 542 */     double d2 = FRAC_BIAS + p_181159_0_;
/* 543 */     int i = (int)Double.doubleToRawLongBits(d2);
/* 544 */     double d3 = ASINE_TAB[i];
/* 545 */     double d4 = COS_TAB[i];
/* 546 */     double d5 = d2 - FRAC_BIAS;
/* 547 */     double d6 = p_181159_0_ * d4 - p_181159_2_ * d5;
/* 548 */     double d7 = (6.0D + d6 * d6) * d6 * 0.16666666666666666D;
/* 549 */     double d8 = d3 + d7;
/*     */     
/* 551 */     if (flag2)
/*     */     {
/* 553 */       d8 = 1.5707963267948966D - d8;
/*     */     }
/*     */     
/* 556 */     if (flag1)
/*     */     {
/* 558 */       d8 = Math.PI - d8;
/*     */     }
/*     */     
/* 561 */     if (flag)
/*     */     {
/* 563 */       d8 = -d8;
/*     */     }
/*     */     
/* 566 */     return d8;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static double fastInvSqrt(double p_181161_0_) {
/* 572 */     double d0 = 0.5D * p_181161_0_;
/* 573 */     long i = Double.doubleToRawLongBits(p_181161_0_);
/* 574 */     i = 6910469410427058090L - (i >> 1L);
/* 575 */     p_181161_0_ = Double.longBitsToDouble(i);
/* 576 */     p_181161_0_ *= 1.5D - d0 * p_181161_0_ * p_181161_0_;
/* 577 */     return p_181161_0_;
/*     */   }
/*     */   
/*     */   public static int hsvToRGB(float hue, float saturation, float value) {
/*     */     float f4, f5, f6;
/* 582 */     int j, k, l, i = (int)(hue * 6.0F) % 6;
/* 583 */     float f = hue * 6.0F - i;
/* 584 */     float f1 = value * (1.0F - saturation);
/* 585 */     float f2 = value * (1.0F - f * saturation);
/* 586 */     float f3 = value * (1.0F - (1.0F - f) * saturation);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 591 */     switch (i) {
/*     */       
/*     */       case 0:
/* 594 */         f4 = value;
/* 595 */         f5 = f3;
/* 596 */         f6 = f1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 633 */         j = clamp((int)(f4 * 255.0F), 0, 255);
/* 634 */         k = clamp((int)(f5 * 255.0F), 0, 255);
/* 635 */         l = clamp((int)(f6 * 255.0F), 0, 255);
/* 636 */         return j << 16 | k << 8 | l;case 1: f4 = f2; f5 = value; f6 = f1; j = clamp((int)(f4 * 255.0F), 0, 255); k = clamp((int)(f5 * 255.0F), 0, 255); l = clamp((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 2: f4 = f1; f5 = value; f6 = f3; j = clamp((int)(f4 * 255.0F), 0, 255); k = clamp((int)(f5 * 255.0F), 0, 255); l = clamp((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 3: f4 = f1; f5 = f2; f6 = value; j = clamp((int)(f4 * 255.0F), 0, 255); k = clamp((int)(f5 * 255.0F), 0, 255); l = clamp((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 4: f4 = f3; f5 = f1; f6 = value; j = clamp((int)(f4 * 255.0F), 0, 255); k = clamp((int)(f5 * 255.0F), 0, 255); l = clamp((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;case 5: f4 = value; f5 = f1; f6 = f2; j = clamp((int)(f4 * 255.0F), 0, 255); k = clamp((int)(f5 * 255.0F), 0, 255); l = clamp((int)(f6 * 255.0F), 0, 255); return j << 16 | k << 8 | l;
/*     */     } 
/*     */     throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
/*     */   }
/*     */   public static int hash(int p_188208_0_) {
/* 641 */     p_188208_0_ ^= p_188208_0_ >>> 16;
/* 642 */     p_188208_0_ *= -2048144789;
/* 643 */     p_188208_0_ ^= p_188208_0_ >>> 13;
/* 644 */     p_188208_0_ *= -1028477387;
/* 645 */     p_188208_0_ ^= p_188208_0_ >>> 16;
/* 646 */     return p_188208_0_;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 651 */     for (int i = 0; i < 65536; i++)
/*     */     {
/* 653 */       SIN_TABLE[i] = (float)Math.sin(i * Math.PI * 2.0D / 65536.0D);
/*     */     }
/*     */     
/* 656 */     for (int j = 0; j < 4096; j++)
/*     */     {
/* 658 */       SIN_TABLE_FAST[j] = (float)Math.sin(((j + 0.5F) / 4096.0F * 6.2831855F));
/*     */     }
/*     */     
/* 661 */     for (int k = 0; k < 360; k += 90)
/*     */     {
/* 663 */       SIN_TABLE_FAST[(int)(k * 11.377778F) & 0xFFF] = (float)Math.sin((k * 0.017453292F)); } 
/*     */   }
/*     */   
/* 666 */   private static final int[] MULTIPLY_DE_BRUIJN_BIT_POSITION = new int[] { 0, 1, 28, 2, 29, 14, 24, 3, 30, 22, 20, 15, 25, 17, 4, 8, 31, 27, 13, 23, 21, 19, 16, 7, 26, 12, 18, 6, 11, 5, 10, 9 };
/* 667 */   private static final double FRAC_BIAS = Double.longBitsToDouble(4805340802404319232L);
/* 668 */   private static final double[] ASINE_TAB = new double[257];
/* 669 */   private static final double[] COS_TAB = new double[257];
/*     */   static {
/* 671 */     for (int l = 0; l < 257; l++) {
/*     */       
/* 673 */       double d0 = l / 256.0D;
/* 674 */       double d1 = Math.asin(d0);
/* 675 */       COS_TAB[l] = Math.cos(d1);
/* 676 */       ASINE_TAB[l] = d1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\math\MathHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */