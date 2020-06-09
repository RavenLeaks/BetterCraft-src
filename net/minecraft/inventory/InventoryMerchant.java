/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.IMerchant;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.village.MerchantRecipe;
/*     */ import net.minecraft.village.MerchantRecipeList;
/*     */ 
/*     */ public class InventoryMerchant implements IInventory {
/*     */   private final IMerchant theMerchant;
/*  16 */   private final NonNullList<ItemStack> theInventory = NonNullList.func_191197_a(3, ItemStack.field_190927_a);
/*     */   
/*     */   private final EntityPlayer thePlayer;
/*     */   private MerchantRecipe currentRecipe;
/*     */   private int currentRecipeIndex;
/*     */   
/*     */   public InventoryMerchant(EntityPlayer thePlayerIn, IMerchant theMerchantIn) {
/*  23 */     this.thePlayer = thePlayerIn;
/*  24 */     this.theMerchant = theMerchantIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  32 */     return this.theInventory.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  37 */     for (ItemStack itemstack : this.theInventory) {
/*     */       
/*  39 */       if (!itemstack.func_190926_b())
/*     */       {
/*  41 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  45 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  53 */     return (ItemStack)this.theInventory.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  61 */     ItemStack itemstack = (ItemStack)this.theInventory.get(index);
/*     */     
/*  63 */     if (index == 2 && !itemstack.func_190926_b())
/*     */     {
/*  65 */       return ItemStackHelper.getAndSplit((List<ItemStack>)this.theInventory, index, itemstack.func_190916_E());
/*     */     }
/*     */ 
/*     */     
/*  69 */     ItemStack itemstack1 = ItemStackHelper.getAndSplit((List<ItemStack>)this.theInventory, index, count);
/*     */     
/*  71 */     if (!itemstack1.func_190926_b() && inventoryResetNeededOnSlotChange(index))
/*     */     {
/*  73 */       resetRecipeAndSlots();
/*     */     }
/*     */     
/*  76 */     return itemstack1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean inventoryResetNeededOnSlotChange(int slotIn) {
/*  85 */     return !(slotIn != 0 && slotIn != 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/*  93 */     return ItemStackHelper.getAndRemove((List<ItemStack>)this.theInventory, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 101 */     this.theInventory.set(index, stack);
/*     */     
/* 103 */     if (!stack.func_190926_b() && stack.func_190916_E() > getInventoryStackLimit())
/*     */     {
/* 105 */       stack.func_190920_e(getInventoryStackLimit());
/*     */     }
/*     */     
/* 108 */     if (inventoryResetNeededOnSlotChange(index))
/*     */     {
/* 110 */       resetRecipeAndSlots();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 119 */     return "mob.villager";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 127 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent getDisplayName() {
/* 135 */     return hasCustomName() ? (ITextComponent)new TextComponentString(getName()) : (ITextComponent)new TextComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 143 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsableByPlayer(EntityPlayer player) {
/* 151 */     return (this.theMerchant.getCustomer() == player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 168 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 177 */     resetRecipeAndSlots();
/*     */   }
/*     */ 
/*     */   
/*     */   public void resetRecipeAndSlots() {
/* 182 */     this.currentRecipe = null;
/* 183 */     ItemStack itemstack = (ItemStack)this.theInventory.get(0);
/* 184 */     ItemStack itemstack1 = (ItemStack)this.theInventory.get(1);
/*     */     
/* 186 */     if (itemstack.func_190926_b()) {
/*     */       
/* 188 */       itemstack = itemstack1;
/* 189 */       itemstack1 = ItemStack.field_190927_a;
/*     */     } 
/*     */     
/* 192 */     if (itemstack.func_190926_b()) {
/*     */       
/* 194 */       setInventorySlotContents(2, ItemStack.field_190927_a);
/*     */     }
/*     */     else {
/*     */       
/* 198 */       MerchantRecipeList merchantrecipelist = this.theMerchant.getRecipes(this.thePlayer);
/*     */       
/* 200 */       if (merchantrecipelist != null) {
/*     */         
/* 202 */         MerchantRecipe merchantrecipe = merchantrecipelist.canRecipeBeUsed(itemstack, itemstack1, this.currentRecipeIndex);
/*     */         
/* 204 */         if (merchantrecipe != null && !merchantrecipe.isRecipeDisabled()) {
/*     */           
/* 206 */           this.currentRecipe = merchantrecipe;
/* 207 */           setInventorySlotContents(2, merchantrecipe.getItemToSell().copy());
/*     */         }
/* 209 */         else if (!itemstack1.func_190926_b()) {
/*     */           
/* 211 */           merchantrecipe = merchantrecipelist.canRecipeBeUsed(itemstack1, itemstack, this.currentRecipeIndex);
/*     */           
/* 213 */           if (merchantrecipe != null && !merchantrecipe.isRecipeDisabled())
/*     */           {
/* 215 */             this.currentRecipe = merchantrecipe;
/* 216 */             setInventorySlotContents(2, merchantrecipe.getItemToSell().copy());
/*     */           }
/*     */           else
/*     */           {
/* 220 */             setInventorySlotContents(2, ItemStack.field_190927_a);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 225 */           setInventorySlotContents(2, ItemStack.field_190927_a);
/*     */         } 
/*     */       } 
/*     */       
/* 229 */       this.theMerchant.verifySellingItem(getStackInSlot(2));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MerchantRecipe getCurrentRecipe() {
/* 235 */     return this.currentRecipe;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentRecipeIndex(int currentRecipeIndexIn) {
/* 240 */     this.currentRecipeIndex = currentRecipeIndexIn;
/* 241 */     resetRecipeAndSlots();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 246 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 255 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 260 */     this.theInventory.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\InventoryMerchant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */