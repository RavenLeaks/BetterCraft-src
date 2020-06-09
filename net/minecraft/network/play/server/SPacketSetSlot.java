/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ public class SPacketSetSlot implements Packet<INetHandlerPlayClient> {
/*    */   private int windowId;
/*    */   private int slot;
/* 13 */   private ItemStack item = ItemStack.field_190927_a;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public SPacketSetSlot(int windowIdIn, int slotIn, ItemStack itemIn) {
/* 21 */     this.windowId = windowIdIn;
/* 22 */     this.slot = slotIn;
/* 23 */     this.item = itemIn.copy();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 31 */     handler.handleSetSlot(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 39 */     this.windowId = buf.readByte();
/* 40 */     this.slot = buf.readShort();
/* 41 */     this.item = buf.readItemStackFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 49 */     buf.writeByte(this.windowId);
/* 50 */     buf.writeShort(this.slot);
/* 51 */     buf.writeItemStackToBuffer(this.item);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWindowId() {
/* 56 */     return this.windowId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSlot() {
/* 61 */     return this.slot;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getStack() {
/* 66 */     return this.item;
/*    */   }
/*    */   
/*    */   public SPacketSetSlot() {}
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketSetSlot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */