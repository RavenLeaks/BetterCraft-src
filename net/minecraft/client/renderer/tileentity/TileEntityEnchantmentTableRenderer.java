/*    */ package net.minecraft.client.renderer.tileentity;
/*    */ 
/*    */ import net.minecraft.client.model.ModelBook;
/*    */ import net.minecraft.client.renderer.GlStateManager;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityEnchantmentTable;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ public class TileEntityEnchantmentTableRenderer
/*    */   extends TileEntitySpecialRenderer<TileEntityEnchantmentTable>
/*    */ {
/* 13 */   private static final ResourceLocation TEXTURE_BOOK = new ResourceLocation("textures/entity/enchanting_table_book.png");
/* 14 */   private final ModelBook modelBook = new ModelBook();
/*    */ 
/*    */   
/*    */   public void func_192841_a(TileEntityEnchantmentTable p_192841_1_, double p_192841_2_, double p_192841_4_, double p_192841_6_, float p_192841_8_, int p_192841_9_, float p_192841_10_) {
/* 18 */     GlStateManager.pushMatrix();
/* 19 */     GlStateManager.translate((float)p_192841_2_ + 0.5F, (float)p_192841_4_ + 0.75F, (float)p_192841_6_ + 0.5F);
/* 20 */     float f = p_192841_1_.tickCount + p_192841_8_;
/* 21 */     GlStateManager.translate(0.0F, 0.1F + MathHelper.sin(f * 0.1F) * 0.01F, 0.0F);
/*    */     
/*    */     float f1;
/* 24 */     for (f1 = p_192841_1_.bookRotation - p_192841_1_.bookRotationPrev; f1 >= 3.1415927F; f1 -= 6.2831855F);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 29 */     while (f1 < -3.1415927F)
/*    */     {
/* 31 */       f1 += 6.2831855F;
/*    */     }
/*    */     
/* 34 */     float f2 = p_192841_1_.bookRotationPrev + f1 * p_192841_8_;
/* 35 */     GlStateManager.rotate(-f2 * 57.295776F, 0.0F, 1.0F, 0.0F);
/* 36 */     GlStateManager.rotate(80.0F, 0.0F, 0.0F, 1.0F);
/* 37 */     bindTexture(TEXTURE_BOOK);
/* 38 */     float f3 = p_192841_1_.pageFlipPrev + (p_192841_1_.pageFlip - p_192841_1_.pageFlipPrev) * p_192841_8_ + 0.25F;
/* 39 */     float f4 = p_192841_1_.pageFlipPrev + (p_192841_1_.pageFlip - p_192841_1_.pageFlipPrev) * p_192841_8_ + 0.75F;
/* 40 */     f3 = (f3 - MathHelper.fastFloor(f3)) * 1.6F - 0.3F;
/* 41 */     f4 = (f4 - MathHelper.fastFloor(f4)) * 1.6F - 0.3F;
/*    */     
/* 43 */     if (f3 < 0.0F)
/*    */     {
/* 45 */       f3 = 0.0F;
/*    */     }
/*    */     
/* 48 */     if (f4 < 0.0F)
/*    */     {
/* 50 */       f4 = 0.0F;
/*    */     }
/*    */     
/* 53 */     if (f3 > 1.0F)
/*    */     {
/* 55 */       f3 = 1.0F;
/*    */     }
/*    */     
/* 58 */     if (f4 > 1.0F)
/*    */     {
/* 60 */       f4 = 1.0F;
/*    */     }
/*    */     
/* 63 */     float f5 = p_192841_1_.bookSpreadPrev + (p_192841_1_.bookSpread - p_192841_1_.bookSpreadPrev) * p_192841_8_;
/* 64 */     GlStateManager.enableCull();
/* 65 */     this.modelBook.render(null, f, f3, f4, f5, 0.0F, 0.0625F);
/* 66 */     GlStateManager.popMatrix();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\tileentity\TileEntityEnchantmentTableRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */