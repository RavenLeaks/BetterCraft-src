/*      */ package javax.vecmath;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Matrix4d
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 8223903484171633710L;
/*      */   public double m00;
/*      */   public double m01;
/*      */   public double m02;
/*      */   public double m03;
/*      */   public double m10;
/*      */   public double m11;
/*      */   public double m12;
/*      */   public double m13;
/*      */   public double m20;
/*      */   public double m21;
/*      */   public double m22;
/*      */   public double m23;
/*      */   public double m30;
/*      */   public double m31;
/*      */   public double m32;
/*      */   public double m33;
/*      */   private static final double EPS = 1.0E-10D;
/*      */   
/*      */   public Matrix4d(double m00, double m01, double m02, double m03, double m10, double m11, double m12, double m13, double m20, double m21, double m22, double m23, double m30, double m31, double m32, double m33) {
/*  151 */     this.m00 = m00;
/*  152 */     this.m01 = m01;
/*  153 */     this.m02 = m02;
/*  154 */     this.m03 = m03;
/*      */     
/*  156 */     this.m10 = m10;
/*  157 */     this.m11 = m11;
/*  158 */     this.m12 = m12;
/*  159 */     this.m13 = m13;
/*      */     
/*  161 */     this.m20 = m20;
/*  162 */     this.m21 = m21;
/*  163 */     this.m22 = m22;
/*  164 */     this.m23 = m23;
/*      */     
/*  166 */     this.m30 = m30;
/*  167 */     this.m31 = m31;
/*  168 */     this.m32 = m32;
/*  169 */     this.m33 = m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(double[] v) {
/*  180 */     this.m00 = v[0];
/*  181 */     this.m01 = v[1];
/*  182 */     this.m02 = v[2];
/*  183 */     this.m03 = v[3];
/*      */     
/*  185 */     this.m10 = v[4];
/*  186 */     this.m11 = v[5];
/*  187 */     this.m12 = v[6];
/*  188 */     this.m13 = v[7];
/*      */     
/*  190 */     this.m20 = v[8];
/*  191 */     this.m21 = v[9];
/*  192 */     this.m22 = v[10];
/*  193 */     this.m23 = v[11];
/*      */     
/*  195 */     this.m30 = v[12];
/*  196 */     this.m31 = v[13];
/*  197 */     this.m32 = v[14];
/*  198 */     this.m33 = v[15];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(Quat4d q1, Vector3d t1, double s) {
/*  213 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/*  214 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/*  215 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/*  217 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/*  218 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/*  219 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/*  221 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/*  222 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/*  223 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/*  225 */     this.m03 = t1.x;
/*  226 */     this.m13 = t1.y;
/*  227 */     this.m23 = t1.z;
/*      */     
/*  229 */     this.m30 = 0.0D;
/*  230 */     this.m31 = 0.0D;
/*  231 */     this.m32 = 0.0D;
/*  232 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(Quat4f q1, Vector3d t1, double s) {
/*  247 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/*  248 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/*  249 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/*  251 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/*  252 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/*  253 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/*  255 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/*  256 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/*  257 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/*  259 */     this.m03 = t1.x;
/*  260 */     this.m13 = t1.y;
/*  261 */     this.m23 = t1.z;
/*      */     
/*  263 */     this.m30 = 0.0D;
/*  264 */     this.m31 = 0.0D;
/*  265 */     this.m32 = 0.0D;
/*  266 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(Matrix4d m1) {
/*  277 */     this.m00 = m1.m00;
/*  278 */     this.m01 = m1.m01;
/*  279 */     this.m02 = m1.m02;
/*  280 */     this.m03 = m1.m03;
/*      */     
/*  282 */     this.m10 = m1.m10;
/*  283 */     this.m11 = m1.m11;
/*  284 */     this.m12 = m1.m12;
/*  285 */     this.m13 = m1.m13;
/*      */     
/*  287 */     this.m20 = m1.m20;
/*  288 */     this.m21 = m1.m21;
/*  289 */     this.m22 = m1.m22;
/*  290 */     this.m23 = m1.m23;
/*      */     
/*  292 */     this.m30 = m1.m30;
/*  293 */     this.m31 = m1.m31;
/*  294 */     this.m32 = m1.m32;
/*  295 */     this.m33 = m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(Matrix4f m1) {
/*  306 */     this.m00 = m1.m00;
/*  307 */     this.m01 = m1.m01;
/*  308 */     this.m02 = m1.m02;
/*  309 */     this.m03 = m1.m03;
/*      */     
/*  311 */     this.m10 = m1.m10;
/*  312 */     this.m11 = m1.m11;
/*  313 */     this.m12 = m1.m12;
/*  314 */     this.m13 = m1.m13;
/*      */     
/*  316 */     this.m20 = m1.m20;
/*  317 */     this.m21 = m1.m21;
/*  318 */     this.m22 = m1.m22;
/*  319 */     this.m23 = m1.m23;
/*      */     
/*  321 */     this.m30 = m1.m30;
/*  322 */     this.m31 = m1.m31;
/*  323 */     this.m32 = m1.m32;
/*  324 */     this.m33 = m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(Matrix3f m1, Vector3d t1, double s) {
/*  339 */     this.m00 = m1.m00 * s;
/*  340 */     this.m01 = m1.m01 * s;
/*  341 */     this.m02 = m1.m02 * s;
/*  342 */     this.m03 = t1.x;
/*      */     
/*  344 */     this.m10 = m1.m10 * s;
/*  345 */     this.m11 = m1.m11 * s;
/*  346 */     this.m12 = m1.m12 * s;
/*  347 */     this.m13 = t1.y;
/*      */     
/*  349 */     this.m20 = m1.m20 * s;
/*  350 */     this.m21 = m1.m21 * s;
/*  351 */     this.m22 = m1.m22 * s;
/*  352 */     this.m23 = t1.z;
/*      */     
/*  354 */     this.m30 = 0.0D;
/*  355 */     this.m31 = 0.0D;
/*  356 */     this.m32 = 0.0D;
/*  357 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d(Matrix3d m1, Vector3d t1, double s) {
/*  372 */     this.m00 = m1.m00 * s;
/*  373 */     this.m01 = m1.m01 * s;
/*  374 */     this.m02 = m1.m02 * s;
/*  375 */     this.m03 = t1.x;
/*      */     
/*  377 */     this.m10 = m1.m10 * s;
/*  378 */     this.m11 = m1.m11 * s;
/*  379 */     this.m12 = m1.m12 * s;
/*  380 */     this.m13 = t1.y;
/*      */     
/*  382 */     this.m20 = m1.m20 * s;
/*  383 */     this.m21 = m1.m21 * s;
/*  384 */     this.m22 = m1.m22 * s;
/*  385 */     this.m23 = t1.z;
/*      */     
/*  387 */     this.m30 = 0.0D;
/*  388 */     this.m31 = 0.0D;
/*  389 */     this.m32 = 0.0D;
/*  390 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4d() {
/*  399 */     this.m00 = 0.0D;
/*  400 */     this.m01 = 0.0D;
/*  401 */     this.m02 = 0.0D;
/*  402 */     this.m03 = 0.0D;
/*      */     
/*  404 */     this.m10 = 0.0D;
/*  405 */     this.m11 = 0.0D;
/*  406 */     this.m12 = 0.0D;
/*  407 */     this.m13 = 0.0D;
/*      */     
/*  409 */     this.m20 = 0.0D;
/*  410 */     this.m21 = 0.0D;
/*  411 */     this.m22 = 0.0D;
/*  412 */     this.m23 = 0.0D;
/*      */     
/*  414 */     this.m30 = 0.0D;
/*  415 */     this.m31 = 0.0D;
/*  416 */     this.m32 = 0.0D;
/*  417 */     this.m33 = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  427 */     return 
/*  428 */       String.valueOf(this.m00) + ", " + this.m01 + ", " + this.m02 + ", " + this.m03 + "\n" + 
/*  429 */       this.m10 + ", " + this.m11 + ", " + this.m12 + ", " + this.m13 + "\n" + 
/*  430 */       this.m20 + ", " + this.m21 + ", " + this.m22 + ", " + this.m23 + "\n" + 
/*  431 */       this.m30 + ", " + this.m31 + ", " + this.m32 + ", " + this.m33 + "\n";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIdentity() {
/*  439 */     this.m00 = 1.0D;
/*  440 */     this.m01 = 0.0D;
/*  441 */     this.m02 = 0.0D;
/*  442 */     this.m03 = 0.0D;
/*      */     
/*  444 */     this.m10 = 0.0D;
/*  445 */     this.m11 = 1.0D;
/*  446 */     this.m12 = 0.0D;
/*  447 */     this.m13 = 0.0D;
/*      */     
/*  449 */     this.m20 = 0.0D;
/*  450 */     this.m21 = 0.0D;
/*  451 */     this.m22 = 1.0D;
/*  452 */     this.m23 = 0.0D;
/*      */     
/*  454 */     this.m30 = 0.0D;
/*  455 */     this.m31 = 0.0D;
/*  456 */     this.m32 = 0.0D;
/*  457 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setElement(int row, int column, double value) {
/*  468 */     switch (row) {
/*      */       
/*      */       case 0:
/*  471 */         switch (column) {
/*      */           
/*      */           case 0:
/*  474 */             this.m00 = value;
/*      */             return;
/*      */           case 1:
/*  477 */             this.m01 = value;
/*      */             return;
/*      */           case 2:
/*  480 */             this.m02 = value;
/*      */             return;
/*      */           case 3:
/*  483 */             this.m03 = value;
/*      */             return;
/*      */         } 
/*  486 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  491 */         switch (column) {
/*      */           
/*      */           case 0:
/*  494 */             this.m10 = value;
/*      */             return;
/*      */           case 1:
/*  497 */             this.m11 = value;
/*      */             return;
/*      */           case 2:
/*  500 */             this.m12 = value;
/*      */             return;
/*      */           case 3:
/*  503 */             this.m13 = value;
/*      */             return;
/*      */         } 
/*  506 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  511 */         switch (column) {
/*      */           
/*      */           case 0:
/*  514 */             this.m20 = value;
/*      */             return;
/*      */           case 1:
/*  517 */             this.m21 = value;
/*      */             return;
/*      */           case 2:
/*  520 */             this.m22 = value;
/*      */             return;
/*      */           case 3:
/*  523 */             this.m23 = value;
/*      */             return;
/*      */         } 
/*  526 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  531 */         switch (column) {
/*      */           
/*      */           case 0:
/*  534 */             this.m30 = value;
/*      */             return;
/*      */           case 1:
/*  537 */             this.m31 = value;
/*      */             return;
/*      */           case 2:
/*  540 */             this.m32 = value;
/*      */             return;
/*      */           case 3:
/*  543 */             this.m33 = value;
/*      */             return;
/*      */         } 
/*  546 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  551 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d0"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getElement(int row, int column) {
/*  563 */     switch (row) {
/*      */       
/*      */       case 0:
/*  566 */         switch (column) {
/*      */           
/*      */           case 0:
/*  569 */             return this.m00;
/*      */           case 1:
/*  571 */             return this.m01;
/*      */           case 2:
/*  573 */             return this.m02;
/*      */           case 3:
/*  575 */             return this.m03;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  581 */         switch (column) {
/*      */           
/*      */           case 0:
/*  584 */             return this.m10;
/*      */           case 1:
/*  586 */             return this.m11;
/*      */           case 2:
/*  588 */             return this.m12;
/*      */           case 3:
/*  590 */             return this.m13;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  597 */         switch (column) {
/*      */           
/*      */           case 0:
/*  600 */             return this.m20;
/*      */           case 1:
/*  602 */             return this.m21;
/*      */           case 2:
/*  604 */             return this.m22;
/*      */           case 3:
/*  606 */             return this.m23;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 3:
/*  613 */         switch (column) {
/*      */           
/*      */           case 0:
/*  616 */             return this.m30;
/*      */           case 1:
/*  618 */             return this.m31;
/*      */           case 2:
/*  620 */             return this.m32;
/*      */           case 3:
/*  622 */             return this.m33;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  631 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d1"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, Vector4d v) {
/*  640 */     if (row == 0) {
/*  641 */       v.x = this.m00;
/*  642 */       v.y = this.m01;
/*  643 */       v.z = this.m02;
/*  644 */       v.w = this.m03;
/*  645 */     } else if (row == 1) {
/*  646 */       v.x = this.m10;
/*  647 */       v.y = this.m11;
/*  648 */       v.z = this.m12;
/*  649 */       v.w = this.m13;
/*  650 */     } else if (row == 2) {
/*  651 */       v.x = this.m20;
/*  652 */       v.y = this.m21;
/*  653 */       v.z = this.m22;
/*  654 */       v.w = this.m23;
/*  655 */     } else if (row == 3) {
/*  656 */       v.x = this.m30;
/*  657 */       v.y = this.m31;
/*  658 */       v.z = this.m32;
/*  659 */       v.w = this.m33;
/*      */     } else {
/*  661 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d2"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, double[] v) {
/*  672 */     if (row == 0) {
/*  673 */       v[0] = this.m00;
/*  674 */       v[1] = this.m01;
/*  675 */       v[2] = this.m02;
/*  676 */       v[3] = this.m03;
/*  677 */     } else if (row == 1) {
/*  678 */       v[0] = this.m10;
/*  679 */       v[1] = this.m11;
/*  680 */       v[2] = this.m12;
/*  681 */       v[3] = this.m13;
/*  682 */     } else if (row == 2) {
/*  683 */       v[0] = this.m20;
/*  684 */       v[1] = this.m21;
/*  685 */       v[2] = this.m22;
/*  686 */       v[3] = this.m23;
/*  687 */     } else if (row == 3) {
/*  688 */       v[0] = this.m30;
/*  689 */       v[1] = this.m31;
/*  690 */       v[2] = this.m32;
/*  691 */       v[3] = this.m33;
/*      */     } else {
/*      */       
/*  694 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d2"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getColumn(int column, Vector4d v) {
/*  707 */     if (column == 0) {
/*  708 */       v.x = this.m00;
/*  709 */       v.y = this.m10;
/*  710 */       v.z = this.m20;
/*  711 */       v.w = this.m30;
/*  712 */     } else if (column == 1) {
/*  713 */       v.x = this.m01;
/*  714 */       v.y = this.m11;
/*  715 */       v.z = this.m21;
/*  716 */       v.w = this.m31;
/*  717 */     } else if (column == 2) {
/*  718 */       v.x = this.m02;
/*  719 */       v.y = this.m12;
/*  720 */       v.z = this.m22;
/*  721 */       v.w = this.m32;
/*  722 */     } else if (column == 3) {
/*  723 */       v.x = this.m03;
/*  724 */       v.y = this.m13;
/*  725 */       v.z = this.m23;
/*  726 */       v.w = this.m33;
/*      */     } else {
/*  728 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d3"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getColumn(int column, double[] v) {
/*  743 */     if (column == 0) {
/*  744 */       v[0] = this.m00;
/*  745 */       v[1] = this.m10;
/*  746 */       v[2] = this.m20;
/*  747 */       v[3] = this.m30;
/*  748 */     } else if (column == 1) {
/*  749 */       v[0] = this.m01;
/*  750 */       v[1] = this.m11;
/*  751 */       v[2] = this.m21;
/*  752 */       v[3] = this.m31;
/*  753 */     } else if (column == 2) {
/*  754 */       v[0] = this.m02;
/*  755 */       v[1] = this.m12;
/*  756 */       v[2] = this.m22;
/*  757 */       v[3] = this.m32;
/*  758 */     } else if (column == 3) {
/*  759 */       v[0] = this.m03;
/*  760 */       v[1] = this.m13;
/*  761 */       v[2] = this.m23;
/*  762 */       v[3] = this.m33;
/*      */     } else {
/*  764 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d3"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Matrix3d m1) {
/*  780 */     double[] tmp_rot = new double[9];
/*  781 */     double[] tmp_scale = new double[3];
/*  782 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  784 */     m1.m00 = tmp_rot[0];
/*  785 */     m1.m01 = tmp_rot[1];
/*  786 */     m1.m02 = tmp_rot[2];
/*      */     
/*  788 */     m1.m10 = tmp_rot[3];
/*  789 */     m1.m11 = tmp_rot[4];
/*  790 */     m1.m12 = tmp_rot[5];
/*      */     
/*  792 */     m1.m20 = tmp_rot[6];
/*  793 */     m1.m21 = tmp_rot[7];
/*  794 */     m1.m22 = tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Matrix3f m1) {
/*  807 */     double[] tmp_rot = new double[9];
/*  808 */     double[] tmp_scale = new double[3];
/*      */     
/*  810 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  812 */     m1.m00 = (float)tmp_rot[0];
/*  813 */     m1.m01 = (float)tmp_rot[1];
/*  814 */     m1.m02 = (float)tmp_rot[2];
/*      */     
/*  816 */     m1.m10 = (float)tmp_rot[3];
/*  817 */     m1.m11 = (float)tmp_rot[4];
/*  818 */     m1.m12 = (float)tmp_rot[5];
/*      */     
/*  820 */     m1.m20 = (float)tmp_rot[6];
/*  821 */     m1.m21 = (float)tmp_rot[7];
/*  822 */     m1.m22 = (float)tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double get(Matrix3d m1, Vector3d t1) {
/*  836 */     double[] tmp_rot = new double[9];
/*  837 */     double[] tmp_scale = new double[3];
/*  838 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  840 */     m1.m00 = tmp_rot[0];
/*  841 */     m1.m01 = tmp_rot[1];
/*  842 */     m1.m02 = tmp_rot[2];
/*      */     
/*  844 */     m1.m10 = tmp_rot[3];
/*  845 */     m1.m11 = tmp_rot[4];
/*  846 */     m1.m12 = tmp_rot[5];
/*      */     
/*  848 */     m1.m20 = tmp_rot[6];
/*  849 */     m1.m21 = tmp_rot[7];
/*  850 */     m1.m22 = tmp_rot[8];
/*      */     
/*  852 */     t1.x = this.m03;
/*  853 */     t1.y = this.m13;
/*  854 */     t1.z = this.m23;
/*      */     
/*  856 */     return Matrix3d.max3(tmp_scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double get(Matrix3f m1, Vector3d t1) {
/*  870 */     double[] tmp_rot = new double[9];
/*  871 */     double[] tmp_scale = new double[3];
/*  872 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  874 */     m1.m00 = (float)tmp_rot[0];
/*  875 */     m1.m01 = (float)tmp_rot[1];
/*  876 */     m1.m02 = (float)tmp_rot[2];
/*      */     
/*  878 */     m1.m10 = (float)tmp_rot[3];
/*  879 */     m1.m11 = (float)tmp_rot[4];
/*  880 */     m1.m12 = (float)tmp_rot[5];
/*      */     
/*  882 */     m1.m20 = (float)tmp_rot[6];
/*  883 */     m1.m21 = (float)tmp_rot[7];
/*  884 */     m1.m22 = (float)tmp_rot[8];
/*      */     
/*  886 */     t1.x = this.m03;
/*  887 */     t1.y = this.m13;
/*  888 */     t1.z = this.m23;
/*      */     
/*  890 */     return Matrix3d.max3(tmp_scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Quat4f q1) {
/*  903 */     double[] tmp_rot = new double[9];
/*  904 */     double[] tmp_scale = new double[3];
/*  905 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */ 
/*      */ 
/*      */     
/*  909 */     double ww = 0.25D * (1.0D + tmp_rot[0] + tmp_rot[4] + tmp_rot[8]);
/*  910 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  911 */       q1.w = (float)Math.sqrt(ww);
/*  912 */       ww = 0.25D / q1.w;
/*  913 */       q1.x = (float)((tmp_rot[7] - tmp_rot[5]) * ww);
/*  914 */       q1.y = (float)((tmp_rot[2] - tmp_rot[6]) * ww);
/*  915 */       q1.z = (float)((tmp_rot[3] - tmp_rot[1]) * ww);
/*      */       
/*      */       return;
/*      */     } 
/*  919 */     q1.w = 0.0F;
/*  920 */     ww = -0.5D * (tmp_rot[4] + tmp_rot[8]);
/*  921 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  922 */       q1.x = (float)Math.sqrt(ww);
/*  923 */       ww = 0.5D / q1.x;
/*  924 */       q1.y = (float)(tmp_rot[3] * ww);
/*  925 */       q1.z = (float)(tmp_rot[6] * ww);
/*      */       
/*      */       return;
/*      */     } 
/*  929 */     q1.x = 0.0F;
/*  930 */     ww = 0.5D * (1.0D - tmp_rot[8]);
/*  931 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  932 */       q1.y = (float)Math.sqrt(ww);
/*  933 */       q1.z = (float)(tmp_rot[7] / 2.0D * q1.y);
/*      */       
/*      */       return;
/*      */     } 
/*  937 */     q1.y = 0.0F;
/*  938 */     q1.z = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Quat4d q1) {
/*  950 */     double[] tmp_rot = new double[9];
/*  951 */     double[] tmp_scale = new double[3];
/*      */     
/*  953 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */ 
/*      */ 
/*      */     
/*  957 */     double ww = 0.25D * (1.0D + tmp_rot[0] + tmp_rot[4] + tmp_rot[8]);
/*  958 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  959 */       q1.w = Math.sqrt(ww);
/*  960 */       ww = 0.25D / q1.w;
/*  961 */       q1.x = (tmp_rot[7] - tmp_rot[5]) * ww;
/*  962 */       q1.y = (tmp_rot[2] - tmp_rot[6]) * ww;
/*  963 */       q1.z = (tmp_rot[3] - tmp_rot[1]) * ww;
/*      */       
/*      */       return;
/*      */     } 
/*  967 */     q1.w = 0.0D;
/*  968 */     ww = -0.5D * (tmp_rot[4] + tmp_rot[8]);
/*  969 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  970 */       q1.x = Math.sqrt(ww);
/*  971 */       ww = 0.5D / q1.x;
/*  972 */       q1.y = tmp_rot[3] * ww;
/*  973 */       q1.z = tmp_rot[6] * ww;
/*      */       
/*      */       return;
/*      */     } 
/*  977 */     q1.x = 0.0D;
/*  978 */     ww = 0.5D * (1.0D - tmp_rot[8]);
/*  979 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  980 */       q1.y = Math.sqrt(ww);
/*  981 */       q1.z = tmp_rot[7] / 2.0D * q1.y;
/*      */       
/*      */       return;
/*      */     } 
/*  985 */     q1.y = 0.0D;
/*  986 */     q1.z = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Vector3d trans) {
/*  995 */     trans.x = this.m03;
/*  996 */     trans.y = this.m13;
/*  997 */     trans.z = this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRotationScale(Matrix3f m1) {
/* 1007 */     m1.m00 = (float)this.m00; m1.m01 = (float)this.m01; m1.m02 = (float)this.m02;
/* 1008 */     m1.m10 = (float)this.m10; m1.m11 = (float)this.m11; m1.m12 = (float)this.m12;
/* 1009 */     m1.m20 = (float)this.m20; m1.m21 = (float)this.m21; m1.m22 = (float)this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRotationScale(Matrix3d m1) {
/* 1019 */     m1.m00 = this.m00; m1.m01 = this.m01; m1.m02 = this.m02;
/* 1020 */     m1.m10 = this.m10; m1.m11 = this.m11; m1.m12 = this.m12;
/* 1021 */     m1.m20 = this.m20; m1.m21 = this.m21; m1.m22 = this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getScale() {
/* 1034 */     double[] tmp_rot = new double[9];
/* 1035 */     double[] tmp_scale = new double[3];
/* 1036 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 1038 */     return Matrix3d.max3(tmp_scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotationScale(Matrix3d m1) {
/* 1049 */     this.m00 = m1.m00; this.m01 = m1.m01; this.m02 = m1.m02;
/* 1050 */     this.m10 = m1.m10; this.m11 = m1.m11; this.m12 = m1.m12;
/* 1051 */     this.m20 = m1.m20; this.m21 = m1.m21; this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotationScale(Matrix3f m1) {
/* 1061 */     this.m00 = m1.m00; this.m01 = m1.m01; this.m02 = m1.m02;
/* 1062 */     this.m10 = m1.m10; this.m11 = m1.m11; this.m12 = m1.m12;
/* 1063 */     this.m20 = m1.m20; this.m21 = m1.m21; this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setScale(double scale) {
/* 1074 */     double[] tmp_rot = new double[9];
/* 1075 */     double[] tmp_scale = new double[3];
/*      */     
/* 1077 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 1079 */     this.m00 = tmp_rot[0] * scale;
/* 1080 */     this.m01 = tmp_rot[1] * scale;
/* 1081 */     this.m02 = tmp_rot[2] * scale;
/*      */     
/* 1083 */     this.m10 = tmp_rot[3] * scale;
/* 1084 */     this.m11 = tmp_rot[4] * scale;
/* 1085 */     this.m12 = tmp_rot[5] * scale;
/*      */     
/* 1087 */     this.m20 = tmp_rot[6] * scale;
/* 1088 */     this.m21 = tmp_rot[7] * scale;
/* 1089 */     this.m22 = tmp_rot[8] * scale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, double x, double y, double z, double w) {
/* 1103 */     switch (row) {
/*      */       case 0:
/* 1105 */         this.m00 = x;
/* 1106 */         this.m01 = y;
/* 1107 */         this.m02 = z;
/* 1108 */         this.m03 = w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1112 */         this.m10 = x;
/* 1113 */         this.m11 = y;
/* 1114 */         this.m12 = z;
/* 1115 */         this.m13 = w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1119 */         this.m20 = x;
/* 1120 */         this.m21 = y;
/* 1121 */         this.m22 = z;
/* 1122 */         this.m23 = w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1126 */         this.m30 = x;
/* 1127 */         this.m31 = y;
/* 1128 */         this.m32 = z;
/* 1129 */         this.m33 = w;
/*      */         return;
/*      */     } 
/*      */     
/* 1133 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, Vector4d v) {
/* 1145 */     switch (row) {
/*      */       case 0:
/* 1147 */         this.m00 = v.x;
/* 1148 */         this.m01 = v.y;
/* 1149 */         this.m02 = v.z;
/* 1150 */         this.m03 = v.w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1154 */         this.m10 = v.x;
/* 1155 */         this.m11 = v.y;
/* 1156 */         this.m12 = v.z;
/* 1157 */         this.m13 = v.w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1161 */         this.m20 = v.x;
/* 1162 */         this.m21 = v.y;
/* 1163 */         this.m22 = v.z;
/* 1164 */         this.m23 = v.w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1168 */         this.m30 = v.x;
/* 1169 */         this.m31 = v.y;
/* 1170 */         this.m32 = v.z;
/* 1171 */         this.m33 = v.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1175 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, double[] v) {
/* 1186 */     switch (row) {
/*      */       case 0:
/* 1188 */         this.m00 = v[0];
/* 1189 */         this.m01 = v[1];
/* 1190 */         this.m02 = v[2];
/* 1191 */         this.m03 = v[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1195 */         this.m10 = v[0];
/* 1196 */         this.m11 = v[1];
/* 1197 */         this.m12 = v[2];
/* 1198 */         this.m13 = v[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1202 */         this.m20 = v[0];
/* 1203 */         this.m21 = v[1];
/* 1204 */         this.m22 = v[2];
/* 1205 */         this.m23 = v[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1209 */         this.m30 = v[0];
/* 1210 */         this.m31 = v[1];
/* 1211 */         this.m32 = v[2];
/* 1212 */         this.m33 = v[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1216 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d4"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, double x, double y, double z, double w) {
/* 1230 */     switch (column) {
/*      */       case 0:
/* 1232 */         this.m00 = x;
/* 1233 */         this.m10 = y;
/* 1234 */         this.m20 = z;
/* 1235 */         this.m30 = w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1239 */         this.m01 = x;
/* 1240 */         this.m11 = y;
/* 1241 */         this.m21 = z;
/* 1242 */         this.m31 = w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1246 */         this.m02 = x;
/* 1247 */         this.m12 = y;
/* 1248 */         this.m22 = z;
/* 1249 */         this.m32 = w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1253 */         this.m03 = x;
/* 1254 */         this.m13 = y;
/* 1255 */         this.m23 = z;
/* 1256 */         this.m33 = w;
/*      */         return;
/*      */     } 
/*      */     
/* 1260 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, Vector4d v) {
/* 1271 */     switch (column) {
/*      */       case 0:
/* 1273 */         this.m00 = v.x;
/* 1274 */         this.m10 = v.y;
/* 1275 */         this.m20 = v.z;
/* 1276 */         this.m30 = v.w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1280 */         this.m01 = v.x;
/* 1281 */         this.m11 = v.y;
/* 1282 */         this.m21 = v.z;
/* 1283 */         this.m31 = v.w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1287 */         this.m02 = v.x;
/* 1288 */         this.m12 = v.y;
/* 1289 */         this.m22 = v.z;
/* 1290 */         this.m32 = v.w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1294 */         this.m03 = v.x;
/* 1295 */         this.m13 = v.y;
/* 1296 */         this.m23 = v.z;
/* 1297 */         this.m33 = v.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1301 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, double[] v) {
/* 1312 */     switch (column) {
/*      */       case 0:
/* 1314 */         this.m00 = v[0];
/* 1315 */         this.m10 = v[1];
/* 1316 */         this.m20 = v[2];
/* 1317 */         this.m30 = v[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1321 */         this.m01 = v[0];
/* 1322 */         this.m11 = v[1];
/* 1323 */         this.m21 = v[2];
/* 1324 */         this.m31 = v[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1328 */         this.m02 = v[0];
/* 1329 */         this.m12 = v[1];
/* 1330 */         this.m22 = v[2];
/* 1331 */         this.m32 = v[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1335 */         this.m03 = v[0];
/* 1336 */         this.m13 = v[1];
/* 1337 */         this.m23 = v[2];
/* 1338 */         this.m33 = v[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1342 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4d7"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(double scalar) {
/* 1352 */     this.m00 += scalar;
/* 1353 */     this.m01 += scalar;
/* 1354 */     this.m02 += scalar;
/* 1355 */     this.m03 += scalar;
/* 1356 */     this.m10 += scalar;
/* 1357 */     this.m11 += scalar;
/* 1358 */     this.m12 += scalar;
/* 1359 */     this.m13 += scalar;
/* 1360 */     this.m20 += scalar;
/* 1361 */     this.m21 += scalar;
/* 1362 */     this.m22 += scalar;
/* 1363 */     this.m23 += scalar;
/* 1364 */     this.m30 += scalar;
/* 1365 */     this.m31 += scalar;
/* 1366 */     this.m32 += scalar;
/* 1367 */     this.m33 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(double scalar, Matrix4d m1) {
/* 1378 */     m1.m00 += scalar;
/* 1379 */     m1.m01 += scalar;
/* 1380 */     m1.m02 += scalar;
/* 1381 */     m1.m03 += scalar;
/* 1382 */     m1.m10 += scalar;
/* 1383 */     m1.m11 += scalar;
/* 1384 */     m1.m12 += scalar;
/* 1385 */     m1.m13 += scalar;
/* 1386 */     m1.m20 += scalar;
/* 1387 */     m1.m21 += scalar;
/* 1388 */     m1.m22 += scalar;
/* 1389 */     m1.m23 += scalar;
/* 1390 */     m1.m30 += scalar;
/* 1391 */     m1.m31 += scalar;
/* 1392 */     m1.m32 += scalar;
/* 1393 */     m1.m33 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4d m1, Matrix4d m2) {
/* 1403 */     m1.m00 += m2.m00;
/* 1404 */     m1.m01 += m2.m01;
/* 1405 */     m1.m02 += m2.m02;
/* 1406 */     m1.m03 += m2.m03;
/*      */     
/* 1408 */     m1.m10 += m2.m10;
/* 1409 */     m1.m11 += m2.m11;
/* 1410 */     m1.m12 += m2.m12;
/* 1411 */     m1.m13 += m2.m13;
/*      */     
/* 1413 */     m1.m20 += m2.m20;
/* 1414 */     m1.m21 += m2.m21;
/* 1415 */     m1.m22 += m2.m22;
/* 1416 */     m1.m23 += m2.m23;
/*      */     
/* 1418 */     m1.m30 += m2.m30;
/* 1419 */     m1.m31 += m2.m31;
/* 1420 */     m1.m32 += m2.m32;
/* 1421 */     m1.m33 += m2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4d m1) {
/* 1430 */     this.m00 += m1.m00;
/* 1431 */     this.m01 += m1.m01;
/* 1432 */     this.m02 += m1.m02;
/* 1433 */     this.m03 += m1.m03;
/*      */     
/* 1435 */     this.m10 += m1.m10;
/* 1436 */     this.m11 += m1.m11;
/* 1437 */     this.m12 += m1.m12;
/* 1438 */     this.m13 += m1.m13;
/*      */     
/* 1440 */     this.m20 += m1.m20;
/* 1441 */     this.m21 += m1.m21;
/* 1442 */     this.m22 += m1.m22;
/* 1443 */     this.m23 += m1.m23;
/*      */     
/* 1445 */     this.m30 += m1.m30;
/* 1446 */     this.m31 += m1.m31;
/* 1447 */     this.m32 += m1.m32;
/* 1448 */     this.m33 += m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix4d m1, Matrix4d m2) {
/* 1459 */     m1.m00 -= m2.m00;
/* 1460 */     m1.m01 -= m2.m01;
/* 1461 */     m1.m02 -= m2.m02;
/* 1462 */     m1.m03 -= m2.m03;
/*      */     
/* 1464 */     m1.m10 -= m2.m10;
/* 1465 */     m1.m11 -= m2.m11;
/* 1466 */     m1.m12 -= m2.m12;
/* 1467 */     m1.m13 -= m2.m13;
/*      */     
/* 1469 */     m1.m20 -= m2.m20;
/* 1470 */     m1.m21 -= m2.m21;
/* 1471 */     m1.m22 -= m2.m22;
/* 1472 */     m1.m23 -= m2.m23;
/*      */     
/* 1474 */     m1.m30 -= m2.m30;
/* 1475 */     m1.m31 -= m2.m31;
/* 1476 */     m1.m32 -= m2.m32;
/* 1477 */     m1.m33 -= m2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix4d m1) {
/* 1488 */     this.m00 -= m1.m00;
/* 1489 */     this.m01 -= m1.m01;
/* 1490 */     this.m02 -= m1.m02;
/* 1491 */     this.m03 -= m1.m03;
/*      */     
/* 1493 */     this.m10 -= m1.m10;
/* 1494 */     this.m11 -= m1.m11;
/* 1495 */     this.m12 -= m1.m12;
/* 1496 */     this.m13 -= m1.m13;
/*      */     
/* 1498 */     this.m20 -= m1.m20;
/* 1499 */     this.m21 -= m1.m21;
/* 1500 */     this.m22 -= m1.m22;
/* 1501 */     this.m23 -= m1.m23;
/*      */     
/* 1503 */     this.m30 -= m1.m30;
/* 1504 */     this.m31 -= m1.m31;
/* 1505 */     this.m32 -= m1.m32;
/* 1506 */     this.m33 -= m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/* 1516 */     double temp = this.m10;
/* 1517 */     this.m10 = this.m01;
/* 1518 */     this.m01 = temp;
/*      */     
/* 1520 */     temp = this.m20;
/* 1521 */     this.m20 = this.m02;
/* 1522 */     this.m02 = temp;
/*      */     
/* 1524 */     temp = this.m30;
/* 1525 */     this.m30 = this.m03;
/* 1526 */     this.m03 = temp;
/*      */     
/* 1528 */     temp = this.m21;
/* 1529 */     this.m21 = this.m12;
/* 1530 */     this.m12 = temp;
/*      */     
/* 1532 */     temp = this.m31;
/* 1533 */     this.m31 = this.m13;
/* 1534 */     this.m13 = temp;
/*      */     
/* 1536 */     temp = this.m32;
/* 1537 */     this.m32 = this.m23;
/* 1538 */     this.m23 = temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix4d m1) {
/* 1547 */     if (this != m1) {
/* 1548 */       this.m00 = m1.m00;
/* 1549 */       this.m01 = m1.m10;
/* 1550 */       this.m02 = m1.m20;
/* 1551 */       this.m03 = m1.m30;
/*      */       
/* 1553 */       this.m10 = m1.m01;
/* 1554 */       this.m11 = m1.m11;
/* 1555 */       this.m12 = m1.m21;
/* 1556 */       this.m13 = m1.m31;
/*      */       
/* 1558 */       this.m20 = m1.m02;
/* 1559 */       this.m21 = m1.m12;
/* 1560 */       this.m22 = m1.m22;
/* 1561 */       this.m23 = m1.m32;
/*      */       
/* 1563 */       this.m30 = m1.m03;
/* 1564 */       this.m31 = m1.m13;
/* 1565 */       this.m32 = m1.m23;
/* 1566 */       this.m33 = m1.m33;
/*      */     } else {
/* 1568 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(double[] m) {
/* 1579 */     this.m00 = m[0];
/* 1580 */     this.m01 = m[1];
/* 1581 */     this.m02 = m[2];
/* 1582 */     this.m03 = m[3];
/* 1583 */     this.m10 = m[4];
/* 1584 */     this.m11 = m[5];
/* 1585 */     this.m12 = m[6];
/* 1586 */     this.m13 = m[7];
/* 1587 */     this.m20 = m[8];
/* 1588 */     this.m21 = m[9];
/* 1589 */     this.m22 = m[10];
/* 1590 */     this.m23 = m[11];
/* 1591 */     this.m30 = m[12];
/* 1592 */     this.m31 = m[13];
/* 1593 */     this.m32 = m[14];
/* 1594 */     this.m33 = m[15];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3f m1) {
/* 1606 */     this.m00 = m1.m00; this.m01 = m1.m01; this.m02 = m1.m02; this.m03 = 0.0D;
/* 1607 */     this.m10 = m1.m10; this.m11 = m1.m11; this.m12 = m1.m12; this.m13 = 0.0D;
/* 1608 */     this.m20 = m1.m20; this.m21 = m1.m21; this.m22 = m1.m22; this.m23 = 0.0D;
/* 1609 */     this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3d m1) {
/* 1621 */     this.m00 = m1.m00; this.m01 = m1.m01; this.m02 = m1.m02; this.m03 = 0.0D;
/* 1622 */     this.m10 = m1.m10; this.m11 = m1.m11; this.m12 = m1.m12; this.m13 = 0.0D;
/* 1623 */     this.m20 = m1.m20; this.m21 = m1.m21; this.m22 = m1.m22; this.m23 = 0.0D;
/* 1624 */     this.m30 = 0.0D; this.m31 = 0.0D; this.m32 = 0.0D; this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d q1) {
/* 1634 */     this.m00 = 1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z;
/* 1635 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1636 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1638 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1639 */     this.m11 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z;
/* 1640 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1642 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1643 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1644 */     this.m22 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y;
/*      */     
/* 1646 */     this.m03 = 0.0D;
/* 1647 */     this.m13 = 0.0D;
/* 1648 */     this.m23 = 0.0D;
/*      */     
/* 1650 */     this.m30 = 0.0D;
/* 1651 */     this.m31 = 0.0D;
/* 1652 */     this.m32 = 0.0D;
/* 1653 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4d a1) {
/* 1663 */     double mag = Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/*      */     
/* 1665 */     if (mag < 1.0E-10D) {
/* 1666 */       this.m00 = 1.0D;
/* 1667 */       this.m01 = 0.0D;
/* 1668 */       this.m02 = 0.0D;
/*      */       
/* 1670 */       this.m10 = 0.0D;
/* 1671 */       this.m11 = 1.0D;
/* 1672 */       this.m12 = 0.0D;
/*      */       
/* 1674 */       this.m20 = 0.0D;
/* 1675 */       this.m21 = 0.0D;
/* 1676 */       this.m22 = 1.0D;
/*      */     } else {
/* 1678 */       mag = 1.0D / mag;
/* 1679 */       double ax = a1.x * mag;
/* 1680 */       double ay = a1.y * mag;
/* 1681 */       double az = a1.z * mag;
/*      */       
/* 1683 */       double sinTheta = Math.sin(a1.angle);
/* 1684 */       double cosTheta = Math.cos(a1.angle);
/* 1685 */       double t = 1.0D - cosTheta;
/*      */       
/* 1687 */       double xz = ax * az;
/* 1688 */       double xy = ax * ay;
/* 1689 */       double yz = ay * az;
/*      */       
/* 1691 */       this.m00 = t * ax * ax + cosTheta;
/* 1692 */       this.m01 = t * xy - sinTheta * az;
/* 1693 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/* 1695 */       this.m10 = t * xy + sinTheta * az;
/* 1696 */       this.m11 = t * ay * ay + cosTheta;
/* 1697 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/* 1699 */       this.m20 = t * xz - sinTheta * ay;
/* 1700 */       this.m21 = t * yz + sinTheta * ax;
/* 1701 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/*      */     
/* 1704 */     this.m03 = 0.0D;
/* 1705 */     this.m13 = 0.0D;
/* 1706 */     this.m23 = 0.0D;
/*      */     
/* 1708 */     this.m30 = 0.0D;
/* 1709 */     this.m31 = 0.0D;
/* 1710 */     this.m32 = 0.0D;
/* 1711 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1) {
/* 1721 */     this.m00 = 1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z;
/* 1722 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1723 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1725 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1726 */     this.m11 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z;
/* 1727 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1729 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1730 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1731 */     this.m22 = 1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y;
/*      */     
/* 1733 */     this.m03 = 0.0D;
/* 1734 */     this.m13 = 0.0D;
/* 1735 */     this.m23 = 0.0D;
/*      */     
/* 1737 */     this.m30 = 0.0D;
/* 1738 */     this.m31 = 0.0D;
/* 1739 */     this.m32 = 0.0D;
/* 1740 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f a1) {
/* 1750 */     double mag = Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/*      */     
/* 1752 */     if (mag < 1.0E-10D) {
/* 1753 */       this.m00 = 1.0D;
/* 1754 */       this.m01 = 0.0D;
/* 1755 */       this.m02 = 0.0D;
/*      */       
/* 1757 */       this.m10 = 0.0D;
/* 1758 */       this.m11 = 1.0D;
/* 1759 */       this.m12 = 0.0D;
/*      */       
/* 1761 */       this.m20 = 0.0D;
/* 1762 */       this.m21 = 0.0D;
/* 1763 */       this.m22 = 1.0D;
/*      */     } else {
/* 1765 */       mag = 1.0D / mag;
/* 1766 */       double ax = a1.x * mag;
/* 1767 */       double ay = a1.y * mag;
/* 1768 */       double az = a1.z * mag;
/*      */       
/* 1770 */       double sinTheta = Math.sin(a1.angle);
/* 1771 */       double cosTheta = Math.cos(a1.angle);
/* 1772 */       double t = 1.0D - cosTheta;
/*      */       
/* 1774 */       double xz = ax * az;
/* 1775 */       double xy = ax * ay;
/* 1776 */       double yz = ay * az;
/*      */       
/* 1778 */       this.m00 = t * ax * ax + cosTheta;
/* 1779 */       this.m01 = t * xy - sinTheta * az;
/* 1780 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/* 1782 */       this.m10 = t * xy + sinTheta * az;
/* 1783 */       this.m11 = t * ay * ay + cosTheta;
/* 1784 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/* 1786 */       this.m20 = t * xz - sinTheta * ay;
/* 1787 */       this.m21 = t * yz + sinTheta * ax;
/* 1788 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/* 1790 */     this.m03 = 0.0D;
/* 1791 */     this.m13 = 0.0D;
/* 1792 */     this.m23 = 0.0D;
/*      */     
/* 1794 */     this.m30 = 0.0D;
/* 1795 */     this.m31 = 0.0D;
/* 1796 */     this.m32 = 0.0D;
/* 1797 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d q1, Vector3d t1, double s) {
/* 1809 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/* 1810 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1811 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1813 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1814 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/* 1815 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1817 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1818 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1819 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/* 1821 */     this.m03 = t1.x;
/* 1822 */     this.m13 = t1.y;
/* 1823 */     this.m23 = t1.z;
/*      */     
/* 1825 */     this.m30 = 0.0D;
/* 1826 */     this.m31 = 0.0D;
/* 1827 */     this.m32 = 0.0D;
/* 1828 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1, Vector3d t1, double s) {
/* 1840 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/* 1841 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1842 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1844 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1845 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/* 1846 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1848 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1849 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1850 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/* 1852 */     this.m03 = t1.x;
/* 1853 */     this.m13 = t1.y;
/* 1854 */     this.m23 = t1.z;
/*      */     
/* 1856 */     this.m30 = 0.0D;
/* 1857 */     this.m31 = 0.0D;
/* 1858 */     this.m32 = 0.0D;
/* 1859 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1, Vector3f t1, float s) {
/* 1871 */     this.m00 = s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/* 1872 */     this.m10 = s * 2.0D * (q1.x * q1.y + q1.w * q1.z);
/* 1873 */     this.m20 = s * 2.0D * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1875 */     this.m01 = s * 2.0D * (q1.x * q1.y - q1.w * q1.z);
/* 1876 */     this.m11 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/* 1877 */     this.m21 = s * 2.0D * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1879 */     this.m02 = s * 2.0D * (q1.x * q1.z + q1.w * q1.y);
/* 1880 */     this.m12 = s * 2.0D * (q1.y * q1.z - q1.w * q1.x);
/* 1881 */     this.m22 = s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/* 1883 */     this.m03 = t1.x;
/* 1884 */     this.m13 = t1.y;
/* 1885 */     this.m23 = t1.z;
/*      */     
/* 1887 */     this.m30 = 0.0D;
/* 1888 */     this.m31 = 0.0D;
/* 1889 */     this.m32 = 0.0D;
/* 1890 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4f m1) {
/* 1900 */     this.m00 = m1.m00;
/* 1901 */     this.m01 = m1.m01;
/* 1902 */     this.m02 = m1.m02;
/* 1903 */     this.m03 = m1.m03;
/*      */     
/* 1905 */     this.m10 = m1.m10;
/* 1906 */     this.m11 = m1.m11;
/* 1907 */     this.m12 = m1.m12;
/* 1908 */     this.m13 = m1.m13;
/*      */     
/* 1910 */     this.m20 = m1.m20;
/* 1911 */     this.m21 = m1.m21;
/* 1912 */     this.m22 = m1.m22;
/* 1913 */     this.m23 = m1.m23;
/*      */     
/* 1915 */     this.m30 = m1.m30;
/* 1916 */     this.m31 = m1.m31;
/* 1917 */     this.m32 = m1.m32;
/* 1918 */     this.m33 = m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4d m1) {
/* 1928 */     this.m00 = m1.m00;
/* 1929 */     this.m01 = m1.m01;
/* 1930 */     this.m02 = m1.m02;
/* 1931 */     this.m03 = m1.m03;
/*      */     
/* 1933 */     this.m10 = m1.m10;
/* 1934 */     this.m11 = m1.m11;
/* 1935 */     this.m12 = m1.m12;
/* 1936 */     this.m13 = m1.m13;
/*      */     
/* 1938 */     this.m20 = m1.m20;
/* 1939 */     this.m21 = m1.m21;
/* 1940 */     this.m22 = m1.m22;
/* 1941 */     this.m23 = m1.m23;
/*      */     
/* 1943 */     this.m30 = m1.m30;
/* 1944 */     this.m31 = m1.m31;
/* 1945 */     this.m32 = m1.m32;
/* 1946 */     this.m33 = m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(Matrix4d m1) {
/* 1957 */     invertGeneral(m1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1965 */     invertGeneral(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void invertGeneral(Matrix4d m1) {
/* 1977 */     double[] result = new double[16];
/* 1978 */     int[] row_perm = new int[4];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1983 */     double[] tmp = new double[16];
/*      */     
/* 1985 */     tmp[0] = m1.m00;
/* 1986 */     tmp[1] = m1.m01;
/* 1987 */     tmp[2] = m1.m02;
/* 1988 */     tmp[3] = m1.m03;
/*      */     
/* 1990 */     tmp[4] = m1.m10;
/* 1991 */     tmp[5] = m1.m11;
/* 1992 */     tmp[6] = m1.m12;
/* 1993 */     tmp[7] = m1.m13;
/*      */     
/* 1995 */     tmp[8] = m1.m20;
/* 1996 */     tmp[9] = m1.m21;
/* 1997 */     tmp[10] = m1.m22;
/* 1998 */     tmp[11] = m1.m23;
/*      */     
/* 2000 */     tmp[12] = m1.m30;
/* 2001 */     tmp[13] = m1.m31;
/* 2002 */     tmp[14] = m1.m32;
/* 2003 */     tmp[15] = m1.m33;
/*      */ 
/*      */     
/* 2006 */     if (!luDecomposition(tmp, row_perm))
/*      */     {
/* 2008 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix4d10"));
/*      */     }
/*      */ 
/*      */     
/* 2012 */     for (int i = 0; i < 16; ) { result[i] = 0.0D; i++; }
/* 2013 */      result[0] = 1.0D; result[5] = 1.0D; result[10] = 1.0D; result[15] = 1.0D;
/* 2014 */     luBacksubstitution(tmp, row_perm, result);
/*      */     
/* 2016 */     this.m00 = result[0];
/* 2017 */     this.m01 = result[1];
/* 2018 */     this.m02 = result[2];
/* 2019 */     this.m03 = result[3];
/*      */     
/* 2021 */     this.m10 = result[4];
/* 2022 */     this.m11 = result[5];
/* 2023 */     this.m12 = result[6];
/* 2024 */     this.m13 = result[7];
/*      */     
/* 2026 */     this.m20 = result[8];
/* 2027 */     this.m21 = result[9];
/* 2028 */     this.m22 = result[10];
/* 2029 */     this.m23 = result[11];
/*      */     
/* 2031 */     this.m30 = result[12];
/* 2032 */     this.m31 = result[13];
/* 2033 */     this.m32 = result[14];
/* 2034 */     this.m33 = result[15];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean luDecomposition(double[] matrix0, int[] row_perm) {
/* 2061 */     double[] row_scale = new double[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2069 */     int ptr = 0;
/* 2070 */     int rs = 0;
/*      */ 
/*      */     
/* 2073 */     int i = 4;
/* 2074 */     while (i-- != 0) {
/* 2075 */       double big = 0.0D;
/*      */ 
/*      */       
/* 2078 */       int k = 4;
/* 2079 */       while (k-- != 0) {
/* 2080 */         double temp = matrix0[ptr++];
/* 2081 */         temp = Math.abs(temp);
/* 2082 */         if (temp > big) {
/* 2083 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 2088 */       if (big == 0.0D) {
/* 2089 */         return false;
/*      */       }
/* 2091 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2099 */     int mtx = 0;
/*      */ 
/*      */     
/* 2102 */     for (int j = 0; j < 4; j++) {
/*      */       int k;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2108 */       for (k = 0; k < j; k++) {
/* 2109 */         int target = mtx + 4 * k + j;
/* 2110 */         double sum = matrix0[target];
/* 2111 */         int m = k;
/* 2112 */         int p1 = mtx + 4 * k;
/* 2113 */         int p2 = mtx + j;
/* 2114 */         while (m-- != 0) {
/* 2115 */           sum -= matrix0[p1] * matrix0[p2];
/* 2116 */           p1++;
/* 2117 */           p2 += 4;
/*      */         } 
/* 2119 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2124 */       double big = 0.0D;
/* 2125 */       int imax = -1;
/* 2126 */       for (k = j; k < 4; k++) {
/* 2127 */         int target = mtx + 4 * k + j;
/* 2128 */         double sum = matrix0[target];
/* 2129 */         int m = j;
/* 2130 */         int p1 = mtx + 4 * k;
/* 2131 */         int p2 = mtx + j;
/* 2132 */         while (m-- != 0) {
/* 2133 */           sum -= matrix0[p1] * matrix0[p2];
/* 2134 */           p1++;
/* 2135 */           p2 += 4;
/*      */         } 
/* 2137 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 2140 */         if ((temp = row_scale[k] * Math.abs(sum)) >= big) {
/* 2141 */           big = temp;
/* 2142 */           imax = k;
/*      */         } 
/*      */       } 
/*      */       
/* 2146 */       if (imax < 0) {
/* 2147 */         throw new RuntimeException(VecMathI18N.getString("Matrix4d11"));
/*      */       }
/*      */ 
/*      */       
/* 2151 */       if (j != imax) {
/*      */         
/* 2153 */         int m = 4;
/* 2154 */         int p1 = mtx + 4 * imax;
/* 2155 */         int p2 = mtx + 4 * j;
/* 2156 */         while (m-- != 0) {
/* 2157 */           double temp = matrix0[p1];
/* 2158 */           matrix0[p1++] = matrix0[p2];
/* 2159 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 2163 */         row_scale[imax] = row_scale[j];
/*      */       } 
/*      */ 
/*      */       
/* 2167 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 2170 */       if (matrix0[mtx + 4 * j + j] == 0.0D) {
/* 2171 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 2175 */       if (j != 3) {
/* 2176 */         double temp = 1.0D / matrix0[mtx + 4 * j + j];
/* 2177 */         int target = mtx + 4 * (j + 1) + j;
/* 2178 */         k = 3 - j;
/* 2179 */         while (k-- != 0) {
/* 2180 */           matrix0[target] = matrix0[target] * temp;
/* 2181 */           target += 4;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2187 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void luBacksubstitution(double[] matrix1, int[] row_perm, double[] matrix2) {
/* 2217 */     int rp = 0;
/*      */ 
/*      */     
/* 2220 */     for (int k = 0; k < 4; k++) {
/*      */       
/* 2222 */       int cv = k;
/* 2223 */       int ii = -1;
/*      */ 
/*      */       
/* 2226 */       for (int i = 0; i < 4; i++) {
/*      */ 
/*      */         
/* 2229 */         int ip = row_perm[rp + i];
/* 2230 */         double sum = matrix2[cv + 4 * ip];
/* 2231 */         matrix2[cv + 4 * ip] = matrix2[cv + 4 * i];
/* 2232 */         if (ii >= 0) {
/*      */           
/* 2234 */           int m = i * 4;
/* 2235 */           for (int j = ii; j <= i - 1; j++) {
/* 2236 */             sum -= matrix1[m + j] * matrix2[cv + 4 * j];
/*      */           }
/*      */         }
/* 2239 */         else if (sum != 0.0D) {
/* 2240 */           ii = i;
/*      */         } 
/* 2242 */         matrix2[cv + 4 * i] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2247 */       int rv = 12;
/* 2248 */       matrix2[cv + 12] = matrix2[cv + 12] / matrix1[rv + 3];
/*      */       
/* 2250 */       rv -= 4;
/* 2251 */       matrix2[cv + 8] = (matrix2[cv + 8] - 
/* 2252 */         matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 2];
/*      */       
/* 2254 */       rv -= 4;
/* 2255 */       matrix2[cv + 4] = (matrix2[cv + 4] - 
/* 2256 */         matrix1[rv + 2] * matrix2[cv + 8] - 
/* 2257 */         matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 1];
/*      */       
/* 2259 */       rv -= 4;
/* 2260 */       matrix2[cv + 0] = (matrix2[cv + 0] - 
/* 2261 */         matrix1[rv + 1] * matrix2[cv + 4] - 
/* 2262 */         matrix1[rv + 2] * matrix2[cv + 8] - 
/* 2263 */         matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 0];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double determinant() {
/* 2277 */     double det = this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - 
/* 2278 */       this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33);
/* 2279 */     det -= this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - 
/* 2280 */       this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33);
/* 2281 */     det += this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - 
/* 2282 */       this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33);
/* 2283 */     det -= this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - 
/* 2284 */       this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
/*      */     
/* 2286 */     return det;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(double scale) {
/* 2296 */     this.m00 = scale;
/* 2297 */     this.m01 = 0.0D;
/* 2298 */     this.m02 = 0.0D;
/* 2299 */     this.m03 = 0.0D;
/*      */     
/* 2301 */     this.m10 = 0.0D;
/* 2302 */     this.m11 = scale;
/* 2303 */     this.m12 = 0.0D;
/* 2304 */     this.m13 = 0.0D;
/*      */     
/* 2306 */     this.m20 = 0.0D;
/* 2307 */     this.m21 = 0.0D;
/* 2308 */     this.m22 = scale;
/* 2309 */     this.m23 = 0.0D;
/*      */     
/* 2311 */     this.m30 = 0.0D;
/* 2312 */     this.m31 = 0.0D;
/* 2313 */     this.m32 = 0.0D;
/* 2314 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Vector3d v1) {
/* 2324 */     this.m00 = 1.0D;
/* 2325 */     this.m01 = 0.0D;
/* 2326 */     this.m02 = 0.0D;
/* 2327 */     this.m03 = v1.x;
/*      */     
/* 2329 */     this.m10 = 0.0D;
/* 2330 */     this.m11 = 1.0D;
/* 2331 */     this.m12 = 0.0D;
/* 2332 */     this.m13 = v1.y;
/*      */     
/* 2334 */     this.m20 = 0.0D;
/* 2335 */     this.m21 = 0.0D;
/* 2336 */     this.m22 = 1.0D;
/* 2337 */     this.m23 = v1.z;
/*      */     
/* 2339 */     this.m30 = 0.0D;
/* 2340 */     this.m31 = 0.0D;
/* 2341 */     this.m32 = 0.0D;
/* 2342 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(double scale, Vector3d v1) {
/* 2354 */     this.m00 = scale;
/* 2355 */     this.m01 = 0.0D;
/* 2356 */     this.m02 = 0.0D;
/* 2357 */     this.m03 = v1.x;
/*      */     
/* 2359 */     this.m10 = 0.0D;
/* 2360 */     this.m11 = scale;
/* 2361 */     this.m12 = 0.0D;
/* 2362 */     this.m13 = v1.y;
/*      */     
/* 2364 */     this.m20 = 0.0D;
/* 2365 */     this.m21 = 0.0D;
/* 2366 */     this.m22 = scale;
/* 2367 */     this.m23 = v1.z;
/*      */     
/* 2369 */     this.m30 = 0.0D;
/* 2370 */     this.m31 = 0.0D;
/* 2371 */     this.m32 = 0.0D;
/* 2372 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Vector3d v1, double scale) {
/* 2384 */     this.m00 = scale;
/* 2385 */     this.m01 = 0.0D;
/* 2386 */     this.m02 = 0.0D;
/* 2387 */     this.m03 = scale * v1.x;
/*      */     
/* 2389 */     this.m10 = 0.0D;
/* 2390 */     this.m11 = scale;
/* 2391 */     this.m12 = 0.0D;
/* 2392 */     this.m13 = scale * v1.y;
/*      */     
/* 2394 */     this.m20 = 0.0D;
/* 2395 */     this.m21 = 0.0D;
/* 2396 */     this.m22 = scale;
/* 2397 */     this.m23 = scale * v1.z;
/*      */     
/* 2399 */     this.m30 = 0.0D;
/* 2400 */     this.m31 = 0.0D;
/* 2401 */     this.m32 = 0.0D;
/* 2402 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3f m1, Vector3f t1, float scale) {
/* 2415 */     this.m00 = (m1.m00 * scale);
/* 2416 */     this.m01 = (m1.m01 * scale);
/* 2417 */     this.m02 = (m1.m02 * scale);
/* 2418 */     this.m03 = t1.x;
/*      */     
/* 2420 */     this.m10 = (m1.m10 * scale);
/* 2421 */     this.m11 = (m1.m11 * scale);
/* 2422 */     this.m12 = (m1.m12 * scale);
/* 2423 */     this.m13 = t1.y;
/*      */     
/* 2425 */     this.m20 = (m1.m20 * scale);
/* 2426 */     this.m21 = (m1.m21 * scale);
/* 2427 */     this.m22 = (m1.m22 * scale);
/* 2428 */     this.m23 = t1.z;
/*      */     
/* 2430 */     this.m30 = 0.0D;
/* 2431 */     this.m31 = 0.0D;
/* 2432 */     this.m32 = 0.0D;
/* 2433 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3d m1, Vector3d t1, double scale) {
/* 2447 */     this.m00 = m1.m00 * scale;
/* 2448 */     this.m01 = m1.m01 * scale;
/* 2449 */     this.m02 = m1.m02 * scale;
/* 2450 */     this.m03 = t1.x;
/*      */     
/* 2452 */     this.m10 = m1.m10 * scale;
/* 2453 */     this.m11 = m1.m11 * scale;
/* 2454 */     this.m12 = m1.m12 * scale;
/* 2455 */     this.m13 = t1.y;
/*      */     
/* 2457 */     this.m20 = m1.m20 * scale;
/* 2458 */     this.m21 = m1.m21 * scale;
/* 2459 */     this.m22 = m1.m22 * scale;
/* 2460 */     this.m23 = t1.z;
/*      */     
/* 2462 */     this.m30 = 0.0D;
/* 2463 */     this.m31 = 0.0D;
/* 2464 */     this.m32 = 0.0D;
/* 2465 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setTranslation(Vector3d trans) {
/* 2476 */     this.m03 = trans.x;
/* 2477 */     this.m13 = trans.y;
/* 2478 */     this.m23 = trans.z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void rotX(double angle) {
/* 2490 */     double sinAngle = Math.sin(angle);
/* 2491 */     double cosAngle = Math.cos(angle);
/*      */     
/* 2493 */     this.m00 = 1.0D;
/* 2494 */     this.m01 = 0.0D;
/* 2495 */     this.m02 = 0.0D;
/* 2496 */     this.m03 = 0.0D;
/*      */     
/* 2498 */     this.m10 = 0.0D;
/* 2499 */     this.m11 = cosAngle;
/* 2500 */     this.m12 = -sinAngle;
/* 2501 */     this.m13 = 0.0D;
/*      */     
/* 2503 */     this.m20 = 0.0D;
/* 2504 */     this.m21 = sinAngle;
/* 2505 */     this.m22 = cosAngle;
/* 2506 */     this.m23 = 0.0D;
/*      */     
/* 2508 */     this.m30 = 0.0D;
/* 2509 */     this.m31 = 0.0D;
/* 2510 */     this.m32 = 0.0D;
/* 2511 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void rotY(double angle) {
/* 2523 */     double sinAngle = Math.sin(angle);
/* 2524 */     double cosAngle = Math.cos(angle);
/*      */     
/* 2526 */     this.m00 = cosAngle;
/* 2527 */     this.m01 = 0.0D;
/* 2528 */     this.m02 = sinAngle;
/* 2529 */     this.m03 = 0.0D;
/*      */     
/* 2531 */     this.m10 = 0.0D;
/* 2532 */     this.m11 = 1.0D;
/* 2533 */     this.m12 = 0.0D;
/* 2534 */     this.m13 = 0.0D;
/*      */     
/* 2536 */     this.m20 = -sinAngle;
/* 2537 */     this.m21 = 0.0D;
/* 2538 */     this.m22 = cosAngle;
/* 2539 */     this.m23 = 0.0D;
/*      */     
/* 2541 */     this.m30 = 0.0D;
/* 2542 */     this.m31 = 0.0D;
/* 2543 */     this.m32 = 0.0D;
/* 2544 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void rotZ(double angle) {
/* 2556 */     double sinAngle = Math.sin(angle);
/* 2557 */     double cosAngle = Math.cos(angle);
/*      */     
/* 2559 */     this.m00 = cosAngle;
/* 2560 */     this.m01 = -sinAngle;
/* 2561 */     this.m02 = 0.0D;
/* 2562 */     this.m03 = 0.0D;
/*      */     
/* 2564 */     this.m10 = sinAngle;
/* 2565 */     this.m11 = cosAngle;
/* 2566 */     this.m12 = 0.0D;
/* 2567 */     this.m13 = 0.0D;
/*      */     
/* 2569 */     this.m20 = 0.0D;
/* 2570 */     this.m21 = 0.0D;
/* 2571 */     this.m22 = 1.0D;
/* 2572 */     this.m23 = 0.0D;
/*      */     
/* 2574 */     this.m30 = 0.0D;
/* 2575 */     this.m31 = 0.0D;
/* 2576 */     this.m32 = 0.0D;
/* 2577 */     this.m33 = 1.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(double scalar) {
/* 2586 */     this.m00 *= scalar;
/* 2587 */     this.m01 *= scalar;
/* 2588 */     this.m02 *= scalar;
/* 2589 */     this.m03 *= scalar;
/* 2590 */     this.m10 *= scalar;
/* 2591 */     this.m11 *= scalar;
/* 2592 */     this.m12 *= scalar;
/* 2593 */     this.m13 *= scalar;
/* 2594 */     this.m20 *= scalar;
/* 2595 */     this.m21 *= scalar;
/* 2596 */     this.m22 *= scalar;
/* 2597 */     this.m23 *= scalar;
/* 2598 */     this.m30 *= scalar;
/* 2599 */     this.m31 *= scalar;
/* 2600 */     this.m32 *= scalar;
/* 2601 */     this.m33 *= scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(double scalar, Matrix4d m1) {
/* 2612 */     m1.m00 *= scalar;
/* 2613 */     m1.m01 *= scalar;
/* 2614 */     m1.m02 *= scalar;
/* 2615 */     m1.m03 *= scalar;
/* 2616 */     m1.m10 *= scalar;
/* 2617 */     m1.m11 *= scalar;
/* 2618 */     m1.m12 *= scalar;
/* 2619 */     m1.m13 *= scalar;
/* 2620 */     m1.m20 *= scalar;
/* 2621 */     m1.m21 *= scalar;
/* 2622 */     m1.m22 *= scalar;
/* 2623 */     m1.m23 *= scalar;
/* 2624 */     m1.m30 *= scalar;
/* 2625 */     m1.m31 *= scalar;
/* 2626 */     m1.m32 *= scalar;
/* 2627 */     m1.m33 *= scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix4d m1) {
/* 2642 */     double m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + 
/* 2643 */       this.m02 * m1.m20 + this.m03 * m1.m30;
/* 2644 */     double m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + 
/* 2645 */       this.m02 * m1.m21 + this.m03 * m1.m31;
/* 2646 */     double m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + 
/* 2647 */       this.m02 * m1.m22 + this.m03 * m1.m32;
/* 2648 */     double m03 = this.m00 * m1.m03 + this.m01 * m1.m13 + 
/* 2649 */       this.m02 * m1.m23 + this.m03 * m1.m33;
/*      */     
/* 2651 */     double m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + 
/* 2652 */       this.m12 * m1.m20 + this.m13 * m1.m30;
/* 2653 */     double m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + 
/* 2654 */       this.m12 * m1.m21 + this.m13 * m1.m31;
/* 2655 */     double m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + 
/* 2656 */       this.m12 * m1.m22 + this.m13 * m1.m32;
/* 2657 */     double m13 = this.m10 * m1.m03 + this.m11 * m1.m13 + 
/* 2658 */       this.m12 * m1.m23 + this.m13 * m1.m33;
/*      */     
/* 2660 */     double m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + 
/* 2661 */       this.m22 * m1.m20 + this.m23 * m1.m30;
/* 2662 */     double m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + 
/* 2663 */       this.m22 * m1.m21 + this.m23 * m1.m31;
/* 2664 */     double m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + 
/* 2665 */       this.m22 * m1.m22 + this.m23 * m1.m32;
/* 2666 */     double m23 = this.m20 * m1.m03 + this.m21 * m1.m13 + 
/* 2667 */       this.m22 * m1.m23 + this.m23 * m1.m33;
/*      */     
/* 2669 */     double m30 = this.m30 * m1.m00 + this.m31 * m1.m10 + 
/* 2670 */       this.m32 * m1.m20 + this.m33 * m1.m30;
/* 2671 */     double m31 = this.m30 * m1.m01 + this.m31 * m1.m11 + 
/* 2672 */       this.m32 * m1.m21 + this.m33 * m1.m31;
/* 2673 */     double m32 = this.m30 * m1.m02 + this.m31 * m1.m12 + 
/* 2674 */       this.m32 * m1.m22 + this.m33 * m1.m32;
/* 2675 */     double m33 = this.m30 * m1.m03 + this.m31 * m1.m13 + 
/* 2676 */       this.m32 * m1.m23 + this.m33 * m1.m33;
/*      */     
/* 2678 */     this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2679 */     this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2680 */     this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2681 */     this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix4d m1, Matrix4d m2) {
/* 2692 */     if (this != m1 && this != m2) {
/*      */       
/* 2694 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + 
/* 2695 */         m1.m02 * m2.m20 + m1.m03 * m2.m30;
/* 2696 */       this.m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + 
/* 2697 */         m1.m02 * m2.m21 + m1.m03 * m2.m31;
/* 2698 */       this.m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + 
/* 2699 */         m1.m02 * m2.m22 + m1.m03 * m2.m32;
/* 2700 */       this.m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + 
/* 2701 */         m1.m02 * m2.m23 + m1.m03 * m2.m33;
/*      */       
/* 2703 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + 
/* 2704 */         m1.m12 * m2.m20 + m1.m13 * m2.m30;
/* 2705 */       this.m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + 
/* 2706 */         m1.m12 * m2.m21 + m1.m13 * m2.m31;
/* 2707 */       this.m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + 
/* 2708 */         m1.m12 * m2.m22 + m1.m13 * m2.m32;
/* 2709 */       this.m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + 
/* 2710 */         m1.m12 * m2.m23 + m1.m13 * m2.m33;
/*      */       
/* 2712 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + 
/* 2713 */         m1.m22 * m2.m20 + m1.m23 * m2.m30;
/* 2714 */       this.m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + 
/* 2715 */         m1.m22 * m2.m21 + m1.m23 * m2.m31;
/* 2716 */       this.m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + 
/* 2717 */         m1.m22 * m2.m22 + m1.m23 * m2.m32;
/* 2718 */       this.m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + 
/* 2719 */         m1.m22 * m2.m23 + m1.m23 * m2.m33;
/*      */       
/* 2721 */       this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + 
/* 2722 */         m1.m32 * m2.m20 + m1.m33 * m2.m30;
/* 2723 */       this.m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + 
/* 2724 */         m1.m32 * m2.m21 + m1.m33 * m2.m31;
/* 2725 */       this.m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + 
/* 2726 */         m1.m32 * m2.m22 + m1.m33 * m2.m32;
/* 2727 */       this.m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + 
/* 2728 */         m1.m32 * m2.m23 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2736 */       double m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20 + m1.m03 * m2.m30;
/* 2737 */       double m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21 + m1.m03 * m2.m31;
/* 2738 */       double m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22 + m1.m03 * m2.m32;
/* 2739 */       double m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + m1.m02 * m2.m23 + m1.m03 * m2.m33;
/*      */       
/* 2741 */       double m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20 + m1.m13 * m2.m30;
/* 2742 */       double m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13 * m2.m31;
/* 2743 */       double m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22 + m1.m13 * m2.m32;
/* 2744 */       double m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33;
/*      */       
/* 2746 */       double m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20 + m1.m23 * m2.m30;
/* 2747 */       double m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21 + m1.m23 * m2.m31;
/* 2748 */       double m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32;
/* 2749 */       double m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23 * m2.m33;
/*      */       
/* 2751 */       double m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + m1.m32 * m2.m20 + m1.m33 * m2.m30;
/* 2752 */       double m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + m1.m32 * m2.m21 + m1.m33 * m2.m31;
/* 2753 */       double m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32;
/* 2754 */       double m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33 * m2.m33;
/*      */       
/* 2756 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2757 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2758 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2759 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulTransposeBoth(Matrix4d m1, Matrix4d m2) {
/* 2772 */     if (this != m1 && this != m2) {
/* 2773 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
/* 2774 */       this.m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
/* 2775 */       this.m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
/* 2776 */       this.m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
/*      */       
/* 2778 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
/* 2779 */       this.m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
/* 2780 */       this.m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
/* 2781 */       this.m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
/*      */       
/* 2783 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
/* 2784 */       this.m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
/* 2785 */       this.m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
/* 2786 */       this.m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
/*      */       
/* 2788 */       this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
/* 2789 */       this.m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
/* 2790 */       this.m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
/* 2791 */       this.m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2798 */       double m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
/* 2799 */       double m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
/* 2800 */       double m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
/* 2801 */       double m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
/*      */       
/* 2803 */       double m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
/* 2804 */       double m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
/* 2805 */       double m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
/* 2806 */       double m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
/*      */       
/* 2808 */       double m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
/* 2809 */       double m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
/* 2810 */       double m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
/* 2811 */       double m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
/*      */       
/* 2813 */       double m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
/* 2814 */       double m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
/* 2815 */       double m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
/* 2816 */       double m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
/*      */       
/* 2818 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2819 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2820 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2821 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulTransposeRight(Matrix4d m1, Matrix4d m2) {
/* 2836 */     if (this != m1 && this != m2) {
/* 2837 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
/* 2838 */       this.m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
/* 2839 */       this.m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
/* 2840 */       this.m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
/*      */       
/* 2842 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
/* 2843 */       this.m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
/* 2844 */       this.m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
/* 2845 */       this.m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
/*      */       
/* 2847 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
/* 2848 */       this.m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
/* 2849 */       this.m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
/* 2850 */       this.m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
/*      */       
/* 2852 */       this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
/* 2853 */       this.m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
/* 2854 */       this.m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
/* 2855 */       this.m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2862 */       double m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
/* 2863 */       double m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
/* 2864 */       double m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
/* 2865 */       double m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
/*      */       
/* 2867 */       double m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
/* 2868 */       double m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
/* 2869 */       double m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
/* 2870 */       double m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
/*      */       
/* 2872 */       double m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
/* 2873 */       double m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
/* 2874 */       double m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
/* 2875 */       double m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
/*      */       
/* 2877 */       double m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
/* 2878 */       double m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
/* 2879 */       double m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
/* 2880 */       double m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
/*      */       
/* 2882 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2883 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2884 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2885 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulTransposeLeft(Matrix4d m1, Matrix4d m2) {
/* 2898 */     if (this != m1 && this != m2) {
/* 2899 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
/* 2900 */       this.m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
/* 2901 */       this.m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
/* 2902 */       this.m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
/*      */       
/* 2904 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
/* 2905 */       this.m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
/* 2906 */       this.m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
/* 2907 */       this.m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
/*      */       
/* 2909 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
/* 2910 */       this.m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
/* 2911 */       this.m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
/* 2912 */       this.m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
/*      */       
/* 2914 */       this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
/* 2915 */       this.m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
/* 2916 */       this.m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
/* 2917 */       this.m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2926 */       double m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
/* 2927 */       double m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
/* 2928 */       double m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
/* 2929 */       double m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
/*      */       
/* 2931 */       double m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
/* 2932 */       double m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
/* 2933 */       double m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
/* 2934 */       double m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
/*      */       
/* 2936 */       double m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
/* 2937 */       double m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
/* 2938 */       double m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
/* 2939 */       double m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
/*      */       
/* 2941 */       double m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
/* 2942 */       double m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
/* 2943 */       double m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
/* 2944 */       double m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
/*      */       
/* 2946 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2947 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2948 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2949 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Matrix4d m1) {
/*      */     try {
/* 2964 */       return (this.m00 == m1.m00 && this.m01 == m1.m01 && this.m02 == m1.m02 && 
/* 2965 */         this.m03 == m1.m03 && this.m10 == m1.m10 && this.m11 == m1.m11 && 
/* 2966 */         this.m12 == m1.m12 && this.m13 == m1.m13 && this.m20 == m1.m20 && 
/* 2967 */         this.m21 == m1.m21 && this.m22 == m1.m22 && this.m23 == m1.m23 && 
/* 2968 */         this.m30 == m1.m30 && this.m31 == m1.m31 && this.m32 == m1.m32 && 
/* 2969 */         this.m33 == m1.m33);
/*      */     } catch (NullPointerException e2) {
/* 2971 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object t1) {
/*      */     
/* 2986 */     try { Matrix4d m2 = (Matrix4d)t1;
/* 2987 */       return (this.m00 == m2.m00 && this.m01 == m2.m01 && this.m02 == m2.m02 && 
/* 2988 */         this.m03 == m2.m03 && this.m10 == m2.m10 && this.m11 == m2.m11 && 
/* 2989 */         this.m12 == m2.m12 && this.m13 == m2.m13 && this.m20 == m2.m20 && 
/* 2990 */         this.m21 == m2.m21 && this.m22 == m2.m22 && this.m23 == m2.m23 && 
/* 2991 */         this.m30 == m2.m30 && this.m31 == m2.m31 && this.m32 == m2.m32 && 
/* 2992 */         this.m33 == m2.m33); }
/*      */     catch (ClassCastException e1)
/* 2994 */     { return false; }
/* 2995 */     catch (NullPointerException e2) { return false; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean epsilonEquals(Matrix4d m1, float epsilon) {
/* 3002 */     return epsilonEquals(m1, epsilon);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean epsilonEquals(Matrix4d m1, double epsilon) {
/* 3017 */     double diff = this.m00 - m1.m00;
/* 3018 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3020 */     diff = this.m01 - m1.m01;
/* 3021 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3023 */     diff = this.m02 - m1.m02;
/* 3024 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3026 */     diff = this.m03 - m1.m03;
/* 3027 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3029 */     diff = this.m10 - m1.m10;
/* 3030 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3032 */     diff = this.m11 - m1.m11;
/* 3033 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3035 */     diff = this.m12 - m1.m12;
/* 3036 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3038 */     diff = this.m13 - m1.m13;
/* 3039 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3041 */     diff = this.m20 - m1.m20;
/* 3042 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3044 */     diff = this.m21 - m1.m21;
/* 3045 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3047 */     diff = this.m22 - m1.m22;
/* 3048 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3050 */     diff = this.m23 - m1.m23;
/* 3051 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3053 */     diff = this.m30 - m1.m30;
/* 3054 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3056 */     diff = this.m31 - m1.m31;
/* 3057 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3059 */     diff = this.m32 - m1.m32;
/* 3060 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3062 */     diff = this.m33 - m1.m33;
/* 3063 */     if (((diff < 0.0D) ? -diff : diff) > epsilon) return false;
/*      */     
/* 3065 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 3078 */     long bits = 1L;
/* 3079 */     bits = VecMathUtil.hashDoubleBits(bits, this.m00);
/* 3080 */     bits = VecMathUtil.hashDoubleBits(bits, this.m01);
/* 3081 */     bits = VecMathUtil.hashDoubleBits(bits, this.m02);
/* 3082 */     bits = VecMathUtil.hashDoubleBits(bits, this.m03);
/* 3083 */     bits = VecMathUtil.hashDoubleBits(bits, this.m10);
/* 3084 */     bits = VecMathUtil.hashDoubleBits(bits, this.m11);
/* 3085 */     bits = VecMathUtil.hashDoubleBits(bits, this.m12);
/* 3086 */     bits = VecMathUtil.hashDoubleBits(bits, this.m13);
/* 3087 */     bits = VecMathUtil.hashDoubleBits(bits, this.m20);
/* 3088 */     bits = VecMathUtil.hashDoubleBits(bits, this.m21);
/* 3089 */     bits = VecMathUtil.hashDoubleBits(bits, this.m22);
/* 3090 */     bits = VecMathUtil.hashDoubleBits(bits, this.m23);
/* 3091 */     bits = VecMathUtil.hashDoubleBits(bits, this.m30);
/* 3092 */     bits = VecMathUtil.hashDoubleBits(bits, this.m31);
/* 3093 */     bits = VecMathUtil.hashDoubleBits(bits, this.m32);
/* 3094 */     bits = VecMathUtil.hashDoubleBits(bits, this.m33);
/* 3095 */     return VecMathUtil.hashFinish(bits);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple4d vec, Tuple4d vecOut) {
/* 3108 */     double x = this.m00 * vec.x + this.m01 * vec.y + 
/* 3109 */       this.m02 * vec.z + this.m03 * vec.w;
/* 3110 */     double y = this.m10 * vec.x + this.m11 * vec.y + 
/* 3111 */       this.m12 * vec.z + this.m13 * vec.w;
/* 3112 */     double z = this.m20 * vec.x + this.m21 * vec.y + 
/* 3113 */       this.m22 * vec.z + this.m23 * vec.w;
/* 3114 */     vecOut.w = this.m30 * vec.x + this.m31 * vec.y + 
/* 3115 */       this.m32 * vec.z + this.m33 * vec.w;
/* 3116 */     vecOut.x = x;
/* 3117 */     vecOut.y = y;
/* 3118 */     vecOut.z = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple4d vec) {
/* 3130 */     double x = this.m00 * vec.x + this.m01 * vec.y + 
/* 3131 */       this.m02 * vec.z + this.m03 * vec.w;
/* 3132 */     double y = this.m10 * vec.x + this.m11 * vec.y + 
/* 3133 */       this.m12 * vec.z + this.m13 * vec.w;
/* 3134 */     double z = this.m20 * vec.x + this.m21 * vec.y + 
/* 3135 */       this.m22 * vec.z + this.m23 * vec.w;
/* 3136 */     vec.w = this.m30 * vec.x + this.m31 * vec.y + 
/* 3137 */       this.m32 * vec.z + this.m33 * vec.w;
/* 3138 */     vec.x = x;
/* 3139 */     vec.y = y;
/* 3140 */     vec.z = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple4f vec, Tuple4f vecOut) {
/* 3152 */     float x = (float)(this.m00 * vec.x + this.m01 * vec.y + 
/* 3153 */       this.m02 * vec.z + this.m03 * vec.w);
/* 3154 */     float y = (float)(this.m10 * vec.x + this.m11 * vec.y + 
/* 3155 */       this.m12 * vec.z + this.m13 * vec.w);
/* 3156 */     float z = (float)(this.m20 * vec.x + this.m21 * vec.y + 
/* 3157 */       this.m22 * vec.z + this.m23 * vec.w);
/* 3158 */     vecOut.w = 
/* 3159 */       (float)(this.m30 * vec.x + this.m31 * vec.y + this.m32 * vec.z + this.m33 * vec.w);
/* 3160 */     vecOut.x = x;
/* 3161 */     vecOut.y = y;
/* 3162 */     vecOut.z = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple4f vec) {
/* 3174 */     float x = (float)(this.m00 * vec.x + this.m01 * vec.y + 
/* 3175 */       this.m02 * vec.z + this.m03 * vec.w);
/* 3176 */     float y = (float)(this.m10 * vec.x + this.m11 * vec.y + 
/* 3177 */       this.m12 * vec.z + this.m13 * vec.w);
/* 3178 */     float z = (float)(this.m20 * vec.x + this.m21 * vec.y + 
/* 3179 */       this.m22 * vec.z + this.m23 * vec.w);
/* 3180 */     vec.w = 
/* 3181 */       (float)(this.m30 * vec.x + this.m31 * vec.y + this.m32 * vec.z + this.m33 * vec.w);
/* 3182 */     vec.x = x;
/* 3183 */     vec.y = y;
/* 3184 */     vec.z = z;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Point3d point, Point3d pointOut) {
/* 3198 */     double x = this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03;
/* 3199 */     double y = this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13;
/* 3200 */     pointOut.z = this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23;
/* 3201 */     pointOut.x = x;
/* 3202 */     pointOut.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Point3d point) {
/* 3216 */     double x = this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03;
/* 3217 */     double y = this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13;
/* 3218 */     point.z = this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23;
/* 3219 */     point.x = x;
/* 3220 */     point.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Point3f point, Point3f pointOut) {
/* 3235 */     float x = (float)(this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03);
/* 3236 */     float y = (float)(this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13);
/* 3237 */     pointOut.z = (float)(this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23);
/* 3238 */     pointOut.x = x;
/* 3239 */     pointOut.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Point3f point) {
/* 3252 */     float x = (float)(this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03);
/* 3253 */     float y = (float)(this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13);
/* 3254 */     point.z = (float)(this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23);
/* 3255 */     point.x = x;
/* 3256 */     point.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Vector3d normal, Vector3d normalOut) {
/* 3269 */     double x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
/* 3270 */     double y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
/* 3271 */     normalOut.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
/* 3272 */     normalOut.x = x;
/* 3273 */     normalOut.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Vector3d normal) {
/* 3286 */     double x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
/* 3287 */     double y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
/* 3288 */     normal.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
/* 3289 */     normal.x = x;
/* 3290 */     normal.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Vector3f normal, Vector3f normalOut) {
/* 3303 */     float x = (float)(this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z);
/* 3304 */     float y = (float)(this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z);
/* 3305 */     normalOut.z = (float)(this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z);
/* 3306 */     normalOut.x = x;
/* 3307 */     normalOut.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Vector3f normal) {
/* 3320 */     float x = (float)(this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z);
/* 3321 */     float y = (float)(this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z);
/* 3322 */     normal.z = (float)(this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z);
/* 3323 */     normal.x = x;
/* 3324 */     normal.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotation(Matrix3d m1) {
/* 3338 */     double[] tmp_rot = new double[9];
/* 3339 */     double[] tmp_scale = new double[3];
/*      */     
/* 3341 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3343 */     this.m00 = m1.m00 * tmp_scale[0];
/* 3344 */     this.m01 = m1.m01 * tmp_scale[1];
/* 3345 */     this.m02 = m1.m02 * tmp_scale[2];
/*      */     
/* 3347 */     this.m10 = m1.m10 * tmp_scale[0];
/* 3348 */     this.m11 = m1.m11 * tmp_scale[1];
/* 3349 */     this.m12 = m1.m12 * tmp_scale[2];
/*      */     
/* 3351 */     this.m20 = m1.m20 * tmp_scale[0];
/* 3352 */     this.m21 = m1.m21 * tmp_scale[1];
/* 3353 */     this.m22 = m1.m22 * tmp_scale[2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotation(Matrix3f m1) {
/* 3371 */     double[] tmp_rot = new double[9];
/* 3372 */     double[] tmp_scale = new double[3];
/* 3373 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3375 */     this.m00 = m1.m00 * tmp_scale[0];
/* 3376 */     this.m01 = m1.m01 * tmp_scale[1];
/* 3377 */     this.m02 = m1.m02 * tmp_scale[2];
/*      */     
/* 3379 */     this.m10 = m1.m10 * tmp_scale[0];
/* 3380 */     this.m11 = m1.m11 * tmp_scale[1];
/* 3381 */     this.m12 = m1.m12 * tmp_scale[2];
/*      */     
/* 3383 */     this.m20 = m1.m20 * tmp_scale[0];
/* 3384 */     this.m21 = m1.m21 * tmp_scale[1];
/* 3385 */     this.m22 = m1.m22 * tmp_scale[2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotation(Quat4f q1) {
/* 3399 */     double[] tmp_rot = new double[9];
/* 3400 */     double[] tmp_scale = new double[3];
/* 3401 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3403 */     this.m00 = (1.0D - (2.0F * q1.y * q1.y) - (2.0F * q1.z * q1.z)) * tmp_scale[0];
/* 3404 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z) * tmp_scale[0];
/* 3405 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y) * tmp_scale[0];
/*      */     
/* 3407 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z) * tmp_scale[1];
/* 3408 */     this.m11 = (1.0D - (2.0F * q1.x * q1.x) - (2.0F * q1.z * q1.z)) * tmp_scale[1];
/* 3409 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x) * tmp_scale[1];
/*      */     
/* 3411 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y) * tmp_scale[2];
/* 3412 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x) * tmp_scale[2];
/* 3413 */     this.m22 = (1.0D - (2.0F * q1.x * q1.x) - (2.0F * q1.y * q1.y)) * tmp_scale[2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotation(Quat4d q1) {
/* 3430 */     double[] tmp_rot = new double[9];
/* 3431 */     double[] tmp_scale = new double[3];
/* 3432 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3434 */     this.m00 = (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z) * tmp_scale[0];
/* 3435 */     this.m10 = 2.0D * (q1.x * q1.y + q1.w * q1.z) * tmp_scale[0];
/* 3436 */     this.m20 = 2.0D * (q1.x * q1.z - q1.w * q1.y) * tmp_scale[0];
/*      */     
/* 3438 */     this.m01 = 2.0D * (q1.x * q1.y - q1.w * q1.z) * tmp_scale[1];
/* 3439 */     this.m11 = (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z) * tmp_scale[1];
/* 3440 */     this.m21 = 2.0D * (q1.y * q1.z + q1.w * q1.x) * tmp_scale[1];
/*      */     
/* 3442 */     this.m02 = 2.0D * (q1.x * q1.z + q1.w * q1.y) * tmp_scale[2];
/* 3443 */     this.m12 = 2.0D * (q1.y * q1.z - q1.w * q1.x) * tmp_scale[2];
/* 3444 */     this.m22 = (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y) * tmp_scale[2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRotation(AxisAngle4d a1) {
/* 3460 */     double[] tmp_rot = new double[9];
/* 3461 */     double[] tmp_scale = new double[3];
/*      */     
/* 3463 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3465 */     double mag = 1.0D / Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/* 3466 */     double ax = a1.x * mag;
/* 3467 */     double ay = a1.y * mag;
/* 3468 */     double az = a1.z * mag;
/*      */     
/* 3470 */     double sinTheta = Math.sin(a1.angle);
/* 3471 */     double cosTheta = Math.cos(a1.angle);
/* 3472 */     double t = 1.0D - cosTheta;
/*      */     
/* 3474 */     double xz = a1.x * a1.z;
/* 3475 */     double xy = a1.x * a1.y;
/* 3476 */     double yz = a1.y * a1.z;
/*      */     
/* 3478 */     this.m00 = (t * ax * ax + cosTheta) * tmp_scale[0];
/* 3479 */     this.m01 = (t * xy - sinTheta * az) * tmp_scale[1];
/* 3480 */     this.m02 = (t * xz + sinTheta * ay) * tmp_scale[2];
/*      */     
/* 3482 */     this.m10 = (t * xy + sinTheta * az) * tmp_scale[0];
/* 3483 */     this.m11 = (t * ay * ay + cosTheta) * tmp_scale[1];
/* 3484 */     this.m12 = (t * yz - sinTheta * ax) * tmp_scale[2];
/*      */     
/* 3486 */     this.m20 = (t * xz - sinTheta * ay) * tmp_scale[0];
/* 3487 */     this.m21 = (t * yz + sinTheta * ax) * tmp_scale[1];
/* 3488 */     this.m22 = (t * az * az + cosTheta) * tmp_scale[2];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 3497 */     this.m00 = 0.0D;
/* 3498 */     this.m01 = 0.0D;
/* 3499 */     this.m02 = 0.0D;
/* 3500 */     this.m03 = 0.0D;
/* 3501 */     this.m10 = 0.0D;
/* 3502 */     this.m11 = 0.0D;
/* 3503 */     this.m12 = 0.0D;
/* 3504 */     this.m13 = 0.0D;
/* 3505 */     this.m20 = 0.0D;
/* 3506 */     this.m21 = 0.0D;
/* 3507 */     this.m22 = 0.0D;
/* 3508 */     this.m23 = 0.0D;
/* 3509 */     this.m30 = 0.0D;
/* 3510 */     this.m31 = 0.0D;
/* 3511 */     this.m32 = 0.0D;
/* 3512 */     this.m33 = 0.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 3520 */     this.m00 = -this.m00;
/* 3521 */     this.m01 = -this.m01;
/* 3522 */     this.m02 = -this.m02;
/* 3523 */     this.m03 = -this.m03;
/* 3524 */     this.m10 = -this.m10;
/* 3525 */     this.m11 = -this.m11;
/* 3526 */     this.m12 = -this.m12;
/* 3527 */     this.m13 = -this.m13;
/* 3528 */     this.m20 = -this.m20;
/* 3529 */     this.m21 = -this.m21;
/* 3530 */     this.m22 = -this.m22;
/* 3531 */     this.m23 = -this.m23;
/* 3532 */     this.m30 = -this.m30;
/* 3533 */     this.m31 = -this.m31;
/* 3534 */     this.m32 = -this.m32;
/* 3535 */     this.m33 = -this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix4d m1) {
/* 3545 */     this.m00 = -m1.m00;
/* 3546 */     this.m01 = -m1.m01;
/* 3547 */     this.m02 = -m1.m02;
/* 3548 */     this.m03 = -m1.m03;
/* 3549 */     this.m10 = -m1.m10;
/* 3550 */     this.m11 = -m1.m11;
/* 3551 */     this.m12 = -m1.m12;
/* 3552 */     this.m13 = -m1.m13;
/* 3553 */     this.m20 = -m1.m20;
/* 3554 */     this.m21 = -m1.m21;
/* 3555 */     this.m22 = -m1.m22;
/* 3556 */     this.m23 = -m1.m23;
/* 3557 */     this.m30 = -m1.m30;
/* 3558 */     this.m31 = -m1.m31;
/* 3559 */     this.m32 = -m1.m32;
/* 3560 */     this.m33 = -m1.m33;
/*      */   }
/*      */   private final void getScaleRotate(double[] scales, double[] rots) {
/* 3563 */     double[] tmp = new double[9];
/* 3564 */     tmp[0] = this.m00;
/* 3565 */     tmp[1] = this.m01;
/* 3566 */     tmp[2] = this.m02;
/*      */     
/* 3568 */     tmp[3] = this.m10;
/* 3569 */     tmp[4] = this.m11;
/* 3570 */     tmp[5] = this.m12;
/*      */     
/* 3572 */     tmp[6] = this.m20;
/* 3573 */     tmp[7] = this.m21;
/* 3574 */     tmp[8] = this.m22;
/*      */     
/* 3576 */     Matrix3d.compute_svd(tmp, scales, rots);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object clone() {
/* 3591 */     Matrix4d m1 = null;
/*      */     try {
/* 3593 */       m1 = (Matrix4d)super.clone();
/* 3594 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 3596 */       throw new InternalError();
/*      */     } 
/*      */     
/* 3599 */     return m1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM00() {
/* 3610 */     return this.m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM00(double m00) {
/* 3622 */     this.m00 = m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM01() {
/* 3633 */     return this.m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM01(double m01) {
/* 3644 */     this.m01 = m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM02() {
/* 3655 */     return this.m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM02(double m02) {
/* 3666 */     this.m02 = m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM10() {
/* 3677 */     return this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM10(double m10) {
/* 3688 */     this.m10 = m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM11() {
/* 3699 */     return this.m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM11(double m11) {
/* 3710 */     this.m11 = m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM12() {
/* 3721 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM12(double m12) {
/* 3733 */     this.m12 = m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM20() {
/* 3744 */     return this.m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM20(double m20) {
/* 3755 */     this.m20 = m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM21() {
/* 3766 */     return this.m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM21(double m21) {
/* 3777 */     this.m21 = m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM22() {
/* 3788 */     return this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM22(double m22) {
/* 3799 */     this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM03() {
/* 3810 */     return this.m03;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM03(double m03) {
/* 3821 */     this.m03 = m03;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM13() {
/* 3832 */     return this.m13;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM13(double m13) {
/* 3843 */     this.m13 = m13;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM23() {
/* 3854 */     return this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM23(double m23) {
/* 3865 */     this.m23 = m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM30() {
/* 3876 */     return this.m30;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM30(double m30) {
/* 3887 */     this.m30 = m30;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM31() {
/* 3898 */     return this.m31;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM31(double m31) {
/* 3909 */     this.m31 = m31;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM32() {
/* 3921 */     return this.m32;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM32(double m32) {
/* 3932 */     this.m32 = m32;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getM33() {
/* 3943 */     return this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM33(double m33) {
/* 3954 */     this.m33 = m33;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Matrix4d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */