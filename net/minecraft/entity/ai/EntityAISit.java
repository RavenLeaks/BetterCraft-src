/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.passive.EntityTameable;
/*    */ 
/*    */ 
/*    */ public class EntityAISit
/*    */   extends EntityAIBase
/*    */ {
/*    */   private final EntityTameable theEntity;
/*    */   private boolean isSitting;
/*    */   
/*    */   public EntityAISit(EntityTameable entityIn) {
/* 15 */     this.theEntity = entityIn;
/* 16 */     setMutexBits(5);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 24 */     if (!this.theEntity.isTamed())
/*    */     {
/* 26 */       return false;
/*    */     }
/* 28 */     if (this.theEntity.isInWater())
/*    */     {
/* 30 */       return false;
/*    */     }
/* 32 */     if (!this.theEntity.onGround)
/*    */     {
/* 34 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 38 */     EntityLivingBase entitylivingbase = this.theEntity.getOwner();
/*    */     
/* 40 */     if (entitylivingbase == null)
/*    */     {
/* 42 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 46 */     return (this.theEntity.getDistanceSqToEntity((Entity)entitylivingbase) < 144.0D && entitylivingbase.getAITarget() != null) ? false : this.isSitting;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 56 */     this.theEntity.getNavigator().clearPathEntity();
/* 57 */     this.theEntity.setSitting(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 65 */     this.theEntity.setSitting(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSitting(boolean sitting) {
/* 73 */     this.isSitting = sitting;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAISit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */