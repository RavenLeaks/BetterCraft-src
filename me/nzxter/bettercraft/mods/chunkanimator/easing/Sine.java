/*    */ package me.nzxter.bettercraft.mods.chunkanimator.easing;
/*    */ 
/*    */ public class Sine
/*    */ {
/*    */   public static float easeIn(float t, float b, float c, float d) {
/*  6 */     return -c * (float)Math.cos((t / d) * 1.5707963267948966D) + c + b;
/*    */   }
/*    */   
/*    */   public static float easeOut(float t, float b, float c, float d) {
/* 10 */     return c * (float)Math.sin((t / d) * 1.5707963267948966D) + b;
/*    */   }
/*    */   
/*    */   public static float easeInOut(float t, float b, float c, float d) {
/* 14 */     return -c / 2.0F * ((float)Math.cos(Math.PI * t / d) - 1.0F) + b;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\chunkanimator\easing\Sine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */