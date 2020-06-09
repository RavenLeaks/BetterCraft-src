/*     */ package net.minecraft.network.play.server;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import net.minecraft.network.INetHandler;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.INetHandlerPlayClient;
/*     */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*     */ import net.minecraft.scoreboard.Team;
/*     */ 
/*     */ public class SPacketTeams implements Packet<INetHandlerPlayClient> {
/*  14 */   public String name = "";
/*  15 */   public String displayName = "";
/*  16 */   public String prefix = "";
/*  17 */   public String suffix = "";
/*     */   
/*     */   public String nameTagVisibility;
/*     */   public String collisionRule;
/*     */   public int color;
/*     */   public final Collection<String> players;
/*     */   public int action;
/*     */   public int friendlyFlags;
/*     */   
/*     */   public SPacketTeams() {
/*  27 */     this.nameTagVisibility = Team.EnumVisible.ALWAYS.internalName;
/*  28 */     this.collisionRule = Team.CollisionRule.ALWAYS.name;
/*  29 */     this.color = -1;
/*  30 */     this.players = Lists.newArrayList();
/*     */   }
/*     */ 
/*     */   
/*     */   public SPacketTeams(ScorePlayerTeam teamIn, int actionIn) {
/*  35 */     this.nameTagVisibility = Team.EnumVisible.ALWAYS.internalName;
/*  36 */     this.collisionRule = Team.CollisionRule.ALWAYS.name;
/*  37 */     this.color = -1;
/*  38 */     this.players = Lists.newArrayList();
/*  39 */     this.name = teamIn.getRegisteredName();
/*  40 */     this.action = actionIn;
/*     */     
/*  42 */     if (actionIn == 0 || actionIn == 2) {
/*     */       
/*  44 */       this.displayName = teamIn.getTeamName();
/*  45 */       this.prefix = teamIn.getColorPrefix();
/*  46 */       this.suffix = teamIn.getColorSuffix();
/*  47 */       this.friendlyFlags = teamIn.getFriendlyFlags();
/*  48 */       this.nameTagVisibility = (teamIn.getNameTagVisibility()).internalName;
/*  49 */       this.collisionRule = (teamIn.getCollisionRule()).name;
/*  50 */       this.color = teamIn.getChatFormat().getColorIndex();
/*     */     } 
/*     */     
/*  53 */     if (actionIn == 0)
/*     */     {
/*  55 */       this.players.addAll(teamIn.getMembershipCollection());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public SPacketTeams(ScorePlayerTeam teamIn, Collection<String> playersIn, int actionIn) {
/*  61 */     this.nameTagVisibility = Team.EnumVisible.ALWAYS.internalName;
/*  62 */     this.collisionRule = Team.CollisionRule.ALWAYS.name;
/*  63 */     this.color = -1;
/*  64 */     this.players = Lists.newArrayList();
/*     */     
/*  66 */     if (actionIn != 3 && actionIn != 4)
/*     */     {
/*  68 */       throw new IllegalArgumentException("Method must be join or leave for player constructor");
/*     */     }
/*  70 */     if (playersIn != null && !playersIn.isEmpty()) {
/*     */       
/*  72 */       this.action = actionIn;
/*  73 */       this.name = teamIn.getRegisteredName();
/*  74 */       this.players.addAll(playersIn);
/*     */     }
/*     */     else {
/*     */       
/*  78 */       throw new IllegalArgumentException("Players cannot be null/empty");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void readPacketData(PacketBuffer buf) throws IOException {
/*  87 */     this.name = buf.readStringFromBuffer(16);
/*  88 */     this.action = buf.readByte();
/*     */     
/*  90 */     if (this.action == 0 || this.action == 2) {
/*     */       
/*  92 */       this.displayName = buf.readStringFromBuffer(32);
/*  93 */       this.prefix = buf.readStringFromBuffer(16);
/*  94 */       this.suffix = buf.readStringFromBuffer(16);
/*  95 */       this.friendlyFlags = buf.readByte();
/*  96 */       this.nameTagVisibility = buf.readStringFromBuffer(32);
/*  97 */       this.collisionRule = buf.readStringFromBuffer(32);
/*  98 */       this.color = buf.readByte();
/*     */     } 
/*     */     
/* 101 */     if (this.action == 0 || this.action == 3 || this.action == 4) {
/*     */       
/* 103 */       int i = buf.readVarIntFromBuffer();
/*     */       
/* 105 */       for (int j = 0; j < i; j++)
/*     */       {
/* 107 */         this.players.add(buf.readStringFromBuffer(40));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePacketData(PacketBuffer buf) throws IOException {
/* 117 */     buf.writeString(this.name);
/* 118 */     buf.writeByte(this.action);
/*     */     
/* 120 */     if (this.action == 0 || this.action == 2) {
/*     */       
/* 122 */       buf.writeString(this.displayName);
/* 123 */       buf.writeString(this.prefix);
/* 124 */       buf.writeString(this.suffix);
/* 125 */       buf.writeByte(this.friendlyFlags);
/* 126 */       buf.writeString(this.nameTagVisibility);
/* 127 */       buf.writeString(this.collisionRule);
/* 128 */       buf.writeByte(this.color);
/*     */     } 
/*     */     
/* 131 */     if (this.action == 0 || this.action == 3 || this.action == 4) {
/*     */       
/* 133 */       buf.writeVarIntToBuffer(this.players.size());
/*     */       
/* 135 */       for (String s : this.players)
/*     */       {
/* 137 */         buf.writeString(s);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processPacket(INetHandlerPlayClient handler) {
/* 147 */     handler.handleTeams(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 152 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDisplayName() {
/* 157 */     return this.displayName;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPrefix() {
/* 162 */     return this.prefix;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSuffix() {
/* 167 */     return this.suffix;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<String> getPlayers() {
/* 172 */     return this.players;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAction() {
/* 177 */     return this.action;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFriendlyFlags() {
/* 182 */     return this.friendlyFlags;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getColor() {
/* 187 */     return this.color;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNameTagVisibility() {
/* 192 */     return this.nameTagVisibility;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCollisionRule() {
/* 197 */     return this.collisionRule;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\play\server\SPacketTeams.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */