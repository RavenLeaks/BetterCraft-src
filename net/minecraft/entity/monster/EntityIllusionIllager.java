/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.IRangedAttackMob;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackRangedBow;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWander;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.passive.EntityVillager;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.entity.projectile.EntityTippedArrow;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityIllusionIllager extends EntitySpellcasterIllager implements IRangedAttackMob {
/*     */   private int field_193099_c;
/*     */   private final Vec3d[][] field_193100_bx;
/*     */   
/*     */   public EntityIllusionIllager(World p_i47507_1_) {
/*  47 */     super(p_i47507_1_);
/*  48 */     setSize(0.6F, 1.95F);
/*  49 */     this.experienceValue = 5;
/*  50 */     this.field_193100_bx = new Vec3d[2][4];
/*     */     
/*  52 */     for (int i = 0; i < 4; i++) {
/*     */       
/*  54 */       this.field_193100_bx[0][i] = new Vec3d(0.0D, 0.0D, 0.0D);
/*  55 */       this.field_193100_bx[1][i] = new Vec3d(0.0D, 0.0D, 0.0D);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  61 */     super.initEntityAI();
/*  62 */     this.tasks.addTask(0, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  63 */     this.tasks.addTask(1, new EntitySpellcasterIllager.AICastingApell(this));
/*  64 */     this.tasks.addTask(4, new AIMirriorSpell(null));
/*  65 */     this.tasks.addTask(5, new AIBlindnessSpell(null));
/*  66 */     this.tasks.addTask(6, (EntityAIBase)new EntityAIAttackRangedBow(this, 0.5D, 20, 15.0F));
/*  67 */     this.tasks.addTask(8, (EntityAIBase)new EntityAIWander(this, 0.6D));
/*  68 */     this.tasks.addTask(9, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 3.0F, 1.0F));
/*  69 */     this.tasks.addTask(10, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityLiving.class, 8.0F));
/*  70 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[] { EntityIllusionIllager.class }));
/*  71 */     this.targetTasks.addTask(2, (EntityAIBase)(new EntityAINearestAttackableTarget(this, EntityPlayer.class, true)).func_190882_b(300));
/*  72 */     this.targetTasks.addTask(3, (EntityAIBase)(new EntityAINearestAttackableTarget(this, EntityVillager.class, false)).func_190882_b(300));
/*  73 */     this.targetTasks.addTask(3, (EntityAIBase)(new EntityAINearestAttackableTarget(this, EntityIronGolem.class, false)).func_190882_b(300));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  78 */     super.applyEntityAttributes();
/*  79 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
/*  80 */     getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(18.0D);
/*  81 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(32.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata) {
/*  90 */     setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack((Item)Items.BOW));
/*  91 */     return super.onInitialSpawn(difficulty, livingdata);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void entityInit() {
/*  96 */     super.entityInit();
/*     */   }
/*     */ 
/*     */   
/*     */   protected ResourceLocation getLootTable() {
/* 101 */     return LootTableList.EMPTY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getRenderBoundingBox() {
/* 110 */     return getEntityBoundingBox().expand(3.0D, 0.0D, 3.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 119 */     super.onLivingUpdate();
/*     */     
/* 121 */     if (this.world.isRemote && isInvisible()) {
/*     */       
/* 123 */       this.field_193099_c--;
/*     */       
/* 125 */       if (this.field_193099_c < 0)
/*     */       {
/* 127 */         this.field_193099_c = 0;
/*     */       }
/*     */       
/* 130 */       if (this.hurtTime != 1 && this.ticksExisted % 1200 != 0) {
/*     */         
/* 132 */         if (this.hurtTime == this.maxHurtTime - 1) {
/*     */           
/* 134 */           this.field_193099_c = 3;
/*     */           
/* 136 */           for (int k = 0; k < 4; k++)
/*     */           {
/* 138 */             this.field_193100_bx[0][k] = this.field_193100_bx[1][k];
/* 139 */             this.field_193100_bx[1][k] = new Vec3d(0.0D, 0.0D, 0.0D);
/*     */           }
/*     */         
/*     */         } 
/*     */       } else {
/*     */         
/* 145 */         this.field_193099_c = 3;
/* 146 */         float f = -6.0F;
/* 147 */         int i = 13;
/*     */         
/* 149 */         for (int j = 0; j < 4; j++) {
/*     */           
/* 151 */           this.field_193100_bx[0][j] = this.field_193100_bx[1][j];
/* 152 */           this.field_193100_bx[1][j] = new Vec3d((-6.0F + this.rand.nextInt(13)) * 0.5D, Math.max(0, this.rand.nextInt(6) - 4), (-6.0F + this.rand.nextInt(13)) * 0.5D);
/*     */         } 
/*     */         
/* 155 */         for (int l = 0; l < 16; l++)
/*     */         {
/* 157 */           this.world.spawnParticle(EnumParticleTypes.CLOUD, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */         
/* 160 */         this.world.playSound(this.posX, this.posY, this.posZ, SoundEvents.field_193788_dg, getSoundCategory(), 1.0F, 1.0F, false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d[] func_193098_a(float p_193098_1_) {
/* 167 */     if (this.field_193099_c <= 0)
/*     */     {
/* 169 */       return this.field_193100_bx[1];
/*     */     }
/*     */ 
/*     */     
/* 173 */     double d0 = ((this.field_193099_c - p_193098_1_) / 3.0F);
/* 174 */     d0 = Math.pow(d0, 0.25D);
/* 175 */     Vec3d[] avec3d = new Vec3d[4];
/*     */     
/* 177 */     for (int i = 0; i < 4; i++)
/*     */     {
/* 179 */       avec3d[i] = this.field_193100_bx[1][i].scale(1.0D - d0).add(this.field_193100_bx[0][i].scale(d0));
/*     */     }
/*     */     
/* 182 */     return avec3d;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnSameTeam(Entity entityIn) {
/* 191 */     if (super.isOnSameTeam(entityIn))
/*     */     {
/* 193 */       return true;
/*     */     }
/* 195 */     if (entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getCreatureAttribute() == EnumCreatureAttribute.ILLAGER)
/*     */     {
/* 197 */       return (getTeam() == null && entityIn.getTeam() == null);
/*     */     }
/*     */ 
/*     */     
/* 201 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 207 */     return SoundEvents.field_193783_dc;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 212 */     return SoundEvents.field_193786_de;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 217 */     return SoundEvents.field_193787_df;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent func_193086_dk() {
/* 222 */     return SoundEvents.field_193784_dd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) {
/* 232 */     EntityArrow entityarrow = func_193097_t(distanceFactor);
/* 233 */     double d0 = target.posX - this.posX;
/* 234 */     double d1 = (target.getEntityBoundingBox()).minY + (target.height / 3.0F) - entityarrow.posY;
/* 235 */     double d2 = target.posZ - this.posZ;
/* 236 */     double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
/* 237 */     entityarrow.setThrowableHeading(d0, d1 + d3 * 0.20000000298023224D, d2, 1.6F, (14 - this.world.getDifficulty().getDifficultyId() * 4));
/* 238 */     playSound(SoundEvents.ENTITY_SKELETON_SHOOT, 1.0F, 1.0F / (getRNG().nextFloat() * 0.4F + 0.8F));
/* 239 */     this.world.spawnEntityInWorld((Entity)entityarrow);
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntityArrow func_193097_t(float p_193097_1_) {
/* 244 */     EntityTippedArrow entitytippedarrow = new EntityTippedArrow(this.world, (EntityLivingBase)this);
/* 245 */     entitytippedarrow.func_190547_a((EntityLivingBase)this, p_193097_1_);
/* 246 */     return (EntityArrow)entitytippedarrow;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193096_dj() {
/* 251 */     return func_193078_a(1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSwingingArms(boolean swingingArms) {
/* 256 */     func_193079_a(1, swingingArms);
/*     */   }
/*     */ 
/*     */   
/*     */   public AbstractIllager.IllagerArmPose func_193077_p() {
/* 261 */     if (func_193082_dl())
/*     */     {
/* 263 */       return AbstractIllager.IllagerArmPose.SPELLCASTING;
/*     */     }
/*     */ 
/*     */     
/* 267 */     return func_193096_dj() ? AbstractIllager.IllagerArmPose.BOW_AND_ARROW : AbstractIllager.IllagerArmPose.CROSSED;
/*     */   }
/*     */ 
/*     */   
/*     */   class AIBlindnessSpell
/*     */     extends EntitySpellcasterIllager.AIUseSpell
/*     */   {
/*     */     private int field_193325_b;
/*     */ 
/*     */     
/*     */     private AIBlindnessSpell() {}
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 281 */       if (!super.shouldExecute())
/*     */       {
/* 283 */         return false;
/*     */       }
/* 285 */       if (EntityIllusionIllager.this.getAttackTarget() == null)
/*     */       {
/* 287 */         return false;
/*     */       }
/* 289 */       if (EntityIllusionIllager.this.getAttackTarget().getEntityId() == this.field_193325_b)
/*     */       {
/* 291 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 295 */       return EntityIllusionIllager.this.world.getDifficultyForLocation(new BlockPos((Entity)EntityIllusionIllager.this)).func_193845_a(EnumDifficulty.NORMAL.ordinal());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void startExecuting() {
/* 301 */       super.startExecuting();
/* 302 */       this.field_193325_b = EntityIllusionIllager.this.getAttackTarget().getEntityId();
/*     */     }
/*     */ 
/*     */     
/*     */     protected int func_190869_f() {
/* 307 */       return 20;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int func_190872_i() {
/* 312 */       return 180;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_190868_j() {
/* 317 */       EntityIllusionIllager.this.getAttackTarget().addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 400));
/*     */     }
/*     */ 
/*     */     
/*     */     protected SoundEvent func_190871_k() {
/* 322 */       return SoundEvents.field_193789_dh;
/*     */     }
/*     */ 
/*     */     
/*     */     protected EntitySpellcasterIllager.SpellType func_193320_l() {
/* 327 */       return EntitySpellcasterIllager.SpellType.BLINDNESS;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   class AIMirriorSpell
/*     */     extends EntitySpellcasterIllager.AIUseSpell
/*     */   {
/*     */     private AIMirriorSpell() {}
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 339 */       if (!super.shouldExecute())
/*     */       {
/* 341 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 345 */       return !EntityIllusionIllager.this.isPotionActive(MobEffects.INVISIBILITY);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected int func_190869_f() {
/* 351 */       return 20;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int func_190872_i() {
/* 356 */       return 340;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void func_190868_j() {
/* 361 */       EntityIllusionIllager.this.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 1200));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     protected SoundEvent func_190871_k() {
/* 367 */       return SoundEvents.field_193790_di;
/*     */     }
/*     */ 
/*     */     
/*     */     protected EntitySpellcasterIllager.SpellType func_193320_l() {
/* 372 */       return EntitySpellcasterIllager.SpellType.DISAPPEAR;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityIllusionIllager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */