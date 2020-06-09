/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.monster.EntityZombie;
/*    */ 
/*    */ public class EntityAIZombieAttack
/*    */   extends EntityAIAttackMelee {
/*    */   private final EntityZombie zombie;
/*    */   private int raiseArmTicks;
/*    */   
/*    */   public EntityAIZombieAttack(EntityZombie zombieIn, double speedIn, boolean longMemoryIn) {
/* 12 */     super((EntityCreature)zombieIn, speedIn, longMemoryIn);
/* 13 */     this.zombie = zombieIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 21 */     super.startExecuting();
/* 22 */     this.raiseArmTicks = 0;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 30 */     super.resetTask();
/* 31 */     this.zombie.setArmsRaised(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 39 */     super.updateTask();
/* 40 */     this.raiseArmTicks++;
/*    */     
/* 42 */     if (this.raiseArmTicks >= 5 && this.attackTick < 10) {
/*    */       
/* 44 */       this.zombie.setArmsRaised(true);
/*    */     }
/*    */     else {
/*    */       
/* 48 */       this.zombie.setArmsRaised(false);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIZombieAttack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */