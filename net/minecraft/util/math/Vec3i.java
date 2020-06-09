/*     */ package net.minecraft.util.math;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import javax.annotation.concurrent.Immutable;
/*     */ 
/*     */ @Immutable
/*     */ public class Vec3i
/*     */   implements Comparable<Vec3i>
/*     */ {
/*  10 */   public static final Vec3i NULL_VECTOR = new Vec3i(0, 0, 0);
/*     */ 
/*     */   
/*     */   private final int x;
/*     */ 
/*     */   
/*     */   private final int y;
/*     */ 
/*     */   
/*     */   private final int z;
/*     */ 
/*     */   
/*     */   public Vec3i(int xIn, int yIn, int zIn) {
/*  23 */     this.x = xIn;
/*  24 */     this.y = yIn;
/*  25 */     this.z = zIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3i(double xIn, double yIn, double zIn) {
/*  30 */     this(MathHelper.floor(xIn), MathHelper.floor(yIn), MathHelper.floor(zIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  35 */     if (this == p_equals_1_)
/*     */     {
/*  37 */       return true;
/*     */     }
/*  39 */     if (!(p_equals_1_ instanceof Vec3i))
/*     */     {
/*  41 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  45 */     Vec3i vec3i = (Vec3i)p_equals_1_;
/*     */     
/*  47 */     if (getX() != vec3i.getX())
/*     */     {
/*  49 */       return false;
/*     */     }
/*  51 */     if (getY() != vec3i.getY())
/*     */     {
/*  53 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  57 */     return (getZ() == vec3i.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  64 */     return (getY() + getZ() * 31) * 31 + getX();
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(Vec3i p_compareTo_1_) {
/*  69 */     if (getY() == p_compareTo_1_.getY())
/*     */     {
/*  71 */       return (getZ() == p_compareTo_1_.getZ()) ? (getX() - p_compareTo_1_.getX()) : (getZ() - p_compareTo_1_.getZ());
/*     */     }
/*     */ 
/*     */     
/*  75 */     return getY() - p_compareTo_1_.getY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getX() {
/*  84 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getY() {
/*  92 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getZ() {
/* 100 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3i crossProduct(Vec3i vec) {
/* 108 */     return new Vec3i(getY() * vec.getZ() - getZ() * vec.getY(), getZ() * vec.getX() - getX() * vec.getZ(), getX() * vec.getY() - getY() * vec.getX());
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDistance(int xIn, int yIn, int zIn) {
/* 113 */     double d0 = (getX() - xIn);
/* 114 */     double d1 = (getY() - yIn);
/* 115 */     double d2 = (getZ() - zIn);
/* 116 */     return Math.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceSq(double toX, double toY, double toZ) {
/* 124 */     double d0 = getX() - toX;
/* 125 */     double d1 = getY() - toY;
/* 126 */     double d2 = getZ() - toZ;
/* 127 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceSqToCenter(double xIn, double yIn, double zIn) {
/* 135 */     double d0 = getX() + 0.5D - xIn;
/* 136 */     double d1 = getY() + 0.5D - yIn;
/* 137 */     double d2 = getZ() + 0.5D - zIn;
/* 138 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceSq(Vec3i to) {
/* 146 */     return distanceSq(to.getX(), to.getY(), to.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 151 */     return MoreObjects.toStringHelper(this).add("x", getX()).add("y", getY()).add("z", getZ()).toString();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\math\Vec3i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */