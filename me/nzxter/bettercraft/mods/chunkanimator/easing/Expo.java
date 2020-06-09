/*    */ package me.nzxter.bettercraft.mods.chunkanimator.easing;
/*    */ 
/*    */ public class Expo
/*    */ {
/*    */   public static float easeIn(float t, float b, float c, float d) {
/*  6 */     return (t == 0.0F) ? b : (c * (float)Math.pow(2.0D, (10.0F * (t / d - 1.0F))) + b);
/*    */   }
/*    */   
/*    */   public static float easeOut(float t, float b, float c, float d) {
/* 10 */     return (t == d) ? (b + c) : (c * (-((float)Math.pow(2.0D, (-10.0F * t / d))) + 1.0F) + b);
/*    */   }
/*    */   
/*    */   public static float easeInOut(float t, float b, float c, float d) {
/* 14 */     if (t == 0.0F) return b; 
/* 15 */     if (t == d) return b + c; 
/* 16 */     if ((t /= d / 2.0F) < 1.0F) return c / 2.0F * (float)Math.pow(2.0D, (10.0F * (t - 1.0F))) + b; 
/* 17 */     return c / 2.0F * (-((float)Math.pow(2.0D, (-10.0F * --t))) + 2.0F) + b;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\chunkanimator\easing\Expo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */