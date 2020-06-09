/*    */ package net.minecraft.client.renderer.culling;
/*    */ 
/*    */ public class ClippingHelper
/*    */ {
/*  5 */   public float[][] frustum = new float[6][4];
/*  6 */   public float[] projectionMatrix = new float[16];
/*  7 */   public float[] modelviewMatrix = new float[16];
/*  8 */   public float[] clippingMatrix = new float[16];
/*    */ 
/*    */   
/*    */   private float dot(float[] p_dot_1_, float p_dot_2_, float p_dot_3_, float p_dot_4_) {
/* 12 */     return p_dot_1_[0] * p_dot_2_ + p_dot_1_[1] * p_dot_3_ + p_dot_1_[2] * p_dot_4_ + p_dot_1_[3];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isBoxInFrustum(double p_78553_1_, double p_78553_3_, double p_78553_5_, double p_78553_7_, double p_78553_9_, double p_78553_11_) {
/* 20 */     float f = (float)p_78553_1_;
/* 21 */     float f1 = (float)p_78553_3_;
/* 22 */     float f2 = (float)p_78553_5_;
/* 23 */     float f3 = (float)p_78553_7_;
/* 24 */     float f4 = (float)p_78553_9_;
/* 25 */     float f5 = (float)p_78553_11_;
/*    */     
/* 27 */     for (int i = 0; i < 6; i++) {
/*    */       
/* 29 */       float[] afloat = this.frustum[i];
/*    */       
/* 31 */       if (dot(afloat, f, f1, f2) <= 0.0F && dot(afloat, f3, f1, f2) <= 0.0F && dot(afloat, f, f4, f2) <= 0.0F && dot(afloat, f3, f4, f2) <= 0.0F && dot(afloat, f, f1, f5) <= 0.0F && dot(afloat, f3, f1, f5) <= 0.0F && dot(afloat, f, f4, f5) <= 0.0F && dot(afloat, f3, f4, f5) <= 0.0F)
/*    */       {
/* 33 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\culling\ClippingHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */