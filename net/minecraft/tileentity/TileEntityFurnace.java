/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFurnace;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerFurnace;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.inventory.ItemStackHelper;
/*     */ import net.minecraft.inventory.SlotFurnaceFuel;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemHoe;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.ItemSword;
/*     */ import net.minecraft.item.ItemTool;
/*     */ import net.minecraft.item.crafting.FurnaceRecipes;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.walkers.ItemStackDataLists;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class TileEntityFurnace
/*     */   extends TileEntityLockable implements ITickable, ISidedInventory {
/*  35 */   private static final int[] SLOTS_TOP = new int[1];
/*  36 */   private static final int[] SLOTS_BOTTOM = new int[] { 2, 1 };
/*  37 */   private static final int[] SLOTS_SIDES = new int[] { 1 };
/*  38 */   private NonNullList<ItemStack> furnaceItemStacks = NonNullList.func_191197_a(3, ItemStack.field_190927_a);
/*     */ 
/*     */   
/*     */   private int furnaceBurnTime;
/*     */ 
/*     */   
/*     */   private int currentItemBurnTime;
/*     */ 
/*     */   
/*     */   private int cookTime;
/*     */ 
/*     */   
/*     */   private int totalCookTime;
/*     */   
/*     */   private String furnaceCustomName;
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  56 */     return this.furnaceItemStacks.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  61 */     for (ItemStack itemstack : this.furnaceItemStacks) {
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
/*  77 */     return (ItemStack)this.furnaceItemStacks.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  85 */     return ItemStackHelper.getAndSplit((List)this.furnaceItemStacks, index, count);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/*  93 */     return ItemStackHelper.getAndRemove((List)this.furnaceItemStacks, index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 101 */     ItemStack itemstack = (ItemStack)this.furnaceItemStacks.get(index);
/* 102 */     boolean flag = (!stack.func_190926_b() && stack.isItemEqual(itemstack) && ItemStack.areItemStackTagsEqual(stack, itemstack));
/* 103 */     this.furnaceItemStacks.set(index, stack);
/*     */     
/* 105 */     if (stack.func_190916_E() > getInventoryStackLimit())
/*     */     {
/* 107 */       stack.func_190920_e(getInventoryStackLimit());
/*     */     }
/*     */     
/* 110 */     if (index == 0 && !flag) {
/*     */       
/* 112 */       this.totalCookTime = getCookTime(stack);
/* 113 */       this.cookTime = 0;
/* 114 */       markDirty();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 123 */     return hasCustomName() ? this.furnaceCustomName : "container.furnace";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 131 */     return (this.furnaceCustomName != null && !this.furnaceCustomName.isEmpty());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomInventoryName(String p_145951_1_) {
/* 136 */     this.furnaceCustomName = p_145951_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesFurnace(DataFixer fixer) {
/* 141 */     fixer.registerWalker(FixTypes.BLOCK_ENTITY, (IDataWalker)new ItemStackDataLists(TileEntityFurnace.class, new String[] { "Items" }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 146 */     super.readFromNBT(compound);
/* 147 */     this.furnaceItemStacks = NonNullList.func_191197_a(getSizeInventory(), ItemStack.field_190927_a);
/* 148 */     ItemStackHelper.func_191283_b(compound, this.furnaceItemStacks);
/* 149 */     this.furnaceBurnTime = compound.getShort("BurnTime");
/* 150 */     this.cookTime = compound.getShort("CookTime");
/* 151 */     this.totalCookTime = compound.getShort("CookTimeTotal");
/* 152 */     this.currentItemBurnTime = getItemBurnTime((ItemStack)this.furnaceItemStacks.get(1));
/*     */     
/* 154 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/* 156 */       this.furnaceCustomName = compound.getString("CustomName");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 162 */     super.writeToNBT(compound);
/* 163 */     compound.setShort("BurnTime", (short)this.furnaceBurnTime);
/* 164 */     compound.setShort("CookTime", (short)this.cookTime);
/* 165 */     compound.setShort("CookTimeTotal", (short)this.totalCookTime);
/* 166 */     ItemStackHelper.func_191282_a(compound, this.furnaceItemStacks);
/*     */     
/* 168 */     if (hasCustomName())
/*     */     {
/* 170 */       compound.setString("CustomName", this.furnaceCustomName);
/*     */     }
/*     */     
/* 173 */     return compound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 181 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/* 189 */     return (this.furnaceBurnTime > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isBurning(IInventory inventory) {
/* 194 */     return (inventory.getField(0) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 202 */     boolean flag = isBurning();
/* 203 */     boolean flag1 = false;
/*     */     
/* 205 */     if (isBurning())
/*     */     {
/* 207 */       this.furnaceBurnTime--;
/*     */     }
/*     */     
/* 210 */     if (!this.world.isRemote) {
/*     */       
/* 212 */       ItemStack itemstack = (ItemStack)this.furnaceItemStacks.get(1);
/*     */       
/* 214 */       if (isBurning() || (!itemstack.func_190926_b() && !((ItemStack)this.furnaceItemStacks.get(0)).func_190926_b())) {
/*     */         
/* 216 */         if (!isBurning() && canSmelt()) {
/*     */           
/* 218 */           this.furnaceBurnTime = getItemBurnTime(itemstack);
/* 219 */           this.currentItemBurnTime = this.furnaceBurnTime;
/*     */           
/* 221 */           if (isBurning()) {
/*     */             
/* 223 */             flag1 = true;
/*     */             
/* 225 */             if (!itemstack.func_190926_b()) {
/*     */               
/* 227 */               Item item = itemstack.getItem();
/* 228 */               itemstack.func_190918_g(1);
/*     */               
/* 230 */               if (itemstack.func_190926_b()) {
/*     */                 
/* 232 */                 Item item1 = item.getContainerItem();
/* 233 */                 this.furnaceItemStacks.set(1, (item1 == null) ? ItemStack.field_190927_a : new ItemStack(item1));
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 239 */         if (isBurning() && canSmelt()) {
/*     */           
/* 241 */           this.cookTime++;
/*     */           
/* 243 */           if (this.cookTime == this.totalCookTime)
/*     */           {
/* 245 */             this.cookTime = 0;
/* 246 */             this.totalCookTime = getCookTime((ItemStack)this.furnaceItemStacks.get(0));
/* 247 */             smeltItem();
/* 248 */             flag1 = true;
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 253 */           this.cookTime = 0;
/*     */         }
/*     */       
/* 256 */       } else if (!isBurning() && this.cookTime > 0) {
/*     */         
/* 258 */         this.cookTime = MathHelper.clamp(this.cookTime - 2, 0, this.totalCookTime);
/*     */       } 
/*     */       
/* 261 */       if (flag != isBurning()) {
/*     */         
/* 263 */         flag1 = true;
/* 264 */         BlockFurnace.setState(isBurning(), this.world, this.pos);
/*     */       } 
/*     */     } 
/*     */     
/* 268 */     if (flag1)
/*     */     {
/* 270 */       markDirty();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCookTime(ItemStack stack) {
/* 276 */     return 200;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canSmelt() {
/* 284 */     if (((ItemStack)this.furnaceItemStacks.get(0)).func_190926_b())
/*     */     {
/* 286 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 290 */     ItemStack itemstack = FurnaceRecipes.instance().getSmeltingResult((ItemStack)this.furnaceItemStacks.get(0));
/*     */     
/* 292 */     if (itemstack.func_190926_b())
/*     */     {
/* 294 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 298 */     ItemStack itemstack1 = (ItemStack)this.furnaceItemStacks.get(2);
/*     */     
/* 300 */     if (itemstack1.func_190926_b())
/*     */     {
/* 302 */       return true;
/*     */     }
/* 304 */     if (!itemstack1.isItemEqual(itemstack))
/*     */     {
/* 306 */       return false;
/*     */     }
/* 308 */     if (itemstack1.func_190916_E() < getInventoryStackLimit() && itemstack1.func_190916_E() < itemstack1.getMaxStackSize())
/*     */     {
/* 310 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 314 */     return (itemstack1.func_190916_E() < itemstack.getMaxStackSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void smeltItem() {
/* 325 */     if (canSmelt()) {
/*     */       
/* 327 */       ItemStack itemstack = (ItemStack)this.furnaceItemStacks.get(0);
/* 328 */       ItemStack itemstack1 = FurnaceRecipes.instance().getSmeltingResult(itemstack);
/* 329 */       ItemStack itemstack2 = (ItemStack)this.furnaceItemStacks.get(2);
/*     */       
/* 331 */       if (itemstack2.func_190926_b()) {
/*     */         
/* 333 */         this.furnaceItemStacks.set(2, itemstack1.copy());
/*     */       }
/* 335 */       else if (itemstack2.getItem() == itemstack1.getItem()) {
/*     */         
/* 337 */         itemstack2.func_190917_f(1);
/*     */       } 
/*     */       
/* 340 */       if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SPONGE) && itemstack.getMetadata() == 1 && !((ItemStack)this.furnaceItemStacks.get(1)).func_190926_b() && ((ItemStack)this.furnaceItemStacks.get(1)).getItem() == Items.BUCKET)
/*     */       {
/* 342 */         this.furnaceItemStacks.set(1, new ItemStack(Items.WATER_BUCKET));
/*     */       }
/*     */       
/* 345 */       itemstack.func_190918_g(1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getItemBurnTime(ItemStack stack) {
/* 355 */     if (stack.func_190926_b())
/*     */     {
/* 357 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 361 */     Item item = stack.getItem();
/*     */     
/* 363 */     if (item == Item.getItemFromBlock((Block)Blocks.WOODEN_SLAB))
/*     */     {
/* 365 */       return 150;
/*     */     }
/* 367 */     if (item == Item.getItemFromBlock(Blocks.WOOL))
/*     */     {
/* 369 */       return 100;
/*     */     }
/* 371 */     if (item == Item.getItemFromBlock(Blocks.CARPET))
/*     */     {
/* 373 */       return 67;
/*     */     }
/* 375 */     if (item == Item.getItemFromBlock(Blocks.LADDER))
/*     */     {
/* 377 */       return 300;
/*     */     }
/* 379 */     if (item == Item.getItemFromBlock(Blocks.WOODEN_BUTTON))
/*     */     {
/* 381 */       return 100;
/*     */     }
/* 383 */     if (Block.getBlockFromItem(item).getDefaultState().getMaterial() == Material.WOOD)
/*     */     {
/* 385 */       return 300;
/*     */     }
/* 387 */     if (item == Item.getItemFromBlock(Blocks.COAL_BLOCK))
/*     */     {
/* 389 */       return 16000;
/*     */     }
/* 391 */     if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName()))
/*     */     {
/* 393 */       return 200;
/*     */     }
/* 395 */     if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName()))
/*     */     {
/* 397 */       return 200;
/*     */     }
/* 399 */     if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName()))
/*     */     {
/* 401 */       return 200;
/*     */     }
/* 403 */     if (item == Items.STICK)
/*     */     {
/* 405 */       return 100;
/*     */     }
/* 407 */     if (item != Items.BOW && item != Items.FISHING_ROD) {
/*     */       
/* 409 */       if (item == Items.SIGN)
/*     */       {
/* 411 */         return 200;
/*     */       }
/* 413 */       if (item == Items.COAL)
/*     */       {
/* 415 */         return 1600;
/*     */       }
/* 417 */       if (item == Items.LAVA_BUCKET)
/*     */       {
/* 419 */         return 20000;
/*     */       }
/* 421 */       if (item != Item.getItemFromBlock(Blocks.SAPLING) && item != Items.BOWL) {
/*     */         
/* 423 */         if (item == Items.BLAZE_ROD)
/*     */         {
/* 425 */           return 2400;
/*     */         }
/* 427 */         if (item instanceof net.minecraft.item.ItemDoor && item != Items.IRON_DOOR)
/*     */         {
/* 429 */           return 200;
/*     */         }
/*     */ 
/*     */         
/* 433 */         return (item instanceof net.minecraft.item.ItemBoat) ? 400 : 0;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 438 */       return 100;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 443 */     return 300;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean isItemFuel(ItemStack stack) {
/* 450 */     return (getItemBurnTime(stack) > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsableByPlayer(EntityPlayer player) {
/* 458 */     if (this.world.getTileEntity(this.pos) != this)
/*     */     {
/* 460 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 464 */     return (player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D);
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
/* 482 */     if (index == 2)
/*     */     {
/* 484 */       return false;
/*     */     }
/* 486 */     if (index != 1)
/*     */     {
/* 488 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 492 */     ItemStack itemstack = (ItemStack)this.furnaceItemStacks.get(1);
/* 493 */     return !(!isItemFuel(stack) && (!SlotFurnaceFuel.isBucket(stack) || itemstack.getItem() == Items.BUCKET));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getSlotsForFace(EnumFacing side) {
/* 499 */     if (side == EnumFacing.DOWN)
/*     */     {
/* 501 */       return SLOTS_BOTTOM;
/*     */     }
/*     */ 
/*     */     
/* 505 */     return (side == EnumFacing.UP) ? SLOTS_TOP : SLOTS_SIDES;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
/* 514 */     return isItemValidForSlot(index, itemStackIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
/* 522 */     if (direction == EnumFacing.DOWN && index == 1) {
/*     */       
/* 524 */       Item item = stack.getItem();
/*     */       
/* 526 */       if (item != Items.WATER_BUCKET && item != Items.BUCKET)
/*     */       {
/* 528 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 532 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 537 */     return "minecraft:furnace";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 542 */     return (Container)new ContainerFurnace(playerInventory, (IInventory)this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 547 */     switch (id) {
/*     */       
/*     */       case 0:
/* 550 */         return this.furnaceBurnTime;
/*     */       
/*     */       case 1:
/* 553 */         return this.currentItemBurnTime;
/*     */       
/*     */       case 2:
/* 556 */         return this.cookTime;
/*     */       
/*     */       case 3:
/* 559 */         return this.totalCookTime;
/*     */     } 
/*     */     
/* 562 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {
/* 568 */     switch (id) {
/*     */       
/*     */       case 0:
/* 571 */         this.furnaceBurnTime = value;
/*     */         break;
/*     */       
/*     */       case 1:
/* 575 */         this.currentItemBurnTime = value;
/*     */         break;
/*     */       
/*     */       case 2:
/* 579 */         this.cookTime = value;
/*     */         break;
/*     */       
/*     */       case 3:
/* 583 */         this.totalCookTime = value;
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getFieldCount() {
/* 589 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 594 */     this.furnaceItemStacks.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityFurnace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */