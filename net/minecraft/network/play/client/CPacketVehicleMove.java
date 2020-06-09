/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class CPacketVehicleMove
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private double x;
/*    */   private double y;
/*    */   private double z;
/*    */   private float yaw;
/*    */   private float pitch;
/*    */   
/*    */   public CPacketVehicleMove() {}
/*    */   
/*    */   public CPacketVehicleMove(Entity entityIn) {
/* 23 */     this.x = entityIn.posX;
/* 24 */     this.y = entityIn.posY;
/* 25 */     this.z = entityIn.posZ;
/* 26 */     this.yaw = entityIn.rotationYaw;
/* 27 */     this.pitch = entityIn.rotationPitch;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 35 */     this.x = buf.readDouble();
/* 36 */     this.y = buf.readDouble();
/* 37 */     this.z = buf.readDouble();
/* 38 */     this.yaw = buf.readFloat();
/* 39 */     this.pitch = buf.readFloat();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 47 */     buf.writeDouble(this.x);
/* 48 */     buf.writeDouble(this.y);
/* 49 */     buf.writeDouble(this.z);
/* 50 */     buf.writeFloat(this.yaw);
/* 51 */     buf.writeFloat(this.pitch);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 59 */     handler.processVehicleMove(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getX() {
/* 64 */     return this.x;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getY() {
/* 69 */     return this.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getZ() {
/* 74 */     return this.z;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getYaw() {
/* 79 */     return this.yaw;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getPitch() {
/* 84 */     return this.pitch;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketVehicleMove.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */