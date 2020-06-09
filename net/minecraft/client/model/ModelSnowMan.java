/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class ModelSnowMan
/*    */   extends ModelBase
/*    */ {
/*    */   public ModelRenderer body;
/*    */   public ModelRenderer bottomBody;
/*    */   public ModelRenderer head;
/*    */   public ModelRenderer rightHand;
/*    */   public ModelRenderer leftHand;
/*    */   
/*    */   public ModelSnowMan() {
/* 16 */     float f = 4.0F;
/* 17 */     float f1 = 0.0F;
/* 18 */     this.head = (new ModelRenderer(this, 0, 0)).setTextureSize(64, 64);
/* 19 */     this.head.addBox(-4.0F, -8.0F, -4.0F, 8, 8, 8, -0.5F);
/* 20 */     this.head.setRotationPoint(0.0F, 4.0F, 0.0F);
/* 21 */     this.rightHand = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 64);
/* 22 */     this.rightHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, -0.5F);
/* 23 */     this.rightHand.setRotationPoint(0.0F, 6.0F, 0.0F);
/* 24 */     this.leftHand = (new ModelRenderer(this, 32, 0)).setTextureSize(64, 64);
/* 25 */     this.leftHand.addBox(-1.0F, 0.0F, -1.0F, 12, 2, 2, -0.5F);
/* 26 */     this.leftHand.setRotationPoint(0.0F, 6.0F, 0.0F);
/* 27 */     this.body = (new ModelRenderer(this, 0, 16)).setTextureSize(64, 64);
/* 28 */     this.body.addBox(-5.0F, -10.0F, -5.0F, 10, 10, 10, -0.5F);
/* 29 */     this.body.setRotationPoint(0.0F, 13.0F, 0.0F);
/* 30 */     this.bottomBody = (new ModelRenderer(this, 0, 36)).setTextureSize(64, 64);
/* 31 */     this.bottomBody.addBox(-6.0F, -12.0F, -6.0F, 12, 12, 12, -0.5F);
/* 32 */     this.bottomBody.setRotationPoint(0.0F, 24.0F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 42 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 43 */     this.head.rotateAngleY = netHeadYaw * 0.017453292F;
/* 44 */     this.head.rotateAngleX = headPitch * 0.017453292F;
/* 45 */     this.body.rotateAngleY = netHeadYaw * 0.017453292F * 0.25F;
/* 46 */     float f = MathHelper.sin(this.body.rotateAngleY);
/* 47 */     float f1 = MathHelper.cos(this.body.rotateAngleY);
/* 48 */     this.rightHand.rotateAngleZ = 1.0F;
/* 49 */     this.leftHand.rotateAngleZ = -1.0F;
/* 50 */     this.rightHand.rotateAngleY = 0.0F + this.body.rotateAngleY;
/* 51 */     this.leftHand.rotateAngleY = 3.1415927F + this.body.rotateAngleY;
/* 52 */     this.rightHand.rotationPointX = f1 * 5.0F;
/* 53 */     this.rightHand.rotationPointZ = -f * 5.0F;
/* 54 */     this.leftHand.rotationPointX = -f1 * 5.0F;
/* 55 */     this.leftHand.rotationPointZ = f * 5.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 63 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/* 64 */     this.body.render(scale);
/* 65 */     this.bottomBody.render(scale);
/* 66 */     this.head.render(scale);
/* 67 */     this.rightHand.render(scale);
/* 68 */     this.leftHand.render(scale);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelSnowMan.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */