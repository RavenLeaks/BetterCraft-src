/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.advancements.CriteriaTriggers;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityAgeable;
/*     */ import net.minecraft.entity.item.EntityXPOrb;
/*     */ import net.minecraft.entity.passive.EntityAnimal;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAIMate
/*     */   extends EntityAIBase
/*     */ {
/*     */   private final EntityAnimal theAnimal;
/*     */   private final Class<? extends EntityAnimal> field_190857_e;
/*     */   World theWorld;
/*     */   private EntityAnimal targetMate;
/*     */   int spawnBabyDelay;
/*     */   double moveSpeed;
/*     */   
/*     */   public EntityAIMate(EntityAnimal animal, double speedIn) {
/*  31 */     this(animal, speedIn, (Class)animal.getClass());
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityAIMate(EntityAnimal p_i47306_1_, double p_i47306_2_, Class<? extends EntityAnimal> p_i47306_4_) {
/*  36 */     this.theAnimal = p_i47306_1_;
/*  37 */     this.theWorld = p_i47306_1_.world;
/*  38 */     this.field_190857_e = p_i47306_4_;
/*  39 */     this.moveSpeed = p_i47306_2_;
/*  40 */     setMutexBits(3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  48 */     if (!this.theAnimal.isInLove())
/*     */     {
/*  50 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  54 */     this.targetMate = getNearbyMate();
/*  55 */     return (this.targetMate != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  64 */     return (this.targetMate.isEntityAlive() && this.targetMate.isInLove() && this.spawnBabyDelay < 60);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/*  72 */     this.targetMate = null;
/*  73 */     this.spawnBabyDelay = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/*  81 */     this.theAnimal.getLookHelper().setLookPositionWithEntity((Entity)this.targetMate, 10.0F, this.theAnimal.getVerticalFaceSpeed());
/*  82 */     this.theAnimal.getNavigator().tryMoveToEntityLiving((Entity)this.targetMate, this.moveSpeed);
/*  83 */     this.spawnBabyDelay++;
/*     */     
/*  85 */     if (this.spawnBabyDelay >= 60 && this.theAnimal.getDistanceSqToEntity((Entity)this.targetMate) < 9.0D)
/*     */     {
/*  87 */       spawnBaby();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private EntityAnimal getNearbyMate() {
/*  97 */     List<EntityAnimal> list = this.theWorld.getEntitiesWithinAABB(this.field_190857_e, this.theAnimal.getEntityBoundingBox().expandXyz(8.0D));
/*  98 */     double d0 = Double.MAX_VALUE;
/*  99 */     EntityAnimal entityanimal = null;
/*     */     
/* 101 */     for (EntityAnimal entityanimal1 : list) {
/*     */       
/* 103 */       if (this.theAnimal.canMateWith(entityanimal1) && this.theAnimal.getDistanceSqToEntity((Entity)entityanimal1) < d0) {
/*     */         
/* 105 */         entityanimal = entityanimal1;
/* 106 */         d0 = this.theAnimal.getDistanceSqToEntity((Entity)entityanimal1);
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     return entityanimal;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void spawnBaby() {
/* 118 */     EntityAgeable entityageable = this.theAnimal.createChild((EntityAgeable)this.targetMate);
/*     */     
/* 120 */     if (entityageable != null) {
/*     */       
/* 122 */       EntityPlayerMP entityplayermp = this.theAnimal.func_191993_do();
/*     */       
/* 124 */       if (entityplayermp == null && this.targetMate.func_191993_do() != null)
/*     */       {
/* 126 */         entityplayermp = this.targetMate.func_191993_do();
/*     */       }
/*     */       
/* 129 */       if (entityplayermp != null) {
/*     */         
/* 131 */         entityplayermp.addStat(StatList.ANIMALS_BRED);
/* 132 */         CriteriaTriggers.field_192134_n.func_192168_a(entityplayermp, this.theAnimal, this.targetMate, entityageable);
/*     */       } 
/*     */       
/* 135 */       this.theAnimal.setGrowingAge(6000);
/* 136 */       this.targetMate.setGrowingAge(6000);
/* 137 */       this.theAnimal.resetInLove();
/* 138 */       this.targetMate.resetInLove();
/* 139 */       entityageable.setGrowingAge(-24000);
/* 140 */       entityageable.setLocationAndAngles(this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, 0.0F, 0.0F);
/* 141 */       this.theWorld.spawnEntityInWorld((Entity)entityageable);
/* 142 */       Random random = this.theAnimal.getRNG();
/*     */       
/* 144 */       for (int i = 0; i < 7; i++) {
/*     */         
/* 146 */         double d0 = random.nextGaussian() * 0.02D;
/* 147 */         double d1 = random.nextGaussian() * 0.02D;
/* 148 */         double d2 = random.nextGaussian() * 0.02D;
/* 149 */         double d3 = random.nextDouble() * this.theAnimal.width * 2.0D - this.theAnimal.width;
/* 150 */         double d4 = 0.5D + random.nextDouble() * this.theAnimal.height;
/* 151 */         double d5 = random.nextDouble() * this.theAnimal.width * 2.0D - this.theAnimal.width;
/* 152 */         this.theWorld.spawnParticle(EnumParticleTypes.HEART, this.theAnimal.posX + d3, this.theAnimal.posY + d4, this.theAnimal.posZ + d5, d0, d1, d2, new int[0]);
/*     */       } 
/*     */       
/* 155 */       if (this.theWorld.getGameRules().getBoolean("doMobLoot"))
/*     */       {
/* 157 */         this.theWorld.spawnEntityInWorld((Entity)new EntityXPOrb(this.theWorld, this.theAnimal.posX, this.theAnimal.posY, this.theAnimal.posZ, random.nextInt(7) + 1));
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIMate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */