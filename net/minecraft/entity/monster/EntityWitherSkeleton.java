/*     */ package net.minecraft.entity.monster;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.SharedMonsterAttributes;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.MobEffects;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.inventory.EntityEquipmentSlot;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.potion.PotionEffect;
/*     */ import net.minecraft.util.DamageSource;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.SoundEvent;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.world.DifficultyInstance;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.storage.loot.LootTableList;
/*     */ 
/*     */ public class EntityWitherSkeleton
/*     */   extends AbstractSkeleton
/*     */ {
/*     */   public EntityWitherSkeleton(World p_i47278_1_) {
/*  28 */     super(p_i47278_1_);
/*  29 */     setSize(0.7F, 2.4F);
/*  30 */     this.isImmuneToFire = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_190729_b(DataFixer p_190729_0_) {
/*  35 */     EntityLiving.registerFixesMob(p_190729_0_, EntityWitherSkeleton.class);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected ResourceLocation getLootTable() {
/*  41 */     return LootTableList.ENTITIES_WITHER_SKELETON;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getAmbientSound() {
/*  46 */     return SoundEvents.ENTITY_WITHER_SKELETON_AMBIENT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getHurtSound(DamageSource p_184601_1_) {
/*  51 */     return SoundEvents.ENTITY_WITHER_SKELETON_HURT;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SoundEvent getDeathSound() {
/*  56 */     return SoundEvents.ENTITY_WITHER_SKELETON_DEATH;
/*     */   }
/*     */ 
/*     */   
/*     */   SoundEvent func_190727_o() {
/*  61 */     return SoundEvents.ENTITY_WITHER_SKELETON_STEP;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDeath(DamageSource cause) {
/*  69 */     super.onDeath(cause);
/*     */     
/*  71 */     if (cause.getEntity() instanceof EntityCreeper) {
/*     */       
/*  73 */       EntityCreeper entitycreeper = (EntityCreeper)cause.getEntity();
/*     */       
/*  75 */       if (entitycreeper.getPowered() && entitycreeper.isAIEnabled()) {
/*     */         
/*  77 */         entitycreeper.incrementDroppedSkulls();
/*  78 */         entityDropItem(new ItemStack(Items.SKULL, 1, 1), 0.0F);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
/*  88 */     setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.STONE_SWORD));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEnchantmentBasedOnDifficulty(DifficultyInstance difficulty) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
/* 106 */     IEntityLivingData ientitylivingdata = super.onInitialSpawn(difficulty, livingdata);
/* 107 */     getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
/* 108 */     setCombatTask();
/* 109 */     return ientitylivingdata;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getEyeHeight() {
/* 114 */     return 2.1F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean attackEntityAsMob(Entity entityIn) {
/* 119 */     if (!super.attackEntityAsMob(entityIn))
/*     */     {
/* 121 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 125 */     if (entityIn instanceof EntityLivingBase)
/*     */     {
/* 127 */       ((EntityLivingBase)entityIn).addPotionEffect(new PotionEffect(MobEffects.WITHER, 200));
/*     */     }
/*     */     
/* 130 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected EntityArrow func_190726_a(float p_190726_1_) {
/* 136 */     EntityArrow entityarrow = super.func_190726_a(p_190726_1_);
/* 137 */     entityarrow.setFire(100);
/* 138 */     return entityarrow;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\monster\EntityWitherSkeleton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */