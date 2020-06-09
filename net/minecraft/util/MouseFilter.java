/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MouseFilter
/*    */ {
/*    */   private float targetValue;
/*    */   private float remainingValue;
/*    */   private float lastAmount;
/*    */   
/*    */   public float smooth(float p_76333_1_, float p_76333_2_) {
/* 14 */     this.targetValue += p_76333_1_;
/* 15 */     p_76333_1_ = (this.targetValue - this.remainingValue) * p_76333_2_;
/* 16 */     this.lastAmount += (p_76333_1_ - this.lastAmount) * 0.5F;
/*    */     
/* 18 */     if ((p_76333_1_ > 0.0F && p_76333_1_ > this.lastAmount) || (p_76333_1_ < 0.0F && p_76333_1_ < this.lastAmount))
/*    */     {
/* 20 */       p_76333_1_ = this.lastAmount;
/*    */     }
/*    */     
/* 23 */     this.remainingValue += p_76333_1_;
/* 24 */     return p_76333_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public void reset() {
/* 29 */     this.targetValue = 0.0F;
/* 30 */     this.remainingValue = 0.0F;
/* 31 */     this.lastAmount = 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\MouseFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */