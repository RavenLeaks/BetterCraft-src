/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EnumCreatureAttribute;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.ai.EntityAIAttackMelee;
/*     */ import net.minecraft.entity.ai.EntityAIBase;
/*     */ import net.minecraft.entity.ai.EntityAIHurtByTarget;
/*     */ import net.minecraft.entity.ai.EntityAILookIdle;
/*     */ import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
/*     */ import net.minecraft.entity.ai.EntityAISwimming;
/*     */ import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
/*     */ import net.minecraft.entity.ai.EntityAIWatchClosest;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityEndermite extends EntityMob {
/*     */   private int lifetime;
/*     */   private boolean playerSpawned;
/*     */   
/*     */   public EntityEndermite(World worldIn) {
/*  34 */     super(worldIn);
/*  35 */     this.experienceValue = 3;
/*  36 */     setSize(0.4F, 0.3F);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initEntityAI() {
/*  41 */     this.tasks.addTask(1, (EntityAIBase)new EntityAISwimming((EntityLiving)this));
/*  42 */     this.tasks.addTask(2, (EntityAIBase)new EntityAIAttackMelee(this, 1.0D, false));
/*  43 */     this.tasks.addTask(3, (EntityAIBase)new EntityAIWanderAvoidWater(this, 1.0D));
/*  44 */     this.tasks.addTask(7, (EntityAIBase)new EntityAIWatchClosest((EntityLiving)this, EntityPlayer.class, 8.0F));
/*  45 */     this.tasks.addTask(8, (EntityAIBase)new EntityAILookIdle((EntityLiving)this));
/*  46 */     this.targetTasks.addTask(1, (EntityAIBase)new EntityAIHurtByTarget(this, true, new Class[0]));
/*  47 */     this.targetTasks.addTask(2, (EntityAIBase)new EntityAINearestAttackableTarget(this, EntityPlayer.class, true));
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/*  52 */     return 0.1F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  57 */     super.applyEntityAttributes();
/*  58 */     getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
/*  59 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
/*  60 */     getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(2.0D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canTriggerWalking() {
/*  69 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  74 */     return SoundEvents.ENTITY_ENDERMITE_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  79 */     return SoundEvents.ENTITY_ENDERMITE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  84 */     return SoundEvents.ENTITY_ENDERMITE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void playStepSound(BlockPos pos, Block blockIn) {
/*  89 */     playSound(SoundEvents.ENTITY_ENDERMITE_STEP, 0.15F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/*  95 */     return LootTableList.ENTITIES_ENDERMITE;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesEndermite(DataFixer fixer) {
/* 100 */     EntityLiving.registerFixesMob(fixer, EntityEndermite.class);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readEntityFromNBT(NBTTagCompound compound) {
/* 108 */     super.readEntityFromNBT(compound);
/* 109 */     this.lifetime = compound.getInteger("Lifetime");
/* 110 */     this.playerSpawned = compound.getBoolean("PlayerSpawned");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeEntityToNBT(NBTTagCompound compound) {
/* 118 */     super.writeEntityToNBT(compound);
/* 119 */     compound.setInteger("Lifetime", this.lifetime);
/* 120 */     compound.setBoolean("PlayerSpawned", this.playerSpawned);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onUpdate() {
/* 128 */     this.renderYawOffset = this.rotationYaw;
/* 129 */     super.onUpdate();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRenderYawOffset(float offset) {
/* 137 */     this.rotationYaw = offset;
/* 138 */     super.setRenderYawOffset(offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getYOffset() {
/* 146 */     return 0.1D;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSpawnedByPlayer() {
/* 151 */     return this.playerSpawned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnedByPlayer(boolean spawnedByPlayer) {
/* 159 */     this.playerSpawned = spawnedByPlayer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onLivingUpdate() {
/* 168 */     super.onLivingUpdate();
/*     */     
/* 170 */     if (this.world.isRemote) {
/*     */       
/* 172 */       for (int i = 0; i < 2; i++)
/*     */       {
/* 174 */         this.world.spawnParticle(EnumParticleTypes.PORTAL, this.posX + (this.rand.nextDouble() - 0.5D) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5D) * this.width, (this.rand.nextDouble() - 0.5D) * 2.0D, -this.rand.nextDouble(), (this.rand.nextDouble() - 0.5D) * 2.0D, new int[0]);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 179 */       if (!isNoDespawnRequired())
/*     */       {
/* 181 */         this.lifetime++;
/*     */       }
/*     */       
/* 184 */       if (this.lifetime >= 2400)
/*     */       {
/* 186 */         setDead();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isValidLightLevel() {
/* 196 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/* 204 */     if (super.getCanSpawnHere()) {
/*     */       
/* 206 */       EntityPlayer entityplayer = this.world.getClosestPlayerToEntity((Entity)this, 5.0D);
/* 207 */       return (entityplayer == null);
/*     */     } 
/*     */ 
/*     */     
/* 211 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumCreatureAttribute getCreatureAttribute() {
/* 220 */     return EnumCreatureAttribute.ARTHROPOD;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityEndermite.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */