/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderMooshroom;
/*    */ import net.minecraft.client.renderer.texture.TextureMap;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityMooshroom;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class LayerMooshroomMushroom
/*    */   implements LayerRenderer<EntityMooshroom> {
/*    */   private final RenderMooshroom mooshroomRenderer;
/*    */   
/*    */   public LayerMooshroomMushroom(RenderMooshroom mooshroomRendererIn) {
/* 17 */     this.mooshroomRenderer = mooshroomRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityMooshroom entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 22 */     if (!entitylivingbaseIn.isChild() && !entitylivingbaseIn.isInvisible()) {
/*    */       
/* 24 */       BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 25 */       this.mooshroomRenderer.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 26 */       GlStateManager.enableCull();
/* 27 */       GlStateManager.cullFace(GlStateManager.CullFace.FRONT);
/* 28 */       GlStateManager.pushMatrix();
/* 29 */       GlStateManager.scale(1.0F, -1.0F, 1.0F);
/* 30 */       GlStateManager.translate(0.2F, 0.35F, 0.5F);
/* 31 */       GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
/* 32 */       GlStateManager.pushMatrix();
/* 33 */       GlStateManager.translate(-0.5F, -0.5F, 0.5F);
/* 34 */       blockrendererdispatcher.renderBlockBrightness(Blocks.RED_MUSHROOM.getDefaultState(), 1.0F);
/* 35 */       GlStateManager.popMatrix();
/* 36 */       GlStateManager.pushMatrix();
/* 37 */       GlStateManager.translate(0.1F, 0.0F, -0.6F);
/* 38 */       GlStateManager.rotate(42.0F, 0.0F, 1.0F, 0.0F);
/* 39 */       GlStateManager.translate(-0.5F, -0.5F, 0.5F);
/* 40 */       blockrendererdispatcher.renderBlockBrightness(Blocks.RED_MUSHROOM.getDefaultState(), 1.0F);
/* 41 */       GlStateManager.popMatrix();
/* 42 */       GlStateManager.popMatrix();
/* 43 */       GlStateManager.pushMatrix();
/* 44 */       (this.mooshroomRenderer.getMainModel()).head.postRender(0.0625F);
/* 45 */       GlStateManager.scale(1.0F, -1.0F, 1.0F);
/* 46 */       GlStateManager.translate(0.0F, 0.7F, -0.2F);
/* 47 */       GlStateManager.rotate(12.0F, 0.0F, 1.0F, 0.0F);
/* 48 */       GlStateManager.translate(-0.5F, -0.5F, 0.5F);
/* 49 */       blockrendererdispatcher.renderBlockBrightness(Blocks.RED_MUSHROOM.getDefaultState(), 1.0F);
/* 50 */       GlStateManager.popMatrix();
/* 51 */       GlStateManager.cullFace(GlStateManager.CullFace.BACK);
/* 52 */       GlStateManager.disableCull();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 58 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerMooshroomMushroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */