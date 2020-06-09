/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketDestroyEntities
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int[] entityIDs;
/*    */   
/*    */   public SPacketDestroyEntities() {}
/*    */   
/*    */   public SPacketDestroyEntities(int... entityIdsIn) {
/* 18 */     this.entityIDs = entityIdsIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 26 */     this.entityIDs = new int[buf.readVarIntFromBuffer()];
/*    */     
/* 28 */     for (int i = 0; i < this.entityIDs.length; i++)
/*    */     {
/* 30 */       this.entityIDs[i] = buf.readVarIntFromBuffer();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 39 */     buf.writeVarIntToBuffer(this.entityIDs.length); byte b;
/*    */     int i, arrayOfInt[];
/* 41 */     for (i = (arrayOfInt = this.entityIDs).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/*    */       
/* 43 */       buf.writeVarIntToBuffer(j);
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 52 */     handler.handleDestroyEntities(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getEntityIDs() {
/* 57 */     return this.entityIDs;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketDestroyEntities.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */