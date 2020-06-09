/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.buffer.Unpooled;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import io.netty.handler.codec.ByteToMessageDecoder;
/*    */ import io.netty.handler.codec.CorruptedFrameException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class NettyVarint21FrameDecoder
/*    */   extends ByteToMessageDecoder
/*    */ {
/*    */   protected void decode(ChannelHandlerContext p_decode_1_, ByteBuf p_decode_2_, List<Object> p_decode_3_) throws Exception {
/* 14 */     p_decode_2_.markReaderIndex();
/* 15 */     byte[] abyte = new byte[3];
/*    */     
/* 17 */     for (int i = 0; i < abyte.length; i++) {
/*    */       
/* 19 */       if (!p_decode_2_.isReadable()) {
/*    */         
/* 21 */         p_decode_2_.resetReaderIndex();
/*    */         
/*    */         return;
/*    */       } 
/* 25 */       abyte[i] = p_decode_2_.readByte();
/*    */       
/* 27 */       if (abyte[i] >= 0) {
/*    */         
/* 29 */         PacketBuffer packetbuffer = new PacketBuffer(Unpooled.wrappedBuffer(abyte));
/*    */ 
/*    */         
/*    */         try {
/* 33 */           int j = packetbuffer.readVarIntFromBuffer();
/*    */           
/* 35 */           if (p_decode_2_.readableBytes() >= j) {
/*    */             
/* 37 */             p_decode_3_.add(p_decode_2_.readBytes(j));
/*    */             
/*    */             return;
/*    */           } 
/* 41 */           p_decode_2_.resetReaderIndex();
/*    */         }
/*    */         finally {
/*    */           
/* 45 */           packetbuffer.release();
/*    */         } 
/*    */         
/*    */         return;
/*    */       } 
/*    */     } 
/*    */     
/* 52 */     throw new CorruptedFrameException("length wider than 21-bit");
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\NettyVarint21FrameDecoder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */