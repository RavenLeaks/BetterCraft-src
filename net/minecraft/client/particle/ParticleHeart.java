/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleHeart
/*    */   extends Particle
/*    */ {
/*    */   float particleScaleOverTime;
/*    */   
/*    */   protected ParticleHeart(World worldIn, double p_i1211_2_, double p_i1211_4_, double p_i1211_6_, double p_i1211_8_, double p_i1211_10_, double p_i1211_12_) {
/* 14 */     this(worldIn, p_i1211_2_, p_i1211_4_, p_i1211_6_, p_i1211_8_, p_i1211_10_, p_i1211_12_, 2.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ParticleHeart(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46354_8_, double p_i46354_10_, double p_i46354_12_, float scale) {
/* 19 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 20 */     this.motionX *= 0.009999999776482582D;
/* 21 */     this.motionY *= 0.009999999776482582D;
/* 22 */     this.motionZ *= 0.009999999776482582D;
/* 23 */     this.motionY += 0.1D;
/* 24 */     this.particleScale *= 0.75F;
/* 25 */     this.particleScale *= scale;
/* 26 */     this.particleScaleOverTime = this.particleScale;
/* 27 */     this.particleMaxAge = 16;
/* 28 */     setParticleTextureIndex(80);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 36 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/* 37 */     f = MathHelper.clamp(f, 0.0F, 1.0F);
/* 38 */     this.particleScale = this.particleScaleOverTime * f;
/* 39 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 44 */     this.prevPosX = this.posX;
/* 45 */     this.prevPosY = this.posY;
/* 46 */     this.prevPosZ = this.posZ;
/*    */     
/* 48 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 50 */       setExpired();
/*    */     }
/*    */     
/* 53 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*    */     
/* 55 */     if (this.posY == this.prevPosY) {
/*    */       
/* 57 */       this.motionX *= 1.1D;
/* 58 */       this.motionZ *= 1.1D;
/*    */     } 
/*    */     
/* 61 */     this.motionX *= 0.8600000143051147D;
/* 62 */     this.motionY *= 0.8600000143051147D;
/* 63 */     this.motionZ *= 0.8600000143051147D;
/*    */     
/* 65 */     if (this.isCollided) {
/*    */       
/* 67 */       this.motionX *= 0.699999988079071D;
/* 68 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class AngryVillagerFactory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 76 */       Particle particle = new ParticleHeart(worldIn, xCoordIn, yCoordIn + 0.5D, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 77 */       particle.setParticleTextureIndex(81);
/* 78 */       particle.setRBGColorF(1.0F, 1.0F, 1.0F);
/* 79 */       return particle;
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 87 */       return new ParticleHeart(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleHeart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */