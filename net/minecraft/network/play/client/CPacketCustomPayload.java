/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import io.netty.buffer.ByteBuf;
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class CPacketCustomPayload
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   public String channel;
/*    */   private PacketBuffer data;
/*    */   
/*    */   public CPacketCustomPayload() {}
/*    */   
/*    */   public CPacketCustomPayload(String channelIn, PacketBuffer bufIn) {
/* 20 */     this.channel = channelIn;
/* 21 */     this.data = bufIn;
/*    */     
/* 23 */     if (bufIn.writerIndex() > 32767)
/*    */     {
/* 25 */       throw new IllegalArgumentException("Payload may not be larger than 32767 bytes");
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
/* 37 */     if (i >= 0 && i <= 32767) {
/*    */       
/* 39 */       this.data = new PacketBuffer(buf.readBytes(i));
/*    */     }
/*    */     else {
/*    */       
/* 43 */       throw new IOException("Payload may not be larger than 32767 bytes");
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
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 61 */     handler.processCustomPayload(this);
/*    */     
/* 63 */     if (this.data != null)
/*    */     {
/* 65 */       this.data.release();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String getChannelName() {
/* 71 */     return this.channel;
/*    */   }
/*    */ 
/*    */   
/*    */   public PacketBuffer getBufferData() {
/* 76 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketCustomPayload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */