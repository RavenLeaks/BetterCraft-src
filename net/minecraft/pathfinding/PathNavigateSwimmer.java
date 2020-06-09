/*    */ package net.minecraft.pathfinding;
/*    */ 
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.RayTraceResult;
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class PathNavigateSwimmer
/*    */   extends PathNavigate {
/*    */   public PathNavigateSwimmer(EntityLiving entitylivingIn, World worldIn) {
/* 13 */     super(entitylivingIn, worldIn);
/*    */   }
/*    */ 
/*    */   
/*    */   protected PathFinder getPathFinder() {
/* 18 */     return new PathFinder(new SwimNodeProcessor());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean canNavigate() {
/* 26 */     return isInLiquid();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Vec3d getEntityPosition() {
/* 31 */     return new Vec3d(this.theEntity.posX, this.theEntity.posY + this.theEntity.height * 0.5D, this.theEntity.posZ);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void pathFollow() {
/* 36 */     Vec3d vec3d = getEntityPosition();
/* 37 */     float f = this.theEntity.width * this.theEntity.width;
/* 38 */     int i = 6;
/*    */     
/* 40 */     if (vec3d.squareDistanceTo(this.currentPath.getVectorFromIndex((Entity)this.theEntity, this.currentPath.getCurrentPathIndex())) < f)
/*    */     {
/* 42 */       this.currentPath.incrementPathIndex();
/*    */     }
/*    */     
/* 45 */     for (int j = Math.min(this.currentPath.getCurrentPathIndex() + 6, this.currentPath.getCurrentPathLength() - 1); j > this.currentPath.getCurrentPathIndex(); j--) {
/*    */       
/* 47 */       Vec3d vec3d1 = this.currentPath.getVectorFromIndex((Entity)this.theEntity, j);
/*    */       
/* 49 */       if (vec3d1.squareDistanceTo(vec3d) <= 36.0D && isDirectPathBetweenPoints(vec3d, vec3d1, 0, 0, 0)) {
/*    */         
/* 51 */         this.currentPath.setCurrentPathIndex(j);
/*    */         
/*    */         break;
/*    */       } 
/*    */     } 
/* 56 */     checkForStuck(vec3d);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ) {
/* 64 */     RayTraceResult raytraceresult = this.worldObj.rayTraceBlocks(posVec31, new Vec3d(posVec32.xCoord, posVec32.yCoord + this.theEntity.height * 0.5D, posVec32.zCoord), false, true, false);
/* 65 */     return !(raytraceresult != null && raytraceresult.typeOfHit != RayTraceResult.Type.MISS);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canEntityStandOnPos(BlockPos pos) {
/* 70 */     return !this.worldObj.getBlockState(pos).isFullBlock();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\pathfinding\PathNavigateSwimmer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */