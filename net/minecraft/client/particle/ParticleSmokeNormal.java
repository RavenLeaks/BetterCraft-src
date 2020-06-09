/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleSmokeNormal
/*    */   extends Particle
/*    */ {
/*    */   float smokeParticleScale;
/*    */   
/*    */   private ParticleSmokeNormal(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46347_8_, double p_i46347_10_, double p_i46347_12_) {
/* 14 */     this(worldIn, xCoordIn, yCoordIn, zCoordIn, p_i46347_8_, p_i46347_10_, p_i46347_12_, 1.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ParticleSmokeNormal(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46348_8_, double p_i46348_10_, double p_i46348_12_, float p_i46348_14_) {
/* 19 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 20 */     this.motionX *= 0.10000000149011612D;
/* 21 */     this.motionY *= 0.10000000149011612D;
/* 22 */     this.motionZ *= 0.10000000149011612D;
/* 23 */     this.motionX += p_i46348_8_;
/* 24 */     this.motionY += p_i46348_10_;
/* 25 */     this.motionZ += p_i46348_12_;
/* 26 */     float f = (float)(Math.random() * 0.30000001192092896D);
/* 27 */     this.particleRed = f;
/* 28 */     this.particleGreen = f;
/* 29 */     this.particleBlue = f;
/* 30 */     this.particleScale *= 0.75F;
/* 31 */     this.particleScale *= p_i46348_14_;
/* 32 */     this.smokeParticleScale = this.particleScale;
/* 33 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/* 34 */     this.particleMaxAge = (int)(this.particleMaxAge * p_i46348_14_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 42 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/* 43 */     f = MathHelper.clamp(f, 0.0F, 1.0F);
/* 44 */     this.particleScale = this.smokeParticleScale * f;
/* 45 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 50 */     this.prevPosX = this.posX;
/* 51 */     this.prevPosY = this.posY;
/* 52 */     this.prevPosZ = this.posZ;
/*    */     
/* 54 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 56 */       setExpired();
/*    */     }
/*    */     
/* 59 */     setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
/* 60 */     this.motionY += 0.004D;
/* 61 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*    */     
/* 63 */     if (this.posY == this.prevPosY) {
/*    */       
/* 65 */       this.motionX *= 1.1D;
/* 66 */       this.motionZ *= 1.1D;
/*    */     } 
/*    */     
/* 69 */     this.motionX *= 0.9599999785423279D;
/* 70 */     this.motionY *= 0.9599999785423279D;
/* 71 */     this.motionZ *= 0.9599999785423279D;
/*    */     
/* 73 */     if (this.isCollided) {
/*    */       
/* 75 */       this.motionX *= 0.699999988079071D;
/* 76 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 84 */       return new ParticleSmokeNormal(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, null);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleSmokeNormal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */