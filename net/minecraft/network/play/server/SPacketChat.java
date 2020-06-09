/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.text.ChatType;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ 
/*    */ 
/*    */ public class SPacketChat
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private ITextComponent chatComponent;
/*    */   private ChatType type;
/*    */   
/*    */   public SPacketChat() {}
/*    */   
/*    */   public SPacketChat(ITextComponent componentIn) {
/* 21 */     this(componentIn, ChatType.SYSTEM);
/*    */   }
/*    */ 
/*    */   
/*    */   public SPacketChat(ITextComponent p_i47428_1_, ChatType p_i47428_2_) {
/* 26 */     this.chatComponent = p_i47428_1_;
/* 27 */     this.type = p_i47428_2_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 35 */     this.chatComponent = buf.readTextComponent();
/* 36 */     this.type = ChatType.func_192582_a(buf.readByte());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 44 */     buf.writeTextComponent(this.chatComponent);
/* 45 */     buf.writeByte(this.type.func_192583_a());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 53 */     handler.handleChat(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public ITextComponent getChatComponent() {
/* 58 */     return this.chatComponent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSystem() {
/* 66 */     return !(this.type != ChatType.SYSTEM && this.type != ChatType.GAME_INFO);
/*    */   }
/*    */ 
/*    */   
/*    */   public ChatType func_192590_c() {
/* 71 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */