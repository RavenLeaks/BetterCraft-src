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
/*    */ public class SPacketAnimation
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int entityId;
/*    */   private int type;
/*    */   
/*    */   public SPacketAnimation() {}
/*    */   
/*    */   public SPacketAnimation(Entity entityIn, int typeIn) {
/* 20 */     this.entityId = entityIn.getEntityId();
/* 21 */     this.type = typeIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 29 */     this.entityId = buf.readVarIntFromBuffer();
/* 30 */     this.type = buf.readUnsignedByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 38 */     buf.writeVarIntToBuffer(this.entityId);
/* 39 */     buf.writeByte(this.type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 47 */     handler.handleAnimation(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getEntityID() {
/* 52 */     return this.entityId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAnimationType() {
/* 57 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */