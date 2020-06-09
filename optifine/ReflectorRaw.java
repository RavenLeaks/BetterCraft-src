/*     */ package optifine;
/*     */ 
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReflectorRaw
/*     */ {
/*     */   public static Field getField(Class p_getField_0_, Class<?> p_getField_1_) {
/*     */     try {
/*  15 */       Field[] afield = p_getField_0_.getDeclaredFields();
/*     */       
/*  17 */       for (int i = 0; i < afield.length; i++) {
/*     */         
/*  19 */         Field field = afield[i];
/*     */         
/*  21 */         if (field.getType() == p_getField_1_) {
/*     */           
/*  23 */           field.setAccessible(true);
/*  24 */           return field;
/*     */         } 
/*     */       } 
/*     */       
/*  28 */       return null;
/*     */     }
/*  30 */     catch (Exception var5) {
/*     */       
/*  32 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getFields(Class p_getFields_0_, Class p_getFields_1_) {
/*     */     try {
/*  40 */       Field[] afield = p_getFields_0_.getDeclaredFields();
/*  41 */       return getFields(afield, p_getFields_1_);
/*     */     }
/*  43 */     catch (Exception var3) {
/*     */       
/*  45 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getFields(Field[] p_getFields_0_, Class<?> p_getFields_1_) {
/*     */     try {
/*  53 */       List<Field> list = new ArrayList();
/*     */       
/*  55 */       for (int i = 0; i < p_getFields_0_.length; i++) {
/*     */         
/*  57 */         Field field = p_getFields_0_[i];
/*     */         
/*  59 */         if (field.getType() == p_getFields_1_) {
/*     */           
/*  61 */           field.setAccessible(true);
/*  62 */           list.add(field);
/*     */         } 
/*     */       } 
/*     */       
/*  66 */       Field[] afield = list.<Field>toArray(new Field[list.size()]);
/*  67 */       return afield;
/*     */     }
/*  69 */     catch (Exception var5) {
/*     */       
/*  71 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getFieldsAfter(Class p_getFieldsAfter_0_, Field p_getFieldsAfter_1_, Class p_getFieldsAfter_2_) {
/*     */     try {
/*  79 */       Field[] afield = p_getFieldsAfter_0_.getDeclaredFields();
/*  80 */       List<Field> list = Arrays.asList(afield);
/*  81 */       int i = list.indexOf(p_getFieldsAfter_1_);
/*     */       
/*  83 */       if (i < 0)
/*     */       {
/*  85 */         return new Field[0];
/*     */       }
/*     */ 
/*     */       
/*  89 */       List<Field> list1 = list.subList(i + 1, list.size());
/*  90 */       Field[] afield1 = list1.<Field>toArray(new Field[list1.size()]);
/*  91 */       return getFields(afield1, p_getFieldsAfter_2_);
/*     */     
/*     */     }
/*  94 */     catch (Exception var8) {
/*     */       
/*  96 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Field[] getFields(Object p_getFields_0_, Field[] p_getFields_1_, Class<?> p_getFields_2_, Object p_getFields_3_) {
/*     */     try {
/* 104 */       List<Field> list = new ArrayList<>();
/*     */       
/* 106 */       for (int i = 0; i < p_getFields_1_.length; i++) {
/*     */         
/* 108 */         Field field = p_getFields_1_[i];
/*     */         
/* 110 */         if (field.getType() == p_getFields_2_) {
/*     */           
/* 112 */           boolean flag = Modifier.isStatic(field.getModifiers());
/*     */           
/* 114 */           if ((p_getFields_0_ != null || flag) && (p_getFields_0_ == null || !flag)) {
/*     */             
/* 116 */             field.setAccessible(true);
/* 117 */             Object object = field.get(p_getFields_0_);
/*     */             
/* 119 */             if (object == p_getFields_3_) {
/*     */               
/* 121 */               list.add(field);
/*     */             }
/* 123 */             else if (object != null && p_getFields_3_ != null && object.equals(p_getFields_3_)) {
/*     */               
/* 125 */               list.add(field);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 131 */       Field[] afield = list.<Field>toArray(new Field[list.size()]);
/* 132 */       return afield;
/*     */     }
/* 134 */     catch (Exception var9) {
/*     */       
/* 136 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static Field getField(Class p_getField_0_, Class p_getField_1_, int p_getField_2_) {
/* 142 */     Field[] afield = getFields(p_getField_0_, p_getField_1_);
/* 143 */     return (p_getField_2_ >= 0 && p_getField_2_ < afield.length) ? afield[p_getField_2_] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Field getFieldAfter(Class p_getFieldAfter_0_, Field p_getFieldAfter_1_, Class p_getFieldAfter_2_, int p_getFieldAfter_3_) {
/* 148 */     Field[] afield = getFieldsAfter(p_getFieldAfter_0_, p_getFieldAfter_1_, p_getFieldAfter_2_);
/* 149 */     return (p_getFieldAfter_3_ >= 0 && p_getFieldAfter_3_ < afield.length) ? afield[p_getFieldAfter_3_] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Object getFieldValue(Object p_getFieldValue_0_, Class p_getFieldValue_1_, Class p_getFieldValue_2_) {
/* 154 */     ReflectorField reflectorfield = getReflectorField(p_getFieldValue_1_, p_getFieldValue_2_);
/*     */     
/* 156 */     if (reflectorfield == null)
/*     */     {
/* 158 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 162 */     return !reflectorfield.exists() ? null : Reflector.getFieldValue(p_getFieldValue_0_, reflectorfield);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static Object getFieldValue(Object p_getFieldValue_0_, Class p_getFieldValue_1_, Class p_getFieldValue_2_, int p_getFieldValue_3_) {
/* 168 */     ReflectorField reflectorfield = getReflectorField(p_getFieldValue_1_, p_getFieldValue_2_, p_getFieldValue_3_);
/*     */     
/* 170 */     if (reflectorfield == null)
/*     */     {
/* 172 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 176 */     return !reflectorfield.exists() ? null : Reflector.getFieldValue(p_getFieldValue_0_, reflectorfield);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setFieldValue(Object p_setFieldValue_0_, Class p_setFieldValue_1_, Class p_setFieldValue_2_, Object p_setFieldValue_3_) {
/* 182 */     ReflectorField reflectorfield = getReflectorField(p_setFieldValue_1_, p_setFieldValue_2_);
/*     */     
/* 184 */     if (reflectorfield == null)
/*     */     {
/* 186 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 190 */     return !reflectorfield.exists() ? false : Reflector.setFieldValue(p_setFieldValue_0_, reflectorfield, p_setFieldValue_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean setFieldValue(Object p_setFieldValue_0_, Class p_setFieldValue_1_, Class p_setFieldValue_2_, int p_setFieldValue_3_, Object p_setFieldValue_4_) {
/* 196 */     ReflectorField reflectorfield = getReflectorField(p_setFieldValue_1_, p_setFieldValue_2_, p_setFieldValue_3_);
/*     */     
/* 198 */     if (reflectorfield == null)
/*     */     {
/* 200 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 204 */     return !reflectorfield.exists() ? false : Reflector.setFieldValue(p_setFieldValue_0_, reflectorfield, p_setFieldValue_4_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReflectorField getReflectorField(Class p_getReflectorField_0_, Class p_getReflectorField_1_) {
/* 210 */     Field field = getField(p_getReflectorField_0_, p_getReflectorField_1_);
/*     */     
/* 212 */     if (field == null)
/*     */     {
/* 214 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 218 */     ReflectorClass reflectorclass = new ReflectorClass(p_getReflectorField_0_);
/* 219 */     return new ReflectorField(reflectorclass, field.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static ReflectorField getReflectorField(Class p_getReflectorField_0_, Class p_getReflectorField_1_, int p_getReflectorField_2_) {
/* 225 */     Field field = getField(p_getReflectorField_0_, p_getReflectorField_1_, p_getReflectorField_2_);
/*     */     
/* 227 */     if (field == null)
/*     */     {
/* 229 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 233 */     ReflectorClass reflectorclass = new ReflectorClass(p_getReflectorField_0_);
/* 234 */     return new ReflectorField(reflectorclass, field.getName());
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ReflectorRaw.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */