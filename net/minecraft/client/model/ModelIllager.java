/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.AbstractIllager;
/*     */ import net.minecraft.util.EnumHandSide;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class ModelIllager
/*     */   extends ModelBase
/*     */ {
/*     */   public ModelRenderer field_191217_a;
/*     */   public ModelRenderer field_193775_b;
/*     */   public ModelRenderer field_191218_b;
/*     */   public ModelRenderer field_191219_c;
/*     */   public ModelRenderer field_191220_d;
/*     */   public ModelRenderer field_191221_e;
/*     */   public ModelRenderer field_191222_f;
/*     */   public ModelRenderer field_191223_g;
/*     */   public ModelRenderer field_191224_h;
/*     */   
/*     */   public ModelIllager(float p_i47227_1_, float p_i47227_2_, int p_i47227_3_, int p_i47227_4_) {
/*  23 */     this.field_191217_a = (new ModelRenderer(this)).setTextureSize(p_i47227_3_, p_i47227_4_);
/*  24 */     this.field_191217_a.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
/*  25 */     this.field_191217_a.setTextureOffset(0, 0).addBox(-4.0F, -10.0F, -4.0F, 8, 10, 8, p_i47227_1_);
/*  26 */     this.field_193775_b = (new ModelRenderer(this, 32, 0)).setTextureSize(p_i47227_3_, p_i47227_4_);
/*  27 */     this.field_193775_b.addBox(-4.0F, -10.0F, -4.0F, 8, 12, 8, p_i47227_1_ + 0.45F);
/*  28 */     this.field_191217_a.addChild(this.field_193775_b);
/*  29 */     this.field_193775_b.showModel = false;
/*  30 */     this.field_191222_f = (new ModelRenderer(this)).setTextureSize(p_i47227_3_, p_i47227_4_);
/*  31 */     this.field_191222_f.setRotationPoint(0.0F, p_i47227_2_ - 2.0F, 0.0F);
/*  32 */     this.field_191222_f.setTextureOffset(24, 0).addBox(-1.0F, -1.0F, -6.0F, 2, 4, 2, p_i47227_1_);
/*  33 */     this.field_191217_a.addChild(this.field_191222_f);
/*  34 */     this.field_191218_b = (new ModelRenderer(this)).setTextureSize(p_i47227_3_, p_i47227_4_);
/*  35 */     this.field_191218_b.setRotationPoint(0.0F, 0.0F + p_i47227_2_, 0.0F);
/*  36 */     this.field_191218_b.setTextureOffset(16, 20).addBox(-4.0F, 0.0F, -3.0F, 8, 12, 6, p_i47227_1_);
/*  37 */     this.field_191218_b.setTextureOffset(0, 38).addBox(-4.0F, 0.0F, -3.0F, 8, 18, 6, p_i47227_1_ + 0.5F);
/*  38 */     this.field_191219_c = (new ModelRenderer(this)).setTextureSize(p_i47227_3_, p_i47227_4_);
/*  39 */     this.field_191219_c.setRotationPoint(0.0F, 0.0F + p_i47227_2_ + 2.0F, 0.0F);
/*  40 */     this.field_191219_c.setTextureOffset(44, 22).addBox(-8.0F, -2.0F, -2.0F, 4, 8, 4, p_i47227_1_);
/*  41 */     ModelRenderer modelrenderer = (new ModelRenderer(this, 44, 22)).setTextureSize(p_i47227_3_, p_i47227_4_);
/*  42 */     modelrenderer.mirror = true;
/*  43 */     modelrenderer.addBox(4.0F, -2.0F, -2.0F, 4, 8, 4, p_i47227_1_);
/*  44 */     this.field_191219_c.addChild(modelrenderer);
/*  45 */     this.field_191219_c.setTextureOffset(40, 38).addBox(-4.0F, 2.0F, -2.0F, 8, 4, 4, p_i47227_1_);
/*  46 */     this.field_191220_d = (new ModelRenderer(this, 0, 22)).setTextureSize(p_i47227_3_, p_i47227_4_);
/*  47 */     this.field_191220_d.setRotationPoint(-2.0F, 12.0F + p_i47227_2_, 0.0F);
/*  48 */     this.field_191220_d.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i47227_1_);
/*  49 */     this.field_191221_e = (new ModelRenderer(this, 0, 22)).setTextureSize(p_i47227_3_, p_i47227_4_);
/*  50 */     this.field_191221_e.mirror = true;
/*  51 */     this.field_191221_e.setRotationPoint(2.0F, 12.0F + p_i47227_2_, 0.0F);
/*  52 */     this.field_191221_e.addBox(-2.0F, 0.0F, -2.0F, 4, 12, 4, p_i47227_1_);
/*  53 */     this.field_191223_g = (new ModelRenderer(this, 40, 46)).setTextureSize(p_i47227_3_, p_i47227_4_);
/*  54 */     this.field_191223_g.addBox(-3.0F, -2.0F, -2.0F, 4, 12, 4, p_i47227_1_);
/*  55 */     this.field_191223_g.setRotationPoint(-5.0F, 2.0F + p_i47227_2_, 0.0F);
/*  56 */     this.field_191224_h = (new ModelRenderer(this, 40, 46)).setTextureSize(p_i47227_3_, p_i47227_4_);
/*  57 */     this.field_191224_h.mirror = true;
/*  58 */     this.field_191224_h.addBox(-1.0F, -2.0F, -2.0F, 4, 12, 4, p_i47227_1_);
/*  59 */     this.field_191224_h.setRotationPoint(5.0F, 2.0F + p_i47227_2_, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  67 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/*  68 */     this.field_191217_a.render(scale);
/*  69 */     this.field_191218_b.render(scale);
/*  70 */     this.field_191220_d.render(scale);
/*  71 */     this.field_191221_e.render(scale);
/*  72 */     AbstractIllager abstractillager = (AbstractIllager)entityIn;
/*     */     
/*  74 */     if (abstractillager.func_193077_p() == AbstractIllager.IllagerArmPose.CROSSED) {
/*     */       
/*  76 */       this.field_191219_c.render(scale);
/*     */     }
/*     */     else {
/*     */       
/*  80 */       this.field_191223_g.render(scale);
/*  81 */       this.field_191224_h.render(scale);
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
/*  92 */     this.field_191217_a.rotateAngleY = netHeadYaw * 0.017453292F;
/*  93 */     this.field_191217_a.rotateAngleX = headPitch * 0.017453292F;
/*  94 */     this.field_191219_c.rotationPointY = 3.0F;
/*  95 */     this.field_191219_c.rotationPointZ = -1.0F;
/*  96 */     this.field_191219_c.rotateAngleX = -0.75F;
/*  97 */     this.field_191220_d.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount * 0.5F;
/*  98 */     this.field_191221_e.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount * 0.5F;
/*  99 */     this.field_191220_d.rotateAngleY = 0.0F;
/* 100 */     this.field_191221_e.rotateAngleY = 0.0F;
/* 101 */     AbstractIllager.IllagerArmPose abstractillager$illagerarmpose = ((AbstractIllager)entityIn).func_193077_p();
/*     */     
/* 103 */     if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.ATTACKING) {
/*     */       
/* 105 */       float f = MathHelper.sin(this.swingProgress * 3.1415927F);
/* 106 */       float f1 = MathHelper.sin((1.0F - (1.0F - this.swingProgress) * (1.0F - this.swingProgress)) * 3.1415927F);
/* 107 */       this.field_191223_g.rotateAngleZ = 0.0F;
/* 108 */       this.field_191224_h.rotateAngleZ = 0.0F;
/* 109 */       this.field_191223_g.rotateAngleY = 0.15707964F;
/* 110 */       this.field_191224_h.rotateAngleY = -0.15707964F;
/*     */       
/* 112 */       if (((EntityLivingBase)entityIn).getPrimaryHand() == EnumHandSide.RIGHT) {
/*     */         
/* 114 */         this.field_191223_g.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
/* 115 */         this.field_191224_h.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.19F) * 0.5F;
/* 116 */         this.field_191223_g.rotateAngleX += f * 2.2F - f1 * 0.4F;
/* 117 */         this.field_191224_h.rotateAngleX += f * 1.2F - f1 * 0.4F;
/*     */       }
/*     */       else {
/*     */         
/* 121 */         this.field_191223_g.rotateAngleX = -0.0F + MathHelper.cos(ageInTicks * 0.19F) * 0.5F;
/* 122 */         this.field_191224_h.rotateAngleX = -1.8849558F + MathHelper.cos(ageInTicks * 0.09F) * 0.15F;
/* 123 */         this.field_191223_g.rotateAngleX += f * 1.2F - f1 * 0.4F;
/* 124 */         this.field_191224_h.rotateAngleX += f * 2.2F - f1 * 0.4F;
/*     */       } 
/*     */       
/* 127 */       this.field_191223_g.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 128 */       this.field_191224_h.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
/* 129 */       this.field_191223_g.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/* 130 */       this.field_191224_h.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
/*     */     }
/* 132 */     else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.SPELLCASTING) {
/*     */       
/* 134 */       this.field_191223_g.rotationPointZ = 0.0F;
/* 135 */       this.field_191223_g.rotationPointX = -5.0F;
/* 136 */       this.field_191224_h.rotationPointZ = 0.0F;
/* 137 */       this.field_191224_h.rotationPointX = 5.0F;
/* 138 */       this.field_191223_g.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
/* 139 */       this.field_191224_h.rotateAngleX = MathHelper.cos(ageInTicks * 0.6662F) * 0.25F;
/* 140 */       this.field_191223_g.rotateAngleZ = 2.3561945F;
/* 141 */       this.field_191224_h.rotateAngleZ = -2.3561945F;
/* 142 */       this.field_191223_g.rotateAngleY = 0.0F;
/* 143 */       this.field_191224_h.rotateAngleY = 0.0F;
/*     */     }
/* 145 */     else if (abstractillager$illagerarmpose == AbstractIllager.IllagerArmPose.BOW_AND_ARROW) {
/*     */       
/* 147 */       this.field_191223_g.rotateAngleY = -0.1F + this.field_191217_a.rotateAngleY;
/* 148 */       this.field_191223_g.rotateAngleX = -1.5707964F + this.field_191217_a.rotateAngleX;
/* 149 */       this.field_191224_h.rotateAngleX = -0.9424779F + this.field_191217_a.rotateAngleX;
/* 150 */       this.field_191217_a.rotateAngleY -= 0.4F;
/* 151 */       this.field_191224_h.rotateAngleZ = 1.5707964F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelRenderer func_191216_a(EnumHandSide p_191216_1_) {
/* 157 */     return (p_191216_1_ == EnumHandSide.LEFT) ? this.field_191224_h : this.field_191223_g;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelIllager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */