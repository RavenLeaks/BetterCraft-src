/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderSpider;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import optifine.Config;
/*    */ import shadersmod.client.Shaders;
/*    */ 
/*    */ public class LayerSpiderEyes<T extends EntitySpider> implements LayerRenderer<T> {
/* 14 */   private static final ResourceLocation SPIDER_EYES = new ResourceLocation("textures/entity/spider_eyes.png");
/*    */   
/*    */   private final RenderSpider<T> spiderRenderer;
/*    */   
/*    */   public LayerSpiderEyes(RenderSpider<T> spiderRendererIn) {
/* 19 */     this.spiderRenderer = spiderRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(T entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 24 */     this.spiderRenderer.bindTexture(SPIDER_EYES);
/* 25 */     GlStateManager.enableBlend();
/* 26 */     GlStateManager.disableAlpha();
/* 27 */     GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
/*    */     
/* 29 */     if (entitylivingbaseIn.isInvisible()) {
/*    */       
/* 31 */       GlStateManager.depthMask(false);
/*    */     }
/*    */     else {
/*    */       
/* 35 */       GlStateManager.depthMask(true);
/*    */     } 
/*    */     
/* 38 */     int i = 61680;
/* 39 */     int j = i % 65536;
/* 40 */     int k = i / 65536;
/* 41 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/* 42 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 43 */     (Minecraft.getMinecraft()).entityRenderer.func_191514_d(true);
/*    */     
/* 45 */     if (Config.isShaders())
/*    */     {
/* 47 */       Shaders.beginSpiderEyes();
/*    */     }
/*    */     
/* 50 */     (Config.getRenderGlobal()).renderOverlayEyes = true;
/* 51 */     this.spiderRenderer.getMainModel().render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/* 52 */     (Config.getRenderGlobal()).renderOverlayEyes = false;
/*    */     
/* 54 */     if (Config.isShaders())
/*    */     {
/* 56 */       Shaders.endSpiderEyes();
/*    */     }
/*    */     
/* 59 */     (Minecraft.getMinecraft()).entityRenderer.func_191514_d(false);
/* 60 */     i = entitylivingbaseIn.getBrightnessForRender();
/* 61 */     j = i % 65536;
/* 62 */     k = i / 65536;
/* 63 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/* 64 */     this.spiderRenderer.setLightmap((EntityLiving)entitylivingbaseIn);
/* 65 */     GlStateManager.disableBlend();
/* 66 */     GlStateManager.enableAlpha();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 71 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerSpiderEyes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */