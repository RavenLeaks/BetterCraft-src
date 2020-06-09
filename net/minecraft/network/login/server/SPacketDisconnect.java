/*    */ package net.minecraft.network.login.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.login.INetHandlerLoginClient;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ 
/*    */ 
/*    */ public class SPacketDisconnect
/*    */   implements Packet<INetHandlerLoginClient>
/*    */ {
/*    */   private ITextComponent reason;
/*    */   
/*    */   public SPacketDisconnect() {}
/*    */   
/*    */   public SPacketDisconnect(ITextComponent p_i46853_1_) {
/* 19 */     this.reason = p_i46853_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 27 */     this.reason = ITextComponent.Serializer.fromJsonLenient(buf.readStringFromBuffer(32767));
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
/*    */   public void processPacket(INetHandlerLoginClient handler) {
/* 43 */     handler.handleDisconnect(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public ITextComponent getReason() {
/* 48 */     return this.reason;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\login\server\SPacketDisconnect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */