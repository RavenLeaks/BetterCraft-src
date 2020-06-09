/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTUtil;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class EntityShulkerBullet
/*     */   extends Entity {
/*     */   private EntityLivingBase owner;
/*     */   private Entity target;
/*     */   @Nullable
/*     */   private EnumFacing direction;
/*     */   private int steps;
/*     */   private double targetDeltaX;
/*     */   private double targetDeltaY;
/*     */   private double targetDeltaZ;
/*     */   @Nullable
/*     */   private UUID ownerUniqueId;
/*     */   private BlockPos ownerBlockPos;
/*     */   @Nullable
/*     */   private UUID targetUniqueId;
/*     */   private BlockPos targetBlockPos;
/*     */   
/*     */   public EntityShulkerBullet(World worldIn) {
/*  46 */     super(worldIn);
/*  47 */     setSize(0.3125F, 0.3125F);
/*  48 */     this.noClip = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundCategory getSoundCategory() {
/*  53 */     return SoundCategory.HOSTILE;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityShulkerBullet(World worldIn, double x, double y, double z, double motionXIn, double motionYIn, double motionZIn) {
/*  58 */     this(worldIn);
/*  59 */     setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
/*  60 */     this.motionX = motionXIn;
/*  61 */     this.motionY = motionYIn;
/*  62 */     this.motionZ = motionZIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityShulkerBullet(World worldIn, EntityLivingBase ownerIn, Entity targetIn, EnumFacing.Axis p_i46772_4_) {
/*  67 */     this(worldIn);
/*  68 */     this.owner = ownerIn;
/*  69 */     BlockPos blockpos = new BlockPos((Entity)ownerIn);
/*  70 */     double d0 = blockpos.getX() + 0.5D;
/*  71 */     double d1 = blockpos.getY() + 0.5D;
/*  72 */     double d2 = blockpos.getZ() + 0.5D;
/*  73 */     setLocationAndAngles(d0, d1, d2, this.rotationYaw, this.rotationPitch);
/*  74 */     this.target = targetIn;
/*  75 */     this.direction = EnumFacing.UP;
/*  76 */     selectNextMoveDirection(p_i46772_4_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound compound) {
/*  84 */     if (this.owner != null) {
/*     */       
/*  86 */       BlockPos blockpos = new BlockPos((Entity)this.owner);
/*  87 */       NBTTagCompound nbttagcompound = NBTUtil.createUUIDTag(this.owner.getUniqueID());
/*  88 */       nbttagcompound.setInteger("X", blockpos.getX());
/*  89 */       nbttagcompound.setInteger("Y", blockpos.getY());
/*  90 */       nbttagcompound.setInteger("Z", blockpos.getZ());
/*  91 */       compound.setTag("Owner", (NBTBase)nbttagcompound);
/*     */     } 
/*     */     
/*  94 */     if (this.target != null) {
/*     */       
/*  96 */       BlockPos blockpos1 = new BlockPos(this.target);
/*  97 */       NBTTagCompound nbttagcompound1 = NBTUtil.createUUIDTag(this.target.getUniqueID());
/*  98 */       nbttagcompound1.setInteger("X", blockpos1.getX());
/*  99 */       nbttagcompound1.setInteger("Y", blockpos1.getY());
/* 100 */       nbttagcompound1.setInteger("Z", blockpos1.getZ());
/* 101 */       compound.setTag("Target", (NBTBase)nbttagcompound1);
/*     */     } 
/*     */     
/* 104 */     if (this.direction != null)
/*     */     {
/* 106 */       compound.setInteger("Dir", this.direction.getIndex());
/*     */     }
/*     */     
/* 109 */     compound.setInteger("Steps", this.steps);
/* 110 */     compound.setDouble("TXD", this.targetDeltaX);
/* 111 */     compound.setDouble("TYD", this.targetDeltaY);
/* 112 */     compound.setDouble("TZD", this.targetDeltaZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound compound) {
/* 120 */     this.steps = compound.getInteger("Steps");
/* 121 */     this.targetDeltaX = compound.getDouble("TXD");
/* 122 */     this.targetDeltaY = compound.getDouble("TYD");
/* 123 */     this.targetDeltaZ = compound.getDouble("TZD");
/*     */     
/* 125 */     if (compound.hasKey("Dir", 99))
/*     */     {
/* 127 */       this.direction = EnumFacing.getFront(compound.getInteger("Dir"));
/*     */     }
/*     */     
/* 130 */     if (compound.hasKey("Owner", 10)) {
/*     */       
/* 132 */       NBTTagCompound nbttagcompound = compound.getCompoundTag("Owner");
/* 133 */       this.ownerUniqueId = NBTUtil.getUUIDFromTag(nbttagcompound);
/* 134 */       this.ownerBlockPos = new BlockPos(nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Y"), nbttagcompound.getInteger("Z"));
/*     */     } 
/*     */     
/* 137 */     if (compound.hasKey("Target", 10)) {
/*     */       
/* 139 */       NBTTagCompound nbttagcompound1 = compound.getCompoundTag("Target");
/* 140 */       this.targetUniqueId = NBTUtil.getUUIDFromTag(nbttagcompound1);
/* 141 */       this.targetBlockPos = new BlockPos(nbttagcompound1.getInteger("X"), nbttagcompound1.getInteger("Y"), nbttagcompound1.getInteger("Z"));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void entityInit() {}
/*     */ 
/*     */   
/*     */   private void setDirection(@Nullable EnumFacing directionIn) {
/* 151 */     this.direction = directionIn;
/*     */   }
/*     */   
/*     */   private void selectNextMoveDirection(@Nullable EnumFacing.Axis p_184569_1_) {
/*     */     BlockPos blockpos;
/* 156 */     double d0 = 0.5D;
/*     */ 
/*     */     
/* 159 */     if (this.target == null) {
/*     */       
/* 161 */       blockpos = (new BlockPos(this)).down();
/*     */     }
/*     */     else {
/*     */       
/* 165 */       d0 = this.target.height * 0.5D;
/* 166 */       blockpos = new BlockPos(this.target.posX, this.target.posY + d0, this.target.posZ);
/*     */     } 
/*     */     
/* 169 */     double d1 = blockpos.getX() + 0.5D;
/* 170 */     double d2 = blockpos.getY() + d0;
/* 171 */     double d3 = blockpos.getZ() + 0.5D;
/* 172 */     EnumFacing enumfacing = null;
/*     */     
/* 174 */     if (blockpos.distanceSqToCenter(this.posX, this.posY, this.posZ) >= 4.0D) {
/*     */       
/* 176 */       BlockPos blockpos1 = new BlockPos(this);
/* 177 */       List<EnumFacing> list = Lists.newArrayList();
/*     */       
/* 179 */       if (p_184569_1_ != EnumFacing.Axis.X)
/*     */       {
/* 181 */         if (blockpos1.getX() < blockpos.getX() && this.world.isAirBlock(blockpos1.east())) {
/*     */           
/* 183 */           list.add(EnumFacing.EAST);
/*     */         }
/* 185 */         else if (blockpos1.getX() > blockpos.getX() && this.world.isAirBlock(blockpos1.west())) {
/*     */           
/* 187 */           list.add(EnumFacing.WEST);
/*     */         } 
/*     */       }
/*     */       
/* 191 */       if (p_184569_1_ != EnumFacing.Axis.Y)
/*     */       {
/* 193 */         if (blockpos1.getY() < blockpos.getY() && this.world.isAirBlock(blockpos1.up())) {
/*     */           
/* 195 */           list.add(EnumFacing.UP);
/*     */         }
/* 197 */         else if (blockpos1.getY() > blockpos.getY() && this.world.isAirBlock(blockpos1.down())) {
/*     */           
/* 199 */           list.add(EnumFacing.DOWN);
/*     */         } 
/*     */       }
/*     */       
/* 203 */       if (p_184569_1_ != EnumFacing.Axis.Z)
/*     */       {
/* 205 */         if (blockpos1.getZ() < blockpos.getZ() && this.world.isAirBlock(blockpos1.south())) {
/*     */           
/* 207 */           list.add(EnumFacing.SOUTH);
/*     */         }
/* 209 */         else if (blockpos1.getZ() > blockpos.getZ() && this.world.isAirBlock(blockpos1.north())) {
/*     */           
/* 211 */           list.add(EnumFacing.NORTH);
/*     */         } 
/*     */       }
/*     */       
/* 215 */       enumfacing = EnumFacing.random(this.rand);
/*     */       
/* 217 */       if (list.isEmpty()) {
/*     */         
/* 219 */         for (int i = 5; !this.world.isAirBlock(blockpos1.offset(enumfacing)) && i > 0; i--)
/*     */         {
/* 221 */           enumfacing = EnumFacing.random(this.rand);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 226 */         enumfacing = list.get(this.rand.nextInt(list.size()));
/*     */       } 
/*     */       
/* 229 */       d1 = this.posX + enumfacing.getFrontOffsetX();
/* 230 */       d2 = this.posY + enumfacing.getFrontOffsetY();
/* 231 */       d3 = this.posZ + enumfacing.getFrontOffsetZ();
/*     */     } 
/*     */     
/* 234 */     setDirection(enumfacing);
/* 235 */     double d6 = d1 - this.posX;
/* 236 */     double d7 = d2 - this.posY;
/* 237 */     double d4 = d3 - this.posZ;
/* 238 */     double d5 = MathHelper.sqrt(d6 * d6 + d7 * d7 + d4 * d4);
/*     */     
/* 240 */     if (d5 == 0.0D) {
/*     */       
/* 242 */       this.targetDeltaX = 0.0D;
/* 243 */       this.targetDeltaY = 0.0D;
/* 244 */       this.targetDeltaZ = 0.0D;
/*     */     }
/*     */     else {
/*     */       
/* 248 */       this.targetDeltaX = d6 / d5 * 0.15D;
/* 249 */       this.targetDeltaY = d7 / d5 * 0.15D;
/* 250 */       this.targetDeltaZ = d4 / d5 * 0.15D;
/*     */     } 
/*     */     
/* 253 */     this.isAirBorne = true;
/* 254 */     this.steps = 10 + this.rand.nextInt(5) * 10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 262 */     if (!this.world.isRemote && this.world.getDifficulty() == EnumDifficulty.PEACEFUL) {
/*     */       
/* 264 */       setDead();
/*     */     }
/*     */     else {
/*     */       
/* 268 */       super.onUpdate();
/*     */       
/* 270 */       if (!this.world.isRemote) {
/*     */         
/* 272 */         if (this.target == null && this.targetUniqueId != null) {
/*     */           
/* 274 */           for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.targetBlockPos.add(-2, -2, -2), this.targetBlockPos.add(2, 2, 2)))) {
/*     */             
/* 276 */             if (entitylivingbase.getUniqueID().equals(this.targetUniqueId)) {
/*     */               
/* 278 */               this.target = (Entity)entitylivingbase;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 283 */           this.targetUniqueId = null;
/*     */         } 
/*     */         
/* 286 */         if (this.owner == null && this.ownerUniqueId != null) {
/*     */           
/* 288 */           for (EntityLivingBase entitylivingbase1 : this.world.getEntitiesWithinAABB(EntityLivingBase.class, new AxisAlignedBB(this.ownerBlockPos.add(-2, -2, -2), this.ownerBlockPos.add(2, 2, 2)))) {
/*     */             
/* 290 */             if (entitylivingbase1.getUniqueID().equals(this.ownerUniqueId)) {
/*     */               
/* 292 */               this.owner = entitylivingbase1;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 297 */           this.ownerUniqueId = null;
/*     */         } 
/*     */         
/* 300 */         if (this.target == null || !this.target.isEntityAlive() || (this.target instanceof EntityPlayer && ((EntityPlayer)this.target).isSpectator())) {
/*     */           
/* 302 */           if (!hasNoGravity())
/*     */           {
/* 304 */             this.motionY -= 0.04D;
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 309 */           this.targetDeltaX = MathHelper.clamp(this.targetDeltaX * 1.025D, -1.0D, 1.0D);
/* 310 */           this.targetDeltaY = MathHelper.clamp(this.targetDeltaY * 1.025D, -1.0D, 1.0D);
/* 311 */           this.targetDeltaZ = MathHelper.clamp(this.targetDeltaZ * 1.025D, -1.0D, 1.0D);
/* 312 */           this.motionX += (this.targetDeltaX - this.motionX) * 0.2D;
/* 313 */           this.motionY += (this.targetDeltaY - this.motionY) * 0.2D;
/* 314 */           this.motionZ += (this.targetDeltaZ - this.motionZ) * 0.2D;
/*     */         } 
/*     */         
/* 317 */         RayTraceResult raytraceresult = ProjectileHelper.forwardsRaycast(this, true, false, (Entity)this.owner);
/*     */         
/* 319 */         if (raytraceresult != null)
/*     */         {
/* 321 */           bulletHit(raytraceresult);
/*     */         }
/*     */       } 
/*     */       
/* 325 */       setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/* 326 */       ProjectileHelper.rotateTowardsMovement(this, 0.5F);
/*     */       
/* 328 */       if (this.world.isRemote) {
/*     */         
/* 330 */         this.world.spawnParticle(EnumParticleTypes.END_ROD, this.posX - this.motionX, this.posY - this.motionY + 0.15D, this.posZ - this.motionZ, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/* 332 */       else if (this.target != null && !this.target.isDead) {
/*     */         
/* 334 */         if (this.steps > 0) {
/*     */           
/* 336 */           this.steps--;
/*     */           
/* 338 */           if (this.steps == 0)
/*     */           {
/* 340 */             selectNextMoveDirection((this.direction == null) ? null : this.direction.getAxis());
/*     */           }
/*     */         } 
/*     */         
/* 344 */         if (this.direction != null) {
/*     */           
/* 346 */           BlockPos blockpos = new BlockPos(this);
/* 347 */           EnumFacing.Axis enumfacing$axis = this.direction.getAxis();
/*     */           
/* 349 */           if (this.world.isBlockNormalCube(blockpos.offset(this.direction), false)) {
/*     */             
/* 351 */             selectNextMoveDirection(enumfacing$axis);
/*     */           }
/*     */           else {
/*     */             
/* 355 */             BlockPos blockpos1 = new BlockPos(this.target);
/*     */             
/* 357 */             if ((enumfacing$axis == EnumFacing.Axis.X && blockpos.getX() == blockpos1.getX()) || (enumfacing$axis == EnumFacing.Axis.Z && blockpos.getZ() == blockpos1.getZ()) || (enumfacing$axis == EnumFacing.Axis.Y && blockpos.getY() == blockpos1.getY()))
/*     */             {
/* 359 */               selectNextMoveDirection(enumfacing$axis);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/* 372 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/* 380 */     return (distance < 16384.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness() {
/* 388 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender() {
/* 393 */     return 15728880;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void bulletHit(RayTraceResult result) {
/* 398 */     if (result.entityHit == null) {
/*     */       
/* 400 */       ((WorldServer)this.world).spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, this.posX, this.posY, this.posZ, 2, 0.2D, 0.2D, 0.2D, 0.0D, new int[0]);
/* 401 */       playSound(SoundEvents.ENTITY_SHULKER_BULLET_HIT, 1.0F, 1.0F);
/*     */     }
/*     */     else {
/*     */       
/* 405 */       boolean flag = result.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, this.owner).setProjectile(), 4.0F);
/*     */       
/* 407 */       if (flag) {
/*     */         
/* 409 */         applyEnchantments(this.owner, result.entityHit);
/*     */         
/* 411 */         if (result.entityHit instanceof EntityLivingBase)
/*     */         {
/* 413 */           ((EntityLivingBase)result.entityHit).addPotionEffect(new PotionEffect(MobEffects.LEVITATION, 200));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 418 */     setDead();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeCollidedWith() {
/* 426 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 434 */     if (!this.world.isRemote) {
/*     */       
/* 436 */       playSound(SoundEvents.ENTITY_SHULKER_BULLET_HURT, 1.0F, 1.0F);
/* 437 */       ((WorldServer)this.world).spawnParticle(EnumParticleTypes.CRIT, this.posX, this.posY, this.posZ, 15, 0.2D, 0.2D, 0.2D, 0.0D, new int[0]);
/* 438 */       setDead();
/*     */     } 
/*     */     
/* 441 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntityShulkerBullet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */