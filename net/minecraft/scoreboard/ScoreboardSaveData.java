/*     */ package net.minecraft.scoreboard;
/*     */ 
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.util.text.TextFormatting;
/*     */ import net.minecraft.world.storage.WorldSavedData;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ScoreboardSaveData extends WorldSavedData {
/*  13 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private Scoreboard theScoreboard;
/*     */   private NBTTagCompound delayedInitNbt;
/*     */   
/*     */   public ScoreboardSaveData() {
/*  19 */     this("scoreboard");
/*     */   }
/*     */ 
/*     */   
/*     */   public ScoreboardSaveData(String name) {
/*  24 */     super(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setScoreboard(Scoreboard scoreboardIn) {
/*  29 */     this.theScoreboard = scoreboardIn;
/*     */     
/*  31 */     if (this.delayedInitNbt != null)
/*     */     {
/*  33 */       readFromNBT(this.delayedInitNbt);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound nbt) {
/*  42 */     if (this.theScoreboard == null) {
/*     */       
/*  44 */       this.delayedInitNbt = nbt;
/*     */     }
/*     */     else {
/*     */       
/*  48 */       readObjectives(nbt.getTagList("Objectives", 10));
/*  49 */       readScores(nbt.getTagList("PlayerScores", 10));
/*     */       
/*  51 */       if (nbt.hasKey("DisplaySlots", 10))
/*     */       {
/*  53 */         readDisplayConfig(nbt.getCompoundTag("DisplaySlots"));
/*     */       }
/*     */       
/*  56 */       if (nbt.hasKey("Teams", 9))
/*     */       {
/*  58 */         readTeams(nbt.getTagList("Teams", 10));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readTeams(NBTTagList tagList) {
/*  65 */     for (int i = 0; i < tagList.tagCount(); i++) {
/*     */       
/*  67 */       NBTTagCompound nbttagcompound = tagList.getCompoundTagAt(i);
/*  68 */       String s = nbttagcompound.getString("Name");
/*     */       
/*  70 */       if (s.length() > 16)
/*     */       {
/*  72 */         s = s.substring(0, 16);
/*     */       }
/*     */       
/*  75 */       ScorePlayerTeam scoreplayerteam = this.theScoreboard.createTeam(s);
/*  76 */       String s1 = nbttagcompound.getString("DisplayName");
/*     */       
/*  78 */       if (s1.length() > 32)
/*     */       {
/*  80 */         s1 = s1.substring(0, 32);
/*     */       }
/*     */       
/*  83 */       scoreplayerteam.setTeamName(s1);
/*     */       
/*  85 */       if (nbttagcompound.hasKey("TeamColor", 8))
/*     */       {
/*  87 */         scoreplayerteam.setChatFormat(TextFormatting.getValueByName(nbttagcompound.getString("TeamColor")));
/*     */       }
/*     */       
/*  90 */       scoreplayerteam.setNamePrefix(nbttagcompound.getString("Prefix"));
/*  91 */       scoreplayerteam.setNameSuffix(nbttagcompound.getString("Suffix"));
/*     */       
/*  93 */       if (nbttagcompound.hasKey("AllowFriendlyFire", 99))
/*     */       {
/*  95 */         scoreplayerteam.setAllowFriendlyFire(nbttagcompound.getBoolean("AllowFriendlyFire"));
/*     */       }
/*     */       
/*  98 */       if (nbttagcompound.hasKey("SeeFriendlyInvisibles", 99))
/*     */       {
/* 100 */         scoreplayerteam.setSeeFriendlyInvisiblesEnabled(nbttagcompound.getBoolean("SeeFriendlyInvisibles"));
/*     */       }
/*     */       
/* 103 */       if (nbttagcompound.hasKey("NameTagVisibility", 8)) {
/*     */         
/* 105 */         Team.EnumVisible team$enumvisible = Team.EnumVisible.getByName(nbttagcompound.getString("NameTagVisibility"));
/*     */         
/* 107 */         if (team$enumvisible != null)
/*     */         {
/* 109 */           scoreplayerteam.setNameTagVisibility(team$enumvisible);
/*     */         }
/*     */       } 
/*     */       
/* 113 */       if (nbttagcompound.hasKey("DeathMessageVisibility", 8)) {
/*     */         
/* 115 */         Team.EnumVisible team$enumvisible1 = Team.EnumVisible.getByName(nbttagcompound.getString("DeathMessageVisibility"));
/*     */         
/* 117 */         if (team$enumvisible1 != null)
/*     */         {
/* 119 */           scoreplayerteam.setDeathMessageVisibility(team$enumvisible1);
/*     */         }
/*     */       } 
/*     */       
/* 123 */       if (nbttagcompound.hasKey("CollisionRule", 8)) {
/*     */         
/* 125 */         Team.CollisionRule team$collisionrule = Team.CollisionRule.getByName(nbttagcompound.getString("CollisionRule"));
/*     */         
/* 127 */         if (team$collisionrule != null)
/*     */         {
/* 129 */           scoreplayerteam.setCollisionRule(team$collisionrule);
/*     */         }
/*     */       } 
/*     */       
/* 133 */       loadTeamPlayers(scoreplayerteam, nbttagcompound.getTagList("Players", 8));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadTeamPlayers(ScorePlayerTeam playerTeam, NBTTagList tagList) {
/* 139 */     for (int i = 0; i < tagList.tagCount(); i++)
/*     */     {
/* 141 */       this.theScoreboard.addPlayerToTeam(tagList.getStringTagAt(i), playerTeam.getRegisteredName());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readDisplayConfig(NBTTagCompound compound) {
/* 147 */     for (int i = 0; i < 19; i++) {
/*     */       
/* 149 */       if (compound.hasKey("slot_" + i, 8)) {
/*     */         
/* 151 */         String s = compound.getString("slot_" + i);
/* 152 */         ScoreObjective scoreobjective = this.theScoreboard.getObjective(s);
/* 153 */         this.theScoreboard.setObjectiveInDisplaySlot(i, scoreobjective);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readObjectives(NBTTagList nbt) {
/* 160 */     for (int i = 0; i < nbt.tagCount(); i++) {
/*     */       
/* 162 */       NBTTagCompound nbttagcompound = nbt.getCompoundTagAt(i);
/* 163 */       IScoreCriteria iscorecriteria = IScoreCriteria.INSTANCES.get(nbttagcompound.getString("CriteriaName"));
/*     */       
/* 165 */       if (iscorecriteria != null) {
/*     */         
/* 167 */         String s = nbttagcompound.getString("Name");
/*     */         
/* 169 */         if (s.length() > 16)
/*     */         {
/* 171 */           s = s.substring(0, 16);
/*     */         }
/*     */         
/* 174 */         ScoreObjective scoreobjective = this.theScoreboard.addScoreObjective(s, iscorecriteria);
/* 175 */         scoreobjective.setDisplayName(nbttagcompound.getString("DisplayName"));
/* 176 */         scoreobjective.setRenderType(IScoreCriteria.EnumRenderType.getByName(nbttagcompound.getString("RenderType")));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void readScores(NBTTagList nbt) {
/* 183 */     for (int i = 0; i < nbt.tagCount(); i++) {
/*     */       
/* 185 */       NBTTagCompound nbttagcompound = nbt.getCompoundTagAt(i);
/* 186 */       ScoreObjective scoreobjective = this.theScoreboard.getObjective(nbttagcompound.getString("Objective"));
/* 187 */       String s = nbttagcompound.getString("Name");
/*     */       
/* 189 */       if (s.length() > 40)
/*     */       {
/* 191 */         s = s.substring(0, 40);
/*     */       }
/*     */       
/* 194 */       Score score = this.theScoreboard.getOrCreateScore(s, scoreobjective);
/* 195 */       score.setScorePoints(nbttagcompound.getInteger("Score"));
/*     */       
/* 197 */       if (nbttagcompound.hasKey("Locked"))
/*     */       {
/* 199 */         score.setLocked(nbttagcompound.getBoolean("Locked"));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound writeToNBT(NBTTagCompound compound) {
/* 206 */     if (this.theScoreboard == null) {
/*     */       
/* 208 */       LOGGER.warn("Tried to save scoreboard without having a scoreboard...");
/* 209 */       return compound;
/*     */     } 
/*     */ 
/*     */     
/* 213 */     compound.setTag("Objectives", (NBTBase)objectivesToNbt());
/* 214 */     compound.setTag("PlayerScores", (NBTBase)scoresToNbt());
/* 215 */     compound.setTag("Teams", (NBTBase)teamsToNbt());
/* 216 */     fillInDisplaySlots(compound);
/* 217 */     return compound;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected NBTTagList teamsToNbt() {
/* 223 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 225 */     for (ScorePlayerTeam scoreplayerteam : this.theScoreboard.getTeams()) {
/*     */       
/* 227 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 228 */       nbttagcompound.setString("Name", scoreplayerteam.getRegisteredName());
/* 229 */       nbttagcompound.setString("DisplayName", scoreplayerteam.getTeamName());
/*     */       
/* 231 */       if (scoreplayerteam.getChatFormat().getColorIndex() >= 0)
/*     */       {
/* 233 */         nbttagcompound.setString("TeamColor", scoreplayerteam.getChatFormat().getFriendlyName());
/*     */       }
/*     */       
/* 236 */       nbttagcompound.setString("Prefix", scoreplayerteam.getColorPrefix());
/* 237 */       nbttagcompound.setString("Suffix", scoreplayerteam.getColorSuffix());
/* 238 */       nbttagcompound.setBoolean("AllowFriendlyFire", scoreplayerteam.getAllowFriendlyFire());
/* 239 */       nbttagcompound.setBoolean("SeeFriendlyInvisibles", scoreplayerteam.getSeeFriendlyInvisiblesEnabled());
/* 240 */       nbttagcompound.setString("NameTagVisibility", (scoreplayerteam.getNameTagVisibility()).internalName);
/* 241 */       nbttagcompound.setString("DeathMessageVisibility", (scoreplayerteam.getDeathMessageVisibility()).internalName);
/* 242 */       nbttagcompound.setString("CollisionRule", (scoreplayerteam.getCollisionRule()).name);
/* 243 */       NBTTagList nbttaglist1 = new NBTTagList();
/*     */       
/* 245 */       for (String s : scoreplayerteam.getMembershipCollection())
/*     */       {
/* 247 */         nbttaglist1.appendTag((NBTBase)new NBTTagString(s));
/*     */       }
/*     */       
/* 250 */       nbttagcompound.setTag("Players", (NBTBase)nbttaglist1);
/* 251 */       nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */     } 
/*     */     
/* 254 */     return nbttaglist;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void fillInDisplaySlots(NBTTagCompound compound) {
/* 259 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 260 */     boolean flag = false;
/*     */     
/* 262 */     for (int i = 0; i < 19; i++) {
/*     */       
/* 264 */       ScoreObjective scoreobjective = this.theScoreboard.getObjectiveInDisplaySlot(i);
/*     */       
/* 266 */       if (scoreobjective != null) {
/*     */         
/* 268 */         nbttagcompound.setString("slot_" + i, scoreobjective.getName());
/* 269 */         flag = true;
/*     */       } 
/*     */     } 
/*     */     
/* 273 */     if (flag)
/*     */     {
/* 275 */       compound.setTag("DisplaySlots", (NBTBase)nbttagcompound);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected NBTTagList objectivesToNbt() {
/* 281 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 283 */     for (ScoreObjective scoreobjective : this.theScoreboard.getScoreObjectives()) {
/*     */       
/* 285 */       if (scoreobjective.getCriteria() != null) {
/*     */         
/* 287 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 288 */         nbttagcompound.setString("Name", scoreobjective.getName());
/* 289 */         nbttagcompound.setString("CriteriaName", scoreobjective.getCriteria().getName());
/* 290 */         nbttagcompound.setString("DisplayName", scoreobjective.getDisplayName());
/* 291 */         nbttagcompound.setString("RenderType", scoreobjective.getRenderType().getRenderType());
/* 292 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 296 */     return nbttaglist;
/*     */   }
/*     */ 
/*     */   
/*     */   protected NBTTagList scoresToNbt() {
/* 301 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/* 303 */     for (Score score : this.theScoreboard.getScores()) {
/*     */       
/* 305 */       if (score.getObjective() != null) {
/*     */         
/* 307 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 308 */         nbttagcompound.setString("Name", score.getPlayerName());
/* 309 */         nbttagcompound.setString("Objective", score.getObjective().getName());
/* 310 */         nbttagcompound.setInteger("Score", score.getScorePoints());
/* 311 */         nbttagcompound.setBoolean("Locked", score.isLocked());
/* 312 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 316 */     return nbttaglist;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\scoreboard\ScoreboardSaveData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */