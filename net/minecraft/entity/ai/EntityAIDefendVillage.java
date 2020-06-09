/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.entity.monster.EntityIronGolem;
/*    */ import net.minecraft.village.Village;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityAIDefendVillage
/*    */   extends EntityAITarget
/*    */ {
/*    */   EntityIronGolem irongolem;
/*    */   EntityLivingBase villageAgressorTarget;
/*    */   
/*    */   public EntityAIDefendVillage(EntityIronGolem ironGolemIn) {
/* 19 */     super((EntityCreature)ironGolemIn, false, true);
/* 20 */     this.irongolem = ironGolemIn;
/* 21 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 29 */     Village village = this.irongolem.getVillage();
/*    */     
/* 31 */     if (village == null)
/*    */     {
/* 33 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 37 */     this.villageAgressorTarget = village.findNearestVillageAggressor((EntityLivingBase)this.irongolem);
/*    */     
/* 39 */     if (this.villageAgressorTarget instanceof net.minecraft.entity.monster.EntityCreeper)
/*    */     {
/* 41 */       return false;
/*    */     }
/* 43 */     if (isSuitableTarget(this.villageAgressorTarget, false))
/*    */     {
/* 45 */       return true;
/*    */     }
/* 47 */     if (this.taskOwner.getRNG().nextInt(20) == 0) {
/*    */       
/* 49 */       this.villageAgressorTarget = (EntityLivingBase)village.getNearestTargetPlayer((EntityLivingBase)this.irongolem);
/* 50 */       return isSuitableTarget(this.villageAgressorTarget, false);
/*    */     } 
/*    */ 
/*    */     
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 64 */     this.irongolem.setAttackTarget(this.villageAgressorTarget);
/* 65 */     super.startExecuting();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIDefendVillage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */