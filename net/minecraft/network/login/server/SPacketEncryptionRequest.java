/*    */ package net.minecraft.network.login.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.security.PublicKey;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.login.INetHandlerLoginClient;
/*    */ import net.minecraft.util.CryptManager;
/*    */ 
/*    */ 
/*    */ public class SPacketEncryptionRequest
/*    */   implements Packet<INetHandlerLoginClient>
/*    */ {
/*    */   private String hashedServerId;
/*    */   private PublicKey publicKey;
/*    */   private byte[] verifyToken;
/*    */   
/*    */   public SPacketEncryptionRequest() {}
/*    */   
/*    */   public SPacketEncryptionRequest(String serverIdIn, PublicKey publicKeyIn, byte[] verifyTokenIn) {
/* 22 */     this.hashedServerId = serverIdIn;
/* 23 */     this.publicKey = publicKeyIn;
/* 24 */     this.verifyToken = verifyTokenIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 32 */     this.hashedServerId = buf.readStringFromBuffer(20);
/* 33 */     this.publicKey = CryptManager.decodePublicKey(buf.readByteArray());
/* 34 */     this.verifyToken = buf.readByteArray();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 42 */     buf.writeString(this.hashedServerId);
/* 43 */     buf.writeByteArray(this.publicKey.getEncoded());
/* 44 */     buf.writeByteArray(this.verifyToken);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerLoginClient handler) {
/* 52 */     handler.handleEncryptionRequest(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getServerId() {
/* 57 */     return this.hashedServerId;
/*    */   }
/*    */ 
/*    */   
/*    */   public PublicKey getPublicKey() {
/* 62 */     return this.publicKey;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getVerifyToken() {
/* 67 */     return this.verifyToken;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\login\server\SPacketEncryptionRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */