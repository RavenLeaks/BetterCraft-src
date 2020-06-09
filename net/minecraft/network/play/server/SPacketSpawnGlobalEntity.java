/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPacketSpawnGlobalEntity
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   public int entityId;
/*    */   public double x;
/*    */   public double y;
/*    */   public double z;
/*    */   public int type;
/*    */   
/*    */   public SPacketSpawnGlobalEntity() {}
/*    */   
/*    */   public SPacketSpawnGlobalEntity(Entity entityIn) {
/* 24 */     this.entityId = entityIn.getEntityId();
/* 25 */     this.x = entityIn.posX;
/* 26 */     this.y = entityIn.posY;
/* 27 */     this.z = entityIn.posZ;
/*    */     
/* 29 */     if (entityIn instanceof net.minecraft.entity.effect.EntityLightningBolt)
/*    */     {
/* 31 */       this.type = 1;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 40 */     this.entityId = buf.readVarIntFromBuffer();
/* 41 */     this.type = buf.readByte();
/* 42 */     this.x = buf.readDouble();
/* 43 */     this.y = buf.readDouble();
/* 44 */     this.z = buf.readDouble();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 52 */     buf.writeVarIntToBuffer(this.entityId);
/* 53 */     buf.writeByte(this.type);
/* 54 */     buf.writeDouble(this.x);
/* 55 */     buf.writeDouble(this.y);
/* 56 */     buf.writeDouble(this.z);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 64 */     handler.handleSpawnGlobalEntity(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityId() {
/* 69 */     return this.entityId;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getX() {
/* 74 */     return this.x;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getY() {
/* 79 */     return this.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getZ() {
/* 84 */     return this.z;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getType() {
/* 89 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketSpawnGlobalEntity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */