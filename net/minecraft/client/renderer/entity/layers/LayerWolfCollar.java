/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderWolf;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityWolf;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import optifine.Config;
/*    */ import optifine.CustomColors;
/*    */ 
/*    */ public class LayerWolfCollar implements LayerRenderer<EntityWolf> {
/* 12 */   private static final ResourceLocation WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
/*    */   
/*    */   private final RenderWolf wolfRenderer;
/*    */   
/*    */   public LayerWolfCollar(RenderWolf wolfRendererIn) {
/* 17 */     this.wolfRenderer = wolfRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityWolf entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 22 */     if (entitylivingbaseIn.isTamed() && !entitylivingbaseIn.isInvisible()) {
/*    */       
/* 24 */       this.wolfRenderer.bindTexture(WOLF_COLLAR);
/* 25 */       float[] afloat = entitylivingbaseIn.getCollarColor().func_193349_f();
/*    */       
/* 27 */       if (Config.isCustomColors())
/*    */       {
/* 29 */         afloat = CustomColors.getWolfCollarColors(entitylivingbaseIn.getCollarColor(), afloat);
/*    */       }
/*    */       
/* 32 */       GlStateManager.color(afloat[0], afloat[1], afloat[2]);
/* 33 */       this.wolfRenderer.getMainModel().render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 39 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerWolfCollar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */