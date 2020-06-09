/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackMelee;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.pathfinding.PathNavigateClimber;
/*     */ import net.minecraft.potion.Potion;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntitySpider extends EntityMob {
/*  41 */   private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntitySpider.class, DataSerializers.BYTE);
/*     */ 
/*     */   
/*     */   public EntitySpider(World worldIn) {
/*  45 */     super(worldIn);
/*  46 */     setSize(1.4F, 0.9F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesSpider(DataFixer fixer) {
/*  51 */     EntityLiving.registerFixesMob(fixer, EntitySpider.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  56 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  57 */     this.tasks.addTask(3, (EntityAIBase)new EntityAILeapAtTarget((EntityLiving)this, 0.4F));
/*  58 */     this.tasks.addTask(4, (EntityAIBase)new AISpiderAttack(this));
/*  59 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWanderAvoidWater(this, 0.8D));
/*  60 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  61 */     this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  62 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*  63 */     this.targetTasks.addTask(2, (EntityAIBase)new AISpiderTarget<>(this, EntityPlayer.class));
/*  64 */     this.targetTasks.addTask(3, (EntityAIBase)new AISpiderTarget<>(this, EntityIronGolem.class));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getMountedYOffset() {
/*  72 */     return (this.height * 0.5F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PathNavigate getNewNavigator(World worldIn) {
/*  80 */     return (PathNavigate)new PathNavigateClimber((EntityLiving)this, worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  85 */     super.entityInit();
/*  86 */     this.dataManager.register(CLIMBING, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  94 */     super.onUpdate();
/*     */     
/*  96 */     if (!this.world.isRemote)
/*     */     {
/*  98 */       setBesideClimbableBlock(this.isCollidedHorizontally);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 104 */     super.applyEntityAttributes();
/* 105 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
/* 106 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 111 */     return SoundEvents.ENTITY_SPIDER_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 116 */     return SoundEvents.ENTITY_SPIDER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 121 */     return SoundEvents.ENTITY_SPIDER_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 126 */     playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 132 */     return LootTableList.ENTITIES_SPIDER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnLadder() {
/* 140 */     return isBesideClimbableBlock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInWeb() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 155 */     return EnumCreatureAttribute.ARTHROPOD;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPotionApplicable(PotionEffect potioneffectIn) {
/* 160 */     return (potioneffectIn.getPotion() == MobEffects.POISON) ? false : super.isPotionApplicable(potioneffectIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBesideClimbableBlock() {
/* 169 */     return ((((Byte)this.dataManager.get(CLIMBING)).byteValue() & 0x1) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBesideClimbableBlock(boolean climbing) {
/* 178 */     byte b0 = ((Byte)this.dataManager.get(CLIMBING)).byteValue();
/*     */     
/* 180 */     if (climbing) {
/*     */       
/* 182 */       b0 = (byte)(b0 | 0x1);
/*     */     }
/*     */     else {
/*     */       
/* 186 */       b0 = (byte)(b0 & 0xFFFFFFFE);
/*     */     } 
/*     */     
/* 189 */     this.dataManager.set(CLIMBING, Byte.valueOf(b0));
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
/* 200 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/*     */     
/* 202 */     if (this.world.rand.nextInt(100) == 0) {
/*     */       
/* 204 */       EntitySkeleton entityskeleton = new EntitySkeleton(this.world);
/* 205 */       entityskeleton.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
/* 206 */       entityskeleton.onInitialSpawn(difficulty, (IEntityLivingData)null);
/* 207 */       this.world.spawnEntityInWorld((Entity)entityskeleton);
/* 208 */       entityskeleton.startRiding((Entity)this);
/*     */     } 
/*     */     
/* 211 */     if (livingdata == null) {
/*     */       
/* 213 */       livingdata = new GroupData();
/*     */       
/* 215 */       if (this.world.getDifficulty() == EnumDifficulty.HARD && this.world.rand.nextFloat() < 0.1F * difficulty.getClampedAdditionalDifficulty())
/*     */       {
/* 217 */         ((GroupData)livingdata).setRandomEffect(this.world.rand);
/*     */       }
/*     */     } 
/*     */     
/* 221 */     if (livingdata instanceof GroupData) {
/*     */       
/* 223 */       Potion potion = ((GroupData)livingdata).effect;
/*     */       
/* 225 */       if (potion != null)
/*     */       {
/* 227 */         addPotionEffect(new PotionEffect(potion, 2147483647));
/*     */       }
/*     */     } 
/*     */     
/* 231 */     return livingdata;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 236 */     return 0.65F;
/*     */   }
/*     */   
/*     */   static class AISpiderAttack
/*     */     extends EntityAIAttackMelee
/*     */   {
/*     */     public AISpiderAttack(EntitySpider spider) {
/* 243 */       super(spider, 1.0D, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 248 */       float f = this.attacker.getBrightness();
/*     */       
/* 250 */       if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
/*     */         
/* 252 */         this.attacker.setAttackTarget(null);
/* 253 */         return false;
/*     */       } 
/*     */ 
/*     */       
/* 257 */       return super.continueExecuting();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected double getAttackReachSqr(EntityLivingBase attackTarget) {
/* 263 */       return (4.0F + attackTarget.width);
/*     */     }
/*     */   }
/*     */   
/*     */   static class AISpiderTarget<T extends EntityLivingBase>
/*     */     extends EntityAINearestAttackableTarget<T>
/*     */   {
/*     */     public AISpiderTarget(EntitySpider spider, Class<T> classTarget) {
/* 271 */       super(spider, classTarget, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 276 */       float f = this.taskOwner.getBrightness();
/* 277 */       return (f >= 0.5F) ? false : super.shouldExecute();
/*     */     }
/*     */   }
/*     */   
/*     */   public static class GroupData
/*     */     implements IEntityLivingData
/*     */   {
/*     */     public Potion effect;
/*     */     
/*     */     public void setRandomEffect(Random rand) {
/* 287 */       int i = rand.nextInt(5);
/*     */       
/* 289 */       if (i <= 1) {
/*     */         
/* 291 */         this.effect = MobEffects.SPEED;
/*     */       }
/* 293 */       else if (i <= 2) {
/*     */         
/* 295 */         this.effect = MobEffects.STRENGTH;
/*     */       }
/* 297 */       else if (i <= 3) {
/*     */         
/* 299 */         this.effect = MobEffects.REGENERATION;
/*     */       }
/* 301 */       else if (i <= 4) {
/*     */         
/* 303 */         this.effect = MobEffects.INVISIBILITY;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntitySpider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */