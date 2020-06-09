/*     */ package net.minecraft.scoreboard;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ 
/*     */ 
/*     */ public class Scoreboard
/*     */ {
/*  16 */   private final Map<String, ScoreObjective> scoreObjectives = Maps.newHashMap();
/*  17 */   private final Map<IScoreCriteria, List<ScoreObjective>> scoreObjectiveCriterias = Maps.newHashMap();
/*  18 */   private final Map<String, Map<ScoreObjective, Score>> entitiesScoreObjectives = Maps.newHashMap();
/*     */ 
/*     */   
/*  21 */   private final ScoreObjective[] objectiveDisplaySlots = new ScoreObjective[19];
/*  22 */   private final Map<String, ScorePlayerTeam> teams = Maps.newHashMap();
/*  23 */   private final Map<String, ScorePlayerTeam> teamMemberships = Maps.newHashMap();
/*     */ 
/*     */ 
/*     */   
/*     */   private static String[] displaySlots;
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ScoreObjective getObjective(String name) {
/*  33 */     return this.scoreObjectives.get(name);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScoreObjective addScoreObjective(String name, IScoreCriteria criteria) {
/*  41 */     if (name.length() > 16)
/*     */     {
/*  43 */       throw new IllegalArgumentException("The objective name '" + name + "' is too long!");
/*     */     }
/*     */ 
/*     */     
/*  47 */     ScoreObjective scoreobjective = getObjective(name);
/*     */     
/*  49 */     if (scoreobjective != null)
/*     */     {
/*  51 */       throw new IllegalArgumentException("An objective with the name '" + name + "' already exists!");
/*     */     }
/*     */ 
/*     */     
/*  55 */     scoreobjective = new ScoreObjective(this, name, criteria);
/*  56 */     List<ScoreObjective> list = this.scoreObjectiveCriterias.get(criteria);
/*     */     
/*  58 */     if (list == null) {
/*     */       
/*  60 */       list = Lists.newArrayList();
/*  61 */       this.scoreObjectiveCriterias.put(criteria, list);
/*     */     } 
/*     */     
/*  64 */     list.add(scoreobjective);
/*  65 */     this.scoreObjectives.put(name, scoreobjective);
/*  66 */     onScoreObjectiveAdded(scoreobjective);
/*  67 */     return scoreobjective;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<ScoreObjective> getObjectivesFromCriteria(IScoreCriteria criteria) {
/*  74 */     Collection<ScoreObjective> collection = this.scoreObjectiveCriterias.get(criteria);
/*  75 */     return (collection == null) ? Lists.newArrayList() : Lists.newArrayList(collection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean entityHasObjective(String name, ScoreObjective objective) {
/*  83 */     Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.get(name);
/*     */     
/*  85 */     if (map == null)
/*     */     {
/*  87 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  91 */     Score score = map.get(objective);
/*  92 */     return (score != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Score getOrCreateScore(String username, ScoreObjective objective) {
/* 101 */     if (username.length() > 40)
/*     */     {
/* 103 */       throw new IllegalArgumentException("The player name '" + username + "' is too long!");
/*     */     }
/*     */ 
/*     */     
/* 107 */     Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.get(username);
/*     */     
/* 109 */     if (map == null) {
/*     */       
/* 111 */       map = Maps.newHashMap();
/* 112 */       this.entitiesScoreObjectives.put(username, map);
/*     */     } 
/*     */     
/* 115 */     Score score = map.get(objective);
/*     */     
/* 117 */     if (score == null) {
/*     */       
/* 119 */       score = new Score(this, objective, username);
/* 120 */       map.put(objective, score);
/*     */     } 
/*     */     
/* 123 */     return score;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<Score> getSortedScores(ScoreObjective objective) {
/* 129 */     List<Score> list = Lists.newArrayList();
/*     */     
/* 131 */     for (Map<ScoreObjective, Score> map : this.entitiesScoreObjectives.values()) {
/*     */       
/* 133 */       Score score = map.get(objective);
/*     */       
/* 135 */       if (score != null)
/*     */       {
/* 137 */         list.add(score);
/*     */       }
/*     */     } 
/*     */     
/* 141 */     Collections.sort(list, Score.SCORE_COMPARATOR);
/* 142 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ScoreObjective> getScoreObjectives() {
/* 147 */     return this.scoreObjectives.values();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getObjectiveNames() {
/* 152 */     return this.entitiesScoreObjectives.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeObjectiveFromEntity(String name, ScoreObjective objective) {
/* 160 */     if (objective == null) {
/*     */       
/* 162 */       Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.remove(name);
/*     */       
/* 164 */       if (map != null)
/*     */       {
/* 166 */         broadcastScoreUpdate(name);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 171 */       Map<ScoreObjective, Score> map2 = this.entitiesScoreObjectives.get(name);
/*     */       
/* 173 */       if (map2 != null) {
/*     */         
/* 175 */         Score score = map2.remove(objective);
/*     */         
/* 177 */         if (map2.size() < 1) {
/*     */           
/* 179 */           Map<ScoreObjective, Score> map1 = this.entitiesScoreObjectives.remove(name);
/*     */           
/* 181 */           if (map1 != null)
/*     */           {
/* 183 */             broadcastScoreUpdate(name);
/*     */           }
/*     */         }
/* 186 */         else if (score != null) {
/*     */           
/* 188 */           broadcastScoreUpdate(name, objective);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Score> getScores() {
/* 196 */     Collection<Map<ScoreObjective, Score>> collection = this.entitiesScoreObjectives.values();
/* 197 */     List<Score> list = Lists.newArrayList();
/*     */     
/* 199 */     for (Map<ScoreObjective, Score> map : collection)
/*     */     {
/* 201 */       list.addAll(map.values());
/*     */     }
/*     */     
/* 204 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<ScoreObjective, Score> getObjectivesForEntity(String name) {
/* 209 */     Map<ScoreObjective, Score> map = this.entitiesScoreObjectives.get(name);
/*     */     
/* 211 */     if (map == null)
/*     */     {
/* 213 */       map = Maps.newHashMap();
/*     */     }
/*     */     
/* 216 */     return map;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeObjective(ScoreObjective objective) {
/* 221 */     this.scoreObjectives.remove(objective.getName());
/*     */     
/* 223 */     for (int i = 0; i < 19; i++) {
/*     */       
/* 225 */       if (getObjectiveInDisplaySlot(i) == objective)
/*     */       {
/* 227 */         setObjectiveInDisplaySlot(i, null);
/*     */       }
/*     */     } 
/*     */     
/* 231 */     List<ScoreObjective> list = this.scoreObjectiveCriterias.get(objective.getCriteria());
/*     */     
/* 233 */     if (list != null)
/*     */     {
/* 235 */       list.remove(objective);
/*     */     }
/*     */     
/* 238 */     for (Map<ScoreObjective, Score> map : this.entitiesScoreObjectives.values())
/*     */     {
/* 240 */       map.remove(objective);
/*     */     }
/*     */     
/* 243 */     onScoreObjectiveRemoved(objective);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObjectiveInDisplaySlot(int objectiveSlot, ScoreObjective objective) {
/* 251 */     this.objectiveDisplaySlots[objectiveSlot] = objective;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ScoreObjective getObjectiveInDisplaySlot(int slotIn) {
/* 261 */     return this.objectiveDisplaySlots[slotIn];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScorePlayerTeam getTeam(String teamName) {
/* 269 */     return this.teams.get(teamName);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScorePlayerTeam createTeam(String name) {
/* 274 */     if (name.length() > 16)
/*     */     {
/* 276 */       throw new IllegalArgumentException("The team name '" + name + "' is too long!");
/*     */     }
/*     */ 
/*     */     
/* 280 */     ScorePlayerTeam scoreplayerteam = getTeam(name);
/*     */     
/* 282 */     if (scoreplayerteam != null)
/*     */     {
/* 284 */       throw new IllegalArgumentException("A team with the name '" + name + "' already exists!");
/*     */     }
/*     */ 
/*     */     
/* 288 */     scoreplayerteam = new ScorePlayerTeam(this, name);
/* 289 */     this.teams.put(name, scoreplayerteam);
/* 290 */     broadcastTeamCreated(scoreplayerteam);
/* 291 */     return scoreplayerteam;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTeam(ScorePlayerTeam playerTeam) {
/* 301 */     this.teams.remove(playerTeam.getRegisteredName());
/*     */     
/* 303 */     for (String s : playerTeam.getMembershipCollection())
/*     */     {
/* 305 */       this.teamMemberships.remove(s);
/*     */     }
/*     */     
/* 308 */     broadcastTeamRemove(playerTeam);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addPlayerToTeam(String player, String newTeam) {
/* 316 */     if (player.length() > 40)
/*     */     {
/* 318 */       throw new IllegalArgumentException("The player name '" + player + "' is too long!");
/*     */     }
/* 320 */     if (!this.teams.containsKey(newTeam))
/*     */     {
/* 322 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 326 */     ScorePlayerTeam scoreplayerteam = getTeam(newTeam);
/*     */     
/* 328 */     if (getPlayersTeam(player) != null)
/*     */     {
/* 330 */       removePlayerFromTeams(player);
/*     */     }
/*     */     
/* 333 */     this.teamMemberships.put(player, scoreplayerteam);
/* 334 */     scoreplayerteam.getMembershipCollection().add(player);
/* 335 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean removePlayerFromTeams(String playerName) {
/* 341 */     ScorePlayerTeam scoreplayerteam = getPlayersTeam(playerName);
/*     */     
/* 343 */     if (scoreplayerteam != null) {
/*     */       
/* 345 */       removePlayerFromTeam(playerName, scoreplayerteam);
/* 346 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 350 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayerFromTeam(String username, ScorePlayerTeam playerTeam) {
/* 360 */     if (getPlayersTeam(username) != playerTeam)
/*     */     {
/* 362 */       throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + playerTeam.getRegisteredName() + "'.");
/*     */     }
/*     */ 
/*     */     
/* 366 */     this.teamMemberships.remove(username);
/* 367 */     playerTeam.getMembershipCollection().remove(username);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Collection<String> getTeamNames() {
/* 373 */     return this.teams.keySet();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ScorePlayerTeam> getTeams() {
/* 378 */     return this.teams.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ScorePlayerTeam getPlayersTeam(String username) {
/* 388 */     return this.teamMemberships.get(username);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onScoreObjectiveAdded(ScoreObjective scoreObjectiveIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onObjectiveDisplayNameChanged(ScoreObjective objective) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onScoreObjectiveRemoved(ScoreObjective objective) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onScoreUpdated(Score scoreIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastScoreUpdate(String scoreName) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastScoreUpdate(String scoreName, ScoreObjective objective) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastTeamCreated(ScorePlayerTeam playerTeam) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastTeamInfoUpdate(ScorePlayerTeam playerTeam) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastTeamRemove(ScorePlayerTeam playerTeam) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getObjectiveDisplaySlot(int id) {
/* 441 */     switch (id) {
/*     */       
/*     */       case 0:
/* 444 */         return "list";
/*     */       
/*     */       case 1:
/* 447 */         return "sidebar";
/*     */       
/*     */       case 2:
/* 450 */         return "belowName";
/*     */     } 
/*     */     
/* 453 */     if (id >= 3 && id <= 18) {
/*     */       
/* 455 */       TextFormatting textformatting = TextFormatting.fromColorIndex(id - 3);
/*     */       
/* 457 */       if (textformatting != null && textformatting != TextFormatting.RESET)
/*     */       {
/* 459 */         return "sidebar.team." + textformatting.getFriendlyName();
/*     */       }
/*     */     } 
/*     */     
/* 463 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getObjectiveDisplaySlotNumber(String name) {
/* 472 */     if ("list".equalsIgnoreCase(name))
/*     */     {
/* 474 */       return 0;
/*     */     }
/* 476 */     if ("sidebar".equalsIgnoreCase(name))
/*     */     {
/* 478 */       return 1;
/*     */     }
/* 480 */     if ("belowName".equalsIgnoreCase(name))
/*     */     {
/* 482 */       return 2;
/*     */     }
/*     */ 
/*     */     
/* 486 */     if (name.startsWith("sidebar.team.")) {
/*     */       
/* 488 */       String s = name.substring("sidebar.team.".length());
/* 489 */       TextFormatting textformatting = TextFormatting.getValueByName(s);
/*     */       
/* 491 */       if (textformatting != null && textformatting.getColorIndex() >= 0)
/*     */       {
/* 493 */         return textformatting.getColorIndex() + 3;
/*     */       }
/*     */     } 
/*     */     
/* 497 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String[] getDisplaySlotStrings() {
/* 503 */     if (displaySlots == null) {
/*     */       
/* 505 */       displaySlots = new String[19];
/*     */       
/* 507 */       for (int i = 0; i < 19; i++)
/*     */       {
/* 509 */         displaySlots[i] = getObjectiveDisplaySlot(i);
/*     */       }
/*     */     } 
/*     */     
/* 513 */     return displaySlots;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeEntity(Entity entityIn) {
/* 518 */     if (entityIn != null && !(entityIn instanceof net.minecraft.entity.player.EntityPlayer) && !entityIn.isEntityAlive()) {
/*     */       
/* 520 */       String s = entityIn.getCachedUniqueIdString();
/* 521 */       removeObjectiveFromEntity(s, null);
/* 522 */       removePlayerFromTeams(s);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\scoreboard\Scoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */