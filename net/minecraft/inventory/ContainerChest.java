/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ContainerChest
/*     */   extends Container
/*     */ {
/*     */   private final IInventory lowerChestInventory;
/*     */   private final int numRows;
/*     */   
/*     */   public ContainerChest(IInventory playerInventory, IInventory chestInventory, EntityPlayer player) {
/*  13 */     this.lowerChestInventory = chestInventory;
/*  14 */     this.numRows = chestInventory.getSizeInventory() / 9;
/*  15 */     chestInventory.openInventory(player);
/*  16 */     int i = (this.numRows - 4) * 18;
/*     */     
/*  18 */     for (int j = 0; j < this.numRows; j++) {
/*     */       
/*  20 */       for (int k = 0; k < 9; k++)
/*     */       {
/*  22 */         addSlotToContainer(new Slot(chestInventory, k + j * 9, 8 + k * 18, 18 + j * 18));
/*     */       }
/*     */     } 
/*     */     
/*  26 */     for (int l = 0; l < 3; l++) {
/*     */       
/*  28 */       for (int j1 = 0; j1 < 9; j1++)
/*     */       {
/*  30 */         addSlotToContainer(new Slot(playerInventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
/*     */       }
/*     */     } 
/*     */     
/*  34 */     for (int i1 = 0; i1 < 9; i1++)
/*     */     {
/*  36 */       addSlotToContainer(new Slot(playerInventory, i1, 8 + i1 * 18, 161 + i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/*  45 */     return this.lowerChestInventory.isUsableByPlayer(playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/*  53 */     ItemStack itemstack = ItemStack.field_190927_a;
/*  54 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/*  56 */     if (slot != null && slot.getHasStack()) {
/*     */       
/*  58 */       ItemStack itemstack1 = slot.getStack();
/*  59 */       itemstack = itemstack1.copy();
/*     */       
/*  61 */       if (index < this.numRows * 9) {
/*     */         
/*  63 */         if (!mergeItemStack(itemstack1, this.numRows * 9, this.inventorySlots.size(), true))
/*     */         {
/*  65 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/*  68 */       else if (!mergeItemStack(itemstack1, 0, this.numRows * 9, false)) {
/*     */         
/*  70 */         return ItemStack.field_190927_a;
/*     */       } 
/*     */       
/*  73 */       if (itemstack1.func_190926_b()) {
/*     */         
/*  75 */         slot.putStack(ItemStack.field_190927_a);
/*     */       }
/*     */       else {
/*     */         
/*  79 */         slot.onSlotChanged();
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/*  91 */     super.onContainerClosed(playerIn);
/*  92 */     this.lowerChestInventory.closeInventory(playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IInventory getLowerChestInventory() {
/* 100 */     return this.lowerChestInventory;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\ContainerChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */