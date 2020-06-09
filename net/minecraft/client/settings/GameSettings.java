/*      */ package net.minecraft.client.settings;
/*      */ 
/*      */ import com.google.common.base.Splitter;
/*      */ import com.google.common.collect.ImmutableSet;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.gson.Gson;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.io.PrintWriter;
/*      */ import java.lang.reflect.ParameterizedType;
/*      */ import java.lang.reflect.Type;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.Arrays;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.GuiNewChat;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.chat.NarratorChatListener;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.tutorial.TutorialSteps;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EnumPlayerModelParts;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.client.CPacketClientSettings;
/*      */ import net.minecraft.util.EnumHandSide;
/*      */ import net.minecraft.util.JsonUtils;
/*      */ import net.minecraft.util.SoundCategory;
/*      */ import net.minecraft.util.datafix.FixTypes;
/*      */ import net.minecraft.util.datafix.IFixType;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.world.EnumDifficulty;
/*      */ import net.minecraft.world.World;
/*      */ import optifine.ClearWater;
/*      */ import optifine.Config;
/*      */ import optifine.CustomColors;
/*      */ import optifine.CustomGuis;
/*      */ import optifine.CustomSky;
/*      */ import optifine.DynamicLights;
/*      */ import optifine.Lang;
/*      */ import optifine.NaturalTextures;
/*      */ import optifine.RandomMobs;
/*      */ import optifine.Reflector;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.apache.commons.lang3.ArrayUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.DisplayMode;
/*      */ import shadersmod.client.Shaders;
/*      */ 
/*      */ public class GameSettings {
/*   66 */   private static final Logger LOGGER = LogManager.getLogger();
/*   67 */   private static final Gson GSON = new Gson();
/*   68 */   private static final Type TYPE_LIST_STRING = new ParameterizedType()
/*      */     {
/*      */       public Type[] getActualTypeArguments()
/*      */       {
/*   72 */         return new Type[] { String.class };
/*      */       }
/*      */       
/*      */       public Type getRawType() {
/*   76 */         return List.class;
/*      */       }
/*      */       
/*      */       public Type getOwnerType() {
/*   80 */         return null;
/*      */       }
/*      */     };
/*   83 */   public static final Splitter COLON_SPLITTER = Splitter.on(':');
/*      */ 
/*      */   
/*   86 */   private static final String[] GUISCALES = new String[] { "options.guiScale.auto", "options.guiScale.small", "options.guiScale.normal", "options.guiScale.large" };
/*   87 */   private static final String[] PARTICLES = new String[] { "options.particles.all", "options.particles.decreased", "options.particles.minimal" };
/*   88 */   private static final String[] AMBIENT_OCCLUSIONS = new String[] { "options.ao.off", "options.ao.min", "options.ao.max" };
/*   89 */   private static final String[] CLOUDS_TYPES = new String[] { "options.off", "options.clouds.fast", "options.clouds.fancy" };
/*   90 */   private static final String[] ATTACK_INDICATORS = new String[] { "options.off", "options.attack.crosshair", "options.attack.hotbar" };
/*   91 */   public static final String[] field_193632_b = new String[] { "options.narrator.off", "options.narrator.all", "options.narrator.chat", "options.narrator.system" };
/*   92 */   public float mouseSensitivity = 0.5F;
/*      */   public boolean invertMouse;
/*   94 */   public int renderDistanceChunks = -1;
/*      */   public boolean viewBobbing = true;
/*      */   public boolean anaglyph;
/*      */   public boolean fboEnable = true;
/*   98 */   public int limitFramerate = 120;
/*      */ 
/*      */   
/*  101 */   public int clouds = 2;
/*      */   
/*      */   public boolean fancyGraphics = true;
/*      */   
/*  105 */   public int ambientOcclusion = 2;
/*  106 */   public List<String> resourcePacks = Lists.newArrayList();
/*  107 */   public List<String> incompatibleResourcePacks = Lists.newArrayList();
/*  108 */   public EntityPlayer.EnumChatVisibility chatVisibility = EntityPlayer.EnumChatVisibility.FULL;
/*      */   public boolean chatColours = true;
/*      */   public boolean chatLinks = true;
/*      */   public boolean chatLinksPrompt = true;
/*  112 */   public float chatOpacity = 1.0F;
/*      */   
/*      */   public boolean snooperEnabled = false;
/*      */   
/*      */   public boolean fullScreen;
/*      */   
/*      */   public boolean enableVsync = true;
/*      */   
/*      */   public boolean useVbo = true;
/*      */   
/*      */   public boolean reducedDebugInfo;
/*      */   
/*      */   public boolean hideServerAddress;
/*      */   public boolean advancedItemTooltips;
/*      */   public boolean pauseOnLostFocus = true;
/*  127 */   private final Set<EnumPlayerModelParts> setModelParts = Sets.newHashSet((Object[])EnumPlayerModelParts.values());
/*      */   public boolean touchscreen;
/*  129 */   public EnumHandSide mainHand = EnumHandSide.RIGHT;
/*      */   public int overrideWidth;
/*      */   public int overrideHeight;
/*      */   public boolean heldItemTooltips = true;
/*  133 */   public float chatScale = 1.0F;
/*  134 */   public float chatWidth = 1.0F;
/*  135 */   public float chatHeightUnfocused = 0.44366196F;
/*  136 */   public float chatHeightFocused = 1.0F;
/*  137 */   public int mipmapLevels = 4;
/*  138 */   private final Map<SoundCategory, Float> soundLevels = Maps.newEnumMap(SoundCategory.class);
/*      */   public boolean useNativeTransport = true;
/*      */   public boolean entityShadows = true;
/*  141 */   public int attackIndicator = 1;
/*      */   public boolean enableWeakAttacks;
/*      */   public boolean showSubtitles;
/*      */   public boolean realmsNotifications = false;
/*      */   public boolean autoJump = false;
/*  146 */   public TutorialSteps field_193631_S = TutorialSteps.MOVEMENT;
/*  147 */   public KeyBinding keyBindForward = new KeyBinding("key.forward", 17, "key.categories.movement");
/*  148 */   public KeyBinding keyBindLeft = new KeyBinding("key.left", 30, "key.categories.movement");
/*  149 */   public KeyBinding keyBindBack = new KeyBinding("key.back", 31, "key.categories.movement");
/*  150 */   public KeyBinding keyBindRight = new KeyBinding("key.right", 32, "key.categories.movement");
/*  151 */   public KeyBinding keyBindJump = new KeyBinding("key.jump", 57, "key.categories.movement");
/*  152 */   public KeyBinding keyBindSneak = new KeyBinding("key.sneak", 42, "key.categories.movement");
/*  153 */   public KeyBinding keyBindSprint = new KeyBinding("key.sprint", 29, "key.categories.movement");
/*  154 */   public KeyBinding keyBindInventory = new KeyBinding("key.inventory", 18, "key.categories.inventory");
/*  155 */   public KeyBinding keyBindSwapHands = new KeyBinding("key.swapHands", 33, "key.categories.inventory");
/*  156 */   public KeyBinding keyBindDrop = new KeyBinding("key.drop", 16, "key.categories.inventory");
/*  157 */   public KeyBinding keyBindUseItem = new KeyBinding("key.use", -99, "key.categories.gameplay");
/*  158 */   public KeyBinding keyBindAttack = new KeyBinding("key.attack", -100, "key.categories.gameplay");
/*  159 */   public KeyBinding keyBindPickBlock = new KeyBinding("key.pickItem", -98, "key.categories.gameplay");
/*  160 */   public KeyBinding keyBindChat = new KeyBinding("key.chat", 20, "key.categories.multiplayer");
/*  161 */   public KeyBinding keyBindPlayerList = new KeyBinding("key.playerlist", 15, "key.categories.multiplayer");
/*  162 */   public KeyBinding keyBindCommand = new KeyBinding("key.command", 53, "key.categories.multiplayer");
/*  163 */   public KeyBinding keyBindScreenshot = new KeyBinding("key.screenshot", 60, "key.categories.misc");
/*  164 */   public KeyBinding keyBindTogglePerspective = new KeyBinding("key.togglePerspective", 63, "key.categories.misc");
/*  165 */   public KeyBinding keyBindSmoothCamera = new KeyBinding("key.smoothCamera", 0, "key.categories.misc");
/*  166 */   public KeyBinding keyBindFullscreen = new KeyBinding("key.fullscreen", 87, "key.categories.misc");
/*  167 */   public KeyBinding keyBindSpectatorOutlines = new KeyBinding("key.spectatorOutlines", 0, "key.categories.misc");
/*  168 */   public KeyBinding field_194146_ao = new KeyBinding("key.advancements", 38, "key.categories.misc");
/*  169 */   public KeyBinding[] keyBindsHotbar = new KeyBinding[] { new KeyBinding("key.hotbar.1", 2, "key.categories.inventory"), new KeyBinding("key.hotbar.2", 3, "key.categories.inventory"), new KeyBinding("key.hotbar.3", 4, "key.categories.inventory"), new KeyBinding("key.hotbar.4", 5, "key.categories.inventory"), new KeyBinding("key.hotbar.5", 6, "key.categories.inventory"), new KeyBinding("key.hotbar.6", 7, "key.categories.inventory"), new KeyBinding("key.hotbar.7", 8, "key.categories.inventory"), new KeyBinding("key.hotbar.8", 9, "key.categories.inventory"), new KeyBinding("key.hotbar.9", 10, "key.categories.inventory") };
/*  170 */   public KeyBinding field_193629_ap = new KeyBinding("key.saveToolbarActivator", 46, "key.categories.creative");
/*  171 */   public KeyBinding field_193630_aq = new KeyBinding("key.loadToolbarActivator", 45, "key.categories.creative");
/*      */   
/*      */   public KeyBinding[] keyBindings;
/*      */   
/*      */   protected Minecraft mc;
/*      */   
/*      */   private File optionsFile;
/*      */   
/*      */   public EnumDifficulty difficulty;
/*      */   
/*      */   public boolean hideGUI;
/*      */   
/*      */   public int thirdPersonView;
/*      */   public boolean showDebugInfo;
/*      */   public boolean showDebugProfilerChart;
/*      */   public boolean showLagometer;
/*      */   public String lastServer;
/*      */   public boolean smoothCamera;
/*      */   public boolean debugCamEnable;
/*  190 */   public float fovSetting = 110.0F;
/*  191 */   public float gammaSetting = 999.0F;
/*      */   
/*      */   public float saturation;
/*      */   
/*  195 */   public int guiScale = 2;
/*      */   
/*      */   public int particleSetting;
/*      */   
/*      */   public int field_192571_R;
/*      */   
/*      */   public String language;
/*      */   
/*      */   public boolean forceUnicodeFont;
/*  204 */   public int ofFogType = 1;
/*  205 */   public float ofFogStart = 0.8F;
/*  206 */   public int ofMipmapType = 0;
/*      */   public boolean ofOcclusionFancy = false;
/*      */   public boolean ofSmoothFps = false;
/*  209 */   public boolean ofSmoothWorld = Config.isSingleProcessor();
/*  210 */   public boolean ofLazyChunkLoading = Config.isSingleProcessor();
/*  211 */   public float ofAoLevel = 1.0F;
/*  212 */   public int ofAaLevel = 0;
/*  213 */   public int ofAfLevel = 1;
/*  214 */   public int ofClouds = 0;
/*  215 */   public float ofCloudsHeight = 0.0F;
/*  216 */   public int ofTrees = 0;
/*  217 */   public int ofRain = 0;
/*  218 */   public int ofDroppedItems = 0;
/*  219 */   public int ofBetterGrass = 3;
/*  220 */   public int ofAutoSaveTicks = 4000;
/*      */   public boolean ofLagometer = false;
/*      */   public boolean ofProfiler = false;
/*      */   public boolean ofShowFps = false;
/*      */   public boolean ofWeather = true;
/*      */   public boolean ofSky = true;
/*      */   public boolean ofStars = true;
/*      */   public boolean ofSunMoon = true;
/*  228 */   public int ofVignette = 0;
/*  229 */   public int ofChunkUpdates = 1;
/*      */   public boolean ofChunkUpdatesDynamic = false;
/*  231 */   public int ofTime = 0;
/*      */   public boolean ofClearWater = false;
/*      */   public boolean ofBetterSnow = false;
/*  234 */   public String ofFullscreenMode = "Default";
/*      */   public boolean ofSwampColors = true;
/*      */   public boolean ofRandomMobs = true;
/*      */   public boolean ofSmoothBiomes = true;
/*      */   public boolean ofCustomFonts = true;
/*      */   public boolean ofCustomColors = true;
/*      */   public boolean ofCustomSky = true;
/*      */   public boolean ofShowCapes = true;
/*  242 */   public int ofConnectedTextures = 2;
/*      */   public boolean ofCustomItems = true;
/*      */   public boolean ofNaturalTextures = false;
/*      */   public boolean ofFastMath = false;
/*      */   public boolean ofFastRender = false;
/*  247 */   public int ofTranslucentBlocks = 0;
/*      */   public boolean ofDynamicFov = true;
/*      */   public boolean ofAlternateBlocks = true;
/*  250 */   public int ofDynamicLights = 3;
/*      */   public boolean ofCustomEntityModels = true;
/*      */   public boolean ofCustomGuis = true;
/*  253 */   public int ofScreenshotSize = 1;
/*  254 */   public int ofAnimatedWater = 0;
/*  255 */   public int ofAnimatedLava = 0;
/*      */   public boolean ofAnimatedFire = true;
/*      */   public boolean ofAnimatedPortal = true;
/*      */   public boolean ofAnimatedRedstone = true;
/*      */   public boolean ofAnimatedExplosion = true;
/*      */   public boolean ofAnimatedFlame = true;
/*      */   public boolean ofAnimatedSmoke = true;
/*      */   public boolean ofVoidParticles = true;
/*      */   public boolean ofWaterParticles = true;
/*      */   public boolean ofRainSplash = true;
/*      */   public boolean ofPortalParticles = true;
/*      */   public boolean ofPotionParticles = true;
/*      */   public boolean ofFireworkParticles = true;
/*      */   public boolean ofDrippingWaterLava = true;
/*      */   public boolean ofAnimatedTerrain = true;
/*      */   public boolean ofAnimatedTextures = true;
/*      */   public static final int DEFAULT = 0;
/*      */   public static final int FAST = 1;
/*      */   public static final int FANCY = 2;
/*      */   public static final int OFF = 3;
/*      */   public static final int SMART = 4;
/*      */   public static final int ANIM_ON = 0;
/*      */   public static final int ANIM_GENERATED = 1;
/*      */   public static final int ANIM_OFF = 2;
/*      */   public static final String DEFAULT_STR = "Default";
/*  280 */   private static final int[] OF_TREES_VALUES = new int[] { 0, 1, 4, 2 };
/*  281 */   private static final int[] OF_DYNAMIC_LIGHTS = new int[] { 3, 1, 2 };
/*  282 */   private static final String[] KEYS_DYNAMIC_LIGHTS = new String[] { "options.off", "options.graphics.fast", "options.graphics.fancy" };
/*      */   
/*      */   public KeyBinding ofKeyBindZoom;
/*      */   private File optionsFileOF;
/*      */   private boolean needsResourceRefresh = false;
/*      */   
/*      */   public GameSettings(Minecraft mcIn, File optionsFileIn) {
/*  289 */     setForgeKeybindProperties();
/*  290 */     this.keyBindings = (KeyBinding[])ArrayUtils.addAll((Object[])new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindSprint, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindFullscreen, this.keyBindSpectatorOutlines, this.keyBindSwapHands, this.field_193629_ap, this.field_193630_aq, this.field_194146_ao }, (Object[])this.keyBindsHotbar);
/*  291 */     this.difficulty = EnumDifficulty.NORMAL;
/*  292 */     this.lastServer = "nzxter.tk";
/*  293 */     this.fovSetting = 110.0F;
/*  294 */     this.language = "en_us";
/*  295 */     this.mc = mcIn;
/*  296 */     this.optionsFile = new File(optionsFileIn, "options.txt");
/*      */     
/*  298 */     if (mcIn.isJava64bit() && Runtime.getRuntime().maxMemory() >= 1000000000L) {
/*      */       
/*  300 */       Options.RENDER_DISTANCE.setValueMax(32.0F);
/*      */     }
/*      */     else {
/*      */       
/*  304 */       Options.RENDER_DISTANCE.setValueMax(16.0F);
/*      */     } 
/*      */     
/*  307 */     this.renderDistanceChunks = mcIn.isJava64bit() ? 12 : 8;
/*  308 */     this.optionsFileOF = new File(optionsFileIn, "optionsof.txt");
/*  309 */     this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
/*  310 */     this.ofKeyBindZoom = new KeyBinding("of.key.zoom", 46, "key.categories.misc");
/*  311 */     this.keyBindings = (KeyBinding[])ArrayUtils.add((Object[])this.keyBindings, this.ofKeyBindZoom);
/*  312 */     Options.RENDER_DISTANCE.setValueMax(32.0F);
/*  313 */     this.renderDistanceChunks = 8;
/*  314 */     loadOptions();
/*  315 */     Config.initGameSettings(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public GameSettings() {
/*  320 */     setForgeKeybindProperties();
/*  321 */     this.keyBindings = (KeyBinding[])ArrayUtils.addAll((Object[])new KeyBinding[] { this.keyBindAttack, this.keyBindUseItem, this.keyBindForward, this.keyBindLeft, this.keyBindBack, this.keyBindRight, this.keyBindJump, this.keyBindSneak, this.keyBindSprint, this.keyBindDrop, this.keyBindInventory, this.keyBindChat, this.keyBindPlayerList, this.keyBindPickBlock, this.keyBindCommand, this.keyBindScreenshot, this.keyBindTogglePerspective, this.keyBindSmoothCamera, this.keyBindFullscreen, this.keyBindSpectatorOutlines, this.keyBindSwapHands, this.field_193629_ap, this.field_193630_aq, this.field_194146_ao }, (Object[])this.keyBindsHotbar);
/*  322 */     this.difficulty = EnumDifficulty.NORMAL;
/*  323 */     this.lastServer = "nzxter.tk";
/*  324 */     this.fovSetting = 110.0F;
/*  325 */     this.language = "en_us";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getKeyDisplayString(int key) {
/*  333 */     if (key < 0) {
/*      */       
/*  335 */       switch (key) {
/*      */         
/*      */         case -100:
/*  338 */           return I18n.format("key.mouse.left", new Object[0]);
/*      */         
/*      */         case -99:
/*  341 */           return I18n.format("key.mouse.right", new Object[0]);
/*      */         
/*      */         case -98:
/*  344 */           return I18n.format("key.mouse.middle", new Object[0]);
/*      */       } 
/*      */       
/*  347 */       return I18n.format("key.mouseButton", new Object[] { Integer.valueOf(key + 101) });
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  352 */     return (key < 256) ? Keyboard.getKeyName(key) : String.format("%c", new Object[] { Character.valueOf((char)(key - 256)) }).toUpperCase();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isKeyDown(KeyBinding key) {
/*  361 */     int i = key.getKeyCode();
/*      */     
/*  363 */     if (i != 0 && i < 256)
/*      */     {
/*  365 */       return (i < 0) ? Mouse.isButtonDown(i + 100) : Keyboard.isKeyDown(i);
/*      */     }
/*      */ 
/*      */     
/*  369 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOptionKeyBinding(KeyBinding key, int keyCode) {
/*  378 */     key.setKeyCode(keyCode);
/*  379 */     saveOptions();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOptionFloatValue(Options settingsOption, float value) {
/*  387 */     setOptionFloatValueOF(settingsOption, value);
/*      */     
/*  389 */     if (settingsOption == Options.SENSITIVITY)
/*      */     {
/*  391 */       this.mouseSensitivity = value;
/*      */     }
/*      */     
/*  394 */     if (settingsOption == Options.FOV)
/*      */     {
/*  396 */       this.fovSetting = value;
/*      */     }
/*      */     
/*  399 */     if (settingsOption == Options.GAMMA)
/*      */     {
/*  401 */       this.gammaSetting = value;
/*      */     }
/*      */     
/*  404 */     if (settingsOption == Options.FRAMERATE_LIMIT) {
/*      */       
/*  406 */       this.limitFramerate = (int)value;
/*  407 */       this.enableVsync = false;
/*      */       
/*  409 */       if (this.limitFramerate <= 0) {
/*      */         
/*  411 */         this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
/*  412 */         this.enableVsync = true;
/*      */       } 
/*      */       
/*  415 */       updateVSync();
/*      */     } 
/*      */     
/*  418 */     if (settingsOption == Options.CHAT_OPACITY) {
/*      */       
/*  420 */       this.chatOpacity = value;
/*  421 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  424 */     if (settingsOption == Options.CHAT_HEIGHT_FOCUSED) {
/*      */       
/*  426 */       this.chatHeightFocused = value;
/*  427 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  430 */     if (settingsOption == Options.CHAT_HEIGHT_UNFOCUSED) {
/*      */       
/*  432 */       this.chatHeightUnfocused = value;
/*  433 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  436 */     if (settingsOption == Options.CHAT_WIDTH) {
/*      */       
/*  438 */       this.chatWidth = value;
/*  439 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  442 */     if (settingsOption == Options.CHAT_SCALE) {
/*      */       
/*  444 */       this.chatScale = value;
/*  445 */       this.mc.ingameGUI.getChatGUI().refreshChat();
/*      */     } 
/*      */     
/*  448 */     if (settingsOption == Options.MIPMAP_LEVELS) {
/*      */       
/*  450 */       int i = this.mipmapLevels;
/*  451 */       this.mipmapLevels = (int)value;
/*      */       
/*  453 */       if (i != value) {
/*      */         
/*  455 */         this.mc.getTextureMapBlocks().setMipmapLevels(this.mipmapLevels);
/*  456 */         this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/*  457 */         this.mc.getTextureMapBlocks().setBlurMipmapDirect(false, (this.mipmapLevels > 0));
/*  458 */         this.mc.scheduleResourcesRefresh();
/*      */       } 
/*      */     } 
/*      */     
/*  462 */     if (settingsOption == Options.RENDER_DISTANCE) {
/*      */       
/*  464 */       this.renderDistanceChunks = (int)value;
/*  465 */       this.mc.renderGlobal.setDisplayListEntitiesDirty();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOptionValue(Options settingsOption, int value) {
/*  474 */     setOptionValueOF(settingsOption, value);
/*      */     
/*  476 */     if (settingsOption == Options.RENDER_DISTANCE)
/*      */     {
/*  478 */       setOptionFloatValue(settingsOption, MathHelper.clamp((this.renderDistanceChunks + value), settingsOption.getValueMin(), settingsOption.getValueMax()));
/*      */     }
/*      */     
/*  481 */     if (settingsOption == Options.MAIN_HAND)
/*      */     {
/*  483 */       this.mainHand = this.mainHand.opposite();
/*      */     }
/*      */     
/*  486 */     if (settingsOption == Options.INVERT_MOUSE)
/*      */     {
/*  488 */       this.invertMouse = !this.invertMouse;
/*      */     }
/*      */     
/*  491 */     if (settingsOption == Options.GUI_SCALE) {
/*      */       
/*  493 */       this.guiScale += value;
/*      */       
/*  495 */       if (GuiScreen.isShiftKeyDown())
/*      */       {
/*  497 */         this.guiScale = 0;
/*      */       }
/*      */       
/*  500 */       DisplayMode displaymode = Config.getLargestDisplayMode();
/*  501 */       int i = displaymode.getWidth() / 320;
/*  502 */       int j = displaymode.getHeight() / 240;
/*  503 */       int k = Math.min(i, j);
/*      */       
/*  505 */       if (this.guiScale < 0)
/*      */       {
/*  507 */         this.guiScale = k - 1;
/*      */       }
/*      */       
/*  510 */       if (this.mc.isUnicode() && this.guiScale % 2 != 0)
/*      */       {
/*  512 */         this.guiScale += value;
/*      */       }
/*      */       
/*  515 */       if (this.guiScale < 0 || this.guiScale >= k)
/*      */       {
/*  517 */         this.guiScale = 0;
/*      */       }
/*      */     } 
/*      */     
/*  521 */     if (settingsOption == Options.PARTICLES)
/*      */     {
/*  523 */       this.particleSetting = (this.particleSetting + value) % 3;
/*      */     }
/*      */     
/*  526 */     if (settingsOption == Options.VIEW_BOBBING)
/*      */     {
/*  528 */       this.viewBobbing = !this.viewBobbing;
/*      */     }
/*      */     
/*  531 */     if (settingsOption == Options.RENDER_CLOUDS)
/*      */     {
/*  533 */       this.clouds = (this.clouds + value) % 3;
/*      */     }
/*      */     
/*  536 */     if (settingsOption == Options.FORCE_UNICODE_FONT) {
/*      */ 
/*      */       
/*  539 */       this.forceUnicodeFont = !this.forceUnicodeFont;
/*      */ 
/*      */       
/*  542 */       this.mc.fontRendererObj.setUnicodeFlag(!(!this.mc.getLanguageManager().isCurrentLocaleUnicode() && !this.forceUnicodeFont));
/*      */     } 
/*      */     
/*  545 */     if (settingsOption == Options.FBO_ENABLE)
/*      */     {
/*  547 */       this.fboEnable = !this.fboEnable;
/*      */     }
/*      */     
/*  550 */     if (settingsOption == Options.ANAGLYPH) {
/*      */       
/*  552 */       if (!this.anaglyph && Config.isShaders()) {
/*      */         
/*  554 */         Config.showGuiMessage(Lang.get("of.message.an.shaders1"), Lang.get("of.message.an.shaders2"));
/*      */         
/*      */         return;
/*      */       } 
/*  558 */       this.anaglyph = !this.anaglyph;
/*  559 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/*  562 */     if (settingsOption == Options.GRAPHICS) {
/*      */       
/*  564 */       this.fancyGraphics = !this.fancyGraphics;
/*  565 */       updateRenderClouds();
/*  566 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/*  569 */     if (settingsOption == Options.AMBIENT_OCCLUSION) {
/*      */       
/*  571 */       this.ambientOcclusion = (this.ambientOcclusion + value) % 3;
/*  572 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/*  575 */     if (settingsOption == Options.CHAT_VISIBILITY)
/*      */     {
/*  577 */       this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility((this.chatVisibility.getChatVisibility() + value) % 3);
/*      */     }
/*      */     
/*  580 */     if (settingsOption == Options.CHAT_COLOR)
/*      */     {
/*  582 */       this.chatColours = !this.chatColours;
/*      */     }
/*      */     
/*  585 */     if (settingsOption == Options.CHAT_LINKS)
/*      */     {
/*  587 */       this.chatLinks = !this.chatLinks;
/*      */     }
/*      */     
/*  590 */     if (settingsOption == Options.CHAT_LINKS_PROMPT)
/*      */     {
/*  592 */       this.chatLinksPrompt = !this.chatLinksPrompt;
/*      */     }
/*      */     
/*  595 */     if (settingsOption == Options.SNOOPER_ENABLED)
/*      */     {
/*  597 */       this.snooperEnabled = !this.snooperEnabled;
/*      */     }
/*      */     
/*  600 */     if (settingsOption == Options.TOUCHSCREEN)
/*      */     {
/*  602 */       this.touchscreen = !this.touchscreen;
/*      */     }
/*      */     
/*  605 */     if (settingsOption == Options.USE_FULLSCREEN) {
/*      */       
/*  607 */       this.fullScreen = !this.fullScreen;
/*      */       
/*  609 */       if (this.mc.isFullScreen() != this.fullScreen)
/*      */       {
/*  611 */         this.mc.toggleFullscreen();
/*      */       }
/*      */     } 
/*      */     
/*  615 */     if (settingsOption == Options.ENABLE_VSYNC) {
/*      */       
/*  617 */       this.enableVsync = !this.enableVsync;
/*  618 */       Display.setVSyncEnabled(this.enableVsync);
/*      */     } 
/*      */     
/*  621 */     if (settingsOption == Options.USE_VBO) {
/*      */       
/*  623 */       this.useVbo = !this.useVbo;
/*  624 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/*  627 */     if (settingsOption == Options.REDUCED_DEBUG_INFO)
/*      */     {
/*  629 */       this.reducedDebugInfo = !this.reducedDebugInfo;
/*      */     }
/*      */     
/*  632 */     if (settingsOption == Options.ENTITY_SHADOWS)
/*      */     {
/*  634 */       this.entityShadows = !this.entityShadows;
/*      */     }
/*      */     
/*  637 */     if (settingsOption == Options.ATTACK_INDICATOR)
/*      */     {
/*  639 */       this.attackIndicator = (this.attackIndicator + value) % 3;
/*      */     }
/*      */     
/*  642 */     if (settingsOption == Options.SHOW_SUBTITLES)
/*      */     {
/*  644 */       this.showSubtitles = !this.showSubtitles;
/*      */     }
/*      */     
/*  647 */     if (settingsOption == Options.REALMS_NOTIFICATIONS)
/*      */     {
/*  649 */       this.realmsNotifications = !this.realmsNotifications;
/*      */     }
/*      */     
/*  652 */     if (settingsOption == Options.AUTO_JUMP)
/*      */     {
/*  654 */       this.autoJump = !this.autoJump;
/*      */     }
/*      */     
/*  657 */     if (settingsOption == Options.NARRATOR) {
/*      */       
/*  659 */       if (NarratorChatListener.field_193643_a.func_193640_a()) {
/*      */         
/*  661 */         this.field_192571_R = (this.field_192571_R + value) % field_193632_b.length;
/*      */       }
/*      */       else {
/*      */         
/*  665 */         this.field_192571_R = 0;
/*      */       } 
/*      */       
/*  668 */       NarratorChatListener.field_193643_a.func_193641_a(this.field_192571_R);
/*      */     } 
/*      */     
/*  671 */     saveOptions();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getOptionFloatValue(Options settingOption) {
/*  676 */     float f = getOptionFloatValueOF(settingOption);
/*      */     
/*  678 */     if (f != Float.MAX_VALUE)
/*      */     {
/*  680 */       return f;
/*      */     }
/*  682 */     if (settingOption == Options.FOV)
/*      */     {
/*  684 */       return this.fovSetting;
/*      */     }
/*  686 */     if (settingOption == Options.GAMMA)
/*      */     {
/*  688 */       return this.gammaSetting;
/*      */     }
/*  690 */     if (settingOption == Options.SATURATION)
/*      */     {
/*  692 */       return this.saturation;
/*      */     }
/*  694 */     if (settingOption == Options.SENSITIVITY)
/*      */     {
/*  696 */       return this.mouseSensitivity;
/*      */     }
/*  698 */     if (settingOption == Options.CHAT_OPACITY)
/*      */     {
/*  700 */       return this.chatOpacity;
/*      */     }
/*  702 */     if (settingOption == Options.CHAT_HEIGHT_FOCUSED)
/*      */     {
/*  704 */       return this.chatHeightFocused;
/*      */     }
/*  706 */     if (settingOption == Options.CHAT_HEIGHT_UNFOCUSED)
/*      */     {
/*  708 */       return this.chatHeightUnfocused;
/*      */     }
/*  710 */     if (settingOption == Options.CHAT_SCALE)
/*      */     {
/*  712 */       return this.chatScale;
/*      */     }
/*  714 */     if (settingOption == Options.CHAT_WIDTH)
/*      */     {
/*  716 */       return this.chatWidth;
/*      */     }
/*  718 */     if (settingOption == Options.FRAMERATE_LIMIT)
/*      */     {
/*  720 */       return this.limitFramerate;
/*      */     }
/*  722 */     if (settingOption == Options.MIPMAP_LEVELS)
/*      */     {
/*  724 */       return this.mipmapLevels;
/*      */     }
/*      */ 
/*      */     
/*  728 */     return (settingOption == Options.RENDER_DISTANCE) ? this.renderDistanceChunks : 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOptionOrdinalValue(Options settingOption) {
/*  734 */     switch (settingOption) {
/*      */       
/*      */       case INVERT_MOUSE:
/*  737 */         return this.invertMouse;
/*      */       
/*      */       case VIEW_BOBBING:
/*  740 */         return this.viewBobbing;
/*      */       
/*      */       case ANAGLYPH:
/*  743 */         return this.anaglyph;
/*      */       
/*      */       case FBO_ENABLE:
/*  746 */         return this.fboEnable;
/*      */       
/*      */       case CHAT_COLOR:
/*  749 */         return this.chatColours;
/*      */       
/*      */       case CHAT_LINKS:
/*  752 */         return this.chatLinks;
/*      */       
/*      */       case CHAT_LINKS_PROMPT:
/*  755 */         return this.chatLinksPrompt;
/*      */       
/*      */       case SNOOPER_ENABLED:
/*  758 */         return this.snooperEnabled;
/*      */       
/*      */       case USE_FULLSCREEN:
/*  761 */         return this.fullScreen;
/*      */       
/*      */       case ENABLE_VSYNC:
/*  764 */         return this.enableVsync;
/*      */       
/*      */       case USE_VBO:
/*  767 */         return this.useVbo;
/*      */       
/*      */       case TOUCHSCREEN:
/*  770 */         return this.touchscreen;
/*      */       
/*      */       case FORCE_UNICODE_FONT:
/*  773 */         return this.forceUnicodeFont;
/*      */       
/*      */       case REDUCED_DEBUG_INFO:
/*  776 */         return this.reducedDebugInfo;
/*      */       
/*      */       case ENTITY_SHADOWS:
/*  779 */         return this.entityShadows;
/*      */       
/*      */       case SHOW_SUBTITLES:
/*  782 */         return this.showSubtitles;
/*      */       
/*      */       case REALMS_NOTIFICATIONS:
/*  785 */         return this.realmsNotifications;
/*      */       
/*      */       case ENABLE_WEAK_ATTACKS:
/*  788 */         return this.enableWeakAttacks;
/*      */       
/*      */       case AUTO_JUMP:
/*  791 */         return this.autoJump;
/*      */     } 
/*      */     
/*  794 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getTranslation(String[] strArray, int index) {
/*  804 */     if (index < 0 || index >= strArray.length)
/*      */     {
/*  806 */       index = 0;
/*      */     }
/*      */     
/*  809 */     return I18n.format(strArray[index], new Object[0]);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getKeyBinding(Options settingOption) {
/*  817 */     String s = getKeyBindingOF(settingOption);
/*      */     
/*  819 */     if (s != null)
/*      */     {
/*  821 */       return s;
/*      */     }
/*      */ 
/*      */     
/*  825 */     String s1 = String.valueOf(I18n.format(settingOption.getEnumString(), new Object[0])) + ": ";
/*      */     
/*  827 */     if (settingOption.getEnumFloat()) {
/*      */       
/*  829 */       float f1 = getOptionFloatValue(settingOption);
/*  830 */       float f = settingOption.normalizeValue(f1);
/*      */       
/*  832 */       if (settingOption == Options.SENSITIVITY) {
/*      */         
/*  834 */         if (f == 0.0F)
/*      */         {
/*  836 */           return String.valueOf(s1) + I18n.format("options.sensitivity.min", new Object[0]);
/*      */         }
/*      */ 
/*      */         
/*  840 */         return (f == 1.0F) ? (String.valueOf(s1) + I18n.format("options.sensitivity.max", new Object[0])) : (String.valueOf(s1) + (int)(f * 200.0F) + "%");
/*      */       } 
/*      */       
/*  843 */       if (settingOption == Options.FOV) {
/*      */         
/*  845 */         if (f1 == 70.0F)
/*      */         {
/*  847 */           return String.valueOf(s1) + I18n.format("options.fov.min", new Object[0]);
/*      */         }
/*      */ 
/*      */         
/*  851 */         return (f1 == 110.0F) ? (String.valueOf(s1) + I18n.format("options.fov.max", new Object[0])) : (String.valueOf(s1) + (int)f1);
/*      */       } 
/*      */       
/*  854 */       if (settingOption == Options.FRAMERATE_LIMIT)
/*      */       {
/*  856 */         return (f1 == settingOption.valueMax) ? (String.valueOf(s1) + I18n.format("options.framerateLimit.max", new Object[0])) : (String.valueOf(s1) + I18n.format("options.framerate", new Object[] { Integer.valueOf((int)f1) }));
/*      */       }
/*  858 */       if (settingOption == Options.RENDER_CLOUDS)
/*      */       {
/*  860 */         return (f1 == settingOption.valueMin) ? (String.valueOf(s1) + I18n.format("options.cloudHeight.min", new Object[0])) : (String.valueOf(s1) + ((int)f1 + 128));
/*      */       }
/*  862 */       if (settingOption == Options.GAMMA) {
/*      */         
/*  864 */         if (f == 0.0F)
/*      */         {
/*  866 */           return String.valueOf(s1) + I18n.format("options.gamma.min", new Object[0]);
/*      */         }
/*      */ 
/*      */         
/*  870 */         return (f == 1.0F) ? (String.valueOf(s1) + I18n.format("options.gamma.max", new Object[0])) : (String.valueOf(s1) + "+" + (int)(f * 100.0F) + "%");
/*      */       } 
/*      */       
/*  873 */       if (settingOption == Options.SATURATION)
/*      */       {
/*  875 */         return String.valueOf(s1) + (int)(f * 400.0F) + "%";
/*      */       }
/*  877 */       if (settingOption == Options.CHAT_OPACITY)
/*      */       {
/*  879 */         return String.valueOf(s1) + (int)(f * 90.0F + 10.0F) + "%";
/*      */       }
/*  881 */       if (settingOption == Options.CHAT_HEIGHT_UNFOCUSED)
/*      */       {
/*  883 */         return String.valueOf(s1) + GuiNewChat.calculateChatboxHeight(f) + "px";
/*      */       }
/*  885 */       if (settingOption == Options.CHAT_HEIGHT_FOCUSED)
/*      */       {
/*  887 */         return String.valueOf(s1) + GuiNewChat.calculateChatboxHeight(f) + "px";
/*      */       }
/*  889 */       if (settingOption == Options.CHAT_WIDTH)
/*      */       {
/*  891 */         return String.valueOf(s1) + GuiNewChat.calculateChatboxWidth(f) + "px";
/*      */       }
/*  893 */       if (settingOption == Options.RENDER_DISTANCE)
/*      */       {
/*  895 */         return String.valueOf(s1) + I18n.format("options.chunks", new Object[] { Integer.valueOf((int)f1) });
/*      */       }
/*  897 */       if (settingOption == Options.MIPMAP_LEVELS)
/*      */       {
/*  899 */         return (f1 == 0.0F) ? (String.valueOf(s1) + I18n.format("options.off", new Object[0])) : (String.valueOf(s1) + (int)f1);
/*      */       }
/*      */ 
/*      */       
/*  903 */       return (f == 0.0F) ? (String.valueOf(s1) + I18n.format("options.off", new Object[0])) : (String.valueOf(s1) + (int)(f * 100.0F) + "%");
/*      */     } 
/*      */     
/*  906 */     if (settingOption.getEnumBoolean()) {
/*      */       
/*  908 */       boolean flag = getOptionOrdinalValue(settingOption);
/*  909 */       return flag ? (String.valueOf(s1) + I18n.format("options.on", new Object[0])) : (String.valueOf(s1) + I18n.format("options.off", new Object[0]));
/*      */     } 
/*  911 */     if (settingOption == Options.MAIN_HAND)
/*      */     {
/*  913 */       return String.valueOf(s1) + this.mainHand;
/*      */     }
/*  915 */     if (settingOption == Options.GUI_SCALE)
/*      */     {
/*  917 */       return (this.guiScale >= GUISCALES.length) ? (String.valueOf(s1) + this.guiScale + "x") : (String.valueOf(s1) + getTranslation(GUISCALES, this.guiScale));
/*      */     }
/*  919 */     if (settingOption == Options.CHAT_VISIBILITY)
/*      */     {
/*  921 */       return String.valueOf(s1) + I18n.format(this.chatVisibility.getResourceKey(), new Object[0]);
/*      */     }
/*  923 */     if (settingOption == Options.PARTICLES)
/*      */     {
/*  925 */       return String.valueOf(s1) + getTranslation(PARTICLES, this.particleSetting);
/*      */     }
/*  927 */     if (settingOption == Options.AMBIENT_OCCLUSION)
/*      */     {
/*  929 */       return String.valueOf(s1) + getTranslation(AMBIENT_OCCLUSIONS, this.ambientOcclusion);
/*      */     }
/*  931 */     if (settingOption == Options.RENDER_CLOUDS)
/*      */     {
/*  933 */       return String.valueOf(s1) + getTranslation(CLOUDS_TYPES, this.clouds);
/*      */     }
/*  935 */     if (settingOption == Options.GRAPHICS) {
/*      */       
/*  937 */       if (this.fancyGraphics)
/*      */       {
/*  939 */         return String.valueOf(s1) + I18n.format("options.graphics.fancy", new Object[0]);
/*      */       }
/*      */ 
/*      */       
/*  943 */       String s2 = "options.graphics.fast";
/*  944 */       return String.valueOf(s1) + I18n.format("options.graphics.fast", new Object[0]);
/*      */     } 
/*      */     
/*  947 */     if (settingOption == Options.ATTACK_INDICATOR)
/*      */     {
/*  949 */       return String.valueOf(s1) + getTranslation(ATTACK_INDICATORS, this.attackIndicator);
/*      */     }
/*  951 */     if (settingOption == Options.NARRATOR)
/*      */     {
/*  953 */       return NarratorChatListener.field_193643_a.func_193640_a() ? (String.valueOf(s1) + getTranslation(field_193632_b, this.field_192571_R)) : (String.valueOf(s1) + I18n.format("options.narrator.notavailable", new Object[0]));
/*      */     }
/*      */ 
/*      */     
/*  957 */     return s1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadOptions() {
/*      */     try {
/*  969 */       if (!this.optionsFile.exists()) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  974 */       this.soundLevels.clear();
/*  975 */       List<String> list = IOUtils.readLines(new FileInputStream(this.optionsFile), StandardCharsets.UTF_8);
/*  976 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/*      */       
/*  978 */       for (String s : list) {
/*      */ 
/*      */         
/*      */         try {
/*  982 */           Iterator<String> iterator = COLON_SPLITTER.omitEmptyStrings().limit(2).split(s).iterator();
/*  983 */           nbttagcompound.setString(iterator.next(), iterator.next());
/*      */         }
/*  985 */         catch (Exception var12) {
/*      */           
/*  987 */           LOGGER.warn("Skipping bad option: {}", s);
/*      */         } 
/*      */       } 
/*      */       
/*  991 */       nbttagcompound = dataFix(nbttagcompound);
/*      */       
/*  993 */       for (String s1 : nbttagcompound.getKeySet()) {
/*      */         
/*  995 */         String s2 = nbttagcompound.getString(s1);
/*      */ 
/*      */         
/*      */         try {
/*  999 */           if ("mouseSensitivity".equals(s1))
/*      */           {
/* 1001 */             this.mouseSensitivity = parseFloat(s2);
/*      */           }
/*      */           
/* 1004 */           if ("fov".equals(s1))
/*      */           {
/* 1006 */             this.fovSetting = parseFloat(s2) * 40.0F + 70.0F;
/*      */           }
/*      */           
/* 1009 */           if ("gamma".equals(s1))
/*      */           {
/* 1011 */             this.gammaSetting = parseFloat(s2);
/*      */           }
/*      */           
/* 1014 */           if ("saturation".equals(s1))
/*      */           {
/* 1016 */             this.saturation = parseFloat(s2);
/*      */           }
/*      */           
/* 1019 */           if ("invertYMouse".equals(s1))
/*      */           {
/* 1021 */             this.invertMouse = "true".equals(s2);
/*      */           }
/*      */           
/* 1024 */           if ("renderDistance".equals(s1))
/*      */           {
/* 1026 */             this.renderDistanceChunks = Integer.parseInt(s2);
/*      */           }
/*      */           
/* 1029 */           if ("guiScale".equals(s1))
/*      */           {
/* 1031 */             this.guiScale = Integer.parseInt(s2);
/*      */           }
/*      */           
/* 1034 */           if ("particles".equals(s1))
/*      */           {
/* 1036 */             this.particleSetting = Integer.parseInt(s2);
/*      */           }
/*      */           
/* 1039 */           if ("bobView".equals(s1))
/*      */           {
/* 1041 */             this.viewBobbing = "true".equals(s2);
/*      */           }
/*      */           
/* 1044 */           if ("anaglyph3d".equals(s1))
/*      */           {
/* 1046 */             this.anaglyph = "true".equals(s2);
/*      */           }
/*      */           
/* 1049 */           if ("maxFps".equals(s1)) {
/*      */             
/* 1051 */             this.limitFramerate = Integer.parseInt(s2);
/*      */             
/* 1053 */             if (this.enableVsync)
/*      */             {
/* 1055 */               this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
/*      */             }
/*      */             
/* 1058 */             if (this.limitFramerate <= 0)
/*      */             {
/* 1060 */               this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
/*      */             }
/*      */           } 
/*      */           
/* 1064 */           if ("fboEnable".equals(s1))
/*      */           {
/* 1066 */             this.fboEnable = "true".equals(s2);
/*      */           }
/*      */           
/* 1069 */           if ("difficulty".equals(s1))
/*      */           {
/* 1071 */             this.difficulty = EnumDifficulty.getDifficultyEnum(Integer.parseInt(s2));
/*      */           }
/*      */           
/* 1074 */           if ("fancyGraphics".equals(s1)) {
/*      */             
/* 1076 */             this.fancyGraphics = "true".equals(s2);
/* 1077 */             updateRenderClouds();
/*      */           } 
/*      */           
/* 1080 */           if ("tutorialStep".equals(s1))
/*      */           {
/* 1082 */             this.field_193631_S = TutorialSteps.func_193307_a(s2);
/*      */           }
/*      */           
/* 1085 */           if ("ao".equals(s1))
/*      */           {
/* 1087 */             if ("true".equals(s2)) {
/*      */               
/* 1089 */               this.ambientOcclusion = 2;
/*      */             }
/* 1091 */             else if ("false".equals(s2)) {
/*      */               
/* 1093 */               this.ambientOcclusion = 0;
/*      */             }
/*      */             else {
/*      */               
/* 1097 */               this.ambientOcclusion = Integer.parseInt(s2);
/*      */             } 
/*      */           }
/*      */           
/* 1101 */           if ("renderClouds".equals(s1))
/*      */           {
/* 1103 */             if ("true".equals(s2)) {
/*      */               
/* 1105 */               this.clouds = 2;
/*      */             }
/* 1107 */             else if ("false".equals(s2)) {
/*      */               
/* 1109 */               this.clouds = 0;
/*      */             }
/* 1111 */             else if ("fast".equals(s2)) {
/*      */               
/* 1113 */               this.clouds = 1;
/*      */             } 
/*      */           }
/*      */           
/* 1117 */           if ("attackIndicator".equals(s1))
/*      */           {
/* 1119 */             if ("0".equals(s2)) {
/*      */               
/* 1121 */               this.attackIndicator = 0;
/*      */             }
/* 1123 */             else if ("1".equals(s2)) {
/*      */               
/* 1125 */               this.attackIndicator = 1;
/*      */             }
/* 1127 */             else if ("2".equals(s2)) {
/*      */               
/* 1129 */               this.attackIndicator = 2;
/*      */             } 
/*      */           }
/*      */           
/* 1133 */           if ("resourcePacks".equals(s1)) {
/*      */             
/* 1135 */             this.resourcePacks = (List<String>)JsonUtils.func_193840_a(GSON, s2, TYPE_LIST_STRING);
/*      */             
/* 1137 */             if (this.resourcePacks == null)
/*      */             {
/* 1139 */               this.resourcePacks = Lists.newArrayList();
/*      */             }
/*      */           } 
/*      */           
/* 1143 */           if ("incompatibleResourcePacks".equals(s1)) {
/*      */             
/* 1145 */             this.incompatibleResourcePacks = (List<String>)JsonUtils.func_193840_a(GSON, s2, TYPE_LIST_STRING);
/*      */             
/* 1147 */             if (this.incompatibleResourcePacks == null)
/*      */             {
/* 1149 */               this.incompatibleResourcePacks = Lists.newArrayList();
/*      */             }
/*      */           } 
/*      */           
/* 1153 */           if ("lastServer".equals(s1))
/*      */           {
/* 1155 */             this.lastServer = s2;
/*      */           }
/*      */           
/* 1158 */           if ("lang".equals(s1))
/*      */           {
/* 1160 */             this.language = s2;
/*      */           }
/*      */           
/* 1163 */           if ("chatVisibility".equals(s1))
/*      */           {
/* 1165 */             this.chatVisibility = EntityPlayer.EnumChatVisibility.getEnumChatVisibility(Integer.parseInt(s2));
/*      */           }
/*      */           
/* 1168 */           if ("chatColors".equals(s1))
/*      */           {
/* 1170 */             this.chatColours = "true".equals(s2);
/*      */           }
/*      */           
/* 1173 */           if ("chatLinks".equals(s1))
/*      */           {
/* 1175 */             this.chatLinks = "true".equals(s2);
/*      */           }
/*      */           
/* 1178 */           if ("chatLinksPrompt".equals(s1))
/*      */           {
/* 1180 */             this.chatLinksPrompt = "true".equals(s2);
/*      */           }
/*      */           
/* 1183 */           if ("chatOpacity".equals(s1))
/*      */           {
/* 1185 */             this.chatOpacity = parseFloat(s2);
/*      */           }
/*      */           
/* 1188 */           if ("snooperEnabled".equals(s1))
/*      */           {
/* 1190 */             this.snooperEnabled = "true".equals(s2);
/*      */           }
/*      */           
/* 1193 */           if ("fullscreen".equals(s1))
/*      */           {
/* 1195 */             this.fullScreen = "true".equals(s2);
/*      */           }
/*      */           
/* 1198 */           if ("enableVsync".equals(s1)) {
/*      */             
/* 1200 */             this.enableVsync = "true".equals(s2);
/*      */             
/* 1202 */             if (this.enableVsync)
/*      */             {
/* 1204 */               this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
/*      */             }
/*      */             
/* 1207 */             updateVSync();
/*      */           } 
/*      */           
/* 1210 */           if ("useVbo".equals(s1))
/*      */           {
/* 1212 */             this.useVbo = "true".equals(s2);
/*      */           }
/*      */           
/* 1215 */           if ("hideServerAddress".equals(s1))
/*      */           {
/* 1217 */             this.hideServerAddress = "true".equals(s2);
/*      */           }
/*      */           
/* 1220 */           if ("advancedItemTooltips".equals(s1))
/*      */           {
/* 1222 */             this.advancedItemTooltips = "true".equals(s2);
/*      */           }
/*      */           
/* 1225 */           if ("pauseOnLostFocus".equals(s1))
/*      */           {
/* 1227 */             this.pauseOnLostFocus = "true".equals(s2);
/*      */           }
/*      */           
/* 1230 */           if ("touchscreen".equals(s1))
/*      */           {
/* 1232 */             this.touchscreen = "true".equals(s2);
/*      */           }
/*      */           
/* 1235 */           if ("overrideHeight".equals(s1))
/*      */           {
/* 1237 */             this.overrideHeight = Integer.parseInt(s2);
/*      */           }
/*      */           
/* 1240 */           if ("overrideWidth".equals(s1))
/*      */           {
/* 1242 */             this.overrideWidth = Integer.parseInt(s2);
/*      */           }
/*      */           
/* 1245 */           if ("heldItemTooltips".equals(s1))
/*      */           {
/* 1247 */             this.heldItemTooltips = "true".equals(s2);
/*      */           }
/*      */           
/* 1250 */           if ("chatHeightFocused".equals(s1))
/*      */           {
/* 1252 */             this.chatHeightFocused = parseFloat(s2);
/*      */           }
/*      */           
/* 1255 */           if ("chatHeightUnfocused".equals(s1))
/*      */           {
/* 1257 */             this.chatHeightUnfocused = parseFloat(s2);
/*      */           }
/*      */           
/* 1260 */           if ("chatScale".equals(s1))
/*      */           {
/* 1262 */             this.chatScale = parseFloat(s2);
/*      */           }
/*      */           
/* 1265 */           if ("chatWidth".equals(s1))
/*      */           {
/* 1267 */             this.chatWidth = parseFloat(s2);
/*      */           }
/*      */           
/* 1270 */           if ("mipmapLevels".equals(s1))
/*      */           {
/* 1272 */             this.mipmapLevels = Integer.parseInt(s2);
/*      */           }
/*      */           
/* 1275 */           if ("forceUnicodeFont".equals(s1))
/*      */           {
/* 1277 */             this.forceUnicodeFont = "true".equals(s2);
/*      */           }
/*      */           
/* 1280 */           if ("reducedDebugInfo".equals(s1))
/*      */           {
/* 1282 */             this.reducedDebugInfo = "true".equals(s2);
/*      */           }
/*      */           
/* 1285 */           if ("useNativeTransport".equals(s1))
/*      */           {
/* 1287 */             this.useNativeTransport = "true".equals(s2);
/*      */           }
/*      */           
/* 1290 */           if ("entityShadows".equals(s1))
/*      */           {
/* 1292 */             this.entityShadows = "true".equals(s2);
/*      */           }
/*      */           
/* 1295 */           if ("mainHand".equals(s1))
/*      */           {
/* 1297 */             this.mainHand = "left".equals(s2) ? EnumHandSide.LEFT : EnumHandSide.RIGHT;
/*      */           }
/*      */           
/* 1300 */           if ("showSubtitles".equals(s1))
/*      */           {
/* 1302 */             this.showSubtitles = "true".equals(s2);
/*      */           }
/*      */           
/* 1305 */           if ("realmsNotifications".equals(s1))
/*      */           {
/* 1307 */             this.realmsNotifications = "true".equals(s2);
/*      */           }
/*      */           
/* 1310 */           if ("enableWeakAttacks".equals(s1))
/*      */           {
/* 1312 */             this.enableWeakAttacks = "true".equals(s2);
/*      */           }
/*      */           
/* 1315 */           if ("autoJump".equals(s1))
/*      */           {
/* 1317 */             this.autoJump = "true".equals(s2);
/*      */           }
/*      */           
/* 1320 */           if ("narrator".equals(s1))
/*      */           {
/* 1322 */             this.field_192571_R = Integer.parseInt(s2); }  byte b;
/*      */           int i;
/*      */           KeyBinding[] arrayOfKeyBinding;
/* 1325 */           for (i = (arrayOfKeyBinding = this.keyBindings).length, b = 0; b < i; ) { KeyBinding keybinding = arrayOfKeyBinding[b];
/*      */             
/* 1327 */             if (s1.equals("key_" + keybinding.getKeyDescription()))
/*      */             {
/* 1329 */               if (Reflector.KeyModifier_valueFromString.exists()) {
/*      */                 
/* 1331 */                 if (s2.indexOf(':') != -1)
/*      */                 {
/* 1333 */                   String[] astring = s2.split(":");
/* 1334 */                   Object object = Reflector.call(Reflector.KeyModifier_valueFromString, new Object[] { astring[1] });
/* 1335 */                   Reflector.call(keybinding, Reflector.ForgeKeyBinding_setKeyModifierAndCode, new Object[] { object, Integer.valueOf(Integer.parseInt(astring[0])) });
/*      */                 }
/*      */                 else
/*      */                 {
/* 1339 */                   Object object1 = Reflector.getFieldValue(Reflector.KeyModifier_NONE);
/* 1340 */                   Reflector.call(keybinding, Reflector.ForgeKeyBinding_setKeyModifierAndCode, new Object[] { object1, Integer.valueOf(Integer.parseInt(s2)) });
/*      */                 }
/*      */               
/*      */               } else {
/*      */                 
/* 1345 */                 keybinding.setKeyCode(Integer.parseInt(s2));
/*      */               }  } 
/*      */             b++; }
/*      */           
/*      */           SoundCategory[] arrayOfSoundCategory;
/* 1350 */           for (i = (arrayOfSoundCategory = SoundCategory.values()).length, b = 0; b < i; ) { SoundCategory soundcategory = arrayOfSoundCategory[b];
/*      */             
/* 1352 */             if (s1.equals("soundCategory_" + soundcategory.getName()))
/*      */             {
/* 1354 */               this.soundLevels.put(soundcategory, Float.valueOf(parseFloat(s2))); } 
/*      */             b++; }
/*      */           
/*      */           EnumPlayerModelParts[] arrayOfEnumPlayerModelParts;
/* 1358 */           for (i = (arrayOfEnumPlayerModelParts = EnumPlayerModelParts.values()).length, b = 0; b < i; ) { EnumPlayerModelParts enumplayermodelparts = arrayOfEnumPlayerModelParts[b];
/*      */             
/* 1360 */             if (s1.equals("modelPart_" + enumplayermodelparts.getPartName()))
/*      */             {
/* 1362 */               setModelPartEnabled(enumplayermodelparts, "true".equals(s2));
/*      */             }
/*      */             b++; }
/*      */         
/* 1366 */         } catch (Exception exception1) {
/*      */           
/* 1368 */           LOGGER.warn("Skipping bad option: {}:{}", s1, s2);
/* 1369 */           exception1.printStackTrace();
/*      */         } 
/*      */       } 
/*      */       
/* 1373 */       KeyBinding.resetKeyBindingArrayAndHash();
/*      */     }
/* 1375 */     catch (Exception exception1) {
/*      */       
/* 1377 */       LOGGER.error("Failed to load options", exception1);
/*      */     } 
/*      */     
/* 1380 */     loadOfOptions();
/*      */   }
/*      */ 
/*      */   
/*      */   private NBTTagCompound dataFix(NBTTagCompound p_189988_1_) {
/* 1385 */     int i = 0;
/*      */ 
/*      */     
/*      */     try {
/* 1389 */       i = Integer.parseInt(p_189988_1_.getString("version"));
/*      */     }
/* 1391 */     catch (RuntimeException runtimeException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1396 */     return this.mc.getDataFixer().process((IFixType)FixTypes.OPTIONS, p_189988_1_, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float parseFloat(String str) {
/* 1404 */     if ("true".equals(str))
/*      */     {
/* 1406 */       return 1.0F;
/*      */     }
/*      */ 
/*      */     
/* 1410 */     return "false".equals(str) ? 0.0F : Float.parseFloat(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveOptions() {
/* 1419 */     if (Reflector.FMLClientHandler.exists()) {
/*      */       
/* 1421 */       Object object = Reflector.call(Reflector.FMLClientHandler_instance, new Object[0]);
/*      */       
/* 1423 */       if (object != null && Reflector.callBoolean(object, Reflector.FMLClientHandler_isLoading, new Object[0])) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1429 */     PrintWriter printwriter = null;
/*      */ 
/*      */     
/*      */     try {
/* 1433 */       printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.optionsFile), StandardCharsets.UTF_8));
/* 1434 */       printwriter.println("version:1343");
/* 1435 */       printwriter.println("invertYMouse:" + this.invertMouse);
/* 1436 */       printwriter.println("mouseSensitivity:" + this.mouseSensitivity);
/* 1437 */       printwriter.println("fov:" + ((this.fovSetting - 70.0F) / 40.0F));
/* 1438 */       printwriter.println("gamma:" + this.gammaSetting);
/* 1439 */       printwriter.println("saturation:" + this.saturation);
/* 1440 */       printwriter.println("renderDistance:" + this.renderDistanceChunks);
/* 1441 */       printwriter.println("guiScale:" + this.guiScale);
/* 1442 */       printwriter.println("particles:" + this.particleSetting);
/* 1443 */       printwriter.println("bobView:" + this.viewBobbing);
/* 1444 */       printwriter.println("anaglyph3d:" + this.anaglyph);
/* 1445 */       printwriter.println("maxFps:" + this.limitFramerate);
/* 1446 */       printwriter.println("fboEnable:" + this.fboEnable);
/* 1447 */       printwriter.println("difficulty:" + this.difficulty.getDifficultyId());
/* 1448 */       printwriter.println("fancyGraphics:" + this.fancyGraphics);
/* 1449 */       printwriter.println("ao:" + this.ambientOcclusion);
/*      */       
/* 1451 */       switch (this.clouds) {
/*      */         
/*      */         case 0:
/* 1454 */           printwriter.println("renderClouds:false");
/*      */           break;
/*      */         
/*      */         case 1:
/* 1458 */           printwriter.println("renderClouds:fast");
/*      */           break;
/*      */         
/*      */         case 2:
/* 1462 */           printwriter.println("renderClouds:true");
/*      */           break;
/*      */       } 
/* 1465 */       printwriter.println("resourcePacks:" + GSON.toJson(this.resourcePacks));
/* 1466 */       printwriter.println("incompatibleResourcePacks:" + GSON.toJson(this.incompatibleResourcePacks));
/* 1467 */       printwriter.println("lastServer:" + this.lastServer);
/* 1468 */       printwriter.println("lang:" + this.language);
/* 1469 */       printwriter.println("chatVisibility:" + this.chatVisibility.getChatVisibility());
/* 1470 */       printwriter.println("chatColors:" + this.chatColours);
/* 1471 */       printwriter.println("chatLinks:" + this.chatLinks);
/* 1472 */       printwriter.println("chatLinksPrompt:" + this.chatLinksPrompt);
/* 1473 */       printwriter.println("chatOpacity:" + this.chatOpacity);
/* 1474 */       printwriter.println("snooperEnabled:" + this.snooperEnabled);
/* 1475 */       printwriter.println("fullscreen:" + this.fullScreen);
/* 1476 */       printwriter.println("enableVsync:" + this.enableVsync);
/* 1477 */       printwriter.println("useVbo:" + this.useVbo);
/* 1478 */       printwriter.println("hideServerAddress:" + this.hideServerAddress);
/* 1479 */       printwriter.println("advancedItemTooltips:" + this.advancedItemTooltips);
/* 1480 */       printwriter.println("pauseOnLostFocus:" + this.pauseOnLostFocus);
/* 1481 */       printwriter.println("touchscreen:" + this.touchscreen);
/* 1482 */       printwriter.println("overrideWidth:" + this.overrideWidth);
/* 1483 */       printwriter.println("overrideHeight:" + this.overrideHeight);
/* 1484 */       printwriter.println("heldItemTooltips:" + this.heldItemTooltips);
/* 1485 */       printwriter.println("chatHeightFocused:" + this.chatHeightFocused);
/* 1486 */       printwriter.println("chatHeightUnfocused:" + this.chatHeightUnfocused);
/* 1487 */       printwriter.println("chatScale:" + this.chatScale);
/* 1488 */       printwriter.println("chatWidth:" + this.chatWidth);
/* 1489 */       printwriter.println("mipmapLevels:" + this.mipmapLevels);
/* 1490 */       printwriter.println("forceUnicodeFont:" + this.forceUnicodeFont);
/* 1491 */       printwriter.println("reducedDebugInfo:" + this.reducedDebugInfo);
/* 1492 */       printwriter.println("useNativeTransport:" + this.useNativeTransport);
/* 1493 */       printwriter.println("entityShadows:" + this.entityShadows);
/* 1494 */       printwriter.println("mainHand:" + ((this.mainHand == EnumHandSide.LEFT) ? "left" : "right"));
/* 1495 */       printwriter.println("attackIndicator:" + this.attackIndicator);
/* 1496 */       printwriter.println("showSubtitles:" + this.showSubtitles);
/* 1497 */       printwriter.println("realmsNotifications:" + this.realmsNotifications);
/* 1498 */       printwriter.println("enableWeakAttacks:" + this.enableWeakAttacks);
/* 1499 */       printwriter.println("autoJump:" + this.autoJump);
/* 1500 */       printwriter.println("narrator:" + this.field_192571_R);
/* 1501 */       printwriter.println("tutorialStep:" + this.field_193631_S.func_193308_a()); byte b; int i;
/*      */       KeyBinding[] arrayOfKeyBinding;
/* 1503 */       for (i = (arrayOfKeyBinding = this.keyBindings).length, b = 0; b < i; ) { KeyBinding keybinding = arrayOfKeyBinding[b];
/*      */         
/* 1505 */         if (Reflector.ForgeKeyBinding_getKeyModifier.exists()) {
/*      */           
/* 1507 */           String s = "key_" + keybinding.getKeyDescription() + ":" + keybinding.getKeyCode();
/* 1508 */           Object object1 = Reflector.call(keybinding, Reflector.ForgeKeyBinding_getKeyModifier, new Object[0]);
/* 1509 */           Object object2 = Reflector.getFieldValue(Reflector.KeyModifier_NONE);
/* 1510 */           printwriter.println((object1 != object2) ? (String.valueOf(s) + ":" + object1) : s);
/*      */         }
/*      */         else {
/*      */           
/* 1514 */           printwriter.println("key_" + keybinding.getKeyDescription() + ":" + keybinding.getKeyCode());
/*      */         }  b++; }
/*      */       
/*      */       SoundCategory[] arrayOfSoundCategory;
/* 1518 */       for (i = (arrayOfSoundCategory = SoundCategory.values()).length, b = 0; b < i; ) { SoundCategory soundcategory = arrayOfSoundCategory[b];
/*      */         
/* 1520 */         printwriter.println("soundCategory_" + soundcategory.getName() + ":" + getSoundLevel(soundcategory)); b++; }
/*      */       
/*      */       EnumPlayerModelParts[] arrayOfEnumPlayerModelParts;
/* 1523 */       for (i = (arrayOfEnumPlayerModelParts = EnumPlayerModelParts.values()).length, b = 0; b < i; ) { EnumPlayerModelParts enumplayermodelparts = arrayOfEnumPlayerModelParts[b];
/*      */         
/* 1525 */         printwriter.println("modelPart_" + enumplayermodelparts.getPartName() + ":" + this.setModelParts.contains(enumplayermodelparts));
/*      */         b++; }
/*      */     
/* 1528 */     } catch (Exception exception) {
/*      */       
/* 1530 */       LOGGER.error("Failed to save options", exception);
/*      */     }
/*      */     finally {
/*      */       
/* 1534 */       IOUtils.closeQuietly(printwriter);
/*      */     } 
/*      */     
/* 1537 */     saveOfOptions();
/* 1538 */     sendSettingsToServer();
/*      */   }
/*      */ 
/*      */   
/*      */   public float getSoundLevel(SoundCategory category) {
/* 1543 */     return this.soundLevels.containsKey(category) ? ((Float)this.soundLevels.get(category)).floatValue() : 1.0F;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSoundLevel(SoundCategory category, float volume) {
/* 1548 */     this.mc.getSoundHandler().setSoundLevel(category, volume);
/* 1549 */     this.soundLevels.put(category, Float.valueOf(volume));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendSettingsToServer() {
/* 1557 */     if (this.mc.player != null) {
/*      */       
/* 1559 */       int i = 0;
/*      */       
/* 1561 */       for (EnumPlayerModelParts enumplayermodelparts : this.setModelParts)
/*      */       {
/* 1563 */         i |= enumplayermodelparts.getPartMask();
/*      */       }
/*      */       
/* 1566 */       this.mc.player.connection.sendPacket((Packet)new CPacketClientSettings(this.language, this.renderDistanceChunks, this.chatVisibility, this.chatColours, i, this.mainHand));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Set<EnumPlayerModelParts> getModelParts() {
/* 1572 */     return (Set<EnumPlayerModelParts>)ImmutableSet.copyOf(this.setModelParts);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setModelPartEnabled(EnumPlayerModelParts modelPart, boolean enable) {
/* 1577 */     if (enable) {
/*      */       
/* 1579 */       this.setModelParts.add(modelPart);
/*      */     }
/*      */     else {
/*      */       
/* 1583 */       this.setModelParts.remove(modelPart);
/*      */     } 
/*      */     
/* 1586 */     sendSettingsToServer();
/*      */   }
/*      */ 
/*      */   
/*      */   public void switchModelPartEnabled(EnumPlayerModelParts modelPart) {
/* 1591 */     if (getModelParts().contains(modelPart)) {
/*      */       
/* 1593 */       this.setModelParts.remove(modelPart);
/*      */     }
/*      */     else {
/*      */       
/* 1597 */       this.setModelParts.add(modelPart);
/*      */     } 
/*      */     
/* 1600 */     sendSettingsToServer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int shouldRenderClouds() {
/* 1608 */     return (this.renderDistanceChunks >= 4) ? this.clouds : 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUsingNativeTransport() {
/* 1616 */     return this.useNativeTransport;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setOptionFloatValueOF(Options p_setOptionFloatValueOF_1_, float p_setOptionFloatValueOF_2_) {
/* 1621 */     if (p_setOptionFloatValueOF_1_ == Options.CLOUD_HEIGHT) {
/*      */       
/* 1623 */       this.ofCloudsHeight = p_setOptionFloatValueOF_2_;
/* 1624 */       this.mc.renderGlobal.resetClouds();
/*      */     } 
/*      */     
/* 1627 */     if (p_setOptionFloatValueOF_1_ == Options.AO_LEVEL) {
/*      */       
/* 1629 */       this.ofAoLevel = p_setOptionFloatValueOF_2_;
/* 1630 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1633 */     if (p_setOptionFloatValueOF_1_ == Options.AA_LEVEL) {
/*      */       
/* 1635 */       int i = (int)p_setOptionFloatValueOF_2_;
/*      */       
/* 1637 */       if (i > 0 && Config.isShaders()) {
/*      */         
/* 1639 */         Config.showGuiMessage(Lang.get("of.message.aa.shaders1"), Lang.get("of.message.aa.shaders2"));
/*      */         
/*      */         return;
/*      */       } 
/* 1643 */       int[] aint = { 0, 2, 4, 6, 8, 12, 16 };
/* 1644 */       this.ofAaLevel = 0;
/*      */       
/* 1646 */       for (int j = 0; j < aint.length; j++) {
/*      */         
/* 1648 */         if (i >= aint[j])
/*      */         {
/* 1650 */           this.ofAaLevel = aint[j];
/*      */         }
/*      */       } 
/*      */       
/* 1654 */       this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
/*      */     } 
/*      */     
/* 1657 */     if (p_setOptionFloatValueOF_1_ == Options.AF_LEVEL) {
/*      */       
/* 1659 */       int k = (int)p_setOptionFloatValueOF_2_;
/*      */       
/* 1661 */       if (k > 1 && Config.isShaders()) {
/*      */         
/* 1663 */         Config.showGuiMessage(Lang.get("of.message.af.shaders1"), Lang.get("of.message.af.shaders2"));
/*      */         
/*      */         return;
/*      */       } 
/* 1667 */       for (this.ofAfLevel = 1; this.ofAfLevel * 2 <= k; this.ofAfLevel *= 2);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1672 */       this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
/* 1673 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 1676 */     if (p_setOptionFloatValueOF_1_ == Options.MIPMAP_TYPE) {
/*      */       
/* 1678 */       int l = (int)p_setOptionFloatValueOF_2_;
/* 1679 */       this.ofMipmapType = Config.limit(l, 0, 3);
/* 1680 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 1683 */     if (p_setOptionFloatValueOF_1_ == Options.FULLSCREEN_MODE) {
/*      */       
/* 1685 */       int i1 = (int)p_setOptionFloatValueOF_2_ - 1;
/* 1686 */       String[] astring = Config.getDisplayModeNames();
/*      */       
/* 1688 */       if (i1 < 0 || i1 >= astring.length) {
/*      */         
/* 1690 */         this.ofFullscreenMode = "Default";
/*      */         
/*      */         return;
/*      */       } 
/* 1694 */       this.ofFullscreenMode = astring[i1];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private float getOptionFloatValueOF(Options p_getOptionFloatValueOF_1_) {
/* 1700 */     if (p_getOptionFloatValueOF_1_ == Options.CLOUD_HEIGHT)
/*      */     {
/* 1702 */       return this.ofCloudsHeight;
/*      */     }
/* 1704 */     if (p_getOptionFloatValueOF_1_ == Options.AO_LEVEL)
/*      */     {
/* 1706 */       return this.ofAoLevel;
/*      */     }
/* 1708 */     if (p_getOptionFloatValueOF_1_ == Options.AA_LEVEL)
/*      */     {
/* 1710 */       return this.ofAaLevel;
/*      */     }
/* 1712 */     if (p_getOptionFloatValueOF_1_ == Options.AF_LEVEL)
/*      */     {
/* 1714 */       return this.ofAfLevel;
/*      */     }
/* 1716 */     if (p_getOptionFloatValueOF_1_ == Options.MIPMAP_TYPE)
/*      */     {
/* 1718 */       return this.ofMipmapType;
/*      */     }
/* 1720 */     if (p_getOptionFloatValueOF_1_ == Options.FRAMERATE_LIMIT)
/*      */     {
/* 1722 */       return (this.limitFramerate == Options.FRAMERATE_LIMIT.getValueMax() && this.enableVsync) ? 0.0F : this.limitFramerate;
/*      */     }
/* 1724 */     if (p_getOptionFloatValueOF_1_ == Options.FULLSCREEN_MODE) {
/*      */       
/* 1726 */       if (this.ofFullscreenMode.equals("Default"))
/*      */       {
/* 1728 */         return 0.0F;
/*      */       }
/*      */ 
/*      */       
/* 1732 */       List<String> list = Arrays.asList(Config.getDisplayModeNames());
/* 1733 */       int i = list.indexOf(this.ofFullscreenMode);
/* 1734 */       return (i < 0) ? 0.0F : (i + 1);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1739 */     return Float.MAX_VALUE;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void setOptionValueOF(Options p_setOptionValueOF_1_, int p_setOptionValueOF_2_) {
/* 1745 */     if (p_setOptionValueOF_1_ == Options.FOG_FANCY)
/*      */     {
/* 1747 */       switch (this.ofFogType) {
/*      */         
/*      */         case 1:
/* 1750 */           this.ofFogType = 2;
/*      */           
/* 1752 */           if (!Config.isFancyFogAvailable())
/*      */           {
/* 1754 */             this.ofFogType = 3;
/*      */           }
/*      */           break;
/*      */ 
/*      */         
/*      */         case 2:
/* 1760 */           this.ofFogType = 3;
/*      */           break;
/*      */         
/*      */         case 3:
/* 1764 */           this.ofFogType = 1;
/*      */           break;
/*      */         
/*      */         default:
/* 1768 */           this.ofFogType = 1;
/*      */           break;
/*      */       } 
/*      */     }
/* 1772 */     if (p_setOptionValueOF_1_ == Options.FOG_START) {
/*      */       
/* 1774 */       this.ofFogStart += 0.2F;
/*      */       
/* 1776 */       if (this.ofFogStart > 0.81F)
/*      */       {
/* 1778 */         this.ofFogStart = 0.2F;
/*      */       }
/*      */     } 
/*      */     
/* 1782 */     if (p_setOptionValueOF_1_ == Options.SMOOTH_FPS)
/*      */     {
/* 1784 */       this.ofSmoothFps = !this.ofSmoothFps;
/*      */     }
/*      */     
/* 1787 */     if (p_setOptionValueOF_1_ == Options.SMOOTH_WORLD) {
/*      */       
/* 1789 */       this.ofSmoothWorld = !this.ofSmoothWorld;
/* 1790 */       Config.updateThreadPriorities();
/*      */     } 
/*      */     
/* 1793 */     if (p_setOptionValueOF_1_ == Options.CLOUDS) {
/*      */       
/* 1795 */       this.ofClouds++;
/*      */       
/* 1797 */       if (this.ofClouds > 3)
/*      */       {
/* 1799 */         this.ofClouds = 0;
/*      */       }
/*      */       
/* 1802 */       updateRenderClouds();
/* 1803 */       this.mc.renderGlobal.resetClouds();
/*      */     } 
/*      */     
/* 1806 */     if (p_setOptionValueOF_1_ == Options.TREES) {
/*      */       
/* 1808 */       this.ofTrees = nextValue(this.ofTrees, OF_TREES_VALUES);
/* 1809 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1812 */     if (p_setOptionValueOF_1_ == Options.DROPPED_ITEMS) {
/*      */       
/* 1814 */       this.ofDroppedItems++;
/*      */       
/* 1816 */       if (this.ofDroppedItems > 2)
/*      */       {
/* 1818 */         this.ofDroppedItems = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1822 */     if (p_setOptionValueOF_1_ == Options.RAIN) {
/*      */       
/* 1824 */       this.ofRain++;
/*      */       
/* 1826 */       if (this.ofRain > 3)
/*      */       {
/* 1828 */         this.ofRain = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1832 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_WATER) {
/*      */       
/* 1834 */       this.ofAnimatedWater++;
/*      */       
/* 1836 */       if (this.ofAnimatedWater == 1)
/*      */       {
/* 1838 */         this.ofAnimatedWater++;
/*      */       }
/*      */       
/* 1841 */       if (this.ofAnimatedWater > 2)
/*      */       {
/* 1843 */         this.ofAnimatedWater = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1847 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_LAVA) {
/*      */       
/* 1849 */       this.ofAnimatedLava++;
/*      */       
/* 1851 */       if (this.ofAnimatedLava == 1)
/*      */       {
/* 1853 */         this.ofAnimatedLava++;
/*      */       }
/*      */       
/* 1856 */       if (this.ofAnimatedLava > 2)
/*      */       {
/* 1858 */         this.ofAnimatedLava = 0;
/*      */       }
/*      */     } 
/*      */     
/* 1862 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_FIRE)
/*      */     {
/* 1864 */       this.ofAnimatedFire = !this.ofAnimatedFire;
/*      */     }
/*      */     
/* 1867 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_PORTAL)
/*      */     {
/* 1869 */       this.ofAnimatedPortal = !this.ofAnimatedPortal;
/*      */     }
/*      */     
/* 1872 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_REDSTONE)
/*      */     {
/* 1874 */       this.ofAnimatedRedstone = !this.ofAnimatedRedstone;
/*      */     }
/*      */     
/* 1877 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_EXPLOSION)
/*      */     {
/* 1879 */       this.ofAnimatedExplosion = !this.ofAnimatedExplosion;
/*      */     }
/*      */     
/* 1882 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_FLAME)
/*      */     {
/* 1884 */       this.ofAnimatedFlame = !this.ofAnimatedFlame;
/*      */     }
/*      */     
/* 1887 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_SMOKE)
/*      */     {
/* 1889 */       this.ofAnimatedSmoke = !this.ofAnimatedSmoke;
/*      */     }
/*      */     
/* 1892 */     if (p_setOptionValueOF_1_ == Options.VOID_PARTICLES)
/*      */     {
/* 1894 */       this.ofVoidParticles = !this.ofVoidParticles;
/*      */     }
/*      */     
/* 1897 */     if (p_setOptionValueOF_1_ == Options.WATER_PARTICLES)
/*      */     {
/* 1899 */       this.ofWaterParticles = !this.ofWaterParticles;
/*      */     }
/*      */     
/* 1902 */     if (p_setOptionValueOF_1_ == Options.PORTAL_PARTICLES)
/*      */     {
/* 1904 */       this.ofPortalParticles = !this.ofPortalParticles;
/*      */     }
/*      */     
/* 1907 */     if (p_setOptionValueOF_1_ == Options.POTION_PARTICLES)
/*      */     {
/* 1909 */       this.ofPotionParticles = !this.ofPotionParticles;
/*      */     }
/*      */     
/* 1912 */     if (p_setOptionValueOF_1_ == Options.FIREWORK_PARTICLES)
/*      */     {
/* 1914 */       this.ofFireworkParticles = !this.ofFireworkParticles;
/*      */     }
/*      */     
/* 1917 */     if (p_setOptionValueOF_1_ == Options.DRIPPING_WATER_LAVA)
/*      */     {
/* 1919 */       this.ofDrippingWaterLava = !this.ofDrippingWaterLava;
/*      */     }
/*      */     
/* 1922 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_TERRAIN)
/*      */     {
/* 1924 */       this.ofAnimatedTerrain = !this.ofAnimatedTerrain;
/*      */     }
/*      */     
/* 1927 */     if (p_setOptionValueOF_1_ == Options.ANIMATED_TEXTURES)
/*      */     {
/* 1929 */       this.ofAnimatedTextures = !this.ofAnimatedTextures;
/*      */     }
/*      */     
/* 1932 */     if (p_setOptionValueOF_1_ == Options.RAIN_SPLASH)
/*      */     {
/* 1934 */       this.ofRainSplash = !this.ofRainSplash;
/*      */     }
/*      */     
/* 1937 */     if (p_setOptionValueOF_1_ == Options.LAGOMETER)
/*      */     {
/* 1939 */       this.ofLagometer = !this.ofLagometer;
/*      */     }
/*      */     
/* 1942 */     if (p_setOptionValueOF_1_ == Options.SHOW_FPS)
/*      */     {
/* 1944 */       this.ofShowFps = !this.ofShowFps;
/*      */     }
/*      */     
/* 1947 */     if (p_setOptionValueOF_1_ == Options.AUTOSAVE_TICKS) {
/*      */       
/* 1949 */       this.ofAutoSaveTicks *= 10;
/*      */       
/* 1951 */       if (this.ofAutoSaveTicks > 40000)
/*      */       {
/* 1953 */         this.ofAutoSaveTicks = 40;
/*      */       }
/*      */     } 
/*      */     
/* 1957 */     if (p_setOptionValueOF_1_ == Options.BETTER_GRASS) {
/*      */       
/* 1959 */       this.ofBetterGrass++;
/*      */       
/* 1961 */       if (this.ofBetterGrass > 3)
/*      */       {
/* 1963 */         this.ofBetterGrass = 1;
/*      */       }
/*      */       
/* 1966 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 1969 */     if (p_setOptionValueOF_1_ == Options.CONNECTED_TEXTURES) {
/*      */       
/* 1971 */       this.ofConnectedTextures++;
/*      */       
/* 1973 */       if (this.ofConnectedTextures > 3)
/*      */       {
/* 1975 */         this.ofConnectedTextures = 1;
/*      */       }
/*      */       
/* 1978 */       if (this.ofConnectedTextures == 2) {
/*      */         
/* 1980 */         this.mc.renderGlobal.loadRenderers();
/*      */       }
/*      */       else {
/*      */         
/* 1984 */         this.mc.refreshResources();
/*      */       } 
/*      */     } 
/*      */     
/* 1988 */     if (p_setOptionValueOF_1_ == Options.WEATHER)
/*      */     {
/* 1990 */       this.ofWeather = !this.ofWeather;
/*      */     }
/*      */     
/* 1993 */     if (p_setOptionValueOF_1_ == Options.SKY)
/*      */     {
/* 1995 */       this.ofSky = !this.ofSky;
/*      */     }
/*      */     
/* 1998 */     if (p_setOptionValueOF_1_ == Options.STARS)
/*      */     {
/* 2000 */       this.ofStars = !this.ofStars;
/*      */     }
/*      */     
/* 2003 */     if (p_setOptionValueOF_1_ == Options.SUN_MOON)
/*      */     {
/* 2005 */       this.ofSunMoon = !this.ofSunMoon;
/*      */     }
/*      */     
/* 2008 */     if (p_setOptionValueOF_1_ == Options.VIGNETTE) {
/*      */       
/* 2010 */       this.ofVignette++;
/*      */       
/* 2012 */       if (this.ofVignette > 2)
/*      */       {
/* 2014 */         this.ofVignette = 0;
/*      */       }
/*      */     } 
/*      */     
/* 2018 */     if (p_setOptionValueOF_1_ == Options.CHUNK_UPDATES) {
/*      */       
/* 2020 */       this.ofChunkUpdates++;
/*      */       
/* 2022 */       if (this.ofChunkUpdates > 5)
/*      */       {
/* 2024 */         this.ofChunkUpdates = 1;
/*      */       }
/*      */     } 
/*      */     
/* 2028 */     if (p_setOptionValueOF_1_ == Options.CHUNK_UPDATES_DYNAMIC)
/*      */     {
/* 2030 */       this.ofChunkUpdatesDynamic = !this.ofChunkUpdatesDynamic;
/*      */     }
/*      */     
/* 2033 */     if (p_setOptionValueOF_1_ == Options.TIME) {
/*      */       
/* 2035 */       this.ofTime++;
/*      */       
/* 2037 */       if (this.ofTime > 2)
/*      */       {
/* 2039 */         this.ofTime = 0;
/*      */       }
/*      */     } 
/*      */     
/* 2043 */     if (p_setOptionValueOF_1_ == Options.CLEAR_WATER) {
/*      */       
/* 2045 */       this.ofClearWater = !this.ofClearWater;
/* 2046 */       updateWaterOpacity();
/*      */     } 
/*      */     
/* 2049 */     if (p_setOptionValueOF_1_ == Options.PROFILER)
/*      */     {
/* 2051 */       this.ofProfiler = !this.ofProfiler;
/*      */     }
/*      */     
/* 2054 */     if (p_setOptionValueOF_1_ == Options.BETTER_SNOW) {
/*      */       
/* 2056 */       this.ofBetterSnow = !this.ofBetterSnow;
/* 2057 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 2060 */     if (p_setOptionValueOF_1_ == Options.SWAMP_COLORS) {
/*      */       
/* 2062 */       this.ofSwampColors = !this.ofSwampColors;
/* 2063 */       CustomColors.updateUseDefaultGrassFoliageColors();
/* 2064 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 2067 */     if (p_setOptionValueOF_1_ == Options.RANDOM_MOBS) {
/*      */       
/* 2069 */       this.ofRandomMobs = !this.ofRandomMobs;
/* 2070 */       RandomMobs.resetTextures();
/*      */     } 
/*      */     
/* 2073 */     if (p_setOptionValueOF_1_ == Options.SMOOTH_BIOMES) {
/*      */       
/* 2075 */       this.ofSmoothBiomes = !this.ofSmoothBiomes;
/* 2076 */       CustomColors.updateUseDefaultGrassFoliageColors();
/* 2077 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 2080 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_FONTS) {
/*      */       
/* 2082 */       this.ofCustomFonts = !this.ofCustomFonts;
/* 2083 */       this.mc.fontRendererObj.onResourceManagerReload(Config.getResourceManager());
/* 2084 */       this.mc.standardGalacticFontRenderer.onResourceManagerReload(Config.getResourceManager());
/*      */     } 
/*      */     
/* 2087 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_COLORS) {
/*      */       
/* 2089 */       this.ofCustomColors = !this.ofCustomColors;
/* 2090 */       CustomColors.update();
/* 2091 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 2094 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_ITEMS) {
/*      */       
/* 2096 */       this.ofCustomItems = !this.ofCustomItems;
/* 2097 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 2100 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_SKY) {
/*      */       
/* 2102 */       this.ofCustomSky = !this.ofCustomSky;
/* 2103 */       CustomSky.update();
/*      */     } 
/*      */     
/* 2106 */     if (p_setOptionValueOF_1_ == Options.SHOW_CAPES)
/*      */     {
/* 2108 */       this.ofShowCapes = !this.ofShowCapes;
/*      */     }
/*      */     
/* 2111 */     if (p_setOptionValueOF_1_ == Options.NATURAL_TEXTURES) {
/*      */       
/* 2113 */       this.ofNaturalTextures = !this.ofNaturalTextures;
/* 2114 */       NaturalTextures.update();
/* 2115 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 2118 */     if (p_setOptionValueOF_1_ == Options.FAST_MATH) {
/*      */       
/* 2120 */       this.ofFastMath = !this.ofFastMath;
/* 2121 */       MathHelper.fastMath = this.ofFastMath;
/*      */     } 
/*      */     
/* 2124 */     if (p_setOptionValueOF_1_ == Options.FAST_RENDER) {
/*      */       
/* 2126 */       if (!this.ofFastRender && Config.isShaders()) {
/*      */         
/* 2128 */         Config.showGuiMessage(Lang.get("of.message.fr.shaders1"), Lang.get("of.message.fr.shaders2"));
/*      */         
/*      */         return;
/*      */       } 
/* 2132 */       this.ofFastRender = !this.ofFastRender;
/*      */       
/* 2134 */       if (this.ofFastRender)
/*      */       {
/* 2136 */         this.mc.entityRenderer.stopUseShader();
/*      */       }
/*      */       
/* 2139 */       Config.updateFramebufferSize();
/*      */     } 
/*      */     
/* 2142 */     if (p_setOptionValueOF_1_ == Options.TRANSLUCENT_BLOCKS) {
/*      */       
/* 2144 */       if (this.ofTranslucentBlocks == 0) {
/*      */         
/* 2146 */         this.ofTranslucentBlocks = 1;
/*      */       }
/* 2148 */       else if (this.ofTranslucentBlocks == 1) {
/*      */         
/* 2150 */         this.ofTranslucentBlocks = 2;
/*      */       }
/* 2152 */       else if (this.ofTranslucentBlocks == 2) {
/*      */         
/* 2154 */         this.ofTranslucentBlocks = 0;
/*      */       }
/*      */       else {
/*      */         
/* 2158 */         this.ofTranslucentBlocks = 0;
/*      */       } 
/*      */       
/* 2161 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 2164 */     if (p_setOptionValueOF_1_ == Options.LAZY_CHUNK_LOADING) {
/*      */       
/* 2166 */       this.ofLazyChunkLoading = !this.ofLazyChunkLoading;
/* 2167 */       Config.updateAvailableProcessors();
/*      */       
/* 2169 */       if (!Config.isSingleProcessor())
/*      */       {
/* 2171 */         this.ofLazyChunkLoading = false;
/*      */       }
/*      */       
/* 2174 */       this.mc.renderGlobal.loadRenderers();
/*      */     } 
/*      */     
/* 2177 */     if (p_setOptionValueOF_1_ == Options.DYNAMIC_FOV)
/*      */     {
/* 2179 */       this.ofDynamicFov = !this.ofDynamicFov;
/*      */     }
/*      */     
/* 2182 */     if (p_setOptionValueOF_1_ == Options.ALTERNATE_BLOCKS) {
/*      */       
/* 2184 */       this.ofAlternateBlocks = !this.ofAlternateBlocks;
/* 2185 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 2188 */     if (p_setOptionValueOF_1_ == Options.DYNAMIC_LIGHTS) {
/*      */       
/* 2190 */       this.ofDynamicLights = nextValue(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
/* 2191 */       DynamicLights.removeLights(this.mc.renderGlobal);
/*      */     } 
/*      */     
/* 2194 */     if (p_setOptionValueOF_1_ == Options.SCREENSHOT_SIZE) {
/*      */       
/* 2196 */       this.ofScreenshotSize++;
/*      */       
/* 2198 */       if (this.ofScreenshotSize > 4)
/*      */       {
/* 2200 */         this.ofScreenshotSize = 1;
/*      */       }
/*      */       
/* 2203 */       if (!OpenGlHelper.isFramebufferEnabled())
/*      */       {
/* 2205 */         this.ofScreenshotSize = 1;
/*      */       }
/*      */     } 
/*      */     
/* 2209 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_ENTITY_MODELS) {
/*      */       
/* 2211 */       this.ofCustomEntityModels = !this.ofCustomEntityModels;
/* 2212 */       this.mc.refreshResources();
/*      */     } 
/*      */     
/* 2215 */     if (p_setOptionValueOF_1_ == Options.CUSTOM_GUIS) {
/*      */       
/* 2217 */       this.ofCustomGuis = !this.ofCustomGuis;
/* 2218 */       CustomGuis.update();
/*      */     } 
/*      */     
/* 2221 */     if (p_setOptionValueOF_1_ == Options.HELD_ITEM_TOOLTIPS)
/*      */     {
/* 2223 */       this.heldItemTooltips = !this.heldItemTooltips;
/*      */     }
/*      */     
/* 2226 */     if (p_setOptionValueOF_1_ == Options.ADVANCED_TOOLTIPS)
/*      */     {
/* 2228 */       this.advancedItemTooltips = !this.advancedItemTooltips;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private String getKeyBindingOF(Options p_getKeyBindingOF_1_) {
/* 2234 */     String s = String.valueOf(I18n.format(p_getKeyBindingOF_1_.getEnumString(), new Object[0])) + ": ";
/*      */     
/* 2236 */     if (s == null)
/*      */     {
/* 2238 */       s = p_getKeyBindingOF_1_.getEnumString();
/*      */     }
/*      */     
/* 2241 */     if (p_getKeyBindingOF_1_ == Options.RENDER_DISTANCE) {
/*      */       
/* 2243 */       int l = (int)getOptionFloatValue(p_getKeyBindingOF_1_);
/* 2244 */       String s1 = I18n.format("of.options.renderDistance.tiny", new Object[0]);
/* 2245 */       int i = 2;
/*      */       
/* 2247 */       if (l >= 4) {
/*      */         
/* 2249 */         s1 = I18n.format("of.options.renderDistance.short", new Object[0]);
/* 2250 */         i = 4;
/*      */       } 
/*      */       
/* 2253 */       if (l >= 8) {
/*      */         
/* 2255 */         s1 = I18n.format("of.options.renderDistance.normal", new Object[0]);
/* 2256 */         i = 8;
/*      */       } 
/*      */       
/* 2259 */       if (l >= 16) {
/*      */         
/* 2261 */         s1 = I18n.format("of.options.renderDistance.far", new Object[0]);
/* 2262 */         i = 16;
/*      */       } 
/*      */       
/* 2265 */       if (l >= 32) {
/*      */         
/* 2267 */         s1 = Lang.get("of.options.renderDistance.extreme");
/* 2268 */         i = 32;
/*      */       } 
/*      */       
/* 2271 */       int j = this.renderDistanceChunks - i;
/* 2272 */       String s2 = s1;
/*      */       
/* 2274 */       if (j > 0)
/*      */       {
/* 2276 */         s2 = String.valueOf(s1) + "+";
/*      */       }
/*      */       
/* 2279 */       return String.valueOf(s) + l + " " + s2;
/*      */     } 
/* 2281 */     if (p_getKeyBindingOF_1_ == Options.FOG_FANCY) {
/*      */       
/* 2283 */       switch (this.ofFogType) {
/*      */         
/*      */         case 1:
/* 2286 */           return String.valueOf(s) + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2289 */           return String.valueOf(s) + Lang.getFancy();
/*      */         
/*      */         case 3:
/* 2292 */           return String.valueOf(s) + Lang.getOff();
/*      */       } 
/*      */       
/* 2295 */       return String.valueOf(s) + Lang.getOff();
/*      */     } 
/*      */     
/* 2298 */     if (p_getKeyBindingOF_1_ == Options.FOG_START)
/*      */     {
/* 2300 */       return String.valueOf(s) + this.ofFogStart;
/*      */     }
/* 2302 */     if (p_getKeyBindingOF_1_ == Options.MIPMAP_TYPE) {
/*      */       
/* 2304 */       switch (this.ofMipmapType) {
/*      */         
/*      */         case 0:
/* 2307 */           return String.valueOf(s) + Lang.get("of.options.mipmap.nearest");
/*      */         
/*      */         case 1:
/* 2310 */           return String.valueOf(s) + Lang.get("of.options.mipmap.linear");
/*      */         
/*      */         case 2:
/* 2313 */           return String.valueOf(s) + Lang.get("of.options.mipmap.bilinear");
/*      */         
/*      */         case 3:
/* 2316 */           return String.valueOf(s) + Lang.get("of.options.mipmap.trilinear");
/*      */       } 
/*      */       
/* 2319 */       return String.valueOf(s) + "of.options.mipmap.nearest";
/*      */     } 
/*      */     
/* 2322 */     if (p_getKeyBindingOF_1_ == Options.SMOOTH_FPS)
/*      */     {
/* 2324 */       return this.ofSmoothFps ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2326 */     if (p_getKeyBindingOF_1_ == Options.SMOOTH_WORLD)
/*      */     {
/* 2328 */       return this.ofSmoothWorld ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2330 */     if (p_getKeyBindingOF_1_ == Options.CLOUDS) {
/*      */       
/* 2332 */       switch (this.ofClouds) {
/*      */         
/*      */         case 1:
/* 2335 */           return String.valueOf(s) + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2338 */           return String.valueOf(s) + Lang.getFancy();
/*      */         
/*      */         case 3:
/* 2341 */           return String.valueOf(s) + Lang.getOff();
/*      */       } 
/*      */       
/* 2344 */       return String.valueOf(s) + Lang.getDefault();
/*      */     } 
/*      */     
/* 2347 */     if (p_getKeyBindingOF_1_ == Options.TREES) {
/*      */       
/* 2349 */       switch (this.ofTrees) {
/*      */         
/*      */         case 1:
/* 2352 */           return String.valueOf(s) + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2355 */           return String.valueOf(s) + Lang.getFancy();
/*      */ 
/*      */         
/*      */         default:
/* 2359 */           return String.valueOf(s) + Lang.getDefault();
/*      */         case 4:
/*      */           break;
/* 2362 */       }  return String.valueOf(s) + Lang.get("of.general.smart");
/*      */     } 
/*      */     
/* 2365 */     if (p_getKeyBindingOF_1_ == Options.DROPPED_ITEMS) {
/*      */       
/* 2367 */       switch (this.ofDroppedItems) {
/*      */         
/*      */         case 1:
/* 2370 */           return String.valueOf(s) + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2373 */           return String.valueOf(s) + Lang.getFancy();
/*      */       } 
/*      */       
/* 2376 */       return String.valueOf(s) + Lang.getDefault();
/*      */     } 
/*      */     
/* 2379 */     if (p_getKeyBindingOF_1_ == Options.RAIN) {
/*      */       
/* 2381 */       switch (this.ofRain) {
/*      */         
/*      */         case 1:
/* 2384 */           return String.valueOf(s) + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2387 */           return String.valueOf(s) + Lang.getFancy();
/*      */         
/*      */         case 3:
/* 2390 */           return String.valueOf(s) + Lang.getOff();
/*      */       } 
/*      */       
/* 2393 */       return String.valueOf(s) + Lang.getDefault();
/*      */     } 
/*      */     
/* 2396 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_WATER) {
/*      */       
/* 2398 */       switch (this.ofAnimatedWater) {
/*      */         
/*      */         case 1:
/* 2401 */           return String.valueOf(s) + Lang.get("of.options.animation.dynamic");
/*      */         
/*      */         case 2:
/* 2404 */           return String.valueOf(s) + Lang.getOff();
/*      */       } 
/*      */       
/* 2407 */       return String.valueOf(s) + Lang.getOn();
/*      */     } 
/*      */     
/* 2410 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_LAVA) {
/*      */       
/* 2412 */       switch (this.ofAnimatedLava) {
/*      */         
/*      */         case 1:
/* 2415 */           return String.valueOf(s) + Lang.get("of.options.animation.dynamic");
/*      */         
/*      */         case 2:
/* 2418 */           return String.valueOf(s) + Lang.getOff();
/*      */       } 
/*      */       
/* 2421 */       return String.valueOf(s) + Lang.getOn();
/*      */     } 
/*      */     
/* 2424 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_FIRE)
/*      */     {
/* 2426 */       return this.ofAnimatedFire ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2428 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_PORTAL)
/*      */     {
/* 2430 */       return this.ofAnimatedPortal ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2432 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_REDSTONE)
/*      */     {
/* 2434 */       return this.ofAnimatedRedstone ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2436 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_EXPLOSION)
/*      */     {
/* 2438 */       return this.ofAnimatedExplosion ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2440 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_FLAME)
/*      */     {
/* 2442 */       return this.ofAnimatedFlame ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2444 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_SMOKE)
/*      */     {
/* 2446 */       return this.ofAnimatedSmoke ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2448 */     if (p_getKeyBindingOF_1_ == Options.VOID_PARTICLES)
/*      */     {
/* 2450 */       return this.ofVoidParticles ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2452 */     if (p_getKeyBindingOF_1_ == Options.WATER_PARTICLES)
/*      */     {
/* 2454 */       return this.ofWaterParticles ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2456 */     if (p_getKeyBindingOF_1_ == Options.PORTAL_PARTICLES)
/*      */     {
/* 2458 */       return this.ofPortalParticles ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2460 */     if (p_getKeyBindingOF_1_ == Options.POTION_PARTICLES)
/*      */     {
/* 2462 */       return this.ofPotionParticles ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2464 */     if (p_getKeyBindingOF_1_ == Options.FIREWORK_PARTICLES)
/*      */     {
/* 2466 */       return this.ofFireworkParticles ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2468 */     if (p_getKeyBindingOF_1_ == Options.DRIPPING_WATER_LAVA)
/*      */     {
/* 2470 */       return this.ofDrippingWaterLava ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2472 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_TERRAIN)
/*      */     {
/* 2474 */       return this.ofAnimatedTerrain ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2476 */     if (p_getKeyBindingOF_1_ == Options.ANIMATED_TEXTURES)
/*      */     {
/* 2478 */       return this.ofAnimatedTextures ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2480 */     if (p_getKeyBindingOF_1_ == Options.RAIN_SPLASH)
/*      */     {
/* 2482 */       return this.ofRainSplash ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2484 */     if (p_getKeyBindingOF_1_ == Options.LAGOMETER)
/*      */     {
/* 2486 */       return this.ofLagometer ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2488 */     if (p_getKeyBindingOF_1_ == Options.SHOW_FPS)
/*      */     {
/* 2490 */       return this.ofShowFps ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2492 */     if (p_getKeyBindingOF_1_ == Options.AUTOSAVE_TICKS) {
/*      */       
/* 2494 */       if (this.ofAutoSaveTicks <= 40)
/*      */       {
/* 2496 */         return String.valueOf(s) + Lang.get("of.options.save.default");
/*      */       }
/* 2498 */       if (this.ofAutoSaveTicks <= 400)
/*      */       {
/* 2500 */         return String.valueOf(s) + Lang.get("of.options.save.20s");
/*      */       }
/*      */ 
/*      */       
/* 2504 */       return (this.ofAutoSaveTicks <= 4000) ? (String.valueOf(s) + Lang.get("of.options.save.3min")) : (String.valueOf(s) + Lang.get("of.options.save.30min"));
/*      */     } 
/*      */     
/* 2507 */     if (p_getKeyBindingOF_1_ == Options.BETTER_GRASS) {
/*      */       
/* 2509 */       switch (this.ofBetterGrass) {
/*      */         
/*      */         case 1:
/* 2512 */           return String.valueOf(s) + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2515 */           return String.valueOf(s) + Lang.getFancy();
/*      */       } 
/*      */       
/* 2518 */       return String.valueOf(s) + Lang.getOff();
/*      */     } 
/*      */     
/* 2521 */     if (p_getKeyBindingOF_1_ == Options.CONNECTED_TEXTURES) {
/*      */       
/* 2523 */       switch (this.ofConnectedTextures) {
/*      */         
/*      */         case 1:
/* 2526 */           return String.valueOf(s) + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2529 */           return String.valueOf(s) + Lang.getFancy();
/*      */       } 
/*      */       
/* 2532 */       return String.valueOf(s) + Lang.getOff();
/*      */     } 
/*      */     
/* 2535 */     if (p_getKeyBindingOF_1_ == Options.WEATHER)
/*      */     {
/* 2537 */       return this.ofWeather ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2539 */     if (p_getKeyBindingOF_1_ == Options.SKY)
/*      */     {
/* 2541 */       return this.ofSky ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2543 */     if (p_getKeyBindingOF_1_ == Options.STARS)
/*      */     {
/* 2545 */       return this.ofStars ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2547 */     if (p_getKeyBindingOF_1_ == Options.SUN_MOON)
/*      */     {
/* 2549 */       return this.ofSunMoon ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2551 */     if (p_getKeyBindingOF_1_ == Options.VIGNETTE) {
/*      */       
/* 2553 */       switch (this.ofVignette) {
/*      */         
/*      */         case 1:
/* 2556 */           return String.valueOf(s) + Lang.getFast();
/*      */         
/*      */         case 2:
/* 2559 */           return String.valueOf(s) + Lang.getFancy();
/*      */       } 
/*      */       
/* 2562 */       return String.valueOf(s) + Lang.getDefault();
/*      */     } 
/*      */     
/* 2565 */     if (p_getKeyBindingOF_1_ == Options.CHUNK_UPDATES)
/*      */     {
/* 2567 */       return String.valueOf(s) + this.ofChunkUpdates;
/*      */     }
/* 2569 */     if (p_getKeyBindingOF_1_ == Options.CHUNK_UPDATES_DYNAMIC)
/*      */     {
/* 2571 */       return this.ofChunkUpdatesDynamic ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2573 */     if (p_getKeyBindingOF_1_ == Options.TIME) {
/*      */       
/* 2575 */       if (this.ofTime == 1)
/*      */       {
/* 2577 */         return String.valueOf(s) + Lang.get("of.options.time.dayOnly");
/*      */       }
/*      */ 
/*      */       
/* 2581 */       return (this.ofTime == 2) ? (String.valueOf(s) + Lang.get("of.options.time.nightOnly")) : (String.valueOf(s) + Lang.getDefault());
/*      */     } 
/*      */     
/* 2584 */     if (p_getKeyBindingOF_1_ == Options.CLEAR_WATER)
/*      */     {
/* 2586 */       return this.ofClearWater ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2588 */     if (p_getKeyBindingOF_1_ == Options.AA_LEVEL) {
/*      */       
/* 2590 */       String s3 = "";
/*      */       
/* 2592 */       if (this.ofAaLevel != Config.getAntialiasingLevel())
/*      */       {
/* 2594 */         s3 = " (" + Lang.get("of.general.restart") + ")";
/*      */       }
/*      */       
/* 2597 */       return (this.ofAaLevel == 0) ? (String.valueOf(s) + Lang.getOff() + s3) : (String.valueOf(s) + this.ofAaLevel + s3);
/*      */     } 
/* 2599 */     if (p_getKeyBindingOF_1_ == Options.AF_LEVEL)
/*      */     {
/* 2601 */       return (this.ofAfLevel == 1) ? (String.valueOf(s) + Lang.getOff()) : (String.valueOf(s) + this.ofAfLevel);
/*      */     }
/* 2603 */     if (p_getKeyBindingOF_1_ == Options.PROFILER)
/*      */     {
/* 2605 */       return this.ofProfiler ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2607 */     if (p_getKeyBindingOF_1_ == Options.BETTER_SNOW)
/*      */     {
/* 2609 */       return this.ofBetterSnow ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2611 */     if (p_getKeyBindingOF_1_ == Options.SWAMP_COLORS)
/*      */     {
/* 2613 */       return this.ofSwampColors ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2615 */     if (p_getKeyBindingOF_1_ == Options.RANDOM_MOBS)
/*      */     {
/* 2617 */       return this.ofRandomMobs ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2619 */     if (p_getKeyBindingOF_1_ == Options.SMOOTH_BIOMES)
/*      */     {
/* 2621 */       return this.ofSmoothBiomes ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2623 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_FONTS)
/*      */     {
/* 2625 */       return this.ofCustomFonts ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2627 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_COLORS)
/*      */     {
/* 2629 */       return this.ofCustomColors ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2631 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_SKY)
/*      */     {
/* 2633 */       return this.ofCustomSky ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2635 */     if (p_getKeyBindingOF_1_ == Options.SHOW_CAPES)
/*      */     {
/* 2637 */       return this.ofShowCapes ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2639 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_ITEMS)
/*      */     {
/* 2641 */       return this.ofCustomItems ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2643 */     if (p_getKeyBindingOF_1_ == Options.NATURAL_TEXTURES)
/*      */     {
/* 2645 */       return this.ofNaturalTextures ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2647 */     if (p_getKeyBindingOF_1_ == Options.FAST_MATH)
/*      */     {
/* 2649 */       return this.ofFastMath ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2651 */     if (p_getKeyBindingOF_1_ == Options.FAST_RENDER)
/*      */     {
/* 2653 */       return this.ofFastRender ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2655 */     if (p_getKeyBindingOF_1_ == Options.TRANSLUCENT_BLOCKS) {
/*      */       
/* 2657 */       if (this.ofTranslucentBlocks == 1)
/*      */       {
/* 2659 */         return String.valueOf(s) + Lang.getFast();
/*      */       }
/*      */ 
/*      */       
/* 2663 */       return (this.ofTranslucentBlocks == 2) ? (String.valueOf(s) + Lang.getFancy()) : (String.valueOf(s) + Lang.getDefault());
/*      */     } 
/*      */     
/* 2666 */     if (p_getKeyBindingOF_1_ == Options.LAZY_CHUNK_LOADING)
/*      */     {
/* 2668 */       return this.ofLazyChunkLoading ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2670 */     if (p_getKeyBindingOF_1_ == Options.DYNAMIC_FOV)
/*      */     {
/* 2672 */       return this.ofDynamicFov ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2674 */     if (p_getKeyBindingOF_1_ == Options.ALTERNATE_BLOCKS)
/*      */     {
/* 2676 */       return this.ofAlternateBlocks ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2678 */     if (p_getKeyBindingOF_1_ == Options.DYNAMIC_LIGHTS) {
/*      */       
/* 2680 */       int k = indexOf(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
/* 2681 */       return String.valueOf(s) + getTranslation(KEYS_DYNAMIC_LIGHTS, k);
/*      */     } 
/* 2683 */     if (p_getKeyBindingOF_1_ == Options.SCREENSHOT_SIZE)
/*      */     {
/* 2685 */       return (this.ofScreenshotSize <= 1) ? (String.valueOf(s) + Lang.getDefault()) : (String.valueOf(s) + this.ofScreenshotSize + "x");
/*      */     }
/* 2687 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_ENTITY_MODELS)
/*      */     {
/* 2689 */       return this.ofCustomEntityModels ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2691 */     if (p_getKeyBindingOF_1_ == Options.CUSTOM_GUIS)
/*      */     {
/* 2693 */       return this.ofCustomGuis ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2695 */     if (p_getKeyBindingOF_1_ == Options.FULLSCREEN_MODE)
/*      */     {
/* 2697 */       return this.ofFullscreenMode.equals("Default") ? (String.valueOf(s) + Lang.getDefault()) : (String.valueOf(s) + this.ofFullscreenMode);
/*      */     }
/* 2699 */     if (p_getKeyBindingOF_1_ == Options.HELD_ITEM_TOOLTIPS)
/*      */     {
/* 2701 */       return this.heldItemTooltips ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2703 */     if (p_getKeyBindingOF_1_ == Options.ADVANCED_TOOLTIPS)
/*      */     {
/* 2705 */       return this.advancedItemTooltips ? (String.valueOf(s) + Lang.getOn()) : (String.valueOf(s) + Lang.getOff());
/*      */     }
/* 2707 */     if (p_getKeyBindingOF_1_ == Options.FRAMERATE_LIMIT) {
/*      */       
/* 2709 */       float f = getOptionFloatValue(p_getKeyBindingOF_1_);
/*      */       
/* 2711 */       if (f == 0.0F)
/*      */       {
/* 2713 */         return String.valueOf(s) + Lang.get("of.options.framerateLimit.vsync");
/*      */       }
/*      */ 
/*      */       
/* 2717 */       return (f == p_getKeyBindingOF_1_.valueMax) ? (String.valueOf(s) + I18n.format("options.framerateLimit.max", new Object[0])) : (String.valueOf(s) + (int)f + " fps");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 2722 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadOfOptions() {
/*      */     try {
/* 2730 */       File file1 = this.optionsFileOF;
/*      */       
/* 2732 */       if (!file1.exists())
/*      */       {
/* 2734 */         file1 = this.optionsFile;
/*      */       }
/*      */       
/* 2737 */       if (!file1.exists()) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 2742 */       BufferedReader bufferedreader = new BufferedReader(new InputStreamReader(new FileInputStream(file1), StandardCharsets.UTF_8));
/* 2743 */       String s = "";
/*      */       
/* 2745 */       while ((s = bufferedreader.readLine()) != null) {
/*      */ 
/*      */         
/*      */         try {
/* 2749 */           String[] astring = s.split(":");
/*      */           
/* 2751 */           if (astring[0].equals("ofRenderDistanceChunks") && astring.length >= 2) {
/*      */             
/* 2753 */             this.renderDistanceChunks = Integer.valueOf(astring[1]).intValue();
/* 2754 */             this.renderDistanceChunks = Config.limit(this.renderDistanceChunks, 2, 1024);
/*      */           } 
/*      */           
/* 2757 */           if (astring[0].equals("ofFogType") && astring.length >= 2) {
/*      */             
/* 2759 */             this.ofFogType = Integer.valueOf(astring[1]).intValue();
/* 2760 */             this.ofFogType = Config.limit(this.ofFogType, 1, 3);
/*      */           } 
/*      */           
/* 2763 */           if (astring[0].equals("ofFogStart") && astring.length >= 2) {
/*      */             
/* 2765 */             this.ofFogStart = Float.valueOf(astring[1]).floatValue();
/*      */             
/* 2767 */             if (this.ofFogStart < 0.2F)
/*      */             {
/* 2769 */               this.ofFogStart = 0.2F;
/*      */             }
/*      */             
/* 2772 */             if (this.ofFogStart > 0.81F)
/*      */             {
/* 2774 */               this.ofFogStart = 0.8F;
/*      */             }
/*      */           } 
/*      */           
/* 2778 */           if (astring[0].equals("ofMipmapType") && astring.length >= 2) {
/*      */             
/* 2780 */             this.ofMipmapType = Integer.valueOf(astring[1]).intValue();
/* 2781 */             this.ofMipmapType = Config.limit(this.ofMipmapType, 0, 3);
/*      */           } 
/*      */           
/* 2784 */           if (astring[0].equals("ofOcclusionFancy") && astring.length >= 2)
/*      */           {
/* 2786 */             this.ofOcclusionFancy = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2789 */           if (astring[0].equals("ofSmoothFps") && astring.length >= 2)
/*      */           {
/* 2791 */             this.ofSmoothFps = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2794 */           if (astring[0].equals("ofSmoothWorld") && astring.length >= 2)
/*      */           {
/* 2796 */             this.ofSmoothWorld = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2799 */           if (astring[0].equals("ofAoLevel") && astring.length >= 2) {
/*      */             
/* 2801 */             this.ofAoLevel = Float.valueOf(astring[1]).floatValue();
/* 2802 */             this.ofAoLevel = Config.limit(this.ofAoLevel, 0.0F, 1.0F);
/*      */           } 
/*      */           
/* 2805 */           if (astring[0].equals("ofClouds") && astring.length >= 2) {
/*      */             
/* 2807 */             this.ofClouds = Integer.valueOf(astring[1]).intValue();
/* 2808 */             this.ofClouds = Config.limit(this.ofClouds, 0, 3);
/* 2809 */             updateRenderClouds();
/*      */           } 
/*      */           
/* 2812 */           if (astring[0].equals("ofCloudsHeight") && astring.length >= 2) {
/*      */             
/* 2814 */             this.ofCloudsHeight = Float.valueOf(astring[1]).floatValue();
/* 2815 */             this.ofCloudsHeight = Config.limit(this.ofCloudsHeight, 0.0F, 1.0F);
/*      */           } 
/*      */           
/* 2818 */           if (astring[0].equals("ofTrees") && astring.length >= 2) {
/*      */             
/* 2820 */             this.ofTrees = Integer.valueOf(astring[1]).intValue();
/* 2821 */             this.ofTrees = limit(this.ofTrees, OF_TREES_VALUES);
/*      */           } 
/*      */           
/* 2824 */           if (astring[0].equals("ofDroppedItems") && astring.length >= 2) {
/*      */             
/* 2826 */             this.ofDroppedItems = Integer.valueOf(astring[1]).intValue();
/* 2827 */             this.ofDroppedItems = Config.limit(this.ofDroppedItems, 0, 2);
/*      */           } 
/*      */           
/* 2830 */           if (astring[0].equals("ofRain") && astring.length >= 2) {
/*      */             
/* 2832 */             this.ofRain = Integer.valueOf(astring[1]).intValue();
/* 2833 */             this.ofRain = Config.limit(this.ofRain, 0, 3);
/*      */           } 
/*      */           
/* 2836 */           if (astring[0].equals("ofAnimatedWater") && astring.length >= 2) {
/*      */             
/* 2838 */             this.ofAnimatedWater = Integer.valueOf(astring[1]).intValue();
/* 2839 */             this.ofAnimatedWater = Config.limit(this.ofAnimatedWater, 0, 2);
/*      */           } 
/*      */           
/* 2842 */           if (astring[0].equals("ofAnimatedLava") && astring.length >= 2) {
/*      */             
/* 2844 */             this.ofAnimatedLava = Integer.valueOf(astring[1]).intValue();
/* 2845 */             this.ofAnimatedLava = Config.limit(this.ofAnimatedLava, 0, 2);
/*      */           } 
/*      */           
/* 2848 */           if (astring[0].equals("ofAnimatedFire") && astring.length >= 2)
/*      */           {
/* 2850 */             this.ofAnimatedFire = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2853 */           if (astring[0].equals("ofAnimatedPortal") && astring.length >= 2)
/*      */           {
/* 2855 */             this.ofAnimatedPortal = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2858 */           if (astring[0].equals("ofAnimatedRedstone") && astring.length >= 2)
/*      */           {
/* 2860 */             this.ofAnimatedRedstone = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2863 */           if (astring[0].equals("ofAnimatedExplosion") && astring.length >= 2)
/*      */           {
/* 2865 */             this.ofAnimatedExplosion = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2868 */           if (astring[0].equals("ofAnimatedFlame") && astring.length >= 2)
/*      */           {
/* 2870 */             this.ofAnimatedFlame = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2873 */           if (astring[0].equals("ofAnimatedSmoke") && astring.length >= 2)
/*      */           {
/* 2875 */             this.ofAnimatedSmoke = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2878 */           if (astring[0].equals("ofVoidParticles") && astring.length >= 2)
/*      */           {
/* 2880 */             this.ofVoidParticles = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2883 */           if (astring[0].equals("ofWaterParticles") && astring.length >= 2)
/*      */           {
/* 2885 */             this.ofWaterParticles = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2888 */           if (astring[0].equals("ofPortalParticles") && astring.length >= 2)
/*      */           {
/* 2890 */             this.ofPortalParticles = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2893 */           if (astring[0].equals("ofPotionParticles") && astring.length >= 2)
/*      */           {
/* 2895 */             this.ofPotionParticles = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2898 */           if (astring[0].equals("ofFireworkParticles") && astring.length >= 2)
/*      */           {
/* 2900 */             this.ofFireworkParticles = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2903 */           if (astring[0].equals("ofDrippingWaterLava") && astring.length >= 2)
/*      */           {
/* 2905 */             this.ofDrippingWaterLava = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2908 */           if (astring[0].equals("ofAnimatedTerrain") && astring.length >= 2)
/*      */           {
/* 2910 */             this.ofAnimatedTerrain = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2913 */           if (astring[0].equals("ofAnimatedTextures") && astring.length >= 2)
/*      */           {
/* 2915 */             this.ofAnimatedTextures = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2918 */           if (astring[0].equals("ofRainSplash") && astring.length >= 2)
/*      */           {
/* 2920 */             this.ofRainSplash = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2923 */           if (astring[0].equals("ofLagometer") && astring.length >= 2)
/*      */           {
/* 2925 */             this.ofLagometer = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2928 */           if (astring[0].equals("ofShowFps") && astring.length >= 2)
/*      */           {
/* 2930 */             this.ofShowFps = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2933 */           if (astring[0].equals("ofAutoSaveTicks") && astring.length >= 2) {
/*      */             
/* 2935 */             this.ofAutoSaveTicks = Integer.valueOf(astring[1]).intValue();
/* 2936 */             this.ofAutoSaveTicks = Config.limit(this.ofAutoSaveTicks, 40, 40000);
/*      */           } 
/*      */           
/* 2939 */           if (astring[0].equals("ofBetterGrass") && astring.length >= 2) {
/*      */             
/* 2941 */             this.ofBetterGrass = Integer.valueOf(astring[1]).intValue();
/* 2942 */             this.ofBetterGrass = Config.limit(this.ofBetterGrass, 1, 3);
/*      */           } 
/*      */           
/* 2945 */           if (astring[0].equals("ofConnectedTextures") && astring.length >= 2) {
/*      */             
/* 2947 */             this.ofConnectedTextures = Integer.valueOf(astring[1]).intValue();
/* 2948 */             this.ofConnectedTextures = Config.limit(this.ofConnectedTextures, 1, 3);
/*      */           } 
/*      */           
/* 2951 */           if (astring[0].equals("ofWeather") && astring.length >= 2)
/*      */           {
/* 2953 */             this.ofWeather = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2956 */           if (astring[0].equals("ofSky") && astring.length >= 2)
/*      */           {
/* 2958 */             this.ofSky = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2961 */           if (astring[0].equals("ofStars") && astring.length >= 2)
/*      */           {
/* 2963 */             this.ofStars = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2966 */           if (astring[0].equals("ofSunMoon") && astring.length >= 2)
/*      */           {
/* 2968 */             this.ofSunMoon = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2971 */           if (astring[0].equals("ofVignette") && astring.length >= 2) {
/*      */             
/* 2973 */             this.ofVignette = Integer.valueOf(astring[1]).intValue();
/* 2974 */             this.ofVignette = Config.limit(this.ofVignette, 0, 2);
/*      */           } 
/*      */           
/* 2977 */           if (astring[0].equals("ofChunkUpdates") && astring.length >= 2) {
/*      */             
/* 2979 */             this.ofChunkUpdates = Integer.valueOf(astring[1]).intValue();
/* 2980 */             this.ofChunkUpdates = Config.limit(this.ofChunkUpdates, 1, 5);
/*      */           } 
/*      */           
/* 2983 */           if (astring[0].equals("ofChunkUpdatesDynamic") && astring.length >= 2)
/*      */           {
/* 2985 */             this.ofChunkUpdatesDynamic = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 2988 */           if (astring[0].equals("ofTime") && astring.length >= 2) {
/*      */             
/* 2990 */             this.ofTime = Integer.valueOf(astring[1]).intValue();
/* 2991 */             this.ofTime = Config.limit(this.ofTime, 0, 2);
/*      */           } 
/*      */           
/* 2994 */           if (astring[0].equals("ofClearWater") && astring.length >= 2) {
/*      */             
/* 2996 */             this.ofClearWater = Boolean.valueOf(astring[1]).booleanValue();
/* 2997 */             updateWaterOpacity();
/*      */           } 
/*      */           
/* 3000 */           if (astring[0].equals("ofAaLevel") && astring.length >= 2) {
/*      */             
/* 3002 */             this.ofAaLevel = Integer.valueOf(astring[1]).intValue();
/* 3003 */             this.ofAaLevel = Config.limit(this.ofAaLevel, 0, 16);
/*      */           } 
/*      */           
/* 3006 */           if (astring[0].equals("ofAfLevel") && astring.length >= 2) {
/*      */             
/* 3008 */             this.ofAfLevel = Integer.valueOf(astring[1]).intValue();
/* 3009 */             this.ofAfLevel = Config.limit(this.ofAfLevel, 1, 16);
/*      */           } 
/*      */           
/* 3012 */           if (astring[0].equals("ofProfiler") && astring.length >= 2)
/*      */           {
/* 3014 */             this.ofProfiler = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3017 */           if (astring[0].equals("ofBetterSnow") && astring.length >= 2)
/*      */           {
/* 3019 */             this.ofBetterSnow = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3022 */           if (astring[0].equals("ofSwampColors") && astring.length >= 2)
/*      */           {
/* 3024 */             this.ofSwampColors = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3027 */           if (astring[0].equals("ofRandomMobs") && astring.length >= 2)
/*      */           {
/* 3029 */             this.ofRandomMobs = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3032 */           if (astring[0].equals("ofSmoothBiomes") && astring.length >= 2)
/*      */           {
/* 3034 */             this.ofSmoothBiomes = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3037 */           if (astring[0].equals("ofCustomFonts") && astring.length >= 2)
/*      */           {
/* 3039 */             this.ofCustomFonts = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3042 */           if (astring[0].equals("ofCustomColors") && astring.length >= 2)
/*      */           {
/* 3044 */             this.ofCustomColors = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3047 */           if (astring[0].equals("ofCustomItems") && astring.length >= 2)
/*      */           {
/* 3049 */             this.ofCustomItems = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3052 */           if (astring[0].equals("ofCustomSky") && astring.length >= 2)
/*      */           {
/* 3054 */             this.ofCustomSky = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3057 */           if (astring[0].equals("ofShowCapes") && astring.length >= 2)
/*      */           {
/* 3059 */             this.ofShowCapes = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3062 */           if (astring[0].equals("ofNaturalTextures") && astring.length >= 2)
/*      */           {
/* 3064 */             this.ofNaturalTextures = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3067 */           if (astring[0].equals("ofLazyChunkLoading") && astring.length >= 2)
/*      */           {
/* 3069 */             this.ofLazyChunkLoading = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3072 */           if (astring[0].equals("ofDynamicFov") && astring.length >= 2)
/*      */           {
/* 3074 */             this.ofDynamicFov = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3077 */           if (astring[0].equals("ofAlternateBlocks") && astring.length >= 2)
/*      */           {
/* 3079 */             this.ofAlternateBlocks = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3082 */           if (astring[0].equals("ofDynamicLights") && astring.length >= 2) {
/*      */             
/* 3084 */             this.ofDynamicLights = Integer.valueOf(astring[1]).intValue();
/* 3085 */             this.ofDynamicLights = limit(this.ofDynamicLights, OF_DYNAMIC_LIGHTS);
/*      */           } 
/*      */           
/* 3088 */           if (astring[0].equals("ofScreenshotSize") && astring.length >= 2) {
/*      */             
/* 3090 */             this.ofScreenshotSize = Integer.valueOf(astring[1]).intValue();
/* 3091 */             this.ofScreenshotSize = Config.limit(this.ofScreenshotSize, 1, 4);
/*      */           } 
/*      */           
/* 3094 */           if (astring[0].equals("ofCustomEntityModels") && astring.length >= 2)
/*      */           {
/* 3096 */             this.ofCustomEntityModels = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3099 */           if (astring[0].equals("ofCustomGuis") && astring.length >= 2)
/*      */           {
/* 3101 */             this.ofCustomGuis = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3104 */           if (astring[0].equals("ofFullscreenMode") && astring.length >= 2)
/*      */           {
/* 3106 */             this.ofFullscreenMode = astring[1];
/*      */           }
/*      */           
/* 3109 */           if (astring[0].equals("ofFastMath") && astring.length >= 2) {
/*      */             
/* 3111 */             this.ofFastMath = Boolean.valueOf(astring[1]).booleanValue();
/* 3112 */             MathHelper.fastMath = this.ofFastMath;
/*      */           } 
/*      */           
/* 3115 */           if (astring[0].equals("ofFastRender") && astring.length >= 2)
/*      */           {
/* 3117 */             this.ofFastRender = Boolean.valueOf(astring[1]).booleanValue();
/*      */           }
/*      */           
/* 3120 */           if (astring[0].equals("ofTranslucentBlocks") && astring.length >= 2) {
/*      */             
/* 3122 */             this.ofTranslucentBlocks = Integer.valueOf(astring[1]).intValue();
/* 3123 */             this.ofTranslucentBlocks = Config.limit(this.ofTranslucentBlocks, 0, 2);
/*      */           } 
/*      */           
/* 3126 */           if (astring[0].equals("key_" + this.ofKeyBindZoom.getKeyDescription()))
/*      */           {
/* 3128 */             this.ofKeyBindZoom.setKeyCode(Integer.parseInt(astring[1]));
/*      */           }
/*      */         }
/* 3131 */         catch (Exception exception1) {
/*      */           
/* 3133 */           Config.dbg("Skipping bad option: " + s);
/* 3134 */           exception1.printStackTrace();
/*      */         } 
/*      */       } 
/*      */       
/* 3138 */       KeyBinding.resetKeyBindingArrayAndHash();
/* 3139 */       bufferedreader.close();
/*      */     }
/* 3141 */     catch (Exception exception11) {
/*      */       
/* 3143 */       Config.warn("Failed to load options");
/* 3144 */       exception11.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveOfOptions() {
/*      */     try {
/* 3152 */       PrintWriter printwriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(this.optionsFileOF), StandardCharsets.UTF_8));
/* 3153 */       printwriter.println("ofRenderDistanceChunks:" + this.renderDistanceChunks);
/* 3154 */       printwriter.println("ofFogType:" + this.ofFogType);
/* 3155 */       printwriter.println("ofFogStart:" + this.ofFogStart);
/* 3156 */       printwriter.println("ofMipmapType:" + this.ofMipmapType);
/* 3157 */       printwriter.println("ofOcclusionFancy:" + this.ofOcclusionFancy);
/* 3158 */       printwriter.println("ofSmoothFps:" + this.ofSmoothFps);
/* 3159 */       printwriter.println("ofSmoothWorld:" + this.ofSmoothWorld);
/* 3160 */       printwriter.println("ofAoLevel:" + this.ofAoLevel);
/* 3161 */       printwriter.println("ofClouds:" + this.ofClouds);
/* 3162 */       printwriter.println("ofCloudsHeight:" + this.ofCloudsHeight);
/* 3163 */       printwriter.println("ofTrees:" + this.ofTrees);
/* 3164 */       printwriter.println("ofDroppedItems:" + this.ofDroppedItems);
/* 3165 */       printwriter.println("ofRain:" + this.ofRain);
/* 3166 */       printwriter.println("ofAnimatedWater:" + this.ofAnimatedWater);
/* 3167 */       printwriter.println("ofAnimatedLava:" + this.ofAnimatedLava);
/* 3168 */       printwriter.println("ofAnimatedFire:" + this.ofAnimatedFire);
/* 3169 */       printwriter.println("ofAnimatedPortal:" + this.ofAnimatedPortal);
/* 3170 */       printwriter.println("ofAnimatedRedstone:" + this.ofAnimatedRedstone);
/* 3171 */       printwriter.println("ofAnimatedExplosion:" + this.ofAnimatedExplosion);
/* 3172 */       printwriter.println("ofAnimatedFlame:" + this.ofAnimatedFlame);
/* 3173 */       printwriter.println("ofAnimatedSmoke:" + this.ofAnimatedSmoke);
/* 3174 */       printwriter.println("ofVoidParticles:" + this.ofVoidParticles);
/* 3175 */       printwriter.println("ofWaterParticles:" + this.ofWaterParticles);
/* 3176 */       printwriter.println("ofPortalParticles:" + this.ofPortalParticles);
/* 3177 */       printwriter.println("ofPotionParticles:" + this.ofPotionParticles);
/* 3178 */       printwriter.println("ofFireworkParticles:" + this.ofFireworkParticles);
/* 3179 */       printwriter.println("ofDrippingWaterLava:" + this.ofDrippingWaterLava);
/* 3180 */       printwriter.println("ofAnimatedTerrain:" + this.ofAnimatedTerrain);
/* 3181 */       printwriter.println("ofAnimatedTextures:" + this.ofAnimatedTextures);
/* 3182 */       printwriter.println("ofRainSplash:" + this.ofRainSplash);
/* 3183 */       printwriter.println("ofLagometer:" + this.ofLagometer);
/* 3184 */       printwriter.println("ofShowFps:" + this.ofShowFps);
/* 3185 */       printwriter.println("ofAutoSaveTicks:" + this.ofAutoSaveTicks);
/* 3186 */       printwriter.println("ofBetterGrass:" + this.ofBetterGrass);
/* 3187 */       printwriter.println("ofConnectedTextures:" + this.ofConnectedTextures);
/* 3188 */       printwriter.println("ofWeather:" + this.ofWeather);
/* 3189 */       printwriter.println("ofSky:" + this.ofSky);
/* 3190 */       printwriter.println("ofStars:" + this.ofStars);
/* 3191 */       printwriter.println("ofSunMoon:" + this.ofSunMoon);
/* 3192 */       printwriter.println("ofVignette:" + this.ofVignette);
/* 3193 */       printwriter.println("ofChunkUpdates:" + this.ofChunkUpdates);
/* 3194 */       printwriter.println("ofChunkUpdatesDynamic:" + this.ofChunkUpdatesDynamic);
/* 3195 */       printwriter.println("ofTime:" + this.ofTime);
/* 3196 */       printwriter.println("ofClearWater:" + this.ofClearWater);
/* 3197 */       printwriter.println("ofAaLevel:" + this.ofAaLevel);
/* 3198 */       printwriter.println("ofAfLevel:" + this.ofAfLevel);
/* 3199 */       printwriter.println("ofProfiler:" + this.ofProfiler);
/* 3200 */       printwriter.println("ofBetterSnow:" + this.ofBetterSnow);
/* 3201 */       printwriter.println("ofSwampColors:" + this.ofSwampColors);
/* 3202 */       printwriter.println("ofRandomMobs:" + this.ofRandomMobs);
/* 3203 */       printwriter.println("ofSmoothBiomes:" + this.ofSmoothBiomes);
/* 3204 */       printwriter.println("ofCustomFonts:" + this.ofCustomFonts);
/* 3205 */       printwriter.println("ofCustomColors:" + this.ofCustomColors);
/* 3206 */       printwriter.println("ofCustomItems:" + this.ofCustomItems);
/* 3207 */       printwriter.println("ofCustomSky:" + this.ofCustomSky);
/* 3208 */       printwriter.println("ofShowCapes:" + this.ofShowCapes);
/* 3209 */       printwriter.println("ofNaturalTextures:" + this.ofNaturalTextures);
/* 3210 */       printwriter.println("ofLazyChunkLoading:" + this.ofLazyChunkLoading);
/* 3211 */       printwriter.println("ofDynamicFov:" + this.ofDynamicFov);
/* 3212 */       printwriter.println("ofAlternateBlocks:" + this.ofAlternateBlocks);
/* 3213 */       printwriter.println("ofDynamicLights:" + this.ofDynamicLights);
/* 3214 */       printwriter.println("ofScreenshotSize:" + this.ofScreenshotSize);
/* 3215 */       printwriter.println("ofCustomEntityModels:" + this.ofCustomEntityModels);
/* 3216 */       printwriter.println("ofCustomGuis:" + this.ofCustomGuis);
/* 3217 */       printwriter.println("ofFullscreenMode:" + this.ofFullscreenMode);
/* 3218 */       printwriter.println("ofFastMath:" + this.ofFastMath);
/* 3219 */       printwriter.println("ofFastRender:" + this.ofFastRender);
/* 3220 */       printwriter.println("ofTranslucentBlocks:" + this.ofTranslucentBlocks);
/* 3221 */       printwriter.println("key_" + this.ofKeyBindZoom.getKeyDescription() + ":" + this.ofKeyBindZoom.getKeyCode());
/* 3222 */       printwriter.close();
/*      */     }
/* 3224 */     catch (Exception exception1) {
/*      */       
/* 3226 */       Config.warn("Failed to save options");
/* 3227 */       exception1.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateRenderClouds() {
/* 3233 */     switch (this.ofClouds) {
/*      */       
/*      */       case 1:
/* 3236 */         this.clouds = 1;
/*      */         return;
/*      */       
/*      */       case 2:
/* 3240 */         this.clouds = 2;
/*      */         return;
/*      */       
/*      */       case 3:
/* 3244 */         this.clouds = 0;
/*      */         return;
/*      */     } 
/*      */     
/* 3248 */     if (this.fancyGraphics) {
/*      */       
/* 3250 */       this.clouds = 2;
/*      */     }
/*      */     else {
/*      */       
/* 3254 */       this.clouds = 1;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetSettings() {
/* 3261 */     this.renderDistanceChunks = 8;
/* 3262 */     this.viewBobbing = true;
/* 3263 */     this.anaglyph = false;
/* 3264 */     this.limitFramerate = (int)Options.FRAMERATE_LIMIT.getValueMax();
/* 3265 */     this.enableVsync = false;
/* 3266 */     updateVSync();
/* 3267 */     this.mipmapLevels = 4;
/* 3268 */     this.fancyGraphics = true;
/* 3269 */     this.ambientOcclusion = 2;
/* 3270 */     this.clouds = 2;
/* 3271 */     this.fovSetting = 70.0F;
/* 3272 */     this.gammaSetting = 0.0F;
/* 3273 */     this.guiScale = 0;
/* 3274 */     this.particleSetting = 0;
/* 3275 */     this.heldItemTooltips = true;
/* 3276 */     this.useVbo = false;
/* 3277 */     this.forceUnicodeFont = false;
/* 3278 */     this.ofFogType = 1;
/* 3279 */     this.ofFogStart = 0.8F;
/* 3280 */     this.ofMipmapType = 0;
/* 3281 */     this.ofOcclusionFancy = false;
/* 3282 */     this.ofSmoothFps = false;
/* 3283 */     Config.updateAvailableProcessors();
/* 3284 */     this.ofSmoothWorld = Config.isSingleProcessor();
/* 3285 */     this.ofLazyChunkLoading = Config.isSingleProcessor();
/* 3286 */     this.ofFastMath = false;
/* 3287 */     this.ofFastRender = false;
/* 3288 */     this.ofTranslucentBlocks = 0;
/* 3289 */     this.ofDynamicFov = true;
/* 3290 */     this.ofAlternateBlocks = true;
/* 3291 */     this.ofDynamicLights = 3;
/* 3292 */     this.ofScreenshotSize = 1;
/* 3293 */     this.ofCustomEntityModels = true;
/* 3294 */     this.ofCustomGuis = true;
/* 3295 */     this.ofAoLevel = 1.0F;
/* 3296 */     this.ofAaLevel = 0;
/* 3297 */     this.ofAfLevel = 1;
/* 3298 */     this.ofClouds = 0;
/* 3299 */     this.ofCloudsHeight = 0.0F;
/* 3300 */     this.ofTrees = 0;
/* 3301 */     this.ofRain = 0;
/* 3302 */     this.ofBetterGrass = 3;
/* 3303 */     this.ofAutoSaveTicks = 4000;
/* 3304 */     this.ofLagometer = false;
/* 3305 */     this.ofShowFps = false;
/* 3306 */     this.ofProfiler = false;
/* 3307 */     this.ofWeather = true;
/* 3308 */     this.ofSky = true;
/* 3309 */     this.ofStars = true;
/* 3310 */     this.ofSunMoon = true;
/* 3311 */     this.ofVignette = 0;
/* 3312 */     this.ofChunkUpdates = 1;
/* 3313 */     this.ofChunkUpdatesDynamic = false;
/* 3314 */     this.ofTime = 0;
/* 3315 */     this.ofClearWater = false;
/* 3316 */     this.ofBetterSnow = false;
/* 3317 */     this.ofFullscreenMode = "Default";
/* 3318 */     this.ofSwampColors = true;
/* 3319 */     this.ofRandomMobs = true;
/* 3320 */     this.ofSmoothBiomes = true;
/* 3321 */     this.ofCustomFonts = true;
/* 3322 */     this.ofCustomColors = true;
/* 3323 */     this.ofCustomItems = true;
/* 3324 */     this.ofCustomSky = true;
/* 3325 */     this.ofShowCapes = true;
/* 3326 */     this.ofConnectedTextures = 2;
/* 3327 */     this.ofNaturalTextures = false;
/* 3328 */     this.ofAnimatedWater = 0;
/* 3329 */     this.ofAnimatedLava = 0;
/* 3330 */     this.ofAnimatedFire = true;
/* 3331 */     this.ofAnimatedPortal = true;
/* 3332 */     this.ofAnimatedRedstone = true;
/* 3333 */     this.ofAnimatedExplosion = true;
/* 3334 */     this.ofAnimatedFlame = true;
/* 3335 */     this.ofAnimatedSmoke = true;
/* 3336 */     this.ofVoidParticles = true;
/* 3337 */     this.ofWaterParticles = true;
/* 3338 */     this.ofRainSplash = true;
/* 3339 */     this.ofPortalParticles = true;
/* 3340 */     this.ofPotionParticles = true;
/* 3341 */     this.ofFireworkParticles = true;
/* 3342 */     this.ofDrippingWaterLava = true;
/* 3343 */     this.ofAnimatedTerrain = true;
/* 3344 */     this.ofAnimatedTextures = true;
/* 3345 */     Shaders.setShaderPack(Shaders.packNameNone);
/* 3346 */     Shaders.configAntialiasingLevel = 0;
/* 3347 */     Shaders.uninit();
/* 3348 */     Shaders.storeConfig();
/* 3349 */     updateWaterOpacity();
/* 3350 */     this.mc.refreshResources();
/* 3351 */     saveOptions();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateVSync() {
/* 3356 */     Display.setVSyncEnabled(this.enableVsync);
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateWaterOpacity() {
/* 3361 */     if (this.mc.isIntegratedServerRunning() && this.mc.getIntegratedServer() != null)
/*      */     {
/* 3363 */       Config.waterOpacityChanged = true;
/*      */     }
/*      */     
/* 3366 */     ClearWater.updateWaterOpacity(this, (World)this.mc.world);
/*      */   }
/*      */ 
/*      */   
/*      */   public void setAllAnimations(boolean p_setAllAnimations_1_) {
/* 3371 */     int i = p_setAllAnimations_1_ ? 0 : 2;
/* 3372 */     this.ofAnimatedWater = i;
/* 3373 */     this.ofAnimatedLava = i;
/* 3374 */     this.ofAnimatedFire = p_setAllAnimations_1_;
/* 3375 */     this.ofAnimatedPortal = p_setAllAnimations_1_;
/* 3376 */     this.ofAnimatedRedstone = p_setAllAnimations_1_;
/* 3377 */     this.ofAnimatedExplosion = p_setAllAnimations_1_;
/* 3378 */     this.ofAnimatedFlame = p_setAllAnimations_1_;
/* 3379 */     this.ofAnimatedSmoke = p_setAllAnimations_1_;
/* 3380 */     this.ofVoidParticles = p_setAllAnimations_1_;
/* 3381 */     this.ofWaterParticles = p_setAllAnimations_1_;
/* 3382 */     this.ofRainSplash = p_setAllAnimations_1_;
/* 3383 */     this.ofPortalParticles = p_setAllAnimations_1_;
/* 3384 */     this.ofPotionParticles = p_setAllAnimations_1_;
/* 3385 */     this.ofFireworkParticles = p_setAllAnimations_1_;
/* 3386 */     this.particleSetting = p_setAllAnimations_1_ ? 0 : 2;
/* 3387 */     this.ofDrippingWaterLava = p_setAllAnimations_1_;
/* 3388 */     this.ofAnimatedTerrain = p_setAllAnimations_1_;
/* 3389 */     this.ofAnimatedTextures = p_setAllAnimations_1_;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int nextValue(int p_nextValue_0_, int[] p_nextValue_1_) {
/* 3394 */     int i = indexOf(p_nextValue_0_, p_nextValue_1_);
/*      */     
/* 3396 */     if (i < 0)
/*      */     {
/* 3398 */       return p_nextValue_1_[0];
/*      */     }
/*      */ 
/*      */     
/* 3402 */     i++;
/*      */     
/* 3404 */     if (i >= p_nextValue_1_.length)
/*      */     {
/* 3406 */       i = 0;
/*      */     }
/*      */     
/* 3409 */     return p_nextValue_1_[i];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int limit(int p_limit_0_, int[] p_limit_1_) {
/* 3415 */     int i = indexOf(p_limit_0_, p_limit_1_);
/* 3416 */     return (i < 0) ? p_limit_1_[0] : p_limit_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int indexOf(int p_indexOf_0_, int[] p_indexOf_1_) {
/* 3421 */     for (int i = 0; i < p_indexOf_1_.length; i++) {
/*      */       
/* 3423 */       if (p_indexOf_1_[i] == p_indexOf_0_)
/*      */       {
/* 3425 */         return i;
/*      */       }
/*      */     } 
/*      */     
/* 3429 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setForgeKeybindProperties() {
/* 3434 */     if (Reflector.KeyConflictContext_IN_GAME.exists())
/*      */     {
/* 3436 */       if (Reflector.ForgeKeyBinding_setKeyConflictContext.exists()) {
/*      */         
/* 3438 */         Object object = Reflector.getFieldValue(Reflector.KeyConflictContext_IN_GAME);
/* 3439 */         Reflector.call(this.keyBindForward, Reflector.ForgeKeyBinding_setKeyConflictContext, new Object[] { object });
/* 3440 */         Reflector.call(this.keyBindLeft, Reflector.ForgeKeyBinding_setKeyConflictContext, new Object[] { object });
/* 3441 */         Reflector.call(this.keyBindBack, Reflector.ForgeKeyBinding_setKeyConflictContext, new Object[] { object });
/* 3442 */         Reflector.call(this.keyBindRight, Reflector.ForgeKeyBinding_setKeyConflictContext, new Object[] { object });
/* 3443 */         Reflector.call(this.keyBindJump, Reflector.ForgeKeyBinding_setKeyConflictContext, new Object[] { object });
/* 3444 */         Reflector.call(this.keyBindSneak, Reflector.ForgeKeyBinding_setKeyConflictContext, new Object[] { object });
/* 3445 */         Reflector.call(this.keyBindSprint, Reflector.ForgeKeyBinding_setKeyConflictContext, new Object[] { object });
/* 3446 */         Reflector.call(this.keyBindAttack, Reflector.ForgeKeyBinding_setKeyConflictContext, new Object[] { object });
/* 3447 */         Reflector.call(this.keyBindChat, Reflector.ForgeKeyBinding_setKeyConflictContext, new Object[] { object });
/* 3448 */         Reflector.call(this.keyBindPlayerList, Reflector.ForgeKeyBinding_setKeyConflictContext, new Object[] { object });
/* 3449 */         Reflector.call(this.keyBindCommand, Reflector.ForgeKeyBinding_setKeyConflictContext, new Object[] { object });
/* 3450 */         Reflector.call(this.keyBindTogglePerspective, Reflector.ForgeKeyBinding_setKeyConflictContext, new Object[] { object });
/* 3451 */         Reflector.call(this.keyBindSmoothCamera, Reflector.ForgeKeyBinding_setKeyConflictContext, new Object[] { object });
/* 3452 */         Reflector.call(this.keyBindSwapHands, Reflector.ForgeKeyBinding_setKeyConflictContext, new Object[] { object });
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void onGuiClosed() {
/* 3459 */     if (this.needsResourceRefresh) {
/*      */       
/* 3461 */       this.mc.scheduleResourcesRefresh();
/* 3462 */       this.needsResourceRefresh = false;
/*      */     } 
/*      */   }
/*      */   
/*      */   public enum Options
/*      */   {
/* 3468 */     INVERT_MOUSE("options.invertMouse", false, true),
/* 3469 */     SENSITIVITY("options.sensitivity", true, false),
/* 3470 */     FOV("options.fov", true, false, 30.0F, 110.0F, 1.0F),
/* 3471 */     GAMMA("options.gamma", true, false),
/* 3472 */     SATURATION("options.saturation", true, false),
/* 3473 */     RENDER_DISTANCE("options.renderDistance", true, false, 2.0F, 16.0F, 1.0F),
/* 3474 */     VIEW_BOBBING("options.viewBobbing", false, true),
/* 3475 */     ANAGLYPH("options.anaglyph", false, true),
/* 3476 */     FRAMERATE_LIMIT("options.framerateLimit", true, false, 0.0F, 260.0F, 5.0F),
/* 3477 */     FBO_ENABLE("options.fboEnable", false, true),
/* 3478 */     RENDER_CLOUDS("options.renderClouds", false, false),
/* 3479 */     GRAPHICS("options.graphics", false, false),
/* 3480 */     AMBIENT_OCCLUSION("options.ao", false, false),
/* 3481 */     GUI_SCALE("options.guiScale", false, false),
/* 3482 */     PARTICLES("options.particles", false, false),
/* 3483 */     CHAT_VISIBILITY("options.chat.visibility", false, false),
/* 3484 */     CHAT_COLOR("options.chat.color", false, true),
/* 3485 */     CHAT_LINKS("options.chat.links", false, true),
/* 3486 */     CHAT_OPACITY("options.chat.opacity", true, false),
/* 3487 */     CHAT_LINKS_PROMPT("options.chat.links.prompt", false, true),
/* 3488 */     SNOOPER_ENABLED("options.snooper", false, true),
/* 3489 */     USE_FULLSCREEN("options.fullscreen", false, true),
/* 3490 */     ENABLE_VSYNC("options.vsync", false, true),
/* 3491 */     USE_VBO("options.vbo", false, true),
/* 3492 */     TOUCHSCREEN("options.touchscreen", false, true),
/* 3493 */     CHAT_SCALE("options.chat.scale", true, false),
/* 3494 */     CHAT_WIDTH("options.chat.width", true, false),
/* 3495 */     CHAT_HEIGHT_FOCUSED("options.chat.height.focused", true, false),
/* 3496 */     CHAT_HEIGHT_UNFOCUSED("options.chat.height.unfocused", true, false),
/* 3497 */     MIPMAP_LEVELS("options.mipmapLevels", true, false, 0.0F, 4.0F, 1.0F),
/* 3498 */     FORCE_UNICODE_FONT("options.forceUnicodeFont", false, true),
/* 3499 */     REDUCED_DEBUG_INFO("options.reducedDebugInfo", false, true),
/* 3500 */     ENTITY_SHADOWS("options.entityShadows", false, true),
/* 3501 */     MAIN_HAND("options.mainHand", false, false),
/* 3502 */     ATTACK_INDICATOR("options.attackIndicator", false, false),
/* 3503 */     ENABLE_WEAK_ATTACKS("options.enableWeakAttacks", false, true),
/* 3504 */     SHOW_SUBTITLES("options.showSubtitles", false, true),
/* 3505 */     REALMS_NOTIFICATIONS("options.realmsNotifications", false, true),
/* 3506 */     AUTO_JUMP("options.autoJump", false, true),
/* 3507 */     NARRATOR("options.narrator", false, false),
/* 3508 */     FOG_FANCY("of.options.FOG_FANCY", false, false),
/* 3509 */     FOG_START("of.options.FOG_START", false, false),
/* 3510 */     MIPMAP_TYPE("of.options.MIPMAP_TYPE", true, false, 0.0F, 3.0F, 1.0F),
/* 3511 */     SMOOTH_FPS("of.options.SMOOTH_FPS", false, false),
/* 3512 */     CLOUDS("of.options.CLOUDS", false, false),
/* 3513 */     CLOUD_HEIGHT("of.options.CLOUD_HEIGHT", true, false),
/* 3514 */     TREES("of.options.TREES", false, false),
/* 3515 */     RAIN("of.options.RAIN", false, false),
/* 3516 */     ANIMATED_WATER("of.options.ANIMATED_WATER", false, false),
/* 3517 */     ANIMATED_LAVA("of.options.ANIMATED_LAVA", false, false),
/* 3518 */     ANIMATED_FIRE("of.options.ANIMATED_FIRE", false, false),
/* 3519 */     ANIMATED_PORTAL("of.options.ANIMATED_PORTAL", false, false),
/* 3520 */     AO_LEVEL("of.options.AO_LEVEL", true, false),
/* 3521 */     LAGOMETER("of.options.LAGOMETER", false, false),
/* 3522 */     SHOW_FPS("of.options.SHOW_FPS", false, false),
/* 3523 */     AUTOSAVE_TICKS("of.options.AUTOSAVE_TICKS", false, false),
/* 3524 */     BETTER_GRASS("of.options.BETTER_GRASS", false, false),
/* 3525 */     ANIMATED_REDSTONE("of.options.ANIMATED_REDSTONE", false, false),
/* 3526 */     ANIMATED_EXPLOSION("of.options.ANIMATED_EXPLOSION", false, false),
/* 3527 */     ANIMATED_FLAME("of.options.ANIMATED_FLAME", false, false),
/* 3528 */     ANIMATED_SMOKE("of.options.ANIMATED_SMOKE", false, false),
/* 3529 */     WEATHER("of.options.WEATHER", false, false),
/* 3530 */     SKY("of.options.SKY", false, false),
/* 3531 */     STARS("of.options.STARS", false, false),
/* 3532 */     SUN_MOON("of.options.SUN_MOON", false, false),
/* 3533 */     VIGNETTE("of.options.VIGNETTE", false, false),
/* 3534 */     CHUNK_UPDATES("of.options.CHUNK_UPDATES", false, false),
/* 3535 */     CHUNK_UPDATES_DYNAMIC("of.options.CHUNK_UPDATES_DYNAMIC", false, false),
/* 3536 */     TIME("of.options.TIME", false, false),
/* 3537 */     CLEAR_WATER("of.options.CLEAR_WATER", false, false),
/* 3538 */     SMOOTH_WORLD("of.options.SMOOTH_WORLD", false, false),
/* 3539 */     VOID_PARTICLES("of.options.VOID_PARTICLES", false, false),
/* 3540 */     WATER_PARTICLES("of.options.WATER_PARTICLES", false, false),
/* 3541 */     RAIN_SPLASH("of.options.RAIN_SPLASH", false, false),
/* 3542 */     PORTAL_PARTICLES("of.options.PORTAL_PARTICLES", false, false),
/* 3543 */     POTION_PARTICLES("of.options.POTION_PARTICLES", false, false),
/* 3544 */     FIREWORK_PARTICLES("of.options.FIREWORK_PARTICLES", false, false),
/* 3545 */     PROFILER("of.options.PROFILER", false, false),
/* 3546 */     DRIPPING_WATER_LAVA("of.options.DRIPPING_WATER_LAVA", false, false),
/* 3547 */     BETTER_SNOW("of.options.BETTER_SNOW", false, false),
/* 3548 */     FULLSCREEN_MODE("of.options.FULLSCREEN_MODE", true, false, 0.0F, (Config.getDisplayModes()).length, 1.0F),
/* 3549 */     ANIMATED_TERRAIN("of.options.ANIMATED_TERRAIN", false, false),
/* 3550 */     SWAMP_COLORS("of.options.SWAMP_COLORS", false, false),
/* 3551 */     RANDOM_MOBS("of.options.RANDOM_MOBS", false, false),
/* 3552 */     SMOOTH_BIOMES("of.options.SMOOTH_BIOMES", false, false),
/* 3553 */     CUSTOM_FONTS("of.options.CUSTOM_FONTS", false, false),
/* 3554 */     CUSTOM_COLORS("of.options.CUSTOM_COLORS", false, false),
/* 3555 */     SHOW_CAPES("of.options.SHOW_CAPES", false, false),
/* 3556 */     CONNECTED_TEXTURES("of.options.CONNECTED_TEXTURES", false, false),
/* 3557 */     CUSTOM_ITEMS("of.options.CUSTOM_ITEMS", false, false),
/* 3558 */     AA_LEVEL("of.options.AA_LEVEL", true, false, 0.0F, 16.0F, 1.0F),
/* 3559 */     AF_LEVEL("of.options.AF_LEVEL", true, false, 1.0F, 16.0F, 1.0F),
/* 3560 */     ANIMATED_TEXTURES("of.options.ANIMATED_TEXTURES", false, false),
/* 3561 */     NATURAL_TEXTURES("of.options.NATURAL_TEXTURES", false, false),
/* 3562 */     HELD_ITEM_TOOLTIPS("of.options.HELD_ITEM_TOOLTIPS", false, false),
/* 3563 */     DROPPED_ITEMS("of.options.DROPPED_ITEMS", false, false),
/* 3564 */     LAZY_CHUNK_LOADING("of.options.LAZY_CHUNK_LOADING", false, false),
/* 3565 */     CUSTOM_SKY("of.options.CUSTOM_SKY", false, false),
/* 3566 */     FAST_MATH("of.options.FAST_MATH", false, false),
/* 3567 */     FAST_RENDER("of.options.FAST_RENDER", false, false),
/* 3568 */     TRANSLUCENT_BLOCKS("of.options.TRANSLUCENT_BLOCKS", false, false),
/* 3569 */     DYNAMIC_FOV("of.options.DYNAMIC_FOV", false, false),
/* 3570 */     DYNAMIC_LIGHTS("of.options.DYNAMIC_LIGHTS", false, false),
/* 3571 */     ALTERNATE_BLOCKS("of.options.ALTERNATE_BLOCKS", false, false),
/* 3572 */     CUSTOM_ENTITY_MODELS("of.options.CUSTOM_ENTITY_MODELS", false, false),
/* 3573 */     ADVANCED_TOOLTIPS("of.options.ADVANCED_TOOLTIPS", false, false),
/* 3574 */     SCREENSHOT_SIZE("of.options.SCREENSHOT_SIZE", false, false),
/* 3575 */     CUSTOM_GUIS("of.options.CUSTOM_GUIS", false, false); private final boolean enumFloat;
/*      */     private final boolean enumBoolean;
/*      */     private final String enumString;
/*      */     private final float valueStep;
/*      */     private float valueMin;
/*      */     private float valueMax;
/*      */     
/*      */     public static Options getEnumOptions(int ordinal) {
/*      */       byte b;
/*      */       int i;
/*      */       Options[] arrayOfOptions;
/* 3586 */       for (i = (arrayOfOptions = values()).length, b = 0; b < i; ) { Options gamesettings$options = arrayOfOptions[b];
/*      */         
/* 3588 */         if (gamesettings$options.returnEnumOrdinal() == ordinal)
/*      */         {
/* 3590 */           return gamesettings$options;
/*      */         }
/*      */         b++; }
/*      */       
/* 3594 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     Options(String str, boolean isFloat, boolean isBoolean, float valMin, float valMax, float valStep) {
/* 3604 */       this.enumString = str;
/* 3605 */       this.enumFloat = isFloat;
/* 3606 */       this.enumBoolean = isBoolean;
/* 3607 */       this.valueMin = valMin;
/* 3608 */       this.valueMax = valMax;
/* 3609 */       this.valueStep = valStep;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getEnumFloat() {
/* 3614 */       return this.enumFloat;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean getEnumBoolean() {
/* 3619 */       return this.enumBoolean;
/*      */     }
/*      */ 
/*      */     
/*      */     public int returnEnumOrdinal() {
/* 3624 */       return ordinal();
/*      */     }
/*      */ 
/*      */     
/*      */     public String getEnumString() {
/* 3629 */       return this.enumString;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getValueMin() {
/* 3634 */       return this.valueMin;
/*      */     }
/*      */ 
/*      */     
/*      */     public float getValueMax() {
/* 3639 */       return this.valueMax;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setValueMax(float value) {
/* 3644 */       this.valueMax = value;
/*      */     }
/*      */ 
/*      */     
/*      */     public float normalizeValue(float value) {
/* 3649 */       return MathHelper.clamp((snapToStepClamp(value) - this.valueMin) / (this.valueMax - this.valueMin), 0.0F, 1.0F);
/*      */     }
/*      */ 
/*      */     
/*      */     public float denormalizeValue(float value) {
/* 3654 */       return snapToStepClamp(this.valueMin + (this.valueMax - this.valueMin) * MathHelper.clamp(value, 0.0F, 1.0F));
/*      */     }
/*      */ 
/*      */     
/*      */     public float snapToStepClamp(float value) {
/* 3659 */       value = snapToStep(value);
/* 3660 */       return MathHelper.clamp(value, this.valueMin, this.valueMax);
/*      */     }
/*      */ 
/*      */     
/*      */     private float snapToStep(float value) {
/* 3665 */       if (this.valueStep > 0.0F)
/*      */       {
/* 3667 */         value = this.valueStep * Math.round(value / this.valueStep);
/*      */       }
/*      */       
/* 3670 */       return value;
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\settings\GameSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */