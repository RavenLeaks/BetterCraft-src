/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleRedstone
/*    */   extends Particle
/*    */ {
/*    */   float reddustParticleScale;
/*    */   
/*    */   protected ParticleRedstone(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float p_i46349_8_, float p_i46349_9_, float p_i46349_10_) {
/* 14 */     this(worldIn, xCoordIn, yCoordIn, zCoordIn, 1.0F, p_i46349_8_, p_i46349_9_, p_i46349_10_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ParticleRedstone(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, float p_i46350_8_, float p_i46350_9_, float p_i46350_10_, float p_i46350_11_) {
/* 19 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 20 */     this.motionX *= 0.10000000149011612D;
/* 21 */     this.motionY *= 0.10000000149011612D;
/* 22 */     this.motionZ *= 0.10000000149011612D;
/*    */     
/* 24 */     if (p_i46350_9_ == 0.0F)
/*    */     {
/* 26 */       p_i46350_9_ = 1.0F;
/*    */     }
/*    */     
/* 29 */     float f = (float)Math.random() * 0.4F + 0.6F;
/* 30 */     this.particleRed = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * p_i46350_9_ * f;
/* 31 */     this.particleGreen = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * p_i46350_10_ * f;
/* 32 */     this.particleBlue = ((float)(Math.random() * 0.20000000298023224D) + 0.8F) * p_i46350_11_ * f;
/* 33 */     this.particleScale *= 0.75F;
/* 34 */     this.particleScale *= p_i46350_8_;
/* 35 */     this.reddustParticleScale = this.particleScale;
/* 36 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/* 37 */     this.particleMaxAge = (int)(this.particleMaxAge * p_i46350_8_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 45 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/* 46 */     f = MathHelper.clamp(f, 0.0F, 1.0F);
/* 47 */     this.particleScale = this.reddustParticleScale * f;
/* 48 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 53 */     this.prevPosX = this.posX;
/* 54 */     this.prevPosY = this.posY;
/* 55 */     this.prevPosZ = this.posZ;
/*    */     
/* 57 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 59 */       setExpired();
/*    */     }
/*    */     
/* 62 */     setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
/* 63 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*    */     
/* 65 */     if (this.posY == this.prevPosY) {
/*    */       
/* 67 */       this.motionX *= 1.1D;
/* 68 */       this.motionZ *= 1.1D;
/*    */     } 
/*    */     
/* 71 */     this.motionX *= 0.9599999785423279D;
/* 72 */     this.motionY *= 0.9599999785423279D;
/* 73 */     this.motionZ *= 0.9599999785423279D;
/*    */     
/* 75 */     if (this.isCollided) {
/*    */       
/* 77 */       this.motionX *= 0.699999988079071D;
/* 78 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 86 */       return new ParticleRedstone(worldIn, xCoordIn, yCoordIn, zCoordIn, (float)xSpeedIn, (float)ySpeedIn, (float)zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleRedstone.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */