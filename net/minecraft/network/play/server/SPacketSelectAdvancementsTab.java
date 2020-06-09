/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class SPacketSelectAdvancementsTab
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   @Nullable
/*    */   private ResourceLocation field_194155_a;
/*    */   
/*    */   public SPacketSelectAdvancementsTab() {}
/*    */   
/*    */   public SPacketSelectAdvancementsTab(@Nullable ResourceLocation p_i47596_1_) {
/* 21 */     this.field_194155_a = p_i47596_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 29 */     handler.func_194022_a(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 37 */     if (buf.readBoolean())
/*    */     {
/* 39 */       this.field_194155_a = buf.func_192575_l();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 48 */     buf.writeBoolean((this.field_194155_a != null));
/*    */     
/* 50 */     if (this.field_194155_a != null)
/*    */     {
/* 52 */       buf.func_192572_a(this.field_194155_a);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public ResourceLocation func_194154_a() {
/* 59 */     return this.field_194155_a;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketSelectAdvancementsTab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */