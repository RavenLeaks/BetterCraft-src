/*    */ package optifine;
/*    */ 
/*    */ 
/*    */ public class ReflectorFields
/*    */ {
/*    */   private ReflectorClass reflectorClass;
/*    */   private Class fieldType;
/*    */   private int fieldCount;
/*    */   private ReflectorField[] reflectorFields;
/*    */   
/*    */   public ReflectorFields(ReflectorClass p_i92_1_, Class p_i92_2_, int p_i92_3_) {
/* 12 */     this.reflectorClass = p_i92_1_;
/* 13 */     this.fieldType = p_i92_2_;
/*    */     
/* 15 */     if (p_i92_1_.exists())
/*    */     {
/* 17 */       if (p_i92_2_ != null) {
/*    */         
/* 19 */         this.reflectorFields = new ReflectorField[p_i92_3_];
/*    */         
/* 21 */         for (int i = 0; i < this.reflectorFields.length; i++)
/*    */         {
/* 23 */           this.reflectorFields[i] = new ReflectorField(p_i92_1_, p_i92_2_, i);
/*    */         }
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorClass getReflectorClass() {
/* 31 */     return this.reflectorClass;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class getFieldType() {
/* 36 */     return this.fieldType;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFieldCount() {
/* 41 */     return this.fieldCount;
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField getReflectorField(int p_getReflectorField_1_) {
/* 46 */     return (p_getReflectorField_1_ >= 0 && p_getReflectorField_1_ < this.reflectorFields.length) ? this.reflectorFields[p_getReflectorField_1_] : null;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ReflectorFields.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */