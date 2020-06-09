/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.AbstractChestHorse;
/*     */ 
/*     */ public class ModelLlama
/*     */   extends ModelQuadruped
/*     */ {
/*     */   private final ModelRenderer field_191226_i;
/*     */   private final ModelRenderer field_191227_j;
/*     */   
/*     */   public ModelLlama(float p_i47226_1_) {
/*  14 */     super(15, p_i47226_1_);
/*  15 */     this.textureWidth = 128;
/*  16 */     this.textureHeight = 64;
/*  17 */     this.head = new ModelRenderer(this, 0, 0);
/*  18 */     this.head.addBox(-2.0F, -14.0F, -10.0F, 4, 4, 9, p_i47226_1_);
/*  19 */     this.head.setRotationPoint(0.0F, 7.0F, -6.0F);
/*  20 */     this.head.setTextureOffset(0, 14).addBox(-4.0F, -16.0F, -6.0F, 8, 18, 6, p_i47226_1_);
/*  21 */     this.head.setTextureOffset(17, 0).addBox(-4.0F, -19.0F, -4.0F, 3, 3, 2, p_i47226_1_);
/*  22 */     this.head.setTextureOffset(17, 0).addBox(1.0F, -19.0F, -4.0F, 3, 3, 2, p_i47226_1_);
/*  23 */     this.body = new ModelRenderer(this, 29, 0);
/*  24 */     this.body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, p_i47226_1_);
/*  25 */     this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
/*  26 */     this.field_191226_i = new ModelRenderer(this, 45, 28);
/*  27 */     this.field_191226_i.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, p_i47226_1_);
/*  28 */     this.field_191226_i.setRotationPoint(-8.5F, 3.0F, 3.0F);
/*  29 */     this.field_191226_i.rotateAngleY = 1.5707964F;
/*  30 */     this.field_191227_j = new ModelRenderer(this, 45, 41);
/*  31 */     this.field_191227_j.addBox(-3.0F, 0.0F, 0.0F, 8, 8, 3, p_i47226_1_);
/*  32 */     this.field_191227_j.setRotationPoint(5.5F, 3.0F, 3.0F);
/*  33 */     this.field_191227_j.rotateAngleY = 1.5707964F;
/*  34 */     int i = 4;
/*  35 */     int j = 14;
/*  36 */     this.leg1 = new ModelRenderer(this, 29, 29);
/*  37 */     this.leg1.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
/*  38 */     this.leg1.setRotationPoint(-2.5F, 10.0F, 6.0F);
/*  39 */     this.leg2 = new ModelRenderer(this, 29, 29);
/*  40 */     this.leg2.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
/*  41 */     this.leg2.setRotationPoint(2.5F, 10.0F, 6.0F);
/*  42 */     this.leg3 = new ModelRenderer(this, 29, 29);
/*  43 */     this.leg3.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
/*  44 */     this.leg3.setRotationPoint(-2.5F, 10.0F, -4.0F);
/*  45 */     this.leg4 = new ModelRenderer(this, 29, 29);
/*  46 */     this.leg4.addBox(-2.0F, 0.0F, -2.0F, 4, 14, 4, p_i47226_1_);
/*  47 */     this.leg4.setRotationPoint(2.5F, 10.0F, -4.0F);
/*  48 */     this.leg1.rotationPointX--;
/*  49 */     this.leg2.rotationPointX++;
/*  50 */     this.leg1.rotationPointZ += 0.0F;
/*  51 */     this.leg2.rotationPointZ += 0.0F;
/*  52 */     this.leg3.rotationPointX--;
/*  53 */     this.leg4.rotationPointX++;
/*  54 */     this.leg3.rotationPointZ--;
/*  55 */     this.leg4.rotationPointZ--;
/*  56 */     this.childZOffset += 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  64 */     AbstractChestHorse abstractchesthorse = (AbstractChestHorse)entityIn;
/*  65 */     boolean flag = (!abstractchesthorse.isChild() && abstractchesthorse.func_190695_dh());
/*  66 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/*     */     
/*  68 */     if (this.isChild) {
/*     */       
/*  70 */       float f = 2.0F;
/*  71 */       GlStateManager.pushMatrix();
/*  72 */       GlStateManager.translate(0.0F, this.childYOffset * scale, this.childZOffset * scale);
/*  73 */       GlStateManager.popMatrix();
/*  74 */       GlStateManager.pushMatrix();
/*  75 */       float f1 = 0.7F;
/*  76 */       GlStateManager.scale(0.71428573F, 0.64935064F, 0.7936508F);
/*  77 */       GlStateManager.translate(0.0F, 21.0F * scale, 0.22F);
/*  78 */       this.head.render(scale);
/*  79 */       GlStateManager.popMatrix();
/*  80 */       GlStateManager.pushMatrix();
/*  81 */       float f2 = 1.1F;
/*  82 */       GlStateManager.scale(0.625F, 0.45454544F, 0.45454544F);
/*  83 */       GlStateManager.translate(0.0F, 33.0F * scale, 0.0F);
/*  84 */       this.body.render(scale);
/*  85 */       GlStateManager.popMatrix();
/*  86 */       GlStateManager.pushMatrix();
/*  87 */       GlStateManager.scale(0.45454544F, 0.41322312F, 0.45454544F);
/*  88 */       GlStateManager.translate(0.0F, 33.0F * scale, 0.0F);
/*  89 */       this.leg1.render(scale);
/*  90 */       this.leg2.render(scale);
/*  91 */       this.leg3.render(scale);
/*  92 */       this.leg4.render(scale);
/*  93 */       GlStateManager.popMatrix();
/*     */     }
/*     */     else {
/*     */       
/*  97 */       this.head.render(scale);
/*  98 */       this.body.render(scale);
/*  99 */       this.leg1.render(scale);
/* 100 */       this.leg2.render(scale);
/* 101 */       this.leg3.render(scale);
/* 102 */       this.leg4.render(scale);
/*     */     } 
/*     */     
/* 105 */     if (flag) {
/*     */       
/* 107 */       this.field_191226_i.render(scale);
/* 108 */       this.field_191227_j.render(scale);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelLlama.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */