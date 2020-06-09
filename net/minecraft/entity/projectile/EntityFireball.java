/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class EntityFireball
/*     */   extends Entity {
/*     */   public EntityLivingBase shootingEntity;
/*     */   private int ticksAlive;
/*     */   private int ticksInAir;
/*     */   public double accelerationX;
/*     */   public double accelerationY;
/*     */   public double accelerationZ;
/*     */   
/*     */   public EntityFireball(World worldIn) {
/*  27 */     super(worldIn);
/*  28 */     setSize(1.0F, 1.0F);
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
/*  40 */     double d0 = getEntityBoundingBox().getAverageEdgeLength() * 4.0D;
/*     */     
/*  42 */     if (Double.isNaN(d0))
/*     */     {
/*  44 */       d0 = 4.0D;
/*     */     }
/*     */     
/*  47 */     d0 *= 64.0D;
/*  48 */     return (distance < d0 * d0);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityFireball(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ) {
/*  53 */     super(worldIn);
/*  54 */     setSize(1.0F, 1.0F);
/*  55 */     setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
/*  56 */     setPosition(x, y, z);
/*  57 */     double d0 = MathHelper.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
/*  58 */     this.accelerationX = accelX / d0 * 0.1D;
/*  59 */     this.accelerationY = accelY / d0 * 0.1D;
/*  60 */     this.accelerationZ = accelZ / d0 * 0.1D;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityFireball(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ) {
/*  65 */     super(worldIn);
/*  66 */     this.shootingEntity = shooter;
/*  67 */     setSize(1.0F, 1.0F);
/*  68 */     setLocationAndAngles(shooter.posX, shooter.posY, shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
/*  69 */     setPosition(this.posX, this.posY, this.posZ);
/*  70 */     this.motionX = 0.0D;
/*  71 */     this.motionY = 0.0D;
/*  72 */     this.motionZ = 0.0D;
/*  73 */     accelX += this.rand.nextGaussian() * 0.4D;
/*  74 */     accelY += this.rand.nextGaussian() * 0.4D;
/*  75 */     accelZ += this.rand.nextGaussian() * 0.4D;
/*  76 */     double d0 = MathHelper.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
/*  77 */     this.accelerationX = accelX / d0 * 0.1D;
/*  78 */     this.accelerationY = accelY / d0 * 0.1D;
/*  79 */     this.accelerationZ = accelZ / d0 * 0.1D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  87 */     if (this.world.isRemote || ((this.shootingEntity == null || !this.shootingEntity.isDead) && this.world.isBlockLoaded(new BlockPos(this)))) {
/*     */       
/*  89 */       super.onUpdate();
/*     */       
/*  91 */       if (isFireballFiery())
/*     */       {
/*  93 */         setFire(1);
/*     */       }
/*     */       
/*  96 */       this.ticksInAir++;
/*  97 */       RayTraceResult raytraceresult = ProjectileHelper.forwardsRaycast(this, true, (this.ticksInAir >= 25), (Entity)this.shootingEntity);
/*     */       
/*  99 */       if (raytraceresult != null)
/*     */       {
/* 101 */         onImpact(raytraceresult);
/*     */       }
/*     */       
/* 104 */       this.posX += this.motionX;
/* 105 */       this.posY += this.motionY;
/* 106 */       this.posZ += this.motionZ;
/* 107 */       ProjectileHelper.rotateTowardsMovement(this, 0.2F);
/* 108 */       float f = getMotionFactor();
/*     */       
/* 110 */       if (isInWater()) {
/*     */         
/* 112 */         for (int i = 0; i < 4; i++) {
/*     */           
/* 114 */           float f1 = 0.25F;
/* 115 */           this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ, new int[0]);
/*     */         } 
/*     */         
/* 118 */         f = 0.8F;
/*     */       } 
/*     */       
/* 121 */       this.motionX += this.accelerationX;
/* 122 */       this.motionY += this.accelerationY;
/* 123 */       this.motionZ += this.accelerationZ;
/* 124 */       this.motionX *= f;
/* 125 */       this.motionY *= f;
/* 126 */       this.motionZ *= f;
/* 127 */       this.world.spawnParticle(getParticleType(), this.posX, this.posY + 0.5D, this.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/* 128 */       setPosition(this.posX, this.posY, this.posZ);
/*     */     }
/*     */     else {
/*     */       
/* 132 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean isFireballFiery() {
/* 138 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected EnumParticleTypes getParticleType() {
/* 143 */     return EnumParticleTypes.SMOKE_NORMAL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float getMotionFactor() {
/* 151 */     return 0.95F;
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
/*     */   public static void registerFixesFireball(DataFixer fixer, String name) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 168 */     compound.setTag("direction", (NBTBase)newDoubleNBTList(new double[] { this.motionX, this.motionY, this.motionZ }));
/* 169 */     compound.setTag("power", (NBTBase)newDoubleNBTList(new double[] { this.accelerationX, this.accelerationY, this.accelerationZ }));
/* 170 */     compound.setInteger("life", this.ticksAlive);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 178 */     if (compound.hasKey("power", 9)) {
/*     */       
/* 180 */       NBTTagList nbttaglist = compound.getTagList("power", 6);
/*     */       
/* 182 */       if (nbttaglist.tagCount() == 3) {
/*     */         
/* 184 */         this.accelerationX = nbttaglist.getDoubleAt(0);
/* 185 */         this.accelerationY = nbttaglist.getDoubleAt(1);
/* 186 */         this.accelerationZ = nbttaglist.getDoubleAt(2);
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     this.ticksAlive = compound.getInteger("life");
/*     */     
/* 192 */     if (compound.hasKey("direction", 9) && compound.getTagList("direction", 6).tagCount() == 3) {
/*     */       
/* 194 */       NBTTagList nbttaglist1 = compound.getTagList("direction", 6);
/* 195 */       this.motionX = nbttaglist1.getDoubleAt(0);
/* 196 */       this.motionY = nbttaglist1.getDoubleAt(1);
/* 197 */       this.motionZ = nbttaglist1.getDoubleAt(2);
/*     */     }
/*     */     else {
/*     */       
/* 201 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 210 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCollisionBorderSize() {
/* 215 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 223 */     if (isEntityInvulnerable(source))
/*     */     {
/* 225 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 229 */     setBeenAttacked();
/*     */     
/* 231 */     if (source.getEntity() != null) {
/*     */       
/* 233 */       Vec3d vec3d = source.getEntity().getLookVec();
/*     */       
/* 235 */       if (vec3d != null) {
/*     */         
/* 237 */         this.motionX = vec3d.xCoord;
/* 238 */         this.motionY = vec3d.yCoord;
/* 239 */         this.motionZ = vec3d.zCoord;
/* 240 */         this.accelerationX = this.motionX * 0.1D;
/* 241 */         this.accelerationY = this.motionY * 0.1D;
/* 242 */         this.accelerationZ = this.motionZ * 0.1D;
/*     */       } 
/*     */       
/* 245 */       if (source.getEntity() instanceof EntityLivingBase)
/*     */       {
/* 247 */         this.shootingEntity = (EntityLivingBase)source.getEntity();
/*     */       }
/*     */       
/* 250 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 254 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness() {
/* 264 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender() {
/* 269 */     return 15728880;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntityFireball.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */