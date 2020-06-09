/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleDragonBreath
/*    */   extends Particle
/*    */ {
/*    */   private final float oSize;
/*    */   private boolean hasHitGround;
/*    */   
/*    */   protected ParticleDragonBreath(World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
/* 15 */     super(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
/* 16 */     this.motionX = xSpeed;
/* 17 */     this.motionY = ySpeed;
/* 18 */     this.motionZ = zSpeed;
/* 19 */     this.particleRed = MathHelper.nextFloat(this.rand, 0.7176471F, 0.8745098F);
/* 20 */     this.particleGreen = MathHelper.nextFloat(this.rand, 0.0F, 0.0F);
/* 21 */     this.particleBlue = MathHelper.nextFloat(this.rand, 0.8235294F, 0.9764706F);
/* 22 */     this.particleScale *= 0.75F;
/* 23 */     this.oSize = this.particleScale;
/* 24 */     this.particleMaxAge = (int)(20.0D / (this.rand.nextFloat() * 0.8D + 0.2D));
/* 25 */     this.hasHitGround = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 30 */     this.prevPosX = this.posX;
/* 31 */     this.prevPosY = this.posY;
/* 32 */     this.prevPosZ = this.posZ;
/*    */     
/* 34 */     if (this.particleAge++ >= this.particleMaxAge) {
/*    */       
/* 36 */       setExpired();
/*    */     }
/*    */     else {
/*    */       
/* 40 */       setParticleTextureIndex(3 * this.particleAge / this.particleMaxAge + 5);
/*    */       
/* 42 */       if (this.isCollided) {
/*    */         
/* 44 */         this.motionY = 0.0D;
/* 45 */         this.hasHitGround = true;
/*    */       } 
/*    */       
/* 48 */       if (this.hasHitGround)
/*    */       {
/* 50 */         this.motionY += 0.002D;
/*    */       }
/*    */       
/* 53 */       moveEntity(this.motionX, this.motionY, this.motionZ);
/*    */       
/* 55 */       if (this.posY == this.prevPosY) {
/*    */         
/* 57 */         this.motionX *= 1.1D;
/* 58 */         this.motionZ *= 1.1D;
/*    */       } 
/*    */       
/* 61 */       this.motionX *= 0.9599999785423279D;
/* 62 */       this.motionZ *= 0.9599999785423279D;
/*    */       
/* 64 */       if (this.hasHitGround)
/*    */       {
/* 66 */         this.motionY *= 0.9599999785423279D;
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 76 */     this.particleScale = this.oSize * MathHelper.clamp((this.particleAge + partialTicks) / this.particleMaxAge * 32.0F, 0.0F, 1.0F);
/* 77 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 84 */       return new ParticleDragonBreath(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleDragonBreath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */