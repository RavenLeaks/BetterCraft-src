/*     */ package net.minecraft.client.renderer.entity;
/*     */ 
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
/*     */ import optifine.Config;
/*     */ import shadersmod.client.Shaders;
/*     */ 
/*     */ public abstract class RenderLiving<T extends EntityLiving>
/*     */   extends RenderLivingBase<T>
/*     */ {
/*     */   public RenderLiving(RenderManager rendermanagerIn, ModelBase modelbaseIn, float shadowsizeIn) {
/*  20 */     super(rendermanagerIn, modelbaseIn, shadowsizeIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canRenderName(T entity) {
/*  25 */     return (super.canRenderName(entity) && (entity.getAlwaysRenderNameTagForRender() || (entity.hasCustomName() && entity == this.renderManager.pointedEntity)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldRender(T livingEntity, ICamera camera, double camX, double camY, double camZ) {
/*  30 */     if (super.shouldRender(livingEntity, camera, camX, camY, camZ))
/*     */     {
/*  32 */       return true;
/*     */     }
/*  34 */     if (livingEntity.getLeashed() && livingEntity.getLeashedToEntity() != null) {
/*     */       
/*  36 */       Entity entity = livingEntity.getLeashedToEntity();
/*  37 */       return camera.isBoundingBoxInFrustum(entity.getRenderBoundingBox());
/*     */     } 
/*     */ 
/*     */     
/*  41 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
/*  50 */     super.doRender(entity, x, y, z, entityYaw, partialTicks);
/*     */     
/*  52 */     if (!this.renderOutlines)
/*     */     {
/*  54 */       renderLeash(entity, x, y, z, entityYaw, partialTicks);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLightmap(T entityLivingIn) {
/*  60 */     int i = entityLivingIn.getBrightnessForRender();
/*  61 */     int j = i % 65536;
/*  62 */     int k = i / 65536;
/*  63 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private double interpolateValue(double start, double end, double pct) {
/*  71 */     return start + (end - start) * pct;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderLeash(T entityLivingIn, double x, double y, double z, float entityYaw, float partialTicks) {
/*  76 */     if (!Config.isShaders() || !Shaders.isShadowPass) {
/*     */       
/*  78 */       Entity entity = entityLivingIn.getLeashedToEntity();
/*     */       
/*  80 */       if (entity != null) {
/*     */         
/*  82 */         y -= (1.6D - ((EntityLiving)entityLivingIn).height) * 0.5D;
/*  83 */         Tessellator tessellator = Tessellator.getInstance();
/*  84 */         BufferBuilder bufferbuilder = tessellator.getBuffer();
/*  85 */         double d0 = interpolateValue(entity.prevRotationYaw, entity.rotationYaw, (partialTicks * 0.5F)) * 0.01745329238474369D;
/*  86 */         double d1 = interpolateValue(entity.prevRotationPitch, entity.rotationPitch, (partialTicks * 0.5F)) * 0.01745329238474369D;
/*  87 */         double d2 = Math.cos(d0);
/*  88 */         double d3 = Math.sin(d0);
/*  89 */         double d4 = Math.sin(d1);
/*     */         
/*  91 */         if (entity instanceof net.minecraft.entity.EntityHanging) {
/*     */           
/*  93 */           d2 = 0.0D;
/*  94 */           d3 = 0.0D;
/*  95 */           d4 = -1.0D;
/*     */         } 
/*     */         
/*  98 */         double d5 = Math.cos(d1);
/*  99 */         double d6 = interpolateValue(entity.prevPosX, entity.posX, partialTicks) - d2 * 0.7D - d3 * 0.5D * d5;
/* 100 */         double d7 = interpolateValue(entity.prevPosY + entity.getEyeHeight() * 0.7D, entity.posY + entity.getEyeHeight() * 0.7D, partialTicks) - d4 * 0.5D - 0.25D;
/* 101 */         double d8 = interpolateValue(entity.prevPosZ, entity.posZ, partialTicks) - d3 * 0.7D + d2 * 0.5D * d5;
/* 102 */         double d9 = interpolateValue(((EntityLiving)entityLivingIn).prevRenderYawOffset, ((EntityLiving)entityLivingIn).renderYawOffset, partialTicks) * 0.01745329238474369D + 1.5707963267948966D;
/* 103 */         d2 = Math.cos(d9) * ((EntityLiving)entityLivingIn).width * 0.4D;
/* 104 */         d3 = Math.sin(d9) * ((EntityLiving)entityLivingIn).width * 0.4D;
/* 105 */         double d10 = interpolateValue(((EntityLiving)entityLivingIn).prevPosX, ((EntityLiving)entityLivingIn).posX, partialTicks) + d2;
/* 106 */         double d11 = interpolateValue(((EntityLiving)entityLivingIn).prevPosY, ((EntityLiving)entityLivingIn).posY, partialTicks);
/* 107 */         double d12 = interpolateValue(((EntityLiving)entityLivingIn).prevPosZ, ((EntityLiving)entityLivingIn).posZ, partialTicks) + d3;
/* 108 */         x += d2;
/* 109 */         z += d3;
/* 110 */         double d13 = (float)(d6 - d10);
/* 111 */         double d14 = (float)(d7 - d11);
/* 112 */         double d15 = (float)(d8 - d12);
/* 113 */         GlStateManager.disableTexture2D();
/* 114 */         GlStateManager.disableLighting();
/* 115 */         GlStateManager.disableCull();
/*     */         
/* 117 */         if (Config.isShaders())
/*     */         {
/* 119 */           Shaders.beginLeash();
/*     */         }
/*     */         
/* 122 */         int i = 24;
/* 123 */         double d16 = 0.025D;
/* 124 */         bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*     */         
/* 126 */         for (int j = 0; j <= 24; j++) {
/*     */           
/* 128 */           float f = 0.5F;
/* 129 */           float f1 = 0.4F;
/* 130 */           float f2 = 0.3F;
/*     */           
/* 132 */           if (j % 2 == 0) {
/*     */             
/* 134 */             f *= 0.7F;
/* 135 */             f1 *= 0.7F;
/* 136 */             f2 *= 0.7F;
/*     */           } 
/*     */           
/* 139 */           float f3 = j / 24.0F;
/* 140 */           bufferbuilder.pos(x + d13 * f3 + 0.0D, y + d14 * (f3 * f3 + f3) * 0.5D + ((24.0F - j) / 18.0F + 0.125F), z + d15 * f3).color(f, f1, f2, 1.0F).endVertex();
/* 141 */           bufferbuilder.pos(x + d13 * f3 + 0.025D, y + d14 * (f3 * f3 + f3) * 0.5D + ((24.0F - j) / 18.0F + 0.125F) + 0.025D, z + d15 * f3).color(f, f1, f2, 1.0F).endVertex();
/*     */         } 
/*     */         
/* 144 */         tessellator.draw();
/* 145 */         bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*     */         
/* 147 */         for (int k = 0; k <= 24; k++) {
/*     */           
/* 149 */           float f4 = 0.5F;
/* 150 */           float f5 = 0.4F;
/* 151 */           float f6 = 0.3F;
/*     */           
/* 153 */           if (k % 2 == 0) {
/*     */             
/* 155 */             f4 *= 0.7F;
/* 156 */             f5 *= 0.7F;
/* 157 */             f6 *= 0.7F;
/*     */           } 
/*     */           
/* 160 */           float f7 = k / 24.0F;
/* 161 */           bufferbuilder.pos(x + d13 * f7 + 0.0D, y + d14 * (f7 * f7 + f7) * 0.5D + ((24.0F - k) / 18.0F + 0.125F) + 0.025D, z + d15 * f7).color(f4, f5, f6, 1.0F).endVertex();
/* 162 */           bufferbuilder.pos(x + d13 * f7 + 0.025D, y + d14 * (f7 * f7 + f7) * 0.5D + ((24.0F - k) / 18.0F + 0.125F), z + d15 * f7 + 0.025D).color(f4, f5, f6, 1.0F).endVertex();
/*     */         } 
/*     */         
/* 165 */         tessellator.draw();
/*     */         
/* 167 */         if (Config.isShaders())
/*     */         {
/* 169 */           Shaders.endLeash();
/*     */         }
/*     */         
/* 172 */         GlStateManager.enableLighting();
/* 173 */         GlStateManager.enableTexture2D();
/* 174 */         GlStateManager.enableCull();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\RenderLiving.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */