/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.CraftingManager;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketSetSlot;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class Container {
/*  22 */   public NonNullList<ItemStack> inventoryItemStacks = NonNullList.func_191196_a();
/*  23 */   public List<Slot> inventorySlots = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public int windowId;
/*     */   
/*     */   private short transactionID;
/*     */   
/*  30 */   private int dragMode = -1;
/*     */   
/*     */   private int dragEvent;
/*     */   
/*  34 */   private final Set<Slot> dragSlots = Sets.newHashSet();
/*  35 */   protected List<IContainerListener> listeners = Lists.newArrayList();
/*  36 */   private final Set<EntityPlayer> playerList = Sets.newHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected Slot addSlotToContainer(Slot slotIn) {
/*  43 */     slotIn.slotNumber = this.inventorySlots.size();
/*  44 */     this.inventorySlots.add(slotIn);
/*  45 */     this.inventoryItemStacks.add(ItemStack.field_190927_a);
/*  46 */     return slotIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addListener(IContainerListener listener) {
/*  51 */     if (this.listeners.contains(listener))
/*     */     {
/*  53 */       throw new IllegalArgumentException("Listener already listening");
/*     */     }
/*     */ 
/*     */     
/*  57 */     this.listeners.add(listener);
/*  58 */     listener.updateCraftingInventory(this, getInventory());
/*  59 */     detectAndSendChanges();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeListener(IContainerListener listener) {
/*  68 */     this.listeners.remove(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public NonNullList<ItemStack> getInventory() {
/*  73 */     NonNullList<ItemStack> nonnulllist = NonNullList.func_191196_a();
/*     */     
/*  75 */     for (int i = 0; i < this.inventorySlots.size(); i++)
/*     */     {
/*  77 */       nonnulllist.add(((Slot)this.inventorySlots.get(i)).getStack());
/*     */     }
/*     */     
/*  80 */     return nonnulllist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void detectAndSendChanges() {
/*  88 */     for (int i = 0; i < this.inventorySlots.size(); i++) {
/*     */       
/*  90 */       ItemStack itemstack = ((Slot)this.inventorySlots.get(i)).getStack();
/*  91 */       ItemStack itemstack1 = (ItemStack)this.inventoryItemStacks.get(i);
/*     */       
/*  93 */       if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
/*     */         
/*  95 */         itemstack1 = itemstack.func_190926_b() ? ItemStack.field_190927_a : itemstack.copy();
/*  96 */         this.inventoryItemStacks.set(i, itemstack1);
/*     */         
/*  98 */         for (int j = 0; j < this.listeners.size(); j++)
/*     */         {
/* 100 */           ((IContainerListener)this.listeners.get(j)).sendSlotContents(this, i, itemstack1);
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
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Slot getSlotFromInventory(IInventory inv, int slotIn) {
/* 117 */     for (int i = 0; i < this.inventorySlots.size(); i++) {
/*     */       
/* 119 */       Slot slot = this.inventorySlots.get(i);
/*     */       
/* 121 */       if (slot.isHere(inv, slotIn))
/*     */       {
/* 123 */         return slot;
/*     */       }
/*     */     } 
/*     */     
/* 127 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Slot getSlot(int slotId) {
/* 132 */     return this.inventorySlots.get(slotId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 140 */     Slot slot = this.inventorySlots.get(index);
/* 141 */     return (slot != null) ? slot.getStack() : ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack slotClick(int slotId, int dragType, ClickType clickTypeIn, EntityPlayer player) {
/* 146 */     ItemStack itemstack = ItemStack.field_190927_a;
/* 147 */     InventoryPlayer inventoryplayer = player.inventory;
/*     */     
/* 149 */     if (clickTypeIn == ClickType.QUICK_CRAFT) {
/*     */       
/* 151 */       int j1 = this.dragEvent;
/* 152 */       this.dragEvent = getDragEvent(dragType);
/*     */       
/* 154 */       if ((j1 != 1 || this.dragEvent != 2) && j1 != this.dragEvent) {
/*     */         
/* 156 */         resetDrag();
/*     */       }
/* 158 */       else if (inventoryplayer.getItemStack().func_190926_b()) {
/*     */         
/* 160 */         resetDrag();
/*     */       }
/* 162 */       else if (this.dragEvent == 0) {
/*     */         
/* 164 */         this.dragMode = extractDragMode(dragType);
/*     */         
/* 166 */         if (isValidDragMode(this.dragMode, player))
/*     */         {
/* 168 */           this.dragEvent = 1;
/* 169 */           this.dragSlots.clear();
/*     */         }
/*     */         else
/*     */         {
/* 173 */           resetDrag();
/*     */         }
/*     */       
/* 176 */       } else if (this.dragEvent == 1) {
/*     */         
/* 178 */         Slot slot7 = this.inventorySlots.get(slotId);
/* 179 */         ItemStack itemstack12 = inventoryplayer.getItemStack();
/*     */         
/* 181 */         if (slot7 != null && canAddItemToSlot(slot7, itemstack12, true) && slot7.isItemValid(itemstack12) && (this.dragMode == 2 || itemstack12.func_190916_E() > this.dragSlots.size()) && canDragIntoSlot(slot7))
/*     */         {
/* 183 */           this.dragSlots.add(slot7);
/*     */         }
/*     */       }
/* 186 */       else if (this.dragEvent == 2) {
/*     */         
/* 188 */         if (!this.dragSlots.isEmpty()) {
/*     */           
/* 190 */           ItemStack itemstack9 = inventoryplayer.getItemStack().copy();
/* 191 */           int k1 = inventoryplayer.getItemStack().func_190916_E();
/*     */           
/* 193 */           for (Slot slot8 : this.dragSlots) {
/*     */             
/* 195 */             ItemStack itemstack13 = inventoryplayer.getItemStack();
/*     */             
/* 197 */             if (slot8 != null && canAddItemToSlot(slot8, itemstack13, true) && slot8.isItemValid(itemstack13) && (this.dragMode == 2 || itemstack13.func_190916_E() >= this.dragSlots.size()) && canDragIntoSlot(slot8)) {
/*     */               
/* 199 */               ItemStack itemstack14 = itemstack9.copy();
/* 200 */               int j3 = slot8.getHasStack() ? slot8.getStack().func_190916_E() : 0;
/* 201 */               computeStackSize(this.dragSlots, this.dragMode, itemstack14, j3);
/* 202 */               int k3 = Math.min(itemstack14.getMaxStackSize(), slot8.getItemStackLimit(itemstack14));
/*     */               
/* 204 */               if (itemstack14.func_190916_E() > k3)
/*     */               {
/* 206 */                 itemstack14.func_190920_e(k3);
/*     */               }
/*     */               
/* 209 */               k1 -= itemstack14.func_190916_E() - j3;
/* 210 */               slot8.putStack(itemstack14);
/*     */             } 
/*     */           } 
/*     */           
/* 214 */           itemstack9.func_190920_e(k1);
/* 215 */           inventoryplayer.setItemStack(itemstack9);
/*     */         } 
/*     */         
/* 218 */         resetDrag();
/*     */       }
/*     */       else {
/*     */         
/* 222 */         resetDrag();
/*     */       }
/*     */     
/* 225 */     } else if (this.dragEvent != 0) {
/*     */       
/* 227 */       resetDrag();
/*     */     }
/* 229 */     else if ((clickTypeIn == ClickType.PICKUP || clickTypeIn == ClickType.QUICK_MOVE) && (dragType == 0 || dragType == 1)) {
/*     */       
/* 231 */       if (slotId == -999) {
/*     */         
/* 233 */         if (!inventoryplayer.getItemStack().func_190926_b())
/*     */         {
/* 235 */           if (dragType == 0) {
/*     */             
/* 237 */             player.dropItem(inventoryplayer.getItemStack(), true);
/* 238 */             inventoryplayer.setItemStack(ItemStack.field_190927_a);
/*     */           } 
/*     */           
/* 241 */           if (dragType == 1)
/*     */           {
/* 243 */             player.dropItem(inventoryplayer.getItemStack().splitStack(1), true);
/*     */           }
/*     */         }
/*     */       
/* 247 */       } else if (clickTypeIn == ClickType.QUICK_MOVE) {
/*     */         
/* 249 */         if (slotId < 0)
/*     */         {
/* 251 */           return ItemStack.field_190927_a;
/*     */         }
/*     */         
/* 254 */         Slot slot5 = this.inventorySlots.get(slotId);
/*     */         
/* 256 */         if (slot5 == null || !slot5.canTakeStack(player))
/*     */         {
/* 258 */           return ItemStack.field_190927_a;
/*     */         }
/*     */         
/* 261 */         for (ItemStack itemstack7 = transferStackInSlot(player, slotId); !itemstack7.func_190926_b() && ItemStack.areItemsEqual(slot5.getStack(), itemstack7); itemstack7 = transferStackInSlot(player, slotId))
/*     */         {
/* 263 */           itemstack = itemstack7.copy();
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 268 */         if (slotId < 0)
/*     */         {
/* 270 */           return ItemStack.field_190927_a;
/*     */         }
/*     */         
/* 273 */         Slot slot6 = this.inventorySlots.get(slotId);
/*     */         
/* 275 */         if (slot6 != null)
/*     */         {
/* 277 */           ItemStack itemstack8 = slot6.getStack();
/* 278 */           ItemStack itemstack11 = inventoryplayer.getItemStack();
/*     */           
/* 280 */           if (!itemstack8.func_190926_b())
/*     */           {
/* 282 */             itemstack = itemstack8.copy();
/*     */           }
/*     */           
/* 285 */           if (itemstack8.func_190926_b()) {
/*     */             
/* 287 */             if (!itemstack11.func_190926_b() && slot6.isItemValid(itemstack11))
/*     */             {
/* 289 */               int i3 = (dragType == 0) ? itemstack11.func_190916_E() : 1;
/*     */               
/* 291 */               if (i3 > slot6.getItemStackLimit(itemstack11))
/*     */               {
/* 293 */                 i3 = slot6.getItemStackLimit(itemstack11);
/*     */               }
/*     */               
/* 296 */               slot6.putStack(itemstack11.splitStack(i3));
/*     */             }
/*     */           
/* 299 */           } else if (slot6.canTakeStack(player)) {
/*     */             
/* 301 */             if (itemstack11.func_190926_b()) {
/*     */               
/* 303 */               if (itemstack8.func_190926_b())
/*     */               {
/* 305 */                 slot6.putStack(ItemStack.field_190927_a);
/* 306 */                 inventoryplayer.setItemStack(ItemStack.field_190927_a);
/*     */               }
/*     */               else
/*     */               {
/* 310 */                 int l2 = (dragType == 0) ? itemstack8.func_190916_E() : ((itemstack8.func_190916_E() + 1) / 2);
/* 311 */                 inventoryplayer.setItemStack(slot6.decrStackSize(l2));
/*     */                 
/* 313 */                 if (itemstack8.func_190926_b())
/*     */                 {
/* 315 */                   slot6.putStack(ItemStack.field_190927_a);
/*     */                 }
/*     */                 
/* 318 */                 slot6.func_190901_a(player, inventoryplayer.getItemStack());
/*     */               }
/*     */             
/* 321 */             } else if (slot6.isItemValid(itemstack11)) {
/*     */               
/* 323 */               if (itemstack8.getItem() == itemstack11.getItem() && itemstack8.getMetadata() == itemstack11.getMetadata() && ItemStack.areItemStackTagsEqual(itemstack8, itemstack11))
/*     */               {
/* 325 */                 int k2 = (dragType == 0) ? itemstack11.func_190916_E() : 1;
/*     */                 
/* 327 */                 if (k2 > slot6.getItemStackLimit(itemstack11) - itemstack8.func_190916_E())
/*     */                 {
/* 329 */                   k2 = slot6.getItemStackLimit(itemstack11) - itemstack8.func_190916_E();
/*     */                 }
/*     */                 
/* 332 */                 if (k2 > itemstack11.getMaxStackSize() - itemstack8.func_190916_E())
/*     */                 {
/* 334 */                   k2 = itemstack11.getMaxStackSize() - itemstack8.func_190916_E();
/*     */                 }
/*     */                 
/* 337 */                 itemstack11.func_190918_g(k2);
/* 338 */                 itemstack8.func_190917_f(k2);
/*     */               }
/* 340 */               else if (itemstack11.func_190916_E() <= slot6.getItemStackLimit(itemstack11))
/*     */               {
/* 342 */                 slot6.putStack(itemstack11);
/* 343 */                 inventoryplayer.setItemStack(itemstack8);
/*     */               }
/*     */             
/* 346 */             } else if (itemstack8.getItem() == itemstack11.getItem() && itemstack11.getMaxStackSize() > 1 && (!itemstack8.getHasSubtypes() || itemstack8.getMetadata() == itemstack11.getMetadata()) && ItemStack.areItemStackTagsEqual(itemstack8, itemstack11) && !itemstack8.func_190926_b()) {
/*     */               
/* 348 */               int j2 = itemstack8.func_190916_E();
/*     */               
/* 350 */               if (j2 + itemstack11.func_190916_E() <= itemstack11.getMaxStackSize()) {
/*     */                 
/* 352 */                 itemstack11.func_190917_f(j2);
/* 353 */                 itemstack8 = slot6.decrStackSize(j2);
/*     */                 
/* 355 */                 if (itemstack8.func_190926_b())
/*     */                 {
/* 357 */                   slot6.putStack(ItemStack.field_190927_a);
/*     */                 }
/*     */                 
/* 360 */                 slot6.func_190901_a(player, inventoryplayer.getItemStack());
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 365 */           slot6.onSlotChanged();
/*     */         }
/*     */       
/*     */       } 
/* 369 */     } else if (clickTypeIn == ClickType.SWAP && dragType >= 0 && dragType < 9) {
/*     */       
/* 371 */       Slot slot4 = this.inventorySlots.get(slotId);
/* 372 */       ItemStack itemstack6 = inventoryplayer.getStackInSlot(dragType);
/* 373 */       ItemStack itemstack10 = slot4.getStack();
/*     */       
/* 375 */       if (!itemstack6.func_190926_b() || !itemstack10.func_190926_b())
/*     */       {
/* 377 */         if (itemstack6.func_190926_b()) {
/*     */           
/* 379 */           if (slot4.canTakeStack(player))
/*     */           {
/* 381 */             inventoryplayer.setInventorySlotContents(dragType, itemstack10);
/* 382 */             slot4.func_190900_b(itemstack10.func_190916_E());
/* 383 */             slot4.putStack(ItemStack.field_190927_a);
/* 384 */             slot4.func_190901_a(player, itemstack10);
/*     */           }
/*     */         
/* 387 */         } else if (itemstack10.func_190926_b()) {
/*     */           
/* 389 */           if (slot4.isItemValid(itemstack6)) {
/*     */             
/* 391 */             int l1 = slot4.getItemStackLimit(itemstack6);
/*     */             
/* 393 */             if (itemstack6.func_190916_E() > l1)
/*     */             {
/* 395 */               slot4.putStack(itemstack6.splitStack(l1));
/*     */             }
/*     */             else
/*     */             {
/* 399 */               slot4.putStack(itemstack6);
/* 400 */               inventoryplayer.setInventorySlotContents(dragType, ItemStack.field_190927_a);
/*     */             }
/*     */           
/*     */           } 
/* 404 */         } else if (slot4.canTakeStack(player) && slot4.isItemValid(itemstack6)) {
/*     */           
/* 406 */           int i2 = slot4.getItemStackLimit(itemstack6);
/*     */           
/* 408 */           if (itemstack6.func_190916_E() > i2)
/*     */           {
/* 410 */             slot4.putStack(itemstack6.splitStack(i2));
/* 411 */             slot4.func_190901_a(player, itemstack10);
/*     */             
/* 413 */             if (!inventoryplayer.addItemStackToInventory(itemstack10))
/*     */             {
/* 415 */               player.dropItem(itemstack10, true);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 420 */             slot4.putStack(itemstack6);
/* 421 */             inventoryplayer.setInventorySlotContents(dragType, itemstack10);
/* 422 */             slot4.func_190901_a(player, itemstack10);
/*     */           }
/*     */         
/*     */         } 
/*     */       }
/* 427 */     } else if (clickTypeIn == ClickType.CLONE && player.capabilities.isCreativeMode && inventoryplayer.getItemStack().func_190926_b() && slotId >= 0) {
/*     */       
/* 429 */       Slot slot3 = this.inventorySlots.get(slotId);
/*     */       
/* 431 */       if (slot3 != null && slot3.getHasStack())
/*     */       {
/* 433 */         ItemStack itemstack5 = slot3.getStack().copy();
/* 434 */         itemstack5.func_190920_e(itemstack5.getMaxStackSize());
/* 435 */         inventoryplayer.setItemStack(itemstack5);
/*     */       }
/*     */     
/* 438 */     } else if (clickTypeIn == ClickType.THROW && inventoryplayer.getItemStack().func_190926_b() && slotId >= 0) {
/*     */       
/* 440 */       Slot slot2 = this.inventorySlots.get(slotId);
/*     */       
/* 442 */       if (slot2 != null && slot2.getHasStack() && slot2.canTakeStack(player))
/*     */       {
/* 444 */         ItemStack itemstack4 = slot2.decrStackSize((dragType == 0) ? 1 : slot2.getStack().func_190916_E());
/* 445 */         slot2.func_190901_a(player, itemstack4);
/* 446 */         player.dropItem(itemstack4, true);
/*     */       }
/*     */     
/* 449 */     } else if (clickTypeIn == ClickType.PICKUP_ALL && slotId >= 0) {
/*     */       
/* 451 */       Slot slot = this.inventorySlots.get(slotId);
/* 452 */       ItemStack itemstack1 = inventoryplayer.getItemStack();
/*     */       
/* 454 */       if (!itemstack1.func_190926_b() && (slot == null || !slot.getHasStack() || !slot.canTakeStack(player))) {
/*     */         
/* 456 */         int i = (dragType == 0) ? 0 : (this.inventorySlots.size() - 1);
/* 457 */         int j = (dragType == 0) ? 1 : -1;
/*     */         
/* 459 */         for (int k = 0; k < 2; k++) {
/*     */           
/* 461 */           for (int l = i; l >= 0 && l < this.inventorySlots.size() && itemstack1.func_190916_E() < itemstack1.getMaxStackSize(); l += j) {
/*     */             
/* 463 */             Slot slot1 = this.inventorySlots.get(l);
/*     */             
/* 465 */             if (slot1.getHasStack() && canAddItemToSlot(slot1, itemstack1, true) && slot1.canTakeStack(player) && canMergeSlot(itemstack1, slot1)) {
/*     */               
/* 467 */               ItemStack itemstack2 = slot1.getStack();
/*     */               
/* 469 */               if (k != 0 || itemstack2.func_190916_E() != itemstack2.getMaxStackSize()) {
/*     */                 
/* 471 */                 int i1 = Math.min(itemstack1.getMaxStackSize() - itemstack1.func_190916_E(), itemstack2.func_190916_E());
/* 472 */                 ItemStack itemstack3 = slot1.decrStackSize(i1);
/* 473 */                 itemstack1.func_190917_f(i1);
/*     */                 
/* 475 */                 if (itemstack3.func_190926_b())
/*     */                 {
/* 477 */                   slot1.putStack(ItemStack.field_190927_a);
/*     */                 }
/*     */                 
/* 480 */                 slot1.func_190901_a(player, itemstack3);
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 487 */       detectAndSendChanges();
/*     */     } 
/*     */     
/* 490 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMergeSlot(ItemStack stack, Slot slotIn) {
/* 499 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/* 507 */     InventoryPlayer inventoryplayer = playerIn.inventory;
/*     */     
/* 509 */     if (!inventoryplayer.getItemStack().func_190926_b()) {
/*     */       
/* 511 */       playerIn.dropItem(inventoryplayer.getItemStack(), false);
/* 512 */       inventoryplayer.setItemStack(ItemStack.field_190927_a);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_193327_a(EntityPlayer p_193327_1_, World p_193327_2_, IInventory p_193327_3_) {
/* 518 */     if (!p_193327_1_.isEntityAlive() || (p_193327_1_ instanceof EntityPlayerMP && ((EntityPlayerMP)p_193327_1_).func_193105_t())) {
/*     */       
/* 520 */       for (int j = 0; j < p_193327_3_.getSizeInventory(); j++)
/*     */       {
/* 522 */         p_193327_1_.dropItem(p_193327_3_.removeStackFromSlot(j), false);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 527 */       for (int i = 0; i < p_193327_3_.getSizeInventory(); i++)
/*     */       {
/* 529 */         p_193327_1_.inventory.func_191975_a(p_193327_2_, p_193327_3_.removeStackFromSlot(i));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/* 539 */     detectAndSendChanges();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void putStackInSlot(int slotID, ItemStack stack) {
/* 547 */     getSlot(slotID).putStack(stack);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190896_a(List<ItemStack> p_190896_1_) {
/* 552 */     for (int i = 0; i < p_190896_1_.size(); i++)
/*     */     {
/* 554 */       getSlot(i).putStack(p_190896_1_.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateProgressBar(int id, int data) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getNextTransactionID(InventoryPlayer invPlayer) {
/* 567 */     this.transactionID = (short)(this.transactionID + 1);
/* 568 */     return this.transactionID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanCraft(EntityPlayer player) {
/* 576 */     return !this.playerList.contains(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCanCraft(EntityPlayer player, boolean canCraft) {
/* 584 */     if (canCraft) {
/*     */       
/* 586 */       this.playerList.remove(player);
/*     */     }
/*     */     else {
/*     */       
/* 590 */       this.playerList.add(player);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean canInteractWith(EntityPlayer paramEntityPlayer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean mergeItemStack(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
/* 606 */     boolean flag = false;
/* 607 */     int i = startIndex;
/*     */     
/* 609 */     if (reverseDirection)
/*     */     {
/* 611 */       i = endIndex - 1;
/*     */     }
/*     */     
/* 614 */     if (stack.isStackable())
/*     */     {
/* 616 */       while (!stack.func_190926_b()) {
/*     */         
/* 618 */         if (reverseDirection) {
/*     */           
/* 620 */           if (i < startIndex)
/*     */           {
/*     */             break;
/*     */           }
/*     */         }
/* 625 */         else if (i >= endIndex) {
/*     */           break;
/*     */         } 
/*     */ 
/*     */         
/* 630 */         Slot slot = this.inventorySlots.get(i);
/* 631 */         ItemStack itemstack = slot.getStack();
/*     */         
/* 633 */         if (!itemstack.func_190926_b() && itemstack.getItem() == stack.getItem() && (!stack.getHasSubtypes() || stack.getMetadata() == itemstack.getMetadata()) && ItemStack.areItemStackTagsEqual(stack, itemstack)) {
/*     */           
/* 635 */           int j = itemstack.func_190916_E() + stack.func_190916_E();
/*     */           
/* 637 */           if (j <= stack.getMaxStackSize()) {
/*     */             
/* 639 */             stack.func_190920_e(0);
/* 640 */             itemstack.func_190920_e(j);
/* 641 */             slot.onSlotChanged();
/* 642 */             flag = true;
/*     */           }
/* 644 */           else if (itemstack.func_190916_E() < stack.getMaxStackSize()) {
/*     */             
/* 646 */             stack.func_190918_g(stack.getMaxStackSize() - itemstack.func_190916_E());
/* 647 */             itemstack.func_190920_e(stack.getMaxStackSize());
/* 648 */             slot.onSlotChanged();
/* 649 */             flag = true;
/*     */           } 
/*     */         } 
/*     */         
/* 653 */         if (reverseDirection) {
/*     */           
/* 655 */           i--;
/*     */           
/*     */           continue;
/*     */         } 
/* 659 */         i++;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/* 664 */     if (!stack.func_190926_b()) {
/*     */       
/* 666 */       if (reverseDirection) {
/*     */         
/* 668 */         i = endIndex - 1;
/*     */       }
/*     */       else {
/*     */         
/* 672 */         i = startIndex;
/*     */       } 
/*     */ 
/*     */       
/*     */       while (true) {
/* 677 */         if (reverseDirection) {
/*     */           
/* 679 */           if (i < startIndex)
/*     */           {
/*     */             break;
/*     */           }
/*     */         }
/* 684 */         else if (i >= endIndex) {
/*     */           break;
/*     */         } 
/*     */ 
/*     */         
/* 689 */         Slot slot1 = this.inventorySlots.get(i);
/* 690 */         ItemStack itemstack1 = slot1.getStack();
/*     */         
/* 692 */         if (itemstack1.func_190926_b() && slot1.isItemValid(stack)) {
/*     */           
/* 694 */           if (stack.func_190916_E() > slot1.getSlotStackLimit()) {
/*     */             
/* 696 */             slot1.putStack(stack.splitStack(slot1.getSlotStackLimit()));
/*     */           }
/*     */           else {
/*     */             
/* 700 */             slot1.putStack(stack.splitStack(stack.func_190916_E()));
/*     */           } 
/*     */           
/* 703 */           slot1.onSlotChanged();
/* 704 */           flag = true;
/*     */           
/*     */           break;
/*     */         } 
/* 708 */         if (reverseDirection) {
/*     */           
/* 710 */           i--;
/*     */           
/*     */           continue;
/*     */         } 
/* 714 */         i++;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 719 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int extractDragMode(int eventButton) {
/* 727 */     return eventButton >> 2 & 0x3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getDragEvent(int clickedButton) {
/* 735 */     return clickedButton & 0x3;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getQuickcraftMask(int p_94534_0_, int p_94534_1_) {
/* 740 */     return p_94534_0_ & 0x3 | (p_94534_1_ & 0x3) << 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isValidDragMode(int dragModeIn, EntityPlayer player) {
/* 745 */     if (dragModeIn == 0)
/*     */     {
/* 747 */       return true;
/*     */     }
/* 749 */     if (dragModeIn == 1)
/*     */     {
/* 751 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 755 */     return (dragModeIn == 2 && player.capabilities.isCreativeMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void resetDrag() {
/* 764 */     this.dragEvent = 0;
/* 765 */     this.dragSlots.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canAddItemToSlot(@Nullable Slot slotIn, ItemStack stack, boolean stackSizeMatters) {
/* 773 */     boolean flag = !(slotIn != null && slotIn.getHasStack());
/*     */     
/* 775 */     if (!flag && stack.isItemEqual(slotIn.getStack()) && ItemStack.areItemStackTagsEqual(slotIn.getStack(), stack))
/*     */     {
/* 777 */       return (slotIn.getStack().func_190916_E() + (stackSizeMatters ? 0 : stack.func_190916_E()) <= stack.getMaxStackSize());
/*     */     }
/*     */ 
/*     */     
/* 781 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void computeStackSize(Set<Slot> dragSlotsIn, int dragModeIn, ItemStack stack, int slotStackSize) {
/* 791 */     switch (dragModeIn) {
/*     */       
/*     */       case 0:
/* 794 */         stack.func_190920_e(MathHelper.floor(stack.func_190916_E() / dragSlotsIn.size()));
/*     */         break;
/*     */       
/*     */       case 1:
/* 798 */         stack.func_190920_e(1);
/*     */         break;
/*     */       
/*     */       case 2:
/* 802 */         stack.func_190920_e(stack.getItem().getItemStackLimit());
/*     */         break;
/*     */     } 
/* 805 */     stack.func_190917_f(slotStackSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDragIntoSlot(Slot slotIn) {
/* 814 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int calcRedstone(@Nullable TileEntity te) {
/* 822 */     return (te instanceof IInventory) ? calcRedstoneFromInventory((IInventory)te) : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int calcRedstoneFromInventory(@Nullable IInventory inv) {
/* 827 */     if (inv == null)
/*     */     {
/* 829 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 833 */     int i = 0;
/* 834 */     float f = 0.0F;
/*     */     
/* 836 */     for (int j = 0; j < inv.getSizeInventory(); j++) {
/*     */       
/* 838 */       ItemStack itemstack = inv.getStackInSlot(j);
/*     */       
/* 840 */       if (!itemstack.func_190926_b()) {
/*     */         
/* 842 */         f += itemstack.func_190916_E() / Math.min(inv.getInventoryStackLimit(), itemstack.getMaxStackSize());
/* 843 */         i++;
/*     */       } 
/*     */     } 
/*     */     
/* 847 */     f /= inv.getSizeInventory();
/* 848 */     return MathHelper.floor(f * 14.0F) + ((i > 0) ? 1 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_192389_a(World p_192389_1_, EntityPlayer p_192389_2_, InventoryCrafting p_192389_3_, InventoryCraftResult p_192389_4_) {
/* 854 */     if (!p_192389_1_.isRemote) {
/*     */       
/* 856 */       EntityPlayerMP entityplayermp = (EntityPlayerMP)p_192389_2_;
/* 857 */       ItemStack itemstack = ItemStack.field_190927_a;
/* 858 */       IRecipe irecipe = CraftingManager.func_192413_b(p_192389_3_, p_192389_1_);
/*     */       
/* 860 */       if (irecipe != null && (irecipe.func_192399_d() || !p_192389_1_.getGameRules().getBoolean("doLimitedCrafting") || entityplayermp.func_192037_E().func_193830_f(irecipe))) {
/*     */         
/* 862 */         p_192389_4_.func_193056_a(irecipe);
/* 863 */         itemstack = irecipe.getCraftingResult(p_192389_3_);
/*     */       } 
/*     */       
/* 866 */       p_192389_4_.setInventorySlotContents(0, itemstack);
/* 867 */       entityplayermp.connection.sendPacket((Packet)new SPacketSetSlot(this.windowId, 0, itemstack));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\Container.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */