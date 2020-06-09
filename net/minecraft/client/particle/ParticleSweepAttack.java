/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleSweepAttack
/*    */   extends Particle {
/* 17 */   private static final ResourceLocation SWEEP_TEXTURE = new ResourceLocation("textures/entity/sweep.png");
/* 18 */   private static final VertexFormat VERTEX_FORMAT = (new VertexFormat()).addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B);
/*    */   
/*    */   private int life;
/*    */   private final int lifeTime;
/*    */   private final TextureManager textureManager;
/*    */   private final float size;
/*    */   
/*    */   protected ParticleSweepAttack(TextureManager textureManagerIn, World worldIn, double x, double y, double z, double p_i46582_9_, double p_i46582_11_, double p_i46582_13_) {
/* 26 */     super(worldIn, x, y, z, 0.0D, 0.0D, 0.0D);
/* 27 */     this.textureManager = textureManagerIn;
/* 28 */     this.lifeTime = 4;
/* 29 */     float f = this.rand.nextFloat() * 0.6F + 0.4F;
/* 30 */     this.particleRed = f;
/* 31 */     this.particleGreen = f;
/* 32 */     this.particleBlue = f;
/* 33 */     this.size = 1.0F - (float)p_i46582_9_ * 0.5F;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 41 */     int i = (int)((this.life + partialTicks) * 3.0F / this.lifeTime);
/*    */     
/* 43 */     if (i <= 7) {
/*    */       
/* 45 */       this.textureManager.bindTexture(SWEEP_TEXTURE);
/* 46 */       float f = (i % 4) / 4.0F;
/* 47 */       float f1 = f + 0.24975F;
/* 48 */       float f2 = (i / 2) / 2.0F;
/* 49 */       float f3 = f2 + 0.4995F;
/* 50 */       float f4 = 1.0F * this.size;
/* 51 */       float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/* 52 */       float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/* 53 */       float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/* 54 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 55 */       GlStateManager.disableLighting();
/* 56 */       RenderHelper.disableStandardItemLighting();
/* 57 */       worldRendererIn.begin(7, VERTEX_FORMAT);
/* 58 */       worldRendererIn.pos((f5 - rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4 * 0.5F), (f7 - rotationYZ * f4 - rotationXZ * f4)).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 59 */       worldRendererIn.pos((f5 - rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4 * 0.5F), (f7 - rotationYZ * f4 + rotationXZ * f4)).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 60 */       worldRendererIn.pos((f5 + rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4 * 0.5F), (f7 + rotationYZ * f4 + rotationXZ * f4)).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 61 */       worldRendererIn.pos((f5 + rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4 * 0.5F), (f7 + rotationYZ * f4 - rotationXZ * f4)).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 62 */       Tessellator.getInstance().draw();
/* 63 */       GlStateManager.enableLighting();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBrightnessForRender(float p_189214_1_) {
/* 69 */     return 61680;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 74 */     this.prevPosX = this.posX;
/* 75 */     this.prevPosY = this.posY;
/* 76 */     this.prevPosZ = this.posZ;
/* 77 */     this.life++;
/*    */     
/* 79 */     if (this.life == this.lifeTime)
/*    */     {
/* 81 */       setExpired();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFXLayer() {
/* 91 */     return 3;
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 98 */       return new ParticleSweepAttack(Minecraft.getMinecraft().getTextureManager(), worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleSweepAttack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */