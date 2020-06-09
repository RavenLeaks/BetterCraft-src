/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class CPacketSteerBoat
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private boolean left;
/*    */   private boolean right;
/*    */   
/*    */   public CPacketSteerBoat() {}
/*    */   
/*    */   public CPacketSteerBoat(boolean p_i46873_1_, boolean p_i46873_2_) {
/* 19 */     this.left = p_i46873_1_;
/* 20 */     this.right = p_i46873_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 28 */     this.left = buf.readBoolean();
/* 29 */     this.right = buf.readBoolean();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 37 */     buf.writeBoolean(this.left);
/* 38 */     buf.writeBoolean(this.right);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 46 */     handler.processSteerBoat(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getLeft() {
/* 51 */     return this.left;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean getRight() {
/* 56 */     return this.right;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketSteerBoat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */