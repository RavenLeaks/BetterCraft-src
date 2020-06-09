/*     */ package net.minecraft.util.math;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ 
/*     */ 
/*     */ public class AxisAlignedBB
/*     */ {
/*     */   public double minX;
/*     */   public double minY;
/*     */   public double minZ;
/*     */   public double maxX;
/*     */   public double maxY;
/*     */   public double maxZ;
/*     */   
/*     */   public AxisAlignedBB(double x1, double y1, double z1, double x2, double y2, double z2) {
/*  18 */     this.minX = Math.min(x1, x2);
/*  19 */     this.minY = Math.min(y1, y2);
/*  20 */     this.minZ = Math.min(z1, z2);
/*  21 */     this.maxX = Math.max(x1, x2);
/*  22 */     this.maxY = Math.max(y1, y2);
/*  23 */     this.maxZ = Math.max(z1, z2);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB(BlockPos pos) {
/*  28 */     this(pos.getX(), pos.getY(), pos.getZ(), (pos.getX() + 1), (pos.getY() + 1), (pos.getZ() + 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB(BlockPos pos1, BlockPos pos2) {
/*  33 */     this(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB(Vec3d min, Vec3d max) {
/*  38 */     this(min.xCoord, min.yCoord, min.zCoord, max.xCoord, max.yCoord, max.zCoord);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB setMaxY(double y2) {
/*  43 */     return new AxisAlignedBB(this.minX, this.minY, this.minZ, this.maxX, y2, this.maxZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  48 */     if (this == p_equals_1_)
/*     */     {
/*  50 */       return true;
/*     */     }
/*  52 */     if (!(p_equals_1_ instanceof AxisAlignedBB))
/*     */     {
/*  54 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  58 */     AxisAlignedBB axisalignedbb = (AxisAlignedBB)p_equals_1_;
/*     */     
/*  60 */     if (Double.compare(axisalignedbb.minX, this.minX) != 0)
/*     */     {
/*  62 */       return false;
/*     */     }
/*  64 */     if (Double.compare(axisalignedbb.minY, this.minY) != 0)
/*     */     {
/*  66 */       return false;
/*     */     }
/*  68 */     if (Double.compare(axisalignedbb.minZ, this.minZ) != 0)
/*     */     {
/*  70 */       return false;
/*     */     }
/*  72 */     if (Double.compare(axisalignedbb.maxX, this.maxX) != 0)
/*     */     {
/*  74 */       return false;
/*     */     }
/*  76 */     if (Double.compare(axisalignedbb.maxY, this.maxY) != 0)
/*     */     {
/*  78 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  82 */     return (Double.compare(axisalignedbb.maxZ, this.maxZ) == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  89 */     long i = Double.doubleToLongBits(this.minX);
/*  90 */     int j = (int)(i ^ i >>> 32L);
/*  91 */     i = Double.doubleToLongBits(this.minY);
/*  92 */     j = 31 * j + (int)(i ^ i >>> 32L);
/*  93 */     i = Double.doubleToLongBits(this.minZ);
/*  94 */     j = 31 * j + (int)(i ^ i >>> 32L);
/*  95 */     i = Double.doubleToLongBits(this.maxX);
/*  96 */     j = 31 * j + (int)(i ^ i >>> 32L);
/*  97 */     i = Double.doubleToLongBits(this.maxY);
/*  98 */     j = 31 * j + (int)(i ^ i >>> 32L);
/*  99 */     i = Double.doubleToLongBits(this.maxZ);
/* 100 */     j = 31 * j + (int)(i ^ i >>> 32L);
/* 101 */     return j;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_191195_a(double p_191195_1_, double p_191195_3_, double p_191195_5_) {
/* 106 */     double d0 = this.minX;
/* 107 */     double d1 = this.minY;
/* 108 */     double d2 = this.minZ;
/* 109 */     double d3 = this.maxX;
/* 110 */     double d4 = this.maxY;
/* 111 */     double d5 = this.maxZ;
/*     */     
/* 113 */     if (p_191195_1_ < 0.0D) {
/*     */       
/* 115 */       d0 -= p_191195_1_;
/*     */     }
/* 117 */     else if (p_191195_1_ > 0.0D) {
/*     */       
/* 119 */       d3 -= p_191195_1_;
/*     */     } 
/*     */     
/* 122 */     if (p_191195_3_ < 0.0D) {
/*     */       
/* 124 */       d1 -= p_191195_3_;
/*     */     }
/* 126 */     else if (p_191195_3_ > 0.0D) {
/*     */       
/* 128 */       d4 -= p_191195_3_;
/*     */     } 
/*     */     
/* 131 */     if (p_191195_5_ < 0.0D) {
/*     */       
/* 133 */       d2 -= p_191195_5_;
/*     */     }
/* 135 */     else if (p_191195_5_ > 0.0D) {
/*     */       
/* 137 */       d5 -= p_191195_5_;
/*     */     } 
/*     */     
/* 140 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB addCoord(double x, double y, double z) {
/* 148 */     double d0 = this.minX;
/* 149 */     double d1 = this.minY;
/* 150 */     double d2 = this.minZ;
/* 151 */     double d3 = this.maxX;
/* 152 */     double d4 = this.maxY;
/* 153 */     double d5 = this.maxZ;
/*     */     
/* 155 */     if (x < 0.0D) {
/*     */       
/* 157 */       d0 += x;
/*     */     }
/* 159 */     else if (x > 0.0D) {
/*     */       
/* 161 */       d3 += x;
/*     */     } 
/*     */     
/* 164 */     if (y < 0.0D) {
/*     */       
/* 166 */       d1 += y;
/*     */     }
/* 168 */     else if (y > 0.0D) {
/*     */       
/* 170 */       d4 += y;
/*     */     } 
/*     */     
/* 173 */     if (z < 0.0D) {
/*     */       
/* 175 */       d2 += z;
/*     */     }
/* 177 */     else if (z > 0.0D) {
/*     */       
/* 179 */       d5 += z;
/*     */     } 
/*     */     
/* 182 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB expand(double x, double y, double z) {
/* 190 */     double d0 = this.minX - x;
/* 191 */     double d1 = this.minY - y;
/* 192 */     double d2 = this.minZ - z;
/* 193 */     double d3 = this.maxX + x;
/* 194 */     double d4 = this.maxY + y;
/* 195 */     double d5 = this.maxZ + z;
/* 196 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB expandXyz(double value) {
/* 201 */     return expand(value, value, value);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_191500_a(AxisAlignedBB p_191500_1_) {
/* 206 */     double d0 = Math.max(this.minX, p_191500_1_.minX);
/* 207 */     double d1 = Math.max(this.minY, p_191500_1_.minY);
/* 208 */     double d2 = Math.max(this.minZ, p_191500_1_.minZ);
/* 209 */     double d3 = Math.min(this.maxX, p_191500_1_.maxX);
/* 210 */     double d4 = Math.min(this.maxY, p_191500_1_.maxY);
/* 211 */     double d5 = Math.min(this.maxZ, p_191500_1_.maxZ);
/* 212 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB union(AxisAlignedBB other) {
/* 217 */     double d0 = Math.min(this.minX, other.minX);
/* 218 */     double d1 = Math.min(this.minY, other.minY);
/* 219 */     double d2 = Math.min(this.minZ, other.minZ);
/* 220 */     double d3 = Math.max(this.maxX, other.maxX);
/* 221 */     double d4 = Math.max(this.maxY, other.maxY);
/* 222 */     double d5 = Math.max(this.maxZ, other.maxZ);
/* 223 */     return new AxisAlignedBB(d0, d1, d2, d3, d4, d5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB offset(double x, double y, double z) {
/* 231 */     return new AxisAlignedBB(this.minX + x, this.minY + y, this.minZ + z, this.maxX + x, this.maxY + y, this.maxZ + z);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB offset(BlockPos pos) {
/* 236 */     return new AxisAlignedBB(this.minX + pos.getX(), this.minY + pos.getY(), this.minZ + pos.getZ(), this.maxX + pos.getX(), this.maxY + pos.getY(), this.maxZ + pos.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB func_191194_a(Vec3d p_191194_1_) {
/* 241 */     return offset(p_191194_1_.xCoord, p_191194_1_.yCoord, p_191194_1_.zCoord);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateXOffset(AxisAlignedBB other, double offsetX) {
/* 251 */     if (other.maxY > this.minY && other.minY < this.maxY && other.maxZ > this.minZ && other.minZ < this.maxZ) {
/*     */       
/* 253 */       if (offsetX > 0.0D && other.maxX <= this.minX) {
/*     */         
/* 255 */         double d1 = this.minX - other.maxX;
/*     */         
/* 257 */         if (d1 < offsetX)
/*     */         {
/* 259 */           offsetX = d1;
/*     */         }
/*     */       }
/* 262 */       else if (offsetX < 0.0D && other.minX >= this.maxX) {
/*     */         
/* 264 */         double d0 = this.maxX - other.minX;
/*     */         
/* 266 */         if (d0 > offsetX)
/*     */         {
/* 268 */           offsetX = d0;
/*     */         }
/*     */       } 
/*     */       
/* 272 */       return offsetX;
/*     */     } 
/*     */ 
/*     */     
/* 276 */     return offsetX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateYOffset(AxisAlignedBB other, double offsetY) {
/* 287 */     if (other.maxX > this.minX && other.minX < this.maxX && other.maxZ > this.minZ && other.minZ < this.maxZ) {
/*     */       
/* 289 */       if (offsetY > 0.0D && other.maxY <= this.minY) {
/*     */         
/* 291 */         double d1 = this.minY - other.maxY;
/*     */         
/* 293 */         if (d1 < offsetY)
/*     */         {
/* 295 */           offsetY = d1;
/*     */         }
/*     */       }
/* 298 */       else if (offsetY < 0.0D && other.minY >= this.maxY) {
/*     */         
/* 300 */         double d0 = this.maxY - other.minY;
/*     */         
/* 302 */         if (d0 > offsetY)
/*     */         {
/* 304 */           offsetY = d0;
/*     */         }
/*     */       } 
/*     */       
/* 308 */       return offsetY;
/*     */     } 
/*     */ 
/*     */     
/* 312 */     return offsetY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double calculateZOffset(AxisAlignedBB other, double offsetZ) {
/* 323 */     if (other.maxX > this.minX && other.minX < this.maxX && other.maxY > this.minY && other.minY < this.maxY) {
/*     */       
/* 325 */       if (offsetZ > 0.0D && other.maxZ <= this.minZ) {
/*     */         
/* 327 */         double d1 = this.minZ - other.maxZ;
/*     */         
/* 329 */         if (d1 < offsetZ)
/*     */         {
/* 331 */           offsetZ = d1;
/*     */         }
/*     */       }
/* 334 */       else if (offsetZ < 0.0D && other.minZ >= this.maxZ) {
/*     */         
/* 336 */         double d0 = this.maxZ - other.minZ;
/*     */         
/* 338 */         if (d0 > offsetZ)
/*     */         {
/* 340 */           offsetZ = d0;
/*     */         }
/*     */       } 
/*     */       
/* 344 */       return offsetZ;
/*     */     } 
/*     */ 
/*     */     
/* 348 */     return offsetZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean intersectsWith(AxisAlignedBB other) {
/* 357 */     return intersects(other.minX, other.minY, other.minZ, other.maxX, other.maxY, other.maxZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean intersects(double x1, double y1, double z1, double x2, double y2, double z2) {
/* 362 */     return (this.minX < x2 && this.maxX > x1 && this.minY < y2 && this.maxY > y1 && this.minZ < z2 && this.maxZ > z1);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean intersects(Vec3d min, Vec3d max) {
/* 367 */     return intersects(Math.min(min.xCoord, max.xCoord), Math.min(min.yCoord, max.yCoord), Math.min(min.zCoord, max.zCoord), Math.max(min.xCoord, max.xCoord), Math.max(min.yCoord, max.yCoord), Math.max(min.zCoord, max.zCoord));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isVecInside(Vec3d vec) {
/* 375 */     if (vec.xCoord > this.minX && vec.xCoord < this.maxX) {
/*     */       
/* 377 */       if (vec.yCoord > this.minY && vec.yCoord < this.maxY)
/*     */       {
/* 379 */         return (vec.zCoord > this.minZ && vec.zCoord < this.maxZ);
/*     */       }
/*     */ 
/*     */       
/* 383 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 388 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getAverageEdgeLength() {
/* 397 */     double d0 = this.maxX - this.minX;
/* 398 */     double d1 = this.maxY - this.minY;
/* 399 */     double d2 = this.maxZ - this.minZ;
/* 400 */     return (d0 + d1 + d2) / 3.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB contract(double value) {
/* 405 */     return expandXyz(-value);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public RayTraceResult calculateIntercept(Vec3d vecA, Vec3d vecB) {
/* 411 */     Vec3d vec3d = collideWithXPlane(this.minX, vecA, vecB);
/* 412 */     EnumFacing enumfacing = EnumFacing.WEST;
/* 413 */     Vec3d vec3d1 = collideWithXPlane(this.maxX, vecA, vecB);
/*     */     
/* 415 */     if (vec3d1 != null && isClosest(vecA, vec3d, vec3d1)) {
/*     */       
/* 417 */       vec3d = vec3d1;
/* 418 */       enumfacing = EnumFacing.EAST;
/*     */     } 
/*     */     
/* 421 */     vec3d1 = collideWithYPlane(this.minY, vecA, vecB);
/*     */     
/* 423 */     if (vec3d1 != null && isClosest(vecA, vec3d, vec3d1)) {
/*     */       
/* 425 */       vec3d = vec3d1;
/* 426 */       enumfacing = EnumFacing.DOWN;
/*     */     } 
/*     */     
/* 429 */     vec3d1 = collideWithYPlane(this.maxY, vecA, vecB);
/*     */     
/* 431 */     if (vec3d1 != null && isClosest(vecA, vec3d, vec3d1)) {
/*     */       
/* 433 */       vec3d = vec3d1;
/* 434 */       enumfacing = EnumFacing.UP;
/*     */     } 
/*     */     
/* 437 */     vec3d1 = collideWithZPlane(this.minZ, vecA, vecB);
/*     */     
/* 439 */     if (vec3d1 != null && isClosest(vecA, vec3d, vec3d1)) {
/*     */       
/* 441 */       vec3d = vec3d1;
/* 442 */       enumfacing = EnumFacing.NORTH;
/*     */     } 
/*     */     
/* 445 */     vec3d1 = collideWithZPlane(this.maxZ, vecA, vecB);
/*     */     
/* 447 */     if (vec3d1 != null && isClosest(vecA, vec3d, vec3d1)) {
/*     */       
/* 449 */       vec3d = vec3d1;
/* 450 */       enumfacing = EnumFacing.SOUTH;
/*     */     } 
/*     */     
/* 453 */     return (vec3d == null) ? null : new RayTraceResult(vec3d, enumfacing);
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   boolean isClosest(Vec3d p_186661_1_, @Nullable Vec3d p_186661_2_, Vec3d p_186661_3_) {
/* 459 */     return !(p_186661_2_ != null && p_186661_1_.squareDistanceTo(p_186661_3_) >= p_186661_1_.squareDistanceTo(p_186661_2_));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @VisibleForTesting
/*     */   Vec3d collideWithXPlane(double p_186671_1_, Vec3d p_186671_3_, Vec3d p_186671_4_) {
/* 466 */     Vec3d vec3d = p_186671_3_.getIntermediateWithXValue(p_186671_4_, p_186671_1_);
/* 467 */     return (vec3d != null && intersectsWithYZ(vec3d)) ? vec3d : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @VisibleForTesting
/*     */   Vec3d collideWithYPlane(double p_186663_1_, Vec3d p_186663_3_, Vec3d p_186663_4_) {
/* 474 */     Vec3d vec3d = p_186663_3_.getIntermediateWithYValue(p_186663_4_, p_186663_1_);
/* 475 */     return (vec3d != null && intersectsWithXZ(vec3d)) ? vec3d : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @VisibleForTesting
/*     */   Vec3d collideWithZPlane(double p_186665_1_, Vec3d p_186665_3_, Vec3d p_186665_4_) {
/* 482 */     Vec3d vec3d = p_186665_3_.getIntermediateWithZValue(p_186665_4_, p_186665_1_);
/* 483 */     return (vec3d != null && intersectsWithXY(vec3d)) ? vec3d : null;
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   public boolean intersectsWithYZ(Vec3d vec) {
/* 489 */     return (vec.yCoord >= this.minY && vec.yCoord <= this.maxY && vec.zCoord >= this.minZ && vec.zCoord <= this.maxZ);
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   public boolean intersectsWithXZ(Vec3d vec) {
/* 495 */     return (vec.xCoord >= this.minX && vec.xCoord <= this.maxX && vec.zCoord >= this.minZ && vec.zCoord <= this.maxZ);
/*     */   }
/*     */ 
/*     */   
/*     */   @VisibleForTesting
/*     */   public boolean intersectsWithXY(Vec3d vec) {
/* 501 */     return (vec.xCoord >= this.minX && vec.xCoord <= this.maxX && vec.yCoord >= this.minY && vec.yCoord <= this.maxY);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 506 */     return "box[" + this.minX + ", " + this.minY + ", " + this.minZ + " -> " + this.maxX + ", " + this.maxY + ", " + this.maxZ + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasNaN() {
/* 511 */     return !(!Double.isNaN(this.minX) && !Double.isNaN(this.minY) && !Double.isNaN(this.minZ) && !Double.isNaN(this.maxX) && !Double.isNaN(this.maxY) && !Double.isNaN(this.maxZ));
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d getCenter() {
/* 516 */     return new Vec3d(this.minX + (this.maxX - this.minX) * 0.5D, this.minY + (this.maxY - this.minY) * 0.5D, this.minZ + (this.maxZ - this.minZ) * 0.5D);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\math\AxisAlignedBB.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */