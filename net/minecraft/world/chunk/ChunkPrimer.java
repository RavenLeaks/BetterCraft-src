/*    */ package net.minecraft.world.chunk;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class ChunkPrimer
/*    */ {
/*  9 */   private static final IBlockState DEFAULT_STATE = Blocks.AIR.getDefaultState();
/* 10 */   private final char[] data = new char[65536];
/*    */ 
/*    */   
/*    */   public IBlockState getBlockState(int x, int y, int z) {
/* 14 */     IBlockState iblockstate = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(this.data[getBlockIndex(x, y, z)]);
/* 15 */     return (iblockstate == null) ? DEFAULT_STATE : iblockstate;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setBlockState(int x, int y, int z, IBlockState state) {
/* 20 */     this.data[getBlockIndex(x, y, z)] = (char)Block.BLOCK_STATE_IDS.get(state);
/*    */   }
/*    */ 
/*    */   
/*    */   private static int getBlockIndex(int x, int y, int z) {
/* 25 */     return x << 12 | z << 8 | y;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int findGroundBlockIdx(int x, int z) {
/* 34 */     int i = (x << 12 | z << 8) + 256 - 1;
/*    */     
/* 36 */     for (int j = 255; j >= 0; j--) {
/*    */       
/* 38 */       IBlockState iblockstate = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(this.data[i + j]);
/*    */       
/* 40 */       if (iblockstate != null && iblockstate != DEFAULT_STATE)
/*    */       {
/* 42 */         return j;
/*    */       }
/*    */     } 
/*    */     
/* 46 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\chunk\ChunkPrimer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */