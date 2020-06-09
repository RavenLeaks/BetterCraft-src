/*    */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_7;
/*    */ 
/*    */ import me.nzxter.bettercraft.mods.protocolhack.PacketWrapper;
/*    */ import me.nzxter.bettercraft.mods.protocolhack.ProtocolHack;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.network.play.client.CPacketKeepAlive;
/*    */ import net.minecraft.network.play.server.SPacketJoinGame;
/*    */ import net.minecraft.network.play.server.SPacketKeepAlive;
/*    */ 
/*    */ public class PacketWrapper5
/*    */   extends PacketWrapper {
/*    */   public PacketWrapper5() {
/* 15 */     super(ProtocolHack.PROTOCOL_5);
/*    */   }
/*    */ 
/*    */   
/*    */   public void writePacket(Packet<?> packet, PacketBuffer buffer) throws Exception {
/* 20 */     if (packet instanceof CPacketKeepAlive) {
/* 21 */       buffer.writeVarIntToBuffer(0);
/* 22 */       buffer.writeVarIntToBuffer((int)((CPacketKeepAlive)packet).getKey());
/*    */     } 
/*    */   }
/*    */   public Packet<?> readPacket(int packetid, PacketBuffer buffer) throws Exception {
/*    */     SPacketKeepAlive sPacketKeepAlive;
/*    */     SPacketJoinGame sPacketJoinGame;
/* 28 */     Packet<INetHandlerPlayClient> packet = null;
/* 29 */     switch (packetid) {
/*    */       case 0:
/* 31 */         sPacketKeepAlive = new SPacketKeepAlive();
/* 32 */         sPacketKeepAlive.readPacketData(buffer);
/*    */         break;
/*    */       
/*    */       case 1:
/* 36 */         sPacketJoinGame = new SPacketJoinGame();
/* 37 */         sPacketJoinGame.readPacketData(buffer);
/*    */         break;
/*    */     } 
/* 40 */     return (Packet<?>)sPacketJoinGame;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_7\PacketWrapper5.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */