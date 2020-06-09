/*    */ package net.minecraft.realms;
/*    */ 
/*    */ import net.minecraft.world.storage.WorldSummary;
/*    */ 
/*    */ public class RealmsLevelSummary
/*    */   implements Comparable<RealmsLevelSummary>
/*    */ {
/*    */   private final WorldSummary levelSummary;
/*    */   
/*    */   public RealmsLevelSummary(WorldSummary levelSummaryIn) {
/* 11 */     this.levelSummary = levelSummaryIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGameMode() {
/* 16 */     return this.levelSummary.getEnumGameType().getID();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLevelId() {
/* 21 */     return this.levelSummary.getFileName();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasCheats() {
/* 26 */     return this.levelSummary.getCheatsEnabled();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHardcore() {
/* 31 */     return this.levelSummary.isHardcoreModeEnabled();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isRequiresConversion() {
/* 36 */     return this.levelSummary.requiresConversion();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLevelName() {
/* 41 */     return this.levelSummary.getDisplayName();
/*    */   }
/*    */ 
/*    */   
/*    */   public long getLastPlayed() {
/* 46 */     return this.levelSummary.getLastTimePlayed();
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(WorldSummary p_compareTo_1_) {
/* 51 */     return this.levelSummary.compareTo(p_compareTo_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public long getSizeOnDisk() {
/* 56 */     return this.levelSummary.getSizeOnDisk();
/*    */   }
/*    */ 
/*    */   
/*    */   public int compareTo(RealmsLevelSummary p_compareTo_1_) {
/* 61 */     if (this.levelSummary.getLastTimePlayed() < p_compareTo_1_.getLastPlayed())
/*    */     {
/* 63 */       return 1;
/*    */     }
/*    */ 
/*    */     
/* 67 */     return (this.levelSummary.getLastTimePlayed() > p_compareTo_1_.getLastPlayed()) ? -1 : this.levelSummary.getFileName().compareTo(p_compareTo_1_.getLevelId());
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\realms\RealmsLevelSummary.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */