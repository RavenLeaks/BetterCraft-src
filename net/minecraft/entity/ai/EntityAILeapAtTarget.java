/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityAILeapAtTarget
/*    */   extends EntityAIBase
/*    */ {
/*    */   EntityLiving leaper;
/*    */   EntityLivingBase leapTarget;
/*    */   float leapMotionY;
/*    */   
/*    */   public EntityAILeapAtTarget(EntityLiving leapingEntity, float leapMotionYIn) {
/* 20 */     this.leaper = leapingEntity;
/* 21 */     this.leapMotionY = leapMotionYIn;
/* 22 */     setMutexBits(5);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 30 */     this.leapTarget = this.leaper.getAttackTarget();
/*    */     
/* 32 */     if (this.leapTarget == null)
/*    */     {
/* 34 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 38 */     double d0 = this.leaper.getDistanceSqToEntity((Entity)this.leapTarget);
/*    */     
/* 40 */     if (d0 >= 4.0D && d0 <= 16.0D) {
/*    */       
/* 42 */       if (!this.leaper.onGround)
/*    */       {
/* 44 */         return false;
/*    */       }
/*    */ 
/*    */       
/* 48 */       return (this.leaper.getRNG().nextInt(5) == 0);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 53 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 63 */     return !this.leaper.onGround;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 71 */     double d0 = this.leapTarget.posX - this.leaper.posX;
/* 72 */     double d1 = this.leapTarget.posZ - this.leaper.posZ;
/* 73 */     float f = MathHelper.sqrt(d0 * d0 + d1 * d1);
/*    */     
/* 75 */     if (f >= 1.0E-4D) {
/*    */       
/* 77 */       this.leaper.motionX += d0 / f * 0.5D * 0.800000011920929D + this.leaper.motionX * 0.20000000298023224D;
/* 78 */       this.leaper.motionZ += d1 / f * 0.5D * 0.800000011920929D + this.leaper.motionZ * 0.20000000298023224D;
/*    */     } 
/*    */     
/* 81 */     this.leaper.motionY = this.leapMotionY;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAILeapAtTarget.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */