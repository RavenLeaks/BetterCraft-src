/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ItemStackHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.storage.loot.ILootContainer;
/*     */ import net.minecraft.world.storage.loot.LootContext;
/*     */ import net.minecraft.world.storage.loot.LootTable;
/*     */ 
/*     */ public abstract class TileEntityLockableLoot extends TileEntityLockable implements ILootContainer {
/*     */   protected ResourceLocation lootTable;
/*     */   protected long lootTableSeed;
/*     */   protected String field_190577_o;
/*     */   
/*     */   protected boolean checkLootAndRead(NBTTagCompound compound) {
/*  24 */     if (compound.hasKey("LootTable", 8)) {
/*     */       
/*  26 */       this.lootTable = new ResourceLocation(compound.getString("LootTable"));
/*  27 */       this.lootTableSeed = compound.getLong("LootTableSeed");
/*  28 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  32 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkLootAndWrite(NBTTagCompound compound) {
/*  38 */     if (this.lootTable != null) {
/*     */       
/*  40 */       compound.setString("LootTable", this.lootTable.toString());
/*     */       
/*  42 */       if (this.lootTableSeed != 0L)
/*     */       {
/*  44 */         compound.setLong("LootTableSeed", this.lootTableSeed);
/*     */       }
/*     */       
/*  47 */       return true;
/*     */     } 
/*     */ 
/*     */     
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fillWithLoot(@Nullable EntityPlayer player) {
/*  57 */     if (this.lootTable != null) {
/*     */       Random random;
/*  59 */       LootTable loottable = this.world.getLootTableManager().getLootTableFromLocation(this.lootTable);
/*  60 */       this.lootTable = null;
/*     */ 
/*     */       
/*  63 */       if (this.lootTableSeed == 0L) {
/*     */         
/*  65 */         random = new Random();
/*     */       }
/*     */       else {
/*     */         
/*  69 */         random = new Random(this.lootTableSeed);
/*     */       } 
/*     */       
/*  72 */       LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)this.world);
/*     */       
/*  74 */       if (player != null)
/*     */       {
/*  76 */         lootcontext$builder.withLuck(player.getLuck());
/*     */       }
/*     */       
/*  79 */       loottable.fillInventory((IInventory)this, random, lootcontext$builder.build());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getLootTable() {
/*  85 */     return this.lootTable;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLootTable(ResourceLocation p_189404_1_, long p_189404_2_) {
/*  90 */     this.lootTable = p_189404_1_;
/*  91 */     this.lootTableSeed = p_189404_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/*  99 */     return (this.field_190577_o != null && !this.field_190577_o.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190575_a(String p_190575_1_) {
/* 104 */     this.field_190577_o = p_190575_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/* 112 */     fillWithLoot((EntityPlayer)null);
/* 113 */     return (ItemStack)func_190576_q().get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 121 */     fillWithLoot((EntityPlayer)null);
/* 122 */     ItemStack itemstack = ItemStackHelper.getAndSplit((List)func_190576_q(), index, count);
/*     */     
/* 124 */     if (!itemstack.func_190926_b())
/*     */     {
/* 126 */       markDirty();
/*     */     }
/*     */     
/* 129 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 137 */     fillWithLoot((EntityPlayer)null);
/* 138 */     return ItemStackHelper.getAndRemove((List)func_190576_q(), index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, @Nullable ItemStack stack) {
/* 146 */     fillWithLoot((EntityPlayer)null);
/* 147 */     func_190576_q().set(index, stack);
/*     */     
/* 149 */     if (stack.func_190916_E() > getInventoryStackLimit())
/*     */     {
/* 151 */       stack.func_190920_e(getInventoryStackLimit());
/*     */     }
/*     */     
/* 154 */     markDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsableByPlayer(EntityPlayer player) {
/* 162 */     if (this.world.getTileEntity(this.pos) != this)
/*     */     {
/* 164 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 168 */     return (player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D);
/*     */   }
/*     */ 
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
/* 186 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 191 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 200 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 205 */     fillWithLoot((EntityPlayer)null);
/* 206 */     func_190576_q().clear();
/*     */   }
/*     */   
/*     */   protected abstract NonNullList<ItemStack> func_190576_q();
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityLockableLoot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */