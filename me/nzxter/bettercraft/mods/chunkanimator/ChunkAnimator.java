/*    */ package me.nzxter.bettercraft.mods.chunkanimator;
/*    */ 
/*    */ import me.nzxter.bettercraft.mods.chunkanimator.handler.AnimationHandler;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChunkAnimator
/*    */ {
/*    */   public static ChunkAnimator INSTANCE;
/*    */   public AnimationHandler animationHandler;
/* 11 */   public int mode = 0;
/* 12 */   public int animationDuration = 2000;
/* 13 */   public int easingFunction = 1;
/*    */   
/*    */   public boolean disableAroundPlayer = true;
/*    */   
/*    */   public ChunkAnimator() {
/* 18 */     INSTANCE = this;
/*    */   }
/*    */   
/*    */   public void onStart() {
/* 22 */     this.animationHandler = new AnimationHandler();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\chunkanimator\ChunkAnimator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */