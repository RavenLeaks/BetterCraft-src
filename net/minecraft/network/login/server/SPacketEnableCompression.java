/*    */ package net.minecraft.network.login.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.login.INetHandlerLoginClient;
/*    */ 
/*    */ 
/*    */ public class SPacketEnableCompression
/*    */   implements Packet<INetHandlerLoginClient>
/*    */ {
/*    */   private int compressionThreshold;
/*    */   
/*    */   public SPacketEnableCompression() {}
/*    */   
/*    */   public SPacketEnableCompression(int thresholdIn) {
/* 18 */     this.compressionThreshold = thresholdIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 26 */     this.compressionThreshold = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 34 */     buf.writeVarIntToBuffer(this.compressionThreshold);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerLoginClient handler) {
/* 42 */     handler.handleEnableCompression(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCompressionThreshold() {
/* 47 */     return this.compressionThreshold;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\login\server\SPacketEnableCompression.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */