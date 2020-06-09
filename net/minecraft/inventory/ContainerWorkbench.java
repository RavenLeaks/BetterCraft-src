/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class ContainerWorkbench
/*     */   extends Container
/*     */ {
/*  13 */   public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);
/*  14 */   public InventoryCraftResult craftResult = new InventoryCraftResult();
/*     */   
/*     */   private final World worldObj;
/*     */   
/*     */   private final BlockPos pos;
/*     */   
/*     */   private final EntityPlayer field_192390_i;
/*     */   
/*     */   public ContainerWorkbench(InventoryPlayer playerInventory, World worldIn, BlockPos posIn) {
/*  23 */     this.worldObj = worldIn;
/*  24 */     this.pos = posIn;
/*  25 */     this.field_192390_i = playerInventory.player;
/*  26 */     addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 124, 35));
/*     */     
/*  28 */     for (int i = 0; i < 3; i++) {
/*     */       
/*  30 */       for (int j = 0; j < 3; j++)
/*     */       {
/*  32 */         addSlotToContainer(new Slot(this.craftMatrix, j + i * 3, 30 + j * 18, 17 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  36 */     for (int k = 0; k < 3; k++) {
/*     */       
/*  38 */       for (int i1 = 0; i1 < 9; i1++)
/*     */       {
/*  40 */         addSlotToContainer(new Slot((IInventory)playerInventory, i1 + k * 9 + 9, 8 + i1 * 18, 84 + k * 18));
/*     */       }
/*     */     } 
/*     */     
/*  44 */     for (int l = 0; l < 9; l++)
/*     */     {
/*  46 */       addSlotToContainer(new Slot((IInventory)playerInventory, l, 8 + l * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/*  55 */     func_192389_a(this.worldObj, this.field_192390_i, this.craftMatrix, this.craftResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/*  63 */     super.onContainerClosed(playerIn);
/*     */     
/*  65 */     if (!this.worldObj.isRemote)
/*     */     {
/*  67 */       func_193327_a(playerIn, this.worldObj, this.craftMatrix);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/*  76 */     if (this.worldObj.getBlockState(this.pos).getBlock() != Blocks.CRAFTING_TABLE)
/*     */     {
/*  78 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  82 */     return (playerIn.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/*  91 */     ItemStack itemstack = ItemStack.field_190927_a;
/*  92 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/*  94 */     if (slot != null && slot.getHasStack()) {
/*     */       
/*  96 */       ItemStack itemstack1 = slot.getStack();
/*  97 */       itemstack = itemstack1.copy();
/*     */       
/*  99 */       if (index == 0) {
/*     */         
/* 101 */         itemstack1.getItem().onCreated(itemstack1, this.worldObj, playerIn);
/*     */         
/* 103 */         if (!mergeItemStack(itemstack1, 10, 46, true))
/*     */         {
/* 105 */           return ItemStack.field_190927_a;
/*     */         }
/*     */         
/* 108 */         slot.onSlotChange(itemstack1, itemstack);
/*     */       }
/* 110 */       else if (index >= 10 && index < 37) {
/*     */         
/* 112 */         if (!mergeItemStack(itemstack1, 37, 46, false))
/*     */         {
/* 114 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 117 */       else if (index >= 37 && index < 46) {
/*     */         
/* 119 */         if (!mergeItemStack(itemstack1, 10, 37, false))
/*     */         {
/* 121 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 124 */       else if (!mergeItemStack(itemstack1, 10, 46, false)) {
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
/* 143 */       ItemStack itemstack2 = slot.func_190901_a(playerIn, itemstack1);
/*     */       
/* 145 */       if (index == 0)
/*     */       {
/* 147 */         playerIn.dropItem(itemstack2, false);
/*     */       }
/*     */     } 
/*     */     
/* 151 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
/* 160 */     return (slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\ContainerWorkbench.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */