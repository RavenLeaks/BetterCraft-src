/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import java.util.UUID;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityCreature;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*     */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityPigZombie extends EntityZombie {
/*  32 */   private static final UUID ATTACK_SPEED_BOOST_MODIFIER_UUID = UUID.fromString("49455A49-7EC5-45BA-B886-3B90B23A1718");
/*  33 */   private static final AttributeModifier ATTACK_SPEED_BOOST_MODIFIER = (new AttributeModifier(ATTACK_SPEED_BOOST_MODIFIER_UUID, "Attacking speed boost", 0.05D, 0)).setSaved(false);
/*     */ 
/*     */   
/*     */   private int angerLevel;
/*     */   
/*     */   private int randomSoundDelay;
/*     */   
/*     */   private UUID angerTargetUUID;
/*     */ 
/*     */   
/*     */   public EntityPigZombie(World worldIn) {
/*  44 */     super(worldIn);
/*  45 */     this.isImmuneToFire = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRevengeTarget(@Nullable EntityLivingBase livingBase) {
/*  50 */     super.setRevengeTarget(livingBase);
/*     */     
/*  52 */     if (livingBase != null)
/*     */     {
/*  54 */       this.angerTargetUUID = livingBase.getUniqueID();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAI() {
/*  60 */     this.targetTasks.addTask(1, (EntityAIBase)new AIHurtByAggressor(this));
/*  61 */     this.targetTasks.addTask(2, (EntityAIBase)new AITargetAggressor(this));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  66 */     super.applyEntityAttributes();
/*  67 */     getEntityAttribute(SPAWN_REINFORCEMENTS_CHANCE).setBaseValue(0.0D);
/*  68 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.23000000417232513D);
/*  69 */     getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateAITasks() {
/*  74 */     IAttributeInstance iattributeinstance = getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED);
/*     */     
/*  76 */     if (isAngry()) {
/*     */       
/*  78 */       if (!isChild() && !iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER))
/*     */       {
/*  80 */         iattributeinstance.applyModifier(ATTACK_SPEED_BOOST_MODIFIER);
/*     */       }
/*     */       
/*  83 */       this.angerLevel--;
/*     */     }
/*  85 */     else if (iattributeinstance.hasModifier(ATTACK_SPEED_BOOST_MODIFIER)) {
/*     */       
/*  87 */       iattributeinstance.removeModifier(ATTACK_SPEED_BOOST_MODIFIER);
/*     */     } 
/*     */     
/*  90 */     if (this.randomSoundDelay > 0 && --this.randomSoundDelay == 0)
/*     */     {
/*  92 */       playSound(SoundEvents.ENTITY_ZOMBIE_PIG_ANGRY, getSoundVolume() * 2.0F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) * 1.8F);
/*     */     }
/*     */     
/*  95 */     if (this.angerLevel > 0 && this.angerTargetUUID != null && getAITarget() == null) {
/*     */       
/*  97 */       EntityPlayer entityplayer = this.world.getPlayerEntityByUUID(this.angerTargetUUID);
/*  98 */       setRevengeTarget((EntityLivingBase)entityplayer);
/*  99 */       this.attackingPlayer = entityplayer;
/* 100 */       this.recentlyHit = getRevengeTimer();
/*     */     } 
/*     */     
/* 103 */     super.updateAITasks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 111 */     return (this.world.getDifficulty() != EnumDifficulty.PEACEFUL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotColliding() {
/* 119 */     return (this.world.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.world.getCollisionBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(getEntityBoundingBox()));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesPigZombie(DataFixer fixer) {
/* 124 */     EntityLiving.registerFixesMob(fixer, EntityPigZombie.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 132 */     super.writeEntityToNBT(compound);
/* 133 */     compound.setShort("Anger", (short)this.angerLevel);
/*     */     
/* 135 */     if (this.angerTargetUUID != null) {
/*     */       
/* 137 */       compound.setString("HurtBy", this.angerTargetUUID.toString());
/*     */     }
/*     */     else {
/*     */       
/* 141 */       compound.setString("HurtBy", "");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 150 */     super.readEntityFromNBT(compound);
/* 151 */     this.angerLevel = compound.getShort("Anger");
/* 152 */     String s = compound.getString("HurtBy");
/*     */     
/* 154 */     if (!s.isEmpty()) {
/*     */       
/* 156 */       this.angerTargetUUID = UUID.fromString(s);
/* 157 */       EntityPlayer entityplayer = this.world.getPlayerEntityByUUID(this.angerTargetUUID);
/* 158 */       setRevengeTarget((EntityLivingBase)entityplayer);
/*     */       
/* 160 */       if (entityplayer != null) {
/*     */         
/* 162 */         this.attackingPlayer = entityplayer;
/* 163 */         this.recentlyHit = getRevengeTimer();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean attackEntityFrom(DamageSource source, float amount) {
/* 173 */     if (isEntityInvulnerable(source))
/*     */     {
/* 175 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 179 */     Entity entity = source.getEntity();
/*     */     
/* 181 */     if (entity instanceof EntityPlayer)
/*     */     {
/* 183 */       becomeAngryAt(entity);
/*     */     }
/*     */     
/* 186 */     return super.attackEntityFrom(source, amount);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void becomeAngryAt(Entity p_70835_1_) {
/* 195 */     this.angerLevel = 400 + this.rand.nextInt(400);
/* 196 */     this.randomSoundDelay = this.rand.nextInt(40);
/*     */     
/* 198 */     if (p_70835_1_ instanceof EntityLivingBase)
/*     */     {
/* 200 */       setRevengeTarget((EntityLivingBase)p_70835_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAngry() {
/* 206 */     return (this.angerLevel > 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/* 211 */     return SoundEvents.ENTITY_ZOMBIE_PIG_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 216 */     return SoundEvents.ENTITY_ZOMBIE_PIG_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 221 */     return SoundEvents.ENTITY_ZOMBIE_PIG_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/* 227 */     return LootTableList.ENTITIES_ZOMBIE_PIGMAN;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processInteract(EntityPlayer player, EnumHand hand) {
/* 232 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/* 240 */     setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack func_190732_dj() {
/* 245 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191990_c(EntityPlayer p_191990_1_) {
/* 250 */     return isAngry();
/*     */   }
/*     */   
/*     */   static class AIHurtByAggressor
/*     */     extends EntityAIHurtByTarget
/*     */   {
/*     */     public AIHurtByAggressor(EntityPigZombie p_i45828_1_) {
/* 257 */       super(p_i45828_1_, true, new Class[0]);
/*     */     }
/*     */ 
/*     */     
/*     */     protected void setEntityAttackTarget(EntityCreature creatureIn, EntityLivingBase entityLivingBaseIn) {
/* 262 */       super.setEntityAttackTarget(creatureIn, entityLivingBaseIn);
/*     */       
/* 264 */       if (creatureIn instanceof EntityPigZombie)
/*     */       {
/* 266 */         ((EntityPigZombie)creatureIn).becomeAngryAt((Entity)entityLivingBaseIn);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   static class AITargetAggressor
/*     */     extends EntityAINearestAttackableTarget<EntityPlayer>
/*     */   {
/*     */     public AITargetAggressor(EntityPigZombie p_i45829_1_) {
/* 275 */       super(p_i45829_1_, EntityPlayer.class, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean shouldExecute() {
/* 280 */       return (((EntityPigZombie)this.taskOwner).isAngry() && super.shouldExecute());
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityPigZombie.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */