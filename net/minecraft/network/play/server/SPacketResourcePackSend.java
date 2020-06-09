/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketResourcePackSend
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private String url;
/*    */   private String hash;
/*    */   
/*    */   public SPacketResourcePackSend() {}
/*    */   
/*    */   public SPacketResourcePackSend(String urlIn, String hashIn) {
/* 19 */     this.url = urlIn;
/* 20 */     this.hash = hashIn;
/*    */     
/* 22 */     if (hashIn.length() > 40)
/*    */     {
/* 24 */       throw new IllegalArgumentException("Hash is too long (max 40, was " + hashIn.length() + ")");
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 33 */     this.url = buf.readStringFromBuffer(32767);
/* 34 */     this.hash = buf.readStringFromBuffer(40);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 42 */     buf.writeString(this.url);
/* 43 */     buf.writeString(this.hash);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 51 */     handler.handleResourcePack(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getURL() {
/* 56 */     return this.url;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHash() {
/* 61 */     return this.hash;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketResourcePackSend.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */