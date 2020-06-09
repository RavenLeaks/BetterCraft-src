/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelCreeper;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderCreeper;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityCreeper;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class LayerCreeperCharge implements LayerRenderer<EntityCreeper> {
/* 12 */   private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
/*    */   private final RenderCreeper creeperRenderer;
/* 14 */   private final ModelCreeper creeperModel = new ModelCreeper(2.0F);
/*    */ 
/*    */   
/*    */   public LayerCreeperCharge(RenderCreeper creeperRendererIn) {
/* 18 */     this.creeperRenderer = creeperRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityCreeper entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 23 */     if (entitylivingbaseIn.getPowered()) {
/*    */       
/* 25 */       boolean flag = entitylivingbaseIn.isInvisible();
/* 26 */       GlStateManager.depthMask(!flag);
/* 27 */       this.creeperRenderer.bindTexture(LIGHTNING_TEXTURE);
/* 28 */       GlStateManager.matrixMode(5890);
/* 29 */       GlStateManager.loadIdentity();
/* 30 */       float f = entitylivingbaseIn.ticksExisted + partialTicks;
/* 31 */       GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
/* 32 */       GlStateManager.matrixMode(5888);
/* 33 */       GlStateManager.enableBlend();
/* 34 */       float f1 = 0.5F;
/* 35 */       GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
/* 36 */       GlStateManager.disableLighting();
/* 37 */       GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
/* 38 */       this.creeperModel.setModelAttributes(this.creeperRenderer.getMainModel());
/* 39 */       (Minecraft.getMinecraft()).entityRenderer.func_191514_d(true);
/* 40 */       this.creeperModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/* 41 */       (Minecraft.getMinecraft()).entityRenderer.func_191514_d(false);
/* 42 */       GlStateManager.matrixMode(5890);
/* 43 */       GlStateManager.loadIdentity();
/* 44 */       GlStateManager.matrixMode(5888);
/* 45 */       GlStateManager.enableLighting();
/* 46 */       GlStateManager.disableBlend();
/* 47 */       GlStateManager.depthMask(flag);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 53 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerCreeperCharge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */