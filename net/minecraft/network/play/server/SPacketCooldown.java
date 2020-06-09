/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.item.Item;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketCooldown
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private Item item;
/*    */   private int ticks;
/*    */   
/*    */   public SPacketCooldown() {}
/*    */   
/*    */   public SPacketCooldown(Item itemIn, int ticksIn) {
/* 20 */     this.item = itemIn;
/* 21 */     this.ticks = ticksIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 29 */     this.item = Item.getItemById(buf.readVarIntFromBuffer());
/* 30 */     this.ticks = buf.readVarIntFromBuffer();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 38 */     buf.writeVarIntToBuffer(Item.getIdFromItem(this.item));
/* 39 */     buf.writeVarIntToBuffer(this.ticks);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 47 */     handler.handleCooldown(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public Item getItem() {
/* 52 */     return this.item;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTicks() {
/* 57 */     return this.ticks;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketCooldown.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */