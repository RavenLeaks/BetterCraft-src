/*    */ package me.nzxter.bettercraft.mods.chunkanimator.easing;
/*    */ 
/*    */ public class Elastic
/*    */ {
/*    */   public static float easeIn(float t, float b, float c, float d) {
/*  6 */     if (t == 0.0F) return b;  if ((t /= d) == 1.0F) return b + c; 
/*  7 */     float p = d * 0.3F;
/*  8 */     float a = c;
/*  9 */     float s = p / 4.0F;
/* 10 */     return -(a * (float)Math.pow(2.0D, (10.0F * --t)) * (float)Math.sin(((t * d - s) * 6.2831855F / p))) + b;
/*    */   }
/*    */   
/*    */   public static float easeIn(float t, float b, float c, float d, float a, float p) {
/*    */     float s;
/* 15 */     if (t == 0.0F) return b;  if ((t /= d) == 1.0F) return b + c; 
/* 16 */     if (a < Math.abs(c)) { a = c; s = p / 4.0F; }
/* 17 */     else { s = p / 6.2831855F * (float)Math.asin((c / a)); }
/* 18 */      return -(a * (float)Math.pow(2.0D, (10.0F * --t)) * (float)Math.sin((t * d - s) * 6.283185307179586D / p)) + b;
/*    */   }
/*    */   
/*    */   public static float easeOut(float t, float b, float c, float d) {
/* 22 */     if (t == 0.0F) return b;  if ((t /= d) == 1.0F) return b + c; 
/* 23 */     float p = d * 0.3F;
/* 24 */     float a = c;
/* 25 */     float s = p / 4.0F;
/* 26 */     return a * (float)Math.pow(2.0D, (-10.0F * t)) * (float)Math.sin(((t * d - s) * 6.2831855F / p)) + c + b;
/*    */   }
/*    */   
/*    */   public static float easeOut(float t, float b, float c, float d, float a, float p) {
/*    */     float s;
/* 31 */     if (t == 0.0F) return b;  if ((t /= d) == 1.0F) return b + c; 
/* 32 */     if (a < Math.abs(c)) { a = c; s = p / 4.0F; }
/* 33 */     else { s = p / 6.2831855F * (float)Math.asin((c / a)); }
/* 34 */      return a * (float)Math.pow(2.0D, (-10.0F * t)) * (float)Math.sin(((t * d - s) * 6.2831855F / p)) + c + b;
/*    */   }
/*    */   
/*    */   public static float easeInOut(float t, float b, float c, float d) {
/* 38 */     if (t == 0.0F) return b;  if ((t /= d / 2.0F) == 2.0F) return b + c; 
/* 39 */     float p = d * 0.45000002F;
/* 40 */     float a = c;
/* 41 */     float s = p / 4.0F;
/* 42 */     if (t < 1.0F) return -0.5F * a * (float)Math.pow(2.0D, (10.0F * --t)) * (float)Math.sin(((t * d - s) * 6.2831855F / p)) + b; 
/* 43 */     return a * (float)Math.pow(2.0D, (-10.0F * --t)) * (float)Math.sin(((t * d - s) * 6.2831855F / p)) * 0.5F + c + b;
/*    */   }
/*    */   
/*    */   public static float easeInOut(float t, float b, float c, float d, float a, float p) {
/*    */     float s;
/* 48 */     if (t == 0.0F) return b;  if ((t /= d / 2.0F) == 2.0F) return b + c; 
/* 49 */     if (a < Math.abs(c)) { a = c; s = p / 4.0F; }
/* 50 */     else { s = p / 6.2831855F * (float)Math.asin((c / a)); }
/* 51 */      if (t < 1.0F) return -0.5F * a * (float)Math.pow(2.0D, (10.0F * --t)) * (float)Math.sin(((t * d - s) * 6.2831855F / p)) + b; 
/* 52 */     return a * (float)Math.pow(2.0D, (-10.0F * --t)) * (float)Math.sin(((t * d - s) * 6.2831855F / p)) * 0.5F + c + b;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\chunkanimator\easing\Elastic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */