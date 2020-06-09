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
/*     */ public abstract class Tuple2i
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -3555701650170169638L;
/*     */   public int x;
/*     */   public int y;
/*     */   
/*     */   public Tuple2i(int x, int y) {
/*  58 */     this.x = x;
/*  59 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2i(int[] t) {
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
/*     */   public Tuple2i(Tuple2i t1) {
/*  79 */     this.x = t1.x;
/*  80 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tuple2i() {
/*  88 */     this.x = 0;
/*  89 */     this.y = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(int x, int y) {
/* 100 */     this.x = x;
/* 101 */     this.y = y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(int[] t) {
/* 111 */     this.x = t[0];
/* 112 */     this.y = t[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2i t1) {
/* 121 */     this.x = t1.x;
/* 122 */     this.y = t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(int[] t) {
/* 131 */     t[0] = this.x;
/* 132 */     t[1] = this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(Tuple2i t) {
/* 141 */     t.x = this.x;
/* 142 */     t.y = this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2i t1, Tuple2i t2) {
/* 152 */     t1.x += t2.x;
/* 153 */     t1.y += t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void add(Tuple2i t1) {
/* 162 */     this.x += t1.x;
/* 163 */     this.y += t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple2i t1, Tuple2i t2) {
/* 174 */     t1.x -= t2.x;
/* 175 */     t1.y -= t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void sub(Tuple2i t1) {
/* 185 */     this.x -= t1.x;
/* 186 */     this.y -= t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate(Tuple2i t1) {
/* 195 */     this.x = -t1.x;
/* 196 */     this.y = -t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 204 */     this.x = -this.x;
/* 205 */     this.y = -this.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(int s, Tuple2i t1) {
/* 216 */     this.x = s * t1.x;
/* 217 */     this.y = s * t1.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scale(int s) {
/* 227 */     this.x *= s;
/* 228 */     this.y *= s;
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
/*     */   public final void scaleAdd(int s, Tuple2i t1, Tuple2i t2) {
/* 240 */     this.x = s * t1.x + t2.x;
/* 241 */     this.y = s * t1.y + t2.y;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void scaleAdd(int s, Tuple2i t1) {
/* 252 */     this.x = s * this.x + t1.x;
/* 253 */     this.y = s * this.y + t1.y;
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
/* 264 */     return "(" + this.x + ", " + this.y + ")";
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
/* 277 */       Tuple2i t2 = (Tuple2i)t1;
/* 278 */       return (this.x == t2.x && this.y == t2.y);
/*     */     }
/* 280 */     catch (NullPointerException e2) {
/* 281 */       return false;
/*     */     }
/* 283 */     catch (ClassCastException e1) {
/* 284 */       return false;
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
/* 299 */     long bits = 1L;
/* 300 */     bits = 31L * bits + this.x;
/* 301 */     bits = 31L * bits + this.y;
/* 302 */     return (int)(bits ^ bits >> 32L);
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
/*     */   public final void clamp(int min, int max, Tuple2i t) {
/* 314 */     if (t.x > max) {
/* 315 */       this.x = max;
/* 316 */     } else if (t.x < min) {
/* 317 */       this.x = min;
/*     */     } else {
/* 319 */       this.x = t.x;
/*     */     } 
/*     */     
/* 322 */     if (t.y > max) {
/* 323 */       this.y = max;
/* 324 */     } else if (t.y < min) {
/* 325 */       this.y = min;
/*     */     } else {
/* 327 */       this.y = t.y;
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
/*     */   public final void clampMin(int min, Tuple2i t) {
/* 339 */     if (t.x < min) {
/* 340 */       this.x = min;
/*     */     } else {
/* 342 */       this.x = t.x;
/*     */     } 
/*     */     
/* 345 */     if (t.y < min) {
/* 346 */       this.y = min;
/*     */     } else {
/* 348 */       this.y = t.y;
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
/*     */   public final void clampMax(int max, Tuple2i t) {
/* 360 */     if (t.x > max) {
/* 361 */       this.x = max;
/*     */     } else {
/* 363 */       this.x = t.x;
/*     */     } 
/*     */     
/* 366 */     if (t.y > max) {
/* 367 */       this.y = max;
/*     */     } else {
/* 369 */       this.y = t.y;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute(Tuple2i t) {
/* 380 */     this.x = Math.abs(t.x);
/* 381 */     this.y = Math.abs(t.y);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clamp(int min, int max) {
/* 391 */     if (this.x > max) {
/* 392 */       this.x = max;
/* 393 */     } else if (this.x < min) {
/* 394 */       this.x = min;
/*     */     } 
/*     */     
/* 397 */     if (this.y > max) {
/* 398 */       this.y = max;
/* 399 */     } else if (this.y < min) {
/* 400 */       this.y = min;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMin(int min) {
/* 410 */     if (this.x < min) {
/* 411 */       this.x = min;
/*     */     }
/* 413 */     if (this.y < min) {
/* 414 */       this.y = min;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void clampMax(int max) {
/* 423 */     if (this.x > max) {
/* 424 */       this.x = max;
/*     */     }
/* 426 */     if (this.y > max) {
/* 427 */       this.y = max;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void absolute() {
/* 435 */     this.x = Math.abs(this.x);
/* 436 */     this.y = Math.abs(this.y);
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
/*     */   public Object clone() {
/*     */     try {
/* 450 */       return super.clone();
/* 451 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 453 */       throw new InternalError();
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
/* 466 */     return this.x;
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
/* 478 */     this.x = x;
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
/* 490 */     return this.y;
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
/* 502 */     this.y = y;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Tuple2i.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */