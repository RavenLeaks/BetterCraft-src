/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderEnderman;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ 
/*    */ public class LayerHeldBlock
/*    */   implements LayerRenderer<EntityEnderman> {
/*    */   private final RenderEnderman endermanRenderer;
/*    */   
/*    */   public LayerHeldBlock(RenderEnderman endermanRendererIn) {
/* 18 */     this.endermanRenderer = endermanRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityEnderman entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 23 */     IBlockState iblockstate = entitylivingbaseIn.getHeldBlockState();
/*    */     
/* 25 */     if (iblockstate != null) {
/*    */       
/* 27 */       BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 28 */       GlStateManager.enableRescaleNormal();
/* 29 */       GlStateManager.pushMatrix();
/* 30 */       GlStateManager.translate(0.0F, 0.6875F, -0.75F);
/* 31 */       GlStateManager.rotate(20.0F, 1.0F, 0.0F, 0.0F);
/* 32 */       GlStateManager.rotate(45.0F, 0.0F, 1.0F, 0.0F);
/* 33 */       GlStateManager.translate(0.25F, 0.1875F, 0.25F);
/* 34 */       float f = 0.5F;
/* 35 */       GlStateManager.scale(-0.5F, -0.5F, 0.5F);
/* 36 */       int i = entitylivingbaseIn.getBrightnessForRender();
/* 37 */       int j = i % 65536;
/* 38 */       int k = i / 65536;
/* 39 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/* 40 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 41 */       this.endermanRenderer.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 42 */       blockrendererdispatcher.renderBlockBrightness(iblockstate, 1.0F);
/* 43 */       GlStateManager.popMatrix();
/* 44 */       GlStateManager.disableRescaleNormal();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 50 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerHeldBlock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */