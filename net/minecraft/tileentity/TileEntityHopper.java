/*     */ package net.minecraft.tileentity;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.block.BlockHopper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerHopper;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ISidedInventory;
/*     */ import net.minecraft.inventory.ItemStackHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.walkers.ItemStackDataLists;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TileEntityHopper extends TileEntityLockableLoot implements IHopper, ITickable {
/*  33 */   private NonNullList<ItemStack> inventory = NonNullList.func_191197_a(5, ItemStack.field_190927_a);
/*  34 */   private int transferCooldown = -1;
/*     */   
/*     */   private long field_190578_g;
/*     */   
/*     */   public static void registerFixesHopper(DataFixer fixer) {
/*  39 */     fixer.registerWalker(FixTypes.BLOCK_ENTITY, (IDataWalker)new ItemStackDataLists(TileEntityHopper.class, new String[] { "Items" }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  44 */     super.readFromNBT(compound);
/*  45 */     this.inventory = NonNullList.func_191197_a(getSizeInventory(), ItemStack.field_190927_a);
/*     */     
/*  47 */     if (!checkLootAndRead(compound))
/*     */     {
/*  49 */       ItemStackHelper.func_191283_b(compound, this.inventory);
/*     */     }
/*     */     
/*  52 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/*  54 */       this.field_190577_o = compound.getString("CustomName");
/*     */     }
/*     */     
/*  57 */     this.transferCooldown = compound.getInteger("TransferCooldown");
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/*  62 */     super.writeToNBT(compound);
/*     */     
/*  64 */     if (!checkLootAndWrite(compound))
/*     */     {
/*  66 */       ItemStackHelper.func_191282_a(compound, this.inventory);
/*     */     }
/*     */     
/*  69 */     compound.setInteger("TransferCooldown", this.transferCooldown);
/*     */     
/*  71 */     if (hasCustomName())
/*     */     {
/*  73 */       compound.setString("CustomName", this.field_190577_o);
/*     */     }
/*     */     
/*  76 */     return compound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  84 */     return this.inventory.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*  92 */     fillWithLoot((EntityPlayer)null);
/*  93 */     ItemStack itemstack = ItemStackHelper.getAndSplit((List)func_190576_q(), index, count);
/*  94 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 102 */     fillWithLoot((EntityPlayer)null);
/* 103 */     func_190576_q().set(index, stack);
/*     */     
/* 105 */     if (stack.func_190916_E() > getInventoryStackLimit())
/*     */     {
/* 107 */       stack.func_190920_e(getInventoryStackLimit());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 116 */     return hasCustomName() ? this.field_190577_o : "container.hopper";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 124 */     return 64;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 132 */     if (this.world != null && !this.world.isRemote) {
/*     */       
/* 134 */       this.transferCooldown--;
/* 135 */       this.field_190578_g = this.world.getTotalWorldTime();
/*     */       
/* 137 */       if (!isOnTransferCooldown()) {
/*     */         
/* 139 */         setTransferCooldown(0);
/* 140 */         updateHopper();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean updateHopper() {
/* 147 */     if (this.world != null && !this.world.isRemote) {
/*     */       
/* 149 */       if (!isOnTransferCooldown() && BlockHopper.isEnabled(getBlockMetadata())) {
/*     */         
/* 151 */         boolean flag = false;
/*     */         
/* 153 */         if (!isEmpty())
/*     */         {
/* 155 */           flag = transferItemsOut();
/*     */         }
/*     */         
/* 158 */         if (!isFull())
/*     */         {
/* 160 */           flag = !(!captureDroppedItems(this) && !flag);
/*     */         }
/*     */         
/* 163 */         if (flag) {
/*     */           
/* 165 */           setTransferCooldown(8);
/* 166 */           markDirty();
/* 167 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 171 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 175 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isEmpty() {
/* 181 */     for (ItemStack itemstack : this.inventory) {
/*     */       
/* 183 */       if (!itemstack.func_190926_b())
/*     */       {
/* 185 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 189 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/* 194 */     return isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isFull() {
/* 199 */     for (ItemStack itemstack : this.inventory) {
/*     */       
/* 201 */       if (itemstack.func_190926_b() || itemstack.func_190916_E() != itemstack.getMaxStackSize())
/*     */       {
/* 203 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 207 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean transferItemsOut() {
/* 212 */     IInventory iinventory = getInventoryForHopperTransfer();
/*     */     
/* 214 */     if (iinventory == null)
/*     */     {
/* 216 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 220 */     EnumFacing enumfacing = BlockHopper.getFacing(getBlockMetadata()).getOpposite();
/*     */     
/* 222 */     if (isInventoryFull(iinventory, enumfacing))
/*     */     {
/* 224 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 228 */     for (int i = 0; i < getSizeInventory(); i++) {
/*     */       
/* 230 */       if (!getStackInSlot(i).func_190926_b()) {
/*     */         
/* 232 */         ItemStack itemstack = getStackInSlot(i).copy();
/* 233 */         ItemStack itemstack1 = putStackInInventoryAllSlots(this, iinventory, decrStackSize(i, 1), enumfacing);
/*     */         
/* 235 */         if (itemstack1.func_190926_b()) {
/*     */           
/* 237 */           iinventory.markDirty();
/* 238 */           return true;
/*     */         } 
/*     */         
/* 241 */         setInventorySlotContents(i, itemstack);
/*     */       } 
/*     */     } 
/*     */     
/* 245 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isInventoryFull(IInventory inventoryIn, EnumFacing side) {
/* 255 */     if (inventoryIn instanceof ISidedInventory) {
/*     */       
/* 257 */       ISidedInventory isidedinventory = (ISidedInventory)inventoryIn;
/* 258 */       int[] aint = isidedinventory.getSlotsForFace(side); byte b;
/*     */       int i, arrayOfInt1[];
/* 260 */       for (i = (arrayOfInt1 = aint).length, b = 0; b < i; ) { int k = arrayOfInt1[b];
/*     */         
/* 262 */         ItemStack itemstack1 = isidedinventory.getStackInSlot(k);
/*     */         
/* 264 */         if (itemstack1.func_190926_b() || itemstack1.func_190916_E() != itemstack1.getMaxStackSize())
/*     */         {
/* 266 */           return false;
/*     */         }
/*     */         
/*     */         b++; }
/*     */     
/*     */     } else {
/* 272 */       int i = inventoryIn.getSizeInventory();
/*     */       
/* 274 */       for (int j = 0; j < i; j++) {
/*     */         
/* 276 */         ItemStack itemstack = inventoryIn.getStackInSlot(j);
/*     */         
/* 278 */         if (itemstack.func_190926_b() || itemstack.func_190916_E() != itemstack.getMaxStackSize())
/*     */         {
/* 280 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 285 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean isInventoryEmpty(IInventory inventoryIn, EnumFacing side) {
/* 293 */     if (inventoryIn instanceof ISidedInventory) {
/*     */       
/* 295 */       ISidedInventory isidedinventory = (ISidedInventory)inventoryIn;
/* 296 */       int[] aint = isidedinventory.getSlotsForFace(side); byte b;
/*     */       int i, arrayOfInt1[];
/* 298 */       for (i = (arrayOfInt1 = aint).length, b = 0; b < i; ) { int j = arrayOfInt1[b];
/*     */         
/* 300 */         if (!isidedinventory.getStackInSlot(j).func_190926_b())
/*     */         {
/* 302 */           return false;
/*     */         }
/*     */         
/*     */         b++; }
/*     */     
/*     */     } else {
/* 308 */       int j = inventoryIn.getSizeInventory();
/*     */       
/* 310 */       for (int k = 0; k < j; k++) {
/*     */         
/* 312 */         if (!inventoryIn.getStackInSlot(k).func_190926_b())
/*     */         {
/* 314 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 319 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean captureDroppedItems(IHopper hopper) {
/* 324 */     IInventory iinventory = getHopperInventory(hopper);
/*     */     
/* 326 */     if (iinventory != null) {
/*     */       
/* 328 */       EnumFacing enumfacing = EnumFacing.DOWN;
/*     */       
/* 330 */       if (isInventoryEmpty(iinventory, enumfacing))
/*     */       {
/* 332 */         return false;
/*     */       }
/*     */       
/* 335 */       if (iinventory instanceof ISidedInventory) {
/*     */         
/* 337 */         ISidedInventory isidedinventory = (ISidedInventory)iinventory;
/* 338 */         int[] aint = isidedinventory.getSlotsForFace(enumfacing); byte b;
/*     */         int i, arrayOfInt1[];
/* 340 */         for (i = (arrayOfInt1 = aint).length, b = 0; b < i; ) { int j = arrayOfInt1[b];
/*     */           
/* 342 */           if (pullItemFromSlot(hopper, iinventory, j, enumfacing))
/*     */           {
/* 344 */             return true;
/*     */           }
/*     */           
/*     */           b++; }
/*     */       
/*     */       } else {
/* 350 */         int j = iinventory.getSizeInventory();
/*     */         
/* 352 */         for (int k = 0; k < j; k++)
/*     */         {
/* 354 */           if (pullItemFromSlot(hopper, iinventory, k, enumfacing))
/*     */           {
/* 356 */             return true;
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 363 */       for (EntityItem entityitem : getCaptureItems(hopper.getWorld(), hopper.getXPos(), hopper.getYPos(), hopper.getZPos())) {
/*     */         
/* 365 */         if (putDropInInventoryAllSlots((IInventory)null, hopper, entityitem))
/*     */         {
/* 367 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 372 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean pullItemFromSlot(IHopper hopper, IInventory inventoryIn, int index, EnumFacing direction) {
/* 381 */     ItemStack itemstack = inventoryIn.getStackInSlot(index);
/*     */     
/* 383 */     if (!itemstack.func_190926_b() && canExtractItemFromSlot(inventoryIn, itemstack, index, direction)) {
/*     */       
/* 385 */       ItemStack itemstack1 = itemstack.copy();
/* 386 */       ItemStack itemstack2 = putStackInInventoryAllSlots(inventoryIn, hopper, inventoryIn.decrStackSize(index, 1), (EnumFacing)null);
/*     */       
/* 388 */       if (itemstack2.func_190926_b()) {
/*     */         
/* 390 */         inventoryIn.markDirty();
/* 391 */         return true;
/*     */       } 
/*     */       
/* 394 */       inventoryIn.setInventorySlotContents(index, itemstack1);
/*     */     } 
/*     */     
/* 397 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean putDropInInventoryAllSlots(IInventory p_145898_0_, IInventory itemIn, EntityItem p_145898_2_) {
/* 406 */     boolean flag = false;
/*     */     
/* 408 */     if (p_145898_2_ == null)
/*     */     {
/* 410 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 414 */     ItemStack itemstack = p_145898_2_.getEntityItem().copy();
/* 415 */     ItemStack itemstack1 = putStackInInventoryAllSlots(p_145898_0_, itemIn, itemstack, (EnumFacing)null);
/*     */     
/* 417 */     if (itemstack1.func_190926_b()) {
/*     */       
/* 419 */       flag = true;
/* 420 */       p_145898_2_.setDead();
/*     */     }
/*     */     else {
/*     */       
/* 424 */       p_145898_2_.setEntityItemStack(itemstack1);
/*     */     } 
/*     */     
/* 427 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemStack putStackInInventoryAllSlots(IInventory inventoryIn, IInventory stack, ItemStack side, @Nullable EnumFacing p_174918_3_) {
/* 436 */     if (stack instanceof ISidedInventory && p_174918_3_ != null) {
/*     */       
/* 438 */       ISidedInventory isidedinventory = (ISidedInventory)stack;
/* 439 */       int[] aint = isidedinventory.getSlotsForFace(p_174918_3_);
/*     */       
/* 441 */       for (int k = 0; k < aint.length && !side.func_190926_b(); k++)
/*     */       {
/* 443 */         side = insertStack(inventoryIn, stack, side, aint[k], p_174918_3_);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 448 */       int i = stack.getSizeInventory();
/*     */       
/* 450 */       for (int j = 0; j < i && !side.func_190926_b(); j++)
/*     */       {
/* 452 */         side = insertStack(inventoryIn, stack, side, j, p_174918_3_);
/*     */       }
/*     */     } 
/*     */     
/* 456 */     return side;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canInsertItemInSlot(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side) {
/* 464 */     if (!inventoryIn.isItemValidForSlot(index, stack))
/*     */     {
/* 466 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 470 */     return !(inventoryIn instanceof ISidedInventory && !((ISidedInventory)inventoryIn).canInsertItem(index, stack, side));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean canExtractItemFromSlot(IInventory inventoryIn, ItemStack stack, int index, EnumFacing side) {
/* 479 */     return !(inventoryIn instanceof ISidedInventory && !((ISidedInventory)inventoryIn).canExtractItem(index, stack, side));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ItemStack insertStack(IInventory inventoryIn, IInventory stack, ItemStack index, int side, EnumFacing p_174916_4_) {
/* 487 */     ItemStack itemstack = stack.getStackInSlot(side);
/*     */     
/* 489 */     if (canInsertItemInSlot(stack, index, side, p_174916_4_)) {
/*     */       
/* 491 */       boolean flag = false;
/* 492 */       boolean flag1 = stack.func_191420_l();
/*     */       
/* 494 */       if (itemstack.func_190926_b()) {
/*     */         
/* 496 */         stack.setInventorySlotContents(side, index);
/* 497 */         index = ItemStack.field_190927_a;
/* 498 */         flag = true;
/*     */       }
/* 500 */       else if (canCombine(itemstack, index)) {
/*     */         
/* 502 */         int i = index.getMaxStackSize() - itemstack.func_190916_E();
/* 503 */         int j = Math.min(index.func_190916_E(), i);
/* 504 */         index.func_190918_g(j);
/* 505 */         itemstack.func_190917_f(j);
/* 506 */         flag = (j > 0);
/*     */       } 
/*     */       
/* 509 */       if (flag) {
/*     */         
/* 511 */         if (flag1 && stack instanceof TileEntityHopper) {
/*     */           
/* 513 */           TileEntityHopper tileentityhopper1 = (TileEntityHopper)stack;
/*     */           
/* 515 */           if (!tileentityhopper1.mayTransfer()) {
/*     */             
/* 517 */             int k = 0;
/*     */             
/* 519 */             if (inventoryIn != null && inventoryIn instanceof TileEntityHopper) {
/*     */               
/* 521 */               TileEntityHopper tileentityhopper = (TileEntityHopper)inventoryIn;
/*     */               
/* 523 */               if (tileentityhopper1.field_190578_g >= tileentityhopper.field_190578_g)
/*     */               {
/* 525 */                 k = 1;
/*     */               }
/*     */             } 
/*     */             
/* 529 */             tileentityhopper1.setTransferCooldown(8 - k);
/*     */           } 
/*     */         } 
/*     */         
/* 533 */         stack.markDirty();
/*     */       } 
/*     */     } 
/*     */     
/* 537 */     return index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private IInventory getInventoryForHopperTransfer() {
/* 545 */     EnumFacing enumfacing = BlockHopper.getFacing(getBlockMetadata());
/* 546 */     return getInventoryAtPosition(getWorld(), getXPos() + enumfacing.getFrontOffsetX(), getYPos() + enumfacing.getFrontOffsetY(), getZPos() + enumfacing.getFrontOffsetZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IInventory getHopperInventory(IHopper hopper) {
/* 554 */     return getInventoryAtPosition(hopper.getWorld(), hopper.getXPos(), hopper.getYPos() + 1.0D, hopper.getZPos());
/*     */   }
/*     */ 
/*     */   
/*     */   public static List<EntityItem> getCaptureItems(World worldIn, double p_184292_1_, double p_184292_3_, double p_184292_5_) {
/* 559 */     return worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(p_184292_1_ - 0.5D, p_184292_3_, p_184292_5_ - 0.5D, p_184292_1_ + 0.5D, p_184292_3_ + 1.5D, p_184292_5_ + 0.5D), EntitySelectors.IS_ALIVE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static IInventory getInventoryAtPosition(World worldIn, double x, double y, double z) {
/*     */     ILockableContainer iLockableContainer;
/* 567 */     IInventory iInventory1, iinventory = null;
/* 568 */     int i = MathHelper.floor(x);
/* 569 */     int j = MathHelper.floor(y);
/* 570 */     int k = MathHelper.floor(z);
/* 571 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 572 */     Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */     
/* 574 */     if (block.hasTileEntity()) {
/*     */       
/* 576 */       TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */       
/* 578 */       if (tileentity instanceof IInventory) {
/*     */         
/* 580 */         iinventory = (IInventory)tileentity;
/*     */         
/* 582 */         if (iinventory instanceof TileEntityChest && block instanceof BlockChest)
/*     */         {
/* 584 */           iLockableContainer = ((BlockChest)block).getContainer(worldIn, blockpos, true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 589 */     if (iLockableContainer == null) {
/*     */       
/* 591 */       List<Entity> list = worldIn.getEntitiesInAABBexcluding(null, new AxisAlignedBB(x - 0.5D, y - 0.5D, z - 0.5D, x + 0.5D, y + 0.5D, z + 0.5D), EntitySelectors.HAS_INVENTORY);
/*     */       
/* 593 */       if (!list.isEmpty())
/*     */       {
/* 595 */         iInventory1 = (IInventory)list.get(worldIn.rand.nextInt(list.size()));
/*     */       }
/*     */     } 
/*     */     
/* 599 */     return iInventory1;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean canCombine(ItemStack stack1, ItemStack stack2) {
/* 604 */     if (stack1.getItem() != stack2.getItem())
/*     */     {
/* 606 */       return false;
/*     */     }
/* 608 */     if (stack1.getMetadata() != stack2.getMetadata())
/*     */     {
/* 610 */       return false;
/*     */     }
/* 612 */     if (stack1.func_190916_E() > stack1.getMaxStackSize())
/*     */     {
/* 614 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 618 */     return ItemStack.areItemStackTagsEqual(stack1, stack2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getXPos() {
/* 627 */     return this.pos.getX() + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYPos() {
/* 635 */     return this.pos.getY() + 0.5D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZPos() {
/* 643 */     return this.pos.getZ() + 0.5D;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setTransferCooldown(int ticks) {
/* 648 */     this.transferCooldown = ticks;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isOnTransferCooldown() {
/* 653 */     return (this.transferCooldown > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean mayTransfer() {
/* 658 */     return (this.transferCooldown > 8);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 663 */     return "minecraft:hopper";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 668 */     fillWithLoot(playerIn);
/* 669 */     return (Container)new ContainerHopper(playerInventory, this, playerIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NonNullList<ItemStack> func_190576_q() {
/* 674 */     return this.inventory;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityHopper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */