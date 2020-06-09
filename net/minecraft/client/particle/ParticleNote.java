/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleNote
/*    */   extends Particle
/*    */ {
/*    */   float noteParticleScale;
/*    */   
/*    */   protected ParticleNote(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i46353_8_, double p_i46353_10_, double p_i46353_12_) {
/* 14 */     this(worldIn, xCoordIn, yCoordIn, zCoordIn, p_i46353_8_, p_i46353_10_, p_i46353_12_, 2.0F);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ParticleNote(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1217_8_, double p_i1217_10_, double p_i1217_12_, float p_i1217_14_) {
/* 19 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 20 */     this.motionX *= 0.009999999776482582D;
/* 21 */     this.motionY *= 0.009999999776482582D;
/* 22 */     this.motionZ *= 0.009999999776482582D;
/* 23 */     this.motionY += 0.2D;
/* 24 */     this.particleRed = MathHelper.sin(((float)p_i1217_8_ + 0.0F) * 6.2831855F) * 0.65F + 0.35F;
/* 25 */     this.particleGreen = MathHelper.sin(((float)p_i1217_8_ + 0.33333334F) * 6.2831855F) * 0.65F + 0.35F;
/* 26 */     this.particleBlue = MathHelper.sin(((float)p_i1217_8_ + 0.6666667F) * 6.2831855F) * 0.65F + 0.35F;
/* 27 */     this.particleScale *= 0.75F;
/* 28 */     this.particleScale *= p_i1217_14_;
/* 29 */     this.noteParticleScale = this.particleScale;
/* 30 */     this.particleMaxAge = 6;
/* 31 */     setParticleTextureIndex(64);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 39 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/* 40 */     f = MathHelper.clamp(f, 0.0F, 1.0F);
/* 41 */     this.particleScale = this.noteParticleScale * f;
/* 42 */     super.renderParticle(worldRendererIn, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 47 */     this.prevPosX = this.posX;
/* 48 */     this.prevPosY = this.posY;
/* 49 */     this.prevPosZ = this.posZ;
/*    */     
/* 51 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 53 */       setExpired();
/*    */     }
/*    */     
/* 56 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/*    */     
/* 58 */     if (this.posY == this.prevPosY) {
/*    */       
/* 60 */       this.motionX *= 1.1D;
/* 61 */       this.motionZ *= 1.1D;
/*    */     } 
/*    */     
/* 64 */     this.motionX *= 0.6600000262260437D;
/* 65 */     this.motionY *= 0.6600000262260437D;
/* 66 */     this.motionZ *= 0.6600000262260437D;
/*    */     
/* 68 */     if (this.isCollided) {
/*    */       
/* 70 */       this.motionX *= 0.699999988079071D;
/* 71 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 79 */       return new ParticleNote(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleNote.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */