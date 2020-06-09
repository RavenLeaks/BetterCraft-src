/*    */ package optifine;
/*    */ 
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ public class MathUtils
/*    */ {
/*    */   public static int getAverage(int[] p_getAverage_0_) {
/*  9 */     if (p_getAverage_0_.length <= 0)
/*    */     {
/* 11 */       return 0;
/*    */     }
/*    */ 
/*    */     
/* 15 */     int i = getSum(p_getAverage_0_);
/* 16 */     int j = i / p_getAverage_0_.length;
/* 17 */     return j;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int getSum(int[] p_getSum_0_) {
/* 23 */     if (p_getSum_0_.length <= 0)
/*    */     {
/* 25 */       return 0;
/*    */     }
/*    */ 
/*    */     
/* 29 */     int i = 0;
/*    */     
/* 31 */     for (int j = 0; j < p_getSum_0_.length; j++) {
/*    */       
/* 33 */       int k = p_getSum_0_[j];
/* 34 */       i += k;
/*    */     } 
/*    */     
/* 37 */     return i;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static int roundDownToPowerOfTwo(int p_roundDownToPowerOfTwo_0_) {
/* 43 */     int i = MathHelper.smallestEncompassingPowerOfTwo(p_roundDownToPowerOfTwo_0_);
/* 44 */     return (p_roundDownToPowerOfTwo_0_ == i) ? i : (i / 2);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean equalsDelta(float p_equalsDelta_0_, float p_equalsDelta_1_, float p_equalsDelta_2_) {
/* 49 */     return (Math.abs(p_equalsDelta_0_ - p_equalsDelta_1_) <= p_equalsDelta_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public static float toDeg(float p_toDeg_0_) {
/* 54 */     return p_toDeg_0_ * 180.0F / 3.1415927F;
/*    */   }
/*    */ 
/*    */   
/*    */   public static float toRad(float p_toRad_0_) {
/* 59 */     return p_toRad_0_ / 180.0F * 3.1415927F;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\MathUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */