/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandler.Sharable;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.MessageToByteEncoder;
/*    */ 
/*    */ @Sharable
/*    */ public class NettyVarint21FrameEncoder
/*    */   extends MessageToByteEncoder<ByteBuf>
/*    */ {
/*    */   protected void encode(ChannelHandlerContext p_encode_1_, ByteBuf p_encode_2_, ByteBuf p_encode_3_) throws Exception {
/* 13 */     int i = p_encode_2_.readableBytes();
/* 14 */     int j = PacketBuffer.getVarIntSize(i);
/*    */     
/* 16 */     if (j > 3)
/*    */     {
/* 18 */       throw new IllegalArgumentException("unable to fit " + i + " into " + '\003');
/*    */     }
/*    */ 
/*    */     
/* 22 */     PacketBuffer packetbuffer = new PacketBuffer(p_encode_3_);
/* 23 */     packetbuffer.ensureWritable(j + i);
/* 24 */     packetbuffer.writeVarIntToBuffer(i);
/* 25 */     packetbuffer.writeBytes(p_encode_2_, p_encode_2_.readerIndex(), i);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\NettyVarint21FrameEncoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */