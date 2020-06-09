/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class ParticleFlame
/*    */   extends Particle
/*    */ {
/*    */   private final float flameScale;
/*    */   
/*    */   protected ParticleFlame(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/* 15 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 16 */     this.motionX = this.motionX * 0.009999999776482582D + xSpeedIn;
/* 17 */     this.motionY = this.motionY * 0.009999999776482582D + ySpeedIn;
/* 18 */     this.motionZ = this.motionZ * 0.009999999776482582D + zSpeedIn;
/* 19 */     this.posX += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
/* 20 */     this.posY += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
/* 21 */     this.posZ += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.05F);
/* 22 */     this.flameScale = this.particleScale;
/* 23 */     this.particleRed = 1.0F;
/* 24 */     this.particleGreen = 1.0F;
/* 25 */     this.particleBlue = 1.0F;
/* 26 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D)) + 4;
/* 27 */     setParticleTextureIndex(48);
/*    */   }
/*    */ 
/*    */   
/*    */   public void moveEntity(double x, double y, double z) {
/* 32 */     setEntityBoundingBox(getEntityBoundingBox().offset(x, y, z));
/* 33 */     resetPositionToBB();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 41 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 42 */     this.particleScale = this.flameScale * (1.0F - f * f * 0.5F);
/* 43 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBrightnessForRender(float p_189214_1_) {
/* 48 */     float f = (this.particleAge + p_189214_1_) / this.particleMaxAge;
/* 49 */     f = MathHelper.clamp(f, 0.0F, 1.0F);
/* 50 */     int i = super.getBrightnessForRender(p_189214_1_);
/* 51 */     int j = i & 0xFF;
/* 52 */     int k = i >> 16 & 0xFF;
/* 53 */     j += (int)(f * 15.0F * 16.0F);
/*    */     
/* 55 */     if (j > 240)
/*    */     {
/* 57 */       j = 240;
/*    */     }
/*    */     
/* 60 */     return j | k << 16;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 65 */     this.prevPosX = this.posX;
/* 66 */     this.prevPosY = this.posY;
/* 67 */     this.prevPosZ = this.posZ;
/*    */     
/* 69 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 71 */       setExpired();
/*    */     }
/*    */     
/* 74 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 75 */     this.motionX *= 0.9599999785423279D;
/* 76 */     this.motionY *= 0.9599999785423279D;
/* 77 */     this.motionZ *= 0.9599999785423279D;
/*    */     
/* 79 */     if (this.isCollided) {
/*    */       
/* 81 */       this.motionX *= 0.699999988079071D;
/* 82 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 90 */       return new ParticleFlame(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleFlame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */