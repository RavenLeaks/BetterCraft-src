/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.item.crafting.CraftingManager;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ 
/*    */ 
/*    */ public class CPacketPlaceRecipe
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private int field_194320_a;
/*    */   private IRecipe field_194321_b;
/*    */   private boolean field_194322_c;
/*    */   
/*    */   public CPacketPlaceRecipe() {}
/*    */   
/*    */   public CPacketPlaceRecipe(int p_i47614_1_, IRecipe p_i47614_2_, boolean p_i47614_3_) {
/* 22 */     this.field_194320_a = p_i47614_1_;
/* 23 */     this.field_194321_b = p_i47614_2_;
/* 24 */     this.field_194322_c = p_i47614_3_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 32 */     this.field_194320_a = buf.readByte();
/* 33 */     this.field_194321_b = CraftingManager.func_193374_a(buf.readVarIntFromBuffer());
/* 34 */     this.field_194322_c = buf.readBoolean();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 42 */     buf.writeByte(this.field_194320_a);
/* 43 */     buf.writeVarIntToBuffer(CraftingManager.func_193375_a(this.field_194321_b));
/* 44 */     buf.writeBoolean(this.field_194322_c);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 52 */     handler.func_194308_a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_194318_a() {
/* 57 */     return this.field_194320_a;
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipe func_194317_b() {
/* 62 */     return this.field_194321_b;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_194319_c() {
/* 67 */     return this.field_194322_c;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketPlaceRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */