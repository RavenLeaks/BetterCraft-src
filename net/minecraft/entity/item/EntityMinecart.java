/*      */ package net.minecraft.entity.item;
/*      */ 
/*      */ import com.google.common.collect.Maps;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockRailBase;
/*      */ import net.minecraft.block.BlockRailPowered;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.MoverType;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.datasync.DataSerializers;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EntitySelectors;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.datafix.DataFixer;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.IWorldNameable;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public abstract class EntityMinecart
/*      */   extends Entity
/*      */   implements IWorldNameable {
/*   39 */   private static final DataParameter<Integer> ROLLING_AMPLITUDE = EntityDataManager.createKey(EntityMinecart.class, DataSerializers.VARINT);
/*   40 */   private static final DataParameter<Integer> ROLLING_DIRECTION = EntityDataManager.createKey(EntityMinecart.class, DataSerializers.VARINT);
/*   41 */   private static final DataParameter<Float> DAMAGE = EntityDataManager.createKey(EntityMinecart.class, DataSerializers.FLOAT);
/*   42 */   private static final DataParameter<Integer> DISPLAY_TILE = EntityDataManager.createKey(EntityMinecart.class, DataSerializers.VARINT);
/*   43 */   private static final DataParameter<Integer> DISPLAY_TILE_OFFSET = EntityDataManager.createKey(EntityMinecart.class, DataSerializers.VARINT);
/*   44 */   private static final DataParameter<Boolean> SHOW_BLOCK = EntityDataManager.createKey(EntityMinecart.class, DataSerializers.BOOLEAN);
/*      */   
/*      */   private boolean isInReverse;
/*      */   
/*   48 */   private static final int[][][] MATRIX = new int[][][] { { { 0, 0, -1 }, { 0, 0, 1 } }, { { -1 }, { 1 } }, { { -1, -1 }, { 1 } }, { { -1 }, { 1, -1 } }, { { 0, 0, -1 }, { 0, -1, 1 } }, { { 0, -1, -1 }, { 0, 0, 1 } }, { { 0, 0, 1 }, { 1 } }, { { 0, 0, 1 }, { -1 } }, { { 0, 0, -1 }, { -1 } }, { { 0, 0, -1 }, { 1 } } };
/*      */   
/*      */   private int turnProgress;
/*      */   
/*      */   private double minecartX;
/*      */   
/*      */   private double minecartY;
/*      */   private double minecartZ;
/*      */   private double minecartYaw;
/*      */   private double minecartPitch;
/*      */   private double velocityX;
/*      */   private double velocityY;
/*      */   private double velocityZ;
/*      */   
/*      */   public EntityMinecart(World worldIn) {
/*   63 */     super(worldIn);
/*   64 */     this.preventEntitySpawning = true;
/*   65 */     setSize(0.98F, 0.7F);
/*      */   }
/*      */ 
/*      */   
/*      */   public static EntityMinecart create(World worldIn, double x, double y, double z, Type typeIn) {
/*   70 */     switch (typeIn) {
/*      */       
/*      */       case null:
/*   73 */         return new EntityMinecartChest(worldIn, x, y, z);
/*      */       
/*      */       case FURNACE:
/*   76 */         return new EntityMinecartFurnace(worldIn, x, y, z);
/*      */       
/*      */       case TNT:
/*   79 */         return new EntityMinecartTNT(worldIn, x, y, z);
/*      */       
/*      */       case SPAWNER:
/*   82 */         return new EntityMinecartMobSpawner(worldIn, x, y, z);
/*      */       
/*      */       case HOPPER:
/*   85 */         return new EntityMinecartHopper(worldIn, x, y, z);
/*      */       
/*      */       case COMMAND_BLOCK:
/*   88 */         return new EntityMinecartCommandBlock(worldIn, x, y, z);
/*      */     } 
/*      */     
/*   91 */     return new EntityMinecartEmpty(worldIn, x, y, z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canTriggerWalking() {
/*  101 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  106 */     this.dataManager.register(ROLLING_AMPLITUDE, Integer.valueOf(0));
/*  107 */     this.dataManager.register(ROLLING_DIRECTION, Integer.valueOf(1));
/*  108 */     this.dataManager.register(DAMAGE, Float.valueOf(0.0F));
/*  109 */     this.dataManager.register(DISPLAY_TILE, Integer.valueOf(0));
/*  110 */     this.dataManager.register(DISPLAY_TILE_OFFSET, Integer.valueOf(6));
/*  111 */     this.dataManager.register(SHOW_BLOCK, Boolean.valueOf(false));
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
/*  122 */     return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public AxisAlignedBB getCollisionBoundingBox() {
/*  132 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBePushed() {
/*  140 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityMinecart(World worldIn, double x, double y, double z) {
/*  145 */     this(worldIn);
/*  146 */     setPosition(x, y, z);
/*  147 */     this.motionX = 0.0D;
/*  148 */     this.motionY = 0.0D;
/*  149 */     this.motionZ = 0.0D;
/*  150 */     this.prevPosX = x;
/*  151 */     this.prevPosY = y;
/*  152 */     this.prevPosZ = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMountedYOffset() {
/*  160 */     return 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  168 */     if (!this.world.isRemote && !this.isDead) {
/*      */       
/*  170 */       if (isEntityInvulnerable(source))
/*      */       {
/*  172 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  176 */       setRollingDirection(-getRollingDirection());
/*  177 */       setRollingAmplitude(10);
/*  178 */       setBeenAttacked();
/*  179 */       setDamage(getDamage() + amount * 10.0F);
/*  180 */       boolean flag = (source.getEntity() instanceof EntityPlayer && ((EntityPlayer)source.getEntity()).capabilities.isCreativeMode);
/*      */       
/*  182 */       if (flag || getDamage() > 40.0F) {
/*      */         
/*  184 */         removePassengers();
/*      */         
/*  186 */         if (flag && !hasCustomName()) {
/*      */           
/*  188 */           setDead();
/*      */         }
/*      */         else {
/*      */           
/*  192 */           killMinecart(source);
/*      */         } 
/*      */       } 
/*      */       
/*  196 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  201 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void killMinecart(DamageSource source) {
/*  207 */     setDead();
/*      */     
/*  209 */     if (this.world.getGameRules().getBoolean("doEntityDrops")) {
/*      */       
/*  211 */       ItemStack itemstack = new ItemStack(Items.MINECART, 1);
/*      */       
/*  213 */       if (hasCustomName())
/*      */       {
/*  215 */         itemstack.setStackDisplayName(getCustomNameTag());
/*      */       }
/*      */       
/*  218 */       entityDropItem(itemstack, 0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void performHurtAnimation() {
/*  227 */     setRollingDirection(-getRollingDirection());
/*  228 */     setRollingAmplitude(10);
/*  229 */     setDamage(getDamage() + getDamage() * 10.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedWith() {
/*  237 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumFacing getAdjustedHorizontalFacing() {
/*  246 */     return this.isInReverse ? getHorizontalFacing().getOpposite().rotateY() : getHorizontalFacing().rotateY();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  254 */     if (getRollingAmplitude() > 0)
/*      */     {
/*  256 */       setRollingAmplitude(getRollingAmplitude() - 1);
/*      */     }
/*      */     
/*  259 */     if (getDamage() > 0.0F)
/*      */     {
/*  261 */       setDamage(getDamage() - 1.0F);
/*      */     }
/*      */     
/*  264 */     if (this.posY < -64.0D)
/*      */     {
/*  266 */       kill();
/*      */     }
/*      */     
/*  269 */     if (!this.world.isRemote && this.world instanceof net.minecraft.world.WorldServer) {
/*      */       
/*  271 */       this.world.theProfiler.startSection("portal");
/*  272 */       MinecraftServer minecraftserver = this.world.getMinecraftServer();
/*  273 */       int i = getMaxInPortalTime();
/*      */       
/*  275 */       if (this.inPortal) {
/*      */         
/*  277 */         if (minecraftserver.getAllowNether())
/*      */         {
/*  279 */           if (!isRiding() && this.portalCounter++ >= i) {
/*      */             int j;
/*  281 */             this.portalCounter = i;
/*  282 */             this.timeUntilPortal = getPortalCooldown();
/*      */ 
/*      */             
/*  285 */             if (this.world.provider.getDimensionType().getId() == -1) {
/*      */               
/*  287 */               j = 0;
/*      */             }
/*      */             else {
/*      */               
/*  291 */               j = -1;
/*      */             } 
/*      */             
/*  294 */             changeDimension(j);
/*      */           } 
/*      */           
/*  297 */           this.inPortal = false;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  302 */         if (this.portalCounter > 0)
/*      */         {
/*  304 */           this.portalCounter -= 4;
/*      */         }
/*      */         
/*  307 */         if (this.portalCounter < 0)
/*      */         {
/*  309 */           this.portalCounter = 0;
/*      */         }
/*      */       } 
/*      */       
/*  313 */       if (this.timeUntilPortal > 0)
/*      */       {
/*  315 */         this.timeUntilPortal--;
/*      */       }
/*      */       
/*  318 */       this.world.theProfiler.endSection();
/*      */     } 
/*      */     
/*  321 */     if (this.world.isRemote) {
/*      */       
/*  323 */       if (this.turnProgress > 0)
/*      */       {
/*  325 */         double d4 = this.posX + (this.minecartX - this.posX) / this.turnProgress;
/*  326 */         double d5 = this.posY + (this.minecartY - this.posY) / this.turnProgress;
/*  327 */         double d6 = this.posZ + (this.minecartZ - this.posZ) / this.turnProgress;
/*  328 */         double d1 = MathHelper.wrapDegrees(this.minecartYaw - this.rotationYaw);
/*  329 */         this.rotationYaw = (float)(this.rotationYaw + d1 / this.turnProgress);
/*  330 */         this.rotationPitch = (float)(this.rotationPitch + (this.minecartPitch - this.rotationPitch) / this.turnProgress);
/*  331 */         this.turnProgress--;
/*  332 */         setPosition(d4, d5, d6);
/*  333 */         setRotation(this.rotationYaw, this.rotationPitch);
/*      */       }
/*      */       else
/*      */       {
/*  337 */         setPosition(this.posX, this.posY, this.posZ);
/*  338 */         setRotation(this.rotationYaw, this.rotationPitch);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  343 */       this.prevPosX = this.posX;
/*  344 */       this.prevPosY = this.posY;
/*  345 */       this.prevPosZ = this.posZ;
/*      */       
/*  347 */       if (!hasNoGravity())
/*      */       {
/*  349 */         this.motionY -= 0.03999999910593033D;
/*      */       }
/*      */       
/*  352 */       int k = MathHelper.floor(this.posX);
/*  353 */       int l = MathHelper.floor(this.posY);
/*  354 */       int i1 = MathHelper.floor(this.posZ);
/*      */       
/*  356 */       if (BlockRailBase.isRailBlock(this.world, new BlockPos(k, l - 1, i1)))
/*      */       {
/*  358 */         l--;
/*      */       }
/*      */       
/*  361 */       BlockPos blockpos = new BlockPos(k, l, i1);
/*  362 */       IBlockState iblockstate = this.world.getBlockState(blockpos);
/*      */       
/*  364 */       if (BlockRailBase.isRailBlock(iblockstate)) {
/*      */         
/*  366 */         moveAlongTrack(blockpos, iblockstate);
/*      */         
/*  368 */         if (iblockstate.getBlock() == Blocks.ACTIVATOR_RAIL)
/*      */         {
/*  370 */           onActivatorRailPass(k, l, i1, ((Boolean)iblockstate.getValue((IProperty)BlockRailPowered.POWERED)).booleanValue());
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  375 */         moveDerailedMinecart();
/*      */       } 
/*      */       
/*  378 */       doBlockCollisions();
/*  379 */       this.rotationPitch = 0.0F;
/*  380 */       double d0 = this.prevPosX - this.posX;
/*  381 */       double d2 = this.prevPosZ - this.posZ;
/*      */       
/*  383 */       if (d0 * d0 + d2 * d2 > 0.001D) {
/*      */         
/*  385 */         this.rotationYaw = (float)(MathHelper.atan2(d2, d0) * 180.0D / Math.PI);
/*      */         
/*  387 */         if (this.isInReverse)
/*      */         {
/*  389 */           this.rotationYaw += 180.0F;
/*      */         }
/*      */       } 
/*      */       
/*  393 */       double d3 = MathHelper.wrapDegrees(this.rotationYaw - this.prevRotationYaw);
/*      */       
/*  395 */       if (d3 < -170.0D || d3 >= 170.0D) {
/*      */         
/*  397 */         this.rotationYaw += 180.0F;
/*  398 */         this.isInReverse = !this.isInReverse;
/*      */       } 
/*      */       
/*  401 */       setRotation(this.rotationYaw, this.rotationPitch);
/*      */       
/*  403 */       if (getType() == Type.RIDEABLE && this.motionX * this.motionX + this.motionZ * this.motionZ > 0.01D) {
/*      */         
/*  405 */         List<Entity> list = this.world.getEntitiesInAABBexcluding(this, getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D), EntitySelectors.getTeamCollisionPredicate(this));
/*      */         
/*  407 */         if (!list.isEmpty())
/*      */         {
/*  409 */           for (int j1 = 0; j1 < list.size(); j1++) {
/*      */             
/*  411 */             Entity entity1 = list.get(j1);
/*      */             
/*  413 */             if (!(entity1 instanceof EntityPlayer) && !(entity1 instanceof net.minecraft.entity.monster.EntityIronGolem) && !(entity1 instanceof EntityMinecart) && !isBeingRidden() && !entity1.isRiding())
/*      */             {
/*  415 */               entity1.startRiding(this);
/*      */             }
/*      */             else
/*      */             {
/*  419 */               entity1.applyEntityCollision(this);
/*      */             }
/*      */           
/*      */           } 
/*      */         }
/*      */       } else {
/*      */         
/*  426 */         for (Entity entity : this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().expand(0.20000000298023224D, 0.0D, 0.20000000298023224D))) {
/*      */           
/*  428 */           if (!isPassenger(entity) && entity.canBePushed() && entity instanceof EntityMinecart)
/*      */           {
/*  430 */             entity.applyEntityCollision(this);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  435 */       handleWaterMovement();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected double getMaximumSpeed() {
/*  444 */     return 0.4D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onActivatorRailPass(int x, int y, int z, boolean receivingPower) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void moveDerailedMinecart() {
/*  459 */     double d0 = getMaximumSpeed();
/*  460 */     this.motionX = MathHelper.clamp(this.motionX, -d0, d0);
/*  461 */     this.motionZ = MathHelper.clamp(this.motionZ, -d0, d0);
/*      */     
/*  463 */     if (this.onGround) {
/*      */       
/*  465 */       this.motionX *= 0.5D;
/*  466 */       this.motionY *= 0.5D;
/*  467 */       this.motionZ *= 0.5D;
/*      */     } 
/*      */     
/*  470 */     moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/*      */     
/*  472 */     if (!this.onGround) {
/*      */       
/*  474 */       this.motionX *= 0.949999988079071D;
/*  475 */       this.motionY *= 0.949999988079071D;
/*  476 */       this.motionZ *= 0.949999988079071D;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void moveAlongTrack(BlockPos pos, IBlockState state) {
/*      */     double d10;
/*  483 */     this.fallDistance = 0.0F;
/*  484 */     Vec3d vec3d = getPos(this.posX, this.posY, this.posZ);
/*  485 */     this.posY = pos.getY();
/*  486 */     boolean flag = false;
/*  487 */     boolean flag1 = false;
/*  488 */     BlockRailBase blockrailbase = (BlockRailBase)state.getBlock();
/*      */     
/*  490 */     if (blockrailbase == Blocks.GOLDEN_RAIL) {
/*      */       
/*  492 */       flag = ((Boolean)state.getValue((IProperty)BlockRailPowered.POWERED)).booleanValue();
/*  493 */       flag1 = !flag;
/*      */     } 
/*      */     
/*  496 */     double d0 = 0.0078125D;
/*  497 */     BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)state.getValue(blockrailbase.getShapeProperty());
/*      */     
/*  499 */     switch (blockrailbase$enumraildirection) {
/*      */       
/*      */       case null:
/*  502 */         this.motionX -= 0.0078125D;
/*  503 */         this.posY++;
/*      */         break;
/*      */       
/*      */       case ASCENDING_WEST:
/*  507 */         this.motionX += 0.0078125D;
/*  508 */         this.posY++;
/*      */         break;
/*      */       
/*      */       case ASCENDING_NORTH:
/*  512 */         this.motionZ += 0.0078125D;
/*  513 */         this.posY++;
/*      */         break;
/*      */       
/*      */       case ASCENDING_SOUTH:
/*  517 */         this.motionZ -= 0.0078125D;
/*  518 */         this.posY++;
/*      */         break;
/*      */     } 
/*  521 */     int[][] aint = MATRIX[blockrailbase$enumraildirection.getMetadata()];
/*  522 */     double d1 = (aint[1][0] - aint[0][0]);
/*  523 */     double d2 = (aint[1][2] - aint[0][2]);
/*  524 */     double d3 = Math.sqrt(d1 * d1 + d2 * d2);
/*  525 */     double d4 = this.motionX * d1 + this.motionZ * d2;
/*      */     
/*  527 */     if (d4 < 0.0D) {
/*      */       
/*  529 */       d1 = -d1;
/*  530 */       d2 = -d2;
/*      */     } 
/*      */     
/*  533 */     double d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*      */     
/*  535 */     if (d5 > 2.0D)
/*      */     {
/*  537 */       d5 = 2.0D;
/*      */     }
/*      */     
/*  540 */     this.motionX = d5 * d1 / d3;
/*  541 */     this.motionZ = d5 * d2 / d3;
/*  542 */     Entity entity = getPassengers().isEmpty() ? null : getPassengers().get(0);
/*      */     
/*  544 */     if (entity instanceof EntityLivingBase) {
/*      */       
/*  546 */       double d6 = ((EntityLivingBase)entity).field_191988_bg;
/*      */       
/*  548 */       if (d6 > 0.0D) {
/*      */         
/*  550 */         double d7 = -Math.sin((entity.rotationYaw * 0.017453292F));
/*  551 */         double d8 = Math.cos((entity.rotationYaw * 0.017453292F));
/*  552 */         double d9 = this.motionX * this.motionX + this.motionZ * this.motionZ;
/*      */         
/*  554 */         if (d9 < 0.01D) {
/*      */           
/*  556 */           this.motionX += d7 * 0.1D;
/*  557 */           this.motionZ += d8 * 0.1D;
/*  558 */           flag1 = false;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  563 */     if (flag1) {
/*      */       
/*  565 */       double d17 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*      */       
/*  567 */       if (d17 < 0.03D) {
/*      */         
/*  569 */         this.motionX *= 0.0D;
/*  570 */         this.motionY *= 0.0D;
/*  571 */         this.motionZ *= 0.0D;
/*      */       }
/*      */       else {
/*      */         
/*  575 */         this.motionX *= 0.5D;
/*  576 */         this.motionY *= 0.0D;
/*  577 */         this.motionZ *= 0.5D;
/*      */       } 
/*      */     } 
/*      */     
/*  581 */     double d18 = pos.getX() + 0.5D + aint[0][0] * 0.5D;
/*  582 */     double d19 = pos.getZ() + 0.5D + aint[0][2] * 0.5D;
/*  583 */     double d20 = pos.getX() + 0.5D + aint[1][0] * 0.5D;
/*  584 */     double d21 = pos.getZ() + 0.5D + aint[1][2] * 0.5D;
/*  585 */     d1 = d20 - d18;
/*  586 */     d2 = d21 - d19;
/*      */ 
/*      */     
/*  589 */     if (d1 == 0.0D) {
/*      */       
/*  591 */       this.posX = pos.getX() + 0.5D;
/*  592 */       d10 = this.posZ - pos.getZ();
/*      */     }
/*  594 */     else if (d2 == 0.0D) {
/*      */       
/*  596 */       this.posZ = pos.getZ() + 0.5D;
/*  597 */       d10 = this.posX - pos.getX();
/*      */     }
/*      */     else {
/*      */       
/*  601 */       double d11 = this.posX - d18;
/*  602 */       double d12 = this.posZ - d19;
/*  603 */       d10 = (d11 * d1 + d12 * d2) * 2.0D;
/*      */     } 
/*      */     
/*  606 */     this.posX = d18 + d1 * d10;
/*  607 */     this.posZ = d19 + d2 * d10;
/*  608 */     setPosition(this.posX, this.posY, this.posZ);
/*  609 */     double d22 = this.motionX;
/*  610 */     double d23 = this.motionZ;
/*      */     
/*  612 */     if (isBeingRidden()) {
/*      */       
/*  614 */       d22 *= 0.75D;
/*  615 */       d23 *= 0.75D;
/*      */     } 
/*      */     
/*  618 */     double d13 = getMaximumSpeed();
/*  619 */     d22 = MathHelper.clamp(d22, -d13, d13);
/*  620 */     d23 = MathHelper.clamp(d23, -d13, d13);
/*  621 */     moveEntity(MoverType.SELF, d22, 0.0D, d23);
/*      */     
/*  623 */     if (aint[0][1] != 0 && MathHelper.floor(this.posX) - pos.getX() == aint[0][0] && MathHelper.floor(this.posZ) - pos.getZ() == aint[0][2]) {
/*      */       
/*  625 */       setPosition(this.posX, this.posY + aint[0][1], this.posZ);
/*      */     }
/*  627 */     else if (aint[1][1] != 0 && MathHelper.floor(this.posX) - pos.getX() == aint[1][0] && MathHelper.floor(this.posZ) - pos.getZ() == aint[1][2]) {
/*      */       
/*  629 */       setPosition(this.posX, this.posY + aint[1][1], this.posZ);
/*      */     } 
/*      */     
/*  632 */     applyDrag();
/*  633 */     Vec3d vec3d1 = getPos(this.posX, this.posY, this.posZ);
/*      */     
/*  635 */     if (vec3d1 != null && vec3d != null) {
/*      */       
/*  637 */       double d14 = (vec3d.yCoord - vec3d1.yCoord) * 0.05D;
/*  638 */       d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*      */       
/*  640 */       if (d5 > 0.0D) {
/*      */         
/*  642 */         this.motionX = this.motionX / d5 * (d5 + d14);
/*  643 */         this.motionZ = this.motionZ / d5 * (d5 + d14);
/*      */       } 
/*      */       
/*  646 */       setPosition(this.posX, vec3d1.yCoord, this.posZ);
/*      */     } 
/*      */     
/*  649 */     int j = MathHelper.floor(this.posX);
/*  650 */     int i = MathHelper.floor(this.posZ);
/*      */     
/*  652 */     if (j != pos.getX() || i != pos.getZ()) {
/*      */       
/*  654 */       d5 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  655 */       this.motionX = d5 * (j - pos.getX());
/*  656 */       this.motionZ = d5 * (i - pos.getZ());
/*      */     } 
/*      */     
/*  659 */     if (flag) {
/*      */       
/*  661 */       double d15 = Math.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*      */       
/*  663 */       if (d15 > 0.01D) {
/*      */         
/*  665 */         double d16 = 0.06D;
/*  666 */         this.motionX += this.motionX / d15 * 0.06D;
/*  667 */         this.motionZ += this.motionZ / d15 * 0.06D;
/*      */       }
/*  669 */       else if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
/*      */         
/*  671 */         if (this.world.getBlockState(pos.west()).isNormalCube())
/*      */         {
/*  673 */           this.motionX = 0.02D;
/*      */         }
/*  675 */         else if (this.world.getBlockState(pos.east()).isNormalCube())
/*      */         {
/*  677 */           this.motionX = -0.02D;
/*      */         }
/*      */       
/*  680 */       } else if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
/*      */         
/*  682 */         if (this.world.getBlockState(pos.north()).isNormalCube()) {
/*      */           
/*  684 */           this.motionZ = 0.02D;
/*      */         }
/*  686 */         else if (this.world.getBlockState(pos.south()).isNormalCube()) {
/*      */           
/*  688 */           this.motionZ = -0.02D;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void applyDrag() {
/*  696 */     if (isBeingRidden()) {
/*      */       
/*  698 */       this.motionX *= 0.996999979019165D;
/*  699 */       this.motionY *= 0.0D;
/*  700 */       this.motionZ *= 0.996999979019165D;
/*      */     }
/*      */     else {
/*      */       
/*  704 */       this.motionX *= 0.9599999785423279D;
/*  705 */       this.motionY *= 0.0D;
/*  706 */       this.motionZ *= 0.9599999785423279D;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPosition(double x, double y, double z) {
/*  715 */     this.posX = x;
/*  716 */     this.posY = y;
/*  717 */     this.posZ = z;
/*  718 */     float f = this.width / 2.0F;
/*  719 */     float f1 = this.height;
/*  720 */     setEntityBoundingBox(new AxisAlignedBB(x - f, y, z - f, x + f, y + f1, z + f));
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Vec3d getPosOffset(double x, double y, double z, double offset) {
/*  726 */     int i = MathHelper.floor(x);
/*  727 */     int j = MathHelper.floor(y);
/*  728 */     int k = MathHelper.floor(z);
/*      */     
/*  730 */     if (BlockRailBase.isRailBlock(this.world, new BlockPos(i, j - 1, k)))
/*      */     {
/*  732 */       j--;
/*      */     }
/*      */     
/*  735 */     IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));
/*      */     
/*  737 */     if (BlockRailBase.isRailBlock(iblockstate)) {
/*      */       
/*  739 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)iblockstate.getValue(((BlockRailBase)iblockstate.getBlock()).getShapeProperty());
/*  740 */       y = j;
/*      */       
/*  742 */       if (blockrailbase$enumraildirection.isAscending())
/*      */       {
/*  744 */         y = (j + 1);
/*      */       }
/*      */       
/*  747 */       int[][] aint = MATRIX[blockrailbase$enumraildirection.getMetadata()];
/*  748 */       double d0 = (aint[1][0] - aint[0][0]);
/*  749 */       double d1 = (aint[1][2] - aint[0][2]);
/*  750 */       double d2 = Math.sqrt(d0 * d0 + d1 * d1);
/*  751 */       d0 /= d2;
/*  752 */       d1 /= d2;
/*  753 */       x += d0 * offset;
/*  754 */       z += d1 * offset;
/*      */       
/*  756 */       if (aint[0][1] != 0 && MathHelper.floor(x) - i == aint[0][0] && MathHelper.floor(z) - k == aint[0][2]) {
/*      */         
/*  758 */         y += aint[0][1];
/*      */       }
/*  760 */       else if (aint[1][1] != 0 && MathHelper.floor(x) - i == aint[1][0] && MathHelper.floor(z) - k == aint[1][2]) {
/*      */         
/*  762 */         y += aint[1][1];
/*      */       } 
/*      */       
/*  765 */       return getPos(x, y, z);
/*      */     } 
/*      */ 
/*      */     
/*  769 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Vec3d getPos(double p_70489_1_, double p_70489_3_, double p_70489_5_) {
/*  776 */     int i = MathHelper.floor(p_70489_1_);
/*  777 */     int j = MathHelper.floor(p_70489_3_);
/*  778 */     int k = MathHelper.floor(p_70489_5_);
/*      */     
/*  780 */     if (BlockRailBase.isRailBlock(this.world, new BlockPos(i, j - 1, k)))
/*      */     {
/*  782 */       j--;
/*      */     }
/*      */     
/*  785 */     IBlockState iblockstate = this.world.getBlockState(new BlockPos(i, j, k));
/*      */     
/*  787 */     if (BlockRailBase.isRailBlock(iblockstate)) {
/*      */       double d9;
/*  789 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)iblockstate.getValue(((BlockRailBase)iblockstate.getBlock()).getShapeProperty());
/*  790 */       int[][] aint = MATRIX[blockrailbase$enumraildirection.getMetadata()];
/*  791 */       double d0 = i + 0.5D + aint[0][0] * 0.5D;
/*  792 */       double d1 = j + 0.0625D + aint[0][1] * 0.5D;
/*  793 */       double d2 = k + 0.5D + aint[0][2] * 0.5D;
/*  794 */       double d3 = i + 0.5D + aint[1][0] * 0.5D;
/*  795 */       double d4 = j + 0.0625D + aint[1][1] * 0.5D;
/*  796 */       double d5 = k + 0.5D + aint[1][2] * 0.5D;
/*  797 */       double d6 = d3 - d0;
/*  798 */       double d7 = (d4 - d1) * 2.0D;
/*  799 */       double d8 = d5 - d2;
/*      */ 
/*      */       
/*  802 */       if (d6 == 0.0D) {
/*      */         
/*  804 */         d9 = p_70489_5_ - k;
/*      */       }
/*  806 */       else if (d8 == 0.0D) {
/*      */         
/*  808 */         d9 = p_70489_1_ - i;
/*      */       }
/*      */       else {
/*      */         
/*  812 */         double d10 = p_70489_1_ - d0;
/*  813 */         double d11 = p_70489_5_ - d2;
/*  814 */         d9 = (d10 * d6 + d11 * d8) * 2.0D;
/*      */       } 
/*      */       
/*  817 */       p_70489_1_ = d0 + d6 * d9;
/*  818 */       p_70489_3_ = d1 + d7 * d9;
/*  819 */       p_70489_5_ = d2 + d8 * d9;
/*      */       
/*  821 */       if (d7 < 0.0D)
/*      */       {
/*  823 */         p_70489_3_++;
/*      */       }
/*      */       
/*  826 */       if (d7 > 0.0D)
/*      */       {
/*  828 */         p_70489_3_ += 0.5D;
/*      */       }
/*      */       
/*  831 */       return new Vec3d(p_70489_1_, p_70489_3_, p_70489_5_);
/*      */     } 
/*      */ 
/*      */     
/*  835 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AxisAlignedBB getRenderBoundingBox() {
/*  845 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/*  846 */     return hasDisplayTile() ? axisalignedbb.expandXyz(Math.abs(getDisplayTileOffset()) / 16.0D) : axisalignedbb;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registerFixesMinecart(DataFixer fixer, Class<?> name) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void readEntityFromNBT(NBTTagCompound compound) {
/*  858 */     if (compound.getBoolean("CustomDisplayTile")) {
/*      */       Block block;
/*      */ 
/*      */       
/*  862 */       if (compound.hasKey("DisplayTile", 8)) {
/*      */         
/*  864 */         block = Block.getBlockFromName(compound.getString("DisplayTile"));
/*      */       }
/*      */       else {
/*      */         
/*  868 */         block = Block.getBlockById(compound.getInteger("DisplayTile"));
/*      */       } 
/*      */       
/*  871 */       int i = compound.getInteger("DisplayData");
/*  872 */       setDisplayTile((block == null) ? Blocks.AIR.getDefaultState() : block.getStateFromMeta(i));
/*  873 */       setDisplayTileOffset(compound.getInteger("DisplayOffset"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeEntityToNBT(NBTTagCompound compound) {
/*  882 */     if (hasDisplayTile()) {
/*      */       
/*  884 */       compound.setBoolean("CustomDisplayTile", true);
/*  885 */       IBlockState iblockstate = getDisplayTile();
/*  886 */       ResourceLocation resourcelocation = (ResourceLocation)Block.REGISTRY.getNameForObject(iblockstate.getBlock());
/*  887 */       compound.setString("DisplayTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/*  888 */       compound.setInteger("DisplayData", iblockstate.getBlock().getMetaFromState(iblockstate));
/*  889 */       compound.setInteger("DisplayOffset", getDisplayTileOffset());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyEntityCollision(Entity entityIn) {
/*  898 */     if (!this.world.isRemote)
/*      */     {
/*  900 */       if (!entityIn.noClip && !this.noClip)
/*      */       {
/*  902 */         if (!isPassenger(entityIn)) {
/*      */           
/*  904 */           double d0 = entityIn.posX - this.posX;
/*  905 */           double d1 = entityIn.posZ - this.posZ;
/*  906 */           double d2 = d0 * d0 + d1 * d1;
/*      */           
/*  908 */           if (d2 >= 9.999999747378752E-5D) {
/*      */             
/*  910 */             d2 = MathHelper.sqrt(d2);
/*  911 */             d0 /= d2;
/*  912 */             d1 /= d2;
/*  913 */             double d3 = 1.0D / d2;
/*      */             
/*  915 */             if (d3 > 1.0D)
/*      */             {
/*  917 */               d3 = 1.0D;
/*      */             }
/*      */             
/*  920 */             d0 *= d3;
/*  921 */             d1 *= d3;
/*  922 */             d0 *= 0.10000000149011612D;
/*  923 */             d1 *= 0.10000000149011612D;
/*  924 */             d0 *= (1.0F - this.entityCollisionReduction);
/*  925 */             d1 *= (1.0F - this.entityCollisionReduction);
/*  926 */             d0 *= 0.5D;
/*  927 */             d1 *= 0.5D;
/*      */             
/*  929 */             if (entityIn instanceof EntityMinecart) {
/*      */               
/*  931 */               double d4 = entityIn.posX - this.posX;
/*  932 */               double d5 = entityIn.posZ - this.posZ;
/*  933 */               Vec3d vec3d = (new Vec3d(d4, 0.0D, d5)).normalize();
/*  934 */               Vec3d vec3d1 = (new Vec3d(MathHelper.cos(this.rotationYaw * 0.017453292F), 0.0D, MathHelper.sin(this.rotationYaw * 0.017453292F))).normalize();
/*  935 */               double d6 = Math.abs(vec3d.dotProduct(vec3d1));
/*      */               
/*  937 */               if (d6 < 0.800000011920929D) {
/*      */                 return;
/*      */               }
/*      */ 
/*      */               
/*  942 */               double d7 = entityIn.motionX + this.motionX;
/*  943 */               double d8 = entityIn.motionZ + this.motionZ;
/*      */               
/*  945 */               if (((EntityMinecart)entityIn).getType() == Type.FURNACE && getType() != Type.FURNACE)
/*      */               {
/*  947 */                 this.motionX *= 0.20000000298023224D;
/*  948 */                 this.motionZ *= 0.20000000298023224D;
/*  949 */                 addVelocity(entityIn.motionX - d0, 0.0D, entityIn.motionZ - d1);
/*  950 */                 entityIn.motionX *= 0.949999988079071D;
/*  951 */                 entityIn.motionZ *= 0.949999988079071D;
/*      */               }
/*  953 */               else if (((EntityMinecart)entityIn).getType() != Type.FURNACE && getType() == Type.FURNACE)
/*      */               {
/*  955 */                 entityIn.motionX *= 0.20000000298023224D;
/*  956 */                 entityIn.motionZ *= 0.20000000298023224D;
/*  957 */                 entityIn.addVelocity(this.motionX + d0, 0.0D, this.motionZ + d1);
/*  958 */                 this.motionX *= 0.949999988079071D;
/*  959 */                 this.motionZ *= 0.949999988079071D;
/*      */               }
/*      */               else
/*      */               {
/*  963 */                 d7 /= 2.0D;
/*  964 */                 d8 /= 2.0D;
/*  965 */                 this.motionX *= 0.20000000298023224D;
/*  966 */                 this.motionZ *= 0.20000000298023224D;
/*  967 */                 addVelocity(d7 - d0, 0.0D, d8 - d1);
/*  968 */                 entityIn.motionX *= 0.20000000298023224D;
/*  969 */                 entityIn.motionZ *= 0.20000000298023224D;
/*  970 */                 entityIn.addVelocity(d7 + d0, 0.0D, d8 + d1);
/*      */               }
/*      */             
/*      */             } else {
/*      */               
/*  975 */               addVelocity(-d0, 0.0D, -d1);
/*  976 */               entityIn.addVelocity(d0 / 4.0D, 0.0D, d1 / 4.0D);
/*      */             } 
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
/*      */   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/*  989 */     this.minecartX = x;
/*  990 */     this.minecartY = y;
/*  991 */     this.minecartZ = z;
/*  992 */     this.minecartYaw = yaw;
/*  993 */     this.minecartPitch = pitch;
/*  994 */     this.turnProgress = posRotationIncrements + 2;
/*  995 */     this.motionX = this.velocityX;
/*  996 */     this.motionY = this.velocityY;
/*  997 */     this.motionZ = this.velocityZ;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVelocity(double x, double y, double z) {
/* 1005 */     this.motionX = x;
/* 1006 */     this.motionY = y;
/* 1007 */     this.motionZ = z;
/* 1008 */     this.velocityX = this.motionX;
/* 1009 */     this.velocityY = this.motionY;
/* 1010 */     this.velocityZ = this.motionZ;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDamage(float damage) {
/* 1019 */     this.dataManager.set(DAMAGE, Float.valueOf(damage));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDamage() {
/* 1028 */     return ((Float)this.dataManager.get(DAMAGE)).floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRollingAmplitude(int rollingAmplitude) {
/* 1036 */     this.dataManager.set(ROLLING_AMPLITUDE, Integer.valueOf(rollingAmplitude));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRollingAmplitude() {
/* 1044 */     return ((Integer)this.dataManager.get(ROLLING_AMPLITUDE)).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRollingDirection(int rollingDirection) {
/* 1052 */     this.dataManager.set(ROLLING_DIRECTION, Integer.valueOf(rollingDirection));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRollingDirection() {
/* 1060 */     return ((Integer)this.dataManager.get(ROLLING_DIRECTION)).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState getDisplayTile() {
/* 1067 */     return !hasDisplayTile() ? getDefaultDisplayTile() : Block.getStateById(((Integer)getDataManager().get(DISPLAY_TILE)).intValue());
/*      */   }
/*      */ 
/*      */   
/*      */   public IBlockState getDefaultDisplayTile() {
/* 1072 */     return Blocks.AIR.getDefaultState();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDisplayTileOffset() {
/* 1077 */     return !hasDisplayTile() ? getDefaultDisplayTileOffset() : ((Integer)getDataManager().get(DISPLAY_TILE_OFFSET)).intValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getDefaultDisplayTileOffset() {
/* 1082 */     return 6;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDisplayTile(IBlockState displayTile) {
/* 1087 */     getDataManager().set(DISPLAY_TILE, Integer.valueOf(Block.getStateId(displayTile)));
/* 1088 */     setHasDisplayTile(true);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDisplayTileOffset(int displayTileOffset) {
/* 1093 */     getDataManager().set(DISPLAY_TILE_OFFSET, Integer.valueOf(displayTileOffset));
/* 1094 */     setHasDisplayTile(true);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasDisplayTile() {
/* 1099 */     return ((Boolean)getDataManager().get(SHOW_BLOCK)).booleanValue();
/*      */   }
/*      */   public abstract Type getType();
/*      */   
/*      */   public void setHasDisplayTile(boolean showBlock) {
/* 1104 */     getDataManager().set(SHOW_BLOCK, Boolean.valueOf(showBlock));
/*      */   }
/*      */   
/*      */   public enum Type
/*      */   {
/* 1109 */     RIDEABLE(0, "MinecartRideable"),
/* 1110 */     CHEST(1, "MinecartChest"),
/* 1111 */     FURNACE(2, "MinecartFurnace"),
/* 1112 */     TNT(3, "MinecartTNT"),
/* 1113 */     SPAWNER(4, "MinecartSpawner"),
/* 1114 */     HOPPER(5, "MinecartHopper"),
/* 1115 */     COMMAND_BLOCK(6, "MinecartCommandBlock");
/*      */     
/* 1117 */     private static final Map<Integer, Type> BY_ID = Maps.newHashMap();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final int id;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private final String name;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/*      */       byte b;
/*      */       int i;
/*      */       Type[] arrayOfType;
/* 1144 */       for (i = (arrayOfType = values()).length, b = 0; b < i; ) { Type entityminecart$type = arrayOfType[b];
/*      */         
/* 1146 */         BY_ID.put(Integer.valueOf(entityminecart$type.getId()), entityminecart$type);
/*      */         b++; }
/*      */     
/*      */     }
/*      */     
/*      */     Type(int idIn, String nameIn) {
/*      */       this.id = idIn;
/*      */       this.name = nameIn;
/*      */     }
/*      */     
/*      */     public int getId() {
/*      */       return this.id;
/*      */     }
/*      */     
/*      */     public String getName() {
/*      */       return this.name;
/*      */     }
/*      */     
/*      */     public static Type getById(int idIn) {
/*      */       Type entityminecart$type = BY_ID.get(Integer.valueOf(idIn));
/*      */       return (entityminecart$type == null) ? RIDEABLE : entityminecart$type;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityMinecart.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */