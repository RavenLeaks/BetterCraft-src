/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.entity.player.InventoryPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerHopper
/*    */   extends Container
/*    */ {
/*    */   private final IInventory hopperInventory;
/*    */   
/*    */   public ContainerHopper(InventoryPlayer playerInventory, IInventory hopperInventoryIn, EntityPlayer player) {
/* 13 */     this.hopperInventory = hopperInventoryIn;
/* 14 */     hopperInventoryIn.openInventory(player);
/* 15 */     int i = 51;
/*    */     
/* 17 */     for (int j = 0; j < hopperInventoryIn.getSizeInventory(); j++)
/*    */     {
/* 19 */       addSlotToContainer(new Slot(hopperInventoryIn, j, 44 + j * 18, 20));
/*    */     }
/*    */     
/* 22 */     for (int l = 0; l < 3; l++) {
/*    */       
/* 24 */       for (int k = 0; k < 9; k++)
/*    */       {
/* 26 */         addSlotToContainer(new Slot((IInventory)playerInventory, k + l * 9 + 9, 8 + k * 18, l * 18 + 51));
/*    */       }
/*    */     } 
/*    */     
/* 30 */     for (int i1 = 0; i1 < 9; i1++)
/*    */     {
/* 32 */       addSlotToContainer(new Slot((IInventory)playerInventory, i1, 8 + i1 * 18, 109));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canInteractWith(EntityPlayer playerIn) {
/* 41 */     return this.hopperInventory.isUsableByPlayer(playerIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 49 */     ItemStack itemstack = ItemStack.field_190927_a;
/* 50 */     Slot slot = this.inventorySlots.get(index);
/*    */     
/* 52 */     if (slot != null && slot.getHasStack()) {
/*    */       
/* 54 */       ItemStack itemstack1 = slot.getStack();
/* 55 */       itemstack = itemstack1.copy();
/*    */       
/* 57 */       if (index < this.hopperInventory.getSizeInventory()) {
/*    */         
/* 59 */         if (!mergeItemStack(itemstack1, this.hopperInventory.getSizeInventory(), this.inventorySlots.size(), true))
/*    */         {
/* 61 */           return ItemStack.field_190927_a;
/*    */         }
/*    */       }
/* 64 */       else if (!mergeItemStack(itemstack1, 0, this.hopperInventory.getSizeInventory(), false)) {
/*    */         
/* 66 */         return ItemStack.field_190927_a;
/*    */       } 
/*    */       
/* 69 */       if (itemstack1.func_190926_b()) {
/*    */         
/* 71 */         slot.putStack(ItemStack.field_190927_a);
/*    */       }
/*    */       else {
/*    */         
/* 75 */         slot.onSlotChanged();
/*    */       } 
/*    */     } 
/*    */     
/* 79 */     return itemstack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onContainerClosed(EntityPlayer playerIn) {
/* 87 */     super.onContainerClosed(playerIn);
/* 88 */     this.hopperInventory.closeInventory(playerIn);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\ContainerHopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */