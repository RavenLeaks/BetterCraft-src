/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.scoreboard.IScoreCriteria;
/*    */ import net.minecraft.scoreboard.ScoreObjective;
/*    */ 
/*    */ 
/*    */ public class SPacketScoreboardObjective
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private String objectiveName;
/*    */   private String objectiveValue;
/*    */   private IScoreCriteria.EnumRenderType type;
/*    */   private int action;
/*    */   
/*    */   public SPacketScoreboardObjective() {}
/*    */   
/*    */   public SPacketScoreboardObjective(ScoreObjective objective, int actionIn) {
/* 23 */     this.objectiveName = objective.getName();
/* 24 */     this.objectiveValue = objective.getDisplayName();
/* 25 */     this.type = objective.getCriteria().getRenderType();
/* 26 */     this.action = actionIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 34 */     this.objectiveName = buf.readStringFromBuffer(16);
/* 35 */     this.action = buf.readByte();
/*    */     
/* 37 */     if (this.action == 0 || this.action == 2) {
/*    */       
/* 39 */       this.objectiveValue = buf.readStringFromBuffer(32);
/* 40 */       this.type = IScoreCriteria.EnumRenderType.getByName(buf.readStringFromBuffer(16));
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 49 */     buf.writeString(this.objectiveName);
/* 50 */     buf.writeByte(this.action);
/*    */     
/* 52 */     if (this.action == 0 || this.action == 2) {
/*    */       
/* 54 */       buf.writeString(this.objectiveValue);
/* 55 */       buf.writeString(this.type.getRenderType());
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 64 */     handler.handleScoreboardObjective(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getObjectiveName() {
/* 69 */     return this.objectiveName;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getObjectiveValue() {
/* 74 */     return this.objectiveValue;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAction() {
/* 79 */     return this.action;
/*    */   }
/*    */ 
/*    */   
/*    */   public IScoreCriteria.EnumRenderType getRenderType() {
/* 84 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketScoreboardObjective.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */