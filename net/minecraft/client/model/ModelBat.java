/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.EntityBat;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ModelBat
/*     */   extends ModelBase
/*     */ {
/*     */   private final ModelRenderer batHead;
/*     */   private final ModelRenderer batBody;
/*     */   private final ModelRenderer batRightWing;
/*     */   private final ModelRenderer batLeftWing;
/*     */   private final ModelRenderer batOuterRightWing;
/*     */   private final ModelRenderer batOuterLeftWing;
/*     */   
/*     */   public ModelBat() {
/*  28 */     this.textureWidth = 64;
/*  29 */     this.textureHeight = 64;
/*  30 */     this.batHead = new ModelRenderer(this, 0, 0);
/*  31 */     this.batHead.addBox(-3.0F, -3.0F, -3.0F, 6, 6, 6);
/*  32 */     ModelRenderer modelrenderer = new ModelRenderer(this, 24, 0);
/*  33 */     modelrenderer.addBox(-4.0F, -6.0F, -2.0F, 3, 4, 1);
/*  34 */     this.batHead.addChild(modelrenderer);
/*  35 */     ModelRenderer modelrenderer1 = new ModelRenderer(this, 24, 0);
/*  36 */     modelrenderer1.mirror = true;
/*  37 */     modelrenderer1.addBox(1.0F, -6.0F, -2.0F, 3, 4, 1);
/*  38 */     this.batHead.addChild(modelrenderer1);
/*  39 */     this.batBody = new ModelRenderer(this, 0, 16);
/*  40 */     this.batBody.addBox(-3.0F, 4.0F, -3.0F, 6, 12, 6);
/*  41 */     this.batBody.setTextureOffset(0, 34).addBox(-5.0F, 16.0F, 0.0F, 10, 6, 1);
/*  42 */     this.batRightWing = new ModelRenderer(this, 42, 0);
/*  43 */     this.batRightWing.addBox(-12.0F, 1.0F, 1.5F, 10, 16, 1);
/*  44 */     this.batOuterRightWing = new ModelRenderer(this, 24, 16);
/*  45 */     this.batOuterRightWing.setRotationPoint(-12.0F, 1.0F, 1.5F);
/*  46 */     this.batOuterRightWing.addBox(-8.0F, 1.0F, 0.0F, 8, 12, 1);
/*  47 */     this.batLeftWing = new ModelRenderer(this, 42, 0);
/*  48 */     this.batLeftWing.mirror = true;
/*  49 */     this.batLeftWing.addBox(2.0F, 1.0F, 1.5F, 10, 16, 1);
/*  50 */     this.batOuterLeftWing = new ModelRenderer(this, 24, 16);
/*  51 */     this.batOuterLeftWing.mirror = true;
/*  52 */     this.batOuterLeftWing.setRotationPoint(12.0F, 1.0F, 1.5F);
/*  53 */     this.batOuterLeftWing.addBox(0.0F, 1.0F, 0.0F, 8, 12, 1);
/*  54 */     this.batBody.addChild(this.batRightWing);
/*  55 */     this.batBody.addChild(this.batLeftWing);
/*  56 */     this.batRightWing.addChild(this.batOuterRightWing);
/*  57 */     this.batLeftWing.addChild(this.batOuterLeftWing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  65 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/*  66 */     this.batHead.render(scale);
/*  67 */     this.batBody.render(scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/*  77 */     if (((EntityBat)entityIn).getIsBatHanging()) {
/*     */       
/*  79 */       this.batHead.rotateAngleX = headPitch * 0.017453292F;
/*  80 */       this.batHead.rotateAngleY = 3.1415927F - netHeadYaw * 0.017453292F;
/*  81 */       this.batHead.rotateAngleZ = 3.1415927F;
/*  82 */       this.batHead.setRotationPoint(0.0F, -2.0F, 0.0F);
/*  83 */       this.batRightWing.setRotationPoint(-3.0F, 0.0F, 3.0F);
/*  84 */       this.batLeftWing.setRotationPoint(3.0F, 0.0F, 3.0F);
/*  85 */       this.batBody.rotateAngleX = 3.1415927F;
/*  86 */       this.batRightWing.rotateAngleX = -0.15707964F;
/*  87 */       this.batRightWing.rotateAngleY = -1.2566371F;
/*  88 */       this.batOuterRightWing.rotateAngleY = -1.7278761F;
/*  89 */       this.batLeftWing.rotateAngleX = this.batRightWing.rotateAngleX;
/*  90 */       this.batLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY;
/*  91 */       this.batOuterLeftWing.rotateAngleY = -this.batOuterRightWing.rotateAngleY;
/*     */     }
/*     */     else {
/*     */       
/*  95 */       this.batHead.rotateAngleX = headPitch * 0.017453292F;
/*  96 */       this.batHead.rotateAngleY = netHeadYaw * 0.017453292F;
/*  97 */       this.batHead.rotateAngleZ = 0.0F;
/*  98 */       this.batHead.setRotationPoint(0.0F, 0.0F, 0.0F);
/*  99 */       this.batRightWing.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 100 */       this.batLeftWing.setRotationPoint(0.0F, 0.0F, 0.0F);
/* 101 */       this.batBody.rotateAngleX = 0.7853982F + MathHelper.cos(ageInTicks * 0.1F) * 0.15F;
/* 102 */       this.batBody.rotateAngleY = 0.0F;
/* 103 */       this.batRightWing.rotateAngleY = MathHelper.cos(ageInTicks * 1.3F) * 3.1415927F * 0.25F;
/* 104 */       this.batLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY;
/* 105 */       this.batRightWing.rotateAngleY *= 0.5F;
/* 106 */       this.batOuterLeftWing.rotateAngleY = -this.batRightWing.rotateAngleY * 0.5F;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelBat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */