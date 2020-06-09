/*      */ package net.minecraft.entity;
/*      */ 
/*      */ import com.google.common.collect.Iterables;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Sets;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockLiquid;
/*      */ import net.minecraft.block.SoundType;
/*      */ import net.minecraft.block.material.EnumPushReaction;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.block.state.pattern.BlockPattern;
/*      */ import net.minecraft.command.CommandResultStats;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.crash.ICrashReportDetail;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.enchantment.EnchantmentProtection;
/*      */ import net.minecraft.entity.effect.EntityLightningBolt;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.SoundEvents;
/*      */ import net.minecraft.inventory.EntityEquipmentSlot;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagDouble;
/*      */ import net.minecraft.nbt.NBTTagFloat;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.nbt.NBTTagString;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.datasync.DataSerializers;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EnumActionResult;
/*      */ import net.minecraft.util.EnumBlockRenderType;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.Mirror;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.Rotation;
/*      */ import net.minecraft.util.SoundCategory;
/*      */ import net.minecraft.util.SoundEvent;
/*      */ import net.minecraft.util.datafix.DataFixer;
/*      */ import net.minecraft.util.datafix.FixTypes;
/*      */ import net.minecraft.util.datafix.IDataFixer;
/*      */ import net.minecraft.util.datafix.IDataWalker;
/*      */ import net.minecraft.util.datafix.IFixType;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.RayTraceResult;
/*      */ import net.minecraft.util.math.Vec2f;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.util.math.Vec3i;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.TextComponentString;
/*      */ import net.minecraft.util.text.event.HoverEvent;
/*      */ import net.minecraft.util.text.translation.I18n;
/*      */ import net.minecraft.world.Explosion;
/*      */ import net.minecraft.world.Teleporter;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public abstract class Entity
/*      */   implements ICommandSender
/*      */ {
/*   88 */   private static final Logger LOGGER = LogManager.getLogger();
/*   89 */   private static final List<ItemStack> field_190535_b = Collections.emptyList();
/*   90 */   private static final AxisAlignedBB ZERO_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0D, 0.0D, 0.0D);
/*   91 */   private static double renderDistanceWeight = 1.0D;
/*      */ 
/*      */   
/*      */   private static int nextEntityID;
/*      */ 
/*      */   
/*      */   private int entityId;
/*      */ 
/*      */   
/*      */   public boolean preventEntitySpawning;
/*      */ 
/*      */   
/*      */   private final List<Entity> riddenByEntities;
/*      */ 
/*      */   
/*      */   protected int rideCooldown;
/*      */ 
/*      */   
/*      */   private Entity ridingEntity;
/*      */ 
/*      */   
/*      */   public boolean forceSpawn;
/*      */ 
/*      */   
/*      */   public World world;
/*      */ 
/*      */   
/*      */   public double prevPosX;
/*      */ 
/*      */   
/*      */   public double prevPosY;
/*      */ 
/*      */   
/*      */   public double prevPosZ;
/*      */ 
/*      */   
/*      */   public double posX;
/*      */ 
/*      */   
/*      */   public double posY;
/*      */ 
/*      */   
/*      */   public double posZ;
/*      */ 
/*      */   
/*      */   public double motionX;
/*      */ 
/*      */   
/*      */   public double motionY;
/*      */ 
/*      */   
/*      */   public double motionZ;
/*      */ 
/*      */   
/*      */   public float rotationYaw;
/*      */ 
/*      */   
/*      */   public float rotationPitch;
/*      */ 
/*      */   
/*      */   public float prevRotationYaw;
/*      */ 
/*      */   
/*      */   public float prevRotationPitch;
/*      */ 
/*      */   
/*      */   public AxisAlignedBB boundingBox;
/*      */ 
/*      */   
/*      */   public boolean onGround;
/*      */ 
/*      */   
/*      */   public boolean isCollidedHorizontally;
/*      */ 
/*      */   
/*      */   public boolean isCollidedVertically;
/*      */ 
/*      */   
/*      */   public boolean isCollided;
/*      */ 
/*      */   
/*      */   public boolean velocityChanged;
/*      */ 
/*      */   
/*      */   protected boolean isInWeb;
/*      */ 
/*      */   
/*      */   private boolean isOutsideBorder;
/*      */ 
/*      */   
/*      */   public boolean isDead;
/*      */ 
/*      */   
/*      */   public float width;
/*      */ 
/*      */   
/*      */   public float height;
/*      */ 
/*      */   
/*      */   public float prevDistanceWalkedModified;
/*      */ 
/*      */   
/*      */   public float distanceWalkedModified;
/*      */ 
/*      */   
/*      */   public float distanceWalkedOnStepModified;
/*      */   
/*      */   public float fallDistance;
/*      */   
/*      */   private int nextStepDistance;
/*      */   
/*      */   private float field_191959_ay;
/*      */   
/*      */   public double lastTickPosX;
/*      */   
/*      */   public double lastTickPosY;
/*      */   
/*      */   public double lastTickPosZ;
/*      */   
/*      */   public float stepHeight;
/*      */   
/*      */   public boolean noClip;
/*      */   
/*      */   public float entityCollisionReduction;
/*      */   
/*      */   protected Random rand;
/*      */   
/*      */   public int ticksExisted;
/*      */   
/*      */   private int field_190534_ay;
/*      */   
/*      */   protected boolean inWater;
/*      */   
/*      */   public int hurtResistantTime;
/*      */   
/*      */   protected boolean firstUpdate;
/*      */   
/*      */   protected boolean isImmuneToFire;
/*      */   
/*      */   protected EntityDataManager dataManager;
/*      */   
/*  232 */   public static final DataParameter<Byte> FLAGS = EntityDataManager.createKey(Entity.class, DataSerializers.BYTE);
/*  233 */   public static final DataParameter<Integer> AIR = EntityDataManager.createKey(Entity.class, DataSerializers.VARINT);
/*  234 */   public static final DataParameter<String> CUSTOM_NAME = EntityDataManager.createKey(Entity.class, DataSerializers.STRING);
/*  235 */   public static final DataParameter<Boolean> CUSTOM_NAME_VISIBLE = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
/*  236 */   public static final DataParameter<Boolean> SILENT = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
/*  237 */   private static final DataParameter<Boolean> NO_GRAVITY = EntityDataManager.createKey(Entity.class, DataSerializers.BOOLEAN);
/*      */   
/*      */   public boolean addedToChunk;
/*      */   
/*      */   public int chunkCoordX;
/*      */   
/*      */   public int chunkCoordY;
/*      */   
/*      */   public int chunkCoordZ;
/*      */   
/*      */   public long serverPosX;
/*      */   
/*      */   public long serverPosY;
/*      */   
/*      */   public long serverPosZ;
/*      */   
/*      */   public boolean ignoreFrustumCheck;
/*      */   
/*      */   public boolean isAirBorne;
/*      */   
/*      */   public int timeUntilPortal;
/*      */   
/*      */   protected boolean inPortal;
/*      */   
/*      */   protected int portalCounter;
/*      */   
/*      */   public int dimension;
/*      */   
/*      */   protected BlockPos lastPortalPos;
/*      */   
/*      */   protected Vec3d lastPortalVec;
/*      */   
/*      */   protected EnumFacing teleportDirection;
/*      */   
/*      */   private boolean invulnerable;
/*      */   
/*      */   protected UUID entityUniqueID;
/*      */   
/*      */   protected String cachedUniqueIdString;
/*      */   
/*      */   private final CommandResultStats cmdResultStats;
/*      */   
/*      */   protected boolean glowing;
/*      */   
/*      */   private final Set<String> tags;
/*      */   
/*      */   private boolean isPositionDirty;
/*      */   
/*      */   private final double[] field_191505_aI;
/*      */   private long field_191506_aJ;
/*      */   
/*      */   public Entity(World worldIn) {
/*  289 */     this.entityId = nextEntityID++;
/*  290 */     this.riddenByEntities = Lists.newArrayList();
/*  291 */     this.boundingBox = ZERO_AABB;
/*  292 */     this.width = 0.6F;
/*  293 */     this.height = 1.8F;
/*  294 */     this.nextStepDistance = 1;
/*  295 */     this.field_191959_ay = 1.0F;
/*  296 */     this.rand = new Random();
/*  297 */     this.field_190534_ay = -func_190531_bD();
/*  298 */     this.firstUpdate = true;
/*  299 */     this.entityUniqueID = MathHelper.getRandomUUID(this.rand);
/*  300 */     this.cachedUniqueIdString = this.entityUniqueID.toString();
/*  301 */     this.cmdResultStats = new CommandResultStats();
/*  302 */     this.tags = Sets.newHashSet();
/*  303 */     this.field_191505_aI = new double[] { 0.0D, 0.0D, 0.0D };
/*  304 */     this.world = worldIn;
/*  305 */     setPosition(0.0D, 0.0D, 0.0D);
/*      */     
/*  307 */     if (worldIn != null)
/*      */     {
/*  309 */       this.dimension = worldIn.provider.getDimensionType().getId();
/*      */     }
/*      */     
/*  312 */     this.dataManager = new EntityDataManager(this);
/*  313 */     this.dataManager.register(FLAGS, Byte.valueOf((byte)0));
/*  314 */     this.dataManager.register(AIR, Integer.valueOf(300));
/*  315 */     this.dataManager.register(CUSTOM_NAME_VISIBLE, Boolean.valueOf(false));
/*  316 */     this.dataManager.register(CUSTOM_NAME, "");
/*  317 */     this.dataManager.register(SILENT, Boolean.valueOf(false));
/*  318 */     this.dataManager.register(NO_GRAVITY, Boolean.valueOf(false));
/*  319 */     entityInit();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getEntityId() {
/*  324 */     return this.entityId;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntityId(int id) {
/*  329 */     this.entityId = id;
/*      */   }
/*      */ 
/*      */   
/*      */   public Set<String> getTags() {
/*  334 */     return this.tags;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean addTag(String tag) {
/*  339 */     if (this.tags.size() >= 1024)
/*      */     {
/*  341 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  345 */     this.tags.add(tag);
/*  346 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean removeTag(String tag) {
/*  352 */     return this.tags.remove(tag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillCommand() {
/*  360 */     setDead();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityDataManager getDataManager() {
/*  367 */     return this.dataManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object p_equals_1_) {
/*  372 */     if (p_equals_1_ instanceof Entity)
/*      */     {
/*  374 */       return (((Entity)p_equals_1_).entityId == this.entityId);
/*      */     }
/*      */ 
/*      */     
/*  378 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  384 */     return this.entityId;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void preparePlayerToSpawn() {
/*  393 */     if (this.world != null) {
/*      */       
/*  395 */       while (this.posY > 0.0D && this.posY < 256.0D) {
/*      */         
/*  397 */         setPosition(this.posX, this.posY, this.posZ);
/*      */         
/*  399 */         if (this.world.getCollisionBoxes(this, getEntityBoundingBox()).isEmpty()) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/*  404 */         this.posY++;
/*      */       } 
/*      */       
/*  407 */       this.motionX = 0.0D;
/*  408 */       this.motionY = 0.0D;
/*  409 */       this.motionZ = 0.0D;
/*  410 */       this.rotationPitch = 0.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDead() {
/*  419 */     this.isDead = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDropItemsWhenDead(boolean dropWhenDead) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setSize(float width, float height) {
/*  434 */     if (width != this.width || height != this.height) {
/*      */       
/*  436 */       float f = this.width;
/*  437 */       this.width = width;
/*  438 */       this.height = height;
/*      */       
/*  440 */       if (this.width < f) {
/*      */         
/*  442 */         double d0 = width / 2.0D;
/*  443 */         setEntityBoundingBox(new AxisAlignedBB(this.posX - d0, this.posY, this.posZ - d0, this.posX + d0, this.posY + this.height, this.posZ + d0));
/*      */         
/*      */         return;
/*      */       } 
/*  447 */       AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/*  448 */       setEntityBoundingBox(new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.minX + this.width, axisalignedbb.minY + this.height, axisalignedbb.minZ + this.width));
/*      */       
/*  450 */       if (this.width > f && !this.firstUpdate && !this.world.isRemote)
/*      */       {
/*  452 */         moveEntity(MoverType.SELF, (f - this.width), 0.0D, (f - this.width));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setRotation(float yaw, float pitch) {
/*  462 */     this.rotationYaw = yaw % 360.0F;
/*  463 */     this.rotationPitch = pitch % 360.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPosition(double x, double y, double z) {
/*  471 */     this.posX = x;
/*  472 */     this.posY = y;
/*  473 */     this.posZ = z;
/*  474 */     float f = this.width / 2.0F;
/*  475 */     float f1 = this.height;
/*  476 */     setEntityBoundingBox(new AxisAlignedBB(x - f, y, z - f, x + f, y + f1, z + f));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAngles(float yaw, float pitch) {
/*  485 */     float f = this.rotationPitch;
/*  486 */     float f1 = this.rotationYaw;
/*  487 */     this.rotationYaw = (float)(this.rotationYaw + yaw * 0.15D);
/*  488 */     this.rotationPitch = (float)(this.rotationPitch - pitch * 0.15D);
/*  489 */     this.rotationPitch = MathHelper.clamp(this.rotationPitch, -90.0F, 90.0F);
/*  490 */     this.prevRotationPitch += this.rotationPitch - f;
/*  491 */     this.prevRotationYaw += this.rotationYaw - f1;
/*      */     
/*  493 */     if (this.ridingEntity != null)
/*      */     {
/*  495 */       this.ridingEntity.applyOrientationToEntity(this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  504 */     if (!this.world.isRemote)
/*      */     {
/*  506 */       setFlag(6, isGlowing());
/*      */     }
/*      */     
/*  509 */     onEntityUpdate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityUpdate() {
/*  517 */     this.world.theProfiler.startSection("entityBaseTick");
/*      */     
/*  519 */     if (isRiding() && (getRidingEntity()).isDead)
/*      */     {
/*  521 */       dismountRidingEntity();
/*      */     }
/*      */     
/*  524 */     if (this.rideCooldown > 0)
/*      */     {
/*  526 */       this.rideCooldown--;
/*      */     }
/*      */     
/*  529 */     this.prevDistanceWalkedModified = this.distanceWalkedModified;
/*  530 */     this.prevPosX = this.posX;
/*  531 */     this.prevPosY = this.posY;
/*  532 */     this.prevPosZ = this.posZ;
/*  533 */     this.prevRotationPitch = this.rotationPitch;
/*  534 */     this.prevRotationYaw = this.rotationYaw;
/*      */     
/*  536 */     if (!this.world.isRemote && this.world instanceof WorldServer) {
/*      */       
/*  538 */       this.world.theProfiler.startSection("portal");
/*      */       
/*  540 */       if (this.inPortal) {
/*      */         
/*  542 */         MinecraftServer minecraftserver = this.world.getMinecraftServer();
/*      */         
/*  544 */         if (minecraftserver.getAllowNether())
/*      */         {
/*  546 */           if (!isRiding()) {
/*      */             
/*  548 */             int i = getMaxInPortalTime();
/*      */             
/*  550 */             if (this.portalCounter++ >= i) {
/*      */               int j;
/*  552 */               this.portalCounter = i;
/*  553 */               this.timeUntilPortal = getPortalCooldown();
/*      */ 
/*      */               
/*  556 */               if (this.world.provider.getDimensionType().getId() == -1) {
/*      */                 
/*  558 */                 j = 0;
/*      */               }
/*      */               else {
/*      */                 
/*  562 */                 j = -1;
/*      */               } 
/*      */               
/*  565 */               changeDimension(j);
/*      */             } 
/*      */           } 
/*      */           
/*  569 */           this.inPortal = false;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  574 */         if (this.portalCounter > 0)
/*      */         {
/*  576 */           this.portalCounter -= 4;
/*      */         }
/*      */         
/*  579 */         if (this.portalCounter < 0)
/*      */         {
/*  581 */           this.portalCounter = 0;
/*      */         }
/*      */       } 
/*      */       
/*  585 */       decrementTimeUntilPortal();
/*  586 */       this.world.theProfiler.endSection();
/*      */     } 
/*      */     
/*  589 */     spawnRunningParticles();
/*  590 */     handleWaterMovement();
/*      */     
/*  592 */     if (this.world.isRemote) {
/*      */       
/*  594 */       extinguish();
/*      */     }
/*  596 */     else if (this.field_190534_ay > 0) {
/*      */       
/*  598 */       if (this.isImmuneToFire) {
/*      */         
/*  600 */         this.field_190534_ay -= 4;
/*      */         
/*  602 */         if (this.field_190534_ay < 0)
/*      */         {
/*  604 */           extinguish();
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  609 */         if (this.field_190534_ay % 20 == 0)
/*      */         {
/*  611 */           attackEntityFrom(DamageSource.onFire, 1.0F);
/*      */         }
/*      */         
/*  614 */         this.field_190534_ay--;
/*      */       } 
/*      */     } 
/*      */     
/*  618 */     if (isInLava()) {
/*      */       
/*  620 */       setOnFireFromLava();
/*  621 */       this.fallDistance *= 0.5F;
/*      */     } 
/*      */     
/*  624 */     if (this.posY < -64.0D)
/*      */     {
/*  626 */       kill();
/*      */     }
/*      */     
/*  629 */     if (!this.world.isRemote)
/*      */     {
/*  631 */       setFlag(0, (this.field_190534_ay > 0));
/*      */     }
/*      */     
/*  634 */     this.firstUpdate = false;
/*  635 */     this.world.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void decrementTimeUntilPortal() {
/*  643 */     if (this.timeUntilPortal > 0)
/*      */     {
/*  645 */       this.timeUntilPortal--;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxInPortalTime() {
/*  654 */     return 1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setOnFireFromLava() {
/*  662 */     if (!this.isImmuneToFire) {
/*      */       
/*  664 */       attackEntityFrom(DamageSource.lava, 4.0F);
/*  665 */       setFire(15);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFire(int seconds) {
/*  674 */     int i = seconds * 20;
/*      */     
/*  676 */     if (this instanceof EntityLivingBase)
/*      */     {
/*  678 */       i = EnchantmentProtection.getFireTimeForEntity((EntityLivingBase)this, i);
/*      */     }
/*      */     
/*  681 */     if (this.field_190534_ay < i)
/*      */     {
/*  683 */       this.field_190534_ay = i;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void extinguish() {
/*  692 */     this.field_190534_ay = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void kill() {
/*  700 */     setDead();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOffsetPositionInLiquid(double x, double y, double z) {
/*  708 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox().offset(x, y, z);
/*  709 */     return isLiquidPresentInAABB(axisalignedbb);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isLiquidPresentInAABB(AxisAlignedBB bb) {
/*  717 */     return (this.world.getCollisionBoxes(this, bb).isEmpty() && !this.world.containsAnyLiquid(bb));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void moveEntity(MoverType x, double p_70091_2_, double p_70091_4_, double p_70091_6_) {
/*  725 */     if (this.noClip) {
/*      */       
/*  727 */       setEntityBoundingBox(getEntityBoundingBox().offset(p_70091_2_, p_70091_4_, p_70091_6_));
/*  728 */       resetPositionToBB();
/*      */     }
/*      */     else {
/*      */       
/*  732 */       if (x == MoverType.PISTON) {
/*      */         
/*  734 */         long i = this.world.getTotalWorldTime();
/*      */         
/*  736 */         if (i != this.field_191506_aJ) {
/*      */           
/*  738 */           Arrays.fill(this.field_191505_aI, 0.0D);
/*  739 */           this.field_191506_aJ = i;
/*      */         } 
/*      */         
/*  742 */         if (p_70091_2_ != 0.0D) {
/*      */           
/*  744 */           int j = EnumFacing.Axis.X.ordinal();
/*  745 */           double d0 = MathHelper.clamp(p_70091_2_ + this.field_191505_aI[j], -0.51D, 0.51D);
/*  746 */           p_70091_2_ = d0 - this.field_191505_aI[j];
/*  747 */           this.field_191505_aI[j] = d0;
/*      */           
/*  749 */           if (Math.abs(p_70091_2_) <= 9.999999747378752E-6D)
/*      */           {
/*      */             return;
/*      */           }
/*      */         }
/*  754 */         else if (p_70091_4_ != 0.0D) {
/*      */           
/*  756 */           int l4 = EnumFacing.Axis.Y.ordinal();
/*  757 */           double d12 = MathHelper.clamp(p_70091_4_ + this.field_191505_aI[l4], -0.51D, 0.51D);
/*  758 */           p_70091_4_ = d12 - this.field_191505_aI[l4];
/*  759 */           this.field_191505_aI[l4] = d12;
/*      */           
/*  761 */           if (Math.abs(p_70091_4_) <= 9.999999747378752E-6D)
/*      */           {
/*      */             return;
/*      */           }
/*      */         }
/*      */         else {
/*      */           
/*  768 */           if (p_70091_6_ == 0.0D) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/*  773 */           int i5 = EnumFacing.Axis.Z.ordinal();
/*  774 */           double d13 = MathHelper.clamp(p_70091_6_ + this.field_191505_aI[i5], -0.51D, 0.51D);
/*  775 */           p_70091_6_ = d13 - this.field_191505_aI[i5];
/*  776 */           this.field_191505_aI[i5] = d13;
/*      */           
/*  778 */           if (Math.abs(p_70091_6_) <= 9.999999747378752E-6D) {
/*      */             return;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  785 */       this.world.theProfiler.startSection("move");
/*  786 */       double d10 = this.posX;
/*  787 */       double d11 = this.posY;
/*  788 */       double d1 = this.posZ;
/*      */       
/*  790 */       if (this.isInWeb) {
/*      */         
/*  792 */         this.isInWeb = false;
/*  793 */         p_70091_2_ *= 0.25D;
/*  794 */         p_70091_4_ *= 0.05000000074505806D;
/*  795 */         p_70091_6_ *= 0.25D;
/*  796 */         this.motionX = 0.0D;
/*  797 */         this.motionY = 0.0D;
/*  798 */         this.motionZ = 0.0D;
/*      */       } 
/*      */       
/*  801 */       double d2 = p_70091_2_;
/*  802 */       double d3 = p_70091_4_;
/*  803 */       double d4 = p_70091_6_;
/*      */       
/*  805 */       if ((x == MoverType.SELF || x == MoverType.PLAYER) && this.onGround && isSneaking() && this instanceof EntityPlayer) {
/*      */         
/*  807 */         for (double d5 = 0.05D; p_70091_2_ != 0.0D && this.world.getCollisionBoxes(this, getEntityBoundingBox().offset(p_70091_2_, -this.stepHeight, 0.0D)).isEmpty(); d2 = p_70091_2_) {
/*      */           
/*  809 */           if (p_70091_2_ < 0.05D && p_70091_2_ >= -0.05D) {
/*      */             
/*  811 */             p_70091_2_ = 0.0D;
/*      */           }
/*  813 */           else if (p_70091_2_ > 0.0D) {
/*      */             
/*  815 */             p_70091_2_ -= 0.05D;
/*      */           }
/*      */           else {
/*      */             
/*  819 */             p_70091_2_ += 0.05D;
/*      */           } 
/*      */         } 
/*      */         
/*  823 */         for (; p_70091_6_ != 0.0D && this.world.getCollisionBoxes(this, getEntityBoundingBox().offset(0.0D, -this.stepHeight, p_70091_6_)).isEmpty(); d4 = p_70091_6_) {
/*      */           
/*  825 */           if (p_70091_6_ < 0.05D && p_70091_6_ >= -0.05D) {
/*      */             
/*  827 */             p_70091_6_ = 0.0D;
/*      */           }
/*  829 */           else if (p_70091_6_ > 0.0D) {
/*      */             
/*  831 */             p_70091_6_ -= 0.05D;
/*      */           }
/*      */           else {
/*      */             
/*  835 */             p_70091_6_ += 0.05D;
/*      */           } 
/*      */         } 
/*      */         
/*  839 */         for (; p_70091_2_ != 0.0D && p_70091_6_ != 0.0D && this.world.getCollisionBoxes(this, getEntityBoundingBox().offset(p_70091_2_, -this.stepHeight, p_70091_6_)).isEmpty(); d4 = p_70091_6_) {
/*      */           
/*  841 */           if (p_70091_2_ < 0.05D && p_70091_2_ >= -0.05D) {
/*      */             
/*  843 */             p_70091_2_ = 0.0D;
/*      */           }
/*  845 */           else if (p_70091_2_ > 0.0D) {
/*      */             
/*  847 */             p_70091_2_ -= 0.05D;
/*      */           }
/*      */           else {
/*      */             
/*  851 */             p_70091_2_ += 0.05D;
/*      */           } 
/*      */           
/*  854 */           d2 = p_70091_2_;
/*      */           
/*  856 */           if (p_70091_6_ < 0.05D && p_70091_6_ >= -0.05D) {
/*      */             
/*  858 */             p_70091_6_ = 0.0D;
/*      */           }
/*  860 */           else if (p_70091_6_ > 0.0D) {
/*      */             
/*  862 */             p_70091_6_ -= 0.05D;
/*      */           }
/*      */           else {
/*      */             
/*  866 */             p_70091_6_ += 0.05D;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  871 */       List<AxisAlignedBB> list1 = this.world.getCollisionBoxes(this, getEntityBoundingBox().addCoord(p_70091_2_, p_70091_4_, p_70091_6_));
/*  872 */       AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/*      */       
/*  874 */       if (p_70091_4_ != 0.0D) {
/*      */         
/*  876 */         int k = 0;
/*      */         
/*  878 */         for (int l = list1.size(); k < l; k++)
/*      */         {
/*  880 */           p_70091_4_ = ((AxisAlignedBB)list1.get(k)).calculateYOffset(getEntityBoundingBox(), p_70091_4_);
/*      */         }
/*      */         
/*  883 */         setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, p_70091_4_, 0.0D));
/*      */       } 
/*      */       
/*  886 */       if (p_70091_2_ != 0.0D) {
/*      */         
/*  888 */         int j5 = 0;
/*      */         
/*  890 */         for (int l5 = list1.size(); j5 < l5; j5++)
/*      */         {
/*  892 */           p_70091_2_ = ((AxisAlignedBB)list1.get(j5)).calculateXOffset(getEntityBoundingBox(), p_70091_2_);
/*      */         }
/*      */         
/*  895 */         if (p_70091_2_ != 0.0D)
/*      */         {
/*  897 */           setEntityBoundingBox(getEntityBoundingBox().offset(p_70091_2_, 0.0D, 0.0D));
/*      */         }
/*      */       } 
/*      */       
/*  901 */       if (p_70091_6_ != 0.0D) {
/*      */         
/*  903 */         int k5 = 0;
/*      */         
/*  905 */         for (int i6 = list1.size(); k5 < i6; k5++)
/*      */         {
/*  907 */           p_70091_6_ = ((AxisAlignedBB)list1.get(k5)).calculateZOffset(getEntityBoundingBox(), p_70091_6_);
/*      */         }
/*      */         
/*  910 */         if (p_70091_6_ != 0.0D)
/*      */         {
/*  912 */           setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, 0.0D, p_70091_6_));
/*      */         }
/*      */       } 
/*      */       
/*  916 */       boolean flag = !(!this.onGround && (d3 == p_70091_4_ || d3 >= 0.0D));
/*      */       
/*  918 */       if (this.stepHeight > 0.0F && flag && (d2 != p_70091_2_ || d4 != p_70091_6_)) {
/*      */         
/*  920 */         double d14 = p_70091_2_;
/*  921 */         double d6 = p_70091_4_;
/*  922 */         double d7 = p_70091_6_;
/*  923 */         AxisAlignedBB axisalignedbb1 = getEntityBoundingBox();
/*  924 */         setEntityBoundingBox(axisalignedbb);
/*  925 */         p_70091_4_ = this.stepHeight;
/*  926 */         List<AxisAlignedBB> list = this.world.getCollisionBoxes(this, getEntityBoundingBox().addCoord(d2, p_70091_4_, d4));
/*  927 */         AxisAlignedBB axisalignedbb2 = getEntityBoundingBox();
/*  928 */         AxisAlignedBB axisalignedbb3 = axisalignedbb2.addCoord(d2, 0.0D, d4);
/*  929 */         double d8 = p_70091_4_;
/*  930 */         int j1 = 0;
/*      */         
/*  932 */         for (int k1 = list.size(); j1 < k1; j1++)
/*      */         {
/*  934 */           d8 = ((AxisAlignedBB)list.get(j1)).calculateYOffset(axisalignedbb3, d8);
/*      */         }
/*      */         
/*  937 */         axisalignedbb2 = axisalignedbb2.offset(0.0D, d8, 0.0D);
/*  938 */         double d18 = d2;
/*  939 */         int l1 = 0;
/*      */         
/*  941 */         for (int i2 = list.size(); l1 < i2; l1++)
/*      */         {
/*  943 */           d18 = ((AxisAlignedBB)list.get(l1)).calculateXOffset(axisalignedbb2, d18);
/*      */         }
/*      */         
/*  946 */         axisalignedbb2 = axisalignedbb2.offset(d18, 0.0D, 0.0D);
/*  947 */         double d19 = d4;
/*  948 */         int j2 = 0;
/*      */         
/*  950 */         for (int k2 = list.size(); j2 < k2; j2++)
/*      */         {
/*  952 */           d19 = ((AxisAlignedBB)list.get(j2)).calculateZOffset(axisalignedbb2, d19);
/*      */         }
/*      */         
/*  955 */         axisalignedbb2 = axisalignedbb2.offset(0.0D, 0.0D, d19);
/*  956 */         AxisAlignedBB axisalignedbb4 = getEntityBoundingBox();
/*  957 */         double d20 = p_70091_4_;
/*  958 */         int l2 = 0;
/*      */         
/*  960 */         for (int i3 = list.size(); l2 < i3; l2++)
/*      */         {
/*  962 */           d20 = ((AxisAlignedBB)list.get(l2)).calculateYOffset(axisalignedbb4, d20);
/*      */         }
/*      */         
/*  965 */         axisalignedbb4 = axisalignedbb4.offset(0.0D, d20, 0.0D);
/*  966 */         double d21 = d2;
/*  967 */         int j3 = 0;
/*      */         
/*  969 */         for (int k3 = list.size(); j3 < k3; j3++)
/*      */         {
/*  971 */           d21 = ((AxisAlignedBB)list.get(j3)).calculateXOffset(axisalignedbb4, d21);
/*      */         }
/*      */         
/*  974 */         axisalignedbb4 = axisalignedbb4.offset(d21, 0.0D, 0.0D);
/*  975 */         double d22 = d4;
/*  976 */         int l3 = 0;
/*      */         
/*  978 */         for (int i4 = list.size(); l3 < i4; l3++)
/*      */         {
/*  980 */           d22 = ((AxisAlignedBB)list.get(l3)).calculateZOffset(axisalignedbb4, d22);
/*      */         }
/*      */         
/*  983 */         axisalignedbb4 = axisalignedbb4.offset(0.0D, 0.0D, d22);
/*  984 */         double d23 = d18 * d18 + d19 * d19;
/*  985 */         double d9 = d21 * d21 + d22 * d22;
/*      */         
/*  987 */         if (d23 > d9) {
/*      */           
/*  989 */           p_70091_2_ = d18;
/*  990 */           p_70091_6_ = d19;
/*  991 */           p_70091_4_ = -d8;
/*  992 */           setEntityBoundingBox(axisalignedbb2);
/*      */         }
/*      */         else {
/*      */           
/*  996 */           p_70091_2_ = d21;
/*  997 */           p_70091_6_ = d22;
/*  998 */           p_70091_4_ = -d20;
/*  999 */           setEntityBoundingBox(axisalignedbb4);
/*      */         } 
/*      */         
/* 1002 */         int j4 = 0;
/*      */         
/* 1004 */         for (int k4 = list.size(); j4 < k4; j4++)
/*      */         {
/* 1006 */           p_70091_4_ = ((AxisAlignedBB)list.get(j4)).calculateYOffset(getEntityBoundingBox(), p_70091_4_);
/*      */         }
/*      */         
/* 1009 */         setEntityBoundingBox(getEntityBoundingBox().offset(0.0D, p_70091_4_, 0.0D));
/*      */         
/* 1011 */         if (d14 * d14 + d7 * d7 >= p_70091_2_ * p_70091_2_ + p_70091_6_ * p_70091_6_) {
/*      */           
/* 1013 */           p_70091_2_ = d14;
/* 1014 */           p_70091_4_ = d6;
/* 1015 */           p_70091_6_ = d7;
/* 1016 */           setEntityBoundingBox(axisalignedbb1);
/*      */         } 
/*      */       } 
/*      */       
/* 1020 */       this.world.theProfiler.endSection();
/* 1021 */       this.world.theProfiler.startSection("rest");
/* 1022 */       resetPositionToBB();
/* 1023 */       this.isCollidedHorizontally = !(d2 == p_70091_2_ && d4 == p_70091_6_);
/* 1024 */       this.isCollidedVertically = (d3 != p_70091_4_);
/* 1025 */       this.onGround = (this.isCollidedVertically && d3 < 0.0D);
/* 1026 */       this.isCollided = !(!this.isCollidedHorizontally && !this.isCollidedVertically);
/* 1027 */       int j6 = MathHelper.floor(this.posX);
/* 1028 */       int i1 = MathHelper.floor(this.posY - 0.20000000298023224D);
/* 1029 */       int k6 = MathHelper.floor(this.posZ);
/* 1030 */       BlockPos blockpos = new BlockPos(j6, i1, k6);
/* 1031 */       IBlockState iblockstate = this.world.getBlockState(blockpos);
/*      */       
/* 1033 */       if (iblockstate.getMaterial() == Material.AIR) {
/*      */         
/* 1035 */         BlockPos blockpos1 = blockpos.down();
/* 1036 */         IBlockState iblockstate1 = this.world.getBlockState(blockpos1);
/* 1037 */         Block block1 = iblockstate1.getBlock();
/*      */         
/* 1039 */         if (block1 instanceof net.minecraft.block.BlockFence || block1 instanceof net.minecraft.block.BlockWall || block1 instanceof net.minecraft.block.BlockFenceGate) {
/*      */           
/* 1041 */           iblockstate = iblockstate1;
/* 1042 */           blockpos = blockpos1;
/*      */         } 
/*      */       } 
/*      */       
/* 1046 */       updateFallState(p_70091_4_, this.onGround, iblockstate, blockpos);
/*      */       
/* 1048 */       if (d2 != p_70091_2_)
/*      */       {
/* 1050 */         this.motionX = 0.0D;
/*      */       }
/*      */       
/* 1053 */       if (d4 != p_70091_6_)
/*      */       {
/* 1055 */         this.motionZ = 0.0D;
/*      */       }
/*      */       
/* 1058 */       Block block = iblockstate.getBlock();
/*      */       
/* 1060 */       if (d3 != p_70091_4_)
/*      */       {
/* 1062 */         block.onLanded(this.world, this);
/*      */       }
/*      */       
/* 1065 */       if (canTriggerWalking() && (!this.onGround || !isSneaking() || !(this instanceof EntityPlayer)) && !isRiding()) {
/*      */         
/* 1067 */         double d15 = this.posX - d10;
/* 1068 */         double d16 = this.posY - d11;
/* 1069 */         double d17 = this.posZ - d1;
/*      */         
/* 1071 */         if (block != Blocks.LADDER)
/*      */         {
/* 1073 */           d16 = 0.0D;
/*      */         }
/*      */         
/* 1076 */         if (block != null && this.onGround)
/*      */         {
/* 1078 */           block.onEntityWalk(this.world, blockpos, this);
/*      */         }
/*      */         
/* 1081 */         this.distanceWalkedModified = (float)(this.distanceWalkedModified + MathHelper.sqrt(d15 * d15 + d17 * d17) * 0.6D);
/* 1082 */         this.distanceWalkedOnStepModified = (float)(this.distanceWalkedOnStepModified + MathHelper.sqrt(d15 * d15 + d16 * d16 + d17 * d17) * 0.6D);
/*      */         
/* 1084 */         if (this.distanceWalkedOnStepModified > this.nextStepDistance && iblockstate.getMaterial() != Material.AIR) {
/*      */           
/* 1086 */           this.nextStepDistance = (int)this.distanceWalkedOnStepModified + 1;
/*      */           
/* 1088 */           if (isInWater())
/*      */           {
/* 1090 */             Entity entity = (isBeingRidden() && getControllingPassenger() != null) ? getControllingPassenger() : this;
/* 1091 */             float f = (entity == this) ? 0.35F : 0.4F;
/* 1092 */             float f1 = MathHelper.sqrt(entity.motionX * entity.motionX * 0.20000000298023224D + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ * 0.20000000298023224D) * f;
/*      */             
/* 1094 */             if (f1 > 1.0F)
/*      */             {
/* 1096 */               f1 = 1.0F;
/*      */             }
/*      */             
/* 1099 */             playSound(getSwimSound(), f1, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/*      */           }
/*      */           else
/*      */           {
/* 1103 */             playStepSound(blockpos, block);
/*      */           }
/*      */         
/* 1106 */         } else if (this.distanceWalkedOnStepModified > this.field_191959_ay && func_191957_ae() && iblockstate.getMaterial() == Material.AIR) {
/*      */           
/* 1108 */           this.field_191959_ay = func_191954_d(this.distanceWalkedOnStepModified);
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/* 1114 */         doBlockCollisions();
/*      */       }
/* 1116 */       catch (Throwable throwable) {
/*      */         
/* 1118 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Checking entity block collision");
/* 1119 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being checked for collision");
/* 1120 */         addEntityCrashInfo(crashreportcategory);
/* 1121 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/* 1124 */       boolean flag1 = isWet();
/*      */       
/* 1126 */       if (this.world.isFlammableWithin(getEntityBoundingBox().contract(0.001D))) {
/*      */         
/* 1128 */         dealFireDamage(1);
/*      */         
/* 1130 */         if (!flag1)
/*      */         {
/* 1132 */           this.field_190534_ay++;
/*      */           
/* 1134 */           if (this.field_190534_ay == 0)
/*      */           {
/* 1136 */             setFire(8);
/*      */           }
/*      */         }
/*      */       
/* 1140 */       } else if (this.field_190534_ay <= 0) {
/*      */         
/* 1142 */         this.field_190534_ay = -func_190531_bD();
/*      */       } 
/*      */       
/* 1145 */       if (flag1 && isBurning()) {
/*      */         
/* 1147 */         playSound(SoundEvents.ENTITY_GENERIC_EXTINGUISH_FIRE, 0.7F, 1.6F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/* 1148 */         this.field_190534_ay = -func_190531_bD();
/*      */       } 
/*      */       
/* 1151 */       this.world.theProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetPositionToBB() {
/* 1160 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/* 1161 */     this.posX = (axisalignedbb.minX + axisalignedbb.maxX) / 2.0D;
/* 1162 */     this.posY = axisalignedbb.minY;
/* 1163 */     this.posZ = (axisalignedbb.minZ + axisalignedbb.maxZ) / 2.0D;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getSwimSound() {
/* 1168 */     return SoundEvents.ENTITY_GENERIC_SWIM;
/*      */   }
/*      */ 
/*      */   
/*      */   protected SoundEvent getSplashSound() {
/* 1173 */     return SoundEvents.ENTITY_GENERIC_SPLASH;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void doBlockCollisions() {
/* 1178 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/* 1179 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.minX + 0.001D, axisalignedbb.minY + 0.001D, axisalignedbb.minZ + 0.001D);
/* 1180 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos1 = BlockPos.PooledMutableBlockPos.retain(axisalignedbb.maxX - 0.001D, axisalignedbb.maxY - 0.001D, axisalignedbb.maxZ - 0.001D);
/* 1181 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos2 = BlockPos.PooledMutableBlockPos.retain();
/*      */     
/* 1183 */     if (this.world.isAreaLoaded((BlockPos)blockpos$pooledmutableblockpos, (BlockPos)blockpos$pooledmutableblockpos1))
/*      */     {
/* 1185 */       for (int i = blockpos$pooledmutableblockpos.getX(); i <= blockpos$pooledmutableblockpos1.getX(); i++) {
/*      */         
/* 1187 */         for (int j = blockpos$pooledmutableblockpos.getY(); j <= blockpos$pooledmutableblockpos1.getY(); j++) {
/*      */           
/* 1189 */           for (int k = blockpos$pooledmutableblockpos.getZ(); k <= blockpos$pooledmutableblockpos1.getZ(); k++) {
/*      */             
/* 1191 */             blockpos$pooledmutableblockpos2.setPos(i, j, k);
/* 1192 */             IBlockState iblockstate = this.world.getBlockState((BlockPos)blockpos$pooledmutableblockpos2);
/*      */ 
/*      */             
/*      */             try {
/* 1196 */               iblockstate.getBlock().onEntityCollidedWithBlock(this.world, (BlockPos)blockpos$pooledmutableblockpos2, iblockstate, this);
/* 1197 */               func_191955_a(iblockstate);
/*      */             }
/* 1199 */             catch (Throwable throwable) {
/*      */               
/* 1201 */               CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Colliding entity with block");
/* 1202 */               CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being collided with");
/* 1203 */               CrashReportCategory.addBlockInfo(crashreportcategory, (BlockPos)blockpos$pooledmutableblockpos2, iblockstate);
/* 1204 */               throw new ReportedException(crashreport);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */     
/* 1211 */     blockpos$pooledmutableblockpos.release();
/* 1212 */     blockpos$pooledmutableblockpos1.release();
/* 1213 */     blockpos$pooledmutableblockpos2.release();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void func_191955_a(IBlockState p_191955_1_) {}
/*      */ 
/*      */   
/*      */   protected void playStepSound(BlockPos pos, Block blockIn) {
/* 1222 */     SoundType soundtype = blockIn.getSoundType();
/*      */     
/* 1224 */     if (this.world.getBlockState(pos.up()).getBlock() == Blocks.SNOW_LAYER) {
/*      */       
/* 1226 */       soundtype = Blocks.SNOW_LAYER.getSoundType();
/* 1227 */       playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
/*      */     }
/* 1229 */     else if (!blockIn.getDefaultState().getMaterial().isLiquid()) {
/*      */       
/* 1231 */       playSound(soundtype.getStepSound(), soundtype.getVolume() * 0.15F, soundtype.getPitch());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected float func_191954_d(float p_191954_1_) {
/* 1237 */     return 0.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean func_191957_ae() {
/* 1242 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSound(SoundEvent soundIn, float volume, float pitch) {
/* 1247 */     if (!isSilent())
/*      */     {
/* 1249 */       this.world.playSound(null, this.posX, this.posY, this.posZ, soundIn, getSoundCategory(), volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSilent() {
/* 1258 */     return ((Boolean)this.dataManager.get(SILENT)).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSilent(boolean isSilent) {
/* 1266 */     this.dataManager.set(SILENT, Boolean.valueOf(isSilent));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasNoGravity() {
/* 1271 */     return ((Boolean)this.dataManager.get(NO_GRAVITY)).booleanValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setNoGravity(boolean noGravity) {
/* 1276 */     this.dataManager.set(NO_GRAVITY, Boolean.valueOf(noGravity));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canTriggerWalking() {
/* 1285 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
/* 1290 */     if (onGroundIn) {
/*      */       
/* 1292 */       if (this.fallDistance > 0.0F)
/*      */       {
/* 1294 */         state.getBlock().onFallenUpon(this.world, pos, this, this.fallDistance);
/*      */       }
/*      */       
/* 1297 */       this.fallDistance = 0.0F;
/*      */     }
/* 1299 */     else if (y < 0.0D) {
/*      */       
/* 1301 */       this.fallDistance = (float)(this.fallDistance - y);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public AxisAlignedBB getCollisionBoundingBox() {
/* 1312 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void dealFireDamage(int amount) {
/* 1320 */     if (!this.isImmuneToFire)
/*      */     {
/* 1322 */       attackEntityFrom(DamageSource.inFire, amount);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public final boolean isImmuneToFire() {
/* 1328 */     return this.isImmuneToFire;
/*      */   }
/*      */ 
/*      */   
/*      */   public void fall(float distance, float damageMultiplier) {
/* 1333 */     if (isBeingRidden())
/*      */     {
/* 1335 */       for (Entity entity : getPassengers())
/*      */       {
/* 1337 */         entity.fall(distance, damageMultiplier);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isWet() {
/* 1347 */     if (this.inWater)
/*      */     {
/* 1349 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1353 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(this.posX, this.posY, this.posZ);
/*      */     
/* 1355 */     if (!this.world.isRainingAt((BlockPos)blockpos$pooledmutableblockpos) && !this.world.isRainingAt((BlockPos)blockpos$pooledmutableblockpos.setPos(this.posX, this.posY + this.height, this.posZ))) {
/*      */       
/* 1357 */       blockpos$pooledmutableblockpos.release();
/* 1358 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1362 */     blockpos$pooledmutableblockpos.release();
/* 1363 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInWater() {
/* 1374 */     return this.inWater;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_191953_am() {
/* 1379 */     return this.world.handleMaterialAcceleration(getEntityBoundingBox().expand(0.0D, -20.0D, 0.0D).contract(0.001D), Material.WATER, this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleWaterMovement() {
/* 1387 */     if (getRidingEntity() instanceof net.minecraft.entity.item.EntityBoat) {
/*      */       
/* 1389 */       this.inWater = false;
/*      */     }
/* 1391 */     else if (this.world.handleMaterialAcceleration(getEntityBoundingBox().expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D), Material.WATER, this)) {
/*      */       
/* 1393 */       if (!this.inWater && !this.firstUpdate)
/*      */       {
/* 1395 */         resetHeight();
/*      */       }
/*      */       
/* 1398 */       this.fallDistance = 0.0F;
/* 1399 */       this.inWater = true;
/* 1400 */       extinguish();
/*      */     }
/*      */     else {
/*      */       
/* 1404 */       this.inWater = false;
/*      */     } 
/*      */     
/* 1407 */     return this.inWater;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void resetHeight() {
/* 1415 */     Entity entity = (isBeingRidden() && getControllingPassenger() != null) ? getControllingPassenger() : this;
/* 1416 */     float f = (entity == this) ? 0.2F : 0.9F;
/* 1417 */     float f1 = MathHelper.sqrt(entity.motionX * entity.motionX * 0.20000000298023224D + entity.motionY * entity.motionY + entity.motionZ * entity.motionZ * 0.20000000298023224D) * f;
/*      */     
/* 1419 */     if (f1 > 1.0F)
/*      */     {
/* 1421 */       f1 = 1.0F;
/*      */     }
/*      */     
/* 1424 */     playSound(getSplashSound(), f1, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/* 1425 */     float f2 = MathHelper.floor((getEntityBoundingBox()).minY);
/*      */     
/* 1427 */     for (int i = 0; i < 1.0F + this.width * 20.0F; i++) {
/*      */       
/* 1429 */       float f3 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1430 */       float f4 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1431 */       this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + f3, (f2 + 1.0F), this.posZ + f4, this.motionX, this.motionY - (this.rand.nextFloat() * 0.2F), this.motionZ, new int[0]);
/*      */     } 
/*      */     
/* 1434 */     for (int j = 0; j < 1.0F + this.width * 20.0F; j++) {
/*      */       
/* 1436 */       float f5 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1437 */       float f6 = (this.rand.nextFloat() * 2.0F - 1.0F) * this.width;
/* 1438 */       this.world.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + f5, (f2 + 1.0F), this.posZ + f6, this.motionX, this.motionY, this.motionZ, new int[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnRunningParticles() {
/* 1447 */     if (isSprinting() && !isInWater())
/*      */     {
/* 1449 */       createRunningParticles();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void createRunningParticles() {
/* 1455 */     int i = MathHelper.floor(this.posX);
/* 1456 */     int j = MathHelper.floor(this.posY - 0.20000000298023224D);
/* 1457 */     int k = MathHelper.floor(this.posZ);
/* 1458 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 1459 */     IBlockState iblockstate = this.world.getBlockState(blockpos);
/*      */     
/* 1461 */     if (iblockstate.getRenderType() != EnumBlockRenderType.INVISIBLE)
/*      */     {
/* 1463 */       this.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, this.posX + (this.rand.nextFloat() - 0.5D) * this.width, (getEntityBoundingBox()).minY + 0.1D, this.posZ + (this.rand.nextFloat() - 0.5D) * this.width, -this.motionX * 4.0D, 1.5D, -this.motionZ * 4.0D, new int[] { Block.getStateId(iblockstate) });
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInsideOfMaterial(Material materialIn) {
/* 1472 */     if (getRidingEntity() instanceof net.minecraft.entity.item.EntityBoat)
/*      */     {
/* 1474 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1478 */     double d0 = this.posY + getEyeHeight();
/* 1479 */     BlockPos blockpos = new BlockPos(this.posX, d0, this.posZ);
/* 1480 */     IBlockState iblockstate = this.world.getBlockState(blockpos);
/*      */     
/* 1482 */     if (iblockstate.getMaterial() == materialIn) {
/*      */       
/* 1484 */       float f = BlockLiquid.getLiquidHeightPercent(iblockstate.getBlock().getMetaFromState(iblockstate)) - 0.11111111F;
/* 1485 */       float f1 = (blockpos.getY() + 1) - f;
/* 1486 */       boolean flag = (d0 < f1);
/* 1487 */       return (!flag && this instanceof EntityPlayer) ? false : flag;
/*      */     } 
/*      */ 
/*      */     
/* 1491 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInLava() {
/* 1498 */     return this.world.isMaterialInBB(getEntityBoundingBox().expand(-0.10000000149011612D, -0.4000000059604645D, -0.10000000149011612D), Material.LAVA);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_191958_b(float p_191958_1_, float p_191958_2_, float p_191958_3_, float p_191958_4_) {
/* 1503 */     float f = p_191958_1_ * p_191958_1_ + p_191958_2_ * p_191958_2_ + p_191958_3_ * p_191958_3_;
/*      */     
/* 1505 */     if (f >= 1.0E-4F) {
/*      */       
/* 1507 */       f = MathHelper.sqrt(f);
/*      */       
/* 1509 */       if (f < 1.0F)
/*      */       {
/* 1511 */         f = 1.0F;
/*      */       }
/*      */       
/* 1514 */       f = p_191958_4_ / f;
/* 1515 */       p_191958_1_ *= f;
/* 1516 */       p_191958_2_ *= f;
/* 1517 */       p_191958_3_ *= f;
/* 1518 */       float f1 = MathHelper.sin(this.rotationYaw * 0.017453292F);
/* 1519 */       float f2 = MathHelper.cos(this.rotationYaw * 0.017453292F);
/* 1520 */       this.motionX += (p_191958_1_ * f2 - p_191958_3_ * f1);
/* 1521 */       this.motionY += p_191958_2_;
/* 1522 */       this.motionZ += (p_191958_3_ * f2 + p_191958_1_ * f1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBrightnessForRender() {
/* 1528 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));
/*      */     
/* 1530 */     if (this.world.isBlockLoaded((BlockPos)blockpos$mutableblockpos)) {
/*      */       
/* 1532 */       blockpos$mutableblockpos.setY(MathHelper.floor(this.posY + getEyeHeight()));
/* 1533 */       return this.world.getCombinedLight((BlockPos)blockpos$mutableblockpos, 0);
/*      */     } 
/*      */ 
/*      */     
/* 1537 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBrightness() {
/* 1546 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(MathHelper.floor(this.posX), 0, MathHelper.floor(this.posZ));
/*      */     
/* 1548 */     if (this.world.isBlockLoaded((BlockPos)blockpos$mutableblockpos)) {
/*      */       
/* 1550 */       blockpos$mutableblockpos.setY(MathHelper.floor(this.posY + getEyeHeight()));
/* 1551 */       return this.world.getLightBrightness((BlockPos)blockpos$mutableblockpos);
/*      */     } 
/*      */ 
/*      */     
/* 1555 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorld(World worldIn) {
/* 1564 */     this.world = worldIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndRotation(double x, double y, double z, float yaw, float pitch) {
/* 1572 */     this.posX = MathHelper.clamp(x, -3.0E7D, 3.0E7D);
/* 1573 */     this.posY = y;
/* 1574 */     this.posZ = MathHelper.clamp(z, -3.0E7D, 3.0E7D);
/* 1575 */     this.prevPosX = this.posX;
/* 1576 */     this.prevPosY = this.posY;
/* 1577 */     this.prevPosZ = this.posZ;
/* 1578 */     pitch = MathHelper.clamp(pitch, -90.0F, 90.0F);
/* 1579 */     this.rotationYaw = yaw;
/* 1580 */     this.rotationPitch = pitch;
/* 1581 */     this.prevRotationYaw = this.rotationYaw;
/* 1582 */     this.prevRotationPitch = this.rotationPitch;
/* 1583 */     double d0 = (this.prevRotationYaw - yaw);
/*      */     
/* 1585 */     if (d0 < -180.0D)
/*      */     {
/* 1587 */       this.prevRotationYaw += 360.0F;
/*      */     }
/*      */     
/* 1590 */     if (d0 >= 180.0D)
/*      */     {
/* 1592 */       this.prevRotationYaw -= 360.0F;
/*      */     }
/*      */     
/* 1595 */     setPosition(this.posX, this.posY, this.posZ);
/* 1596 */     setRotation(yaw, pitch);
/*      */   }
/*      */ 
/*      */   
/*      */   public void moveToBlockPosAndAngles(BlockPos pos, float rotationYawIn, float rotationPitchIn) {
/* 1601 */     setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, rotationYawIn, rotationPitchIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocationAndAngles(double x, double y, double z, float yaw, float pitch) {
/* 1609 */     this.posX = x;
/* 1610 */     this.posY = y;
/* 1611 */     this.posZ = z;
/* 1612 */     this.prevPosX = this.posX;
/* 1613 */     this.prevPosY = this.posY;
/* 1614 */     this.prevPosZ = this.posZ;
/* 1615 */     this.lastTickPosX = this.posX;
/* 1616 */     this.lastTickPosY = this.posY;
/* 1617 */     this.lastTickPosZ = this.posZ;
/* 1618 */     this.rotationYaw = yaw;
/* 1619 */     this.rotationPitch = pitch;
/* 1620 */     setPosition(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDistanceToEntity(Entity entityIn) {
/* 1628 */     float f = (float)(this.posX - entityIn.posX);
/* 1629 */     float f1 = (float)(this.posY - entityIn.posY);
/* 1630 */     float f2 = (float)(this.posZ - entityIn.posZ);
/* 1631 */     return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistanceSq(double x, double y, double z) {
/* 1639 */     double d0 = this.posX - x;
/* 1640 */     double d1 = this.posY - y;
/* 1641 */     double d2 = this.posZ - z;
/* 1642 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDistanceSq(BlockPos pos) {
/* 1647 */     return pos.distanceSq(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDistanceSqToCenter(BlockPos pos) {
/* 1652 */     return pos.distanceSqToCenter(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistance(double x, double y, double z) {
/* 1660 */     double d0 = this.posX - x;
/* 1661 */     double d1 = this.posY - y;
/* 1662 */     double d2 = this.posZ - z;
/* 1663 */     return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getDistanceSqToEntity(Entity entityIn) {
/* 1671 */     double d0 = this.posX - entityIn.posX;
/* 1672 */     double d1 = this.posY - entityIn.posY;
/* 1673 */     double d2 = this.posZ - entityIn.posZ;
/* 1674 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onCollideWithPlayer(EntityPlayer entityIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyEntityCollision(Entity entityIn) {
/* 1689 */     if (!isRidingSameEntity(entityIn))
/*      */     {
/* 1691 */       if (!entityIn.noClip && !this.noClip) {
/*      */         
/* 1693 */         double d0 = entityIn.posX - this.posX;
/* 1694 */         double d1 = entityIn.posZ - this.posZ;
/* 1695 */         double d2 = MathHelper.absMax(d0, d1);
/*      */         
/* 1697 */         if (d2 >= 0.009999999776482582D) {
/*      */           
/* 1699 */           d2 = MathHelper.sqrt(d2);
/* 1700 */           d0 /= d2;
/* 1701 */           d1 /= d2;
/* 1702 */           double d3 = 1.0D / d2;
/*      */           
/* 1704 */           if (d3 > 1.0D)
/*      */           {
/* 1706 */             d3 = 1.0D;
/*      */           }
/*      */           
/* 1709 */           d0 *= d3;
/* 1710 */           d1 *= d3;
/* 1711 */           d0 *= 0.05000000074505806D;
/* 1712 */           d1 *= 0.05000000074505806D;
/* 1713 */           d0 *= (1.0F - this.entityCollisionReduction);
/* 1714 */           d1 *= (1.0F - this.entityCollisionReduction);
/*      */           
/* 1716 */           if (!isBeingRidden())
/*      */           {
/* 1718 */             addVelocity(-d0, 0.0D, -d1);
/*      */           }
/*      */           
/* 1721 */           if (!entityIn.isBeingRidden())
/*      */           {
/* 1723 */             entityIn.addVelocity(d0, 0.0D, d1);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addVelocity(double x, double y, double z) {
/* 1735 */     this.motionX += x;
/* 1736 */     this.motionY += y;
/* 1737 */     this.motionZ += z;
/* 1738 */     this.isAirBorne = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setBeenAttacked() {
/* 1746 */     this.velocityChanged = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 1754 */     if (isEntityInvulnerable(source))
/*      */     {
/* 1756 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1760 */     setBeenAttacked();
/* 1761 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3d getLook(float partialTicks) {
/* 1770 */     if (partialTicks == 1.0F)
/*      */     {
/* 1772 */       return getVectorForRotation(this.rotationPitch, this.rotationYaw);
/*      */     }
/*      */ 
/*      */     
/* 1776 */     float f = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * partialTicks;
/* 1777 */     float f1 = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * partialTicks;
/* 1778 */     return getVectorForRotation(f, f1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected final Vec3d getVectorForRotation(float pitch, float yaw) {
/* 1787 */     float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
/* 1788 */     float f1 = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
/* 1789 */     float f2 = -MathHelper.cos(-pitch * 0.017453292F);
/* 1790 */     float f3 = MathHelper.sin(-pitch * 0.017453292F);
/* 1791 */     return new Vec3d((f1 * f2), f3, (f * f2));
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3d getPositionEyes(float partialTicks) {
/* 1796 */     if (partialTicks == 1.0F)
/*      */     {
/* 1798 */       return new Vec3d(this.posX, this.posY + getEyeHeight(), this.posZ);
/*      */     }
/*      */ 
/*      */     
/* 1802 */     double d0 = this.prevPosX + (this.posX - this.prevPosX) * partialTicks;
/* 1803 */     double d1 = this.prevPosY + (this.posY - this.prevPosY) * partialTicks + getEyeHeight();
/* 1804 */     double d2 = this.prevPosZ + (this.posZ - this.prevPosZ) * partialTicks;
/* 1805 */     return new Vec3d(d0, d1, d2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public RayTraceResult rayTrace(double blockReachDistance, float partialTicks) {
/* 1812 */     Vec3d vec3d = getPositionEyes(partialTicks);
/* 1813 */     Vec3d vec3d1 = getLook(partialTicks);
/* 1814 */     Vec3d vec3d2 = vec3d.addVector(vec3d1.xCoord * blockReachDistance, vec3d1.yCoord * blockReachDistance, vec3d1.zCoord * blockReachDistance);
/* 1815 */     return this.world.rayTraceBlocks(vec3d, vec3d2, false, false, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedWith() {
/* 1823 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/* 1831 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_191956_a(Entity p_191956_1_, int p_191956_2_, DamageSource p_191956_3_) {
/* 1836 */     if (p_191956_1_ instanceof EntityPlayerMP)
/*      */     {
/* 1838 */       CriteriaTriggers.field_192123_c.func_192211_a((EntityPlayerMP)p_191956_1_, this, p_191956_3_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInRangeToRender3d(double x, double y, double z) {
/* 1844 */     double d0 = this.posX - x;
/* 1845 */     double d1 = this.posY - y;
/* 1846 */     double d2 = this.posZ - z;
/* 1847 */     double d3 = d0 * d0 + d1 * d1 + d2 * d2;
/* 1848 */     return isInRangeToRenderDist(d3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInRangeToRenderDist(double distance) {
/* 1856 */     double d0 = getEntityBoundingBox().getAverageEdgeLength();
/*      */     
/* 1858 */     if (Double.isNaN(d0))
/*      */     {
/* 1860 */       d0 = 1.0D;
/*      */     }
/*      */     
/* 1863 */     d0 = d0 * 64.0D * renderDistanceWeight;
/* 1864 */     return (distance < d0 * d0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeToNBTAtomically(NBTTagCompound compound) {
/* 1875 */     String s = getEntityString();
/*      */     
/* 1877 */     if (!this.isDead && s != null) {
/*      */       
/* 1879 */       compound.setString("id", s);
/* 1880 */       writeToNBT(compound);
/* 1881 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1885 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean writeToNBTOptional(NBTTagCompound compound) {
/* 1896 */     String s = getEntityString();
/*      */     
/* 1898 */     if (!this.isDead && s != null && !isRiding()) {
/*      */       
/* 1900 */       compound.setString("id", s);
/* 1901 */       writeToNBT(compound);
/* 1902 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1906 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void func_190533_a(DataFixer p_190533_0_) {
/* 1912 */     p_190533_0_.registerWalker(FixTypes.ENTITY, new IDataWalker()
/*      */         {
/*      */           public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn)
/*      */           {
/* 1916 */             if (compound.hasKey("Passengers", 9)) {
/*      */               
/* 1918 */               NBTTagList nbttaglist = compound.getTagList("Passengers", 10);
/*      */               
/* 1920 */               for (int i = 0; i < nbttaglist.tagCount(); i++)
/*      */               {
/* 1922 */                 nbttaglist.set(i, (NBTBase)fixer.process((IFixType)FixTypes.ENTITY, nbttaglist.getCompoundTagAt(i), versionIn));
/*      */               }
/*      */             } 
/*      */             
/* 1926 */             return compound;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/*      */     try {
/* 1935 */       compound.setTag("Pos", (NBTBase)newDoubleNBTList(new double[] { this.posX, this.posY, this.posZ }));
/* 1936 */       compound.setTag("Motion", (NBTBase)newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
/* 1937 */       compound.setTag("Rotation", (NBTBase)newFloatNBTList(new float[] { this.rotationYaw, this.rotationPitch }));
/* 1938 */       compound.setFloat("FallDistance", this.fallDistance);
/* 1939 */       compound.setShort("Fire", (short)this.field_190534_ay);
/* 1940 */       compound.setShort("Air", (short)getAir());
/* 1941 */       compound.setBoolean("OnGround", this.onGround);
/* 1942 */       compound.setInteger("Dimension", this.dimension);
/* 1943 */       compound.setBoolean("Invulnerable", this.invulnerable);
/* 1944 */       compound.setInteger("PortalCooldown", this.timeUntilPortal);
/* 1945 */       compound.setUniqueId("UUID", getUniqueID());
/*      */       
/* 1947 */       if (hasCustomName())
/*      */       {
/* 1949 */         compound.setString("CustomName", getCustomNameTag());
/*      */       }
/*      */       
/* 1952 */       if (getAlwaysRenderNameTag())
/*      */       {
/* 1954 */         compound.setBoolean("CustomNameVisible", getAlwaysRenderNameTag());
/*      */       }
/*      */       
/* 1957 */       this.cmdResultStats.writeStatsToNBT(compound);
/*      */       
/* 1959 */       if (isSilent())
/*      */       {
/* 1961 */         compound.setBoolean("Silent", isSilent());
/*      */       }
/*      */       
/* 1964 */       if (hasNoGravity())
/*      */       {
/* 1966 */         compound.setBoolean("NoGravity", hasNoGravity());
/*      */       }
/*      */       
/* 1969 */       if (this.glowing)
/*      */       {
/* 1971 */         compound.setBoolean("Glowing", this.glowing);
/*      */       }
/*      */       
/* 1974 */       if (!this.tags.isEmpty()) {
/*      */         
/* 1976 */         NBTTagList nbttaglist = new NBTTagList();
/*      */         
/* 1978 */         for (String s : this.tags)
/*      */         {
/* 1980 */           nbttaglist.appendTag((NBTBase)new NBTTagString(s));
/*      */         }
/*      */         
/* 1983 */         compound.setTag("Tags", (NBTBase)nbttaglist);
/*      */       } 
/*      */       
/* 1986 */       writeEntityToNBT(compound);
/*      */       
/* 1988 */       if (isBeingRidden()) {
/*      */         
/* 1990 */         NBTTagList nbttaglist1 = new NBTTagList();
/*      */         
/* 1992 */         for (Entity entity : getPassengers()) {
/*      */           
/* 1994 */           NBTTagCompound nbttagcompound = new NBTTagCompound();
/*      */           
/* 1996 */           if (entity.writeToNBTAtomically(nbttagcompound))
/*      */           {
/* 1998 */             nbttaglist1.appendTag((NBTBase)nbttagcompound);
/*      */           }
/*      */         } 
/*      */         
/* 2002 */         if (!nbttaglist1.hasNoTags())
/*      */         {
/* 2004 */           compound.setTag("Passengers", (NBTBase)nbttaglist1);
/*      */         }
/*      */       } 
/*      */       
/* 2008 */       return compound;
/*      */     }
/* 2010 */     catch (Throwable throwable) {
/*      */       
/* 2012 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Saving entity NBT");
/* 2013 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being saved");
/* 2014 */       addEntityCrashInfo(crashreportcategory);
/* 2015 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void readFromNBT(NBTTagCompound compound) {
/*      */     try {
/* 2026 */       NBTTagList nbttaglist = compound.getTagList("Pos", 6);
/* 2027 */       NBTTagList nbttaglist2 = compound.getTagList("Motion", 6);
/* 2028 */       NBTTagList nbttaglist3 = compound.getTagList("Rotation", 5);
/* 2029 */       this.motionX = nbttaglist2.getDoubleAt(0);
/* 2030 */       this.motionY = nbttaglist2.getDoubleAt(1);
/* 2031 */       this.motionZ = nbttaglist2.getDoubleAt(2);
/*      */       
/* 2033 */       if (Math.abs(this.motionX) > 10.0D)
/*      */       {
/* 2035 */         this.motionX = 0.0D;
/*      */       }
/*      */       
/* 2038 */       if (Math.abs(this.motionY) > 10.0D)
/*      */       {
/* 2040 */         this.motionY = 0.0D;
/*      */       }
/*      */       
/* 2043 */       if (Math.abs(this.motionZ) > 10.0D)
/*      */       {
/* 2045 */         this.motionZ = 0.0D;
/*      */       }
/*      */       
/* 2048 */       this.posX = nbttaglist.getDoubleAt(0);
/* 2049 */       this.posY = nbttaglist.getDoubleAt(1);
/* 2050 */       this.posZ = nbttaglist.getDoubleAt(2);
/* 2051 */       this.lastTickPosX = this.posX;
/* 2052 */       this.lastTickPosY = this.posY;
/* 2053 */       this.lastTickPosZ = this.posZ;
/* 2054 */       this.prevPosX = this.posX;
/* 2055 */       this.prevPosY = this.posY;
/* 2056 */       this.prevPosZ = this.posZ;
/* 2057 */       this.rotationYaw = nbttaglist3.getFloatAt(0);
/* 2058 */       this.rotationPitch = nbttaglist3.getFloatAt(1);
/* 2059 */       this.prevRotationYaw = this.rotationYaw;
/* 2060 */       this.prevRotationPitch = this.rotationPitch;
/* 2061 */       setRotationYawHead(this.rotationYaw);
/* 2062 */       setRenderYawOffset(this.rotationYaw);
/* 2063 */       this.fallDistance = compound.getFloat("FallDistance");
/* 2064 */       this.field_190534_ay = compound.getShort("Fire");
/* 2065 */       setAir(compound.getShort("Air"));
/* 2066 */       this.onGround = compound.getBoolean("OnGround");
/*      */       
/* 2068 */       if (compound.hasKey("Dimension"))
/*      */       {
/* 2070 */         this.dimension = compound.getInteger("Dimension");
/*      */       }
/*      */       
/* 2073 */       this.invulnerable = compound.getBoolean("Invulnerable");
/* 2074 */       this.timeUntilPortal = compound.getInteger("PortalCooldown");
/*      */       
/* 2076 */       if (compound.hasUniqueId("UUID")) {
/*      */         
/* 2078 */         this.entityUniqueID = compound.getUniqueId("UUID");
/* 2079 */         this.cachedUniqueIdString = this.entityUniqueID.toString();
/*      */       } 
/*      */       
/* 2082 */       setPosition(this.posX, this.posY, this.posZ);
/* 2083 */       setRotation(this.rotationYaw, this.rotationPitch);
/*      */       
/* 2085 */       if (compound.hasKey("CustomName", 8))
/*      */       {
/* 2087 */         setCustomNameTag(compound.getString("CustomName"));
/*      */       }
/*      */       
/* 2090 */       setAlwaysRenderNameTag(compound.getBoolean("CustomNameVisible"));
/* 2091 */       this.cmdResultStats.readStatsFromNBT(compound);
/* 2092 */       setSilent(compound.getBoolean("Silent"));
/* 2093 */       setNoGravity(compound.getBoolean("NoGravity"));
/* 2094 */       setGlowing(compound.getBoolean("Glowing"));
/*      */       
/* 2096 */       if (compound.hasKey("Tags", 9)) {
/*      */         
/* 2098 */         this.tags.clear();
/* 2099 */         NBTTagList nbttaglist1 = compound.getTagList("Tags", 8);
/* 2100 */         int i = Math.min(nbttaglist1.tagCount(), 1024);
/*      */         
/* 2102 */         for (int j = 0; j < i; j++)
/*      */         {
/* 2104 */           this.tags.add(nbttaglist1.getStringTagAt(j));
/*      */         }
/*      */       } 
/*      */       
/* 2108 */       readEntityFromNBT(compound);
/*      */       
/* 2110 */       if (shouldSetPosAfterLoading())
/*      */       {
/* 2112 */         setPosition(this.posX, this.posY, this.posZ);
/*      */       }
/*      */     }
/* 2115 */     catch (Throwable throwable) {
/*      */       
/* 2117 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Loading entity NBT");
/* 2118 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being loaded");
/* 2119 */       addEntityCrashInfo(crashreportcategory);
/* 2120 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean shouldSetPosAfterLoading() {
/* 2126 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected final String getEntityString() {
/* 2136 */     ResourceLocation resourcelocation = EntityList.func_191301_a(this);
/* 2137 */     return (resourcelocation == null) ? null : resourcelocation.toString();
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
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NBTTagList newDoubleNBTList(double... numbers) {
/* 2155 */     NBTTagList nbttaglist = new NBTTagList(); byte b; int i;
/*      */     double[] arrayOfDouble;
/* 2157 */     for (i = (arrayOfDouble = numbers).length, b = 0; b < i; ) { double d0 = arrayOfDouble[b];
/*      */       
/* 2159 */       nbttaglist.appendTag((NBTBase)new NBTTagDouble(d0));
/*      */       b++; }
/*      */     
/* 2162 */     return nbttaglist;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NBTTagList newFloatNBTList(float... numbers) {
/* 2170 */     NBTTagList nbttaglist = new NBTTagList(); byte b; int i;
/*      */     float[] arrayOfFloat;
/* 2172 */     for (i = (arrayOfFloat = numbers).length, b = 0; b < i; ) { float f = arrayOfFloat[b];
/*      */       
/* 2174 */       nbttaglist.appendTag((NBTBase)new NBTTagFloat(f));
/*      */       b++; }
/*      */     
/* 2177 */     return nbttaglist;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityItem dropItem(Item itemIn, int size) {
/* 2183 */     return dropItemWithOffset(itemIn, size, 0.0F);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityItem dropItemWithOffset(Item itemIn, int size, float offsetY) {
/* 2189 */     return entityDropItem(new ItemStack(itemIn, size, 0), offsetY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityItem entityDropItem(ItemStack stack, float offsetY) {
/* 2199 */     if (stack.func_190926_b())
/*      */     {
/* 2201 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 2205 */     EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY + offsetY, this.posZ, stack);
/* 2206 */     entityitem.setDefaultPickupDelay();
/* 2207 */     this.world.spawnEntityInWorld((Entity)entityitem);
/* 2208 */     return entityitem;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityAlive() {
/* 2217 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityInsideOpaqueBlock() {
/* 2225 */     if (this.noClip)
/*      */     {
/* 2227 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2231 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/*      */     
/* 2233 */     for (int i = 0; i < 8; i++) {
/*      */       
/* 2235 */       int j = MathHelper.floor(this.posY + ((((i >> 0) % 2) - 0.5F) * 0.1F) + getEyeHeight());
/* 2236 */       int k = MathHelper.floor(this.posX + ((((i >> 1) % 2) - 0.5F) * this.width * 0.8F));
/* 2237 */       int l = MathHelper.floor(this.posZ + ((((i >> 2) % 2) - 0.5F) * this.width * 0.8F));
/*      */       
/* 2239 */       if (blockpos$pooledmutableblockpos.getX() != k || blockpos$pooledmutableblockpos.getY() != j || blockpos$pooledmutableblockpos.getZ() != l) {
/*      */         
/* 2241 */         blockpos$pooledmutableblockpos.setPos(k, j, l);
/*      */         
/* 2243 */         if (this.world.getBlockState((BlockPos)blockpos$pooledmutableblockpos).func_191058_s()) {
/*      */           
/* 2245 */           blockpos$pooledmutableblockpos.release();
/* 2246 */           return true;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2251 */     blockpos$pooledmutableblockpos.release();
/* 2252 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean processInitialInteract(EntityPlayer player, EnumHand stack) {
/* 2258 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public AxisAlignedBB getCollisionBox(Entity entityIn) {
/* 2269 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRidden() {
/* 2277 */     Entity entity = getRidingEntity();
/*      */     
/* 2279 */     if (isRiding() && entity.isDead) {
/*      */       
/* 2281 */       dismountRidingEntity();
/*      */     }
/*      */     else {
/*      */       
/* 2285 */       this.motionX = 0.0D;
/* 2286 */       this.motionY = 0.0D;
/* 2287 */       this.motionZ = 0.0D;
/* 2288 */       onUpdate();
/*      */       
/* 2290 */       if (isRiding())
/*      */       {
/* 2292 */         entity.updatePassenger(this);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updatePassenger(Entity passenger) {
/* 2299 */     if (isPassenger(passenger))
/*      */     {
/* 2301 */       passenger.setPosition(this.posX, this.posY + getMountedYOffset() + passenger.getYOffset(), this.posZ);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyOrientationToEntity(Entity entityToUpdate) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getYOffset() {
/* 2317 */     return 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMountedYOffset() {
/* 2325 */     return this.height * 0.75D;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean startRiding(Entity entityIn) {
/* 2330 */     return startRiding(entityIn, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean startRiding(Entity entityIn, boolean force) {
/* 2335 */     for (Entity entity = entityIn; entity.ridingEntity != null; entity = entity.ridingEntity) {
/*      */       
/* 2337 */       if (entity.ridingEntity == this)
/*      */       {
/* 2339 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 2343 */     if (force || (canBeRidden(entityIn) && entityIn.canFitPassenger(this))) {
/*      */       
/* 2345 */       if (isRiding())
/*      */       {
/* 2347 */         dismountRidingEntity();
/*      */       }
/*      */       
/* 2350 */       this.ridingEntity = entityIn;
/* 2351 */       this.ridingEntity.addPassenger(this);
/* 2352 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2356 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canBeRidden(Entity entityIn) {
/* 2362 */     return (this.rideCooldown <= 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void removePassengers() {
/* 2367 */     for (int i = this.riddenByEntities.size() - 1; i >= 0; i--)
/*      */     {
/* 2369 */       ((Entity)this.riddenByEntities.get(i)).dismountRidingEntity();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void dismountRidingEntity() {
/* 2375 */     if (this.ridingEntity != null) {
/*      */       
/* 2377 */       Entity entity = this.ridingEntity;
/* 2378 */       this.ridingEntity = null;
/* 2379 */       entity.removePassenger(this);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void addPassenger(Entity passenger) {
/* 2385 */     if (passenger.getRidingEntity() != this)
/*      */     {
/* 2387 */       throw new IllegalStateException("Use x.startRiding(y), not y.addPassenger(x)");
/*      */     }
/*      */ 
/*      */     
/* 2391 */     if (!this.world.isRemote && passenger instanceof EntityPlayer && !(getControllingPassenger() instanceof EntityPlayer)) {
/*      */       
/* 2393 */       this.riddenByEntities.add(0, passenger);
/*      */     }
/*      */     else {
/*      */       
/* 2397 */       this.riddenByEntities.add(passenger);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removePassenger(Entity passenger) {
/* 2404 */     if (passenger.getRidingEntity() == this)
/*      */     {
/* 2406 */       throw new IllegalStateException("Use x.stopRiding(y), not y.removePassenger(x)");
/*      */     }
/*      */ 
/*      */     
/* 2410 */     this.riddenByEntities.remove(passenger);
/* 2411 */     passenger.rideCooldown = 60;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canFitPassenger(Entity passenger) {
/* 2417 */     return (getPassengers().size() < 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/* 2425 */     setPosition(x, y, z);
/* 2426 */     setRotation(yaw, pitch);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getCollisionBorderSize() {
/* 2431 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3d getLookVec() {
/* 2439 */     return getVectorForRotation(this.rotationPitch, this.rotationYaw);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec2f getPitchYaw() {
/* 2447 */     return new Vec2f(this.rotationPitch, this.rotationYaw);
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3d getForward() {
/* 2452 */     return Vec3d.fromPitchYawVector(getPitchYaw());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPortal(BlockPos pos) {
/* 2461 */     if (this.timeUntilPortal > 0) {
/*      */       
/* 2463 */       this.timeUntilPortal = getPortalCooldown();
/*      */     }
/*      */     else {
/*      */       
/* 2467 */       if (!this.world.isRemote && !pos.equals(this.lastPortalPos)) {
/*      */         
/* 2469 */         this.lastPortalPos = new BlockPos((Vec3i)pos);
/* 2470 */         BlockPattern.PatternHelper blockpattern$patternhelper = Blocks.PORTAL.createPatternHelper(this.world, this.lastPortalPos);
/* 2471 */         double d0 = (blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X) ? blockpattern$patternhelper.getFrontTopLeft().getZ() : blockpattern$patternhelper.getFrontTopLeft().getX();
/* 2472 */         double d1 = (blockpattern$patternhelper.getForwards().getAxis() == EnumFacing.Axis.X) ? this.posZ : this.posX;
/* 2473 */         d1 = Math.abs(MathHelper.pct(d1 - ((blockpattern$patternhelper.getForwards().rotateY().getAxisDirection() == EnumFacing.AxisDirection.NEGATIVE) ? true : false), d0, d0 - blockpattern$patternhelper.getWidth()));
/* 2474 */         double d2 = MathHelper.pct(this.posY - 1.0D, blockpattern$patternhelper.getFrontTopLeft().getY(), (blockpattern$patternhelper.getFrontTopLeft().getY() - blockpattern$patternhelper.getHeight()));
/* 2475 */         this.lastPortalVec = new Vec3d(d1, d2, 0.0D);
/* 2476 */         this.teleportDirection = blockpattern$patternhelper.getForwards();
/*      */       } 
/*      */       
/* 2479 */       this.inPortal = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPortalCooldown() {
/* 2488 */     return 300;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVelocity(double x, double y, double z) {
/* 2496 */     this.motionX = x;
/* 2497 */     this.motionY = y;
/* 2498 */     this.motionZ = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleStatusUpdate(byte id) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void performHurtAnimation() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public Iterable<ItemStack> getHeldEquipment() {
/* 2514 */     return field_190535_b;
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterable<ItemStack> getArmorInventoryList() {
/* 2519 */     return field_190535_b;
/*      */   }
/*      */ 
/*      */   
/*      */   public Iterable<ItemStack> getEquipmentAndArmor() {
/* 2524 */     return Iterables.concat(getHeldEquipment(), getArmorInventoryList());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemStackToSlot(EntityEquipmentSlot slotIn, ItemStack stack) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBurning() {
/* 2536 */     boolean flag = (this.world != null && this.world.isRemote);
/* 2537 */     return (!this.isImmuneToFire && (this.field_190534_ay > 0 || (flag && getFlag(0))));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRiding() {
/* 2542 */     return (getRidingEntity() != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBeingRidden() {
/* 2550 */     return !getPassengers().isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSneaking() {
/* 2558 */     return getFlag(1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSneaking(boolean sneaking) {
/* 2566 */     setFlag(1, sneaking);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSprinting() {
/* 2574 */     return getFlag(3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSprinting(boolean sprinting) {
/* 2582 */     setFlag(3, sprinting);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isGlowing() {
/* 2587 */     return !(!this.glowing && (!this.world.isRemote || !getFlag(6)));
/*      */   }
/*      */ 
/*      */   
/*      */   public void setGlowing(boolean glowingIn) {
/* 2592 */     this.glowing = glowingIn;
/*      */     
/* 2594 */     if (!this.world.isRemote)
/*      */     {
/* 2596 */       setFlag(6, this.glowing);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isInvisible() {
/* 2602 */     return getFlag(5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInvisibleToPlayer(EntityPlayer player) {
/* 2612 */     if (player.isSpectator())
/*      */     {
/* 2614 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2618 */     Team team = getTeam();
/* 2619 */     return (team != null && player != null && player.getTeam() == team && team.getSeeFriendlyInvisiblesEnabled()) ? false : isInvisible();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Team getTeam() {
/* 2626 */     return (Team)this.world.getScoreboard().getPlayersTeam(getCachedUniqueIdString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnSameTeam(Entity entityIn) {
/* 2634 */     return isOnScoreboardTeam(entityIn.getTeam());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isOnScoreboardTeam(Team teamIn) {
/* 2642 */     return (getTeam() != null) ? getTeam().isSameTeam(teamIn) : false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInvisible(boolean invisible) {
/* 2647 */     setFlag(5, invisible);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean getFlag(int flag) {
/* 2656 */     return ((((Byte)this.dataManager.get(FLAGS)).byteValue() & 1 << flag) != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setFlag(int flag, boolean set) {
/* 2664 */     byte b0 = ((Byte)this.dataManager.get(FLAGS)).byteValue();
/*      */     
/* 2666 */     if (set) {
/*      */       
/* 2668 */       this.dataManager.set(FLAGS, Byte.valueOf((byte)(b0 | 1 << flag)));
/*      */     }
/*      */     else {
/*      */       
/* 2672 */       this.dataManager.set(FLAGS, Byte.valueOf((byte)(b0 & (1 << flag ^ 0xFFFFFFFF))));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getAir() {
/* 2678 */     return ((Integer)this.dataManager.get(AIR)).intValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAir(int air) {
/* 2683 */     this.dataManager.set(AIR, Integer.valueOf(air));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/* 2691 */     attackEntityFrom(DamageSource.lightningBolt, 5.0F);
/* 2692 */     this.field_190534_ay++;
/*      */     
/* 2694 */     if (this.field_190534_ay == 0)
/*      */     {
/* 2696 */       setFire(8);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onKillEntity(EntityLivingBase entityLivingIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean pushOutOfBlocks(double x, double y, double z) {
/* 2709 */     BlockPos blockpos = new BlockPos(x, y, z);
/* 2710 */     double d0 = x - blockpos.getX();
/* 2711 */     double d1 = y - blockpos.getY();
/* 2712 */     double d2 = z - blockpos.getZ();
/*      */     
/* 2714 */     if (!this.world.collidesWithAnyBlock(getEntityBoundingBox()))
/*      */     {
/* 2716 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2720 */     EnumFacing enumfacing = EnumFacing.UP;
/* 2721 */     double d3 = Double.MAX_VALUE;
/*      */     
/* 2723 */     if (!this.world.isBlockFullCube(blockpos.west()) && d0 < d3) {
/*      */       
/* 2725 */       d3 = d0;
/* 2726 */       enumfacing = EnumFacing.WEST;
/*      */     } 
/*      */     
/* 2729 */     if (!this.world.isBlockFullCube(blockpos.east()) && 1.0D - d0 < d3) {
/*      */       
/* 2731 */       d3 = 1.0D - d0;
/* 2732 */       enumfacing = EnumFacing.EAST;
/*      */     } 
/*      */     
/* 2735 */     if (!this.world.isBlockFullCube(blockpos.north()) && d2 < d3) {
/*      */       
/* 2737 */       d3 = d2;
/* 2738 */       enumfacing = EnumFacing.NORTH;
/*      */     } 
/*      */     
/* 2741 */     if (!this.world.isBlockFullCube(blockpos.south()) && 1.0D - d2 < d3) {
/*      */       
/* 2743 */       d3 = 1.0D - d2;
/* 2744 */       enumfacing = EnumFacing.SOUTH;
/*      */     } 
/*      */     
/* 2747 */     if (!this.world.isBlockFullCube(blockpos.up()) && 1.0D - d1 < d3) {
/*      */       
/* 2749 */       d3 = 1.0D - d1;
/* 2750 */       enumfacing = EnumFacing.UP;
/*      */     } 
/*      */     
/* 2753 */     float f = this.rand.nextFloat() * 0.2F + 0.1F;
/* 2754 */     float f1 = enumfacing.getAxisDirection().getOffset();
/*      */     
/* 2756 */     if (enumfacing.getAxis() == EnumFacing.Axis.X) {
/*      */       
/* 2758 */       this.motionX = (f1 * f);
/* 2759 */       this.motionY *= 0.75D;
/* 2760 */       this.motionZ *= 0.75D;
/*      */     }
/* 2762 */     else if (enumfacing.getAxis() == EnumFacing.Axis.Y) {
/*      */       
/* 2764 */       this.motionX *= 0.75D;
/* 2765 */       this.motionY = (f1 * f);
/* 2766 */       this.motionZ *= 0.75D;
/*      */     }
/* 2768 */     else if (enumfacing.getAxis() == EnumFacing.Axis.Z) {
/*      */       
/* 2770 */       this.motionX *= 0.75D;
/* 2771 */       this.motionY *= 0.75D;
/* 2772 */       this.motionZ = (f1 * f);
/*      */     } 
/*      */     
/* 2775 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInWeb() {
/* 2784 */     this.isInWeb = true;
/* 2785 */     this.fallDistance = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 2793 */     if (hasCustomName())
/*      */     {
/* 2795 */       return getCustomNameTag();
/*      */     }
/*      */ 
/*      */     
/* 2799 */     String s = EntityList.getEntityString(this);
/*      */     
/* 2801 */     if (s == null)
/*      */     {
/* 2803 */       s = "generic";
/*      */     }
/*      */     
/* 2806 */     return I18n.translateToLocal("entity." + s + ".name");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Entity[] getParts() {
/* 2817 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityEqual(Entity entityIn) {
/* 2825 */     return (this == entityIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getRotationYawHead() {
/* 2830 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRotationYawHead(float rotation) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRenderYawOffset(float offset) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeAttackedWithItem() {
/* 2852 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hitByEntity(Entity entityIn) {
/* 2860 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2865 */     return String.format("%s['%s'/%d, l='%s', x=%.2f, y=%.2f, z=%.2f]", new Object[] { getClass().getSimpleName(), getName(), Integer.valueOf(this.entityId), (this.world == null) ? "~NULL~" : this.world.getWorldInfo().getWorldName(), Double.valueOf(this.posX), Double.valueOf(this.posY), Double.valueOf(this.posZ) });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEntityInvulnerable(DamageSource source) {
/* 2873 */     return (this.invulnerable && source != DamageSource.outOfWorld && !source.isCreativePlayer());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_190530_aW() {
/* 2878 */     return this.invulnerable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntityInvulnerable(boolean isInvulnerable) {
/* 2886 */     this.invulnerable = isInvulnerable;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyLocationAndAnglesFrom(Entity entityIn) {
/* 2894 */     setLocationAndAngles(entityIn.posX, entityIn.posY, entityIn.posZ, entityIn.rotationYaw, entityIn.rotationPitch);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void copyDataFromOld(Entity entityIn) {
/* 2902 */     NBTTagCompound nbttagcompound = entityIn.writeToNBT(new NBTTagCompound());
/* 2903 */     nbttagcompound.removeTag("Dimension");
/* 2904 */     readFromNBT(nbttagcompound);
/* 2905 */     this.timeUntilPortal = entityIn.timeUntilPortal;
/* 2906 */     this.lastPortalPos = entityIn.lastPortalPos;
/* 2907 */     this.lastPortalVec = entityIn.lastPortalVec;
/* 2908 */     this.teleportDirection = entityIn.teleportDirection;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Entity changeDimension(int dimensionIn) {
/* 2914 */     if (!this.world.isRemote && !this.isDead) {
/*      */       BlockPos blockpos;
/* 2916 */       this.world.theProfiler.startSection("changeDimension");
/* 2917 */       MinecraftServer minecraftserver = getServer();
/* 2918 */       int i = this.dimension;
/* 2919 */       WorldServer worldserver = minecraftserver.worldServerForDimension(i);
/* 2920 */       WorldServer worldserver1 = minecraftserver.worldServerForDimension(dimensionIn);
/* 2921 */       this.dimension = dimensionIn;
/*      */       
/* 2923 */       if (i == 1 && dimensionIn == 1) {
/*      */         
/* 2925 */         worldserver1 = minecraftserver.worldServerForDimension(0);
/* 2926 */         this.dimension = 0;
/*      */       } 
/*      */       
/* 2929 */       this.world.removeEntity(this);
/* 2930 */       this.isDead = false;
/* 2931 */       this.world.theProfiler.startSection("reposition");
/*      */ 
/*      */       
/* 2934 */       if (dimensionIn == 1) {
/*      */         
/* 2936 */         blockpos = worldserver1.getSpawnCoordinate();
/*      */       }
/*      */       else {
/*      */         
/* 2940 */         double d0 = this.posX;
/* 2941 */         double d1 = this.posZ;
/* 2942 */         double d2 = 8.0D;
/*      */         
/* 2944 */         if (dimensionIn == -1) {
/*      */           
/* 2946 */           d0 = MathHelper.clamp(d0 / 8.0D, worldserver1.getWorldBorder().minX() + 16.0D, worldserver1.getWorldBorder().maxX() - 16.0D);
/* 2947 */           d1 = MathHelper.clamp(d1 / 8.0D, worldserver1.getWorldBorder().minZ() + 16.0D, worldserver1.getWorldBorder().maxZ() - 16.0D);
/*      */         }
/* 2949 */         else if (dimensionIn == 0) {
/*      */           
/* 2951 */           d0 = MathHelper.clamp(d0 * 8.0D, worldserver1.getWorldBorder().minX() + 16.0D, worldserver1.getWorldBorder().maxX() - 16.0D);
/* 2952 */           d1 = MathHelper.clamp(d1 * 8.0D, worldserver1.getWorldBorder().minZ() + 16.0D, worldserver1.getWorldBorder().maxZ() - 16.0D);
/*      */         } 
/*      */         
/* 2955 */         d0 = MathHelper.clamp((int)d0, -29999872, 29999872);
/* 2956 */         d1 = MathHelper.clamp((int)d1, -29999872, 29999872);
/* 2957 */         float f = this.rotationYaw;
/* 2958 */         setLocationAndAngles(d0, this.posY, d1, 90.0F, 0.0F);
/* 2959 */         Teleporter teleporter = worldserver1.getDefaultTeleporter();
/* 2960 */         teleporter.placeInExistingPortal(this, f);
/* 2961 */         blockpos = new BlockPos(this);
/*      */       } 
/*      */       
/* 2964 */       worldserver.updateEntityWithOptionalForce(this, false);
/* 2965 */       this.world.theProfiler.endStartSection("reloading");
/* 2966 */       Entity entity = EntityList.func_191304_a((Class)getClass(), (World)worldserver1);
/*      */       
/* 2968 */       if (entity != null) {
/*      */         
/* 2970 */         entity.copyDataFromOld(this);
/*      */         
/* 2972 */         if (i == 1 && dimensionIn == 1) {
/*      */           
/* 2974 */           BlockPos blockpos1 = worldserver1.getTopSolidOrLiquidBlock(worldserver1.getSpawnPoint());
/* 2975 */           entity.moveToBlockPosAndAngles(blockpos1, entity.rotationYaw, entity.rotationPitch);
/*      */         }
/*      */         else {
/*      */           
/* 2979 */           entity.moveToBlockPosAndAngles(blockpos, entity.rotationYaw, entity.rotationPitch);
/*      */         } 
/*      */         
/* 2982 */         boolean flag = entity.forceSpawn;
/* 2983 */         entity.forceSpawn = true;
/* 2984 */         worldserver1.spawnEntityInWorld(entity);
/* 2985 */         entity.forceSpawn = flag;
/* 2986 */         worldserver1.updateEntityWithOptionalForce(entity, false);
/*      */       } 
/*      */       
/* 2989 */       this.isDead = true;
/* 2990 */       this.world.theProfiler.endSection();
/* 2991 */       worldserver.resetUpdateEntityTick();
/* 2992 */       worldserver1.resetUpdateEntityTick();
/* 2993 */       this.world.theProfiler.endSection();
/* 2994 */       return entity;
/*      */     } 
/*      */ 
/*      */     
/* 2998 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isNonBoss() {
/* 3007 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getExplosionResistance(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn) {
/* 3015 */     return blockStateIn.getBlock().getExplosionResistance(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean verifyExplosion(Explosion explosionIn, World worldIn, BlockPos pos, IBlockState blockStateIn, float p_174816_5_) {
/* 3020 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxFallHeight() {
/* 3028 */     return 3;
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3d getLastPortalVec() {
/* 3033 */     return this.lastPortalVec;
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumFacing getTeleportDirection() {
/* 3038 */     return this.teleportDirection;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean doesEntityNotTriggerPressurePlate() {
/* 3046 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addEntityCrashInfo(CrashReportCategory category) {
/* 3051 */     category.setDetail("Entity Type", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 3055 */             return EntityList.func_191301_a(Entity.this) + " (" + Entity.this.getClass().getCanonicalName() + ")";
/*      */           }
/*      */         });
/* 3058 */     category.addCrashSection("Entity ID", Integer.valueOf(this.entityId));
/* 3059 */     category.setDetail("Entity Name", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 3063 */             return Entity.this.getName();
/*      */           }
/*      */         });
/* 3066 */     category.addCrashSection("Entity's Exact location", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.posX), Double.valueOf(this.posY), Double.valueOf(this.posZ) }));
/* 3067 */     category.addCrashSection("Entity's Block location", CrashReportCategory.getCoordinateInfo(MathHelper.floor(this.posX), MathHelper.floor(this.posY), MathHelper.floor(this.posZ)));
/* 3068 */     category.addCrashSection("Entity's Momentum", String.format("%.2f, %.2f, %.2f", new Object[] { Double.valueOf(this.motionX), Double.valueOf(this.motionY), Double.valueOf(this.motionZ) }));
/* 3069 */     category.setDetail("Entity's Passengers", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 3073 */             return Entity.this.getPassengers().toString();
/*      */           }
/*      */         });
/* 3076 */     category.setDetail("Entity's Vehicle", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 3080 */             return Entity.this.getRidingEntity().toString();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canRenderOnFire() {
/* 3090 */     return isBurning();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setUniqueId(UUID uniqueIdIn) {
/* 3095 */     this.entityUniqueID = uniqueIdIn;
/* 3096 */     this.cachedUniqueIdString = this.entityUniqueID.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public UUID getUniqueID() {
/* 3104 */     return this.entityUniqueID;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getCachedUniqueIdString() {
/* 3109 */     return this.cachedUniqueIdString;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPushedByWater() {
/* 3114 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static double getRenderDistanceWeight() {
/* 3119 */     return renderDistanceWeight;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setRenderDistanceWeight(double renderDistWeight) {
/* 3124 */     renderDistanceWeight = renderDistWeight;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ITextComponent getDisplayName() {
/* 3132 */     TextComponentString textcomponentstring = new TextComponentString(ScorePlayerTeam.formatPlayerName(getTeam(), getName()));
/* 3133 */     textcomponentstring.getStyle().setHoverEvent(getHoverEvent());
/* 3134 */     textcomponentstring.getStyle().setInsertion(getCachedUniqueIdString());
/* 3135 */     return (ITextComponent)textcomponentstring;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCustomNameTag(String name) {
/* 3143 */     this.dataManager.set(CUSTOM_NAME, name);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getCustomNameTag() {
/* 3148 */     return (String)this.dataManager.get(CUSTOM_NAME);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasCustomName() {
/* 3156 */     return !((String)this.dataManager.get(CUSTOM_NAME)).isEmpty();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAlwaysRenderNameTag(boolean alwaysRenderNameTag) {
/* 3161 */     this.dataManager.set(CUSTOM_NAME_VISIBLE, Boolean.valueOf(alwaysRenderNameTag));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAlwaysRenderNameTag() {
/* 3166 */     return ((Boolean)this.dataManager.get(CUSTOM_NAME_VISIBLE)).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndUpdate(double x, double y, double z) {
/* 3174 */     this.isPositionDirty = true;
/* 3175 */     setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
/* 3176 */     this.world.updateEntityWithOptionalForce(this, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAlwaysRenderNameTagForRender() {
/* 3181 */     return getAlwaysRenderNameTag();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void notifyDataManagerChange(DataParameter<?> key) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumFacing getHorizontalFacing() {
/* 3193 */     return EnumFacing.getHorizontal(MathHelper.floor((this.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumFacing getAdjustedHorizontalFacing() {
/* 3202 */     return getHorizontalFacing();
/*      */   }
/*      */ 
/*      */   
/*      */   protected HoverEvent getHoverEvent() {
/* 3207 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 3208 */     ResourceLocation resourcelocation = EntityList.func_191301_a(this);
/* 3209 */     nbttagcompound.setString("id", getCachedUniqueIdString());
/*      */     
/* 3211 */     if (resourcelocation != null)
/*      */     {
/* 3213 */       nbttagcompound.setString("type", resourcelocation.toString());
/*      */     }
/*      */     
/* 3216 */     nbttagcompound.setString("name", getName());
/* 3217 */     return new HoverEvent(HoverEvent.Action.SHOW_ENTITY, (ITextComponent)new TextComponentString(nbttagcompound.toString()));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSpectatedByPlayer(EntityPlayerMP player) {
/* 3222 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getEntityBoundingBox() {
/* 3227 */     return this.boundingBox;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getRenderBoundingBox() {
/* 3236 */     return getEntityBoundingBox();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setEntityBoundingBox(AxisAlignedBB bb) {
/* 3241 */     this.boundingBox = bb;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getEyeHeight() {
/* 3246 */     return this.height * 0.85F;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isOutsideBorder() {
/* 3251 */     return this.isOutsideBorder;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOutsideBorder(boolean outsideBorder) {
/* 3256 */     this.isOutsideBorder = outsideBorder;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean replaceItemInInventory(int inventorySlot, ItemStack itemStackIn) {
/* 3261 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(ITextComponent component) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 3276 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getPosition() {
/* 3285 */     return new BlockPos(this.posX, this.posY + 0.5D, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3d getPositionVector() {
/* 3294 */     return new Vec3d(this.posX, this.posY, this.posZ);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public World getEntityWorld() {
/* 3303 */     return this.world;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity getCommandSenderEntity() {
/* 3311 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sendCommandFeedback() {
/* 3319 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 3324 */     if (this.world != null && !this.world.isRemote)
/*      */     {
/* 3326 */       this.cmdResultStats.setCommandStatForSender(this.world.getMinecraftServer(), this, type, amount);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public MinecraftServer getServer() {
/* 3337 */     return this.world.getMinecraftServer();
/*      */   }
/*      */ 
/*      */   
/*      */   public CommandResultStats getCommandStats() {
/* 3342 */     return this.cmdResultStats;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCommandStats(Entity entityIn) {
/* 3350 */     this.cmdResultStats.addAllStats(entityIn.getCommandStats());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand stack) {
/* 3358 */     return EnumActionResult.PASS;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isImmuneToExplosions() {
/* 3363 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyEnchantments(EntityLivingBase entityLivingBaseIn, Entity entityIn) {
/* 3368 */     if (entityIn instanceof EntityLivingBase)
/*      */     {
/* 3370 */       EnchantmentHelper.applyThornEnchantments((EntityLivingBase)entityIn, entityLivingBaseIn);
/*      */     }
/*      */     
/* 3373 */     EnchantmentHelper.applyArthropodEnchantments(entityLivingBaseIn, entityIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addTrackingPlayer(EntityPlayerMP player) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeTrackingPlayer(EntityPlayerMP player) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRotatedYaw(Rotation transformRotation) {
/* 3397 */     float f = MathHelper.wrapDegrees(this.rotationYaw);
/*      */     
/* 3399 */     switch (transformRotation) {
/*      */       
/*      */       case null:
/* 3402 */         return f + 180.0F;
/*      */       
/*      */       case COUNTERCLOCKWISE_90:
/* 3405 */         return f + 270.0F;
/*      */       
/*      */       case CLOCKWISE_90:
/* 3408 */         return f + 90.0F;
/*      */     } 
/*      */     
/* 3411 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getMirroredYaw(Mirror transformMirror) {
/* 3420 */     float f = MathHelper.wrapDegrees(this.rotationYaw);
/*      */     
/* 3422 */     switch (transformMirror) {
/*      */       
/*      */       case LEFT_RIGHT:
/* 3425 */         return -f;
/*      */       
/*      */       case null:
/* 3428 */         return 180.0F - f;
/*      */     } 
/*      */     
/* 3431 */     return f;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean ignoreItemEntityData() {
/* 3437 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean setPositionNonDirty() {
/* 3442 */     boolean flag = this.isPositionDirty;
/* 3443 */     this.isPositionDirty = false;
/* 3444 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Entity getControllingPassenger() {
/* 3455 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Entity> getPassengers() {
/* 3460 */     return this.riddenByEntities.isEmpty() ? Collections.<Entity>emptyList() : Lists.newArrayList(this.riddenByEntities);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPassenger(Entity entityIn) {
/* 3465 */     for (Entity entity : getPassengers()) {
/*      */       
/* 3467 */       if (entity.equals(entityIn))
/*      */       {
/* 3469 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 3473 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Collection<Entity> getRecursivePassengers() {
/* 3478 */     Set<Entity> set = Sets.newHashSet();
/* 3479 */     getRecursivePassengersByType(Entity.class, set);
/* 3480 */     return set;
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Entity> Collection<T> getRecursivePassengersByType(Class<T> entityClass) {
/* 3485 */     Set<T> set = Sets.newHashSet();
/* 3486 */     getRecursivePassengersByType(entityClass, set);
/* 3487 */     return set;
/*      */   }
/*      */ 
/*      */   
/*      */   private <T extends Entity> void getRecursivePassengersByType(Class<T> entityClass, Set<T> theSet) {
/* 3492 */     for (Entity entity : getPassengers()) {
/*      */       
/* 3494 */       if (entityClass.isAssignableFrom(entity.getClass()))
/*      */       {
/* 3496 */         theSet.add((T)entity);
/*      */       }
/*      */       
/* 3499 */       entity.getRecursivePassengersByType(entityClass, theSet);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Entity getLowestRidingEntity() {
/* 3507 */     for (Entity entity = this; entity.isRiding(); entity = entity.getRidingEntity());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3512 */     return entity;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRidingSameEntity(Entity entityIn) {
/* 3517 */     return (getLowestRidingEntity() == entityIn.getLowestRidingEntity());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isRidingOrBeingRiddenBy(Entity entityIn) {
/* 3522 */     for (Entity entity : getPassengers()) {
/*      */       
/* 3524 */       if (entity.equals(entityIn))
/*      */       {
/* 3526 */         return true;
/*      */       }
/*      */       
/* 3529 */       if (entity.isRidingOrBeingRiddenBy(entityIn))
/*      */       {
/* 3531 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 3535 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canPassengerSteer() {
/* 3540 */     Entity entity = getControllingPassenger();
/*      */     
/* 3542 */     if (entity instanceof EntityPlayer)
/*      */     {
/* 3544 */       return ((EntityPlayer)entity).isUser();
/*      */     }
/*      */ 
/*      */     
/* 3548 */     return !this.world.isRemote;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Entity getRidingEntity() {
/* 3559 */     return this.ridingEntity;
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumPushReaction getPushReaction() {
/* 3564 */     return EnumPushReaction.NORMAL;
/*      */   }
/*      */ 
/*      */   
/*      */   public SoundCategory getSoundCategory() {
/* 3569 */     return SoundCategory.NEUTRAL;
/*      */   }
/*      */ 
/*      */   
/*      */   protected int func_190531_bD() {
/* 3574 */     return 1;
/*      */   }
/*      */   
/*      */   protected abstract void entityInit();
/*      */   
/*      */   protected abstract void readEntityFromNBT(NBTTagCompound paramNBTTagCompound);
/*      */   
/*      */   protected abstract void writeEntityToNBT(NBTTagCompound paramNBTTagCompound);
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\Entity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */