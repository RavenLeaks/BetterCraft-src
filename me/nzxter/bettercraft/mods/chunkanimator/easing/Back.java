/*    */ package me.nzxter.bettercraft.mods.chunkanimator.easing;
/*    */ 
/*    */ public class Back
/*    */ {
/*    */   public static float easeIn(float t, float b, float c, float d) {
/*  6 */     float s = 1.70158F;
/*  7 */     return c * (t /= d) * t * ((s + 1.0F) * t - s) + b;
/*    */   }
/*    */   
/*    */   public static float easeIn(float t, float b, float c, float d, float s) {
/* 11 */     return c * (t /= d) * t * ((s + 1.0F) * t - s) + b;
/*    */   }
/*    */   
/*    */   public static float easeOut(float t, float b, float c, float d) {
/* 15 */     float s = 1.70158F;
/* 16 */     return c * ((t = t / d - 1.0F) * t * ((s + 1.0F) * t + s) + 1.0F) + b;
/*    */   }
/*    */   
/*    */   public static float easeOut(float t, float b, float c, float d, float s) {
/* 20 */     return c * ((t = t / d - 1.0F) * t * ((s + 1.0F) * t + s) + 1.0F) + b;
/*    */   }
/*    */   
/*    */   public static float easeInOut(float t, float b, float c, float d) {
/* 24 */     float s = 1.70158F;
/* 25 */     if ((t /= d / 2.0F) < 1.0F) return c / 2.0F * t * t * (((s *= 1.525F) + 1.0F) * t - s) + b; 
/* 26 */     return c / 2.0F * ((t -= 2.0F) * t * (((s *= 1.525F) + 1.0F) * t + s) + 2.0F) + b;
/*    */   }
/*    */   
/*    */   public static float easeInOut(float t, float b, float c, float d, float s) {
/* 30 */     if ((t /= d / 2.0F) < 1.0F) return c / 2.0F * t * t * (((s *= 1.525F) + 1.0F) * t - s) + b; 
/* 31 */     return c / 2.0F * ((t -= 2.0F) * t * (((s *= 1.525F) + 1.0F) * t + s) + 2.0F) + b;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\chunkanimator\easing\Back.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */