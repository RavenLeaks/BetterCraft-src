/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPacketPlayerListHeaderFooter
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private ITextComponent header;
/*    */   private ITextComponent footer;
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 19 */     this.header = buf.readTextComponent();
/* 20 */     this.footer = buf.readTextComponent();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 28 */     buf.writeTextComponent(this.header);
/* 29 */     buf.writeTextComponent(this.footer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 37 */     handler.handlePlayerListHeaderFooter(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public ITextComponent getHeader() {
/* 42 */     return this.header;
/*    */   }
/*    */ 
/*    */   
/*    */   public ITextComponent getFooter() {
/* 47 */     return this.footer;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketPlayerListHeaderFooter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */