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
/*     */ public abstract class Tuple2f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 9011180388985266884L;
/*     */   public float x;
/*     */   public float y;
/*     */   
/*     */   public Tuple2f(float x, float y) {
/*  57 */     this.x = x;
/*  58 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2f(float[] t) {
/*  68 */     this.x = t[0];
/*  69 */     this.y = t[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2f(Tuple2f t1) {
/*  79 */     this.x = t1.x;
/*  80 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2f(Tuple2d t1) {
/*  90 */     this.x = (float)t1.x;
/*  91 */     this.y = (float)t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2f() {
/* 100 */     this.x = 0.0F;
/* 101 */     this.y = 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(float x, float y) {
/* 112 */     this.x = x;
/* 113 */     this.y = y;
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
/* 124 */     this.x = t[0];
/* 125 */     this.y = t[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2f t1) {
/* 135 */     this.x = t1.x;
/* 136 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2d t1) {
/* 146 */     this.x = (float)t1.x;
/* 147 */     this.y = (float)t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] t) {
/* 157 */     t[0] = this.x;
/* 158 */     t[1] = this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2f t1, Tuple2f t2) {
/* 169 */     t1.x += t2.x;
/* 170 */     t1.y += t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2f t1) {
/* 180 */     this.x += t1.x;
/* 181 */     this.y += t1.y;
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
/*     */   public final void sub(Tuple2f t1, Tuple2f t2) {
/* 193 */     t1.x -= t2.x;
/* 194 */     t1.y -= t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple2f t1) {
/* 205 */     this.x -= t1.x;
/* 206 */     this.y -= t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple2f t1) {
/* 216 */     this.x = -t1.x;
/* 217 */     this.y = -t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 226 */     this.x = -this.x;
/* 227 */     this.y = -this.y;
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
/*     */   public final void scale(float s, Tuple2f t1) {
/* 239 */     this.x = s * t1.x;
/* 240 */     this.y = s * t1.y;
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
/* 251 */     this.x *= s;
/* 252 */     this.y *= s;
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
/*     */   public final void scaleAdd(float s, Tuple2f t1, Tuple2f t2) {
/* 265 */     this.x = s * t1.x + t2.x;
/* 266 */     this.y = s * t1.y + t2.y;
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
/*     */   public final void scaleAdd(float s, Tuple2f t1) {
/* 278 */     this.x = s * this.x + t1.x;
/* 279 */     this.y = s * this.y + t1.y;
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
/* 294 */     long bits = 1L;
/* 295 */     bits = VecMathUtil.hashFloatBits(bits, this.x);
/* 296 */     bits = VecMathUtil.hashFloatBits(bits, this.y);
/* 297 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public boolean equals(Tuple2f t1) {
/*     */     try {
/* 310 */       return (this.x == t1.x && this.y == t1.y);
/*     */     } catch (NullPointerException e2) {
/* 312 */       return false;
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
/* 327 */     try { Tuple2f t2 = (Tuple2f)t1;
/* 328 */       return (this.x == t2.x && this.y == t2.y); }
/*     */     catch (NullPointerException e2)
/* 330 */     { return false; }
/* 331 */     catch (ClassCastException e1) { return false; }
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
/*     */   public boolean epsilonEquals(Tuple2f t1, float epsilon) {
/* 348 */     float diff = this.x - t1.x;
/* 349 */     if (Float.isNaN(diff)) return false; 
/* 350 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 352 */     diff = this.y - t1.y;
/* 353 */     if (Float.isNaN(diff)) return false; 
/* 354 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 356 */     return true;
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
/* 367 */     return "(" + this.x + ", " + this.y + ")";
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
/*     */   public final void clamp(float min, float max, Tuple2f t) {
/* 380 */     if (t.x > max) {
/* 381 */       this.x = max;
/* 382 */     } else if (t.x < min) {
/* 383 */       this.x = min;
/*     */     } else {
/* 385 */       this.x = t.x;
/*     */     } 
/*     */     
/* 388 */     if (t.y > max) {
/* 389 */       this.y = max;
/* 390 */     } else if (t.y < min) {
/* 391 */       this.y = min;
/*     */     } else {
/* 393 */       this.y = t.y;
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
/*     */   public final void clampMin(float min, Tuple2f t) {
/* 407 */     if (t.x < min) {
/* 408 */       this.x = min;
/*     */     } else {
/* 410 */       this.x = t.x;
/*     */     } 
/*     */     
/* 413 */     if (t.y < min) {
/* 414 */       this.y = min;
/*     */     } else {
/* 416 */       this.y = t.y;
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
/*     */   public final void clampMax(float max, Tuple2f t) {
/* 430 */     if (t.x > max) {
/* 431 */       this.x = max;
/*     */     } else {
/* 433 */       this.x = t.x;
/*     */     } 
/*     */     
/* 436 */     if (t.y > max) {
/* 437 */       this.y = max;
/*     */     } else {
/* 439 */       this.y = t.y;
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
/*     */   public final void absolute(Tuple2f t) {
/* 452 */     this.x = Math.abs(t.x);
/* 453 */     this.y = Math.abs(t.y);
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
/* 465 */     if (this.x > max) {
/* 466 */       this.x = max;
/* 467 */     } else if (this.x < min) {
/* 468 */       this.x = min;
/*     */     } 
/*     */     
/* 471 */     if (this.y > max) {
/* 472 */       this.y = max;
/* 473 */     } else if (this.y < min) {
/* 474 */       this.y = min;
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
/* 486 */     if (this.x < min) this.x = min; 
/* 487 */     if (this.y < min) this.y = min;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(float max) {
/* 497 */     if (this.x > max) this.x = max; 
/* 498 */     if (this.y > max) this.y = max;
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 507 */     this.x = Math.abs(this.x);
/* 508 */     this.y = Math.abs(this.y);
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
/*     */   public final void interpolate(Tuple2f t1, Tuple2f t2, float alpha) {
/* 521 */     this.x = (1.0F - alpha) * t1.x + alpha * t2.x;
/* 522 */     this.y = (1.0F - alpha) * t1.y + alpha * t2.y;
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
/*     */   public final void interpolate(Tuple2f t1, float alpha) {
/* 536 */     this.x = (1.0F - alpha) * this.x + alpha * t1.x;
/* 537 */     this.y = (1.0F - alpha) * this.y + alpha * t1.y;
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
/* 553 */       return super.clone();
/* 554 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 556 */       throw new InternalError();
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
/* 569 */     return this.x;
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
/* 581 */     this.x = x;
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
/* 593 */     return this.y;
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
/* 605 */     this.y = y;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Tuple2f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */