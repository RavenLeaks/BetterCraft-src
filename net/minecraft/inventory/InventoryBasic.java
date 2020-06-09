/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ 
/*     */ public class InventoryBasic
/*     */   implements IInventory
/*     */ {
/*     */   private String inventoryTitle;
/*     */   private final int slotsCount;
/*     */   private final NonNullList<ItemStack> inventoryContents;
/*     */   private List<IInventoryChangedListener> changeListeners;
/*     */   private boolean hasCustomName;
/*     */   
/*     */   public InventoryBasic(String title, boolean customName, int slotCount) {
/*  22 */     this.inventoryTitle = title;
/*  23 */     this.hasCustomName = customName;
/*  24 */     this.slotsCount = slotCount;
/*  25 */     this.inventoryContents = NonNullList.func_191197_a(slotCount, ItemStack.field_190927_a);
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryBasic(ITextComponent title, int slotCount) {
/*  30 */     this(title.getUnformattedText(), true, slotCount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addInventoryChangeListener(IInventoryChangedListener listener) {
/*  38 */     if (this.changeListeners == null)
/*     */     {
/*  40 */       this.changeListeners = Lists.newArrayList();
/*     */     }
/*     */     
/*  43 */     this.changeListeners.add(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeInventoryChangeListener(IInventoryChangedListener listener) {
/*  51 */     this.changeListeners.remove(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  59 */     return (index >= 0 && index < this.inventoryContents.size()) ? (ItemStack)this.inventoryContents.get(index) : ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  67 */     ItemStack itemstack = ItemStackHelper.getAndSplit((List<ItemStack>)this.inventoryContents, index, count);
/*     */     
/*  69 */     if (!itemstack.func_190926_b())
/*     */     {
/*  71 */       markDirty();
/*     */     }
/*     */     
/*  74 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack addItem(ItemStack stack) {
/*  79 */     ItemStack itemstack = stack.copy();
/*     */     
/*  81 */     for (int i = 0; i < this.slotsCount; i++) {
/*     */       
/*  83 */       ItemStack itemstack1 = getStackInSlot(i);
/*     */       
/*  85 */       if (itemstack1.func_190926_b()) {
/*     */         
/*  87 */         setInventorySlotContents(i, itemstack);
/*  88 */         markDirty();
/*  89 */         return ItemStack.field_190927_a;
/*     */       } 
/*     */       
/*  92 */       if (ItemStack.areItemsEqual(itemstack1, itemstack)) {
/*     */         
/*  94 */         int j = Math.min(getInventoryStackLimit(), itemstack1.getMaxStackSize());
/*  95 */         int k = Math.min(itemstack.func_190916_E(), j - itemstack1.func_190916_E());
/*     */         
/*  97 */         if (k > 0) {
/*     */           
/*  99 */           itemstack1.func_190917_f(k);
/* 100 */           itemstack.func_190918_g(k);
/*     */           
/* 102 */           if (itemstack.func_190926_b()) {
/*     */             
/* 104 */             markDirty();
/* 105 */             return ItemStack.field_190927_a;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     if (itemstack.func_190916_E() != stack.func_190916_E())
/*     */     {
/* 113 */       markDirty();
/*     */     }
/*     */     
/* 116 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 124 */     ItemStack itemstack = (ItemStack)this.inventoryContents.get(index);
/*     */     
/* 126 */     if (itemstack.func_190926_b())
/*     */     {
/* 128 */       return ItemStack.field_190927_a;
/*     */     }
/*     */ 
/*     */     
/* 132 */     this.inventoryContents.set(index, ItemStack.field_190927_a);
/* 133 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 142 */     this.inventoryContents.set(index, stack);
/*     */     
/* 144 */     if (!stack.func_190926_b() && stack.func_190916_E() > getInventoryStackLimit())
/*     */     {
/* 146 */       stack.func_190920_e(getInventoryStackLimit());
/*     */     }
/*     */     
/* 149 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/* 157 */     return this.slotsCount;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/* 162 */     for (ItemStack itemstack : this.inventoryContents) {
/*     */       
/* 164 */       if (!itemstack.func_190926_b())
/*     */       {
/* 166 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 170 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 178 */     return this.inventoryTitle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 186 */     return this.hasCustomName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomName(String inventoryTitleIn) {
/* 194 */     this.hasCustomName = true;
/* 195 */     this.inventoryTitle = inventoryTitleIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent getDisplayName() {
/* 203 */     return hasCustomName() ? (ITextComponent)new TextComponentString(getName()) : (ITextComponent)new TextComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 211 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 220 */     if (this.changeListeners != null)
/*     */     {
/* 222 */       for (int i = 0; i < this.changeListeners.size(); i++)
/*     */       {
/* 224 */         ((IInventoryChangedListener)this.changeListeners.get(i)).onInventoryChanged(this);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsableByPlayer(EntityPlayer player) {
/* 234 */     return true;
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
/* 251 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 256 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 265 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 270 */     this.inventoryContents.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\InventoryBasic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */