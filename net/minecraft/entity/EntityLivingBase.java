/*      */ package net.minecraft.entity;
/*      */ 
/*      */ import com.google.common.base.Objects;
/*      */ import com.google.common.collect.Maps;
/*      */ import java.util.Collection;
/*      */ import java.util.ConcurrentModificationException;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockLadder;
/*      */ import net.minecraft.block.BlockTrapDoor;
/*      */ import net.minecraft.block.SoundType;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.enchantment.EnchantmentFrostWalker;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
/*      */ import net.minecraft.entity.ai.attributes.AttributeMap;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.ai.attributes.IAttribute;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.passive.EntityWolf;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Enchantments;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.init.MobEffects;
/*      */ import net.minecraft.init.SoundEvents;
/*      */ import net.minecraft.inventory.EntityEquipmentSlot;
/*      */ import net.minecraft.item.EnumAction;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.item.ItemElytra;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.datasync.DataSerializers;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.network.play.server.SPacketAnimation;
/*      */ import net.minecraft.network.play.server.SPacketCollectItem;
/*      */ import net.minecraft.network.play.server.SPacketEntityEquipment;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.potion.PotionUtils;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.util.CombatRules;
/*      */ import net.minecraft.util.CombatTracker;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EntityDamageSource;
/*      */ import net.minecraft.util.EntitySelectors;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.EnumHandSide;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.NonNullList;
/*      */ import net.minecraft.util.SoundEvent;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public abstract class EntityLivingBase
/*      */   extends Entity
/*      */ {
/*   80 */   private static final Logger field_190632_a = LogManager.getLogger();
/*   81 */   private static final UUID SPRINTING_SPEED_BOOST_ID = UUID.fromString("662A6B8D-DA3E-4C1C-8813-96EA6097278D");
/*   82 */   private static final AttributeModifier SPRINTING_SPEED_BOOST = (new AttributeModifier(SPRINTING_SPEED_BOOST_ID, "Sprinting speed boost", 0.30000001192092896D, 2)).setSaved(false);
/*   83 */   protected static final DataParameter<Byte> HAND_STATES = EntityDataManager.createKey(EntityLivingBase.class, DataSerializers.BYTE);
/*   84 */   public static final DataParameter<Float> HEALTH = EntityDataManager.createKey(EntityLivingBase.class, DataSerializers.FLOAT);
/*   85 */   public static final DataParameter<Integer> POTION_EFFECTS = EntityDataManager.createKey(EntityLivingBase.class, DataSerializers.VARINT);
/*   86 */   public static final DataParameter<Boolean> HIDE_PARTICLES = EntityDataManager.createKey(EntityLivingBase.class, DataSerializers.BOOLEAN);
/*   87 */   public static final DataParameter<Integer> ARROW_COUNT_IN_ENTITY = EntityDataManager.createKey(EntityLivingBase.class, DataSerializers.VARINT);
/*      */   private AbstractAttributeMap attributeMap;
/*   89 */   private final CombatTracker _combatTracker = new CombatTracker(this);
/*   90 */   private final Map<Potion, PotionEffect> activePotionsMap = Maps.newHashMap();
/*   91 */   private final NonNullList<ItemStack> handInventory = NonNullList.func_191197_a(2, ItemStack.field_190927_a);
/*   92 */   private final NonNullList<ItemStack> armorArray = NonNullList.func_191197_a(4, ItemStack.field_190927_a);
/*      */   
/*      */   public boolean isSwingInProgress;
/*      */   
/*      */   public EnumHand swingingHand;
/*      */   
/*      */   public int swingProgressInt;
/*      */   
/*      */   public int arrowHitTimer;
/*      */   
/*      */   public int hurtTime;
/*      */   
/*      */   public int maxHurtTime;
/*      */   
/*      */   public float attackedAtYaw;
/*      */   
/*      */   public int deathTime;
/*      */   
/*      */   public float prevSwingProgress;
/*      */   
/*      */   public float swingProgress;
/*      */   
/*      */   protected int ticksSinceLastSwing;
/*      */   
/*      */   public float prevLimbSwingAmount;
/*      */   
/*      */   public float limbSwingAmount;
/*      */   
/*      */   public float limbSwing;
/*  121 */   public int maxHurtResistantTime = 20;
/*      */   
/*      */   public float prevCameraPitch;
/*      */   
/*      */   public float cameraPitch;
/*      */   
/*      */   public float randomUnused2;
/*      */   
/*      */   public float randomUnused1;
/*      */   
/*      */   public float renderYawOffset;
/*      */   
/*      */   public float prevRenderYawOffset;
/*      */   
/*      */   public float rotationYawHead;
/*      */   
/*      */   public float prevRotationYawHead;
/*  138 */   public float jumpMovementFactor = 0.02F;
/*      */ 
/*      */   
/*      */   protected EntityPlayer attackingPlayer;
/*      */ 
/*      */   
/*      */   protected int recentlyHit;
/*      */ 
/*      */   
/*      */   protected boolean dead;
/*      */ 
/*      */   
/*      */   protected int entityAge;
/*      */ 
/*      */   
/*      */   protected float prevOnGroundSpeedFactor;
/*      */ 
/*      */   
/*      */   protected float onGroundSpeedFactor;
/*      */ 
/*      */   
/*      */   protected float movedDistance;
/*      */ 
/*      */   
/*      */   protected float prevMovedDistance;
/*      */ 
/*      */   
/*      */   protected float unused180;
/*      */ 
/*      */   
/*      */   protected int scoreValue;
/*      */ 
/*      */   
/*      */   protected float lastDamage;
/*      */ 
/*      */   
/*      */   protected boolean isJumping;
/*      */ 
/*      */   
/*      */   public float moveStrafing;
/*      */ 
/*      */   
/*      */   public float moveForward;
/*      */ 
/*      */   
/*      */   public float field_191988_bg;
/*      */ 
/*      */   
/*      */   public float randomYawVelocity;
/*      */ 
/*      */   
/*      */   protected int newPosRotationIncrements;
/*      */ 
/*      */   
/*      */   protected double interpTargetX;
/*      */ 
/*      */   
/*      */   protected double interpTargetY;
/*      */ 
/*      */   
/*      */   protected double interpTargetZ;
/*      */ 
/*      */   
/*      */   protected double interpTargetYaw;
/*      */ 
/*      */   
/*      */   protected double interpTargetPitch;
/*      */ 
/*      */   
/*      */   private boolean potionsNeedUpdate = true;
/*      */ 
/*      */   
/*      */   private EntityLivingBase entityLivingToAttack;
/*      */ 
/*      */   
/*      */   private int revengeTimer;
/*      */ 
/*      */   
/*      */   private EntityLivingBase lastAttacker;
/*      */ 
/*      */   
/*      */   private int lastAttackerTime;
/*      */   
/*      */   private float landMovementFactor;
/*      */   
/*      */   private int jumpTicks;
/*      */   
/*      */   private float absorptionAmount;
/*      */   
/*  227 */   protected ItemStack activeItemStack = ItemStack.field_190927_a;
/*      */   
/*      */   protected int activeItemStackUseCount;
/*      */   
/*      */   protected int ticksElytraFlying;
/*      */   
/*      */   private BlockPos prevBlockpos;
/*      */   
/*      */   private DamageSource lastDamageSource;
/*      */   
/*      */   private long lastDamageStamp;
/*      */ 
/*      */   
/*      */   public void onKillCommand() {
/*  241 */     attackEntityFrom(DamageSource.outOfWorld, Float.MAX_VALUE);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityLivingBase(World worldIn) {
/*  246 */     super(worldIn);
/*  247 */     applyEntityAttributes();
/*  248 */     setHealth(getMaxHealth());
/*  249 */     this.preventEntitySpawning = true;
/*  250 */     this.randomUnused1 = (float)((Math.random() + 1.0D) * 0.009999999776482582D);
/*  251 */     setPosition(this.posX, this.posY, this.posZ);
/*  252 */     this.randomUnused2 = (float)Math.random() * 12398.0F;
/*  253 */     this.rotationYaw = (float)(Math.random() * 6.283185307179586D);
/*  254 */     this.rotationYawHead = this.rotationYaw;
/*  255 */     this.stepHeight = 0.6F;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  260 */     this.dataManager.register(HAND_STATES, Byte.valueOf((byte)0));
/*  261 */     this.dataManager.register(POTION_EFFECTS, Integer.valueOf(0));
/*  262 */     this.dataManager.register(HIDE_PARTICLES, Boolean.valueOf(false));
/*  263 */     this.dataManager.register(ARROW_COUNT_IN_ENTITY, Integer.valueOf(0));
/*  264 */     this.dataManager.register(HEALTH, Float.valueOf(1.0F));
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEntityAttributes() {
/*  269 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.MAX_HEALTH);
/*  270 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE);
/*  271 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
/*  272 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.ARMOR);
/*  273 */     getAttributeMap().registerAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
/*  278 */     if (!isInWater())
/*      */     {
/*  280 */       handleWaterMovement();
/*      */     }
/*      */     
/*  283 */     if (!this.world.isRemote && this.fallDistance > 3.0F && onGroundIn) {
/*      */       
/*  285 */       float f = MathHelper.ceil(this.fallDistance - 3.0F);
/*      */       
/*  287 */       if (state.getMaterial() != Material.AIR) {
/*      */         
/*  289 */         double d0 = Math.min((0.2F + f / 15.0F), 2.5D);
/*  290 */         int i = (int)(150.0D * d0);
/*  291 */         ((WorldServer)this.world).spawnParticle(EnumParticleTypes.BLOCK_DUST, this.posX, this.posY, this.posZ, i, 0.0D, 0.0D, 0.0D, 0.15000000596046448D, new int[] { Block.getStateId(state) });
/*      */       } 
/*      */     } 
/*      */     
/*  295 */     super.updateFallState(y, onGroundIn, state, pos);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBreatheUnderwater() {
/*  300 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityUpdate() {
/*  308 */     this.prevSwingProgress = this.swingProgress;
/*  309 */     super.onEntityUpdate();
/*  310 */     this.world.theProfiler.startSection("livingEntityBaseTick");
/*  311 */     boolean flag = this instanceof EntityPlayer;
/*      */     
/*  313 */     if (isEntityAlive())
/*      */     {
/*  315 */       if (isEntityInsideOpaqueBlock()) {
/*      */         
/*  317 */         attackEntityFrom(DamageSource.inWall, 1.0F);
/*      */       }
/*  319 */       else if (flag && !this.world.getWorldBorder().contains(getEntityBoundingBox())) {
/*      */         
/*  321 */         double d0 = this.world.getWorldBorder().getClosestDistance(this) + this.world.getWorldBorder().getDamageBuffer();
/*      */         
/*  323 */         if (d0 < 0.0D) {
/*      */           
/*  325 */           double d1 = this.world.getWorldBorder().getDamageAmount();
/*      */           
/*  327 */           if (d1 > 0.0D)
/*      */           {
/*  329 */             attackEntityFrom(DamageSource.inWall, Math.max(1, MathHelper.floor(-d0 * d1)));
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/*  335 */     if (isImmuneToFire() || this.world.isRemote)
/*      */     {
/*  337 */       extinguish();
/*      */     }
/*      */     
/*  340 */     boolean flag1 = (flag && ((EntityPlayer)this).capabilities.disableDamage);
/*      */     
/*  342 */     if (isEntityAlive()) {
/*      */       
/*  344 */       if (!isInsideOfMaterial(Material.WATER)) {
/*      */         
/*  346 */         setAir(300);
/*      */       }
/*      */       else {
/*      */         
/*  350 */         if (!canBreatheUnderwater() && !isPotionActive(MobEffects.WATER_BREATHING) && !flag1) {
/*      */           
/*  352 */           setAir(decreaseAirSupply(getAir()));
/*      */           
/*  354 */           if (getAir() == -20) {
/*      */             
/*  356 */             setAir(0);
/*      */             
/*  358 */             for (int i = 0; i < 8; i++) {
/*      */               
/*  360 */               float f2 = this.rand.nextFloat() - this.rand.nextFloat();
/*  361 */               float f = this.rand.nextFloat() - this.rand.nextFloat();
/*  362 */               float f1 = this.rand.nextFloat() - this.rand.nextFloat();
/*  363 */               this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + f2, this.posY + f, this.posZ + f1, this.motionX, this.motionY, this.motionZ, new int[0]);
/*      */             } 
/*      */             
/*  366 */             attackEntityFrom(DamageSource.drown, 2.0F);
/*      */           } 
/*      */         } 
/*      */         
/*  370 */         if (!this.world.isRemote && isRiding() && getRidingEntity() instanceof EntityLivingBase)
/*      */         {
/*  372 */           dismountRidingEntity();
/*      */         }
/*      */       } 
/*      */       
/*  376 */       if (!this.world.isRemote) {
/*      */         
/*  378 */         BlockPos blockpos = new BlockPos(this);
/*      */         
/*  380 */         if (!Objects.equal(this.prevBlockpos, blockpos)) {
/*      */           
/*  382 */           this.prevBlockpos = blockpos;
/*  383 */           frostWalk(blockpos);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  388 */     if (isEntityAlive() && isWet())
/*      */     {
/*  390 */       extinguish();
/*      */     }
/*      */     
/*  393 */     this.prevCameraPitch = this.cameraPitch;
/*      */     
/*  395 */     if (this.hurtTime > 0)
/*      */     {
/*  397 */       this.hurtTime--;
/*      */     }
/*      */     
/*  400 */     if (this.hurtResistantTime > 0 && !(this instanceof EntityPlayerMP))
/*      */     {
/*  402 */       this.hurtResistantTime--;
/*      */     }
/*      */     
/*  405 */     if (getHealth() <= 0.0F)
/*      */     {
/*  407 */       onDeathUpdate();
/*      */     }
/*      */     
/*  410 */     if (this.recentlyHit > 0) {
/*      */       
/*  412 */       this.recentlyHit--;
/*      */     }
/*      */     else {
/*      */       
/*  416 */       this.attackingPlayer = null;
/*      */     } 
/*      */     
/*  419 */     if (this.lastAttacker != null && !this.lastAttacker.isEntityAlive())
/*      */     {
/*  421 */       this.lastAttacker = null;
/*      */     }
/*      */     
/*  424 */     if (this.entityLivingToAttack != null)
/*      */     {
/*  426 */       if (!this.entityLivingToAttack.isEntityAlive()) {
/*      */         
/*  428 */         setRevengeTarget((EntityLivingBase)null);
/*      */       }
/*  430 */       else if (this.ticksExisted - this.revengeTimer > 100) {
/*      */         
/*  432 */         setRevengeTarget((EntityLivingBase)null);
/*      */       } 
/*      */     }
/*      */     
/*  436 */     updatePotionEffects();
/*  437 */     this.prevMovedDistance = this.movedDistance;
/*  438 */     this.prevRenderYawOffset = this.renderYawOffset;
/*  439 */     this.prevRotationYawHead = this.rotationYawHead;
/*  440 */     this.prevRotationYaw = this.rotationYaw;
/*  441 */     this.prevRotationPitch = this.rotationPitch;
/*  442 */     this.world.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void frostWalk(BlockPos pos) {
/*  447 */     int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FROST_WALKER, this);
/*      */     
/*  449 */     if (i > 0)
/*      */     {
/*  451 */       EnchantmentFrostWalker.freezeNearby(this, this.world, pos, i);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isChild() {
/*  460 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onDeathUpdate() {
/*  468 */     this.deathTime++;
/*      */     
/*  470 */     if (this.deathTime == 20) {
/*      */       
/*  472 */       if (!this.world.isRemote && (isPlayer() || (this.recentlyHit > 0 && canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot")))) {
/*      */         
/*  474 */         int i = getExperiencePoints(this.attackingPlayer);
/*      */         
/*  476 */         while (i > 0) {
/*      */           
/*  478 */           int j = EntityXPOrb.getXPSplit(i);
/*  479 */           i -= j;
/*  480 */           this.world.spawnEntityInWorld((Entity)new EntityXPOrb(this.world, this.posX, this.posY, this.posZ, j));
/*      */         } 
/*      */       } 
/*      */       
/*  484 */       setDead();
/*      */       
/*  486 */       for (int k = 0; k < 20; k++) {
/*      */         
/*  488 */         double d2 = this.rand.nextGaussian() * 0.02D;
/*  489 */         double d0 = this.rand.nextGaussian() * 0.02D;
/*  490 */         double d1 = this.rand.nextGaussian() * 0.02D;
/*  491 */         this.world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width, this.posY + (this.rand.nextFloat() * this.height), this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width, d2, d0, d1, new int[0]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canDropLoot() {
/*  501 */     return !isChild();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int decreaseAirSupply(int air) {
/*  509 */     int i = EnchantmentHelper.getRespirationModifier(this);
/*  510 */     return (i > 0 && this.rand.nextInt(i + 1) > 0) ? air : (air - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int getExperiencePoints(EntityPlayer player) {
/*  518 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isPlayer() {
/*  526 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Random getRNG() {
/*  531 */     return this.rand;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityLivingBase getAITarget() {
/*  537 */     return this.entityLivingToAttack;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getRevengeTimer() {
/*  542 */     return this.revengeTimer;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRevengeTarget(@Nullable EntityLivingBase livingBase) {
/*  547 */     this.entityLivingToAttack = livingBase;
/*  548 */     this.revengeTimer = this.ticksExisted;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityLivingBase getLastAttacker() {
/*  553 */     return this.lastAttacker;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLastAttackerTime() {
/*  558 */     return this.lastAttackerTime;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLastAttacker(Entity entityIn) {
/*  563 */     if (entityIn instanceof EntityLivingBase) {
/*      */       
/*  565 */       this.lastAttacker = (EntityLivingBase)entityIn;
/*      */     }
/*      */     else {
/*      */       
/*  569 */       this.lastAttacker = null;
/*      */     } 
/*      */     
/*  572 */     this.lastAttackerTime = this.ticksExisted;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAge() {
/*  577 */     return this.entityAge;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void playEquipSound(ItemStack stack) {
/*  582 */     if (!stack.func_190926_b()) {
/*      */       
/*  584 */       SoundEvent soundevent = SoundEvents.ITEM_ARMOR_EQUIP_GENERIC;
/*  585 */       Item item = stack.getItem();
/*      */       
/*  587 */       if (item instanceof ItemArmor) {
/*      */         
/*  589 */         soundevent = ((ItemArmor)item).getArmorMaterial().getSoundEvent();
/*      */       }
/*  591 */       else if (item == Items.ELYTRA) {
/*      */         
/*  593 */         soundevent = SoundEvents.field_191258_p;
/*      */       } 
/*      */       
/*  596 */       playSound(soundevent, 1.0F, 1.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeEntityToNBT(NBTTagCompound compound) {
/*  605 */     compound.setFloat("Health", getHealth());
/*  606 */     compound.setShort("HurtTime", (short)this.hurtTime);
/*  607 */     compound.setInteger("HurtByTimestamp", this.revengeTimer);
/*  608 */     compound.setShort("DeathTime", (short)this.deathTime);
/*  609 */     compound.setFloat("AbsorptionAmount", getAbsorptionAmount()); byte b; int i;
/*      */     EntityEquipmentSlot[] arrayOfEntityEquipmentSlot;
/*  611 */     for (i = (arrayOfEntityEquipmentSlot = EntityEquipmentSlot.values()).length, b = 0; b < i; ) { EntityEquipmentSlot entityequipmentslot = arrayOfEntityEquipmentSlot[b];
/*      */       
/*  613 */       ItemStack itemstack = getItemStackFromSlot(entityequipmentslot);
/*      */       
/*  615 */       if (!itemstack.func_190926_b())
/*      */       {
/*  617 */         getAttributeMap().removeAttributeModifiers(itemstack.getAttributeModifiers(entityequipmentslot));
/*      */       }
/*      */       b++; }
/*      */     
/*  621 */     compound.setTag("Attributes", (NBTBase)SharedMonsterAttributes.writeBaseAttributeMapToNBT(getAttributeMap()));
/*      */     
/*  623 */     for (i = (arrayOfEntityEquipmentSlot = EntityEquipmentSlot.values()).length, b = 0; b < i; ) { EntityEquipmentSlot entityequipmentslot1 = arrayOfEntityEquipmentSlot[b];
/*      */       
/*  625 */       ItemStack itemstack1 = getItemStackFromSlot(entityequipmentslot1);
/*      */       
/*  627 */       if (!itemstack1.func_190926_b())
/*      */       {
/*  629 */         getAttributeMap().applyAttributeModifiers(itemstack1.getAttributeModifiers(entityequipmentslot1));
/*      */       }
/*      */       b++; }
/*      */     
/*  633 */     if (!this.activePotionsMap.isEmpty()) {
/*      */       
/*  635 */       NBTTagList nbttaglist = new NBTTagList();
/*      */       
/*  637 */       for (PotionEffect potioneffect : this.activePotionsMap.values())
/*      */       {
/*  639 */         nbttaglist.appendTag((NBTBase)potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
/*      */       }
/*      */       
/*  642 */       compound.setTag("ActiveEffects", (NBTBase)nbttaglist);
/*      */     } 
/*      */     
/*  645 */     compound.setBoolean("FallFlying", isElytraFlying());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readEntityFromNBT(NBTTagCompound compound) {
/*  653 */     setAbsorptionAmount(compound.getFloat("AbsorptionAmount"));
/*      */     
/*  655 */     if (compound.hasKey("Attributes", 9) && this.world != null && !this.world.isRemote)
/*      */     {
/*  657 */       SharedMonsterAttributes.setAttributeModifiers(getAttributeMap(), compound.getTagList("Attributes", 10));
/*      */     }
/*      */     
/*  660 */     if (compound.hasKey("ActiveEffects", 9)) {
/*      */       
/*  662 */       NBTTagList nbttaglist = compound.getTagList("ActiveEffects", 10);
/*      */       
/*  664 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*      */         
/*  666 */         NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/*  667 */         PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttagcompound);
/*      */         
/*  669 */         if (potioneffect != null)
/*      */         {
/*  671 */           this.activePotionsMap.put(potioneffect.getPotion(), potioneffect);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  676 */     if (compound.hasKey("Health", 99))
/*      */     {
/*  678 */       setHealth(compound.getFloat("Health"));
/*      */     }
/*      */     
/*  681 */     this.hurtTime = compound.getShort("HurtTime");
/*  682 */     this.deathTime = compound.getShort("DeathTime");
/*  683 */     this.revengeTimer = compound.getInteger("HurtByTimestamp");
/*      */     
/*  685 */     if (compound.hasKey("Team", 8)) {
/*      */       
/*  687 */       String s = compound.getString("Team");
/*  688 */       boolean flag = this.world.getScoreboard().addPlayerToTeam(getCachedUniqueIdString(), s);
/*      */       
/*  690 */       if (!flag)
/*      */       {
/*  692 */         field_190632_a.warn("Unable to add mob to team \"" + s + "\" (that team probably doesn't exist)");
/*      */       }
/*      */     } 
/*      */     
/*  696 */     if (compound.getBoolean("FallFlying"))
/*      */     {
/*  698 */       setFlag(7, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updatePotionEffects() {
/*  704 */     Iterator<Potion> iterator = this.activePotionsMap.keySet().iterator();
/*      */ 
/*      */     
/*      */     try {
/*  708 */       while (iterator.hasNext())
/*      */       {
/*  710 */         Potion potion = iterator.next();
/*  711 */         PotionEffect potioneffect = this.activePotionsMap.get(potion);
/*      */         
/*  713 */         if (!potioneffect.onUpdate(this)) {
/*      */           
/*  715 */           if (!this.world.isRemote) {
/*      */             
/*  717 */             iterator.remove();
/*  718 */             onFinishedPotionEffect(potioneffect);
/*      */           }  continue;
/*      */         } 
/*  721 */         if (potioneffect.getDuration() % 600 == 0)
/*      */         {
/*  723 */           onChangedPotionEffect(potioneffect, false);
/*      */         }
/*      */       }
/*      */     
/*  727 */     } catch (ConcurrentModificationException concurrentModificationException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  732 */     if (this.potionsNeedUpdate) {
/*      */       
/*  734 */       if (!this.world.isRemote)
/*      */       {
/*  736 */         updatePotionMetadata();
/*      */       }
/*      */       
/*  739 */       this.potionsNeedUpdate = false;
/*      */     } 
/*      */     
/*  742 */     int i = ((Integer)this.dataManager.get(POTION_EFFECTS)).intValue();
/*  743 */     boolean flag1 = ((Boolean)this.dataManager.get(HIDE_PARTICLES)).booleanValue();
/*      */     
/*  745 */     if (i > 0) {
/*      */       boolean flag;
/*      */       
/*      */       int j;
/*  749 */       if (isInvisible()) {
/*      */         
/*  751 */         flag = (this.rand.nextInt(15) == 0);
/*      */       }
/*      */       else {
/*      */         
/*  755 */         flag = this.rand.nextBoolean();
/*      */       } 
/*      */       
/*  758 */       if (flag1)
/*      */       {
/*  760 */         j = flag & ((this.rand.nextInt(5) == 0) ? 1 : 0);
/*      */       }
/*      */       
/*  763 */       if (j != 0 && i > 0) {
/*      */         
/*  765 */         double d0 = (i >> 16 & 0xFF) / 255.0D;
/*  766 */         double d1 = (i >> 8 & 0xFF) / 255.0D;
/*  767 */         double d2 = (i >> 0 & 0xFF) / 255.0D;
/*  768 */         this.world.spawnParticle(flag1 ? EnumParticleTypes.SPELL_MOB_AMBIENT : EnumParticleTypes.SPELL_MOB, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, d0, d1, d2, new int[0]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updatePotionMetadata() {
/*  779 */     if (this.activePotionsMap.isEmpty()) {
/*      */       
/*  781 */       resetPotionEffectMetadata();
/*  782 */       setInvisible(false);
/*      */     }
/*      */     else {
/*      */       
/*  786 */       Collection<PotionEffect> collection = this.activePotionsMap.values();
/*  787 */       this.dataManager.set(HIDE_PARTICLES, Boolean.valueOf(areAllPotionsAmbient(collection)));
/*  788 */       this.dataManager.set(POTION_EFFECTS, Integer.valueOf(PotionUtils.getPotionColorFromEffectList(collection)));
/*  789 */       setInvisible(isPotionActive(MobEffects.INVISIBILITY));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean areAllPotionsAmbient(Collection<PotionEffect> potionEffects) {
/*  798 */     for (PotionEffect potioneffect : potionEffects) {
/*      */       
/*  800 */       if (!potioneffect.getIsAmbient())
/*      */       {
/*  802 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  806 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetPotionEffectMetadata() {
/*  814 */     this.dataManager.set(HIDE_PARTICLES, Boolean.valueOf(false));
/*  815 */     this.dataManager.set(POTION_EFFECTS, Integer.valueOf(0));
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearActivePotions() {
/*  820 */     if (!this.world.isRemote) {
/*      */       
/*  822 */       Iterator<PotionEffect> iterator = this.activePotionsMap.values().iterator();
/*      */       
/*  824 */       while (iterator.hasNext()) {
/*      */         
/*  826 */         onFinishedPotionEffect(iterator.next());
/*  827 */         iterator.remove();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Collection<PotionEffect> getActivePotionEffects() {
/*  834 */     return this.activePotionsMap.values();
/*      */   }
/*      */ 
/*      */   
/*      */   public Map<Potion, PotionEffect> func_193076_bZ() {
/*  839 */     return this.activePotionsMap;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPotionActive(Potion potionIn) {
/*  844 */     return this.activePotionsMap.containsKey(potionIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public PotionEffect getActivePotionEffect(Potion potionIn) {
/*  854 */     return this.activePotionsMap.get(potionIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addPotionEffect(PotionEffect potioneffectIn) {
/*  862 */     if (isPotionApplicable(potioneffectIn)) {
/*      */       
/*  864 */       PotionEffect potioneffect = this.activePotionsMap.get(potioneffectIn.getPotion());
/*      */       
/*  866 */       if (potioneffect == null) {
/*      */         
/*  868 */         this.activePotionsMap.put(potioneffectIn.getPotion(), potioneffectIn);
/*  869 */         onNewPotionEffect(potioneffectIn);
/*      */       }
/*      */       else {
/*      */         
/*  873 */         potioneffect.combine(potioneffectIn);
/*  874 */         onChangedPotionEffect(potioneffect, true);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPotionApplicable(PotionEffect potioneffectIn) {
/*  881 */     if (getCreatureAttribute() == EnumCreatureAttribute.UNDEAD) {
/*      */       
/*  883 */       Potion potion = potioneffectIn.getPotion();
/*      */       
/*  885 */       if (potion == MobEffects.REGENERATION || potion == MobEffects.POISON)
/*      */       {
/*  887 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  891 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityUndead() {
/*  899 */     return (getCreatureAttribute() == EnumCreatureAttribute.UNDEAD);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public PotionEffect removeActivePotionEffect(@Nullable Potion potioneffectin) {
/*  910 */     return this.activePotionsMap.remove(potioneffectin);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removePotionEffect(Potion potionIn) {
/*  918 */     PotionEffect potioneffect = removeActivePotionEffect(potionIn);
/*      */     
/*  920 */     if (potioneffect != null)
/*      */     {
/*  922 */       onFinishedPotionEffect(potioneffect);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onNewPotionEffect(PotionEffect id) {
/*  928 */     this.potionsNeedUpdate = true;
/*      */     
/*  930 */     if (!this.world.isRemote)
/*      */     {
/*  932 */       id.getPotion().applyAttributesModifiersToEntity(this, getAttributeMap(), id.getAmplifier());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onChangedPotionEffect(PotionEffect id, boolean p_70695_2_) {
/*  938 */     this.potionsNeedUpdate = true;
/*      */     
/*  940 */     if (p_70695_2_ && !this.world.isRemote) {
/*      */       
/*  942 */       Potion potion = id.getPotion();
/*  943 */       potion.removeAttributesModifiersFromEntity(this, getAttributeMap(), id.getAmplifier());
/*  944 */       potion.applyAttributesModifiersToEntity(this, getAttributeMap(), id.getAmplifier());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onFinishedPotionEffect(PotionEffect effect) {
/*  950 */     this.potionsNeedUpdate = true;
/*      */     
/*  952 */     if (!this.world.isRemote)
/*      */     {
/*  954 */       effect.getPotion().removeAttributesModifiersFromEntity(this, getAttributeMap(), effect.getAmplifier());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void heal(float healAmount) {
/*  963 */     float f = getHealth();
/*      */     
/*  965 */     if (f > 0.0F)
/*      */     {
/*  967 */       setHealth(f + healAmount);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public final float getHealth() {
/*  973 */     return ((Float)this.dataManager.get(HEALTH)).floatValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHealth(float health) {
/*  978 */     this.dataManager.set(HEALTH, Float.valueOf(MathHelper.clamp(health, 0.0F, getMaxHealth())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  986 */     if (isEntityInvulnerable(source))
/*      */     {
/*  988 */       return false;
/*      */     }
/*  990 */     if (this.world.isRemote)
/*      */     {
/*  992 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  996 */     this.entityAge = 0;
/*      */     
/*  998 */     if (getHealth() <= 0.0F)
/*      */     {
/* 1000 */       return false;
/*      */     }
/* 1002 */     if (source.isFireDamage() && isPotionActive(MobEffects.FIRE_RESISTANCE))
/*      */     {
/* 1004 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1008 */     float f = amount;
/*      */     
/* 1010 */     if ((source == DamageSource.anvil || source == DamageSource.fallingBlock) && !getItemStackFromSlot(EntityEquipmentSlot.HEAD).func_190926_b()) {
/*      */       
/* 1012 */       getItemStackFromSlot(EntityEquipmentSlot.HEAD).damageItem((int)(amount * 4.0F + this.rand.nextFloat() * amount * 2.0F), this);
/* 1013 */       amount *= 0.75F;
/*      */     } 
/*      */     
/* 1016 */     boolean flag = false;
/*      */     
/* 1018 */     if (amount > 0.0F && canBlockDamageSource(source)) {
/*      */       
/* 1020 */       damageShield(amount);
/* 1021 */       amount = 0.0F;
/*      */       
/* 1023 */       if (!source.isProjectile()) {
/*      */         
/* 1025 */         Entity entity = source.getSourceOfDamage();
/*      */         
/* 1027 */         if (entity instanceof EntityLivingBase)
/*      */         {
/* 1029 */           func_190629_c((EntityLivingBase)entity);
/*      */         }
/*      */       } 
/*      */       
/* 1033 */       flag = true;
/*      */     } 
/*      */     
/* 1036 */     this.limbSwingAmount = 1.5F;
/* 1037 */     boolean flag1 = true;
/*      */     
/* 1039 */     if (this.hurtResistantTime > this.maxHurtResistantTime / 2.0F) {
/*      */       
/* 1041 */       if (amount <= this.lastDamage)
/*      */       {
/* 1043 */         return false;
/*      */       }
/*      */       
/* 1046 */       damageEntity(source, amount - this.lastDamage);
/* 1047 */       this.lastDamage = amount;
/* 1048 */       flag1 = false;
/*      */     }
/*      */     else {
/*      */       
/* 1052 */       this.lastDamage = amount;
/* 1053 */       this.hurtResistantTime = this.maxHurtResistantTime;
/* 1054 */       damageEntity(source, amount);
/* 1055 */       this.maxHurtTime = 10;
/* 1056 */       this.hurtTime = this.maxHurtTime;
/*      */     } 
/*      */     
/* 1059 */     this.attackedAtYaw = 0.0F;
/* 1060 */     Entity entity1 = source.getEntity();
/*      */     
/* 1062 */     if (entity1 != null) {
/*      */       
/* 1064 */       if (entity1 instanceof EntityLivingBase)
/*      */       {
/* 1066 */         setRevengeTarget((EntityLivingBase)entity1);
/*      */       }
/*      */       
/* 1069 */       if (entity1 instanceof EntityPlayer) {
/*      */         
/* 1071 */         this.recentlyHit = 100;
/* 1072 */         this.attackingPlayer = (EntityPlayer)entity1;
/*      */       }
/* 1074 */       else if (entity1 instanceof EntityWolf) {
/*      */         
/* 1076 */         EntityWolf entitywolf = (EntityWolf)entity1;
/*      */         
/* 1078 */         if (entitywolf.isTamed()) {
/*      */           
/* 1080 */           this.recentlyHit = 100;
/* 1081 */           this.attackingPlayer = null;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1086 */     if (flag1) {
/*      */       
/* 1088 */       if (flag) {
/*      */         
/* 1090 */         this.world.setEntityState(this, (byte)29);
/*      */       }
/* 1092 */       else if (source instanceof EntityDamageSource && ((EntityDamageSource)source).getIsThornsDamage()) {
/*      */         
/* 1094 */         this.world.setEntityState(this, (byte)33);
/*      */       } else {
/*      */         byte b0;
/*      */ 
/*      */ 
/*      */         
/* 1100 */         if (source == DamageSource.drown) {
/*      */           
/* 1102 */           b0 = 36;
/*      */         }
/* 1104 */         else if (source.isFireDamage()) {
/*      */           
/* 1106 */           b0 = 37;
/*      */         }
/*      */         else {
/*      */           
/* 1110 */           b0 = 2;
/*      */         } 
/*      */         
/* 1113 */         this.world.setEntityState(this, b0);
/*      */       } 
/*      */       
/* 1116 */       if (source != DamageSource.drown && (!flag || amount > 0.0F))
/*      */       {
/* 1118 */         setBeenAttacked();
/*      */       }
/*      */       
/* 1121 */       if (entity1 != null) {
/*      */         
/* 1123 */         double d1 = entity1.posX - this.posX;
/*      */         
/*      */         double d0;
/* 1126 */         for (d0 = entity1.posZ - this.posZ; d1 * d1 + d0 * d0 < 1.0E-4D; d0 = (Math.random() - Math.random()) * 0.01D)
/*      */         {
/* 1128 */           d1 = (Math.random() - Math.random()) * 0.01D;
/*      */         }
/*      */         
/* 1131 */         this.attackedAtYaw = (float)(MathHelper.atan2(d0, d1) * 57.29577951308232D - this.rotationYaw);
/* 1132 */         knockBack(entity1, 0.4F, d1, d0);
/*      */       }
/*      */       else {
/*      */         
/* 1136 */         this.attackedAtYaw = ((int)(Math.random() * 2.0D) * 180);
/*      */       } 
/*      */     } 
/*      */     
/* 1140 */     if (getHealth() <= 0.0F) {
/*      */       
/* 1142 */       if (!func_190628_d(source))
/*      */       {
/* 1144 */         SoundEvent soundevent = getDeathSound();
/*      */         
/* 1146 */         if (flag1 && soundevent != null)
/*      */         {
/* 1148 */           playSound(soundevent, getSoundVolume(), getSoundPitch());
/*      */         }
/*      */         
/* 1151 */         onDeath(source);
/*      */       }
/*      */     
/* 1154 */     } else if (flag1) {
/*      */       
/* 1156 */       playHurtSound(source);
/*      */     } 
/*      */     
/* 1159 */     boolean flag2 = !(flag && amount <= 0.0F);
/*      */     
/* 1161 */     if (flag2) {
/*      */       
/* 1163 */       this.lastDamageSource = source;
/* 1164 */       this.lastDamageStamp = this.world.getTotalWorldTime();
/*      */     } 
/*      */     
/* 1167 */     if (this instanceof EntityPlayerMP)
/*      */     {
/* 1169 */       CriteriaTriggers.field_192128_h.func_192200_a((EntityPlayerMP)this, source, f, amount, flag);
/*      */     }
/*      */     
/* 1172 */     if (entity1 instanceof EntityPlayerMP)
/*      */     {
/* 1174 */       CriteriaTriggers.field_192127_g.func_192220_a((EntityPlayerMP)entity1, this, source, f, amount, flag);
/*      */     }
/*      */     
/* 1177 */     return flag2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_190629_c(EntityLivingBase p_190629_1_) {
/* 1184 */     p_190629_1_.knockBack(this, 0.5F, this.posX - p_190629_1_.posX, this.posZ - p_190629_1_.posZ);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean func_190628_d(DamageSource p_190628_1_) {
/* 1189 */     if (p_190628_1_.canHarmInCreative())
/*      */     {
/* 1191 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1195 */     ItemStack itemstack = null; byte b; int i;
/*      */     EnumHand[] arrayOfEnumHand;
/* 1197 */     for (i = (arrayOfEnumHand = EnumHand.values()).length, b = 0; b < i; ) { EnumHand enumhand = arrayOfEnumHand[b];
/*      */       
/* 1199 */       ItemStack itemstack1 = getHeldItem(enumhand);
/*      */       
/* 1201 */       if (itemstack1.getItem() == Items.field_190929_cY) {
/*      */         
/* 1203 */         itemstack = itemstack1.copy();
/* 1204 */         itemstack1.func_190918_g(1);
/*      */         break;
/*      */       } 
/*      */       b++; }
/*      */     
/* 1209 */     if (itemstack != null) {
/*      */       
/* 1211 */       if (this instanceof EntityPlayerMP) {
/*      */         
/* 1213 */         EntityPlayerMP entityplayermp = (EntityPlayerMP)this;
/* 1214 */         entityplayermp.addStat(StatList.getObjectUseStats(Items.field_190929_cY));
/* 1215 */         CriteriaTriggers.field_193130_A.func_193187_a(entityplayermp, itemstack);
/*      */       } 
/*      */       
/* 1218 */       setHealth(1.0F);
/* 1219 */       clearActivePotions();
/* 1220 */       addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
/* 1221 */       addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
/* 1222 */       this.world.setEntityState(this, (byte)35);
/*      */     } 
/*      */     
/* 1225 */     return (itemstack != null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public DamageSource getLastDamageSource() {
/* 1232 */     if (this.world.getTotalWorldTime() - this.lastDamageStamp > 40L)
/*      */     {
/* 1234 */       this.lastDamageSource = null;
/*      */     }
/*      */     
/* 1237 */     return this.lastDamageSource;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void playHurtSound(DamageSource source) {
/* 1242 */     SoundEvent soundevent = getHurtSound(source);
/*      */     
/* 1244 */     if (soundevent != null)
/*      */     {
/* 1246 */       playSound(soundevent, getSoundVolume(), getSoundPitch());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canBlockDamageSource(DamageSource damageSourceIn) {
/* 1256 */     if (!damageSourceIn.isUnblockable() && isActiveItemStackBlocking()) {
/*      */       
/* 1258 */       Vec3d vec3d = damageSourceIn.getDamageLocation();
/*      */       
/* 1260 */       if (vec3d != null) {
/*      */         
/* 1262 */         Vec3d vec3d1 = getLook(1.0F);
/* 1263 */         Vec3d vec3d2 = vec3d.subtractReverse(new Vec3d(this.posX, this.posY, this.posZ)).normalize();
/* 1264 */         vec3d2 = new Vec3d(vec3d2.xCoord, 0.0D, vec3d2.zCoord);
/*      */         
/* 1266 */         if (vec3d2.dotProduct(vec3d1) < 0.0D)
/*      */         {
/* 1268 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1273 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderBrokenItemStack(ItemStack stack) {
/* 1281 */     playSound(SoundEvents.ENTITY_ITEM_BREAK, 0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
/*      */     
/* 1283 */     for (int i = 0; i < 5; i++) {
/*      */       
/* 1285 */       Vec3d vec3d = new Vec3d((this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/* 1286 */       vec3d = vec3d.rotatePitch(-this.rotationPitch * 0.017453292F);
/* 1287 */       vec3d = vec3d.rotateYaw(-this.rotationYaw * 0.017453292F);
/* 1288 */       double d0 = -this.rand.nextFloat() * 0.6D - 0.3D;
/* 1289 */       Vec3d vec3d1 = new Vec3d((this.rand.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
/* 1290 */       vec3d1 = vec3d1.rotatePitch(-this.rotationPitch * 0.017453292F);
/* 1291 */       vec3d1 = vec3d1.rotateYaw(-this.rotationYaw * 0.017453292F);
/* 1292 */       vec3d1 = vec3d1.addVector(this.posX, this.posY + getEyeHeight(), this.posZ);
/* 1293 */       this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, vec3d.xCoord, vec3d.yCoord + 0.05D, vec3d.zCoord, new int[] { Item.getIdFromItem(stack.getItem()) });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDeath(DamageSource cause) {
/* 1302 */     if (!this.dead) {
/*      */       
/* 1304 */       Entity entity = cause.getEntity();
/* 1305 */       EntityLivingBase entitylivingbase = getAttackingEntity();
/*      */       
/* 1307 */       if (this.scoreValue >= 0 && entitylivingbase != null)
/*      */       {
/* 1309 */         entitylivingbase.func_191956_a(this, this.scoreValue, cause);
/*      */       }
/*      */       
/* 1312 */       if (entity != null)
/*      */       {
/* 1314 */         entity.onKillEntity(this);
/*      */       }
/*      */       
/* 1317 */       this.dead = true;
/* 1318 */       getCombatTracker().reset();
/*      */       
/* 1320 */       if (!this.world.isRemote) {
/*      */         
/* 1322 */         int i = 0;
/*      */         
/* 1324 */         if (entity instanceof EntityPlayer)
/*      */         {
/* 1326 */           i = EnchantmentHelper.getLootingModifier((EntityLivingBase)entity);
/*      */         }
/*      */         
/* 1329 */         if (canDropLoot() && this.world.getGameRules().getBoolean("doMobLoot")) {
/*      */           
/* 1331 */           boolean flag = (this.recentlyHit > 0);
/* 1332 */           dropLoot(flag, i, cause);
/*      */         } 
/*      */       } 
/*      */       
/* 1336 */       this.world.setEntityState(this, (byte)3);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropLoot(boolean wasRecentlyHit, int lootingModifier, DamageSource source) {
/* 1345 */     dropFewItems(wasRecentlyHit, lootingModifier);
/* 1346 */     dropEquipment(wasRecentlyHit, lootingModifier);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropEquipment(boolean wasRecentlyHit, int lootingModifier) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void knockBack(Entity entityIn, float strength, double xRatio, double zRatio) {
/* 1369 */     if (this.rand.nextDouble() >= getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue()) {
/*      */       
/* 1371 */       this.isAirBorne = true;
/* 1372 */       float f = MathHelper.sqrt(xRatio * xRatio + zRatio * zRatio);
/* 1373 */       this.motionX /= 2.0D;
/* 1374 */       this.motionZ /= 2.0D;
/* 1375 */       this.motionX -= xRatio / f * strength;
/* 1376 */       this.motionZ -= zRatio / f * strength;
/*      */       
/* 1378 */       if (this.onGround) {
/*      */         
/* 1380 */         this.motionY /= 2.0D;
/* 1381 */         this.motionY += strength;
/*      */         
/* 1383 */         if (this.motionY > 0.4000000059604645D)
/*      */         {
/* 1385 */           this.motionY = 0.4000000059604645D;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 1394 */     return SoundEvents.ENTITY_GENERIC_HURT;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent getDeathSound() {
/* 1400 */     return SoundEvents.ENTITY_GENERIC_DEATH;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getFallSound(int heightIn) {
/* 1405 */     return (heightIn > 4) ? SoundEvents.ENTITY_GENERIC_BIG_FALL : SoundEvents.ENTITY_GENERIC_SMALL_FALL;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dropFewItems(boolean wasRecentlyHit, int lootingModifier) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnLadder() {
/* 1420 */     int i = MathHelper.floor(this.posX);
/* 1421 */     int j = MathHelper.floor((getEntityBoundingBox()).minY);
/* 1422 */     int k = MathHelper.floor(this.posZ);
/*      */     
/* 1424 */     if (this instanceof EntityPlayer && ((EntityPlayer)this).isSpectator())
/*      */     {
/* 1426 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1430 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 1431 */     IBlockState iblockstate = this.world.getBlockState(blockpos);
/* 1432 */     Block block = iblockstate.getBlock();
/*      */     
/* 1434 */     if (block != Blocks.LADDER && block != Blocks.VINE)
/*      */     {
/* 1436 */       return (block instanceof BlockTrapDoor && canGoThroughtTrapDoorOnLadder(blockpos, iblockstate));
/*      */     }
/*      */ 
/*      */     
/* 1440 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean canGoThroughtTrapDoorOnLadder(BlockPos pos, IBlockState state) {
/* 1447 */     if (((Boolean)state.getValue((IProperty)BlockTrapDoor.OPEN)).booleanValue()) {
/*      */       
/* 1449 */       IBlockState iblockstate = this.world.getBlockState(pos.down());
/*      */       
/* 1451 */       if (iblockstate.getBlock() == Blocks.LADDER && iblockstate.getValue((IProperty)BlockLadder.FACING) == state.getValue((IProperty)BlockTrapDoor.FACING))
/*      */       {
/* 1453 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1457 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityAlive() {
/* 1465 */     return (!this.isDead && getHealth() > 0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/* 1470 */     super.fall(distance, damageMultiplier);
/* 1471 */     PotionEffect potioneffect = getActivePotionEffect(MobEffects.JUMP_BOOST);
/* 1472 */     float f = (potioneffect == null) ? 0.0F : (potioneffect.getAmplifier() + 1);
/* 1473 */     int i = MathHelper.ceil((distance - 3.0F - f) * damageMultiplier);
/*      */     
/* 1475 */     if (i > 0) {
/*      */       
/* 1477 */       playSound(getFallSound(i), 1.0F, 1.0F);
/* 1478 */       attackEntityFrom(DamageSource.fall, i);
/* 1479 */       int j = MathHelper.floor(this.posX);
/* 1480 */       int k = MathHelper.floor(this.posY - 0.20000000298023224D);
/* 1481 */       int l = MathHelper.floor(this.posZ);
/* 1482 */       IBlockState iblockstate = this.world.getBlockState(new BlockPos(j, k, l));
/*      */       
/* 1484 */       if (iblockstate.getMaterial() != Material.AIR) {
/*      */         
/* 1486 */         SoundType soundtype = iblockstate.getBlock().getSoundType();
/* 1487 */         playSound(soundtype.getFallSound(), soundtype.getVolume() * 0.5F, soundtype.getPitch() * 0.75F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void performHurtAnimation() {
/* 1497 */     this.maxHurtTime = 10;
/* 1498 */     this.hurtTime = this.maxHurtTime;
/* 1499 */     this.attackedAtYaw = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTotalArmorValue() {
/* 1507 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.ARMOR);
/* 1508 */     return MathHelper.floor(iattributeinstance.getAttributeValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageArmor(float damage) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageShield(float damage) {}
/*      */ 
/*      */ 
/*      */   
/*      */   protected float applyArmorCalculations(DamageSource source, float damage) {
/* 1524 */     if (!source.isUnblockable()) {
/*      */       
/* 1526 */       damageArmor(damage);
/* 1527 */       damage = CombatRules.getDamageAfterAbsorb(damage, getTotalArmorValue(), (float)getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getAttributeValue());
/*      */     } 
/*      */     
/* 1530 */     return damage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float applyPotionDamageCalculations(DamageSource source, float damage) {
/* 1538 */     if (source.isDamageAbsolute())
/*      */     {
/* 1540 */       return damage;
/*      */     }
/*      */ 
/*      */     
/* 1544 */     if (isPotionActive(MobEffects.RESISTANCE) && source != DamageSource.outOfWorld) {
/*      */       
/* 1546 */       int i = (getActivePotionEffect(MobEffects.RESISTANCE).getAmplifier() + 1) * 5;
/* 1547 */       int j = 25 - i;
/* 1548 */       float f = damage * j;
/* 1549 */       damage = f / 25.0F;
/*      */     } 
/*      */     
/* 1552 */     if (damage <= 0.0F)
/*      */     {
/* 1554 */       return 0.0F;
/*      */     }
/*      */ 
/*      */     
/* 1558 */     int k = EnchantmentHelper.getEnchantmentModifierDamage(getArmorInventoryList(), source);
/*      */     
/* 1560 */     if (k > 0)
/*      */     {
/* 1562 */       damage = CombatRules.getDamageAfterMagicAbsorb(damage, k);
/*      */     }
/*      */     
/* 1565 */     return damage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void damageEntity(DamageSource damageSrc, float damageAmount) {
/* 1576 */     if (!isEntityInvulnerable(damageSrc)) {
/*      */       
/* 1578 */       damageAmount = applyArmorCalculations(damageSrc, damageAmount);
/* 1579 */       damageAmount = applyPotionDamageCalculations(damageSrc, damageAmount);
/* 1580 */       float f = damageAmount;
/* 1581 */       damageAmount = Math.max(damageAmount - getAbsorptionAmount(), 0.0F);
/* 1582 */       setAbsorptionAmount(getAbsorptionAmount() - f - damageAmount);
/*      */       
/* 1584 */       if (damageAmount != 0.0F) {
/*      */         
/* 1586 */         float f1 = getHealth();
/* 1587 */         setHealth(f1 - damageAmount);
/* 1588 */         getCombatTracker().trackDamage(damageSrc, f1, damageAmount);
/* 1589 */         setAbsorptionAmount(getAbsorptionAmount() - damageAmount);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CombatTracker getCombatTracker() {
/* 1599 */     return this._combatTracker;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityLivingBase getAttackingEntity() {
/* 1605 */     if (this._combatTracker.getBestAttacker() != null)
/*      */     {
/* 1607 */       return this._combatTracker.getBestAttacker();
/*      */     }
/* 1609 */     if (this.attackingPlayer != null)
/*      */     {
/* 1611 */       return (EntityLivingBase)this.attackingPlayer;
/*      */     }
/*      */ 
/*      */     
/* 1615 */     return (this.entityLivingToAttack != null) ? this.entityLivingToAttack : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getMaxHealth() {
/* 1624 */     return (float)getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).getAttributeValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getArrowCountInEntity() {
/* 1632 */     return ((Integer)this.dataManager.get(ARROW_COUNT_IN_ENTITY)).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setArrowCountInEntity(int count) {
/* 1640 */     this.dataManager.set(ARROW_COUNT_IN_ENTITY, Integer.valueOf(count));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getArmSwingAnimationEnd() {
/* 1649 */     if (isPotionActive(MobEffects.HASTE))
/*      */     {
/* 1651 */       return 6 - 1 + getActivePotionEffect(MobEffects.HASTE).getAmplifier();
/*      */     }
/*      */ 
/*      */     
/* 1655 */     return isPotionActive(MobEffects.MINING_FATIGUE) ? (6 + (1 + getActivePotionEffect(MobEffects.MINING_FATIGUE).getAmplifier()) * 2) : 6;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void swingArm(EnumHand hand) {
/* 1661 */     if (!this.isSwingInProgress || this.swingProgressInt >= getArmSwingAnimationEnd() / 2 || this.swingProgressInt < 0) {
/*      */       
/* 1663 */       this.swingProgressInt = -1;
/* 1664 */       this.isSwingInProgress = true;
/* 1665 */       this.swingingHand = hand;
/*      */       
/* 1667 */       if (this.world instanceof WorldServer)
/*      */       {
/* 1669 */         ((WorldServer)this.world).getEntityTracker().sendToAllTrackingEntity(this, (Packet<?>)new SPacketAnimation(this, (hand == EnumHand.MAIN_HAND) ? 0 : 3));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {
/* 1676 */     boolean flag = (id == 33);
/* 1677 */     boolean flag1 = (id == 36);
/* 1678 */     boolean flag2 = (id == 37);
/*      */     
/* 1680 */     if (id != 2 && !flag && !flag1 && !flag2) {
/*      */       
/* 1682 */       if (id == 3) {
/*      */         
/* 1684 */         SoundEvent soundevent1 = getDeathSound();
/*      */         
/* 1686 */         if (soundevent1 != null)
/*      */         {
/* 1688 */           playSound(soundevent1, getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */         }
/*      */         
/* 1691 */         setHealth(0.0F);
/* 1692 */         onDeath(DamageSource.generic);
/*      */       }
/* 1694 */       else if (id == 30) {
/*      */         
/* 1696 */         playSound(SoundEvents.ITEM_SHIELD_BREAK, 0.8F, 0.8F + this.world.rand.nextFloat() * 0.4F);
/*      */       }
/* 1698 */       else if (id == 29) {
/*      */         
/* 1700 */         playSound(SoundEvents.ITEM_SHIELD_BLOCK, 1.0F, 0.8F + this.world.rand.nextFloat() * 0.4F);
/*      */       }
/*      */       else {
/*      */         
/* 1704 */         super.handleStatusUpdate(id);
/*      */       } 
/*      */     } else {
/*      */       DamageSource damagesource;
/*      */       
/* 1709 */       this.limbSwingAmount = 1.5F;
/* 1710 */       this.hurtResistantTime = this.maxHurtResistantTime;
/* 1711 */       this.maxHurtTime = 10;
/* 1712 */       this.hurtTime = this.maxHurtTime;
/* 1713 */       this.attackedAtYaw = 0.0F;
/*      */       
/* 1715 */       if (flag)
/*      */       {
/* 1717 */         playSound(SoundEvents.ENCHANT_THORNS_HIT, getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/* 1722 */       if (flag2) {
/*      */         
/* 1724 */         damagesource = DamageSource.onFire;
/*      */       }
/* 1726 */       else if (flag1) {
/*      */         
/* 1728 */         damagesource = DamageSource.drown;
/*      */       }
/*      */       else {
/*      */         
/* 1732 */         damagesource = DamageSource.generic;
/*      */       } 
/*      */       
/* 1735 */       SoundEvent soundevent = getHurtSound(damagesource);
/*      */       
/* 1737 */       if (soundevent != null)
/*      */       {
/* 1739 */         playSound(soundevent, getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */       }
/*      */       
/* 1742 */       attackEntityFrom(DamageSource.generic, 0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void kill() {
/* 1751 */     attackEntityFrom(DamageSource.outOfWorld, 4.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateArmSwingProgress() {
/* 1759 */     int i = getArmSwingAnimationEnd();
/*      */     
/* 1761 */     if (this.isSwingInProgress) {
/*      */       
/* 1763 */       this.swingProgressInt++;
/*      */       
/* 1765 */       if (this.swingProgressInt >= i)
/*      */       {
/* 1767 */         this.swingProgressInt = 0;
/* 1768 */         this.isSwingInProgress = false;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 1773 */       this.swingProgressInt = 0;
/*      */     } 
/*      */     
/* 1776 */     this.swingProgress = this.swingProgressInt / i;
/*      */   }
/*      */ 
/*      */   
/*      */   public IAttributeInstance getEntityAttribute(IAttribute attribute) {
/* 1781 */     return getAttributeMap().getAttributeInstance(attribute);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AbstractAttributeMap getAttributeMap() {
/* 1789 */     if (this.attributeMap == null)
/*      */     {
/* 1791 */       this.attributeMap = (AbstractAttributeMap)new AttributeMap();
/*      */     }
/*      */     
/* 1794 */     return this.attributeMap;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumCreatureAttribute getCreatureAttribute() {
/* 1802 */     return EnumCreatureAttribute.UNDEFINED;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getHeldItemMainhand() {
/* 1807 */     return getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getHeldItemOffhand() {
/* 1812 */     return getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getHeldItem(EnumHand hand) {
/* 1817 */     if (hand == EnumHand.MAIN_HAND)
/*      */     {
/* 1819 */       return getItemStackFromSlot(EntityEquipmentSlot.MAINHAND);
/*      */     }
/* 1821 */     if (hand == EnumHand.OFF_HAND)
/*      */     {
/* 1823 */       return getItemStackFromSlot(EntityEquipmentSlot.OFFHAND);
/*      */     }
/*      */ 
/*      */     
/* 1827 */     throw new IllegalArgumentException("Invalid hand " + hand);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHeldItem(EnumHand hand, ItemStack stack) {
/* 1833 */     if (hand == EnumHand.MAIN_HAND) {
/*      */       
/* 1835 */       setItemStackToSlot(EntityEquipmentSlot.MAINHAND, stack);
/*      */     }
/*      */     else {
/*      */       
/* 1839 */       if (hand != EnumHand.OFF_HAND)
/*      */       {
/* 1841 */         throw new IllegalArgumentException("Invalid hand " + hand);
/*      */       }
/*      */       
/* 1844 */       setItemStackToSlot(EntityEquipmentSlot.OFFHAND, stack);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_190630_a(EntityEquipmentSlot p_190630_1_) {
/* 1850 */     return !getItemStackFromSlot(p_190630_1_).func_190926_b();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSprinting(boolean sprinting) {
/* 1864 */     super.setSprinting(sprinting);
/* 1865 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
/*      */     
/* 1867 */     if (iattributeinstance.getModifier(SPRINTING_SPEED_BOOST_ID) != null)
/*      */     {
/* 1869 */       iattributeinstance.removeModifier(SPRINTING_SPEED_BOOST);
/*      */     }
/*      */     
/* 1872 */     if (sprinting)
/*      */     {
/* 1874 */       iattributeinstance.applyModifier(SPRINTING_SPEED_BOOST);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getSoundVolume() {
/* 1883 */     return 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected float getSoundPitch() {
/* 1891 */     return isChild() ? ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.5F) : ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isMovementBlocked() {
/* 1899 */     return (getHealth() <= 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void dismountEntity(Entity entityIn) {
/* 1907 */     if (!(entityIn instanceof net.minecraft.entity.item.EntityBoat) && !(entityIn instanceof net.minecraft.entity.passive.AbstractHorse)) {
/*      */       
/* 1909 */       double d1 = entityIn.posX;
/* 1910 */       double d13 = (entityIn.getEntityBoundingBox()).minY + entityIn.height;
/* 1911 */       double d14 = entityIn.posZ;
/* 1912 */       EnumFacing enumfacing1 = entityIn.getAdjustedHorizontalFacing();
/*      */       
/* 1914 */       if (enumfacing1 != null) {
/*      */         
/* 1916 */         EnumFacing enumfacing = enumfacing1.rotateY();
/* 1917 */         int[][] aint1 = { { 0, 1 }, { 0, -1 }, { -1, 1 }, { -1, -1 }, { 1, 1 }, { 1, -1 }, { -1 }, { 1 }, { 0, 1 } };
/* 1918 */         double d5 = Math.floor(this.posX) + 0.5D;
/* 1919 */         double d6 = Math.floor(this.posZ) + 0.5D;
/* 1920 */         double d7 = (getEntityBoundingBox()).maxX - (getEntityBoundingBox()).minX;
/* 1921 */         double d8 = (getEntityBoundingBox()).maxZ - (getEntityBoundingBox()).minZ;
/* 1922 */         AxisAlignedBB axisalignedbb = new AxisAlignedBB(d5 - d7 / 2.0D, (entityIn.getEntityBoundingBox()).minY, d6 - d8 / 2.0D, d5 + d7 / 2.0D, Math.floor((entityIn.getEntityBoundingBox()).minY) + this.height, d6 + d8 / 2.0D); byte b;
/*      */         int i, arrayOfInt1[][];
/* 1924 */         for (i = (arrayOfInt1 = aint1).length, b = 0; b < i; ) { int[] aint = arrayOfInt1[b];
/*      */           
/* 1926 */           double d9 = (enumfacing1.getFrontOffsetX() * aint[0] + enumfacing.getFrontOffsetX() * aint[1]);
/* 1927 */           double d10 = (enumfacing1.getFrontOffsetZ() * aint[0] + enumfacing.getFrontOffsetZ() * aint[1]);
/* 1928 */           double d11 = d5 + d9;
/* 1929 */           double d12 = d6 + d10;
/* 1930 */           AxisAlignedBB axisalignedbb1 = axisalignedbb.offset(d9, 0.0D, d10);
/*      */           
/* 1932 */           if (!this.world.collidesWithAnyBlock(axisalignedbb1)) {
/*      */             
/* 1934 */             if (this.world.getBlockState(new BlockPos(d11, this.posY, d12)).isFullyOpaque()) {
/*      */               
/* 1936 */               setPositionAndUpdate(d11, this.posY + 1.0D, d12);
/*      */               
/*      */               return;
/*      */             } 
/* 1940 */             BlockPos blockpos = new BlockPos(d11, this.posY - 1.0D, d12);
/*      */             
/* 1942 */             if (this.world.getBlockState(blockpos).isFullyOpaque() || this.world.getBlockState(blockpos).getMaterial() == Material.WATER)
/*      */             {
/* 1944 */               d1 = d11;
/* 1945 */               d13 = this.posY + 1.0D;
/* 1946 */               d14 = d12;
/*      */             }
/*      */           
/* 1949 */           } else if (!this.world.collidesWithAnyBlock(axisalignedbb1.offset(0.0D, 1.0D, 0.0D)) && this.world.getBlockState(new BlockPos(d11, this.posY + 1.0D, d12)).isFullyOpaque()) {
/*      */             
/* 1951 */             d1 = d11;
/* 1952 */             d13 = this.posY + 2.0D;
/* 1953 */             d14 = d12;
/*      */           } 
/*      */           b++; }
/*      */       
/*      */       } 
/* 1958 */       setPositionAndUpdate(d1, d13, d14);
/*      */     } else {
/*      */       float f;
/*      */       
/* 1962 */       double d0 = (this.width / 2.0F + entityIn.width / 2.0F) + 0.4D;
/*      */ 
/*      */       
/* 1965 */       if (entityIn instanceof net.minecraft.entity.item.EntityBoat) {
/*      */         
/* 1967 */         f = 0.0F;
/*      */       }
/*      */       else {
/*      */         
/* 1971 */         f = 1.5707964F * ((getPrimaryHand() == EnumHandSide.RIGHT) ? -1 : true);
/*      */       } 
/*      */       
/* 1974 */       float f1 = -MathHelper.sin(-this.rotationYaw * 0.017453292F - 3.1415927F + f);
/* 1975 */       float f2 = -MathHelper.cos(-this.rotationYaw * 0.017453292F - 3.1415927F + f);
/* 1976 */       double d2 = (Math.abs(f1) > Math.abs(f2)) ? (d0 / Math.abs(f1)) : (d0 / Math.abs(f2));
/* 1977 */       double d3 = this.posX + f1 * d2;
/* 1978 */       double d4 = this.posZ + f2 * d2;
/* 1979 */       setPosition(d3, entityIn.posY + entityIn.height + 0.001D, d4);
/*      */       
/* 1981 */       if (this.world.collidesWithAnyBlock(getEntityBoundingBox())) {
/*      */         
/* 1983 */         setPosition(d3, entityIn.posY + entityIn.height + 1.001D, d4);
/*      */         
/* 1985 */         if (this.world.collidesWithAnyBlock(getEntityBoundingBox()))
/*      */         {
/* 1987 */           setPosition(entityIn.posX, entityIn.posY + this.height + 0.001D, entityIn.posZ);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAlwaysRenderNameTagForRender() {
/* 1995 */     return getAlwaysRenderNameTag();
/*      */   }
/*      */ 
/*      */   
/*      */   protected float getJumpUpwardsMotion() {
/* 2000 */     return 0.42F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void jump() {
/* 2008 */     this.motionY = getJumpUpwardsMotion();
/*      */     
/* 2010 */     if (isPotionActive(MobEffects.JUMP_BOOST))
/*      */     {
/* 2012 */       this.motionY += ((getActivePotionEffect(MobEffects.JUMP_BOOST).getAmplifier() + 1) * 0.1F);
/*      */     }
/*      */     
/* 2015 */     if (isSprinting()) {
/*      */       
/* 2017 */       float f = this.rotationYaw * 0.017453292F;
/* 2018 */       this.motionX -= (MathHelper.sin(f) * 0.2F);
/* 2019 */       this.motionZ += (MathHelper.cos(f) * 0.2F);
/*      */     } 
/*      */     
/* 2022 */     this.isAirBorne = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void handleJumpWater() {
/* 2030 */     this.motionY += 0.03999999910593033D;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void handleJumpLava() {
/* 2035 */     this.motionY += 0.03999999910593033D;
/*      */   }
/*      */ 
/*      */   
/*      */   protected float getWaterSlowDown() {
/* 2040 */     return 0.8F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_191986_a(float p_191986_1_, float p_191986_2_, float p_191986_3_) {
/* 2045 */     if (isServerWorld() || canPassengerSteer())
/*      */     {
/* 2047 */       if (!isInWater() || (this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.isFlying)) {
/*      */         
/* 2049 */         if (!isInLava() || (this instanceof EntityPlayer && ((EntityPlayer)this).capabilities.isFlying)) {
/*      */           
/* 2051 */           if (isElytraFlying())
/*      */           {
/* 2053 */             if (this.motionY > -0.5D)
/*      */             {
/* 2055 */               this.fallDistance = 1.0F;
/*      */             }
/*      */             
/* 2058 */             Vec3d vec3d = getLookVec();
/* 2059 */             float f = this.rotationPitch * 0.017453292F;
/* 2060 */             double d6 = Math.sqrt(vec3d.xCoord * vec3d.xCoord + vec3d.zCoord * vec3d.zCoord);
/* 2061 */             double d8 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 2062 */             double d1 = vec3d.lengthVector();
/* 2063 */             float f4 = MathHelper.cos(f);
/* 2064 */             f4 = (float)(f4 * f4 * Math.min(1.0D, d1 / 0.4D));
/* 2065 */             this.motionY += -0.08D + f4 * 0.06D;
/*      */             
/* 2067 */             if (this.motionY < 0.0D && d6 > 0.0D) {
/*      */               
/* 2069 */               double d2 = this.motionY * -0.1D * f4;
/* 2070 */               this.motionY += d2;
/* 2071 */               this.motionX += vec3d.xCoord * d2 / d6;
/* 2072 */               this.motionZ += vec3d.zCoord * d2 / d6;
/*      */             } 
/*      */             
/* 2075 */             if (f < 0.0F) {
/*      */               
/* 2077 */               double d10 = d8 * -MathHelper.sin(f) * 0.04D;
/* 2078 */               this.motionY += d10 * 3.2D;
/* 2079 */               this.motionX -= vec3d.xCoord * d10 / d6;
/* 2080 */               this.motionZ -= vec3d.zCoord * d10 / d6;
/*      */             } 
/*      */             
/* 2083 */             if (d6 > 0.0D) {
/*      */               
/* 2085 */               this.motionX += (vec3d.xCoord / d6 * d8 - this.motionX) * 0.1D;
/* 2086 */               this.motionZ += (vec3d.zCoord / d6 * d8 - this.motionZ) * 0.1D;
/*      */             } 
/*      */             
/* 2089 */             this.motionX *= 0.9900000095367432D;
/* 2090 */             this.motionY *= 0.9800000190734863D;
/* 2091 */             this.motionZ *= 0.9900000095367432D;
/* 2092 */             moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/*      */             
/* 2094 */             if (this.isCollidedHorizontally && !this.world.isRemote) {
/*      */               
/* 2096 */               double d11 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 2097 */               double d3 = d8 - d11;
/* 2098 */               float f5 = (float)(d3 * 10.0D - 3.0D);
/*      */               
/* 2100 */               if (f5 > 0.0F) {
/*      */                 
/* 2102 */                 playSound(getFallSound((int)f5), 1.0F, 1.0F);
/* 2103 */                 attackEntityFrom(DamageSource.flyIntoWall, f5);
/*      */               } 
/*      */             } 
/*      */             
/* 2107 */             if (this.onGround && !this.world.isRemote)
/*      */             {
/* 2109 */               setFlag(7, false);
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/* 2114 */             float f8, f6 = 0.91F;
/* 2115 */             BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(this.posX, (getEntityBoundingBox()).minY - 1.0D, this.posZ);
/*      */             
/* 2117 */             if (this.onGround)
/*      */             {
/* 2119 */               f6 = (this.world.getBlockState((BlockPos)blockpos$pooledmutableblockpos).getBlock()).slipperiness * 0.91F;
/*      */             }
/*      */             
/* 2122 */             float f7 = 0.16277136F / f6 * f6 * f6;
/*      */ 
/*      */             
/* 2125 */             if (this.onGround) {
/*      */               
/* 2127 */               f8 = getAIMoveSpeed() * f7;
/*      */             }
/*      */             else {
/*      */               
/* 2131 */               f8 = this.jumpMovementFactor;
/*      */             } 
/*      */             
/* 2134 */             func_191958_b(p_191986_1_, p_191986_2_, p_191986_3_, f8);
/* 2135 */             f6 = 0.91F;
/*      */             
/* 2137 */             if (this.onGround)
/*      */             {
/* 2139 */               f6 = (this.world.getBlockState((BlockPos)blockpos$pooledmutableblockpos.setPos(this.posX, (getEntityBoundingBox()).minY - 1.0D, this.posZ)).getBlock()).slipperiness * 0.91F;
/*      */             }
/*      */             
/* 2142 */             if (isOnLadder()) {
/*      */               
/* 2144 */               float f9 = 0.15F;
/* 2145 */               this.motionX = MathHelper.clamp(this.motionX, -0.15000000596046448D, 0.15000000596046448D);
/* 2146 */               this.motionZ = MathHelper.clamp(this.motionZ, -0.15000000596046448D, 0.15000000596046448D);
/* 2147 */               this.fallDistance = 0.0F;
/*      */               
/* 2149 */               if (this.motionY < -0.15D)
/*      */               {
/* 2151 */                 this.motionY = -0.15D;
/*      */               }
/*      */               
/* 2154 */               boolean flag = (isSneaking() && this instanceof EntityPlayer);
/*      */               
/* 2156 */               if (flag && this.motionY < 0.0D)
/*      */               {
/* 2158 */                 this.motionY = 0.0D;
/*      */               }
/*      */             } 
/*      */             
/* 2162 */             moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/*      */             
/* 2164 */             if (this.isCollidedHorizontally && isOnLadder())
/*      */             {
/* 2166 */               this.motionY = 0.2D;
/*      */             }
/*      */             
/* 2169 */             if (isPotionActive(MobEffects.LEVITATION)) {
/*      */               
/* 2171 */               this.motionY += (0.05D * (getActivePotionEffect(MobEffects.LEVITATION).getAmplifier() + 1) - this.motionY) * 0.2D;
/*      */             }
/*      */             else {
/*      */               
/* 2175 */               blockpos$pooledmutableblockpos.setPos(this.posX, 0.0D, this.posZ);
/*      */               
/* 2177 */               if (!this.world.isRemote || (this.world.isBlockLoaded((BlockPos)blockpos$pooledmutableblockpos) && this.world.getChunkFromBlockCoords((BlockPos)blockpos$pooledmutableblockpos).isLoaded())) {
/*      */                 
/* 2179 */                 if (!hasNoGravity())
/*      */                 {
/* 2181 */                   this.motionY -= 0.08D;
/*      */                 }
/*      */               }
/* 2184 */               else if (this.posY > 0.0D) {
/*      */                 
/* 2186 */                 this.motionY = -0.1D;
/*      */               }
/*      */               else {
/*      */                 
/* 2190 */                 this.motionY = 0.0D;
/*      */               } 
/*      */             } 
/*      */             
/* 2194 */             this.motionY *= 0.9800000190734863D;
/* 2195 */             this.motionX *= f6;
/* 2196 */             this.motionZ *= f6;
/* 2197 */             blockpos$pooledmutableblockpos.release();
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 2202 */           double d4 = this.posY;
/* 2203 */           func_191958_b(p_191986_1_, p_191986_2_, p_191986_3_, 0.02F);
/* 2204 */           moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/* 2205 */           this.motionX *= 0.5D;
/* 2206 */           this.motionY *= 0.5D;
/* 2207 */           this.motionZ *= 0.5D;
/*      */           
/* 2209 */           if (!hasNoGravity())
/*      */           {
/* 2211 */             this.motionY -= 0.02D;
/*      */           }
/*      */           
/* 2214 */           if (this.isCollidedHorizontally && isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d4, this.motionZ))
/*      */           {
/* 2216 */             this.motionY = 0.30000001192092896D;
/*      */           }
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 2222 */         double d0 = this.posY;
/* 2223 */         float f1 = getWaterSlowDown();
/* 2224 */         float f2 = 0.02F;
/* 2225 */         float f3 = EnchantmentHelper.getDepthStriderModifier(this);
/*      */         
/* 2227 */         if (f3 > 3.0F)
/*      */         {
/* 2229 */           f3 = 3.0F;
/*      */         }
/*      */         
/* 2232 */         if (!this.onGround)
/*      */         {
/* 2234 */           f3 *= 0.5F;
/*      */         }
/*      */         
/* 2237 */         if (f3 > 0.0F) {
/*      */           
/* 2239 */           f1 += (0.54600006F - f1) * f3 / 3.0F;
/* 2240 */           f2 += (getAIMoveSpeed() - f2) * f3 / 3.0F;
/*      */         } 
/*      */         
/* 2243 */         func_191958_b(p_191986_1_, p_191986_2_, p_191986_3_, f2);
/* 2244 */         moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/* 2245 */         this.motionX *= f1;
/* 2246 */         this.motionY *= 0.800000011920929D;
/* 2247 */         this.motionZ *= f1;
/*      */         
/* 2249 */         if (!hasNoGravity())
/*      */         {
/* 2251 */           this.motionY -= 0.02D;
/*      */         }
/*      */         
/* 2254 */         if (this.isCollidedHorizontally && isOffsetPositionInLiquid(this.motionX, this.motionY + 0.6000000238418579D - this.posY + d0, this.motionZ))
/*      */         {
/* 2256 */           this.motionY = 0.30000001192092896D;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 2261 */     this.prevLimbSwingAmount = this.limbSwingAmount;
/* 2262 */     double d5 = this.posX - this.prevPosX;
/* 2263 */     double d7 = this.posZ - this.prevPosZ;
/* 2264 */     double d9 = (this instanceof net.minecraft.entity.passive.EntityFlying) ? (this.posY - this.prevPosY) : 0.0D;
/* 2265 */     float f10 = MathHelper.sqrt(d5 * d5 + d9 * d9 + d7 * d7) * 4.0F;
/*      */     
/* 2267 */     if (f10 > 1.0F)
/*      */     {
/* 2269 */       f10 = 1.0F;
/*      */     }
/*      */     
/* 2272 */     this.limbSwingAmount += (f10 - this.limbSwingAmount) * 0.4F;
/* 2273 */     this.limbSwing += this.limbSwingAmount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAIMoveSpeed() {
/* 2281 */     return this.landMovementFactor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAIMoveSpeed(float speedIn) {
/* 2289 */     this.landMovementFactor = speedIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean attackEntityAsMob(Entity entityIn) {
/* 2294 */     setLastAttacker(entityIn);
/* 2295 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isPlayerSleeping() {
/* 2303 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/* 2311 */     super.onUpdate();
/* 2312 */     updateActiveHand();
/*      */     
/* 2314 */     if (!this.world.isRemote) {
/*      */       
/* 2316 */       int i = getArrowCountInEntity();
/*      */       
/* 2318 */       if (i > 0) {
/*      */         
/* 2320 */         if (this.arrowHitTimer <= 0)
/*      */         {
/* 2322 */           this.arrowHitTimer = 20 * (30 - i);
/*      */         }
/*      */         
/* 2325 */         this.arrowHitTimer--;
/*      */         
/* 2327 */         if (this.arrowHitTimer <= 0)
/*      */         {
/* 2329 */           setArrowCountInEntity(i - 1); } 
/*      */       }  byte b;
/*      */       int j;
/*      */       EntityEquipmentSlot[] arrayOfEntityEquipmentSlot;
/* 2333 */       for (j = (arrayOfEntityEquipmentSlot = EntityEquipmentSlot.values()).length, b = 0; b < j; ) { ItemStack itemstack; EntityEquipmentSlot entityequipmentslot = arrayOfEntityEquipmentSlot[b];
/*      */ 
/*      */ 
/*      */         
/* 2337 */         switch (entityequipmentslot.getSlotType()) {
/*      */           
/*      */           case HAND:
/* 2340 */             itemstack = (ItemStack)this.handInventory.get(entityequipmentslot.getIndex());
/*      */             break;
/*      */           
/*      */           case null:
/* 2344 */             itemstack = (ItemStack)this.armorArray.get(entityequipmentslot.getIndex());
/*      */             break;
/*      */           
/*      */           default:
/*      */             b++;
/*      */             continue;
/*      */         } 
/* 2351 */         ItemStack itemstack1 = getItemStackFromSlot(entityequipmentslot);
/*      */         
/* 2353 */         if (!ItemStack.areItemStacksEqual(itemstack1, itemstack)) {
/*      */           
/* 2355 */           ((WorldServer)this.world).getEntityTracker().sendToAllTrackingEntity(this, (Packet<?>)new SPacketEntityEquipment(getEntityId(), entityequipmentslot, itemstack1));
/*      */           
/* 2357 */           if (!itemstack.func_190926_b())
/*      */           {
/* 2359 */             getAttributeMap().removeAttributeModifiers(itemstack.getAttributeModifiers(entityequipmentslot));
/*      */           }
/*      */           
/* 2362 */           if (!itemstack1.func_190926_b())
/*      */           {
/* 2364 */             getAttributeMap().applyAttributeModifiers(itemstack1.getAttributeModifiers(entityequipmentslot));
/*      */           }
/*      */           
/* 2367 */           switch (entityequipmentslot.getSlotType()) {
/*      */             
/*      */             case HAND:
/* 2370 */               this.handInventory.set(entityequipmentslot.getIndex(), itemstack1.func_190926_b() ? ItemStack.field_190927_a : itemstack1.copy());
/*      */ 
/*      */             
/*      */             case null:
/* 2374 */               this.armorArray.set(entityequipmentslot.getIndex(), itemstack1.func_190926_b() ? ItemStack.field_190927_a : itemstack1.copy());
/*      */           } 
/*      */         
/*      */         }  }
/*      */       
/* 2379 */       if (this.ticksExisted % 20 == 0)
/*      */       {
/* 2381 */         getCombatTracker().reset();
/*      */       }
/* 2383 */       if (!this.glowing) {
/*      */         
/* 2385 */         boolean flag = isPotionActive(MobEffects.GLOWING);
/*      */         
/* 2387 */         if (getFlag(6) != flag)
/*      */         {
/* 2389 */           setFlag(6, flag);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2394 */     onLivingUpdate();
/* 2395 */     double d0 = this.posX - this.prevPosX;
/* 2396 */     double d1 = this.posZ - this.prevPosZ;
/* 2397 */     float f3 = (float)(d0 * d0 + d1 * d1);
/* 2398 */     float f4 = this.renderYawOffset;
/* 2399 */     float f5 = 0.0F;
/* 2400 */     this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
/* 2401 */     float f = 0.0F;
/*      */     
/* 2403 */     if (f3 > 0.0025000002F) {
/*      */       
/* 2405 */       f = 1.0F;
/* 2406 */       f5 = (float)Math.sqrt(f3) * 3.0F;
/* 2407 */       float f1 = (float)MathHelper.atan2(d1, d0) * 57.295776F - 90.0F;
/* 2408 */       float f2 = MathHelper.abs(MathHelper.wrapDegrees(this.rotationYaw) - f1);
/*      */       
/* 2410 */       if (95.0F < f2 && f2 < 265.0F) {
/*      */         
/* 2412 */         float f6 = f1 - 180.0F;
/*      */       }
/*      */       else {
/*      */         
/* 2416 */         f4 = f1;
/*      */       } 
/*      */     } 
/*      */     
/* 2420 */     if (this.swingProgress > 0.0F)
/*      */     {
/* 2422 */       f4 = this.rotationYaw;
/*      */     }
/*      */     
/* 2425 */     if (!this.onGround)
/*      */     {
/* 2427 */       f = 0.0F;
/*      */     }
/*      */     
/* 2430 */     this.onGroundSpeedFactor += (f - this.onGroundSpeedFactor) * 0.3F;
/* 2431 */     this.world.theProfiler.startSection("headTurn");
/* 2432 */     f5 = updateDistance(f4, f5);
/* 2433 */     this.world.theProfiler.endSection();
/* 2434 */     this.world.theProfiler.startSection("rangeChecks");
/*      */     
/* 2436 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*      */     {
/* 2438 */       this.prevRotationYaw -= 360.0F;
/*      */     }
/*      */     
/* 2441 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*      */     {
/* 2443 */       this.prevRotationYaw += 360.0F;
/*      */     }
/*      */     
/* 2446 */     while (this.renderYawOffset - this.prevRenderYawOffset < -180.0F)
/*      */     {
/* 2448 */       this.prevRenderYawOffset -= 360.0F;
/*      */     }
/*      */     
/* 2451 */     while (this.renderYawOffset - this.prevRenderYawOffset >= 180.0F)
/*      */     {
/* 2453 */       this.prevRenderYawOffset += 360.0F;
/*      */     }
/*      */     
/* 2456 */     while (this.rotationPitch - this.prevRotationPitch < -180.0F)
/*      */     {
/* 2458 */       this.prevRotationPitch -= 360.0F;
/*      */     }
/*      */     
/* 2461 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*      */     {
/* 2463 */       this.prevRotationPitch += 360.0F;
/*      */     }
/*      */     
/* 2466 */     while (this.rotationYawHead - this.prevRotationYawHead < -180.0F)
/*      */     {
/* 2468 */       this.prevRotationYawHead -= 360.0F;
/*      */     }
/*      */     
/* 2471 */     while (this.rotationYawHead - this.prevRotationYawHead >= 180.0F)
/*      */     {
/* 2473 */       this.prevRotationYawHead += 360.0F;
/*      */     }
/*      */     
/* 2476 */     this.world.theProfiler.endSection();
/* 2477 */     this.movedDistance += f5;
/*      */     
/* 2479 */     if (isElytraFlying()) {
/*      */       
/* 2481 */       this.ticksElytraFlying++;
/*      */     }
/*      */     else {
/*      */       
/* 2485 */       this.ticksElytraFlying = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected float updateDistance(float p_110146_1_, float p_110146_2_) {
/* 2491 */     float f = MathHelper.wrapDegrees(p_110146_1_ - this.renderYawOffset);
/* 2492 */     this.renderYawOffset += f * 0.3F;
/* 2493 */     float f1 = MathHelper.wrapDegrees(this.rotationYaw - this.renderYawOffset);
/* 2494 */     boolean flag = !(f1 >= -90.0F && f1 < 90.0F);
/*      */     
/* 2496 */     if (f1 < -75.0F)
/*      */     {
/* 2498 */       f1 = -75.0F;
/*      */     }
/*      */     
/* 2501 */     if (f1 >= 75.0F)
/*      */     {
/* 2503 */       f1 = 75.0F;
/*      */     }
/*      */     
/* 2506 */     this.renderYawOffset = this.rotationYaw - f1;
/*      */     
/* 2508 */     if (f1 * f1 > 2500.0F)
/*      */     {
/* 2510 */       this.renderYawOffset += f1 * 0.2F;
/*      */     }
/*      */     
/* 2513 */     if (flag)
/*      */     {
/* 2515 */       p_110146_2_ *= -1.0F;
/*      */     }
/*      */     
/* 2518 */     return p_110146_2_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onLivingUpdate() {
/* 2527 */     if (this.jumpTicks > 0)
/*      */     {
/* 2529 */       this.jumpTicks--;
/*      */     }
/*      */     
/* 2532 */     if (this.newPosRotationIncrements > 0 && !canPassengerSteer()) {
/*      */       
/* 2534 */       double d0 = this.posX + (this.interpTargetX - this.posX) / this.newPosRotationIncrements;
/* 2535 */       double d1 = this.posY + (this.interpTargetY - this.posY) / this.newPosRotationIncrements;
/* 2536 */       double d2 = this.posZ + (this.interpTargetZ - this.posZ) / this.newPosRotationIncrements;
/* 2537 */       double d3 = MathHelper.wrapDegrees(this.interpTargetYaw - this.rotationYaw);
/* 2538 */       this.rotationYaw = (float)(this.rotationYaw + d3 / this.newPosRotationIncrements);
/* 2539 */       this.rotationPitch = (float)(this.rotationPitch + (this.interpTargetPitch - this.rotationPitch) / this.newPosRotationIncrements);
/* 2540 */       this.newPosRotationIncrements--;
/* 2541 */       setPosition(d0, d1, d2);
/* 2542 */       setRotation(this.rotationYaw, this.rotationPitch);
/*      */     }
/* 2544 */     else if (!isServerWorld()) {
/*      */       
/* 2546 */       this.motionX *= 0.98D;
/* 2547 */       this.motionY *= 0.98D;
/* 2548 */       this.motionZ *= 0.98D;
/*      */     } 
/*      */     
/* 2551 */     if (Math.abs(this.motionX) < 0.003D)
/*      */     {
/* 2553 */       this.motionX = 0.0D;
/*      */     }
/*      */     
/* 2556 */     if (Math.abs(this.motionY) < 0.003D)
/*      */     {
/* 2558 */       this.motionY = 0.0D;
/*      */     }
/*      */     
/* 2561 */     if (Math.abs(this.motionZ) < 0.003D)
/*      */     {
/* 2563 */       this.motionZ = 0.0D;
/*      */     }
/*      */     
/* 2566 */     this.world.theProfiler.startSection("ai");
/*      */     
/* 2568 */     if (isMovementBlocked()) {
/*      */       
/* 2570 */       this.isJumping = false;
/* 2571 */       this.moveStrafing = 0.0F;
/* 2572 */       this.field_191988_bg = 0.0F;
/* 2573 */       this.randomYawVelocity = 0.0F;
/*      */     }
/* 2575 */     else if (isServerWorld()) {
/*      */       
/* 2577 */       this.world.theProfiler.startSection("newAi");
/* 2578 */       updateEntityActionState();
/* 2579 */       this.world.theProfiler.endSection();
/*      */     } 
/*      */     
/* 2582 */     this.world.theProfiler.endSection();
/* 2583 */     this.world.theProfiler.startSection("jump");
/*      */     
/* 2585 */     if (this.isJumping) {
/*      */       
/* 2587 */       if (isInWater())
/*      */       {
/* 2589 */         handleJumpWater();
/*      */       }
/* 2591 */       else if (isInLava())
/*      */       {
/* 2593 */         handleJumpLava();
/*      */       }
/* 2595 */       else if (this.onGround && this.jumpTicks == 0)
/*      */       {
/* 2597 */         jump();
/* 2598 */         this.jumpTicks = 10;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 2603 */       this.jumpTicks = 0;
/*      */     } 
/*      */     
/* 2606 */     this.world.theProfiler.endSection();
/* 2607 */     this.world.theProfiler.startSection("travel");
/* 2608 */     this.moveStrafing *= 0.98F;
/* 2609 */     this.field_191988_bg *= 0.98F;
/* 2610 */     this.randomYawVelocity *= 0.9F;
/* 2611 */     updateElytra();
/* 2612 */     func_191986_a(this.moveStrafing, this.moveForward, this.field_191988_bg);
/* 2613 */     this.world.theProfiler.endSection();
/* 2614 */     this.world.theProfiler.startSection("push");
/* 2615 */     collideWithNearbyEntities();
/* 2616 */     this.world.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateElytra() {
/* 2624 */     boolean flag = getFlag(7);
/*      */     
/* 2626 */     if (flag && !this.onGround && !isRiding()) {
/*      */       
/* 2628 */       ItemStack itemstack = getItemStackFromSlot(EntityEquipmentSlot.CHEST);
/*      */       
/* 2630 */       if (itemstack.getItem() == Items.ELYTRA && ItemElytra.isBroken(itemstack))
/*      */       {
/* 2632 */         flag = true;
/*      */         
/* 2634 */         if (!this.world.isRemote && (this.ticksElytraFlying + 1) % 20 == 0)
/*      */         {
/* 2636 */           itemstack.damageItem(1, this);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 2641 */         flag = false;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/* 2646 */       flag = false;
/*      */     } 
/*      */     
/* 2649 */     if (!this.world.isRemote)
/*      */     {
/* 2651 */       setFlag(7, flag);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateEntityActionState() {}
/*      */ 
/*      */   
/*      */   protected void collideWithNearbyEntities() {
/* 2661 */     List<Entity> list = this.world.getEntitiesInAABBexcluding(this, getEntityBoundingBox(), EntitySelectors.getTeamCollisionPredicate(this));
/*      */     
/* 2663 */     if (!list.isEmpty()) {
/*      */       
/* 2665 */       int i = this.world.getGameRules().getInt("maxEntityCramming");
/*      */       
/* 2667 */       if (i > 0 && list.size() > i - 1 && this.rand.nextInt(4) == 0) {
/*      */         
/* 2669 */         int j = 0;
/*      */         
/* 2671 */         for (int k = 0; k < list.size(); k++) {
/*      */           
/* 2673 */           if (!((Entity)list.get(k)).isRiding())
/*      */           {
/* 2675 */             j++;
/*      */           }
/*      */         } 
/*      */         
/* 2679 */         if (j > i - 1)
/*      */         {
/* 2681 */           attackEntityFrom(DamageSource.field_191291_g, 6.0F);
/*      */         }
/*      */       } 
/*      */       
/* 2685 */       for (int l = 0; l < list.size(); l++) {
/*      */         
/* 2687 */         Entity entity = list.get(l);
/* 2688 */         collideWithEntity(entity);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void collideWithEntity(Entity entityIn) {
/* 2695 */     entityIn.applyEntityCollision(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void dismountRidingEntity() {
/* 2700 */     Entity entity = getRidingEntity();
/* 2701 */     super.dismountRidingEntity();
/*      */     
/* 2703 */     if (entity != null && entity != getRidingEntity() && !this.world.isRemote)
/*      */     {
/* 2705 */       dismountEntity(entity);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRidden() {
/* 2714 */     super.updateRidden();
/* 2715 */     this.prevOnGroundSpeedFactor = this.onGroundSpeedFactor;
/* 2716 */     this.onGroundSpeedFactor = 0.0F;
/* 2717 */     this.fallDistance = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/* 2725 */     this.interpTargetX = x;
/* 2726 */     this.interpTargetY = y;
/* 2727 */     this.interpTargetZ = z;
/* 2728 */     this.interpTargetYaw = yaw;
/* 2729 */     this.interpTargetPitch = pitch;
/* 2730 */     this.newPosRotationIncrements = posRotationIncrements;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setJumping(boolean jumping) {
/* 2735 */     this.isJumping = jumping;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onItemPickup(Entity entityIn, int quantity) {
/* 2743 */     if (!entityIn.isDead && !this.world.isRemote) {
/*      */       
/* 2745 */       EntityTracker entitytracker = ((WorldServer)this.world).getEntityTracker();
/*      */       
/* 2747 */       if (entityIn instanceof net.minecraft.entity.item.EntityItem || entityIn instanceof net.minecraft.entity.projectile.EntityArrow || entityIn instanceof EntityXPOrb)
/*      */       {
/* 2749 */         entitytracker.sendToAllTrackingEntity(entityIn, (Packet<?>)new SPacketCollectItem(entityIn.getEntityId(), getEntityId(), quantity));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canEntityBeSeen(Entity entityIn) {
/* 2759 */     return (this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY + getEyeHeight(), this.posZ), new Vec3d(entityIn.posX, entityIn.posY + entityIn.getEyeHeight(), entityIn.posZ), false, true, false) == null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3d getLook(float partialTicks) {
/* 2767 */     if (partialTicks == 1.0F)
/*      */     {
/* 2769 */       return getVectorForRotation(this.rotationPitch, this.rotationYawHead);
/*      */     }
/*      */ 
/*      */     
/* 2773 */     float f = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * partialTicks;
/* 2774 */     float f1 = this.prevRotationYawHead + (this.rotationYawHead - this.prevRotationYawHead) * partialTicks;
/* 2775 */     return getVectorForRotation(f, f1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getSwingProgress(float partialTickTime) {
/* 2784 */     float f = this.swingProgress - this.prevSwingProgress;
/*      */     
/* 2786 */     if (f < 0.0F)
/*      */     {
/* 2788 */       f++;
/*      */     }
/*      */     
/* 2791 */     return this.prevSwingProgress + f * partialTickTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServerWorld() {
/* 2799 */     return !this.world.isRemote;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedWith() {
/* 2807 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/* 2815 */     return (isEntityAlive() && !isOnLadder());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setBeenAttacked() {
/* 2823 */     this.velocityChanged = (this.rand.nextDouble() >= getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue());
/*      */   }
/*      */ 
/*      */   
/*      */   public float getRotationYawHead() {
/* 2828 */     return this.rotationYawHead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRotationYawHead(float rotation) {
/* 2836 */     this.rotationYawHead = rotation;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderYawOffset(float offset) {
/* 2844 */     this.renderYawOffset = offset;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getAbsorptionAmount() {
/* 2852 */     return this.absorptionAmount;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAbsorptionAmount(float amount) {
/* 2857 */     if (amount < 0.0F)
/*      */     {
/* 2859 */       amount = 0.0F;
/*      */     }
/*      */     
/* 2862 */     this.absorptionAmount = amount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEnterCombat() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendEndCombat() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void markPotionsDirty() {
/* 2881 */     this.potionsNeedUpdate = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isHandActive() {
/* 2888 */     return ((((Byte)this.dataManager.get(HAND_STATES)).byteValue() & 0x1) > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumHand getActiveHand() {
/* 2893 */     return ((((Byte)this.dataManager.get(HAND_STATES)).byteValue() & 0x2) > 0) ? EnumHand.OFF_HAND : EnumHand.MAIN_HAND;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateActiveHand() {
/* 2898 */     if (isHandActive()) {
/*      */       
/* 2900 */       ItemStack itemstack = getHeldItem(getActiveHand());
/*      */       
/* 2902 */       if (itemstack == this.activeItemStack) {
/*      */         
/* 2904 */         if (getItemInUseCount() <= 25 && getItemInUseCount() % 4 == 0)
/*      */         {
/* 2906 */           updateItemUse(this.activeItemStack, 5);
/*      */         }
/*      */         
/* 2909 */         if (--this.activeItemStackUseCount == 0 && !this.world.isRemote)
/*      */         {
/* 2911 */           onItemUseFinish();
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 2916 */         resetActiveHand();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setActiveHand(EnumHand hand) {
/* 2923 */     ItemStack itemstack = getHeldItem(hand);
/*      */     
/* 2925 */     if (!itemstack.func_190926_b() && !isHandActive()) {
/*      */       
/* 2927 */       this.activeItemStack = itemstack;
/* 2928 */       this.activeItemStackUseCount = itemstack.getMaxItemUseDuration();
/*      */       
/* 2930 */       if (!this.world.isRemote) {
/*      */         
/* 2932 */         int i = 1;
/*      */         
/* 2934 */         if (hand == EnumHand.OFF_HAND)
/*      */         {
/* 2936 */           i |= 0x2;
/*      */         }
/*      */         
/* 2939 */         this.dataManager.set(HAND_STATES, Byte.valueOf((byte)i));
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 2946 */     super.notifyDataManagerChange(key);
/*      */     
/* 2948 */     if (HAND_STATES.equals(key) && this.world.isRemote)
/*      */     {
/* 2950 */       if (isHandActive() && this.activeItemStack.func_190926_b()) {
/*      */         
/* 2952 */         this.activeItemStack = getHeldItem(getActiveHand());
/*      */         
/* 2954 */         if (!this.activeItemStack.func_190926_b())
/*      */         {
/* 2956 */           this.activeItemStackUseCount = this.activeItemStack.getMaxItemUseDuration();
/*      */         }
/*      */       }
/* 2959 */       else if (!isHandActive() && !this.activeItemStack.func_190926_b()) {
/*      */         
/* 2961 */         this.activeItemStack = ItemStack.field_190927_a;
/* 2962 */         this.activeItemStackUseCount = 0;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateItemUse(ItemStack stack, int eatingParticleCount) {
/* 2972 */     if (!stack.func_190926_b() && isHandActive()) {
/*      */       
/* 2974 */       if (stack.getItemUseAction() == EnumAction.DRINK)
/*      */       {
/* 2976 */         playSound(SoundEvents.ENTITY_GENERIC_DRINK, 0.5F, this.world.rand.nextFloat() * 0.1F + 0.9F);
/*      */       }
/*      */       
/* 2979 */       if (stack.getItemUseAction() == EnumAction.EAT) {
/*      */         
/* 2981 */         for (int i = 0; i < eatingParticleCount; i++) {
/*      */           
/* 2983 */           Vec3d vec3d = new Vec3d((this.rand.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
/* 2984 */           vec3d = vec3d.rotatePitch(-this.rotationPitch * 0.017453292F);
/* 2985 */           vec3d = vec3d.rotateYaw(-this.rotationYaw * 0.017453292F);
/* 2986 */           double d0 = -this.rand.nextFloat() * 0.6D - 0.3D;
/* 2987 */           Vec3d vec3d1 = new Vec3d((this.rand.nextFloat() - 0.5D) * 0.3D, d0, 0.6D);
/* 2988 */           vec3d1 = vec3d1.rotatePitch(-this.rotationPitch * 0.017453292F);
/* 2989 */           vec3d1 = vec3d1.rotateYaw(-this.rotationYaw * 0.017453292F);
/* 2990 */           vec3d1 = vec3d1.addVector(this.posX, this.posY + getEyeHeight(), this.posZ);
/*      */           
/* 2992 */           if (stack.getHasSubtypes()) {
/*      */             
/* 2994 */             this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, vec3d.xCoord, vec3d.yCoord + 0.05D, vec3d.zCoord, new int[] { Item.getIdFromItem(stack.getItem()), stack.getMetadata() });
/*      */           }
/*      */           else {
/*      */             
/* 2998 */             this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, vec3d1.xCoord, vec3d1.yCoord, vec3d1.zCoord, vec3d.xCoord, vec3d.yCoord + 0.05D, vec3d.zCoord, new int[] { Item.getIdFromItem(stack.getItem()) });
/*      */           } 
/*      */         } 
/*      */         
/* 3002 */         playSound(SoundEvents.ENTITY_GENERIC_EAT, 0.5F + 0.5F * this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onItemUseFinish() {
/* 3012 */     if (!this.activeItemStack.func_190926_b() && isHandActive()) {
/*      */       
/* 3014 */       updateItemUse(this.activeItemStack, 16);
/* 3015 */       setHeldItem(getActiveHand(), this.activeItemStack.onItemUseFinish(this.world, this));
/* 3016 */       resetActiveHand();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemStack getActiveItemStack() {
/* 3022 */     return this.activeItemStack;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getItemInUseCount() {
/* 3027 */     return this.activeItemStackUseCount;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getItemInUseMaxCount() {
/* 3032 */     return isHandActive() ? (this.activeItemStack.getMaxItemUseDuration() - getItemInUseCount()) : 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void stopActiveHand() {
/* 3037 */     if (!this.activeItemStack.func_190926_b())
/*      */     {
/* 3039 */       this.activeItemStack.onPlayerStoppedUsing(this.world, this, getItemInUseCount());
/*      */     }
/*      */     
/* 3042 */     resetActiveHand();
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetActiveHand() {
/* 3047 */     if (!this.world.isRemote)
/*      */     {
/* 3049 */       this.dataManager.set(HAND_STATES, Byte.valueOf((byte)0));
/*      */     }
/*      */     
/* 3052 */     this.activeItemStack = ItemStack.field_190927_a;
/* 3053 */     this.activeItemStackUseCount = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isActiveItemStackBlocking() {
/* 3058 */     if (isHandActive() && !this.activeItemStack.func_190926_b()) {
/*      */       
/* 3060 */       Item item = this.activeItemStack.getItem();
/*      */       
/* 3062 */       if (item.getItemUseAction(this.activeItemStack) != EnumAction.BLOCK)
/*      */       {
/* 3064 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 3068 */       return (item.getMaxItemUseDuration(this.activeItemStack) - this.activeItemStackUseCount >= 5);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3073 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isElytraFlying() {
/* 3079 */     return getFlag(7);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getTicksElytraFlying() {
/* 3084 */     return this.ticksElytraFlying;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attemptTeleport(double x, double y, double z) {
/* 3092 */     double d0 = this.posX;
/* 3093 */     double d1 = this.posY;
/* 3094 */     double d2 = this.posZ;
/* 3095 */     this.posX = x;
/* 3096 */     this.posY = y;
/* 3097 */     this.posZ = z;
/* 3098 */     boolean flag = false;
/* 3099 */     BlockPos blockpos = new BlockPos(this);
/* 3100 */     World world = this.world;
/* 3101 */     Random random = getRNG();
/*      */     
/* 3103 */     if (world.isBlockLoaded(blockpos)) {
/*      */       
/* 3105 */       boolean flag1 = false;
/*      */       
/* 3107 */       while (!flag1 && blockpos.getY() > 0) {
/*      */         
/* 3109 */         BlockPos blockpos1 = blockpos.down();
/* 3110 */         IBlockState iblockstate = world.getBlockState(blockpos1);
/*      */         
/* 3112 */         if (iblockstate.getMaterial().blocksMovement()) {
/*      */           
/* 3114 */           flag1 = true;
/*      */           
/*      */           continue;
/*      */         } 
/* 3118 */         this.posY--;
/* 3119 */         blockpos = blockpos1;
/*      */       } 
/*      */ 
/*      */       
/* 3123 */       if (flag1) {
/*      */         
/* 3125 */         setPositionAndUpdate(this.posX, this.posY, this.posZ);
/*      */         
/* 3127 */         if (world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty() && !world.containsAnyLiquid(getEntityBoundingBox()))
/*      */         {
/* 3129 */           flag = true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3134 */     if (!flag) {
/*      */       
/* 3136 */       setPositionAndUpdate(d0, d1, d2);
/* 3137 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 3141 */     int i = 128;
/*      */     
/* 3143 */     for (int j = 0; j < 128; j++) {
/*      */       
/* 3145 */       double d6 = j / 127.0D;
/* 3146 */       float f = (random.nextFloat() - 0.5F) * 0.2F;
/* 3147 */       float f1 = (random.nextFloat() - 0.5F) * 0.2F;
/* 3148 */       float f2 = (random.nextFloat() - 0.5F) * 0.2F;
/* 3149 */       double d3 = d0 + (this.posX - d0) * d6 + (random.nextDouble() - 0.5D) * this.width * 2.0D;
/* 3150 */       double d4 = d1 + (this.posY - d1) * d6 + random.nextDouble() * this.height;
/* 3151 */       double d5 = d2 + (this.posZ - d2) * d6 + (random.nextDouble() - 0.5D) * this.width * 2.0D;
/* 3152 */       world.spawnParticle(EnumParticleTypes.PORTAL, d3, d4, d5, f, f1, f2, new int[0]);
/*      */     } 
/*      */     
/* 3155 */     if (this instanceof EntityCreature)
/*      */     {
/* 3157 */       ((EntityCreature)this).getNavigator().clearPathEntity();
/*      */     }
/*      */     
/* 3160 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeHitWithPotion() {
/* 3169 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_190631_cK() {
/* 3174 */     return true;
/*      */   }
/*      */   
/*      */   public void func_191987_a(BlockPos p_191987_1_, boolean p_191987_2_) {}
/*      */   
/*      */   public abstract Iterable<ItemStack> getArmorInventoryList();
/*      */   
/*      */   public abstract ItemStack getItemStackFromSlot(EntityEquipmentSlot paramEntityEquipmentSlot);
/*      */   
/*      */   public abstract void setItemStackToSlot(EntityEquipmentSlot paramEntityEquipmentSlot, ItemStack paramItemStack);
/*      */   
/*      */   public abstract EnumHandSide getPrimaryHand();
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\EntityLivingBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */