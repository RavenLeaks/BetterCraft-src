/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.tileentity.TileEntityFurnace;
/*     */ 
/*     */ public class ContainerFurnace
/*     */   extends Container
/*     */ {
/*     */   private final IInventory tileFurnace;
/*     */   private int cookTime;
/*     */   private int totalCookTime;
/*     */   private int furnaceBurnTime;
/*     */   private int currentItemBurnTime;
/*     */   
/*     */   public ContainerFurnace(InventoryPlayer playerInventory, IInventory furnaceInventory) {
/*  19 */     this.tileFurnace = furnaceInventory;
/*  20 */     addSlotToContainer(new Slot(furnaceInventory, 0, 56, 17));
/*  21 */     addSlotToContainer(new SlotFurnaceFuel(furnaceInventory, 1, 56, 53));
/*  22 */     addSlotToContainer(new SlotFurnaceOutput(playerInventory.player, furnaceInventory, 2, 116, 35));
/*     */     
/*  24 */     for (int i = 0; i < 3; i++) {
/*     */       
/*  26 */       for (int j = 0; j < 9; j++)
/*     */       {
/*  28 */         addSlotToContainer(new Slot((IInventory)playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  32 */     for (int k = 0; k < 9; k++)
/*     */     {
/*  34 */       addSlotToContainer(new Slot((IInventory)playerInventory, k, 8 + k * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addListener(IContainerListener listener) {
/*  40 */     super.addListener(listener);
/*  41 */     listener.sendAllWindowProperties(this, this.tileFurnace);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detectAndSendChanges() {
/*  49 */     super.detectAndSendChanges();
/*     */     
/*  51 */     for (int i = 0; i < this.listeners.size(); i++) {
/*     */       
/*  53 */       IContainerListener icontainerlistener = this.listeners.get(i);
/*     */       
/*  55 */       if (this.cookTime != this.tileFurnace.getField(2))
/*     */       {
/*  57 */         icontainerlistener.sendProgressBarUpdate(this, 2, this.tileFurnace.getField(2));
/*     */       }
/*     */       
/*  60 */       if (this.furnaceBurnTime != this.tileFurnace.getField(0))
/*     */       {
/*  62 */         icontainerlistener.sendProgressBarUpdate(this, 0, this.tileFurnace.getField(0));
/*     */       }
/*     */       
/*  65 */       if (this.currentItemBurnTime != this.tileFurnace.getField(1))
/*     */       {
/*  67 */         icontainerlistener.sendProgressBarUpdate(this, 1, this.tileFurnace.getField(1));
/*     */       }
/*     */       
/*  70 */       if (this.totalCookTime != this.tileFurnace.getField(3))
/*     */       {
/*  72 */         icontainerlistener.sendProgressBarUpdate(this, 3, this.tileFurnace.getField(3));
/*     */       }
/*     */     } 
/*     */     
/*  76 */     this.cookTime = this.tileFurnace.getField(2);
/*  77 */     this.furnaceBurnTime = this.tileFurnace.getField(0);
/*  78 */     this.currentItemBurnTime = this.tileFurnace.getField(1);
/*  79 */     this.totalCookTime = this.tileFurnace.getField(3);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateProgressBar(int id, int data) {
/*  84 */     this.tileFurnace.setField(id, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/*  92 */     return this.tileFurnace.isUsableByPlayer(playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 100 */     ItemStack itemstack = ItemStack.field_190927_a;
/* 101 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/* 103 */     if (slot != null && slot.getHasStack()) {
/*     */       
/* 105 */       ItemStack itemstack1 = slot.getStack();
/* 106 */       itemstack = itemstack1.copy();
/*     */       
/* 108 */       if (index == 2) {
/*     */         
/* 110 */         if (!mergeItemStack(itemstack1, 3, 39, true))
/*     */         {
/* 112 */           return ItemStack.field_190927_a;
/*     */         }
/*     */         
/* 115 */         slot.onSlotChange(itemstack1, itemstack);
/*     */       }
/* 117 */       else if (index != 1 && index != 0) {
/*     */         
/* 119 */         if (!FurnaceRecipes.instance().getSmeltingResult(itemstack1).func_190926_b())
/*     */         {
/* 121 */           if (!mergeItemStack(itemstack1, 0, 1, false))
/*     */           {
/* 123 */             return ItemStack.field_190927_a;
/*     */           }
/*     */         }
/* 126 */         else if (TileEntityFurnace.isItemFuel(itemstack1))
/*     */         {
/* 128 */           if (!mergeItemStack(itemstack1, 1, 2, false))
/*     */           {
/* 130 */             return ItemStack.field_190927_a;
/*     */           }
/*     */         }
/* 133 */         else if (index >= 3 && index < 30)
/*     */         {
/* 135 */           if (!mergeItemStack(itemstack1, 30, 39, false))
/*     */           {
/* 137 */             return ItemStack.field_190927_a;
/*     */           }
/*     */         }
/* 140 */         else if (index >= 30 && index < 39 && !mergeItemStack(itemstack1, 3, 30, false))
/*     */         {
/* 142 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       
/* 145 */       } else if (!mergeItemStack(itemstack1, 3, 39, false)) {
/*     */         
/* 147 */         return ItemStack.field_190927_a;
/*     */       } 
/*     */       
/* 150 */       if (itemstack1.func_190926_b()) {
/*     */         
/* 152 */         slot.putStack(ItemStack.field_190927_a);
/*     */       }
/*     */       else {
/*     */         
/* 156 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 159 */       if (itemstack1.func_190916_E() == itemstack.func_190916_E())
/*     */       {
/* 161 */         return ItemStack.field_190927_a;
/*     */       }
/*     */       
/* 164 */       slot.func_190901_a(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 167 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\ContainerFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */