/*     */ package net.minecraft.client.model;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class ModelGuardian
/*     */   extends ModelBase {
/*     */   private final ModelRenderer guardianBody;
/*     */   private final ModelRenderer guardianEye;
/*     */   private final ModelRenderer[] guardianSpines;
/*     */   private final ModelRenderer[] guardianTail;
/*     */   
/*     */   public ModelGuardian() {
/*  18 */     this.textureWidth = 64;
/*  19 */     this.textureHeight = 64;
/*  20 */     this.guardianSpines = new ModelRenderer[12];
/*  21 */     this.guardianBody = new ModelRenderer(this);
/*  22 */     this.guardianBody.setTextureOffset(0, 0).addBox(-6.0F, 10.0F, -8.0F, 12, 12, 16);
/*  23 */     this.guardianBody.setTextureOffset(0, 28).addBox(-8.0F, 10.0F, -6.0F, 2, 12, 12);
/*  24 */     this.guardianBody.setTextureOffset(0, 28).addBox(6.0F, 10.0F, -6.0F, 2, 12, 12, true);
/*  25 */     this.guardianBody.setTextureOffset(16, 40).addBox(-6.0F, 8.0F, -6.0F, 12, 2, 12);
/*  26 */     this.guardianBody.setTextureOffset(16, 40).addBox(-6.0F, 22.0F, -6.0F, 12, 2, 12);
/*     */     
/*  28 */     for (int i = 0; i < this.guardianSpines.length; i++) {
/*     */       
/*  30 */       this.guardianSpines[i] = new ModelRenderer(this, 0, 0);
/*  31 */       this.guardianSpines[i].addBox(-1.0F, -4.5F, -1.0F, 2, 9, 2);
/*  32 */       this.guardianBody.addChild(this.guardianSpines[i]);
/*     */     } 
/*     */     
/*  35 */     this.guardianEye = new ModelRenderer(this, 8, 0);
/*  36 */     this.guardianEye.addBox(-1.0F, 15.0F, 0.0F, 2, 2, 1);
/*  37 */     this.guardianBody.addChild(this.guardianEye);
/*  38 */     this.guardianTail = new ModelRenderer[3];
/*  39 */     this.guardianTail[0] = new ModelRenderer(this, 40, 0);
/*  40 */     this.guardianTail[0].addBox(-2.0F, 14.0F, 7.0F, 4, 4, 8);
/*  41 */     this.guardianTail[1] = new ModelRenderer(this, 0, 54);
/*  42 */     this.guardianTail[1].addBox(0.0F, 14.0F, 0.0F, 3, 3, 7);
/*  43 */     this.guardianTail[2] = new ModelRenderer(this);
/*  44 */     this.guardianTail[2].setTextureOffset(41, 32).addBox(0.0F, 14.0F, 0.0F, 2, 2, 6);
/*  45 */     this.guardianTail[2].setTextureOffset(25, 19).addBox(1.0F, 10.5F, 3.0F, 1, 9, 9);
/*  46 */     this.guardianBody.addChild(this.guardianTail[0]);
/*  47 */     this.guardianTail[0].addChild(this.guardianTail[1]);
/*  48 */     this.guardianTail[1].addChild(this.guardianTail[2]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/*  56 */     setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entityIn);
/*  57 */     this.guardianBody.render(scale);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn) {
/*     */     EntityLivingBase entityLivingBase;
/*  67 */     EntityGuardian entityguardian = (EntityGuardian)entityIn;
/*  68 */     float f = ageInTicks - entityguardian.ticksExisted;
/*  69 */     this.guardianBody.rotateAngleY = netHeadYaw * 0.017453292F;
/*  70 */     this.guardianBody.rotateAngleX = headPitch * 0.017453292F;
/*  71 */     float[] afloat = { 1.75F, 0.25F, 0.0F, 0.0F, 0.5F, 0.5F, 0.5F, 0.5F, 1.25F, 0.75F, 0.0F, 0.0F };
/*  72 */     float[] afloat1 = { 0.0F, 0.0F, 0.0F, 0.0F, 0.25F, 1.75F, 1.25F, 0.75F, 0.0F, 0.0F, 0.0F, 0.0F };
/*  73 */     float[] afloat2 = { 0.0F, 0.0F, 0.25F, 1.75F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.75F, 1.25F };
/*  74 */     float[] afloat3 = { 0.0F, 0.0F, 8.0F, -8.0F, -8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F, 8.0F, -8.0F };
/*  75 */     float[] afloat4 = { -8.0F, -8.0F, -8.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F, 8.0F, 8.0F, 8.0F, 8.0F };
/*  76 */     float[] afloat5 = { 8.0F, -8.0F, 0.0F, 0.0F, -8.0F, -8.0F, 8.0F, 8.0F, 8.0F, -8.0F, 0.0F, 0.0F };
/*  77 */     float f1 = (1.0F - entityguardian.getSpikesAnimation(f)) * 0.55F;
/*     */     
/*  79 */     for (int i = 0; i < 12; i++) {
/*     */       
/*  81 */       (this.guardianSpines[i]).rotateAngleX = 3.1415927F * afloat[i];
/*  82 */       (this.guardianSpines[i]).rotateAngleY = 3.1415927F * afloat1[i];
/*  83 */       (this.guardianSpines[i]).rotateAngleZ = 3.1415927F * afloat2[i];
/*  84 */       (this.guardianSpines[i]).rotationPointX = afloat3[i] * (1.0F + MathHelper.cos(ageInTicks * 1.5F + i) * 0.01F - f1);
/*  85 */       (this.guardianSpines[i]).rotationPointY = 16.0F + afloat4[i] * (1.0F + MathHelper.cos(ageInTicks * 1.5F + i) * 0.01F - f1);
/*  86 */       (this.guardianSpines[i]).rotationPointZ = afloat5[i] * (1.0F + MathHelper.cos(ageInTicks * 1.5F + i) * 0.01F - f1);
/*     */     } 
/*     */     
/*  89 */     this.guardianEye.rotationPointZ = -8.25F;
/*  90 */     Entity entity = Minecraft.getMinecraft().getRenderViewEntity();
/*     */     
/*  92 */     if (entityguardian.hasTargetedEntity())
/*     */     {
/*  94 */       entityLivingBase = entityguardian.getTargetedEntity();
/*     */     }
/*     */     
/*  97 */     if (entityLivingBase != null) {
/*     */       
/*  99 */       Vec3d vec3d = entityLivingBase.getPositionEyes(0.0F);
/* 100 */       Vec3d vec3d1 = entityIn.getPositionEyes(0.0F);
/* 101 */       double d0 = vec3d.yCoord - vec3d1.yCoord;
/*     */       
/* 103 */       if (d0 > 0.0D) {
/*     */         
/* 105 */         this.guardianEye.rotationPointY = 0.0F;
/*     */       }
/*     */       else {
/*     */         
/* 109 */         this.guardianEye.rotationPointY = 1.0F;
/*     */       } 
/*     */       
/* 112 */       Vec3d vec3d2 = entityIn.getLook(0.0F);
/* 113 */       vec3d2 = new Vec3d(vec3d2.xCoord, 0.0D, vec3d2.zCoord);
/* 114 */       Vec3d vec3d3 = (new Vec3d(vec3d1.xCoord - vec3d.xCoord, 0.0D, vec3d1.zCoord - vec3d.zCoord)).normalize().rotateYaw(1.5707964F);
/* 115 */       double d1 = vec3d2.dotProduct(vec3d3);
/* 116 */       this.guardianEye.rotationPointX = MathHelper.sqrt((float)Math.abs(d1)) * 2.0F * (float)Math.signum(d1);
/*     */     } 
/*     */     
/* 119 */     this.guardianEye.showModel = true;
/* 120 */     float f2 = entityguardian.getTailAnimation(f);
/* 121 */     (this.guardianTail[0]).rotateAngleY = MathHelper.sin(f2) * 3.1415927F * 0.05F;
/* 122 */     (this.guardianTail[1]).rotateAngleY = MathHelper.sin(f2) * 3.1415927F * 0.1F;
/* 123 */     (this.guardianTail[1]).rotationPointX = -1.5F;
/* 124 */     (this.guardianTail[1]).rotationPointY = 0.5F;
/* 125 */     (this.guardianTail[1]).rotationPointZ = 14.0F;
/* 126 */     (this.guardianTail[2]).rotateAngleY = MathHelper.sin(f2) * 3.1415927F * 0.15F;
/* 127 */     (this.guardianTail[2]).rotationPointX = 0.5F;
/* 128 */     (this.guardianTail[2]).rotationPointY = 0.5F;
/* 129 */     (this.guardianTail[2]).rotationPointZ = 6.0F;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\model\ModelGuardian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */