/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ public class Function
/*    */   implements IExpression
/*    */ {
/*    */   private EnumFunctionType enumFunction;
/*    */   private IExpression[] arguments;
/*    */   
/*    */   public Function(EnumFunctionType enumFunction, IExpression[] arguments) {
/* 10 */     this.enumFunction = enumFunction;
/* 11 */     this.arguments = arguments;
/*    */   }
/*    */ 
/*    */   
/*    */   public float eval() {
/* 16 */     return this.enumFunction.eval(this.arguments);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 21 */     return this.enumFunction + "()";
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\anim\Function.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */