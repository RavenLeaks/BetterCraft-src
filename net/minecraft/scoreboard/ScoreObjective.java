/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScoreObjective
/*    */ {
/*    */   private final Scoreboard theScoreboard;
/*    */   private final String name;
/*    */   private final IScoreCriteria objectiveCriteria;
/*    */   private IScoreCriteria.EnumRenderType renderType;
/*    */   private String displayName;
/*    */   
/*    */   public ScoreObjective(Scoreboard theScoreboardIn, String nameIn, IScoreCriteria objectiveCriteriaIn) {
/* 15 */     this.theScoreboard = theScoreboardIn;
/* 16 */     this.name = nameIn;
/* 17 */     this.objectiveCriteria = objectiveCriteriaIn;
/* 18 */     this.displayName = nameIn;
/* 19 */     this.renderType = objectiveCriteriaIn.getRenderType();
/*    */   }
/*    */ 
/*    */   
/*    */   public Scoreboard getScoreboard() {
/* 24 */     return this.theScoreboard;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 29 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public IScoreCriteria getCriteria() {
/* 34 */     return this.objectiveCriteria;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayName() {
/* 39 */     return this.displayName;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDisplayName(String nameIn) {
/* 44 */     this.displayName = nameIn;
/* 45 */     this.theScoreboard.onObjectiveDisplayNameChanged(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public IScoreCriteria.EnumRenderType getRenderType() {
/* 50 */     return this.renderType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setRenderType(IScoreCriteria.EnumRenderType type) {
/* 55 */     this.renderType = type;
/* 56 */     this.theScoreboard.onObjectiveDisplayNameChanged(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\scoreboard\ScoreObjective.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */