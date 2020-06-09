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
/*    */ public class SPacketSpawnPosition
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private BlockPos spawnBlockPos;
/*    */   
/*    */   public SPacketSpawnPosition() {}
/*    */   
/*    */   public SPacketSpawnPosition(BlockPos posIn) {
/* 19 */     this.spawnBlockPos = posIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 27 */     this.spawnBlockPos = buf.readBlockPos();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 35 */     buf.writeBlockPos(this.spawnBlockPos);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 43 */     handler.handleSpawnPosition(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockPos getSpawnPos() {
/* 48 */     return this.spawnBlockPos;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketSpawnPosition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */