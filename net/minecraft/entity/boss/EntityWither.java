/*     */ package net.minecraft.entity.boss;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackRanged;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.monster.EntityMob;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.pathfinding.PathNavigateGround;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.BossInfo;
/*     */ import net.minecraft.world.BossInfoServer;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityWither
/*     */   extends EntityMob implements IRangedAttackMob {
/*  53 */   private static final DataParameter<Integer> FIRST_HEAD_TARGET = EntityDataManager.createKey(EntityWither.class, DataSerializers.VARINT);
/*  54 */   private static final DataParameter<Integer> SECOND_HEAD_TARGET = EntityDataManager.createKey(EntityWither.class, DataSerializers.VARINT);
/*  55 */   private static final DataParameter<Integer> THIRD_HEAD_TARGET = EntityDataManager.createKey(EntityWither.class, DataSerializers.VARINT);
/*  56 */   private static final DataParameter<Integer>[] HEAD_TARGETS = new DataParameter[] { FIRST_HEAD_TARGET, SECOND_HEAD_TARGET, THIRD_HEAD_TARGET };
/*  57 */   private static final DataParameter<Integer> INVULNERABILITY_TIME = EntityDataManager.createKey(EntityWither.class, DataSerializers.VARINT);
/*  58 */   private final float[] xRotationHeads = new float[2];
/*  59 */   private final float[] yRotationHeads = new float[2];
/*  60 */   private final float[] xRotOHeads = new float[2];
/*  61 */   private final float[] yRotOHeads = new float[2];
/*  62 */   private final int[] nextHeadUpdate = new int[2];
/*  63 */   private final int[] idleHeadUpdates = new int[2];
/*     */   
/*     */   private int blockBreakCounter;
/*     */   
/*  67 */   private final BossInfoServer bossInfo = (BossInfoServer)(new BossInfoServer(getDisplayName(), BossInfo.Color.PURPLE, BossInfo.Overlay.PROGRESS)).setDarkenSky(true);
/*  68 */   private static final Predicate<Entity> NOT_UNDEAD = new Predicate<Entity>()
/*     */     {
/*     */       public boolean apply(@Nullable Entity p_apply_1_)
/*     */       {
/*  72 */         return (p_apply_1_ instanceof EntityLivingBase && ((EntityLivingBase)p_apply_1_).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD && ((EntityLivingBase)p_apply_1_).func_190631_cK());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   public EntityWither(World worldIn) {
/*  78 */     super(worldIn);
/*  79 */     setHealth(getMaxHealth());
/*  80 */     setSize(0.9F, 3.5F);
/*  81 */     this.isImmuneToFire = true;
/*  82 */     ((PathNavigateGround)getNavigator()).setCanSwim(true);
/*  83 */     this.experienceValue = 50;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  88 */     this.tasks.addTask(0, new AIDoNothing());
/*  89 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  90 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIAttackRanged(this, 1.0D, 40, 20.0F));
/*  91 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWanderAvoidWater((EntityCreature)this, 1.0D));
/*  92 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  93 */     this.tasks.addTask(7, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  94 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget((EntityCreature)this, false, new Class[0]));
/*  95 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget((EntityCreature)this, EntityLiving.class, 0, false, false, NOT_UNDEAD));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 100 */     super.entityInit();
/* 101 */     this.dataManager.register(FIRST_HEAD_TARGET, Integer.valueOf(0));
/* 102 */     this.dataManager.register(SECOND_HEAD_TARGET, Integer.valueOf(0));
/* 103 */     this.dataManager.register(THIRD_HEAD_TARGET, Integer.valueOf(0));
/* 104 */     this.dataManager.register(INVULNERABILITY_TIME, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesWither(DataFixer fixer) {
/* 109 */     EntityLiving.registerFixesMob(fixer, EntityWither.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 117 */     super.writeEntityToNBT(compound);
/* 118 */     compound.setInteger("Invul", getInvulTime());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 126 */     super.readEntityFromNBT(compound);
/* 127 */     setInvulTime(compound.getInteger("Invul"));
/*     */     
/* 129 */     if (hasCustomName())
/*     */     {
/* 131 */       this.bossInfo.setName(getDisplayName());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCustomNameTag(String name) {
/* 140 */     super.setCustomNameTag(name);
/* 141 */     this.bossInfo.setName(getDisplayName());
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 146 */     return SoundEvents.ENTITY_WITHER_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 151 */     return SoundEvents.ENTITY_WITHER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 156 */     return SoundEvents.ENTITY_WITHER_DEATH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 165 */     this.motionY *= 0.6000000238418579D;
/*     */     
/* 167 */     if (!this.world.isRemote && getWatchedTargetId(0) > 0) {
/*     */       
/* 169 */       Entity entity = this.world.getEntityByID(getWatchedTargetId(0));
/*     */       
/* 171 */       if (entity != null) {
/*     */         
/* 173 */         if (this.posY < entity.posY || (!isArmored() && this.posY < entity.posY + 5.0D)) {
/*     */           
/* 175 */           if (this.motionY < 0.0D)
/*     */           {
/* 177 */             this.motionY = 0.0D;
/*     */           }
/*     */           
/* 180 */           this.motionY += (0.5D - this.motionY) * 0.6000000238418579D;
/*     */         } 
/*     */         
/* 183 */         double d0 = entity.posX - this.posX;
/* 184 */         double d1 = entity.posZ - this.posZ;
/* 185 */         double d3 = d0 * d0 + d1 * d1;
/*     */         
/* 187 */         if (d3 > 9.0D) {
/*     */           
/* 189 */           double d5 = MathHelper.sqrt(d3);
/* 190 */           this.motionX += (d0 / d5 * 0.5D - this.motionX) * 0.6000000238418579D;
/* 191 */           this.motionZ += (d1 / d5 * 0.5D - this.motionZ) * 0.6000000238418579D;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 196 */     if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806D)
/*     */     {
/* 198 */       this.rotationYaw = (float)MathHelper.atan2(this.motionZ, this.motionX) * 57.295776F - 90.0F;
/*     */     }
/*     */     
/* 201 */     super.onLivingUpdate();
/*     */     
/* 203 */     for (int i = 0; i < 2; i++) {
/*     */       
/* 205 */       this.yRotOHeads[i] = this.yRotationHeads[i];
/* 206 */       this.xRotOHeads[i] = this.xRotationHeads[i];
/*     */     } 
/*     */     
/* 209 */     for (int j = 0; j < 2; j++) {
/*     */       
/* 211 */       int k = getWatchedTargetId(j + 1);
/* 212 */       Entity entity1 = null;
/*     */       
/* 214 */       if (k > 0)
/*     */       {
/* 216 */         entity1 = this.world.getEntityByID(k);
/*     */       }
/*     */       
/* 219 */       if (entity1 != null) {
/*     */         
/* 221 */         double d11 = getHeadX(j + 1);
/* 222 */         double d12 = getHeadY(j + 1);
/* 223 */         double d13 = getHeadZ(j + 1);
/* 224 */         double d6 = entity1.posX - d11;
/* 225 */         double d7 = entity1.posY + entity1.getEyeHeight() - d12;
/* 226 */         double d8 = entity1.posZ - d13;
/* 227 */         double d9 = MathHelper.sqrt(d6 * d6 + d8 * d8);
/* 228 */         float f = (float)(MathHelper.atan2(d8, d6) * 57.29577951308232D) - 90.0F;
/* 229 */         float f1 = (float)-(MathHelper.atan2(d7, d9) * 57.29577951308232D);
/* 230 */         this.xRotationHeads[j] = rotlerp(this.xRotationHeads[j], f1, 40.0F);
/* 231 */         this.yRotationHeads[j] = rotlerp(this.yRotationHeads[j], f, 10.0F);
/*     */       }
/*     */       else {
/*     */         
/* 235 */         this.yRotationHeads[j] = rotlerp(this.yRotationHeads[j], this.renderYawOffset, 10.0F);
/*     */       } 
/*     */     } 
/*     */     
/* 239 */     boolean flag = isArmored();
/*     */     
/* 241 */     for (int l = 0; l < 3; l++) {
/*     */       
/* 243 */       double d10 = getHeadX(l);
/* 244 */       double d2 = getHeadY(l);
/* 245 */       double d4 = getHeadZ(l);
/* 246 */       this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       
/* 248 */       if (flag && this.world.rand.nextInt(4) == 0)
/*     */       {
/* 250 */         this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, d10 + this.rand.nextGaussian() * 0.30000001192092896D, d2 + this.rand.nextGaussian() * 0.30000001192092896D, d4 + this.rand.nextGaussian() * 0.30000001192092896D, 0.699999988079071D, 0.699999988079071D, 0.5D, new int[0]);
/*     */       }
/*     */     } 
/*     */     
/* 254 */     if (getInvulTime() > 0)
/*     */     {
/* 256 */       for (int i1 = 0; i1 < 3; i1++)
/*     */       {
/* 258 */         this.world.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + this.rand.nextGaussian(), this.posY + (this.rand.nextFloat() * 3.3F), this.posZ + this.rand.nextGaussian(), 0.699999988079071D, 0.699999988079071D, 0.8999999761581421D, new int[0]);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/* 265 */     if (getInvulTime() > 0) {
/*     */       
/* 267 */       int j1 = getInvulTime() - 1;
/*     */       
/* 269 */       if (j1 <= 0) {
/*     */         
/* 271 */         this.world.newExplosion((Entity)this, this.posX, this.posY + getEyeHeight(), this.posZ, 7.0F, false, this.world.getGameRules().getBoolean("mobGriefing"));
/* 272 */         this.world.playBroadcastSound(1023, new BlockPos((Entity)this), 0);
/*     */       } 
/*     */       
/* 275 */       setInvulTime(j1);
/*     */       
/* 277 */       if (this.ticksExisted % 10 == 0)
/*     */       {
/* 279 */         heal(10.0F);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 284 */       super.updateAITasks();
/*     */       
/* 286 */       for (int i = 1; i < 3; i++) {
/*     */         
/* 288 */         if (this.ticksExisted >= this.nextHeadUpdate[i - 1]) {
/*     */           
/* 290 */           this.nextHeadUpdate[i - 1] = this.ticksExisted + 10 + this.rand.nextInt(10);
/*     */           
/* 292 */           if (this.world.getDifficulty() == EnumDifficulty.NORMAL || this.world.getDifficulty() == EnumDifficulty.HARD) {
/*     */             
/* 294 */             int j3 = i - 1;
/* 295 */             int k3 = this.idleHeadUpdates[i - 1];
/* 296 */             this.idleHeadUpdates[j3] = this.idleHeadUpdates[i - 1] + 1;
/*     */             
/* 298 */             if (k3 > 15) {
/*     */               
/* 300 */               float f = 10.0F;
/* 301 */               float f1 = 5.0F;
/* 302 */               double d0 = MathHelper.nextDouble(this.rand, this.posX - 10.0D, this.posX + 10.0D);
/* 303 */               double d1 = MathHelper.nextDouble(this.rand, this.posY - 5.0D, this.posY + 5.0D);
/* 304 */               double d2 = MathHelper.nextDouble(this.rand, this.posZ - 10.0D, this.posZ + 10.0D);
/* 305 */               launchWitherSkullToCoords(i + 1, d0, d1, d2, true);
/* 306 */               this.idleHeadUpdates[i - 1] = 0;
/*     */             } 
/*     */           } 
/*     */           
/* 310 */           int k1 = getWatchedTargetId(i);
/*     */           
/* 312 */           if (k1 > 0) {
/*     */             
/* 314 */             Entity entity = this.world.getEntityByID(k1);
/*     */             
/* 316 */             if (entity != null && entity.isEntityAlive() && getDistanceSqToEntity(entity) <= 900.0D && canEntityBeSeen(entity)) {
/*     */               
/* 318 */               if (entity instanceof EntityPlayer && ((EntityPlayer)entity).capabilities.disableDamage)
/*     */               {
/* 320 */                 updateWatchedTargetId(i, 0);
/*     */               }
/*     */               else
/*     */               {
/* 324 */                 launchWitherSkullToEntity(i + 1, (EntityLivingBase)entity);
/* 325 */                 this.nextHeadUpdate[i - 1] = this.ticksExisted + 40 + this.rand.nextInt(20);
/* 326 */                 this.idleHeadUpdates[i - 1] = 0;
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 331 */               updateWatchedTargetId(i, 0);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 336 */             List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expand(20.0D, 8.0D, 20.0D), Predicates.and(NOT_UNDEAD, EntitySelectors.NOT_SPECTATING));
/*     */             
/* 338 */             for (int j2 = 0; j2 < 10 && !list.isEmpty(); j2++) {
/*     */               
/* 340 */               EntityLivingBase entitylivingbase = list.get(this.rand.nextInt(list.size()));
/*     */               
/* 342 */               if (entitylivingbase != this && entitylivingbase.isEntityAlive() && canEntityBeSeen((Entity)entitylivingbase)) {
/*     */                 
/* 344 */                 if (entitylivingbase instanceof EntityPlayer) {
/*     */                   
/* 346 */                   if (!((EntityPlayer)entitylivingbase).capabilities.disableDamage)
/*     */                   {
/* 348 */                     updateWatchedTargetId(i, entitylivingbase.getEntityId());
/*     */                   }
/*     */                   
/*     */                   break;
/*     */                 } 
/* 353 */                 updateWatchedTargetId(i, entitylivingbase.getEntityId());
/*     */ 
/*     */                 
/*     */                 break;
/*     */               } 
/*     */               
/* 359 */               list.remove(entitylivingbase);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 365 */       if (getAttackTarget() != null) {
/*     */         
/* 367 */         updateWatchedTargetId(0, getAttackTarget().getEntityId());
/*     */       }
/*     */       else {
/*     */         
/* 371 */         updateWatchedTargetId(0, 0);
/*     */       } 
/*     */       
/* 374 */       if (this.blockBreakCounter > 0) {
/*     */         
/* 376 */         this.blockBreakCounter--;
/*     */         
/* 378 */         if (this.blockBreakCounter == 0 && this.world.getGameRules().getBoolean("mobGriefing")) {
/*     */           
/* 380 */           int i1 = MathHelper.floor(this.posY);
/* 381 */           int l1 = MathHelper.floor(this.posX);
/* 382 */           int i2 = MathHelper.floor(this.posZ);
/* 383 */           boolean flag = false;
/*     */           
/* 385 */           for (int k2 = -1; k2 <= 1; k2++) {
/*     */             
/* 387 */             for (int l2 = -1; l2 <= 1; l2++) {
/*     */               
/* 389 */               for (int j = 0; j <= 3; j++) {
/*     */                 
/* 391 */                 int i3 = l1 + k2;
/* 392 */                 int k = i1 + j;
/* 393 */                 int l = i2 + l2;
/* 394 */                 BlockPos blockpos = new BlockPos(i3, k, l);
/* 395 */                 IBlockState iblockstate = this.world.getBlockState(blockpos);
/* 396 */                 Block block = iblockstate.getBlock();
/*     */                 
/* 398 */                 if (iblockstate.getMaterial() != Material.AIR && canDestroyBlock(block))
/*     */                 {
/* 400 */                   flag = !(!this.world.destroyBlock(blockpos, true) && !flag);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*     */           
/* 406 */           if (flag)
/*     */           {
/* 408 */             this.world.playEvent(null, 1022, new BlockPos((Entity)this), 0);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 413 */       if (this.ticksExisted % 20 == 0)
/*     */       {
/* 415 */         heal(1.0F);
/*     */       }
/*     */       
/* 418 */       this.bossInfo.setPercent(getHealth() / getMaxHealth());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canDestroyBlock(Block blockIn) {
/* 424 */     return (blockIn != Blocks.BEDROCK && blockIn != Blocks.END_PORTAL && blockIn != Blocks.END_PORTAL_FRAME && blockIn != Blocks.COMMAND_BLOCK && blockIn != Blocks.REPEATING_COMMAND_BLOCK && blockIn != Blocks.CHAIN_COMMAND_BLOCK && blockIn != Blocks.BARRIER && blockIn != Blocks.STRUCTURE_BLOCK && blockIn != Blocks.STRUCTURE_VOID && blockIn != Blocks.PISTON_EXTENSION && blockIn != Blocks.END_GATEWAY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void ignite() {
/* 432 */     setInvulTime(220);
/* 433 */     setHealth(getMaxHealth() / 3.0F);
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
/*     */   
/*     */   public void addTrackingPlayer(EntityPlayerMP player) {
/* 449 */     super.addTrackingPlayer(player);
/* 450 */     this.bossInfo.addPlayer(player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTrackingPlayer(EntityPlayerMP player) {
/* 459 */     super.removeTrackingPlayer(player);
/* 460 */     this.bossInfo.removePlayer(player);
/*     */   }
/*     */ 
/*     */   
/*     */   private double getHeadX(int p_82214_1_) {
/* 465 */     if (p_82214_1_ <= 0)
/*     */     {
/* 467 */       return this.posX;
/*     */     }
/*     */ 
/*     */     
/* 471 */     float f = (this.renderYawOffset + (180 * (p_82214_1_ - 1))) * 0.017453292F;
/* 472 */     float f1 = MathHelper.cos(f);
/* 473 */     return this.posX + f1 * 1.3D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private double getHeadY(int p_82208_1_) {
/* 479 */     return (p_82208_1_ <= 0) ? (this.posY + 3.0D) : (this.posY + 2.2D);
/*     */   }
/*     */ 
/*     */   
/*     */   private double getHeadZ(int p_82213_1_) {
/* 484 */     if (p_82213_1_ <= 0)
/*     */     {
/* 486 */       return this.posZ;
/*     */     }
/*     */ 
/*     */     
/* 490 */     float f = (this.renderYawOffset + (180 * (p_82213_1_ - 1))) * 0.017453292F;
/* 491 */     float f1 = MathHelper.sin(f);
/* 492 */     return this.posZ + f1 * 1.3D;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float rotlerp(float p_82204_1_, float p_82204_2_, float p_82204_3_) {
/* 498 */     float f = MathHelper.wrapDegrees(p_82204_2_ - p_82204_1_);
/*     */     
/* 500 */     if (f > p_82204_3_)
/*     */     {
/* 502 */       f = p_82204_3_;
/*     */     }
/*     */     
/* 505 */     if (f < -p_82204_3_)
/*     */     {
/* 507 */       f = -p_82204_3_;
/*     */     }
/*     */     
/* 510 */     return p_82204_1_ + f;
/*     */   }
/*     */ 
/*     */   
/*     */   private void launchWitherSkullToEntity(int p_82216_1_, EntityLivingBase p_82216_2_) {
/* 515 */     launchWitherSkullToCoords(p_82216_1_, p_82216_2_.posX, p_82216_2_.posY + p_82216_2_.getEyeHeight() * 0.5D, p_82216_2_.posZ, (p_82216_1_ == 0 && this.rand.nextFloat() < 0.001F));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void launchWitherSkullToCoords(int p_82209_1_, double x, double y, double z, boolean invulnerable) {
/* 523 */     this.world.playEvent(null, 1024, new BlockPos((Entity)this), 0);
/* 524 */     double d0 = getHeadX(p_82209_1_);
/* 525 */     double d1 = getHeadY(p_82209_1_);
/* 526 */     double d2 = getHeadZ(p_82209_1_);
/* 527 */     double d3 = x - d0;
/* 528 */     double d4 = y - d1;
/* 529 */     double d5 = z - d2;
/* 530 */     EntityWitherSkull entitywitherskull = new EntityWitherSkull(this.world, (EntityLivingBase)this, d3, d4, d5);
/*     */     
/* 532 */     if (invulnerable)
/*     */     {
/* 534 */       entitywitherskull.setInvulnerable(true);
/*     */     }
/*     */     
/* 537 */     entitywitherskull.posY = d1;
/* 538 */     entitywitherskull.posX = d0;
/* 539 */     entitywitherskull.posZ = d2;
/* 540 */     this.world.spawnEntityInWorld((Entity)entitywitherskull);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
/* 550 */     launchWitherSkullToEntity(0, target);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 558 */     if (isEntityInvulnerable(source))
/*     */     {
/* 560 */       return false;
/*     */     }
/* 562 */     if (source != DamageSource.drown && !(source.getEntity() instanceof EntityWither)) {
/*     */       
/* 564 */       if (getInvulTime() > 0 && source != DamageSource.outOfWorld)
/*     */       {
/* 566 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 570 */       if (isArmored()) {
/*     */         
/* 572 */         Entity entity = source.getSourceOfDamage();
/*     */         
/* 574 */         if (entity instanceof net.minecraft.entity.projectile.EntityArrow)
/*     */         {
/* 576 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 580 */       Entity entity1 = source.getEntity();
/*     */       
/* 582 */       if (entity1 != null && !(entity1 instanceof EntityPlayer) && entity1 instanceof EntityLivingBase && ((EntityLivingBase)entity1).getCreatureAttribute() == getCreatureAttribute())
/*     */       {
/* 584 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 588 */       if (this.blockBreakCounter <= 0)
/*     */       {
/* 590 */         this.blockBreakCounter = 20;
/*     */       }
/*     */       
/* 593 */       for (int i = 0; i < this.idleHeadUpdates.length; i++)
/*     */       {
/* 595 */         this.idleHeadUpdates[i] = this.idleHeadUpdates[i] + 3;
/*     */       }
/*     */       
/* 598 */       return super.attackEntityFrom(source, amount);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 604 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {
/* 613 */     EntityItem entityitem = dropItem(Items.NETHER_STAR, 1);
/*     */     
/* 615 */     if (entityitem != null)
/*     */     {
/* 617 */       entityitem.setNoDespawn();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void despawnEntity() {
/* 626 */     this.entityAge = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender() {
/* 631 */     return 15728880;
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
/*     */   public void addPotionEffect(PotionEffect potioneffectIn) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 647 */     super.applyEntityAttributes();
/* 648 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(300.0D);
/* 649 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.6000000238418579D);
/* 650 */     getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(40.0D);
/* 651 */     getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(4.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHeadYRotation(int p_82207_1_) {
/* 656 */     return this.yRotationHeads[p_82207_1_];
/*     */   }
/*     */ 
/*     */   
/*     */   public float getHeadXRotation(int p_82210_1_) {
/* 661 */     return this.xRotationHeads[p_82210_1_];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInvulTime() {
/* 666 */     return ((Integer)this.dataManager.get(INVULNERABILITY_TIME)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setInvulTime(int time) {
/* 671 */     this.dataManager.set(INVULNERABILITY_TIME, Integer.valueOf(time));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWatchedTargetId(int head) {
/* 679 */     return ((Integer)this.dataManager.get(HEAD_TARGETS[head])).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateWatchedTargetId(int targetOffset, int newId) {
/* 687 */     this.dataManager.set(HEAD_TARGETS[targetOffset], Integer.valueOf(newId));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isArmored() {
/* 696 */     return (getHealth() <= getMaxHealth() / 2.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 704 */     return EnumCreatureAttribute.UNDEAD;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canBeRidden(Entity entityIn) {
/* 709 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNonBoss() {
/* 717 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSwingingArms(boolean swingingArms) {}
/*     */ 
/*     */   
/*     */   class AIDoNothing
/*     */     extends EntityAIBase
/*     */   {
/*     */     public AIDoNothing() {
/* 728 */       setMutexBits(7);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 733 */       return (EntityWither.this.getInvulTime() > 0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\boss\EntityWither.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */