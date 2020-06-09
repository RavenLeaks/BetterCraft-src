/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.AbstractSkeleton;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class ModelSkeleton
/*     */   extends ModelBiped
/*     */ {
/*     */   public ModelSkeleton() {
/*  16 */     this(0.0F, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelSkeleton(float modelSize, boolean p_i46303_2_) {
/*  21 */     super(modelSize, 0.0F, 64, 32);
/*     */     
/*  23 */     if (!p_i46303_2_) {
/*     */       
/*  25 */       this.bipedRightArm = new ModelRenderer(this, 40, 16);
/*  26 */       this.bipedRightArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, modelSize);
/*  27 */       this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
/*  28 */       this.bipedLeftArm = new ModelRenderer(this, 40, 16);
/*  29 */       this.bipedLeftArm.mirror = true;
/*  30 */       this.bipedLeftArm.addBox(-1.0F, -2.0F, -1.0F, 2, 12, 2, modelSize);
/*  31 */       this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
/*  32 */       this.bipedRightLeg = new ModelRenderer(this, 0, 16);
/*  33 */       this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, modelSize);
/*  34 */       this.bipedRightLeg.setRotationPoint(-2.0F, 12.0F, 0.0F);
/*  35 */       this.bipedLeftLeg = new ModelRenderer(this, 0, 16);
/*  36 */       this.bipedLeftLeg.mirror = true;
/*  37 */       this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 12, 2, modelSize);
/*  38 */       this.bipedLeftLeg.setRotationPoint(2.0F, 12.0F, 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/*  48 */     this.rightArmPose = ModelBiped.ArmPose.EMPTY;
/*  49 */     this.leftArmPose = ModelBiped.ArmPose.EMPTY;
/*  50 */     ItemStack itemstack = entitylivingbaseIn.getHeldItem(EnumHand.MAIN_HAND);
/*     */     
/*  52 */     if (itemstack.getItem() == Items.BOW && ((AbstractSkeleton)entitylivingbaseIn).isSwingingArms())
/*     */     {
/*  54 */       if (entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT) {
/*     */         
/*  56 */         this.rightArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
/*     */       }
/*     */       else {
/*     */         
/*  60 */         this.leftArmPose = ModelBiped.ArmPose.BOW_AND_ARROW;
/*     */       } 
/*     */     }
/*     */     
/*  64 */     super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/*  74 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/*  75 */     ItemStack itemstack = ((EntityLivingBase)entityIn).getHeldItemMainhand();
/*  76 */     AbstractSkeleton abstractskeleton = (AbstractSkeleton)entityIn;
/*     */     
/*  78 */     if (abstractskeleton.isSwingingArms() && (itemstack.func_190926_b() || itemstack.getItem() != Items.BOW)) {
/*     */       
/*  80 */       float f = MathHelper.sin(this.swingProgress * 3.1415927F);
/*  81 */       float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * 3.1415927F);
/*  82 */       this.bipedRightArm.rotateAngleZ = 0.0F;
/*  83 */       this.bipedLeftArm.rotateAngleZ = 0.0F;
/*  84 */       this.bipedRightArm.rotateAngleY = -(0.1F - f * 0.6F);
/*  85 */       this.bipedLeftArm.rotateAngleY = 0.1F - f * 0.6F;
/*  86 */       this.bipedRightArm.rotateAngleX = -1.5707964F;
/*  87 */       this.bipedLeftArm.rotateAngleX = -1.5707964F;
/*  88 */       this.bipedRightArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
/*  89 */       this.bipedLeftArm.rotateAngleX -= f * 1.2F - f1 * 0.4F;
/*  90 */       this.bipedRightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/*  91 */       this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/*  92 */       this.bipedRightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/*  93 */       this.bipedLeftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void postRenderArm(float scale, EnumHandSide side) {
/*  99 */     float f = (side == EnumHandSide.RIGHT) ? 1.0F : -1.0F;
/* 100 */     ModelRenderer modelrenderer = getArmForSide(side);
/* 101 */     modelrenderer.rotationPointX += f;
/* 102 */     modelrenderer.postRender(scale);
/* 103 */     modelrenderer.rotationPointX -= f;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelSkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */