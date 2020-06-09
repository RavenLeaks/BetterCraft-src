/*    */ package net.minecraft.entity.ai;
/*    */ 
/*    */ import net.minecraft.entity.EntityCreature;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class EntityAIMoveTowardsRestriction
/*    */   extends EntityAIBase
/*    */ {
/*    */   private final EntityCreature theEntity;
/*    */   private double movePosX;
/*    */   private double movePosY;
/*    */   private double movePosZ;
/*    */   private final double movementSpeed;
/*    */   
/*    */   public EntityAIMoveTowardsRestriction(EntityCreature creatureIn, double speedIn) {
/* 17 */     this.theEntity = creatureIn;
/* 18 */     this.movementSpeed = speedIn;
/* 19 */     setMutexBits(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean shouldExecute() {
/* 27 */     if (this.theEntity.isWithinHomeDistanceCurrentPosition())
/*    */     {
/* 29 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 33 */     BlockPos blockpos = this.theEntity.getHomePosition();
/* 34 */     Vec3d vec3d = RandomPositionGenerator.findRandomTargetBlockTowards(this.theEntity, 16, 7, new Vec3d(blockpos.getX(), blockpos.getY(), blockpos.getZ()));
/*    */     
/* 36 */     if (vec3d == null)
/*    */     {
/* 38 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 42 */     this.movePosX = vec3d.xCoord;
/* 43 */     this.movePosY = vec3d.yCoord;
/* 44 */     this.movePosZ = vec3d.zCoord;
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean continueExecuting() {
/* 55 */     return !this.theEntity.getNavigator().noPath();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startExecuting() {
/* 63 */     this.theEntity.getNavigator().tryMoveToXYZ(this.movePosX, this.movePosY, this.movePosZ, this.movementSpeed);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\ai\EntityAIMoveTowardsRestriction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */