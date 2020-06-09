/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.pathfinding.PathNodeType;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityBlaze
/*     */   extends EntityMob {
/*  35 */   private float heightOffset = 0.5F;
/*     */   
/*     */   private int heightOffsetUpdateTime;
/*     */   
/*  39 */   private static final DataParameter<Byte> ON_FIRE = EntityDataManager.createKey(EntityBlaze.class, DataSerializers.BYTE);
/*     */ 
/*     */   
/*     */   public EntityBlaze(World worldIn) {
/*  43 */     super(worldIn);
/*  44 */     setPathPriority(PathNodeType.WATER, -1.0F);
/*  45 */     setPathPriority(PathNodeType.LAVA, 8.0F);
/*  46 */     setPathPriority(PathNodeType.DANGER_FIRE, 0.0F);
/*  47 */     setPathPriority(PathNodeType.DAMAGE_FIRE, 0.0F);
/*  48 */     this.isImmuneToFire = true;
/*  49 */     this.experienceValue = 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesBlaze(DataFixer fixer) {
/*  54 */     EntityLiving.registerFixesMob(fixer, EntityBlaze.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  59 */     this.tasks.addTask(4, new AIFireballAttack(this));
/*  60 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIMoveTowardsRestriction(this, 1.0D));
/*  61 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
/*  62 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  63 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  64 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[0]));
/*  65 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  70 */     super.applyEntityAttributes();
/*  71 */     getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(6.0D);
/*  72 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
/*  73 */     getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(48.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  78 */     super.entityInit();
/*  79 */     this.dataManager.register(ON_FIRE, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  84 */     return SoundEvents.ENTITY_BLAZE_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  89 */     return SoundEvents.ENTITY_BLAZE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  94 */     return SoundEvents.ENTITY_BLAZE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender() {
/*  99 */     return 15728880;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness() {
/* 107 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 116 */     if (!this.onGround && this.motionY < 0.0D)
/*     */     {
/* 118 */       this.motionY *= 0.6D;
/*     */     }
/*     */     
/* 121 */     if (this.world.isRemote) {
/*     */       
/* 123 */       if (this.rand.nextInt(24) == 0 && !isSilent())
/*     */       {
/* 125 */         this.world.playSound(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D, SoundEvents.ENTITY_BLAZE_BURN, getSoundCategory(), 1.0F + this.rand.nextFloat(), this.rand.nextFloat() * 0.7F + 0.3F, false);
/*     */       }
/*     */       
/* 128 */       for (int i = 0; i < 2; i++)
/*     */       {
/* 130 */         this.world.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     } 
/*     */     
/* 134 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 139 */     if (isWet())
/*     */     {
/* 141 */       attackEntityFrom(DamageSource.drown, 1.0F);
/*     */     }
/*     */     
/* 144 */     this.heightOffsetUpdateTime--;
/*     */     
/* 146 */     if (this.heightOffsetUpdateTime <= 0) {
/*     */       
/* 148 */       this.heightOffsetUpdateTime = 100;
/* 149 */       this.heightOffset = 0.5F + (float)this.rand.nextGaussian() * 3.0F;
/*     */     } 
/*     */     
/* 152 */     EntityLivingBase entitylivingbase = getAttackTarget();
/*     */     
/* 154 */     if (entitylivingbase != null && entitylivingbase.posY + entitylivingbase.getEyeHeight() > this.posY + getEyeHeight() + this.heightOffset) {
/*     */       
/* 156 */       this.motionY += (0.30000001192092896D - this.motionY) * 0.30000001192092896D;
/* 157 */       this.isAirBorne = true;
/*     */     } 
/*     */     
/* 160 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/* 172 */     return isCharged();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 178 */     return LootTableList.ENTITIES_BLAZE;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCharged() {
/* 183 */     return ((((Byte)this.dataManager.get(ON_FIRE)).byteValue() & 0x1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOnFire(boolean onFire) {
/* 188 */     byte b0 = ((Byte)this.dataManager.get(ON_FIRE)).byteValue();
/*     */     
/* 190 */     if (onFire) {
/*     */       
/* 192 */       b0 = (byte)(b0 | 0x1);
/*     */     }
/*     */     else {
/*     */       
/* 196 */       b0 = (byte)(b0 & 0xFFFFFFFE);
/*     */     } 
/*     */     
/* 199 */     this.dataManager.set(ON_FIRE, Byte.valueOf(b0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 207 */     return true;
/*     */   }
/*     */   
/*     */   static class AIFireballAttack
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntityBlaze blaze;
/*     */     private int attackStep;
/*     */     private int attackTime;
/*     */     
/*     */     public AIFireballAttack(EntityBlaze blazeIn) {
/* 218 */       this.blaze = blazeIn;
/* 219 */       setMutexBits(3);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 224 */       EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
/* 225 */       return (entitylivingbase != null && entitylivingbase.isEntityAlive());
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 230 */       this.attackStep = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 235 */       this.blaze.setOnFire(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 240 */       this.attackTime--;
/* 241 */       EntityLivingBase entitylivingbase = this.blaze.getAttackTarget();
/* 242 */       double d0 = this.blaze.getDistanceSqToEntity((Entity)entitylivingbase);
/*     */       
/* 244 */       if (d0 < 4.0D) {
/*     */         
/* 246 */         if (this.attackTime <= 0) {
/*     */           
/* 248 */           this.attackTime = 20;
/* 249 */           this.blaze.attackEntityAsMob((Entity)entitylivingbase);
/*     */         } 
/*     */         
/* 252 */         this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
/*     */       }
/* 254 */       else if (d0 < func_191523_f() * func_191523_f()) {
/*     */         
/* 256 */         double d1 = entitylivingbase.posX - this.blaze.posX;
/* 257 */         double d2 = (entitylivingbase.getEntityBoundingBox()).minY + (entitylivingbase.height / 2.0F) - this.blaze.posY + (this.blaze.height / 2.0F);
/* 258 */         double d3 = entitylivingbase.posZ - this.blaze.posZ;
/*     */         
/* 260 */         if (this.attackTime <= 0) {
/*     */           
/* 262 */           this.attackStep++;
/*     */           
/* 264 */           if (this.attackStep == 1) {
/*     */             
/* 266 */             this.attackTime = 60;
/* 267 */             this.blaze.setOnFire(true);
/*     */           }
/* 269 */           else if (this.attackStep <= 4) {
/*     */             
/* 271 */             this.attackTime = 6;
/*     */           }
/*     */           else {
/*     */             
/* 275 */             this.attackTime = 100;
/* 276 */             this.attackStep = 0;
/* 277 */             this.blaze.setOnFire(false);
/*     */           } 
/*     */           
/* 280 */           if (this.attackStep > 1) {
/*     */             
/* 282 */             float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;
/* 283 */             this.blaze.world.playEvent(null, 1018, new BlockPos((int)this.blaze.posX, (int)this.blaze.posY, (int)this.blaze.posZ), 0);
/*     */             
/* 285 */             for (int i = 0; i < 1; i++) {
/*     */               
/* 287 */               EntitySmallFireball entitysmallfireball = new EntitySmallFireball(this.blaze.world, (EntityLivingBase)this.blaze, d1 + this.blaze.getRNG().nextGaussian() * f, d2, d3 + this.blaze.getRNG().nextGaussian() * f);
/* 288 */               entitysmallfireball.posY = this.blaze.posY + (this.blaze.height / 2.0F) + 0.5D;
/* 289 */               this.blaze.world.spawnEntityInWorld((Entity)entitysmallfireball);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 294 */         this.blaze.getLookHelper().setLookPositionWithEntity((Entity)entitylivingbase, 10.0F, 10.0F);
/*     */       }
/*     */       else {
/*     */         
/* 298 */         this.blaze.getNavigator().clearPathEntity();
/* 299 */         this.blaze.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
/*     */       } 
/*     */       
/* 302 */       super.updateTask();
/*     */     }
/*     */ 
/*     */     
/*     */     private double func_191523_f() {
/* 307 */       IAttributeInstance iattributeinstance = this.blaze.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
/* 308 */       return (iattributeinstance == null) ? 16.0D : iattributeinstance.getAttributeValue();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityBlaze.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */