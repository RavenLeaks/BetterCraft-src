/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.passive.AbstractChestHorse;
/*     */ import net.minecraft.entity.passive.AbstractHorse;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ContainerHorseInventory
/*     */   extends Container {
/*     */   private final IInventory horseInventory;
/*     */   private final AbstractHorse theHorse;
/*     */   
/*     */   public ContainerHorseInventory(IInventory playerInventory, IInventory horseInventoryIn, final AbstractHorse horse, EntityPlayer player) {
/*  16 */     this.horseInventory = horseInventoryIn;
/*  17 */     this.theHorse = horse;
/*  18 */     int i = 3;
/*  19 */     horseInventoryIn.openInventory(player);
/*  20 */     int j = -18;
/*  21 */     addSlotToContainer(new Slot(horseInventoryIn, 0, 8, 18)
/*     */         {
/*     */           public boolean isItemValid(ItemStack stack)
/*     */           {
/*  25 */             return (stack.getItem() == Items.SADDLE && !getHasStack() && horse.func_190685_dA());
/*     */           }
/*     */           
/*     */           public boolean canBeHovered() {
/*  29 */             return horse.func_190685_dA();
/*     */           }
/*     */         });
/*  32 */     addSlotToContainer(new Slot(horseInventoryIn, 1, 8, 36)
/*     */         {
/*     */           public boolean isItemValid(ItemStack stack)
/*     */           {
/*  36 */             return horse.func_190682_f(stack);
/*     */           }
/*     */           
/*     */           public boolean canBeHovered() {
/*  40 */             return horse.func_190677_dK();
/*     */           }
/*     */           
/*     */           public int getSlotStackLimit() {
/*  44 */             return 1;
/*     */           }
/*     */         });
/*     */     
/*  48 */     if (horse instanceof AbstractChestHorse && ((AbstractChestHorse)horse).func_190695_dh())
/*     */     {
/*  50 */       for (int k = 0; k < 3; k++) {
/*     */         
/*  52 */         for (int l = 0; l < ((AbstractChestHorse)horse).func_190696_dl(); l++)
/*     */         {
/*  54 */           addSlotToContainer(new Slot(horseInventoryIn, 2 + l + k * ((AbstractChestHorse)horse).func_190696_dl(), 80 + l * 18, 18 + k * 18));
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/*  59 */     for (int i1 = 0; i1 < 3; i1++) {
/*     */       
/*  61 */       for (int k1 = 0; k1 < 9; k1++)
/*     */       {
/*  63 */         addSlotToContainer(new Slot(playerInventory, k1 + i1 * 9 + 9, 8 + k1 * 18, 102 + i1 * 18 + -18));
/*     */       }
/*     */     } 
/*     */     
/*  67 */     for (int j1 = 0; j1 < 9; j1++)
/*     */     {
/*  69 */       addSlotToContainer(new Slot(playerInventory, j1, 8 + j1 * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/*  78 */     return (this.horseInventory.isUsableByPlayer(playerIn) && this.theHorse.isEntityAlive() && this.theHorse.getDistanceToEntity((Entity)playerIn) < 8.0F);
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
/*  94 */       if (index < this.horseInventory.getSizeInventory()) {
/*     */         
/*  96 */         if (!mergeItemStack(itemstack1, this.horseInventory.getSizeInventory(), this.inventorySlots.size(), true))
/*     */         {
/*  98 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 101 */       else if (getSlot(1).isItemValid(itemstack1) && !getSlot(1).getHasStack()) {
/*     */         
/* 103 */         if (!mergeItemStack(itemstack1, 1, 2, false))
/*     */         {
/* 105 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 108 */       else if (getSlot(0).isItemValid(itemstack1)) {
/*     */         
/* 110 */         if (!mergeItemStack(itemstack1, 0, 1, false))
/*     */         {
/* 112 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 115 */       else if (this.horseInventory.getSizeInventory() <= 2 || !mergeItemStack(itemstack1, 2, this.horseInventory.getSizeInventory(), false)) {
/*     */         
/* 117 */         return ItemStack.field_190927_a;
/*     */       } 
/*     */       
/* 120 */       if (itemstack1.func_190926_b()) {
/*     */         
/* 122 */         slot.putStack(ItemStack.field_190927_a);
/*     */       }
/*     */       else {
/*     */         
/* 126 */         slot.onSlotChanged();
/*     */       } 
/*     */     } 
/*     */     
/* 130 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/* 138 */     super.onContainerClosed(playerIn);
/* 139 */     this.horseInventory.closeInventory(playerIn);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\ContainerHorseInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */