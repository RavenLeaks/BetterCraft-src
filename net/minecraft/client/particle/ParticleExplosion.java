/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleExplosion
/*    */   extends Particle
/*    */ {
/*    */   protected ParticleExplosion(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/*  9 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 10 */     this.motionX = xSpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
/* 11 */     this.motionY = ySpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
/* 12 */     this.motionZ = zSpeedIn + (Math.random() * 2.0D - 1.0D) * 0.05000000074505806D;
/* 13 */     float f = this.rand.nextFloat() * 0.3F + 0.7F;
/* 14 */     this.particleRed = f;
/* 15 */     this.particleGreen = f;
/* 16 */     this.particleBlue = f;
/* 17 */     this.particleScale = this.rand.nextFloat() * this.rand.nextFloat() * 6.0F + 1.0F;
/* 18 */     this.particleMaxAge = (int)(16.0D / (this.rand.nextFloat() * 0.8D + 0.2D)) + 2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 23 */     this.prevPosX = this.posX;
/* 24 */     this.prevPosY = this.posY;
/* 25 */     this.prevPosZ = this.posZ;
/*    */     
/* 27 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 29 */       setExpired();
/*    */     }
/*    */     
/* 32 */     setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
/* 33 */     this.motionY += 0.004D;
/* 34 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 35 */     this.motionX *= 0.8999999761581421D;
/* 36 */     this.motionY *= 0.8999999761581421D;
/* 37 */     this.motionZ *= 0.8999999761581421D;
/*    */     
/* 39 */     if (this.isCollided) {
/*    */       
/* 41 */       this.motionX *= 0.699999988079071D;
/* 42 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 50 */       return new ParticleExplosion(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleExplosion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */