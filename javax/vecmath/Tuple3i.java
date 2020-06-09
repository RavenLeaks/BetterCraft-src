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
/*     */ public abstract class Tuple3i
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -732740491767276200L;
/*     */   public int x;
/*     */   public int y;
/*     */   public int z;
/*     */   
/*     */   public Tuple3i(int x, int y, int z) {
/*  64 */     this.x = x;
/*  65 */     this.y = y;
/*  66 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3i(int[] t) {
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
/*     */   public Tuple3i(Tuple3i t1) {
/*  87 */     this.x = t1.x;
/*  88 */     this.y = t1.y;
/*  89 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple3i() {
/*  97 */     this.x = 0;
/*  98 */     this.y = 0;
/*  99 */     this.z = 0;
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
/*     */   public final void set(int x, int y, int z) {
/* 111 */     this.x = x;
/* 112 */     this.y = y;
/* 113 */     this.z = z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(int[] t) {
/* 123 */     this.x = t[0];
/* 124 */     this.y = t[1];
/* 125 */     this.z = t[2];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3i t1) {
/* 134 */     this.x = t1.x;
/* 135 */     this.y = t1.y;
/* 136 */     this.z = t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(int[] t) {
/* 145 */     t[0] = this.x;
/* 146 */     t[1] = this.y;
/* 147 */     t[2] = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple3i t) {
/* 156 */     t.x = this.x;
/* 157 */     t.y = this.y;
/* 158 */     t.z = this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3i t1, Tuple3i t2) {
/* 168 */     t1.x += t2.x;
/* 169 */     t1.y += t2.y;
/* 170 */     t1.z += t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple3i t1) {
/* 179 */     this.x += t1.x;
/* 180 */     this.y += t1.y;
/* 181 */     this.z += t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3i t1, Tuple3i t2) {
/* 192 */     t1.x -= t2.x;
/* 193 */     t1.y -= t2.y;
/* 194 */     t1.z -= t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple3i t1) {
/* 204 */     this.x -= t1.x;
/* 205 */     this.y -= t1.y;
/* 206 */     this.z -= t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple3i t1) {
/* 215 */     this.x = -t1.x;
/* 216 */     this.y = -t1.y;
/* 217 */     this.z = -t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 225 */     this.x = -this.x;
/* 226 */     this.y = -this.y;
/* 227 */     this.z = -this.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(int s, Tuple3i t1) {
/* 238 */     this.x = s * t1.x;
/* 239 */     this.y = s * t1.y;
/* 240 */     this.z = s * t1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(int s) {
/* 250 */     this.x *= s;
/* 251 */     this.y *= s;
/* 252 */     this.z *= s;
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
/*     */   public final void scaleAdd(int s, Tuple3i t1, Tuple3i t2) {
/* 264 */     this.x = s * t1.x + t2.x;
/* 265 */     this.y = s * t1.y + t2.y;
/* 266 */     this.z = s * t1.z + t2.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(int s, Tuple3i t1) {
/* 277 */     this.x = s * this.x + t1.x;
/* 278 */     this.y = s * this.y + t1.y;
/* 279 */     this.z = s * this.z + t1.z;
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
/* 290 */     return "(" + this.x + ", " + this.y + ", " + this.z + ")";
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
/*     */     try {
/* 303 */       Tuple3i t2 = (Tuple3i)t1;
/* 304 */       return (this.x == t2.x && this.y == t2.y && this.z == t2.z);
/*     */     }
/* 306 */     catch (NullPointerException e2) {
/* 307 */       return false;
/*     */     }
/* 309 */     catch (ClassCastException e1) {
/* 310 */       return false;
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
/* 325 */     long bits = 1L;
/* 326 */     bits = 31L * bits + this.x;
/* 327 */     bits = 31L * bits + this.y;
/* 328 */     bits = 31L * bits + this.z;
/* 329 */     return (int)(bits ^ bits >> 32L);
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
/*     */   public final void clamp(int min, int max, Tuple3i t) {
/* 341 */     if (t.x > max) {
/* 342 */       this.x = max;
/* 343 */     } else if (t.x < min) {
/* 344 */       this.x = min;
/*     */     } else {
/* 346 */       this.x = t.x;
/*     */     } 
/*     */     
/* 349 */     if (t.y > max) {
/* 350 */       this.y = max;
/* 351 */     } else if (t.y < min) {
/* 352 */       this.y = min;
/*     */     } else {
/* 354 */       this.y = t.y;
/*     */     } 
/*     */     
/* 357 */     if (t.z > max) {
/* 358 */       this.z = max;
/* 359 */     } else if (t.z < min) {
/* 360 */       this.z = min;
/*     */     } else {
/* 362 */       this.z = t.z;
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
/*     */   public final void clampMin(int min, Tuple3i t) {
/* 374 */     if (t.x < min) {
/* 375 */       this.x = min;
/*     */     } else {
/* 377 */       this.x = t.x;
/*     */     } 
/*     */     
/* 380 */     if (t.y < min) {
/* 381 */       this.y = min;
/*     */     } else {
/* 383 */       this.y = t.y;
/*     */     } 
/*     */     
/* 386 */     if (t.z < min) {
/* 387 */       this.z = min;
/*     */     } else {
/* 389 */       this.z = t.z;
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
/*     */   public final void clampMax(int max, Tuple3i t) {
/* 401 */     if (t.x > max) {
/* 402 */       this.x = max;
/*     */     } else {
/* 404 */       this.x = t.x;
/*     */     } 
/*     */     
/* 407 */     if (t.y > max) {
/* 408 */       this.y = max;
/*     */     } else {
/* 410 */       this.y = t.y;
/*     */     } 
/*     */     
/* 413 */     if (t.z > max) {
/* 414 */       this.z = max;
/*     */     } else {
/* 416 */       this.z = t.z;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute(Tuple3i t) {
/* 427 */     this.x = Math.abs(t.x);
/* 428 */     this.y = Math.abs(t.y);
/* 429 */     this.z = Math.abs(t.z);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(int min, int max) {
/* 439 */     if (this.x > max) {
/* 440 */       this.x = max;
/* 441 */     } else if (this.x < min) {
/* 442 */       this.x = min;
/*     */     } 
/*     */     
/* 445 */     if (this.y > max) {
/* 446 */       this.y = max;
/* 447 */     } else if (this.y < min) {
/* 448 */       this.y = min;
/*     */     } 
/*     */     
/* 451 */     if (this.z > max) {
/* 452 */       this.z = max;
/* 453 */     } else if (this.z < min) {
/* 454 */       this.z = min;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(int min) {
/* 464 */     if (this.x < min) {
/* 465 */       this.x = min;
/*     */     }
/* 467 */     if (this.y < min) {
/* 468 */       this.y = min;
/*     */     }
/* 470 */     if (this.z < min) {
/* 471 */       this.z = min;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(int max) {
/* 480 */     if (this.x > max) {
/* 481 */       this.x = max;
/*     */     }
/* 483 */     if (this.y > max) {
/* 484 */       this.y = max;
/*     */     }
/* 486 */     if (this.z > max) {
/* 487 */       this.z = max;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 495 */     this.x = Math.abs(this.x);
/* 496 */     this.y = Math.abs(this.y);
/* 497 */     this.z = Math.abs(this.z);
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
/* 512 */       return super.clone();
/* 513 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 515 */       throw new InternalError();
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
/*     */   public final int getX() {
/* 528 */     return this.x;
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
/* 540 */     this.x = x;
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
/* 552 */     return this.y;
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
/* 564 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getZ() {
/* 574 */     return this.z;
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
/* 586 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Tuple3i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */