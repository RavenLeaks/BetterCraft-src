/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.EntityLookHelper;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.pathfinding.PathNavigateSwimmer;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityGuardian
/*     */   extends EntityMob
/*     */ {
/*  41 */   private static final DataParameter<Boolean> field_190766_bz = EntityDataManager.createKey(EntityGuardian.class, DataSerializers.BOOLEAN);
/*  42 */   private static final DataParameter<Integer> TARGET_ENTITY = EntityDataManager.createKey(EntityGuardian.class, DataSerializers.VARINT);
/*     */   
/*     */   protected float clientSideTailAnimation;
/*     */   protected float clientSideTailAnimationO;
/*     */   protected float clientSideTailAnimationSpeed;
/*     */   protected float clientSideSpikesAnimation;
/*     */   protected float clientSideSpikesAnimationO;
/*     */   private EntityLivingBase targetedEntity;
/*     */   private int clientSideAttackTime;
/*     */   private boolean clientSideTouchedGround;
/*     */   protected EntityAIWander wander;
/*     */   
/*     */   public EntityGuardian(World worldIn) {
/*  55 */     super(worldIn);
/*  56 */     this.experienceValue = 10;
/*  57 */     setSize(0.85F, 0.85F);
/*  58 */     this.moveHelper = new GuardianMoveHelper(this);
/*  59 */     this.clientSideTailAnimation = this.rand.nextFloat();
/*  60 */     this.clientSideTailAnimationO = this.clientSideTailAnimation;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  65 */     EntityAIMoveTowardsRestriction entityaimovetowardsrestriction = new EntityAIMoveTowardsRestriction(this, 1.0D);
/*  66 */     this.wander = new EntityAIWander(this, 1.0D, 80);
/*  67 */     this.tasks.addTask(4, new AIGuardianAttack(this));
/*  68 */     this.tasks.addTask(5, (EntityAIBase)entityaimovetowardsrestriction);
/*  69 */     this.tasks.addTask(7, (EntityAIBase)this.wander);
/*  70 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  71 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityGuardian.class, 12.0F, 0.01F));
/*  72 */     this.tasks.addTask(9, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  73 */     this.wander.setMutexBits(3);
/*  74 */     entityaimovetowardsrestriction.setMutexBits(3);
/*  75 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityLivingBase.class, 10, true, false, new GuardianTargetSelector(this)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  80 */     super.applyEntityAttributes();
/*  81 */     getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
/*  82 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
/*  83 */     getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(16.0D);
/*  84 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesGuardian(DataFixer fixer) {
/*  89 */     EntityLiving.registerFixesMob(fixer, EntityGuardian.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PathNavigate getNewNavigator(World worldIn) {
/*  97 */     return (PathNavigate)new PathNavigateSwimmer((EntityLiving)this, worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 102 */     super.entityInit();
/* 103 */     this.dataManager.register(field_190766_bz, Boolean.valueOf(false));
/* 104 */     this.dataManager.register(TARGET_ENTITY, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMoving() {
/* 109 */     return ((Boolean)this.dataManager.get(field_190766_bz)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   private void setMoving(boolean moving) {
/* 114 */     this.dataManager.set(field_190766_bz, Boolean.valueOf(moving));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAttackDuration() {
/* 119 */     return 80;
/*     */   }
/*     */ 
/*     */   
/*     */   private void setTargetedEntity(int entityId) {
/* 124 */     this.dataManager.set(TARGET_ENTITY, Integer.valueOf(entityId));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasTargetedEntity() {
/* 129 */     return (((Integer)this.dataManager.get(TARGET_ENTITY)).intValue() != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EntityLivingBase getTargetedEntity() {
/* 135 */     if (!hasTargetedEntity())
/*     */     {
/* 137 */       return null;
/*     */     }
/* 139 */     if (this.world.isRemote) {
/*     */       
/* 141 */       if (this.targetedEntity != null)
/*     */       {
/* 143 */         return this.targetedEntity;
/*     */       }
/*     */ 
/*     */       
/* 147 */       Entity entity = this.world.getEntityByID(((Integer)this.dataManager.get(TARGET_ENTITY)).intValue());
/*     */       
/* 149 */       if (entity instanceof EntityLivingBase) {
/*     */         
/* 151 */         this.targetedEntity = (EntityLivingBase)entity;
/* 152 */         return this.targetedEntity;
/*     */       } 
/*     */ 
/*     */       
/* 156 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     return getAttackTarget();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 168 */     super.notifyDataManagerChange(key);
/*     */     
/* 170 */     if (TARGET_ENTITY.equals(key)) {
/*     */       
/* 172 */       this.clientSideAttackTime = 0;
/* 173 */       this.targetedEntity = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTalkInterval() {
/* 182 */     return 160;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 187 */     return isInWater() ? SoundEvents.ENTITY_GUARDIAN_AMBIENT : SoundEvents.ENTITY_GUARDIAN_AMBIENT_LAND;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 192 */     return isInWater() ? SoundEvents.ENTITY_GUARDIAN_HURT : SoundEvents.ENTITY_GUARDIAN_HURT_LAND;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 197 */     return isInWater() ? SoundEvents.ENTITY_GUARDIAN_DEATH : SoundEvents.ENTITY_GUARDIAN_DEATH_LAND;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 206 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 211 */     return this.height * 0.5F;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getBlockPathWeight(BlockPos pos) {
/* 216 */     return (this.world.getBlockState(pos).getMaterial() == Material.WATER) ? (10.0F + this.world.getLightBrightness(pos) - 0.5F) : super.getBlockPathWeight(pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 225 */     if (this.world.isRemote) {
/*     */       
/* 227 */       this.clientSideTailAnimationO = this.clientSideTailAnimation;
/*     */       
/* 229 */       if (!isInWater()) {
/*     */         
/* 231 */         this.clientSideTailAnimationSpeed = 2.0F;
/*     */         
/* 233 */         if (this.motionY > 0.0D && this.clientSideTouchedGround && !isSilent())
/*     */         {
/* 235 */           this.world.playSound(this.posX, this.posY, this.posZ, func_190765_dj(), getSoundCategory(), 1.0F, 1.0F, false);
/*     */         }
/*     */         
/* 238 */         this.clientSideTouchedGround = (this.motionY < 0.0D && this.world.isBlockNormalCube((new BlockPos((Entity)this)).down(), false));
/*     */       }
/* 240 */       else if (isMoving()) {
/*     */         
/* 242 */         if (this.clientSideTailAnimationSpeed < 0.5F)
/*     */         {
/* 244 */           this.clientSideTailAnimationSpeed = 4.0F;
/*     */         }
/*     */         else
/*     */         {
/* 248 */           this.clientSideTailAnimationSpeed += (0.5F - this.clientSideTailAnimationSpeed) * 0.1F;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 253 */         this.clientSideTailAnimationSpeed += (0.125F - this.clientSideTailAnimationSpeed) * 0.2F;
/*     */       } 
/*     */       
/* 256 */       this.clientSideTailAnimation += this.clientSideTailAnimationSpeed;
/* 257 */       this.clientSideSpikesAnimationO = this.clientSideSpikesAnimation;
/*     */       
/* 259 */       if (!isInWater()) {
/*     */         
/* 261 */         this.clientSideSpikesAnimation = this.rand.nextFloat();
/*     */       }
/* 263 */       else if (isMoving()) {
/*     */         
/* 265 */         this.clientSideSpikesAnimation += (0.0F - this.clientSideSpikesAnimation) * 0.25F;
/*     */       }
/*     */       else {
/*     */         
/* 269 */         this.clientSideSpikesAnimation += (1.0F - this.clientSideSpikesAnimation) * 0.06F;
/*     */       } 
/*     */       
/* 272 */       if (isMoving() && isInWater()) {
/*     */         
/* 274 */         Vec3d vec3d = getLook(0.0F);
/*     */         
/* 276 */         for (int i = 0; i < 2; i++)
/*     */         {
/* 278 */           this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width - vec3d.xCoord * 1.5D, this.posY + this.rand.nextDouble() * this.height - vec3d.yCoord * 1.5D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width - vec3d.zCoord * 1.5D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */       } 
/*     */       
/* 282 */       if (hasTargetedEntity()) {
/*     */         
/* 284 */         if (this.clientSideAttackTime < getAttackDuration())
/*     */         {
/* 286 */           this.clientSideAttackTime++;
/*     */         }
/*     */         
/* 289 */         EntityLivingBase entitylivingbase = getTargetedEntity();
/*     */         
/* 291 */         if (entitylivingbase != null) {
/*     */           
/* 293 */           getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 90.0F, 90.0F);
/* 294 */           getLookHelper().onUpdateLook();
/* 295 */           double d5 = getAttackAnimationScale(0.0F);
/* 296 */           double d0 = entitylivingbase.posX - this.posX;
/* 297 */           double d1 = entitylivingbase.posY + (entitylivingbase.height * 0.5F) - this.posY + getEyeHeight();
/* 298 */           double d2 = entitylivingbase.posZ - this.posZ;
/* 299 */           double d3 = Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/* 300 */           d0 /= d3;
/* 301 */           d1 /= d3;
/* 302 */           d2 /= d3;
/* 303 */           double d4 = this.rand.nextDouble();
/*     */           
/* 305 */           while (d4 < d3) {
/*     */             
/* 307 */             d4 += 1.8D - d5 + this.rand.nextDouble() * (1.7D - d5);
/* 308 */             this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + d0 * d4, this.posY + d1 * d4 + getEyeHeight(), this.posZ + d2 * d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 314 */     if (this.inWater) {
/*     */       
/* 316 */       setAir(300);
/*     */     }
/* 318 */     else if (this.onGround) {
/*     */       
/* 320 */       this.motionY += 0.5D;
/* 321 */       this.motionX += ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
/* 322 */       this.motionZ += ((this.rand.nextFloat() * 2.0F - 1.0F) * 0.4F);
/* 323 */       this.rotationYaw = this.rand.nextFloat() * 360.0F;
/* 324 */       this.onGround = false;
/* 325 */       this.isAirBorne = true;
/*     */     } 
/*     */     
/* 328 */     if (hasTargetedEntity())
/*     */     {
/* 330 */       this.rotationYaw = this.rotationYawHead;
/*     */     }
/*     */     
/* 333 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent func_190765_dj() {
/* 338 */     return SoundEvents.ENTITY_GUARDIAN_FLOP;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getTailAnimation(float p_175471_1_) {
/* 343 */     return this.clientSideTailAnimationO + (this.clientSideTailAnimation - this.clientSideTailAnimationO) * p_175471_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getSpikesAnimation(float p_175469_1_) {
/* 348 */     return this.clientSideSpikesAnimationO + (this.clientSideSpikesAnimation - this.clientSideSpikesAnimationO) * p_175469_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getAttackAnimationScale(float p_175477_1_) {
/* 353 */     return (this.clientSideAttackTime + p_175477_1_) / getAttackDuration();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 359 */     return LootTableList.ENTITIES_GUARDIAN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 367 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotColliding() {
/* 375 */     return (this.world.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.world.getCollisionBoxes((Entity)this, getEntityBoundingBox()).isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 383 */     return ((this.rand.nextInt(20) == 0 || !this.world.canBlockSeeSky(new BlockPos((Entity)this))) && super.getCanSpawnHere());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 391 */     if (!isMoving() && !source.isMagicDamage() && source.getSourceOfDamage() instanceof EntityLivingBase) {
/*     */       
/* 393 */       EntityLivingBase entitylivingbase = (EntityLivingBase)source.getSourceOfDamage();
/*     */       
/* 395 */       if (!source.isExplosion())
/*     */       {
/* 397 */         entitylivingbase.attackEntityFrom(DamageSource.causeThornsDamage((Entity)this), 2.0F);
/*     */       }
/*     */     } 
/*     */     
/* 401 */     if (this.wander != null)
/*     */     {
/* 403 */       this.wander.makeUpdate();
/*     */     }
/*     */     
/* 406 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getVerticalFaceSpeed() {
/* 415 */     return 180;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
/* 420 */     if (isServerWorld() && isInWater()) {
/*     */       
/* 422 */       func_191958_b(p_191986_1_, p_191986_2_, p_191986_3_, 0.1F);
/* 423 */       moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/* 424 */       this.motionX *= 0.8999999761581421D;
/* 425 */       this.motionY *= 0.8999999761581421D;
/* 426 */       this.motionZ *= 0.8999999761581421D;
/*     */       
/* 428 */       if (!isMoving() && getAttackTarget() == null)
/*     */       {
/* 430 */         this.motionY -= 0.005D;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 435 */       super.func_191986_a(p_191986_1_, p_191986_2_, p_191986_3_);
/*     */     } 
/*     */   }
/*     */   
/*     */   static class AIGuardianAttack
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntityGuardian theEntity;
/*     */     private int tickCounter;
/*     */     private final boolean field_190881_c;
/*     */     
/*     */     public AIGuardianAttack(EntityGuardian guardian) {
/* 447 */       this.theEntity = guardian;
/* 448 */       this.field_190881_c = guardian instanceof EntityElderGuardian;
/* 449 */       setMutexBits(3);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 454 */       EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
/* 455 */       return (entitylivingbase != null && entitylivingbase.isEntityAlive());
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 460 */       return (super.continueExecuting() && (this.field_190881_c || this.theEntity.getDistanceSqToEntity((Entity)this.theEntity.getAttackTarget()) > 9.0D));
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 465 */       this.tickCounter = -10;
/* 466 */       this.theEntity.getNavigator().clearPathEntity();
/* 467 */       this.theEntity.getLookHelper().setLookPositionWithEntity((Entity)this.theEntity.getAttackTarget(), 90.0F, 90.0F);
/* 468 */       this.theEntity.isAirBorne = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 473 */       this.theEntity.setTargetedEntity(0);
/* 474 */       this.theEntity.setAttackTarget(null);
/* 475 */       this.theEntity.wander.makeUpdate();
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 480 */       EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
/* 481 */       this.theEntity.getNavigator().clearPathEntity();
/* 482 */       this.theEntity.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 90.0F, 90.0F);
/*     */       
/* 484 */       if (!this.theEntity.canEntityBeSeen((Entity)entitylivingbase)) {
/*     */         
/* 486 */         this.theEntity.setAttackTarget(null);
/*     */       }
/*     */       else {
/*     */         
/* 490 */         this.tickCounter++;
/*     */         
/* 492 */         if (this.tickCounter == 0) {
/*     */           
/* 494 */           this.theEntity.setTargetedEntity(this.theEntity.getAttackTarget().getEntityId());
/* 495 */           this.theEntity.world.setEntityState((Entity)this.theEntity, (byte)21);
/*     */         }
/* 497 */         else if (this.tickCounter >= this.theEntity.getAttackDuration()) {
/*     */           
/* 499 */           float f = 1.0F;
/*     */           
/* 501 */           if (this.theEntity.world.getDifficulty() == EnumDifficulty.HARD)
/*     */           {
/* 503 */             f += 2.0F;
/*     */           }
/*     */           
/* 506 */           if (this.field_190881_c)
/*     */           {
/* 508 */             f += 2.0F;
/*     */           }
/*     */           
/* 511 */           entitylivingbase.attackEntityFrom(DamageSource.causeIndirectMagicDamage((Entity)this.theEntity, (Entity)this.theEntity), f);
/* 512 */           entitylivingbase.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this.theEntity), (float)this.theEntity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
/* 513 */           this.theEntity.setAttackTarget(null);
/*     */         } 
/*     */         
/* 516 */         super.updateTask();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class GuardianMoveHelper
/*     */     extends EntityMoveHelper
/*     */   {
/*     */     private final EntityGuardian entityGuardian;
/*     */     
/*     */     public GuardianMoveHelper(EntityGuardian guardian) {
/* 527 */       super((EntityLiving)guardian);
/* 528 */       this.entityGuardian = guardian;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUpdateMoveHelper() {
/* 533 */       if (this.action == EntityMoveHelper.Action.MOVE_TO && !this.entityGuardian.getNavigator().noPath()) {
/*     */         
/* 535 */         double d0 = this.posX - this.entityGuardian.posX;
/* 536 */         double d1 = this.posY - this.entityGuardian.posY;
/* 537 */         double d2 = this.posZ - this.entityGuardian.posZ;
/* 538 */         double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/* 539 */         d1 /= d3;
/* 540 */         float f = (float)(MathHelper.atan2(d2, d0) * 57.29577951308232D) - 90.0F;
/* 541 */         this.entityGuardian.rotationYaw = limitAngle(this.entityGuardian.rotationYaw, f, 90.0F);
/* 542 */         this.entityGuardian.renderYawOffset = this.entityGuardian.rotationYaw;
/* 543 */         float f1 = (float)(this.speed * this.entityGuardian.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
/* 544 */         this.entityGuardian.setAIMoveSpeed(this.entityGuardian.getAIMoveSpeed() + (f1 - this.entityGuardian.getAIMoveSpeed()) * 0.125F);
/* 545 */         double d4 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.5D) * 0.05D;
/* 546 */         double d5 = Math.cos((this.entityGuardian.rotationYaw * 0.017453292F));
/* 547 */         double d6 = Math.sin((this.entityGuardian.rotationYaw * 0.017453292F));
/* 548 */         this.entityGuardian.motionX += d4 * d5;
/* 549 */         this.entityGuardian.motionZ += d4 * d6;
/* 550 */         d4 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.75D) * 0.05D;
/* 551 */         this.entityGuardian.motionY += d4 * (d6 + d5) * 0.25D;
/* 552 */         this.entityGuardian.motionY += this.entityGuardian.getAIMoveSpeed() * d1 * 0.1D;
/* 553 */         EntityLookHelper entitylookhelper = this.entityGuardian.getLookHelper();
/* 554 */         double d7 = this.entityGuardian.posX + d0 / d3 * 2.0D;
/* 555 */         double d8 = this.entityGuardian.getEyeHeight() + this.entityGuardian.posY + d1 / d3;
/* 556 */         double d9 = this.entityGuardian.posZ + d2 / d3 * 2.0D;
/* 557 */         double d10 = entitylookhelper.getLookPosX();
/* 558 */         double d11 = entitylookhelper.getLookPosY();
/* 559 */         double d12 = entitylookhelper.getLookPosZ();
/*     */         
/* 561 */         if (!entitylookhelper.getIsLooking()) {
/*     */           
/* 563 */           d10 = d7;
/* 564 */           d11 = d8;
/* 565 */           d12 = d9;
/*     */         } 
/*     */         
/* 568 */         this.entityGuardian.getLookHelper().setLookPosition(d10 + (d7 - d10) * 0.125D, d11 + (d8 - d11) * 0.125D, d12 + (d9 - d12) * 0.125D, 10.0F, 40.0F);
/* 569 */         this.entityGuardian.setMoving(true);
/*     */       }
/*     */       else {
/*     */         
/* 573 */         this.entityGuardian.setAIMoveSpeed(0.0F);
/* 574 */         this.entityGuardian.setMoving(false);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class GuardianTargetSelector
/*     */     implements Predicate<EntityLivingBase>
/*     */   {
/*     */     private final EntityGuardian parentEntity;
/*     */     
/*     */     public GuardianTargetSelector(EntityGuardian guardian) {
/* 585 */       this.parentEntity = guardian;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean apply(@Nullable EntityLivingBase p_apply_1_) {
/* 590 */       return ((p_apply_1_ instanceof EntityPlayer || p_apply_1_ instanceof net.minecraft.entity.passive.EntitySquid) && p_apply_1_.getDistanceSqToEntity((Entity)this.parentEntity) > 9.0D);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityGuardian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */