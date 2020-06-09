/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleSuspendedTown
/*    */   extends Particle
/*    */ {
/*    */   protected ParticleSuspendedTown(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double speedIn) {
/*  9 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, speedIn);
/* 10 */     float f = this.rand.nextFloat() * 0.1F + 0.2F;
/* 11 */     this.particleRed = f;
/* 12 */     this.particleGreen = f;
/* 13 */     this.particleBlue = f;
/* 14 */     setParticleTextureIndex(0);
/* 15 */     setSize(0.02F, 0.02F);
/* 16 */     this.particleScale *= this.rand.nextFloat() * 0.6F + 0.5F;
/* 17 */     this.motionX *= 0.019999999552965164D;
/* 18 */     this.motionY *= 0.019999999552965164D;
/* 19 */     this.motionZ *= 0.019999999552965164D;
/* 20 */     this.particleMaxAge = (int)(20.0D / (Math.random() * 0.8D + 0.2D));
/*    */   }
/*    */ 
/*    */   
/*    */   public void moveEntity(double x, double y, double z) {
/* 25 */     setEntityBoundingBox(getEntityBoundingBox().offset(x, y, z));
/* 26 */     resetPositionToBB();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 31 */     this.prevPosX = this.posX;
/* 32 */     this.prevPosY = this.posY;
/* 33 */     this.prevPosZ = this.posZ;
/* 34 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 35 */     this.motionX *= 0.99D;
/* 36 */     this.motionY *= 0.99D;
/* 37 */     this.motionZ *= 0.99D;
/*    */     
/* 39 */     if (this.particleMaxAge-- <= 0)
/*    */     {
/* 41 */       setExpired();
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 49 */       return new ParticleSuspendedTown(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */   
/*    */   public static class HappyVillagerFactory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 57 */       Particle particle = new ParticleSuspendedTown(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 58 */       particle.setParticleTextureIndex(82);
/* 59 */       particle.setRBGColorF(1.0F, 1.0F, 1.0F);
/* 60 */       return particle;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleSuspendedTown.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */