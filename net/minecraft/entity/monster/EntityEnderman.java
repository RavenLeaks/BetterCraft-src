/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import com.google.common.base.Optional;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
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
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
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
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ 
/*     */ public class EntityEnderman
/*     */   extends EntityMob
/*     */ {
/*  53 */   private static final UUID ATTACKING_SPEED_BOOST_ID = UUID.fromString("020E0DFB-87AE-4653-9556-831010E291A0");
/*  54 */   private static final AttributeModifier ATTACKING_SPEED_BOOST = (new AttributeModifier(ATTACKING_SPEED_BOOST_ID, "Attacking speed boost", 0.15000000596046448D, 0)).setSaved(false);
/*  55 */   private static final Set<Block> CARRIABLE_BLOCKS = Sets.newIdentityHashSet();
/*  56 */   private static final DataParameter<Optional<IBlockState>> CARRIED_BLOCK = EntityDataManager.createKey(EntityEnderman.class, DataSerializers.OPTIONAL_BLOCK_STATE);
/*  57 */   private static final DataParameter<Boolean> SCREAMING = EntityDataManager.createKey(EntityEnderman.class, DataSerializers.BOOLEAN);
/*     */   
/*     */   private int lastCreepySound;
/*     */   private int targetChangeTime;
/*     */   
/*     */   public EntityEnderman(World worldIn) {
/*  63 */     super(worldIn);
/*  64 */     setSize(0.6F, 2.9F);
/*  65 */     this.stepHeight = 1.0F;
/*  66 */     setPathPriority(PathNodeType.WATER, -1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  71 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  72 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIAttackMelee(this, 1.0D, false));
/*  73 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWanderAvoidWater(this, 1.0D, 0.0F));
/*  74 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  75 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  76 */     this.tasks.addTask(10, new AIPlaceBlock(this));
/*  77 */     this.tasks.addTask(11, new AITakeBlock(this));
/*  78 */     this.targetTasks.addTask(1, (EntityAIBase)new AIFindPlayer(this));
/*  79 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*  80 */     this.targetTasks.addTask(3, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityEndermite.class, 10, true, false, new Predicate<EntityEndermite>()
/*     */           {
/*     */             public boolean apply(@Nullable EntityEndermite p_apply_1_)
/*     */             {
/*  84 */               return p_apply_1_.isSpawnedByPlayer();
/*     */             }
/*     */           }));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  91 */     super.applyEntityAttributes();
/*  92 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
/*  93 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
/*  94 */     getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
/*  95 */     getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAttackTarget(@Nullable EntityLivingBase entitylivingbaseIn) {
/* 103 */     super.setAttackTarget(entitylivingbaseIn);
/* 104 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
/*     */     
/* 106 */     if (entitylivingbaseIn == null) {
/*     */       
/* 108 */       this.targetChangeTime = 0;
/* 109 */       this.dataManager.set(SCREAMING, Boolean.valueOf(false));
/* 110 */       iattributeinstance.removeModifier(ATTACKING_SPEED_BOOST);
/*     */     }
/*     */     else {
/*     */       
/* 114 */       this.targetChangeTime = this.ticksExisted;
/* 115 */       this.dataManager.set(SCREAMING, Boolean.valueOf(true));
/*     */       
/* 117 */       if (!iattributeinstance.hasModifier(ATTACKING_SPEED_BOOST))
/*     */       {
/* 119 */         iattributeinstance.applyModifier(ATTACKING_SPEED_BOOST);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 126 */     super.entityInit();
/* 127 */     this.dataManager.register(CARRIED_BLOCK, Optional.absent());
/* 128 */     this.dataManager.register(SCREAMING, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public void playEndermanSound() {
/* 133 */     if (this.ticksExisted >= this.lastCreepySound + 400) {
/*     */       
/* 135 */       this.lastCreepySound = this.ticksExisted;
/*     */       
/* 137 */       if (!isSilent())
/*     */       {
/* 139 */         this.world.playSound(this.posX, this.posY + getEyeHeight(), this.posZ, SoundEvents.ENTITY_ENDERMEN_STARE, getSoundCategory(), 2.5F, 1.0F, false);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 146 */     if (SCREAMING.equals(key) && isScreaming() && this.world.isRemote)
/*     */     {
/* 148 */       playEndermanSound();
/*     */     }
/*     */     
/* 151 */     super.notifyDataManagerChange(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesEnderman(DataFixer fixer) {
/* 156 */     EntityLiving.registerFixesMob(fixer, EntityEnderman.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 164 */     super.writeEntityToNBT(compound);
/* 165 */     IBlockState iblockstate = getHeldBlockState();
/*     */     
/* 167 */     if (iblockstate != null) {
/*     */       
/* 169 */       compound.setShort("carried", (short)Block.getIdFromBlock(iblockstate.getBlock()));
/* 170 */       compound.setShort("carriedData", (short)iblockstate.getBlock().getMetaFromState(iblockstate));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/*     */     IBlockState iblockstate;
/* 179 */     super.readEntityFromNBT(compound);
/*     */ 
/*     */     
/* 182 */     if (compound.hasKey("carried", 8)) {
/*     */       
/* 184 */       iblockstate = Block.getBlockFromName(compound.getString("carried")).getStateFromMeta(compound.getShort("carriedData") & 0xFFFF);
/*     */     }
/*     */     else {
/*     */       
/* 188 */       iblockstate = Block.getBlockById(compound.getShort("carried")).getStateFromMeta(compound.getShort("carriedData") & 0xFFFF);
/*     */     } 
/*     */     
/* 191 */     if (iblockstate == null || iblockstate.getBlock() == null || iblockstate.getMaterial() == Material.AIR)
/*     */     {
/* 193 */       iblockstate = null;
/*     */     }
/*     */     
/* 196 */     setHeldBlockState(iblockstate);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean shouldAttackPlayer(EntityPlayer player) {
/* 204 */     ItemStack itemstack = (ItemStack)player.inventory.armorInventory.get(3);
/*     */     
/* 206 */     if (itemstack.getItem() == Item.getItemFromBlock(Blocks.PUMPKIN))
/*     */     {
/* 208 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 212 */     Vec3d vec3d = player.getLook(1.0F).normalize();
/* 213 */     Vec3d vec3d1 = new Vec3d(this.posX - player.posX, (getEntityBoundingBox()).minY + getEyeHeight() - player.posY + player.getEyeHeight(), this.posZ - player.posZ);
/* 214 */     double d0 = vec3d1.lengthVector();
/* 215 */     vec3d1 = vec3d1.normalize();
/* 216 */     double d1 = vec3d.dotProduct(vec3d1);
/* 217 */     return (d1 > 1.0D - 0.025D / d0) ? player.canEntityBeSeen((Entity)this) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 223 */     return 2.55F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 232 */     if (this.world.isRemote)
/*     */     {
/* 234 */       for (int i = 0; i < 2; i++)
/*     */       {
/* 236 */         this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height - 0.25D, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */       }
/*     */     }
/*     */     
/* 240 */     this.isJumping = false;
/* 241 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 246 */     if (isWet())
/*     */     {
/* 248 */       attackEntityFrom(DamageSource.drown, 1.0F);
/*     */     }
/*     */     
/* 251 */     if (this.world.isDaytime() && this.ticksExisted >= this.targetChangeTime + 600) {
/*     */       
/* 253 */       float f = getBrightness();
/*     */       
/* 255 */       if (f > 0.5F && this.world.canSeeSky(new BlockPos((Entity)this)) && this.rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F) {
/*     */         
/* 257 */         setAttackTarget((EntityLivingBase)null);
/* 258 */         teleportRandomly();
/*     */       } 
/*     */     } 
/*     */     
/* 262 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean teleportRandomly() {
/* 270 */     double d0 = this.posX + (this.rand.nextDouble() - 0.5D) * 64.0D;
/* 271 */     double d1 = this.posY + (this.rand.nextInt(64) - 32);
/* 272 */     double d2 = this.posZ + (this.rand.nextDouble() - 0.5D) * 64.0D;
/* 273 */     return teleportTo(d0, d1, d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean teleportToEntity(Entity p_70816_1_) {
/* 281 */     Vec3d vec3d = new Vec3d(this.posX - p_70816_1_.posX, (getEntityBoundingBox()).minY + (this.height / 2.0F) - p_70816_1_.posY + p_70816_1_.getEyeHeight(), this.posZ - p_70816_1_.posZ);
/* 282 */     vec3d = vec3d.normalize();
/* 283 */     double d0 = 16.0D;
/* 284 */     double d1 = this.posX + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.xCoord * 16.0D;
/* 285 */     double d2 = this.posY + (this.rand.nextInt(16) - 8) - vec3d.yCoord * 16.0D;
/* 286 */     double d3 = this.posZ + (this.rand.nextDouble() - 0.5D) * 8.0D - vec3d.zCoord * 16.0D;
/* 287 */     return teleportTo(d1, d2, d3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean teleportTo(double x, double y, double z) {
/* 295 */     boolean flag = attemptTeleport(x, y, z);
/*     */     
/* 297 */     if (flag) {
/*     */       
/* 299 */       this.world.playSound(null, this.prevPosX, this.prevPosY, this.prevPosZ, SoundEvents.ENTITY_ENDERMEN_TELEPORT, getSoundCategory(), 1.0F, 1.0F);
/* 300 */       playSound(SoundEvents.ENTITY_ENDERMEN_TELEPORT, 1.0F, 1.0F);
/*     */     } 
/*     */     
/* 303 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 308 */     return isScreaming() ? SoundEvents.ENTITY_ENDERMEN_SCREAM : SoundEvents.ENTITY_ENDERMEN_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 313 */     return SoundEvents.ENTITY_ENDERMEN_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 318 */     return SoundEvents.ENTITY_ENDERMEN_DEATH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {
/* 326 */     super.dropEquipment(wasRecentlyHit, lootingModifier);
/* 327 */     IBlockState iblockstate = getHeldBlockState();
/*     */     
/* 329 */     if (iblockstate != null) {
/*     */       
/* 331 */       Item item = Item.getItemFromBlock(iblockstate.getBlock());
/* 332 */       int i = item.getHasSubtypes() ? iblockstate.getBlock().getMetaFromState(iblockstate) : 0;
/* 333 */       entityDropItem(new ItemStack(item, 1, i), 0.0F);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 340 */     return LootTableList.ENTITIES_ENDERMAN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeldBlockState(@Nullable IBlockState state) {
/* 348 */     this.dataManager.set(CARRIED_BLOCK, Optional.fromNullable(state));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IBlockState getHeldBlockState() {
/* 358 */     return (IBlockState)((Optional)this.dataManager.get(CARRIED_BLOCK)).orNull();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 366 */     if (isEntityInvulnerable(source))
/*     */     {
/* 368 */       return false;
/*     */     }
/* 370 */     if (source instanceof net.minecraft.util.EntityDamageSourceIndirect) {
/*     */       
/* 372 */       for (int i = 0; i < 64; i++) {
/*     */         
/* 374 */         if (teleportRandomly())
/*     */         {
/* 376 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 380 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 384 */     boolean flag = super.attackEntityFrom(source, amount);
/*     */     
/* 386 */     if (source.isUnblockable() && this.rand.nextInt(10) != 0)
/*     */     {
/* 388 */       teleportRandomly();
/*     */     }
/*     */     
/* 391 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isScreaming() {
/* 397 */     return ((Boolean)this.dataManager.get(SCREAMING)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 402 */     CARRIABLE_BLOCKS.add(Blocks.GRASS);
/* 403 */     CARRIABLE_BLOCKS.add(Blocks.DIRT);
/* 404 */     CARRIABLE_BLOCKS.add(Blocks.SAND);
/* 405 */     CARRIABLE_BLOCKS.add(Blocks.GRAVEL);
/* 406 */     CARRIABLE_BLOCKS.add(Blocks.YELLOW_FLOWER);
/* 407 */     CARRIABLE_BLOCKS.add(Blocks.RED_FLOWER);
/* 408 */     CARRIABLE_BLOCKS.add(Blocks.BROWN_MUSHROOM);
/* 409 */     CARRIABLE_BLOCKS.add(Blocks.RED_MUSHROOM);
/* 410 */     CARRIABLE_BLOCKS.add(Blocks.TNT);
/* 411 */     CARRIABLE_BLOCKS.add(Blocks.CACTUS);
/* 412 */     CARRIABLE_BLOCKS.add(Blocks.CLAY);
/* 413 */     CARRIABLE_BLOCKS.add(Blocks.PUMPKIN);
/* 414 */     CARRIABLE_BLOCKS.add(Blocks.MELON_BLOCK);
/* 415 */     CARRIABLE_BLOCKS.add(Blocks.MYCELIUM);
/* 416 */     CARRIABLE_BLOCKS.add(Blocks.NETHERRACK);
/*     */   }
/*     */   
/*     */   static class AIFindPlayer
/*     */     extends EntityAINearestAttackableTarget<EntityPlayer>
/*     */   {
/*     */     private final EntityEnderman enderman;
/*     */     private EntityPlayer player;
/*     */     private int aggroTime;
/*     */     private int teleportTime;
/*     */     
/*     */     public AIFindPlayer(EntityEnderman p_i45842_1_) {
/* 428 */       super(p_i45842_1_, EntityPlayer.class, false);
/* 429 */       this.enderman = p_i45842_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 434 */       double d0 = getTargetDistance();
/* 435 */       this.player = this.enderman.world.getNearestAttackablePlayer(this.enderman.posX, this.enderman.posY, this.enderman.posZ, d0, d0, null, new Predicate<EntityPlayer>()
/*     */           {
/*     */             public boolean apply(@Nullable EntityPlayer p_apply_1_)
/*     */             {
/* 439 */               return (p_apply_1_ != null && EntityEnderman.AIFindPlayer.this.enderman.shouldAttackPlayer(p_apply_1_));
/*     */             }
/*     */           });
/* 442 */       return (this.player != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 447 */       this.aggroTime = 5;
/* 448 */       this.teleportTime = 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public void resetTask() {
/* 453 */       this.player = null;
/* 454 */       super.resetTask();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean continueExecuting() {
/* 459 */       if (this.player != null) {
/*     */         
/* 461 */         if (!this.enderman.shouldAttackPlayer(this.player))
/*     */         {
/* 463 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 467 */         this.enderman.faceEntity((Entity)this.player, 10.0F, 10.0F);
/* 468 */         return true;
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 473 */       return (this.targetEntity != null && ((EntityPlayer)this.targetEntity).isEntityAlive()) ? true : super.continueExecuting();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 479 */       if (this.player != null) {
/*     */         
/* 481 */         if (--this.aggroTime <= 0)
/*     */         {
/* 483 */           this.targetEntity = (EntityLivingBase)this.player;
/* 484 */           this.player = null;
/* 485 */           super.startExecuting();
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 490 */         if (this.targetEntity != null)
/*     */         {
/* 492 */           if (this.enderman.shouldAttackPlayer((EntityPlayer)this.targetEntity)) {
/*     */             
/* 494 */             if (((EntityPlayer)this.targetEntity).getDistanceSqToEntity((Entity)this.enderman) < 16.0D)
/*     */             {
/* 496 */               this.enderman.teleportRandomly();
/*     */             }
/*     */             
/* 499 */             this.teleportTime = 0;
/*     */           }
/* 501 */           else if (((EntityPlayer)this.targetEntity).getDistanceSqToEntity((Entity)this.enderman) > 256.0D && this.teleportTime++ >= 30 && this.enderman.teleportToEntity((Entity)this.targetEntity)) {
/*     */             
/* 503 */             this.teleportTime = 0;
/*     */           } 
/*     */         }
/*     */         
/* 507 */         super.updateTask();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   static class AIPlaceBlock
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntityEnderman enderman;
/*     */     
/*     */     public AIPlaceBlock(EntityEnderman p_i45843_1_) {
/* 518 */       this.enderman = p_i45843_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 523 */       if (this.enderman.getHeldBlockState() == null)
/*     */       {
/* 525 */         return false;
/*     */       }
/* 527 */       if (!this.enderman.world.getGameRules().getBoolean("mobGriefing"))
/*     */       {
/* 529 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 533 */       return (this.enderman.getRNG().nextInt(2000) == 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 539 */       Random random = this.enderman.getRNG();
/* 540 */       World world = this.enderman.world;
/* 541 */       int i = MathHelper.floor(this.enderman.posX - 1.0D + random.nextDouble() * 2.0D);
/* 542 */       int j = MathHelper.floor(this.enderman.posY + random.nextDouble() * 2.0D);
/* 543 */       int k = MathHelper.floor(this.enderman.posZ - 1.0D + random.nextDouble() * 2.0D);
/* 544 */       BlockPos blockpos = new BlockPos(i, j, k);
/* 545 */       IBlockState iblockstate = world.getBlockState(blockpos);
/* 546 */       IBlockState iblockstate1 = world.getBlockState(blockpos.down());
/* 547 */       IBlockState iblockstate2 = this.enderman.getHeldBlockState();
/*     */       
/* 549 */       if (iblockstate2 != null && canPlaceBlock(world, blockpos, iblockstate2.getBlock(), iblockstate, iblockstate1)) {
/*     */         
/* 551 */         world.setBlockState(blockpos, iblockstate2, 3);
/* 552 */         this.enderman.setHeldBlockState((IBlockState)null);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean canPlaceBlock(World p_188518_1_, BlockPos p_188518_2_, Block p_188518_3_, IBlockState p_188518_4_, IBlockState p_188518_5_) {
/* 558 */       if (!p_188518_3_.canPlaceBlockAt(p_188518_1_, p_188518_2_))
/*     */       {
/* 560 */         return false;
/*     */       }
/* 562 */       if (p_188518_4_.getMaterial() != Material.AIR)
/*     */       {
/* 564 */         return false;
/*     */       }
/* 566 */       if (p_188518_5_.getMaterial() == Material.AIR)
/*     */       {
/* 568 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 572 */       return p_188518_5_.isFullCube();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class AITakeBlock
/*     */     extends EntityAIBase
/*     */   {
/*     */     private final EntityEnderman enderman;
/*     */     
/*     */     public AITakeBlock(EntityEnderman p_i45841_1_) {
/* 583 */       this.enderman = p_i45841_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 588 */       if (this.enderman.getHeldBlockState() != null)
/*     */       {
/* 590 */         return false;
/*     */       }
/* 592 */       if (!this.enderman.world.getGameRules().getBoolean("mobGriefing"))
/*     */       {
/* 594 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 598 */       return (this.enderman.getRNG().nextInt(20) == 0);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void updateTask() {
/* 604 */       Random random = this.enderman.getRNG();
/* 605 */       World world = this.enderman.world;
/* 606 */       int i = MathHelper.floor(this.enderman.posX - 2.0D + random.nextDouble() * 4.0D);
/* 607 */       int j = MathHelper.floor(this.enderman.posY + random.nextDouble() * 3.0D);
/* 608 */       int k = MathHelper.floor(this.enderman.posZ - 2.0D + random.nextDouble() * 4.0D);
/* 609 */       BlockPos blockpos = new BlockPos(i, j, k);
/* 610 */       IBlockState iblockstate = world.getBlockState(blockpos);
/* 611 */       Block block = iblockstate.getBlock();
/* 612 */       RayTraceResult raytraceresult = world.rayTraceBlocks(new Vec3d((MathHelper.floor(this.enderman.posX) + 0.5F), (j + 0.5F), (MathHelper.floor(this.enderman.posZ) + 0.5F)), new Vec3d((i + 0.5F), (j + 0.5F), (k + 0.5F)), false, true, false);
/* 613 */       boolean flag = (raytraceresult != null && raytraceresult.getBlockPos().equals(blockpos));
/*     */       
/* 615 */       if (EntityEnderman.CARRIABLE_BLOCKS.contains(block) && flag) {
/*     */         
/* 617 */         this.enderman.setHeldBlockState(iblockstate);
/* 618 */         world.setBlockToAir(blockpos);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityEnderman.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */