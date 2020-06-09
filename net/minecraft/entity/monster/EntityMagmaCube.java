/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityMagmaCube
/*     */   extends EntitySlime {
/*     */   public EntityMagmaCube(World worldIn) {
/*  20 */     super(worldIn);
/*  21 */     this.isImmuneToFire = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixesMagmaCube(DataFixer fixer) {
/*  26 */     EntityLiving.registerFixesMob(fixer, EntityMagmaCube.class);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void applyEntityAttributes() {
/*  31 */     super.applyEntityAttributes();
/*  32 */     getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getCanSpawnHere() {
/*  40 */     return (this.world.getDifficulty() != EnumDifficulty.PEACEFUL);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isNotColliding() {
/*  48 */     return (this.world.checkNoEntityCollision(getEntityBoundingBox(), (Entity)this) && this.world.getCollisionBoxes((Entity)this, getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(getEntityBoundingBox()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void setSlimeSize(int size, boolean p_70799_2_) {
/*  53 */     super.setSlimeSize(size, p_70799_2_);
/*  54 */     getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue((size * 3));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getBrightnessForRender() {
/*  59 */     return 15728880;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getBrightness() {
/*  67 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected EnumParticleTypes getParticleType() {
/*  72 */     return EnumParticleTypes.FLAME;
/*     */   }
/*     */ 
/*     */   
/*     */   protected EntitySlime createInstance() {
/*  77 */     return new EntityMagmaCube(this.world);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/*  83 */     return isSmallSlime() ? LootTableList.EMPTY : LootTableList.ENTITIES_MAGMA_CUBE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/*  91 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getJumpDelay() {
/*  99 */     return super.getJumpDelay() * 4;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void alterSquishAmount() {
/* 104 */     this.squishAmount *= 0.9F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void jump() {
/* 112 */     this.motionY = (0.42F + getSlimeSize() * 0.1F);
/* 113 */     this.isAirBorne = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void handleJumpLava() {
/* 118 */     this.motionY = (0.22F + getSlimeSize() * 0.05F);
/* 119 */     this.isAirBorne = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fall(float distance, float damageMultiplier) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canDamagePlayer() {
/* 131 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getAttackStrength() {
/* 139 */     return super.getAttackStrength() + 2;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/* 144 */     return isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_HURT : SoundEvents.ENTITY_MAGMACUBE_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/* 149 */     return isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_DEATH : SoundEvents.ENTITY_MAGMACUBE_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getSquishSound() {
/* 154 */     return isSmallSlime() ? SoundEvents.ENTITY_SMALL_MAGMACUBE_SQUISH : SoundEvents.ENTITY_MAGMACUBE_SQUISH;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getJumpSound() {
/* 159 */     return SoundEvents.ENTITY_MAGMACUBE_JUMP;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityMagmaCube.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */