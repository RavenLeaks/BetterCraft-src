/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.entity.AbstractClientPlayer;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class ModelElytra
/*     */   extends ModelBase {
/*     */   private final ModelRenderer rightWing;
/*  12 */   private final ModelRenderer leftWing = new ModelRenderer(this, 22, 0);
/*     */ 
/*     */   
/*     */   public ModelElytra() {
/*  16 */     this.leftWing.addBox(-10.0F, 0.0F, 0.0F, 10, 20, 2, 1.0F);
/*  17 */     this.rightWing = new ModelRenderer(this, 22, 0);
/*  18 */     this.rightWing.mirror = true;
/*  19 */     this.rightWing.addBox(0.0F, 0.0F, 0.0F, 10, 20, 2, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  27 */     GlStateManager.disableRescaleNormal();
/*  28 */     GlStateManager.disableCull();
/*     */     
/*  30 */     if (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).isChild()) {
/*     */       
/*  32 */       GlStateManager.pushMatrix();
/*  33 */       GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*  34 */       GlStateManager.translate(0.0F, 1.5F, -0.1F);
/*  35 */       this.leftWing.render(scale);
/*  36 */       this.rightWing.render(scale);
/*  37 */       GlStateManager.popMatrix();
/*     */     }
/*     */     else {
/*     */       
/*  41 */       this.leftWing.render(scale);
/*  42 */       this.rightWing.render(scale);
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
/*  53 */     super.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor, entityIn);
/*  54 */     float f = 0.2617994F;
/*  55 */     float f1 = -0.2617994F;
/*  56 */     float f2 = 0.0F;
/*  57 */     float f3 = 0.0F;
/*     */     
/*  59 */     if (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).isElytraFlying()) {
/*     */       
/*  61 */       float f4 = 1.0F;
/*     */       
/*  63 */       if (entityIn.motionY < 0.0D) {
/*     */         
/*  65 */         Vec3d vec3d = (new Vec3d(entityIn.motionX, entityIn.motionY, entityIn.motionZ)).normalize();
/*  66 */         f4 = 1.0F - (float)Math.pow(-vec3d.yCoord, 1.5D);
/*     */       } 
/*     */       
/*  69 */       f = f4 * 0.34906584F + (1.0F - f4) * f;
/*  70 */       f1 = f4 * -1.5707964F + (1.0F - f4) * f1;
/*     */     }
/*  72 */     else if (entityIn.isSneaking()) {
/*     */       
/*  74 */       f = 0.69813174F;
/*  75 */       f1 = -0.7853982F;
/*  76 */       f2 = 3.0F;
/*  77 */       f3 = 0.08726646F;
/*     */     } 
/*     */     
/*  80 */     this.leftWing.rotationPointX = 5.0F;
/*  81 */     this.leftWing.rotationPointY = f2;
/*     */     
/*  83 */     if (entityIn instanceof AbstractClientPlayer) {
/*     */       
/*  85 */       AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)entityIn;
/*  86 */       abstractclientplayer.rotateElytraX = (float)(abstractclientplayer.rotateElytraX + (f - abstractclientplayer.rotateElytraX) * 0.1D);
/*  87 */       abstractclientplayer.rotateElytraY = (float)(abstractclientplayer.rotateElytraY + (f3 - abstractclientplayer.rotateElytraY) * 0.1D);
/*  88 */       abstractclientplayer.rotateElytraZ = (float)(abstractclientplayer.rotateElytraZ + (f1 - abstractclientplayer.rotateElytraZ) * 0.1D);
/*  89 */       this.leftWing.rotateAngleX = abstractclientplayer.rotateElytraX;
/*  90 */       this.leftWing.rotateAngleY = abstractclientplayer.rotateElytraY;
/*  91 */       this.leftWing.rotateAngleZ = abstractclientplayer.rotateElytraZ;
/*     */     }
/*     */     else {
/*     */       
/*  95 */       this.leftWing.rotateAngleX = f;
/*  96 */       this.leftWing.rotateAngleZ = f1;
/*  97 */       this.leftWing.rotateAngleY = f3;
/*     */     } 
/*     */     
/* 100 */     this.rightWing.rotationPointX = -this.leftWing.rotationPointX;
/* 101 */     this.rightWing.rotateAngleY = -this.leftWing.rotateAngleY;
/* 102 */     this.rightWing.rotationPointY = this.leftWing.rotationPointY;
/* 103 */     this.rightWing.rotateAngleX = this.leftWing.rotateAngleX;
/* 104 */     this.rightWing.rotateAngleZ = -this.leftWing.rotateAngleZ;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelElytra.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */