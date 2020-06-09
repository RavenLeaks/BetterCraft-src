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
/*     */ public abstract class Tuple3d
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 5542096614926168415L;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   
/*     */   public Tuple3d(double x, double y, double z) {
/*  63 */     this.x = x;
/*  64 */     this.y = y;
/*  65 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d(double[] t) {
/*  74 */     this.x = t[0];
/*  75 */     this.y = t[1];
/*  76 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d(Tuple3d t1) {
/*  85 */     this.x = t1.x;
/*  86 */     this.y = t1.y;
/*  87 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d(Tuple3f t1) {
/*  96 */     this.x = t1.x;
/*  97 */     this.y = t1.y;
/*  98 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3d() {
/* 106 */     this.x = 0.0D;
/* 107 */     this.y = 0.0D;
/* 108 */     this.z = 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double x, double y, double z) {
/* 119 */     this.x = x;
/* 120 */     this.y = y;
/* 121 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] t) {
/* 131 */     this.x = t[0];
/* 132 */     this.y = t[1];
/* 133 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d t1) {
/* 142 */     this.x = t1.x;
/* 143 */     this.y = t1.y;
/* 144 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f t1) {
/* 153 */     this.x = t1.x;
/* 154 */     this.y = t1.y;
/* 155 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(double[] t) {
/* 165 */     t[0] = this.x;
/* 166 */     t[1] = this.y;
/* 167 */     t[2] = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple3d t) {
/* 177 */     t.x = this.x;
/* 178 */     t.y = this.y;
/* 179 */     t.z = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3d t1, Tuple3d t2) {
/* 190 */     t1.x += t2.x;
/* 191 */     t1.y += t2.y;
/* 192 */     t1.z += t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3d t1) {
/* 202 */     this.x += t1.x;
/* 203 */     this.y += t1.y;
/* 204 */     this.z += t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3d t1, Tuple3d t2) {
/* 215 */     t1.x -= t2.x;
/* 216 */     t1.y -= t2.y;
/* 217 */     t1.z -= t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3d t1) {
/* 227 */     this.x -= t1.x;
/* 228 */     this.y -= t1.y;
/* 229 */     this.z -= t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple3d t1) {
/* 239 */     this.x = -t1.x;
/* 240 */     this.y = -t1.y;
/* 241 */     this.z = -t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 250 */     this.x = -this.x;
/* 251 */     this.y = -this.y;
/* 252 */     this.z = -this.z;
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
/*     */   public final void scale(double s, Tuple3d t1) {
/* 264 */     this.x = s * t1.x;
/* 265 */     this.y = s * t1.y;
/* 266 */     this.z = s * t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(double s) {
/* 277 */     this.x *= s;
/* 278 */     this.y *= s;
/* 279 */     this.z *= s;
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
/*     */   public final void scaleAdd(double s, Tuple3d t1, Tuple3d t2) {
/* 292 */     this.x = s * t1.x + t2.x;
/* 293 */     this.y = s * t1.y + t2.y;
/* 294 */     this.z = s * t1.z + t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(double s, Tuple3f t1) {
/* 302 */     scaleAdd(s, new Point3d(t1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(double s, Tuple3d t1) {
/* 313 */     this.x = s * this.x + t1.x;
/* 314 */     this.y = s * this.y + t1.y;
/* 315 */     this.z = s * this.z + t1.z;
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
/*     */   public String toString() {
/* 327 */     return "(" + this.x + ", " + this.y + ", " + this.z + ")";
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
/* 341 */     long bits = 1L;
/* 342 */     bits = VecMathUtil.hashDoubleBits(bits, this.x);
/* 343 */     bits = VecMathUtil.hashDoubleBits(bits, this.y);
/* 344 */     bits = VecMathUtil.hashDoubleBits(bits, this.z);
/* 345 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public boolean equals(Tuple3d t1) {
/*     */     try {
/* 358 */       return (this.x == t1.x && this.y == t1.y && this.z == t1.z);
/*     */     } catch (NullPointerException e2) {
/* 360 */       return false;
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
/*     */   public boolean equals(Object t1) {
/*     */     
/* 374 */     try { Tuple3d t2 = (Tuple3d)t1;
/* 375 */       return (this.x == t2.x && this.y == t2.y && this.z == t2.z); }
/*     */     catch (ClassCastException e1)
/* 377 */     { return false; }
/* 378 */     catch (NullPointerException e2) { return false; }
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
/*     */   public boolean epsilonEquals(Tuple3d t1, double epsilon) {
/* 395 */     double diff = this.x - t1.x;
/* 396 */     if (Double.isNaN(diff)) return false; 
/* 397 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 399 */     diff = this.y - t1.y;
/* 400 */     if (Double.isNaN(diff)) return false; 
/* 401 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 403 */     diff = this.z - t1.z;
/* 404 */     if (Double.isNaN(diff)) return false; 
/* 405 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 407 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max, Tuple3d t) {
/* 416 */     clamp(min, max, t);
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
/*     */   public final void clamp(double min, double max, Tuple3d t) {
/* 428 */     if (t.x > max) {
/* 429 */       this.x = max;
/* 430 */     } else if (t.x < min) {
/* 431 */       this.x = min;
/*     */     } else {
/* 433 */       this.x = t.x;
/*     */     } 
/*     */     
/* 436 */     if (t.y > max) {
/* 437 */       this.y = max;
/* 438 */     } else if (t.y < min) {
/* 439 */       this.y = min;
/*     */     } else {
/* 441 */       this.y = t.y;
/*     */     } 
/*     */     
/* 444 */     if (t.z > max) {
/* 445 */       this.z = max;
/* 446 */     } else if (t.z < min) {
/* 447 */       this.z = min;
/*     */     } else {
/* 449 */       this.z = t.z;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float min, Tuple3d t) {
/* 459 */     clampMin(min, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double min, Tuple3d t) {
/* 470 */     if (t.x < min) {
/* 471 */       this.x = min;
/*     */     } else {
/* 473 */       this.x = t.x;
/*     */     } 
/*     */     
/* 476 */     if (t.y < min) {
/* 477 */       this.y = min;
/*     */     } else {
/* 479 */       this.y = t.y;
/*     */     } 
/*     */     
/* 482 */     if (t.z < min) {
/* 483 */       this.z = min;
/*     */     } else {
/* 485 */       this.z = t.z;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max, Tuple3d t) {
/* 495 */     clampMax(max, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double max, Tuple3d t) {
/* 506 */     if (t.x > max) {
/* 507 */       this.x = max;
/*     */     } else {
/* 509 */       this.x = t.x;
/*     */     } 
/*     */     
/* 512 */     if (t.y > max) {
/* 513 */       this.y = max;
/*     */     } else {
/* 515 */       this.y = t.y;
/*     */     } 
/*     */     
/* 518 */     if (t.z > max) {
/* 519 */       this.z = max;
/*     */     } else {
/* 521 */       this.z = t.z;
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
/*     */   public final void absolute(Tuple3d t) {
/* 534 */     this.x = Math.abs(t.x);
/* 535 */     this.y = Math.abs(t.y);
/* 536 */     this.z = Math.abs(t.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max) {
/* 545 */     clamp(min, max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(double min, double max) {
/* 555 */     if (this.x > max) {
/* 556 */       this.x = max;
/* 557 */     } else if (this.x < min) {
/* 558 */       this.x = min;
/*     */     } 
/*     */     
/* 561 */     if (this.y > max) {
/* 562 */       this.y = max;
/* 563 */     } else if (this.y < min) {
/* 564 */       this.y = min;
/*     */     } 
/*     */     
/* 567 */     if (this.z > max) {
/* 568 */       this.z = max;
/* 569 */     } else if (this.z < min) {
/* 570 */       this.z = min;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float min) {
/* 580 */     clampMin(min);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double min) {
/* 589 */     if (this.x < min) this.x = min; 
/* 590 */     if (this.y < min) this.y = min; 
/* 591 */     if (this.z < min) this.z = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max) {
/* 600 */     clampMax(max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double max) {
/* 609 */     if (this.x > max) this.x = max; 
/* 610 */     if (this.y > max) this.y = max; 
/* 611 */     if (this.z > max) this.z = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 620 */     this.x = Math.abs(this.x);
/* 621 */     this.y = Math.abs(this.y);
/* 622 */     this.z = Math.abs(this.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Tuple3d t1, Tuple3d t2, float alpha) {
/* 630 */     interpolate(t1, t2, alpha);
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
/*     */   public final void interpolate(Tuple3d t1, Tuple3d t2, double alpha) {
/* 642 */     this.x = (1.0D - alpha) * t1.x + alpha * t2.x;
/* 643 */     this.y = (1.0D - alpha) * t1.y + alpha * t2.y;
/* 644 */     this.z = (1.0D - alpha) * t1.z + alpha * t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Tuple3d t1, float alpha) {
/* 652 */     interpolate(t1, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Tuple3d t1, double alpha) {
/* 663 */     this.x = (1.0D - alpha) * this.x + alpha * t1.x;
/* 664 */     this.y = (1.0D - alpha) * this.y + alpha * t1.y;
/* 665 */     this.z = (1.0D - alpha) * this.z + alpha * t1.z;
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
/* 680 */       return super.clone();
/* 681 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 683 */       throw new InternalError();
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
/*     */   public final double getX() {
/* 695 */     return this.x;
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
/* 707 */     this.x = x;
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
/* 719 */     return this.y;
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
/* 731 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getZ() {
/* 742 */     return this.z;
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
/* 754 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Tuple3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */