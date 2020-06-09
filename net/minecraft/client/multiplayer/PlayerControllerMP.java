/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import io.netty.buffer.Unpooled;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.SoundType;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.entity.EntityPlayerSP;
/*     */ import net.minecraft.client.network.NetHandlerPlayClient;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.inventory.ClickType;
/*     */ import net.minecraft.item.ItemBlock;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.item.crafting.IRecipe;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.CPacketClickWindow;
/*     */ import net.minecraft.network.play.client.CPacketCreativeInventoryAction;
/*     */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*     */ import net.minecraft.network.play.client.CPacketEnchantItem;
/*     */ import net.minecraft.network.play.client.CPacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.CPacketPlaceRecipe;
/*     */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
/*     */ import net.minecraft.network.play.client.CPacketPlayerTryUseItemOnBlock;
/*     */ import net.minecraft.network.play.client.CPacketUseEntity;
/*     */ import net.minecraft.stats.RecipeBook;
/*     */ import net.minecraft.stats.StatisticsManager;
/*     */ import net.minecraft.util.ActionResult;
/*     */ import net.minecraft.util.EnumActionResult;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerControllerMP
/*     */ {
/*     */   private final Minecraft mc;
/*     */   private final NetHandlerPlayClient connection;
/*  53 */   private BlockPos currentBlock = new BlockPos(-1, -1, -1);
/*     */ 
/*     */   
/*  56 */   private ItemStack currentItemHittingBlock = ItemStack.field_190927_a;
/*     */ 
/*     */ 
/*     */   
/*     */   private float curBlockDamageMP;
/*     */ 
/*     */ 
/*     */   
/*     */   private float stepSoundTickCounter;
/*     */ 
/*     */ 
/*     */   
/*     */   private int blockHitDelay;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isHittingBlock;
/*     */ 
/*     */   
/*  75 */   private GameType currentGameType = GameType.SURVIVAL;
/*     */ 
/*     */   
/*     */   private int currentPlayerItem;
/*     */ 
/*     */   
/*     */   public PlayerControllerMP(Minecraft mcIn, NetHandlerPlayClient netHandler) {
/*  82 */     this.mc = mcIn;
/*  83 */     this.connection = netHandler;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void clickBlockCreative(Minecraft mcIn, PlayerControllerMP playerController, BlockPos pos, EnumFacing facing) {
/*  88 */     if (!mcIn.world.extinguishFire((EntityPlayer)mcIn.player, pos, facing))
/*     */     {
/*  90 */       playerController.onPlayerDestroyBlock(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayerCapabilities(EntityPlayer player) {
/*  99 */     this.currentGameType.configurePlayerCapabilities(player.capabilities);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSpectator() {
/* 107 */     return (this.currentGameType == GameType.SPECTATOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameType(GameType type) {
/* 115 */     this.currentGameType = type;
/* 116 */     this.currentGameType.configurePlayerCapabilities(this.mc.player.capabilities);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flipPlayer(EntityPlayer playerIn) {
/* 124 */     playerIn.rotationYaw = -180.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldDrawHUD() {
/* 129 */     return this.currentGameType.isSurvivalOrAdventure();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDestroyBlock(BlockPos pos) {
/* 134 */     if (this.currentGameType.isAdventure()) {
/*     */       
/* 136 */       if (this.currentGameType == GameType.SPECTATOR)
/*     */       {
/* 138 */         return false;
/*     */       }
/*     */       
/* 141 */       if (!this.mc.player.isAllowEdit()) {
/*     */         
/* 143 */         ItemStack itemstack = this.mc.player.getHeldItemMainhand();
/*     */         
/* 145 */         if (itemstack.func_190926_b())
/*     */         {
/* 147 */           return false;
/*     */         }
/*     */         
/* 150 */         if (!itemstack.canDestroy(this.mc.world.getBlockState(pos).getBlock()))
/*     */         {
/* 152 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 157 */     if (this.currentGameType.isCreative() && !this.mc.player.getHeldItemMainhand().func_190926_b() && this.mc.player.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemSword)
/*     */     {
/* 159 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 163 */     World world = this.mc.world;
/* 164 */     IBlockState iblockstate = world.getBlockState(pos);
/* 165 */     Block block = iblockstate.getBlock();
/*     */     
/* 167 */     if ((block instanceof net.minecraft.block.BlockCommandBlock || block instanceof net.minecraft.block.BlockStructure) && !this.mc.player.canUseCommandBlock())
/*     */     {
/* 169 */       return false;
/*     */     }
/* 171 */     if (iblockstate.getMaterial() == Material.AIR)
/*     */     {
/* 173 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 177 */     world.playEvent(2001, pos, Block.getStateId(iblockstate));
/* 178 */     block.onBlockHarvested(world, pos, iblockstate, (EntityPlayer)this.mc.player);
/* 179 */     boolean flag = world.setBlockState(pos, Blocks.AIR.getDefaultState(), 11);
/*     */     
/* 181 */     if (flag)
/*     */     {
/* 183 */       block.onBlockDestroyedByPlayer(world, pos, iblockstate);
/*     */     }
/*     */     
/* 186 */     this.currentBlock = new BlockPos(this.currentBlock.getX(), -1, this.currentBlock.getZ());
/*     */     
/* 188 */     if (!this.currentGameType.isCreative()) {
/*     */       
/* 190 */       ItemStack itemstack1 = this.mc.player.getHeldItemMainhand();
/*     */       
/* 192 */       if (!itemstack1.func_190926_b()) {
/*     */         
/* 194 */         itemstack1.onBlockDestroyed(world, iblockstate, pos, (EntityPlayer)this.mc.player);
/*     */         
/* 196 */         if (itemstack1.func_190926_b())
/*     */         {
/* 198 */           this.mc.player.setHeldItem(EnumHand.MAIN_HAND, ItemStack.field_190927_a);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 203 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean clickBlock(BlockPos loc, EnumFacing face) {
/* 213 */     if (this.currentGameType.isAdventure()) {
/*     */       
/* 215 */       if (this.currentGameType == GameType.SPECTATOR)
/*     */       {
/* 217 */         return false;
/*     */       }
/*     */       
/* 220 */       if (!this.mc.player.isAllowEdit()) {
/*     */         
/* 222 */         ItemStack itemstack = this.mc.player.getHeldItemMainhand();
/*     */         
/* 224 */         if (itemstack.func_190926_b())
/*     */         {
/* 226 */           return false;
/*     */         }
/*     */         
/* 229 */         if (!itemstack.canDestroy(this.mc.world.getBlockState(loc).getBlock()))
/*     */         {
/* 231 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 236 */     if (!this.mc.world.getWorldBorder().contains(loc))
/*     */     {
/* 238 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 242 */     if (this.currentGameType.isCreative()) {
/*     */       
/* 244 */       this.mc.func_193032_ao().func_193294_a(this.mc.world, loc, this.mc.world.getBlockState(loc), 1.0F);
/* 245 */       this.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face));
/* 246 */       clickBlockCreative(this.mc, this, loc, face);
/* 247 */       this.blockHitDelay = 5;
/*     */     }
/* 249 */     else if (!this.isHittingBlock || !isHittingPosition(loc)) {
/*     */       
/* 251 */       if (this.isHittingBlock)
/*     */       {
/* 253 */         this.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, face));
/*     */       }
/*     */       
/* 256 */       IBlockState iblockstate = this.mc.world.getBlockState(loc);
/* 257 */       this.mc.func_193032_ao().func_193294_a(this.mc.world, loc, iblockstate, 0.0F);
/* 258 */       this.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, loc, face));
/* 259 */       boolean flag = (iblockstate.getMaterial() != Material.AIR);
/*     */       
/* 261 */       if (flag && this.curBlockDamageMP == 0.0F)
/*     */       {
/* 263 */         iblockstate.getBlock().onBlockClicked(this.mc.world, loc, (EntityPlayer)this.mc.player);
/*     */       }
/*     */       
/* 266 */       if (flag && iblockstate.getPlayerRelativeBlockHardness((EntityPlayer)this.mc.player, this.mc.player.world, loc) >= 1.0F) {
/*     */         
/* 268 */         onPlayerDestroyBlock(loc);
/*     */       }
/*     */       else {
/*     */         
/* 272 */         this.isHittingBlock = true;
/* 273 */         this.currentBlock = loc;
/* 274 */         this.currentItemHittingBlock = this.mc.player.getHeldItemMainhand();
/* 275 */         this.curBlockDamageMP = 0.0F;
/* 276 */         this.stepSoundTickCounter = 0.0F;
/* 277 */         this.mc.world.sendBlockBreakProgress(this.mc.player.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0F) - 1);
/*     */       } 
/*     */     } 
/*     */     
/* 281 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetBlockRemoving() {
/* 290 */     if (this.isHittingBlock) {
/*     */       
/* 292 */       this.mc.func_193032_ao().func_193294_a(this.mc.world, this.currentBlock, this.mc.world.getBlockState(this.currentBlock), -1.0F);
/* 293 */       this.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, this.currentBlock, EnumFacing.DOWN));
/* 294 */       this.isHittingBlock = false;
/* 295 */       this.curBlockDamageMP = 0.0F;
/* 296 */       this.mc.world.sendBlockBreakProgress(this.mc.player.getEntityId(), this.currentBlock, -1);
/* 297 */       this.mc.player.resetCooldown();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onPlayerDamageBlock(BlockPos posBlock, EnumFacing directionFacing) {
/* 303 */     syncCurrentPlayItem();
/*     */     
/* 305 */     if (this.blockHitDelay > 0) {
/*     */       
/* 307 */       this.blockHitDelay--;
/* 308 */       return true;
/*     */     } 
/* 310 */     if (this.currentGameType.isCreative() && this.mc.world.getWorldBorder().contains(posBlock)) {
/*     */       
/* 312 */       this.blockHitDelay = 5;
/* 313 */       this.mc.func_193032_ao().func_193294_a(this.mc.world, posBlock, this.mc.world.getBlockState(posBlock), 1.0F);
/* 314 */       this.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.START_DESTROY_BLOCK, posBlock, directionFacing));
/* 315 */       clickBlockCreative(this.mc, this, posBlock, directionFacing);
/* 316 */       return true;
/*     */     } 
/* 318 */     if (isHittingPosition(posBlock)) {
/*     */       
/* 320 */       IBlockState iblockstate = this.mc.world.getBlockState(posBlock);
/* 321 */       Block block = iblockstate.getBlock();
/*     */       
/* 323 */       if (iblockstate.getMaterial() == Material.AIR) {
/*     */         
/* 325 */         this.isHittingBlock = false;
/* 326 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 330 */       this.curBlockDamageMP += iblockstate.getPlayerRelativeBlockHardness((EntityPlayer)this.mc.player, this.mc.player.world, posBlock);
/*     */       
/* 332 */       if (this.stepSoundTickCounter % 4.0F == 0.0F) {
/*     */         
/* 334 */         SoundType soundtype = block.getSoundType();
/* 335 */         this.mc.getSoundHandler().playSound((ISound)new PositionedSoundRecord(soundtype.getHitSound(), SoundCategory.NEUTRAL, (soundtype.getVolume() + 1.0F) / 8.0F, soundtype.getPitch() * 0.5F, posBlock));
/*     */       } 
/*     */       
/* 338 */       this.stepSoundTickCounter++;
/* 339 */       this.mc.func_193032_ao().func_193294_a(this.mc.world, posBlock, iblockstate, MathHelper.clamp(this.curBlockDamageMP, 0.0F, 1.0F));
/*     */       
/* 341 */       if (this.curBlockDamageMP >= 1.0F) {
/*     */         
/* 343 */         this.isHittingBlock = false;
/* 344 */         this.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.STOP_DESTROY_BLOCK, posBlock, directionFacing));
/* 345 */         onPlayerDestroyBlock(posBlock);
/* 346 */         this.curBlockDamageMP = 0.0F;
/* 347 */         this.stepSoundTickCounter = 0.0F;
/* 348 */         this.blockHitDelay = 5;
/*     */       } 
/*     */       
/* 351 */       this.mc.world.sendBlockBreakProgress(this.mc.player.getEntityId(), this.currentBlock, (int)(this.curBlockDamageMP * 10.0F) - 1);
/* 352 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 357 */     return clickBlock(posBlock, directionFacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBlockReachDistance() {
/* 366 */     return this.currentGameType.isCreative() ? 5.0F : 4.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateController() {
/* 371 */     syncCurrentPlayItem();
/*     */     
/* 373 */     if (this.connection.getNetworkManager().isChannelOpen()) {
/*     */       
/* 375 */       this.connection.getNetworkManager().processReceivedPackets();
/*     */     }
/*     */     else {
/*     */       
/* 379 */       this.connection.getNetworkManager().checkDisconnected();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isHittingPosition(BlockPos pos) {
/* 385 */     ItemStack itemstack = this.mc.player.getHeldItemMainhand();
/* 386 */     boolean flag = (this.currentItemHittingBlock.func_190926_b() && itemstack.func_190926_b());
/*     */     
/* 388 */     if (!this.currentItemHittingBlock.func_190926_b() && !itemstack.func_190926_b())
/*     */     {
/* 390 */       flag = (itemstack.getItem() == this.currentItemHittingBlock.getItem() && ItemStack.areItemStackTagsEqual(itemstack, this.currentItemHittingBlock) && (itemstack.isItemStackDamageable() || itemstack.getMetadata() == this.currentItemHittingBlock.getMetadata()));
/*     */     }
/*     */     
/* 393 */     return (pos.equals(this.currentBlock) && flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void syncCurrentPlayItem() {
/* 401 */     int i = this.mc.player.inventory.currentItem;
/*     */     
/* 403 */     if (i != this.currentPlayerItem) {
/*     */       
/* 405 */       this.currentPlayerItem = i;
/* 406 */       this.connection.sendPacket((Packet)new CPacketHeldItemChange(this.currentPlayerItem));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumActionResult processRightClickBlock(EntityPlayerSP player, WorldClient worldIn, BlockPos stack, EnumFacing pos, Vec3d facing, EnumHand vec) {
/* 412 */     syncCurrentPlayItem();
/* 413 */     ItemStack itemstack = player.getHeldItem(vec);
/* 414 */     float f = (float)(facing.xCoord - stack.getX());
/* 415 */     float f1 = (float)(facing.yCoord - stack.getY());
/* 416 */     float f2 = (float)(facing.zCoord - stack.getZ());
/* 417 */     boolean flag = false;
/*     */     
/* 419 */     if (!this.mc.world.getWorldBorder().contains(stack))
/*     */     {
/* 421 */       return EnumActionResult.FAIL;
/*     */     }
/*     */ 
/*     */     
/* 425 */     if (this.currentGameType != GameType.SPECTATOR) {
/*     */       
/* 427 */       IBlockState iblockstate = worldIn.getBlockState(stack);
/*     */       
/* 429 */       if ((!player.isSneaking() || (player.getHeldItemMainhand().func_190926_b() && player.getHeldItemOffhand().func_190926_b())) && iblockstate.getBlock().onBlockActivated(worldIn, stack, iblockstate, (EntityPlayer)player, vec, pos, f, f1, f2))
/*     */       {
/* 431 */         flag = true;
/*     */       }
/*     */       
/* 434 */       if (!flag && itemstack.getItem() instanceof ItemBlock) {
/*     */         
/* 436 */         ItemBlock itemblock = (ItemBlock)itemstack.getItem();
/*     */         
/* 438 */         if (!itemblock.canPlaceBlockOnSide(worldIn, stack, pos, (EntityPlayer)player, itemstack))
/*     */         {
/* 440 */           return EnumActionResult.FAIL;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 445 */     this.connection.sendPacket((Packet)new CPacketPlayerTryUseItemOnBlock(stack, pos, vec, f, f1, f2));
/*     */     
/* 447 */     if (!flag && this.currentGameType != GameType.SPECTATOR) {
/*     */       
/* 449 */       if (itemstack.func_190926_b())
/*     */       {
/* 451 */         return EnumActionResult.PASS;
/*     */       }
/* 453 */       if (player.getCooldownTracker().hasCooldown(itemstack.getItem()))
/*     */       {
/* 455 */         return EnumActionResult.PASS;
/*     */       }
/*     */ 
/*     */       
/* 459 */       if (itemstack.getItem() instanceof ItemBlock && !player.canUseCommandBlock()) {
/*     */         
/* 461 */         Block block = ((ItemBlock)itemstack.getItem()).getBlock();
/*     */         
/* 463 */         if (block instanceof net.minecraft.block.BlockCommandBlock || block instanceof net.minecraft.block.BlockStructure)
/*     */         {
/* 465 */           return EnumActionResult.FAIL;
/*     */         }
/*     */       } 
/*     */       
/* 469 */       if (this.currentGameType.isCreative()) {
/*     */         
/* 471 */         int i = itemstack.getMetadata();
/* 472 */         int j = itemstack.func_190916_E();
/* 473 */         EnumActionResult enumactionresult = itemstack.onItemUse((EntityPlayer)player, worldIn, stack, vec, pos, f, f1, f2);
/* 474 */         itemstack.setItemDamage(i);
/* 475 */         itemstack.func_190920_e(j);
/* 476 */         return enumactionresult;
/*     */       } 
/*     */ 
/*     */       
/* 480 */       return itemstack.onItemUse((EntityPlayer)player, worldIn, stack, vec, pos, f, f1, f2);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 486 */     return EnumActionResult.SUCCESS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult processRightClick(EntityPlayer player, World worldIn, EnumHand stack) {
/* 493 */     if (this.currentGameType == GameType.SPECTATOR)
/*     */     {
/* 495 */       return EnumActionResult.PASS;
/*     */     }
/*     */ 
/*     */     
/* 499 */     syncCurrentPlayItem();
/* 500 */     this.connection.sendPacket((Packet)new CPacketPlayerTryUseItem(stack));
/* 501 */     ItemStack itemstack = player.getHeldItem(stack);
/*     */     
/* 503 */     if (player.getCooldownTracker().hasCooldown(itemstack.getItem()))
/*     */     {
/* 505 */       return EnumActionResult.PASS;
/*     */     }
/*     */ 
/*     */     
/* 509 */     int i = itemstack.func_190916_E();
/* 510 */     ActionResult<ItemStack> actionresult = itemstack.useItemRightClick(worldIn, player, stack);
/* 511 */     ItemStack itemstack1 = (ItemStack)actionresult.getResult();
/*     */     
/* 513 */     if (itemstack1 != itemstack || itemstack1.func_190916_E() != i)
/*     */     {
/* 515 */       player.setHeldItem(stack, itemstack1);
/*     */     }
/*     */     
/* 518 */     return actionresult.getType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityPlayerSP func_192830_a(World p_192830_1_, StatisticsManager p_192830_2_, RecipeBook p_192830_3_) {
/* 525 */     return new EntityPlayerSP(this.mc, p_192830_1_, this.connection, p_192830_2_, p_192830_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntity(EntityPlayer playerIn, Entity targetEntity) {
/* 533 */     syncCurrentPlayItem();
/* 534 */     this.connection.sendPacket((Packet)new CPacketUseEntity(targetEntity));
/*     */     
/* 536 */     if (this.currentGameType != GameType.SPECTATOR) {
/*     */       
/* 538 */       playerIn.attackTargetEntityWithCurrentItem(targetEntity);
/* 539 */       playerIn.resetCooldown();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult interactWithEntity(EntityPlayer player, Entity target, EnumHand heldItem) {
/* 548 */     syncCurrentPlayItem();
/* 549 */     this.connection.sendPacket((Packet)new CPacketUseEntity(target, heldItem));
/* 550 */     return (this.currentGameType == GameType.SPECTATOR) ? EnumActionResult.PASS : player.func_190775_a(target, heldItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumActionResult interactWithEntity(EntityPlayer player, Entity target, RayTraceResult raytrace, EnumHand heldItem) {
/* 558 */     syncCurrentPlayItem();
/* 559 */     Vec3d vec3d = new Vec3d(raytrace.hitVec.xCoord - target.posX, raytrace.hitVec.yCoord - target.posY, raytrace.hitVec.zCoord - target.posZ);
/* 560 */     this.connection.sendPacket((Packet)new CPacketUseEntity(target, heldItem, vec3d));
/* 561 */     return (this.currentGameType == GameType.SPECTATOR) ? EnumActionResult.PASS : target.applyPlayerInteraction(player, vec3d, heldItem);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack windowClick(int windowId, int slotId, int mouseButton, ClickType type, EntityPlayer player) {
/* 569 */     short short1 = player.openContainer.getNextTransactionID(player.inventory);
/* 570 */     ItemStack itemstack = player.openContainer.slotClick(slotId, mouseButton, type, player);
/* 571 */     this.connection.sendPacket((Packet)new CPacketClickWindow(windowId, slotId, mouseButton, type, itemstack, short1));
/* 572 */     return itemstack;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194338_a(int p_194338_1_, IRecipe p_194338_2_, boolean p_194338_3_, EntityPlayer p_194338_4_) {
/* 577 */     this.connection.sendPacket((Packet)new CPacketPlaceRecipe(p_194338_1_, p_194338_2_, p_194338_3_));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendEnchantPacket(int windowID, int button) {
/* 586 */     this.connection.sendPacket((Packet)new CPacketEnchantItem(windowID, button));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendSlotPacket(ItemStack itemStackIn, int slotId) {
/* 594 */     if (this.currentGameType.isCreative())
/*     */     {
/* 596 */       this.connection.sendPacket((Packet)new CPacketCreativeInventoryAction(slotId, itemStackIn));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendPacketDropItem(ItemStack itemStackIn) {
/* 605 */     if (this.currentGameType.isCreative() && !itemStackIn.func_190926_b())
/*     */     {
/* 607 */       this.connection.sendPacket((Packet)new CPacketCreativeInventoryAction(-1, itemStackIn));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void onStoppedUsingItem(EntityPlayer playerIn) {
/* 613 */     syncCurrentPlayItem();
/* 614 */     this.connection.sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
/* 615 */     playerIn.stopActiveHand();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean gameIsSurvivalOrAdventure() {
/* 620 */     return this.currentGameType.isSurvivalOrAdventure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotCreative() {
/* 628 */     return !this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInCreativeMode() {
/* 636 */     return this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean extendedReach() {
/* 644 */     return this.currentGameType.isCreative();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRidingHorse() {
/* 652 */     return (this.mc.player.isRiding() && this.mc.player.getRidingEntity() instanceof net.minecraft.entity.passive.AbstractHorse);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSpectatorMode() {
/* 657 */     return (this.currentGameType == GameType.SPECTATOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public GameType getCurrentGameType() {
/* 662 */     return this.currentGameType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsHittingBlock() {
/* 670 */     return this.isHittingBlock;
/*     */   }
/*     */ 
/*     */   
/*     */   public void pickItem(int index) {
/* 675 */     this.connection.sendPacket((Packet)new CPacketCustomPayload("MC|PickItem", (new PacketBuffer(Unpooled.buffer())).writeVarIntToBuffer(index)));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\multiplayer\PlayerControllerMP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */