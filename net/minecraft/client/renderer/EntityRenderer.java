/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.base.Predicates;
/*      */ import com.google.gson.JsonSyntaxException;
/*      */ import java.awt.Graphics;
/*      */ import java.awt.image.BufferedImage;
/*      */ import java.io.IOException;
/*      */ import java.nio.FloatBuffer;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import javax.annotation.Nullable;
/*      */ import javax.imageio.ImageIO;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockBed;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.entity.AbstractClientPlayer;
/*      */ import net.minecraft.client.gui.FontRenderer;
/*      */ import net.minecraft.client.gui.Gui;
/*      */ import net.minecraft.client.gui.GuiMainMenu;
/*      */ import net.minecraft.client.gui.MapItemRenderer;
/*      */ import net.minecraft.client.gui.ScaledResolution;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.particle.ParticleManager;
/*      */ import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
/*      */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelper;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelperImpl;
/*      */ import net.minecraft.client.renderer.culling.Frustum;
/*      */ import net.minecraft.client.renderer.culling.ICamera;
/*      */ import net.minecraft.client.renderer.texture.DynamicTexture;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.client.shader.ShaderGroup;
/*      */ import net.minecraft.client.shader.ShaderLinkHelper;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.crash.ICrashReportDetail;
/*      */ import net.minecraft.enchantment.EnchantmentHelper;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.passive.EntityAnimal;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.MobEffects;
/*      */ import net.minecraft.init.SoundEvents;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.server.integrated.IntegratedServer;
/*      */ import net.minecraft.util.BlockRenderLayer;
/*      */ import net.minecraft.util.EntitySelectors;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.MouseFilter;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.ScreenShotHelper;
/*      */ import net.minecraft.util.SoundCategory;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.RayTraceResult;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.TextComponentString;
/*      */ import net.minecraft.world.GameType;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProvider;
/*      */ import net.minecraft.world.biome.Biome;
/*      */ import optifine.Config;
/*      */ import optifine.CustomColors;
/*      */ import optifine.Lagometer;
/*      */ import optifine.RandomMobs;
/*      */ import optifine.Reflector;
/*      */ import optifine.ReflectorForge;
/*      */ import optifine.TextureUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.input.Mouse;
/*      */ import org.lwjgl.opengl.Display;
/*      */ import org.lwjgl.opengl.GL11;
/*      */ import org.lwjgl.opengl.GLContext;
/*      */ import org.lwjgl.util.glu.GLU;
/*      */ import org.lwjgl.util.glu.Project;
/*      */ import shadersmod.client.Shaders;
/*      */ import shadersmod.client.ShadersRender;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class EntityRenderer
/*      */   implements IResourceManagerReloadListener
/*      */ {
/*  110 */   private static final Logger LOGGER = LogManager.getLogger();
/*  111 */   private static final ResourceLocation RAIN_TEXTURES = new ResourceLocation("textures/environment/rain.png");
/*  112 */   private static final ResourceLocation SNOW_TEXTURES = new ResourceLocation("textures/environment/snow.png");
/*      */   
/*      */   public static boolean anaglyphEnable;
/*      */   
/*      */   public static int anaglyphField;
/*      */   
/*      */   private final Minecraft mc;
/*      */   
/*      */   private final IResourceManager resourceManager;
/*  121 */   private final Random random = new Random();
/*      */   
/*      */   private float farPlaneDistance;
/*      */   
/*      */   public ItemRenderer itemRenderer;
/*      */   
/*      */   private final MapItemRenderer theMapItemRenderer;
/*      */   
/*      */   private int rendererUpdateCount;
/*      */   private Entity pointedEntity;
/*  131 */   private MouseFilter mouseFilterXAxis = new MouseFilter();
/*  132 */   private MouseFilter mouseFilterYAxis = new MouseFilter();
/*  133 */   private final float thirdPersonDistance = 4.0F;
/*      */ 
/*      */   
/*  136 */   private float thirdPersonDistancePrev = 4.0F;
/*      */ 
/*      */   
/*      */   private float smoothCamYaw;
/*      */ 
/*      */   
/*      */   private float smoothCamPitch;
/*      */ 
/*      */   
/*      */   private float smoothCamFilterX;
/*      */ 
/*      */   
/*      */   private float smoothCamFilterY;
/*      */   
/*      */   private float smoothCamPartialTicks;
/*      */   
/*      */   private float fovModifierHand;
/*      */   
/*      */   private float fovModifierHandPrev;
/*      */   
/*      */   private float bossColorModifier;
/*      */   
/*      */   private float bossColorModifierPrev;
/*      */   
/*      */   private boolean cloudFog;
/*      */   
/*      */   private boolean renderHand = true;
/*      */   
/*      */   private boolean drawBlockOutline = true;
/*      */   
/*      */   private long timeWorldIcon;
/*      */   
/*  168 */   private long prevFrameTime = Minecraft.getSystemTime();
/*      */ 
/*      */ 
/*      */   
/*      */   private long renderEndNanoTime;
/*      */ 
/*      */   
/*      */   private final DynamicTexture lightmapTexture;
/*      */ 
/*      */   
/*      */   private final int[] lightmapColors;
/*      */ 
/*      */   
/*      */   private final ResourceLocation locationLightMap;
/*      */ 
/*      */   
/*      */   private boolean lightmapUpdateNeeded;
/*      */ 
/*      */   
/*      */   private float torchFlickerX;
/*      */ 
/*      */   
/*      */   private float torchFlickerDX;
/*      */ 
/*      */   
/*      */   private int rainSoundCounter;
/*      */ 
/*      */   
/*  196 */   private final float[] rainXCoords = new float[1024];
/*  197 */   private final float[] rainYCoords = new float[1024];
/*      */ 
/*      */   
/*  200 */   private final FloatBuffer fogColorBuffer = GLAllocation.createDirectFloatBuffer(16);
/*      */   
/*      */   public float fogColorRed;
/*      */   
/*      */   public float fogColorGreen;
/*      */   
/*      */   public float fogColorBlue;
/*      */   
/*      */   private float fogColor2;
/*      */   private float fogColor1;
/*      */   private int debugViewDirection;
/*      */   private boolean debugView;
/*  212 */   private double cameraZoom = 1.0D;
/*      */   private double cameraYaw;
/*      */   private double cameraPitch;
/*      */   private ItemStack field_190566_ab;
/*      */   private int field_190567_ac;
/*      */   private float field_190568_ad;
/*      */   private float field_190569_ae;
/*      */   private ShaderGroup theShaderGroup;
/*  220 */   private static final ResourceLocation[] SHADERS_TEXTURES = new ResourceLocation[] { 
/*  221 */       new ResourceLocation("shaders/post/notch.json"), new ResourceLocation("shaders/post/fxaa.json"), 
/*  222 */       new ResourceLocation("shaders/post/art.json"), new ResourceLocation("shaders/post/bumpy.json"), 
/*  223 */       new ResourceLocation("shaders/post/blobs2.json"), new ResourceLocation("shaders/post/pencil.json"), 
/*  224 */       new ResourceLocation("shaders/post/color_convolve.json"), 
/*  225 */       new ResourceLocation("shaders/post/deconverge.json"), new ResourceLocation("shaders/post/flip.json"), 
/*  226 */       new ResourceLocation("shaders/post/invert.json"), new ResourceLocation("shaders/post/ntsc.json"), 
/*  227 */       new ResourceLocation("shaders/post/outline.json"), new ResourceLocation("shaders/post/phosphor.json"), 
/*  228 */       new ResourceLocation("shaders/post/scan_pincushion.json"), new ResourceLocation("shaders/post/sobel.json"), 
/*  229 */       new ResourceLocation("shaders/post/bits.json"), new ResourceLocation("shaders/post/desaturate.json"), 
/*  230 */       new ResourceLocation("shaders/post/green.json"), new ResourceLocation("shaders/post/blur.json"), 
/*  231 */       new ResourceLocation("shaders/post/wobble.json"), new ResourceLocation("shaders/post/blobs.json"), 
/*  232 */       new ResourceLocation("shaders/post/antialias.json"), new ResourceLocation("shaders/post/creeper.json"), 
/*  233 */       new ResourceLocation("shaders/post/spider.json") };
/*  234 */   public static final int SHADER_COUNT = SHADERS_TEXTURES.length;
/*      */   private int shaderIndex;
/*      */   private boolean useShader;
/*      */   public int frameCount;
/*      */   private boolean initialized = false;
/*  239 */   private World updatedWorld = null;
/*      */   public boolean fogStandard = false;
/*  241 */   private float clipDistance = 128.0F;
/*  242 */   private long lastServerTime = 0L;
/*  243 */   private int lastServerTicks = 0;
/*  244 */   private int serverWaitTime = 0;
/*  245 */   private int serverWaitTimeCurrent = 0;
/*  246 */   private float avgServerTimeDiff = 0.0F;
/*  247 */   private float avgServerTickDiff = 0.0F;
/*  248 */   private long lastErrorCheckTimeMs = 0L;
/*  249 */   private ShaderGroup[] fxaaShaders = new ShaderGroup[10];
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean loadVisibleChunks = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private float zoomCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isShaderActive() {
/*  275 */     return (OpenGlHelper.shadersSupported && this.theShaderGroup != null);
/*      */   }
/*      */   
/*      */   public void stopUseShader() {
/*  279 */     if (this.theShaderGroup != null) {
/*  280 */       this.theShaderGroup.deleteShaderGroup();
/*      */     }
/*      */     
/*  283 */     this.theShaderGroup = null;
/*  284 */     this.shaderIndex = SHADER_COUNT;
/*      */   }
/*      */   
/*      */   public void switchUseShader() {
/*  288 */     this.useShader = !this.useShader;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadEntityShader(@Nullable Entity entityIn) {
/*  295 */     if (OpenGlHelper.shadersSupported) {
/*  296 */       if (this.theShaderGroup != null) {
/*  297 */         this.theShaderGroup.deleteShaderGroup();
/*      */       }
/*      */       
/*  300 */       this.theShaderGroup = null;
/*      */       
/*  302 */       if (entityIn instanceof net.minecraft.entity.monster.EntityCreeper) {
/*  303 */         loadShader(new ResourceLocation("shaders/post/creeper.json"));
/*  304 */       } else if (entityIn instanceof net.minecraft.entity.monster.EntitySpider) {
/*  305 */         loadShader(new ResourceLocation("shaders/post/spider.json"));
/*  306 */       } else if (entityIn instanceof net.minecraft.entity.monster.EntityEnderman) {
/*  307 */         loadShader(new ResourceLocation("shaders/post/invert.json"));
/*  308 */       } else if (Reflector.ForgeHooksClient_loadEntityShader.exists()) {
/*  309 */         Reflector.call(Reflector.ForgeHooksClient_loadEntityShader, new Object[] { entityIn, this });
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void loadShader(ResourceLocation resourceLocationIn) {
/*  315 */     if (OpenGlHelper.isFramebufferEnabled()) {
/*      */       try {
/*  317 */         this.theShaderGroup = new ShaderGroup(this.mc.getTextureManager(), this.resourceManager, 
/*  318 */             this.mc.getFramebuffer(), resourceLocationIn);
/*  319 */         this.theShaderGroup.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
/*  320 */         this.useShader = true;
/*  321 */       } catch (IOException ioexception) {
/*  322 */         LOGGER.warn("Failed to load shader: {}", resourceLocationIn, ioexception);
/*  323 */         this.shaderIndex = SHADER_COUNT;
/*  324 */         this.useShader = false;
/*  325 */       } catch (JsonSyntaxException jsonsyntaxexception) {
/*  326 */         LOGGER.warn("Failed to load shader: {}", resourceLocationIn, jsonsyntaxexception);
/*  327 */         this.shaderIndex = SHADER_COUNT;
/*  328 */         this.useShader = false;
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  334 */     if (this.theShaderGroup != null) {
/*  335 */       this.theShaderGroup.deleteShaderGroup();
/*      */     }
/*      */     
/*  338 */     this.theShaderGroup = null;
/*      */     
/*  340 */     if (this.shaderIndex == SHADER_COUNT) {
/*  341 */       loadEntityShader(this.mc.getRenderViewEntity());
/*      */     } else {
/*  343 */       loadShader(SHADERS_TEXTURES[this.shaderIndex]);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateRenderer() {
/*  351 */     if (OpenGlHelper.shadersSupported && ShaderLinkHelper.getStaticShaderLinkHelper() == null) {
/*  352 */       ShaderLinkHelper.setNewStaticShaderLinkHelper();
/*      */     }
/*      */     
/*  355 */     updateFovModifierHand();
/*  356 */     updateTorchFlicker();
/*  357 */     this.fogColor2 = this.fogColor1;
/*  358 */     this.thirdPersonDistancePrev = 4.0F;
/*      */     
/*  360 */     if (this.mc.gameSettings.smoothCamera) {
/*  361 */       float f = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
/*  362 */       float f1 = f * f * f * 8.0F;
/*  363 */       this.smoothCamFilterX = this.mouseFilterXAxis.smooth(this.smoothCamYaw, 0.05F * f1);
/*  364 */       this.smoothCamFilterY = this.mouseFilterYAxis.smooth(this.smoothCamPitch, 0.05F * f1);
/*  365 */       this.smoothCamPartialTicks = 0.0F;
/*  366 */       this.smoothCamYaw = 0.0F;
/*  367 */       this.smoothCamPitch = 0.0F;
/*      */     } else {
/*  369 */       this.smoothCamFilterX = 0.0F;
/*  370 */       this.smoothCamFilterY = 0.0F;
/*  371 */       this.mouseFilterXAxis.reset();
/*  372 */       this.mouseFilterYAxis.reset();
/*      */     } 
/*      */     
/*  375 */     if (this.mc.getRenderViewEntity() == null) {
/*  376 */       this.mc.setRenderViewEntity((Entity)this.mc.player);
/*      */     }
/*      */     
/*  379 */     Entity entity = this.mc.getRenderViewEntity();
/*  380 */     double d2 = entity.posX;
/*  381 */     double d0 = entity.posY + entity.getEyeHeight();
/*  382 */     double d1 = entity.posZ;
/*  383 */     float f2 = this.mc.world.getLightBrightness(new BlockPos(d2, d0, d1));
/*  384 */     float f3 = this.mc.gameSettings.renderDistanceChunks / 16.0F;
/*  385 */     f3 = MathHelper.clamp(f3, 0.0F, 1.0F);
/*  386 */     float f4 = f2 * (1.0F - f3) + f3;
/*  387 */     this.fogColor1 += (f4 - this.fogColor1) * 0.1F;
/*  388 */     this.rendererUpdateCount++;
/*  389 */     this.itemRenderer.updateEquippedItem();
/*  390 */     addRainParticles();
/*  391 */     this.bossColorModifierPrev = this.bossColorModifier;
/*      */     
/*  393 */     if (this.mc.ingameGUI.getBossOverlay().shouldDarkenSky()) {
/*  394 */       this.bossColorModifier += 0.05F;
/*      */       
/*  396 */       if (this.bossColorModifier > 1.0F) {
/*  397 */         this.bossColorModifier = 1.0F;
/*      */       }
/*  399 */     } else if (this.bossColorModifier > 0.0F) {
/*  400 */       this.bossColorModifier -= 0.0125F;
/*      */     } 
/*      */     
/*  403 */     if (this.field_190567_ac > 0) {
/*  404 */       this.field_190567_ac--;
/*      */       
/*  406 */       if (this.field_190567_ac == 0) {
/*  407 */         this.field_190566_ab = null;
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public ShaderGroup getShaderGroup() {
/*  413 */     return this.theShaderGroup;
/*      */   }
/*      */   
/*      */   public void updateShaderGroupSize(int width, int height) {
/*  417 */     if (OpenGlHelper.shadersSupported) {
/*  418 */       if (this.theShaderGroup != null) {
/*  419 */         this.theShaderGroup.createBindFramebuffers(width, height);
/*      */       }
/*      */       
/*  422 */       this.mc.renderGlobal.createBindEntityOutlineFbs(width, height);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getMouseOver(float partialTicks) {
/*  430 */     Entity entity = this.mc.getRenderViewEntity();
/*      */     
/*  432 */     if (entity != null && this.mc.world != null) {
/*  433 */       this.mc.mcProfiler.startSection("pick");
/*  434 */       this.mc.pointedEntity = null;
/*  435 */       double d0 = this.mc.playerController.getBlockReachDistance();
/*  436 */       this.mc.objectMouseOver = entity.rayTrace(d0, partialTicks);
/*  437 */       Vec3d vec3d = entity.getPositionEyes(partialTicks);
/*  438 */       boolean flag = false;
/*  439 */       int i = 3;
/*  440 */       double d1 = d0;
/*      */       
/*  442 */       if (this.mc.playerController.extendedReach()) {
/*  443 */         d1 = 6.0D;
/*  444 */         d0 = d1;
/*  445 */       } else if (d0 > 3.0D) {
/*  446 */         flag = true;
/*      */       } 
/*      */       
/*  449 */       if (this.mc.objectMouseOver != null) {
/*  450 */         d1 = this.mc.objectMouseOver.hitVec.distanceTo(vec3d);
/*      */       }
/*      */       
/*  453 */       Vec3d vec3d1 = entity.getLook(1.0F);
/*  454 */       Vec3d vec3d2 = vec3d.addVector(vec3d1.xCoord * d0, vec3d1.yCoord * d0, vec3d1.zCoord * d0);
/*  455 */       this.pointedEntity = null;
/*  456 */       Vec3d vec3d3 = null;
/*  457 */       float f = 1.0F;
/*  458 */       List<Entity> list = this.mc.world.getEntitiesInAABBexcluding(entity, 
/*  459 */           entity.getEntityBoundingBox().addCoord(vec3d1.xCoord * d0, vec3d1.yCoord * d0, vec3d1.zCoord * d0)
/*  460 */           .expand(1.0D, 1.0D, 1.0D), 
/*  461 */           Predicates.and(EntitySelectors.NOT_SPECTATING, new Predicate<Entity>() {
/*      */               public boolean apply(@Nullable Entity p_apply_1_) {
/*  463 */                 return (p_apply_1_ != null && p_apply_1_.canBeCollidedWith());
/*      */               }
/*      */             }));
/*  466 */       double d2 = d1;
/*      */       
/*  468 */       for (int j = 0; j < list.size(); j++) {
/*  469 */         Entity entity1 = list.get(j);
/*  470 */         AxisAlignedBB axisalignedbb = entity1.getEntityBoundingBox()
/*  471 */           .expandXyz(entity1.getCollisionBorderSize());
/*  472 */         RayTraceResult raytraceresult = axisalignedbb.calculateIntercept(vec3d, vec3d2);
/*      */         
/*  474 */         if (axisalignedbb.isVecInside(vec3d)) {
/*  475 */           if (d2 >= 0.0D) {
/*  476 */             this.pointedEntity = entity1;
/*  477 */             vec3d3 = (raytraceresult == null) ? vec3d : raytraceresult.hitVec;
/*  478 */             d2 = 0.0D;
/*      */           } 
/*  480 */         } else if (raytraceresult != null) {
/*  481 */           double d3 = vec3d.distanceTo(raytraceresult.hitVec);
/*      */           
/*  483 */           if (d3 < d2 || d2 == 0.0D) {
/*  484 */             boolean flag1 = false;
/*      */             
/*  486 */             if (Reflector.ForgeEntity_canRiderInteract.exists()) {
/*  487 */               flag1 = Reflector.callBoolean(entity1, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
/*      */             }
/*      */             
/*  490 */             if (!flag1 && entity1.getLowestRidingEntity() == entity.getLowestRidingEntity()) {
/*  491 */               if (d2 == 0.0D) {
/*  492 */                 this.pointedEntity = entity1;
/*  493 */                 vec3d3 = raytraceresult.hitVec;
/*      */               } 
/*      */             } else {
/*  496 */               this.pointedEntity = entity1;
/*  497 */               vec3d3 = raytraceresult.hitVec;
/*  498 */               d2 = d3;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/*  504 */       if (this.pointedEntity != null && flag && vec3d.distanceTo(vec3d3) > 3.0D) {
/*  505 */         this.pointedEntity = null;
/*  506 */         this.mc.objectMouseOver = new RayTraceResult(RayTraceResult.Type.MISS, vec3d3, null, 
/*  507 */             new BlockPos(vec3d3));
/*      */       } 
/*      */       
/*  510 */       if (this.pointedEntity != null && (d2 < d1 || this.mc.objectMouseOver == null)) {
/*  511 */         this.mc.objectMouseOver = new RayTraceResult(this.pointedEntity, vec3d3);
/*      */         
/*  513 */         if (this.pointedEntity instanceof EntityLivingBase || this.pointedEntity instanceof net.minecraft.entity.item.EntityItemFrame) {
/*  514 */           this.mc.pointedEntity = this.pointedEntity;
/*      */         }
/*      */       } 
/*      */       
/*  518 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateFovModifierHand() {
/*  526 */     float f = 1.0F;
/*      */     
/*  528 */     if (this.mc.getRenderViewEntity() instanceof AbstractClientPlayer) {
/*  529 */       AbstractClientPlayer abstractclientplayer = (AbstractClientPlayer)this.mc.getRenderViewEntity();
/*  530 */       f = abstractclientplayer.getFovModifier();
/*      */     } 
/*      */     
/*  533 */     this.fovModifierHandPrev = this.fovModifierHand;
/*  534 */     this.fovModifierHand += (f - this.fovModifierHand) * 0.5F;
/*      */     
/*  536 */     if (this.fovModifierHand > 1.5F) {
/*  537 */       this.fovModifierHand = 1.5F;
/*      */     }
/*      */     
/*  540 */     if (this.fovModifierHand < 0.1F) {
/*  541 */       this.fovModifierHand = 0.1F;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityRenderer(Minecraft mcIn, IResourceManager resourceManagerIn) {
/*  550 */     this.zoomCount = 1.0F; this.shaderIndex = SHADER_COUNT; this.mc = mcIn; this.resourceManager = resourceManagerIn; this.itemRenderer = mcIn.getItemRenderer(); this.theMapItemRenderer = new MapItemRenderer(mcIn.getTextureManager()); this.lightmapTexture = new DynamicTexture(16, 16); this.locationLightMap = mcIn.getTextureManager().getDynamicTextureLocation("lightMap", this.lightmapTexture); this.lightmapColors = this.lightmapTexture.getTextureData(); this.theShaderGroup = null; for (int i = 0; i < 32; i++) { for (int j = 0; j < 32; j++) {
/*      */         float f = (j - 16); float f1 = (i - 16); float f2 = MathHelper.sqrt(f * f + f1 * f1); this.rainXCoords[i << 5 | j] = -f1 / f2; this.rainYCoords[i << 5 | j] = f / f2;
/*      */       }  }
/*  553 */      } private float getFOVModifier(float partialTicks, boolean useFOVSetting) { if (this.debugView) {
/*  554 */       return 90.0F;
/*      */     }
/*  556 */     Entity entity = this.mc.getRenderViewEntity();
/*  557 */     float f = 70.0F;
/*      */     
/*  559 */     if (useFOVSetting) {
/*  560 */       f = this.mc.gameSettings.fovSetting;
/*      */       
/*  562 */       if (Config.isDynamicFov()) {
/*  563 */         f *= this.fovModifierHandPrev + (this.fovModifierHand - this.fovModifierHandPrev) * partialTicks;
/*      */       }
/*      */     } 
/*      */     
/*  567 */     boolean flag = false;
/*      */     
/*  569 */     if (this.mc.currentScreen == null) {
/*  570 */       GameSettings gamesettings = this.mc.gameSettings;
/*  571 */       flag = GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom);
/*      */     } 
/*      */     
/*  574 */     if (flag) {
/*  575 */       if (!Config.zoomMode) {
/*  576 */         Config.zoomMode = true;
/*  577 */         this.mc.gameSettings.smoothCamera = true;
/*  578 */         this.mc.renderGlobal.displayListEntitiesDirty = true;
/*      */       } 
/*      */       
/*  581 */       if (Config.zoomMode && 
/*  582 */         this.zoomCount < 4.0F) {
/*  583 */         this.zoomCount += 0.015F;
/*      */       }
/*      */     } else {
/*      */       
/*  587 */       if (this.zoomCount > 1.0F) {
/*  588 */         this.zoomCount -= 0.015F;
/*      */       }
/*  590 */       if (Config.zoomMode) {
/*  591 */         Config.zoomMode = false;
/*  592 */         this.mc.gameSettings.smoothCamera = false;
/*  593 */         this.mouseFilterXAxis = new MouseFilter();
/*  594 */         this.mouseFilterYAxis = new MouseFilter();
/*  595 */         this.mc.renderGlobal.displayListEntitiesDirty = true;
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  600 */     f /= this.zoomCount;
/*      */ 
/*      */     
/*  603 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getHealth() <= 0.0F) {
/*  604 */       float f1 = ((EntityLivingBase)entity).deathTime + partialTicks;
/*  605 */       f /= (1.0F - 500.0F / (f1 + 500.0F)) * 2.0F + 1.0F;
/*      */     } 
/*      */     
/*  608 */     IBlockState iblockstate = ActiveRenderInfo.getBlockStateAtEntityViewpoint((World)this.mc.world, entity, 
/*  609 */         partialTicks);
/*      */     
/*  611 */     if (iblockstate.getMaterial() == Material.WATER) {
/*  612 */       f = f * 60.0F / 70.0F;
/*      */     }
/*      */     
/*  615 */     return Reflector.ForgeHooksClient_getFOVModifier.exists() ? 
/*  616 */       Reflector.callFloat(Reflector.ForgeHooksClient_getFOVModifier, new Object[] { this, entity, iblockstate, 
/*  617 */           Float.valueOf(partialTicks), Float.valueOf(f)
/*  618 */         }) : f; }
/*      */ 
/*      */ 
/*      */   
/*      */   private void hurtCameraEffect(float partialTicks) {
/*  623 */     if (this.mc.getRenderViewEntity() instanceof EntityLivingBase) {
/*  624 */       EntityLivingBase entitylivingbase = (EntityLivingBase)this.mc.getRenderViewEntity();
/*  625 */       float f = entitylivingbase.hurtTime - partialTicks;
/*      */       
/*  627 */       if (entitylivingbase.getHealth() <= 0.0F) {
/*  628 */         float f1 = entitylivingbase.deathTime + partialTicks;
/*  629 */         GlStateManager.rotate(40.0F - 8000.0F / (f1 + 200.0F), 0.0F, 0.0F, 1.0F);
/*      */       } 
/*      */       
/*  632 */       if (f < 0.0F) {
/*      */         return;
/*      */       }
/*      */       
/*  636 */       f /= entitylivingbase.maxHurtTime;
/*  637 */       f = MathHelper.sin(f * f * f * f * 3.1415927F);
/*  638 */       float f2 = entitylivingbase.attackedAtYaw;
/*  639 */       GlStateManager.rotate(-f2, 0.0F, 1.0F, 0.0F);
/*  640 */       GlStateManager.rotate(-f * 14.0F, 0.0F, 0.0F, 1.0F);
/*  641 */       GlStateManager.rotate(f2, 0.0F, 1.0F, 0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupViewBobbing(float partialTicks) {
/*  649 */     if (this.mc.getRenderViewEntity() instanceof EntityPlayer) {
/*  650 */       EntityPlayer entityplayer = (EntityPlayer)this.mc.getRenderViewEntity();
/*  651 */       float f = entityplayer.distanceWalkedModified - entityplayer.prevDistanceWalkedModified;
/*  652 */       float f1 = -(entityplayer.distanceWalkedModified + f * partialTicks);
/*  653 */       float f2 = entityplayer.prevCameraYaw + (
/*  654 */         entityplayer.cameraYaw - entityplayer.prevCameraYaw) * partialTicks;
/*  655 */       float f3 = entityplayer.prevCameraPitch + (
/*  656 */         entityplayer.cameraPitch - entityplayer.prevCameraPitch) * partialTicks;
/*  657 */       GlStateManager.translate(MathHelper.sin(f1 * 3.1415927F) * f2 * 0.5F, 
/*  658 */           -Math.abs(MathHelper.cos(f1 * 3.1415927F) * f2), 0.0F);
/*  659 */       GlStateManager.rotate(MathHelper.sin(f1 * 3.1415927F) * f2 * 3.0F, 0.0F, 0.0F, 1.0F);
/*  660 */       GlStateManager.rotate(Math.abs(MathHelper.cos(f1 * 3.1415927F - 0.2F) * f2) * 5.0F, 1.0F, 0.0F, 0.0F);
/*  661 */       GlStateManager.rotate(f3, 1.0F, 0.0F, 0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void orientCamera(float partialTicks) {
/*  669 */     Entity entity = this.mc.getRenderViewEntity();
/*  670 */     float f = entity.getEyeHeight();
/*  671 */     double d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/*  672 */     double d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
/*  673 */     double d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/*      */     
/*  675 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPlayerSleeping()) {
/*  676 */       f = (float)(f + 1.0D);
/*  677 */       GlStateManager.translate(0.0F, 0.3F, 0.0F);
/*      */       
/*  679 */       if (!this.mc.gameSettings.debugCamEnable) {
/*  680 */         BlockPos blockpos = new BlockPos(entity);
/*  681 */         IBlockState iblockstate = this.mc.world.getBlockState(blockpos);
/*  682 */         Block block = iblockstate.getBlock();
/*      */         
/*  684 */         if (Reflector.ForgeHooksClient_orientBedCamera.exists()) {
/*  685 */           Reflector.callVoid(Reflector.ForgeHooksClient_orientBedCamera, new Object[] { this.mc.world, blockpos, iblockstate, 
/*  686 */                 entity });
/*  687 */         } else if (block == Blocks.BED) {
/*  688 */           int j = ((EnumFacing)iblockstate.getValue((IProperty)BlockBed.FACING)).getHorizontalIndex();
/*  689 */           GlStateManager.rotate((j * 90), 0.0F, 1.0F, 0.0F);
/*      */         } 
/*      */         
/*  692 */         GlStateManager.rotate(
/*  693 */             entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 
/*  694 */             0.0F, -1.0F, 0.0F);
/*  695 */         GlStateManager.rotate(
/*  696 */             entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 
/*  697 */             -1.0F, 0.0F, 0.0F);
/*      */       } 
/*  699 */     } else if (this.mc.gameSettings.thirdPersonView > 0) {
/*  700 */       double d3 = (this.thirdPersonDistancePrev + (4.0F - this.thirdPersonDistancePrev) * partialTicks);
/*      */       
/*  702 */       if (this.mc.gameSettings.debugCamEnable) {
/*  703 */         GlStateManager.translate(0.0F, 0.0F, (float)-d3);
/*      */       } else {
/*  705 */         float f1 = entity.rotationYaw;
/*  706 */         float f2 = entity.rotationPitch;
/*      */         
/*  708 */         if (this.mc.gameSettings.thirdPersonView == 2) {
/*  709 */           f2 += 180.0F;
/*      */         }
/*      */         
/*  712 */         double d4 = (-MathHelper.sin(f1 * 0.017453292F) * MathHelper.cos(f2 * 0.017453292F)) * d3;
/*  713 */         double d5 = (MathHelper.cos(f1 * 0.017453292F) * MathHelper.cos(f2 * 0.017453292F)) * d3;
/*  714 */         double d6 = -MathHelper.sin(f2 * 0.017453292F) * d3;
/*      */         
/*  716 */         for (int i = 0; i < 8; i++) {
/*  717 */           float f3 = ((i & 0x1) * 2 - 1);
/*  718 */           float f4 = ((i >> 1 & 0x1) * 2 - 1);
/*  719 */           float f5 = ((i >> 2 & 0x1) * 2 - 1);
/*  720 */           f3 *= 0.1F;
/*  721 */           f4 *= 0.1F;
/*  722 */           f5 *= 0.1F;
/*  723 */           RayTraceResult raytraceresult = this.mc.world
/*  724 */             .rayTraceBlocks(new Vec3d(d0 + f3, d1 + f4, d2 + f5), new Vec3d(
/*  725 */                 d0 - d4 + f3 + f5, d1 - d6 + f4, d2 - d5 + f5));
/*      */           
/*  727 */           if (raytraceresult != null) {
/*  728 */             double d7 = raytraceresult.hitVec.distanceTo(new Vec3d(d0, d1, d2));
/*      */             
/*  730 */             if (d7 < d3) {
/*  731 */               d3 = d7;
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/*  736 */         if (this.mc.gameSettings.thirdPersonView == 2) {
/*  737 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*      */         }
/*      */         
/*  740 */         GlStateManager.rotate(entity.rotationPitch - f2, 1.0F, 0.0F, 0.0F);
/*  741 */         GlStateManager.rotate(entity.rotationYaw - f1, 0.0F, 1.0F, 0.0F);
/*  742 */         GlStateManager.translate(0.0F, 0.0F, (float)-d3);
/*  743 */         GlStateManager.rotate(f1 - entity.rotationYaw, 0.0F, 1.0F, 0.0F);
/*  744 */         GlStateManager.rotate(f2 - entity.rotationPitch, 1.0F, 0.0F, 0.0F);
/*      */       } 
/*      */     } else {
/*  747 */       GlStateManager.translate(0.0F, 0.0F, 0.05F);
/*      */     } 
/*      */     
/*  750 */     if (Reflector.EntityViewRenderEvent_CameraSetup_Constructor.exists()) {
/*  751 */       if (!this.mc.gameSettings.debugCamEnable) {
/*  752 */         float f6 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 
/*  753 */           180.0F;
/*  754 */         float f7 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks;
/*  755 */         float f8 = 0.0F;
/*      */         
/*  757 */         if (entity instanceof EntityAnimal) {
/*  758 */           EntityAnimal entityanimal1 = (EntityAnimal)entity;
/*  759 */           f6 = entityanimal1.prevRotationYawHead + (
/*  760 */             entityanimal1.rotationYawHead - entityanimal1.prevRotationYawHead) * partialTicks + 
/*  761 */             180.0F;
/*      */         } 
/*      */         
/*  764 */         IBlockState iblockstate1 = ActiveRenderInfo.getBlockStateAtEntityViewpoint((World)this.mc.world, entity, 
/*  765 */             partialTicks);
/*  766 */         Object object = Reflector.newInstance(Reflector.EntityViewRenderEvent_CameraSetup_Constructor, new Object[] { this, 
/*  767 */               entity, iblockstate1, Float.valueOf(partialTicks), Float.valueOf(f6), Float.valueOf(f7), Float.valueOf(f8) });
/*  768 */         Reflector.postForgeBusEvent(object);
/*  769 */         f8 = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_getRoll, new Object[0]);
/*  770 */         f7 = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_getPitch, new Object[0]);
/*  771 */         f6 = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_CameraSetup_getYaw, new Object[0]);
/*  772 */         GlStateManager.rotate(f8, 0.0F, 0.0F, 1.0F);
/*  773 */         GlStateManager.rotate(f7, 1.0F, 0.0F, 0.0F);
/*  774 */         GlStateManager.rotate(f6, 0.0F, 1.0F, 0.0F);
/*      */       } 
/*  776 */     } else if (!this.mc.gameSettings.debugCamEnable) {
/*  777 */       GlStateManager.rotate(
/*  778 */           entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks, 1.0F, 
/*  779 */           0.0F, 0.0F);
/*      */       
/*  781 */       if (entity instanceof EntityAnimal) {
/*  782 */         EntityAnimal entityanimal = (EntityAnimal)entity;
/*  783 */         GlStateManager.rotate(entityanimal.prevRotationYawHead + (
/*  784 */             entityanimal.rotationYawHead - entityanimal.prevRotationYawHead) * partialTicks + 180.0F, 
/*  785 */             0.0F, 1.0F, 0.0F);
/*      */       } else {
/*  787 */         GlStateManager.rotate(
/*  788 */             entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * partialTicks + 180.0F, 
/*  789 */             0.0F, 1.0F, 0.0F);
/*      */       } 
/*      */     } 
/*      */     
/*  793 */     GlStateManager.translate(0.0F, -f, 0.0F);
/*  794 */     d0 = entity.prevPosX + (entity.posX - entity.prevPosX) * partialTicks;
/*  795 */     d1 = entity.prevPosY + (entity.posY - entity.prevPosY) * partialTicks + f;
/*  796 */     d2 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * partialTicks;
/*  797 */     this.cloudFog = this.mc.renderGlobal.hasCloudFog(d0, d1, d2, partialTicks);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupCameraTransform(float partialTicks, int pass) {
/*  804 */     this.farPlaneDistance = (this.mc.gameSettings.renderDistanceChunks * 16);
/*      */     
/*  806 */     if (Config.isFogFancy()) {
/*  807 */       this.farPlaneDistance *= 0.95F;
/*      */     }
/*      */     
/*  810 */     if (Config.isFogFast()) {
/*  811 */       this.farPlaneDistance *= 0.83F;
/*      */     }
/*      */     
/*  814 */     GlStateManager.matrixMode(5889);
/*  815 */     GlStateManager.loadIdentity();
/*  816 */     float f = 0.07F;
/*      */     
/*  818 */     if (this.mc.gameSettings.anaglyph) {
/*  819 */       GlStateManager.translate(-(pass * 2 - 1) * 0.07F, 0.0F, 0.0F);
/*      */     }
/*      */     
/*  822 */     this.clipDistance = this.farPlaneDistance * 2.0F;
/*      */     
/*  824 */     if (this.clipDistance < 173.0F) {
/*  825 */       this.clipDistance = 173.0F;
/*      */     }
/*      */     
/*  828 */     if (this.cameraZoom != 1.0D) {
/*  829 */       GlStateManager.translate((float)this.cameraYaw, (float)-this.cameraPitch, 0.0F);
/*  830 */       GlStateManager.scale(this.cameraZoom, this.cameraZoom, 1.0D);
/*      */     } 
/*      */     
/*  833 */     Project.gluPerspective(getFOVModifier(partialTicks, true), 
/*  834 */         this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/*  835 */     GlStateManager.matrixMode(5888);
/*  836 */     GlStateManager.loadIdentity();
/*      */     
/*  838 */     if (this.mc.gameSettings.anaglyph) {
/*  839 */       GlStateManager.translate((pass * 2 - 1) * 0.1F, 0.0F, 0.0F);
/*      */     }
/*      */     
/*  842 */     hurtCameraEffect(partialTicks);
/*      */     
/*  844 */     if (this.mc.gameSettings.viewBobbing) {
/*  845 */       setupViewBobbing(partialTicks);
/*      */     }
/*      */     
/*  848 */     float f1 = this.mc.player.prevTimeInPortal + (
/*  849 */       this.mc.player.timeInPortal - this.mc.player.prevTimeInPortal) * partialTicks;
/*      */     
/*  851 */     if (f1 > 0.0F) {
/*  852 */       int i = 20;
/*      */       
/*  854 */       if (this.mc.player.isPotionActive(MobEffects.NAUSEA)) {
/*  855 */         i = 7;
/*      */       }
/*      */       
/*  858 */       float f2 = 5.0F / (f1 * f1 + 5.0F) - f1 * 0.04F;
/*  859 */       f2 *= f2;
/*  860 */       GlStateManager.rotate((this.rendererUpdateCount + partialTicks) * i, 0.0F, 1.0F, 1.0F);
/*  861 */       GlStateManager.scale(1.0F / f2, 1.0F, 1.0F);
/*  862 */       GlStateManager.rotate(-(this.rendererUpdateCount + partialTicks) * i, 0.0F, 1.0F, 1.0F);
/*      */     } 
/*      */     
/*  865 */     orientCamera(partialTicks);
/*      */     
/*  867 */     if (this.debugView) {
/*  868 */       switch (this.debugViewDirection) {
/*      */         case 0:
/*  870 */           GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 1:
/*  874 */           GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 2:
/*  878 */           GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 3:
/*  882 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*      */           break;
/*      */         
/*      */         case 4:
/*  886 */           GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/*      */           break;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderHand(float partialTicks, int pass) {
/*  895 */     renderHand(partialTicks, pass, true, true, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderHand(float p_renderHand_1_, int p_renderHand_2_, boolean p_renderHand_3_, boolean p_renderHand_4_, boolean p_renderHand_5_) {
/*  900 */     if (!this.debugView) {
/*  901 */       GlStateManager.matrixMode(5889);
/*  902 */       GlStateManager.loadIdentity();
/*  903 */       float f = 0.07F;
/*      */       
/*  905 */       if (this.mc.gameSettings.anaglyph) {
/*  906 */         GlStateManager.translate(-(p_renderHand_2_ * 2 - 1) * 0.07F, 0.0F, 0.0F);
/*      */       }
/*      */       
/*  909 */       if (Config.isShaders()) {
/*  910 */         Shaders.applyHandDepth();
/*      */       }
/*      */       
/*  913 */       Project.gluPerspective(getFOVModifier(p_renderHand_1_, false), 
/*  914 */           this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.farPlaneDistance * 2.0F);
/*  915 */       GlStateManager.matrixMode(5888);
/*  916 */       GlStateManager.loadIdentity();
/*      */       
/*  918 */       if (this.mc.gameSettings.anaglyph) {
/*  919 */         GlStateManager.translate((p_renderHand_2_ * 2 - 1) * 0.1F, 0.0F, 0.0F);
/*      */       }
/*      */       
/*  922 */       boolean flag = false;
/*      */       
/*  924 */       if (p_renderHand_3_) {
/*  925 */         GlStateManager.pushMatrix();
/*  926 */         hurtCameraEffect(p_renderHand_1_);
/*      */         
/*  928 */         if (this.mc.gameSettings.viewBobbing) {
/*  929 */           setupViewBobbing(p_renderHand_1_);
/*      */         }
/*      */         
/*  932 */         flag = (this.mc.getRenderViewEntity() instanceof EntityLivingBase && (
/*  933 */           (EntityLivingBase)this.mc.getRenderViewEntity()).isPlayerSleeping());
/*  934 */         boolean flag1 = !ReflectorForge.renderFirstPersonHand(this.mc.renderGlobal, p_renderHand_1_, 
/*  935 */             p_renderHand_2_);
/*      */         
/*  937 */         if (flag1 && this.mc.gameSettings.thirdPersonView == 0 && !flag && !this.mc.gameSettings.hideGUI && 
/*  938 */           !this.mc.playerController.isSpectator()) {
/*  939 */           enableLightmap();
/*      */           
/*  941 */           if (Config.isShaders()) {
/*  942 */             ShadersRender.renderItemFP(this.itemRenderer, p_renderHand_1_, p_renderHand_5_);
/*      */           } else {
/*  944 */             this.itemRenderer.renderItemInFirstPerson(p_renderHand_1_);
/*      */           } 
/*      */           
/*  947 */           disableLightmap();
/*      */         } 
/*      */         
/*  950 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/*  953 */       if (!p_renderHand_4_) {
/*      */         return;
/*      */       }
/*      */       
/*  957 */       disableLightmap();
/*      */       
/*  959 */       if (this.mc.gameSettings.thirdPersonView == 0 && !flag) {
/*  960 */         this.itemRenderer.renderOverlays(p_renderHand_1_);
/*  961 */         hurtCameraEffect(p_renderHand_1_);
/*      */       } 
/*      */       
/*  964 */       if (this.mc.gameSettings.viewBobbing) {
/*  965 */         setupViewBobbing(p_renderHand_1_);
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void disableLightmap() {
/*  971 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  972 */     GlStateManager.disableTexture2D();
/*  973 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */     
/*  975 */     if (Config.isShaders()) {
/*  976 */       Shaders.disableLightmap();
/*      */     }
/*      */   }
/*      */   
/*      */   public void enableLightmap() {
/*  981 */     GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
/*  982 */     GlStateManager.matrixMode(5890);
/*  983 */     GlStateManager.loadIdentity();
/*  984 */     float f = 0.00390625F;
/*  985 */     GlStateManager.scale(0.00390625F, 0.00390625F, 0.00390625F);
/*  986 */     GlStateManager.translate(8.0F, 8.0F, 8.0F);
/*  987 */     GlStateManager.matrixMode(5888);
/*  988 */     this.mc.getTextureManager().bindTexture(this.locationLightMap);
/*  989 */     GlStateManager.glTexParameteri(3553, 10241, 9729);
/*  990 */     GlStateManager.glTexParameteri(3553, 10240, 9729);
/*  991 */     GlStateManager.glTexParameteri(3553, 10242, 10496);
/*  992 */     GlStateManager.glTexParameteri(3553, 10243, 10496);
/*  993 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  994 */     GlStateManager.enableTexture2D();
/*  995 */     GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */     
/*  997 */     if (Config.isShaders()) {
/*  998 */       Shaders.enableLightmap();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateTorchFlicker() {
/* 1006 */     this.torchFlickerDX = 
/* 1007 */       (float)(this.torchFlickerDX + (Math.random() - Math.random()) * Math.random() * Math.random());
/* 1008 */     this.torchFlickerDX = (float)(this.torchFlickerDX * 0.9D);
/* 1009 */     this.torchFlickerX += this.torchFlickerDX - this.torchFlickerX;
/* 1010 */     this.lightmapUpdateNeeded = true;
/*      */   }
/*      */   
/*      */   private void updateLightmap(float partialTicks) {
/* 1014 */     if (this.lightmapUpdateNeeded) {
/* 1015 */       this.mc.mcProfiler.startSection("lightTex");
/* 1016 */       WorldClient worldClient = this.mc.world;
/*      */       
/* 1018 */       if (worldClient != null) {
/* 1019 */         if (Config.isCustomColors() && CustomColors.updateLightmap((World)worldClient, this.torchFlickerX, 
/* 1020 */             this.lightmapColors, this.mc.player.isPotionActive(MobEffects.NIGHT_VISION))) {
/* 1021 */           this.lightmapTexture.updateDynamicTexture();
/* 1022 */           this.lightmapUpdateNeeded = false;
/* 1023 */           this.mc.mcProfiler.endSection();
/*      */           
/*      */           return;
/*      */         } 
/* 1027 */         float f = worldClient.getSunBrightness(1.0F);
/* 1028 */         float f1 = f * 0.95F + 0.05F;
/*      */         
/* 1030 */         for (int i = 0; i < 256; i++) {
/* 1031 */           float f2 = ((World)worldClient).provider.getLightBrightnessTable()[i / 16] * f1;
/* 1032 */           float f3 = ((World)worldClient).provider.getLightBrightnessTable()[i % 16] * (this.torchFlickerX * 0.1F + 1.5F);
/*      */           
/* 1034 */           if (worldClient.getLastLightningBolt() > 0) {
/* 1035 */             f2 = ((World)worldClient).provider.getLightBrightnessTable()[i / 16];
/*      */           }
/*      */           
/* 1038 */           float f4 = f2 * (f * 0.65F + 0.35F);
/* 1039 */           float f5 = f2 * (f * 0.65F + 0.35F);
/* 1040 */           float f6 = f3 * ((f3 * 0.6F + 0.4F) * 0.6F + 0.4F);
/* 1041 */           float f7 = f3 * (f3 * f3 * 0.6F + 0.4F);
/* 1042 */           float f8 = f4 + f3;
/* 1043 */           float f9 = f5 + f6;
/* 1044 */           float f10 = f2 + f7;
/* 1045 */           f8 = f8 * 0.96F + 0.03F;
/* 1046 */           f9 = f9 * 0.96F + 0.03F;
/* 1047 */           f10 = f10 * 0.96F + 0.03F;
/*      */           
/* 1049 */           if (this.bossColorModifier > 0.0F) {
/* 1050 */             float f11 = this.bossColorModifierPrev + (
/* 1051 */               this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
/* 1052 */             f8 = f8 * (1.0F - f11) + f8 * 0.7F * f11;
/* 1053 */             f9 = f9 * (1.0F - f11) + f9 * 0.6F * f11;
/* 1054 */             f10 = f10 * (1.0F - f11) + f10 * 0.6F * f11;
/*      */           } 
/*      */           
/* 1057 */           if (((World)worldClient).provider.getDimensionType().getId() == 1) {
/* 1058 */             f8 = 0.22F + f3 * 0.75F;
/* 1059 */             f9 = 0.28F + f6 * 0.75F;
/* 1060 */             f10 = 0.25F + f7 * 0.75F;
/*      */           } 
/*      */           
/* 1063 */           if (Reflector.ForgeWorldProvider_getLightmapColors.exists()) {
/* 1064 */             float[] afloat = { f8, f9, f10 };
/* 1065 */             Reflector.call(((World)worldClient).provider, Reflector.ForgeWorldProvider_getLightmapColors, new Object[] { Float.valueOf(partialTicks), Float.valueOf(f), 
/* 1066 */                   Float.valueOf(f2), Float.valueOf(f3), afloat });
/* 1067 */             f8 = afloat[0];
/* 1068 */             f9 = afloat[1];
/* 1069 */             f10 = afloat[2];
/*      */           } 
/*      */           
/* 1072 */           f8 = MathHelper.clamp(f8, 0.0F, 1.0F);
/* 1073 */           f9 = MathHelper.clamp(f9, 0.0F, 1.0F);
/* 1074 */           f10 = MathHelper.clamp(f10, 0.0F, 1.0F);
/*      */           
/* 1076 */           if (this.mc.player.isPotionActive(MobEffects.NIGHT_VISION)) {
/* 1077 */             float f15 = getNightVisionBrightness((EntityLivingBase)this.mc.player, partialTicks);
/* 1078 */             float f12 = 1.0F / f8;
/*      */             
/* 1080 */             if (f12 > 1.0F / f9) {
/* 1081 */               f12 = 1.0F / f9;
/*      */             }
/*      */             
/* 1084 */             if (f12 > 1.0F / f10) {
/* 1085 */               f12 = 1.0F / f10;
/*      */             }
/*      */             
/* 1088 */             f8 = f8 * (1.0F - f15) + f8 * f12 * f15;
/* 1089 */             f9 = f9 * (1.0F - f15) + f9 * f12 * f15;
/* 1090 */             f10 = f10 * (1.0F - f15) + f10 * f12 * f15;
/*      */           } 
/*      */           
/* 1093 */           if (f8 > 1.0F) {
/* 1094 */             f8 = 1.0F;
/*      */           }
/*      */           
/* 1097 */           if (f9 > 1.0F) {
/* 1098 */             f9 = 1.0F;
/*      */           }
/*      */           
/* 1101 */           if (f10 > 1.0F) {
/* 1102 */             f10 = 1.0F;
/*      */           }
/*      */           
/* 1105 */           float f16 = this.mc.gameSettings.gammaSetting;
/* 1106 */           float f17 = 1.0F - f8;
/* 1107 */           float f13 = 1.0F - f9;
/* 1108 */           float f14 = 1.0F - f10;
/* 1109 */           f17 = 1.0F - f17 * f17 * f17 * f17;
/* 1110 */           f13 = 1.0F - f13 * f13 * f13 * f13;
/* 1111 */           f14 = 1.0F - f14 * f14 * f14 * f14;
/* 1112 */           f8 = f8 * (1.0F - f16) + f17 * f16;
/* 1113 */           f9 = f9 * (1.0F - f16) + f13 * f16;
/* 1114 */           f10 = f10 * (1.0F - f16) + f14 * f16;
/* 1115 */           f8 = f8 * 0.96F + 0.03F;
/* 1116 */           f9 = f9 * 0.96F + 0.03F;
/* 1117 */           f10 = f10 * 0.96F + 0.03F;
/*      */           
/* 1119 */           if (f8 > 1.0F) {
/* 1120 */             f8 = 1.0F;
/*      */           }
/*      */           
/* 1123 */           if (f9 > 1.0F) {
/* 1124 */             f9 = 1.0F;
/*      */           }
/*      */           
/* 1127 */           if (f10 > 1.0F) {
/* 1128 */             f10 = 1.0F;
/*      */           }
/*      */           
/* 1131 */           if (f8 < 0.0F) {
/* 1132 */             f8 = 0.0F;
/*      */           }
/*      */           
/* 1135 */           if (f9 < 0.0F) {
/* 1136 */             f9 = 0.0F;
/*      */           }
/*      */           
/* 1139 */           if (f10 < 0.0F) {
/* 1140 */             f10 = 0.0F;
/*      */           }
/*      */           
/* 1143 */           int j = 255;
/* 1144 */           int k = (int)(f8 * 255.0F);
/* 1145 */           int l = (int)(f9 * 255.0F);
/* 1146 */           int i1 = (int)(f10 * 255.0F);
/* 1147 */           this.lightmapColors[i] = 0xFF000000 | k << 16 | l << 8 | i1;
/*      */         } 
/*      */         
/* 1150 */         this.lightmapTexture.updateDynamicTexture();
/* 1151 */         this.lightmapUpdateNeeded = false;
/* 1152 */         this.mc.mcProfiler.endSection();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public float getNightVisionBrightness(EntityLivingBase entitylivingbaseIn, float partialTicks) {
/* 1158 */     int i = entitylivingbaseIn.getActivePotionEffect(MobEffects.NIGHT_VISION).getDuration();
/* 1159 */     return (i > 200) ? 1.0F : (0.7F + MathHelper.sin((i - partialTicks) * 3.1415927F * 0.2F) * 0.3F);
/*      */   }
/*      */   
/*      */   public void updateCameraAndRender(float partialTicks, long nanoTime) {
/* 1163 */     frameInit();
/* 1164 */     boolean flag = Display.isActive();
/*      */     
/* 1166 */     if (!flag && this.mc.gameSettings.pauseOnLostFocus && (
/* 1167 */       !this.mc.gameSettings.touchscreen || !Mouse.isButtonDown(1))) {
/* 1168 */       if (Minecraft.getSystemTime() - this.prevFrameTime > 500L) {
/* 1169 */         this.mc.displayInGameMenu();
/*      */       }
/*      */     } else {
/* 1172 */       this.prevFrameTime = Minecraft.getSystemTime();
/*      */     } 
/*      */     
/* 1175 */     this.mc.mcProfiler.startSection("mouse");
/*      */     
/* 1177 */     if (flag && Minecraft.IS_RUNNING_ON_MAC && this.mc.inGameHasFocus && !Mouse.isInsideWindow()) {
/* 1178 */       Mouse.setGrabbed(false);
/* 1179 */       Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2 - 20);
/* 1180 */       Mouse.setGrabbed(true);
/*      */     } 
/*      */     
/* 1183 */     if (this.mc.inGameHasFocus && flag) {
/* 1184 */       this.mc.mouseHelper.mouseXYChange();
/* 1185 */       this.mc.func_193032_ao().func_193299_a(this.mc.mouseHelper);
/* 1186 */       float f = this.mc.gameSettings.mouseSensitivity * 0.6F + 0.2F;
/* 1187 */       float f1 = f * f * f * 8.0F;
/* 1188 */       float f2 = this.mc.mouseHelper.deltaX * f1;
/* 1189 */       float f3 = this.mc.mouseHelper.deltaY * f1;
/* 1190 */       int i = 1;
/*      */       
/* 1192 */       if (this.mc.gameSettings.invertMouse) {
/* 1193 */         i = -1;
/*      */       }
/*      */       
/* 1196 */       if (this.mc.gameSettings.smoothCamera) {
/* 1197 */         this.smoothCamYaw += f2;
/* 1198 */         this.smoothCamPitch += f3;
/* 1199 */         float f4 = partialTicks - this.smoothCamPartialTicks;
/* 1200 */         this.smoothCamPartialTicks = partialTicks;
/* 1201 */         f2 = this.smoothCamFilterX * f4;
/* 1202 */         f3 = this.smoothCamFilterY * f4;
/* 1203 */         this.mc.player.setAngles(f2, f3 * i);
/*      */       } else {
/* 1205 */         this.smoothCamYaw = 0.0F;
/* 1206 */         this.smoothCamPitch = 0.0F;
/* 1207 */         this.mc.player.setAngles(f2, f3 * i);
/*      */       } 
/*      */     } 
/*      */     
/* 1211 */     this.mc.mcProfiler.endSection();
/*      */     
/* 1213 */     if (!this.mc.skipRenderWorld) {
/* 1214 */       anaglyphEnable = this.mc.gameSettings.anaglyph;
/* 1215 */       final ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 1216 */       int i1 = ScaledResolution.getScaledWidth();
/* 1217 */       int j1 = ScaledResolution.getScaledHeight();
/* 1218 */       final int k1 = Mouse.getX() * i1 / this.mc.displayWidth;
/* 1219 */       final int l1 = j1 - Mouse.getY() * j1 / this.mc.displayHeight - 1;
/* 1220 */       int i2 = this.mc.gameSettings.limitFramerate;
/*      */       
/* 1222 */       if (this.mc.world == null) {
/* 1223 */         GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/* 1224 */         GlStateManager.matrixMode(5889);
/* 1225 */         GlStateManager.loadIdentity();
/* 1226 */         GlStateManager.matrixMode(5888);
/* 1227 */         GlStateManager.loadIdentity();
/* 1228 */         setupOverlayRendering();
/* 1229 */         this.renderEndNanoTime = System.nanoTime();
/* 1230 */         TileEntityRendererDispatcher.instance.renderEngine = this.mc.getTextureManager();
/* 1231 */         TileEntityRendererDispatcher.instance.fontRenderer = this.mc.fontRendererObj;
/*      */       } else {
/* 1233 */         this.mc.mcProfiler.startSection("level");
/* 1234 */         int j = Math.min(Minecraft.getDebugFPS(), i2);
/* 1235 */         j = Math.max(j, 60);
/* 1236 */         long k = System.nanoTime() - nanoTime;
/* 1237 */         long l = Math.max((1000000000 / j / 4) - k, 0L);
/* 1238 */         renderWorld(partialTicks, System.nanoTime() + l);
/*      */         
/* 1240 */         if (this.mc.isSingleplayer() && this.timeWorldIcon < Minecraft.getSystemTime() - 1000L) {
/* 1241 */           this.timeWorldIcon = Minecraft.getSystemTime();
/*      */           
/* 1243 */           if (!this.mc.getIntegratedServer().isWorldIconSet()) {
/* 1244 */             createWorldIcon();
/*      */           }
/*      */         } 
/*      */         
/* 1248 */         if (OpenGlHelper.shadersSupported) {
/* 1249 */           this.mc.renderGlobal.renderEntityOutlineFramebuffer();
/*      */           
/* 1251 */           if (this.theShaderGroup != null && this.useShader) {
/* 1252 */             GlStateManager.matrixMode(5890);
/* 1253 */             GlStateManager.pushMatrix();
/* 1254 */             GlStateManager.loadIdentity();
/* 1255 */             this.theShaderGroup.loadShaderGroup(partialTicks);
/* 1256 */             GlStateManager.popMatrix();
/*      */           } 
/*      */           
/* 1259 */           this.mc.getFramebuffer().bindFramebuffer(true);
/*      */         } 
/*      */         
/* 1262 */         this.renderEndNanoTime = System.nanoTime();
/* 1263 */         this.mc.mcProfiler.endStartSection("gui");
/*      */         
/* 1265 */         if (!this.mc.gameSettings.hideGUI || this.mc.currentScreen != null) {
/* 1266 */           GlStateManager.alphaFunc(516, 0.1F);
/* 1267 */           setupOverlayRendering();
/* 1268 */           func_190563_a(i1, j1, partialTicks);
/* 1269 */           this.mc.ingameGUI.renderGameOverlay(partialTicks);
/*      */           
/* 1271 */           if (this.mc.gameSettings.ofShowFps && !this.mc.gameSettings.showDebugInfo) {
/* 1272 */             Config.drawFps();
/*      */           }
/*      */           
/* 1275 */           if (this.mc.gameSettings.showDebugInfo) {
/* 1276 */             Lagometer.showLagometer(scaledresolution);
/*      */           }
/*      */         } 
/*      */         
/* 1280 */         this.mc.mcProfiler.endSection();
/*      */       } 
/*      */       
/* 1283 */       if (this.mc.currentScreen != null) {
/* 1284 */         GlStateManager.clear(256);
/*      */         
/*      */         try {
/* 1287 */           if (Reflector.ForgeHooksClient_drawScreen.exists()) {
/* 1288 */             Reflector.callVoid(Reflector.ForgeHooksClient_drawScreen, new Object[] { this.mc.currentScreen, Integer.valueOf(k1), Integer.valueOf(l1), 
/* 1289 */                   Float.valueOf(this.mc.func_193989_ak()) });
/*      */           } else {
/* 1291 */             this.mc.currentScreen.drawScreen(k1, l1, this.mc.func_193989_ak());
/*      */           } 
/* 1293 */         } catch (Throwable throwable1) {
/* 1294 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable1, "Rendering screen");
/* 1295 */           CrashReportCategory crashreportcategory = crashreport.makeCategory("Screen render details");
/* 1296 */           crashreportcategory.setDetail("Screen name", new ICrashReportDetail<String>() {
/*      */                 public String call() throws Exception {
/* 1298 */                   return EntityRenderer.this.mc.currentScreen.getClass().getCanonicalName();
/*      */                 }
/*      */               });
/* 1301 */           crashreportcategory.setDetail("Mouse location", new ICrashReportDetail<String>() {
/*      */                 public String call() throws Exception {
/* 1303 */                   return String.format("Scaled: (%d, %d). Absolute: (%d, %d)", new Object[] { Integer.valueOf(this.val$k1), Integer.valueOf(this.val$l1), Integer.valueOf(Mouse.getX()), 
/* 1304 */                         Integer.valueOf(Mouse.getY()) });
/*      */                 }
/*      */               });
/* 1307 */           crashreportcategory.setDetail("Screen size", new ICrashReportDetail<String>() {
/*      */                 public String call() throws Exception {
/* 1309 */                   return String.format("Scaled: (%d, %d). Absolute: (%d, %d). Scale factor of %d", new Object[] {
/* 1310 */                         Integer.valueOf(ScaledResolution.getScaledWidth()), Integer.valueOf(ScaledResolution.getScaledHeight()), 
/* 1311 */                         Integer.valueOf((EntityRenderer.access$0(this.this$0)).displayWidth), Integer.valueOf((EntityRenderer.access$0(this.this$0)).displayHeight), 
/* 1312 */                         Integer.valueOf(this.val$scaledresolution.getScaleFactor()) });
/*      */                 }
/*      */               });
/* 1315 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1320 */     frameFinish();
/* 1321 */     waitForServerThread();
/* 1322 */     Lagometer.updateLagometer();
/*      */     
/* 1324 */     if (this.mc.gameSettings.ofProfiler) {
/* 1325 */       this.mc.gameSettings.showDebugProfilerChart = true;
/*      */     }
/*      */   }
/*      */   
/*      */   private void createWorldIcon() {
/* 1330 */     if (this.mc.renderGlobal.getRenderedChunks() > 10 && this.mc.renderGlobal.hasNoChunkUpdates() && 
/* 1331 */       !this.mc.getIntegratedServer().isWorldIconSet()) {
/* 1332 */       BufferedImage bufferedimage = ScreenShotHelper.createScreenshot(this.mc.displayWidth, this.mc.displayHeight, 
/* 1333 */           this.mc.getFramebuffer());
/* 1334 */       int i = bufferedimage.getWidth();
/* 1335 */       int j = bufferedimage.getHeight();
/* 1336 */       int k = 0;
/* 1337 */       int l = 0;
/*      */       
/* 1339 */       if (i > j) {
/* 1340 */         k = (i - j) / 2;
/* 1341 */         i = j;
/*      */       } else {
/* 1343 */         l = (j - i) / 2;
/*      */       } 
/*      */       
/*      */       try {
/* 1347 */         BufferedImage bufferedimage1 = new BufferedImage(64, 64, 1);
/* 1348 */         Graphics graphics = bufferedimage1.createGraphics();
/* 1349 */         graphics.drawImage(bufferedimage, 0, 0, 64, 64, k, l, k + i, l + i, null);
/* 1350 */         graphics.dispose();
/* 1351 */         ImageIO.write(bufferedimage1, "png", this.mc.getIntegratedServer().getWorldIconFile());
/* 1352 */       } catch (IOException ioexception1) {
/* 1353 */         LOGGER.warn("Couldn't save auto screenshot", ioexception1);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void renderStreamIndicator(float partialTicks) {
/* 1359 */     setupOverlayRendering();
/*      */   }
/*      */   
/*      */   private boolean isDrawBlockOutline() {
/* 1363 */     if (!this.drawBlockOutline) {
/* 1364 */       return false;
/*      */     }
/* 1366 */     Entity entity = this.mc.getRenderViewEntity();
/* 1367 */     boolean flag = (entity instanceof EntityPlayer && !this.mc.gameSettings.hideGUI);
/*      */     
/* 1369 */     if (flag && !((EntityPlayer)entity).capabilities.allowEdit) {
/* 1370 */       ItemStack itemstack = ((EntityPlayer)entity).getHeldItemMainhand();
/*      */       
/* 1372 */       if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK) {
/* 1373 */         BlockPos blockpos = this.mc.objectMouseOver.getBlockPos();
/* 1374 */         IBlockState iblockstate = this.mc.world.getBlockState(blockpos);
/* 1375 */         Block block = iblockstate.getBlock();
/*      */         
/* 1377 */         if (this.mc.playerController.getCurrentGameType() == GameType.SPECTATOR) {
/* 1378 */           flag = (ReflectorForge.blockHasTileEntity(iblockstate) && 
/* 1379 */             this.mc.world.getTileEntity(blockpos) instanceof net.minecraft.inventory.IInventory);
/*      */         } else {
/* 1381 */           flag = (!itemstack.func_190926_b() && (
/* 1382 */             itemstack.canDestroy(block) || itemstack.canPlaceOn(block)));
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1387 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderWorld(float partialTicks, long finishTimeNano) {
/* 1392 */     updateLightmap(partialTicks);
/*      */     
/* 1394 */     if (this.mc.getRenderViewEntity() == null) {
/* 1395 */       this.mc.setRenderViewEntity((Entity)this.mc.player);
/*      */     }
/*      */     
/* 1398 */     getMouseOver(partialTicks);
/*      */     
/* 1400 */     if (Config.isShaders()) {
/* 1401 */       Shaders.beginRender(this.mc, partialTicks, finishTimeNano);
/*      */     }
/*      */     
/* 1404 */     GlStateManager.enableDepth();
/* 1405 */     GlStateManager.enableAlpha();
/* 1406 */     GlStateManager.alphaFunc(516, 0.1F);
/* 1407 */     this.mc.mcProfiler.startSection("center");
/*      */     
/* 1409 */     if (this.mc.gameSettings.anaglyph) {
/* 1410 */       anaglyphField = 0;
/* 1411 */       GlStateManager.colorMask(false, true, true, false);
/* 1412 */       renderWorldPass(0, partialTicks, finishTimeNano);
/* 1413 */       anaglyphField = 1;
/* 1414 */       GlStateManager.colorMask(true, false, false, false);
/* 1415 */       renderWorldPass(1, partialTicks, finishTimeNano);
/* 1416 */       GlStateManager.colorMask(true, true, true, false);
/*      */     } else {
/* 1418 */       renderWorldPass(2, partialTicks, finishTimeNano);
/*      */     } 
/*      */     
/* 1421 */     this.mc.mcProfiler.endSection();
/*      */   }
/*      */   
/*      */   private void renderWorldPass(int pass, float partialTicks, long finishTimeNano) {
/* 1425 */     boolean flag = Config.isShaders();
/*      */     
/* 1427 */     if (flag) {
/* 1428 */       Shaders.beginRenderPass(pass, partialTicks, finishTimeNano);
/*      */     }
/*      */     
/* 1431 */     RenderGlobal renderglobal = this.mc.renderGlobal;
/* 1432 */     ParticleManager particlemanager = this.mc.effectRenderer;
/* 1433 */     boolean flag1 = isDrawBlockOutline();
/* 1434 */     GlStateManager.enableCull();
/* 1435 */     this.mc.mcProfiler.endStartSection("clear");
/*      */     
/* 1437 */     if (flag) {
/* 1438 */       Shaders.setViewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/*      */     } else {
/* 1440 */       GlStateManager.viewport(0, 0, this.mc.displayWidth, this.mc.displayHeight);
/*      */     } 
/*      */     
/* 1443 */     updateFogColor(partialTicks);
/* 1444 */     GlStateManager.clear(16640);
/*      */     
/* 1446 */     if (flag) {
/* 1447 */       Shaders.clearRenderBuffer();
/*      */     }
/*      */     
/* 1450 */     this.mc.mcProfiler.endStartSection("camera");
/* 1451 */     setupCameraTransform(partialTicks, pass);
/*      */     
/* 1453 */     if (flag) {
/* 1454 */       Shaders.setCamera(partialTicks);
/*      */     }
/*      */     
/* 1457 */     ActiveRenderInfo.updateRenderInfo((EntityPlayer)this.mc.player, (this.mc.gameSettings.thirdPersonView == 2));
/* 1458 */     this.mc.mcProfiler.endStartSection("frustum");
/* 1459 */     ClippingHelper clippinghelper = ClippingHelperImpl.getInstance();
/* 1460 */     this.mc.mcProfiler.endStartSection("culling");
/* 1461 */     Frustum frustum = new Frustum(clippinghelper);
/* 1462 */     Entity entity = this.mc.getRenderViewEntity();
/* 1463 */     double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 1464 */     double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 1465 */     double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/*      */     
/* 1467 */     if (flag) {
/* 1468 */       ShadersRender.setFrustrumPosition((ICamera)frustum, d0, d1, d2);
/*      */     } else {
/* 1470 */       frustum.setPosition(d0, d1, d2);
/*      */     } 
/*      */     
/* 1473 */     if ((Config.isSkyEnabled() || Config.isSunMoonEnabled() || Config.isStarsEnabled()) && !Shaders.isShadowPass) {
/* 1474 */       setupFog(-1, partialTicks);
/* 1475 */       this.mc.mcProfiler.endStartSection("sky");
/* 1476 */       GlStateManager.matrixMode(5889);
/* 1477 */       GlStateManager.loadIdentity();
/* 1478 */       Project.gluPerspective(getFOVModifier(partialTicks, true), 
/* 1479 */           this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1480 */       GlStateManager.matrixMode(5888);
/*      */       
/* 1482 */       if (flag) {
/* 1483 */         Shaders.beginSky();
/*      */       }
/*      */       
/* 1486 */       renderglobal.renderSky(partialTicks, pass);
/*      */       
/* 1488 */       if (flag) {
/* 1489 */         Shaders.endSky();
/*      */       }
/*      */       
/* 1492 */       GlStateManager.matrixMode(5889);
/* 1493 */       GlStateManager.loadIdentity();
/* 1494 */       Project.gluPerspective(getFOVModifier(partialTicks, true), 
/* 1495 */           this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1496 */       GlStateManager.matrixMode(5888);
/*      */     } else {
/* 1498 */       GlStateManager.disableBlend();
/*      */     } 
/*      */     
/* 1501 */     setupFog(0, partialTicks);
/* 1502 */     GlStateManager.shadeModel(7425);
/*      */     
/* 1504 */     if (entity.posY + entity.getEyeHeight() < 128.0D + (
/* 1505 */       this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
/* 1506 */       renderCloudsCheck(renderglobal, partialTicks, pass, d0, d1, d2);
/*      */     }
/*      */     
/* 1509 */     this.mc.mcProfiler.endStartSection("prepareterrain");
/* 1510 */     setupFog(0, partialTicks);
/* 1511 */     this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 1512 */     RenderHelper.disableStandardItemLighting();
/* 1513 */     this.mc.mcProfiler.endStartSection("terrain_setup");
/* 1514 */     checkLoadVisibleChunks(entity, partialTicks, (ICamera)frustum, this.mc.player.isSpectator());
/*      */     
/* 1516 */     if (flag) {
/* 1517 */       ShadersRender.setupTerrain(renderglobal, entity, partialTicks, (ICamera)frustum, this.frameCount++, 
/* 1518 */           this.mc.player.isSpectator());
/*      */     } else {
/* 1520 */       renderglobal.setupTerrain(entity, partialTicks, (ICamera)frustum, this.frameCount++, 
/* 1521 */           this.mc.player.isSpectator());
/*      */     } 
/*      */     
/* 1524 */     if (pass == 0 || pass == 2) {
/* 1525 */       this.mc.mcProfiler.endStartSection("updatechunks");
/* 1526 */       Lagometer.timerChunkUpload.start();
/* 1527 */       this.mc.renderGlobal.updateChunks(finishTimeNano);
/* 1528 */       Lagometer.timerChunkUpload.end();
/*      */     } 
/*      */     
/* 1531 */     this.mc.mcProfiler.endStartSection("terrain");
/* 1532 */     Lagometer.timerTerrain.start();
/*      */     
/* 1534 */     if (this.mc.gameSettings.ofSmoothFps && pass > 0) {
/* 1535 */       this.mc.mcProfiler.endStartSection("finish");
/* 1536 */       GL11.glFinish();
/* 1537 */       this.mc.mcProfiler.endStartSection("terrain");
/*      */     } 
/*      */     
/* 1540 */     GlStateManager.matrixMode(5888);
/* 1541 */     GlStateManager.pushMatrix();
/* 1542 */     GlStateManager.disableAlpha();
/*      */     
/* 1544 */     if (flag) {
/* 1545 */       ShadersRender.beginTerrainSolid();
/*      */     }
/*      */     
/* 1548 */     renderglobal.renderBlockLayer(BlockRenderLayer.SOLID, partialTicks, pass, entity);
/* 1549 */     GlStateManager.enableAlpha();
/*      */     
/* 1551 */     if (flag) {
/* 1552 */       ShadersRender.beginTerrainCutoutMipped();
/*      */     }
/*      */     
/* 1555 */     renderglobal.renderBlockLayer(BlockRenderLayer.CUTOUT_MIPPED, partialTicks, pass, entity);
/* 1556 */     this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
/*      */     
/* 1558 */     if (flag) {
/* 1559 */       ShadersRender.beginTerrainCutout();
/*      */     }
/*      */     
/* 1562 */     renderglobal.renderBlockLayer(BlockRenderLayer.CUTOUT, partialTicks, pass, entity);
/* 1563 */     this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
/*      */     
/* 1565 */     if (flag) {
/* 1566 */       ShadersRender.endTerrain();
/*      */     }
/*      */     
/* 1569 */     Lagometer.timerTerrain.end();
/* 1570 */     GlStateManager.shadeModel(7424);
/* 1571 */     GlStateManager.alphaFunc(516, 0.1F);
/*      */     
/* 1573 */     if (!this.debugView) {
/* 1574 */       GlStateManager.matrixMode(5888);
/* 1575 */       GlStateManager.popMatrix();
/* 1576 */       GlStateManager.pushMatrix();
/* 1577 */       RenderHelper.enableStandardItemLighting();
/* 1578 */       this.mc.mcProfiler.endStartSection("entities");
/*      */       
/* 1580 */       if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
/* 1581 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(0) });
/*      */       }
/*      */       
/* 1584 */       renderglobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/*      */       
/* 1586 */       if (Reflector.ForgeHooksClient_setRenderPass.exists()) {
/* 1587 */         Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/*      */       }
/*      */       
/* 1590 */       RenderHelper.disableStandardItemLighting();
/* 1591 */       disableLightmap();
/*      */     } 
/*      */     
/* 1594 */     GlStateManager.matrixMode(5888);
/* 1595 */     GlStateManager.popMatrix();
/*      */     
/* 1597 */     if (flag1 && this.mc.objectMouseOver != null && !entity.isInsideOfMaterial(Material.WATER)) {
/* 1598 */       EntityPlayer entityplayer = (EntityPlayer)entity;
/* 1599 */       GlStateManager.disableAlpha();
/* 1600 */       this.mc.mcProfiler.endStartSection("outline");
/*      */       
/* 1602 */       if (!Reflector.ForgeHooksClient_onDrawBlockHighlight.exists() || 
/* 1603 */         !Reflector.callBoolean(Reflector.ForgeHooksClient_onDrawBlockHighlight, new Object[] { renderglobal, 
/* 1604 */             entityplayer, this.mc.objectMouseOver, Integer.valueOf(0), Float.valueOf(partialTicks) })) {
/* 1605 */         renderglobal.drawSelectionBox(entityplayer, this.mc.objectMouseOver, 0, partialTicks);
/*      */       }
/*      */       
/* 1608 */       GlStateManager.enableAlpha();
/*      */     } 
/*      */     
/* 1611 */     if (this.mc.debugRenderer.shouldRender()) {
/* 1612 */       boolean flag2 = GlStateManager.isFogEnabled();
/* 1613 */       GlStateManager.disableFog();
/* 1614 */       this.mc.debugRenderer.renderDebug(partialTicks, finishTimeNano);
/* 1615 */       GlStateManager.setFogEnabled(flag2);
/*      */     } 
/*      */     
/* 1618 */     if (!renderglobal.damagedBlocks.isEmpty()) {
/* 1619 */       this.mc.mcProfiler.endStartSection("destroyProgress");
/* 1620 */       GlStateManager.enableBlend();
/* 1621 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, 
/* 1622 */           GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 1623 */       this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).setBlurMipmap(false, false);
/* 1624 */       renderglobal.drawBlockDamageTexture(Tessellator.getInstance(), Tessellator.getInstance().getBuffer(), 
/* 1625 */           entity, partialTicks);
/* 1626 */       this.mc.getTextureManager().getTexture(TextureMap.LOCATION_BLOCKS_TEXTURE).restoreLastBlurMipmap();
/* 1627 */       GlStateManager.disableBlend();
/*      */     } 
/*      */     
/* 1630 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 1631 */     GlStateManager.disableBlend();
/*      */     
/* 1633 */     if (!this.debugView) {
/* 1634 */       enableLightmap();
/* 1635 */       this.mc.mcProfiler.endStartSection("litParticles");
/*      */       
/* 1637 */       if (flag) {
/* 1638 */         Shaders.beginLitParticles();
/*      */       }
/*      */       
/* 1641 */       particlemanager.renderLitParticles(entity, partialTicks);
/* 1642 */       RenderHelper.disableStandardItemLighting();
/* 1643 */       setupFog(0, partialTicks);
/* 1644 */       this.mc.mcProfiler.endStartSection("particles");
/*      */       
/* 1646 */       if (flag) {
/* 1647 */         Shaders.beginParticles();
/*      */       }
/*      */       
/* 1650 */       particlemanager.renderParticles(entity, partialTicks);
/*      */       
/* 1652 */       if (flag) {
/* 1653 */         Shaders.endParticles();
/*      */       }
/*      */       
/* 1656 */       disableLightmap();
/*      */     } 
/*      */     
/* 1659 */     GlStateManager.depthMask(false);
/* 1660 */     GlStateManager.enableCull();
/* 1661 */     this.mc.mcProfiler.endStartSection("weather");
/*      */     
/* 1663 */     if (flag) {
/* 1664 */       Shaders.beginWeather();
/*      */     }
/*      */     
/* 1667 */     renderRainSnow(partialTicks);
/*      */     
/* 1669 */     if (flag) {
/* 1670 */       Shaders.endWeather();
/*      */     }
/*      */     
/* 1673 */     GlStateManager.depthMask(true);
/* 1674 */     renderglobal.renderWorldBorder(entity, partialTicks);
/*      */     
/* 1676 */     if (flag) {
/* 1677 */       ShadersRender.renderHand0(this, partialTicks, pass);
/* 1678 */       Shaders.preWater();
/*      */     } 
/*      */     
/* 1681 */     GlStateManager.disableBlend();
/* 1682 */     GlStateManager.enableCull();
/* 1683 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, 
/* 1684 */         GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, 
/* 1685 */         GlStateManager.DestFactor.ZERO);
/* 1686 */     GlStateManager.alphaFunc(516, 0.1F);
/* 1687 */     setupFog(0, partialTicks);
/* 1688 */     GlStateManager.enableBlend();
/* 1689 */     GlStateManager.depthMask(false);
/* 1690 */     this.mc.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 1691 */     GlStateManager.shadeModel(7425);
/* 1692 */     this.mc.mcProfiler.endStartSection("translucent");
/*      */     
/* 1694 */     if (flag) {
/* 1695 */       Shaders.beginWater();
/*      */     }
/*      */     
/* 1698 */     renderglobal.renderBlockLayer(BlockRenderLayer.TRANSLUCENT, partialTicks, pass, entity);
/*      */     
/* 1700 */     if (flag) {
/* 1701 */       Shaders.endWater();
/*      */     }
/*      */     
/* 1704 */     if (Reflector.ForgeHooksClient_setRenderPass.exists() && !this.debugView) {
/* 1705 */       RenderHelper.enableStandardItemLighting();
/* 1706 */       this.mc.mcProfiler.endStartSection("entities");
/* 1707 */       Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(1) });
/* 1708 */       this.mc.renderGlobal.renderEntities(entity, (ICamera)frustum, partialTicks);
/* 1709 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, 
/* 1710 */           GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, 
/* 1711 */           GlStateManager.DestFactor.ZERO);
/* 1712 */       Reflector.callVoid(Reflector.ForgeHooksClient_setRenderPass, new Object[] { Integer.valueOf(-1) });
/* 1713 */       RenderHelper.disableStandardItemLighting();
/*      */     } 
/*      */     
/* 1716 */     GlStateManager.shadeModel(7424);
/* 1717 */     GlStateManager.depthMask(true);
/* 1718 */     GlStateManager.enableCull();
/* 1719 */     GlStateManager.disableBlend();
/* 1720 */     GlStateManager.disableFog();
/*      */     
/* 1722 */     if (entity.posY + entity.getEyeHeight() >= 128.0D + (
/* 1723 */       this.mc.gameSettings.ofCloudsHeight * 128.0F)) {
/* 1724 */       this.mc.mcProfiler.endStartSection("aboveClouds");
/* 1725 */       renderCloudsCheck(renderglobal, partialTicks, pass, d0, d1, d2);
/*      */     } 
/*      */     
/* 1728 */     if (Reflector.ForgeHooksClient_dispatchRenderLast.exists()) {
/* 1729 */       this.mc.mcProfiler.endStartSection("forge_render_last");
/* 1730 */       Reflector.callVoid(Reflector.ForgeHooksClient_dispatchRenderLast, new Object[] { renderglobal, Float.valueOf(partialTicks) });
/*      */     } 
/*      */     
/* 1733 */     this.mc.mcProfiler.endStartSection("hand");
/*      */     
/* 1735 */     if (this.renderHand && !Shaders.isShadowPass) {
/* 1736 */       if (flag) {
/* 1737 */         ShadersRender.renderHand1(this, partialTicks, pass);
/* 1738 */         Shaders.renderCompositeFinal();
/*      */       } 
/*      */       
/* 1741 */       GlStateManager.clear(256);
/*      */       
/* 1743 */       if (flag) {
/* 1744 */         ShadersRender.renderFPOverlay(this, partialTicks, pass);
/*      */       } else {
/* 1746 */         renderHand(partialTicks, pass);
/*      */       } 
/*      */     } 
/*      */     
/* 1750 */     if (flag) {
/* 1751 */       Shaders.endRender();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderCloudsCheck(RenderGlobal renderGlobalIn, float partialTicks, int pass, double p_180437_4_, double p_180437_6_, double p_180437_8_) {
/* 1757 */     if (this.mc.gameSettings.renderDistanceChunks >= 4 && !Config.isCloudsOff() && 
/* 1758 */       Shaders.shouldRenderClouds(this.mc.gameSettings)) {
/* 1759 */       this.mc.mcProfiler.endStartSection("clouds");
/* 1760 */       GlStateManager.matrixMode(5889);
/* 1761 */       GlStateManager.loadIdentity();
/* 1762 */       Project.gluPerspective(getFOVModifier(partialTicks, true), 
/* 1763 */           this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance * 4.0F);
/* 1764 */       GlStateManager.matrixMode(5888);
/* 1765 */       GlStateManager.pushMatrix();
/* 1766 */       setupFog(0, partialTicks);
/* 1767 */       renderGlobalIn.renderClouds(partialTicks, pass, p_180437_4_, p_180437_6_, p_180437_8_);
/* 1768 */       GlStateManager.disableFog();
/* 1769 */       GlStateManager.popMatrix();
/* 1770 */       GlStateManager.matrixMode(5889);
/* 1771 */       GlStateManager.loadIdentity();
/* 1772 */       Project.gluPerspective(getFOVModifier(partialTicks, true), 
/* 1773 */           this.mc.displayWidth / this.mc.displayHeight, 0.05F, this.clipDistance);
/* 1774 */       GlStateManager.matrixMode(5888);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void addRainParticles() {
/* 1779 */     float f = this.mc.world.getRainStrength(1.0F);
/*      */     
/* 1781 */     if (!Config.isRainFancy()) {
/* 1782 */       f /= 2.0F;
/*      */     }
/*      */     
/* 1785 */     if (f != 0.0F && Config.isRainSplash()) {
/* 1786 */       this.random.setSeed(this.rendererUpdateCount * 312987231L);
/* 1787 */       Entity entity = this.mc.getRenderViewEntity();
/* 1788 */       WorldClient worldClient = this.mc.world;
/* 1789 */       BlockPos blockpos = new BlockPos(entity);
/* 1790 */       int i = 10;
/* 1791 */       double d0 = 0.0D;
/* 1792 */       double d1 = 0.0D;
/* 1793 */       double d2 = 0.0D;
/* 1794 */       int j = 0;
/* 1795 */       int k = (int)(100.0F * f * f);
/*      */       
/* 1797 */       if (this.mc.gameSettings.particleSetting == 1) {
/* 1798 */         k >>= 1;
/* 1799 */       } else if (this.mc.gameSettings.particleSetting == 2) {
/* 1800 */         k = 0;
/*      */       } 
/*      */       
/* 1803 */       for (int l = 0; l < k; l++) {
/* 1804 */         BlockPos blockpos1 = worldClient
/* 1805 */           .getPrecipitationHeight(blockpos.add(this.random.nextInt(10) - this.random.nextInt(10), 0, 
/* 1806 */               this.random.nextInt(10) - this.random.nextInt(10)));
/* 1807 */         Biome biome = worldClient.getBiome(blockpos1);
/* 1808 */         BlockPos blockpos2 = blockpos1.down();
/* 1809 */         IBlockState iblockstate = worldClient.getBlockState(blockpos2);
/*      */         
/* 1811 */         if (blockpos1.getY() <= blockpos.getY() + 10 && blockpos1.getY() >= blockpos.getY() - 10 && 
/* 1812 */           biome.canRain() && biome.getFloatTemperature(blockpos1) >= 0.15F) {
/* 1813 */           double d3 = this.random.nextDouble();
/* 1814 */           double d4 = this.random.nextDouble();
/* 1815 */           AxisAlignedBB axisalignedbb = iblockstate.getBoundingBox((IBlockAccess)worldClient, blockpos2);
/*      */           
/* 1817 */           if (iblockstate.getMaterial() != Material.LAVA && iblockstate.getBlock() != Blocks.MAGMA) {
/* 1818 */             if (iblockstate.getMaterial() != Material.AIR) {
/* 1819 */               j++;
/*      */               
/* 1821 */               if (this.random.nextInt(j) == 0) {
/* 1822 */                 d0 = blockpos2.getX() + d3;
/* 1823 */                 d1 = (blockpos2.getY() + 0.1F) + axisalignedbb.maxY - 1.0D;
/* 1824 */                 d2 = blockpos2.getZ() + d4;
/*      */               } 
/*      */               
/* 1827 */               this.mc.world.spawnParticle(EnumParticleTypes.WATER_DROP, blockpos2.getX() + d3, (
/* 1828 */                   blockpos2.getY() + 0.1F) + axisalignedbb.maxY, 
/* 1829 */                   blockpos2.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */             } 
/*      */           } else {
/* 1832 */             this.mc.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockpos1.getX() + d3, (
/* 1833 */                 blockpos1.getY() + 0.1F) - axisalignedbb.minY, 
/* 1834 */                 blockpos1.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1839 */       if (j > 0 && this.random.nextInt(3) < this.rainSoundCounter++) {
/* 1840 */         this.rainSoundCounter = 0;
/*      */         
/* 1842 */         if (d1 > (blockpos.getY() + 1) && 
/* 1843 */           worldClient.getPrecipitationHeight(blockpos).getY() > MathHelper.floor(blockpos.getY())) {
/* 1844 */           this.mc.world.playSound(d0, d1, d2, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1F, 
/* 1845 */               0.5F, false);
/*      */         } else {
/* 1847 */           this.mc.world.playSound(d0, d1, d2, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2F, 1.0F, 
/* 1848 */               false);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void renderRainSnow(float partialTicks) {
/* 1858 */     if (Reflector.ForgeWorldProvider_getWeatherRenderer.exists()) {
/* 1859 */       WorldProvider worldprovider = this.mc.world.provider;
/* 1860 */       Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getWeatherRenderer, new Object[0]);
/*      */       
/* 1862 */       if (object != null) {
/* 1863 */         Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.mc.world, this.mc });
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1868 */     float f5 = this.mc.world.getRainStrength(partialTicks);
/*      */     
/* 1870 */     if (f5 > 0.0F) {
/* 1871 */       if (Config.isRainOff()) {
/*      */         return;
/*      */       }
/*      */       
/* 1875 */       enableLightmap();
/* 1876 */       Entity entity = this.mc.getRenderViewEntity();
/* 1877 */       WorldClient worldClient = this.mc.world;
/* 1878 */       int i = MathHelper.floor(entity.posX);
/* 1879 */       int j = MathHelper.floor(entity.posY);
/* 1880 */       int k = MathHelper.floor(entity.posZ);
/* 1881 */       Tessellator tessellator = Tessellator.getInstance();
/* 1882 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 1883 */       GlStateManager.disableCull();
/* 1884 */       GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
/* 1885 */       GlStateManager.enableBlend();
/* 1886 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, 
/* 1887 */           GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, 
/* 1888 */           GlStateManager.DestFactor.ZERO);
/* 1889 */       GlStateManager.alphaFunc(516, 0.1F);
/* 1890 */       double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
/* 1891 */       double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
/* 1892 */       double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
/* 1893 */       int l = MathHelper.floor(d1);
/* 1894 */       int i1 = 5;
/*      */       
/* 1896 */       if (Config.isRainFancy()) {
/* 1897 */         i1 = 10;
/*      */       }
/*      */       
/* 1900 */       int j1 = -1;
/* 1901 */       float f = this.rendererUpdateCount + partialTicks;
/* 1902 */       bufferbuilder.setTranslation(-d0, -d1, -d2);
/* 1903 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1904 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*      */       
/* 1906 */       for (int k1 = k - i1; k1 <= k + i1; k1++) {
/* 1907 */         for (int l1 = i - i1; l1 <= i + i1; l1++) {
/* 1908 */           int i2 = (k1 - k + 16) * 32 + l1 - i + 16;
/* 1909 */           double d3 = this.rainXCoords[i2] * 0.5D;
/* 1910 */           double d4 = this.rainYCoords[i2] * 0.5D;
/* 1911 */           blockpos$mutableblockpos.setPos(l1, 0, k1);
/* 1912 */           Biome biome = worldClient.getBiome((BlockPos)blockpos$mutableblockpos);
/*      */           
/* 1914 */           if (biome.canRain() || biome.getEnableSnow()) {
/* 1915 */             int j2 = worldClient.getPrecipitationHeight((BlockPos)blockpos$mutableblockpos).getY();
/* 1916 */             int k2 = j - i1;
/* 1917 */             int l2 = j + i1;
/*      */             
/* 1919 */             if (k2 < j2) {
/* 1920 */               k2 = j2;
/*      */             }
/*      */             
/* 1923 */             if (l2 < j2) {
/* 1924 */               l2 = j2;
/*      */             }
/*      */             
/* 1927 */             int i3 = j2;
/*      */             
/* 1929 */             if (j2 < l) {
/* 1930 */               i3 = l;
/*      */             }
/*      */             
/* 1933 */             if (k2 != l2) {
/* 1934 */               this.random
/* 1935 */                 .setSeed((l1 * l1 * 3121 + l1 * 45238971 ^ k1 * k1 * 418711 + k1 * 13761));
/* 1936 */               blockpos$mutableblockpos.setPos(l1, k2, k1);
/* 1937 */               float f1 = biome.getFloatTemperature((BlockPos)blockpos$mutableblockpos);
/*      */               
/* 1939 */               if (worldClient.getBiomeProvider().getTemperatureAtHeight(f1, j2) >= 0.15F) {
/* 1940 */                 if (j1 != 0) {
/* 1941 */                   if (j1 >= 0) {
/* 1942 */                     tessellator.draw();
/*      */                   }
/*      */                   
/* 1945 */                   j1 = 0;
/* 1946 */                   this.mc.getTextureManager().bindTexture(RAIN_TEXTURES);
/* 1947 */                   bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*      */                 } 
/*      */                 
/* 1950 */                 double d5 = -((this.rendererUpdateCount + l1 * l1 * 3121 + l1 * 45238971 + 
/* 1951 */                   k1 * k1 * 418711 + k1 * 13761 & 0x1F) + partialTicks) / 32.0D * (
/* 1952 */                   3.0D + this.random.nextDouble());
/* 1953 */                 double d6 = (l1 + 0.5F) - entity.posX;
/* 1954 */                 double d7 = (k1 + 0.5F) - entity.posZ;
/* 1955 */                 float f2 = MathHelper.sqrt(d6 * d6 + d7 * d7) / i1;
/* 1956 */                 float f3 = ((1.0F - f2 * f2) * 0.5F + 0.5F) * f5;
/* 1957 */                 blockpos$mutableblockpos.setPos(l1, i3, k1);
/* 1958 */                 int j3 = worldClient.getCombinedLight((BlockPos)blockpos$mutableblockpos, 0);
/* 1959 */                 int k3 = j3 >> 16 & 0xFFFF;
/* 1960 */                 int l3 = j3 & 0xFFFF;
/* 1961 */                 bufferbuilder.pos(l1 - d3 + 0.5D, l2, k1 - d4 + 0.5D)
/* 1962 */                   .tex(0.0D, k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3)
/* 1963 */                   .lightmap(k3, l3).endVertex();
/* 1964 */                 bufferbuilder.pos(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D)
/* 1965 */                   .tex(1.0D, k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3)
/* 1966 */                   .lightmap(k3, l3).endVertex();
/* 1967 */                 bufferbuilder.pos(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D)
/* 1968 */                   .tex(1.0D, l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3)
/* 1969 */                   .lightmap(k3, l3).endVertex();
/* 1970 */                 bufferbuilder.pos(l1 - d3 + 0.5D, k2, k1 - d4 + 0.5D)
/* 1971 */                   .tex(0.0D, l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f3)
/* 1972 */                   .lightmap(k3, l3).endVertex();
/*      */               } else {
/* 1974 */                 if (j1 != 1) {
/* 1975 */                   if (j1 >= 0) {
/* 1976 */                     tessellator.draw();
/*      */                   }
/*      */                   
/* 1979 */                   j1 = 1;
/* 1980 */                   this.mc.getTextureManager().bindTexture(SNOW_TEXTURES);
/* 1981 */                   bufferbuilder.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
/*      */                 } 
/*      */                 
/* 1984 */                 double d8 = (-((this.rendererUpdateCount & 0x1FF) + partialTicks) / 
/* 1985 */                   512.0F);
/* 1986 */                 double d9 = this.random.nextDouble() + 
/* 1987 */                   f * 0.01D * (float)this.random.nextGaussian();
/* 1988 */                 double d10 = this.random.nextDouble() + (
/* 1989 */                   f * (float)this.random.nextGaussian()) * 0.001D;
/* 1990 */                 double d11 = (l1 + 0.5F) - entity.posX;
/* 1991 */                 double d12 = (k1 + 0.5F) - entity.posZ;
/* 1992 */                 float f6 = MathHelper.sqrt(d11 * d11 + d12 * d12) / i1;
/* 1993 */                 float f4 = ((1.0F - f6 * f6) * 0.3F + 0.5F) * f5;
/* 1994 */                 blockpos$mutableblockpos.setPos(l1, i3, k1);
/* 1995 */                 int i4 = (worldClient.getCombinedLight((BlockPos)blockpos$mutableblockpos, 0) * 3 + 15728880) / 4;
/* 1996 */                 int j4 = i4 >> 16 & 0xFFFF;
/* 1997 */                 int k4 = i4 & 0xFFFF;
/* 1998 */                 bufferbuilder.pos(l1 - d3 + 0.5D, l2, k1 - d4 + 0.5D)
/* 1999 */                   .tex(0.0D + d9, k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4)
/* 2000 */                   .lightmap(j4, k4).endVertex();
/* 2001 */                 bufferbuilder.pos(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D)
/* 2002 */                   .tex(1.0D + d9, k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4)
/* 2003 */                   .lightmap(j4, k4).endVertex();
/* 2004 */                 bufferbuilder.pos(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D)
/* 2005 */                   .tex(1.0D + d9, l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4)
/* 2006 */                   .lightmap(j4, k4).endVertex();
/* 2007 */                 bufferbuilder.pos(l1 - d3 + 0.5D, k2, k1 - d4 + 0.5D)
/* 2008 */                   .tex(0.0D + d9, l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f4)
/* 2009 */                   .lightmap(j4, k4).endVertex();
/*      */               } 
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2016 */       if (j1 >= 0) {
/* 2017 */         tessellator.draw();
/*      */       }
/*      */       
/* 2020 */       bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
/* 2021 */       GlStateManager.enableCull();
/* 2022 */       GlStateManager.disableBlend();
/* 2023 */       GlStateManager.alphaFunc(516, 0.1F);
/* 2024 */       disableLightmap();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setupOverlayRendering() {
/* 2032 */     ScaledResolution scaledresolution = new ScaledResolution(this.mc);
/* 2033 */     GlStateManager.clear(256);
/* 2034 */     GlStateManager.matrixMode(5889);
/* 2035 */     GlStateManager.loadIdentity();
/* 2036 */     GlStateManager.ortho(0.0D, scaledresolution.getScaledWidth_double(), scaledresolution.getScaledHeight_double(), 
/* 2037 */         0.0D, 1000.0D, 3000.0D);
/* 2038 */     GlStateManager.matrixMode(5888);
/* 2039 */     GlStateManager.loadIdentity();
/* 2040 */     GlStateManager.translate(0.0F, 0.0F, -2000.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateFogColor(float partialTicks) {
/* 2047 */     WorldClient worldClient = this.mc.world;
/* 2048 */     Entity entity = this.mc.getRenderViewEntity();
/* 2049 */     float f = 0.25F + 0.75F * this.mc.gameSettings.renderDistanceChunks / 32.0F;
/* 2050 */     f = 1.0F - (float)Math.pow(f, 0.25D);
/* 2051 */     Vec3d vec3d = worldClient.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
/* 2052 */     vec3d = CustomColors.getWorldSkyColor(vec3d, (World)worldClient, this.mc.getRenderViewEntity(), partialTicks);
/* 2053 */     float f1 = (float)vec3d.xCoord;
/* 2054 */     float f2 = (float)vec3d.yCoord;
/* 2055 */     float f3 = (float)vec3d.zCoord;
/* 2056 */     Vec3d vec3d1 = worldClient.getFogColor(partialTicks);
/* 2057 */     vec3d1 = CustomColors.getWorldFogColor(vec3d1, (World)worldClient, this.mc.getRenderViewEntity(), partialTicks);
/* 2058 */     this.fogColorRed = (float)vec3d1.xCoord;
/* 2059 */     this.fogColorGreen = (float)vec3d1.yCoord;
/* 2060 */     this.fogColorBlue = (float)vec3d1.zCoord;
/*      */     
/* 2062 */     if (this.mc.gameSettings.renderDistanceChunks >= 4) {
/* 2063 */       double d0 = (MathHelper.sin(worldClient.getCelestialAngleRadians(partialTicks)) > 0.0F) ? -1.0D : 1.0D;
/* 2064 */       Vec3d vec3d2 = new Vec3d(d0, 0.0D, 0.0D);
/* 2065 */       float f5 = (float)entity.getLook(partialTicks).dotProduct(vec3d2);
/*      */       
/* 2067 */       if (f5 < 0.0F) {
/* 2068 */         f5 = 0.0F;
/*      */       }
/*      */       
/* 2071 */       if (f5 > 0.0F) {
/* 2072 */         float[] afloat = ((World)worldClient).provider.calcSunriseSunsetColors(worldClient.getCelestialAngle(partialTicks), 
/* 2073 */             partialTicks);
/*      */         
/* 2075 */         if (afloat != null) {
/* 2076 */           f5 *= afloat[3];
/* 2077 */           this.fogColorRed = this.fogColorRed * (1.0F - f5) + afloat[0] * f5;
/* 2078 */           this.fogColorGreen = this.fogColorGreen * (1.0F - f5) + afloat[1] * f5;
/* 2079 */           this.fogColorBlue = this.fogColorBlue * (1.0F - f5) + afloat[2] * f5;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2084 */     this.fogColorRed += (f1 - this.fogColorRed) * f;
/* 2085 */     this.fogColorGreen += (f2 - this.fogColorGreen) * f;
/* 2086 */     this.fogColorBlue += (f3 - this.fogColorBlue) * f;
/* 2087 */     float f8 = worldClient.getRainStrength(partialTicks);
/*      */     
/* 2089 */     if (f8 > 0.0F) {
/* 2090 */       float f4 = 1.0F - f8 * 0.5F;
/* 2091 */       float f10 = 1.0F - f8 * 0.4F;
/* 2092 */       this.fogColorRed *= f4;
/* 2093 */       this.fogColorGreen *= f4;
/* 2094 */       this.fogColorBlue *= f10;
/*      */     } 
/*      */     
/* 2097 */     float f9 = worldClient.getThunderStrength(partialTicks);
/*      */     
/* 2099 */     if (f9 > 0.0F) {
/* 2100 */       float f11 = 1.0F - f9 * 0.5F;
/* 2101 */       this.fogColorRed *= f11;
/* 2102 */       this.fogColorGreen *= f11;
/* 2103 */       this.fogColorBlue *= f11;
/*      */     } 
/*      */     
/* 2106 */     IBlockState iblockstate1 = ActiveRenderInfo.getBlockStateAtEntityViewpoint((World)this.mc.world, entity, partialTicks);
/*      */     
/* 2108 */     if (this.cloudFog) {
/* 2109 */       Vec3d vec3d4 = worldClient.getCloudColour(partialTicks);
/* 2110 */       this.fogColorRed = (float)vec3d4.xCoord;
/* 2111 */       this.fogColorGreen = (float)vec3d4.yCoord;
/* 2112 */       this.fogColorBlue = (float)vec3d4.zCoord;
/* 2113 */     } else if (Reflector.ForgeBlock_getFogColor.exists()) {
/* 2114 */       Vec3d vec3d5 = ActiveRenderInfo.projectViewFromEntity(entity, partialTicks);
/* 2115 */       BlockPos blockpos = new BlockPos(vec3d5);
/* 2116 */       IBlockState iblockstate = this.mc.world.getBlockState(blockpos);
/* 2117 */       Vec3d vec3d3 = (Vec3d)Reflector.call(iblockstate.getBlock(), Reflector.ForgeBlock_getFogColor, new Object[] {
/* 2118 */             this.mc.world, blockpos, iblockstate, entity, 
/* 2119 */             new Vec3d(this.fogColorRed, this.fogColorGreen, this.fogColorBlue), 
/* 2120 */             Float.valueOf(partialTicks) });
/* 2121 */       this.fogColorRed = (float)vec3d3.xCoord;
/* 2122 */       this.fogColorGreen = (float)vec3d3.yCoord;
/* 2123 */       this.fogColorBlue = (float)vec3d3.zCoord;
/* 2124 */     } else if (iblockstate1.getMaterial() == Material.WATER) {
/* 2125 */       float f12 = 0.0F;
/*      */       
/* 2127 */       if (entity instanceof EntityLivingBase) {
/* 2128 */         f12 = EnchantmentHelper.getRespirationModifier((EntityLivingBase)entity) * 0.2F;
/*      */         
/* 2130 */         if (((EntityLivingBase)entity).isPotionActive(MobEffects.WATER_BREATHING)) {
/* 2131 */           f12 = f12 * 0.3F + 0.6F;
/*      */         }
/*      */       } 
/*      */       
/* 2135 */       this.fogColorRed = 0.02F + f12;
/* 2136 */       this.fogColorGreen = 0.02F + f12;
/* 2137 */       this.fogColorBlue = 0.2F + f12;
/* 2138 */       Vec3d vec3d7 = CustomColors.getUnderwaterColor((IBlockAccess)this.mc.world, (this.mc.getRenderViewEntity()).posX, 
/* 2139 */           (this.mc.getRenderViewEntity()).posY + 1.0D, (this.mc.getRenderViewEntity()).posZ);
/*      */       
/* 2141 */       if (vec3d7 != null) {
/* 2142 */         this.fogColorRed = (float)vec3d7.xCoord;
/* 2143 */         this.fogColorGreen = (float)vec3d7.yCoord;
/* 2144 */         this.fogColorBlue = (float)vec3d7.zCoord;
/*      */       } 
/* 2146 */     } else if (iblockstate1.getMaterial() == Material.LAVA) {
/* 2147 */       this.fogColorRed = 0.6F;
/* 2148 */       this.fogColorGreen = 0.1F;
/* 2149 */       this.fogColorBlue = 0.0F;
/* 2150 */       Vec3d vec3d6 = CustomColors.getUnderlavaColor((IBlockAccess)this.mc.world, (this.mc.getRenderViewEntity()).posX, 
/* 2151 */           (this.mc.getRenderViewEntity()).posY + 1.0D, (this.mc.getRenderViewEntity()).posZ);
/*      */       
/* 2153 */       if (vec3d6 != null) {
/* 2154 */         this.fogColorRed = (float)vec3d6.xCoord;
/* 2155 */         this.fogColorGreen = (float)vec3d6.yCoord;
/* 2156 */         this.fogColorBlue = (float)vec3d6.zCoord;
/*      */       } 
/*      */     } 
/*      */     
/* 2160 */     float f13 = this.fogColor2 + (this.fogColor1 - this.fogColor2) * partialTicks;
/* 2161 */     this.fogColorRed *= f13;
/* 2162 */     this.fogColorGreen *= f13;
/* 2163 */     this.fogColorBlue *= f13;
/* 2164 */     double d1 = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks) * 
/* 2165 */       ((World)worldClient).provider.getVoidFogYFactor();
/*      */     
/* 2167 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(MobEffects.BLINDNESS)) {
/* 2168 */       int i = ((EntityLivingBase)entity).getActivePotionEffect(MobEffects.BLINDNESS).getDuration();
/*      */       
/* 2170 */       if (i < 20) {
/* 2171 */         d1 *= (1.0F - i / 20.0F);
/*      */       } else {
/* 2173 */         d1 = 0.0D;
/*      */       } 
/*      */     } 
/*      */     
/* 2177 */     if (d1 < 1.0D) {
/* 2178 */       if (d1 < 0.0D) {
/* 2179 */         d1 = 0.0D;
/*      */       }
/*      */       
/* 2182 */       d1 *= d1;
/* 2183 */       this.fogColorRed = (float)(this.fogColorRed * d1);
/* 2184 */       this.fogColorGreen = (float)(this.fogColorGreen * d1);
/* 2185 */       this.fogColorBlue = (float)(this.fogColorBlue * d1);
/*      */     } 
/*      */     
/* 2188 */     if (this.bossColorModifier > 0.0F) {
/* 2189 */       float f14 = this.bossColorModifierPrev + (
/* 2190 */         this.bossColorModifier - this.bossColorModifierPrev) * partialTicks;
/* 2191 */       this.fogColorRed = this.fogColorRed * (1.0F - f14) + this.fogColorRed * 0.7F * f14;
/* 2192 */       this.fogColorGreen = this.fogColorGreen * (1.0F - f14) + this.fogColorGreen * 0.6F * f14;
/* 2193 */       this.fogColorBlue = this.fogColorBlue * (1.0F - f14) + this.fogColorBlue * 0.6F * f14;
/*      */     } 
/*      */     
/* 2196 */     if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).isPotionActive(MobEffects.NIGHT_VISION)) {
/* 2197 */       float f15 = getNightVisionBrightness((EntityLivingBase)entity, partialTicks);
/* 2198 */       float f6 = 1.0F / this.fogColorRed;
/*      */       
/* 2200 */       if (f6 > 1.0F / this.fogColorGreen) {
/* 2201 */         f6 = 1.0F / this.fogColorGreen;
/*      */       }
/*      */       
/* 2204 */       if (f6 > 1.0F / this.fogColorBlue) {
/* 2205 */         f6 = 1.0F / this.fogColorBlue;
/*      */       }
/*      */       
/* 2208 */       this.fogColorRed = this.fogColorRed * (1.0F - f15) + this.fogColorRed * f6 * f15;
/* 2209 */       this.fogColorGreen = this.fogColorGreen * (1.0F - f15) + this.fogColorGreen * f6 * f15;
/* 2210 */       this.fogColorBlue = this.fogColorBlue * (1.0F - f15) + this.fogColorBlue * f6 * f15;
/*      */     } 
/*      */     
/* 2213 */     if (this.mc.gameSettings.anaglyph) {
/* 2214 */       float f16 = (this.fogColorRed * 30.0F + this.fogColorGreen * 59.0F + this.fogColorBlue * 11.0F) / 100.0F;
/* 2215 */       float f17 = (this.fogColorRed * 30.0F + this.fogColorGreen * 70.0F) / 100.0F;
/* 2216 */       float f7 = (this.fogColorRed * 30.0F + this.fogColorBlue * 70.0F) / 100.0F;
/* 2217 */       this.fogColorRed = f16;
/* 2218 */       this.fogColorGreen = f17;
/* 2219 */       this.fogColorBlue = f7;
/*      */     } 
/*      */     
/* 2222 */     if (Reflector.EntityViewRenderEvent_FogColors_Constructor.exists()) {
/* 2223 */       Object object = Reflector.newInstance(Reflector.EntityViewRenderEvent_FogColors_Constructor, new Object[] { this, entity, 
/* 2224 */             iblockstate1, Float.valueOf(partialTicks), Float.valueOf(this.fogColorRed), Float.valueOf(this.fogColorGreen), Float.valueOf(this.fogColorBlue) });
/* 2225 */       Reflector.postForgeBusEvent(object);
/* 2226 */       this.fogColorRed = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_FogColors_getRed, new Object[0]);
/* 2227 */       this.fogColorGreen = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_FogColors_getGreen, new Object[0]);
/* 2228 */       this.fogColorBlue = Reflector.callFloat(object, Reflector.EntityViewRenderEvent_FogColors_getBlue, new Object[0]);
/*      */     } 
/*      */     
/* 2231 */     Shaders.setClearColor(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void setupFog(int startCoords, float partialTicks) {
/* 2239 */     this.fogStandard = false;
/* 2240 */     Entity entity = this.mc.getRenderViewEntity();
/* 2241 */     func_191514_d(false);
/* 2242 */     GlStateManager.glNormal3f(0.0F, -1.0F, 0.0F);
/* 2243 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 2244 */     IBlockState iblockstate = ActiveRenderInfo.getBlockStateAtEntityViewpoint((World)this.mc.world, entity, partialTicks);
/* 2245 */     float f = -1.0F;
/*      */     
/* 2247 */     if (Reflector.ForgeHooksClient_getFogDensity.exists()) {
/* 2248 */       f = Reflector.callFloat(Reflector.ForgeHooksClient_getFogDensity, new Object[] { this, entity, iblockstate, Float.valueOf(partialTicks), 
/* 2249 */             Float.valueOf(0.1F) });
/*      */     }
/*      */     
/* 2252 */     if (f >= 0.0F) {
/* 2253 */       GlStateManager.setFogDensity(f);
/* 2254 */     } else if (entity instanceof EntityLivingBase && (
/* 2255 */       (EntityLivingBase)entity).isPotionActive(MobEffects.BLINDNESS)) {
/* 2256 */       float f2 = 5.0F;
/* 2257 */       int i = ((EntityLivingBase)entity).getActivePotionEffect(MobEffects.BLINDNESS).getDuration();
/*      */       
/* 2259 */       if (i < 20) {
/* 2260 */         f2 = 5.0F + (this.farPlaneDistance - 5.0F) * (1.0F - i / 20.0F);
/*      */       }
/*      */       
/* 2263 */       if (Config.isShaders()) {
/* 2264 */         Shaders.setFog(GlStateManager.FogMode.LINEAR);
/*      */       } else {
/* 2266 */         GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
/*      */       } 
/*      */       
/* 2269 */       if (startCoords == -1) {
/* 2270 */         GlStateManager.setFogStart(0.0F);
/* 2271 */         GlStateManager.setFogEnd(f2 * 0.8F);
/*      */       } else {
/* 2273 */         GlStateManager.setFogStart(f2 * 0.25F);
/* 2274 */         GlStateManager.setFogEnd(f2);
/*      */       } 
/*      */       
/* 2277 */       if ((GLContext.getCapabilities()).GL_NV_fog_distance && Config.isFogFancy()) {
/* 2278 */         GlStateManager.glFogi(34138, 34139);
/*      */       }
/* 2280 */     } else if (this.cloudFog) {
/* 2281 */       if (Config.isShaders()) {
/* 2282 */         Shaders.setFog(GlStateManager.FogMode.EXP);
/*      */       } else {
/* 2284 */         GlStateManager.setFog(GlStateManager.FogMode.EXP);
/*      */       } 
/*      */       
/* 2287 */       GlStateManager.setFogDensity(0.1F);
/* 2288 */     } else if (iblockstate.getMaterial() == Material.WATER) {
/* 2289 */       if (Config.isShaders()) {
/* 2290 */         Shaders.setFog(GlStateManager.FogMode.EXP);
/*      */       } else {
/* 2292 */         GlStateManager.setFog(GlStateManager.FogMode.EXP);
/*      */       } 
/*      */       
/* 2295 */       if (entity instanceof EntityLivingBase) {
/* 2296 */         if (((EntityLivingBase)entity).isPotionActive(MobEffects.WATER_BREATHING)) {
/* 2297 */           GlStateManager.setFogDensity(0.01F);
/*      */         } else {
/* 2299 */           GlStateManager.setFogDensity(
/* 2300 */               0.1F - EnchantmentHelper.getRespirationModifier((EntityLivingBase)entity) * 0.03F);
/*      */         } 
/*      */       } else {
/* 2303 */         GlStateManager.setFogDensity(0.1F);
/*      */       } 
/*      */       
/* 2306 */       if (Config.isClearWater()) {
/* 2307 */         GlStateManager.setFogDensity(0.02F);
/*      */       }
/* 2309 */     } else if (iblockstate.getMaterial() == Material.LAVA) {
/* 2310 */       if (Config.isShaders()) {
/* 2311 */         Shaders.setFog(GlStateManager.FogMode.EXP);
/*      */       } else {
/* 2313 */         GlStateManager.setFog(GlStateManager.FogMode.EXP);
/*      */       } 
/*      */       
/* 2316 */       GlStateManager.setFogDensity(2.0F);
/*      */     } else {
/* 2318 */       float f1 = this.farPlaneDistance;
/* 2319 */       this.fogStandard = true;
/*      */       
/* 2321 */       if (Config.isShaders()) {
/* 2322 */         Shaders.setFog(GlStateManager.FogMode.LINEAR);
/*      */       } else {
/* 2324 */         GlStateManager.setFog(GlStateManager.FogMode.LINEAR);
/*      */       } 
/*      */       
/* 2327 */       if (startCoords == -1) {
/* 2328 */         GlStateManager.setFogStart(0.0F);
/* 2329 */         GlStateManager.setFogEnd(f1);
/*      */       } else {
/* 2331 */         GlStateManager.setFogStart(f1 * Config.getFogStart());
/* 2332 */         GlStateManager.setFogEnd(f1);
/*      */       } 
/*      */       
/* 2335 */       if ((GLContext.getCapabilities()).GL_NV_fog_distance) {
/* 2336 */         if (Config.isFogFancy()) {
/* 2337 */           GlStateManager.glFogi(34138, 34139);
/*      */         }
/*      */         
/* 2340 */         if (Config.isFogFast()) {
/* 2341 */           GlStateManager.glFogi(34138, 34140);
/*      */         }
/*      */       } 
/*      */       
/* 2345 */       if (this.mc.world.provider.doesXZShowFog((int)entity.posX, (int)entity.posZ) || 
/* 2346 */         this.mc.ingameGUI.getBossOverlay().shouldCreateFog()) {
/* 2347 */         GlStateManager.setFogStart(f1 * 0.05F);
/* 2348 */         GlStateManager.setFogEnd(f1);
/*      */       } 
/*      */       
/* 2351 */       if (Reflector.ForgeHooksClient_onFogRender.exists()) {
/* 2352 */         Reflector.callVoid(Reflector.ForgeHooksClient_onFogRender, new Object[] { this, entity, iblockstate, Float.valueOf(partialTicks), 
/* 2353 */               Integer.valueOf(startCoords), Float.valueOf(f1) });
/*      */       }
/*      */     } 
/*      */     
/* 2357 */     GlStateManager.enableColorMaterial();
/* 2358 */     GlStateManager.enableFog();
/* 2359 */     GlStateManager.colorMaterial(1028, 4608);
/*      */   }
/*      */   
/*      */   public void func_191514_d(boolean p_191514_1_) {
/* 2363 */     if (p_191514_1_) {
/* 2364 */       GlStateManager.glFog(2918, setFogColorBuffer(0.0F, 0.0F, 0.0F, 1.0F));
/*      */     } else {
/* 2366 */       GlStateManager.glFog(2918, 
/* 2367 */           setFogColorBuffer(this.fogColorRed, this.fogColorGreen, this.fogColorBlue, 1.0F));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private FloatBuffer setFogColorBuffer(float red, float green, float blue, float alpha) {
/* 2375 */     if (Config.isShaders()) {
/* 2376 */       Shaders.setFogColor(red, green, blue);
/*      */     }
/*      */     
/* 2379 */     this.fogColorBuffer.clear();
/* 2380 */     this.fogColorBuffer.put(red).put(green).put(blue).put(alpha);
/* 2381 */     this.fogColorBuffer.flip();
/* 2382 */     return this.fogColorBuffer;
/*      */   }
/*      */   
/*      */   public void func_190564_k() {
/* 2386 */     this.field_190566_ab = null;
/* 2387 */     this.theMapItemRenderer.clearLoadedMaps();
/*      */   }
/*      */   
/*      */   public MapItemRenderer getMapItemRenderer() {
/* 2391 */     return this.theMapItemRenderer;
/*      */   }
/*      */   
/*      */   private void waitForServerThread() {
/* 2395 */     this.serverWaitTimeCurrent = 0;
/*      */     
/* 2397 */     if (Config.isSmoothWorld() && Config.isSingleProcessor()) {
/* 2398 */       if (this.mc.isIntegratedServerRunning()) {
/* 2399 */         IntegratedServer integratedserver = this.mc.getIntegratedServer();
/*      */         
/* 2401 */         if (integratedserver != null) {
/* 2402 */           boolean flag = this.mc.isGamePaused();
/*      */           
/* 2404 */           if (!flag && !(this.mc.currentScreen instanceof net.minecraft.client.gui.GuiDownloadTerrain)) {
/* 2405 */             if (this.serverWaitTime > 0) {
/* 2406 */               Lagometer.timerServer.start();
/* 2407 */               Config.sleep(this.serverWaitTime);
/* 2408 */               Lagometer.timerServer.end();
/* 2409 */               this.serverWaitTimeCurrent = this.serverWaitTime;
/*      */             } 
/*      */             
/* 2412 */             long i = System.nanoTime() / 1000000L;
/*      */             
/* 2414 */             if (this.lastServerTime != 0L && this.lastServerTicks != 0) {
/* 2415 */               long j = i - this.lastServerTime;
/*      */               
/* 2417 */               if (j < 0L) {
/* 2418 */                 this.lastServerTime = i;
/* 2419 */                 j = 0L;
/*      */               } 
/*      */               
/* 2422 */               if (j >= 50L) {
/* 2423 */                 this.lastServerTime = i;
/* 2424 */                 int k = integratedserver.getTickCounter();
/* 2425 */                 int l = k - this.lastServerTicks;
/*      */                 
/* 2427 */                 if (l < 0) {
/* 2428 */                   this.lastServerTicks = k;
/* 2429 */                   l = 0;
/*      */                 } 
/*      */                 
/* 2432 */                 if (l < 1 && this.serverWaitTime < 100) {
/* 2433 */                   this.serverWaitTime += 2;
/*      */                 }
/*      */                 
/* 2436 */                 if (l > 1 && this.serverWaitTime > 0) {
/* 2437 */                   this.serverWaitTime--;
/*      */                 }
/*      */                 
/* 2440 */                 this.lastServerTicks = k;
/*      */               } 
/*      */             } else {
/* 2443 */               this.lastServerTime = i;
/* 2444 */               this.lastServerTicks = integratedserver.getTickCounter();
/* 2445 */               this.avgServerTickDiff = 1.0F;
/* 2446 */               this.avgServerTimeDiff = 50.0F;
/*      */             } 
/*      */           } else {
/* 2449 */             if (this.mc.currentScreen instanceof net.minecraft.client.gui.GuiDownloadTerrain) {
/* 2450 */               Config.sleep(20L);
/*      */             }
/*      */             
/* 2453 */             this.lastServerTime = 0L;
/* 2454 */             this.lastServerTicks = 0;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } else {
/* 2459 */       this.lastServerTime = 0L;
/* 2460 */       this.lastServerTicks = 0;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void frameInit() {
/* 2465 */     if (!this.initialized) {
/* 2466 */       TextureUtils.registerResourceListener();
/*      */       
/* 2468 */       if (Config.getBitsOs() == 64 && Config.getBitsJre() == 32) {
/* 2469 */         Config.setNotify64BitJava(true);
/*      */       }
/*      */       
/* 2472 */       this.initialized = true;
/*      */     } 
/*      */     
/* 2475 */     Config.checkDisplayMode();
/* 2476 */     WorldClient worldClient = this.mc.world;
/*      */     
/* 2478 */     if (worldClient != null) {
/* 2479 */       if (Config.getNewRelease() != null) {
/* 2480 */         String s = "HD_U".replace("HD_U", "HD Ultra").replace("L", "Light");
/* 2481 */         String s1 = String.valueOf(s) + " " + Config.getNewRelease();
/* 2482 */         TextComponentString textcomponentstring = new TextComponentString(
/* 2483 */             I18n.format("of.message.newVersion", new Object[] { s1 }));
/* 2484 */         this.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)textcomponentstring);
/* 2485 */         Config.setNewRelease(null);
/*      */       } 
/*      */       
/* 2488 */       if (Config.isNotify64BitJava()) {
/* 2489 */         Config.setNotify64BitJava(false);
/* 2490 */         TextComponentString textcomponentstring1 = new TextComponentString(I18n.format("of.message.java64Bit", new Object[0]));
/* 2491 */         this.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)textcomponentstring1);
/*      */       } 
/*      */     } 
/*      */     
/* 2495 */     if (this.mc.currentScreen instanceof GuiMainMenu) {
/* 2496 */       updateMainMenu((GuiMainMenu)this.mc.currentScreen);
/*      */     }
/*      */     
/* 2499 */     if (this.updatedWorld != worldClient) {
/* 2500 */       RandomMobs.worldChanged(this.updatedWorld, (World)worldClient);
/* 2501 */       Config.updateThreadPriorities();
/* 2502 */       this.lastServerTime = 0L;
/* 2503 */       this.lastServerTicks = 0;
/* 2504 */       this.updatedWorld = (World)worldClient;
/*      */     } 
/*      */     
/* 2507 */     if (!setFxaaShader(Shaders.configAntialiasingLevel)) {
/* 2508 */       Shaders.configAntialiasingLevel = 0;
/*      */     }
/*      */   }
/*      */   
/*      */   private void frameFinish() {
/* 2513 */     if (this.mc.world != null) {
/* 2514 */       long i = System.currentTimeMillis();
/*      */       
/* 2516 */       if (i > this.lastErrorCheckTimeMs + 10000L) {
/* 2517 */         this.lastErrorCheckTimeMs = i;
/* 2518 */         int j = GlStateManager.glGetError();
/*      */         
/* 2520 */         if (j != 0) {
/* 2521 */           String s = GLU.gluErrorString(j);
/* 2522 */           TextComponentString textcomponentstring = new TextComponentString(
/* 2523 */               I18n.format("of.message.openglError", new Object[] { Integer.valueOf(j), s }));
/* 2524 */           this.mc.ingameGUI.getChatGUI().printChatMessage((ITextComponent)textcomponentstring);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateMainMenu(GuiMainMenu p_updateMainMenu_1_) {
/*      */     try {
/* 2532 */       String s = null;
/* 2533 */       Calendar calendar = Calendar.getInstance();
/* 2534 */       calendar.setTime(new Date());
/* 2535 */       int i = calendar.get(5);
/* 2536 */       int j = calendar.get(2) + 1;
/*      */       
/* 2538 */       if (i == 8 && j == 4) {
/* 2539 */         s = "Happy birthday, OptiFine!";
/*      */       }
/*      */       
/* 2542 */       if (i == 14 && j == 8) {
/* 2543 */         s = "Happy birthday, sp614x!";
/*      */       }
/*      */       
/* 2546 */       if (s == null) {
/*      */         return;
/*      */       }
/*      */       
/* 2550 */       Reflector.setFieldValue(p_updateMainMenu_1_, Reflector.GuiMainMenu_splashText, s);
/* 2551 */     } catch (Throwable throwable) {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setFxaaShader(int p_setFxaaShader_1_) {
/* 2557 */     if (!OpenGlHelper.isFramebufferEnabled())
/* 2558 */       return false; 
/* 2559 */     if (this.theShaderGroup != null && this.theShaderGroup != this.fxaaShaders[2] && 
/* 2560 */       this.theShaderGroup != this.fxaaShaders[4])
/* 2561 */       return true; 
/* 2562 */     if (p_setFxaaShader_1_ != 2 && p_setFxaaShader_1_ != 4) {
/* 2563 */       if (this.theShaderGroup == null) {
/* 2564 */         return true;
/*      */       }
/* 2566 */       this.theShaderGroup.deleteShaderGroup();
/* 2567 */       this.theShaderGroup = null;
/* 2568 */       return true;
/*      */     } 
/* 2570 */     if (this.theShaderGroup != null && this.theShaderGroup == this.fxaaShaders[p_setFxaaShader_1_])
/* 2571 */       return true; 
/* 2572 */     if (this.mc.world == null) {
/* 2573 */       return true;
/*      */     }
/* 2575 */     loadShader(new ResourceLocation("shaders/post/fxaa_of_" + p_setFxaaShader_1_ + "x.json"));
/* 2576 */     this.fxaaShaders[p_setFxaaShader_1_] = this.theShaderGroup;
/* 2577 */     return this.useShader;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkLoadVisibleChunks(Entity p_checkLoadVisibleChunks_1_, float p_checkLoadVisibleChunks_2_, ICamera p_checkLoadVisibleChunks_3_, boolean p_checkLoadVisibleChunks_4_) {
/* 2583 */     int i = 201435902;
/*      */     
/* 2585 */     if (this.loadVisibleChunks) {
/* 2586 */       this.loadVisibleChunks = false;
/* 2587 */       loadAllVisibleChunks(p_checkLoadVisibleChunks_1_, p_checkLoadVisibleChunks_2_, 
/* 2588 */           p_checkLoadVisibleChunks_3_, p_checkLoadVisibleChunks_4_);
/* 2589 */       this.mc.ingameGUI.getChatGUI().deleteChatLine(i);
/*      */     } 
/*      */     
/* 2592 */     if (Keyboard.isKeyDown(61) && Keyboard.isKeyDown(38)) {
/* 2593 */       if (this.mc.gameSettings.field_194146_ao.getKeyCode() == 38) {
/* 2594 */         if (this.mc.currentScreen instanceof net.minecraft.client.gui.advancements.GuiScreenAdvancements)
/* 2595 */           this.mc.displayGuiScreen(null); 
/*      */         do {
/*      */         
/* 2598 */         } while (Keyboard.next());
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 2603 */       if (this.mc.currentScreen != null) {
/*      */         return;
/*      */       }
/*      */       
/* 2607 */       this.loadVisibleChunks = true;
/* 2608 */       TextComponentString textcomponentstring = new TextComponentString(
/* 2609 */           I18n.format("of.message.loadingVisibleChunks", new Object[0]));
/* 2610 */       this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((ITextComponent)textcomponentstring, i);
/* 2611 */       Reflector.Minecraft_actionKeyF3.setValue(this.mc, Boolean.TRUE);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadAllVisibleChunks(Entity p_loadAllVisibleChunks_1_, double p_loadAllVisibleChunks_2_, ICamera p_loadAllVisibleChunks_4_, boolean p_loadAllVisibleChunks_5_) {
/* 2617 */     RenderGlobal renderglobal = Config.getRenderGlobal();
/* 2618 */     int i = renderglobal.getCountLoadedChunks();
/* 2619 */     long j = System.currentTimeMillis();
/* 2620 */     Config.dbg("Loading visible chunks");
/* 2621 */     long k = System.currentTimeMillis() + 5000L;
/* 2622 */     int l = 0;
/* 2623 */     boolean flag = false;
/*      */     
/*      */     do {
/* 2626 */       flag = false;
/*      */       
/* 2628 */       for (int i1 = 0; i1 < 100; i1++) {
/* 2629 */         renderglobal.displayListEntitiesDirty = true;
/* 2630 */         renderglobal.setupTerrain(p_loadAllVisibleChunks_1_, p_loadAllVisibleChunks_2_, 
/* 2631 */             p_loadAllVisibleChunks_4_, this.frameCount++, p_loadAllVisibleChunks_5_);
/*      */         
/* 2633 */         if (!renderglobal.hasNoChunkUpdates()) {
/* 2634 */           flag = true;
/*      */         }
/*      */         
/* 2637 */         l += renderglobal.getCountChunksToUpdate();
/* 2638 */         renderglobal.updateChunks(System.nanoTime() + 1000000000L);
/* 2639 */         l -= renderglobal.getCountChunksToUpdate();
/*      */       } 
/*      */       
/* 2642 */       if (renderglobal.getCountLoadedChunks() != i) {
/* 2643 */         flag = true;
/* 2644 */         i = renderglobal.getCountLoadedChunks();
/*      */       } 
/*      */       
/* 2647 */       if (System.currentTimeMillis() <= k)
/* 2648 */         continue;  Config.log("Chunks loaded: " + l);
/* 2649 */       k = System.currentTimeMillis() + 5000L;
/*      */     
/*      */     }
/* 2652 */     while (flag);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2657 */     Config.log("Chunks loaded: " + l);
/* 2658 */     Config.log("Finished loading visible chunks");
/* 2659 */     RenderChunk.renderChunksUpdated = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static <T> void drawNameplate(FontRenderer fontRendererIn, T entityIn, String str, float x, float y, float z, int verticalShift, float viewerYaw, float viewerPitch, boolean isThirdPersonFrontal, boolean isSneaking) {
/* 2665 */     GlStateManager.pushMatrix();
/* 2666 */     GlStateManager.translate(x, y, z);
/* 2667 */     GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
/* 2668 */     GlStateManager.rotate(-viewerYaw, 0.0F, 1.0F, 0.0F);
/* 2669 */     GlStateManager.rotate((isThirdPersonFrontal ? -1 : true) * viewerPitch, 1.0F, 0.0F, 0.0F);
/* 2670 */     GlStateManager.scale(-0.025F, -0.025F, 0.025F);
/* 2671 */     GlStateManager.disableLighting();
/* 2672 */     GlStateManager.depthMask(false);
/*      */     
/* 2674 */     GlStateManager.enableBlend();
/* 2675 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, 
/* 2676 */         GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, 
/* 2677 */         GlStateManager.DestFactor.ZERO);
/* 2678 */     int i = fontRendererIn.getStringWidth(str) / 2;
/*      */     
/* 2680 */     if (entityIn instanceof EntityPlayer) {
/* 2681 */       GL11.glPushMatrix();
/* 2682 */       if ((Minecraft.getMinecraft()).player.getDistanceSqToEntity((Entity)entityIn) >= 116.0D) {
/* 2683 */         double distanceOffset = (Minecraft.getMinecraft()).player.getDistanceSqToEntity((Entity)entityIn) * 0.01D;
/* 2684 */         GL11.glScaled(distanceOffset, distanceOffset, distanceOffset);
/*      */       } 
/* 2686 */       Gui.drawRect(-fontRendererIn.getStringWidth(str) / 2 - 1, -1, 
/* 2687 */           -fontRendererIn.getStringWidth(str) / 2 + fontRendererIn.getStringWidth(str), 8, 
/* 2688 */           -2147483648);
/*      */       
/* 2690 */       double health = 0.0D;
/*      */       
/* 2692 */       if (entityIn instanceof EntityLivingBase) {
/* 2693 */         EntityLivingBase e = (EntityLivingBase)entityIn;
/* 2694 */         health = e.getHealth();
/*      */       } 
/*      */       
/* 2697 */       fontRendererIn.drawString(String.valueOf(str) + " §a" + (Math.round(health) / 2L) + " §c<3", -fontRendererIn.getStringWidth(str) / 2, 0, -1);
/*      */       
/* 2699 */       GL11.glEvalCoord1f(Float.NaN);
/* 2700 */       GL11.glPopMatrix();
/* 2701 */       GlStateManager.enableTexture2D();
/* 2702 */       GlStateManager.enableDepth();
/* 2703 */       GlStateManager.depthMask(true);
/*      */     } else {
/* 2705 */       GlStateManager.disableTexture2D();
/* 2706 */       Tessellator tessellator = Tessellator.getInstance();
/* 2707 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 2708 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 2709 */       bufferbuilder.pos((-i - 1), (-1 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F)
/* 2710 */         .endVertex();
/* 2711 */       bufferbuilder.pos((-i - 1), (8 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F)
/* 2712 */         .endVertex();
/* 2713 */       bufferbuilder.pos((i + 1), (8 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F)
/* 2714 */         .endVertex();
/* 2715 */       bufferbuilder.pos((i + 1), (-1 + verticalShift), 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F)
/* 2716 */         .endVertex();
/* 2717 */       tessellator.draw();
/* 2718 */       GlStateManager.enableTexture2D();
/*      */       
/* 2720 */       if (!isSneaking) {
/* 2721 */         fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, 553648127);
/* 2722 */         GlStateManager.enableDepth();
/*      */       } 
/*      */       
/* 2725 */       GlStateManager.depthMask(true);
/* 2726 */       fontRendererIn.drawString(str, -fontRendererIn.getStringWidth(str) / 2, verticalShift, 
/* 2727 */           isSneaking ? 553648127 : -1);
/*      */     } 
/*      */ 
/*      */     
/* 2731 */     GlStateManager.enableLighting();
/* 2732 */     GlStateManager.disableBlend();
/* 2733 */     GlStateManager.color(1.0F, 1.0F, 1.0F);
/* 2734 */     GlStateManager.popMatrix();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_190565_a(ItemStack p_190565_1_) {
/* 2740 */     this.field_190566_ab = p_190565_1_;
/* 2741 */     this.field_190567_ac = 40;
/* 2742 */     this.field_190568_ad = this.random.nextFloat() * 2.0F - 1.0F;
/* 2743 */     this.field_190569_ae = this.random.nextFloat() * 2.0F - 1.0F;
/*      */   }
/*      */   
/*      */   private void func_190563_a(int p_190563_1_, int p_190563_2_, float p_190563_3_) {
/* 2747 */     if (this.field_190566_ab != null && this.field_190567_ac > 0) {
/* 2748 */       int i = 40 - this.field_190567_ac;
/* 2749 */       float f = (i + p_190563_3_) / 40.0F;
/* 2750 */       float f1 = f * f;
/* 2751 */       float f2 = f * f1;
/* 2752 */       float f3 = 10.25F * f2 * f1 + -24.95F * f1 * f1 + 25.5F * f2 + -13.8F * f1 + 4.0F * f;
/* 2753 */       float f4 = f3 * 3.1415927F;
/* 2754 */       float f5 = this.field_190568_ad * (p_190563_1_ / 4);
/* 2755 */       float f6 = this.field_190569_ae * (p_190563_2_ / 4);
/* 2756 */       GlStateManager.enableAlpha();
/* 2757 */       GlStateManager.pushMatrix();
/* 2758 */       GlStateManager.pushAttrib();
/* 2759 */       GlStateManager.enableDepth();
/* 2760 */       GlStateManager.disableCull();
/* 2761 */       RenderHelper.enableStandardItemLighting();
/* 2762 */       GlStateManager.translate((p_190563_1_ / 2) + f5 * MathHelper.abs(MathHelper.sin(f4 * 2.0F)), (
/* 2763 */           p_190563_2_ / 2) + f6 * MathHelper.abs(MathHelper.sin(f4 * 2.0F)), -50.0F);
/* 2764 */       float f7 = 50.0F + 175.0F * MathHelper.sin(f4);
/* 2765 */       GlStateManager.scale(f7, -f7, f7);
/* 2766 */       GlStateManager.rotate(900.0F * MathHelper.abs(MathHelper.sin(f4)), 0.0F, 1.0F, 0.0F);
/* 2767 */       GlStateManager.rotate(6.0F * MathHelper.cos(f * 8.0F), 1.0F, 0.0F, 0.0F);
/* 2768 */       GlStateManager.rotate(6.0F * MathHelper.cos(f * 8.0F), 0.0F, 0.0F, 1.0F);
/* 2769 */       this.mc.getRenderItem().renderItem(this.field_190566_ab, ItemCameraTransforms.TransformType.FIXED);
/* 2770 */       GlStateManager.popAttrib();
/* 2771 */       GlStateManager.popMatrix();
/* 2772 */       RenderHelper.disableStandardItemLighting();
/* 2773 */       GlStateManager.enableCull();
/* 2774 */       GlStateManager.disableDepth();
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\EntityRenderer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */