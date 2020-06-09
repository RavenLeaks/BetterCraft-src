/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentData;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemEnchantedBook;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ContainerEnchantment
/*     */   extends Container
/*     */ {
/*     */   public IInventory tableInventory;
/*     */   private final World worldPointer;
/*     */   private final BlockPos position;
/*     */   private final Random rand;
/*     */   public int xpSeed;
/*     */   public int[] enchantLevels;
/*     */   public int[] enchantClue;
/*     */   public int[] worldClue;
/*     */   
/*     */   public ContainerEnchantment(InventoryPlayer playerInv, World worldIn) {
/*  41 */     this(playerInv, worldIn, BlockPos.ORIGIN);
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerEnchantment(InventoryPlayer playerInv, World worldIn, BlockPos pos) {
/*  46 */     this.tableInventory = new InventoryBasic("Enchant", true, 2)
/*     */       {
/*     */         public int getInventoryStackLimit()
/*     */         {
/*  50 */           return 64;
/*     */         }
/*     */         
/*     */         public void markDirty() {
/*  54 */           super.markDirty();
/*  55 */           ContainerEnchantment.this.onCraftMatrixChanged(this);
/*     */         }
/*     */       };
/*  58 */     this.rand = new Random();
/*  59 */     this.enchantLevels = new int[3];
/*  60 */     this.enchantClue = new int[] { -1, -1, -1 };
/*  61 */     this.worldClue = new int[] { -1, -1, -1 };
/*  62 */     this.worldPointer = worldIn;
/*  63 */     this.position = pos;
/*  64 */     this.xpSeed = playerInv.player.getXPSeed();
/*  65 */     addSlotToContainer(new Slot(this.tableInventory, 0, 15, 47)
/*     */         {
/*     */           public boolean isItemValid(ItemStack stack)
/*     */           {
/*  69 */             return true;
/*     */           }
/*     */           
/*     */           public int getSlotStackLimit() {
/*  73 */             return 1;
/*     */           }
/*     */         });
/*  76 */     addSlotToContainer(new Slot(this.tableInventory, 1, 35, 47)
/*     */         {
/*     */           public boolean isItemValid(ItemStack stack)
/*     */           {
/*  80 */             return (stack.getItem() == Items.DYE && EnumDyeColor.byDyeDamage(stack.getMetadata()) == EnumDyeColor.BLUE);
/*     */           }
/*     */         });
/*     */     
/*  84 */     for (int i = 0; i < 3; i++) {
/*     */       
/*  86 */       for (int j = 0; j < 9; j++)
/*     */       {
/*  88 */         addSlotToContainer(new Slot((IInventory)playerInv, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/*  92 */     for (int k = 0; k < 9; k++)
/*     */     {
/*  94 */       addSlotToContainer(new Slot((IInventory)playerInv, k, 8 + k * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void broadcastData(IContainerListener crafting) {
/* 100 */     crafting.sendProgressBarUpdate(this, 0, this.enchantLevels[0]);
/* 101 */     crafting.sendProgressBarUpdate(this, 1, this.enchantLevels[1]);
/* 102 */     crafting.sendProgressBarUpdate(this, 2, this.enchantLevels[2]);
/* 103 */     crafting.sendProgressBarUpdate(this, 3, this.xpSeed & 0xFFFFFFF0);
/* 104 */     crafting.sendProgressBarUpdate(this, 4, this.enchantClue[0]);
/* 105 */     crafting.sendProgressBarUpdate(this, 5, this.enchantClue[1]);
/* 106 */     crafting.sendProgressBarUpdate(this, 6, this.enchantClue[2]);
/* 107 */     crafting.sendProgressBarUpdate(this, 7, this.worldClue[0]);
/* 108 */     crafting.sendProgressBarUpdate(this, 8, this.worldClue[1]);
/* 109 */     crafting.sendProgressBarUpdate(this, 9, this.worldClue[2]);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addListener(IContainerListener listener) {
/* 114 */     super.addListener(listener);
/* 115 */     broadcastData(listener);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detectAndSendChanges() {
/* 123 */     super.detectAndSendChanges();
/*     */     
/* 125 */     for (int i = 0; i < this.listeners.size(); i++) {
/*     */       
/* 127 */       IContainerListener icontainerlistener = this.listeners.get(i);
/* 128 */       broadcastData(icontainerlistener);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateProgressBar(int id, int data) {
/* 134 */     if (id >= 0 && id <= 2) {
/*     */       
/* 136 */       this.enchantLevels[id] = data;
/*     */     }
/* 138 */     else if (id == 3) {
/*     */       
/* 140 */       this.xpSeed = data;
/*     */     }
/* 142 */     else if (id >= 4 && id <= 6) {
/*     */       
/* 144 */       this.enchantClue[id - 4] = data;
/*     */     }
/* 146 */     else if (id >= 7 && id <= 9) {
/*     */       
/* 148 */       this.worldClue[id - 7] = data;
/*     */     }
/*     */     else {
/*     */       
/* 152 */       super.updateProgressBar(id, data);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/* 161 */     if (inventoryIn == this.tableInventory) {
/*     */       
/* 163 */       ItemStack itemstack = inventoryIn.getStackInSlot(0);
/*     */       
/* 165 */       if (!itemstack.func_190926_b() && itemstack.isItemEnchantable()) {
/*     */         
/* 167 */         if (!this.worldPointer.isRemote)
/*     */         {
/* 169 */           int l = 0;
/*     */           
/* 171 */           for (int j = -1; j <= 1; j++) {
/*     */             
/* 173 */             for (int k = -1; k <= 1; k++) {
/*     */               
/* 175 */               if ((j != 0 || k != 0) && this.worldPointer.isAirBlock(this.position.add(k, 0, j)) && this.worldPointer.isAirBlock(this.position.add(k, 1, j))) {
/*     */                 
/* 177 */                 if (this.worldPointer.getBlockState(this.position.add(k * 2, 0, j * 2)).getBlock() == Blocks.BOOKSHELF)
/*     */                 {
/* 179 */                   l++;
/*     */                 }
/*     */                 
/* 182 */                 if (this.worldPointer.getBlockState(this.position.add(k * 2, 1, j * 2)).getBlock() == Blocks.BOOKSHELF)
/*     */                 {
/* 184 */                   l++;
/*     */                 }
/*     */                 
/* 187 */                 if (k != 0 && j != 0) {
/*     */                   
/* 189 */                   if (this.worldPointer.getBlockState(this.position.add(k * 2, 0, j)).getBlock() == Blocks.BOOKSHELF)
/*     */                   {
/* 191 */                     l++;
/*     */                   }
/*     */                   
/* 194 */                   if (this.worldPointer.getBlockState(this.position.add(k * 2, 1, j)).getBlock() == Blocks.BOOKSHELF)
/*     */                   {
/* 196 */                     l++;
/*     */                   }
/*     */                   
/* 199 */                   if (this.worldPointer.getBlockState(this.position.add(k, 0, j * 2)).getBlock() == Blocks.BOOKSHELF)
/*     */                   {
/* 201 */                     l++;
/*     */                   }
/*     */                   
/* 204 */                   if (this.worldPointer.getBlockState(this.position.add(k, 1, j * 2)).getBlock() == Blocks.BOOKSHELF)
/*     */                   {
/* 206 */                     l++;
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 213 */           this.rand.setSeed(this.xpSeed);
/*     */           
/* 215 */           for (int i1 = 0; i1 < 3; i1++) {
/*     */             
/* 217 */             this.enchantLevels[i1] = EnchantmentHelper.calcItemStackEnchantability(this.rand, i1, l, itemstack);
/* 218 */             this.enchantClue[i1] = -1;
/* 219 */             this.worldClue[i1] = -1;
/*     */             
/* 221 */             if (this.enchantLevels[i1] < i1 + 1)
/*     */             {
/* 223 */               this.enchantLevels[i1] = 0;
/*     */             }
/*     */           } 
/*     */           
/* 227 */           for (int j1 = 0; j1 < 3; j1++) {
/*     */             
/* 229 */             if (this.enchantLevels[j1] > 0) {
/*     */               
/* 231 */               List<EnchantmentData> list = getEnchantmentList(itemstack, j1, this.enchantLevels[j1]);
/*     */               
/* 233 */               if (list != null && !list.isEmpty()) {
/*     */                 
/* 235 */                 EnchantmentData enchantmentdata = list.get(this.rand.nextInt(list.size()));
/* 236 */                 this.enchantClue[j1] = Enchantment.getEnchantmentID(enchantmentdata.enchantmentobj);
/* 237 */                 this.worldClue[j1] = enchantmentdata.enchantmentLevel;
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 242 */           detectAndSendChanges();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 247 */         for (int i = 0; i < 3; i++) {
/*     */           
/* 249 */           this.enchantLevels[i] = 0;
/* 250 */           this.enchantClue[i] = -1;
/* 251 */           this.worldClue[i] = -1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean enchantItem(EntityPlayer playerIn, int id) {
/* 262 */     ItemStack itemstack = this.tableInventory.getStackInSlot(0);
/* 263 */     ItemStack itemstack1 = this.tableInventory.getStackInSlot(1);
/* 264 */     int i = id + 1;
/*     */     
/* 266 */     if ((itemstack1.func_190926_b() || itemstack1.func_190916_E() < i) && !playerIn.capabilities.isCreativeMode)
/*     */     {
/* 268 */       return false;
/*     */     }
/* 270 */     if (this.enchantLevels[id] > 0 && !itemstack.func_190926_b() && ((playerIn.experienceLevel >= i && playerIn.experienceLevel >= this.enchantLevels[id]) || playerIn.capabilities.isCreativeMode)) {
/*     */       
/* 272 */       if (!this.worldPointer.isRemote) {
/*     */         
/* 274 */         List<EnchantmentData> list = getEnchantmentList(itemstack, id, this.enchantLevels[id]);
/*     */         
/* 276 */         if (!list.isEmpty()) {
/*     */           
/* 278 */           playerIn.func_192024_a(itemstack, i);
/* 279 */           boolean flag = (itemstack.getItem() == Items.BOOK);
/*     */           
/* 281 */           if (flag) {
/*     */             
/* 283 */             itemstack = new ItemStack(Items.ENCHANTED_BOOK);
/* 284 */             this.tableInventory.setInventorySlotContents(0, itemstack);
/*     */           } 
/*     */           
/* 287 */           for (int j = 0; j < list.size(); j++) {
/*     */             
/* 289 */             EnchantmentData enchantmentdata = list.get(j);
/*     */             
/* 291 */             if (flag) {
/*     */               
/* 293 */               ItemEnchantedBook.addEnchantment(itemstack, enchantmentdata);
/*     */             }
/*     */             else {
/*     */               
/* 297 */               itemstack.addEnchantment(enchantmentdata.enchantmentobj, enchantmentdata.enchantmentLevel);
/*     */             } 
/*     */           } 
/*     */           
/* 301 */           if (!playerIn.capabilities.isCreativeMode) {
/*     */             
/* 303 */             itemstack1.func_190918_g(i);
/*     */             
/* 305 */             if (itemstack1.func_190926_b())
/*     */             {
/* 307 */               this.tableInventory.setInventorySlotContents(1, ItemStack.field_190927_a);
/*     */             }
/*     */           } 
/*     */           
/* 311 */           playerIn.addStat(StatList.ITEM_ENCHANTED);
/*     */           
/* 313 */           if (playerIn instanceof EntityPlayerMP)
/*     */           {
/* 315 */             CriteriaTriggers.field_192129_i.func_192190_a((EntityPlayerMP)playerIn, itemstack, i);
/*     */           }
/*     */           
/* 318 */           this.tableInventory.markDirty();
/* 319 */           this.xpSeed = playerIn.getXPSeed();
/* 320 */           onCraftMatrixChanged(this.tableInventory);
/* 321 */           this.worldPointer.playSound(null, this.position, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, this.worldPointer.rand.nextFloat() * 0.1F + 0.9F);
/*     */         } 
/*     */       } 
/*     */       
/* 325 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 329 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private List<EnchantmentData> getEnchantmentList(ItemStack stack, int p_178148_2_, int p_178148_3_) {
/* 335 */     this.rand.setSeed((this.xpSeed + p_178148_2_));
/* 336 */     List<EnchantmentData> list = EnchantmentHelper.buildEnchantmentList(this.rand, stack, p_178148_3_, false);
/*     */     
/* 338 */     if (stack.getItem() == Items.BOOK && list.size() > 1)
/*     */     {
/* 340 */       list.remove(this.rand.nextInt(list.size()));
/*     */     }
/*     */     
/* 343 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLapisAmount() {
/* 348 */     ItemStack itemstack = this.tableInventory.getStackInSlot(1);
/* 349 */     return itemstack.func_190926_b() ? 0 : itemstack.func_190916_E();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/* 357 */     super.onContainerClosed(playerIn);
/*     */     
/* 359 */     if (!this.worldPointer.isRemote)
/*     */     {
/* 361 */       func_193327_a(playerIn, playerIn.world, this.tableInventory);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/* 370 */     if (this.worldPointer.getBlockState(this.position).getBlock() != Blocks.ENCHANTING_TABLE)
/*     */     {
/* 372 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 376 */     return (playerIn.getDistanceSq(this.position.getX() + 0.5D, this.position.getY() + 0.5D, this.position.getZ() + 0.5D) <= 64.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 385 */     ItemStack itemstack = ItemStack.field_190927_a;
/* 386 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/* 388 */     if (slot != null && slot.getHasStack()) {
/*     */       
/* 390 */       ItemStack itemstack1 = slot.getStack();
/* 391 */       itemstack = itemstack1.copy();
/*     */       
/* 393 */       if (index == 0) {
/*     */         
/* 395 */         if (!mergeItemStack(itemstack1, 2, 38, true))
/*     */         {
/* 397 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 400 */       else if (index == 1) {
/*     */         
/* 402 */         if (!mergeItemStack(itemstack1, 2, 38, true))
/*     */         {
/* 404 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 407 */       else if (itemstack1.getItem() == Items.DYE && EnumDyeColor.byDyeDamage(itemstack1.getMetadata()) == EnumDyeColor.BLUE) {
/*     */         
/* 409 */         if (!mergeItemStack(itemstack1, 1, 2, true))
/*     */         {
/* 411 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 416 */         if (((Slot)this.inventorySlots.get(0)).getHasStack() || !((Slot)this.inventorySlots.get(0)).isItemValid(itemstack1))
/*     */         {
/* 418 */           return ItemStack.field_190927_a;
/*     */         }
/*     */         
/* 421 */         if (itemstack1.hasTagCompound() && itemstack1.func_190916_E() == 1) {
/*     */           
/* 423 */           ((Slot)this.inventorySlots.get(0)).putStack(itemstack1.copy());
/* 424 */           itemstack1.func_190920_e(0);
/*     */         }
/* 426 */         else if (!itemstack1.func_190926_b()) {
/*     */           
/* 428 */           ((Slot)this.inventorySlots.get(0)).putStack(new ItemStack(itemstack1.getItem(), 1, itemstack1.getMetadata()));
/* 429 */           itemstack1.func_190918_g(1);
/*     */         } 
/*     */       } 
/*     */       
/* 433 */       if (itemstack1.func_190926_b()) {
/*     */         
/* 435 */         slot.putStack(ItemStack.field_190927_a);
/*     */       }
/*     */       else {
/*     */         
/* 439 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 442 */       if (itemstack1.func_190916_E() == itemstack.func_190916_E())
/*     */       {
/* 444 */         return ItemStack.field_190927_a;
/*     */       }
/*     */       
/* 447 */       slot.func_190901_a(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 450 */     return itemstack;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\ContainerEnchantment.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */