/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class EntityThrowable
/*     */   extends Entity
/*     */   implements IProjectile
/*     */ {
/*     */   private int xTile;
/*     */   private int yTile;
/*     */   private int zTile;
/*     */   private Block inTile;
/*     */   protected boolean inGround;
/*     */   public int throwableShake;
/*     */   protected EntityLivingBase thrower;
/*     */   private String throwerName;
/*     */   private int ticksInGround;
/*     */   private int ticksInAir;
/*     */   public Entity ignoreEntity;
/*     */   private int ignoreTime;
/*     */   
/*     */   public EntityThrowable(World worldIn) {
/*  43 */     super(worldIn);
/*  44 */     this.xTile = -1;
/*  45 */     this.yTile = -1;
/*  46 */     this.zTile = -1;
/*  47 */     setSize(0.25F, 0.25F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityThrowable(World worldIn, double x, double y, double z) {
/*  52 */     this(worldIn);
/*  53 */     setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityThrowable(World worldIn, EntityLivingBase throwerIn) {
/*  58 */     this(worldIn, throwerIn.posX, throwerIn.posY + throwerIn.getEyeHeight() - 0.10000000149011612D, throwerIn.posZ);
/*  59 */     this.thrower = throwerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/*  71 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*     */     
/*  73 */     if (Double.isNaN(d0))
/*     */     {
/*  75 */       d0 = 4.0D;
/*     */     }
/*     */     
/*  78 */     d0 *= 64.0D;
/*  79 */     return (distance < d0 * d0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHeadingFromThrower(Entity entityThrower, float rotationPitchIn, float rotationYawIn, float pitchOffset, float velocity, float inaccuracy) {
/*  87 */     float f = -MathHelper.sin(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
/*  88 */     float f1 = -MathHelper.sin((rotationPitchIn + pitchOffset) * 0.017453292F);
/*  89 */     float f2 = MathHelper.cos(rotationYawIn * 0.017453292F) * MathHelper.cos(rotationPitchIn * 0.017453292F);
/*  90 */     setThrowableHeading(f, f1, f2, velocity, inaccuracy);
/*  91 */     this.motionX += entityThrower.motionX;
/*  92 */     this.motionZ += entityThrower.motionZ;
/*     */     
/*  94 */     if (!entityThrower.onGround)
/*     */     {
/*  96 */       this.motionY += entityThrower.motionY;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
/* 105 */     float f = MathHelper.sqrt(x * x + y * y + z * z);
/* 106 */     x /= f;
/* 107 */     y /= f;
/* 108 */     z /= f;
/* 109 */     x += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 110 */     y += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 111 */     z += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 112 */     x *= velocity;
/* 113 */     y *= velocity;
/* 114 */     z *= velocity;
/* 115 */     this.motionX = x;
/* 116 */     this.motionY = y;
/* 117 */     this.motionZ = z;
/* 118 */     float f1 = MathHelper.sqrt(x * x + z * z);
/* 119 */     this.rotationYaw = (float)(MathHelper.atan2(x, z) * 57.29577951308232D);
/* 120 */     this.rotationPitch = (float)(MathHelper.atan2(y, f1) * 57.29577951308232D);
/* 121 */     this.prevRotationYaw = this.rotationYaw;
/* 122 */     this.prevRotationPitch = this.rotationPitch;
/* 123 */     this.ticksInGround = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/* 131 */     this.motionX = x;
/* 132 */     this.motionY = y;
/* 133 */     this.motionZ = z;
/*     */     
/* 135 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/*     */       
/* 137 */       float f = MathHelper.sqrt(x * x + z * z);
/* 138 */       this.rotationYaw = (float)(MathHelper.atan2(x, z) * 57.29577951308232D);
/* 139 */       this.rotationPitch = (float)(MathHelper.atan2(y, f) * 57.29577951308232D);
/* 140 */       this.prevRotationYaw = this.rotationYaw;
/* 141 */       this.prevRotationPitch = this.rotationPitch;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 150 */     this.lastTickPosX = this.posX;
/* 151 */     this.lastTickPosY = this.posY;
/* 152 */     this.lastTickPosZ = this.posZ;
/* 153 */     super.onUpdate();
/*     */     
/* 155 */     if (this.throwableShake > 0)
/*     */     {
/* 157 */       this.throwableShake--;
/*     */     }
/*     */     
/* 160 */     if (this.inGround) {
/*     */       
/* 162 */       if (this.world.getBlockState(new BlockPos(this.xTile, this.yTile, this.zTile)).getBlock() == this.inTile) {
/*     */         
/* 164 */         this.ticksInGround++;
/*     */         
/* 166 */         if (this.ticksInGround == 1200)
/*     */         {
/* 168 */           setDead();
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 174 */       this.inGround = false;
/* 175 */       this.motionX *= (this.rand.nextFloat() * 0.2F);
/* 176 */       this.motionY *= (this.rand.nextFloat() * 0.2F);
/* 177 */       this.motionZ *= (this.rand.nextFloat() * 0.2F);
/* 178 */       this.ticksInGround = 0;
/* 179 */       this.ticksInAir = 0;
/*     */     }
/*     */     else {
/*     */       
/* 183 */       this.ticksInAir++;
/*     */     } 
/*     */     
/* 186 */     Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
/* 187 */     Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 188 */     RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1);
/* 189 */     vec3d = new Vec3d(this.posX, this.posY, this.posZ);
/* 190 */     vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */     
/* 192 */     if (raytraceresult != null)
/*     */     {
/* 194 */       vec3d1 = new Vec3d(raytraceresult.hitVec.xCoord, raytraceresult.hitVec.yCoord, raytraceresult.hitVec.zCoord);
/*     */     }
/*     */     
/* 197 */     Entity entity = null;
/* 198 */     List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expandXyz(1.0D));
/* 199 */     double d0 = 0.0D;
/* 200 */     boolean flag = false;
/*     */     
/* 202 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 204 */       Entity entity1 = list.get(i);
/*     */       
/* 206 */       if (entity1.canBeCollidedWith())
/*     */       {
/* 208 */         if (entity1 == this.ignoreEntity) {
/*     */           
/* 210 */           flag = true;
/*     */         }
/* 212 */         else if (this.thrower != null && this.ticksExisted < 2 && this.ignoreEntity == null) {
/*     */           
/* 214 */           this.ignoreEntity = entity1;
/* 215 */           flag = true;
/*     */         }
/*     */         else {
/*     */           
/* 219 */           flag = false;
/* 220 */           AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(0.30000001192092896D);
/* 221 */           RayTraceResult raytraceresult1 = axisalignedbb.calculateIntercept(vec3d, vec3d1);
/*     */           
/* 223 */           if (raytraceresult1 != null) {
/*     */             
/* 225 */             double d1 = vec3d.squareDistanceTo(raytraceresult1.hitVec);
/*     */             
/* 227 */             if (d1 < d0 || d0 == 0.0D) {
/*     */               
/* 229 */               entity = entity1;
/* 230 */               d0 = d1;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 237 */     if (this.ignoreEntity != null)
/*     */     {
/* 239 */       if (flag) {
/*     */         
/* 241 */         this.ignoreTime = 2;
/*     */       }
/* 243 */       else if (this.ignoreTime-- <= 0) {
/*     */         
/* 245 */         this.ignoreEntity = null;
/*     */       } 
/*     */     }
/*     */     
/* 249 */     if (entity != null)
/*     */     {
/* 251 */       raytraceresult = new RayTraceResult(entity);
/*     */     }
/*     */     
/* 254 */     if (raytraceresult != null)
/*     */     {
/* 256 */       if (raytraceresult.typeOfHit == RayTraceResult.Type.BLOCK && this.world.getBlockState(raytraceresult.getBlockPos()).getBlock() == Blocks.PORTAL) {
/*     */         
/* 258 */         setPortal(raytraceresult.getBlockPos());
/*     */       }
/*     */       else {
/*     */         
/* 262 */         onImpact(raytraceresult);
/*     */       } 
/*     */     }
/*     */     
/* 266 */     this.posX += this.motionX;
/* 267 */     this.posY += this.motionY;
/* 268 */     this.posZ += this.motionZ;
/* 269 */     float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 270 */     this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 57.29577951308232D);
/*     */     
/* 272 */     for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * 57.29577951308232D); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 277 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*     */     {
/* 279 */       this.prevRotationPitch += 360.0F;
/*     */     }
/*     */     
/* 282 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*     */     {
/* 284 */       this.prevRotationYaw -= 360.0F;
/*     */     }
/*     */     
/* 287 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*     */     {
/* 289 */       this.prevRotationYaw += 360.0F;
/*     */     }
/*     */     
/* 292 */     this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 293 */     this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/* 294 */     float f1 = 0.99F;
/* 295 */     float f2 = getGravityVelocity();
/*     */     
/* 297 */     if (isInWater()) {
/*     */       
/* 299 */       for (int j = 0; j < 4; j++) {
/*     */         
/* 301 */         float f3 = 0.25F;
/* 302 */         this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */       } 
/*     */       
/* 305 */       f1 = 0.8F;
/*     */     } 
/*     */     
/* 308 */     this.motionX *= f1;
/* 309 */     this.motionY *= f1;
/* 310 */     this.motionZ *= f1;
/*     */     
/* 312 */     if (!hasNoGravity())
/*     */     {
/* 314 */       this.motionY -= f2;
/*     */     }
/*     */     
/* 317 */     setPosition(this.posX, this.posY, this.posZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getGravityVelocity() {
/* 325 */     return 0.03F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void onImpact(RayTraceResult paramRayTraceResult);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerFixesThrowable(DataFixer fixer, String name) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 342 */     compound.setInteger("xTile", this.xTile);
/* 343 */     compound.setInteger("yTile", this.yTile);
/* 344 */     compound.setInteger("zTile", this.zTile);
/* 345 */     ResourceLocation resourcelocation = (ResourceLocation)Block.REGISTRY.getNameForObject(this.inTile);
/* 346 */     compound.setString("inTile", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 347 */     compound.setByte("shake", (byte)this.throwableShake);
/* 348 */     compound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
/*     */     
/* 350 */     if ((this.throwerName == null || this.throwerName.isEmpty()) && this.thrower instanceof net.minecraft.entity.player.EntityPlayer)
/*     */     {
/* 352 */       this.throwerName = this.thrower.getName();
/*     */     }
/*     */     
/* 355 */     compound.setString("ownerName", (this.throwerName == null) ? "" : this.throwerName);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 363 */     this.xTile = compound.getInteger("xTile");
/* 364 */     this.yTile = compound.getInteger("yTile");
/* 365 */     this.zTile = compound.getInteger("zTile");
/*     */     
/* 367 */     if (compound.hasKey("inTile", 8)) {
/*     */       
/* 369 */       this.inTile = Block.getBlockFromName(compound.getString("inTile"));
/*     */     }
/*     */     else {
/*     */       
/* 373 */       this.inTile = Block.getBlockById(compound.getByte("inTile") & 0xFF);
/*     */     } 
/*     */     
/* 376 */     this.throwableShake = compound.getByte("shake") & 0xFF;
/* 377 */     this.inGround = (compound.getByte("inGround") == 1);
/* 378 */     this.thrower = null;
/* 379 */     this.throwerName = compound.getString("ownerName");
/*     */     
/* 381 */     if (this.throwerName != null && this.throwerName.isEmpty())
/*     */     {
/* 383 */       this.throwerName = null;
/*     */     }
/*     */     
/* 386 */     this.thrower = getThrower();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EntityLivingBase getThrower() {
/* 392 */     if (this.thrower == null && this.throwerName != null && !this.throwerName.isEmpty()) {
/*     */       
/* 394 */       this.thrower = (EntityLivingBase)this.world.getPlayerEntityByName(this.throwerName);
/*     */       
/* 396 */       if (this.thrower == null && this.world instanceof WorldServer) {
/*     */         
/*     */         try {
/*     */           
/* 400 */           Entity entity = ((WorldServer)this.world).getEntityFromUuid(UUID.fromString(this.throwerName));
/*     */           
/* 402 */           if (entity instanceof EntityLivingBase)
/*     */           {
/* 404 */             this.thrower = (EntityLivingBase)entity;
/*     */           }
/*     */         }
/* 407 */         catch (Throwable var2) {
/*     */           
/* 409 */           this.thrower = null;
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 414 */     return this.thrower;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntityThrowable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */