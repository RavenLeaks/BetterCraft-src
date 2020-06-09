/*    */ package shadersmod.client;
/*    */ 
/*    */ import optifine.MatchBlock;
/*    */ 
/*    */ 
/*    */ public class BlockAlias
/*    */ {
/*    */   private int blockId;
/*    */   private MatchBlock[] matchBlocks;
/*    */   
/*    */   public BlockAlias(int blockId, MatchBlock[] matchBlocks) {
/* 12 */     this.blockId = blockId;
/* 13 */     this.matchBlocks = matchBlocks;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBlockId() {
/* 18 */     return this.blockId;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean matches(int id, int metadata) {
/* 23 */     for (int i = 0; i < this.matchBlocks.length; i++) {
/*    */       
/* 25 */       MatchBlock matchblock = this.matchBlocks[i];
/*    */       
/* 27 */       if (matchblock.matches(id, metadata))
/*    */       {
/* 29 */         return true;
/*    */       }
/*    */     } 
/*    */     
/* 33 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getMatchBlockIds() {
/* 38 */     int[] aint = new int[this.matchBlocks.length];
/*    */     
/* 40 */     for (int i = 0; i < aint.length; i++)
/*    */     {
/* 42 */       aint[i] = this.matchBlocks[i].getBlockId();
/*    */     }
/*    */     
/* 45 */     return aint;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\BlockAlias.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */