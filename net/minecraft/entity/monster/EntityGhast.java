/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityFlying;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFindEntityNearestPlayer;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityGhast
/*     */   extends EntityFlying implements IMob {
/*  34 */   private static final DataParameter<Boolean> ATTACKING = EntityDataManager.createKey(EntityGhast.class, DataSerializers.BOOLEAN);
/*     */ 
/*     */   
/*  37 */   private int explosionStrength = 1;
/*     */ 
/*     */   
/*     */   public EntityGhast(World worldIn) {
/*  41 */     super(worldIn);
/*  42 */     setSize(4.0F, 4.0F);
/*  43 */     this.isImmuneToFire = true;
/*  44 */     this.experienceValue = 5;
/*  45 */     this.moveHelper = new GhastMoveHelper(this);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  50 */     this.tasks.addTask(5, new AIRandomFly(this));
/*  51 */     this.tasks.addTask(7, new AILookAround(this));
/*  52 */     this.tasks.addTask(7, new AIFireballAttack(this));
/*  53 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIFindEntityNearestPlayer((EntityLiving)this));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAttacking() {
/*  58 */     return ((Boolean)this.dataManager.get(ATTACKING)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAttacking(boolean attacking) {
/*  63 */     this.dataManager.set(ATTACKING, Boolean.valueOf(attacking));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFireballStrength() {
/*  68 */     return this.explosionStrength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  76 */     super.onUpdate();
/*     */     
/*  78 */     if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL)
/*     */     {
/*  80 */       setDead();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  89 */     if (isEntityInvulnerable(source))
/*     */     {
/*  91 */       return false;
/*     */     }
/*  93 */     if (source.getSourceOfDamage() instanceof EntityLargeFireball && source.getEntity() instanceof net.minecraft.entity.player.EntityPlayer) {
/*     */       
/*  95 */       super.attackEntityFrom(source, 1000.0F);
/*  96 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 100 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 106 */     super.entityInit();
/* 107 */     this.dataManager.register(ATTACKING, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 112 */     super.applyEntityAttributes();
/* 113 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
/* 114 */     getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(100.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundCategory getSoundCategory() {
/* 119 */     return SoundCategory.HOSTILE;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 124 */     return SoundEvents.ENTITY_GHAST_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 129 */     return SoundEvents.ENTITY_GHAST_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 134 */     return SoundEvents.ENTITY_GHAST_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 140 */     return LootTableList.ENTITIES_GHAST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 148 */     return 10.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 156 */     return (this.rand.nextInt(20) == 0 && super.getCanSpawnHere() && this.world.getDifficulty() != EnumDifficulty.PEACEFUL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxSpawnedInChunk() {
/* 164 */     return 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesGhast(DataFixer fixer) {
/* 169 */     EntityLiving.registerFixesMob(fixer, EntityGhast.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 177 */     super.writeEntityToNBT(compound);
/* 178 */     compound.setInteger("ExplosionPower", this.explosionStrength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 186 */     super.readEntityFromNBT(compound);
/*     */     
/* 188 */     if (compound.hasKey("ExplosionPower", 99))
/*     */     {
/* 190 */       this.explosionStrength = compound.getInteger("ExplosionPower");
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 196 */     return 2.6F;
/*     */   }
/*     */   
/*     */   static class AIFireballAttack
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntityGhast parentEntity;
/*     */     public int attackTimer;
/*     */     
/*     */     public AIFireballAttack(EntityGhast ghast) {
/* 206 */       this.parentEntity = ghast;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 211 */       return (this.parentEntity.getAttackTarget() != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 216 */       this.attackTimer = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 221 */       this.parentEntity.setAttacking(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 226 */       EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
/* 227 */       double d0 = 64.0D;
/*     */       
/* 229 */       if (entitylivingbase.getDistanceSqToEntity((Entity)this.parentEntity) < 4096.0D && this.parentEntity.canEntityBeSeen((Entity)entitylivingbase)) {
/*     */         
/* 231 */         World world = this.parentEntity.world;
/* 232 */         this.attackTimer++;
/*     */         
/* 234 */         if (this.attackTimer == 10)
/*     */         {
/* 236 */           world.playEvent(null, 1015, new BlockPos((Entity)this.parentEntity), 0);
/*     */         }
/*     */         
/* 239 */         if (this.attackTimer == 20)
/*     */         {
/* 241 */           double d1 = 4.0D;
/* 242 */           Vec3d vec3d = this.parentEntity.getLook(1.0F);
/* 243 */           double d2 = entitylivingbase.posX - this.parentEntity.posX + vec3d.xCoord * 4.0D;
/* 244 */           double d3 = (entitylivingbase.getEntityBoundingBox()).minY + (entitylivingbase.height / 2.0F) - 0.5D + this.parentEntity.posY + (this.parentEntity.height / 2.0F);
/* 245 */           double d4 = entitylivingbase.posZ - this.parentEntity.posZ + vec3d.zCoord * 4.0D;
/* 246 */           world.playEvent(null, 1016, new BlockPos((Entity)this.parentEntity), 0);
/* 247 */           EntityLargeFireball entitylargefireball = new EntityLargeFireball(world, (EntityLivingBase)this.parentEntity, d2, d3, d4);
/* 248 */           entitylargefireball.explosionPower = this.parentEntity.getFireballStrength();
/* 249 */           entitylargefireball.posX = this.parentEntity.posX + vec3d.xCoord * 4.0D;
/* 250 */           entitylargefireball.posY = this.parentEntity.posY + (this.parentEntity.height / 2.0F) + 0.5D;
/* 251 */           entitylargefireball.posZ = this.parentEntity.posZ + vec3d.zCoord * 4.0D;
/* 252 */           world.spawnEntityInWorld((Entity)entitylargefireball);
/* 253 */           this.attackTimer = -40;
/*     */         }
/*     */       
/* 256 */       } else if (this.attackTimer > 0) {
/*     */         
/* 258 */         this.attackTimer--;
/*     */       } 
/*     */       
/* 261 */       this.parentEntity.setAttacking((this.attackTimer > 10));
/*     */     }
/*     */   }
/*     */   
/*     */   static class AILookAround
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntityGhast parentEntity;
/*     */     
/*     */     public AILookAround(EntityGhast ghast) {
/* 271 */       this.parentEntity = ghast;
/* 272 */       setMutexBits(2);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 277 */       return true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 282 */       if (this.parentEntity.getAttackTarget() == null) {
/*     */         
/* 284 */         this.parentEntity.rotationYaw = -((float)MathHelper.atan2(this.parentEntity.motionX, this.parentEntity.motionZ)) * 57.295776F;
/* 285 */         this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
/*     */       }
/*     */       else {
/*     */         
/* 289 */         EntityLivingBase entitylivingbase = this.parentEntity.getAttackTarget();
/* 290 */         double d0 = 64.0D;
/*     */         
/* 292 */         if (entitylivingbase.getDistanceSqToEntity((Entity)this.parentEntity) < 4096.0D) {
/*     */           
/* 294 */           double d1 = entitylivingbase.posX - this.parentEntity.posX;
/* 295 */           double d2 = entitylivingbase.posZ - this.parentEntity.posZ;
/* 296 */           this.parentEntity.rotationYaw = -((float)MathHelper.atan2(d1, d2)) * 57.295776F;
/* 297 */           this.parentEntity.renderYawOffset = this.parentEntity.rotationYaw;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIRandomFly
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntityGhast parentEntity;
/*     */     
/*     */     public AIRandomFly(EntityGhast ghast) {
/* 309 */       this.parentEntity = ghast;
/* 310 */       setMutexBits(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 315 */       EntityMoveHelper entitymovehelper = this.parentEntity.getMoveHelper();
/*     */       
/* 317 */       if (!entitymovehelper.isUpdating())
/*     */       {
/* 319 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 323 */       double d0 = entitymovehelper.getX() - this.parentEntity.posX;
/* 324 */       double d1 = entitymovehelper.getY() - this.parentEntity.posY;
/* 325 */       double d2 = entitymovehelper.getZ() - this.parentEntity.posZ;
/* 326 */       double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/* 327 */       return !(d3 >= 1.0D && d3 <= 3600.0D);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 333 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 338 */       Random random = this.parentEntity.getRNG();
/* 339 */       double d0 = this.parentEntity.posX + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/* 340 */       double d1 = this.parentEntity.posY + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/* 341 */       double d2 = this.parentEntity.posZ + ((random.nextFloat() * 2.0F - 1.0F) * 16.0F);
/* 342 */       this.parentEntity.getMoveHelper().setMoveTo(d0, d1, d2, 1.0D);
/*     */     }
/*     */   }
/*     */   
/*     */   static class GhastMoveHelper
/*     */     extends EntityMoveHelper
/*     */   {
/*     */     private final EntityGhast parentEntity;
/*     */     private int courseChangeCooldown;
/*     */     
/*     */     public GhastMoveHelper(EntityGhast ghast) {
/* 353 */       super((EntityLiving)ghast);
/* 354 */       this.parentEntity = ghast;
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUpdateMoveHelper() {
/* 359 */       if (this.action == EntityMoveHelper.Action.MOVE_TO) {
/*     */         
/* 361 */         double d0 = this.posX - this.parentEntity.posX;
/* 362 */         double d1 = this.posY - this.parentEntity.posY;
/* 363 */         double d2 = this.posZ - this.parentEntity.posZ;
/* 364 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*     */         
/* 366 */         if (this.courseChangeCooldown-- <= 0) {
/*     */           
/* 368 */           this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(5) + 2;
/* 369 */           d3 = MathHelper.sqrt(d3);
/*     */           
/* 371 */           if (isNotColliding(this.posX, this.posY, this.posZ, d3)) {
/*     */             
/* 373 */             this.parentEntity.motionX += d0 / d3 * 0.1D;
/* 374 */             this.parentEntity.motionY += d1 / d3 * 0.1D;
/* 375 */             this.parentEntity.motionZ += d2 / d3 * 0.1D;
/*     */           }
/*     */           else {
/*     */             
/* 379 */             this.action = EntityMoveHelper.Action.WAIT;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean isNotColliding(double x, double y, double z, double p_179926_7_) {
/* 387 */       double d0 = (x - this.parentEntity.posX) / p_179926_7_;
/* 388 */       double d1 = (y - this.parentEntity.posY) / p_179926_7_;
/* 389 */       double d2 = (z - this.parentEntity.posZ) / p_179926_7_;
/* 390 */       AxisAlignedBB axisalignedbb = this.parentEntity.getEntityBoundingBox();
/*     */       
/* 392 */       for (int i = 1; i < p_179926_7_; i++) {
/*     */         
/* 394 */         axisalignedbb = axisalignedbb.offset(d0, d1, d2);
/*     */         
/* 396 */         if (!this.parentEntity.world.getCollisionBoxes((Entity)this.parentEntity, axisalignedbb).isEmpty())
/*     */         {
/* 398 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 402 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityGhast.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */