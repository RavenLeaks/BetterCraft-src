/*     */ package net.minecraft.entity.passive;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFollowOwner;
/*     */ import net.minecraft.entity.ai.EntityAILeapAtTarget;
/*     */ import net.minecraft.entity.ai.EntityAIMate;
/*     */ import net.minecraft.entity.ai.EntityAIOcelotAttack;
/*     */ import net.minecraft.entity.ai.EntityAIOcelotSit;
/*     */ import net.minecraft.entity.ai.EntityAISit;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAITargetNonTamed;
/*     */ import net.minecraft.entity.ai.EntityAITempt;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityOcelot extends EntityTameable {
/*  47 */   public static final DataParameter<Integer> OCELOT_VARIANT = EntityDataManager.createKey(EntityOcelot.class, DataSerializers.VARINT);
/*     */ 
/*     */   
/*     */   private EntityAIAvoidEntity<EntityPlayer> avoidEntity;
/*     */ 
/*     */   
/*     */   private EntityAITempt aiTempt;
/*     */ 
/*     */   
/*     */   public EntityOcelot(World worldIn) {
/*  57 */     super(worldIn);
/*  58 */     setSize(0.6F, 0.7F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  63 */     this.aiSit = new EntityAISit(this);
/*  64 */     this.aiTempt = new EntityAITempt((EntityCreature)this, 0.6D, Items.FISH, true);
/*  65 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  66 */     this.tasks.addTask(2, (EntityAIBase)this.aiSit);
/*  67 */     this.tasks.addTask(3, (EntityAIBase)this.aiTempt);
/*  68 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIFollowOwner(this, 1.0D, 10.0F, 5.0F));
/*  69 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIOcelotSit(this, 0.8D));
/*  70 */     this.tasks.addTask(7, (EntityAIBase)new EntityAILeapAtTarget((EntityLiving)this, 0.3F));
/*  71 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIOcelotAttack((EntityLiving)this));
/*  72 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIMate(this, 0.8D));
/*  73 */     this.tasks.addTask(10, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 0.8D, 1.0000001E-5F));
/*  74 */     this.tasks.addTask(11, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 10.0F));
/*  75 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAITargetNonTamed(this, EntityChicken.class, false, null));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  80 */     super.entityInit();
/*  81 */     this.dataManager.register(OCELOT_VARIANT, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAITasks() {
/*  86 */     if (getMoveHelper().isUpdating()) {
/*     */       
/*  88 */       double d0 = getMoveHelper().getSpeed();
/*     */       
/*  90 */       if (d0 == 0.6D)
/*     */       {
/*  92 */         setSneaking(true);
/*  93 */         setSprinting(false);
/*     */       }
/*  95 */       else if (d0 == 1.33D)
/*     */       {
/*  97 */         setSneaking(false);
/*  98 */         setSprinting(true);
/*     */       }
/*     */       else
/*     */       {
/* 102 */         setSneaking(false);
/* 103 */         setSprinting(false);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 108 */       setSneaking(false);
/* 109 */       setSprinting(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDespawn() {
/* 118 */     return (!isTamed() && this.ticksExisted > 2400);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 123 */     super.applyEntityAttributes();
/* 124 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
/* 125 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */ 
/*     */   
/*     */   public static void registerFixesOcelot(DataFixer fixer) {
/* 134 */     EntityLiving.registerFixesMob(fixer, EntityOcelot.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 142 */     super.writeEntityToNBT(compound);
/* 143 */     compound.setInteger("CatType", getTameSkin());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 151 */     super.readEntityFromNBT(compound);
/* 152 */     setTameSkin(compound.getInteger("CatType"));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected SoundEvent getAmbientSound() {
/* 158 */     if (isTamed()) {
/*     */       
/* 160 */       if (isInLove())
/*     */       {
/* 162 */         return SoundEvents.ENTITY_CAT_PURR;
/*     */       }
/*     */ 
/*     */       
/* 166 */       return (this.rand.nextInt(4) == 0) ? SoundEvents.ENTITY_CAT_PURREOW : SoundEvents.ENTITY_CAT_AMBIENT;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 171 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 177 */     return SoundEvents.ENTITY_CAT_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 182 */     return SoundEvents.ENTITY_CAT_DEATH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundVolume() {
/* 190 */     return 0.4F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 195 */     return entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 3.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 203 */     if (isEntityInvulnerable(source))
/*     */     {
/* 205 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 209 */     if (this.aiSit != null)
/*     */     {
/* 211 */       this.aiSit.setSitting(false);
/*     */     }
/*     */     
/* 214 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 221 */     return LootTableList.ENTITIES_OCELOT;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 226 */     ItemStack itemstack = player.getHeldItem(hand);
/*     */     
/* 228 */     if (isTamed()) {
/*     */       
/* 230 */       if (isOwner((EntityLivingBase)player) && !this.world.isRemote && !isBreedingItem(itemstack))
/*     */       {
/* 232 */         this.aiSit.setSitting(!isSitting());
/*     */       }
/*     */     }
/* 235 */     else if ((this.aiTempt == null || this.aiTempt.isRunning()) && itemstack.getItem() == Items.FISH && player.getDistanceSqToEntity((Entity)this) < 9.0D) {
/*     */       
/* 237 */       if (!player.capabilities.isCreativeMode)
/*     */       {
/* 239 */         itemstack.func_190918_g(1);
/*     */       }
/*     */       
/* 242 */       if (!this.world.isRemote)
/*     */       {
/* 244 */         if (this.rand.nextInt(3) == 0) {
/*     */           
/* 246 */           func_193101_c(player);
/* 247 */           setTameSkin(1 + this.world.rand.nextInt(3));
/* 248 */           playTameEffect(true);
/* 249 */           this.aiSit.setSitting(true);
/* 250 */           this.world.setEntityState((Entity)this, (byte)7);
/*     */         }
/*     */         else {
/*     */           
/* 254 */           playTameEffect(false);
/* 255 */           this.world.setEntityState((Entity)this, (byte)6);
/*     */         } 
/*     */       }
/*     */       
/* 259 */       return true;
/*     */     } 
/*     */     
/* 262 */     return super.processInteract(player, hand);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityOcelot createChild(EntityAgeable ageable) {
/* 267 */     EntityOcelot entityocelot = new EntityOcelot(this.world);
/*     */     
/* 269 */     if (isTamed()) {
/*     */       
/* 271 */       entityocelot.setOwnerId(getOwnerId());
/* 272 */       entityocelot.setTamed(true);
/* 273 */       entityocelot.setTameSkin(getTameSkin());
/*     */     } 
/*     */     
/* 276 */     return entityocelot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 285 */     return (stack.getItem() == Items.FISH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 293 */     if (otherAnimal == this)
/*     */     {
/* 295 */       return false;
/*     */     }
/* 297 */     if (!isTamed())
/*     */     {
/* 299 */       return false;
/*     */     }
/* 301 */     if (!(otherAnimal instanceof EntityOcelot))
/*     */     {
/* 303 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 307 */     EntityOcelot entityocelot = (EntityOcelot)otherAnimal;
/*     */     
/* 309 */     if (!entityocelot.isTamed())
/*     */     {
/* 311 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 315 */     return (isInLove() && entityocelot.isInLove());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTameSkin() {
/* 322 */     return ((Integer)this.dataManager.get(OCELOT_VARIANT)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTameSkin(int skinId) {
/* 327 */     this.dataManager.set(OCELOT_VARIANT, Integer.valueOf(skinId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 335 */     return (this.world.rand.nextInt(3) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotColliding() {
/* 343 */     if (this.world.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.world.getCollisionBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(getEntityBoundingBox())) {
/*     */       
/* 345 */       BlockPos blockpos = new BlockPos(this.posX, (getEntityBoundingBox()).minY, this.posZ);
/*     */       
/* 347 */       if (blockpos.getY() < this.world.getSeaLevel())
/*     */       {
/* 349 */         return false;
/*     */       }
/*     */       
/* 352 */       IBlockState iblockstate = this.world.getBlockState(blockpos.down());
/* 353 */       Block block = iblockstate.getBlock();
/*     */       
/* 355 */       if (block == Blocks.GRASS || iblockstate.getMaterial() == Material.LEAVES)
/*     */       {
/* 357 */         return true;
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
/*     */   public String getName() {
/* 369 */     if (hasCustomName())
/*     */     {
/* 371 */       return getCustomNameTag();
/*     */     }
/*     */ 
/*     */     
/* 375 */     return isTamed() ? I18n.translateToLocal("entity.Cat.name") : super.getName();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setupTamedAI() {
/* 381 */     if (this.avoidEntity == null)
/*     */     {
/* 383 */       this.avoidEntity = new EntityAIAvoidEntity((EntityCreature)this, EntityPlayer.class, 16.0F, 0.8D, 1.33D);
/*     */     }
/*     */     
/* 386 */     this.tasks.removeTask((EntityAIBase)this.avoidEntity);
/*     */     
/* 388 */     if (!isTamed())
/*     */     {
/* 390 */       this.tasks.addTask(4, (EntityAIBase)this.avoidEntity);
/*     */     }
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
/* 402 */     livingdata = super.onInitialSpawn(difficulty, livingdata);
/*     */     
/* 404 */     if (getTameSkin() == 0 && this.world.rand.nextInt(7) == 0)
/*     */     {
/* 406 */       for (int i = 0; i < 2; i++) {
/*     */         
/* 408 */         EntityOcelot entityocelot = new EntityOcelot(this.world);
/* 409 */         entityocelot.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0F);
/* 410 */         entityocelot.setGrowingAge(-24000);
/* 411 */         this.world.spawnEntityInWorld((Entity)entityocelot);
/*     */       } 
/*     */     }
/*     */     
/* 415 */     return livingdata;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityOcelot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */