/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.scoreboard.ScoreObjective;
/*    */ 
/*    */ 
/*    */ public class SPacketDisplayObjective
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private int position;
/*    */   private String scoreName;
/*    */   
/*    */   public SPacketDisplayObjective() {}
/*    */   
/*    */   public SPacketDisplayObjective(int positionIn, ScoreObjective objective) {
/* 20 */     this.position = positionIn;
/*    */     
/* 22 */     if (objective == null) {
/*    */       
/* 24 */       this.scoreName = "";
/*    */     }
/*    */     else {
/*    */       
/* 28 */       this.scoreName = objective.getName();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 37 */     this.position = buf.readByte();
/* 38 */     this.scoreName = buf.readStringFromBuffer(16);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 46 */     buf.writeByte(this.position);
/* 47 */     buf.writeString(this.scoreName);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 55 */     handler.handleDisplayObjective(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPosition() {
/* 60 */     return this.position;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 65 */     return this.scoreName;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketDisplayObjective.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */