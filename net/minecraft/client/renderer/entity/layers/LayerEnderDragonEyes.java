/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderDragon;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.boss.EntityDragon;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import optifine.Config;
/*    */ import shadersmod.client.Shaders;
/*    */ 
/*    */ public class LayerEnderDragonEyes implements LayerRenderer<EntityDragon> {
/* 14 */   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/enderdragon/dragon_eyes.png");
/*    */   
/*    */   private final RenderDragon dragonRenderer;
/*    */   
/*    */   public LayerEnderDragonEyes(RenderDragon dragonRendererIn) {
/* 19 */     this.dragonRenderer = dragonRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityDragon entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 24 */     this.dragonRenderer.bindTexture(TEXTURE);
/* 25 */     GlStateManager.enableBlend();
/* 26 */     GlStateManager.disableAlpha();
/* 27 */     GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
/* 28 */     GlStateManager.disableLighting();
/* 29 */     GlStateManager.depthFunc(514);
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
/* 44 */     this.dragonRenderer.getMainModel().render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/* 45 */     (Config.getRenderGlobal()).renderOverlayEyes = false;
/*    */     
/* 47 */     if (Config.isShaders())
/*    */     {
/* 49 */       Shaders.endSpiderEyes();
/*    */     }
/*    */     
/* 52 */     (Minecraft.getMinecraft()).entityRenderer.func_191514_d(false);
/* 53 */     this.dragonRenderer.setLightmap((EntityLiving)entitylivingbaseIn);
/* 54 */     GlStateManager.disableBlend();
/* 55 */     GlStateManager.enableAlpha();
/* 56 */     GlStateManager.depthFunc(515);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 61 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerEnderDragonEyes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */