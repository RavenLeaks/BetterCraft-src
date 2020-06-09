/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.BlockFalling;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.EnumBlockRenderType;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleFallingDust
/*    */   extends Particle
/*    */ {
/*    */   float oSize;
/*    */   final float rotSpeed;
/*    */   
/*    */   protected ParticleFallingDust(World p_i47135_1_, double p_i47135_2_, double p_i47135_4_, double p_i47135_6_, float p_i47135_8_, float p_i47135_9_, float p_i47135_10_) {
/* 23 */     super(p_i47135_1_, p_i47135_2_, p_i47135_4_, p_i47135_6_, 0.0D, 0.0D, 0.0D);
/* 24 */     this.motionX = 0.0D;
/* 25 */     this.motionY = 0.0D;
/* 26 */     this.motionZ = 0.0D;
/* 27 */     this.particleRed = p_i47135_8_;
/* 28 */     this.particleGreen = p_i47135_9_;
/* 29 */     this.particleBlue = p_i47135_10_;
/* 30 */     float f = 0.9F;
/* 31 */     this.particleScale *= 0.75F;
/* 32 */     this.particleScale *= 0.9F;
/* 33 */     this.oSize = this.particleScale;
/* 34 */     this.particleMaxAge = (int)(32.0D / (Math.random() * 0.8D + 0.2D));
/* 35 */     this.particleMaxAge = (int)(this.particleMaxAge * 0.9F);
/* 36 */     this.rotSpeed = ((float)Math.random() - 0.5F) * 0.1F;
/* 37 */     this.particleAngle = (float)Math.random() * 6.2831855F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 45 */     float f = (this.particleAge + partialTicks) / this.particleMaxAge * 32.0F;
/* 46 */     f = MathHelper.clamp(f, 0.0F, 1.0F);
/* 47 */     this.particleScale = this.oSize * f;
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
/* 62 */     this.prevParticleAngle = this.particleAngle;
/* 63 */     this.particleAngle += 3.1415927F * this.rotSpeed * 2.0F;
/*    */     
/* 65 */     if (this.isCollided)
/*    */     {
/* 67 */       this.prevParticleAngle = this.particleAngle = 0.0F;
/*    */     }
/*    */     
/* 70 */     setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);
/* 71 */     moveEntity(this.motionX, this.motionY, this.motionZ);
/* 72 */     this.motionY -= 0.003000000026077032D;
/* 73 */     this.motionY = Math.max(this.motionY, -0.14000000059604645D);
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     @Nullable
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 81 */       IBlockState iblockstate = Block.getStateById(p_178902_15_[0]);
/*    */       
/* 83 */       if (iblockstate.getBlock() != Blocks.AIR && iblockstate.getRenderType() == EnumBlockRenderType.INVISIBLE)
/*    */       {
/* 85 */         return null;
/*    */       }
/*    */ 
/*    */       
/* 89 */       int i = Minecraft.getMinecraft().getBlockColors().getColor(iblockstate, worldIn, new BlockPos(xCoordIn, yCoordIn, zCoordIn));
/*    */       
/* 91 */       if (iblockstate.getBlock() instanceof BlockFalling)
/*    */       {
/* 93 */         i = ((BlockFalling)iblockstate.getBlock()).getDustColor(iblockstate);
/*    */       }
/*    */       
/* 96 */       float f = (i >> 16 & 0xFF) / 255.0F;
/* 97 */       float f1 = (i >> 8 & 0xFF) / 255.0F;
/* 98 */       float f2 = (i & 0xFF) / 255.0F;
/* 99 */       return new ParticleFallingDust(worldIn, xCoordIn, yCoordIn, zCoordIn, f, f1, f2);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleFallingDust.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */