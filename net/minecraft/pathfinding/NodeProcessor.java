/*    */ package net.minecraft.pathfinding;
/*    */ 
/*    */ import net.minecraft.entity.EntityLiving;
/*    */ import net.minecraft.util.IntHashMap;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public abstract class NodeProcessor
/*    */ {
/*    */   protected IBlockAccess blockaccess;
/*    */   protected EntityLiving entity;
/* 12 */   protected final IntHashMap<PathPoint> pointMap = new IntHashMap();
/*    */   
/*    */   protected int entitySizeX;
/*    */   protected int entitySizeY;
/*    */   protected int entitySizeZ;
/*    */   protected boolean canEnterDoors;
/*    */   protected boolean canBreakDoors;
/*    */   protected boolean canSwim;
/*    */   
/*    */   public void initProcessor(IBlockAccess sourceIn, EntityLiving mob) {
/* 22 */     this.blockaccess = sourceIn;
/* 23 */     this.entity = mob;
/* 24 */     this.pointMap.clearMap();
/* 25 */     this.entitySizeX = MathHelper.floor(mob.width + 1.0F);
/* 26 */     this.entitySizeY = MathHelper.floor(mob.height + 1.0F);
/* 27 */     this.entitySizeZ = MathHelper.floor(mob.width + 1.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void postProcess() {
/* 37 */     this.blockaccess = null;
/* 38 */     this.entity = null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected PathPoint openPoint(int x, int y, int z) {
/* 46 */     int i = PathPoint.makeHash(x, y, z);
/* 47 */     PathPoint pathpoint = (PathPoint)this.pointMap.lookup(i);
/*    */     
/* 49 */     if (pathpoint == null) {
/*    */       
/* 51 */       pathpoint = new PathPoint(x, y, z);
/* 52 */       this.pointMap.addKey(i, pathpoint);
/*    */     } 
/*    */     
/* 55 */     return pathpoint;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract PathPoint getStart();
/*    */ 
/*    */   
/*    */   public abstract PathPoint getPathPointToCoords(double paramDouble1, double paramDouble2, double paramDouble3);
/*    */ 
/*    */   
/*    */   public abstract int findPathOptions(PathPoint[] paramArrayOfPathPoint, PathPoint paramPathPoint1, PathPoint paramPathPoint2, float paramFloat);
/*    */ 
/*    */   
/*    */   public abstract PathNodeType getPathNodeType(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3, EntityLiving paramEntityLiving, int paramInt4, int paramInt5, int paramInt6, boolean paramBoolean1, boolean paramBoolean2);
/*    */   
/*    */   public abstract PathNodeType getPathNodeType(IBlockAccess paramIBlockAccess, int paramInt1, int paramInt2, int paramInt3);
/*    */   
/*    */   public void setCanEnterDoors(boolean canEnterDoorsIn) {
/* 73 */     this.canEnterDoors = canEnterDoorsIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCanBreakDoors(boolean canBreakDoorsIn) {
/* 78 */     this.canBreakDoors = canBreakDoorsIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCanSwim(boolean canSwimIn) {
/* 83 */     this.canSwim = canSwimIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getCanEnterDoors() {
/* 88 */     return this.canEnterDoors;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getCanBreakDoors() {
/* 93 */     return this.canBreakDoors;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getCanSwim() {
/* 98 */     return this.canSwim;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\pathfinding\NodeProcessor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */