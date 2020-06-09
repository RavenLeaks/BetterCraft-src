/*     */ package net.minecraft.server.integrated;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.mojang.authlib.GameProfileRepository;
/*     */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*     */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.concurrent.Future;
/*     */ import net.minecraft.client.ClientBrandRetriever;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.multiplayer.ThreadLanServerPing;
/*     */ import net.minecraft.command.ServerCommandManager;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.profiler.Snooper;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.server.management.PlayerProfileCache;
/*     */ import net.minecraft.util.CryptManager;
/*     */ import net.minecraft.util.HttpUtil;
/*     */ import net.minecraft.util.Util;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraft.world.IWorldEventListener;
/*     */ import net.minecraft.world.ServerWorldEventHandler;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.WorldServerDemo;
/*     */ import net.minecraft.world.WorldServerMulti;
/*     */ import net.minecraft.world.WorldSettings;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import optifine.Reflector;
/*     */ import optifine.WorldServerOF;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class IntegratedServer
/*     */   extends MinecraftServer {
/*  42 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final Minecraft mc;
/*     */   
/*     */   private final WorldSettings theWorldSettings;
/*     */   
/*     */   private boolean isGamePaused;
/*     */   private boolean isPublic;
/*     */   private ThreadLanServerPing lanServerPing;
/*     */   
/*     */   public IntegratedServer(Minecraft clientIn, String folderNameIn, String worldNameIn, WorldSettings worldSettingsIn, YggdrasilAuthenticationService authServiceIn, MinecraftSessionService sessionServiceIn, GameProfileRepository profileRepoIn, PlayerProfileCache profileCacheIn) {
/*  53 */     super(new File(clientIn.mcDataDir, "saves"), clientIn.getProxy(), clientIn.getDataFixer(), authServiceIn, sessionServiceIn, profileRepoIn, profileCacheIn);
/*  54 */     setServerOwner(Minecraft.getSession().getUsername());
/*  55 */     setFolderName(folderNameIn);
/*  56 */     setWorldName(worldNameIn);
/*  57 */     setDemo(clientIn.isDemo());
/*  58 */     canCreateBonusChest(worldSettingsIn.isBonusChestEnabled());
/*  59 */     setBuildLimit(256);
/*  60 */     setPlayerList(new IntegratedPlayerList(this));
/*  61 */     this.mc = clientIn;
/*  62 */     this.theWorldSettings = isDemo() ? WorldServerDemo.DEMO_WORLD_SETTINGS : worldSettingsIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerCommandManager createNewCommandManager() {
/*  67 */     return new IntegratedServerCommandManager(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadAllWorlds(String saveName, String worldNameIn, long seed, WorldType type, String generatorOptions) {
/*  72 */     convertMapIfNeeded(saveName);
/*  73 */     ISaveHandler isavehandler = getActiveAnvilConverter().getSaveLoader(saveName, true);
/*  74 */     setResourcePackFromWorld(getFolderName(), isavehandler);
/*  75 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*     */     
/*  77 */     if (Reflector.DimensionManager.exists()) {
/*     */       
/*  79 */       WorldServer worldserver = isDemo() ? (WorldServer)(new WorldServerDemo(this, isavehandler, worldinfo, 0, this.theProfiler)).init() : (WorldServer)(new WorldServerOF(this, isavehandler, worldinfo, 0, this.theProfiler)).init();
/*  80 */       worldserver.initialize(this.theWorldSettings);
/*  81 */       Integer[] ainteger = (Integer[])Reflector.call(Reflector.DimensionManager_getStaticDimensionIDs, new Object[0]);
/*  82 */       Integer[] ainteger1 = ainteger;
/*  83 */       int i1 = ainteger.length;
/*     */       
/*  85 */       for (int j1 = 0; j1 < i1; j1++) {
/*     */         
/*  87 */         int k = ainteger1[j1].intValue();
/*  88 */         WorldServer worldserver1 = (k == 0) ? worldserver : (WorldServer)(new WorldServerMulti(this, isavehandler, k, worldserver, this.theProfiler)).init();
/*  89 */         worldserver1.addEventListener((IWorldEventListener)new ServerWorldEventHandler(this, worldserver1));
/*     */         
/*  91 */         if (!isSinglePlayer())
/*     */         {
/*  93 */           worldserver1.getWorldInfo().setGameType(getGameType());
/*     */         }
/*     */         
/*  96 */         if (Reflector.EventBus.exists())
/*     */         {
/*  98 */           Reflector.postForgeBusEvent(Reflector.WorldEvent_Load_Constructor, new Object[] { worldserver1 });
/*     */         }
/*     */       } 
/*     */       
/* 102 */       getPlayerList().setPlayerManager(new WorldServer[] { worldserver });
/*     */       
/* 104 */       if (worldserver.getWorldInfo().getDifficulty() == null)
/*     */       {
/* 106 */         setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 111 */       this.worldServers = new WorldServer[3];
/* 112 */       this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
/* 113 */       setResourcePackFromWorld(getFolderName(), isavehandler);
/*     */       
/* 115 */       if (worldinfo == null) {
/*     */         
/* 117 */         worldinfo = new WorldInfo(this.theWorldSettings, worldNameIn);
/*     */       }
/*     */       else {
/*     */         
/* 121 */         worldinfo.setWorldName(worldNameIn);
/*     */       } 
/*     */       
/* 124 */       for (int l = 0; l < this.worldServers.length; l++) {
/*     */         
/* 126 */         int i1 = 0;
/*     */         
/* 128 */         if (l == 1)
/*     */         {
/* 130 */           i1 = -1;
/*     */         }
/*     */         
/* 133 */         if (l == 2)
/*     */         {
/* 135 */           i1 = 1;
/*     */         }
/*     */         
/* 138 */         if (l == 0) {
/*     */           
/* 140 */           if (isDemo()) {
/*     */             
/* 142 */             this.worldServers[l] = (WorldServer)(new WorldServerDemo(this, isavehandler, worldinfo, i1, this.theProfiler)).init();
/*     */           }
/*     */           else {
/*     */             
/* 146 */             this.worldServers[l] = (WorldServer)(new WorldServerOF(this, isavehandler, worldinfo, i1, this.theProfiler)).init();
/*     */           } 
/*     */           
/* 149 */           this.worldServers[l].initialize(this.theWorldSettings);
/*     */         }
/*     */         else {
/*     */           
/* 153 */           this.worldServers[l] = (WorldServer)(new WorldServerMulti(this, isavehandler, i1, this.worldServers[0], this.theProfiler)).init();
/*     */         } 
/*     */         
/* 156 */         this.worldServers[l].addEventListener((IWorldEventListener)new ServerWorldEventHandler(this, this.worldServers[l]));
/*     */       } 
/*     */       
/* 159 */       getPlayerList().setPlayerManager(this.worldServers);
/*     */       
/* 161 */       if (this.worldServers[0].getWorldInfo().getDifficulty() == null)
/*     */       {
/* 163 */         setDifficultyForAllWorlds(this.mc.gameSettings.difficulty);
/*     */       }
/*     */     } 
/*     */     
/* 167 */     initialWorldChunkLoad();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean startServer() throws IOException {
/* 175 */     LOGGER.info("Starting integrated minecraft server version 1.12.2");
/* 176 */     setOnlineMode(true);
/* 177 */     setCanSpawnAnimals(true);
/* 178 */     setCanSpawnNPCs(true);
/* 179 */     setAllowPvp(true);
/* 180 */     setAllowFlight(true);
/* 181 */     LOGGER.info("Generating keypair");
/* 182 */     setKeyPair(CryptManager.generateKeyPair());
/*     */     
/* 184 */     if (Reflector.FMLCommonHandler_handleServerAboutToStart.exists()) {
/*     */       
/* 186 */       Object object = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/*     */       
/* 188 */       if (!Reflector.callBoolean(object, Reflector.FMLCommonHandler_handleServerAboutToStart, new Object[] { this }))
/*     */       {
/* 190 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 194 */     loadAllWorlds(getFolderName(), getWorldName(), this.theWorldSettings.getSeed(), this.theWorldSettings.getTerrainType(), this.theWorldSettings.getGeneratorOptions());
/* 195 */     setMOTD(String.valueOf(getServerOwner()) + " - " + this.worldServers[0].getWorldInfo().getWorldName());
/*     */     
/* 197 */     if (Reflector.FMLCommonHandler_handleServerStarting.exists()) {
/*     */       
/* 199 */       Object object1 = Reflector.call(Reflector.FMLCommonHandler_instance, new Object[0]);
/*     */       
/* 201 */       if (Reflector.FMLCommonHandler_handleServerStarting.getReturnType() == boolean.class)
/*     */       {
/* 203 */         return Reflector.callBoolean(object1, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
/*     */       }
/*     */       
/* 206 */       Reflector.callVoid(object1, Reflector.FMLCommonHandler_handleServerStarting, new Object[] { this });
/*     */     } 
/*     */     
/* 209 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void tick() {
/* 217 */     boolean flag = this.isGamePaused;
/* 218 */     this.isGamePaused = (Minecraft.getMinecraft().getConnection() != null && Minecraft.getMinecraft().isGamePaused());
/*     */     
/* 220 */     if (!flag && this.isGamePaused) {
/*     */       
/* 222 */       LOGGER.info("Saving and pausing game...");
/* 223 */       getPlayerList().saveAllPlayerData();
/* 224 */       saveAllWorlds(false);
/*     */     } 
/*     */     
/* 227 */     if (this.isGamePaused) {
/*     */       
/* 229 */       synchronized (this.futureTaskQueue)
/*     */       {
/* 231 */         while (!this.futureTaskQueue.isEmpty())
/*     */         {
/* 233 */           Util.runTask(this.futureTaskQueue.poll(), LOGGER);
/*     */         }
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 239 */       super.tick();
/*     */       
/* 241 */       if (this.mc.gameSettings.renderDistanceChunks != getPlayerList().getViewDistance()) {
/*     */         
/* 243 */         LOGGER.info("Changing view distance to {}, from {}", Integer.valueOf(this.mc.gameSettings.renderDistanceChunks), Integer.valueOf(getPlayerList().getViewDistance()));
/* 244 */         getPlayerList().setViewDistance(this.mc.gameSettings.renderDistanceChunks);
/*     */       } 
/*     */       
/* 247 */       if (this.mc.world != null) {
/*     */         
/* 249 */         WorldInfo worldinfo1 = this.worldServers[0].getWorldInfo();
/* 250 */         WorldInfo worldinfo = this.mc.world.getWorldInfo();
/*     */         
/* 252 */         if (!worldinfo1.isDifficultyLocked() && worldinfo.getDifficulty() != worldinfo1.getDifficulty()) {
/*     */           
/* 254 */           LOGGER.info("Changing difficulty to {}, from {}", worldinfo.getDifficulty(), worldinfo1.getDifficulty());
/* 255 */           setDifficultyForAllWorlds(worldinfo.getDifficulty());
/*     */         }
/* 257 */         else if (worldinfo.isDifficultyLocked() && !worldinfo1.isDifficultyLocked()) {
/*     */           
/* 259 */           LOGGER.info("Locking difficulty to {}", worldinfo.getDifficulty()); byte b; int i;
/*     */           WorldServer[] arrayOfWorldServer;
/* 261 */           for (i = (arrayOfWorldServer = this.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/*     */             
/* 263 */             if (worldserver != null)
/*     */             {
/* 265 */               worldserver.getWorldInfo().setDifficultyLocked(true);
/*     */             }
/*     */             b++; }
/*     */         
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean canStructuresSpawn() {
/* 275 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public GameType getGameType() {
/* 280 */     return this.theWorldSettings.getGameType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumDifficulty getDifficulty() {
/* 288 */     return (this.mc.world == null) ? this.mc.gameSettings.difficulty : this.mc.world.getWorldInfo().getDifficulty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHardcore() {
/* 296 */     return this.theWorldSettings.getHardcoreEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldBroadcastRconToOps() {
/* 304 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldBroadcastConsoleToOps() {
/* 312 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAllWorlds(boolean isSilent) {
/* 320 */     super.saveAllWorlds(isSilent);
/*     */   }
/*     */ 
/*     */   
/*     */   public File getDataDirectory() {
/* 325 */     return this.mc.mcDataDir;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDedicatedServer() {
/* 330 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldUseNativeTransport() {
/* 339 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void finalTick(CrashReport report) {
/* 347 */     this.mc.crashed(report);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CrashReport addServerInfoToCrashReport(CrashReport report) {
/* 355 */     report = super.addServerInfoToCrashReport(report);
/* 356 */     report.getCategory().setDetail("Type", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 360 */             return "Integrated Server (map_client.txt)";
/*     */           }
/*     */         });
/* 363 */     report.getCategory().setDetail("Is Modded", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 367 */             String s = ClientBrandRetriever.getClientModName();
/*     */             
/* 369 */             if (!s.equals("vanilla"))
/*     */             {
/* 371 */               return "Definitely; Client brand changed to '" + s + "'";
/*     */             }
/*     */ 
/*     */             
/* 375 */             s = IntegratedServer.this.getServerModName();
/*     */             
/* 377 */             if (!"vanilla".equals(s))
/*     */             {
/* 379 */               return "Definitely; Server brand changed to '" + s + "'";
/*     */             }
/*     */ 
/*     */             
/* 383 */             return (Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and both client + server brands are untouched.";
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 388 */     return report;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDifficultyForAllWorlds(EnumDifficulty difficulty) {
/* 393 */     super.setDifficultyForAllWorlds(difficulty);
/*     */     
/* 395 */     if (this.mc.world != null)
/*     */     {
/* 397 */       this.mc.world.getWorldInfo().setDifficulty(difficulty);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void addServerStatsToSnooper(Snooper playerSnooper) {
/* 403 */     super.addServerStatsToSnooper(playerSnooper);
/* 404 */     playerSnooper.addClientStat("snooper_partner", this.mc.getPlayerUsageSnooper().getUniqueID());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSnooperEnabled() {
/* 412 */     return Minecraft.getMinecraft().isSnooperEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String shareToLAN(GameType type, boolean allowCheats) {
/*     */     try {
/* 422 */       int i = -1;
/*     */ 
/*     */       
/*     */       try {
/* 426 */         i = HttpUtil.getSuitableLanPort();
/*     */       }
/* 428 */       catch (IOException iOException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 433 */       if (i <= 0)
/*     */       {
/* 435 */         i = 25564;
/*     */       }
/*     */       
/* 438 */       getNetworkSystem().addLanEndpoint(null, i);
/* 439 */       LOGGER.info("Started on {}", Integer.valueOf(i));
/* 440 */       this.isPublic = true;
/* 441 */       this.lanServerPing = new ThreadLanServerPing(getMOTD(), (new StringBuilder(String.valueOf(i))).toString());
/* 442 */       this.lanServerPing.start();
/* 443 */       getPlayerList().setGameType(type);
/* 444 */       getPlayerList().setCommandsAllowedForAll(allowCheats);
/* 445 */       this.mc.player.setPermissionLevel(allowCheats ? 4 : 0);
/* 446 */       return (new StringBuilder(String.valueOf(i))).toString();
/*     */     }
/* 448 */     catch (IOException var61) {
/*     */       
/* 450 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopServer() {
/* 459 */     super.stopServer();
/*     */     
/* 461 */     if (this.lanServerPing != null) {
/*     */       
/* 463 */       this.lanServerPing.interrupt();
/* 464 */       this.lanServerPing = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initiateShutdown() {
/* 473 */     if (!Reflector.MinecraftForge.exists() || isServerRunning())
/*     */     {
/* 475 */       Futures.getUnchecked((Future)addScheduledTask(new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/* 479 */                 for (EntityPlayerMP entityplayermp : Lists.newArrayList(IntegratedServer.this.getPlayerList().getPlayerList())) {
/*     */                   
/* 481 */                   if (!entityplayermp.getUniqueID().equals(IntegratedServer.this.mc.player.getUniqueID()))
/*     */                   {
/* 483 */                     IntegratedServer.this.getPlayerList().playerLoggedOut(entityplayermp);
/*     */                   }
/*     */                 } 
/*     */               }
/*     */             }));
/*     */     }
/*     */     
/* 490 */     super.initiateShutdown();
/*     */     
/* 492 */     if (this.lanServerPing != null) {
/*     */       
/* 494 */       this.lanServerPing.interrupt();
/* 495 */       this.lanServerPing = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getPublic() {
/* 504 */     return this.isPublic;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setGameType(GameType gameMode) {
/* 512 */     super.setGameType(gameMode);
/* 513 */     getPlayerList().setGameType(gameMode);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCommandBlockEnabled() {
/* 521 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOpPermissionLevel() {
/* 526 */     return 4;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\integrated\IntegratedServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */