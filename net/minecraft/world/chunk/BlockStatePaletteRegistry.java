/*    */ package net.minecraft.world.chunk;
/*    */ 
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ 
/*    */ public class BlockStatePaletteRegistry
/*    */   implements IBlockStatePalette
/*    */ {
/*    */   public int idFor(IBlockState state) {
/* 12 */     int i = Block.BLOCK_STATE_IDS.get(state);
/* 13 */     return (i == -1) ? 0 : i;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getBlockState(int indexKey) {
/* 21 */     IBlockState iblockstate = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(indexKey);
/* 22 */     return (iblockstate == null) ? Blocks.AIR.getDefaultState() : iblockstate;
/*    */   }
/*    */ 
/*    */   
/*    */   public void read(PacketBuffer buf) {
/* 27 */     buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(PacketBuffer buf) {
/* 32 */     buf.writeVarIntToBuffer(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedState() {
/* 37 */     return PacketBuffer.getVarIntSize(0);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\chunk\BlockStatePaletteRegistry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */