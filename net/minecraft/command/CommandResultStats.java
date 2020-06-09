/*     */ package net.minecraft.command;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.text.ITextComponent;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CommandResultStats
/*     */ {
/*  18 */   private static final int NUM_RESULT_TYPES = (Type.values()).length;
/*  19 */   private static final String[] STRING_RESULT_TYPES = new String[NUM_RESULT_TYPES];
/*     */ 
/*     */ 
/*     */   
/*     */   private String[] entitiesID;
/*     */ 
/*     */   
/*     */   private String[] objectives;
/*     */ 
/*     */ 
/*     */   
/*     */   public CommandResultStats() {
/*  31 */     this.entitiesID = STRING_RESULT_TYPES;
/*  32 */     this.objectives = STRING_RESULT_TYPES;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCommandStatForSender(MinecraftServer server, final ICommandSender sender, Type typeIn, int p_184932_4_) {
/*  37 */     String s = this.entitiesID[typeIn.getTypeID()];
/*     */     
/*  39 */     if (s != null) {
/*     */       String s1;
/*  41 */       ICommandSender icommandsender = new ICommandSender()
/*     */         {
/*     */           public String getName()
/*     */           {
/*  45 */             return sender.getName();
/*     */           }
/*     */           
/*     */           public ITextComponent getDisplayName() {
/*  49 */             return sender.getDisplayName();
/*     */           }
/*     */           
/*     */           public void addChatMessage(ITextComponent component) {
/*  53 */             sender.addChatMessage(component);
/*     */           }
/*     */           
/*     */           public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  57 */             return true;
/*     */           }
/*     */           
/*     */           public BlockPos getPosition() {
/*  61 */             return sender.getPosition();
/*     */           }
/*     */           
/*     */           public Vec3d getPositionVector() {
/*  65 */             return sender.getPositionVector();
/*     */           }
/*     */           
/*     */           public World getEntityWorld() {
/*  69 */             return sender.getEntityWorld();
/*     */           }
/*     */           
/*     */           public Entity getCommandSenderEntity() {
/*  73 */             return sender.getCommandSenderEntity();
/*     */           }
/*     */           
/*     */           public boolean sendCommandFeedback() {
/*  77 */             return sender.sendCommandFeedback();
/*     */           }
/*     */           
/*     */           public void setCommandStat(CommandResultStats.Type type, int amount) {
/*  81 */             sender.setCommandStat(type, amount);
/*     */           }
/*     */           
/*     */           public MinecraftServer getServer() {
/*  85 */             return sender.getServer();
/*     */           }
/*     */         };
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  92 */         s1 = CommandBase.getEntityName(server, icommandsender, s);
/*     */       }
/*  94 */       catch (CommandException var12) {
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/*  99 */       String s2 = this.objectives[typeIn.getTypeID()];
/*     */       
/* 101 */       if (s2 != null) {
/*     */         
/* 103 */         Scoreboard scoreboard = sender.getEntityWorld().getScoreboard();
/* 104 */         ScoreObjective scoreobjective = scoreboard.getObjective(s2);
/*     */         
/* 106 */         if (scoreobjective != null)
/*     */         {
/* 108 */           if (scoreboard.entityHasObjective(s1, scoreobjective)) {
/*     */             
/* 110 */             Score score = scoreboard.getOrCreateScore(s1, scoreobjective);
/* 111 */             score.setScorePoints(p_184932_4_);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readStatsFromNBT(NBTTagCompound tagcompound) {
/* 120 */     if (tagcompound.hasKey("CommandStats", 10)) {
/*     */       
/* 122 */       NBTTagCompound nbttagcompound = tagcompound.getCompoundTag("CommandStats"); byte b; int i;
/*     */       Type[] arrayOfType;
/* 124 */       for (i = (arrayOfType = Type.values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/*     */         
/* 126 */         String s = String.valueOf(commandresultstats$type.getTypeName()) + "Name";
/* 127 */         String s1 = String.valueOf(commandresultstats$type.getTypeName()) + "Objective";
/*     */         
/* 129 */         if (nbttagcompound.hasKey(s, 8) && nbttagcompound.hasKey(s1, 8)) {
/*     */           
/* 131 */           String s2 = nbttagcompound.getString(s);
/* 132 */           String s3 = nbttagcompound.getString(s1);
/* 133 */           setScoreBoardStat(this, commandresultstats$type, s2, s3);
/*     */         } 
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeStatsToNBT(NBTTagCompound tagcompound) {
/* 141 */     NBTTagCompound nbttagcompound = new NBTTagCompound(); byte b; int i;
/*     */     Type[] arrayOfType;
/* 143 */     for (i = (arrayOfType = Type.values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/*     */       
/* 145 */       String s = this.entitiesID[commandresultstats$type.getTypeID()];
/* 146 */       String s1 = this.objectives[commandresultstats$type.getTypeID()];
/*     */       
/* 148 */       if (s != null && s1 != null) {
/*     */         
/* 150 */         nbttagcompound.setString(String.valueOf(commandresultstats$type.getTypeName()) + "Name", s);
/* 151 */         nbttagcompound.setString(String.valueOf(commandresultstats$type.getTypeName()) + "Objective", s1);
/*     */       } 
/*     */       b++; }
/*     */     
/* 155 */     if (!nbttagcompound.hasNoTags())
/*     */     {
/* 157 */       tagcompound.setTag("CommandStats", (NBTBase)nbttagcompound);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setScoreBoardStat(CommandResultStats stats, Type resultType, @Nullable String entityID, @Nullable String objectiveName) {
/* 166 */     if (entityID != null && !entityID.isEmpty() && objectiveName != null && !objectiveName.isEmpty()) {
/*     */       
/* 168 */       if (stats.entitiesID == STRING_RESULT_TYPES || stats.objectives == STRING_RESULT_TYPES) {
/*     */         
/* 170 */         stats.entitiesID = new String[NUM_RESULT_TYPES];
/* 171 */         stats.objectives = new String[NUM_RESULT_TYPES];
/*     */       } 
/*     */       
/* 174 */       stats.entitiesID[resultType.getTypeID()] = entityID;
/* 175 */       stats.objectives[resultType.getTypeID()] = objectiveName;
/*     */     }
/*     */     else {
/*     */       
/* 179 */       removeScoreBoardStat(stats, resultType);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void removeScoreBoardStat(CommandResultStats resultStatsIn, Type resultTypeIn) {
/* 188 */     if (resultStatsIn.entitiesID != STRING_RESULT_TYPES && resultStatsIn.objectives != STRING_RESULT_TYPES) {
/*     */       
/* 190 */       resultStatsIn.entitiesID[resultTypeIn.getTypeID()] = null;
/* 191 */       resultStatsIn.objectives[resultTypeIn.getTypeID()] = null;
/* 192 */       boolean flag = true; byte b; int i;
/*     */       Type[] arrayOfType;
/* 194 */       for (i = (arrayOfType = Type.values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/*     */         
/* 196 */         if (resultStatsIn.entitiesID[commandresultstats$type.getTypeID()] != null && resultStatsIn.objectives[commandresultstats$type.getTypeID()] != null) {
/*     */           
/* 198 */           flag = false;
/*     */           break;
/*     */         } 
/*     */         b++; }
/*     */       
/* 203 */       if (flag) {
/*     */         
/* 205 */         resultStatsIn.entitiesID = STRING_RESULT_TYPES;
/* 206 */         resultStatsIn.objectives = STRING_RESULT_TYPES;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addAllStats(CommandResultStats resultStatsIn) {
/*     */     byte b;
/*     */     int i;
/*     */     Type[] arrayOfType;
/* 216 */     for (i = (arrayOfType = Type.values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/*     */       
/* 218 */       setScoreBoardStat(this, commandresultstats$type, resultStatsIn.entitiesID[commandresultstats$type.getTypeID()], resultStatsIn.objectives[commandresultstats$type.getTypeID()]);
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   public enum Type {
/* 224 */     SUCCESS_COUNT(0, "SuccessCount"),
/* 225 */     AFFECTED_BLOCKS(1, "AffectedBlocks"),
/* 226 */     AFFECTED_ENTITIES(2, "AffectedEntities"),
/* 227 */     AFFECTED_ITEMS(3, "AffectedItems"),
/* 228 */     QUERY_RESULT(4, "QueryResult");
/*     */     
/*     */     final int typeID;
/*     */     
/*     */     final String typeName;
/*     */     
/*     */     Type(int id, String name) {
/* 235 */       this.typeID = id;
/* 236 */       this.typeName = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTypeID() {
/* 241 */       return this.typeID;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTypeName() {
/* 246 */       return this.typeName;
/*     */     }
/*     */ 
/*     */     
/*     */     public static String[] getTypeNames() {
/* 251 */       String[] astring = new String[(values()).length];
/* 252 */       int i = 0; byte b; int j;
/*     */       Type[] arrayOfType;
/* 254 */       for (j = (arrayOfType = values()).length, b = 0; b < j; ) { Type commandresultstats$type = arrayOfType[b];
/*     */         
/* 256 */         astring[i++] = commandresultstats$type.getTypeName();
/*     */         b++; }
/*     */       
/* 259 */       return astring;
/*     */     } @Nullable
/*     */     public static Type getTypeByName(String name) {
/*     */       byte b;
/*     */       int i;
/*     */       Type[] arrayOfType;
/* 265 */       for (i = (arrayOfType = values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/*     */         
/* 267 */         if (commandresultstats$type.getTypeName().equals(name))
/*     */         {
/* 269 */           return commandresultstats$type;
/*     */         }
/*     */         b++; }
/*     */       
/* 273 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\command\CommandResultStats.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */