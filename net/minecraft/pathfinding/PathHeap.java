/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ 
/*     */ public class PathHeap
/*     */ {
/*   6 */   private PathPoint[] pathPoints = new PathPoint[128];
/*     */ 
/*     */ 
/*     */   
/*     */   private int count;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint addPoint(PathPoint point) {
/*  16 */     if (point.index >= 0)
/*     */     {
/*  18 */       throw new IllegalStateException("OW KNOWS!");
/*     */     }
/*     */ 
/*     */     
/*  22 */     if (this.count == this.pathPoints.length) {
/*     */       
/*  24 */       PathPoint[] apathpoint = new PathPoint[this.count << 1];
/*  25 */       System.arraycopy(this.pathPoints, 0, apathpoint, 0, this.count);
/*  26 */       this.pathPoints = apathpoint;
/*     */     } 
/*     */     
/*  29 */     this.pathPoints[this.count] = point;
/*  30 */     point.index = this.count;
/*  31 */     sortBack(this.count++);
/*  32 */     return point;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearPath() {
/*  41 */     this.count = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PathPoint dequeue() {
/*  49 */     PathPoint pathpoint = this.pathPoints[0];
/*  50 */     this.pathPoints[0] = this.pathPoints[--this.count];
/*  51 */     this.pathPoints[this.count] = null;
/*     */     
/*  53 */     if (this.count > 0)
/*     */     {
/*  55 */       sortForward(0);
/*     */     }
/*     */     
/*  58 */     pathpoint.index = -1;
/*  59 */     return pathpoint;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void changeDistance(PathPoint point, float distance) {
/*  67 */     float f = point.distanceToTarget;
/*  68 */     point.distanceToTarget = distance;
/*     */     
/*  70 */     if (distance < f) {
/*     */       
/*  72 */       sortBack(point.index);
/*     */     }
/*     */     else {
/*     */       
/*  76 */       sortForward(point.index);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sortBack(int index) {
/*  85 */     PathPoint pathpoint = this.pathPoints[index];
/*     */ 
/*     */     
/*  88 */     for (float f = pathpoint.distanceToTarget; index > 0; index = i) {
/*     */       
/*  90 */       int i = index - 1 >> 1;
/*  91 */       PathPoint pathpoint1 = this.pathPoints[i];
/*     */       
/*  93 */       if (f >= pathpoint1.distanceToTarget) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/*  98 */       this.pathPoints[index] = pathpoint1;
/*  99 */       pathpoint1.index = index;
/*     */     } 
/*     */     
/* 102 */     this.pathPoints[index] = pathpoint;
/* 103 */     pathpoint.index = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sortForward(int index) {
/* 111 */     PathPoint pathpoint = this.pathPoints[index];
/* 112 */     float f = pathpoint.distanceToTarget;
/*     */     while (true) {
/*     */       PathPoint pathpoint2;
/*     */       float f2;
/* 116 */       int i = 1 + (index << 1);
/* 117 */       int j = i + 1;
/*     */       
/* 119 */       if (i >= this.count) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 124 */       PathPoint pathpoint1 = this.pathPoints[i];
/* 125 */       float f1 = pathpoint1.distanceToTarget;
/*     */ 
/*     */ 
/*     */       
/* 129 */       if (j >= this.count) {
/*     */         
/* 131 */         pathpoint2 = null;
/* 132 */         f2 = Float.POSITIVE_INFINITY;
/*     */       }
/*     */       else {
/*     */         
/* 136 */         pathpoint2 = this.pathPoints[j];
/* 137 */         f2 = pathpoint2.distanceToTarget;
/*     */       } 
/*     */       
/* 140 */       if (f1 < f2) {
/*     */         
/* 142 */         if (f1 >= f) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 147 */         this.pathPoints[index] = pathpoint1;
/* 148 */         pathpoint1.index = index;
/* 149 */         index = i;
/*     */         
/*     */         continue;
/*     */       } 
/* 153 */       if (f2 >= f) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 158 */       this.pathPoints[index] = pathpoint2;
/* 159 */       pathpoint2.index = index;
/* 160 */       index = j;
/*     */     } 
/*     */ 
/*     */     
/* 164 */     this.pathPoints[index] = pathpoint;
/* 165 */     pathpoint.index = index;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isPathEmpty() {
/* 173 */     return (this.count == 0);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\pathfinding\PathHeap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */