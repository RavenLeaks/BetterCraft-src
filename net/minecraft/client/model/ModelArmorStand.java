/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityArmorStand;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ 
/*     */ public class ModelArmorStand
/*     */   extends ModelArmorStandArmor
/*     */ {
/*     */   public ModelRenderer standRightSide;
/*     */   public ModelRenderer standLeftSide;
/*     */   public ModelRenderer standWaist;
/*     */   public ModelRenderer standBase;
/*     */   
/*     */   public ModelArmorStand() {
/*  17 */     this(0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelArmorStand(float modelSize) {
/*  22 */     super(modelSize, 64, 64);
/*  23 */     this.bipedHead = new ModelRenderer(this, 0, 0);
/*  24 */     this.bipedHead.addBox(-1.0F, -7.0F, -1.0F, 2, 7, 2, modelSize);
/*  25 */     this.bipedHead.setRotationPoint(0.0F, 0.0F, 0.0F);
/*  26 */     this.bipedBody = new ModelRenderer(this, 0, 26);
/*  27 */     this.bipedBody.addBox(-6.0F, 0.0F, -1.5F, 12, 3, 3, modelSize);
/*  28 */     this.bipedBody.setRotationPoint(0.0F, 0.0F, 0.0F);
/*  29 */     this.bipedRightArm = new ModelRenderer(this, 24, 0);
/*  30 */     this.bipedRightArm.addBox(-2.0F, -2.0F, -1.0F, 2, 12, 2, modelSize);
/*  31 */     this.bipedRightArm.setRotationPoint(-5.0F, 2.0F, 0.0F);
/*  32 */     this.bipedLeftArm = new ModelRenderer(this, 32, 16);
/*  33 */     this.bipedLeftArm.mirror = true;
/*  34 */     this.bipedLeftArm.addBox(0.0F, -2.0F, -1.0F, 2, 12, 2, modelSize);
/*  35 */     this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
/*  36 */     this.bipedRightLeg = new ModelRenderer(this, 8, 0);
/*  37 */     this.bipedRightLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, modelSize);
/*  38 */     this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
/*  39 */     this.bipedLeftLeg = new ModelRenderer(this, 40, 16);
/*  40 */     this.bipedLeftLeg.mirror = true;
/*  41 */     this.bipedLeftLeg.addBox(-1.0F, 0.0F, -1.0F, 2, 11, 2, modelSize);
/*  42 */     this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
/*  43 */     this.standRightSide = new ModelRenderer(this, 16, 0);
/*  44 */     this.standRightSide.addBox(-3.0F, 3.0F, -1.0F, 2, 7, 2, modelSize);
/*  45 */     this.standRightSide.setRotationPoint(0.0F, 0.0F, 0.0F);
/*  46 */     this.standRightSide.showModel = true;
/*  47 */     this.standLeftSide = new ModelRenderer(this, 48, 16);
/*  48 */     this.standLeftSide.addBox(1.0F, 3.0F, -1.0F, 2, 7, 2, modelSize);
/*  49 */     this.standLeftSide.setRotationPoint(0.0F, 0.0F, 0.0F);
/*  50 */     this.standWaist = new ModelRenderer(this, 0, 48);
/*  51 */     this.standWaist.addBox(-4.0F, 10.0F, -1.0F, 8, 2, 2, modelSize);
/*  52 */     this.standWaist.setRotationPoint(0.0F, 0.0F, 0.0F);
/*  53 */     this.standBase = new ModelRenderer(this, 0, 32);
/*  54 */     this.standBase.addBox(-6.0F, 11.0F, -6.0F, 12, 1, 12, modelSize);
/*  55 */     this.standBase.setRotationPoint(0.0F, 12.0F, 0.0F);
/*  56 */     this.bipedHeadwear.showModel = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/*  66 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/*     */     
/*  68 */     if (entityIn instanceof EntityArmorStand) {
/*     */       
/*  70 */       EntityArmorStand entityarmorstand = (EntityArmorStand)entityIn;
/*  71 */       this.bipedLeftArm.showModel = entityarmorstand.getShowArms();
/*  72 */       this.bipedRightArm.showModel = entityarmorstand.getShowArms();
/*  73 */       this.standBase.showModel = !entityarmorstand.hasNoBasePlate();
/*  74 */       this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
/*  75 */       this.bipedRightLeg.setRotationPoint(-1.9F, 12.0F, 0.0F);
/*  76 */       this.standRightSide.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
/*  77 */       this.standRightSide.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
/*  78 */       this.standRightSide.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();
/*  79 */       this.standLeftSide.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
/*  80 */       this.standLeftSide.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
/*  81 */       this.standLeftSide.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();
/*  82 */       this.standWaist.rotateAngleX = 0.017453292F * entityarmorstand.getBodyRotation().getX();
/*  83 */       this.standWaist.rotateAngleY = 0.017453292F * entityarmorstand.getBodyRotation().getY();
/*  84 */       this.standWaist.rotateAngleZ = 0.017453292F * entityarmorstand.getBodyRotation().getZ();
/*  85 */       this.standBase.rotateAngleX = 0.0F;
/*  86 */       this.standBase.rotateAngleY = 0.017453292F * -entityIn.rotationYaw;
/*  87 */       this.standBase.rotateAngleZ = 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  96 */     super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*  97 */     GlStateManager.pushMatrix();
/*     */     
/*  99 */     if (this.isChild) {
/*     */       
/* 101 */       float f = 2.0F;
/* 102 */       GlStateManager.scale(0.5F, 0.5F, 0.5F);
/* 103 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/* 104 */       this.standRightSide.render(scale);
/* 105 */       this.standLeftSide.render(scale);
/* 106 */       this.standWaist.render(scale);
/* 107 */       this.standBase.render(scale);
/*     */     }
/*     */     else {
/*     */       
/* 111 */       if (entityIn.isSneaking())
/*     */       {
/* 113 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */       }
/*     */       
/* 116 */       this.standRightSide.render(scale);
/* 117 */       this.standLeftSide.render(scale);
/* 118 */       this.standWaist.render(scale);
/* 119 */       this.standBase.render(scale);
/*     */     } 
/*     */     
/* 122 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void postRenderArm(float scale, EnumHandSide side) {
/* 127 */     ModelRenderer modelrenderer = getArmForSide(side);
/* 128 */     boolean flag = modelrenderer.showModel;
/* 129 */     modelrenderer.showModel = true;
/* 130 */     super.postRenderArm(scale, side);
/* 131 */     modelrenderer.showModel = flag;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelArmorStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */