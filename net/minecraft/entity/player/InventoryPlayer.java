/*     */ package net.minecraft.entity.player;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.util.RecipeItemHelper;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.ItemStackHelper;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketSetSlot;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.util.text.TextComponentString;
/*     */ import net.minecraft.util.text.TextComponentTranslation;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class InventoryPlayer implements IInventory {
/*  30 */   public final NonNullList<ItemStack> mainInventory = NonNullList.func_191197_a(36, ItemStack.field_190927_a);
/*  31 */   public final NonNullList<ItemStack> armorInventory = NonNullList.func_191197_a(4, ItemStack.field_190927_a);
/*  32 */   public final NonNullList<ItemStack> offHandInventory = NonNullList.func_191197_a(1, ItemStack.field_190927_a);
/*     */   
/*     */   private final List<NonNullList<ItemStack>> allInventories;
/*  35 */   public ItemStack[] armorInventoryfix = new ItemStack[4];
/*     */   
/*     */   public int currentItem;
/*     */   
/*     */   public EntityPlayer player;
/*     */   
/*     */   private ItemStack itemStack;
/*     */   
/*     */   private int field_194017_h;
/*     */ 
/*     */   
/*     */   public InventoryPlayer(EntityPlayer playerIn) {
/*  47 */     this.allInventories = Arrays.asList((NonNullList<ItemStack>[])new NonNullList[] { this.mainInventory, this.armorInventory, this.offHandInventory });
/*  48 */     this.itemStack = ItemStack.field_190927_a;
/*  49 */     this.player = playerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCurrentItem() {
/*  57 */     return isHotbar(this.currentItem) ? (ItemStack)this.mainInventory.get(this.currentItem) : ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getHotbarSize() {
/*  65 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canMergeStacks(ItemStack stack1, ItemStack stack2) {
/*  70 */     return (!stack1.func_190926_b() && stackEqualExact(stack1, stack2) && stack1.isStackable() && stack1.func_190916_E() < stack1.getMaxStackSize() && stack1.func_190916_E() < getInventoryStackLimit());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean stackEqualExact(ItemStack stack1, ItemStack stack2) {
/*  78 */     return (stack1.getItem() == stack2.getItem() && (!stack1.getHasSubtypes() || stack1.getMetadata() == stack2.getMetadata()) && ItemStack.areItemStackTagsEqual(stack1, stack2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFirstEmptyStack() {
/*  86 */     for (int i = 0; i < this.mainInventory.size(); i++) {
/*     */       
/*  88 */       if (((ItemStack)this.mainInventory.get(i)).func_190926_b())
/*     */       {
/*  90 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  94 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPickedItemStack(ItemStack stack) {
/*  99 */     int i = getSlotFor(stack);
/*     */     
/* 101 */     if (isHotbar(i)) {
/*     */       
/* 103 */       this.currentItem = i;
/*     */ 
/*     */     
/*     */     }
/* 107 */     else if (i == -1) {
/*     */       
/* 109 */       this.currentItem = getBestHotbarSlot();
/*     */       
/* 111 */       if (!((ItemStack)this.mainInventory.get(this.currentItem)).func_190926_b()) {
/*     */         
/* 113 */         int j = getFirstEmptyStack();
/*     */         
/* 115 */         if (j != -1)
/*     */         {
/* 117 */           this.mainInventory.set(j, this.mainInventory.get(this.currentItem));
/*     */         }
/*     */       } 
/*     */       
/* 121 */       this.mainInventory.set(this.currentItem, stack);
/*     */     }
/*     */     else {
/*     */       
/* 125 */       pickItem(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void pickItem(int index) {
/* 132 */     this.currentItem = getBestHotbarSlot();
/* 133 */     ItemStack itemstack = (ItemStack)this.mainInventory.get(this.currentItem);
/* 134 */     this.mainInventory.set(this.currentItem, this.mainInventory.get(index));
/* 135 */     this.mainInventory.set(index, itemstack);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isHotbar(int index) {
/* 140 */     return (index >= 0 && index < 9);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSlotFor(ItemStack stack) {
/* 148 */     for (int i = 0; i < this.mainInventory.size(); i++) {
/*     */       
/* 150 */       if (!((ItemStack)this.mainInventory.get(i)).func_190926_b() && stackEqualExact(stack, (ItemStack)this.mainInventory.get(i)))
/*     */       {
/* 152 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 156 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_194014_c(ItemStack p_194014_1_) {
/* 161 */     for (int i = 0; i < this.mainInventory.size(); i++) {
/*     */       
/* 163 */       ItemStack itemstack = (ItemStack)this.mainInventory.get(i);
/*     */       
/* 165 */       if (!((ItemStack)this.mainInventory.get(i)).func_190926_b() && stackEqualExact(p_194014_1_, (ItemStack)this.mainInventory.get(i)) && !((ItemStack)this.mainInventory.get(i)).isItemDamaged() && !itemstack.isItemEnchanted() && !itemstack.hasDisplayName())
/*     */       {
/* 167 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 171 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBestHotbarSlot() {
/* 176 */     for (int i = 0; i < 9; i++) {
/*     */       
/* 178 */       int j = (this.currentItem + i) % 9;
/*     */       
/* 180 */       if (((ItemStack)this.mainInventory.get(j)).func_190926_b())
/*     */       {
/* 182 */         return j;
/*     */       }
/*     */     } 
/*     */     
/* 186 */     for (int k = 0; k < 9; k++) {
/*     */       
/* 188 */       int l = (this.currentItem + k) % 9;
/*     */       
/* 190 */       if (!((ItemStack)this.mainInventory.get(l)).isItemEnchanted())
/*     */       {
/* 192 */         return l;
/*     */       }
/*     */     } 
/*     */     
/* 196 */     return this.currentItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void changeCurrentItem(int direction) {
/* 204 */     if (direction > 0)
/*     */     {
/* 206 */       direction = 1;
/*     */     }
/*     */     
/* 209 */     if (direction < 0)
/*     */     {
/* 211 */       direction = -1;
/*     */     }
/*     */     
/* 214 */     for (this.currentItem -= direction; this.currentItem < 0; this.currentItem += 9);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 219 */     while (this.currentItem >= 9)
/*     */     {
/* 221 */       this.currentItem -= 9;
/*     */     }
/*     */   }
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
/*     */   public int clearMatchingItems(@Nullable Item itemIn, int metadataIn, int removeCount, @Nullable NBTTagCompound itemNBT) {
/* 235 */     int i = 0;
/*     */     
/* 237 */     for (int j = 0; j < getSizeInventory(); j++) {
/*     */       
/* 239 */       ItemStack itemstack = getStackInSlot(j);
/*     */       
/* 241 */       if (!itemstack.func_190926_b() && (itemIn == null || itemstack.getItem() == itemIn) && (metadataIn <= -1 || itemstack.getMetadata() == metadataIn) && (itemNBT == null || NBTUtil.areNBTEquals((NBTBase)itemNBT, (NBTBase)itemstack.getTagCompound(), true))) {
/*     */         
/* 243 */         int k = (removeCount <= 0) ? itemstack.func_190916_E() : Math.min(removeCount - i, itemstack.func_190916_E());
/* 244 */         i += k;
/*     */         
/* 246 */         if (removeCount != 0) {
/*     */           
/* 248 */           itemstack.func_190918_g(k);
/*     */           
/* 250 */           if (itemstack.func_190926_b())
/*     */           {
/* 252 */             setInventorySlotContents(j, ItemStack.field_190927_a);
/*     */           }
/*     */           
/* 255 */           if (removeCount > 0 && i >= removeCount)
/*     */           {
/* 257 */             return i;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 263 */     if (!this.itemStack.func_190926_b()) {
/*     */       
/* 265 */       if (itemIn != null && this.itemStack.getItem() != itemIn)
/*     */       {
/* 267 */         return i;
/*     */       }
/*     */       
/* 270 */       if (metadataIn > -1 && this.itemStack.getMetadata() != metadataIn)
/*     */       {
/* 272 */         return i;
/*     */       }
/*     */       
/* 275 */       if (itemNBT != null && !NBTUtil.areNBTEquals((NBTBase)itemNBT, (NBTBase)this.itemStack.getTagCompound(), true))
/*     */       {
/* 277 */         return i;
/*     */       }
/*     */       
/* 280 */       int l = (removeCount <= 0) ? this.itemStack.func_190916_E() : Math.min(removeCount - i, this.itemStack.func_190916_E());
/* 281 */       i += l;
/*     */       
/* 283 */       if (removeCount != 0) {
/*     */         
/* 285 */         this.itemStack.func_190918_g(l);
/*     */         
/* 287 */         if (this.itemStack.func_190926_b())
/*     */         {
/* 289 */           this.itemStack = ItemStack.field_190927_a;
/*     */         }
/*     */         
/* 292 */         if (removeCount > 0 && i >= removeCount)
/*     */         {
/* 294 */           return i;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 299 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int storePartialItemStack(ItemStack itemStackIn) {
/* 308 */     int i = storeItemStack(itemStackIn);
/*     */     
/* 310 */     if (i == -1)
/*     */     {
/* 312 */       i = getFirstEmptyStack();
/*     */     }
/*     */     
/* 315 */     return (i == -1) ? itemStackIn.func_190916_E() : func_191973_d(i, itemStackIn);
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_191973_d(int p_191973_1_, ItemStack p_191973_2_) {
/* 320 */     Item item = p_191973_2_.getItem();
/* 321 */     int i = p_191973_2_.func_190916_E();
/* 322 */     ItemStack itemstack = getStackInSlot(p_191973_1_);
/*     */     
/* 324 */     if (itemstack.func_190926_b()) {
/*     */       
/* 326 */       itemstack = new ItemStack(item, 0, p_191973_2_.getMetadata());
/*     */       
/* 328 */       if (p_191973_2_.hasTagCompound())
/*     */       {
/* 330 */         itemstack.setTagCompound(p_191973_2_.getTagCompound().copy());
/*     */       }
/*     */       
/* 333 */       setInventorySlotContents(p_191973_1_, itemstack);
/*     */     } 
/*     */     
/* 336 */     int j = i;
/*     */     
/* 338 */     if (i > itemstack.getMaxStackSize() - itemstack.func_190916_E())
/*     */     {
/* 340 */       j = itemstack.getMaxStackSize() - itemstack.func_190916_E();
/*     */     }
/*     */     
/* 343 */     if (j > getInventoryStackLimit() - itemstack.func_190916_E())
/*     */     {
/* 345 */       j = getInventoryStackLimit() - itemstack.func_190916_E();
/*     */     }
/*     */     
/* 348 */     if (j == 0)
/*     */     {
/* 350 */       return i;
/*     */     }
/*     */ 
/*     */     
/* 354 */     i -= j;
/* 355 */     itemstack.func_190917_f(j);
/* 356 */     itemstack.func_190915_d(5);
/* 357 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int storeItemStack(ItemStack itemStackIn) {
/* 366 */     if (canMergeStacks(getStackInSlot(this.currentItem), itemStackIn))
/*     */     {
/* 368 */       return this.currentItem;
/*     */     }
/* 370 */     if (canMergeStacks(getStackInSlot(40), itemStackIn))
/*     */     {
/* 372 */       return 40;
/*     */     }
/*     */ 
/*     */     
/* 376 */     for (int i = 0; i < this.mainInventory.size(); i++) {
/*     */       
/* 378 */       if (canMergeStacks((ItemStack)this.mainInventory.get(i), itemStackIn))
/*     */       {
/* 380 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 384 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void decrementAnimations() {
/* 394 */     for (NonNullList<ItemStack> nonnulllist : this.allInventories) {
/*     */       
/* 396 */       for (int i = 0; i < nonnulllist.size(); i++) {
/*     */         
/* 398 */         if (!((ItemStack)nonnulllist.get(i)).func_190926_b())
/*     */         {
/* 400 */           ((ItemStack)nonnulllist.get(i)).updateAnimation(this.player.world, (Entity)this.player, i, (this.currentItem == i));
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addItemStackToInventory(ItemStack itemStackIn) {
/* 411 */     return func_191971_c(-1, itemStackIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191971_c(int p_191971_1_, final ItemStack p_191971_2_) {
/* 416 */     if (p_191971_2_.func_190926_b())
/*     */     {
/* 418 */       return false;
/*     */     }
/*     */     
/*     */     try {
/*     */       int i;
/*     */       
/* 424 */       if (p_191971_2_.isItemDamaged()) {
/*     */         
/* 426 */         if (p_191971_1_ == -1)
/*     */         {
/* 428 */           p_191971_1_ = getFirstEmptyStack();
/*     */         }
/*     */         
/* 431 */         if (p_191971_1_ >= 0) {
/*     */           
/* 433 */           this.mainInventory.set(p_191971_1_, p_191971_2_.copy());
/* 434 */           ((ItemStack)this.mainInventory.get(p_191971_1_)).func_190915_d(5);
/* 435 */           p_191971_2_.func_190920_e(0);
/* 436 */           return true;
/*     */         } 
/* 438 */         if (this.player.capabilities.isCreativeMode) {
/*     */           
/* 440 */           p_191971_2_.func_190920_e(0);
/* 441 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 445 */         return false;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       do {
/* 454 */         i = p_191971_2_.func_190916_E();
/*     */         
/* 456 */         if (p_191971_1_ == -1)
/*     */         {
/* 458 */           p_191971_2_.func_190920_e(storePartialItemStack(p_191971_2_));
/*     */         }
/*     */         else
/*     */         {
/* 462 */           p_191971_2_.func_190920_e(func_191973_d(p_191971_1_, p_191971_2_));
/*     */         }
/*     */       
/* 465 */       } while (!p_191971_2_.func_190926_b() && p_191971_2_.func_190916_E() < i);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 471 */       if (p_191971_2_.func_190916_E() == i && this.player.capabilities.isCreativeMode) {
/*     */         
/* 473 */         p_191971_2_.func_190920_e(0);
/* 474 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 478 */       return (p_191971_2_.func_190916_E() < i);
/*     */ 
/*     */     
/*     */     }
/* 482 */     catch (Throwable throwable) {
/*     */       
/* 484 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Adding item to inventory");
/* 485 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Item being added");
/* 486 */       crashreportcategory.addCrashSection("Item ID", Integer.valueOf(Item.getIdFromItem(p_191971_2_.getItem())));
/* 487 */       crashreportcategory.addCrashSection("Item data", Integer.valueOf(p_191971_2_.getMetadata()));
/* 488 */       crashreportcategory.setDetail("Item name", new ICrashReportDetail<String>()
/*     */           {
/*     */             public String call() throws Exception
/*     */             {
/* 492 */               return p_191971_2_.getDisplayName();
/*     */             }
/*     */           });
/* 495 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_191975_a(World p_191975_1_, ItemStack p_191975_2_) {
/* 502 */     if (!p_191975_1_.isRemote)
/*     */     {
/* 504 */       while (!p_191975_2_.func_190926_b()) {
/*     */         
/* 506 */         int i = storeItemStack(p_191975_2_);
/*     */         
/* 508 */         if (i == -1)
/*     */         {
/* 510 */           i = getFirstEmptyStack();
/*     */         }
/*     */         
/* 513 */         if (i == -1) {
/*     */           
/* 515 */           this.player.dropItem(p_191975_2_, false);
/*     */           
/*     */           break;
/*     */         } 
/* 519 */         int j = p_191975_2_.getMaxStackSize() - getStackInSlot(i).func_190916_E();
/*     */         
/* 521 */         if (func_191971_c(i, p_191975_2_.splitStack(j)))
/*     */         {
/* 523 */           ((EntityPlayerMP)this.player).connection.sendPacket((Packet)new SPacketSetSlot(-2, i, getStackInSlot(i)));
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack decrStackSize(int index, int count) {
/*     */     NonNullList<ItemStack> nonNullList;
/* 534 */     List<ItemStack> list = null;
/*     */     
/* 536 */     for (NonNullList<ItemStack> nonnulllist : this.allInventories) {
/*     */       
/* 538 */       if (index < nonnulllist.size()) {
/*     */         
/* 540 */         nonNullList = nonnulllist;
/*     */         
/*     */         break;
/*     */       } 
/* 544 */       index -= nonnulllist.size();
/*     */     } 
/*     */     
/* 547 */     return (nonNullList != null && !((ItemStack)nonNullList.get(index)).func_190926_b()) ? ItemStackHelper.getAndSplit((List)nonNullList, index, count) : ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteStack(ItemStack stack) {
/* 552 */     for (NonNullList<ItemStack> nonnulllist : this.allInventories) {
/*     */       
/* 554 */       for (int i = 0; i < nonnulllist.size(); i++) {
/*     */         
/* 556 */         if (nonnulllist.get(i) == stack) {
/*     */           
/* 558 */           nonnulllist.set(i, ItemStack.field_190927_a);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack removeStackFromSlot(int index) {
/* 570 */     NonNullList<ItemStack> nonnulllist = null;
/*     */     
/* 572 */     for (NonNullList<ItemStack> nonnulllist1 : this.allInventories) {
/*     */       
/* 574 */       if (index < nonnulllist1.size()) {
/*     */         
/* 576 */         nonnulllist = nonnulllist1;
/*     */         
/*     */         break;
/*     */       } 
/* 580 */       index -= nonnulllist1.size();
/*     */     } 
/*     */     
/* 583 */     if (nonnulllist != null && !((ItemStack)nonnulllist.get(index)).func_190926_b()) {
/*     */       
/* 585 */       ItemStack itemstack = (ItemStack)nonnulllist.get(index);
/* 586 */       nonnulllist.set(index, ItemStack.field_190927_a);
/* 587 */       return itemstack;
/*     */     } 
/*     */ 
/*     */     
/* 591 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInventorySlotContents(int index, ItemStack stack) {
/* 600 */     NonNullList<ItemStack> nonnulllist = null;
/*     */     
/* 602 */     for (NonNullList<ItemStack> nonnulllist1 : this.allInventories) {
/*     */       
/* 604 */       if (index < nonnulllist1.size()) {
/*     */         
/* 606 */         nonnulllist = nonnulllist1;
/*     */         
/*     */         break;
/*     */       } 
/* 610 */       index -= nonnulllist1.size();
/*     */     } 
/*     */     
/* 613 */     if (nonnulllist != null)
/*     */     {
/* 615 */       nonnulllist.set(index, stack);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public float getStrVsBlock(IBlockState state) {
/* 621 */     float f = 1.0F;
/*     */     
/* 623 */     if (!((ItemStack)this.mainInventory.get(this.currentItem)).func_190926_b())
/*     */     {
/* 625 */       f *= ((ItemStack)this.mainInventory.get(this.currentItem)).getStrVsBlock(state);
/*     */     }
/*     */     
/* 628 */     return f;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagList writeToNBT(NBTTagList nbtTagListIn) {
/* 637 */     for (int i = 0; i < this.mainInventory.size(); i++) {
/*     */       
/* 639 */       if (!((ItemStack)this.mainInventory.get(i)).func_190926_b()) {
/*     */         
/* 641 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 642 */         nbttagcompound.setByte("Slot", (byte)i);
/* 643 */         ((ItemStack)this.mainInventory.get(i)).writeToNBT(nbttagcompound);
/* 644 */         nbtTagListIn.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 648 */     for (int j = 0; j < this.armorInventory.size(); j++) {
/*     */       
/* 650 */       if (!((ItemStack)this.armorInventory.get(j)).func_190926_b()) {
/*     */         
/* 652 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 653 */         nbttagcompound1.setByte("Slot", (byte)(j + 100));
/* 654 */         ((ItemStack)this.armorInventory.get(j)).writeToNBT(nbttagcompound1);
/* 655 */         nbtTagListIn.appendTag((NBTBase)nbttagcompound1);
/*     */       } 
/*     */     } 
/*     */     
/* 659 */     for (int k = 0; k < this.offHandInventory.size(); k++) {
/*     */       
/* 661 */       if (!((ItemStack)this.offHandInventory.get(k)).func_190926_b()) {
/*     */         
/* 663 */         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 664 */         nbttagcompound2.setByte("Slot", (byte)(k + 150));
/* 665 */         ((ItemStack)this.offHandInventory.get(k)).writeToNBT(nbttagcompound2);
/* 666 */         nbtTagListIn.appendTag((NBTBase)nbttagcompound2);
/*     */       } 
/*     */     } 
/*     */     
/* 670 */     return nbtTagListIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagList nbtTagListIn) {
/* 678 */     this.mainInventory.clear();
/* 679 */     this.armorInventory.clear();
/* 680 */     this.offHandInventory.clear();
/*     */     
/* 682 */     for (int i = 0; i < nbtTagListIn.tagCount(); i++) {
/*     */       
/* 684 */       NBTTagCompound nbttagcompound = nbtTagListIn.getCompoundTagAt(i);
/* 685 */       int j = nbttagcompound.getByte("Slot") & 0xFF;
/* 686 */       ItemStack itemstack = new ItemStack(nbttagcompound);
/*     */       
/* 688 */       if (!itemstack.func_190926_b())
/*     */       {
/* 690 */         if (j >= 0 && j < this.mainInventory.size()) {
/*     */           
/* 692 */           this.mainInventory.set(j, itemstack);
/*     */         }
/* 694 */         else if (j >= 100 && j < this.armorInventory.size() + 100) {
/*     */           
/* 696 */           this.armorInventory.set(j - 100, itemstack);
/*     */         }
/* 698 */         else if (j >= 150 && j < this.offHandInventory.size() + 150) {
/*     */           
/* 700 */           this.offHandInventory.set(j - 150, itemstack);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/* 711 */     return this.mainInventory.size() + this.armorInventory.size() + this.offHandInventory.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/* 716 */     for (ItemStack itemstack : this.mainInventory) {
/*     */       
/* 718 */       if (!itemstack.func_190926_b())
/*     */       {
/* 720 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 724 */     for (ItemStack itemstack1 : this.armorInventory) {
/*     */       
/* 726 */       if (!itemstack1.func_190926_b())
/*     */       {
/* 728 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 732 */     for (ItemStack itemstack2 : this.offHandInventory) {
/*     */       
/* 734 */       if (!itemstack2.func_190926_b())
/*     */       {
/* 736 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 740 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getStackInSlot(int index) {
/*     */     NonNullList<ItemStack> nonNullList;
/* 748 */     List<ItemStack> list = null;
/*     */     
/* 750 */     for (NonNullList<ItemStack> nonnulllist : this.allInventories) {
/*     */       
/* 752 */       if (index < nonnulllist.size()) {
/*     */         
/* 754 */         nonNullList = nonnulllist;
/*     */         
/*     */         break;
/*     */       } 
/* 758 */       index -= nonnulllist.size();
/*     */     } 
/*     */     
/* 761 */     return (nonNullList == null) ? ItemStack.field_190927_a : nonNullList.get(index);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 769 */     return "container.inventory";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasCustomName() {
/* 777 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ITextComponent getDisplayName() {
/* 785 */     return hasCustomName() ? (ITextComponent)new TextComponentString(getName()) : (ITextComponent)new TextComponentTranslation(getName(), new Object[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 793 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHarvestBlock(IBlockState state) {
/* 798 */     if (state.getMaterial().isToolNotRequired())
/*     */     {
/* 800 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 804 */     ItemStack itemstack = getStackInSlot(this.currentItem);
/* 805 */     return !itemstack.func_190926_b() ? itemstack.canHarvestBlock(state) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack armorItemInSlot(int slotIn) {
/* 814 */     return (ItemStack)this.armorInventory.get(slotIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void damageArmor(float damage) {
/* 822 */     damage /= 4.0F;
/*     */     
/* 824 */     if (damage < 1.0F)
/*     */     {
/* 826 */       damage = 1.0F;
/*     */     }
/*     */     
/* 829 */     for (int i = 0; i < this.armorInventory.size(); i++) {
/*     */       
/* 831 */       ItemStack itemstack = (ItemStack)this.armorInventory.get(i);
/*     */       
/* 833 */       if (itemstack.getItem() instanceof net.minecraft.item.ItemArmor)
/*     */       {
/* 835 */         itemstack.damageItem((int)damage, this.player);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropAllItems() {
/* 845 */     for (List<ItemStack> list : this.allInventories) {
/*     */       
/* 847 */       for (int i = 0; i < list.size(); i++) {
/*     */         
/* 849 */         ItemStack itemstack = list.get(i);
/*     */         
/* 851 */         if (!itemstack.func_190926_b()) {
/*     */           
/* 853 */           this.player.dropItem(itemstack, true, false);
/* 854 */           list.set(i, ItemStack.field_190927_a);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void markDirty() {
/* 866 */     this.field_194017_h++;
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_194015_p() {
/* 871 */     return this.field_194017_h;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemStack(ItemStack itemStackIn) {
/* 879 */     this.itemStack = itemStackIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack() {
/* 887 */     return this.itemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsableByPlayer(EntityPlayer player) {
/* 895 */     if (this.player.isDead)
/*     */     {
/* 897 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 901 */     return (player.getDistanceSqToEntity((Entity)this.player) <= 64.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasItemStack(ItemStack itemStackIn) {
/* 912 */     for (List<ItemStack> list : this.allInventories) {
/*     */       
/* 914 */       Iterator<ItemStack> iterator = list.iterator();
/*     */ 
/*     */ 
/*     */       
/* 918 */       while (iterator.hasNext()) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 923 */         ItemStack itemstack = iterator.next();
/*     */         
/* 925 */         if (!itemstack.func_190926_b() && itemstack.isItemEqual(itemStackIn))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 931 */           return true; } 
/*     */       } 
/*     */     } 
/* 934 */     return false;
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
/* 951 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void copyInventory(InventoryPlayer playerInventory) {
/* 959 */     for (int i = 0; i < getSizeInventory(); i++)
/*     */     {
/* 961 */       setInventorySlotContents(i, playerInventory.getStackInSlot(i));
/*     */     }
/*     */     
/* 964 */     this.currentItem = playerInventory.currentItem;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getField(int id) {
/* 969 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setField(int id, int value) {}
/*     */ 
/*     */   
/*     */   public int getFieldCount() {
/* 978 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 983 */     for (List<ItemStack> list : this.allInventories)
/*     */     {
/* 985 */       list.clear();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194016_a(RecipeItemHelper p_194016_1_, boolean p_194016_2_) {
/* 991 */     for (ItemStack itemstack : this.mainInventory)
/*     */     {
/* 993 */       p_194016_1_.func_194112_a(itemstack);
/*     */     }
/*     */     
/* 996 */     if (p_194016_2_)
/*     */     {
/* 998 */       p_194016_1_.func_194112_a((ItemStack)this.offHandInventory.get(0));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\player\InventoryPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */