/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIBreakDoor;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.EntityAIZombieAttack;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttribute;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*     */ import net.minecraft.entity.passive.EntityChicken;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ 
/*     */ public class EntityZombie
/*     */   extends EntityMob
/*     */ {
/*  58 */   protected static final IAttribute SPAWN_REINFORCEMENTS_CHANCE = (IAttribute)(new RangedAttribute(null, "zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).setDescription("Spawn Reinforcements Chance");
/*  59 */   private static final UUID BABY_SPEED_BOOST_ID = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
/*  60 */   private static final AttributeModifier BABY_SPEED_BOOST = new AttributeModifier(BABY_SPEED_BOOST_ID, "Baby speed boost", 0.5D, 1);
/*  61 */   private static final DataParameter<Boolean> IS_CHILD = EntityDataManager.createKey(EntityZombie.class, DataSerializers.BOOLEAN);
/*  62 */   private static final DataParameter<Integer> VILLAGER_TYPE = EntityDataManager.createKey(EntityZombie.class, DataSerializers.VARINT);
/*  63 */   private static final DataParameter<Boolean> ARMS_RAISED = EntityDataManager.createKey(EntityZombie.class, DataSerializers.BOOLEAN);
/*  64 */   private final EntityAIBreakDoor breakDoor = new EntityAIBreakDoor((EntityLiving)this);
/*     */   
/*     */   private boolean isBreakDoorsTaskSet;
/*     */   
/*  68 */   private float zombieWidth = -1.0F;
/*     */ 
/*     */   
/*     */   private float zombieHeight;
/*     */ 
/*     */   
/*     */   public EntityZombie(World worldIn) {
/*  75 */     super(worldIn);
/*  76 */     setSize(0.6F, 1.95F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  81 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  82 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIZombieAttack(this, 1.0D, false));
/*  83 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIMoveTowardsRestriction(this, 1.0D));
/*  84 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWanderAvoidWater(this, 1.0D));
/*  85 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  86 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  87 */     applyEntityAI();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAI() {
/*  92 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIMoveThroughVillage(this, 1.0D, false));
/*  93 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[] { EntityPigZombie.class }));
/*  94 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  95 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityVillager.class, false));
/*  96 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityIronGolem.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 101 */     super.applyEntityAttributes();
/* 102 */     getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(35.0D);
/* 103 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
/* 104 */     getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
/* 105 */     getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
/* 106 */     getAttributeMap().registerAttribute(SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(this.rand.nextDouble() * 0.10000000149011612D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 111 */     super.entityInit();
/* 112 */     getDataManager().register(IS_CHILD, Boolean.valueOf(false));
/* 113 */     getDataManager().register(VILLAGER_TYPE, Integer.valueOf(0));
/* 114 */     getDataManager().register(ARMS_RAISED, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setArmsRaised(boolean armsRaised) {
/* 119 */     getDataManager().set(ARMS_RAISED, Boolean.valueOf(armsRaised));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isArmsRaised() {
/* 124 */     return ((Boolean)getDataManager().get(ARMS_RAISED)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBreakDoorsTaskSet() {
/* 129 */     return this.isBreakDoorsTaskSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBreakDoorsAItask(boolean enabled) {
/* 137 */     if (this.isBreakDoorsTaskSet != enabled) {
/*     */       
/* 139 */       this.isBreakDoorsTaskSet = enabled;
/* 140 */       ((PathNavigateGround)getNavigator()).setBreakDoors(enabled);
/*     */       
/* 142 */       if (enabled) {
/*     */         
/* 144 */         this.tasks.addTask(1, (EntityAIBase)this.breakDoor);
/*     */       }
/*     */       else {
/*     */         
/* 148 */         this.tasks.removeTask((EntityAIBase)this.breakDoor);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChild() {
/* 158 */     return ((Boolean)getDataManager().get(IS_CHILD)).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getExperiencePoints(EntityPlayer player) {
/* 166 */     if (isChild())
/*     */     {
/* 168 */       this.experienceValue = (int)(this.experienceValue * 2.5F);
/*     */     }
/*     */     
/* 171 */     return super.getExperiencePoints(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChild(boolean childZombie) {
/* 179 */     getDataManager().set(IS_CHILD, Boolean.valueOf(childZombie));
/*     */     
/* 181 */     if (this.world != null && !this.world.isRemote) {
/*     */       
/* 183 */       IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
/* 184 */       iattributeinstance.removeModifier(BABY_SPEED_BOOST);
/*     */       
/* 186 */       if (childZombie)
/*     */       {
/* 188 */         iattributeinstance.applyModifier(BABY_SPEED_BOOST);
/*     */       }
/*     */     } 
/*     */     
/* 192 */     setChildSize(childZombie);
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 197 */     if (IS_CHILD.equals(key))
/*     */     {
/* 199 */       setChildSize(isChild());
/*     */     }
/*     */     
/* 202 */     super.notifyDataManagerChange(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 211 */     if (this.world.isDaytime() && !this.world.isRemote && !isChild() && func_190730_o()) {
/*     */       
/* 213 */       float f = getBrightness();
/*     */       
/* 215 */       if (f > 0.5F && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.canSeeSky(new BlockPos(this.posX, this.posY + getEyeHeight(), this.posZ))) {
/*     */         
/* 217 */         boolean flag = true;
/* 218 */         ItemStack itemstack = getItemStackFromSlot(EntityEquipmentSlot.HEAD);
/*     */         
/* 220 */         if (!itemstack.func_190926_b()) {
/*     */           
/* 222 */           if (itemstack.isItemStackDamageable()) {
/*     */             
/* 224 */             itemstack.setItemDamage(itemstack.getItemDamage() + this.rand.nextInt(2));
/*     */             
/* 226 */             if (itemstack.getItemDamage() >= itemstack.getMaxDamage()) {
/*     */               
/* 228 */               renderBrokenItemStack(itemstack);
/* 229 */               setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.field_190927_a);
/*     */             } 
/*     */           } 
/*     */           
/* 233 */           flag = false;
/*     */         } 
/*     */         
/* 236 */         if (flag)
/*     */         {
/* 238 */           setFire(8);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 243 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_190730_o() {
/* 248 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 256 */     if (super.attackEntityFrom(source, amount)) {
/*     */       
/* 258 */       EntityLivingBase entitylivingbase = getAttackTarget();
/*     */       
/* 260 */       if (entitylivingbase == null && source.getEntity() instanceof EntityLivingBase)
/*     */       {
/* 262 */         entitylivingbase = (EntityLivingBase)source.getEntity();
/*     */       }
/*     */       
/* 265 */       if (entitylivingbase != null && this.world.getDifficulty() == EnumDifficulty.HARD && this.rand.nextFloat() < getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).getAttributeValue() && this.world.getGameRules().getBoolean("doMobSpawning")) {
/*     */         
/* 267 */         int i = MathHelper.floor(this.posX);
/* 268 */         int j = MathHelper.floor(this.posY);
/* 269 */         int k = MathHelper.floor(this.posZ);
/* 270 */         EntityZombie entityzombie = new EntityZombie(this.world);
/*     */         
/* 272 */         for (int l = 0; l < 50; l++) {
/*     */           
/* 274 */           int i1 = i + MathHelper.getInt(this.rand, 7, 40) * MathHelper.getInt(this.rand, -1, 1);
/* 275 */           int j1 = j + MathHelper.getInt(this.rand, 7, 40) * MathHelper.getInt(this.rand, -1, 1);
/* 276 */           int k1 = k + MathHelper.getInt(this.rand, 7, 40) * MathHelper.getInt(this.rand, -1, 1);
/*     */           
/* 278 */           if (this.world.getBlockState(new BlockPos(i1, j1 - 1, k1)).isFullyOpaque() && this.world.getLightFromNeighbors(new BlockPos(i1, j1, k1)) < 10) {
/*     */             
/* 280 */             entityzombie.setPosition(i1, j1, k1);
/*     */             
/* 282 */             if (!this.world.isAnyPlayerWithinRangeAt(i1, j1, k1, 7.0D) && this.world.checkNoEntityCollision(entityzombie.getEntityBoundingBox(), (Entity)entityzombie) && this.world.getCollisionBoxes((Entity)entityzombie, entityzombie.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(entityzombie.getEntityBoundingBox())) {
/*     */               
/* 284 */               this.world.spawnEntityInWorld((Entity)entityzombie);
/* 285 */               entityzombie.setAttackTarget(entitylivingbase);
/* 286 */               entityzombie.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)entityzombie)), (IEntityLivingData)null);
/* 287 */               getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
/* 288 */               entityzombie.getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/* 295 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 299 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 305 */     boolean flag = super.attackEntityAsMob(entityIn);
/*     */     
/* 307 */     if (flag) {
/*     */       
/* 309 */       float f = this.world.getDifficultyForLocation(new BlockPos((Entity)this)).getAdditionalDifficulty();
/*     */       
/* 311 */       if (getHeldItemMainhand().func_190926_b() && isBurning() && this.rand.nextFloat() < f * 0.3F)
/*     */       {
/* 313 */         entityIn.setFire(2 * (int)f);
/*     */       }
/*     */     } 
/*     */     
/* 317 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 322 */     return SoundEvents.ENTITY_ZOMBIE_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 327 */     return SoundEvents.ENTITY_ZOMBIE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 332 */     return SoundEvents.ENTITY_ZOMBIE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent func_190731_di() {
/* 337 */     return SoundEvents.ENTITY_ZOMBIE_STEP;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 342 */     playSound(func_190731_di(), 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 350 */     return EnumCreatureAttribute.UNDEAD;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 356 */     return LootTableList.ENTITIES_ZOMBIE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 364 */     super.setEquipmentBasedOnDifficulty(difficulty);
/*     */     
/* 366 */     if (this.rand.nextFloat() < ((this.world.getDifficulty() == EnumDifficulty.HARD) ? 0.05F : 0.01F)) {
/*     */       
/* 368 */       int i = this.rand.nextInt(3);
/*     */       
/* 370 */       if (i == 0) {
/*     */         
/* 372 */         setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
/*     */       }
/*     */       else {
/*     */         
/* 376 */         setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SHOVEL));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesZombie(DataFixer fixer) {
/* 383 */     EntityLiving.registerFixesMob(fixer, EntityZombie.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 391 */     super.writeEntityToNBT(compound);
/*     */     
/* 393 */     if (isChild())
/*     */     {
/* 395 */       compound.setBoolean("IsBaby", true);
/*     */     }
/*     */     
/* 398 */     compound.setBoolean("CanBreakDoors", isBreakDoorsTaskSet());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 406 */     super.readEntityFromNBT(compound);
/*     */     
/* 408 */     if (compound.getBoolean("IsBaby"))
/*     */     {
/* 410 */       setChild(true);
/*     */     }
/*     */     
/* 413 */     setBreakDoorsAItask(compound.getBoolean("CanBreakDoors"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onKillEntity(EntityLivingBase entityLivingIn) {
/* 421 */     super.onKillEntity(entityLivingIn);
/*     */     
/* 423 */     if ((this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD) && entityLivingIn instanceof EntityVillager) {
/*     */       
/* 425 */       if (this.world.getDifficulty() != EnumDifficulty.HARD && this.rand.nextBoolean()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 430 */       EntityVillager entityvillager = (EntityVillager)entityLivingIn;
/* 431 */       EntityZombieVillager entityzombievillager = new EntityZombieVillager(this.world);
/* 432 */       entityzombievillager.copyLocationAndAnglesFrom((Entity)entityvillager);
/* 433 */       this.world.removeEntity((Entity)entityvillager);
/* 434 */       entityzombievillager.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos((Entity)entityzombievillager)), new GroupData(false, null));
/* 435 */       entityzombievillager.func_190733_a(entityvillager.getProfession());
/* 436 */       entityzombievillager.setChild(entityvillager.isChild());
/* 437 */       entityzombievillager.setNoAI(entityvillager.isAIDisabled());
/*     */       
/* 439 */       if (entityvillager.hasCustomName()) {
/*     */         
/* 441 */         entityzombievillager.setCustomNameTag(entityvillager.getCustomNameTag());
/* 442 */         entityzombievillager.setAlwaysRenderNameTag(entityvillager.getAlwaysRenderNameTag());
/*     */       } 
/*     */       
/* 445 */       this.world.spawnEntityInWorld((Entity)entityzombievillager);
/* 446 */       this.world.playEvent(null, 1026, new BlockPos((Entity)this), 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 452 */     float f = 1.74F;
/*     */     
/* 454 */     if (isChild())
/*     */     {
/* 456 */       f = (float)(f - 0.81D);
/*     */     }
/*     */     
/* 459 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canEquipItem(ItemStack stack) {
/* 464 */     return (stack.getItem() == Items.EGG && isChild() && isRiding()) ? false : super.canEquipItem(stack);
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
/* 475 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/* 476 */     float f = difficulty.getClampedAdditionalDifficulty();
/* 477 */     setCanPickUpLoot((this.rand.nextFloat() < 0.55F * f));
/*     */     
/* 479 */     if (livingdata == null)
/*     */     {
/* 481 */       livingdata = new GroupData((this.world.rand.nextFloat() < 0.05F), null);
/*     */     }
/*     */     
/* 484 */     if (livingdata instanceof GroupData) {
/*     */       
/* 486 */       GroupData entityzombie$groupdata = (GroupData)livingdata;
/*     */       
/* 488 */       if (entityzombie$groupdata.isChild) {
/*     */         
/* 490 */         setChild(true);
/*     */         
/* 492 */         if (this.world.rand.nextFloat() < 0.05D) {
/*     */           
/* 494 */           List<EntityChicken> list = this.world.getEntitiesWithinAABB(EntityChicken.class, getEntityBoundingBox().expand(5.0D, 3.0D, 5.0D), EntitySelectors.IS_STANDALONE);
/*     */           
/* 496 */           if (!list.isEmpty())
/*     */           {
/* 498 */             EntityChicken entitychicken = list.get(0);
/* 499 */             entitychicken.setChickenJockey(true);
/* 500 */             startRiding((Entity)entitychicken);
/*     */           }
/*     */         
/* 503 */         } else if (this.world.rand.nextFloat() < 0.05D) {
/*     */           
/* 505 */           EntityChicken entitychicken1 = new EntityChicken(this.world);
/* 506 */           entitychicken1.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
/* 507 */           entitychicken1.onInitialSpawn(difficulty, null);
/* 508 */           entitychicken1.setChickenJockey(true);
/* 509 */           this.world.spawnEntityInWorld((Entity)entitychicken1);
/* 510 */           startRiding((Entity)entitychicken1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 515 */     setBreakDoorsAItask((this.rand.nextFloat() < f * 0.1F));
/* 516 */     setEquipmentBasedOnDifficulty(difficulty);
/* 517 */     setEnchantmentBasedOnDifficulty(difficulty);
/*     */     
/* 519 */     if (getItemStackFromSlot(EntityEquipmentSlot.HEAD).func_190926_b()) {
/*     */       
/* 521 */       Calendar calendar = this.world.getCurrentDate();
/*     */       
/* 523 */       if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.rand.nextFloat() < 0.25F) {
/*     */         
/* 525 */         setItemStackToSlot(EntityEquipmentSlot.HEAD, new ItemStack((this.rand.nextFloat() < 0.1F) ? Blocks.LIT_PUMPKIN : Blocks.PUMPKIN));
/* 526 */         this.inventoryArmorDropChances[EntityEquipmentSlot.HEAD.getIndex()] = 0.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 530 */     getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).applyModifier(new AttributeModifier("Random spawn bonus", this.rand.nextDouble() * 0.05000000074505806D, 0));
/* 531 */     double d0 = this.rand.nextDouble() * 1.5D * f;
/*     */     
/* 533 */     if (d0 > 1.0D)
/*     */     {
/* 535 */       getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).applyModifier(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
/*     */     }
/*     */     
/* 538 */     if (this.rand.nextFloat() < f * 0.05F) {
/*     */       
/* 540 */       getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 0.25D + 0.5D, 0));
/* 541 */       getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).applyModifier(new AttributeModifier("Leader zombie bonus", this.rand.nextDouble() * 3.0D + 1.0D, 2));
/* 542 */       setBreakDoorsAItask(true);
/*     */     } 
/*     */     
/* 545 */     return livingdata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setChildSize(boolean isChild) {
/* 553 */     multiplySize(isChild ? 0.5F : 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void setSize(float width, float height) {
/* 561 */     boolean flag = (this.zombieWidth > 0.0F && this.zombieHeight > 0.0F);
/* 562 */     this.zombieWidth = width;
/* 563 */     this.zombieHeight = height;
/*     */     
/* 565 */     if (!flag)
/*     */     {
/* 567 */       multiplySize(1.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected final void multiplySize(float size) {
/* 576 */     super.setSize(this.zombieWidth * size, this.zombieHeight * size);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYOffset() {
/* 584 */     return isChild() ? 0.0D : -0.45D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 592 */     super.onDeath(cause);
/*     */     
/* 594 */     if (cause.getEntity() instanceof EntityCreeper) {
/*     */       
/* 596 */       EntityCreeper entitycreeper = (EntityCreeper)cause.getEntity();
/*     */       
/* 598 */       if (entitycreeper.getPowered() && entitycreeper.isAIEnabled()) {
/*     */         
/* 600 */         entitycreeper.incrementDroppedSkulls();
/* 601 */         ItemStack itemstack = func_190732_dj();
/*     */         
/* 603 */         if (!itemstack.func_190926_b())
/*     */         {
/* 605 */           entityDropItem(itemstack, 0.0F);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack func_190732_dj() {
/* 613 */     return new ItemStack(Items.SKULL, 1, 2);
/*     */   }
/*     */   
/*     */   class GroupData
/*     */     implements IEntityLivingData
/*     */   {
/*     */     public boolean isChild;
/*     */     
/*     */     private GroupData(boolean p_i47328_2_) {
/* 622 */       this.isChild = p_i47328_2_;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */