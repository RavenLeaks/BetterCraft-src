/*    */ package net.minecraft.client.model;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ModelVillager
/*    */   extends ModelBase
/*    */ {
/*    */   public ModelRenderer villagerHead;
/*    */   public ModelRenderer villagerBody;
/*    */   public ModelRenderer villagerArms;
/*    */   public ModelRenderer rightVillagerLeg;
/*    */   public ModelRenderer leftVillagerLeg;
/*    */   public ModelRenderer villagerNose;
/*    */   
/*    */   public ModelVillager(float scale) {
/* 26 */     this(scale, 0.0F, 64, 64);
/*    */   }
/*    */ 
/*    */   
/*    */   public ModelVillager(float scale, float p_i1164_2_, int width, int height) {
/* 31 */     this.villagerHead = (new ModelRenderer(this)).setTextureSize(width, height);
/* 32 */     this.villagerHead.setRotationPoint(0.0F, 0.0F + p_i1164_2_, 0.0F);
/* 33 */     this.villagerHead.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, scale);
/* 34 */     this.villagerNose = (new ModelRenderer(this)).setTextureSize(width, height);
/* 35 */     this.villagerNose.setRotationPoint(0.0F, p_i1164_2_ - 2.0F, 0.0F);
/* 36 */     this.villagerNose.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, scale);
/* 37 */     this.villagerHead.addChild(this.villagerNose);
/* 38 */     this.villagerBody = (new ModelRenderer(this)).setTextureSize(width, height);
/* 39 */     this.villagerBody.setRotationPoint(0.0F, 0.0F + p_i1164_2_, 0.0F);
/* 40 */     this.villagerBody.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, scale);
/* 41 */     this.villagerBody.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, scale + 0.5F);
/* 42 */     this.villagerArms = (new ModelRenderer(this)).setTextureSize(width, height);
/* 43 */     this.villagerArms.setRotationPoint(0.0F, 0.0F + p_i1164_2_ + 2.0F, 0.0F);
/* 44 */     this.villagerArms.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, scale);
/* 45 */     this.villagerArms.setTextureOffset(44, 22).addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, scale);
/* 46 */     this.villagerArms.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, scale);
/* 47 */     this.rightVillagerLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(width, height);
/* 48 */     this.rightVillagerLeg.setRotationPoint(-2.0F, 12.0F + p_i1164_2_, 0.0F);
/* 49 */     this.rightVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
/* 50 */     this.leftVillagerLeg = (new ModelRenderer(this, 0, 22)).setTextureSize(width, height);
/* 51 */     this.leftVillagerLeg.mirror = true;
/* 52 */     this.leftVillagerLeg.setRotationPoint(2.0F, 12.0F + p_i1164_2_, 0.0F);
/* 53 */     this.leftVillagerLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, scale);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 61 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/* 62 */     this.villagerHead.render(scale);
/* 63 */     this.villagerBody.render(scale);
/* 64 */     this.rightVillagerLeg.render(scale);
/* 65 */     this.leftVillagerLeg.render(scale);
/* 66 */     this.villagerArms.render(scale);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 76 */     this.villagerHead.rotateAngleY = netHeadYaw * 0.017453292F;
/* 77 */     this.villagerHead.rotateAngleX = headPitch * 0.017453292F;
/* 78 */     this.villagerArms.rotationPointY = 3.0F;
/* 79 */     this.villagerArms.rotationPointZ = -1.0F;
/* 80 */     this.villagerArms.rotateAngleX = -0.75F;
/* 81 */     this.rightVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
/* 82 */     this.leftVillagerLeg.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount * 0.5F;
/* 83 */     this.rightVillagerLeg.rotateAngleY = 0.0F;
/* 84 */     this.leftVillagerLeg.rotateAngleY = 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelVillager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */