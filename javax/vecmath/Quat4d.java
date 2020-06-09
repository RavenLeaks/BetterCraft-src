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
/*     */ public class Quat4d
/*     */   extends Tuple4d
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 7577479888820201099L;
/*     */   static final double EPS = 1.0E-12D;
/*     */   static final double EPS2 = 1.0E-30D;
/*     */   static final double PIO2 = 1.57079632679D;
/*     */   
/*     */   public Quat4d(double x, double y, double z, double w) {
/*  54 */     double mag = 1.0D / Math.sqrt(x * x + y * y + z * z + w * w);
/*  55 */     this.x = x * mag;
/*  56 */     this.y = y * mag;
/*  57 */     this.z = z * mag;
/*  58 */     this.w = w * mag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d(double[] q) {
/*  69 */     double mag = 1.0D / Math.sqrt(q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3]);
/*  70 */     this.x = q[0] * mag;
/*  71 */     this.y = q[1] * mag;
/*  72 */     this.z = q[2] * mag;
/*  73 */     this.w = q[3] * mag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d(Quat4d q1) {
/*  83 */     super(q1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d(Quat4f q1) {
/*  92 */     super(q1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d(Tuple4f t1) {
/* 103 */     double mag = 1.0D / Math.sqrt((t1.x * t1.x + t1.y * t1.y + t1.z * t1.z + t1.w * t1.w));
/* 104 */     this.x = t1.x * mag;
/* 105 */     this.y = t1.y * mag;
/* 106 */     this.z = t1.z * mag;
/* 107 */     this.w = t1.w * mag;
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
/*     */   public Quat4d(Tuple4d t1) {
/* 119 */     double mag = 1.0D / Math.sqrt(t1.x * t1.x + t1.y * t1.y + t1.z * t1.z + t1.w * t1.w);
/* 120 */     this.x = t1.x * mag;
/* 121 */     this.y = t1.y * mag;
/* 122 */     this.z = t1.z * mag;
/* 123 */     this.w = t1.w * mag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4d() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void conjugate(Quat4d q1) {
/* 142 */     this.x = -q1.x;
/* 143 */     this.y = -q1.y;
/* 144 */     this.z = -q1.z;
/* 145 */     this.w = q1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void conjugate() {
/* 155 */     this.x = -this.x;
/* 156 */     this.y = -this.y;
/* 157 */     this.z = -this.z;
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
/*     */   public final void mul(Quat4d q1, Quat4d q2) {
/* 170 */     if (this != q1 && this != q2) {
/* 171 */       this.w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
/* 172 */       this.x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
/* 173 */       this.y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
/* 174 */       this.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
/*     */     }
/*     */     else {
/*     */       
/* 178 */       double w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
/* 179 */       double x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
/* 180 */       double y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
/* 181 */       this.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
/* 182 */       this.w = w;
/* 183 */       this.x = x;
/* 184 */       this.y = y;
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
/*     */   public final void mul(Quat4d q1) {
/* 198 */     double w = this.w * q1.w - this.x * q1.x - this.y * q1.y - this.z * q1.z;
/* 199 */     double x = this.w * q1.x + q1.w * this.x + this.y * q1.z - this.z * q1.y;
/* 200 */     double y = this.w * q1.y + q1.w * this.y - this.x * q1.z + this.z * q1.x;
/* 201 */     this.z = this.w * q1.z + q1.w * this.z + this.x * q1.y - this.y * q1.x;
/* 202 */     this.w = w;
/* 203 */     this.x = x;
/* 204 */     this.y = y;
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
/*     */   public final void mulInverse(Quat4d q1, Quat4d q2) {
/* 217 */     Quat4d tempQuat = new Quat4d(q2);
/*     */     
/* 219 */     tempQuat.inverse();
/* 220 */     mul(q1, tempQuat);
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
/*     */   public final void mulInverse(Quat4d q1) {
/* 233 */     Quat4d tempQuat = new Quat4d(q1);
/*     */     
/* 235 */     tempQuat.inverse();
/* 236 */     mul(tempQuat);
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
/*     */   public final void inverse(Quat4d q1) {
/* 248 */     double norm = 1.0D / (q1.w * q1.w + q1.x * q1.x + q1.y * q1.y + q1.z * q1.z);
/* 249 */     this.w = norm * q1.w;
/* 250 */     this.x = -norm * q1.x;
/* 251 */     this.y = -norm * q1.y;
/* 252 */     this.z = -norm * q1.z;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void inverse() {
/* 263 */     double norm = 1.0D / (this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z);
/* 264 */     this.w *= norm;
/* 265 */     this.x *= -norm;
/* 266 */     this.y *= -norm;
/* 267 */     this.z *= -norm;
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
/*     */   public final void normalize(Quat4d q1) {
/* 280 */     double norm = q1.x * q1.x + q1.y * q1.y + q1.z * q1.z + q1.w * q1.w;
/*     */     
/* 282 */     if (norm > 0.0D) {
/* 283 */       norm = 1.0D / Math.sqrt(norm);
/* 284 */       this.x = norm * q1.x;
/* 285 */       this.y = norm * q1.y;
/* 286 */       this.z = norm * q1.z;
/* 287 */       this.w = norm * q1.w;
/*     */     } else {
/* 289 */       this.x = 0.0D;
/* 290 */       this.y = 0.0D;
/* 291 */       this.z = 0.0D;
/* 292 */       this.w = 0.0D;
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
/*     */   public final void normalize() {
/* 304 */     double norm = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
/*     */     
/* 306 */     if (norm > 0.0D) {
/* 307 */       norm = 1.0D / Math.sqrt(norm);
/* 308 */       this.x *= norm;
/* 309 */       this.y *= norm;
/* 310 */       this.z *= norm;
/* 311 */       this.w *= norm;
/*     */     } else {
/* 313 */       this.x = 0.0D;
/* 314 */       this.y = 0.0D;
/* 315 */       this.z = 0.0D;
/* 316 */       this.w = 0.0D;
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
/*     */   public final void set(Matrix4f m1) {
/* 328 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + m1.m33);
/*     */     
/* 330 */     if (ww >= 0.0D) {
/* 331 */       if (ww >= 1.0E-30D) {
/* 332 */         this.w = Math.sqrt(ww);
/* 333 */         ww = 0.25D / this.w;
/* 334 */         this.x = (m1.m21 - m1.m12) * ww;
/* 335 */         this.y = (m1.m02 - m1.m20) * ww;
/* 336 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 340 */       this.w = 0.0D;
/* 341 */       this.x = 0.0D;
/* 342 */       this.y = 0.0D;
/* 343 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 347 */     this.w = 0.0D;
/* 348 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 349 */     if (ww >= 0.0D) {
/* 350 */       if (ww >= 1.0E-30D) {
/* 351 */         this.x = Math.sqrt(ww);
/* 352 */         ww = 1.0D / 2.0D * this.x;
/* 353 */         this.y = m1.m10 * ww;
/* 354 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 358 */       this.x = 0.0D;
/* 359 */       this.y = 0.0D;
/* 360 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 364 */     this.x = 0.0D;
/* 365 */     ww = 0.5D * (1.0D - m1.m22);
/* 366 */     if (ww >= 1.0E-30D) {
/* 367 */       this.y = Math.sqrt(ww);
/* 368 */       this.z = m1.m21 / 2.0D * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 372 */     this.y = 0.0D;
/* 373 */     this.z = 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Matrix4d m1) {
/* 384 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + m1.m33);
/*     */     
/* 386 */     if (ww >= 0.0D) {
/* 387 */       if (ww >= 1.0E-30D) {
/* 388 */         this.w = Math.sqrt(ww);
/* 389 */         ww = 0.25D / this.w;
/* 390 */         this.x = (m1.m21 - m1.m12) * ww;
/* 391 */         this.y = (m1.m02 - m1.m20) * ww;
/* 392 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 396 */       this.w = 0.0D;
/* 397 */       this.x = 0.0D;
/* 398 */       this.y = 0.0D;
/* 399 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 403 */     this.w = 0.0D;
/* 404 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 405 */     if (ww >= 0.0D) {
/* 406 */       if (ww >= 1.0E-30D) {
/* 407 */         this.x = Math.sqrt(ww);
/* 408 */         ww = 0.5D / this.x;
/* 409 */         this.y = m1.m10 * ww;
/* 410 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 414 */       this.x = 0.0D;
/* 415 */       this.y = 0.0D;
/* 416 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 420 */     this.x = 0.0D;
/* 421 */     ww = 0.5D * (1.0D - m1.m22);
/* 422 */     if (ww >= 1.0E-30D) {
/* 423 */       this.y = Math.sqrt(ww);
/* 424 */       this.z = m1.m21 / 2.0D * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 428 */     this.y = 0.0D;
/* 429 */     this.z = 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Matrix3f m1) {
/* 440 */     double ww = 0.25D * ((m1.m00 + m1.m11 + m1.m22) + 1.0D);
/*     */     
/* 442 */     if (ww >= 0.0D) {
/* 443 */       if (ww >= 1.0E-30D) {
/* 444 */         this.w = Math.sqrt(ww);
/* 445 */         ww = 0.25D / this.w;
/* 446 */         this.x = (m1.m21 - m1.m12) * ww;
/* 447 */         this.y = (m1.m02 - m1.m20) * ww;
/* 448 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 452 */       this.w = 0.0D;
/* 453 */       this.x = 0.0D;
/* 454 */       this.y = 0.0D;
/* 455 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 459 */     this.w = 0.0D;
/* 460 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 461 */     if (ww >= 0.0D) {
/* 462 */       if (ww >= 1.0E-30D) {
/* 463 */         this.x = Math.sqrt(ww);
/* 464 */         ww = 0.5D / this.x;
/* 465 */         this.y = m1.m10 * ww;
/* 466 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 470 */       this.x = 0.0D;
/* 471 */       this.y = 0.0D;
/* 472 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 476 */     this.x = 0.0D;
/* 477 */     ww = 0.5D * (1.0D - m1.m22);
/* 478 */     if (ww >= 1.0E-30D) {
/* 479 */       this.y = Math.sqrt(ww);
/* 480 */       this.z = m1.m21 / 2.0D * this.y;
/*     */     } 
/*     */     
/* 483 */     this.y = 0.0D;
/* 484 */     this.z = 1.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Matrix3d m1) {
/* 495 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + 1.0D);
/*     */     
/* 497 */     if (ww >= 0.0D) {
/* 498 */       if (ww >= 1.0E-30D) {
/* 499 */         this.w = Math.sqrt(ww);
/* 500 */         ww = 0.25D / this.w;
/* 501 */         this.x = (m1.m21 - m1.m12) * ww;
/* 502 */         this.y = (m1.m02 - m1.m20) * ww;
/* 503 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 507 */       this.w = 0.0D;
/* 508 */       this.x = 0.0D;
/* 509 */       this.y = 0.0D;
/* 510 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 514 */     this.w = 0.0D;
/* 515 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 516 */     if (ww >= 0.0D) {
/* 517 */       if (ww >= 1.0E-30D) {
/* 518 */         this.x = Math.sqrt(ww);
/* 519 */         ww = 0.5D / this.x;
/* 520 */         this.y = m1.m10 * ww;
/* 521 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 525 */       this.x = 0.0D;
/* 526 */       this.y = 0.0D;
/* 527 */       this.z = 1.0D;
/*     */       
/*     */       return;
/*     */     } 
/* 531 */     this.x = 0.0D;
/* 532 */     ww = 0.5D * (1.0D - m1.m22);
/* 533 */     if (ww >= 1.0E-30D) {
/* 534 */       this.y = Math.sqrt(ww);
/* 535 */       this.z = m1.m21 / 2.0D * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 539 */     this.y = 0.0D;
/* 540 */     this.z = 1.0D;
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
/*     */   public final void set(AxisAngle4f a) {
/* 554 */     double amag = Math.sqrt((a.x * a.x + a.y * a.y + a.z * a.z));
/* 555 */     if (amag < 1.0E-12D) {
/* 556 */       this.w = 0.0D;
/* 557 */       this.x = 0.0D;
/* 558 */       this.y = 0.0D;
/* 559 */       this.z = 0.0D;
/*     */     } else {
/* 561 */       double mag = Math.sin(a.angle / 2.0D);
/* 562 */       amag = 1.0D / amag;
/* 563 */       this.w = Math.cos(a.angle / 2.0D);
/* 564 */       this.x = a.x * amag * mag;
/* 565 */       this.y = a.y * amag * mag;
/* 566 */       this.z = a.z * amag * mag;
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
/*     */   public final void set(AxisAngle4d a) {
/* 581 */     double amag = Math.sqrt(a.x * a.x + a.y * a.y + a.z * a.z);
/* 582 */     if (amag < 1.0E-12D) {
/* 583 */       this.w = 0.0D;
/* 584 */       this.x = 0.0D;
/* 585 */       this.y = 0.0D;
/* 586 */       this.z = 0.0D;
/*     */     } else {
/* 588 */       amag = 1.0D / amag;
/* 589 */       double mag = Math.sin(a.angle / 2.0D);
/* 590 */       this.w = Math.cos(a.angle / 2.0D);
/* 591 */       this.x = a.x * amag * mag;
/* 592 */       this.y = a.y * amag * mag;
/* 593 */       this.z = a.z * amag * mag;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(Quat4d q1, double alpha) {
/* 614 */     double s1, s2, dot = this.x * q1.x + this.y * q1.y + this.z * q1.z + this.w * q1.w;
/*     */     
/* 616 */     if (dot < 0.0D) {
/*     */       
/* 618 */       q1.x = -q1.x; q1.y = -q1.y; q1.z = -q1.z; q1.w = -q1.w;
/* 619 */       dot = -dot;
/*     */     } 
/*     */     
/* 622 */     if (1.0D - dot > 1.0E-12D) {
/* 623 */       double om = Math.acos(dot);
/* 624 */       double sinom = Math.sin(om);
/* 625 */       s1 = Math.sin((1.0D - alpha) * om) / sinom;
/* 626 */       s2 = Math.sin(alpha * om) / sinom;
/*     */     } else {
/* 628 */       s1 = 1.0D - alpha;
/* 629 */       s2 = alpha;
/*     */     } 
/*     */     
/* 632 */     this.w = s1 * this.w + s2 * q1.w;
/* 633 */     this.x = s1 * this.x + s2 * q1.x;
/* 634 */     this.y = s1 * this.y + s2 * q1.y;
/* 635 */     this.z = s1 * this.z + s2 * q1.z;
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
/*     */ 
/*     */   
/*     */   public final void interpolate(Quat4d q1, Quat4d q2, double alpha) {
/* 654 */     double s1, s2, dot = q2.x * q1.x + q2.y * q1.y + q2.z * q1.z + q2.w * q1.w;
/*     */     
/* 656 */     if (dot < 0.0D) {
/*     */       
/* 658 */       q1.x = -q1.x; q1.y = -q1.y; q1.z = -q1.z; q1.w = -q1.w;
/* 659 */       dot = -dot;
/*     */     } 
/*     */     
/* 662 */     if (1.0D - dot > 1.0E-12D) {
/* 663 */       double om = Math.acos(dot);
/* 664 */       double sinom = Math.sin(om);
/* 665 */       s1 = Math.sin((1.0D - alpha) * om) / sinom;
/* 666 */       s2 = Math.sin(alpha * om) / sinom;
/*     */     } else {
/* 668 */       s1 = 1.0D - alpha;
/* 669 */       s2 = alpha;
/*     */     } 
/* 671 */     this.w = s1 * q1.w + s2 * q2.w;
/* 672 */     this.x = s1 * q1.x + s2 * q2.x;
/* 673 */     this.y = s1 * q1.y + s2 * q2.y;
/* 674 */     this.z = s1 * q1.z + s2 * q2.z;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Quat4d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */