/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.monster.EntityZombie;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class ModelZombie
/*    */   extends ModelBiped
/*    */ {
/*    */   public ModelZombie() {
/* 11 */     this(0.0F, false);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelZombie(float modelSize, boolean p_i1168_2_) {
/* 16 */     super(modelSize, 0.0F, 64, p_i1168_2_ ? 32 : 64);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 26 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 27 */     boolean flag = (entityIn instanceof EntityZombie && ((EntityZombie)entityIn).isArmsRaised());
/* 28 */     float f = MathHelper.sin(this.swingProgress * 3.1415927F);
/* 29 */     float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * 3.1415927F);
/* 30 */     this.bipedRightArm.rotateAngleZ = 0.0F;
/* 31 */     this.bipedLeftArm.rotateAngleZ = 0.0F;
/* 32 */     this.bipedRightArm.rotateAngleY = -(0.1F - f * 0.6F);
/* 33 */     this.bipedLeftArm.rotateAngleY = 0.1F - f * 0.6F;
/* 34 */     float f2 = -3.1415927F / (flag ? 1.5F : 2.25F);
/* 35 */     this.bipedRightArm.rotateAngleX = f2;
/* 36 */     this.bipedLeftArm.rotateAngleX = f2;
/* 37 */     this.bipedRightArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
/* 38 */     this.bipedLeftArm.rotateAngleX += f * 1.2F - f1 * 0.4F;
/* 39 */     this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 40 */     this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 41 */     this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/* 42 */     this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */