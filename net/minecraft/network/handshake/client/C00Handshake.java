/*    */ package net.minecraft.network.handshake.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import me.nzxter.bettercraft.BetterCraft;
/*    */ import net.minecraft.network.EnumConnectionState;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.handshake.INetHandlerHandshakeServer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class C00Handshake
/*    */   implements Packet<INetHandlerHandshakeServer>
/*    */ {
/*    */   private int protocolVersion;
/*    */   private String ip;
/*    */   private int port;
/*    */   private EnumConnectionState requestedState;
/*    */   
/*    */   public C00Handshake() {}
/*    */   
/*    */   public C00Handshake(String p_i47613_1_, int p_i47613_2_, EnumConnectionState p_i47613_3_) {
/* 24 */     this.protocolVersion = BetterCraft.INSTANCE.getCurrentVersion().getId();
/* 25 */     this.ip = p_i47613_1_;
/* 26 */     this.port = p_i47613_2_;
/* 27 */     this.requestedState = p_i47613_3_;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 32 */     this.protocolVersion = buf.readVarIntFromBuffer();
/* 33 */     this.ip = buf.readStringFromBuffer(255);
/* 34 */     this.port = buf.readUnsignedShort();
/* 35 */     this.requestedState = EnumConnectionState.getById(buf.readVarIntFromBuffer());
/*    */   }
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 40 */     buf.writeVarIntToBuffer(this.protocolVersion);
/* 41 */     buf.writeString(this.ip);
/* 42 */     buf.writeShort(this.port);
/* 43 */     buf.writeVarIntToBuffer(this.requestedState.getId());
/*    */   }
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerHandshakeServer handler) {
/* 48 */     handler.processHandshake(this);
/*    */   }
/*    */   
/*    */   public EnumConnectionState getRequestedState() {
/* 52 */     return this.requestedState;
/*    */   }
/*    */   
/*    */   public int getProtocolVersion() {
/* 56 */     return this.protocolVersion;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\handshake\client\C00Handshake.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */