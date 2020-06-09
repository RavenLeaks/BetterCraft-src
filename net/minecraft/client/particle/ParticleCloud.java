/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.math.AxisAlignedBB;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleCloud
/*    */   extends Particle
/*    */ {
/*    */   float oSize;
/*    */   
/*    */   protected ParticleCloud(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1221_8_, double p_i1221_10_, double p_i1221_12_) {
/* 16 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 17 */     float f = 2.5F;
/* 18 */     this.motionX *= 0.10000000149011612D;
/* 19 */     this.motionY *= 0.10000000149011612D;
/* 20 */     this.motionZ *= 0.10000000149011612D;
/* 21 */     this.motionX += p_i1221_8_;
/* 22 */     this.motionY += p_i1221_10_;
/* 23 */     this.motionZ += p_i1221_12_;
/* 24 */     float f1 = 1.0F - (float)(Math.random() * 0.30000001192092896D);
/* 25 */     this.particleRed = f1;
/* 26 */     this.particleGreen = f1;
/* 27 */     this.particleBlue = f1;
/* 28 */     this.particleScale *= 0.75F;
/* 29 */     this.particleScale *= 2.5F;
/* 30 */     this.oSize = this.particleScale;
/* 31 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.3D));
/* 32 */     this.particleMaxAge = (int)(this.particleMaxAge * 2.5F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 40 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/* 41 */     f = MathHelper.clamp(f, 0.0F, 1.0F);
/* 42 */     this.particleScale = this.oSize * f;
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
/* 57 */     setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
/* 58 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 59 */     this.motionX *= 0.9599999785423279D;
/* 60 */     this.motionY *= 0.9599999785423279D;
/* 61 */     this.motionZ *= 0.9599999785423279D;
/* 62 */     EntityPlayer entityplayer = this.worldObj.getClosestPlayer(this.posX, this.posY, this.posZ, 2.0D, false);
/*    */     
/* 64 */     if (entityplayer != null) {
/*    */       
/* 66 */       AxisAlignedBB axisalignedbb = entityplayer.getEntityBoundingBox();
/*    */       
/* 68 */       if (this.posY > axisalignedbb.minY) {
/*    */         
/* 70 */         this.posY += (axisalignedbb.minY - this.posY) * 0.2D;
/* 71 */         this.motionY += (entityplayer.motionY - this.motionY) * 0.2D;
/* 72 */         setPosition(this.posX, this.posY, this.posZ);
/*    */       } 
/*    */     } 
/*    */     
/* 76 */     if (this.isCollided) {
/*    */       
/* 78 */       this.motionX *= 0.699999988079071D;
/* 79 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 87 */       return new ParticleCloud(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleCloud.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */