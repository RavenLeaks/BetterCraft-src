/*      */ package optifine;
/*      */ 
/*      */ import java.awt.Dimension;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.lang.reflect.Array;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.client.LoadingScreenRenderer;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.RenderGlobal;
/*      */ import net.minecraft.client.renderer.block.model.ModelManager;
/*      */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.resources.DefaultResourcePack;
/*      */ import net.minecraft.client.resources.IResource;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.ResourcePackRepository;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.server.integrated.IntegratedServer;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.world.DimensionType;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProvider;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.LWJGLException;
/*      */ import org.lwjgl.Sys;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.DisplayMode;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL30;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.opengl.PixelFormat;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ import shadersmod.client.Shaders;
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Config
/*      */ {
/*      */   public static final String OF_NAME = "OptiFine";
/*      */   public static final String MC_VERSION = "1.12.2";
/*      */   public static final String OF_EDITION = "HD_U";
/*      */   public static final String OF_RELEASE = "C6";
/*      */   public static final String VERSION = "OptiFine_1.12.2_HD_U_C6";
/*   71 */   private static String newRelease = null;
/*      */   private static boolean notify64BitJava = false;
/*   73 */   public static String openGlVersion = null;
/*   74 */   public static String openGlRenderer = null;
/*   75 */   public static String openGlVendor = null;
/*   76 */   public static String[] openGlExtensions = null;
/*   77 */   public static GlVersion glVersion = null;
/*   78 */   public static GlVersion glslVersion = null;
/*   79 */   public static int minecraftVersionInt = -1;
/*      */   public static boolean fancyFogAvailable = false;
/*      */   public static boolean occlusionAvailable = false;
/*   82 */   private static GameSettings gameSettings = null;
/*   83 */   private static Minecraft minecraft = Minecraft.getMinecraft();
/*      */   private static boolean initialized = false;
/*   85 */   private static Thread minecraftThread = null;
/*   86 */   private static DisplayMode desktopDisplayMode = null;
/*   87 */   private static DisplayMode[] displayModes = null;
/*   88 */   private static int antialiasingLevel = 0;
/*   89 */   private static int availableProcessors = 0;
/*      */   public static boolean zoomMode = false;
/*   91 */   private static int texturePackClouds = 0;
/*      */   public static boolean waterOpacityChanged = false;
/*      */   private static boolean fullscreenModeChecked = false;
/*      */   private static boolean desktopModeChecked = false;
/*   95 */   private static DefaultResourcePack defaultResourcePackLazy = null;
/*   96 */   public static final Float DEF_ALPHA_FUNC_LEVEL = Float.valueOf(0.1F);
/*   97 */   private static final Logger LOGGER = LogManager.getLogger();
/*      */ 
/*      */   
/*      */   public static String getVersion() {
/*  101 */     return "OptiFine_1.12.2_HD_U_C6";
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getVersionDebug() {
/*  106 */     StringBuffer stringbuffer = new StringBuffer(32);
/*      */     
/*  108 */     if (isDynamicLights()) {
/*      */       
/*  110 */       stringbuffer.append("DL: ");
/*  111 */       stringbuffer.append(String.valueOf(DynamicLights.getCount()));
/*  112 */       stringbuffer.append(", ");
/*      */     } 
/*      */     
/*  115 */     stringbuffer.append("OptiFine_1.12.2_HD_U_C6");
/*  116 */     String s = Shaders.getShaderPackName();
/*      */     
/*  118 */     if (s != null) {
/*      */       
/*  120 */       stringbuffer.append(", ");
/*  121 */       stringbuffer.append(s);
/*      */     } 
/*      */     
/*  124 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void initGameSettings(GameSettings p_initGameSettings_0_) {
/*  129 */     if (gameSettings == null) {
/*      */       
/*  131 */       gameSettings = p_initGameSettings_0_;
/*  132 */       desktopDisplayMode = Display.getDesktopDisplayMode();
/*  133 */       updateAvailableProcessors();
/*  134 */       ReflectorForge.putLaunchBlackboard("optifine.ForgeSplashCompatible", Boolean.TRUE);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void initDisplay() {
/*  140 */     checkInitialized();
/*  141 */     antialiasingLevel = gameSettings.ofAaLevel;
/*  142 */     checkDisplaySettings();
/*  143 */     checkDisplayMode();
/*  144 */     minecraftThread = Thread.currentThread();
/*  145 */     updateThreadPriorities();
/*  146 */     Shaders.startup(Minecraft.getMinecraft());
/*      */   }
/*      */ 
/*      */   
/*      */   public static void checkInitialized() {
/*  151 */     if (!initialized)
/*      */     {
/*  153 */       if (Display.isCreated()) {
/*      */         
/*  155 */         initialized = true;
/*  156 */         checkOpenGlCaps();
/*  157 */         startVersionCheckThread();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkOpenGlCaps() {
/*  164 */     log("");
/*  165 */     log(getVersion());
/*  166 */     log("Build: " + getBuild());
/*  167 */     log("OS: " + System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
/*  168 */     log("Java: " + System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
/*  169 */     log("VM: " + System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
/*  170 */     log("LWJGL: " + Sys.getVersion());
/*  171 */     openGlVersion = GL11.glGetString(7938);
/*  172 */     openGlRenderer = GL11.glGetString(7937);
/*  173 */     openGlVendor = GL11.glGetString(7936);
/*  174 */     log("OpenGL: " + openGlRenderer + ", version " + openGlVersion + ", " + openGlVendor);
/*  175 */     log("OpenGL Version: " + getOpenGlVersionString());
/*      */     
/*  177 */     if (!(GLContext.getCapabilities()).OpenGL12)
/*      */     {
/*  179 */       log("OpenGL Mipmap levels: Not available (GL12.GL_TEXTURE_MAX_LEVEL)");
/*      */     }
/*      */     
/*  182 */     fancyFogAvailable = (GLContext.getCapabilities()).GL_NV_fog_distance;
/*      */     
/*  184 */     if (!fancyFogAvailable)
/*      */     {
/*  186 */       log("OpenGL Fancy fog: Not available (GL_NV_fog_distance)");
/*      */     }
/*      */     
/*  189 */     occlusionAvailable = (GLContext.getCapabilities()).GL_ARB_occlusion_query;
/*      */     
/*  191 */     if (!occlusionAvailable)
/*      */     {
/*  193 */       log("OpenGL Occlussion culling: Not available (GL_ARB_occlusion_query)");
/*      */     }
/*      */     
/*  196 */     int i = TextureUtils.getGLMaximumTextureSize();
/*  197 */     dbg("Maximum texture size: " + i + "x" + i);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getBuild() {
/*      */     try {
/*  204 */       InputStream inputstream = Config.class.getResourceAsStream("/buildof.txt");
/*      */       
/*  206 */       if (inputstream == null)
/*      */       {
/*  208 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  212 */       String s = readLines(inputstream)[0];
/*  213 */       return s;
/*      */     
/*      */     }
/*  216 */     catch (Exception exception) {
/*      */       
/*  218 */       warn(exception.getClass().getName() + ": " + exception.getMessage());
/*  219 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFancyFogAvailable() {
/*  225 */     return fancyFogAvailable;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isOcclusionAvailable() {
/*  230 */     return occlusionAvailable;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getMinecraftVersionInt() {
/*  235 */     if (minecraftVersionInt < 0) {
/*      */       
/*  237 */       String[] astring = tokenize("1.12.2", ".");
/*  238 */       int i = 0;
/*      */       
/*  240 */       if (astring.length > 0)
/*      */       {
/*  242 */         i += 10000 * parseInt(astring[0], 0);
/*      */       }
/*      */       
/*  245 */       if (astring.length > 1)
/*      */       {
/*  247 */         i += 100 * parseInt(astring[1], 0);
/*      */       }
/*      */       
/*  250 */       if (astring.length > 2)
/*      */       {
/*  252 */         i += 1 * parseInt(astring[2], 0);
/*      */       }
/*      */       
/*  255 */       minecraftVersionInt = i;
/*      */     } 
/*      */     
/*  258 */     return minecraftVersionInt;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getOpenGlVersionString() {
/*  263 */     GlVersion glversion = getGlVersion();
/*  264 */     String s = glversion.getMajor() + "." + glversion.getMinor() + "." + glversion.getRelease();
/*  265 */     return s;
/*      */   }
/*      */ 
/*      */   
/*      */   private static GlVersion getGlVersionLwjgl() {
/*  270 */     if ((GLContext.getCapabilities()).OpenGL44)
/*      */     {
/*  272 */       return new GlVersion(4, 4);
/*      */     }
/*  274 */     if ((GLContext.getCapabilities()).OpenGL43)
/*      */     {
/*  276 */       return new GlVersion(4, 3);
/*      */     }
/*  278 */     if ((GLContext.getCapabilities()).OpenGL42)
/*      */     {
/*  280 */       return new GlVersion(4, 2);
/*      */     }
/*  282 */     if ((GLContext.getCapabilities()).OpenGL41)
/*      */     {
/*  284 */       return new GlVersion(4, 1);
/*      */     }
/*  286 */     if ((GLContext.getCapabilities()).OpenGL40)
/*      */     {
/*  288 */       return new GlVersion(4, 0);
/*      */     }
/*  290 */     if ((GLContext.getCapabilities()).OpenGL33)
/*      */     {
/*  292 */       return new GlVersion(3, 3);
/*      */     }
/*  294 */     if ((GLContext.getCapabilities()).OpenGL32)
/*      */     {
/*  296 */       return new GlVersion(3, 2);
/*      */     }
/*  298 */     if ((GLContext.getCapabilities()).OpenGL31)
/*      */     {
/*  300 */       return new GlVersion(3, 1);
/*      */     }
/*  302 */     if ((GLContext.getCapabilities()).OpenGL30)
/*      */     {
/*  304 */       return new GlVersion(3, 0);
/*      */     }
/*  306 */     if ((GLContext.getCapabilities()).OpenGL21)
/*      */     {
/*  308 */       return new GlVersion(2, 1);
/*      */     }
/*  310 */     if ((GLContext.getCapabilities()).OpenGL20)
/*      */     {
/*  312 */       return new GlVersion(2, 0);
/*      */     }
/*  314 */     if ((GLContext.getCapabilities()).OpenGL15)
/*      */     {
/*  316 */       return new GlVersion(1, 5);
/*      */     }
/*  318 */     if ((GLContext.getCapabilities()).OpenGL14)
/*      */     {
/*  320 */       return new GlVersion(1, 4);
/*      */     }
/*  322 */     if ((GLContext.getCapabilities()).OpenGL13)
/*      */     {
/*  324 */       return new GlVersion(1, 3);
/*      */     }
/*  326 */     if ((GLContext.getCapabilities()).OpenGL12)
/*      */     {
/*  328 */       return new GlVersion(1, 2);
/*      */     }
/*      */ 
/*      */     
/*  332 */     return (GLContext.getCapabilities()).OpenGL11 ? new GlVersion(1, 1) : new GlVersion(1, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static GlVersion getGlVersion() {
/*  338 */     if (glVersion == null) {
/*      */       
/*  340 */       String s = GL11.glGetString(7938);
/*  341 */       glVersion = parseGlVersion(s, null);
/*      */       
/*  343 */       if (glVersion == null)
/*      */       {
/*  345 */         glVersion = getGlVersionLwjgl();
/*      */       }
/*      */       
/*  348 */       if (glVersion == null)
/*      */       {
/*  350 */         glVersion = new GlVersion(1, 0);
/*      */       }
/*      */     } 
/*      */     
/*  354 */     return glVersion;
/*      */   }
/*      */ 
/*      */   
/*      */   public static GlVersion getGlslVersion() {
/*  359 */     if (glslVersion == null) {
/*      */       
/*  361 */       String s = GL11.glGetString(35724);
/*  362 */       glslVersion = parseGlVersion(s, null);
/*      */       
/*  364 */       if (glslVersion == null)
/*      */       {
/*  366 */         glslVersion = new GlVersion(1, 10);
/*      */       }
/*      */     } 
/*      */     
/*  370 */     return glslVersion;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static GlVersion parseGlVersion(String p_parseGlVersion_0_, GlVersion p_parseGlVersion_1_) {
/*      */     try {
/*  377 */       if (p_parseGlVersion_0_ == null)
/*      */       {
/*  379 */         return p_parseGlVersion_1_;
/*      */       }
/*      */ 
/*      */       
/*  383 */       Pattern pattern = Pattern.compile("([0-9]+)\\.([0-9]+)(\\.([0-9]+))?(.+)?");
/*  384 */       Matcher matcher = pattern.matcher(p_parseGlVersion_0_);
/*      */       
/*  386 */       if (!matcher.matches())
/*      */       {
/*  388 */         return p_parseGlVersion_1_;
/*      */       }
/*      */ 
/*      */       
/*  392 */       int i = Integer.parseInt(matcher.group(1));
/*  393 */       int j = Integer.parseInt(matcher.group(2));
/*  394 */       int k = (matcher.group(4) != null) ? Integer.parseInt(matcher.group(4)) : 0;
/*  395 */       String s = matcher.group(5);
/*  396 */       return new GlVersion(i, j, k, s);
/*      */ 
/*      */     
/*      */     }
/*  400 */     catch (Exception exception) {
/*      */       
/*  402 */       exception.printStackTrace();
/*  403 */       return p_parseGlVersion_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String[] getOpenGlExtensions() {
/*  409 */     if (openGlExtensions == null)
/*      */     {
/*  411 */       openGlExtensions = detectOpenGlExtensions();
/*      */     }
/*      */     
/*  414 */     return openGlExtensions;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] detectOpenGlExtensions() {
/*      */     try {
/*  421 */       GlVersion glversion = getGlVersion();
/*      */       
/*  423 */       if (glversion.getMajor() >= 3) {
/*      */         
/*  425 */         int i = GL11.glGetInteger(33309);
/*      */         
/*  427 */         if (i > 0)
/*      */         {
/*  429 */           String[] astring = new String[i];
/*      */           
/*  431 */           for (int j = 0; j < i; j++)
/*      */           {
/*  433 */             astring[j] = GL30.glGetStringi(7939, j);
/*      */           }
/*      */           
/*  436 */           return astring;
/*      */         }
/*      */       
/*      */       } 
/*  440 */     } catch (Exception exception1) {
/*      */       
/*  442 */       exception1.printStackTrace();
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  447 */       String s = GL11.glGetString(7939);
/*  448 */       String[] astring1 = s.split(" ");
/*  449 */       return astring1;
/*      */     }
/*  451 */     catch (Exception exception) {
/*      */       
/*  453 */       exception.printStackTrace();
/*  454 */       return new String[0];
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateThreadPriorities() {
/*  460 */     updateAvailableProcessors();
/*  461 */     int i = 8;
/*      */     
/*  463 */     if (isSingleProcessor()) {
/*      */       
/*  465 */       if (isSmoothWorld())
/*      */       {
/*  467 */         minecraftThread.setPriority(10);
/*  468 */         setThreadPriority("Server thread", 1);
/*      */       }
/*      */       else
/*      */       {
/*  472 */         minecraftThread.setPriority(5);
/*  473 */         setThreadPriority("Server thread", 5);
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  478 */       minecraftThread.setPriority(10);
/*  479 */       setThreadPriority("Server thread", 5);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void setThreadPriority(String p_setThreadPriority_0_, int p_setThreadPriority_1_) {
/*      */     try {
/*  487 */       ThreadGroup threadgroup = Thread.currentThread().getThreadGroup();
/*      */       
/*  489 */       if (threadgroup == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  494 */       int i = (threadgroup.activeCount() + 10) * 2;
/*  495 */       Thread[] athread = new Thread[i];
/*  496 */       threadgroup.enumerate(athread, false);
/*      */       
/*  498 */       for (int j = 0; j < athread.length; j++)
/*      */       {
/*  500 */         Thread thread = athread[j];
/*      */         
/*  502 */         if (thread != null && thread.getName().startsWith(p_setThreadPriority_0_))
/*      */         {
/*  504 */           thread.setPriority(p_setThreadPriority_1_);
/*      */         }
/*      */       }
/*      */     
/*  508 */     } catch (Throwable throwable) {
/*      */       
/*  510 */       warn(String.valueOf(throwable.getClass().getName()) + ": " + throwable.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isMinecraftThread() {
/*  516 */     return (Thread.currentThread() == minecraftThread);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void startVersionCheckThread() {
/*  521 */     VersionCheckThread versioncheckthread = new VersionCheckThread();
/*  522 */     versioncheckthread.start();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isMipmaps() {
/*  527 */     return (gameSettings.mipmapLevels > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getMipmapLevels() {
/*  532 */     return gameSettings.mipmapLevels;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getMipmapType() {
/*  537 */     switch (gameSettings.ofMipmapType) {
/*      */       
/*      */       case 0:
/*  540 */         return 9986;
/*      */       
/*      */       case 1:
/*  543 */         return 9986;
/*      */       
/*      */       case 2:
/*  546 */         if (isMultiTexture())
/*      */         {
/*  548 */           return 9985;
/*      */         }
/*      */         
/*  551 */         return 9986;
/*      */       
/*      */       case 3:
/*  554 */         if (isMultiTexture())
/*      */         {
/*  556 */           return 9987;
/*      */         }
/*      */         
/*  559 */         return 9986;
/*      */     } 
/*      */     
/*  562 */     return 9986;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isUseAlphaFunc() {
/*  568 */     float f = getAlphaFuncLevel();
/*  569 */     return (f > DEF_ALPHA_FUNC_LEVEL.floatValue() + 1.0E-5F);
/*      */   }
/*      */ 
/*      */   
/*      */   public static float getAlphaFuncLevel() {
/*  574 */     return DEF_ALPHA_FUNC_LEVEL.floatValue();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFogFancy() {
/*  579 */     if (!isFancyFogAvailable())
/*      */     {
/*  581 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  585 */     return (gameSettings.ofFogType == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isFogFast() {
/*  591 */     return (gameSettings.ofFogType == 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFogOff() {
/*  596 */     return (gameSettings.ofFogType == 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static float getFogStart() {
/*  601 */     return gameSettings.ofFogStart;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void dbg(String p_dbg_0_) {
/*  606 */     LOGGER.info("[OptiFine] " + p_dbg_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void warn(String p_warn_0_) {
/*  611 */     LOGGER.warn("[OptiFine] " + p_warn_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void error(String p_error_0_) {
/*  616 */     LOGGER.error("[OptiFine] " + p_error_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void log(String p_log_0_) {
/*  621 */     dbg(p_log_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getUpdatesPerFrame() {
/*  626 */     return gameSettings.ofChunkUpdates;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDynamicUpdates() {
/*  631 */     return gameSettings.ofChunkUpdatesDynamic;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRainFancy() {
/*  636 */     if (gameSettings.ofRain == 0)
/*      */     {
/*  638 */       return gameSettings.fancyGraphics;
/*      */     }
/*      */ 
/*      */     
/*  642 */     return (gameSettings.ofRain == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRainOff() {
/*  648 */     return (gameSettings.ofRain == 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCloudsFancy() {
/*  653 */     if (gameSettings.ofClouds != 0)
/*      */     {
/*  655 */       return (gameSettings.ofClouds == 2);
/*      */     }
/*  657 */     if (isShaders() && !Shaders.shaderPackClouds.isDefault())
/*      */     {
/*  659 */       return Shaders.shaderPackClouds.isFancy();
/*      */     }
/*  661 */     if (texturePackClouds != 0)
/*      */     {
/*  663 */       return (texturePackClouds == 2);
/*      */     }
/*      */ 
/*      */     
/*  667 */     return gameSettings.fancyGraphics;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isCloudsOff() {
/*  673 */     if (gameSettings.ofClouds != 0)
/*      */     {
/*  675 */       return (gameSettings.ofClouds == 3);
/*      */     }
/*  677 */     if (isShaders() && !Shaders.shaderPackClouds.isDefault())
/*      */     {
/*  679 */       return Shaders.shaderPackClouds.isOff();
/*      */     }
/*  681 */     if (texturePackClouds != 0)
/*      */     {
/*  683 */       return (texturePackClouds == 3);
/*      */     }
/*      */ 
/*      */     
/*  687 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void updateTexturePackClouds() {
/*  693 */     texturePackClouds = 0;
/*  694 */     IResourceManager iresourcemanager = getResourceManager();
/*      */     
/*  696 */     if (iresourcemanager != null) {
/*      */       
/*      */       try {
/*      */         
/*  700 */         InputStream inputstream = iresourcemanager.getResource(new ResourceLocation("mcpatcher/color.properties")).getInputStream();
/*      */         
/*  702 */         if (inputstream == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/*  707 */         Properties properties = new Properties();
/*  708 */         properties.load(inputstream);
/*  709 */         inputstream.close();
/*  710 */         String s = properties.getProperty("clouds");
/*      */         
/*  712 */         if (s == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/*  717 */         dbg("Texture pack clouds: " + s);
/*  718 */         s = s.toLowerCase();
/*      */         
/*  720 */         if (s.equals("fast"))
/*      */         {
/*  722 */           texturePackClouds = 1;
/*      */         }
/*      */         
/*  725 */         if (s.equals("fancy"))
/*      */         {
/*  727 */           texturePackClouds = 2;
/*      */         }
/*      */         
/*  730 */         if (s.equals("off"))
/*      */         {
/*  732 */           texturePackClouds = 3;
/*      */         }
/*      */       }
/*  735 */       catch (Exception exception) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static ModelManager getModelManager() {
/*  744 */     return (minecraft.getRenderItem()).modelManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTreesFancy() {
/*  749 */     if (gameSettings.ofTrees == 0)
/*      */     {
/*  751 */       return gameSettings.fancyGraphics;
/*      */     }
/*      */ 
/*      */     
/*  755 */     return (gameSettings.ofTrees != 1);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isTreesSmart() {
/*  761 */     return (gameSettings.ofTrees == 4);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCullFacesLeaves() {
/*  766 */     if (gameSettings.ofTrees == 0)
/*      */     {
/*  768 */       return !gameSettings.fancyGraphics;
/*      */     }
/*      */ 
/*      */     
/*  772 */     return (gameSettings.ofTrees == 4);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDroppedItemsFancy() {
/*  778 */     if (gameSettings.ofDroppedItems == 0)
/*      */     {
/*  780 */       return gameSettings.fancyGraphics;
/*      */     }
/*      */ 
/*      */     
/*  784 */     return (gameSettings.ofDroppedItems == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int limit(int p_limit_0_, int p_limit_1_, int p_limit_2_) {
/*  790 */     if (p_limit_0_ < p_limit_1_)
/*      */     {
/*  792 */       return p_limit_1_;
/*      */     }
/*      */ 
/*      */     
/*  796 */     return (p_limit_0_ > p_limit_2_) ? p_limit_2_ : p_limit_0_;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static float limit(float p_limit_0_, float p_limit_1_, float p_limit_2_) {
/*  802 */     if (p_limit_0_ < p_limit_1_)
/*      */     {
/*  804 */       return p_limit_1_;
/*      */     }
/*      */ 
/*      */     
/*  808 */     return (p_limit_0_ > p_limit_2_) ? p_limit_2_ : p_limit_0_;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static double limit(double p_limit_0_, double p_limit_2_, double p_limit_4_) {
/*  814 */     if (p_limit_0_ < p_limit_2_)
/*      */     {
/*  816 */       return p_limit_2_;
/*      */     }
/*      */ 
/*      */     
/*  820 */     return (p_limit_0_ > p_limit_4_) ? p_limit_4_ : p_limit_0_;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static float limitTo1(float p_limitTo1_0_) {
/*  826 */     if (p_limitTo1_0_ < 0.0F)
/*      */     {
/*  828 */       return 0.0F;
/*      */     }
/*      */ 
/*      */     
/*  832 */     return (p_limitTo1_0_ > 1.0F) ? 1.0F : p_limitTo1_0_;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedWater() {
/*  838 */     return (gameSettings.ofAnimatedWater != 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isGeneratedWater() {
/*  843 */     return (gameSettings.ofAnimatedWater == 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedPortal() {
/*  848 */     return gameSettings.ofAnimatedPortal;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedLava() {
/*  853 */     return (gameSettings.ofAnimatedLava != 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isGeneratedLava() {
/*  858 */     return (gameSettings.ofAnimatedLava == 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedFire() {
/*  863 */     return gameSettings.ofAnimatedFire;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedRedstone() {
/*  868 */     return gameSettings.ofAnimatedRedstone;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedExplosion() {
/*  873 */     return gameSettings.ofAnimatedExplosion;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedFlame() {
/*  878 */     return gameSettings.ofAnimatedFlame;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedSmoke() {
/*  883 */     return gameSettings.ofAnimatedSmoke;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isVoidParticles() {
/*  888 */     return gameSettings.ofVoidParticles;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isWaterParticles() {
/*  893 */     return gameSettings.ofWaterParticles;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRainSplash() {
/*  898 */     return gameSettings.ofRainSplash;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isPortalParticles() {
/*  903 */     return gameSettings.ofPortalParticles;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isPotionParticles() {
/*  908 */     return gameSettings.ofPotionParticles;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFireworkParticles() {
/*  913 */     return gameSettings.ofFireworkParticles;
/*      */   }
/*      */ 
/*      */   
/*      */   public static float getAmbientOcclusionLevel() {
/*  918 */     return (isShaders() && Shaders.aoLevel >= 0.0F) ? Shaders.aoLevel : gameSettings.ofAoLevel;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String arrayToString(Object[] p_arrayToString_0_) {
/*  923 */     if (p_arrayToString_0_ == null)
/*      */     {
/*  925 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  929 */     StringBuffer stringbuffer = new StringBuffer(p_arrayToString_0_.length * 5);
/*      */     
/*  931 */     for (int i = 0; i < p_arrayToString_0_.length; i++) {
/*      */       
/*  933 */       Object object = p_arrayToString_0_[i];
/*      */       
/*  935 */       if (i > 0)
/*      */       {
/*  937 */         stringbuffer.append(", ");
/*      */       }
/*      */       
/*  940 */       stringbuffer.append(String.valueOf(object));
/*      */     } 
/*      */     
/*  943 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String arrayToString(int[] p_arrayToString_0_) {
/*  949 */     if (p_arrayToString_0_ == null)
/*      */     {
/*  951 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  955 */     StringBuffer stringbuffer = new StringBuffer(p_arrayToString_0_.length * 5);
/*      */     
/*  957 */     for (int i = 0; i < p_arrayToString_0_.length; i++) {
/*      */       
/*  959 */       int j = p_arrayToString_0_[i];
/*      */       
/*  961 */       if (i > 0)
/*      */       {
/*  963 */         stringbuffer.append(", ");
/*      */       }
/*      */       
/*  966 */       stringbuffer.append(String.valueOf(j));
/*      */     } 
/*      */     
/*  969 */     return stringbuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Minecraft getMinecraft() {
/*  975 */     return minecraft;
/*      */   }
/*      */ 
/*      */   
/*      */   public static TextureManager getTextureManager() {
/*  980 */     return minecraft.getTextureManager();
/*      */   }
/*      */ 
/*      */   
/*      */   public static IResourceManager getResourceManager() {
/*  985 */     return minecraft.getResourceManager();
/*      */   }
/*      */ 
/*      */   
/*      */   public static InputStream getResourceStream(ResourceLocation p_getResourceStream_0_) throws IOException {
/*  990 */     return getResourceStream(minecraft.getResourceManager(), p_getResourceStream_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static InputStream getResourceStream(IResourceManager p_getResourceStream_0_, ResourceLocation p_getResourceStream_1_) throws IOException {
/*  995 */     IResource iresource = p_getResourceStream_0_.getResource(p_getResourceStream_1_);
/*  996 */     return (iresource == null) ? null : iresource.getInputStream();
/*      */   }
/*      */ 
/*      */   
/*      */   public static IResource getResource(ResourceLocation p_getResource_0_) throws IOException {
/* 1001 */     return minecraft.getResourceManager().getResource(p_getResource_0_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean hasResource(ResourceLocation p_hasResource_0_) {
/* 1006 */     IResourcePack iresourcepack = getDefiningResourcePack(p_hasResource_0_);
/* 1007 */     return (iresourcepack != null);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean hasResource(IResourceManager p_hasResource_0_, ResourceLocation p_hasResource_1_) {
/*      */     try {
/* 1014 */       IResource iresource = p_hasResource_0_.getResource(p_hasResource_1_);
/* 1015 */       return (iresource != null);
/*      */     }
/* 1017 */     catch (IOException var3) {
/*      */       
/* 1019 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static IResourcePack[] getResourcePacks() {
/* 1025 */     ResourcePackRepository resourcepackrepository = minecraft.getResourcePackRepository();
/* 1026 */     List list = resourcepackrepository.getRepositoryEntries();
/* 1027 */     List<IResourcePack> list1 = new ArrayList();
/*      */     
/* 1029 */     for (Object resourcepackrepository$entry : list)
/*      */     {
/* 1031 */       list1.add(((ResourcePackRepository.Entry)resourcepackrepository$entry).getResourcePack());
/*      */     }
/*      */     
/* 1034 */     if (resourcepackrepository.getResourcePackInstance() != null)
/*      */     {
/* 1036 */       list1.add(resourcepackrepository.getResourcePackInstance());
/*      */     }
/*      */     
/* 1039 */     IResourcePack[] airesourcepack = list1.<IResourcePack>toArray(new IResourcePack[list1.size()]);
/* 1040 */     return airesourcepack;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getResourcePackNames() {
/* 1045 */     if (minecraft.getResourcePackRepository() == null)
/*      */     {
/* 1047 */       return "";
/*      */     }
/*      */ 
/*      */     
/* 1051 */     IResourcePack[] airesourcepack = getResourcePacks();
/*      */     
/* 1053 */     if (airesourcepack.length <= 0)
/*      */     {
/* 1055 */       return getDefaultResourcePack().getPackName();
/*      */     }
/*      */ 
/*      */     
/* 1059 */     String[] astring = new String[airesourcepack.length];
/*      */     
/* 1061 */     for (int i = 0; i < airesourcepack.length; i++)
/*      */     {
/* 1063 */       astring[i] = airesourcepack[i].getPackName();
/*      */     }
/*      */     
/* 1066 */     String s = arrayToString((Object[])astring);
/* 1067 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DefaultResourcePack getDefaultResourcePack() {
/* 1074 */     if (defaultResourcePackLazy == null) {
/*      */       
/* 1076 */       Minecraft minecraft = Minecraft.getMinecraft();
/* 1077 */       defaultResourcePackLazy = (DefaultResourcePack)Reflector.getFieldValue(minecraft, Reflector.Minecraft_defaultResourcePack);
/*      */       
/* 1079 */       if (defaultResourcePackLazy == null) {
/*      */         
/* 1081 */         ResourcePackRepository resourcepackrepository = minecraft.getResourcePackRepository();
/*      */         
/* 1083 */         if (resourcepackrepository != null)
/*      */         {
/* 1085 */           defaultResourcePackLazy = (DefaultResourcePack)resourcepackrepository.rprDefaultResourcePack;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1090 */     return defaultResourcePackLazy;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFromDefaultResourcePack(ResourceLocation p_isFromDefaultResourcePack_0_) {
/* 1095 */     IResourcePack iresourcepack = getDefiningResourcePack(p_isFromDefaultResourcePack_0_);
/* 1096 */     return (iresourcepack == getDefaultResourcePack());
/*      */   }
/*      */ 
/*      */   
/*      */   public static IResourcePack getDefiningResourcePack(ResourceLocation p_getDefiningResourcePack_0_) {
/* 1101 */     ResourcePackRepository resourcepackrepository = minecraft.getResourcePackRepository();
/* 1102 */     IResourcePack iresourcepack = resourcepackrepository.getResourcePackInstance();
/*      */     
/* 1104 */     if (iresourcepack != null && iresourcepack.resourceExists(p_getDefiningResourcePack_0_))
/*      */     {
/* 1106 */       return iresourcepack;
/*      */     }
/*      */ 
/*      */     
/* 1110 */     List<ResourcePackRepository.Entry> list = resourcepackrepository.repositoryEntries;
/*      */     
/* 1112 */     for (int i = list.size() - 1; i >= 0; i--) {
/*      */       
/* 1114 */       ResourcePackRepository.Entry resourcepackrepository$entry = list.get(i);
/* 1115 */       IResourcePack iresourcepack1 = resourcepackrepository$entry.getResourcePack();
/*      */       
/* 1117 */       if (iresourcepack1.resourceExists(p_getDefiningResourcePack_0_))
/*      */       {
/* 1119 */         return iresourcepack1;
/*      */       }
/*      */     } 
/*      */     
/* 1123 */     if (getDefaultResourcePack().resourceExists(p_getDefiningResourcePack_0_))
/*      */     {
/* 1125 */       return (IResourcePack)getDefaultResourcePack();
/*      */     }
/*      */ 
/*      */     
/* 1129 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static RenderGlobal getRenderGlobal() {
/* 1136 */     return minecraft.renderGlobal;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isBetterGrass() {
/* 1141 */     return (gameSettings.ofBetterGrass != 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isBetterGrassFancy() {
/* 1146 */     return (gameSettings.ofBetterGrass == 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isWeatherEnabled() {
/* 1151 */     return gameSettings.ofWeather;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSkyEnabled() {
/* 1156 */     return gameSettings.ofSky;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSunMoonEnabled() {
/* 1161 */     return gameSettings.ofSunMoon;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSunTexture() {
/* 1166 */     if (!isSunMoonEnabled())
/*      */     {
/* 1168 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1172 */     return !(isShaders() && !Shaders.isSun());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isMoonTexture() {
/* 1178 */     if (!isSunMoonEnabled())
/*      */     {
/* 1180 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1184 */     return !(isShaders() && !Shaders.isMoon());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isVignetteEnabled() {
/* 1190 */     if (isShaders() && !Shaders.isVignette())
/*      */     {
/* 1192 */       return false;
/*      */     }
/* 1194 */     if (gameSettings.ofVignette == 0)
/*      */     {
/* 1196 */       return gameSettings.fancyGraphics;
/*      */     }
/*      */ 
/*      */     
/* 1200 */     return (gameSettings.ofVignette == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isStarsEnabled() {
/* 1206 */     return gameSettings.ofStars;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void sleep(long p_sleep_0_) {
/*      */     try {
/* 1213 */       Thread.sleep(p_sleep_0_);
/*      */     }
/* 1215 */     catch (InterruptedException interruptedexception) {
/*      */       
/* 1217 */       interruptedexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTimeDayOnly() {
/* 1223 */     return (gameSettings.ofTime == 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTimeDefault() {
/* 1228 */     return (gameSettings.ofTime == 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTimeNightOnly() {
/* 1233 */     return (gameSettings.ofTime == 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isClearWater() {
/* 1238 */     return gameSettings.ofClearWater;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getAnisotropicFilterLevel() {
/* 1243 */     return gameSettings.ofAfLevel;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnisotropicFiltering() {
/* 1248 */     return (getAnisotropicFilterLevel() > 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getAntialiasingLevel() {
/* 1253 */     return antialiasingLevel;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAntialiasing() {
/* 1258 */     return (getAntialiasingLevel() > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAntialiasingConfigured() {
/* 1263 */     return ((getGameSettings()).ofAaLevel > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isMultiTexture() {
/* 1268 */     if (getAnisotropicFilterLevel() > 1)
/*      */     {
/* 1270 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1274 */     return (getAntialiasingLevel() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean between(int p_between_0_, int p_between_1_, int p_between_2_) {
/* 1280 */     return (p_between_0_ >= p_between_1_ && p_between_0_ <= p_between_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDrippingWaterLava() {
/* 1285 */     return gameSettings.ofDrippingWaterLava;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isBetterSnow() {
/* 1290 */     return gameSettings.ofBetterSnow;
/*      */   }
/*      */ 
/*      */   
/*      */   public static Dimension getFullscreenDimension() {
/* 1295 */     if (desktopDisplayMode == null)
/*      */     {
/* 1297 */       return null;
/*      */     }
/* 1299 */     if (gameSettings == null)
/*      */     {
/* 1301 */       return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
/*      */     }
/*      */ 
/*      */     
/* 1305 */     String s = gameSettings.ofFullscreenMode;
/*      */     
/* 1307 */     if (s.equals("Default"))
/*      */     {
/* 1309 */       return new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight());
/*      */     }
/*      */ 
/*      */     
/* 1313 */     String[] astring = tokenize(s, " x");
/* 1314 */     return (astring.length < 2) ? new Dimension(desktopDisplayMode.getWidth(), desktopDisplayMode.getHeight()) : new Dimension(parseInt(astring[0], -1), parseInt(astring[1], -1));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int parseInt(String p_parseInt_0_, int p_parseInt_1_) {
/*      */     try {
/* 1323 */       if (p_parseInt_0_ == null)
/*      */       {
/* 1325 */         return p_parseInt_1_;
/*      */       }
/*      */ 
/*      */       
/* 1329 */       p_parseInt_0_ = p_parseInt_0_.trim();
/* 1330 */       return Integer.parseInt(p_parseInt_0_);
/*      */     
/*      */     }
/* 1333 */     catch (NumberFormatException var3) {
/*      */       
/* 1335 */       return p_parseInt_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static float parseFloat(String p_parseFloat_0_, float p_parseFloat_1_) {
/*      */     try {
/* 1343 */       if (p_parseFloat_0_ == null)
/*      */       {
/* 1345 */         return p_parseFloat_1_;
/*      */       }
/*      */ 
/*      */       
/* 1349 */       p_parseFloat_0_ = p_parseFloat_0_.trim();
/* 1350 */       return Float.parseFloat(p_parseFloat_0_);
/*      */     
/*      */     }
/* 1353 */     catch (NumberFormatException var3) {
/*      */       
/* 1355 */       return p_parseFloat_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean parseBoolean(String p_parseBoolean_0_, boolean p_parseBoolean_1_) {
/*      */     try {
/* 1363 */       if (p_parseBoolean_0_ == null)
/*      */       {
/* 1365 */         return p_parseBoolean_1_;
/*      */       }
/*      */ 
/*      */       
/* 1369 */       p_parseBoolean_0_ = p_parseBoolean_0_.trim();
/* 1370 */       return Boolean.parseBoolean(p_parseBoolean_0_);
/*      */     
/*      */     }
/* 1373 */     catch (NumberFormatException var3) {
/*      */       
/* 1375 */       return p_parseBoolean_1_;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String[] tokenize(String p_tokenize_0_, String p_tokenize_1_) {
/* 1381 */     StringTokenizer stringtokenizer = new StringTokenizer(p_tokenize_0_, p_tokenize_1_);
/* 1382 */     List<String> list = new ArrayList();
/*      */     
/* 1384 */     while (stringtokenizer.hasMoreTokens()) {
/*      */       
/* 1386 */       String s = stringtokenizer.nextToken();
/* 1387 */       list.add(s);
/*      */     } 
/*      */     
/* 1390 */     String[] astring = list.<String>toArray(new String[list.size()]);
/* 1391 */     return astring;
/*      */   }
/*      */ 
/*      */   
/*      */   public static DisplayMode getDesktopDisplayMode() {
/* 1396 */     return desktopDisplayMode;
/*      */   }
/*      */ 
/*      */   
/*      */   public static DisplayMode[] getDisplayModes() {
/* 1401 */     if (displayModes == null) {
/*      */       
/*      */       try {
/*      */         
/* 1405 */         DisplayMode[] adisplaymode = Display.getAvailableDisplayModes();
/* 1406 */         Set<Dimension> set = getDisplayModeDimensions(adisplaymode);
/* 1407 */         List<DisplayMode> list = new ArrayList();
/*      */         
/* 1409 */         for (Dimension dimension : set) {
/*      */           
/* 1411 */           DisplayMode[] adisplaymode1 = getDisplayModes(adisplaymode, dimension);
/* 1412 */           DisplayMode displaymode = getDisplayMode(adisplaymode1, desktopDisplayMode);
/*      */           
/* 1414 */           if (displaymode != null)
/*      */           {
/* 1416 */             list.add(displaymode);
/*      */           }
/*      */         } 
/*      */         
/* 1420 */         DisplayMode[] adisplaymode2 = list.<DisplayMode>toArray(new DisplayMode[list.size()]);
/* 1421 */         Arrays.sort(adisplaymode2, new DisplayModeComparator());
/* 1422 */         return adisplaymode2;
/*      */       }
/* 1424 */       catch (Exception exception) {
/*      */         
/* 1426 */         exception.printStackTrace();
/* 1427 */         displayModes = new DisplayMode[] { desktopDisplayMode };
/*      */       } 
/*      */     }
/*      */     
/* 1431 */     return displayModes;
/*      */   }
/*      */ 
/*      */   
/*      */   public static DisplayMode getLargestDisplayMode() {
/* 1436 */     DisplayMode[] adisplaymode = getDisplayModes();
/*      */     
/* 1438 */     if (adisplaymode != null && adisplaymode.length >= 1) {
/*      */       
/* 1440 */       DisplayMode displaymode = adisplaymode[adisplaymode.length - 1];
/*      */       
/* 1442 */       if (desktopDisplayMode.getWidth() > displaymode.getWidth())
/*      */       {
/* 1444 */         return desktopDisplayMode;
/*      */       }
/*      */ 
/*      */       
/* 1448 */       return (desktopDisplayMode.getWidth() == displaymode.getWidth() && desktopDisplayMode.getHeight() > displaymode.getHeight()) ? desktopDisplayMode : displaymode;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1453 */     return desktopDisplayMode;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static Set<Dimension> getDisplayModeDimensions(DisplayMode[] p_getDisplayModeDimensions_0_) {
/* 1459 */     Set<Dimension> set = new HashSet<>();
/*      */     
/* 1461 */     for (int i = 0; i < p_getDisplayModeDimensions_0_.length; i++) {
/*      */       
/* 1463 */       DisplayMode displaymode = p_getDisplayModeDimensions_0_[i];
/* 1464 */       Dimension dimension = new Dimension(displaymode.getWidth(), displaymode.getHeight());
/* 1465 */       set.add(dimension);
/*      */     } 
/*      */     
/* 1468 */     return set;
/*      */   }
/*      */ 
/*      */   
/*      */   private static DisplayMode[] getDisplayModes(DisplayMode[] p_getDisplayModes_0_, Dimension p_getDisplayModes_1_) {
/* 1473 */     List<DisplayMode> list = new ArrayList();
/*      */     
/* 1475 */     for (int i = 0; i < p_getDisplayModes_0_.length; i++) {
/*      */       
/* 1477 */       DisplayMode displaymode = p_getDisplayModes_0_[i];
/*      */       
/* 1479 */       if (displaymode.getWidth() == p_getDisplayModes_1_.getWidth() && displaymode.getHeight() == p_getDisplayModes_1_.getHeight())
/*      */       {
/* 1481 */         list.add(displaymode);
/*      */       }
/*      */     } 
/*      */     
/* 1485 */     DisplayMode[] adisplaymode = list.<DisplayMode>toArray(new DisplayMode[list.size()]);
/* 1486 */     return adisplaymode;
/*      */   }
/*      */ 
/*      */   
/*      */   private static DisplayMode getDisplayMode(DisplayMode[] p_getDisplayMode_0_, DisplayMode p_getDisplayMode_1_) {
/* 1491 */     if (p_getDisplayMode_1_ != null)
/*      */     {
/* 1493 */       for (int i = 0; i < p_getDisplayMode_0_.length; i++) {
/*      */         
/* 1495 */         DisplayMode displaymode = p_getDisplayMode_0_[i];
/*      */         
/* 1497 */         if (displaymode.getBitsPerPixel() == p_getDisplayMode_1_.getBitsPerPixel() && displaymode.getFrequency() == p_getDisplayMode_1_.getFrequency())
/*      */         {
/* 1499 */           return displaymode;
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/* 1504 */     if (p_getDisplayMode_0_.length <= 0)
/*      */     {
/* 1506 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1510 */     Arrays.sort(p_getDisplayMode_0_, new DisplayModeComparator());
/* 1511 */     return p_getDisplayMode_0_[p_getDisplayMode_0_.length - 1];
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String[] getDisplayModeNames() {
/* 1517 */     DisplayMode[] adisplaymode = getDisplayModes();
/* 1518 */     String[] astring = new String[adisplaymode.length];
/*      */     
/* 1520 */     for (int i = 0; i < adisplaymode.length; i++) {
/*      */       
/* 1522 */       DisplayMode displaymode = adisplaymode[i];
/* 1523 */       String s = displaymode.getWidth() + "x" + displaymode.getHeight();
/* 1524 */       astring[i] = s;
/*      */     } 
/*      */     
/* 1527 */     return astring;
/*      */   }
/*      */ 
/*      */   
/*      */   public static DisplayMode getDisplayMode(Dimension p_getDisplayMode_0_) throws LWJGLException {
/* 1532 */     DisplayMode[] adisplaymode = getDisplayModes();
/*      */     
/* 1534 */     for (int i = 0; i < adisplaymode.length; i++) {
/*      */       
/* 1536 */       DisplayMode displaymode = adisplaymode[i];
/*      */       
/* 1538 */       if (displaymode.getWidth() == p_getDisplayMode_0_.width && displaymode.getHeight() == p_getDisplayMode_0_.height)
/*      */       {
/* 1540 */         return displaymode;
/*      */       }
/*      */     } 
/*      */     
/* 1544 */     return desktopDisplayMode;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedTerrain() {
/* 1549 */     return gameSettings.ofAnimatedTerrain;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAnimatedTextures() {
/* 1554 */     return gameSettings.ofAnimatedTextures;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSwampColors() {
/* 1559 */     return gameSettings.ofSwampColors;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRandomMobs() {
/* 1564 */     return gameSettings.ofRandomMobs;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void checkGlError(String p_checkGlError_0_) {
/* 1569 */     int i = GL11.glGetError();
/*      */     
/* 1571 */     if (i != 0) {
/*      */       
/* 1573 */       String s = GLU.gluErrorString(i);
/* 1574 */       error("OpenGlError: " + i + " (" + s + "), at: " + p_checkGlError_0_);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSmoothBiomes() {
/* 1580 */     return gameSettings.ofSmoothBiomes;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCustomColors() {
/* 1585 */     return gameSettings.ofCustomColors;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCustomSky() {
/* 1590 */     return gameSettings.ofCustomSky;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCustomFonts() {
/* 1595 */     return gameSettings.ofCustomFonts;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isShowCapes() {
/* 1600 */     return gameSettings.ofShowCapes;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isConnectedTextures() {
/* 1605 */     return (gameSettings.ofConnectedTextures != 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isNaturalTextures() {
/* 1610 */     return gameSettings.ofNaturalTextures;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isConnectedTexturesFancy() {
/* 1615 */     return (gameSettings.ofConnectedTextures == 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isFastRender() {
/* 1620 */     return gameSettings.ofFastRender;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isTranslucentBlocksFancy() {
/* 1625 */     if (gameSettings.ofTranslucentBlocks == 0)
/*      */     {
/* 1627 */       return gameSettings.fancyGraphics;
/*      */     }
/*      */ 
/*      */     
/* 1631 */     return (gameSettings.ofTranslucentBlocks == 2);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isShaders() {
/* 1637 */     return Shaders.shaderPackLoaded;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String[] readLines(File p_readLines_0_) throws IOException {
/* 1642 */     FileInputStream fileinputstream = new FileInputStream(p_readLines_0_);
/* 1643 */     return readLines(fileinputstream);
/*      */   }
/*      */ 
/*      */   
/*      */   public static String[] readLines(InputStream p_readLines_0_) throws IOException {
/* 1648 */     List<String> list = new ArrayList();
/* 1649 */     InputStreamReader inputstreamreader = new InputStreamReader(p_readLines_0_, "ASCII");
/* 1650 */     BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/*      */ 
/*      */     
/*      */     while (true) {
/* 1654 */       String s = bufferedreader.readLine();
/*      */       
/* 1656 */       if (s == null) {
/*      */         
/* 1658 */         String[] astring = (String[])list.toArray((Object[])new String[list.size()]);
/* 1659 */         return astring;
/*      */       } 
/*      */       
/* 1662 */       list.add(s);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String readFile(File p_readFile_0_) throws IOException {
/* 1668 */     FileInputStream fileinputstream = new FileInputStream(p_readFile_0_);
/* 1669 */     return readInputStream(fileinputstream, "ASCII");
/*      */   }
/*      */ 
/*      */   
/*      */   public static String readInputStream(InputStream p_readInputStream_0_) throws IOException {
/* 1674 */     return readInputStream(p_readInputStream_0_, "ASCII");
/*      */   }
/*      */ 
/*      */   
/*      */   public static String readInputStream(InputStream p_readInputStream_0_, String p_readInputStream_1_) throws IOException {
/* 1679 */     InputStreamReader inputstreamreader = new InputStreamReader(p_readInputStream_0_, p_readInputStream_1_);
/* 1680 */     BufferedReader bufferedreader = new BufferedReader(inputstreamreader);
/* 1681 */     StringBuffer stringbuffer = new StringBuffer();
/*      */ 
/*      */     
/*      */     while (true) {
/* 1685 */       String s = bufferedreader.readLine();
/*      */       
/* 1687 */       if (s == null)
/*      */       {
/* 1689 */         return stringbuffer.toString();
/*      */       }
/*      */       
/* 1692 */       stringbuffer.append(s);
/* 1693 */       stringbuffer.append("\n");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static byte[] readAll(InputStream p_readAll_0_) throws IOException {
/* 1699 */     ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream();
/* 1700 */     byte[] abyte = new byte[1024];
/*      */ 
/*      */     
/*      */     while (true) {
/* 1704 */       int i = p_readAll_0_.read(abyte);
/*      */       
/* 1706 */       if (i < 0) {
/*      */         
/* 1708 */         p_readAll_0_.close();
/* 1709 */         byte[] abyte1 = bytearrayoutputstream.toByteArray();
/* 1710 */         return abyte1;
/*      */       } 
/*      */       
/* 1713 */       bytearrayoutputstream.write(abyte, 0, i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static GameSettings getGameSettings() {
/* 1719 */     return gameSettings;
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getNewRelease() {
/* 1724 */     return newRelease;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setNewRelease(String p_setNewRelease_0_) {
/* 1729 */     newRelease = p_setNewRelease_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int compareRelease(String p_compareRelease_0_, String p_compareRelease_1_) {
/* 1734 */     String[] astring = splitRelease(p_compareRelease_0_);
/* 1735 */     String[] astring1 = splitRelease(p_compareRelease_1_);
/* 1736 */     String s = astring[0];
/* 1737 */     String s1 = astring1[0];
/*      */     
/* 1739 */     if (!s.equals(s1))
/*      */     {
/* 1741 */       return s.compareTo(s1);
/*      */     }
/*      */ 
/*      */     
/* 1745 */     int i = parseInt(astring[1], -1);
/* 1746 */     int j = parseInt(astring1[1], -1);
/*      */     
/* 1748 */     if (i != j)
/*      */     {
/* 1750 */       return i - j;
/*      */     }
/*      */ 
/*      */     
/* 1754 */     String s2 = astring[2];
/* 1755 */     String s3 = astring1[2];
/*      */     
/* 1757 */     if (!s2.equals(s3)) {
/*      */       
/* 1759 */       if (s2.isEmpty())
/*      */       {
/* 1761 */         return 1;
/*      */       }
/*      */       
/* 1764 */       if (s3.isEmpty())
/*      */       {
/* 1766 */         return -1;
/*      */       }
/*      */     } 
/*      */     
/* 1770 */     return s2.compareTo(s3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String[] splitRelease(String p_splitRelease_0_) {
/* 1777 */     if (p_splitRelease_0_ != null && p_splitRelease_0_.length() > 0) {
/*      */       
/* 1779 */       Pattern pattern = Pattern.compile("([A-Z])([0-9]+)(.*)");
/* 1780 */       Matcher matcher = pattern.matcher(p_splitRelease_0_);
/*      */       
/* 1782 */       if (!matcher.matches())
/*      */       {
/* 1784 */         return new String[] { "", "", "" };
/*      */       }
/*      */ 
/*      */       
/* 1788 */       String s = normalize(matcher.group(1));
/* 1789 */       String s1 = normalize(matcher.group(2));
/* 1790 */       String s2 = normalize(matcher.group(3));
/* 1791 */       return new String[] { s, s1, s2 };
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1796 */     return new String[] { "", "", "" };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int intHash(int p_intHash_0_) {
/* 1802 */     p_intHash_0_ = p_intHash_0_ ^ 0x3D ^ p_intHash_0_ >> 16;
/* 1803 */     p_intHash_0_ += p_intHash_0_ << 3;
/* 1804 */     p_intHash_0_ ^= p_intHash_0_ >> 4;
/* 1805 */     p_intHash_0_ *= 668265261;
/* 1806 */     p_intHash_0_ ^= p_intHash_0_ >> 15;
/* 1807 */     return p_intHash_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getRandom(BlockPos p_getRandom_0_, int p_getRandom_1_) {
/* 1812 */     int i = intHash(p_getRandom_1_ + 37);
/* 1813 */     i = intHash(i + p_getRandom_0_.getX());
/* 1814 */     i = intHash(i + p_getRandom_0_.getZ());
/* 1815 */     i = intHash(i + p_getRandom_0_.getY());
/* 1816 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public static WorldServer getWorldServer() {
/* 1821 */     WorldClient worldClient = minecraft.world;
/*      */     
/* 1823 */     if (worldClient == null)
/*      */     {
/* 1825 */       return null;
/*      */     }
/* 1827 */     if (!minecraft.isIntegratedServerRunning())
/*      */     {
/* 1829 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1833 */     IntegratedServer integratedserver = minecraft.getIntegratedServer();
/*      */     
/* 1835 */     if (integratedserver == null)
/*      */     {
/* 1837 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1841 */     WorldProvider worldprovider = ((World)worldClient).provider;
/*      */     
/* 1843 */     if (worldprovider == null)
/*      */     {
/* 1845 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 1849 */     DimensionType dimensiontype = worldprovider.getDimensionType();
/*      */ 
/*      */     
/*      */     try {
/* 1853 */       WorldServer worldserver = integratedserver.worldServerForDimension(dimensiontype.getId());
/* 1854 */       return worldserver;
/*      */     }
/* 1856 */     catch (NullPointerException var5) {
/*      */       
/* 1858 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getAvailableProcessors() {
/* 1867 */     return availableProcessors;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateAvailableProcessors() {
/* 1872 */     availableProcessors = Runtime.getRuntime().availableProcessors();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSingleProcessor() {
/* 1877 */     return (getAvailableProcessors() <= 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSmoothWorld() {
/* 1882 */     return gameSettings.ofSmoothWorld;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isLazyChunkLoading() {
/* 1887 */     return !isSingleProcessor() ? false : gameSettings.ofLazyChunkLoading;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDynamicFov() {
/* 1892 */     return gameSettings.ofDynamicFov;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isAlternateBlocks() {
/* 1897 */     return gameSettings.ofAlternateBlocks;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getChunkViewDistance() {
/* 1902 */     if (gameSettings == null)
/*      */     {
/* 1904 */       return 10;
/*      */     }
/*      */ 
/*      */     
/* 1908 */     int i = gameSettings.renderDistanceChunks;
/* 1909 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equals(Object p_equals_0_, Object p_equals_1_) {
/* 1915 */     if (p_equals_0_ == p_equals_1_)
/*      */     {
/* 1917 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 1921 */     return (p_equals_0_ == null) ? false : p_equals_0_.equals(p_equals_1_);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equalsOne(Object p_equalsOne_0_, Object[] p_equalsOne_1_) {
/* 1927 */     if (p_equalsOne_1_ == null)
/*      */     {
/* 1929 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1933 */     for (int i = 0; i < p_equalsOne_1_.length; i++) {
/*      */       
/* 1935 */       Object object = p_equalsOne_1_[i];
/*      */       
/* 1937 */       if (equals(p_equalsOne_0_, object))
/*      */       {
/* 1939 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1943 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean equalsOne(int p_equalsOne_0_, int[] p_equalsOne_1_) {
/* 1949 */     for (int i = 0; i < p_equalsOne_1_.length; i++) {
/*      */       
/* 1951 */       if (p_equalsOne_1_[i] == p_equalsOne_0_)
/*      */       {
/* 1953 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1957 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSameOne(Object p_isSameOne_0_, Object[] p_isSameOne_1_) {
/* 1962 */     if (p_isSameOne_1_ == null)
/*      */     {
/* 1964 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1968 */     for (int i = 0; i < p_isSameOne_1_.length; i++) {
/*      */       
/* 1970 */       Object object = p_isSameOne_1_[i];
/*      */       
/* 1972 */       if (p_isSameOne_0_ == object)
/*      */       {
/* 1974 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 1978 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String normalize(String p_normalize_0_) {
/* 1984 */     return (p_normalize_0_ == null) ? "" : p_normalize_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void checkDisplaySettings() {
/* 1989 */     int i = getAntialiasingLevel();
/*      */     
/* 1991 */     if (i > 0) {
/*      */       
/* 1993 */       DisplayMode displaymode = Display.getDisplayMode();
/* 1994 */       dbg("FSAA Samples: " + i);
/*      */ 
/*      */       
/*      */       try {
/* 1998 */         Display.destroy();
/* 1999 */         Display.setDisplayMode(displaymode);
/* 2000 */         Display.create((new PixelFormat()).withDepthBits(24).withSamples(i));
/* 2001 */         Display.setResizable(false);
/* 2002 */         Display.setResizable(true);
/*      */       }
/* 2004 */       catch (LWJGLException lwjglexception2) {
/*      */         
/* 2006 */         warn("Error setting FSAA: " + i + "x");
/* 2007 */         lwjglexception2.printStackTrace();
/*      */ 
/*      */         
/*      */         try {
/* 2011 */           Display.setDisplayMode(displaymode);
/* 2012 */           Display.create((new PixelFormat()).withDepthBits(24));
/* 2013 */           Display.setResizable(false);
/* 2014 */           Display.setResizable(true);
/*      */         }
/* 2016 */         catch (LWJGLException lwjglexception1) {
/*      */           
/* 2018 */           lwjglexception1.printStackTrace();
/*      */ 
/*      */           
/*      */           try {
/* 2022 */             Display.setDisplayMode(displaymode);
/* 2023 */             Display.create();
/* 2024 */             Display.setResizable(false);
/* 2025 */             Display.setResizable(true);
/*      */           }
/* 2027 */           catch (LWJGLException lwjglexception) {
/*      */             
/* 2029 */             lwjglexception.printStackTrace();
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2034 */       if (!Minecraft.IS_RUNNING_ON_MAC && getDefaultResourcePack() != null) {
/*      */         
/* 2036 */         InputStream inputstream = null;
/* 2037 */         InputStream inputstream1 = null;
/*      */ 
/*      */         
/*      */         try {
/* 2041 */           inputstream = getDefaultResourcePack().getInputStreamAssets(new ResourceLocation("icons/icon_16x16.png"));
/* 2042 */           inputstream1 = getDefaultResourcePack().getInputStreamAssets(new ResourceLocation("icons/icon_32x32.png"));
/*      */           
/* 2044 */           if (inputstream != null && inputstream1 != null)
/*      */           {
/* 2046 */             Display.setIcon(new ByteBuffer[] { readIconImage(inputstream), readIconImage(inputstream1) });
/*      */           }
/*      */         }
/* 2049 */         catch (IOException ioexception) {
/*      */           
/* 2051 */           warn("Error setting window icon: " + ioexception.getClass().getName() + ": " + ioexception.getMessage());
/*      */         }
/*      */         finally {
/*      */           
/* 2055 */           IOUtils.closeQuietly(inputstream);
/* 2056 */           IOUtils.closeQuietly(inputstream1);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static ByteBuffer readIconImage(InputStream p_readIconImage_0_) throws IOException {
/* 2064 */     BufferedImage bufferedimage = ImageIO.read(p_readIconImage_0_);
/* 2065 */     int[] aint = bufferedimage.getRGB(0, 0, bufferedimage.getWidth(), bufferedimage.getHeight(), null, 0, bufferedimage.getWidth());
/* 2066 */     ByteBuffer bytebuffer = ByteBuffer.allocate(4 * aint.length); byte b;
/*      */     int i, arrayOfInt1[];
/* 2068 */     for (i = (arrayOfInt1 = aint).length, b = 0; b < i; ) { int j = arrayOfInt1[b];
/*      */       
/* 2070 */       bytebuffer.putInt(j << 8 | j >> 24 & 0xFF);
/*      */       b++; }
/*      */     
/* 2073 */     bytebuffer.flip();
/* 2074 */     return bytebuffer;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkDisplayMode() {
/*      */     try {
/* 2081 */       if (minecraft.isFullScreen())
/*      */       {
/* 2083 */         if (fullscreenModeChecked) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2088 */         fullscreenModeChecked = true;
/* 2089 */         desktopModeChecked = false;
/* 2090 */         DisplayMode displaymode = Display.getDisplayMode();
/* 2091 */         Dimension dimension = getFullscreenDimension();
/*      */         
/* 2093 */         if (dimension == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2098 */         if (displaymode.getWidth() == dimension.width && displaymode.getHeight() == dimension.height) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2103 */         DisplayMode displaymode1 = getDisplayMode(dimension);
/*      */         
/* 2105 */         if (displaymode1 == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2110 */         Display.setDisplayMode(displaymode1);
/* 2111 */         minecraft.displayWidth = Display.getDisplayMode().getWidth();
/* 2112 */         minecraft.displayHeight = Display.getDisplayMode().getHeight();
/*      */         
/* 2114 */         if (minecraft.displayWidth <= 0)
/*      */         {
/* 2116 */           minecraft.displayWidth = 1;
/*      */         }
/*      */         
/* 2119 */         if (minecraft.displayHeight <= 0)
/*      */         {
/* 2121 */           minecraft.displayHeight = 1;
/*      */         }
/*      */         
/* 2124 */         if (minecraft.currentScreen != null) {
/*      */           
/* 2126 */           ScaledResolution scaledresolution = new ScaledResolution(minecraft);
/* 2127 */           int i = ScaledResolution.getScaledWidth();
/* 2128 */           int j = ScaledResolution.getScaledHeight();
/* 2129 */           minecraft.currentScreen.setWorldAndResolution(minecraft, i, j);
/*      */         } 
/*      */         
/* 2132 */         minecraft.loadingScreen = new LoadingScreenRenderer(minecraft);
/* 2133 */         updateFramebufferSize();
/* 2134 */         Display.setFullscreen(true);
/* 2135 */         minecraft.gameSettings.updateVSync();
/* 2136 */         GlStateManager.enableTexture2D();
/*      */       }
/*      */       else
/*      */       {
/* 2140 */         if (desktopModeChecked) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 2145 */         desktopModeChecked = true;
/* 2146 */         fullscreenModeChecked = false;
/* 2147 */         minecraft.gameSettings.updateVSync();
/* 2148 */         Display.update();
/* 2149 */         GlStateManager.enableTexture2D();
/* 2150 */         Display.setResizable(false);
/* 2151 */         Display.setResizable(true);
/*      */       }
/*      */     
/* 2154 */     } catch (Exception exception) {
/*      */       
/* 2156 */       exception.printStackTrace();
/* 2157 */       gameSettings.ofFullscreenMode = "Default";
/* 2158 */       gameSettings.saveOfOptions();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateFramebufferSize() {
/* 2164 */     minecraft.getFramebuffer().createBindFramebuffer(minecraft.displayWidth, minecraft.displayHeight);
/*      */     
/* 2166 */     if (minecraft.entityRenderer != null)
/*      */     {
/* 2168 */       minecraft.entityRenderer.updateShaderGroupSize(minecraft.displayWidth, minecraft.displayHeight);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object[] addObjectToArray(Object[] p_addObjectToArray_0_, Object p_addObjectToArray_1_) {
/* 2174 */     if (p_addObjectToArray_0_ == null)
/*      */     {
/* 2176 */       throw new NullPointerException("The given array is NULL");
/*      */     }
/*      */ 
/*      */     
/* 2180 */     int i = p_addObjectToArray_0_.length;
/* 2181 */     int j = i + 1;
/* 2182 */     Object[] aobject = (Object[])Array.newInstance(p_addObjectToArray_0_.getClass().getComponentType(), j);
/* 2183 */     System.arraycopy(p_addObjectToArray_0_, 0, aobject, 0, i);
/* 2184 */     aobject[i] = p_addObjectToArray_1_;
/* 2185 */     return aobject;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object[] addObjectToArray(Object[] p_addObjectToArray_0_, Object p_addObjectToArray_1_, int p_addObjectToArray_2_) {
/* 2191 */     List<Object> list = new ArrayList(Arrays.asList(p_addObjectToArray_0_));
/* 2192 */     list.add(p_addObjectToArray_2_, p_addObjectToArray_1_);
/* 2193 */     Object[] aobject = (Object[])Array.newInstance(p_addObjectToArray_0_.getClass().getComponentType(), list.size());
/* 2194 */     return list.toArray(aobject);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object[] addObjectsToArray(Object[] p_addObjectsToArray_0_, Object[] p_addObjectsToArray_1_) {
/* 2199 */     if (p_addObjectsToArray_0_ == null)
/*      */     {
/* 2201 */       throw new NullPointerException("The given array is NULL");
/*      */     }
/* 2203 */     if (p_addObjectsToArray_1_.length == 0)
/*      */     {
/* 2205 */       return p_addObjectsToArray_0_;
/*      */     }
/*      */ 
/*      */     
/* 2209 */     int i = p_addObjectsToArray_0_.length;
/* 2210 */     int j = i + p_addObjectsToArray_1_.length;
/* 2211 */     Object[] aobject = (Object[])Array.newInstance(p_addObjectsToArray_0_.getClass().getComponentType(), j);
/* 2212 */     System.arraycopy(p_addObjectsToArray_0_, 0, aobject, 0, i);
/* 2213 */     System.arraycopy(p_addObjectsToArray_1_, 0, aobject, i, p_addObjectsToArray_1_.length);
/* 2214 */     return aobject;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isCustomItems() {
/* 2220 */     return gameSettings.ofCustomItems;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawFps() {
/* 2225 */     int i = Minecraft.getDebugFPS();
/* 2226 */     String s = getUpdates(minecraft.debug);
/* 2227 */     int j = minecraft.renderGlobal.getCountActiveRenderers();
/* 2228 */     int k = minecraft.renderGlobal.getCountEntitiesRendered();
/* 2229 */     int l = minecraft.renderGlobal.getCountTileEntitiesRendered();
/* 2230 */     String s1 = i + " fps, C: " + j + ", E: " + k + "+" + l + ", U: " + s;
/* 2231 */     minecraft.fontRendererObj.drawString(s1, 2, 2, -2039584);
/*      */   }
/*      */ 
/*      */   
/*      */   private static String getUpdates(String p_getUpdates_0_) {
/* 2236 */     int i = p_getUpdates_0_.indexOf('(');
/*      */     
/* 2238 */     if (i < 0)
/*      */     {
/* 2240 */       return "";
/*      */     }
/*      */ 
/*      */     
/* 2244 */     int j = p_getUpdates_0_.indexOf(' ', i);
/* 2245 */     return (j < 0) ? "" : p_getUpdates_0_.substring(i + 1, j);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getBitsOs() {
/* 2251 */     String s = System.getenv("ProgramFiles(X86)");
/* 2252 */     return (s != null) ? 64 : 32;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getBitsJre() {
/* 2257 */     String[] astring = { "sun.arch.data.model", "com.ibm.vm.bitmode", "os.arch" };
/*      */     
/* 2259 */     for (int i = 0; i < astring.length; i++) {
/*      */       
/* 2261 */       String s = astring[i];
/* 2262 */       String s1 = System.getProperty(s);
/*      */       
/* 2264 */       if (s1 != null && s1.contains("64"))
/*      */       {
/* 2266 */         return 64;
/*      */       }
/*      */     } 
/*      */     
/* 2270 */     return 32;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isNotify64BitJava() {
/* 2275 */     return notify64BitJava;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setNotify64BitJava(boolean p_setNotify64BitJava_0_) {
/* 2280 */     notify64BitJava = p_setNotify64BitJava_0_;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isConnectedModels() {
/* 2285 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void showGuiMessage(String p_showGuiMessage_0_, String p_showGuiMessage_1_) {
/* 2290 */     GuiMessage guimessage = new GuiMessage(minecraft.currentScreen, p_showGuiMessage_0_, p_showGuiMessage_1_);
/* 2291 */     minecraft.displayGuiScreen(guimessage);
/*      */   }
/*      */ 
/*      */   
/*      */   public static int[] addIntToArray(int[] p_addIntToArray_0_, int p_addIntToArray_1_) {
/* 2296 */     return addIntsToArray(p_addIntToArray_0_, new int[] { p_addIntToArray_1_ });
/*      */   }
/*      */ 
/*      */   
/*      */   public static int[] addIntsToArray(int[] p_addIntsToArray_0_, int[] p_addIntsToArray_1_) {
/* 2301 */     if (p_addIntsToArray_0_ != null && p_addIntsToArray_1_ != null) {
/*      */       
/* 2303 */       int i = p_addIntsToArray_0_.length;
/* 2304 */       int j = i + p_addIntsToArray_1_.length;
/* 2305 */       int[] aint = new int[j];
/* 2306 */       System.arraycopy(p_addIntsToArray_0_, 0, aint, 0, i);
/*      */       
/* 2308 */       for (int k = 0; k < p_addIntsToArray_1_.length; k++)
/*      */       {
/* 2310 */         aint[k + i] = p_addIntsToArray_1_[k];
/*      */       }
/*      */       
/* 2313 */       return aint;
/*      */     } 
/*      */ 
/*      */     
/* 2317 */     throw new NullPointerException("The given array is NULL");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static DynamicTexture getMojangLogoTexture(DynamicTexture p_getMojangLogoTexture_0_) {
/*      */     try {
/* 2325 */       ResourceLocation resourcelocation = new ResourceLocation("textures/gui/title/mojang.png");
/* 2326 */       InputStream inputstream = getResourceStream(resourcelocation);
/*      */       
/* 2328 */       if (inputstream == null)
/*      */       {
/* 2330 */         return p_getMojangLogoTexture_0_;
/*      */       }
/*      */ 
/*      */       
/* 2334 */       BufferedImage bufferedimage = ImageIO.read(inputstream);
/*      */       
/* 2336 */       if (bufferedimage == null)
/*      */       {
/* 2338 */         return p_getMojangLogoTexture_0_;
/*      */       }
/*      */ 
/*      */       
/* 2342 */       DynamicTexture dynamictexture = new DynamicTexture(bufferedimage);
/* 2343 */       return dynamictexture;
/*      */ 
/*      */     
/*      */     }
/* 2347 */     catch (Exception exception) {
/*      */       
/* 2349 */       warn(String.valueOf(exception.getClass().getName()) + ": " + exception.getMessage());
/* 2350 */       return p_getMojangLogoTexture_0_;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void writeFile(File p_writeFile_0_, String p_writeFile_1_) throws IOException {
/* 2356 */     FileOutputStream fileoutputstream = new FileOutputStream(p_writeFile_0_);
/* 2357 */     byte[] abyte = p_writeFile_1_.getBytes("ASCII");
/* 2358 */     fileoutputstream.write(abyte);
/* 2359 */     fileoutputstream.close();
/*      */   }
/*      */ 
/*      */   
/*      */   public static TextureMap getTextureMap() {
/* 2364 */     return getMinecraft().getTextureMapBlocks();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDynamicLights() {
/* 2369 */     return (gameSettings.ofDynamicLights != 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDynamicLightsFast() {
/* 2374 */     return (gameSettings.ofDynamicLights == 1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isDynamicHandLight() {
/* 2379 */     if (!isDynamicLights())
/*      */     {
/* 2381 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2385 */     return isShaders() ? Shaders.isDynamicHandLight() : true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isCustomEntityModels() {
/* 2391 */     return gameSettings.ofCustomEntityModels;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isCustomGuis() {
/* 2396 */     return gameSettings.ofCustomGuis;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getScreenshotSize() {
/* 2401 */     return gameSettings.ofScreenshotSize;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\Config.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */