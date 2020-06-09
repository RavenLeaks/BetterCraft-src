/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ import net.minecraft.stats.StatBase;
/*    */ 
/*    */ public class ScoreCriteriaStat
/*    */   extends ScoreCriteria
/*    */ {
/*    */   private final StatBase stat;
/*    */   
/*    */   public ScoreCriteriaStat(StatBase statIn) {
/* 11 */     super(statIn.statId);
/* 12 */     this.stat = statIn;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\scoreboard\ScoreCriteriaStat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */