/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
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
/*    */ public class NettyPacketDecoder
/*    */   extends ByteToMessageDecoder
/*    */ {
/* 25 */   private static final Logger LOGGER = LogManager.getLogger();
/* 26 */   private static final Marker RECEIVED_PACKET_MARKER = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.NETWORK_PACKETS_MARKER);
/*    */   private final EnumPacketDirection direction;
/*    */   
/*    */   public NettyPacketDecoder(EnumPacketDirection direction) {
/* 30 */     this.direction = direction;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> out) throws IOException, InstantiationException, IllegalAccessException, Exception {
/* 35 */     if (buf.readableBytes() != 0) {
/* 36 */       PacketBuffer packetbuffer = new PacketBuffer(buf);
/* 37 */       int id = packetbuffer.readVarIntFromBuffer();
/* 38 */       if (BetterCraft.INSTANCE.getCurrentVersion().getPacketWrapper() == null || ctx.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get() != EnumConnectionState.PLAY) {
/* 39 */         Packet<?> packet = ((EnumConnectionState)ctx.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get()).getPacket(this.direction, id);
/* 40 */         if (packet == null) {
/* 41 */           throw new IOException("Bad packet id " + id);
/*    */         }
/* 43 */         packet.readPacketData(packetbuffer);
/* 44 */         if (packetbuffer.readableBytes() > 0) {
/* 45 */           throw new IOException("Packet " + ((EnumConnectionState)ctx.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get()).getId() + "/" + id + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetbuffer.readableBytes() + " bytes extra whilst reading packet " + id);
/*    */         }
/* 47 */         out.add(packet);
/* 48 */         if (LOGGER.isDebugEnabled()) {
/* 49 */           LOGGER.debug(RECEIVED_PACKET_MARKER, " IN: [{}:{}] {}", ctx.channel().attr(NetworkManager.PROTOCOL_ATTRIBUTE_KEY).get(), Integer.valueOf(id), packet.getClass().getName());
/*    */         }
/*    */       }
/* 52 */       else if (BetterCraft.INSTANCE.getCurrentVersion() != ProtocolHack.PROTOCOL_340) {
/* 53 */         PacketWrapper wrapper = BetterCraft.INSTANCE.getCurrentVersion().getPacketWrapper();
/* 54 */         Packet<?> packet2 = wrapper.readPacket(id, packetbuffer);
/* 55 */         if (packet2 == null) {
/* 56 */           buf.skipBytes(buf.readableBytes());
/*    */           return;
/*    */         } 
/* 59 */         out.add(packet2);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\NettyPacketDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */