/*    */ package me.nzxter.bettercraft.mods.chunkanimator.easing;
/*    */ 
/*    */ public class Linear
/*    */ {
/*    */   public static float easeNone(float t, float b, float c, float d) {
/*  6 */     return c * t / d + b;
/*    */   }
/*    */   
/*    */   public static float easeIn(float t, float b, float c, float d) {
/* 10 */     return c * t / d + b;
/*    */   }
/*    */   
/*    */   public static float easeOut(float t, float b, float c, float d) {
/* 14 */     return c * t / d + b;
/*    */   }
/*    */   
/*    */   public static float easeInOut(float t, float b, float c, float d) {
/* 18 */     return c * t / d + b;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\chunkanimator\easing\Linear.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */