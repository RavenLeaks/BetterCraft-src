/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelWither;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderWither;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.boss.EntityWither;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class LayerWitherAura implements LayerRenderer<EntityWither> {
/* 13 */   private static final ResourceLocation WITHER_ARMOR = new ResourceLocation("textures/entity/wither/wither_armor.png");
/*    */   private final RenderWither witherRenderer;
/* 15 */   private final ModelWither witherModel = new ModelWither(0.5F);
/*    */ 
/*    */   
/*    */   public LayerWitherAura(RenderWither witherRendererIn) {
/* 19 */     this.witherRenderer = witherRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityWither entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 24 */     if (entitylivingbaseIn.isArmored()) {
/*    */       
/* 26 */       GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
/* 27 */       this.witherRenderer.bindTexture(WITHER_ARMOR);
/* 28 */       GlStateManager.matrixMode(5890);
/* 29 */       GlStateManager.loadIdentity();
/* 30 */       float f = entitylivingbaseIn.ticksExisted + partialTicks;
/* 31 */       float f1 = MathHelper.cos(f * 0.02F) * 3.0F;
/* 32 */       float f2 = f * 0.01F;
/* 33 */       GlStateManager.translate(f1, f2, 0.0F);
/* 34 */       GlStateManager.matrixMode(5888);
/* 35 */       GlStateManager.enableBlend();
/* 36 */       float f3 = 0.5F;
/* 37 */       GlStateManager.color(0.5F, 0.5F, 0.5F, 1.0F);
/* 38 */       GlStateManager.disableLighting();
/* 39 */       GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
/* 40 */       this.witherModel.setLivingAnimations((EntityLivingBase)entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
/* 41 */       this.witherModel.setModelAttributes(this.witherRenderer.getMainModel());
/* 42 */       (Minecraft.getMinecraft()).entityRenderer.func_191514_d(true);
/* 43 */       this.witherModel.render((Entity)entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
/* 44 */       (Minecraft.getMinecraft()).entityRenderer.func_191514_d(false);
/* 45 */       GlStateManager.matrixMode(5890);
/* 46 */       GlStateManager.loadIdentity();
/* 47 */       GlStateManager.matrixMode(5888);
/* 48 */       GlStateManager.enableLighting();
/* 49 */       GlStateManager.disableBlend();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 55 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerWitherAura.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */