/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ public class ScoreCriteriaHealth
/*    */   extends ScoreCriteria
/*    */ {
/*    */   public ScoreCriteriaHealth(String name) {
/*  7 */     super(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isReadOnly() {
/* 12 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public IScoreCriteria.EnumRenderType getRenderType() {
/* 17 */     return IScoreCriteria.EnumRenderType.HEARTS;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\scoreboard\ScoreCriteriaHealth.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */