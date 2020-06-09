/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.item.ItemStack;
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
/*     */ public class Slot
/*     */ {
/*     */   private final int slotIndex;
/*     */   public final IInventory inventory;
/*     */   public int slotNumber;
/*     */   public int xDisplayPosition;
/*     */   public int yDisplayPosition;
/*     */   
/*     */   public Slot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
/*  26 */     this.inventory = inventoryIn;
/*  27 */     this.slotIndex = index;
/*  28 */     this.xDisplayPosition = xPosition;
/*  29 */     this.yDisplayPosition = yPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_) {
/*  37 */     int i = p_75220_2_.func_190916_E() - p_75220_1_.func_190916_E();
/*     */     
/*  39 */     if (i > 0)
/*     */     {
/*  41 */       onCrafting(p_75220_2_, i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCrafting(ItemStack stack, int amount) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_190900_b(int p_190900_1_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onCrafting(ItemStack stack) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack func_190901_a(EntityPlayer p_190901_1_, ItemStack p_190901_2_) {
/*  66 */     onSlotChanged();
/*  67 */     return p_190901_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValid(ItemStack stack) {
/*  75 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStack() {
/*  83 */     return this.inventory.getStackInSlot(this.slotIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getHasStack() {
/*  91 */     return !getStack().func_190926_b();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putStack(ItemStack stack) {
/*  99 */     this.inventory.setInventorySlotContents(this.slotIndex, stack);
/* 100 */     onSlotChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onSlotChanged() {
/* 108 */     this.inventory.markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSlotStackLimit() {
/* 117 */     return this.inventory.getInventoryStackLimit();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemStackLimit(ItemStack stack) {
/* 122 */     return getSlotStackLimit();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public String getSlotTexture() {
/* 128 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int amount) {
/* 137 */     return this.inventory.decrStackSize(this.slotIndex, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHere(IInventory inv, int slotIn) {
/* 145 */     return (inv == this.inventory && slotIn == this.slotIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canTakeStack(EntityPlayer playerIn) {
/* 153 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeHovered() {
/* 162 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\Slot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */