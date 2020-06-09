/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.EnumHand;
/*    */ 
/*    */ 
/*    */ public class CPacketAnimation
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private EnumHand hand;
/*    */   
/*    */   public CPacketAnimation() {}
/*    */   
/*    */   public CPacketAnimation(EnumHand handIn) {
/* 19 */     this.hand = handIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 27 */     this.hand = (EnumHand)buf.readEnumValue(EnumHand.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 35 */     buf.writeEnumValue((Enum)this.hand);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 43 */     handler.handleAnimation(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumHand getHand() {
/* 48 */     return this.hand;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */