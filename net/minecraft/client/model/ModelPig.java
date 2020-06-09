/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ public class ModelPig
/*    */   extends ModelQuadruped
/*    */ {
/*    */   public ModelPig() {
/*  7 */     this(0.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelPig(float scale) {
/* 12 */     super(6, scale);
/* 13 */     this.head.setTextureOffset(16, 16).addBox(-2.0F, 0.0F, -9.0F, 4, 3, 1, scale);
/* 14 */     this.childYOffset = 4.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelPig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */