/*    */ package optifine;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ 
/*    */ 
/*    */ public class ReflectorField
/*    */ {
/*    */   private IFieldLocator fieldLocator;
/*    */   private boolean checked;
/*    */   private Field targetField;
/*    */   
/*    */   public ReflectorField(ReflectorClass p_i85_1_, String p_i85_2_) {
/* 13 */     this(new FieldLocatorName(p_i85_1_, p_i85_2_));
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(ReflectorClass p_i86_1_, String p_i86_2_, boolean p_i86_3_) {
/* 18 */     this(new FieldLocatorName(p_i86_1_, p_i86_2_), p_i86_3_);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(ReflectorClass p_i87_1_, Class p_i87_2_) {
/* 23 */     this(p_i87_1_, p_i87_2_, 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(ReflectorClass p_i88_1_, Class p_i88_2_, int p_i88_3_) {
/* 28 */     this(new FieldLocatorType(p_i88_1_, p_i88_2_, p_i88_3_));
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(Field p_i89_1_) {
/* 33 */     this(new FieldLocatorFixed(p_i89_1_));
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(IFieldLocator p_i90_1_) {
/* 38 */     this(p_i90_1_, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField(IFieldLocator p_i91_1_, boolean p_i91_2_) {
/* 43 */     this.fieldLocator = null;
/* 44 */     this.checked = false;
/* 45 */     this.targetField = null;
/* 46 */     this.fieldLocator = p_i91_1_;
/*    */     
/* 48 */     if (!p_i91_2_)
/*    */     {
/* 50 */       getTargetField();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public Field getTargetField() {
/* 56 */     if (this.checked)
/*    */     {
/* 58 */       return this.targetField;
/*    */     }
/*    */ 
/*    */     
/* 62 */     this.checked = true;
/* 63 */     this.targetField = this.fieldLocator.getField();
/*    */     
/* 65 */     if (this.targetField != null)
/*    */     {
/* 67 */       this.targetField.setAccessible(true);
/*    */     }
/*    */     
/* 70 */     return this.targetField;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getValue() {
/* 76 */     return Reflector.getFieldValue((Object)null, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(Object p_setValue_1_) {
/* 81 */     Reflector.setFieldValue(null, this, p_setValue_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(Object p_setValue_1_, Object p_setValue_2_) {
/* 86 */     Reflector.setFieldValue(p_setValue_1_, this, p_setValue_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean exists() {
/* 91 */     return (getTargetField() != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ReflectorField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */