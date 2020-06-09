/*    */ package net.minecraft.client.resources.data;
/*    */ 
/*    */ 
/*    */ public class AnimationFrame
/*    */ {
/*    */   private final int frameIndex;
/*    */   private final int frameTime;
/*    */   
/*    */   public AnimationFrame(int frameIndexIn) {
/* 10 */     this(frameIndexIn, -1);
/*    */   }
/*    */ 
/*    */   
/*    */   public AnimationFrame(int frameIndexIn, int frameTimeIn) {
/* 15 */     this.frameIndex = frameIndexIn;
/* 16 */     this.frameTime = frameTimeIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasNoTime() {
/* 21 */     return (this.frameTime == -1);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFrameTime() {
/* 26 */     return this.frameTime;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFrameIndex() {
/* 31 */     return this.frameIndex;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\resources\data\AnimationFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */