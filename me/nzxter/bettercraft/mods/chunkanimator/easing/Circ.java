/*    */ package me.nzxter.bettercraft.mods.chunkanimator.easing;
/*    */ 
/*    */ public class Circ
/*    */ {
/*    */   public static float easeIn(float t, float b, float c, float d) {
/*  6 */     return -c * ((float)Math.sqrt((1.0F - (t /= d) * t)) - 1.0F) + b;
/*    */   }
/*    */   
/*    */   public static float easeOut(float t, float b, float c, float d) {
/* 10 */     return c * (float)Math.sqrt((1.0F - (t = t / d - 1.0F) * t)) + b;
/*    */   }
/*    */   
/*    */   public static float easeInOut(float t, float b, float c, float d) {
/* 14 */     if ((t /= d / 2.0F) < 1.0F) return -c / 2.0F * ((float)Math.sqrt((1.0F - t * t)) - 1.0F) + b; 
/* 15 */     return c / 2.0F * ((float)Math.sqrt((1.0F - (t -= 2.0F) * t)) + 1.0F) + b;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\chunkanimator\easing\Circ.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */