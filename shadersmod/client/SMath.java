/*    */ package shadersmod.client;
/*    */ 
/*    */ import java.nio.FloatBuffer;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ public class SMath
/*    */ {
/*    */   static void multiplyMat4xMat4(float[] matOut, float[] matA, float[] matB) {
/* 10 */     for (int i = 0; i < 4; i++) {
/*    */       
/* 12 */       for (int j = 0; j < 4; j++)
/*    */       {
/* 14 */         matOut[4 * i + j] = matA[4 * i + 0] * matB[0 + j] + matA[4 * i + 1] * matB[4 + j] + matA[4 * i + 2] * matB[8 + j] + matA[4 * i + 3] * matB[12 + j];
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   static void multiplyMat4xVec4(float[] vecOut, float[] matA, float[] vecB) {
/* 21 */     vecOut[0] = matA[0] * vecB[0] + matA[4] * vecB[1] + matA[8] * vecB[2] + matA[12] * vecB[3];
/* 22 */     vecOut[1] = matA[1] * vecB[0] + matA[5] * vecB[1] + matA[9] * vecB[2] + matA[13] * vecB[3];
/* 23 */     vecOut[2] = matA[2] * vecB[0] + matA[6] * vecB[1] + matA[10] * vecB[2] + matA[14] * vecB[3];
/* 24 */     vecOut[3] = matA[3] * vecB[0] + matA[7] * vecB[1] + matA[11] * vecB[2] + matA[15] * vecB[3];
/*    */   }
/*    */ 
/*    */   
/*    */   static void invertMat4(float[] matOut, float[] m) {
/* 29 */     matOut[0] = m[5] * m[10] * m[15] - m[5] * m[11] * m[14] - m[9] * m[6] * m[15] + m[9] * m[7] * m[14] + m[13] * m[6] * m[11] - m[13] * m[7] * m[10];
/* 30 */     matOut[1] = -m[1] * m[10] * m[15] + m[1] * m[11] * m[14] + m[9] * m[2] * m[15] - m[9] * m[3] * m[14] - m[13] * m[2] * m[11] + m[13] * m[3] * m[10];
/* 31 */     matOut[2] = m[1] * m[6] * m[15] - m[1] * m[7] * m[14] - m[5] * m[2] * m[15] + m[5] * m[3] * m[14] + m[13] * m[2] * m[7] - m[13] * m[3] * m[6];
/* 32 */     matOut[3] = -m[1] * m[6] * m[11] + m[1] * m[7] * m[10] + m[5] * m[2] * m[11] - m[5] * m[3] * m[10] - m[9] * m[2] * m[7] + m[9] * m[3] * m[6];
/* 33 */     matOut[4] = -m[4] * m[10] * m[15] + m[4] * m[11] * m[14] + m[8] * m[6] * m[15] - m[8] * m[7] * m[14] - m[12] * m[6] * m[11] + m[12] * m[7] * m[10];
/* 34 */     matOut[5] = m[0] * m[10] * m[15] - m[0] * m[11] * m[14] - m[8] * m[2] * m[15] + m[8] * m[3] * m[14] + m[12] * m[2] * m[11] - m[12] * m[3] * m[10];
/* 35 */     matOut[6] = -m[0] * m[6] * m[15] + m[0] * m[7] * m[14] + m[4] * m[2] * m[15] - m[4] * m[3] * m[14] - m[12] * m[2] * m[7] + m[12] * m[3] * m[6];
/* 36 */     matOut[7] = m[0] * m[6] * m[11] - m[0] * m[7] * m[10] - m[4] * m[2] * m[11] + m[4] * m[3] * m[10] + m[8] * m[2] * m[7] - m[8] * m[3] * m[6];
/* 37 */     matOut[8] = m[4] * m[9] * m[15] - m[4] * m[11] * m[13] - m[8] * m[5] * m[15] + m[8] * m[7] * m[13] + m[12] * m[5] * m[11] - m[12] * m[7] * m[9];
/* 38 */     matOut[9] = -m[0] * m[9] * m[15] + m[0] * m[11] * m[13] + m[8] * m[1] * m[15] - m[8] * m[3] * m[13] - m[12] * m[1] * m[11] + m[12] * m[3] * m[9];
/* 39 */     matOut[10] = m[0] * m[5] * m[15] - m[0] * m[7] * m[13] - m[4] * m[1] * m[15] + m[4] * m[3] * m[13] + m[12] * m[1] * m[7] - m[12] * m[3] * m[5];
/* 40 */     matOut[11] = -m[0] * m[5] * m[11] + m[0] * m[7] * m[9] + m[4] * m[1] * m[11] - m[4] * m[3] * m[9] - m[8] * m[1] * m[7] + m[8] * m[3] * m[5];
/* 41 */     matOut[12] = -m[4] * m[9] * m[14] + m[4] * m[10] * m[13] + m[8] * m[5] * m[14] - m[8] * m[6] * m[13] - m[12] * m[5] * m[10] + m[12] * m[6] * m[9];
/* 42 */     matOut[13] = m[0] * m[9] * m[14] - m[0] * m[10] * m[13] - m[8] * m[1] * m[14] + m[8] * m[2] * m[13] + m[12] * m[1] * m[10] - m[12] * m[2] * m[9];
/* 43 */     matOut[14] = -m[0] * m[5] * m[14] + m[0] * m[6] * m[13] + m[4] * m[1] * m[14] - m[4] * m[2] * m[13] - m[12] * m[1] * m[6] + m[12] * m[2] * m[5];
/* 44 */     matOut[15] = m[0] * m[5] * m[10] - m[0] * m[6] * m[9] - m[4] * m[1] * m[10] + m[4] * m[2] * m[9] + m[8] * m[1] * m[6] - m[8] * m[2] * m[5];
/* 45 */     float f = m[0] * matOut[0] + m[1] * matOut[4] + m[2] * matOut[8] + m[3] * matOut[12];
/*    */     
/* 47 */     if (f != 0.0D) {
/*    */       
/* 49 */       for (int i = 0; i < 16; i++)
/*    */       {
/* 51 */         matOut[i] = matOut[i] / f;
/*    */       }
/*    */     }
/*    */     else {
/*    */       
/* 56 */       Arrays.fill(matOut, 0.0F);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   static void invertMat4FBFA(FloatBuffer fbInvOut, FloatBuffer fbMatIn, float[] faInv, float[] faMat) {
/* 62 */     fbMatIn.get(faMat);
/* 63 */     invertMat4(faInv, faMat);
/* 64 */     fbInvOut.put(faInv);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\SMath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */