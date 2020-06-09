/*      */ package net.minecraft.entity.passive;
/*      */ import com.google.common.base.Optional;
/*      */ import com.google.common.base.Predicate;
/*      */ import java.util.UUID;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.SoundType;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityAgeable;
/*      */ import net.minecraft.entity.EntityCreature;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.EnumCreatureAttribute;
/*      */ import net.minecraft.entity.IEntityLivingData;
/*      */ import net.minecraft.entity.IJumpingMount;
/*      */ import net.minecraft.entity.SharedMonsterAttributes;
/*      */ import net.minecraft.entity.ai.EntityAIBase;
/*      */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*      */ import net.minecraft.entity.ai.EntityAILookIdle;
/*      */ import net.minecraft.entity.ai.EntityAIMate;
/*      */ import net.minecraft.entity.ai.EntityAIPanic;
/*      */ import net.minecraft.entity.ai.EntityAIRunAroundLikeCrazy;
/*      */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*      */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*      */ import net.minecraft.entity.ai.attributes.IAttribute;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.init.MobEffects;
/*      */ import net.minecraft.init.SoundEvents;
/*      */ import net.minecraft.inventory.ContainerHorseChest;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.IInventoryChangedListener;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.datasync.DataSerializers;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.server.management.PreYggdrasilConverter;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.SoundEvent;
/*      */ import net.minecraft.util.datafix.DataFixer;
/*      */ import net.minecraft.util.datafix.FixTypes;
/*      */ import net.minecraft.util.datafix.IDataWalker;
/*      */ import net.minecraft.util.datafix.walkers.ItemStackData;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.world.DifficultyInstance;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public abstract class AbstractHorse extends EntityAnimal implements IInventoryChangedListener, IJumpingMount {
/*   60 */   private static final Predicate<Entity> IS_HORSE_BREEDING = new Predicate<Entity>()
/*      */     {
/*      */       public boolean apply(@Nullable Entity p_apply_1_)
/*      */       {
/*   64 */         return (p_apply_1_ instanceof AbstractHorse && ((AbstractHorse)p_apply_1_).isBreeding());
/*      */       }
/*      */     };
/*   67 */   protected static final IAttribute JUMP_STRENGTH = (IAttribute)(new RangedAttribute(null, "horse.jumpStrength", 0.7D, 0.0D, 2.0D)).setDescription("Jump Strength").setShouldWatch(true);
/*   68 */   public static final DataParameter<Byte> STATUS = EntityDataManager.createKey(AbstractHorse.class, DataSerializers.BYTE);
/*   69 */   public static final DataParameter<Optional<UUID>> OWNER_UNIQUE_ID = EntityDataManager.createKey(AbstractHorse.class, DataSerializers.OPTIONAL_UNIQUE_ID);
/*      */   
/*      */   private int field_190689_bJ;
/*      */   
/*      */   private int openMouthCounter;
/*      */   
/*      */   private int jumpRearingCounter;
/*      */   
/*      */   public int tailCounter;
/*      */   
/*      */   public int sprintCounter;
/*      */   
/*      */   protected boolean horseJumping;
/*      */   
/*      */   protected ContainerHorseChest horseChest;
/*      */   protected int temper;
/*      */   protected float jumpPower;
/*      */   private boolean allowStandSliding;
/*      */   private float headLean;
/*      */   private float prevHeadLean;
/*      */   private float rearingAmount;
/*      */   private float prevRearingAmount;
/*      */   private float mouthOpenness;
/*      */   private float prevMouthOpenness;
/*      */   protected boolean field_190688_bE = true;
/*      */   protected int gallopTime;
/*      */   
/*      */   public AbstractHorse(World p_i47299_1_) {
/*   97 */     super(p_i47299_1_);
/*   98 */     setSize(1.3964844F, 1.6F);
/*   99 */     this.stepHeight = 1.0F;
/*  100 */     initHorseChest();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void initEntityAI() {
/*  105 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  106 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.2D));
/*  107 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIRunAroundLikeCrazy(this, 1.2D));
/*  108 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMate(this, 1.0D, AbstractHorse.class));
/*  109 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent(this, 1.0D));
/*  110 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 0.7D));
/*  111 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  112 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  117 */     super.entityInit();
/*  118 */     this.dataManager.register(STATUS, Byte.valueOf((byte)0));
/*  119 */     this.dataManager.register(OWNER_UNIQUE_ID, Optional.absent());
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean getHorseWatchableBoolean(int p_110233_1_) {
/*  124 */     return ((((Byte)this.dataManager.get(STATUS)).byteValue() & p_110233_1_) != 0);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void setHorseWatchableBoolean(int p_110208_1_, boolean p_110208_2_) {
/*  129 */     byte b0 = ((Byte)this.dataManager.get(STATUS)).byteValue();
/*      */     
/*  131 */     if (p_110208_2_) {
/*      */       
/*  133 */       this.dataManager.set(STATUS, Byte.valueOf((byte)(b0 | p_110208_1_)));
/*      */     }
/*      */     else {
/*      */       
/*  137 */       this.dataManager.set(STATUS, Byte.valueOf((byte)(b0 & (p_110208_1_ ^ 0xFFFFFFFF))));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTame() {
/*  143 */     return getHorseWatchableBoolean(2);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public UUID getOwnerUniqueId() {
/*  149 */     return (UUID)((Optional)this.dataManager.get(OWNER_UNIQUE_ID)).orNull();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOwnerUniqueId(@Nullable UUID uniqueId) {
/*  154 */     this.dataManager.set(OWNER_UNIQUE_ID, Optional.fromNullable(uniqueId));
/*      */   }
/*      */ 
/*      */   
/*      */   public float getHorseSize() {
/*  159 */     return 0.5F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScaleForAge(boolean child) {
/*  167 */     setScale(child ? getHorseSize() : 1.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isHorseJumping() {
/*  172 */     return this.horseJumping;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHorseTamed(boolean tamed) {
/*  177 */     setHorseWatchableBoolean(2, tamed);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHorseJumping(boolean jumping) {
/*  182 */     this.horseJumping = jumping;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBeLeashedTo(EntityPlayer player) {
/*  187 */     return (super.canBeLeashedTo(player) && getCreatureAttribute() != EnumCreatureAttribute.UNDEAD);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onLeashDistance(float p_142017_1_) {
/*  192 */     if (p_142017_1_ > 6.0F && isEatingHaystack())
/*      */     {
/*  194 */       setEatingHaystack(false);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEatingHaystack() {
/*  200 */     return getHorseWatchableBoolean(16);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRearing() {
/*  205 */     return getHorseWatchableBoolean(32);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBreeding() {
/*  210 */     return getHorseWatchableBoolean(8);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBreeding(boolean breeding) {
/*  215 */     setHorseWatchableBoolean(8, breeding);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHorseSaddled(boolean saddled) {
/*  220 */     setHorseWatchableBoolean(4, saddled);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTemper() {
/*  225 */     return this.temper;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTemper(int temperIn) {
/*  230 */     this.temper = temperIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public int increaseTemper(int p_110198_1_) {
/*  235 */     int i = MathHelper.clamp(getTemper() + p_110198_1_, 0, func_190676_dC());
/*  236 */     setTemper(i);
/*  237 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  245 */     Entity entity = source.getEntity();
/*  246 */     return (isBeingRidden() && entity != null && isRidingOrBeingRiddenBy(entity)) ? false : super.attackEntityFrom(source, amount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/*  254 */     return !isBeingRidden();
/*      */   }
/*      */ 
/*      */   
/*      */   private void eatingHorse() {
/*  259 */     openHorseMouth();
/*      */     
/*  261 */     if (!isSilent())
/*      */     {
/*  263 */       this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_HORSE_EAT, getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/*  269 */     if (distance > 1.0F)
/*      */     {
/*  271 */       playSound(SoundEvents.ENTITY_HORSE_LAND, 0.4F, 1.0F);
/*      */     }
/*      */     
/*  274 */     int i = MathHelper.ceil((distance * 0.5F - 3.0F) * damageMultiplier);
/*      */     
/*  276 */     if (i > 0) {
/*      */       
/*  278 */       attackEntityFrom(DamageSource.fall, i);
/*      */       
/*  280 */       if (isBeingRidden())
/*      */       {
/*  282 */         for (Entity entity : getRecursivePassengers())
/*      */         {
/*  284 */           entity.attackEntityFrom(DamageSource.fall, i);
/*      */         }
/*      */       }
/*      */       
/*  288 */       IBlockState iblockstate = this.world.getBlockState(new BlockPos(this.posX, this.posY - 0.2D - this.prevRotationYaw, this.posZ));
/*  289 */       Block block = iblockstate.getBlock();
/*      */       
/*  291 */       if (iblockstate.getMaterial() != Material.AIR && !isSilent()) {
/*      */         
/*  293 */         SoundType soundtype = block.getSoundType();
/*  294 */         this.world.playSound(null, this.posX, this.posY, this.posZ, soundtype.getStepSound(), getSoundCategory(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected int func_190686_di() {
/*  301 */     return 2;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void initHorseChest() {
/*  306 */     ContainerHorseChest containerhorsechest = this.horseChest;
/*  307 */     this.horseChest = new ContainerHorseChest("HorseChest", func_190686_di());
/*  308 */     this.horseChest.setCustomName(getName());
/*      */     
/*  310 */     if (containerhorsechest != null) {
/*      */       
/*  312 */       containerhorsechest.removeInventoryChangeListener(this);
/*  313 */       int i = Math.min(containerhorsechest.getSizeInventory(), this.horseChest.getSizeInventory());
/*      */       
/*  315 */       for (int j = 0; j < i; j++) {
/*      */         
/*  317 */         ItemStack itemstack = containerhorsechest.getStackInSlot(j);
/*      */         
/*  319 */         if (!itemstack.func_190926_b())
/*      */         {
/*  321 */           this.horseChest.setInventorySlotContents(j, itemstack.copy());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  326 */     this.horseChest.addInventoryChangeListener(this);
/*  327 */     updateHorseSlots();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateHorseSlots() {
/*  335 */     if (!this.world.isRemote)
/*      */     {
/*  337 */       setHorseSaddled((!this.horseChest.getStackInSlot(0).func_190926_b() && func_190685_dA()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onInventoryChanged(IInventory invBasic) {
/*  346 */     boolean flag = isHorseSaddled();
/*  347 */     updateHorseSlots();
/*      */     
/*  349 */     if (this.ticksExisted > 20 && !flag && isHorseSaddled())
/*      */     {
/*  351 */       playSound(SoundEvents.ENTITY_HORSE_SADDLE, 0.5F, 1.0F);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected AbstractHorse getClosestHorse(Entity entityIn, double distance) {
/*  358 */     double d0 = Double.MAX_VALUE;
/*  359 */     Entity entity = null;
/*      */     
/*  361 */     for (Entity entity1 : this.world.getEntitiesInAABBexcluding(entityIn, entityIn.getEntityBoundingBox().addCoord(distance, distance, distance), IS_HORSE_BREEDING)) {
/*      */       
/*  363 */       double d1 = entity1.getDistanceSq(entityIn.posX, entityIn.posY, entityIn.posZ);
/*      */       
/*  365 */       if (d1 < d0) {
/*      */         
/*  367 */         entity = entity1;
/*  368 */         d0 = d1;
/*      */       } 
/*      */     } 
/*      */     
/*  372 */     return (AbstractHorse)entity;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getHorseJumpStrength() {
/*  377 */     return getEntityAttribute(JUMP_STRENGTH).getAttributeValue();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getDeathSound() {
/*  383 */     openHorseMouth();
/*  384 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  390 */     openHorseMouth();
/*      */     
/*  392 */     if (this.rand.nextInt(3) == 0)
/*      */     {
/*  394 */       makeHorseRear();
/*      */     }
/*      */     
/*  397 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getAmbientSound() {
/*  403 */     openHorseMouth();
/*      */     
/*  405 */     if (this.rand.nextInt(10) == 0 && !isMovementBlocked())
/*      */     {
/*  407 */       makeHorseRear();
/*      */     }
/*      */     
/*  410 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_190685_dA() {
/*  415 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isHorseSaddled() {
/*  420 */     return getHorseWatchableBoolean(4);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getAngrySound() {
/*  426 */     openHorseMouth();
/*  427 */     makeHorseRear();
/*  428 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  433 */     if (!blockIn.getDefaultState().getMaterial().isLiquid()) {
/*      */       
/*  435 */       SoundType soundtype = blockIn.getSoundType();
/*      */       
/*  437 */       if (this.world.getBlockState(pos.up()).getBlock() == Blocks.SNOW_LAYER)
/*      */       {
/*  439 */         soundtype = Blocks.SNOW_LAYER.getSoundType();
/*      */       }
/*      */       
/*  442 */       if (isBeingRidden() && this.field_190688_bE) {
/*      */         
/*  444 */         this.gallopTime++;
/*      */         
/*  446 */         if (this.gallopTime > 5 && this.gallopTime % 3 == 0)
/*      */         {
/*  448 */           func_190680_a(soundtype);
/*      */         }
/*  450 */         else if (this.gallopTime <= 5)
/*      */         {
/*  452 */           playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, soundtype.getVolume() * 0.15F, soundtype.getPitch());
/*      */         }
/*      */       
/*  455 */       } else if (soundtype == SoundType.WOOD) {
/*      */         
/*  457 */         playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, soundtype.getVolume() * 0.15F, soundtype.getPitch());
/*      */       }
/*      */       else {
/*      */         
/*  461 */         playSound(SoundEvents.ENTITY_HORSE_STEP, soundtype.getVolume() * 0.15F, soundtype.getPitch());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_190680_a(SoundType p_190680_1_) {
/*  468 */     playSound(SoundEvents.ENTITY_HORSE_GALLOP, p_190680_1_.getVolume() * 0.15F, p_190680_1_.getPitch());
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  473 */     super.applyEntityAttributes();
/*  474 */     getAttributeMap().registerAttribute(JUMP_STRENGTH);
/*  475 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(53.0D);
/*  476 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.22499999403953552D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxSpawnedInChunk() {
/*  484 */     return 6;
/*      */   }
/*      */ 
/*      */   
/*      */   public int func_190676_dC() {
/*  489 */     return 100;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getSoundVolume() {
/*  497 */     return 0.8F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTalkInterval() {
/*  505 */     return 400;
/*      */   }
/*      */ 
/*      */   
/*      */   public void openGUI(EntityPlayer playerEntity) {
/*  510 */     if (!this.world.isRemote && (!isBeingRidden() || isPassenger((Entity)playerEntity)) && isTame()) {
/*      */       
/*  512 */       this.horseChest.setCustomName(getName());
/*  513 */       playerEntity.openGuiHorseInventory(this, (IInventory)this.horseChest);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean func_190678_b(EntityPlayer p_190678_1_, ItemStack p_190678_2_) {
/*  519 */     boolean flag = false;
/*  520 */     float f = 0.0F;
/*  521 */     int i = 0;
/*  522 */     int j = 0;
/*  523 */     Item item = p_190678_2_.getItem();
/*      */     
/*  525 */     if (item == Items.WHEAT) {
/*      */       
/*  527 */       f = 2.0F;
/*  528 */       i = 20;
/*  529 */       j = 3;
/*      */     }
/*  531 */     else if (item == Items.SUGAR) {
/*      */       
/*  533 */       f = 1.0F;
/*  534 */       i = 30;
/*  535 */       j = 3;
/*      */     }
/*  537 */     else if (item == Item.getItemFromBlock(Blocks.HAY_BLOCK)) {
/*      */       
/*  539 */       f = 20.0F;
/*  540 */       i = 180;
/*      */     }
/*  542 */     else if (item == Items.APPLE) {
/*      */       
/*  544 */       f = 3.0F;
/*  545 */       i = 60;
/*  546 */       j = 3;
/*      */     }
/*  548 */     else if (item == Items.GOLDEN_CARROT) {
/*      */       
/*  550 */       f = 4.0F;
/*  551 */       i = 60;
/*  552 */       j = 5;
/*      */       
/*  554 */       if (isTame() && getGrowingAge() == 0 && !isInLove())
/*      */       {
/*  556 */         flag = true;
/*  557 */         setInLove(p_190678_1_);
/*      */       }
/*      */     
/*  560 */     } else if (item == Items.GOLDEN_APPLE) {
/*      */       
/*  562 */       f = 10.0F;
/*  563 */       i = 240;
/*  564 */       j = 10;
/*      */       
/*  566 */       if (isTame() && getGrowingAge() == 0 && !isInLove()) {
/*      */         
/*  568 */         flag = true;
/*  569 */         setInLove(p_190678_1_);
/*      */       } 
/*      */     } 
/*      */     
/*  573 */     if (getHealth() < getMaxHealth() && f > 0.0F) {
/*      */       
/*  575 */       heal(f);
/*  576 */       flag = true;
/*      */     } 
/*      */     
/*  579 */     if (isChild() && i > 0) {
/*      */       
/*  581 */       this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */       
/*  583 */       if (!this.world.isRemote)
/*      */       {
/*  585 */         addGrowth(i);
/*      */       }
/*      */       
/*  588 */       flag = true;
/*      */     } 
/*      */     
/*  591 */     if (j > 0 && (flag || !isTame()) && getTemper() < func_190676_dC()) {
/*      */       
/*  593 */       flag = true;
/*      */       
/*  595 */       if (!this.world.isRemote)
/*      */       {
/*  597 */         increaseTemper(j);
/*      */       }
/*      */     } 
/*      */     
/*  601 */     if (flag)
/*      */     {
/*  603 */       eatingHorse();
/*      */     }
/*      */     
/*  606 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void mountTo(EntityPlayer player) {
/*  611 */     player.rotationYaw = this.rotationYaw;
/*  612 */     player.rotationPitch = this.rotationPitch;
/*  613 */     setEatingHaystack(false);
/*  614 */     setRearing(false);
/*      */     
/*  616 */     if (!this.world.isRemote)
/*      */     {
/*  618 */       player.startRiding((Entity)this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isMovementBlocked() {
/*  627 */     return !((!super.isMovementBlocked() || !isBeingRidden() || !isHorseSaddled()) && !isEatingHaystack() && !isRearing());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBreedingItem(ItemStack stack) {
/*  636 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void moveTail() {
/*  641 */     this.tailCounter = 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/*  649 */     super.onDeath(cause);
/*      */     
/*  651 */     if (!this.world.isRemote && this.horseChest != null)
/*      */     {
/*  653 */       for (int i = 0; i < this.horseChest.getSizeInventory(); i++) {
/*      */         
/*  655 */         ItemStack itemstack = this.horseChest.getStackInSlot(i);
/*      */         
/*  657 */         if (!itemstack.func_190926_b())
/*      */         {
/*  659 */           entityDropItem(itemstack, 0.0F);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/*  671 */     if (this.rand.nextInt(200) == 0)
/*      */     {
/*  673 */       moveTail();
/*      */     }
/*      */     
/*  676 */     super.onLivingUpdate();
/*      */     
/*  678 */     if (!this.world.isRemote) {
/*      */       
/*  680 */       if (this.rand.nextInt(900) == 0 && this.deathTime == 0)
/*      */       {
/*  682 */         heal(1.0F);
/*      */       }
/*      */       
/*  685 */       if (func_190684_dE()) {
/*      */         
/*  687 */         if (!isEatingHaystack() && !isBeingRidden() && this.rand.nextInt(300) == 0 && this.world.getBlockState(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY) - 1, MathHelper.floor(this.posZ))).getBlock() == Blocks.GRASS)
/*      */         {
/*  689 */           setEatingHaystack(true);
/*      */         }
/*      */         
/*  692 */         if (isEatingHaystack() && ++this.field_190689_bJ > 50) {
/*      */           
/*  694 */           this.field_190689_bJ = 0;
/*  695 */           setEatingHaystack(false);
/*      */         } 
/*      */       } 
/*      */       
/*  699 */       func_190679_dD();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_190679_dD() {
/*  705 */     if (isBreeding() && isChild() && !isEatingHaystack()) {
/*      */       
/*  707 */       AbstractHorse abstracthorse = getClosestHorse((Entity)this, 16.0D);
/*      */       
/*  709 */       if (abstracthorse != null && getDistanceSqToEntity((Entity)abstracthorse) > 4.0D)
/*      */       {
/*  711 */         this.navigator.getPathToEntityLiving((Entity)abstracthorse);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_190684_dE() {
/*  718 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  726 */     super.onUpdate();
/*      */     
/*  728 */     if (this.openMouthCounter > 0 && ++this.openMouthCounter > 30) {
/*      */       
/*  730 */       this.openMouthCounter = 0;
/*  731 */       setHorseWatchableBoolean(64, false);
/*      */     } 
/*      */     
/*  734 */     if (canPassengerSteer() && this.jumpRearingCounter > 0 && ++this.jumpRearingCounter > 20) {
/*      */       
/*  736 */       this.jumpRearingCounter = 0;
/*  737 */       setRearing(false);
/*      */     } 
/*      */     
/*  740 */     if (this.tailCounter > 0 && ++this.tailCounter > 8)
/*      */     {
/*  742 */       this.tailCounter = 0;
/*      */     }
/*      */     
/*  745 */     if (this.sprintCounter > 0) {
/*      */       
/*  747 */       this.sprintCounter++;
/*      */       
/*  749 */       if (this.sprintCounter > 300)
/*      */       {
/*  751 */         this.sprintCounter = 0;
/*      */       }
/*      */     } 
/*      */     
/*  755 */     this.prevHeadLean = this.headLean;
/*      */     
/*  757 */     if (isEatingHaystack()) {
/*      */       
/*  759 */       this.headLean += (1.0F - this.headLean) * 0.4F + 0.05F;
/*      */       
/*  761 */       if (this.headLean > 1.0F)
/*      */       {
/*  763 */         this.headLean = 1.0F;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  768 */       this.headLean += (0.0F - this.headLean) * 0.4F - 0.05F;
/*      */       
/*  770 */       if (this.headLean < 0.0F)
/*      */       {
/*  772 */         this.headLean = 0.0F;
/*      */       }
/*      */     } 
/*      */     
/*  776 */     this.prevRearingAmount = this.rearingAmount;
/*      */     
/*  778 */     if (isRearing()) {
/*      */       
/*  780 */       this.headLean = 0.0F;
/*  781 */       this.prevHeadLean = this.headLean;
/*  782 */       this.rearingAmount += (1.0F - this.rearingAmount) * 0.4F + 0.05F;
/*      */       
/*  784 */       if (this.rearingAmount > 1.0F)
/*      */       {
/*  786 */         this.rearingAmount = 1.0F;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  791 */       this.allowStandSliding = false;
/*  792 */       this.rearingAmount += (0.8F * this.rearingAmount * this.rearingAmount * this.rearingAmount - this.rearingAmount) * 0.6F - 0.05F;
/*      */       
/*  794 */       if (this.rearingAmount < 0.0F)
/*      */       {
/*  796 */         this.rearingAmount = 0.0F;
/*      */       }
/*      */     } 
/*      */     
/*  800 */     this.prevMouthOpenness = this.mouthOpenness;
/*      */     
/*  802 */     if (getHorseWatchableBoolean(64)) {
/*      */       
/*  804 */       this.mouthOpenness += (1.0F - this.mouthOpenness) * 0.7F + 0.05F;
/*      */       
/*  806 */       if (this.mouthOpenness > 1.0F)
/*      */       {
/*  808 */         this.mouthOpenness = 1.0F;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  813 */       this.mouthOpenness += (0.0F - this.mouthOpenness) * 0.7F - 0.05F;
/*      */       
/*  815 */       if (this.mouthOpenness < 0.0F)
/*      */       {
/*  817 */         this.mouthOpenness = 0.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void openHorseMouth() {
/*  824 */     if (!this.world.isRemote) {
/*      */       
/*  826 */       this.openMouthCounter = 1;
/*  827 */       setHorseWatchableBoolean(64, true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEatingHaystack(boolean p_110227_1_) {
/*  833 */     setHorseWatchableBoolean(16, p_110227_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRearing(boolean rearing) {
/*  838 */     if (rearing)
/*      */     {
/*  840 */       setEatingHaystack(false);
/*      */     }
/*      */     
/*  843 */     setHorseWatchableBoolean(32, rearing);
/*      */   }
/*      */ 
/*      */   
/*      */   private void makeHorseRear() {
/*  848 */     if (canPassengerSteer()) {
/*      */       
/*  850 */       this.jumpRearingCounter = 1;
/*  851 */       setRearing(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_190687_dF() {
/*  857 */     makeHorseRear();
/*  858 */     SoundEvent soundevent = getAngrySound();
/*      */     
/*  860 */     if (soundevent != null)
/*      */     {
/*  862 */       playSound(soundevent, getSoundVolume(), getSoundPitch());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setTamedBy(EntityPlayer player) {
/*  868 */     setOwnerUniqueId(player.getUniqueID());
/*  869 */     setHorseTamed(true);
/*      */     
/*  871 */     if (player instanceof EntityPlayerMP)
/*      */     {
/*  873 */       CriteriaTriggers.field_193136_w.func_193178_a((EntityPlayerMP)player, this);
/*      */     }
/*      */     
/*  876 */     this.world.setEntityState((Entity)this, (byte)7);
/*  877 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
/*  882 */     if (isBeingRidden() && canBeSteered() && isHorseSaddled()) {
/*      */       
/*  884 */       EntityLivingBase entitylivingbase = (EntityLivingBase)getControllingPassenger();
/*  885 */       this.rotationYaw = entitylivingbase.rotationYaw;
/*  886 */       this.prevRotationYaw = this.rotationYaw;
/*  887 */       this.rotationPitch = entitylivingbase.rotationPitch * 0.5F;
/*  888 */       setRotation(this.rotationYaw, this.rotationPitch);
/*  889 */       this.renderYawOffset = this.rotationYaw;
/*  890 */       this.rotationYawHead = this.renderYawOffset;
/*  891 */       p_191986_1_ = entitylivingbase.moveStrafing * 0.5F;
/*  892 */       p_191986_3_ = entitylivingbase.field_191988_bg;
/*      */       
/*  894 */       if (p_191986_3_ <= 0.0F) {
/*      */         
/*  896 */         p_191986_3_ *= 0.25F;
/*  897 */         this.gallopTime = 0;
/*      */       } 
/*      */       
/*  900 */       if (this.onGround && this.jumpPower == 0.0F && isRearing() && !this.allowStandSliding) {
/*      */         
/*  902 */         p_191986_1_ = 0.0F;
/*  903 */         p_191986_3_ = 0.0F;
/*      */       } 
/*      */       
/*  906 */       if (this.jumpPower > 0.0F && !isHorseJumping() && this.onGround) {
/*      */         
/*  908 */         this.motionY = getHorseJumpStrength() * this.jumpPower;
/*      */         
/*  910 */         if (isPotionActive(MobEffects.JUMP_BOOST))
/*      */         {
/*  912 */           this.motionY += ((getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
/*      */         }
/*      */         
/*  915 */         setHorseJumping(true);
/*  916 */         this.isAirBorne = true;
/*      */         
/*  918 */         if (p_191986_3_ > 0.0F) {
/*      */           
/*  920 */           float f = MathHelper.sin(this.rotationYaw * 0.017453292F);
/*  921 */           float f1 = MathHelper.cos(this.rotationYaw * 0.017453292F);
/*  922 */           this.motionX += (-0.4F * f * this.jumpPower);
/*  923 */           this.motionZ += (0.4F * f1 * this.jumpPower);
/*  924 */           playSound(SoundEvents.ENTITY_HORSE_JUMP, 0.4F, 1.0F);
/*      */         } 
/*      */         
/*  927 */         this.jumpPower = 0.0F;
/*      */       } 
/*      */       
/*  930 */       this.jumpMovementFactor = getAIMoveSpeed() * 0.1F;
/*      */       
/*  932 */       if (canPassengerSteer()) {
/*      */         
/*  934 */         setAIMoveSpeed((float)getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
/*  935 */         super.func_191986_a(p_191986_1_, p_191986_2_, p_191986_3_);
/*      */       }
/*  937 */       else if (entitylivingbase instanceof EntityPlayer) {
/*      */         
/*  939 */         this.motionX = 0.0D;
/*  940 */         this.motionY = 0.0D;
/*  941 */         this.motionZ = 0.0D;
/*      */       } 
/*      */       
/*  944 */       if (this.onGround) {
/*      */         
/*  946 */         this.jumpPower = 0.0F;
/*  947 */         setHorseJumping(false);
/*      */       } 
/*      */       
/*  950 */       this.prevLimbSwingAmount = this.limbSwingAmount;
/*  951 */       double d1 = this.posX - this.prevPosX;
/*  952 */       double d0 = this.posZ - this.prevPosZ;
/*  953 */       float f2 = MathHelper.sqrt(d1 * d1 + d0 * d0) * 4.0F;
/*      */       
/*  955 */       if (f2 > 1.0F)
/*      */       {
/*  957 */         f2 = 1.0F;
/*      */       }
/*      */       
/*  960 */       this.limbSwingAmount += (f2 - this.limbSwingAmount) * 0.4F;
/*  961 */       this.limbSwing += this.limbSwingAmount;
/*      */     }
/*      */     else {
/*      */       
/*  965 */       this.jumpMovementFactor = 0.02F;
/*  966 */       super.func_191986_a(p_191986_1_, p_191986_2_, p_191986_3_);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void func_190683_c(DataFixer p_190683_0_, Class<?> p_190683_1_) {
/*  972 */     EntityLiving.registerFixesMob(p_190683_0_, p_190683_1_);
/*  973 */     p_190683_0_.registerWalker(FixTypes.ENTITY, (IDataWalker)new ItemStackData(p_190683_1_, new String[] { "SaddleItem" }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  981 */     super.writeEntityToNBT(compound);
/*  982 */     compound.setBoolean("EatingHaystack", isEatingHaystack());
/*  983 */     compound.setBoolean("Bred", isBreeding());
/*  984 */     compound.setInteger("Temper", getTemper());
/*  985 */     compound.setBoolean("Tame", isTame());
/*      */     
/*  987 */     if (getOwnerUniqueId() != null)
/*      */     {
/*  989 */       compound.setString("OwnerUUID", getOwnerUniqueId().toString());
/*      */     }
/*      */     
/*  992 */     if (!this.horseChest.getStackInSlot(0).func_190926_b())
/*      */     {
/*  994 */       compound.setTag("SaddleItem", (NBTBase)this.horseChest.getStackInSlot(0).writeToNBT(new NBTTagCompound()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound compound) {
/*      */     String s;
/* 1003 */     super.readEntityFromNBT(compound);
/* 1004 */     setEatingHaystack(compound.getBoolean("EatingHaystack"));
/* 1005 */     setBreeding(compound.getBoolean("Bred"));
/* 1006 */     setTemper(compound.getInteger("Temper"));
/* 1007 */     setHorseTamed(compound.getBoolean("Tame"));
/*      */ 
/*      */     
/* 1010 */     if (compound.hasKey("OwnerUUID", 8)) {
/*      */       
/* 1012 */       s = compound.getString("OwnerUUID");
/*      */     }
/*      */     else {
/*      */       
/* 1016 */       String s1 = compound.getString("Owner");
/* 1017 */       s = PreYggdrasilConverter.convertMobOwnerIfNeeded(getServer(), s1);
/*      */     } 
/*      */     
/* 1020 */     if (!s.isEmpty())
/*      */     {
/* 1022 */       setOwnerUniqueId(UUID.fromString(s));
/*      */     }
/*      */     
/* 1025 */     IAttributeInstance iattributeinstance = getAttributeMap().getAttributeInstanceByName("Speed");
/*      */     
/* 1027 */     if (iattributeinstance != null)
/*      */     {
/* 1029 */       getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(iattributeinstance.getBaseValue() * 0.25D);
/*      */     }
/*      */     
/* 1032 */     if (compound.hasKey("SaddleItem", 10)) {
/*      */       
/* 1034 */       ItemStack itemstack = new ItemStack(compound.getCompoundTag("SaddleItem"));
/*      */       
/* 1036 */       if (itemstack.getItem() == Items.SADDLE)
/*      */       {
/* 1038 */         this.horseChest.setInventorySlotContents(0, itemstack);
/*      */       }
/*      */     } 
/*      */     
/* 1042 */     updateHorseSlots();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 1050 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canMate() {
/* 1058 */     return (!isBeingRidden() && !isRiding() && isTame() && !isChild() && getHealth() >= getMaxHealth() && isInLove());
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityAgeable createChild(EntityAgeable ageable) {
/* 1064 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void func_190681_a(EntityAgeable p_190681_1_, AbstractHorse p_190681_2_) {
/* 1069 */     double d0 = getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() + p_190681_1_.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getBaseValue() + getModifiedMaxHealth();
/* 1070 */     p_190681_2_.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(d0 / 3.0D);
/* 1071 */     double d1 = getEntityAttribute(JUMP_STRENGTH).getBaseValue() + p_190681_1_.getEntityAttribute(JUMP_STRENGTH).getBaseValue() + getModifiedJumpStrength();
/* 1072 */     p_190681_2_.getEntityAttribute(JUMP_STRENGTH).setBaseValue(d1 / 3.0D);
/* 1073 */     double d2 = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() + p_190681_1_.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() + getModifiedMovementSpeed();
/* 1074 */     p_190681_2_.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(d2 / 3.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeSteered() {
/* 1083 */     return getControllingPassenger() instanceof EntityLivingBase;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getGrassEatingAmount(float p_110258_1_) {
/* 1088 */     return this.prevHeadLean + (this.headLean - this.prevHeadLean) * p_110258_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getRearingAmount(float p_110223_1_) {
/* 1093 */     return this.prevRearingAmount + (this.rearingAmount - this.prevRearingAmount) * p_110223_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getMouthOpennessAngle(float p_110201_1_) {
/* 1098 */     return this.prevMouthOpenness + (this.mouthOpenness - this.prevMouthOpenness) * p_110201_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setJumpPower(int jumpPowerIn) {
/* 1103 */     if (isHorseSaddled()) {
/*      */       
/* 1105 */       if (jumpPowerIn < 0) {
/*      */         
/* 1107 */         jumpPowerIn = 0;
/*      */       }
/*      */       else {
/*      */         
/* 1111 */         this.allowStandSliding = true;
/* 1112 */         makeHorseRear();
/*      */       } 
/*      */       
/* 1115 */       if (jumpPowerIn >= 90) {
/*      */         
/* 1117 */         this.jumpPower = 1.0F;
/*      */       }
/*      */       else {
/*      */         
/* 1121 */         this.jumpPower = 0.4F + 0.4F * jumpPowerIn / 90.0F;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canJump() {
/* 1128 */     return isHorseSaddled();
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStartJump(int p_184775_1_) {
/* 1133 */     this.allowStandSliding = true;
/* 1134 */     makeHorseRear();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleStopJump() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void spawnHorseParticles(boolean p_110216_1_) {
/* 1146 */     EnumParticleTypes enumparticletypes = p_110216_1_ ? EnumParticleTypes.HEART : EnumParticleTypes.SMOKE_NORMAL;
/*      */     
/* 1148 */     for (int i = 0; i < 7; i++) {
/*      */       
/* 1150 */       double d0 = this.rand.nextGaussian() * 0.02D;
/* 1151 */       double d1 = this.rand.nextGaussian() * 0.02D;
/* 1152 */       double d2 = this.rand.nextGaussian() * 0.02D;
/* 1153 */       this.world.spawnParticle(enumparticletypes, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d0, d1, d2, new int[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/* 1159 */     if (id == 7) {
/*      */       
/* 1161 */       spawnHorseParticles(true);
/*      */     }
/* 1163 */     else if (id == 6) {
/*      */       
/* 1165 */       spawnHorseParticles(false);
/*      */     }
/*      */     else {
/*      */       
/* 1169 */       super.handleStatusUpdate(id);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updatePassenger(Entity passenger) {
/* 1175 */     super.updatePassenger(passenger);
/*      */     
/* 1177 */     if (passenger instanceof EntityLiving) {
/*      */       
/* 1179 */       EntityLiving entityliving = (EntityLiving)passenger;
/* 1180 */       this.renderYawOffset = entityliving.renderYawOffset;
/*      */     } 
/*      */     
/* 1183 */     if (this.prevRearingAmount > 0.0F) {
/*      */       
/* 1185 */       float f3 = MathHelper.sin(this.renderYawOffset * 0.017453292F);
/* 1186 */       float f = MathHelper.cos(this.renderYawOffset * 0.017453292F);
/* 1187 */       float f1 = 0.7F * this.prevRearingAmount;
/* 1188 */       float f2 = 0.15F * this.prevRearingAmount;
/* 1189 */       passenger.setPosition(this.posX + (f1 * f3), this.posY + getMountedYOffset() + passenger.getYOffset() + f2, this.posZ - (f1 * f));
/*      */       
/* 1191 */       if (passenger instanceof EntityLivingBase)
/*      */       {
/* 1193 */         ((EntityLivingBase)passenger).renderYawOffset = this.renderYawOffset;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getModifiedMaxHealth() {
/* 1203 */     return 15.0F + this.rand.nextInt(8) + this.rand.nextInt(9);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double getModifiedJumpStrength() {
/* 1211 */     return 0.4000000059604645D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D + this.rand.nextDouble() * 0.2D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double getModifiedMovementSpeed() {
/* 1219 */     return (0.44999998807907104D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D + this.rand.nextDouble() * 0.3D) * 0.25D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnLadder() {
/* 1227 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getEyeHeight() {
/* 1232 */     return this.height;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_190677_dK() {
/* 1237 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_190682_f(ItemStack p_190682_1_) {
/* 1242 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/* 1247 */     int i = inventorySlot - 400;
/*      */     
/* 1249 */     if (i >= 0 && i < 2 && i < this.horseChest.getSizeInventory()) {
/*      */       
/* 1251 */       if (i == 0 && itemStackIn.getItem() != Items.SADDLE)
/*      */       {
/* 1253 */         return false;
/*      */       }
/* 1255 */       if (i != 1 || (func_190677_dK() && func_190682_f(itemStackIn))) {
/*      */         
/* 1257 */         this.horseChest.setInventorySlotContents(i, itemStackIn);
/* 1258 */         updateHorseSlots();
/* 1259 */         return true;
/*      */       } 
/*      */ 
/*      */       
/* 1263 */       return false;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1268 */     int j = inventorySlot - 500 + 2;
/*      */     
/* 1270 */     if (j >= 2 && j < this.horseChest.getSizeInventory()) {
/*      */       
/* 1272 */       this.horseChest.setInventorySlotContents(j, itemStackIn);
/* 1273 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1277 */     return false;
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
/*      */   @Nullable
/*      */   public Entity getControllingPassenger() {
/* 1290 */     return getPassengers().isEmpty() ? null : getPassengers().get(0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
/* 1301 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/*      */     
/* 1303 */     if (this.rand.nextInt(5) == 0)
/*      */     {
/* 1305 */       setGrowingAge(-24000);
/*      */     }
/*      */     
/* 1308 */     return livingdata;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\AbstractHorse.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */