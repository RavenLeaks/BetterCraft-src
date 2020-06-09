/*     */ package net.minecraft.entity.projectile;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IProjectile;
/*     */ import net.minecraft.entity.passive.EntityLlama;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityLlamaSpit extends Entity implements IProjectile {
/*     */   public EntityLlama field_190539_a;
/*     */   private NBTTagCompound field_190540_b;
/*     */   
/*     */   public EntityLlamaSpit(World p_i47272_1_) {
/*  26 */     super(p_i47272_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLlamaSpit(World p_i47273_1_, EntityLlama p_i47273_2_) {
/*  31 */     super(p_i47273_1_);
/*  32 */     this.field_190539_a = p_i47273_2_;
/*  33 */     setPosition(p_i47273_2_.posX - (p_i47273_2_.width + 1.0F) * 0.5D * MathHelper.sin(p_i47273_2_.renderYawOffset * 0.017453292F), p_i47273_2_.posY + p_i47273_2_.getEyeHeight() - 0.10000000149011612D, p_i47273_2_.posZ + (p_i47273_2_.width + 1.0F) * 0.5D * MathHelper.cos(p_i47273_2_.renderYawOffset * 0.017453292F));
/*  34 */     setSize(0.25F, 0.25F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLlamaSpit(World p_i47274_1_, double p_i47274_2_, double p_i47274_4_, double p_i47274_6_, double p_i47274_8_, double p_i47274_10_, double p_i47274_12_) {
/*  39 */     super(p_i47274_1_);
/*  40 */     setPosition(p_i47274_2_, p_i47274_4_, p_i47274_6_);
/*     */     
/*  42 */     for (int i = 0; i < 7; i++) {
/*     */       
/*  44 */       double d0 = 0.4D + 0.1D * i;
/*  45 */       p_i47274_1_.spawnParticle(EnumParticleTypes.SPIT, p_i47274_2_, p_i47274_4_, p_i47274_6_, p_i47274_8_ * d0, p_i47274_10_, p_i47274_12_ * d0, new int[0]);
/*     */     } 
/*     */     
/*  48 */     this.motionX = p_i47274_8_;
/*  49 */     this.motionY = p_i47274_10_;
/*  50 */     this.motionZ = p_i47274_12_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/*  58 */     super.onUpdate();
/*     */     
/*  60 */     if (this.field_190540_b != null)
/*     */     {
/*  62 */       func_190537_j();
/*     */     }
/*     */     
/*  65 */     Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
/*  66 */     Vec3d vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*  67 */     RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, vec3d1);
/*  68 */     vec3d = new Vec3d(this.posX, this.posY, this.posZ);
/*  69 */     vec3d1 = new Vec3d(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
/*     */     
/*  71 */     if (raytraceresult != null)
/*     */     {
/*  73 */       vec3d1 = new Vec3d(raytraceresult.hitVec.xCoord, raytraceresult.hitVec.yCoord, raytraceresult.hitVec.zCoord);
/*     */     }
/*     */     
/*  76 */     Entity entity = func_190538_a(vec3d, vec3d1);
/*     */     
/*  78 */     if (entity != null)
/*     */     {
/*  80 */       raytraceresult = new RayTraceResult(entity);
/*     */     }
/*     */     
/*  83 */     if (raytraceresult != null)
/*     */     {
/*  85 */       func_190536_a(raytraceresult);
/*     */     }
/*     */     
/*  88 */     this.posX += this.motionX;
/*  89 */     this.posY += this.motionY;
/*  90 */     this.posZ += this.motionZ;
/*  91 */     float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/*  92 */     this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 57.29577951308232D);
/*     */     
/*  94 */     for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * 57.29577951308232D); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  99 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*     */     {
/* 101 */       this.prevRotationPitch += 360.0F;
/*     */     }
/*     */     
/* 104 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*     */     {
/* 106 */       this.prevRotationYaw -= 360.0F;
/*     */     }
/*     */     
/* 109 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*     */     {
/* 111 */       this.prevRotationYaw += 360.0F;
/*     */     }
/*     */     
/* 114 */     this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 115 */     this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/* 116 */     float f1 = 0.99F;
/* 117 */     float f2 = 0.06F;
/*     */     
/* 119 */     if (!this.world.isMaterialInBB(getEntityBoundingBox(), Material.AIR)) {
/*     */       
/* 121 */       setDead();
/*     */     }
/* 123 */     else if (isInWater()) {
/*     */       
/* 125 */       setDead();
/*     */     }
/*     */     else {
/*     */       
/* 129 */       this.motionX *= 0.9900000095367432D;
/* 130 */       this.motionY *= 0.9900000095367432D;
/* 131 */       this.motionZ *= 0.9900000095367432D;
/*     */       
/* 133 */       if (!hasNoGravity())
/*     */       {
/* 135 */         this.motionY -= 0.05999999865889549D;
/*     */       }
/*     */       
/* 138 */       setPosition(this.posX, this.posY, this.posZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/* 147 */     this.motionX = x;
/* 148 */     this.motionY = y;
/* 149 */     this.motionZ = z;
/*     */     
/* 151 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/*     */       
/* 153 */       float f = MathHelper.sqrt(x * x + z * z);
/* 154 */       this.rotationPitch = (float)(MathHelper.atan2(y, f) * 57.29577951308232D);
/* 155 */       this.rotationYaw = (float)(MathHelper.atan2(x, z) * 57.29577951308232D);
/* 156 */       this.prevRotationPitch = this.rotationPitch;
/* 157 */       this.prevRotationYaw = this.rotationYaw;
/* 158 */       setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Entity func_190538_a(Vec3d p_190538_1_, Vec3d p_190538_2_) {
/* 165 */     Entity entity = null;
/* 166 */     List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().addCoord(this.motionX, this.motionY, this.motionZ).expandXyz(1.0D));
/* 167 */     double d0 = 0.0D;
/*     */     
/* 169 */     for (Entity entity1 : list) {
/*     */       
/* 171 */       if (entity1 != this.field_190539_a) {
/*     */         
/* 173 */         AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox().expandXyz(0.30000001192092896D);
/* 174 */         RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(p_190538_1_, p_190538_2_);
/*     */         
/* 176 */         if (raytraceresult != null) {
/*     */           
/* 178 */           double d1 = p_190538_1_.squareDistanceTo(raytraceresult.hitVec);
/*     */           
/* 180 */           if (d1 < d0 || d0 == 0.0D) {
/*     */             
/* 182 */             entity = entity1;
/* 183 */             d0 = d1;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 189 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThrowableHeading(double x, double y, double z, float velocity, float inaccuracy) {
/* 197 */     float f = MathHelper.sqrt(x * x + y * y + z * z);
/* 198 */     x /= f;
/* 199 */     y /= f;
/* 200 */     z /= f;
/* 201 */     x += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 202 */     y += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 203 */     z += this.rand.nextGaussian() * 0.007499999832361937D * inaccuracy;
/* 204 */     x *= velocity;
/* 205 */     y *= velocity;
/* 206 */     z *= velocity;
/* 207 */     this.motionX = x;
/* 208 */     this.motionY = y;
/* 209 */     this.motionZ = z;
/* 210 */     float f1 = MathHelper.sqrt(x * x + z * z);
/* 211 */     this.rotationYaw = (float)(MathHelper.atan2(x, z) * 57.29577951308232D);
/* 212 */     this.rotationPitch = (float)(MathHelper.atan2(y, f1) * 57.29577951308232D);
/* 213 */     this.prevRotationYaw = this.rotationYaw;
/* 214 */     this.prevRotationPitch = this.rotationPitch;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_190536_a(RayTraceResult p_190536_1_) {
/* 219 */     if (p_190536_1_.entityHit != null && this.field_190539_a != null)
/*     */     {
/* 221 */       p_190536_1_.entityHit.attackEntityFrom(DamageSource.causeIndirectDamage(this, (EntityLivingBase)this.field_190539_a).setProjectile(), 1.0F);
/*     */     }
/*     */     
/* 224 */     if (!this.world.isRemote)
/*     */     {
/* 226 */       setDead();
/*     */     }
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
/*     */   protected void readEntityFromNBT(NBTTagCompound compound) {
/* 239 */     if (compound.hasKey("Owner", 10))
/*     */     {
/* 241 */       this.field_190540_b = compound.getCompoundTag("Owner");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound compound) {
/* 250 */     if (this.field_190539_a != null) {
/*     */       
/* 252 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 253 */       UUID uuid = this.field_190539_a.getUniqueID();
/* 254 */       nbttagcompound.setUniqueId("OwnerUUID", uuid);
/* 255 */       compound.setTag("Owner", (NBTBase)nbttagcompound);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190537_j() {
/* 261 */     if (this.field_190540_b != null && this.field_190540_b.hasUniqueId("OwnerUUID")) {
/*     */       
/* 263 */       UUID uuid = this.field_190540_b.getUniqueId("OwnerUUID");
/*     */       
/* 265 */       for (EntityLlama entityllama : this.world.getEntitiesWithinAABB(EntityLlama.class, getEntityBoundingBox().expandXyz(15.0D))) {
/*     */         
/* 267 */         if (entityllama.getUniqueID().equals(uuid)) {
/*     */           
/* 269 */           this.field_190539_a = entityllama;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 275 */     this.field_190540_b = null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\projectile\EntityLlamaSpit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */