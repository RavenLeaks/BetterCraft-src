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
/*     */ public abstract class Tuple4f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 7068460319248845763L;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   public float w;
/*     */   
/*     */   public Tuple4f(float x, float y, float z, float w) {
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
/*     */   public Tuple4f(float[] t) {
/*  82 */     this.x = t[0];
/*  83 */     this.y = t[1];
/*  84 */     this.z = t[2];
/*  85 */     this.w = t[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4f(Tuple4f t1) {
/*  95 */     this.x = t1.x;
/*  96 */     this.y = t1.y;
/*  97 */     this.z = t1.z;
/*  98 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4f(Tuple4d t1) {
/* 108 */     this.x = (float)t1.x;
/* 109 */     this.y = (float)t1.y;
/* 110 */     this.z = (float)t1.z;
/* 111 */     this.w = (float)t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4f() {
/* 120 */     this.x = 0.0F;
/* 121 */     this.y = 0.0F;
/* 122 */     this.z = 0.0F;
/* 123 */     this.w = 0.0F;
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
/*     */   public final void set(float x, float y, float z, float w) {
/* 136 */     this.x = x;
/* 137 */     this.y = y;
/* 138 */     this.z = z;
/* 139 */     this.w = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(float[] t) {
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
/*     */   public final void set(Tuple4f t1) {
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
/*     */   public final void set(Tuple4d t1) {
/* 176 */     this.x = (float)t1.x;
/* 177 */     this.y = (float)t1.y;
/* 178 */     this.z = (float)t1.z;
/* 179 */     this.w = (float)t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] t) {
/* 189 */     t[0] = this.x;
/* 190 */     t[1] = this.y;
/* 191 */     t[2] = this.z;
/* 192 */     t[3] = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple4f t) {
/* 202 */     t.x = this.x;
/* 203 */     t.y = this.y;
/* 204 */     t.z = this.z;
/* 205 */     t.w = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4f t1, Tuple4f t2) {
/* 216 */     t1.x += t2.x;
/* 217 */     t1.y += t2.y;
/* 218 */     t1.z += t2.z;
/* 219 */     t1.w += t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4f t1) {
/* 229 */     this.x += t1.x;
/* 230 */     this.y += t1.y;
/* 231 */     this.z += t1.z;
/* 232 */     this.w += t1.w;
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
/*     */   public final void sub(Tuple4f t1, Tuple4f t2) {
/* 244 */     t1.x -= t2.x;
/* 245 */     t1.y -= t2.y;
/* 246 */     t1.z -= t2.z;
/* 247 */     t1.w -= t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple4f t1) {
/* 258 */     this.x -= t1.x;
/* 259 */     this.y -= t1.y;
/* 260 */     this.z -= t1.z;
/* 261 */     this.w -= t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple4f t1) {
/* 271 */     this.x = -t1.x;
/* 272 */     this.y = -t1.y;
/* 273 */     this.z = -t1.z;
/* 274 */     this.w = -t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 283 */     this.x = -this.x;
/* 284 */     this.y = -this.y;
/* 285 */     this.z = -this.z;
/* 286 */     this.w = -this.w;
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
/*     */   public final void scale(float s, Tuple4f t1) {
/* 298 */     this.x = s * t1.x;
/* 299 */     this.y = s * t1.y;
/* 300 */     this.z = s * t1.z;
/* 301 */     this.w = s * t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(float s) {
/* 312 */     this.x *= s;
/* 313 */     this.y *= s;
/* 314 */     this.z *= s;
/* 315 */     this.w *= s;
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
/*     */   public final void scaleAdd(float s, Tuple4f t1, Tuple4f t2) {
/* 328 */     this.x = s * t1.x + t2.x;
/* 329 */     this.y = s * t1.y + t2.y;
/* 330 */     this.z = s * t1.z + t2.z;
/* 331 */     this.w = s * t1.w + t2.w;
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
/*     */   public final void scaleAdd(float s, Tuple4f t1) {
/* 343 */     this.x = s * this.x + t1.x;
/* 344 */     this.y = s * this.y + t1.y;
/* 345 */     this.z = s * this.z + t1.z;
/* 346 */     this.w = s * this.w + t1.w;
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
/* 358 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Tuple4f t1) {
/*     */     try {
/* 370 */       return (this.x == t1.x && this.y == t1.y && this.z == t1.z && 
/* 371 */         this.w == t1.w);
/*     */     } catch (NullPointerException e2) {
/* 373 */       return false;
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
/* 387 */     try { Tuple4f t2 = (Tuple4f)t1;
/* 388 */       return (this.x == t2.x && this.y == t2.y && 
/* 389 */         this.z == t2.z && this.w == t2.w); }
/*     */     catch (NullPointerException e2)
/* 391 */     { return false; }
/* 392 */     catch (ClassCastException e1) { return false; }
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
/*     */   public boolean epsilonEquals(Tuple4f t1, float epsilon) {
/* 410 */     float diff = this.x - t1.x;
/* 411 */     if (Float.isNaN(diff)) return false; 
/* 412 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 414 */     diff = this.y - t1.y;
/* 415 */     if (Float.isNaN(diff)) return false; 
/* 416 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 418 */     diff = this.z - t1.z;
/* 419 */     if (Float.isNaN(diff)) return false; 
/* 420 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 422 */     diff = this.w - t1.w;
/* 423 */     if (Float.isNaN(diff)) return false; 
/* 424 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 426 */     return true;
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
/* 440 */     long bits = 1L;
/* 441 */     bits = VecMathUtil.hashFloatBits(bits, this.x);
/* 442 */     bits = VecMathUtil.hashFloatBits(bits, this.y);
/* 443 */     bits = VecMathUtil.hashFloatBits(bits, this.z);
/* 444 */     bits = VecMathUtil.hashFloatBits(bits, this.w);
/* 445 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public final void clamp(float min, float max, Tuple4f t) {
/* 458 */     if (t.x > max) {
/* 459 */       this.x = max;
/* 460 */     } else if (t.x < min) {
/* 461 */       this.x = min;
/*     */     } else {
/* 463 */       this.x = t.x;
/*     */     } 
/*     */     
/* 466 */     if (t.y > max) {
/* 467 */       this.y = max;
/* 468 */     } else if (t.y < min) {
/* 469 */       this.y = min;
/*     */     } else {
/* 471 */       this.y = t.y;
/*     */     } 
/*     */     
/* 474 */     if (t.z > max) {
/* 475 */       this.z = max;
/* 476 */     } else if (t.z < min) {
/* 477 */       this.z = min;
/*     */     } else {
/* 479 */       this.z = t.z;
/*     */     } 
/*     */     
/* 482 */     if (t.w > max) {
/* 483 */       this.w = max;
/* 484 */     } else if (t.w < min) {
/* 485 */       this.w = min;
/*     */     } else {
/* 487 */       this.w = t.w;
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
/*     */   public final void clampMin(float min, Tuple4f t) {
/* 501 */     if (t.x < min) {
/* 502 */       this.x = min;
/*     */     } else {
/* 504 */       this.x = t.x;
/*     */     } 
/*     */     
/* 507 */     if (t.y < min) {
/* 508 */       this.y = min;
/*     */     } else {
/* 510 */       this.y = t.y;
/*     */     } 
/*     */     
/* 513 */     if (t.z < min) {
/* 514 */       this.z = min;
/*     */     } else {
/* 516 */       this.z = t.z;
/*     */     } 
/*     */     
/* 519 */     if (t.w < min) {
/* 520 */       this.w = min;
/*     */     } else {
/* 522 */       this.w = t.w;
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
/*     */   public final void clampMax(float max, Tuple4f t) {
/* 537 */     if (t.x > max) {
/* 538 */       this.x = max;
/*     */     } else {
/* 540 */       this.x = t.x;
/*     */     } 
/*     */     
/* 543 */     if (t.y > max) {
/* 544 */       this.y = max;
/*     */     } else {
/* 546 */       this.y = t.y;
/*     */     } 
/*     */     
/* 549 */     if (t.z > max) {
/* 550 */       this.z = max;
/*     */     } else {
/* 552 */       this.z = t.z;
/*     */     } 
/*     */     
/* 555 */     if (t.w > max) {
/* 556 */       this.w = max;
/*     */     } else {
/* 558 */       this.w = t.z;
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
/*     */   public final void absolute(Tuple4f t) {
/* 571 */     this.x = Math.abs(t.x);
/* 572 */     this.y = Math.abs(t.y);
/* 573 */     this.z = Math.abs(t.z);
/* 574 */     this.w = Math.abs(t.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(float min, float max) {
/* 585 */     if (this.x > max) {
/* 586 */       this.x = max;
/* 587 */     } else if (this.x < min) {
/* 588 */       this.x = min;
/*     */     } 
/*     */     
/* 591 */     if (this.y > max) {
/* 592 */       this.y = max;
/* 593 */     } else if (this.y < min) {
/* 594 */       this.y = min;
/*     */     } 
/*     */     
/* 597 */     if (this.z > max) {
/* 598 */       this.z = max;
/* 599 */     } else if (this.z < min) {
/* 600 */       this.z = min;
/*     */     } 
/*     */     
/* 603 */     if (this.w > max) {
/* 604 */       this.w = max;
/* 605 */     } else if (this.w < min) {
/* 606 */       this.w = min;
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
/*     */   public final void clampMin(float min) {
/* 618 */     if (this.x < min) this.x = min; 
/* 619 */     if (this.y < min) this.y = min; 
/* 620 */     if (this.z < min) this.z = min; 
/* 621 */     if (this.w < min) this.w = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max) {
/* 632 */     if (this.x > max) this.x = max; 
/* 633 */     if (this.y > max) this.y = max; 
/* 634 */     if (this.z > max) this.z = max; 
/* 635 */     if (this.w > max) this.w = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 645 */     this.x = Math.abs(this.x);
/* 646 */     this.y = Math.abs(this.y);
/* 647 */     this.z = Math.abs(this.z);
/* 648 */     this.w = Math.abs(this.w);
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
/*     */   public void interpolate(Tuple4f t1, Tuple4f t2, float alpha) {
/* 661 */     this.x = (1.0F - alpha) * t1.x + alpha * t2.x;
/* 662 */     this.y = (1.0F - alpha) * t1.y + alpha * t2.y;
/* 663 */     this.z = (1.0F - alpha) * t1.z + alpha * t2.z;
/* 664 */     this.w = (1.0F - alpha) * t1.w + alpha * t2.w;
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
/*     */   public void interpolate(Tuple4f t1, float alpha) {
/* 677 */     this.x = (1.0F - alpha) * this.x + alpha * t1.x;
/* 678 */     this.y = (1.0F - alpha) * this.y + alpha * t1.y;
/* 679 */     this.z = (1.0F - alpha) * this.z + alpha * t1.z;
/* 680 */     this.w = (1.0F - alpha) * this.w + alpha * t1.w;
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
/*     */   public Object clone() {
/*     */     try {
/* 696 */       return super.clone();
/* 697 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 699 */       throw new InternalError();
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
/*     */   public final float getX() {
/* 711 */     return this.x;
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
/*     */   public final void setX(float x) {
/* 723 */     this.x = x;
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
/*     */   public final float getY() {
/* 735 */     return this.y;
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
/*     */   public final void setY(float y) {
/* 747 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getZ() {
/* 758 */     return this.z;
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
/*     */   public final void setZ(float z) {
/* 770 */     this.z = z;
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
/*     */   public final float getW() {
/* 782 */     return this.w;
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
/*     */   public final void setW(float w) {
/* 794 */     this.w = w;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Tuple4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */