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
/*     */ public abstract class Tuple3f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 5019834619484343712L;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   
/*     */   public Tuple3f(float x, float y, float z) {
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
/*     */   
/*     */   public Tuple3f(float[] t) {
/*  75 */     this.x = t[0];
/*  76 */     this.y = t[1];
/*  77 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3f(Tuple3f t1) {
/*  87 */     this.x = t1.x;
/*  88 */     this.y = t1.y;
/*  89 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3f(Tuple3d t1) {
/*  99 */     this.x = (float)t1.x;
/* 100 */     this.y = (float)t1.y;
/* 101 */     this.z = (float)t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3f() {
/* 110 */     this.x = 0.0F;
/* 111 */     this.y = 0.0F;
/* 112 */     this.z = 0.0F;
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
/* 123 */     return "(" + this.x + ", " + this.y + ", " + this.z + ")";
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
/*     */   public final void set(float x, float y, float z) {
/* 135 */     this.x = x;
/* 136 */     this.y = y;
/* 137 */     this.z = z;
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
/* 148 */     this.x = t[0];
/* 149 */     this.y = t[1];
/* 150 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f t1) {
/* 160 */     this.x = t1.x;
/* 161 */     this.y = t1.y;
/* 162 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d t1) {
/* 172 */     this.x = (float)t1.x;
/* 173 */     this.y = (float)t1.y;
/* 174 */     this.z = (float)t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] t) {
/* 184 */     t[0] = this.x;
/* 185 */     t[1] = this.y;
/* 186 */     t[2] = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple3f t) {
/* 196 */     t.x = this.x;
/* 197 */     t.y = this.y;
/* 198 */     t.z = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3f t1, Tuple3f t2) {
/* 209 */     t1.x += t2.x;
/* 210 */     t1.y += t2.y;
/* 211 */     t1.z += t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3f t1) {
/* 221 */     this.x += t1.x;
/* 222 */     this.y += t1.y;
/* 223 */     this.z += t1.z;
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
/*     */   public final void sub(Tuple3f t1, Tuple3f t2) {
/* 235 */     t1.x -= t2.x;
/* 236 */     t1.y -= t2.y;
/* 237 */     t1.z -= t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3f t1) {
/* 248 */     this.x -= t1.x;
/* 249 */     this.y -= t1.y;
/* 250 */     this.z -= t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple3f t1) {
/* 260 */     this.x = -t1.x;
/* 261 */     this.y = -t1.y;
/* 262 */     this.z = -t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 271 */     this.x = -this.x;
/* 272 */     this.y = -this.y;
/* 273 */     this.z = -this.z;
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
/*     */   public final void scale(float s, Tuple3f t1) {
/* 285 */     this.x = s * t1.x;
/* 286 */     this.y = s * t1.y;
/* 287 */     this.z = s * t1.z;
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
/* 298 */     this.x *= s;
/* 299 */     this.y *= s;
/* 300 */     this.z *= s;
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
/*     */   public final void scaleAdd(float s, Tuple3f t1, Tuple3f t2) {
/* 313 */     this.x = s * t1.x + t2.x;
/* 314 */     this.y = s * t1.y + t2.y;
/* 315 */     this.z = s * t1.z + t2.z;
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
/*     */   public final void scaleAdd(float s, Tuple3f t1) {
/* 328 */     this.x = s * this.x + t1.x;
/* 329 */     this.y = s * this.y + t1.y;
/* 330 */     this.z = s * this.z + t1.z;
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
/*     */   public boolean equals(Tuple3f t1) {
/*     */     try {
/* 344 */       return (this.x == t1.x && this.y == t1.y && this.z == t1.z);
/*     */     } catch (NullPointerException e2) {
/* 346 */       return false;
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
/*     */   public boolean equals(Object t1) {
/*     */     
/* 359 */     try { Tuple3f t2 = (Tuple3f)t1;
/* 360 */       return (this.x == t2.x && this.y == t2.y && this.z == t2.z); }
/*     */     catch (NullPointerException e2)
/* 362 */     { return false; }
/* 363 */     catch (ClassCastException e1) { return false; }
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
/*     */   public boolean epsilonEquals(Tuple3f t1, float epsilon) {
/* 380 */     float diff = this.x - t1.x;
/* 381 */     if (Float.isNaN(diff)) return false; 
/* 382 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 384 */     diff = this.y - t1.y;
/* 385 */     if (Float.isNaN(diff)) return false; 
/* 386 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 388 */     diff = this.z - t1.z;
/* 389 */     if (Float.isNaN(diff)) return false; 
/* 390 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 392 */     return true;
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
/* 407 */     long bits = 1L;
/* 408 */     bits = VecMathUtil.hashFloatBits(bits, this.x);
/* 409 */     bits = VecMathUtil.hashFloatBits(bits, this.y);
/* 410 */     bits = VecMathUtil.hashFloatBits(bits, this.z);
/* 411 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public final void clamp(float min, float max, Tuple3f t) {
/* 425 */     if (t.x > max) {
/* 426 */       this.x = max;
/* 427 */     } else if (t.x < min) {
/* 428 */       this.x = min;
/*     */     } else {
/* 430 */       this.x = t.x;
/*     */     } 
/*     */     
/* 433 */     if (t.y > max) {
/* 434 */       this.y = max;
/* 435 */     } else if (t.y < min) {
/* 436 */       this.y = min;
/*     */     } else {
/* 438 */       this.y = t.y;
/*     */     } 
/*     */     
/* 441 */     if (t.z > max) {
/* 442 */       this.z = max;
/* 443 */     } else if (t.z < min) {
/* 444 */       this.z = min;
/*     */     } else {
/* 446 */       this.z = t.z;
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
/*     */   public final void clampMin(float min, Tuple3f t) {
/* 460 */     if (t.x < min) {
/* 461 */       this.x = min;
/*     */     } else {
/* 463 */       this.x = t.x;
/*     */     } 
/*     */     
/* 466 */     if (t.y < min) {
/* 467 */       this.y = min;
/*     */     } else {
/* 469 */       this.y = t.y;
/*     */     } 
/*     */     
/* 472 */     if (t.z < min) {
/* 473 */       this.z = min;
/*     */     } else {
/* 475 */       this.z = t.z;
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
/*     */   public final void clampMax(float max, Tuple3f t) {
/* 489 */     if (t.x > max) {
/* 490 */       this.x = max;
/*     */     } else {
/* 492 */       this.x = t.x;
/*     */     } 
/*     */     
/* 495 */     if (t.y > max) {
/* 496 */       this.y = max;
/*     */     } else {
/* 498 */       this.y = t.y;
/*     */     } 
/*     */     
/* 501 */     if (t.z > max) {
/* 502 */       this.z = max;
/*     */     } else {
/* 504 */       this.z = t.z;
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
/*     */   public final void absolute(Tuple3f t) {
/* 517 */     this.x = Math.abs(t.x);
/* 518 */     this.y = Math.abs(t.y);
/* 519 */     this.z = Math.abs(t.z);
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
/*     */   public final void clamp(float min, float max) {
/* 531 */     if (this.x > max) {
/* 532 */       this.x = max;
/* 533 */     } else if (this.x < min) {
/* 534 */       this.x = min;
/*     */     } 
/*     */     
/* 537 */     if (this.y > max) {
/* 538 */       this.y = max;
/* 539 */     } else if (this.y < min) {
/* 540 */       this.y = min;
/*     */     } 
/*     */     
/* 543 */     if (this.z > max) {
/* 544 */       this.z = max;
/* 545 */     } else if (this.z < min) {
/* 546 */       this.z = min;
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
/* 558 */     if (this.x < min) this.x = min; 
/* 559 */     if (this.y < min) this.y = min; 
/* 560 */     if (this.z < min) this.z = min;
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
/* 571 */     if (this.x > max) this.x = max; 
/* 572 */     if (this.y > max) this.y = max; 
/* 573 */     if (this.z > max) this.z = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 583 */     this.x = Math.abs(this.x);
/* 584 */     this.y = Math.abs(this.y);
/* 585 */     this.z = Math.abs(this.z);
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
/*     */   public final void interpolate(Tuple3f t1, Tuple3f t2, float alpha) {
/* 599 */     this.x = (1.0F - alpha) * t1.x + alpha * t2.x;
/* 600 */     this.y = (1.0F - alpha) * t1.y + alpha * t2.y;
/* 601 */     this.z = (1.0F - alpha) * t1.z + alpha * t2.z;
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
/*     */   public final void interpolate(Tuple3f t1, float alpha) {
/* 615 */     this.x = (1.0F - alpha) * this.x + alpha * t1.x;
/* 616 */     this.y = (1.0F - alpha) * this.y + alpha * t1.y;
/* 617 */     this.z = (1.0F - alpha) * this.z + alpha * t1.z;
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
/*     */   public Object clone() {
/*     */     try {
/* 634 */       return super.clone();
/* 635 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 637 */       throw new InternalError();
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
/*     */   public final float getX() {
/* 650 */     return this.x;
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
/* 662 */     this.x = x;
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
/* 674 */     return this.y;
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
/* 686 */     this.y = y;
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
/* 697 */     return this.z;
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
/* 709 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Tuple3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */