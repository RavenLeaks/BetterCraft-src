/*    */ package optifine;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ public class FieldLocatorName
/*    */   implements IFieldLocator {
/*  7 */   private ReflectorClass reflectorClass = null;
/*  8 */   private String targetFieldName = null;
/*    */ 
/*    */   
/*    */   public FieldLocatorName(ReflectorClass p_i38_1_, String p_i38_2_) {
/* 12 */     this.reflectorClass = p_i38_1_;
/* 13 */     this.targetFieldName = p_i38_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getField() {
/* 18 */     Class oclass = this.reflectorClass.getTargetClass();
/*    */     
/* 20 */     if (oclass == null)
/*    */     {
/* 22 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 28 */       Field field = getDeclaredField(oclass, this.targetFieldName);
/* 29 */       field.setAccessible(true);
/* 30 */       return field;
/*    */     }
/* 32 */     catch (NoSuchFieldException var3) {
/*    */       
/* 34 */       Config.log("(Reflector) Field not present: " + oclass.getName() + "." + this.targetFieldName);
/* 35 */       return null;
/*    */     }
/* 37 */     catch (SecurityException securityexception) {
/*    */       
/* 39 */       securityexception.printStackTrace();
/* 40 */       return null;
/*    */     }
/* 42 */     catch (Throwable throwable) {
/*    */       
/* 44 */       throwable.printStackTrace();
/* 45 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private Field getDeclaredField(Class<Object> p_getDeclaredField_1_, String p_getDeclaredField_2_) throws NoSuchFieldException {
/* 52 */     Field[] afield = p_getDeclaredField_1_.getDeclaredFields();
/*    */     
/* 54 */     for (int i = 0; i < afield.length; i++) {
/*    */       
/* 56 */       Field field = afield[i];
/*    */       
/* 58 */       if (field.getName().equals(p_getDeclaredField_2_))
/*    */       {
/* 60 */         return field;
/*    */       }
/*    */     } 
/*    */     
/* 64 */     if (p_getDeclaredField_1_ == Object.class)
/*    */     {
/* 66 */       throw new NoSuchFieldException(p_getDeclaredField_2_);
/*    */     }
/*    */ 
/*    */     
/* 70 */     return getDeclaredField(p_getDeclaredField_1_.getSuperclass(), p_getDeclaredField_2_);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\FieldLocatorName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */