/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ public class SPacketBlockAction
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private BlockPos blockPosition;
/*    */   private int instrument;
/*    */   private int pitch;
/*    */   private Block block;
/*    */   
/*    */   public SPacketBlockAction() {}
/*    */   
/*    */   public SPacketBlockAction(BlockPos pos, Block blockIn, int instrumentIn, int pitchIn) {
/* 23 */     this.blockPosition = pos;
/* 24 */     this.instrument = instrumentIn;
/* 25 */     this.pitch = pitchIn;
/* 26 */     this.block = blockIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 34 */     this.blockPosition = buf.readBlockPos();
/* 35 */     this.instrument = buf.readUnsignedByte();
/* 36 */     this.pitch = buf.readUnsignedByte();
/* 37 */     this.block = Block.getBlockById(buf.readVarIntFromBuffer() & 0xFFF);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 45 */     buf.writeBlockPos(this.blockPosition);
/* 46 */     buf.writeByte(this.instrument);
/* 47 */     buf.writeByte(this.pitch);
/* 48 */     buf.writeVarIntToBuffer(Block.getIdFromBlock(this.block) & 0xFFF);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 56 */     handler.handleBlockAction(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getBlockPosition() {
/* 61 */     return this.blockPosition;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getData1() {
/* 69 */     return this.instrument;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getData2() {
/* 77 */     return this.pitch;
/*    */   }
/*    */ 
/*    */   
/*    */   public Block getBlockType() {
/* 82 */     return this.block;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketBlockAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */