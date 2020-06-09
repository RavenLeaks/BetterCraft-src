/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class ChestRenderer
/*    */ {
/*    */   public void renderChestBrightness(Block blockIn, float color) {
/* 11 */     GlStateManager.color(color, color, color, 1.0F);
/* 12 */     GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/* 13 */     TileEntityItemStackRenderer.instance.renderByItem(new ItemStack(blockIn));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\ChestRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */