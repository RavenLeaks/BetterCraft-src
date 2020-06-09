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
/*     */ 
/*     */ public class AxisAngle4f
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   static final long serialVersionUID = -163246355858070601L;
/*     */   public float x;
/*     */   public float y;
/*     */   public float z;
/*     */   public float angle;
/*     */   static final double EPS = 1.0E-6D;
/*     */   
/*     */   public AxisAngle4f(float x, float y, float z, float angle) {
/*  72 */     this.x = x;
/*  73 */     this.y = y;
/*  74 */     this.z = z;
/*  75 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4f(float[] a) {
/*  85 */     this.x = a[0];
/*  86 */     this.y = a[1];
/*  87 */     this.z = a[2];
/*  88 */     this.angle = a[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4f(AxisAngle4f a1) {
/*  99 */     this.x = a1.x;
/* 100 */     this.y = a1.y;
/* 101 */     this.z = a1.z;
/* 102 */     this.angle = a1.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4f(AxisAngle4d a1) {
/* 112 */     this.x = (float)a1.x;
/* 113 */     this.y = (float)a1.y;
/* 114 */     this.z = (float)a1.z;
/* 115 */     this.angle = (float)a1.angle;
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
/*     */   public AxisAngle4f(Vector3f axis, float angle) {
/* 128 */     this.x = axis.x;
/* 129 */     this.y = axis.y;
/* 130 */     this.z = axis.z;
/* 131 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAngle4f() {
/* 140 */     this.x = 0.0F;
/* 141 */     this.y = 0.0F;
/* 142 */     this.z = 1.0F;
/* 143 */     this.angle = 0.0F;
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
/*     */   public final void set(float x, float y, float z, float angle) {
/* 156 */     this.x = x;
/* 157 */     this.y = y;
/* 158 */     this.z = z;
/* 159 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(float[] a) {
/* 170 */     this.x = a[0];
/* 171 */     this.y = a[1];
/* 172 */     this.z = a[2];
/* 173 */     this.angle = a[3];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(AxisAngle4f a1) {
/* 183 */     this.x = a1.x;
/* 184 */     this.y = a1.y;
/* 185 */     this.z = a1.z;
/* 186 */     this.angle = a1.angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(AxisAngle4d a1) {
/* 196 */     this.x = (float)a1.x;
/* 197 */     this.y = (float)a1.y;
/* 198 */     this.z = (float)a1.z;
/* 199 */     this.angle = (float)a1.angle;
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
/*     */   public final void set(Vector3f axis, float angle) {
/* 212 */     this.x = axis.x;
/* 213 */     this.y = axis.y;
/* 214 */     this.z = axis.z;
/* 215 */     this.angle = angle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void get(float[] a) {
/* 225 */     a[0] = this.x;
/* 226 */     a[1] = this.y;
/* 227 */     a[2] = this.z;
/* 228 */     a[3] = this.angle;
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
/*     */   public final void set(Quat4f q1) {
/* 241 */     double mag = (q1.x * q1.x + q1.y * q1.y + q1.z * q1.z);
/*     */     
/* 243 */     if (mag > 1.0E-6D) {
/* 244 */       mag = Math.sqrt(mag);
/* 245 */       double invMag = 1.0D / mag;
/*     */       
/* 247 */       this.x = (float)(q1.x * invMag);
/* 248 */       this.y = (float)(q1.y * invMag);
/* 249 */       this.z = (float)(q1.z * invMag);
/* 250 */       this.angle = (float)(2.0D * Math.atan2(mag, q1.w));
/*     */     } else {
/* 252 */       this.x = 0.0F;
/* 253 */       this.y = 1.0F;
/* 254 */       this.z = 0.0F;
/* 255 */       this.angle = 0.0F;
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
/*     */   public final void set(Quat4d q1) {
/* 269 */     double mag = q1.x * q1.x + q1.y * q1.y + q1.z * q1.z;
/*     */     
/* 271 */     if (mag > 1.0E-6D) {
/* 272 */       mag = Math.sqrt(mag);
/* 273 */       double invMag = 1.0D / mag;
/*     */       
/* 275 */       this.x = (float)(q1.x * invMag);
/* 276 */       this.y = (float)(q1.y * invMag);
/* 277 */       this.z = (float)(q1.z * invMag);
/* 278 */       this.angle = (float)(2.0D * Math.atan2(mag, q1.w));
/*     */     } else {
/* 280 */       this.x = 0.0F;
/* 281 */       this.y = 1.0F;
/* 282 */       this.z = 0.0F;
/* 283 */       this.angle = 0.0F;
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
/*     */   public final void set(Matrix4f m1) {
/* 297 */     Matrix3f m3f = new Matrix3f();
/*     */     
/* 299 */     m1.get(m3f);
/*     */     
/* 301 */     this.x = m3f.m21 - m3f.m12;
/* 302 */     this.y = m3f.m02 - m3f.m20;
/* 303 */     this.z = m3f.m10 - m3f.m01;
/* 304 */     double mag = (this.x * this.x + this.y * this.y + this.z * this.z);
/*     */     
/* 306 */     if (mag > 1.0E-6D) {
/* 307 */       mag = Math.sqrt(mag);
/* 308 */       double sin = 0.5D * mag;
/* 309 */       double cos = 0.5D * ((m3f.m00 + m3f.m11 + m3f.m22) - 1.0D);
/*     */       
/* 311 */       this.angle = (float)Math.atan2(sin, cos);
/* 312 */       double invMag = 1.0D / mag;
/* 313 */       this.x = (float)(this.x * invMag);
/* 314 */       this.y = (float)(this.y * invMag);
/* 315 */       this.z = (float)(this.z * invMag);
/*     */     } else {
/* 317 */       this.x = 0.0F;
/* 318 */       this.y = 1.0F;
/* 319 */       this.z = 0.0F;
/* 320 */       this.angle = 0.0F;
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
/*     */   public final void set(Matrix4d m1) {
/* 336 */     Matrix3d m3d = new Matrix3d();
/*     */     
/* 338 */     m1.get(m3d);
/*     */ 
/*     */     
/* 341 */     this.x = (float)(m3d.m21 - m3d.m12);
/* 342 */     this.y = (float)(m3d.m02 - m3d.m20);
/* 343 */     this.z = (float)(m3d.m10 - m3d.m01);
/* 344 */     double mag = (this.x * this.x + this.y * this.y + this.z * this.z);
/*     */     
/* 346 */     if (mag > 1.0E-6D) {
/* 347 */       mag = Math.sqrt(mag);
/* 348 */       double sin = 0.5D * mag;
/* 349 */       double cos = 0.5D * (m3d.m00 + m3d.m11 + m3d.m22 - 1.0D);
/* 350 */       this.angle = (float)Math.atan2(sin, cos);
/*     */       
/* 352 */       double invMag = 1.0D / mag;
/* 353 */       this.x = (float)(this.x * invMag);
/* 354 */       this.y = (float)(this.y * invMag);
/* 355 */       this.z = (float)(this.z * invMag);
/*     */     } else {
/* 357 */       this.x = 0.0F;
/* 358 */       this.y = 1.0F;
/* 359 */       this.z = 0.0F;
/* 360 */       this.angle = 0.0F;
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
/*     */   public final void set(Matrix3f m1) {
/* 375 */     this.x = m1.m21 - m1.m12;
/* 376 */     this.y = m1.m02 - m1.m20;
/* 377 */     this.z = m1.m10 - m1.m01;
/* 378 */     double mag = (this.x * this.x + this.y * this.y + this.z * this.z);
/* 379 */     if (mag > 1.0E-6D) {
/* 380 */       mag = Math.sqrt(mag);
/* 381 */       double sin = 0.5D * mag;
/* 382 */       double cos = 0.5D * ((m1.m00 + m1.m11 + m1.m22) - 1.0D);
/*     */       
/* 384 */       this.angle = (float)Math.atan2(sin, cos);
/*     */       
/* 386 */       double invMag = 1.0D / mag;
/* 387 */       this.x = (float)(this.x * invMag);
/* 388 */       this.y = (float)(this.y * invMag);
/* 389 */       this.z = (float)(this.z * invMag);
/*     */     } else {
/* 391 */       this.x = 0.0F;
/* 392 */       this.y = 1.0F;
/* 393 */       this.z = 0.0F;
/* 394 */       this.angle = 0.0F;
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
/*     */   public final void set(Matrix3d m1) {
/* 410 */     this.x = (float)(m1.m21 - m1.m12);
/* 411 */     this.y = (float)(m1.m02 - m1.m20);
/* 412 */     this.z = (float)(m1.m10 - m1.m01);
/* 413 */     double mag = (this.x * this.x + this.y * this.y + this.z * this.z);
/*     */     
/* 415 */     if (mag > 1.0E-6D) {
/* 416 */       mag = Math.sqrt(mag);
/* 417 */       double sin = 0.5D * mag;
/* 418 */       double cos = 0.5D * (m1.m00 + m1.m11 + m1.m22 - 1.0D);
/*     */       
/* 420 */       this.angle = (float)Math.atan2(sin, cos);
/*     */       
/* 422 */       double invMag = 1.0D / mag;
/* 423 */       this.x = (float)(this.x * invMag);
/* 424 */       this.y = (float)(this.y * invMag);
/* 425 */       this.z = (float)(this.z * invMag);
/*     */     } else {
/* 427 */       this.x = 0.0F;
/* 428 */       this.y = 1.0F;
/* 429 */       this.z = 0.0F;
/* 430 */       this.angle = 0.0F;
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
/*     */   public String toString() {
/* 442 */     return "(" + this.x + ", " + this.y + ", " + this.z + ", " + this.angle + ")";
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
/*     */   public boolean equals(AxisAngle4f a1) {
/*     */     try {
/* 455 */       return (this.x == a1.x && this.y == a1.y && this.z == a1.z && 
/* 456 */         this.angle == a1.angle);
/*     */     } catch (NullPointerException e2) {
/* 458 */       return false;
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
/*     */   public boolean equals(Object o1) {
/*     */     
/* 473 */     try { AxisAngle4f a2 = (AxisAngle4f)o1;
/* 474 */       return (this.x == a2.x && this.y == a2.y && this.z == a2.z && 
/* 475 */         this.angle == a2.angle); }
/*     */     catch (NullPointerException e2)
/* 477 */     { return false; }
/* 478 */     catch (ClassCastException e1) { return false; }
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
/*     */   public boolean epsilonEquals(AxisAngle4f a1, float epsilon) {
/* 495 */     float diff = this.x - a1.x;
/* 496 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 498 */     diff = this.y - a1.y;
/* 499 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 501 */     diff = this.z - a1.z;
/* 502 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 504 */     diff = this.angle - a1.angle;
/* 505 */     if (((diff < 0.0F) ? -diff : diff) > epsilon) return false;
/*     */     
/* 507 */     return true;
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
/* 522 */     long bits = 1L;
/* 523 */     bits = VecMathUtil.hashFloatBits(bits, this.x);
/* 524 */     bits = VecMathUtil.hashFloatBits(bits, this.y);
/* 525 */     bits = VecMathUtil.hashFloatBits(bits, this.z);
/* 526 */     bits = VecMathUtil.hashFloatBits(bits, this.angle);
/* 527 */     return VecMathUtil.hashFinish(bits);
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
/* 542 */       return super.clone();
/* 543 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 545 */       throw new InternalError();
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
/*     */   public final float getAngle() {
/* 559 */     return this.angle;
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
/*     */   public final void setAngle(float angle) {
/* 572 */     this.angle = angle;
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
/* 584 */     return this.x;
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
/* 596 */     this.x = x;
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
/* 608 */     return this.y;
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
/* 620 */     this.y = y;
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
/*     */   public final float getZ() {
/* 632 */     return this.z;
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
/* 644 */     this.z = z;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\AxisAngle4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */