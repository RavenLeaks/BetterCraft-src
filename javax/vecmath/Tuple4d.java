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
/*     */ public abstract class Tuple4d
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -4748953690425311052L;
/*     */   public double x;
/*     */   public double y;
/*     */   public double z;
/*     */   public double w;
/*     */   
/*     */   public Tuple4d(double x, double y, double z, double w) {
/*  69 */     this.x = x;
/*  70 */     this.y = y;
/*  71 */     this.z = z;
/*  72 */     this.w = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d(double[] t) {
/*  83 */     this.x = t[0];
/*  84 */     this.y = t[1];
/*  85 */     this.z = t[2];
/*  86 */     this.w = t[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d(Tuple4d t1) {
/*  96 */     this.x = t1.x;
/*  97 */     this.y = t1.y;
/*  98 */     this.z = t1.z;
/*  99 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d(Tuple4f t1) {
/* 109 */     this.x = t1.x;
/* 110 */     this.y = t1.y;
/* 111 */     this.z = t1.z;
/* 112 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4d() {
/* 121 */     this.x = 0.0D;
/* 122 */     this.y = 0.0D;
/* 123 */     this.z = 0.0D;
/* 124 */     this.w = 0.0D;
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
/*     */   public final void set(double x, double y, double z, double w) {
/* 137 */     this.x = x;
/* 138 */     this.y = y;
/* 139 */     this.z = z;
/* 140 */     this.w = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] t) {
/* 150 */     this.x = t[0];
/* 151 */     this.y = t[1];
/* 152 */     this.z = t[2];
/* 153 */     this.w = t[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4d t1) {
/* 163 */     this.x = t1.x;
/* 164 */     this.y = t1.y;
/* 165 */     this.z = t1.z;
/* 166 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4f t1) {
/* 176 */     this.x = t1.x;
/* 177 */     this.y = t1.y;
/* 178 */     this.z = t1.z;
/* 179 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(double[] t) {
/* 190 */     t[0] = this.x;
/* 191 */     t[1] = this.y;
/* 192 */     t[2] = this.z;
/* 193 */     t[3] = this.w;
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
/*     */   public final void get(Tuple4d t) {
/* 205 */     t.x = this.x;
/* 206 */     t.y = this.y;
/* 207 */     t.z = this.z;
/* 208 */     t.w = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4d t1, Tuple4d t2) {
/* 219 */     t1.x += t2.x;
/* 220 */     t1.y += t2.y;
/* 221 */     t1.z += t2.z;
/* 222 */     t1.w += t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4d t1) {
/* 232 */     this.x += t1.x;
/* 233 */     this.y += t1.y;
/* 234 */     this.z += t1.z;
/* 235 */     this.w += t1.w;
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
/*     */   public final void sub(Tuple4d t1, Tuple4d t2) {
/* 247 */     t1.x -= t2.x;
/* 248 */     t1.y -= t2.y;
/* 249 */     t1.z -= t2.z;
/* 250 */     t1.w -= t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple4d t1) {
/* 261 */     this.x -= t1.x;
/* 262 */     this.y -= t1.y;
/* 263 */     this.z -= t1.z;
/* 264 */     this.w -= t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple4d t1) {
/* 274 */     this.x = -t1.x;
/* 275 */     this.y = -t1.y;
/* 276 */     this.z = -t1.z;
/* 277 */     this.w = -t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 286 */     this.x = -this.x;
/* 287 */     this.y = -this.y;
/* 288 */     this.z = -this.z;
/* 289 */     this.w = -this.w;
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
/*     */   public final void scale(double s, Tuple4d t1) {
/* 301 */     this.x = s * t1.x;
/* 302 */     this.y = s * t1.y;
/* 303 */     this.z = s * t1.z;
/* 304 */     this.w = s * t1.w;
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
/* 315 */     this.x *= s;
/* 316 */     this.y *= s;
/* 317 */     this.z *= s;
/* 318 */     this.w *= s;
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
/*     */   public final void scaleAdd(double s, Tuple4d t1, Tuple4d t2) {
/* 331 */     this.x = s * t1.x + t2.x;
/* 332 */     this.y = s * t1.y + t2.y;
/* 333 */     this.z = s * t1.z + t2.z;
/* 334 */     this.w = s * t1.w + t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(float s, Tuple4d t1) {
/* 343 */     scaleAdd(s, t1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(double s, Tuple4d t1) {
/* 354 */     this.x = s * this.x + t1.x;
/* 355 */     this.y = s * this.y + t1.y;
/* 356 */     this.z = s * this.z + t1.z;
/* 357 */     this.w = s * this.w + t1.w;
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
/* 369 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
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
/*     */   public boolean equals(Tuple4d t1) {
/*     */     try {
/* 382 */       return (this.x == t1.x && this.y == t1.y && this.z == t1.z && 
/* 383 */         this.w == t1.w);
/*     */     } catch (NullPointerException e2) {
/* 385 */       return false;
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
/*     */   public boolean equals(Object t1) {
/*     */     
/* 400 */     try { Tuple4d t2 = (Tuple4d)t1;
/* 401 */       return (this.x == t2.x && this.y == t2.y && 
/* 402 */         this.z == t2.z && this.w == t2.w); }
/*     */     catch (NullPointerException e2)
/* 404 */     { return false; }
/* 405 */     catch (ClassCastException e1) { return false; }
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
/*     */   public boolean epsilonEquals(Tuple4d t1, double epsilon) {
/* 423 */     double diff = this.x - t1.x;
/* 424 */     if (Double.isNaN(diff)) return false; 
/* 425 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 427 */     diff = this.y - t1.y;
/* 428 */     if (Double.isNaN(diff)) return false; 
/* 429 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 431 */     diff = this.z - t1.z;
/* 432 */     if (Double.isNaN(diff)) return false; 
/* 433 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 435 */     diff = this.w - t1.w;
/* 436 */     if (Double.isNaN(diff)) return false; 
/* 437 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*     */     
/* 439 */     return true;
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
/*     */   public int hashCode() {
/* 454 */     long bits = 1L;
/* 455 */     bits = VecMathUtil.hashDoubleBits(bits, this.x);
/* 456 */     bits = VecMathUtil.hashDoubleBits(bits, this.y);
/* 457 */     bits = VecMathUtil.hashDoubleBits(bits, this.z);
/* 458 */     bits = VecMathUtil.hashDoubleBits(bits, this.w);
/* 459 */     return VecMathUtil.hashFinish(bits);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max, Tuple4d t) {
/* 467 */     clamp(min, max, t);
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
/*     */   public final void clamp(double min, double max, Tuple4d t) {
/* 479 */     if (t.x > max) {
/* 480 */       this.x = max;
/* 481 */     } else if (t.x < min) {
/* 482 */       this.x = min;
/*     */     } else {
/* 484 */       this.x = t.x;
/*     */     } 
/*     */     
/* 487 */     if (t.y > max) {
/* 488 */       this.y = max;
/* 489 */     } else if (t.y < min) {
/* 490 */       this.y = min;
/*     */     } else {
/* 492 */       this.y = t.y;
/*     */     } 
/*     */     
/* 495 */     if (t.z > max) {
/* 496 */       this.z = max;
/* 497 */     } else if (t.z < min) {
/* 498 */       this.z = min;
/*     */     } else {
/* 500 */       this.z = t.z;
/*     */     } 
/*     */     
/* 503 */     if (t.w > max) {
/* 504 */       this.w = max;
/* 505 */     } else if (t.w < min) {
/* 506 */       this.w = min;
/*     */     } else {
/* 508 */       this.w = t.w;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float min, Tuple4d t) {
/* 518 */     clampMin(min, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double min, Tuple4d t) {
/* 529 */     if (t.x < min) {
/* 530 */       this.x = min;
/*     */     } else {
/* 532 */       this.x = t.x;
/*     */     } 
/*     */     
/* 535 */     if (t.y < min) {
/* 536 */       this.y = min;
/*     */     } else {
/* 538 */       this.y = t.y;
/*     */     } 
/*     */     
/* 541 */     if (t.z < min) {
/* 542 */       this.z = min;
/*     */     } else {
/* 544 */       this.z = t.z;
/*     */     } 
/*     */     
/* 547 */     if (t.w < min) {
/* 548 */       this.w = min;
/*     */     } else {
/* 550 */       this.w = t.w;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max, Tuple4d t) {
/* 560 */     clampMax(max, t);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double max, Tuple4d t) {
/* 571 */     if (t.x > max) {
/* 572 */       this.x = max;
/*     */     } else {
/* 574 */       this.x = t.x;
/*     */     } 
/*     */     
/* 577 */     if (t.y > max) {
/* 578 */       this.y = max;
/*     */     } else {
/* 580 */       this.y = t.y;
/*     */     } 
/*     */     
/* 583 */     if (t.z > max) {
/* 584 */       this.z = max;
/*     */     } else {
/* 586 */       this.z = t.z;
/*     */     } 
/*     */     
/* 589 */     if (t.w > max) {
/* 590 */       this.w = max;
/*     */     } else {
/* 592 */       this.w = t.z;
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
/*     */   public final void absolute(Tuple4d t) {
/* 605 */     this.x = Math.abs(t.x);
/* 606 */     this.y = Math.abs(t.y);
/* 607 */     this.z = Math.abs(t.z);
/* 608 */     this.w = Math.abs(t.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max) {
/* 618 */     clamp(min, max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(double min, double max) {
/* 628 */     if (this.x > max) {
/* 629 */       this.x = max;
/* 630 */     } else if (this.x < min) {
/* 631 */       this.x = min;
/*     */     } 
/*     */     
/* 634 */     if (this.y > max) {
/* 635 */       this.y = max;
/* 636 */     } else if (this.y < min) {
/* 637 */       this.y = min;
/*     */     } 
/*     */     
/* 640 */     if (this.z > max) {
/* 641 */       this.z = max;
/* 642 */     } else if (this.z < min) {
/* 643 */       this.z = min;
/*     */     } 
/*     */     
/* 646 */     if (this.w > max) {
/* 647 */       this.w = max;
/* 648 */     } else if (this.w < min) {
/* 649 */       this.w = min;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(float min) {
/* 659 */     clampMin(min);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(double min) {
/* 668 */     if (this.x < min) this.x = min; 
/* 669 */     if (this.y < min) this.y = min; 
/* 670 */     if (this.z < min) this.z = min; 
/* 671 */     if (this.w < min) this.w = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max) {
/* 679 */     clampMax(max);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(double max) {
/* 688 */     if (this.x > max) this.x = max; 
/* 689 */     if (this.y > max) this.y = max; 
/* 690 */     if (this.z > max) this.z = max; 
/* 691 */     if (this.w > max) this.w = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 701 */     this.x = Math.abs(this.x);
/* 702 */     this.y = Math.abs(this.y);
/* 703 */     this.z = Math.abs(this.z);
/* 704 */     this.w = Math.abs(this.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(Tuple4d t1, Tuple4d t2, float alpha) {
/* 713 */     interpolate(t1, t2, alpha);
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
/*     */   public void interpolate(Tuple4d t1, Tuple4d t2, double alpha) {
/* 725 */     this.x = (1.0D - alpha) * t1.x + alpha * t2.x;
/* 726 */     this.y = (1.0D - alpha) * t1.y + alpha * t2.y;
/* 727 */     this.z = (1.0D - alpha) * t1.z + alpha * t2.z;
/* 728 */     this.w = (1.0D - alpha) * t1.w + alpha * t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(Tuple4d t1, float alpha) {
/* 736 */     interpolate(t1, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void interpolate(Tuple4d t1, double alpha) {
/* 747 */     this.x = (1.0D - alpha) * this.x + alpha * t1.x;
/* 748 */     this.y = (1.0D - alpha) * this.y + alpha * t1.y;
/* 749 */     this.z = (1.0D - alpha) * this.z + alpha * t1.z;
/* 750 */     this.w = (1.0D - alpha) * this.w + alpha * t1.w;
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
/* 765 */       return super.clone();
/* 766 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 768 */       throw new InternalError();
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
/* 780 */     return this.x;
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
/* 792 */     this.x = x;
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
/* 804 */     return this.y;
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
/* 816 */     this.y = y;
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
/* 827 */     return this.z;
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
/* 839 */     this.z = z;
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
/*     */   public final double getW() {
/* 851 */     return this.w;
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
/*     */   public final void setW(double w) {
/* 863 */     this.w = w;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Tuple4d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */