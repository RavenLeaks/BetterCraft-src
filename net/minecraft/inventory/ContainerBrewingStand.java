/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionHelper;
/*     */ import net.minecraft.potion.PotionType;
/*     */ import net.minecraft.potion.PotionUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerBrewingStand
/*     */   extends Container
/*     */ {
/*     */   private final IInventory tileBrewingStand;
/*     */   private final Slot theSlot;
/*     */   private int prevBrewTime;
/*     */   private int prevFuel;
/*     */   
/*     */   public ContainerBrewingStand(InventoryPlayer playerInventory, IInventory tileBrewingStandIn) {
/*  33 */     this.tileBrewingStand = tileBrewingStandIn;
/*  34 */     addSlotToContainer(new Potion(tileBrewingStandIn, 0, 56, 51));
/*  35 */     addSlotToContainer(new Potion(tileBrewingStandIn, 1, 79, 58));
/*  36 */     addSlotToContainer(new Potion(tileBrewingStandIn, 2, 102, 51));
/*  37 */     this.theSlot = addSlotToContainer(new Ingredient(tileBrewingStandIn, 3, 79, 17));
/*  38 */     addSlotToContainer(new Fuel(tileBrewingStandIn, 4, 17, 17));
/*     */     
/*  40 */     for (int i = 0; i < 3; i++) {
/*     */       
/*  42 */       for (int j = 0; j < 9; j++)
/*     */       {
/*  44 */         addSlotToContainer(new Slot((IInventory)playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  48 */     for (int k = 0; k < 9; k++)
/*     */     {
/*  50 */       addSlotToContainer(new Slot((IInventory)playerInventory, k, 8 + k * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addListener(IContainerListener listener) {
/*  56 */     super.addListener(listener);
/*  57 */     listener.sendAllWindowProperties(this, this.tileBrewingStand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detectAndSendChanges() {
/*  65 */     super.detectAndSendChanges();
/*     */     
/*  67 */     for (int i = 0; i < this.listeners.size(); i++) {
/*     */       
/*  69 */       IContainerListener icontainerlistener = this.listeners.get(i);
/*     */       
/*  71 */       if (this.prevBrewTime != this.tileBrewingStand.getField(0))
/*     */       {
/*  73 */         icontainerlistener.sendProgressBarUpdate(this, 0, this.tileBrewingStand.getField(0));
/*     */       }
/*     */       
/*  76 */       if (this.prevFuel != this.tileBrewingStand.getField(1))
/*     */       {
/*  78 */         icontainerlistener.sendProgressBarUpdate(this, 1, this.tileBrewingStand.getField(1));
/*     */       }
/*     */     } 
/*     */     
/*  82 */     this.prevBrewTime = this.tileBrewingStand.getField(0);
/*  83 */     this.prevFuel = this.tileBrewingStand.getField(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateProgressBar(int id, int data) {
/*  88 */     this.tileBrewingStand.setField(id, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/*  96 */     return this.tileBrewingStand.isUsableByPlayer(playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 104 */     ItemStack itemstack = ItemStack.field_190927_a;
/* 105 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/* 107 */     if (slot != null && slot.getHasStack()) {
/*     */       
/* 109 */       ItemStack itemstack1 = slot.getStack();
/* 110 */       itemstack = itemstack1.copy();
/*     */       
/* 112 */       if ((index < 0 || index > 2) && index != 3 && index != 4) {
/*     */         
/* 114 */         if (this.theSlot.isItemValid(itemstack1))
/*     */         {
/* 116 */           if (!mergeItemStack(itemstack1, 3, 4, false))
/*     */           {
/* 118 */             return ItemStack.field_190927_a;
/*     */           }
/*     */         }
/* 121 */         else if (Potion.canHoldPotion(itemstack) && itemstack.func_190916_E() == 1)
/*     */         {
/* 123 */           if (!mergeItemStack(itemstack1, 0, 3, false))
/*     */           {
/* 125 */             return ItemStack.field_190927_a;
/*     */           }
/*     */         }
/* 128 */         else if (Fuel.isValidBrewingFuel(itemstack))
/*     */         {
/* 130 */           if (!mergeItemStack(itemstack1, 4, 5, false))
/*     */           {
/* 132 */             return ItemStack.field_190927_a;
/*     */           }
/*     */         }
/* 135 */         else if (index >= 5 && index < 32)
/*     */         {
/* 137 */           if (!mergeItemStack(itemstack1, 32, 41, false))
/*     */           {
/* 139 */             return ItemStack.field_190927_a;
/*     */           }
/*     */         }
/* 142 */         else if (index >= 32 && index < 41)
/*     */         {
/* 144 */           if (!mergeItemStack(itemstack1, 5, 32, false))
/*     */           {
/* 146 */             return ItemStack.field_190927_a;
/*     */           }
/*     */         }
/* 149 */         else if (!mergeItemStack(itemstack1, 5, 41, false))
/*     */         {
/* 151 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 156 */         if (!mergeItemStack(itemstack1, 5, 41, true))
/*     */         {
/* 158 */           return ItemStack.field_190927_a;
/*     */         }
/*     */         
/* 161 */         slot.onSlotChange(itemstack1, itemstack);
/*     */       } 
/*     */       
/* 164 */       if (itemstack1.func_190926_b()) {
/*     */         
/* 166 */         slot.putStack(ItemStack.field_190927_a);
/*     */       }
/*     */       else {
/*     */         
/* 170 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 173 */       if (itemstack1.func_190916_E() == itemstack.func_190916_E())
/*     */       {
/* 175 */         return ItemStack.field_190927_a;
/*     */       }
/*     */       
/* 178 */       slot.func_190901_a(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 181 */     return itemstack;
/*     */   }
/*     */   
/*     */   static class Fuel
/*     */     extends Slot
/*     */   {
/*     */     public Fuel(IInventory iInventoryIn, int index, int xPosition, int yPosition) {
/* 188 */       super(iInventoryIn, index, xPosition, yPosition);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isItemValid(ItemStack stack) {
/* 193 */       return isValidBrewingFuel(stack);
/*     */     }
/*     */ 
/*     */     
/*     */     public static boolean isValidBrewingFuel(ItemStack itemStackIn) {
/* 198 */       return (itemStackIn.getItem() == Items.BLAZE_POWDER);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSlotStackLimit() {
/* 203 */       return 64;
/*     */     }
/*     */   }
/*     */   
/*     */   static class Ingredient
/*     */     extends Slot
/*     */   {
/*     */     public Ingredient(IInventory iInventoryIn, int index, int xPosition, int yPosition) {
/* 211 */       super(iInventoryIn, index, xPosition, yPosition);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isItemValid(ItemStack stack) {
/* 216 */       return PotionHelper.isReagent(stack);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSlotStackLimit() {
/* 221 */       return 64;
/*     */     }
/*     */   }
/*     */   
/*     */   static class Potion
/*     */     extends Slot
/*     */   {
/*     */     public Potion(IInventory p_i47598_1_, int p_i47598_2_, int p_i47598_3_, int p_i47598_4_) {
/* 229 */       super(p_i47598_1_, p_i47598_2_, p_i47598_3_, p_i47598_4_);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isItemValid(ItemStack stack) {
/* 234 */       return canHoldPotion(stack);
/*     */     }
/*     */ 
/*     */     
/*     */     public int getSlotStackLimit() {
/* 239 */       return 1;
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack func_190901_a(EntityPlayer p_190901_1_, ItemStack p_190901_2_) {
/* 244 */       PotionType potiontype = PotionUtils.getPotionFromItem(p_190901_2_);
/*     */       
/* 246 */       if (p_190901_1_ instanceof EntityPlayerMP)
/*     */       {
/* 248 */         CriteriaTriggers.field_192130_j.func_192173_a((EntityPlayerMP)p_190901_1_, potiontype);
/*     */       }
/*     */       
/* 251 */       super.func_190901_a(p_190901_1_, p_190901_2_);
/* 252 */       return p_190901_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public static boolean canHoldPotion(ItemStack stack) {
/* 257 */       Item item = stack.getItem();
/* 258 */       return !(item != Items.POTIONITEM && item != Items.SPLASH_POTION && item != Items.LINGERING_POTION && item != Items.GLASS_BOTTLE);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\ContainerBrewingStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */