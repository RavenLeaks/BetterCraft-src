/*     */ package net.minecraft.util.math;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class Vec3d
/*     */ {
/*   7 */   public static final Vec3d ZERO = new Vec3d(0.0D, 0.0D, 0.0D);
/*     */ 
/*     */   
/*     */   public final double xCoord;
/*     */ 
/*     */   
/*     */   public final double yCoord;
/*     */ 
/*     */   
/*     */   public final double zCoord;
/*     */ 
/*     */   
/*     */   public Vec3d(double x, double y, double z) {
/*  20 */     if (x == -0.0D)
/*     */     {
/*  22 */       x = 0.0D;
/*     */     }
/*     */     
/*  25 */     if (y == -0.0D)
/*     */     {
/*  27 */       y = 0.0D;
/*     */     }
/*     */     
/*  30 */     if (z == -0.0D)
/*     */     {
/*  32 */       z = 0.0D;
/*     */     }
/*     */     
/*  35 */     this.xCoord = x;
/*  36 */     this.yCoord = y;
/*  37 */     this.zCoord = z;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d(Vec3i vector) {
/*  42 */     this(vector.getX(), vector.getY(), vector.getZ());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3d subtractReverse(Vec3d vec) {
/*  50 */     return new Vec3d(vec.xCoord - this.xCoord, vec.yCoord - this.yCoord, vec.zCoord - this.zCoord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3d normalize() {
/*  58 */     double d0 = MathHelper.sqrt(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
/*  59 */     return (d0 < 1.0E-4D) ? ZERO : new Vec3d(this.xCoord / d0, this.yCoord / d0, this.zCoord / d0);
/*     */   }
/*     */ 
/*     */   
/*     */   public double dotProduct(Vec3d vec) {
/*  64 */     return this.xCoord * vec.xCoord + this.yCoord * vec.yCoord + this.zCoord * vec.zCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3d crossProduct(Vec3d vec) {
/*  72 */     return new Vec3d(this.yCoord * vec.zCoord - this.zCoord * vec.yCoord, this.zCoord * vec.xCoord - this.xCoord * vec.zCoord, this.xCoord * vec.yCoord - this.yCoord * vec.xCoord);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d subtract(Vec3d vec) {
/*  77 */     return subtract(vec.xCoord, vec.yCoord, vec.zCoord);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d subtract(double x, double y, double z) {
/*  82 */     return addVector(-x, -y, -z);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d add(Vec3d vec) {
/*  87 */     return addVector(vec.xCoord, vec.yCoord, vec.zCoord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3d addVector(double x, double y, double z) {
/*  96 */     return new Vec3d(this.xCoord + x, this.yCoord + y, this.zCoord + z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double distanceTo(Vec3d vec) {
/* 104 */     double d0 = vec.xCoord - this.xCoord;
/* 105 */     double d1 = vec.yCoord - this.yCoord;
/* 106 */     double d2 = vec.zCoord - this.zCoord;
/* 107 */     return MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double squareDistanceTo(Vec3d vec) {
/* 115 */     double d0 = vec.xCoord - this.xCoord;
/* 116 */     double d1 = vec.yCoord - this.yCoord;
/* 117 */     double d2 = vec.zCoord - this.zCoord;
/* 118 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */   
/*     */   public double squareDistanceTo(double xIn, double yIn, double zIn) {
/* 123 */     double d0 = xIn - this.xCoord;
/* 124 */     double d1 = yIn - this.yCoord;
/* 125 */     double d2 = zIn - this.zCoord;
/* 126 */     return d0 * d0 + d1 * d1 + d2 * d2;
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d scale(double p_186678_1_) {
/* 131 */     return new Vec3d(this.xCoord * p_186678_1_, this.yCoord * p_186678_1_, this.zCoord * p_186678_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double lengthVector() {
/* 139 */     return MathHelper.sqrt(this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord);
/*     */   }
/*     */ 
/*     */   
/*     */   public double lengthSquared() {
/* 144 */     return this.xCoord * this.xCoord + this.yCoord * this.yCoord + this.zCoord * this.zCoord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Vec3d getIntermediateWithXValue(Vec3d vec, double x) {
/* 155 */     double d0 = vec.xCoord - this.xCoord;
/* 156 */     double d1 = vec.yCoord - this.yCoord;
/* 157 */     double d2 = vec.zCoord - this.zCoord;
/*     */     
/* 159 */     if (d0 * d0 < 1.0000000116860974E-7D)
/*     */     {
/* 161 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 165 */     double d3 = (x - this.xCoord) / d0;
/* 166 */     return (d3 >= 0.0D && d3 <= 1.0D) ? new Vec3d(this.xCoord + d0 * d3, this.yCoord + d1 * d3, this.zCoord + d2 * d3) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Vec3d getIntermediateWithYValue(Vec3d vec, double y) {
/* 178 */     double d0 = vec.xCoord - this.xCoord;
/* 179 */     double d1 = vec.yCoord - this.yCoord;
/* 180 */     double d2 = vec.zCoord - this.zCoord;
/*     */     
/* 182 */     if (d1 * d1 < 1.0000000116860974E-7D)
/*     */     {
/* 184 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 188 */     double d3 = (y - this.yCoord) / d1;
/* 189 */     return (d3 >= 0.0D && d3 <= 1.0D) ? new Vec3d(this.xCoord + d0 * d3, this.yCoord + d1 * d3, this.zCoord + d2 * d3) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Vec3d getIntermediateWithZValue(Vec3d vec, double z) {
/* 201 */     double d0 = vec.xCoord - this.xCoord;
/* 202 */     double d1 = vec.yCoord - this.yCoord;
/* 203 */     double d2 = vec.zCoord - this.zCoord;
/*     */     
/* 205 */     if (d2 * d2 < 1.0000000116860974E-7D)
/*     */     {
/* 207 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 211 */     double d3 = (z - this.zCoord) / d2;
/* 212 */     return (d3 >= 0.0D && d3 <= 1.0D) ? new Vec3d(this.xCoord + d0 * d3, this.yCoord + d1 * d3, this.zCoord + d2 * d3) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 218 */     if (this == p_equals_1_)
/*     */     {
/* 220 */       return true;
/*     */     }
/* 222 */     if (!(p_equals_1_ instanceof Vec3d))
/*     */     {
/* 224 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 228 */     Vec3d vec3d = (Vec3d)p_equals_1_;
/*     */     
/* 230 */     if (Double.compare(vec3d.xCoord, this.xCoord) != 0)
/*     */     {
/* 232 */       return false;
/*     */     }
/* 234 */     if (Double.compare(vec3d.yCoord, this.yCoord) != 0)
/*     */     {
/* 236 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 240 */     return (Double.compare(vec3d.zCoord, this.zCoord) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 247 */     long j = Double.doubleToLongBits(this.xCoord);
/* 248 */     int i = (int)(j ^ j >>> 32L);
/* 249 */     j = Double.doubleToLongBits(this.yCoord);
/* 250 */     i = 31 * i + (int)(j ^ j >>> 32L);
/* 251 */     j = Double.doubleToLongBits(this.zCoord);
/* 252 */     i = 31 * i + (int)(j ^ j >>> 32L);
/* 253 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 258 */     return "(" + this.xCoord + ", " + this.yCoord + ", " + this.zCoord + ")";
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d rotatePitch(float pitch) {
/* 263 */     float f = MathHelper.cos(pitch);
/* 264 */     float f1 = MathHelper.sin(pitch);
/* 265 */     double d0 = this.xCoord;
/* 266 */     double d1 = this.yCoord * f + this.zCoord * f1;
/* 267 */     double d2 = this.zCoord * f - this.yCoord * f1;
/* 268 */     return new Vec3d(d0, d1, d2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d rotateYaw(float yaw) {
/* 273 */     float f = MathHelper.cos(yaw);
/* 274 */     float f1 = MathHelper.sin(yaw);
/* 275 */     double d0 = this.xCoord * f + this.zCoord * f1;
/* 276 */     double d1 = this.yCoord;
/* 277 */     double d2 = this.zCoord * f - this.xCoord * f1;
/* 278 */     return new Vec3d(d0, d1, d2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3d fromPitchYawVector(Vec2f p_189984_0_) {
/* 286 */     return fromPitchYaw(p_189984_0_.x, p_189984_0_.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Vec3d fromPitchYaw(float p_189986_0_, float p_189986_1_) {
/* 294 */     float f = MathHelper.cos(-p_189986_1_ * 0.017453292F - 3.1415927F);
/* 295 */     float f1 = MathHelper.sin(-p_189986_1_ * 0.017453292F - 3.1415927F);
/* 296 */     float f2 = -MathHelper.cos(-p_189986_0_ * 0.017453292F);
/* 297 */     float f3 = MathHelper.sin(-p_189986_0_ * 0.017453292F);
/* 298 */     return new Vec3d((f1 * f2), f3, (f * f2));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\math\Vec3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */