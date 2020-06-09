/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.item.EntityXPOrb;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketSpawnExperienceOrb
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   public int entityID;
/*    */   public double posX;
/*    */   public double posY;
/*    */   public double posZ;
/*    */   public int xpValue;
/*    */   
/*    */   public SPacketSpawnExperienceOrb() {}
/*    */   
/*    */   public SPacketSpawnExperienceOrb(EntityXPOrb orb) {
/* 23 */     this.entityID = orb.getEntityId();
/* 24 */     this.posX = orb.posX;
/* 25 */     this.posY = orb.posY;
/* 26 */     this.posZ = orb.posZ;
/* 27 */     this.xpValue = orb.getXpValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 35 */     this.entityID = buf.readVarIntFromBuffer();
/* 36 */     this.posX = buf.readDouble();
/* 37 */     this.posY = buf.readDouble();
/* 38 */     this.posZ = buf.readDouble();
/* 39 */     this.xpValue = buf.readShort();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 47 */     buf.writeVarIntToBuffer(this.entityID);
/* 48 */     buf.writeDouble(this.posX);
/* 49 */     buf.writeDouble(this.posY);
/* 50 */     buf.writeDouble(this.posZ);
/* 51 */     buf.writeShort(this.xpValue);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 59 */     handler.handleSpawnExperienceOrb(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityID() {
/* 64 */     return this.entityID;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getX() {
/* 69 */     return this.posX;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getY() {
/* 74 */     return this.posY;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getZ() {
/* 79 */     return this.posZ;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getXPValue() {
/* 84 */     return this.xpValue;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketSpawnExperienceOrb.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */