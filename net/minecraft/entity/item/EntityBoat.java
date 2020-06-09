/*      */ package net.minecraft.entity.item;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.List;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.BlockLiquid;
/*      */ import net.minecraft.block.BlockPlanks;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.MoverType;
/*      */ import net.minecraft.entity.passive.EntityAnimal;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.init.SoundEvents;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.datasync.DataParameter;
/*      */ import net.minecraft.network.datasync.DataSerializers;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.network.play.client.CPacketSteerBoat;
/*      */ import net.minecraft.util.DamageSource;
/*      */ import net.minecraft.util.EntitySelectors;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.SoundEvent;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ 
/*      */ public class EntityBoat
/*      */   extends Entity {
/*   40 */   private static final DataParameter<Integer> TIME_SINCE_HIT = EntityDataManager.createKey(EntityBoat.class, DataSerializers.VARINT);
/*   41 */   private static final DataParameter<Integer> FORWARD_DIRECTION = EntityDataManager.createKey(EntityBoat.class, DataSerializers.VARINT);
/*   42 */   private static final DataParameter<Float> DAMAGE_TAKEN = EntityDataManager.createKey(EntityBoat.class, DataSerializers.FLOAT);
/*   43 */   private static final DataParameter<Integer> BOAT_TYPE = EntityDataManager.createKey(EntityBoat.class, DataSerializers.VARINT);
/*   44 */   private static final DataParameter<Boolean>[] DATA_ID_PADDLE = new DataParameter[] { EntityDataManager.createKey(EntityBoat.class, DataSerializers.BOOLEAN), EntityDataManager.createKey(EntityBoat.class, DataSerializers.BOOLEAN) };
/*      */   
/*      */   private final float[] paddlePositions;
/*      */   
/*      */   private float momentum;
/*      */   
/*      */   private float outOfControlTicks;
/*      */   
/*      */   private float deltaRotation;
/*      */   
/*      */   private int lerpSteps;
/*      */   
/*      */   private double boatPitch;
/*      */   
/*      */   private double lerpY;
/*      */   
/*      */   private double lerpZ;
/*      */   private double boatYaw;
/*      */   private double lerpXRot;
/*      */   private boolean leftInputDown;
/*      */   private boolean rightInputDown;
/*      */   private boolean forwardInputDown;
/*      */   private boolean backInputDown;
/*      */   private double waterLevel;
/*      */   private float boatGlide;
/*      */   private Status status;
/*      */   private Status previousStatus;
/*      */   private double lastYd;
/*      */   
/*      */   public EntityBoat(World worldIn) {
/*   74 */     super(worldIn);
/*   75 */     this.paddlePositions = new float[2];
/*   76 */     this.preventEntitySpawning = true;
/*   77 */     setSize(1.375F, 0.5625F);
/*      */   }
/*      */ 
/*      */   
/*      */   public EntityBoat(World worldIn, double x, double y, double z) {
/*   82 */     this(worldIn);
/*   83 */     setPosition(x, y, z);
/*   84 */     this.motionX = 0.0D;
/*   85 */     this.motionY = 0.0D;
/*   86 */     this.motionZ = 0.0D;
/*   87 */     this.prevPosX = x;
/*   88 */     this.prevPosY = y;
/*   89 */     this.prevPosZ = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean canTriggerWalking() {
/*   98 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   protected void entityInit() {
/*  103 */     this.dataManager.register(TIME_SINCE_HIT, Integer.valueOf(0));
/*  104 */     this.dataManager.register(FORWARD_DIRECTION, Integer.valueOf(1));
/*  105 */     this.dataManager.register(DAMAGE_TAKEN, Float.valueOf(0.0F));
/*  106 */     this.dataManager.register(BOAT_TYPE, Integer.valueOf(Type.OAK.ordinal())); byte b; int i;
/*      */     DataParameter<Boolean>[] arrayOfDataParameter;
/*  108 */     for (i = (arrayOfDataParameter = DATA_ID_PADDLE).length, b = 0; b < i; ) { DataParameter<Boolean> dataparameter = arrayOfDataParameter[b];
/*      */       
/*  110 */       this.dataManager.register(dataparameter, Boolean.valueOf(false));
/*      */       b++; }
/*      */   
/*      */   }
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
/*  132 */     return getEntityBoundingBox();
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
/*      */ 
/*      */ 
/*      */   
/*      */   public double getMountedYOffset() {
/*  148 */     return -0.1D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean attackEntityFrom(DamageSource source, float amount) {
/*  156 */     if (isEntityInvulnerable(source))
/*      */     {
/*  158 */       return false;
/*      */     }
/*  160 */     if (!this.world.isRemote && !this.isDead) {
/*      */       
/*  162 */       if (source instanceof net.minecraft.util.EntityDamageSourceIndirect && source.getEntity() != null && isPassenger(source.getEntity()))
/*      */       {
/*  164 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  168 */       setForwardDirection(-getForwardDirection());
/*  169 */       setTimeSinceHit(10);
/*  170 */       setDamageTaken(getDamageTaken() + amount * 10.0F);
/*  171 */       setBeenAttacked();
/*  172 */       boolean flag = (source.getEntity() instanceof EntityPlayer && ((EntityPlayer)source.getEntity()).capabilities.isCreativeMode);
/*      */       
/*  174 */       if (flag || getDamageTaken() > 40.0F) {
/*      */         
/*  176 */         if (!flag && this.world.getGameRules().getBoolean("doEntityDrops"))
/*      */         {
/*  178 */           dropItemWithOffset(getItemBoat(), 1, 0.0F);
/*      */         }
/*      */         
/*  181 */         setDead();
/*      */       } 
/*      */       
/*  184 */       return true;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  189 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyEntityCollision(Entity entityIn) {
/*  198 */     if (entityIn instanceof EntityBoat) {
/*      */       
/*  200 */       if ((entityIn.getEntityBoundingBox()).minY < (getEntityBoundingBox()).maxY)
/*      */       {
/*  202 */         super.applyEntityCollision(entityIn);
/*      */       }
/*      */     }
/*  205 */     else if ((entityIn.getEntityBoundingBox()).minY <= (getEntityBoundingBox()).minY) {
/*      */       
/*  207 */       super.applyEntityCollision(entityIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Item getItemBoat() {
/*  213 */     switch (getBoatType()) {
/*      */ 
/*      */       
/*      */       default:
/*  217 */         return Items.BOAT;
/*      */       
/*      */       case SPRUCE:
/*  220 */         return Items.SPRUCE_BOAT;
/*      */       
/*      */       case BIRCH:
/*  223 */         return Items.BIRCH_BOAT;
/*      */       
/*      */       case JUNGLE:
/*  226 */         return Items.JUNGLE_BOAT;
/*      */       
/*      */       case null:
/*  229 */         return Items.ACACIA_BOAT;
/*      */       case DARK_OAK:
/*      */         break;
/*  232 */     }  return Items.DARK_OAK_BOAT;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void performHurtAnimation() {
/*  241 */     setForwardDirection(-getForwardDirection());
/*  242 */     setTimeSinceHit(10);
/*  243 */     setDamageTaken(getDamageTaken() * 11.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBeCollidedWith() {
/*  251 */     return !this.isDead;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/*  259 */     this.boatPitch = x;
/*  260 */     this.lerpY = y;
/*  261 */     this.lerpZ = z;
/*  262 */     this.boatYaw = yaw;
/*  263 */     this.lerpXRot = pitch;
/*  264 */     this.lerpSteps = 10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EnumFacing getAdjustedHorizontalFacing() {
/*  273 */     return getHorizontalFacing().rotateY();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onUpdate() {
/*  281 */     this.previousStatus = this.status;
/*  282 */     this.status = getBoatStatus();
/*      */     
/*  284 */     if (this.status != Status.UNDER_WATER && this.status != Status.UNDER_FLOWING_WATER) {
/*      */       
/*  286 */       this.outOfControlTicks = 0.0F;
/*      */     }
/*      */     else {
/*      */       
/*  290 */       this.outOfControlTicks++;
/*      */     } 
/*      */     
/*  293 */     if (!this.world.isRemote && this.outOfControlTicks >= 60.0F)
/*      */     {
/*  295 */       removePassengers();
/*      */     }
/*      */     
/*  298 */     if (getTimeSinceHit() > 0)
/*      */     {
/*  300 */       setTimeSinceHit(getTimeSinceHit() - 1);
/*      */     }
/*      */     
/*  303 */     if (getDamageTaken() > 0.0F)
/*      */     {
/*  305 */       setDamageTaken(getDamageTaken() - 1.0F);
/*      */     }
/*      */     
/*  308 */     this.prevPosX = this.posX;
/*  309 */     this.prevPosY = this.posY;
/*  310 */     this.prevPosZ = this.posZ;
/*  311 */     super.onUpdate();
/*  312 */     tickLerp();
/*      */     
/*  314 */     if (canPassengerSteer()) {
/*      */       
/*  316 */       if (getPassengers().isEmpty() || !(getPassengers().get(0) instanceof EntityPlayer))
/*      */       {
/*  318 */         setPaddleState(false, false);
/*      */       }
/*      */       
/*  321 */       updateMotion();
/*      */       
/*  323 */       if (this.world.isRemote) {
/*      */         
/*  325 */         controlBoat();
/*  326 */         this.world.sendPacketToServer((Packet)new CPacketSteerBoat(getPaddleState(0), getPaddleState(1)));
/*      */       } 
/*      */       
/*  329 */       moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/*      */     }
/*      */     else {
/*      */       
/*  333 */       this.motionX = 0.0D;
/*  334 */       this.motionY = 0.0D;
/*  335 */       this.motionZ = 0.0D;
/*      */     } 
/*      */     
/*  338 */     for (int i = 0; i <= 1; i++) {
/*      */       
/*  340 */       if (getPaddleState(i)) {
/*      */         
/*  342 */         if (!isSilent() && (this.paddlePositions[i] % 6.2831855F) <= 0.7853981633974483D && (this.paddlePositions[i] + 0.39269909262657166D) % 6.283185307179586D >= 0.7853981633974483D) {
/*      */           
/*  344 */           SoundEvent soundevent = func_193047_k();
/*      */           
/*  346 */           if (soundevent != null) {
/*      */             
/*  348 */             Vec3d vec3d = getLook(1.0F);
/*  349 */             double d0 = (i == 1) ? -vec3d.zCoord : vec3d.zCoord;
/*  350 */             double d1 = (i == 1) ? vec3d.xCoord : -vec3d.xCoord;
/*  351 */             this.world.playSound(null, this.posX + d0, this.posY, this.posZ + d1, soundevent, getSoundCategory(), 1.0F, 0.8F + 0.4F * this.rand.nextFloat());
/*      */           } 
/*      */         } 
/*      */         
/*  355 */         this.paddlePositions[i] = (float)(this.paddlePositions[i] + 0.39269909262657166D);
/*      */       }
/*      */       else {
/*      */         
/*  359 */         this.paddlePositions[i] = 0.0F;
/*      */       } 
/*      */     } 
/*      */     
/*  363 */     doBlockCollisions();
/*  364 */     List<Entity> list = this.world.getEntitiesInAABBexcluding(this, getEntityBoundingBox().expand(0.20000000298023224D, -0.009999999776482582D, 0.20000000298023224D), EntitySelectors.getTeamCollisionPredicate(this));
/*      */     
/*  366 */     if (!list.isEmpty()) {
/*      */       
/*  368 */       boolean flag = (!this.world.isRemote && !(getControllingPassenger() instanceof EntityPlayer));
/*      */       
/*  370 */       for (int j = 0; j < list.size(); j++) {
/*      */         
/*  372 */         Entity entity = list.get(j);
/*      */         
/*  374 */         if (!entity.isPassenger(this))
/*      */         {
/*  376 */           if (flag && getPassengers().size() < 2 && !entity.isRiding() && entity.width < this.width && entity instanceof net.minecraft.entity.EntityLivingBase && !(entity instanceof net.minecraft.entity.passive.EntityWaterMob) && !(entity instanceof EntityPlayer)) {
/*      */             
/*  378 */             entity.startRiding(this);
/*      */           }
/*      */           else {
/*      */             
/*  382 */             applyEntityCollision(entity);
/*      */           } 
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   protected SoundEvent func_193047_k() {
/*  392 */     switch (getBoatStatus()) {
/*      */       
/*      */       case IN_WATER:
/*      */       case UNDER_WATER:
/*      */       case UNDER_FLOWING_WATER:
/*  397 */         return SoundEvents.field_193779_I;
/*      */       
/*      */       case ON_LAND:
/*  400 */         return SoundEvents.field_193778_H;
/*      */     } 
/*      */ 
/*      */     
/*  404 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void tickLerp() {
/*  410 */     if (this.lerpSteps > 0 && !canPassengerSteer()) {
/*      */       
/*  412 */       double d0 = this.posX + (this.boatPitch - this.posX) / this.lerpSteps;
/*  413 */       double d1 = this.posY + (this.lerpY - this.posY) / this.lerpSteps;
/*  414 */       double d2 = this.posZ + (this.lerpZ - this.posZ) / this.lerpSteps;
/*  415 */       double d3 = MathHelper.wrapDegrees(this.boatYaw - this.rotationYaw);
/*  416 */       this.rotationYaw = (float)(this.rotationYaw + d3 / this.lerpSteps);
/*  417 */       this.rotationPitch = (float)(this.rotationPitch + (this.lerpXRot - this.rotationPitch) / this.lerpSteps);
/*  418 */       this.lerpSteps--;
/*  419 */       setPosition(d0, d1, d2);
/*  420 */       setRotation(this.rotationYaw, this.rotationPitch);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPaddleState(boolean p_184445_1_, boolean p_184445_2_) {
/*  426 */     this.dataManager.set(DATA_ID_PADDLE[0], Boolean.valueOf(p_184445_1_));
/*  427 */     this.dataManager.set(DATA_ID_PADDLE[1], Boolean.valueOf(p_184445_2_));
/*      */   }
/*      */ 
/*      */   
/*      */   public float getRowingTime(int p_184448_1_, float limbSwing) {
/*  432 */     return getPaddleState(p_184448_1_) ? (float)MathHelper.clampedLerp(this.paddlePositions[p_184448_1_] - 0.39269909262657166D, this.paddlePositions[p_184448_1_], limbSwing) : 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Status getBoatStatus() {
/*  440 */     Status entityboat$status = getUnderwaterStatus();
/*      */     
/*  442 */     if (entityboat$status != null) {
/*      */       
/*  444 */       this.waterLevel = (getEntityBoundingBox()).maxY;
/*  445 */       return entityboat$status;
/*      */     } 
/*  447 */     if (checkInWater())
/*      */     {
/*  449 */       return Status.IN_WATER;
/*      */     }
/*      */ 
/*      */     
/*  453 */     float f = getBoatGlide();
/*      */     
/*  455 */     if (f > 0.0F) {
/*      */       
/*  457 */       this.boatGlide = f;
/*  458 */       return Status.ON_LAND;
/*      */     } 
/*      */ 
/*      */     
/*  462 */     return Status.IN_AIR;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getWaterLevelAbove() {
/*  469 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/*  470 */     int i = MathHelper.floor(axisalignedbb.minX);
/*  471 */     int j = MathHelper.ceil(axisalignedbb.maxX);
/*  472 */     int k = MathHelper.floor(axisalignedbb.maxY);
/*  473 */     int l = MathHelper.ceil(axisalignedbb.maxY - this.lastYd);
/*  474 */     int i1 = MathHelper.floor(axisalignedbb.minZ);
/*  475 */     int j1 = MathHelper.ceil(axisalignedbb.maxZ);
/*  476 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  482 */       for (int k1 = k; k1 < l; k1++) {
/*      */         
/*  484 */         float f = 0.0F;
/*  485 */         int l1 = i;
/*      */ 
/*      */         
/*      */         label28: while (true) {
/*  489 */           if (l1 >= j) {
/*      */             
/*  491 */             if (f < 1.0F) {
/*      */               
/*  493 */               float f2 = blockpos$pooledmutableblockpos.getY() + f;
/*  494 */               return f2;
/*      */             } 
/*      */             
/*      */             break;
/*      */           } 
/*      */           
/*  500 */           for (int i2 = i1; i2 < j1; i2++) {
/*      */             
/*  502 */             blockpos$pooledmutableblockpos.setPos(l1, k1, i2);
/*  503 */             IBlockState iblockstate = this.world.getBlockState((BlockPos)blockpos$pooledmutableblockpos);
/*      */             
/*  505 */             if (iblockstate.getMaterial() == Material.WATER)
/*      */             {
/*  507 */               f = Math.max(f, BlockLiquid.func_190973_f(iblockstate, (IBlockAccess)this.world, (BlockPos)blockpos$pooledmutableblockpos));
/*      */             }
/*      */             
/*  510 */             if (f >= 1.0F) {
/*      */               break label28;
/*      */             }
/*      */           } 
/*      */ 
/*      */           
/*  516 */           l1++;
/*      */         } 
/*      */       } 
/*      */       
/*  520 */       float f1 = (l + 1);
/*  521 */       return f1;
/*      */     }
/*      */     finally {
/*      */       
/*  525 */       blockpos$pooledmutableblockpos.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBoatGlide() {
/*  534 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/*  535 */     AxisAlignedBB axisalignedbb1 = new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY - 0.001D, axisalignedbb.minZ, axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
/*  536 */     int i = MathHelper.floor(axisalignedbb1.minX) - 1;
/*  537 */     int j = MathHelper.ceil(axisalignedbb1.maxX) + 1;
/*  538 */     int k = MathHelper.floor(axisalignedbb1.minY) - 1;
/*  539 */     int l = MathHelper.ceil(axisalignedbb1.maxY) + 1;
/*  540 */     int i1 = MathHelper.floor(axisalignedbb1.minZ) - 1;
/*  541 */     int j1 = MathHelper.ceil(axisalignedbb1.maxZ) + 1;
/*  542 */     List<AxisAlignedBB> list = Lists.newArrayList();
/*  543 */     float f = 0.0F;
/*  544 */     int k1 = 0;
/*  545 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/*      */ 
/*      */     
/*      */     try {
/*  549 */       for (int l1 = i; l1 < j; l1++) {
/*      */         
/*  551 */         for (int i2 = i1; i2 < j1; i2++) {
/*      */           
/*  553 */           int j2 = ((l1 != i && l1 != j - 1) ? 0 : 1) + ((i2 != i1 && i2 != j1 - 1) ? 0 : 1);
/*      */           
/*  555 */           if (j2 != 2)
/*      */           {
/*  557 */             for (int k2 = k; k2 < l; k2++) {
/*      */               
/*  559 */               if (j2 <= 0 || (k2 != k && k2 != l - 1))
/*      */               {
/*  561 */                 blockpos$pooledmutableblockpos.setPos(l1, k2, i2);
/*  562 */                 IBlockState iblockstate = this.world.getBlockState((BlockPos)blockpos$pooledmutableblockpos);
/*  563 */                 iblockstate.addCollisionBoxToList(this.world, (BlockPos)blockpos$pooledmutableblockpos, axisalignedbb1, list, this, false);
/*      */                 
/*  565 */                 if (!list.isEmpty()) {
/*      */                   
/*  567 */                   f += (iblockstate.getBlock()).slipperiness;
/*  568 */                   k1++;
/*      */                 } 
/*      */                 
/*  571 */                 list.clear();
/*      */               }
/*      */             
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/*  580 */       blockpos$pooledmutableblockpos.release();
/*      */     } 
/*      */     
/*  583 */     return f / k1;
/*      */   }
/*      */   
/*      */   private boolean checkInWater() {
/*      */     int m;
/*  588 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/*  589 */     int i = MathHelper.floor(axisalignedbb.minX);
/*  590 */     int j = MathHelper.ceil(axisalignedbb.maxX);
/*  591 */     int k = MathHelper.floor(axisalignedbb.minY);
/*  592 */     int l = MathHelper.ceil(axisalignedbb.minY + 0.001D);
/*  593 */     int i1 = MathHelper.floor(axisalignedbb.minZ);
/*  594 */     int j1 = MathHelper.ceil(axisalignedbb.maxZ);
/*  595 */     boolean flag = false;
/*  596 */     this.waterLevel = Double.MIN_VALUE;
/*  597 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/*      */ 
/*      */     
/*      */     try {
/*  601 */       for (int k1 = i; k1 < j; k1++) {
/*      */         
/*  603 */         for (int l1 = k; l1 < l; l1++) {
/*      */           
/*  605 */           for (int i2 = i1; i2 < j1; i2++) {
/*      */             
/*  607 */             blockpos$pooledmutableblockpos.setPos(k1, l1, i2);
/*  608 */             IBlockState iblockstate = this.world.getBlockState((BlockPos)blockpos$pooledmutableblockpos);
/*      */             
/*  610 */             if (iblockstate.getMaterial() == Material.WATER)
/*      */             {
/*  612 */               float f = BlockLiquid.func_190972_g(iblockstate, (IBlockAccess)this.world, (BlockPos)blockpos$pooledmutableblockpos);
/*  613 */               this.waterLevel = Math.max(f, this.waterLevel);
/*  614 */               m = flag | ((axisalignedbb.minY < f) ? 1 : 0);
/*      */             }
/*      */           
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/*  622 */       blockpos$pooledmutableblockpos.release();
/*      */     } 
/*      */     
/*  625 */     return m;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private Status getUnderwaterStatus() {
/*  635 */     AxisAlignedBB axisalignedbb = getEntityBoundingBox();
/*  636 */     double d0 = axisalignedbb.maxY + 0.001D;
/*  637 */     int i = MathHelper.floor(axisalignedbb.minX);
/*  638 */     int j = MathHelper.ceil(axisalignedbb.maxX);
/*  639 */     int k = MathHelper.floor(axisalignedbb.maxY);
/*  640 */     int l = MathHelper.ceil(d0);
/*  641 */     int i1 = MathHelper.floor(axisalignedbb.minZ);
/*  642 */     int j1 = MathHelper.ceil(axisalignedbb.maxZ);
/*  643 */     boolean flag = false;
/*  644 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/*      */ 
/*      */     
/*      */     try {
/*  648 */       for (int k1 = i; k1 < j; k1++) {
/*      */         
/*  650 */         for (int l1 = k; l1 < l; l1++) {
/*      */           
/*  652 */           for (int i2 = i1; i2 < j1; i2++) {
/*      */             
/*  654 */             blockpos$pooledmutableblockpos.setPos(k1, l1, i2);
/*  655 */             IBlockState iblockstate = this.world.getBlockState((BlockPos)blockpos$pooledmutableblockpos);
/*      */             
/*  657 */             if (iblockstate.getMaterial() == Material.WATER && d0 < BlockLiquid.func_190972_g(iblockstate, (IBlockAccess)this.world, (BlockPos)blockpos$pooledmutableblockpos))
/*      */             {
/*  659 */               if (((Integer)iblockstate.getValue((IProperty)BlockLiquid.LEVEL)).intValue() != 0) {
/*      */                 
/*  661 */                 Status entityboat$status = Status.UNDER_FLOWING_WATER;
/*  662 */                 return entityboat$status;
/*      */               } 
/*      */               
/*  665 */               flag = true;
/*      */             }
/*      */           
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/*  673 */       blockpos$pooledmutableblockpos.release();
/*      */     } 
/*      */     
/*  676 */     return flag ? Status.UNDER_WATER : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateMotion() {
/*  684 */     double d0 = -0.03999999910593033D;
/*  685 */     double d1 = hasNoGravity() ? 0.0D : -0.03999999910593033D;
/*  686 */     double d2 = 0.0D;
/*  687 */     this.momentum = 0.05F;
/*      */     
/*  689 */     if (this.previousStatus == Status.IN_AIR && this.status != Status.IN_AIR && this.status != Status.ON_LAND) {
/*      */       
/*  691 */       this.waterLevel = (getEntityBoundingBox()).minY + this.height;
/*  692 */       setPosition(this.posX, (getWaterLevelAbove() - this.height) + 0.101D, this.posZ);
/*  693 */       this.motionY = 0.0D;
/*  694 */       this.lastYd = 0.0D;
/*  695 */       this.status = Status.IN_WATER;
/*      */     }
/*      */     else {
/*      */       
/*  699 */       if (this.status == Status.IN_WATER) {
/*      */         
/*  701 */         d2 = (this.waterLevel - (getEntityBoundingBox()).minY) / this.height;
/*  702 */         this.momentum = 0.9F;
/*      */       }
/*  704 */       else if (this.status == Status.UNDER_FLOWING_WATER) {
/*      */         
/*  706 */         d1 = -7.0E-4D;
/*  707 */         this.momentum = 0.9F;
/*      */       }
/*  709 */       else if (this.status == Status.UNDER_WATER) {
/*      */         
/*  711 */         d2 = 0.009999999776482582D;
/*  712 */         this.momentum = 0.45F;
/*      */       }
/*  714 */       else if (this.status == Status.IN_AIR) {
/*      */         
/*  716 */         this.momentum = 0.9F;
/*      */       }
/*  718 */       else if (this.status == Status.ON_LAND) {
/*      */         
/*  720 */         this.momentum = this.boatGlide;
/*      */         
/*  722 */         if (getControllingPassenger() instanceof EntityPlayer)
/*      */         {
/*  724 */           this.boatGlide /= 2.0F;
/*      */         }
/*      */       } 
/*      */       
/*  728 */       this.motionX *= this.momentum;
/*  729 */       this.motionZ *= this.momentum;
/*  730 */       this.deltaRotation *= this.momentum;
/*  731 */       this.motionY += d1;
/*      */       
/*  733 */       if (d2 > 0.0D) {
/*      */         
/*  735 */         double d3 = 0.65D;
/*  736 */         this.motionY += d2 * 0.06153846016296973D;
/*  737 */         double d4 = 0.75D;
/*  738 */         this.motionY *= 0.75D;
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void controlBoat() {
/*  745 */     if (isBeingRidden()) {
/*      */       
/*  747 */       float f = 0.0F;
/*      */       
/*  749 */       if (this.leftInputDown)
/*      */       {
/*  751 */         this.deltaRotation--;
/*      */       }
/*      */       
/*  754 */       if (this.rightInputDown)
/*      */       {
/*  756 */         this.deltaRotation++;
/*      */       }
/*      */       
/*  759 */       if (this.rightInputDown != this.leftInputDown && !this.forwardInputDown && !this.backInputDown)
/*      */       {
/*  761 */         f += 0.005F;
/*      */       }
/*      */       
/*  764 */       this.rotationYaw += this.deltaRotation;
/*      */       
/*  766 */       if (this.forwardInputDown)
/*      */       {
/*  768 */         f += 0.04F;
/*      */       }
/*      */       
/*  771 */       if (this.backInputDown)
/*      */       {
/*  773 */         f -= 0.005F;
/*      */       }
/*      */       
/*  776 */       this.motionX += (MathHelper.sin(-this.rotationYaw * 0.017453292F) * f);
/*  777 */       this.motionZ += (MathHelper.cos(this.rotationYaw * 0.017453292F) * f);
/*  778 */       setPaddleState(!((!this.rightInputDown || this.leftInputDown) && !this.forwardInputDown), !((!this.leftInputDown || this.rightInputDown) && !this.forwardInputDown));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updatePassenger(Entity passenger) {
/*  784 */     if (isPassenger(passenger)) {
/*      */       
/*  786 */       float f = 0.0F;
/*  787 */       float f1 = (float)((this.isDead ? 0.009999999776482582D : getMountedYOffset()) + passenger.getYOffset());
/*      */       
/*  789 */       if (getPassengers().size() > 1) {
/*      */         
/*  791 */         int i = getPassengers().indexOf(passenger);
/*      */         
/*  793 */         if (i == 0) {
/*      */           
/*  795 */           f = 0.2F;
/*      */         }
/*      */         else {
/*      */           
/*  799 */           f = -0.6F;
/*      */         } 
/*      */         
/*  802 */         if (passenger instanceof EntityAnimal)
/*      */         {
/*  804 */           f = (float)(f + 0.2D);
/*      */         }
/*      */       } 
/*      */       
/*  808 */       Vec3d vec3d = (new Vec3d(f, 0.0D, 0.0D)).rotateYaw(-this.rotationYaw * 0.017453292F - 1.5707964F);
/*  809 */       passenger.setPosition(this.posX + vec3d.xCoord, this.posY + f1, this.posZ + vec3d.zCoord);
/*  810 */       passenger.rotationYaw += this.deltaRotation;
/*  811 */       passenger.setRotationYawHead(passenger.getRotationYawHead() + this.deltaRotation);
/*  812 */       applyYawToEntity(passenger);
/*      */       
/*  814 */       if (passenger instanceof EntityAnimal && getPassengers().size() > 1) {
/*      */         
/*  816 */         int j = (passenger.getEntityId() % 2 == 0) ? 90 : 270;
/*  817 */         passenger.setRenderYawOffset(((EntityAnimal)passenger).renderYawOffset + j);
/*  818 */         passenger.setRotationYawHead(passenger.getRotationYawHead() + j);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void applyYawToEntity(Entity entityToUpdate) {
/*  828 */     entityToUpdate.setRenderYawOffset(this.rotationYaw);
/*  829 */     float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
/*  830 */     float f1 = MathHelper.clamp(f, -105.0F, 105.0F);
/*  831 */     entityToUpdate.prevRotationYaw += f1 - f;
/*  832 */     entityToUpdate.rotationYaw += f1 - f;
/*  833 */     entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void applyOrientationToEntity(Entity entityToUpdate) {
/*  841 */     applyYawToEntity(entityToUpdate);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writeEntityToNBT(NBTTagCompound compound) {
/*  849 */     compound.setString("Type", getBoatType().getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void readEntityFromNBT(NBTTagCompound compound) {
/*  857 */     if (compound.hasKey("Type", 8))
/*      */     {
/*  859 */       setBoatType(Type.getTypeFromString(compound.getString("Type")));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean processInitialInteract(EntityPlayer player, EnumHand stack) {
/*  865 */     if (player.isSneaking())
/*      */     {
/*  867 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  871 */     if (!this.world.isRemote && this.outOfControlTicks < 60.0F)
/*      */     {
/*  873 */       player.startRiding(this);
/*      */     }
/*      */     
/*  876 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
/*  882 */     this.lastYd = this.motionY;
/*      */     
/*  884 */     if (!isRiding())
/*      */     {
/*  886 */       if (onGroundIn) {
/*      */         
/*  888 */         if (this.fallDistance > 3.0F) {
/*      */           
/*  890 */           if (this.status != Status.ON_LAND) {
/*      */             
/*  892 */             this.fallDistance = 0.0F;
/*      */             
/*      */             return;
/*      */           } 
/*  896 */           fall(this.fallDistance, 1.0F);
/*      */           
/*  898 */           if (!this.world.isRemote && !this.isDead) {
/*      */             
/*  900 */             setDead();
/*      */             
/*  902 */             if (this.world.getGameRules().getBoolean("doEntityDrops")) {
/*      */               
/*  904 */               for (int i = 0; i < 3; i++)
/*      */               {
/*  906 */                 entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.PLANKS), 1, getBoatType().getMetadata()), 0.0F);
/*      */               }
/*      */               
/*  909 */               for (int j = 0; j < 2; j++)
/*      */               {
/*  911 */                 dropItemWithOffset(Items.STICK, 1, 0.0F);
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  917 */         this.fallDistance = 0.0F;
/*      */       }
/*  919 */       else if (this.world.getBlockState((new BlockPos(this)).down()).getMaterial() != Material.WATER && y < 0.0D) {
/*      */         
/*  921 */         this.fallDistance = (float)(this.fallDistance - y);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getPaddleState(int p_184457_1_) {
/*  928 */     return (((Boolean)this.dataManager.get(DATA_ID_PADDLE[p_184457_1_])).booleanValue() && getControllingPassenger() != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDamageTaken(float damageTaken) {
/*  936 */     this.dataManager.set(DAMAGE_TAKEN, Float.valueOf(damageTaken));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getDamageTaken() {
/*  944 */     return ((Float)this.dataManager.get(DAMAGE_TAKEN)).floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTimeSinceHit(int timeSinceHit) {
/*  952 */     this.dataManager.set(TIME_SINCE_HIT, Integer.valueOf(timeSinceHit));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTimeSinceHit() {
/*  960 */     return ((Integer)this.dataManager.get(TIME_SINCE_HIT)).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setForwardDirection(int forwardDirection) {
/*  968 */     this.dataManager.set(FORWARD_DIRECTION, Integer.valueOf(forwardDirection));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getForwardDirection() {
/*  976 */     return ((Integer)this.dataManager.get(FORWARD_DIRECTION)).intValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBoatType(Type boatType) {
/*  981 */     this.dataManager.set(BOAT_TYPE, Integer.valueOf(boatType.ordinal()));
/*      */   }
/*      */ 
/*      */   
/*      */   public Type getBoatType() {
/*  986 */     return Type.byId(((Integer)this.dataManager.get(BOAT_TYPE)).intValue());
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean canFitPassenger(Entity passenger) {
/*  991 */     return (getPassengers().size() < 2);
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
/* 1002 */     List<Entity> list = getPassengers();
/* 1003 */     return list.isEmpty() ? null : list.get(0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateInputs(boolean p_184442_1_, boolean p_184442_2_, boolean p_184442_3_, boolean p_184442_4_) {
/* 1008 */     this.leftInputDown = p_184442_1_;
/* 1009 */     this.rightInputDown = p_184442_2_;
/* 1010 */     this.forwardInputDown = p_184442_3_;
/* 1011 */     this.backInputDown = p_184442_4_;
/*      */   }
/*      */   
/*      */   public enum Status
/*      */   {
/* 1016 */     IN_WATER,
/* 1017 */     UNDER_WATER,
/* 1018 */     UNDER_FLOWING_WATER,
/* 1019 */     ON_LAND,
/* 1020 */     IN_AIR;
/*      */   }
/*      */   
/*      */   public enum Type
/*      */   {
/* 1025 */     OAK(BlockPlanks.EnumType.OAK.getMetadata(), "oak"),
/* 1026 */     SPRUCE(BlockPlanks.EnumType.SPRUCE.getMetadata(), "spruce"),
/* 1027 */     BIRCH(BlockPlanks.EnumType.BIRCH.getMetadata(), "birch"),
/* 1028 */     JUNGLE(BlockPlanks.EnumType.JUNGLE.getMetadata(), "jungle"),
/* 1029 */     ACACIA(BlockPlanks.EnumType.ACACIA.getMetadata(), "acacia"),
/* 1030 */     DARK_OAK(BlockPlanks.EnumType.DARK_OAK.getMetadata(), "dark_oak");
/*      */     
/*      */     private final String name;
/*      */     
/*      */     private final int metadata;
/*      */     
/*      */     Type(int metadataIn, String nameIn) {
/* 1037 */       this.name = nameIn;
/* 1038 */       this.metadata = metadataIn;
/*      */     }
/*      */ 
/*      */     
/*      */     public String getName() {
/* 1043 */       return this.name;
/*      */     }
/*      */ 
/*      */     
/*      */     public int getMetadata() {
/* 1048 */       return this.metadata;
/*      */     }
/*      */ 
/*      */     
/*      */     public String toString() {
/* 1053 */       return this.name;
/*      */     }
/*      */ 
/*      */     
/*      */     public static Type byId(int id) {
/* 1058 */       if (id < 0 || id >= (values()).length)
/*      */       {
/* 1060 */         id = 0;
/*      */       }
/*      */       
/* 1063 */       return values()[id];
/*      */     }
/*      */ 
/*      */     
/*      */     public static Type getTypeFromString(String nameIn) {
/* 1068 */       for (int i = 0; i < (values()).length; i++) {
/*      */         
/* 1070 */         if (values()[i].getName().equals(nameIn))
/*      */         {
/* 1072 */           return values()[i];
/*      */         }
/*      */       } 
/*      */       
/* 1076 */       return values()[0];
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */