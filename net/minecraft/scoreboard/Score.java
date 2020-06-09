/*     */ package net.minecraft.scoreboard;
/*     */ 
/*     */ import java.util.Comparator;
/*     */ 
/*     */ public class Score
/*     */ {
/*   7 */   public static final Comparator<Score> SCORE_COMPARATOR = new Comparator<Score>()
/*     */     {
/*     */       public int compare(Score p_compare_1_, Score p_compare_2_)
/*     */       {
/*  11 */         if (p_compare_1_.getScorePoints() > p_compare_2_.getScorePoints())
/*     */         {
/*  13 */           return 1;
/*     */         }
/*     */ 
/*     */         
/*  17 */         return (p_compare_1_.getScorePoints() < p_compare_2_.getScorePoints()) ? -1 : p_compare_2_.getPlayerName().compareToIgnoreCase(p_compare_1_.getPlayerName());
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private final Scoreboard theScoreboard;
/*     */   private final ScoreObjective theScoreObjective;
/*     */   private final String scorePlayerName;
/*     */   private int scorePoints;
/*     */   private boolean locked;
/*     */   private boolean forceUpdate;
/*     */   
/*     */   public Score(Scoreboard theScoreboardIn, ScoreObjective theScoreObjectiveIn, String scorePlayerNameIn) {
/*  30 */     this.theScoreboard = theScoreboardIn;
/*  31 */     this.theScoreObjective = theScoreObjectiveIn;
/*  32 */     this.scorePlayerName = scorePlayerNameIn;
/*  33 */     this.forceUpdate = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void increaseScore(int amount) {
/*  38 */     if (this.theScoreObjective.getCriteria().isReadOnly())
/*     */     {
/*  40 */       throw new IllegalStateException("Cannot modify read-only score");
/*     */     }
/*     */ 
/*     */     
/*  44 */     setScorePoints(getScorePoints() + amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decreaseScore(int amount) {
/*  50 */     if (this.theScoreObjective.getCriteria().isReadOnly())
/*     */     {
/*  52 */       throw new IllegalStateException("Cannot modify read-only score");
/*     */     }
/*     */ 
/*     */     
/*  56 */     setScorePoints(getScorePoints() - amount);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void incrementScore() {
/*  62 */     if (this.theScoreObjective.getCriteria().isReadOnly())
/*     */     {
/*  64 */       throw new IllegalStateException("Cannot modify read-only score");
/*     */     }
/*     */ 
/*     */     
/*  68 */     increaseScore(1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getScorePoints() {
/*  74 */     return this.scorePoints;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setScorePoints(int points) {
/*  79 */     int i = this.scorePoints;
/*  80 */     this.scorePoints = points;
/*     */     
/*  82 */     if (i != points || this.forceUpdate) {
/*     */       
/*  84 */       this.forceUpdate = false;
/*  85 */       getScoreScoreboard().onScoreUpdated(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ScoreObjective getObjective() {
/*  91 */     return this.theScoreObjective;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPlayerName() {
/*  99 */     return this.scorePlayerName;
/*     */   }
/*     */ 
/*     */   
/*     */   public Scoreboard getScoreScoreboard() {
/* 104 */     return this.theScoreboard;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isLocked() {
/* 109 */     return this.locked;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setLocked(boolean locked) {
/* 114 */     this.locked = locked;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\scoreboard\Score.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */