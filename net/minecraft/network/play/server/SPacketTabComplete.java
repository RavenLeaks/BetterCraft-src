/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ 
/*    */ 
/*    */ public class SPacketTabComplete
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private String[] matches;
/*    */   
/*    */   public SPacketTabComplete() {}
/*    */   
/*    */   public SPacketTabComplete(String[] matchesIn) {
/* 18 */     this.matches = matchesIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 26 */     this.matches = new String[buf.readVarIntFromBuffer()];
/*    */     
/* 28 */     for (int i = 0; i < this.matches.length; i++)
/*    */     {
/* 30 */       this.matches[i] = buf.readStringFromBuffer(32767);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 39 */     buf.writeVarIntToBuffer(this.matches.length); byte b; int i;
/*    */     String[] arrayOfString;
/* 41 */     for (i = (arrayOfString = this.matches).length, b = 0; b < i; ) { String s = arrayOfString[b];
/*    */       
/* 43 */       buf.writeString(s);
/*    */       b++; }
/*    */   
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 52 */     handler.handleTabComplete(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String[] getMatches() {
/* 57 */     return this.matches;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketTabComplete.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */