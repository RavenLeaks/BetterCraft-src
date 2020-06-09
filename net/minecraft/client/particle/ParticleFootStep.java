/*    */ package net.minecraft.client.particle;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BufferBuilder;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.Tessellator;
/*    */ import net.minecraft.client.renderer.texture.TextureManager;
/*    */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class ParticleFootStep
/*    */   extends Particle {
/* 16 */   private static final ResourceLocation FOOTPRINT_TEXTURE = new ResourceLocation("textures/particle/footprint.png");
/*    */   
/*    */   private int footstepAge;
/*    */   private final int footstepMaxAge;
/*    */   private final TextureManager currentFootSteps;
/*    */   
/*    */   protected ParticleFootStep(TextureManager currentFootStepsIn, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn) {
/* 23 */     super(worldIn, xCoordIn, yCoordIn, zCoordIn, 0.0D, 0.0D, 0.0D);
/* 24 */     this.currentFootSteps = currentFootStepsIn;
/* 25 */     this.motionX = 0.0D;
/* 26 */     this.motionY = 0.0D;
/* 27 */     this.motionZ = 0.0D;
/* 28 */     this.footstepMaxAge = 200;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void renderParticle(BufferBuilder worldRendererIn, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
/* 36 */     float f = (this.footstepAge + partialTicks) / this.footstepMaxAge;
/* 37 */     f *= f;
/* 38 */     float f1 = 2.0F - f * 2.0F;
/*    */     
/* 40 */     if (f1 > 1.0F)
/*    */     {
/* 42 */       f1 = 1.0F;
/*    */     }
/*    */     
/* 45 */     f1 *= 0.2F;
/* 46 */     GlStateManager.disableLighting();
/* 47 */     float f2 = 0.125F;
/* 48 */     float f3 = (float)(this.posX - interpPosX);
/* 49 */     float f4 = (float)(this.posY - interpPosY);
/* 50 */     float f5 = (float)(this.posZ - interpPosZ);
/* 51 */     float f6 = this.worldObj.getLightBrightness(new BlockPos(this.posX, this.posY, this.posZ));
/* 52 */     this.currentFootSteps.bindTexture(FOOTPRINT_TEXTURE);
/* 53 */     GlStateManager.enableBlend();
/* 54 */     GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 55 */     worldRendererIn.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 56 */     worldRendererIn.pos((f3 - 0.125F), f4, (f5 + 0.125F)).tex(0.0D, 1.0D).color(f6, f6, f6, f1).endVertex();
/* 57 */     worldRendererIn.pos((f3 + 0.125F), f4, (f5 + 0.125F)).tex(1.0D, 1.0D).color(f6, f6, f6, f1).endVertex();
/* 58 */     worldRendererIn.pos((f3 + 0.125F), f4, (f5 - 0.125F)).tex(1.0D, 0.0D).color(f6, f6, f6, f1).endVertex();
/* 59 */     worldRendererIn.pos((f3 - 0.125F), f4, (f5 - 0.125F)).tex(0.0D, 0.0D).color(f6, f6, f6, f1).endVertex();
/* 60 */     Tessellator.getInstance().draw();
/* 61 */     GlStateManager.disableBlend();
/* 62 */     GlStateManager.enableLighting();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 67 */     this.footstepAge++;
/*    */     
/* 69 */     if (this.footstepAge == this.footstepMaxAge)
/*    */     {
/* 71 */       setExpired();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getFXLayer() {
/* 81 */     return 3;
/*    */   }
/*    */   
/*    */   public static class Factory
/*    */     implements IParticleFactory
/*    */   {
/*    */     public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_) {
/* 88 */       return new ParticleFootStep(Minecraft.getMinecraft().getTextureManager(), worldIn, xCoordIn, yCoordIn, zCoordIn);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\particle\ParticleFootStep.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */