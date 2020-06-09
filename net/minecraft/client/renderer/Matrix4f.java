/*    */ package net.minecraft.client.renderer;
/*    */ 
/*    */ import org.lwjgl.util.vector.Matrix4f;
/*    */ 
/*    */ public class Matrix4f extends Matrix4f {
/*    */   public Matrix4f(float[] matrix) {
/*  7 */     this.m00 = matrix[0];
/*  8 */     this.m01 = matrix[1];
/*  9 */     this.m02 = matrix[2];
/* 10 */     this.m03 = matrix[3];
/* 11 */     this.m10 = matrix[4];
/* 12 */     this.m11 = matrix[5];
/* 13 */     this.m12 = matrix[6];
/* 14 */     this.m13 = matrix[7];
/* 15 */     this.m20 = matrix[8];
/* 16 */     this.m21 = matrix[9];
/* 17 */     this.m22 = matrix[10];
/* 18 */     this.m23 = matrix[11];
/* 19 */     this.m30 = matrix[12];
/* 20 */     this.m31 = matrix[13];
/* 21 */     this.m32 = matrix[14];
/* 22 */     this.m33 = matrix[15];
/*    */   }
/*    */ 
/*    */   
/*    */   public Matrix4f() {
/* 27 */     this.m00 = 0.0F;
/* 28 */     this.m01 = 0.0F;
/* 29 */     this.m02 = 0.0F;
/* 30 */     this.m03 = 0.0F;
/* 31 */     this.m10 = 0.0F;
/* 32 */     this.m11 = 0.0F;
/* 33 */     this.m12 = 0.0F;
/* 34 */     this.m13 = 0.0F;
/* 35 */     this.m20 = 0.0F;
/* 36 */     this.m21 = 0.0F;
/* 37 */     this.m22 = 0.0F;
/* 38 */     this.m23 = 0.0F;
/* 39 */     this.m30 = 0.0F;
/* 40 */     this.m31 = 0.0F;
/* 41 */     this.m32 = 0.0F;
/* 42 */     this.m33 = 0.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\Matrix4f.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */