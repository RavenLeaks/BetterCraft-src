/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBase;
/*    */ import net.minecraft.client.model.ModelSlime;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderSlime;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntitySlime;
/*    */ 
/*    */ public class LayerSlimeGel implements LayerRenderer<EntitySlime> {
/* 12 */   private final ModelBase slimeModel = (ModelBase)new ModelSlime(0);
/*    */   private final RenderSlime slimeRenderer;
/*    */   
/*    */   public LayerSlimeGel(RenderSlime slimeRendererIn) {
/* 16 */     this.slimeRenderer = slimeRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntitySlime entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 21 */     if (!entitylivingbaseIn.isInvisible()) {
/*    */       
/* 23 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 24 */       GlStateManager.enableNormalize();
/* 25 */       GlStateManager.enableBlend();
/* 26 */       GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
/* 27 */       this.slimeModel.setModelAttributes(this.slimeRenderer.getMainModel());
/* 28 */       this.slimeModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/* 29 */       GlStateManager.disableBlend();
/* 30 */       GlStateManager.disableNormalize();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 36 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerSlimeGel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */