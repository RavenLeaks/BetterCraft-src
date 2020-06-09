/*      */ package shadersmod.client;
/*      */ 
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.nio.IntBuffer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.IdentityHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.model.ModelBase;
/*      */ import net.minecraft.client.model.ModelRenderer;
/*      */ import net.minecraft.client.renderer.BufferBuilder;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.GlStateManager;
/*      */ import net.minecraft.client.renderer.OpenGlHelper;
/*      */ import net.minecraft.client.renderer.RenderHelper;
/*      */ import net.minecraft.client.renderer.Tessellator;
/*      */ import net.minecraft.client.renderer.entity.Render;
/*      */ import net.minecraft.client.renderer.entity.RenderLiving;
/*      */ import net.minecraft.client.renderer.texture.ITextureObject;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.init.MobEffects;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemBlock;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.BlockRenderLayer;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.TextComponentString;
/*      */ import net.minecraft.world.World;
/*      */ import optifine.Config;
/*      */ import optifine.CustomColors;
/*      */ import optifine.EntityUtils;
/*      */ import optifine.Lang;
/*      */ import optifine.PropertiesOrdered;
/*      */ import optifine.Reflector;
/*      */ import optifine.StrUtils;
/*      */ import org.apache.commons.io.IOUtils;
/*      */ import org.lwjgl.BufferUtils;
/*      */ import org.lwjgl.opengl.ARBShaderObjects;
/*      */ import org.lwjgl.opengl.ARBVertexShader;
/*      */ import org.lwjgl.opengl.ContextCapabilities;
/*      */ import org.lwjgl.opengl.EXTFramebufferObject;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GL20;
/*      */ import org.lwjgl.opengl.GL30;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ import shadersmod.common.SMCLog;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Shaders
/*      */ {
/*      */   static Minecraft mc;
/*      */   static EntityRenderer entityRenderer;
/*      */   public static boolean isInitializedOnce = false;
/*      */   public static boolean isShaderPackInitialized = false;
/*      */   public static ContextCapabilities capabilities;
/*      */   public static String glVersionString;
/*      */   public static String glVendorString;
/*      */   public static String glRendererString;
/*      */   public static boolean hasGlGenMipmap = false;
/*      */   public static boolean hasForge = false;
/*   92 */   public static int numberResetDisplayList = 0;
/*      */   static boolean needResetModels = false;
/*   94 */   private static int renderDisplayWidth = 0;
/*   95 */   private static int renderDisplayHeight = 0;
/*   96 */   public static int renderWidth = 0;
/*   97 */   public static int renderHeight = 0;
/*      */   public static boolean isRenderingWorld = false;
/*      */   public static boolean isRenderingSky = false;
/*      */   public static boolean isCompositeRendered = false;
/*      */   public static boolean isRenderingDfb = false;
/*      */   public static boolean isShadowPass = false;
/*      */   public static boolean isSleeping;
/*      */   private static boolean isRenderingFirstPersonHand;
/*      */   private static boolean isHandRenderedMain;
/*      */   private static boolean isHandRenderedOff;
/*      */   private static boolean skipRenderHandMain;
/*      */   private static boolean skipRenderHandOff;
/*      */   public static boolean renderItemKeepDepthMask = false;
/*      */   public static boolean itemToRenderMainTranslucent = false;
/*      */   public static boolean itemToRenderOffTranslucent = false;
/*  112 */   static float[] sunPosition = new float[4];
/*  113 */   static float[] moonPosition = new float[4];
/*  114 */   static float[] shadowLightPosition = new float[4];
/*  115 */   static float[] upPosition = new float[4];
/*  116 */   static float[] shadowLightPositionVector = new float[4];
/*  117 */   static float[] upPosModelView = new float[] { 0.0F, 100.0F, 0.0F, 0.0F };
/*  118 */   static float[] sunPosModelView = new float[] { 0.0F, 100.0F, 0.0F, 0.0F };
/*  119 */   static float[] moonPosModelView = new float[] { 0.0F, -100.0F, 0.0F, 0.0F };
/*  120 */   private static float[] tempMat = new float[16];
/*      */   static float clearColorR;
/*      */   static float clearColorG;
/*      */   static float clearColorB;
/*      */   static float skyColorR;
/*      */   static float skyColorG;
/*      */   static float skyColorB;
/*  127 */   static long worldTime = 0L;
/*  128 */   static long lastWorldTime = 0L;
/*  129 */   static long diffWorldTime = 0L;
/*  130 */   static float celestialAngle = 0.0F;
/*  131 */   static float sunAngle = 0.0F;
/*  132 */   static float shadowAngle = 0.0F;
/*  133 */   static int moonPhase = 0;
/*  134 */   static long systemTime = 0L;
/*  135 */   static long lastSystemTime = 0L;
/*  136 */   static long diffSystemTime = 0L;
/*  137 */   static int frameCounter = 0;
/*  138 */   static float frameTime = 0.0F;
/*  139 */   static float frameTimeCounter = 0.0F;
/*  140 */   static int systemTimeInt32 = 0;
/*  141 */   static float rainStrength = 0.0F;
/*  142 */   static float wetness = 0.0F;
/*  143 */   public static float wetnessHalfLife = 600.0F;
/*  144 */   public static float drynessHalfLife = 200.0F;
/*  145 */   public static float eyeBrightnessHalflife = 10.0F;
/*      */   static boolean usewetness = false;
/*  147 */   static int isEyeInWater = 0;
/*  148 */   static int eyeBrightness = 0;
/*  149 */   static float eyeBrightnessFadeX = 0.0F;
/*  150 */   static float eyeBrightnessFadeY = 0.0F;
/*  151 */   static float eyePosY = 0.0F;
/*  152 */   static float centerDepth = 0.0F;
/*  153 */   static float centerDepthSmooth = 0.0F;
/*  154 */   static float centerDepthSmoothHalflife = 1.0F;
/*      */   static boolean centerDepthSmoothEnabled = false;
/*  156 */   static int superSamplingLevel = 1;
/*  157 */   static float nightVision = 0.0F;
/*  158 */   static float blindness = 0.0F;
/*      */   static boolean updateChunksErrorRecorded = false;
/*      */   static boolean lightmapEnabled = false;
/*      */   static boolean fogEnabled = true;
/*  162 */   public static int entityAttrib = 10;
/*  163 */   public static int midTexCoordAttrib = 11;
/*  164 */   public static int tangentAttrib = 12;
/*      */   public static boolean useEntityAttrib = false;
/*      */   public static boolean useMidTexCoordAttrib = false;
/*      */   public static boolean useMultiTexCoord3Attrib = false;
/*      */   public static boolean useTangentAttrib = false;
/*      */   public static boolean progUseEntityAttrib = false;
/*      */   public static boolean progUseMidTexCoordAttrib = false;
/*      */   public static boolean progUseTangentAttrib = false;
/*  172 */   public static int atlasSizeX = 0;
/*  173 */   public static int atlasSizeY = 0;
/*  174 */   public static ShaderUniformFloat4 uniformEntityColor = new ShaderUniformFloat4("entityColor");
/*  175 */   public static ShaderUniformInt uniformEntityId = new ShaderUniformInt("entityId");
/*  176 */   public static ShaderUniformInt uniformBlockEntityId = new ShaderUniformInt("blockEntityId");
/*      */   static double previousCameraPositionX;
/*      */   static double previousCameraPositionY;
/*      */   static double previousCameraPositionZ;
/*      */   static double cameraPositionX;
/*      */   static double cameraPositionY;
/*      */   static double cameraPositionZ;
/*  183 */   static int shadowPassInterval = 0;
/*      */   public static boolean needResizeShadow = false;
/*  185 */   static int shadowMapWidth = 1024;
/*  186 */   static int shadowMapHeight = 1024;
/*  187 */   static int spShadowMapWidth = 1024;
/*  188 */   static int spShadowMapHeight = 1024;
/*  189 */   static float shadowMapFOV = 90.0F;
/*  190 */   static float shadowMapHalfPlane = 160.0F;
/*      */   static boolean shadowMapIsOrtho = true;
/*  192 */   static float shadowDistanceRenderMul = -1.0F;
/*  193 */   static int shadowPassCounter = 0;
/*      */   static int preShadowPassThirdPersonView;
/*      */   public static boolean shouldSkipDefaultShadow = false;
/*      */   static boolean waterShadowEnabled = false;
/*      */   static final int MaxDrawBuffers = 8;
/*      */   static final int MaxColorBuffers = 8;
/*      */   static final int MaxDepthBuffers = 3;
/*      */   static final int MaxShadowColorBuffers = 8;
/*      */   static final int MaxShadowDepthBuffers = 2;
/*  202 */   static int usedColorBuffers = 0;
/*  203 */   static int usedDepthBuffers = 0;
/*  204 */   static int usedShadowColorBuffers = 0;
/*  205 */   static int usedShadowDepthBuffers = 0;
/*  206 */   static int usedColorAttachs = 0;
/*  207 */   static int usedDrawBuffers = 0;
/*  208 */   static int dfb = 0;
/*  209 */   static int sfb = 0;
/*  210 */   private static int[] gbuffersFormat = new int[8];
/*  211 */   private static boolean[] gbuffersClear = new boolean[8];
/*  212 */   public static int activeProgram = 0;
/*      */   public static final int ProgramNone = 0;
/*      */   public static final int ProgramBasic = 1;
/*      */   public static final int ProgramTextured = 2;
/*      */   public static final int ProgramTexturedLit = 3;
/*      */   public static final int ProgramSkyBasic = 4;
/*      */   public static final int ProgramSkyTextured = 5;
/*      */   public static final int ProgramClouds = 6;
/*      */   public static final int ProgramTerrain = 7;
/*      */   public static final int ProgramTerrainSolid = 8;
/*      */   public static final int ProgramTerrainCutoutMip = 9;
/*      */   public static final int ProgramTerrainCutout = 10;
/*      */   public static final int ProgramDamagedBlock = 11;
/*      */   public static final int ProgramWater = 12;
/*      */   public static final int ProgramBlock = 13;
/*      */   public static final int ProgramBeaconBeam = 14;
/*      */   public static final int ProgramItem = 15;
/*      */   public static final int ProgramEntities = 16;
/*      */   public static final int ProgramArmorGlint = 17;
/*      */   public static final int ProgramSpiderEyes = 18;
/*      */   public static final int ProgramHand = 19;
/*      */   public static final int ProgramWeather = 20;
/*      */   public static final int ProgramComposite = 21;
/*      */   public static final int ProgramComposite1 = 22;
/*      */   public static final int ProgramComposite2 = 23;
/*      */   public static final int ProgramComposite3 = 24;
/*      */   public static final int ProgramComposite4 = 25;
/*      */   public static final int ProgramComposite5 = 26;
/*      */   public static final int ProgramComposite6 = 27;
/*      */   public static final int ProgramComposite7 = 28;
/*      */   public static final int ProgramFinal = 29;
/*      */   public static final int ProgramShadow = 30;
/*      */   public static final int ProgramShadowSolid = 31;
/*      */   public static final int ProgramShadowCutout = 32;
/*      */   public static final int ProgramCount = 33;
/*      */   public static final int MaxCompositePasses = 8;
/*  248 */   private static final String[] programNames = new String[] { "", "gbuffers_basic", "gbuffers_textured", "gbuffers_textured_lit", "gbuffers_skybasic", "gbuffers_skytextured", "gbuffers_clouds", "gbuffers_terrain", "gbuffers_terrain_solid", "gbuffers_terrain_cutout_mip", "gbuffers_terrain_cutout", "gbuffers_damagedblock", "gbuffers_water", "gbuffers_block", "gbuffers_beaconbeam", "gbuffers_item", "gbuffers_entities", "gbuffers_armor_glint", "gbuffers_spidereyes", "gbuffers_hand", "gbuffers_weather", "composite", "composite1", "composite2", "composite3", "composite4", "composite5", "composite6", "composite7", "final", "shadow", "shadow_solid", "shadow_cutout" };
/*  249 */   private static final int[] programBackups = new int[] { 0, 0, 1, 2, 1, 2, 2, 3, 7, 7, 7, 7, 7, 7, 2, 3, 3, 2, 2, 3, 3, 30, 30 };
/*  250 */   static int[] programsID = new int[33];
/*  251 */   private static int[] programsRef = new int[33];
/*  252 */   private static int programIDCopyDepth = 0;
/*  253 */   private static String[] programsDrawBufSettings = new String[33];
/*  254 */   private static String newDrawBufSetting = null;
/*  255 */   static IntBuffer[] programsDrawBuffers = new IntBuffer[33];
/*  256 */   static IntBuffer activeDrawBuffers = null;
/*  257 */   private static String[] programsColorAtmSettings = new String[33];
/*  258 */   private static String newColorAtmSetting = null;
/*  259 */   private static String activeColorAtmSettings = null;
/*  260 */   private static int[] programsCompositeMipmapSetting = new int[33];
/*  261 */   private static int newCompositeMipmapSetting = 0;
/*  262 */   private static int activeCompositeMipmapSetting = 0;
/*  263 */   public static Properties loadedShaders = null;
/*  264 */   public static Properties shadersConfig = null;
/*  265 */   public static ITextureObject defaultTexture = null;
/*      */   public static boolean normalMapEnabled = false;
/*  267 */   public static boolean[] shadowHardwareFilteringEnabled = new boolean[2];
/*  268 */   public static boolean[] shadowMipmapEnabled = new boolean[2];
/*  269 */   public static boolean[] shadowFilterNearest = new boolean[2];
/*  270 */   public static boolean[] shadowColorMipmapEnabled = new boolean[8];
/*  271 */   public static boolean[] shadowColorFilterNearest = new boolean[8];
/*      */   public static boolean configTweakBlockDamage = false;
/*      */   public static boolean configCloudShadow = false;
/*  274 */   public static float configHandDepthMul = 0.125F;
/*  275 */   public static float configRenderResMul = 1.0F;
/*  276 */   public static float configShadowResMul = 1.0F;
/*  277 */   public static int configTexMinFilB = 0;
/*  278 */   public static int configTexMinFilN = 0;
/*  279 */   public static int configTexMinFilS = 0;
/*  280 */   public static int configTexMagFilB = 0;
/*  281 */   public static int configTexMagFilN = 0;
/*  282 */   public static int configTexMagFilS = 0;
/*      */   public static boolean configShadowClipFrustrum = true;
/*      */   public static boolean configNormalMap = true;
/*      */   public static boolean configSpecularMap = true;
/*  286 */   public static PropertyDefaultTrueFalse configOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
/*  287 */   public static PropertyDefaultTrueFalse configOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
/*  288 */   public static int configAntialiasingLevel = 0;
/*      */   public static final int texMinFilRange = 3;
/*      */   public static final int texMagFilRange = 2;
/*  291 */   public static final String[] texMinFilDesc = new String[] { "Nearest", "Nearest-Nearest", "Nearest-Linear" };
/*  292 */   public static final String[] texMagFilDesc = new String[] { "Nearest", "Linear" };
/*  293 */   public static final int[] texMinFilValue = new int[] { 9728, 9984, 9986 };
/*  294 */   public static final int[] texMagFilValue = new int[] { 9728, 9729 };
/*  295 */   static IShaderPack shaderPack = null;
/*      */   public static boolean shaderPackLoaded = false;
/*      */   static File currentshader;
/*      */   static String currentshadername;
/*  299 */   public static String packNameNone = "OFF";
/*  300 */   static String packNameDefault = "(internal)";
/*  301 */   static String shaderpacksdirname = "shaderpacks";
/*  302 */   static String optionsfilename = "optionsshaders.txt";
/*      */   static File shadersdir;
/*      */   static File shaderpacksdir;
/*      */   static File configFile;
/*  306 */   static ShaderOption[] shaderPackOptions = null;
/*  307 */   static Set<String> shaderPackOptionSliders = null;
/*  308 */   static ShaderProfile[] shaderPackProfiles = null;
/*  309 */   static Map<String, ScreenShaderOptions> shaderPackGuiScreens = null;
/*  310 */   public static PropertyDefaultFastFancyOff shaderPackClouds = new PropertyDefaultFastFancyOff("clouds", "Clouds", 0);
/*  311 */   public static PropertyDefaultTrueFalse shaderPackOldLighting = new PropertyDefaultTrueFalse("oldLighting", "Classic Lighting", 0);
/*  312 */   public static PropertyDefaultTrueFalse shaderPackOldHandLight = new PropertyDefaultTrueFalse("oldHandLight", "Old Hand Light", 0);
/*  313 */   public static PropertyDefaultTrueFalse shaderPackDynamicHandLight = new PropertyDefaultTrueFalse("dynamicHandLight", "Dynamic Hand Light", 0);
/*  314 */   public static PropertyDefaultTrueFalse shaderPackShadowTranslucent = new PropertyDefaultTrueFalse("shadowTranslucent", "Shadow Translucent", 0);
/*  315 */   public static PropertyDefaultTrueFalse shaderPackUnderwaterOverlay = new PropertyDefaultTrueFalse("underwaterOverlay", "Underwater Overlay", 0);
/*  316 */   public static PropertyDefaultTrueFalse shaderPackSun = new PropertyDefaultTrueFalse("sun", "Sun", 0);
/*  317 */   public static PropertyDefaultTrueFalse shaderPackMoon = new PropertyDefaultTrueFalse("moon", "Moon", 0);
/*  318 */   public static PropertyDefaultTrueFalse shaderPackVignette = new PropertyDefaultTrueFalse("vignette", "Vignette", 0);
/*  319 */   public static PropertyDefaultTrueFalse shaderPackBackFaceSolid = new PropertyDefaultTrueFalse("backFace.solid", "Back-face Solid", 0);
/*  320 */   public static PropertyDefaultTrueFalse shaderPackBackFaceCutout = new PropertyDefaultTrueFalse("backFace.cutout", "Back-face Cutout", 0);
/*  321 */   public static PropertyDefaultTrueFalse shaderPackBackFaceCutoutMipped = new PropertyDefaultTrueFalse("backFace.cutoutMipped", "Back-face Cutout Mipped", 0);
/*  322 */   public static PropertyDefaultTrueFalse shaderPackBackFaceTranslucent = new PropertyDefaultTrueFalse("backFace.translucent", "Back-face Translucent", 0);
/*  323 */   private static Map<String, String> shaderPackResources = new HashMap<>();
/*  324 */   private static World currentWorld = null;
/*  325 */   private static List<Integer> shaderPackDimensions = new ArrayList<>();
/*  326 */   private static CustomTexture[] customTexturesGbuffers = null;
/*  327 */   private static CustomTexture[] customTexturesComposite = null;
/*  328 */   private static String noiseTexturePath = null;
/*      */   private static final int STAGE_GBUFFERS = 0;
/*      */   private static final int STAGE_COMPOSITE = 1;
/*  331 */   private static final String[] STAGE_NAMES = new String[] { "gbuffers", "composite" };
/*      */   public static final boolean enableShadersOption = true;
/*      */   private static final boolean enableShadersDebug = true;
/*  334 */   private static final boolean saveFinalShaders = System.getProperty("shaders.debug.save", "false").equals("true");
/*  335 */   public static float blockLightLevel05 = 0.5F;
/*  336 */   public static float blockLightLevel06 = 0.6F;
/*  337 */   public static float blockLightLevel08 = 0.8F;
/*  338 */   public static float aoLevel = -1.0F;
/*  339 */   public static float sunPathRotation = 0.0F;
/*  340 */   public static float shadowAngleInterval = 0.0F;
/*  341 */   public static int fogMode = 0;
/*      */   public static float fogColorR;
/*      */   public static float fogColorG;
/*      */   public static float fogColorB;
/*  345 */   public static float shadowIntervalSize = 2.0F;
/*  346 */   public static int terrainIconSize = 16;
/*  347 */   public static int[] terrainTextureSize = new int[2];
/*      */   private static ICustomTexture noiseTexture;
/*      */   private static boolean noiseTextureEnabled = false;
/*  350 */   private static int noiseTextureResolution = 256;
/*  351 */   static final int[] dfbColorTexturesA = new int[16];
/*  352 */   static final int[] colorTexturesToggle = new int[8];
/*  353 */   static final int[] colorTextureTextureImageUnit = new int[] { 0, 1, 2, 3, 7, 8, 9, 10 };
/*  354 */   static final boolean[][] programsToggleColorTextures = new boolean[33][8];
/*      */   private static final int bigBufferSize = 2196;
/*  356 */   private static final ByteBuffer bigBuffer = (ByteBuffer)BufferUtils.createByteBuffer(2196).limit(0);
/*  357 */   static final float[] faProjection = new float[16];
/*  358 */   static final float[] faProjectionInverse = new float[16];
/*  359 */   static final float[] faModelView = new float[16];
/*  360 */   static final float[] faModelViewInverse = new float[16];
/*  361 */   static final float[] faShadowProjection = new float[16];
/*  362 */   static final float[] faShadowProjectionInverse = new float[16];
/*  363 */   static final float[] faShadowModelView = new float[16];
/*  364 */   static final float[] faShadowModelViewInverse = new float[16];
/*  365 */   static final FloatBuffer projection = nextFloatBuffer(16);
/*  366 */   static final FloatBuffer projectionInverse = nextFloatBuffer(16);
/*  367 */   static final FloatBuffer modelView = nextFloatBuffer(16);
/*  368 */   static final FloatBuffer modelViewInverse = nextFloatBuffer(16);
/*  369 */   static final FloatBuffer shadowProjection = nextFloatBuffer(16);
/*  370 */   static final FloatBuffer shadowProjectionInverse = nextFloatBuffer(16);
/*  371 */   static final FloatBuffer shadowModelView = nextFloatBuffer(16);
/*  372 */   static final FloatBuffer shadowModelViewInverse = nextFloatBuffer(16);
/*  373 */   static final FloatBuffer previousProjection = nextFloatBuffer(16);
/*  374 */   static final FloatBuffer previousModelView = nextFloatBuffer(16);
/*  375 */   static final FloatBuffer tempMatrixDirectBuffer = nextFloatBuffer(16);
/*  376 */   static final FloatBuffer tempDirectFloatBuffer = nextFloatBuffer(16);
/*  377 */   static final IntBuffer dfbColorTextures = nextIntBuffer(16);
/*  378 */   static final IntBuffer dfbDepthTextures = nextIntBuffer(3);
/*  379 */   static final IntBuffer sfbColorTextures = nextIntBuffer(8);
/*  380 */   static final IntBuffer sfbDepthTextures = nextIntBuffer(2);
/*  381 */   static final IntBuffer dfbDrawBuffers = nextIntBuffer(8);
/*  382 */   static final IntBuffer sfbDrawBuffers = nextIntBuffer(8);
/*  383 */   static final IntBuffer drawBuffersNone = nextIntBuffer(8);
/*  384 */   static final IntBuffer drawBuffersAll = nextIntBuffer(8);
/*  385 */   static final IntBuffer drawBuffersClear0 = nextIntBuffer(8);
/*  386 */   static final IntBuffer drawBuffersClear1 = nextIntBuffer(8);
/*  387 */   static final IntBuffer drawBuffersClearColor = nextIntBuffer(8);
/*  388 */   static final IntBuffer drawBuffersColorAtt0 = nextIntBuffer(8);
/*  389 */   static final IntBuffer[] drawBuffersBuffer = nextIntBufferArray(33, 8);
/*      */   static Map<Block, Integer> mapBlockToEntityData;
/*  391 */   private static final String[] formatNames = new String[] { "R8", "RG8", "RGB8", "RGBA8", "R8_SNORM", "RG8_SNORM", "RGB8_SNORM", "RGBA8_SNORM", "R16", "RG16", "RGB16", "RGBA16", "R16_SNORM", "RG16_SNORM", "RGB16_SNORM", "RGBA16_SNORM", "R16F", "RG16F", "RGB16F", "RGBA16F", "R32F", "RG32F", "RGB32F", "RGBA32F", "R32I", "RG32I", "RGB32I", "RGBA32I", "R32UI", "RG32UI", "RGB32UI", "RGBA32UI", "R3_G3_B2", "RGB5_A1", "RGB10_A2", "R11F_G11F_B10F", "RGB9_E5" };
/*  392 */   private static final int[] formatIds = new int[] { 33321, 33323, 32849, 32856, 36756, 36757, 36758, 36759, 33322, 33324, 32852, 32859, 36760, 36761, 36762, 36763, 33325, 33327, 34843, 34842, 33326, 33328, 34837, 34836, 33333, 33339, 36227, 36226, 33334, 33340, 36209, 36208, 10768, 32855, 32857, 35898, 35901 };
/*  393 */   private static final Pattern patternLoadEntityDataMap = Pattern.compile("\\s*([\\w:]+)\\s*=\\s*([-]?\\d+)\\s*");
/*  394 */   public static int[] entityData = new int[32];
/*  395 */   public static int entityDataIndex = 0;
/*      */ 
/*      */   
/*      */   private static ByteBuffer nextByteBuffer(int size) {
/*  399 */     ByteBuffer bytebuffer = bigBuffer;
/*  400 */     int i = bytebuffer.limit();
/*  401 */     bytebuffer.position(i).limit(i + size);
/*  402 */     return bytebuffer.slice();
/*      */   }
/*      */ 
/*      */   
/*      */   private static IntBuffer nextIntBuffer(int size) {
/*  407 */     ByteBuffer bytebuffer = bigBuffer;
/*  408 */     int i = bytebuffer.limit();
/*  409 */     bytebuffer.position(i).limit(i + size * 4);
/*  410 */     return bytebuffer.asIntBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static FloatBuffer nextFloatBuffer(int size) {
/*  415 */     ByteBuffer bytebuffer = bigBuffer;
/*  416 */     int i = bytebuffer.limit();
/*  417 */     bytebuffer.position(i).limit(i + size * 4);
/*  418 */     return bytebuffer.asFloatBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static IntBuffer[] nextIntBufferArray(int count, int size) {
/*  423 */     IntBuffer[] aintbuffer = new IntBuffer[count];
/*      */     
/*  425 */     for (int i = 0; i < count; i++)
/*      */     {
/*  427 */       aintbuffer[i] = nextIntBuffer(size);
/*      */     }
/*      */     
/*  430 */     return aintbuffer;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void loadConfig() {
/*  435 */     SMCLog.info("Load ShadersMod configuration.");
/*      */ 
/*      */     
/*      */     try {
/*  439 */       if (!shaderpacksdir.exists())
/*      */       {
/*  441 */         shaderpacksdir.mkdir();
/*      */       }
/*      */     }
/*  444 */     catch (Exception var8) {
/*      */       
/*  446 */       SMCLog.severe("Failed to open the shaderpacks directory: " + shaderpacksdir);
/*      */     } 
/*      */     
/*  449 */     shadersConfig = (Properties)new PropertiesOrdered();
/*  450 */     shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), "");
/*      */     
/*  452 */     if (configFile.exists()) {
/*      */       
/*      */       try {
/*      */         
/*  456 */         FileReader filereader = new FileReader(configFile);
/*  457 */         shadersConfig.load(filereader);
/*  458 */         filereader.close();
/*      */       }
/*  460 */       catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  466 */     if (!configFile.exists()) {
/*      */       
/*      */       try {
/*      */         
/*  470 */         storeConfig();
/*      */       }
/*  472 */       catch (Exception exception) {}
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  478 */     EnumShaderOption[] aenumshaderoption = EnumShaderOption.values();
/*      */     
/*  480 */     for (int i = 0; i < aenumshaderoption.length; i++) {
/*      */       
/*  482 */       EnumShaderOption enumshaderoption = aenumshaderoption[i];
/*  483 */       String s = enumshaderoption.getPropertyKey();
/*  484 */       String s1 = enumshaderoption.getValueDefault();
/*  485 */       String s2 = shadersConfig.getProperty(s, s1);
/*  486 */       setEnumShaderOption(enumshaderoption, s2);
/*      */     } 
/*      */     
/*  489 */     loadShaderPack();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setEnumShaderOption(EnumShaderOption eso, String str) {
/*  494 */     if (str == null)
/*      */     {
/*  496 */       str = eso.getValueDefault();
/*      */     }
/*      */     
/*  499 */     switch (eso) {
/*      */       
/*      */       case null:
/*  502 */         configAntialiasingLevel = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case NORMAL_MAP:
/*  506 */         configNormalMap = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case SPECULAR_MAP:
/*  510 */         configSpecularMap = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case RENDER_RES_MUL:
/*  514 */         configRenderResMul = Config.parseFloat(str, 1.0F);
/*      */         return;
/*      */       
/*      */       case SHADOW_RES_MUL:
/*  518 */         configShadowResMul = Config.parseFloat(str, 1.0F);
/*      */         return;
/*      */       
/*      */       case HAND_DEPTH_MUL:
/*  522 */         configHandDepthMul = Config.parseFloat(str, 0.125F);
/*      */         return;
/*      */       
/*      */       case CLOUD_SHADOW:
/*  526 */         configCloudShadow = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case OLD_HAND_LIGHT:
/*  530 */         configOldHandLight.setPropertyValue(str);
/*      */         return;
/*      */       
/*      */       case OLD_LIGHTING:
/*  534 */         configOldLighting.setPropertyValue(str);
/*      */         return;
/*      */       
/*      */       case SHADER_PACK:
/*  538 */         currentshadername = str;
/*      */         return;
/*      */       
/*      */       case TWEAK_BLOCK_DAMAGE:
/*  542 */         configTweakBlockDamage = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case SHADOW_CLIP_FRUSTRUM:
/*  546 */         configShadowClipFrustrum = Config.parseBoolean(str, true);
/*      */         return;
/*      */       
/*      */       case TEX_MIN_FIL_B:
/*  550 */         configTexMinFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case TEX_MIN_FIL_N:
/*  554 */         configTexMinFilN = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case TEX_MIN_FIL_S:
/*  558 */         configTexMinFilS = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case TEX_MAG_FIL_B:
/*  562 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case TEX_MAG_FIL_N:
/*  566 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */       
/*      */       case TEX_MAG_FIL_S:
/*  570 */         configTexMagFilB = Config.parseInt(str, 0);
/*      */         return;
/*      */     } 
/*      */     
/*  574 */     throw new IllegalArgumentException("Unknown option: " + eso);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void storeConfig() {
/*  580 */     SMCLog.info("Save ShadersMod configuration.");
/*      */     
/*  582 */     if (shadersConfig == null)
/*      */     {
/*  584 */       shadersConfig = (Properties)new PropertiesOrdered();
/*      */     }
/*      */     
/*  587 */     EnumShaderOption[] aenumshaderoption = EnumShaderOption.values();
/*      */     
/*  589 */     for (int i = 0; i < aenumshaderoption.length; i++) {
/*      */       
/*  591 */       EnumShaderOption enumshaderoption = aenumshaderoption[i];
/*  592 */       String s = enumshaderoption.getPropertyKey();
/*  593 */       String s1 = getEnumShaderOption(enumshaderoption);
/*  594 */       shadersConfig.setProperty(s, s1);
/*      */     } 
/*      */ 
/*      */     
/*      */     try {
/*  599 */       FileWriter filewriter = new FileWriter(configFile);
/*  600 */       shadersConfig.store(filewriter, (String)null);
/*  601 */       filewriter.close();
/*      */     }
/*  603 */     catch (Exception exception) {
/*      */       
/*  605 */       SMCLog.severe("Error saving configuration: " + exception.getClass().getName() + ": " + exception.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getEnumShaderOption(EnumShaderOption eso) {
/*  611 */     switch (eso) {
/*      */       
/*      */       case null:
/*  614 */         return Integer.toString(configAntialiasingLevel);
/*      */       
/*      */       case NORMAL_MAP:
/*  617 */         return Boolean.toString(configNormalMap);
/*      */       
/*      */       case SPECULAR_MAP:
/*  620 */         return Boolean.toString(configSpecularMap);
/*      */       
/*      */       case RENDER_RES_MUL:
/*  623 */         return Float.toString(configRenderResMul);
/*      */       
/*      */       case SHADOW_RES_MUL:
/*  626 */         return Float.toString(configShadowResMul);
/*      */       
/*      */       case HAND_DEPTH_MUL:
/*  629 */         return Float.toString(configHandDepthMul);
/*      */       
/*      */       case CLOUD_SHADOW:
/*  632 */         return Boolean.toString(configCloudShadow);
/*      */       
/*      */       case OLD_HAND_LIGHT:
/*  635 */         return configOldHandLight.getPropertyValue();
/*      */       
/*      */       case OLD_LIGHTING:
/*  638 */         return configOldLighting.getPropertyValue();
/*      */       
/*      */       case SHADER_PACK:
/*  641 */         return currentshadername;
/*      */       
/*      */       case TWEAK_BLOCK_DAMAGE:
/*  644 */         return Boolean.toString(configTweakBlockDamage);
/*      */       
/*      */       case SHADOW_CLIP_FRUSTRUM:
/*  647 */         return Boolean.toString(configShadowClipFrustrum);
/*      */       
/*      */       case TEX_MIN_FIL_B:
/*  650 */         return Integer.toString(configTexMinFilB);
/*      */       
/*      */       case TEX_MIN_FIL_N:
/*  653 */         return Integer.toString(configTexMinFilN);
/*      */       
/*      */       case TEX_MIN_FIL_S:
/*  656 */         return Integer.toString(configTexMinFilS);
/*      */       
/*      */       case TEX_MAG_FIL_B:
/*  659 */         return Integer.toString(configTexMagFilB);
/*      */       
/*      */       case TEX_MAG_FIL_N:
/*  662 */         return Integer.toString(configTexMagFilB);
/*      */       
/*      */       case TEX_MAG_FIL_S:
/*  665 */         return Integer.toString(configTexMagFilB);
/*      */     } 
/*      */     
/*  668 */     throw new IllegalArgumentException("Unknown option: " + eso);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setShaderPack(String par1name) {
/*  674 */     currentshadername = par1name;
/*  675 */     shadersConfig.setProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), par1name);
/*  676 */     loadShaderPack();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void loadShaderPack() {
/*  681 */     boolean flag = shaderPackLoaded;
/*  682 */     boolean flag1 = isOldLighting();
/*  683 */     shaderPackLoaded = false;
/*      */     
/*  685 */     if (shaderPack != null) {
/*      */       
/*  687 */       shaderPack.close();
/*  688 */       shaderPack = null;
/*  689 */       shaderPackResources.clear();
/*  690 */       shaderPackDimensions.clear();
/*  691 */       shaderPackOptions = null;
/*  692 */       shaderPackOptionSliders = null;
/*  693 */       shaderPackProfiles = null;
/*  694 */       shaderPackGuiScreens = null;
/*  695 */       shaderPackClouds.resetValue();
/*  696 */       shaderPackOldHandLight.resetValue();
/*  697 */       shaderPackDynamicHandLight.resetValue();
/*  698 */       shaderPackOldLighting.resetValue();
/*  699 */       resetCustomTextures();
/*  700 */       noiseTexturePath = null;
/*      */     } 
/*      */     
/*  703 */     boolean flag2 = false;
/*      */     
/*  705 */     if (Config.isAntialiasing()) {
/*      */       
/*  707 */       SMCLog.info("Shaders can not be loaded, Antialiasing is enabled: " + Config.getAntialiasingLevel() + "x");
/*  708 */       flag2 = true;
/*      */     } 
/*      */     
/*  711 */     if (Config.isAnisotropicFiltering()) {
/*      */       
/*  713 */       SMCLog.info("Shaders can not be loaded, Anisotropic Filtering is enabled: " + Config.getAnisotropicFilterLevel() + "x");
/*  714 */       flag2 = true;
/*      */     } 
/*      */     
/*  717 */     if (Config.isFastRender()) {
/*      */       
/*  719 */       SMCLog.info("Shaders can not be loaded, Fast Render is enabled.");
/*  720 */       flag2 = true;
/*      */     } 
/*      */     
/*  723 */     String s = shadersConfig.getProperty(EnumShaderOption.SHADER_PACK.getPropertyKey(), packNameDefault);
/*      */     
/*  725 */     if (!s.isEmpty() && !s.equals(packNameNone) && !flag2)
/*      */     {
/*  727 */       if (s.equals(packNameDefault)) {
/*      */         
/*  729 */         shaderPack = new ShaderPackDefault();
/*  730 */         shaderPackLoaded = true;
/*      */       } else {
/*      */ 
/*      */         
/*      */         try {
/*      */           
/*  736 */           File file1 = new File(shaderpacksdir, s);
/*      */           
/*  738 */           if (file1.isDirectory())
/*      */           {
/*  740 */             shaderPack = new ShaderPackFolder(s, file1);
/*  741 */             shaderPackLoaded = true;
/*      */           }
/*  743 */           else if (file1.isFile() && s.toLowerCase().endsWith(".zip"))
/*      */           {
/*  745 */             shaderPack = new ShaderPackZip(s, file1);
/*  746 */             shaderPackLoaded = true;
/*      */           }
/*      */         
/*  749 */         } catch (Exception exception) {}
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  756 */     if (shaderPack != null) {
/*      */       
/*  758 */       SMCLog.info("Loaded shaderpack: " + getShaderPackName());
/*      */     }
/*      */     else {
/*      */       
/*  762 */       SMCLog.info("No shaderpack loaded.");
/*  763 */       shaderPack = new ShaderPackNone();
/*      */     } 
/*      */     
/*  766 */     loadShaderPackResources();
/*  767 */     loadShaderPackDimensions();
/*  768 */     shaderPackOptions = loadShaderPackOptions();
/*  769 */     loadShaderPackProperties();
/*  770 */     boolean flag4 = shaderPackLoaded ^ flag;
/*  771 */     boolean flag3 = isOldLighting() ^ flag1;
/*      */     
/*  773 */     if (flag4 || flag3) {
/*      */       
/*  775 */       DefaultVertexFormats.updateVertexFormats();
/*      */       
/*  777 */       if (Reflector.LightUtil.exists()) {
/*      */         
/*  779 */         Reflector.LightUtil_itemConsumer.setValue(null);
/*  780 */         Reflector.LightUtil_tessellator.setValue(null);
/*      */       } 
/*      */       
/*  783 */       updateBlockLightLevel();
/*  784 */       mc.scheduleResourcesRefresh();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadShaderPackDimensions() {
/*  790 */     shaderPackDimensions.clear();
/*      */     
/*  792 */     for (int i = -128; i <= 128; i++) {
/*      */       
/*  794 */       String s = "/shaders/world" + i;
/*      */       
/*  796 */       if (shaderPack.hasDirectory(s))
/*      */       {
/*  798 */         shaderPackDimensions.add(Integer.valueOf(i));
/*      */       }
/*      */     } 
/*      */     
/*  802 */     if (shaderPackDimensions.size() > 0) {
/*      */       
/*  804 */       Integer[] ainteger = shaderPackDimensions.<Integer>toArray(new Integer[shaderPackDimensions.size()]);
/*  805 */       Config.dbg("[Shaders] Worlds: " + Config.arrayToString((Object[])ainteger));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadShaderPackProperties() {
/*  811 */     shaderPackClouds.resetValue();
/*  812 */     shaderPackOldHandLight.resetValue();
/*  813 */     shaderPackDynamicHandLight.resetValue();
/*  814 */     shaderPackOldLighting.resetValue();
/*  815 */     shaderPackShadowTranslucent.resetValue();
/*  816 */     shaderPackUnderwaterOverlay.resetValue();
/*  817 */     shaderPackSun.resetValue();
/*  818 */     shaderPackMoon.resetValue();
/*  819 */     shaderPackVignette.resetValue();
/*  820 */     shaderPackBackFaceSolid.resetValue();
/*  821 */     shaderPackBackFaceCutout.resetValue();
/*  822 */     shaderPackBackFaceCutoutMipped.resetValue();
/*  823 */     shaderPackBackFaceTranslucent.resetValue();
/*  824 */     BlockAliases.reset();
/*      */     
/*  826 */     if (shaderPack != null) {
/*      */       
/*  828 */       BlockAliases.update(shaderPack);
/*  829 */       String s = "/shaders/shaders.properties";
/*      */ 
/*      */       
/*      */       try {
/*  833 */         InputStream inputstream = shaderPack.getResourceAsStream(s);
/*      */         
/*  835 */         if (inputstream == null) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/*  840 */         PropertiesOrdered propertiesOrdered = new PropertiesOrdered();
/*  841 */         propertiesOrdered.load(inputstream);
/*  842 */         inputstream.close();
/*  843 */         shaderPackClouds.loadFrom((Properties)propertiesOrdered);
/*  844 */         shaderPackOldHandLight.loadFrom((Properties)propertiesOrdered);
/*  845 */         shaderPackDynamicHandLight.loadFrom((Properties)propertiesOrdered);
/*  846 */         shaderPackOldLighting.loadFrom((Properties)propertiesOrdered);
/*  847 */         shaderPackShadowTranslucent.loadFrom((Properties)propertiesOrdered);
/*  848 */         shaderPackUnderwaterOverlay.loadFrom((Properties)propertiesOrdered);
/*  849 */         shaderPackSun.loadFrom((Properties)propertiesOrdered);
/*  850 */         shaderPackVignette.loadFrom((Properties)propertiesOrdered);
/*  851 */         shaderPackMoon.loadFrom((Properties)propertiesOrdered);
/*  852 */         shaderPackBackFaceSolid.loadFrom((Properties)propertiesOrdered);
/*  853 */         shaderPackBackFaceCutout.loadFrom((Properties)propertiesOrdered);
/*  854 */         shaderPackBackFaceCutoutMipped.loadFrom((Properties)propertiesOrdered);
/*  855 */         shaderPackBackFaceTranslucent.loadFrom((Properties)propertiesOrdered);
/*  856 */         shaderPackOptionSliders = ShaderPackParser.parseOptionSliders((Properties)propertiesOrdered, shaderPackOptions);
/*  857 */         shaderPackProfiles = ShaderPackParser.parseProfiles((Properties)propertiesOrdered, shaderPackOptions);
/*  858 */         shaderPackGuiScreens = ShaderPackParser.parseGuiScreens((Properties)propertiesOrdered, shaderPackProfiles, shaderPackOptions);
/*  859 */         customTexturesGbuffers = loadCustomTextures((Properties)propertiesOrdered, 0);
/*  860 */         customTexturesComposite = loadCustomTextures((Properties)propertiesOrdered, 1);
/*  861 */         noiseTexturePath = propertiesOrdered.getProperty("texture.noise");
/*      */         
/*  863 */         if (noiseTexturePath != null)
/*      */         {
/*  865 */           noiseTextureEnabled = true;
/*      */         }
/*      */       }
/*  868 */       catch (IOException var3) {
/*      */         
/*  870 */         Config.warn("[Shaders] Error reading: " + s);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static CustomTexture[] loadCustomTextures(Properties props, int stage) {
/*  877 */     String s = "texture." + STAGE_NAMES[stage] + ".";
/*  878 */     Set set = props.keySet();
/*  879 */     List<CustomTexture> list = new ArrayList<>();
/*      */     
/*  881 */     for (Object s10 : set) {
/*      */       
/*  883 */       String s1 = (String)s10;
/*  884 */       if (s1.startsWith(s)) {
/*      */         
/*  886 */         String s2 = s1.substring(s.length());
/*  887 */         String s3 = props.getProperty(s1).trim();
/*  888 */         int i = getTextureIndex(stage, s2);
/*      */         
/*  890 */         if (i < 0) {
/*      */           
/*  892 */           SMCLog.warning("Invalid texture name: " + s1);
/*      */           
/*      */           continue;
/*      */         } 
/*  896 */         CustomTexture customtexture = loadCustomTexture(i, s3);
/*      */         
/*  898 */         if (customtexture != null)
/*      */         {
/*  900 */           list.add(customtexture);
/*      */         }
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  906 */     if (list.size() <= 0)
/*      */     {
/*  908 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  912 */     CustomTexture[] acustomtexture = list.<CustomTexture>toArray(new CustomTexture[list.size()]);
/*  913 */     return acustomtexture;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static CustomTexture loadCustomTexture(int index, String path) {
/*  919 */     if (path == null)
/*      */     {
/*  921 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  925 */     path = path.trim();
/*      */     
/*  927 */     if (path.indexOf('.') < 0)
/*      */     {
/*  929 */       path = String.valueOf(path) + ".png";
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/*  934 */       String s = "shaders/" + StrUtils.removePrefix(path, "/");
/*  935 */       InputStream inputstream = shaderPack.getResourceAsStream(s);
/*      */       
/*  937 */       if (inputstream == null) {
/*      */         
/*  939 */         SMCLog.warning("Texture not found: " + path);
/*  940 */         return null;
/*      */       } 
/*      */ 
/*      */       
/*  944 */       IOUtils.closeQuietly(inputstream);
/*  945 */       SimpleShaderTexture simpleshadertexture = new SimpleShaderTexture(s);
/*  946 */       simpleshadertexture.loadTexture(mc.getResourceManager());
/*  947 */       CustomTexture customtexture = new CustomTexture(index, s, (ITextureObject)simpleshadertexture);
/*  948 */       return customtexture;
/*      */     
/*      */     }
/*  951 */     catch (IOException ioexception) {
/*      */       
/*  953 */       SMCLog.warning("Error loading texture: " + path);
/*  954 */       SMCLog.warning(ioexception.getClass().getName() + ": " + ioexception.getMessage());
/*  955 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getTextureIndex(int stage, String name) {
/*  962 */     if (stage == 0) {
/*      */       
/*  964 */       if (name.equals("texture"))
/*      */       {
/*  966 */         return 0;
/*      */       }
/*      */       
/*  969 */       if (name.equals("lightmap"))
/*      */       {
/*  971 */         return 1;
/*      */       }
/*      */       
/*  974 */       if (name.equals("normals"))
/*      */       {
/*  976 */         return 2;
/*      */       }
/*      */       
/*  979 */       if (name.equals("specular"))
/*      */       {
/*  981 */         return 3;
/*      */       }
/*      */       
/*  984 */       if (name.equals("shadowtex0") || name.equals("watershadow"))
/*      */       {
/*  986 */         return 4;
/*      */       }
/*      */       
/*  989 */       if (name.equals("shadow"))
/*      */       {
/*  991 */         return waterShadowEnabled ? 5 : 4;
/*      */       }
/*      */       
/*  994 */       if (name.equals("shadowtex1"))
/*      */       {
/*  996 */         return 5;
/*      */       }
/*      */       
/*  999 */       if (name.equals("depthtex0"))
/*      */       {
/* 1001 */         return 6;
/*      */       }
/*      */       
/* 1004 */       if (name.equals("gaux1"))
/*      */       {
/* 1006 */         return 7;
/*      */       }
/*      */       
/* 1009 */       if (name.equals("gaux2"))
/*      */       {
/* 1011 */         return 8;
/*      */       }
/*      */       
/* 1014 */       if (name.equals("gaux3"))
/*      */       {
/* 1016 */         return 9;
/*      */       }
/*      */       
/* 1019 */       if (name.equals("gaux4"))
/*      */       {
/* 1021 */         return 10;
/*      */       }
/*      */       
/* 1024 */       if (name.equals("depthtex1"))
/*      */       {
/* 1026 */         return 12;
/*      */       }
/*      */       
/* 1029 */       if (name.equals("shadowcolor0") || name.equals("shadowcolor"))
/*      */       {
/* 1031 */         return 13;
/*      */       }
/*      */       
/* 1034 */       if (name.equals("shadowcolor1"))
/*      */       {
/* 1036 */         return 14;
/*      */       }
/*      */       
/* 1039 */       if (name.equals("noisetex"))
/*      */       {
/* 1041 */         return 15;
/*      */       }
/*      */     } 
/*      */     
/* 1045 */     if (stage == 1) {
/*      */       
/* 1047 */       if (name.equals("colortex0") || name.equals("colortex0"))
/*      */       {
/* 1049 */         return 0;
/*      */       }
/*      */       
/* 1052 */       if (name.equals("colortex1") || name.equals("gdepth"))
/*      */       {
/* 1054 */         return 1;
/*      */       }
/*      */       
/* 1057 */       if (name.equals("colortex2") || name.equals("gnormal"))
/*      */       {
/* 1059 */         return 2;
/*      */       }
/*      */       
/* 1062 */       if (name.equals("colortex3") || name.equals("composite"))
/*      */       {
/* 1064 */         return 3;
/*      */       }
/*      */       
/* 1067 */       if (name.equals("shadowtex0") || name.equals("watershadow"))
/*      */       {
/* 1069 */         return 4;
/*      */       }
/*      */       
/* 1072 */       if (name.equals("shadow"))
/*      */       {
/* 1074 */         return waterShadowEnabled ? 5 : 4;
/*      */       }
/*      */       
/* 1077 */       if (name.equals("shadowtex1"))
/*      */       {
/* 1079 */         return 5;
/*      */       }
/*      */       
/* 1082 */       if (name.equals("depthtex0") || name.equals("gdepthtex"))
/*      */       {
/* 1084 */         return 6;
/*      */       }
/*      */       
/* 1087 */       if (name.equals("colortex4") || name.equals("gaux1"))
/*      */       {
/* 1089 */         return 7;
/*      */       }
/*      */       
/* 1092 */       if (name.equals("colortex5") || name.equals("gaux2"))
/*      */       {
/* 1094 */         return 8;
/*      */       }
/*      */       
/* 1097 */       if (name.equals("colortex6") || name.equals("gaux3"))
/*      */       {
/* 1099 */         return 9;
/*      */       }
/*      */       
/* 1102 */       if (name.equals("colortex7") || name.equals("gaux4"))
/*      */       {
/* 1104 */         return 10;
/*      */       }
/*      */       
/* 1107 */       if (name.equals("depthtex1"))
/*      */       {
/* 1109 */         return 11;
/*      */       }
/*      */       
/* 1112 */       if (name.equals("depthtex2"))
/*      */       {
/* 1114 */         return 12;
/*      */       }
/*      */       
/* 1117 */       if (name.equals("shadowcolor0") || name.equals("shadowcolor"))
/*      */       {
/* 1119 */         return 13;
/*      */       }
/*      */       
/* 1122 */       if (name.equals("shadowcolor1"))
/*      */       {
/* 1124 */         return 14;
/*      */       }
/*      */       
/* 1127 */       if (name.equals("noisetex"))
/*      */       {
/* 1129 */         return 15;
/*      */       }
/*      */     } 
/*      */     
/* 1133 */     return -1;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void bindCustomTextures(CustomTexture[] cts) {
/* 1138 */     if (cts != null)
/*      */     {
/* 1140 */       for (int i = 0; i < cts.length; i++) {
/*      */         
/* 1142 */         CustomTexture customtexture = cts[i];
/* 1143 */         GlStateManager.setActiveTexture(33984 + customtexture.getTextureUnit());
/* 1144 */         ITextureObject itextureobject = customtexture.getTexture();
/* 1145 */         GlStateManager.bindTexture(itextureobject.getGlTextureId());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void resetCustomTextures() {
/* 1152 */     deleteCustomTextures(customTexturesGbuffers);
/* 1153 */     deleteCustomTextures(customTexturesComposite);
/* 1154 */     customTexturesGbuffers = null;
/* 1155 */     customTexturesComposite = null;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void deleteCustomTextures(CustomTexture[] cts) {
/* 1160 */     if (cts != null)
/*      */     {
/* 1162 */       for (int i = 0; i < cts.length; i++) {
/*      */         
/* 1164 */         CustomTexture customtexture = cts[i];
/* 1165 */         customtexture.deleteTexture();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShaderOption[] getShaderPackOptions(String screenName) {
/* 1172 */     ShaderOption[] ashaderoption = (ShaderOption[])shaderPackOptions.clone();
/*      */     
/* 1174 */     if (shaderPackGuiScreens == null) {
/*      */       
/* 1176 */       if (shaderPackProfiles != null) {
/*      */         
/* 1178 */         ShaderOptionProfile shaderoptionprofile = new ShaderOptionProfile(shaderPackProfiles, ashaderoption);
/* 1179 */         ashaderoption = (ShaderOption[])Config.addObjectToArray((Object[])ashaderoption, shaderoptionprofile, 0);
/*      */       } 
/*      */       
/* 1182 */       ashaderoption = getVisibleOptions(ashaderoption);
/* 1183 */       return ashaderoption;
/*      */     } 
/*      */ 
/*      */     
/* 1187 */     String s = (screenName != null) ? ("screen." + screenName) : "screen";
/* 1188 */     ScreenShaderOptions screenshaderoptions = shaderPackGuiScreens.get(s);
/*      */     
/* 1190 */     if (screenshaderoptions == null)
/*      */     {
/* 1192 */       return new ShaderOption[0];
/*      */     }
/*      */ 
/*      */     
/* 1196 */     ShaderOption[] ashaderoption1 = screenshaderoptions.getShaderOptions();
/* 1197 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1199 */     for (int i = 0; i < ashaderoption1.length; i++) {
/*      */       
/* 1201 */       ShaderOption shaderoption = ashaderoption1[i];
/*      */       
/* 1203 */       if (shaderoption == null) {
/*      */         
/* 1205 */         list.add(null);
/*      */       }
/* 1207 */       else if (shaderoption instanceof ShaderOptionRest) {
/*      */         
/* 1209 */         ShaderOption[] ashaderoption2 = getShaderOptionsRest(shaderPackGuiScreens, ashaderoption);
/* 1210 */         list.addAll(Arrays.asList(ashaderoption2));
/*      */       }
/*      */       else {
/*      */         
/* 1214 */         list.add(shaderoption);
/*      */       } 
/*      */     } 
/*      */     
/* 1218 */     ShaderOption[] ashaderoption3 = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/* 1219 */     return ashaderoption3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int getShaderPackColumns(String screenName, int def) {
/* 1226 */     String s = (screenName != null) ? ("screen." + screenName) : "screen";
/*      */     
/* 1228 */     if (shaderPackGuiScreens == null)
/*      */     {
/* 1230 */       return def;
/*      */     }
/*      */ 
/*      */     
/* 1234 */     ScreenShaderOptions screenshaderoptions = shaderPackGuiScreens.get(s);
/* 1235 */     return (screenshaderoptions == null) ? def : screenshaderoptions.getColumns();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ShaderOption[] getShaderOptionsRest(Map<String, ScreenShaderOptions> mapScreens, ShaderOption[] ops) {
/* 1241 */     Set<String> set = new HashSet<>();
/*      */     
/* 1243 */     for (String s : mapScreens.keySet()) {
/*      */       
/* 1245 */       ScreenShaderOptions screenshaderoptions = mapScreens.get(s);
/* 1246 */       ShaderOption[] ashaderoption = screenshaderoptions.getShaderOptions();
/*      */       
/* 1248 */       for (int i = 0; i < ashaderoption.length; i++) {
/*      */         
/* 1250 */         ShaderOption shaderoption = ashaderoption[i];
/*      */         
/* 1252 */         if (shaderoption != null)
/*      */         {
/* 1254 */           set.add(shaderoption.getName());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1259 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1261 */     for (int j = 0; j < ops.length; j++) {
/*      */       
/* 1263 */       ShaderOption shaderoption1 = ops[j];
/*      */       
/* 1265 */       if (shaderoption1.isVisible()) {
/*      */         
/* 1267 */         String s1 = shaderoption1.getName();
/*      */         
/* 1269 */         if (!set.contains(s1))
/*      */         {
/* 1271 */           list.add(shaderoption1);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1276 */     ShaderOption[] ashaderoption1 = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/* 1277 */     return ashaderoption1;
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShaderOption getShaderOption(String name) {
/* 1282 */     return ShaderUtils.getShaderOption(name, shaderPackOptions);
/*      */   }
/*      */ 
/*      */   
/*      */   public static ShaderOption[] getShaderPackOptions() {
/* 1287 */     return shaderPackOptions;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isShaderPackOptionSlider(String name) {
/* 1292 */     return (shaderPackOptionSliders == null) ? false : shaderPackOptionSliders.contains(name);
/*      */   }
/*      */ 
/*      */   
/*      */   private static ShaderOption[] getVisibleOptions(ShaderOption[] ops) {
/* 1297 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1299 */     for (int i = 0; i < ops.length; i++) {
/*      */       
/* 1301 */       ShaderOption shaderoption = ops[i];
/*      */       
/* 1303 */       if (shaderoption.isVisible())
/*      */       {
/* 1305 */         list.add(shaderoption);
/*      */       }
/*      */     } 
/*      */     
/* 1309 */     ShaderOption[] ashaderoption = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/* 1310 */     return ashaderoption;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void saveShaderPackOptions() {
/* 1315 */     saveShaderPackOptions(shaderPackOptions, shaderPack);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void saveShaderPackOptions(ShaderOption[] sos, IShaderPack sp) {
/* 1320 */     Properties properties = new Properties();
/*      */     
/* 1322 */     if (shaderPackOptions != null)
/*      */     {
/* 1324 */       for (int i = 0; i < sos.length; i++) {
/*      */         
/* 1326 */         ShaderOption shaderoption = sos[i];
/*      */         
/* 1328 */         if (shaderoption.isChanged() && shaderoption.isEnabled())
/*      */         {
/* 1330 */           properties.setProperty(shaderoption.getName(), shaderoption.getValue());
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     try {
/* 1337 */       saveOptionProperties(sp, properties);
/*      */     }
/* 1339 */     catch (IOException ioexception) {
/*      */       
/* 1341 */       Config.warn("[Shaders] Error saving configuration for " + shaderPack.getName());
/* 1342 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void saveOptionProperties(IShaderPack sp, Properties props) throws IOException {
/* 1348 */     String s = String.valueOf(shaderpacksdirname) + "/" + sp.getName() + ".txt";
/* 1349 */     File file1 = new File((Minecraft.getMinecraft()).mcDataDir, s);
/*      */     
/* 1351 */     if (props.isEmpty()) {
/*      */       
/* 1353 */       file1.delete();
/*      */     }
/*      */     else {
/*      */       
/* 1357 */       FileOutputStream fileoutputstream = new FileOutputStream(file1);
/* 1358 */       props.store(fileoutputstream, (String)null);
/* 1359 */       fileoutputstream.flush();
/* 1360 */       fileoutputstream.close();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ShaderOption[] loadShaderPackOptions() {
/*      */     try {
/* 1368 */       ShaderOption[] ashaderoption = ShaderPackParser.parseShaderPackOptions(shaderPack, programNames, shaderPackDimensions);
/* 1369 */       Properties properties = loadOptionProperties(shaderPack);
/*      */       
/* 1371 */       for (int i = 0; i < ashaderoption.length; i++) {
/*      */         
/* 1373 */         ShaderOption shaderoption = ashaderoption[i];
/* 1374 */         String s = properties.getProperty(shaderoption.getName());
/*      */         
/* 1376 */         if (s != null) {
/*      */           
/* 1378 */           shaderoption.resetValue();
/*      */           
/* 1380 */           if (!shaderoption.setValue(s))
/*      */           {
/* 1382 */             Config.warn("[Shaders] Invalid value, option: " + shaderoption.getName() + ", value: " + s);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1387 */       return ashaderoption;
/*      */     }
/* 1389 */     catch (IOException ioexception) {
/*      */       
/* 1391 */       Config.warn("[Shaders] Error reading configuration for " + shaderPack.getName());
/* 1392 */       ioexception.printStackTrace();
/* 1393 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Properties loadOptionProperties(IShaderPack sp) throws IOException {
/* 1399 */     Properties properties = new Properties();
/* 1400 */     String s = String.valueOf(shaderpacksdirname) + "/" + sp.getName() + ".txt";
/* 1401 */     File file1 = new File((Minecraft.getMinecraft()).mcDataDir, s);
/*      */     
/* 1403 */     if (file1.exists() && file1.isFile() && file1.canRead()) {
/*      */       
/* 1405 */       FileInputStream fileinputstream = new FileInputStream(file1);
/* 1406 */       properties.load(fileinputstream);
/* 1407 */       fileinputstream.close();
/* 1408 */       return properties;
/*      */     } 
/*      */ 
/*      */     
/* 1412 */     return properties;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static ShaderOption[] getChangedOptions(ShaderOption[] ops) {
/* 1418 */     List<ShaderOption> list = new ArrayList<>();
/*      */     
/* 1420 */     for (int i = 0; i < ops.length; i++) {
/*      */       
/* 1422 */       ShaderOption shaderoption = ops[i];
/*      */       
/* 1424 */       if (shaderoption.isEnabled() && shaderoption.isChanged())
/*      */       {
/* 1426 */         list.add(shaderoption);
/*      */       }
/*      */     } 
/*      */     
/* 1430 */     ShaderOption[] ashaderoption = list.<ShaderOption>toArray(new ShaderOption[list.size()]);
/* 1431 */     return ashaderoption;
/*      */   }
/*      */ 
/*      */   
/*      */   private static String applyOptions(String line, ShaderOption[] ops) {
/* 1436 */     if (ops != null && ops.length > 0) {
/*      */       
/* 1438 */       for (int i = 0; i < ops.length; i++) {
/*      */         
/* 1440 */         ShaderOption shaderoption = ops[i];
/* 1441 */         String s = shaderoption.getName();
/*      */         
/* 1443 */         if (shaderoption.matchesLine(line)) {
/*      */           
/* 1445 */           line = shaderoption.getSourceLine();
/*      */           
/*      */           break;
/*      */         } 
/*      */       } 
/* 1450 */       return line;
/*      */     } 
/*      */ 
/*      */     
/* 1454 */     return line;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static ArrayList listOfShaders() {
/* 1460 */     ArrayList<String> arraylist = new ArrayList<>();
/* 1461 */     arraylist.add(packNameNone);
/* 1462 */     arraylist.add(packNameDefault);
/*      */ 
/*      */     
/*      */     try {
/* 1466 */       if (!shaderpacksdir.exists())
/*      */       {
/* 1468 */         shaderpacksdir.mkdir();
/*      */       }
/*      */       
/* 1471 */       File[] afile = shaderpacksdir.listFiles();
/*      */       
/* 1473 */       for (int i = 0; i < afile.length; i++) {
/*      */         
/* 1475 */         File file1 = afile[i];
/* 1476 */         String s = file1.getName();
/*      */         
/* 1478 */         if (file1.isDirectory())
/*      */         {
/* 1480 */           File file2 = new File(file1, "shaders");
/*      */           
/* 1482 */           if (file2.exists() && file2.isDirectory())
/*      */           {
/* 1484 */             arraylist.add(s);
/*      */           }
/*      */         }
/* 1487 */         else if (file1.isFile() && s.toLowerCase().endsWith(".zip"))
/*      */         {
/* 1489 */           arraylist.add(s);
/*      */         }
/*      */       
/*      */       } 
/* 1493 */     } catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1498 */     return arraylist;
/*      */   }
/*      */ 
/*      */   
/*      */   static String versiontostring(int vv) {
/* 1503 */     String s = Integer.toString(vv);
/* 1504 */     return String.valueOf(Integer.toString(Integer.parseInt(s.substring(1, 3)))) + "." + Integer.toString(Integer.parseInt(s.substring(3, 5))) + "." + Integer.toString(Integer.parseInt(s.substring(5)));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkOptifine() {}
/*      */ 
/*      */   
/*      */   public static int checkFramebufferStatus(String location) {
/* 1513 */     int i = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */     
/* 1515 */     if (i != 36053)
/*      */     {
/* 1517 */       System.err.format("FramebufferStatus 0x%04X at %s\n", new Object[] { Integer.valueOf(i), location });
/*      */     }
/*      */     
/* 1520 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int checkGLError(String location) {
/* 1525 */     int i = GL11.glGetError();
/*      */     
/* 1527 */     if (i != 0) {
/*      */       
/* 1529 */       boolean flag = false;
/*      */       
/* 1531 */       if (!flag)
/*      */       {
/* 1533 */         if (i == 1286) {
/*      */           
/* 1535 */           int j = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/* 1536 */           System.err.format("GL error 0x%04X: %s (Fb status 0x%04X) at %s\n", new Object[] { Integer.valueOf(i), GLU.gluErrorString(i), Integer.valueOf(j), location });
/*      */         }
/*      */         else {
/*      */           
/* 1540 */           System.err.format("GL error 0x%04X: %s at %s\n", new Object[] { Integer.valueOf(i), GLU.gluErrorString(i), location });
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1545 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int checkGLError(String location, String info) {
/* 1550 */     int i = GL11.glGetError();
/*      */     
/* 1552 */     if (i != 0)
/*      */     {
/* 1554 */       System.err.format("GL error 0x%04x: %s at %s %s\n", new Object[] { Integer.valueOf(i), GLU.gluErrorString(i), location, info });
/*      */     }
/*      */     
/* 1557 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int checkGLError(String location, String info1, String info2) {
/* 1562 */     int i = GL11.glGetError();
/*      */     
/* 1564 */     if (i != 0)
/*      */     {
/* 1566 */       System.err.format("GL error 0x%04x: %s at %s %s %s\n", new Object[] { Integer.valueOf(i), GLU.gluErrorString(i), location, info1, info2 });
/*      */     }
/*      */     
/* 1569 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void printChat(String str) {
/* 1574 */     mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(str));
/*      */   }
/*      */ 
/*      */   
/*      */   private static void printChatAndLogError(String str) {
/* 1579 */     SMCLog.severe(str);
/* 1580 */     mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentString(str));
/*      */   }
/*      */ 
/*      */   
/*      */   public static void printIntBuffer(String title, IntBuffer buf) {
/* 1585 */     StringBuilder stringbuilder = new StringBuilder(128);
/* 1586 */     stringbuilder.append(title).append(" [pos ").append(buf.position()).append(" lim ").append(buf.limit()).append(" cap ").append(buf.capacity()).append(" :");
/* 1587 */     int i = buf.limit();
/*      */     
/* 1589 */     for (int j = 0; j < i; j++)
/*      */     {
/* 1591 */       stringbuilder.append(" ").append(buf.get(j));
/*      */     }
/*      */     
/* 1594 */     stringbuilder.append("]");
/* 1595 */     SMCLog.info(stringbuilder.toString());
/*      */   }
/*      */ 
/*      */   
/*      */   public static void startup(Minecraft mc) {
/* 1600 */     checkShadersModInstalled();
/* 1601 */     Shaders.mc = mc;
/* 1602 */     mc = Minecraft.getMinecraft();
/* 1603 */     capabilities = GLContext.getCapabilities();
/* 1604 */     glVersionString = GL11.glGetString(7938);
/* 1605 */     glVendorString = GL11.glGetString(7936);
/* 1606 */     glRendererString = GL11.glGetString(7937);
/* 1607 */     SMCLog.info("ShadersMod version: 2.4.12");
/* 1608 */     SMCLog.info("OpenGL Version: " + glVersionString);
/* 1609 */     SMCLog.info("Vendor:  " + glVendorString);
/* 1610 */     SMCLog.info("Renderer: " + glRendererString);
/* 1611 */     SMCLog.info("Capabilities: " + (capabilities.OpenGL20 ? " 2.0 " : " - ") + (capabilities.OpenGL21 ? " 2.1 " : " - ") + (capabilities.OpenGL30 ? " 3.0 " : " - ") + (capabilities.OpenGL32 ? " 3.2 " : " - ") + (capabilities.OpenGL40 ? " 4.0 " : " - "));
/* 1612 */     SMCLog.info("GL_MAX_DRAW_BUFFERS: " + GL11.glGetInteger(34852));
/* 1613 */     SMCLog.info("GL_MAX_COLOR_ATTACHMENTS_EXT: " + GL11.glGetInteger(36063));
/* 1614 */     SMCLog.info("GL_MAX_TEXTURE_IMAGE_UNITS: " + GL11.glGetInteger(34930));
/* 1615 */     hasGlGenMipmap = capabilities.OpenGL30;
/* 1616 */     loadConfig();
/*      */   }
/*      */ 
/*      */   
/*      */   private static String toStringYN(boolean b) {
/* 1621 */     return b ? "Y" : "N";
/*      */   }
/*      */ 
/*      */   
/*      */   public static void updateBlockLightLevel() {
/* 1626 */     if (isOldLighting()) {
/*      */       
/* 1628 */       blockLightLevel05 = 0.5F;
/* 1629 */       blockLightLevel06 = 0.6F;
/* 1630 */       blockLightLevel08 = 0.8F;
/*      */     }
/*      */     else {
/*      */       
/* 1634 */       blockLightLevel05 = 1.0F;
/* 1635 */       blockLightLevel06 = 1.0F;
/* 1636 */       blockLightLevel08 = 1.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isOldHandLight() {
/* 1642 */     if (!configOldHandLight.isDefault())
/*      */     {
/* 1644 */       return configOldHandLight.isTrue();
/*      */     }
/*      */ 
/*      */     
/* 1648 */     return !shaderPackOldHandLight.isDefault() ? shaderPackOldHandLight.isTrue() : true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDynamicHandLight() {
/* 1654 */     return !shaderPackDynamicHandLight.isDefault() ? shaderPackDynamicHandLight.isTrue() : true;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isOldLighting() {
/* 1659 */     if (!configOldLighting.isDefault())
/*      */     {
/* 1661 */       return configOldLighting.isTrue();
/*      */     }
/*      */ 
/*      */     
/* 1665 */     return !shaderPackOldLighting.isDefault() ? shaderPackOldLighting.isTrue() : true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRenderShadowTranslucent() {
/* 1671 */     return !shaderPackShadowTranslucent.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isUnderwaterOverlay() {
/* 1676 */     return !shaderPackUnderwaterOverlay.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isSun() {
/* 1681 */     return !shaderPackSun.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isMoon() {
/* 1686 */     return !shaderPackMoon.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isVignette() {
/* 1691 */     return !shaderPackVignette.isFalse();
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRenderBackFace(BlockRenderLayer blockLayerIn) {
/* 1696 */     switch (blockLayerIn) {
/*      */       
/*      */       case SOLID:
/* 1699 */         return shaderPackBackFaceSolid.isTrue();
/*      */       
/*      */       case null:
/* 1702 */         return shaderPackBackFaceCutout.isTrue();
/*      */       
/*      */       case CUTOUT_MIPPED:
/* 1705 */         return shaderPackBackFaceCutoutMipped.isTrue();
/*      */       
/*      */       case TRANSLUCENT:
/* 1708 */         return shaderPackBackFaceTranslucent.isTrue();
/*      */     } 
/*      */     
/* 1711 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void init() {
/*      */     boolean flag;
/* 1719 */     if (!isInitializedOnce) {
/*      */       
/* 1721 */       isInitializedOnce = true;
/* 1722 */       flag = true;
/*      */     }
/*      */     else {
/*      */       
/* 1726 */       flag = false;
/*      */     } 
/*      */     
/* 1729 */     if (!isShaderPackInitialized) {
/*      */       
/* 1731 */       checkGLError("Shaders.init pre");
/*      */       
/* 1733 */       if (getShaderPackName() != null);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1738 */       if (!capabilities.OpenGL20)
/*      */       {
/* 1740 */         printChatAndLogError("No OpenGL 2.0");
/*      */       }
/*      */       
/* 1743 */       if (!capabilities.GL_EXT_framebuffer_object)
/*      */       {
/* 1745 */         printChatAndLogError("No EXT_framebuffer_object");
/*      */       }
/*      */       
/* 1748 */       dfbDrawBuffers.position(0).limit(8);
/* 1749 */       dfbColorTextures.position(0).limit(16);
/* 1750 */       dfbDepthTextures.position(0).limit(3);
/* 1751 */       sfbDrawBuffers.position(0).limit(8);
/* 1752 */       sfbDepthTextures.position(0).limit(2);
/* 1753 */       sfbColorTextures.position(0).limit(8);
/* 1754 */       usedColorBuffers = 4;
/* 1755 */       usedDepthBuffers = 1;
/* 1756 */       usedShadowColorBuffers = 0;
/* 1757 */       usedShadowDepthBuffers = 0;
/* 1758 */       usedColorAttachs = 1;
/* 1759 */       usedDrawBuffers = 1;
/* 1760 */       Arrays.fill(gbuffersFormat, 6408);
/* 1761 */       Arrays.fill(gbuffersClear, true);
/* 1762 */       Arrays.fill(shadowHardwareFilteringEnabled, false);
/* 1763 */       Arrays.fill(shadowMipmapEnabled, false);
/* 1764 */       Arrays.fill(shadowFilterNearest, false);
/* 1765 */       Arrays.fill(shadowColorMipmapEnabled, false);
/* 1766 */       Arrays.fill(shadowColorFilterNearest, false);
/* 1767 */       centerDepthSmoothEnabled = false;
/* 1768 */       noiseTextureEnabled = false;
/* 1769 */       sunPathRotation = 0.0F;
/* 1770 */       shadowIntervalSize = 2.0F;
/* 1771 */       shadowDistanceRenderMul = -1.0F;
/* 1772 */       aoLevel = -1.0F;
/* 1773 */       useEntityAttrib = false;
/* 1774 */       useMidTexCoordAttrib = false;
/* 1775 */       useMultiTexCoord3Attrib = false;
/* 1776 */       useTangentAttrib = false;
/* 1777 */       waterShadowEnabled = false;
/* 1778 */       updateChunksErrorRecorded = false;
/* 1779 */       updateBlockLightLevel();
/* 1780 */       ShaderProfile shaderprofile = ShaderUtils.detectProfile(shaderPackProfiles, shaderPackOptions, false);
/* 1781 */       String s = "";
/*      */       
/* 1783 */       if (currentWorld != null) {
/*      */         
/* 1785 */         int i = currentWorld.provider.getDimensionType().getId();
/*      */         
/* 1787 */         if (shaderPackDimensions.contains(Integer.valueOf(i)))
/*      */         {
/* 1789 */           s = "world" + i + "/";
/*      */         }
/*      */       } 
/*      */       
/* 1793 */       if (saveFinalShaders)
/*      */       {
/* 1795 */         clearDirectory(new File(shaderpacksdir, "debug"));
/*      */       }
/*      */       
/* 1798 */       for (int k1 = 0; k1 < 33; k1++) {
/*      */         
/* 1800 */         String s1 = programNames[k1];
/*      */         
/* 1802 */         if (s1.equals("")) {
/*      */           
/* 1804 */           programsRef[k1] = 0; programsID[k1] = 0;
/* 1805 */           programsDrawBufSettings[k1] = null;
/* 1806 */           programsColorAtmSettings[k1] = null;
/* 1807 */           programsCompositeMipmapSetting[k1] = 0;
/*      */         }
/*      */         else {
/*      */           
/* 1811 */           newDrawBufSetting = null;
/* 1812 */           newColorAtmSetting = null;
/* 1813 */           newCompositeMipmapSetting = 0;
/* 1814 */           String s2 = String.valueOf(s) + s1;
/*      */           
/* 1816 */           if (shaderprofile != null && shaderprofile.isProgramDisabled(s2)) {
/*      */             
/* 1818 */             SMCLog.info("Program disabled: " + s2);
/* 1819 */             s1 = "<disabled>";
/* 1820 */             s2 = String.valueOf(s) + s1;
/*      */           } 
/*      */           
/* 1823 */           String s3 = "/shaders/" + s2;
/* 1824 */           int j = setupProgram(k1, String.valueOf(s3) + ".vsh", String.valueOf(s3) + ".fsh");
/*      */           
/* 1826 */           if (j > 0)
/*      */           {
/* 1828 */             SMCLog.info("Program loaded: " + s2);
/*      */           }
/*      */           
/* 1831 */           programsRef[k1] = j; programsID[k1] = j;
/* 1832 */           programsDrawBufSettings[k1] = (j != 0) ? newDrawBufSetting : null;
/* 1833 */           programsColorAtmSettings[k1] = (j != 0) ? newColorAtmSetting : null;
/* 1834 */           programsCompositeMipmapSetting[k1] = (j != 0) ? newCompositeMipmapSetting : 0;
/*      */         } 
/*      */       } 
/*      */       
/* 1838 */       int l1 = GL11.glGetInteger(34852);
/*      */ 
/*      */       
/* 1841 */       for (int i2 = 0; i2 < 33; i2++) {
/*      */         
/* 1843 */         Arrays.fill(programsToggleColorTextures[i2], false);
/*      */         
/* 1845 */         if (i2 == 29) {
/*      */           
/* 1847 */           programsDrawBuffers[i2] = null;
/*      */         }
/* 1849 */         else if (programsID[i2] == 0) {
/*      */           
/* 1851 */           if (i2 == 30)
/*      */           {
/* 1853 */             programsDrawBuffers[i2] = drawBuffersNone;
/*      */           }
/*      */           else
/*      */           {
/* 1857 */             programsDrawBuffers[i2] = drawBuffersColorAtt0;
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/* 1862 */           String s4 = programsDrawBufSettings[i2];
/*      */           
/* 1864 */           if (s4 != null) {
/*      */             
/* 1866 */             IntBuffer intbuffer = drawBuffersBuffer[i2];
/* 1867 */             int k = s4.length();
/*      */             
/* 1869 */             if (k > usedDrawBuffers)
/*      */             {
/* 1871 */               usedDrawBuffers = k;
/*      */             }
/*      */             
/* 1874 */             if (k > l1)
/*      */             {
/* 1876 */               k = l1;
/*      */             }
/*      */             
/* 1879 */             programsDrawBuffers[i2] = intbuffer;
/* 1880 */             intbuffer.limit(k);
/*      */             
/* 1882 */             for (int l = 0; l < k; l++)
/*      */             {
/* 1884 */               int i1 = 0;
/*      */               
/* 1886 */               if (s4.length() > l) {
/*      */                 
/* 1888 */                 int j1 = s4.charAt(l) - 48;
/*      */                 
/* 1890 */                 if (i2 != 30) {
/*      */                   
/* 1892 */                   if (j1 >= 0 && j1 <= 7)
/*      */                   {
/* 1894 */                     programsToggleColorTextures[i2][j1] = true;
/* 1895 */                     i1 = j1 + 36064;
/*      */                     
/* 1897 */                     if (j1 > usedColorAttachs)
/*      */                     {
/* 1899 */                       usedColorAttachs = j1;
/*      */                     }
/*      */                     
/* 1902 */                     if (j1 > usedColorBuffers)
/*      */                     {
/* 1904 */                       usedColorBuffers = j1;
/*      */                     }
/*      */                   }
/*      */                 
/* 1908 */                 } else if (j1 >= 0 && j1 <= 1) {
/*      */                   
/* 1910 */                   i1 = j1 + 36064;
/*      */                   
/* 1912 */                   if (j1 > usedShadowColorBuffers)
/*      */                   {
/* 1914 */                     usedShadowColorBuffers = j1;
/*      */                   }
/*      */                 } 
/*      */               } 
/*      */               
/* 1919 */               intbuffer.put(l, i1);
/*      */             }
/*      */           
/* 1922 */           } else if (i2 != 30 && i2 != 31 && i2 != 32) {
/*      */             
/* 1924 */             programsDrawBuffers[i2] = dfbDrawBuffers;
/* 1925 */             usedDrawBuffers = usedColorBuffers;
/* 1926 */             Arrays.fill(programsToggleColorTextures[i2], 0, usedColorBuffers, true);
/*      */           }
/*      */           else {
/*      */             
/* 1930 */             programsDrawBuffers[i2] = sfbDrawBuffers;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1935 */       usedColorAttachs = usedColorBuffers;
/* 1936 */       shadowPassInterval = (usedShadowDepthBuffers > 0) ? 1 : 0;
/* 1937 */       shouldSkipDefaultShadow = (usedShadowDepthBuffers > 0);
/* 1938 */       SMCLog.info("usedColorBuffers: " + usedColorBuffers);
/* 1939 */       SMCLog.info("usedDepthBuffers: " + usedDepthBuffers);
/* 1940 */       SMCLog.info("usedShadowColorBuffers: " + usedShadowColorBuffers);
/* 1941 */       SMCLog.info("usedShadowDepthBuffers: " + usedShadowDepthBuffers);
/* 1942 */       SMCLog.info("usedColorAttachs: " + usedColorAttachs);
/* 1943 */       SMCLog.info("usedDrawBuffers: " + usedDrawBuffers);
/* 1944 */       dfbDrawBuffers.position(0).limit(usedDrawBuffers);
/* 1945 */       dfbColorTextures.position(0).limit(usedColorBuffers * 2);
/*      */       
/* 1947 */       for (int j2 = 0; j2 < usedDrawBuffers; j2++)
/*      */       {
/* 1949 */         dfbDrawBuffers.put(j2, 36064 + j2);
/*      */       }
/*      */       
/* 1952 */       if (usedDrawBuffers > l1)
/*      */       {
/* 1954 */         printChatAndLogError("[Shaders] Error: Not enough draw buffers, needed: " + usedDrawBuffers + ", available: " + l1);
/*      */       }
/*      */       
/* 1957 */       sfbDrawBuffers.position(0).limit(usedShadowColorBuffers);
/*      */       
/* 1959 */       for (int k2 = 0; k2 < usedShadowColorBuffers; k2++)
/*      */       {
/* 1961 */         sfbDrawBuffers.put(k2, 36064 + k2);
/*      */       }
/*      */       
/* 1964 */       for (int l2 = 0; l2 < 33; l2++) {
/*      */         int i3;
/*      */ 
/*      */         
/* 1968 */         for (i3 = l2; programsID[i3] == 0 && programBackups[i3] != i3; i3 = programBackups[i3]);
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1973 */         if (i3 != l2 && l2 != 30) {
/*      */           
/* 1975 */           programsID[l2] = programsID[i3];
/* 1976 */           programsDrawBufSettings[l2] = programsDrawBufSettings[i3];
/* 1977 */           programsDrawBuffers[l2] = programsDrawBuffers[i3];
/*      */         } 
/*      */       } 
/*      */       
/* 1981 */       resize();
/* 1982 */       resizeShadow();
/*      */       
/* 1984 */       if (noiseTextureEnabled)
/*      */       {
/* 1986 */         setupNoiseTexture();
/*      */       }
/*      */       
/* 1989 */       if (defaultTexture == null)
/*      */       {
/* 1991 */         defaultTexture = ShadersTex.createDefaultTexture();
/*      */       }
/*      */       
/* 1994 */       GlStateManager.pushMatrix();
/* 1995 */       GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/* 1996 */       preCelestialRotate();
/* 1997 */       postCelestialRotate();
/* 1998 */       GlStateManager.popMatrix();
/* 1999 */       isShaderPackInitialized = true;
/* 2000 */       loadEntityDataMap();
/* 2001 */       resetDisplayList();
/*      */       
/* 2003 */       if (!flag);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2008 */       checkGLError("Shaders.init");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resetDisplayList() {
/* 2014 */     numberResetDisplayList++;
/* 2015 */     needResetModels = true;
/* 2016 */     SMCLog.info("Reset world renderers");
/* 2017 */     mc.renderGlobal.loadRenderers();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resetDisplayListModels() {
/* 2022 */     if (needResetModels) {
/*      */       
/* 2024 */       needResetModels = false;
/* 2025 */       SMCLog.info("Reset model renderers");
/*      */       
/* 2027 */       for (Render render : mc.getRenderManager().getEntityRenderMap().values()) {
/*      */         
/* 2029 */         if (render instanceof RenderLiving) {
/*      */           
/* 2031 */           RenderLiving renderliving = (RenderLiving)render;
/* 2032 */           resetDisplayListModel(renderliving.getMainModel());
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resetDisplayListModel(ModelBase model) {
/* 2040 */     if (model != null)
/*      */     {
/* 2042 */       for (Object object : model.boxList) {
/*      */         
/* 2044 */         if (object instanceof ModelRenderer)
/*      */         {
/* 2046 */           resetDisplayListModelRenderer((ModelRenderer)object);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resetDisplayListModelRenderer(ModelRenderer mrr) {
/* 2054 */     mrr.resetDisplayList();
/*      */     
/* 2056 */     if (mrr.childModels != null) {
/*      */       
/* 2058 */       int i = 0;
/*      */       
/* 2060 */       for (int j = mrr.childModels.size(); i < j; i++)
/*      */       {
/* 2062 */         resetDisplayListModelRenderer(mrr.childModels.get(i));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int setupProgram(int program, String vShaderPath, String fShaderPath) {
/* 2069 */     checkGLError("pre setupProgram");
/* 2070 */     int i = ARBShaderObjects.glCreateProgramObjectARB();
/* 2071 */     checkGLError("create");
/*      */     
/* 2073 */     if (i != 0) {
/*      */       
/* 2075 */       progUseEntityAttrib = false;
/* 2076 */       progUseMidTexCoordAttrib = false;
/* 2077 */       progUseTangentAttrib = false;
/* 2078 */       int j = createVertShader(vShaderPath);
/* 2079 */       int k = createFragShader(fShaderPath);
/* 2080 */       checkGLError("create");
/*      */       
/* 2082 */       if (j == 0 && k == 0) {
/*      */         
/* 2084 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 2085 */         i = 0;
/*      */       }
/*      */       else {
/*      */         
/* 2089 */         if (j != 0) {
/*      */           
/* 2091 */           ARBShaderObjects.glAttachObjectARB(i, j);
/* 2092 */           checkGLError("attach");
/*      */         } 
/*      */         
/* 2095 */         if (k != 0) {
/*      */           
/* 2097 */           ARBShaderObjects.glAttachObjectARB(i, k);
/* 2098 */           checkGLError("attach");
/*      */         } 
/*      */         
/* 2101 */         if (progUseEntityAttrib) {
/*      */           
/* 2103 */           ARBVertexShader.glBindAttribLocationARB(i, entityAttrib, "mc_Entity");
/* 2104 */           checkGLError("mc_Entity");
/*      */         } 
/*      */         
/* 2107 */         if (progUseMidTexCoordAttrib) {
/*      */           
/* 2109 */           ARBVertexShader.glBindAttribLocationARB(i, midTexCoordAttrib, "mc_midTexCoord");
/* 2110 */           checkGLError("mc_midTexCoord");
/*      */         } 
/*      */         
/* 2113 */         if (progUseTangentAttrib) {
/*      */           
/* 2115 */           ARBVertexShader.glBindAttribLocationARB(i, tangentAttrib, "at_tangent");
/* 2116 */           checkGLError("at_tangent");
/*      */         } 
/*      */         
/* 2119 */         ARBShaderObjects.glLinkProgramARB(i);
/*      */         
/* 2121 */         if (GL20.glGetProgrami(i, 35714) != 1)
/*      */         {
/* 2123 */           SMCLog.severe("Error linking program: " + i);
/*      */         }
/*      */         
/* 2126 */         printLogInfo(i, String.valueOf(vShaderPath) + ", " + fShaderPath);
/*      */         
/* 2128 */         if (j != 0) {
/*      */           
/* 2130 */           ARBShaderObjects.glDetachObjectARB(i, j);
/* 2131 */           ARBShaderObjects.glDeleteObjectARB(j);
/*      */         } 
/*      */         
/* 2134 */         if (k != 0) {
/*      */           
/* 2136 */           ARBShaderObjects.glDetachObjectARB(i, k);
/* 2137 */           ARBShaderObjects.glDeleteObjectARB(k);
/*      */         } 
/*      */         
/* 2140 */         programsID[program] = i;
/* 2141 */         useProgram(program);
/* 2142 */         ARBShaderObjects.glValidateProgramARB(i);
/* 2143 */         useProgram(0);
/* 2144 */         printLogInfo(i, String.valueOf(vShaderPath) + ", " + fShaderPath);
/* 2145 */         int l = GL20.glGetProgrami(i, 35715);
/*      */         
/* 2147 */         if (l != 1) {
/*      */           
/* 2149 */           String s = "\"";
/* 2150 */           printChatAndLogError("[Shaders] Error: Invalid program " + s + programNames[program] + s);
/* 2151 */           ARBShaderObjects.glDeleteObjectARB(i);
/* 2152 */           i = 0;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2157 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   private static int createVertShader(String filename) {
/* 2162 */     int i = ARBShaderObjects.glCreateShaderObjectARB(35633);
/*      */     
/* 2164 */     if (i == 0)
/*      */     {
/* 2166 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 2170 */     StringBuilder stringbuilder = new StringBuilder(131072);
/* 2171 */     BufferedReader bufferedreader = null;
/*      */ 
/*      */     
/*      */     try {
/* 2175 */       bufferedreader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream(filename)));
/*      */     }
/* 2177 */     catch (Exception var7) {
/*      */       
/* 2179 */       ARBShaderObjects.glDeleteObjectARB(i);
/* 2180 */       return 0;
/*      */     } 
/*      */     
/* 2183 */     ShaderOption[] ashaderoption = getChangedOptions(shaderPackOptions);
/* 2184 */     List<String> list = new ArrayList<>();
/*      */     
/* 2186 */     if (bufferedreader != null) {
/*      */       
/*      */       try {
/*      */         
/* 2190 */         bufferedreader = ShaderPackParser.resolveIncludes(bufferedreader, filename, shaderPack, 0, list, 0);
/*      */ 
/*      */         
/*      */         while (true) {
/* 2194 */           String s = bufferedreader.readLine();
/*      */           
/* 2196 */           if (s == null) {
/*      */             
/* 2198 */             bufferedreader.close();
/*      */             
/*      */             break;
/*      */           } 
/* 2202 */           s = applyOptions(s, ashaderoption);
/* 2203 */           stringbuilder.append(s).append('\n');
/*      */           
/* 2205 */           if (s.matches("attribute [_a-zA-Z0-9]+ mc_Entity.*")) {
/*      */             
/* 2207 */             useEntityAttrib = true;
/* 2208 */             progUseEntityAttrib = true; continue;
/*      */           } 
/* 2210 */           if (s.matches("attribute [_a-zA-Z0-9]+ mc_midTexCoord.*")) {
/*      */             
/* 2212 */             useMidTexCoordAttrib = true;
/* 2213 */             progUseMidTexCoordAttrib = true; continue;
/*      */           } 
/* 2215 */           if (s.matches(".*gl_MultiTexCoord3.*")) {
/*      */             
/* 2217 */             useMultiTexCoord3Attrib = true; continue;
/*      */           } 
/* 2219 */           if (s.matches("attribute [_a-zA-Z0-9]+ at_tangent.*"))
/*      */           {
/* 2221 */             useTangentAttrib = true;
/* 2222 */             progUseTangentAttrib = true;
/*      */           }
/*      */         
/*      */         } 
/* 2226 */       } catch (Exception exception) {
/*      */         
/* 2228 */         SMCLog.severe("Couldn't read " + filename + "!");
/* 2229 */         exception.printStackTrace();
/* 2230 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 2231 */         return 0;
/*      */       } 
/*      */     }
/*      */     
/* 2235 */     if (saveFinalShaders)
/*      */     {
/* 2237 */       saveShader(filename, stringbuilder.toString());
/*      */     }
/*      */     
/* 2240 */     ARBShaderObjects.glShaderSourceARB(i, stringbuilder);
/* 2241 */     ARBShaderObjects.glCompileShaderARB(i);
/*      */     
/* 2243 */     if (GL20.glGetShaderi(i, 35713) != 1)
/*      */     {
/* 2245 */       SMCLog.severe("Error compiling vertex shader: " + filename);
/*      */     }
/*      */     
/* 2248 */     printShaderLogInfo(i, filename, list);
/* 2249 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int createFragShader(String filename) {
/* 2255 */     int i = ARBShaderObjects.glCreateShaderObjectARB(35632);
/*      */     
/* 2257 */     if (i == 0)
/*      */     {
/* 2259 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 2263 */     StringBuilder stringbuilder = new StringBuilder(131072);
/* 2264 */     BufferedReader bufferedreader = null;
/*      */ 
/*      */     
/*      */     try {
/* 2268 */       bufferedreader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream(filename)));
/*      */     }
/* 2270 */     catch (Exception var12) {
/*      */       
/* 2272 */       ARBShaderObjects.glDeleteObjectARB(i);
/* 2273 */       return 0;
/*      */     } 
/*      */     
/* 2276 */     ShaderOption[] ashaderoption = getChangedOptions(shaderPackOptions);
/* 2277 */     List<String> list = new ArrayList<>();
/*      */     
/* 2279 */     if (bufferedreader != null) {
/*      */       
/*      */       try {
/*      */         
/* 2283 */         bufferedreader = ShaderPackParser.resolveIncludes(bufferedreader, filename, shaderPack, 0, list, 0);
/*      */ 
/*      */         
/*      */         while (true) {
/* 2287 */           String s = bufferedreader.readLine();
/*      */           
/* 2289 */           if (s == null) {
/*      */             
/* 2291 */             bufferedreader.close();
/*      */             
/*      */             break;
/*      */           } 
/* 2295 */           s = applyOptions(s, ashaderoption);
/* 2296 */           stringbuilder.append(s).append('\n');
/* 2297 */           ShaderLine shaderline = ShaderParser.parseLine(s);
/*      */           
/* 2299 */           if (shaderline != null)
/*      */           {
/* 2301 */             if (shaderline.isUniform()) {
/*      */               
/* 2303 */               String s5 = shaderline.getName();
/*      */               
/*      */               int k1;
/* 2306 */               if ((k1 = ShaderParser.getShadowDepthIndex(s5)) >= 0) {
/*      */                 
/* 2308 */                 usedShadowDepthBuffers = Math.max(usedShadowDepthBuffers, k1 + 1); continue;
/*      */               } 
/* 2310 */               if ((k1 = ShaderParser.getShadowColorIndex(s5)) >= 0) {
/*      */                 
/* 2312 */                 usedShadowColorBuffers = Math.max(usedShadowColorBuffers, k1 + 1); continue;
/*      */               } 
/* 2314 */               if ((k1 = ShaderParser.getDepthIndex(s5)) >= 0) {
/*      */                 
/* 2316 */                 usedDepthBuffers = Math.max(usedDepthBuffers, k1 + 1); continue;
/*      */               } 
/* 2318 */               if (s5.equals("gdepth") && gbuffersFormat[1] == 6408) {
/*      */                 
/* 2320 */                 gbuffersFormat[1] = 34836; continue;
/*      */               } 
/* 2322 */               if ((k1 = ShaderParser.getColorIndex(s5)) >= 0) {
/*      */                 
/* 2324 */                 usedColorBuffers = Math.max(usedColorBuffers, k1 + 1); continue;
/*      */               } 
/* 2326 */               if (s5.equals("centerDepthSmooth"))
/*      */               {
/* 2328 */                 centerDepthSmoothEnabled = true; } 
/*      */               continue;
/*      */             } 
/* 2331 */             if (!shaderline.isConstInt("shadowMapResolution") && !shaderline.isComment("SHADOWRES")) {
/*      */               
/* 2333 */               if (!shaderline.isConstFloat("shadowMapFov") && !shaderline.isComment("SHADOWFOV")) {
/*      */                 
/* 2335 */                 if (!shaderline.isConstFloat("shadowDistance") && !shaderline.isComment("SHADOWHPL")) {
/*      */                   
/* 2337 */                   if (shaderline.isConstFloat("shadowDistanceRenderMul")) {
/*      */                     
/* 2339 */                     shadowDistanceRenderMul = shaderline.getValueFloat();
/* 2340 */                     SMCLog.info("Shadow distance render mul: " + shadowDistanceRenderMul); continue;
/*      */                   } 
/* 2342 */                   if (shaderline.isConstFloat("shadowIntervalSize")) {
/*      */                     
/* 2344 */                     shadowIntervalSize = shaderline.getValueFloat();
/* 2345 */                     SMCLog.info("Shadow map interval size: " + shadowIntervalSize); continue;
/*      */                   } 
/* 2347 */                   if (shaderline.isConstBool("generateShadowMipmap", true)) {
/*      */                     
/* 2349 */                     Arrays.fill(shadowMipmapEnabled, true);
/* 2350 */                     SMCLog.info("Generate shadow mipmap"); continue;
/*      */                   } 
/* 2352 */                   if (shaderline.isConstBool("generateShadowColorMipmap", true)) {
/*      */                     
/* 2354 */                     Arrays.fill(shadowColorMipmapEnabled, true);
/* 2355 */                     SMCLog.info("Generate shadow color mipmap"); continue;
/*      */                   } 
/* 2357 */                   if (shaderline.isConstBool("shadowHardwareFiltering", true)) {
/*      */                     
/* 2359 */                     Arrays.fill(shadowHardwareFilteringEnabled, true);
/* 2360 */                     SMCLog.info("Hardware shadow filtering enabled."); continue;
/*      */                   } 
/* 2362 */                   if (shaderline.isConstBool("shadowHardwareFiltering0", true)) {
/*      */                     
/* 2364 */                     shadowHardwareFilteringEnabled[0] = true;
/* 2365 */                     SMCLog.info("shadowHardwareFiltering0"); continue;
/*      */                   } 
/* 2367 */                   if (shaderline.isConstBool("shadowHardwareFiltering1", true)) {
/*      */                     
/* 2369 */                     shadowHardwareFilteringEnabled[1] = true;
/* 2370 */                     SMCLog.info("shadowHardwareFiltering1"); continue;
/*      */                   } 
/* 2372 */                   if (shaderline.isConstBool("shadowtex0Mipmap", "shadowtexMipmap", true)) {
/*      */                     
/* 2374 */                     shadowMipmapEnabled[0] = true;
/* 2375 */                     SMCLog.info("shadowtex0Mipmap"); continue;
/*      */                   } 
/* 2377 */                   if (shaderline.isConstBool("shadowtex1Mipmap", true)) {
/*      */                     
/* 2379 */                     shadowMipmapEnabled[1] = true;
/* 2380 */                     SMCLog.info("shadowtex1Mipmap"); continue;
/*      */                   } 
/* 2382 */                   if (shaderline.isConstBool("shadowcolor0Mipmap", "shadowColor0Mipmap", true)) {
/*      */                     
/* 2384 */                     shadowColorMipmapEnabled[0] = true;
/* 2385 */                     SMCLog.info("shadowcolor0Mipmap"); continue;
/*      */                   } 
/* 2387 */                   if (shaderline.isConstBool("shadowcolor1Mipmap", "shadowColor1Mipmap", true)) {
/*      */                     
/* 2389 */                     shadowColorMipmapEnabled[1] = true;
/* 2390 */                     SMCLog.info("shadowcolor1Mipmap"); continue;
/*      */                   } 
/* 2392 */                   if (shaderline.isConstBool("shadowtex0Nearest", "shadowtexNearest", "shadow0MinMagNearest", true)) {
/*      */                     
/* 2394 */                     shadowFilterNearest[0] = true;
/* 2395 */                     SMCLog.info("shadowtex0Nearest"); continue;
/*      */                   } 
/* 2397 */                   if (shaderline.isConstBool("shadowtex1Nearest", "shadow1MinMagNearest", true)) {
/*      */                     
/* 2399 */                     shadowFilterNearest[1] = true;
/* 2400 */                     SMCLog.info("shadowtex1Nearest"); continue;
/*      */                   } 
/* 2402 */                   if (shaderline.isConstBool("shadowcolor0Nearest", "shadowColor0Nearest", "shadowColor0MinMagNearest", true)) {
/*      */                     
/* 2404 */                     shadowColorFilterNearest[0] = true;
/* 2405 */                     SMCLog.info("shadowcolor0Nearest"); continue;
/*      */                   } 
/* 2407 */                   if (shaderline.isConstBool("shadowcolor1Nearest", "shadowColor1Nearest", "shadowColor1MinMagNearest", true)) {
/*      */                     
/* 2409 */                     shadowColorFilterNearest[1] = true;
/* 2410 */                     SMCLog.info("shadowcolor1Nearest"); continue;
/*      */                   } 
/* 2412 */                   if (!shaderline.isConstFloat("wetnessHalflife") && !shaderline.isComment("WETNESSHL")) {
/*      */                     
/* 2414 */                     if (!shaderline.isConstFloat("drynessHalflife") && !shaderline.isComment("DRYNESSHL")) {
/*      */                       
/* 2416 */                       if (shaderline.isConstFloat("eyeBrightnessHalflife")) {
/*      */                         
/* 2418 */                         eyeBrightnessHalflife = shaderline.getValueFloat();
/* 2419 */                         SMCLog.info("Eye brightness halflife: " + eyeBrightnessHalflife); continue;
/*      */                       } 
/* 2421 */                       if (shaderline.isConstFloat("centerDepthHalflife")) {
/*      */                         
/* 2423 */                         centerDepthSmoothHalflife = shaderline.getValueFloat();
/* 2424 */                         SMCLog.info("Center depth halflife: " + centerDepthSmoothHalflife); continue;
/*      */                       } 
/* 2426 */                       if (shaderline.isConstFloat("sunPathRotation")) {
/*      */                         
/* 2428 */                         sunPathRotation = shaderline.getValueFloat();
/* 2429 */                         SMCLog.info("Sun path rotation: " + sunPathRotation); continue;
/*      */                       } 
/* 2431 */                       if (shaderline.isConstFloat("ambientOcclusionLevel")) {
/*      */                         
/* 2433 */                         aoLevel = Config.limit(shaderline.getValueFloat(), 0.0F, 1.0F);
/* 2434 */                         SMCLog.info("AO Level: " + aoLevel); continue;
/*      */                       } 
/* 2436 */                       if (shaderline.isConstInt("superSamplingLevel")) {
/*      */                         
/* 2438 */                         int i1 = shaderline.getValueInt();
/*      */                         
/* 2440 */                         if (i1 > 1) {
/*      */                           
/* 2442 */                           SMCLog.info("Super sampling level: " + i1 + "x");
/* 2443 */                           superSamplingLevel = i1;
/*      */                           
/*      */                           continue;
/*      */                         } 
/* 2447 */                         superSamplingLevel = 1;
/*      */                         continue;
/*      */                       } 
/* 2450 */                       if (shaderline.isConstInt("noiseTextureResolution")) {
/*      */                         
/* 2452 */                         noiseTextureResolution = shaderline.getValueInt();
/* 2453 */                         noiseTextureEnabled = true;
/* 2454 */                         SMCLog.info("Noise texture enabled");
/* 2455 */                         SMCLog.info("Noise texture resolution: " + noiseTextureResolution); continue;
/*      */                       } 
/* 2457 */                       if (shaderline.isConstIntSuffix("Format")) {
/*      */                         
/* 2459 */                         String s4 = StrUtils.removeSuffix(shaderline.getName(), "Format");
/* 2460 */                         String s6 = shaderline.getValue();
/* 2461 */                         int k = getBufferIndexFromString(s4);
/* 2462 */                         int l = getTextureFormatFromString(s6);
/*      */                         
/* 2464 */                         if (k >= 0 && l != 0) {
/*      */                           
/* 2466 */                           gbuffersFormat[k] = l;
/* 2467 */                           SMCLog.info("%s format: %s", new Object[] { s4, s6 });
/*      */                         }  continue;
/*      */                       } 
/* 2470 */                       if (shaderline.isConstBoolSuffix("Clear", false)) {
/*      */                         
/* 2472 */                         if (ShaderParser.isComposite(filename)) {
/*      */                           
/* 2474 */                           String s3 = StrUtils.removeSuffix(shaderline.getName(), "Clear");
/* 2475 */                           int j1 = getBufferIndexFromString(s3);
/*      */                           
/* 2477 */                           if (j1 >= 0) {
/*      */                             
/* 2479 */                             gbuffersClear[j1] = false;
/* 2480 */                             SMCLog.info("%s clear disabled", new Object[] { s3 });
/*      */                           } 
/*      */                         }  continue;
/*      */                       } 
/* 2484 */                       if (shaderline.isComment("GAUX4FORMAT", "RGBA32F")) {
/*      */                         
/* 2486 */                         gbuffersFormat[7] = 34836;
/* 2487 */                         SMCLog.info("gaux4 format : RGB32AF"); continue;
/*      */                       } 
/* 2489 */                       if (shaderline.isComment("GAUX4FORMAT", "RGB32F")) {
/*      */                         
/* 2491 */                         gbuffersFormat[7] = 34837;
/* 2492 */                         SMCLog.info("gaux4 format : RGB32F"); continue;
/*      */                       } 
/* 2494 */                       if (shaderline.isComment("GAUX4FORMAT", "RGB16")) {
/*      */                         
/* 2496 */                         gbuffersFormat[7] = 32852;
/* 2497 */                         SMCLog.info("gaux4 format : RGB16"); continue;
/*      */                       } 
/* 2499 */                       if (shaderline.isConstBoolSuffix("MipmapEnabled", true)) {
/*      */                         
/* 2501 */                         if (ShaderParser.isComposite(filename) || ShaderParser.isFinal(filename)) {
/*      */                           
/* 2503 */                           String s2 = StrUtils.removeSuffix(shaderline.getName(), "MipmapEnabled");
/* 2504 */                           int j = getBufferIndexFromString(s2);
/*      */                           
/* 2506 */                           if (j >= 0) {
/*      */                             
/* 2508 */                             newCompositeMipmapSetting |= 1 << j;
/* 2509 */                             SMCLog.info("%s mipmap enabled", new Object[] { s2 });
/*      */                           } 
/*      */                         }  continue;
/*      */                       } 
/* 2513 */                       if (shaderline.isComment("DRAWBUFFERS")) {
/*      */                         
/* 2515 */                         String s1 = shaderline.getValue();
/*      */                         
/* 2517 */                         if (ShaderParser.isValidDrawBuffers(s1)) {
/*      */                           
/* 2519 */                           newDrawBufSetting = s1;
/*      */                           
/*      */                           continue;
/*      */                         } 
/* 2523 */                         SMCLog.warning("Invalid draw buffers: " + s1);
/*      */                       } 
/*      */                       
/*      */                       continue;
/*      */                     } 
/*      */                     
/* 2529 */                     drynessHalfLife = shaderline.getValueFloat();
/* 2530 */                     SMCLog.info("Dryness halflife: " + drynessHalfLife);
/*      */                     
/*      */                     continue;
/*      */                   } 
/*      */                   
/* 2535 */                   wetnessHalfLife = shaderline.getValueFloat();
/* 2536 */                   SMCLog.info("Wetness halflife: " + wetnessHalfLife);
/*      */                   
/*      */                   continue;
/*      */                 } 
/*      */                 
/* 2541 */                 shadowMapHalfPlane = shaderline.getValueFloat();
/* 2542 */                 shadowMapIsOrtho = true;
/* 2543 */                 SMCLog.info("Shadow map distance: " + shadowMapHalfPlane);
/*      */                 
/*      */                 continue;
/*      */               } 
/*      */               
/* 2548 */               shadowMapFOV = shaderline.getValueFloat();
/* 2549 */               shadowMapIsOrtho = false;
/* 2550 */               SMCLog.info("Shadow map field of view: " + shadowMapFOV);
/*      */               
/*      */               continue;
/*      */             } 
/*      */             
/* 2555 */             spShadowMapWidth = spShadowMapHeight = shaderline.getValueInt();
/* 2556 */             shadowMapWidth = shadowMapHeight = Math.round(spShadowMapWidth * configShadowResMul);
/* 2557 */             SMCLog.info("Shadow map resolution: " + spShadowMapWidth);
/*      */           }
/*      */         
/*      */         }
/*      */       
/* 2562 */       } catch (Exception exception) {
/*      */         
/* 2564 */         SMCLog.severe("Couldn't read " + filename + "!");
/* 2565 */         exception.printStackTrace();
/* 2566 */         ARBShaderObjects.glDeleteObjectARB(i);
/* 2567 */         return 0;
/*      */       } 
/*      */     }
/*      */     
/* 2571 */     if (saveFinalShaders)
/*      */     {
/* 2573 */       saveShader(filename, stringbuilder.toString());
/*      */     }
/*      */     
/* 2576 */     ARBShaderObjects.glShaderSourceARB(i, stringbuilder);
/* 2577 */     ARBShaderObjects.glCompileShaderARB(i);
/*      */     
/* 2579 */     if (GL20.glGetShaderi(i, 35713) != 1)
/*      */     {
/* 2581 */       SMCLog.severe("Error compiling fragment shader: " + filename);
/*      */     }
/*      */     
/* 2584 */     printShaderLogInfo(i, filename, list);
/* 2585 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void saveShader(String filename, String code) {
/*      */     try {
/* 2593 */       File file1 = new File(shaderpacksdir, "debug/" + filename);
/* 2594 */       file1.getParentFile().mkdirs();
/* 2595 */       Config.writeFile(file1, code);
/*      */     }
/* 2597 */     catch (IOException ioexception) {
/*      */       
/* 2599 */       Config.warn("Error saving: " + filename);
/* 2600 */       ioexception.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void clearDirectory(File dir) {
/* 2606 */     if (dir.exists())
/*      */     {
/* 2608 */       if (dir.isDirectory()) {
/*      */         
/* 2610 */         File[] afile = dir.listFiles();
/*      */         
/* 2612 */         if (afile != null)
/*      */         {
/* 2614 */           for (int i = 0; i < afile.length; i++) {
/*      */             
/* 2616 */             File file1 = afile[i];
/*      */             
/* 2618 */             if (file1.isDirectory())
/*      */             {
/* 2620 */               clearDirectory(file1);
/*      */             }
/*      */             
/* 2623 */             file1.delete();
/*      */           } 
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean printLogInfo(int obj, String name) {
/* 2632 */     IntBuffer intbuffer = BufferUtils.createIntBuffer(1);
/* 2633 */     ARBShaderObjects.glGetObjectParameterARB(obj, 35716, intbuffer);
/* 2634 */     int i = intbuffer.get();
/*      */     
/* 2636 */     if (i > 1) {
/*      */       
/* 2638 */       ByteBuffer bytebuffer = BufferUtils.createByteBuffer(i);
/* 2639 */       intbuffer.flip();
/* 2640 */       ARBShaderObjects.glGetInfoLogARB(obj, intbuffer, bytebuffer);
/* 2641 */       byte[] abyte = new byte[i];
/* 2642 */       bytebuffer.get(abyte);
/*      */       
/* 2644 */       if (abyte[i - 1] == 0)
/*      */       {
/* 2646 */         abyte[i - 1] = 10;
/*      */       }
/*      */       
/* 2649 */       String s = new String(abyte);
/* 2650 */       SMCLog.info("Info log: " + name + "\n" + s);
/* 2651 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 2655 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean printShaderLogInfo(int shader, String name, List<String> listFiles) {
/* 2661 */     IntBuffer intbuffer = BufferUtils.createIntBuffer(1);
/* 2662 */     int i = GL20.glGetShaderi(shader, 35716);
/*      */     
/* 2664 */     if (i <= 1)
/*      */     {
/* 2666 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 2670 */     for (int j = 0; j < listFiles.size(); j++) {
/*      */       
/* 2672 */       String s = listFiles.get(j);
/* 2673 */       SMCLog.info("File: " + (j + 1) + " = " + s);
/*      */     } 
/*      */     
/* 2676 */     String s1 = GL20.glGetShaderInfoLog(shader, i);
/* 2677 */     SMCLog.info("Shader info log: " + name + "\n" + s1);
/* 2678 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setDrawBuffers(IntBuffer drawBuffers) {
/* 2684 */     if (drawBuffers == null)
/*      */     {
/* 2686 */       drawBuffers = drawBuffersNone;
/*      */     }
/*      */     
/* 2689 */     if (activeDrawBuffers != drawBuffers) {
/*      */       
/* 2691 */       activeDrawBuffers = drawBuffers;
/* 2692 */       GL20.glDrawBuffers(drawBuffers);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void useProgram(int program) {
/* 2698 */     checkGLError("pre-useProgram");
/*      */     
/* 2700 */     if (isShadowPass) {
/*      */       
/* 2702 */       program = 30;
/*      */       
/* 2704 */       if (programsID[30] == 0) {
/*      */         
/* 2706 */         normalMapEnabled = false;
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 2711 */     if (activeProgram != program) {
/*      */       
/* 2713 */       activeProgram = program;
/* 2714 */       ARBShaderObjects.glUseProgramObjectARB(programsID[program]);
/*      */       
/* 2716 */       if (programsID[program] == 0) {
/*      */         
/* 2718 */         normalMapEnabled = false;
/*      */       }
/*      */       else {
/*      */         
/* 2722 */         if (checkGLError("useProgram ", programNames[program]) != 0)
/*      */         {
/* 2724 */           programsID[program] = 0;
/*      */         }
/*      */         
/* 2727 */         IntBuffer intbuffer = programsDrawBuffers[program];
/*      */         
/* 2729 */         if (isRenderingDfb) {
/*      */           
/* 2731 */           setDrawBuffers(intbuffer);
/* 2732 */           checkGLError(programNames[program], " draw buffers = ", programsDrawBufSettings[program]);
/*      */         } 
/*      */         
/* 2735 */         activeCompositeMipmapSetting = programsCompositeMipmapSetting[program];
/* 2736 */         uniformEntityColor.setProgram(programsID[activeProgram]);
/* 2737 */         uniformEntityId.setProgram(programsID[activeProgram]);
/* 2738 */         uniformBlockEntityId.setProgram(programsID[activeProgram]);
/*      */         
/* 2740 */         switch (program) {
/*      */           
/*      */           case 1:
/*      */           case 2:
/*      */           case 3:
/*      */           case 4:
/*      */           case 5:
/*      */           case 6:
/*      */           case 7:
/*      */           case 8:
/*      */           case 9:
/*      */           case 10:
/*      */           case 11:
/*      */           case 12:
/*      */           case 13:
/*      */           case 16:
/*      */           case 18:
/*      */           case 19:
/*      */           case 20:
/* 2759 */             normalMapEnabled = true;
/* 2760 */             setProgramUniform1i("texture", 0);
/* 2761 */             setProgramUniform1i("lightmap", 1);
/* 2762 */             setProgramUniform1i("normals", 2);
/* 2763 */             setProgramUniform1i("specular", 3);
/* 2764 */             setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
/* 2765 */             setProgramUniform1i("watershadow", 4);
/* 2766 */             setProgramUniform1i("shadowtex0", 4);
/* 2767 */             setProgramUniform1i("shadowtex1", 5);
/* 2768 */             setProgramUniform1i("depthtex0", 6);
/*      */             
/* 2770 */             if (customTexturesGbuffers != null) {
/*      */               
/* 2772 */               setProgramUniform1i("gaux1", 7);
/* 2773 */               setProgramUniform1i("gaux2", 8);
/* 2774 */               setProgramUniform1i("gaux3", 9);
/* 2775 */               setProgramUniform1i("gaux4", 10);
/*      */             } 
/*      */             
/* 2778 */             setProgramUniform1i("depthtex1", 12);
/* 2779 */             setProgramUniform1i("shadowcolor", 13);
/* 2780 */             setProgramUniform1i("shadowcolor0", 13);
/* 2781 */             setProgramUniform1i("shadowcolor1", 14);
/* 2782 */             setProgramUniform1i("noisetex", 15);
/*      */             break;
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*      */           default:
/* 2789 */             normalMapEnabled = false;
/*      */             break;
/*      */           
/*      */           case 21:
/*      */           case 22:
/*      */           case 23:
/*      */           case 24:
/*      */           case 25:
/*      */           case 26:
/*      */           case 27:
/*      */           case 28:
/*      */           case 29:
/* 2801 */             normalMapEnabled = false;
/* 2802 */             setProgramUniform1i("gcolor", 0);
/* 2803 */             setProgramUniform1i("gdepth", 1);
/* 2804 */             setProgramUniform1i("gnormal", 2);
/* 2805 */             setProgramUniform1i("composite", 3);
/* 2806 */             setProgramUniform1i("gaux1", 7);
/* 2807 */             setProgramUniform1i("gaux2", 8);
/* 2808 */             setProgramUniform1i("gaux3", 9);
/* 2809 */             setProgramUniform1i("gaux4", 10);
/* 2810 */             setProgramUniform1i("colortex0", 0);
/* 2811 */             setProgramUniform1i("colortex1", 1);
/* 2812 */             setProgramUniform1i("colortex2", 2);
/* 2813 */             setProgramUniform1i("colortex3", 3);
/* 2814 */             setProgramUniform1i("colortex4", 7);
/* 2815 */             setProgramUniform1i("colortex5", 8);
/* 2816 */             setProgramUniform1i("colortex6", 9);
/* 2817 */             setProgramUniform1i("colortex7", 10);
/* 2818 */             setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
/* 2819 */             setProgramUniform1i("watershadow", 4);
/* 2820 */             setProgramUniform1i("shadowtex0", 4);
/* 2821 */             setProgramUniform1i("shadowtex1", 5);
/* 2822 */             setProgramUniform1i("gdepthtex", 6);
/* 2823 */             setProgramUniform1i("depthtex0", 6);
/* 2824 */             setProgramUniform1i("depthtex1", 11);
/* 2825 */             setProgramUniform1i("depthtex2", 12);
/* 2826 */             setProgramUniform1i("shadowcolor", 13);
/* 2827 */             setProgramUniform1i("shadowcolor0", 13);
/* 2828 */             setProgramUniform1i("shadowcolor1", 14);
/* 2829 */             setProgramUniform1i("noisetex", 15);
/*      */             break;
/*      */           
/*      */           case 30:
/*      */           case 31:
/*      */           case 32:
/* 2835 */             setProgramUniform1i("tex", 0);
/* 2836 */             setProgramUniform1i("texture", 0);
/* 2837 */             setProgramUniform1i("lightmap", 1);
/* 2838 */             setProgramUniform1i("normals", 2);
/* 2839 */             setProgramUniform1i("specular", 3);
/* 2840 */             setProgramUniform1i("shadow", waterShadowEnabled ? 5 : 4);
/* 2841 */             setProgramUniform1i("watershadow", 4);
/* 2842 */             setProgramUniform1i("shadowtex0", 4);
/* 2843 */             setProgramUniform1i("shadowtex1", 5);
/*      */             
/* 2845 */             if (customTexturesGbuffers != null) {
/*      */               
/* 2847 */               setProgramUniform1i("gaux1", 7);
/* 2848 */               setProgramUniform1i("gaux2", 8);
/* 2849 */               setProgramUniform1i("gaux3", 9);
/* 2850 */               setProgramUniform1i("gaux4", 10);
/*      */             } 
/*      */             
/* 2853 */             setProgramUniform1i("shadowcolor", 13);
/* 2854 */             setProgramUniform1i("shadowcolor0", 13);
/* 2855 */             setProgramUniform1i("shadowcolor1", 14);
/* 2856 */             setProgramUniform1i("noisetex", 15);
/*      */             break;
/*      */         } 
/* 2859 */         ItemStack itemstack = (mc.player != null) ? mc.player.getHeldItemMainhand() : null;
/* 2860 */         Item item = (itemstack != null) ? itemstack.getItem() : null;
/* 2861 */         int i = -1;
/* 2862 */         Block block = null;
/*      */         
/* 2864 */         if (item != null) {
/*      */           
/* 2866 */           i = Item.REGISTRY.getIDForObject(item);
/* 2867 */           block = (Block)Block.REGISTRY.getObjectById(i);
/*      */         } 
/*      */         
/* 2870 */         int j = (block != null) ? block.getLightValue(block.getDefaultState()) : 0;
/* 2871 */         ItemStack itemstack1 = (mc.player != null) ? mc.player.getHeldItemOffhand() : null;
/* 2872 */         Item item1 = (itemstack1 != null) ? itemstack1.getItem() : null;
/* 2873 */         int k = -1;
/* 2874 */         Block block1 = null;
/*      */         
/* 2876 */         if (item1 != null) {
/*      */           
/* 2878 */           k = Item.REGISTRY.getIDForObject(item1);
/* 2879 */           block1 = (Block)Block.REGISTRY.getObjectById(k);
/*      */         } 
/*      */         
/* 2882 */         int l = (block1 != null) ? block1.getLightValue(block1.getDefaultState()) : 0;
/*      */         
/* 2884 */         if (isOldHandLight() && l > j) {
/*      */           
/* 2886 */           i = k;
/* 2887 */           j = l;
/*      */         } 
/*      */         
/* 2890 */         setProgramUniform1i("heldItemId", i);
/* 2891 */         setProgramUniform1i("heldBlockLightValue", j);
/* 2892 */         setProgramUniform1i("heldItemId2", k);
/* 2893 */         setProgramUniform1i("heldBlockLightValue2", l);
/* 2894 */         setProgramUniform1i("fogMode", fogEnabled ? fogMode : 0);
/* 2895 */         setProgramUniform3f("fogColor", fogColorR, fogColorG, fogColorB);
/* 2896 */         setProgramUniform3f("skyColor", skyColorR, skyColorG, skyColorB);
/* 2897 */         setProgramUniform1i("worldTime", (int)(worldTime % 24000L));
/* 2898 */         setProgramUniform1i("worldDay", (int)(worldTime / 24000L));
/* 2899 */         setProgramUniform1i("moonPhase", moonPhase);
/* 2900 */         setProgramUniform1i("frameCounter", frameCounter);
/* 2901 */         setProgramUniform1f("frameTime", frameTime);
/* 2902 */         setProgramUniform1f("frameTimeCounter", frameTimeCounter);
/* 2903 */         setProgramUniform1f("sunAngle", sunAngle);
/* 2904 */         setProgramUniform1f("shadowAngle", shadowAngle);
/* 2905 */         setProgramUniform1f("rainStrength", rainStrength);
/* 2906 */         setProgramUniform1f("aspectRatio", renderWidth / renderHeight);
/* 2907 */         setProgramUniform1f("viewWidth", renderWidth);
/* 2908 */         setProgramUniform1f("viewHeight", renderHeight);
/* 2909 */         setProgramUniform1f("near", 0.05F);
/* 2910 */         setProgramUniform1f("far", (mc.gameSettings.renderDistanceChunks * 16));
/* 2911 */         setProgramUniform3f("sunPosition", sunPosition[0], sunPosition[1], sunPosition[2]);
/* 2912 */         setProgramUniform3f("moonPosition", moonPosition[0], moonPosition[1], moonPosition[2]);
/* 2913 */         setProgramUniform3f("shadowLightPosition", shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
/* 2914 */         setProgramUniform3f("upPosition", upPosition[0], upPosition[1], upPosition[2]);
/* 2915 */         setProgramUniform3f("previousCameraPosition", (float)previousCameraPositionX, (float)previousCameraPositionY, (float)previousCameraPositionZ);
/* 2916 */         setProgramUniform3f("cameraPosition", (float)cameraPositionX, (float)cameraPositionY, (float)cameraPositionZ);
/* 2917 */         setProgramUniformMatrix4ARB("gbufferModelView", false, modelView);
/* 2918 */         setProgramUniformMatrix4ARB("gbufferModelViewInverse", false, modelViewInverse);
/* 2919 */         setProgramUniformMatrix4ARB("gbufferPreviousProjection", false, previousProjection);
/* 2920 */         setProgramUniformMatrix4ARB("gbufferProjection", false, projection);
/* 2921 */         setProgramUniformMatrix4ARB("gbufferProjectionInverse", false, projectionInverse);
/* 2922 */         setProgramUniformMatrix4ARB("gbufferPreviousModelView", false, previousModelView);
/*      */         
/* 2924 */         if (usedShadowDepthBuffers > 0) {
/*      */           
/* 2926 */           setProgramUniformMatrix4ARB("shadowProjection", false, shadowProjection);
/* 2927 */           setProgramUniformMatrix4ARB("shadowProjectionInverse", false, shadowProjectionInverse);
/* 2928 */           setProgramUniformMatrix4ARB("shadowModelView", false, shadowModelView);
/* 2929 */           setProgramUniformMatrix4ARB("shadowModelViewInverse", false, shadowModelViewInverse);
/*      */         } 
/*      */         
/* 2932 */         setProgramUniform1f("wetness", wetness);
/* 2933 */         setProgramUniform1f("eyeAltitude", eyePosY);
/* 2934 */         setProgramUniform2i("eyeBrightness", eyeBrightness & 0xFFFF, eyeBrightness >> 16);
/* 2935 */         setProgramUniform2i("eyeBrightnessSmooth", Math.round(eyeBrightnessFadeX), Math.round(eyeBrightnessFadeY));
/* 2936 */         setProgramUniform2i("terrainTextureSize", terrainTextureSize[0], terrainTextureSize[1]);
/* 2937 */         setProgramUniform1i("terrainIconSize", terrainIconSize);
/* 2938 */         setProgramUniform1i("isEyeInWater", isEyeInWater);
/* 2939 */         setProgramUniform1f("nightVision", nightVision);
/* 2940 */         setProgramUniform1f("blindness", blindness);
/* 2941 */         setProgramUniform1f("screenBrightness", mc.gameSettings.gammaSetting);
/* 2942 */         setProgramUniform1i("hideGUI", mc.gameSettings.hideGUI ? 1 : 0);
/* 2943 */         setProgramUniform1f("centerDepthSmooth", centerDepthSmooth);
/* 2944 */         setProgramUniform2i("atlasSize", atlasSizeX, atlasSizeY);
/* 2945 */         checkGLError("useProgram ", programNames[program]);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setProgramUniform1i(String name, int x) {
/* 2952 */     int i = programsID[activeProgram];
/*      */     
/* 2954 */     if (i != 0) {
/*      */       
/* 2956 */       int j = ARBShaderObjects.glGetUniformLocationARB(i, name);
/* 2957 */       ARBShaderObjects.glUniform1iARB(j, x);
/* 2958 */       checkGLError(programNames[activeProgram], name);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setProgramUniform2i(String name, int x, int y) {
/* 2964 */     int i = programsID[activeProgram];
/*      */     
/* 2966 */     if (i != 0) {
/*      */       
/* 2968 */       int j = ARBShaderObjects.glGetUniformLocationARB(i, name);
/* 2969 */       ARBShaderObjects.glUniform2iARB(j, x, y);
/* 2970 */       checkGLError(programNames[activeProgram], name);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setProgramUniform1f(String name, float x) {
/* 2976 */     int i = programsID[activeProgram];
/*      */     
/* 2978 */     if (i != 0) {
/*      */       
/* 2980 */       int j = ARBShaderObjects.glGetUniformLocationARB(i, name);
/* 2981 */       ARBShaderObjects.glUniform1fARB(j, x);
/* 2982 */       checkGLError(programNames[activeProgram], name);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setProgramUniform3f(String name, float x, float y, float z) {
/* 2988 */     int i = programsID[activeProgram];
/*      */     
/* 2990 */     if (i != 0) {
/*      */       
/* 2992 */       int j = ARBShaderObjects.glGetUniformLocationARB(i, name);
/* 2993 */       ARBShaderObjects.glUniform3fARB(j, x, y, z);
/* 2994 */       checkGLError(programNames[activeProgram], name);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setProgramUniformMatrix4ARB(String name, boolean transpose, FloatBuffer matrix) {
/* 3000 */     int i = programsID[activeProgram];
/*      */     
/* 3002 */     if (i != 0 && matrix != null) {
/*      */       
/* 3004 */       int j = ARBShaderObjects.glGetUniformLocationARB(i, name);
/* 3005 */       ARBShaderObjects.glUniformMatrix4ARB(j, transpose, matrix);
/* 3006 */       checkGLError(programNames[activeProgram], name);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static int getBufferIndexFromString(String name) {
/* 3012 */     if (!name.equals("colortex0") && !name.equals("gcolor")) {
/*      */       
/* 3014 */       if (!name.equals("colortex1") && !name.equals("gdepth")) {
/*      */         
/* 3016 */         if (!name.equals("colortex2") && !name.equals("gnormal")) {
/*      */           
/* 3018 */           if (!name.equals("colortex3") && !name.equals("composite")) {
/*      */             
/* 3020 */             if (!name.equals("colortex4") && !name.equals("gaux1")) {
/*      */               
/* 3022 */               if (!name.equals("colortex5") && !name.equals("gaux2")) {
/*      */                 
/* 3024 */                 if (!name.equals("colortex6") && !name.equals("gaux3"))
/*      */                 {
/* 3026 */                   return (!name.equals("colortex7") && !name.equals("gaux4")) ? -1 : 7;
/*      */                 }
/*      */ 
/*      */                 
/* 3030 */                 return 6;
/*      */               } 
/*      */ 
/*      */ 
/*      */               
/* 3035 */               return 5;
/*      */             } 
/*      */ 
/*      */ 
/*      */             
/* 3040 */             return 4;
/*      */           } 
/*      */ 
/*      */ 
/*      */           
/* 3045 */           return 3;
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/* 3050 */         return 2;
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 3055 */       return 1;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3060 */     return 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static int getTextureFormatFromString(String par) {
/* 3066 */     par = par.trim();
/*      */     
/* 3068 */     for (int i = 0; i < formatNames.length; i++) {
/*      */       
/* 3070 */       String s = formatNames[i];
/*      */       
/* 3072 */       if (par.equals(s))
/*      */       {
/* 3074 */         return formatIds[i];
/*      */       }
/*      */     } 
/*      */     
/* 3078 */     return 0;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setupNoiseTexture() {
/* 3083 */     if (noiseTexture == null && noiseTexturePath != null)
/*      */     {
/* 3085 */       noiseTexture = loadCustomTexture(15, noiseTexturePath);
/*      */     }
/*      */     
/* 3088 */     if (noiseTexture == null)
/*      */     {
/* 3090 */       noiseTexture = new HFNoiseTexture(noiseTextureResolution, noiseTextureResolution);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadEntityDataMap() {
/* 3096 */     mapBlockToEntityData = new IdentityHashMap<>(300);
/*      */     
/* 3098 */     if (mapBlockToEntityData.isEmpty())
/*      */     {
/* 3100 */       for (ResourceLocation resourcelocation : Block.REGISTRY.getKeys()) {
/*      */         
/* 3102 */         Block block = (Block)Block.REGISTRY.getObject(resourcelocation);
/* 3103 */         int i = Block.REGISTRY.getIDForObject(block);
/* 3104 */         mapBlockToEntityData.put(block, Integer.valueOf(i));
/*      */       } 
/*      */     }
/*      */     
/* 3108 */     BufferedReader bufferedreader = null;
/*      */ 
/*      */     
/*      */     try {
/* 3112 */       bufferedreader = new BufferedReader(new InputStreamReader(shaderPack.getResourceAsStream("/mc_Entity_x.txt")));
/*      */     }
/* 3114 */     catch (Exception exception) {}
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 3119 */     if (bufferedreader != null) {
/*      */       try {
/*      */         String s1;
/*      */ 
/*      */ 
/*      */         
/* 3125 */         while ((s1 = bufferedreader.readLine()) != null)
/*      */         {
/* 3127 */           Matcher matcher = patternLoadEntityDataMap.matcher(s1);
/*      */           
/* 3129 */           if (matcher.matches()) {
/*      */             
/* 3131 */             String s2 = matcher.group(1);
/* 3132 */             String s = matcher.group(2);
/* 3133 */             int j = Integer.parseInt(s);
/* 3134 */             Block block1 = Block.getBlockFromName(s2);
/*      */             
/* 3136 */             if (block1 != null) {
/*      */               
/* 3138 */               mapBlockToEntityData.put(block1, Integer.valueOf(j));
/*      */               
/*      */               continue;
/*      */             } 
/* 3142 */             SMCLog.warning("Unknown block name %s", new Object[] { s2 });
/*      */             
/*      */             continue;
/*      */           } 
/*      */           
/* 3147 */           SMCLog.warning("unmatched %s\n", new Object[] { s1 });
/*      */         }
/*      */       
/*      */       }
/* 3151 */       catch (Exception var9) {
/*      */         
/* 3153 */         SMCLog.warning("Error parsing mc_Entity_x.txt");
/*      */       } 
/*      */     }
/*      */     
/* 3157 */     if (bufferedreader != null) {
/*      */       
/*      */       try {
/*      */         
/* 3161 */         bufferedreader.close();
/*      */       }
/* 3163 */       catch (Exception s1) {}
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static IntBuffer fillIntBufferZero(IntBuffer buf) {
/* 3172 */     int i = buf.limit();
/*      */     
/* 3174 */     for (int j = buf.position(); j < i; j++)
/*      */     {
/* 3176 */       buf.put(j, 0);
/*      */     }
/*      */     
/* 3179 */     return buf;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void uninit() {
/* 3184 */     if (isShaderPackInitialized) {
/*      */       
/* 3186 */       checkGLError("Shaders.uninit pre");
/*      */       
/* 3188 */       for (int i = 0; i < 33; i++) {
/*      */         
/* 3190 */         if (programsRef[i] != 0) {
/*      */           
/* 3192 */           ARBShaderObjects.glDeleteObjectARB(programsRef[i]);
/* 3193 */           checkGLError("del programRef");
/*      */         } 
/*      */         
/* 3196 */         programsRef[i] = 0;
/* 3197 */         programsID[i] = 0;
/* 3198 */         programsDrawBufSettings[i] = null;
/* 3199 */         programsDrawBuffers[i] = null;
/* 3200 */         programsCompositeMipmapSetting[i] = 0;
/*      */       } 
/*      */       
/* 3203 */       if (dfb != 0) {
/*      */         
/* 3205 */         EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
/* 3206 */         dfb = 0;
/* 3207 */         checkGLError("del dfb");
/*      */       } 
/*      */       
/* 3210 */       if (sfb != 0) {
/*      */         
/* 3212 */         EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
/* 3213 */         sfb = 0;
/* 3214 */         checkGLError("del sfb");
/*      */       } 
/*      */       
/* 3217 */       if (dfbDepthTextures != null) {
/*      */         
/* 3219 */         GlStateManager.deleteTextures(dfbDepthTextures);
/* 3220 */         fillIntBufferZero(dfbDepthTextures);
/* 3221 */         checkGLError("del dfbDepthTextures");
/*      */       } 
/*      */       
/* 3224 */       if (dfbColorTextures != null) {
/*      */         
/* 3226 */         GlStateManager.deleteTextures(dfbColorTextures);
/* 3227 */         fillIntBufferZero(dfbColorTextures);
/* 3228 */         checkGLError("del dfbTextures");
/*      */       } 
/*      */       
/* 3231 */       if (sfbDepthTextures != null) {
/*      */         
/* 3233 */         GlStateManager.deleteTextures(sfbDepthTextures);
/* 3234 */         fillIntBufferZero(sfbDepthTextures);
/* 3235 */         checkGLError("del shadow depth");
/*      */       } 
/*      */       
/* 3238 */       if (sfbColorTextures != null) {
/*      */         
/* 3240 */         GlStateManager.deleteTextures(sfbColorTextures);
/* 3241 */         fillIntBufferZero(sfbColorTextures);
/* 3242 */         checkGLError("del shadow color");
/*      */       } 
/*      */       
/* 3245 */       if (dfbDrawBuffers != null)
/*      */       {
/* 3247 */         fillIntBufferZero(dfbDrawBuffers);
/*      */       }
/*      */       
/* 3250 */       if (noiseTexture != null) {
/*      */         
/* 3252 */         noiseTexture.deleteTexture();
/* 3253 */         noiseTexture = null;
/*      */       } 
/*      */       
/* 3256 */       SMCLog.info("Uninit");
/* 3257 */       shadowPassInterval = 0;
/* 3258 */       shouldSkipDefaultShadow = false;
/* 3259 */       isShaderPackInitialized = false;
/* 3260 */       checkGLError("Shaders.uninit");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scheduleResize() {
/* 3266 */     renderDisplayHeight = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void scheduleResizeShadow() {
/* 3271 */     needResizeShadow = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private static void resize() {
/* 3276 */     renderDisplayWidth = mc.displayWidth;
/* 3277 */     renderDisplayHeight = mc.displayHeight;
/* 3278 */     renderWidth = Math.round(renderDisplayWidth * configRenderResMul);
/* 3279 */     renderHeight = Math.round(renderDisplayHeight * configRenderResMul);
/* 3280 */     setupFrameBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void resizeShadow() {
/* 3285 */     needResizeShadow = false;
/* 3286 */     shadowMapWidth = Math.round(spShadowMapWidth * configShadowResMul);
/* 3287 */     shadowMapHeight = Math.round(spShadowMapHeight * configShadowResMul);
/* 3288 */     setupShadowFrameBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setupFrameBuffer() {
/* 3293 */     if (dfb != 0) {
/*      */       
/* 3295 */       EXTFramebufferObject.glDeleteFramebuffersEXT(dfb);
/* 3296 */       GlStateManager.deleteTextures(dfbDepthTextures);
/* 3297 */       GlStateManager.deleteTextures(dfbColorTextures);
/*      */     } 
/*      */     
/* 3300 */     dfb = EXTFramebufferObject.glGenFramebuffersEXT();
/* 3301 */     GL11.glGenTextures((IntBuffer)dfbDepthTextures.clear().limit(usedDepthBuffers));
/* 3302 */     GL11.glGenTextures((IntBuffer)dfbColorTextures.clear().limit(16));
/* 3303 */     dfbDepthTextures.position(0);
/* 3304 */     dfbColorTextures.position(0);
/* 3305 */     dfbColorTextures.get(dfbColorTexturesA).position(0);
/* 3306 */     EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 3307 */     GL20.glDrawBuffers(0);
/* 3308 */     GL11.glReadBuffer(0);
/*      */     
/* 3310 */     for (int i = 0; i < usedDepthBuffers; i++) {
/*      */       
/* 3312 */       GlStateManager.bindTexture(dfbDepthTextures.get(i));
/* 3313 */       GL11.glTexParameteri(3553, 10242, 10496);
/* 3314 */       GL11.glTexParameteri(3553, 10243, 10496);
/* 3315 */       GL11.glTexParameteri(3553, 10241, 9728);
/* 3316 */       GL11.glTexParameteri(3553, 10240, 9728);
/* 3317 */       GL11.glTexParameteri(3553, 34891, 6409);
/* 3318 */       GL11.glTexImage2D(3553, 0, 6402, renderWidth, renderHeight, 0, 6402, 5126, null);
/*      */     } 
/*      */     
/* 3321 */     EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
/* 3322 */     GL20.glDrawBuffers(dfbDrawBuffers);
/* 3323 */     GL11.glReadBuffer(0);
/* 3324 */     checkGLError("FT d");
/*      */     
/* 3326 */     for (int k = 0; k < usedColorBuffers; k++) {
/*      */       
/* 3328 */       GlStateManager.bindTexture(dfbColorTexturesA[k]);
/* 3329 */       GL11.glTexParameteri(3553, 10242, 10496);
/* 3330 */       GL11.glTexParameteri(3553, 10243, 10496);
/* 3331 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 3332 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3333 */       GL11.glTexImage2D(3553, 0, gbuffersFormat[k], renderWidth, renderHeight, 0, 32993, 33639, null);
/* 3334 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, dfbColorTexturesA[k], 0);
/* 3335 */       checkGLError("FT c");
/*      */     } 
/*      */     
/* 3338 */     for (int l = 0; l < usedColorBuffers; l++) {
/*      */       
/* 3340 */       GlStateManager.bindTexture(dfbColorTexturesA[8 + l]);
/* 3341 */       GL11.glTexParameteri(3553, 10242, 10496);
/* 3342 */       GL11.glTexParameteri(3553, 10243, 10496);
/* 3343 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 3344 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3345 */       GL11.glTexImage2D(3553, 0, gbuffersFormat[l], renderWidth, renderHeight, 0, 32993, 33639, null);
/* 3346 */       checkGLError("FT ca");
/*      */     } 
/*      */     
/* 3349 */     int i1 = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */     
/* 3351 */     if (i1 == 36058) {
/*      */       
/* 3353 */       printChatAndLogError("[Shaders] Error: Failed framebuffer incomplete formats");
/*      */       
/* 3355 */       for (int j = 0; j < usedColorBuffers; j++) {
/*      */         
/* 3357 */         GlStateManager.bindTexture(dfbColorTextures.get(j));
/* 3358 */         GL11.glTexImage2D(3553, 0, 6408, renderWidth, renderHeight, 0, 32993, 33639, null);
/* 3359 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTextures.get(j), 0);
/* 3360 */         checkGLError("FT c");
/*      */       } 
/*      */       
/* 3363 */       i1 = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */       
/* 3365 */       if (i1 == 36053)
/*      */       {
/* 3367 */         SMCLog.info("complete");
/*      */       }
/*      */     } 
/*      */     
/* 3371 */     GlStateManager.bindTexture(0);
/*      */     
/* 3373 */     if (i1 != 36053) {
/*      */       
/* 3375 */       printChatAndLogError("[Shaders] Error: Failed creating framebuffer! (Status " + i1 + ")");
/*      */     }
/*      */     else {
/*      */       
/* 3379 */       SMCLog.info("Framebuffer created.");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void setupShadowFrameBuffer() {
/* 3385 */     if (usedShadowDepthBuffers != 0) {
/*      */       
/* 3387 */       if (sfb != 0) {
/*      */         
/* 3389 */         EXTFramebufferObject.glDeleteFramebuffersEXT(sfb);
/* 3390 */         GlStateManager.deleteTextures(sfbDepthTextures);
/* 3391 */         GlStateManager.deleteTextures(sfbColorTextures);
/*      */       } 
/*      */       
/* 3394 */       sfb = EXTFramebufferObject.glGenFramebuffersEXT();
/* 3395 */       EXTFramebufferObject.glBindFramebufferEXT(36160, sfb);
/* 3396 */       GL11.glDrawBuffer(0);
/* 3397 */       GL11.glReadBuffer(0);
/* 3398 */       GL11.glGenTextures((IntBuffer)sfbDepthTextures.clear().limit(usedShadowDepthBuffers));
/* 3399 */       GL11.glGenTextures((IntBuffer)sfbColorTextures.clear().limit(usedShadowColorBuffers));
/* 3400 */       sfbDepthTextures.position(0);
/* 3401 */       sfbColorTextures.position(0);
/*      */       
/* 3403 */       for (int i = 0; i < usedShadowDepthBuffers; i++) {
/*      */         
/* 3405 */         GlStateManager.bindTexture(sfbDepthTextures.get(i));
/* 3406 */         GL11.glTexParameterf(3553, 10242, 10496.0F);
/* 3407 */         GL11.glTexParameterf(3553, 10243, 10496.0F);
/* 3408 */         int j = shadowFilterNearest[i] ? 9728 : 9729;
/* 3409 */         GL11.glTexParameteri(3553, 10241, j);
/* 3410 */         GL11.glTexParameteri(3553, 10240, j);
/*      */         
/* 3412 */         if (shadowHardwareFilteringEnabled[i])
/*      */         {
/* 3414 */           GL11.glTexParameteri(3553, 34892, 34894);
/*      */         }
/*      */         
/* 3417 */         GL11.glTexImage2D(3553, 0, 6402, shadowMapWidth, shadowMapHeight, 0, 6402, 5126, null);
/*      */       } 
/*      */       
/* 3420 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
/* 3421 */       checkGLError("FT sd");
/*      */       
/* 3423 */       for (int k = 0; k < usedShadowColorBuffers; k++) {
/*      */         
/* 3425 */         GlStateManager.bindTexture(sfbColorTextures.get(k));
/* 3426 */         GL11.glTexParameterf(3553, 10242, 10496.0F);
/* 3427 */         GL11.glTexParameterf(3553, 10243, 10496.0F);
/* 3428 */         int i1 = shadowColorFilterNearest[k] ? 9728 : 9729;
/* 3429 */         GL11.glTexParameteri(3553, 10241, i1);
/* 3430 */         GL11.glTexParameteri(3553, 10240, i1);
/* 3431 */         GL11.glTexImage2D(3553, 0, 6408, shadowMapWidth, shadowMapHeight, 0, 32993, 33639, null);
/* 3432 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, sfbColorTextures.get(k), 0);
/* 3433 */         checkGLError("FT sc");
/*      */       } 
/*      */       
/* 3436 */       GlStateManager.bindTexture(0);
/*      */       
/* 3438 */       if (usedShadowColorBuffers > 0)
/*      */       {
/* 3440 */         GL20.glDrawBuffers(sfbDrawBuffers);
/*      */       }
/*      */       
/* 3443 */       int l = EXTFramebufferObject.glCheckFramebufferStatusEXT(36160);
/*      */       
/* 3445 */       if (l != 36053) {
/*      */         
/* 3447 */         printChatAndLogError("[Shaders] Error: Failed creating shadow framebuffer! (Status " + l + ")");
/*      */       }
/*      */       else {
/*      */         
/* 3451 */         SMCLog.info("Shadow framebuffer created.");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginRender(Minecraft minecraft, float partialTicks, long finishTimeNano) {
/* 3458 */     checkGLError("pre beginRender");
/* 3459 */     checkWorldChanged((World)mc.world);
/* 3460 */     mc = minecraft;
/* 3461 */     mc.mcProfiler.startSection("init");
/* 3462 */     entityRenderer = mc.entityRenderer;
/*      */     
/* 3464 */     if (!isShaderPackInitialized) {
/*      */       
/*      */       try {
/*      */         
/* 3468 */         init();
/*      */       }
/* 3470 */       catch (IllegalStateException illegalstateexception) {
/*      */         
/* 3472 */         if (Config.normalize(illegalstateexception.getMessage()).equals("Function is not supported")) {
/*      */           
/* 3474 */           printChatAndLogError("[Shaders] Error: " + illegalstateexception.getMessage());
/* 3475 */           illegalstateexception.printStackTrace();
/* 3476 */           setShaderPack(packNameNone);
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     }
/* 3482 */     if (mc.displayWidth != renderDisplayWidth || mc.displayHeight != renderDisplayHeight)
/*      */     {
/* 3484 */       resize();
/*      */     }
/*      */     
/* 3487 */     if (needResizeShadow)
/*      */     {
/* 3489 */       resizeShadow();
/*      */     }
/*      */     
/* 3492 */     worldTime = mc.world.getWorldTime();
/* 3493 */     diffWorldTime = (worldTime - lastWorldTime) % 24000L;
/*      */     
/* 3495 */     if (diffWorldTime < 0L)
/*      */     {
/* 3497 */       diffWorldTime += 24000L;
/*      */     }
/*      */     
/* 3500 */     lastWorldTime = worldTime;
/* 3501 */     moonPhase = mc.world.getMoonPhase();
/* 3502 */     frameCounter++;
/*      */     
/* 3504 */     if (frameCounter >= 720720)
/*      */     {
/* 3506 */       frameCounter = 0;
/*      */     }
/*      */     
/* 3509 */     systemTime = System.currentTimeMillis();
/*      */     
/* 3511 */     if (lastSystemTime == 0L)
/*      */     {
/* 3513 */       lastSystemTime = systemTime;
/*      */     }
/*      */     
/* 3516 */     diffSystemTime = systemTime - lastSystemTime;
/* 3517 */     lastSystemTime = systemTime;
/* 3518 */     frameTime = (float)diffSystemTime / 1000.0F;
/* 3519 */     frameTimeCounter += frameTime;
/* 3520 */     frameTimeCounter %= 3600.0F;
/* 3521 */     rainStrength = minecraft.world.getRainStrength(partialTicks);
/* 3522 */     float f = (float)diffSystemTime * 0.01F;
/* 3523 */     float f1 = (float)Math.exp(Math.log(0.5D) * f / ((wetness < rainStrength) ? drynessHalfLife : wetnessHalfLife));
/* 3524 */     wetness = wetness * f1 + rainStrength * (1.0F - f1);
/* 3525 */     Entity entity = mc.getRenderViewEntity();
/*      */     
/* 3527 */     if (entity != null) {
/*      */       
/* 3529 */       isSleeping = (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping());
/* 3530 */       eyePosY = (float)entity.posY * partialTicks + (float)entity.lastTickPosY * (1.0F - partialTicks);
/* 3531 */       eyeBrightness = entity.getBrightnessForRender();
/* 3532 */       f1 = (float)diffSystemTime * 0.01F;
/* 3533 */       float f2 = (float)Math.exp(Math.log(0.5D) * f1 / eyeBrightnessHalflife);
/* 3534 */       eyeBrightnessFadeX = eyeBrightnessFadeX * f2 + (eyeBrightness & 0xFFFF) * (1.0F - f2);
/* 3535 */       eyeBrightnessFadeY = eyeBrightnessFadeY * f2 + (eyeBrightness >> 16) * (1.0F - f2);
/* 3536 */       isEyeInWater = 0;
/*      */       
/* 3538 */       if (mc.gameSettings.thirdPersonView == 0 && !isSleeping)
/*      */       {
/* 3540 */         if (entity.isInsideOfMaterial(Material.WATER)) {
/*      */           
/* 3542 */           isEyeInWater = 1;
/*      */         }
/* 3544 */         else if (entity.isInsideOfMaterial(Material.LAVA)) {
/*      */           
/* 3546 */           isEyeInWater = 2;
/*      */         } 
/*      */       }
/*      */       
/* 3550 */       if (mc.player != null) {
/*      */         
/* 3552 */         nightVision = 0.0F;
/*      */         
/* 3554 */         if (mc.player.isPotionActive(MobEffects.NIGHT_VISION))
/*      */         {
/* 3556 */           nightVision = (Config.getMinecraft()).entityRenderer.getNightVisionBrightness((EntityLivingBase)mc.player, partialTicks);
/*      */         }
/*      */         
/* 3559 */         blindness = 0.0F;
/*      */         
/* 3561 */         if (mc.player.isPotionActive(MobEffects.BLINDNESS)) {
/*      */           
/* 3563 */           int i = mc.player.getActivePotionEffect(MobEffects.BLINDNESS).getDuration();
/* 3564 */           blindness = Config.limit(i / 20.0F, 0.0F, 1.0F);
/*      */         } 
/*      */       } 
/*      */       
/* 3568 */       Vec3d vec3d = mc.world.getSkyColor(entity, partialTicks);
/* 3569 */       vec3d = CustomColors.getWorldSkyColor(vec3d, currentWorld, entity, partialTicks);
/* 3570 */       skyColorR = (float)vec3d.xCoord;
/* 3571 */       skyColorG = (float)vec3d.yCoord;
/* 3572 */       skyColorB = (float)vec3d.zCoord;
/*      */     } 
/*      */     
/* 3575 */     isRenderingWorld = true;
/* 3576 */     isCompositeRendered = false;
/* 3577 */     isHandRenderedMain = false;
/* 3578 */     isHandRenderedOff = false;
/* 3579 */     skipRenderHandMain = false;
/* 3580 */     skipRenderHandOff = false;
/*      */     
/* 3582 */     if (usedShadowDepthBuffers >= 1) {
/*      */       
/* 3584 */       GlStateManager.setActiveTexture(33988);
/* 3585 */       GlStateManager.bindTexture(sfbDepthTextures.get(0));
/*      */       
/* 3587 */       if (usedShadowDepthBuffers >= 2) {
/*      */         
/* 3589 */         GlStateManager.setActiveTexture(33989);
/* 3590 */         GlStateManager.bindTexture(sfbDepthTextures.get(1));
/*      */       } 
/*      */     } 
/*      */     
/* 3594 */     GlStateManager.setActiveTexture(33984);
/*      */     
/* 3596 */     for (int j = 0; j < usedColorBuffers; j++) {
/*      */       
/* 3598 */       GlStateManager.bindTexture(dfbColorTexturesA[j]);
/* 3599 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3600 */       GL11.glTexParameteri(3553, 10241, 9729);
/* 3601 */       GlStateManager.bindTexture(dfbColorTexturesA[8 + j]);
/* 3602 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3603 */       GL11.glTexParameteri(3553, 10241, 9729);
/*      */     } 
/*      */     
/* 3606 */     GlStateManager.bindTexture(0);
/*      */     
/* 3608 */     for (int k = 0; k < 4 && 4 + k < usedColorBuffers; k++) {
/*      */       
/* 3610 */       GlStateManager.setActiveTexture(33991 + k);
/* 3611 */       GlStateManager.bindTexture(dfbColorTextures.get(4 + k));
/*      */     } 
/*      */     
/* 3614 */     GlStateManager.setActiveTexture(33990);
/* 3615 */     GlStateManager.bindTexture(dfbDepthTextures.get(0));
/*      */     
/* 3617 */     if (usedDepthBuffers >= 2) {
/*      */       
/* 3619 */       GlStateManager.setActiveTexture(33995);
/* 3620 */       GlStateManager.bindTexture(dfbDepthTextures.get(1));
/*      */       
/* 3622 */       if (usedDepthBuffers >= 3) {
/*      */         
/* 3624 */         GlStateManager.setActiveTexture(33996);
/* 3625 */         GlStateManager.bindTexture(dfbDepthTextures.get(2));
/*      */       } 
/*      */     } 
/*      */     
/* 3629 */     for (int l = 0; l < usedShadowColorBuffers; l++) {
/*      */       
/* 3631 */       GlStateManager.setActiveTexture(33997 + l);
/* 3632 */       GlStateManager.bindTexture(sfbColorTextures.get(l));
/*      */     } 
/*      */     
/* 3635 */     if (noiseTextureEnabled) {
/*      */       
/* 3637 */       GlStateManager.setActiveTexture(33984 + noiseTexture.getTextureUnit());
/* 3638 */       GlStateManager.bindTexture(noiseTexture.getTextureId());
/* 3639 */       GL11.glTexParameteri(3553, 10242, 10497);
/* 3640 */       GL11.glTexParameteri(3553, 10243, 10497);
/* 3641 */       GL11.glTexParameteri(3553, 10240, 9729);
/* 3642 */       GL11.glTexParameteri(3553, 10241, 9729);
/*      */     } 
/*      */     
/* 3645 */     bindCustomTextures(customTexturesGbuffers);
/* 3646 */     GlStateManager.setActiveTexture(33984);
/* 3647 */     previousCameraPositionX = cameraPositionX;
/* 3648 */     previousCameraPositionY = cameraPositionY;
/* 3649 */     previousCameraPositionZ = cameraPositionZ;
/* 3650 */     previousProjection.position(0);
/* 3651 */     projection.position(0);
/* 3652 */     previousProjection.put(projection);
/* 3653 */     previousProjection.position(0);
/* 3654 */     projection.position(0);
/* 3655 */     previousModelView.position(0);
/* 3656 */     modelView.position(0);
/* 3657 */     previousModelView.put(modelView);
/* 3658 */     previousModelView.position(0);
/* 3659 */     modelView.position(0);
/* 3660 */     checkGLError("beginRender");
/* 3661 */     ShadersRender.renderShadowMap(entityRenderer, 0, partialTicks, finishTimeNano);
/* 3662 */     mc.mcProfiler.endSection();
/* 3663 */     EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/*      */     
/* 3665 */     for (int i1 = 0; i1 < usedColorBuffers; i1++) {
/*      */       
/* 3667 */       colorTexturesToggle[i1] = 0;
/* 3668 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + i1, 3553, dfbColorTexturesA[i1], 0);
/*      */     } 
/*      */     
/* 3671 */     checkGLError("end beginRender");
/*      */   }
/*      */ 
/*      */   
/*      */   private static void checkWorldChanged(World worldd) {
/* 3676 */     if (currentWorld != worldd) {
/*      */       
/* 3678 */       World world = currentWorld;
/* 3679 */       currentWorld = worldd;
/*      */       
/* 3681 */       if (world != null && worldd != null) {
/*      */         
/* 3683 */         int i = world.provider.getDimensionType().getId();
/* 3684 */         int j = worldd.provider.getDimensionType().getId();
/* 3685 */         boolean flag = shaderPackDimensions.contains(Integer.valueOf(i));
/* 3686 */         boolean flag1 = shaderPackDimensions.contains(Integer.valueOf(j));
/*      */         
/* 3688 */         if (flag || flag1)
/*      */         {
/* 3690 */           uninit();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginRenderPass(int pass, float partialTicks, long finishTimeNano) {
/* 3698 */     if (!isShadowPass) {
/*      */       
/* 3700 */       EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 3701 */       GL11.glViewport(0, 0, renderWidth, renderHeight);
/* 3702 */       activeDrawBuffers = null;
/* 3703 */       ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
/* 3704 */       useProgram(2);
/* 3705 */       checkGLError("end beginRenderPass");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setViewport(int vx, int vy, int vw, int vh) {
/* 3711 */     GlStateManager.colorMask(true, true, true, true);
/*      */     
/* 3713 */     if (isShadowPass) {
/*      */       
/* 3715 */       GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
/*      */     }
/*      */     else {
/*      */       
/* 3719 */       GL11.glViewport(0, 0, renderWidth, renderHeight);
/* 3720 */       EXTFramebufferObject.glBindFramebufferEXT(36160, dfb);
/* 3721 */       isRenderingDfb = true;
/* 3722 */       GlStateManager.enableCull();
/* 3723 */       GlStateManager.enableDepth();
/* 3724 */       setDrawBuffers(drawBuffersNone);
/* 3725 */       useProgram(2);
/* 3726 */       checkGLError("beginRenderPass");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int setFogMode(int val) {
/* 3732 */     fogMode = val;
/* 3733 */     return val;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFogColor(float r, float g, float b) {
/* 3738 */     fogColorR = r;
/* 3739 */     fogColorG = g;
/* 3740 */     fogColorB = b;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setClearColor(float red, float green, float blue, float alpha) {
/* 3745 */     GlStateManager.clearColor(red, green, blue, alpha);
/* 3746 */     clearColorR = red;
/* 3747 */     clearColorG = green;
/* 3748 */     clearColorB = blue;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void clearRenderBuffer() {
/* 3753 */     if (isShadowPass) {
/*      */       
/* 3755 */       checkGLError("shadow clear pre");
/* 3756 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, sfbDepthTextures.get(0), 0);
/* 3757 */       GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 3758 */       GL20.glDrawBuffers(programsDrawBuffers[30]);
/* 3759 */       checkFramebufferStatus("shadow clear");
/* 3760 */       GL11.glClear(16640);
/* 3761 */       checkGLError("shadow clear");
/*      */     }
/*      */     else {
/*      */       
/* 3765 */       checkGLError("clear pre");
/*      */       
/* 3767 */       if (gbuffersClear[0]) {
/*      */         
/* 3769 */         GL20.glDrawBuffers(36064);
/* 3770 */         GL11.glClear(16384);
/*      */       } 
/*      */       
/* 3773 */       if (gbuffersClear[1]) {
/*      */         
/* 3775 */         GL20.glDrawBuffers(36065);
/* 3776 */         GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
/* 3777 */         GL11.glClear(16384);
/*      */       } 
/*      */       
/* 3780 */       for (int i = 2; i < usedColorBuffers; i++) {
/*      */         
/* 3782 */         if (gbuffersClear[i]) {
/*      */           
/* 3784 */           GL20.glDrawBuffers(36064 + i);
/* 3785 */           GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
/* 3786 */           GL11.glClear(16384);
/*      */         } 
/*      */       } 
/*      */       
/* 3790 */       setDrawBuffers(dfbDrawBuffers);
/* 3791 */       checkFramebufferStatus("clear");
/* 3792 */       checkGLError("clear");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setCamera(float partialTicks) {
/* 3798 */     Entity entity = mc.getRenderViewEntity();
/* 3799 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 3800 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 3801 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 3802 */     cameraPositionX = d0;
/* 3803 */     cameraPositionY = d1;
/* 3804 */     cameraPositionZ = d2;
/* 3805 */     GL11.glGetFloat(2983, (FloatBuffer)projection.position(0));
/* 3806 */     SMath.invertMat4FBFA((FloatBuffer)projectionInverse.position(0), (FloatBuffer)projection.position(0), faProjectionInverse, faProjection);
/* 3807 */     projection.position(0);
/* 3808 */     projectionInverse.position(0);
/* 3809 */     GL11.glGetFloat(2982, (FloatBuffer)modelView.position(0));
/* 3810 */     SMath.invertMat4FBFA((FloatBuffer)modelViewInverse.position(0), (FloatBuffer)modelView.position(0), faModelViewInverse, faModelView);
/* 3811 */     modelView.position(0);
/* 3812 */     modelViewInverse.position(0);
/* 3813 */     checkGLError("setCamera");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setCameraShadow(float partialTicks) {
/* 3818 */     Entity entity = mc.getRenderViewEntity();
/* 3819 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 3820 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 3821 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 3822 */     cameraPositionX = d0;
/* 3823 */     cameraPositionY = d1;
/* 3824 */     cameraPositionZ = d2;
/* 3825 */     GL11.glGetFloat(2983, (FloatBuffer)projection.position(0));
/* 3826 */     SMath.invertMat4FBFA((FloatBuffer)projectionInverse.position(0), (FloatBuffer)projection.position(0), faProjectionInverse, faProjection);
/* 3827 */     projection.position(0);
/* 3828 */     projectionInverse.position(0);
/* 3829 */     GL11.glGetFloat(2982, (FloatBuffer)modelView.position(0));
/* 3830 */     SMath.invertMat4FBFA((FloatBuffer)modelViewInverse.position(0), (FloatBuffer)modelView.position(0), faModelViewInverse, faModelView);
/* 3831 */     modelView.position(0);
/* 3832 */     modelViewInverse.position(0);
/* 3833 */     GL11.glViewport(0, 0, shadowMapWidth, shadowMapHeight);
/* 3834 */     GL11.glMatrixMode(5889);
/* 3835 */     GL11.glLoadIdentity();
/*      */     
/* 3837 */     if (shadowMapIsOrtho) {
/*      */       
/* 3839 */       GL11.glOrtho(-shadowMapHalfPlane, shadowMapHalfPlane, -shadowMapHalfPlane, shadowMapHalfPlane, 0.05000000074505806D, 256.0D);
/*      */     }
/*      */     else {
/*      */       
/* 3843 */       GLU.gluPerspective(shadowMapFOV, shadowMapWidth / shadowMapHeight, 0.05F, 256.0F);
/*      */     } 
/*      */     
/* 3846 */     GL11.glMatrixMode(5888);
/* 3847 */     GL11.glLoadIdentity();
/* 3848 */     GL11.glTranslatef(0.0F, 0.0F, -100.0F);
/* 3849 */     GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 3850 */     celestialAngle = mc.world.getCelestialAngle(partialTicks);
/* 3851 */     sunAngle = (celestialAngle < 0.75F) ? (celestialAngle + 0.25F) : (celestialAngle - 0.75F);
/* 3852 */     float f = celestialAngle * -360.0F;
/* 3853 */     float f1 = (shadowAngleInterval > 0.0F) ? (f % shadowAngleInterval - shadowAngleInterval * 0.5F) : 0.0F;
/*      */     
/* 3855 */     if (sunAngle <= 0.5D) {
/*      */       
/* 3857 */       GL11.glRotatef(f - f1, 0.0F, 0.0F, 1.0F);
/* 3858 */       GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
/* 3859 */       shadowAngle = sunAngle;
/*      */     }
/*      */     else {
/*      */       
/* 3863 */       GL11.glRotatef(f + 180.0F - f1, 0.0F, 0.0F, 1.0F);
/* 3864 */       GL11.glRotatef(sunPathRotation, 1.0F, 0.0F, 0.0F);
/* 3865 */       shadowAngle = sunAngle - 0.5F;
/*      */     } 
/*      */     
/* 3868 */     if (shadowMapIsOrtho) {
/*      */       
/* 3870 */       float f2 = shadowIntervalSize;
/* 3871 */       float f3 = f2 / 2.0F;
/* 3872 */       GL11.glTranslatef((float)d0 % f2 - f3, (float)d1 % f2 - f3, (float)d2 % f2 - f3);
/*      */     } 
/*      */     
/* 3875 */     float f9 = sunAngle * 6.2831855F;
/* 3876 */     float f10 = (float)Math.cos(f9);
/* 3877 */     float f4 = (float)Math.sin(f9);
/* 3878 */     float f5 = sunPathRotation * 6.2831855F;
/* 3879 */     float f6 = f10;
/* 3880 */     float f7 = f4 * (float)Math.cos(f5);
/* 3881 */     float f8 = f4 * (float)Math.sin(f5);
/*      */     
/* 3883 */     if (sunAngle > 0.5D) {
/*      */       
/* 3885 */       f6 = -f10;
/* 3886 */       f7 = -f7;
/* 3887 */       f8 = -f8;
/*      */     } 
/*      */     
/* 3890 */     shadowLightPositionVector[0] = f6;
/* 3891 */     shadowLightPositionVector[1] = f7;
/* 3892 */     shadowLightPositionVector[2] = f8;
/* 3893 */     shadowLightPositionVector[3] = 0.0F;
/* 3894 */     GL11.glGetFloat(2983, (FloatBuffer)shadowProjection.position(0));
/* 3895 */     SMath.invertMat4FBFA((FloatBuffer)shadowProjectionInverse.position(0), (FloatBuffer)shadowProjection.position(0), faShadowProjectionInverse, faShadowProjection);
/* 3896 */     shadowProjection.position(0);
/* 3897 */     shadowProjectionInverse.position(0);
/* 3898 */     GL11.glGetFloat(2982, (FloatBuffer)shadowModelView.position(0));
/* 3899 */     SMath.invertMat4FBFA((FloatBuffer)shadowModelViewInverse.position(0), (FloatBuffer)shadowModelView.position(0), faShadowModelViewInverse, faShadowModelView);
/* 3900 */     shadowModelView.position(0);
/* 3901 */     shadowModelViewInverse.position(0);
/* 3902 */     setProgramUniformMatrix4ARB("gbufferProjection", false, projection);
/* 3903 */     setProgramUniformMatrix4ARB("gbufferProjectionInverse", false, projectionInverse);
/* 3904 */     setProgramUniformMatrix4ARB("gbufferPreviousProjection", false, previousProjection);
/* 3905 */     setProgramUniformMatrix4ARB("gbufferModelView", false, modelView);
/* 3906 */     setProgramUniformMatrix4ARB("gbufferModelViewInverse", false, modelViewInverse);
/* 3907 */     setProgramUniformMatrix4ARB("gbufferPreviousModelView", false, previousModelView);
/* 3908 */     setProgramUniformMatrix4ARB("shadowProjection", false, shadowProjection);
/* 3909 */     setProgramUniformMatrix4ARB("shadowProjectionInverse", false, shadowProjectionInverse);
/* 3910 */     setProgramUniformMatrix4ARB("shadowModelView", false, shadowModelView);
/* 3911 */     setProgramUniformMatrix4ARB("shadowModelViewInverse", false, shadowModelViewInverse);
/* 3912 */     mc.gameSettings.thirdPersonView = 1;
/* 3913 */     checkGLError("setCamera");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void preCelestialRotate() {
/* 3918 */     GL11.glRotatef(sunPathRotation * 1.0F, 0.0F, 0.0F, 1.0F);
/* 3919 */     checkGLError("preCelestialRotate");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void postCelestialRotate() {
/* 3924 */     FloatBuffer floatbuffer = tempMatrixDirectBuffer;
/* 3925 */     floatbuffer.clear();
/* 3926 */     GL11.glGetFloat(2982, floatbuffer);
/* 3927 */     floatbuffer.get(tempMat, 0, 16);
/* 3928 */     SMath.multiplyMat4xVec4(sunPosition, tempMat, sunPosModelView);
/* 3929 */     SMath.multiplyMat4xVec4(moonPosition, tempMat, moonPosModelView);
/* 3930 */     System.arraycopy((shadowAngle == sunAngle) ? sunPosition : moonPosition, 0, shadowLightPosition, 0, 3);
/* 3931 */     setProgramUniform3f("sunPosition", sunPosition[0], sunPosition[1], sunPosition[2]);
/* 3932 */     setProgramUniform3f("moonPosition", moonPosition[0], moonPosition[1], moonPosition[2]);
/* 3933 */     setProgramUniform3f("shadowLightPosition", shadowLightPosition[0], shadowLightPosition[1], shadowLightPosition[2]);
/* 3934 */     checkGLError("postCelestialRotate");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setUpPosition() {
/* 3939 */     FloatBuffer floatbuffer = tempMatrixDirectBuffer;
/* 3940 */     floatbuffer.clear();
/* 3941 */     GL11.glGetFloat(2982, floatbuffer);
/* 3942 */     floatbuffer.get(tempMat, 0, 16);
/* 3943 */     SMath.multiplyMat4xVec4(upPosition, tempMat, upPosModelView);
/* 3944 */     setProgramUniform3f("upPosition", upPosition[0], upPosition[1], upPosition[2]);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void genCompositeMipmap() {
/* 3949 */     if (hasGlGenMipmap) {
/*      */       
/* 3951 */       for (int i = 0; i < usedColorBuffers; i++) {
/*      */         
/* 3953 */         if ((activeCompositeMipmapSetting & 1 << i) != 0) {
/*      */           
/* 3955 */           GlStateManager.setActiveTexture(33984 + colorTextureTextureImageUnit[i]);
/* 3956 */           GL11.glTexParameteri(3553, 10241, 9987);
/* 3957 */           GL30.glGenerateMipmap(3553);
/*      */         } 
/*      */       } 
/*      */       
/* 3961 */       GlStateManager.setActiveTexture(33984);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawComposite() {
/* 3967 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 3968 */     GL11.glBegin(7);
/* 3969 */     GL11.glTexCoord2f(0.0F, 0.0F);
/* 3970 */     GL11.glVertex3f(0.0F, 0.0F, 0.0F);
/* 3971 */     GL11.glTexCoord2f(1.0F, 0.0F);
/* 3972 */     GL11.glVertex3f(1.0F, 0.0F, 0.0F);
/* 3973 */     GL11.glTexCoord2f(1.0F, 1.0F);
/* 3974 */     GL11.glVertex3f(1.0F, 1.0F, 0.0F);
/* 3975 */     GL11.glTexCoord2f(0.0F, 1.0F);
/* 3976 */     GL11.glVertex3f(0.0F, 1.0F, 0.0F);
/* 3977 */     GL11.glEnd();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderCompositeFinal() {
/* 3982 */     if (!isShadowPass) {
/*      */       
/* 3984 */       checkGLError("pre-renderCompositeFinal");
/* 3985 */       GL11.glPushMatrix();
/* 3986 */       GL11.glLoadIdentity();
/* 3987 */       GL11.glMatrixMode(5889);
/* 3988 */       GL11.glPushMatrix();
/* 3989 */       GL11.glLoadIdentity();
/* 3990 */       GL11.glOrtho(0.0D, 1.0D, 0.0D, 1.0D, 0.0D, 1.0D);
/* 3991 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 3992 */       GlStateManager.enableTexture2D();
/* 3993 */       GlStateManager.disableAlpha();
/* 3994 */       GlStateManager.disableBlend();
/* 3995 */       GlStateManager.enableDepth();
/* 3996 */       GlStateManager.depthFunc(519);
/* 3997 */       GlStateManager.depthMask(false);
/* 3998 */       GlStateManager.disableLighting();
/*      */       
/* 4000 */       if (usedShadowDepthBuffers >= 1) {
/*      */         
/* 4002 */         GlStateManager.setActiveTexture(33988);
/* 4003 */         GlStateManager.bindTexture(sfbDepthTextures.get(0));
/*      */         
/* 4005 */         if (usedShadowDepthBuffers >= 2) {
/*      */           
/* 4007 */           GlStateManager.setActiveTexture(33989);
/* 4008 */           GlStateManager.bindTexture(sfbDepthTextures.get(1));
/*      */         } 
/*      */       } 
/*      */       
/* 4012 */       for (int i = 0; i < usedColorBuffers; i++) {
/*      */         
/* 4014 */         GlStateManager.setActiveTexture(33984 + colorTextureTextureImageUnit[i]);
/* 4015 */         GlStateManager.bindTexture(dfbColorTexturesA[i]);
/*      */       } 
/*      */       
/* 4018 */       GlStateManager.setActiveTexture(33990);
/* 4019 */       GlStateManager.bindTexture(dfbDepthTextures.get(0));
/*      */       
/* 4021 */       if (usedDepthBuffers >= 2) {
/*      */         
/* 4023 */         GlStateManager.setActiveTexture(33995);
/* 4024 */         GlStateManager.bindTexture(dfbDepthTextures.get(1));
/*      */         
/* 4026 */         if (usedDepthBuffers >= 3) {
/*      */           
/* 4028 */           GlStateManager.setActiveTexture(33996);
/* 4029 */           GlStateManager.bindTexture(dfbDepthTextures.get(2));
/*      */         } 
/*      */       } 
/*      */       
/* 4033 */       for (int j1 = 0; j1 < usedShadowColorBuffers; j1++) {
/*      */         
/* 4035 */         GlStateManager.setActiveTexture(33997 + j1);
/* 4036 */         GlStateManager.bindTexture(sfbColorTextures.get(j1));
/*      */       } 
/*      */       
/* 4039 */       if (noiseTextureEnabled) {
/*      */         
/* 4041 */         GlStateManager.setActiveTexture(33984 + noiseTexture.getTextureUnit());
/* 4042 */         GlStateManager.bindTexture(noiseTexture.getTextureId());
/* 4043 */         GL11.glTexParameteri(3553, 10242, 10497);
/* 4044 */         GL11.glTexParameteri(3553, 10243, 10497);
/* 4045 */         GL11.glTexParameteri(3553, 10240, 9729);
/* 4046 */         GL11.glTexParameteri(3553, 10241, 9729);
/*      */       } 
/*      */       
/* 4049 */       bindCustomTextures(customTexturesComposite);
/* 4050 */       GlStateManager.setActiveTexture(33984);
/* 4051 */       boolean flag = true;
/*      */       
/* 4053 */       for (int j = 0; j < usedColorBuffers; j++)
/*      */       {
/* 4055 */         EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + j, 3553, dfbColorTexturesA[8 + j], 0);
/*      */       }
/*      */       
/* 4058 */       EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36096, 3553, dfbDepthTextures.get(0), 0);
/* 4059 */       GL20.glDrawBuffers(dfbDrawBuffers);
/* 4060 */       checkGLError("pre-composite");
/*      */       
/* 4062 */       for (int k1 = 0; k1 < 8; k1++) {
/*      */         
/* 4064 */         if (programsID[21 + k1] != 0) {
/*      */           
/* 4066 */           useProgram(21 + k1);
/* 4067 */           checkGLError(programNames[21 + k1]);
/*      */           
/* 4069 */           if (activeCompositeMipmapSetting != 0)
/*      */           {
/* 4071 */             genCompositeMipmap();
/*      */           }
/*      */           
/* 4074 */           drawComposite();
/*      */           
/* 4076 */           for (int k = 0; k < usedColorBuffers; k++) {
/*      */             
/* 4078 */             if (programsToggleColorTextures[21 + k1][k]) {
/*      */               
/* 4080 */               int l = colorTexturesToggle[k];
/* 4081 */               int i1 = colorTexturesToggle[k] = 8 - l;
/* 4082 */               GlStateManager.setActiveTexture(33984 + colorTextureTextureImageUnit[k]);
/* 4083 */               GlStateManager.bindTexture(dfbColorTexturesA[i1 + k]);
/* 4084 */               EXTFramebufferObject.glFramebufferTexture2DEXT(36160, 36064 + k, 3553, dfbColorTexturesA[l + k], 0);
/*      */             } 
/*      */           } 
/*      */           
/* 4088 */           GlStateManager.setActiveTexture(33984);
/*      */         } 
/*      */       } 
/*      */       
/* 4092 */       checkGLError("composite");
/* 4093 */       isRenderingDfb = false;
/* 4094 */       mc.getFramebuffer().bindFramebuffer(true);
/* 4095 */       OpenGlHelper.glFramebufferTexture2D(OpenGlHelper.GL_FRAMEBUFFER, OpenGlHelper.GL_COLOR_ATTACHMENT0, 3553, (mc.getFramebuffer()).framebufferTexture, 0);
/* 4096 */       GL11.glViewport(0, 0, mc.displayWidth, mc.displayHeight);
/*      */       
/* 4098 */       if (EntityRenderer.anaglyphEnable) {
/*      */         
/* 4100 */         boolean flag1 = (EntityRenderer.anaglyphField != 0);
/* 4101 */         GlStateManager.colorMask(flag1, !flag1, !flag1, true);
/*      */       } 
/*      */       
/* 4104 */       GlStateManager.depthMask(true);
/* 4105 */       GL11.glClearColor(clearColorR, clearColorG, clearColorB, 1.0F);
/* 4106 */       GL11.glClear(16640);
/* 4107 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 4108 */       GlStateManager.enableTexture2D();
/* 4109 */       GlStateManager.disableAlpha();
/* 4110 */       GlStateManager.disableBlend();
/* 4111 */       GlStateManager.enableDepth();
/* 4112 */       GlStateManager.depthFunc(519);
/* 4113 */       GlStateManager.depthMask(false);
/* 4114 */       checkGLError("pre-final");
/* 4115 */       useProgram(29);
/* 4116 */       checkGLError("final");
/*      */       
/* 4118 */       if (activeCompositeMipmapSetting != 0)
/*      */       {
/* 4120 */         genCompositeMipmap();
/*      */       }
/*      */       
/* 4123 */       drawComposite();
/* 4124 */       checkGLError("renderCompositeFinal");
/* 4125 */       isCompositeRendered = true;
/* 4126 */       GlStateManager.enableLighting();
/* 4127 */       GlStateManager.enableTexture2D();
/* 4128 */       GlStateManager.enableAlpha();
/* 4129 */       GlStateManager.enableBlend();
/* 4130 */       GlStateManager.depthFunc(515);
/* 4131 */       GlStateManager.depthMask(true);
/* 4132 */       GL11.glPopMatrix();
/* 4133 */       GL11.glMatrixMode(5888);
/* 4134 */       GL11.glPopMatrix();
/* 4135 */       useProgram(0);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endRender() {
/* 4141 */     if (isShadowPass) {
/*      */       
/* 4143 */       checkGLError("shadow endRender");
/*      */     }
/*      */     else {
/*      */       
/* 4147 */       if (!isCompositeRendered)
/*      */       {
/* 4149 */         renderCompositeFinal();
/*      */       }
/*      */       
/* 4152 */       isRenderingWorld = false;
/* 4153 */       GlStateManager.colorMask(true, true, true, true);
/* 4154 */       useProgram(0);
/* 4155 */       RenderHelper.disableStandardItemLighting();
/* 4156 */       checkGLError("endRender end");
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginSky() {
/* 4162 */     isRenderingSky = true;
/* 4163 */     fogEnabled = true;
/* 4164 */     setDrawBuffers(dfbDrawBuffers);
/* 4165 */     useProgram(5);
/* 4166 */     pushEntity(-2, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setSkyColor(Vec3d v3color) {
/* 4171 */     skyColorR = (float)v3color.xCoord;
/* 4172 */     skyColorG = (float)v3color.yCoord;
/* 4173 */     skyColorB = (float)v3color.zCoord;
/* 4174 */     setProgramUniform3f("skyColor", skyColorR, skyColorG, skyColorB);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawHorizon() {
/* 4179 */     BufferBuilder bufferbuilder = Tessellator.getInstance().getBuffer();
/* 4180 */     float f = (mc.gameSettings.renderDistanceChunks * 16);
/* 4181 */     double d0 = f * 0.9238D;
/* 4182 */     double d1 = f * 0.3826D;
/* 4183 */     double d2 = -d1;
/* 4184 */     double d3 = -d0;
/* 4185 */     double d4 = 16.0D;
/* 4186 */     double d5 = -cameraPositionY;
/* 4187 */     bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
/* 4188 */     bufferbuilder.pos(d2, d5, d3).endVertex();
/* 4189 */     bufferbuilder.pos(d2, d4, d3).endVertex();
/* 4190 */     bufferbuilder.pos(d3, d4, d2).endVertex();
/* 4191 */     bufferbuilder.pos(d3, d5, d2).endVertex();
/* 4192 */     bufferbuilder.pos(d3, d5, d2).endVertex();
/* 4193 */     bufferbuilder.pos(d3, d4, d2).endVertex();
/* 4194 */     bufferbuilder.pos(d3, d4, d1).endVertex();
/* 4195 */     bufferbuilder.pos(d3, d5, d1).endVertex();
/* 4196 */     bufferbuilder.pos(d3, d5, d1).endVertex();
/* 4197 */     bufferbuilder.pos(d3, d4, d1).endVertex();
/* 4198 */     bufferbuilder.pos(d2, d4, d1).endVertex();
/* 4199 */     bufferbuilder.pos(d2, d5, d1).endVertex();
/* 4200 */     bufferbuilder.pos(d2, d5, d1).endVertex();
/* 4201 */     bufferbuilder.pos(d2, d4, d1).endVertex();
/* 4202 */     bufferbuilder.pos(d1, d4, d0).endVertex();
/* 4203 */     bufferbuilder.pos(d1, d5, d0).endVertex();
/* 4204 */     bufferbuilder.pos(d1, d5, d0).endVertex();
/* 4205 */     bufferbuilder.pos(d1, d4, d0).endVertex();
/* 4206 */     bufferbuilder.pos(d0, d4, d1).endVertex();
/* 4207 */     bufferbuilder.pos(d0, d5, d1).endVertex();
/* 4208 */     bufferbuilder.pos(d0, d5, d1).endVertex();
/* 4209 */     bufferbuilder.pos(d0, d4, d1).endVertex();
/* 4210 */     bufferbuilder.pos(d0, d4, d2).endVertex();
/* 4211 */     bufferbuilder.pos(d0, d5, d2).endVertex();
/* 4212 */     bufferbuilder.pos(d0, d5, d2).endVertex();
/* 4213 */     bufferbuilder.pos(d0, d4, d2).endVertex();
/* 4214 */     bufferbuilder.pos(d1, d4, d3).endVertex();
/* 4215 */     bufferbuilder.pos(d1, d5, d3).endVertex();
/* 4216 */     bufferbuilder.pos(d1, d5, d3).endVertex();
/* 4217 */     bufferbuilder.pos(d1, d4, d3).endVertex();
/* 4218 */     bufferbuilder.pos(d2, d4, d3).endVertex();
/* 4219 */     bufferbuilder.pos(d2, d5, d3).endVertex();
/* 4220 */     Tessellator.getInstance().draw();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void preSkyList() {
/* 4225 */     setUpPosition();
/* 4226 */     GL11.glColor3f(fogColorR, fogColorG, fogColorB);
/* 4227 */     drawHorizon();
/* 4228 */     GL11.glColor3f(skyColorR, skyColorG, skyColorB);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endSky() {
/* 4233 */     isRenderingSky = false;
/* 4234 */     setDrawBuffers(dfbDrawBuffers);
/* 4235 */     useProgram(lightmapEnabled ? 3 : 2);
/* 4236 */     popEntity();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginUpdateChunks() {
/* 4241 */     checkGLError("beginUpdateChunks1");
/* 4242 */     checkFramebufferStatus("beginUpdateChunks1");
/*      */     
/* 4244 */     if (!isShadowPass)
/*      */     {
/* 4246 */       useProgram(7);
/*      */     }
/*      */     
/* 4249 */     checkGLError("beginUpdateChunks2");
/* 4250 */     checkFramebufferStatus("beginUpdateChunks2");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endUpdateChunks() {
/* 4255 */     checkGLError("endUpdateChunks1");
/* 4256 */     checkFramebufferStatus("endUpdateChunks1");
/*      */     
/* 4258 */     if (!isShadowPass)
/*      */     {
/* 4260 */       useProgram(7);
/*      */     }
/*      */     
/* 4263 */     checkGLError("endUpdateChunks2");
/* 4264 */     checkFramebufferStatus("endUpdateChunks2");
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean shouldRenderClouds(GameSettings gs) {
/* 4269 */     if (!shaderPackLoaded)
/*      */     {
/* 4271 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 4275 */     checkGLError("shouldRenderClouds");
/* 4276 */     return isShadowPass ? configCloudShadow : ((gs.clouds > 0));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void beginClouds() {
/* 4282 */     fogEnabled = true;
/* 4283 */     pushEntity(-3, 0);
/* 4284 */     useProgram(6);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endClouds() {
/* 4289 */     disableFog();
/* 4290 */     popEntity();
/* 4291 */     useProgram(lightmapEnabled ? 3 : 2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginEntities() {
/* 4296 */     if (isRenderingWorld) {
/*      */       
/* 4298 */       useProgram(16);
/* 4299 */       resetDisplayListModels();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void nextEntity(Entity entity) {
/* 4305 */     if (isRenderingWorld) {
/*      */       
/* 4307 */       useProgram(16);
/* 4308 */       setEntityId(entity);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setEntityId(Entity entity) {
/* 4314 */     if (isRenderingWorld && !isShadowPass && uniformEntityId.isDefined()) {
/*      */       
/* 4316 */       int i = EntityUtils.getEntityIdByClass(entity);
/* 4317 */       uniformEntityId.setValue(i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginSpiderEyes() {
/* 4323 */     if (isRenderingWorld && programsID[18] != programsID[0]) {
/*      */       
/* 4325 */       useProgram(18);
/* 4326 */       GlStateManager.enableAlpha();
/* 4327 */       GlStateManager.alphaFunc(516, 0.0F);
/* 4328 */       GlStateManager.blendFunc(770, 771);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endSpiderEyes() {
/* 4334 */     if (isRenderingWorld && programsID[18] != programsID[0]) {
/*      */       
/* 4336 */       useProgram(16);
/* 4337 */       GlStateManager.disableAlpha();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endEntities() {
/* 4343 */     if (isRenderingWorld)
/*      */     {
/* 4345 */       useProgram(lightmapEnabled ? 3 : 2);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setEntityColor(float r, float g, float b, float a) {
/* 4351 */     if (isRenderingWorld && !isShadowPass)
/*      */     {
/* 4353 */       uniformEntityColor.setValue(r, g, b, a);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginLivingDamage() {
/* 4359 */     if (isRenderingWorld) {
/*      */       
/* 4361 */       ShadersTex.bindTexture(defaultTexture);
/*      */       
/* 4363 */       if (!isShadowPass)
/*      */       {
/* 4365 */         setDrawBuffers(drawBuffersColorAtt0);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endLivingDamage() {
/* 4372 */     if (isRenderingWorld && !isShadowPass)
/*      */     {
/* 4374 */       setDrawBuffers(programsDrawBuffers[16]);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginBlockEntities() {
/* 4380 */     if (isRenderingWorld) {
/*      */       
/* 4382 */       checkGLError("beginBlockEntities");
/* 4383 */       useProgram(13);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void nextBlockEntity(TileEntity tileEntity) {
/* 4389 */     if (isRenderingWorld) {
/*      */       
/* 4391 */       checkGLError("nextBlockEntity");
/* 4392 */       useProgram(13);
/* 4393 */       setBlockEntityId(tileEntity);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setBlockEntityId(TileEntity tileEntity) {
/* 4399 */     if (isRenderingWorld && !isShadowPass && uniformBlockEntityId.isDefined()) {
/*      */       
/* 4401 */       Block block = tileEntity.getBlockType();
/* 4402 */       int i = Block.getIdFromBlock(block);
/* 4403 */       uniformBlockEntityId.setValue(i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endBlockEntities() {
/* 4409 */     if (isRenderingWorld) {
/*      */       
/* 4411 */       checkGLError("endBlockEntities");
/* 4412 */       useProgram(lightmapEnabled ? 3 : 2);
/* 4413 */       ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginLitParticles() {
/* 4419 */     useProgram(3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginParticles() {
/* 4424 */     useProgram(2);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endParticles() {
/* 4429 */     useProgram(3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void readCenterDepth() {
/* 4434 */     if (!isShadowPass && centerDepthSmoothEnabled) {
/*      */       
/* 4436 */       tempDirectFloatBuffer.clear();
/* 4437 */       GL11.glReadPixels(renderWidth / 2, renderHeight / 2, 1, 1, 6402, 5126, tempDirectFloatBuffer);
/* 4438 */       centerDepth = tempDirectFloatBuffer.get(0);
/* 4439 */       float f = (float)diffSystemTime * 0.01F;
/* 4440 */       float f1 = (float)Math.exp(Math.log(0.5D) * f / centerDepthSmoothHalflife);
/* 4441 */       centerDepthSmooth = centerDepthSmooth * f1 + centerDepth * (1.0F - f1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginWeather() {
/* 4447 */     if (!isShadowPass) {
/*      */       
/* 4449 */       if (usedDepthBuffers >= 3) {
/*      */         
/* 4451 */         GlStateManager.setActiveTexture(33996);
/* 4452 */         GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
/* 4453 */         GlStateManager.setActiveTexture(33984);
/*      */       } 
/*      */       
/* 4456 */       GlStateManager.enableDepth();
/* 4457 */       GlStateManager.enableBlend();
/* 4458 */       GlStateManager.blendFunc(770, 771);
/* 4459 */       GlStateManager.enableAlpha();
/* 4460 */       useProgram(20);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endWeather() {
/* 4466 */     GlStateManager.disableBlend();
/* 4467 */     useProgram(3);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void preWater() {
/* 4472 */     if (usedDepthBuffers >= 2) {
/*      */       
/* 4474 */       GlStateManager.setActiveTexture(33995);
/* 4475 */       checkGLError("pre copy depth");
/* 4476 */       GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, renderWidth, renderHeight);
/* 4477 */       checkGLError("copy depth");
/* 4478 */       GlStateManager.setActiveTexture(33984);
/*      */     } 
/*      */     
/* 4481 */     ShadersTex.bindNSTextures(defaultTexture.getMultiTexID());
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginWater() {
/* 4486 */     if (isRenderingWorld)
/*      */     {
/* 4488 */       if (!isShadowPass) {
/*      */         
/* 4490 */         useProgram(12);
/* 4491 */         GlStateManager.enableBlend();
/* 4492 */         GlStateManager.depthMask(true);
/*      */       }
/*      */       else {
/*      */         
/* 4496 */         GlStateManager.depthMask(true);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endWater() {
/* 4503 */     if (isRenderingWorld) {
/*      */       
/* 4505 */       if (isShadowPass);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 4510 */       useProgram(lightmapEnabled ? 3 : 2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginProjectRedHalo() {
/* 4516 */     if (isRenderingWorld)
/*      */     {
/* 4518 */       useProgram(1);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endProjectRedHalo() {
/* 4524 */     if (isRenderingWorld)
/*      */     {
/* 4526 */       useProgram(3);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void applyHandDepth() {
/* 4532 */     if (configHandDepthMul != 1.0D)
/*      */     {
/* 4534 */       GL11.glScaled(1.0D, 1.0D, configHandDepthMul);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginHand() {
/* 4540 */     GL11.glMatrixMode(5888);
/* 4541 */     GL11.glPushMatrix();
/* 4542 */     GL11.glMatrixMode(5889);
/* 4543 */     GL11.glPushMatrix();
/* 4544 */     GL11.glMatrixMode(5888);
/* 4545 */     useProgram(19);
/* 4546 */     checkGLError("beginHand");
/* 4547 */     checkFramebufferStatus("beginHand");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endHand() {
/* 4552 */     checkGLError("pre endHand");
/* 4553 */     checkFramebufferStatus("pre endHand");
/* 4554 */     GL11.glMatrixMode(5889);
/* 4555 */     GL11.glPopMatrix();
/* 4556 */     GL11.glMatrixMode(5888);
/* 4557 */     GL11.glPopMatrix();
/* 4558 */     GlStateManager.blendFunc(770, 771);
/* 4559 */     checkGLError("endHand");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginFPOverlay() {
/* 4564 */     GlStateManager.disableLighting();
/* 4565 */     GlStateManager.disableBlend();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void endFPOverlay() {}
/*      */ 
/*      */   
/*      */   public static void glEnableWrapper(int cap) {
/* 4574 */     GL11.glEnable(cap);
/*      */     
/* 4576 */     if (cap == 3553) {
/*      */       
/* 4578 */       enableTexture2D();
/*      */     }
/* 4580 */     else if (cap == 2912) {
/*      */       
/* 4582 */       enableFog();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void glDisableWrapper(int cap) {
/* 4588 */     GL11.glDisable(cap);
/*      */     
/* 4590 */     if (cap == 3553) {
/*      */       
/* 4592 */       disableTexture2D();
/*      */     }
/* 4594 */     else if (cap == 2912) {
/*      */       
/* 4596 */       disableFog();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglEnableT2D(int cap) {
/* 4602 */     GL11.glEnable(cap);
/* 4603 */     enableTexture2D();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglDisableT2D(int cap) {
/* 4608 */     GL11.glDisable(cap);
/* 4609 */     disableTexture2D();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglEnableFog(int cap) {
/* 4614 */     GL11.glEnable(cap);
/* 4615 */     enableFog();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglDisableFog(int cap) {
/* 4620 */     GL11.glDisable(cap);
/* 4621 */     disableFog();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableTexture2D() {
/* 4626 */     if (isRenderingSky) {
/*      */       
/* 4628 */       useProgram(5);
/*      */     }
/* 4630 */     else if (activeProgram == 1) {
/*      */       
/* 4632 */       useProgram(lightmapEnabled ? 3 : 2);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableTexture2D() {
/* 4638 */     if (isRenderingSky) {
/*      */       
/* 4640 */       useProgram(4);
/*      */     }
/* 4642 */     else if (activeProgram == 2 || activeProgram == 3) {
/*      */       
/* 4644 */       useProgram(1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginLeash() {
/* 4650 */     useProgram(1);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endLeash() {
/* 4655 */     useProgram(16);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableFog() {
/* 4660 */     fogEnabled = true;
/* 4661 */     setProgramUniform1i("fogMode", fogMode);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableFog() {
/* 4666 */     fogEnabled = false;
/* 4667 */     setProgramUniform1i("fogMode", 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setFog(GlStateManager.FogMode fogMode) {
/* 4672 */     GlStateManager.setFog(fogMode);
/*      */ 
/*      */     
/* 4675 */     if (fogEnabled)
/*      */     {
/* 4677 */       setProgramUniform1i("fogMode", fogMode.capabilityId);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void sglFogi(int pname, int param) {
/* 4683 */     GL11.glFogi(pname, param);
/*      */     
/* 4685 */     if (pname == 2917) {
/*      */       
/* 4687 */       fogMode = param;
/*      */       
/* 4689 */       if (fogEnabled)
/*      */       {
/* 4691 */         setProgramUniform1i("fogMode", fogMode);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void enableLightmap() {
/* 4698 */     lightmapEnabled = true;
/*      */     
/* 4700 */     if (activeProgram == 2)
/*      */     {
/* 4702 */       useProgram(3);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void disableLightmap() {
/* 4708 */     lightmapEnabled = false;
/*      */     
/* 4710 */     if (activeProgram == 3)
/*      */     {
/* 4712 */       useProgram(2);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getEntityData() {
/* 4718 */     return entityData[entityDataIndex * 2];
/*      */   }
/*      */ 
/*      */   
/*      */   public static int getEntityData2() {
/* 4723 */     return entityData[entityDataIndex * 2 + 1];
/*      */   }
/*      */ 
/*      */   
/*      */   public static int setEntityData1(int data1) {
/* 4728 */     entityData[entityDataIndex * 2] = entityData[entityDataIndex * 2] & 0xFFFF | data1 << 16;
/* 4729 */     return data1;
/*      */   }
/*      */ 
/*      */   
/*      */   public static int setEntityData2(int data2) {
/* 4734 */     entityData[entityDataIndex * 2 + 1] = entityData[entityDataIndex * 2 + 1] & 0xFFFF0000 | data2 & 0xFFFF;
/* 4735 */     return data2;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushEntity(int data0, int data1) {
/* 4740 */     entityDataIndex++;
/* 4741 */     entityData[entityDataIndex * 2] = data0 & 0xFFFF | data1 << 16;
/* 4742 */     entityData[entityDataIndex * 2 + 1] = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushEntity(int data0) {
/* 4747 */     entityDataIndex++;
/* 4748 */     entityData[entityDataIndex * 2] = data0 & 0xFFFF;
/* 4749 */     entityData[entityDataIndex * 2 + 1] = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void pushEntity(Block block) {
/* 4754 */     entityDataIndex++;
/* 4755 */     int i = block.getRenderType(block.getDefaultState()).ordinal();
/* 4756 */     entityData[entityDataIndex * 2] = Block.REGISTRY.getIDForObject(block) & 0xFFFF | i << 16;
/* 4757 */     entityData[entityDataIndex * 2 + 1] = 0;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void popEntity() {
/* 4762 */     entityData[entityDataIndex * 2] = 0;
/* 4763 */     entityData[entityDataIndex * 2 + 1] = 0;
/* 4764 */     entityDataIndex--;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void mcProfilerEndSection() {
/* 4769 */     mc.mcProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   public static String getShaderPackName() {
/* 4774 */     if (shaderPack == null)
/*      */     {
/* 4776 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 4780 */     return (shaderPack instanceof ShaderPackNone) ? null : shaderPack.getName();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static InputStream getShaderPackResourceStream(String path) {
/* 4786 */     return (shaderPack == null) ? null : shaderPack.getResourceAsStream(path);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void nextAntialiasingLevel() {
/* 4791 */     configAntialiasingLevel += 2;
/* 4792 */     configAntialiasingLevel = configAntialiasingLevel / 2 * 2;
/*      */     
/* 4794 */     if (configAntialiasingLevel > 4)
/*      */     {
/* 4796 */       configAntialiasingLevel = 0;
/*      */     }
/*      */     
/* 4799 */     configAntialiasingLevel = Config.limit(configAntialiasingLevel, 0, 4);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void checkShadersModInstalled() {
/*      */     try {
/* 4806 */       Class<?> clazz = Class.forName("shadersmod.transform.SMCClassTransformer");
/*      */     }
/* 4808 */     catch (Throwable var1) {
/*      */       return;
/*      */     } 
/*      */ 
/*      */     
/* 4813 */     throw new RuntimeException("Shaders Mod detected. Please remove it, OptiFine has built-in support for shaders.");
/*      */   }
/*      */ 
/*      */   
/*      */   public static void resourcesReloaded() {
/* 4818 */     loadShaderPackResources();
/*      */   }
/*      */ 
/*      */   
/*      */   private static void loadShaderPackResources() {
/* 4823 */     shaderPackResources = new HashMap<>();
/*      */     
/* 4825 */     if (shaderPackLoaded) {
/*      */       
/* 4827 */       List<String> list = new ArrayList<>();
/* 4828 */       String s = "/shaders/lang/";
/* 4829 */       String s1 = "en_US";
/* 4830 */       String s2 = ".lang";
/* 4831 */       list.add(String.valueOf(s) + s1 + s2);
/*      */       
/* 4833 */       if (!(Config.getGameSettings()).language.equals(s1))
/*      */       {
/* 4835 */         list.add(String.valueOf(s) + (Config.getGameSettings()).language + s2);
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/* 4840 */         for (String s3 : list) {
/*      */           
/* 4842 */           InputStream inputstream = shaderPack.getResourceAsStream(s3);
/*      */           
/* 4844 */           if (inputstream != null) {
/*      */             
/* 4846 */             Properties properties = new Properties();
/* 4847 */             Lang.loadLocaleData(inputstream, properties);
/* 4848 */             inputstream.close();
/*      */             
/* 4850 */             for (Object s4 : properties.keySet())
/*      */             {
/* 4852 */               String s5 = properties.getProperty((String)s4);
/* 4853 */               shaderPackResources.put((String)s4, s5);
/*      */             }
/*      */           
/*      */           } 
/*      */         } 
/* 4858 */       } catch (IOException ioexception) {
/*      */         
/* 4860 */         ioexception.printStackTrace();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static String translate(String key, String def) {
/* 4867 */     String s = shaderPackResources.get(key);
/* 4868 */     return (s == null) ? def : s;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isProgramPath(String program) {
/* 4873 */     if (program == null)
/*      */     {
/* 4875 */       return false;
/*      */     }
/* 4877 */     if (program.length() <= 0)
/*      */     {
/* 4879 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 4883 */     int i = program.lastIndexOf("/");
/*      */     
/* 4885 */     if (i >= 0)
/*      */     {
/* 4887 */       program = program.substring(i + 1);
/*      */     }
/*      */     
/* 4890 */     return Arrays.<String>asList(programNames).contains(program);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void setItemToRenderMain(ItemStack itemToRenderMain) {
/* 4896 */     itemToRenderMainTranslucent = isTranslucentBlock(itemToRenderMain);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setItemToRenderOff(ItemStack itemToRenderOff) {
/* 4901 */     itemToRenderOffTranslucent = isTranslucentBlock(itemToRenderOff);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isItemToRenderMainTranslucent() {
/* 4906 */     return itemToRenderMainTranslucent;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isItemToRenderOffTranslucent() {
/* 4911 */     return itemToRenderOffTranslucent;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isBothHandsRendered() {
/* 4916 */     return (isHandRenderedMain && isHandRenderedOff);
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean isTranslucentBlock(ItemStack stack) {
/* 4921 */     if (stack == null)
/*      */     {
/* 4923 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 4927 */     Item item = stack.getItem();
/*      */     
/* 4929 */     if (item == null)
/*      */     {
/* 4931 */       return false;
/*      */     }
/* 4933 */     if (!(item instanceof ItemBlock))
/*      */     {
/* 4935 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 4939 */     ItemBlock itemblock = (ItemBlock)item;
/* 4940 */     Block block = itemblock.getBlock();
/*      */     
/* 4942 */     if (block == null)
/*      */     {
/* 4944 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 4948 */     BlockRenderLayer blockrenderlayer = block.getBlockLayer();
/* 4949 */     return (blockrenderlayer == BlockRenderLayer.TRANSLUCENT);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isSkipRenderHand(EnumHand hand) {
/* 4957 */     if (hand == EnumHand.MAIN_HAND && skipRenderHandMain)
/*      */     {
/* 4959 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 4963 */     return (hand == EnumHand.OFF_HAND && skipRenderHandOff);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isRenderBothHands() {
/* 4969 */     return (!skipRenderHandMain && !skipRenderHandOff);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setSkipRenderHands(boolean skipMain, boolean skipOff) {
/* 4974 */     skipRenderHandMain = skipMain;
/* 4975 */     skipRenderHandOff = skipOff;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setHandsRendered(boolean handMain, boolean handOff) {
/* 4980 */     isHandRenderedMain = handMain;
/* 4981 */     isHandRenderedOff = handOff;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isHandRenderedMain() {
/* 4986 */     return isHandRenderedMain;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isHandRenderedOff() {
/* 4991 */     return isHandRenderedOff;
/*      */   }
/*      */ 
/*      */   
/*      */   public static float getShadowRenderDistance() {
/* 4996 */     return (shadowDistanceRenderMul < 0.0F) ? -1.0F : (shadowMapHalfPlane * shadowDistanceRenderMul);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void setRenderingFirstPersonHand(boolean flag) {
/* 5001 */     isRenderingFirstPersonHand = flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean isRenderingFirstPersonHand() {
/* 5006 */     return isRenderingFirstPersonHand;
/*      */   }
/*      */ 
/*      */   
/*      */   public static void beginBeacon() {
/* 5011 */     if (isRenderingWorld)
/*      */     {
/* 5013 */       useProgram(14);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void endBeacon() {
/* 5019 */     if (isRenderingWorld)
/*      */     {
/* 5021 */       useProgram(13);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   static {
/* 5027 */     shadersdir = new File((Minecraft.getMinecraft()).mcDataDir, "shaders");
/* 5028 */     shaderpacksdir = new File((Minecraft.getMinecraft()).mcDataDir, shaderpacksdirname);
/* 5029 */     configFile = new File((Minecraft.getMinecraft()).mcDataDir, optionsfilename);
/* 5030 */     drawBuffersNone.limit(0);
/* 5031 */     drawBuffersColorAtt0.put(36064).position(0).limit(1);
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\shadersmod\client\Shaders.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */