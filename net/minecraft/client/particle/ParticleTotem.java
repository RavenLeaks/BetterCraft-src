/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleTotem
/*    */   extends ParticleSimpleAnimated
/*    */ {
/*    */   public ParticleTotem(World p_i47220_1_, double p_i47220_2_, double p_i47220_4_, double p_i47220_6_, double p_i47220_8_, double p_i47220_10_, double p_i47220_12_) {
/*  9 */     super(p_i47220_1_, p_i47220_2_, p_i47220_4_, p_i47220_6_, 176, 8, -0.05F);
/* 10 */     this.motionX = p_i47220_8_;
/* 11 */     this.motionY = p_i47220_10_;
/* 12 */     this.motionZ = p_i47220_12_;
/* 13 */     this.particleScale *= 0.75F;
/* 14 */     this.particleMaxAge = 60 + this.rand.nextInt(12);
/*    */     
/* 16 */     if (this.rand.nextInt(4) == 0) {
/*    */       
/* 18 */       setRBGColorF(0.6F + this.rand.nextFloat() * 0.2F, 0.6F + this.rand.nextFloat() * 0.3F, this.rand.nextFloat() * 0.2F);
/*    */     }
/*    */     else {
/*    */       
/* 22 */       setRBGColorF(0.1F + this.rand.nextFloat() * 0.2F, 0.4F + this.rand.nextFloat() * 0.3F, this.rand.nextFloat() * 0.2F);
/*    */     } 
/*    */     
/* 25 */     func_191238_f(0.6F);
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 32 */       return new ParticleTotem(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleTotem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */