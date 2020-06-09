/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ import net.minecraft.util.text.TextFormatting;
/*    */ 
/*    */ public class ScoreCriteriaColored
/*    */   implements IScoreCriteria
/*    */ {
/*    */   private final String goalName;
/*    */   
/*    */   public ScoreCriteriaColored(String name, TextFormatting format) {
/* 11 */     this.goalName = String.valueOf(name) + format.getFriendlyName();
/* 12 */     IScoreCriteria.INSTANCES.put(this.goalName, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 17 */     return this.goalName;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isReadOnly() {
/* 22 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public IScoreCriteria.EnumRenderType getRenderType() {
/* 27 */     return IScoreCriteria.EnumRenderType.INTEGER;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\scoreboard\ScoreCriteriaColored.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */