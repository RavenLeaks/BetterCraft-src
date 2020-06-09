/*    */ package net.minecraft.scoreboard;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Map;
/*    */ import net.minecraft.util.text.TextFormatting;
/*    */ 
/*    */ public interface IScoreCriteria
/*    */ {
/*  9 */   public static final Map<String, IScoreCriteria> INSTANCES = Maps.newHashMap();
/* 10 */   public static final IScoreCriteria DUMMY = new ScoreCriteria("dummy");
/* 11 */   public static final IScoreCriteria TRIGGER = new ScoreCriteria("trigger");
/* 12 */   public static final IScoreCriteria DEATH_COUNT = new ScoreCriteria("deathCount");
/* 13 */   public static final IScoreCriteria PLAYER_KILL_COUNT = new ScoreCriteria("playerKillCount");
/* 14 */   public static final IScoreCriteria TOTAL_KILL_COUNT = new ScoreCriteria("totalKillCount");
/* 15 */   public static final IScoreCriteria HEALTH = new ScoreCriteriaHealth("health");
/* 16 */   public static final IScoreCriteria FOOD = new ScoreCriteriaReadOnly("food");
/* 17 */   public static final IScoreCriteria AIR = new ScoreCriteriaReadOnly("air");
/* 18 */   public static final IScoreCriteria ARMOR = new ScoreCriteriaReadOnly("armor");
/* 19 */   public static final IScoreCriteria XP = new ScoreCriteriaReadOnly("xp");
/* 20 */   public static final IScoreCriteria LEVEL = new ScoreCriteriaReadOnly("level");
/* 21 */   public static final IScoreCriteria[] TEAM_KILL = new IScoreCriteria[] { new ScoreCriteriaColored("teamkill.", TextFormatting.BLACK), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_BLUE), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_GREEN), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_AQUA), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_RED), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_PURPLE), new ScoreCriteriaColored("teamkill.", TextFormatting.GOLD), new ScoreCriteriaColored("teamkill.", TextFormatting.GRAY), new ScoreCriteriaColored("teamkill.", TextFormatting.DARK_GRAY), new ScoreCriteriaColored("teamkill.", TextFormatting.BLUE), new ScoreCriteriaColored("teamkill.", TextFormatting.GREEN), new ScoreCriteriaColored("teamkill.", TextFormatting.AQUA), new ScoreCriteriaColored("teamkill.", TextFormatting.RED), new ScoreCriteriaColored("teamkill.", TextFormatting.LIGHT_PURPLE), new ScoreCriteriaColored("teamkill.", TextFormatting.YELLOW), new ScoreCriteriaColored("teamkill.", TextFormatting.WHITE) };
/* 22 */   public static final IScoreCriteria[] KILLED_BY_TEAM = new IScoreCriteria[] { new ScoreCriteriaColored("killedByTeam.", TextFormatting.BLACK), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_BLUE), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_GREEN), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_AQUA), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_RED), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_PURPLE), new ScoreCriteriaColored("killedByTeam.", TextFormatting.GOLD), new ScoreCriteriaColored("killedByTeam.", TextFormatting.GRAY), new ScoreCriteriaColored("killedByTeam.", TextFormatting.DARK_GRAY), new ScoreCriteriaColored("killedByTeam.", TextFormatting.BLUE), new ScoreCriteriaColored("killedByTeam.", TextFormatting.GREEN), new ScoreCriteriaColored("killedByTeam.", TextFormatting.AQUA), new ScoreCriteriaColored("killedByTeam.", TextFormatting.RED), new ScoreCriteriaColored("killedByTeam.", TextFormatting.LIGHT_PURPLE), new ScoreCriteriaColored("killedByTeam.", TextFormatting.YELLOW), new ScoreCriteriaColored("killedByTeam.", TextFormatting.WHITE) };
/*    */   
/*    */   String getName();
/*    */   
/*    */   boolean isReadOnly();
/*    */   
/*    */   EnumRenderType getRenderType();
/*    */   
/*    */   public enum EnumRenderType
/*    */   {
/* 32 */     INTEGER("integer"),
/* 33 */     HEARTS("hearts");
/*    */     
/* 35 */     private static final Map<String, EnumRenderType> BY_NAME = Maps.newHashMap();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     private final String renderType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     static {
/*    */       byte b;
/*    */       int i;
/*    */       EnumRenderType[] arrayOfEnumRenderType;
/* 55 */       for (i = (arrayOfEnumRenderType = values()).length, b = 0; b < i; ) { EnumRenderType iscorecriteria$enumrendertype = arrayOfEnumRenderType[b];
/*    */         
/* 57 */         BY_NAME.put(iscorecriteria$enumrendertype.getRenderType(), iscorecriteria$enumrendertype);
/*    */         b++; }
/*    */     
/*    */     }
/*    */     
/*    */     EnumRenderType(String renderTypeIn) {
/*    */       this.renderType = renderTypeIn;
/*    */     }
/*    */     
/*    */     public String getRenderType() {
/*    */       return this.renderType;
/*    */     }
/*    */     
/*    */     public static EnumRenderType getByName(String name) {
/*    */       EnumRenderType iscorecriteria$enumrendertype = BY_NAME.get(name);
/*    */       return (iscorecriteria$enumrendertype == null) ? INTEGER : iscorecriteria$enumrendertype;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\scoreboard\IScoreCriteria.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */