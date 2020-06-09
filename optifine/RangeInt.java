/*    */ package optifine;
/*    */ 
/*    */ 
/*    */ public class RangeInt
/*    */ {
/*    */   private int min;
/*    */   private int max;
/*    */   
/*    */   public RangeInt(int p_i80_1_, int p_i80_2_) {
/* 10 */     this.min = Math.min(p_i80_1_, p_i80_2_);
/* 11 */     this.max = Math.max(p_i80_1_, p_i80_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInRange(int p_isInRange_1_) {
/* 16 */     if (p_isInRange_1_ < this.min)
/*    */     {
/* 18 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 22 */     return (p_isInRange_1_ <= this.max);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMin() {
/* 28 */     return this.min;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getMax() {
/* 33 */     return this.max;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 38 */     return "min: " + this.min + ", max: " + this.max;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\RangeInt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */