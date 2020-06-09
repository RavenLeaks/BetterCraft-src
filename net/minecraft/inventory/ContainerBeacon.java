/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerBeacon
/*     */   extends Container
/*     */ {
/*     */   private final IInventory tileBeacon;
/*     */   private final BeaconSlot beaconSlot;
/*     */   
/*     */   public ContainerBeacon(IInventory playerInventory, IInventory tileBeaconIn) {
/*  19 */     this.tileBeacon = tileBeaconIn;
/*  20 */     this.beaconSlot = new BeaconSlot(tileBeaconIn, 0, 136, 110);
/*  21 */     addSlotToContainer(this.beaconSlot);
/*  22 */     int i = 36;
/*  23 */     int j = 137;
/*     */     
/*  25 */     for (int k = 0; k < 3; k++) {
/*     */       
/*  27 */       for (int l = 0; l < 9; l++)
/*     */       {
/*  29 */         addSlotToContainer(new Slot(playerInventory, l + k * 9 + 9, 36 + l * 18, 137 + k * 18));
/*     */       }
/*     */     } 
/*     */     
/*  33 */     for (int i1 = 0; i1 < 9; i1++)
/*     */     {
/*  35 */       addSlotToContainer(new Slot(playerInventory, i1, 36 + i1 * 18, 195));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addListener(IContainerListener listener) {
/*  41 */     super.addListener(listener);
/*  42 */     listener.sendAllWindowProperties(this, this.tileBeacon);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateProgressBar(int id, int data) {
/*  47 */     this.tileBeacon.setField(id, data);
/*     */   }
/*     */ 
/*     */   
/*     */   public IInventory getTileEntity() {
/*  52 */     return this.tileBeacon;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/*  60 */     super.onContainerClosed(playerIn);
/*     */     
/*  62 */     if (!playerIn.world.isRemote) {
/*     */       
/*  64 */       ItemStack itemstack = this.beaconSlot.decrStackSize(this.beaconSlot.getSlotStackLimit());
/*     */       
/*  66 */       if (!itemstack.func_190926_b())
/*     */       {
/*  68 */         playerIn.dropItem(itemstack, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/*  78 */     return this.tileBeacon.isUsableByPlayer(playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/*  86 */     ItemStack itemstack = ItemStack.field_190927_a;
/*  87 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/*  89 */     if (slot != null && slot.getHasStack()) {
/*     */       
/*  91 */       ItemStack itemstack1 = slot.getStack();
/*  92 */       itemstack = itemstack1.copy();
/*     */       
/*  94 */       if (index == 0) {
/*     */         
/*  96 */         if (!mergeItemStack(itemstack1, 1, 37, true))
/*     */         {
/*  98 */           return ItemStack.field_190927_a;
/*     */         }
/*     */         
/* 101 */         slot.onSlotChange(itemstack1, itemstack);
/*     */       }
/* 103 */       else if (!this.beaconSlot.getHasStack() && this.beaconSlot.isItemValid(itemstack1) && itemstack1.func_190916_E() == 1) {
/*     */         
/* 105 */         if (!mergeItemStack(itemstack1, 0, 1, false))
/*     */         {
/* 107 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 110 */       else if (index >= 1 && index < 28) {
/*     */         
/* 112 */         if (!mergeItemStack(itemstack1, 28, 37, false))
/*     */         {
/* 114 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 117 */       else if (index >= 28 && index < 37) {
/*     */         
/* 119 */         if (!mergeItemStack(itemstack1, 1, 28, false))
/*     */         {
/* 121 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 124 */       else if (!mergeItemStack(itemstack1, 1, 37, false)) {
/*     */         
/* 126 */         return ItemStack.field_190927_a;
/*     */       } 
/*     */       
/* 129 */       if (itemstack1.func_190926_b()) {
/*     */         
/* 131 */         slot.putStack(ItemStack.field_190927_a);
/*     */       }
/*     */       else {
/*     */         
/* 135 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 138 */       if (itemstack1.func_190916_E() == itemstack.func_190916_E())
/*     */       {
/* 140 */         return ItemStack.field_190927_a;
/*     */       }
/*     */       
/* 143 */       slot.func_190901_a(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 146 */     return itemstack;
/*     */   }
/*     */   
/*     */   class BeaconSlot
/*     */     extends Slot
/*     */   {
/*     */     public BeaconSlot(IInventory inventoryIn, int index, int xIn, int yIn) {
/* 153 */       super(inventoryIn, index, xIn, yIn);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isItemValid(ItemStack stack) {
/* 158 */       Item item = stack.getItem();
/* 159 */       return !(item != Items.EMERALD && item != Items.DIAMOND && item != Items.GOLD_INGOT && item != Items.IRON_INGOT);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSlotStackLimit() {
/* 164 */       return 1;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\ContainerBeacon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */