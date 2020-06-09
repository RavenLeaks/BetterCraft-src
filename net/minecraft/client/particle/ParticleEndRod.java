/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleEndRod
/*    */   extends ParticleSimpleAnimated
/*    */ {
/*    */   public ParticleEndRod(World p_i46580_1_, double p_i46580_2_, double p_i46580_4_, double p_i46580_6_, double p_i46580_8_, double p_i46580_10_, double p_i46580_12_) {
/*  9 */     super(p_i46580_1_, p_i46580_2_, p_i46580_4_, p_i46580_6_, 176, 8, -5.0E-4F);
/* 10 */     this.motionX = p_i46580_8_;
/* 11 */     this.motionY = p_i46580_10_;
/* 12 */     this.motionZ = p_i46580_12_;
/* 13 */     this.particleScale *= 0.75F;
/* 14 */     this.particleMaxAge = 60 + this.rand.nextInt(12);
/* 15 */     setColorFade(15916745);
/*    */   }
/*    */ 
/*    */   
/*    */   public void moveEntity(double x, double y, double z) {
/* 20 */     setEntityBoundingBox(getEntityBoundingBox().offset(x, y, z));
/* 21 */     resetPositionToBB();
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 28 */       return new ParticleEndRod(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleEndRod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */