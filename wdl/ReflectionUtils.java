/*     */ package wdl;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectionUtils
/*     */ {
/*     */   public static Field stealField(Class<?> typeOfClass, Class<?> typeOfField) {
/*  20 */     Field[] fields = typeOfClass.getDeclaredFields(); byte b; int i;
/*     */     Field[] arrayOfField1;
/*  22 */     for (i = (arrayOfField1 = fields).length, b = 0; b < i; ) { Field f = arrayOfField1[b];
/*  23 */       if (f.getType().equals(typeOfField)) {
/*     */         try {
/*  25 */           f.setAccessible(true);
/*  26 */           return f;
/*  27 */         } catch (Exception e) {
/*  28 */           throw new RuntimeException(
/*  29 */               "WorldDownloader: Couldn't steal Field of type \"" + 
/*  30 */               typeOfField + "\" from class \"" + typeOfClass + 
/*  31 */               "\" !", e);
/*     */         } 
/*     */       }
/*     */       b++; }
/*     */     
/*  36 */     throw new RuntimeException(
/*  37 */         "WorldDownloader: Couldn't steal Field of type \"" + 
/*  38 */         typeOfField + "\" from class \"" + typeOfClass + 
/*  39 */         "\" !");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T stealAndGetField(Object object, Class<T> typeOfField) {
/*     */     Class<?> typeOfObject;
/*  55 */     if (object instanceof Class) {
/*  56 */       typeOfObject = (Class)object;
/*  57 */       object = null;
/*     */     } else {
/*  59 */       typeOfObject = object.getClass();
/*     */     } 
/*     */     
/*     */     try {
/*  63 */       Field f = stealField(typeOfObject, typeOfField);
/*  64 */       return typeOfField.cast(f.get(object));
/*  65 */     } catch (Exception e) {
/*  66 */       throw new RuntimeException(
/*  67 */           "WorldDownloader: Couldn't get Field of type \"" + 
/*  68 */           typeOfField + "\" from object \"" + object + 
/*  69 */           "\" !", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void stealAndSetField(Object object, Class<?> typeOfField, Object value) {
/*     */     Class<?> typeOfObject;
/*  88 */     if (object instanceof Class) {
/*  89 */       typeOfObject = (Class)object;
/*  90 */       object = null;
/*     */     } else {
/*  92 */       typeOfObject = object.getClass();
/*     */     } 
/*     */     
/*     */     try {
/*  96 */       Field f = stealField(typeOfObject, typeOfField);
/*  97 */       f.set(object, value);
/*  98 */     } catch (Exception e) {
/*  99 */       throw new RuntimeException(
/* 100 */           "WorldDownloader: Couldn't set Field of type \"" + 
/* 101 */           typeOfField + "\" from object \"" + object + 
/* 102 */           "\" to " + value + "!", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T stealAndGetField(Object object, Class<?> typeOfObject, Class<T> typeOfField) {
/*     */     try {
/* 119 */       Field f = stealField(typeOfObject, typeOfField);
/* 120 */       return typeOfField.cast(f.get(object));
/* 121 */     } catch (Exception e) {
/* 122 */       throw new RuntimeException(
/* 123 */           "WorldDownloader: Couldn't get Field of type \"" + 
/* 124 */           typeOfField + "\" from object \"" + object + 
/* 125 */           "\" !", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void stealAndSetField(Object object, Class<?> typeOfObject, Class<?> typeOfField, Object value) {
/*     */     try {
/* 145 */       Field f = stealField(typeOfObject, typeOfField);
/* 146 */       f.set(object, value);
/* 147 */     } catch (Exception e) {
/* 148 */       throw new RuntimeException(
/* 149 */           "WorldDownloader: Couldn't set Field of type \"" + 
/* 150 */           typeOfField + "\" from object \"" + object + 
/* 151 */           "\" to " + value + "!", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\ReflectionUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */