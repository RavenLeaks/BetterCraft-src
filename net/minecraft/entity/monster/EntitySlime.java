/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFindEntityNearest;
/*     */ import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntitySlime
/*     */   extends EntityLiving implements IMob {
/*  41 */   private static final DataParameter<Integer> SLIME_SIZE = EntityDataManager.createKey(EntitySlime.class, DataSerializers.VARINT);
/*     */   
/*     */   public float squishAmount;
/*     */   public float squishFactor;
/*     */   public float prevSquishFactor;
/*     */   private boolean wasOnGround;
/*     */   
/*     */   public EntitySlime(World worldIn) {
/*  49 */     super(worldIn);
/*  50 */     this.moveHelper = new SlimeMoveHelper(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  55 */     this.tasks.addTask(1, new AISlimeFloat(this));
/*  56 */     this.tasks.addTask(2, new AISlimeAttack(this));
/*  57 */     this.tasks.addTask(3, new AISlimeFaceRandom(this));
/*  58 */     this.tasks.addTask(5, new AISlimeHop(this));
/*  59 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIFindEntityNearestPlayer(this));
/*  60 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAIFindEntityNearest(this, EntityIronGolem.class));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  65 */     super.entityInit();
/*  66 */     this.dataManager.register(SLIME_SIZE, Integer.valueOf(1));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setSlimeSize(int size, boolean p_70799_2_) {
/*  71 */     this.dataManager.set(SLIME_SIZE, Integer.valueOf(size));
/*  72 */     setSize(0.51000005F * size, 0.51000005F * size);
/*  73 */     setPosition(this.posX, this.posY, this.posZ);
/*  74 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((size * size));
/*  75 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue((0.2F + 0.1F * size));
/*     */     
/*  77 */     if (p_70799_2_)
/*     */     {
/*  79 */       setHealth(getMaxHealth());
/*     */     }
/*     */     
/*  82 */     this.experienceValue = size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSlimeSize() {
/*  90 */     return ((Integer)this.dataManager.get(SLIME_SIZE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesSlime(DataFixer fixer) {
/*  95 */     EntityLiving.registerFixesMob(fixer, EntitySlime.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 103 */     super.writeEntityToNBT(compound);
/* 104 */     compound.setInteger("Size", getSlimeSize() - 1);
/* 105 */     compound.setBoolean("wasOnGround", this.wasOnGround);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 113 */     super.readEntityFromNBT(compound);
/* 114 */     int i = compound.getInteger("Size");
/*     */     
/* 116 */     if (i < 0)
/*     */     {
/* 118 */       i = 0;
/*     */     }
/*     */     
/* 121 */     setSlimeSize(i + 1, false);
/* 122 */     this.wasOnGround = compound.getBoolean("wasOnGround");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSmallSlime() {
/* 127 */     return (getSlimeSize() <= 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected EnumParticleTypes getParticleType() {
/* 132 */     return EnumParticleTypes.SLIME;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 140 */     if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL && getSlimeSize() > 0)
/*     */     {
/* 142 */       this.isDead = true;
/*     */     }
/*     */     
/* 145 */     this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5F;
/* 146 */     this.prevSquishFactor = this.squishFactor;
/* 147 */     super.onUpdate();
/*     */     
/* 149 */     if (this.onGround && !this.wasOnGround) {
/*     */       
/* 151 */       int i = getSlimeSize();
/*     */       
/* 153 */       for (int j = 0; j < i * 8; j++) {
/*     */         
/* 155 */         float f = this.rand.nextFloat() * 6.2831855F;
/* 156 */         float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
/* 157 */         float f2 = MathHelper.sin(f) * i * 0.5F * f1;
/* 158 */         float f3 = MathHelper.cos(f) * i * 0.5F * f1;
/* 159 */         World world = this.world;
/* 160 */         EnumParticleTypes enumparticletypes = getParticleType();
/* 161 */         double d0 = this.posX + f2;
/* 162 */         double d1 = this.posZ + f3;
/* 163 */         world.spawnParticle(enumparticletypes, d0, (getEntityBoundingBox()).minY, d1, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */       
/* 166 */       playSound(getSquishSound(), getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
/* 167 */       this.squishAmount = -0.5F;
/*     */     }
/* 169 */     else if (!this.onGround && this.wasOnGround) {
/*     */       
/* 171 */       this.squishAmount = 1.0F;
/*     */     } 
/*     */     
/* 174 */     this.wasOnGround = this.onGround;
/* 175 */     alterSquishAmount();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void alterSquishAmount() {
/* 180 */     this.squishAmount *= 0.6F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getJumpDelay() {
/* 188 */     return this.rand.nextInt(20) + 10;
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntitySlime createInstance() {
/* 193 */     return new EntitySlime(this.world);
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 198 */     if (SLIME_SIZE.equals(key)) {
/*     */       
/* 200 */       int i = getSlimeSize();
/* 201 */       setSize(0.51000005F * i, 0.51000005F * i);
/* 202 */       this.rotationYaw = this.rotationYawHead;
/* 203 */       this.renderYawOffset = this.rotationYawHead;
/*     */       
/* 205 */       if (isInWater() && this.rand.nextInt(20) == 0)
/*     */       {
/* 207 */         resetHeight();
/*     */       }
/*     */     } 
/*     */     
/* 211 */     super.notifyDataManagerChange(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDead() {
/* 219 */     int i = getSlimeSize();
/*     */     
/* 221 */     if (!this.world.isRemote && i > 1 && getHealth() <= 0.0F) {
/*     */       
/* 223 */       int j = 2 + this.rand.nextInt(3);
/*     */       
/* 225 */       for (int k = 0; k < j; k++) {
/*     */         
/* 227 */         float f = ((k % 2) - 0.5F) * i / 4.0F;
/* 228 */         float f1 = ((k / 2) - 0.5F) * i / 4.0F;
/* 229 */         EntitySlime entityslime = createInstance();
/*     */         
/* 231 */         if (hasCustomName())
/*     */         {
/* 233 */           entityslime.setCustomNameTag(getCustomNameTag());
/*     */         }
/*     */         
/* 236 */         if (isNoDespawnRequired())
/*     */         {
/* 238 */           entityslime.enablePersistence();
/*     */         }
/*     */         
/* 241 */         entityslime.setSlimeSize(i / 2, true);
/* 242 */         entityslime.setLocationAndAngles(this.posX + f, this.posY + 0.5D, this.posZ + f1, this.rand.nextFloat() * 360.0F, 0.0F);
/* 243 */         this.world.spawnEntityInWorld((Entity)entityslime);
/*     */       } 
/*     */     } 
/*     */     
/* 247 */     super.setDead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void applyEntityCollision(Entity entityIn) {
/* 255 */     super.applyEntityCollision(entityIn);
/*     */     
/* 257 */     if (entityIn instanceof EntityIronGolem && canDamagePlayer())
/*     */     {
/* 259 */       dealDamage((EntityLivingBase)entityIn);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCollideWithPlayer(EntityPlayer entityIn) {
/* 268 */     if (canDamagePlayer())
/*     */     {
/* 270 */       dealDamage((EntityLivingBase)entityIn);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dealDamage(EntityLivingBase entityIn) {
/* 276 */     int i = getSlimeSize();
/*     */     
/* 278 */     if (canEntityBeSeen((Entity)entityIn) && getDistanceSqToEntity((Entity)entityIn) < 0.6D * i * 0.6D * i && entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), getAttackStrength())) {
/*     */       
/* 280 */       playSound(SoundEvents.ENTITY_SLIME_ATTACK, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/* 281 */       applyEnchantments((EntityLivingBase)this, (Entity)entityIn);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 287 */     return 0.625F * this.height;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDamagePlayer() {
/* 295 */     return !isSmallSlime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getAttackStrength() {
/* 303 */     return getSlimeSize();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 308 */     return isSmallSlime() ? SoundEvents.ENTITY_SMALL_SLIME_HURT : SoundEvents.ENTITY_SLIME_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 313 */     return isSmallSlime() ? SoundEvents.ENTITY_SMALL_SLIME_DEATH : SoundEvents.ENTITY_SLIME_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSquishSound() {
/* 318 */     return isSmallSlime() ? SoundEvents.ENTITY_SMALL_SLIME_SQUISH : SoundEvents.ENTITY_SLIME_SQUISH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getDropItem() {
/* 323 */     return (getSlimeSize() == 1) ? Items.SLIME_BALL : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 329 */     return (getSlimeSize() == 1) ? LootTableList.ENTITIES_SLIME : LootTableList.EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 337 */     BlockPos blockpos = new BlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));
/* 338 */     Chunk chunk = this.world.getChunkFromBlockCoords(blockpos);
/*     */     
/* 340 */     if (this.world.getWorldInfo().getTerrainType() == WorldType.FLAT && this.rand.nextInt(4) != 1)
/*     */     {
/* 342 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 346 */     if (this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
/*     */       
/* 348 */       Biome biome = this.world.getBiome(blockpos);
/*     */       
/* 350 */       if (biome == Biomes.SWAMPLAND && this.posY > 50.0D && this.posY < 70.0D && this.rand.nextFloat() < 0.5F && this.rand.nextFloat() < this.world.getCurrentMoonPhaseFactor() && this.world.getLightFromNeighbors(new BlockPos((Entity)this)) <= this.rand.nextInt(8))
/*     */       {
/* 352 */         return super.getCanSpawnHere();
/*     */       }
/*     */       
/* 355 */       if (this.rand.nextInt(10) == 0 && chunk.getRandomWithSeed(987234911L).nextInt(10) == 0 && this.posY < 40.0D)
/*     */       {
/* 357 */         return super.getCanSpawnHere();
/*     */       }
/*     */     } 
/*     */     
/* 361 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 370 */     return 0.4F * getSlimeSize();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVerticalFaceSpeed() {
/* 379 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean makesSoundOnJump() {
/* 387 */     return (getSlimeSize() > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void jump() {
/* 395 */     this.motionY = 0.41999998688697815D;
/* 396 */     this.isAirBorne = true;
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
/* 407 */     int i = this.rand.nextInt(3);
/*     */     
/* 409 */     if (i < 2 && this.rand.nextFloat() < 0.5F * difficulty.getClampedAdditionalDifficulty())
/*     */     {
/* 411 */       i++;
/*     */     }
/*     */     
/* 414 */     int j = 1 << i;
/* 415 */     setSlimeSize(j, true);
/* 416 */     return super.onInitialSpawn(difficulty, livingdata);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getJumpSound() {
/* 421 */     return isSmallSlime() ? SoundEvents.ENTITY_SMALL_SLIME_JUMP : SoundEvents.ENTITY_SLIME_JUMP;
/*     */   }
/*     */   
/*     */   static class AISlimeAttack
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntitySlime slime;
/*     */     private int growTieredTimer;
/*     */     
/*     */     public AISlimeAttack(EntitySlime slimeIn) {
/* 431 */       this.slime = slimeIn;
/* 432 */       setMutexBits(2);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 437 */       EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
/*     */       
/* 439 */       if (entitylivingbase == null)
/*     */       {
/* 441 */         return false;
/*     */       }
/* 443 */       if (!entitylivingbase.isEntityAlive())
/*     */       {
/* 445 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 449 */       return !(entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 455 */       this.growTieredTimer = 300;
/* 456 */       super.startExecuting();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 461 */       EntityLivingBase entitylivingbase = this.slime.getAttackTarget();
/*     */       
/* 463 */       if (entitylivingbase == null)
/*     */       {
/* 465 */         return false;
/*     */       }
/* 467 */       if (!entitylivingbase.isEntityAlive())
/*     */       {
/* 469 */         return false;
/*     */       }
/* 471 */       if (entitylivingbase instanceof EntityPlayer && ((EntityPlayer)entitylivingbase).capabilities.disableDamage)
/*     */       {
/* 473 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 477 */       return (--this.growTieredTimer > 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 483 */       this.slime.faceEntity((Entity)this.slime.getAttackTarget(), 10.0F, 10.0F);
/* 484 */       ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).setDirection(this.slime.rotationYaw, this.slime.canDamagePlayer());
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISlimeFaceRandom
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntitySlime slime;
/*     */     private float chosenDegrees;
/*     */     private int nextRandomizeTime;
/*     */     
/*     */     public AISlimeFaceRandom(EntitySlime slimeIn) {
/* 496 */       this.slime = slimeIn;
/* 497 */       setMutexBits(2);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 502 */       return (this.slime.getAttackTarget() == null && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava() || this.slime.isPotionActive(MobEffects.LEVITATION)));
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 507 */       if (--this.nextRandomizeTime <= 0) {
/*     */         
/* 509 */         this.nextRandomizeTime = 40 + this.slime.getRNG().nextInt(60);
/* 510 */         this.chosenDegrees = this.slime.getRNG().nextInt(360);
/*     */       } 
/*     */       
/* 513 */       ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).setDirection(this.chosenDegrees, false);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISlimeFloat
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntitySlime slime;
/*     */     
/*     */     public AISlimeFloat(EntitySlime slimeIn) {
/* 523 */       this.slime = slimeIn;
/* 524 */       setMutexBits(5);
/* 525 */       ((PathNavigateGround)slimeIn.getNavigator()).setCanSwim(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 530 */       return !(!this.slime.isInWater() && !this.slime.isInLava());
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 535 */       if (this.slime.getRNG().nextFloat() < 0.8F)
/*     */       {
/* 537 */         this.slime.getJumpHelper().setJumping();
/*     */       }
/*     */       
/* 540 */       ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.2D);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISlimeHop
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntitySlime slime;
/*     */     
/*     */     public AISlimeHop(EntitySlime slimeIn) {
/* 550 */       this.slime = slimeIn;
/* 551 */       setMutexBits(5);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 556 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 561 */       ((EntitySlime.SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   static class SlimeMoveHelper
/*     */     extends EntityMoveHelper
/*     */   {
/*     */     private float yRot;
/*     */     private int jumpDelay;
/*     */     private final EntitySlime slime;
/*     */     private boolean isAggressive;
/*     */     
/*     */     public SlimeMoveHelper(EntitySlime slimeIn) {
/* 574 */       super(slimeIn);
/* 575 */       this.slime = slimeIn;
/* 576 */       this.yRot = 180.0F * slimeIn.rotationYaw / 3.1415927F;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDirection(float p_179920_1_, boolean p_179920_2_) {
/* 581 */       this.yRot = p_179920_1_;
/* 582 */       this.isAggressive = p_179920_2_;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setSpeed(double speedIn) {
/* 587 */       this.speed = speedIn;
/* 588 */       this.action = EntityMoveHelper.Action.MOVE_TO;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUpdateMoveHelper() {
/* 593 */       this.entity.rotationYaw = limitAngle(this.entity.rotationYaw, this.yRot, 90.0F);
/* 594 */       this.entity.rotationYawHead = this.entity.rotationYaw;
/* 595 */       this.entity.renderYawOffset = this.entity.rotationYaw;
/*     */       
/* 597 */       if (this.action != EntityMoveHelper.Action.MOVE_TO) {
/*     */         
/* 599 */         this.entity.func_191989_p(0.0F);
/*     */       }
/*     */       else {
/*     */         
/* 603 */         this.action = EntityMoveHelper.Action.WAIT;
/*     */         
/* 605 */         if (this.entity.onGround) {
/*     */           
/* 607 */           this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
/*     */           
/* 609 */           if (this.jumpDelay-- <= 0)
/*     */           {
/* 611 */             this.jumpDelay = this.slime.getJumpDelay();
/*     */             
/* 613 */             if (this.isAggressive)
/*     */             {
/* 615 */               this.jumpDelay /= 3;
/*     */             }
/*     */             
/* 618 */             this.slime.getJumpHelper().setJumping();
/*     */             
/* 620 */             if (this.slime.makesSoundOnJump())
/*     */             {
/* 622 */               this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), ((this.slime.getRNG().nextFloat() - this.slime.getRNG().nextFloat()) * 0.2F + 1.0F) * 0.8F);
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 627 */             this.slime.moveStrafing = 0.0F;
/* 628 */             this.slime.field_191988_bg = 0.0F;
/* 629 */             this.entity.setAIMoveSpeed(0.0F);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 634 */           this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntitySlime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */