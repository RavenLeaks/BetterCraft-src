/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ParticleDigging
/*     */   extends Particle {
/*     */   private final IBlockState sourceState;
/*     */   private BlockPos sourcePos;
/*     */   
/*     */   protected ParticleDigging(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, IBlockState state) {
/*  20 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*  21 */     this.sourceState = state;
/*  22 */     setParticleTexture(Minecraft.getMinecraft().getBlockRendererDispatcher().getBlockModelShapes().getTexture(state));
/*  23 */     this.particleGravity = (state.getBlock()).blockParticleGravity;
/*  24 */     this.particleRed = 0.6F;
/*  25 */     this.particleGreen = 0.6F;
/*  26 */     this.particleBlue = 0.6F;
/*  27 */     this.particleScale /= 2.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticleDigging setBlockPos(BlockPos pos) {
/*  35 */     this.sourcePos = pos;
/*     */     
/*  37 */     if (this.sourceState.getBlock() == Blocks.GRASS)
/*     */     {
/*  39 */       return this;
/*     */     }
/*     */ 
/*     */     
/*  43 */     multiplyColor(pos);
/*  44 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ParticleDigging init() {
/*  50 */     this.sourcePos = new BlockPos(this.posX, this.posY, this.posZ);
/*  51 */     Block block = this.sourceState.getBlock();
/*     */     
/*  53 */     if (block == Blocks.GRASS)
/*     */     {
/*  55 */       return this;
/*     */     }
/*     */ 
/*     */     
/*  59 */     multiplyColor(this.sourcePos);
/*  60 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void multiplyColor(@Nullable BlockPos p_187154_1_) {
/*  66 */     int i = Minecraft.getMinecraft().getBlockColors().colorMultiplier(this.sourceState, (IBlockAccess)this.worldObj, p_187154_1_, 0);
/*  67 */     this.particleRed *= (i >> 16 & 0xFF) / 255.0F;
/*  68 */     this.particleGreen *= (i >> 8 & 0xFF) / 255.0F;
/*  69 */     this.particleBlue *= (i & 0xFF) / 255.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/*  78 */     return 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/*  86 */     float f = (this.particleTextureIndexX + this.particleTextureJitterX / 4.0F) / 16.0F;
/*  87 */     float f1 = f + 0.015609375F;
/*  88 */     float f2 = (this.particleTextureIndexY + this.particleTextureJitterY / 4.0F) / 16.0F;
/*  89 */     float f3 = f2 + 0.015609375F;
/*  90 */     float f4 = 0.1F * this.particleScale;
/*     */     
/*  92 */     if (this.particleTexture != null) {
/*     */       
/*  94 */       f = this.particleTexture.getInterpolatedU((this.particleTextureJitterX / 4.0F * 16.0F));
/*  95 */       f1 = this.particleTexture.getInterpolatedU(((this.particleTextureJitterX + 1.0F) / 4.0F * 16.0F));
/*  96 */       f2 = this.particleTexture.getInterpolatedV((this.particleTextureJitterY / 4.0F * 16.0F));
/*  97 */       f3 = this.particleTexture.getInterpolatedV(((this.particleTextureJitterY + 1.0F) / 4.0F * 16.0F));
/*     */     } 
/*     */     
/* 100 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/* 101 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/* 102 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/* 103 */     int i = getBrightnessForRender(partialTicks);
/* 104 */     int j = i >> 16 & 0xFFFF;
/* 105 */     int k = i & 0xFFFF;
/* 106 */     worldRendererIn.pos((f5 - rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 - rotationYZ * f4 - rotationXZ * f4)).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/* 107 */     worldRendererIn.pos((f5 - rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 - rotationYZ * f4 + rotationXZ * f4)).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/* 108 */     worldRendererIn.pos((f5 + rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 + rotationYZ * f4 + rotationXZ * f4)).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/* 109 */     worldRendererIn.pos((f5 + rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 + rotationYZ * f4 - rotationXZ * f4)).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float p_189214_1_) {
/* 114 */     int i = super.getBrightnessForRender(p_189214_1_);
/* 115 */     int j = 0;
/*     */     
/* 117 */     if (this.worldObj.isBlockLoaded(this.sourcePos))
/*     */     {
/* 119 */       j = this.worldObj.getCombinedLight(this.sourcePos, 0);
/*     */     }
/*     */     
/* 122 */     return (i == 0) ? j : i;
/*     */   }
/*     */   
/*     */   public static class Factory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 129 */       return (new ParticleDigging(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn, Block.getStateById(p_178902_15_[0]))).init();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleDigging.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */