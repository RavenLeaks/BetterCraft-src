/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityTameable;
/*    */ 
/*    */ public class EntityAIOwnerHurtByTarget
/*    */   extends EntityAITarget {
/*    */   EntityTameable theDefendingTameable;
/*    */   EntityLivingBase theOwnerAttacker;
/*    */   private int timestamp;
/*    */   
/*    */   public EntityAIOwnerHurtByTarget(EntityTameable theDefendingTameableIn) {
/* 14 */     super((EntityCreature)theDefendingTameableIn, false);
/* 15 */     this.theDefendingTameable = theDefendingTameableIn;
/* 16 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 24 */     if (!this.theDefendingTameable.isTamed())
/*    */     {
/* 26 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 30 */     EntityLivingBase entitylivingbase = this.theDefendingTameable.getOwner();
/*    */     
/* 32 */     if (entitylivingbase == null)
/*    */     {
/* 34 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 38 */     this.theOwnerAttacker = entitylivingbase.getAITarget();
/* 39 */     int i = entitylivingbase.getRevengeTimer();
/* 40 */     return (i != this.timestamp && isSuitableTarget(this.theOwnerAttacker, false) && this.theDefendingTameable.shouldAttackEntity(this.theOwnerAttacker, entitylivingbase));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 50 */     this.taskOwner.setAttackTarget(this.theOwnerAttacker);
/* 51 */     EntityLivingBase entitylivingbase = this.theDefendingTameable.getOwner();
/*    */     
/* 53 */     if (entitylivingbase != null)
/*    */     {
/* 55 */       this.timestamp = entitylivingbase.getRevengeTimer();
/*    */     }
/*    */     
/* 58 */     super.startExecuting();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIOwnerHurtByTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */