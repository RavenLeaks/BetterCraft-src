/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.LockCode;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryLargeChest
/*     */   implements ILockableContainer
/*     */ {
/*     */   private final String name;
/*     */   private final ILockableContainer upperChest;
/*     */   private final ILockableContainer lowerChest;
/*     */   
/*     */   public InventoryLargeChest(String nameIn, ILockableContainer upperChestIn, ILockableContainer lowerChestIn) {
/*  25 */     this.name = nameIn;
/*     */     
/*  27 */     if (upperChestIn == null)
/*     */     {
/*  29 */       upperChestIn = lowerChestIn;
/*     */     }
/*     */     
/*  32 */     if (lowerChestIn == null)
/*     */     {
/*  34 */       lowerChestIn = upperChestIn;
/*     */     }
/*     */     
/*  37 */     this.upperChest = upperChestIn;
/*  38 */     this.lowerChest = lowerChestIn;
/*     */     
/*  40 */     if (upperChestIn.isLocked()) {
/*     */       
/*  42 */       lowerChestIn.setLockCode(upperChestIn.getLockCode());
/*     */     }
/*  44 */     else if (lowerChestIn.isLocked()) {
/*     */       
/*  46 */       upperChestIn.setLockCode(lowerChestIn.getLockCode());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  55 */     return this.upperChest.getSizeInventory() + this.lowerChest.getSizeInventory();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  60 */     return (this.upperChest.func_191420_l() && this.lowerChest.func_191420_l());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPartOfLargeChest(IInventory inventoryIn) {
/*  68 */     return !(this.upperChest != inventoryIn && this.lowerChest != inventoryIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  76 */     if (this.upperChest.hasCustomName())
/*     */     {
/*  78 */       return this.upperChest.getName();
/*     */     }
/*     */ 
/*     */     
/*  82 */     return this.lowerChest.hasCustomName() ? this.lowerChest.getName() : this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/*  91 */     return !(!this.upperChest.hasCustomName() && !this.lowerChest.hasCustomName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent getDisplayName() {
/*  99 */     return hasCustomName() ? (ITextComponent)new TextComponentString(getName()) : (ITextComponent)new TextComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/* 107 */     return (index >= this.upperChest.getSizeInventory()) ? this.lowerChest.getStackInSlot(index - this.upperChest.getSizeInventory()) : this.upperChest.getStackInSlot(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 115 */     return (index >= this.upperChest.getSizeInventory()) ? this.lowerChest.decrStackSize(index - this.upperChest.getSizeInventory(), count) : this.upperChest.decrStackSize(index, count);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 123 */     return (index >= this.upperChest.getSizeInventory()) ? this.lowerChest.removeStackFromSlot(index - this.upperChest.getSizeInventory()) : this.upperChest.removeStackFromSlot(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 131 */     if (index >= this.upperChest.getSizeInventory()) {
/*     */       
/* 133 */       this.lowerChest.setInventorySlotContents(index - this.upperChest.getSizeInventory(), stack);
/*     */     }
/*     */     else {
/*     */       
/* 137 */       this.upperChest.setInventorySlotContents(index, stack);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 146 */     return this.upperChest.getInventoryStackLimit();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 155 */     this.upperChest.markDirty();
/* 156 */     this.lowerChest.markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsableByPlayer(EntityPlayer player) {
/* 164 */     return (this.upperChest.isUsableByPlayer(player) && this.lowerChest.isUsableByPlayer(player));
/*     */   }
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {
/* 169 */     this.upperChest.openInventory(player);
/* 170 */     this.lowerChest.openInventory(player);
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {
/* 175 */     this.upperChest.closeInventory(player);
/* 176 */     this.lowerChest.closeInventory(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isItemValidForSlot(int index, ItemStack stack) {
/* 185 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 190 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 199 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLocked() {
/* 204 */     return !(!this.upperChest.isLocked() && !this.lowerChest.isLocked());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLockCode(LockCode code) {
/* 209 */     this.upperChest.setLockCode(code);
/* 210 */     this.lowerChest.setLockCode(code);
/*     */   }
/*     */ 
/*     */   
/*     */   public LockCode getLockCode() {
/* 215 */     return this.upperChest.getLockCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 220 */     return this.upperChest.getGuiID();
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 225 */     return new ContainerChest((IInventory)playerInventory, (IInventory)this, playerIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 230 */     this.upperChest.clear();
/* 231 */     this.lowerChest.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\InventoryLargeChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */