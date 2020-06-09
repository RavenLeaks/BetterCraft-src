/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ 
/*    */ public class ModelLeashKnot
/*    */   extends ModelBase
/*    */ {
/*    */   public ModelRenderer knotRenderer;
/*    */   
/*    */   public ModelLeashKnot() {
/* 11 */     this(0, 0, 32, 32);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelLeashKnot(int p_i46365_1_, int p_i46365_2_, int p_i46365_3_, int p_i46365_4_) {
/* 16 */     this.textureWidth = p_i46365_3_;
/* 17 */     this.textureHeight = p_i46365_4_;
/* 18 */     this.knotRenderer = new ModelRenderer(this, p_i46365_1_, p_i46365_2_);
/* 19 */     this.knotRenderer.addBox(-3.0F, -6.0F, -3.0F, 6, 8, 6, 0.0F);
/* 20 */     this.knotRenderer.setRotationPoint(0.0F, 0.0F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 28 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/* 29 */     this.knotRenderer.render(scale);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 39 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 40 */     this.knotRenderer.rotateAngleY = netHeadYaw * 0.017453292F;
/* 41 */     this.knotRenderer.rotateAngleX = headPitch * 0.017453292F;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelLeashKnot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */