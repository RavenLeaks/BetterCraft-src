/*    */ package javax.vecmath;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class VecMathUtil
/*    */ {
/*    */   static final long hashLongBits(long hash, long l) {
/* 40 */     hash *= 31L;
/* 41 */     return hash + l;
/*    */   }
/*    */   
/*    */   static final long hashFloatBits(long hash, float f) {
/* 45 */     hash *= 31L;
/*    */     
/* 47 */     if (f == 0.0F) {
/* 48 */       return hash;
/*    */     }
/* 50 */     return hash + Float.floatToIntBits(f);
/*    */   }
/*    */   
/*    */   static final long hashDoubleBits(long hash, double d) {
/* 54 */     hash *= 31L;
/*    */     
/* 56 */     if (d == 0.0D) {
/* 57 */       return hash;
/*    */     }
/* 59 */     return hash + Double.doubleToLongBits(d);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static final int hashFinish(long hash) {
/* 66 */     return (int)(hash ^ hash >> 32L);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\VecMathUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */