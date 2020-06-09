/*     */ package net.minecraft.tileentity;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerDispenser;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ItemStackHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.walkers.ItemStackDataLists;
/*     */ 
/*     */ public class TileEntityDispenser extends TileEntityLockableLoot {
/*  18 */   private static final Random RNG = new Random();
/*  19 */   private NonNullList<ItemStack> stacks = NonNullList.func_191197_a(9, ItemStack.field_190927_a);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  26 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  31 */     for (ItemStack itemstack : this.stacks) {
/*     */       
/*  33 */       if (!itemstack.func_190926_b())
/*     */       {
/*  35 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  39 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDispenseSlot() {
/*  44 */     fillWithLoot((EntityPlayer)null);
/*  45 */     int i = -1;
/*  46 */     int j = 1;
/*     */     
/*  48 */     for (int k = 0; k < this.stacks.size(); k++) {
/*     */       
/*  50 */       if (!((ItemStack)this.stacks.get(k)).func_190926_b() && RNG.nextInt(j++) == 0)
/*     */       {
/*  52 */         i = k;
/*     */       }
/*     */     } 
/*     */     
/*  56 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int addItemStack(ItemStack stack) {
/*  65 */     for (int i = 0; i < this.stacks.size(); i++) {
/*     */       
/*  67 */       if (((ItemStack)this.stacks.get(i)).func_190926_b()) {
/*     */         
/*  69 */         setInventorySlotContents(i, stack);
/*  70 */         return i;
/*     */       } 
/*     */     } 
/*     */     
/*  74 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  82 */     return hasCustomName() ? this.field_190577_o : "container.dispenser";
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixes(DataFixer fixer) {
/*  87 */     fixer.registerWalker(FixTypes.BLOCK_ENTITY, (IDataWalker)new ItemStackDataLists(TileEntityDispenser.class, new String[] { "Items" }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  92 */     super.readFromNBT(compound);
/*  93 */     this.stacks = NonNullList.func_191197_a(getSizeInventory(), ItemStack.field_190927_a);
/*     */     
/*  95 */     if (!checkLootAndRead(compound))
/*     */     {
/*  97 */       ItemStackHelper.func_191283_b(compound, this.stacks);
/*     */     }
/*     */     
/* 100 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/* 102 */       this.field_190577_o = compound.getString("CustomName");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 108 */     super.writeToNBT(compound);
/*     */     
/* 110 */     if (!checkLootAndWrite(compound))
/*     */     {
/* 112 */       ItemStackHelper.func_191282_a(compound, this.stacks);
/*     */     }
/*     */     
/* 115 */     if (hasCustomName())
/*     */     {
/* 117 */       compound.setString("CustomName", this.field_190577_o);
/*     */     }
/*     */     
/* 120 */     return compound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 128 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 133 */     return "minecraft:dispenser";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 138 */     fillWithLoot(playerIn);
/* 139 */     return (Container)new ContainerDispenser((IInventory)playerInventory, (IInventory)this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NonNullList<ItemStack> func_190576_q() {
/* 144 */     return this.stacks;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityDispenser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */