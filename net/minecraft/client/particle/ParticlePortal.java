/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticlePortal
/*    */   extends Particle
/*    */ {
/*    */   private final float portalParticleScale;
/*    */   private final double portalPosX;
/*    */   private final double portalPosY;
/*    */   private final double portalPosZ;
/*    */   
/*    */   protected ParticlePortal(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/* 16 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 17 */     this.motionX = xSpeedIn;
/* 18 */     this.motionY = ySpeedIn;
/* 19 */     this.motionZ = zSpeedIn;
/* 20 */     this.posX = xCoordIn;
/* 21 */     this.posY = yCoordIn;
/* 22 */     this.posZ = zCoordIn;
/* 23 */     this.portalPosX = this.posX;
/* 24 */     this.portalPosY = this.posY;
/* 25 */     this.portalPosZ = this.posZ;
/* 26 */     float f = this.rand.nextFloat() * 0.6F + 0.4F;
/* 27 */     this.particleScale = this.rand.nextFloat() * 0.2F + 0.5F;
/* 28 */     this.portalParticleScale = this.particleScale;
/* 29 */     this.particleRed = f * 0.9F;
/* 30 */     this.particleGreen = f * 0.3F;
/* 31 */     this.particleBlue = f;
/* 32 */     this.particleMaxAge = (int)(Math.random() * 10.0D) + 40;
/* 33 */     setParticleTextureIndex((int)(Math.random() * 8.0D));
/*    */   }
/*    */ 
/*    */   
/*    */   public void moveEntity(double x, double y, double z) {
/* 38 */     setEntityBoundingBox(getEntityBoundingBox().offset(x, y, z));
/* 39 */     resetPositionToBB();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 47 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 48 */     f = 1.0F - f;
/* 49 */     f *= f;
/* 50 */     f = 1.0F - f;
/* 51 */     this.particleScale = this.portalParticleScale * f;
/* 52 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBrightnessForRender(float p_189214_1_) {
/* 57 */     int i = super.getBrightnessForRender(p_189214_1_);
/* 58 */     float f = this.particleAge / this.particleMaxAge;
/* 59 */     f *= f;
/* 60 */     f *= f;
/* 61 */     int j = i & 0xFF;
/* 62 */     int k = i >> 16 & 0xFF;
/* 63 */     k += (int)(f * 15.0F * 16.0F);
/*    */     
/* 65 */     if (k > 240)
/*    */     {
/* 67 */       k = 240;
/*    */     }
/*    */     
/* 70 */     return j | k << 16;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 75 */     this.prevPosX = this.posX;
/* 76 */     this.prevPosY = this.posY;
/* 77 */     this.prevPosZ = this.posZ;
/* 78 */     float f = this.particleAge / this.particleMaxAge;
/* 79 */     float f1 = -f + f * f * 2.0F;
/* 80 */     float f2 = 1.0F - f1;
/* 81 */     this.posX = this.portalPosX + this.motionX * f2;
/* 82 */     this.posY = this.portalPosY + this.motionY * f2 + (1.0F - f);
/* 83 */     this.posZ = this.portalPosZ + this.motionZ * f2;
/*    */     
/* 85 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 87 */       setExpired();
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 95 */       return new ParticlePortal(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticlePortal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */