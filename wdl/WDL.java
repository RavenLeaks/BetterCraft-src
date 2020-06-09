/*      */ package wdl;
/*      */ 
/*      */ import com.google.common.collect.HashMultimap;
/*      */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import net.minecraft.client.ClientBrandRetriever;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.multiplayer.ChunkProviderClient;
/*      */ import net.minecraft.client.multiplayer.ServerData;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.Slot;
/*      */ import net.minecraft.nbt.CompressedStreamTools;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagDouble;
/*      */ import net.minecraft.nbt.NBTTagFloat;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.network.NetworkManager;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.ChunkPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.world.MinecraftException;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*      */ import net.minecraft.world.storage.MapData;
/*      */ import net.minecraft.world.storage.SaveHandler;
/*      */ import net.minecraft.world.storage.ThreadedFileIOBase;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import wdl.api.IPlayerInfoEditor;
/*      */ import wdl.api.ISaveListener;
/*      */ import wdl.api.IWorldInfoEditor;
/*      */ import wdl.api.WDLApi;
/*      */ import wdl.gui.GuiWDLMultiworld;
/*      */ import wdl.gui.GuiWDLMultiworldSelect;
/*      */ import wdl.gui.GuiWDLOverwriteChanges;
/*      */ import wdl.gui.GuiWDLSaveProgress;
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
/*      */ public class WDL
/*      */ {
/*      */   public static final String VERSION = "1.11a-beta1";
/*      */   public static final String EXPECTED_MINECRAFT_VERSION = "1.11";
/*      */   public static final String GITHUB_REPO = "Pokechu22/WorldDownloader";
/*      */   public static Minecraft minecraft;
/*      */   public static WorldClient worldClient;
/*  109 */   public static NetworkManager networkManager = null;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static EntityPlayerSP thePlayer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Container windowContainer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BlockPos lastClickedBlock;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Entity lastEntity;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static SaveHandler saveHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static IChunkLoader chunkLoader;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  146 */   public static HashMap<ChunkPos, Map<BlockPos, TileEntity>> newTileEntities = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  151 */   public static HashMultimap<ChunkPos, Entity> newEntities = HashMultimap.create();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  157 */   public static HashMap<Integer, MapData> newMapDatas = new HashMap<>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean downloading = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMultiworld = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean propsFound = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean startOnChange = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean overrideLastModifiedCheck = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean saving = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean worldLoadingDeferred = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  199 */   public static String worldName = "WorldDownloaderERROR";
/*      */ 
/*      */ 
/*      */   
/*  203 */   public static String baseFolderName = "WorldDownloaderERROR";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Properties baseProps;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Properties worldProps;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final Properties globalProps;
/*      */ 
/*      */ 
/*      */   
/*      */   public static final Properties defaultProps;
/*      */ 
/*      */ 
/*      */   
/*  225 */   private static Logger logger = LogManager.getLogger();
/*      */   private static final int ANVIL_SAVE_VERSION = 19133;
/*      */   
/*      */   static {
/*  229 */     minecraft = Minecraft.getMinecraft();
/*      */     
/*  231 */     defaultProps = new Properties();
/*  232 */     defaultProps.setProperty("ServerName", "");
/*  233 */     defaultProps.setProperty("WorldName", "");
/*  234 */     defaultProps.setProperty("LinkedWorlds", "");
/*  235 */     defaultProps.setProperty("Backup", "ZIP");
/*  236 */     defaultProps.setProperty("AllowCheats", "true");
/*  237 */     defaultProps.setProperty("GameType", "keep");
/*  238 */     defaultProps.setProperty("Time", "keep");
/*  239 */     defaultProps.setProperty("Weather", "keep");
/*  240 */     defaultProps.setProperty("MapFeatures", "false");
/*  241 */     defaultProps.setProperty("RandomSeed", "");
/*  242 */     defaultProps.setProperty("MapGenerator", "void");
/*  243 */     defaultProps.setProperty("GeneratorName", "flat");
/*  244 */     defaultProps.setProperty("GeneratorVersion", "0");
/*  245 */     defaultProps.setProperty("GeneratorOptions", ";0");
/*  246 */     defaultProps.setProperty("Spawn", "player");
/*  247 */     defaultProps.setProperty("SpawnX", "8");
/*  248 */     defaultProps.setProperty("SpawnY", "127");
/*  249 */     defaultProps.setProperty("SpawnZ", "8");
/*  250 */     defaultProps.setProperty("PlayerPos", "keep");
/*  251 */     defaultProps.setProperty("PlayerX", "8");
/*  252 */     defaultProps.setProperty("PlayerY", "127");
/*  253 */     defaultProps.setProperty("PlayerZ", "8");
/*  254 */     defaultProps.setProperty("PlayerHealth", "20");
/*  255 */     defaultProps.setProperty("PlayerFood", "20");
/*      */     
/*  257 */     defaultProps.setProperty("Messages.enableAll", "false");
/*      */ 
/*      */     
/*  260 */     defaultProps.setProperty("Entity.TrackDistanceMode", "server");
/*      */ 
/*      */     
/*  263 */     defaultProps.setProperty("Entity.FireworksRocketEntity.Enabled", "false");
/*  264 */     defaultProps.setProperty("Entity.EnderDragon.Enabled", "false");
/*  265 */     defaultProps.setProperty("Entity.WitherBoss.Enabled", "false");
/*  266 */     defaultProps.setProperty("Entity.PrimedTnt.Enabled", "false");
/*  267 */     defaultProps.setProperty("Entity.null.Enabled", "false");
/*      */ 
/*      */     
/*  270 */     defaultProps.setProperty("EntityGroup.Other.Enabled", "true");
/*  271 */     defaultProps.setProperty("EntityGroup.Hostile.Enabled", "true");
/*  272 */     defaultProps.setProperty("EntityGroup.Passive.Enabled", "true");
/*      */ 
/*      */     
/*  275 */     defaultProps.setProperty("LastSaved", "-1");
/*      */ 
/*      */     
/*  278 */     defaultProps.setProperty("TutorialShown", "false");
/*      */ 
/*      */     
/*  281 */     defaultProps.setProperty("UpdateMinecraftVersion", "client");
/*      */     
/*  283 */     defaultProps.setProperty("UpdateAllowBetas", "true");
/*      */     
/*  285 */     globalProps = new Properties(defaultProps);
/*  286 */     FileReader reader = null;
/*      */     try {
/*  288 */       reader = new FileReader(new File(minecraft.mcDataDir, 
/*  289 */             "WorldDownloader.txt"));
/*  290 */       globalProps.load(reader);
/*  291 */     } catch (Exception e) {
/*  292 */       logger.debug("Failed to load global properties", e);
/*      */     } finally {
/*  294 */       if (reader != null) {
/*      */         try {
/*  296 */           reader.close();
/*  297 */         } catch (Exception e) {
/*  298 */           logger.warn("Failed to close global properties reader", e);
/*      */         } 
/*      */       }
/*      */     } 
/*  302 */     baseProps = new Properties(globalProps);
/*  303 */     worldProps = new Properties(baseProps);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void startDownload() {
/*  310 */     worldClient = minecraft.world;
/*      */     
/*  312 */     if (!WDLPluginChannels.canDownloadAtAll()) {
/*      */       return;
/*      */     }
/*      */     
/*  316 */     if (isMultiworld && worldName.isEmpty()) {
/*      */       
/*  318 */       minecraft.displayGuiScreen((GuiScreen)new GuiWDLMultiworldSelect(
/*  319 */             I18n.format("wdl.gui.multiworldSelect.title.startDownload", new Object[0]), 
/*  320 */             new GuiWDLMultiworldSelect.WorldSelectionCallback()
/*      */             {
/*      */               public void onWorldSelected(String selectedWorld) {
/*  323 */                 WDL.worldName = selectedWorld;
/*  324 */                 WDL.isMultiworld = true;
/*  325 */                 WDL.propsFound = true;
/*      */                 
/*  327 */                 WDL.minecraft.displayGuiScreen(null);
/*  328 */                 WDL.startDownload();
/*      */               }
/*      */ 
/*      */               
/*      */               public void onCancel() {
/*  333 */                 WDL.minecraft.displayGuiScreen(null);
/*  334 */                 WDL.cancelDownload();
/*      */               }
/*      */             }));
/*      */       
/*      */       return;
/*      */     } 
/*  340 */     if (!propsFound) {
/*      */       
/*  342 */       minecraft.displayGuiScreen((GuiScreen)new GuiWDLMultiworld(new GuiWDLMultiworld.MultiworldCallback()
/*      */             {
/*      */               public void onSelect(boolean enableMutliworld) {
/*  345 */                 WDL.isMultiworld = enableMutliworld;
/*      */                 
/*  347 */                 if (WDL.isMultiworld) {
/*      */ 
/*      */                   
/*  350 */                   WDL.minecraft.displayGuiScreen((GuiScreen)new GuiWDLMultiworldSelect(
/*  351 */                         I18n.format("wdl.gui.multiworldSelect.title.startDownload", new Object[0]), 
/*  352 */                         new GuiWDLMultiworldSelect.WorldSelectionCallback()
/*      */                         {
/*      */                           public void onWorldSelected(String selectedWorld) {
/*  355 */                             WDL.worldName = selectedWorld;
/*  356 */                             WDL.isMultiworld = true;
/*  357 */                             WDL.propsFound = true;
/*      */                             
/*  359 */                             WDL.minecraft.displayGuiScreen(null);
/*  360 */                             WDL.startDownload();
/*      */                           }
/*      */ 
/*      */                           
/*      */                           public void onCancel() {
/*  365 */                             WDL.minecraft.displayGuiScreen(null);
/*  366 */                             WDL.cancelDownload();
/*      */                           }
/*      */                         }));
/*      */                 } else {
/*  370 */                   WDL.baseProps.setProperty("LinkedWorlds", "");
/*  371 */                   WDL.saveProps();
/*  372 */                   WDL.propsFound = true;
/*      */                   
/*  374 */                   WDL.minecraft.displayGuiScreen(null);
/*  375 */                   WDL.startDownload();
/*      */                 } 
/*      */               }
/*      */ 
/*      */               
/*      */               public void onCancel() {
/*  381 */                 WDL.minecraft.displayGuiScreen(null);
/*  382 */                 WDL.cancelDownload();
/*      */               }
/*      */             }));
/*      */       
/*      */       return;
/*      */     } 
/*  388 */     worldProps = loadWorldProps(worldName);
/*  389 */     saveHandler = (SaveHandler)minecraft.getSaveLoader().getSaveLoader(
/*  390 */         getWorldFolderName(worldName), true);
/*      */     
/*  392 */     FileInputStream worldDat = null;
/*      */     try {
/*  394 */       long lastSaved = Long.parseLong(worldProps.getProperty("LastSaved", 
/*  395 */             "-1"));
/*      */ 
/*      */       
/*  398 */       worldDat = new FileInputStream(new File(
/*  399 */             saveHandler.getWorldDirectory(), "level.dat"));
/*  400 */       long lastPlayed = CompressedStreamTools.readCompressed(worldDat)
/*  401 */         .getCompoundTag("Data").getLong("LastPlayed");
/*  402 */       if (!overrideLastModifiedCheck && lastPlayed > lastSaved) {
/*      */ 
/*      */         
/*  405 */         minecraft.displayGuiScreen((GuiScreen)new GuiWDLOverwriteChanges(
/*  406 */               lastSaved, lastPlayed));
/*      */         return;
/*      */       } 
/*  409 */     } catch (Exception e) {
/*  410 */       logger.warn("Error while checking if the map has been played andneeds to be backed up (this is normal if this world has not been saved before): ", 
/*      */           
/*  412 */           e);
/*      */     } finally {
/*  414 */       if (worldDat != null) {
/*      */         try {
/*  416 */           worldDat.close();
/*  417 */         } catch (Exception e) {
/*  418 */           e.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*  422 */     minecraft.displayGuiScreen(null);
/*  423 */     minecraft.setIngameFocus();
/*  424 */     chunkLoader = (IChunkLoader)WDLChunkLoader.create(saveHandler, worldClient.provider);
/*  425 */     newTileEntities = new HashMap<>();
/*  426 */     newEntities = HashMultimap.create();
/*  427 */     newMapDatas = new HashMap<>();
/*      */     
/*  429 */     if (baseProps.getProperty("ServerName").isEmpty()) {
/*  430 */       baseProps.setProperty("ServerName", getServerName());
/*      */     }
/*      */     
/*  433 */     startOnChange = true;
/*  434 */     downloading = true;
/*  435 */     WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, 
/*  436 */         "wdl.messages.generalInfo.downloadStarted", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void stopDownload() {
/*  443 */     if (downloading) {
/*      */       
/*  445 */       downloading = false;
/*  446 */       startOnChange = false;
/*  447 */       WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, 
/*  448 */           "wdl.messages.generalInfo.downloadStopped", new Object[0]);
/*  449 */       startSaveThread();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void cancelDownload() {
/*  457 */     boolean wasDownloading = downloading;
/*      */     
/*  459 */     if (wasDownloading) {
/*  460 */       minecraft.getSaveLoader().flushCache();
/*  461 */       saveHandler.flush();
/*  462 */       startOnChange = false;
/*  463 */       saving = false;
/*  464 */       downloading = false;
/*  465 */       worldLoadingDeferred = false;
/*      */       
/*  467 */       WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, 
/*  468 */           "wdl.messages.generalInfo.downloadCanceled", new Object[0]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void startSaveThread() {
/*  477 */     WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, 
/*  478 */         "wdl.messages.generalInfo.saveStarted", new Object[0]);
/*  479 */     saving = true;
/*  480 */     Thread thread = new Thread("WDL Save Thread")
/*      */       {
/*      */         public void run() {
/*      */           try {
/*  484 */             WDL.saveEverything();
/*  485 */             WDL.saving = false;
/*  486 */             WDL.onSaveComplete();
/*  487 */           } catch (Throwable e) {
/*  488 */             WDL.crashed(e, "World Downloader Mod: Saving world");
/*      */           } 
/*      */         }
/*      */       };
/*  492 */     thread.start();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean loadWorld() {
/*  501 */     worldName = "";
/*  502 */     worldClient = minecraft.world;
/*  503 */     thePlayer = minecraft.player;
/*  504 */     windowContainer = thePlayer.openContainer;
/*  505 */     overrideLastModifiedCheck = false;
/*      */     
/*  507 */     NetworkManager newNM = thePlayer.connection.getNetworkManager();
/*      */ 
/*      */ 
/*      */     
/*  511 */     if (networkManager != newNM) {
/*  512 */       loadBaseProps();
/*  513 */       WDLMessages.onNewServer();
/*      */     } 
/*      */     
/*  516 */     WDLPluginChannels.onWorldLoad();
/*      */ 
/*      */     
/*  519 */     if (networkManager != newNM) {
/*      */       
/*  521 */       WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_WORLD_LOAD, 
/*  522 */           "wdl.messages.onWorldLoad.differentServer", new Object[0]);
/*      */       
/*  524 */       networkManager = newNM;
/*      */       
/*  526 */       if (isSpigot()) {
/*  527 */         WDLMessages.chatMessageTranslated(
/*  528 */             WDLMessageTypes.ON_WORLD_LOAD, 
/*  529 */             "wdl.messages.onWorldLoad.spigot", new Object[] {
/*  530 */               thePlayer.getServerBrand() });
/*      */       } else {
/*  532 */         WDLMessages.chatMessageTranslated(
/*  533 */             WDLMessageTypes.ON_WORLD_LOAD, 
/*  534 */             "wdl.messages.onWorldLoad.vanilla", new Object[] {
/*  535 */               thePlayer.getServerBrand()
/*      */             });
/*      */       } 
/*  538 */       startOnChange = false;
/*      */       
/*  540 */       return true;
/*      */     } 
/*      */     
/*  543 */     WDLMessages.chatMessageTranslated(WDLMessageTypes.ON_WORLD_LOAD, 
/*  544 */         "wdl.messages.onWorldLoad.sameServer", new Object[0]);
/*      */     
/*  546 */     if (isSpigot()) {
/*  547 */       WDLMessages.chatMessageTranslated(
/*  548 */           WDLMessageTypes.ON_WORLD_LOAD, 
/*  549 */           "wdl.messages.onWorldLoad.spigot", new Object[] {
/*  550 */             thePlayer.getServerBrand() });
/*      */     } else {
/*  552 */       WDLMessages.chatMessageTranslated(
/*  553 */           WDLMessageTypes.ON_WORLD_LOAD, 
/*  554 */           "wdl.messages.onWorldLoad.vanilla", new Object[] {
/*  555 */             thePlayer.getServerBrand()
/*      */           });
/*      */     } 
/*  558 */     if (startOnChange) {
/*  559 */       startDownload();
/*      */     }
/*      */     
/*  562 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void onSaveComplete() {
/*  570 */     minecraft.getSaveLoader().flushCache();
/*  571 */     saveHandler.flush();
/*  572 */     worldClient = null;
/*      */     
/*  574 */     worldLoadingDeferred = false;
/*      */ 
/*      */     
/*  577 */     if (downloading) {
/*  578 */       WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, 
/*  579 */           "wdl.messages.generalInfo.saveComplete.startingAgain", new Object[0]);
/*  580 */       loadWorld();
/*      */       
/*      */       return;
/*      */     } 
/*  584 */     WDLMessages.chatMessageTranslated(WDLMessageTypes.INFO, 
/*  585 */         "wdl.messages.generalInfo.saveComplete.done", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveEverything() throws Exception {
/*  593 */     if (!WDLPluginChannels.canDownloadAtAll()) {
/*  594 */       WDLMessages.chatMessageTranslated(WDLMessageTypes.ERROR, 
/*  595 */           "wdl.messages.generalError.forbidden", new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/*  599 */     WorldBackup.WorldBackupType backupType = 
/*  600 */       WorldBackup.WorldBackupType.match(baseProps.getProperty("Backup", "ZIP"));
/*      */     
/*  602 */     final GuiWDLSaveProgress progressScreen = new GuiWDLSaveProgress(
/*  603 */         I18n.format("wdl.saveProgress.title", new Object[0]), (
/*  604 */         (backupType != WorldBackup.WorldBackupType.NONE) ? 6 : 5) + 
/*  605 */         WDLApi.getImplementingExtensions(ISaveListener.class).size());
/*      */     
/*  607 */     minecraft.addScheduledTask(new Runnable()
/*      */         {
/*      */           public void run() {
/*  610 */             WDL.minecraft.displayGuiScreen((GuiScreen)progressScreen);
/*      */           }
/*      */         });
/*      */     
/*  614 */     saveProps();
/*      */     
/*      */     try {
/*  617 */       saveHandler.checkSessionLock();
/*  618 */     } catch (MinecraftException e) {
/*  619 */       throw new RuntimeException(
/*  620 */           "WorldDownloader: Couldn't get session lock for saving the world!", e);
/*      */     } 
/*      */ 
/*      */     
/*  624 */     NBTTagCompound playerNBT = savePlayer(progressScreen);
/*  625 */     saveWorldInfo(progressScreen, playerNBT);
/*      */     
/*  627 */     saveMapData(progressScreen);
/*  628 */     saveChunks(progressScreen);
/*      */     
/*  630 */     saveProps();
/*      */ 
/*      */     
/*  633 */     Iterator<WDLApi.ModInfo<ISaveListener>> iterator = WDLApi.getImplementingExtensions(ISaveListener.class).iterator(); while (iterator.hasNext()) { WDLApi.ModInfo<ISaveListener> info = iterator.next();
/*  634 */       progressScreen.startMajorTask(
/*  635 */           I18n.format("wdl.saveProgress.extension.title", new Object[] {
/*  636 */               info.getDisplayName() }), 1);
/*  637 */       ((ISaveListener)info.mod).afterChunksSaved(saveHandler.getWorldDirectory()); }
/*      */ 
/*      */     
/*      */     try {
/*  641 */       WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, 
/*  642 */           "wdl.messages.saving.flushingIO", new Object[0]);
/*      */       
/*  644 */       progressScreen.startMajorTask(
/*  645 */           I18n.format("wdl.saveProgress.flushingIO.title", new Object[0]), 1);
/*  646 */       progressScreen.setMinorTaskProgress(
/*  647 */           I18n.format("wdl.saveProgress.flushingIO.subtitle", new Object[0]), 1);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  652 */       ThreadedFileIOBase.getThreadedIOInstance().waitForFinish();
/*  653 */     } catch (Exception e) {
/*  654 */       throw new RuntimeException("Threw exception waiting for asynchronous IO to finish. Hmmm.", e);
/*      */     } 
/*      */     
/*  657 */     if (backupType != WorldBackup.WorldBackupType.NONE) {
/*  658 */       WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, 
/*  659 */           "wdl.messages.saving.backingUp", new Object[0]);
/*  660 */       progressScreen.startMajorTask(
/*  661 */           backupType.getTitle(), 1);
/*  662 */       progressScreen.setMinorTaskProgress(
/*  663 */           I18n.format("wdl.saveProgress.backingUp.preparing", new Object[0]), 1);
/*      */       
/*      */       try {
/*  666 */         WorldBackup.backupWorld(saveHandler.getWorldDirectory(), 
/*  667 */             getWorldFolderName(worldName), backupType, (WorldBackup.IBackupProgressMonitor)progressScreen);
/*  668 */       } catch (IOException e) {
/*  669 */         WDLMessages.chatMessageTranslated(WDLMessageTypes.ERROR, 
/*  670 */             "wdl.messages.generalError.failedToBackUp", new Object[0]);
/*      */       } 
/*      */     } 
/*      */     
/*  674 */     progressScreen.setDoneWorking();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static NBTTagCompound savePlayer(GuiWDLSaveProgress progressScreen) {
/*  684 */     if (!WDLPluginChannels.canDownloadAtAll()) return new NBTTagCompound();
/*      */     
/*  686 */     progressScreen.startMajorTask(
/*  687 */         I18n.format("wdl.saveProgress.playerData.title", new Object[0]), 
/*  688 */         3 + WDLApi.getImplementingExtensions(IPlayerInfoEditor.class).size());
/*  689 */     WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, 
/*  690 */         "wdl.messages.saving.savingPlayer", new Object[0]);
/*      */     
/*  692 */     progressScreen.setMinorTaskProgress(
/*  693 */         I18n.format("wdl.saveProgress.playerData.creatingNBT", new Object[0]), 1);
/*      */     
/*  695 */     NBTTagCompound playerNBT = new NBTTagCompound();
/*  696 */     thePlayer.writeToNBT(playerNBT);
/*      */     
/*  698 */     progressScreen.setMinorTaskProgress(
/*  699 */         I18n.format("wdl.saveProgress.playerData.editingNBT", new Object[0]), 2);
/*  700 */     applyOverridesToPlayer(playerNBT);
/*      */     
/*  702 */     int taskNum = 3;
/*      */     
/*  704 */     Iterator<WDLApi.ModInfo<IPlayerInfoEditor>> iterator = WDLApi.getImplementingExtensions(IPlayerInfoEditor.class).iterator(); while (iterator.hasNext()) { WDLApi.ModInfo<IPlayerInfoEditor> info = iterator.next();
/*  705 */       progressScreen.setMinorTaskProgress(
/*  706 */           I18n.format("wdl.saveProgress.playerData.extension", new Object[] {
/*  707 */               info.getDisplayName() }), taskNum);
/*      */       
/*  709 */       ((IPlayerInfoEditor)info.mod).editPlayerInfo(thePlayer, saveHandler, playerNBT);
/*      */       
/*  711 */       taskNum++; }
/*      */ 
/*      */     
/*  714 */     progressScreen.setMinorTaskProgress(
/*  715 */         I18n.format("wdl.saveProgress.playerData.writingNBT", new Object[0]), taskNum);
/*      */     
/*  717 */     FileOutputStream stream = null;
/*      */     try {
/*  719 */       File playersDirectory = new File(saveHandler.getWorldDirectory(), 
/*  720 */           "playerdata");
/*  721 */       File playerFileTmp = new File(playersDirectory, 
/*  722 */           String.valueOf(thePlayer.getUniqueID().toString()) + ".dat.tmp");
/*  723 */       File playerFile = new File(playersDirectory, 
/*  724 */           String.valueOf(thePlayer.getUniqueID().toString()) + ".dat");
/*      */       
/*  726 */       stream = new FileOutputStream(playerFileTmp);
/*      */       
/*  728 */       CompressedStreamTools.writeCompressed(playerNBT, stream);
/*      */ 
/*      */       
/*  731 */       if (playerFile.exists()) {
/*  732 */         playerFile.delete();
/*      */       }
/*      */       
/*  735 */       playerFileTmp.renameTo(playerFile);
/*  736 */     } catch (Exception e) {
/*  737 */       throw new RuntimeException("Couldn't save the player!", e);
/*      */     } finally {
/*  739 */       if (stream != null) {
/*      */         try {
/*  741 */           stream.close();
/*  742 */         } catch (IOException e) {
/*  743 */           throw new RuntimeException(e);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  748 */     WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, 
/*  749 */         "wdl.messages.saving.playerSaved", new Object[0]);
/*      */     
/*  751 */     return playerNBT;
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
/*      */   public static void saveWorldInfo(GuiWDLSaveProgress progressScreen, NBTTagCompound playerInfoNBT) {
/*  768 */     if (!WDLPluginChannels.canDownloadAtAll())
/*      */       return; 
/*  770 */     progressScreen.startMajorTask(
/*  771 */         I18n.format("wdl.saveProgress.worldMetadata.title", new Object[0]), 
/*  772 */         3 + WDLApi.getImplementingExtensions(IWorldInfoEditor.class).size());
/*  773 */     WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, 
/*  774 */         "wdl.messages.saving.savingWorld", new Object[0]);
/*      */     
/*  776 */     progressScreen.setMinorTaskProgress(
/*  777 */         I18n.format("wdl.saveProgress.worldMetadata.creatingNBT", new Object[0]), 1);
/*      */ 
/*      */ 
/*      */     
/*  781 */     worldClient.getWorldInfo().setSaveVersion(19133);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  786 */     NBTTagCompound worldInfoNBT = worldClient.getWorldInfo()
/*  787 */       .cloneNBTCompound(playerInfoNBT);
/*      */     
/*  789 */     progressScreen.setMinorTaskProgress(
/*  790 */         I18n.format("wdl.saveProgress.worldMetadata.editingNBT", new Object[0]), 2);
/*  791 */     applyOverridesToWorldInfo(worldInfoNBT);
/*      */     
/*  793 */     int taskNum = 3;
/*      */     
/*  795 */     Iterator<WDLApi.ModInfo<IWorldInfoEditor>> iterator = WDLApi.getImplementingExtensions(IWorldInfoEditor.class).iterator(); while (iterator.hasNext()) { WDLApi.ModInfo<IWorldInfoEditor> info = iterator.next();
/*  796 */       progressScreen.setMinorTaskProgress(
/*  797 */           I18n.format("wdl.saveProgress.worldMetadata.extension", new Object[] {
/*  798 */               info.getDisplayName() }), taskNum);
/*      */       
/*  800 */       ((IWorldInfoEditor)info.mod).editWorldInfo(worldClient, worldClient.getWorldInfo(), 
/*  801 */           saveHandler, worldInfoNBT);
/*      */       
/*  803 */       taskNum++; }
/*      */ 
/*      */     
/*  806 */     progressScreen.setMinorTaskProgress(
/*  807 */         I18n.format("wdl.saveProgress.worldMetadata.writingNBT", new Object[0]), taskNum);
/*  808 */     File saveDirectory = saveHandler.getWorldDirectory();
/*  809 */     NBTTagCompound dataNBT = new NBTTagCompound();
/*  810 */     dataNBT.setTag("Data", (NBTBase)worldInfoNBT);
/*      */     
/*  812 */     worldProps.setProperty("LastSaved", 
/*  813 */         Long.toString(worldInfoNBT.getLong("LastPlayed")));
/*      */     
/*  815 */     FileOutputStream stream = null;
/*      */     try {
/*  817 */       File dataFile = new File(saveDirectory, "level.dat_new");
/*  818 */       File dataFileBackup = new File(saveDirectory, "level.dat_old");
/*  819 */       File dataFileOld = new File(saveDirectory, "level.dat");
/*  820 */       stream = new FileOutputStream(dataFile);
/*      */       
/*  822 */       CompressedStreamTools.writeCompressed(dataNBT, stream);
/*      */       
/*  824 */       if (dataFileBackup.exists()) {
/*  825 */         dataFileBackup.delete();
/*      */       }
/*      */       
/*  828 */       dataFileOld.renameTo(dataFileBackup);
/*      */       
/*  830 */       if (dataFileOld.exists()) {
/*  831 */         dataFileOld.delete();
/*      */       }
/*      */       
/*  834 */       dataFile.renameTo(dataFileOld);
/*      */       
/*  836 */       if (dataFile.exists()) {
/*  837 */         dataFile.delete();
/*      */       }
/*  839 */     } catch (Exception e) {
/*  840 */       throw new RuntimeException("Couldn't save the world metadata!", e);
/*      */     } finally {
/*  842 */       if (stream != null) {
/*      */         try {
/*  844 */           stream.close();
/*  845 */         } catch (IOException e) {
/*  846 */           throw new RuntimeException(e);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  851 */     WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, 
/*  852 */         "wdl.messages.saving.worldSaved", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveChunks(GuiWDLSaveProgress progressScreen) throws IllegalArgumentException, IllegalAccessException {
/*  863 */     if (!WDLPluginChannels.canDownloadAtAll())
/*      */       return; 
/*  865 */     WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, 
/*  866 */         "wdl.messages.saving.savingChunks", new Object[0]);
/*      */ 
/*      */     
/*  869 */     ChunkProviderClient chunkProvider = worldClient
/*  870 */       .getChunkProvider();
/*      */ 
/*      */     
/*  873 */     Long2ObjectMap<Chunk> chunkMap = 
/*  874 */       ReflectionUtils.<Long2ObjectMap<Chunk>>stealAndGetField(chunkProvider, (Class)Long2ObjectMap.class);
/*  875 */     List<Chunk> chunks = new ArrayList<>((Collection<? extends Chunk>)chunkMap.values());
/*      */     
/*  877 */     progressScreen.startMajorTask(I18n.format("wdl.saveProgress.chunk.title", new Object[0]), 
/*  878 */         chunks.size());
/*      */     
/*  880 */     for (int currentChunk = 0; currentChunk < chunks.size(); currentChunk++) {
/*  881 */       Chunk c = chunks.get(currentChunk);
/*  882 */       if (c != null)
/*      */       {
/*  884 */         if (WDLPluginChannels.canSaveChunk(c)) {
/*      */ 
/*      */ 
/*      */           
/*  888 */           progressScreen.setMinorTaskProgress(I18n.format(
/*  889 */                 "wdl.saveProgress.chunk.saving", new Object[] { Integer.valueOf(c.xPosition), 
/*  890 */                   Integer.valueOf(c.zPosition) }), currentChunk);
/*      */           
/*  892 */           saveChunk(c);
/*      */         }  } 
/*      */     } 
/*  895 */     WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, 
/*  896 */         "wdl.messages.saving.chunksSaved", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveChunk(Chunk c) {
/*  903 */     if (!WDLPluginChannels.canDownloadAtAll())
/*      */       return; 
/*  905 */     if (!WDLPluginChannels.canSaveChunk(c))
/*      */       return; 
/*  907 */     c.setTerrainPopulated(true);
/*      */     
/*      */     try {
/*  910 */       chunkLoader.saveChunk((World)worldClient, c);
/*  911 */     } catch (Exception e) {
/*      */       
/*  913 */       WDLMessages.chatMessageTranslated(WDLMessageTypes.ERROR, 
/*  914 */           "wdl.messages.generalError.failedToSaveChunk", new Object[] {
/*  915 */             Integer.valueOf(c.xPosition), Integer.valueOf(c.zPosition), e
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void loadBaseProps() {
/*  924 */     baseFolderName = getBaseFolderName();
/*  925 */     baseProps = new Properties(globalProps);
/*      */     
/*  927 */     FileReader reader = null;
/*      */     try {
/*  929 */       File savesFolder = new File(minecraft.mcDataDir, "saves");
/*  930 */       File baseFolder = new File(savesFolder, baseFolderName);
/*  931 */       reader = new FileReader(new File(baseFolder, 
/*  932 */             "WorldDownloader.txt"));
/*  933 */       baseProps.load(reader);
/*  934 */       propsFound = true;
/*  935 */     } catch (Exception e) {
/*  936 */       propsFound = false;
/*  937 */       logger.debug("Failed to load base properties", e);
/*      */     } finally {
/*  939 */       if (reader != null) {
/*      */         try {
/*  941 */           reader.close();
/*  942 */         } catch (Exception e) {
/*  943 */           logger.warn("Failed to close base properties reader", e);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/*  948 */     if (baseProps.getProperty("LinkedWorlds").isEmpty()) {
/*  949 */       isMultiworld = false;
/*  950 */       worldProps = new Properties(baseProps);
/*      */     } else {
/*  952 */       isMultiworld = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Properties loadWorldProps(String theWorldName) {
/*  963 */     Properties ret = new Properties(baseProps);
/*      */     
/*  965 */     if (theWorldName.isEmpty()) {
/*  966 */       return ret;
/*      */     }
/*      */     
/*  969 */     File savesDir = new File(minecraft.mcDataDir, "saves");
/*      */     
/*  971 */     String folder = getWorldFolderName(theWorldName);
/*  972 */     File worldFolder = new File(savesDir, folder);
/*      */     
/*  974 */     FileReader reader = null;
/*      */     try {
/*  976 */       ret.load(new FileReader(new File(worldFolder, 
/*  977 */               "WorldDownloader.txt")));
/*      */       
/*  979 */       return ret;
/*  980 */     } catch (Exception e) {
/*  981 */       logger.debug("Failed to load world props for " + worldName, e);
/*  982 */       return ret;
/*      */     } finally {
/*  984 */       if (reader != null) {
/*      */         try {
/*  986 */           reader.close();
/*  987 */         } catch (Exception e) {
/*  988 */           logger.warn("Failed to close world props reader for " + 
/*  989 */               worldName, e);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveProps() {
/* 1000 */     saveProps(worldName, worldProps);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveProps(String theWorldName, Properties theWorldProps) {
/* 1008 */     File savesDir = new File(minecraft.mcDataDir, "saves");
/*      */     
/* 1010 */     if (theWorldName.length() > 0) {
/* 1011 */       String folder = getWorldFolderName(theWorldName);
/*      */       
/* 1013 */       File worldFolder = new File(savesDir, folder);
/* 1014 */       worldFolder.mkdirs();
/*      */       try {
/* 1016 */         theWorldProps.store(new FileWriter(new File(worldFolder, 
/* 1017 */                 "WorldDownloader.txt")), I18n.format("wdl.props.world.title", new Object[0]));
/* 1018 */       } catch (Exception exception) {}
/*      */     }
/* 1020 */     else if (!isMultiworld) {
/* 1021 */       baseProps.putAll(theWorldProps);
/*      */     } 
/*      */     
/* 1024 */     File baseFolder = new File(savesDir, baseFolderName);
/* 1025 */     baseFolder.mkdirs();
/*      */     
/*      */     try {
/* 1028 */       baseProps.store(new FileWriter(new File(baseFolder, 
/* 1029 */               "WorldDownloader.txt")), I18n.format("wdl.props.base.title", new Object[0]));
/* 1030 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/* 1033 */     saveGlobalProps();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveGlobalProps() {
/*      */     try {
/* 1041 */       globalProps.store(new FileWriter(new File(minecraft.mcDataDir, 
/* 1042 */               "WorldDownloader.txt")), I18n.format("wdl.props.global.title", new Object[0]));
/* 1043 */     } catch (Exception exception) {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void applyOverridesToPlayer(NBTTagCompound playerNBT) {
/* 1054 */     String health = worldProps.getProperty("PlayerHealth");
/*      */     
/* 1056 */     if (!health.equals("keep")) {
/* 1057 */       short h = Short.parseShort(health);
/* 1058 */       playerNBT.setShort("Health", h);
/*      */     } 
/*      */ 
/*      */     
/* 1062 */     String food = worldProps.getProperty("PlayerFood");
/*      */     
/* 1064 */     if (!food.equals("keep")) {
/* 1065 */       int f = Integer.parseInt(food);
/* 1066 */       playerNBT.setInteger("foodLevel", f);
/* 1067 */       playerNBT.setInteger("foodTickTimer", 0);
/*      */       
/* 1069 */       if (f == 20) {
/* 1070 */         playerNBT.setFloat("foodSaturationLevel", 5.0F);
/*      */       } else {
/* 1072 */         playerNBT.setFloat("foodSaturationLevel", 0.0F);
/*      */       } 
/*      */       
/* 1075 */       playerNBT.setFloat("foodExhaustionLevel", 0.0F);
/*      */     } 
/*      */ 
/*      */     
/* 1079 */     String playerPos = worldProps.getProperty("PlayerPos");
/*      */     
/* 1081 */     if (playerPos.equals("xyz")) {
/* 1082 */       int x = Integer.parseInt(worldProps.getProperty("PlayerX"));
/* 1083 */       int y = Integer.parseInt(worldProps.getProperty("PlayerY"));
/* 1084 */       int z = Integer.parseInt(worldProps.getProperty("PlayerZ"));
/*      */ 
/*      */       
/* 1087 */       NBTTagList pos = new NBTTagList();
/* 1088 */       pos.appendTag((NBTBase)new NBTTagDouble(x + 0.5D));
/* 1089 */       pos.appendTag((NBTBase)new NBTTagDouble(y + 0.621D));
/* 1090 */       pos.appendTag((NBTBase)new NBTTagDouble(z + 0.5D));
/* 1091 */       playerNBT.setTag("Pos", (NBTBase)pos);
/* 1092 */       NBTTagList motion = new NBTTagList();
/* 1093 */       motion.appendTag((NBTBase)new NBTTagDouble(0.0D));
/*      */       
/* 1095 */       motion.appendTag((NBTBase)new NBTTagDouble(-1.0E-4D));
/* 1096 */       motion.appendTag((NBTBase)new NBTTagDouble(0.0D));
/* 1097 */       playerNBT.setTag("Motion", (NBTBase)motion);
/* 1098 */       NBTTagList rotation = new NBTTagList();
/* 1099 */       rotation.appendTag((NBTBase)new NBTTagFloat(0.0F));
/* 1100 */       rotation.appendTag((NBTBase)new NBTTagFloat(0.0F));
/* 1101 */       playerNBT.setTag("Rotation", (NBTBase)rotation);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1106 */     if (thePlayer.capabilities.allowFlying) {
/* 1107 */       playerNBT.getCompoundTag("abilities").setBoolean("flying", true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void applyOverridesToWorldInfo(NBTTagCompound worldInfoNBT) {
/* 1117 */     String baseName = baseProps.getProperty("ServerName");
/* 1118 */     String worldName = worldProps.getProperty("WorldName");
/*      */     
/* 1120 */     if (worldName.isEmpty()) {
/* 1121 */       worldInfoNBT.setString("LevelName", baseName);
/*      */     } else {
/* 1123 */       worldInfoNBT.setString("LevelName", String.valueOf(baseName) + " - " + worldName);
/*      */     } 
/*      */ 
/*      */     
/* 1127 */     if (worldProps.getProperty("AllowCheats").equals("true")) {
/* 1128 */       worldInfoNBT.setBoolean("allowCommands", true);
/*      */     } else {
/* 1130 */       worldInfoNBT.setBoolean("allowCommands", false);
/*      */     } 
/*      */ 
/*      */     
/* 1134 */     String gametypeOption = worldProps.getProperty("GameType");
/*      */     
/* 1136 */     if (gametypeOption.equals("keep")) {
/* 1137 */       if (thePlayer.capabilities.isCreativeMode) {
/* 1138 */         worldInfoNBT.setInteger("GameType", 1);
/*      */       } else {
/* 1140 */         worldInfoNBT.setInteger("GameType", 0);
/*      */       } 
/* 1142 */     } else if (gametypeOption.equals("survival")) {
/* 1143 */       worldInfoNBT.setInteger("GameType", 0);
/* 1144 */     } else if (gametypeOption.equals("creative")) {
/* 1145 */       worldInfoNBT.setInteger("GameType", 1);
/* 1146 */     } else if (gametypeOption.equals("hardcore")) {
/* 1147 */       worldInfoNBT.setInteger("GameType", 0);
/* 1148 */       worldInfoNBT.setBoolean("hardcore", true);
/*      */     } 
/*      */ 
/*      */     
/* 1152 */     String timeOption = worldProps.getProperty("Time");
/*      */     
/* 1154 */     if (!timeOption.equals("keep")) {
/* 1155 */       long t = Integer.parseInt(timeOption);
/* 1156 */       worldInfoNBT.setLong("Time", t);
/*      */     } 
/*      */ 
/*      */     
/* 1160 */     String randomSeed = worldProps.getProperty("RandomSeed");
/* 1161 */     long seed = 0L;
/*      */     
/* 1163 */     if (!randomSeed.isEmpty()) {
/*      */       try {
/* 1165 */         seed = Long.parseLong(randomSeed);
/* 1166 */       } catch (NumberFormatException numberformatexception) {
/* 1167 */         seed = randomSeed.hashCode();
/*      */       } 
/*      */     }
/*      */     
/* 1171 */     worldInfoNBT.setLong("RandomSeed", seed);
/*      */     
/* 1173 */     boolean mapFeatures = Boolean.parseBoolean(worldProps
/* 1174 */         .getProperty("MapFeatures"));
/* 1175 */     worldInfoNBT.setBoolean("MapFeatures", mapFeatures);
/*      */     
/* 1177 */     String generatorName = worldProps.getProperty("GeneratorName");
/* 1178 */     worldInfoNBT.setString("generatorName", generatorName);
/*      */     
/* 1180 */     String generatorOptions = worldProps.getProperty("GeneratorOptions");
/* 1181 */     worldInfoNBT.setString("generatorOptions", generatorOptions);
/*      */     
/* 1183 */     int generatorVersion = Integer.parseInt(worldProps
/* 1184 */         .getProperty("GeneratorVersion"));
/* 1185 */     worldInfoNBT.setInteger("generatorVersion", generatorVersion);
/*      */     
/* 1187 */     String weather = worldProps.getProperty("Weather");
/*      */     
/* 1189 */     if (weather.equals("sunny")) {
/* 1190 */       worldInfoNBT.setBoolean("raining", false);
/* 1191 */       worldInfoNBT.setInteger("rainTime", 0);
/* 1192 */       worldInfoNBT.setBoolean("thundering", false);
/* 1193 */       worldInfoNBT.setInteger("thunderTime", 0);
/* 1194 */     } else if (weather.equals("rain")) {
/* 1195 */       worldInfoNBT.setBoolean("raining", true);
/* 1196 */       worldInfoNBT.setInteger("rainTime", 24000);
/* 1197 */       worldInfoNBT.setBoolean("thundering", false);
/* 1198 */       worldInfoNBT.setInteger("thunderTime", 0);
/* 1199 */     } else if (weather.equals("thunderstorm")) {
/* 1200 */       worldInfoNBT.setBoolean("raining", true);
/* 1201 */       worldInfoNBT.setInteger("rainTime", 24000);
/* 1202 */       worldInfoNBT.setBoolean("thundering", true);
/* 1203 */       worldInfoNBT.setInteger("thunderTime", 24000);
/*      */     } 
/*      */ 
/*      */     
/* 1207 */     String spawn = worldProps.getProperty("Spawn");
/*      */     
/* 1209 */     if (spawn.equals("player")) {
/* 1210 */       int x = MathHelper.floor(thePlayer.posX);
/* 1211 */       int y = MathHelper.floor(thePlayer.posY);
/* 1212 */       int z = MathHelper.floor(thePlayer.posZ);
/* 1213 */       worldInfoNBT.setInteger("SpawnX", x);
/* 1214 */       worldInfoNBT.setInteger("SpawnY", y);
/* 1215 */       worldInfoNBT.setInteger("SpawnZ", z);
/* 1216 */       worldInfoNBT.setBoolean("initialized", true);
/* 1217 */     } else if (spawn.equals("xyz")) {
/* 1218 */       int x = Integer.parseInt(worldProps.getProperty("SpawnX"));
/* 1219 */       int y = Integer.parseInt(worldProps.getProperty("SpawnY"));
/* 1220 */       int z = Integer.parseInt(worldProps.getProperty("SpawnZ"));
/* 1221 */       worldInfoNBT.setInteger("SpawnX", x);
/* 1222 */       worldInfoNBT.setInteger("SpawnY", y);
/* 1223 */       worldInfoNBT.setInteger("SpawnZ", z);
/* 1224 */       worldInfoNBT.setBoolean("initialized", true);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void saveMapData(GuiWDLSaveProgress progressScreen) {
/* 1235 */     if (!WDLPluginChannels.canSaveMaps())
/*      */       return; 
/* 1237 */     File dataDirectory = new File(saveHandler.getWorldDirectory(), 
/* 1238 */         "data");
/* 1239 */     dataDirectory.mkdirs();
/*      */     
/* 1241 */     progressScreen.startMajorTask(
/* 1242 */         I18n.format("wdl.saveProgress.map.title", new Object[0]), newMapDatas.size());
/*      */     
/* 1244 */     WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, 
/* 1245 */         "wdl.messages.saving.savingMapItemData", new Object[0]);
/*      */     
/* 1247 */     int count = 0;
/* 1248 */     for (Map.Entry<Integer, MapData> e : newMapDatas.entrySet()) {
/* 1249 */       count++;
/*      */       
/* 1251 */       progressScreen.setMinorTaskProgress(
/* 1252 */           I18n.format("wdl.saveProgress.map.saving", new Object[] { e.getKey()
/* 1253 */             }), count);
/*      */       
/* 1255 */       File mapFile = new File(dataDirectory, "map_" + e.getKey() + ".dat");
/*      */       
/* 1257 */       NBTTagCompound mapNBT = new NBTTagCompound();
/* 1258 */       NBTTagCompound data = new NBTTagCompound();
/*      */       
/* 1260 */       ((MapData)e.getValue()).writeToNBT(data);
/*      */       
/* 1262 */       mapNBT.setTag("data", (NBTBase)data);
/*      */       
/*      */       try {
/* 1265 */         CompressedStreamTools.writeCompressed(mapNBT, 
/* 1266 */             new FileOutputStream(mapFile));
/* 1267 */       } catch (IOException ex) {
/* 1268 */         throw new RuntimeException("WDL: Exception while writing map data for map " + 
/* 1269 */             e.getKey() + "!", ex);
/*      */       } 
/*      */     } 
/*      */     
/* 1273 */     WDLMessages.chatMessageTranslated(WDLMessageTypes.SAVING, 
/* 1274 */         "wdl.messages.saving.mapItemDataSaved", new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getServerName() {
/*      */     try {
/* 1283 */       if (minecraft.getCurrentServerData() != null) {
/* 1284 */         String name = (minecraft.getCurrentServerData()).serverName;
/*      */         
/* 1286 */         if (name.equals(I18n.format("selectServer.defaultName", new Object[0])))
/*      */         {
/* 1288 */           name = (minecraft.getCurrentServerData()).serverIP;
/*      */         }
/*      */         
/* 1291 */         return name;
/*      */       } 
/* 1293 */     } catch (Exception e) {
/* 1294 */       logger.warn("Exception while getting server name: ", e);
/*      */     } 
/*      */     
/* 1297 */     return "Unidentified Server";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getBaseFolderName() {
/* 1304 */     return getServerName().replaceAll("\\W+", "_");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getWorldFolderName(String theWorldName) {
/* 1311 */     if (theWorldName.isEmpty()) {
/* 1312 */       return baseFolderName;
/*      */     }
/* 1314 */     return String.valueOf(baseFolderName) + " - " + theWorldName;
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
/*      */   public static void saveContainerItems(Container container, IInventory tileEntity, int containerStartIndex) {
/* 1330 */     int containerSize = container.inventorySlots.size();
/* 1331 */     int inventorySize = tileEntity.getSizeInventory();
/* 1332 */     int containerIndex = containerStartIndex;
/* 1333 */     int inventoryIndex = 0;
/*      */     
/* 1335 */     while (containerIndex < containerSize && inventoryIndex < inventorySize) {
/* 1336 */       Slot slot = windowContainer.getSlot(containerIndex);
/* 1337 */       if (slot.getHasStack()) {
/* 1338 */         tileEntity.setInventorySlotContents(inventoryIndex, slot.getStack());
/*      */       }
/* 1340 */       inventoryIndex++;
/* 1341 */       containerIndex++;
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
/*      */   public static void saveInventoryFields(IInventory inventory, IInventory tileEntity) {
/* 1355 */     for (int i = 0; i < inventory.getFieldCount(); i++) {
/* 1356 */       tileEntity.setField(i, inventory.getField(i));
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
/*      */   public static void saveTileEntity(BlockPos pos, TileEntity te) {
/* 1369 */     int chunkX = pos.getX() / 16;
/* 1370 */     int chunkZ = pos.getZ() / 16;
/*      */     
/* 1372 */     ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
/*      */     
/* 1374 */     if (!newTileEntities.containsKey(chunkPos)) {
/* 1375 */       newTileEntities.put(chunkPos, new HashMap<>());
/*      */     }
/* 1377 */     ((Map<BlockPos, TileEntity>)newTileEntities.get(chunkPos)).put(pos, te);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSpigot() {
/* 1387 */     if (thePlayer != null && thePlayer.getServerBrand() != null) {
/* 1388 */       return thePlayer.getServerBrand().toLowerCase().contains("spigot");
/*      */     }
/* 1390 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getDebugInfo() {
/* 1397 */     StringBuilder info = new StringBuilder();
/* 1398 */     info.append("### CORE INFO\n\n");
/* 1399 */     info.append("WDL version: ").append("1.11a-beta1").append('\n');
/* 1400 */     info.append("Launched version: ")
/* 1401 */       .append(Minecraft.getMinecraft().getVersion()).append('\n');
/* 1402 */     info.append("Client brand: ")
/* 1403 */       .append(ClientBrandRetriever.getClientModName()).append('\n');
/* 1404 */     info.append("File location: ");
/*      */     
/*      */     try {
/* 1407 */       String path = (new File(WDL.class.getProtectionDomain()
/* 1408 */           .getCodeSource().getLocation().toURI())).getPath();
/*      */ 
/*      */       
/* 1411 */       String username = System.getProperty("user.name");
/* 1412 */       path = path.replace(username, "<USERNAME>");
/*      */       
/* 1414 */       info.append(path);
/* 1415 */     } catch (Exception e) {
/* 1416 */       info.append("Unknown (").append(e.toString()).append(')');
/*      */     } 
/* 1418 */     info.append("\n\n### EXTENSIONS\n\n");
/* 1419 */     Map<String, WDLApi.ModInfo<?>> extensions = WDLApi.getWDLMods();
/* 1420 */     info.append(extensions.size()).append(" loaded\n");
/* 1421 */     for (Map.Entry<String, WDLApi.ModInfo<?>> e : extensions.entrySet()) {
/* 1422 */       info.append("\n#### ").append(e.getKey()).append("\n\n");
/*      */       try {
/* 1424 */         info.append(((WDLApi.ModInfo)e.getValue()).getInfo());
/* 1425 */       } catch (Exception ex) {
/* 1426 */         info.append("ERROR: ").append(ex).append('\n'); byte b; int i; StackTraceElement[] arrayOfStackTraceElement;
/* 1427 */         for (i = (arrayOfStackTraceElement = ex.getStackTrace()).length, b = 0; b < i; ) { StackTraceElement elm = arrayOfStackTraceElement[b];
/* 1428 */           info.append(elm).append('\n'); b++; }
/*      */       
/*      */       } 
/*      */     } 
/* 1432 */     info.append("\n### STATE\n\n");
/* 1433 */     info.append("minecraft: ").append(minecraft).append('\n');
/* 1434 */     info.append("worldClient: ").append(worldClient).append('\n');
/* 1435 */     info.append("networkManager: ").append(networkManager).append('\n');
/* 1436 */     info.append("thePlayer: ").append(thePlayer).append('\n');
/* 1437 */     info.append("windowContainer: ").append(windowContainer).append('\n');
/* 1438 */     info.append("lastClickedBlock: ").append(lastClickedBlock).append('\n');
/* 1439 */     info.append("lastEntity: ").append(lastEntity).append('\n');
/* 1440 */     info.append("saveHandler: ").append(saveHandler).append('\n');
/* 1441 */     info.append("chunkLoader: ").append(chunkLoader).append('\n');
/* 1442 */     info.append("newTileEntities: ").append(newTileEntities).append('\n');
/* 1443 */     info.append("newEntities: ").append(newEntities).append('\n');
/* 1444 */     info.append("newMapDatas: ").append(newMapDatas).append('\n');
/* 1445 */     info.append("downloading: ").append(downloading).append('\n');
/* 1446 */     info.append("isMultiworld: ").append(isMultiworld).append('\n');
/* 1447 */     info.append("propsFound: ").append(propsFound).append('\n');
/* 1448 */     info.append("startOnChange: ").append(startOnChange).append('\n');
/* 1449 */     info.append("overrideLastModifiedCheck: ")
/* 1450 */       .append(overrideLastModifiedCheck).append('\n');
/* 1451 */     info.append("saving: ").append(saving).append('\n');
/* 1452 */     info.append("worldLoadingDeferred: ").append(worldLoadingDeferred)
/* 1453 */       .append('\n');
/* 1454 */     info.append("worldName: ").append(worldName).append('\n');
/* 1455 */     info.append("baseFolderName: ").append(baseFolderName).append('\n');
/*      */     
/* 1457 */     info.append("### CONNECTED SERVER\n\n");
/* 1458 */     ServerData data = Minecraft.getMinecraft().getCurrentServerData();
/* 1459 */     if (data == null) {
/* 1460 */       info.append("No data\n");
/*      */     } else {
/* 1462 */       info.append("Name: ").append(data.serverName).append('\n');
/* 1463 */       info.append("IP: ").append(data.serverIP).append('\n');
/*      */     } 
/*      */     
/* 1466 */     info.append("\n### PROPERTIES\n\n");
/* 1467 */     info.append("\n#### BASE\n\n");
/* 1468 */     if (baseProps != null) {
/* 1469 */       if (!baseProps.isEmpty()) {
/* 1470 */         for (Map.Entry<Object, Object> e : baseProps.entrySet()) {
/* 1471 */           info.append(e.getKey()).append(": ").append(e.getValue());
/* 1472 */           info.append('\n');
/*      */         } 
/*      */       } else {
/* 1475 */         info.append("empty\n");
/*      */       } 
/*      */     } else {
/* 1478 */       info.append("null\n");
/*      */     } 
/* 1480 */     info.append("\n#### WORLD\n\n");
/* 1481 */     if (worldProps != null) {
/* 1482 */       if (!worldProps.isEmpty()) {
/* 1483 */         for (Map.Entry<Object, Object> e : worldProps.entrySet()) {
/* 1484 */           info.append(e.getKey()).append(": ").append(e.getValue());
/* 1485 */           info.append('\n');
/*      */         } 
/*      */       } else {
/* 1488 */         info.append("empty\n");
/*      */       } 
/*      */     } else {
/* 1491 */       info.append("null\n");
/*      */     } 
/* 1493 */     info.append("\n#### DEFAULT\n\n");
/* 1494 */     if (globalProps != null) {
/* 1495 */       if (!globalProps.isEmpty()) {
/* 1496 */         for (Map.Entry<Object, Object> e : globalProps.entrySet()) {
/* 1497 */           info.append(e.getKey()).append(": ").append(e.getValue());
/* 1498 */           info.append('\n');
/*      */         } 
/*      */       } else {
/* 1501 */         info.append("empty\n");
/*      */       } 
/*      */     } else {
/* 1504 */       info.append("null\n");
/*      */     } 
/*      */     
/* 1507 */     return info.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void crashed(Throwable t, String category) {
/*      */     CrashReport report;
/* 1518 */     if (t instanceof ReportedException) {
/* 1519 */       CrashReport oldReport = (
/* 1520 */         (ReportedException)t).getCrashReport();
/*      */       
/* 1522 */       report = CrashReport.makeCrashReport(oldReport.getCrashCause(), 
/* 1523 */           String.valueOf(category) + " (" + oldReport.getCauseStackTraceOrString() + ")");
/*      */ 
/*      */ 
/*      */       
/*      */       try {
/* 1528 */         List<CrashReportCategory> crashReportSectionsOld = 
/* 1529 */           ReflectionUtils.<List<CrashReportCategory>>stealAndGetField(oldReport, (Class)List.class);
/*      */         
/* 1531 */         List<CrashReportCategory> crashReportSectionsNew = 
/* 1532 */           ReflectionUtils.<List<CrashReportCategory>>stealAndGetField(report, (Class)List.class);
/*      */         
/* 1534 */         crashReportSectionsNew.addAll(crashReportSectionsOld);
/* 1535 */       } catch (Exception e) {
/*      */ 
/*      */         
/* 1538 */         report.makeCategory(
/* 1539 */             "An exception occured while trying to copy the origional categories.")
/*      */           
/* 1541 */           .addCrashSectionThrowable(":(", e);
/*      */       } 
/*      */     } else {
/* 1544 */       report = CrashReport.makeCrashReport(t, category);
/*      */     } 
/* 1546 */     minecraft.crashed(report);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getMinecraftVersion() {
/* 1554 */     return "1.11";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getMinecraftVersionInfo() {
/* 1561 */     String version = getMinecraftVersion();
/*      */     
/* 1563 */     String launchedVersion = Minecraft.getMinecraft().getVersion();
/* 1564 */     String brand = ClientBrandRetriever.getClientModName();
/*      */     
/* 1566 */     return String.format("Minecraft %s (%s/%s)", new Object[] { version, 
/* 1567 */           launchedVersion, brand });
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\WDL.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */