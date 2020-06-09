/*     */ package net.minecraft.world;
/*     */ 
/*     */ import net.minecraft.entity.player.PlayerCapabilities;
/*     */ 
/*     */ public enum GameType
/*     */ {
/*   7 */   NOT_SET(-1, "", ""),
/*   8 */   SURVIVAL(0, "survival", "s"),
/*   9 */   CREATIVE(1, "creative", "c"),
/*  10 */   ADVENTURE(2, "adventure", "a"),
/*  11 */   SPECTATOR(3, "spectator", "sp");
/*     */   
/*     */   int id;
/*     */   
/*     */   String name;
/*     */   String shortName;
/*     */   
/*     */   GameType(int idIn, String nameIn, String shortNameIn) {
/*  19 */     this.id = idIn;
/*  20 */     this.name = nameIn;
/*  21 */     this.shortName = shortNameIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getID() {
/*  29 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  37 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void configurePlayerCapabilities(PlayerCapabilities capabilities) {
/*  45 */     if (this == CREATIVE) {
/*     */       
/*  47 */       capabilities.allowFlying = true;
/*  48 */       capabilities.isCreativeMode = true;
/*  49 */       capabilities.disableDamage = true;
/*     */     }
/*  51 */     else if (this == SPECTATOR) {
/*     */       
/*  53 */       capabilities.allowFlying = true;
/*  54 */       capabilities.isCreativeMode = false;
/*  55 */       capabilities.disableDamage = true;
/*  56 */       capabilities.isFlying = true;
/*     */     }
/*     */     else {
/*     */       
/*  60 */       capabilities.allowFlying = false;
/*  61 */       capabilities.isCreativeMode = false;
/*  62 */       capabilities.disableDamage = false;
/*  63 */       capabilities.isFlying = false;
/*     */     } 
/*     */     
/*  66 */     capabilities.allowEdit = !isAdventure();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAdventure() {
/*  74 */     return !(this != ADVENTURE && this != SPECTATOR);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCreative() {
/*  82 */     return (this == CREATIVE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSurvivalOrAdventure() {
/*  90 */     return !(this != SURVIVAL && this != ADVENTURE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GameType getByID(int idIn) {
/*  98 */     return parseGameTypeWithDefault(idIn, SURVIVAL);
/*     */   } public static GameType parseGameTypeWithDefault(int targetId, GameType fallback) {
/*     */     byte b;
/*     */     int i;
/*     */     GameType[] arrayOfGameType;
/* 103 */     for (i = (arrayOfGameType = values()).length, b = 0; b < i; ) { GameType gametype = arrayOfGameType[b];
/*     */       
/* 105 */       if (gametype.id == targetId)
/*     */       {
/* 107 */         return gametype;
/*     */       }
/*     */       b++; }
/*     */     
/* 111 */     return fallback;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GameType getByName(String gamemodeName) {
/* 119 */     return parseGameTypeWithDefault(gamemodeName, SURVIVAL);
/*     */   } public static GameType parseGameTypeWithDefault(String targetName, GameType fallback) {
/*     */     byte b;
/*     */     int i;
/*     */     GameType[] arrayOfGameType;
/* 124 */     for (i = (arrayOfGameType = values()).length, b = 0; b < i; ) { GameType gametype = arrayOfGameType[b];
/*     */       
/* 126 */       if (gametype.name.equals(targetName) || gametype.shortName.equals(targetName))
/*     */       {
/* 128 */         return gametype;
/*     */       }
/*     */       b++; }
/*     */     
/* 132 */     return fallback;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\GameType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */