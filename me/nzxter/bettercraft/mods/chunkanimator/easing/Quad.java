/*    */ package me.nzxter.bettercraft.mods.chunkanimator.easing;
/*    */ 
/*    */ public class Quad
/*    */ {
/*    */   public static float easeIn(float t, float b, float c, float d) {
/*  6 */     return c * (t /= d) * t + b;
/*    */   }
/*    */   
/*    */   public static float easeOut(float t, float b, float c, float d) {
/* 10 */     return -c * (t /= d) * (t - 2.0F) + b;
/*    */   }
/*    */   
/*    */   public static float easeInOut(float t, float b, float c, float d) {
/* 14 */     if ((t /= d / 2.0F) < 1.0F) return c / 2.0F * t * t + b; 
/* 15 */     return -c / 2.0F * (--t * (t - 2.0F) - 1.0F) + b;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\chunkanimator\easing\Quad.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */