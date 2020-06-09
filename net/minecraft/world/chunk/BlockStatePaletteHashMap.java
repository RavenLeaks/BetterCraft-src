/*    */ package net.minecraft.world.chunk;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.util.IntIdentityHashBiMap;
/*    */ 
/*    */ public class BlockStatePaletteHashMap
/*    */   implements IBlockStatePalette
/*    */ {
/*    */   private final IntIdentityHashBiMap<IBlockState> statePaletteMap;
/*    */   private final IBlockStatePaletteResizer paletteResizer;
/*    */   private final int bits;
/*    */   
/*    */   public BlockStatePaletteHashMap(int bitsIn, IBlockStatePaletteResizer p_i47089_2_) {
/* 17 */     this.bits = bitsIn;
/* 18 */     this.paletteResizer = p_i47089_2_;
/* 19 */     this.statePaletteMap = new IntIdentityHashBiMap(1 << bitsIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public int idFor(IBlockState state) {
/* 24 */     int i = this.statePaletteMap.getId(state);
/*    */     
/* 26 */     if (i == -1) {
/*    */       
/* 28 */       i = this.statePaletteMap.add(state);
/*    */       
/* 30 */       if (i >= 1 << this.bits)
/*    */       {
/* 32 */         i = this.paletteResizer.onResize(this.bits + 1, state);
/*    */       }
/*    */     } 
/*    */     
/* 36 */     return i;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public IBlockState getBlockState(int indexKey) {
/* 46 */     return (IBlockState)this.statePaletteMap.get(indexKey);
/*    */   }
/*    */ 
/*    */   
/*    */   public void read(PacketBuffer buf) {
/* 51 */     this.statePaletteMap.clear();
/* 52 */     int i = buf.readVarIntFromBuffer();
/*    */     
/* 54 */     for (int j = 0; j < i; j++)
/*    */     {
/* 56 */       this.statePaletteMap.add(Block.BLOCK_STATE_IDS.getByValue(buf.readVarIntFromBuffer()));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(PacketBuffer buf) {
/* 62 */     int i = this.statePaletteMap.size();
/* 63 */     buf.writeVarIntToBuffer(i);
/*    */     
/* 65 */     for (int j = 0; j < i; j++)
/*    */     {
/* 67 */       buf.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(this.statePaletteMap.get(j)));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedState() {
/* 73 */     int i = PacketBuffer.getVarIntSize(this.statePaletteMap.size());
/*    */     
/* 75 */     for (int j = 0; j < this.statePaletteMap.size(); j++)
/*    */     {
/* 77 */       i += PacketBuffer.getVarIntSize(Block.BLOCK_STATE_IDS.get(this.statePaletteMap.get(j)));
/*    */     }
/*    */     
/* 80 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\chunk\BlockStatePaletteHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */