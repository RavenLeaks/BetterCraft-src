/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.pathfinding.PathNavigateGround;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.village.Village;
/*    */ import net.minecraft.village.VillageDoorInfo;
/*    */ 
/*    */ public class EntityAIRestrictOpenDoor
/*    */   extends EntityAIBase {
/*    */   private final EntityCreature entityObj;
/*    */   private VillageDoorInfo frontDoor;
/*    */   
/*    */   public EntityAIRestrictOpenDoor(EntityCreature creatureIn) {
/* 16 */     this.entityObj = creatureIn;
/*    */     
/* 18 */     if (!(creatureIn.getNavigator() instanceof PathNavigateGround))
/*    */     {
/* 20 */       throw new IllegalArgumentException("Unsupported mob type for RestrictOpenDoorGoal");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 29 */     if (this.entityObj.world.isDaytime())
/*    */     {
/* 31 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 35 */     BlockPos blockpos = new BlockPos((Entity)this.entityObj);
/* 36 */     Village village = this.entityObj.world.getVillageCollection().getNearestVillage(blockpos, 16);
/*    */     
/* 38 */     if (village == null)
/*    */     {
/* 40 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 44 */     this.frontDoor = village.getNearestDoor(blockpos);
/*    */     
/* 46 */     if (this.frontDoor == null)
/*    */     {
/* 48 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 52 */     return (this.frontDoor.getDistanceToInsideBlockSq(blockpos) < 2.25D);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 63 */     if (this.entityObj.world.isDaytime())
/*    */     {
/* 65 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 69 */     return (!this.frontDoor.getIsDetachedFromVillageFlag() && this.frontDoor.isInsideSide(new BlockPos((Entity)this.entityObj)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 78 */     ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(false);
/* 79 */     ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetTask() {
/* 87 */     ((PathNavigateGround)this.entityObj.getNavigator()).setBreakDoors(true);
/* 88 */     ((PathNavigateGround)this.entityObj.getNavigator()).setEnterDoors(true);
/* 89 */     this.frontDoor = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateTask() {
/* 97 */     this.frontDoor.incrementDoorOpeningRestrictionCounter();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIRestrictOpenDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */