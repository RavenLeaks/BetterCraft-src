/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerMerchant
/*     */   extends Container
/*     */ {
/*     */   private final IMerchant theMerchant;
/*     */   private final InventoryMerchant merchantInventory;
/*     */   private final World theWorld;
/*     */   
/*     */   public ContainerMerchant(InventoryPlayer playerInventory, IMerchant merchant, World worldIn) {
/*  20 */     this.theMerchant = merchant;
/*  21 */     this.theWorld = worldIn;
/*  22 */     this.merchantInventory = new InventoryMerchant(playerInventory.player, merchant);
/*  23 */     addSlotToContainer(new Slot(this.merchantInventory, 0, 36, 53));
/*  24 */     addSlotToContainer(new Slot(this.merchantInventory, 1, 62, 53));
/*  25 */     addSlotToContainer(new SlotMerchantResult(playerInventory.player, merchant, this.merchantInventory, 2, 120, 53));
/*     */     
/*  27 */     for (int i = 0; i < 3; i++) {
/*     */       
/*  29 */       for (int j = 0; j < 9; j++)
/*     */       {
/*  31 */         addSlotToContainer(new Slot((IInventory)playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  35 */     for (int k = 0; k < 9; k++)
/*     */     {
/*  37 */       addSlotToContainer(new Slot((IInventory)playerInventory, k, 8 + k * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryMerchant getMerchantInventory() {
/*  43 */     return this.merchantInventory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/*  51 */     this.merchantInventory.resetRecipeAndSlots();
/*  52 */     super.onCraftMatrixChanged(inventoryIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentRecipeIndex(int currentRecipeIndex) {
/*  57 */     this.merchantInventory.setCurrentRecipeIndex(currentRecipeIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/*  65 */     return (this.theMerchant.getCustomer() == playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/*  73 */     ItemStack itemstack = ItemStack.field_190927_a;
/*  74 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/*  76 */     if (slot != null && slot.getHasStack()) {
/*     */       
/*  78 */       ItemStack itemstack1 = slot.getStack();
/*  79 */       itemstack = itemstack1.copy();
/*     */       
/*  81 */       if (index == 2) {
/*     */         
/*  83 */         if (!mergeItemStack(itemstack1, 3, 39, true))
/*     */         {
/*  85 */           return ItemStack.field_190927_a;
/*     */         }
/*     */         
/*  88 */         slot.onSlotChange(itemstack1, itemstack);
/*     */       }
/*  90 */       else if (index != 0 && index != 1) {
/*     */         
/*  92 */         if (index >= 3 && index < 30)
/*     */         {
/*  94 */           if (!mergeItemStack(itemstack1, 30, 39, false))
/*     */           {
/*  96 */             return ItemStack.field_190927_a;
/*     */           }
/*     */         }
/*  99 */         else if (index >= 30 && index < 39 && !mergeItemStack(itemstack1, 3, 30, false))
/*     */         {
/* 101 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       
/* 104 */       } else if (!mergeItemStack(itemstack1, 3, 39, false)) {
/*     */         
/* 106 */         return ItemStack.field_190927_a;
/*     */       } 
/*     */       
/* 109 */       if (itemstack1.func_190926_b()) {
/*     */         
/* 111 */         slot.putStack(ItemStack.field_190927_a);
/*     */       }
/*     */       else {
/*     */         
/* 115 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 118 */       if (itemstack1.func_190916_E() == itemstack.func_190916_E())
/*     */       {
/* 120 */         return ItemStack.field_190927_a;
/*     */       }
/*     */       
/* 123 */       slot.func_190901_a(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 126 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/* 134 */     super.onContainerClosed(playerIn);
/* 135 */     this.theMerchant.setCustomer(null);
/* 136 */     super.onContainerClosed(playerIn);
/*     */     
/* 138 */     if (!this.theWorld.isRemote) {
/*     */       
/* 140 */       ItemStack itemstack = this.merchantInventory.removeStackFromSlot(0);
/*     */       
/* 142 */       if (!itemstack.func_190926_b())
/*     */       {
/* 144 */         playerIn.dropItem(itemstack, false);
/*     */       }
/*     */       
/* 147 */       itemstack = this.merchantInventory.removeStackFromSlot(1);
/*     */       
/* 149 */       if (!itemstack.func_190926_b())
/*     */       {
/* 151 */         playerIn.dropItem(itemstack, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\ContainerMerchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */