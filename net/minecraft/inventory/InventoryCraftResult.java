/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ 
/*     */ public class InventoryCraftResult implements IInventory {
/*  14 */   private final NonNullList<ItemStack> stackResult = NonNullList.func_191197_a(1, ItemStack.field_190927_a);
/*     */ 
/*     */   
/*     */   private IRecipe field_193057_b;
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  22 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  27 */     for (ItemStack itemstack : this.stackResult) {
/*     */       
/*  29 */       if (!itemstack.func_190926_b())
/*     */       {
/*  31 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  35 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  43 */     return (ItemStack)this.stackResult.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  51 */     return "Result";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/*  59 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent getDisplayName() {
/*  67 */     return hasCustomName() ? (ITextComponent)new TextComponentString(getName()) : (ITextComponent)new TextComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  75 */     return ItemStackHelper.getAndRemove((List<ItemStack>)this.stackResult, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/*  83 */     return ItemStackHelper.getAndRemove((List<ItemStack>)this.stackResult, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/*  91 */     this.stackResult.set(0, stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/*  99 */     return 64;
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
/* 115 */     return true;
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
/* 132 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 137 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 146 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 151 */     this.stackResult.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_193056_a(@Nullable IRecipe p_193056_1_) {
/* 156 */     this.field_193057_b = p_193056_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IRecipe func_193055_i() {
/* 162 */     return this.field_193057_b;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\InventoryCraftResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */