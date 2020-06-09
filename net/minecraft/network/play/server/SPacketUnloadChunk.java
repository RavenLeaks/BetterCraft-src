/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketUnloadChunk
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int x;
/*    */   private int z;
/*    */   
/*    */   public SPacketUnloadChunk() {}
/*    */   
/*    */   public SPacketUnloadChunk(int xIn, int zIn) {
/* 19 */     this.x = xIn;
/* 20 */     this.z = zIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 28 */     this.x = buf.readInt();
/* 29 */     this.z = buf.readInt();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 37 */     buf.writeInt(this.x);
/* 38 */     buf.writeInt(this.z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 46 */     handler.processChunkUnload(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getX() {
/* 51 */     return this.x;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getZ() {
/* 56 */     return this.z;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketUnloadChunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */