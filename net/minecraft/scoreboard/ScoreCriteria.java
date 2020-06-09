/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ public class ScoreCriteria
/*    */   implements IScoreCriteria
/*    */ {
/*    */   private final String dummyName;
/*    */   
/*    */   public ScoreCriteria(String name) {
/*  9 */     this.dummyName = name;
/* 10 */     IScoreCriteria.INSTANCES.put(name, this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 15 */     return this.dummyName;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isReadOnly() {
/* 20 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public IScoreCriteria.EnumRenderType getRenderType() {
/* 25 */     return IScoreCriteria.EnumRenderType.INTEGER;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\scoreboard\ScoreCriteria.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */