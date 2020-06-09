/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.EnumBlockRenderType;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleBlockDust
/*    */   extends ParticleDigging
/*    */ {
/*    */   protected ParticleBlockDust(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, IBlockState state) {
/* 13 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, state);
/* 14 */     this.motionX = xSpeedIn;
/* 15 */     this.motionY = ySpeedIn;
/* 16 */     this.motionZ = zSpeedIn;
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     @Nullable
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 24 */       IBlockState iblockstate = Block.getStateById(p_178902_15_[0]);
/* 25 */       return (iblockstate.getRenderType() == EnumBlockRenderType.INVISIBLE) ? null : (new ParticleBlockDust(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, iblockstate)).init();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleBlockDust.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */