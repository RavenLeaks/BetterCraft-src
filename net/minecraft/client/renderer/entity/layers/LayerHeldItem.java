/*    */ package net.minecraft.client.renderer.entity.layers;
/*    */ 
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.model.ModelBiped;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*    */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.util.EnumHandSide;
/*    */ 
/*    */ public class LayerHeldItem
/*    */   implements LayerRenderer<EntityLivingBase>
/*    */ {
/*    */   protected final RenderLivingBase<?> livingEntityRenderer;
/*    */   
/*    */   public LayerHeldItem(RenderLivingBase<?> livingEntityRendererIn) {
/* 18 */     this.livingEntityRenderer = livingEntityRendererIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
/* 23 */     boolean flag = (entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT);
/* 24 */     ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
/* 25 */     ItemStack itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();
/*    */     
/* 27 */     if (!itemstack.func_190926_b() || !itemstack1.func_190926_b()) {
/*    */       
/* 29 */       GlStateManager.pushMatrix();
/*    */       
/* 31 */       if ((this.livingEntityRenderer.getMainModel()).isChild) {
/*    */         
/* 33 */         float f = 0.5F;
/* 34 */         GlStateManager.translate(0.0F, 0.75F, 0.0F);
/* 35 */         GlStateManager.scale(0.5F, 0.5F, 0.5F);
/*    */       } 
/*    */       
/* 38 */       renderHeldItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
/* 39 */       renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
/* 40 */       GlStateManager.popMatrix();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void renderHeldItem(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide) {
/* 46 */     if (!p_188358_2_.func_190926_b()) {
/*    */       
/* 48 */       GlStateManager.pushMatrix();
/* 49 */       func_191361_a(handSide);
/*    */       
/* 51 */       if (p_188358_1_.isSneaking())
/*    */       {
/* 53 */         GlStateManager.translate(0.0F, 0.2F, 0.0F);
/*    */       }
/*    */       
/* 56 */       GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/* 57 */       GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/* 58 */       boolean flag = (handSide == EnumHandSide.LEFT);
/* 59 */       GlStateManager.translate((flag ? -1 : true) / 16.0F, 0.125F, -0.625F);
/* 60 */       Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
/* 61 */       GlStateManager.popMatrix();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void func_191361_a(EnumHandSide p_191361_1_) {
/* 67 */     ((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625F, p_191361_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldCombineTextures() {
/* 72 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\entity\layers\LayerHeldItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */