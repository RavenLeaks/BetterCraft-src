/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.EnumParticleTypes;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleLava
/*    */   extends Particle
/*    */ {
/*    */   private final float lavaParticleScale;
/*    */   
/*    */   protected ParticleLava(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
/* 14 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 15 */     this.motionX *= 0.800000011920929D;
/* 16 */     this.motionY *= 0.800000011920929D;
/* 17 */     this.motionZ *= 0.800000011920929D;
/* 18 */     this.motionY = (this.rand.nextFloat() * 0.4F + 0.05F);
/* 19 */     this.particleRed = 1.0F;
/* 20 */     this.particleGreen = 1.0F;
/* 21 */     this.particleBlue = 1.0F;
/* 22 */     this.particleScale *= this.rand.nextFloat() * 2.0F + 0.2F;
/* 23 */     this.lavaParticleScale = this.particleScale;
/* 24 */     this.particleMaxAge = (int)(16.0D / (Math.random() * 0.8D + 0.2D));
/* 25 */     setParticleTextureIndex(49);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBrightnessForRender(float p_189214_1_) {
/* 30 */     int i = super.getBrightnessForRender(p_189214_1_);
/* 31 */     int j = 240;
/* 32 */     int k = i >> 16 & 0xFF;
/* 33 */     return 0xF0 | k << 16;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 41 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge;
/* 42 */     this.particleScale = this.lavaParticleScale * (1.0F - f * f);
/* 43 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 48 */     this.prevPosX = this.posX;
/* 49 */     this.prevPosY = this.posY;
/* 50 */     this.prevPosZ = this.posZ;
/*    */     
/* 52 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 54 */       setExpired();
/*    */     }
/*    */     
/* 57 */     float f = this.particleAge / this.particleMaxAge;
/*    */     
/* 59 */     if (this.rand.nextFloat() > f)
/*    */     {
/* 61 */       this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, new int[0]);
/*    */     }
/*    */     
/* 64 */     this.motionY -= 0.03D;
/* 65 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 66 */     this.motionX *= 0.9990000128746033D;
/* 67 */     this.motionY *= 0.9990000128746033D;
/* 68 */     this.motionZ *= 0.9990000128746033D;
/*    */     
/* 70 */     if (this.isCollided) {
/*    */       
/* 72 */       this.motionX *= 0.699999988079071D;
/* 73 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 81 */       return new ParticleLava(worldIn, xCoordIn, yCoordIn, zCoordIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleLava.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */