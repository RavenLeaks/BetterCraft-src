/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class CPacketConfirmTeleport
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private int telportId;
/*    */   
/*    */   public CPacketConfirmTeleport() {}
/*    */   
/*    */   public CPacketConfirmTeleport(int teleportIdIn) {
/* 18 */     this.telportId = teleportIdIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 26 */     this.telportId = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 34 */     buf.writeVarIntToBuffer(this.telportId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 42 */     handler.processConfirmTeleport(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTeleportId() {
/* 47 */     return this.telportId;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketConfirmTeleport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */