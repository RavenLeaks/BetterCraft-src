/*     */ package net.minecraft.pathfinding;
/*     */ 
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathPoint
/*     */ {
/*     */   public final int xCoord;
/*     */   public final int yCoord;
/*     */   public final int zCoord;
/*     */   private final int hash;
/*  21 */   public int index = -1;
/*     */ 
/*     */   
/*     */   public float totalPathDistance;
/*     */   
/*     */   public float distanceToNext;
/*     */   
/*     */   public float distanceToTarget;
/*     */   
/*     */   public PathPoint previous;
/*     */   
/*     */   public boolean visited;
/*     */   
/*     */   public float distanceFromOrigin;
/*     */   
/*     */   public float cost;
/*     */   
/*     */   public float costMalus;
/*     */   
/*  40 */   public PathNodeType nodeType = PathNodeType.BLOCKED;
/*     */ 
/*     */   
/*     */   public PathPoint(int x, int y, int z) {
/*  44 */     this.xCoord = x;
/*  45 */     this.yCoord = y;
/*  46 */     this.zCoord = z;
/*  47 */     this.hash = makeHash(x, y, z);
/*     */   }
/*     */ 
/*     */   
/*     */   public PathPoint cloneMove(int x, int y, int z) {
/*  52 */     PathPoint pathpoint = new PathPoint(x, y, z);
/*  53 */     pathpoint.index = this.index;
/*  54 */     pathpoint.totalPathDistance = this.totalPathDistance;
/*  55 */     pathpoint.distanceToNext = this.distanceToNext;
/*  56 */     pathpoint.distanceToTarget = this.distanceToTarget;
/*  57 */     pathpoint.previous = this.previous;
/*  58 */     pathpoint.visited = this.visited;
/*  59 */     pathpoint.distanceFromOrigin = this.distanceFromOrigin;
/*  60 */     pathpoint.cost = this.cost;
/*  61 */     pathpoint.costMalus = this.costMalus;
/*  62 */     pathpoint.nodeType = this.nodeType;
/*  63 */     return pathpoint;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int makeHash(int x, int y, int z) {
/*  68 */     return y & 0xFF | (x & 0x7FFF) << 8 | (z & 0x7FFF) << 24 | ((x < 0) ? Integer.MIN_VALUE : 0) | ((z < 0) ? 32768 : 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float distanceTo(PathPoint pathpointIn) {
/*  76 */     float f = (pathpointIn.xCoord - this.xCoord);
/*  77 */     float f1 = (pathpointIn.yCoord - this.yCoord);
/*  78 */     float f2 = (pathpointIn.zCoord - this.zCoord);
/*  79 */     return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float distanceToSquared(PathPoint pathpointIn) {
/*  87 */     float f = (pathpointIn.xCoord - this.xCoord);
/*  88 */     float f1 = (pathpointIn.yCoord - this.yCoord);
/*  89 */     float f2 = (pathpointIn.zCoord - this.zCoord);
/*  90 */     return f * f + f1 * f1 + f2 * f2;
/*     */   }
/*     */ 
/*     */   
/*     */   public float distanceManhattan(PathPoint p_186281_1_) {
/*  95 */     float f = Math.abs(p_186281_1_.xCoord - this.xCoord);
/*  96 */     float f1 = Math.abs(p_186281_1_.yCoord - this.yCoord);
/*  97 */     float f2 = Math.abs(p_186281_1_.zCoord - this.zCoord);
/*  98 */     return f + f1 + f2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 103 */     if (!(p_equals_1_ instanceof PathPoint))
/*     */     {
/* 105 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 109 */     PathPoint pathpoint = (PathPoint)p_equals_1_;
/* 110 */     return (this.hash == pathpoint.hash && this.xCoord == pathpoint.xCoord && this.yCoord == pathpoint.yCoord && this.zCoord == pathpoint.zCoord);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 116 */     return this.hash;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAssigned() {
/* 124 */     return (this.index >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 129 */     return String.valueOf(this.xCoord) + ", " + this.yCoord + ", " + this.zCoord;
/*     */   }
/*     */ 
/*     */   
/*     */   public static PathPoint createFromBuffer(PacketBuffer buf) {
/* 134 */     PathPoint pathpoint = new PathPoint(buf.readInt(), buf.readInt(), buf.readInt());
/* 135 */     pathpoint.distanceFromOrigin = buf.readFloat();
/* 136 */     pathpoint.cost = buf.readFloat();
/* 137 */     pathpoint.costMalus = buf.readFloat();
/* 138 */     pathpoint.visited = buf.readBoolean();
/* 139 */     pathpoint.nodeType = PathNodeType.values()[buf.readInt()];
/* 140 */     pathpoint.distanceToTarget = buf.readFloat();
/* 141 */     return pathpoint;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\pathfinding\PathPoint.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */