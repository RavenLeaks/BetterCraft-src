/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ 
/*    */ public class ModelVariable
/*    */   implements IExpression
/*    */ {
/*    */   private String name;
/*    */   private ModelRenderer modelRenderer;
/*    */   private EnumModelVariable enumModelVariable;
/*    */   
/*    */   public ModelVariable(String name, ModelRenderer modelRenderer, EnumModelVariable enumModelVariable) {
/* 13 */     this.name = name;
/* 14 */     this.modelRenderer = modelRenderer;
/* 15 */     this.enumModelVariable = enumModelVariable;
/*    */   }
/*    */ 
/*    */   
/*    */   public float eval() {
/* 20 */     return getValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public float getValue() {
/* 25 */     return this.enumModelVariable.getFloat(this.modelRenderer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setValue(float value) {
/* 30 */     this.enumModelVariable.setFloat(this.modelRenderer, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 35 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\anim\ModelVariable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */