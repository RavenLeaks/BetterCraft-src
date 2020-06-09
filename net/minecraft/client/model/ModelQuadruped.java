/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class ModelQuadruped
/*    */   extends ModelBase {
/*  9 */   public ModelRenderer head = new ModelRenderer(this, 0, 0);
/*    */   public ModelRenderer body;
/*    */   public ModelRenderer leg1;
/*    */   public ModelRenderer leg2;
/*    */   public ModelRenderer leg3;
/*    */   public ModelRenderer leg4;
/* 15 */   protected float childYOffset = 8.0F;
/* 16 */   protected float childZOffset = 4.0F;
/*    */ 
/*    */   
/*    */   public ModelQuadruped(int height, float scale) {
/* 20 */     this.head.addBox(-4.0F, -4.0F, -8.0F, 8, 8, 8, scale);
/* 21 */     this.head.setRotationPoint(0.0F, (18 - height), -6.0F);
/* 22 */     this.body = new ModelRenderer(this, 28, 8);
/* 23 */     this.body.addBox(-5.0F, -10.0F, -7.0F, 10, 16, 8, scale);
/* 24 */     this.body.setRotationPoint(0.0F, (17 - height), 2.0F);
/* 25 */     this.leg1 = new ModelRenderer(this, 0, 16);
/* 26 */     this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, height, 4, scale);
/* 27 */     this.leg1.setRotationPoint(-3.0F, (24 - height), 7.0F);
/* 28 */     this.leg2 = new ModelRenderer(this, 0, 16);
/* 29 */     this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, height, 4, scale);
/* 30 */     this.leg2.setRotationPoint(3.0F, (24 - height), 7.0F);
/* 31 */     this.leg3 = new ModelRenderer(this, 0, 16);
/* 32 */     this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, height, 4, scale);
/* 33 */     this.leg3.setRotationPoint(-3.0F, (24 - height), -5.0F);
/* 34 */     this.leg4 = new ModelRenderer(this, 0, 16);
/* 35 */     this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, height, 4, scale);
/* 36 */     this.leg4.setRotationPoint(3.0F, (24 - height), -5.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 44 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/*    */     
/* 46 */     if (this.isChild) {
/*    */       
/* 48 */       float f = 2.0F;
/* 49 */       GlStateManager.pushMatrix();
/* 50 */       GlStateManager.translate(0.0F, this.childYOffset * scale, this.childZOffset * scale);
/* 51 */       this.head.render(scale);
/* 52 */       GlStateManager.popMatrix();
/* 53 */       GlStateManager.pushMatrix();
/* 54 */       GlStateManager.scale(0.5F, 0.5F, 0.5F);
/* 55 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/* 56 */       this.body.render(scale);
/* 57 */       this.leg1.render(scale);
/* 58 */       this.leg2.render(scale);
/* 59 */       this.leg3.render(scale);
/* 60 */       this.leg4.render(scale);
/* 61 */       GlStateManager.popMatrix();
/*    */     }
/*    */     else {
/*    */       
/* 65 */       this.head.render(scale);
/* 66 */       this.body.render(scale);
/* 67 */       this.leg1.render(scale);
/* 68 */       this.leg2.render(scale);
/* 69 */       this.leg3.render(scale);
/* 70 */       this.leg4.render(scale);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 81 */     this.head.rotateAngleX = headPitch * 0.017453292F;
/* 82 */     this.head.rotateAngleY = netHeadYaw * 0.017453292F;
/* 83 */     this.body.rotateAngleX = 1.5707964F;
/* 84 */     this.leg1.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
/* 85 */     this.leg2.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
/* 86 */     this.leg3.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
/* 87 */     this.leg4.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelQuadruped.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */