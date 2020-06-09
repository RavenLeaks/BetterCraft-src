/*    */ package net.minecraft.world.chunk;
/*    */ 
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ 
/*    */ public class BlockStatePaletteLinear
/*    */   implements IBlockStatePalette
/*    */ {
/*    */   private final IBlockState[] states;
/*    */   private final IBlockStatePaletteResizer resizeHandler;
/*    */   private final int bits;
/*    */   private int arraySize;
/*    */   
/*    */   public BlockStatePaletteLinear(int p_i47088_1_, IBlockStatePaletteResizer p_i47088_2_) {
/* 17 */     this.states = new IBlockState[1 << p_i47088_1_];
/* 18 */     this.bits = p_i47088_1_;
/* 19 */     this.resizeHandler = p_i47088_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public int idFor(IBlockState state) {
/* 24 */     for (int i = 0; i < this.arraySize; i++) {
/*    */       
/* 26 */       if (this.states[i] == state)
/*    */       {
/* 28 */         return i;
/*    */       }
/*    */     } 
/*    */     
/* 32 */     int j = this.arraySize;
/*    */     
/* 34 */     if (j < this.states.length) {
/*    */       
/* 36 */       this.states[j] = state;
/* 37 */       this.arraySize++;
/* 38 */       return j;
/*    */     } 
/*    */ 
/*    */     
/* 42 */     return this.resizeHandler.onResize(this.bits + 1, state);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public IBlockState getBlockState(int indexKey) {
/* 53 */     return (indexKey >= 0 && indexKey < this.arraySize) ? this.states[indexKey] : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void read(PacketBuffer buf) {
/* 58 */     this.arraySize = buf.readVarIntFromBuffer();
/*    */     
/* 60 */     for (int i = 0; i < this.arraySize; i++)
/*    */     {
/* 62 */       this.states[i] = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(buf.readVarIntFromBuffer());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(PacketBuffer buf) {
/* 68 */     buf.writeVarIntToBuffer(this.arraySize);
/*    */     
/* 70 */     for (int i = 0; i < this.arraySize; i++)
/*    */     {
/* 72 */       buf.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(this.states[i]));
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedState() {
/* 78 */     int i = PacketBuffer.getVarIntSize(this.arraySize);
/*    */     
/* 80 */     for (int j = 0; j < this.arraySize; j++)
/*    */     {
/* 82 */       i += PacketBuffer.getVarIntSize(Block.BLOCK_STATE_IDS.get(this.states[j]));
/*    */     }
/*    */     
/* 85 */     return i;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\chunk\BlockStatePaletteLinear.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */