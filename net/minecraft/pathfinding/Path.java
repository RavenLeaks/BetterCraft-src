/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ 
/*     */ 
/*     */ public class Path
/*     */ {
/*     */   private final PathPoint[] points;
/*  12 */   private PathPoint[] openSet = new PathPoint[0];
/*  13 */   private PathPoint[] closedSet = new PathPoint[0];
/*     */ 
/*     */   
/*     */   private PathPoint target;
/*     */   
/*     */   private int currentPathIndex;
/*     */   
/*     */   private int pathLength;
/*     */ 
/*     */   
/*     */   public Path(PathPoint[] pathpoints) {
/*  24 */     this.points = pathpoints;
/*  25 */     this.pathLength = pathpoints.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementPathIndex() {
/*  33 */     this.currentPathIndex++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/*  41 */     return (this.currentPathIndex >= this.pathLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public PathPoint getFinalPathPoint() {
/*  51 */     return (this.pathLength > 0) ? this.points[this.pathLength - 1] : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint getPathPointFromIndex(int index) {
/*  59 */     return this.points[index];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPoint(int index, PathPoint point) {
/*  64 */     this.points[index] = point;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentPathLength() {
/*  69 */     return this.pathLength;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentPathLength(int length) {
/*  74 */     this.pathLength = length;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentPathIndex() {
/*  79 */     return this.currentPathIndex;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCurrentPathIndex(int currentPathIndexIn) {
/*  84 */     this.currentPathIndex = currentPathIndexIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3d getVectorFromIndex(Entity entityIn, int index) {
/*  92 */     double d0 = (this.points[index]).xCoord + (int)(entityIn.width + 1.0F) * 0.5D;
/*  93 */     double d1 = (this.points[index]).yCoord;
/*  94 */     double d2 = (this.points[index]).zCoord + (int)(entityIn.width + 1.0F) * 0.5D;
/*  95 */     return new Vec3d(d0, d1, d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3d getPosition(Entity entityIn) {
/* 103 */     return getVectorFromIndex(entityIn, this.currentPathIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d getCurrentPos() {
/* 108 */     PathPoint pathpoint = this.points[this.currentPathIndex];
/* 109 */     return new Vec3d(pathpoint.xCoord, pathpoint.yCoord, pathpoint.zCoord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSamePath(Path pathentityIn) {
/* 117 */     if (pathentityIn == null)
/*     */     {
/* 119 */       return false;
/*     */     }
/* 121 */     if (pathentityIn.points.length != this.points.length)
/*     */     {
/* 123 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 127 */     for (int i = 0; i < this.points.length; i++) {
/*     */       
/* 129 */       if ((this.points[i]).xCoord != (pathentityIn.points[i]).xCoord || (this.points[i]).yCoord != (pathentityIn.points[i]).yCoord || (this.points[i]).zCoord != (pathentityIn.points[i]).zCoord)
/*     */       {
/* 131 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 135 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint[] getOpenSet() {
/* 141 */     return this.openSet;
/*     */   }
/*     */ 
/*     */   
/*     */   public PathPoint[] getClosedSet() {
/* 146 */     return this.closedSet;
/*     */   }
/*     */ 
/*     */   
/*     */   public PathPoint getTarget() {
/* 151 */     return this.target;
/*     */   }
/*     */ 
/*     */   
/*     */   public static Path read(PacketBuffer buf) {
/* 156 */     int i = buf.readInt();
/* 157 */     PathPoint pathpoint = PathPoint.createFromBuffer(buf);
/* 158 */     PathPoint[] apathpoint = new PathPoint[buf.readInt()];
/*     */     
/* 160 */     for (int j = 0; j < apathpoint.length; j++)
/*     */     {
/* 162 */       apathpoint[j] = PathPoint.createFromBuffer(buf);
/*     */     }
/*     */     
/* 165 */     PathPoint[] apathpoint1 = new PathPoint[buf.readInt()];
/*     */     
/* 167 */     for (int k = 0; k < apathpoint1.length; k++)
/*     */     {
/* 169 */       apathpoint1[k] = PathPoint.createFromBuffer(buf);
/*     */     }
/*     */     
/* 172 */     PathPoint[] apathpoint2 = new PathPoint[buf.readInt()];
/*     */     
/* 174 */     for (int l = 0; l < apathpoint2.length; l++)
/*     */     {
/* 176 */       apathpoint2[l] = PathPoint.createFromBuffer(buf);
/*     */     }
/*     */     
/* 179 */     Path path = new Path(apathpoint);
/* 180 */     path.openSet = apathpoint1;
/* 181 */     path.closedSet = apathpoint2;
/* 182 */     path.target = pathpoint;
/* 183 */     path.currentPathIndex = i;
/* 184 */     return path;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\pathfinding\Path.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */