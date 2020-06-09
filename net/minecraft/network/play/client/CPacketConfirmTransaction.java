/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class CPacketConfirmTransaction
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private int windowId;
/*    */   private short uid;
/*    */   private boolean accepted;
/*    */   
/*    */   public CPacketConfirmTransaction() {}
/*    */   
/*    */   public CPacketConfirmTransaction(int windowIdIn, short uidIn, boolean acceptedIn) {
/* 20 */     this.windowId = windowIdIn;
/* 21 */     this.uid = uidIn;
/* 22 */     this.accepted = acceptedIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 30 */     handler.processConfirmTransaction(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 38 */     this.windowId = buf.readByte();
/* 39 */     this.uid = buf.readShort();
/* 40 */     this.accepted = (buf.readByte() != 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 48 */     buf.writeByte(this.windowId);
/* 49 */     buf.writeShort(this.uid);
/* 50 */     buf.writeByte(this.accepted ? 1 : 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWindowId() {
/* 55 */     return this.windowId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getUid() {
/* 60 */     return this.uid;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketConfirmTransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */