/*     */ package net.minecraft.inventory;
/*     */ 
/*     */ import java.util.Map;
/*     */ import net.minecraft.block.BlockAnvil;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.Enchantment;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemEnchantedBook;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ContainerRepair extends Container {
/*  22 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final IInventory outputSlot;
/*     */ 
/*     */   
/*     */   private final IInventory inputSlots;
/*     */ 
/*     */   
/*     */   private final World theWorld;
/*     */ 
/*     */   
/*     */   private final BlockPos selfPosition;
/*     */   
/*     */   public int maximumCost;
/*     */   
/*     */   private int materialCost;
/*     */   
/*     */   private String repairedItemName;
/*     */   
/*     */   private final EntityPlayer thePlayer;
/*     */ 
/*     */   
/*     */   public ContainerRepair(InventoryPlayer playerInventory, World worldIn, EntityPlayer player) {
/*  46 */     this(playerInventory, worldIn, BlockPos.ORIGIN, player);
/*     */   }
/*     */ 
/*     */   
/*     */   public ContainerRepair(InventoryPlayer playerInventory, final World worldIn, final BlockPos blockPosIn, EntityPlayer player) {
/*  51 */     this.outputSlot = new InventoryCraftResult();
/*  52 */     this.inputSlots = new InventoryBasic("Repair", true, 2)
/*     */       {
/*     */         public void markDirty()
/*     */         {
/*  56 */           super.markDirty();
/*  57 */           ContainerRepair.this.onCraftMatrixChanged(this);
/*     */         }
/*     */       };
/*  60 */     this.selfPosition = blockPosIn;
/*  61 */     this.theWorld = worldIn;
/*  62 */     this.thePlayer = player;
/*  63 */     addSlotToContainer(new Slot(this.inputSlots, 0, 27, 47));
/*  64 */     addSlotToContainer(new Slot(this.inputSlots, 1, 76, 47));
/*  65 */     addSlotToContainer(new Slot(this.outputSlot, 2, 134, 47)
/*     */         {
/*     */           public boolean isItemValid(ItemStack stack)
/*     */           {
/*  69 */             return false;
/*     */           }
/*     */           
/*     */           public boolean canTakeStack(EntityPlayer playerIn) {
/*  73 */             return ((playerIn.capabilities.isCreativeMode || playerIn.experienceLevel >= ContainerRepair.this.maximumCost) && ContainerRepair.this.maximumCost > 0 && getHasStack());
/*     */           }
/*     */           
/*     */           public ItemStack func_190901_a(EntityPlayer p_190901_1_, ItemStack p_190901_2_) {
/*  77 */             if (!p_190901_1_.capabilities.isCreativeMode)
/*     */             {
/*  79 */               p_190901_1_.addExperienceLevel(-ContainerRepair.this.maximumCost);
/*     */             }
/*     */             
/*  82 */             ContainerRepair.this.inputSlots.setInventorySlotContents(0, ItemStack.field_190927_a);
/*     */             
/*  84 */             if (ContainerRepair.this.materialCost > 0) {
/*     */               
/*  86 */               ItemStack itemstack = ContainerRepair.this.inputSlots.getStackInSlot(1);
/*     */               
/*  88 */               if (!itemstack.func_190926_b() && itemstack.func_190916_E() > ContainerRepair.this.materialCost)
/*     */               {
/*  90 */                 itemstack.func_190918_g(ContainerRepair.this.materialCost);
/*  91 */                 ContainerRepair.this.inputSlots.setInventorySlotContents(1, itemstack);
/*     */               }
/*     */               else
/*     */               {
/*  95 */                 ContainerRepair.this.inputSlots.setInventorySlotContents(1, ItemStack.field_190927_a);
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 100 */               ContainerRepair.this.inputSlots.setInventorySlotContents(1, ItemStack.field_190927_a);
/*     */             } 
/*     */             
/* 103 */             ContainerRepair.this.maximumCost = 0;
/* 104 */             IBlockState iblockstate = worldIn.getBlockState(blockPosIn);
/*     */             
/* 106 */             if (!p_190901_1_.capabilities.isCreativeMode && !worldIn.isRemote && iblockstate.getBlock() == Blocks.ANVIL && p_190901_1_.getRNG().nextFloat() < 0.12F) {
/*     */               
/* 108 */               int l = ((Integer)iblockstate.getValue((IProperty)BlockAnvil.DAMAGE)).intValue();
/* 109 */               l++;
/*     */               
/* 111 */               if (l > 2)
/*     */               {
/* 113 */                 worldIn.setBlockToAir(blockPosIn);
/* 114 */                 worldIn.playEvent(1029, blockPosIn, 0);
/*     */               }
/*     */               else
/*     */               {
/* 118 */                 worldIn.setBlockState(blockPosIn, iblockstate.withProperty((IProperty)BlockAnvil.DAMAGE, Integer.valueOf(l)), 2);
/* 119 */                 worldIn.playEvent(1030, blockPosIn, 0);
/*     */               }
/*     */             
/* 122 */             } else if (!worldIn.isRemote) {
/*     */               
/* 124 */               worldIn.playEvent(1030, blockPosIn, 0);
/*     */             } 
/*     */             
/* 127 */             return p_190901_2_;
/*     */           }
/*     */         });
/*     */     
/* 131 */     for (int i = 0; i < 3; i++) {
/*     */       
/* 133 */       for (int j = 0; j < 9; j++)
/*     */       {
/* 135 */         addSlotToContainer(new Slot((IInventory)playerInventory, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
/*     */       }
/*     */     } 
/*     */     
/* 139 */     for (int k = 0; k < 9; k++)
/*     */     {
/* 141 */       addSlotToContainer(new Slot((IInventory)playerInventory, k, 8 + k * 18, 142));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCraftMatrixChanged(IInventory inventoryIn) {
/* 150 */     super.onCraftMatrixChanged(inventoryIn);
/*     */     
/* 152 */     if (inventoryIn == this.inputSlots)
/*     */     {
/* 154 */       updateRepairOutput();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateRepairOutput() {
/* 163 */     ItemStack itemstack = this.inputSlots.getStackInSlot(0);
/* 164 */     this.maximumCost = 1;
/* 165 */     int i = 0;
/* 166 */     int j = 0;
/* 167 */     int k = 0;
/*     */     
/* 169 */     if (itemstack.func_190926_b()) {
/*     */       
/* 171 */       this.outputSlot.setInventorySlotContents(0, ItemStack.field_190927_a);
/* 172 */       this.maximumCost = 0;
/*     */     }
/*     */     else {
/*     */       
/* 176 */       ItemStack itemstack1 = itemstack.copy();
/* 177 */       ItemStack itemstack2 = this.inputSlots.getStackInSlot(1);
/* 178 */       Map<Enchantment, Integer> map = EnchantmentHelper.getEnchantments(itemstack1);
/* 179 */       j = j + itemstack.getRepairCost() + (itemstack2.func_190926_b() ? 0 : itemstack2.getRepairCost());
/* 180 */       this.materialCost = 0;
/*     */       
/* 182 */       if (!itemstack2.func_190926_b()) {
/*     */         
/* 184 */         boolean flag = (itemstack2.getItem() == Items.ENCHANTED_BOOK && !ItemEnchantedBook.getEnchantments(itemstack2).hasNoTags());
/*     */         
/* 186 */         if (itemstack1.isItemStackDamageable() && itemstack1.getItem().getIsRepairable(itemstack, itemstack2)) {
/*     */           
/* 188 */           int l2 = Math.min(itemstack1.getItemDamage(), itemstack1.getMaxDamage() / 4);
/*     */           
/* 190 */           if (l2 <= 0) {
/*     */             
/* 192 */             this.outputSlot.setInventorySlotContents(0, ItemStack.field_190927_a);
/* 193 */             this.maximumCost = 0;
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           int i3;
/* 199 */           for (i3 = 0; l2 > 0 && i3 < itemstack2.func_190916_E(); i3++) {
/*     */             
/* 201 */             int j3 = itemstack1.getItemDamage() - l2;
/* 202 */             itemstack1.setItemDamage(j3);
/* 203 */             i++;
/* 204 */             l2 = Math.min(itemstack1.getItemDamage(), itemstack1.getMaxDamage() / 4);
/*     */           } 
/*     */           
/* 207 */           this.materialCost = i3;
/*     */         }
/*     */         else {
/*     */           
/* 211 */           if (!flag && (itemstack1.getItem() != itemstack2.getItem() || !itemstack1.isItemStackDamageable())) {
/*     */             
/* 213 */             this.outputSlot.setInventorySlotContents(0, ItemStack.field_190927_a);
/* 214 */             this.maximumCost = 0;
/*     */             
/*     */             return;
/*     */           } 
/* 218 */           if (itemstack1.isItemStackDamageable() && !flag) {
/*     */             
/* 220 */             int l = itemstack.getMaxDamage() - itemstack.getItemDamage();
/* 221 */             int i1 = itemstack2.getMaxDamage() - itemstack2.getItemDamage();
/* 222 */             int j1 = i1 + itemstack1.getMaxDamage() * 12 / 100;
/* 223 */             int k1 = l + j1;
/* 224 */             int l1 = itemstack1.getMaxDamage() - k1;
/*     */             
/* 226 */             if (l1 < 0)
/*     */             {
/* 228 */               l1 = 0;
/*     */             }
/*     */             
/* 231 */             if (l1 < itemstack1.getMetadata()) {
/*     */               
/* 233 */               itemstack1.setItemDamage(l1);
/* 234 */               i += 2;
/*     */             } 
/*     */           } 
/*     */           
/* 238 */           Map<Enchantment, Integer> map1 = EnchantmentHelper.getEnchantments(itemstack2);
/* 239 */           boolean flag2 = false;
/* 240 */           boolean flag3 = false;
/*     */           
/* 242 */           for (Enchantment enchantment1 : map1.keySet()) {
/*     */             
/* 244 */             if (enchantment1 != null) {
/*     */               
/* 246 */               int i2 = map.containsKey(enchantment1) ? ((Integer)map.get(enchantment1)).intValue() : 0;
/* 247 */               int j2 = ((Integer)map1.get(enchantment1)).intValue();
/* 248 */               j2 = (i2 == j2) ? (j2 + 1) : Math.max(j2, i2);
/* 249 */               boolean flag1 = enchantment1.canApply(itemstack);
/*     */               
/* 251 */               if (this.thePlayer.capabilities.isCreativeMode || itemstack.getItem() == Items.ENCHANTED_BOOK)
/*     */               {
/* 253 */                 flag1 = true;
/*     */               }
/*     */               
/* 256 */               for (Enchantment enchantment : map.keySet()) {
/*     */                 
/* 258 */                 if (enchantment != enchantment1 && !enchantment1.func_191560_c(enchantment)) {
/*     */                   
/* 260 */                   flag1 = false;
/* 261 */                   i++;
/*     */                 } 
/*     */               } 
/*     */               
/* 265 */               if (!flag1) {
/*     */                 
/* 267 */                 flag3 = true;
/*     */                 
/*     */                 continue;
/*     */               } 
/* 271 */               flag2 = true;
/*     */               
/* 273 */               if (j2 > enchantment1.getMaxLevel())
/*     */               {
/* 275 */                 j2 = enchantment1.getMaxLevel();
/*     */               }
/*     */               
/* 278 */               map.put(enchantment1, Integer.valueOf(j2));
/* 279 */               int k3 = 0;
/*     */               
/* 281 */               switch (enchantment1.getRarity()) {
/*     */                 
/*     */                 case null:
/* 284 */                   k3 = 1;
/*     */                   break;
/*     */                 
/*     */                 case UNCOMMON:
/* 288 */                   k3 = 2;
/*     */                   break;
/*     */                 
/*     */                 case RARE:
/* 292 */                   k3 = 4;
/*     */                   break;
/*     */                 
/*     */                 case VERY_RARE:
/* 296 */                   k3 = 8;
/*     */                   break;
/*     */               } 
/* 299 */               if (flag)
/*     */               {
/* 301 */                 k3 = Math.max(1, k3 / 2);
/*     */               }
/*     */               
/* 304 */               i += k3 * j2;
/*     */               
/* 306 */               if (itemstack.func_190916_E() > 1)
/*     */               {
/* 308 */                 i = 40;
/*     */               }
/*     */             } 
/*     */           } 
/*     */ 
/*     */           
/* 314 */           if (flag3 && !flag2) {
/*     */             
/* 316 */             this.outputSlot.setInventorySlotContents(0, ItemStack.field_190927_a);
/* 317 */             this.maximumCost = 0;
/*     */             
/*     */             return;
/*     */           } 
/*     */         } 
/*     */       } 
/* 323 */       if (StringUtils.isBlank(this.repairedItemName)) {
/*     */         
/* 325 */         if (itemstack.hasDisplayName())
/*     */         {
/* 327 */           k = 1;
/* 328 */           i += k;
/* 329 */           itemstack1.clearCustomName();
/*     */         }
/*     */       
/* 332 */       } else if (!this.repairedItemName.equals(itemstack.getDisplayName())) {
/*     */         
/* 334 */         k = 1;
/* 335 */         i += k;
/* 336 */         itemstack1.setStackDisplayName(this.repairedItemName);
/*     */       } 
/*     */       
/* 339 */       this.maximumCost = j + i;
/*     */       
/* 341 */       if (i <= 0)
/*     */       {
/* 343 */         itemstack1 = ItemStack.field_190927_a;
/*     */       }
/*     */       
/* 346 */       if (k == i && k > 0 && this.maximumCost >= 40)
/*     */       {
/* 348 */         this.maximumCost = 39;
/*     */       }
/*     */       
/* 351 */       if (this.maximumCost >= 40 && !this.thePlayer.capabilities.isCreativeMode)
/*     */       {
/* 353 */         itemstack1 = ItemStack.field_190927_a;
/*     */       }
/*     */       
/* 356 */       if (!itemstack1.func_190926_b()) {
/*     */         
/* 358 */         int k2 = itemstack1.getRepairCost();
/*     */         
/* 360 */         if (!itemstack2.func_190926_b() && k2 < itemstack2.getRepairCost())
/*     */         {
/* 362 */           k2 = itemstack2.getRepairCost();
/*     */         }
/*     */         
/* 365 */         if (k != i || k == 0)
/*     */         {
/* 367 */           k2 = k2 * 2 + 1;
/*     */         }
/*     */         
/* 370 */         itemstack1.setRepairCost(k2);
/* 371 */         EnchantmentHelper.setEnchantments(map, itemstack1);
/*     */       } 
/*     */       
/* 374 */       this.outputSlot.setInventorySlotContents(0, itemstack1);
/* 375 */       detectAndSendChanges();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addListener(IContainerListener listener) {
/* 381 */     super.addListener(listener);
/* 382 */     listener.sendProgressBarUpdate(this, 0, this.maximumCost);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateProgressBar(int id, int data) {
/* 387 */     if (id == 0)
/*     */     {
/* 389 */       this.maximumCost = data;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onContainerClosed(EntityPlayer playerIn) {
/* 398 */     super.onContainerClosed(playerIn);
/*     */     
/* 400 */     if (!this.theWorld.isRemote)
/*     */     {
/* 402 */       func_193327_a(playerIn, this.theWorld, this.inputSlots);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canInteractWith(EntityPlayer playerIn) {
/* 411 */     if (this.theWorld.getBlockState(this.selfPosition).getBlock() != Blocks.ANVIL)
/*     */     {
/* 413 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 417 */     return (playerIn.getDistanceSq(this.selfPosition.getX() + 0.5D, this.selfPosition.getY() + 0.5D, this.selfPosition.getZ() + 0.5D) <= 64.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
/* 426 */     ItemStack itemstack = ItemStack.field_190927_a;
/* 427 */     Slot slot = this.inventorySlots.get(index);
/*     */     
/* 429 */     if (slot != null && slot.getHasStack()) {
/*     */       
/* 431 */       ItemStack itemstack1 = slot.getStack();
/* 432 */       itemstack = itemstack1.copy();
/*     */       
/* 434 */       if (index == 2) {
/*     */         
/* 436 */         if (!mergeItemStack(itemstack1, 3, 39, true))
/*     */         {
/* 438 */           return ItemStack.field_190927_a;
/*     */         }
/*     */         
/* 441 */         slot.onSlotChange(itemstack1, itemstack);
/*     */       }
/* 443 */       else if (index != 0 && index != 1) {
/*     */         
/* 445 */         if (index >= 3 && index < 39 && !mergeItemStack(itemstack1, 0, 2, false))
/*     */         {
/* 447 */           return ItemStack.field_190927_a;
/*     */         }
/*     */       }
/* 450 */       else if (!mergeItemStack(itemstack1, 3, 39, false)) {
/*     */         
/* 452 */         return ItemStack.field_190927_a;
/*     */       } 
/*     */       
/* 455 */       if (itemstack1.func_190926_b()) {
/*     */         
/* 457 */         slot.putStack(ItemStack.field_190927_a);
/*     */       }
/*     */       else {
/*     */         
/* 461 */         slot.onSlotChanged();
/*     */       } 
/*     */       
/* 464 */       if (itemstack1.func_190916_E() == itemstack.func_190916_E())
/*     */       {
/* 466 */         return ItemStack.field_190927_a;
/*     */       }
/*     */       
/* 469 */       slot.func_190901_a(playerIn, itemstack1);
/*     */     } 
/*     */     
/* 472 */     return itemstack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateItemName(String newName) {
/* 480 */     this.repairedItemName = newName;
/*     */     
/* 482 */     if (getSlot(2).getHasStack()) {
/*     */       
/* 484 */       ItemStack itemstack = getSlot(2).getStack();
/*     */       
/* 486 */       if (StringUtils.isBlank(newName)) {
/*     */         
/* 488 */         itemstack.clearCustomName();
/*     */       }
/*     */       else {
/*     */         
/* 492 */         itemstack.setStackDisplayName(this.repairedItemName);
/*     */       } 
/*     */     } 
/*     */     
/* 496 */     updateRepairOutput();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\inventory\ContainerRepair.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */