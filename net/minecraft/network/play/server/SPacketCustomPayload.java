/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketCustomPayload
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private String channel;
/*    */   private PacketBuffer data;
/*    */   
/*    */   public SPacketCustomPayload() {}
/*    */   
/*    */   public SPacketCustomPayload(String channelIn, PacketBuffer bufIn) {
/* 20 */     this.channel = channelIn;
/* 21 */     this.data = bufIn;
/*    */     
/* 23 */     if (bufIn.writerIndex() > 1048576)
/*    */     {
/* 25 */       throw new IllegalArgumentException("Payload may not be larger than 1048576 bytes");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 34 */     this.channel = buf.readStringFromBuffer(20);
/* 35 */     int i = buf.readableBytes();
/*    */     
/* 37 */     if (i >= 0 && i <= 1048576) {
/*    */       
/* 39 */       this.data = new PacketBuffer(buf.readBytes(i));
/*    */     }
/*    */     else {
/*    */       
/* 43 */       throw new IOException("Payload may not be larger than 1048576 bytes");
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 52 */     buf.writeString(this.channel);
/* 53 */     buf.writeBytes((ByteBuf)this.data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 61 */     handler.handleCustomPayload(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getChannelName() {
/* 66 */     return this.channel;
/*    */   }
/*    */ 
/*    */   
/*    */   public PacketBuffer getBufferData() {
/* 71 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketCustomPayload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */