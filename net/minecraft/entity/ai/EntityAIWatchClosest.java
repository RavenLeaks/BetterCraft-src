/*     */ package net.minecraft.entity.ai;
/*     */ 
/*     */ import com.google.common.base.Predicates;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.EntitySelectors;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityAIWatchClosest
/*     */   extends EntityAIBase
/*     */ {
/*     */   protected EntityLiving theWatcher;
/*     */   protected Entity closestEntity;
/*     */   protected float maxDistanceForPlayer;
/*     */   private int lookTime;
/*     */   private final float chance;
/*     */   protected Class<? extends Entity> watchedClass;
/*     */   
/*     */   public EntityAIWatchClosest(EntityLiving entitylivingIn, Class<? extends Entity> watchTargetClass, float maxDistance) {
/*  24 */     this.theWatcher = entitylivingIn;
/*  25 */     this.watchedClass = watchTargetClass;
/*  26 */     this.maxDistanceForPlayer = maxDistance;
/*  27 */     this.chance = 0.02F;
/*  28 */     setMutexBits(2);
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityAIWatchClosest(EntityLiving entitylivingIn, Class<? extends Entity> watchTargetClass, float maxDistance, float chanceIn) {
/*  33 */     this.theWatcher = entitylivingIn;
/*  34 */     this.watchedClass = watchTargetClass;
/*  35 */     this.maxDistanceForPlayer = maxDistance;
/*  36 */     this.chance = chanceIn;
/*  37 */     setMutexBits(2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldExecute() {
/*  45 */     if (this.theWatcher.getRNG().nextFloat() >= this.chance)
/*     */     {
/*  47 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  51 */     if (this.theWatcher.getAttackTarget() != null)
/*     */     {
/*  53 */       this.closestEntity = (Entity)this.theWatcher.getAttackTarget();
/*     */     }
/*     */     
/*  56 */     if (this.watchedClass == EntityPlayer.class) {
/*     */       
/*  58 */       this.closestEntity = (Entity)this.theWatcher.world.func_190525_a(this.theWatcher.posX, this.theWatcher.posY, this.theWatcher.posZ, this.maxDistanceForPlayer, Predicates.and(EntitySelectors.NOT_SPECTATING, EntitySelectors.func_191324_b((Entity)this.theWatcher)));
/*     */     }
/*     */     else {
/*     */       
/*  62 */       this.closestEntity = this.theWatcher.world.findNearestEntityWithinAABB(this.watchedClass, this.theWatcher.getEntityBoundingBox().expand(this.maxDistanceForPlayer, 3.0D, this.maxDistanceForPlayer), (Entity)this.theWatcher);
/*     */     } 
/*     */     
/*  65 */     return (this.closestEntity != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean continueExecuting() {
/*  74 */     if (!this.closestEntity.isEntityAlive())
/*     */     {
/*  76 */       return false;
/*     */     }
/*  78 */     if (this.theWatcher.getDistanceSqToEntity(this.closestEntity) > (this.maxDistanceForPlayer * this.maxDistanceForPlayer))
/*     */     {
/*  80 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  84 */     return (this.lookTime > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startExecuting() {
/*  93 */     this.lookTime = 40 + this.theWatcher.getRNG().nextInt(40);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void resetTask() {
/* 101 */     this.closestEntity = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTask() {
/* 109 */     this.theWatcher.getLookHelper().setLookPosition(this.closestEntity.posX, this.closestEntity.posY + this.closestEntity.getEyeHeight(), this.closestEntity.posZ, this.theWatcher.getHorizontalFaceSpeed(), this.theWatcher.getVerticalFaceSpeed());
/* 110 */     this.lookTime--;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIWatchClosest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */