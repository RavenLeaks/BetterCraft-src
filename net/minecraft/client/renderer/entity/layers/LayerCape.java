/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.entity.AbstractClientPlayer;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.entity.RenderPlayer;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.inventory.EntityEquipmentSlot;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class LayerCape
/*    */   implements LayerRenderer<AbstractClientPlayer> {
/*    */   private final RenderPlayer playerRenderer;
/*    */   
/*    */   public LayerCape(RenderPlayer playerRendererIn) {
/* 18 */     this.playerRenderer = playerRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 23 */     if (entitylivingbaseIn.hasPlayerInfo() && !entitylivingbaseIn.isInvisible() && entitylivingbaseIn.isWearing(EnumPlayerModelParts.CAPE) && entitylivingbaseIn.getLocationCape() != null) {
/*    */       
/* 25 */       ItemStack itemstack = entitylivingbaseIn.getItemStackFromSlot(EntityEquipmentSlot.CHEST);
/*    */       
/* 27 */       if (itemstack.getItem() != Items.ELYTRA) {
/*    */         
/* 29 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 30 */         this.playerRenderer.bindTexture(entitylivingbaseIn.getLocationCape());
/* 31 */         GlStateManager.pushMatrix();
/* 32 */         GlStateManager.translate(0.0F, 0.0F, 0.125F);
/* 33 */         double d0 = entitylivingbaseIn.prevChasingPosX + (entitylivingbaseIn.chasingPosX - entitylivingbaseIn.prevChasingPosX) * partialTicks - entitylivingbaseIn.prevPosX + (entitylivingbaseIn.posX - entitylivingbaseIn.prevPosX) * partialTicks;
/* 34 */         double d1 = entitylivingbaseIn.prevChasingPosY + (entitylivingbaseIn.chasingPosY - entitylivingbaseIn.prevChasingPosY) * partialTicks - entitylivingbaseIn.prevPosY + (entitylivingbaseIn.posY - entitylivingbaseIn.prevPosY) * partialTicks;
/* 35 */         double d2 = entitylivingbaseIn.prevChasingPosZ + (entitylivingbaseIn.chasingPosZ - entitylivingbaseIn.prevChasingPosZ) * partialTicks - entitylivingbaseIn.prevPosZ + (entitylivingbaseIn.posZ - entitylivingbaseIn.prevPosZ) * partialTicks;
/* 36 */         float f = entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks;
/* 37 */         double d3 = MathHelper.sin(f * 0.017453292F);
/* 38 */         double d4 = -MathHelper.cos(f * 0.017453292F);
/* 39 */         float f1 = (float)d1 * 10.0F;
/* 40 */         f1 = MathHelper.clamp(f1, -6.0F, 32.0F);
/* 41 */         float f2 = (float)(d0 * d3 + d2 * d4) * 100.0F;
/* 42 */         float f3 = (float)(d0 * d4 - d2 * d3) * 100.0F;
/*    */         
/* 44 */         if (f2 < 0.0F)
/*    */         {
/* 46 */           f2 = 0.0F;
/*    */         }
/*    */         
/* 49 */         if (f2 > 165.0F)
/*    */         {
/* 51 */           f2 = 165.0F;
/*    */         }
/*    */         
/* 54 */         float f4 = entitylivingbaseIn.prevCameraYaw + (entitylivingbaseIn.cameraYaw - entitylivingbaseIn.prevCameraYaw) * partialTicks;
/* 55 */         f1 += MathHelper.sin((entitylivingbaseIn.prevDistanceWalkedModified + (entitylivingbaseIn.distanceWalkedModified - entitylivingbaseIn.prevDistanceWalkedModified) * partialTicks) * 6.0F) * 32.0F * f4;
/*    */         
/* 57 */         if (entitylivingbaseIn.isSneaking()) {
/*    */           
/* 59 */           f1 += 25.0F;
/* 60 */           GlStateManager.translate(0.0F, 0.142F, -0.0178F);
/*    */         } 
/*    */         
/* 63 */         GlStateManager.rotate(6.0F + f2 / 2.0F + f1, 1.0F, 0.0F, 0.0F);
/* 64 */         GlStateManager.rotate(f3 / 2.0F, 0.0F, 0.0F, 1.0F);
/* 65 */         GlStateManager.rotate(-f3 / 2.0F, 0.0F, 1.0F, 0.0F);
/* 66 */         GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/* 67 */         this.playerRenderer.getMainModel().renderCape(0.0625F);
/* 68 */         GlStateManager.popMatrix();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 75 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerCape.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */