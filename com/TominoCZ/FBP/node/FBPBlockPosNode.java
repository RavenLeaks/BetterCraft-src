/*    */ package com.TominoCZ.FBP.node;
/*    */ 
/*    */ import io.netty.util.internal.ConcurrentSet;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ public class FBPBlockPosNode
/*    */ {
/*  8 */   ConcurrentSet<BlockPos> possible = new ConcurrentSet();
/*    */   
/*    */   public boolean checked = false;
/*    */ 
/*    */   
/*    */   public void add(BlockPos pos) {
/* 14 */     this.possible.add(pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPos(BlockPos p1) {
/* 19 */     return this.possible.contains(p1);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\com\TominoCZ\FBP\node\FBPBlockPosNode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */