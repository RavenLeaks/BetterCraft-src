/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ParticleSpell
/*     */   extends Particle {
/*   8 */   private static final Random RANDOM = new Random();
/*     */ 
/*     */   
/*  11 */   private int baseSpellTextureIndex = 128;
/*     */ 
/*     */   
/*     */   protected ParticleSpell(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1229_8_, double ySpeed, double p_i1229_12_) {
/*  15 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.5D - RANDOM.nextDouble(), ySpeed, 0.5D - RANDOM.nextDouble());
/*  16 */     this.motionY *= 0.20000000298023224D;
/*     */     
/*  18 */     if (p_i1229_8_ == 0.0D && p_i1229_12_ == 0.0D) {
/*     */       
/*  20 */       this.motionX *= 0.10000000149011612D;
/*  21 */       this.motionZ *= 0.10000000149011612D;
/*     */     } 
/*     */     
/*  24 */     this.particleScale *= 0.75F;
/*  25 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTransparent() {
/*  30 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  35 */     this.prevPosX = this.posX;
/*  36 */     this.prevPosY = this.posY;
/*  37 */     this.prevPosZ = this.posZ;
/*     */     
/*  39 */     if (this.particleAge++ >= this.particleMaxAge)
/*     */     {
/*  41 */       setExpired();
/*     */     }
/*     */     
/*  44 */     setParticleTextureIndex(this.baseSpellTextureIndex + 7 - this.particleAge * 8 / this.particleMaxAge);
/*  45 */     this.motionY += 0.004D;
/*  46 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*     */     
/*  48 */     if (this.posY == this.prevPosY) {
/*     */       
/*  50 */       this.motionX *= 1.1D;
/*  51 */       this.motionZ *= 1.1D;
/*     */     } 
/*     */     
/*  54 */     this.motionX *= 0.9599999785423279D;
/*  55 */     this.motionY *= 0.9599999785423279D;
/*  56 */     this.motionZ *= 0.9599999785423279D;
/*     */     
/*  58 */     if (this.isCollided) {
/*     */       
/*  60 */       this.motionX *= 0.699999988079071D;
/*  61 */       this.motionZ *= 0.699999988079071D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBaseSpellTextureIndex(int baseSpellTextureIndexIn) {
/*  70 */     this.baseSpellTextureIndex = baseSpellTextureIndexIn;
/*     */   }
/*     */   
/*     */   public static class AmbientMobFactory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/*  77 */       Particle particle = new ParticleSpell(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*  78 */       particle.setAlphaF(0.15F);
/*  79 */       particle.setRBGColorF((float)xSpeedIn, (float)ySpeedIn, (float)zSpeedIn);
/*  80 */       return particle;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Factory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/*  88 */       return new ParticleSpell(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class InstantFactory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/*  96 */       Particle particle = new ParticleSpell(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*  97 */       ((ParticleSpell)particle).setBaseSpellTextureIndex(144);
/*  98 */       return particle;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class MobFactory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 106 */       Particle particle = new ParticleSpell(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 107 */       particle.setRBGColorF((float)xSpeedIn, (float)ySpeedIn, (float)zSpeedIn);
/* 108 */       return particle;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class WitchFactory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 116 */       Particle particle = new ParticleSpell(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 117 */       ((ParticleSpell)particle).setBaseSpellTextureIndex(144);
/* 118 */       float f = worldIn.rand.nextFloat() * 0.5F + 0.35F;
/* 119 */       particle.setRBGColorF(1.0F * f, 0.0F * f, 1.0F * f);
/* 120 */       return particle;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleSpell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */