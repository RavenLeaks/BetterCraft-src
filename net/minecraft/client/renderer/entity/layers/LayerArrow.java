/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.model.ModelBox;
/*    */ import net.minecraft.client.model.ModelRenderer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.RenderHelper;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.projectile.EntityTippedArrow;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class LayerArrow
/*    */   implements LayerRenderer<EntityLivingBase>
/*    */ {
/*    */   private final RenderLivingBase<?> renderer;
/*    */   
/*    */   public LayerArrow(RenderLivingBase<?> rendererIn) {
/* 20 */     this.renderer = rendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 25 */     int i = entitylivingbaseIn.getArrowCountInEntity();
/*    */     
/* 27 */     if (i > 0) {
/*    */       
/* 29 */       EntityTippedArrow entityTippedArrow = new EntityTippedArrow(entitylivingbaseIn.world, entitylivingbaseIn.posX, entitylivingbaseIn.posY, entitylivingbaseIn.posZ);
/* 30 */       Random random = new Random(entitylivingbaseIn.getEntityId());
/* 31 */       RenderHelper.disableStandardItemLighting();
/*    */       
/* 33 */       for (int j = 0; j < i; j++) {
/*    */         
/* 35 */         GlStateManager.pushMatrix();
/* 36 */         ModelRenderer modelrenderer = this.renderer.getMainModel().getRandomModelBox(random);
/* 37 */         ModelBox modelbox = modelrenderer.cubeList.get(random.nextInt(modelrenderer.cubeList.size()));
/* 38 */         modelrenderer.postRender(0.0625F);
/* 39 */         float f = random.nextFloat();
/* 40 */         float f1 = random.nextFloat();
/* 41 */         float f2 = random.nextFloat();
/* 42 */         float f3 = (modelbox.posX1 + (modelbox.posX2 - modelbox.posX1) * f) / 16.0F;
/* 43 */         float f4 = (modelbox.posY1 + (modelbox.posY2 - modelbox.posY1) * f1) / 16.0F;
/* 44 */         float f5 = (modelbox.posZ1 + (modelbox.posZ2 - modelbox.posZ1) * f2) / 16.0F;
/* 45 */         GlStateManager.translate(f3, f4, f5);
/* 46 */         f = f * 2.0F - 1.0F;
/* 47 */         f1 = f1 * 2.0F - 1.0F;
/* 48 */         f2 = f2 * 2.0F - 1.0F;
/* 49 */         f *= -1.0F;
/* 50 */         f1 *= -1.0F;
/* 51 */         f2 *= -1.0F;
/* 52 */         float f6 = MathHelper.sqrt(f * f + f2 * f2);
/* 53 */         ((Entity)entityTippedArrow).rotationYaw = (float)(Math.atan2(f, f2) * 57.29577951308232D);
/* 54 */         ((Entity)entityTippedArrow).rotationPitch = (float)(Math.atan2(f1, f6) * 57.29577951308232D);
/* 55 */         ((Entity)entityTippedArrow).prevRotationYaw = ((Entity)entityTippedArrow).rotationYaw;
/* 56 */         ((Entity)entityTippedArrow).prevRotationPitch = ((Entity)entityTippedArrow).rotationPitch;
/* 57 */         double d0 = 0.0D;
/* 58 */         double d1 = 0.0D;
/* 59 */         double d2 = 0.0D;
/* 60 */         this.renderer.getRenderManager().doRenderEntity((Entity)entityTippedArrow, 0.0D, 0.0D, 0.0D, 0.0F, partialTicks, false);
/* 61 */         GlStateManager.popMatrix();
/*    */       } 
/*    */       
/* 64 */       RenderHelper.enableStandardItemLighting();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 70 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */