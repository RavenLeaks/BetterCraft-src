/*     */ package net.minecraft.entity.passive;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockCarrot;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackMelee;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIMoveToBlock;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITempt;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.EntityJumpHelper;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.pathfinding.Path;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityRabbit extends EntityAnimal {
/*  56 */   private static final DataParameter<Integer> RABBIT_TYPE = EntityDataManager.createKey(EntityRabbit.class, DataSerializers.VARINT);
/*     */   
/*     */   private int jumpTicks;
/*     */   private int jumpDuration;
/*     */   private boolean wasOnGround;
/*     */   private int currentMoveTypeDuration;
/*     */   private int carrotTicks;
/*     */   
/*     */   public EntityRabbit(World worldIn) {
/*  65 */     super(worldIn);
/*  66 */     setSize(0.4F, 0.5F);
/*  67 */     this.jumpHelper = new RabbitJumpHelper(this);
/*  68 */     this.moveHelper = new RabbitMoveHelper(this);
/*  69 */     setMovementSpeed(0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  74 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  75 */     this.tasks.addTask(1, (EntityAIBase)new AIPanic(this, 2.2D));
/*  76 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMate(this, 0.8D));
/*  77 */     this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.0D, Items.CARROT, false));
/*  78 */     this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.0D, Items.GOLDEN_CARROT, false));
/*  79 */     this.tasks.addTask(3, (EntityAIBase)new EntityAITempt((EntityCreature)this, 1.0D, Item.getItemFromBlock((Block)Blocks.YELLOW_FLOWER), false));
/*  80 */     this.tasks.addTask(4, (EntityAIBase)new AIAvoidEntity<>(this, EntityPlayer.class, 8.0F, 2.2D, 2.2D));
/*  81 */     this.tasks.addTask(4, (EntityAIBase)new AIAvoidEntity<>(this, EntityWolf.class, 10.0F, 2.2D, 2.2D));
/*  82 */     this.tasks.addTask(4, (EntityAIBase)new AIAvoidEntity<>(this, EntityMob.class, 4.0F, 2.2D, 2.2D));
/*  83 */     this.tasks.addTask(5, (EntityAIBase)new AIRaidFarm(this));
/*  84 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 0.6D));
/*  85 */     this.tasks.addTask(11, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 10.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getJumpUpwardsMotion() {
/*  90 */     if (!this.isCollidedHorizontally && (!this.moveHelper.isUpdating() || this.moveHelper.getY() <= this.posY + 0.5D)) {
/*     */       
/*  92 */       Path path = this.navigator.getPath();
/*     */       
/*  94 */       if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength()) {
/*     */         
/*  96 */         Vec3d vec3d = path.getPosition((Entity)this);
/*     */         
/*  98 */         if (vec3d.yCoord > this.posY + 0.5D)
/*     */         {
/* 100 */           return 0.5F;
/*     */         }
/*     */       } 
/*     */       
/* 104 */       return (this.moveHelper.getSpeed() <= 0.6D) ? 0.2F : 0.3F;
/*     */     } 
/*     */ 
/*     */     
/* 108 */     return 0.5F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void jump() {
/* 117 */     super.jump();
/* 118 */     double d0 = this.moveHelper.getSpeed();
/*     */     
/* 120 */     if (d0 > 0.0D) {
/*     */       
/* 122 */       double d1 = this.motionX * this.motionX + this.motionZ * this.motionZ;
/*     */       
/* 124 */       if (d1 < 0.010000000000000002D)
/*     */       {
/* 126 */         func_191958_b(0.0F, 0.0F, 1.0F, 0.1F);
/*     */       }
/*     */     } 
/*     */     
/* 130 */     if (!this.world.isRemote)
/*     */     {
/* 132 */       this.world.setEntityState((Entity)this, (byte)1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public float setJumpCompletion(float p_175521_1_) {
/* 138 */     return (this.jumpDuration == 0) ? 0.0F : ((this.jumpTicks + p_175521_1_) / this.jumpDuration);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMovementSpeed(double newSpeed) {
/* 143 */     getNavigator().setSpeed(newSpeed);
/* 144 */     this.moveHelper.setMoveTo(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ(), newSpeed);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setJumping(boolean jumping) {
/* 149 */     super.setJumping(jumping);
/*     */     
/* 151 */     if (jumping)
/*     */     {
/* 153 */       playSound(getJumpSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 0.8F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void startJumping() {
/* 159 */     setJumping(true);
/* 160 */     this.jumpDuration = 10;
/* 161 */     this.jumpTicks = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 166 */     super.entityInit();
/* 167 */     this.dataManager.register(RABBIT_TYPE, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAITasks() {
/* 172 */     if (this.currentMoveTypeDuration > 0)
/*     */     {
/* 174 */       this.currentMoveTypeDuration--;
/*     */     }
/*     */     
/* 177 */     if (this.carrotTicks > 0) {
/*     */       
/* 179 */       this.carrotTicks -= this.rand.nextInt(3);
/*     */       
/* 181 */       if (this.carrotTicks < 0)
/*     */       {
/* 183 */         this.carrotTicks = 0;
/*     */       }
/*     */     } 
/*     */     
/* 187 */     if (this.onGround) {
/*     */       
/* 189 */       if (!this.wasOnGround) {
/*     */         
/* 191 */         setJumping(false);
/* 192 */         checkLandingDelay();
/*     */       } 
/*     */       
/* 195 */       if (getRabbitType() == 99 && this.currentMoveTypeDuration == 0) {
/*     */         
/* 197 */         EntityLivingBase entitylivingbase = getAttackTarget();
/*     */         
/* 199 */         if (entitylivingbase != null && getDistanceSqToEntity((Entity)entitylivingbase) < 16.0D) {
/*     */           
/* 201 */           calculateRotationYaw(entitylivingbase.posX, entitylivingbase.posZ);
/* 202 */           this.moveHelper.setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, this.moveHelper.getSpeed());
/* 203 */           startJumping();
/* 204 */           this.wasOnGround = true;
/*     */         } 
/*     */       } 
/*     */       
/* 208 */       RabbitJumpHelper entityrabbit$rabbitjumphelper = (RabbitJumpHelper)this.jumpHelper;
/*     */       
/* 210 */       if (!entityrabbit$rabbitjumphelper.getIsJumping()) {
/*     */         
/* 212 */         if (this.moveHelper.isUpdating() && this.currentMoveTypeDuration == 0)
/*     */         {
/* 214 */           Path path = this.navigator.getPath();
/* 215 */           Vec3d vec3d = new Vec3d(this.moveHelper.getX(), this.moveHelper.getY(), this.moveHelper.getZ());
/*     */           
/* 217 */           if (path != null && path.getCurrentPathIndex() < path.getCurrentPathLength())
/*     */           {
/* 219 */             vec3d = path.getPosition((Entity)this);
/*     */           }
/*     */           
/* 222 */           calculateRotationYaw(vec3d.xCoord, vec3d.zCoord);
/* 223 */           startJumping();
/*     */         }
/*     */       
/* 226 */       } else if (!entityrabbit$rabbitjumphelper.canJump()) {
/*     */         
/* 228 */         enableJumpControl();
/*     */       } 
/*     */     } 
/*     */     
/* 232 */     this.wasOnGround = this.onGround;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void spawnRunningParticles() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void calculateRotationYaw(double x, double z) {
/* 244 */     this.rotationYaw = (float)(MathHelper.atan2(z - this.posZ, x - this.posX) * 57.29577951308232D) - 90.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private void enableJumpControl() {
/* 249 */     ((RabbitJumpHelper)this.jumpHelper).setCanJump(true);
/*     */   }
/*     */ 
/*     */   
/*     */   private void disableJumpControl() {
/* 254 */     ((RabbitJumpHelper)this.jumpHelper).setCanJump(false);
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateMoveTypeDuration() {
/* 259 */     if (this.moveHelper.getSpeed() < 2.2D) {
/*     */       
/* 261 */       this.currentMoveTypeDuration = 10;
/*     */     }
/*     */     else {
/*     */       
/* 265 */       this.currentMoveTypeDuration = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkLandingDelay() {
/* 271 */     updateMoveTypeDuration();
/* 272 */     disableJumpControl();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 281 */     super.onLivingUpdate();
/*     */     
/* 283 */     if (this.jumpTicks != this.jumpDuration) {
/*     */       
/* 285 */       this.jumpTicks++;
/*     */     }
/* 287 */     else if (this.jumpDuration != 0) {
/*     */       
/* 289 */       this.jumpTicks = 0;
/* 290 */       this.jumpDuration = 0;
/* 291 */       setJumping(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 297 */     super.applyEntityAttributes();
/* 298 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(3.0D);
/* 299 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesRabbit(DataFixer fixer) {
/* 304 */     EntityLiving.registerFixesMob(fixer, EntityRabbit.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 312 */     super.writeEntityToNBT(compound);
/* 313 */     compound.setInteger("RabbitType", getRabbitType());
/* 314 */     compound.setInteger("MoreCarrotTicks", this.carrotTicks);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 322 */     super.readEntityFromNBT(compound);
/* 323 */     setRabbitType(compound.getInteger("RabbitType"));
/* 324 */     this.carrotTicks = compound.getInteger("MoreCarrotTicks");
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getJumpSound() {
/* 329 */     return SoundEvents.ENTITY_RABBIT_JUMP;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 334 */     return SoundEvents.ENTITY_RABBIT_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 339 */     return SoundEvents.ENTITY_RABBIT_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 344 */     return SoundEvents.ENTITY_RABBIT_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 349 */     if (getRabbitType() == 99) {
/*     */       
/* 351 */       playSound(SoundEvents.ENTITY_RABBIT_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/* 352 */       return entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 8.0F);
/*     */     } 
/*     */ 
/*     */     
/* 356 */     return entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 3.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SoundCategory getSoundCategory() {
/* 362 */     return (getRabbitType() == 99) ? SoundCategory.HOSTILE : SoundCategory.NEUTRAL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 370 */     return isEntityInvulnerable(source) ? false : super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 376 */     return LootTableList.ENTITIES_RABBIT;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isRabbitBreedingItem(Item itemIn) {
/* 381 */     return !(itemIn != Items.CARROT && itemIn != Items.GOLDEN_CARROT && itemIn != Item.getItemFromBlock((Block)Blocks.YELLOW_FLOWER));
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityRabbit createChild(EntityAgeable ageable) {
/* 386 */     EntityRabbit entityrabbit = new EntityRabbit(this.world);
/* 387 */     int i = getRandomRabbitType();
/*     */     
/* 389 */     if (this.rand.nextInt(20) != 0)
/*     */     {
/* 391 */       if (ageable instanceof EntityRabbit && this.rand.nextBoolean()) {
/*     */         
/* 393 */         i = ((EntityRabbit)ageable).getRabbitType();
/*     */       }
/*     */       else {
/*     */         
/* 397 */         i = getRabbitType();
/*     */       } 
/*     */     }
/*     */     
/* 401 */     entityrabbit.setRabbitType(i);
/* 402 */     return entityrabbit;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 411 */     return isRabbitBreedingItem(stack.getItem());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRabbitType() {
/* 416 */     return ((Integer)this.dataManager.get(RABBIT_TYPE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRabbitType(int rabbitTypeId) {
/* 421 */     if (rabbitTypeId == 99) {
/*     */       
/* 423 */       getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(8.0D);
/* 424 */       this.tasks.addTask(4, (EntityAIBase)new AIEvilAttack(this));
/* 425 */       this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
/* 426 */       this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityPlayer.class, true));
/* 427 */       this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityWolf.class, true));
/*     */       
/* 429 */       if (!hasCustomName())
/*     */       {
/* 431 */         setCustomNameTag(I18n.translateToLocal("entity.KillerBunny.name"));
/*     */       }
/*     */     } 
/*     */     
/* 435 */     this.dataManager.set(RABBIT_TYPE, Integer.valueOf(rabbitTypeId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
/* 446 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/* 447 */     int i = getRandomRabbitType();
/* 448 */     boolean flag = false;
/*     */     
/* 450 */     if (livingdata instanceof RabbitTypeData) {
/*     */       
/* 452 */       i = ((RabbitTypeData)livingdata).typeData;
/* 453 */       flag = true;
/*     */     }
/*     */     else {
/*     */       
/* 457 */       livingdata = new RabbitTypeData(i);
/*     */     } 
/*     */     
/* 460 */     setRabbitType(i);
/*     */     
/* 462 */     if (flag)
/*     */     {
/* 464 */       setGrowingAge(-24000);
/*     */     }
/*     */     
/* 467 */     return livingdata;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getRandomRabbitType() {
/* 472 */     Biome biome = this.world.getBiome(new BlockPos((Entity)this));
/* 473 */     int i = this.rand.nextInt(100);
/*     */     
/* 475 */     if (biome.isSnowyBiome())
/*     */     {
/* 477 */       return (i < 80) ? 1 : 3;
/*     */     }
/* 479 */     if (biome instanceof net.minecraft.world.biome.BiomeDesert)
/*     */     {
/* 481 */       return 4;
/*     */     }
/*     */ 
/*     */     
/* 485 */     return (i < 50) ? 0 : ((i < 90) ? 5 : 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isCarrotEaten() {
/* 494 */     return (this.carrotTicks == 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createEatingParticles() {
/* 499 */     BlockCarrot blockcarrot = (BlockCarrot)Blocks.CARROTS;
/* 500 */     IBlockState iblockstate = blockcarrot.withAge(blockcarrot.getMaxAge());
/* 501 */     this.world.spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + 0.5D + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, 0.0D, 0.0D, 0.0D, new int[] { Block.getStateId(iblockstate) });
/* 502 */     this.carrotTicks = 40;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 507 */     if (id == 1) {
/*     */       
/* 509 */       createRunningParticles();
/* 510 */       this.jumpDuration = 10;
/* 511 */       this.jumpTicks = 0;
/*     */     }
/*     */     else {
/*     */       
/* 515 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */   
/*     */   static class AIAvoidEntity<T extends Entity>
/*     */     extends EntityAIAvoidEntity<T>
/*     */   {
/*     */     private final EntityRabbit entityInstance;
/*     */     
/*     */     public AIAvoidEntity(EntityRabbit rabbit, Class<T> p_i46403_2_, float p_i46403_3_, double p_i46403_4_, double p_i46403_6_) {
/* 525 */       super((EntityCreature)rabbit, p_i46403_2_, p_i46403_3_, p_i46403_4_, p_i46403_6_);
/* 526 */       this.entityInstance = rabbit;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 531 */       return (this.entityInstance.getRabbitType() != 99 && super.shouldExecute());
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIEvilAttack
/*     */     extends EntityAIAttackMelee
/*     */   {
/*     */     public AIEvilAttack(EntityRabbit rabbit) {
/* 539 */       super((EntityCreature)rabbit, 1.4D, true);
/*     */     }
/*     */ 
/*     */     
/*     */     protected double getAttackReachSqr(EntityLivingBase attackTarget) {
/* 544 */       return (4.0F + attackTarget.width);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIPanic
/*     */     extends EntityAIPanic
/*     */   {
/*     */     private final EntityRabbit theEntity;
/*     */     
/*     */     public AIPanic(EntityRabbit rabbit, double speedIn) {
/* 554 */       super((EntityCreature)rabbit, speedIn);
/* 555 */       this.theEntity = rabbit;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 560 */       super.updateTask();
/* 561 */       this.theEntity.setMovementSpeed(this.speed);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIRaidFarm
/*     */     extends EntityAIMoveToBlock
/*     */   {
/*     */     private final EntityRabbit rabbit;
/*     */     private boolean wantsToRaid;
/*     */     private boolean canRaid;
/*     */     
/*     */     public AIRaidFarm(EntityRabbit rabbitIn) {
/* 573 */       super((EntityCreature)rabbitIn, 0.699999988079071D, 16);
/* 574 */       this.rabbit = rabbitIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 579 */       if (this.runDelay <= 0) {
/*     */         
/* 581 */         if (!this.rabbit.world.getGameRules().getBoolean("mobGriefing"))
/*     */         {
/* 583 */           return false;
/*     */         }
/*     */         
/* 586 */         this.canRaid = false;
/* 587 */         this.wantsToRaid = this.rabbit.isCarrotEaten();
/* 588 */         this.wantsToRaid = true;
/*     */       } 
/*     */       
/* 591 */       return super.shouldExecute();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 596 */       return (this.canRaid && super.continueExecuting());
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 601 */       super.updateTask();
/* 602 */       this.rabbit.getLookHelper().setLookPosition(this.destinationBlock.getX() + 0.5D, (this.destinationBlock.getY() + 1), this.destinationBlock.getZ() + 0.5D, 10.0F, this.rabbit.getVerticalFaceSpeed());
/*     */       
/* 604 */       if (getIsAboveDestination()) {
/*     */         
/* 606 */         World world = this.rabbit.world;
/* 607 */         BlockPos blockpos = this.destinationBlock.up();
/* 608 */         IBlockState iblockstate = world.getBlockState(blockpos);
/* 609 */         Block block = iblockstate.getBlock();
/*     */         
/* 611 */         if (this.canRaid && block instanceof BlockCarrot) {
/*     */           
/* 613 */           Integer integer = (Integer)iblockstate.getValue((IProperty)BlockCarrot.AGE);
/*     */           
/* 615 */           if (integer.intValue() == 0) {
/*     */             
/* 617 */             world.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
/* 618 */             world.destroyBlock(blockpos, true);
/*     */           }
/*     */           else {
/*     */             
/* 622 */             world.setBlockState(blockpos, iblockstate.withProperty((IProperty)BlockCarrot.AGE, Integer.valueOf(integer.intValue() - 1)), 2);
/* 623 */             world.playEvent(2001, blockpos, Block.getStateId(iblockstate));
/*     */           } 
/*     */           
/* 626 */           this.rabbit.createEatingParticles();
/*     */         } 
/*     */         
/* 629 */         this.canRaid = false;
/* 630 */         this.runDelay = 10;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean shouldMoveTo(World worldIn, BlockPos pos) {
/* 636 */       Block block = worldIn.getBlockState(pos).getBlock();
/*     */       
/* 638 */       if (block == Blocks.FARMLAND && this.wantsToRaid && !this.canRaid) {
/*     */         
/* 640 */         pos = pos.up();
/* 641 */         IBlockState iblockstate = worldIn.getBlockState(pos);
/* 642 */         block = iblockstate.getBlock();
/*     */         
/* 644 */         if (block instanceof BlockCarrot && ((BlockCarrot)block).isMaxAge(iblockstate)) {
/*     */           
/* 646 */           this.canRaid = true;
/* 647 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 651 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   public class RabbitJumpHelper
/*     */     extends EntityJumpHelper
/*     */   {
/*     */     private final EntityRabbit theEntity;
/*     */     private boolean canJump;
/*     */     
/*     */     public RabbitJumpHelper(EntityRabbit rabbit) {
/* 662 */       super((EntityLiving)rabbit);
/* 663 */       this.theEntity = rabbit;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean getIsJumping() {
/* 668 */       return this.isJumping;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canJump() {
/* 673 */       return this.canJump;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setCanJump(boolean canJumpIn) {
/* 678 */       this.canJump = canJumpIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public void doJump() {
/* 683 */       if (this.isJumping) {
/*     */         
/* 685 */         this.theEntity.startJumping();
/* 686 */         this.isJumping = false;
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class RabbitMoveHelper
/*     */     extends EntityMoveHelper
/*     */   {
/*     */     private final EntityRabbit theEntity;
/*     */     private double nextJumpSpeed;
/*     */     
/*     */     public RabbitMoveHelper(EntityRabbit rabbit) {
/* 698 */       super((EntityLiving)rabbit);
/* 699 */       this.theEntity = rabbit;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUpdateMoveHelper() {
/* 704 */       if (this.theEntity.onGround && !this.theEntity.isJumping && !((EntityRabbit.RabbitJumpHelper)this.theEntity.jumpHelper).getIsJumping()) {
/*     */         
/* 706 */         this.theEntity.setMovementSpeed(0.0D);
/*     */       }
/* 708 */       else if (isUpdating()) {
/*     */         
/* 710 */         this.theEntity.setMovementSpeed(this.nextJumpSpeed);
/*     */       } 
/*     */       
/* 713 */       super.onUpdateMoveHelper();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setMoveTo(double x, double y, double z, double speedIn) {
/* 718 */       if (this.theEntity.isInWater())
/*     */       {
/* 720 */         speedIn = 1.5D;
/*     */       }
/*     */       
/* 723 */       super.setMoveTo(x, y, z, speedIn);
/*     */       
/* 725 */       if (speedIn > 0.0D)
/*     */       {
/* 727 */         this.nextJumpSpeed = speedIn;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static class RabbitTypeData
/*     */     implements IEntityLivingData
/*     */   {
/*     */     public int typeData;
/*     */     
/*     */     public RabbitTypeData(int type) {
/* 738 */       this.typeData = type;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityRabbit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */