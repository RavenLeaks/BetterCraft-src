/*    */ package net.minecraft.network;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.ShortBufferException;
/*    */ 
/*    */ public class NettyEncryptionTranslator
/*    */ {
/*    */   private final Cipher cipher;
/* 11 */   private byte[] inputBuffer = new byte[0];
/* 12 */   private byte[] outputBuffer = new byte[0];
/*    */ 
/*    */   
/*    */   protected NettyEncryptionTranslator(Cipher cipherIn) {
/* 16 */     this.cipher = cipherIn;
/*    */   }
/*    */ 
/*    */   
/*    */   private byte[] bufToBytes(ByteBuf buf) {
/* 21 */     int i = buf.readableBytes();
/*    */     
/* 23 */     if (this.inputBuffer.length < i)
/*    */     {
/* 25 */       this.inputBuffer = new byte[i];
/*    */     }
/*    */     
/* 28 */     buf.readBytes(this.inputBuffer, 0, i);
/* 29 */     return this.inputBuffer;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ByteBuf decipher(ChannelHandlerContext ctx, ByteBuf buffer) throws ShortBufferException {
/* 34 */     int i = buffer.readableBytes();
/* 35 */     byte[] abyte = bufToBytes(buffer);
/* 36 */     ByteBuf bytebuf = ctx.alloc().heapBuffer(this.cipher.getOutputSize(i));
/* 37 */     bytebuf.writerIndex(this.cipher.update(abyte, 0, i, bytebuf.array(), bytebuf.arrayOffset()));
/* 38 */     return bytebuf;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void cipher(ByteBuf in, ByteBuf out) throws ShortBufferException {
/* 43 */     int i = in.readableBytes();
/* 44 */     byte[] abyte = bufToBytes(in);
/* 45 */     int j = this.cipher.getOutputSize(i);
/*    */     
/* 47 */     if (this.outputBuffer.length < j)
/*    */     {
/* 49 */       this.outputBuffer = new byte[j];
/*    */     }
/*    */     
/* 52 */     out.writeBytes(this.outputBuffer, 0, this.cipher.update(abyte, 0, i, this.outputBuffer));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\NettyEncryptionTranslator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */