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
/*      */ public class Matrix4f
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = -8405036035410109353L;
/*      */   public float m00;
/*      */   public float m01;
/*      */   public float m02;
/*      */   public float m03;
/*      */   public float m10;
/*      */   public float m11;
/*      */   public float m12;
/*      */   public float m13;
/*      */   public float m20;
/*      */   public float m21;
/*      */   public float m22;
/*      */   public float m23;
/*      */   public float m30;
/*      */   public float m31;
/*      */   public float m32;
/*      */   public float m33;
/*      */   private static final double EPS = 1.0E-8D;
/*      */   
/*      */   public Matrix4f(float m00, float m01, float m02, float m03, float m10, float m11, float m12, float m13, float m20, float m21, float m22, float m23, float m30, float m31, float m32, float m33) {
/*  150 */     this.m00 = m00;
/*  151 */     this.m01 = m01;
/*  152 */     this.m02 = m02;
/*  153 */     this.m03 = m03;
/*      */     
/*  155 */     this.m10 = m10;
/*  156 */     this.m11 = m11;
/*  157 */     this.m12 = m12;
/*  158 */     this.m13 = m13;
/*      */     
/*  160 */     this.m20 = m20;
/*  161 */     this.m21 = m21;
/*  162 */     this.m22 = m22;
/*  163 */     this.m23 = m23;
/*      */     
/*  165 */     this.m30 = m30;
/*  166 */     this.m31 = m31;
/*  167 */     this.m32 = m32;
/*  168 */     this.m33 = m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f(float[] v) {
/*  179 */     this.m00 = v[0];
/*  180 */     this.m01 = v[1];
/*  181 */     this.m02 = v[2];
/*  182 */     this.m03 = v[3];
/*      */     
/*  184 */     this.m10 = v[4];
/*  185 */     this.m11 = v[5];
/*  186 */     this.m12 = v[6];
/*  187 */     this.m13 = v[7];
/*      */     
/*  189 */     this.m20 = v[8];
/*  190 */     this.m21 = v[9];
/*  191 */     this.m22 = v[10];
/*  192 */     this.m23 = v[11];
/*      */     
/*  194 */     this.m30 = v[12];
/*  195 */     this.m31 = v[13];
/*  196 */     this.m32 = v[14];
/*  197 */     this.m33 = v[15];
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
/*      */   public Matrix4f(Quat4f q1, Vector3f t1, float s) {
/*  212 */     this.m00 = (float)(s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z));
/*  213 */     this.m10 = (float)(s * 2.0D * (q1.x * q1.y + q1.w * q1.z));
/*  214 */     this.m20 = (float)(s * 2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/*  216 */     this.m01 = (float)(s * 2.0D * (q1.x * q1.y - q1.w * q1.z));
/*  217 */     this.m11 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z));
/*  218 */     this.m21 = (float)(s * 2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/*  220 */     this.m02 = (float)(s * 2.0D * (q1.x * q1.z + q1.w * q1.y));
/*  221 */     this.m12 = (float)(s * 2.0D * (q1.y * q1.z - q1.w * q1.x));
/*  222 */     this.m22 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y));
/*      */     
/*  224 */     this.m03 = t1.x;
/*  225 */     this.m13 = t1.y;
/*  226 */     this.m23 = t1.z;
/*      */     
/*  228 */     this.m30 = 0.0F;
/*  229 */     this.m31 = 0.0F;
/*  230 */     this.m32 = 0.0F;
/*  231 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f(Matrix4d m1) {
/*  242 */     this.m00 = (float)m1.m00;
/*  243 */     this.m01 = (float)m1.m01;
/*  244 */     this.m02 = (float)m1.m02;
/*  245 */     this.m03 = (float)m1.m03;
/*      */     
/*  247 */     this.m10 = (float)m1.m10;
/*  248 */     this.m11 = (float)m1.m11;
/*  249 */     this.m12 = (float)m1.m12;
/*  250 */     this.m13 = (float)m1.m13;
/*      */     
/*  252 */     this.m20 = (float)m1.m20;
/*  253 */     this.m21 = (float)m1.m21;
/*  254 */     this.m22 = (float)m1.m22;
/*  255 */     this.m23 = (float)m1.m23;
/*      */     
/*  257 */     this.m30 = (float)m1.m30;
/*  258 */     this.m31 = (float)m1.m31;
/*  259 */     this.m32 = (float)m1.m32;
/*  260 */     this.m33 = (float)m1.m33;
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
/*      */   public Matrix4f(Matrix4f m1) {
/*  272 */     this.m00 = m1.m00;
/*  273 */     this.m01 = m1.m01;
/*  274 */     this.m02 = m1.m02;
/*  275 */     this.m03 = m1.m03;
/*      */     
/*  277 */     this.m10 = m1.m10;
/*  278 */     this.m11 = m1.m11;
/*  279 */     this.m12 = m1.m12;
/*  280 */     this.m13 = m1.m13;
/*      */     
/*  282 */     this.m20 = m1.m20;
/*  283 */     this.m21 = m1.m21;
/*  284 */     this.m22 = m1.m22;
/*  285 */     this.m23 = m1.m23;
/*      */     
/*  287 */     this.m30 = m1.m30;
/*  288 */     this.m31 = m1.m31;
/*  289 */     this.m32 = m1.m32;
/*  290 */     this.m33 = m1.m33;
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
/*      */   public Matrix4f(Matrix3f m1, Vector3f t1, float s) {
/*  306 */     this.m00 = m1.m00 * s;
/*  307 */     this.m01 = m1.m01 * s;
/*  308 */     this.m02 = m1.m02 * s;
/*  309 */     this.m03 = t1.x;
/*      */     
/*  311 */     this.m10 = m1.m10 * s;
/*  312 */     this.m11 = m1.m11 * s;
/*  313 */     this.m12 = m1.m12 * s;
/*  314 */     this.m13 = t1.y;
/*      */     
/*  316 */     this.m20 = m1.m20 * s;
/*  317 */     this.m21 = m1.m21 * s;
/*  318 */     this.m22 = m1.m22 * s;
/*  319 */     this.m23 = t1.z;
/*      */     
/*  321 */     this.m30 = 0.0F;
/*  322 */     this.m31 = 0.0F;
/*  323 */     this.m32 = 0.0F;
/*  324 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Matrix4f() {
/*  334 */     this.m00 = 0.0F;
/*  335 */     this.m01 = 0.0F;
/*  336 */     this.m02 = 0.0F;
/*  337 */     this.m03 = 0.0F;
/*      */     
/*  339 */     this.m10 = 0.0F;
/*  340 */     this.m11 = 0.0F;
/*  341 */     this.m12 = 0.0F;
/*  342 */     this.m13 = 0.0F;
/*      */     
/*  344 */     this.m20 = 0.0F;
/*  345 */     this.m21 = 0.0F;
/*  346 */     this.m22 = 0.0F;
/*  347 */     this.m23 = 0.0F;
/*      */     
/*  349 */     this.m30 = 0.0F;
/*  350 */     this.m31 = 0.0F;
/*  351 */     this.m32 = 0.0F;
/*  352 */     this.m33 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  362 */     return 
/*  363 */       String.valueOf(this.m00) + ", " + this.m01 + ", " + this.m02 + ", " + this.m03 + "\n" + 
/*  364 */       this.m10 + ", " + this.m11 + ", " + this.m12 + ", " + this.m13 + "\n" + 
/*  365 */       this.m20 + ", " + this.m21 + ", " + this.m22 + ", " + this.m23 + "\n" + 
/*  366 */       this.m30 + ", " + this.m31 + ", " + this.m32 + ", " + this.m33 + "\n";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIdentity() {
/*  374 */     this.m00 = 1.0F;
/*  375 */     this.m01 = 0.0F;
/*  376 */     this.m02 = 0.0F;
/*  377 */     this.m03 = 0.0F;
/*      */     
/*  379 */     this.m10 = 0.0F;
/*  380 */     this.m11 = 1.0F;
/*  381 */     this.m12 = 0.0F;
/*  382 */     this.m13 = 0.0F;
/*      */     
/*  384 */     this.m20 = 0.0F;
/*  385 */     this.m21 = 0.0F;
/*  386 */     this.m22 = 1.0F;
/*  387 */     this.m23 = 0.0F;
/*      */     
/*  389 */     this.m30 = 0.0F;
/*  390 */     this.m31 = 0.0F;
/*  391 */     this.m32 = 0.0F;
/*  392 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setElement(int row, int column, float value) {
/*  403 */     switch (row) {
/*      */       
/*      */       case 0:
/*  406 */         switch (column) {
/*      */           
/*      */           case 0:
/*  409 */             this.m00 = value;
/*      */             return;
/*      */           case 1:
/*  412 */             this.m01 = value;
/*      */             return;
/*      */           case 2:
/*  415 */             this.m02 = value;
/*      */             return;
/*      */           case 3:
/*  418 */             this.m03 = value;
/*      */             return;
/*      */         } 
/*  421 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 1:
/*  426 */         switch (column) {
/*      */           
/*      */           case 0:
/*  429 */             this.m10 = value;
/*      */             return;
/*      */           case 1:
/*  432 */             this.m11 = value;
/*      */             return;
/*      */           case 2:
/*  435 */             this.m12 = value;
/*      */             return;
/*      */           case 3:
/*  438 */             this.m13 = value;
/*      */             return;
/*      */         } 
/*  441 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 2:
/*  446 */         switch (column) {
/*      */           
/*      */           case 0:
/*  449 */             this.m20 = value;
/*      */             return;
/*      */           case 1:
/*  452 */             this.m21 = value;
/*      */             return;
/*      */           case 2:
/*  455 */             this.m22 = value;
/*      */             return;
/*      */           case 3:
/*  458 */             this.m23 = value;
/*      */             return;
/*      */         } 
/*  461 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */ 
/*      */ 
/*      */       
/*      */       case 3:
/*  466 */         switch (column) {
/*      */           
/*      */           case 0:
/*  469 */             this.m30 = value;
/*      */             return;
/*      */           case 1:
/*  472 */             this.m31 = value;
/*      */             return;
/*      */           case 2:
/*  475 */             this.m32 = value;
/*      */             return;
/*      */           case 3:
/*  478 */             this.m33 = value;
/*      */             return;
/*      */         } 
/*  481 */         throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  486 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f0"));
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
/*  498 */     switch (row) {
/*      */       
/*      */       case 0:
/*  501 */         switch (column) {
/*      */           
/*      */           case 0:
/*  504 */             return this.m00;
/*      */           case 1:
/*  506 */             return this.m01;
/*      */           case 2:
/*  508 */             return this.m02;
/*      */           case 3:
/*  510 */             return this.m03;
/*      */         } 
/*      */         
/*      */         break;
/*      */       
/*      */       case 1:
/*  516 */         switch (column) {
/*      */           
/*      */           case 0:
/*  519 */             return this.m10;
/*      */           case 1:
/*  521 */             return this.m11;
/*      */           case 2:
/*  523 */             return this.m12;
/*      */           case 3:
/*  525 */             return this.m13;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 2:
/*  532 */         switch (column) {
/*      */           
/*      */           case 0:
/*  535 */             return this.m20;
/*      */           case 1:
/*  537 */             return this.m21;
/*      */           case 2:
/*  539 */             return this.m22;
/*      */           case 3:
/*  541 */             return this.m23;
/*      */         } 
/*      */ 
/*      */         
/*      */         break;
/*      */       
/*      */       case 3:
/*  548 */         switch (column) {
/*      */           
/*      */           case 0:
/*  551 */             return this.m30;
/*      */           case 1:
/*  553 */             return this.m31;
/*      */           case 2:
/*  555 */             return this.m32;
/*      */           case 3:
/*  557 */             return this.m33;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  566 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f1"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, Vector4f v) {
/*  575 */     if (row == 0) {
/*  576 */       v.x = this.m00;
/*  577 */       v.y = this.m01;
/*  578 */       v.z = this.m02;
/*  579 */       v.w = this.m03;
/*  580 */     } else if (row == 1) {
/*  581 */       v.x = this.m10;
/*  582 */       v.y = this.m11;
/*  583 */       v.z = this.m12;
/*  584 */       v.w = this.m13;
/*  585 */     } else if (row == 2) {
/*  586 */       v.x = this.m20;
/*  587 */       v.y = this.m21;
/*  588 */       v.z = this.m22;
/*  589 */       v.w = this.m23;
/*  590 */     } else if (row == 3) {
/*  591 */       v.x = this.m30;
/*  592 */       v.y = this.m31;
/*  593 */       v.z = this.m32;
/*  594 */       v.w = this.m33;
/*      */     } else {
/*  596 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f2"));
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
/*  607 */     if (row == 0) {
/*  608 */       v[0] = this.m00;
/*  609 */       v[1] = this.m01;
/*  610 */       v[2] = this.m02;
/*  611 */       v[3] = this.m03;
/*  612 */     } else if (row == 1) {
/*  613 */       v[0] = this.m10;
/*  614 */       v[1] = this.m11;
/*  615 */       v[2] = this.m12;
/*  616 */       v[3] = this.m13;
/*  617 */     } else if (row == 2) {
/*  618 */       v[0] = this.m20;
/*  619 */       v[1] = this.m21;
/*  620 */       v[2] = this.m22;
/*  621 */       v[3] = this.m23;
/*  622 */     } else if (row == 3) {
/*  623 */       v[0] = this.m30;
/*  624 */       v[1] = this.m31;
/*  625 */       v[2] = this.m32;
/*  626 */       v[3] = this.m33;
/*      */     } else {
/*  628 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f2"));
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
/*      */   public final void getColumn(int column, Vector4f v) {
/*  640 */     if (column == 0) {
/*  641 */       v.x = this.m00;
/*  642 */       v.y = this.m10;
/*  643 */       v.z = this.m20;
/*  644 */       v.w = this.m30;
/*  645 */     } else if (column == 1) {
/*  646 */       v.x = this.m01;
/*  647 */       v.y = this.m11;
/*  648 */       v.z = this.m21;
/*  649 */       v.w = this.m31;
/*  650 */     } else if (column == 2) {
/*  651 */       v.x = this.m02;
/*  652 */       v.y = this.m12;
/*  653 */       v.z = this.m22;
/*  654 */       v.w = this.m32;
/*  655 */     } else if (column == 3) {
/*  656 */       v.x = this.m03;
/*  657 */       v.y = this.m13;
/*  658 */       v.z = this.m23;
/*  659 */       v.w = this.m33;
/*      */     } else {
/*  661 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f4"));
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
/*  673 */     if (column == 0) {
/*  674 */       v[0] = this.m00;
/*  675 */       v[1] = this.m10;
/*  676 */       v[2] = this.m20;
/*  677 */       v[3] = this.m30;
/*  678 */     } else if (column == 1) {
/*  679 */       v[0] = this.m01;
/*  680 */       v[1] = this.m11;
/*  681 */       v[2] = this.m21;
/*  682 */       v[3] = this.m31;
/*  683 */     } else if (column == 2) {
/*  684 */       v[0] = this.m02;
/*  685 */       v[1] = this.m12;
/*  686 */       v[2] = this.m22;
/*  687 */       v[3] = this.m32;
/*  688 */     } else if (column == 3) {
/*  689 */       v[0] = this.m03;
/*  690 */       v[1] = this.m13;
/*  691 */       v[2] = this.m23;
/*  692 */       v[3] = this.m33;
/*      */     } else {
/*  694 */       throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f4"));
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
/*      */   public final void setScale(float scale) {
/*  708 */     double[] tmp_rot = new double[9];
/*  709 */     double[] tmp_scale = new double[3];
/*  710 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  712 */     this.m00 = (float)(tmp_rot[0] * scale);
/*  713 */     this.m01 = (float)(tmp_rot[1] * scale);
/*  714 */     this.m02 = (float)(tmp_rot[2] * scale);
/*      */     
/*  716 */     this.m10 = (float)(tmp_rot[3] * scale);
/*  717 */     this.m11 = (float)(tmp_rot[4] * scale);
/*  718 */     this.m12 = (float)(tmp_rot[5] * scale);
/*      */     
/*  720 */     this.m20 = (float)(tmp_rot[6] * scale);
/*  721 */     this.m21 = (float)(tmp_rot[7] * scale);
/*  722 */     this.m22 = (float)(tmp_rot[8] * scale);
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
/*      */   public final void get(Matrix3d m1) {
/*  734 */     double[] tmp_rot = new double[9];
/*  735 */     double[] tmp_scale = new double[3];
/*      */     
/*  737 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  739 */     m1.m00 = tmp_rot[0];
/*  740 */     m1.m01 = tmp_rot[1];
/*  741 */     m1.m02 = tmp_rot[2];
/*      */     
/*  743 */     m1.m10 = tmp_rot[3];
/*  744 */     m1.m11 = tmp_rot[4];
/*  745 */     m1.m12 = tmp_rot[5];
/*      */     
/*  747 */     m1.m20 = tmp_rot[6];
/*  748 */     m1.m21 = tmp_rot[7];
/*  749 */     m1.m22 = tmp_rot[8];
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
/*      */   public final void get(Matrix3f m1) {
/*  761 */     double[] tmp_rot = new double[9];
/*  762 */     double[] tmp_scale = new double[3];
/*      */     
/*  764 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  766 */     m1.m00 = (float)tmp_rot[0];
/*  767 */     m1.m01 = (float)tmp_rot[1];
/*  768 */     m1.m02 = (float)tmp_rot[2];
/*      */     
/*  770 */     m1.m10 = (float)tmp_rot[3];
/*  771 */     m1.m11 = (float)tmp_rot[4];
/*  772 */     m1.m12 = (float)tmp_rot[5];
/*      */     
/*  774 */     m1.m20 = (float)tmp_rot[6];
/*  775 */     m1.m21 = (float)tmp_rot[7];
/*  776 */     m1.m22 = (float)tmp_rot[8];
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
/*      */   public final float get(Matrix3f m1, Vector3f t1) {
/*  791 */     double[] tmp_rot = new double[9];
/*  792 */     double[] tmp_scale = new double[3];
/*      */     
/*  794 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  796 */     m1.m00 = (float)tmp_rot[0];
/*  797 */     m1.m01 = (float)tmp_rot[1];
/*  798 */     m1.m02 = (float)tmp_rot[2];
/*      */     
/*  800 */     m1.m10 = (float)tmp_rot[3];
/*  801 */     m1.m11 = (float)tmp_rot[4];
/*  802 */     m1.m12 = (float)tmp_rot[5];
/*      */     
/*  804 */     m1.m20 = (float)tmp_rot[6];
/*  805 */     m1.m21 = (float)tmp_rot[7];
/*  806 */     m1.m22 = (float)tmp_rot[8];
/*      */     
/*  808 */     t1.x = this.m03;
/*  809 */     t1.y = this.m13;
/*  810 */     t1.z = this.m23;
/*      */     
/*  812 */     return (float)Matrix3d.max3(tmp_scale);
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
/*      */   public final void get(Quat4f q1) {
/*  824 */     double[] tmp_rot = new double[9];
/*  825 */     double[] tmp_scale = new double[3];
/*  826 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */ 
/*      */ 
/*      */     
/*  830 */     double ww = 0.25D * (1.0D + tmp_rot[0] + tmp_rot[4] + tmp_rot[8]);
/*  831 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  832 */       q1.w = (float)Math.sqrt(ww);
/*  833 */       ww = 0.25D / q1.w;
/*  834 */       q1.x = (float)((tmp_rot[7] - tmp_rot[5]) * ww);
/*  835 */       q1.y = (float)((tmp_rot[2] - tmp_rot[6]) * ww);
/*  836 */       q1.z = (float)((tmp_rot[3] - tmp_rot[1]) * ww);
/*      */       
/*      */       return;
/*      */     } 
/*  840 */     q1.w = 0.0F;
/*  841 */     ww = -0.5D * (tmp_rot[4] + tmp_rot[8]);
/*  842 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  843 */       q1.x = (float)Math.sqrt(ww);
/*  844 */       ww = 0.5D / q1.x;
/*  845 */       q1.y = (float)(tmp_rot[3] * ww);
/*  846 */       q1.z = (float)(tmp_rot[6] * ww);
/*      */       
/*      */       return;
/*      */     } 
/*  850 */     q1.x = 0.0F;
/*  851 */     ww = 0.5D * (1.0D - tmp_rot[8]);
/*  852 */     if (((ww < 0.0D) ? -ww : ww) >= 1.0E-30D) {
/*  853 */       q1.y = (float)Math.sqrt(ww);
/*  854 */       q1.z = (float)(tmp_rot[7] / 2.0D * q1.y);
/*      */       
/*      */       return;
/*      */     } 
/*  858 */     q1.y = 0.0F;
/*  859 */     q1.z = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Vector3f trans) {
/*  870 */     trans.x = this.m03;
/*  871 */     trans.y = this.m13;
/*  872 */     trans.z = this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRotationScale(Matrix3f m1) {
/*  882 */     m1.m00 = this.m00; m1.m01 = this.m01; m1.m02 = this.m02;
/*  883 */     m1.m10 = this.m10; m1.m11 = this.m11; m1.m12 = this.m12;
/*  884 */     m1.m20 = this.m20; m1.m21 = this.m21; m1.m22 = this.m22;
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
/*      */   public final float getScale() {
/*  896 */     double[] tmp_rot = new double[9];
/*  897 */     double[] tmp_scale = new double[3];
/*      */     
/*  899 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/*  901 */     return (float)Matrix3d.max3(tmp_scale);
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
/*      */   public final void setRotationScale(Matrix3f m1) {
/*  913 */     this.m00 = m1.m00; this.m01 = m1.m01; this.m02 = m1.m02;
/*  914 */     this.m10 = m1.m10; this.m11 = m1.m11; this.m12 = m1.m12;
/*  915 */     this.m20 = m1.m20; this.m21 = m1.m21; this.m22 = m1.m22;
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
/*      */   public final void setRow(int row, float x, float y, float z, float w) {
/*  929 */     switch (row) {
/*      */       case 0:
/*  931 */         this.m00 = x;
/*  932 */         this.m01 = y;
/*  933 */         this.m02 = z;
/*  934 */         this.m03 = w;
/*      */         return;
/*      */       
/*      */       case 1:
/*  938 */         this.m10 = x;
/*  939 */         this.m11 = y;
/*  940 */         this.m12 = z;
/*  941 */         this.m13 = w;
/*      */         return;
/*      */       
/*      */       case 2:
/*  945 */         this.m20 = x;
/*  946 */         this.m21 = y;
/*  947 */         this.m22 = z;
/*  948 */         this.m23 = w;
/*      */         return;
/*      */       
/*      */       case 3:
/*  952 */         this.m30 = x;
/*  953 */         this.m31 = y;
/*  954 */         this.m32 = z;
/*  955 */         this.m33 = w;
/*      */         return;
/*      */     } 
/*      */     
/*  959 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setRow(int row, Vector4f v) {
/*  970 */     switch (row) {
/*      */       case 0:
/*  972 */         this.m00 = v.x;
/*  973 */         this.m01 = v.y;
/*  974 */         this.m02 = v.z;
/*  975 */         this.m03 = v.w;
/*      */         return;
/*      */       
/*      */       case 1:
/*  979 */         this.m10 = v.x;
/*  980 */         this.m11 = v.y;
/*  981 */         this.m12 = v.z;
/*  982 */         this.m13 = v.w;
/*      */         return;
/*      */       
/*      */       case 2:
/*  986 */         this.m20 = v.x;
/*  987 */         this.m21 = v.y;
/*  988 */         this.m22 = v.z;
/*  989 */         this.m23 = v.w;
/*      */         return;
/*      */       
/*      */       case 3:
/*  993 */         this.m30 = v.x;
/*  994 */         this.m31 = v.y;
/*  995 */         this.m32 = v.z;
/*  996 */         this.m33 = v.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1000 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
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
/*      */   public final void setRow(int row, float[] v) {
/* 1012 */     switch (row) {
/*      */       case 0:
/* 1014 */         this.m00 = v[0];
/* 1015 */         this.m01 = v[1];
/* 1016 */         this.m02 = v[2];
/* 1017 */         this.m03 = v[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1021 */         this.m10 = v[0];
/* 1022 */         this.m11 = v[1];
/* 1023 */         this.m12 = v[2];
/* 1024 */         this.m13 = v[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1028 */         this.m20 = v[0];
/* 1029 */         this.m21 = v[1];
/* 1030 */         this.m22 = v[2];
/* 1031 */         this.m23 = v[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1035 */         this.m30 = v[0];
/* 1036 */         this.m31 = v[1];
/* 1037 */         this.m32 = v[2];
/* 1038 */         this.m33 = v[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1042 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f6"));
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
/*      */   public final void setColumn(int column, float x, float y, float z, float w) {
/* 1056 */     switch (column) {
/*      */       case 0:
/* 1058 */         this.m00 = x;
/* 1059 */         this.m10 = y;
/* 1060 */         this.m20 = z;
/* 1061 */         this.m30 = w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1065 */         this.m01 = x;
/* 1066 */         this.m11 = y;
/* 1067 */         this.m21 = z;
/* 1068 */         this.m31 = w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1072 */         this.m02 = x;
/* 1073 */         this.m12 = y;
/* 1074 */         this.m22 = z;
/* 1075 */         this.m32 = w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1079 */         this.m03 = x;
/* 1080 */         this.m13 = y;
/* 1081 */         this.m23 = z;
/* 1082 */         this.m33 = w;
/*      */         return;
/*      */     } 
/*      */     
/* 1086 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setColumn(int column, Vector4f v) {
/* 1097 */     switch (column) {
/*      */       case 0:
/* 1099 */         this.m00 = v.x;
/* 1100 */         this.m10 = v.y;
/* 1101 */         this.m20 = v.z;
/* 1102 */         this.m30 = v.w;
/*      */         return;
/*      */       
/*      */       case 1:
/* 1106 */         this.m01 = v.x;
/* 1107 */         this.m11 = v.y;
/* 1108 */         this.m21 = v.z;
/* 1109 */         this.m31 = v.w;
/*      */         return;
/*      */       
/*      */       case 2:
/* 1113 */         this.m02 = v.x;
/* 1114 */         this.m12 = v.y;
/* 1115 */         this.m22 = v.z;
/* 1116 */         this.m32 = v.w;
/*      */         return;
/*      */       
/*      */       case 3:
/* 1120 */         this.m03 = v.x;
/* 1121 */         this.m13 = v.y;
/* 1122 */         this.m23 = v.z;
/* 1123 */         this.m33 = v.w;
/*      */         return;
/*      */     } 
/*      */     
/* 1127 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
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
/* 1138 */     switch (column) {
/*      */       case 0:
/* 1140 */         this.m00 = v[0];
/* 1141 */         this.m10 = v[1];
/* 1142 */         this.m20 = v[2];
/* 1143 */         this.m30 = v[3];
/*      */         return;
/*      */       
/*      */       case 1:
/* 1147 */         this.m01 = v[0];
/* 1148 */         this.m11 = v[1];
/* 1149 */         this.m21 = v[2];
/* 1150 */         this.m31 = v[3];
/*      */         return;
/*      */       
/*      */       case 2:
/* 1154 */         this.m02 = v[0];
/* 1155 */         this.m12 = v[1];
/* 1156 */         this.m22 = v[2];
/* 1157 */         this.m32 = v[3];
/*      */         return;
/*      */       
/*      */       case 3:
/* 1161 */         this.m03 = v[0];
/* 1162 */         this.m13 = v[1];
/* 1163 */         this.m23 = v[2];
/* 1164 */         this.m33 = v[3];
/*      */         return;
/*      */     } 
/*      */     
/* 1168 */     throw new ArrayIndexOutOfBoundsException(VecMathI18N.getString("Matrix4f9"));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float scalar) {
/* 1178 */     this.m00 += scalar;
/* 1179 */     this.m01 += scalar;
/* 1180 */     this.m02 += scalar;
/* 1181 */     this.m03 += scalar;
/* 1182 */     this.m10 += scalar;
/* 1183 */     this.m11 += scalar;
/* 1184 */     this.m12 += scalar;
/* 1185 */     this.m13 += scalar;
/* 1186 */     this.m20 += scalar;
/* 1187 */     this.m21 += scalar;
/* 1188 */     this.m22 += scalar;
/* 1189 */     this.m23 += scalar;
/* 1190 */     this.m30 += scalar;
/* 1191 */     this.m31 += scalar;
/* 1192 */     this.m32 += scalar;
/* 1193 */     this.m33 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(float scalar, Matrix4f m1) {
/* 1204 */     m1.m00 += scalar;
/* 1205 */     m1.m01 += scalar;
/* 1206 */     m1.m02 += scalar;
/* 1207 */     m1.m03 += scalar;
/* 1208 */     m1.m10 += scalar;
/* 1209 */     m1.m11 += scalar;
/* 1210 */     m1.m12 += scalar;
/* 1211 */     m1.m13 += scalar;
/* 1212 */     m1.m20 += scalar;
/* 1213 */     m1.m21 += scalar;
/* 1214 */     m1.m22 += scalar;
/* 1215 */     m1.m23 += scalar;
/* 1216 */     m1.m30 += scalar;
/* 1217 */     m1.m31 += scalar;
/* 1218 */     m1.m32 += scalar;
/* 1219 */     m1.m33 += scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4f m1, Matrix4f m2) {
/* 1229 */     m1.m00 += m2.m00;
/* 1230 */     m1.m01 += m2.m01;
/* 1231 */     m1.m02 += m2.m02;
/* 1232 */     m1.m03 += m2.m03;
/*      */     
/* 1234 */     m1.m10 += m2.m10;
/* 1235 */     m1.m11 += m2.m11;
/* 1236 */     m1.m12 += m2.m12;
/* 1237 */     m1.m13 += m2.m13;
/*      */     
/* 1239 */     m1.m20 += m2.m20;
/* 1240 */     m1.m21 += m2.m21;
/* 1241 */     m1.m22 += m2.m22;
/* 1242 */     m1.m23 += m2.m23;
/*      */     
/* 1244 */     m1.m30 += m2.m30;
/* 1245 */     m1.m31 += m2.m31;
/* 1246 */     m1.m32 += m2.m32;
/* 1247 */     m1.m33 += m2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void add(Matrix4f m1) {
/* 1257 */     this.m00 += m1.m00;
/* 1258 */     this.m01 += m1.m01;
/* 1259 */     this.m02 += m1.m02;
/* 1260 */     this.m03 += m1.m03;
/*      */     
/* 1262 */     this.m10 += m1.m10;
/* 1263 */     this.m11 += m1.m11;
/* 1264 */     this.m12 += m1.m12;
/* 1265 */     this.m13 += m1.m13;
/*      */     
/* 1267 */     this.m20 += m1.m20;
/* 1268 */     this.m21 += m1.m21;
/* 1269 */     this.m22 += m1.m22;
/* 1270 */     this.m23 += m1.m23;
/*      */     
/* 1272 */     this.m30 += m1.m30;
/* 1273 */     this.m31 += m1.m31;
/* 1274 */     this.m32 += m1.m32;
/* 1275 */     this.m33 += m1.m33;
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
/*      */   public final void sub(Matrix4f m1, Matrix4f m2) {
/* 1287 */     m1.m00 -= m2.m00;
/* 1288 */     m1.m01 -= m2.m01;
/* 1289 */     m1.m02 -= m2.m02;
/* 1290 */     m1.m03 -= m2.m03;
/*      */     
/* 1292 */     m1.m10 -= m2.m10;
/* 1293 */     m1.m11 -= m2.m11;
/* 1294 */     m1.m12 -= m2.m12;
/* 1295 */     m1.m13 -= m2.m13;
/*      */     
/* 1297 */     m1.m20 -= m2.m20;
/* 1298 */     m1.m21 -= m2.m21;
/* 1299 */     m1.m22 -= m2.m22;
/* 1300 */     m1.m23 -= m2.m23;
/*      */     
/* 1302 */     m1.m30 -= m2.m30;
/* 1303 */     m1.m31 -= m2.m31;
/* 1304 */     m1.m32 -= m2.m32;
/* 1305 */     m1.m33 -= m2.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void sub(Matrix4f m1) {
/* 1315 */     this.m00 -= m1.m00;
/* 1316 */     this.m01 -= m1.m01;
/* 1317 */     this.m02 -= m1.m02;
/* 1318 */     this.m03 -= m1.m03;
/*      */     
/* 1320 */     this.m10 -= m1.m10;
/* 1321 */     this.m11 -= m1.m11;
/* 1322 */     this.m12 -= m1.m12;
/* 1323 */     this.m13 -= m1.m13;
/*      */     
/* 1325 */     this.m20 -= m1.m20;
/* 1326 */     this.m21 -= m1.m21;
/* 1327 */     this.m22 -= m1.m22;
/* 1328 */     this.m23 -= m1.m23;
/*      */     
/* 1330 */     this.m30 -= m1.m30;
/* 1331 */     this.m31 -= m1.m31;
/* 1332 */     this.m32 -= m1.m32;
/* 1333 */     this.m33 -= m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose() {
/* 1343 */     float temp = this.m10;
/* 1344 */     this.m10 = this.m01;
/* 1345 */     this.m01 = temp;
/*      */     
/* 1347 */     temp = this.m20;
/* 1348 */     this.m20 = this.m02;
/* 1349 */     this.m02 = temp;
/*      */     
/* 1351 */     temp = this.m30;
/* 1352 */     this.m30 = this.m03;
/* 1353 */     this.m03 = temp;
/*      */     
/* 1355 */     temp = this.m21;
/* 1356 */     this.m21 = this.m12;
/* 1357 */     this.m12 = temp;
/*      */     
/* 1359 */     temp = this.m31;
/* 1360 */     this.m31 = this.m13;
/* 1361 */     this.m13 = temp;
/*      */     
/* 1363 */     temp = this.m32;
/* 1364 */     this.m32 = this.m23;
/* 1365 */     this.m23 = temp;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void transpose(Matrix4f m1) {
/* 1374 */     if (this != m1) {
/* 1375 */       this.m00 = m1.m00;
/* 1376 */       this.m01 = m1.m10;
/* 1377 */       this.m02 = m1.m20;
/* 1378 */       this.m03 = m1.m30;
/*      */       
/* 1380 */       this.m10 = m1.m01;
/* 1381 */       this.m11 = m1.m11;
/* 1382 */       this.m12 = m1.m21;
/* 1383 */       this.m13 = m1.m31;
/*      */       
/* 1385 */       this.m20 = m1.m02;
/* 1386 */       this.m21 = m1.m12;
/* 1387 */       this.m22 = m1.m22;
/* 1388 */       this.m23 = m1.m32;
/*      */       
/* 1390 */       this.m30 = m1.m03;
/* 1391 */       this.m31 = m1.m13;
/* 1392 */       this.m32 = m1.m23;
/* 1393 */       this.m33 = m1.m33;
/*      */     } else {
/* 1395 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4f q1) {
/* 1405 */     this.m00 = 1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z;
/* 1406 */     this.m10 = 2.0F * (q1.x * q1.y + q1.w * q1.z);
/* 1407 */     this.m20 = 2.0F * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1409 */     this.m01 = 2.0F * (q1.x * q1.y - q1.w * q1.z);
/* 1410 */     this.m11 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z;
/* 1411 */     this.m21 = 2.0F * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1413 */     this.m02 = 2.0F * (q1.x * q1.z + q1.w * q1.y);
/* 1414 */     this.m12 = 2.0F * (q1.y * q1.z - q1.w * q1.x);
/* 1415 */     this.m22 = 1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y;
/*      */     
/* 1417 */     this.m03 = 0.0F;
/* 1418 */     this.m13 = 0.0F;
/* 1419 */     this.m23 = 0.0F;
/*      */     
/* 1421 */     this.m30 = 0.0F;
/* 1422 */     this.m31 = 0.0F;
/* 1423 */     this.m32 = 0.0F;
/* 1424 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4f a1) {
/* 1434 */     float mag = (float)Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/* 1435 */     if (mag < 1.0E-8D) {
/* 1436 */       this.m00 = 1.0F;
/* 1437 */       this.m01 = 0.0F;
/* 1438 */       this.m02 = 0.0F;
/*      */       
/* 1440 */       this.m10 = 0.0F;
/* 1441 */       this.m11 = 1.0F;
/* 1442 */       this.m12 = 0.0F;
/*      */       
/* 1444 */       this.m20 = 0.0F;
/* 1445 */       this.m21 = 0.0F;
/* 1446 */       this.m22 = 1.0F;
/*      */     } else {
/* 1448 */       mag = 1.0F / mag;
/* 1449 */       float ax = a1.x * mag;
/* 1450 */       float ay = a1.y * mag;
/* 1451 */       float az = a1.z * mag;
/*      */       
/* 1453 */       float sinTheta = (float)Math.sin(a1.angle);
/* 1454 */       float cosTheta = (float)Math.cos(a1.angle);
/* 1455 */       float t = 1.0F - cosTheta;
/*      */       
/* 1457 */       float xz = ax * az;
/* 1458 */       float xy = ax * ay;
/* 1459 */       float yz = ay * az;
/*      */       
/* 1461 */       this.m00 = t * ax * ax + cosTheta;
/* 1462 */       this.m01 = t * xy - sinTheta * az;
/* 1463 */       this.m02 = t * xz + sinTheta * ay;
/*      */       
/* 1465 */       this.m10 = t * xy + sinTheta * az;
/* 1466 */       this.m11 = t * ay * ay + cosTheta;
/* 1467 */       this.m12 = t * yz - sinTheta * ax;
/*      */       
/* 1469 */       this.m20 = t * xz - sinTheta * ay;
/* 1470 */       this.m21 = t * yz + sinTheta * ax;
/* 1471 */       this.m22 = t * az * az + cosTheta;
/*      */     } 
/* 1473 */     this.m03 = 0.0F;
/* 1474 */     this.m13 = 0.0F;
/* 1475 */     this.m23 = 0.0F;
/*      */     
/* 1477 */     this.m30 = 0.0F;
/* 1478 */     this.m31 = 0.0F;
/* 1479 */     this.m32 = 0.0F;
/* 1480 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Quat4d q1) {
/* 1490 */     this.m00 = (float)(1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z);
/* 1491 */     this.m10 = (float)(2.0D * (q1.x * q1.y + q1.w * q1.z));
/* 1492 */     this.m20 = (float)(2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/* 1494 */     this.m01 = (float)(2.0D * (q1.x * q1.y - q1.w * q1.z));
/* 1495 */     this.m11 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z);
/* 1496 */     this.m21 = (float)(2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/* 1498 */     this.m02 = (float)(2.0D * (q1.x * q1.z + q1.w * q1.y));
/* 1499 */     this.m12 = (float)(2.0D * (q1.y * q1.z - q1.w * q1.x));
/* 1500 */     this.m22 = (float)(1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y);
/*      */     
/* 1502 */     this.m03 = 0.0F;
/* 1503 */     this.m13 = 0.0F;
/* 1504 */     this.m23 = 0.0F;
/*      */     
/* 1506 */     this.m30 = 0.0F;
/* 1507 */     this.m31 = 0.0F;
/* 1508 */     this.m32 = 0.0F;
/* 1509 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(AxisAngle4d a1) {
/* 1519 */     double mag = Math.sqrt(a1.x * a1.x + a1.y * a1.y + a1.z * a1.z);
/*      */     
/* 1521 */     if (mag < 1.0E-8D) {
/* 1522 */       this.m00 = 1.0F;
/* 1523 */       this.m01 = 0.0F;
/* 1524 */       this.m02 = 0.0F;
/*      */       
/* 1526 */       this.m10 = 0.0F;
/* 1527 */       this.m11 = 1.0F;
/* 1528 */       this.m12 = 0.0F;
/*      */       
/* 1530 */       this.m20 = 0.0F;
/* 1531 */       this.m21 = 0.0F;
/* 1532 */       this.m22 = 1.0F;
/*      */     } else {
/* 1534 */       mag = 1.0D / mag;
/* 1535 */       double ax = a1.x * mag;
/* 1536 */       double ay = a1.y * mag;
/* 1537 */       double az = a1.z * mag;
/*      */       
/* 1539 */       float sinTheta = (float)Math.sin(a1.angle);
/* 1540 */       float cosTheta = (float)Math.cos(a1.angle);
/* 1541 */       float t = 1.0F - cosTheta;
/*      */       
/* 1543 */       float xz = (float)(ax * az);
/* 1544 */       float xy = (float)(ax * ay);
/* 1545 */       float yz = (float)(ay * az);
/*      */       
/* 1547 */       this.m00 = t * (float)(ax * ax) + cosTheta;
/* 1548 */       this.m01 = t * xy - sinTheta * (float)az;
/* 1549 */       this.m02 = t * xz + sinTheta * (float)ay;
/*      */       
/* 1551 */       this.m10 = t * xy + sinTheta * (float)az;
/* 1552 */       this.m11 = t * (float)(ay * ay) + cosTheta;
/* 1553 */       this.m12 = t * yz - sinTheta * (float)ax;
/*      */       
/* 1555 */       this.m20 = t * xz - sinTheta * (float)ay;
/* 1556 */       this.m21 = t * yz + sinTheta * (float)ax;
/* 1557 */       this.m22 = t * (float)(az * az) + cosTheta;
/*      */     } 
/* 1559 */     this.m03 = 0.0F;
/* 1560 */     this.m13 = 0.0F;
/* 1561 */     this.m23 = 0.0F;
/*      */     
/* 1563 */     this.m30 = 0.0F;
/* 1564 */     this.m31 = 0.0F;
/* 1565 */     this.m32 = 0.0F;
/* 1566 */     this.m33 = 1.0F;
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
/* 1578 */     this.m00 = (float)(s * (1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z));
/* 1579 */     this.m10 = (float)(s * 2.0D * (q1.x * q1.y + q1.w * q1.z));
/* 1580 */     this.m20 = (float)(s * 2.0D * (q1.x * q1.z - q1.w * q1.y));
/*      */     
/* 1582 */     this.m01 = (float)(s * 2.0D * (q1.x * q1.y - q1.w * q1.z));
/* 1583 */     this.m11 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z));
/* 1584 */     this.m21 = (float)(s * 2.0D * (q1.y * q1.z + q1.w * q1.x));
/*      */     
/* 1586 */     this.m02 = (float)(s * 2.0D * (q1.x * q1.z + q1.w * q1.y));
/* 1587 */     this.m12 = (float)(s * 2.0D * (q1.y * q1.z - q1.w * q1.x));
/* 1588 */     this.m22 = (float)(s * (1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y));
/*      */     
/* 1590 */     this.m03 = (float)t1.x;
/* 1591 */     this.m13 = (float)t1.y;
/* 1592 */     this.m23 = (float)t1.z;
/*      */     
/* 1594 */     this.m30 = 0.0F;
/* 1595 */     this.m31 = 0.0F;
/* 1596 */     this.m32 = 0.0F;
/* 1597 */     this.m33 = 1.0F;
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
/* 1609 */     this.m00 = s * (1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z);
/* 1610 */     this.m10 = s * 2.0F * (q1.x * q1.y + q1.w * q1.z);
/* 1611 */     this.m20 = s * 2.0F * (q1.x * q1.z - q1.w * q1.y);
/*      */     
/* 1613 */     this.m01 = s * 2.0F * (q1.x * q1.y - q1.w * q1.z);
/* 1614 */     this.m11 = s * (1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z);
/* 1615 */     this.m21 = s * 2.0F * (q1.y * q1.z + q1.w * q1.x);
/*      */     
/* 1617 */     this.m02 = s * 2.0F * (q1.x * q1.z + q1.w * q1.y);
/* 1618 */     this.m12 = s * 2.0F * (q1.y * q1.z - q1.w * q1.x);
/* 1619 */     this.m22 = s * (1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y);
/*      */     
/* 1621 */     this.m03 = t1.x;
/* 1622 */     this.m13 = t1.y;
/* 1623 */     this.m23 = t1.z;
/*      */     
/* 1625 */     this.m30 = 0.0F;
/* 1626 */     this.m31 = 0.0F;
/* 1627 */     this.m32 = 0.0F;
/* 1628 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4d m1) {
/* 1638 */     this.m00 = (float)m1.m00;
/* 1639 */     this.m01 = (float)m1.m01;
/* 1640 */     this.m02 = (float)m1.m02;
/* 1641 */     this.m03 = (float)m1.m03;
/*      */     
/* 1643 */     this.m10 = (float)m1.m10;
/* 1644 */     this.m11 = (float)m1.m11;
/* 1645 */     this.m12 = (float)m1.m12;
/* 1646 */     this.m13 = (float)m1.m13;
/*      */     
/* 1648 */     this.m20 = (float)m1.m20;
/* 1649 */     this.m21 = (float)m1.m21;
/* 1650 */     this.m22 = (float)m1.m22;
/* 1651 */     this.m23 = (float)m1.m23;
/*      */     
/* 1653 */     this.m30 = (float)m1.m30;
/* 1654 */     this.m31 = (float)m1.m31;
/* 1655 */     this.m32 = (float)m1.m32;
/* 1656 */     this.m33 = (float)m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4f m1) {
/* 1666 */     this.m00 = m1.m00;
/* 1667 */     this.m01 = m1.m01;
/* 1668 */     this.m02 = m1.m02;
/* 1669 */     this.m03 = m1.m03;
/*      */     
/* 1671 */     this.m10 = m1.m10;
/* 1672 */     this.m11 = m1.m11;
/* 1673 */     this.m12 = m1.m12;
/* 1674 */     this.m13 = m1.m13;
/*      */     
/* 1676 */     this.m20 = m1.m20;
/* 1677 */     this.m21 = m1.m21;
/* 1678 */     this.m22 = m1.m22;
/* 1679 */     this.m23 = m1.m23;
/*      */     
/* 1681 */     this.m30 = m1.m30;
/* 1682 */     this.m31 = m1.m31;
/* 1683 */     this.m32 = m1.m32;
/* 1684 */     this.m33 = m1.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(Matrix4f m1) {
/* 1695 */     invertGeneral(m1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/* 1703 */     invertGeneral(this);
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
/*      */   final void invertGeneral(Matrix4f m1) {
/* 1715 */     double[] temp = new double[16];
/* 1716 */     double[] result = new double[16];
/* 1717 */     int[] row_perm = new int[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1724 */     temp[0] = m1.m00;
/* 1725 */     temp[1] = m1.m01;
/* 1726 */     temp[2] = m1.m02;
/* 1727 */     temp[3] = m1.m03;
/*      */     
/* 1729 */     temp[4] = m1.m10;
/* 1730 */     temp[5] = m1.m11;
/* 1731 */     temp[6] = m1.m12;
/* 1732 */     temp[7] = m1.m13;
/*      */     
/* 1734 */     temp[8] = m1.m20;
/* 1735 */     temp[9] = m1.m21;
/* 1736 */     temp[10] = m1.m22;
/* 1737 */     temp[11] = m1.m23;
/*      */     
/* 1739 */     temp[12] = m1.m30;
/* 1740 */     temp[13] = m1.m31;
/* 1741 */     temp[14] = m1.m32;
/* 1742 */     temp[15] = m1.m33;
/*      */ 
/*      */     
/* 1745 */     if (!luDecomposition(temp, row_perm))
/*      */     {
/* 1747 */       throw new SingularMatrixException(VecMathI18N.getString("Matrix4f12"));
/*      */     }
/*      */ 
/*      */     
/* 1751 */     for (int i = 0; i < 16; ) { result[i] = 0.0D; i++; }
/* 1752 */      result[0] = 1.0D; result[5] = 1.0D; result[10] = 1.0D; result[15] = 1.0D;
/* 1753 */     luBacksubstitution(temp, row_perm, result);
/*      */     
/* 1755 */     this.m00 = (float)result[0];
/* 1756 */     this.m01 = (float)result[1];
/* 1757 */     this.m02 = (float)result[2];
/* 1758 */     this.m03 = (float)result[3];
/*      */     
/* 1760 */     this.m10 = (float)result[4];
/* 1761 */     this.m11 = (float)result[5];
/* 1762 */     this.m12 = (float)result[6];
/* 1763 */     this.m13 = (float)result[7];
/*      */     
/* 1765 */     this.m20 = (float)result[8];
/* 1766 */     this.m21 = (float)result[9];
/* 1767 */     this.m22 = (float)result[10];
/* 1768 */     this.m23 = (float)result[11];
/*      */     
/* 1770 */     this.m30 = (float)result[12];
/* 1771 */     this.m31 = (float)result[13];
/* 1772 */     this.m32 = (float)result[14];
/* 1773 */     this.m33 = (float)result[15];
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
/* 1800 */     double[] row_scale = new double[4];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1808 */     int ptr = 0;
/* 1809 */     int rs = 0;
/*      */ 
/*      */     
/* 1812 */     int i = 4;
/* 1813 */     while (i-- != 0) {
/* 1814 */       double big = 0.0D;
/*      */ 
/*      */       
/* 1817 */       int k = 4;
/* 1818 */       while (k-- != 0) {
/* 1819 */         double temp = matrix0[ptr++];
/* 1820 */         temp = Math.abs(temp);
/* 1821 */         if (temp > big) {
/* 1822 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1827 */       if (big == 0.0D) {
/* 1828 */         return false;
/*      */       }
/* 1830 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1838 */     int mtx = 0;
/*      */ 
/*      */     
/* 1841 */     for (int j = 0; j < 4; j++) {
/*      */       int k;
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1847 */       for (k = 0; k < j; k++) {
/* 1848 */         int target = mtx + 4 * k + j;
/* 1849 */         double sum = matrix0[target];
/* 1850 */         int m = k;
/* 1851 */         int p1 = mtx + 4 * k;
/* 1852 */         int p2 = mtx + j;
/* 1853 */         while (m-- != 0) {
/* 1854 */           sum -= matrix0[p1] * matrix0[p2];
/* 1855 */           p1++;
/* 1856 */           p2 += 4;
/*      */         } 
/* 1858 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1863 */       double big = 0.0D;
/* 1864 */       int imax = -1;
/* 1865 */       for (k = j; k < 4; k++) {
/* 1866 */         int target = mtx + 4 * k + j;
/* 1867 */         double sum = matrix0[target];
/* 1868 */         int m = j;
/* 1869 */         int p1 = mtx + 4 * k;
/* 1870 */         int p2 = mtx + j;
/* 1871 */         while (m-- != 0) {
/* 1872 */           sum -= matrix0[p1] * matrix0[p2];
/* 1873 */           p1++;
/* 1874 */           p2 += 4;
/*      */         } 
/* 1876 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 1879 */         if ((temp = row_scale[k] * Math.abs(sum)) >= big) {
/* 1880 */           big = temp;
/* 1881 */           imax = k;
/*      */         } 
/*      */       } 
/*      */       
/* 1885 */       if (imax < 0) {
/* 1886 */         throw new RuntimeException(VecMathI18N.getString("Matrix4f13"));
/*      */       }
/*      */ 
/*      */       
/* 1890 */       if (j != imax) {
/*      */         
/* 1892 */         int m = 4;
/* 1893 */         int p1 = mtx + 4 * imax;
/* 1894 */         int p2 = mtx + 4 * j;
/* 1895 */         while (m-- != 0) {
/* 1896 */           double temp = matrix0[p1];
/* 1897 */           matrix0[p1++] = matrix0[p2];
/* 1898 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 1902 */         row_scale[imax] = row_scale[j];
/*      */       } 
/*      */ 
/*      */       
/* 1906 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 1909 */       if (matrix0[mtx + 4 * j + j] == 0.0D) {
/* 1910 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1914 */       if (j != 3) {
/* 1915 */         double temp = 1.0D / matrix0[mtx + 4 * j + j];
/* 1916 */         int target = mtx + 4 * (j + 1) + j;
/* 1917 */         k = 3 - j;
/* 1918 */         while (k-- != 0) {
/* 1919 */           matrix0[target] = matrix0[target] * temp;
/* 1920 */           target += 4;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1926 */     return true;
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
/* 1956 */     int rp = 0;
/*      */ 
/*      */     
/* 1959 */     for (int k = 0; k < 4; k++) {
/*      */       
/* 1961 */       int cv = k;
/* 1962 */       int ii = -1;
/*      */ 
/*      */       
/* 1965 */       for (int i = 0; i < 4; i++) {
/*      */ 
/*      */         
/* 1968 */         int ip = row_perm[rp + i];
/* 1969 */         double sum = matrix2[cv + 4 * ip];
/* 1970 */         matrix2[cv + 4 * ip] = matrix2[cv + 4 * i];
/* 1971 */         if (ii >= 0) {
/*      */           
/* 1973 */           int m = i * 4;
/* 1974 */           for (int j = ii; j <= i - 1; j++) {
/* 1975 */             sum -= matrix1[m + j] * matrix2[cv + 4 * j];
/*      */           }
/*      */         }
/* 1978 */         else if (sum != 0.0D) {
/* 1979 */           ii = i;
/*      */         } 
/* 1981 */         matrix2[cv + 4 * i] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1986 */       int rv = 12;
/* 1987 */       matrix2[cv + 12] = matrix2[cv + 12] / matrix1[rv + 3];
/*      */       
/* 1989 */       rv -= 4;
/* 1990 */       matrix2[cv + 8] = (matrix2[cv + 8] - 
/* 1991 */         matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 2];
/*      */       
/* 1993 */       rv -= 4;
/* 1994 */       matrix2[cv + 4] = (matrix2[cv + 4] - 
/* 1995 */         matrix1[rv + 2] * matrix2[cv + 8] - 
/* 1996 */         matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 1];
/*      */       
/* 1998 */       rv -= 4;
/* 1999 */       matrix2[cv + 0] = (matrix2[cv + 0] - 
/* 2000 */         matrix1[rv + 1] * matrix2[cv + 4] - 
/* 2001 */         matrix1[rv + 2] * matrix2[cv + 8] - 
/* 2002 */         matrix1[rv + 3] * matrix2[cv + 12]) / matrix1[rv + 0];
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
/*      */   public final float determinant() {
/* 2016 */     float det = this.m00 * (this.m11 * this.m22 * this.m33 + this.m12 * this.m23 * this.m31 + this.m13 * this.m21 * this.m32 - 
/* 2017 */       this.m13 * this.m22 * this.m31 - this.m11 * this.m23 * this.m32 - this.m12 * this.m21 * this.m33);
/* 2018 */     det -= this.m01 * (this.m10 * this.m22 * this.m33 + this.m12 * this.m23 * this.m30 + this.m13 * this.m20 * this.m32 - 
/* 2019 */       this.m13 * this.m22 * this.m30 - this.m10 * this.m23 * this.m32 - this.m12 * this.m20 * this.m33);
/* 2020 */     det += this.m02 * (this.m10 * this.m21 * this.m33 + this.m11 * this.m23 * this.m30 + this.m13 * this.m20 * this.m31 - 
/* 2021 */       this.m13 * this.m21 * this.m30 - this.m10 * this.m23 * this.m31 - this.m11 * this.m20 * this.m33);
/* 2022 */     det -= this.m03 * (this.m10 * this.m21 * this.m32 + this.m11 * this.m22 * this.m30 + this.m12 * this.m20 * this.m31 - 
/* 2023 */       this.m12 * this.m21 * this.m30 - this.m10 * this.m22 * this.m31 - this.m11 * this.m20 * this.m32);
/*      */     
/* 2025 */     return det;
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
/* 2037 */     this.m00 = m1.m00; this.m01 = m1.m01; this.m02 = m1.m02; this.m03 = 0.0F;
/* 2038 */     this.m10 = m1.m10; this.m11 = m1.m11; this.m12 = m1.m12; this.m13 = 0.0F;
/* 2039 */     this.m20 = m1.m20; this.m21 = m1.m21; this.m22 = m1.m22; this.m23 = 0.0F;
/* 2040 */     this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
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
/* 2052 */     this.m00 = (float)m1.m00; this.m01 = (float)m1.m01; this.m02 = (float)m1.m02; this.m03 = 0.0F;
/* 2053 */     this.m10 = (float)m1.m10; this.m11 = (float)m1.m11; this.m12 = (float)m1.m12; this.m13 = 0.0F;
/* 2054 */     this.m20 = (float)m1.m20; this.m21 = (float)m1.m21; this.m22 = (float)m1.m22; this.m23 = 0.0F;
/* 2055 */     this.m30 = 0.0F; this.m31 = 0.0F; this.m32 = 0.0F; this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(float scale) {
/* 2065 */     this.m00 = scale;
/* 2066 */     this.m01 = 0.0F;
/* 2067 */     this.m02 = 0.0F;
/* 2068 */     this.m03 = 0.0F;
/*      */     
/* 2070 */     this.m10 = 0.0F;
/* 2071 */     this.m11 = scale;
/* 2072 */     this.m12 = 0.0F;
/* 2073 */     this.m13 = 0.0F;
/*      */     
/* 2075 */     this.m20 = 0.0F;
/* 2076 */     this.m21 = 0.0F;
/* 2077 */     this.m22 = scale;
/* 2078 */     this.m23 = 0.0F;
/*      */     
/* 2080 */     this.m30 = 0.0F;
/* 2081 */     this.m31 = 0.0F;
/* 2082 */     this.m32 = 0.0F;
/* 2083 */     this.m33 = 1.0F;
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
/* 2094 */     this.m00 = m[0];
/* 2095 */     this.m01 = m[1];
/* 2096 */     this.m02 = m[2];
/* 2097 */     this.m03 = m[3];
/* 2098 */     this.m10 = m[4];
/* 2099 */     this.m11 = m[5];
/* 2100 */     this.m12 = m[6];
/* 2101 */     this.m13 = m[7];
/* 2102 */     this.m20 = m[8];
/* 2103 */     this.m21 = m[9];
/* 2104 */     this.m22 = m[10];
/* 2105 */     this.m23 = m[11];
/* 2106 */     this.m30 = m[12];
/* 2107 */     this.m31 = m[13];
/* 2108 */     this.m32 = m[14];
/* 2109 */     this.m33 = m[15];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Vector3f v1) {
/* 2119 */     this.m00 = 1.0F;
/* 2120 */     this.m01 = 0.0F;
/* 2121 */     this.m02 = 0.0F;
/* 2122 */     this.m03 = v1.x;
/*      */     
/* 2124 */     this.m10 = 0.0F;
/* 2125 */     this.m11 = 1.0F;
/* 2126 */     this.m12 = 0.0F;
/* 2127 */     this.m13 = v1.y;
/*      */     
/* 2129 */     this.m20 = 0.0F;
/* 2130 */     this.m21 = 0.0F;
/* 2131 */     this.m22 = 1.0F;
/* 2132 */     this.m23 = v1.z;
/*      */     
/* 2134 */     this.m30 = 0.0F;
/* 2135 */     this.m31 = 0.0F;
/* 2136 */     this.m32 = 0.0F;
/* 2137 */     this.m33 = 1.0F;
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
/*      */   public final void set(float scale, Vector3f t1) {
/* 2149 */     this.m00 = scale;
/* 2150 */     this.m01 = 0.0F;
/* 2151 */     this.m02 = 0.0F;
/* 2152 */     this.m03 = t1.x;
/*      */     
/* 2154 */     this.m10 = 0.0F;
/* 2155 */     this.m11 = scale;
/* 2156 */     this.m12 = 0.0F;
/* 2157 */     this.m13 = t1.y;
/*      */     
/* 2159 */     this.m20 = 0.0F;
/* 2160 */     this.m21 = 0.0F;
/* 2161 */     this.m22 = scale;
/* 2162 */     this.m23 = t1.z;
/*      */     
/* 2164 */     this.m30 = 0.0F;
/* 2165 */     this.m31 = 0.0F;
/* 2166 */     this.m32 = 0.0F;
/* 2167 */     this.m33 = 1.0F;
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
/*      */   public final void set(Vector3f t1, float scale) {
/* 2179 */     this.m00 = scale;
/* 2180 */     this.m01 = 0.0F;
/* 2181 */     this.m02 = 0.0F;
/* 2182 */     this.m03 = scale * t1.x;
/*      */     
/* 2184 */     this.m10 = 0.0F;
/* 2185 */     this.m11 = scale;
/* 2186 */     this.m12 = 0.0F;
/* 2187 */     this.m13 = scale * t1.y;
/*      */     
/* 2189 */     this.m20 = 0.0F;
/* 2190 */     this.m21 = 0.0F;
/* 2191 */     this.m22 = scale;
/* 2192 */     this.m23 = scale * t1.z;
/*      */     
/* 2194 */     this.m30 = 0.0F;
/* 2195 */     this.m31 = 0.0F;
/* 2196 */     this.m32 = 0.0F;
/* 2197 */     this.m33 = 1.0F;
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
/* 2210 */     this.m00 = m1.m00 * scale;
/* 2211 */     this.m01 = m1.m01 * scale;
/* 2212 */     this.m02 = m1.m02 * scale;
/* 2213 */     this.m03 = t1.x;
/*      */     
/* 2215 */     this.m10 = m1.m10 * scale;
/* 2216 */     this.m11 = m1.m11 * scale;
/* 2217 */     this.m12 = m1.m12 * scale;
/* 2218 */     this.m13 = t1.y;
/*      */     
/* 2220 */     this.m20 = m1.m20 * scale;
/* 2221 */     this.m21 = m1.m21 * scale;
/* 2222 */     this.m22 = m1.m22 * scale;
/* 2223 */     this.m23 = t1.z;
/*      */     
/* 2225 */     this.m30 = 0.0F;
/* 2226 */     this.m31 = 0.0F;
/* 2227 */     this.m32 = 0.0F;
/* 2228 */     this.m33 = 1.0F;
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
/*      */   public final void set(Matrix3d m1, Vector3d t1, double scale) {
/* 2241 */     this.m00 = (float)(m1.m00 * scale);
/* 2242 */     this.m01 = (float)(m1.m01 * scale);
/* 2243 */     this.m02 = (float)(m1.m02 * scale);
/* 2244 */     this.m03 = (float)t1.x;
/*      */     
/* 2246 */     this.m10 = (float)(m1.m10 * scale);
/* 2247 */     this.m11 = (float)(m1.m11 * scale);
/* 2248 */     this.m12 = (float)(m1.m12 * scale);
/* 2249 */     this.m13 = (float)t1.y;
/*      */     
/* 2251 */     this.m20 = (float)(m1.m20 * scale);
/* 2252 */     this.m21 = (float)(m1.m21 * scale);
/* 2253 */     this.m22 = (float)(m1.m22 * scale);
/* 2254 */     this.m23 = (float)t1.z;
/*      */     
/* 2256 */     this.m30 = 0.0F;
/* 2257 */     this.m31 = 0.0F;
/* 2258 */     this.m32 = 0.0F;
/* 2259 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setTranslation(Vector3f trans) {
/* 2270 */     this.m03 = trans.x;
/* 2271 */     this.m13 = trans.y;
/* 2272 */     this.m23 = trans.z;
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
/*      */   public final void rotX(float angle) {
/* 2285 */     float sinAngle = (float)Math.sin(angle);
/* 2286 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 2288 */     this.m00 = 1.0F;
/* 2289 */     this.m01 = 0.0F;
/* 2290 */     this.m02 = 0.0F;
/* 2291 */     this.m03 = 0.0F;
/*      */     
/* 2293 */     this.m10 = 0.0F;
/* 2294 */     this.m11 = cosAngle;
/* 2295 */     this.m12 = -sinAngle;
/* 2296 */     this.m13 = 0.0F;
/*      */     
/* 2298 */     this.m20 = 0.0F;
/* 2299 */     this.m21 = sinAngle;
/* 2300 */     this.m22 = cosAngle;
/* 2301 */     this.m23 = 0.0F;
/*      */     
/* 2303 */     this.m30 = 0.0F;
/* 2304 */     this.m31 = 0.0F;
/* 2305 */     this.m32 = 0.0F;
/* 2306 */     this.m33 = 1.0F;
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
/* 2318 */     float sinAngle = (float)Math.sin(angle);
/* 2319 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 2321 */     this.m00 = cosAngle;
/* 2322 */     this.m01 = 0.0F;
/* 2323 */     this.m02 = sinAngle;
/* 2324 */     this.m03 = 0.0F;
/*      */     
/* 2326 */     this.m10 = 0.0F;
/* 2327 */     this.m11 = 1.0F;
/* 2328 */     this.m12 = 0.0F;
/* 2329 */     this.m13 = 0.0F;
/*      */     
/* 2331 */     this.m20 = -sinAngle;
/* 2332 */     this.m21 = 0.0F;
/* 2333 */     this.m22 = cosAngle;
/* 2334 */     this.m23 = 0.0F;
/*      */     
/* 2336 */     this.m30 = 0.0F;
/* 2337 */     this.m31 = 0.0F;
/* 2338 */     this.m32 = 0.0F;
/* 2339 */     this.m33 = 1.0F;
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
/* 2351 */     float sinAngle = (float)Math.sin(angle);
/* 2352 */     float cosAngle = (float)Math.cos(angle);
/*      */     
/* 2354 */     this.m00 = cosAngle;
/* 2355 */     this.m01 = -sinAngle;
/* 2356 */     this.m02 = 0.0F;
/* 2357 */     this.m03 = 0.0F;
/*      */     
/* 2359 */     this.m10 = sinAngle;
/* 2360 */     this.m11 = cosAngle;
/* 2361 */     this.m12 = 0.0F;
/* 2362 */     this.m13 = 0.0F;
/*      */     
/* 2364 */     this.m20 = 0.0F;
/* 2365 */     this.m21 = 0.0F;
/* 2366 */     this.m22 = 1.0F;
/* 2367 */     this.m23 = 0.0F;
/*      */     
/* 2369 */     this.m30 = 0.0F;
/* 2370 */     this.m31 = 0.0F;
/* 2371 */     this.m32 = 0.0F;
/* 2372 */     this.m33 = 1.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float scalar) {
/* 2381 */     this.m00 *= scalar;
/* 2382 */     this.m01 *= scalar;
/* 2383 */     this.m02 *= scalar;
/* 2384 */     this.m03 *= scalar;
/* 2385 */     this.m10 *= scalar;
/* 2386 */     this.m11 *= scalar;
/* 2387 */     this.m12 *= scalar;
/* 2388 */     this.m13 *= scalar;
/* 2389 */     this.m20 *= scalar;
/* 2390 */     this.m21 *= scalar;
/* 2391 */     this.m22 *= scalar;
/* 2392 */     this.m23 *= scalar;
/* 2393 */     this.m30 *= scalar;
/* 2394 */     this.m31 *= scalar;
/* 2395 */     this.m32 *= scalar;
/* 2396 */     this.m33 *= scalar;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(float scalar, Matrix4f m1) {
/* 2407 */     m1.m00 *= scalar;
/* 2408 */     m1.m01 *= scalar;
/* 2409 */     m1.m02 *= scalar;
/* 2410 */     m1.m03 *= scalar;
/* 2411 */     m1.m10 *= scalar;
/* 2412 */     m1.m11 *= scalar;
/* 2413 */     m1.m12 *= scalar;
/* 2414 */     m1.m13 *= scalar;
/* 2415 */     m1.m20 *= scalar;
/* 2416 */     m1.m21 *= scalar;
/* 2417 */     m1.m22 *= scalar;
/* 2418 */     m1.m23 *= scalar;
/* 2419 */     m1.m30 *= scalar;
/* 2420 */     m1.m31 *= scalar;
/* 2421 */     m1.m32 *= scalar;
/* 2422 */     m1.m33 *= scalar;
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
/*      */   public final void mul(Matrix4f m1) {
/* 2437 */     float m00 = this.m00 * m1.m00 + this.m01 * m1.m10 + 
/* 2438 */       this.m02 * m1.m20 + this.m03 * m1.m30;
/* 2439 */     float m01 = this.m00 * m1.m01 + this.m01 * m1.m11 + 
/* 2440 */       this.m02 * m1.m21 + this.m03 * m1.m31;
/* 2441 */     float m02 = this.m00 * m1.m02 + this.m01 * m1.m12 + 
/* 2442 */       this.m02 * m1.m22 + this.m03 * m1.m32;
/* 2443 */     float m03 = this.m00 * m1.m03 + this.m01 * m1.m13 + 
/* 2444 */       this.m02 * m1.m23 + this.m03 * m1.m33;
/*      */     
/* 2446 */     float m10 = this.m10 * m1.m00 + this.m11 * m1.m10 + 
/* 2447 */       this.m12 * m1.m20 + this.m13 * m1.m30;
/* 2448 */     float m11 = this.m10 * m1.m01 + this.m11 * m1.m11 + 
/* 2449 */       this.m12 * m1.m21 + this.m13 * m1.m31;
/* 2450 */     float m12 = this.m10 * m1.m02 + this.m11 * m1.m12 + 
/* 2451 */       this.m12 * m1.m22 + this.m13 * m1.m32;
/* 2452 */     float m13 = this.m10 * m1.m03 + this.m11 * m1.m13 + 
/* 2453 */       this.m12 * m1.m23 + this.m13 * m1.m33;
/*      */     
/* 2455 */     float m20 = this.m20 * m1.m00 + this.m21 * m1.m10 + 
/* 2456 */       this.m22 * m1.m20 + this.m23 * m1.m30;
/* 2457 */     float m21 = this.m20 * m1.m01 + this.m21 * m1.m11 + 
/* 2458 */       this.m22 * m1.m21 + this.m23 * m1.m31;
/* 2459 */     float m22 = this.m20 * m1.m02 + this.m21 * m1.m12 + 
/* 2460 */       this.m22 * m1.m22 + this.m23 * m1.m32;
/* 2461 */     float m23 = this.m20 * m1.m03 + this.m21 * m1.m13 + 
/* 2462 */       this.m22 * m1.m23 + this.m23 * m1.m33;
/*      */     
/* 2464 */     float m30 = this.m30 * m1.m00 + this.m31 * m1.m10 + 
/* 2465 */       this.m32 * m1.m20 + this.m33 * m1.m30;
/* 2466 */     float m31 = this.m30 * m1.m01 + this.m31 * m1.m11 + 
/* 2467 */       this.m32 * m1.m21 + this.m33 * m1.m31;
/* 2468 */     float m32 = this.m30 * m1.m02 + this.m31 * m1.m12 + 
/* 2469 */       this.m32 * m1.m22 + this.m33 * m1.m32;
/* 2470 */     float m33 = this.m30 * m1.m03 + this.m31 * m1.m13 + 
/* 2471 */       this.m32 * m1.m23 + this.m33 * m1.m33;
/*      */     
/* 2473 */     this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2474 */     this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2475 */     this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2476 */     this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void mul(Matrix4f m1, Matrix4f m2) {
/* 2487 */     if (this != m1 && this != m2) {
/*      */       
/* 2489 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + 
/* 2490 */         m1.m02 * m2.m20 + m1.m03 * m2.m30;
/* 2491 */       this.m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + 
/* 2492 */         m1.m02 * m2.m21 + m1.m03 * m2.m31;
/* 2493 */       this.m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + 
/* 2494 */         m1.m02 * m2.m22 + m1.m03 * m2.m32;
/* 2495 */       this.m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + 
/* 2496 */         m1.m02 * m2.m23 + m1.m03 * m2.m33;
/*      */       
/* 2498 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + 
/* 2499 */         m1.m12 * m2.m20 + m1.m13 * m2.m30;
/* 2500 */       this.m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + 
/* 2501 */         m1.m12 * m2.m21 + m1.m13 * m2.m31;
/* 2502 */       this.m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + 
/* 2503 */         m1.m12 * m2.m22 + m1.m13 * m2.m32;
/* 2504 */       this.m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + 
/* 2505 */         m1.m12 * m2.m23 + m1.m13 * m2.m33;
/*      */       
/* 2507 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + 
/* 2508 */         m1.m22 * m2.m20 + m1.m23 * m2.m30;
/* 2509 */       this.m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + 
/* 2510 */         m1.m22 * m2.m21 + m1.m23 * m2.m31;
/* 2511 */       this.m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + 
/* 2512 */         m1.m22 * m2.m22 + m1.m23 * m2.m32;
/* 2513 */       this.m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + 
/* 2514 */         m1.m22 * m2.m23 + m1.m23 * m2.m33;
/*      */       
/* 2516 */       this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + 
/* 2517 */         m1.m32 * m2.m20 + m1.m33 * m2.m30;
/* 2518 */       this.m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + 
/* 2519 */         m1.m32 * m2.m21 + m1.m33 * m2.m31;
/* 2520 */       this.m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + 
/* 2521 */         m1.m32 * m2.m22 + m1.m33 * m2.m32;
/* 2522 */       this.m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + 
/* 2523 */         m1.m32 * m2.m23 + m1.m33 * m2.m33;
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2529 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m10 + m1.m02 * m2.m20 + m1.m03 * m2.m30;
/* 2530 */       float m01 = m1.m00 * m2.m01 + m1.m01 * m2.m11 + m1.m02 * m2.m21 + m1.m03 * m2.m31;
/* 2531 */       float m02 = m1.m00 * m2.m02 + m1.m01 * m2.m12 + m1.m02 * m2.m22 + m1.m03 * m2.m32;
/* 2532 */       float m03 = m1.m00 * m2.m03 + m1.m01 * m2.m13 + m1.m02 * m2.m23 + m1.m03 * m2.m33;
/*      */       
/* 2534 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m10 + m1.m12 * m2.m20 + m1.m13 * m2.m30;
/* 2535 */       float m11 = m1.m10 * m2.m01 + m1.m11 * m2.m11 + m1.m12 * m2.m21 + m1.m13 * m2.m31;
/* 2536 */       float m12 = m1.m10 * m2.m02 + m1.m11 * m2.m12 + m1.m12 * m2.m22 + m1.m13 * m2.m32;
/* 2537 */       float m13 = m1.m10 * m2.m03 + m1.m11 * m2.m13 + m1.m12 * m2.m23 + m1.m13 * m2.m33;
/*      */       
/* 2539 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m10 + m1.m22 * m2.m20 + m1.m23 * m2.m30;
/* 2540 */       float m21 = m1.m20 * m2.m01 + m1.m21 * m2.m11 + m1.m22 * m2.m21 + m1.m23 * m2.m31;
/* 2541 */       float m22 = m1.m20 * m2.m02 + m1.m21 * m2.m12 + m1.m22 * m2.m22 + m1.m23 * m2.m32;
/* 2542 */       float m23 = m1.m20 * m2.m03 + m1.m21 * m2.m13 + m1.m22 * m2.m23 + m1.m23 * m2.m33;
/*      */       
/* 2544 */       float m30 = m1.m30 * m2.m00 + m1.m31 * m2.m10 + m1.m32 * m2.m20 + m1.m33 * m2.m30;
/* 2545 */       float m31 = m1.m30 * m2.m01 + m1.m31 * m2.m11 + m1.m32 * m2.m21 + m1.m33 * m2.m31;
/* 2546 */       float m32 = m1.m30 * m2.m02 + m1.m31 * m2.m12 + m1.m32 * m2.m22 + m1.m33 * m2.m32;
/* 2547 */       float m33 = m1.m30 * m2.m03 + m1.m31 * m2.m13 + m1.m32 * m2.m23 + m1.m33 * m2.m33;
/*      */       
/* 2549 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2550 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2551 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2552 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/*      */   public final void mulTransposeBoth(Matrix4f m1, Matrix4f m2) {
/* 2564 */     if (this != m1 && this != m2) {
/* 2565 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
/* 2566 */       this.m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
/* 2567 */       this.m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
/* 2568 */       this.m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
/*      */       
/* 2570 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
/* 2571 */       this.m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
/* 2572 */       this.m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
/* 2573 */       this.m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
/*      */       
/* 2575 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
/* 2576 */       this.m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
/* 2577 */       this.m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
/* 2578 */       this.m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
/*      */       
/* 2580 */       this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
/* 2581 */       this.m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
/* 2582 */       this.m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
/* 2583 */       this.m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2590 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m01 + m1.m20 * m2.m02 + m1.m30 * m2.m03;
/* 2591 */       float m01 = m1.m00 * m2.m10 + m1.m10 * m2.m11 + m1.m20 * m2.m12 + m1.m30 * m2.m13;
/* 2592 */       float m02 = m1.m00 * m2.m20 + m1.m10 * m2.m21 + m1.m20 * m2.m22 + m1.m30 * m2.m23;
/* 2593 */       float m03 = m1.m00 * m2.m30 + m1.m10 * m2.m31 + m1.m20 * m2.m32 + m1.m30 * m2.m33;
/*      */       
/* 2595 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m01 + m1.m21 * m2.m02 + m1.m31 * m2.m03;
/* 2596 */       float m11 = m1.m01 * m2.m10 + m1.m11 * m2.m11 + m1.m21 * m2.m12 + m1.m31 * m2.m13;
/* 2597 */       float m12 = m1.m01 * m2.m20 + m1.m11 * m2.m21 + m1.m21 * m2.m22 + m1.m31 * m2.m23;
/* 2598 */       float m13 = m1.m01 * m2.m30 + m1.m11 * m2.m31 + m1.m21 * m2.m32 + m1.m31 * m2.m33;
/*      */       
/* 2600 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m01 + m1.m22 * m2.m02 + m1.m32 * m2.m03;
/* 2601 */       float m21 = m1.m02 * m2.m10 + m1.m12 * m2.m11 + m1.m22 * m2.m12 + m1.m32 * m2.m13;
/* 2602 */       float m22 = m1.m02 * m2.m20 + m1.m12 * m2.m21 + m1.m22 * m2.m22 + m1.m32 * m2.m23;
/* 2603 */       float m23 = m1.m02 * m2.m30 + m1.m12 * m2.m31 + m1.m22 * m2.m32 + m1.m32 * m2.m33;
/*      */       
/* 2605 */       float m30 = m1.m03 * m2.m00 + m1.m13 * m2.m01 + m1.m23 * m2.m02 + m1.m33 * m2.m03;
/* 2606 */       float m31 = m1.m03 * m2.m10 + m1.m13 * m2.m11 + m1.m23 * m2.m12 + m1.m33 * m2.m13;
/* 2607 */       float m32 = m1.m03 * m2.m20 + m1.m13 * m2.m21 + m1.m23 * m2.m22 + m1.m33 * m2.m23;
/* 2608 */       float m33 = m1.m03 * m2.m30 + m1.m13 * m2.m31 + m1.m23 * m2.m32 + m1.m33 * m2.m33;
/*      */       
/* 2610 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2611 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2612 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2613 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/*      */   public final void mulTransposeRight(Matrix4f m1, Matrix4f m2) {
/* 2626 */     if (this != m1 && this != m2) {
/* 2627 */       this.m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
/* 2628 */       this.m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
/* 2629 */       this.m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
/* 2630 */       this.m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
/*      */       
/* 2632 */       this.m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
/* 2633 */       this.m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
/* 2634 */       this.m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
/* 2635 */       this.m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
/*      */       
/* 2637 */       this.m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
/* 2638 */       this.m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
/* 2639 */       this.m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
/* 2640 */       this.m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
/*      */       
/* 2642 */       this.m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
/* 2643 */       this.m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
/* 2644 */       this.m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
/* 2645 */       this.m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */       
/* 2652 */       float m00 = m1.m00 * m2.m00 + m1.m01 * m2.m01 + m1.m02 * m2.m02 + m1.m03 * m2.m03;
/* 2653 */       float m01 = m1.m00 * m2.m10 + m1.m01 * m2.m11 + m1.m02 * m2.m12 + m1.m03 * m2.m13;
/* 2654 */       float m02 = m1.m00 * m2.m20 + m1.m01 * m2.m21 + m1.m02 * m2.m22 + m1.m03 * m2.m23;
/* 2655 */       float m03 = m1.m00 * m2.m30 + m1.m01 * m2.m31 + m1.m02 * m2.m32 + m1.m03 * m2.m33;
/*      */       
/* 2657 */       float m10 = m1.m10 * m2.m00 + m1.m11 * m2.m01 + m1.m12 * m2.m02 + m1.m13 * m2.m03;
/* 2658 */       float m11 = m1.m10 * m2.m10 + m1.m11 * m2.m11 + m1.m12 * m2.m12 + m1.m13 * m2.m13;
/* 2659 */       float m12 = m1.m10 * m2.m20 + m1.m11 * m2.m21 + m1.m12 * m2.m22 + m1.m13 * m2.m23;
/* 2660 */       float m13 = m1.m10 * m2.m30 + m1.m11 * m2.m31 + m1.m12 * m2.m32 + m1.m13 * m2.m33;
/*      */       
/* 2662 */       float m20 = m1.m20 * m2.m00 + m1.m21 * m2.m01 + m1.m22 * m2.m02 + m1.m23 * m2.m03;
/* 2663 */       float m21 = m1.m20 * m2.m10 + m1.m21 * m2.m11 + m1.m22 * m2.m12 + m1.m23 * m2.m13;
/* 2664 */       float m22 = m1.m20 * m2.m20 + m1.m21 * m2.m21 + m1.m22 * m2.m22 + m1.m23 * m2.m23;
/* 2665 */       float m23 = m1.m20 * m2.m30 + m1.m21 * m2.m31 + m1.m22 * m2.m32 + m1.m23 * m2.m33;
/*      */       
/* 2667 */       float m30 = m1.m30 * m2.m00 + m1.m31 * m2.m01 + m1.m32 * m2.m02 + m1.m33 * m2.m03;
/* 2668 */       float m31 = m1.m30 * m2.m10 + m1.m31 * m2.m11 + m1.m32 * m2.m12 + m1.m33 * m2.m13;
/* 2669 */       float m32 = m1.m30 * m2.m20 + m1.m31 * m2.m21 + m1.m32 * m2.m22 + m1.m33 * m2.m23;
/* 2670 */       float m33 = m1.m30 * m2.m30 + m1.m31 * m2.m31 + m1.m32 * m2.m32 + m1.m33 * m2.m33;
/*      */       
/* 2672 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2673 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2674 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2675 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/*      */   public final void mulTransposeLeft(Matrix4f m1, Matrix4f m2) {
/* 2689 */     if (this != m1 && this != m2) {
/* 2690 */       this.m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
/* 2691 */       this.m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
/* 2692 */       this.m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
/* 2693 */       this.m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
/*      */       
/* 2695 */       this.m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
/* 2696 */       this.m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
/* 2697 */       this.m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
/* 2698 */       this.m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
/*      */       
/* 2700 */       this.m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
/* 2701 */       this.m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
/* 2702 */       this.m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
/* 2703 */       this.m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
/*      */       
/* 2705 */       this.m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
/* 2706 */       this.m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
/* 2707 */       this.m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
/* 2708 */       this.m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 2717 */       float m00 = m1.m00 * m2.m00 + m1.m10 * m2.m10 + m1.m20 * m2.m20 + m1.m30 * m2.m30;
/* 2718 */       float m01 = m1.m00 * m2.m01 + m1.m10 * m2.m11 + m1.m20 * m2.m21 + m1.m30 * m2.m31;
/* 2719 */       float m02 = m1.m00 * m2.m02 + m1.m10 * m2.m12 + m1.m20 * m2.m22 + m1.m30 * m2.m32;
/* 2720 */       float m03 = m1.m00 * m2.m03 + m1.m10 * m2.m13 + m1.m20 * m2.m23 + m1.m30 * m2.m33;
/*      */       
/* 2722 */       float m10 = m1.m01 * m2.m00 + m1.m11 * m2.m10 + m1.m21 * m2.m20 + m1.m31 * m2.m30;
/* 2723 */       float m11 = m1.m01 * m2.m01 + m1.m11 * m2.m11 + m1.m21 * m2.m21 + m1.m31 * m2.m31;
/* 2724 */       float m12 = m1.m01 * m2.m02 + m1.m11 * m2.m12 + m1.m21 * m2.m22 + m1.m31 * m2.m32;
/* 2725 */       float m13 = m1.m01 * m2.m03 + m1.m11 * m2.m13 + m1.m21 * m2.m23 + m1.m31 * m2.m33;
/*      */       
/* 2727 */       float m20 = m1.m02 * m2.m00 + m1.m12 * m2.m10 + m1.m22 * m2.m20 + m1.m32 * m2.m30;
/* 2728 */       float m21 = m1.m02 * m2.m01 + m1.m12 * m2.m11 + m1.m22 * m2.m21 + m1.m32 * m2.m31;
/* 2729 */       float m22 = m1.m02 * m2.m02 + m1.m12 * m2.m12 + m1.m22 * m2.m22 + m1.m32 * m2.m32;
/* 2730 */       float m23 = m1.m02 * m2.m03 + m1.m12 * m2.m13 + m1.m22 * m2.m23 + m1.m32 * m2.m33;
/*      */       
/* 2732 */       float m30 = m1.m03 * m2.m00 + m1.m13 * m2.m10 + m1.m23 * m2.m20 + m1.m33 * m2.m30;
/* 2733 */       float m31 = m1.m03 * m2.m01 + m1.m13 * m2.m11 + m1.m23 * m2.m21 + m1.m33 * m2.m31;
/* 2734 */       float m32 = m1.m03 * m2.m02 + m1.m13 * m2.m12 + m1.m23 * m2.m22 + m1.m33 * m2.m32;
/* 2735 */       float m33 = m1.m03 * m2.m03 + m1.m13 * m2.m13 + m1.m23 * m2.m23 + m1.m33 * m2.m33;
/*      */       
/* 2737 */       this.m00 = m00; this.m01 = m01; this.m02 = m02; this.m03 = m03;
/* 2738 */       this.m10 = m10; this.m11 = m11; this.m12 = m12; this.m13 = m13;
/* 2739 */       this.m20 = m20; this.m21 = m21; this.m22 = m22; this.m23 = m23;
/* 2740 */       this.m30 = m30; this.m31 = m31; this.m32 = m32; this.m33 = m33;
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
/*      */   public boolean equals(Matrix4f m1) {
/*      */     try {
/* 2755 */       return (this.m00 == m1.m00 && this.m01 == m1.m01 && this.m02 == m1.m02 && 
/* 2756 */         this.m03 == m1.m03 && this.m10 == m1.m10 && this.m11 == m1.m11 && 
/* 2757 */         this.m12 == m1.m12 && this.m13 == m1.m13 && this.m20 == m1.m20 && 
/* 2758 */         this.m21 == m1.m21 && this.m22 == m1.m22 && this.m23 == m1.m23 && 
/* 2759 */         this.m30 == m1.m30 && this.m31 == m1.m31 && this.m32 == m1.m32 && 
/* 2760 */         this.m33 == m1.m33);
/*      */     } catch (NullPointerException e2) {
/* 2762 */       return false;
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
/* 2777 */     try { Matrix4f m2 = (Matrix4f)t1;
/* 2778 */       return (this.m00 == m2.m00 && this.m01 == m2.m01 && this.m02 == m2.m02 && 
/* 2779 */         this.m03 == m2.m03 && this.m10 == m2.m10 && this.m11 == m2.m11 && 
/* 2780 */         this.m12 == m2.m12 && this.m13 == m2.m13 && this.m20 == m2.m20 && 
/* 2781 */         this.m21 == m2.m21 && this.m22 == m2.m22 && this.m23 == m2.m23 && 
/* 2782 */         this.m30 == m2.m30 && this.m31 == m2.m31 && this.m32 == m2.m32 && 
/* 2783 */         this.m33 == m2.m33); }
/*      */     catch (ClassCastException e1)
/* 2785 */     { return false; }
/* 2786 */     catch (NullPointerException e2) { return false; }
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
/*      */   
/*      */   public boolean epsilonEquals(Matrix4f m1, float epsilon) {
/* 2801 */     boolean status = true;
/*      */     
/* 2803 */     if (Math.abs(this.m00 - m1.m00) > epsilon) status = false; 
/* 2804 */     if (Math.abs(this.m01 - m1.m01) > epsilon) status = false; 
/* 2805 */     if (Math.abs(this.m02 - m1.m02) > epsilon) status = false; 
/* 2806 */     if (Math.abs(this.m03 - m1.m03) > epsilon) status = false;
/*      */     
/* 2808 */     if (Math.abs(this.m10 - m1.m10) > epsilon) status = false; 
/* 2809 */     if (Math.abs(this.m11 - m1.m11) > epsilon) status = false; 
/* 2810 */     if (Math.abs(this.m12 - m1.m12) > epsilon) status = false; 
/* 2811 */     if (Math.abs(this.m13 - m1.m13) > epsilon) status = false;
/*      */     
/* 2813 */     if (Math.abs(this.m20 - m1.m20) > epsilon) status = false; 
/* 2814 */     if (Math.abs(this.m21 - m1.m21) > epsilon) status = false; 
/* 2815 */     if (Math.abs(this.m22 - m1.m22) > epsilon) status = false; 
/* 2816 */     if (Math.abs(this.m23 - m1.m23) > epsilon) status = false;
/*      */     
/* 2818 */     if (Math.abs(this.m30 - m1.m30) > epsilon) status = false; 
/* 2819 */     if (Math.abs(this.m31 - m1.m31) > epsilon) status = false; 
/* 2820 */     if (Math.abs(this.m32 - m1.m32) > epsilon) status = false; 
/* 2821 */     if (Math.abs(this.m33 - m1.m33) > epsilon) status = false;
/*      */     
/* 2823 */     return status;
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
/* 2838 */     long bits = 1L;
/* 2839 */     bits = VecMathUtil.hashFloatBits(bits, this.m00);
/* 2840 */     bits = VecMathUtil.hashFloatBits(bits, this.m01);
/* 2841 */     bits = VecMathUtil.hashFloatBits(bits, this.m02);
/* 2842 */     bits = VecMathUtil.hashFloatBits(bits, this.m03);
/* 2843 */     bits = VecMathUtil.hashFloatBits(bits, this.m10);
/* 2844 */     bits = VecMathUtil.hashFloatBits(bits, this.m11);
/* 2845 */     bits = VecMathUtil.hashFloatBits(bits, this.m12);
/* 2846 */     bits = VecMathUtil.hashFloatBits(bits, this.m13);
/* 2847 */     bits = VecMathUtil.hashFloatBits(bits, this.m20);
/* 2848 */     bits = VecMathUtil.hashFloatBits(bits, this.m21);
/* 2849 */     bits = VecMathUtil.hashFloatBits(bits, this.m22);
/* 2850 */     bits = VecMathUtil.hashFloatBits(bits, this.m23);
/* 2851 */     bits = VecMathUtil.hashFloatBits(bits, this.m30);
/* 2852 */     bits = VecMathUtil.hashFloatBits(bits, this.m31);
/* 2853 */     bits = VecMathUtil.hashFloatBits(bits, this.m32);
/* 2854 */     bits = VecMathUtil.hashFloatBits(bits, this.m33);
/* 2855 */     return VecMathUtil.hashFinish(bits);
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
/*      */   public final void transform(Tuple4f vec, Tuple4f vecOut) {
/* 2868 */     float x = this.m00 * vec.x + this.m01 * vec.y + 
/* 2869 */       this.m02 * vec.z + this.m03 * vec.w;
/* 2870 */     float y = this.m10 * vec.x + this.m11 * vec.y + 
/* 2871 */       this.m12 * vec.z + this.m13 * vec.w;
/* 2872 */     float z = this.m20 * vec.x + this.m21 * vec.y + 
/* 2873 */       this.m22 * vec.z + this.m23 * vec.w;
/* 2874 */     vecOut.w = this.m30 * vec.x + this.m31 * vec.y + 
/* 2875 */       this.m32 * vec.z + this.m33 * vec.w;
/* 2876 */     vecOut.x = x;
/* 2877 */     vecOut.y = y;
/* 2878 */     vecOut.z = z;
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
/*      */   public final void transform(Tuple4f vec) {
/* 2891 */     float x = this.m00 * vec.x + this.m01 * vec.y + 
/* 2892 */       this.m02 * vec.z + this.m03 * vec.w;
/* 2893 */     float y = this.m10 * vec.x + this.m11 * vec.y + 
/* 2894 */       this.m12 * vec.z + this.m13 * vec.w;
/* 2895 */     float z = this.m20 * vec.x + this.m21 * vec.y + 
/* 2896 */       this.m22 * vec.z + this.m23 * vec.w;
/* 2897 */     vec.w = this.m30 * vec.x + this.m31 * vec.y + 
/* 2898 */       this.m32 * vec.z + this.m33 * vec.w;
/* 2899 */     vec.x = x;
/* 2900 */     vec.y = y;
/* 2901 */     vec.z = z;
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
/*      */   public final void transform(Point3f point, Point3f pointOut) {
/* 2914 */     float x = this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03;
/* 2915 */     float y = this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13;
/* 2916 */     pointOut.z = this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23;
/* 2917 */     pointOut.x = x;
/* 2918 */     pointOut.y = y;
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
/* 2931 */     float x = this.m00 * point.x + this.m01 * point.y + this.m02 * point.z + this.m03;
/* 2932 */     float y = this.m10 * point.x + this.m11 * point.y + this.m12 * point.z + this.m13;
/* 2933 */     point.z = this.m20 * point.x + this.m21 * point.y + this.m22 * point.z + this.m23;
/* 2934 */     point.x = x;
/* 2935 */     point.y = y;
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
/* 2948 */     float x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
/* 2949 */     float y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
/* 2950 */     normalOut.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
/* 2951 */     normalOut.x = x;
/* 2952 */     normalOut.y = y;
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
/* 2965 */     float x = this.m00 * normal.x + this.m01 * normal.y + this.m02 * normal.z;
/* 2966 */     float y = this.m10 * normal.x + this.m11 * normal.y + this.m12 * normal.z;
/* 2967 */     normal.z = this.m20 * normal.x + this.m21 * normal.y + this.m22 * normal.z;
/* 2968 */     normal.x = x;
/* 2969 */     normal.y = y;
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
/*      */   public final void setRotation(Matrix3d m1) {
/* 2985 */     double[] tmp_rot = new double[9];
/* 2986 */     double[] tmp_scale = new double[3];
/*      */     
/* 2988 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 2990 */     this.m00 = (float)(m1.m00 * tmp_scale[0]);
/* 2991 */     this.m01 = (float)(m1.m01 * tmp_scale[1]);
/* 2992 */     this.m02 = (float)(m1.m02 * tmp_scale[2]);
/*      */     
/* 2994 */     this.m10 = (float)(m1.m10 * tmp_scale[0]);
/* 2995 */     this.m11 = (float)(m1.m11 * tmp_scale[1]);
/* 2996 */     this.m12 = (float)(m1.m12 * tmp_scale[2]);
/*      */     
/* 2998 */     this.m20 = (float)(m1.m20 * tmp_scale[0]);
/* 2999 */     this.m21 = (float)(m1.m21 * tmp_scale[1]);
/* 3000 */     this.m22 = (float)(m1.m22 * tmp_scale[2]);
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
/*      */   public final void setRotation(Matrix3f m1) {
/* 3015 */     double[] tmp_rot = new double[9];
/* 3016 */     double[] tmp_scale = new double[3];
/*      */     
/* 3018 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3020 */     this.m00 = (float)(m1.m00 * tmp_scale[0]);
/* 3021 */     this.m01 = (float)(m1.m01 * tmp_scale[1]);
/* 3022 */     this.m02 = (float)(m1.m02 * tmp_scale[2]);
/*      */     
/* 3024 */     this.m10 = (float)(m1.m10 * tmp_scale[0]);
/* 3025 */     this.m11 = (float)(m1.m11 * tmp_scale[1]);
/* 3026 */     this.m12 = (float)(m1.m12 * tmp_scale[2]);
/*      */     
/* 3028 */     this.m20 = (float)(m1.m20 * tmp_scale[0]);
/* 3029 */     this.m21 = (float)(m1.m21 * tmp_scale[1]);
/* 3030 */     this.m22 = (float)(m1.m22 * tmp_scale[2]);
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
/* 3044 */     double[] tmp_rot = new double[9];
/* 3045 */     double[] tmp_scale = new double[3];
/* 3046 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3048 */     this.m00 = (float)((1.0F - 2.0F * q1.y * q1.y - 2.0F * q1.z * q1.z) * tmp_scale[0]);
/* 3049 */     this.m10 = (float)((2.0F * (q1.x * q1.y + q1.w * q1.z)) * tmp_scale[0]);
/* 3050 */     this.m20 = (float)((2.0F * (q1.x * q1.z - q1.w * q1.y)) * tmp_scale[0]);
/*      */     
/* 3052 */     this.m01 = (float)((2.0F * (q1.x * q1.y - q1.w * q1.z)) * tmp_scale[1]);
/* 3053 */     this.m11 = (float)((1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.z * q1.z) * tmp_scale[1]);
/* 3054 */     this.m21 = (float)((2.0F * (q1.y * q1.z + q1.w * q1.x)) * tmp_scale[1]);
/*      */     
/* 3056 */     this.m02 = (float)((2.0F * (q1.x * q1.z + q1.w * q1.y)) * tmp_scale[2]);
/* 3057 */     this.m12 = (float)((2.0F * (q1.y * q1.z - q1.w * q1.x)) * tmp_scale[2]);
/* 3058 */     this.m22 = (float)((1.0F - 2.0F * q1.x * q1.x - 2.0F * q1.y * q1.y) * tmp_scale[2]);
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
/*      */   public final void setRotation(Quat4d q1) {
/* 3074 */     double[] tmp_rot = new double[9];
/* 3075 */     double[] tmp_scale = new double[3];
/*      */     
/* 3077 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3079 */     this.m00 = (float)((1.0D - 2.0D * q1.y * q1.y - 2.0D * q1.z * q1.z) * tmp_scale[0]);
/* 3080 */     this.m10 = (float)(2.0D * (q1.x * q1.y + q1.w * q1.z) * tmp_scale[0]);
/* 3081 */     this.m20 = (float)(2.0D * (q1.x * q1.z - q1.w * q1.y) * tmp_scale[0]);
/*      */     
/* 3083 */     this.m01 = (float)(2.0D * (q1.x * q1.y - q1.w * q1.z) * tmp_scale[1]);
/* 3084 */     this.m11 = (float)((1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.z * q1.z) * tmp_scale[1]);
/* 3085 */     this.m21 = (float)(2.0D * (q1.y * q1.z + q1.w * q1.x) * tmp_scale[1]);
/*      */     
/* 3087 */     this.m02 = (float)(2.0D * (q1.x * q1.z + q1.w * q1.y) * tmp_scale[2]);
/* 3088 */     this.m12 = (float)(2.0D * (q1.y * q1.z - q1.w * q1.x) * tmp_scale[2]);
/* 3089 */     this.m22 = (float)((1.0D - 2.0D * q1.x * q1.x - 2.0D * q1.y * q1.y) * tmp_scale[2]);
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
/*      */   public final void setRotation(AxisAngle4f a1) {
/* 3103 */     double[] tmp_rot = new double[9];
/* 3104 */     double[] tmp_scale = new double[3];
/*      */     
/* 3106 */     getScaleRotate(tmp_scale, tmp_rot);
/*      */     
/* 3108 */     double mag = Math.sqrt((a1.x * a1.x + a1.y * a1.y + a1.z * a1.z));
/* 3109 */     if (mag < 1.0E-8D) {
/* 3110 */       this.m00 = 1.0F;
/* 3111 */       this.m01 = 0.0F;
/* 3112 */       this.m02 = 0.0F;
/*      */       
/* 3114 */       this.m10 = 0.0F;
/* 3115 */       this.m11 = 1.0F;
/* 3116 */       this.m12 = 0.0F;
/*      */       
/* 3118 */       this.m20 = 0.0F;
/* 3119 */       this.m21 = 0.0F;
/* 3120 */       this.m22 = 1.0F;
/*      */     } else {
/* 3122 */       mag = 1.0D / mag;
/* 3123 */       double ax = a1.x * mag;
/* 3124 */       double ay = a1.y * mag;
/* 3125 */       double az = a1.z * mag;
/*      */       
/* 3127 */       double sinTheta = Math.sin(a1.angle);
/* 3128 */       double cosTheta = Math.cos(a1.angle);
/* 3129 */       double t = 1.0D - cosTheta;
/*      */       
/* 3131 */       double xz = (a1.x * a1.z);
/* 3132 */       double xy = (a1.x * a1.y);
/* 3133 */       double yz = (a1.y * a1.z);
/*      */       
/* 3135 */       this.m00 = (float)((t * ax * ax + cosTheta) * tmp_scale[0]);
/* 3136 */       this.m01 = (float)((t * xy - sinTheta * az) * tmp_scale[1]);
/* 3137 */       this.m02 = (float)((t * xz + sinTheta * ay) * tmp_scale[2]);
/*      */       
/* 3139 */       this.m10 = (float)((t * xy + sinTheta * az) * tmp_scale[0]);
/* 3140 */       this.m11 = (float)((t * ay * ay + cosTheta) * tmp_scale[1]);
/* 3141 */       this.m12 = (float)((t * yz - sinTheta * ax) * tmp_scale[2]);
/*      */       
/* 3143 */       this.m20 = (float)((t * xz - sinTheta * ay) * tmp_scale[0]);
/* 3144 */       this.m21 = (float)((t * yz + sinTheta * ax) * tmp_scale[1]);
/* 3145 */       this.m22 = (float)((t * az * az + cosTheta) * tmp_scale[2]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/* 3156 */     this.m00 = 0.0F;
/* 3157 */     this.m01 = 0.0F;
/* 3158 */     this.m02 = 0.0F;
/* 3159 */     this.m03 = 0.0F;
/* 3160 */     this.m10 = 0.0F;
/* 3161 */     this.m11 = 0.0F;
/* 3162 */     this.m12 = 0.0F;
/* 3163 */     this.m13 = 0.0F;
/* 3164 */     this.m20 = 0.0F;
/* 3165 */     this.m21 = 0.0F;
/* 3166 */     this.m22 = 0.0F;
/* 3167 */     this.m23 = 0.0F;
/* 3168 */     this.m30 = 0.0F;
/* 3169 */     this.m31 = 0.0F;
/* 3170 */     this.m32 = 0.0F;
/* 3171 */     this.m33 = 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/* 3179 */     this.m00 = -this.m00;
/* 3180 */     this.m01 = -this.m01;
/* 3181 */     this.m02 = -this.m02;
/* 3182 */     this.m03 = -this.m03;
/* 3183 */     this.m10 = -this.m10;
/* 3184 */     this.m11 = -this.m11;
/* 3185 */     this.m12 = -this.m12;
/* 3186 */     this.m13 = -this.m13;
/* 3187 */     this.m20 = -this.m20;
/* 3188 */     this.m21 = -this.m21;
/* 3189 */     this.m22 = -this.m22;
/* 3190 */     this.m23 = -this.m23;
/* 3191 */     this.m30 = -this.m30;
/* 3192 */     this.m31 = -this.m31;
/* 3193 */     this.m32 = -this.m32;
/* 3194 */     this.m33 = -this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate(Matrix4f m1) {
/* 3204 */     this.m00 = -m1.m00;
/* 3205 */     this.m01 = -m1.m01;
/* 3206 */     this.m02 = -m1.m02;
/* 3207 */     this.m03 = -m1.m03;
/* 3208 */     this.m10 = -m1.m10;
/* 3209 */     this.m11 = -m1.m11;
/* 3210 */     this.m12 = -m1.m12;
/* 3211 */     this.m13 = -m1.m13;
/* 3212 */     this.m20 = -m1.m20;
/* 3213 */     this.m21 = -m1.m21;
/* 3214 */     this.m22 = -m1.m22;
/* 3215 */     this.m23 = -m1.m23;
/* 3216 */     this.m30 = -m1.m30;
/* 3217 */     this.m31 = -m1.m31;
/* 3218 */     this.m32 = -m1.m32;
/* 3219 */     this.m33 = -m1.m33;
/*      */   }
/*      */   
/*      */   private final void getScaleRotate(double[] scales, double[] rots) {
/* 3223 */     double[] tmp = new double[9];
/* 3224 */     tmp[0] = this.m00;
/* 3225 */     tmp[1] = this.m01;
/* 3226 */     tmp[2] = this.m02;
/*      */     
/* 3228 */     tmp[3] = this.m10;
/* 3229 */     tmp[4] = this.m11;
/* 3230 */     tmp[5] = this.m12;
/*      */     
/* 3232 */     tmp[6] = this.m20;
/* 3233 */     tmp[7] = this.m21;
/* 3234 */     tmp[8] = this.m22;
/*      */     
/* 3236 */     Matrix3d.compute_svd(tmp, scales, rots);
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
/* 3251 */     Matrix4f m1 = null;
/*      */     try {
/* 3253 */       m1 = (Matrix4f)super.clone();
/* 3254 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 3256 */       throw new InternalError();
/*      */     } 
/*      */     
/* 3259 */     return m1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM00() {
/* 3270 */     return this.m00;
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
/* 3281 */     this.m00 = m00;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM01() {
/* 3292 */     return this.m01;
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
/* 3303 */     this.m01 = m01;
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
/* 3314 */     return this.m02;
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
/* 3325 */     this.m02 = m02;
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
/* 3336 */     return this.m10;
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
/* 3347 */     this.m10 = m10;
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
/* 3358 */     return this.m11;
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
/* 3369 */     this.m11 = m11;
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
/* 3380 */     return this.m12;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM12(float m12) {
/* 3391 */     this.m12 = m12;
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
/* 3402 */     return this.m20;
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
/* 3413 */     this.m20 = m20;
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
/* 3424 */     return this.m21;
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
/* 3435 */     this.m21 = m21;
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
/* 3446 */     return this.m22;
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
/* 3457 */     this.m22 = m22;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM03() {
/* 3468 */     return this.m03;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM03(float m03) {
/* 3479 */     this.m03 = m03;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM13() {
/* 3490 */     return this.m13;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM13(float m13) {
/* 3501 */     this.m13 = m13;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM23() {
/* 3512 */     return this.m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM23(float m23) {
/* 3523 */     this.m23 = m23;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM30() {
/* 3534 */     return this.m30;
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
/*      */   public final void setM30(float m30) {
/* 3546 */     this.m30 = m30;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM31() {
/* 3557 */     return this.m31;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM31(float m31) {
/* 3568 */     this.m31 = m31;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM32() {
/* 3579 */     return this.m32;
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
/*      */   public final void setM32(float m32) {
/* 3591 */     this.m32 = m32;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final float getM33() {
/* 3602 */     return this.m33;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setM33(float m33) {
/* 3613 */     this.m33 = m33;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\Matrix4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */