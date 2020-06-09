/*    */ package net.minecraft.stats;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.entity.player.EntityPlayer;
/*    */ import net.minecraft.util.TupleIntJsonSerializable;
/*    */ 
/*    */ public class StatisticsManager
/*    */ {
/* 10 */   protected final Map<StatBase, TupleIntJsonSerializable> statsData = Maps.newConcurrentMap();
/*    */ 
/*    */   
/*    */   public void increaseStat(EntityPlayer player, StatBase stat, int amount) {
/* 14 */     unlockAchievement(player, stat, readStat(stat) + amount);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void unlockAchievement(EntityPlayer playerIn, StatBase statIn, int p_150873_3_) {
/* 22 */     TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(statIn);
/*    */     
/* 24 */     if (tupleintjsonserializable == null) {
/*    */       
/* 26 */       tupleintjsonserializable = new TupleIntJsonSerializable();
/* 27 */       this.statsData.put(statIn, tupleintjsonserializable);
/*    */     } 
/*    */     
/* 30 */     tupleintjsonserializable.setIntegerValue(p_150873_3_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int readStat(StatBase stat) {
/* 38 */     TupleIntJsonSerializable tupleintjsonserializable = this.statsData.get(stat);
/* 39 */     return (tupleintjsonserializable == null) ? 0 : tupleintjsonserializable.getIntegerValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\stats\StatisticsManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */