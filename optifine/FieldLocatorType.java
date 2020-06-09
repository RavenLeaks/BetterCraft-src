/*    */ package optifine;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ public class FieldLocatorType
/*    */   implements IFieldLocator
/*    */ {
/*    */   private ReflectorClass reflectorClass;
/*    */   private Class targetFieldType;
/*    */   private int targetFieldIndex;
/*    */   
/*    */   public FieldLocatorType(ReflectorClass p_i39_1_, Class p_i39_2_) {
/* 13 */     this(p_i39_1_, p_i39_2_, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public FieldLocatorType(ReflectorClass p_i40_1_, Class p_i40_2_, int p_i40_3_) {
/* 18 */     this.reflectorClass = null;
/* 19 */     this.targetFieldType = null;
/* 20 */     this.reflectorClass = p_i40_1_;
/* 21 */     this.targetFieldType = p_i40_2_;
/* 22 */     this.targetFieldIndex = p_i40_3_;
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getField() {
/* 27 */     Class oclass = this.reflectorClass.getTargetClass();
/*    */     
/* 29 */     if (oclass == null)
/*    */     {
/* 31 */       return null;
/*    */     }
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 37 */       Field[] afield = oclass.getDeclaredFields();
/* 38 */       int i = 0;
/*    */       
/* 40 */       for (int j = 0; j < afield.length; j++) {
/*    */         
/* 42 */         Field field = afield[j];
/*    */         
/* 44 */         if (field.getType() == this.targetFieldType) {
/*    */           
/* 46 */           if (i == this.targetFieldIndex) {
/*    */             
/* 48 */             field.setAccessible(true);
/* 49 */             return field;
/*    */           } 
/*    */           
/* 52 */           i++;
/*    */         } 
/*    */       } 
/*    */       
/* 56 */       Config.log("(Reflector) Field not present: " + oclass.getName() + ".(type: " + this.targetFieldType + ", index: " + this.targetFieldIndex + ")");
/* 57 */       return null;
/*    */     }
/* 59 */     catch (SecurityException securityexception) {
/*    */       
/* 61 */       securityexception.printStackTrace();
/* 62 */       return null;
/*    */     }
/* 64 */     catch (Throwable throwable) {
/*    */       
/* 66 */       throwable.printStackTrace();
/* 67 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\FieldLocatorType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */