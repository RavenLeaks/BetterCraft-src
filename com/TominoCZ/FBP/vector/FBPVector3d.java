/*    */ package com.TominoCZ.FBP.vector;
/*    */ 
/*    */ import net.minecraft.client.renderer.Vector3d;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FBPVector3d
/*    */   extends Vector3d
/*    */ {
/*    */   public FBPVector3d() {}
/*    */   
/*    */   public FBPVector3d(double x, double y, double z) {
/* 14 */     this.x = x;
/* 15 */     this.y = y;
/* 16 */     this.z = z;
/*    */   }
/*    */ 
/*    */   
/*    */   public FBPVector3d(FBPVector3d vec) {
/* 21 */     this.x = vec.x;
/* 22 */     this.y = vec.y;
/* 23 */     this.z = vec.z;
/*    */   }
/*    */ 
/*    */   
/*    */   public void copyFrom(Vector3d vec) {
/* 28 */     this.x = vec.x;
/* 29 */     this.y = vec.y;
/* 30 */     this.z = vec.z;
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(Vector3d vec) {
/* 35 */     this.x += vec.x;
/* 36 */     this.y += vec.y;
/* 37 */     this.z += vec.z;
/*    */   }
/*    */ 
/*    */   
/*    */   public void zero() {
/* 42 */     this.x = 0.0D;
/* 43 */     this.y = 0.0D;
/* 44 */     this.z = 0.0D;
/*    */   }
/*    */ 
/*    */   
/*    */   public FBPVector3d partialVec(FBPVector3d prevRot, float partialTicks) {
/* 49 */     FBPVector3d v = new FBPVector3d();
/*    */     
/* 51 */     prevRot.x += (this.x - prevRot.x) * partialTicks;
/* 52 */     prevRot.y += (this.y - prevRot.y) * partialTicks;
/* 53 */     prevRot.z += (this.z - prevRot.z) * partialTicks;
/*    */     
/* 55 */     return v;
/*    */   }
/*    */ 
/*    */   
/*    */   public FBPVector3d multiply(double d) {
/* 60 */     FBPVector3d v = new FBPVector3d(this);
/*    */     
/* 62 */     v.x *= d;
/* 63 */     v.y *= d;
/* 64 */     v.z *= d;
/*    */     
/* 66 */     return v;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\vector\FBPVector3d.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */