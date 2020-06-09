/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.Collection;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAreaEffectCloud;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackMelee;
/*     */ import net.minecraft.entity.ai.EntityAIAvoidEntity;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAICreeperSwell;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.effect.EntityLightningBolt;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityCreeper extends EntityMob {
/*  40 */   private static final DataParameter<Integer> STATE = EntityDataManager.createKey(EntityCreeper.class, DataSerializers.VARINT);
/*  41 */   private static final DataParameter<Boolean> POWERED = EntityDataManager.createKey(EntityCreeper.class, DataSerializers.BOOLEAN);
/*  42 */   private static final DataParameter<Boolean> IGNITED = EntityDataManager.createKey(EntityCreeper.class, DataSerializers.BOOLEAN);
/*     */ 
/*     */ 
/*     */   
/*     */   private int lastActiveTime;
/*     */ 
/*     */ 
/*     */   
/*     */   private int timeSinceIgnited;
/*     */ 
/*     */ 
/*     */   
/*  54 */   private int fuseTime = 30;
/*     */ 
/*     */   
/*  57 */   private int explosionRadius = 3;
/*     */   
/*     */   private int droppedSkulls;
/*     */   
/*     */   public EntityCreeper(World worldIn) {
/*  62 */     super(worldIn);
/*  63 */     setSize(0.6F, 1.7F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  68 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  69 */     this.tasks.addTask(2, (EntityAIBase)new EntityAICreeperSwell(this));
/*  70 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIAvoidEntity(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
/*  71 */     this.tasks.addTask(4, (EntityAIBase)new EntityAIAttackMelee(this, 1.0D, false));
/*  72 */     this.tasks.addTask(5, (EntityAIBase)new EntityAIWanderAvoidWater(this, 0.8D));
/*  73 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  74 */     this.tasks.addTask(6, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  75 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*  76 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  81 */     super.applyEntityAttributes();
/*  82 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxFallHeight() {
/*  90 */     return (getAttackTarget() == null) ? 3 : (3 + (int)(getHealth() - 1.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {
/*  95 */     super.fall(distance, damageMultiplier);
/*  96 */     this.timeSinceIgnited = (int)(this.timeSinceIgnited + distance * 1.5F);
/*     */     
/*  98 */     if (this.timeSinceIgnited > this.fuseTime - 5)
/*     */     {
/* 100 */       this.timeSinceIgnited = this.fuseTime - 5;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/* 106 */     super.entityInit();
/* 107 */     this.dataManager.register(STATE, Integer.valueOf(-1));
/* 108 */     this.dataManager.register(POWERED, Boolean.valueOf(false));
/* 109 */     this.dataManager.register(IGNITED, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesCreeper(DataFixer fixer) {
/* 114 */     EntityLiving.registerFixesMob(fixer, EntityCreeper.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 122 */     super.writeEntityToNBT(compound);
/*     */     
/* 124 */     if (((Boolean)this.dataManager.get(POWERED)).booleanValue())
/*     */     {
/* 126 */       compound.setBoolean("powered", true);
/*     */     }
/*     */     
/* 129 */     compound.setShort("Fuse", (short)this.fuseTime);
/* 130 */     compound.setByte("ExplosionRadius", (byte)this.explosionRadius);
/* 131 */     compound.setBoolean("ignited", hasIgnited());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 139 */     super.readEntityFromNBT(compound);
/* 140 */     this.dataManager.set(POWERED, Boolean.valueOf(compound.getBoolean("powered")));
/*     */     
/* 142 */     if (compound.hasKey("Fuse", 99))
/*     */     {
/* 144 */       this.fuseTime = compound.getShort("Fuse");
/*     */     }
/*     */     
/* 147 */     if (compound.hasKey("ExplosionRadius", 99))
/*     */     {
/* 149 */       this.explosionRadius = compound.getByte("ExplosionRadius");
/*     */     }
/*     */     
/* 152 */     if (compound.getBoolean("ignited"))
/*     */     {
/* 154 */       ignite();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 163 */     if (isEntityAlive()) {
/*     */       
/* 165 */       this.lastActiveTime = this.timeSinceIgnited;
/*     */       
/* 167 */       if (hasIgnited())
/*     */       {
/* 169 */         setCreeperState(1);
/*     */       }
/*     */       
/* 172 */       int i = getCreeperState();
/*     */       
/* 174 */       if (i > 0 && this.timeSinceIgnited == 0)
/*     */       {
/* 176 */         playSound(SoundEvents.ENTITY_CREEPER_PRIMED, 1.0F, 0.5F);
/*     */       }
/*     */       
/* 179 */       this.timeSinceIgnited += i;
/*     */       
/* 181 */       if (this.timeSinceIgnited < 0)
/*     */       {
/* 183 */         this.timeSinceIgnited = 0;
/*     */       }
/*     */       
/* 186 */       if (this.timeSinceIgnited >= this.fuseTime) {
/*     */         
/* 188 */         this.timeSinceIgnited = this.fuseTime;
/* 189 */         explode();
/*     */       } 
/*     */     } 
/*     */     
/* 193 */     super.onUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 198 */     return SoundEvents.ENTITY_CREEPER_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 203 */     return SoundEvents.ENTITY_CREEPER_DEATH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/* 211 */     super.onDeath(cause);
/*     */     
/* 213 */     if (this.world.getGameRules().getBoolean("doMobLoot"))
/*     */     {
/* 215 */       if (cause.getEntity() instanceof EntitySkeleton) {
/*     */         
/* 217 */         int i = Item.getIdFromItem(Items.RECORD_13);
/* 218 */         int j = Item.getIdFromItem(Items.RECORD_WAIT);
/* 219 */         int k = i + this.rand.nextInt(j - i + 1);
/* 220 */         dropItem(Item.getItemById(k), 1);
/*     */       }
/* 222 */       else if (cause.getEntity() instanceof EntityCreeper && cause.getEntity() != this && ((EntityCreeper)cause.getEntity()).getPowered() && ((EntityCreeper)cause.getEntity()).isAIEnabled()) {
/*     */         
/* 224 */         ((EntityCreeper)cause.getEntity()).incrementDroppedSkulls();
/* 225 */         entityDropItem(new ItemStack(Items.SKULL, 1, 4), 0.0F);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 232 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPowered() {
/* 240 */     return ((Boolean)this.dataManager.get(POWERED)).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCreeperFlashIntensity(float p_70831_1_) {
/* 248 */     return (this.lastActiveTime + (this.timeSinceIgnited - this.lastActiveTime) * p_70831_1_) / (this.fuseTime - 2);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 254 */     return LootTableList.ENTITIES_CREEPER;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCreeperState() {
/* 262 */     return ((Integer)this.dataManager.get(STATE)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCreeperState(int state) {
/* 270 */     this.dataManager.set(STATE, Integer.valueOf(state));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onStruckByLightning(EntityLightningBolt lightningBolt) {
/* 278 */     super.onStruckByLightning(lightningBolt);
/* 279 */     this.dataManager.set(POWERED, Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 284 */     ItemStack itemstack = player.getHeldItem(hand);
/*     */     
/* 286 */     if (itemstack.getItem() == Items.FLINT_AND_STEEL) {
/*     */       
/* 288 */       this.world.playSound(player, this.posX, this.posY, this.posZ, SoundEvents.ITEM_FLINTANDSTEEL_USE, getSoundCategory(), 1.0F, this.rand.nextFloat() * 0.4F + 0.8F);
/* 289 */       player.swingArm(hand);
/*     */       
/* 291 */       if (!this.world.isRemote) {
/*     */         
/* 293 */         ignite();
/* 294 */         itemstack.damageItem(1, (EntityLivingBase)player);
/* 295 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 299 */     return super.processInteract(player, hand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void explode() {
/* 307 */     if (!this.world.isRemote) {
/*     */       
/* 309 */       boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
/* 310 */       float f = getPowered() ? 2.0F : 1.0F;
/* 311 */       this.dead = true;
/* 312 */       this.world.createExplosion((Entity)this, this.posX, this.posY, this.posZ, this.explosionRadius * f, flag);
/* 313 */       setDead();
/* 314 */       func_190741_do();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190741_do() {
/* 320 */     Collection<PotionEffect> collection = getActivePotionEffects();
/*     */     
/* 322 */     if (!collection.isEmpty()) {
/*     */       
/* 324 */       EntityAreaEffectCloud entityareaeffectcloud = new EntityAreaEffectCloud(this.world, this.posX, this.posY, this.posZ);
/* 325 */       entityareaeffectcloud.setRadius(2.5F);
/* 326 */       entityareaeffectcloud.setRadiusOnUse(-0.5F);
/* 327 */       entityareaeffectcloud.setWaitTime(10);
/* 328 */       entityareaeffectcloud.setDuration(entityareaeffectcloud.getDuration() / 2);
/* 329 */       entityareaeffectcloud.setRadiusPerTick(-entityareaeffectcloud.getRadius() / entityareaeffectcloud.getDuration());
/*     */       
/* 331 */       for (PotionEffect potioneffect : collection)
/*     */       {
/* 333 */         entityareaeffectcloud.addEffect(new PotionEffect(potioneffect));
/*     */       }
/*     */       
/* 336 */       this.world.spawnEntityInWorld((Entity)entityareaeffectcloud);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasIgnited() {
/* 342 */     return ((Boolean)this.dataManager.get(IGNITED)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void ignite() {
/* 347 */     this.dataManager.set(IGNITED, Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAIEnabled() {
/* 355 */     return (this.droppedSkulls < 1 && this.world.getGameRules().getBoolean("doMobLoot"));
/*     */   }
/*     */ 
/*     */   
/*     */   public void incrementDroppedSkulls() {
/* 360 */     this.droppedSkulls++;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityCreeper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */