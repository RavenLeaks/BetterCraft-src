/*     */ package net.minecraft.world;
/*     */ 
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class WorldSettings
/*     */ {
/*     */   private final long seed;
/*     */   private final GameType theGameType;
/*     */   private final boolean mapFeaturesEnabled;
/*     */   private final boolean hardcoreEnabled;
/*     */   private final WorldType terrainType;
/*     */   private boolean commandsAllowed;
/*     */   private boolean bonusChestEnabled;
/*     */   private String generatorOptions;
/*     */   
/*     */   public WorldSettings(long seedIn, GameType gameType, boolean enableMapFeatures, boolean hardcoreMode, WorldType worldTypeIn) {
/*  31 */     this.generatorOptions = "";
/*  32 */     this.seed = seedIn;
/*  33 */     this.theGameType = gameType;
/*  34 */     this.mapFeaturesEnabled = enableMapFeatures;
/*  35 */     this.hardcoreEnabled = hardcoreMode;
/*  36 */     this.terrainType = worldTypeIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldSettings(WorldInfo info) {
/*  41 */     this(info.getSeed(), info.getGameType(), info.isMapFeaturesEnabled(), info.isHardcoreModeEnabled(), info.getTerrainType());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldSettings enableBonusChest() {
/*  49 */     this.bonusChestEnabled = true;
/*  50 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldSettings enableCommands() {
/*  58 */     this.commandsAllowed = true;
/*  59 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldSettings setGeneratorOptions(String options) {
/*  64 */     this.generatorOptions = options;
/*  65 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBonusChestEnabled() {
/*  73 */     return this.bonusChestEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSeed() {
/*  81 */     return this.seed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameType getGameType() {
/*  89 */     return this.theGameType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getHardcoreEnabled() {
/*  97 */     return this.hardcoreEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMapFeaturesEnabled() {
/* 105 */     return this.mapFeaturesEnabled;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldType getTerrainType() {
/* 110 */     return this.terrainType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean areCommandsAllowed() {
/* 118 */     return this.commandsAllowed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GameType getGameTypeById(int id) {
/* 126 */     return GameType.getByID(id);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getGeneratorOptions() {
/* 131 */     return this.generatorOptions;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\WorldSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */