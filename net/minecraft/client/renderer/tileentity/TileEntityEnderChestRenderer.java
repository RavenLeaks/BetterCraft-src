/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelChest;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityEnderChest;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ public class TileEntityEnderChestRenderer extends TileEntitySpecialRenderer<TileEntityEnderChest> {
/* 10 */   private static final ResourceLocation ENDER_CHEST_TEXTURE = new ResourceLocation("textures/entity/chest/ender.png");
/* 11 */   private final ModelChest modelChest = new ModelChest();
/*    */ 
/*    */   
/*    */   public void func_192841_a(TileEntityEnderChest p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
/* 15 */     int i = 0;
/*    */     
/* 17 */     if (p_192841_1_.hasWorldObj())
/*    */     {
/* 19 */       i = p_192841_1_.getBlockMetadata();
/*    */     }
/*    */     
/* 22 */     if (p_192841_9_ >= 0) {
/*    */       
/* 24 */       bindTexture(DESTROY_STAGES[p_192841_9_]);
/* 25 */       GlStateManager.matrixMode(5890);
/* 26 */       GlStateManager.pushMatrix();
/* 27 */       GlStateManager.scale(4.0F, 4.0F, 1.0F);
/* 28 */       GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
/* 29 */       GlStateManager.matrixMode(5888);
/*    */     }
/*    */     else {
/*    */       
/* 33 */       bindTexture(ENDER_CHEST_TEXTURE);
/*    */     } 
/*    */     
/* 36 */     GlStateManager.pushMatrix();
/* 37 */     GlStateManager.enableRescaleNormal();
/* 38 */     GlStateManager.color(1.0F, 1.0F, 1.0F, p_192841_10_);
/* 39 */     GlStateManager.translate((float)p_192841_2_, (float)p_192841_4_ + 1.0F, (float)p_192841_6_ + 1.0F);
/* 40 */     GlStateManager.scale(1.0F, -1.0F, -1.0F);
/* 41 */     GlStateManager.translate(0.5F, 0.5F, 0.5F);
/* 42 */     int j = 0;
/*    */     
/* 44 */     if (i == 2)
/*    */     {
/* 46 */       j = 180;
/*    */     }
/*    */     
/* 49 */     if (i == 3)
/*    */     {
/* 51 */       j = 0;
/*    */     }
/*    */     
/* 54 */     if (i == 4)
/*    */     {
/* 56 */       j = 90;
/*    */     }
/*    */     
/* 59 */     if (i == 5)
/*    */     {
/* 61 */       j = -90;
/*    */     }
/*    */     
/* 64 */     GlStateManager.rotate(j, 0.0F, 1.0F, 0.0F);
/* 65 */     GlStateManager.translate(-0.5F, -0.5F, -0.5F);
/* 66 */     float f = p_192841_1_.prevLidAngle + (p_192841_1_.lidAngle - p_192841_1_.prevLidAngle) * p_192841_8_;
/* 67 */     f = 1.0F - f;
/* 68 */     f = 1.0F - f * f * f;
/* 69 */     this.modelChest.chestLid.rotateAngleX = -(f * 1.5707964F);
/* 70 */     this.modelChest.renderAll();
/* 71 */     GlStateManager.disableRescaleNormal();
/* 72 */     GlStateManager.popMatrix();
/* 73 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*    */     
/* 75 */     if (p_192841_9_ >= 0) {
/*    */       
/* 77 */       GlStateManager.matrixMode(5890);
/* 78 */       GlStateManager.popMatrix();
/* 79 */       GlStateManager.matrixMode(5888);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntityEnderChestRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */