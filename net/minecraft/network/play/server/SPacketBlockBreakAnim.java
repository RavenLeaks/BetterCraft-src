/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ public class SPacketBlockBreakAnim
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int breakerId;
/*    */   private BlockPos position;
/*    */   private int progress;
/*    */   
/*    */   public SPacketBlockBreakAnim() {}
/*    */   
/*    */   public SPacketBlockBreakAnim(int breakerIdIn, BlockPos positionIn, int progressIn) {
/* 21 */     this.breakerId = breakerIdIn;
/* 22 */     this.position = positionIn;
/* 23 */     this.progress = progressIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 31 */     this.breakerId = buf.readVarIntFromBuffer();
/* 32 */     this.position = buf.readBlockPos();
/* 33 */     this.progress = buf.readUnsignedByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 41 */     buf.writeVarIntToBuffer(this.breakerId);
/* 42 */     buf.writeBlockPos(this.position);
/* 43 */     buf.writeByte(this.progress);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 51 */     handler.handleBlockBreakAnim(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBreakerId() {
/* 56 */     return this.breakerId;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getPosition() {
/* 61 */     return this.position;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getProgress() {
/* 66 */     return this.progress;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketBlockBreakAnim.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */