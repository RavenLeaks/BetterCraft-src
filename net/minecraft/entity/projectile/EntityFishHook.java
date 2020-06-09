/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockLiquid;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.entity.item.EntityItem;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.storage.loot.LootContext;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityFishHook extends Entity {
/*  36 */   private static final DataParameter<Integer> DATA_HOOKED_ENTITY = EntityDataManager.createKey(EntityFishHook.class, DataSerializers.VARINT);
/*     */   private boolean inGround;
/*     */   private int ticksInGround;
/*     */   private EntityPlayer angler;
/*     */   private int ticksInAir;
/*     */   private int ticksCatchable;
/*     */   private int ticksCaughtDelay;
/*     */   private int ticksCatchableDelay;
/*     */   private float fishApproachAngle;
/*     */   public Entity caughtEntity;
/*  46 */   private State field_190627_av = State.FLYING;
/*     */   
/*     */   private int field_191518_aw;
/*     */   private int field_191519_ax;
/*     */   
/*     */   public EntityFishHook(World p_i47290_1_, EntityPlayer p_i47290_2_, double p_i47290_3_, double p_i47290_5_, double p_i47290_7_) {
/*  52 */     super(p_i47290_1_);
/*  53 */     func_190626_a(p_i47290_2_);
/*  54 */     setPosition(p_i47290_3_, p_i47290_5_, p_i47290_7_);
/*  55 */     this.prevPosX = this.posX;
/*  56 */     this.prevPosY = this.posY;
/*  57 */     this.prevPosZ = this.posZ;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityFishHook(World worldIn, EntityPlayer fishingPlayer) {
/*  62 */     super(worldIn);
/*  63 */     func_190626_a(fishingPlayer);
/*  64 */     func_190620_n();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190626_a(EntityPlayer p_190626_1_) {
/*  69 */     setSize(0.25F, 0.25F);
/*  70 */     this.ignoreFrustumCheck = true;
/*  71 */     this.angler = p_190626_1_;
/*  72 */     this.angler.fishEntity = this;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191516_a(int p_191516_1_) {
/*  77 */     this.field_191519_ax = p_191516_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_191517_b(int p_191517_1_) {
/*  82 */     this.field_191518_aw = p_191517_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190620_n() {
/*  87 */     float f = this.angler.prevRotationPitch + this.angler.rotationPitch - this.angler.prevRotationPitch;
/*  88 */     float f1 = this.angler.prevRotationYaw + this.angler.rotationYaw - this.angler.prevRotationYaw;
/*  89 */     float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
/*  90 */     float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
/*  91 */     float f4 = -MathHelper.cos(-f * 0.017453292F);
/*  92 */     float f5 = MathHelper.sin(-f * 0.017453292F);
/*  93 */     double d0 = this.angler.prevPosX + this.angler.posX - this.angler.prevPosX - f3 * 0.3D;
/*  94 */     double d1 = this.angler.prevPosY + this.angler.posY - this.angler.prevPosY + this.angler.getEyeHeight();
/*  95 */     double d2 = this.angler.prevPosZ + this.angler.posZ - this.angler.prevPosZ - f2 * 0.3D;
/*  96 */     setLocationAndAngles(d0, d1, d2, f1, f);
/*  97 */     this.motionX = -f3;
/*  98 */     this.motionY = MathHelper.clamp(-(f5 / f4), -5.0F, 5.0F);
/*  99 */     this.motionZ = -f2;
/* 100 */     float f6 = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
/* 101 */     this.motionX *= 0.6D / f6 + 0.5D + this.rand.nextGaussian() * 0.0045D;
/* 102 */     this.motionY *= 0.6D / f6 + 0.5D + this.rand.nextGaussian() * 0.0045D;
/* 103 */     this.motionZ *= 0.6D / f6 + 0.5D + this.rand.nextGaussian() * 0.0045D;
/* 104 */     float f7 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 105 */     this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 57.29577951308232D);
/* 106 */     this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f7) * 57.29577951308232D);
/* 107 */     this.prevRotationYaw = this.rotationYaw;
/* 108 */     this.prevRotationPitch = this.rotationPitch;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 113 */     getDataManager().register(DATA_HOOKED_ENTITY, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 118 */     if (DATA_HOOKED_ENTITY.equals(key)) {
/*     */       
/* 120 */       int i = ((Integer)getDataManager().get(DATA_HOOKED_ENTITY)).intValue();
/* 121 */       this.caughtEntity = (i > 0) ? this.world.getEntityByID(i - 1) : null;
/*     */     } 
/*     */     
/* 124 */     super.notifyDataManagerChange(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/* 132 */     double d0 = 64.0D;
/* 133 */     return (distance < 4096.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch, int posRotationIncrements, boolean teleport) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 148 */     super.onUpdate();
/*     */     
/* 150 */     if (this.angler == null) {
/*     */       
/* 152 */       setDead();
/*     */     }
/* 154 */     else if (this.world.isRemote || !func_190625_o()) {
/*     */       
/* 156 */       if (this.inGround) {
/*     */         
/* 158 */         this.ticksInGround++;
/*     */         
/* 160 */         if (this.ticksInGround >= 1200) {
/*     */           
/* 162 */           setDead();
/*     */           
/*     */           return;
/*     */         } 
/*     */       } 
/* 167 */       float f = 0.0F;
/* 168 */       BlockPos blockpos = new BlockPos(this);
/* 169 */       IBlockState iblockstate = this.world.getBlockState(blockpos);
/*     */       
/* 171 */       if (iblockstate.getMaterial() == Material.WATER)
/*     */       {
/* 173 */         f = BlockLiquid.func_190973_f(iblockstate, (IBlockAccess)this.world, blockpos);
/*     */       }
/*     */       
/* 176 */       if (this.field_190627_av == State.FLYING) {
/*     */         
/* 178 */         if (this.caughtEntity != null) {
/*     */           
/* 180 */           this.motionX = 0.0D;
/* 181 */           this.motionY = 0.0D;
/* 182 */           this.motionZ = 0.0D;
/* 183 */           this.field_190627_av = State.HOOKED_IN_ENTITY;
/*     */           
/*     */           return;
/*     */         } 
/* 187 */         if (f > 0.0F) {
/*     */           
/* 189 */           this.motionX *= 0.3D;
/* 190 */           this.motionY *= 0.2D;
/* 191 */           this.motionZ *= 0.3D;
/* 192 */           this.field_190627_av = State.BOBBING;
/*     */           
/*     */           return;
/*     */         } 
/* 196 */         if (!this.world.isRemote)
/*     */         {
/* 198 */           func_190624_r();
/*     */         }
/*     */         
/* 201 */         if (!this.inGround && !this.onGround && !this.isCollidedHorizontally)
/*     */         {
/* 203 */           this.ticksInAir++;
/*     */         }
/*     */         else
/*     */         {
/* 207 */           this.ticksInAir = 0;
/* 208 */           this.motionX = 0.0D;
/* 209 */           this.motionY = 0.0D;
/* 210 */           this.motionZ = 0.0D;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 215 */         if (this.field_190627_av == State.HOOKED_IN_ENTITY) {
/*     */           
/* 217 */           if (this.caughtEntity != null)
/*     */           {
/* 219 */             if (this.caughtEntity.isDead) {
/*     */               
/* 221 */               this.caughtEntity = null;
/* 222 */               this.field_190627_av = State.FLYING;
/*     */             }
/*     */             else {
/*     */               
/* 226 */               this.posX = this.caughtEntity.posX;
/* 227 */               double d2 = this.caughtEntity.height;
/* 228 */               this.posY = (this.caughtEntity.getEntityBoundingBox()).minY + d2 * 0.8D;
/* 229 */               this.posZ = this.caughtEntity.posZ;
/* 230 */               setPosition(this.posX, this.posY, this.posZ);
/*     */             } 
/*     */           }
/*     */           
/*     */           return;
/*     */         } 
/*     */         
/* 237 */         if (this.field_190627_av == State.BOBBING) {
/*     */           
/* 239 */           this.motionX *= 0.9D;
/* 240 */           this.motionZ *= 0.9D;
/* 241 */           double d0 = this.posY + this.motionY - blockpos.getY() - f;
/*     */           
/* 243 */           if (Math.abs(d0) < 0.01D)
/*     */           {
/* 245 */             d0 += Math.signum(d0) * 0.1D;
/*     */           }
/*     */           
/* 248 */           this.motionY -= d0 * this.rand.nextFloat() * 0.2D;
/*     */           
/* 250 */           if (!this.world.isRemote && f > 0.0F)
/*     */           {
/* 252 */             func_190621_a(blockpos);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 257 */       if (iblockstate.getMaterial() != Material.WATER)
/*     */       {
/* 259 */         this.motionY -= 0.03D;
/*     */       }
/*     */       
/* 262 */       moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/* 263 */       func_190623_q();
/* 264 */       double d1 = 0.92D;
/* 265 */       this.motionX *= 0.92D;
/* 266 */       this.motionY *= 0.92D;
/* 267 */       this.motionZ *= 0.92D;
/* 268 */       setPosition(this.posX, this.posY, this.posZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_190625_o() {
/* 274 */     ItemStack itemstack = this.angler.getHeldItemMainhand();
/* 275 */     ItemStack itemstack1 = this.angler.getHeldItemOffhand();
/* 276 */     boolean flag = (itemstack.getItem() == Items.FISHING_ROD);
/* 277 */     boolean flag1 = (itemstack1.getItem() == Items.FISHING_ROD);
/*     */     
/* 279 */     if (!this.angler.isDead && this.angler.isEntityAlive() && (flag || flag1) && getDistanceSqToEntity((Entity)this.angler) <= 1024.0D)
/*     */     {
/* 281 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 285 */     setDead();
/* 286 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void func_190623_q() {
/* 292 */     float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 293 */     this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 57.29577951308232D);
/*     */     
/* 295 */     for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * 57.29577951308232D); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 300 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*     */     {
/* 302 */       this.prevRotationPitch += 360.0F;
/*     */     }
/*     */     
/* 305 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*     */     {
/* 307 */       this.prevRotationYaw -= 360.0F;
/*     */     }
/*     */     
/* 310 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*     */     {
/* 312 */       this.prevRotationYaw += 360.0F;
/*     */     }
/*     */     
/* 315 */     this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 316 */     this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190624_r() {
/* 321 */     Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
/* 322 */     Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 323 */     RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1, false, true, false);
/* 324 */     vec3d = new Vec3d(this.posX, this.posY, this.posZ);
/* 325 */     vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */     
/* 327 */     if (raytraceresult != null)
/*     */     {
/* 329 */       vec3d1 = new Vec3d(raytraceresult.hitVec.xCoord, raytraceresult.hitVec.yCoord, raytraceresult.hitVec.zCoord);
/*     */     }
/*     */     
/* 332 */     Entity entity = null;
/* 333 */     List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expandXyz(1.0D));
/* 334 */     double d0 = 0.0D;
/*     */     
/* 336 */     for (Entity entity1 : list) {
/*     */       
/* 338 */       if (canBeHooked(entity1) && (entity1 != this.angler || this.ticksInAir >= 5)) {
/*     */         
/* 340 */         AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(0.30000001192092896D);
/* 341 */         RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
/*     */         
/* 343 */         if (raytraceresult1 != null) {
/*     */           
/* 345 */           double d1 = vec3d.squareDistanceTo(raytraceresult1.hitVec);
/*     */           
/* 347 */           if (d1 < d0 || d0 == 0.0D) {
/*     */             
/* 349 */             entity = entity1;
/* 350 */             d0 = d1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 356 */     if (entity != null)
/*     */     {
/* 358 */       raytraceresult = new RayTraceResult(entity);
/*     */     }
/*     */     
/* 361 */     if (raytraceresult != null && raytraceresult.typeOfHit != RayTraceResult.Type.MISS)
/*     */     {
/* 363 */       if (raytraceresult.typeOfHit == RayTraceResult.Type.ENTITY) {
/*     */         
/* 365 */         this.caughtEntity = raytraceresult.entityHit;
/* 366 */         func_190622_s();
/*     */       }
/*     */       else {
/*     */         
/* 370 */         this.inGround = true;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190622_s() {
/* 377 */     getDataManager().set(DATA_HOOKED_ENTITY, Integer.valueOf(this.caughtEntity.getEntityId() + 1));
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190621_a(BlockPos p_190621_1_) {
/* 382 */     WorldServer worldserver = (WorldServer)this.world;
/* 383 */     int i = 1;
/* 384 */     BlockPos blockpos = p_190621_1_.up();
/*     */     
/* 386 */     if (this.rand.nextFloat() < 0.25F && this.world.isRainingAt(blockpos))
/*     */     {
/* 388 */       i++;
/*     */     }
/*     */     
/* 391 */     if (this.rand.nextFloat() < 0.5F && !this.world.canSeeSky(blockpos))
/*     */     {
/* 393 */       i--;
/*     */     }
/*     */     
/* 396 */     if (this.ticksCatchable > 0) {
/*     */       
/* 398 */       this.ticksCatchable--;
/*     */       
/* 400 */       if (this.ticksCatchable <= 0)
/*     */       {
/* 402 */         this.ticksCaughtDelay = 0;
/* 403 */         this.ticksCatchableDelay = 0;
/*     */       }
/*     */       else
/*     */       {
/* 407 */         this.motionY -= 0.2D * this.rand.nextFloat() * this.rand.nextFloat();
/*     */       }
/*     */     
/* 410 */     } else if (this.ticksCatchableDelay > 0) {
/*     */       
/* 412 */       this.ticksCatchableDelay -= i;
/*     */       
/* 414 */       if (this.ticksCatchableDelay > 0) {
/*     */         
/* 416 */         this.fishApproachAngle = (float)(this.fishApproachAngle + this.rand.nextGaussian() * 4.0D);
/* 417 */         float f = this.fishApproachAngle * 0.017453292F;
/* 418 */         float f1 = MathHelper.sin(f);
/* 419 */         float f2 = MathHelper.cos(f);
/* 420 */         double d0 = this.posX + (f1 * this.ticksCatchableDelay * 0.1F);
/* 421 */         double d1 = (MathHelper.floor((getEntityBoundingBox()).minY) + 1.0F);
/* 422 */         double d2 = this.posZ + (f2 * this.ticksCatchableDelay * 0.1F);
/* 423 */         Block block = worldserver.getBlockState(new BlockPos(d0, d1 - 1.0D, d2)).getBlock();
/*     */         
/* 425 */         if (block == Blocks.WATER || block == Blocks.FLOWING_WATER)
/*     */         {
/* 427 */           if (this.rand.nextFloat() < 0.15F)
/*     */           {
/* 429 */             worldserver.spawnParticle(EnumParticleTypes.WATER_BUBBLE, d0, d1 - 0.10000000149011612D, d2, 1, f1, 0.1D, f2, 0.0D, new int[0]);
/*     */           }
/*     */           
/* 432 */           float f3 = f1 * 0.04F;
/* 433 */           float f4 = f2 * 0.04F;
/* 434 */           worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, d0, d1, d2, 0, f4, 0.01D, -f3, 1.0D, new int[0]);
/* 435 */           worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, d0, d1, d2, 0, -f4, 0.01D, f3, 1.0D, new int[0]);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 440 */         this.motionY = (-0.4F * MathHelper.nextFloat(this.rand, 0.6F, 1.0F));
/* 441 */         playSound(SoundEvents.ENTITY_BOBBER_SPLASH, 0.25F, 1.0F + (this.rand.nextFloat() - this.rand.nextFloat()) * 0.4F);
/* 442 */         double d3 = (getEntityBoundingBox()).minY + 0.5D;
/* 443 */         worldserver.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX, d3, this.posZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0D, this.width, 0.20000000298023224D, new int[0]);
/* 444 */         worldserver.spawnParticle(EnumParticleTypes.WATER_WAKE, this.posX, d3, this.posZ, (int)(1.0F + this.width * 20.0F), this.width, 0.0D, this.width, 0.20000000298023224D, new int[0]);
/* 445 */         this.ticksCatchable = MathHelper.getInt(this.rand, 20, 40);
/*     */       }
/*     */     
/* 448 */     } else if (this.ticksCaughtDelay > 0) {
/*     */       
/* 450 */       this.ticksCaughtDelay -= i;
/* 451 */       float f5 = 0.15F;
/*     */       
/* 453 */       if (this.ticksCaughtDelay < 20) {
/*     */         
/* 455 */         f5 = (float)(f5 + (20 - this.ticksCaughtDelay) * 0.05D);
/*     */       }
/* 457 */       else if (this.ticksCaughtDelay < 40) {
/*     */         
/* 459 */         f5 = (float)(f5 + (40 - this.ticksCaughtDelay) * 0.02D);
/*     */       }
/* 461 */       else if (this.ticksCaughtDelay < 60) {
/*     */         
/* 463 */         f5 = (float)(f5 + (60 - this.ticksCaughtDelay) * 0.01D);
/*     */       } 
/*     */       
/* 466 */       if (this.rand.nextFloat() < f5) {
/*     */         
/* 468 */         float f6 = MathHelper.nextFloat(this.rand, 0.0F, 360.0F) * 0.017453292F;
/* 469 */         float f7 = MathHelper.nextFloat(this.rand, 25.0F, 60.0F);
/* 470 */         double d4 = this.posX + (MathHelper.sin(f6) * f7 * 0.1F);
/* 471 */         double d5 = (MathHelper.floor((getEntityBoundingBox()).minY) + 1.0F);
/* 472 */         double d6 = this.posZ + (MathHelper.cos(f6) * f7 * 0.1F);
/* 473 */         Block block1 = worldserver.getBlockState(new BlockPos((int)d4, (int)d5 - 1, (int)d6)).getBlock();
/*     */         
/* 475 */         if (block1 == Blocks.WATER || block1 == Blocks.FLOWING_WATER)
/*     */         {
/* 477 */           worldserver.spawnParticle(EnumParticleTypes.WATER_SPLASH, d4, d5, d6, 2 + this.rand.nextInt(2), 0.10000000149011612D, 0.0D, 0.10000000149011612D, 0.0D, new int[0]);
/*     */         }
/*     */       } 
/*     */       
/* 481 */       if (this.ticksCaughtDelay <= 0)
/*     */       {
/* 483 */         this.fishApproachAngle = MathHelper.nextFloat(this.rand, 0.0F, 360.0F);
/* 484 */         this.ticksCatchableDelay = MathHelper.getInt(this.rand, 20, 80);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 489 */       this.ticksCaughtDelay = MathHelper.getInt(this.rand, 100, 600);
/* 490 */       this.ticksCaughtDelay -= this.field_191519_ax * 20 * 5;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canBeHooked(Entity p_189739_1_) {
/* 496 */     return !(!p_189739_1_.canBeCollidedWith() && !(p_189739_1_ instanceof EntityItem));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int handleHookRetraction() {
/* 515 */     if (!this.world.isRemote && this.angler != null) {
/*     */       
/* 517 */       int i = 0;
/*     */       
/* 519 */       if (this.caughtEntity != null) {
/*     */         
/* 521 */         bringInHookedEntity();
/* 522 */         this.world.setEntityState(this, (byte)31);
/* 523 */         i = (this.caughtEntity instanceof EntityItem) ? 3 : 5;
/*     */       }
/* 525 */       else if (this.ticksCatchable > 0) {
/*     */         
/* 527 */         LootContext.Builder lootcontext$builder = new LootContext.Builder((WorldServer)this.world);
/* 528 */         lootcontext$builder.withLuck(this.field_191518_aw + this.angler.getLuck());
/*     */         
/* 530 */         for (ItemStack itemstack : this.world.getLootTableManager().getLootTableFromLocation(LootTableList.GAMEPLAY_FISHING).generateLootForPools(this.rand, lootcontext$builder.build())) {
/*     */           
/* 532 */           EntityItem entityitem = new EntityItem(this.world, this.posX, this.posY, this.posZ, itemstack);
/* 533 */           double d0 = this.angler.posX - this.posX;
/* 534 */           double d1 = this.angler.posY - this.posY;
/* 535 */           double d2 = this.angler.posZ - this.posZ;
/* 536 */           double d3 = MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/* 537 */           double d4 = 0.1D;
/* 538 */           entityitem.motionX = d0 * 0.1D;
/* 539 */           entityitem.motionY = d1 * 0.1D + MathHelper.sqrt(d3) * 0.08D;
/* 540 */           entityitem.motionZ = d2 * 0.1D;
/* 541 */           this.world.spawnEntityInWorld((Entity)entityitem);
/* 542 */           this.angler.world.spawnEntityInWorld((Entity)new EntityXPOrb(this.angler.world, this.angler.posX, this.angler.posY + 0.5D, this.angler.posZ + 0.5D, this.rand.nextInt(6) + 1));
/* 543 */           Item item = itemstack.getItem();
/*     */           
/* 545 */           if (item == Items.FISH || item == Items.COOKED_FISH)
/*     */           {
/* 547 */             this.angler.addStat(StatList.FISH_CAUGHT, 1);
/*     */           }
/*     */         } 
/*     */         
/* 551 */         i = 1;
/*     */       } 
/*     */       
/* 554 */       if (this.inGround)
/*     */       {
/* 556 */         i = 2;
/*     */       }
/*     */       
/* 559 */       setDead();
/* 560 */       return i;
/*     */     } 
/*     */ 
/*     */     
/* 564 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 570 */     if (id == 31 && this.world.isRemote && this.caughtEntity instanceof EntityPlayer && ((EntityPlayer)this.caughtEntity).isUser())
/*     */     {
/* 572 */       bringInHookedEntity();
/*     */     }
/*     */     
/* 575 */     super.handleStatusUpdate(id);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void bringInHookedEntity() {
/* 580 */     if (this.angler != null) {
/*     */       
/* 582 */       double d0 = this.angler.posX - this.posX;
/* 583 */       double d1 = this.angler.posY - this.posY;
/* 584 */       double d2 = this.angler.posZ - this.posZ;
/* 585 */       double d3 = 0.1D;
/* 586 */       this.caughtEntity.motionX += d0 * 0.1D;
/* 587 */       this.caughtEntity.motionY += d1 * 0.1D;
/* 588 */       this.caughtEntity.motionZ += d2 * 0.1D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/* 598 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDead() {
/* 606 */     super.setDead();
/*     */     
/* 608 */     if (this.angler != null)
/*     */     {
/* 610 */       this.angler.fishEntity = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPlayer func_190619_l() {
/* 616 */     return this.angler;
/*     */   }
/*     */   
/*     */   enum State
/*     */   {
/* 621 */     FLYING,
/* 622 */     HOOKED_IN_ENTITY,
/* 623 */     BOBBING;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntityFishHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */