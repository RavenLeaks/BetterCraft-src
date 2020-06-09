/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketWindowProperty
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int windowId;
/*    */   private int property;
/*    */   private int value;
/*    */   
/*    */   public SPacketWindowProperty() {}
/*    */   
/*    */   public SPacketWindowProperty(int windowIdIn, int propertyIn, int valueIn) {
/* 20 */     this.windowId = windowIdIn;
/* 21 */     this.property = propertyIn;
/* 22 */     this.value = valueIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 30 */     handler.handleWindowProperty(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 38 */     this.windowId = buf.readUnsignedByte();
/* 39 */     this.property = buf.readShort();
/* 40 */     this.value = buf.readShort();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 48 */     buf.writeByte(this.windowId);
/* 49 */     buf.writeShort(this.property);
/* 50 */     buf.writeShort(this.value);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWindowId() {
/* 55 */     return this.windowId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getProperty() {
/* 60 */     return this.property;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getValue() {
/* 65 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketWindowProperty.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */