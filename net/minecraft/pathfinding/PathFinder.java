/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ 
/*     */ 
/*     */ public class PathFinder
/*     */ {
/*  14 */   private final PathHeap path = new PathHeap();
/*  15 */   private final Set<PathPoint> closedSet = Sets.newHashSet();
/*     */ 
/*     */   
/*  18 */   private final PathPoint[] pathOptions = new PathPoint[32];
/*     */   
/*     */   private final NodeProcessor nodeProcessor;
/*     */   
/*     */   public PathFinder(NodeProcessor processor) {
/*  23 */     this.nodeProcessor = processor;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Path findPath(IBlockAccess worldIn, EntityLiving p_186333_2_, Entity p_186333_3_, float p_186333_4_) {
/*  29 */     return findPath(worldIn, p_186333_2_, p_186333_3_.posX, (p_186333_3_.getEntityBoundingBox()).minY, p_186333_3_.posZ, p_186333_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Path findPath(IBlockAccess worldIn, EntityLiving p_186336_2_, BlockPos p_186336_3_, float p_186336_4_) {
/*  35 */     return findPath(worldIn, p_186336_2_, (p_186336_3_.getX() + 0.5F), (p_186336_3_.getY() + 0.5F), (p_186336_3_.getZ() + 0.5F), p_186336_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Path findPath(IBlockAccess worldIn, EntityLiving p_186334_2_, double p_186334_3_, double p_186334_5_, double p_186334_7_, float p_186334_9_) {
/*  41 */     this.path.clearPath();
/*  42 */     this.nodeProcessor.initProcessor(worldIn, p_186334_2_);
/*  43 */     PathPoint pathpoint = this.nodeProcessor.getStart();
/*  44 */     PathPoint pathpoint1 = this.nodeProcessor.getPathPointToCoords(p_186334_3_, p_186334_5_, p_186334_7_);
/*  45 */     Path path = findPath(pathpoint, pathpoint1, p_186334_9_);
/*  46 */     this.nodeProcessor.postProcess();
/*  47 */     return path;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Path findPath(PathPoint p_186335_1_, PathPoint p_186335_2_, float p_186335_3_) {
/*  53 */     p_186335_1_.totalPathDistance = 0.0F;
/*  54 */     p_186335_1_.distanceToNext = p_186335_1_.distanceManhattan(p_186335_2_);
/*  55 */     p_186335_1_.distanceToTarget = p_186335_1_.distanceToNext;
/*  56 */     this.path.clearPath();
/*  57 */     this.closedSet.clear();
/*  58 */     this.path.addPoint(p_186335_1_);
/*  59 */     PathPoint pathpoint = p_186335_1_;
/*  60 */     int i = 0;
/*     */     
/*  62 */     while (!this.path.isPathEmpty()) {
/*     */       
/*  64 */       i++;
/*     */       
/*  66 */       if (i >= 200) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/*  71 */       PathPoint pathpoint1 = this.path.dequeue();
/*     */       
/*  73 */       if (pathpoint1.equals(p_186335_2_)) {
/*     */         
/*  75 */         pathpoint = p_186335_2_;
/*     */         
/*     */         break;
/*     */       } 
/*  79 */       if (pathpoint1.distanceManhattan(p_186335_2_) < pathpoint.distanceManhattan(p_186335_2_))
/*     */       {
/*  81 */         pathpoint = pathpoint1;
/*     */       }
/*     */       
/*  84 */       pathpoint1.visited = true;
/*  85 */       int j = this.nodeProcessor.findPathOptions(this.pathOptions, pathpoint1, p_186335_2_, p_186335_3_);
/*     */       
/*  87 */       for (int k = 0; k < j; k++) {
/*     */         
/*  89 */         PathPoint pathpoint2 = this.pathOptions[k];
/*  90 */         float f = pathpoint1.distanceManhattan(pathpoint2);
/*  91 */         pathpoint1.distanceFromOrigin += f;
/*  92 */         pathpoint2.cost = f + pathpoint2.costMalus;
/*  93 */         float f1 = pathpoint1.totalPathDistance + pathpoint2.cost;
/*     */         
/*  95 */         if (pathpoint2.distanceFromOrigin < p_186335_3_ && (!pathpoint2.isAssigned() || f1 < pathpoint2.totalPathDistance)) {
/*     */           
/*  97 */           pathpoint2.previous = pathpoint1;
/*  98 */           pathpoint2.totalPathDistance = f1;
/*  99 */           pathpoint2.distanceToNext = pathpoint2.distanceManhattan(p_186335_2_) + pathpoint2.costMalus;
/*     */           
/* 101 */           if (pathpoint2.isAssigned()) {
/*     */             
/* 103 */             this.path.changeDistance(pathpoint2, pathpoint2.totalPathDistance + pathpoint2.distanceToNext);
/*     */           }
/*     */           else {
/*     */             
/* 107 */             pathpoint2.distanceToTarget = pathpoint2.totalPathDistance + pathpoint2.distanceToNext;
/* 108 */             this.path.addPoint(pathpoint2);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 114 */     if (pathpoint == p_186335_1_)
/*     */     {
/* 116 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 120 */     Path path = createEntityPath(p_186335_1_, pathpoint);
/* 121 */     return path;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Path createEntityPath(PathPoint start, PathPoint end) {
/* 130 */     int i = 1;
/*     */     
/* 132 */     for (PathPoint pathpoint = end; pathpoint.previous != null; pathpoint = pathpoint.previous)
/*     */     {
/* 134 */       i++;
/*     */     }
/*     */     
/* 137 */     PathPoint[] apathpoint = new PathPoint[i];
/* 138 */     PathPoint pathpoint1 = end;
/* 139 */     i--;
/*     */     
/* 141 */     for (apathpoint[i] = end; pathpoint1.previous != null; apathpoint[i] = pathpoint1) {
/*     */       
/* 143 */       pathpoint1 = pathpoint1.previous;
/* 144 */       i--;
/*     */     } 
/*     */     
/* 147 */     return new Path(apathpoint);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\pathfinding\PathFinder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */