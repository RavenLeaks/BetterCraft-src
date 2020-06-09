/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.model.ModelSkeleton;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityStray;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class LayerStrayClothing implements LayerRenderer<EntityStray> {
/* 11 */   private static final ResourceLocation STRAY_CLOTHES_TEXTURES = new ResourceLocation("textures/entity/skeleton/stray_overlay.png");
/*    */   private final RenderLivingBase<?> renderer;
/* 13 */   private final ModelSkeleton layerModel = new ModelSkeleton(0.25F, true);
/*    */ 
/*    */   
/*    */   public LayerStrayClothing(RenderLivingBase<?> p_i47183_1_) {
/* 17 */     this.renderer = p_i47183_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityStray entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 22 */     this.layerModel.setModelAttributes(this.renderer.getMainModel());
/* 23 */     this.layerModel.setLivingAnimations((EntityLivingBase)entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
/* 24 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 25 */     this.renderer.bindTexture(STRAY_CLOTHES_TEXTURES);
/* 26 */     this.layerModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 31 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerStrayClothing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */