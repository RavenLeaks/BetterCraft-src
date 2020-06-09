/*    */ package optifine;
/*    */ 
/*    */ import net.minecraft.util.math.Vec3d;
/*    */ 
/*    */ public class CustomColorFader
/*    */ {
/*  7 */   private Vec3d color = null;
/*  8 */   private long timeUpdate = System.currentTimeMillis();
/*    */ 
/*    */   
/*    */   public Vec3d getColor(double p_getColor_1_, double p_getColor_3_, double p_getColor_5_) {
/* 12 */     if (this.color == null) {
/*    */       
/* 14 */       this.color = new Vec3d(p_getColor_1_, p_getColor_3_, p_getColor_5_);
/* 15 */       return this.color;
/*    */     } 
/*    */ 
/*    */     
/* 19 */     long i = System.currentTimeMillis();
/* 20 */     long j = i - this.timeUpdate;
/*    */     
/* 22 */     if (j == 0L)
/*    */     {
/* 24 */       return this.color;
/*    */     }
/*    */ 
/*    */     
/* 28 */     this.timeUpdate = i;
/*    */     
/* 30 */     if (Math.abs(p_getColor_1_ - this.color.xCoord) < 0.004D && Math.abs(p_getColor_3_ - this.color.yCoord) < 0.004D && Math.abs(p_getColor_5_ - this.color.zCoord) < 0.004D)
/*    */     {
/* 32 */       return this.color;
/*    */     }
/*    */ 
/*    */     
/* 36 */     double d0 = j * 0.001D;
/* 37 */     d0 = Config.limit(d0, 0.0D, 1.0D);
/* 38 */     double d1 = p_getColor_1_ - this.color.xCoord;
/* 39 */     double d2 = p_getColor_3_ - this.color.yCoord;
/* 40 */     double d3 = p_getColor_5_ - this.color.zCoord;
/* 41 */     double d4 = this.color.xCoord + d1 * d0;
/* 42 */     double d5 = this.color.yCoord + d2 * d0;
/* 43 */     double d6 = this.color.zCoord + d3 * d0;
/* 44 */     this.color = new Vec3d(d4, d5, d6);
/* 45 */     return this.color;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\CustomColorFader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */