/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleBubble
/*    */   extends Particle
/*    */ {
/*    */   protected ParticleBubble(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/* 11 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 12 */     this.particleRed = 1.0F;
/* 13 */     this.particleGreen = 1.0F;
/* 14 */     this.particleBlue = 1.0F;
/* 15 */     setParticleTextureIndex(32);
/* 16 */     setSize(0.02F, 0.02F);
/* 17 */     this.particleScale *= this.rand.nextFloat() * 0.6F + 0.2F;
/* 18 */     this.motionX = xSpeedIn * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
/* 19 */     this.motionY = ySpeedIn * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
/* 20 */     this.motionZ = zSpeedIn * 0.20000000298023224D + (Math.random() * 2.0D - 1.0D) * 0.019999999552965164D;
/* 21 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 26 */     this.prevPosX = this.posX;
/* 27 */     this.prevPosY = this.posY;
/* 28 */     this.prevPosZ = this.posZ;
/* 29 */     this.motionY += 0.002D;
/* 30 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 31 */     this.motionX *= 0.8500000238418579D;
/* 32 */     this.motionY *= 0.8500000238418579D;
/* 33 */     this.motionZ *= 0.8500000238418579D;
/*    */     
/* 35 */     if (this.worldObj.getBlockState(new BlockPos(this.posX, this.posY, this.posZ)).getMaterial() != Material.WATER)
/*    */     {
/* 37 */       setExpired();
/*    */     }
/*    */     
/* 40 */     if (this.particleMaxAge-- <= 0)
/*    */     {
/* 42 */       setExpired();
/*    */     }
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 50 */       return new ParticleBubble(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleBubble.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */