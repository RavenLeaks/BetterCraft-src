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
/*     */ public class Quat4f
/*     */   extends Tuple4f
/*     */   implements Serializable
/*     */ {
/*     */   static final long serialVersionUID = 2675933778405442383L;
/*     */   static final double EPS = 1.0E-6D;
/*     */   static final double EPS2 = 1.0E-30D;
/*     */   static final double PIO2 = 1.57079632679D;
/*     */   
/*     */   public Quat4f(float x, float y, float z, float w) {
/*  54 */     float mag = (float)(1.0D / Math.sqrt((x * x + y * y + z * z + w * w)));
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
/*     */   public Quat4f(float[] q) {
/*  69 */     float mag = (float)(1.0D / Math.sqrt((q[0] * q[0] + q[1] * q[1] + q[2] * q[2] + q[3] * q[3])));
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
/*     */   
/*     */   public Quat4f(Quat4f q1) {
/*  84 */     super(q1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f(Quat4d q1) {
/*  93 */     super(q1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f(Tuple4f t1) {
/* 104 */     float mag = (float)(1.0D / Math.sqrt((t1.x * t1.x + t1.y * t1.y + t1.z * t1.z + t1.w * t1.w)));
/* 105 */     this.x = t1.x * mag;
/* 106 */     this.y = t1.y * mag;
/* 107 */     this.z = t1.z * mag;
/* 108 */     this.w = t1.w * mag;
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
/*     */   public Quat4f(Tuple4d t1) {
/* 120 */     double mag = 1.0D / Math.sqrt(t1.x * t1.x + t1.y * t1.y + t1.z * t1.z + t1.w * t1.w);
/* 121 */     this.x = (float)(t1.x * mag);
/* 122 */     this.y = (float)(t1.y * mag);
/* 123 */     this.z = (float)(t1.z * mag);
/* 124 */     this.w = (float)(t1.w * mag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Quat4f() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void conjugate(Quat4f q1) {
/* 143 */     this.x = -q1.x;
/* 144 */     this.y = -q1.y;
/* 145 */     this.z = -q1.z;
/* 146 */     this.w = q1.w;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void conjugate() {
/* 154 */     this.x = -this.x;
/* 155 */     this.y = -this.y;
/* 156 */     this.z = -this.z;
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
/*     */   public final void mul(Quat4f q1, Quat4f q2) {
/* 169 */     if (this != q1 && this != q2) {
/* 170 */       this.w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
/* 171 */       this.x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
/* 172 */       this.y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
/* 173 */       this.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
/*     */     }
/*     */     else {
/*     */       
/* 177 */       float w = q1.w * q2.w - q1.x * q2.x - q1.y * q2.y - q1.z * q2.z;
/* 178 */       float x = q1.w * q2.x + q2.w * q1.x + q1.y * q2.z - q1.z * q2.y;
/* 179 */       float y = q1.w * q2.y + q2.w * q1.y - q1.x * q2.z + q1.z * q2.x;
/* 180 */       this.z = q1.w * q2.z + q2.w * q1.z + q1.x * q2.y - q1.y * q2.x;
/* 181 */       this.w = w;
/* 182 */       this.x = x;
/* 183 */       this.y = y;
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
/*     */   public final void mul(Quat4f q1) {
/* 197 */     float w = this.w * q1.w - this.x * q1.x - this.y * q1.y - this.z * q1.z;
/* 198 */     float x = this.w * q1.x + q1.w * this.x + this.y * q1.z - this.z * q1.y;
/* 199 */     float y = this.w * q1.y + q1.w * this.y - this.x * q1.z + this.z * q1.x;
/* 200 */     this.z = this.w * q1.z + q1.w * this.z + this.x * q1.y - this.y * q1.x;
/* 201 */     this.w = w;
/* 202 */     this.x = x;
/* 203 */     this.y = y;
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
/*     */   public final void mulInverse(Quat4f q1, Quat4f q2) {
/* 216 */     Quat4f tempQuat = new Quat4f(q2);
/*     */     
/* 218 */     tempQuat.inverse();
/* 219 */     mul(q1, tempQuat);
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
/*     */   public final void mulInverse(Quat4f q1) {
/* 232 */     Quat4f tempQuat = new Quat4f(q1);
/*     */     
/* 234 */     tempQuat.inverse();
/* 235 */     mul(tempQuat);
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
/*     */   public final void inverse(Quat4f q1) {
/* 248 */     float norm = 1.0F / (q1.w * q1.w + q1.x * q1.x + q1.y * q1.y + q1.z * q1.z);
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
/* 263 */     float norm = 1.0F / (this.w * this.w + this.x * this.x + this.y * this.y + this.z * this.z);
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
/*     */   public final void normalize(Quat4f q1) {
/* 280 */     float norm = q1.x * q1.x + q1.y * q1.y + q1.z * q1.z + q1.w * q1.w;
/*     */     
/* 282 */     if (norm > 0.0F) {
/* 283 */       norm = 1.0F / (float)Math.sqrt(norm);
/* 284 */       this.x = norm * q1.x;
/* 285 */       this.y = norm * q1.y;
/* 286 */       this.z = norm * q1.z;
/* 287 */       this.w = norm * q1.w;
/*     */     } else {
/* 289 */       this.x = 0.0F;
/* 290 */       this.y = 0.0F;
/* 291 */       this.z = 0.0F;
/* 292 */       this.w = 0.0F;
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
/* 304 */     float norm = this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
/*     */     
/* 306 */     if (norm > 0.0F) {
/* 307 */       norm = 1.0F / (float)Math.sqrt(norm);
/* 308 */       this.x *= norm;
/* 309 */       this.y *= norm;
/* 310 */       this.z *= norm;
/* 311 */       this.w *= norm;
/*     */     } else {
/* 313 */       this.x = 0.0F;
/* 314 */       this.y = 0.0F;
/* 315 */       this.z = 0.0F;
/* 316 */       this.w = 0.0F;
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
/* 328 */     float ww = 0.25F * (m1.m00 + m1.m11 + m1.m22 + m1.m33);
/*     */     
/* 330 */     if (ww >= 0.0F) {
/* 331 */       if (ww >= 1.0E-30D) {
/* 332 */         this.w = (float)Math.sqrt(ww);
/* 333 */         ww = 0.25F / this.w;
/* 334 */         this.x = (m1.m21 - m1.m12) * ww;
/* 335 */         this.y = (m1.m02 - m1.m20) * ww;
/* 336 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 340 */       this.w = 0.0F;
/* 341 */       this.x = 0.0F;
/* 342 */       this.y = 0.0F;
/* 343 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 347 */     this.w = 0.0F;
/* 348 */     ww = -0.5F * (m1.m11 + m1.m22);
/*     */     
/* 350 */     if (ww >= 0.0F) {
/* 351 */       if (ww >= 1.0E-30D) {
/* 352 */         this.x = (float)Math.sqrt(ww);
/* 353 */         ww = 1.0F / 2.0F * this.x;
/* 354 */         this.y = m1.m10 * ww;
/* 355 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 359 */       this.x = 0.0F;
/* 360 */       this.y = 0.0F;
/* 361 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 365 */     this.x = 0.0F;
/* 366 */     ww = 0.5F * (1.0F - m1.m22);
/*     */     
/* 368 */     if (ww >= 1.0E-30D) {
/* 369 */       this.y = (float)Math.sqrt(ww);
/* 370 */       this.z = m1.m21 / 2.0F * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 374 */     this.y = 0.0F;
/* 375 */     this.z = 1.0F;
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
/* 386 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + m1.m33);
/*     */     
/* 388 */     if (ww >= 0.0D) {
/* 389 */       if (ww >= 1.0E-30D) {
/* 390 */         this.w = (float)Math.sqrt(ww);
/* 391 */         ww = 0.25D / this.w;
/* 392 */         this.x = (float)((m1.m21 - m1.m12) * ww);
/* 393 */         this.y = (float)((m1.m02 - m1.m20) * ww);
/* 394 */         this.z = (float)((m1.m10 - m1.m01) * ww);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 398 */       this.w = 0.0F;
/* 399 */       this.x = 0.0F;
/* 400 */       this.y = 0.0F;
/* 401 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 405 */     this.w = 0.0F;
/* 406 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 407 */     if (ww >= 0.0D) {
/* 408 */       if (ww >= 1.0E-30D) {
/* 409 */         this.x = (float)Math.sqrt(ww);
/* 410 */         ww = 0.5D / this.x;
/* 411 */         this.y = (float)(m1.m10 * ww);
/* 412 */         this.z = (float)(m1.m20 * ww);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 416 */       this.x = 0.0F;
/* 417 */       this.y = 0.0F;
/* 418 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 422 */     this.x = 0.0F;
/* 423 */     ww = 0.5D * (1.0D - m1.m22);
/* 424 */     if (ww >= 1.0E-30D) {
/* 425 */       this.y = (float)Math.sqrt(ww);
/* 426 */       this.z = (float)(m1.m21 / 2.0D * this.y);
/*     */       
/*     */       return;
/*     */     } 
/* 430 */     this.y = 0.0F;
/* 431 */     this.z = 1.0F;
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
/* 442 */     float ww = 0.25F * (m1.m00 + m1.m11 + m1.m22 + 1.0F);
/*     */     
/* 444 */     if (ww >= 0.0F) {
/* 445 */       if (ww >= 1.0E-30D) {
/* 446 */         this.w = (float)Math.sqrt(ww);
/* 447 */         ww = 0.25F / this.w;
/* 448 */         this.x = (m1.m21 - m1.m12) * ww;
/* 449 */         this.y = (m1.m02 - m1.m20) * ww;
/* 450 */         this.z = (m1.m10 - m1.m01) * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 454 */       this.w = 0.0F;
/* 455 */       this.x = 0.0F;
/* 456 */       this.y = 0.0F;
/* 457 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 461 */     this.w = 0.0F;
/* 462 */     ww = -0.5F * (m1.m11 + m1.m22);
/* 463 */     if (ww >= 0.0F) {
/* 464 */       if (ww >= 1.0E-30D) {
/* 465 */         this.x = (float)Math.sqrt(ww);
/* 466 */         ww = 0.5F / this.x;
/* 467 */         this.y = m1.m10 * ww;
/* 468 */         this.z = m1.m20 * ww;
/*     */         return;
/*     */       } 
/*     */     } else {
/* 472 */       this.x = 0.0F;
/* 473 */       this.y = 0.0F;
/* 474 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 478 */     this.x = 0.0F;
/* 479 */     ww = 0.5F * (1.0F - m1.m22);
/* 480 */     if (ww >= 1.0E-30D) {
/* 481 */       this.y = (float)Math.sqrt(ww);
/* 482 */       this.z = m1.m21 / 2.0F * this.y;
/*     */       
/*     */       return;
/*     */     } 
/* 486 */     this.y = 0.0F;
/* 487 */     this.z = 1.0F;
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
/* 498 */     double ww = 0.25D * (m1.m00 + m1.m11 + m1.m22 + 1.0D);
/*     */     
/* 500 */     if (ww >= 0.0D) {
/* 501 */       if (ww >= 1.0E-30D) {
/* 502 */         this.w = (float)Math.sqrt(ww);
/* 503 */         ww = 0.25D / this.w;
/* 504 */         this.x = (float)((m1.m21 - m1.m12) * ww);
/* 505 */         this.y = (float)((m1.m02 - m1.m20) * ww);
/* 506 */         this.z = (float)((m1.m10 - m1.m01) * ww);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 510 */       this.w = 0.0F;
/* 511 */       this.x = 0.0F;
/* 512 */       this.y = 0.0F;
/* 513 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 517 */     this.w = 0.0F;
/* 518 */     ww = -0.5D * (m1.m11 + m1.m22);
/* 519 */     if (ww >= 0.0D) {
/* 520 */       if (ww >= 1.0E-30D) {
/* 521 */         this.x = (float)Math.sqrt(ww);
/* 522 */         ww = 0.5D / this.x;
/* 523 */         this.y = (float)(m1.m10 * ww);
/* 524 */         this.z = (float)(m1.m20 * ww);
/*     */         return;
/*     */       } 
/*     */     } else {
/* 528 */       this.x = 0.0F;
/* 529 */       this.y = 0.0F;
/* 530 */       this.z = 1.0F;
/*     */       
/*     */       return;
/*     */     } 
/* 534 */     this.x = 0.0F;
/* 535 */     ww = 0.5D * (1.0D - m1.m22);
/* 536 */     if (ww >= 1.0E-30D) {
/* 537 */       this.y = (float)Math.sqrt(ww);
/* 538 */       this.z = (float)(m1.m21 / 2.0D * this.y);
/*     */       
/*     */       return;
/*     */     } 
/* 542 */     this.y = 0.0F;
/* 543 */     this.z = 1.0F;
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
/*     */   public final void set(AxisAngle4f a) {
/* 556 */     float amag = (float)Math.sqrt((a.x * a.x + a.y * a.y + a.z * a.z));
/* 557 */     if (amag < 1.0E-6D) {
/* 558 */       this.w = 0.0F;
/* 559 */       this.x = 0.0F;
/* 560 */       this.y = 0.0F;
/* 561 */       this.z = 0.0F;
/*     */     } else {
/* 563 */       amag = 1.0F / amag;
/* 564 */       float mag = (float)Math.sin(a.angle / 2.0D);
/* 565 */       this.w = (float)Math.cos(a.angle / 2.0D);
/* 566 */       this.x = a.x * amag * mag;
/* 567 */       this.y = a.y * amag * mag;
/* 568 */       this.z = a.z * amag * mag;
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
/* 583 */     float amag = (float)(1.0D / Math.sqrt(a.x * a.x + a.y * a.y + a.z * a.z));
/*     */     
/* 585 */     if (amag < 1.0E-6D) {
/* 586 */       this.w = 0.0F;
/* 587 */       this.x = 0.0F;
/* 588 */       this.y = 0.0F;
/* 589 */       this.z = 0.0F;
/*     */     } else {
/* 591 */       amag = 1.0F / amag;
/* 592 */       float mag = (float)Math.sin(a.angle / 2.0D);
/* 593 */       this.w = (float)Math.cos(a.angle / 2.0D);
/* 594 */       this.x = (float)a.x * amag * mag;
/* 595 */       this.y = (float)a.y * amag * mag;
/* 596 */       this.z = (float)a.z * amag * mag;
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
/*     */ 
/*     */   
/*     */   public final void interpolate(Quat4f q1, float alpha) {
/* 619 */     double s1, s2, dot = (this.x * q1.x + this.y * q1.y + this.z * q1.z + this.w * q1.w);
/*     */     
/* 621 */     if (dot < 0.0D) {
/*     */       
/* 623 */       q1.x = -q1.x; q1.y = -q1.y; q1.z = -q1.z; q1.w = -q1.w;
/* 624 */       dot = -dot;
/*     */     } 
/*     */     
/* 627 */     if (1.0D - dot > 1.0E-6D) {
/* 628 */       double om = Math.acos(dot);
/* 629 */       double sinom = Math.sin(om);
/* 630 */       s1 = Math.sin((1.0D - alpha) * om) / sinom;
/* 631 */       s2 = Math.sin(alpha * om) / sinom;
/*     */     } else {
/* 633 */       s1 = 1.0D - alpha;
/* 634 */       s2 = alpha;
/*     */     } 
/*     */     
/* 637 */     this.w = (float)(s1 * this.w + s2 * q1.w);
/* 638 */     this.x = (float)(s1 * this.x + s2 * q1.x);
/* 639 */     this.y = (float)(s1 * this.y + s2 * q1.y);
/* 640 */     this.z = (float)(s1 * this.z + s2 * q1.z);
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
/*     */ 
/*     */   
/*     */   public final void interpolate(Quat4f q1, Quat4f q2, float alpha) {
/* 662 */     double s1, s2, dot = (q2.x * q1.x + q2.y * q1.y + q2.z * q1.z + q2.w * q1.w);
/*     */     
/* 664 */     if (dot < 0.0D) {
/*     */       
/* 666 */       q1.x = -q1.x; q1.y = -q1.y; q1.z = -q1.z; q1.w = -q1.w;
/* 667 */       dot = -dot;
/*     */     } 
/*     */     
/* 670 */     if (1.0D - dot > 1.0E-6D) {
/* 671 */       double om = Math.acos(dot);
/* 672 */       double sinom = Math.sin(om);
/* 673 */       s1 = Math.sin((1.0D - alpha) * om) / sinom;
/* 674 */       s2 = Math.sin(alpha * om) / sinom;
/*     */     } else {
/* 676 */       s1 = 1.0D - alpha;
/* 677 */       s2 = alpha;
/*     */     } 
/* 679 */     this.w = (float)(s1 * q1.w + s2 * q2.w);
/* 680 */     this.x = (float)(s1 * q1.x + s2 * q2.x);
/* 681 */     this.y = (float)(s1 * q1.y + s2 * q2.y);
/* 682 */     this.z = (float)(s1 * q1.z + s2 * q2.z);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Quat4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */