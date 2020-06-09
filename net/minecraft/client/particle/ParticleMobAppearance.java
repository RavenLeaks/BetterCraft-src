/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderManager;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityElderGuardian;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleMobAppearance
/*    */   extends Particle
/*    */ {
/*    */   private EntityLivingBase entity;
/*    */   
/*    */   protected ParticleMobAppearance(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
/* 20 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 21 */     this.particleRed = 1.0F;
/* 22 */     this.particleGreen = 1.0F;
/* 23 */     this.particleBlue = 1.0F;
/* 24 */     this.motionX = 0.0D;
/* 25 */     this.motionY = 0.0D;
/* 26 */     this.motionZ = 0.0D;
/* 27 */     this.particleGravity = 0.0F;
/* 28 */     this.particleMaxAge = 30;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFXLayer() {
/* 37 */     return 3;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 42 */     super.onUpdate();
/*    */     
/* 44 */     if (this.entity == null) {
/*    */       
/* 46 */       EntityElderGuardian entityelderguardian = new EntityElderGuardian(this.worldObj);
/* 47 */       entityelderguardian.func_190767_di();
/* 48 */       this.entity = (EntityLivingBase)entityelderguardian;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 57 */     if (this.entity != null) {
/*    */       
/* 59 */       RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
/* 60 */       rendermanager.setRenderPosition(Particle.interpPosX, Particle.interpPosY, Particle.interpPosZ);
/* 61 */       float f = 0.42553192F;
/* 62 */       float f1 = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 63 */       GlStateManager.depthMask(true);
/* 64 */       GlStateManager.enableBlend();
/* 65 */       GlStateManager.enableDepth();
/* 66 */       GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 67 */       float f2 = 240.0F;
/* 68 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
/* 69 */       GlStateManager.pushMatrix();
/* 70 */       float f3 = 0.05F + 0.5F * MathHelper.sin(f1 * 3.1415927F);
/* 71 */       GlStateManager.color(1.0F, 1.0F, 1.0F, f3);
/* 72 */       GlStateManager.translate(0.0F, 1.8F, 0.0F);
/* 73 */       GlStateManager.rotate(180.0F - entityIn.rotationYaw, 0.0F, 1.0F, 0.0F);
/* 74 */       GlStateManager.rotate(60.0F - 150.0F * f1 - entityIn.rotationPitch, 1.0F, 0.0F, 0.0F);
/* 75 */       GlStateManager.translate(0.0F, -0.4F, -1.5F);
/* 76 */       GlStateManager.scale(0.42553192F, 0.42553192F, 0.42553192F);
/* 77 */       this.entity.rotationYaw = 0.0F;
/* 78 */       this.entity.rotationYawHead = 0.0F;
/* 79 */       this.entity.prevRotationYaw = 0.0F;
/* 80 */       this.entity.prevRotationYawHead = 0.0F;
/* 81 */       rendermanager.doRenderEntity((Entity)this.entity, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
/* 82 */       GlStateManager.popMatrix();
/* 83 */       GlStateManager.enableDepth();
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 91 */       return new ParticleMobAppearance(worldIn, xCoordIn, yCoordIn, zCoordIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleMobAppearance.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */