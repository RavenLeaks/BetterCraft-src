/*     */ package net.minecraft.entity.item;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.inventory.ItemStackHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.LockCode;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.storage.loot.ILootContainer;
/*     */ import net.minecraft.world.storage.loot.LootContext;
/*     */ import net.minecraft.world.storage.loot.LootTable;
/*     */ 
/*     */ public abstract class EntityMinecartContainer extends EntityMinecart implements ILockableContainer, ILootContainer {
/*  29 */   private NonNullList<ItemStack> minecartContainerItems = NonNullList.func_191197_a(36, ItemStack.field_190927_a);
/*     */ 
/*     */   
/*     */   private boolean dropContentsWhenDead = true;
/*     */ 
/*     */   
/*     */   private ResourceLocation lootTable;
/*     */   
/*     */   private long lootTableSeed;
/*     */ 
/*     */   
/*     */   public EntityMinecartContainer(World worldIn) {
/*  41 */     super(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityMinecartContainer(World worldIn, double x, double y, double z) {
/*  46 */     super(worldIn, x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public void killMinecart(DamageSource source) {
/*  51 */     super.killMinecart(source);
/*     */     
/*  53 */     if (this.world.getGameRules().getBoolean("doEntityDrops"))
/*     */     {
/*  55 */       InventoryHelper.dropInventoryItems(this.world, this, (IInventory)this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  61 */     for (ItemStack itemstack : this.minecartContainerItems) {
/*     */       
/*  63 */       if (!itemstack.func_190926_b())
/*     */       {
/*  65 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  69 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*  77 */     addLoot((EntityPlayer)null);
/*  78 */     return (ItemStack)this.minecartContainerItems.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  86 */     addLoot((EntityPlayer)null);
/*  87 */     return ItemStackHelper.getAndSplit((List)this.minecartContainerItems, index, count);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/*  95 */     addLoot((EntityPlayer)null);
/*  96 */     ItemStack itemstack = (ItemStack)this.minecartContainerItems.get(index);
/*     */     
/*  98 */     if (itemstack.func_190926_b())
/*     */     {
/* 100 */       return ItemStack.field_190927_a;
/*     */     }
/*     */ 
/*     */     
/* 104 */     this.minecartContainerItems.set(index, ItemStack.field_190927_a);
/* 105 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 114 */     addLoot((EntityPlayer)null);
/* 115 */     this.minecartContainerItems.set(index, stack);
/*     */     
/* 117 */     if (!stack.func_190926_b() && stack.func_190916_E() > getInventoryStackLimit())
/*     */     {
/* 119 */       stack.func_190920_e(getInventoryStackLimit());
/*     */     }
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
/* 136 */     if (this.isDead)
/*     */     {
/* 138 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 142 */     return (player.getDistanceSqToEntity(this) <= 64.0D);
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
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 168 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Entity changeDimension(int dimensionIn) {
/* 174 */     this.dropContentsWhenDead = false;
/* 175 */     return super.changeDimension(dimensionIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDead() {
/* 183 */     if (this.dropContentsWhenDead)
/*     */     {
/* 185 */       InventoryHelper.dropInventoryItems(this.world, this, (IInventory)this);
/*     */     }
/*     */     
/* 188 */     super.setDead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDropItemsWhenDead(boolean dropWhenDead) {
/* 196 */     this.dropContentsWhenDead = dropWhenDead;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_190574_b(DataFixer p_190574_0_, Class<?> p_190574_1_) {
/* 201 */     EntityMinecart.registerFixesMinecart(p_190574_0_, p_190574_1_);
/* 202 */     p_190574_0_.registerWalker(FixTypes.ENTITY, (IDataWalker)new ItemStackDataLists(p_190574_1_, new String[] { "Items" }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound compound) {
/* 210 */     super.writeEntityToNBT(compound);
/*     */     
/* 212 */     if (this.lootTable != null) {
/*     */       
/* 214 */       compound.setString("LootTable", this.lootTable.toString());
/*     */       
/* 216 */       if (this.lootTableSeed != 0L)
/*     */       {
/* 218 */         compound.setLong("LootTableSeed", this.lootTableSeed);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 223 */       ItemStackHelper.func_191282_a(compound, this.minecartContainerItems);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound compound) {
/* 232 */     super.readEntityFromNBT(compound);
/* 233 */     this.minecartContainerItems = NonNullList.func_191197_a(getSizeInventory(), ItemStack.field_190927_a);
/*     */     
/* 235 */     if (compound.hasKey("LootTable", 8)) {
/*     */       
/* 237 */       this.lootTable = new ResourceLocation(compound.getString("LootTable"));
/* 238 */       this.lootTableSeed = compound.getLong("LootTableSeed");
/*     */     }
/*     */     else {
/*     */       
/* 242 */       ItemStackHelper.func_191283_b(compound, this.minecartContainerItems);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInitialInteract(EntityPlayer player, EnumHand stack) {
/* 248 */     if (!this.world.isRemote)
/*     */     {
/* 250 */       player.displayGUIChest((IInventory)this);
/*     */     }
/*     */     
/* 253 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyDrag() {
/* 258 */     float f = 0.98F;
/*     */     
/* 260 */     if (this.lootTable == null) {
/*     */       
/* 262 */       int i = 15 - Container.calcRedstoneFromInventory((IInventory)this);
/* 263 */       f += i * 0.001F;
/*     */     } 
/*     */     
/* 266 */     this.motionX *= f;
/* 267 */     this.motionY *= 0.0D;
/* 268 */     this.motionZ *= f;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 273 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 282 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLocked() {
/* 287 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLockCode(LockCode code) {}
/*     */ 
/*     */   
/*     */   public LockCode getLockCode() {
/* 296 */     return LockCode.EMPTY_CODE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addLoot(@Nullable EntityPlayer player) {
/* 304 */     if (this.lootTable != null) {
/*     */       Random random;
/* 306 */       LootTable loottable = this.world.getLootTableManager().getLootTableFromLocation(this.lootTable);
/* 307 */       this.lootTable = null;
/*     */ 
/*     */       
/* 310 */       if (this.lootTableSeed == 0L) {
/*     */         
/* 312 */         random = new Random();
/*     */       }
/*     */       else {
/*     */         
/* 316 */         random = new Random(this.lootTableSeed);
/*     */       } 
/*     */       
/* 319 */       LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)this.world);
/*     */       
/* 321 */       if (player != null)
/*     */       {
/* 323 */         lootcontext$builder.withLuck(player.getLuck());
/*     */       }
/*     */       
/* 326 */       loottable.fillInventory((IInventory)this, random, lootcontext$builder.build());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 332 */     addLoot((EntityPlayer)null);
/* 333 */     this.minecartContainerItems.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLootTable(ResourceLocation lootTableIn, long lootTableSeedIn) {
/* 338 */     this.lootTable = lootTableIn;
/* 339 */     this.lootTableSeed = lootTableSeedIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResourceLocation getLootTable() {
/* 344 */     return this.lootTable;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityMinecartContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */