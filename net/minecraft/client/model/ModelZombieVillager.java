/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.monster.EntityZombie;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class ModelZombieVillager
/*    */   extends ModelBiped
/*    */ {
/*    */   public ModelZombieVillager() {
/* 11 */     this(0.0F, 0.0F, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelZombieVillager(float p_i1165_1_, float p_i1165_2_, boolean p_i1165_3_) {
/* 16 */     super(p_i1165_1_, 0.0F, 64, p_i1165_3_ ? 32 : 64);
/*    */     
/* 18 */     if (p_i1165_3_) {
/*    */       
/* 20 */       this.bipedHead = new ModelRenderer(this, 0, 0);
/* 21 */       this.bipedHead.addBox(-4.0F, -10.0F, -4.0F, 8, 8, 8, p_i1165_1_);
/* 22 */       this.bipedHead.setRotationPoint(0.0F, 0.0F + p_i1165_2_, 0.0F);
/* 23 */       this.bipedBody = new ModelRenderer(this, 16, 16);
/* 24 */       this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i1165_2_, 0.0F);
/* 25 */       this.bipedBody.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, p_i1165_1_ + 0.1F);
/* 26 */       this.bipedRightLeg = new ModelRenderer(this, 0, 16);
/* 27 */       this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F + p_i1165_2_, 0.0F);
/* 28 */       this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_ + 0.1F);
/* 29 */       this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
/* 30 */       this.bipedLeftLeg.mirror = true;
/* 31 */       this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F + p_i1165_2_, 0.0F);
/* 32 */       this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_ + 0.1F);
/*    */     }
/*    */     else {
/*    */       
/* 36 */       this.bipedHead = new ModelRenderer(this, 0, 0);
/* 37 */       this.bipedHead.setRotationPoint(0.0F, p_i1165_2_, 0.0F);
/* 38 */       this.bipedHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i1165_1_);
/* 39 */       this.bipedHead.setTextureOffset(24, 0).addBox(-1.0F, -3.0F, -6.0F, 2, 4, 2, p_i1165_1_);
/* 40 */       this.bipedBody = new ModelRenderer(this, 16, 20);
/* 41 */       this.bipedBody.setRotationPoint(0.0F, 0.0F + p_i1165_2_, 0.0F);
/* 42 */       this.bipedBody.addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, p_i1165_1_);
/* 43 */       this.bipedBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, p_i1165_1_ + 0.05F);
/* 44 */       this.bipedRightArm = new ModelRenderer(this, 44, 38);
/* 45 */       this.bipedRightArm.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i1165_1_);
/* 46 */       this.bipedRightArm.setRotationPoint(-5.0F, 2.0F + p_i1165_2_, 0.0F);
/* 47 */       this.bipedLeftArm = new ModelRenderer(this, 44, 38);
/* 48 */       this.bipedLeftArm.mirror = true;
/* 49 */       this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i1165_1_);
/* 50 */       this.bipedLeftArm.setRotationPoint(5.0F, 2.0F + p_i1165_2_, 0.0F);
/* 51 */       this.bipedRightLeg = new ModelRenderer(this, 0, 22);
/* 52 */       this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F + p_i1165_2_, 0.0F);
/* 53 */       this.bipedRightLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_);
/* 54 */       this.bipedLeftLeg = new ModelRenderer(this, 0, 22);
/* 55 */       this.bipedLeftLeg.mirror = true;
/* 56 */       this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F + p_i1165_2_, 0.0F);
/* 57 */       this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i1165_1_);
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
/* 68 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 69 */     EntityZombie entityzombie = (EntityZombie)entityIn;
/* 70 */     float f = MathHelper.sin(this.swingProgress * 3.1415927F);
/* 71 */     float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * 3.1415927F);
/* 72 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/* 73 */     this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 74 */     this.bipedRightArm.rotateAngleY = -(0.1F - f * 0.6F);
/* 75 */     this.bipedLeftArm.rotateAngleY = 0.1F - f * 0.6F;
/* 76 */     float f2 = -3.1415927F / (entityzombie.isArmsRaised() ? 1.5F : 2.25F);
/* 77 */     this.bipedRightArm.rotateAngleX = f2;
/* 78 */     this.bipedLeftArm.rotateAngleX = f2;
/* 79 */     this.bipedRightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
/* 80 */     this.bipedLeftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
/* 81 */     this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 82 */     this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 83 */     this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/* 84 */     this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelZombieVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */