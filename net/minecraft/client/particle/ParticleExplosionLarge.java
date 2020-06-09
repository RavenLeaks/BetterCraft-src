/*     */ package net.minecraft.client.particle;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BufferBuilder;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.texture.TextureManager;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ParticleExplosionLarge
/*     */   extends Particle {
/*  17 */   private static final ResourceLocation EXPLOSION_TEXTURE = new ResourceLocation("textures/entity/explosion.png");
/*  18 */   private static final VertexFormat VERTEX_FORMAT = (new VertexFormat()).addElement(DefaultVertexFormats.POSITION_3F).addElement(DefaultVertexFormats.TEX_2F).addElement(DefaultVertexFormats.COLOR_4UB).addElement(DefaultVertexFormats.TEX_2S).addElement(DefaultVertexFormats.NORMAL_3B).addElement(DefaultVertexFormats.PADDING_1B);
/*     */   
/*     */   private int life;
/*     */   
/*     */   private final int lifeTime;
/*     */   
/*     */   private final TextureManager theRenderEngine;
/*     */   private final float size;
/*     */   
/*     */   protected ParticleExplosionLarge(TextureManager renderEngine, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double p_i1213_9_, double p_i1213_11_, double p_i1213_13_) {
/*  28 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/*  29 */     this.theRenderEngine = renderEngine;
/*  30 */     this.lifeTime = 6 + this.rand.nextInt(4);
/*  31 */     float f = this.rand.nextFloat() * 0.6F + 0.4F;
/*  32 */     this.particleRed = f;
/*  33 */     this.particleGreen = f;
/*  34 */     this.particleBlue = f;
/*  35 */     this.size = 1.0F - (float)p_i1213_9_ * 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/*  43 */     int i = (int)((this.life + partialTicks) * 15.0F / this.lifeTime);
/*     */     
/*  45 */     if (i <= 15) {
/*     */       
/*  47 */       this.theRenderEngine.bindTexture(EXPLOSION_TEXTURE);
/*  48 */       float f = (i % 4) / 4.0F;
/*  49 */       float f1 = f + 0.24975F;
/*  50 */       float f2 = (i / 4) / 4.0F;
/*  51 */       float f3 = f2 + 0.24975F;
/*  52 */       float f4 = 2.0F * this.size;
/*  53 */       float f5 = (float)(this.prevPosX + (this.posX - this.prevPosX) * partialTicks - interpPosX);
/*  54 */       float f6 = (float)(this.prevPosY + (this.posY - this.prevPosY) * partialTicks - interpPosY);
/*  55 */       float f7 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks - interpPosZ);
/*  56 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  57 */       GlStateManager.disableLighting();
/*  58 */       RenderHelper.disableStandardItemLighting();
/*  59 */       worldRendererIn.begin(7, VERTEX_FORMAT);
/*  60 */       worldRendererIn.pos((f5 - rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 - rotationYZ * f4 - rotationXZ * f4)).tex(f1, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  61 */       worldRendererIn.pos((f5 - rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 - rotationYZ * f4 + rotationXZ * f4)).tex(f1, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  62 */       worldRendererIn.pos((f5 + rotationX * f4 + rotationXY * f4), (f6 + rotationZ * f4), (f7 + rotationYZ * f4 + rotationXZ * f4)).tex(f, f2).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  63 */       worldRendererIn.pos((f5 + rotationX * f4 - rotationXY * f4), (f6 - rotationZ * f4), (f7 + rotationYZ * f4 - rotationXZ * f4)).tex(f, f3).color(this.particleRed, this.particleGreen, this.particleBlue, 1.0F).lightmap(0, 240).normal(0.0F, 1.0F, 0.0F).endVertex();
/*  64 */       Tessellator.getInstance().draw();
/*  65 */       GlStateManager.enableLighting();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender(float p_189214_1_) {
/*  71 */     return 61680;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  76 */     this.prevPosX = this.posX;
/*  77 */     this.prevPosY = this.posY;
/*  78 */     this.prevPosZ = this.posZ;
/*  79 */     this.life++;
/*     */     
/*  81 */     if (this.life == this.lifeTime)
/*     */     {
/*  83 */       setExpired();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFXLayer() {
/*  93 */     return 3;
/*     */   }
/*     */   
/*     */   public static class Factory
/*     */     implements IParticleFactory
/*     */   {
/*     */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 100 */       return new ParticleExplosionLarge(Minecraft.getMinecraft().getTextureManager(), worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleExplosionLarge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */