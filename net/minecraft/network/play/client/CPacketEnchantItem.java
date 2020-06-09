/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class CPacketEnchantItem
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private int windowId;
/*    */   private int button;
/*    */   
/*    */   public CPacketEnchantItem() {}
/*    */   
/*    */   public CPacketEnchantItem(int windowIdIn, int buttonIn) {
/* 19 */     this.windowId = windowIdIn;
/* 20 */     this.button = buttonIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 28 */     handler.processEnchantItem(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 36 */     this.windowId = buf.readByte();
/* 37 */     this.button = buf.readByte();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 45 */     buf.writeByte(this.windowId);
/* 46 */     buf.writeByte(this.button);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWindowId() {
/* 51 */     return this.windowId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getButton() {
/* 56 */     return this.button;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketEnchantItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */