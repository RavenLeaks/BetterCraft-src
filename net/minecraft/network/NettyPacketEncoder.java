/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ import java.io.IOException;
/*    */ import me.nzxter.bettercraft.BetterCraft;
/*    */ import me.nzxter.bettercraft.mods.protocolhack.PacketWrapper;
/*    */ import me.nzxter.bettercraft.mods.protocolhack.ProtocolHack;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ import org.apache.logging.log4j.Marker;
/*    */ import org.apache.logging.log4j.MarkerManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NettyPacketEncoder
/*    */   extends MessageToByteEncoder<Packet<?>>
/*    */ {
/* 24 */   private static final Logger LOGGER = LogManager.getLogger();
/* 25 */   private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_SENT", NetworkManager.NETWORK_PACKETS_MARKER);
/*    */   private final EnumPacketDirection direction;
/*    */   
/*    */   public NettyPacketEncoder(EnumPacketDirection direction) {
/* 29 */     this.direction = direction;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void encode(ChannelHandlerContext ctx, Packet<?> packet, ByteBuf buf) throws IOException, Exception {
/* 34 */     EnumConnectionState enumconnectionstate = (EnumConnectionState)ctx.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get();
/* 35 */     if (enumconnectionstate == null) {
/* 36 */       throw new RuntimeException("ConnectionProtocol unknown: " + packet.toString());
/*    */     }
/* 38 */     if (BetterCraft.INSTANCE.getCurrentVersion() == ProtocolHack.PROTOCOL_340 || enumconnectionstate != EnumConnectionState.PLAY) {
/* 39 */       Integer packetId = enumconnectionstate.getPacketId(this.direction, packet);
/* 40 */       if (LOGGER.isDebugEnabled()) {
/* 41 */         LOGGER.debug(RECEIVED_PACKET_MARKER, "OUT: [{}:{}] {}", ctx.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get(), packetId, packet.getClass().getName());
/*    */       }
/* 43 */       if (packetId == null) {
/* 44 */         throw new IOException("Can't serialize unregistered packet");
/*    */       }
/* 46 */       PacketBuffer packetbuffer = new PacketBuffer(buf);
/* 47 */       packetbuffer.writeVarIntToBuffer(packetId.intValue());
/*    */       try {
/* 49 */         packet.writePacketData(packetbuffer);
/*    */       }
/* 51 */       catch (Throwable throwable) {
/* 52 */         LOGGER.error(throwable);
/*    */       }
/*    */     
/* 55 */     } else if (BetterCraft.INSTANCE.getCurrentVersion() != ProtocolHack.PROTOCOL_340) {
/* 56 */       PacketWrapper wrapper = BetterCraft.INSTANCE.getCurrentVersion().getPacketWrapper();
/* 57 */       PacketBuffer packetbuffer = new PacketBuffer(buf);
/* 58 */       wrapper.writePacket(packet, packetbuffer);
/* 59 */       if (packetbuffer.readableBytes() <= 0) {
/* 60 */         throw new IllegalStateException("Bytebuffe");
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 67 */     if (cause instanceof IllegalStateException && cause.getMessage().equalsIgnoreCase("Bytebuffe"))
/*    */       return; 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\NettyPacketEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */