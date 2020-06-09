/*     */ package net.minecraft.server.management;
/*     */ 
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockChest;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketBlockChange;
/*     */ import net.minecraft.network.play.server.SPacketPlayerListItem;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerInteractionManager
/*     */ {
/*     */   public World theWorld;
/*     */   public EntityPlayerMP thisPlayerMP;
/*  36 */   private GameType gameType = GameType.NOT_SET;
/*     */   
/*     */   private boolean isDestroyingBlock;
/*     */   
/*     */   private int initialDamage;
/*  41 */   private BlockPos destroyPos = BlockPos.ORIGIN;
/*     */ 
/*     */   
/*     */   private int curblockDamage;
/*     */ 
/*     */   
/*     */   private boolean receivedFinishDiggingPacket;
/*     */   
/*  49 */   private BlockPos delayedDestroyPos = BlockPos.ORIGIN;
/*     */   private int initialBlockDamage;
/*  51 */   private int durabilityRemainingOnBlock = -1;
/*     */ 
/*     */   
/*     */   public PlayerInteractionManager(World worldIn) {
/*  55 */     this.theWorld = worldIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setGameType(GameType type) {
/*  60 */     this.gameType = type;
/*  61 */     type.configurePlayerCapabilities(this.thisPlayerMP.capabilities);
/*  62 */     this.thisPlayerMP.sendPlayerAbilities();
/*  63 */     this.thisPlayerMP.mcServer.getPlayerList().sendPacketToAllPlayers((Packet<?>)new SPacketPlayerListItem(SPacketPlayerListItem.Action.UPDATE_GAME_MODE, new EntityPlayerMP[] { this.thisPlayerMP }));
/*  64 */     this.theWorld.updateAllPlayersSleepingFlag();
/*     */   }
/*     */ 
/*     */   
/*     */   public GameType getGameType() {
/*  69 */     return this.gameType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean survivalOrAdventure() {
/*  74 */     return this.gameType.isSurvivalOrAdventure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCreative() {
/*  82 */     return this.gameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initializeGameType(GameType type) {
/*  90 */     if (this.gameType == GameType.NOT_SET)
/*     */     {
/*  92 */       this.gameType = type;
/*     */     }
/*     */     
/*  95 */     setGameType(this.gameType);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBlockRemoving() {
/* 100 */     this.curblockDamage++;
/*     */     
/* 102 */     if (this.receivedFinishDiggingPacket) {
/*     */       
/* 104 */       int i = this.curblockDamage - this.initialBlockDamage;
/* 105 */       IBlockState iblockstate = this.theWorld.getBlockState(this.delayedDestroyPos);
/*     */       
/* 107 */       if (iblockstate.getMaterial() == Material.AIR) {
/*     */         
/* 109 */         this.receivedFinishDiggingPacket = false;
/*     */       }
/*     */       else {
/*     */         
/* 113 */         float f = iblockstate.getPlayerRelativeBlockHardness((EntityPlayer)this.thisPlayerMP, this.thisPlayerMP.world, this.delayedDestroyPos) * (i + 1);
/* 114 */         int j = (int)(f * 10.0F);
/*     */         
/* 116 */         if (j != this.durabilityRemainingOnBlock) {
/*     */           
/* 118 */           this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.delayedDestroyPos, j);
/* 119 */           this.durabilityRemainingOnBlock = j;
/*     */         } 
/*     */         
/* 122 */         if (f >= 1.0F)
/*     */         {
/* 124 */           this.receivedFinishDiggingPacket = false;
/* 125 */           tryHarvestBlock(this.delayedDestroyPos);
/*     */         }
/*     */       
/*     */       } 
/* 129 */     } else if (this.isDestroyingBlock) {
/*     */       
/* 131 */       IBlockState iblockstate1 = this.theWorld.getBlockState(this.destroyPos);
/*     */       
/* 133 */       if (iblockstate1.getMaterial() == Material.AIR) {
/*     */         
/* 135 */         this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.destroyPos, -1);
/* 136 */         this.durabilityRemainingOnBlock = -1;
/* 137 */         this.isDestroyingBlock = false;
/*     */       }
/*     */       else {
/*     */         
/* 141 */         int k = this.curblockDamage - this.initialDamage;
/* 142 */         float f1 = iblockstate1.getPlayerRelativeBlockHardness((EntityPlayer)this.thisPlayerMP, this.thisPlayerMP.world, this.delayedDestroyPos) * (k + 1);
/* 143 */         int l = (int)(f1 * 10.0F);
/*     */         
/* 145 */         if (l != this.durabilityRemainingOnBlock) {
/*     */           
/* 147 */           this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.destroyPos, l);
/* 148 */           this.durabilityRemainingOnBlock = l;
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
/*     */   public void onBlockClicked(BlockPos pos, EnumFacing side) {
/* 160 */     if (isCreative()) {
/*     */       
/* 162 */       if (!this.theWorld.extinguishFire(null, pos, side))
/*     */       {
/* 164 */         tryHarvestBlock(pos);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 169 */       IBlockState iblockstate = this.theWorld.getBlockState(pos);
/* 170 */       Block block = iblockstate.getBlock();
/*     */       
/* 172 */       if (this.gameType.isAdventure()) {
/*     */         
/* 174 */         if (this.gameType == GameType.SPECTATOR) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 179 */         if (!this.thisPlayerMP.isAllowEdit()) {
/*     */           
/* 181 */           ItemStack itemstack = this.thisPlayerMP.getHeldItemMainhand();
/*     */           
/* 183 */           if (itemstack.func_190926_b()) {
/*     */             return;
/*     */           }
/*     */ 
/*     */           
/* 188 */           if (!itemstack.canDestroy(block)) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 195 */       this.theWorld.extinguishFire(null, pos, side);
/* 196 */       this.initialDamage = this.curblockDamage;
/* 197 */       float f = 1.0F;
/*     */       
/* 199 */       if (iblockstate.getMaterial() != Material.AIR) {
/*     */         
/* 201 */         block.onBlockClicked(this.theWorld, pos, (EntityPlayer)this.thisPlayerMP);
/* 202 */         f = iblockstate.getPlayerRelativeBlockHardness((EntityPlayer)this.thisPlayerMP, this.thisPlayerMP.world, pos);
/*     */       } 
/*     */       
/* 205 */       if (iblockstate.getMaterial() != Material.AIR && f >= 1.0F) {
/*     */         
/* 207 */         tryHarvestBlock(pos);
/*     */       }
/*     */       else {
/*     */         
/* 211 */         this.isDestroyingBlock = true;
/* 212 */         this.destroyPos = pos;
/* 213 */         int i = (int)(f * 10.0F);
/* 214 */         this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), pos, i);
/* 215 */         this.durabilityRemainingOnBlock = i;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void blockRemoving(BlockPos pos) {
/* 222 */     if (pos.equals(this.destroyPos)) {
/*     */       
/* 224 */       int i = this.curblockDamage - this.initialDamage;
/* 225 */       IBlockState iblockstate = this.theWorld.getBlockState(pos);
/*     */       
/* 227 */       if (iblockstate.getMaterial() != Material.AIR) {
/*     */         
/* 229 */         float f = iblockstate.getPlayerRelativeBlockHardness((EntityPlayer)this.thisPlayerMP, this.thisPlayerMP.world, pos) * (i + 1);
/*     */         
/* 231 */         if (f >= 0.7F) {
/*     */           
/* 233 */           this.isDestroyingBlock = false;
/* 234 */           this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), pos, -1);
/* 235 */           tryHarvestBlock(pos);
/*     */         }
/* 237 */         else if (!this.receivedFinishDiggingPacket) {
/*     */           
/* 239 */           this.isDestroyingBlock = false;
/* 240 */           this.receivedFinishDiggingPacket = true;
/* 241 */           this.delayedDestroyPos = pos;
/* 242 */           this.initialBlockDamage = this.initialDamage;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cancelDestroyingBlock() {
/* 253 */     this.isDestroyingBlock = false;
/* 254 */     this.theWorld.sendBlockBreakProgress(this.thisPlayerMP.getEntityId(), this.destroyPos, -1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean removeBlock(BlockPos pos) {
/* 262 */     IBlockState iblockstate = this.theWorld.getBlockState(pos);
/* 263 */     iblockstate.getBlock().onBlockHarvested(this.theWorld, pos, iblockstate, (EntityPlayer)this.thisPlayerMP);
/* 264 */     boolean flag = this.theWorld.setBlockToAir(pos);
/*     */     
/* 266 */     if (flag)
/*     */     {
/* 268 */       iblockstate.getBlock().onBlockDestroyedByPlayer(this.theWorld, pos, iblockstate);
/*     */     }
/*     */     
/* 271 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean tryHarvestBlock(BlockPos pos) {
/* 279 */     if (this.gameType.isCreative() && !this.thisPlayerMP.getHeldItemMainhand().func_190926_b() && this.thisPlayerMP.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemSword)
/*     */     {
/* 281 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 285 */     IBlockState iblockstate = this.theWorld.getBlockState(pos);
/* 286 */     TileEntity tileentity = this.theWorld.getTileEntity(pos);
/* 287 */     Block block = iblockstate.getBlock();
/*     */     
/* 289 */     if ((block instanceof net.minecraft.block.BlockCommandBlock || block instanceof net.minecraft.block.BlockStructure) && !this.thisPlayerMP.canUseCommandBlock()) {
/*     */       
/* 291 */       this.theWorld.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);
/* 292 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 296 */     if (this.gameType.isAdventure()) {
/*     */       
/* 298 */       if (this.gameType == GameType.SPECTATOR)
/*     */       {
/* 300 */         return false;
/*     */       }
/*     */       
/* 303 */       if (!this.thisPlayerMP.isAllowEdit()) {
/*     */         
/* 305 */         ItemStack itemstack = this.thisPlayerMP.getHeldItemMainhand();
/*     */         
/* 307 */         if (itemstack.func_190926_b())
/*     */         {
/* 309 */           return false;
/*     */         }
/*     */         
/* 312 */         if (!itemstack.canDestroy(block))
/*     */         {
/* 314 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 319 */     this.theWorld.playEvent((EntityPlayer)this.thisPlayerMP, 2001, pos, Block.getStateId(iblockstate));
/* 320 */     boolean flag1 = removeBlock(pos);
/*     */     
/* 322 */     if (isCreative()) {
/*     */       
/* 324 */       this.thisPlayerMP.connection.sendPacket((Packet)new SPacketBlockChange(this.theWorld, pos));
/*     */     }
/*     */     else {
/*     */       
/* 328 */       ItemStack itemstack1 = this.thisPlayerMP.getHeldItemMainhand();
/* 329 */       ItemStack itemstack2 = itemstack1.func_190926_b() ? ItemStack.field_190927_a : itemstack1.copy();
/* 330 */       boolean flag = this.thisPlayerMP.canHarvestBlock(iblockstate);
/*     */       
/* 332 */       if (!itemstack1.func_190926_b())
/*     */       {
/* 334 */         itemstack1.onBlockDestroyed(this.theWorld, iblockstate, pos, (EntityPlayer)this.thisPlayerMP);
/*     */       }
/*     */       
/* 337 */       if (flag1 && flag)
/*     */       {
/* 339 */         iblockstate.getBlock().harvestBlock(this.theWorld, (EntityPlayer)this.thisPlayerMP, pos, iblockstate, tileentity, itemstack2);
/*     */       }
/*     */     } 
/*     */     
/* 343 */     return flag1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult processRightClick(EntityPlayer player, World worldIn, ItemStack stack, EnumHand hand) {
/* 350 */     if (this.gameType == GameType.SPECTATOR)
/*     */     {
/* 352 */       return EnumActionResult.PASS;
/*     */     }
/* 354 */     if (player.getCooldownTracker().hasCooldown(stack.getItem()))
/*     */     {
/* 356 */       return EnumActionResult.PASS;
/*     */     }
/*     */ 
/*     */     
/* 360 */     int i = stack.func_190916_E();
/* 361 */     int j = stack.getMetadata();
/* 362 */     ActionResult<ItemStack> actionresult = stack.useItemRightClick(worldIn, player, hand);
/* 363 */     ItemStack itemstack = (ItemStack)actionresult.getResult();
/*     */     
/* 365 */     if (itemstack == stack && itemstack.func_190916_E() == i && itemstack.getMaxItemUseDuration() <= 0 && itemstack.getMetadata() == j)
/*     */     {
/* 367 */       return actionresult.getType();
/*     */     }
/* 369 */     if (actionresult.getType() == EnumActionResult.FAIL && itemstack.getMaxItemUseDuration() > 0 && !player.isHandActive())
/*     */     {
/* 371 */       return actionresult.getType();
/*     */     }
/*     */ 
/*     */     
/* 375 */     player.setHeldItem(hand, itemstack);
/*     */     
/* 377 */     if (isCreative()) {
/*     */       
/* 379 */       itemstack.func_190920_e(i);
/*     */       
/* 381 */       if (itemstack.isItemStackDamageable())
/*     */       {
/* 383 */         itemstack.setItemDamage(j);
/*     */       }
/*     */     } 
/*     */     
/* 387 */     if (itemstack.func_190926_b())
/*     */     {
/* 389 */       player.setHeldItem(hand, ItemStack.field_190927_a);
/*     */     }
/*     */     
/* 392 */     if (!player.isHandActive())
/*     */     {
/* 394 */       ((EntityPlayerMP)player).sendContainerToPlayer(player.inventoryContainer);
/*     */     }
/*     */     
/* 397 */     return actionresult.getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult processRightClickBlock(EntityPlayer player, World worldIn, ItemStack stack, EnumHand hand, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ) {
/* 404 */     if (this.gameType == GameType.SPECTATOR) {
/*     */       
/* 406 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 408 */       if (tileentity instanceof ILockableContainer) {
/*     */         
/* 410 */         Block block1 = worldIn.getBlockState(pos).getBlock();
/* 411 */         ILockableContainer ilockablecontainer = (ILockableContainer)tileentity;
/*     */         
/* 413 */         if (ilockablecontainer instanceof net.minecraft.tileentity.TileEntityChest && block1 instanceof BlockChest)
/*     */         {
/* 415 */           ilockablecontainer = ((BlockChest)block1).getLockableContainer(worldIn, pos);
/*     */         }
/*     */         
/* 418 */         if (ilockablecontainer != null)
/*     */         {
/* 420 */           player.displayGUIChest((IInventory)ilockablecontainer);
/* 421 */           return EnumActionResult.SUCCESS;
/*     */         }
/*     */       
/* 424 */       } else if (tileentity instanceof IInventory) {
/*     */         
/* 426 */         player.displayGUIChest((IInventory)tileentity);
/* 427 */         return EnumActionResult.SUCCESS;
/*     */       } 
/*     */       
/* 430 */       return EnumActionResult.PASS;
/*     */     } 
/*     */ 
/*     */     
/* 434 */     if (!player.isSneaking() || (player.getHeldItemMainhand().func_190926_b() && player.getHeldItemOffhand().func_190926_b())) {
/*     */       
/* 436 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */       
/* 438 */       if (iblockstate.getBlock().onBlockActivated(worldIn, pos, iblockstate, player, hand, facing, hitX, hitY, hitZ))
/*     */       {
/* 440 */         return EnumActionResult.SUCCESS;
/*     */       }
/*     */     } 
/*     */     
/* 444 */     if (stack.func_190926_b())
/*     */     {
/* 446 */       return EnumActionResult.PASS;
/*     */     }
/* 448 */     if (player.getCooldownTracker().hasCooldown(stack.getItem()))
/*     */     {
/* 450 */       return EnumActionResult.PASS;
/*     */     }
/*     */ 
/*     */     
/* 454 */     if (stack.getItem() instanceof ItemBlock && !player.canUseCommandBlock()) {
/*     */       
/* 456 */       Block block = ((ItemBlock)stack.getItem()).getBlock();
/*     */       
/* 458 */       if (block instanceof net.minecraft.block.BlockCommandBlock || block instanceof net.minecraft.block.BlockStructure)
/*     */       {
/* 460 */         return EnumActionResult.FAIL;
/*     */       }
/*     */     } 
/*     */     
/* 464 */     if (isCreative()) {
/*     */       
/* 466 */       int j = stack.getMetadata();
/* 467 */       int i = stack.func_190916_E();
/* 468 */       EnumActionResult enumactionresult = stack.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
/* 469 */       stack.setItemDamage(j);
/* 470 */       stack.func_190920_e(i);
/* 471 */       return enumactionresult;
/*     */     } 
/*     */ 
/*     */     
/* 475 */     return stack.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorld(WorldServer serverWorld) {
/* 486 */     this.theWorld = (World)serverWorld;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\management\PlayerInteractionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */