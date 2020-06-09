/*    */ package net.minecraft.network.status.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.status.INetHandlerStatusServer;
/*    */ 
/*    */ 
/*    */ public class CPacketPing
/*    */   implements Packet<INetHandlerStatusServer>
/*    */ {
/*    */   private long clientTime;
/*    */   
/*    */   public CPacketPing() {}
/*    */   
/*    */   public CPacketPing(long clientTimeIn) {
/* 18 */     this.clientTime = clientTimeIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 26 */     this.clientTime = buf.readLong();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 34 */     buf.writeLong(this.clientTime);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerStatusServer handler) {
/* 42 */     handler.processPing(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public long getClientTime() {
/* 47 */     return this.clientTime;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\status\client\CPacketPing.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */