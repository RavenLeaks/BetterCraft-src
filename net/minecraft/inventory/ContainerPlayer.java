/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemArmor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ 
/*     */ public class ContainerPlayer
/*     */   extends Container {
/*  13 */   private static final EntityEquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EntityEquipmentSlot[] { EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET };
/*     */ 
/*     */   
/*  16 */   public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
/*  17 */   public InventoryCraftResult craftResult = new InventoryCraftResult();
/*     */   
/*     */   public boolean isLocalWorld;
/*     */   
/*     */   private final EntityPlayer thePlayer;
/*     */ 
/*     */   
/*     */   public ContainerPlayer(InventoryPlayer playerInventory, boolean localWorld, EntityPlayer player) {
/*  25 */     this.isLocalWorld = localWorld;
/*  26 */     this.thePlayer = player;
/*  27 */     addSlotToContainer(new SlotCrafting(playerInventory.player, this.craftMatrix, this.craftResult, 0, 154, 28));
/*     */     
/*  29 */     for (int i = 0; i < 2; i++) {
/*     */       
/*  31 */       for (int j = 0; j < 2; j++)
/*     */       {
/*  33 */         addSlotToContainer(new Slot(this.craftMatrix, j + i * 2, 98 + j * 18, 18 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  37 */     for (int k = 0; k < 4; k++) {
/*     */       
/*  39 */       final EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[k];
/*  40 */       addSlotToContainer(new Slot((IInventory)playerInventory, 36 + 3 - k, 8, 8 + k * 18)
/*     */           {
/*     */             public int getSlotStackLimit()
/*     */             {
/*  44 */               return 1;
/*     */             }
/*     */             
/*     */             public boolean isItemValid(ItemStack stack) {
/*  48 */               return (entityequipmentslot == EntityLiving.getSlotForItemStack(stack));
/*     */             }
/*     */             
/*     */             public boolean canTakeStack(EntityPlayer playerIn) {
/*  52 */               ItemStack itemstack = getStack();
/*  53 */               return (!itemstack.func_190926_b() && !playerIn.isCreative() && EnchantmentHelper.func_190938_b(itemstack)) ? false : super.canTakeStack(playerIn);
/*     */             }
/*     */             
/*     */             @Nullable
/*     */             public String getSlotTexture() {
/*  58 */               return ItemArmor.EMPTY_SLOT_NAMES[entityequipmentslot.getIndex()];
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/*  63 */     for (int l = 0; l < 3; l++) {
/*     */       
/*  65 */       for (int j1 = 0; j1 < 9; j1++)
/*     */       {
/*  67 */         addSlotToContainer(new Slot((IInventory)playerInventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
/*     */       }
/*     */     } 
/*     */     
/*  71 */     for (int i1 = 0; i1 < 9; i1++)
/*     */     {
/*  73 */       addSlotToContainer(new Slot((IInventory)playerInventory, i1, 8 + i1 * 18, 142));
/*     */     }
/*     */     
/*  76 */     addSlotToContainer(new Slot((IInventory)playerInventory, 40, 77, 62)
/*     */         {
/*     */           @Nullable
/*     */           public String getSlotTexture()
/*     */           {
/*  81 */             return "minecraft:items/empty_armor_slot_shield";
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/*  91 */     func_192389_a(this.thePlayer.world, this.thePlayer, this.craftMatrix, this.craftResult);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/*  99 */     super.onContainerClosed(playerIn);
/* 100 */     this.craftResult.clear();
/*     */     
/* 102 */     if (!playerIn.world.isRemote)
/*     */     {
/* 104 */       func_193327_a(playerIn, playerIn.world, this.craftMatrix);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/* 113 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 121 */     ItemStack itemstack = ItemStack.field_190927_a;
/* 122 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/* 124 */     if (slot != null && slot.getHasStack()) {
/*     */       
/* 126 */       ItemStack itemstack1 = slot.getStack();
/* 127 */       itemstack = itemstack1.copy();
/* 128 */       EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemstack);
/*     */       
/* 130 */       if (index == 0) {
/*     */         
/* 132 */         if (!mergeItemStack(itemstack1, 9, 45, true))
/*     */         {
/* 134 */           return ItemStack.field_190927_a;
/*     */         }
/*     */         
/* 137 */         slot.onSlotChange(itemstack1, itemstack);
/*     */       }
/* 139 */       else if (index >= 1 && index < 5) {
/*     */         
/* 141 */         if (!mergeItemStack(itemstack1, 9, 45, false))
/*     */         {
/* 143 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 146 */       else if (index >= 5 && index < 9) {
/*     */         
/* 148 */         if (!mergeItemStack(itemstack1, 9, 45, false))
/*     */         {
/* 150 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 153 */       else if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.ARMOR && !((Slot)this.inventorySlots.get(8 - entityequipmentslot.getIndex())).getHasStack()) {
/*     */         
/* 155 */         int i = 8 - entityequipmentslot.getIndex();
/*     */         
/* 157 */         if (!mergeItemStack(itemstack1, i, i + 1, false))
/*     */         {
/* 159 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 162 */       else if (entityequipmentslot == EntityEquipmentSlot.OFFHAND && !((Slot)this.inventorySlots.get(45)).getHasStack()) {
/*     */         
/* 164 */         if (!mergeItemStack(itemstack1, 45, 46, false))
/*     */         {
/* 166 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 169 */       else if (index >= 9 && index < 36) {
/*     */         
/* 171 */         if (!mergeItemStack(itemstack1, 36, 45, false))
/*     */         {
/* 173 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 176 */       else if (index >= 36 && index < 45) {
/*     */         
/* 178 */         if (!mergeItemStack(itemstack1, 9, 36, false))
/*     */         {
/* 180 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 183 */       else if (!mergeItemStack(itemstack1, 9, 45, false)) {
/*     */         
/* 185 */         return ItemStack.field_190927_a;
/*     */       } 
/*     */       
/* 188 */       if (itemstack1.func_190926_b()) {
/*     */         
/* 190 */         slot.putStack(ItemStack.field_190927_a);
/*     */       }
/*     */       else {
/*     */         
/* 194 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 197 */       if (itemstack1.func_190916_E() == itemstack.func_190916_E())
/*     */       {
/* 199 */         return ItemStack.field_190927_a;
/*     */       }
/*     */       
/* 202 */       ItemStack itemstack2 = slot.func_190901_a(playerIn, itemstack1);
/*     */       
/* 204 */       if (index == 0)
/*     */       {
/* 206 */         playerIn.dropItem(itemstack2, false);
/*     */       }
/*     */     } 
/*     */     
/* 210 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
/* 219 */     return (slotIn.inventory != this.craftResult && super.canMergeSlot(stack, slotIn));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\ContainerPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */