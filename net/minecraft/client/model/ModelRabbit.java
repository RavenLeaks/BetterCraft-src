/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
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
/*     */ 
/*     */ 
/*     */ public class ModelRabbit
/*     */   extends ModelBase
/*     */ {
/*     */   private final ModelRenderer rabbitLeftFoot;
/*     */   private final ModelRenderer rabbitRightFoot;
/*     */   private final ModelRenderer rabbitLeftThigh;
/*     */   private final ModelRenderer rabbitRightThigh;
/*     */   private final ModelRenderer rabbitBody;
/*     */   private final ModelRenderer rabbitLeftArm;
/*     */   private final ModelRenderer rabbitRightArm;
/*     */   private final ModelRenderer rabbitHead;
/*     */   private final ModelRenderer rabbitRightEar;
/*     */   private final ModelRenderer rabbitLeftEar;
/*     */   private final ModelRenderer rabbitTail;
/*     */   private final ModelRenderer rabbitNose;
/*     */   private float jumpRotation;
/*     */   
/*     */   public ModelRabbit() {
/*  50 */     setTextureOffset("head.main", 0, 0);
/*  51 */     setTextureOffset("head.nose", 0, 24);
/*  52 */     setTextureOffset("head.ear1", 0, 10);
/*  53 */     setTextureOffset("head.ear2", 6, 10);
/*  54 */     this.rabbitLeftFoot = new ModelRenderer(this, 26, 24);
/*  55 */     this.rabbitLeftFoot.addBox(-1.0F, 5.5F, -3.7F, 2, 1, 7);
/*  56 */     this.rabbitLeftFoot.setRotationPoint(3.0F, 17.5F, 3.7F);
/*  57 */     this.rabbitLeftFoot.mirror = true;
/*  58 */     setRotationOffset(this.rabbitLeftFoot, 0.0F, 0.0F, 0.0F);
/*  59 */     this.rabbitRightFoot = new ModelRenderer(this, 8, 24);
/*  60 */     this.rabbitRightFoot.addBox(-1.0F, 5.5F, -3.7F, 2, 1, 7);
/*  61 */     this.rabbitRightFoot.setRotationPoint(-3.0F, 17.5F, 3.7F);
/*  62 */     this.rabbitRightFoot.mirror = true;
/*  63 */     setRotationOffset(this.rabbitRightFoot, 0.0F, 0.0F, 0.0F);
/*  64 */     this.rabbitLeftThigh = new ModelRenderer(this, 30, 15);
/*  65 */     this.rabbitLeftThigh.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 5);
/*  66 */     this.rabbitLeftThigh.setRotationPoint(3.0F, 17.5F, 3.7F);
/*  67 */     this.rabbitLeftThigh.mirror = true;
/*  68 */     setRotationOffset(this.rabbitLeftThigh, -0.34906584F, 0.0F, 0.0F);
/*  69 */     this.rabbitRightThigh = new ModelRenderer(this, 16, 15);
/*  70 */     this.rabbitRightThigh.addBox(-1.0F, 0.0F, 0.0F, 2, 4, 5);
/*  71 */     this.rabbitRightThigh.setRotationPoint(-3.0F, 17.5F, 3.7F);
/*  72 */     this.rabbitRightThigh.mirror = true;
/*  73 */     setRotationOffset(this.rabbitRightThigh, -0.34906584F, 0.0F, 0.0F);
/*  74 */     this.rabbitBody = new ModelRenderer(this, 0, 0);
/*  75 */     this.rabbitBody.addBox(-3.0F, -2.0F, -10.0F, 6, 5, 10);
/*  76 */     this.rabbitBody.setRotationPoint(0.0F, 19.0F, 8.0F);
/*  77 */     this.rabbitBody.mirror = true;
/*  78 */     setRotationOffset(this.rabbitBody, -0.34906584F, 0.0F, 0.0F);
/*  79 */     this.rabbitLeftArm = new ModelRenderer(this, 8, 15);
/*  80 */     this.rabbitLeftArm.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2);
/*  81 */     this.rabbitLeftArm.setRotationPoint(3.0F, 17.0F, -1.0F);
/*  82 */     this.rabbitLeftArm.mirror = true;
/*  83 */     setRotationOffset(this.rabbitLeftArm, -0.17453292F, 0.0F, 0.0F);
/*  84 */     this.rabbitRightArm = new ModelRenderer(this, 0, 15);
/*  85 */     this.rabbitRightArm.addBox(-1.0F, 0.0F, -1.0F, 2, 7, 2);
/*  86 */     this.rabbitRightArm.setRotationPoint(-3.0F, 17.0F, -1.0F);
/*  87 */     this.rabbitRightArm.mirror = true;
/*  88 */     setRotationOffset(this.rabbitRightArm, -0.17453292F, 0.0F, 0.0F);
/*  89 */     this.rabbitHead = new ModelRenderer(this, 32, 0);
/*  90 */     this.rabbitHead.addBox(-2.5F, -4.0F, -5.0F, 5, 4, 5);
/*  91 */     this.rabbitHead.setRotationPoint(0.0F, 16.0F, -1.0F);
/*  92 */     this.rabbitHead.mirror = true;
/*  93 */     setRotationOffset(this.rabbitHead, 0.0F, 0.0F, 0.0F);
/*  94 */     this.rabbitRightEar = new ModelRenderer(this, 52, 0);
/*  95 */     this.rabbitRightEar.addBox(-2.5F, -9.0F, -1.0F, 2, 5, 1);
/*  96 */     this.rabbitRightEar.setRotationPoint(0.0F, 16.0F, -1.0F);
/*  97 */     this.rabbitRightEar.mirror = true;
/*  98 */     setRotationOffset(this.rabbitRightEar, 0.0F, -0.2617994F, 0.0F);
/*  99 */     this.rabbitLeftEar = new ModelRenderer(this, 58, 0);
/* 100 */     this.rabbitLeftEar.addBox(0.5F, -9.0F, -1.0F, 2, 5, 1);
/* 101 */     this.rabbitLeftEar.setRotationPoint(0.0F, 16.0F, -1.0F);
/* 102 */     this.rabbitLeftEar.mirror = true;
/* 103 */     setRotationOffset(this.rabbitLeftEar, 0.0F, 0.2617994F, 0.0F);
/* 104 */     this.rabbitTail = new ModelRenderer(this, 52, 6);
/* 105 */     this.rabbitTail.addBox(-1.5F, -1.5F, 0.0F, 3, 3, 2);
/* 106 */     this.rabbitTail.setRotationPoint(0.0F, 20.0F, 7.0F);
/* 107 */     this.rabbitTail.mirror = true;
/* 108 */     setRotationOffset(this.rabbitTail, -0.3490659F, 0.0F, 0.0F);
/* 109 */     this.rabbitNose = new ModelRenderer(this, 32, 9);
/* 110 */     this.rabbitNose.addBox(-0.5F, -2.5F, -5.5F, 1, 1, 1);
/* 111 */     this.rabbitNose.setRotationPoint(0.0F, 16.0F, -1.0F);
/* 112 */     this.rabbitNose.mirror = true;
/* 113 */     setRotationOffset(this.rabbitNose, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private void setRotationOffset(ModelRenderer renderer, float x, float y, float z) {
/* 118 */     renderer.rotateAngleX = x;
/* 119 */     renderer.rotateAngleY = y;
/* 120 */     renderer.rotateAngleZ = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 128 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/*     */     
/* 130 */     if (this.isChild) {
/*     */       
/* 132 */       float f = 1.5F;
/* 133 */       GlStateManager.pushMatrix();
/* 134 */       GlStateManager.scale(0.56666666F, 0.56666666F, 0.56666666F);
/* 135 */       GlStateManager.translate(0.0F, 22.0F * scale, 2.0F * scale);
/* 136 */       this.rabbitHead.render(scale);
/* 137 */       this.rabbitLeftEar.render(scale);
/* 138 */       this.rabbitRightEar.render(scale);
/* 139 */       this.rabbitNose.render(scale);
/* 140 */       GlStateManager.popMatrix();
/* 141 */       GlStateManager.pushMatrix();
/* 142 */       GlStateManager.scale(0.4F, 0.4F, 0.4F);
/* 143 */       GlStateManager.translate(0.0F, 36.0F * scale, 0.0F);
/* 144 */       this.rabbitLeftFoot.render(scale);
/* 145 */       this.rabbitRightFoot.render(scale);
/* 146 */       this.rabbitLeftThigh.render(scale);
/* 147 */       this.rabbitRightThigh.render(scale);
/* 148 */       this.rabbitBody.render(scale);
/* 149 */       this.rabbitLeftArm.render(scale);
/* 150 */       this.rabbitRightArm.render(scale);
/* 151 */       this.rabbitTail.render(scale);
/* 152 */       GlStateManager.popMatrix();
/*     */     }
/*     */     else {
/*     */       
/* 156 */       GlStateManager.pushMatrix();
/* 157 */       GlStateManager.scale(0.6F, 0.6F, 0.6F);
/* 158 */       GlStateManager.translate(0.0F, 16.0F * scale, 0.0F);
/* 159 */       this.rabbitLeftFoot.render(scale);
/* 160 */       this.rabbitRightFoot.render(scale);
/* 161 */       this.rabbitLeftThigh.render(scale);
/* 162 */       this.rabbitRightThigh.render(scale);
/* 163 */       this.rabbitBody.render(scale);
/* 164 */       this.rabbitLeftArm.render(scale);
/* 165 */       this.rabbitRightArm.render(scale);
/* 166 */       this.rabbitHead.render(scale);
/* 167 */       this.rabbitRightEar.render(scale);
/* 168 */       this.rabbitLeftEar.render(scale);
/* 169 */       this.rabbitTail.render(scale);
/* 170 */       this.rabbitNose.render(scale);
/* 171 */       GlStateManager.popMatrix();
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
/* 182 */     float f = ageInTicks - entityIn.ticksExisted;
/* 183 */     EntityRabbit entityrabbit = (EntityRabbit)entityIn;
/* 184 */     this.rabbitNose.rotateAngleX = headPitch * 0.017453292F;
/* 185 */     this.rabbitHead.rotateAngleX = headPitch * 0.017453292F;
/* 186 */     this.rabbitRightEar.rotateAngleX = headPitch * 0.017453292F;
/* 187 */     this.rabbitLeftEar.rotateAngleX = headPitch * 0.017453292F;
/* 188 */     this.rabbitNose.rotateAngleY = netHeadYaw * 0.017453292F;
/* 189 */     this.rabbitHead.rotateAngleY = netHeadYaw * 0.017453292F;
/* 190 */     this.rabbitNose.rotateAngleY -= 0.2617994F;
/* 191 */     this.rabbitNose.rotateAngleY += 0.2617994F;
/* 192 */     this.jumpRotation = MathHelper.sin(entityrabbit.setJumpCompletion(f) * 3.1415927F);
/* 193 */     this.rabbitLeftThigh.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * 0.017453292F;
/* 194 */     this.rabbitRightThigh.rotateAngleX = (this.jumpRotation * 50.0F - 21.0F) * 0.017453292F;
/* 195 */     this.rabbitLeftFoot.rotateAngleX = this.jumpRotation * 50.0F * 0.017453292F;
/* 196 */     this.rabbitRightFoot.rotateAngleX = this.jumpRotation * 50.0F * 0.017453292F;
/* 197 */     this.rabbitLeftArm.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * 0.017453292F;
/* 198 */     this.rabbitRightArm.rotateAngleX = (this.jumpRotation * -40.0F - 11.0F) * 0.017453292F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLivingAnimations(EntityLivingBase entitylivingbaseIn, float p_78086_2_, float p_78086_3_, float partialTickTime) {
/* 207 */     super.setLivingAnimations(entitylivingbaseIn, p_78086_2_, p_78086_3_, partialTickTime);
/* 208 */     this.jumpRotation = MathHelper.sin(((EntityRabbit)entitylivingbaseIn).setJumpCompletion(partialTickTime) * 3.1415927F);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelRabbit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */