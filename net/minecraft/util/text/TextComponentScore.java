/*     */ package net.minecraft.util.text;
/*     */ 
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.StringUtils;
/*     */ 
/*     */ 
/*     */ public class TextComponentScore
/*     */   extends TextComponentBase
/*     */ {
/*     */   private final String name;
/*     */   private final String objective;
/*  16 */   private String value = "";
/*     */ 
/*     */   
/*     */   public TextComponentScore(String nameIn, String objectiveIn) {
/*  20 */     this.name = nameIn;
/*  21 */     this.objective = objectiveIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  26 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getObjective() {
/*  31 */     return this.objective;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setValue(String valueIn) {
/*  39 */     this.value = valueIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnformattedComponentText() {
/*  48 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public void resolve(ICommandSender sender) {
/*  53 */     MinecraftServer minecraftserver = sender.getServer();
/*     */     
/*  55 */     if (minecraftserver != null && minecraftserver.isAnvilFileSet() && StringUtils.isNullOrEmpty(this.value)) {
/*     */       
/*  57 */       Scoreboard scoreboard = minecraftserver.worldServerForDimension(0).getScoreboard();
/*  58 */       ScoreObjective scoreobjective = scoreboard.getObjective(this.objective);
/*     */       
/*  60 */       if (scoreboard.entityHasObjective(this.name, scoreobjective)) {
/*     */         
/*  62 */         Score score = scoreboard.getOrCreateScore(this.name, scoreobjective);
/*  63 */         setValue(String.format("%d", new Object[] { Integer.valueOf(score.getScorePoints()) }));
/*     */       }
/*     */       else {
/*     */         
/*  67 */         this.value = "";
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TextComponentScore createCopy() {
/*  77 */     TextComponentScore textcomponentscore = new TextComponentScore(this.name, this.objective);
/*  78 */     textcomponentscore.setValue(this.value);
/*  79 */     textcomponentscore.setStyle(getStyle().createShallowCopy());
/*     */     
/*  81 */     for (ITextComponent itextcomponent : getSiblings())
/*     */     {
/*  83 */       textcomponentscore.appendSibling(itextcomponent.createCopy());
/*     */     }
/*     */     
/*  86 */     return textcomponentscore;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  91 */     if (this == p_equals_1_)
/*     */     {
/*  93 */       return true;
/*     */     }
/*  95 */     if (!(p_equals_1_ instanceof TextComponentScore))
/*     */     {
/*  97 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 101 */     TextComponentScore textcomponentscore = (TextComponentScore)p_equals_1_;
/* 102 */     return (this.name.equals(textcomponentscore.name) && this.objective.equals(textcomponentscore.objective) && super.equals(p_equals_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 108 */     return "ScoreComponent{name='" + this.name + '\'' + "objective='" + this.objective + '\'' + ", siblings=" + this.siblings + ", style=" + getStyle() + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\text\TextComponentScore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */