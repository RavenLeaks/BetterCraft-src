/*     */ package javax.vecmath;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AxisAngle4d
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 3644296204459140589L;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public double angle;
/*     */   static final double EPS = 1.0E-12D;
/*     */   
/*     */   public AxisAngle4d(double x, double y, double z, double angle) {
/*  74 */     this.x = x;
/*  75 */     this.y = y;
/*  76 */     this.z = z;
/*  77 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4d(double[] a) {
/*  88 */     this.x = a[0];
/*  89 */     this.y = a[1];
/*  90 */     this.z = a[2];
/*  91 */     this.angle = a[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4d(AxisAngle4d a1) {
/*  99 */     this.x = a1.x;
/* 100 */     this.y = a1.y;
/* 101 */     this.z = a1.z;
/* 102 */     this.angle = a1.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4d(AxisAngle4f a1) {
/* 113 */     this.x = a1.x;
/* 114 */     this.y = a1.y;
/* 115 */     this.z = a1.z;
/* 116 */     this.angle = a1.angle;
/*     */   }
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
/*     */   public AxisAngle4d(Vector3d axis, double angle) {
/* 129 */     this.x = axis.x;
/* 130 */     this.y = axis.y;
/* 131 */     this.z = axis.z;
/* 132 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4d() {
/* 141 */     this.x = 0.0D;
/* 142 */     this.y = 0.0D;
/* 143 */     this.z = 1.0D;
/* 144 */     this.angle = 0.0D;
/*     */   }
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
/*     */   public final void set(double x, double y, double z, double angle) {
/* 157 */     this.x = x;
/* 158 */     this.y = y;
/* 159 */     this.z = z;
/* 160 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] a) {
/* 170 */     this.x = a[0];
/* 171 */     this.y = a[1];
/* 172 */     this.z = a[2];
/* 173 */     this.angle = a[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(AxisAngle4d a1) {
/* 183 */     this.x = a1.x;
/* 184 */     this.y = a1.y;
/* 185 */     this.z = a1.z;
/* 186 */     this.angle = a1.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(AxisAngle4f a1) {
/* 196 */     this.x = a1.x;
/* 197 */     this.y = a1.y;
/* 198 */     this.z = a1.z;
/* 199 */     this.angle = a1.angle;
/*     */   }
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
/*     */   public final void set(Vector3d axis, double angle) {
/* 212 */     this.x = axis.x;
/* 213 */     this.y = axis.y;
/* 214 */     this.z = axis.z;
/* 215 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(double[] a) {
/* 226 */     a[0] = this.x;
/* 227 */     a[1] = this.y;
/* 228 */     a[2] = this.z;
/* 229 */     a[3] = this.angle;
/*     */   }
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
/*     */   
/*     */   public final void set(Matrix4f m1) {
/* 243 */     Matrix3d m3d = new Matrix3d();
/*     */     
/* 245 */     m1.get(m3d);
/*     */     
/* 247 */     this.x = (float)(m3d.m21 - m3d.m12);
/* 248 */     this.y = (float)(m3d.m02 - m3d.m20);
/* 249 */     this.z = (float)(m3d.m10 - m3d.m01);
/* 250 */     double mag = this.x * this.x + this.y * this.y + this.z * this.z;
/*     */     
/* 252 */     if (mag > 1.0E-12D) {
/* 253 */       mag = Math.sqrt(mag);
/* 254 */       double sin = 0.5D * mag;
/* 255 */       double cos = 0.5D * (m3d.m00 + m3d.m11 + m3d.m22 - 1.0D);
/*     */       
/* 257 */       this.angle = (float)Math.atan2(sin, cos);
/*     */       
/* 259 */       double invMag = 1.0D / mag;
/* 260 */       this.x *= invMag;
/* 261 */       this.y *= invMag;
/* 262 */       this.z *= invMag;
/*     */     } else {
/* 264 */       this.x = 0.0D;
/* 265 */       this.y = 1.0D;
/* 266 */       this.z = 0.0D;
/* 267 */       this.angle = 0.0D;
/*     */     } 
/*     */   }
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
/*     */   
/*     */   public final void set(Matrix4d m1) {
/* 282 */     Matrix3d m3d = new Matrix3d();
/*     */     
/* 284 */     m1.get(m3d);
/*     */     
/* 286 */     this.x = (float)(m3d.m21 - m3d.m12);
/* 287 */     this.y = (float)(m3d.m02 - m3d.m20);
/* 288 */     this.z = (float)(m3d.m10 - m3d.m01);
/*     */     
/* 290 */     double mag = this.x * this.x + this.y * this.y + this.z * this.z;
/*     */     
/* 292 */     if (mag > 1.0E-12D) {
/* 293 */       mag = Math.sqrt(mag);
/*     */       
/* 295 */       double sin = 0.5D * mag;
/* 296 */       double cos = 0.5D * (m3d.m00 + m3d.m11 + m3d.m22 - 1.0D);
/* 297 */       this.angle = (float)Math.atan2(sin, cos);
/*     */       
/* 299 */       double invMag = 1.0D / mag;
/* 300 */       this.x *= invMag;
/* 301 */       this.y *= invMag;
/* 302 */       this.z *= invMag;
/*     */     } else {
/* 304 */       this.x = 0.0D;
/* 305 */       this.y = 1.0D;
/* 306 */       this.z = 0.0D;
/* 307 */       this.angle = 0.0D;
/*     */     } 
/*     */   }
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
/*     */   public final void set(Matrix3f m1) {
/* 321 */     this.x = (m1.m21 - m1.m12);
/* 322 */     this.y = (m1.m02 - m1.m20);
/* 323 */     this.z = (m1.m10 - m1.m01);
/* 324 */     double mag = this.x * this.x + this.y * this.y + this.z * this.z;
/*     */     
/* 326 */     if (mag > 1.0E-12D) {
/* 327 */       mag = Math.sqrt(mag);
/*     */       
/* 329 */       double sin = 0.5D * mag;
/* 330 */       double cos = 0.5D * ((m1.m00 + m1.m11 + m1.m22) - 1.0D);
/* 331 */       this.angle = (float)Math.atan2(sin, cos);
/*     */       
/* 333 */       double invMag = 1.0D / mag;
/* 334 */       this.x *= invMag;
/* 335 */       this.y *= invMag;
/* 336 */       this.z *= invMag;
/*     */     } else {
/* 338 */       this.x = 0.0D;
/* 339 */       this.y = 1.0D;
/* 340 */       this.z = 0.0D;
/* 341 */       this.angle = 0.0D;
/*     */     } 
/*     */   }
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
/*     */   public final void set(Matrix3d m1) {
/* 355 */     this.x = (float)(m1.m21 - m1.m12);
/* 356 */     this.y = (float)(m1.m02 - m1.m20);
/* 357 */     this.z = (float)(m1.m10 - m1.m01);
/*     */     
/* 359 */     double mag = this.x * this.x + this.y * this.y + this.z * this.z;
/*     */     
/* 361 */     if (mag > 1.0E-12D) {
/* 362 */       mag = Math.sqrt(mag);
/*     */       
/* 364 */       double sin = 0.5D * mag;
/* 365 */       double cos = 0.5D * (m1.m00 + m1.m11 + m1.m22 - 1.0D);
/*     */       
/* 367 */       this.angle = (float)Math.atan2(sin, cos);
/*     */       
/* 369 */       double invMag = 1.0D / mag;
/* 370 */       this.x *= invMag;
/* 371 */       this.y *= invMag;
/* 372 */       this.z *= invMag;
/*     */     } else {
/* 374 */       this.x = 0.0D;
/* 375 */       this.y = 1.0D;
/* 376 */       this.z = 0.0D;
/* 377 */       this.angle = 0.0D;
/*     */     } 
/*     */   }
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
/*     */ 
/*     */   
/*     */   public final void set(Quat4f q1) {
/* 393 */     double mag = (q1.x * q1.x + q1.y * q1.y + q1.z * q1.z);
/*     */     
/* 395 */     if (mag > 1.0E-12D) {
/* 396 */       mag = Math.sqrt(mag);
/* 397 */       double invMag = 1.0D / mag;
/*     */       
/* 399 */       this.x = q1.x * invMag;
/* 400 */       this.y = q1.y * invMag;
/* 401 */       this.z = q1.z * invMag;
/* 402 */       this.angle = 2.0D * Math.atan2(mag, q1.w);
/*     */     } else {
/* 404 */       this.x = 0.0D;
/* 405 */       this.y = 1.0D;
/* 406 */       this.z = 0.0D;
/* 407 */       this.angle = 0.0D;
/*     */     } 
/*     */   }
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
/*     */   public final void set(Quat4d q1) {
/* 421 */     double mag = q1.x * q1.x + q1.y * q1.y + q1.z * q1.z;
/*     */     
/* 423 */     if (mag > 1.0E-12D) {
/* 424 */       mag = Math.sqrt(mag);
/* 425 */       double invMag = 1.0D / mag;
/*     */       
/* 427 */       this.x = q1.x * invMag;
/* 428 */       this.y = q1.y * invMag;
/* 429 */       this.z = q1.z * invMag;
/* 430 */       this.angle = 2.0D * Math.atan2(mag, q1.w);
/*     */     } else {
/* 432 */       this.x = 0.0D;
/* 433 */       this.y = 1.0D;
/* 434 */       this.z = 0.0D;
/* 435 */       this.angle = 0.0D;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 447 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.angle + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(AxisAngle4d a1) {
/*     */     try {
/* 460 */       return (this.x == a1.x && this.y == a1.y && this.z == a1.z && 
/* 461 */         this.angle == a1.angle);
/*     */     } catch (NullPointerException e2) {
/* 463 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o1) {
/*     */     
/* 477 */     try { AxisAngle4d a2 = (AxisAngle4d)o1;
/* 478 */       return (this.x == a2.x && this.y == a2.y && this.z == a2.z && 
/* 479 */         this.angle == a2.angle); }
/*     */     catch (NullPointerException e2)
/* 481 */     { return false; }
/* 482 */     catch (ClassCastException e1) { return false; }
/*     */   
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean epsilonEquals(AxisAngle4d a1, double epsilon) {
/* 500 */     double diff = this.x - a1.x;
/* 501 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 503 */     diff = this.y - a1.y;
/* 504 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 506 */     diff = this.z - a1.z;
/* 507 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 509 */     diff = this.angle - a1.angle;
/* 510 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 512 */     return true;
/*     */   }
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
/*     */   
/*     */   public int hashCode() {
/* 526 */     long bits = 1L;
/* 527 */     bits = VecMathUtil.hashDoubleBits(bits, this.x);
/* 528 */     bits = VecMathUtil.hashDoubleBits(bits, this.y);
/* 529 */     bits = VecMathUtil.hashDoubleBits(bits, this.z);
/* 530 */     bits = VecMathUtil.hashDoubleBits(bits, this.angle);
/* 531 */     return VecMathUtil.hashFinish(bits);
/*     */   }
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
/*     */   
/*     */   public Object clone() {
/*     */     try {
/* 546 */       return super.clone();
/* 547 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 549 */       throw new InternalError();
/*     */     } 
/*     */   }
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
/*     */   public final double getAngle() {
/* 563 */     return this.angle;
/*     */   }
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
/*     */   public final void setAngle(double angle) {
/* 576 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getX() {
/* 588 */     return this.x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setX(double x) {
/* 600 */     this.x = x;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getY() {
/* 612 */     return this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setY(double y) {
/* 624 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getZ() {
/* 636 */     return this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setZ(double z) {
/* 648 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\AxisAngle4d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */