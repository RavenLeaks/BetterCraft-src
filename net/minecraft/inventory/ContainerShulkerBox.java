/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerShulkerBox
/*    */   extends Container
/*    */ {
/*    */   private final IInventory field_190899_a;
/*    */   
/*    */   public ContainerShulkerBox(InventoryPlayer p_i47266_1_, IInventory p_i47266_2_, EntityPlayer p_i47266_3_) {
/* 13 */     this.field_190899_a = p_i47266_2_;
/* 14 */     p_i47266_2_.openInventory(p_i47266_3_);
/* 15 */     int i = 3;
/* 16 */     int j = 9;
/*    */     
/* 18 */     for (int k = 0; k < 3; k++) {
/*    */       
/* 20 */       for (int l = 0; l < 9; l++)
/*    */       {
/* 22 */         addSlotToContainer(new SlotShulkerBox(p_i47266_2_, l + k * 9, 8 + l * 18, 18 + k * 18));
/*    */       }
/*    */     } 
/*    */     
/* 26 */     for (int i1 = 0; i1 < 3; i1++) {
/*    */       
/* 28 */       for (int k1 = 0; k1 < 9; k1++)
/*    */       {
/* 30 */         addSlotToContainer(new Slot((IInventory)p_i47266_1_, k1 + i1 * 9 + 9, 8 + k1 * 18, 84 + i1 * 18));
/*    */       }
/*    */     } 
/*    */     
/* 34 */     for (int j1 = 0; j1 < 9; j1++)
/*    */     {
/* 36 */       addSlotToContainer(new Slot((IInventory)p_i47266_1_, j1, 8 + j1 * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canInteractWith(EntityPlayer playerIn) {
/* 45 */     return this.field_190899_a.isUsableByPlayer(playerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 53 */     ItemStack itemstack = ItemStack.field_190927_a;
/* 54 */     Slot slot = this.inventorySlots.get(index);
/*    */     
/* 56 */     if (slot != null && slot.getHasStack()) {
/*    */       
/* 58 */       ItemStack itemstack1 = slot.getStack();
/* 59 */       itemstack = itemstack1.copy();
/*    */       
/* 61 */       if (index < this.field_190899_a.getSizeInventory()) {
/*    */         
/* 63 */         if (!mergeItemStack(itemstack1, this.field_190899_a.getSizeInventory(), this.inventorySlots.size(), true))
/*    */         {
/* 65 */           return ItemStack.field_190927_a;
/*    */         }
/*    */       }
/* 68 */       else if (!mergeItemStack(itemstack1, 0, this.field_190899_a.getSizeInventory(), false)) {
/*    */         
/* 70 */         return ItemStack.field_190927_a;
/*    */       } 
/*    */       
/* 73 */       if (itemstack1.func_190926_b()) {
/*    */         
/* 75 */         slot.putStack(ItemStack.field_190927_a);
/*    */       }
/*    */       else {
/*    */         
/* 79 */         slot.onSlotChanged();
/*    */       } 
/*    */     } 
/*    */     
/* 83 */     return itemstack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onContainerClosed(EntityPlayer playerIn) {
/* 91 */     super.onContainerClosed(playerIn);
/* 92 */     this.field_190899_a.closeInventory(playerIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\ContainerShulkerBox.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */