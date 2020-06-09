/*     */ package net.minecraft.entity.item;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.MoverType;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.walkers.ItemStackData;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class EntityFireworkRocket extends Entity {
/*  27 */   private static final DataParameter<ItemStack> FIREWORK_ITEM = EntityDataManager.createKey(EntityFireworkRocket.class, DataSerializers.OPTIONAL_ITEM_STACK);
/*  28 */   private static final DataParameter<Integer> field_191512_b = EntityDataManager.createKey(EntityFireworkRocket.class, DataSerializers.VARINT);
/*     */ 
/*     */   
/*     */   private int fireworkAge;
/*     */ 
/*     */   
/*     */   private int lifetime;
/*     */ 
/*     */   
/*     */   private EntityLivingBase field_191513_e;
/*     */ 
/*     */   
/*     */   public EntityFireworkRocket(World worldIn) {
/*  41 */     super(worldIn);
/*  42 */     setSize(0.25F, 0.25F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  47 */     this.dataManager.register(FIREWORK_ITEM, ItemStack.field_190927_a);
/*  48 */     this.dataManager.register(field_191512_b, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRenderDist(double distance) {
/*  56 */     return (distance < 4096.0D && !func_191511_j());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInRangeToRender3d(double x, double y, double z) {
/*  61 */     return (super.isInRangeToRender3d(x, y, z) && !func_191511_j());
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityFireworkRocket(World worldIn, double x, double y, double z, ItemStack givenItem) {
/*  66 */     super(worldIn);
/*  67 */     this.fireworkAge = 0;
/*  68 */     setSize(0.25F, 0.25F);
/*  69 */     setPosition(x, y, z);
/*  70 */     int i = 1;
/*     */     
/*  72 */     if (!givenItem.func_190926_b() && givenItem.hasTagCompound()) {
/*     */       
/*  74 */       this.dataManager.set(FIREWORK_ITEM, givenItem.copy());
/*  75 */       NBTTagCompound nbttagcompound = givenItem.getTagCompound();
/*  76 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Fireworks");
/*  77 */       i += nbttagcompound1.getByte("Flight");
/*     */     } 
/*     */     
/*  80 */     this.motionX = this.rand.nextGaussian() * 0.001D;
/*  81 */     this.motionZ = this.rand.nextGaussian() * 0.001D;
/*  82 */     this.motionY = 0.05D;
/*  83 */     this.lifetime = 10 * i + this.rand.nextInt(6) + this.rand.nextInt(7);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityFireworkRocket(World p_i47367_1_, ItemStack p_i47367_2_, EntityLivingBase p_i47367_3_) {
/*  88 */     this(p_i47367_1_, p_i47367_3_.posX, p_i47367_3_.posY, p_i47367_3_.posZ, p_i47367_2_);
/*  89 */     this.dataManager.set(field_191512_b, Integer.valueOf(p_i47367_3_.getEntityId()));
/*  90 */     this.field_191513_e = p_i47367_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVelocity(double x, double y, double z) {
/*  98 */     this.motionX = x;
/*  99 */     this.motionY = y;
/* 100 */     this.motionZ = z;
/*     */     
/* 102 */     if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F) {
/*     */       
/* 104 */       float f = MathHelper.sqrt(x * x + z * z);
/* 105 */       this.rotationYaw = (float)(MathHelper.atan2(x, z) * 57.29577951308232D);
/* 106 */       this.rotationPitch = (float)(MathHelper.atan2(y, f) * 57.29577951308232D);
/* 107 */       this.prevRotationYaw = this.rotationYaw;
/* 108 */       this.prevRotationPitch = this.rotationPitch;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 117 */     this.lastTickPosX = this.posX;
/* 118 */     this.lastTickPosY = this.posY;
/* 119 */     this.lastTickPosZ = this.posZ;
/* 120 */     super.onUpdate();
/*     */     
/* 122 */     if (func_191511_j()) {
/*     */       
/* 124 */       if (this.field_191513_e == null) {
/*     */         
/* 126 */         Entity entity = this.world.getEntityByID(((Integer)this.dataManager.get(field_191512_b)).intValue());
/*     */         
/* 128 */         if (entity instanceof EntityLivingBase)
/*     */         {
/* 130 */           this.field_191513_e = (EntityLivingBase)entity;
/*     */         }
/*     */       } 
/*     */       
/* 134 */       if (this.field_191513_e != null)
/*     */       {
/* 136 */         if (this.field_191513_e.isElytraFlying()) {
/*     */           
/* 138 */           Vec3d vec3d = this.field_191513_e.getLookVec();
/* 139 */           double d0 = 1.5D;
/* 140 */           double d1 = 0.1D;
/* 141 */           this.field_191513_e.motionX += vec3d.xCoord * 0.1D + (vec3d.xCoord * 1.5D - this.field_191513_e.motionX) * 0.5D;
/* 142 */           this.field_191513_e.motionY += vec3d.yCoord * 0.1D + (vec3d.yCoord * 1.5D - this.field_191513_e.motionY) * 0.5D;
/* 143 */           this.field_191513_e.motionZ += vec3d.zCoord * 0.1D + (vec3d.zCoord * 1.5D - this.field_191513_e.motionZ) * 0.5D;
/*     */         } 
/*     */         
/* 146 */         setPosition(this.field_191513_e.posX, this.field_191513_e.posY, this.field_191513_e.posZ);
/* 147 */         this.motionX = this.field_191513_e.motionX;
/* 148 */         this.motionY = this.field_191513_e.motionY;
/* 149 */         this.motionZ = this.field_191513_e.motionZ;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 154 */       this.motionX *= 1.15D;
/* 155 */       this.motionZ *= 1.15D;
/* 156 */       this.motionY += 0.04D;
/* 157 */       moveEntity(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
/*     */     } 
/*     */     
/* 160 */     float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);
/* 161 */     this.rotationYaw = (float)(MathHelper.atan2(this.motionX, this.motionZ) * 57.29577951308232D);
/*     */     
/* 163 */     for (this.rotationPitch = (float)(MathHelper.atan2(this.motionY, f) * 57.29577951308232D); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 168 */     while (this.rotationPitch - this.prevRotationPitch >= 180.0F)
/*     */     {
/* 170 */       this.prevRotationPitch += 360.0F;
/*     */     }
/*     */     
/* 173 */     while (this.rotationYaw - this.prevRotationYaw < -180.0F)
/*     */     {
/* 175 */       this.prevRotationYaw -= 360.0F;
/*     */     }
/*     */     
/* 178 */     while (this.rotationYaw - this.prevRotationYaw >= 180.0F)
/*     */     {
/* 180 */       this.prevRotationYaw += 360.0F;
/*     */     }
/*     */     
/* 183 */     this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
/* 184 */     this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
/*     */     
/* 186 */     if (this.fireworkAge == 0 && !isSilent())
/*     */     {
/* 188 */       this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_FIREWORK_LAUNCH, SoundCategory.AMBIENT, 3.0F, 1.0F);
/*     */     }
/*     */     
/* 191 */     this.fireworkAge++;
/*     */     
/* 193 */     if (this.world.isRemote && this.fireworkAge % 2 < 2)
/*     */     {
/* 195 */       this.world.spawnParticle(EnumParticleTypes.FIREWORKS_SPARK, this.posX, this.posY - 0.3D, this.posZ, this.rand.nextGaussian() * 0.05D, -this.motionY * 0.5D, this.rand.nextGaussian() * 0.05D, new int[0]);
/*     */     }
/*     */     
/* 198 */     if (!this.world.isRemote && this.fireworkAge > this.lifetime) {
/*     */       
/* 200 */       this.world.setEntityState(this, (byte)17);
/* 201 */       func_191510_k();
/* 202 */       setDead();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_191510_k() {
/* 208 */     float f = 0.0F;
/* 209 */     ItemStack itemstack = (ItemStack)this.dataManager.get(FIREWORK_ITEM);
/* 210 */     NBTTagCompound nbttagcompound = itemstack.func_190926_b() ? null : itemstack.getSubCompound("Fireworks");
/* 211 */     NBTTagList nbttaglist = (nbttagcompound != null) ? nbttagcompound.getTagList("Explosions", 10) : null;
/*     */     
/* 213 */     if (nbttaglist != null && !nbttaglist.hasNoTags())
/*     */     {
/* 215 */       f = (5 + nbttaglist.tagCount() * 2);
/*     */     }
/*     */     
/* 218 */     if (f > 0.0F) {
/*     */       
/* 220 */       if (this.field_191513_e != null)
/*     */       {
/* 222 */         this.field_191513_e.attackEntityFrom(DamageSource.field_191552_t, (5 + nbttaglist.tagCount() * 2));
/*     */       }
/*     */       
/* 225 */       double d0 = 5.0D;
/* 226 */       Vec3d vec3d = new Vec3d(this.posX, this.posY, this.posZ);
/*     */       
/* 228 */       for (EntityLivingBase entitylivingbase : this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox().expandXyz(5.0D))) {
/*     */         
/* 230 */         if (entitylivingbase != this.field_191513_e && getDistanceSqToEntity((Entity)entitylivingbase) <= 25.0D) {
/*     */           
/* 232 */           boolean flag = false;
/*     */           
/* 234 */           for (int i = 0; i < 2; i++) {
/*     */             
/* 236 */             RayTraceResult raytraceresult = this.world.rayTraceBlocks(vec3d, new Vec3d(entitylivingbase.posX, entitylivingbase.posY + entitylivingbase.height * 0.5D * i, entitylivingbase.posZ), false, true, false);
/*     */             
/* 238 */             if (raytraceresult == null || raytraceresult.typeOfHit == RayTraceResult.Type.MISS) {
/*     */               
/* 240 */               flag = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 245 */           if (flag) {
/*     */             
/* 247 */             float f1 = f * (float)Math.sqrt((5.0D - getDistanceToEntity((Entity)entitylivingbase)) / 5.0D);
/* 248 */             entitylivingbase.attackEntityFrom(DamageSource.field_191552_t, f1);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191511_j() {
/* 257 */     return (((Integer)this.dataManager.get(field_191512_b)).intValue() > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 262 */     if (id == 17 && this.world.isRemote) {
/*     */       
/* 264 */       ItemStack itemstack = (ItemStack)this.dataManager.get(FIREWORK_ITEM);
/* 265 */       NBTTagCompound nbttagcompound = itemstack.func_190926_b() ? null : itemstack.getSubCompound("Fireworks");
/* 266 */       this.world.makeFireworks(this.posX, this.posY, this.posZ, this.motionX, this.motionY, this.motionZ, nbttagcompound);
/*     */     } 
/*     */     
/* 269 */     super.handleStatusUpdate(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesFireworkRocket(DataFixer fixer) {
/* 274 */     fixer.registerWalker(FixTypes.ENTITY, (IDataWalker)new ItemStackData(EntityFireworkRocket.class, new String[] { "FireworksItem" }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 282 */     compound.setInteger("Life", this.fireworkAge);
/* 283 */     compound.setInteger("LifeTime", this.lifetime);
/* 284 */     ItemStack itemstack = (ItemStack)this.dataManager.get(FIREWORK_ITEM);
/*     */     
/* 286 */     if (!itemstack.func_190926_b())
/*     */     {
/* 288 */       compound.setTag("FireworksItem", (NBTBase)itemstack.writeToNBT(new NBTTagCompound()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 297 */     this.fireworkAge = compound.getInteger("Life");
/* 298 */     this.lifetime = compound.getInteger("LifeTime");
/* 299 */     NBTTagCompound nbttagcompound = compound.getCompoundTag("FireworksItem");
/*     */     
/* 301 */     if (nbttagcompound != null) {
/*     */       
/* 303 */       ItemStack itemstack = new ItemStack(nbttagcompound);
/*     */       
/* 305 */       if (!itemstack.func_190926_b())
/*     */       {
/* 307 */         this.dataManager.set(FIREWORK_ITEM, itemstack);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBeAttackedWithItem() {
/* 317 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\item\EntityFireworkRocket.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */