/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ 
/*     */ public class ModelPlayer
/*     */   extends ModelBiped
/*     */ {
/*     */   public ModelRenderer bipedLeftArmwear;
/*     */   public ModelRenderer bipedRightArmwear;
/*     */   public ModelRenderer bipedLeftLegwear;
/*     */   public ModelRenderer bipedRightLegwear;
/*     */   public ModelRenderer bipedBodyWear;
/*     */   private final ModelRenderer bipedCape;
/*     */   private final ModelRenderer bipedDeadmau5Head;
/*     */   private final boolean smallArms;
/*     */   
/*     */   public ModelPlayer(float modelSize, boolean smallArmsIn) {
/*  20 */     super(modelSize, 0.0F, 64, 64);
/*  21 */     this.smallArms = smallArmsIn;
/*  22 */     this.bipedDeadmau5Head = new ModelRenderer(this, 24, 0);
/*  23 */     this.bipedDeadmau5Head.addBox(-3.0F, -6.0F, -1.0F, 6, 6, 1, modelSize);
/*  24 */     this.bipedCape = new ModelRenderer(this, 0, 0);
/*  25 */     this.bipedCape.setTextureSize(64, 32);
/*  26 */     this.bipedCape.addBox(-5.0F, 0.0F, -1.0F, 10, 16, 1, modelSize);
/*     */     
/*  28 */     if (smallArmsIn) {
/*     */       
/*  30 */       this.bipedLeftArm = new ModelRenderer(this, 32, 48);
/*  31 */       this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
/*  32 */       this.bipedLeftArm.setRotationPoint(5.0F, 2.5F, 0.0F);
/*  33 */       this.bipedRightArm = new ModelRenderer(this, 40, 16);
/*  34 */       this.bipedRightArm.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize);
/*  35 */       this.bipedRightArm.setRotationPoint(-5.0F, 2.5F, 0.0F);
/*  36 */       this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
/*  37 */       this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
/*  38 */       this.bipedLeftArmwear.setRotationPoint(5.0F, 2.5F, 0.0F);
/*  39 */       this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
/*  40 */       this.bipedRightArmwear.addBox(-2.0F, -2.0F, -2.0F, 3, 12, 4, modelSize + 0.25F);
/*  41 */       this.bipedRightArmwear.setRotationPoint(-5.0F, 2.5F, 10.0F);
/*     */     }
/*     */     else {
/*     */       
/*  45 */       this.bipedLeftArm = new ModelRenderer(this, 32, 48);
/*  46 */       this.bipedLeftArm.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize);
/*  47 */       this.bipedLeftArm.setRotationPoint(5.0F, 2.0F, 0.0F);
/*  48 */       this.bipedLeftArmwear = new ModelRenderer(this, 48, 48);
/*  49 */       this.bipedLeftArmwear.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
/*  50 */       this.bipedLeftArmwear.setRotationPoint(5.0F, 2.0F, 0.0F);
/*  51 */       this.bipedRightArmwear = new ModelRenderer(this, 40, 32);
/*  52 */       this.bipedRightArmwear.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
/*  53 */       this.bipedRightArmwear.setRotationPoint(-5.0F, 2.0F, 10.0F);
/*     */     } 
/*     */     
/*  56 */     this.bipedLeftLeg = new ModelRenderer(this, 16, 48);
/*  57 */     this.bipedLeftLeg.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize);
/*  58 */     this.bipedLeftLeg.setRotationPoint(1.9F, 12.0F, 0.0F);
/*  59 */     this.bipedLeftLegwear = new ModelRenderer(this, 0, 48);
/*  60 */     this.bipedLeftLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
/*  61 */     this.bipedLeftLegwear.setRotationPoint(1.9F, 12.0F, 0.0F);
/*  62 */     this.bipedRightLegwear = new ModelRenderer(this, 0, 32);
/*  63 */     this.bipedRightLegwear.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, modelSize + 0.25F);
/*  64 */     this.bipedRightLegwear.setRotationPoint(-1.9F, 12.0F, 0.0F);
/*  65 */     this.bipedBodyWear = new ModelRenderer(this, 16, 32);
/*  66 */     this.bipedBodyWear.addBox(-4.0F, 0.0F, -2.0F, 8, 12, 4, modelSize + 0.25F);
/*  67 */     this.bipedBodyWear.setRotationPoint(0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  75 */     super.render(entityIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*  76 */     GlStateManager.pushMatrix();
/*     */     
/*  78 */     if (this.isChild) {
/*     */       
/*  80 */       float f = 2.0F;
/*  81 */       GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*  82 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/*  83 */       this.bipedLeftLegwear.render(scale);
/*  84 */       this.bipedRightLegwear.render(scale);
/*  85 */       this.bipedLeftArmwear.render(scale);
/*  86 */       this.bipedRightArmwear.render(scale);
/*  87 */       this.bipedBodyWear.render(scale);
/*     */     }
/*     */     else {
/*     */       
/*  91 */       if (entityIn.isSneaking())
/*     */       {
/*  93 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*     */       }
/*     */       
/*  96 */       this.bipedLeftLegwear.render(scale);
/*  97 */       this.bipedRightLegwear.render(scale);
/*  98 */       this.bipedLeftArmwear.render(scale);
/*  99 */       this.bipedRightArmwear.render(scale);
/* 100 */       this.bipedBodyWear.render(scale);
/*     */     } 
/*     */     
/* 103 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderDeadmau5Head(float scale) {
/* 108 */     copyModelAngles(this.bipedHead, this.bipedDeadmau5Head);
/* 109 */     this.bipedDeadmau5Head.rotationPointX = 0.0F;
/* 110 */     this.bipedDeadmau5Head.rotationPointY = 0.0F;
/* 111 */     this.bipedDeadmau5Head.render(scale);
/*     */   }
/*     */ 
/*     */   
/*     */   public void renderCape(float scale) {
/* 116 */     this.bipedCape.render(scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/* 126 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/* 127 */     copyModelAngles(this.bipedLeftLeg, this.bipedLeftLegwear);
/* 128 */     copyModelAngles(this.bipedRightLeg, this.bipedRightLegwear);
/* 129 */     copyModelAngles(this.bipedLeftArm, this.bipedLeftArmwear);
/* 130 */     copyModelAngles(this.bipedRightArm, this.bipedRightArmwear);
/* 131 */     copyModelAngles(this.bipedBody, this.bipedBodyWear);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInvisible(boolean invisible) {
/* 136 */     super.setInvisible(invisible);
/* 137 */     this.bipedLeftArmwear.showModel = invisible;
/* 138 */     this.bipedRightArmwear.showModel = invisible;
/* 139 */     this.bipedLeftLegwear.showModel = invisible;
/* 140 */     this.bipedRightLegwear.showModel = invisible;
/* 141 */     this.bipedBodyWear.showModel = invisible;
/* 142 */     this.bipedCape.showModel = invisible;
/* 143 */     this.bipedDeadmau5Head.showModel = invisible;
/*     */   }
/*     */ 
/*     */   
/*     */   public void postRenderArm(float scale, EnumHandSide side) {
/* 148 */     ModelRenderer modelrenderer = getArmForSide(side);
/*     */     
/* 150 */     if (this.smallArms) {
/*     */       
/* 152 */       float f = 0.5F * ((side == EnumHandSide.RIGHT) ? true : -1);
/* 153 */       modelrenderer.rotationPointX += f;
/* 154 */       modelrenderer.postRender(scale);
/* 155 */       modelrenderer.rotationPointX -= f;
/*     */     }
/*     */     else {
/*     */       
/* 159 */       modelrenderer.postRender(scale);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */