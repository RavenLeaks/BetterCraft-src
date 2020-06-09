/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ public class SPacketBlockChange
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   public BlockPos blockPosition;
/*    */   public IBlockState blockState;
/*    */   
/*    */   public SPacketBlockChange() {}
/*    */   
/*    */   public SPacketBlockChange(World worldIn, BlockPos posIn) {
/* 23 */     this.blockPosition = posIn;
/* 24 */     this.blockState = worldIn.getBlockState(posIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 32 */     this.blockPosition = buf.readBlockPos();
/* 33 */     this.blockState = (IBlockState)Block.BLOCK_STATE_IDS.getByValue(buf.readVarIntFromBuffer());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 41 */     buf.writeBlockPos(this.blockPosition);
/* 42 */     buf.writeVarIntToBuffer(Block.BLOCK_STATE_IDS.get(this.blockState));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 50 */     handler.handleBlockChange(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public IBlockState getBlockState() {
/* 55 */     return this.blockState;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getBlockPosition() {
/* 60 */     return this.blockPosition;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketBlockChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */