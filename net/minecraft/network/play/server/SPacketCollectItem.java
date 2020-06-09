/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketCollectItem
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int collectedItemEntityId;
/*    */   private int entityId;
/*    */   private int field_191209_c;
/*    */   
/*    */   public SPacketCollectItem() {}
/*    */   
/*    */   public SPacketCollectItem(int p_i47316_1_, int p_i47316_2_, int p_i47316_3_) {
/* 20 */     this.collectedItemEntityId = p_i47316_1_;
/* 21 */     this.entityId = p_i47316_2_;
/* 22 */     this.field_191209_c = p_i47316_3_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 30 */     this.collectedItemEntityId = buf.readVarIntFromBuffer();
/* 31 */     this.entityId = buf.readVarIntFromBuffer();
/* 32 */     this.field_191209_c = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 40 */     buf.writeVarIntToBuffer(this.collectedItemEntityId);
/* 41 */     buf.writeVarIntToBuffer(this.entityId);
/* 42 */     buf.writeVarIntToBuffer(this.field_191209_c);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 50 */     handler.handleCollectItem(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getCollectedItemEntityID() {
/* 55 */     return this.collectedItemEntityId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityID() {
/* 60 */     return this.entityId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_191208_c() {
/* 65 */     return this.field_191209_c;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketCollectItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */