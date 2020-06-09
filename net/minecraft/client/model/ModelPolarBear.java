/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.monster.EntityPolarBear;
/*     */ 
/*     */ public class ModelPolarBear
/*     */   extends ModelQuadruped
/*     */ {
/*     */   public ModelPolarBear() {
/*  11 */     super(12, 0.0F);
/*  12 */     this.textureWidth = 128;
/*  13 */     this.textureHeight = 64;
/*  14 */     this.head = new ModelRenderer(this, 0, 0);
/*  15 */     this.head.addBox(-3.5F, -3.0F, -3.0F, 7, 7, 7, 0.0F);
/*  16 */     this.head.setRotationPoint(0.0F, 10.0F, -16.0F);
/*  17 */     this.head.setTextureOffset(0, 44).addBox(-2.5F, 1.0F, -6.0F, 5, 3, 3, 0.0F);
/*  18 */     this.head.setTextureOffset(26, 0).addBox(-4.5F, -4.0F, -1.0F, 2, 2, 1, 0.0F);
/*  19 */     ModelRenderer modelrenderer = this.head.setTextureOffset(26, 0);
/*  20 */     modelrenderer.mirror = true;
/*  21 */     modelrenderer.addBox(2.5F, -4.0F, -1.0F, 2, 2, 1, 0.0F);
/*  22 */     this.body = new ModelRenderer(this);
/*  23 */     this.body.setTextureOffset(0, 19).addBox(-5.0F, -13.0F, -7.0F, 14, 14, 11, 0.0F);
/*  24 */     this.body.setTextureOffset(39, 0).addBox(-4.0F, -25.0F, -7.0F, 12, 12, 10, 0.0F);
/*  25 */     this.body.setRotationPoint(-2.0F, 9.0F, 12.0F);
/*  26 */     int i = 10;
/*  27 */     this.leg1 = new ModelRenderer(this, 50, 22);
/*  28 */     this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 8, 0.0F);
/*  29 */     this.leg1.setRotationPoint(-3.5F, 14.0F, 6.0F);
/*  30 */     this.leg2 = new ModelRenderer(this, 50, 22);
/*  31 */     this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 8, 0.0F);
/*  32 */     this.leg2.setRotationPoint(3.5F, 14.0F, 6.0F);
/*  33 */     this.leg3 = new ModelRenderer(this, 50, 40);
/*  34 */     this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 6, 0.0F);
/*  35 */     this.leg3.setRotationPoint(-2.5F, 14.0F, -7.0F);
/*  36 */     this.leg4 = new ModelRenderer(this, 50, 40);
/*  37 */     this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 10, 6, 0.0F);
/*  38 */     this.leg4.setRotationPoint(2.5F, 14.0F, -7.0F);
/*  39 */     this.leg1.rotationPointX--;
/*  40 */     this.leg2.rotationPointX++;
/*  41 */     this.leg1.rotationPointZ += 0.0F;
/*  42 */     this.leg2.rotationPointZ += 0.0F;
/*  43 */     this.leg3.rotationPointX--;
/*  44 */     this.leg4.rotationPointX++;
/*  45 */     this.leg3.rotationPointZ--;
/*  46 */     this.leg4.rotationPointZ--;
/*  47 */     this.childZOffset += 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  55 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/*     */     
/*  57 */     if (this.isChild) {
/*     */       
/*  59 */       float f = 2.0F;
/*  60 */       this.childYOffset = 16.0F;
/*  61 */       this.childZOffset = 4.0F;
/*  62 */       GlStateManager.pushMatrix();
/*  63 */       GlStateManager.scale(0.6666667F, 0.6666667F, 0.6666667F);
/*  64 */       GlStateManager.translate(0.0F, this.childYOffset * scale, this.childZOffset * scale);
/*  65 */       this.head.render(scale);
/*  66 */       GlStateManager.popMatrix();
/*  67 */       GlStateManager.pushMatrix();
/*  68 */       GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*  69 */       GlStateManager.translate(0.0F, 24.0F * scale, 0.0F);
/*  70 */       this.body.render(scale);
/*  71 */       this.leg1.render(scale);
/*  72 */       this.leg2.render(scale);
/*  73 */       this.leg3.render(scale);
/*  74 */       this.leg4.render(scale);
/*  75 */       GlStateManager.popMatrix();
/*     */     }
/*     */     else {
/*     */       
/*  79 */       this.head.render(scale);
/*  80 */       this.body.render(scale);
/*  81 */       this.leg1.render(scale);
/*  82 */       this.leg2.render(scale);
/*  83 */       this.leg3.render(scale);
/*  84 */       this.leg4.render(scale);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/*  95 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/*  96 */     float f = ageInTicks - entityIn.ticksExisted;
/*  97 */     float f1 = ((EntityPolarBear)entityIn).getStandingAnimationScale(f);
/*  98 */     f1 *= f1;
/*  99 */     float f2 = 1.0F - f1;
/* 100 */     this.body.rotateAngleX = 1.5707964F - f1 * 3.1415927F * 0.35F;
/* 101 */     this.body.rotationPointY = 9.0F * f2 + 11.0F * f1;
/* 102 */     this.leg3.rotationPointY = 14.0F * f2 + -6.0F * f1;
/* 103 */     this.leg3.rotationPointZ = -8.0F * f2 + -4.0F * f1;
/* 104 */     this.leg3.rotateAngleX -= f1 * 3.1415927F * 0.45F;
/* 105 */     this.leg4.rotationPointY = this.leg3.rotationPointY;
/* 106 */     this.leg4.rotationPointZ = this.leg3.rotationPointZ;
/* 107 */     this.leg4.rotateAngleX -= f1 * 3.1415927F * 0.45F;
/* 108 */     this.head.rotationPointY = 10.0F * f2 + -12.0F * f1;
/* 109 */     this.head.rotationPointZ = -16.0F * f2 + -3.0F * f1;
/* 110 */     this.head.rotateAngleX += f1 * 3.1415927F * 0.15F;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelPolarBear.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */