/*    */ package net.minecraft.util;
/*    */ 
/*    */ 
/*    */ public class FrameTimer
/*    */ {
/*  6 */   private final long[] frames = new long[240];
/*    */ 
/*    */ 
/*    */   
/*    */   private int lastIndex;
/*    */ 
/*    */ 
/*    */   
/*    */   private int counter;
/*    */ 
/*    */   
/*    */   private int index;
/*    */ 
/*    */ 
/*    */   
/*    */   public void addFrame(long runningTime) {
/* 22 */     this.frames[this.index] = runningTime;
/* 23 */     this.index++;
/*    */     
/* 25 */     if (this.index == 240)
/*    */     {
/* 27 */       this.index = 0;
/*    */     }
/*    */     
/* 30 */     if (this.counter < 240) {
/*    */       
/* 32 */       this.lastIndex = 0;
/* 33 */       this.counter++;
/*    */     }
/*    */     else {
/*    */       
/* 37 */       this.lastIndex = parseIndex(this.index + 1);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLagometerValue(long time, int multiplier) {
/* 46 */     double d0 = time / 1.6666666E7D;
/* 47 */     return (int)(d0 * multiplier);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLastIndex() {
/* 55 */     return this.lastIndex;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIndex() {
/* 63 */     return this.index;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int parseIndex(int rawIndex) {
/* 71 */     return rawIndex % 240;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long[] getFrames() {
/* 79 */     return this.frames;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\FrameTimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */