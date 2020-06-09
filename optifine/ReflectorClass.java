/*    */ package optifine;
/*    */ 
/*    */ 
/*    */ public class ReflectorClass
/*    */ {
/*    */   private String targetClassName;
/*    */   private boolean checked;
/*    */   private Class targetClass;
/*    */   
/*    */   public ReflectorClass(String p_i81_1_) {
/* 11 */     this(p_i81_1_, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorClass(String p_i82_1_, boolean p_i82_2_) {
/* 16 */     this.targetClassName = null;
/* 17 */     this.checked = false;
/* 18 */     this.targetClass = null;
/* 19 */     this.targetClassName = p_i82_1_;
/*    */     
/* 21 */     if (!p_i82_2_)
/*    */     {
/* 23 */       Class clazz = getTargetClass();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorClass(Class p_i83_1_) {
/* 29 */     this.targetClassName = null;
/* 30 */     this.checked = false;
/* 31 */     this.targetClass = null;
/* 32 */     this.targetClass = p_i83_1_;
/* 33 */     this.targetClassName = p_i83_1_.getName();
/* 34 */     this.checked = true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class getTargetClass() {
/* 39 */     if (this.checked)
/*    */     {
/* 41 */       return this.targetClass;
/*    */     }
/*    */ 
/*    */     
/* 45 */     this.checked = true;
/*    */ 
/*    */     
/*    */     try {
/* 49 */       this.targetClass = Class.forName(this.targetClassName);
/*    */     }
/* 51 */     catch (ClassNotFoundException var2) {
/*    */       
/* 53 */       Config.log("(Reflector) Class not present: " + this.targetClassName);
/*    */     }
/* 55 */     catch (Throwable throwable) {
/*    */       
/* 57 */       throwable.printStackTrace();
/*    */     } 
/*    */     
/* 60 */     return this.targetClass;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean exists() {
/* 66 */     return (getTargetClass() != null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getTargetClassName() {
/* 71 */     return this.targetClassName;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInstance(Object p_isInstance_1_) {
/* 76 */     return (getTargetClass() == null) ? false : getTargetClass().isInstance(p_isInstance_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorField makeField(String p_makeField_1_) {
/* 81 */     return new ReflectorField(this, p_makeField_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorMethod makeMethod(String p_makeMethod_1_) {
/* 86 */     return new ReflectorMethod(this, p_makeMethod_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorMethod makeMethod(String p_makeMethod_1_, Class[] p_makeMethod_2_) {
/* 91 */     return new ReflectorMethod(this, p_makeMethod_1_, p_makeMethod_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectorMethod makeMethod(String p_makeMethod_1_, Class[] p_makeMethod_2_, boolean p_makeMethod_3_) {
/* 96 */     return new ReflectorMethod(this, p_makeMethod_1_, p_makeMethod_2_, p_makeMethod_3_);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ReflectorClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */