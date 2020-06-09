/*     */ package net.minecraft.entity.passive;
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.collect.Sets;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIFollow;
/*     */ import net.minecraft.entity.ai.EntityAIFollowOwnerFlying;
/*     */ import net.minecraft.entity.ai.EntityAILandOnOwnersShoulder;
/*     */ import net.minecraft.entity.ai.EntityAIPanic;
/*     */ import net.minecraft.entity.ai.EntityAISit;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWaterFlying;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.EntityFlyHelper;
/*     */ import net.minecraft.entity.ai.EntityMoveHelper;
/*     */ import net.minecraft.entity.boss.EntityDragon;
/*     */ import net.minecraft.entity.boss.EntityWither;
/*     */ import net.minecraft.entity.monster.EntityBlaze;
/*     */ import net.minecraft.entity.monster.EntityCaveSpider;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityElderGuardian;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntityEndermite;
/*     */ import net.minecraft.entity.monster.EntityEvoker;
/*     */ import net.minecraft.entity.monster.EntityGhast;
/*     */ import net.minecraft.entity.monster.EntityHusk;
/*     */ import net.minecraft.entity.monster.EntityIllusionIllager;
/*     */ import net.minecraft.entity.monster.EntityMagmaCube;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.entity.monster.EntityPolarBear;
/*     */ import net.minecraft.entity.monster.EntityShulker;
/*     */ import net.minecraft.entity.monster.EntitySilverfish;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.monster.EntitySpider;
/*     */ import net.minecraft.entity.monster.EntityStray;
/*     */ import net.minecraft.entity.monster.EntityVex;
/*     */ import net.minecraft.entity.monster.EntityVindicator;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.monster.EntityWitherSkeleton;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.pathfinding.PathNavigate;
/*     */ import net.minecraft.pathfinding.PathNavigateFlying;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityParrot extends EntityShoulderRiding implements EntityFlying {
/*  85 */   private static final DataParameter<Integer> field_192013_bG = EntityDataManager.createKey(EntityParrot.class, DataSerializers.VARINT);
/*  86 */   private static final Predicate<EntityLiving> field_192014_bH = new Predicate<EntityLiving>()
/*     */     {
/*     */       public boolean apply(@Nullable EntityLiving p_apply_1_)
/*     */       {
/*  90 */         return (p_apply_1_ != null && EntityParrot.field_192017_bK.containsKey(EntityList.field_191308_b.getIDForObject(p_apply_1_.getClass())));
/*     */       }
/*     */     };
/*  93 */   private static final Item field_192015_bI = Items.COOKIE;
/*  94 */   private static final Set<Item> field_192016_bJ = Sets.newHashSet((Object[])new Item[] { Items.WHEAT_SEEDS, Items.MELON_SEEDS, Items.PUMPKIN_SEEDS, Items.BEETROOT_SEEDS });
/*  95 */   private static final Int2ObjectMap<SoundEvent> field_192017_bK = (Int2ObjectMap<SoundEvent>)new Int2ObjectOpenHashMap(32);
/*     */   public float field_192008_bB;
/*     */   public float field_192009_bC;
/*     */   public float field_192010_bD;
/*     */   public float field_192011_bE;
/* 100 */   public float field_192012_bF = 1.0F;
/*     */   
/*     */   private boolean field_192018_bL;
/*     */   private BlockPos field_192019_bM;
/*     */   
/*     */   public EntityParrot(World p_i47411_1_) {
/* 106 */     super(p_i47411_1_);
/* 107 */     setSize(0.5F, 0.9F);
/* 108 */     this.moveHelper = (EntityMoveHelper)new EntityFlyHelper((EntityLiving)this);
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
/* 119 */     func_191997_m(this.rand.nextInt(5));
/* 120 */     return super.onInitialSpawn(difficulty, livingdata);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/* 125 */     this.aiSit = new EntityAISit(this);
/* 126 */     this.tasks.addTask(0, (EntityAIBase)new EntityAIPanic((EntityCreature)this, 1.25D));
/* 127 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/* 128 */     this.tasks.addTask(1, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/* 129 */     this.tasks.addTask(2, (EntityAIBase)this.aiSit);
/* 130 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIFollowOwnerFlying(this, 1.0D, 5.0F, 1.0F));
/* 131 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIWanderAvoidWaterFlying((EntityCreature)this, 1.0D));
/* 132 */     this.tasks.addTask(3, (EntityAIBase)new EntityAILandOnOwnersShoulder(this));
/* 133 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIFollow((EntityLiving)this, 1.0D, 3.0F, 7.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 138 */     super.applyEntityAttributes();
/* 139 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.field_193334_e);
/* 140 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(6.0D);
/* 141 */     getEntityAttribute(SharedMonsterAttributes.field_193334_e).setBaseValue(0.4000000059604645D);
/* 142 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected PathNavigate getNewNavigator(World worldIn) {
/* 150 */     PathNavigateFlying pathnavigateflying = new PathNavigateFlying((EntityLiving)this, worldIn);
/* 151 */     pathnavigateflying.func_192879_a(false);
/* 152 */     pathnavigateflying.func_192877_c(true);
/* 153 */     pathnavigateflying.func_192878_b(true);
/* 154 */     return (PathNavigate)pathnavigateflying;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 159 */     return this.height * 0.6F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 168 */     func_192006_b(this.world, (Entity)this);
/*     */     
/* 170 */     if (this.field_192019_bM == null || this.field_192019_bM.distanceSq(this.posX, this.posY, this.posZ) > 12.0D || this.world.getBlockState(this.field_192019_bM).getBlock() != Blocks.JUKEBOX) {
/*     */       
/* 172 */       this.field_192018_bL = false;
/* 173 */       this.field_192019_bM = null;
/*     */     } 
/*     */     
/* 176 */     super.onLivingUpdate();
/* 177 */     func_192001_dv();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191987_a(BlockPos p_191987_1_, boolean p_191987_2_) {
/* 182 */     this.field_192019_bM = p_191987_1_;
/* 183 */     this.field_192018_bL = p_191987_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192004_dr() {
/* 188 */     return this.field_192018_bL;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_192001_dv() {
/* 193 */     this.field_192011_bE = this.field_192008_bB;
/* 194 */     this.field_192010_bD = this.field_192009_bC;
/* 195 */     this.field_192009_bC = (float)(this.field_192009_bC + (this.onGround ? -1 : 4) * 0.3D);
/* 196 */     this.field_192009_bC = MathHelper.clamp(this.field_192009_bC, 0.0F, 1.0F);
/*     */     
/* 198 */     if (!this.onGround && this.field_192012_bF < 1.0F)
/*     */     {
/* 200 */       this.field_192012_bF = 1.0F;
/*     */     }
/*     */     
/* 203 */     this.field_192012_bF = (float)(this.field_192012_bF * 0.9D);
/*     */     
/* 205 */     if (!this.onGround && this.motionY < 0.0D)
/*     */     {
/* 207 */       this.motionY *= 0.6D;
/*     */     }
/*     */     
/* 210 */     this.field_192008_bB += this.field_192012_bF * 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean func_192006_b(World p_192006_0_, Entity p_192006_1_) {
/* 215 */     if (!p_192006_1_.isSilent() && p_192006_0_.rand.nextInt(50) == 0) {
/*     */       
/* 217 */       List<EntityLiving> list = p_192006_0_.getEntitiesWithinAABB(EntityLiving.class, p_192006_1_.getEntityBoundingBox().expandXyz(20.0D), field_192014_bH);
/*     */       
/* 219 */       if (!list.isEmpty()) {
/*     */         
/* 221 */         EntityLiving entityliving = list.get(p_192006_0_.rand.nextInt(list.size()));
/*     */         
/* 223 */         if (!entityliving.isSilent()) {
/*     */           
/* 225 */           SoundEvent soundevent = func_191999_g(EntityList.field_191308_b.getIDForObject(entityliving.getClass()));
/* 226 */           p_192006_0_.playSound(null, p_192006_1_.posX, p_192006_1_.posY, p_192006_1_.posZ, soundevent, p_192006_1_.getSoundCategory(), 0.7F, func_192000_b(p_192006_0_.rand));
/* 227 */           return true;
/*     */         } 
/*     */       } 
/*     */       
/* 231 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 235 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 241 */     ItemStack itemstack = player.getHeldItem(hand);
/*     */     
/* 243 */     if (!isTamed() && field_192016_bJ.contains(itemstack.getItem())) {
/*     */       
/* 245 */       if (!player.capabilities.isCreativeMode)
/*     */       {
/* 247 */         itemstack.func_190918_g(1);
/*     */       }
/*     */       
/* 250 */       if (!isSilent())
/*     */       {
/* 252 */         this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.field_192797_eu, getSoundCategory(), 1.0F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F);
/*     */       }
/*     */       
/* 255 */       if (!this.world.isRemote)
/*     */       {
/* 257 */         if (this.rand.nextInt(10) == 0) {
/*     */           
/* 259 */           func_193101_c(player);
/* 260 */           playTameEffect(true);
/* 261 */           this.world.setEntityState((Entity)this, (byte)7);
/*     */         }
/*     */         else {
/*     */           
/* 265 */           playTameEffect(false);
/* 266 */           this.world.setEntityState((Entity)this, (byte)6);
/*     */         } 
/*     */       }
/*     */       
/* 270 */       return true;
/*     */     } 
/* 272 */     if (itemstack.getItem() == field_192015_bI) {
/*     */       
/* 274 */       if (!player.capabilities.isCreativeMode)
/*     */       {
/* 276 */         itemstack.func_190918_g(1);
/*     */       }
/*     */       
/* 279 */       addPotionEffect(new PotionEffect(MobEffects.POISON, 900));
/*     */       
/* 281 */       if (player.isCreative() || !func_190530_aW())
/*     */       {
/* 283 */         attackEntityFrom(DamageSource.causePlayerDamage(player), Float.MAX_VALUE);
/*     */       }
/*     */       
/* 286 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 290 */     if (!this.world.isRemote && !func_192002_a() && isTamed() && isOwner((EntityLivingBase)player))
/*     */     {
/* 292 */       this.aiSit.setSitting(!isSitting());
/*     */     }
/*     */     
/* 295 */     return super.processInteract(player, hand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBreedingItem(ItemStack stack) {
/* 305 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 313 */     int i = MathHelper.floor(this.posX);
/* 314 */     int j = MathHelper.floor((getEntityBoundingBox()).minY);
/* 315 */     int k = MathHelper.floor(this.posZ);
/* 316 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 317 */     Block block = this.world.getBlockState(blockpos.down()).getBlock();
/* 318 */     return !(!(block instanceof net.minecraft.block.BlockLeaves) && block != Blocks.GRASS && !(block instanceof net.minecraft.block.BlockLog) && (block != Blocks.AIR || this.world.getLight(blockpos) <= 8 || !super.getCanSpawnHere()));
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
/*     */   protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canMateWith(EntityAnimal otherAnimal) {
/* 334 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EntityAgeable createChild(EntityAgeable ageable) {
/* 340 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_192005_a(World p_192005_0_, Entity p_192005_1_) {
/* 345 */     if (!p_192005_1_.isSilent() && !func_192006_b(p_192005_0_, p_192005_1_) && p_192005_0_.rand.nextInt(200) == 0)
/*     */     {
/* 347 */       p_192005_0_.playSound(null, p_192005_1_.posX, p_192005_1_.posY, p_192005_1_.posZ, func_192003_a(p_192005_0_.rand), p_192005_1_.getSoundCategory(), 1.0F, func_192000_b(p_192005_0_.rand));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 353 */     return entityIn.attackEntityFrom(DamageSource.causeMobDamage((EntityLivingBase)this), 3.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public SoundEvent getAmbientSound() {
/* 359 */     return func_192003_a(this.rand);
/*     */   }
/*     */ 
/*     */   
/*     */   private static SoundEvent func_192003_a(Random p_192003_0_) {
/* 364 */     if (p_192003_0_.nextInt(1000) == 0) {
/*     */       
/* 366 */       List<Integer> list = new ArrayList<>((Collection<? extends Integer>)field_192017_bK.keySet());
/* 367 */       return func_191999_g(((Integer)list.get(p_192003_0_.nextInt(list.size()))).intValue());
/*     */     } 
/*     */ 
/*     */     
/* 371 */     return SoundEvents.field_192792_ep;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static SoundEvent func_191999_g(int p_191999_0_) {
/* 377 */     return field_192017_bK.containsKey(p_191999_0_) ? (SoundEvent)field_192017_bK.get(p_191999_0_) : SoundEvents.field_192792_ep;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 382 */     return SoundEvents.field_192794_er;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 387 */     return SoundEvents.field_192793_eq;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 392 */     playSound(SoundEvents.field_192795_es, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected float func_191954_d(float p_191954_1_) {
/* 397 */     playSound(SoundEvents.field_192796_et, 0.15F, 1.0F);
/* 398 */     return p_191954_1_ + this.field_192009_bC / 2.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_191957_ae() {
/* 403 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getSoundPitch() {
/* 411 */     return func_192000_b(this.rand);
/*     */   }
/*     */ 
/*     */   
/*     */   private static float func_192000_b(Random p_192000_0_) {
/* 416 */     return (p_192000_0_.nextFloat() - p_192000_0_.nextFloat()) * 0.2F + 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundCategory getSoundCategory() {
/* 421 */     return SoundCategory.NEUTRAL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBePushed() {
/* 429 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void collideWithEntity(Entity entityIn) {
/* 434 */     if (!(entityIn instanceof EntityPlayer))
/*     */     {
/* 436 */       super.collideWithEntity(entityIn);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 445 */     if (isEntityInvulnerable(source))
/*     */     {
/* 447 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 451 */     if (this.aiSit != null)
/*     */     {
/* 453 */       this.aiSit.setSitting(false);
/*     */     }
/*     */     
/* 456 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int func_191998_ds() {
/* 462 */     return MathHelper.clamp(((Integer)this.dataManager.get(field_192013_bG)).intValue(), 0, 4);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191997_m(int p_191997_1_) {
/* 467 */     this.dataManager.set(field_192013_bG, Integer.valueOf(p_191997_1_));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 472 */     super.entityInit();
/* 473 */     this.dataManager.register(field_192013_bG, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 481 */     super.writeEntityToNBT(compound);
/* 482 */     compound.setInteger("Variant", func_191998_ds());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 490 */     super.readEntityFromNBT(compound);
/* 491 */     func_191997_m(compound.getInteger("Variant"));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 497 */     return LootTableList.field_192561_ax;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_192002_a() {
/* 502 */     return !this.onGround;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 507 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityBlaze.class), SoundEvents.field_193791_eM);
/* 508 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityCaveSpider.class), SoundEvents.field_193813_fc);
/* 509 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityCreeper.class), SoundEvents.field_193792_eN);
/* 510 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityElderGuardian.class), SoundEvents.field_193793_eO);
/* 511 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityDragon.class), SoundEvents.field_193794_eP);
/* 512 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityEnderman.class), SoundEvents.field_193795_eQ);
/* 513 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityEndermite.class), SoundEvents.field_193796_eR);
/* 514 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityEvoker.class), SoundEvents.field_193797_eS);
/* 515 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityGhast.class), SoundEvents.field_193798_eT);
/* 516 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityHusk.class), SoundEvents.field_193799_eU);
/* 517 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityIllusionIllager.class), SoundEvents.field_193800_eV);
/* 518 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityMagmaCube.class), SoundEvents.field_193801_eW);
/* 519 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityPigZombie.class), SoundEvents.field_193822_fl);
/* 520 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityPolarBear.class), SoundEvents.field_193802_eX);
/* 521 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityShulker.class), SoundEvents.field_193803_eY);
/* 522 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntitySilverfish.class), SoundEvents.field_193804_eZ);
/* 523 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntitySkeleton.class), SoundEvents.field_193811_fa);
/* 524 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntitySlime.class), SoundEvents.field_193812_fb);
/* 525 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntitySpider.class), SoundEvents.field_193813_fc);
/* 526 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityStray.class), SoundEvents.field_193814_fd);
/* 527 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityVex.class), SoundEvents.field_193815_fe);
/* 528 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityVindicator.class), SoundEvents.field_193816_ff);
/* 529 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityWitch.class), SoundEvents.field_193817_fg);
/* 530 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityWither.class), SoundEvents.field_193818_fh);
/* 531 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityWitherSkeleton.class), SoundEvents.field_193819_fi);
/* 532 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityWolf.class), SoundEvents.field_193820_fj);
/* 533 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityZombie.class), SoundEvents.field_193821_fk);
/* 534 */     field_192017_bK.put(EntityList.field_191308_b.getIDForObject(EntityZombieVillager.class), SoundEvents.field_193823_fm);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\passive\EntityParrot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */