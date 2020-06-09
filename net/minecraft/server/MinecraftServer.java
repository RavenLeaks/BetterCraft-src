/*      */ package net.minecraft.server;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Queues;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import com.google.common.util.concurrent.ListenableFutureTask;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.GameProfileRepository;
/*      */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*      */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufOutputStream;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.handler.codec.base64.Base64;
/*      */ import java.awt.GraphicsEnvironment;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.net.Proxy;
/*      */ import java.net.URLEncoder;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.security.KeyPair;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Queue;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import javax.annotation.Nullable;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.advancements.AdvancementManager;
/*      */ import net.minecraft.advancements.FunctionManager;
/*      */ import net.minecraft.command.CommandBase;
/*      */ import net.minecraft.command.ICommandManager;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.command.ServerCommandManager;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.ICrashReportDetail;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.network.NetworkSystem;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.ServerStatusResponse;
/*      */ import net.minecraft.network.play.server.SPacketTimeUpdate;
/*      */ import net.minecraft.profiler.ISnooperInfo;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.profiler.Snooper;
/*      */ import net.minecraft.server.management.PlayerList;
/*      */ import net.minecraft.server.management.PlayerProfileCache;
/*      */ import net.minecraft.util.IProgressUpdate;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.ITickable;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.Util;
/*      */ import net.minecraft.util.datafix.DataFixer;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.TextComponentString;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.GameType;
/*      */ import net.minecraft.world.IWorldEventListener;
/*      */ import net.minecraft.world.MinecraftException;
/*      */ import net.minecraft.world.ServerWorldEventHandler;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraft.world.WorldServerDemo;
/*      */ import net.minecraft.world.WorldServerMulti;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.WorldType;
/*      */ import net.minecraft.world.chunk.storage.AnvilSaveConverter;
/*      */ import net.minecraft.world.storage.ISaveFormat;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public abstract class MinecraftServer implements ICommandSender, Runnable, IThreadListener, ISnooperInfo {
/*   88 */   private static final Logger LOG = LogManager.getLogger();
/*   89 */   public static final File USER_CACHE_FILE = new File("usercache.json");
/*      */   
/*      */   private final ISaveFormat anvilConverterForAnvilFile;
/*      */   
/*   93 */   private final Snooper usageSnooper = new Snooper("server", this, getCurrentTimeMillis());
/*      */   private final File anvilFile;
/*   95 */   private final List<ITickable> tickables = Lists.newArrayList();
/*      */   public final ICommandManager commandManager;
/*   97 */   public final Profiler theProfiler = new Profiler();
/*      */   private final NetworkSystem networkSystem;
/*   99 */   private final ServerStatusResponse statusResponse = new ServerStatusResponse();
/*  100 */   private final Random random = new Random();
/*      */   
/*      */   private final DataFixer dataFixer;
/*      */   
/*  104 */   private int serverPort = -1;
/*      */ 
/*      */   
/*      */   public WorldServer[] worldServers;
/*      */ 
/*      */   
/*      */   private PlayerList playerList;
/*      */ 
/*      */   
/*      */   private boolean serverRunning = true;
/*      */ 
/*      */   
/*      */   private boolean serverStopped;
/*      */ 
/*      */   
/*      */   private int tickCounter;
/*      */ 
/*      */   
/*      */   protected final Proxy serverProxy;
/*      */ 
/*      */   
/*      */   public String currentTask;
/*      */ 
/*      */   
/*      */   public int percentDone;
/*      */ 
/*      */   
/*      */   private boolean onlineMode;
/*      */ 
/*      */   
/*      */   private boolean field_190519_A;
/*      */ 
/*      */   
/*      */   private boolean canSpawnAnimals;
/*      */ 
/*      */   
/*      */   private boolean canSpawnNPCs;
/*      */   
/*      */   private boolean pvpEnabled;
/*      */   
/*      */   private boolean allowFlight;
/*      */   
/*      */   private String motd;
/*      */   
/*      */   private int buildLimit;
/*      */   
/*      */   private int maxPlayerIdleMinutes;
/*      */   
/*  152 */   public final long[] tickTimeArray = new long[100];
/*      */   
/*      */   public long[][] timeOfLastDimensionTick;
/*      */   
/*      */   private KeyPair serverKeyPair;
/*      */   
/*      */   private String serverOwner;
/*      */   
/*      */   private String folderName;
/*      */   
/*      */   private String worldName;
/*      */   
/*      */   private boolean isDemo;
/*      */   private boolean enableBonusChest;
/*  166 */   private String resourcePackUrl = "";
/*  167 */   private String resourcePackHash = "";
/*      */   
/*      */   private boolean serverIsRunning;
/*      */   
/*      */   private long timeOfLastWarning;
/*      */   
/*      */   private String userMessage;
/*      */   
/*      */   private boolean startProfiling;
/*      */   private boolean isGamemodeForced;
/*      */   private final YggdrasilAuthenticationService authService;
/*      */   private final MinecraftSessionService sessionService;
/*      */   private final GameProfileRepository profileRepo;
/*      */   private final PlayerProfileCache profileCache;
/*      */   private long nanoTimeSinceStatusRefresh;
/*  182 */   public final Queue<FutureTask<?>> futureTaskQueue = Queues.newArrayDeque();
/*      */   private Thread serverThread;
/*  184 */   private long currentTime = getCurrentTimeMillis();
/*      */   
/*      */   private boolean worldIconSet;
/*      */   
/*      */   public MinecraftServer(File anvilFileIn, Proxy proxyIn, DataFixer dataFixerIn, YggdrasilAuthenticationService authServiceIn, MinecraftSessionService sessionServiceIn, GameProfileRepository profileRepoIn, PlayerProfileCache profileCacheIn) {
/*  189 */     this.serverProxy = proxyIn;
/*  190 */     this.authService = authServiceIn;
/*  191 */     this.sessionService = sessionServiceIn;
/*  192 */     this.profileRepo = profileRepoIn;
/*  193 */     this.profileCache = profileCacheIn;
/*  194 */     this.anvilFile = anvilFileIn;
/*  195 */     this.networkSystem = new NetworkSystem(this);
/*  196 */     this.commandManager = (ICommandManager)createNewCommandManager();
/*  197 */     this.anvilConverterForAnvilFile = (ISaveFormat)new AnvilSaveConverter(anvilFileIn, dataFixerIn);
/*  198 */     this.dataFixer = dataFixerIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public ServerCommandManager createNewCommandManager() {
/*  203 */     return new ServerCommandManager(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void convertMapIfNeeded(String worldNameIn) {
/*  213 */     if (getActiveAnvilConverter().isOldMapFormat(worldNameIn)) {
/*      */       
/*  215 */       LOG.info("Converting map!");
/*  216 */       setUserMessage("menu.convertingLevel");
/*  217 */       getActiveAnvilConverter().convertMapFormat(worldNameIn, new IProgressUpdate()
/*      */           {
/*  219 */             private long startTime = System.currentTimeMillis();
/*      */ 
/*      */             
/*      */             public void displaySavingString(String message) {}
/*      */ 
/*      */             
/*      */             public void resetProgressAndMessage(String message) {}
/*      */             
/*      */             public void setLoadingProgress(int progress) {
/*  228 */               if (System.currentTimeMillis() - this.startTime >= 1000L) {
/*      */                 
/*  230 */                 this.startTime = System.currentTimeMillis();
/*  231 */                 MinecraftServer.LOG.info("Converting... {}%", Integer.valueOf(progress));
/*      */               } 
/*      */             }
/*      */ 
/*      */ 
/*      */             
/*      */             public void setDoneWorking() {}
/*      */ 
/*      */ 
/*      */             
/*      */             public void displayLoadingString(String message) {}
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected synchronized void setUserMessage(String message) {
/*  249 */     this.userMessage = message;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public synchronized String getUserMessage() {
/*  256 */     return this.userMessage;
/*      */   }
/*      */   
/*      */   public void loadAllWorlds(String saveName, String worldNameIn, long seed, WorldType type, String generatorOptions) {
/*      */     WorldSettings worldsettings;
/*  261 */     convertMapIfNeeded(saveName);
/*  262 */     setUserMessage("menu.loadingLevel");
/*  263 */     this.worldServers = new WorldServer[3];
/*  264 */     this.timeOfLastDimensionTick = new long[this.worldServers.length][100];
/*  265 */     ISaveHandler isavehandler = this.anvilConverterForAnvilFile.getSaveLoader(saveName, true);
/*  266 */     setResourcePackFromWorld(getFolderName(), isavehandler);
/*  267 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*      */ 
/*      */     
/*  270 */     if (worldinfo == null) {
/*      */       
/*  272 */       if (isDemo()) {
/*      */         
/*  274 */         worldsettings = WorldServerDemo.DEMO_WORLD_SETTINGS;
/*      */       }
/*      */       else {
/*      */         
/*  278 */         worldsettings = new WorldSettings(seed, getGameType(), canStructuresSpawn(), isHardcore(), type);
/*  279 */         worldsettings.setGeneratorOptions(generatorOptions);
/*      */         
/*  281 */         if (this.enableBonusChest)
/*      */         {
/*  283 */           worldsettings.enableBonusChest();
/*      */         }
/*      */       } 
/*      */       
/*  287 */       worldinfo = new WorldInfo(worldsettings, worldNameIn);
/*      */     }
/*      */     else {
/*      */       
/*  291 */       worldinfo.setWorldName(worldNameIn);
/*  292 */       worldsettings = new WorldSettings(worldinfo);
/*      */     } 
/*      */     
/*  295 */     for (int i = 0; i < this.worldServers.length; i++) {
/*      */       
/*  297 */       int j = 0;
/*      */       
/*  299 */       if (i == 1)
/*      */       {
/*  301 */         j = -1;
/*      */       }
/*      */       
/*  304 */       if (i == 2)
/*      */       {
/*  306 */         j = 1;
/*      */       }
/*      */       
/*  309 */       if (i == 0) {
/*      */         
/*  311 */         if (isDemo()) {
/*      */           
/*  313 */           this.worldServers[i] = (WorldServer)(new WorldServerDemo(this, isavehandler, worldinfo, j, this.theProfiler)).init();
/*      */         }
/*      */         else {
/*      */           
/*  317 */           this.worldServers[i] = (WorldServer)(new WorldServer(this, isavehandler, worldinfo, j, this.theProfiler)).init();
/*      */         } 
/*      */         
/*  320 */         this.worldServers[i].initialize(worldsettings);
/*      */       }
/*      */       else {
/*      */         
/*  324 */         this.worldServers[i] = (WorldServer)(new WorldServerMulti(this, isavehandler, j, this.worldServers[0], this.theProfiler)).init();
/*      */       } 
/*      */       
/*  327 */       this.worldServers[i].addEventListener((IWorldEventListener)new ServerWorldEventHandler(this, this.worldServers[i]));
/*      */       
/*  329 */       if (!isSinglePlayer())
/*      */       {
/*  331 */         this.worldServers[i].getWorldInfo().setGameType(getGameType());
/*      */       }
/*      */     } 
/*      */     
/*  335 */     this.playerList.setPlayerManager(this.worldServers);
/*  336 */     setDifficultyForAllWorlds(getDifficulty());
/*  337 */     initialWorldChunkLoad();
/*      */   }
/*      */ 
/*      */   
/*      */   public void initialWorldChunkLoad() {
/*  342 */     int i = 16;
/*  343 */     int j = 4;
/*  344 */     int k = 192;
/*  345 */     int l = 625;
/*  346 */     int i1 = 0;
/*  347 */     setUserMessage("menu.generatingTerrain");
/*  348 */     int j1 = 0;
/*  349 */     LOG.info("Preparing start region for level 0");
/*  350 */     WorldServer worldserver = this.worldServers[0];
/*  351 */     BlockPos blockpos = worldserver.getSpawnPoint();
/*  352 */     long k1 = getCurrentTimeMillis();
/*      */     
/*  354 */     for (int l1 = -192; l1 <= 192 && isServerRunning(); l1 += 16) {
/*      */       
/*  356 */       for (int i2 = -192; i2 <= 192 && isServerRunning(); i2 += 16) {
/*      */         
/*  358 */         long j2 = getCurrentTimeMillis();
/*      */         
/*  360 */         if (j2 - k1 > 1000L) {
/*      */           
/*  362 */           outputPercentRemaining("Preparing spawn area", i1 * 100 / 625);
/*  363 */           k1 = j2;
/*      */         } 
/*      */         
/*  366 */         i1++;
/*  367 */         worldserver.getChunkProvider().provideChunk(blockpos.getX() + l1 >> 4, blockpos.getZ() + i2 >> 4);
/*      */       } 
/*      */     } 
/*      */     
/*  371 */     clearCurrentTask();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setResourcePackFromWorld(String worldNameIn, ISaveHandler saveHandlerIn) {
/*  376 */     File file1 = new File(saveHandlerIn.getWorldDirectory(), "resources.zip");
/*      */     
/*  378 */     if (file1.isFile()) {
/*      */       
/*      */       try {
/*      */         
/*  382 */         setResourcePack("level://" + URLEncoder.encode(worldNameIn, StandardCharsets.UTF_8.toString()) + "/" + "resources.zip", "");
/*      */       }
/*  384 */       catch (UnsupportedEncodingException var5) {
/*      */         
/*  386 */         LOG.warn("Something went wrong url encoding {}", worldNameIn);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void outputPercentRemaining(String message, int percent) {
/*  422 */     this.currentTask = message;
/*  423 */     this.percentDone = percent;
/*  424 */     LOG.info("{}: {}%", message, Integer.valueOf(percent));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void clearCurrentTask() {
/*  432 */     this.currentTask = null;
/*  433 */     this.percentDone = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public void saveAllWorlds(boolean isSilent) {
/*      */     byte b;
/*      */     int i;
/*      */     WorldServer[] arrayOfWorldServer;
/*  441 */     for (i = (arrayOfWorldServer = this.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/*      */       
/*  443 */       if (worldserver != null) {
/*      */         
/*  445 */         if (!isSilent)
/*      */         {
/*  447 */           LOG.info("Saving chunks for level '{}'/{}", worldserver.getWorldInfo().getWorldName(), worldserver.provider.getDimensionType().getName());
/*      */         }
/*      */ 
/*      */         
/*      */         try {
/*  452 */           worldserver.saveAllChunks(true, null);
/*      */         }
/*  454 */         catch (MinecraftException minecraftexception) {
/*      */           
/*  456 */           LOG.warn(minecraftexception.getMessage());
/*      */         } 
/*      */       } 
/*      */       b++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void stopServer() {
/*  467 */     LOG.info("Stopping server");
/*      */     
/*  469 */     if (getNetworkSystem() != null)
/*      */     {
/*  471 */       getNetworkSystem().terminateEndpoints();
/*      */     }
/*      */     
/*  474 */     if (this.playerList != null) {
/*      */       
/*  476 */       LOG.info("Saving players");
/*  477 */       this.playerList.saveAllPlayerData();
/*  478 */       this.playerList.removeAllPlayers();
/*      */     } 
/*      */     
/*  481 */     if (this.worldServers != null) {
/*      */       
/*  483 */       LOG.info("Saving worlds"); byte b; int i;
/*      */       WorldServer[] arrayOfWorldServer;
/*  485 */       for (i = (arrayOfWorldServer = this.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/*      */         
/*  487 */         if (worldserver != null)
/*      */         {
/*  489 */           worldserver.disableLevelSaving = false;
/*      */         }
/*      */         b++; }
/*      */       
/*  493 */       saveAllWorlds(false);
/*      */       
/*  495 */       for (i = (arrayOfWorldServer = this.worldServers).length, b = 0; b < i; ) { WorldServer worldserver1 = arrayOfWorldServer[b];
/*      */         
/*  497 */         if (worldserver1 != null)
/*      */         {
/*  499 */           worldserver1.flush();
/*      */         }
/*      */         b++; }
/*      */     
/*      */     } 
/*  504 */     if (this.usageSnooper.isSnooperRunning())
/*      */     {
/*  506 */       this.usageSnooper.stopSnooper();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isServerRunning() {
/*  512 */     return this.serverRunning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initiateShutdown() {
/*  520 */     this.serverRunning = false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void run() {
/*      */     try {
/*  527 */       if (startServer()) {
/*      */         
/*  529 */         this.currentTime = getCurrentTimeMillis();
/*  530 */         long i = 0L;
/*  531 */         this.statusResponse.setServerDescription((ITextComponent)new TextComponentString(this.motd));
/*  532 */         this.statusResponse.setVersion(new ServerStatusResponse.Version("1.12.2", 340));
/*  533 */         applyServerIconToResponse(this.statusResponse);
/*      */         
/*  535 */         while (this.serverRunning)
/*      */         {
/*  537 */           long k = getCurrentTimeMillis();
/*  538 */           long j = k - this.currentTime;
/*      */           
/*  540 */           if (j > 2000L && this.currentTime - this.timeOfLastWarning >= 15000L) {
/*      */             
/*  542 */             LOG.warn("Can't keep up! Did the system time change, or is the server overloaded? Running {}ms behind, skipping {} tick(s)", Long.valueOf(j), Long.valueOf(j / 50L));
/*  543 */             j = 2000L;
/*  544 */             this.timeOfLastWarning = this.currentTime;
/*      */           } 
/*      */           
/*  547 */           if (j < 0L) {
/*      */             
/*  549 */             LOG.warn("Time ran backwards! Did the system time change?");
/*  550 */             j = 0L;
/*      */           } 
/*      */           
/*  553 */           i += j;
/*  554 */           this.currentTime = k;
/*      */           
/*  556 */           if (this.worldServers[0].areAllPlayersAsleep()) {
/*      */             
/*  558 */             tick();
/*  559 */             i = 0L;
/*      */           }
/*      */           else {
/*      */             
/*  563 */             while (i > 50L) {
/*      */               
/*  565 */               i -= 50L;
/*  566 */               tick();
/*      */             } 
/*      */           } 
/*      */           
/*  570 */           Thread.sleep(Math.max(1L, 50L - i));
/*  571 */           this.serverIsRunning = true;
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/*  576 */         finalTick(null);
/*      */       }
/*      */     
/*  579 */     } catch (Throwable throwable1) {
/*      */       
/*  581 */       LOG.error("Encountered an unexpected exception", throwable1);
/*  582 */       CrashReport crashreport = null;
/*      */       
/*  584 */       if (throwable1 instanceof ReportedException) {
/*      */         
/*  586 */         crashreport = addServerInfoToCrashReport(((ReportedException)throwable1).getCrashReport());
/*      */       }
/*      */       else {
/*      */         
/*  590 */         crashreport = addServerInfoToCrashReport(new CrashReport("Exception in server tick loop", throwable1));
/*      */       } 
/*      */       
/*  593 */       File file1 = new File(new File(getDataDirectory(), "crash-reports"), "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-server.txt");
/*      */       
/*  595 */       if (crashreport.saveToFile(file1)) {
/*      */         
/*  597 */         LOG.error("This crash report has been saved to: {}", file1.getAbsolutePath());
/*      */       }
/*      */       else {
/*      */         
/*  601 */         LOG.error("We were unable to save this crash report to disk.");
/*      */       } 
/*      */       
/*  604 */       finalTick(crashreport);
/*      */     } finally {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  610 */         this.serverStopped = true;
/*  611 */         stopServer();
/*      */       }
/*  613 */       catch (Throwable throwable) {
/*      */         
/*  615 */         LOG.error("Exception stopping the server", throwable);
/*      */       }
/*      */       finally {
/*      */         
/*  619 */         systemExitNow();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void applyServerIconToResponse(ServerStatusResponse response) {
/*  626 */     File file1 = getFile("server-icon.png");
/*      */     
/*  628 */     if (!file1.exists())
/*      */     {
/*  630 */       file1 = getActiveAnvilConverter().getFile(getFolderName(), "icon.png");
/*      */     }
/*      */     
/*  633 */     if (file1.isFile()) {
/*      */       
/*  635 */       ByteBuf bytebuf = Unpooled.buffer();
/*      */ 
/*      */       
/*      */       try {
/*  639 */         BufferedImage bufferedimage = ImageIO.read(file1);
/*  640 */         Validate.validState((bufferedimage.getWidth() == 64), "Must be 64 pixels wide", new Object[0]);
/*  641 */         Validate.validState((bufferedimage.getHeight() == 64), "Must be 64 pixels high", new Object[0]);
/*  642 */         ImageIO.write(bufferedimage, "PNG", (OutputStream)new ByteBufOutputStream(bytebuf));
/*  643 */         ByteBuf bytebuf1 = Base64.encode(bytebuf);
/*  644 */         response.setFavicon("data:image/png;base64," + bytebuf1.toString(StandardCharsets.UTF_8));
/*      */       }
/*  646 */       catch (Exception exception) {
/*      */         
/*  648 */         LOG.error("Couldn't load server icon", exception);
/*      */       }
/*      */       finally {
/*      */         
/*  652 */         bytebuf.release();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWorldIconSet() {
/*  659 */     this.worldIconSet = !(!this.worldIconSet && !getWorldIconFile().isFile());
/*  660 */     return this.worldIconSet;
/*      */   }
/*      */ 
/*      */   
/*      */   public File getWorldIconFile() {
/*  665 */     return getActiveAnvilConverter().getFile(getFolderName(), "icon.png");
/*      */   }
/*      */ 
/*      */   
/*      */   public File getDataDirectory() {
/*  670 */     return new File(".");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void finalTick(CrashReport report) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void systemExitNow() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick() {
/*  692 */     long i = System.nanoTime();
/*  693 */     this.tickCounter++;
/*      */     
/*  695 */     if (this.startProfiling) {
/*      */       
/*  697 */       this.startProfiling = false;
/*  698 */       this.theProfiler.profilingEnabled = true;
/*  699 */       this.theProfiler.clearProfiling();
/*      */     } 
/*      */     
/*  702 */     this.theProfiler.startSection("root");
/*  703 */     updateTimeLightAndEntities();
/*      */     
/*  705 */     if (i - this.nanoTimeSinceStatusRefresh >= 5000000000L) {
/*      */       
/*  707 */       this.nanoTimeSinceStatusRefresh = i;
/*  708 */       this.statusResponse.setPlayers(new ServerStatusResponse.Players(getMaxPlayers(), getCurrentPlayerCount()));
/*  709 */       GameProfile[] agameprofile = new GameProfile[Math.min(getCurrentPlayerCount(), 12)];
/*  710 */       int j = MathHelper.getInt(this.random, 0, getCurrentPlayerCount() - agameprofile.length);
/*      */       
/*  712 */       for (int k = 0; k < agameprofile.length; k++)
/*      */       {
/*  714 */         agameprofile[k] = ((EntityPlayerMP)this.playerList.getPlayerList().get(j + k)).getGameProfile();
/*      */       }
/*      */       
/*  717 */       Collections.shuffle(Arrays.asList((Object[])agameprofile));
/*  718 */       this.statusResponse.getPlayers().setPlayers(agameprofile);
/*      */     } 
/*      */     
/*  721 */     if (this.tickCounter % 900 == 0) {
/*      */       
/*  723 */       this.theProfiler.startSection("save");
/*  724 */       this.playerList.saveAllPlayerData();
/*  725 */       saveAllWorlds(true);
/*  726 */       this.theProfiler.endSection();
/*      */     } 
/*      */     
/*  729 */     this.theProfiler.startSection("tallying");
/*  730 */     this.tickTimeArray[this.tickCounter % 100] = System.nanoTime() - i;
/*  731 */     this.theProfiler.endSection();
/*  732 */     this.theProfiler.startSection("snooper");
/*      */     
/*  734 */     if (!this.usageSnooper.isSnooperRunning() && this.tickCounter > 100)
/*      */     {
/*  736 */       this.usageSnooper.startSnooper();
/*      */     }
/*      */     
/*  739 */     if (this.tickCounter % 6000 == 0)
/*      */     {
/*  741 */       this.usageSnooper.addMemoryStatsToSnooper();
/*      */     }
/*      */     
/*  744 */     this.theProfiler.endSection();
/*  745 */     this.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateTimeLightAndEntities() {
/*  750 */     this.theProfiler.startSection("jobs");
/*      */     
/*  752 */     synchronized (this.futureTaskQueue) {
/*      */       
/*  754 */       while (!this.futureTaskQueue.isEmpty())
/*      */       {
/*  756 */         Util.runTask(this.futureTaskQueue.poll(), LOG);
/*      */       }
/*      */     } 
/*      */     
/*  760 */     this.theProfiler.endStartSection("levels");
/*      */     
/*  762 */     for (int j = 0; j < this.worldServers.length; j++) {
/*      */       
/*  764 */       long i = System.nanoTime();
/*      */       
/*  766 */       if (j == 0 || getAllowNether()) {
/*      */         
/*  768 */         WorldServer worldserver = this.worldServers[j];
/*  769 */         this.theProfiler.func_194340_a(() -> paramWorldServer.getWorldInfo().getWorldName());
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  774 */         if (this.tickCounter % 20 == 0) {
/*      */           
/*  776 */           this.theProfiler.startSection("timeSync");
/*  777 */           this.playerList.sendPacketToAllPlayersInDimension((Packet)new SPacketTimeUpdate(worldserver.getTotalWorldTime(), worldserver.getWorldTime(), worldserver.getGameRules().getBoolean("doDaylightCycle")), worldserver.provider.getDimensionType().getId());
/*  778 */           this.theProfiler.endSection();
/*      */         } 
/*      */         
/*  781 */         this.theProfiler.startSection("tick");
/*      */ 
/*      */         
/*      */         try {
/*  785 */           worldserver.tick();
/*      */         }
/*  787 */         catch (Throwable throwable1) {
/*      */           
/*  789 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Exception ticking world");
/*  790 */           worldserver.addWorldInfoToCrashReport(crashreport);
/*  791 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */ 
/*      */         
/*      */         try {
/*  796 */           worldserver.updateEntities();
/*      */         }
/*  798 */         catch (Throwable throwable) {
/*      */           
/*  800 */           CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Exception ticking world entities");
/*  801 */           worldserver.addWorldInfoToCrashReport(crashreport1);
/*  802 */           throw new ReportedException(crashreport1);
/*      */         } 
/*      */         
/*  805 */         this.theProfiler.endSection();
/*  806 */         this.theProfiler.startSection("tracker");
/*  807 */         worldserver.getEntityTracker().updateTrackedEntities();
/*  808 */         this.theProfiler.endSection();
/*  809 */         this.theProfiler.endSection();
/*      */       } 
/*      */       
/*  812 */       this.timeOfLastDimensionTick[j][this.tickCounter % 100] = System.nanoTime() - i;
/*      */     } 
/*      */     
/*  815 */     this.theProfiler.endStartSection("connection");
/*  816 */     getNetworkSystem().networkTick();
/*  817 */     this.theProfiler.endStartSection("players");
/*  818 */     this.playerList.onTick();
/*  819 */     this.theProfiler.endStartSection("commandFunctions");
/*  820 */     func_193030_aL().update();
/*  821 */     this.theProfiler.endStartSection("tickables");
/*      */     
/*  823 */     for (int k = 0; k < this.tickables.size(); k++)
/*      */     {
/*  825 */       ((ITickable)this.tickables.get(k)).update();
/*      */     }
/*      */     
/*  828 */     this.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getAllowNether() {
/*  833 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void startServerThread() {
/*  838 */     this.serverThread = new Thread(this, "Server thread");
/*  839 */     this.serverThread.start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public File getFile(String fileName) {
/*  847 */     return new File(getDataDirectory(), fileName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void logWarning(String msg) {
/*  855 */     LOG.warn(msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldServer worldServerForDimension(int dimension) {
/*  863 */     if (dimension == -1)
/*      */     {
/*  865 */       return this.worldServers[1];
/*      */     }
/*      */ 
/*      */     
/*  869 */     return (dimension == 1) ? this.worldServers[2] : this.worldServers[0];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMinecraftVersion() {
/*  878 */     return "1.12.2";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCurrentPlayerCount() {
/*  886 */     return this.playerList.getCurrentPlayerCount();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxPlayers() {
/*  894 */     return this.playerList.getMaxPlayers();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getAllUsernames() {
/*  902 */     return this.playerList.getAllUsernames();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameProfile[] getGameProfiles() {
/*  910 */     return this.playerList.getAllProfiles();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getServerModName() {
/*  915 */     return "vanilla";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CrashReport addServerInfoToCrashReport(CrashReport report) {
/*  923 */     report.getCategory().setDetail("Profiler Position", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/*  927 */             return MinecraftServer.this.theProfiler.profilingEnabled ? MinecraftServer.this.theProfiler.getNameOfLastSection() : "N/A (disabled)";
/*      */           }
/*      */         });
/*      */     
/*  931 */     if (this.playerList != null)
/*      */     {
/*  933 */       report.getCategory().setDetail("Player Count", new ICrashReportDetail<String>()
/*      */           {
/*      */             public String call()
/*      */             {
/*  937 */               return String.valueOf(MinecraftServer.this.playerList.getCurrentPlayerCount()) + " / " + MinecraftServer.this.playerList.getMaxPlayers() + "; " + MinecraftServer.this.playerList.getPlayerList();
/*      */             }
/*      */           });
/*      */     }
/*      */     
/*  942 */     return report;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> getTabCompletions(ICommandSender sender, String input, @Nullable BlockPos pos, boolean hasTargetBlock) {
/*  947 */     List<String> list = Lists.newArrayList();
/*  948 */     boolean flag = input.startsWith("/");
/*      */     
/*  950 */     if (flag)
/*      */     {
/*  952 */       input = input.substring(1);
/*      */     }
/*      */     
/*  955 */     if (!flag && !hasTargetBlock) {
/*      */       
/*  957 */       String[] astring = input.split(" ", -1);
/*  958 */       String s2 = astring[astring.length - 1]; byte b; int i;
/*      */       String[] arrayOfString1;
/*  960 */       for (i = (arrayOfString1 = this.playerList.getAllUsernames()).length, b = 0; b < i; ) { String s1 = arrayOfString1[b];
/*      */         
/*  962 */         if (CommandBase.doesStringStartWith(s2, s1))
/*      */         {
/*  964 */           list.add(s1);
/*      */         }
/*      */         b++; }
/*      */       
/*  968 */       return list;
/*      */     } 
/*      */ 
/*      */     
/*  972 */     boolean flag1 = !input.contains(" ");
/*  973 */     List<String> list1 = this.commandManager.getTabCompletionOptions(sender, input, pos);
/*      */     
/*  975 */     if (!list1.isEmpty())
/*      */     {
/*  977 */       for (String s : list1) {
/*      */         
/*  979 */         if (flag1 && !hasTargetBlock) {
/*      */           
/*  981 */           list.add("/" + s);
/*      */           
/*      */           continue;
/*      */         } 
/*  985 */         list.add(s);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*  990 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAnvilFileSet() {
/*  996 */     return (this.anvilFile != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getName() {
/* 1004 */     return "Server";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addChatMessage(ITextComponent component) {
/* 1012 */     LOG.info(component.getUnformattedText());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 1020 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public ICommandManager getCommandManager() {
/* 1025 */     return this.commandManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public KeyPair getKeyPair() {
/* 1033 */     return this.serverKeyPair;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getServerOwner() {
/* 1041 */     return this.serverOwner;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerOwner(String owner) {
/* 1049 */     this.serverOwner = owner;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isSinglePlayer() {
/* 1054 */     return (this.serverOwner != null);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getFolderName() {
/* 1059 */     return this.folderName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setFolderName(String name) {
/* 1064 */     this.folderName = name;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setWorldName(String worldNameIn) {
/* 1069 */     this.worldName = worldNameIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getWorldName() {
/* 1074 */     return this.worldName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setKeyPair(KeyPair keyPair) {
/* 1079 */     this.serverKeyPair = keyPair;
/*      */   } public void setDifficultyForAllWorlds(EnumDifficulty difficulty) {
/*      */     byte b;
/*      */     int i;
/*      */     WorldServer[] arrayOfWorldServer;
/* 1084 */     for (i = (arrayOfWorldServer = this.worldServers).length, b = 0; b < i; ) { WorldServer worldserver1 = arrayOfWorldServer[b];
/*      */       
/* 1086 */       if (worldserver1 != null)
/*      */       {
/* 1088 */         if (worldserver1.getWorldInfo().isHardcoreModeEnabled()) {
/*      */           
/* 1090 */           worldserver1.getWorldInfo().setDifficulty(EnumDifficulty.HARD);
/* 1091 */           worldserver1.setAllowedSpawnTypes(true, true);
/*      */         }
/* 1093 */         else if (isSinglePlayer()) {
/*      */           
/* 1095 */           worldserver1.getWorldInfo().setDifficulty(difficulty);
/* 1096 */           worldserver1.setAllowedSpawnTypes((worldserver1.getDifficulty() != EnumDifficulty.PEACEFUL), true);
/*      */         }
/*      */         else {
/*      */           
/* 1100 */           worldserver1.getWorldInfo().setDifficulty(difficulty);
/* 1101 */           worldserver1.setAllowedSpawnTypes(allowSpawnMonsters(), this.canSpawnAnimals);
/*      */         } 
/*      */       }
/*      */       b++; }
/*      */   
/*      */   }
/*      */   
/*      */   public boolean allowSpawnMonsters() {
/* 1109 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDemo() {
/* 1117 */     return this.isDemo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDemo(boolean demo) {
/* 1125 */     this.isDemo = demo;
/*      */   }
/*      */ 
/*      */   
/*      */   public void canCreateBonusChest(boolean enable) {
/* 1130 */     this.enableBonusChest = enable;
/*      */   }
/*      */ 
/*      */   
/*      */   public ISaveFormat getActiveAnvilConverter() {
/* 1135 */     return this.anvilConverterForAnvilFile;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getResourcePackUrl() {
/* 1140 */     return this.resourcePackUrl;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getResourcePackHash() {
/* 1145 */     return this.resourcePackHash;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setResourcePack(String url, String hash) {
/* 1150 */     this.resourcePackUrl = url;
/* 1151 */     this.resourcePackHash = hash;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerStatsToSnooper(Snooper playerSnooper) {
/* 1156 */     playerSnooper.addClientStat("whitelist_enabled", Boolean.valueOf(false));
/* 1157 */     playerSnooper.addClientStat("whitelist_count", Integer.valueOf(0));
/*      */     
/* 1159 */     if (this.playerList != null) {
/*      */       
/* 1161 */       playerSnooper.addClientStat("players_current", Integer.valueOf(getCurrentPlayerCount()));
/* 1162 */       playerSnooper.addClientStat("players_max", Integer.valueOf(getMaxPlayers()));
/* 1163 */       playerSnooper.addClientStat("players_seen", Integer.valueOf((this.playerList.getAvailablePlayerDat()).length));
/*      */     } 
/*      */     
/* 1166 */     playerSnooper.addClientStat("uses_auth", Boolean.valueOf(this.onlineMode));
/* 1167 */     playerSnooper.addClientStat("gui_state", getGuiEnabled() ? "enabled" : "disabled");
/* 1168 */     playerSnooper.addClientStat("run_time", Long.valueOf((getCurrentTimeMillis() - playerSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L));
/* 1169 */     playerSnooper.addClientStat("avg_tick_ms", Integer.valueOf((int)(MathHelper.average(this.tickTimeArray) * 1.0E-6D)));
/* 1170 */     int l = 0;
/*      */     
/* 1172 */     if (this.worldServers != null) {
/*      */       byte b; int i; WorldServer[] arrayOfWorldServer;
/* 1174 */       for (i = (arrayOfWorldServer = this.worldServers).length, b = 0; b < i; ) { WorldServer worldserver1 = arrayOfWorldServer[b];
/*      */         
/* 1176 */         if (worldserver1 != null) {
/*      */           
/* 1178 */           WorldInfo worldinfo = worldserver1.getWorldInfo();
/* 1179 */           playerSnooper.addClientStat("world[" + l + "][dimension]", Integer.valueOf(worldserver1.provider.getDimensionType().getId()));
/* 1180 */           playerSnooper.addClientStat("world[" + l + "][mode]", worldinfo.getGameType());
/* 1181 */           playerSnooper.addClientStat("world[" + l + "][difficulty]", worldserver1.getDifficulty());
/* 1182 */           playerSnooper.addClientStat("world[" + l + "][hardcore]", Boolean.valueOf(worldinfo.isHardcoreModeEnabled()));
/* 1183 */           playerSnooper.addClientStat("world[" + l + "][generator_name]", worldinfo.getTerrainType().getWorldTypeName());
/* 1184 */           playerSnooper.addClientStat("world[" + l + "][generator_version]", Integer.valueOf(worldinfo.getTerrainType().getGeneratorVersion()));
/* 1185 */           playerSnooper.addClientStat("world[" + l + "][height]", Integer.valueOf(this.buildLimit));
/* 1186 */           playerSnooper.addClientStat("world[" + l + "][chunks_loaded]", Integer.valueOf(worldserver1.getChunkProvider().getLoadedChunkCount()));
/* 1187 */           l++;
/*      */         } 
/*      */         b++; }
/*      */     
/*      */     } 
/* 1192 */     playerSnooper.addClientStat("worlds", Integer.valueOf(l));
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerTypeToSnooper(Snooper playerSnooper) {
/* 1197 */     playerSnooper.addStatToSnooper("singleplayer", Boolean.valueOf(isSinglePlayer()));
/* 1198 */     playerSnooper.addStatToSnooper("server_brand", getServerModName());
/* 1199 */     playerSnooper.addStatToSnooper("gui_supported", GraphicsEnvironment.isHeadless() ? "headless" : "supported");
/* 1200 */     playerSnooper.addStatToSnooper("dedicated", Boolean.valueOf(isDedicatedServer()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSnooperEnabled() {
/* 1208 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isServerInOnlineMode() {
/* 1215 */     return this.onlineMode;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setOnlineMode(boolean online) {
/* 1220 */     this.onlineMode = online;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_190518_ac() {
/* 1225 */     return this.field_190519_A;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getCanSpawnAnimals() {
/* 1230 */     return this.canSpawnAnimals;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCanSpawnAnimals(boolean spawnAnimals) {
/* 1235 */     this.canSpawnAnimals = spawnAnimals;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getCanSpawnNPCs() {
/* 1240 */     return this.canSpawnNPCs;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCanSpawnNPCs(boolean spawnNpcs) {
/* 1251 */     this.canSpawnNPCs = spawnNpcs;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPVPEnabled() {
/* 1256 */     return this.pvpEnabled;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAllowPvp(boolean allowPvp) {
/* 1261 */     this.pvpEnabled = allowPvp;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFlightAllowed() {
/* 1266 */     return this.allowFlight;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAllowFlight(boolean allow) {
/* 1271 */     this.allowFlight = allow;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getMOTD() {
/* 1281 */     return this.motd;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMOTD(String motdIn) {
/* 1286 */     this.motd = motdIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBuildLimit() {
/* 1291 */     return this.buildLimit;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setBuildLimit(int maxBuildHeight) {
/* 1296 */     this.buildLimit = maxBuildHeight;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isServerStopped() {
/* 1301 */     return this.serverStopped;
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerList getPlayerList() {
/* 1306 */     return this.playerList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlayerList(PlayerList list) {
/* 1311 */     this.playerList = list;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setGameType(GameType gameMode) {
/*      */     byte b;
/*      */     int i;
/*      */     WorldServer[] arrayOfWorldServer;
/* 1319 */     for (i = (arrayOfWorldServer = this.worldServers).length, b = 0; b < i; ) { WorldServer worldserver1 = arrayOfWorldServer[b];
/*      */       
/* 1321 */       worldserver1.getWorldInfo().setGameType(gameMode);
/*      */       b++; }
/*      */   
/*      */   }
/*      */   
/*      */   public NetworkSystem getNetworkSystem() {
/* 1327 */     return this.networkSystem;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean serverIsInRunLoop() {
/* 1332 */     return this.serverIsRunning;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getGuiEnabled() {
/* 1337 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTickCounter() {
/* 1347 */     return this.tickCounter;
/*      */   }
/*      */ 
/*      */   
/*      */   public void enableProfiling() {
/* 1352 */     this.startProfiling = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public Snooper getPlayerUsageSnooper() {
/* 1357 */     return this.usageSnooper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public World getEntityWorld() {
/* 1366 */     return (World)this.worldServers[0];
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockProtected(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/* 1371 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getForceGamemode() {
/* 1379 */     return this.isGamemodeForced;
/*      */   }
/*      */ 
/*      */   
/*      */   public Proxy getServerProxy() {
/* 1384 */     return this.serverProxy;
/*      */   }
/*      */ 
/*      */   
/*      */   public static long getCurrentTimeMillis() {
/* 1389 */     return System.currentTimeMillis();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxPlayerIdleMinutes() {
/* 1394 */     return this.maxPlayerIdleMinutes;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlayerIdleTimeout(int idleTimeout) {
/* 1399 */     this.maxPlayerIdleMinutes = idleTimeout;
/*      */   }
/*      */ 
/*      */   
/*      */   public MinecraftSessionService getMinecraftSessionService() {
/* 1404 */     return this.sessionService;
/*      */   }
/*      */ 
/*      */   
/*      */   public GameProfileRepository getGameProfileRepository() {
/* 1409 */     return this.profileRepo;
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerProfileCache getPlayerProfileCache() {
/* 1414 */     return this.profileCache;
/*      */   }
/*      */ 
/*      */   
/*      */   public ServerStatusResponse getServerStatusResponse() {
/* 1419 */     return this.statusResponse;
/*      */   }
/*      */ 
/*      */   
/*      */   public void refreshStatusNextTick() {
/* 1424 */     this.nanoTimeSinceStatusRefresh = 0L;
/*      */   } @Nullable
/*      */   public Entity getEntityFromUuid(UUID uuid) {
/*      */     byte b;
/*      */     int i;
/*      */     WorldServer[] arrayOfWorldServer;
/* 1430 */     for (i = (arrayOfWorldServer = this.worldServers).length, b = 0; b < i; ) { WorldServer worldserver1 = arrayOfWorldServer[b];
/*      */       
/* 1432 */       if (worldserver1 != null) {
/*      */         
/* 1434 */         Entity entity = worldserver1.getEntityFromUuid(uuid);
/*      */         
/* 1436 */         if (entity != null)
/*      */         {
/* 1438 */           return entity;
/*      */         }
/*      */       } 
/*      */       b++; }
/*      */     
/* 1443 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean sendCommandFeedback() {
/* 1451 */     return this.worldServers[0].getGameRules().getBoolean("sendCommandFeedback");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MinecraftServer getServer() {
/* 1459 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMaxWorldSize() {
/* 1464 */     return 29999984;
/*      */   }
/*      */ 
/*      */   
/*      */   public <V> ListenableFuture<V> callFromMainThread(Callable<V> callable) {
/* 1469 */     Validate.notNull(callable);
/*      */     
/* 1471 */     if (!isCallingFromMinecraftThread() && !isServerStopped()) {
/*      */       
/* 1473 */       ListenableFutureTask<V> listenablefuturetask = ListenableFutureTask.create(callable);
/*      */       
/* 1475 */       synchronized (this.futureTaskQueue) {
/*      */         
/* 1477 */         this.futureTaskQueue.add(listenablefuturetask);
/* 1478 */         return (ListenableFuture<V>)listenablefuturetask;
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1485 */       return Futures.immediateFuture(callable.call());
/*      */     }
/* 1487 */     catch (Exception exception) {
/*      */       
/* 1489 */       return (ListenableFuture<V>)Futures.immediateFailedCheckedFuture(exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
/* 1496 */     Validate.notNull(runnableToSchedule);
/* 1497 */     return callFromMainThread(Executors.callable(runnableToSchedule));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCallingFromMinecraftThread() {
/* 1502 */     return (Thread.currentThread() == this.serverThread);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNetworkCompressionThreshold() {
/* 1510 */     return 256;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSpawnRadius(@Nullable WorldServer worldIn) {
/* 1515 */     return (worldIn != null) ? worldIn.getGameRules().getInt("spawnRadius") : 10;
/*      */   }
/*      */ 
/*      */   
/*      */   public AdvancementManager func_191949_aK() {
/* 1520 */     return this.worldServers[0].func_191952_z();
/*      */   }
/*      */ 
/*      */   
/*      */   public FunctionManager func_193030_aL() {
/* 1525 */     return this.worldServers[0].func_193037_A();
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_193031_aM() {
/* 1530 */     if (isCallingFromMinecraftThread()) {
/*      */       
/* 1532 */       getPlayerList().saveAllPlayerData();
/* 1533 */       this.worldServers[0].getLootTableManager().reloadLootTables();
/* 1534 */       func_191949_aK().func_192779_a();
/* 1535 */       func_193030_aL().func_193059_f();
/* 1536 */       getPlayerList().func_193244_w();
/*      */     }
/*      */     else {
/*      */       
/* 1540 */       addScheduledTask(this::func_193031_aM);
/*      */     } 
/*      */   }
/*      */   
/*      */   public abstract boolean startServer() throws IOException;
/*      */   
/*      */   public abstract boolean canStructuresSpawn();
/*      */   
/*      */   public abstract GameType getGameType();
/*      */   
/*      */   public abstract EnumDifficulty getDifficulty();
/*      */   
/*      */   public abstract boolean isHardcore();
/*      */   
/*      */   public abstract int getOpPermissionLevel();
/*      */   
/*      */   public abstract boolean shouldBroadcastRconToOps();
/*      */   
/*      */   public abstract boolean shouldBroadcastConsoleToOps();
/*      */   
/*      */   public abstract boolean isDedicatedServer();
/*      */   
/*      */   public abstract boolean shouldUseNativeTransport();
/*      */   
/*      */   public abstract boolean isCommandBlockEnabled();
/*      */   
/*      */   public abstract String shareToLAN(GameType paramGameType, boolean paramBoolean);
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\MinecraftServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */