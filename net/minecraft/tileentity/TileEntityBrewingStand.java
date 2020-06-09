/*     */ package net.minecraft.tileentity;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import net.minecraft.block.BlockBrewingStand;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerBrewingStand;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.inventory.ItemStackHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.potion.PotionHelper;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class TileEntityBrewingStand extends TileEntityLockable implements ITickable, ISidedInventory {
/*  29 */   private static final int[] SLOTS_FOR_UP = new int[] { 3 };
/*  30 */   private static final int[] SLOTS_FOR_DOWN = new int[] { 0, 1, 2, 3 };
/*     */ 
/*     */   
/*  33 */   private static final int[] OUTPUT_SLOTS = new int[] { 0, 1, 2, 4 };
/*  34 */   private NonNullList<ItemStack> brewingItemStacks = NonNullList.func_191197_a(5, ItemStack.field_190927_a);
/*     */ 
/*     */   
/*     */   private int brewTime;
/*     */ 
/*     */   
/*     */   private boolean[] filledSlots;
/*     */ 
/*     */   
/*     */   private Item ingredientID;
/*     */ 
/*     */   
/*     */   private String customName;
/*     */ 
/*     */   
/*     */   private int fuel;
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  54 */     return hasCustomName() ? this.customName : "container.brewing";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/*  62 */     return (this.customName != null && !this.customName.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setName(String name) {
/*  67 */     this.customName = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  75 */     return this.brewingItemStacks.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  80 */     for (ItemStack itemstack : this.brewingItemStacks) {
/*     */       
/*  82 */       if (!itemstack.func_190926_b())
/*     */       {
/*  84 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/*  96 */     ItemStack itemstack = (ItemStack)this.brewingItemStacks.get(4);
/*     */     
/*  98 */     if (this.fuel <= 0 && itemstack.getItem() == Items.BLAZE_POWDER) {
/*     */       
/* 100 */       this.fuel = 20;
/* 101 */       itemstack.func_190918_g(1);
/* 102 */       markDirty();
/*     */     } 
/*     */     
/* 105 */     boolean flag = canBrew();
/* 106 */     boolean flag1 = (this.brewTime > 0);
/* 107 */     ItemStack itemstack1 = (ItemStack)this.brewingItemStacks.get(3);
/*     */     
/* 109 */     if (flag1) {
/*     */       
/* 111 */       this.brewTime--;
/* 112 */       boolean flag2 = (this.brewTime == 0);
/*     */       
/* 114 */       if (flag2 && flag)
/*     */       {
/* 116 */         brewPotions();
/* 117 */         markDirty();
/*     */       }
/* 119 */       else if (!flag)
/*     */       {
/* 121 */         this.brewTime = 0;
/* 122 */         markDirty();
/*     */       }
/* 124 */       else if (this.ingredientID != itemstack1.getItem())
/*     */       {
/* 126 */         this.brewTime = 0;
/* 127 */         markDirty();
/*     */       }
/*     */     
/* 130 */     } else if (flag && this.fuel > 0) {
/*     */       
/* 132 */       this.fuel--;
/* 133 */       this.brewTime = 400;
/* 134 */       this.ingredientID = itemstack1.getItem();
/* 135 */       markDirty();
/*     */     } 
/*     */     
/* 138 */     if (!this.world.isRemote) {
/*     */       
/* 140 */       boolean[] aboolean = createFilledSlotsArray();
/*     */       
/* 142 */       if (!Arrays.equals(aboolean, this.filledSlots)) {
/*     */         
/* 144 */         this.filledSlots = aboolean;
/* 145 */         IBlockState iblockstate = this.world.getBlockState(getPos());
/*     */         
/* 147 */         if (!(iblockstate.getBlock() instanceof BlockBrewingStand)) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 152 */         for (int i = 0; i < BlockBrewingStand.HAS_BOTTLE.length; i++)
/*     */         {
/* 154 */           iblockstate = iblockstate.withProperty((IProperty)BlockBrewingStand.HAS_BOTTLE[i], Boolean.valueOf(aboolean[i]));
/*     */         }
/*     */         
/* 157 */         this.world.setBlockState(this.pos, iblockstate, 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean[] createFilledSlotsArray() {
/* 168 */     boolean[] aboolean = new boolean[3];
/*     */     
/* 170 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 172 */       if (!((ItemStack)this.brewingItemStacks.get(i)).func_190926_b())
/*     */       {
/* 174 */         aboolean[i] = true;
/*     */       }
/*     */     } 
/*     */     
/* 178 */     return aboolean;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canBrew() {
/* 183 */     ItemStack itemstack = (ItemStack)this.brewingItemStacks.get(3);
/*     */     
/* 185 */     if (itemstack.func_190926_b())
/*     */     {
/* 187 */       return false;
/*     */     }
/* 189 */     if (!PotionHelper.isReagent(itemstack))
/*     */     {
/* 191 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 195 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 197 */       ItemStack itemstack1 = (ItemStack)this.brewingItemStacks.get(i);
/*     */       
/* 199 */       if (!itemstack1.func_190926_b() && PotionHelper.hasConversions(itemstack1, itemstack))
/*     */       {
/* 201 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 205 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void brewPotions() {
/* 211 */     ItemStack itemstack = (ItemStack)this.brewingItemStacks.get(3);
/*     */     
/* 213 */     for (int i = 0; i < 3; i++)
/*     */     {
/* 215 */       this.brewingItemStacks.set(i, PotionHelper.doReaction(itemstack, (ItemStack)this.brewingItemStacks.get(i)));
/*     */     }
/*     */     
/* 218 */     itemstack.func_190918_g(1);
/* 219 */     BlockPos blockpos = getPos();
/*     */     
/* 221 */     if (itemstack.getItem().hasContainerItem()) {
/*     */       
/* 223 */       ItemStack itemstack1 = new ItemStack(itemstack.getItem().getContainerItem());
/*     */       
/* 225 */       if (itemstack.func_190926_b()) {
/*     */         
/* 227 */         itemstack = itemstack1;
/*     */       }
/*     */       else {
/*     */         
/* 231 */         InventoryHelper.spawnItemStack(this.world, blockpos.getX(), blockpos.getY(), blockpos.getZ(), itemstack1);
/*     */       } 
/*     */     } 
/*     */     
/* 235 */     this.brewingItemStacks.set(3, itemstack);
/* 236 */     this.world.playEvent(1035, blockpos, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesBrewingStand(DataFixer fixer) {
/* 241 */     fixer.registerWalker(FixTypes.BLOCK_ENTITY, (IDataWalker)new ItemStackDataLists(TileEntityBrewingStand.class, new String[] { "Items" }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 246 */     super.readFromNBT(compound);
/* 247 */     this.brewingItemStacks = NonNullList.func_191197_a(getSizeInventory(), ItemStack.field_190927_a);
/* 248 */     ItemStackHelper.func_191283_b(compound, this.brewingItemStacks);
/* 249 */     this.brewTime = compound.getShort("BrewTime");
/*     */     
/* 251 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/* 253 */       this.customName = compound.getString("CustomName");
/*     */     }
/*     */     
/* 256 */     this.fuel = compound.getByte("Fuel");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 261 */     super.writeToNBT(compound);
/* 262 */     compound.setShort("BrewTime", (short)this.brewTime);
/* 263 */     ItemStackHelper.func_191282_a(compound, this.brewingItemStacks);
/*     */     
/* 265 */     if (hasCustomName())
/*     */     {
/* 267 */       compound.setString("CustomName", this.customName);
/*     */     }
/*     */     
/* 270 */     compound.setByte("Fuel", (byte)this.fuel);
/* 271 */     return compound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/* 279 */     return (index >= 0 && index < this.brewingItemStacks.size()) ? (ItemStack)this.brewingItemStacks.get(index) : ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/* 287 */     return ItemStackHelper.getAndSplit((List)this.brewingItemStacks, index, count);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 295 */     return ItemStackHelper.getAndRemove((List)this.brewingItemStacks, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 303 */     if (index >= 0 && index < this.brewingItemStacks.size())
/*     */     {
/* 305 */       this.brewingItemStacks.set(index, stack);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 314 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsableByPlayer(EntityPlayer player) {
/* 322 */     if (this.world.getTileEntity(this.pos) != this)
/*     */     {
/* 324 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 328 */     return (player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D);
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
/* 346 */     if (index == 3)
/*     */     {
/* 348 */       return PotionHelper.isReagent(stack);
/*     */     }
/*     */ 
/*     */     
/* 352 */     Item item = stack.getItem();
/*     */     
/* 354 */     if (index == 4)
/*     */     {
/* 356 */       return (item == Items.BLAZE_POWDER);
/*     */     }
/*     */ 
/*     */     
/* 360 */     return ((item == Items.POTIONITEM || item == Items.SPLASH_POTION || item == Items.LINGERING_POTION || item == Items.GLASS_BOTTLE) && getStackInSlot(index).func_190926_b());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getSlotsForFace(EnumFacing side) {
/* 367 */     if (side == EnumFacing.UP)
/*     */     {
/* 369 */       return SLOTS_FOR_UP;
/*     */     }
/*     */ 
/*     */     
/* 373 */     return (side == EnumFacing.DOWN) ? SLOTS_FOR_DOWN : OUTPUT_SLOTS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
/* 382 */     return isItemValidForSlot(index, itemStackIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
/* 390 */     if (index == 3)
/*     */     {
/* 392 */       return (stack.getItem() == Items.GLASS_BOTTLE);
/*     */     }
/*     */ 
/*     */     
/* 396 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 402 */     return "minecraft:brewing_stand";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 407 */     return (Container)new ContainerBrewingStand(playerInventory, (IInventory)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 412 */     switch (id) {
/*     */       
/*     */       case 0:
/* 415 */         return this.brewTime;
/*     */       
/*     */       case 1:
/* 418 */         return this.fuel;
/*     */     } 
/*     */     
/* 421 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {
/* 427 */     switch (id) {
/*     */       
/*     */       case 0:
/* 430 */         this.brewTime = value;
/*     */         break;
/*     */       
/*     */       case 1:
/* 434 */         this.fuel = value;
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getFieldCount() {
/* 440 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 445 */     this.brewingItemStacks.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityBrewingStand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */