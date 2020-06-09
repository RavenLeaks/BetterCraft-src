/*      */ package net.minecraft.world.storage;
/*      */ 
/*      */ import com.google.common.collect.Maps;
/*      */ import java.util.Map;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.crash.ICrashReportDetail;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.util.datafix.DataFixer;
/*      */ import net.minecraft.util.datafix.FixTypes;
/*      */ import net.minecraft.util.datafix.IDataFixer;
/*      */ import net.minecraft.util.datafix.IDataWalker;
/*      */ import net.minecraft.util.datafix.IFixType;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.world.DimensionType;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.GameRules;
/*      */ import net.minecraft.world.GameType;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.WorldType;
/*      */ 
/*      */ public class WorldInfo {
/*      */   private String versionName;
/*      */   private int versionId;
/*      */   private boolean versionSnapshot;
/*   28 */   public static final EnumDifficulty DEFAULT_DIFFICULTY = EnumDifficulty.NORMAL;
/*      */   
/*      */   private long randomSeed;
/*      */   
/*   32 */   private WorldType terrainType = WorldType.DEFAULT;
/*   33 */   private String generatorOptions = "";
/*      */ 
/*      */   
/*      */   private int spawnX;
/*      */ 
/*      */   
/*      */   private int spawnY;
/*      */ 
/*      */   
/*      */   private int spawnZ;
/*      */ 
/*      */   
/*      */   private long totalTime;
/*      */ 
/*      */   
/*      */   private long worldTime;
/*      */ 
/*      */   
/*      */   private long lastTimePlayed;
/*      */ 
/*      */   
/*      */   private long sizeOnDisk;
/*      */ 
/*      */   
/*      */   private NBTTagCompound playerTag;
/*      */   
/*      */   private int dimension;
/*      */   
/*      */   private String levelName;
/*      */   
/*      */   private int saveVersion;
/*      */   
/*      */   private int cleanWeatherTime;
/*      */   
/*      */   private boolean raining;
/*      */   
/*      */   private int rainTime;
/*      */   
/*      */   private boolean thundering;
/*      */   
/*      */   private int thunderTime;
/*      */   
/*      */   private GameType theGameType;
/*      */   
/*      */   private boolean mapFeaturesEnabled;
/*      */   
/*      */   private boolean hardcore;
/*      */   
/*      */   private boolean allowCommands;
/*      */   
/*      */   private boolean initialized;
/*      */   
/*      */   private EnumDifficulty difficulty;
/*      */   
/*      */   private boolean difficultyLocked;
/*      */   
/*      */   private double borderCenterX;
/*      */   
/*      */   private double borderCenterZ;
/*      */   
/*   93 */   private double borderSize = 6.0E7D;
/*      */   private long borderSizeLerpTime;
/*      */   private double borderSizeLerpTarget;
/*   96 */   private double borderSafeZone = 5.0D;
/*   97 */   private double borderDamagePerBlock = 0.2D;
/*   98 */   private int borderWarningDistance = 5;
/*   99 */   private int borderWarningTime = 15;
/*  100 */   private final Map<DimensionType, NBTTagCompound> dimensionData = Maps.newEnumMap(DimensionType.class);
/*  101 */   private GameRules theGameRules = new GameRules();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void registerFixes(DataFixer fixer) {
/*  109 */     fixer.registerWalker(FixTypes.LEVEL, new IDataWalker()
/*      */         {
/*      */           public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn)
/*      */           {
/*  113 */             if (compound.hasKey("Player", 10))
/*      */             {
/*  115 */               compound.setTag("Player", (NBTBase)fixer.process((IFixType)FixTypes.PLAYER, compound.getCompoundTag("Player"), versionIn));
/*      */             }
/*      */             
/*  118 */             return compound;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldInfo(NBTTagCompound nbt) {
/*  125 */     if (nbt.hasKey("Version", 10)) {
/*      */       
/*  127 */       NBTTagCompound nbttagcompound = nbt.getCompoundTag("Version");
/*  128 */       this.versionName = nbttagcompound.getString("Name");
/*  129 */       this.versionId = nbttagcompound.getInteger("Id");
/*  130 */       this.versionSnapshot = nbttagcompound.getBoolean("Snapshot");
/*      */     } 
/*      */     
/*  133 */     this.randomSeed = nbt.getLong("RandomSeed");
/*      */     
/*  135 */     if (nbt.hasKey("generatorName", 8)) {
/*      */       
/*  137 */       String s1 = nbt.getString("generatorName");
/*  138 */       this.terrainType = WorldType.parseWorldType(s1);
/*      */       
/*  140 */       if (this.terrainType == null) {
/*      */         
/*  142 */         this.terrainType = WorldType.DEFAULT;
/*      */       }
/*  144 */       else if (this.terrainType.isVersioned()) {
/*      */         
/*  146 */         int i = 0;
/*      */         
/*  148 */         if (nbt.hasKey("generatorVersion", 99))
/*      */         {
/*  150 */           i = nbt.getInteger("generatorVersion");
/*      */         }
/*      */         
/*  153 */         this.terrainType = this.terrainType.getWorldTypeForGeneratorVersion(i);
/*      */       } 
/*      */       
/*  156 */       if (nbt.hasKey("generatorOptions", 8))
/*      */       {
/*  158 */         this.generatorOptions = nbt.getString("generatorOptions");
/*      */       }
/*      */     } 
/*      */     
/*  162 */     this.theGameType = GameType.getByID(nbt.getInteger("GameType"));
/*      */     
/*  164 */     if (nbt.hasKey("MapFeatures", 99)) {
/*      */       
/*  166 */       this.mapFeaturesEnabled = nbt.getBoolean("MapFeatures");
/*      */     }
/*      */     else {
/*      */       
/*  170 */       this.mapFeaturesEnabled = true;
/*      */     } 
/*      */     
/*  173 */     this.spawnX = nbt.getInteger("SpawnX");
/*  174 */     this.spawnY = nbt.getInteger("SpawnY");
/*  175 */     this.spawnZ = nbt.getInteger("SpawnZ");
/*  176 */     this.totalTime = nbt.getLong("Time");
/*      */     
/*  178 */     if (nbt.hasKey("DayTime", 99)) {
/*      */       
/*  180 */       this.worldTime = nbt.getLong("DayTime");
/*      */     }
/*      */     else {
/*      */       
/*  184 */       this.worldTime = this.totalTime;
/*      */     } 
/*      */     
/*  187 */     this.lastTimePlayed = nbt.getLong("LastPlayed");
/*  188 */     this.sizeOnDisk = nbt.getLong("SizeOnDisk");
/*  189 */     this.levelName = nbt.getString("LevelName");
/*  190 */     this.saveVersion = nbt.getInteger("version");
/*  191 */     this.cleanWeatherTime = nbt.getInteger("clearWeatherTime");
/*  192 */     this.rainTime = nbt.getInteger("rainTime");
/*  193 */     this.raining = nbt.getBoolean("raining");
/*  194 */     this.thunderTime = nbt.getInteger("thunderTime");
/*  195 */     this.thundering = nbt.getBoolean("thundering");
/*  196 */     this.hardcore = nbt.getBoolean("hardcore");
/*      */     
/*  198 */     if (nbt.hasKey("initialized", 99)) {
/*      */       
/*  200 */       this.initialized = nbt.getBoolean("initialized");
/*      */     }
/*      */     else {
/*      */       
/*  204 */       this.initialized = true;
/*      */     } 
/*      */     
/*  207 */     if (nbt.hasKey("allowCommands", 99)) {
/*      */       
/*  209 */       this.allowCommands = nbt.getBoolean("allowCommands");
/*      */     }
/*      */     else {
/*      */       
/*  213 */       this.allowCommands = (this.theGameType == GameType.CREATIVE);
/*      */     } 
/*      */     
/*  216 */     if (nbt.hasKey("Player", 10)) {
/*      */       
/*  218 */       this.playerTag = nbt.getCompoundTag("Player");
/*  219 */       this.dimension = this.playerTag.getInteger("Dimension");
/*      */     } 
/*      */     
/*  222 */     if (nbt.hasKey("GameRules", 10))
/*      */     {
/*  224 */       this.theGameRules.readFromNBT(nbt.getCompoundTag("GameRules"));
/*      */     }
/*      */     
/*  227 */     if (nbt.hasKey("Difficulty", 99))
/*      */     {
/*  229 */       this.difficulty = EnumDifficulty.getDifficultyEnum(nbt.getByte("Difficulty"));
/*      */     }
/*      */     
/*  232 */     if (nbt.hasKey("DifficultyLocked", 1))
/*      */     {
/*  234 */       this.difficultyLocked = nbt.getBoolean("DifficultyLocked");
/*      */     }
/*      */     
/*  237 */     if (nbt.hasKey("BorderCenterX", 99))
/*      */     {
/*  239 */       this.borderCenterX = nbt.getDouble("BorderCenterX");
/*      */     }
/*      */     
/*  242 */     if (nbt.hasKey("BorderCenterZ", 99))
/*      */     {
/*  244 */       this.borderCenterZ = nbt.getDouble("BorderCenterZ");
/*      */     }
/*      */     
/*  247 */     if (nbt.hasKey("BorderSize", 99))
/*      */     {
/*  249 */       this.borderSize = nbt.getDouble("BorderSize");
/*      */     }
/*      */     
/*  252 */     if (nbt.hasKey("BorderSizeLerpTime", 99))
/*      */     {
/*  254 */       this.borderSizeLerpTime = nbt.getLong("BorderSizeLerpTime");
/*      */     }
/*      */     
/*  257 */     if (nbt.hasKey("BorderSizeLerpTarget", 99))
/*      */     {
/*  259 */       this.borderSizeLerpTarget = nbt.getDouble("BorderSizeLerpTarget");
/*      */     }
/*      */     
/*  262 */     if (nbt.hasKey("BorderSafeZone", 99))
/*      */     {
/*  264 */       this.borderSafeZone = nbt.getDouble("BorderSafeZone");
/*      */     }
/*      */     
/*  267 */     if (nbt.hasKey("BorderDamagePerBlock", 99))
/*      */     {
/*  269 */       this.borderDamagePerBlock = nbt.getDouble("BorderDamagePerBlock");
/*      */     }
/*      */     
/*  272 */     if (nbt.hasKey("BorderWarningBlocks", 99))
/*      */     {
/*  274 */       this.borderWarningDistance = nbt.getInteger("BorderWarningBlocks");
/*      */     }
/*      */     
/*  277 */     if (nbt.hasKey("BorderWarningTime", 99))
/*      */     {
/*  279 */       this.borderWarningTime = nbt.getInteger("BorderWarningTime");
/*      */     }
/*      */     
/*  282 */     if (nbt.hasKey("DimensionData", 10)) {
/*      */       
/*  284 */       NBTTagCompound nbttagcompound1 = nbt.getCompoundTag("DimensionData");
/*      */       
/*  286 */       for (String s : nbttagcompound1.getKeySet())
/*      */       {
/*  288 */         this.dimensionData.put(DimensionType.getById(Integer.parseInt(s)), nbttagcompound1.getCompoundTag(s));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldInfo(WorldSettings settings, String name) {
/*  295 */     populateFromWorldSettings(settings);
/*  296 */     this.levelName = name;
/*  297 */     this.difficulty = DEFAULT_DIFFICULTY;
/*  298 */     this.initialized = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void populateFromWorldSettings(WorldSettings settings) {
/*  303 */     this.randomSeed = settings.getSeed();
/*  304 */     this.theGameType = settings.getGameType();
/*  305 */     this.mapFeaturesEnabled = settings.isMapFeaturesEnabled();
/*  306 */     this.hardcore = settings.getHardcoreEnabled();
/*  307 */     this.terrainType = settings.getTerrainType();
/*  308 */     this.generatorOptions = settings.getGeneratorOptions();
/*  309 */     this.allowCommands = settings.areCommandsAllowed();
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldInfo(WorldInfo worldInformation) {
/*  314 */     this.randomSeed = worldInformation.randomSeed;
/*  315 */     this.terrainType = worldInformation.terrainType;
/*  316 */     this.generatorOptions = worldInformation.generatorOptions;
/*  317 */     this.theGameType = worldInformation.theGameType;
/*  318 */     this.mapFeaturesEnabled = worldInformation.mapFeaturesEnabled;
/*  319 */     this.spawnX = worldInformation.spawnX;
/*  320 */     this.spawnY = worldInformation.spawnY;
/*  321 */     this.spawnZ = worldInformation.spawnZ;
/*  322 */     this.totalTime = worldInformation.totalTime;
/*  323 */     this.worldTime = worldInformation.worldTime;
/*  324 */     this.lastTimePlayed = worldInformation.lastTimePlayed;
/*  325 */     this.sizeOnDisk = worldInformation.sizeOnDisk;
/*  326 */     this.playerTag = worldInformation.playerTag;
/*  327 */     this.dimension = worldInformation.dimension;
/*  328 */     this.levelName = worldInformation.levelName;
/*  329 */     this.saveVersion = worldInformation.saveVersion;
/*  330 */     this.rainTime = worldInformation.rainTime;
/*  331 */     this.raining = worldInformation.raining;
/*  332 */     this.thunderTime = worldInformation.thunderTime;
/*  333 */     this.thundering = worldInformation.thundering;
/*  334 */     this.hardcore = worldInformation.hardcore;
/*  335 */     this.allowCommands = worldInformation.allowCommands;
/*  336 */     this.initialized = worldInformation.initialized;
/*  337 */     this.theGameRules = worldInformation.theGameRules;
/*  338 */     this.difficulty = worldInformation.difficulty;
/*  339 */     this.difficultyLocked = worldInformation.difficultyLocked;
/*  340 */     this.borderCenterX = worldInformation.borderCenterX;
/*  341 */     this.borderCenterZ = worldInformation.borderCenterZ;
/*  342 */     this.borderSize = worldInformation.borderSize;
/*  343 */     this.borderSizeLerpTime = worldInformation.borderSizeLerpTime;
/*  344 */     this.borderSizeLerpTarget = worldInformation.borderSizeLerpTarget;
/*  345 */     this.borderSafeZone = worldInformation.borderSafeZone;
/*  346 */     this.borderDamagePerBlock = worldInformation.borderDamagePerBlock;
/*  347 */     this.borderWarningTime = worldInformation.borderWarningTime;
/*  348 */     this.borderWarningDistance = worldInformation.borderWarningDistance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound cloneNBTCompound(@Nullable NBTTagCompound nbt) {
/*  356 */     if (nbt == null)
/*      */     {
/*  358 */       nbt = this.playerTag;
/*      */     }
/*      */     
/*  361 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  362 */     updateTagCompound(nbttagcompound, nbt);
/*  363 */     return nbttagcompound;
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateTagCompound(NBTTagCompound nbt, NBTTagCompound playerNbt) {
/*  368 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*  369 */     nbttagcompound.setString("Name", "1.12.2");
/*  370 */     nbttagcompound.setInteger("Id", 1343);
/*  371 */     nbttagcompound.setBoolean("Snapshot", false);
/*  372 */     nbt.setTag("Version", (NBTBase)nbttagcompound);
/*  373 */     nbt.setInteger("DataVersion", 1343);
/*  374 */     nbt.setLong("RandomSeed", this.randomSeed);
/*  375 */     nbt.setString("generatorName", this.terrainType.getWorldTypeName());
/*  376 */     nbt.setInteger("generatorVersion", this.terrainType.getGeneratorVersion());
/*  377 */     nbt.setString("generatorOptions", this.generatorOptions);
/*  378 */     nbt.setInteger("GameType", this.theGameType.getID());
/*  379 */     nbt.setBoolean("MapFeatures", this.mapFeaturesEnabled);
/*  380 */     nbt.setInteger("SpawnX", this.spawnX);
/*  381 */     nbt.setInteger("SpawnY", this.spawnY);
/*  382 */     nbt.setInteger("SpawnZ", this.spawnZ);
/*  383 */     nbt.setLong("Time", this.totalTime);
/*  384 */     nbt.setLong("DayTime", this.worldTime);
/*  385 */     nbt.setLong("SizeOnDisk", this.sizeOnDisk);
/*  386 */     nbt.setLong("LastPlayed", MinecraftServer.getCurrentTimeMillis());
/*  387 */     nbt.setString("LevelName", this.levelName);
/*  388 */     nbt.setInteger("version", this.saveVersion);
/*  389 */     nbt.setInteger("clearWeatherTime", this.cleanWeatherTime);
/*  390 */     nbt.setInteger("rainTime", this.rainTime);
/*  391 */     nbt.setBoolean("raining", this.raining);
/*  392 */     nbt.setInteger("thunderTime", this.thunderTime);
/*  393 */     nbt.setBoolean("thundering", this.thundering);
/*  394 */     nbt.setBoolean("hardcore", this.hardcore);
/*  395 */     nbt.setBoolean("allowCommands", this.allowCommands);
/*  396 */     nbt.setBoolean("initialized", this.initialized);
/*  397 */     nbt.setDouble("BorderCenterX", this.borderCenterX);
/*  398 */     nbt.setDouble("BorderCenterZ", this.borderCenterZ);
/*  399 */     nbt.setDouble("BorderSize", this.borderSize);
/*  400 */     nbt.setLong("BorderSizeLerpTime", this.borderSizeLerpTime);
/*  401 */     nbt.setDouble("BorderSafeZone", this.borderSafeZone);
/*  402 */     nbt.setDouble("BorderDamagePerBlock", this.borderDamagePerBlock);
/*  403 */     nbt.setDouble("BorderSizeLerpTarget", this.borderSizeLerpTarget);
/*  404 */     nbt.setDouble("BorderWarningBlocks", this.borderWarningDistance);
/*  405 */     nbt.setDouble("BorderWarningTime", this.borderWarningTime);
/*      */     
/*  407 */     if (this.difficulty != null)
/*      */     {
/*  409 */       nbt.setByte("Difficulty", (byte)this.difficulty.getDifficultyId());
/*      */     }
/*      */     
/*  412 */     nbt.setBoolean("DifficultyLocked", this.difficultyLocked);
/*  413 */     nbt.setTag("GameRules", (NBTBase)this.theGameRules.writeToNBT());
/*  414 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/*      */     
/*  416 */     for (Map.Entry<DimensionType, NBTTagCompound> entry : this.dimensionData.entrySet())
/*      */     {
/*  418 */       nbttagcompound1.setTag(String.valueOf(((DimensionType)entry.getKey()).getId()), (NBTBase)entry.getValue());
/*      */     }
/*      */     
/*  421 */     nbt.setTag("DimensionData", (NBTBase)nbttagcompound1);
/*      */     
/*  423 */     if (playerNbt != null)
/*      */     {
/*  425 */       nbt.setTag("Player", (NBTBase)playerNbt);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getSeed() {
/*  434 */     return this.randomSeed;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSpawnX() {
/*  442 */     return this.spawnX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSpawnY() {
/*  450 */     return this.spawnY;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSpawnZ() {
/*  458 */     return this.spawnZ;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getWorldTotalTime() {
/*  463 */     return this.totalTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getWorldTime() {
/*  471 */     return this.worldTime;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getSizeOnDisk() {
/*  476 */     return this.sizeOnDisk;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound getPlayerNBTTagCompound() {
/*  484 */     return this.playerTag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSpawnX(int x) {
/*  492 */     this.spawnX = x;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSpawnY(int y) {
/*  500 */     this.spawnY = y;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSpawnZ(int z) {
/*  508 */     this.spawnZ = z;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setWorldTotalTime(long time) {
/*  513 */     this.totalTime = time;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorldTime(long time) {
/*  521 */     this.worldTime = time;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSpawn(BlockPos spawnPoint) {
/*  526 */     this.spawnX = spawnPoint.getX();
/*  527 */     this.spawnY = spawnPoint.getY();
/*  528 */     this.spawnZ = spawnPoint.getZ();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getWorldName() {
/*  536 */     return this.levelName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setWorldName(String worldName) {
/*  541 */     this.levelName = worldName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSaveVersion() {
/*  549 */     return this.saveVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSaveVersion(int version) {
/*  557 */     this.saveVersion = version;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLastTimePlayed() {
/*  565 */     return this.lastTimePlayed;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCleanWeatherTime() {
/*  570 */     return this.cleanWeatherTime;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCleanWeatherTime(int cleanWeatherTimeIn) {
/*  575 */     this.cleanWeatherTime = cleanWeatherTimeIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isThundering() {
/*  583 */     return this.thundering;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThundering(boolean thunderingIn) {
/*  591 */     this.thundering = thunderingIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getThunderTime() {
/*  599 */     return this.thunderTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThunderTime(int time) {
/*  607 */     this.thunderTime = time;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRaining() {
/*  615 */     return this.raining;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRaining(boolean isRaining) {
/*  623 */     this.raining = isRaining;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getRainTime() {
/*  631 */     return this.rainTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRainTime(int time) {
/*  639 */     this.rainTime = time;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameType getGameType() {
/*  647 */     return this.theGameType;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMapFeaturesEnabled() {
/*  655 */     return this.mapFeaturesEnabled;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMapFeaturesEnabled(boolean enabled) {
/*  660 */     this.mapFeaturesEnabled = enabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGameType(GameType type) {
/*  668 */     this.theGameType = type;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isHardcoreModeEnabled() {
/*  676 */     return this.hardcore;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHardcore(boolean hardcoreIn) {
/*  681 */     this.hardcore = hardcoreIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldType getTerrainType() {
/*  686 */     return this.terrainType;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTerrainType(WorldType type) {
/*  691 */     this.terrainType = type;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getGeneratorOptions() {
/*  696 */     return (this.generatorOptions == null) ? "" : this.generatorOptions;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean areCommandsAllowed() {
/*  704 */     return this.allowCommands;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAllowCommands(boolean allow) {
/*  709 */     this.allowCommands = allow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isInitialized() {
/*  717 */     return this.initialized;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerInitialized(boolean initializedIn) {
/*  725 */     this.initialized = initializedIn;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameRules getGameRulesInstance() {
/*  733 */     return this.theGameRules;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getBorderCenterX() {
/*  741 */     return this.borderCenterX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getBorderCenterZ() {
/*  749 */     return this.borderCenterZ;
/*      */   }
/*      */ 
/*      */   
/*      */   public double getBorderSize() {
/*  754 */     return this.borderSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorderSize(double size) {
/*  762 */     this.borderSize = size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getBorderLerpTime() {
/*  770 */     return this.borderSizeLerpTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorderLerpTime(long time) {
/*  778 */     this.borderSizeLerpTime = time;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getBorderLerpTarget() {
/*  786 */     return this.borderSizeLerpTarget;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorderLerpTarget(double lerpSize) {
/*  794 */     this.borderSizeLerpTarget = lerpSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getBorderCenterZ(double posZ) {
/*  802 */     this.borderCenterZ = posZ;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getBorderCenterX(double posX) {
/*  810 */     this.borderCenterX = posX;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getBorderSafeZone() {
/*  818 */     return this.borderSafeZone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorderSafeZone(double amount) {
/*  826 */     this.borderSafeZone = amount;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getBorderDamagePerBlock() {
/*  834 */     return this.borderDamagePerBlock;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorderDamagePerBlock(double damage) {
/*  842 */     this.borderDamagePerBlock = damage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBorderWarningDistance() {
/*  850 */     return this.borderWarningDistance;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBorderWarningTime() {
/*  858 */     return this.borderWarningTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorderWarningDistance(int amountOfBlocks) {
/*  866 */     this.borderWarningDistance = amountOfBlocks;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBorderWarningTime(int ticks) {
/*  874 */     this.borderWarningTime = ticks;
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumDifficulty getDifficulty() {
/*  879 */     return this.difficulty;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDifficulty(EnumDifficulty newDifficulty) {
/*  884 */     this.difficulty = newDifficulty;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDifficultyLocked() {
/*  889 */     return this.difficultyLocked;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDifficultyLocked(boolean locked) {
/*  894 */     this.difficultyLocked = locked;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addToCrashReport(CrashReportCategory category) {
/*  902 */     category.setDetail("Level seed", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/*  906 */             return String.valueOf(WorldInfo.this.getSeed());
/*      */           }
/*      */         });
/*  909 */     category.setDetail("Level generator", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/*  913 */             return String.format("ID %02d - %s, ver %d. Features enabled: %b", new Object[] { Integer.valueOf(WorldInfo.access$0(this.this$0).getWorldTypeID()), WorldInfo.access$0(this.this$0).getWorldTypeName(), Integer.valueOf(WorldInfo.access$0(this.this$0).getGeneratorVersion()), Boolean.valueOf(WorldInfo.access$1(this.this$0)) });
/*      */           }
/*      */         });
/*  916 */     category.setDetail("Level generator options", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/*  920 */             return WorldInfo.this.generatorOptions;
/*      */           }
/*      */         });
/*  923 */     category.setDetail("Level spawn location", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/*  927 */             return CrashReportCategory.getCoordinateInfo(WorldInfo.this.spawnX, WorldInfo.this.spawnY, WorldInfo.this.spawnZ);
/*      */           }
/*      */         });
/*  930 */     category.setDetail("Level time", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/*  934 */             return String.format("%d game time, %d day time", new Object[] { Long.valueOf(WorldInfo.access$6(this.this$0)), Long.valueOf(WorldInfo.access$7(this.this$0)) });
/*      */           }
/*      */         });
/*  937 */     category.setDetail("Level dimension", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/*  941 */             return String.valueOf(WorldInfo.this.dimension);
/*      */           }
/*      */         });
/*  944 */     category.setDetail("Level storage version", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/*  948 */             String s = "Unknown?";
/*      */ 
/*      */             
/*      */             try {
/*  952 */               switch (WorldInfo.this.saveVersion) {
/*      */                 
/*      */                 case 19132:
/*  955 */                   s = "McRegion";
/*      */                   break;
/*      */                 
/*      */                 case 19133:
/*  959 */                   s = "Anvil";
/*      */                   break;
/*      */               } 
/*  962 */             } catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  967 */             return String.format("0x%05X - %s", new Object[] { Integer.valueOf(WorldInfo.access$9(this.this$0)), s });
/*      */           }
/*      */         });
/*  970 */     category.setDetail("Level weather", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/*  974 */             return String.format("Rain time: %d (now: %b), thunder time: %d (now: %b)", new Object[] { Integer.valueOf(WorldInfo.access$10(this.this$0)), Boolean.valueOf(WorldInfo.access$11(this.this$0)), Integer.valueOf(WorldInfo.access$12(this.this$0)), Boolean.valueOf(WorldInfo.access$13(this.this$0)) });
/*      */           }
/*      */         });
/*  977 */     category.setDetail("Level game mode", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/*  981 */             return String.format("Game mode: %s (ID %d). Hardcore: %b. Cheats: %b", new Object[] { WorldInfo.access$14(this.this$0).getName(), Integer.valueOf(WorldInfo.access$14(this.this$0).getID()), Boolean.valueOf(WorldInfo.access$15(this.this$0)), Boolean.valueOf(WorldInfo.access$16(this.this$0)) });
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public NBTTagCompound getDimensionData(DimensionType dimensionIn) {
/*  988 */     NBTTagCompound nbttagcompound = this.dimensionData.get(dimensionIn);
/*  989 */     return (nbttagcompound == null) ? new NBTTagCompound() : nbttagcompound;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDimensionData(DimensionType dimensionIn, NBTTagCompound compound) {
/*  994 */     this.dimensionData.put(dimensionIn, compound);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getVersionId() {
/*  999 */     return this.versionId;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isVersionSnapshot() {
/* 1004 */     return this.versionSnapshot;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getVersionName() {
/* 1009 */     return this.versionName;
/*      */   }
/*      */   
/*      */   protected WorldInfo() {}
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\WorldInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */