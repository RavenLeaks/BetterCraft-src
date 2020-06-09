/*    */ package com.TominoCZ.FBP.util;
/*    */ 
/*    */ public class FBPReflectionHelper {
/*    */   public static Field findField(Class<?> clazz, String... fields) {
/*    */     byte b;
/*    */     int i;
/*    */     Field[] arrayOfField;
/*  8 */     for (i = (arrayOfField = clazz.getDeclaredFields()).length, b = 0; b < i; ) { Field fld = arrayOfField[b]; byte b1; int j; String[] arrayOfString;
/*  9 */       for (j = (arrayOfString = fields).length, b1 = 0; b1 < j; ) { String name = arrayOfString[b1];
/* 10 */         if (name.equals(fld.getName())) {
/* 11 */           fld.setAccessible(true);
/* 12 */           return fld;
/*    */         }  b1++; }
/*    */        b++; }
/*    */     
/* 16 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FB\\util\FBPReflectionHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */