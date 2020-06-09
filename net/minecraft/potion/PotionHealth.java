/*    */ package net.minecraft.potion;
/*    */ 
/*    */ public class PotionHealth
/*    */   extends Potion
/*    */ {
/*    */   public PotionHealth(boolean isBadEffectIn, int liquidColorIn) {
/*  7 */     super(isBadEffectIn, liquidColorIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isInstant() {
/* 15 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isReady(int duration, int amplifier) {
/* 23 */     return (duration >= 1);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\potion\PotionHealth.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */