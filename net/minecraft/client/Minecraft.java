/*      */ package net.minecraft.client;
/*      */ 
/*      */ import com.TominoCZ.FBP.FBP;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Multimap;
/*      */ import com.google.common.collect.Queues;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.common.hash.Hashing;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import com.google.common.util.concurrent.ListenableFutureTask;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import com.mojang.authlib.GameProfileRepository;
/*      */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*      */ import com.mojang.authlib.properties.PropertyMap;
/*      */ import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.net.Proxy;
/*      */ import java.net.SocketAddress;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.Collections;
/*      */ import java.util.Date;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.Executors;
/*      */ import java.util.concurrent.FutureTask;
/*      */ import java.util.stream.Collectors;
/*      */ import java.util.stream.Stream;
/*      */ import javax.annotation.Nullable;
/*      */ import javax.imageio.ImageIO;
/*      */ import me.nzxter.bettercraft.BetterCraft;
/*      */ import me.nzxter.bettercraft.utils.SplashProgressUtils;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.audio.MusicTicker;
/*      */ import net.minecraft.client.audio.SoundHandler;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.gui.GuiChat;
/*      */ import net.minecraft.client.gui.GuiControls;
/*      */ import net.minecraft.client.gui.GuiGameOver;
/*      */ import net.minecraft.client.gui.GuiIngame;
/*      */ import net.minecraft.client.gui.GuiIngameMenu;
/*      */ import net.minecraft.client.gui.GuiMainMenu;
/*      */ import net.minecraft.client.gui.GuiMemoryErrorScreen;
/*      */ import net.minecraft.client.gui.GuiNewChat;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.GuiScreenWorking;
/*      */ import net.minecraft.client.gui.GuiSleepMP;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.gui.ScreenChatOptions;
/*      */ import net.minecraft.client.gui.advancements.GuiScreenAdvancements;
/*      */ import net.minecraft.client.gui.chat.NarratorChatListener;
/*      */ import net.minecraft.client.gui.inventory.GuiContainerCreative;
/*      */ import net.minecraft.client.gui.inventory.GuiInventory;
/*      */ import net.minecraft.client.gui.recipebook.RecipeList;
/*      */ import net.minecraft.client.gui.toasts.GuiToast;
/*      */ import net.minecraft.client.main.GameConfiguration;
/*      */ import net.minecraft.client.multiplayer.GuiConnecting;
/*      */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*      */ import net.minecraft.client.multiplayer.ServerData;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.network.NetHandlerLoginClient;
/*      */ import net.minecraft.client.network.NetHandlerPlayClient;
/*      */ import net.minecraft.client.particle.ParticleManager;
/*      */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*      */ import net.minecraft.client.renderer.BufferBuilder;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.ItemRenderer;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderGlobal;
/*      */ import net.minecraft.client.renderer.RenderItem;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.block.model.ModelManager;
/*      */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*      */ import net.minecraft.client.renderer.color.BlockColors;
/*      */ import net.minecraft.client.renderer.color.ItemColors;
/*      */ import net.minecraft.client.renderer.debug.DebugRenderer;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*      */ import net.minecraft.client.renderer.texture.ITickableTextureObject;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.DefaultResourcePack;
/*      */ import net.minecraft.client.resources.FoliageColorReloadListener;
/*      */ import net.minecraft.client.resources.GrassColorReloadListener;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.resources.IReloadableResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.LanguageManager;
/*      */ import net.minecraft.client.resources.ResourcePackRepository;
/*      */ import net.minecraft.client.resources.SimpleReloadableResourceManager;
/*      */ import net.minecraft.client.resources.SkinManager;
/*      */ import net.minecraft.client.resources.data.AnimationMetadataSection;
/*      */ import net.minecraft.client.resources.data.AnimationMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.FontMetadataSection;
/*      */ import net.minecraft.client.resources.data.FontMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.IMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.LanguageMetadataSection;
/*      */ import net.minecraft.client.resources.data.LanguageMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.MetadataSerializer;
/*      */ import net.minecraft.client.resources.data.PackMetadataSection;
/*      */ import net.minecraft.client.resources.data.PackMetadataSectionSerializer;
/*      */ import net.minecraft.client.resources.data.TextureMetadataSection;
/*      */ import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
/*      */ import net.minecraft.client.settings.CreativeSettings;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.client.settings.KeyBinding;
/*      */ import net.minecraft.client.shader.Framebuffer;
/*      */ import net.minecraft.client.tutorial.Tutorial;
/*      */ import net.minecraft.client.util.ISearchTree;
/*      */ import net.minecraft.client.util.ITooltipFlag;
/*      */ import net.minecraft.client.util.RecipeBookClient;
/*      */ import net.minecraft.client.util.SearchTree;
/*      */ import net.minecraft.client.util.SearchTreeManager;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.crash.ICrashReportDetail;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.item.EntityBoat;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.init.Bootstrap;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemMonsterPlacer;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.crafting.IRecipe;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagList;
/*      */ import net.minecraft.nbt.NBTTagString;
/*      */ import net.minecraft.network.EnumConnectionState;
/*      */ import net.minecraft.network.INetHandler;
/*      */ import net.minecraft.network.NetworkManager;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.handshake.client.C00Handshake;
/*      */ import net.minecraft.network.login.client.CPacketLoginStart;
/*      */ import net.minecraft.network.play.client.CPacketPlayerDigging;
/*      */ import net.minecraft.profiler.ISnooperInfo;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.profiler.Snooper;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.integrated.IntegratedServer;
/*      */ import net.minecraft.server.management.PlayerProfileCache;
/*      */ import net.minecraft.stats.RecipeBook;
/*      */ import net.minecraft.stats.StatisticsManager;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntitySkull;
/*      */ import net.minecraft.util.EnumActionResult;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.FrameTimer;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.MinecraftError;
/*      */ import net.minecraft.util.MouseHelper;
/*      */ import net.minecraft.util.MovementInput;
/*      */ import net.minecraft.util.MovementInputFromOptions;
/*      */ import net.minecraft.util.NonNullList;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.ScreenShotHelper;
/*      */ import net.minecraft.util.Session;
/*      */ import net.minecraft.util.Timer;
/*      */ import net.minecraft.util.Util;
/*      */ import net.minecraft.util.datafix.DataFixer;
/*      */ import net.minecraft.util.datafix.DataFixesManager;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.RayTraceResult;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.Style;
/*      */ import net.minecraft.util.text.TextComponentKeybind;
/*      */ import net.minecraft.util.text.TextComponentString;
/*      */ import net.minecraft.util.text.TextComponentTranslation;
/*      */ import net.minecraft.util.text.TextFormatting;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.chunk.storage.AnvilSaveConverter;
/*      */ import net.minecraft.world.storage.ISaveFormat;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import org.apache.commons.io.Charsets;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.apache.commons.lang3.Validate;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.LWJGLException;
/*      */ import org.lwjgl.Sys;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.ContextCapabilities;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.DisplayMode;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.opengl.OpenGLException;
/*      */ import org.lwjgl.opengl.PixelFormat;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ import org.newdawn.slick.SlickException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Minecraft
/*      */   implements IThreadListener, ISnooperInfo
/*      */ {
/*  234 */   private static final Logger LOGGER = LogManager.getLogger();
/*  235 */   private static final ResourceLocation LOCATION_MOJANG_PNG = new ResourceLocation("textures/gui/title/mojang.png");
/*  236 */   public static final boolean IS_RUNNING_ON_MAC = (Util.getOSType() == Util.EnumOS.OSX);
/*      */ 
/*      */   
/*  239 */   public static byte[] memoryReserve = new byte[10485760];
/*  240 */   private static final List<DisplayMode> MAC_DISPLAY_MODES = Lists.newArrayList((Object[])new DisplayMode[] { new DisplayMode(2560, 1600), new DisplayMode(2880, 1800) });
/*      */   
/*      */   private final File fileResourcepacks;
/*      */   
/*      */   private final PropertyMap twitchDetails;
/*      */   
/*      */   private final PropertyMap profileProperties;
/*      */   
/*      */   private ServerData currentServerData;
/*      */   
/*      */   private TextureManager renderEngine;
/*      */   
/*      */   private static Minecraft theMinecraft;
/*      */   
/*      */   private final DataFixer dataFixer;
/*      */   
/*      */   public PlayerControllerMP playerController;
/*      */   
/*      */   private boolean fullscreen;
/*      */   
/*      */   private final boolean enableGLErrorChecking = true;
/*      */   
/*      */   private boolean hasCrashed;
/*      */   
/*      */   private CrashReport crashReporter;
/*      */   public int displayWidth;
/*      */   public int displayHeight;
/*      */   private boolean connectedToRealms;
/*  268 */   public final Timer timer = new Timer(20.0F);
/*      */ 
/*      */   
/*  271 */   private final Snooper usageSnooper = new Snooper("client", this, MinecraftServer.getCurrentTimeMillis());
/*      */   public WorldClient world;
/*      */   public RenderGlobal renderGlobal;
/*      */   private RenderManager renderManager;
/*      */   private RenderItem renderItem;
/*      */   private ItemRenderer itemRenderer;
/*      */   public EntityPlayerSP player;
/*      */   @Nullable
/*      */   private Entity renderViewEntity;
/*      */   public Entity pointedEntity;
/*      */   public ParticleManager effectRenderer;
/*  282 */   private SearchTreeManager field_193995_ae = new SearchTreeManager();
/*      */ 
/*      */   
/*      */   public static Session session;
/*      */ 
/*      */   
/*      */   private boolean isGamePaused;
/*      */ 
/*      */   
/*      */   private float field_193996_ah;
/*      */ 
/*      */   
/*      */   public FontRenderer fontRendererObj;
/*      */ 
/*      */   
/*      */   public FontRenderer mcfontRendererObj;
/*      */   
/*      */   public FontRenderer standardGalacticFontRenderer;
/*      */   
/*      */   @Nullable
/*      */   public GuiScreen currentScreen;
/*      */   
/*      */   public LoadingScreenRenderer loadingScreen;
/*      */   
/*      */   public EntityRenderer entityRenderer;
/*      */   
/*      */   public DebugRenderer debugRenderer;
/*      */   
/*      */   private int leftClickCounter;
/*      */   
/*      */   private final int tempDisplayWidth;
/*      */   
/*      */   private final int tempDisplayHeight;
/*      */   
/*      */   @Nullable
/*      */   private IntegratedServer theIntegratedServer;
/*      */   
/*      */   public GuiIngame ingameGUI;
/*      */   
/*      */   public boolean skipRenderWorld;
/*      */   
/*      */   public RayTraceResult objectMouseOver;
/*      */   
/*      */   public GameSettings gameSettings;
/*      */   
/*      */   public CreativeSettings field_191950_u;
/*      */   
/*      */   public MouseHelper mouseHelper;
/*      */   
/*      */   public final File mcDataDir;
/*      */   
/*      */   private final File fileAssets;
/*      */   
/*      */   private final String launchedVersion;
/*      */   
/*      */   private final String versionType;
/*      */   
/*      */   private final Proxy proxy;
/*      */   
/*      */   private ISaveFormat saveLoader;
/*      */   
/*      */   public static int debugFPS;
/*      */   
/*      */   private int rightClickDelayTimer;
/*      */   
/*      */   private String serverName;
/*      */   
/*      */   private int serverPort;
/*      */   
/*      */   public boolean inGameHasFocus;
/*      */   
/*  353 */   long systemTime = getSystemTime();
/*      */ 
/*      */   
/*      */   private int joinPlayerCounter;
/*      */ 
/*      */   
/*  359 */   public final FrameTimer frameTimer = new FrameTimer();
/*      */ 
/*      */   
/*  362 */   long startNanoTime = System.nanoTime();
/*      */   
/*      */   private final boolean jvm64bit;
/*      */   
/*      */   private final boolean isDemo;
/*      */   @Nullable
/*      */   private NetworkManager myNetworkManager;
/*      */   private boolean integratedServerIsRunning;
/*  370 */   public final Profiler mcProfiler = new Profiler();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  375 */   private long debugCrashKeyPressTime = -1L;
/*      */   private IReloadableResourceManager mcResourceManager;
/*  377 */   private final MetadataSerializer metadataSerializer_ = new MetadataSerializer();
/*  378 */   private final List<IResourcePack> defaultResourcePacks = Lists.newArrayList();
/*      */   private final DefaultResourcePack mcDefaultResourcePack;
/*      */   private ResourcePackRepository mcResourcePackRepository;
/*      */   private LanguageManager mcLanguageManager;
/*      */   private BlockColors blockColors;
/*      */   private ItemColors itemColors;
/*      */   private Framebuffer framebufferMc;
/*      */   private TextureMap textureMapBlocks;
/*      */   private SoundHandler mcSoundHandler;
/*      */   private MusicTicker mcMusicTicker;
/*      */   private ResourceLocation mojangLogo;
/*      */   private final MinecraftSessionService sessionService;
/*      */   private SkinManager skinManager;
/*  391 */   private final Queue<FutureTask<?>> scheduledTasks = Queues.newArrayDeque();
/*  392 */   private final Thread mcThread = Thread.currentThread();
/*      */ 
/*      */   
/*      */   private ModelManager modelManager;
/*      */ 
/*      */   
/*      */   private BlockRendererDispatcher blockRenderDispatcher;
/*      */ 
/*      */   
/*      */   private final GuiToast field_193034_aS;
/*      */ 
/*      */   
/*      */   volatile boolean running = true;
/*      */ 
/*      */   
/*  407 */   public String debug = "";
/*      */   
/*      */   public boolean renderChunksMany = true;
/*      */   
/*  411 */   private long debugUpdateTime = getSystemTime();
/*      */   
/*      */   private int fpsCounter;
/*      */   
/*      */   private boolean actionKeyF3;
/*      */   private final Tutorial field_193035_aW;
/*  417 */   long prevFrameTime = -1L;
/*      */ 
/*      */   
/*  420 */   private String debugProfilerName = "root";
/*      */ 
/*      */   
/*      */   public Minecraft(GameConfiguration gameConfig) {
/*  424 */     theMinecraft = this;
/*  425 */     this.mcDataDir = gameConfig.folderInfo.mcDataDir;
/*  426 */     this.fileAssets = gameConfig.folderInfo.assetsDir;
/*  427 */     this.fileResourcepacks = gameConfig.folderInfo.resourcePacksDir;
/*  428 */     this.launchedVersion = gameConfig.gameInfo.version;
/*  429 */     this.versionType = gameConfig.gameInfo.versionType;
/*  430 */     this.twitchDetails = gameConfig.userInfo.userProperties;
/*  431 */     this.profileProperties = gameConfig.userInfo.profileProperties;
/*  432 */     this.mcDefaultResourcePack = new DefaultResourcePack(gameConfig.folderInfo.getAssetsIndex());
/*  433 */     this.proxy = (gameConfig.userInfo.proxy == null) ? Proxy.NO_PROXY : gameConfig.userInfo.proxy;
/*  434 */     this.sessionService = (new YggdrasilAuthenticationService(this.proxy, UUID.randomUUID().toString())).createMinecraftSessionService();
/*  435 */     session = gameConfig.userInfo.session;
/*  436 */     LOGGER.info("Setting user: {}", session.getUsername());
/*  437 */     LOGGER.debug("(Session ID is {})", session.getSessionID());
/*  438 */     this.isDemo = gameConfig.gameInfo.isDemo;
/*  439 */     this.displayWidth = (gameConfig.displayInfo.width > 0) ? gameConfig.displayInfo.width : 1;
/*  440 */     this.displayHeight = (gameConfig.displayInfo.height > 0) ? gameConfig.displayInfo.height : 1;
/*  441 */     this.tempDisplayWidth = gameConfig.displayInfo.width;
/*  442 */     this.tempDisplayHeight = gameConfig.displayInfo.height;
/*  443 */     this.fullscreen = gameConfig.displayInfo.fullscreen;
/*  444 */     this.jvm64bit = isJvm64bit();
/*  445 */     this.theIntegratedServer = null;
/*      */     
/*  447 */     if (gameConfig.serverInfo.serverName != null) {
/*      */       
/*  449 */       this.serverName = gameConfig.serverInfo.serverName;
/*  450 */       this.serverPort = gameConfig.serverInfo.serverPort;
/*      */     } 
/*      */     
/*  453 */     ImageIO.setUseCache(false);
/*  454 */     Locale.setDefault(Locale.ROOT);
/*  455 */     Bootstrap.register();
/*  456 */     TextComponentKeybind.field_193637_b = KeyBinding::func_193626_b;
/*  457 */     this.dataFixer = DataFixesManager.createFixer();
/*  458 */     this.field_193034_aS = new GuiToast(this);
/*  459 */     this.field_193035_aW = new Tutorial(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public void run() {
/*  464 */     this.running = true;
/*  465 */     while (this.running) {
/*      */       
/*      */       try {
/*  468 */         startGame();
/*      */       }
/*  470 */       catch (Throwable var3) {
/*  471 */         CrashReport var2 = CrashReport.makeCrashReport(var3, "Initializing game");
/*  472 */         var2.makeCategory("Initialization");
/*  473 */         displayCrashReport(addGraphicsAndWorldToCrashReport(var2));
/*      */         return;
/*      */       } 
/*      */       try {
/*      */         do {
/*      */           try {
/*  479 */             runGameLoop();
/*      */           }
/*  481 */           catch (OutOfMemoryError var6) {
/*  482 */             freeMemory();
/*  483 */             displayGuiScreen((GuiScreen)new GuiMemoryErrorScreen());
/*  484 */             System.gc();
/*      */           }
/*  486 */           catch (Throwable throwable) {}
/*      */         
/*      */         }
/*  489 */         while (this.running && !Display.isCloseRequested());
/*  490 */         shutdownMinecraftApplet();
/*      */       }
/*  492 */       catch (MinecraftError minecraftError) {
/*      */       
/*  494 */       } catch (ReportedException var4) {
/*  495 */         addGraphicsAndWorldToCrashReport(var4.getCrashReport());
/*  496 */         freeMemory();
/*  497 */         LOGGER.fatal("Reported exception thrown!", (Throwable)var4);
/*  498 */         displayCrashReport(var4.getCrashReport());
/*      */       }
/*  500 */       catch (Throwable var5) {
/*  501 */         CrashReport var2 = addGraphicsAndWorldToCrashReport(new CrashReport("Unexpected error", var5));
/*  502 */         freeMemory();
/*  503 */         LOGGER.fatal("Unreported exception thrown!", var5);
/*      */       } 
/*      */     } 
/*  506 */     displayGuiScreen((GuiScreen)new GuiMainMenu());
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
/*      */   private void startGame() throws LWJGLException, IOException, SlickException {
/*  518 */     BetterCraft.INSTANCE.onEnable();
/*      */ 
/*      */     
/*  521 */     this.gameSettings = new GameSettings(this, this.mcDataDir);
/*  522 */     this.field_191950_u = new CreativeSettings(this, this.mcDataDir);
/*  523 */     this.defaultResourcePacks.add(this.mcDefaultResourcePack);
/*  524 */     startTimerHackThread();
/*      */     
/*  526 */     if (this.gameSettings.overrideHeight > 0 && this.gameSettings.overrideWidth > 0) {
/*      */       
/*  528 */       this.displayWidth = this.gameSettings.overrideWidth;
/*  529 */       this.displayHeight = this.gameSettings.overrideHeight;
/*      */     } 
/*      */     
/*  532 */     LOGGER.info("LWJGL Version: {}", Sys.getVersion());
/*  533 */     setWindowIcon();
/*  534 */     setInitialDisplayMode();
/*  535 */     createDisplay();
/*  536 */     OpenGlHelper.initializeTextures();
/*  537 */     this.framebufferMc = new Framebuffer(this.displayWidth, this.displayHeight, true);
/*  538 */     this.framebufferMc.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
/*  539 */     registerMetadataSerializers();
/*  540 */     this.mcResourcePackRepository = new ResourcePackRepository(this.fileResourcepacks, new File(this.mcDataDir, "server-resource-packs"), (IResourcePack)this.mcDefaultResourcePack, this.metadataSerializer_, this.gameSettings);
/*  541 */     this.mcResourceManager = (IReloadableResourceManager)new SimpleReloadableResourceManager(this.metadataSerializer_);
/*  542 */     this.mcLanguageManager = new LanguageManager(this.metadataSerializer_, this.gameSettings.language);
/*  543 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.mcLanguageManager);
/*  544 */     refreshResources();
/*  545 */     this.renderEngine = new TextureManager((IResourceManager)this.mcResourceManager);
/*  546 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderEngine);
/*      */ 
/*      */ 
/*      */     
/*  550 */     SplashProgressUtils.drawSplash(getTextureManager());
/*      */ 
/*      */     
/*  553 */     this.skinManager = new SkinManager(this.renderEngine, new File(this.fileAssets, "skins"), this.sessionService);
/*  554 */     this.saveLoader = (ISaveFormat)new AnvilSaveConverter(new File(this.mcDataDir, "saves"), this.dataFixer);
/*  555 */     this.mcSoundHandler = new SoundHandler((IResourceManager)this.mcResourceManager, this.gameSettings);
/*  556 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.mcSoundHandler);
/*  557 */     this.mcMusicTicker = new MusicTicker(this);
/*      */     
/*  559 */     this.fontRendererObj = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii.png"), this.renderEngine, false);
/*      */     
/*  561 */     if (this.gameSettings.language != null) {
/*      */       
/*  563 */       this.fontRendererObj.setUnicodeFlag(isUnicode());
/*  564 */       this.fontRendererObj.setBidiFlag(this.mcLanguageManager.isCurrentLanguageBidirectional());
/*      */     } 
/*      */     
/*  567 */     this.standardGalacticFontRenderer = new FontRenderer(this.gameSettings, new ResourceLocation("textures/font/ascii_sga.png"), this.renderEngine, false);
/*      */     
/*  569 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.fontRendererObj);
/*  570 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.standardGalacticFontRenderer);
/*  571 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)new GrassColorReloadListener());
/*  572 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)new FoliageColorReloadListener());
/*  573 */     this.mouseHelper = new MouseHelper();
/*  574 */     checkGLError("Pre startup");
/*  575 */     GlStateManager.enableTexture2D();
/*  576 */     GlStateManager.shadeModel(7425);
/*  577 */     GlStateManager.clearDepth(1.0D);
/*  578 */     GlStateManager.enableDepth();
/*  579 */     GlStateManager.depthFunc(515);
/*  580 */     GlStateManager.enableAlpha();
/*  581 */     GlStateManager.alphaFunc(516, 0.1F);
/*  582 */     GlStateManager.cullFace(GlStateManager.CullFace.BACK);
/*  583 */     GlStateManager.matrixMode(5889);
/*  584 */     GlStateManager.loadIdentity();
/*  585 */     GlStateManager.matrixMode(5888);
/*  586 */     checkGLError("Startup");
/*  587 */     this.textureMapBlocks = new TextureMap("textures");
/*  588 */     this.textureMapBlocks.setMipmapLevels(this.gameSettings.mipmapLevels);
/*  589 */     this.renderEngine.loadTickableTexture(TextureMap.LOCATION_BLOCKS_TEXTURE, (ITickableTextureObject)this.textureMapBlocks);
/*  590 */     this.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  591 */     this.textureMapBlocks.setBlurMipmapDirect(false, (this.gameSettings.mipmapLevels > 0));
/*      */ 
/*      */     
/*  594 */     SplashProgressUtils.setProgress(2, "Minecraft - ModelManager");
/*  595 */     this.modelManager = new ModelManager(this.textureMapBlocks);
/*      */     
/*  597 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.modelManager);
/*  598 */     this.blockColors = BlockColors.init();
/*  599 */     this.itemColors = ItemColors.init(this.blockColors);
/*  600 */     this.renderItem = new RenderItem(this.renderEngine, this.modelManager, this.itemColors);
/*      */     
/*  602 */     SplashProgressUtils.setProgress(3, "Minecraft - RenderItem");
/*  603 */     this.renderManager = new RenderManager(this.renderEngine, this.renderItem);
/*      */     
/*  605 */     SplashProgressUtils.setProgress(4, "Minecraft - RenderManager");
/*  606 */     this.itemRenderer = new ItemRenderer(this);
/*      */     
/*  608 */     SplashProgressUtils.setProgress(4, "Minecraft - ItemRender");
/*  609 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderItem);
/*      */     
/*  611 */     SplashProgressUtils.setProgress(5, "Minecraft - EntityRender");
/*  612 */     this.entityRenderer = new EntityRenderer(this, (IResourceManager)this.mcResourceManager);
/*      */     
/*  614 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.entityRenderer);
/*  615 */     this.blockRenderDispatcher = new BlockRendererDispatcher(this.modelManager.getBlockModelShapes(), this.blockColors);
/*      */     
/*  617 */     SplashProgressUtils.setProgress(6, "Minecraft - BlockRenderDispatcher");
/*  618 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.blockRenderDispatcher);
/*      */     
/*  620 */     this.renderGlobal = new RenderGlobal(this);
/*      */     
/*  622 */     SplashProgressUtils.setProgress(7, "Minecraft - RenderGlobal");
/*  623 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.renderGlobal);
/*      */ 
/*      */     
/*  626 */     func_193986_ar();
/*  627 */     this.mcResourceManager.registerReloadListener((IResourceManagerReloadListener)this.field_193995_ae);
/*  628 */     GlStateManager.viewport(0, 0, this.displayWidth, this.displayHeight);
/*  629 */     this.effectRenderer = new ParticleManager((World)this.world, this.renderEngine);
/*  630 */     checkGLError("Post startup");
/*  631 */     this.ingameGUI = new GuiIngame(this);
/*      */     
/*  633 */     if (this.serverName != null) {
/*      */       
/*  635 */       displayGuiScreen((GuiScreen)new GuiConnecting((GuiScreen)new GuiMainMenu(), this, this.serverName, this.serverPort));
/*      */     }
/*      */     else {
/*      */       
/*  639 */       displayGuiScreen((GuiScreen)new GuiMainMenu());
/*      */     } 
/*      */     
/*  642 */     this.renderEngine.deleteTexture(this.mojangLogo);
/*  643 */     this.mojangLogo = null;
/*  644 */     this.loadingScreen = new LoadingScreenRenderer(this);
/*  645 */     this.debugRenderer = new DebugRenderer(this);
/*      */     
/*  647 */     if (this.gameSettings.fullScreen && !this.fullscreen)
/*      */     {
/*  649 */       toggleFullscreen();
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  654 */       Display.setVSyncEnabled(this.gameSettings.enableVsync);
/*      */     }
/*  656 */     catch (OpenGLException var2) {
/*      */       
/*  658 */       this.gameSettings.enableVsync = false;
/*  659 */       this.gameSettings.saveOptions();
/*      */     } 
/*      */     
/*  662 */     this.renderGlobal.makeEntityOutlineShader();
/*      */ 
/*      */     
/*  665 */     Display.setTitle(String.valueOf(BetterCraft.clientName) + " " + BetterCraft.clientVersion + " | Minecraft 1.12");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void func_193986_ar() {
/*  671 */     SearchTree<ItemStack> searchtree = new SearchTree(p_193988_0_ -> (List)p_193988_0_.getTooltip(null, (ITooltipFlag)ITooltipFlag.TooltipFlags.NORMAL).stream().map(TextFormatting::getTextWithoutFormattingCodes).map(String::trim).filter(()).collect(Collectors.toList()), p_193985_0_ -> Collections.singleton((ResourceLocation)Item.REGISTRY.getNameForObject(p_193985_0_.getItem())));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  680 */     NonNullList<ItemStack> nonnulllist = NonNullList.func_191196_a();
/*      */     
/*  682 */     for (Item item : Item.REGISTRY)
/*      */     {
/*  684 */       item.getSubItems(CreativeTabs.SEARCH, nonnulllist);
/*      */     }
/*      */     
/*  687 */     nonnulllist.forEach(searchtree::func_194043_a);
/*  688 */     SearchTree<RecipeList> searchtree1 = new SearchTree(p_193990_0_ -> (List)p_193990_0_.func_192711_b().stream().flatMap(()).map(TextFormatting::getTextWithoutFormattingCodes).map(String::trim).filter(()).collect(Collectors.toList()), p_193991_0_ -> (List)p_193991_0_.func_192711_b().stream().map(()).collect(Collectors.toList()));
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
/*  701 */     RecipeBookClient.field_194087_f.forEach(searchtree1::func_194043_a);
/*  702 */     this.field_193995_ae.func_194009_a(SearchTreeManager.field_194011_a, searchtree);
/*  703 */     this.field_193995_ae.func_194009_a(SearchTreeManager.field_194012_b, searchtree1);
/*      */   }
/*      */ 
/*      */   
/*      */   private void registerMetadataSerializers() {
/*  708 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new TextureMetadataSectionSerializer(), TextureMetadataSection.class);
/*  709 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new FontMetadataSectionSerializer(), FontMetadataSection.class);
/*  710 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new AnimationMetadataSectionSerializer(), AnimationMetadataSection.class);
/*  711 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new PackMetadataSectionSerializer(), PackMetadataSection.class);
/*  712 */     this.metadataSerializer_.registerMetadataSectionType((IMetadataSectionSerializer)new LanguageMetadataSectionSerializer(), LanguageMetadataSection.class);
/*      */   }
/*      */ 
/*      */   
/*      */   private void createDisplay() throws LWJGLException {
/*  717 */     Display.setResizable(true);
/*      */ 
/*      */     
/*  720 */     Display.setTitle("Loading...");
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  725 */       Display.create((new PixelFormat()).withDepthBits(24));
/*      */     }
/*  727 */     catch (LWJGLException lwjglexception) {
/*      */       
/*  729 */       LOGGER.error("Couldn't set pixel format", (Throwable)lwjglexception);
/*      */ 
/*      */       
/*      */       try {
/*  733 */         Thread.sleep(1000L);
/*      */       }
/*  735 */       catch (InterruptedException interruptedException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  740 */       if (this.fullscreen)
/*      */       {
/*  742 */         updateDisplayMode();
/*      */       }
/*      */       
/*  745 */       Display.create();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setInitialDisplayMode() throws LWJGLException {
/*  751 */     if (this.fullscreen) {
/*      */       
/*  753 */       Display.setFullscreen(true);
/*  754 */       DisplayMode displaymode = Display.getDisplayMode();
/*  755 */       this.displayWidth = Math.max(1, displaymode.getWidth());
/*  756 */       this.displayHeight = Math.max(1, displaymode.getHeight());
/*      */     }
/*      */     else {
/*      */       
/*  760 */       Display.setDisplayMode(new DisplayMode(this.displayWidth, this.displayHeight));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setWindowIcon() {
/*  766 */     if (Util.getOSType() != Util.EnumOS.OSX) {
/*  767 */       setIcon("textures/icons/icon_16x16.png", "textures/icons/icon_32x32.png");
/*      */     }
/*      */   }
/*      */   
/*      */   public static void setIcon(String icon_16, String icon_32) {
/*  772 */     InputStream inputstream = null;
/*  773 */     InputStream inputstream1 = null;
/*      */     try {
/*  775 */       inputstream = (getMinecraft()).mcDefaultResourcePack.getInputStream(new ResourceLocation(icon_16));
/*  776 */       inputstream1 = (getMinecraft()).mcDefaultResourcePack.getInputStream(new ResourceLocation(icon_32));
/*  777 */       if (inputstream != null && inputstream1 != null) {
/*  778 */         Display.setIcon(new ByteBuffer[] { getMinecraft().readImageToBuffer(inputstream), getMinecraft().readImageToBuffer(inputstream1) });
/*      */       }
/*  780 */     } catch (IOException ioexception) {
/*  781 */       LOGGER.error("Couldn't set icon", ioexception);
/*      */     } finally {
/*  783 */       IOUtils.closeQuietly(inputstream);
/*  784 */       IOUtils.closeQuietly(inputstream1);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isJvm64bit() {
/*  791 */     String[] astring = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" }; byte b; int i;
/*      */     String[] arrayOfString1;
/*  793 */     for (i = (arrayOfString1 = astring).length, b = 0; b < i; ) { String s = arrayOfString1[b];
/*      */       
/*  795 */       String s1 = System.getProperty(s);
/*      */       
/*  797 */       if (s1 != null && s1.contains("64"))
/*      */       {
/*  799 */         return true;
/*      */       }
/*      */       b++; }
/*      */     
/*  803 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public Framebuffer getFramebuffer() {
/*  808 */     return this.framebufferMc;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getVersion() {
/*  813 */     return this.launchedVersion;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getVersionType() {
/*  818 */     return this.versionType;
/*      */   }
/*      */ 
/*      */   
/*      */   private void startTimerHackThread() {
/*  823 */     Thread thread = new Thread("Timer hack thread")
/*      */       {
/*      */         public void run()
/*      */         {
/*  827 */           while (Minecraft.this.running) {
/*      */ 
/*      */             
/*      */             try {
/*  831 */               Thread.sleep(2147483647L);
/*      */             }
/*  833 */             catch (InterruptedException interruptedException) {}
/*      */           } 
/*      */         }
/*      */       };
/*      */ 
/*      */ 
/*      */     
/*  840 */     thread.setDaemon(true);
/*  841 */     thread.start();
/*      */   }
/*      */ 
/*      */   
/*      */   public void crashed(CrashReport crash) {
/*  846 */     this.hasCrashed = true;
/*  847 */     this.crashReporter = crash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayCrashReport(CrashReport crashReportIn) {
/*  855 */     File file1 = new File((getMinecraft()).mcDataDir, "crash-reports");
/*  856 */     File file2 = new File(file1, "crash-" + (new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss")).format(new Date()) + "-client.txt");
/*  857 */     Bootstrap.printToSYSOUT(crashReportIn.getCompleteReport());
/*      */     
/*  859 */     if (crashReportIn.getFile() != null) {
/*      */       
/*  861 */       Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + crashReportIn.getFile());
/*  862 */       System.exit(-1);
/*      */     }
/*  864 */     else if (crashReportIn.saveToFile(file2)) {
/*      */       
/*  866 */       Bootstrap.printToSYSOUT("#@!@# Game crashed! Crash report saved to: #@!@# " + file2.getAbsolutePath());
/*  867 */       System.exit(-1);
/*      */     }
/*      */     else {
/*      */       
/*  871 */       Bootstrap.printToSYSOUT("#@?@# Game crashed! Crash report could not be saved. #@?@#");
/*  872 */       System.exit(-2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isUnicode() {
/*  878 */     return !(!this.mcLanguageManager.isCurrentLocaleUnicode() && !this.gameSettings.forceUnicodeFont);
/*      */   }
/*      */ 
/*      */   
/*      */   public void refreshResources() {
/*  883 */     List<IResourcePack> list = Lists.newArrayList(this.defaultResourcePacks);
/*      */     
/*  885 */     if (this.theIntegratedServer != null)
/*      */     {
/*  887 */       this.theIntegratedServer.func_193031_aM();
/*      */     }
/*      */     
/*  890 */     for (ResourcePackRepository.Entry resourcepackrepository$entry : this.mcResourcePackRepository.getRepositoryEntries())
/*      */     {
/*  892 */       list.add(resourcepackrepository$entry.getResourcePack());
/*      */     }
/*      */     
/*  895 */     if (this.mcResourcePackRepository.getResourcePackInstance() != null)
/*      */     {
/*  897 */       list.add(this.mcResourcePackRepository.getResourcePackInstance());
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  902 */       this.mcResourceManager.reloadResources(list);
/*      */     }
/*  904 */     catch (RuntimeException runtimeexception) {
/*      */       
/*  906 */       LOGGER.info("Caught error stitching, removing all assigned resourcepacks", runtimeexception);
/*  907 */       list.clear();
/*  908 */       list.addAll(this.defaultResourcePacks);
/*  909 */       this.mcResourcePackRepository.setRepositories(Collections.emptyList());
/*  910 */       this.mcResourceManager.reloadResources(list);
/*  911 */       this.gameSettings.resourcePacks.clear();
/*  912 */       this.gameSettings.incompatibleResourcePacks.clear();
/*  913 */       this.gameSettings.saveOptions();
/*      */     } 
/*      */     
/*  916 */     this.mcLanguageManager.parseLanguageMetadata(list);
/*      */     
/*  918 */     if (this.renderGlobal != null)
/*      */     {
/*  920 */       this.renderGlobal.loadRenderers();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private ByteBuffer readImageToBuffer(InputStream imageStream) throws IOException {
/*  926 */     BufferedImage bufferedimage = ImageIO.read(imageStream);
/*  927 */     int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
/*  928 */     ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length); byte b;
/*      */     int i, arrayOfInt1[];
/*  930 */     for (i = (arrayOfInt1 = aint).length, b = 0; b < i; ) { int j = arrayOfInt1[b];
/*      */       
/*  932 */       bytebuffer.putInt(j << 8 | j >> 24 & 0xFF);
/*      */       b++; }
/*      */     
/*  935 */     bytebuffer.flip();
/*  936 */     return bytebuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateDisplayMode() throws LWJGLException {
/*  941 */     Set<DisplayMode> set = Sets.newHashSet();
/*  942 */     Collections.addAll(set, Display.getAvailableDisplayModes());
/*  943 */     DisplayMode displaymode = Display.getDesktopDisplayMode();
/*      */     
/*  945 */     if (!set.contains(displaymode) && Util.getOSType() == Util.EnumOS.OSX)
/*      */     {
/*      */ 
/*      */       
/*  949 */       for (DisplayMode displaymode1 : MAC_DISPLAY_MODES) {
/*      */         
/*  951 */         boolean flag = true;
/*      */         
/*  953 */         for (DisplayMode displaymode2 : set) {
/*      */           
/*  955 */           if (displaymode2.getBitsPerPixel() == 32 && displaymode2.getWidth() == displaymode1.getWidth() && displaymode2.getHeight() == displaymode1.getHeight()) {
/*      */             
/*  957 */             flag = false;
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*  962 */         if (!flag) {
/*      */           
/*  964 */           Iterator<DisplayMode> iterator = set.iterator();
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  969 */           while (iterator.hasNext()) {
/*      */ 
/*      */ 
/*      */ 
/*      */             
/*  974 */             DisplayMode displaymode3 = iterator.next();
/*      */             
/*  976 */             if (displaymode3.getBitsPerPixel() == 32 && displaymode3.getWidth() == displaymode1.getWidth() / 2 && displaymode3.getHeight() == displaymode1.getHeight() / 2)
/*      */             {
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*  982 */               displaymode = displaymode3; } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*  987 */     Display.setDisplayMode(displaymode);
/*  988 */     this.displayWidth = displaymode.getWidth();
/*  989 */     this.displayHeight = displaymode.getHeight();
/*      */   }
/*      */ 
/*      */   
/*      */   private void drawSplashScreen(TextureManager textureManagerInstance) throws LWJGLException {
/*  994 */     ScaledResolution scaledresolution = new ScaledResolution(this);
/*  995 */     int i = scaledresolution.getScaleFactor();
/*  996 */     Framebuffer framebuffer = new Framebuffer(ScaledResolution.getScaledWidth() * i, ScaledResolution.getScaledHeight() * i, true);
/*  997 */     framebuffer.bindFramebuffer(false);
/*  998 */     GlStateManager.matrixMode(5889);
/*  999 */     GlStateManager.loadIdentity();
/* 1000 */     GlStateManager.ortho(0.0D, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight(), 0.0D, 1000.0D, 3000.0D);
/* 1001 */     GlStateManager.matrixMode(5888);
/* 1002 */     GlStateManager.loadIdentity();
/* 1003 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/* 1004 */     GlStateManager.disableLighting();
/* 1005 */     GlStateManager.disableFog();
/* 1006 */     GlStateManager.disableDepth();
/* 1007 */     GlStateManager.enableTexture2D();
/* 1008 */     InputStream inputstream = null;
/*      */ 
/*      */     
/*      */     try {
/* 1012 */       inputstream = this.mcDefaultResourcePack.getInputStream(LOCATION_MOJANG_PNG);
/* 1013 */       this.mojangLogo = textureManagerInstance.getDynamicTextureLocation("logo", new DynamicTexture(ImageIO.read(inputstream)));
/* 1014 */       textureManagerInstance.bindTexture(this.mojangLogo);
/*      */     }
/* 1016 */     catch (IOException ioexception) {
/*      */       
/* 1018 */       LOGGER.error("Unable to load logo: {}", LOCATION_MOJANG_PNG, ioexception);
/*      */     }
/*      */     finally {
/*      */       
/* 1022 */       IOUtils.closeQuietly(inputstream);
/*      */     } 
/*      */     
/* 1025 */     Tessellator tessellator = Tessellator.getInstance();
/* 1026 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 1027 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 1028 */     bufferbuilder.pos(0.0D, this.displayHeight, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/* 1029 */     bufferbuilder.pos(this.displayWidth, this.displayHeight, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/* 1030 */     bufferbuilder.pos(this.displayWidth, 0.0D, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/* 1031 */     bufferbuilder.pos(0.0D, 0.0D, 0.0D).tex(0.0D, 0.0D).color(255, 255, 255, 255).endVertex();
/* 1032 */     tessellator.draw();
/* 1033 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1034 */     int j = 256;
/* 1035 */     int k = 256;
/* 1036 */     draw((ScaledResolution.getScaledWidth() - 256) / 2, (ScaledResolution.getScaledHeight() - 256) / 2, 0, 0, 256, 256, 255, 255, 255, 255);
/* 1037 */     GlStateManager.disableLighting();
/* 1038 */     GlStateManager.disableFog();
/* 1039 */     framebuffer.unbindFramebuffer();
/* 1040 */     framebuffer.framebufferRender(ScaledResolution.getScaledWidth() * i, ScaledResolution.getScaledHeight() * i);
/* 1041 */     GlStateManager.enableAlpha();
/* 1042 */     GlStateManager.alphaFunc(516, 0.1F);
/* 1043 */     updateDisplay();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void draw(int posX, int posY, int texU, int texV, int width, int height, int red, int green, int blue, int alpha) {
/* 1051 */     BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
/* 1052 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 1053 */     float f = 0.00390625F;
/* 1054 */     float f1 = 0.00390625F;
/* 1055 */     bufferbuilder.pos(posX, (posY + height), 0.0D).tex((texU * 0.00390625F), ((texV + height) * 0.00390625F)).color(red, green, blue, alpha).endVertex();
/* 1056 */     bufferbuilder.pos((posX + width), (posY + height), 0.0D).tex(((texU + width) * 0.00390625F), ((texV + height) * 0.00390625F)).color(red, green, blue, alpha).endVertex();
/* 1057 */     bufferbuilder.pos((posX + width), posY, 0.0D).tex(((texU + width) * 0.00390625F), (texV * 0.00390625F)).color(red, green, blue, alpha).endVertex();
/* 1058 */     bufferbuilder.pos(posX, posY, 0.0D).tex((texU * 0.00390625F), (texV * 0.00390625F)).color(red, green, blue, alpha).endVertex();
/* 1059 */     Tessellator.getInstance().draw();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ISaveFormat getSaveLoader() {
/* 1067 */     return this.saveLoader;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayGuiScreen(@Nullable GuiScreen guiScreenIn) {
/*      */     GuiMainMenu guiMainMenu;
/*      */     GuiGameOver guiGameOver;
/* 1075 */     if (this.currentScreen != null)
/*      */     {
/* 1077 */       this.currentScreen.onGuiClosed();
/*      */     }
/*      */     
/* 1080 */     if (guiScreenIn == null && this.world == null) {
/*      */       
/* 1082 */       guiMainMenu = new GuiMainMenu();
/*      */     }
/* 1084 */     else if (guiMainMenu == null && this.player.getHealth() <= 0.0F) {
/*      */       
/* 1086 */       guiGameOver = new GuiGameOver(null);
/*      */     } 
/*      */     
/* 1089 */     if (guiGameOver instanceof GuiMainMenu || guiGameOver instanceof net.minecraft.client.gui.GuiMultiplayer) {
/*      */       
/* 1091 */       this.gameSettings.showDebugInfo = false;
/* 1092 */       this.ingameGUI.getChatGUI().clearChatMessages(true);
/*      */     } 
/*      */     
/* 1095 */     this.currentScreen = (GuiScreen)guiGameOver;
/*      */     
/* 1097 */     if (guiGameOver != null) {
/*      */       
/* 1099 */       setIngameNotInFocus();
/* 1100 */       KeyBinding.unPressAllKeys(); do {
/*      */       
/* 1102 */       } while (Mouse.next());
/*      */ 
/*      */       
/*      */       do {
/*      */       
/* 1107 */       } while (Keyboard.next());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1112 */       ScaledResolution scaledresolution = new ScaledResolution(this);
/* 1113 */       int i = ScaledResolution.getScaledWidth();
/* 1114 */       int j = ScaledResolution.getScaledHeight();
/* 1115 */       guiGameOver.setWorldAndResolution(this, i, j);
/* 1116 */       this.skipRenderWorld = false;
/*      */     }
/*      */     else {
/*      */       
/* 1120 */       this.mcSoundHandler.resumeSounds();
/* 1121 */       setIngameFocus();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkGLError(String message) {
/* 1130 */     int i = GlStateManager.glGetError();
/*      */     
/* 1132 */     if (i != 0) {
/*      */       
/* 1134 */       String s = GLU.gluErrorString(i);
/* 1135 */       LOGGER.error("########## GL ERROR ##########");
/* 1136 */       LOGGER.error("@ {}", message);
/* 1137 */       LOGGER.error("{}: {}", Integer.valueOf(i), s);
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
/*      */   public void shutdownMinecraftApplet() {
/* 1149 */     BetterCraft.INSTANCE.onDisable();
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1154 */       LOGGER.info("Stopping!");
/*      */ 
/*      */       
/*      */       try {
/* 1158 */         loadWorld(null);
/*      */       }
/* 1160 */       catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1165 */       this.mcSoundHandler.unloadSounds();
/*      */     }
/*      */     finally {
/*      */       
/* 1169 */       Display.destroy();
/*      */       
/* 1171 */       if (!this.hasCrashed)
/*      */       {
/* 1173 */         System.exit(0);
/*      */       }
/*      */     } 
/*      */     
/* 1177 */     System.gc();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void runGameLoop() throws IOException {
/* 1185 */     long i = System.nanoTime();
/* 1186 */     this.mcProfiler.startSection("root");
/*      */     
/* 1188 */     if (Display.isCreated() && Display.isCloseRequested())
/*      */     {
/* 1190 */       shutdown();
/*      */     }
/*      */     
/* 1193 */     this.timer.updateTimer();
/* 1194 */     this.mcProfiler.startSection("scheduledExecutables");
/*      */     
/* 1196 */     synchronized (this.scheduledTasks) {
/*      */       
/* 1198 */       while (!this.scheduledTasks.isEmpty())
/*      */       {
/* 1200 */         Util.runTask(this.scheduledTasks.poll(), LOGGER);
/*      */       }
/*      */     } 
/*      */     
/* 1204 */     this.mcProfiler.endSection();
/* 1205 */     long l = System.nanoTime();
/* 1206 */     this.mcProfiler.startSection("tick");
/*      */     
/* 1208 */     for (int j = 0; j < Math.min(10, this.timer.elapsedTicks); j++)
/*      */     {
/* 1210 */       runTick();
/*      */     }
/*      */     
/* 1213 */     this.mcProfiler.endStartSection("preRenderErrors");
/* 1214 */     long i1 = System.nanoTime() - l;
/* 1215 */     checkGLError("Pre render");
/* 1216 */     this.mcProfiler.endStartSection("sound");
/* 1217 */     this.mcSoundHandler.setListener((EntityPlayer)this.player, this.timer.field_194147_b);
/* 1218 */     this.mcProfiler.endSection();
/* 1219 */     this.mcProfiler.startSection("render");
/* 1220 */     GlStateManager.pushMatrix();
/* 1221 */     GlStateManager.clear(16640);
/* 1222 */     this.framebufferMc.bindFramebuffer(true);
/* 1223 */     this.mcProfiler.startSection("display");
/* 1224 */     GlStateManager.enableTexture2D();
/* 1225 */     this.mcProfiler.endSection();
/*      */     
/* 1227 */     if (!this.skipRenderWorld) {
/*      */       
/* 1229 */       this.mcProfiler.endStartSection("gameRenderer");
/* 1230 */       this.entityRenderer.updateCameraAndRender(this.isGamePaused ? this.field_193996_ah : this.timer.field_194147_b, i);
/* 1231 */       this.mcProfiler.endStartSection("toasts");
/* 1232 */       this.field_193034_aS.func_191783_a(new ScaledResolution(this));
/* 1233 */       this.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1236 */     this.mcProfiler.endSection();
/*      */     
/* 1238 */     if (this.gameSettings.showDebugInfo && this.gameSettings.showDebugProfilerChart && !this.gameSettings.hideGUI) {
/*      */       
/* 1240 */       if (!this.mcProfiler.profilingEnabled)
/*      */       {
/* 1242 */         this.mcProfiler.clearProfiling();
/*      */       }
/*      */       
/* 1245 */       this.mcProfiler.profilingEnabled = true;
/* 1246 */       displayDebugInfo(i1);
/*      */     }
/*      */     else {
/*      */       
/* 1250 */       this.mcProfiler.profilingEnabled = false;
/* 1251 */       this.prevFrameTime = System.nanoTime();
/*      */     } 
/*      */     
/* 1254 */     this.framebufferMc.unbindFramebuffer();
/* 1255 */     GlStateManager.popMatrix();
/* 1256 */     GlStateManager.pushMatrix();
/* 1257 */     this.framebufferMc.framebufferRender(this.displayWidth, this.displayHeight);
/* 1258 */     GlStateManager.popMatrix();
/* 1259 */     GlStateManager.pushMatrix();
/* 1260 */     this.entityRenderer.renderStreamIndicator(this.timer.field_194147_b);
/* 1261 */     GlStateManager.popMatrix();
/* 1262 */     this.mcProfiler.startSection("root");
/* 1263 */     updateDisplay();
/* 1264 */     Thread.yield();
/* 1265 */     checkGLError("Post render");
/* 1266 */     this.fpsCounter++;
/* 1267 */     boolean flag = (isSingleplayer() && this.currentScreen != null && this.currentScreen.doesGuiPauseGame() && !this.theIntegratedServer.getPublic());
/*      */     
/* 1269 */     if (this.isGamePaused != flag) {
/*      */       
/* 1271 */       if (this.isGamePaused) {
/*      */         
/* 1273 */         this.field_193996_ah = this.timer.field_194147_b;
/*      */       }
/*      */       else {
/*      */         
/* 1277 */         this.timer.field_194147_b = this.field_193996_ah;
/*      */       } 
/*      */       
/* 1280 */       this.isGamePaused = flag;
/*      */     } 
/*      */     
/* 1283 */     long k = System.nanoTime();
/* 1284 */     this.frameTimer.addFrame(k - this.startNanoTime);
/* 1285 */     this.startNanoTime = k;
/*      */     
/* 1287 */     while (getSystemTime() >= this.debugUpdateTime + 1000L) {
/*      */       
/* 1289 */       debugFPS = this.fpsCounter;
/* 1290 */       this.debug = String.format("%d fps (%d chunk update%s) T: %s%s%s%s%s", new Object[] { Integer.valueOf(debugFPS), Integer.valueOf(RenderChunk.renderChunksUpdated), (RenderChunk.renderChunksUpdated == 1) ? "" : "s", (this.gameSettings.limitFramerate == GameSettings.Options.FRAMERATE_LIMIT.getValueMax()) ? "inf" : Integer.valueOf(this.gameSettings.limitFramerate), this.gameSettings.enableVsync ? " vsync" : "", this.gameSettings.fancyGraphics ? "" : " fast", (this.gameSettings.clouds == 0) ? "" : ((this.gameSettings.clouds == 1) ? " fast-clouds" : " fancy-clouds"), OpenGlHelper.useVbo() ? " vbo" : "" });
/* 1291 */       RenderChunk.renderChunksUpdated = 0;
/* 1292 */       this.debugUpdateTime += 1000L;
/* 1293 */       this.fpsCounter = 0;
/* 1294 */       this.usageSnooper.addMemoryStatsToSnooper();
/*      */       
/* 1296 */       if (!this.usageSnooper.isSnooperRunning())
/*      */       {
/* 1298 */         this.usageSnooper.startSnooper();
/*      */       }
/*      */     } 
/*      */     
/* 1302 */     if (isFramerateLimitBelowMax()) {
/*      */       
/* 1304 */       this.mcProfiler.startSection("fpslimit_wait");
/* 1305 */       Display.sync(getLimitFramerate());
/* 1306 */       this.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1309 */     this.mcProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateDisplay() {
/* 1314 */     this.mcProfiler.startSection("display_update");
/* 1315 */     Display.update();
/* 1316 */     this.mcProfiler.endSection();
/* 1317 */     checkWindowResize();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void checkWindowResize() {
/* 1322 */     if (!this.fullscreen && Display.wasResized()) {
/*      */       
/* 1324 */       int i = this.displayWidth;
/* 1325 */       int j = this.displayHeight;
/* 1326 */       this.displayWidth = Display.getWidth();
/* 1327 */       this.displayHeight = Display.getHeight();
/*      */       
/* 1329 */       if (this.displayWidth != i || this.displayHeight != j) {
/*      */         
/* 1331 */         if (this.displayWidth <= 0)
/*      */         {
/* 1333 */           this.displayWidth = 1;
/*      */         }
/*      */         
/* 1336 */         if (this.displayHeight <= 0)
/*      */         {
/* 1338 */           this.displayHeight = 1;
/*      */         }
/*      */         
/* 1341 */         resize(this.displayWidth, this.displayHeight);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLimitFramerate() {
/* 1348 */     return (this.world == null && this.currentScreen != null) ? 30 : this.gameSettings.limitFramerate;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFramerateLimitBelowMax() {
/* 1353 */     return (getLimitFramerate() < GameSettings.Options.FRAMERATE_LIMIT.getValueMax());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void freeMemory() {
/*      */     try {
/* 1360 */       memoryReserve = new byte[0];
/* 1361 */       this.renderGlobal.deleteAllDisplayLists();
/*      */     }
/* 1363 */     catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/* 1370 */       System.gc();
/* 1371 */       loadWorld(null);
/*      */     }
/* 1373 */     catch (Throwable throwable) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1378 */     System.gc();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateDebugProfilerName(int keyCount) {
/* 1386 */     List<Profiler.Result> list = this.mcProfiler.getProfilingData(this.debugProfilerName);
/*      */     
/* 1388 */     if (!list.isEmpty()) {
/*      */       
/* 1390 */       Profiler.Result profiler$result = list.remove(0);
/*      */       
/* 1392 */       if (keyCount == 0) {
/*      */         
/* 1394 */         if (!profiler$result.profilerName.isEmpty())
/*      */         {
/* 1396 */           int i = this.debugProfilerName.lastIndexOf('.');
/*      */           
/* 1398 */           if (i >= 0)
/*      */           {
/* 1400 */             this.debugProfilerName = this.debugProfilerName.substring(0, i);
/*      */           }
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1406 */         keyCount--;
/*      */         
/* 1408 */         if (keyCount < list.size() && !"unspecified".equals(((Profiler.Result)list.get(keyCount)).profilerName)) {
/*      */           
/* 1410 */           if (!this.debugProfilerName.isEmpty())
/*      */           {
/* 1412 */             this.debugProfilerName = String.valueOf(this.debugProfilerName) + ".";
/*      */           }
/*      */           
/* 1415 */           this.debugProfilerName = String.valueOf(this.debugProfilerName) + ((Profiler.Result)list.get(keyCount)).profilerName;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void displayDebugInfo(long elapsedTicksTime) {
/* 1426 */     if (this.mcProfiler.profilingEnabled) {
/*      */       
/* 1428 */       List<Profiler.Result> list = this.mcProfiler.getProfilingData(this.debugProfilerName);
/* 1429 */       Profiler.Result profiler$result = list.remove(0);
/* 1430 */       GlStateManager.clear(256);
/* 1431 */       GlStateManager.matrixMode(5889);
/* 1432 */       GlStateManager.enableColorMaterial();
/* 1433 */       GlStateManager.loadIdentity();
/* 1434 */       GlStateManager.ortho(0.0D, this.displayWidth, this.displayHeight, 0.0D, 1000.0D, 3000.0D);
/* 1435 */       GlStateManager.matrixMode(5888);
/* 1436 */       GlStateManager.loadIdentity();
/* 1437 */       GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/* 1438 */       GlStateManager.glLineWidth(1.0F);
/* 1439 */       GlStateManager.disableTexture2D();
/* 1440 */       Tessellator tessellator = Tessellator.getInstance();
/* 1441 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 1442 */       int i = 160;
/* 1443 */       int j = this.displayWidth - 160 - 10;
/* 1444 */       int k = this.displayHeight - 320;
/* 1445 */       GlStateManager.enableBlend();
/* 1446 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 1447 */       bufferbuilder.pos((j - 176.0F), (k - 96.0F - 16.0F), 0.0D).color(200, 0, 0, 0).endVertex();
/* 1448 */       bufferbuilder.pos((j - 176.0F), (k + 320), 0.0D).color(200, 0, 0, 0).endVertex();
/* 1449 */       bufferbuilder.pos((j + 176.0F), (k + 320), 0.0D).color(200, 0, 0, 0).endVertex();
/* 1450 */       bufferbuilder.pos((j + 176.0F), (k - 96.0F - 16.0F), 0.0D).color(200, 0, 0, 0).endVertex();
/* 1451 */       tessellator.draw();
/* 1452 */       GlStateManager.disableBlend();
/* 1453 */       double d0 = 0.0D;
/*      */       
/* 1455 */       for (int l = 0; l < list.size(); l++) {
/*      */         
/* 1457 */         Profiler.Result profiler$result1 = list.get(l);
/* 1458 */         int i1 = MathHelper.floor(profiler$result1.usePercentage / 4.0D) + 1;
/* 1459 */         bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
/* 1460 */         int j1 = profiler$result1.getColor();
/* 1461 */         int k1 = j1 >> 16 & 0xFF;
/* 1462 */         int l1 = j1 >> 8 & 0xFF;
/* 1463 */         int i2 = j1 & 0xFF;
/* 1464 */         bufferbuilder.pos(j, k, 0.0D).color(k1, l1, i2, 255).endVertex();
/*      */         
/* 1466 */         for (int j2 = i1; j2 >= 0; j2--) {
/*      */           
/* 1468 */           float f = (float)((d0 + profiler$result1.usePercentage * j2 / i1) * 6.283185307179586D / 100.0D);
/* 1469 */           float f1 = MathHelper.sin(f) * 160.0F;
/* 1470 */           float f2 = MathHelper.cos(f) * 160.0F * 0.5F;
/* 1471 */           bufferbuilder.pos((j + f1), (k - f2), 0.0D).color(k1, l1, i2, 255).endVertex();
/*      */         } 
/*      */         
/* 1474 */         tessellator.draw();
/* 1475 */         bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
/*      */         
/* 1477 */         for (int i3 = i1; i3 >= 0; i3--) {
/*      */           
/* 1479 */           float f3 = (float)((d0 + profiler$result1.usePercentage * i3 / i1) * 6.283185307179586D / 100.0D);
/* 1480 */           float f4 = MathHelper.sin(f3) * 160.0F;
/* 1481 */           float f5 = MathHelper.cos(f3) * 160.0F * 0.5F;
/* 1482 */           bufferbuilder.pos((j + f4), (k - f5), 0.0D).color(k1 >> 1, l1 >> 1, i2 >> 1, 255).endVertex();
/* 1483 */           bufferbuilder.pos((j + f4), (k - f5 + 10.0F), 0.0D).color(k1 >> 1, l1 >> 1, i2 >> 1, 255).endVertex();
/*      */         } 
/*      */         
/* 1486 */         tessellator.draw();
/* 1487 */         d0 += profiler$result1.usePercentage;
/*      */       } 
/*      */       
/* 1490 */       DecimalFormat decimalformat = new DecimalFormat("##0.00");
/* 1491 */       GlStateManager.enableTexture2D();
/* 1492 */       String s = "";
/*      */       
/* 1494 */       if (!"unspecified".equals(profiler$result.profilerName))
/*      */       {
/* 1496 */         s = String.valueOf(s) + "[0] ";
/*      */       }
/*      */       
/* 1499 */       if (profiler$result.profilerName.isEmpty()) {
/*      */         
/* 1501 */         s = String.valueOf(s) + "ROOT ";
/*      */       }
/*      */       else {
/*      */         
/* 1505 */         s = String.valueOf(s) + profiler$result.profilerName + ' ';
/*      */       } 
/*      */       
/* 1508 */       int l2 = 16777215;
/* 1509 */       this.fontRendererObj.drawStringWithShadow(s, (j - 160), (k - 80 - 16), 16777215);
/* 1510 */       s = String.valueOf(decimalformat.format(profiler$result.totalUsePercentage)) + "%";
/* 1511 */       this.fontRendererObj.drawStringWithShadow(s, (j + 160 - this.fontRendererObj.getStringWidth(s)), (k - 80 - 16), 16777215);
/*      */       
/* 1513 */       for (int k2 = 0; k2 < list.size(); k2++) {
/*      */         
/* 1515 */         Profiler.Result profiler$result2 = list.get(k2);
/* 1516 */         StringBuilder stringbuilder = new StringBuilder();
/*      */         
/* 1518 */         if ("unspecified".equals(profiler$result2.profilerName)) {
/*      */           
/* 1520 */           stringbuilder.append("[?] ");
/*      */         }
/*      */         else {
/*      */           
/* 1524 */           stringbuilder.append("[").append(k2 + 1).append("] ");
/*      */         } 
/*      */         
/* 1527 */         String s1 = stringbuilder.append(profiler$result2.profilerName).toString();
/* 1528 */         this.fontRendererObj.drawStringWithShadow(s1, (j - 160), (k + 80 + k2 * 8 + 20), profiler$result2.getColor());
/* 1529 */         s1 = String.valueOf(decimalformat.format(profiler$result2.usePercentage)) + "%";
/* 1530 */         this.fontRendererObj.drawStringWithShadow(s1, (j + 160 - 50 - this.fontRendererObj.getStringWidth(s1)), (k + 80 + k2 * 8 + 20), profiler$result2.getColor());
/* 1531 */         s1 = String.valueOf(decimalformat.format(profiler$result2.totalUsePercentage)) + "%";
/* 1532 */         this.fontRendererObj.drawStringWithShadow(s1, (j + 160 - this.fontRendererObj.getStringWidth(s1)), (k + 80 + k2 * 8 + 20), profiler$result2.getColor());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void shutdown() {
/* 1542 */     this.running = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIngameFocus() {
/* 1551 */     if (Display.isActive())
/*      */     {
/* 1553 */       if (!this.inGameHasFocus) {
/*      */         
/* 1555 */         if (!IS_RUNNING_ON_MAC)
/*      */         {
/* 1557 */           KeyBinding.updateKeyBindState();
/*      */         }
/*      */         
/* 1560 */         this.inGameHasFocus = true;
/* 1561 */         this.mouseHelper.grabMouseCursor();
/* 1562 */         displayGuiScreen(null);
/* 1563 */         this.leftClickCounter = 10000;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIngameNotInFocus() {
/* 1573 */     if (this.inGameHasFocus) {
/*      */       
/* 1575 */       this.inGameHasFocus = false;
/* 1576 */       this.mouseHelper.ungrabMouseCursor();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void displayInGameMenu() {
/* 1585 */     if (this.currentScreen == null) {
/*      */       
/* 1587 */       displayGuiScreen((GuiScreen)new GuiIngameMenu());
/*      */       
/* 1589 */       if (isSingleplayer() && !this.theIntegratedServer.getPublic())
/*      */       {
/* 1591 */         this.mcSoundHandler.pauseSounds();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void sendClickBlockToController(boolean leftClick) {
/* 1598 */     if (!leftClick)
/*      */     {
/* 1600 */       this.leftClickCounter = 0;
/*      */     }
/*      */     
/* 1603 */     if (this.leftClickCounter <= 0 && !this.player.isHandActive())
/*      */     {
/* 1605 */       if (leftClick && this.objectMouseOver != null && this.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
/*      */         
/* 1607 */         BlockPos blockpos = this.objectMouseOver.getBlockPos();
/*      */         
/* 1609 */         if (this.world.getBlockState(blockpos).getMaterial() != Material.AIR && this.playerController.onPlayerDamageBlock(blockpos, this.objectMouseOver.sideHit))
/*      */         {
/* 1611 */           this.effectRenderer.addBlockHitEffects(blockpos, this.objectMouseOver.sideHit);
/* 1612 */           this.player.swingArm(EnumHand.MAIN_HAND);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1617 */         this.playerController.resetBlockRemoving();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void clickMouse() {
/* 1624 */     if (this.leftClickCounter <= 0)
/*      */     {
/* 1626 */       if (this.objectMouseOver == null) {
/*      */         
/* 1628 */         LOGGER.error("Null returned as 'hitResult', this shouldn't happen!");
/*      */         
/* 1630 */         if (this.playerController.isNotCreative())
/*      */         {
/* 1632 */           this.leftClickCounter = 10;
/*      */         }
/*      */       }
/* 1635 */       else if (!this.player.isRowingBoat()) {
/*      */         BlockPos blockpos;
/* 1637 */         switch (this.objectMouseOver.typeOfHit) {
/*      */           
/*      */           case ENTITY:
/* 1640 */             this.playerController.attackEntity((EntityPlayer)this.player, this.objectMouseOver.entityHit);
/*      */             break;
/*      */           
/*      */           case null:
/* 1644 */             blockpos = this.objectMouseOver.getBlockPos();
/*      */             
/* 1646 */             if (this.world.getBlockState(blockpos).getMaterial() != Material.AIR) {
/*      */               
/* 1648 */               this.playerController.clickBlock(blockpos, this.objectMouseOver.sideHit);
/*      */               break;
/*      */             } 
/*      */           
/*      */           case MISS:
/* 1653 */             if (this.playerController.isNotCreative())
/*      */             {
/* 1655 */               this.leftClickCounter = 10;
/*      */             }
/*      */             
/* 1658 */             this.player.resetCooldown();
/*      */             break;
/*      */         } 
/* 1661 */         this.player.swingArm(EnumHand.MAIN_HAND);
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
/*      */   private void rightClickMouse() {
/* 1673 */     if (!this.playerController.getIsHittingBlock()) {
/*      */       
/* 1675 */       this.rightClickDelayTimer = 4;
/*      */       
/* 1677 */       if (!this.player.isRowingBoat()) {
/*      */         
/* 1679 */         if (this.objectMouseOver == null)
/*      */         {
/* 1681 */           LOGGER.warn("Null returned as 'hitResult', this shouldn't happen!"); }  byte b;
/*      */         int i;
/*      */         EnumHand[] arrayOfEnumHand;
/* 1684 */         for (i = (arrayOfEnumHand = EnumHand.values()).length, b = 0; b < i; ) { EnumHand enumhand = arrayOfEnumHand[b];
/*      */           
/* 1686 */           ItemStack itemstack = this.player.getHeldItem(enumhand);
/*      */           
/* 1688 */           if (this.objectMouseOver != null) {
/*      */             BlockPos blockpos;
/* 1690 */             switch (this.objectMouseOver.typeOfHit) {
/*      */               
/*      */               case ENTITY:
/* 1693 */                 if (this.playerController.interactWithEntity((EntityPlayer)this.player, this.objectMouseOver.entityHit, this.objectMouseOver, enumhand) == EnumActionResult.SUCCESS) {
/*      */                   return;
/*      */                 }
/*      */ 
/*      */                 
/* 1698 */                 if (this.playerController.interactWithEntity((EntityPlayer)this.player, this.objectMouseOver.entityHit, enumhand) == EnumActionResult.SUCCESS) {
/*      */                   return;
/*      */                 }
/*      */                 break;
/*      */ 
/*      */ 
/*      */               
/*      */               case null:
/* 1706 */                 blockpos = this.objectMouseOver.getBlockPos();
/*      */                 
/* 1708 */                 if (this.world.getBlockState(blockpos).getMaterial() != Material.AIR) {
/*      */                   
/* 1710 */                   int j = itemstack.func_190916_E();
/* 1711 */                   EnumActionResult enumactionresult = this.playerController.processRightClickBlock(this.player, this.world, blockpos, this.objectMouseOver.sideHit, this.objectMouseOver.hitVec, enumhand);
/*      */                   
/* 1713 */                   if (enumactionresult == EnumActionResult.SUCCESS) {
/*      */                     
/* 1715 */                     this.player.swingArm(enumhand);
/*      */                     
/* 1717 */                     if (!itemstack.func_190926_b() && (itemstack.func_190916_E() != j || this.playerController.isInCreativeMode()))
/*      */                     {
/* 1719 */                       this.entityRenderer.itemRenderer.resetEquippedProgress(enumhand);
/*      */                     }
/*      */                     return;
/*      */                   } 
/*      */                 } 
/*      */                 break;
/*      */             } 
/*      */           
/*      */           } 
/* 1728 */           if (!itemstack.func_190926_b() && this.playerController.processRightClick((EntityPlayer)this.player, (World)this.world, enumhand) == EnumActionResult.SUCCESS) {
/*      */             
/* 1730 */             this.entityRenderer.itemRenderer.resetEquippedProgress(enumhand);
/*      */             return;
/*      */           } 
/*      */           b++; }
/*      */       
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void toggleFullscreen() {
/*      */     try {
/* 1745 */       this.fullscreen = !this.fullscreen;
/* 1746 */       this.gameSettings.fullScreen = this.fullscreen;
/*      */       
/* 1748 */       if (this.fullscreen) {
/*      */         
/* 1750 */         updateDisplayMode();
/* 1751 */         this.displayWidth = Display.getDisplayMode().getWidth();
/* 1752 */         this.displayHeight = Display.getDisplayMode().getHeight();
/*      */         
/* 1754 */         if (this.displayWidth <= 0)
/*      */         {
/* 1756 */           this.displayWidth = 1;
/*      */         }
/*      */         
/* 1759 */         if (this.displayHeight <= 0)
/*      */         {
/* 1761 */           this.displayHeight = 1;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1766 */         Display.setDisplayMode(new DisplayMode(this.tempDisplayWidth, this.tempDisplayHeight));
/* 1767 */         this.displayWidth = this.tempDisplayWidth;
/* 1768 */         this.displayHeight = this.tempDisplayHeight;
/*      */         
/* 1770 */         if (this.displayWidth <= 0)
/*      */         {
/* 1772 */           this.displayWidth = 1;
/*      */         }
/*      */         
/* 1775 */         if (this.displayHeight <= 0)
/*      */         {
/* 1777 */           this.displayHeight = 1;
/*      */         }
/*      */       } 
/*      */       
/* 1781 */       if (this.currentScreen != null) {
/*      */         
/* 1783 */         resize(this.displayWidth, this.displayHeight);
/*      */       }
/*      */       else {
/*      */         
/* 1787 */         updateFramebufferSize();
/*      */       } 
/*      */       
/* 1790 */       Display.setFullscreen(this.fullscreen);
/* 1791 */       Display.setVSyncEnabled(this.gameSettings.enableVsync);
/* 1792 */       updateDisplay();
/*      */     }
/* 1794 */     catch (Exception exception) {
/*      */       
/* 1796 */       LOGGER.error("Couldn't toggle fullscreen", exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resize(int width, int height) {
/* 1805 */     this.displayWidth = Math.max(1, width);
/* 1806 */     this.displayHeight = Math.max(1, height);
/*      */     
/* 1808 */     if (this.currentScreen != null) {
/*      */       
/* 1810 */       ScaledResolution scaledresolution = new ScaledResolution(this);
/* 1811 */       this.currentScreen.onResize(this, ScaledResolution.getScaledWidth(), ScaledResolution.getScaledHeight());
/*      */     } 
/*      */     
/* 1814 */     this.loadingScreen = new LoadingScreenRenderer(this);
/* 1815 */     updateFramebufferSize();
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateFramebufferSize() {
/* 1820 */     this.framebufferMc.createBindFramebuffer(this.displayWidth, this.displayHeight);
/*      */     
/* 1822 */     if (this.entityRenderer != null)
/*      */     {
/* 1824 */       this.entityRenderer.updateShaderGroupSize(this.displayWidth, this.displayHeight);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public MusicTicker getMusicTicker() {
/* 1833 */     return this.mcMusicTicker;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void runTick() throws IOException {
/* 1841 */     if (this.rightClickDelayTimer > 0)
/*      */     {
/* 1843 */       this.rightClickDelayTimer--;
/*      */     }
/*      */     
/* 1846 */     this.mcProfiler.startSection("gui");
/*      */     
/* 1848 */     if (!this.isGamePaused)
/*      */     {
/* 1850 */       this.ingameGUI.updateTick();
/*      */     }
/*      */     
/* 1853 */     this.mcProfiler.endSection();
/* 1854 */     this.entityRenderer.getMouseOver(1.0F);
/* 1855 */     this.field_193035_aW.func_193297_a(this.world, this.objectMouseOver);
/* 1856 */     this.mcProfiler.startSection("gameMode");
/*      */     
/* 1858 */     if (!this.isGamePaused && this.world != null)
/*      */     {
/* 1860 */       this.playerController.updateController();
/*      */     }
/*      */     
/* 1863 */     this.mcProfiler.endStartSection("textures");
/*      */     
/* 1865 */     if (this.world != null)
/*      */     {
/* 1867 */       this.renderEngine.tick();
/*      */     }
/*      */     
/* 1870 */     if (this.currentScreen == null && this.player != null) {
/*      */       
/* 1872 */       if (this.player.getHealth() <= 0.0F && !(this.currentScreen instanceof GuiGameOver))
/*      */       {
/* 1874 */         displayGuiScreen(null);
/*      */       }
/* 1876 */       else if (this.player.isPlayerSleeping() && this.world != null)
/*      */       {
/* 1878 */         displayGuiScreen((GuiScreen)new GuiSleepMP());
/*      */       }
/*      */     
/* 1881 */     } else if (this.currentScreen != null && this.currentScreen instanceof GuiSleepMP && !this.player.isPlayerSleeping()) {
/*      */       
/* 1883 */       displayGuiScreen(null);
/*      */     } 
/*      */     
/* 1886 */     if (this.currentScreen != null)
/*      */     {
/* 1888 */       this.leftClickCounter = 10000;
/*      */     }
/*      */     
/* 1891 */     if (this.currentScreen != null) {
/*      */ 
/*      */       
/*      */       try {
/* 1895 */         this.currentScreen.handleInput();
/*      */       }
/* 1897 */       catch (Throwable throwable1) {
/*      */         
/* 1899 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Updating screen events");
/* 1900 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Affected screen");
/* 1901 */         crashreportcategory.setDetail("Screen name", new ICrashReportDetail<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/* 1905 */                 return Minecraft.this.currentScreen.getClass().getCanonicalName();
/*      */               }
/*      */             });
/* 1908 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/* 1911 */       if (this.currentScreen != null) {
/*      */         
/*      */         try {
/*      */           
/* 1915 */           this.currentScreen.updateScreen();
/*      */         }
/* 1917 */         catch (Throwable throwable) {
/*      */           
/* 1919 */           CrashReport crashreport1 = CrashReport.makeCrashReport(throwable, "Ticking screen");
/* 1920 */           CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Affected screen");
/* 1921 */           crashreportcategory1.setDetail("Screen name", new ICrashReportDetail<String>()
/*      */               {
/*      */                 public String call() throws Exception
/*      */                 {
/* 1925 */                   return Minecraft.this.currentScreen.getClass().getCanonicalName();
/*      */                 }
/*      */               });
/* 1928 */           throw new ReportedException(crashreport1);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1933 */     if (this.currentScreen == null || this.currentScreen.allowUserInput) {
/*      */       
/* 1935 */       this.mcProfiler.endStartSection("mouse");
/* 1936 */       runTickMouse();
/*      */       
/* 1938 */       if (this.leftClickCounter > 0)
/*      */       {
/* 1940 */         this.leftClickCounter--;
/*      */       }
/*      */       
/* 1943 */       this.mcProfiler.endStartSection("keyboard");
/* 1944 */       runTickKeyboard();
/*      */     } 
/*      */     
/* 1947 */     if (this.world != null) {
/*      */       
/* 1949 */       if (this.player != null) {
/*      */         
/* 1951 */         this.joinPlayerCounter++;
/*      */         
/* 1953 */         if (this.joinPlayerCounter == 30) {
/*      */           
/* 1955 */           this.joinPlayerCounter = 0;
/* 1956 */           this.world.joinEntityInSurroundings((Entity)this.player);
/*      */         } 
/*      */       } 
/*      */       
/* 1960 */       this.mcProfiler.endStartSection("gameRenderer");
/*      */       
/* 1962 */       if (!this.isGamePaused)
/*      */       {
/* 1964 */         this.entityRenderer.updateRenderer();
/*      */       }
/*      */       
/* 1967 */       this.mcProfiler.endStartSection("levelRenderer");
/*      */       
/* 1969 */       if (!this.isGamePaused)
/*      */       {
/* 1971 */         this.renderGlobal.updateClouds();
/*      */       }
/*      */       
/* 1974 */       this.mcProfiler.endStartSection("level");
/*      */       
/* 1976 */       if (!this.isGamePaused)
/*      */       {
/* 1978 */         if (this.world.getLastLightningBolt() > 0)
/*      */         {
/* 1980 */           this.world.setLastLightningBolt(this.world.getLastLightningBolt() - 1);
/*      */         }
/*      */         
/* 1983 */         this.world.updateEntities();
/*      */       }
/*      */     
/* 1986 */     } else if (this.entityRenderer.isShaderActive()) {
/*      */       
/* 1988 */       this.entityRenderer.stopUseShader();
/*      */     } 
/*      */     
/* 1991 */     if (!this.isGamePaused) {
/*      */       
/* 1993 */       this.mcMusicTicker.update();
/* 1994 */       this.mcSoundHandler.update();
/*      */     } 
/*      */     
/* 1997 */     if (this.world != null) {
/*      */       
/* 1999 */       if (!this.isGamePaused) {
/*      */         
/* 2001 */         this.world.setAllowedSpawnTypes((this.world.getDifficulty() != EnumDifficulty.PEACEFUL), true);
/* 2002 */         this.field_193035_aW.func_193303_d();
/*      */ 
/*      */         
/*      */         try {
/* 2006 */           this.world.tick();
/*      */         }
/* 2008 */         catch (Throwable throwable2) {
/*      */           
/* 2010 */           CrashReport crashreport2 = CrashReport.makeCrashReport(throwable2, "Exception in world tick");
/*      */           
/* 2012 */           if (this.world == null) {
/*      */             
/* 2014 */             CrashReportCategory crashreportcategory2 = crashreport2.makeCategory("Affected level");
/* 2015 */             crashreportcategory2.addCrashSection("Problem", "Level is null!");
/*      */           }
/*      */           else {
/*      */             
/* 2019 */             this.world.addWorldInfoToCrashReport(crashreport2);
/*      */           } 
/*      */           
/* 2022 */           throw new ReportedException(crashreport2);
/*      */         } 
/*      */       } 
/*      */       
/* 2026 */       this.mcProfiler.endStartSection("animateTick");
/*      */       
/* 2028 */       if (!this.isGamePaused && this.world != null)
/*      */       {
/* 2030 */         this.world.doVoidFogParticles(MathHelper.floor(this.player.posX), MathHelper.floor(this.player.posY), MathHelper.floor(this.player.posZ));
/*      */       }
/*      */       
/* 2033 */       this.mcProfiler.endStartSection("particles");
/*      */       
/* 2035 */       if (!this.isGamePaused)
/*      */       {
/* 2037 */         this.effectRenderer.updateEffects();
/*      */       }
/*      */     }
/* 2040 */     else if (this.myNetworkManager != null) {
/*      */       
/* 2042 */       this.mcProfiler.endStartSection("pendingConnection");
/* 2043 */       this.myNetworkManager.processReceivedPackets();
/*      */     } 
/*      */     
/* 2046 */     this.mcProfiler.endSection();
/* 2047 */     this.systemTime = getSystemTime();
/*      */   }
/*      */ 
/*      */   
/*      */   private void runTickKeyboard() throws IOException {
/* 2052 */     while (Keyboard.next()) {
/*      */       
/* 2054 */       int i = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + 256) : Keyboard.getEventKey();
/*      */       
/* 2056 */       if (this.debugCrashKeyPressTime > 0L) {
/*      */         
/* 2058 */         if (getSystemTime() - this.debugCrashKeyPressTime >= 6000L)
/*      */         {
/* 2060 */           throw new ReportedException(new CrashReport("Manually triggered debug crash", new Throwable()));
/*      */         }
/*      */         
/* 2063 */         if (!Keyboard.isKeyDown(46) || !Keyboard.isKeyDown(61))
/*      */         {
/* 2065 */           this.debugCrashKeyPressTime = -1L;
/*      */         }
/*      */       }
/* 2068 */       else if (Keyboard.isKeyDown(46) && Keyboard.isKeyDown(61)) {
/*      */         
/* 2070 */         this.actionKeyF3 = true;
/* 2071 */         this.debugCrashKeyPressTime = getSystemTime();
/*      */       } 
/*      */       
/* 2074 */       dispatchKeypresses();
/*      */       
/* 2076 */       if (this.currentScreen != null)
/*      */       {
/* 2078 */         this.currentScreen.handleKeyboardInput();
/*      */       }
/*      */       
/* 2081 */       boolean flag = Keyboard.getEventKeyState();
/*      */       
/* 2083 */       if (flag) {
/*      */         
/* 2085 */         if (i == 62 && this.entityRenderer != null)
/*      */         {
/* 2087 */           this.entityRenderer.switchUseShader();
/*      */         }
/*      */         
/* 2090 */         boolean flag1 = false;
/*      */         
/* 2092 */         if (this.currentScreen == null) {
/*      */           
/* 2094 */           if (i == 1)
/*      */           {
/* 2096 */             displayInGameMenu();
/*      */           }
/*      */           
/* 2099 */           flag1 = (Keyboard.isKeyDown(61) && processKeyF3(i));
/* 2100 */           this.actionKeyF3 |= flag1;
/*      */           
/* 2102 */           if (i == 59)
/*      */           {
/* 2104 */             this.gameSettings.hideGUI = !this.gameSettings.hideGUI;
/*      */           }
/*      */         } 
/*      */         
/* 2108 */         if (flag1) {
/*      */           
/* 2110 */           KeyBinding.setKeyBindState(i, false);
/*      */         }
/*      */         else {
/*      */           
/* 2114 */           KeyBinding.setKeyBindState(i, true);
/* 2115 */           KeyBinding.onTick(i);
/*      */         } 
/*      */         
/* 2118 */         if (this.gameSettings.showDebugProfilerChart) {
/*      */           
/* 2120 */           if (i == 11)
/*      */           {
/* 2122 */             updateDebugProfilerName(0);
/*      */           }
/*      */           
/* 2125 */           for (int j = 0; j < 9; j++) {
/*      */             
/* 2127 */             if (i == 2 + j)
/*      */             {
/* 2129 */               updateDebugProfilerName(j + 1);
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*      */         continue;
/*      */       } 
/* 2136 */       KeyBinding.setKeyBindState(i, false);
/*      */       
/* 2138 */       if (i == 61) {
/*      */         
/* 2140 */         if (this.actionKeyF3) {
/*      */           
/* 2142 */           this.actionKeyF3 = false;
/*      */           
/*      */           continue;
/*      */         } 
/* 2146 */         this.gameSettings.showDebugInfo = !this.gameSettings.showDebugInfo;
/* 2147 */         this.gameSettings.showDebugProfilerChart = (this.gameSettings.showDebugInfo && GuiScreen.isShiftKeyDown());
/* 2148 */         this.gameSettings.showLagometer = (this.gameSettings.showDebugInfo && GuiScreen.isAltKeyDown());
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2154 */     processKeyBinds();
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean processKeyF3(int p_184122_1_) {
/* 2159 */     if (p_184122_1_ == 30) {
/*      */       
/* 2161 */       this.renderGlobal.loadRenderers();
/* 2162 */       func_190521_a("debug.reload_chunks.message", new Object[0]);
/* 2163 */       return true;
/*      */     } 
/* 2165 */     if (p_184122_1_ == 48) {
/*      */       
/* 2167 */       boolean flag1 = !this.renderManager.isDebugBoundingBox();
/* 2168 */       this.renderManager.setDebugBoundingBox(flag1);
/* 2169 */       func_190521_a(flag1 ? "debug.show_hitboxes.on" : "debug.show_hitboxes.off", new Object[0]);
/* 2170 */       return true;
/*      */     } 
/* 2172 */     if (p_184122_1_ == 32) {
/*      */       
/* 2174 */       if (this.ingameGUI != null)
/*      */       {
/* 2176 */         this.ingameGUI.getChatGUI().clearChatMessages(false);
/*      */       }
/*      */       
/* 2179 */       return true;
/*      */     } 
/* 2181 */     if (p_184122_1_ == 33) {
/*      */       
/* 2183 */       this.gameSettings.setOptionValue(GameSettings.Options.RENDER_DISTANCE, GuiScreen.isShiftKeyDown() ? -1 : 1);
/* 2184 */       func_190521_a("debug.cycle_renderdistance.message", new Object[] { Integer.valueOf(this.gameSettings.renderDistanceChunks) });
/* 2185 */       return true;
/*      */     } 
/* 2187 */     if (p_184122_1_ == 34) {
/*      */       
/* 2189 */       boolean flag = this.debugRenderer.toggleDebugScreen();
/* 2190 */       func_190521_a(flag ? "debug.chunk_boundaries.on" : "debug.chunk_boundaries.off", new Object[0]);
/* 2191 */       return true;
/*      */     } 
/* 2193 */     if (p_184122_1_ == 35) {
/*      */       
/* 2195 */       this.gameSettings.advancedItemTooltips = !this.gameSettings.advancedItemTooltips;
/* 2196 */       func_190521_a(this.gameSettings.advancedItemTooltips ? "debug.advanced_tooltips.on" : "debug.advanced_tooltips.off", new Object[0]);
/* 2197 */       this.gameSettings.saveOptions();
/* 2198 */       return true;
/*      */     } 
/* 2200 */     if (p_184122_1_ == 49) {
/*      */       
/* 2202 */       if (!this.player.canCommandSenderUseCommand(2, "")) {
/*      */         
/* 2204 */         func_190521_a("debug.creative_spectator.error", new Object[0]);
/*      */       }
/* 2206 */       else if (this.player.isCreative()) {
/*      */         
/* 2208 */         this.player.sendChatMessage("/gamemode spectator");
/*      */       }
/* 2210 */       else if (this.player.isSpectator()) {
/*      */         
/* 2212 */         this.player.sendChatMessage("/gamemode creative");
/*      */       } 
/*      */       
/* 2215 */       return true;
/*      */     } 
/* 2217 */     if (p_184122_1_ == 25) {
/*      */       
/* 2219 */       this.gameSettings.pauseOnLostFocus = !this.gameSettings.pauseOnLostFocus;
/* 2220 */       this.gameSettings.saveOptions();
/* 2221 */       func_190521_a(this.gameSettings.pauseOnLostFocus ? "debug.pause_focus.on" : "debug.pause_focus.off", new Object[0]);
/* 2222 */       return true;
/*      */     } 
/* 2224 */     if (p_184122_1_ == 16) {
/*      */       
/* 2226 */       func_190521_a("debug.help.message", new Object[0]);
/* 2227 */       GuiNewChat guinewchat = this.ingameGUI.getChatGUI();
/* 2228 */       guinewchat.printChatMessage((ITextComponent)new TextComponentTranslation("debug.reload_chunks.help", new Object[0]));
/* 2229 */       guinewchat.printChatMessage((ITextComponent)new TextComponentTranslation("debug.show_hitboxes.help", new Object[0]));
/* 2230 */       guinewchat.printChatMessage((ITextComponent)new TextComponentTranslation("debug.clear_chat.help", new Object[0]));
/* 2231 */       guinewchat.printChatMessage((ITextComponent)new TextComponentTranslation("debug.cycle_renderdistance.help", new Object[0]));
/* 2232 */       guinewchat.printChatMessage((ITextComponent)new TextComponentTranslation("debug.chunk_boundaries.help", new Object[0]));
/* 2233 */       guinewchat.printChatMessage((ITextComponent)new TextComponentTranslation("debug.advanced_tooltips.help", new Object[0]));
/* 2234 */       guinewchat.printChatMessage((ITextComponent)new TextComponentTranslation("debug.creative_spectator.help", new Object[0]));
/* 2235 */       guinewchat.printChatMessage((ITextComponent)new TextComponentTranslation("debug.pause_focus.help", new Object[0]));
/* 2236 */       guinewchat.printChatMessage((ITextComponent)new TextComponentTranslation("debug.help.help", new Object[0]));
/* 2237 */       guinewchat.printChatMessage((ITextComponent)new TextComponentTranslation("debug.reload_resourcepacks.help", new Object[0]));
/* 2238 */       return true;
/*      */     } 
/* 2240 */     if (p_184122_1_ == 20) {
/*      */       
/* 2242 */       func_190521_a("debug.reload_resourcepacks.message", new Object[0]);
/* 2243 */       refreshResources();
/* 2244 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2248 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void processKeyBinds() {
/* 2254 */     for (; this.gameSettings.keyBindTogglePerspective.isPressed(); this.renderGlobal.setDisplayListEntitiesDirty()) {
/*      */       
/* 2256 */       this.gameSettings.thirdPersonView++;
/*      */       
/* 2258 */       if (this.gameSettings.thirdPersonView > 2)
/*      */       {
/* 2260 */         this.gameSettings.thirdPersonView = 0;
/*      */       }
/*      */       
/* 2263 */       if (this.gameSettings.thirdPersonView == 0) {
/*      */         
/* 2265 */         this.entityRenderer.loadEntityShader(getRenderViewEntity());
/*      */       }
/* 2267 */       else if (this.gameSettings.thirdPersonView == 1) {
/*      */         
/* 2269 */         this.entityRenderer.loadEntityShader(null);
/*      */       } 
/*      */     } 
/*      */     
/* 2273 */     while (this.gameSettings.keyBindSmoothCamera.isPressed())
/*      */     {
/* 2275 */       this.gameSettings.smoothCamera = !this.gameSettings.smoothCamera;
/*      */     }
/*      */     
/* 2278 */     for (int i = 0; i < 9; i++) {
/*      */       
/* 2280 */       boolean flag = this.gameSettings.field_193629_ap.isKeyDown();
/* 2281 */       boolean flag1 = this.gameSettings.field_193630_aq.isKeyDown();
/*      */       
/* 2283 */       if (this.gameSettings.keyBindsHotbar[i].isPressed())
/*      */       {
/* 2285 */         if (this.player.isSpectator()) {
/*      */           
/* 2287 */           this.ingameGUI.getSpectatorGui().onHotbarSelected(i);
/*      */         }
/* 2289 */         else if (!this.player.isCreative() || this.currentScreen != null || (!flag1 && !flag)) {
/*      */           
/* 2291 */           this.player.inventory.currentItem = i;
/*      */         }
/*      */         else {
/*      */           
/* 2295 */           GuiContainerCreative.func_192044_a(this, i, flag1, flag);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 2300 */     while (this.gameSettings.keyBindInventory.isPressed()) {
/*      */       
/* 2302 */       if (this.playerController.isRidingHorse()) {
/*      */         
/* 2304 */         this.player.sendHorseInventory();
/*      */         
/*      */         continue;
/*      */       } 
/* 2308 */       this.field_193035_aW.func_193296_a();
/* 2309 */       displayGuiScreen((GuiScreen)new GuiInventory((EntityPlayer)this.player));
/*      */     } 
/*      */ 
/*      */     
/* 2313 */     while (this.gameSettings.field_194146_ao.isPressed())
/*      */     {
/* 2315 */       displayGuiScreen((GuiScreen)new GuiScreenAdvancements(this.player.connection.func_191982_f()));
/*      */     }
/*      */     
/* 2318 */     while (this.gameSettings.keyBindSwapHands.isPressed()) {
/*      */       
/* 2320 */       if (!this.player.isSpectator())
/*      */       {
/* 2322 */         getConnection().sendPacket((Packet)new CPacketPlayerDigging(CPacketPlayerDigging.Action.SWAP_HELD_ITEMS, BlockPos.ORIGIN, EnumFacing.DOWN));
/*      */       }
/*      */     } 
/*      */     
/* 2326 */     while (this.gameSettings.keyBindDrop.isPressed()) {
/*      */       
/* 2328 */       if (!this.player.isSpectator())
/*      */       {
/* 2330 */         this.player.dropItem(GuiScreen.isCtrlKeyDown());
/*      */       }
/*      */     } 
/*      */     
/* 2334 */     boolean flag2 = (this.gameSettings.chatVisibility != EntityPlayer.EnumChatVisibility.HIDDEN);
/*      */     
/* 2336 */     if (flag2) {
/*      */       
/* 2338 */       while (this.gameSettings.keyBindChat.isPressed())
/*      */       {
/* 2340 */         displayGuiScreen((GuiScreen)new GuiChat());
/*      */       }
/*      */       
/* 2343 */       if (this.currentScreen == null && this.gameSettings.keyBindCommand.isPressed())
/*      */       {
/* 2345 */         displayGuiScreen((GuiScreen)new GuiChat("/"));
/*      */       }
/*      */     } 
/*      */     
/* 2349 */     if (this.player.isHandActive()) {
/*      */       
/* 2351 */       if (!this.gameSettings.keyBindUseItem.isKeyDown())
/*      */       {
/* 2353 */         this.playerController.onStoppedUsingItem((EntityPlayer)this.player);
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       do {
/*      */       
/* 2360 */       } while (this.gameSettings.keyBindAttack.isPressed()); do {
/*      */       
/* 2362 */       } while (this.gameSettings.keyBindUseItem.isPressed());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2369 */       while (this.gameSettings.keyBindPickBlock.isPressed());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2381 */       while (this.gameSettings.keyBindAttack.isPressed())
/*      */       {
/* 2383 */         clickMouse();
/*      */       }
/*      */       
/* 2386 */       while (this.gameSettings.keyBindUseItem.isPressed())
/*      */       {
/* 2388 */         rightClickMouse();
/*      */       }
/*      */       
/* 2391 */       while (this.gameSettings.keyBindPickBlock.isPressed())
/*      */       {
/* 2393 */         middleClickMouse();
/*      */       }
/*      */     } 
/*      */     
/* 2397 */     if (this.gameSettings.keyBindUseItem.isKeyDown() && this.rightClickDelayTimer == 0 && !this.player.isHandActive())
/*      */     {
/* 2399 */       rightClickMouse();
/*      */     }
/*      */     
/* 2402 */     sendClickBlockToController((this.currentScreen == null && this.gameSettings.keyBindAttack.isKeyDown() && this.inGameHasFocus));
/*      */   }
/*      */ 
/*      */   
/*      */   private void runTickMouse() throws IOException {
/* 2407 */     while (Mouse.next()) {
/*      */       
/* 2409 */       int i = Mouse.getEventButton();
/* 2410 */       KeyBinding.setKeyBindState(i - 100, Mouse.getEventButtonState());
/*      */       
/* 2412 */       if (Mouse.getEventButtonState())
/*      */       {
/* 2414 */         if (this.player.isSpectator() && i == 2) {
/*      */           
/* 2416 */           this.ingameGUI.getSpectatorGui().onMiddleClick();
/*      */         }
/*      */         else {
/*      */           
/* 2420 */           KeyBinding.onTick(i - 100);
/*      */         } 
/*      */       }
/*      */       
/* 2424 */       long j = getSystemTime() - this.systemTime;
/*      */       
/* 2426 */       if (j <= 200L) {
/*      */         
/* 2428 */         int k = Mouse.getEventDWheel();
/*      */         
/* 2430 */         if (k != 0)
/*      */         {
/* 2432 */           if (this.player.isSpectator()) {
/*      */             
/* 2434 */             k = (k < 0) ? -1 : 1;
/*      */             
/* 2436 */             if (this.ingameGUI.getSpectatorGui().isMenuActive())
/*      */             {
/* 2438 */               this.ingameGUI.getSpectatorGui().onMouseScroll(-k);
/*      */             }
/*      */             else
/*      */             {
/* 2442 */               float f = MathHelper.clamp(this.player.capabilities.getFlySpeed() + k * 0.005F, 0.0F, 0.2F);
/* 2443 */               this.player.capabilities.setFlySpeed(f);
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 2448 */             this.player.inventory.changeCurrentItem(k);
/*      */           } 
/*      */         }
/*      */         
/* 2452 */         if (this.currentScreen == null) {
/*      */           
/* 2454 */           if (!this.inGameHasFocus && Mouse.getEventButtonState())
/*      */           {
/* 2456 */             setIngameFocus(); } 
/*      */           continue;
/*      */         } 
/* 2459 */         if (this.currentScreen != null)
/*      */         {
/* 2461 */           this.currentScreen.handleMouseInput();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_190521_a(String p_190521_1_, Object... p_190521_2_) {
/* 2469 */     this.ingameGUI.getChatGUI().printChatMessage((new TextComponentString("")).appendSibling((new TextComponentTranslation("debug.prefix", new Object[0])).setStyle((new Style()).setColor(TextFormatting.YELLOW).setBold(Boolean.valueOf(true)))).appendText(" ").appendSibling((ITextComponent)new TextComponentTranslation(p_190521_1_, p_190521_2_)));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void launchIntegratedServer(String folderName, String worldName, @Nullable WorldSettings worldSettingsIn) {
/* 2477 */     loadWorld(null);
/* 2478 */     System.gc();
/* 2479 */     ISaveHandler isavehandler = this.saveLoader.getSaveLoader(folderName, false);
/* 2480 */     WorldInfo worldinfo = isavehandler.loadWorldInfo();
/*      */     
/* 2482 */     if (worldinfo == null && worldSettingsIn != null) {
/*      */       
/* 2484 */       worldinfo = new WorldInfo(worldSettingsIn, folderName);
/* 2485 */       isavehandler.saveWorldInfo(worldinfo);
/*      */     } 
/*      */     
/* 2488 */     if (worldSettingsIn == null)
/*      */     {
/* 2490 */       worldSettingsIn = new WorldSettings(worldinfo);
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 2495 */       YggdrasilAuthenticationService yggdrasilauthenticationservice = new YggdrasilAuthenticationService(this.proxy, UUID.randomUUID().toString());
/* 2496 */       MinecraftSessionService minecraftsessionservice = yggdrasilauthenticationservice.createMinecraftSessionService();
/* 2497 */       GameProfileRepository gameprofilerepository = yggdrasilauthenticationservice.createProfileRepository();
/* 2498 */       PlayerProfileCache playerprofilecache = new PlayerProfileCache(gameprofilerepository, new File(this.mcDataDir, MinecraftServer.USER_CACHE_FILE.getName()));
/* 2499 */       TileEntitySkull.setProfileCache(playerprofilecache);
/* 2500 */       TileEntitySkull.setSessionService(minecraftsessionservice);
/* 2501 */       PlayerProfileCache.setOnlineMode(false);
/* 2502 */       this.theIntegratedServer = new IntegratedServer(this, folderName, worldName, worldSettingsIn, yggdrasilauthenticationservice, minecraftsessionservice, gameprofilerepository, playerprofilecache);
/* 2503 */       this.theIntegratedServer.startServerThread();
/* 2504 */       this.integratedServerIsRunning = true;
/*      */     }
/* 2506 */     catch (Throwable throwable) {
/*      */       
/* 2508 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Starting integrated server");
/* 2509 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Starting integrated server");
/* 2510 */       crashreportcategory.addCrashSection("Level ID", folderName);
/* 2511 */       crashreportcategory.addCrashSection("Level Name", worldName);
/* 2512 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */     
/* 2515 */     this.loadingScreen.displaySavingString(I18n.format("menu.loadingLevel", new Object[0]));
/*      */     
/* 2517 */     while (!this.theIntegratedServer.serverIsInRunLoop()) {
/*      */       
/* 2519 */       String s = this.theIntegratedServer.getUserMessage();
/*      */       
/* 2521 */       if (s != null) {
/*      */         
/* 2523 */         this.loadingScreen.displayLoadingString(I18n.format(s, new Object[0]));
/*      */       }
/*      */       else {
/*      */         
/* 2527 */         this.loadingScreen.displayLoadingString("");
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/* 2532 */         Thread.sleep(200L);
/*      */       }
/* 2534 */       catch (InterruptedException interruptedException) {}
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2540 */     displayGuiScreen((GuiScreen)new GuiScreenWorking());
/* 2541 */     SocketAddress socketaddress = this.theIntegratedServer.getNetworkSystem().addLocalEndpoint();
/* 2542 */     NetworkManager networkmanager = NetworkManager.provideLocalClient(socketaddress);
/* 2543 */     networkmanager.setNetHandler((INetHandler)new NetHandlerLoginClient(networkmanager, this, null));
/* 2544 */     networkmanager.sendPacket((Packet)new C00Handshake(socketaddress.toString(), 0, EnumConnectionState.LOGIN));
/* 2545 */     networkmanager.sendPacket((Packet)new CPacketLoginStart(getSession().getProfile()));
/* 2546 */     this.myNetworkManager = networkmanager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadWorld(@Nullable WorldClient worldClientIn) {
/* 2554 */     loadWorld(worldClientIn, "");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadWorld(@Nullable WorldClient worldClientIn, String loadingMessage) {
/* 2562 */     if (worldClientIn == null) {
/*      */       
/* 2564 */       NetHandlerPlayClient nethandlerplayclient = getConnection();
/*      */       
/* 2566 */       if (nethandlerplayclient != null)
/*      */       {
/* 2568 */         nethandlerplayclient.cleanup();
/*      */       }
/*      */       
/* 2571 */       if (this.theIntegratedServer != null && this.theIntegratedServer.isAnvilFileSet())
/*      */       {
/* 2573 */         this.theIntegratedServer.initiateShutdown();
/*      */       }
/*      */       
/* 2576 */       this.theIntegratedServer = null;
/* 2577 */       this.entityRenderer.func_190564_k();
/* 2578 */       this.playerController = null;
/* 2579 */       NarratorChatListener.field_193643_a.func_193642_b();
/*      */     } 
/*      */     
/* 2582 */     this.renderViewEntity = null;
/* 2583 */     this.myNetworkManager = null;
/*      */     
/* 2585 */     if (this.loadingScreen != null) {
/*      */       
/* 2587 */       this.loadingScreen.resetProgressAndMessage(loadingMessage);
/* 2588 */       this.loadingScreen.displayLoadingString("");
/*      */     } 
/*      */     
/* 2591 */     if (worldClientIn == null && this.world != null) {
/*      */       
/* 2593 */       this.mcResourcePackRepository.clearResourcePack();
/* 2594 */       this.ingameGUI.resetPlayersOverlayFooterHeader();
/* 2595 */       setServerData(null);
/* 2596 */       this.integratedServerIsRunning = false;
/*      */     } 
/*      */     
/* 2599 */     this.mcSoundHandler.stopSounds();
/* 2600 */     this.world = worldClientIn;
/*      */     
/* 2602 */     if (this.renderGlobal != null)
/*      */     {
/* 2604 */       this.renderGlobal.setWorldAndLoadRenderers(worldClientIn);
/*      */     }
/*      */     
/* 2607 */     if (this.effectRenderer != null)
/*      */     {
/* 2609 */       this.effectRenderer.clearEffects((World)worldClientIn);
/*      */     }
/*      */     
/* 2612 */     TileEntityRendererDispatcher.instance.setWorld((World)worldClientIn);
/*      */     
/* 2614 */     if (worldClientIn != null) {
/*      */       
/* 2616 */       if (!this.integratedServerIsRunning) {
/*      */         
/* 2618 */         YggdrasilAuthenticationService yggdrasilAuthenticationService = new YggdrasilAuthenticationService(this.proxy, UUID.randomUUID().toString());
/* 2619 */         MinecraftSessionService minecraftsessionservice = yggdrasilAuthenticationService.createMinecraftSessionService();
/* 2620 */         GameProfileRepository gameprofilerepository = yggdrasilAuthenticationService.createProfileRepository();
/* 2621 */         PlayerProfileCache playerprofilecache = new PlayerProfileCache(gameprofilerepository, new File(this.mcDataDir, MinecraftServer.USER_CACHE_FILE.getName()));
/* 2622 */         TileEntitySkull.setProfileCache(playerprofilecache);
/* 2623 */         TileEntitySkull.setSessionService(minecraftsessionservice);
/* 2624 */         PlayerProfileCache.setOnlineMode(false);
/*      */       } 
/*      */       
/* 2627 */       if (this.player == null) {
/*      */         
/* 2629 */         this.player = this.playerController.func_192830_a((World)worldClientIn, new StatisticsManager(), (RecipeBook)new RecipeBookClient());
/* 2630 */         this.playerController.flipPlayer((EntityPlayer)this.player);
/*      */       } 
/*      */       
/* 2633 */       this.player.preparePlayerToSpawn();
/* 2634 */       worldClientIn.spawnEntityInWorld((Entity)this.player);
/* 2635 */       this.player.movementInput = (MovementInput)new MovementInputFromOptions(this.gameSettings);
/* 2636 */       this.playerController.setPlayerCapabilities((EntityPlayer)this.player);
/* 2637 */       this.renderViewEntity = (Entity)this.player;
/*      */ 
/*      */       
/* 2640 */       if (FBP.enabled) {
/* 2641 */         FBP.INSTANCE.eventHandler.onWorldLoadEvent();
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 2646 */       this.saveLoader.flushCache();
/* 2647 */       this.player = null;
/*      */     } 
/*      */     
/* 2650 */     System.gc();
/* 2651 */     this.systemTime = 0L;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDimensionAndSpawnPlayer(int dimension) {
/* 2656 */     this.world.setInitialSpawnLocation();
/* 2657 */     this.world.removeAllEntities();
/* 2658 */     int i = 0;
/* 2659 */     String s = null;
/*      */     
/* 2661 */     if (this.player != null) {
/*      */       
/* 2663 */       i = this.player.getEntityId();
/* 2664 */       this.world.removeEntity((Entity)this.player);
/* 2665 */       s = this.player.getServerBrand();
/*      */     } 
/*      */     
/* 2668 */     this.renderViewEntity = null;
/* 2669 */     EntityPlayerSP entityplayersp = this.player;
/* 2670 */     this.player = this.playerController.func_192830_a((World)this.world, (this.player == null) ? new StatisticsManager() : this.player.getStatFileWriter(), (this.player == null) ? new RecipeBook() : this.player.func_192035_E());
/* 2671 */     this.player.getDataManager().setEntryValues(entityplayersp.getDataManager().getAll());
/* 2672 */     this.player.dimension = dimension;
/* 2673 */     this.renderViewEntity = (Entity)this.player;
/* 2674 */     this.player.preparePlayerToSpawn();
/* 2675 */     this.player.setServerBrand(s);
/* 2676 */     this.world.spawnEntityInWorld((Entity)this.player);
/* 2677 */     this.playerController.flipPlayer((EntityPlayer)this.player);
/* 2678 */     this.player.movementInput = (MovementInput)new MovementInputFromOptions(this.gameSettings);
/* 2679 */     this.player.setEntityId(i);
/* 2680 */     this.playerController.setPlayerCapabilities((EntityPlayer)this.player);
/* 2681 */     this.player.setReducedDebug(entityplayersp.hasReducedDebug());
/*      */     
/* 2683 */     if (this.currentScreen instanceof GuiGameOver)
/*      */     {
/* 2685 */       displayGuiScreen(null);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public final boolean isDemo() {
/* 2694 */     return this.isDemo;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public NetHandlerPlayClient getConnection() {
/* 2700 */     return (this.player == null) ? null : this.player.connection;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isGuiEnabled() {
/* 2705 */     return !(theMinecraft != null && theMinecraft.gameSettings.hideGUI);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFancyGraphicsEnabled() {
/* 2710 */     return (theMinecraft != null && theMinecraft.gameSettings.fancyGraphics);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAmbientOcclusionEnabled() {
/* 2718 */     return (theMinecraft != null && theMinecraft.gameSettings.ambientOcclusion != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void middleClickMouse() {
/* 2726 */     if (this.objectMouseOver != null && this.objectMouseOver.typeOfHit != RayTraceResult.Type.MISS) {
/*      */       ItemStack itemstack;
/* 2728 */       boolean flag = this.player.capabilities.isCreativeMode;
/* 2729 */       TileEntity tileentity = null;
/*      */ 
/*      */       
/* 2732 */       if (this.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
/*      */         
/* 2734 */         BlockPos blockpos = this.objectMouseOver.getBlockPos();
/* 2735 */         IBlockState iblockstate = this.world.getBlockState(blockpos);
/* 2736 */         Block block = iblockstate.getBlock();
/*      */         
/* 2738 */         if (iblockstate.getMaterial() == Material.AIR) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2743 */         itemstack = block.getItem((World)this.world, blockpos, iblockstate);
/*      */         
/* 2745 */         if (itemstack.func_190926_b()) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2750 */         if (flag && GuiScreen.isCtrlKeyDown() && block.hasTileEntity())
/*      */         {
/* 2752 */           tileentity = this.world.getTileEntity(blockpos);
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 2757 */         if (this.objectMouseOver.typeOfHit != RayTraceResult.Type.ENTITY || this.objectMouseOver.entityHit == null || !flag) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2762 */         if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityPainting) {
/*      */           
/* 2764 */           itemstack = new ItemStack(Items.PAINTING);
/*      */         }
/* 2766 */         else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.EntityLeashKnot) {
/*      */           
/* 2768 */           itemstack = new ItemStack(Items.LEAD);
/*      */         }
/* 2770 */         else if (this.objectMouseOver.entityHit instanceof EntityItemFrame) {
/*      */           
/* 2772 */           EntityItemFrame entityitemframe = (EntityItemFrame)this.objectMouseOver.entityHit;
/* 2773 */           ItemStack itemstack1 = entityitemframe.getDisplayedItem();
/*      */           
/* 2775 */           if (itemstack1.func_190926_b())
/*      */           {
/* 2777 */             itemstack = new ItemStack(Items.ITEM_FRAME);
/*      */           }
/*      */           else
/*      */           {
/* 2781 */             itemstack = itemstack1.copy();
/*      */           }
/*      */         
/* 2784 */         } else if (this.objectMouseOver.entityHit instanceof EntityMinecart) {
/*      */           Item item1;
/* 2786 */           EntityMinecart entityminecart = (EntityMinecart)this.objectMouseOver.entityHit;
/*      */ 
/*      */           
/* 2789 */           switch (entityminecart.getType()) {
/*      */             
/*      */             case FURNACE:
/* 2792 */               item1 = Items.FURNACE_MINECART;
/*      */               break;
/*      */             
/*      */             case null:
/* 2796 */               item1 = Items.CHEST_MINECART;
/*      */               break;
/*      */             
/*      */             case TNT:
/* 2800 */               item1 = Items.TNT_MINECART;
/*      */               break;
/*      */             
/*      */             case HOPPER:
/* 2804 */               item1 = Items.HOPPER_MINECART;
/*      */               break;
/*      */             
/*      */             case COMMAND_BLOCK:
/* 2808 */               item1 = Items.COMMAND_BLOCK_MINECART;
/*      */               break;
/*      */             
/*      */             default:
/* 2812 */               item1 = Items.MINECART;
/*      */               break;
/*      */           } 
/* 2815 */           itemstack = new ItemStack(item1);
/*      */         }
/* 2817 */         else if (this.objectMouseOver.entityHit instanceof EntityBoat) {
/*      */           
/* 2819 */           itemstack = new ItemStack(((EntityBoat)this.objectMouseOver.entityHit).getItemBoat());
/*      */         }
/* 2821 */         else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityArmorStand) {
/*      */           
/* 2823 */           itemstack = new ItemStack((Item)Items.ARMOR_STAND);
/*      */         }
/* 2825 */         else if (this.objectMouseOver.entityHit instanceof net.minecraft.entity.item.EntityEnderCrystal) {
/*      */           
/* 2827 */           itemstack = new ItemStack(Items.END_CRYSTAL);
/*      */         }
/*      */         else {
/*      */           
/* 2831 */           ResourceLocation resourcelocation = EntityList.func_191301_a(this.objectMouseOver.entityHit);
/*      */           
/* 2833 */           if (resourcelocation == null || !EntityList.ENTITY_EGGS.containsKey(resourcelocation)) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/* 2838 */           itemstack = new ItemStack(Items.SPAWN_EGG);
/* 2839 */           ItemMonsterPlacer.applyEntityIdToItemStack(itemstack, resourcelocation);
/*      */         } 
/*      */       } 
/*      */       
/* 2843 */       if (itemstack.func_190926_b()) {
/*      */         
/* 2845 */         String s = "";
/*      */         
/* 2847 */         if (this.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
/*      */           
/* 2849 */           s = ((ResourceLocation)Block.REGISTRY.getNameForObject(this.world.getBlockState(this.objectMouseOver.getBlockPos()).getBlock())).toString();
/*      */         }
/* 2851 */         else if (this.objectMouseOver.typeOfHit == RayTraceResult.Type.ENTITY) {
/*      */           
/* 2853 */           s = EntityList.func_191301_a(this.objectMouseOver.entityHit).toString();
/*      */         } 
/*      */         
/* 2856 */         LOGGER.warn("Picking on: [{}] {} gave null item", this.objectMouseOver.typeOfHit, s);
/*      */       }
/*      */       else {
/*      */         
/* 2860 */         InventoryPlayer inventoryplayer = this.player.inventory;
/*      */         
/* 2862 */         if (tileentity != null)
/*      */         {
/* 2864 */           storeTEInStack(itemstack, tileentity);
/*      */         }
/*      */         
/* 2867 */         int i = inventoryplayer.getSlotFor(itemstack);
/*      */         
/* 2869 */         if (flag) {
/*      */           
/* 2871 */           inventoryplayer.setPickedItemStack(itemstack);
/* 2872 */           this.playerController.sendSlotPacket(this.player.getHeldItem(EnumHand.MAIN_HAND), 36 + inventoryplayer.currentItem);
/*      */         }
/* 2874 */         else if (i != -1) {
/*      */           
/* 2876 */           if (InventoryPlayer.isHotbar(i)) {
/*      */             
/* 2878 */             inventoryplayer.currentItem = i;
/*      */           }
/*      */           else {
/*      */             
/* 2882 */             this.playerController.pickItem(i);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private ItemStack storeTEInStack(ItemStack stack, TileEntity te) {
/* 2891 */     NBTTagCompound nbttagcompound = te.writeToNBT(new NBTTagCompound());
/*      */     
/* 2893 */     if (stack.getItem() == Items.SKULL && nbttagcompound.hasKey("Owner")) {
/*      */       
/* 2895 */       NBTTagCompound nbttagcompound2 = nbttagcompound.getCompoundTag("Owner");
/* 2896 */       NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/* 2897 */       nbttagcompound3.setTag("SkullOwner", (NBTBase)nbttagcompound2);
/* 2898 */       stack.setTagCompound(nbttagcompound3);
/* 2899 */       return stack;
/*      */     } 
/*      */ 
/*      */     
/* 2903 */     stack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound);
/* 2904 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 2905 */     NBTTagList nbttaglist = new NBTTagList();
/* 2906 */     nbttaglist.appendTag((NBTBase)new NBTTagString("(+NBT)"));
/* 2907 */     nbttagcompound1.setTag("Lore", (NBTBase)nbttaglist);
/* 2908 */     stack.setTagInfo("display", (NBTBase)nbttagcompound1);
/* 2909 */     return stack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CrashReport addGraphicsAndWorldToCrashReport(CrashReport theCrash) {
/* 2918 */     theCrash.getCategory().setDetail("Launched Version", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2922 */             return Minecraft.this.launchedVersion;
/*      */           }
/*      */         });
/* 2925 */     theCrash.getCategory().setDetail("LWJGL", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2929 */             return Sys.getVersion();
/*      */           }
/*      */         });
/* 2932 */     theCrash.getCategory().setDetail("OpenGL", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 2936 */             return String.valueOf(GlStateManager.glGetString(7937)) + " GL version " + GlStateManager.glGetString(7938) + ", " + GlStateManager.glGetString(7936);
/*      */           }
/*      */         });
/* 2939 */     theCrash.getCategory().setDetail("GL Caps", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 2943 */             return OpenGlHelper.getLogText();
/*      */           }
/*      */         });
/* 2946 */     theCrash.getCategory().setDetail("Using VBOs", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 2950 */             return Minecraft.this.gameSettings.useVbo ? "Yes" : "No";
/*      */           }
/*      */         });
/* 2953 */     theCrash.getCategory().setDetail("Is Modded", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2957 */             String s = ClientBrandRetriever.getClientModName();
/*      */             
/* 2959 */             if (!"vanilla".equals(s))
/*      */             {
/* 2961 */               return "Definitely; Client brand changed to '" + s + "'";
/*      */             }
/*      */ 
/*      */             
/* 2965 */             return (Minecraft.class.getSigners() == null) ? "Very likely; Jar signature invalidated" : "Probably not. Jar signature remains and client brand is untouched.";
/*      */           }
/*      */         });
/*      */     
/* 2969 */     theCrash.getCategory().setDetail("Type", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2973 */             return "Client (map_client.txt)";
/*      */           }
/*      */         });
/* 2976 */     theCrash.getCategory().setDetail("Resource Packs", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 2980 */             StringBuilder stringbuilder = new StringBuilder();
/*      */             
/* 2982 */             for (String s : Minecraft.this.gameSettings.resourcePacks) {
/*      */               
/* 2984 */               if (stringbuilder.length() > 0)
/*      */               {
/* 2986 */                 stringbuilder.append(", ");
/*      */               }
/*      */               
/* 2989 */               stringbuilder.append(s);
/*      */               
/* 2991 */               if (Minecraft.this.gameSettings.incompatibleResourcePacks.contains(s))
/*      */               {
/* 2993 */                 stringbuilder.append(" (incompatible)");
/*      */               }
/*      */             } 
/*      */             
/* 2997 */             return stringbuilder.toString();
/*      */           }
/*      */         });
/* 3000 */     theCrash.getCategory().setDetail("Current Language", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 3004 */             return Minecraft.this.mcLanguageManager.getCurrentLanguage().toString();
/*      */           }
/*      */         });
/* 3007 */     theCrash.getCategory().setDetail("Profiler Position", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 3011 */             return Minecraft.this.mcProfiler.profilingEnabled ? Minecraft.this.mcProfiler.getNameOfLastSection() : "N/A (disabled)";
/*      */           }
/*      */         });
/* 3014 */     theCrash.getCategory().setDetail("CPU", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call() throws Exception
/*      */           {
/* 3018 */             return OpenGlHelper.getCpu();
/*      */           }
/*      */         });
/*      */     
/* 3022 */     if (this.world != null)
/*      */     {
/* 3024 */       this.world.addWorldInfoToCrashReport(theCrash);
/*      */     }
/*      */     
/* 3027 */     return theCrash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Minecraft getMinecraft() {
/* 3035 */     return theMinecraft;
/*      */   }
/*      */ 
/*      */   
/*      */   public ListenableFuture<Object> scheduleResourcesRefresh() {
/* 3040 */     return addScheduledTask(new Runnable()
/*      */         {
/*      */           public void run()
/*      */           {
/* 3044 */             Minecraft.this.refreshResources();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public void addServerStatsToSnooper(Snooper playerSnooper) {
/* 3051 */     playerSnooper.addClientStat("fps", Integer.valueOf(debugFPS));
/* 3052 */     playerSnooper.addClientStat("vsync_enabled", Boolean.valueOf(this.gameSettings.enableVsync));
/* 3053 */     playerSnooper.addClientStat("display_frequency", Integer.valueOf(Display.getDisplayMode().getFrequency()));
/* 3054 */     playerSnooper.addClientStat("display_type", this.fullscreen ? "fullscreen" : "windowed");
/* 3055 */     playerSnooper.addClientStat("run_time", Long.valueOf((MinecraftServer.getCurrentTimeMillis() - playerSnooper.getMinecraftStartTimeMillis()) / 60L * 1000L));
/* 3056 */     playerSnooper.addClientStat("current_action", getCurrentAction());
/* 3057 */     playerSnooper.addClientStat("language", (this.gameSettings.language == null) ? "en_us" : this.gameSettings.language);
/* 3058 */     String s = (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) ? "little" : "big";
/* 3059 */     playerSnooper.addClientStat("endianness", s);
/* 3060 */     playerSnooper.addClientStat("subtitles", Boolean.valueOf(this.gameSettings.showSubtitles));
/* 3061 */     playerSnooper.addClientStat("touch", this.gameSettings.touchscreen ? "touch" : "mouse");
/* 3062 */     playerSnooper.addClientStat("resource_packs", Integer.valueOf(this.mcResourcePackRepository.getRepositoryEntries().size()));
/* 3063 */     int i = 0;
/*      */     
/* 3065 */     for (ResourcePackRepository.Entry resourcepackrepository$entry : this.mcResourcePackRepository.getRepositoryEntries())
/*      */     {
/* 3067 */       playerSnooper.addClientStat("resource_pack[" + i++ + "]", resourcepackrepository$entry.getResourcePackName());
/*      */     }
/*      */     
/* 3070 */     if (this.theIntegratedServer != null && this.theIntegratedServer.getPlayerUsageSnooper() != null)
/*      */     {
/* 3072 */       playerSnooper.addClientStat("snooper_partner", this.theIntegratedServer.getPlayerUsageSnooper().getUniqueID());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String getCurrentAction() {
/* 3081 */     if (this.theIntegratedServer != null)
/*      */     {
/* 3083 */       return this.theIntegratedServer.getPublic() ? "hosting_lan" : "singleplayer";
/*      */     }
/* 3085 */     if (this.currentServerData != null)
/*      */     {
/* 3087 */       return this.currentServerData.isOnLAN() ? "playing_lan" : "multiplayer";
/*      */     }
/*      */ 
/*      */     
/* 3091 */     return "out_of_game";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void addServerTypeToSnooper(Snooper playerSnooper) {
/* 3097 */     playerSnooper.addStatToSnooper("opengl_version", GlStateManager.glGetString(7938));
/* 3098 */     playerSnooper.addStatToSnooper("opengl_vendor", GlStateManager.glGetString(7936));
/* 3099 */     playerSnooper.addStatToSnooper("client_brand", ClientBrandRetriever.getClientModName());
/* 3100 */     playerSnooper.addStatToSnooper("launched_version", this.launchedVersion);
/* 3101 */     ContextCapabilities contextcapabilities = GLContext.getCapabilities();
/* 3102 */     playerSnooper.addStatToSnooper("gl_caps[ARB_arrays_of_arrays]", Boolean.valueOf(contextcapabilities.GL_ARB_arrays_of_arrays));
/* 3103 */     playerSnooper.addStatToSnooper("gl_caps[ARB_base_instance]", Boolean.valueOf(contextcapabilities.GL_ARB_base_instance));
/* 3104 */     playerSnooper.addStatToSnooper("gl_caps[ARB_blend_func_extended]", Boolean.valueOf(contextcapabilities.GL_ARB_blend_func_extended));
/* 3105 */     playerSnooper.addStatToSnooper("gl_caps[ARB_clear_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_clear_buffer_object));
/* 3106 */     playerSnooper.addStatToSnooper("gl_caps[ARB_color_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_color_buffer_float));
/* 3107 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compatibility]", Boolean.valueOf(contextcapabilities.GL_ARB_compatibility));
/* 3108 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compressed_texture_pixel_storage]", Boolean.valueOf(contextcapabilities.GL_ARB_compressed_texture_pixel_storage));
/* 3109 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_compute_shader));
/* 3110 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_buffer));
/* 3111 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_image));
/* 3112 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_buffer_float));
/* 3113 */     playerSnooper.addStatToSnooper("gl_caps[ARB_compute_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_compute_shader));
/* 3114 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_buffer]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_buffer));
/* 3115 */     playerSnooper.addStatToSnooper("gl_caps[ARB_copy_image]", Boolean.valueOf(contextcapabilities.GL_ARB_copy_image));
/* 3116 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_buffer_float]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_buffer_float));
/* 3117 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_clamp]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_clamp));
/* 3118 */     playerSnooper.addStatToSnooper("gl_caps[ARB_depth_texture]", Boolean.valueOf(contextcapabilities.GL_ARB_depth_texture));
/* 3119 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_buffers));
/* 3120 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_buffers_blend]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_buffers_blend));
/* 3121 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_elements_base_vertex]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_elements_base_vertex));
/* 3122 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_indirect]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_indirect));
/* 3123 */     playerSnooper.addStatToSnooper("gl_caps[ARB_draw_instanced]", Boolean.valueOf(contextcapabilities.GL_ARB_draw_instanced));
/* 3124 */     playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_attrib_location]", Boolean.valueOf(contextcapabilities.GL_ARB_explicit_attrib_location));
/* 3125 */     playerSnooper.addStatToSnooper("gl_caps[ARB_explicit_uniform_location]", Boolean.valueOf(contextcapabilities.GL_ARB_explicit_uniform_location));
/* 3126 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_layer_viewport]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_layer_viewport));
/* 3127 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_program));
/* 3128 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_shader));
/* 3129 */     playerSnooper.addStatToSnooper("gl_caps[ARB_fragment_program_shadow]", Boolean.valueOf(contextcapabilities.GL_ARB_fragment_program_shadow));
/* 3130 */     playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_framebuffer_object));
/* 3131 */     playerSnooper.addStatToSnooper("gl_caps[ARB_framebuffer_sRGB]", Boolean.valueOf(contextcapabilities.GL_ARB_framebuffer_sRGB));
/* 3132 */     playerSnooper.addStatToSnooper("gl_caps[ARB_geometry_shader4]", Boolean.valueOf(contextcapabilities.GL_ARB_geometry_shader4));
/* 3133 */     playerSnooper.addStatToSnooper("gl_caps[ARB_gpu_shader5]", Boolean.valueOf(contextcapabilities.GL_ARB_gpu_shader5));
/* 3134 */     playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_pixel]", Boolean.valueOf(contextcapabilities.GL_ARB_half_float_pixel));
/* 3135 */     playerSnooper.addStatToSnooper("gl_caps[ARB_half_float_vertex]", Boolean.valueOf(contextcapabilities.GL_ARB_half_float_vertex));
/* 3136 */     playerSnooper.addStatToSnooper("gl_caps[ARB_instanced_arrays]", Boolean.valueOf(contextcapabilities.GL_ARB_instanced_arrays));
/* 3137 */     playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_alignment]", Boolean.valueOf(contextcapabilities.GL_ARB_map_buffer_alignment));
/* 3138 */     playerSnooper.addStatToSnooper("gl_caps[ARB_map_buffer_range]", Boolean.valueOf(contextcapabilities.GL_ARB_map_buffer_range));
/* 3139 */     playerSnooper.addStatToSnooper("gl_caps[ARB_multisample]", Boolean.valueOf(contextcapabilities.GL_ARB_multisample));
/* 3140 */     playerSnooper.addStatToSnooper("gl_caps[ARB_multitexture]", Boolean.valueOf(contextcapabilities.GL_ARB_multitexture));
/* 3141 */     playerSnooper.addStatToSnooper("gl_caps[ARB_occlusion_query2]", Boolean.valueOf(contextcapabilities.GL_ARB_occlusion_query2));
/* 3142 */     playerSnooper.addStatToSnooper("gl_caps[ARB_pixel_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_pixel_buffer_object));
/* 3143 */     playerSnooper.addStatToSnooper("gl_caps[ARB_seamless_cube_map]", Boolean.valueOf(contextcapabilities.GL_ARB_seamless_cube_map));
/* 3144 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_objects]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_objects));
/* 3145 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_stencil_export]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_stencil_export));
/* 3146 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shader_texture_lod]", Boolean.valueOf(contextcapabilities.GL_ARB_shader_texture_lod));
/* 3147 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shadow]", Boolean.valueOf(contextcapabilities.GL_ARB_shadow));
/* 3148 */     playerSnooper.addStatToSnooper("gl_caps[ARB_shadow_ambient]", Boolean.valueOf(contextcapabilities.GL_ARB_shadow_ambient));
/* 3149 */     playerSnooper.addStatToSnooper("gl_caps[ARB_stencil_texturing]", Boolean.valueOf(contextcapabilities.GL_ARB_stencil_texturing));
/* 3150 */     playerSnooper.addStatToSnooper("gl_caps[ARB_sync]", Boolean.valueOf(contextcapabilities.GL_ARB_sync));
/* 3151 */     playerSnooper.addStatToSnooper("gl_caps[ARB_tessellation_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_tessellation_shader));
/* 3152 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_border_clamp]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_border_clamp));
/* 3153 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_buffer_object));
/* 3154 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_cube_map));
/* 3155 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_cube_map_array]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_cube_map_array));
/* 3156 */     playerSnooper.addStatToSnooper("gl_caps[ARB_texture_non_power_of_two]", Boolean.valueOf(contextcapabilities.GL_ARB_texture_non_power_of_two));
/* 3157 */     playerSnooper.addStatToSnooper("gl_caps[ARB_uniform_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_uniform_buffer_object));
/* 3158 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_blend]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_blend));
/* 3159 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_buffer_object]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_buffer_object));
/* 3160 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_program]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_program));
/* 3161 */     playerSnooper.addStatToSnooper("gl_caps[ARB_vertex_shader]", Boolean.valueOf(contextcapabilities.GL_ARB_vertex_shader));
/* 3162 */     playerSnooper.addStatToSnooper("gl_caps[EXT_bindable_uniform]", Boolean.valueOf(contextcapabilities.GL_EXT_bindable_uniform));
/* 3163 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_equation_separate]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_equation_separate));
/* 3164 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_func_separate]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_func_separate));
/* 3165 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_minmax]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_minmax));
/* 3166 */     playerSnooper.addStatToSnooper("gl_caps[EXT_blend_subtract]", Boolean.valueOf(contextcapabilities.GL_EXT_blend_subtract));
/* 3167 */     playerSnooper.addStatToSnooper("gl_caps[EXT_draw_instanced]", Boolean.valueOf(contextcapabilities.GL_EXT_draw_instanced));
/* 3168 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_multisample]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_multisample));
/* 3169 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_object]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_object));
/* 3170 */     playerSnooper.addStatToSnooper("gl_caps[EXT_framebuffer_sRGB]", Boolean.valueOf(contextcapabilities.GL_EXT_framebuffer_sRGB));
/* 3171 */     playerSnooper.addStatToSnooper("gl_caps[EXT_geometry_shader4]", Boolean.valueOf(contextcapabilities.GL_EXT_geometry_shader4));
/* 3172 */     playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_program_parameters]", Boolean.valueOf(contextcapabilities.GL_EXT_gpu_program_parameters));
/* 3173 */     playerSnooper.addStatToSnooper("gl_caps[EXT_gpu_shader4]", Boolean.valueOf(contextcapabilities.GL_EXT_gpu_shader4));
/* 3174 */     playerSnooper.addStatToSnooper("gl_caps[EXT_multi_draw_arrays]", Boolean.valueOf(contextcapabilities.GL_EXT_multi_draw_arrays));
/* 3175 */     playerSnooper.addStatToSnooper("gl_caps[EXT_packed_depth_stencil]", Boolean.valueOf(contextcapabilities.GL_EXT_packed_depth_stencil));
/* 3176 */     playerSnooper.addStatToSnooper("gl_caps[EXT_paletted_texture]", Boolean.valueOf(contextcapabilities.GL_EXT_paletted_texture));
/* 3177 */     playerSnooper.addStatToSnooper("gl_caps[EXT_rescale_normal]", Boolean.valueOf(contextcapabilities.GL_EXT_rescale_normal));
/* 3178 */     playerSnooper.addStatToSnooper("gl_caps[EXT_separate_shader_objects]", Boolean.valueOf(contextcapabilities.GL_EXT_separate_shader_objects));
/* 3179 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shader_image_load_store]", Boolean.valueOf(contextcapabilities.GL_EXT_shader_image_load_store));
/* 3180 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shadow_funcs]", Boolean.valueOf(contextcapabilities.GL_EXT_shadow_funcs));
/* 3181 */     playerSnooper.addStatToSnooper("gl_caps[EXT_shared_texture_palette]", Boolean.valueOf(contextcapabilities.GL_EXT_shared_texture_palette));
/* 3182 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_clear_tag]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_clear_tag));
/* 3183 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_two_side]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_two_side));
/* 3184 */     playerSnooper.addStatToSnooper("gl_caps[EXT_stencil_wrap]", Boolean.valueOf(contextcapabilities.GL_EXT_stencil_wrap));
/* 3185 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_3d]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_3d));
/* 3186 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_array]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_array));
/* 3187 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_buffer_object]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_buffer_object));
/* 3188 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_integer]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_integer));
/* 3189 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_lod_bias]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_lod_bias));
/* 3190 */     playerSnooper.addStatToSnooper("gl_caps[EXT_texture_sRGB]", Boolean.valueOf(contextcapabilities.GL_EXT_texture_sRGB));
/* 3191 */     playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_shader]", Boolean.valueOf(contextcapabilities.GL_EXT_vertex_shader));
/* 3192 */     playerSnooper.addStatToSnooper("gl_caps[EXT_vertex_weighting]", Boolean.valueOf(contextcapabilities.GL_EXT_vertex_weighting));
/* 3193 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_uniforms]", Integer.valueOf(GlStateManager.glGetInteger(35658)));
/* 3194 */     GlStateManager.glGetError();
/* 3195 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_fragment_uniforms]", Integer.valueOf(GlStateManager.glGetInteger(35657)));
/* 3196 */     GlStateManager.glGetError();
/* 3197 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_attribs]", Integer.valueOf(GlStateManager.glGetInteger(34921)));
/* 3198 */     GlStateManager.glGetError();
/* 3199 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_vertex_texture_image_units]", Integer.valueOf(GlStateManager.glGetInteger(35660)));
/* 3200 */     GlStateManager.glGetError();
/* 3201 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_texture_image_units]", Integer.valueOf(GlStateManager.glGetInteger(34930)));
/* 3202 */     GlStateManager.glGetError();
/* 3203 */     playerSnooper.addStatToSnooper("gl_caps[gl_max_array_texture_layers]", Integer.valueOf(GlStateManager.glGetInteger(35071)));
/* 3204 */     GlStateManager.glGetError();
/* 3205 */     playerSnooper.addStatToSnooper("gl_max_texture_size", Integer.valueOf(getGLMaximumTextureSize()));
/* 3206 */     GameProfile gameprofile = session.getProfile();
/*      */     
/* 3208 */     if (gameprofile != null && gameprofile.getId() != null)
/*      */     {
/* 3210 */       playerSnooper.addStatToSnooper("uuid", Hashing.sha1().hashBytes(gameprofile.getId().toString().getBytes(Charsets.ISO_8859_1)).toString());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getGLMaximumTextureSize() {
/* 3219 */     for (int i = 16384; i > 0; i >>= 1) {
/*      */       
/* 3221 */       GlStateManager.glTexImage2D(32868, 0, 6408, i, i, 0, 6408, 5121, null);
/* 3222 */       int j = GlStateManager.glGetTexLevelParameteri(32868, 0, 4096);
/*      */       
/* 3224 */       if (j != 0)
/*      */       {
/* 3226 */         return i;
/*      */       }
/*      */     } 
/*      */     
/* 3230 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSnooperEnabled() {
/* 3238 */     return this.gameSettings.snooperEnabled;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerData(ServerData serverDataIn) {
/* 3246 */     this.currentServerData = serverDataIn;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public ServerData getCurrentServerData() {
/* 3252 */     return this.currentServerData;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isIntegratedServerRunning() {
/* 3257 */     return this.integratedServerIsRunning;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSingleplayer() {
/* 3265 */     return (this.integratedServerIsRunning && this.theIntegratedServer != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public IntegratedServer getIntegratedServer() {
/* 3275 */     return this.theIntegratedServer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void stopIntegratedServer() {
/* 3280 */     if (theMinecraft != null) {
/*      */       
/* 3282 */       IntegratedServer integratedserver = theMinecraft.getIntegratedServer();
/*      */       
/* 3284 */       if (integratedserver != null)
/*      */       {
/* 3286 */         integratedserver.stopServer();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Snooper getPlayerUsageSnooper() {
/* 3296 */     return this.usageSnooper;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long getSystemTime() {
/* 3304 */     return Sys.getTime() * 1000L / Sys.getTimerResolution();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isFullScreen() {
/* 3312 */     return this.fullscreen;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Session getSession() {
/* 3317 */     return session;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PropertyMap getProfileProperties() {
/* 3325 */     if (this.profileProperties.isEmpty()) {
/*      */       
/* 3327 */       GameProfile gameprofile = getSessionService().fillProfileProperties(session.getProfile(), false);
/* 3328 */       this.profileProperties.putAll((Multimap)gameprofile.getProperties());
/*      */     } 
/*      */     
/* 3331 */     return this.profileProperties;
/*      */   }
/*      */ 
/*      */   
/*      */   public Proxy getProxy() {
/* 3336 */     return this.proxy;
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureManager getTextureManager() {
/* 3341 */     return this.renderEngine;
/*      */   }
/*      */ 
/*      */   
/*      */   public IResourceManager getResourceManager() {
/* 3346 */     return (IResourceManager)this.mcResourceManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public ResourcePackRepository getResourcePackRepository() {
/* 3351 */     return this.mcResourcePackRepository;
/*      */   }
/*      */ 
/*      */   
/*      */   public LanguageManager getLanguageManager() {
/* 3356 */     return this.mcLanguageManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public TextureMap getTextureMapBlocks() {
/* 3361 */     return this.textureMapBlocks;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isJava64bit() {
/* 3366 */     return this.jvm64bit;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isGamePaused() {
/* 3371 */     return this.isGamePaused;
/*      */   }
/*      */ 
/*      */   
/*      */   public SoundHandler getSoundHandler() {
/* 3376 */     return this.mcSoundHandler;
/*      */   }
/*      */ 
/*      */   
/*      */   public MusicTicker.MusicType getAmbientMusicType() {
/* 3381 */     if (this.currentScreen instanceof net.minecraft.client.gui.GuiWinGame)
/*      */     {
/* 3383 */       return MusicTicker.MusicType.CREDITS;
/*      */     }
/* 3385 */     if (this.player != null) {
/*      */       
/* 3387 */       if (this.player.world.provider instanceof net.minecraft.world.WorldProviderHell)
/*      */       {
/* 3389 */         return MusicTicker.MusicType.NETHER;
/*      */       }
/* 3391 */       if (this.player.world.provider instanceof net.minecraft.world.WorldProviderEnd)
/*      */       {
/* 3393 */         return this.ingameGUI.getBossOverlay().shouldPlayEndBossMusic() ? MusicTicker.MusicType.END_BOSS : MusicTicker.MusicType.END;
/*      */       }
/*      */ 
/*      */       
/* 3397 */       return (this.player.capabilities.isCreativeMode && this.player.capabilities.allowFlying) ? MusicTicker.MusicType.CREATIVE : MusicTicker.MusicType.GAME;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3402 */     return MusicTicker.MusicType.MENU;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void dispatchKeypresses() {
/* 3408 */     int i = (Keyboard.getEventKey() == 0) ? (Keyboard.getEventCharacter() + 256) : Keyboard.getEventKey();
/*      */     
/* 3410 */     if (i != 0 && !Keyboard.isRepeatEvent())
/*      */     {
/* 3412 */       if (!(this.currentScreen instanceof GuiControls) || ((GuiControls)this.currentScreen).time <= getSystemTime() - 20L)
/*      */       {
/* 3414 */         if (Keyboard.getEventKeyState())
/*      */         {
/* 3416 */           if (i == this.gameSettings.keyBindFullscreen.getKeyCode()) {
/*      */             
/* 3418 */             toggleFullscreen();
/*      */           }
/* 3420 */           else if (i == this.gameSettings.keyBindScreenshot.getKeyCode()) {
/*      */             
/* 3422 */             this.ingameGUI.getChatGUI().printChatMessage(ScreenShotHelper.saveScreenshot(this.mcDataDir, this.displayWidth, this.displayHeight, this.framebufferMc));
/*      */           }
/* 3424 */           else if (i == 48 && GuiScreen.isCtrlKeyDown() && (this.currentScreen == null || (this.currentScreen != null && !this.currentScreen.func_193976_p()))) {
/*      */             
/* 3426 */             this.gameSettings.setOptionValue(GameSettings.Options.NARRATOR, 1);
/*      */             
/* 3428 */             if (this.currentScreen instanceof ScreenChatOptions)
/*      */             {
/* 3430 */               ((ScreenChatOptions)this.currentScreen).func_193024_a();
/*      */             }
/*      */           } 
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public MinecraftSessionService getSessionService() {
/* 3440 */     return this.sessionService;
/*      */   }
/*      */ 
/*      */   
/*      */   public SkinManager getSkinManager() {
/* 3445 */     return this.skinManager;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Entity getRenderViewEntity() {
/* 3451 */     return this.renderViewEntity;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setRenderViewEntity(Entity viewingEntity) {
/* 3456 */     this.renderViewEntity = viewingEntity;
/* 3457 */     this.entityRenderer.loadEntityShader(viewingEntity);
/*      */   }
/*      */ 
/*      */   
/*      */   public <V> ListenableFuture<V> addScheduledTask(Callable<V> callableToSchedule) {
/* 3462 */     Validate.notNull(callableToSchedule);
/*      */     
/* 3464 */     if (isCallingFromMinecraftThread()) {
/*      */       
/*      */       try {
/*      */         
/* 3468 */         return Futures.immediateFuture(callableToSchedule.call());
/*      */       }
/* 3470 */       catch (Exception exception) {
/*      */         
/* 3472 */         return (ListenableFuture<V>)Futures.immediateFailedCheckedFuture(exception);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 3477 */     ListenableFutureTask<V> listenablefuturetask = ListenableFutureTask.create(callableToSchedule);
/*      */     
/* 3479 */     synchronized (this.scheduledTasks) {
/*      */       
/* 3481 */       this.scheduledTasks.add(listenablefuturetask);
/* 3482 */       return (ListenableFuture<V>)listenablefuturetask;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
/* 3489 */     Validate.notNull(runnableToSchedule);
/* 3490 */     return addScheduledTask(Executors.callable(runnableToSchedule));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCallingFromMinecraftThread() {
/* 3495 */     return (Thread.currentThread() == this.mcThread);
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockRendererDispatcher getBlockRendererDispatcher() {
/* 3500 */     return this.blockRenderDispatcher;
/*      */   }
/*      */ 
/*      */   
/*      */   public RenderManager getRenderManager() {
/* 3505 */     return this.renderManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public RenderItem getRenderItem() {
/* 3510 */     return this.renderItem;
/*      */   }
/*      */ 
/*      */   
/*      */   public ItemRenderer getItemRenderer() {
/* 3515 */     return this.itemRenderer;
/*      */   }
/*      */ 
/*      */   
/*      */   public <T> ISearchTree<T> func_193987_a(SearchTreeManager.Key<T> p_193987_1_) {
/* 3520 */     return this.field_193995_ae.func_194010_a(p_193987_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getDebugFPS() {
/* 3525 */     return debugFPS;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public FrameTimer getFrameTimer() {
/* 3533 */     return this.frameTimer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isConnectedToRealms() {
/* 3541 */     return this.connectedToRealms;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConnectedToRealms(boolean isConnected) {
/* 3549 */     this.connectedToRealms = isConnected;
/*      */   }
/*      */ 
/*      */   
/*      */   public DataFixer getDataFixer() {
/* 3554 */     return this.dataFixer;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getRenderPartialTicks() {
/* 3559 */     return this.timer.field_194147_b;
/*      */   }
/*      */ 
/*      */   
/*      */   public float func_193989_ak() {
/* 3564 */     return this.timer.field_194148_c;
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockColors getBlockColors() {
/* 3569 */     return this.blockColors;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReducedDebug() {
/* 3577 */     return !((this.player == null || !this.player.hasReducedDebug()) && !this.gameSettings.reducedDebugInfo);
/*      */   }
/*      */ 
/*      */   
/*      */   public GuiToast func_193033_an() {
/* 3582 */     return this.field_193034_aS;
/*      */   }
/*      */ 
/*      */   
/*      */   public Tutorial func_193032_ao() {
/* 3587 */     return this.field_193035_aW;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean login(String username) {
/* 3592 */     getMinecraft(); session.username = username;
/* 3593 */     getMinecraft(); session.token = "0";
/* 3594 */     return true;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\Minecraft.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */