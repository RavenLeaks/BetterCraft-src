/*     */ package net.minecraft.scoreboard;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketDisplayObjective;
/*     */ import net.minecraft.network.play.server.SPacketScoreboardObjective;
/*     */ import net.minecraft.network.play.server.SPacketTeams;
/*     */ import net.minecraft.network.play.server.SPacketUpdateScore;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ 
/*     */ public class ServerScoreboard
/*     */   extends Scoreboard {
/*     */   private final MinecraftServer scoreboardMCServer;
/*  19 */   private final Set<ScoreObjective> addedObjectives = Sets.newHashSet();
/*  20 */   private Runnable[] dirtyRunnables = new Runnable[0];
/*     */ 
/*     */   
/*     */   public ServerScoreboard(MinecraftServer mcServer) {
/*  24 */     this.scoreboardMCServer = mcServer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onScoreUpdated(Score scoreIn) {
/*  29 */     super.onScoreUpdated(scoreIn);
/*     */     
/*  31 */     if (this.addedObjectives.contains(scoreIn.getObjective()))
/*     */     {
/*  33 */       this.scoreboardMCServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketUpdateScore(scoreIn));
/*     */     }
/*     */     
/*  36 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void broadcastScoreUpdate(String scoreName) {
/*  41 */     super.broadcastScoreUpdate(scoreName);
/*  42 */     this.scoreboardMCServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketUpdateScore(scoreName));
/*  43 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void broadcastScoreUpdate(String scoreName, ScoreObjective objective) {
/*  48 */     super.broadcastScoreUpdate(scoreName, objective);
/*  49 */     this.scoreboardMCServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketUpdateScore(scoreName, objective));
/*  50 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setObjectiveInDisplaySlot(int objectiveSlot, ScoreObjective objective) {
/*  58 */     ScoreObjective scoreobjective = getObjectiveInDisplaySlot(objectiveSlot);
/*  59 */     super.setObjectiveInDisplaySlot(objectiveSlot, objective);
/*     */     
/*  61 */     if (scoreobjective != objective && scoreobjective != null)
/*     */     {
/*  63 */       if (getObjectiveDisplaySlotCount(scoreobjective) > 0) {
/*     */         
/*  65 */         this.scoreboardMCServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketDisplayObjective(objectiveSlot, objective));
/*     */       }
/*     */       else {
/*     */         
/*  69 */         sendDisplaySlotRemovalPackets(scoreobjective);
/*     */       } 
/*     */     }
/*     */     
/*  73 */     if (objective != null)
/*     */     {
/*  75 */       if (this.addedObjectives.contains(objective)) {
/*     */         
/*  77 */         this.scoreboardMCServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketDisplayObjective(objectiveSlot, objective));
/*     */       }
/*     */       else {
/*     */         
/*  81 */         addObjective(objective);
/*     */       } 
/*     */     }
/*     */     
/*  85 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean addPlayerToTeam(String player, String newTeam) {
/*  93 */     if (super.addPlayerToTeam(player, newTeam)) {
/*     */       
/*  95 */       ScorePlayerTeam scoreplayerteam = getTeam(newTeam);
/*  96 */       this.scoreboardMCServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketTeams(scoreplayerteam, Arrays.asList(new String[] { player }, ), 3));
/*  97 */       markSaveDataDirty();
/*  98 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removePlayerFromTeam(String username, ScorePlayerTeam playerTeam) {
/* 112 */     super.removePlayerFromTeam(username, playerTeam);
/* 113 */     this.scoreboardMCServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketTeams(playerTeam, Arrays.asList(new String[] { username }, ), 4));
/* 114 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onScoreObjectiveAdded(ScoreObjective scoreObjectiveIn) {
/* 122 */     super.onScoreObjectiveAdded(scoreObjectiveIn);
/* 123 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onObjectiveDisplayNameChanged(ScoreObjective objective) {
/* 128 */     super.onObjectiveDisplayNameChanged(objective);
/*     */     
/* 130 */     if (this.addedObjectives.contains(objective))
/*     */     {
/* 132 */       this.scoreboardMCServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketScoreboardObjective(objective, 2));
/*     */     }
/*     */     
/* 135 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onScoreObjectiveRemoved(ScoreObjective objective) {
/* 140 */     super.onScoreObjectiveRemoved(objective);
/*     */     
/* 142 */     if (this.addedObjectives.contains(objective))
/*     */     {
/* 144 */       sendDisplaySlotRemovalPackets(objective);
/*     */     }
/*     */     
/* 147 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastTeamCreated(ScorePlayerTeam playerTeam) {
/* 155 */     super.broadcastTeamCreated(playerTeam);
/* 156 */     this.scoreboardMCServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketTeams(playerTeam, 0));
/* 157 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void broadcastTeamInfoUpdate(ScorePlayerTeam playerTeam) {
/* 165 */     super.broadcastTeamInfoUpdate(playerTeam);
/* 166 */     this.scoreboardMCServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketTeams(playerTeam, 2));
/* 167 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void broadcastTeamRemove(ScorePlayerTeam playerTeam) {
/* 172 */     super.broadcastTeamRemove(playerTeam);
/* 173 */     this.scoreboardMCServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketTeams(playerTeam, 1));
/* 174 */     markSaveDataDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDirtyRunnable(Runnable runnable) {
/* 179 */     this.dirtyRunnables = Arrays.<Runnable>copyOf(this.dirtyRunnables, this.dirtyRunnables.length + 1);
/* 180 */     this.dirtyRunnables[this.dirtyRunnables.length - 1] = runnable;
/*     */   } protected void markSaveDataDirty() {
/*     */     byte b;
/*     */     int i;
/*     */     Runnable[] arrayOfRunnable;
/* 185 */     for (i = (arrayOfRunnable = this.dirtyRunnables).length, b = 0; b < i; ) { Runnable runnable = arrayOfRunnable[b];
/*     */       
/* 187 */       runnable.run();
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   public List<Packet<?>> getCreatePackets(ScoreObjective objective) {
/* 193 */     List<Packet<?>> list = Lists.newArrayList();
/* 194 */     list.add(new SPacketScoreboardObjective(objective, 0));
/*     */     
/* 196 */     for (int i = 0; i < 19; i++) {
/*     */       
/* 198 */       if (getObjectiveInDisplaySlot(i) == objective)
/*     */       {
/* 200 */         list.add(new SPacketDisplayObjective(i, objective));
/*     */       }
/*     */     } 
/*     */     
/* 204 */     for (Score score : getSortedScores(objective))
/*     */     {
/* 206 */       list.add(new SPacketUpdateScore(score));
/*     */     }
/*     */     
/* 209 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addObjective(ScoreObjective objective) {
/* 214 */     List<Packet<?>> list = getCreatePackets(objective);
/*     */     
/* 216 */     for (EntityPlayerMP entityplayermp : this.scoreboardMCServer.getPlayerList().getPlayerList()) {
/*     */       
/* 218 */       for (Packet<?> packet : list)
/*     */       {
/* 220 */         entityplayermp.connection.sendPacket(packet);
/*     */       }
/*     */     } 
/*     */     
/* 224 */     this.addedObjectives.add(objective);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Packet<?>> getDestroyPackets(ScoreObjective p_96548_1_) {
/* 229 */     List<Packet<?>> list = Lists.newArrayList();
/* 230 */     list.add(new SPacketScoreboardObjective(p_96548_1_, 1));
/*     */     
/* 232 */     for (int i = 0; i < 19; i++) {
/*     */       
/* 234 */       if (getObjectiveInDisplaySlot(i) == p_96548_1_)
/*     */       {
/* 236 */         list.add(new SPacketDisplayObjective(i, p_96548_1_));
/*     */       }
/*     */     } 
/*     */     
/* 240 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendDisplaySlotRemovalPackets(ScoreObjective p_96546_1_) {
/* 245 */     List<Packet<?>> list = getDestroyPackets(p_96546_1_);
/*     */     
/* 247 */     for (EntityPlayerMP entityplayermp : this.scoreboardMCServer.getPlayerList().getPlayerList()) {
/*     */       
/* 249 */       for (Packet<?> packet : list)
/*     */       {
/* 251 */         entityplayermp.connection.sendPacket(packet);
/*     */       }
/*     */     } 
/*     */     
/* 255 */     this.addedObjectives.remove(p_96546_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getObjectiveDisplaySlotCount(ScoreObjective p_96552_1_) {
/* 260 */     int i = 0;
/*     */     
/* 262 */     for (int j = 0; j < 19; j++) {
/*     */       
/* 264 */       if (getObjectiveInDisplaySlot(j) == p_96552_1_)
/*     */       {
/* 266 */         i++;
/*     */       }
/*     */     } 
/*     */     
/* 270 */     return i;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\scoreboard\ServerScoreboard.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */