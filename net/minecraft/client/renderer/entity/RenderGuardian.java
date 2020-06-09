/*     */ package net.minecraft.client.renderer.entity;
/*     */ import net.minecraft.client.model.ModelBase;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.culling.ICamera;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ public class RenderGuardian extends RenderLiving<EntityGuardian> {
/*  18 */   private static final ResourceLocation GUARDIAN_TEXTURE = new ResourceLocation("textures/entity/guardian.png");
/*  19 */   private static final ResourceLocation GUARDIAN_BEAM_TEXTURE = new ResourceLocation("textures/entity/guardian_beam.png");
/*     */ 
/*     */   
/*     */   public RenderGuardian(RenderManager renderManagerIn) {
/*  23 */     super(renderManagerIn, (ModelBase)new ModelGuardian(), 0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(EntityGuardian livingEntity, ICamera camera, double camX, double camY, double camZ) {
/*  28 */     if (super.shouldRender(livingEntity, camera, camX, camY, camZ))
/*     */     {
/*  30 */       return true;
/*     */     }
/*     */ 
/*     */     
/*  34 */     if (livingEntity.hasTargetedEntity()) {
/*     */       
/*  36 */       EntityLivingBase entitylivingbase = livingEntity.getTargetedEntity();
/*     */       
/*  38 */       if (entitylivingbase != null) {
/*     */         
/*  40 */         Vec3d vec3d = getPosition(entitylivingbase, entitylivingbase.height * 0.5D, 1.0F);
/*  41 */         Vec3d vec3d1 = getPosition((EntityLivingBase)livingEntity, livingEntity.getEyeHeight(), 1.0F);
/*     */         
/*  43 */         if (camera.isBoundingBoxInFrustum(new AxisAlignedBB(vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, vec3d.xCoord, vec3d.yCoord, vec3d.zCoord)))
/*     */         {
/*  45 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private Vec3d getPosition(EntityLivingBase entityLivingBaseIn, double p_177110_2_, float p_177110_4_) {
/*  56 */     double d0 = entityLivingBaseIn.lastTickPosX + (entityLivingBaseIn.posX - entityLivingBaseIn.lastTickPosX) * p_177110_4_;
/*  57 */     double d1 = p_177110_2_ + entityLivingBaseIn.lastTickPosY + (entityLivingBaseIn.posY - entityLivingBaseIn.lastTickPosY) * p_177110_4_;
/*  58 */     double d2 = entityLivingBaseIn.lastTickPosZ + (entityLivingBaseIn.posZ - entityLivingBaseIn.lastTickPosZ) * p_177110_4_;
/*  59 */     return new Vec3d(d0, d1, d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(EntityGuardian entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  67 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*  68 */     EntityLivingBase entitylivingbase = entity.getTargetedEntity();
/*     */     
/*  70 */     if (entitylivingbase != null) {
/*     */       
/*  72 */       float f = entity.getAttackAnimationScale(partialTicks);
/*  73 */       Tessellator tessellator = Tessellator.getInstance();
/*  74 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  75 */       bindTexture(GUARDIAN_BEAM_TEXTURE);
/*  76 */       GlStateManager.glTexParameteri(3553, 10242, 10497);
/*  77 */       GlStateManager.glTexParameteri(3553, 10243, 10497);
/*  78 */       GlStateManager.disableLighting();
/*  79 */       GlStateManager.disableCull();
/*  80 */       GlStateManager.disableBlend();
/*  81 */       GlStateManager.depthMask(true);
/*  82 */       float f1 = 240.0F;
/*  83 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
/*  84 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/*  85 */       float f2 = (float)entity.world.getTotalWorldTime() + partialTicks;
/*  86 */       float f3 = f2 * 0.5F % 1.0F;
/*  87 */       float f4 = entity.getEyeHeight();
/*  88 */       GlStateManager.pushMatrix();
/*  89 */       GlStateManager.translate((float)x, (float)y + f4, (float)z);
/*  90 */       Vec3d vec3d = getPosition(entitylivingbase, entitylivingbase.height * 0.5D, partialTicks);
/*  91 */       Vec3d vec3d1 = getPosition((EntityLivingBase)entity, f4, partialTicks);
/*  92 */       Vec3d vec3d2 = vec3d.subtract(vec3d1);
/*  93 */       double d0 = vec3d2.lengthVector() + 1.0D;
/*  94 */       vec3d2 = vec3d2.normalize();
/*  95 */       float f5 = (float)Math.acos(vec3d2.yCoord);
/*  96 */       float f6 = (float)Math.atan2(vec3d2.zCoord, vec3d2.xCoord);
/*  97 */       GlStateManager.rotate((1.5707964F + -f6) * 57.295776F, 0.0F, 1.0F, 0.0F);
/*  98 */       GlStateManager.rotate(f5 * 57.295776F, 1.0F, 0.0F, 0.0F);
/*  99 */       int i = 1;
/* 100 */       double d1 = f2 * 0.05D * -1.5D;
/* 101 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 102 */       float f7 = f * f;
/* 103 */       int j = 64 + (int)(f7 * 191.0F);
/* 104 */       int k = 32 + (int)(f7 * 191.0F);
/* 105 */       int l = 128 - (int)(f7 * 64.0F);
/* 106 */       double d2 = 0.2D;
/* 107 */       double d3 = 0.282D;
/* 108 */       double d4 = 0.0D + Math.cos(d1 + 2.356194490192345D) * 0.282D;
/* 109 */       double d5 = 0.0D + Math.sin(d1 + 2.356194490192345D) * 0.282D;
/* 110 */       double d6 = 0.0D + Math.cos(d1 + 0.7853981633974483D) * 0.282D;
/* 111 */       double d7 = 0.0D + Math.sin(d1 + 0.7853981633974483D) * 0.282D;
/* 112 */       double d8 = 0.0D + Math.cos(d1 + 3.9269908169872414D) * 0.282D;
/* 113 */       double d9 = 0.0D + Math.sin(d1 + 3.9269908169872414D) * 0.282D;
/* 114 */       double d10 = 0.0D + Math.cos(d1 + 5.497787143782138D) * 0.282D;
/* 115 */       double d11 = 0.0D + Math.sin(d1 + 5.497787143782138D) * 0.282D;
/* 116 */       double d12 = 0.0D + Math.cos(d1 + Math.PI) * 0.2D;
/* 117 */       double d13 = 0.0D + Math.sin(d1 + Math.PI) * 0.2D;
/* 118 */       double d14 = 0.0D + Math.cos(d1 + 0.0D) * 0.2D;
/* 119 */       double d15 = 0.0D + Math.sin(d1 + 0.0D) * 0.2D;
/* 120 */       double d16 = 0.0D + Math.cos(d1 + 1.5707963267948966D) * 0.2D;
/* 121 */       double d17 = 0.0D + Math.sin(d1 + 1.5707963267948966D) * 0.2D;
/* 122 */       double d18 = 0.0D + Math.cos(d1 + 4.71238898038469D) * 0.2D;
/* 123 */       double d19 = 0.0D + Math.sin(d1 + 4.71238898038469D) * 0.2D;
/* 124 */       double d20 = 0.0D;
/* 125 */       double d21 = 0.4999D;
/* 126 */       double d22 = (-1.0F + f3);
/* 127 */       double d23 = d0 * 2.5D + d22;
/* 128 */       bufferbuilder.pos(d12, d0, d13).tex(0.4999D, d23).color(j, k, l, 255).endVertex();
/* 129 */       bufferbuilder.pos(d12, 0.0D, d13).tex(0.4999D, d22).color(j, k, l, 255).endVertex();
/* 130 */       bufferbuilder.pos(d14, 0.0D, d15).tex(0.0D, d22).color(j, k, l, 255).endVertex();
/* 131 */       bufferbuilder.pos(d14, d0, d15).tex(0.0D, d23).color(j, k, l, 255).endVertex();
/* 132 */       bufferbuilder.pos(d16, d0, d17).tex(0.4999D, d23).color(j, k, l, 255).endVertex();
/* 133 */       bufferbuilder.pos(d16, 0.0D, d17).tex(0.4999D, d22).color(j, k, l, 255).endVertex();
/* 134 */       bufferbuilder.pos(d18, 0.0D, d19).tex(0.0D, d22).color(j, k, l, 255).endVertex();
/* 135 */       bufferbuilder.pos(d18, d0, d19).tex(0.0D, d23).color(j, k, l, 255).endVertex();
/* 136 */       double d24 = 0.0D;
/*     */       
/* 138 */       if (entity.ticksExisted % 2 == 0)
/*     */       {
/* 140 */         d24 = 0.5D;
/*     */       }
/*     */       
/* 143 */       bufferbuilder.pos(d4, d0, d5).tex(0.5D, d24 + 0.5D).color(j, k, l, 255).endVertex();
/* 144 */       bufferbuilder.pos(d6, d0, d7).tex(1.0D, d24 + 0.5D).color(j, k, l, 255).endVertex();
/* 145 */       bufferbuilder.pos(d10, d0, d11).tex(1.0D, d24).color(j, k, l, 255).endVertex();
/* 146 */       bufferbuilder.pos(d8, d0, d9).tex(0.5D, d24).color(j, k, l, 255).endVertex();
/* 147 */       tessellator.draw();
/* 148 */       GlStateManager.popMatrix();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ResourceLocation getEntityTexture(EntityGuardian entity) {
/* 157 */     return GUARDIAN_TEXTURE;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderGuardian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */