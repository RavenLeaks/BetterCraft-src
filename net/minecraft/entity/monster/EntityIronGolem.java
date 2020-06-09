/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackMelee;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIDefendVillage;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookAtVillager;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
/*     */ import net.minecraft.entity.ai.EntityAIMoveTowardsTarget;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.village.Village;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityIronGolem extends EntityGolem {
/*  42 */   protected static final DataParameter<Byte> PLAYER_CREATED = EntityDataManager.createKey(EntityIronGolem.class, DataSerializers.BYTE);
/*     */   
/*     */   private int homeCheckTimer;
/*     */   
/*     */   @Nullable
/*     */   Village villageObj;
/*     */   
/*     */   private int attackTimer;
/*     */   private int holdRoseTick;
/*     */   
/*     */   public EntityIronGolem(World worldIn) {
/*  53 */     super(worldIn);
/*  54 */     setSize(1.4F, 2.7F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  59 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIAttackMelee(this, 1.0D, true));
/*  60 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIMoveTowardsTarget(this, 0.9D, 32.0F));
/*  61 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIMoveThroughVillage(this, 0.6D, true));
/*  62 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIMoveTowardsRestriction(this, 1.0D));
/*  63 */     this.tasks.addTask(5, (EntityAIBase)new EntityAILookAtVillager(this));
/*  64 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWanderAvoidWater(this, 0.6D));
/*  65 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 6.0F));
/*  66 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  67 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIDefendVillage(this));
/*  68 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*  69 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityLiving.class, 10, false, true, new Predicate<EntityLiving>()
/*     */           {
/*     */             public boolean apply(@Nullable EntityLiving p_apply_1_)
/*     */             {
/*  73 */               return (p_apply_1_ != null && IMob.VISIBLE_MOB_SELECTOR.apply(p_apply_1_) && !(p_apply_1_ instanceof EntityCreeper));
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  80 */     super.entityInit();
/*  81 */     this.dataManager.register(PLAYER_CREATED, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/*  86 */     if (--this.homeCheckTimer <= 0) {
/*     */       
/*  88 */       this.homeCheckTimer = 70 + this.rand.nextInt(50);
/*  89 */       this.villageObj = this.world.getVillageCollection().getNearestVillage(new BlockPos((Entity)this), 32);
/*     */       
/*  91 */       if (this.villageObj == null) {
/*     */         
/*  93 */         detachHome();
/*     */       }
/*     */       else {
/*     */         
/*  97 */         BlockPos blockpos = this.villageObj.getCenter();
/*  98 */         setHomePosAndDistance(blockpos, (int)(this.villageObj.getVillageRadius() * 0.6F));
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 107 */     super.applyEntityAttributes();
/* 108 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
/* 109 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
/* 110 */     getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int decreaseAirSupply(int air) {
/* 118 */     return air;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void collideWithEntity(Entity entityIn) {
/* 123 */     if (entityIn instanceof IMob && !(entityIn instanceof EntityCreeper) && getRNG().nextInt(20) == 0)
/*     */     {
/* 125 */       setAttackTarget((EntityLivingBase)entityIn);
/*     */     }
/*     */     
/* 128 */     super.collideWithEntity(entityIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 137 */     super.onLivingUpdate();
/*     */     
/* 139 */     if (this.attackTimer > 0)
/*     */     {
/* 141 */       this.attackTimer--;
/*     */     }
/*     */     
/* 144 */     if (this.holdRoseTick > 0)
/*     */     {
/* 146 */       this.holdRoseTick--;
/*     */     }
/*     */     
/* 149 */     if (this.motionX * this.motionX + this.motionZ * this.motionZ > 2.500000277905201E-7D && this.rand.nextInt(5) == 0) {
/*     */       
/* 151 */       int i = MathHelper.floor(this.posX);
/* 152 */       int j = MathHelper.floor(this.posY - 0.20000000298023224D);
/* 153 */       int k = MathHelper.floor(this.posZ);
/* 154 */       IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));
/*     */       
/* 156 */       if (iblockstate.getMaterial() != Material.AIR)
/*     */       {
/* 158 */         this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, (getEntityBoundingBox()).minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, 4.0D * (this.rand.nextFloat() - 0.5D), 0.5D, (this.rand.nextFloat() - 0.5D) * 4.0D, new int[] { Block.getStateId(iblockstate) });
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canAttackClass(Class<? extends EntityLivingBase> cls) {
/* 168 */     if (isPlayerCreated() && EntityPlayer.class.isAssignableFrom(cls))
/*     */     {
/* 170 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 174 */     return (cls == EntityCreeper.class) ? false : super.canAttackClass(cls);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerFixesIronGolem(DataFixer fixer) {
/* 180 */     EntityLiving.registerFixesMob(fixer, EntityIronGolem.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 188 */     super.writeEntityToNBT(compound);
/* 189 */     compound.setBoolean("PlayerCreated", isPlayerCreated());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 197 */     super.readEntityFromNBT(compound);
/* 198 */     setPlayerCreated(compound.getBoolean("PlayerCreated"));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 203 */     this.attackTimer = 10;
/* 204 */     this.world.setEntityState((Entity)this, (byte)4);
/* 205 */     boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), (7 + this.rand.nextInt(15)));
/*     */     
/* 207 */     if (flag) {
/*     */       
/* 209 */       entityIn.motionY += 0.4000000059604645D;
/* 210 */       applyEnchantments((EntityLivingBase)this, entityIn);
/*     */     } 
/*     */     
/* 213 */     playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
/* 214 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 219 */     if (id == 4) {
/*     */       
/* 221 */       this.attackTimer = 10;
/* 222 */       playSound(SoundEvents.ENTITY_IRONGOLEM_ATTACK, 1.0F, 1.0F);
/*     */     }
/* 224 */     else if (id == 11) {
/*     */       
/* 226 */       this.holdRoseTick = 400;
/*     */     }
/* 228 */     else if (id == 34) {
/*     */       
/* 230 */       this.holdRoseTick = 0;
/*     */     }
/*     */     else {
/*     */       
/* 234 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Village getVillage() {
/* 240 */     return this.villageObj;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAttackTimer() {
/* 245 */     return this.attackTimer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setHoldingRose(boolean p_70851_1_) {
/* 250 */     if (p_70851_1_) {
/*     */       
/* 252 */       this.holdRoseTick = 400;
/* 253 */       this.world.setEntityState((Entity)this, (byte)11);
/*     */     }
/*     */     else {
/*     */       
/* 257 */       this.holdRoseTick = 0;
/* 258 */       this.world.setEntityState((Entity)this, (byte)34);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 264 */     return SoundEvents.ENTITY_IRONGOLEM_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 269 */     return SoundEvents.ENTITY_IRONGOLEM_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 274 */     playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 280 */     return LootTableList.ENTITIES_IRON_GOLEM;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getHoldRoseTick() {
/* 285 */     return this.holdRoseTick;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPlayerCreated() {
/* 290 */     return ((((Byte)this.dataManager.get(PLAYER_CREATED)).byteValue() & 0x1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayerCreated(boolean playerCreated) {
/* 295 */     byte b0 = ((Byte)this.dataManager.get(PLAYER_CREATED)).byteValue();
/*     */     
/* 297 */     if (playerCreated) {
/*     */       
/* 299 */       this.dataManager.set(PLAYER_CREATED, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     }
/*     */     else {
/*     */       
/* 303 */       this.dataManager.set(PLAYER_CREATED, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 312 */     if (!isPlayerCreated() && this.attackingPlayer != null && this.villageObj != null)
/*     */     {
/* 314 */       this.villageObj.modifyPlayerReputation(this.attackingPlayer.getName(), -5);
/*     */     }
/*     */     
/* 317 */     super.onDeath(cause);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityIronGolem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */