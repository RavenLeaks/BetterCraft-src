/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.client.util.RecipeItemHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryCrafting
/*     */   implements IInventory
/*     */ {
/*     */   private final NonNullList<ItemStack> stackList;
/*     */   private final int inventoryWidth;
/*     */   private final int inventoryHeight;
/*     */   private final Container eventHandler;
/*     */   
/*     */   public InventoryCrafting(Container eventHandlerIn, int width, int height) {
/*  26 */     this.stackList = NonNullList.func_191197_a(width * height, ItemStack.field_190927_a);
/*  27 */     this.eventHandler = eventHandlerIn;
/*  28 */     this.inventoryWidth = width;
/*  29 */     this.inventoryHeight = height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  37 */     return this.stackList.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  42 */     for (ItemStack itemstack : this.stackList) {
/*     */       
/*  44 */       if (!itemstack.func_190926_b())
/*     */       {
/*  46 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  50 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  58 */     return (index >= getSizeInventory()) ? ItemStack.field_190927_a : (ItemStack)this.stackList.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInRowAndColumn(int row, int column) {
/*  66 */     return (row >= 0 && row < this.inventoryWidth && column >= 0 && column <= this.inventoryHeight) ? getStackInSlot(row + column * this.inventoryWidth) : ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  74 */     return "container.crafting";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent getDisplayName() {
/*  90 */     return hasCustomName() ? (ITextComponent)new TextComponentString(getName()) : (ITextComponent)new TextComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/*  98 */     return ItemStackHelper.getAndRemove((List<ItemStack>)this.stackList, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 106 */     ItemStack itemstack = ItemStackHelper.getAndSplit((List<ItemStack>)this.stackList, index, count);
/*     */     
/* 108 */     if (!itemstack.func_190926_b())
/*     */     {
/* 110 */       this.eventHandler.onCraftMatrixChanged(this);
/*     */     }
/*     */     
/* 113 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 121 */     this.stackList.set(index, stack);
/* 122 */     this.eventHandler.onCraftMatrixChanged(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 130 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsableByPlayer(EntityPlayer player) {
/* 146 */     return true;
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
/* 163 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 168 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 177 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 182 */     this.stackList.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHeight() {
/* 187 */     return this.inventoryHeight;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 192 */     return this.inventoryWidth;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194018_a(RecipeItemHelper p_194018_1_) {
/* 197 */     for (ItemStack itemstack : this.stackList)
/*     */     {
/* 199 */       p_194018_1_.func_194112_a(itemstack);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\InventoryCrafting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */