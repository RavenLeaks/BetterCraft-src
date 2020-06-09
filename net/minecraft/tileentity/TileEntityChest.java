/*     */ package net.minecraft.tileentity;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.InventoryPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.ContainerChest;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryLargeChest;
/*     */ import net.minecraft.inventory.ItemStackHelper;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.ITickable;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.walkers.ItemStackDataLists;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ 
/*     */ public class TileEntityChest extends TileEntityLockableLoot implements ITickable {
/*  28 */   private NonNullList<ItemStack> chestContents = NonNullList.func_191197_a(27, ItemStack.field_190927_a);
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean adjacentChestChecked;
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestZNeg;
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestXPos;
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestXNeg;
/*     */ 
/*     */   
/*     */   public TileEntityChest adjacentChestZPos;
/*     */ 
/*     */   
/*     */   public float lidAngle;
/*     */ 
/*     */   
/*     */   public float prevLidAngle;
/*     */ 
/*     */   
/*     */   public int numPlayersUsing;
/*     */ 
/*     */   
/*     */   private int ticksSinceSync;
/*     */ 
/*     */   
/*     */   private BlockChest.Type cachedChestType;
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntityChest(BlockChest.Type typeIn) {
/*  64 */     this.cachedChestType = typeIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSizeInventory() {
/*  72 */     return 27;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191420_l() {
/*  77 */     for (ItemStack itemstack : this.chestContents) {
/*     */       
/*  79 */       if (!itemstack.func_190926_b())
/*     */       {
/*  81 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  93 */     return hasCustomName() ? this.field_190577_o : "container.chest";
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesChest(DataFixer fixer) {
/*  98 */     fixer.registerWalker(FixTypes.BLOCK_ENTITY, (IDataWalker)new ItemStackDataLists(TileEntityChest.class, new String[] { "Items" }));
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/* 103 */     super.readFromNBT(compound);
/* 104 */     this.chestContents = NonNullList.func_191197_a(getSizeInventory(), ItemStack.field_190927_a);
/*     */     
/* 106 */     if (!checkLootAndRead(compound))
/*     */     {
/* 108 */       ItemStackHelper.func_191283_b(compound, this.chestContents);
/*     */     }
/*     */     
/* 111 */     if (compound.hasKey("CustomName", 8))
/*     */     {
/* 113 */       this.field_190577_o = compound.getString("CustomName");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 119 */     super.writeToNBT(compound);
/*     */     
/* 121 */     if (!checkLootAndWrite(compound))
/*     */     {
/* 123 */       ItemStackHelper.func_191282_a(compound, this.chestContents);
/*     */     }
/*     */     
/* 126 */     if (hasCustomName())
/*     */     {
/* 128 */       compound.setString("CustomName", this.field_190577_o);
/*     */     }
/*     */     
/* 131 */     return compound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInventoryStackLimit() {
/* 139 */     return 64;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateContainingBlockInfo() {
/* 144 */     super.updateContainingBlockInfo();
/* 145 */     this.adjacentChestChecked = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setNeighbor(TileEntityChest chestTe, EnumFacing side) {
/* 151 */     if (chestTe.isInvalid()) {
/*     */       
/* 153 */       this.adjacentChestChecked = false;
/*     */     }
/* 155 */     else if (this.adjacentChestChecked) {
/*     */       
/* 157 */       switch (side) {
/*     */         
/*     */         case NORTH:
/* 160 */           if (this.adjacentChestZNeg != chestTe)
/*     */           {
/* 162 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case SOUTH:
/* 168 */           if (this.adjacentChestZPos != chestTe)
/*     */           {
/* 170 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case EAST:
/* 176 */           if (this.adjacentChestXPos != chestTe)
/*     */           {
/* 178 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */ 
/*     */         
/*     */         case WEST:
/* 184 */           if (this.adjacentChestXNeg != chestTe)
/*     */           {
/* 186 */             this.adjacentChestChecked = false;
/*     */           }
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkForAdjacentChests() {
/* 197 */     if (!this.adjacentChestChecked) {
/*     */       
/* 199 */       this.adjacentChestChecked = true;
/* 200 */       this.adjacentChestXNeg = getAdjacentChest(EnumFacing.WEST);
/* 201 */       this.adjacentChestXPos = getAdjacentChest(EnumFacing.EAST);
/* 202 */       this.adjacentChestZNeg = getAdjacentChest(EnumFacing.NORTH);
/* 203 */       this.adjacentChestZPos = getAdjacentChest(EnumFacing.SOUTH);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected TileEntityChest getAdjacentChest(EnumFacing side) {
/* 210 */     BlockPos blockpos = this.pos.offset(side);
/*     */     
/* 212 */     if (isChestAt(blockpos)) {
/*     */       
/* 214 */       TileEntity tileentity = this.world.getTileEntity(blockpos);
/*     */       
/* 216 */       if (tileentity instanceof TileEntityChest) {
/*     */         
/* 218 */         TileEntityChest tileentitychest = (TileEntityChest)tileentity;
/* 219 */         tileentitychest.setNeighbor(this, side.getOpposite());
/* 220 */         return tileentitychest;
/*     */       } 
/*     */     } 
/*     */     
/* 224 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isChestAt(BlockPos posIn) {
/* 229 */     if (this.world == null)
/*     */     {
/* 231 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 235 */     Block block = this.world.getBlockState(posIn).getBlock();
/* 236 */     return (block instanceof BlockChest && ((BlockChest)block).chestType == getChestType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 245 */     checkForAdjacentChests();
/* 246 */     int i = this.pos.getX();
/* 247 */     int j = this.pos.getY();
/* 248 */     int k = this.pos.getZ();
/* 249 */     this.ticksSinceSync++;
/*     */     
/* 251 */     if (!this.world.isRemote && this.numPlayersUsing != 0 && (this.ticksSinceSync + i + j + k) % 200 == 0) {
/*     */       
/* 253 */       this.numPlayersUsing = 0;
/* 254 */       float f = 5.0F;
/*     */       
/* 256 */       for (EntityPlayer entityplayer : this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB((i - 5.0F), (j - 5.0F), (k - 5.0F), ((i + 1) + 5.0F), ((j + 1) + 5.0F), ((k + 1) + 5.0F)))) {
/*     */         
/* 258 */         if (entityplayer.openContainer instanceof ContainerChest) {
/*     */           
/* 260 */           IInventory iinventory = ((ContainerChest)entityplayer.openContainer).getLowerChestInventory();
/*     */           
/* 262 */           if (iinventory == this || (iinventory instanceof InventoryLargeChest && ((InventoryLargeChest)iinventory).isPartOfLargeChest((IInventory)this)))
/*     */           {
/* 264 */             this.numPlayersUsing++;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 270 */     this.prevLidAngle = this.lidAngle;
/* 271 */     float f1 = 0.1F;
/*     */     
/* 273 */     if (this.numPlayersUsing > 0 && this.lidAngle == 0.0F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
/*     */       
/* 275 */       double d1 = i + 0.5D;
/* 276 */       double d2 = k + 0.5D;
/*     */       
/* 278 */       if (this.adjacentChestZPos != null)
/*     */       {
/* 280 */         d2 += 0.5D;
/*     */       }
/*     */       
/* 283 */       if (this.adjacentChestXPos != null)
/*     */       {
/* 285 */         d1 += 0.5D;
/*     */       }
/*     */       
/* 288 */       this.world.playSound(null, d1, j + 0.5D, d2, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
/*     */     } 
/*     */     
/* 291 */     if ((this.numPlayersUsing == 0 && this.lidAngle > 0.0F) || (this.numPlayersUsing > 0 && this.lidAngle < 1.0F)) {
/*     */       
/* 293 */       float f2 = this.lidAngle;
/*     */       
/* 295 */       if (this.numPlayersUsing > 0) {
/*     */         
/* 297 */         this.lidAngle += 0.1F;
/*     */       }
/*     */       else {
/*     */         
/* 301 */         this.lidAngle -= 0.1F;
/*     */       } 
/*     */       
/* 304 */       if (this.lidAngle > 1.0F)
/*     */       {
/* 306 */         this.lidAngle = 1.0F;
/*     */       }
/*     */       
/* 309 */       float f3 = 0.5F;
/*     */       
/* 311 */       if (this.lidAngle < 0.5F && f2 >= 0.5F && this.adjacentChestZNeg == null && this.adjacentChestXNeg == null) {
/*     */         
/* 313 */         double d3 = i + 0.5D;
/* 314 */         double d0 = k + 0.5D;
/*     */         
/* 316 */         if (this.adjacentChestZPos != null)
/*     */         {
/* 318 */           d0 += 0.5D;
/*     */         }
/*     */         
/* 321 */         if (this.adjacentChestXPos != null)
/*     */         {
/* 323 */           d3 += 0.5D;
/*     */         }
/*     */         
/* 326 */         this.world.playSound(null, d3, j + 0.5D, d0, SoundEvents.BLOCK_CHEST_CLOSE, SoundCategory.BLOCKS, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
/*     */       } 
/*     */       
/* 329 */       if (this.lidAngle < 0.0F)
/*     */       {
/* 331 */         this.lidAngle = 0.0F;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean receiveClientEvent(int id, int type) {
/* 338 */     if (id == 1) {
/*     */       
/* 340 */       this.numPlayersUsing = type;
/* 341 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 345 */     return super.receiveClientEvent(id, type);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void openInventory(EntityPlayer player) {
/* 351 */     if (!player.isSpectator()) {
/*     */       
/* 353 */       if (this.numPlayersUsing < 0)
/*     */       {
/* 355 */         this.numPlayersUsing = 0;
/*     */       }
/*     */       
/* 358 */       this.numPlayersUsing++;
/* 359 */       this.world.addBlockEvent(this.pos, getBlockType(), 1, this.numPlayersUsing);
/* 360 */       this.world.notifyNeighborsOfStateChange(this.pos, getBlockType(), false);
/*     */       
/* 362 */       if (getChestType() == BlockChest.Type.TRAP)
/*     */       {
/* 364 */         this.world.notifyNeighborsOfStateChange(this.pos.down(), getBlockType(), false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeInventory(EntityPlayer player) {
/* 371 */     if (!player.isSpectator() && getBlockType() instanceof BlockChest) {
/*     */       
/* 373 */       this.numPlayersUsing--;
/* 374 */       this.world.addBlockEvent(this.pos, getBlockType(), 1, this.numPlayersUsing);
/* 375 */       this.world.notifyNeighborsOfStateChange(this.pos, getBlockType(), false);
/*     */       
/* 377 */       if (getChestType() == BlockChest.Type.TRAP)
/*     */       {
/* 379 */         this.world.notifyNeighborsOfStateChange(this.pos.down(), getBlockType(), false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void invalidate() {
/* 389 */     super.invalidate();
/* 390 */     updateContainingBlockInfo();
/* 391 */     checkForAdjacentChests();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockChest.Type getChestType() {
/* 396 */     if (this.cachedChestType == null) {
/*     */       
/* 398 */       if (this.world == null || !(getBlockType() instanceof BlockChest))
/*     */       {
/* 400 */         return BlockChest.Type.BASIC;
/*     */       }
/*     */       
/* 403 */       this.cachedChestType = ((BlockChest)getBlockType()).chestType;
/*     */     } 
/*     */     
/* 406 */     return this.cachedChestType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGuiID() {
/* 411 */     return "minecraft:chest";
/*     */   }
/*     */ 
/*     */   
/*     */   public Container createContainer(InventoryPlayer playerInventory, EntityPlayer playerIn) {
/* 416 */     fillWithLoot(playerIn);
/* 417 */     return (Container)new ContainerChest((IInventory)playerInventory, (IInventory)this, playerIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected NonNullList<ItemStack> func_190576_q() {
/* 422 */     return this.chestContents;
/*     */   }
/*     */   
/*     */   public TileEntityChest() {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\tileentity\TileEntityChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */