/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.pathfinding.PathNavigateFlying;
/*    */ import net.minecraft.pathfinding.PathNavigateGround;
/*    */ 
/*    */ public class EntityAISwimming
/*    */   extends EntityAIBase
/*    */ {
/*    */   private final EntityLiving theEntity;
/*    */   
/*    */   public EntityAISwimming(EntityLiving entitylivingIn) {
/* 13 */     this.theEntity = entitylivingIn;
/* 14 */     setMutexBits(4);
/*    */     
/* 16 */     if (entitylivingIn.getNavigator() instanceof PathNavigateGround) {
/*    */       
/* 18 */       ((PathNavigateGround)entitylivingIn.getNavigator()).setCanSwim(true);
/*    */     }
/* 20 */     else if (entitylivingIn.getNavigator() instanceof PathNavigateFlying) {
/*    */       
/* 22 */       ((PathNavigateFlying)entitylivingIn.getNavigator()).func_192877_c(true);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 31 */     return !(!this.theEntity.isInWater() && !this.theEntity.isInLava());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 39 */     if (this.theEntity.getRNG().nextFloat() < 0.8F)
/*    */     {
/* 41 */       this.theEntity.getJumpHelper().setJumping();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAISwimming.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */