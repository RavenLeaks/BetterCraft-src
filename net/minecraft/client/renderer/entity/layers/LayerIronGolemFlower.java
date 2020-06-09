/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelIronGolem;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.OpenGlHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderIronGolem;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityIronGolem;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class LayerIronGolemFlower
/*    */   implements LayerRenderer<EntityIronGolem> {
/*    */   private final RenderIronGolem ironGolemRenderer;
/*    */   
/*    */   public LayerIronGolemFlower(RenderIronGolem ironGolemRendererIn) {
/* 19 */     this.ironGolemRenderer = ironGolemRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityIronGolem entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 24 */     if (entitylivingbaseIn.getHoldRoseTick() != 0) {
/*    */       
/* 26 */       BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 27 */       GlStateManager.enableRescaleNormal();
/* 28 */       GlStateManager.pushMatrix();
/* 29 */       GlStateManager.rotate(5.0F + 180.0F * ((ModelIronGolem)this.ironGolemRenderer.getMainModel()).ironGolemRightArm.rotateAngleX / 3.1415927F, 1.0F, 0.0F, 0.0F);
/* 30 */       GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 31 */       GlStateManager.translate(-0.9375F, -0.625F, -0.9375F);
/* 32 */       float f = 0.5F;
/* 33 */       GlStateManager.scale(0.5F, -0.5F, 0.5F);
/* 34 */       int i = entitylivingbaseIn.getBrightnessForRender();
/* 35 */       int j = i % 65536;
/* 36 */       int k = i / 65536;
/* 37 */       OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, j, k);
/* 38 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 39 */       this.ironGolemRenderer.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 40 */       blockrendererdispatcher.renderBlockBrightness(Blocks.RED_FLOWER.getDefaultState(), 1.0F);
/* 41 */       GlStateManager.popMatrix();
/* 42 */       GlStateManager.disableRescaleNormal();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 48 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerIronGolemFlower.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */