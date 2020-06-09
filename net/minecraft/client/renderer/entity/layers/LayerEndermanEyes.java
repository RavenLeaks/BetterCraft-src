/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderEnderman;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import optifine.Config;
/*    */ import shadersmod.client.Shaders;
/*    */ 
/*    */ public class LayerEndermanEyes implements LayerRenderer<EntityEnderman> {
/* 14 */   private static final ResourceLocation RES_ENDERMAN_EYES = new ResourceLocation("textures/entity/enderman/enderman_eyes.png");
/*    */   
/*    */   private final RenderEnderman endermanRenderer;
/*    */   
/*    */   public LayerEndermanEyes(RenderEnderman endermanRendererIn) {
/* 19 */     this.endermanRenderer = endermanRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityEnderman entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 24 */     this.endermanRenderer.bindTexture(RES_ENDERMAN_EYES);
/* 25 */     GlStateManager.enableBlend();
/* 26 */     GlStateManager.disableAlpha();
/* 27 */     GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
/* 28 */     GlStateManager.disableLighting();
/* 29 */     GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
/* 30 */     int i = 61680;
/* 31 */     int j = 61680;
/* 32 */     int k = 0;
/* 33 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 61680.0F, 0.0F);
/* 34 */     GlStateManager.enableLighting();
/* 35 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 36 */     (Minecraft.getMinecraft()).entityRenderer.func_191514_d(true);
/*    */     
/* 38 */     if (Config.isShaders())
/*    */     {
/* 40 */       Shaders.beginSpiderEyes();
/*    */     }
/*    */     
/* 43 */     (Config.getRenderGlobal()).renderOverlayEyes = true;
/* 44 */     this.endermanRenderer.getMainModel().render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/* 45 */     (Config.getRenderGlobal()).renderOverlayEyes = false;
/*    */     
/* 47 */     if (Config.isShaders())
/*    */     {
/* 49 */       Shaders.endSpiderEyes();
/*    */     }
/*    */     
/* 52 */     (Minecraft.getMinecraft()).entityRenderer.func_191514_d(false);
/* 53 */     this.endermanRenderer.setLightmap((EntityLiving)entitylivingbaseIn);
/* 54 */     GlStateManager.depthMask(true);
/* 55 */     GlStateManager.disableBlend();
/* 56 */     GlStateManager.enableAlpha();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 61 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerEndermanEyes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */