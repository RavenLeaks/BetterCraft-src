/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import net.minecraft.item.ItemStack;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.NonNullList;
/*    */ 
/*    */ 
/*    */ public class SPacketWindowItems
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int windowId;
/*    */   private List<ItemStack> itemStacks;
/*    */   
/*    */   public SPacketWindowItems() {}
/*    */   
/*    */   public SPacketWindowItems(int p_i47317_1_, NonNullList<ItemStack> p_i47317_2_) {
/* 22 */     this.windowId = p_i47317_1_;
/* 23 */     this.itemStacks = (List<ItemStack>)NonNullList.func_191197_a(p_i47317_2_.size(), ItemStack.field_190927_a);
/*    */     
/* 25 */     for (int i = 0; i < this.itemStacks.size(); i++) {
/*    */       
/* 27 */       ItemStack itemstack = (ItemStack)p_i47317_2_.get(i);
/* 28 */       this.itemStacks.set(i, itemstack.copy());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 37 */     this.windowId = buf.readUnsignedByte();
/* 38 */     int i = buf.readShort();
/* 39 */     this.itemStacks = (List<ItemStack>)NonNullList.func_191197_a(i, ItemStack.field_190927_a);
/*    */     
/* 41 */     for (int j = 0; j < i; j++)
/*    */     {
/* 43 */       this.itemStacks.set(j, buf.readItemStackFromBuffer());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 52 */     buf.writeByte(this.windowId);
/* 53 */     buf.writeShort(this.itemStacks.size());
/*    */     
/* 55 */     for (ItemStack itemstack : this.itemStacks)
/*    */     {
/* 57 */       buf.writeItemStackToBuffer(itemstack);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 66 */     handler.handleWindowItems(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWindowId() {
/* 71 */     return this.windowId;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<ItemStack> getItemStacks() {
/* 76 */     return this.itemStacks;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketWindowItems.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */