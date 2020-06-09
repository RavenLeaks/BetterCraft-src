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
/*     */ public abstract class Tuple4i
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = 8064614250942616720L;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   public int w;
/*     */   
/*     */   public Tuple4i(int x, int y, int z, int w) {
/*  70 */     this.x = x;
/*  71 */     this.y = y;
/*  72 */     this.z = z;
/*  73 */     this.w = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple4i(int[] t) {
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
/*     */   public Tuple4i(Tuple4i t1) {
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
/*     */   public Tuple4i() {
/* 106 */     this.x = 0;
/* 107 */     this.y = 0;
/* 108 */     this.z = 0;
/* 109 */     this.w = 0;
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
/*     */   public final void set(int x, int y, int z, int w) {
/* 122 */     this.x = x;
/* 123 */     this.y = y;
/* 124 */     this.z = z;
/* 125 */     this.w = w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(int[] t) {
/* 135 */     this.x = t[0];
/* 136 */     this.y = t[1];
/* 137 */     this.z = t[2];
/* 138 */     this.w = t[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4i t1) {
/* 147 */     this.x = t1.x;
/* 148 */     this.y = t1.y;
/* 149 */     this.z = t1.z;
/* 150 */     this.w = t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(int[] t) {
/* 159 */     t[0] = this.x;
/* 160 */     t[1] = this.y;
/* 161 */     t[2] = this.z;
/* 162 */     t[3] = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple4i t) {
/* 171 */     t.x = this.x;
/* 172 */     t.y = this.y;
/* 173 */     t.z = this.z;
/* 174 */     t.w = this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4i t1, Tuple4i t2) {
/* 184 */     t1.x += t2.x;
/* 185 */     t1.y += t2.y;
/* 186 */     t1.z += t2.z;
/* 187 */     t1.w += t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple4i t1) {
/* 196 */     this.x += t1.x;
/* 197 */     this.y += t1.y;
/* 198 */     this.z += t1.z;
/* 199 */     this.w += t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple4i t1, Tuple4i t2) {
/* 210 */     t1.x -= t2.x;
/* 211 */     t1.y -= t2.y;
/* 212 */     t1.z -= t2.z;
/* 213 */     t1.w -= t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple4i t1) {
/* 223 */     this.x -= t1.x;
/* 224 */     this.y -= t1.y;
/* 225 */     this.z -= t1.z;
/* 226 */     this.w -= t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple4i t1) {
/* 235 */     this.x = -t1.x;
/* 236 */     this.y = -t1.y;
/* 237 */     this.z = -t1.z;
/* 238 */     this.w = -t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 246 */     this.x = -this.x;
/* 247 */     this.y = -this.y;
/* 248 */     this.z = -this.z;
/* 249 */     this.w = -this.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(int s, Tuple4i t1) {
/* 260 */     this.x = s * t1.x;
/* 261 */     this.y = s * t1.y;
/* 262 */     this.z = s * t1.z;
/* 263 */     this.w = s * t1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(int s) {
/* 273 */     this.x *= s;
/* 274 */     this.y *= s;
/* 275 */     this.z *= s;
/* 276 */     this.w *= s;
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
/*     */   public final void scaleAdd(int s, Tuple4i t1, Tuple4i t2) {
/* 288 */     this.x = s * t1.x + t2.x;
/* 289 */     this.y = s * t1.y + t2.y;
/* 290 */     this.z = s * t1.z + t2.z;
/* 291 */     this.w = s * t1.w + t2.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(int s, Tuple4i t1) {
/* 302 */     this.x = s * this.x + t1.x;
/* 303 */     this.y = s * this.y + t1.y;
/* 304 */     this.z = s * this.z + t1.z;
/* 305 */     this.w = s * this.w + t1.w;
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
/* 316 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.w + ")";
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
/*     */     try {
/* 330 */       Tuple4i t2 = (Tuple4i)t1;
/* 331 */       return (this.x == t2.x && this.y == t2.y && 
/* 332 */         this.z == t2.z && this.w == t2.w);
/*     */     }
/* 334 */     catch (NullPointerException e2) {
/* 335 */       return false;
/*     */     }
/* 337 */     catch (ClassCastException e1) {
/* 338 */       return false;
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
/*     */   public int hashCode() {
/* 353 */     long bits = 1L;
/* 354 */     bits = 31L * bits + this.x;
/* 355 */     bits = 31L * bits + this.y;
/* 356 */     bits = 31L * bits + this.z;
/* 357 */     bits = 31L * bits + this.w;
/* 358 */     return (int)(bits ^ bits >> 32L);
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
/*     */   public final void clamp(int min, int max, Tuple4i t) {
/* 370 */     if (t.x > max) {
/* 371 */       this.x = max;
/* 372 */     } else if (t.x < min) {
/* 373 */       this.x = min;
/*     */     } else {
/* 375 */       this.x = t.x;
/*     */     } 
/*     */     
/* 378 */     if (t.y > max) {
/* 379 */       this.y = max;
/* 380 */     } else if (t.y < min) {
/* 381 */       this.y = min;
/*     */     } else {
/* 383 */       this.y = t.y;
/*     */     } 
/*     */     
/* 386 */     if (t.z > max) {
/* 387 */       this.z = max;
/* 388 */     } else if (t.z < min) {
/* 389 */       this.z = min;
/*     */     } else {
/* 391 */       this.z = t.z;
/*     */     } 
/*     */     
/* 394 */     if (t.w > max) {
/* 395 */       this.w = max;
/* 396 */     } else if (t.w < min) {
/* 397 */       this.w = min;
/*     */     } else {
/* 399 */       this.w = t.w;
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
/*     */   public final void clampMin(int min, Tuple4i t) {
/* 411 */     if (t.x < min) {
/* 412 */       this.x = min;
/*     */     } else {
/* 414 */       this.x = t.x;
/*     */     } 
/*     */     
/* 417 */     if (t.y < min) {
/* 418 */       this.y = min;
/*     */     } else {
/* 420 */       this.y = t.y;
/*     */     } 
/*     */     
/* 423 */     if (t.z < min) {
/* 424 */       this.z = min;
/*     */     } else {
/* 426 */       this.z = t.z;
/*     */     } 
/*     */     
/* 429 */     if (t.w < min) {
/* 430 */       this.w = min;
/*     */     } else {
/* 432 */       this.w = t.w;
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
/*     */   public final void clampMax(int max, Tuple4i t) {
/* 446 */     if (t.x > max) {
/* 447 */       this.x = max;
/*     */     } else {
/* 449 */       this.x = t.x;
/*     */     } 
/*     */     
/* 452 */     if (t.y > max) {
/* 453 */       this.y = max;
/*     */     } else {
/* 455 */       this.y = t.y;
/*     */     } 
/*     */     
/* 458 */     if (t.z > max) {
/* 459 */       this.z = max;
/*     */     } else {
/* 461 */       this.z = t.z;
/*     */     } 
/*     */     
/* 464 */     if (t.w > max) {
/* 465 */       this.w = max;
/*     */     } else {
/* 467 */       this.w = t.z;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute(Tuple4i t) {
/* 478 */     this.x = Math.abs(t.x);
/* 479 */     this.y = Math.abs(t.y);
/* 480 */     this.z = Math.abs(t.z);
/* 481 */     this.w = Math.abs(t.w);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(int min, int max) {
/* 491 */     if (this.x > max) {
/* 492 */       this.x = max;
/* 493 */     } else if (this.x < min) {
/* 494 */       this.x = min;
/*     */     } 
/*     */     
/* 497 */     if (this.y > max) {
/* 498 */       this.y = max;
/* 499 */     } else if (this.y < min) {
/* 500 */       this.y = min;
/*     */     } 
/*     */     
/* 503 */     if (this.z > max) {
/* 504 */       this.z = max;
/* 505 */     } else if (this.z < min) {
/* 506 */       this.z = min;
/*     */     } 
/*     */     
/* 509 */     if (this.w > max) {
/* 510 */       this.w = max;
/* 511 */     } else if (this.w < min) {
/* 512 */       this.w = min;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(int min) {
/* 522 */     if (this.x < min) {
/* 523 */       this.x = min;
/*     */     }
/* 525 */     if (this.y < min) {
/* 526 */       this.y = min;
/*     */     }
/* 528 */     if (this.z < min) {
/* 529 */       this.z = min;
/*     */     }
/* 531 */     if (this.w < min) {
/* 532 */       this.w = min;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(int max) {
/* 541 */     if (this.x > max) {
/* 542 */       this.x = max;
/*     */     }
/* 544 */     if (this.y > max) {
/* 545 */       this.y = max;
/*     */     }
/* 547 */     if (this.z > max) {
/* 548 */       this.z = max;
/*     */     }
/* 550 */     if (this.w > max) {
/* 551 */       this.w = max;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 559 */     this.x = Math.abs(this.x);
/* 560 */     this.y = Math.abs(this.y);
/* 561 */     this.z = Math.abs(this.z);
/* 562 */     this.w = Math.abs(this.w);
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
/* 577 */       return super.clone();
/* 578 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 580 */       throw new InternalError();
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
/*     */   public final int getX() {
/* 594 */     return this.x;
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
/*     */   public final void setX(int x) {
/* 606 */     this.x = x;
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
/*     */   public final int getY() {
/* 618 */     return this.y;
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
/*     */   public final void setY(int y) {
/* 630 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getZ() {
/* 641 */     return this.z;
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
/*     */   public final void setZ(int z) {
/* 653 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getW() {
/* 663 */     return this.w;
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
/*     */   public final void setW(int w) {
/* 675 */     this.w = w;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Tuple4i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */