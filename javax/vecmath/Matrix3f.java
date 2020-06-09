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
/*      */ public class Matrix3f
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 329697160112089834L;
/*      */   public float m00;
/*      */   public float m01;
/*      */   public float m02;
/*      */   public float m10;
/*      */   public float m11;
/*      */   public float m12;
/*      */   public float m20;
/*      */   public float m21;
/*      */   public float m22;
/*      */   private static final double EPS = 1.0E-8D;
/*      */   
/*      */   public Matrix3f(float m00, float m01, float m02, float m10, float m11, float m12, float m20, float m21, float m22) {
/*  109 */     this.m00 = m00;
/*  110 */     this.m01 = m01;
/*  111 */     this.m02 = m02;
/*      */     
/*  113 */     this.m10 = m10;
/*  114 */     this.m11 = m11;
/*  115 */     this.m12 = m12;
/*      */     
/*  117 */     this.m20 = m20;
/*  118 */     this.m21 = m21;
/*  119 */     this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f(float[] v) {
/*  130 */     this.m00 = v[0];
/*  131 */     this.m01 = v[1];
/*  132 */     this.m02 = v[2];
/*      */     
/*  134 */     this.m10 = v[3];
/*  135 */     this.m11 = v[4];
/*  136 */     this.m12 = v[5];
/*      */     
/*  138 */     this.m20 = v[6];
/*  139 */     this.m21 = v[7];
/*  140 */     this.m22 = v[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f(Matrix3d m1) {
/*  151 */     this.m00 = (float)m1.m00;
/*  152 */     this.m01 = (float)m1.m01;
/*  153 */     this.m02 = (float)m1.m02;
/*      */     
/*  155 */     this.m10 = (float)m1.m10;
/*  156 */     this.m11 = (float)m1.m11;
/*  157 */     this.m12 = (float)m1.m12;
/*      */     
/*  159 */     this.m20 = (float)m1.m20;
/*  160 */     this.m21 = (float)m1.m21;
/*  161 */     this.m22 = (float)m1.m22;
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
/*      */   public Matrix3f(Matrix3f m1) {
/*  173 */     this.m00 = m1.m00;
/*  174 */     this.m01 = m1.m01;
/*  175 */     this.m02 = m1.m02;
/*      */     
/*  177 */     this.m10 = m1.m10;
/*  178 */     this.m11 = m1.m11;
/*  179 */     this.m12 = m1.m12;
/*      */     
/*  181 */     this.m20 = m1.m20;
/*  182 */     this.m21 = m1.m21;
/*  183 */     this.m22 = m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix3f() {
/*  193 */     this.m00 = 0.0F;
/*  194 */     this.m01 = 0.0F;
/*  195 */     this.m02 = 0.0F;
/*      */     
/*  197 */     this.m10 = 0.0F;
/*  198 */     this.m11 = 0.0F;
/*  199 */     this.m12 = 0.0F;
/*      */     
/*  201 */     this.m20 = 0.0F;
/*  202 */     this.m21 = 0.0F;
/*  203 */     this.m22 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  213 */     return 
/*  214 */       String.valueOf(this.m00) + ", " + this.m01 + ", " + this.m02 + "\n" + 
/*  215 */       this.m10 + ", " + this.m11 + ", " + this.m12 + "\n" + 
/*  216 */       this.m20 + ", " + this.m21 + ", " + this.m22 + "\n";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIdentity() {
/*  224 */     this.m00 = 1.0F;
/*  225 */     this.m01 = 0.0F;
/*  226 */     this.m02 = 0.0F;
/*      */     
/*  228 */     this.m10 = 0.0F;
/*  229 */     this.m11 = 1.0F;
/*  230 */     this.m12 = 0.0F;
/*      */     
/*  232 */     this.m20 = 0.0F;
/*  233 */     this.m21 = 0.0F;
/*  234 */     this.m22 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setScale(float scale) {
/*  245 */     double[] tmp_rot = new double[9];
/*  246 */     double[] tmp_scale = new double[3];
/*      */     
/*  248 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  250 */     this.m00 = (float)(tmp_rot[0] * scale);
/*  251 */     this.m01 = (float)(tmp_rot[1] * scale);
/*  252 */     this.m02 = (float)(tmp_rot[2] * scale);
/*      */     
/*  254 */     this.m10 = (float)(tmp_rot[3] * scale);
/*  255 */     this.m11 = (float)(tmp_rot[4] * scale);
/*  256 */     this.m12 = (float)(tmp_rot[5] * scale);
/*      */     
/*  258 */     this.m20 = (float)(tmp_rot[6] * scale);
/*  259 */     this.m21 = (float)(tmp_rot[7] * scale);
/*  260 */     this.m22 = (float)(tmp_rot[8] * scale);
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
/*      */   public final void setElement(int row, int column, float value) {
/*  272 */     switch (row) {
/*      */       
/*      */       case 0:
/*  275 */         switch (column) {
/*      */           
/*      */           case 0:
/*  278 */             this.m00 = value;
/*      */             return;
/*      */           case 1:
/*  281 */             this.m01 = value;
/*      */             return;
/*      */           case 2:
/*  284 */             this.m02 = value;
/*      */             return;
/*      */         } 
/*  287 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  292 */         switch (column) {
/*      */           
/*      */           case 0:
/*  295 */             this.m10 = value;
/*      */             return;
/*      */           case 1:
/*  298 */             this.m11 = value;
/*      */             return;
/*      */           case 2:
/*  301 */             this.m12 = value;
/*      */             return;
/*      */         } 
/*  304 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  309 */         switch (column) {
/*      */           
/*      */           case 0:
/*  312 */             this.m20 = value;
/*      */             return;
/*      */           case 1:
/*  315 */             this.m21 = value;
/*      */             return;
/*      */           case 2:
/*  318 */             this.m22 = value;
/*      */             return;
/*      */         } 
/*      */         
/*  322 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  327 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f0"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, Vector3f v) {
/*  337 */     if (row == 0) {
/*  338 */       v.x = this.m00;
/*  339 */       v.y = this.m01;
/*  340 */       v.z = this.m02;
/*  341 */     } else if (row == 1) {
/*  342 */       v.x = this.m10;
/*  343 */       v.y = this.m11;
/*  344 */       v.z = this.m12;
/*  345 */     } else if (row == 2) {
/*  346 */       v.x = this.m20;
/*  347 */       v.y = this.m21;
/*  348 */       v.z = this.m22;
/*      */     } else {
/*  350 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, float[] v) {
/*  361 */     if (row == 0) {
/*  362 */       v[0] = this.m00;
/*  363 */       v[1] = this.m01;
/*  364 */       v[2] = this.m02;
/*  365 */     } else if (row == 1) {
/*  366 */       v[0] = this.m10;
/*  367 */       v[1] = this.m11;
/*  368 */       v[2] = this.m12;
/*  369 */     } else if (row == 2) {
/*  370 */       v[0] = this.m20;
/*  371 */       v[1] = this.m21;
/*  372 */       v[2] = this.m22;
/*      */     } else {
/*  374 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f1"));
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
/*      */   public final void getColumn(int column, Vector3f v) {
/*  386 */     if (column == 0) {
/*  387 */       v.x = this.m00;
/*  388 */       v.y = this.m10;
/*  389 */       v.z = this.m20;
/*  390 */     } else if (column == 1) {
/*  391 */       v.x = this.m01;
/*  392 */       v.y = this.m11;
/*  393 */       v.z = this.m21;
/*  394 */     } else if (column == 2) {
/*  395 */       v.x = this.m02;
/*  396 */       v.y = this.m12;
/*  397 */       v.z = this.m22;
/*      */     } else {
/*  399 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
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
/*      */   public final void getColumn(int column, float[] v) {
/*  411 */     if (column == 0) {
/*  412 */       v[0] = this.m00;
/*  413 */       v[1] = this.m10;
/*  414 */       v[2] = this.m20;
/*  415 */     } else if (column == 1) {
/*  416 */       v[0] = this.m01;
/*  417 */       v[1] = this.m11;
/*  418 */       v[2] = this.m21;
/*  419 */     } else if (column == 2) {
/*  420 */       v[0] = this.m02;
/*  421 */       v[1] = this.m12;
/*  422 */       v[2] = this.m22;
/*      */     } else {
/*  424 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f3"));
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
/*      */   public final float getElement(int row, int column) {
/*  437 */     switch (row) {
/*      */       
/*      */       case 0:
/*  440 */         switch (column) {
/*      */           
/*      */           case 0:
/*  443 */             return this.m00;
/*      */           case 1:
/*  445 */             return this.m01;
/*      */           case 2:
/*  447 */             return this.m02;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  453 */         switch (column) {
/*      */           
/*      */           case 0:
/*  456 */             return this.m10;
/*      */           case 1:
/*  458 */             return this.m11;
/*      */           case 2:
/*  460 */             return this.m12;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  467 */         switch (column) {
/*      */           
/*      */           case 0:
/*  470 */             return this.m20;
/*      */           case 1:
/*  472 */             return this.m21;
/*      */           case 2:
/*  474 */             return this.m22;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  483 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f5"));
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
/*      */   public final void setRow(int row, float x, float y, float z) {
/*  495 */     switch (row) {
/*      */       case 0:
/*  497 */         this.m00 = x;
/*  498 */         this.m01 = y;
/*  499 */         this.m02 = z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  503 */         this.m10 = x;
/*  504 */         this.m11 = y;
/*  505 */         this.m12 = z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  509 */         this.m20 = x;
/*  510 */         this.m21 = y;
/*  511 */         this.m22 = z;
/*      */         return;
/*      */     } 
/*      */     
/*  515 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, Vector3f v) {
/*  526 */     switch (row) {
/*      */       case 0:
/*  528 */         this.m00 = v.x;
/*  529 */         this.m01 = v.y;
/*  530 */         this.m02 = v.z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  534 */         this.m10 = v.x;
/*  535 */         this.m11 = v.y;
/*  536 */         this.m12 = v.z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  540 */         this.m20 = v.x;
/*  541 */         this.m21 = v.y;
/*  542 */         this.m22 = v.z;
/*      */         return;
/*      */     } 
/*      */     
/*  546 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, float[] v) {
/*  557 */     switch (row) {
/*      */       case 0:
/*  559 */         this.m00 = v[0];
/*  560 */         this.m01 = v[1];
/*  561 */         this.m02 = v[2];
/*      */         return;
/*      */       
/*      */       case 1:
/*  565 */         this.m10 = v[0];
/*  566 */         this.m11 = v[1];
/*  567 */         this.m12 = v[2];
/*      */         return;
/*      */       
/*      */       case 2:
/*  571 */         this.m20 = v[0];
/*  572 */         this.m21 = v[1];
/*  573 */         this.m22 = v[2];
/*      */         return;
/*      */     } 
/*      */     
/*  577 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f6"));
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
/*      */   public final void setColumn(int column, float x, float y, float z) {
/*  590 */     switch (column) {
/*      */       case 0:
/*  592 */         this.m00 = x;
/*  593 */         this.m10 = y;
/*  594 */         this.m20 = z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  598 */         this.m01 = x;
/*  599 */         this.m11 = y;
/*  600 */         this.m21 = z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  604 */         this.m02 = x;
/*  605 */         this.m12 = y;
/*  606 */         this.m22 = z;
/*      */         return;
/*      */     } 
/*      */     
/*  610 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, Vector3f v) {
/*  621 */     switch (column) {
/*      */       case 0:
/*  623 */         this.m00 = v.x;
/*  624 */         this.m10 = v.y;
/*  625 */         this.m20 = v.z;
/*      */         return;
/*      */       
/*      */       case 1:
/*  629 */         this.m01 = v.x;
/*  630 */         this.m11 = v.y;
/*  631 */         this.m21 = v.z;
/*      */         return;
/*      */       
/*      */       case 2:
/*  635 */         this.m02 = v.x;
/*  636 */         this.m12 = v.y;
/*  637 */         this.m22 = v.z;
/*      */         return;
/*      */     } 
/*      */     
/*  641 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, float[] v) {
/*  652 */     switch (column) {
/*      */       case 0:
/*  654 */         this.m00 = v[0];
/*  655 */         this.m10 = v[1];
/*  656 */         this.m20 = v[2];
/*      */         return;
/*      */       
/*      */       case 1:
/*  660 */         this.m01 = v[0];
/*  661 */         this.m11 = v[1];
/*  662 */         this.m21 = v[2];
/*      */         return;
/*      */       
/*      */       case 2:
/*  666 */         this.m02 = v[0];
/*  667 */         this.m12 = v[1];
/*  668 */         this.m22 = v[2];
/*      */         return;
/*      */     } 
/*      */     
/*  672 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix3f9"));
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
/*      */   public final float getScale() {
/*  686 */     double[] tmp_rot = new double[9];
/*  687 */     double[] tmp_scale = new double[3];
/*  688 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  690 */     return (float)Matrix3d.max3(tmp_scale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float scalar) {
/*  700 */     this.m00 += scalar;
/*  701 */     this.m01 += scalar;
/*  702 */     this.m02 += scalar;
/*  703 */     this.m10 += scalar;
/*  704 */     this.m11 += scalar;
/*  705 */     this.m12 += scalar;
/*  706 */     this.m20 += scalar;
/*  707 */     this.m21 += scalar;
/*  708 */     this.m22 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float scalar, Matrix3f m1) {
/*  719 */     m1.m00 += scalar;
/*  720 */     m1.m01 += scalar;
/*  721 */     m1.m02 += scalar;
/*  722 */     m1.m10 += scalar;
/*  723 */     m1.m11 += scalar;
/*  724 */     m1.m12 += scalar;
/*  725 */     m1.m20 += scalar;
/*  726 */     m1.m21 += scalar;
/*  727 */     m1.m22 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3f m1, Matrix3f m2) {
/*  737 */     m1.m00 += m2.m00;
/*  738 */     m1.m01 += m2.m01;
/*  739 */     m1.m02 += m2.m02;
/*      */     
/*  741 */     m1.m10 += m2.m10;
/*  742 */     m1.m11 += m2.m11;
/*  743 */     m1.m12 += m2.m12;
/*      */     
/*  745 */     m1.m20 += m2.m20;
/*  746 */     m1.m21 += m2.m21;
/*  747 */     m1.m22 += m2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix3f m1) {
/*  757 */     this.m00 += m1.m00;
/*  758 */     this.m01 += m1.m01;
/*  759 */     this.m02 += m1.m02;
/*      */     
/*  761 */     this.m10 += m1.m10;
/*  762 */     this.m11 += m1.m11;
/*  763 */     this.m12 += m1.m12;
/*      */     
/*  765 */     this.m20 += m1.m20;
/*  766 */     this.m21 += m1.m21;
/*  767 */     this.m22 += m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix3f m1, Matrix3f m2) {
/*  778 */     m1.m00 -= m2.m00;
/*  779 */     m1.m01 -= m2.m01;
/*  780 */     m1.m02 -= m2.m02;
/*      */     
/*  782 */     m1.m10 -= m2.m10;
/*  783 */     m1.m11 -= m2.m11;
/*  784 */     m1.m12 -= m2.m12;
/*      */     
/*  786 */     m1.m20 -= m2.m20;
/*  787 */     m1.m21 -= m2.m21;
/*  788 */     m1.m22 -= m2.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix3f m1) {
/*  798 */     this.m00 -= m1.m00;
/*  799 */     this.m01 -= m1.m01;
/*  800 */     this.m02 -= m1.m02;
/*      */     
/*  802 */     this.m10 -= m1.m10;
/*  803 */     this.m11 -= m1.m11;
/*  804 */     this.m12 -= m1.m12;
/*      */     
/*  806 */     this.m20 -= m1.m20;
/*  807 */     this.m21 -= m1.m21;
/*  808 */     this.m22 -= m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/*  818 */     float temp = this.m10;
/*  819 */     this.m10 = this.m01;
/*  820 */     this.m01 = temp;
/*      */     
/*  822 */     temp = this.m20;
/*  823 */     this.m20 = this.m02;
/*  824 */     this.m02 = temp;
/*      */     
/*  826 */     temp = this.m21;
/*  827 */     this.m21 = this.m12;
/*  828 */     this.m12 = temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix3f m1) {
/*  837 */     if (this != m1) {
/*  838 */       this.m00 = m1.m00;
/*  839 */       this.m01 = m1.m10;
/*  840 */       this.m02 = m1.m20;
/*      */       
/*  842 */       this.m10 = m1.m01;
/*  843 */       this.m11 = m1.m11;
/*  844 */       this.m12 = m1.m21;
/*      */       
/*  846 */       this.m20 = m1.m02;
/*  847 */       this.m21 = m1.m12;
/*  848 */       this.m22 = m1.m22;
/*      */     } else {
/*  850 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1) {
/*  860 */     this.m00 = 1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z;
/*  861 */     this.m10 = 2.0F * (q1.x * q1.y + q1.w * q1.z);
/*  862 */     this.m20 = 2.0F * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/*  864 */     this.m01 = 2.0F * (q1.x * q1.y - q1.w * q1.z);
/*  865 */     this.m11 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z;
/*  866 */     this.m21 = 2.0F * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/*  868 */     this.m02 = 2.0F * (q1.x * q1.z + q1.w * q1.y);
/*  869 */     this.m12 = 2.0F * (q1.y * q1.z - q1.w * q1.x);
/*  870 */     this.m22 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f a1) {
/*  880 */     float mag = (float)Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/*  881 */     if (mag < 1.0E-8D) {
/*  882 */       this.m00 = 1.0F;
/*  883 */       this.m01 = 0.0F;
/*  884 */       this.m02 = 0.0F;
/*      */       
/*  886 */       this.m10 = 0.0F;
/*  887 */       this.m11 = 1.0F;
/*  888 */       this.m12 = 0.0F;
/*      */       
/*  890 */       this.m20 = 0.0F;
/*  891 */       this.m21 = 0.0F;
/*  892 */       this.m22 = 1.0F;
/*      */     } else {
/*  894 */       mag = 1.0F / mag;
/*  895 */       float ax = a1.x * mag;
/*  896 */       float ay = a1.y * mag;
/*  897 */       float az = a1.z * mag;
/*      */       
/*  899 */       float sinTheta = (float)Math.sin(a1.angle);
/*  900 */       float cosTheta = (float)Math.cos(a1.angle);
/*  901 */       float t = 1.0F - cosTheta;
/*      */       
/*  903 */       float xz = ax * az;
/*  904 */       float xy = ax * ay;
/*  905 */       float yz = ay * az;
/*      */       
/*  907 */       this.m00 = t * ax * ax + cosTheta;
/*  908 */       this.m01 = t * xy - sinTheta * az;
/*  909 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/*  911 */       this.m10 = t * xy + sinTheta * az;
/*  912 */       this.m11 = t * ay * ay + cosTheta;
/*  913 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/*  915 */       this.m20 = t * xz - sinTheta * ay;
/*  916 */       this.m21 = t * yz + sinTheta * ax;
/*  917 */       this.m22 = t * az * az + cosTheta;
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
/*      */   public final void set(AxisAngle4d a1) {
/*  929 */     double mag = Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/*  930 */     if (mag < 1.0E-8D) {
/*  931 */       this.m00 = 1.0F;
/*  932 */       this.m01 = 0.0F;
/*  933 */       this.m02 = 0.0F;
/*      */       
/*  935 */       this.m10 = 0.0F;
/*  936 */       this.m11 = 1.0F;
/*  937 */       this.m12 = 0.0F;
/*      */       
/*  939 */       this.m20 = 0.0F;
/*  940 */       this.m21 = 0.0F;
/*  941 */       this.m22 = 1.0F;
/*      */     } else {
/*  943 */       mag = 1.0D / mag;
/*  944 */       double ax = a1.x * mag;
/*  945 */       double ay = a1.y * mag;
/*  946 */       double az = a1.z * mag;
/*      */       
/*  948 */       double sinTheta = Math.sin(a1.angle);
/*  949 */       double cosTheta = Math.cos(a1.angle);
/*  950 */       double t = 1.0D - cosTheta;
/*      */       
/*  952 */       double xz = ax * az;
/*  953 */       double xy = ax * ay;
/*  954 */       double yz = ay * az;
/*      */       
/*  956 */       this.m00 = (float)(t * ax * ax + cosTheta);
/*  957 */       this.m01 = (float)(t * xy - sinTheta * az);
/*  958 */       this.m02 = (float)(t * xz + sinTheta * ay);
/*      */       
/*  960 */       this.m10 = (float)(t * xy + sinTheta * az);
/*  961 */       this.m11 = (float)(t * ay * ay + cosTheta);
/*  962 */       this.m12 = (float)(t * yz - sinTheta * ax);
/*      */       
/*  964 */       this.m20 = (float)(t * xz - sinTheta * ay);
/*  965 */       this.m21 = (float)(t * yz + sinTheta * ax);
/*  966 */       this.m22 = (float)(t * az * az + cosTheta);
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
/*      */   public final void set(Quat4d q1) {
/*  978 */     this.m00 = (float)(1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/*  979 */     this.m10 = (float)(2.0D * (q1.x * q1.y + q1.w * q1.z));
/*  980 */     this.m20 = (float)(2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/*  982 */     this.m01 = (float)(2.0D * (q1.x * q1.y - q1.w * q1.z));
/*  983 */     this.m11 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/*  984 */     this.m21 = (float)(2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/*  986 */     this.m02 = (float)(2.0D * (q1.x * q1.z + q1.w * q1.y));
/*  987 */     this.m12 = (float)(2.0D * (q1.y * q1.z - q1.w * q1.x));
/*  988 */     this.m22 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float[] m) {
/*  999 */     this.m00 = m[0];
/* 1000 */     this.m01 = m[1];
/* 1001 */     this.m02 = m[2];
/*      */     
/* 1003 */     this.m10 = m[3];
/* 1004 */     this.m11 = m[4];
/* 1005 */     this.m12 = m[5];
/*      */     
/* 1007 */     this.m20 = m[6];
/* 1008 */     this.m21 = m[7];
/* 1009 */     this.m22 = m[8];
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
/* 1021 */     this.m00 = m1.m00;
/* 1022 */     this.m01 = m1.m01;
/* 1023 */     this.m02 = m1.m02;
/*      */     
/* 1025 */     this.m10 = m1.m10;
/* 1026 */     this.m11 = m1.m11;
/* 1027 */     this.m12 = m1.m12;
/*      */     
/* 1029 */     this.m20 = m1.m20;
/* 1030 */     this.m21 = m1.m21;
/* 1031 */     this.m22 = m1.m22;
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
/* 1043 */     this.m00 = (float)m1.m00;
/* 1044 */     this.m01 = (float)m1.m01;
/* 1045 */     this.m02 = (float)m1.m02;
/*      */     
/* 1047 */     this.m10 = (float)m1.m10;
/* 1048 */     this.m11 = (float)m1.m11;
/* 1049 */     this.m12 = (float)m1.m12;
/*      */     
/* 1051 */     this.m20 = (float)m1.m20;
/* 1052 */     this.m21 = (float)m1.m21;
/* 1053 */     this.m22 = (float)m1.m22;
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
/*      */   public final void invert(Matrix3f m1) {
/* 1065 */     invertGeneral(m1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1073 */     invertGeneral(this);
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
/*      */   private final void invertGeneral(Matrix3f m1) {
/* 1085 */     double[] temp = new double[9];
/* 1086 */     double[] result = new double[9];
/* 1087 */     int[] row_perm = new int[3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1094 */     temp[0] = m1.m00;
/* 1095 */     temp[1] = m1.m01;
/* 1096 */     temp[2] = m1.m02;
/*      */     
/* 1098 */     temp[3] = m1.m10;
/* 1099 */     temp[4] = m1.m11;
/* 1100 */     temp[5] = m1.m12;
/*      */     
/* 1102 */     temp[6] = m1.m20;
/* 1103 */     temp[7] = m1.m21;
/* 1104 */     temp[8] = m1.m22;
/*      */ 
/*      */ 
/*      */     
/* 1108 */     if (!luDecomposition(temp, row_perm))
/*      */     {
/* 1110 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix3f12"));
/*      */     }
/*      */ 
/*      */     
/* 1114 */     for (int i = 0; i < 9; ) { result[i] = 0.0D; i++; }
/* 1115 */      result[0] = 1.0D; result[4] = 1.0D; result[8] = 1.0D;
/* 1116 */     luBacksubstitution(temp, row_perm, result);
/*      */     
/* 1118 */     this.m00 = (float)result[0];
/* 1119 */     this.m01 = (float)result[1];
/* 1120 */     this.m02 = (float)result[2];
/*      */     
/* 1122 */     this.m10 = (float)result[3];
/* 1123 */     this.m11 = (float)result[4];
/* 1124 */     this.m12 = (float)result[5];
/*      */     
/* 1126 */     this.m20 = (float)result[6];
/* 1127 */     this.m21 = (float)result[7];
/* 1128 */     this.m22 = (float)result[8];
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
/* 1155 */     double[] row_scale = new double[3];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1163 */     int ptr = 0;
/* 1164 */     int rs = 0;
/*      */ 
/*      */     
/* 1167 */     int i = 3;
/* 1168 */     while (i-- != 0) {
/* 1169 */       double big = 0.0D;
/*      */ 
/*      */       
/* 1172 */       int k = 3;
/* 1173 */       while (k-- != 0) {
/* 1174 */         double temp = matrix0[ptr++];
/* 1175 */         temp = Math.abs(temp);
/* 1176 */         if (temp > big) {
/* 1177 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1182 */       if (big == 0.0D) {
/* 1183 */         return false;
/*      */       }
/* 1185 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1193 */     int mtx = 0;
/*      */ 
/*      */     
/* 1196 */     for (int j = 0; j < 3; j++) {
/*      */       int k;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1202 */       for (k = 0; k < j; k++) {
/* 1203 */         int target = mtx + 3 * k + j;
/* 1204 */         double sum = matrix0[target];
/* 1205 */         int m = k;
/* 1206 */         int p1 = mtx + 3 * k;
/* 1207 */         int p2 = mtx + j;
/* 1208 */         while (m-- != 0) {
/* 1209 */           sum -= matrix0[p1] * matrix0[p2];
/* 1210 */           p1++;
/* 1211 */           p2 += 3;
/*      */         } 
/* 1213 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1218 */       double big = 0.0D;
/* 1219 */       int imax = -1;
/* 1220 */       for (k = j; k < 3; k++) {
/* 1221 */         int target = mtx + 3 * k + j;
/* 1222 */         double sum = matrix0[target];
/* 1223 */         int m = j;
/* 1224 */         int p1 = mtx + 3 * k;
/* 1225 */         int p2 = mtx + j;
/* 1226 */         while (m-- != 0) {
/* 1227 */           sum -= matrix0[p1] * matrix0[p2];
/* 1228 */           p1++;
/* 1229 */           p2 += 3;
/*      */         } 
/* 1231 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 1234 */         if ((temp = row_scale[k] * Math.abs(sum)) >= big) {
/* 1235 */           big = temp;
/* 1236 */           imax = k;
/*      */         } 
/*      */       } 
/*      */       
/* 1240 */       if (imax < 0) {
/* 1241 */         throw new RuntimeException(VecMathI18N.getString("Matrix3f13"));
/*      */       }
/*      */ 
/*      */       
/* 1245 */       if (j != imax) {
/*      */         
/* 1247 */         int m = 3;
/* 1248 */         int p1 = mtx + 3 * imax;
/* 1249 */         int p2 = mtx + 3 * j;
/* 1250 */         while (m-- != 0) {
/* 1251 */           double temp = matrix0[p1];
/* 1252 */           matrix0[p1++] = matrix0[p2];
/* 1253 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 1257 */         row_scale[imax] = row_scale[j];
/*      */       } 
/*      */ 
/*      */       
/* 1261 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 1264 */       if (matrix0[mtx + 3 * j + j] == 0.0D) {
/* 1265 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1269 */       if (j != 2) {
/* 1270 */         double temp = 1.0D / matrix0[mtx + 3 * j + j];
/* 1271 */         int target = mtx + 3 * (j + 1) + j;
/* 1272 */         k = 2 - j;
/* 1273 */         while (k-- != 0) {
/* 1274 */           matrix0[target] = matrix0[target] * temp;
/* 1275 */           target += 3;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1281 */     return true;
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
/* 1311 */     int rp = 0;
/*      */ 
/*      */     
/* 1314 */     for (int k = 0; k < 3; k++) {
/*      */       
/* 1316 */       int cv = k;
/* 1317 */       int ii = -1;
/*      */ 
/*      */       
/* 1320 */       for (int i = 0; i < 3; i++) {
/*      */ 
/*      */         
/* 1323 */         int ip = row_perm[rp + i];
/* 1324 */         double sum = matrix2[cv + 3 * ip];
/* 1325 */         matrix2[cv + 3 * ip] = matrix2[cv + 3 * i];
/* 1326 */         if (ii >= 0) {
/*      */           
/* 1328 */           int m = i * 3;
/* 1329 */           for (int j = ii; j <= i - 1; j++) {
/* 1330 */             sum -= matrix1[m + j] * matrix2[cv + 3 * j];
/*      */           }
/*      */         }
/* 1333 */         else if (sum != 0.0D) {
/* 1334 */           ii = i;
/*      */         } 
/* 1336 */         matrix2[cv + 3 * i] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1341 */       int rv = 6;
/* 1342 */       matrix2[cv + 6] = matrix2[cv + 6] / matrix1[rv + 2];
/*      */       
/* 1344 */       rv -= 3;
/* 1345 */       matrix2[cv + 3] = (matrix2[cv + 3] - 
/* 1346 */         matrix1[rv + 2] * matrix2[cv + 6]) / matrix1[rv + 1];
/*      */       
/* 1348 */       rv -= 3;
/* 1349 */       matrix2[cv + 0] = (matrix2[cv + 0] - 
/* 1350 */         matrix1[rv + 1] * matrix2[cv + 3] - 
/* 1351 */         matrix1[rv + 2] * matrix2[cv + 6]) / matrix1[rv + 0];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float determinant() {
/* 1362 */     float total = this.m00 * (this.m11 * this.m22 - this.m12 * this.m21) + 
/* 1363 */       this.m01 * (this.m12 * this.m20 - this.m10 * this.m22) + 
/* 1364 */       this.m02 * (this.m10 * this.m21 - this.m11 * this.m20);
/* 1365 */     return total;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float scale) {
/* 1375 */     this.m00 = scale;
/* 1376 */     this.m01 = 0.0F;
/* 1377 */     this.m02 = 0.0F;
/*      */     
/* 1379 */     this.m10 = 0.0F;
/* 1380 */     this.m11 = scale;
/* 1381 */     this.m12 = 0.0F;
/*      */     
/* 1383 */     this.m20 = 0.0F;
/* 1384 */     this.m21 = 0.0F;
/* 1385 */     this.m22 = scale;
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
/*      */   public final void rotX(float angle) {
/* 1397 */     float sinAngle = (float)Math.sin(angle);
/* 1398 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 1400 */     this.m00 = 1.0F;
/* 1401 */     this.m01 = 0.0F;
/* 1402 */     this.m02 = 0.0F;
/*      */     
/* 1404 */     this.m10 = 0.0F;
/* 1405 */     this.m11 = cosAngle;
/* 1406 */     this.m12 = -sinAngle;
/*      */     
/* 1408 */     this.m20 = 0.0F;
/* 1409 */     this.m21 = sinAngle;
/* 1410 */     this.m22 = cosAngle;
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
/*      */   public final void rotY(float angle) {
/* 1422 */     float sinAngle = (float)Math.sin(angle);
/* 1423 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 1425 */     this.m00 = cosAngle;
/* 1426 */     this.m01 = 0.0F;
/* 1427 */     this.m02 = sinAngle;
/*      */     
/* 1429 */     this.m10 = 0.0F;
/* 1430 */     this.m11 = 1.0F;
/* 1431 */     this.m12 = 0.0F;
/*      */     
/* 1433 */     this.m20 = -sinAngle;
/* 1434 */     this.m21 = 0.0F;
/* 1435 */     this.m22 = cosAngle;
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
/*      */   public final void rotZ(float angle) {
/* 1447 */     float sinAngle = (float)Math.sin(angle);
/* 1448 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 1450 */     this.m00 = cosAngle;
/* 1451 */     this.m01 = -sinAngle;
/* 1452 */     this.m02 = 0.0F;
/*      */     
/* 1454 */     this.m10 = sinAngle;
/* 1455 */     this.m11 = cosAngle;
/* 1456 */     this.m12 = 0.0F;
/*      */     
/* 1458 */     this.m20 = 0.0F;
/* 1459 */     this.m21 = 0.0F;
/* 1460 */     this.m22 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float scalar) {
/* 1469 */     this.m00 *= scalar;
/* 1470 */     this.m01 *= scalar;
/* 1471 */     this.m02 *= scalar;
/*      */     
/* 1473 */     this.m10 *= scalar;
/* 1474 */     this.m11 *= scalar;
/* 1475 */     this.m12 *= scalar;
/*      */     
/* 1477 */     this.m20 *= scalar;
/* 1478 */     this.m21 *= scalar;
/* 1479 */     this.m22 *= scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float scalar, Matrix3f m1) {
/* 1490 */     this.m00 = scalar * m1.m00;
/* 1491 */     this.m01 = scalar * m1.m01;
/* 1492 */     this.m02 = scalar * m1.m02;
/*      */     
/* 1494 */     this.m10 = scalar * m1.m10;
/* 1495 */     this.m11 = scalar * m1.m11;
/* 1496 */     this.m12 = scalar * m1.m12;
/*      */     
/* 1498 */     this.m20 = scalar * m1.m20;
/* 1499 */     this.m21 = scalar * m1.m21;
/* 1500 */     this.m22 = scalar * m1.m22;
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
/*      */   public final void mul(Matrix3f m1) {
/* 1515 */     float m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20;
/* 1516 */     float m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21;
/* 1517 */     float m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22;
/*      */     
/* 1519 */     float m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20;
/* 1520 */     float m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21;
/* 1521 */     float m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22;
/*      */     
/* 1523 */     float m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20;
/* 1524 */     float m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21;
/* 1525 */     float m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22;
/*      */     
/* 1527 */     this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1528 */     this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1529 */     this.m20 = m20; this.m21 = m21; this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix3f m1, Matrix3f m2) {
/* 1540 */     if (this != m1 && this != m2) {
/* 1541 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20;
/* 1542 */       this.m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21;
/* 1543 */       this.m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22;
/*      */       
/* 1545 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20;
/* 1546 */       this.m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21;
/* 1547 */       this.m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22;
/*      */       
/* 1549 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20;
/* 1550 */       this.m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21;
/* 1551 */       this.m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1557 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20;
/* 1558 */       float m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21;
/* 1559 */       float m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22;
/*      */       
/* 1561 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20;
/* 1562 */       float m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21;
/* 1563 */       float m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22;
/*      */       
/* 1565 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20;
/* 1566 */       float m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21;
/* 1567 */       float m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22;
/*      */       
/* 1569 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1570 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1571 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
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
/*      */   public final void mulNormalize(Matrix3f m1) {
/* 1583 */     double[] tmp = new double[9];
/* 1584 */     double[] tmp_rot = new double[9];
/* 1585 */     double[] tmp_scale = new double[3];
/*      */     
/* 1587 */     tmp[0] = (this.m00 * m1.m00 + this.m01 * m1.m10 + this.m02 * m1.m20);
/* 1588 */     tmp[1] = (this.m00 * m1.m01 + this.m01 * m1.m11 + this.m02 * m1.m21);
/* 1589 */     tmp[2] = (this.m00 * m1.m02 + this.m01 * m1.m12 + this.m02 * m1.m22);
/*      */     
/* 1591 */     tmp[3] = (this.m10 * m1.m00 + this.m11 * m1.m10 + this.m12 * m1.m20);
/* 1592 */     tmp[4] = (this.m10 * m1.m01 + this.m11 * m1.m11 + this.m12 * m1.m21);
/* 1593 */     tmp[5] = (this.m10 * m1.m02 + this.m11 * m1.m12 + this.m12 * m1.m22);
/*      */     
/* 1595 */     tmp[6] = (this.m20 * m1.m00 + this.m21 * m1.m10 + this.m22 * m1.m20);
/* 1596 */     tmp[7] = (this.m20 * m1.m01 + this.m21 * m1.m11 + this.m22 * m1.m21);
/* 1597 */     tmp[8] = (this.m20 * m1.m02 + this.m21 * m1.m12 + this.m22 * m1.m22);
/*      */     
/* 1599 */     Matrix3d.compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1601 */     this.m00 = (float)tmp_rot[0];
/* 1602 */     this.m01 = (float)tmp_rot[1];
/* 1603 */     this.m02 = (float)tmp_rot[2];
/*      */     
/* 1605 */     this.m10 = (float)tmp_rot[3];
/* 1606 */     this.m11 = (float)tmp_rot[4];
/* 1607 */     this.m12 = (float)tmp_rot[5];
/*      */     
/* 1609 */     this.m20 = (float)tmp_rot[6];
/* 1610 */     this.m21 = (float)tmp_rot[7];
/* 1611 */     this.m22 = (float)tmp_rot[8];
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
/*      */   public final void mulNormalize(Matrix3f m1, Matrix3f m2) {
/* 1624 */     double[] tmp = new double[9];
/* 1625 */     double[] tmp_rot = new double[9];
/* 1626 */     double[] tmp_scale = new double[3];
/*      */ 
/*      */     
/* 1629 */     tmp[0] = (m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20);
/* 1630 */     tmp[1] = (m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21);
/* 1631 */     tmp[2] = (m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22);
/*      */     
/* 1633 */     tmp[3] = (m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20);
/* 1634 */     tmp[4] = (m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21);
/* 1635 */     tmp[5] = (m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22);
/*      */     
/* 1637 */     tmp[6] = (m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20);
/* 1638 */     tmp[7] = (m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21);
/* 1639 */     tmp[8] = (m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22);
/*      */     
/* 1641 */     Matrix3d.compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1643 */     this.m00 = (float)tmp_rot[0];
/* 1644 */     this.m01 = (float)tmp_rot[1];
/* 1645 */     this.m02 = (float)tmp_rot[2];
/*      */     
/* 1647 */     this.m10 = (float)tmp_rot[3];
/* 1648 */     this.m11 = (float)tmp_rot[4];
/* 1649 */     this.m12 = (float)tmp_rot[5];
/*      */     
/* 1651 */     this.m20 = (float)tmp_rot[6];
/* 1652 */     this.m21 = (float)tmp_rot[7];
/* 1653 */     this.m22 = (float)tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mulTransposeBoth(Matrix3f m1, Matrix3f m2) {
/* 1664 */     if (this != m1 && this != m2) {
/* 1665 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02;
/* 1666 */       this.m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12;
/* 1667 */       this.m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22;
/*      */       
/* 1669 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02;
/* 1670 */       this.m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12;
/* 1671 */       this.m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22;
/*      */       
/* 1673 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02;
/* 1674 */       this.m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12;
/* 1675 */       this.m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1681 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02;
/* 1682 */       float m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12;
/* 1683 */       float m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22;
/*      */       
/* 1685 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02;
/* 1686 */       float m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12;
/* 1687 */       float m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22;
/*      */       
/* 1689 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02;
/* 1690 */       float m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12;
/* 1691 */       float m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22;
/*      */       
/* 1693 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1694 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1695 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
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
/*      */   public final void mulTransposeRight(Matrix3f m1, Matrix3f m2) {
/* 1709 */     if (this != m1 && this != m2) {
/* 1710 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02;
/* 1711 */       this.m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12;
/* 1712 */       this.m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22;
/*      */       
/* 1714 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02;
/* 1715 */       this.m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12;
/* 1716 */       this.m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22;
/*      */       
/* 1718 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02;
/* 1719 */       this.m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12;
/* 1720 */       this.m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1726 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02;
/* 1727 */       float m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12;
/* 1728 */       float m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22;
/*      */       
/* 1730 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02;
/* 1731 */       float m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12;
/* 1732 */       float m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22;
/*      */       
/* 1734 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02;
/* 1735 */       float m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12;
/* 1736 */       float m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22;
/*      */       
/* 1738 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1739 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1740 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
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
/*      */   public final void mulTransposeLeft(Matrix3f m1, Matrix3f m2) {
/* 1752 */     if (this != m1 && this != m2) {
/* 1753 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20;
/* 1754 */       this.m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21;
/* 1755 */       this.m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22;
/*      */       
/* 1757 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20;
/* 1758 */       this.m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21;
/* 1759 */       this.m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22;
/*      */       
/* 1761 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20;
/* 1762 */       this.m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21;
/* 1763 */       this.m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 1769 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20;
/* 1770 */       float m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21;
/* 1771 */       float m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22;
/*      */       
/* 1773 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20;
/* 1774 */       float m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21;
/* 1775 */       float m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22;
/*      */       
/* 1777 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20;
/* 1778 */       float m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21;
/* 1779 */       float m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22;
/*      */       
/* 1781 */       this.m00 = m00; this.m01 = m01; this.m02 = m02;
/* 1782 */       this.m10 = m10; this.m11 = m11; this.m12 = m12;
/* 1783 */       this.m20 = m20; this.m21 = m21; this.m22 = m22;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalize() {
/* 1792 */     double[] tmp_rot = new double[9];
/* 1793 */     double[] tmp_scale = new double[3];
/* 1794 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 1796 */     this.m00 = (float)tmp_rot[0];
/* 1797 */     this.m01 = (float)tmp_rot[1];
/* 1798 */     this.m02 = (float)tmp_rot[2];
/*      */     
/* 1800 */     this.m10 = (float)tmp_rot[3];
/* 1801 */     this.m11 = (float)tmp_rot[4];
/* 1802 */     this.m12 = (float)tmp_rot[5];
/*      */     
/* 1804 */     this.m20 = (float)tmp_rot[6];
/* 1805 */     this.m21 = (float)tmp_rot[7];
/* 1806 */     this.m22 = (float)tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalize(Matrix3f m1) {
/* 1816 */     double[] tmp = new double[9];
/* 1817 */     double[] tmp_rot = new double[9];
/* 1818 */     double[] tmp_scale = new double[3];
/*      */     
/* 1820 */     tmp[0] = m1.m00;
/* 1821 */     tmp[1] = m1.m01;
/* 1822 */     tmp[2] = m1.m02;
/*      */     
/* 1824 */     tmp[3] = m1.m10;
/* 1825 */     tmp[4] = m1.m11;
/* 1826 */     tmp[5] = m1.m12;
/*      */     
/* 1828 */     tmp[6] = m1.m20;
/* 1829 */     tmp[7] = m1.m21;
/* 1830 */     tmp[8] = m1.m22;
/*      */     
/* 1832 */     Matrix3d.compute_svd(tmp, tmp_scale, tmp_rot);
/*      */     
/* 1834 */     this.m00 = (float)tmp_rot[0];
/* 1835 */     this.m01 = (float)tmp_rot[1];
/* 1836 */     this.m02 = (float)tmp_rot[2];
/*      */     
/* 1838 */     this.m10 = (float)tmp_rot[3];
/* 1839 */     this.m11 = (float)tmp_rot[4];
/* 1840 */     this.m12 = (float)tmp_rot[5];
/*      */     
/* 1842 */     this.m20 = (float)tmp_rot[6];
/* 1843 */     this.m21 = (float)tmp_rot[7];
/* 1844 */     this.m22 = (float)tmp_rot[8];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalizeCP() {
/* 1853 */     float mag = 1.0F / (float)Math.sqrt((this.m00 * this.m00 + this.m10 * this.m10 + this.m20 * this.m20));
/* 1854 */     this.m00 *= mag;
/* 1855 */     this.m10 *= mag;
/* 1856 */     this.m20 *= mag;
/*      */     
/* 1858 */     mag = 1.0F / (float)Math.sqrt((this.m01 * this.m01 + this.m11 * this.m11 + this.m21 * this.m21));
/* 1859 */     this.m01 *= mag;
/* 1860 */     this.m11 *= mag;
/* 1861 */     this.m21 *= mag;
/*      */     
/* 1863 */     this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
/* 1864 */     this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
/* 1865 */     this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void normalizeCP(Matrix3f m1) {
/* 1876 */     float mag = 1.0F / (float)Math.sqrt((m1.m00 * m1.m00 + m1.m10 * m1.m10 + m1.m20 * m1.m20));
/* 1877 */     m1.m00 *= mag;
/* 1878 */     m1.m10 *= mag;
/* 1879 */     m1.m20 *= mag;
/*      */     
/* 1881 */     mag = 1.0F / (float)Math.sqrt((m1.m01 * m1.m01 + m1.m11 * m1.m11 + m1.m21 * m1.m21));
/* 1882 */     m1.m01 *= mag;
/* 1883 */     m1.m11 *= mag;
/* 1884 */     m1.m21 *= mag;
/*      */     
/* 1886 */     this.m02 = this.m10 * this.m21 - this.m11 * this.m20;
/* 1887 */     this.m12 = this.m01 * this.m20 - this.m00 * this.m21;
/* 1888 */     this.m22 = this.m00 * this.m11 - this.m01 * this.m10;
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
/*      */   public boolean equals(Matrix3f m1) {
/*      */     try {
/* 1902 */       return (this.m00 == m1.m00 && this.m01 == m1.m01 && this.m02 == m1.m02 && 
/* 1903 */         this.m10 == m1.m10 && this.m11 == m1.m11 && this.m12 == m1.m12 && 
/* 1904 */         this.m20 == m1.m20 && this.m21 == m1.m21 && this.m22 == m1.m22);
/*      */     } catch (NullPointerException e2) {
/* 1906 */       return false;
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
/*      */   public boolean equals(Object o1) {
/*      */     
/* 1922 */     try { Matrix3f m2 = (Matrix3f)o1;
/* 1923 */       return (this.m00 == m2.m00 && this.m01 == m2.m01 && this.m02 == m2.m02 && 
/* 1924 */         this.m10 == m2.m10 && this.m11 == m2.m11 && this.m12 == m2.m12 && 
/* 1925 */         this.m20 == m2.m20 && this.m21 == m2.m21 && this.m22 == m2.m22); }
/*      */     catch (ClassCastException e1)
/* 1927 */     { return false; }
/* 1928 */     catch (NullPointerException e2) { return false; }
/*      */   
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
/*      */   public boolean epsilonEquals(Matrix3f m1, float epsilon) {
/* 1942 */     boolean status = true;
/*      */     
/* 1944 */     if (Math.abs(this.m00 - m1.m00) > epsilon) status = false; 
/* 1945 */     if (Math.abs(this.m01 - m1.m01) > epsilon) status = false; 
/* 1946 */     if (Math.abs(this.m02 - m1.m02) > epsilon) status = false;
/*      */     
/* 1948 */     if (Math.abs(this.m10 - m1.m10) > epsilon) status = false; 
/* 1949 */     if (Math.abs(this.m11 - m1.m11) > epsilon) status = false; 
/* 1950 */     if (Math.abs(this.m12 - m1.m12) > epsilon) status = false;
/*      */     
/* 1952 */     if (Math.abs(this.m20 - m1.m20) > epsilon) status = false; 
/* 1953 */     if (Math.abs(this.m21 - m1.m21) > epsilon) status = false; 
/* 1954 */     if (Math.abs(this.m22 - m1.m22) > epsilon) status = false;
/*      */     
/* 1956 */     return status;
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
/*      */   public int hashCode() {
/* 1971 */     long bits = 1L;
/* 1972 */     bits = VecMathUtil.hashFloatBits(bits, this.m00);
/* 1973 */     bits = VecMathUtil.hashFloatBits(bits, this.m01);
/* 1974 */     bits = VecMathUtil.hashFloatBits(bits, this.m02);
/* 1975 */     bits = VecMathUtil.hashFloatBits(bits, this.m10);
/* 1976 */     bits = VecMathUtil.hashFloatBits(bits, this.m11);
/* 1977 */     bits = VecMathUtil.hashFloatBits(bits, this.m12);
/* 1978 */     bits = VecMathUtil.hashFloatBits(bits, this.m20);
/* 1979 */     bits = VecMathUtil.hashFloatBits(bits, this.m21);
/* 1980 */     bits = VecMathUtil.hashFloatBits(bits, this.m22);
/* 1981 */     return VecMathUtil.hashFinish(bits);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 1990 */     this.m00 = 0.0F;
/* 1991 */     this.m01 = 0.0F;
/* 1992 */     this.m02 = 0.0F;
/*      */     
/* 1994 */     this.m10 = 0.0F;
/* 1995 */     this.m11 = 0.0F;
/* 1996 */     this.m12 = 0.0F;
/*      */     
/* 1998 */     this.m20 = 0.0F;
/* 1999 */     this.m21 = 0.0F;
/* 2000 */     this.m22 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 2009 */     this.m00 = -this.m00;
/* 2010 */     this.m01 = -this.m01;
/* 2011 */     this.m02 = -this.m02;
/*      */     
/* 2013 */     this.m10 = -this.m10;
/* 2014 */     this.m11 = -this.m11;
/* 2015 */     this.m12 = -this.m12;
/*      */     
/* 2017 */     this.m20 = -this.m20;
/* 2018 */     this.m21 = -this.m21;
/* 2019 */     this.m22 = -this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix3f m1) {
/* 2030 */     this.m00 = -m1.m00;
/* 2031 */     this.m01 = -m1.m01;
/* 2032 */     this.m02 = -m1.m02;
/*      */     
/* 2034 */     this.m10 = -m1.m10;
/* 2035 */     this.m11 = -m1.m11;
/* 2036 */     this.m12 = -m1.m12;
/*      */     
/* 2038 */     this.m20 = -m1.m20;
/* 2039 */     this.m21 = -m1.m21;
/* 2040 */     this.m22 = -m1.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple3f t) {
/* 2051 */     float x = this.m00 * t.x + this.m01 * t.y + this.m02 * t.z;
/* 2052 */     float y = this.m10 * t.x + this.m11 * t.y + this.m12 * t.z;
/* 2053 */     float z = this.m20 * t.x + this.m21 * t.y + this.m22 * t.z;
/* 2054 */     t.set(x, y, z);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transform(Tuple3f t, Tuple3f result) {
/* 2065 */     float x = this.m00 * t.x + this.m01 * t.y + this.m02 * t.z;
/* 2066 */     float y = this.m10 * t.x + this.m11 * t.y + this.m12 * t.z;
/* 2067 */     result.z = this.m20 * t.x + this.m21 * t.y + this.m22 * t.z;
/* 2068 */     result.x = x;
/* 2069 */     result.y = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void getScaleRotate(double[] scales, double[] rot) {
/* 2077 */     double[] tmp = new double[9];
/* 2078 */     tmp[0] = this.m00;
/* 2079 */     tmp[1] = this.m01;
/* 2080 */     tmp[2] = this.m02;
/* 2081 */     tmp[3] = this.m10;
/* 2082 */     tmp[4] = this.m11;
/* 2083 */     tmp[5] = this.m12;
/* 2084 */     tmp[6] = this.m20;
/* 2085 */     tmp[7] = this.m21;
/* 2086 */     tmp[8] = this.m22;
/* 2087 */     Matrix3d.compute_svd(tmp, scales, rot);
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
/*      */   public Object clone() {
/* 2103 */     Matrix3f m1 = null;
/*      */     try {
/* 2105 */       m1 = (Matrix3f)super.clone();
/* 2106 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 2108 */       throw new InternalError();
/*      */     } 
/* 2110 */     return m1;
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
/*      */   public final float getM00() {
/* 2122 */     return this.m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM00(float m00) {
/* 2133 */     this.m00 = m00;
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
/*      */   public final float getM01() {
/* 2145 */     return this.m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM01(float m01) {
/* 2156 */     this.m01 = m01;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM02() {
/* 2167 */     return this.m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM02(float m02) {
/* 2178 */     this.m02 = m02;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM10() {
/* 2189 */     return this.m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM10(float m10) {
/* 2200 */     this.m10 = m10;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM11() {
/* 2211 */     return this.m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM11(float m11) {
/* 2222 */     this.m11 = m11;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM12() {
/* 2233 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM12(float m12) {
/* 2242 */     this.m12 = m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM20() {
/* 2253 */     return this.m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM20(float m20) {
/* 2264 */     this.m20 = m20;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM21() {
/* 2275 */     return this.m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM21(float m21) {
/* 2286 */     this.m21 = m21;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM22() {
/* 2297 */     return this.m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM22(float m22) {
/* 2308 */     this.m22 = m22;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Matrix3f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */