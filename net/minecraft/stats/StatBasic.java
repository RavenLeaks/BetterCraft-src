/*    */ package net.minecraft.stats;
/*    */ 
/*    */ import net.minecraft.util.text.ITextComponent;
/*    */ 
/*    */ public class StatBasic
/*    */   extends StatBase
/*    */ {
/*    */   public StatBasic(String statIdIn, ITextComponent statNameIn, IStatType typeIn) {
/*  9 */     super(statIdIn, statNameIn, typeIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public StatBasic(String statIdIn, ITextComponent statNameIn) {
/* 14 */     super(statIdIn, statNameIn);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StatBase registerStat() {
/* 22 */     super.registerStat();
/* 23 */     StatList.BASIC_STATS.add(this);
/* 24 */     return this;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\stats\StatBasic.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */