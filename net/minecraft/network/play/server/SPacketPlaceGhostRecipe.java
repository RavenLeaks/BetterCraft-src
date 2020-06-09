/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.item.crafting.CraftingManager;
/*    */ import net.minecraft.item.crafting.IRecipe;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketPlaceGhostRecipe
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int field_194314_a;
/*    */   private IRecipe field_194315_b;
/*    */   
/*    */   public SPacketPlaceGhostRecipe() {}
/*    */   
/*    */   public SPacketPlaceGhostRecipe(int p_i47615_1_, IRecipe p_i47615_2_) {
/* 21 */     this.field_194314_a = p_i47615_1_;
/* 22 */     this.field_194315_b = p_i47615_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public IRecipe func_194311_a() {
/* 27 */     return this.field_194315_b;
/*    */   }
/*    */ 
/*    */   
/*    */   public int func_194313_b() {
/* 32 */     return this.field_194314_a;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 40 */     this.field_194314_a = buf.readByte();
/* 41 */     this.field_194315_b = CraftingManager.func_193374_a(buf.readVarIntFromBuffer());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 49 */     buf.writeByte(this.field_194314_a);
/* 50 */     buf.writeVarIntToBuffer(CraftingManager.func_193375_a(this.field_194315_b));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 58 */     handler.func_194307_a(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketPlaceGhostRecipe.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */