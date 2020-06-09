/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ 
/*    */ 
/*    */ public class SPacketDisconnect
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private ITextComponent reason;
/*    */   
/*    */   public SPacketDisconnect() {}
/*    */   
/*    */   public SPacketDisconnect(ITextComponent messageIn) {
/* 19 */     this.reason = messageIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 27 */     this.reason = buf.readTextComponent();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 35 */     buf.writeTextComponent(this.reason);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 43 */     handler.handleDisconnect(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public ITextComponent getReason() {
/* 48 */     return this.reason;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketDisconnect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */