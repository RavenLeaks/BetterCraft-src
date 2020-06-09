/*    */ package net.minecraft.network.play.client;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.advancements.Advancement;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayServer;
/*    */ import net.minecraft.util.ResourceLocation;
/*    */ 
/*    */ 
/*    */ public class CPacketSeenAdvancements
/*    */   implements Packet<INetHandlerPlayServer>
/*    */ {
/*    */   private Action field_194166_a;
/*    */   private ResourceLocation field_194167_b;
/*    */   
/*    */   public CPacketSeenAdvancements() {}
/*    */   
/*    */   public CPacketSeenAdvancements(Action p_i47595_1_, @Nullable ResourceLocation p_i47595_2_) {
/* 22 */     this.field_194166_a = p_i47595_1_;
/* 23 */     this.field_194167_b = p_i47595_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public static CPacketSeenAdvancements func_194163_a(Advancement p_194163_0_) {
/* 28 */     return new CPacketSeenAdvancements(Action.OPENED_TAB, p_194163_0_.func_192067_g());
/*    */   }
/*    */ 
/*    */   
/*    */   public static CPacketSeenAdvancements func_194164_a() {
/* 33 */     return new CPacketSeenAdvancements(Action.CLOSED_SCREEN, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 41 */     this.field_194166_a = (Action)buf.readEnumValue(Action.class);
/*    */     
/* 43 */     if (this.field_194166_a == Action.OPENED_TAB)
/*    */     {
/* 45 */       this.field_194167_b = buf.func_192575_l();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 54 */     buf.writeEnumValue(this.field_194166_a);
/*    */     
/* 56 */     if (this.field_194166_a == Action.OPENED_TAB)
/*    */     {
/* 58 */       buf.func_192572_a(this.field_194167_b);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayServer handler) {
/* 67 */     handler.func_194027_a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public Action func_194162_b() {
/* 72 */     return this.field_194166_a;
/*    */   }
/*    */ 
/*    */   
/*    */   public ResourceLocation func_194165_c() {
/* 77 */     return this.field_194167_b;
/*    */   }
/*    */   
/*    */   public enum Action
/*    */   {
/* 82 */     OPENED_TAB,
/* 83 */     CLOSED_SCREEN;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\client\CPacketSeenAdvancements.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */