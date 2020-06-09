/*    */ package com.TominoCZ.FBP.util;
/*    */ 
/*    */ 
/*    */ public class FBPMathUtil
/*    */ {
/*    */   public static double add(double d, double add) {
/*  7 */     if (d < 0.0D) {
/*  8 */       return d - add;
/*    */     }
/* 10 */     return d + add;
/*    */   }
/*    */ 
/*    */   
/*    */   public static double round(double d, int decimals) {
/* 15 */     int i = (int)Math.round(d * Math.pow(10.0D, decimals));
/* 16 */     return i / Math.pow(10.0D, decimals);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FB\\util\FBPMathUtil.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */