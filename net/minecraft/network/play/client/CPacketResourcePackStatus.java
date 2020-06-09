/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class CPacketResourcePackStatus
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private Action action;
/*    */   
/*    */   public CPacketResourcePackStatus() {}
/*    */   
/*    */   public CPacketResourcePackStatus(Action p_i47156_1_) {
/* 18 */     this.action = p_i47156_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 26 */     this.action = (Action)buf.readEnumValue(Action.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 34 */     buf.writeEnumValue(this.action);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 42 */     handler.handleResourcePackStatus(this);
/*    */   }
/*    */   
/*    */   public enum Action
/*    */   {
/* 47 */     SUCCESSFULLY_LOADED,
/* 48 */     DECLINED,
/* 49 */     FAILED_DOWNLOAD,
/* 50 */     ACCEPTED;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketResourcePackStatus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */