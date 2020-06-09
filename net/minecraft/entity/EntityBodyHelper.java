/*    */ package net.minecraft.entity;
/*    */ 
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityBodyHelper
/*    */ {
/*    */   private final EntityLivingBase theLiving;
/*    */   private int rotationTickCounter;
/*    */   private float prevRenderYawHead;
/*    */   
/*    */   public EntityBodyHelper(EntityLivingBase livingIn) {
/* 18 */     this.theLiving = livingIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void updateRenderAngles() {
/* 26 */     double d0 = this.theLiving.posX - this.theLiving.prevPosX;
/* 27 */     double d1 = this.theLiving.posZ - this.theLiving.prevPosZ;
/*    */     
/* 29 */     if (d0 * d0 + d1 * d1 > 2.500000277905201E-7D) {
/*    */       
/* 31 */       this.theLiving.renderYawOffset = this.theLiving.rotationYaw;
/* 32 */       this.theLiving.rotationYawHead = computeAngleWithBound(this.theLiving.renderYawOffset, this.theLiving.rotationYawHead, 75.0F);
/* 33 */       this.prevRenderYawHead = this.theLiving.rotationYawHead;
/* 34 */       this.rotationTickCounter = 0;
/*    */ 
/*    */     
/*    */     }
/* 38 */     else if (this.theLiving.getPassengers().isEmpty() || !(this.theLiving.getPassengers().get(0) instanceof EntityLiving)) {
/*    */       
/* 40 */       float f = 75.0F;
/*    */       
/* 42 */       if (Math.abs(this.theLiving.rotationYawHead - this.prevRenderYawHead) > 15.0F) {
/*    */         
/* 44 */         this.rotationTickCounter = 0;
/* 45 */         this.prevRenderYawHead = this.theLiving.rotationYawHead;
/*    */       }
/*    */       else {
/*    */         
/* 49 */         this.rotationTickCounter++;
/* 50 */         int i = 10;
/*    */         
/* 52 */         if (this.rotationTickCounter > 10)
/*    */         {
/* 54 */           f = Math.max(1.0F - (this.rotationTickCounter - 10) / 10.0F, 0.0F) * 75.0F;
/*    */         }
/*    */       } 
/*    */       
/* 58 */       this.theLiving.renderYawOffset = computeAngleWithBound(this.theLiving.rotationYawHead, this.theLiving.renderYawOffset, f);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private float computeAngleWithBound(float p_75665_1_, float p_75665_2_, float p_75665_3_) {
/* 69 */     float f = MathHelper.wrapDegrees(p_75665_1_ - p_75665_2_);
/*    */     
/* 71 */     if (f < -p_75665_3_)
/*    */     {
/* 73 */       f = -p_75665_3_;
/*    */     }
/*    */     
/* 76 */     if (f >= p_75665_3_)
/*    */     {
/* 78 */       f = p_75665_3_;
/*    */     }
/*    */     
/* 81 */     return p_75665_1_ - f;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\entity\EntityBodyHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */