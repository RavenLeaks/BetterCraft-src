/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ import optifine.Config;
/*    */ 
/*    */ 
/*    */ public class ModelVariableUpdater
/*    */ {
/*    */   private String modelVariableName;
/*    */   private String expressionText;
/*    */   private ModelVariable modelVariable;
/*    */   private IExpression expression;
/*    */   
/*    */   public boolean initialize(IModelResolver mr) {
/* 14 */     this.modelVariable = mr.getModelVariable(this.modelVariableName);
/*    */     
/* 16 */     if (this.modelVariable == null) {
/*    */       
/* 18 */       Config.warn("Model variable not found: " + this.modelVariableName);
/* 19 */       return false;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 25 */       ExpressionParser expressionparser = new ExpressionParser(mr);
/* 26 */       this.expression = expressionparser.parse(this.expressionText);
/* 27 */       return true;
/*    */     }
/* 29 */     catch (ParseException parseexception) {
/*    */       
/* 31 */       Config.warn("Error parsing expression: " + this.expressionText);
/* 32 */       Config.warn(String.valueOf(parseexception.getClass().getName()) + ": " + parseexception.getMessage());
/* 33 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ModelVariableUpdater(String modelVariableName, String expressionText) {
/* 40 */     this.modelVariableName = modelVariableName;
/* 41 */     this.expressionText = expressionText;
/*    */   }
/*    */ 
/*    */   
/*    */   public void update() {
/* 46 */     float f = this.expression.eval();
/* 47 */     this.modelVariable.setValue(f);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\anim\ModelVariableUpdater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */