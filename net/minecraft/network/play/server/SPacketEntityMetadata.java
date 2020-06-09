/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.datasync.EntityDataManager;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketEntityMetadata
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   public int entityId;
/*    */   public List<EntityDataManager.DataEntry<?>> dataManagerEntries;
/*    */   
/*    */   public SPacketEntityMetadata() {}
/*    */   
/*    */   public SPacketEntityMetadata(int entityIdIn, EntityDataManager dataManagerIn, boolean sendAll) {
/* 21 */     this.entityId = entityIdIn;
/*    */     
/* 23 */     if (sendAll) {
/*    */       
/* 25 */       this.dataManagerEntries = dataManagerIn.getAll();
/* 26 */       dataManagerIn.setClean();
/*    */     }
/*    */     else {
/*    */       
/* 30 */       this.dataManagerEntries = dataManagerIn.getDirty();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 39 */     this.entityId = buf.readVarIntFromBuffer();
/* 40 */     this.dataManagerEntries = EntityDataManager.readEntries(buf);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 48 */     buf.writeVarIntToBuffer(this.entityId);
/* 49 */     EntityDataManager.writeEntries(this.dataManagerEntries, buf);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 57 */     handler.handleEntityMetadata(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<EntityDataManager.DataEntry<?>> getDataManagerEntries() {
/* 62 */     return this.dataManagerEntries;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityId() {
/* 67 */     return this.entityId;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketEntityMetadata.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */