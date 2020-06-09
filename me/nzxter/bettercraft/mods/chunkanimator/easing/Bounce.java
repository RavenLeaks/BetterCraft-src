/*    */ package me.nzxter.bettercraft.mods.chunkanimator.easing;
/*    */ 
/*    */ public class Bounce
/*    */ {
/*    */   public static float easeIn(float t, float b, float c, float d) {
/*  6 */     return c - easeOut(d - t, 0.0F, c, d) + b;
/*    */   }
/*    */   
/*    */   public static float easeOut(float t, float b, float c, float d) {
/* 10 */     if ((t /= d) < 0.36363637F)
/* 11 */       return c * 7.5625F * t * t + b; 
/* 12 */     if (t < 0.72727275F)
/* 13 */       return c * (7.5625F * (t -= 0.54545456F) * t + 0.75F) + b; 
/* 14 */     if (t < 0.9090909090909091D) {
/* 15 */       return c * (7.5625F * (t -= 0.8181818F) * t + 0.9375F) + b;
/*    */     }
/* 17 */     return c * (7.5625F * (t -= 0.95454544F) * t + 0.984375F) + b;
/*    */   }
/*    */ 
/*    */   
/*    */   public static float easeInOut(float t, float b, float c, float d) {
/* 22 */     if (t < d / 2.0F) return easeIn(t * 2.0F, 0.0F, c, d) * 0.5F + b; 
/* 23 */     return easeOut(t * 2.0F - d, 0.0F, c, d) * 0.5F + c * 0.5F + b;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\chunkanimator\easing\Bounce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */