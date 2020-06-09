/*    */ package net.optifine.entity.model.anim;
/*    */ 
/*    */ public class Constant
/*    */   implements IExpression
/*    */ {
/*    */   private float value;
/*    */   
/*    */   public Constant(float value) {
/*  9 */     this.value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public float eval() {
/* 14 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 19 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\optifine\entity\model\anim\Constant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */