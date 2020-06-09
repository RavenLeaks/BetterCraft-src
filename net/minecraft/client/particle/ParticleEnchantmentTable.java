/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleEnchantmentTable
/*    */   extends Particle
/*    */ {
/*    */   private final float oSize;
/*    */   private final double coordX;
/*    */   private final double coordY;
/*    */   private final double coordZ;
/*    */   
/*    */   protected ParticleEnchantmentTable(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
/* 14 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/* 15 */     this.motionX = xSpeedIn;
/* 16 */     this.motionY = ySpeedIn;
/* 17 */     this.motionZ = zSpeedIn;
/* 18 */     this.coordX = xCoordIn;
/* 19 */     this.coordY = yCoordIn;
/* 20 */     this.coordZ = zCoordIn;
/* 21 */     this.prevPosX = xCoordIn + xSpeedIn;
/* 22 */     this.prevPosY = yCoordIn + ySpeedIn;
/* 23 */     this.prevPosZ = zCoordIn + zSpeedIn;
/* 24 */     this.posX = this.prevPosX;
/* 25 */     this.posY = this.prevPosY;
/* 26 */     this.posZ = this.prevPosZ;
/* 27 */     float f = this.rand.nextFloat() * 0.6F + 0.4F;
/* 28 */     this.particleScale = this.rand.nextFloat() * 0.5F + 0.2F;
/* 29 */     this.oSize = this.particleScale;
/* 30 */     this.particleRed = 0.9F * f;
/* 31 */     this.particleGreen = 0.9F * f;
/* 32 */     this.particleBlue = f;
/* 33 */     this.particleMaxAge = (int)(Math.random() * 10.0D) + 30;
/* 34 */     setParticleTextureIndex((int)(Math.random() * 26.0D + 1.0D + 224.0D));
/*    */   }
/*    */ 
/*    */   
/*    */   public void moveEntity(double x, double y, double z) {
/* 39 */     setEntityBoundingBox(getEntityBoundingBox().offset(x, y, z));
/* 40 */     resetPositionToBB();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBrightnessForRender(float p_189214_1_) {
/* 45 */     int i = super.getBrightnessForRender(p_189214_1_);
/* 46 */     float f = this.particleAge / this.particleMaxAge;
/* 47 */     f *= f;
/* 48 */     f *= f;
/* 49 */     int j = i & 0xFF;
/* 50 */     int k = i >> 16 & 0xFF;
/* 51 */     k += (int)(f * 15.0F * 16.0F);
/*    */     
/* 53 */     if (k > 240)
/*    */     {
/* 55 */       k = 240;
/*    */     }
/*    */     
/* 58 */     return j | k << 16;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 63 */     this.prevPosX = this.posX;
/* 64 */     this.prevPosY = this.posY;
/* 65 */     this.prevPosZ = this.posZ;
/* 66 */     float f = this.particleAge / this.particleMaxAge;
/* 67 */     f = 1.0F - f;
/* 68 */     float f1 = 1.0F - f;
/* 69 */     f1 *= f1;
/* 70 */     f1 *= f1;
/* 71 */     this.posX = this.coordX + this.motionX * f;
/* 72 */     this.posY = this.coordY + this.motionY * f - (f1 * 1.2F);
/* 73 */     this.posZ = this.coordZ + this.motionZ * f;
/*    */     
/* 75 */     if (this.particleAge++ >= this.particleMaxAge)
/*    */     {
/* 77 */       setExpired();
/*    */     }
/*    */   }
/*    */   
/*    */   public static class EnchantmentTable
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 85 */       return new ParticleEnchantmentTable(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleEnchantmentTable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */