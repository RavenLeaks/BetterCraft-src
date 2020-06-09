/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketConfirmTransaction
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int windowId;
/*    */   private short actionNumber;
/*    */   private boolean accepted;
/*    */   
/*    */   public SPacketConfirmTransaction() {}
/*    */   
/*    */   public SPacketConfirmTransaction(int windowIdIn, short actionNumberIn, boolean acceptedIn) {
/* 20 */     this.windowId = windowIdIn;
/* 21 */     this.actionNumber = actionNumberIn;
/* 22 */     this.accepted = acceptedIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 30 */     handler.handleConfirmTransaction(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 38 */     this.windowId = buf.readUnsignedByte();
/* 39 */     this.actionNumber = buf.readShort();
/* 40 */     this.accepted = buf.readBoolean();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 48 */     buf.writeByte(this.windowId);
/* 49 */     buf.writeShort(this.actionNumber);
/* 50 */     buf.writeBoolean(this.accepted);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWindowId() {
/* 55 */     return this.windowId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getActionNumber() {
/* 60 */     return this.actionNumber;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean wasAccepted() {
/* 65 */     return this.accepted;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketConfirmTransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */