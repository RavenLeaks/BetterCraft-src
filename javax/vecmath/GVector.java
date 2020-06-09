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
/*     */ public class GVector
/*     */   implements Serializable, Cloneable
/*     */ {
/*     */   private int length;
/*     */   double[] values;
/*     */   static final long serialVersionUID = 1398850036893875112L;
/*     */   
/*     */   public GVector(int length) {
/*  52 */     this.length = length;
/*  53 */     this.values = new double[length];
/*  54 */     for (int i = 0; i < length; ) { this.values[i] = 0.0D; i++; }
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
/*     */   public GVector(double[] vector) {
/*  68 */     this.length = vector.length;
/*  69 */     this.values = new double[vector.length];
/*  70 */     for (int i = 0; i < this.length; ) { this.values[i] = vector[i]; i++; }
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
/*     */   public GVector(GVector vector) {
/*  82 */     this.values = new double[vector.length];
/*  83 */     this.length = vector.length;
/*  84 */     for (int i = 0; i < this.length; ) { this.values[i] = vector.values[i]; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple2f tuple) {
/*  94 */     this.values = new double[2];
/*  95 */     this.values[0] = tuple.x;
/*  96 */     this.values[1] = tuple.y;
/*  97 */     this.length = 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple3f tuple) {
/* 107 */     this.values = new double[3];
/* 108 */     this.values[0] = tuple.x;
/* 109 */     this.values[1] = tuple.y;
/* 110 */     this.values[2] = tuple.z;
/* 111 */     this.length = 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple3d tuple) {
/* 121 */     this.values = new double[3];
/* 122 */     this.values[0] = tuple.x;
/* 123 */     this.values[1] = tuple.y;
/* 124 */     this.values[2] = tuple.z;
/* 125 */     this.length = 3;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple4f tuple) {
/* 135 */     this.values = new double[4];
/* 136 */     this.values[0] = tuple.x;
/* 137 */     this.values[1] = tuple.y;
/* 138 */     this.values[2] = tuple.z;
/* 139 */     this.values[3] = tuple.w;
/* 140 */     this.length = 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GVector(Tuple4d tuple) {
/* 150 */     this.values = new double[4];
/* 151 */     this.values[0] = tuple.x;
/* 152 */     this.values[1] = tuple.y;
/* 153 */     this.values[2] = tuple.z;
/* 154 */     this.values[3] = tuple.w;
/* 155 */     this.length = 4;
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
/*     */   public GVector(double[] vector, int length) {
/* 171 */     this.length = length;
/* 172 */     this.values = new double[length];
/* 173 */     for (int i = 0; i < length; i++) {
/* 174 */       this.values[i] = vector[i];
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
/*     */   public final double norm() {
/* 186 */     double sq = 0.0D;
/*     */ 
/*     */     
/* 189 */     for (int i = 0; i < this.length; i++) {
/* 190 */       sq += this.values[i] * this.values[i];
/*     */     }
/*     */     
/* 193 */     return Math.sqrt(sq);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double normSquared() {
/* 204 */     double sq = 0.0D;
/*     */ 
/*     */     
/* 207 */     for (int i = 0; i < this.length; i++) {
/* 208 */       sq += this.values[i] * this.values[i];
/*     */     }
/*     */     
/* 211 */     return sq;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize(GVector v1) {
/* 220 */     double sq = 0.0D;
/*     */ 
/*     */     
/* 223 */     if (this.length != v1.length)
/* 224 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector0")); 
/*     */     int i;
/* 226 */     for (i = 0; i < this.length; i++) {
/* 227 */       sq += v1.values[i] * v1.values[i];
/*     */     }
/*     */ 
/*     */     
/* 231 */     double invMag = 1.0D / Math.sqrt(sq);
/*     */     
/* 233 */     for (i = 0; i < this.length; i++) {
/* 234 */       this.values[i] = v1.values[i] * invMag;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void normalize() {
/* 244 */     double sq = 0.0D;
/*     */     
/*     */     int i;
/* 247 */     for (i = 0; i < this.length; i++) {
/* 248 */       sq += this.values[i] * this.values[i];
/*     */     }
/*     */ 
/*     */     
/* 252 */     double invMag = 1.0D / Math.sqrt(sq);
/*     */     
/* 254 */     for (i = 0; i < this.length; i++) {
/* 255 */       this.values[i] = this.values[i] * invMag;
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
/*     */   public final void scale(double s, GVector v1) {
/* 269 */     if (this.length != v1.length) {
/* 270 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector1"));
/*     */     }
/* 272 */     for (int i = 0; i < this.length; i++) {
/* 273 */       this.values[i] = v1.values[i] * s;
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
/*     */   public final void scale(double s) {
/* 285 */     for (int i = 0; i < this.length; i++) {
/* 286 */       this.values[i] = this.values[i] * s;
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
/*     */   public final void scaleAdd(double s, GVector v1, GVector v2) {
/* 302 */     if (v2.length != v1.length) {
/* 303 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector2"));
/*     */     }
/* 305 */     if (this.length != v1.length) {
/* 306 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector3"));
/*     */     }
/* 308 */     for (int i = 0; i < this.length; i++) {
/* 309 */       this.values[i] = v1.values[i] * s + v2.values[i];
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
/*     */   public final void add(GVector vector) {
/* 322 */     if (this.length != vector.length) {
/* 323 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector4"));
/*     */     }
/* 325 */     for (int i = 0; i < this.length; i++) {
/* 326 */       this.values[i] = this.values[i] + vector.values[i];
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
/*     */   public final void add(GVector vector1, GVector vector2) {
/* 340 */     if (vector1.length != vector2.length) {
/* 341 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector5"));
/*     */     }
/* 343 */     if (this.length != vector1.length) {
/* 344 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector6"));
/*     */     }
/* 346 */     for (int i = 0; i < this.length; i++) {
/* 347 */       this.values[i] = vector1.values[i] + vector2.values[i];
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
/*     */   public final void sub(GVector vector) {
/* 359 */     if (this.length != vector.length) {
/* 360 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector7"));
/*     */     }
/* 362 */     for (int i = 0; i < this.length; i++) {
/* 363 */       this.values[i] = this.values[i] - vector.values[i];
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
/*     */   public final void sub(GVector vector1, GVector vector2) {
/* 378 */     if (vector1.length != vector2.length) {
/* 379 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector8"));
/*     */     }
/* 381 */     if (this.length != vector1.length) {
/* 382 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector9"));
/*     */     }
/* 384 */     for (int i = 0; i < this.length; i++) {
/* 385 */       this.values[i] = vector1.values[i] - vector2.values[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void mul(GMatrix m1, GVector v1) {
/*     */     double[] v;
/* 395 */     if (m1.getNumCol() != v1.length) {
/* 396 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector10"));
/*     */     }
/* 398 */     if (this.length != m1.getNumRow()) {
/* 399 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector11"));
/*     */     }
/*     */     
/* 402 */     if (v1 != this) {
/* 403 */       v = v1.values;
/*     */     } else {
/* 405 */       v = (double[])this.values.clone();
/*     */     } 
/*     */     
/* 408 */     for (int j = this.length - 1; j >= 0; j--) {
/* 409 */       this.values[j] = 0.0D;
/* 410 */       for (int i = v1.length - 1; i >= 0; i--) {
/* 411 */         this.values[j] = this.values[j] + m1.values[j][i] * v[i];
/*     */       }
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
/*     */   public final void mul(GVector v1, GMatrix m1) {
/*     */     double[] v;
/* 427 */     if (m1.getNumRow() != v1.length) {
/* 428 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector12"));
/*     */     }
/* 430 */     if (this.length != m1.getNumCol()) {
/* 431 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector13"));
/*     */     }
/*     */     
/* 434 */     if (v1 != this) {
/* 435 */       v = v1.values;
/*     */     } else {
/* 437 */       v = (double[])this.values.clone();
/*     */     } 
/*     */     
/* 440 */     for (int j = this.length - 1; j >= 0; j--) {
/* 441 */       this.values[j] = 0.0D;
/* 442 */       for (int i = v1.length - 1; i >= 0; i--) {
/* 443 */         this.values[j] = this.values[j] + m1.values[i][j] * v[i];
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void negate() {
/* 452 */     for (int i = this.length - 1; i >= 0; i--) {
/* 453 */       this.values[i] = this.values[i] * -1.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void zero() {
/* 461 */     for (int i = 0; i < this.length; i++) {
/* 462 */       this.values[i] = 0.0D;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setSize(int length) {
/*     */     int max;
/* 473 */     double[] tmp = new double[length];
/*     */ 
/*     */     
/* 476 */     if (this.length < length) {
/* 477 */       max = this.length;
/*     */     } else {
/* 479 */       max = length;
/*     */     } 
/* 481 */     for (int i = 0; i < max; i++) {
/* 482 */       tmp[i] = this.values[i];
/*     */     }
/* 484 */     this.length = length;
/*     */     
/* 486 */     this.values = tmp;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(double[] vector) {
/* 497 */     for (int i = this.length - 1; i >= 0; i--) {
/* 498 */       this.values[i] = vector[i];
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(GVector vector) {
/* 508 */     if (this.length < vector.length) {
/* 509 */       this.length = vector.length;
/* 510 */       this.values = new double[this.length];
/* 511 */       for (int i = 0; i < this.length; i++)
/* 512 */         this.values[i] = vector.values[i]; 
/*     */     } else {
/* 514 */       int i; for (i = 0; i < vector.length; i++)
/* 515 */         this.values[i] = vector.values[i]; 
/* 516 */       for (i = vector.length; i < this.length; i++) {
/* 517 */         this.values[i] = 0.0D;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple2f tuple) {
/* 527 */     if (this.length < 2) {
/* 528 */       this.length = 2;
/* 529 */       this.values = new double[2];
/*     */     } 
/* 531 */     this.values[0] = tuple.x;
/* 532 */     this.values[1] = tuple.y;
/* 533 */     for (int i = 2; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3f tuple) {
/* 543 */     if (this.length < 3) {
/* 544 */       this.length = 3;
/* 545 */       this.values = new double[3];
/*     */     } 
/* 547 */     this.values[0] = tuple.x;
/* 548 */     this.values[1] = tuple.y;
/* 549 */     this.values[2] = tuple.z;
/* 550 */     for (int i = 3; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple3d tuple) {
/* 559 */     if (this.length < 3) {
/* 560 */       this.length = 3;
/* 561 */       this.values = new double[3];
/*     */     } 
/* 563 */     this.values[0] = tuple.x;
/* 564 */     this.values[1] = tuple.y;
/* 565 */     this.values[2] = tuple.z;
/* 566 */     for (int i = 3; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4f tuple) {
/* 575 */     if (this.length < 4) {
/* 576 */       this.length = 4;
/* 577 */       this.values = new double[4];
/*     */     } 
/* 579 */     this.values[0] = tuple.x;
/* 580 */     this.values[1] = tuple.y;
/* 581 */     this.values[2] = tuple.z;
/* 582 */     this.values[3] = tuple.w;
/* 583 */     for (int i = 4; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void set(Tuple4d tuple) {
/* 592 */     if (this.length < 4) {
/* 593 */       this.length = 4;
/* 594 */       this.values = new double[4];
/*     */     } 
/* 596 */     this.values[0] = tuple.x;
/* 597 */     this.values[1] = tuple.y;
/* 598 */     this.values[2] = tuple.z;
/* 599 */     this.values[3] = tuple.w;
/* 600 */     for (int i = 4; i < this.length; ) { this.values[i] = 0.0D; i++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getSize() {
/* 609 */     return this.values.length;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getElement(int index) {
/* 619 */     return this.values[index];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setElement(int index, double value) {
/* 630 */     this.values[index] = value;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 639 */     StringBuffer buffer = new StringBuffer(this.length * 8);
/*     */ 
/*     */ 
/*     */     
/* 643 */     for (int i = 0; i < this.length; i++) {
/* 644 */       buffer.append(this.values[i]).append(" ");
/*     */     }
/*     */     
/* 647 */     return buffer.toString();
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
/*     */   public int hashCode() {
/* 663 */     long bits = 1L;
/*     */     
/* 665 */     for (int i = 0; i < this.length; i++) {
/* 666 */       bits = VecMathUtil.hashDoubleBits(bits, this.values[i]);
/*     */     }
/*     */     
/* 669 */     return VecMathUtil.hashFinish(bits);
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
/*     */   public boolean equals(GVector vector1) {
/*     */     try {
/* 682 */       if (this.length != vector1.length) return false;
/*     */       
/* 684 */       for (int i = 0; i < this.length; i++) {
/* 685 */         if (this.values[i] != vector1.values[i]) return false;
/*     */       
/*     */       } 
/* 688 */       return true;
/*     */     } catch (NullPointerException e2) {
/* 690 */       return false;
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
/*     */   public boolean equals(Object o1) {
/*     */     
/* 704 */     try { GVector v2 = (GVector)o1;
/*     */       
/* 706 */       if (this.length != v2.length) return false;
/*     */       
/* 708 */       for (int i = 0; i < this.length; i++) {
/* 709 */         if (this.values[i] != v2.values[i]) return false; 
/*     */       } 
/* 711 */       return true; }
/*     */     catch (ClassCastException e1)
/* 713 */     { return false; }
/* 714 */     catch (NullPointerException e2) { return false; }
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
/*     */   public boolean epsilonEquals(GVector v1, double epsilon) {
/* 731 */     if (this.length != v1.length) return false;
/*     */     
/* 733 */     for (int i = 0; i < this.length; i++) {
/* 734 */       double diff = this.values[i] - v1.values[i];
/* 735 */       if (((diff < 0.0D) ? -diff : diff) > epsilon) return false; 
/*     */     } 
/* 737 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double dot(GVector v1) {
/* 747 */     if (this.length != v1.length) {
/* 748 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector14"));
/*     */     }
/* 750 */     double result = 0.0D;
/* 751 */     for (int i = 0; i < this.length; i++) {
/* 752 */       result += this.values[i] * v1.values[i];
/*     */     }
/* 754 */     return result;
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
/*     */   public final void SVDBackSolve(GMatrix U, GMatrix W, GMatrix V, GVector b) {
/* 771 */     if (U.nRow != b.getSize() || 
/* 772 */       U.nRow != U.nCol || 
/* 773 */       U.nRow != W.nRow) {
/* 774 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector15"));
/*     */     }
/*     */     
/* 777 */     if (W.nCol != this.values.length || 
/* 778 */       W.nCol != V.nCol || 
/* 779 */       W.nCol != V.nRow) {
/* 780 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector23"));
/*     */     }
/*     */     
/* 783 */     GMatrix tmp = new GMatrix(U.nRow, W.nCol);
/* 784 */     tmp.mul(U, V);
/* 785 */     tmp.mulTransposeRight(U, W);
/* 786 */     tmp.invert();
/* 787 */     mul(tmp, b);
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
/*     */   public final void LUDBackSolve(GMatrix LU, GVector b, GVector permutation) {
/* 805 */     int size = LU.nRow * LU.nCol;
/*     */     
/* 807 */     double[] temp = new double[size];
/* 808 */     double[] result = new double[size];
/* 809 */     int[] row_perm = new int[b.getSize()];
/*     */ 
/*     */     
/* 812 */     if (LU.nRow != b.getSize()) {
/* 813 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector16"));
/*     */     }
/*     */     
/* 816 */     if (LU.nRow != permutation.getSize()) {
/* 817 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector24"));
/*     */     }
/*     */     
/* 820 */     if (LU.nRow != LU.nCol) {
/* 821 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector25"));
/*     */     }
/*     */     int i;
/* 824 */     for (i = 0; i < LU.nRow; i++) {
/* 825 */       for (int j = 0; j < LU.nCol; j++) {
/* 826 */         temp[i * LU.nCol + j] = LU.values[i][j];
/*     */       }
/*     */     } 
/*     */     
/* 830 */     for (i = 0; i < size; ) { result[i] = 0.0D; i++; }
/* 831 */      for (i = 0; i < LU.nRow; ) { result[i * LU.nCol] = b.values[i]; i++; }
/* 832 */      for (i = 0; i < LU.nCol; ) { row_perm[i] = (int)permutation.values[i]; i++; }
/*     */     
/* 834 */     GMatrix.luBacksubstitution(LU.nRow, temp, row_perm, result);
/*     */     
/* 836 */     for (i = 0; i < LU.nRow; ) { this.values[i] = result[i * LU.nCol]; i++; }
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
/*     */   public final double angle(GVector v1) {
/* 848 */     return Math.acos(dot(v1) / norm() * v1.norm());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(GVector v1, GVector v2, float alpha) {
/* 856 */     interpolate(v1, v2, alpha);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void interpolate(GVector v1, float alpha) {
/* 864 */     interpolate(v1, alpha);
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
/*     */   public final void interpolate(GVector v1, GVector v2, double alpha) {
/* 877 */     if (v2.length != v1.length) {
/* 878 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector20"));
/*     */     }
/* 880 */     if (this.length != v1.length) {
/* 881 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector21"));
/*     */     }
/* 883 */     for (int i = 0; i < this.length; i++) {
/* 884 */       this.values[i] = (1.0D - alpha) * v1.values[i] + alpha * v2.values[i];
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
/*     */   public final void interpolate(GVector v1, double alpha) {
/* 896 */     if (v1.length != this.length) {
/* 897 */       throw new MismatchedSizeException(VecMathI18N.getString("GVector22"));
/*     */     }
/* 899 */     for (int i = 0; i < this.length; i++) {
/* 900 */       this.values[i] = (1.0D - alpha) * this.values[i] + alpha * v1.values[i];
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
/*     */   public Object clone() {
/* 914 */     GVector v1 = null;
/*     */     try {
/* 916 */       v1 = (GVector)super.clone();
/* 917 */     } catch (CloneNotSupportedException e) {
/*     */       
/* 919 */       throw new InternalError();
/*     */     } 
/*     */ 
/*     */     
/* 923 */     v1.values = new double[this.length];
/* 924 */     for (int i = 0; i < this.length; i++) {
/* 925 */       v1.values[i] = this.values[i];
/*     */     }
/*     */     
/* 928 */     return v1;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\GVector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */