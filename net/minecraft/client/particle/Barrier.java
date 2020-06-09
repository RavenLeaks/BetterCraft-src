/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class Barrier
/*    */   extends Particle
/*    */ {
/*    */   protected Barrier(World worldIn, double p_i46286_2_, double p_i46286_4_, double p_i46286_6_, Item p_i46286_8_) {
/* 14 */     super(worldIn, p_i46286_2_, p_i46286_4_, p_i46286_6_, 0.0D, 0.0D, 0.0D);
/* 15 */     setParticleTexture(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(p_i46286_8_));
/* 16 */     this.particleRed = 1.0F;
/* 17 */     this.particleGreen = 1.0F;
/* 18 */     this.particleBlue = 1.0F;
/* 19 */     this.motionX = 0.0D;
/* 20 */     this.motionY = 0.0D;
/* 21 */     this.motionZ = 0.0D;
/* 22 */     this.particleGravity = 0.0F;
/* 23 */     this.particleMaxAge = 80;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFXLayer() {
/* 32 */     return 1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 40 */     float f = this.particleTexture.getMinU();
/* 41 */     float f1 = this.particleTexture.getMaxU();
/* 42 */     float f2 = this.particleTexture.getMinV();
/* 43 */     float f3 = this.particleTexture.getMaxV();
/* 44 */     float f4 = 0.5F;
/* 45 */     float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/* 46 */     float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/* 47 */     float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/* 48 */     int i = getBrightnessForRender(partialTicks);
/* 49 */     int j = i >> 16 & 0xFFFF;
/* 50 */     int k = i & 0xFFFF;
/* 51 */     worldRendererIn.pos((f5 - rotationX * 0.5F - rotationXY * 0.5F), (f6 - rotationZ * 0.5F), (f7 - rotationYZ * 0.5F - rotationXZ * 0.5F)).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/* 52 */     worldRendererIn.pos((f5 - rotationX * 0.5F + rotationXY * 0.5F), (f6 + rotationZ * 0.5F), (f7 - rotationYZ * 0.5F + rotationXZ * 0.5F)).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/* 53 */     worldRendererIn.pos((f5 + rotationX * 0.5F + rotationXY * 0.5F), (f6 + rotationZ * 0.5F), (f7 + rotationYZ * 0.5F + rotationXZ * 0.5F)).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/* 54 */     worldRendererIn.pos((f5 + rotationX * 0.5F - rotationXY * 0.5F), (f6 - rotationZ * 0.5F), (f7 + rotationYZ * 0.5F - rotationXZ * 0.5F)).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(j, k).endVertex();
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 61 */       return new Barrier(worldIn, xCoordIn, yCoordIn, zCoordIn, Item.getItemFromBlock(Blocks.BARRIER));
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\Barrier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */