/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketHeldItemChange
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int heldItemHotbarIndex;
/*    */   
/*    */   public SPacketHeldItemChange() {}
/*    */   
/*    */   public SPacketHeldItemChange(int hotbarIndexIn) {
/* 18 */     this.heldItemHotbarIndex = hotbarIndexIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 26 */     this.heldItemHotbarIndex = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 34 */     buf.writeByte(this.heldItemHotbarIndex);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 42 */     handler.handleHeldItemChange(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getHeldItemHotbarIndex() {
/* 47 */     return this.heldItemHotbarIndex;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketHeldItemChange.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */