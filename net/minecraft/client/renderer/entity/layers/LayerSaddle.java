/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.model.ModelPig;
/*    */ import net.minecraft.client.renderer.entity.RenderPig;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityPig;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class LayerSaddle implements LayerRenderer<EntityPig> {
/* 10 */   private static final ResourceLocation TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
/*    */   private final RenderPig pigRenderer;
/* 12 */   private final ModelPig pigModel = new ModelPig(0.5F);
/*    */ 
/*    */   
/*    */   public LayerSaddle(RenderPig pigRendererIn) {
/* 16 */     this.pigRenderer = pigRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityPig entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 21 */     if (entitylivingbaseIn.getSaddled()) {
/*    */       
/* 23 */       this.pigRenderer.bindTexture(TEXTURE);
/* 24 */       this.pigModel.setModelAttributes(this.pigRenderer.getMainModel());
/* 25 */       this.pigModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 31 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerSaddle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */