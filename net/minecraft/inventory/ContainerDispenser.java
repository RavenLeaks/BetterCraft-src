/*    */ package net.minecraft.inventory;
/*    */ 
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.item.ItemStack;
/*    */ 
/*    */ public class ContainerDispenser
/*    */   extends Container
/*    */ {
/*    */   private final IInventory dispenserInventory;
/*    */   
/*    */   public ContainerDispenser(IInventory playerInventory, IInventory dispenserInventoryIn) {
/* 12 */     this.dispenserInventory = dispenserInventoryIn;
/*    */     
/* 14 */     for (int i = 0; i < 3; i++) {
/*    */       
/* 16 */       for (int j = 0; j < 3; j++)
/*    */       {
/* 18 */         addSlotToContainer(new Slot(dispenserInventoryIn, j + i * 3, 62 + j * 18, 17 + i * 18));
/*    */       }
/*    */     } 
/*    */     
/* 22 */     for (int k = 0; k < 3; k++) {
/*    */       
/* 24 */       for (int i1 = 0; i1 < 9; i1++)
/*    */       {
/* 26 */         addSlotToContainer(new Slot(playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
/*    */       }
/*    */     } 
/*    */     
/* 30 */     for (int l = 0; l < 9; l++)
/*    */     {
/* 32 */       addSlotToContainer(new Slot(playerInventory, l, 8 + l * 18, 142));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canInteractWith(EntityPlayer playerIn) {
/* 41 */     return this.dispenserInventory.isUsableByPlayer(playerIn);
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
/* 57 */       if (index < 9) {
/*    */         
/* 59 */         if (!mergeItemStack(itemstack1, 9, 45, true))
/*    */         {
/* 61 */           return ItemStack.field_190927_a;
/*    */         }
/*    */       }
/* 64 */       else if (!mergeItemStack(itemstack1, 0, 9, false)) {
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
/*    */       
/* 78 */       if (itemstack1.func_190916_E() == itemstack.func_190916_E())
/*    */       {
/* 80 */         return ItemStack.field_190927_a;
/*    */       }
/*    */       
/* 83 */       slot.func_190901_a(playerIn, itemstack1);
/*    */     } 
/*    */     
/* 86 */     return itemstack;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\ContainerDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */