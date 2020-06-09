/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ParticleCrit
/*     */   extends Particle
/*     */ {
/*     */   float oSize;
/*     */   
/*     */   protected ParticleCrit(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46284_8_, double p_i46284_10_, double p_i46284_12_) {
/*  14 */     this(worldIn, xCoordIn, yCoordIn, zCoordIn, p_i46284_8_, p_i46284_10_, p_i46284_12_, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ParticleCrit(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46285_8_, double p_i46285_10_, double p_i46285_12_, float p_i46285_14_) {
/*  19 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/*  20 */     this.motionX *= 0.10000000149011612D;
/*  21 */     this.motionY *= 0.10000000149011612D;
/*  22 */     this.motionZ *= 0.10000000149011612D;
/*  23 */     this.motionX += p_i46285_8_ * 0.4D;
/*  24 */     this.motionY += p_i46285_10_ * 0.4D;
/*  25 */     this.motionZ += p_i46285_12_ * 0.4D;
/*  26 */     float f = (float)(Math.random() * 0.30000001192092896D + 0.6000000238418579D);
/*  27 */     this.particleRed = f;
/*  28 */     this.particleGreen = f;
/*  29 */     this.particleBlue = f;
/*  30 */     this.particleScale *= 0.75F;
/*  31 */     this.particleScale *= p_i46285_14_;
/*  32 */     this.oSize = this.particleScale;
/*  33 */     this.particleMaxAge = (int)(6.0D / (Math.random() * 0.8D + 0.6D));
/*  34 */     this.particleMaxAge = (int)(this.particleMaxAge * p_i46285_14_);
/*  35 */     setParticleTextureIndex(65);
/*  36 */     onUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/*  44 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/*  45 */     f = MathHelper.clamp(f, 0.0F, 1.0F);
/*  46 */     this.particleScale = this.oSize * f;
/*  47 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  52 */     this.prevPosX = this.posX;
/*  53 */     this.prevPosY = this.posY;
/*  54 */     this.prevPosZ = this.posZ;
/*     */     
/*  56 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/*  58 */       setExpired();
/*     */     }
/*     */     
/*  61 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*  62 */     this.particleGreen = (float)(this.particleGreen * 0.96D);
/*  63 */     this.particleBlue = (float)(this.particleBlue * 0.9D);
/*  64 */     this.motionX *= 0.699999988079071D;
/*  65 */     this.motionY *= 0.699999988079071D;
/*  66 */     this.motionZ *= 0.699999988079071D;
/*  67 */     this.motionY -= 0.019999999552965164D;
/*     */     
/*  69 */     if (this.isCollided) {
/*     */       
/*  71 */       this.motionX *= 0.699999988079071D;
/*  72 */       this.motionZ *= 0.699999988079071D;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class DamageIndicatorFactory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/*  80 */       Particle particle = new ParticleCrit(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn + 1.0D, zSpeedIn, 1.0F);
/*  81 */       particle.setMaxAge(20);
/*  82 */       particle.setParticleTextureIndex(67);
/*  83 */       return particle;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Factory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/*  91 */       return new ParticleCrit(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MagicFactory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/*  99 */       Particle particle = new ParticleCrit(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 100 */       particle.setRBGColorF(particle.getRedColorF() * 0.3F, particle.getGreenColorF() * 0.8F, particle.getBlueColorF());
/* 101 */       particle.nextTextureIndexX();
/* 102 */       return particle;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleCrit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */