/*     */ package net.minecraft.entity.passive;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.Random;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackMelee;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIBeg;
/*     */ import net.minecraft.entity.ai.EntityAIFollowOwner;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIOwnerHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAIOwnerHurtTarget;
/*     */ import net.minecraft.entity.ai.EntityAISit;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITargetNonTamed;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.monster.AbstractSkeleton;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemFood;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityWolf
/*     */   extends EntityTameable {
/*  55 */   public static final DataParameter<Float> DATA_HEALTH_ID = EntityDataManager.createKey(EntityWolf.class, DataSerializers.FLOAT);
/*  56 */   public static final DataParameter<Boolean> BEGGING = EntityDataManager.createKey(EntityWolf.class, DataSerializers.BOOLEAN);
/*  57 */   public static final DataParameter<Integer> COLLAR_COLOR = EntityDataManager.createKey(EntityWolf.class, DataSerializers.VARINT);
/*     */ 
/*     */   
/*     */   private float headRotationCourse;
/*     */ 
/*     */   
/*     */   private float headRotationCourseOld;
/*     */ 
/*     */   
/*     */   private boolean isWet;
/*     */ 
/*     */   
/*     */   private boolean isShaking;
/*     */   
/*     */   private float timeWolfIsShaking;
/*     */   
/*     */   private float prevTimeWolfIsShaking;
/*     */ 
/*     */   
/*     */   public EntityWolf(World worldIn) {
/*  77 */     super(worldIn);
/*  78 */     setSize(0.6F, 0.85F);
/*  79 */     setTamed(false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  84 */     this.aiSit = new EntityAISit(this);
/*  85 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  86 */     this.tasks.addTask(2, (EntityAIBase)this.aiSit);
/*  87 */     this.tasks.addTask(3, (EntityAIBase)new AIAvoidEntity<>(this, EntityLlama.class, 24.0F, 1.5D, 1.5D));
/*  88 */     this.tasks.addTask(4, (EntityAIBase)new EntityAILeapAtTarget((EntityLiving)this, 0.4F));
/*  89 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIAttackMelee((EntityCreature)this, 1.0D, true));
/*  90 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
/*  91 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIMate(this, 1.0D));
/*  92 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0D));
/*  93 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIBeg(this, 8.0F));
/*  94 */     this.tasks.addTask(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  95 */     this.tasks.addTask(10, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  96 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIOwnerHurtByTarget(this));
/*  97 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAIOwnerHurtTarget(this));
/*  98 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, true, new Class[0]));
/*  99 */     this.targetTasks.addTask(4, (EntityAIBase)new EntityAITargetNonTamed(this, EntityAnimal.class, false, new Predicate<Entity>()
/*     */           {
/*     */             public boolean apply(@Nullable Entity p_apply_1_)
/*     */             {
/* 103 */               return !(!(p_apply_1_ instanceof EntitySheep) && !(p_apply_1_ instanceof EntityRabbit));
/*     */             }
/*     */           }));
/* 106 */     this.targetTasks.addTask(5, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, AbstractSkeleton.class, false));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 111 */     super.applyEntityAttributes();
/* 112 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
/*     */     
/* 114 */     if (isTamed()) {
/*     */       
/* 116 */       getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
/*     */     }
/*     */     else {
/*     */       
/* 120 */       getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
/*     */     } 
/*     */     
/* 123 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
/* 131 */     super.setAttackTarget(entitylivingbaseIn);
/*     */     
/* 133 */     if (entitylivingbaseIn == null) {
/*     */       
/* 135 */       setAngry(false);
/*     */     }
/* 137 */     else if (!isTamed()) {
/*     */       
/* 139 */       setAngry(true);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 145 */     this.dataManager.set(DATA_HEALTH_ID, Float.valueOf(getHealth()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 150 */     super.entityInit();
/* 151 */     this.dataManager.register(DATA_HEALTH_ID, Float.valueOf(getHealth()));
/* 152 */     this.dataManager.register(BEGGING, Boolean.valueOf(false));
/* 153 */     this.dataManager.register(COLLAR_COLOR, Integer.valueOf(EnumDyeColor.RED.getDyeDamage()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 158 */     playSound(SoundEvents.ENTITY_WOLF_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesWolf(DataFixer fixer) {
/* 163 */     EntityLiving.registerFixesMob(fixer, EntityWolf.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 171 */     super.writeEntityToNBT(compound);
/* 172 */     compound.setBoolean("Angry", isAngry());
/* 173 */     compound.setByte("CollarColor", (byte)getCollarColor().getDyeDamage());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 181 */     super.readEntityFromNBT(compound);
/* 182 */     setAngry(compound.getBoolean("Angry"));
/*     */     
/* 184 */     if (compound.hasKey("CollarColor", 99))
/*     */     {
/* 186 */       setCollarColor(EnumDyeColor.byDyeDamage(compound.getByte("CollarColor")));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 192 */     if (isAngry())
/*     */     {
/* 194 */       return SoundEvents.ENTITY_WOLF_GROWL;
/*     */     }
/* 196 */     if (this.rand.nextInt(3) == 0)
/*     */     {
/* 198 */       return (isTamed() && ((Float)this.dataManager.get(DATA_HEALTH_ID)).floatValue() < 10.0F) ? SoundEvents.ENTITY_WOLF_WHINE : SoundEvents.ENTITY_WOLF_PANT;
/*     */     }
/*     */ 
/*     */     
/* 202 */     return SoundEvents.ENTITY_WOLF_AMBIENT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 208 */     return SoundEvents.ENTITY_WOLF_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 213 */     return SoundEvents.ENTITY_WOLF_DEATH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 221 */     return 0.4F;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 227 */     return LootTableList.ENTITIES_WOLF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 236 */     super.onLivingUpdate();
/*     */     
/* 238 */     if (!this.world.isRemote && this.isWet && !this.isShaking && !hasPath() && this.onGround) {
/*     */       
/* 240 */       this.isShaking = true;
/* 241 */       this.timeWolfIsShaking = 0.0F;
/* 242 */       this.prevTimeWolfIsShaking = 0.0F;
/* 243 */       this.world.setEntityState((Entity)this, (byte)8);
/*     */     } 
/*     */     
/* 246 */     if (!this.world.isRemote && getAttackTarget() == null && isAngry())
/*     */     {
/* 248 */       setAngry(false);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 257 */     super.onUpdate();
/* 258 */     this.headRotationCourseOld = this.headRotationCourse;
/*     */     
/* 260 */     if (isBegging()) {
/*     */       
/* 262 */       this.headRotationCourse += (1.0F - this.headRotationCourse) * 0.4F;
/*     */     }
/*     */     else {
/*     */       
/* 266 */       this.headRotationCourse += (0.0F - this.headRotationCourse) * 0.4F;
/*     */     } 
/*     */     
/* 269 */     if (isWet()) {
/*     */       
/* 271 */       this.isWet = true;
/* 272 */       this.isShaking = false;
/* 273 */       this.timeWolfIsShaking = 0.0F;
/* 274 */       this.prevTimeWolfIsShaking = 0.0F;
/*     */     }
/* 276 */     else if ((this.isWet || this.isShaking) && this.isShaking) {
/*     */       
/* 278 */       if (this.timeWolfIsShaking == 0.0F)
/*     */       {
/* 280 */         playSound(SoundEvents.ENTITY_WOLF_SHAKE, getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*     */       }
/*     */       
/* 283 */       this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
/* 284 */       this.timeWolfIsShaking += 0.05F;
/*     */       
/* 286 */       if (this.prevTimeWolfIsShaking >= 2.0F) {
/*     */         
/* 288 */         this.isWet = false;
/* 289 */         this.isShaking = false;
/* 290 */         this.prevTimeWolfIsShaking = 0.0F;
/* 291 */         this.timeWolfIsShaking = 0.0F;
/*     */       } 
/*     */       
/* 294 */       if (this.timeWolfIsShaking > 0.4F) {
/*     */         
/* 296 */         float f = (float)(getEntityBoundingBox()).minY;
/* 297 */         int i = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4F) * 3.1415927F) * 7.0F);
/*     */         
/* 299 */         for (int j = 0; j < i; j++) {
/*     */           
/* 301 */           float f1 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
/* 302 */           float f2 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
/* 303 */           this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + f1, (f + 0.8F), this.posZ + f2, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWolfWet() {
/* 314 */     return this.isWet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getShadingWhileWet(float p_70915_1_) {
/* 322 */     return 0.75F + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70915_1_) / 2.0F * 0.25F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getShakeAngle(float p_70923_1_, float p_70923_2_) {
/* 327 */     float f = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * p_70923_1_ + p_70923_2_) / 1.8F;
/*     */     
/* 329 */     if (f < 0.0F) {
/*     */       
/* 331 */       f = 0.0F;
/*     */     }
/* 333 */     else if (f > 1.0F) {
/*     */       
/* 335 */       f = 1.0F;
/*     */     } 
/*     */     
/* 338 */     return MathHelper.sin(f * 3.1415927F) * MathHelper.sin(f * 3.1415927F * 11.0F) * 0.15F * 3.1415927F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getInterestedAngle(float p_70917_1_) {
/* 343 */     return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * p_70917_1_) * 0.15F * 3.1415927F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 348 */     return this.height * 0.8F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVerticalFaceSpeed() {
/* 357 */     return isSitting() ? 20 : super.getVerticalFaceSpeed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 365 */     if (isEntityInvulnerable(source))
/*     */     {
/* 367 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 371 */     Entity entity = source.getEntity();
/*     */     
/* 373 */     if (this.aiSit != null)
/*     */     {
/* 375 */       this.aiSit.setSitting(false);
/*     */     }
/*     */     
/* 378 */     if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof net.minecraft.entity.projectile.EntityArrow))
/*     */     {
/* 380 */       amount = (amount + 1.0F) / 2.0F;
/*     */     }
/*     */     
/* 383 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 389 */     boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (int)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
/*     */     
/* 391 */     if (flag)
/*     */     {
/* 393 */       applyEnchantments((EntityLivingBase)this, entityIn);
/*     */     }
/*     */     
/* 396 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTamed(boolean tamed) {
/* 401 */     super.setTamed(tamed);
/*     */     
/* 403 */     if (tamed) {
/*     */       
/* 405 */       getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
/*     */     }
/*     */     else {
/*     */       
/* 409 */       getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
/*     */     } 
/*     */     
/* 412 */     getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 417 */     ItemStack itemstack = player.getHeldItem(hand);
/*     */     
/* 419 */     if (isTamed()) {
/*     */       
/* 421 */       if (!itemstack.func_190926_b())
/*     */       {
/* 423 */         if (itemstack.getItem() instanceof ItemFood) {
/*     */           
/* 425 */           ItemFood itemfood = (ItemFood)itemstack.getItem();
/*     */           
/* 427 */           if (itemfood.isWolfsFavoriteMeat() && ((Float)this.dataManager.get(DATA_HEALTH_ID)).floatValue() < 20.0F)
/*     */           {
/* 429 */             if (!player.capabilities.isCreativeMode)
/*     */             {
/* 431 */               itemstack.func_190918_g(1);
/*     */             }
/*     */             
/* 434 */             heal(itemfood.getHealAmount(itemstack));
/* 435 */             return true;
/*     */           }
/*     */         
/* 438 */         } else if (itemstack.getItem() == Items.DYE) {
/*     */           
/* 440 */           EnumDyeColor enumdyecolor = EnumDyeColor.byDyeDamage(itemstack.getMetadata());
/*     */           
/* 442 */           if (enumdyecolor != getCollarColor()) {
/*     */             
/* 444 */             setCollarColor(enumdyecolor);
/*     */             
/* 446 */             if (!player.capabilities.isCreativeMode)
/*     */             {
/* 448 */               itemstack.func_190918_g(1);
/*     */             }
/*     */             
/* 451 */             return true;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 456 */       if (isOwner((EntityLivingBase)player) && !this.world.isRemote && !isBreedingItem(itemstack))
/*     */       {
/* 458 */         this.aiSit.setSitting(!isSitting());
/* 459 */         this.isJumping = false;
/* 460 */         this.navigator.clearPathEntity();
/* 461 */         setAttackTarget((EntityLivingBase)null);
/*     */       }
/*     */     
/* 464 */     } else if (itemstack.getItem() == Items.BONE && !isAngry()) {
/*     */       
/* 466 */       if (!player.capabilities.isCreativeMode)
/*     */       {
/* 468 */         itemstack.func_190918_g(1);
/*     */       }
/*     */       
/* 471 */       if (!this.world.isRemote)
/*     */       {
/* 473 */         if (this.rand.nextInt(3) == 0) {
/*     */           
/* 475 */           func_193101_c(player);
/* 476 */           this.navigator.clearPathEntity();
/* 477 */           setAttackTarget((EntityLivingBase)null);
/* 478 */           this.aiSit.setSitting(true);
/* 479 */           setHealth(20.0F);
/* 480 */           playTameEffect(true);
/* 481 */           this.world.setEntityState((Entity)this, (byte)7);
/*     */         }
/*     */         else {
/*     */           
/* 485 */           playTameEffect(false);
/* 486 */           this.world.setEntityState((Entity)this, (byte)6);
/*     */         } 
/*     */       }
/*     */       
/* 490 */       return true;
/*     */     } 
/*     */     
/* 493 */     return super.processInteract(player, hand);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 498 */     if (id == 8) {
/*     */       
/* 500 */       this.isShaking = true;
/* 501 */       this.timeWolfIsShaking = 0.0F;
/* 502 */       this.prevTimeWolfIsShaking = 0.0F;
/*     */     }
/*     */     else {
/*     */       
/* 506 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTailRotation() {
/* 512 */     if (isAngry())
/*     */     {
/* 514 */       return 1.5393804F;
/*     */     }
/*     */ 
/*     */     
/* 518 */     return isTamed() ? ((0.55F - (getMaxHealth() - ((Float)this.dataManager.get(DATA_HEALTH_ID)).floatValue()) * 0.02F) * 3.1415927F) : 0.62831855F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 528 */     return (stack.getItem() instanceof ItemFood && ((ItemFood)stack.getItem()).isWolfsFavoriteMeat());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxSpawnedInChunk() {
/* 536 */     return 8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAngry() {
/* 544 */     return ((((Byte)this.dataManager.get(TAMED)).byteValue() & 0x2) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAngry(boolean angry) {
/* 552 */     byte b0 = ((Byte)this.dataManager.get(TAMED)).byteValue();
/*     */     
/* 554 */     if (angry) {
/*     */       
/* 556 */       this.dataManager.set(TAMED, Byte.valueOf((byte)(b0 | 0x2)));
/*     */     }
/*     */     else {
/*     */       
/* 560 */       this.dataManager.set(TAMED, Byte.valueOf((byte)(b0 & 0xFFFFFFFD)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumDyeColor getCollarColor() {
/* 566 */     return EnumDyeColor.byDyeDamage(((Integer)this.dataManager.get(COLLAR_COLOR)).intValue() & 0xF);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCollarColor(EnumDyeColor collarcolor) {
/* 571 */     this.dataManager.set(COLLAR_COLOR, Integer.valueOf(collarcolor.getDyeDamage()));
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityWolf createChild(EntityAgeable ageable) {
/* 576 */     EntityWolf entitywolf = new EntityWolf(this.world);
/* 577 */     UUID uuid = getOwnerId();
/*     */     
/* 579 */     if (uuid != null) {
/*     */       
/* 581 */       entitywolf.setOwnerId(uuid);
/* 582 */       entitywolf.setTamed(true);
/*     */     } 
/*     */     
/* 585 */     return entitywolf;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBegging(boolean beg) {
/* 590 */     this.dataManager.set(BEGGING, Boolean.valueOf(beg));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 598 */     if (otherAnimal == this)
/*     */     {
/* 600 */       return false;
/*     */     }
/* 602 */     if (!isTamed())
/*     */     {
/* 604 */       return false;
/*     */     }
/* 606 */     if (!(otherAnimal instanceof EntityWolf))
/*     */     {
/* 608 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 612 */     EntityWolf entitywolf = (EntityWolf)otherAnimal;
/*     */     
/* 614 */     if (!entitywolf.isTamed())
/*     */     {
/* 616 */       return false;
/*     */     }
/* 618 */     if (entitywolf.isSitting())
/*     */     {
/* 620 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 624 */     return (isInLove() && entitywolf.isInLove());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBegging() {
/* 631 */     return ((Boolean)this.dataManager.get(BEGGING)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldAttackEntity(EntityLivingBase p_142018_1_, EntityLivingBase p_142018_2_) {
/* 636 */     if (!(p_142018_1_ instanceof net.minecraft.entity.monster.EntityCreeper) && !(p_142018_1_ instanceof net.minecraft.entity.monster.EntityGhast)) {
/*     */       
/* 638 */       if (p_142018_1_ instanceof EntityWolf) {
/*     */         
/* 640 */         EntityWolf entitywolf = (EntityWolf)p_142018_1_;
/*     */         
/* 642 */         if (entitywolf.isTamed() && entitywolf.getOwner() == p_142018_2_)
/*     */         {
/* 644 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 648 */       if (p_142018_1_ instanceof EntityPlayer && p_142018_2_ instanceof EntityPlayer && !((EntityPlayer)p_142018_2_).canAttackPlayer((EntityPlayer)p_142018_1_))
/*     */       {
/* 650 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 654 */       return !(p_142018_1_ instanceof AbstractHorse && ((AbstractHorse)p_142018_1_).isTame());
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 659 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeLeashedTo(EntityPlayer player) {
/* 665 */     return (!isAngry() && super.canBeLeashedTo(player));
/*     */   }
/*     */   
/*     */   class AIAvoidEntity<T extends Entity>
/*     */     extends EntityAIAvoidEntity<T>
/*     */   {
/*     */     private final EntityWolf field_190856_d;
/*     */     
/*     */     public AIAvoidEntity(EntityWolf p_i47251_2_, Class<T> p_i47251_3_, float p_i47251_4_, double p_i47251_5_, double p_i47251_7_) {
/* 674 */       super((EntityCreature)p_i47251_2_, p_i47251_3_, p_i47251_4_, p_i47251_5_, p_i47251_7_);
/* 675 */       this.field_190856_d = p_i47251_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 680 */       if (super.shouldExecute() && this.closestLivingEntity instanceof EntityLlama)
/*     */       {
/* 682 */         return (!this.field_190856_d.isTamed() && func_190854_a((EntityLlama)this.closestLivingEntity));
/*     */       }
/*     */ 
/*     */       
/* 686 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean func_190854_a(EntityLlama p_190854_1_) {
/* 692 */       return (p_190854_1_.func_190707_dL() >= EntityWolf.this.rand.nextInt(5));
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 697 */       EntityWolf.this.setAttackTarget((EntityLivingBase)null);
/* 698 */       super.startExecuting();
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 703 */       EntityWolf.this.setAttackTarget((EntityLivingBase)null);
/* 704 */       super.updateTask();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityWolf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */