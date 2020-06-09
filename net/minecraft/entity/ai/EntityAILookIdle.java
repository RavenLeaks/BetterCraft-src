/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityAILookIdle
/*    */   extends EntityAIBase
/*    */ {
/*    */   private final EntityLiving idleEntity;
/*    */   private double lookX;
/*    */   private double lookZ;
/*    */   private int idleTime;
/*    */   
/*    */   public EntityAILookIdle(EntityLiving entitylivingIn) {
/* 23 */     this.idleEntity = entitylivingIn;
/* 24 */     setMutexBits(3);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 32 */     return (this.idleEntity.getRNG().nextFloat() < 0.02F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 40 */     return (this.idleTime >= 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 48 */     double d0 = 6.283185307179586D * this.idleEntity.getRNG().nextDouble();
/* 49 */     this.lookX = Math.cos(d0);
/* 50 */     this.lookZ = Math.sin(d0);
/* 51 */     this.idleTime = 20 + this.idleEntity.getRNG().nextInt(20);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 59 */     this.idleTime--;
/* 60 */     this.idleEntity.getLookHelper().setLookPosition(this.idleEntity.posX + this.lookX, this.idleEntity.posY + this.idleEntity.getEyeHeight(), this.idleEntity.posZ + this.lookZ, this.idleEntity.getHorizontalFaceSpeed(), this.idleEntity.getVerticalFaceSpeed());
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAILookIdle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */