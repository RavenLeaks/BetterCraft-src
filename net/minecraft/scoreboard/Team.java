/*     */ package net.minecraft.scoreboard;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Team
/*     */ {
/*     */   public boolean isSameTeam(@Nullable Team other) {
/*  16 */     if (other == null)
/*     */     {
/*  18 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  22 */     return (this == other);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract String getRegisteredName();
/*     */ 
/*     */   
/*     */   public abstract String formatString(String paramString);
/*     */ 
/*     */   
/*     */   public abstract boolean getSeeFriendlyInvisiblesEnabled();
/*     */ 
/*     */   
/*     */   public abstract boolean getAllowFriendlyFire();
/*     */   
/*     */   public abstract EnumVisible getNameTagVisibility();
/*     */   
/*     */   public abstract TextFormatting getChatFormat();
/*     */   
/*     */   public abstract Collection<String> getMembershipCollection();
/*     */   
/*     */   public abstract EnumVisible getDeathMessageVisibility();
/*     */   
/*     */   public abstract CollisionRule getCollisionRule();
/*     */   
/*     */   public enum CollisionRule
/*     */   {
/*  49 */     ALWAYS("always", 0),
/*  50 */     NEVER("never", 1),
/*  51 */     HIDE_FOR_OTHER_TEAMS("pushOtherTeams", 2),
/*  52 */     HIDE_FOR_OWN_TEAM("pushOwnTeam", 3);
/*     */     
/*  54 */     private static final Map<String, CollisionRule> nameMap = Maps.newHashMap();
/*     */     public final String name;
/*     */     public final int id;
/*     */     
/*     */     public static String[] getNames() {
/*     */       return (String[])nameMap.keySet().toArray((Object[])new String[nameMap.size()]);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public static CollisionRule getByName(String nameIn) {
/*     */       return nameMap.get(nameIn);
/*     */     }
/*     */     
/*     */     CollisionRule(String nameIn, int idIn) {
/*     */       this.name = nameIn;
/*     */       this.id = idIn;
/*     */     }
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       CollisionRule[] arrayOfCollisionRule;
/*  76 */       for (i = (arrayOfCollisionRule = values()).length, b = 0; b < i; ) { CollisionRule team$collisionrule = arrayOfCollisionRule[b];
/*     */         
/*  78 */         nameMap.put(team$collisionrule.name, team$collisionrule);
/*     */         b++; }
/*     */     
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumVisible {
/*  85 */     ALWAYS("always", 0),
/*  86 */     NEVER("never", 1),
/*  87 */     HIDE_FOR_OTHER_TEAMS("hideForOtherTeams", 2),
/*  88 */     HIDE_FOR_OWN_TEAM("hideForOwnTeam", 3);
/*     */     
/*  90 */     private static final Map<String, EnumVisible> nameMap = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final String internalName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public final int id;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumVisible[] arrayOfEnumVisible;
/* 112 */       for (i = (arrayOfEnumVisible = values()).length, b = 0; b < i; ) { EnumVisible team$enumvisible = arrayOfEnumVisible[b];
/*     */         
/* 114 */         nameMap.put(team$enumvisible.internalName, team$enumvisible);
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     public static String[] getNames() {
/*     */       return (String[])nameMap.keySet().toArray((Object[])new String[nameMap.size()]);
/*     */     }
/*     */     
/*     */     @Nullable
/*     */     public static EnumVisible getByName(String nameIn) {
/*     */       return nameMap.get(nameIn);
/*     */     }
/*     */     
/*     */     EnumVisible(String nameIn, int idIn) {
/*     */       this.internalName = nameIn;
/*     */       this.id = idIn;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\scoreboard\Team.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */