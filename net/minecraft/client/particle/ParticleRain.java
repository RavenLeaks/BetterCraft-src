/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.block.BlockLiquid;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleRain extends Particle {
/*    */   protected ParticleRain(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
/* 14 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 15 */     this.motionX *= 0.30000001192092896D;
/* 16 */     this.motionY = Math.random() * 0.20000000298023224D + 0.10000000149011612D;
/* 17 */     this.motionZ *= 0.30000001192092896D;
/* 18 */     this.particleRed = 1.0F;
/* 19 */     this.particleGreen = 1.0F;
/* 20 */     this.particleBlue = 1.0F;
/* 21 */     setParticleTextureIndex(19 + this.rand.nextInt(4));
/* 22 */     setSize(0.01F, 0.01F);
/* 23 */     this.particleGravity = 0.06F;
/* 24 */     this.particleMaxAge = (int)(8.0D / (Math.random() * 0.8D + 0.2D));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 29 */     this.prevPosX = this.posX;
/* 30 */     this.prevPosY = this.posY;
/* 31 */     this.prevPosZ = this.posZ;
/* 32 */     this.motionY -= this.particleGravity;
/* 33 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 34 */     this.motionX *= 0.9800000190734863D;
/* 35 */     this.motionY *= 0.9800000190734863D;
/* 36 */     this.motionZ *= 0.9800000190734863D;
/*    */     
/* 38 */     if (this.particleMaxAge-- <= 0)
/*    */     {
/* 40 */       setExpired();
/*    */     }
/*    */     
/* 43 */     if (this.isCollided) {
/*    */       
/* 45 */       if (Math.random() < 0.5D)
/*    */       {
/* 47 */         setExpired();
/*    */       }
/*    */       
/* 50 */       this.motionX *= 0.699999988079071D;
/* 51 */       this.motionZ *= 0.699999988079071D;
/*    */     } 
/*    */     
/* 54 */     BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
/* 55 */     IBlockState iblockstate = this.worldObj.getBlockState(blockpos);
/* 56 */     Material material = iblockstate.getMaterial();
/*    */     
/* 58 */     if (material.isLiquid() || material.isSolid()) {
/*    */       double d0;
/*    */ 
/*    */       
/* 62 */       if (iblockstate.getBlock() instanceof BlockLiquid) {
/*    */         
/* 64 */         d0 = (1.0F - BlockLiquid.getLiquidHeightPercent(((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue()));
/*    */       }
/*    */       else {
/*    */         
/* 68 */         d0 = (iblockstate.getBoundingBox((IBlockAccess)this.worldObj, blockpos)).maxY;
/*    */       } 
/*    */       
/* 71 */       double d1 = MathHelper.floor(this.posY) + d0;
/*    */       
/* 73 */       if (this.posY < d1)
/*    */       {
/* 75 */         setExpired();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 84 */       return new ParticleRain(worldIn, xCoordIn, yCoordIn, zCoordIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleRain.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */