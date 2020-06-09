/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketEntityAttach
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   public int entityId;
/*    */   public int vehicleEntityId;
/*    */   
/*    */   public SPacketEntityAttach() {}
/*    */   
/*    */   public SPacketEntityAttach(Entity entityIn, @Nullable Entity vehicleIn) {
/* 21 */     this.entityId = entityIn.getEntityId();
/* 22 */     this.vehicleEntityId = (vehicleIn != null) ? vehicleIn.getEntityId() : -1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 30 */     this.entityId = buf.readInt();
/* 31 */     this.vehicleEntityId = buf.readInt();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 39 */     buf.writeInt(this.entityId);
/* 40 */     buf.writeInt(this.vehicleEntityId);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 48 */     handler.handleEntityAttach(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityId() {
/* 53 */     return this.entityId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getVehicleEntityId() {
/* 58 */     return this.vehicleEntityId;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketEntityAttach.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */