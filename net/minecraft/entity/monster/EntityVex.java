/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITarget;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityVex extends EntityMob {
/*  39 */   protected static final DataParameter<Byte> field_190664_a = EntityDataManager.createKey(EntityVex.class, DataSerializers.BYTE);
/*     */   
/*     */   private EntityLiving field_190665_b;
/*     */   @Nullable
/*     */   private BlockPos field_190666_c;
/*     */   private boolean field_190667_bw;
/*     */   private int field_190668_bx;
/*     */   
/*     */   public EntityVex(World p_i47280_1_) {
/*  48 */     super(p_i47280_1_);
/*  49 */     this.isImmuneToFire = true;
/*  50 */     this.moveHelper = new AIMoveControl(this);
/*  51 */     setSize(0.4F, 0.8F);
/*  52 */     this.experienceValue = 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntity(MoverType x, double p_70091_2_, double p_70091_4_, double p_70091_6_) {
/*  60 */     super.moveEntity(x, p_70091_2_, p_70091_4_, p_70091_6_);
/*  61 */     doBlockCollisions();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  69 */     this.noClip = true;
/*  70 */     super.onUpdate();
/*  71 */     this.noClip = false;
/*  72 */     setNoGravity(true);
/*     */     
/*  74 */     if (this.field_190667_bw && --this.field_190668_bx <= 0) {
/*     */       
/*  76 */       this.field_190668_bx = 20;
/*  77 */       attackEntityFrom(DamageSource.starve, 1.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  83 */     super.initEntityAI();
/*  84 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  85 */     this.tasks.addTask(4, new AIChargeAttack());
/*  86 */     this.tasks.addTask(8, new AIMoveRandom());
/*  87 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 3.0F, 1.0F));
/*  88 */     this.tasks.addTask(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0F));
/*  89 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[] { EntityVex.class }));
/*  90 */     this.targetTasks.addTask(2, (EntityAIBase)new AICopyOwnerTarget(this));
/*  91 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  96 */     super.applyEntityAttributes();
/*  97 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(14.0D);
/*  98 */     getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 103 */     super.entityInit();
/* 104 */     this.dataManager.register(field_190664_a, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_190663_b(DataFixer p_190663_0_) {
/* 109 */     EntityLiving.registerFixesMob(p_190663_0_, EntityVex.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 117 */     super.readEntityFromNBT(compound);
/*     */     
/* 119 */     if (compound.hasKey("BoundX"))
/*     */     {
/* 121 */       this.field_190666_c = new BlockPos(compound.getInteger("BoundX"), compound.getInteger("BoundY"), compound.getInteger("BoundZ"));
/*     */     }
/*     */     
/* 124 */     if (compound.hasKey("LifeTicks"))
/*     */     {
/* 126 */       func_190653_a(compound.getInteger("LifeTicks"));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 135 */     super.writeEntityToNBT(compound);
/*     */     
/* 137 */     if (this.field_190666_c != null) {
/*     */       
/* 139 */       compound.setInteger("BoundX", this.field_190666_c.getX());
/* 140 */       compound.setInteger("BoundY", this.field_190666_c.getY());
/* 141 */       compound.setInteger("BoundZ", this.field_190666_c.getZ());
/*     */     } 
/*     */     
/* 144 */     if (this.field_190667_bw)
/*     */     {
/* 146 */       compound.setInteger("LifeTicks", this.field_190668_bx);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLiving func_190645_o() {
/* 152 */     return this.field_190665_b;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockPos func_190646_di() {
/* 158 */     return this.field_190666_c;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190651_g(@Nullable BlockPos p_190651_1_) {
/* 163 */     this.field_190666_c = p_190651_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_190656_b(int p_190656_1_) {
/* 168 */     int i = ((Byte)this.dataManager.get(field_190664_a)).byteValue();
/* 169 */     return ((i & p_190656_1_) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190660_a(int p_190660_1_, boolean p_190660_2_) {
/* 174 */     int i = ((Byte)this.dataManager.get(field_190664_a)).byteValue();
/*     */     
/* 176 */     if (p_190660_2_) {
/*     */       
/* 178 */       i |= p_190660_1_;
/*     */     }
/*     */     else {
/*     */       
/* 182 */       i &= p_190660_1_ ^ 0xFFFFFFFF;
/*     */     } 
/*     */     
/* 185 */     this.dataManager.set(field_190664_a, Byte.valueOf((byte)(i & 0xFF)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190647_dj() {
/* 190 */     return func_190656_b(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190648_a(boolean p_190648_1_) {
/* 195 */     func_190660_a(1, p_190648_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190658_a(EntityLiving p_190658_1_) {
/* 200 */     this.field_190665_b = p_190658_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190653_a(int p_190653_1_) {
/* 205 */     this.field_190667_bw = true;
/* 206 */     this.field_190668_bx = p_190653_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 211 */     return SoundEvents.field_191264_hc;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 216 */     return SoundEvents.field_191266_he;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 221 */     return SoundEvents.field_191267_hf;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 227 */     return LootTableList.field_191188_ax;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender() {
/* 232 */     return 15728880;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness() {
/* 240 */     return 1.0F;
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
/* 251 */     setEquipmentBasedOnDifficulty(difficulty);
/* 252 */     setEnchantmentBasedOnDifficulty(difficulty);
/* 253 */     return super.onInitialSpawn(difficulty, livingdata);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 261 */     setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_SWORD));
/* 262 */     setDropChance(EntityEquipmentSlot.MAINHAND, 0.0F);
/*     */   }
/*     */   
/*     */   class AIChargeAttack
/*     */     extends EntityAIBase
/*     */   {
/*     */     public AIChargeAttack() {
/* 269 */       setMutexBits(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 274 */       if (EntityVex.this.getAttackTarget() != null && !EntityVex.this.getMoveHelper().isUpdating() && EntityVex.this.rand.nextInt(7) == 0)
/*     */       {
/* 276 */         return (EntityVex.this.getDistanceSqToEntity((Entity)EntityVex.this.getAttackTarget()) > 4.0D);
/*     */       }
/*     */ 
/*     */       
/* 280 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 286 */       return (EntityVex.this.getMoveHelper().isUpdating() && EntityVex.this.func_190647_dj() && EntityVex.this.getAttackTarget() != null && EntityVex.this.getAttackTarget().isEntityAlive());
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 291 */       EntityLivingBase entitylivingbase = EntityVex.this.getAttackTarget();
/* 292 */       Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
/* 293 */       EntityVex.this.moveHelper.setMoveTo(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, 1.0D);
/* 294 */       EntityVex.this.func_190648_a(true);
/* 295 */       EntityVex.this.playSound(SoundEvents.field_191265_hd, 1.0F, 1.0F);
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 300 */       EntityVex.this.func_190648_a(false);
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 305 */       EntityLivingBase entitylivingbase = EntityVex.this.getAttackTarget();
/*     */       
/* 307 */       if (EntityVex.this.getEntityBoundingBox().intersectsWith(entitylivingbase.getEntityBoundingBox())) {
/*     */         
/* 309 */         EntityVex.this.attackEntityAsMob((Entity)entitylivingbase);
/* 310 */         EntityVex.this.func_190648_a(false);
/*     */       }
/*     */       else {
/*     */         
/* 314 */         double d0 = EntityVex.this.getDistanceSqToEntity((Entity)entitylivingbase);
/*     */         
/* 316 */         if (d0 < 9.0D) {
/*     */           
/* 318 */           Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
/* 319 */           EntityVex.this.moveHelper.setMoveTo(vec3d.xCoord, vec3d.yCoord, vec3d.zCoord, 1.0D);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   class AICopyOwnerTarget
/*     */     extends EntityAITarget
/*     */   {
/*     */     public AICopyOwnerTarget(EntityCreature p_i47231_2_) {
/* 329 */       super(p_i47231_2_, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 334 */       return (EntityVex.this.field_190665_b != null && EntityVex.this.field_190665_b.getAttackTarget() != null && isSuitableTarget(EntityVex.this.field_190665_b.getAttackTarget(), false));
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 339 */       EntityVex.this.setAttackTarget(EntityVex.this.field_190665_b.getAttackTarget());
/* 340 */       super.startExecuting();
/*     */     }
/*     */   }
/*     */   
/*     */   class AIMoveControl
/*     */     extends EntityMoveHelper
/*     */   {
/*     */     public AIMoveControl(EntityVex p_i47230_2_) {
/* 348 */       super((EntityLiving)p_i47230_2_);
/*     */     }
/*     */ 
/*     */     
/*     */     public void onUpdateMoveHelper() {
/* 353 */       if (this.action == EntityMoveHelper.Action.MOVE_TO) {
/*     */         
/* 355 */         double d0 = this.posX - EntityVex.this.posX;
/* 356 */         double d1 = this.posY - EntityVex.this.posY;
/* 357 */         double d2 = this.posZ - EntityVex.this.posZ;
/* 358 */         double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/* 359 */         d3 = MathHelper.sqrt(d3);
/*     */         
/* 361 */         if (d3 < EntityVex.this.getEntityBoundingBox().getAverageEdgeLength()) {
/*     */           
/* 363 */           this.action = EntityMoveHelper.Action.WAIT;
/* 364 */           EntityVex.this.motionX *= 0.5D;
/* 365 */           EntityVex.this.motionY *= 0.5D;
/* 366 */           EntityVex.this.motionZ *= 0.5D;
/*     */         }
/*     */         else {
/*     */           
/* 370 */           EntityVex.this.motionX += d0 / d3 * 0.05D * this.speed;
/* 371 */           EntityVex.this.motionY += d1 / d3 * 0.05D * this.speed;
/* 372 */           EntityVex.this.motionZ += d2 / d3 * 0.05D * this.speed;
/*     */           
/* 374 */           if (EntityVex.this.getAttackTarget() == null) {
/*     */             
/* 376 */             EntityVex.this.rotationYaw = -((float)MathHelper.atan2(EntityVex.this.motionX, EntityVex.this.motionZ)) * 57.295776F;
/* 377 */             EntityVex.this.renderYawOffset = EntityVex.this.rotationYaw;
/*     */           }
/*     */           else {
/*     */             
/* 381 */             double d4 = (EntityVex.this.getAttackTarget()).posX - EntityVex.this.posX;
/* 382 */             double d5 = (EntityVex.this.getAttackTarget()).posZ - EntityVex.this.posZ;
/* 383 */             EntityVex.this.rotationYaw = -((float)MathHelper.atan2(d4, d5)) * 57.295776F;
/* 384 */             EntityVex.this.renderYawOffset = EntityVex.this.rotationYaw;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   class AIMoveRandom
/*     */     extends EntityAIBase
/*     */   {
/*     */     public AIMoveRandom() {
/* 395 */       setMutexBits(1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 400 */       return (!EntityVex.this.getMoveHelper().isUpdating() && EntityVex.this.rand.nextInt(7) == 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 405 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 410 */       BlockPos blockpos = EntityVex.this.func_190646_di();
/*     */       
/* 412 */       if (blockpos == null)
/*     */       {
/* 414 */         blockpos = new BlockPos((Entity)EntityVex.this);
/*     */       }
/*     */       
/* 417 */       for (int i = 0; i < 3; i++) {
/*     */         
/* 419 */         BlockPos blockpos1 = blockpos.add(EntityVex.this.rand.nextInt(15) - 7, EntityVex.this.rand.nextInt(11) - 5, EntityVex.this.rand.nextInt(15) - 7);
/*     */         
/* 421 */         if (EntityVex.this.world.isAirBlock(blockpos1)) {
/*     */           
/* 423 */           EntityVex.this.moveHelper.setMoveTo(blockpos1.getX() + 0.5D, blockpos1.getY() + 0.5D, blockpos1.getZ() + 0.5D, 0.25D);
/*     */           
/* 425 */           if (EntityVex.this.getAttackTarget() == null)
/*     */           {
/* 427 */             EntityVex.this.getLookHelper().setLookPosition(blockpos1.getX() + 0.5D, blockpos1.getY() + 0.5D, blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
/*     */           }
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityVex.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */