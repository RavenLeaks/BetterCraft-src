/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityParrot;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class ModelParrot
/*     */   extends ModelBase {
/*     */   ModelRenderer field_192764_a;
/*     */   ModelRenderer field_192765_b;
/*     */   ModelRenderer field_192766_c;
/*     */   ModelRenderer field_192767_d;
/*     */   ModelRenderer field_192768_e;
/*     */   ModelRenderer field_192769_f;
/*     */   ModelRenderer field_192770_g;
/*     */   ModelRenderer field_192771_h;
/*     */   ModelRenderer field_192772_i;
/*     */   ModelRenderer field_192773_j;
/*     */   ModelRenderer field_192774_k;
/*  21 */   private State field_192775_l = State.STANDING;
/*     */ 
/*     */   
/*     */   public ModelParrot() {
/*  25 */     this.textureWidth = 32;
/*  26 */     this.textureHeight = 32;
/*  27 */     this.field_192764_a = new ModelRenderer(this, 2, 8);
/*  28 */     this.field_192764_a.addBox(-1.5F, 0.0F, -1.5F, 3, 6, 3);
/*  29 */     this.field_192764_a.setRotationPoint(0.0F, 16.5F, -3.0F);
/*  30 */     this.field_192765_b = new ModelRenderer(this, 22, 1);
/*  31 */     this.field_192765_b.addBox(-1.5F, -1.0F, -1.0F, 3, 4, 1);
/*  32 */     this.field_192765_b.setRotationPoint(0.0F, 21.07F, 1.16F);
/*  33 */     this.field_192766_c = new ModelRenderer(this, 19, 8);
/*  34 */     this.field_192766_c.addBox(-0.5F, 0.0F, -1.5F, 1, 5, 3);
/*  35 */     this.field_192766_c.setRotationPoint(1.5F, 16.94F, -2.76F);
/*  36 */     this.field_192767_d = new ModelRenderer(this, 19, 8);
/*  37 */     this.field_192767_d.addBox(-0.5F, 0.0F, -1.5F, 1, 5, 3);
/*  38 */     this.field_192767_d.setRotationPoint(-1.5F, 16.94F, -2.76F);
/*  39 */     this.field_192768_e = new ModelRenderer(this, 2, 2);
/*  40 */     this.field_192768_e.addBox(-1.0F, -1.5F, -1.0F, 2, 3, 2);
/*  41 */     this.field_192768_e.setRotationPoint(0.0F, 15.69F, -2.76F);
/*  42 */     this.field_192769_f = new ModelRenderer(this, 10, 0);
/*  43 */     this.field_192769_f.addBox(-1.0F, -0.5F, -2.0F, 2, 1, 4);
/*  44 */     this.field_192769_f.setRotationPoint(0.0F, -2.0F, -1.0F);
/*  45 */     this.field_192768_e.addChild(this.field_192769_f);
/*  46 */     this.field_192770_g = new ModelRenderer(this, 11, 7);
/*  47 */     this.field_192770_g.addBox(-0.5F, -1.0F, -0.5F, 1, 2, 1);
/*  48 */     this.field_192770_g.setRotationPoint(0.0F, -0.5F, -1.5F);
/*  49 */     this.field_192768_e.addChild(this.field_192770_g);
/*  50 */     this.field_192771_h = new ModelRenderer(this, 16, 7);
/*  51 */     this.field_192771_h.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1);
/*  52 */     this.field_192771_h.setRotationPoint(0.0F, -1.75F, -2.45F);
/*  53 */     this.field_192768_e.addChild(this.field_192771_h);
/*  54 */     this.field_192772_i = new ModelRenderer(this, 2, 18);
/*  55 */     this.field_192772_i.addBox(0.0F, -4.0F, -2.0F, 0, 5, 4);
/*  56 */     this.field_192772_i.setRotationPoint(0.0F, -2.15F, 0.15F);
/*  57 */     this.field_192768_e.addChild(this.field_192772_i);
/*  58 */     this.field_192773_j = new ModelRenderer(this, 14, 18);
/*  59 */     this.field_192773_j.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1);
/*  60 */     this.field_192773_j.setRotationPoint(1.0F, 22.0F, -1.05F);
/*  61 */     this.field_192774_k = new ModelRenderer(this, 14, 18);
/*  62 */     this.field_192774_k.addBox(-0.5F, 0.0F, -0.5F, 1, 2, 1);
/*  63 */     this.field_192774_k.setRotationPoint(-1.0F, 22.0F, -1.05F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  71 */     this.field_192764_a.render(scale);
/*  72 */     this.field_192766_c.render(scale);
/*  73 */     this.field_192767_d.render(scale);
/*  74 */     this.field_192765_b.render(scale);
/*  75 */     this.field_192768_e.render(scale);
/*  76 */     this.field_192773_j.render(scale);
/*  77 */     this.field_192774_k.render(scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/*  87 */     float f = ageInTicks * 0.3F;
/*  88 */     this.field_192768_e.rotateAngleX = headPitch * 0.017453292F;
/*  89 */     this.field_192768_e.rotateAngleY = netHeadYaw * 0.017453292F;
/*  90 */     this.field_192768_e.rotateAngleZ = 0.0F;
/*  91 */     this.field_192768_e.rotationPointX = 0.0F;
/*  92 */     this.field_192764_a.rotationPointX = 0.0F;
/*  93 */     this.field_192765_b.rotationPointX = 0.0F;
/*  94 */     this.field_192767_d.rotationPointX = -1.5F;
/*  95 */     this.field_192766_c.rotationPointX = 1.5F;
/*     */     
/*  97 */     if (this.field_192775_l != State.FLYING) {
/*     */       
/*  99 */       if (this.field_192775_l == State.SITTING) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 104 */       if (this.field_192775_l == State.PARTY) {
/*     */         
/* 106 */         float f1 = MathHelper.cos(entityIn.ticksExisted);
/* 107 */         float f2 = MathHelper.sin(entityIn.ticksExisted);
/* 108 */         this.field_192768_e.rotationPointX = f1;
/* 109 */         this.field_192768_e.rotationPointY = 15.69F + f2;
/* 110 */         this.field_192768_e.rotateAngleX = 0.0F;
/* 111 */         this.field_192768_e.rotateAngleY = 0.0F;
/* 112 */         this.field_192768_e.rotateAngleZ = MathHelper.sin(entityIn.ticksExisted) * 0.4F;
/* 113 */         this.field_192764_a.rotationPointX = f1;
/* 114 */         this.field_192764_a.rotationPointY = 16.5F + f2;
/* 115 */         this.field_192766_c.rotateAngleZ = -0.0873F - ageInTicks;
/* 116 */         this.field_192766_c.rotationPointX = 1.5F + f1;
/* 117 */         this.field_192766_c.rotationPointY = 16.94F + f2;
/* 118 */         this.field_192767_d.rotateAngleZ = 0.0873F + ageInTicks;
/* 119 */         this.field_192767_d.rotationPointX = -1.5F + f1;
/* 120 */         this.field_192767_d.rotationPointY = 16.94F + f2;
/* 121 */         this.field_192765_b.rotationPointX = f1;
/* 122 */         this.field_192765_b.rotationPointY = 21.07F + f2;
/*     */         
/*     */         return;
/*     */       } 
/* 126 */       this.field_192773_j.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F) * 1.4F * limbSwingAmount;
/* 127 */       this.field_192774_k.rotateAngleX += MathHelper.cos(limbSwing * 0.6662F + 3.1415927F) * 1.4F * limbSwingAmount;
/*     */     } 
/*     */     
/* 130 */     this.field_192768_e.rotationPointY = 15.69F + f;
/* 131 */     this.field_192765_b.rotateAngleX = 1.015F + MathHelper.cos(limbSwing * 0.6662F) * 0.3F * limbSwingAmount;
/* 132 */     this.field_192765_b.rotationPointY = 21.07F + f;
/* 133 */     this.field_192764_a.rotationPointY = 16.5F + f;
/* 134 */     this.field_192766_c.rotateAngleZ = -0.0873F - ageInTicks;
/* 135 */     this.field_192766_c.rotationPointY = 16.94F + f;
/* 136 */     this.field_192767_d.rotateAngleZ = 0.0873F + ageInTicks;
/* 137 */     this.field_192767_d.rotationPointY = 16.94F + f;
/* 138 */     this.field_192773_j.rotationPointY = 22.0F + f;
/* 139 */     this.field_192774_k.rotationPointY = 22.0F + f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/* 148 */     this.field_192772_i.rotateAngleX = -0.2214F;
/* 149 */     this.field_192764_a.rotateAngleX = 0.4937F;
/* 150 */     this.field_192766_c.rotateAngleX = -0.69813174F;
/* 151 */     this.field_192766_c.rotateAngleY = -3.1415927F;
/* 152 */     this.field_192767_d.rotateAngleX = -0.69813174F;
/* 153 */     this.field_192767_d.rotateAngleY = -3.1415927F;
/* 154 */     this.field_192773_j.rotateAngleX = -0.0299F;
/* 155 */     this.field_192774_k.rotateAngleX = -0.0299F;
/* 156 */     this.field_192773_j.rotationPointY = 22.0F;
/* 157 */     this.field_192774_k.rotationPointY = 22.0F;
/*     */     
/* 159 */     if (entitylivingbaseIn instanceof EntityParrot) {
/*     */       
/* 161 */       EntityParrot entityparrot = (EntityParrot)entitylivingbaseIn;
/*     */       
/* 163 */       if (entityparrot.func_192004_dr()) {
/*     */         
/* 165 */         this.field_192773_j.rotateAngleZ = -0.34906584F;
/* 166 */         this.field_192774_k.rotateAngleZ = 0.34906584F;
/* 167 */         this.field_192775_l = State.PARTY;
/*     */         
/*     */         return;
/*     */       } 
/* 171 */       if (entityparrot.isSitting()) {
/*     */         
/* 173 */         float f = 1.9F;
/* 174 */         this.field_192768_e.rotationPointY = 17.59F;
/* 175 */         this.field_192765_b.rotateAngleX = 1.5388988F;
/* 176 */         this.field_192765_b.rotationPointY = 22.97F;
/* 177 */         this.field_192764_a.rotationPointY = 18.4F;
/* 178 */         this.field_192766_c.rotateAngleZ = -0.0873F;
/* 179 */         this.field_192766_c.rotationPointY = 18.84F;
/* 180 */         this.field_192767_d.rotateAngleZ = 0.0873F;
/* 181 */         this.field_192767_d.rotationPointY = 18.84F;
/* 182 */         this.field_192773_j.rotationPointY++;
/* 183 */         this.field_192774_k.rotationPointY++;
/* 184 */         this.field_192773_j.rotateAngleX++;
/* 185 */         this.field_192774_k.rotateAngleX++;
/* 186 */         this.field_192775_l = State.SITTING;
/*     */       }
/* 188 */       else if (entityparrot.func_192002_a()) {
/*     */         
/* 190 */         this.field_192773_j.rotateAngleX += 0.69813174F;
/* 191 */         this.field_192774_k.rotateAngleX += 0.69813174F;
/* 192 */         this.field_192775_l = State.FLYING;
/*     */       }
/*     */       else {
/*     */         
/* 196 */         this.field_192775_l = State.STANDING;
/*     */       } 
/*     */       
/* 199 */       this.field_192773_j.rotateAngleZ = 0.0F;
/* 200 */       this.field_192774_k.rotateAngleZ = 0.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   enum State
/*     */   {
/* 206 */     FLYING,
/* 207 */     STANDING,
/* 208 */     SITTING,
/* 209 */     PARTY;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelParrot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */