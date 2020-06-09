/*    */ package net.minecraft.network.play.server;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.io.IOException;
/*    */ import java.util.Map;
/*    */ import net.minecraft.network.INetHandler;
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.PacketBuffer;
/*    */ import net.minecraft.network.play.INetHandlerPlayClient;
/*    */ import net.minecraft.stats.StatBase;
/*    */ import net.minecraft.stats.StatList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SPacketStatistics
/*    */   implements Packet<INetHandlerPlayClient>
/*    */ {
/*    */   private Map<StatBase, Integer> statisticMap;
/*    */   
/*    */   public SPacketStatistics() {}
/*    */   
/*    */   public SPacketStatistics(Map<StatBase, Integer> statisticMapIn) {
/* 23 */     this.statisticMap = statisticMapIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void processPacket(INetHandlerPlayClient handler) {
/* 31 */     handler.handleStatistics(this);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readPacketData(PacketBuffer buf) throws IOException {
/* 39 */     int i = buf.readVarIntFromBuffer();
/* 40 */     this.statisticMap = Maps.newHashMap();
/*    */     
/* 42 */     for (int j = 0; j < i; j++) {
/*    */       
/* 44 */       StatBase statbase = StatList.getOneShotStat(buf.readStringFromBuffer(32767));
/* 45 */       int k = buf.readVarIntFromBuffer();
/*    */       
/* 47 */       if (statbase != null)
/*    */       {
/* 49 */         this.statisticMap.put(statbase, Integer.valueOf(k));
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 59 */     buf.writeVarIntToBuffer(this.statisticMap.size());
/*    */     
/* 61 */     for (Map.Entry<StatBase, Integer> entry : this.statisticMap.entrySet()) {
/*    */       
/* 63 */       buf.writeString(((StatBase)entry.getKey()).statId);
/* 64 */       buf.writeVarIntToBuffer(((Integer)entry.getValue()).intValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public Map<StatBase, Integer> getStatisticMap() {
/* 70 */     return this.statisticMap;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketStatistics.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */