/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackMelee;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFollowParent;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.passive.EntityAnimal;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityPolarBear extends EntityAnimal {
/*  39 */   private static final DataParameter<Boolean> IS_STANDING = EntityDataManager.createKey(EntityPolarBear.class, DataSerializers.BOOLEAN);
/*     */   
/*     */   private float clientSideStandAnimation0;
/*     */   private float clientSideStandAnimation;
/*     */   private int warningSoundTicks;
/*     */   
/*     */   public EntityPolarBear(World worldIn) {
/*  46 */     super(worldIn);
/*  47 */     setSize(1.3F, 1.4F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable ageable) {
/*  52 */     return (EntityAgeable)new EntityPolarBear(this.world);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/*  61 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  66 */     super.initEntityAI();
/*  67 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  68 */     this.tasks.addTask(1, (EntityAIBase)new AIMeleeAttack());
/*  69 */     this.tasks.addTask(1, (EntityAIBase)new AIPanic());
/*  70 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIFollowParent(this, 1.25D));
/*  71 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWander((EntityCreature)this, 1.0D));
/*  72 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  73 */     this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  74 */     this.targetTasks.addTask(1, (EntityAIBase)new AIHurtByTarget());
/*  75 */     this.targetTasks.addTask(2, (EntityAIBase)new AIAttackPlayer());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  80 */     super.applyEntityAttributes();
/*  81 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
/*  82 */     getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
/*  83 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
/*  84 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
/*  85 */     getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  90 */     return isChild() ? SoundEvents.ENTITY_POLAR_BEAR_BABY_AMBIENT : SoundEvents.ENTITY_POLAR_BEAR_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  95 */     return SoundEvents.ENTITY_POLAR_BEAR_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 100 */     return SoundEvents.ENTITY_POLAR_BEAR_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 105 */     playSound(SoundEvents.ENTITY_POLAR_BEAR_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playWarningSound() {
/* 110 */     if (this.warningSoundTicks <= 0) {
/*     */       
/* 112 */       playSound(SoundEvents.ENTITY_POLAR_BEAR_WARNING, 1.0F, 1.0F);
/* 113 */       this.warningSoundTicks = 40;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 120 */     return LootTableList.ENTITIES_POLAR_BEAR;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 125 */     super.entityInit();
/* 126 */     this.dataManager.register(IS_STANDING, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 134 */     super.onUpdate();
/*     */     
/* 136 */     if (this.world.isRemote) {
/*     */       
/* 138 */       this.clientSideStandAnimation0 = this.clientSideStandAnimation;
/*     */       
/* 140 */       if (isStanding()) {
/*     */         
/* 142 */         this.clientSideStandAnimation = MathHelper.clamp(this.clientSideStandAnimation + 1.0F, 0.0F, 6.0F);
/*     */       }
/*     */       else {
/*     */         
/* 146 */         this.clientSideStandAnimation = MathHelper.clamp(this.clientSideStandAnimation - 1.0F, 0.0F, 6.0F);
/*     */       } 
/*     */     } 
/*     */     
/* 150 */     if (this.warningSoundTicks > 0)
/*     */     {
/* 152 */       this.warningSoundTicks--;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 158 */     boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (int)getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue());
/*     */     
/* 160 */     if (flag)
/*     */     {
/* 162 */       applyEnchantments((EntityLivingBase)this, entityIn);
/*     */     }
/*     */     
/* 165 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isStanding() {
/* 170 */     return ((Boolean)this.dataManager.get(IS_STANDING)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStanding(boolean standing) {
/* 175 */     this.dataManager.set(IS_STANDING, Boolean.valueOf(standing));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getStandingAnimationScale(float p_189795_1_) {
/* 180 */     return (this.clientSideStandAnimation0 + (this.clientSideStandAnimation - this.clientSideStandAnimation0) * p_189795_1_) / 6.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float getWaterSlowDown() {
/* 185 */     return 0.98F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/* 194 */     if (livingdata instanceof GroupData) {
/*     */       
/* 196 */       if (((GroupData)livingdata).madeParent)
/*     */       {
/* 198 */         setGrowingAge(-24000);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 203 */       GroupData entitypolarbear$groupdata = new GroupData(null);
/* 204 */       entitypolarbear$groupdata.madeParent = true;
/* 205 */       livingdata = entitypolarbear$groupdata;
/*     */     } 
/*     */     
/* 208 */     return livingdata;
/*     */   }
/*     */   
/*     */   class AIAttackPlayer
/*     */     extends EntityAINearestAttackableTarget<EntityPlayer>
/*     */   {
/*     */     public AIAttackPlayer() {
/* 215 */       super((EntityCreature)EntityPolarBear.this, EntityPlayer.class, 20, true, true, null);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 220 */       if (EntityPolarBear.this.isChild())
/*     */       {
/* 222 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 226 */       if (super.shouldExecute())
/*     */       {
/* 228 */         for (EntityPolarBear entitypolarbear : EntityPolarBear.this.world.getEntitiesWithinAABB(EntityPolarBear.class, EntityPolarBear.this.getEntityBoundingBox().expand(8.0D, 4.0D, 8.0D))) {
/*     */           
/* 230 */           if (entitypolarbear.isChild())
/*     */           {
/* 232 */             return true;
/*     */           }
/*     */         } 
/*     */       }
/*     */       
/* 237 */       EntityPolarBear.this.setAttackTarget(null);
/* 238 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected double getTargetDistance() {
/* 244 */       return super.getTargetDistance() * 0.5D;
/*     */     }
/*     */   }
/*     */   
/*     */   class AIHurtByTarget
/*     */     extends EntityAIHurtByTarget
/*     */   {
/*     */     public AIHurtByTarget() {
/* 252 */       super((EntityCreature)EntityPolarBear.this, false, new Class[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 257 */       super.startExecuting();
/*     */       
/* 259 */       if (EntityPolarBear.this.isChild()) {
/*     */         
/* 261 */         alertOthers();
/* 262 */         resetTask();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn) {
/* 268 */       if (creatureIn instanceof EntityPolarBear && !creatureIn.isChild())
/*     */       {
/* 270 */         super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   class AIMeleeAttack
/*     */     extends EntityAIAttackMelee
/*     */   {
/*     */     public AIMeleeAttack() {
/* 279 */       super((EntityCreature)EntityPolarBear.this, 1.25D, true);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void checkAndPerformAttack(EntityLivingBase p_190102_1_, double p_190102_2_) {
/* 284 */       double d0 = getAttackReachSqr(p_190102_1_);
/*     */       
/* 286 */       if (p_190102_2_ <= d0 && this.attackTick <= 0) {
/*     */         
/* 288 */         this.attackTick = 20;
/* 289 */         this.attacker.attackEntityAsMob((Entity)p_190102_1_);
/* 290 */         EntityPolarBear.this.setStanding(false);
/*     */       }
/* 292 */       else if (p_190102_2_ <= d0 * 2.0D) {
/*     */         
/* 294 */         if (this.attackTick <= 0) {
/*     */           
/* 296 */           EntityPolarBear.this.setStanding(false);
/* 297 */           this.attackTick = 20;
/*     */         } 
/*     */         
/* 300 */         if (this.attackTick <= 10)
/*     */         {
/* 302 */           EntityPolarBear.this.setStanding(true);
/* 303 */           EntityPolarBear.this.playWarningSound();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 308 */         this.attackTick = 20;
/* 309 */         EntityPolarBear.this.setStanding(false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 315 */       EntityPolarBear.this.setStanding(false);
/* 316 */       super.resetTask();
/*     */     }
/*     */ 
/*     */     
/*     */     protected double getAttackReachSqr(EntityLivingBase attackTarget) {
/* 321 */       return (4.0F + attackTarget.width);
/*     */     }
/*     */   }
/*     */   
/*     */   class AIPanic
/*     */     extends EntityAIPanic
/*     */   {
/*     */     public AIPanic() {
/* 329 */       super((EntityCreature)EntityPolarBear.this, 2.0D);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 334 */       return (!EntityPolarBear.this.isChild() && !EntityPolarBear.this.isBurning()) ? false : super.shouldExecute();
/*     */     }
/*     */   }
/*     */   
/*     */   static class GroupData implements IEntityLivingData {
/*     */     public boolean madeParent;
/*     */     
/*     */     private GroupData() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityPolarBear.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */