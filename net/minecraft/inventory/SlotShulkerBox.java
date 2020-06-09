/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ 
/*    */ public class SlotShulkerBox
/*    */   extends Slot
/*    */ {
/*    */   public SlotShulkerBox(IInventory p_i47265_1_, int p_i47265_2_, int p_i47265_3_, int p_i47265_4_) {
/* 11 */     super(p_i47265_1_, p_i47265_2_, p_i47265_3_, p_i47265_4_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isItemValid(ItemStack stack) {
/* 19 */     return !(Block.getBlockFromItem(stack.getItem()) instanceof net.minecraft.block.BlockShulkerBox);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\SlotShulkerBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */