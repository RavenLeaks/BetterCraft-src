/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class CPacketClientStatus
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private State status;
/*    */   
/*    */   public CPacketClientStatus() {}
/*    */   
/*    */   public CPacketClientStatus(State p_i46886_1_) {
/* 18 */     this.status = p_i46886_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 26 */     this.status = (State)buf.readEnumValue(State.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 34 */     buf.writeEnumValue(this.status);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 42 */     handler.processClientStatus(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public State getStatus() {
/* 47 */     return this.status;
/*    */   }
/*    */   
/*    */   public enum State
/*    */   {
/* 52 */     PERFORM_RESPAWN,
/* 53 */     REQUEST_STATS;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketClientStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */