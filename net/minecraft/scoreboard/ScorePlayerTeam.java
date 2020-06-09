/*     */ package net.minecraft.scoreboard;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Collection;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ 
/*     */ public class ScorePlayerTeam
/*     */   extends Team {
/*     */   private final Scoreboard theScoreboard;
/*     */   private final String registeredName;
/*  13 */   private final Set<String> membershipSet = Sets.newHashSet();
/*     */   private String teamNameSPT;
/*  15 */   private String namePrefixSPT = "";
/*  16 */   private String colorSuffix = "";
/*     */   private boolean allowFriendlyFire = true;
/*     */   private boolean canSeeFriendlyInvisibles = true;
/*  19 */   private Team.EnumVisible nameTagVisibility = Team.EnumVisible.ALWAYS;
/*  20 */   private Team.EnumVisible deathMessageVisibility = Team.EnumVisible.ALWAYS;
/*  21 */   private TextFormatting chatFormat = TextFormatting.RESET;
/*  22 */   private Team.CollisionRule collisionRule = Team.CollisionRule.ALWAYS;
/*     */ 
/*     */   
/*     */   public ScorePlayerTeam(Scoreboard theScoreboardIn, String name) {
/*  26 */     this.theScoreboard = theScoreboardIn;
/*  27 */     this.registeredName = name;
/*  28 */     this.teamNameSPT = name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRegisteredName() {
/*  36 */     return this.registeredName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTeamName() {
/*  41 */     return this.teamNameSPT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTeamName(String name) {
/*  46 */     if (name == null)
/*     */     {
/*  48 */       throw new IllegalArgumentException("Name cannot be null");
/*     */     }
/*     */ 
/*     */     
/*  52 */     this.teamNameSPT = name;
/*  53 */     this.theScoreboard.broadcastTeamInfoUpdate(this);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getMembershipCollection() {
/*  59 */     return this.membershipSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColorPrefix() {
/*  67 */     return this.namePrefixSPT;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNamePrefix(String prefix) {
/*  72 */     if (prefix == null)
/*     */     {
/*  74 */       throw new IllegalArgumentException("Prefix cannot be null");
/*     */     }
/*     */ 
/*     */     
/*  78 */     this.namePrefixSPT = prefix;
/*  79 */     this.theScoreboard.broadcastTeamInfoUpdate(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getColorSuffix() {
/*  88 */     return this.colorSuffix;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNameSuffix(String suffix) {
/*  93 */     this.colorSuffix = suffix;
/*  94 */     this.theScoreboard.broadcastTeamInfoUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String formatString(String input) {
/*  99 */     return String.valueOf(getColorPrefix()) + input + getColorSuffix();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String formatPlayerName(@Nullable Team teamIn, String string) {
/* 107 */     return (teamIn == null) ? string : teamIn.formatString(string);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getAllowFriendlyFire() {
/* 112 */     return this.allowFriendlyFire;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAllowFriendlyFire(boolean friendlyFire) {
/* 117 */     this.allowFriendlyFire = friendlyFire;
/* 118 */     this.theScoreboard.broadcastTeamInfoUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getSeeFriendlyInvisiblesEnabled() {
/* 123 */     return this.canSeeFriendlyInvisibles;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSeeFriendlyInvisiblesEnabled(boolean friendlyInvisibles) {
/* 128 */     this.canSeeFriendlyInvisibles = friendlyInvisibles;
/* 129 */     this.theScoreboard.broadcastTeamInfoUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Team.EnumVisible getNameTagVisibility() {
/* 134 */     return this.nameTagVisibility;
/*     */   }
/*     */ 
/*     */   
/*     */   public Team.EnumVisible getDeathMessageVisibility() {
/* 139 */     return this.deathMessageVisibility;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNameTagVisibility(Team.EnumVisible visibility) {
/* 144 */     this.nameTagVisibility = visibility;
/* 145 */     this.theScoreboard.broadcastTeamInfoUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDeathMessageVisibility(Team.EnumVisible visibility) {
/* 150 */     this.deathMessageVisibility = visibility;
/* 151 */     this.theScoreboard.broadcastTeamInfoUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Team.CollisionRule getCollisionRule() {
/* 156 */     return this.collisionRule;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCollisionRule(Team.CollisionRule rule) {
/* 161 */     this.collisionRule = rule;
/* 162 */     this.theScoreboard.broadcastTeamInfoUpdate(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFriendlyFlags() {
/* 167 */     int i = 0;
/*     */     
/* 169 */     if (getAllowFriendlyFire())
/*     */     {
/* 171 */       i |= 0x1;
/*     */     }
/*     */     
/* 174 */     if (getSeeFriendlyInvisiblesEnabled())
/*     */     {
/* 176 */       i |= 0x2;
/*     */     }
/*     */     
/* 179 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFriendlyFlags(int flags) {
/* 184 */     setAllowFriendlyFire(((flags & 0x1) > 0));
/* 185 */     setSeeFriendlyInvisiblesEnabled(((flags & 0x2) > 0));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChatFormat(TextFormatting format) {
/* 190 */     this.chatFormat = format;
/*     */   }
/*     */ 
/*     */   
/*     */   public TextFormatting getChatFormat() {
/* 195 */     return this.chatFormat;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\scoreboard\ScorePlayerTeam.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */