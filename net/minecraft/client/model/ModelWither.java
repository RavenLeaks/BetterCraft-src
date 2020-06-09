/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.boss.EntityWither;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class ModelWither
/*    */   extends ModelBase
/*    */ {
/*    */   private final ModelRenderer[] upperBodyParts;
/*    */   private final ModelRenderer[] heads;
/*    */   
/*    */   public ModelWither(float p_i46302_1_) {
/* 15 */     this.textureWidth = 64;
/* 16 */     this.textureHeight = 64;
/* 17 */     this.upperBodyParts = new ModelRenderer[3];
/* 18 */     this.upperBodyParts[0] = new ModelRenderer(this, 0, 16);
/* 19 */     this.upperBodyParts[0].addBox(-10.0F, 3.9F, -0.5F, 20, 3, 3, p_i46302_1_);
/* 20 */     this.upperBodyParts[1] = (new ModelRenderer(this)).setTextureSize(this.textureWidth, this.textureHeight);
/* 21 */     this.upperBodyParts[1].setRotationPoint(-2.0F, 6.9F, -0.5F);
/* 22 */     this.upperBodyParts[1].setTextureOffset(0, 22).addBox(0.0F, 0.0F, 0.0F, 3, 10, 3, p_i46302_1_);
/* 23 */     this.upperBodyParts[1].setTextureOffset(24, 22).addBox(-4.0F, 1.5F, 0.5F, 11, 2, 2, p_i46302_1_);
/* 24 */     this.upperBodyParts[1].setTextureOffset(24, 22).addBox(-4.0F, 4.0F, 0.5F, 11, 2, 2, p_i46302_1_);
/* 25 */     this.upperBodyParts[1].setTextureOffset(24, 22).addBox(-4.0F, 6.5F, 0.5F, 11, 2, 2, p_i46302_1_);
/* 26 */     this.upperBodyParts[2] = new ModelRenderer(this, 12, 22);
/* 27 */     this.upperBodyParts[2].addBox(0.0F, 0.0F, 0.0F, 3, 6, 3, p_i46302_1_);
/* 28 */     this.heads = new ModelRenderer[3];
/* 29 */     this.heads[0] = new ModelRenderer(this, 0, 0);
/* 30 */     this.heads[0].addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8, p_i46302_1_);
/* 31 */     this.heads[1] = new ModelRenderer(this, 32, 0);
/* 32 */     this.heads[1].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6, p_i46302_1_);
/* 33 */     (this.heads[1]).rotationPointX = -8.0F;
/* 34 */     (this.heads[1]).rotationPointY = 4.0F;
/* 35 */     this.heads[2] = new ModelRenderer(this, 32, 0);
/* 36 */     this.heads[2].addBox(-4.0F, -4.0F, -4.0F, 6, 6, 6, p_i46302_1_);
/* 37 */     (this.heads[2]).rotationPointX = 10.0F;
/* 38 */     (this.heads[2]).rotationPointY = 4.0F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 46 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn); byte b; int i;
/*    */     ModelRenderer[] arrayOfModelRenderer;
/* 48 */     for (i = (arrayOfModelRenderer = this.heads).length, b = 0; b < i; ) { ModelRenderer modelrenderer = arrayOfModelRenderer[b];
/*    */       
/* 50 */       modelrenderer.render(scale);
/*    */       b++; }
/*    */     
/* 53 */     for (i = (arrayOfModelRenderer = this.upperBodyParts).length, b = 0; b < i; ) { ModelRenderer modelrenderer1 = arrayOfModelRenderer[b];
/*    */       
/* 55 */       modelrenderer1.render(scale);
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 66 */     float f = MathHelper.cos(ageInTicks * 0.1F);
/* 67 */     (this.upperBodyParts[1]).rotateAngleX = (0.065F + 0.05F * f) * 3.1415927F;
/* 68 */     this.upperBodyParts[2].setRotationPoint(-2.0F, 6.9F + MathHelper.cos((this.upperBodyParts[1]).rotateAngleX) * 10.0F, -0.5F + MathHelper.sin((this.upperBodyParts[1]).rotateAngleX) * 10.0F);
/* 69 */     (this.upperBodyParts[2]).rotateAngleX = (0.265F + 0.1F * f) * 3.1415927F;
/* 70 */     (this.heads[0]).rotateAngleY = netHeadYaw * 0.017453292F;
/* 71 */     (this.heads[0]).rotateAngleX = headPitch * 0.017453292F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/* 80 */     EntityWither entitywither = (EntityWither)entitylivingbaseIn;
/*    */     
/* 82 */     for (int i = 1; i < 3; i++) {
/*    */       
/* 84 */       (this.heads[i]).rotateAngleY = (entitywither.getHeadYRotation(i - 1) - entitylivingbaseIn.renderYawOffset) * 0.017453292F;
/* 85 */       (this.heads[i]).rotateAngleX = entitywither.getHeadXRotation(i - 1) * 0.017453292F;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelWither.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */