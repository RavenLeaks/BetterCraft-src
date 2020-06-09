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
/*      */ public class GMatrix
/*      */   implements Serializable, Cloneable
/*      */ {
/*      */   static final long serialVersionUID = 2777097312029690941L;
/*      */   private static final boolean debug = false;
/*      */   int nRow;
/*      */   int nCol;
/*      */   double[][] values;
/*      */   private static final double EPS = 1.0E-10D;
/*      */   
/*      */   public GMatrix(int nRow, int nCol) {
/*      */     int l;
/*   60 */     this.values = new double[nRow][nCol];
/*   61 */     this.nRow = nRow;
/*   62 */     this.nCol = nCol;
/*      */     
/*      */     int i;
/*   65 */     for (i = 0; i < nRow; i++) {
/*   66 */       for (int j = 0; j < nCol; j++) {
/*   67 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*   72 */     if (nRow < nCol) {
/*   73 */       l = nRow;
/*      */     } else {
/*   75 */       l = nCol;
/*      */     } 
/*   77 */     for (i = 0; i < l; i++) {
/*   78 */       this.values[i][i] = 1.0D;
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
/*      */ 
/*      */   
/*      */   public GMatrix(int nRow, int nCol, double[] matrix) {
/*   96 */     this.values = new double[nRow][nCol];
/*   97 */     this.nRow = nRow;
/*   98 */     this.nCol = nCol;
/*      */ 
/*      */     
/*  101 */     for (int i = 0; i < nRow; i++) {
/*  102 */       for (int j = 0; j < nCol; j++) {
/*  103 */         this.values[i][j] = matrix[i * nCol + j];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GMatrix(GMatrix matrix) {
/*  115 */     this.nRow = matrix.nRow;
/*  116 */     this.nCol = matrix.nCol;
/*  117 */     this.values = new double[this.nRow][this.nCol];
/*      */ 
/*      */     
/*  120 */     for (int i = 0; i < this.nRow; i++) {
/*  121 */       for (int j = 0; j < this.nCol; j++) {
/*  122 */         this.values[i][j] = matrix.values[i][j];
/*      */       }
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
/*      */   public final void mul(GMatrix m1) {
/*  136 */     if (this.nCol != m1.nRow || this.nCol != m1.nCol) {
/*  137 */       throw new MismatchedSizeException(
/*  138 */           VecMathI18N.getString("GMatrix0"));
/*      */     }
/*  140 */     double[][] tmp = new double[this.nRow][this.nCol];
/*      */     
/*  142 */     for (int i = 0; i < this.nRow; i++) {
/*  143 */       for (int j = 0; j < this.nCol; j++) {
/*  144 */         tmp[i][j] = 0.0D;
/*  145 */         for (int k = 0; k < this.nCol; k++) {
/*  146 */           tmp[i][j] = tmp[i][j] + this.values[i][k] * m1.values[k][j];
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  151 */     this.values = tmp;
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
/*      */   public final void mul(GMatrix m1, GMatrix m2) {
/*  164 */     if (m1.nCol != m2.nRow || this.nRow != m1.nRow || this.nCol != m2.nCol) {
/*  165 */       throw new MismatchedSizeException(
/*  166 */           VecMathI18N.getString("GMatrix1"));
/*      */     }
/*  168 */     double[][] tmp = new double[this.nRow][this.nCol];
/*      */     
/*  170 */     for (int i = 0; i < m1.nRow; i++) {
/*  171 */       for (int j = 0; j < m2.nCol; j++) {
/*  172 */         tmp[i][j] = 0.0D;
/*  173 */         for (int k = 0; k < m1.nCol; k++) {
/*  174 */           tmp[i][j] = tmp[i][j] + m1.values[i][k] * m2.values[k][j];
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/*  179 */     this.values = tmp;
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
/*      */   public final void mul(GVector v1, GVector v2) {
/*  194 */     if (this.nRow < v1.getSize()) {
/*  195 */       throw new MismatchedSizeException(
/*  196 */           VecMathI18N.getString("GMatrix2"));
/*      */     }
/*  198 */     if (this.nCol < v2.getSize()) {
/*  199 */       throw new MismatchedSizeException(
/*  200 */           VecMathI18N.getString("GMatrix3"));
/*      */     }
/*  202 */     for (int i = 0; i < v1.getSize(); i++) {
/*  203 */       for (int j = 0; j < v2.getSize(); j++) {
/*  204 */         this.values[i][j] = v1.values[i] * v2.values[j];
/*      */       }
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
/*      */   public final void add(GMatrix m1) {
/*  217 */     if (this.nRow != m1.nRow) {
/*  218 */       throw new MismatchedSizeException(
/*  219 */           VecMathI18N.getString("GMatrix4"));
/*      */     }
/*  221 */     if (this.nCol != m1.nCol) {
/*  222 */       throw new MismatchedSizeException(
/*  223 */           VecMathI18N.getString("GMatrix5"));
/*      */     }
/*  225 */     for (int i = 0; i < this.nRow; i++) {
/*  226 */       for (int j = 0; j < this.nCol; j++) {
/*  227 */         this.values[i][j] = this.values[i][j] + m1.values[i][j];
/*      */       }
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
/*      */   public final void add(GMatrix m1, GMatrix m2) {
/*  241 */     if (m2.nRow != m1.nRow) {
/*  242 */       throw new MismatchedSizeException(
/*  243 */           VecMathI18N.getString("GMatrix6"));
/*      */     }
/*  245 */     if (m2.nCol != m1.nCol) {
/*  246 */       throw new MismatchedSizeException(
/*  247 */           VecMathI18N.getString("GMatrix7"));
/*      */     }
/*  249 */     if (this.nCol != m1.nCol || this.nRow != m1.nRow) {
/*  250 */       throw new MismatchedSizeException(
/*  251 */           VecMathI18N.getString("GMatrix8"));
/*      */     }
/*  253 */     for (int i = 0; i < this.nRow; i++) {
/*  254 */       for (int j = 0; j < this.nCol; j++) {
/*  255 */         this.values[i][j] = m1.values[i][j] + m2.values[i][j];
/*      */       }
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
/*      */   public final void sub(GMatrix m1) {
/*  268 */     if (this.nRow != m1.nRow) {
/*  269 */       throw new MismatchedSizeException(
/*  270 */           VecMathI18N.getString("GMatrix9"));
/*      */     }
/*  272 */     if (this.nCol != m1.nCol) {
/*  273 */       throw new MismatchedSizeException(
/*  274 */           VecMathI18N.getString("GMatrix28"));
/*      */     }
/*  276 */     for (int i = 0; i < this.nRow; i++) {
/*  277 */       for (int j = 0; j < this.nCol; j++) {
/*  278 */         this.values[i][j] = this.values[i][j] - m1.values[i][j];
/*      */       }
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
/*      */   public final void sub(GMatrix m1, GMatrix m2) {
/*  292 */     if (m2.nRow != m1.nRow) {
/*  293 */       throw new MismatchedSizeException(
/*  294 */           VecMathI18N.getString("GMatrix10"));
/*      */     }
/*  296 */     if (m2.nCol != m1.nCol) {
/*  297 */       throw new MismatchedSizeException(
/*  298 */           VecMathI18N.getString("GMatrix11"));
/*      */     }
/*  300 */     if (this.nRow != m1.nRow || this.nCol != m1.nCol) {
/*  301 */       throw new MismatchedSizeException(
/*  302 */           VecMathI18N.getString("GMatrix12"));
/*      */     }
/*  304 */     for (int i = 0; i < this.nRow; i++) {
/*  305 */       for (int j = 0; j < this.nCol; j++) {
/*  306 */         this.values[i][j] = m1.values[i][j] - m2.values[i][j];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void negate() {
/*  317 */     for (int i = 0; i < this.nRow; i++) {
/*  318 */       for (int j = 0; j < this.nCol; j++) {
/*  319 */         this.values[i][j] = -this.values[i][j];
/*      */       }
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
/*      */   public final void negate(GMatrix m1) {
/*  332 */     if (this.nRow != m1.nRow || this.nCol != m1.nCol) {
/*  333 */       throw new MismatchedSizeException(
/*  334 */           VecMathI18N.getString("GMatrix13"));
/*      */     }
/*  336 */     for (int i = 0; i < this.nRow; i++) {
/*  337 */       for (int j = 0; j < this.nCol; j++) {
/*  338 */         this.values[i][j] = -m1.values[i][j];
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setIdentity() {
/*      */     int l;
/*      */     int i;
/*  349 */     for (i = 0; i < this.nRow; i++) {
/*  350 */       for (int j = 0; j < this.nCol; j++) {
/*  351 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  356 */     if (this.nRow < this.nCol) {
/*  357 */       l = this.nRow;
/*      */     } else {
/*  359 */       l = this.nCol;
/*      */     } 
/*  361 */     for (i = 0; i < l; i++) {
/*  362 */       this.values[i][i] = 1.0D;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void setZero() {
/*  372 */     for (int i = 0; i < this.nRow; i++) {
/*  373 */       for (int j = 0; j < this.nCol; j++) {
/*  374 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void identityMinus() {
/*      */     int l;
/*      */     int i;
/*  387 */     for (i = 0; i < this.nRow; i++) {
/*  388 */       for (int j = 0; j < this.nCol; j++) {
/*  389 */         this.values[i][j] = -this.values[i][j];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  394 */     if (this.nRow < this.nCol) {
/*  395 */       l = this.nRow;
/*      */     } else {
/*  397 */       l = this.nCol;
/*      */     } 
/*  399 */     for (i = 0; i < l; i++) {
/*  400 */       this.values[i][i] = this.values[i][i] + 1.0D;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert() {
/*  410 */     invertGeneral(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void invert(GMatrix m1) {
/*  420 */     invertGeneral(m1);
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
/*      */   public final void copySubMatrix(int rowSource, int colSource, int numRow, int numCol, int rowDest, int colDest, GMatrix target) {
/*  445 */     if (this != target) {
/*  446 */       for (int i = 0; i < numRow; i++) {
/*  447 */         for (int j = 0; j < numCol; j++) {
/*  448 */           target.values[rowDest + i][colDest + j] = 
/*  449 */             this.values[rowSource + i][colSource + j];
/*      */         }
/*      */       } 
/*      */     } else {
/*  453 */       double[][] tmp = new double[numRow][numCol]; int i;
/*  454 */       for (i = 0; i < numRow; i++) {
/*  455 */         for (int j = 0; j < numCol; j++) {
/*  456 */           tmp[i][j] = this.values[rowSource + i][colSource + j];
/*      */         }
/*      */       } 
/*  459 */       for (i = 0; i < numRow; i++) {
/*  460 */         for (int j = 0; j < numCol; j++) {
/*  461 */           target.values[rowDest + i][colDest + j] = tmp[i][j];
/*      */         }
/*      */       } 
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
/*      */   public final void setSize(int nRow, int nCol) {
/*      */     int maxRow, maxCol;
/*  476 */     double[][] tmp = new double[nRow][nCol];
/*      */ 
/*      */     
/*  479 */     if (this.nRow < nRow) {
/*  480 */       maxRow = this.nRow;
/*      */     } else {
/*  482 */       maxRow = nRow;
/*      */     } 
/*  484 */     if (this.nCol < nCol) {
/*  485 */       maxCol = this.nCol;
/*      */     } else {
/*  487 */       maxCol = nCol;
/*      */     } 
/*  489 */     for (int i = 0; i < maxRow; i++) {
/*  490 */       for (int j = 0; j < maxCol; j++) {
/*  491 */         tmp[i][j] = this.values[i][j];
/*      */       }
/*      */     } 
/*      */     
/*  495 */     this.nRow = nRow;
/*  496 */     this.nCol = nCol;
/*      */     
/*  498 */     this.values = tmp;
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
/*      */   public final void set(double[] matrix) {
/*  513 */     for (int i = 0; i < this.nRow; i++) {
/*  514 */       for (int j = 0; j < this.nCol; j++) {
/*  515 */         this.values[i][j] = matrix[this.nCol * i + j];
/*      */       }
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
/*      */   public final void set(Matrix3f m1) {
/*  528 */     if (this.nCol < 3 || this.nRow < 3) {
/*  529 */       this.nCol = 3;
/*  530 */       this.nRow = 3;
/*  531 */       this.values = new double[this.nRow][this.nCol];
/*      */     } 
/*      */     
/*  534 */     this.values[0][0] = m1.m00;
/*  535 */     this.values[0][1] = m1.m01;
/*  536 */     this.values[0][2] = m1.m02;
/*      */     
/*  538 */     this.values[1][0] = m1.m10;
/*  539 */     this.values[1][1] = m1.m11;
/*  540 */     this.values[1][2] = m1.m12;
/*      */     
/*  542 */     this.values[2][0] = m1.m20;
/*  543 */     this.values[2][1] = m1.m21;
/*  544 */     this.values[2][2] = m1.m22;
/*      */     
/*  546 */     for (int i = 3; i < this.nRow; i++) {
/*  547 */       for (int j = 3; j < this.nCol; j++) {
/*  548 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix3d m1) {
/*  559 */     if (this.nRow < 3 || this.nCol < 3) {
/*  560 */       this.values = new double[3][3];
/*  561 */       this.nRow = 3;
/*  562 */       this.nCol = 3;
/*      */     } 
/*      */     
/*  565 */     this.values[0][0] = m1.m00;
/*  566 */     this.values[0][1] = m1.m01;
/*  567 */     this.values[0][2] = m1.m02;
/*      */     
/*  569 */     this.values[1][0] = m1.m10;
/*  570 */     this.values[1][1] = m1.m11;
/*  571 */     this.values[1][2] = m1.m12;
/*      */     
/*  573 */     this.values[2][0] = m1.m20;
/*  574 */     this.values[2][1] = m1.m21;
/*  575 */     this.values[2][2] = m1.m22;
/*      */     
/*  577 */     for (int i = 3; i < this.nRow; i++) {
/*  578 */       for (int j = 3; j < this.nCol; j++) {
/*  579 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4f m1) {
/*  591 */     if (this.nRow < 4 || this.nCol < 4) {
/*  592 */       this.values = new double[4][4];
/*  593 */       this.nRow = 4;
/*  594 */       this.nCol = 4;
/*      */     } 
/*      */     
/*  597 */     this.values[0][0] = m1.m00;
/*  598 */     this.values[0][1] = m1.m01;
/*  599 */     this.values[0][2] = m1.m02;
/*  600 */     this.values[0][3] = m1.m03;
/*      */     
/*  602 */     this.values[1][0] = m1.m10;
/*  603 */     this.values[1][1] = m1.m11;
/*  604 */     this.values[1][2] = m1.m12;
/*  605 */     this.values[1][3] = m1.m13;
/*      */     
/*  607 */     this.values[2][0] = m1.m20;
/*  608 */     this.values[2][1] = m1.m21;
/*  609 */     this.values[2][2] = m1.m22;
/*  610 */     this.values[2][3] = m1.m23;
/*      */     
/*  612 */     this.values[3][0] = m1.m30;
/*  613 */     this.values[3][1] = m1.m31;
/*  614 */     this.values[3][2] = m1.m32;
/*  615 */     this.values[3][3] = m1.m33;
/*      */     
/*  617 */     for (int i = 4; i < this.nRow; i++) {
/*  618 */       for (int j = 4; j < this.nCol; j++) {
/*  619 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void set(Matrix4d m1) {
/*  630 */     if (this.nRow < 4 || this.nCol < 4) {
/*  631 */       this.values = new double[4][4];
/*  632 */       this.nRow = 4;
/*  633 */       this.nCol = 4;
/*      */     } 
/*      */     
/*  636 */     this.values[0][0] = m1.m00;
/*  637 */     this.values[0][1] = m1.m01;
/*  638 */     this.values[0][2] = m1.m02;
/*  639 */     this.values[0][3] = m1.m03;
/*      */     
/*  641 */     this.values[1][0] = m1.m10;
/*  642 */     this.values[1][1] = m1.m11;
/*  643 */     this.values[1][2] = m1.m12;
/*  644 */     this.values[1][3] = m1.m13;
/*      */     
/*  646 */     this.values[2][0] = m1.m20;
/*  647 */     this.values[2][1] = m1.m21;
/*  648 */     this.values[2][2] = m1.m22;
/*  649 */     this.values[2][3] = m1.m23;
/*      */     
/*  651 */     this.values[3][0] = m1.m30;
/*  652 */     this.values[3][1] = m1.m31;
/*  653 */     this.values[3][2] = m1.m32;
/*  654 */     this.values[3][3] = m1.m33;
/*      */     
/*  656 */     for (int i = 4; i < this.nRow; i++) {
/*  657 */       for (int j = 4; j < this.nCol; j++) {
/*  658 */         this.values[i][j] = 0.0D;
/*      */       }
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
/*      */   public final void set(GMatrix m1) {
/*  671 */     if (this.nRow < m1.nRow || this.nCol < m1.nCol) {
/*  672 */       this.nRow = m1.nRow;
/*  673 */       this.nCol = m1.nCol;
/*  674 */       this.values = new double[this.nRow][this.nCol];
/*      */     } 
/*      */     int i;
/*  677 */     for (i = 0; i < Math.min(this.nRow, m1.nRow); i++) {
/*  678 */       for (int j = 0; j < Math.min(this.nCol, m1.nCol); j++) {
/*  679 */         this.values[i][j] = m1.values[i][j];
/*      */       }
/*      */     } 
/*      */     
/*  683 */     for (i = m1.nRow; i < this.nRow; i++) {
/*  684 */       for (int j = m1.nCol; j < this.nCol; j++) {
/*  685 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getNumRow() {
/*  696 */     return this.nRow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final int getNumCol() {
/*  705 */     return this.nCol;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double getElement(int row, int column) {
/*  716 */     return this.values[row][column];
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
/*      */   public final void setElement(int row, int column, double value) {
/*  728 */     this.values[row][column] = value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, double[] array) {
/*  738 */     for (int i = 0; i < this.nCol; i++) {
/*  739 */       array[i] = this.values[row][i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getRow(int row, GVector vector) {
/*  750 */     if (vector.getSize() < this.nCol) {
/*  751 */       vector.setSize(this.nCol);
/*      */     }
/*  753 */     for (int i = 0; i < this.nCol; i++) {
/*  754 */       vector.values[i] = this.values[row][i];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void getColumn(int col, double[] array) {
/*  765 */     for (int i = 0; i < this.nRow; i++) {
/*  766 */       array[i] = this.values[i][col];
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
/*      */   public final void getColumn(int col, GVector vector) {
/*  778 */     if (vector.getSize() < this.nRow) {
/*  779 */       vector.setSize(this.nRow);
/*      */     }
/*  781 */     for (int i = 0; i < this.nRow; i++) {
/*  782 */       vector.values[i] = this.values[i][col];
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Matrix3d m1) {
/*  793 */     if (this.nRow < 3 || this.nCol < 3) {
/*  794 */       m1.setZero();
/*  795 */       if (this.nCol > 0) {
/*  796 */         if (this.nRow > 0) {
/*  797 */           m1.m00 = this.values[0][0];
/*  798 */           if (this.nRow > 1) {
/*  799 */             m1.m10 = this.values[1][0];
/*  800 */             if (this.nRow > 2) {
/*  801 */               m1.m20 = this.values[2][0];
/*      */             }
/*      */           } 
/*      */         } 
/*  805 */         if (this.nCol > 1) {
/*  806 */           if (this.nRow > 0) {
/*  807 */             m1.m01 = this.values[0][1];
/*  808 */             if (this.nRow > 1) {
/*  809 */               m1.m11 = this.values[1][1];
/*  810 */               if (this.nRow > 2) {
/*  811 */                 m1.m21 = this.values[2][1];
/*      */               }
/*      */             } 
/*      */           } 
/*  815 */           if (this.nCol > 2 && 
/*  816 */             this.nRow > 0) {
/*  817 */             m1.m02 = this.values[0][2];
/*  818 */             if (this.nRow > 1) {
/*  819 */               m1.m12 = this.values[1][2];
/*  820 */               if (this.nRow > 2) {
/*  821 */                 m1.m22 = this.values[2][2];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  829 */       m1.m00 = this.values[0][0];
/*  830 */       m1.m01 = this.values[0][1];
/*  831 */       m1.m02 = this.values[0][2];
/*      */       
/*  833 */       m1.m10 = this.values[1][0];
/*  834 */       m1.m11 = this.values[1][1];
/*  835 */       m1.m12 = this.values[1][2];
/*      */       
/*  837 */       m1.m20 = this.values[2][0];
/*  838 */       m1.m21 = this.values[2][1];
/*  839 */       m1.m22 = this.values[2][2];
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
/*      */   public final void get(Matrix3f m1) {
/*  851 */     if (this.nRow < 3 || this.nCol < 3) {
/*  852 */       m1.setZero();
/*  853 */       if (this.nCol > 0) {
/*  854 */         if (this.nRow > 0) {
/*  855 */           m1.m00 = (float)this.values[0][0];
/*  856 */           if (this.nRow > 1) {
/*  857 */             m1.m10 = (float)this.values[1][0];
/*  858 */             if (this.nRow > 2) {
/*  859 */               m1.m20 = (float)this.values[2][0];
/*      */             }
/*      */           } 
/*      */         } 
/*  863 */         if (this.nCol > 1) {
/*  864 */           if (this.nRow > 0) {
/*  865 */             m1.m01 = (float)this.values[0][1];
/*  866 */             if (this.nRow > 1) {
/*  867 */               m1.m11 = (float)this.values[1][1];
/*  868 */               if (this.nRow > 2) {
/*  869 */                 m1.m21 = (float)this.values[2][1];
/*      */               }
/*      */             } 
/*      */           } 
/*  873 */           if (this.nCol > 2 && 
/*  874 */             this.nRow > 0) {
/*  875 */             m1.m02 = (float)this.values[0][2];
/*  876 */             if (this.nRow > 1) {
/*  877 */               m1.m12 = (float)this.values[1][2];
/*  878 */               if (this.nRow > 2) {
/*  879 */                 m1.m22 = (float)this.values[2][2];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  887 */       m1.m00 = (float)this.values[0][0];
/*  888 */       m1.m01 = (float)this.values[0][1];
/*  889 */       m1.m02 = (float)this.values[0][2];
/*      */       
/*  891 */       m1.m10 = (float)this.values[1][0];
/*  892 */       m1.m11 = (float)this.values[1][1];
/*  893 */       m1.m12 = (float)this.values[1][2];
/*      */       
/*  895 */       m1.m20 = (float)this.values[2][0];
/*  896 */       m1.m21 = (float)this.values[2][1];
/*  897 */       m1.m22 = (float)this.values[2][2];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(Matrix4d m1) {
/*  908 */     if (this.nRow < 4 || this.nCol < 4) {
/*  909 */       m1.setZero();
/*  910 */       if (this.nCol > 0) {
/*  911 */         if (this.nRow > 0) {
/*  912 */           m1.m00 = this.values[0][0];
/*  913 */           if (this.nRow > 1) {
/*  914 */             m1.m10 = this.values[1][0];
/*  915 */             if (this.nRow > 2) {
/*  916 */               m1.m20 = this.values[2][0];
/*  917 */               if (this.nRow > 3) {
/*  918 */                 m1.m30 = this.values[3][0];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*  923 */         if (this.nCol > 1) {
/*  924 */           if (this.nRow > 0) {
/*  925 */             m1.m01 = this.values[0][1];
/*  926 */             if (this.nRow > 1) {
/*  927 */               m1.m11 = this.values[1][1];
/*  928 */               if (this.nRow > 2) {
/*  929 */                 m1.m21 = this.values[2][1];
/*  930 */                 if (this.nRow > 3) {
/*  931 */                   m1.m31 = this.values[3][1];
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/*  936 */           if (this.nCol > 2) {
/*  937 */             if (this.nRow > 0) {
/*  938 */               m1.m02 = this.values[0][2];
/*  939 */               if (this.nRow > 1) {
/*  940 */                 m1.m12 = this.values[1][2];
/*  941 */                 if (this.nRow > 2) {
/*  942 */                   m1.m22 = this.values[2][2];
/*  943 */                   if (this.nRow > 3) {
/*  944 */                     m1.m32 = this.values[3][2];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*  949 */             if (this.nCol > 3 && 
/*  950 */               this.nRow > 0) {
/*  951 */               m1.m03 = this.values[0][3];
/*  952 */               if (this.nRow > 1) {
/*  953 */                 m1.m13 = this.values[1][3];
/*  954 */                 if (this.nRow > 2) {
/*  955 */                   m1.m23 = this.values[2][3];
/*  956 */                   if (this.nRow > 3) {
/*  957 */                     m1.m33 = this.values[3][3];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/*  967 */       m1.m00 = this.values[0][0];
/*  968 */       m1.m01 = this.values[0][1];
/*  969 */       m1.m02 = this.values[0][2];
/*  970 */       m1.m03 = this.values[0][3];
/*      */       
/*  972 */       m1.m10 = this.values[1][0];
/*  973 */       m1.m11 = this.values[1][1];
/*  974 */       m1.m12 = this.values[1][2];
/*  975 */       m1.m13 = this.values[1][3];
/*      */       
/*  977 */       m1.m20 = this.values[2][0];
/*  978 */       m1.m21 = this.values[2][1];
/*  979 */       m1.m22 = this.values[2][2];
/*  980 */       m1.m23 = this.values[2][3];
/*      */       
/*  982 */       m1.m30 = this.values[3][0];
/*  983 */       m1.m31 = this.values[3][1];
/*  984 */       m1.m32 = this.values[3][2];
/*  985 */       m1.m33 = this.values[3][3];
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
/*      */   public final void get(Matrix4f m1) {
/*  998 */     if (this.nRow < 4 || this.nCol < 4) {
/*  999 */       m1.setZero();
/* 1000 */       if (this.nCol > 0) {
/* 1001 */         if (this.nRow > 0) {
/* 1002 */           m1.m00 = (float)this.values[0][0];
/* 1003 */           if (this.nRow > 1) {
/* 1004 */             m1.m10 = (float)this.values[1][0];
/* 1005 */             if (this.nRow > 2) {
/* 1006 */               m1.m20 = (float)this.values[2][0];
/* 1007 */               if (this.nRow > 3) {
/* 1008 */                 m1.m30 = (float)this.values[3][0];
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/* 1013 */         if (this.nCol > 1) {
/* 1014 */           if (this.nRow > 0) {
/* 1015 */             m1.m01 = (float)this.values[0][1];
/* 1016 */             if (this.nRow > 1) {
/* 1017 */               m1.m11 = (float)this.values[1][1];
/* 1018 */               if (this.nRow > 2) {
/* 1019 */                 m1.m21 = (float)this.values[2][1];
/* 1020 */                 if (this.nRow > 3) {
/* 1021 */                   m1.m31 = (float)this.values[3][1];
/*      */                 }
/*      */               } 
/*      */             } 
/*      */           } 
/* 1026 */           if (this.nCol > 2) {
/* 1027 */             if (this.nRow > 0) {
/* 1028 */               m1.m02 = (float)this.values[0][2];
/* 1029 */               if (this.nRow > 1) {
/* 1030 */                 m1.m12 = (float)this.values[1][2];
/* 1031 */                 if (this.nRow > 2) {
/* 1032 */                   m1.m22 = (float)this.values[2][2];
/* 1033 */                   if (this.nRow > 3) {
/* 1034 */                     m1.m32 = (float)this.values[3][2];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/* 1039 */             if (this.nCol > 3 && 
/* 1040 */               this.nRow > 0) {
/* 1041 */               m1.m03 = (float)this.values[0][3];
/* 1042 */               if (this.nRow > 1) {
/* 1043 */                 m1.m13 = (float)this.values[1][3];
/* 1044 */                 if (this.nRow > 2) {
/* 1045 */                   m1.m23 = (float)this.values[2][3];
/* 1046 */                   if (this.nRow > 3) {
/* 1047 */                     m1.m33 = (float)this.values[3][3];
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1057 */       m1.m00 = (float)this.values[0][0];
/* 1058 */       m1.m01 = (float)this.values[0][1];
/* 1059 */       m1.m02 = (float)this.values[0][2];
/* 1060 */       m1.m03 = (float)this.values[0][3];
/*      */       
/* 1062 */       m1.m10 = (float)this.values[1][0];
/* 1063 */       m1.m11 = (float)this.values[1][1];
/* 1064 */       m1.m12 = (float)this.values[1][2];
/* 1065 */       m1.m13 = (float)this.values[1][3];
/*      */       
/* 1067 */       m1.m20 = (float)this.values[2][0];
/* 1068 */       m1.m21 = (float)this.values[2][1];
/* 1069 */       m1.m22 = (float)this.values[2][2];
/* 1070 */       m1.m23 = (float)this.values[2][3];
/*      */       
/* 1072 */       m1.m30 = (float)this.values[3][0];
/* 1073 */       m1.m31 = (float)this.values[3][1];
/* 1074 */       m1.m32 = (float)this.values[3][2];
/* 1075 */       m1.m33 = (float)this.values[3][3];
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final void get(GMatrix m1) {
/*      */     int nc;
/*      */     int nr;
/* 1088 */     if (this.nCol < m1.nCol) {
/* 1089 */       nc = this.nCol;
/*      */     } else {
/* 1091 */       nc = m1.nCol;
/*      */     } 
/* 1093 */     if (this.nRow < m1.nRow) {
/* 1094 */       nr = this.nRow;
/*      */     } else {
/* 1096 */       nr = m1.nRow;
/*      */     }  int i;
/* 1098 */     for (i = 0; i < nr; i++) {
/* 1099 */       for (int k = 0; k < nc; k++) {
/* 1100 */         m1.values[i][k] = this.values[i][k];
/*      */       }
/*      */     } 
/* 1103 */     for (i = nr; i < m1.nRow; i++) {
/* 1104 */       for (int k = 0; k < m1.nCol; k++) {
/* 1105 */         m1.values[i][k] = 0.0D;
/*      */       }
/*      */     } 
/* 1108 */     for (int j = nc; j < m1.nCol; j++) {
/* 1109 */       for (i = 0; i < nr; i++) {
/* 1110 */         m1.values[i][j] = 0.0D;
/*      */       }
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
/*      */   public final void setRow(int row, double[] array) {
/* 1124 */     for (int i = 0; i < this.nCol; i++) {
/* 1125 */       this.values[row][i] = array[i];
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
/*      */   public final void setRow(int row, GVector vector) {
/* 1138 */     for (int i = 0; i < this.nCol; i++) {
/* 1139 */       this.values[row][i] = vector.values[i];
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
/*      */   public final void setColumn(int col, double[] array) {
/* 1152 */     for (int i = 0; i < this.nRow; i++) {
/* 1153 */       this.values[i][col] = array[i];
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
/*      */   public final void setColumn(int col, GVector vector) {
/* 1166 */     for (int i = 0; i < this.nRow; i++) {
/* 1167 */       this.values[i][col] = vector.values[i];
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
/*      */   public final void mulTransposeBoth(GMatrix m1, GMatrix m2) {
/* 1182 */     if (m1.nRow != m2.nCol || this.nRow != m1.nCol || this.nCol != m2.nRow) {
/* 1183 */       throw new MismatchedSizeException(
/* 1184 */           VecMathI18N.getString("GMatrix14"));
/*      */     }
/* 1186 */     if (m1 == this || m2 == this) {
/* 1187 */       double[][] tmp = new double[this.nRow][this.nCol];
/* 1188 */       for (int i = 0; i < this.nRow; i++) {
/* 1189 */         for (int j = 0; j < this.nCol; j++) {
/* 1190 */           tmp[i][j] = 0.0D;
/* 1191 */           for (int k = 0; k < m1.nRow; k++) {
/* 1192 */             tmp[i][j] = tmp[i][j] + m1.values[k][i] * m2.values[j][k];
/*      */           }
/*      */         } 
/*      */       } 
/* 1196 */       this.values = tmp;
/*      */     } else {
/* 1198 */       for (int i = 0; i < this.nRow; i++) {
/* 1199 */         for (int j = 0; j < this.nCol; j++) {
/* 1200 */           this.values[i][j] = 0.0D;
/* 1201 */           for (int k = 0; k < m1.nRow; k++) {
/* 1202 */             this.values[i][j] = this.values[i][j] + m1.values[k][i] * m2.values[j][k];
/*      */           }
/*      */         } 
/*      */       } 
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
/*      */   public final void mulTransposeRight(GMatrix m1, GMatrix m2) {
/* 1219 */     if (m1.nCol != m2.nCol || this.nCol != m2.nRow || this.nRow != m1.nRow) {
/* 1220 */       throw new MismatchedSizeException(
/* 1221 */           VecMathI18N.getString("GMatrix15"));
/*      */     }
/* 1223 */     if (m1 == this || m2 == this) {
/* 1224 */       double[][] tmp = new double[this.nRow][this.nCol];
/* 1225 */       for (int i = 0; i < this.nRow; i++) {
/* 1226 */         for (int j = 0; j < this.nCol; j++) {
/* 1227 */           tmp[i][j] = 0.0D;
/* 1228 */           for (int k = 0; k < m1.nCol; k++) {
/* 1229 */             tmp[i][j] = tmp[i][j] + m1.values[i][k] * m2.values[j][k];
/*      */           }
/*      */         } 
/*      */       } 
/* 1233 */       this.values = tmp;
/*      */     } else {
/* 1235 */       for (int i = 0; i < this.nRow; i++) {
/* 1236 */         for (int j = 0; j < this.nCol; j++) {
/* 1237 */           this.values[i][j] = 0.0D;
/* 1238 */           for (int k = 0; k < m1.nCol; k++) {
/* 1239 */             this.values[i][j] = this.values[i][j] + m1.values[i][k] * m2.values[j][k];
/*      */           }
/*      */         } 
/*      */       } 
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
/*      */   public final void mulTransposeLeft(GMatrix m1, GMatrix m2) {
/* 1258 */     if (m1.nRow != m2.nRow || this.nCol != m2.nCol || this.nRow != m1.nCol) {
/* 1259 */       throw new MismatchedSizeException(
/* 1260 */           VecMathI18N.getString("GMatrix16"));
/*      */     }
/* 1262 */     if (m1 == this || m2 == this) {
/* 1263 */       double[][] tmp = new double[this.nRow][this.nCol];
/* 1264 */       for (int i = 0; i < this.nRow; i++) {
/* 1265 */         for (int j = 0; j < this.nCol; j++) {
/* 1266 */           tmp[i][j] = 0.0D;
/* 1267 */           for (int k = 0; k < m1.nRow; k++) {
/* 1268 */             tmp[i][j] = tmp[i][j] + m1.values[k][i] * m2.values[k][j];
/*      */           }
/*      */         } 
/*      */       } 
/* 1272 */       this.values = tmp;
/*      */     } else {
/* 1274 */       for (int i = 0; i < this.nRow; i++) {
/* 1275 */         for (int j = 0; j < this.nCol; j++) {
/* 1276 */           this.values[i][j] = 0.0D;
/* 1277 */           for (int k = 0; k < m1.nRow; k++) {
/* 1278 */             this.values[i][j] = this.values[i][j] + m1.values[k][i] * m2.values[k][j];
/*      */           }
/*      */         } 
/*      */       } 
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
/*      */   public final void transpose() {
/* 1293 */     if (this.nRow != this.nCol) {
/*      */       
/* 1295 */       int i = this.nRow;
/* 1296 */       this.nRow = this.nCol;
/* 1297 */       this.nCol = i;
/* 1298 */       double[][] tmp = new double[this.nRow][this.nCol];
/* 1299 */       for (i = 0; i < this.nRow; i++) {
/* 1300 */         for (int j = 0; j < this.nCol; j++) {
/* 1301 */           tmp[i][j] = this.values[j][i];
/*      */         }
/*      */       } 
/* 1304 */       this.values = tmp;
/*      */     } else {
/*      */       
/* 1307 */       for (int i = 0; i < this.nRow; i++) {
/* 1308 */         for (int j = 0; j < i; j++) {
/* 1309 */           double swap = this.values[i][j];
/* 1310 */           this.values[i][j] = this.values[j][i];
/* 1311 */           this.values[j][i] = swap;
/*      */         } 
/*      */       } 
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
/*      */   public final void transpose(GMatrix m1) {
/* 1325 */     if (this.nRow != m1.nCol || this.nCol != m1.nRow) {
/* 1326 */       throw new MismatchedSizeException(
/* 1327 */           VecMathI18N.getString("GMatrix17"));
/*      */     }
/* 1329 */     if (m1 != this) {
/* 1330 */       for (int i = 0; i < this.nRow; i++) {
/* 1331 */         for (int j = 0; j < this.nCol; j++) {
/* 1332 */           this.values[i][j] = m1.values[j][i];
/*      */         }
/*      */       } 
/*      */     } else {
/* 1336 */       transpose();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1347 */     StringBuffer buffer = new StringBuffer(this.nRow * this.nCol * 8);
/*      */ 
/*      */ 
/*      */     
/* 1351 */     for (int i = 0; i < this.nRow; i++) {
/* 1352 */       for (int j = 0; j < this.nCol; j++) {
/* 1353 */         buffer.append(this.values[i][j]).append(" ");
/*      */       }
/* 1355 */       buffer.append("\n");
/*      */     } 
/*      */     
/* 1358 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkMatrix(GMatrix m) {
/* 1365 */     for (int i = 0; i < m.nRow; i++) {
/* 1366 */       for (int j = 0; j < m.nCol; j++) {
/* 1367 */         if (Math.abs(m.values[i][j]) < 1.0E-10D) {
/* 1368 */           System.out.print(" 0.0     ");
/*      */         } else {
/* 1370 */           System.out.print(" " + m.values[i][j]);
/*      */         } 
/*      */       } 
/* 1373 */       System.out.print("\n");
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
/*      */   public int hashCode() {
/* 1389 */     long bits = 1L;
/*      */     
/* 1391 */     bits = VecMathUtil.hashLongBits(bits, this.nRow);
/* 1392 */     bits = VecMathUtil.hashLongBits(bits, this.nCol);
/*      */     
/* 1394 */     for (int i = 0; i < this.nRow; i++) {
/* 1395 */       for (int j = 0; j < this.nCol; j++) {
/* 1396 */         bits = VecMathUtil.hashDoubleBits(bits, this.values[i][j]);
/*      */       }
/*      */     } 
/*      */     
/* 1400 */     return VecMathUtil.hashFinish(bits);
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
/*      */   public boolean equals(GMatrix m1) {
/*      */     try {
/* 1415 */       if (this.nRow != m1.nRow || this.nCol != m1.nCol) {
/* 1416 */         return false;
/*      */       }
/* 1418 */       for (int i = 0; i < this.nRow; i++) {
/* 1419 */         for (int j = 0; j < this.nCol; j++) {
/* 1420 */           if (this.values[i][j] != m1.values[i][j])
/* 1421 */             return false; 
/*      */         } 
/*      */       } 
/* 1424 */       return true;
/*      */     }
/* 1426 */     catch (NullPointerException e2) {
/* 1427 */       return false;
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
/*      */   public boolean equals(Object o1) {
/*      */     try {
/* 1442 */       GMatrix m2 = (GMatrix)o1;
/*      */       
/* 1444 */       if (this.nRow != m2.nRow || this.nCol != m2.nCol) {
/* 1445 */         return false;
/*      */       }
/* 1447 */       for (int i = 0; i < this.nRow; i++) {
/* 1448 */         for (int j = 0; j < this.nCol; j++) {
/* 1449 */           if (this.values[i][j] != m2.values[i][j])
/* 1450 */             return false; 
/*      */         } 
/*      */       } 
/* 1453 */       return true;
/*      */     }
/* 1455 */     catch (ClassCastException e1) {
/* 1456 */       return false;
/*      */     }
/* 1458 */     catch (NullPointerException e2) {
/* 1459 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean epsilonEquals(GMatrix m1, float epsilon) {
/* 1467 */     return epsilonEquals(m1, epsilon);
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
/*      */   public boolean epsilonEquals(GMatrix m1, double epsilon) {
/* 1483 */     if (this.nRow != m1.nRow || this.nCol != m1.nCol) {
/* 1484 */       return false;
/*      */     }
/* 1486 */     for (int i = 0; i < this.nRow; i++) {
/* 1487 */       for (int j = 0; j < this.nCol; j++) {
/* 1488 */         double diff = this.values[i][j] - m1.values[i][j];
/* 1489 */         if (((diff < 0.0D) ? -diff : diff) > epsilon)
/* 1490 */           return false; 
/*      */       } 
/*      */     } 
/* 1493 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final double trace() {
/*      */     int l;
/* 1505 */     if (this.nRow < this.nCol) {
/* 1506 */       l = this.nRow;
/*      */     } else {
/* 1508 */       l = this.nCol;
/*      */     } 
/* 1510 */     double t = 0.0D;
/* 1511 */     for (int i = 0; i < l; i++) {
/* 1512 */       t += this.values[i][i];
/*      */     }
/* 1514 */     return t;
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
/*      */   public final int SVD(GMatrix U, GMatrix W, GMatrix V) {
/* 1536 */     if (this.nCol != V.nCol || this.nCol != V.nRow) {
/* 1537 */       throw new MismatchedSizeException(
/* 1538 */           VecMathI18N.getString("GMatrix18"));
/*      */     }
/*      */     
/* 1541 */     if (this.nRow != U.nRow || this.nRow != U.nCol) {
/* 1542 */       throw new MismatchedSizeException(
/* 1543 */           VecMathI18N.getString("GMatrix25"));
/*      */     }
/*      */     
/* 1546 */     if (this.nRow != W.nRow || this.nCol != W.nCol) {
/* 1547 */       throw new MismatchedSizeException(
/* 1548 */           VecMathI18N.getString("GMatrix26"));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1561 */     if (this.nRow == 2 && this.nCol == 2 && 
/* 1562 */       this.values[1][0] == 0.0D) {
/* 1563 */       U.setIdentity();
/* 1564 */       V.setIdentity();
/*      */       
/* 1566 */       if (this.values[0][1] == 0.0D) {
/* 1567 */         return 2;
/*      */       }
/*      */       
/* 1570 */       double[] sinl = new double[1];
/* 1571 */       double[] sinr = new double[1];
/* 1572 */       double[] cosl = new double[1];
/* 1573 */       double[] cosr = new double[1];
/* 1574 */       double[] single_values = new double[2];
/*      */       
/* 1576 */       single_values[0] = this.values[0][0];
/* 1577 */       single_values[1] = this.values[1][1];
/*      */       
/* 1579 */       compute_2X2(this.values[0][0], this.values[0][1], this.values[1][1], 
/* 1580 */           single_values, sinl, cosl, sinr, cosr, 0);
/*      */       
/* 1582 */       update_u(0, U, cosl, sinl);
/* 1583 */       update_v(0, V, cosr, sinr);
/*      */       
/* 1585 */       return 2;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1590 */     return computeSVD(this, U, W, V);
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
/*      */   public final int LUD(GMatrix LU, GVector permutation) {
/* 1614 */     int size = LU.nRow * LU.nCol;
/* 1615 */     double[] temp = new double[size];
/* 1616 */     int[] even_row_exchange = new int[1];
/* 1617 */     int[] row_perm = new int[LU.nRow];
/*      */ 
/*      */     
/* 1620 */     if (this.nRow != this.nCol) {
/* 1621 */       throw new MismatchedSizeException(
/* 1622 */           VecMathI18N.getString("GMatrix19"));
/*      */     }
/*      */     
/* 1625 */     if (this.nRow != LU.nRow) {
/* 1626 */       throw new MismatchedSizeException(
/* 1627 */           VecMathI18N.getString("GMatrix27"));
/*      */     }
/*      */     
/* 1630 */     if (this.nCol != LU.nCol) {
/* 1631 */       throw new MismatchedSizeException(
/* 1632 */           VecMathI18N.getString("GMatrix27"));
/*      */     }
/*      */     
/* 1635 */     if (LU.nRow != permutation.getSize()) {
/* 1636 */       throw new MismatchedSizeException(
/* 1637 */           VecMathI18N.getString("GMatrix20"));
/*      */     }
/*      */     int i;
/* 1640 */     for (i = 0; i < this.nRow; i++) {
/* 1641 */       for (int j = 0; j < this.nCol; j++) {
/* 1642 */         temp[i * this.nCol + j] = this.values[i][j];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1647 */     if (!luDecomposition(LU.nRow, temp, row_perm, even_row_exchange))
/*      */     {
/* 1649 */       throw new SingularMatrixException(
/* 1650 */           VecMathI18N.getString("GMatrix21"));
/*      */     }
/*      */     
/* 1653 */     for (i = 0; i < this.nRow; i++) {
/* 1654 */       for (int j = 0; j < this.nCol; j++) {
/* 1655 */         LU.values[i][j] = temp[i * this.nCol + j];
/*      */       }
/*      */     } 
/*      */     
/* 1659 */     for (i = 0; i < LU.nRow; i++) {
/* 1660 */       permutation.values[i] = row_perm[i];
/*      */     }
/*      */     
/* 1663 */     return even_row_exchange[0];
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
/*      */     int l;
/* 1675 */     if (this.nRow < this.nCol) {
/* 1676 */       l = this.nRow;
/*      */     } else {
/* 1678 */       l = this.nCol;
/*      */     }  int i;
/* 1680 */     for (i = 0; i < this.nRow; i++) {
/* 1681 */       for (int j = 0; j < this.nCol; j++) {
/* 1682 */         this.values[i][j] = 0.0D;
/*      */       }
/*      */     } 
/*      */     
/* 1686 */     for (i = 0; i < l; i++) {
/* 1687 */       this.values[i][i] = scale;
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
/*      */   final void invertGeneral(GMatrix m1) {
/* 1700 */     int size = m1.nRow * m1.nCol;
/* 1701 */     double[] temp = new double[size];
/* 1702 */     double[] result = new double[size];
/* 1703 */     int[] row_perm = new int[m1.nRow];
/* 1704 */     int[] even_row_exchange = new int[1];
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1709 */     if (m1.nRow != m1.nCol)
/*      */     {
/* 1711 */       throw new MismatchedSizeException(
/* 1712 */           VecMathI18N.getString("GMatrix22"));
/*      */     }
/*      */     
/*      */     int i;
/* 1716 */     for (i = 0; i < this.nRow; i++) {
/* 1717 */       for (int j = 0; j < this.nCol; j++) {
/* 1718 */         temp[i * this.nCol + j] = m1.values[i][j];
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1723 */     if (!luDecomposition(m1.nRow, temp, row_perm, even_row_exchange))
/*      */     {
/* 1725 */       throw new SingularMatrixException(
/* 1726 */           VecMathI18N.getString("GMatrix21"));
/*      */     }
/*      */ 
/*      */     
/* 1730 */     for (i = 0; i < size; i++) {
/* 1731 */       result[i] = 0.0D;
/*      */     }
/* 1733 */     for (i = 0; i < this.nCol; i++) {
/* 1734 */       result[i + i * this.nCol] = 1.0D;
/*      */     }
/* 1736 */     luBacksubstitution(m1.nRow, temp, row_perm, result);
/*      */     
/* 1738 */     for (i = 0; i < this.nRow; i++) {
/* 1739 */       for (int j = 0; j < this.nCol; j++) {
/* 1740 */         this.values[i][j] = result[i * this.nCol + j];
/*      */       }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean luDecomposition(int dim, double[] matrix0, int[] row_perm, int[] even_row_xchg) {
/* 1765 */     double[] row_scale = new double[dim];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1772 */     int ptr = 0;
/* 1773 */     int rs = 0;
/* 1774 */     even_row_xchg[0] = 1;
/*      */ 
/*      */     
/* 1777 */     int i = dim;
/* 1778 */     while (i-- != 0) {
/* 1779 */       double big = 0.0D;
/*      */ 
/*      */       
/* 1782 */       int k = dim;
/* 1783 */       while (k-- != 0) {
/* 1784 */         double temp = matrix0[ptr++];
/* 1785 */         temp = Math.abs(temp);
/* 1786 */         if (temp > big) {
/* 1787 */           big = temp;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/* 1792 */       if (big == 0.0D) {
/* 1793 */         return false;
/*      */       }
/* 1795 */       row_scale[rs++] = 1.0D / big;
/*      */     } 
/*      */ 
/*      */     
/* 1799 */     int mtx = 0;
/* 1800 */     for (int j = 0; j < dim; j++) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1806 */       for (i = 0; i < j; i++) {
/* 1807 */         int target = mtx + dim * i + j;
/* 1808 */         double sum = matrix0[target];
/* 1809 */         int k = i;
/* 1810 */         int p1 = mtx + dim * i;
/* 1811 */         int p2 = mtx + j;
/* 1812 */         while (k-- != 0) {
/* 1813 */           sum -= matrix0[p1] * matrix0[p2];
/* 1814 */           p1++;
/* 1815 */           p2 += dim;
/*      */         } 
/* 1817 */         matrix0[target] = sum;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1822 */       double big = 0.0D;
/* 1823 */       int imax = -1;
/* 1824 */       for (i = j; i < dim; i++) {
/* 1825 */         int target = mtx + dim * i + j;
/* 1826 */         double sum = matrix0[target];
/* 1827 */         int k = j;
/* 1828 */         int p1 = mtx + dim * i;
/* 1829 */         int p2 = mtx + j;
/* 1830 */         while (k-- != 0) {
/* 1831 */           sum -= matrix0[p1] * matrix0[p2];
/* 1832 */           p1++;
/* 1833 */           p2 += dim;
/*      */         } 
/* 1835 */         matrix0[target] = sum;
/*      */         
/*      */         double temp;
/* 1838 */         if ((temp = row_scale[i] * Math.abs(sum)) >= big) {
/* 1839 */           big = temp;
/* 1840 */           imax = i;
/*      */         } 
/*      */       } 
/*      */       
/* 1844 */       if (imax < 0) {
/* 1845 */         throw new RuntimeException(VecMathI18N.getString("GMatrix24"));
/*      */       }
/*      */ 
/*      */       
/* 1849 */       if (j != imax) {
/*      */         
/* 1851 */         int k = dim;
/* 1852 */         int p1 = mtx + dim * imax;
/* 1853 */         int p2 = mtx + dim * j;
/* 1854 */         while (k-- != 0) {
/* 1855 */           double temp = matrix0[p1];
/* 1856 */           matrix0[p1++] = matrix0[p2];
/* 1857 */           matrix0[p2++] = temp;
/*      */         } 
/*      */ 
/*      */         
/* 1861 */         row_scale[imax] = row_scale[j];
/* 1862 */         even_row_xchg[0] = -even_row_xchg[0];
/*      */       } 
/*      */ 
/*      */       
/* 1866 */       row_perm[j] = imax;
/*      */ 
/*      */       
/* 1869 */       if (matrix0[mtx + dim * j + j] == 0.0D) {
/* 1870 */         return false;
/*      */       }
/*      */ 
/*      */       
/* 1874 */       if (j != dim - 1) {
/* 1875 */         double temp = 1.0D / matrix0[mtx + dim * j + j];
/* 1876 */         int target = mtx + dim * (j + 1) + j;
/* 1877 */         i = dim - 1 - j;
/* 1878 */         while (i-- != 0) {
/* 1879 */           matrix0[target] = matrix0[target] * temp;
/* 1880 */           target += dim;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1886 */     return true;
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
/*      */   
/*      */   static void luBacksubstitution(int dim, double[] matrix1, int[] row_perm, double[] matrix2) {
/* 1917 */     int rp = 0;
/*      */ 
/*      */     
/* 1920 */     for (int k = 0; k < dim; k++) {
/*      */       
/* 1922 */       int cv = k;
/* 1923 */       int ii = -1;
/*      */       
/*      */       int i;
/* 1926 */       for (i = 0; i < dim; i++) {
/*      */ 
/*      */         
/* 1929 */         int ip = row_perm[rp + i];
/* 1930 */         double sum = matrix2[cv + dim * ip];
/* 1931 */         matrix2[cv + dim * ip] = matrix2[cv + dim * i];
/* 1932 */         if (ii >= 0) {
/*      */           
/* 1934 */           int rv = i * dim;
/* 1935 */           for (int j = ii; j <= i - 1; j++) {
/* 1936 */             sum -= matrix1[rv + j] * matrix2[cv + dim * j];
/*      */           }
/*      */         }
/* 1939 */         else if (sum != 0.0D) {
/* 1940 */           ii = i;
/*      */         } 
/* 1942 */         matrix2[cv + dim * i] = sum;
/*      */       } 
/*      */ 
/*      */       
/* 1946 */       for (i = 0; i < dim; i++) {
/* 1947 */         int ri = dim - 1 - i;
/* 1948 */         int rv = dim * ri;
/* 1949 */         double tt = 0.0D;
/* 1950 */         for (int j = 1; j <= i; j++) {
/* 1951 */           tt += matrix1[rv + dim - j] * matrix2[cv + dim * (dim - j)];
/*      */         }
/* 1953 */         matrix2[cv + dim * ri] = (matrix2[cv + dim * ri] - tt) / matrix1[rv + ri];
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int computeSVD(GMatrix mat, GMatrix U, GMatrix W, GMatrix V) {
/*      */     int eLength, sLength, vecLength;
/* 1966 */     GMatrix tmp = new GMatrix(mat.nRow, mat.nCol);
/* 1967 */     GMatrix u = new GMatrix(mat.nRow, mat.nCol);
/* 1968 */     GMatrix v = new GMatrix(mat.nRow, mat.nCol);
/* 1969 */     GMatrix m = new GMatrix(mat);
/*      */ 
/*      */     
/* 1972 */     if (m.nRow >= m.nCol) {
/* 1973 */       sLength = m.nCol;
/* 1974 */       eLength = m.nCol - 1;
/*      */     } else {
/* 1976 */       sLength = m.nRow;
/* 1977 */       eLength = m.nRow;
/*      */     } 
/*      */     
/* 1980 */     if (m.nRow > m.nCol) {
/* 1981 */       vecLength = m.nRow;
/*      */     } else {
/* 1983 */       vecLength = m.nCol;
/*      */     } 
/* 1985 */     double[] vec = new double[vecLength];
/* 1986 */     double[] single_values = new double[sLength];
/* 1987 */     double[] e = new double[eLength];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1993 */     int rank = 0;
/*      */     
/* 1995 */     U.setIdentity();
/* 1996 */     V.setIdentity();
/*      */     
/* 1998 */     int nr = m.nRow;
/* 1999 */     int nc = m.nCol;
/*      */ 
/*      */     
/* 2002 */     for (int si = 0; si < sLength; si++) {
/*      */ 
/*      */       
/* 2005 */       if (nr > 1) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2012 */         double mag = 0.0D; int k;
/* 2013 */         for (k = 0; k < nr; k++) {
/* 2014 */           mag += m.values[k + si][si] * m.values[k + si][si];
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2021 */         mag = Math.sqrt(mag);
/* 2022 */         if (m.values[si][si] == 0.0D) {
/* 2023 */           vec[0] = mag;
/*      */         } else {
/* 2025 */           vec[0] = m.values[si][si] + d_sign(mag, m.values[si][si]);
/*      */         } 
/*      */         
/* 2028 */         for (k = 1; k < nr; k++) {
/* 2029 */           vec[k] = m.values[si + k][si];
/*      */         }
/*      */         
/* 2032 */         double scale = 0.0D;
/* 2033 */         for (k = 0; k < nr; k++)
/*      */         {
/*      */ 
/*      */           
/* 2037 */           scale += vec[k] * vec[k];
/*      */         }
/*      */         
/* 2040 */         scale = 2.0D / scale;
/*      */         
/*      */         int j;
/*      */         
/* 2044 */         for (j = si; j < m.nRow; j++) {
/* 2045 */           for (int n = si; n < m.nRow; n++) {
/* 2046 */             u.values[j][n] = -scale * vec[j - si] * vec[n - si];
/*      */           }
/*      */         } 
/*      */         
/* 2050 */         for (k = si; k < m.nRow; k++) {
/* 2051 */           u.values[k][k] = u.values[k][k] + 1.0D;
/*      */         }
/*      */ 
/*      */         
/* 2055 */         double t = 0.0D;
/* 2056 */         for (k = si; k < m.nRow; k++) {
/* 2057 */           t += u.values[si][k] * m.values[k][si];
/*      */         }
/* 2059 */         m.values[si][si] = t;
/*      */ 
/*      */         
/* 2062 */         for (j = si; j < m.nRow; j++) {
/* 2063 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2064 */             tmp.values[j][n] = 0.0D;
/* 2065 */             for (k = si; k < m.nCol; k++) {
/* 2066 */               tmp.values[j][n] = tmp.values[j][n] + u.values[j][k] * m.values[k][n];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2071 */         for (j = si; j < m.nRow; j++) {
/* 2072 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2073 */             m.values[j][n] = tmp.values[j][n];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2083 */         for (j = si; j < m.nRow; j++) {
/* 2084 */           for (int n = 0; n < m.nCol; n++) {
/* 2085 */             tmp.values[j][n] = 0.0D;
/* 2086 */             for (k = si; k < m.nCol; k++) {
/* 2087 */               tmp.values[j][n] = tmp.values[j][n] + u.values[j][k] * U.values[k][n];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2092 */         for (j = si; j < m.nRow; j++) {
/* 2093 */           for (int n = 0; n < m.nCol; n++) {
/* 2094 */             U.values[j][n] = tmp.values[j][n];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2105 */         nr--;
/*      */       } 
/*      */       
/* 2108 */       if (nc > 2) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2114 */         double mag = 0.0D; int k;
/* 2115 */         for (k = 1; k < nc; k++) {
/* 2116 */           mag += m.values[si][si + k] * m.values[si][si + k];
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2124 */         mag = Math.sqrt(mag);
/* 2125 */         if (m.values[si][si + 1] == 0.0D) {
/* 2126 */           vec[0] = mag;
/*      */         } else {
/* 2128 */           vec[0] = m.values[si][si + 1] + 
/* 2129 */             d_sign(mag, m.values[si][si + 1]);
/*      */         } 
/*      */         
/* 2132 */         for (k = 1; k < nc - 1; k++) {
/* 2133 */           vec[k] = m.values[si][si + k + 1];
/*      */         }
/*      */ 
/*      */         
/* 2137 */         double scale = 0.0D;
/* 2138 */         for (k = 0; k < nc - 1; k++)
/*      */         {
/* 2140 */           scale += vec[k] * vec[k];
/*      */         }
/*      */         
/* 2143 */         scale = 2.0D / scale;
/*      */         
/*      */         int j;
/*      */         
/* 2147 */         for (j = si + 1; j < nc; j++) {
/* 2148 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2149 */             v.values[j][n] = -scale * vec[j - si - 1] * vec[n - si - 1];
/*      */           }
/*      */         } 
/*      */         
/* 2153 */         for (k = si + 1; k < m.nCol; k++) {
/* 2154 */           v.values[k][k] = v.values[k][k] + 1.0D;
/*      */         }
/*      */         
/* 2157 */         double t = 0.0D;
/* 2158 */         for (k = si; k < m.nCol; k++) {
/* 2159 */           t += v.values[k][si + 1] * m.values[si][k];
/*      */         }
/* 2161 */         m.values[si][si + 1] = t;
/*      */ 
/*      */         
/* 2164 */         for (j = si + 1; j < m.nRow; j++) {
/* 2165 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2166 */             tmp.values[j][n] = 0.0D;
/* 2167 */             for (k = si + 1; k < m.nCol; k++) {
/* 2168 */               tmp.values[j][n] = tmp.values[j][n] + v.values[k][n] * m.values[j][k];
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2173 */         for (j = si + 1; j < m.nRow; j++) {
/* 2174 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2175 */             m.values[j][n] = tmp.values[j][n];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2186 */         for (j = 0; j < m.nRow; j++) {
/* 2187 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2188 */             tmp.values[j][n] = 0.0D;
/* 2189 */             for (k = si + 1; k < m.nCol; k++) {
/* 2190 */               tmp.values[j][n] = tmp.values[j][n] + v.values[k][n] * V.values[j][k];
/*      */             }
/*      */           } 
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2198 */         for (j = 0; j < m.nRow; j++) {
/* 2199 */           for (int n = si + 1; n < m.nCol; n++) {
/* 2200 */             V.values[j][n] = tmp.values[j][n];
/*      */           }
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 2209 */         nc--;
/*      */       } 
/*      */     } 
/*      */     int i;
/* 2213 */     for (i = 0; i < sLength; i++) {
/* 2214 */       single_values[i] = m.values[i][i];
/*      */     }
/*      */     
/* 2217 */     for (i = 0; i < eLength; i++) {
/* 2218 */       e[i] = m.values[i][i + 1];
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2231 */     if (m.nRow == 2 && m.nCol == 2) {
/* 2232 */       double[] cosl = new double[1];
/* 2233 */       double[] cosr = new double[1];
/* 2234 */       double[] sinl = new double[1];
/* 2235 */       double[] sinr = new double[1];
/*      */       
/* 2237 */       compute_2X2(single_values[0], e[0], single_values[1], 
/* 2238 */           single_values, sinl, cosl, sinr, cosr, 0);
/*      */       
/* 2240 */       update_u(0, U, cosl, sinl);
/* 2241 */       update_v(0, V, cosr, sinr);
/*      */       
/* 2243 */       return 2;
/*      */     } 
/*      */ 
/*      */     
/* 2247 */     compute_qr(0, e.length - 1, single_values, e, U, V);
/*      */ 
/*      */     
/* 2250 */     rank = single_values.length;
/*      */ 
/*      */ 
/*      */     
/* 2254 */     return rank;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void compute_qr(int start, int end, double[] s, double[] e, GMatrix u, GMatrix v) {
/* 2263 */     double[] cosl = new double[1];
/* 2264 */     double[] cosr = new double[1];
/* 2265 */     double[] sinl = new double[1];
/* 2266 */     double[] sinr = new double[1];
/* 2267 */     GMatrix m = new GMatrix(u.nCol, v.nRow);
/*      */     
/* 2269 */     int MAX_INTERATIONS = 2;
/* 2270 */     double CONVERGE_TOL = 4.89E-15D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2294 */     double c_b48 = 1.0D;
/* 2295 */     double c_b71 = -1.0D;
/* 2296 */     boolean converged = false;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2301 */     double f = 0.0D;
/* 2302 */     double g = 0.0D;
/*      */     
/* 2304 */     for (int k = 0; k < 2 && !converged; k++) {
/* 2305 */       int j; for (j = start; j <= end; j++) {
/*      */ 
/*      */         
/* 2308 */         if (j == start) {
/* 2309 */           int sl; if (e.length == s.length) {
/* 2310 */             sl = end;
/*      */           } else {
/* 2312 */             sl = end + 1;
/*      */           } 
/* 2314 */           double shift = compute_shift(s[sl - 1], e[end], s[sl]);
/*      */           
/* 2316 */           f = (Math.abs(s[j]) - shift) * (
/* 2317 */             d_sign(c_b48, s[j]) + shift / s[j]);
/* 2318 */           g = e[j];
/*      */         } 
/*      */         
/* 2321 */         double r = compute_rot(f, g, sinr, cosr);
/* 2322 */         if (j != start) {
/* 2323 */           e[j - 1] = r;
/*      */         }
/* 2325 */         f = cosr[0] * s[j] + sinr[0] * e[j];
/* 2326 */         e[j] = cosr[0] * e[j] - sinr[0] * s[j];
/* 2327 */         g = sinr[0] * s[j + 1];
/* 2328 */         s[j + 1] = cosr[0] * s[j + 1];
/*      */ 
/*      */         
/* 2331 */         update_v(j, v, cosr, sinr);
/*      */ 
/*      */ 
/*      */         
/* 2335 */         r = compute_rot(f, g, sinl, cosl);
/* 2336 */         s[j] = r;
/* 2337 */         f = cosl[0] * e[j] + sinl[0] * s[j + 1];
/* 2338 */         s[j + 1] = cosl[0] * s[j + 1] - sinl[0] * e[j];
/*      */         
/* 2340 */         if (j < end) {
/*      */           
/* 2342 */           g = sinl[0] * e[j + 1];
/* 2343 */           e[j + 1] = cosl[0] * e[j + 1];
/*      */         } 
/*      */ 
/*      */         
/* 2347 */         update_u(j, u, cosl, sinl);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2353 */       if (s.length == e.length) {
/* 2354 */         double r = compute_rot(f, g, sinr, cosr);
/* 2355 */         f = cosr[0] * s[j] + sinr[0] * e[j];
/* 2356 */         e[j] = cosr[0] * e[j] - sinr[0] * s[j];
/* 2357 */         s[j + 1] = cosr[0] * s[j + 1];
/*      */         
/* 2359 */         update_v(j, v, cosr, sinr);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2372 */       while (end - start > 1 && Math.abs(e[end]) < 4.89E-15D) {
/* 2373 */         end--;
/*      */       }
/*      */ 
/*      */       
/* 2377 */       for (int n = end - 2; n > start; n--) {
/* 2378 */         if (Math.abs(e[n]) < 4.89E-15D) {
/* 2379 */           compute_qr(n + 1, end, s, e, u, v);
/* 2380 */           end = n - 1;
/*      */ 
/*      */           
/* 2383 */           while (end - start > 1 && 
/* 2384 */             Math.abs(e[end]) < 4.89E-15D) {
/* 2385 */             end--;
/*      */           }
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2393 */       if (end - start <= 1 && Math.abs(e[start + 1]) < 4.89E-15D) {
/* 2394 */         converged = true;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2404 */     if (Math.abs(e[1]) < 4.89E-15D) {
/* 2405 */       compute_2X2(s[start], e[start], s[start + 1], s, 
/* 2406 */           sinl, cosl, sinr, cosr, 0);
/* 2407 */       e[start] = 0.0D;
/* 2408 */       e[start + 1] = 0.0D;
/*      */     } 
/*      */ 
/*      */     
/* 2412 */     int i = start;
/* 2413 */     update_u(i, u, cosl, sinl);
/* 2414 */     update_v(i, v, cosr, sinr);
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
/*      */   private static void print_se(double[] s, double[] e) {
/* 2426 */     System.out.println("\ns =" + s[0] + " " + s[1] + " " + s[2]);
/* 2427 */     System.out.println("e =" + e[0] + " " + e[1]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_v(int index, GMatrix v, double[] cosr, double[] sinr) {
/* 2435 */     for (int j = 0; j < v.nRow; j++) {
/* 2436 */       double vtemp = v.values[j][index];
/* 2437 */       v.values[j][index] = 
/* 2438 */         cosr[0] * vtemp + sinr[0] * v.values[j][index + 1];
/* 2439 */       v.values[j][index + 1] = 
/* 2440 */         -sinr[0] * vtemp + cosr[0] * v.values[j][index + 1];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void chase_up(double[] s, double[] e, int k, GMatrix v) {
/* 2446 */     double[] cosr = new double[1];
/* 2447 */     double[] sinr = new double[1];
/*      */     
/* 2449 */     GMatrix t = new GMatrix(v.nRow, v.nCol);
/* 2450 */     GMatrix m = new GMatrix(v.nRow, v.nCol);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2462 */     double f = e[k];
/* 2463 */     double g = s[k];
/*      */     int i;
/* 2465 */     for (i = k; i > 0; i--) {
/* 2466 */       double r = compute_rot(f, g, sinr, cosr);
/* 2467 */       f = -e[i - 1] * sinr[0];
/* 2468 */       g = s[i - 1];
/* 2469 */       s[i] = r;
/* 2470 */       e[i - 1] = e[i - 1] * cosr[0];
/* 2471 */       update_v_split(i, k + 1, v, cosr, sinr, t, m);
/*      */     } 
/*      */     
/* 2474 */     s[i + 1] = compute_rot(f, g, sinr, cosr);
/* 2475 */     update_v_split(i, k + 1, v, cosr, sinr, t, m);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void chase_across(double[] s, double[] e, int k, GMatrix u) {
/* 2480 */     double[] cosl = new double[1];
/* 2481 */     double[] sinl = new double[1];
/*      */     
/* 2483 */     GMatrix t = new GMatrix(u.nRow, u.nCol);
/* 2484 */     GMatrix m = new GMatrix(u.nRow, u.nCol);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2496 */     double g = e[k];
/* 2497 */     double f = s[k + 1];
/*      */     int i;
/* 2499 */     for (i = k; i < u.nCol - 2; i++) {
/* 2500 */       double r = compute_rot(f, g, sinl, cosl);
/* 2501 */       g = -e[i + 1] * sinl[0];
/* 2502 */       f = s[i + 2];
/* 2503 */       s[i + 1] = r;
/* 2504 */       e[i + 1] = e[i + 1] * cosl[0];
/* 2505 */       update_u_split(k, i + 1, u, cosl, sinl, t, m);
/*      */     } 
/*      */     
/* 2508 */     s[i + 1] = compute_rot(f, g, sinl, cosl);
/* 2509 */     update_u_split(k, i + 1, u, cosl, sinl, t, m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_v_split(int topr, int bottomr, GMatrix v, double[] cosr, double[] sinr, GMatrix t, GMatrix m) {
/* 2518 */     for (int j = 0; j < v.nRow; j++) {
/* 2519 */       double vtemp = v.values[j][topr];
/* 2520 */       v.values[j][topr] = cosr[0] * vtemp - sinr[0] * v.values[j][bottomr];
/* 2521 */       v.values[j][bottomr] = sinr[0] * vtemp + cosr[0] * v.values[j][bottomr];
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2535 */     System.out.println("topr    =" + topr);
/* 2536 */     System.out.println("bottomr =" + bottomr);
/* 2537 */     System.out.println("cosr =" + cosr[0]);
/* 2538 */     System.out.println("sinr =" + sinr[0]);
/* 2539 */     System.out.println("\nm =");
/* 2540 */     checkMatrix(m);
/* 2541 */     System.out.println("\nv =");
/* 2542 */     checkMatrix(t);
/* 2543 */     m.mul(m, t);
/* 2544 */     System.out.println("\nt*m =");
/* 2545 */     checkMatrix(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_u_split(int topr, int bottomr, GMatrix u, double[] cosl, double[] sinl, GMatrix t, GMatrix m) {
/* 2554 */     for (int j = 0; j < u.nCol; j++) {
/* 2555 */       double utemp = u.values[topr][j];
/* 2556 */       u.values[topr][j] = cosl[0] * utemp - sinl[0] * u.values[bottomr][j];
/* 2557 */       u.values[bottomr][j] = sinl[0] * utemp + cosl[0] * u.values[bottomr][j];
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2570 */     System.out.println("\nm=");
/* 2571 */     checkMatrix(m);
/* 2572 */     System.out.println("\nu=");
/* 2573 */     checkMatrix(t);
/* 2574 */     m.mul(t, m);
/* 2575 */     System.out.println("\nt*m=");
/* 2576 */     checkMatrix(m);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void update_u(int index, GMatrix u, double[] cosl, double[] sinl) {
/* 2584 */     for (int j = 0; j < u.nCol; j++) {
/* 2585 */       double utemp = u.values[index][j];
/* 2586 */       u.values[index][j] = 
/* 2587 */         cosl[0] * utemp + sinl[0] * u.values[index + 1][j];
/* 2588 */       u.values[index + 1][j] = 
/* 2589 */         -sinl[0] * utemp + cosl[0] * u.values[index + 1][j];
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void print_m(GMatrix m, GMatrix u, GMatrix v) {
/* 2594 */     GMatrix mtmp = new GMatrix(m.nCol, m.nRow);
/*      */     
/* 2596 */     mtmp.mul(u, mtmp);
/* 2597 */     mtmp.mul(mtmp, v);
/* 2598 */     System.out.println("\n m = \n" + toString(mtmp));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String toString(GMatrix m) {
/* 2604 */     StringBuffer buffer = new StringBuffer(m.nRow * m.nCol * 8);
/*      */ 
/*      */     
/* 2607 */     for (int i = 0; i < m.nRow; i++) {
/* 2608 */       for (int j = 0; j < m.nCol; j++) {
/* 2609 */         if (Math.abs(m.values[i][j]) < 1.0E-9D) {
/* 2610 */           buffer.append("0.0000 ");
/*      */         } else {
/* 2612 */           buffer.append(m.values[i][j]).append(" ");
/*      */         } 
/*      */       } 
/* 2615 */       buffer.append("\n");
/*      */     } 
/* 2617 */     return buffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void print_svd(double[] s, double[] e, GMatrix u, GMatrix v) {
/* 2623 */     GMatrix mtmp = new GMatrix(u.nCol, v.nRow);
/*      */     
/* 2625 */     System.out.println(" \ns = "); int i;
/* 2626 */     for (i = 0; i < s.length; i++) {
/* 2627 */       System.out.println(" " + s[i]);
/*      */     }
/*      */     
/* 2630 */     System.out.println(" \ne = ");
/* 2631 */     for (i = 0; i < e.length; i++) {
/* 2632 */       System.out.println(" " + e[i]);
/*      */     }
/*      */     
/* 2635 */     System.out.println(" \nu  = \n" + u.toString());
/* 2636 */     System.out.println(" \nv  = \n" + v.toString());
/*      */     
/* 2638 */     mtmp.setIdentity();
/* 2639 */     for (i = 0; i < s.length; i++) {
/* 2640 */       mtmp.values[i][i] = s[i];
/*      */     }
/* 2642 */     for (i = 0; i < e.length; i++) {
/* 2643 */       mtmp.values[i][i + 1] = e[i];
/*      */     }
/* 2645 */     System.out.println(" \nm  = \n" + mtmp.toString());
/*      */     
/* 2647 */     mtmp.mulTransposeLeft(u, mtmp);
/* 2648 */     mtmp.mulTransposeRight(mtmp, v);
/*      */     
/* 2650 */     System.out.println(" \n u.transpose*m*v.transpose  = \n" + 
/* 2651 */         mtmp.toString());
/*      */   }
/*      */   
/*      */   static double max(double a, double b) {
/* 2655 */     if (a > b) {
/* 2656 */       return a;
/*      */     }
/* 2658 */     return b;
/*      */   }
/*      */   
/*      */   static double min(double a, double b) {
/* 2662 */     if (a < b) {
/* 2663 */       return a;
/*      */     }
/* 2665 */     return b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static double compute_shift(double f, double g, double h) {
/* 2673 */     double ssmin, fa = Math.abs(f);
/* 2674 */     double ga = Math.abs(g);
/* 2675 */     double ha = Math.abs(h);
/* 2676 */     double fhmn = min(fa, ha);
/* 2677 */     double fhmx = max(fa, ha);
/*      */     
/* 2679 */     if (fhmn == 0.0D) {
/* 2680 */       ssmin = 0.0D;
/* 2681 */       if (fhmx != 0.0D)
/*      */       {
/* 2683 */         double d = min(fhmx, ga) / max(fhmx, ga);
/*      */       }
/*      */     }
/* 2686 */     else if (ga < fhmx) {
/* 2687 */       double as = fhmn / fhmx + 1.0D;
/* 2688 */       double at = (fhmx - fhmn) / fhmx;
/* 2689 */       double d__1 = ga / fhmx;
/* 2690 */       double au = d__1 * d__1;
/* 2691 */       double c = 2.0D / (Math.sqrt(as * as + au) + Math.sqrt(at * at + au));
/* 2692 */       ssmin = fhmn * c;
/*      */     } else {
/* 2694 */       double au = fhmx / ga;
/* 2695 */       if (au == 0.0D) {
/* 2696 */         ssmin = fhmn * fhmx / ga;
/*      */       } else {
/* 2698 */         double as = fhmn / fhmx + 1.0D;
/* 2699 */         double at = (fhmx - fhmn) / fhmx;
/* 2700 */         double d__1 = as * au;
/* 2701 */         double d__2 = at * au;
/* 2702 */         double c = 1.0D / (Math.sqrt(d__1 * d__1 + 1.0D) + 
/* 2703 */           Math.sqrt(d__2 * d__2 + 1.0D));
/* 2704 */         ssmin = fhmn * c * au;
/* 2705 */         ssmin += ssmin;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2710 */     return ssmin;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static int compute_2X2(double f, double g, double h, double[] single_values, double[] snl, double[] csl, double[] snr, double[] csr, int index) {
/*      */     boolean swap;
/* 2717 */     double c_b3 = 2.0D;
/* 2718 */     double c_b4 = 1.0D;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2730 */     double ssmax = single_values[0];
/* 2731 */     double ssmin = single_values[1];
/* 2732 */     double clt = 0.0D;
/* 2733 */     double crt = 0.0D;
/* 2734 */     double slt = 0.0D;
/* 2735 */     double srt = 0.0D;
/* 2736 */     double tsign = 0.0D;
/*      */     
/* 2738 */     double ft = f;
/* 2739 */     double fa = Math.abs(ft);
/* 2740 */     double ht = h;
/* 2741 */     double ha = Math.abs(h);
/*      */     
/* 2743 */     int pmax = 1;
/* 2744 */     if (ha > fa) {
/* 2745 */       swap = true;
/*      */     } else {
/* 2747 */       swap = false;
/*      */     } 
/* 2749 */     if (swap) {
/* 2750 */       pmax = 3;
/* 2751 */       double temp = ft;
/* 2752 */       ft = ht;
/* 2753 */       ht = temp;
/* 2754 */       temp = fa;
/* 2755 */       fa = ha;
/* 2756 */       ha = temp;
/*      */     } 
/*      */ 
/*      */     
/* 2760 */     double gt = g;
/* 2761 */     double ga = Math.abs(gt);
/* 2762 */     if (ga == 0.0D) {
/* 2763 */       single_values[1] = ha;
/* 2764 */       single_values[0] = fa;
/* 2765 */       clt = 1.0D;
/* 2766 */       crt = 1.0D;
/* 2767 */       slt = 0.0D;
/* 2768 */       srt = 0.0D;
/*      */     } else {
/* 2770 */       boolean gasmal = true;
/* 2771 */       if (ga > fa) {
/* 2772 */         pmax = 2;
/* 2773 */         if (fa / ga < 1.0E-10D) {
/* 2774 */           gasmal = false;
/* 2775 */           ssmax = ga;
/*      */           
/* 2777 */           if (ha > 1.0D) {
/* 2778 */             ssmin = fa / ga / ha;
/*      */           } else {
/* 2780 */             ssmin = fa / ga * ha;
/*      */           } 
/* 2782 */           clt = 1.0D;
/* 2783 */           slt = ht / gt;
/* 2784 */           srt = 1.0D;
/* 2785 */           crt = ft / gt;
/*      */         } 
/*      */       } 
/* 2788 */       if (gasmal) {
/* 2789 */         double l, r, d = fa - ha;
/* 2790 */         if (d == fa) {
/*      */           
/* 2792 */           l = 1.0D;
/*      */         } else {
/* 2794 */           l = d / fa;
/*      */         } 
/*      */         
/* 2797 */         double m = gt / ft;
/* 2798 */         double t = 2.0D - l;
/* 2799 */         double mm = m * m;
/* 2800 */         double tt = t * t;
/* 2801 */         double s = Math.sqrt(tt + mm);
/*      */         
/* 2803 */         if (l == 0.0D) {
/* 2804 */           r = Math.abs(m);
/*      */         } else {
/* 2806 */           r = Math.sqrt(l * l + mm);
/*      */         } 
/*      */         
/* 2809 */         double a = (s + r) * 0.5D;
/* 2810 */         if (ga > fa) {
/* 2811 */           pmax = 2;
/* 2812 */           if (fa / ga < 1.0E-10D) {
/* 2813 */             gasmal = false;
/* 2814 */             ssmax = ga;
/* 2815 */             if (ha > 1.0D) {
/* 2816 */               ssmin = fa / ga / ha;
/*      */             } else {
/* 2818 */               ssmin = fa / ga * ha;
/*      */             } 
/* 2820 */             clt = 1.0D;
/* 2821 */             slt = ht / gt;
/* 2822 */             srt = 1.0D;
/* 2823 */             crt = ft / gt;
/*      */           } 
/*      */         } 
/* 2826 */         if (gasmal) {
/* 2827 */           d = fa - ha;
/* 2828 */           if (d == fa) {
/* 2829 */             l = 1.0D;
/*      */           } else {
/* 2831 */             l = d / fa;
/*      */           } 
/*      */           
/* 2834 */           m = gt / ft;
/* 2835 */           t = 2.0D - l;
/*      */           
/* 2837 */           mm = m * m;
/* 2838 */           tt = t * t;
/* 2839 */           s = Math.sqrt(tt + mm);
/*      */           
/* 2841 */           if (l == 0.0D) {
/* 2842 */             r = Math.abs(m);
/*      */           } else {
/* 2844 */             r = Math.sqrt(l * l + mm);
/*      */           } 
/*      */           
/* 2847 */           a = (s + r) * 0.5D;
/* 2848 */           ssmin = ha / a;
/* 2849 */           ssmax = fa * a;
/*      */           
/* 2851 */           if (mm == 0.0D) {
/* 2852 */             if (l == 0.0D) {
/* 2853 */               t = d_sign(c_b3, ft) * d_sign(c_b4, gt);
/*      */             } else {
/* 2855 */               t = gt / d_sign(d, ft) + m / t;
/*      */             } 
/*      */           } else {
/* 2858 */             t = (m / (s + t) + m / (r + l)) * (a + 1.0D);
/*      */           } 
/*      */           
/* 2861 */           l = Math.sqrt(t * t + 4.0D);
/* 2862 */           crt = 2.0D / l;
/* 2863 */           srt = t / l;
/* 2864 */           clt = (crt + srt * m) / a;
/* 2865 */           slt = ht / ft * srt / a;
/*      */         } 
/*      */       } 
/* 2868 */       if (swap) {
/* 2869 */         csl[0] = srt;
/* 2870 */         snl[0] = crt;
/* 2871 */         csr[0] = slt;
/* 2872 */         snr[0] = clt;
/*      */       } else {
/* 2874 */         csl[0] = clt;
/* 2875 */         snl[0] = slt;
/* 2876 */         csr[0] = crt;
/* 2877 */         snr[0] = srt;
/*      */       } 
/*      */       
/* 2880 */       if (pmax == 1) {
/* 2881 */         tsign = d_sign(c_b4, csr[0]) * 
/* 2882 */           d_sign(c_b4, csl[0]) * d_sign(c_b4, f);
/*      */       }
/* 2884 */       if (pmax == 2) {
/* 2885 */         tsign = d_sign(c_b4, snr[0]) * 
/* 2886 */           d_sign(c_b4, csl[0]) * d_sign(c_b4, g);
/*      */       }
/* 2888 */       if (pmax == 3) {
/* 2889 */         tsign = d_sign(c_b4, snr[0]) * 
/* 2890 */           d_sign(c_b4, snl[0]) * d_sign(c_b4, h);
/*      */       }
/*      */       
/* 2893 */       single_values[index] = d_sign(ssmax, tsign);
/* 2894 */       double d__1 = tsign * d_sign(c_b4, f) * d_sign(c_b4, h);
/* 2895 */       single_values[index + 1] = d_sign(ssmin, d__1);
/*      */     } 
/*      */     
/* 2898 */     return 0;
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
/*      */   static double compute_rot(double f, double g, double[] sin, double[] cos) {
/* 2910 */     double cs, sn, r, safmn2 = 2.002083095183101E-146D;
/* 2911 */     double safmx2 = 4.9947976805055876E145D;
/*      */     
/* 2913 */     if (g == 0.0D) {
/* 2914 */       cs = 1.0D;
/* 2915 */       sn = 0.0D;
/* 2916 */       r = f;
/* 2917 */     } else if (f == 0.0D) {
/* 2918 */       cs = 0.0D;
/* 2919 */       sn = 1.0D;
/* 2920 */       r = g;
/*      */     } else {
/* 2922 */       double f1 = f;
/* 2923 */       double g1 = g;
/* 2924 */       double scale = max(Math.abs(f1), Math.abs(g1));
/* 2925 */       if (scale >= 4.9947976805055876E145D) {
/* 2926 */         int count = 0;
/* 2927 */         while (scale >= 4.9947976805055876E145D) {
/* 2928 */           count++;
/* 2929 */           f1 *= 2.002083095183101E-146D;
/* 2930 */           g1 *= 2.002083095183101E-146D;
/* 2931 */           scale = max(Math.abs(f1), Math.abs(g1));
/*      */         } 
/* 2933 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 2934 */         cs = f1 / r;
/* 2935 */         sn = g1 / r;
/* 2936 */         int i__1 = count;
/* 2937 */         for (int i = 1; i <= count; i++) {
/* 2938 */           r *= 4.9947976805055876E145D;
/*      */         }
/* 2940 */       } else if (scale <= 2.002083095183101E-146D) {
/* 2941 */         int count = 0;
/* 2942 */         while (scale <= 2.002083095183101E-146D) {
/* 2943 */           count++;
/* 2944 */           f1 *= 4.9947976805055876E145D;
/* 2945 */           g1 *= 4.9947976805055876E145D;
/* 2946 */           scale = max(Math.abs(f1), Math.abs(g1));
/*      */         } 
/* 2948 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 2949 */         cs = f1 / r;
/* 2950 */         sn = g1 / r;
/* 2951 */         int i__1 = count;
/* 2952 */         for (int i = 1; i <= count; i++) {
/* 2953 */           r *= 2.002083095183101E-146D;
/*      */         }
/*      */       } else {
/* 2956 */         r = Math.sqrt(f1 * f1 + g1 * g1);
/* 2957 */         cs = f1 / r;
/* 2958 */         sn = g1 / r;
/*      */       } 
/* 2960 */       if (Math.abs(f) > Math.abs(g) && cs < 0.0D) {
/* 2961 */         cs = -cs;
/* 2962 */         sn = -sn;
/* 2963 */         r = -r;
/*      */       } 
/*      */     } 
/* 2966 */     sin[0] = sn;
/* 2967 */     cos[0] = cs;
/* 2968 */     return r;
/*      */   }
/*      */ 
/*      */   
/*      */   static double d_sign(double a, double b) {
/* 2973 */     double x = (a >= 0.0D) ? a : -a;
/* 2974 */     return (b >= 0.0D) ? x : -x;
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
/*      */   public Object clone() {
/* 2987 */     GMatrix m1 = null;
/*      */     try {
/* 2989 */       m1 = (GMatrix)super.clone();
/* 2990 */     } catch (CloneNotSupportedException e) {
/*      */       
/* 2992 */       throw new InternalError();
/*      */     } 
/*      */ 
/*      */     
/* 2996 */     m1.values = new double[this.nRow][this.nCol];
/* 2997 */     for (int i = 0; i < this.nRow; i++) {
/* 2998 */       for (int j = 0; j < this.nCol; j++) {
/* 2999 */         m1.values[i][j] = this.values[i][j];
/*      */       }
/*      */     } 
/*      */     
/* 3003 */     return m1;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\javax\vecmath\GMatrix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */