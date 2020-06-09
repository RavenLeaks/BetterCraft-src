/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketSetPassengers
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int entityId;
/*    */   private int[] passengerIds;
/*    */   
/*    */   public SPacketSetPassengers() {}
/*    */   
/*    */   public SPacketSetPassengers(Entity entityIn) {
/* 21 */     this.entityId = entityIn.getEntityId();
/* 22 */     List<Entity> list = entityIn.getPassengers();
/* 23 */     this.passengerIds = new int[list.size()];
/*    */     
/* 25 */     for (int i = 0; i < list.size(); i++)
/*    */     {
/* 27 */       this.passengerIds[i] = ((Entity)list.get(i)).getEntityId();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 36 */     this.entityId = buf.readVarIntFromBuffer();
/* 37 */     this.passengerIds = buf.readVarIntArray();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 45 */     buf.writeVarIntToBuffer(this.entityId);
/* 46 */     buf.writeVarIntArray(this.passengerIds);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 54 */     handler.handleSetPassengers(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getPassengerIds() {
/* 59 */     return this.passengerIds;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityId() {
/* 64 */     return this.entityId;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketSetPassengers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */