/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.enchantment.EnchantmentHelper;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Enchantments;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.network.play.server.SPacketChangeGameState;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityArrow extends Entity implements IProjectile {
/*  40 */   private static final Predicate<Entity> ARROW_TARGETS = Predicates.and(new Predicate[] { EntitySelectors.NOT_SPECTATING, EntitySelectors.IS_ALIVE, new Predicate<Entity>()
/*     */         {
/*     */           public boolean apply(@Nullable Entity p_apply_1_)
/*     */           {
/*  44 */             return p_apply_1_.canBeCollidedWith();
/*     */           }
/*     */         } });
/*  47 */   private static final DataParameter<Byte> CRITICAL = EntityDataManager.createKey(EntityArrow.class, DataSerializers.BYTE);
/*     */   
/*     */   private int xTile;
/*     */   
/*     */   private int yTile;
/*     */   
/*     */   private int zTile;
/*     */   
/*     */   private Block inTile;
/*     */   
/*     */   private int inData;
/*     */   
/*     */   protected boolean inGround;
/*     */   
/*     */   protected int timeInGround;
/*     */   
/*     */   public PickupStatus pickupStatus;
/*     */   
/*     */   public int arrowShake;
/*     */   public Entity shootingEntity;
/*     */   private int ticksInGround;
/*     */   private int ticksInAir;
/*     */   private double damage;
/*     */   private int knockbackStrength;
/*     */   
/*     */   public EntityArrow(World worldIn) {
/*  73 */     super(worldIn);
/*  74 */     this.xTile = -1;
/*  75 */     this.yTile = -1;
/*  76 */     this.zTile = -1;
/*  77 */     this.pickupStatus = PickupStatus.DISALLOWED;
/*  78 */     this.damage = 2.0D;
/*  79 */     setSize(0.5F, 0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityArrow(World worldIn, double x, double y, double z) {
/*  84 */     this(worldIn);
/*  85 */     setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityArrow(World worldIn, EntityLivingBase shooter) {
/*  90 */     this(worldIn, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.10000000149011612D, shooter.posZ);
/*  91 */     this.shootingEntity = (Entity)shooter;
/*     */     
/*  93 */     if (shooter instanceof EntityPlayer)
/*     */     {
/*  95 */       this.pickupStatus = PickupStatus.ALLOWED;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/* 104 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 10.0D;
/*     */     
/* 106 */     if (Double.isNaN(d0))
/*     */     {
/* 108 */       d0 = 1.0D;
/*     */     }
/*     */     
/* 111 */     d0 = d0 * 64.0D * getRenderDistanceWeight();
/* 112 */     return (distance < d0 * d0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 117 */     this.dataManager.register(CRITICAL, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAim(Entity shooter, float pitch, float yaw, float p_184547_4_, float velocity, float inaccuracy) {
/* 122 */     float f = -MathHelper.sin(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
/* 123 */     float f1 = -MathHelper.sin(pitch * 0.017453292F);
/* 124 */     float f2 = MathHelper.cos(yaw * 0.017453292F) * MathHelper.cos(pitch * 0.017453292F);
/* 125 */     setThrowableHeading(f, f1, f2, velocity, inaccuracy);
/* 126 */     this.motionX += shooter.motionX;
/* 127 */     this.motionZ += shooter.motionZ;
/*     */     
/* 129 */     if (!shooter.onGround)
/*     */     {
/* 131 */       this.motionY += shooter.motionY;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
/* 140 */     float f = MathHelper.sqrt(x * x + y * y + z * z);
/* 141 */     x /= f;
/* 142 */     y /= f;
/* 143 */     z /= f;
/* 144 */     x += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 145 */     y += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 146 */     z += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 147 */     x *= velocity;
/* 148 */     y *= velocity;
/* 149 */     z *= velocity;
/* 150 */     this.motionX = x;
/* 151 */     this.motionY = y;
/* 152 */     this.motionZ = z;
/* 153 */     float f1 = MathHelper.sqrt(x * x + z * z);
/* 154 */     this.rotationYaw = (float)(MathHelper.atan2(x, z) * 57.29577951308232D);
/* 155 */     this.rotationPitch = (float)(MathHelper.atan2(y, f1) * 57.29577951308232D);
/* 156 */     this.prevRotationYaw = this.rotationYaw;
/* 157 */     this.prevRotationPitch = this.rotationPitch;
/* 158 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {
/* 166 */     setPosition(x, y, z);
/* 167 */     setRotation(yaw, pitch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/* 175 */     this.motionX = x;
/* 176 */     this.motionY = y;
/* 177 */     this.motionZ = z;
/*     */     
/* 179 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/*     */       
/* 181 */       float f = MathHelper.sqrt(x * x + z * z);
/* 182 */       this.rotationPitch = (float)(MathHelper.atan2(y, f) * 57.29577951308232D);
/* 183 */       this.rotationYaw = (float)(MathHelper.atan2(x, z) * 57.29577951308232D);
/* 184 */       this.prevRotationPitch = this.rotationPitch;
/* 185 */       this.prevRotationYaw = this.rotationYaw;
/* 186 */       setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/* 187 */       this.ticksInGround = 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 196 */     super.onUpdate();
/*     */     
/* 198 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/*     */       
/* 200 */       float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 201 */       this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 57.29577951308232D);
/* 202 */       this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * 57.29577951308232D);
/* 203 */       this.prevRotationYaw = this.rotationYaw;
/* 204 */       this.prevRotationPitch = this.rotationPitch;
/*     */     } 
/*     */     
/* 207 */     BlockPos blockpos = new BlockPos(this.xTile, this.yTile, this.zTile);
/* 208 */     IBlockState iblockstate = this.world.getBlockState(blockpos);
/* 209 */     Block block = iblockstate.getBlock();
/*     */     
/* 211 */     if (iblockstate.getMaterial() != Material.AIR) {
/*     */       
/* 213 */       AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox((IBlockAccess)this.world, blockpos);
/*     */       
/* 215 */       if (axisalignedbb != Block.NULL_AABB && axisalignedbb.offset(blockpos).isVecInside(new Vec3d(this.posX, this.posY, this.posZ)))
/*     */       {
/* 217 */         this.inGround = true;
/*     */       }
/*     */     } 
/*     */     
/* 221 */     if (this.arrowShake > 0)
/*     */     {
/* 223 */       this.arrowShake--;
/*     */     }
/*     */     
/* 226 */     if (this.inGround) {
/*     */       
/* 228 */       int j = block.getMetaFromState(iblockstate);
/*     */       
/* 230 */       if ((block != this.inTile || j != this.inData) && !this.world.collidesWithAnyBlock(getEntityBoundingBox().expandXyz(0.05D))) {
/*     */         
/* 232 */         this.inGround = false;
/* 233 */         this.motionX *= (this.rand.nextFloat() * 0.2F);
/* 234 */         this.motionY *= (this.rand.nextFloat() * 0.2F);
/* 235 */         this.motionZ *= (this.rand.nextFloat() * 0.2F);
/* 236 */         this.ticksInGround = 0;
/* 237 */         this.ticksInAir = 0;
/*     */       }
/*     */       else {
/*     */         
/* 241 */         this.ticksInGround++;
/*     */         
/* 243 */         if (this.ticksInGround >= 1200)
/*     */         {
/* 245 */           setDead();
/*     */         }
/*     */       } 
/*     */       
/* 249 */       this.timeInGround++;
/*     */     }
/*     */     else {
/*     */       
/* 253 */       this.timeInGround = 0;
/* 254 */       this.ticksInAir++;
/* 255 */       Vec3d vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
/* 256 */       Vec3d vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 257 */       RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d1, vec3d, false, true, false);
/* 258 */       vec3d1 = new Vec3d(this.posX, this.posY, this.posZ);
/* 259 */       vec3d = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */       
/* 261 */       if (raytraceresult != null)
/*     */       {
/* 263 */         vec3d = new Vec3d(raytraceresult.hitVec.xCoord, raytraceresult.hitVec.yCoord, raytraceresult.hitVec.zCoord);
/*     */       }
/*     */       
/* 266 */       Entity entity = findEntityOnPath(vec3d1, vec3d);
/*     */       
/* 268 */       if (entity != null)
/*     */       {
/* 270 */         raytraceresult = new RayTraceResult(entity);
/*     */       }
/*     */       
/* 273 */       if (raytraceresult != null && raytraceresult.entityHit instanceof EntityPlayer) {
/*     */         
/* 275 */         EntityPlayer entityplayer = (EntityPlayer)raytraceresult.entityHit;
/*     */         
/* 277 */         if (this.shootingEntity instanceof EntityPlayer && !((EntityPlayer)this.shootingEntity).canAttackPlayer(entityplayer))
/*     */         {
/* 279 */           raytraceresult = null;
/*     */         }
/*     */       } 
/*     */       
/* 283 */       if (raytraceresult != null)
/*     */       {
/* 285 */         onHit(raytraceresult);
/*     */       }
/*     */       
/* 288 */       if (getIsCritical())
/*     */       {
/* 290 */         for (int k = 0; k < 4; k++)
/*     */         {
/* 292 */           this.world.spawnParticle(EnumParticleTypes.CRIT, this.posX + this.motionX * k / 4.0D, this.posY + this.motionY * k / 4.0D, this.posZ + this.motionZ * k / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ, new int[0]);
/*     */         }
/*     */       }
/*     */       
/* 296 */       this.posX += this.motionX;
/* 297 */       this.posY += this.motionY;
/* 298 */       this.posZ += this.motionZ;
/* 299 */       float f4 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 300 */       this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 57.29577951308232D);
/*     */       
/* 302 */       for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f4) * 57.29577951308232D); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 307 */       while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*     */       {
/* 309 */         this.prevRotationPitch += 360.0F;
/*     */       }
/*     */       
/* 312 */       while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*     */       {
/* 314 */         this.prevRotationYaw -= 360.0F;
/*     */       }
/*     */       
/* 317 */       while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*     */       {
/* 319 */         this.prevRotationYaw += 360.0F;
/*     */       }
/*     */       
/* 322 */       this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 323 */       this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/* 324 */       float f1 = 0.99F;
/* 325 */       float f2 = 0.05F;
/*     */       
/* 327 */       if (isInWater()) {
/*     */         
/* 329 */         for (int i = 0; i < 4; i++) {
/*     */           
/* 331 */           float f3 = 0.25F;
/* 332 */           this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         } 
/*     */         
/* 335 */         f1 = 0.6F;
/*     */       } 
/*     */       
/* 338 */       if (isWet())
/*     */       {
/* 340 */         extinguish();
/*     */       }
/*     */       
/* 343 */       this.motionX *= f1;
/* 344 */       this.motionY *= f1;
/* 345 */       this.motionZ *= f1;
/*     */       
/* 347 */       if (!hasNoGravity())
/*     */       {
/* 349 */         this.motionY -= 0.05000000074505806D;
/*     */       }
/*     */       
/* 352 */       setPosition(this.posX, this.posY, this.posZ);
/* 353 */       doBlockCollisions();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onHit(RayTraceResult raytraceResultIn) {
/* 362 */     Entity entity = raytraceResultIn.entityHit;
/*     */     
/* 364 */     if (entity != null) {
/*     */       DamageSource damagesource;
/* 366 */       float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 367 */       int i = MathHelper.ceil(f * this.damage);
/*     */       
/* 369 */       if (getIsCritical())
/*     */       {
/* 371 */         i += this.rand.nextInt(i / 2 + 2);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 376 */       if (this.shootingEntity == null) {
/*     */         
/* 378 */         damagesource = DamageSource.causeArrowDamage(this, this);
/*     */       }
/*     */       else {
/*     */         
/* 382 */         damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
/*     */       } 
/*     */       
/* 385 */       if (isBurning() && !(entity instanceof net.minecraft.entity.monster.EntityEnderman))
/*     */       {
/* 387 */         entity.setFire(5);
/*     */       }
/*     */       
/* 390 */       if (entity.attackEntityFrom(damagesource, i)) {
/*     */         
/* 392 */         if (entity instanceof EntityLivingBase) {
/*     */           
/* 394 */           EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/*     */           
/* 396 */           if (!this.world.isRemote)
/*     */           {
/* 398 */             entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
/*     */           }
/*     */           
/* 401 */           if (this.knockbackStrength > 0) {
/*     */             
/* 403 */             float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*     */             
/* 405 */             if (f1 > 0.0F)
/*     */             {
/* 407 */               entitylivingbase.addVelocity(this.motionX * this.knockbackStrength * 0.6000000238418579D / f1, 0.1D, this.motionZ * this.knockbackStrength * 0.6000000238418579D / f1);
/*     */             }
/*     */           } 
/*     */           
/* 411 */           if (this.shootingEntity instanceof EntityLivingBase) {
/*     */             
/* 413 */             EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
/* 414 */             EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase)this.shootingEntity, (Entity)entitylivingbase);
/*     */           } 
/*     */           
/* 417 */           arrowHit(entitylivingbase);
/*     */           
/* 419 */           if (this.shootingEntity != null && entitylivingbase != this.shootingEntity && entitylivingbase instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP)
/*     */           {
/* 421 */             ((EntityPlayerMP)this.shootingEntity).connection.sendPacket((Packet)new SPacketChangeGameState(6, 0.0F));
/*     */           }
/*     */         } 
/*     */         
/* 425 */         playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/*     */         
/* 427 */         if (!(entity instanceof net.minecraft.entity.monster.EntityEnderman))
/*     */         {
/* 429 */           setDead();
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 434 */         this.motionX *= -0.10000000149011612D;
/* 435 */         this.motionY *= -0.10000000149011612D;
/* 436 */         this.motionZ *= -0.10000000149011612D;
/* 437 */         this.rotationYaw += 180.0F;
/* 438 */         this.prevRotationYaw += 180.0F;
/* 439 */         this.ticksInAir = 0;
/*     */         
/* 441 */         if (!this.world.isRemote && this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ < 0.0010000000474974513D)
/*     */         {
/* 443 */           if (this.pickupStatus == PickupStatus.ALLOWED)
/*     */           {
/* 445 */             entityDropItem(getArrowStack(), 0.1F);
/*     */           }
/*     */           
/* 448 */           setDead();
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 454 */       BlockPos blockpos = raytraceResultIn.getBlockPos();
/* 455 */       this.xTile = blockpos.getX();
/* 456 */       this.yTile = blockpos.getY();
/* 457 */       this.zTile = blockpos.getZ();
/* 458 */       IBlockState iblockstate = this.world.getBlockState(blockpos);
/* 459 */       this.inTile = iblockstate.getBlock();
/* 460 */       this.inData = this.inTile.getMetaFromState(iblockstate);
/* 461 */       this.motionX = (float)(raytraceResultIn.hitVec.xCoord - this.posX);
/* 462 */       this.motionY = (float)(raytraceResultIn.hitVec.yCoord - this.posY);
/* 463 */       this.motionZ = (float)(raytraceResultIn.hitVec.zCoord - this.posZ);
/* 464 */       float f2 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 465 */       this.posX -= this.motionX / f2 * 0.05000000074505806D;
/* 466 */       this.posY -= this.motionY / f2 * 0.05000000074505806D;
/* 467 */       this.posZ -= this.motionZ / f2 * 0.05000000074505806D;
/* 468 */       playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
/* 469 */       this.inGround = true;
/* 470 */       this.arrowShake = 7;
/* 471 */       setIsCritical(false);
/*     */       
/* 473 */       if (iblockstate.getMaterial() != Material.AIR)
/*     */       {
/* 475 */         this.inTile.onEntityCollidedWithBlock(this.world, blockpos, iblockstate, this);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveEntity(MoverType x, double p_70091_2_, double p_70091_4_, double p_70091_6_) {
/* 485 */     super.moveEntity(x, p_70091_2_, p_70091_4_, p_70091_6_);
/*     */     
/* 487 */     if (this.inGround) {
/*     */       
/* 489 */       this.xTile = MathHelper.floor(this.posX);
/* 490 */       this.yTile = MathHelper.floor(this.posY);
/* 491 */       this.zTile = MathHelper.floor(this.posZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void arrowHit(EntityLivingBase living) {}
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Entity findEntityOnPath(Vec3d start, Vec3d end) {
/* 502 */     Entity entity = null;
/* 503 */     List<Entity> list = this.world.getEntitiesInAABBexcluding(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expandXyz(1.0D), ARROW_TARGETS);
/* 504 */     double d0 = 0.0D;
/*     */     
/* 506 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 508 */       Entity entity1 = list.get(i);
/*     */       
/* 510 */       if (entity1 != this.shootingEntity || this.ticksInAir >= 5) {
/*     */         
/* 512 */         AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(0.30000001192092896D);
/* 513 */         RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(start, end);
/*     */         
/* 515 */         if (raytraceresult != null) {
/*     */           
/* 517 */           double d1 = start.squareDistanceTo(raytraceresult.hitVec);
/*     */           
/* 519 */           if (d1 < d0 || d0 == 0.0D) {
/*     */             
/* 521 */             entity = entity1;
/* 522 */             d0 = d1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 528 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerFixesArrow(DataFixer fixer, String name) {}
/*     */ 
/*     */   
/*     */   public static void registerFixesArrow(DataFixer fixer) {
/* 537 */     registerFixesArrow(fixer, "Arrow");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 545 */     compound.setInteger("xTile", this.xTile);
/* 546 */     compound.setInteger("yTile", this.yTile);
/* 547 */     compound.setInteger("zTile", this.zTile);
/* 548 */     compound.setShort("life", (short)this.ticksInGround);
/* 549 */     ResourceLocation resourcelocation = (ResourceLocation)Block.REGISTRY.getNameForObject(this.inTile);
/* 550 */     compound.setString("inTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 551 */     compound.setByte("inData", (byte)this.inData);
/* 552 */     compound.setByte("shake", (byte)this.arrowShake);
/* 553 */     compound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/* 554 */     compound.setByte("pickup", (byte)this.pickupStatus.ordinal());
/* 555 */     compound.setDouble("damage", this.damage);
/* 556 */     compound.setBoolean("crit", getIsCritical());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 564 */     this.xTile = compound.getInteger("xTile");
/* 565 */     this.yTile = compound.getInteger("yTile");
/* 566 */     this.zTile = compound.getInteger("zTile");
/* 567 */     this.ticksInGround = compound.getShort("life");
/*     */     
/* 569 */     if (compound.hasKey("inTile", 8)) {
/*     */       
/* 571 */       this.inTile = Block.getBlockFromName(compound.getString("inTile"));
/*     */     }
/*     */     else {
/*     */       
/* 575 */       this.inTile = Block.getBlockById(compound.getByte("inTile") & 0xFF);
/*     */     } 
/*     */     
/* 578 */     this.inData = compound.getByte("inData") & 0xFF;
/* 579 */     this.arrowShake = compound.getByte("shake") & 0xFF;
/* 580 */     this.inGround = (compound.getByte("inGround") == 1);
/*     */     
/* 582 */     if (compound.hasKey("damage", 99))
/*     */     {
/* 584 */       this.damage = compound.getDouble("damage");
/*     */     }
/*     */     
/* 587 */     if (compound.hasKey("pickup", 99)) {
/*     */       
/* 589 */       this.pickupStatus = PickupStatus.getByOrdinal(compound.getByte("pickup"));
/*     */     }
/* 591 */     else if (compound.hasKey("player", 99)) {
/*     */       
/* 593 */       this.pickupStatus = compound.getBoolean("player") ? PickupStatus.ALLOWED : PickupStatus.DISALLOWED;
/*     */     } 
/*     */     
/* 596 */     setIsCritical(compound.getBoolean("crit"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onCollideWithPlayer(EntityPlayer entityIn) {
/* 604 */     if (!this.world.isRemote && this.inGround && this.arrowShake <= 0) {
/*     */       
/* 606 */       boolean flag = !(this.pickupStatus != PickupStatus.ALLOWED && (this.pickupStatus != PickupStatus.CREATIVE_ONLY || !entityIn.capabilities.isCreativeMode));
/*     */       
/* 608 */       if (this.pickupStatus == PickupStatus.ALLOWED && !entityIn.inventory.addItemStackToInventory(getArrowStack()))
/*     */       {
/* 610 */         flag = false;
/*     */       }
/*     */       
/* 613 */       if (flag) {
/*     */         
/* 615 */         entityIn.onItemPickup(this, 1);
/* 616 */         setDead();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ItemStack getArrowStack();
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 629 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDamage(double damageIn) {
/* 634 */     this.damage = damageIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDamage() {
/* 639 */     return this.damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKnockbackStrength(int knockbackStrengthIn) {
/* 647 */     this.knockbackStrength = knockbackStrengthIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeAttackedWithItem() {
/* 655 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 660 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIsCritical(boolean critical) {
/* 668 */     byte b0 = ((Byte)this.dataManager.get(CRITICAL)).byteValue();
/*     */     
/* 670 */     if (critical) {
/*     */       
/* 672 */       this.dataManager.set(CRITICAL, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     }
/*     */     else {
/*     */       
/* 676 */       this.dataManager.set(CRITICAL, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getIsCritical() {
/* 685 */     byte b0 = ((Byte)this.dataManager.get(CRITICAL)).byteValue();
/* 686 */     return ((b0 & 0x1) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190547_a(EntityLivingBase p_190547_1_, float p_190547_2_) {
/* 691 */     int i = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.POWER, p_190547_1_);
/* 692 */     int j = EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.PUNCH, p_190547_1_);
/* 693 */     setDamage((p_190547_2_ * 2.0F) + this.rand.nextGaussian() * 0.25D + (this.world.getDifficulty().getDifficultyId() * 0.11F));
/*     */     
/* 695 */     if (i > 0)
/*     */     {
/* 697 */       setDamage(getDamage() + i * 0.5D + 0.5D);
/*     */     }
/*     */     
/* 700 */     if (j > 0)
/*     */     {
/* 702 */       setKnockbackStrength(j);
/*     */     }
/*     */     
/* 705 */     if (EnchantmentHelper.getMaxEnchantmentLevel(Enchantments.FLAME, p_190547_1_) > 0)
/*     */     {
/* 707 */       setFire(100);
/*     */     }
/*     */   }
/*     */   
/*     */   public enum PickupStatus
/*     */   {
/* 713 */     DISALLOWED,
/* 714 */     ALLOWED,
/* 715 */     CREATIVE_ONLY;
/*     */ 
/*     */     
/*     */     public static PickupStatus getByOrdinal(int ordinal) {
/* 719 */       if (ordinal < 0 || ordinal > (values()).length)
/*     */       {
/* 721 */         ordinal = 0;
/*     */       }
/*     */       
/* 724 */       return values()[ordinal];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntityArrow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */