/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.passive.EntityVillager;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ 
/*    */ public class EntityAITradePlayer
/*    */   extends EntityAIBase {
/*    */   private final EntityVillager villager;
/*    */   
/*    */   public EntityAITradePlayer(EntityVillager villagerIn) {
/* 12 */     this.villager = villagerIn;
/* 13 */     setMutexBits(5);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 21 */     if (!this.villager.isEntityAlive())
/*    */     {
/* 23 */       return false;
/*    */     }
/* 25 */     if (this.villager.isInWater())
/*    */     {
/* 27 */       return false;
/*    */     }
/* 29 */     if (!this.villager.onGround)
/*    */     {
/* 31 */       return false;
/*    */     }
/* 33 */     if (this.villager.velocityChanged)
/*    */     {
/* 35 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 39 */     EntityPlayer entityplayer = this.villager.getCustomer();
/*    */     
/* 41 */     if (entityplayer == null)
/*    */     {
/* 43 */       return false;
/*    */     }
/* 45 */     if (this.villager.getDistanceSqToEntity((Entity)entityplayer) > 16.0D)
/*    */     {
/* 47 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 51 */     return (entityplayer.openContainer != null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 61 */     this.villager.getNavigator().clearPathEntity();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 69 */     this.villager.setCustomer(null);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAITradePlayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */