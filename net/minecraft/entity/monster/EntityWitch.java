/*     */ package net.minecraft.entity.monster;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackRanged;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityPotion;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.PotionTypes;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.network.datasync.DataParameter;
/*     */ import net.minecraft.network.datasync.DataSerializers;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.potion.PotionType;
/*     */ import net.minecraft.potion.PotionUtils;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityWitch extends EntityMob implements IRangedAttackMob {
/*  45 */   private static final UUID MODIFIER_UUID = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
/*  46 */   private static final AttributeModifier MODIFIER = (new AttributeModifier(MODIFIER_UUID, "Drinking speed penalty", -0.25D, 0)).setSaved(false);
/*  47 */   private static final DataParameter<Boolean> IS_AGGRESSIVE = EntityDataManager.createKey(EntityWitch.class, DataSerializers.BOOLEAN);
/*     */ 
/*     */ 
/*     */   
/*     */   private int witchAttackTimer;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityWitch(World worldIn) {
/*  57 */     super(worldIn);
/*  58 */     setSize(0.6F, 1.95F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesWitch(DataFixer fixer) {
/*  63 */     EntityLiving.registerFixesMob(fixer, EntityWitch.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  68 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  69 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIAttackRanged(this, 1.0D, 60, 10.0F));
/*  70 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIWanderAvoidWater(this, 1.0D));
/*  71 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  72 */     this.tasks.addTask(3, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  73 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, false, new Class[0]));
/*  74 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  79 */     super.entityInit();
/*  80 */     getDataManager().register(IS_AGGRESSIVE, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  85 */     return SoundEvents.ENTITY_WITCH_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  90 */     return SoundEvents.ENTITY_WITCH_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  95 */     return SoundEvents.ENTITY_WITCH_DEATH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAggressive(boolean aggressive) {
/* 103 */     getDataManager().set(IS_AGGRESSIVE, Boolean.valueOf(aggressive));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDrinkingPotion() {
/* 108 */     return ((Boolean)getDataManager().get(IS_AGGRESSIVE)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/* 113 */     super.applyEntityAttributes();
/* 114 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(26.0D);
/* 115 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 124 */     if (!this.world.isRemote) {
/*     */       
/* 126 */       if (isDrinkingPotion()) {
/*     */         
/* 128 */         if (this.witchAttackTimer-- <= 0)
/*     */         {
/* 130 */           setAggressive(false);
/* 131 */           ItemStack itemstack = getHeldItemMainhand();
/* 132 */           setItemStackToSlot(EntityEquipmentSlot.MAINHAND, ItemStack.field_190927_a);
/*     */           
/* 134 */           if (itemstack.getItem() == Items.POTIONITEM) {
/*     */             
/* 136 */             List<PotionEffect> list = PotionUtils.getEffectsFromStack(itemstack);
/*     */             
/* 138 */             if (list != null)
/*     */             {
/* 140 */               for (PotionEffect potioneffect : list)
/*     */               {
/* 142 */                 addPotionEffect(new PotionEffect(potioneffect));
/*     */               }
/*     */             }
/*     */           } 
/*     */           
/* 147 */           getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).removeModifier(MODIFIER);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 152 */         PotionType potiontype = null;
/*     */         
/* 154 */         if (this.rand.nextFloat() < 0.15F && isInsideOfMaterial(Material.WATER) && !isPotionActive(MobEffects.WATER_BREATHING)) {
/*     */           
/* 156 */           potiontype = PotionTypes.WATER_BREATHING;
/*     */         }
/* 158 */         else if (this.rand.nextFloat() < 0.15F && (isBurning() || (getLastDamageSource() != null && getLastDamageSource().isFireDamage())) && !isPotionActive(MobEffects.FIRE_RESISTANCE)) {
/*     */           
/* 160 */           potiontype = PotionTypes.FIRE_RESISTANCE;
/*     */         }
/* 162 */         else if (this.rand.nextFloat() < 0.05F && getHealth() < getMaxHealth()) {
/*     */           
/* 164 */           potiontype = PotionTypes.HEALING;
/*     */         }
/* 166 */         else if (this.rand.nextFloat() < 0.5F && getAttackTarget() != null && !isPotionActive(MobEffects.SPEED) && getAttackTarget().getDistanceSqToEntity((Entity)this) > 121.0D) {
/*     */           
/* 168 */           potiontype = PotionTypes.SWIFTNESS;
/*     */         } 
/*     */         
/* 171 */         if (potiontype != null) {
/*     */           
/* 173 */           setItemStackToSlot(EntityEquipmentSlot.MAINHAND, PotionUtils.addPotionToItemStack(new ItemStack((Item)Items.POTIONITEM), potiontype));
/* 174 */           this.witchAttackTimer = getHeldItemMainhand().getMaxItemUseDuration();
/* 175 */           setAggressive(true);
/* 176 */           this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_DRINK, getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
/* 177 */           IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
/* 178 */           iattributeinstance.removeModifier(MODIFIER);
/* 179 */           iattributeinstance.applyModifier(MODIFIER);
/*     */         } 
/*     */       } 
/*     */       
/* 183 */       if (this.rand.nextFloat() < 7.5E-4F)
/*     */       {
/* 185 */         this.world.setEntityState((Entity)this, (byte)15);
/*     */       }
/*     */     } 
/*     */     
/* 189 */     super.onLivingUpdate();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleStatusUpdate(byte id) {
/* 194 */     if (id == 15) {
/*     */       
/* 196 */       for (int i = 0; i < this.rand.nextInt(35) + 10; i++)
/*     */       {
/* 198 */         this.world.spawnParticle(EnumParticleTypes.SPELL_WITCH, this.posX + this.rand.nextGaussian() * 0.12999999523162842D, (getEntityBoundingBox()).maxY + 0.5D + this.rand.nextGaussian() * 0.12999999523162842D, this.posZ + this.rand.nextGaussian() * 0.12999999523162842D, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 203 */       super.handleStatusUpdate(id);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected float applyPotionDamageCalculations(DamageSource source, float damage) {
/* 212 */     damage = super.applyPotionDamageCalculations(source, damage);
/*     */     
/* 214 */     if (source.getEntity() == this)
/*     */     {
/* 216 */       damage = 0.0F;
/*     */     }
/*     */     
/* 219 */     if (source.isMagicDamage())
/*     */     {
/* 221 */       damage = (float)(damage * 0.15D);
/*     */     }
/*     */     
/* 224 */     return damage;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 230 */     return LootTableList.ENTITIES_WITCH;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
/* 240 */     if (!isDrinkingPotion()) {
/*     */       
/* 242 */       double d0 = target.posY + target.getEyeHeight() - 1.100000023841858D;
/* 243 */       double d1 = target.posX + target.motionX - this.posX;
/* 244 */       double d2 = d0 - this.posY;
/* 245 */       double d3 = target.posZ + target.motionZ - this.posZ;
/* 246 */       float f = MathHelper.sqrt(d1 * d1 + d3 * d3);
/* 247 */       PotionType potiontype = PotionTypes.HARMING;
/*     */       
/* 249 */       if (f >= 8.0F && !target.isPotionActive(MobEffects.SLOWNESS)) {
/*     */         
/* 251 */         potiontype = PotionTypes.SLOWNESS;
/*     */       }
/* 253 */       else if (target.getHealth() >= 8.0F && !target.isPotionActive(MobEffects.POISON)) {
/*     */         
/* 255 */         potiontype = PotionTypes.POISON;
/*     */       }
/* 257 */       else if (f <= 3.0F && !target.isPotionActive(MobEffects.WEAKNESS) && this.rand.nextFloat() < 0.25F) {
/*     */         
/* 259 */         potiontype = PotionTypes.WEAKNESS;
/*     */       } 
/*     */       
/* 262 */       EntityPotion entitypotion = new EntityPotion(this.world, (EntityLivingBase)this, PotionUtils.addPotionToItemStack(new ItemStack((Item)Items.SPLASH_POTION), potiontype));
/* 263 */       entitypotion.rotationPitch -= -20.0F;
/* 264 */       entitypotion.setThrowableHeading(d1, d2 + (f * 0.2F), d3, 0.75F, 8.0F);
/* 265 */       this.world.playSound(null, this.posX, this.posY, this.posZ, SoundEvents.ENTITY_WITCH_THROW, getSoundCategory(), 1.0F, 0.8F + this.rand.nextFloat() * 0.4F);
/* 266 */       this.world.spawnEntityInWorld((Entity)entitypotion);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 272 */     return 1.62F;
/*     */   }
/*     */   
/*     */   public void setSwingingArms(boolean swingingArms) {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityWitch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */