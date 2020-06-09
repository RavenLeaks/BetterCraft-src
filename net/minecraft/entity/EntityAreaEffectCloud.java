/*     */ package net.minecraft.entity;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.init.PotionTypes;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.potion.PotionType;
/*     */ import net.minecraft.potion.PotionUtils;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ 
/*     */ public class EntityAreaEffectCloud
/*     */   extends Entity {
/*  29 */   private static final DataParameter<Float> RADIUS = EntityDataManager.createKey(EntityAreaEffectCloud.class, DataSerializers.FLOAT);
/*  30 */   private static final DataParameter<Integer> COLOR = EntityDataManager.createKey(EntityAreaEffectCloud.class, DataSerializers.VARINT);
/*  31 */   private static final DataParameter<Boolean> IGNORE_RADIUS = EntityDataManager.createKey(EntityAreaEffectCloud.class, DataSerializers.BOOLEAN);
/*  32 */   private static final DataParameter<Integer> PARTICLE = EntityDataManager.createKey(EntityAreaEffectCloud.class, DataSerializers.VARINT);
/*  33 */   private static final DataParameter<Integer> PARTICLE_PARAM_1 = EntityDataManager.createKey(EntityAreaEffectCloud.class, DataSerializers.VARINT);
/*  34 */   private static final DataParameter<Integer> PARTICLE_PARAM_2 = EntityDataManager.createKey(EntityAreaEffectCloud.class, DataSerializers.VARINT);
/*     */   
/*     */   private PotionType potion;
/*     */   private final List<PotionEffect> effects;
/*     */   private final Map<Entity, Integer> reapplicationDelayMap;
/*     */   private int duration;
/*     */   private int waitTime;
/*     */   private int reapplicationDelay;
/*     */   private boolean colorSet;
/*     */   private int durationOnUse;
/*     */   private float radiusOnUse;
/*     */   private float radiusPerTick;
/*     */   private EntityLivingBase owner;
/*     */   private UUID ownerUniqueId;
/*     */   
/*     */   public EntityAreaEffectCloud(World worldIn) {
/*  50 */     super(worldIn);
/*  51 */     this.potion = PotionTypes.EMPTY;
/*  52 */     this.effects = Lists.newArrayList();
/*  53 */     this.reapplicationDelayMap = Maps.newHashMap();
/*  54 */     this.duration = 600;
/*  55 */     this.waitTime = 20;
/*  56 */     this.reapplicationDelay = 20;
/*  57 */     this.noClip = true;
/*  58 */     this.isImmuneToFire = true;
/*  59 */     setRadius(3.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityAreaEffectCloud(World worldIn, double x, double y, double z) {
/*  64 */     this(worldIn);
/*  65 */     setPosition(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  70 */     getDataManager().register(COLOR, Integer.valueOf(0));
/*  71 */     getDataManager().register(RADIUS, Float.valueOf(0.5F));
/*  72 */     getDataManager().register(IGNORE_RADIUS, Boolean.valueOf(false));
/*  73 */     getDataManager().register(PARTICLE, Integer.valueOf(EnumParticleTypes.SPELL_MOB.getParticleID()));
/*  74 */     getDataManager().register(PARTICLE_PARAM_1, Integer.valueOf(0));
/*  75 */     getDataManager().register(PARTICLE_PARAM_2, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRadius(float radiusIn) {
/*  80 */     double d0 = this.posX;
/*  81 */     double d1 = this.posY;
/*  82 */     double d2 = this.posZ;
/*  83 */     setSize(radiusIn * 2.0F, 0.5F);
/*  84 */     setPosition(d0, d1, d2);
/*     */     
/*  86 */     if (!this.world.isRemote)
/*     */     {
/*  88 */       getDataManager().set(RADIUS, Float.valueOf(radiusIn));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public float getRadius() {
/*  94 */     return ((Float)getDataManager().get(RADIUS)).floatValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPotion(PotionType potionIn) {
/*  99 */     this.potion = potionIn;
/*     */     
/* 101 */     if (!this.colorSet)
/*     */     {
/* 103 */       func_190618_C();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190618_C() {
/* 109 */     if (this.potion == PotionTypes.EMPTY && this.effects.isEmpty()) {
/*     */       
/* 111 */       getDataManager().set(COLOR, Integer.valueOf(0));
/*     */     }
/*     */     else {
/*     */       
/* 115 */       getDataManager().set(COLOR, Integer.valueOf(PotionUtils.getPotionColorFromEffectList(PotionUtils.mergeEffects(this.potion, this.effects))));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addEffect(PotionEffect effect) {
/* 121 */     this.effects.add(effect);
/*     */     
/* 123 */     if (!this.colorSet)
/*     */     {
/* 125 */       func_190618_C();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor() {
/* 131 */     return ((Integer)getDataManager().get(COLOR)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setColor(int colorIn) {
/* 136 */     this.colorSet = true;
/* 137 */     getDataManager().set(COLOR, Integer.valueOf(colorIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumParticleTypes getParticle() {
/* 142 */     return EnumParticleTypes.getParticleFromId(((Integer)getDataManager().get(PARTICLE)).intValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParticle(EnumParticleTypes particleIn) {
/* 147 */     getDataManager().set(PARTICLE, Integer.valueOf(particleIn.getParticleID()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getParticleParam1() {
/* 152 */     return ((Integer)getDataManager().get(PARTICLE_PARAM_1)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParticleParam1(int particleParam) {
/* 157 */     getDataManager().set(PARTICLE_PARAM_1, Integer.valueOf(particleParam));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getParticleParam2() {
/* 162 */     return ((Integer)getDataManager().get(PARTICLE_PARAM_2)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setParticleParam2(int particleParam) {
/* 167 */     getDataManager().set(PARTICLE_PARAM_2, Integer.valueOf(particleParam));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setIgnoreRadius(boolean ignoreRadius) {
/* 175 */     getDataManager().set(IGNORE_RADIUS, Boolean.valueOf(ignoreRadius));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldIgnoreRadius() {
/* 183 */     return ((Boolean)getDataManager().get(IGNORE_RADIUS)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDuration() {
/* 188 */     return this.duration;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDuration(int durationIn) {
/* 193 */     this.duration = durationIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 201 */     super.onUpdate();
/* 202 */     boolean flag = shouldIgnoreRadius();
/* 203 */     float f = getRadius();
/*     */     
/* 205 */     if (this.world.isRemote) {
/*     */       
/* 207 */       EnumParticleTypes enumparticletypes = getParticle();
/* 208 */       int[] aint = new int[enumparticletypes.getArgumentCount()];
/*     */       
/* 210 */       if (aint.length > 0)
/*     */       {
/* 212 */         aint[0] = getParticleParam1();
/*     */       }
/*     */       
/* 215 */       if (aint.length > 1)
/*     */       {
/* 217 */         aint[1] = getParticleParam2();
/*     */       }
/*     */       
/* 220 */       if (flag) {
/*     */         
/* 222 */         if (this.rand.nextBoolean())
/*     */         {
/* 224 */           for (int i = 0; i < 2; i++) {
/*     */             
/* 226 */             float f1 = this.rand.nextFloat() * 6.2831855F;
/* 227 */             float f2 = MathHelper.sqrt(this.rand.nextFloat()) * 0.2F;
/* 228 */             float f3 = MathHelper.cos(f1) * f2;
/* 229 */             float f4 = MathHelper.sin(f1) * f2;
/*     */             
/* 231 */             if (enumparticletypes == EnumParticleTypes.SPELL_MOB)
/*     */             {
/* 233 */               int j = this.rand.nextBoolean() ? 16777215 : getColor();
/* 234 */               int k = j >> 16 & 0xFF;
/* 235 */               int l = j >> 8 & 0xFF;
/* 236 */               int i1 = j & 0xFF;
/* 237 */               this.world.func_190523_a(EnumParticleTypes.SPELL_MOB.getParticleID(), this.posX + f3, this.posY, this.posZ + f4, (k / 255.0F), (l / 255.0F), (i1 / 255.0F), new int[0]);
/*     */             }
/*     */             else
/*     */             {
/* 241 */               this.world.func_190523_a(enumparticletypes.getParticleID(), this.posX + f3, this.posY, this.posZ + f4, 0.0D, 0.0D, 0.0D, aint);
/*     */             }
/*     */           
/*     */           } 
/*     */         }
/*     */       } else {
/*     */         
/* 248 */         float f5 = 3.1415927F * f * f;
/*     */         
/* 250 */         for (int k1 = 0; k1 < f5; k1++) {
/*     */           
/* 252 */           float f6 = this.rand.nextFloat() * 6.2831855F;
/* 253 */           float f7 = MathHelper.sqrt(this.rand.nextFloat()) * f;
/* 254 */           float f8 = MathHelper.cos(f6) * f7;
/* 255 */           float f9 = MathHelper.sin(f6) * f7;
/*     */           
/* 257 */           if (enumparticletypes == EnumParticleTypes.SPELL_MOB)
/*     */           {
/* 259 */             int l1 = getColor();
/* 260 */             int i2 = l1 >> 16 & 0xFF;
/* 261 */             int j2 = l1 >> 8 & 0xFF;
/* 262 */             int j1 = l1 & 0xFF;
/* 263 */             this.world.func_190523_a(EnumParticleTypes.SPELL_MOB.getParticleID(), this.posX + f8, this.posY, this.posZ + f9, (i2 / 255.0F), (j2 / 255.0F), (j1 / 255.0F), new int[0]);
/*     */           }
/*     */           else
/*     */           {
/* 267 */             this.world.func_190523_a(enumparticletypes.getParticleID(), this.posX + f8, this.posY, this.posZ + f9, (0.5D - this.rand.nextDouble()) * 0.15D, 0.009999999776482582D, (0.5D - this.rand.nextDouble()) * 0.15D, aint);
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 274 */       if (this.ticksExisted >= this.waitTime + this.duration) {
/*     */         
/* 276 */         setDead();
/*     */         
/*     */         return;
/*     */       } 
/* 280 */       boolean flag1 = (this.ticksExisted < this.waitTime);
/*     */       
/* 282 */       if (flag != flag1)
/*     */       {
/* 284 */         setIgnoreRadius(flag1);
/*     */       }
/*     */       
/* 287 */       if (flag1) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 292 */       if (this.radiusPerTick != 0.0F) {
/*     */         
/* 294 */         f += this.radiusPerTick;
/*     */         
/* 296 */         if (f < 0.5F) {
/*     */           
/* 298 */           setDead();
/*     */           
/*     */           return;
/*     */         } 
/* 302 */         setRadius(f);
/*     */       } 
/*     */       
/* 305 */       if (this.ticksExisted % 5 == 0) {
/*     */         
/* 307 */         Iterator<Map.Entry<Entity, Integer>> iterator = this.reapplicationDelayMap.entrySet().iterator();
/*     */         
/* 309 */         while (iterator.hasNext()) {
/*     */           
/* 311 */           Map.Entry<Entity, Integer> entry = iterator.next();
/*     */           
/* 313 */           if (this.ticksExisted >= ((Integer)entry.getValue()).intValue())
/*     */           {
/* 315 */             iterator.remove();
/*     */           }
/*     */         } 
/*     */         
/* 319 */         List<PotionEffect> lstPotions = Lists.newArrayList();
/*     */         
/* 321 */         for (PotionEffect potioneffect1 : this.potion.getEffects())
/*     */         {
/* 323 */           lstPotions.add(new PotionEffect(potioneffect1.getPotion(), potioneffect1.getDuration() / 4, potioneffect1.getAmplifier(), potioneffect1.getIsAmbient(), potioneffect1.doesShowParticles()));
/*     */         }
/*     */         
/* 326 */         lstPotions.addAll(this.effects);
/*     */         
/* 328 */         if (lstPotions.isEmpty()) {
/*     */           
/* 330 */           this.reapplicationDelayMap.clear();
/*     */         }
/*     */         else {
/*     */           
/* 334 */           List<EntityLivingBase> list = this.world.getEntitiesWithinAABB(EntityLivingBase.class, getEntityBoundingBox());
/*     */           
/* 336 */           if (!list.isEmpty())
/*     */           {
/* 338 */             for (EntityLivingBase entitylivingbase : list) {
/*     */               
/* 340 */               if (!this.reapplicationDelayMap.containsKey(entitylivingbase) && entitylivingbase.canBeHitWithPotion()) {
/*     */                 
/* 342 */                 double d0 = entitylivingbase.posX - this.posX;
/* 343 */                 double d1 = entitylivingbase.posZ - this.posZ;
/* 344 */                 double d2 = d0 * d0 + d1 * d1;
/*     */                 
/* 346 */                 if (d2 <= (f * f)) {
/*     */                   
/* 348 */                   this.reapplicationDelayMap.put(entitylivingbase, Integer.valueOf(this.ticksExisted + this.reapplicationDelay));
/*     */                   
/* 350 */                   for (PotionEffect potioneffect : lstPotions) {
/*     */                     
/* 352 */                     if (potioneffect.getPotion().isInstant()) {
/*     */                       
/* 354 */                       potioneffect.getPotion().affectEntity(this, getOwner(), entitylivingbase, potioneffect.getAmplifier(), 0.5D);
/*     */                       
/*     */                       continue;
/*     */                     } 
/* 358 */                     entitylivingbase.addPotionEffect(new PotionEffect(potioneffect));
/*     */                   } 
/*     */ 
/*     */                   
/* 362 */                   if (this.radiusOnUse != 0.0F) {
/*     */                     
/* 364 */                     f += this.radiusOnUse;
/*     */                     
/* 366 */                     if (f < 0.5F) {
/*     */                       
/* 368 */                       setDead();
/*     */                       
/*     */                       return;
/*     */                     } 
/* 372 */                     setRadius(f);
/*     */                   } 
/*     */                   
/* 375 */                   if (this.durationOnUse != 0) {
/*     */                     
/* 377 */                     this.duration += this.durationOnUse;
/*     */                     
/* 379 */                     if (this.duration <= 0) {
/*     */                       
/* 381 */                       setDead();
/*     */                       return;
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRadiusOnUse(float radiusOnUseIn) {
/* 396 */     this.radiusOnUse = radiusOnUseIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRadiusPerTick(float radiusPerTickIn) {
/* 401 */     this.radiusPerTick = radiusPerTickIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWaitTime(int waitTimeIn) {
/* 406 */     this.waitTime = waitTimeIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setOwner(@Nullable EntityLivingBase ownerIn) {
/* 411 */     this.owner = ownerIn;
/* 412 */     this.ownerUniqueId = (ownerIn == null) ? null : ownerIn.getUniqueID();
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public EntityLivingBase getOwner() {
/* 418 */     if (this.owner == null && this.ownerUniqueId != null && this.world instanceof WorldServer) {
/*     */       
/* 420 */       Entity entity = ((WorldServer)this.world).getEntityFromUuid(this.ownerUniqueId);
/*     */       
/* 422 */       if (entity instanceof EntityLivingBase)
/*     */       {
/* 424 */         this.owner = (EntityLivingBase)entity;
/*     */       }
/*     */     } 
/*     */     
/* 428 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void readEntityFromNBT(NBTTagCompound compound) {
/* 436 */     this.ticksExisted = compound.getInteger("Age");
/* 437 */     this.duration = compound.getInteger("Duration");
/* 438 */     this.waitTime = compound.getInteger("WaitTime");
/* 439 */     this.reapplicationDelay = compound.getInteger("ReapplicationDelay");
/* 440 */     this.durationOnUse = compound.getInteger("DurationOnUse");
/* 441 */     this.radiusOnUse = compound.getFloat("RadiusOnUse");
/* 442 */     this.radiusPerTick = compound.getFloat("RadiusPerTick");
/* 443 */     setRadius(compound.getFloat("Radius"));
/* 444 */     this.ownerUniqueId = compound.getUniqueId("OwnerUUID");
/*     */     
/* 446 */     if (compound.hasKey("Particle", 8)) {
/*     */       
/* 448 */       EnumParticleTypes enumparticletypes = EnumParticleTypes.getByName(compound.getString("Particle"));
/*     */       
/* 450 */       if (enumparticletypes != null) {
/*     */         
/* 452 */         setParticle(enumparticletypes);
/* 453 */         setParticleParam1(compound.getInteger("ParticleParam1"));
/* 454 */         setParticleParam2(compound.getInteger("ParticleParam2"));
/*     */       } 
/*     */     } 
/*     */     
/* 458 */     if (compound.hasKey("Color", 99))
/*     */     {
/* 460 */       setColor(compound.getInteger("Color"));
/*     */     }
/*     */     
/* 463 */     if (compound.hasKey("Potion", 8))
/*     */     {
/* 465 */       setPotion(PotionUtils.getPotionTypeFromNBT(compound));
/*     */     }
/*     */     
/* 468 */     if (compound.hasKey("Effects", 9)) {
/*     */       
/* 470 */       NBTTagList nbttaglist = compound.getTagList("Effects", 10);
/* 471 */       this.effects.clear();
/*     */       
/* 473 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/* 475 */         PotionEffect potioneffect = PotionEffect.readCustomPotionEffectFromNBT(nbttaglist.getCompoundTagAt(i));
/*     */         
/* 477 */         if (potioneffect != null)
/*     */         {
/* 479 */           addEffect(potioneffect);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void writeEntityToNBT(NBTTagCompound compound) {
/* 490 */     compound.setInteger("Age", this.ticksExisted);
/* 491 */     compound.setInteger("Duration", this.duration);
/* 492 */     compound.setInteger("WaitTime", this.waitTime);
/* 493 */     compound.setInteger("ReapplicationDelay", this.reapplicationDelay);
/* 494 */     compound.setInteger("DurationOnUse", this.durationOnUse);
/* 495 */     compound.setFloat("RadiusOnUse", this.radiusOnUse);
/* 496 */     compound.setFloat("RadiusPerTick", this.radiusPerTick);
/* 497 */     compound.setFloat("Radius", getRadius());
/* 498 */     compound.setString("Particle", getParticle().getParticleName());
/* 499 */     compound.setInteger("ParticleParam1", getParticleParam1());
/* 500 */     compound.setInteger("ParticleParam2", getParticleParam2());
/*     */     
/* 502 */     if (this.ownerUniqueId != null)
/*     */     {
/* 504 */       compound.setUniqueId("OwnerUUID", this.ownerUniqueId);
/*     */     }
/*     */     
/* 507 */     if (this.colorSet)
/*     */     {
/* 509 */       compound.setInteger("Color", getColor());
/*     */     }
/*     */     
/* 512 */     if (this.potion != PotionTypes.EMPTY && this.potion != null)
/*     */     {
/* 514 */       compound.setString("Potion", ((ResourceLocation)PotionType.REGISTRY.getNameForObject(this.potion)).toString());
/*     */     }
/*     */     
/* 517 */     if (!this.effects.isEmpty()) {
/*     */       
/* 519 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/* 521 */       for (PotionEffect potioneffect : this.effects)
/*     */       {
/* 523 */         nbttaglist.appendTag((NBTBase)potioneffect.writeCustomPotionEffectToNBT(new NBTTagCompound()));
/*     */       }
/*     */       
/* 526 */       compound.setTag("Effects", (NBTBase)nbttaglist);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyDataManagerChange(DataParameter<?> key) {
/* 532 */     if (RADIUS.equals(key))
/*     */     {
/* 534 */       setRadius(getRadius());
/*     */     }
/*     */     
/* 537 */     super.notifyDataManagerChange(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumPushReaction getPushReaction() {
/* 542 */     return EnumPushReaction.IGNORE;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\EntityAreaEffectCloud.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */