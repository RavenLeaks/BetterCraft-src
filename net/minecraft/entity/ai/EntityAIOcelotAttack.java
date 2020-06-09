/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class EntityAIOcelotAttack
/*    */   extends EntityAIBase {
/*    */   World theWorld;
/*    */   EntityLiving theEntity;
/*    */   EntityLivingBase theVictim;
/*    */   int attackCountdown;
/*    */   
/*    */   public EntityAIOcelotAttack(EntityLiving theEntityIn) {
/* 16 */     this.theEntity = theEntityIn;
/* 17 */     this.theWorld = theEntityIn.world;
/* 18 */     setMutexBits(3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 26 */     EntityLivingBase entitylivingbase = this.theEntity.getAttackTarget();
/*    */     
/* 28 */     if (entitylivingbase == null)
/*    */     {
/* 30 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 34 */     this.theVictim = entitylivingbase;
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 44 */     if (!this.theVictim.isEntityAlive())
/*    */     {
/* 46 */       return false;
/*    */     }
/* 48 */     if (this.theEntity.getDistanceSqToEntity((Entity)this.theVictim) > 225.0D)
/*    */     {
/* 50 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 54 */     return !(this.theEntity.getNavigator().noPath() && !shouldExecute());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 63 */     this.theVictim = null;
/* 64 */     this.theEntity.getNavigator().clearPathEntity();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 72 */     this.theEntity.getLookHelper().setLookPositionWithEntity((Entity)this.theVictim, 30.0F, 30.0F);
/* 73 */     double d0 = (this.theEntity.width * 2.0F * this.theEntity.width * 2.0F);
/* 74 */     double d1 = this.theEntity.getDistanceSq(this.theVictim.posX, (this.theVictim.getEntityBoundingBox()).minY, this.theVictim.posZ);
/* 75 */     double d2 = 0.8D;
/*    */     
/* 77 */     if (d1 > d0 && d1 < 16.0D) {
/*    */       
/* 79 */       d2 = 1.33D;
/*    */     }
/* 81 */     else if (d1 < 225.0D) {
/*    */       
/* 83 */       d2 = 0.6D;
/*    */     } 
/*    */     
/* 86 */     this.theEntity.getNavigator().tryMoveToEntityLiving((Entity)this.theVictim, d2);
/* 87 */     this.attackCountdown = Math.max(this.attackCountdown - 1, 0);
/*    */     
/* 89 */     if (d1 <= d0)
/*    */     {
/* 91 */       if (this.attackCountdown <= 0) {
/*    */         
/* 93 */         this.attackCountdown = 20;
/* 94 */         this.theEntity.attackEntityAsMob((Entity)this.theVictim);
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIOcelotAttack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */