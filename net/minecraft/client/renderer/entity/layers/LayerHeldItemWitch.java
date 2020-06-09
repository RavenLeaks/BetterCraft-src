/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.entity.RenderWitch;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityWitch;
/*    */ import net.minecraft.init.Items;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumBlockRenderType;
/*    */ 
/*    */ public class LayerHeldItemWitch
/*    */   implements LayerRenderer<EntityWitch> {
/*    */   private final RenderWitch witchRenderer;
/*    */   
/*    */   public LayerHeldItemWitch(RenderWitch witchRendererIn) {
/* 20 */     this.witchRenderer = witchRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityWitch entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 25 */     ItemStack itemstack = entitylivingbaseIn.getHeldItemMainhand();
/*    */     
/* 27 */     if (!itemstack.func_190926_b()) {
/*    */       
/* 29 */       GlStateManager.color(1.0F, 1.0F, 1.0F);
/* 30 */       GlStateManager.pushMatrix();
/*    */       
/* 32 */       if ((this.witchRenderer.getMainModel()).isChild) {
/*    */         
/* 34 */         GlStateManager.translate(0.0F, 0.625F, 0.0F);
/* 35 */         GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
/* 36 */         float f = 0.5F;
/* 37 */         GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*    */       } 
/*    */       
/* 40 */       (this.witchRenderer.getMainModel()).villagerNose.postRender(0.0625F);
/* 41 */       GlStateManager.translate(-0.0625F, 0.53125F, 0.21875F);
/* 42 */       Item item = itemstack.getItem();
/* 43 */       Minecraft minecraft = Minecraft.getMinecraft();
/*    */       
/* 45 */       if (Block.getBlockFromItem(item).getDefaultState().getRenderType() == EnumBlockRenderType.ENTITYBLOCK_ANIMATED) {
/*    */         
/* 47 */         GlStateManager.translate(0.0F, 0.0625F, -0.25F);
/* 48 */         GlStateManager.rotate(30.0F, 1.0F, 0.0F, 0.0F);
/* 49 */         GlStateManager.rotate(-5.0F, 0.0F, 1.0F, 0.0F);
/* 50 */         float f1 = 0.375F;
/* 51 */         GlStateManager.scale(0.375F, -0.375F, 0.375F);
/*    */       }
/* 53 */       else if (item == Items.BOW) {
/*    */         
/* 55 */         GlStateManager.translate(0.0F, 0.125F, -0.125F);
/* 56 */         GlStateManager.rotate(-45.0F, 0.0F, 1.0F, 0.0F);
/* 57 */         float f2 = 0.625F;
/* 58 */         GlStateManager.scale(0.625F, -0.625F, 0.625F);
/* 59 */         GlStateManager.rotate(-100.0F, 1.0F, 0.0F, 0.0F);
/* 60 */         GlStateManager.rotate(-20.0F, 0.0F, 1.0F, 0.0F);
/*    */       }
/* 62 */       else if (item.isFull3D()) {
/*    */         
/* 64 */         if (item.shouldRotateAroundWhenRendering()) {
/*    */           
/* 66 */           GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
/* 67 */           GlStateManager.translate(0.0F, -0.0625F, 0.0F);
/*    */         } 
/*    */         
/* 70 */         this.witchRenderer.transformHeldFull3DItemLayer();
/* 71 */         GlStateManager.translate(0.0625F, -0.125F, 0.0F);
/* 72 */         float f3 = 0.625F;
/* 73 */         GlStateManager.scale(0.625F, -0.625F, 0.625F);
/* 74 */         GlStateManager.rotate(0.0F, 1.0F, 0.0F, 0.0F);
/* 75 */         GlStateManager.rotate(0.0F, 0.0F, 1.0F, 0.0F);
/*    */       }
/*    */       else {
/*    */         
/* 79 */         GlStateManager.translate(0.1875F, 0.1875F, 0.0F);
/* 80 */         float f4 = 0.875F;
/* 81 */         GlStateManager.scale(0.875F, 0.875F, 0.875F);
/* 82 */         GlStateManager.rotate(-20.0F, 0.0F, 0.0F, 1.0F);
/* 83 */         GlStateManager.rotate(-60.0F, 1.0F, 0.0F, 0.0F);
/* 84 */         GlStateManager.rotate(-30.0F, 0.0F, 0.0F, 1.0F);
/*    */       } 
/*    */       
/* 87 */       GlStateManager.rotate(-15.0F, 1.0F, 0.0F, 0.0F);
/* 88 */       GlStateManager.rotate(40.0F, 0.0F, 0.0F, 1.0F);
/* 89 */       minecraft.getItemRenderer().renderItem((EntityLivingBase)entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND);
/* 90 */       GlStateManager.popMatrix();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 96 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerHeldItemWitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */