/*      */ package net.minecraft.entity.player;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.List;
/*      */ import java.util.UUID;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockBed;
/*      */ import net.minecraft.block.BlockHorizontal;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.EnumCreatureAttribute;
/*      */ import net.minecraft.entity.IEntityMultiPart;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.MultiPartEntityPart;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.monster.EntityMob;
/*      */ import net.minecraft.entity.passive.AbstractHorse;
/*      */ import net.minecraft.entity.passive.EntityParrot;
/*      */ import net.minecraft.entity.passive.EntityPig;
/*      */ import net.minecraft.entity.passive.EntityTameable;
/*      */ import net.minecraft.entity.projectile.EntityFishHook;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.init.MobEffects;
/*      */ import net.minecraft.init.SoundEvents;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.ContainerPlayer;
/*      */ import net.minecraft.inventory.EntityEquipmentSlot;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryEnderChest;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.crafting.IRecipe;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.datasync.DataSerializers;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.network.play.server.SPacketEntityVelocity;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.tileentity.CommandBlockBaseLogic;
/*      */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.tileentity.TileEntityStructure;
/*      */ import net.minecraft.util.CooldownTracker;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumActionResult;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.EnumHandSide;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.FoodStats;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.SoundCategory;
/*      */ import net.minecraft.util.SoundEvent;
/*      */ import net.minecraft.util.datafix.DataFixer;
/*      */ import net.minecraft.util.datafix.DataFixesManager;
/*      */ import net.minecraft.util.datafix.FixTypes;
/*      */ import net.minecraft.util.datafix.IDataFixer;
/*      */ import net.minecraft.util.datafix.IDataWalker;
/*      */ import net.minecraft.util.datafix.IFixType;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.TextComponentString;
/*      */ import net.minecraft.util.text.event.ClickEvent;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.GameType;
/*      */ import net.minecraft.world.IInteractionObject;
/*      */ import net.minecraft.world.LockCode;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class EntityPlayer
/*      */   extends EntityLivingBase
/*      */ {
/*   97 */   public static final DataParameter<Float> ABSORPTION = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.FLOAT);
/*   98 */   public static final DataParameter<Integer> PLAYER_SCORE = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.VARINT);
/*   99 */   public static final DataParameter<Byte> PLAYER_MODEL_FLAG = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.BYTE);
/*  100 */   protected static final DataParameter<Byte> MAIN_HAND = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.BYTE);
/*  101 */   protected static final DataParameter<NBTTagCompound> field_192032_bt = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.field_192734_n);
/*  102 */   protected static final DataParameter<NBTTagCompound> field_192033_bu = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.field_192734_n);
/*      */ 
/*      */   
/*  105 */   public InventoryPlayer inventory = new InventoryPlayer(this);
/*  106 */   protected InventoryEnderChest theInventoryEnderChest = new InventoryEnderChest();
/*      */ 
/*      */ 
/*      */   
/*      */   public Container inventoryContainer;
/*      */ 
/*      */ 
/*      */   
/*      */   public Container openContainer;
/*      */ 
/*      */   
/*  117 */   protected FoodStats foodStats = new FoodStats();
/*      */ 
/*      */   
/*      */   protected int flyToggleTimer;
/*      */ 
/*      */   
/*      */   public float prevCameraYaw;
/*      */ 
/*      */   
/*      */   public float cameraYaw;
/*      */ 
/*      */   
/*      */   public int xpCooldown;
/*      */   
/*      */   public double prevChasingPosX;
/*      */   
/*      */   public double prevChasingPosY;
/*      */   
/*      */   public double prevChasingPosZ;
/*      */   
/*      */   public double chasingPosX;
/*      */   
/*      */   public double chasingPosY;
/*      */   
/*      */   public double chasingPosZ;
/*      */   
/*      */   protected boolean sleeping;
/*      */   
/*      */   public BlockPos bedLocation;
/*      */   
/*      */   private int sleepTimer;
/*      */   
/*      */   public float renderOffsetX;
/*      */   
/*      */   public float renderOffsetY;
/*      */   
/*      */   public float renderOffsetZ;
/*      */   
/*      */   private BlockPos spawnChunk;
/*      */   
/*      */   private boolean spawnForced;
/*      */   
/*  159 */   public PlayerCapabilities capabilities = new PlayerCapabilities();
/*      */ 
/*      */ 
/*      */   
/*      */   public int experienceLevel;
/*      */ 
/*      */   
/*      */   public int experienceTotal;
/*      */ 
/*      */   
/*      */   public float experience;
/*      */ 
/*      */   
/*      */   protected int xpSeed;
/*      */ 
/*      */   
/*  175 */   protected float speedInAir = 0.02F;
/*      */   
/*      */   private int lastXPSound;
/*      */   
/*      */   private final GameProfile gameProfile;
/*      */   private boolean hasReducedDebug;
/*  181 */   private ItemStack itemStackMainHand = ItemStack.field_190927_a;
/*  182 */   private final CooldownTracker cooldownTracker = createCooldownTracker();
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityFishHook fishEntity;
/*      */ 
/*      */ 
/*      */   
/*      */   protected CooldownTracker createCooldownTracker() {
/*  192 */     return new CooldownTracker();
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityPlayer(World worldIn, GameProfile gameProfileIn) {
/*  197 */     super(worldIn);
/*  198 */     setUniqueId(getUUID(gameProfileIn));
/*  199 */     this.gameProfile = gameProfileIn;
/*  200 */     this.inventoryContainer = (Container)new ContainerPlayer(this.inventory, !worldIn.isRemote, this);
/*  201 */     this.openContainer = this.inventoryContainer;
/*  202 */     BlockPos blockpos = worldIn.getSpawnPoint();
/*  203 */     setLocationAndAngles(blockpos.getX() + 0.5D, (blockpos.getY() + 1), blockpos.getZ() + 0.5D, 0.0F, 0.0F);
/*  204 */     this.unused180 = 180.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  209 */     super.applyEntityAttributes();
/*  210 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1.0D);
/*  211 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.10000000149011612D);
/*  212 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_SPEED);
/*  213 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.LUCK);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  218 */     super.entityInit();
/*  219 */     this.dataManager.register(ABSORPTION, Float.valueOf(0.0F));
/*  220 */     this.dataManager.register(PLAYER_SCORE, Integer.valueOf(0));
/*  221 */     this.dataManager.register(PLAYER_MODEL_FLAG, Byte.valueOf((byte)0));
/*  222 */     this.dataManager.register(MAIN_HAND, Byte.valueOf((byte)1));
/*  223 */     this.dataManager.register(field_192032_bt, new NBTTagCompound());
/*  224 */     this.dataManager.register(field_192033_bu, new NBTTagCompound());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  232 */     this.noClip = isSpectator();
/*      */     
/*  234 */     if (isSpectator())
/*      */     {
/*  236 */       this.onGround = false;
/*      */     }
/*      */     
/*  239 */     if (this.xpCooldown > 0)
/*      */     {
/*  241 */       this.xpCooldown--;
/*      */     }
/*      */     
/*  244 */     if (isPlayerSleeping()) {
/*      */       
/*  246 */       this.sleepTimer++;
/*      */       
/*  248 */       if (this.sleepTimer > 100)
/*      */       {
/*  250 */         this.sleepTimer = 100;
/*      */       }
/*      */       
/*  253 */       if (!this.world.isRemote)
/*      */       {
/*  255 */         if (!isInBed())
/*      */         {
/*  257 */           wakeUpPlayer(true, true, false);
/*      */         }
/*  259 */         else if (this.world.isDaytime())
/*      */         {
/*  261 */           wakeUpPlayer(false, true, true);
/*      */         }
/*      */       
/*      */       }
/*  265 */     } else if (this.sleepTimer > 0) {
/*      */       
/*  267 */       this.sleepTimer++;
/*      */       
/*  269 */       if (this.sleepTimer >= 110)
/*      */       {
/*  271 */         this.sleepTimer = 0;
/*      */       }
/*      */     } 
/*      */     
/*  275 */     super.onUpdate();
/*      */     
/*  277 */     if (!this.world.isRemote && this.openContainer != null && !this.openContainer.canInteractWith(this)) {
/*      */       
/*  279 */       closeScreen();
/*  280 */       this.openContainer = this.inventoryContainer;
/*      */     } 
/*      */     
/*  283 */     if (isBurning() && this.capabilities.disableDamage)
/*      */     {
/*  285 */       extinguish();
/*      */     }
/*      */     
/*  288 */     updateCape();
/*      */     
/*  290 */     if (!this.world.isRemote) {
/*      */       
/*  292 */       this.foodStats.onUpdate(this);
/*  293 */       addStat(StatList.PLAY_ONE_MINUTE);
/*      */       
/*  295 */       if (isEntityAlive())
/*      */       {
/*  297 */         addStat(StatList.TIME_SINCE_DEATH);
/*      */       }
/*      */       
/*  300 */       if (isSneaking())
/*      */       {
/*  302 */         addStat(StatList.SNEAK_TIME);
/*      */       }
/*      */     } 
/*      */     
/*  306 */     int i = 29999999;
/*  307 */     double d0 = MathHelper.clamp(this.posX, -2.9999999E7D, 2.9999999E7D);
/*  308 */     double d1 = MathHelper.clamp(this.posZ, -2.9999999E7D, 2.9999999E7D);
/*      */     
/*  310 */     if (d0 != this.posX || d1 != this.posZ)
/*      */     {
/*  312 */       setPosition(d0, this.posY, d1);
/*      */     }
/*      */     
/*  315 */     this.ticksSinceLastSwing++;
/*  316 */     ItemStack itemstack = getHeldItemMainhand();
/*      */     
/*  318 */     if (!ItemStack.areItemStacksEqual(this.itemStackMainHand, itemstack)) {
/*      */       
/*  320 */       if (!ItemStack.areItemsEqualIgnoreDurability(this.itemStackMainHand, itemstack))
/*      */       {
/*  322 */         resetCooldown();
/*      */       }
/*      */       
/*  325 */       this.itemStackMainHand = itemstack.func_190926_b() ? ItemStack.field_190927_a : itemstack.copy();
/*      */     } 
/*      */     
/*  328 */     this.cooldownTracker.tick();
/*  329 */     updateSize();
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateCape() {
/*  334 */     this.prevChasingPosX = this.chasingPosX;
/*  335 */     this.prevChasingPosY = this.chasingPosY;
/*  336 */     this.prevChasingPosZ = this.chasingPosZ;
/*  337 */     double d0 = this.posX - this.chasingPosX;
/*  338 */     double d1 = this.posY - this.chasingPosY;
/*  339 */     double d2 = this.posZ - this.chasingPosZ;
/*  340 */     double d3 = 10.0D;
/*      */     
/*  342 */     if (d0 > 10.0D) {
/*      */       
/*  344 */       this.chasingPosX = this.posX;
/*  345 */       this.prevChasingPosX = this.chasingPosX;
/*      */     } 
/*      */     
/*  348 */     if (d2 > 10.0D) {
/*      */       
/*  350 */       this.chasingPosZ = this.posZ;
/*  351 */       this.prevChasingPosZ = this.chasingPosZ;
/*      */     } 
/*      */     
/*  354 */     if (d1 > 10.0D) {
/*      */       
/*  356 */       this.chasingPosY = this.posY;
/*  357 */       this.prevChasingPosY = this.chasingPosY;
/*      */     } 
/*      */     
/*  360 */     if (d0 < -10.0D) {
/*      */       
/*  362 */       this.chasingPosX = this.posX;
/*  363 */       this.prevChasingPosX = this.chasingPosX;
/*      */     } 
/*      */     
/*  366 */     if (d2 < -10.0D) {
/*      */       
/*  368 */       this.chasingPosZ = this.posZ;
/*  369 */       this.prevChasingPosZ = this.chasingPosZ;
/*      */     } 
/*      */     
/*  372 */     if (d1 < -10.0D) {
/*      */       
/*  374 */       this.chasingPosY = this.posY;
/*  375 */       this.prevChasingPosY = this.chasingPosY;
/*      */     } 
/*      */     
/*  378 */     this.chasingPosX += d0 * 0.25D;
/*  379 */     this.chasingPosZ += d2 * 0.25D;
/*  380 */     this.chasingPosY += d1 * 0.25D;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateSize() {
/*      */     float f;
/*      */     float f1;
/*  388 */     if (isElytraFlying()) {
/*      */       
/*  390 */       f = 0.6F;
/*  391 */       f1 = 0.6F;
/*      */     }
/*  393 */     else if (isPlayerSleeping()) {
/*      */       
/*  395 */       f = 0.2F;
/*  396 */       f1 = 0.2F;
/*      */     }
/*  398 */     else if (isSneaking()) {
/*      */       
/*  400 */       f = 0.6F;
/*  401 */       f1 = 1.65F;
/*      */     }
/*      */     else {
/*      */       
/*  405 */       f = 0.6F;
/*  406 */       f1 = 1.8F;
/*      */     } 
/*      */     
/*  409 */     if (f != this.width || f1 != this.height) {
/*      */       
/*  411 */       AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/*  412 */       axisalignedbb = new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + f, axisalignedbb.minY + f1, axisalignedbb.minZ + f);
/*      */       
/*  414 */       if (!this.world.collidesWithAnyBlock(axisalignedbb))
/*      */       {
/*  416 */         setSize(f, f1);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxInPortalTime() {
/*  426 */     return this.capabilities.disableDamage ? 1 : 80;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getSwimSound() {
/*  431 */     return SoundEvents.ENTITY_PLAYER_SWIM;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getSplashSound() {
/*  436 */     return SoundEvents.ENTITY_PLAYER_SPLASH;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPortalCooldown() {
/*  444 */     return 10;
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSound(SoundEvent soundIn, float volume, float pitch) {
/*  449 */     this.world.playSound(this, this.posX, this.posY, this.posZ, soundIn, getSoundCategory(), volume, pitch);
/*      */   }
/*      */ 
/*      */   
/*      */   public SoundCategory getSoundCategory() {
/*  454 */     return SoundCategory.PLAYERS;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int func_190531_bD() {
/*  459 */     return 20;
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/*  464 */     if (id == 9) {
/*      */       
/*  466 */       onItemUseFinish();
/*      */     }
/*  468 */     else if (id == 23) {
/*      */       
/*  470 */       this.hasReducedDebug = false;
/*      */     }
/*  472 */     else if (id == 22) {
/*      */       
/*  474 */       this.hasReducedDebug = true;
/*      */     }
/*      */     else {
/*      */       
/*  478 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isMovementBlocked() {
/*  487 */     return !(getHealth() > 0.0F && !isPlayerSleeping());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void closeScreen() {
/*  495 */     this.openContainer = this.inventoryContainer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRidden() {
/*  503 */     if (!this.world.isRemote && isSneaking() && isRiding()) {
/*      */       
/*  505 */       dismountRidingEntity();
/*  506 */       setSneaking(false);
/*      */     }
/*      */     else {
/*      */       
/*  510 */       double d0 = this.posX;
/*  511 */       double d1 = this.posY;
/*  512 */       double d2 = this.posZ;
/*  513 */       float f = this.rotationYaw;
/*  514 */       float f1 = this.rotationPitch;
/*  515 */       super.updateRidden();
/*  516 */       this.prevCameraYaw = this.cameraYaw;
/*  517 */       this.cameraYaw = 0.0F;
/*  518 */       addMountedMovementStat(this.posX - d0, this.posY - d1, this.posZ - d2);
/*      */       
/*  520 */       if (getRidingEntity() instanceof EntityPig) {
/*      */         
/*  522 */         this.rotationPitch = f1;
/*  523 */         this.rotationYaw = f;
/*  524 */         this.renderYawOffset = ((EntityPig)getRidingEntity()).renderYawOffset;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void preparePlayerToSpawn() {
/*  535 */     setSize(0.6F, 1.8F);
/*  536 */     super.preparePlayerToSpawn();
/*  537 */     setHealth(getMaxHealth());
/*  538 */     this.deathTime = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateEntityActionState() {
/*  543 */     super.updateEntityActionState();
/*  544 */     updateArmSwingProgress();
/*  545 */     this.rotationYawHead = this.rotationYaw;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/*  554 */     if (this.flyToggleTimer > 0)
/*      */     {
/*  556 */       this.flyToggleTimer--;
/*      */     }
/*      */     
/*  559 */     if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL && this.world.getGameRules().getBoolean("naturalRegeneration")) {
/*      */       
/*  561 */       if (getHealth() < getMaxHealth() && this.ticksExisted % 20 == 0)
/*      */       {
/*  563 */         heal(1.0F);
/*      */       }
/*      */       
/*  566 */       if (this.foodStats.needFood() && this.ticksExisted % 10 == 0)
/*      */       {
/*  568 */         this.foodStats.setFoodLevel(this.foodStats.getFoodLevel() + 1);
/*      */       }
/*      */     } 
/*      */     
/*  572 */     this.inventory.decrementAnimations();
/*  573 */     this.prevCameraYaw = this.cameraYaw;
/*  574 */     super.onLivingUpdate();
/*  575 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
/*      */     
/*  577 */     if (!this.world.isRemote)
/*      */     {
/*  579 */       iattributeinstance.setBaseValue(this.capabilities.getWalkSpeed());
/*      */     }
/*      */     
/*  582 */     this.jumpMovementFactor = this.speedInAir;
/*      */     
/*  584 */     if (isSprinting())
/*      */     {
/*  586 */       this.jumpMovementFactor = (float)(this.jumpMovementFactor + this.speedInAir * 0.3D);
/*      */     }
/*      */     
/*  589 */     setAIMoveSpeed((float)iattributeinstance.getAttributeValue());
/*  590 */     float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  591 */     float f1 = (float)(Math.atan(-this.motionY * 0.20000000298023224D) * 15.0D);
/*      */     
/*  593 */     if (f > 0.1F)
/*      */     {
/*  595 */       f = 0.1F;
/*      */     }
/*      */     
/*  598 */     if (!this.onGround || getHealth() <= 0.0F)
/*      */     {
/*  600 */       f = 0.0F;
/*      */     }
/*      */     
/*  603 */     if (this.onGround || getHealth() <= 0.0F)
/*      */     {
/*  605 */       f1 = 0.0F;
/*      */     }
/*      */     
/*  608 */     this.cameraYaw += (f - this.cameraYaw) * 0.4F;
/*  609 */     this.cameraPitch += (f1 - this.cameraPitch) * 0.8F;
/*      */     
/*  611 */     if (getHealth() > 0.0F && !isSpectator()) {
/*      */       AxisAlignedBB axisalignedbb;
/*      */ 
/*      */       
/*  615 */       if (isRiding() && !(getRidingEntity()).isDead) {
/*      */         
/*  617 */         axisalignedbb = getEntityBoundingBox().union(getRidingEntity().getEntityBoundingBox()).expand(1.0D, 0.0D, 1.0D);
/*      */       }
/*      */       else {
/*      */         
/*  621 */         axisalignedbb = getEntityBoundingBox().expand(1.0D, 0.5D, 1.0D);
/*      */       } 
/*      */       
/*  624 */       List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity((Entity)this, axisalignedbb);
/*      */       
/*  626 */       for (int i = 0; i < list.size(); i++) {
/*      */         
/*  628 */         Entity entity = list.get(i);
/*      */         
/*  630 */         if (!entity.isDead)
/*      */         {
/*  632 */           collideWithPlayer(entity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  637 */     func_192028_j(func_192023_dk());
/*  638 */     func_192028_j(func_192025_dl());
/*      */     
/*  640 */     if ((!this.world.isRemote && (this.fallDistance > 0.5F || isInWater() || isRiding())) || this.capabilities.isFlying)
/*      */     {
/*  642 */       func_192030_dh();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_192028_j(@Nullable NBTTagCompound p_192028_1_) {
/*  648 */     if ((p_192028_1_ != null && !p_192028_1_.hasKey("Silent")) || !p_192028_1_.getBoolean("Silent")) {
/*      */       
/*  650 */       String s = p_192028_1_.getString("id");
/*      */       
/*  652 */       if (s.equals(EntityList.func_191306_a(EntityParrot.class).toString()))
/*      */       {
/*  654 */         EntityParrot.func_192005_a(this.world, (Entity)this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void collideWithPlayer(Entity entityIn) {
/*  661 */     entityIn.onCollideWithPlayer(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getScore() {
/*  666 */     return ((Integer)this.dataManager.get(PLAYER_SCORE)).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScore(int scoreIn) {
/*  674 */     this.dataManager.set(PLAYER_SCORE, Integer.valueOf(scoreIn));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addScore(int scoreIn) {
/*  682 */     int i = getScore();
/*  683 */     this.dataManager.set(PLAYER_SCORE, Integer.valueOf(i + scoreIn));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  691 */     super.onDeath(cause);
/*  692 */     setSize(0.2F, 0.2F);
/*  693 */     setPosition(this.posX, this.posY, this.posZ);
/*  694 */     this.motionY = 0.10000000149011612D;
/*      */     
/*  696 */     if ("Notch".equals(getName()))
/*      */     {
/*  698 */       dropItem(new ItemStack(Items.APPLE, 1), true, false);
/*      */     }
/*      */     
/*  701 */     if (!this.world.getGameRules().getBoolean("keepInventory") && !isSpectator()) {
/*      */       
/*  703 */       func_190776_cN();
/*  704 */       this.inventory.dropAllItems();
/*      */     } 
/*      */     
/*  707 */     if (cause != null) {
/*      */       
/*  709 */       this.motionX = (-MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 0.017453292F) * 0.1F);
/*  710 */       this.motionZ = (-MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 0.017453292F) * 0.1F);
/*      */     }
/*      */     else {
/*      */       
/*  714 */       this.motionX = 0.0D;
/*  715 */       this.motionZ = 0.0D;
/*      */     } 
/*      */     
/*  718 */     addStat(StatList.DEATHS);
/*  719 */     takeStat(StatList.TIME_SINCE_DEATH);
/*  720 */     extinguish();
/*  721 */     setFlag(0, false);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_190776_cN() {
/*  726 */     for (int i = 0; i < this.inventory.getSizeInventory(); i++) {
/*      */       
/*  728 */       ItemStack itemstack = this.inventory.getStackInSlot(i);
/*      */       
/*  730 */       if (!itemstack.func_190926_b() && EnchantmentHelper.func_190939_c(itemstack))
/*      */       {
/*  732 */         this.inventory.removeStackFromSlot(i);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  739 */     if (p_184601_1_ == DamageSource.onFire)
/*      */     {
/*  741 */       return SoundEvents.field_193806_fH;
/*      */     }
/*      */ 
/*      */     
/*  745 */     return (p_184601_1_ == DamageSource.drown) ? SoundEvents.field_193805_fG : SoundEvents.ENTITY_PLAYER_HURT;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected SoundEvent getDeathSound() {
/*  751 */     return SoundEvents.ENTITY_PLAYER_DEATH;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityItem dropItem(boolean dropAll) {
/*  762 */     return dropItem(this.inventory.decrStackSize(this.inventory.currentItem, (dropAll && !this.inventory.getCurrentItem().func_190926_b()) ? this.inventory.getCurrentItem().func_190916_E() : 1), false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityItem dropItem(ItemStack itemStackIn, boolean unused) {
/*  772 */     return dropItem(itemStackIn, false, unused);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityItem dropItem(ItemStack droppedItem, boolean dropAround, boolean traceItem) {
/*  778 */     if (droppedItem.func_190926_b())
/*      */     {
/*  780 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  784 */     double d0 = this.posY - 0.30000001192092896D + getEyeHeight();
/*  785 */     EntityItem entityitem = new EntityItem(this.world, this.posX, d0, this.posZ, droppedItem);
/*  786 */     entityitem.setPickupDelay(40);
/*      */     
/*  788 */     if (traceItem)
/*      */     {
/*  790 */       entityitem.setThrower(getName());
/*      */     }
/*      */     
/*  793 */     if (dropAround) {
/*      */       
/*  795 */       float f = this.rand.nextFloat() * 0.5F;
/*  796 */       float f1 = this.rand.nextFloat() * 6.2831855F;
/*  797 */       entityitem.motionX = (-MathHelper.sin(f1) * f);
/*  798 */       entityitem.motionZ = (MathHelper.cos(f1) * f);
/*  799 */       entityitem.motionY = 0.20000000298023224D;
/*      */     }
/*      */     else {
/*      */       
/*  803 */       float f2 = 0.3F;
/*  804 */       entityitem.motionX = (-MathHelper.sin(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F) * f2);
/*  805 */       entityitem.motionZ = (MathHelper.cos(this.rotationYaw * 0.017453292F) * MathHelper.cos(this.rotationPitch * 0.017453292F) * f2);
/*  806 */       entityitem.motionY = (-MathHelper.sin(this.rotationPitch * 0.017453292F) * f2 + 0.1F);
/*  807 */       float f3 = this.rand.nextFloat() * 6.2831855F;
/*  808 */       f2 = 0.02F * this.rand.nextFloat();
/*  809 */       entityitem.motionX += Math.cos(f3) * f2;
/*  810 */       entityitem.motionY += ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.1F);
/*  811 */       entityitem.motionZ += Math.sin(f3) * f2;
/*      */     } 
/*      */     
/*  814 */     ItemStack itemstack = dropItemAndGetStack(entityitem);
/*      */     
/*  816 */     if (traceItem) {
/*      */       
/*  818 */       if (!itemstack.func_190926_b())
/*      */       {
/*  820 */         addStat(StatList.getDroppedObjectStats(itemstack.getItem()), droppedItem.func_190916_E());
/*      */       }
/*      */       
/*  823 */       addStat(StatList.DROP);
/*      */     } 
/*      */     
/*  826 */     return entityitem;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected ItemStack dropItemAndGetStack(EntityItem p_184816_1_) {
/*  832 */     this.world.spawnEntityInWorld((Entity)p_184816_1_);
/*  833 */     return p_184816_1_.getEntityItem();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getDigSpeed(IBlockState state) {
/*  838 */     float f = this.inventory.getStrVsBlock(state);
/*      */     
/*  840 */     if (f > 1.0F) {
/*      */       
/*  842 */       int i = EnchantmentHelper.getEfficiencyModifier(this);
/*  843 */       ItemStack itemstack = getHeldItemMainhand();
/*      */       
/*  845 */       if (i > 0 && !itemstack.func_190926_b())
/*      */       {
/*  847 */         f += (i * i + 1);
/*      */       }
/*      */     } 
/*      */     
/*  851 */     if (isPotionActive(MobEffects.HASTE))
/*      */     {
/*  853 */       f *= 1.0F + (getActivePotionEffect(MobEffects.HASTE).getAmplifier() + 1) * 0.2F;
/*      */     }
/*      */     
/*  856 */     if (isPotionActive(MobEffects.MINING_FATIGUE)) {
/*      */       float f1;
/*      */ 
/*      */       
/*  860 */       switch (getActivePotionEffect(MobEffects.MINING_FATIGUE).getAmplifier()) {
/*      */         
/*      */         case 0:
/*  863 */           f1 = 0.3F;
/*      */           break;
/*      */         
/*      */         case 1:
/*  867 */           f1 = 0.09F;
/*      */           break;
/*      */         
/*      */         case 2:
/*  871 */           f1 = 0.0027F;
/*      */           break;
/*      */ 
/*      */         
/*      */         default:
/*  876 */           f1 = 8.1E-4F;
/*      */           break;
/*      */       } 
/*  879 */       f *= f1;
/*      */     } 
/*      */     
/*  882 */     if (isInsideOfMaterial(Material.WATER) && !EnchantmentHelper.getAquaAffinityModifier(this))
/*      */     {
/*  884 */       f /= 5.0F;
/*      */     }
/*      */     
/*  887 */     if (!this.onGround)
/*      */     {
/*  889 */       f /= 5.0F;
/*      */     }
/*      */     
/*  892 */     return f;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canHarvestBlock(IBlockState state) {
/*  897 */     return this.inventory.canHarvestBlock(state);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void registerFixesPlayer(DataFixer fixer) {
/*  902 */     fixer.registerWalker(FixTypes.PLAYER, new IDataWalker()
/*      */         {
/*      */           public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn)
/*      */           {
/*  906 */             DataFixesManager.processInventory(fixer, compound, versionIn, "Inventory");
/*  907 */             DataFixesManager.processInventory(fixer, compound, versionIn, "EnderItems");
/*      */             
/*  909 */             if (compound.hasKey("ShoulderEntityLeft", 10))
/*      */             {
/*  911 */               compound.setTag("ShoulderEntityLeft", (NBTBase)fixer.process((IFixType)FixTypes.ENTITY, compound.getCompoundTag("ShoulderEntityLeft"), versionIn));
/*      */             }
/*      */             
/*  914 */             if (compound.hasKey("ShoulderEntityRight", 10))
/*      */             {
/*  916 */               compound.setTag("ShoulderEntityRight", (NBTBase)fixer.process((IFixType)FixTypes.ENTITY, compound.getCompoundTag("ShoulderEntityRight"), versionIn));
/*      */             }
/*      */             
/*  919 */             return compound;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound compound) {
/*  929 */     super.readEntityFromNBT(compound);
/*  930 */     setUniqueId(getUUID(this.gameProfile));
/*  931 */     NBTTagList nbttaglist = compound.getTagList("Inventory", 10);
/*  932 */     this.inventory.readFromNBT(nbttaglist);
/*  933 */     this.inventory.currentItem = compound.getInteger("SelectedItemSlot");
/*  934 */     this.sleeping = compound.getBoolean("Sleeping");
/*  935 */     this.sleepTimer = compound.getShort("SleepTimer");
/*  936 */     this.experience = compound.getFloat("XpP");
/*  937 */     this.experienceLevel = compound.getInteger("XpLevel");
/*  938 */     this.experienceTotal = compound.getInteger("XpTotal");
/*  939 */     this.xpSeed = compound.getInteger("XpSeed");
/*      */     
/*  941 */     if (this.xpSeed == 0)
/*      */     {
/*  943 */       this.xpSeed = this.rand.nextInt();
/*      */     }
/*      */     
/*  946 */     setScore(compound.getInteger("Score"));
/*      */     
/*  948 */     if (this.sleeping) {
/*      */       
/*  950 */       this.bedLocation = new BlockPos((Entity)this);
/*  951 */       wakeUpPlayer(true, true, false);
/*      */     } 
/*      */     
/*  954 */     if (compound.hasKey("SpawnX", 99) && compound.hasKey("SpawnY", 99) && compound.hasKey("SpawnZ", 99)) {
/*      */       
/*  956 */       this.spawnChunk = new BlockPos(compound.getInteger("SpawnX"), compound.getInteger("SpawnY"), compound.getInteger("SpawnZ"));
/*  957 */       this.spawnForced = compound.getBoolean("SpawnForced");
/*      */     } 
/*      */     
/*  960 */     this.foodStats.readNBT(compound);
/*  961 */     this.capabilities.readCapabilitiesFromNBT(compound);
/*      */     
/*  963 */     if (compound.hasKey("EnderItems", 9)) {
/*      */       
/*  965 */       NBTTagList nbttaglist1 = compound.getTagList("EnderItems", 10);
/*  966 */       this.theInventoryEnderChest.loadInventoryFromNBT(nbttaglist1);
/*      */     } 
/*      */     
/*  969 */     if (compound.hasKey("ShoulderEntityLeft", 10))
/*      */     {
/*  971 */       func_192029_h(compound.getCompoundTag("ShoulderEntityLeft"));
/*      */     }
/*      */     
/*  974 */     if (compound.hasKey("ShoulderEntityRight", 10))
/*      */     {
/*  976 */       func_192031_i(compound.getCompoundTag("ShoulderEntityRight"));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  985 */     super.writeEntityToNBT(compound);
/*  986 */     compound.setInteger("DataVersion", 1343);
/*  987 */     compound.setTag("Inventory", (NBTBase)this.inventory.writeToNBT(new NBTTagList()));
/*  988 */     compound.setInteger("SelectedItemSlot", this.inventory.currentItem);
/*  989 */     compound.setBoolean("Sleeping", this.sleeping);
/*  990 */     compound.setShort("SleepTimer", (short)this.sleepTimer);
/*  991 */     compound.setFloat("XpP", this.experience);
/*  992 */     compound.setInteger("XpLevel", this.experienceLevel);
/*  993 */     compound.setInteger("XpTotal", this.experienceTotal);
/*  994 */     compound.setInteger("XpSeed", this.xpSeed);
/*  995 */     compound.setInteger("Score", getScore());
/*      */     
/*  997 */     if (this.spawnChunk != null) {
/*      */       
/*  999 */       compound.setInteger("SpawnX", this.spawnChunk.getX());
/* 1000 */       compound.setInteger("SpawnY", this.spawnChunk.getY());
/* 1001 */       compound.setInteger("SpawnZ", this.spawnChunk.getZ());
/* 1002 */       compound.setBoolean("SpawnForced", this.spawnForced);
/*      */     } 
/*      */     
/* 1005 */     this.foodStats.writeNBT(compound);
/* 1006 */     this.capabilities.writeCapabilitiesToNBT(compound);
/* 1007 */     compound.setTag("EnderItems", (NBTBase)this.theInventoryEnderChest.saveInventoryToNBT());
/*      */     
/* 1009 */     if (!func_192023_dk().hasNoTags())
/*      */     {
/* 1011 */       compound.setTag("ShoulderEntityLeft", (NBTBase)func_192023_dk());
/*      */     }
/*      */     
/* 1014 */     if (!func_192025_dl().hasNoTags())
/*      */     {
/* 1016 */       compound.setTag("ShoulderEntityRight", (NBTBase)func_192025_dl());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 1025 */     if (isEntityInvulnerable(source))
/*      */     {
/* 1027 */       return false;
/*      */     }
/* 1029 */     if (this.capabilities.disableDamage && !source.canHarmInCreative())
/*      */     {
/* 1031 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1035 */     this.entityAge = 0;
/*      */     
/* 1037 */     if (getHealth() <= 0.0F)
/*      */     {
/* 1039 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1043 */     if (isPlayerSleeping() && !this.world.isRemote)
/*      */     {
/* 1045 */       wakeUpPlayer(true, true, false);
/*      */     }
/*      */     
/* 1048 */     func_192030_dh();
/*      */     
/* 1050 */     if (source.isDifficultyScaled()) {
/*      */       
/* 1052 */       if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
/*      */       {
/* 1054 */         amount = 0.0F;
/*      */       }
/*      */       
/* 1057 */       if (this.world.getDifficulty() == EnumDifficulty.EASY)
/*      */       {
/* 1059 */         amount = Math.min(amount / 2.0F + 1.0F, amount);
/*      */       }
/*      */       
/* 1062 */       if (this.world.getDifficulty() == EnumDifficulty.HARD)
/*      */       {
/* 1064 */         amount = amount * 3.0F / 2.0F;
/*      */       }
/*      */     } 
/*      */     
/* 1068 */     return (amount == 0.0F) ? false : super.attackEntityFrom(source, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_190629_c(EntityLivingBase p_190629_1_) {
/* 1075 */     super.func_190629_c(p_190629_1_);
/*      */     
/* 1077 */     if (p_190629_1_.getHeldItemMainhand().getItem() instanceof net.minecraft.item.ItemAxe)
/*      */     {
/* 1079 */       func_190777_m(true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canAttackPlayer(EntityPlayer other) {
/* 1085 */     Team team = getTeam();
/* 1086 */     Team team1 = other.getTeam();
/*      */     
/* 1088 */     if (team == null)
/*      */     {
/* 1090 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1094 */     return !team.isSameTeam(team1) ? true : team.getAllowFriendlyFire();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageArmor(float damage) {
/* 1100 */     this.inventory.damageArmor(damage);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void damageShield(float damage) {
/* 1105 */     if (damage >= 3.0F && this.activeItemStack.getItem() == Items.SHIELD) {
/*      */       
/* 1107 */       int i = 1 + MathHelper.floor(damage);
/* 1108 */       this.activeItemStack.damageItem(i, this);
/*      */       
/* 1110 */       if (this.activeItemStack.func_190926_b()) {
/*      */         
/* 1112 */         EnumHand enumhand = getActiveHand();
/*      */         
/* 1114 */         if (enumhand == EnumHand.MAIN_HAND) {
/*      */           
/* 1116 */           setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.field_190927_a);
/*      */         }
/*      */         else {
/*      */           
/* 1120 */           setItemStackToSlot(EntityEquipmentSlot.OFFHAND, ItemStack.field_190927_a);
/*      */         } 
/*      */         
/* 1123 */         this.activeItemStack = ItemStack.field_190927_a;
/* 1124 */         playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getArmorVisibility() {
/* 1135 */     int i = 0;
/*      */     
/* 1137 */     for (ItemStack itemstack : this.inventory.armorInventory) {
/*      */       
/* 1139 */       if (!itemstack.func_190926_b())
/*      */       {
/* 1141 */         i++;
/*      */       }
/*      */     } 
/*      */     
/* 1145 */     return i / this.inventory.armorInventory.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageEntity(DamageSource damageSrc, float damageAmount) {
/* 1154 */     if (!isEntityInvulnerable(damageSrc)) {
/*      */       
/* 1156 */       damageAmount = applyArmorCalculations(damageSrc, damageAmount);
/* 1157 */       damageAmount = applyPotionDamageCalculations(damageSrc, damageAmount);
/* 1158 */       float f = damageAmount;
/* 1159 */       damageAmount = Math.max(damageAmount - getAbsorptionAmount(), 0.0F);
/* 1160 */       setAbsorptionAmount(getAbsorptionAmount() - f - damageAmount);
/*      */       
/* 1162 */       if (damageAmount != 0.0F) {
/*      */         
/* 1164 */         addExhaustion(damageSrc.getHungerDamage());
/* 1165 */         float f1 = getHealth();
/* 1166 */         setHealth(getHealth() - damageAmount);
/* 1167 */         getCombatTracker().trackDamage(damageSrc, f1, damageAmount);
/*      */         
/* 1169 */         if (damageAmount < 3.4028235E37F)
/*      */         {
/* 1171 */           addStat(StatList.DAMAGE_TAKEN, Math.round(damageAmount * 10.0F));
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void openEditSign(TileEntitySign signTile) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGuiEditCommandCart(CommandBlockBaseLogic commandBlock) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGuiCommandBlock(TileEntityCommandBlock commandBlock) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void openEditStructure(TileEntityStructure structure) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayVillagerTradeGui(IMerchant villager) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGUIChest(IInventory chestInventory) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void openGuiHorseInventory(AbstractHorse horse, IInventory inventoryIn) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGui(IInteractionObject guiOwner) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void openBook(ItemStack stack, EnumHand hand) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumActionResult func_190775_a(Entity p_190775_1_, EnumHand p_190775_2_) {
/* 1218 */     if (isSpectator()) {
/*      */       
/* 1220 */       if (p_190775_1_ instanceof IInventory)
/*      */       {
/* 1222 */         displayGUIChest((IInventory)p_190775_1_);
/*      */       }
/*      */       
/* 1225 */       return EnumActionResult.PASS;
/*      */     } 
/*      */ 
/*      */     
/* 1229 */     ItemStack itemstack = getHeldItem(p_190775_2_);
/* 1230 */     ItemStack itemstack1 = itemstack.func_190926_b() ? ItemStack.field_190927_a : itemstack.copy();
/*      */     
/* 1232 */     if (p_190775_1_.processInitialInteract(this, p_190775_2_)) {
/*      */       
/* 1234 */       if (this.capabilities.isCreativeMode && itemstack == getHeldItem(p_190775_2_) && itemstack.func_190916_E() < itemstack1.func_190916_E())
/*      */       {
/* 1236 */         itemstack.func_190920_e(itemstack1.func_190916_E());
/*      */       }
/*      */       
/* 1239 */       return EnumActionResult.SUCCESS;
/*      */     } 
/*      */ 
/*      */     
/* 1243 */     if (!itemstack.func_190926_b() && p_190775_1_ instanceof EntityLivingBase) {
/*      */       
/* 1245 */       if (this.capabilities.isCreativeMode)
/*      */       {
/* 1247 */         itemstack = itemstack1;
/*      */       }
/*      */       
/* 1250 */       if (itemstack.interactWithEntity(this, (EntityLivingBase)p_190775_1_, p_190775_2_)) {
/*      */         
/* 1252 */         if (itemstack.func_190926_b() && !this.capabilities.isCreativeMode)
/*      */         {
/* 1254 */           setHeldItem(p_190775_2_, ItemStack.field_190927_a);
/*      */         }
/*      */         
/* 1257 */         return EnumActionResult.SUCCESS;
/*      */       } 
/*      */     } 
/*      */     
/* 1261 */     return EnumActionResult.PASS;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getYOffset() {
/* 1271 */     return -0.35D;
/*      */   }
/*      */ 
/*      */   
/*      */   public void dismountRidingEntity() {
/* 1276 */     super.dismountRidingEntity();
/* 1277 */     this.rideCooldown = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void attackTargetEntityWithCurrentItem(Entity targetEntity) {
/* 1286 */     if (targetEntity.canBeAttackedWithItem())
/*      */     {
/* 1288 */       if (!targetEntity.hitByEntity((Entity)this)) {
/*      */         
/* 1290 */         float f1, f = (float)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
/*      */ 
/*      */         
/* 1293 */         if (targetEntity instanceof EntityLivingBase) {
/*      */           
/* 1295 */           f1 = EnchantmentHelper.getModifierForCreature(getHeldItemMainhand(), ((EntityLivingBase)targetEntity).getCreatureAttribute());
/*      */         }
/*      */         else {
/*      */           
/* 1299 */           f1 = EnchantmentHelper.getModifierForCreature(getHeldItemMainhand(), EnumCreatureAttribute.UNDEFINED);
/*      */         } 
/*      */         
/* 1302 */         float f2 = getCooledAttackStrength(0.5F);
/* 1303 */         f *= 0.2F + f2 * f2 * 0.8F;
/* 1304 */         f1 *= f2;
/* 1305 */         resetCooldown();
/*      */         
/* 1307 */         if (f > 0.0F || f1 > 0.0F) {
/*      */           
/* 1309 */           boolean flag = (f2 > 0.9F);
/* 1310 */           boolean flag1 = false;
/* 1311 */           int i = 0;
/* 1312 */           i += EnchantmentHelper.getKnockbackModifier(this);
/*      */           
/* 1314 */           if (isSprinting() && flag) {
/*      */             
/* 1316 */             this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_KNOCKBACK, getSoundCategory(), 1.0F, 1.0F);
/* 1317 */             i++;
/* 1318 */             flag1 = true;
/*      */           } 
/*      */           
/* 1321 */           boolean flag2 = (flag && this.fallDistance > 0.0F && !this.onGround && !isOnLadder() && !isInWater() && !isPotionActive(MobEffects.BLINDNESS) && !isRiding() && targetEntity instanceof EntityLivingBase);
/* 1322 */           flag2 = (flag2 && !isSprinting());
/*      */           
/* 1324 */           if (flag2)
/*      */           {
/* 1326 */             f *= 1.5F;
/*      */           }
/*      */           
/* 1329 */           f += f1;
/* 1330 */           boolean flag3 = false;
/* 1331 */           double d0 = (this.distanceWalkedModified - this.prevDistanceWalkedModified);
/*      */           
/* 1333 */           if (flag && !flag2 && !flag1 && this.onGround && d0 < getAIMoveSpeed()) {
/*      */             
/* 1335 */             ItemStack itemstack = getHeldItem(EnumHand.MAIN_HAND);
/*      */             
/* 1337 */             if (itemstack.getItem() instanceof net.minecraft.item.ItemSword)
/*      */             {
/* 1339 */               flag3 = true;
/*      */             }
/*      */           } 
/*      */           
/* 1343 */           float f4 = 0.0F;
/* 1344 */           boolean flag4 = false;
/* 1345 */           int j = EnchantmentHelper.getFireAspectModifier(this);
/*      */           
/* 1347 */           if (targetEntity instanceof EntityLivingBase) {
/*      */             
/* 1349 */             f4 = ((EntityLivingBase)targetEntity).getHealth();
/*      */             
/* 1351 */             if (j > 0 && !targetEntity.isBurning()) {
/*      */               
/* 1353 */               flag4 = true;
/* 1354 */               targetEntity.setFire(1);
/*      */             } 
/*      */           } 
/*      */           
/* 1358 */           double d1 = targetEntity.motionX;
/* 1359 */           double d2 = targetEntity.motionY;
/* 1360 */           double d3 = targetEntity.motionZ;
/* 1361 */           boolean flag5 = targetEntity.attackEntityFrom(DamageSource.causePlayerDamage(this), f);
/*      */           
/* 1363 */           if (flag5) {
/*      */             EntityLivingBase entityLivingBase;
/* 1365 */             if (i > 0) {
/*      */               
/* 1367 */               if (targetEntity instanceof EntityLivingBase) {
/*      */                 
/* 1369 */                 ((EntityLivingBase)targetEntity).knockBack((Entity)this, i * 0.5F, MathHelper.sin(this.rotationYaw * 0.017453292F), -MathHelper.cos(this.rotationYaw * 0.017453292F));
/*      */               }
/*      */               else {
/*      */                 
/* 1373 */                 targetEntity.addVelocity((-MathHelper.sin(this.rotationYaw * 0.017453292F) * i * 0.5F), 0.1D, (MathHelper.cos(this.rotationYaw * 0.017453292F) * i * 0.5F));
/*      */               } 
/*      */               
/* 1376 */               this.motionX *= 0.6D;
/* 1377 */               this.motionZ *= 0.6D;
/* 1378 */               setSprinting(false);
/*      */             } 
/*      */             
/* 1381 */             if (flag3) {
/*      */               
/* 1383 */               float f3 = 1.0F + EnchantmentHelper.func_191527_a(this) * f;
/*      */               
/* 1385 */               for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, targetEntity.getEntityBoundingBox().expand(1.0D, 0.25D, 1.0D))) {
/*      */                 
/* 1387 */                 if (entityLivingBase != this && entityLivingBase != targetEntity && !isOnSameTeam((Entity)entityLivingBase) && getDistanceSqToEntity((Entity)entityLivingBase) < 9.0D) {
/*      */                   
/* 1389 */                   entityLivingBase.knockBack((Entity)this, 0.4F, MathHelper.sin(this.rotationYaw * 0.017453292F), -MathHelper.cos(this.rotationYaw * 0.017453292F));
/* 1390 */                   entityLivingBase.attackEntityFrom(DamageSource.causePlayerDamage(this), f3);
/*      */                 } 
/*      */               } 
/*      */               
/* 1394 */               this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_SWEEP, getSoundCategory(), 1.0F, 1.0F);
/* 1395 */               spawnSweepParticles();
/*      */             } 
/*      */             
/* 1398 */             if (targetEntity instanceof EntityPlayerMP && targetEntity.velocityChanged) {
/*      */               
/* 1400 */               ((EntityPlayerMP)targetEntity).connection.sendPacket((Packet)new SPacketEntityVelocity(targetEntity));
/* 1401 */               targetEntity.velocityChanged = false;
/* 1402 */               targetEntity.motionX = d1;
/* 1403 */               targetEntity.motionY = d2;
/* 1404 */               targetEntity.motionZ = d3;
/*      */             } 
/*      */             
/* 1407 */             if (flag2) {
/*      */               
/* 1409 */               this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, getSoundCategory(), 1.0F, 1.0F);
/* 1410 */               onCriticalHit(targetEntity);
/*      */             } 
/*      */             
/* 1413 */             if (!flag2 && !flag3)
/*      */             {
/* 1415 */               if (flag) {
/*      */                 
/* 1417 */                 this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, getSoundCategory(), 1.0F, 1.0F);
/*      */               }
/*      */               else {
/*      */                 
/* 1421 */                 this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, getSoundCategory(), 1.0F, 1.0F);
/*      */               } 
/*      */             }
/*      */             
/* 1425 */             if (f1 > 0.0F)
/*      */             {
/* 1427 */               onEnchantmentCritical(targetEntity);
/*      */             }
/*      */             
/* 1430 */             setLastAttacker(targetEntity);
/*      */             
/* 1432 */             if (targetEntity instanceof EntityLivingBase)
/*      */             {
/* 1434 */               EnchantmentHelper.applyThornEnchantments((EntityLivingBase)targetEntity, (Entity)this);
/*      */             }
/*      */             
/* 1437 */             EnchantmentHelper.applyArthropodEnchantments(this, targetEntity);
/* 1438 */             ItemStack itemstack1 = getHeldItemMainhand();
/* 1439 */             Entity entity = targetEntity;
/*      */             
/* 1441 */             if (targetEntity instanceof MultiPartEntityPart) {
/*      */               
/* 1443 */               IEntityMultiPart ientitymultipart = ((MultiPartEntityPart)targetEntity).entityDragonObj;
/*      */               
/* 1445 */               if (ientitymultipart instanceof EntityLivingBase)
/*      */               {
/* 1447 */                 entityLivingBase = (EntityLivingBase)ientitymultipart;
/*      */               }
/*      */             } 
/*      */             
/* 1451 */             if (!itemstack1.func_190926_b() && entityLivingBase instanceof EntityLivingBase) {
/*      */               
/* 1453 */               itemstack1.hitEntity(entityLivingBase, this);
/*      */               
/* 1455 */               if (itemstack1.func_190926_b())
/*      */               {
/* 1457 */                 setHeldItem(EnumHand.MAIN_HAND, ItemStack.field_190927_a);
/*      */               }
/*      */             } 
/*      */             
/* 1461 */             if (targetEntity instanceof EntityLivingBase) {
/*      */               
/* 1463 */               float f5 = f4 - ((EntityLivingBase)targetEntity).getHealth();
/* 1464 */               addStat(StatList.DAMAGE_DEALT, Math.round(f5 * 10.0F));
/*      */               
/* 1466 */               if (j > 0)
/*      */               {
/* 1468 */                 targetEntity.setFire(j * 4);
/*      */               }
/*      */               
/* 1471 */               if (this.world instanceof WorldServer && f5 > 2.0F) {
/*      */                 
/* 1473 */                 int k = (int)(f5 * 0.5D);
/* 1474 */                 ((WorldServer)this.world).spawnParticle(EnumParticleTypes.DAMAGE_INDICATOR, targetEntity.posX, targetEntity.posY + (targetEntity.height * 0.5F), targetEntity.posZ, k, 0.1D, 0.0D, 0.1D, 0.2D, new int[0]);
/*      */               } 
/*      */             } 
/*      */             
/* 1478 */             addExhaustion(0.1F);
/*      */           }
/*      */           else {
/*      */             
/* 1482 */             this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_ATTACK_NODAMAGE, getSoundCategory(), 1.0F, 1.0F);
/*      */             
/* 1484 */             if (flag4)
/*      */             {
/* 1486 */               targetEntity.extinguish();
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_190777_m(boolean p_190777_1_) {
/* 1496 */     float f = 0.25F + EnchantmentHelper.getEfficiencyModifier(this) * 0.05F;
/*      */     
/* 1498 */     if (p_190777_1_)
/*      */     {
/* 1500 */       f += 0.75F;
/*      */     }
/*      */     
/* 1503 */     if (this.rand.nextFloat() < f) {
/*      */       
/* 1505 */       getCooldownTracker().setCooldown(Items.SHIELD, 100);
/* 1506 */       resetActiveHand();
/* 1507 */       this.world.setEntityState((Entity)this, (byte)30);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCriticalHit(Entity entityHit) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEnchantmentCritical(Entity entityHit) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnSweepParticles() {
/* 1524 */     double d0 = -MathHelper.sin(this.rotationYaw * 0.017453292F);
/* 1525 */     double d1 = MathHelper.cos(this.rotationYaw * 0.017453292F);
/*      */     
/* 1527 */     if (this.world instanceof WorldServer)
/*      */     {
/* 1529 */       ((WorldServer)this.world).spawnParticle(EnumParticleTypes.SWEEP_ATTACK, this.posX + d0, this.posY + this.height * 0.5D, this.posZ + d1, 0, d0, 0.0D, d1, 0.0D, new int[0]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void respawnPlayer() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDead() {
/* 1542 */     super.setDead();
/* 1543 */     this.inventoryContainer.onContainerClosed(this);
/*      */     
/* 1545 */     if (this.openContainer != null)
/*      */     {
/* 1547 */       this.openContainer.onContainerClosed(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityInsideOpaqueBlock() {
/* 1556 */     return (!this.sleeping && super.isEntityInsideOpaqueBlock());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUser() {
/* 1564 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameProfile getGameProfile() {
/* 1572 */     return this.gameProfile;
/*      */   }
/*      */ 
/*      */   
/*      */   public SleepResult trySleep(BlockPos bedLocation) {
/* 1577 */     EnumFacing enumfacing = (EnumFacing)this.world.getBlockState(bedLocation).getValue((IProperty)BlockHorizontal.FACING);
/*      */     
/* 1579 */     if (!this.world.isRemote) {
/*      */       
/* 1581 */       if (isPlayerSleeping() || !isEntityAlive())
/*      */       {
/* 1583 */         return SleepResult.OTHER_PROBLEM;
/*      */       }
/*      */       
/* 1586 */       if (!this.world.provider.isSurfaceWorld())
/*      */       {
/* 1588 */         return SleepResult.NOT_POSSIBLE_HERE;
/*      */       }
/*      */       
/* 1591 */       if (this.world.isDaytime())
/*      */       {
/* 1593 */         return SleepResult.NOT_POSSIBLE_NOW;
/*      */       }
/*      */       
/* 1596 */       if (!func_190774_a(bedLocation, enumfacing))
/*      */       {
/* 1598 */         return SleepResult.TOO_FAR_AWAY;
/*      */       }
/*      */       
/* 1601 */       double d0 = 8.0D;
/* 1602 */       double d1 = 5.0D;
/* 1603 */       List<EntityMob> list = this.world.getEntitiesWithinAABB(EntityMob.class, new AxisAlignedBB(bedLocation.getX() - 8.0D, bedLocation.getY() - 5.0D, bedLocation.getZ() - 8.0D, bedLocation.getX() + 8.0D, bedLocation.getY() + 5.0D, bedLocation.getZ() + 8.0D), new SleepEnemyPredicate(this, null));
/*      */       
/* 1605 */       if (!list.isEmpty())
/*      */       {
/* 1607 */         return SleepResult.NOT_SAFE;
/*      */       }
/*      */     } 
/*      */     
/* 1611 */     if (isRiding())
/*      */     {
/* 1613 */       dismountRidingEntity();
/*      */     }
/*      */     
/* 1616 */     func_192030_dh();
/* 1617 */     setSize(0.2F, 0.2F);
/*      */     
/* 1619 */     if (this.world.isBlockLoaded(bedLocation)) {
/*      */       
/* 1621 */       float f1 = 0.5F + enumfacing.getFrontOffsetX() * 0.4F;
/* 1622 */       float f = 0.5F + enumfacing.getFrontOffsetZ() * 0.4F;
/* 1623 */       setRenderOffsetForSleep(enumfacing);
/* 1624 */       setPosition((bedLocation.getX() + f1), (bedLocation.getY() + 0.6875F), (bedLocation.getZ() + f));
/*      */     }
/*      */     else {
/*      */       
/* 1628 */       setPosition((bedLocation.getX() + 0.5F), (bedLocation.getY() + 0.6875F), (bedLocation.getZ() + 0.5F));
/*      */     } 
/*      */     
/* 1631 */     this.sleeping = true;
/* 1632 */     this.sleepTimer = 0;
/* 1633 */     this.bedLocation = bedLocation;
/* 1634 */     this.motionX = 0.0D;
/* 1635 */     this.motionY = 0.0D;
/* 1636 */     this.motionZ = 0.0D;
/*      */     
/* 1638 */     if (!this.world.isRemote)
/*      */     {
/* 1640 */       this.world.updateAllPlayersSleepingFlag();
/*      */     }
/*      */     
/* 1643 */     return SleepResult.OK;
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean func_190774_a(BlockPos p_190774_1_, EnumFacing p_190774_2_) {
/* 1648 */     if (Math.abs(this.posX - p_190774_1_.getX()) <= 3.0D && Math.abs(this.posY - p_190774_1_.getY()) <= 2.0D && Math.abs(this.posZ - p_190774_1_.getZ()) <= 3.0D)
/*      */     {
/* 1650 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1654 */     BlockPos blockpos = p_190774_1_.offset(p_190774_2_.getOpposite());
/* 1655 */     return (Math.abs(this.posX - blockpos.getX()) <= 3.0D && Math.abs(this.posY - blockpos.getY()) <= 2.0D && Math.abs(this.posZ - blockpos.getZ()) <= 3.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setRenderOffsetForSleep(EnumFacing p_175139_1_) {
/* 1661 */     this.renderOffsetX = -1.8F * p_175139_1_.getFrontOffsetX();
/* 1662 */     this.renderOffsetZ = -1.8F * p_175139_1_.getFrontOffsetZ();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void wakeUpPlayer(boolean immediately, boolean updateWorldFlag, boolean setSpawn) {
/* 1670 */     setSize(0.6F, 1.8F);
/* 1671 */     IBlockState iblockstate = this.world.getBlockState(this.bedLocation);
/*      */     
/* 1673 */     if (this.bedLocation != null && iblockstate.getBlock() == Blocks.BED) {
/*      */       
/* 1675 */       this.world.setBlockState(this.bedLocation, iblockstate.withProperty((IProperty)BlockBed.OCCUPIED, Boolean.valueOf(false)), 4);
/* 1676 */       BlockPos blockpos = BlockBed.getSafeExitLocation(this.world, this.bedLocation, 0);
/*      */       
/* 1678 */       if (blockpos == null)
/*      */       {
/* 1680 */         blockpos = this.bedLocation.up();
/*      */       }
/*      */       
/* 1683 */       setPosition((blockpos.getX() + 0.5F), (blockpos.getY() + 0.1F), (blockpos.getZ() + 0.5F));
/*      */     } 
/*      */     
/* 1686 */     this.sleeping = false;
/*      */     
/* 1688 */     if (!this.world.isRemote && updateWorldFlag)
/*      */     {
/* 1690 */       this.world.updateAllPlayersSleepingFlag();
/*      */     }
/*      */     
/* 1693 */     this.sleepTimer = immediately ? 0 : 100;
/*      */     
/* 1695 */     if (setSpawn)
/*      */     {
/* 1697 */       setSpawnPoint(this.bedLocation, false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isInBed() {
/* 1703 */     return (this.world.getBlockState(this.bedLocation).getBlock() == Blocks.BED);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public static BlockPos getBedSpawnLocation(World worldIn, BlockPos bedLocation, boolean forceSpawn) {
/* 1713 */     Block block = worldIn.getBlockState(bedLocation).getBlock();
/*      */     
/* 1715 */     if (block != Blocks.BED) {
/*      */       
/* 1717 */       if (!forceSpawn)
/*      */       {
/* 1719 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 1723 */       boolean flag = block.canSpawnInBlock();
/* 1724 */       boolean flag1 = worldIn.getBlockState(bedLocation.up()).getBlock().canSpawnInBlock();
/* 1725 */       return (flag && flag1) ? bedLocation : null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1730 */     return BlockBed.getSafeExitLocation(worldIn, bedLocation, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBedOrientationInDegrees() {
/* 1739 */     if (this.bedLocation != null) {
/*      */       
/* 1741 */       EnumFacing enumfacing = (EnumFacing)this.world.getBlockState(this.bedLocation).getValue((IProperty)BlockHorizontal.FACING);
/*      */       
/* 1743 */       switch (enumfacing) {
/*      */         
/*      */         case SOUTH:
/* 1746 */           return 90.0F;
/*      */         
/*      */         case WEST:
/* 1749 */           return 0.0F;
/*      */         
/*      */         case NORTH:
/* 1752 */           return 270.0F;
/*      */         
/*      */         case EAST:
/* 1755 */           return 180.0F;
/*      */       } 
/*      */     
/*      */     } 
/* 1759 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPlayerSleeping() {
/* 1767 */     return this.sleeping;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPlayerFullyAsleep() {
/* 1775 */     return (this.sleeping && this.sleepTimer >= 100);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSleepTimer() {
/* 1780 */     return this.sleepTimer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatComponentMessage(ITextComponent chatComponent, boolean p_146105_2_) {}
/*      */ 
/*      */   
/*      */   public BlockPos getBedLocation() {
/* 1789 */     return this.spawnChunk;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSpawnForced() {
/* 1794 */     return this.spawnForced;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSpawnPoint(BlockPos pos, boolean forced) {
/* 1799 */     if (pos != null) {
/*      */       
/* 1801 */       this.spawnChunk = pos;
/* 1802 */       this.spawnForced = forced;
/*      */     }
/*      */     else {
/*      */       
/* 1806 */       this.spawnChunk = null;
/* 1807 */       this.spawnForced = false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addStat(StatBase stat) {
/* 1816 */     addStat(stat, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addStat(StatBase stat, int amount) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void takeStat(StatBase stat) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_192021_a(List<IRecipe> p_192021_1_) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_193102_a(ResourceLocation[] p_193102_1_) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_192022_b(List<IRecipe> p_192022_1_) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void jump() {
/* 1847 */     super.jump();
/* 1848 */     addStat(StatList.JUMP);
/*      */     
/* 1850 */     if (isSprinting()) {
/*      */       
/* 1852 */       addExhaustion(0.2F);
/*      */     }
/*      */     else {
/*      */       
/* 1856 */       addExhaustion(0.05F);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
/* 1862 */     double d0 = this.posX;
/* 1863 */     double d1 = this.posY;
/* 1864 */     double d2 = this.posZ;
/*      */     
/* 1866 */     if (this.capabilities.isFlying && !isRiding()) {
/*      */       
/* 1868 */       double d3 = this.motionY;
/* 1869 */       float f = this.jumpMovementFactor;
/* 1870 */       this.jumpMovementFactor = this.capabilities.getFlySpeed() * (isSprinting() ? 2 : true);
/* 1871 */       super.func_191986_a(p_191986_1_, p_191986_2_, p_191986_3_);
/* 1872 */       this.motionY = d3 * 0.6D;
/* 1873 */       this.jumpMovementFactor = f;
/* 1874 */       this.fallDistance = 0.0F;
/* 1875 */       setFlag(7, false);
/*      */     }
/*      */     else {
/*      */       
/* 1879 */       super.func_191986_a(p_191986_1_, p_191986_2_, p_191986_3_);
/*      */     } 
/*      */     
/* 1882 */     addMovementStat(this.posX - d0, this.posY - d1, this.posZ - d2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAIMoveSpeed() {
/* 1890 */     return (float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addMovementStat(double p_71000_1_, double p_71000_3_, double p_71000_5_) {
/* 1898 */     if (!isRiding())
/*      */     {
/* 1900 */       if (isInsideOfMaterial(Material.WATER)) {
/*      */         
/* 1902 */         int i = Math.round(MathHelper.sqrt(p_71000_1_ * p_71000_1_ + p_71000_3_ * p_71000_3_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1904 */         if (i > 0)
/*      */         {
/* 1906 */           addStat(StatList.DIVE_ONE_CM, i);
/* 1907 */           addExhaustion(0.01F * i * 0.01F);
/*      */         }
/*      */       
/* 1910 */       } else if (isInWater()) {
/*      */         
/* 1912 */         int j = Math.round(MathHelper.sqrt(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1914 */         if (j > 0)
/*      */         {
/* 1916 */           addStat(StatList.SWIM_ONE_CM, j);
/* 1917 */           addExhaustion(0.01F * j * 0.01F);
/*      */         }
/*      */       
/* 1920 */       } else if (isOnLadder()) {
/*      */         
/* 1922 */         if (p_71000_3_ > 0.0D)
/*      */         {
/* 1924 */           addStat(StatList.CLIMB_ONE_CM, (int)Math.round(p_71000_3_ * 100.0D));
/*      */         }
/*      */       }
/* 1927 */       else if (this.onGround) {
/*      */         
/* 1929 */         int k = Math.round(MathHelper.sqrt(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1931 */         if (k > 0)
/*      */         {
/* 1933 */           if (isSprinting())
/*      */           {
/* 1935 */             addStat(StatList.SPRINT_ONE_CM, k);
/* 1936 */             addExhaustion(0.1F * k * 0.01F);
/*      */           }
/* 1938 */           else if (isSneaking())
/*      */           {
/* 1940 */             addStat(StatList.CROUCH_ONE_CM, k);
/* 1941 */             addExhaustion(0.0F * k * 0.01F);
/*      */           }
/*      */           else
/*      */           {
/* 1945 */             addStat(StatList.WALK_ONE_CM, k);
/* 1946 */             addExhaustion(0.0F * k * 0.01F);
/*      */           }
/*      */         
/*      */         }
/* 1950 */       } else if (isElytraFlying()) {
/*      */         
/* 1952 */         int l = Math.round(MathHelper.sqrt(p_71000_1_ * p_71000_1_ + p_71000_3_ * p_71000_3_ + p_71000_5_ * p_71000_5_) * 100.0F);
/* 1953 */         addStat(StatList.AVIATE_ONE_CM, l);
/*      */       }
/*      */       else {
/*      */         
/* 1957 */         int i1 = Math.round(MathHelper.sqrt(p_71000_1_ * p_71000_1_ + p_71000_5_ * p_71000_5_) * 100.0F);
/*      */         
/* 1959 */         if (i1 > 25)
/*      */         {
/* 1961 */           addStat(StatList.FLY_ONE_CM, i1);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addMountedMovementStat(double p_71015_1_, double p_71015_3_, double p_71015_5_) {
/* 1972 */     if (isRiding()) {
/*      */       
/* 1974 */       int i = Math.round(MathHelper.sqrt(p_71015_1_ * p_71015_1_ + p_71015_3_ * p_71015_3_ + p_71015_5_ * p_71015_5_) * 100.0F);
/*      */       
/* 1976 */       if (i > 0)
/*      */       {
/* 1978 */         if (getRidingEntity() instanceof net.minecraft.entity.item.EntityMinecart) {
/*      */           
/* 1980 */           addStat(StatList.MINECART_ONE_CM, i);
/*      */         }
/* 1982 */         else if (getRidingEntity() instanceof net.minecraft.entity.item.EntityBoat) {
/*      */           
/* 1984 */           addStat(StatList.BOAT_ONE_CM, i);
/*      */         }
/* 1986 */         else if (getRidingEntity() instanceof EntityPig) {
/*      */           
/* 1988 */           addStat(StatList.PIG_ONE_CM, i);
/*      */         }
/* 1990 */         else if (getRidingEntity() instanceof AbstractHorse) {
/*      */           
/* 1992 */           addStat(StatList.HORSE_ONE_CM, i);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/* 2000 */     if (!this.capabilities.allowFlying) {
/*      */       
/* 2002 */       if (distance >= 2.0F)
/*      */       {
/* 2004 */         addStat(StatList.FALL_ONE_CM, (int)Math.round(distance * 100.0D));
/*      */       }
/*      */       
/* 2007 */       super.fall(distance, damageMultiplier);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetHeight() {
/* 2016 */     if (!isSpectator())
/*      */     {
/* 2018 */       super.resetHeight();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getFallSound(int heightIn) {
/* 2024 */     return (heightIn > 4) ? SoundEvents.ENTITY_PLAYER_BIG_FALL : SoundEvents.ENTITY_PLAYER_SMALL_FALL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillEntity(EntityLivingBase entityLivingIn) {
/* 2032 */     EntityList.EntityEggInfo entitylist$entityegginfo = (EntityList.EntityEggInfo)EntityList.ENTITY_EGGS.get(EntityList.func_191301_a((Entity)entityLivingIn));
/*      */     
/* 2034 */     if (entitylist$entityegginfo != null)
/*      */     {
/* 2036 */       addStat(entitylist$entityegginfo.killEntityStat);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInWeb() {
/* 2045 */     if (!this.capabilities.isFlying)
/*      */     {
/* 2047 */       super.setInWeb();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExperience(int amount) {
/* 2056 */     addScore(amount);
/* 2057 */     int i = Integer.MAX_VALUE - this.experienceTotal;
/*      */     
/* 2059 */     if (amount > i)
/*      */     {
/* 2061 */       amount = i;
/*      */     }
/*      */     
/* 2064 */     this.experience += amount / xpBarCap();
/*      */     
/* 2066 */     for (this.experienceTotal += amount; this.experience >= 1.0F; this.experience /= xpBarCap()) {
/*      */       
/* 2068 */       this.experience = (this.experience - 1.0F) * xpBarCap();
/* 2069 */       addExperienceLevel(1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getXPSeed() {
/* 2075 */     return this.xpSeed;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_192024_a(ItemStack p_192024_1_, int p_192024_2_) {
/* 2080 */     this.experienceLevel -= p_192024_2_;
/*      */     
/* 2082 */     if (this.experienceLevel < 0) {
/*      */       
/* 2084 */       this.experienceLevel = 0;
/* 2085 */       this.experience = 0.0F;
/* 2086 */       this.experienceTotal = 0;
/*      */     } 
/*      */     
/* 2089 */     this.xpSeed = this.rand.nextInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExperienceLevel(int levels) {
/* 2097 */     this.experienceLevel += levels;
/*      */     
/* 2099 */     if (this.experienceLevel < 0) {
/*      */       
/* 2101 */       this.experienceLevel = 0;
/* 2102 */       this.experience = 0.0F;
/* 2103 */       this.experienceTotal = 0;
/*      */     } 
/*      */     
/* 2106 */     if (levels > 0 && this.experienceLevel % 5 == 0 && this.lastXPSound < this.ticksExisted - 100.0F) {
/*      */       
/* 2108 */       float f = (this.experienceLevel > 30) ? 1.0F : (this.experienceLevel / 30.0F);
/* 2109 */       this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_PLAYER_LEVELUP, getSoundCategory(), f * 0.75F, 1.0F);
/* 2110 */       this.lastXPSound = this.ticksExisted;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int xpBarCap() {
/* 2120 */     if (this.experienceLevel >= 30)
/*      */     {
/* 2122 */       return 112 + (this.experienceLevel - 30) * 9;
/*      */     }
/*      */ 
/*      */     
/* 2126 */     return (this.experienceLevel >= 15) ? (37 + (this.experienceLevel - 15) * 5) : (7 + this.experienceLevel * 2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addExhaustion(float exhaustion) {
/* 2135 */     if (!this.capabilities.disableDamage)
/*      */     {
/* 2137 */       if (!this.world.isRemote)
/*      */       {
/* 2139 */         this.foodStats.addExhaustion(exhaustion);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FoodStats getFoodStats() {
/* 2149 */     return this.foodStats;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canEat(boolean ignoreHunger) {
/* 2154 */     return ((ignoreHunger || this.foodStats.needFood()) && !this.capabilities.disableDamage);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean shouldHeal() {
/* 2162 */     return (getHealth() > 0.0F && getHealth() < getMaxHealth());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAllowEdit() {
/* 2167 */     return this.capabilities.allowEdit;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canPlayerEdit(BlockPos pos, EnumFacing facing, ItemStack stack) {
/* 2172 */     if (this.capabilities.allowEdit)
/*      */     {
/* 2174 */       return true;
/*      */     }
/* 2176 */     if (stack.func_190926_b())
/*      */     {
/* 2178 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2182 */     BlockPos blockpos = pos.offset(facing.getOpposite());
/* 2183 */     Block block = this.world.getBlockState(blockpos).getBlock();
/* 2184 */     return !(!stack.canPlaceOn(block) && !stack.canEditBlocks());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getExperiencePoints(EntityPlayer player) {
/* 2193 */     if (!this.world.getGameRules().getBoolean("keepInventory") && !isSpectator()) {
/*      */       
/* 2195 */       int i = this.experienceLevel * 7;
/* 2196 */       return (i > 100) ? 100 : i;
/*      */     } 
/*      */ 
/*      */     
/* 2200 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isPlayer() {
/* 2209 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAlwaysRenderNameTagForRender() {
/* 2214 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canTriggerWalking() {
/* 2223 */     return !this.capabilities.isFlying;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendPlayerAbilities() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGameType(GameType gameType) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 2245 */     return this.gameProfile.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public InventoryEnderChest getInventoryEnderChest() {
/* 2253 */     return this.theInventoryEnderChest;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getItemStackFromSlot(EntityEquipmentSlot slotIn) {
/* 2258 */     if (slotIn == EntityEquipmentSlot.MAINHAND)
/*      */     {
/* 2260 */       return this.inventory.getCurrentItem();
/*      */     }
/* 2262 */     if (slotIn == EntityEquipmentSlot.OFFHAND)
/*      */     {
/* 2264 */       return (ItemStack)this.inventory.offHandInventory.get(0);
/*      */     }
/*      */ 
/*      */     
/* 2268 */     return (slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR) ? (ItemStack)this.inventory.armorInventory.get(slotIn.getIndex()) : ItemStack.field_190927_a;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {
/* 2274 */     if (slotIn == EntityEquipmentSlot.MAINHAND) {
/*      */       
/* 2276 */       playEquipSound(stack);
/* 2277 */       this.inventory.mainInventory.set(this.inventory.currentItem, stack);
/*      */     }
/* 2279 */     else if (slotIn == EntityEquipmentSlot.OFFHAND) {
/*      */       
/* 2281 */       playEquipSound(stack);
/* 2282 */       this.inventory.offHandInventory.set(0, stack);
/*      */     }
/* 2284 */     else if (slotIn.getSlotType() == EntityEquipmentSlot.Type.ARMOR) {
/*      */       
/* 2286 */       playEquipSound(stack);
/* 2287 */       this.inventory.armorInventory.set(slotIn.getIndex(), stack);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_191521_c(ItemStack p_191521_1_) {
/* 2293 */     playEquipSound(p_191521_1_);
/* 2294 */     return this.inventory.addItemStackToInventory(p_191521_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterable<ItemStack> getHeldEquipment() {
/* 2299 */     return Lists.newArrayList((Object[])new ItemStack[] { getHeldItemMainhand(), getHeldItemOffhand() });
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterable<ItemStack> getArmorInventoryList() {
/* 2304 */     return (Iterable<ItemStack>)this.inventory.armorInventory;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_192027_g(NBTTagCompound p_192027_1_) {
/* 2309 */     if (!isRiding() && this.onGround && !isInWater()) {
/*      */       
/* 2311 */       if (func_192023_dk().hasNoTags()) {
/*      */         
/* 2313 */         func_192029_h(p_192027_1_);
/* 2314 */         return true;
/*      */       } 
/* 2316 */       if (func_192025_dl().hasNoTags()) {
/*      */         
/* 2318 */         func_192031_i(p_192027_1_);
/* 2319 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 2323 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2328 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_192030_dh() {
/* 2334 */     func_192026_k(func_192023_dk());
/* 2335 */     func_192029_h(new NBTTagCompound());
/* 2336 */     func_192026_k(func_192025_dl());
/* 2337 */     func_192031_i(new NBTTagCompound());
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_192026_k(@Nullable NBTTagCompound p_192026_1_) {
/* 2342 */     if (!this.world.isRemote && !p_192026_1_.hasNoTags()) {
/*      */       
/* 2344 */       Entity entity = EntityList.createEntityFromNBT(p_192026_1_, this.world);
/*      */       
/* 2346 */       if (entity instanceof EntityTameable)
/*      */       {
/* 2348 */         ((EntityTameable)entity).setOwnerId(this.entityUniqueID);
/*      */       }
/*      */       
/* 2351 */       entity.setPosition(this.posX, this.posY + 0.699999988079071D, this.posZ);
/* 2352 */       this.world.spawnEntityInWorld(entity);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInvisibleToPlayer(EntityPlayer player) {
/* 2363 */     if (!isInvisible())
/*      */     {
/* 2365 */       return false;
/*      */     }
/* 2367 */     if (player.isSpectator())
/*      */     {
/* 2369 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2373 */     Team team = getTeam();
/* 2374 */     return !(team != null && player != null && player.getTeam() == team && team.getSeeFriendlyInvisiblesEnabled());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPushedByWater() {
/* 2387 */     return !this.capabilities.isFlying;
/*      */   }
/*      */ 
/*      */   
/*      */   public Scoreboard getWorldScoreboard() {
/* 2392 */     return this.world.getScoreboard();
/*      */   }
/*      */ 
/*      */   
/*      */   public Team getTeam() {
/* 2397 */     return (Team)getWorldScoreboard().getPlayersTeam(getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ITextComponent getDisplayName() {
/* 2405 */     TextComponentString textComponentString = new TextComponentString(ScorePlayerTeam.formatPlayerName(getTeam(), getName()));
/* 2406 */     textComponentString.getStyle().setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + getName() + " "));
/* 2407 */     textComponentString.getStyle().setHoverEvent(getHoverEvent());
/* 2408 */     textComponentString.getStyle().setInsertion(getName());
/* 2409 */     return (ITextComponent)textComponentString;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getEyeHeight() {
/* 2414 */     float f = 1.62F;
/*      */     
/* 2416 */     if (isPlayerSleeping()) {
/*      */       
/* 2418 */       f = 0.2F;
/*      */     }
/* 2420 */     else if (!isSneaking() && this.height != 1.65F) {
/*      */       
/* 2422 */       if (isElytraFlying() || this.height == 0.6F)
/*      */       {
/* 2424 */         f = 0.4F;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 2429 */       f -= 0.08F;
/*      */     } 
/*      */     
/* 2432 */     return f;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAbsorptionAmount(float amount) {
/* 2437 */     if (amount < 0.0F)
/*      */     {
/* 2439 */       amount = 0.0F;
/*      */     }
/*      */     
/* 2442 */     getDataManager().set(ABSORPTION, Float.valueOf(amount));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAbsorptionAmount() {
/* 2450 */     return ((Float)getDataManager().get(ABSORPTION)).floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static UUID getUUID(GameProfile profile) {
/* 2458 */     UUID uuid = profile.getId();
/*      */     
/* 2460 */     if (uuid == null)
/*      */     {
/* 2462 */       uuid = getOfflineUUID(profile.getName());
/*      */     }
/*      */     
/* 2465 */     return uuid;
/*      */   }
/*      */ 
/*      */   
/*      */   public static UUID getOfflineUUID(String username) {
/* 2470 */     return UUID.nameUUIDFromBytes(("OfflinePlayer:" + username).getBytes(StandardCharsets.UTF_8));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canOpen(LockCode code) {
/* 2478 */     if (code.isEmpty())
/*      */     {
/* 2480 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 2484 */     ItemStack itemstack = getHeldItemMainhand();
/* 2485 */     return (!itemstack.func_190926_b() && itemstack.hasDisplayName()) ? itemstack.getDisplayName().equals(code.getLock()) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWearing(EnumPlayerModelParts part) {
/* 2491 */     return ((((Byte)getDataManager().get(PLAYER_MODEL_FLAG)).byteValue() & part.getPartMask()) == part.getPartMask());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sendCommandFeedback() {
/* 2499 */     return (getServer()).worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
/*      */   }
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/*      */     EntityEquipmentSlot entityequipmentslot;
/* 2504 */     if (inventorySlot >= 0 && inventorySlot < this.inventory.mainInventory.size()) {
/*      */       
/* 2506 */       this.inventory.setInventorySlotContents(inventorySlot, itemStackIn);
/* 2507 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2513 */     if (inventorySlot == 100 + EntityEquipmentSlot.HEAD.getIndex()) {
/*      */       
/* 2515 */       entityequipmentslot = EntityEquipmentSlot.HEAD;
/*      */     }
/* 2517 */     else if (inventorySlot == 100 + EntityEquipmentSlot.CHEST.getIndex()) {
/*      */       
/* 2519 */       entityequipmentslot = EntityEquipmentSlot.CHEST;
/*      */     }
/* 2521 */     else if (inventorySlot == 100 + EntityEquipmentSlot.LEGS.getIndex()) {
/*      */       
/* 2523 */       entityequipmentslot = EntityEquipmentSlot.LEGS;
/*      */     }
/* 2525 */     else if (inventorySlot == 100 + EntityEquipmentSlot.FEET.getIndex()) {
/*      */       
/* 2527 */       entityequipmentslot = EntityEquipmentSlot.FEET;
/*      */     }
/*      */     else {
/*      */       
/* 2531 */       entityequipmentslot = null;
/*      */     } 
/*      */     
/* 2534 */     if (inventorySlot == 98) {
/*      */       
/* 2536 */       setItemStackToSlot(EntityEquipmentSlot.MAINHAND, itemStackIn);
/* 2537 */       return true;
/*      */     } 
/* 2539 */     if (inventorySlot == 99) {
/*      */       
/* 2541 */       setItemStackToSlot(EntityEquipmentSlot.OFFHAND, itemStackIn);
/* 2542 */       return true;
/*      */     } 
/* 2544 */     if (entityequipmentslot == null) {
/*      */       
/* 2546 */       int i = inventorySlot - 200;
/*      */       
/* 2548 */       if (i >= 0 && i < this.theInventoryEnderChest.getSizeInventory()) {
/*      */         
/* 2550 */         this.theInventoryEnderChest.setInventorySlotContents(i, itemStackIn);
/* 2551 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 2555 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2560 */     if (!itemStackIn.func_190926_b())
/*      */     {
/* 2562 */       if (!(itemStackIn.getItem() instanceof net.minecraft.item.ItemArmor) && !(itemStackIn.getItem() instanceof net.minecraft.item.ItemElytra)) {
/*      */         
/* 2564 */         if (entityequipmentslot != EntityEquipmentSlot.HEAD)
/*      */         {
/* 2566 */           return false;
/*      */         }
/*      */       }
/* 2569 */       else if (EntityLiving.getSlotForItemStack(itemStackIn) != entityequipmentslot) {
/*      */         
/* 2571 */         return false;
/*      */       } 
/*      */     }
/*      */     
/* 2575 */     this.inventory.setInventorySlotContents(entityequipmentslot.getIndex() + this.inventory.mainInventory.size(), itemStackIn);
/* 2576 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasReducedDebug() {
/* 2586 */     return this.hasReducedDebug;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setReducedDebug(boolean reducedDebug) {
/* 2591 */     this.hasReducedDebug = reducedDebug;
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumHandSide getPrimaryHand() {
/* 2596 */     return (((Byte)this.dataManager.get(MAIN_HAND)).byteValue() == 0) ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPrimaryHand(EnumHandSide hand) {
/* 2601 */     this.dataManager.set(MAIN_HAND, Byte.valueOf((byte)((hand == EnumHandSide.LEFT) ? 0 : 1)));
/*      */   }
/*      */ 
/*      */   
/*      */   public NBTTagCompound func_192023_dk() {
/* 2606 */     return (NBTTagCompound)this.dataManager.get(field_192032_bt);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_192029_h(NBTTagCompound p_192029_1_) {
/* 2611 */     this.dataManager.set(field_192032_bt, p_192029_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public NBTTagCompound func_192025_dl() {
/* 2616 */     return (NBTTagCompound)this.dataManager.get(field_192033_bu);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_192031_i(NBTTagCompound p_192031_1_) {
/* 2621 */     this.dataManager.set(field_192033_bu, p_192031_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCooldownPeriod() {
/* 2626 */     return (float)(1.0D / getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getAttributeValue() * 20.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCooledAttackStrength(float adjustTicks) {
/* 2634 */     return MathHelper.clamp((this.ticksSinceLastSwing + adjustTicks) / getCooldownPeriod(), 0.0F, 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetCooldown() {
/* 2639 */     this.ticksSinceLastSwing = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public CooldownTracker getCooldownTracker() {
/* 2644 */     return this.cooldownTracker;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyEntityCollision(Entity entityIn) {
/* 2652 */     if (!isPlayerSleeping())
/*      */     {
/* 2654 */       super.applyEntityCollision(entityIn);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public float getLuck() {
/* 2660 */     return (float)getEntityAttribute(SharedMonsterAttributes.LUCK).getAttributeValue();
/*      */   }
/*      */   
/*      */   public abstract boolean isSpectator();
/*      */   
/*      */   public abstract boolean isCreative();
/*      */   
/*      */   public boolean canUseCommandBlock() {
/* 2668 */     return (this.capabilities.isCreativeMode && canCommandSenderUseCommand(2, ""));
/*      */   }
/*      */   
/*      */   public enum EnumChatVisibility
/*      */   {
/* 2673 */     FULL(0, "options.chat.visibility.full"),
/* 2674 */     SYSTEM(1, "options.chat.visibility.system"),
/* 2675 */     HIDDEN(2, "options.chat.visibility.hidden");
/*      */     
/* 2677 */     private static final EnumChatVisibility[] ID_LOOKUP = new EnumChatVisibility[(values()).length];
/*      */     
/*      */     private final int chatVisibility;
/*      */     
/*      */     private final String resourceKey;
/*      */ 
/*      */     
/*      */     EnumChatVisibility(int id, String resourceKey) {
/*      */       this.chatVisibility = id;
/*      */       this.resourceKey = resourceKey;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getChatVisibility() {
/*      */       return this.chatVisibility;
/*      */     }
/*      */ 
/*      */     
/*      */     public static EnumChatVisibility getEnumChatVisibility(int id) {
/*      */       return ID_LOOKUP[id % ID_LOOKUP.length];
/*      */     }
/*      */     
/*      */     static {
/*      */       byte b;
/*      */       int i;
/*      */       EnumChatVisibility[] arrayOfEnumChatVisibility;
/* 2703 */       for (i = (arrayOfEnumChatVisibility = values()).length, b = 0; b < i; ) { EnumChatVisibility entityplayer$enumchatvisibility = arrayOfEnumChatVisibility[b];
/*      */         
/* 2705 */         ID_LOOKUP[entityplayer$enumchatvisibility.chatVisibility] = entityplayer$enumchatvisibility;
/*      */         b++; }
/*      */     
/*      */     }
/*      */     
/*      */     public String getResourceKey() {
/*      */       return this.resourceKey;
/*      */     } }
/*      */   
/*      */   static class SleepEnemyPredicate implements Predicate<EntityMob> {
/*      */     private SleepEnemyPredicate(EntityPlayer p_i47461_1_) {
/* 2716 */       this.field_192387_a = p_i47461_1_;
/*      */     }
/*      */     private final EntityPlayer field_192387_a;
/*      */     
/*      */     public boolean apply(@Nullable EntityMob p_apply_1_) {
/* 2721 */       return p_apply_1_.func_191990_c(this.field_192387_a);
/*      */     }
/*      */   }
/*      */   
/*      */   public enum SleepResult
/*      */   {
/* 2727 */     OK,
/* 2728 */     NOT_POSSIBLE_HERE,
/* 2729 */     NOT_POSSIBLE_NOW,
/* 2730 */     TOO_FAR_AWAY,
/* 2731 */     OTHER_PROBLEM,
/* 2732 */     NOT_SAFE;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\player\EntityPlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */