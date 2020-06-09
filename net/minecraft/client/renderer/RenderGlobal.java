/*      */ package net.minecraft.client.renderer;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.gson.JsonSyntaxException;
/*      */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayDeque;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Deque;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.SoundType;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.audio.ISound;
/*      */ import net.minecraft.client.audio.PositionedSoundRecord;
/*      */ import net.minecraft.client.multiplayer.ChunkProviderClient;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.particle.Particle;
/*      */ import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
/*      */ import net.minecraft.client.renderer.chunk.CompiledChunk;
/*      */ import net.minecraft.client.renderer.chunk.IRenderChunkFactory;
/*      */ import net.minecraft.client.renderer.chunk.ListChunkFactory;
/*      */ import net.minecraft.client.renderer.chunk.RenderChunk;
/*      */ import net.minecraft.client.renderer.chunk.VboChunkFactory;
/*      */ import net.minecraft.client.renderer.chunk.VisGraph;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelper;
/*      */ import net.minecraft.client.renderer.culling.ClippingHelperImpl;
/*      */ import net.minecraft.client.renderer.culling.Frustum;
/*      */ import net.minecraft.client.renderer.culling.ICamera;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.texture.TextureAtlasSprite;
/*      */ import net.minecraft.client.renderer.texture.TextureManager;
/*      */ import net.minecraft.client.renderer.texture.TextureMap;
/*      */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*      */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormat;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*      */ import net.minecraft.client.resources.IResourceManager;
/*      */ import net.minecraft.client.resources.IResourceManagerReloadListener;
/*      */ import net.minecraft.client.shader.Framebuffer;
/*      */ import net.minecraft.client.shader.ShaderGroup;
/*      */ import net.minecraft.client.shader.ShaderLinkHelper;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.crash.ICrashReportDetail;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.init.SoundEvents;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemDye;
/*      */ import net.minecraft.item.ItemRecord;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.BlockRenderLayer;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.SoundCategory;
/*      */ import net.minecraft.util.SoundEvent;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.RayTraceResult;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.world.DimensionType;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.IWorldEventListener;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProvider;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.chunk.IChunkProvider;
/*      */ import optifine.ChunkUtils;
/*      */ import optifine.CloudRenderer;
/*      */ import optifine.Config;
/*      */ import optifine.CustomColors;
/*      */ import optifine.CustomSky;
/*      */ import optifine.DynamicLights;
/*      */ import optifine.Lagometer;
/*      */ import optifine.RandomMobs;
/*      */ import optifine.Reflector;
/*      */ import optifine.RenderEnv;
/*      */ import optifine.RenderInfoLazy;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import org.lwjgl.input.Keyboard;
/*      */ import org.lwjgl.util.vector.Vector3f;
/*      */ import org.lwjgl.util.vector.Vector4f;
/*      */ import shadersmod.client.Shaders;
/*      */ import shadersmod.client.ShadersRender;
/*      */ import shadersmod.client.ShadowUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class RenderGlobal
/*      */   implements IWorldEventListener, IResourceManagerReloadListener
/*      */ {
/*  121 */   private static final Logger LOGGER = LogManager.getLogger();
/*  122 */   private static final ResourceLocation MOON_PHASES_TEXTURES = new ResourceLocation("textures/environment/moon_phases.png");
/*  123 */   private static final ResourceLocation SUN_TEXTURES = new ResourceLocation("textures/environment/sun.png");
/*  124 */   private static final ResourceLocation CLOUDS_TEXTURES = new ResourceLocation("textures/environment/clouds.png");
/*  125 */   private static final ResourceLocation END_SKY_TEXTURES = new ResourceLocation("textures/environment/end_sky.png");
/*  126 */   private static final ResourceLocation FORCEFIELD_TEXTURES = new ResourceLocation("textures/misc/forcefield.png");
/*      */   
/*      */   public final Minecraft mc;
/*      */   
/*      */   private final TextureManager renderEngine;
/*      */   
/*      */   private final RenderManager renderManager;
/*      */   
/*      */   private WorldClient theWorld;
/*  135 */   private Set<RenderChunk> chunksToUpdate = Sets.newLinkedHashSet();
/*  136 */   private List<ContainerLocalRenderInformation> renderInfos = Lists.newArrayListWithCapacity(69696);
/*  137 */   private final Set<TileEntity> setTileEntities = Sets.newHashSet();
/*      */   
/*      */   private ViewFrustum viewFrustum;
/*      */   
/*  141 */   private int starGLCallList = -1;
/*      */ 
/*      */   
/*  144 */   private int glSkyList = -1;
/*      */ 
/*      */   
/*  147 */   private int glSkyList2 = -1;
/*      */   
/*      */   private final VertexFormat vertexBufferFormat;
/*      */   
/*      */   private VertexBuffer starVBO;
/*      */   
/*      */   private VertexBuffer skyVBO;
/*      */   
/*      */   private VertexBuffer sky2VBO;
/*      */   private int cloudTickCounter;
/*  157 */   public final Map<Integer, DestroyBlockProgress> damagedBlocks = Maps.newHashMap();
/*  158 */   private final Map<BlockPos, ISound> mapSoundPositions = Maps.newHashMap();
/*  159 */   private final TextureAtlasSprite[] destroyBlockIcons = new TextureAtlasSprite[10];
/*      */   
/*      */   private Framebuffer entityOutlineFramebuffer;
/*      */   
/*      */   private ShaderGroup entityOutlineShader;
/*  164 */   private double frustumUpdatePosX = Double.MIN_VALUE;
/*  165 */   private double frustumUpdatePosY = Double.MIN_VALUE;
/*  166 */   private double frustumUpdatePosZ = Double.MIN_VALUE;
/*  167 */   private int frustumUpdatePosChunkX = Integer.MIN_VALUE;
/*  168 */   private int frustumUpdatePosChunkY = Integer.MIN_VALUE;
/*  169 */   private int frustumUpdatePosChunkZ = Integer.MIN_VALUE;
/*  170 */   private double lastViewEntityX = Double.MIN_VALUE;
/*  171 */   private double lastViewEntityY = Double.MIN_VALUE;
/*  172 */   private double lastViewEntityZ = Double.MIN_VALUE;
/*  173 */   private double lastViewEntityPitch = Double.MIN_VALUE;
/*  174 */   private double lastViewEntityYaw = Double.MIN_VALUE;
/*      */   private ChunkRenderDispatcher renderDispatcher;
/*      */   private ChunkRenderContainer renderContainer;
/*  177 */   private int renderDistanceChunks = -1;
/*      */ 
/*      */   
/*  180 */   private int renderEntitiesStartupCounter = 2;
/*      */   
/*      */   private int countEntitiesTotal;
/*      */   
/*      */   private int countEntitiesRendered;
/*      */   
/*      */   private int countEntitiesHidden;
/*      */   
/*      */   private boolean debugFixTerrainFrustum;
/*      */   
/*      */   private ClippingHelper debugFixedClippingHelper;
/*      */   
/*  192 */   private final Vector4f[] debugTerrainMatrix = new Vector4f[8];
/*  193 */   private final Vector3d debugTerrainFrustumPosition = new Vector3d();
/*      */   private boolean vboEnabled;
/*      */   IRenderChunkFactory renderChunkFactory;
/*      */   private double prevRenderSortX;
/*      */   private double prevRenderSortY;
/*      */   private double prevRenderSortZ;
/*      */   public boolean displayListEntitiesDirty = true;
/*      */   private boolean entityOutlinesRendered;
/*  201 */   private final Set<BlockPos> setLightUpdates = Sets.newHashSet();
/*      */   private CloudRenderer cloudRenderer;
/*      */   public Entity renderedEntity;
/*  204 */   public Set chunksToResortTransparency = new LinkedHashSet();
/*  205 */   public Set chunksToUpdateForced = new LinkedHashSet();
/*  206 */   private Deque visibilityDeque = new ArrayDeque();
/*  207 */   private List renderInfosEntities = new ArrayList(1024);
/*  208 */   private List renderInfosTileEntities = new ArrayList(1024);
/*  209 */   private List renderInfosNormal = new ArrayList(1024);
/*  210 */   private List renderInfosEntitiesNormal = new ArrayList(1024);
/*  211 */   private List renderInfosTileEntitiesNormal = new ArrayList(1024);
/*  212 */   private List renderInfosShadow = new ArrayList(1024);
/*  213 */   private List renderInfosEntitiesShadow = new ArrayList(1024);
/*  214 */   private List renderInfosTileEntitiesShadow = new ArrayList(1024);
/*  215 */   private int renderDistance = 0;
/*  216 */   private int renderDistanceSq = 0;
/*  217 */   private static final Set SET_ALL_FACINGS = Collections.unmodifiableSet(new HashSet(Arrays.asList((Object[])EnumFacing.VALUES)));
/*      */   private int countTileEntitiesRendered;
/*  219 */   private IChunkProvider worldChunkProvider = null;
/*  220 */   private Long2ObjectMap<Chunk> worldChunkProviderMap = null;
/*  221 */   private int countLoadedChunksPrev = 0;
/*      */   private RenderEnv renderEnv;
/*      */   public boolean renderOverlayDamaged;
/*      */   public boolean renderOverlayEyes;
/*  225 */   static Deque<ContainerLocalRenderInformation> renderInfoCache = new ArrayDeque<>();
/*      */ 
/*      */   
/*      */   public RenderGlobal(Minecraft mcIn) {
/*  229 */     this.renderEnv = new RenderEnv((IBlockAccess)this.theWorld, Blocks.AIR.getDefaultState(), new BlockPos(0, 0, 0));
/*  230 */     this.renderOverlayDamaged = false;
/*  231 */     this.renderOverlayEyes = false;
/*  232 */     this.cloudRenderer = new CloudRenderer(mcIn);
/*  233 */     this.mc = mcIn;
/*  234 */     this.renderManager = mcIn.getRenderManager();
/*  235 */     this.renderEngine = mcIn.getTextureManager();
/*  236 */     this.renderEngine.bindTexture(FORCEFIELD_TEXTURES);
/*  237 */     GlStateManager.glTexParameteri(3553, 10242, 10497);
/*  238 */     GlStateManager.glTexParameteri(3553, 10243, 10497);
/*  239 */     GlStateManager.bindTexture(0);
/*  240 */     updateDestroyBlockIcons();
/*  241 */     this.vboEnabled = OpenGlHelper.useVbo();
/*      */     
/*  243 */     if (this.vboEnabled) {
/*      */       
/*  245 */       this.renderContainer = new VboRenderList();
/*  246 */       this.renderChunkFactory = (IRenderChunkFactory)new VboChunkFactory();
/*      */     }
/*      */     else {
/*      */       
/*  250 */       this.renderContainer = new RenderList();
/*  251 */       this.renderChunkFactory = (IRenderChunkFactory)new ListChunkFactory();
/*      */     } 
/*      */     
/*  254 */     this.vertexBufferFormat = new VertexFormat();
/*  255 */     this.vertexBufferFormat.addElement(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUsage.POSITION, 3));
/*  256 */     generateStars();
/*  257 */     generateSky();
/*  258 */     generateSky2();
/*      */   }
/*      */ 
/*      */   
/*      */   public void onResourceManagerReload(IResourceManager resourceManager) {
/*  263 */     updateDestroyBlockIcons();
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateDestroyBlockIcons() {
/*  268 */     TextureMap texturemap = this.mc.getTextureMapBlocks();
/*      */     
/*  270 */     for (int i = 0; i < this.destroyBlockIcons.length; i++)
/*      */     {
/*  272 */       this.destroyBlockIcons[i] = texturemap.getAtlasSprite("minecraft:blocks/destroy_stage_" + i);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void makeEntityOutlineShader() {
/*  281 */     if (OpenGlHelper.shadersSupported) {
/*      */       
/*  283 */       if (ShaderLinkHelper.getStaticShaderLinkHelper() == null)
/*      */       {
/*  285 */         ShaderLinkHelper.setNewStaticShaderLinkHelper();
/*      */       }
/*      */       
/*  288 */       ResourceLocation resourcelocation = new ResourceLocation("shaders/post/entity_outline.json");
/*      */ 
/*      */       
/*      */       try {
/*  292 */         this.entityOutlineShader = new ShaderGroup(this.mc.getTextureManager(), this.mc.getResourceManager(), this.mc.getFramebuffer(), resourcelocation);
/*  293 */         this.entityOutlineShader.createBindFramebuffers(this.mc.displayWidth, this.mc.displayHeight);
/*  294 */         this.entityOutlineFramebuffer = this.entityOutlineShader.getFramebufferRaw("final");
/*      */       }
/*  296 */       catch (IOException ioexception) {
/*      */         
/*  298 */         LOGGER.warn("Failed to load shader: {}", resourcelocation, ioexception);
/*  299 */         this.entityOutlineShader = null;
/*  300 */         this.entityOutlineFramebuffer = null;
/*      */       }
/*  302 */       catch (JsonSyntaxException jsonsyntaxexception) {
/*      */         
/*  304 */         LOGGER.warn("Failed to load shader: {}", resourcelocation, jsonsyntaxexception);
/*  305 */         this.entityOutlineShader = null;
/*  306 */         this.entityOutlineFramebuffer = null;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  311 */       this.entityOutlineShader = null;
/*  312 */       this.entityOutlineFramebuffer = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderEntityOutlineFramebuffer() {
/*  318 */     if (isRenderEntityOutlines()) {
/*      */       
/*  320 */       GlStateManager.enableBlend();
/*  321 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ZERO, GlStateManager.DestFactor.ONE);
/*  322 */       this.entityOutlineFramebuffer.framebufferRenderExt(this.mc.displayWidth, this.mc.displayHeight, false);
/*  323 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isRenderEntityOutlines() {
/*  329 */     if (!Config.isFastRender() && !Config.isShaders() && !Config.isAntialiasing())
/*      */     {
/*  331 */       return (this.entityOutlineFramebuffer != null && this.entityOutlineShader != null && this.mc.player != null);
/*      */     }
/*      */ 
/*      */     
/*  335 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void generateSky2() {
/*  341 */     Tessellator tessellator = Tessellator.getInstance();
/*  342 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*      */     
/*  344 */     if (this.sky2VBO != null)
/*      */     {
/*  346 */       this.sky2VBO.deleteGlBuffers();
/*      */     }
/*      */     
/*  349 */     if (this.glSkyList2 >= 0) {
/*      */       
/*  351 */       GLAllocation.deleteDisplayLists(this.glSkyList2);
/*  352 */       this.glSkyList2 = -1;
/*      */     } 
/*      */     
/*  355 */     if (this.vboEnabled) {
/*      */       
/*  357 */       this.sky2VBO = new VertexBuffer(this.vertexBufferFormat);
/*  358 */       renderSky(bufferbuilder, -16.0F, true);
/*  359 */       bufferbuilder.finishDrawing();
/*  360 */       bufferbuilder.reset();
/*  361 */       this.sky2VBO.bufferData(bufferbuilder.getByteBuffer());
/*      */     }
/*      */     else {
/*      */       
/*  365 */       this.glSkyList2 = GLAllocation.generateDisplayLists(1);
/*  366 */       GlStateManager.glNewList(this.glSkyList2, 4864);
/*  367 */       renderSky(bufferbuilder, -16.0F, true);
/*  368 */       tessellator.draw();
/*  369 */       GlStateManager.glEndList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void generateSky() {
/*  375 */     Tessellator tessellator = Tessellator.getInstance();
/*  376 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*      */     
/*  378 */     if (this.skyVBO != null)
/*      */     {
/*  380 */       this.skyVBO.deleteGlBuffers();
/*      */     }
/*      */     
/*  383 */     if (this.glSkyList >= 0) {
/*      */       
/*  385 */       GLAllocation.deleteDisplayLists(this.glSkyList);
/*  386 */       this.glSkyList = -1;
/*      */     } 
/*      */     
/*  389 */     if (this.vboEnabled) {
/*      */       
/*  391 */       this.skyVBO = new VertexBuffer(this.vertexBufferFormat);
/*  392 */       renderSky(bufferbuilder, 16.0F, false);
/*  393 */       bufferbuilder.finishDrawing();
/*  394 */       bufferbuilder.reset();
/*  395 */       this.skyVBO.bufferData(bufferbuilder.getByteBuffer());
/*      */     }
/*      */     else {
/*      */       
/*  399 */       this.glSkyList = GLAllocation.generateDisplayLists(1);
/*  400 */       GlStateManager.glNewList(this.glSkyList, 4864);
/*  401 */       renderSky(bufferbuilder, 16.0F, false);
/*  402 */       tessellator.draw();
/*  403 */       GlStateManager.glEndList();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderSky(BufferBuilder worldRendererIn, float posY, boolean reverseX) {
/*  409 */     int i = 64;
/*  410 */     int j = 6;
/*  411 */     worldRendererIn.begin(7, DefaultVertexFormats.POSITION);
/*      */     
/*  413 */     for (int k = -384; k <= 384; k += 64) {
/*      */       
/*  415 */       for (int l = -384; l <= 384; l += 64) {
/*      */         
/*  417 */         float f = k;
/*  418 */         float f1 = (k + 64);
/*      */         
/*  420 */         if (reverseX) {
/*      */           
/*  422 */           f1 = k;
/*  423 */           f = (k + 64);
/*      */         } 
/*      */         
/*  426 */         worldRendererIn.pos(f, posY, l).endVertex();
/*  427 */         worldRendererIn.pos(f1, posY, l).endVertex();
/*  428 */         worldRendererIn.pos(f1, posY, (l + 64)).endVertex();
/*  429 */         worldRendererIn.pos(f, posY, (l + 64)).endVertex();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void generateStars() {
/*  436 */     Tessellator tessellator = Tessellator.getInstance();
/*  437 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/*      */     
/*  439 */     if (this.starVBO != null)
/*      */     {
/*  441 */       this.starVBO.deleteGlBuffers();
/*      */     }
/*      */     
/*  444 */     if (this.starGLCallList >= 0) {
/*      */       
/*  446 */       GLAllocation.deleteDisplayLists(this.starGLCallList);
/*  447 */       this.starGLCallList = -1;
/*      */     } 
/*      */     
/*  450 */     if (this.vboEnabled) {
/*      */       
/*  452 */       this.starVBO = new VertexBuffer(this.vertexBufferFormat);
/*  453 */       renderStars(bufferbuilder);
/*  454 */       bufferbuilder.finishDrawing();
/*  455 */       bufferbuilder.reset();
/*  456 */       this.starVBO.bufferData(bufferbuilder.getByteBuffer());
/*      */     }
/*      */     else {
/*      */       
/*  460 */       this.starGLCallList = GLAllocation.generateDisplayLists(1);
/*  461 */       GlStateManager.pushMatrix();
/*  462 */       GlStateManager.glNewList(this.starGLCallList, 4864);
/*  463 */       renderStars(bufferbuilder);
/*  464 */       tessellator.draw();
/*  465 */       GlStateManager.glEndList();
/*  466 */       GlStateManager.popMatrix();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderStars(BufferBuilder worldRendererIn) {
/*  472 */     Random random = new Random(10842L);
/*  473 */     worldRendererIn.begin(7, DefaultVertexFormats.POSITION);
/*      */     
/*  475 */     for (int i = 0; i < 1500; i++) {
/*      */       
/*  477 */       double d0 = (random.nextFloat() * 2.0F - 1.0F);
/*  478 */       double d1 = (random.nextFloat() * 2.0F - 1.0F);
/*  479 */       double d2 = (random.nextFloat() * 2.0F - 1.0F);
/*  480 */       double d3 = (0.15F + random.nextFloat() * 0.1F);
/*  481 */       double d4 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */       
/*  483 */       if (d4 < 1.0D && d4 > 0.01D) {
/*      */         
/*  485 */         d4 = 1.0D / Math.sqrt(d4);
/*  486 */         d0 *= d4;
/*  487 */         d1 *= d4;
/*  488 */         d2 *= d4;
/*  489 */         double d5 = d0 * 100.0D;
/*  490 */         double d6 = d1 * 100.0D;
/*  491 */         double d7 = d2 * 100.0D;
/*  492 */         double d8 = Math.atan2(d0, d2);
/*  493 */         double d9 = Math.sin(d8);
/*  494 */         double d10 = Math.cos(d8);
/*  495 */         double d11 = Math.atan2(Math.sqrt(d0 * d0 + d2 * d2), d1);
/*  496 */         double d12 = Math.sin(d11);
/*  497 */         double d13 = Math.cos(d11);
/*  498 */         double d14 = random.nextDouble() * Math.PI * 2.0D;
/*  499 */         double d15 = Math.sin(d14);
/*  500 */         double d16 = Math.cos(d14);
/*      */         
/*  502 */         for (int j = 0; j < 4; j++) {
/*      */           
/*  504 */           double d17 = 0.0D;
/*  505 */           double d18 = ((j & 0x2) - 1) * d3;
/*  506 */           double d19 = ((j + 1 & 0x2) - 1) * d3;
/*  507 */           double d20 = 0.0D;
/*  508 */           double d21 = d18 * d16 - d19 * d15;
/*  509 */           double d22 = d19 * d16 + d18 * d15;
/*  510 */           double d23 = d21 * d12 + 0.0D * d13;
/*  511 */           double d24 = 0.0D * d12 - d21 * d13;
/*  512 */           double d25 = d24 * d9 - d22 * d10;
/*  513 */           double d26 = d22 * d9 + d24 * d10;
/*  514 */           worldRendererIn.pos(d5 + d25, d6 + d23, d7 + d26).endVertex();
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorldAndLoadRenderers(@Nullable WorldClient worldClientIn) {
/*  525 */     if (this.theWorld != null)
/*      */     {
/*  527 */       this.theWorld.removeEventListener(this);
/*      */     }
/*      */     
/*  530 */     this.frustumUpdatePosX = Double.MIN_VALUE;
/*  531 */     this.frustumUpdatePosY = Double.MIN_VALUE;
/*  532 */     this.frustumUpdatePosZ = Double.MIN_VALUE;
/*  533 */     this.frustumUpdatePosChunkX = Integer.MIN_VALUE;
/*  534 */     this.frustumUpdatePosChunkY = Integer.MIN_VALUE;
/*  535 */     this.frustumUpdatePosChunkZ = Integer.MIN_VALUE;
/*  536 */     this.renderManager.set((World)worldClientIn);
/*  537 */     this.theWorld = worldClientIn;
/*      */     
/*  539 */     if (Config.isDynamicLights())
/*      */     {
/*  541 */       DynamicLights.clear();
/*      */     }
/*      */     
/*  544 */     if (worldClientIn != null) {
/*      */       
/*  546 */       worldClientIn.addEventListener(this);
/*  547 */       loadRenderers();
/*      */     }
/*      */     else {
/*      */       
/*  551 */       this.chunksToUpdate.clear();
/*  552 */       this.renderInfos.clear();
/*      */       
/*  554 */       if (this.viewFrustum != null) {
/*      */         
/*  556 */         this.viewFrustum.deleteGlResources();
/*  557 */         this.viewFrustum = null;
/*      */       } 
/*      */       
/*  560 */       if (this.renderDispatcher != null)
/*      */       {
/*  562 */         this.renderDispatcher.stopWorkerThreads();
/*      */       }
/*      */       
/*  565 */       this.renderDispatcher = null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadRenderers() {
/*  574 */     if (this.theWorld != null) {
/*      */       
/*  576 */       if (this.renderDispatcher == null)
/*      */       {
/*  578 */         this.renderDispatcher = new ChunkRenderDispatcher();
/*      */       }
/*      */       
/*  581 */       this.displayListEntitiesDirty = true;
/*  582 */       Blocks.LEAVES.setGraphicsLevel(Config.isTreesFancy());
/*  583 */       Blocks.LEAVES2.setGraphicsLevel(Config.isTreesFancy());
/*  584 */       BlockModelRenderer.updateAoLightValue();
/*  585 */       renderInfoCache.clear();
/*      */       
/*  587 */       if (Config.isDynamicLights())
/*      */       {
/*  589 */         DynamicLights.clear();
/*      */       }
/*      */       
/*  592 */       this.renderDistanceChunks = this.mc.gameSettings.renderDistanceChunks;
/*  593 */       this.renderDistance = this.renderDistanceChunks * 16;
/*  594 */       this.renderDistanceSq = this.renderDistance * this.renderDistance;
/*  595 */       boolean flag = this.vboEnabled;
/*  596 */       this.vboEnabled = OpenGlHelper.useVbo();
/*      */       
/*  598 */       if (flag && !this.vboEnabled) {
/*      */         
/*  600 */         this.renderContainer = new RenderList();
/*  601 */         this.renderChunkFactory = (IRenderChunkFactory)new ListChunkFactory();
/*      */       }
/*  603 */       else if (!flag && this.vboEnabled) {
/*      */         
/*  605 */         this.renderContainer = new VboRenderList();
/*  606 */         this.renderChunkFactory = (IRenderChunkFactory)new VboChunkFactory();
/*      */       } 
/*      */       
/*  609 */       if (flag != this.vboEnabled) {
/*      */         
/*  611 */         generateStars();
/*  612 */         generateSky();
/*  613 */         generateSky2();
/*      */       } 
/*      */       
/*  616 */       if (this.viewFrustum != null)
/*      */       {
/*  618 */         this.viewFrustum.deleteGlResources();
/*      */       }
/*      */       
/*  621 */       stopChunkUpdates();
/*      */       
/*  623 */       synchronized (this.setTileEntities) {
/*      */         
/*  625 */         this.setTileEntities.clear();
/*      */       } 
/*      */       
/*  628 */       this.viewFrustum = new ViewFrustum((World)this.theWorld, this.mc.gameSettings.renderDistanceChunks, this, this.renderChunkFactory);
/*      */       
/*  630 */       if (this.theWorld != null) {
/*      */         
/*  632 */         Entity entity = this.mc.getRenderViewEntity();
/*      */         
/*  634 */         if (entity != null)
/*      */         {
/*  636 */           this.viewFrustum.updateChunkPositions(entity.posX, entity.posZ);
/*      */         }
/*      */       } 
/*      */       
/*  640 */       this.renderEntitiesStartupCounter = 2;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void stopChunkUpdates() {
/*  646 */     this.chunksToUpdate.clear();
/*  647 */     this.renderDispatcher.stopChunkUpdates();
/*      */   }
/*      */ 
/*      */   
/*      */   public void createBindEntityOutlineFbs(int width, int height) {
/*  652 */     if (OpenGlHelper.shadersSupported && this.entityOutlineShader != null)
/*      */     {
/*  654 */       this.entityOutlineShader.createBindFramebuffers(width, height);
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
/*      */   public void renderEntities(Entity renderViewEntity, ICamera camera, float partialTicks) {
/*      */     // Byte code:
/*      */     //   0: iconst_0
/*      */     //   1: istore #4
/*      */     //   3: getstatic optifine/Reflector.MinecraftForgeClient_getRenderPass : Loptifine/ReflectorMethod;
/*      */     //   6: invokevirtual exists : ()Z
/*      */     //   9: ifeq -> 24
/*      */     //   12: getstatic optifine/Reflector.MinecraftForgeClient_getRenderPass : Loptifine/ReflectorMethod;
/*      */     //   15: iconst_0
/*      */     //   16: anewarray java/lang/Object
/*      */     //   19: invokestatic callInt : (Loptifine/ReflectorMethod;[Ljava/lang/Object;)I
/*      */     //   22: istore #4
/*      */     //   24: aload_0
/*      */     //   25: getfield renderEntitiesStartupCounter : I
/*      */     //   28: ifle -> 50
/*      */     //   31: iload #4
/*      */     //   33: ifle -> 37
/*      */     //   36: return
/*      */     //   37: aload_0
/*      */     //   38: dup
/*      */     //   39: getfield renderEntitiesStartupCounter : I
/*      */     //   42: iconst_1
/*      */     //   43: isub
/*      */     //   44: putfield renderEntitiesStartupCounter : I
/*      */     //   47: goto -> 2208
/*      */     //   50: aload_1
/*      */     //   51: getfield prevPosX : D
/*      */     //   54: aload_1
/*      */     //   55: getfield posX : D
/*      */     //   58: aload_1
/*      */     //   59: getfield prevPosX : D
/*      */     //   62: dsub
/*      */     //   63: fload_3
/*      */     //   64: f2d
/*      */     //   65: dmul
/*      */     //   66: dadd
/*      */     //   67: dstore #5
/*      */     //   69: aload_1
/*      */     //   70: getfield prevPosY : D
/*      */     //   73: aload_1
/*      */     //   74: getfield posY : D
/*      */     //   77: aload_1
/*      */     //   78: getfield prevPosY : D
/*      */     //   81: dsub
/*      */     //   82: fload_3
/*      */     //   83: f2d
/*      */     //   84: dmul
/*      */     //   85: dadd
/*      */     //   86: dstore #7
/*      */     //   88: aload_1
/*      */     //   89: getfield prevPosZ : D
/*      */     //   92: aload_1
/*      */     //   93: getfield posZ : D
/*      */     //   96: aload_1
/*      */     //   97: getfield prevPosZ : D
/*      */     //   100: dsub
/*      */     //   101: fload_3
/*      */     //   102: f2d
/*      */     //   103: dmul
/*      */     //   104: dadd
/*      */     //   105: dstore #9
/*      */     //   107: aload_0
/*      */     //   108: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   111: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   114: ldc_w 'prepare'
/*      */     //   117: invokevirtual startSection : (Ljava/lang/String;)V
/*      */     //   120: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   123: aload_0
/*      */     //   124: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   127: aload_0
/*      */     //   128: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   131: invokevirtual getTextureManager : ()Lnet/minecraft/client/renderer/texture/TextureManager;
/*      */     //   134: aload_0
/*      */     //   135: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   138: getfield fontRendererObj : Lnet/minecraft/client/gui/FontRenderer;
/*      */     //   141: aload_0
/*      */     //   142: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   145: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   148: aload_0
/*      */     //   149: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   152: getfield objectMouseOver : Lnet/minecraft/util/math/RayTraceResult;
/*      */     //   155: fload_3
/*      */     //   156: invokevirtual prepare : (Lnet/minecraft/world/World;Lnet/minecraft/client/renderer/texture/TextureManager;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/entity/Entity;Lnet/minecraft/util/math/RayTraceResult;F)V
/*      */     //   159: aload_0
/*      */     //   160: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   163: aload_0
/*      */     //   164: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   167: aload_0
/*      */     //   168: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   171: getfield fontRendererObj : Lnet/minecraft/client/gui/FontRenderer;
/*      */     //   174: aload_0
/*      */     //   175: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   178: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   181: aload_0
/*      */     //   182: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   185: getfield pointedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   188: aload_0
/*      */     //   189: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   192: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   195: fload_3
/*      */     //   196: invokevirtual cacheActiveRenderInfo : (Lnet/minecraft/world/World;Lnet/minecraft/client/gui/FontRenderer;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/settings/GameSettings;F)V
/*      */     //   199: iload #4
/*      */     //   201: ifne -> 224
/*      */     //   204: aload_0
/*      */     //   205: iconst_0
/*      */     //   206: putfield countEntitiesTotal : I
/*      */     //   209: aload_0
/*      */     //   210: iconst_0
/*      */     //   211: putfield countEntitiesRendered : I
/*      */     //   214: aload_0
/*      */     //   215: iconst_0
/*      */     //   216: putfield countEntitiesHidden : I
/*      */     //   219: aload_0
/*      */     //   220: iconst_0
/*      */     //   221: putfield countTileEntitiesRendered : I
/*      */     //   224: aload_0
/*      */     //   225: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   228: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   231: astore #11
/*      */     //   233: aload #11
/*      */     //   235: getfield lastTickPosX : D
/*      */     //   238: aload #11
/*      */     //   240: getfield posX : D
/*      */     //   243: aload #11
/*      */     //   245: getfield lastTickPosX : D
/*      */     //   248: dsub
/*      */     //   249: fload_3
/*      */     //   250: f2d
/*      */     //   251: dmul
/*      */     //   252: dadd
/*      */     //   253: dstore #12
/*      */     //   255: aload #11
/*      */     //   257: getfield lastTickPosY : D
/*      */     //   260: aload #11
/*      */     //   262: getfield posY : D
/*      */     //   265: aload #11
/*      */     //   267: getfield lastTickPosY : D
/*      */     //   270: dsub
/*      */     //   271: fload_3
/*      */     //   272: f2d
/*      */     //   273: dmul
/*      */     //   274: dadd
/*      */     //   275: dstore #14
/*      */     //   277: aload #11
/*      */     //   279: getfield lastTickPosZ : D
/*      */     //   282: aload #11
/*      */     //   284: getfield posZ : D
/*      */     //   287: aload #11
/*      */     //   289: getfield lastTickPosZ : D
/*      */     //   292: dsub
/*      */     //   293: fload_3
/*      */     //   294: f2d
/*      */     //   295: dmul
/*      */     //   296: dadd
/*      */     //   297: dstore #16
/*      */     //   299: dload #12
/*      */     //   301: putstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerX : D
/*      */     //   304: dload #14
/*      */     //   306: putstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerY : D
/*      */     //   309: dload #16
/*      */     //   311: putstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.staticPlayerZ : D
/*      */     //   314: aload_0
/*      */     //   315: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   318: dload #12
/*      */     //   320: dload #14
/*      */     //   322: dload #16
/*      */     //   324: invokevirtual setRenderPosition : (DDD)V
/*      */     //   327: aload_0
/*      */     //   328: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   331: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
/*      */     //   334: invokevirtual enableLightmap : ()V
/*      */     //   337: aload_0
/*      */     //   338: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   341: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   344: ldc_w 'global'
/*      */     //   347: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   350: aload_0
/*      */     //   351: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   354: invokevirtual getLoadedEntityList : ()Ljava/util/List;
/*      */     //   357: astore #18
/*      */     //   359: iload #4
/*      */     //   361: ifne -> 375
/*      */     //   364: aload_0
/*      */     //   365: aload #18
/*      */     //   367: invokeinterface size : ()I
/*      */     //   372: putfield countEntitiesTotal : I
/*      */     //   375: invokestatic isFogOff : ()Z
/*      */     //   378: ifeq -> 397
/*      */     //   381: aload_0
/*      */     //   382: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   385: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
/*      */     //   388: getfield fogStandard : Z
/*      */     //   391: ifeq -> 397
/*      */     //   394: invokestatic disableFog : ()V
/*      */     //   397: getstatic optifine/Reflector.ForgeEntity_shouldRenderInPass : Loptifine/ReflectorMethod;
/*      */     //   400: invokevirtual exists : ()Z
/*      */     //   403: istore #19
/*      */     //   405: getstatic optifine/Reflector.ForgeTileEntity_shouldRenderInPass : Loptifine/ReflectorMethod;
/*      */     //   408: invokevirtual exists : ()Z
/*      */     //   411: istore #20
/*      */     //   413: iconst_0
/*      */     //   414: istore #21
/*      */     //   416: goto -> 504
/*      */     //   419: aload_0
/*      */     //   420: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   423: getfield weatherEffects : Ljava/util/List;
/*      */     //   426: iload #21
/*      */     //   428: invokeinterface get : (I)Ljava/lang/Object;
/*      */     //   433: checkcast net/minecraft/entity/Entity
/*      */     //   436: astore #22
/*      */     //   438: iload #19
/*      */     //   440: ifeq -> 466
/*      */     //   443: aload #22
/*      */     //   445: getstatic optifine/Reflector.ForgeEntity_shouldRenderInPass : Loptifine/ReflectorMethod;
/*      */     //   448: iconst_1
/*      */     //   449: anewarray java/lang/Object
/*      */     //   452: dup
/*      */     //   453: iconst_0
/*      */     //   454: iload #4
/*      */     //   456: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   459: aastore
/*      */     //   460: invokestatic callBoolean : (Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   463: ifeq -> 501
/*      */     //   466: aload_0
/*      */     //   467: dup
/*      */     //   468: getfield countEntitiesRendered : I
/*      */     //   471: iconst_1
/*      */     //   472: iadd
/*      */     //   473: putfield countEntitiesRendered : I
/*      */     //   476: aload #22
/*      */     //   478: dload #5
/*      */     //   480: dload #7
/*      */     //   482: dload #9
/*      */     //   484: invokevirtual isInRangeToRender3d : (DDD)Z
/*      */     //   487: ifeq -> 501
/*      */     //   490: aload_0
/*      */     //   491: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   494: aload #22
/*      */     //   496: fload_3
/*      */     //   497: iconst_0
/*      */     //   498: invokevirtual renderEntityStatic : (Lnet/minecraft/entity/Entity;FZ)V
/*      */     //   501: iinc #21, 1
/*      */     //   504: iload #21
/*      */     //   506: aload_0
/*      */     //   507: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   510: getfield weatherEffects : Ljava/util/List;
/*      */     //   513: invokeinterface size : ()I
/*      */     //   518: if_icmplt -> 419
/*      */     //   521: aload_0
/*      */     //   522: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   525: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   528: ldc_w 'entities'
/*      */     //   531: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   534: invokestatic isShaders : ()Z
/*      */     //   537: istore #21
/*      */     //   539: iload #21
/*      */     //   541: ifeq -> 547
/*      */     //   544: invokestatic beginEntities : ()V
/*      */     //   547: aload_0
/*      */     //   548: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   551: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   554: getfield fancyGraphics : Z
/*      */     //   557: istore #22
/*      */     //   559: aload_0
/*      */     //   560: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   563: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   566: invokestatic isDroppedItemsFancy : ()Z
/*      */     //   569: putfield fancyGraphics : Z
/*      */     //   572: invokestatic newArrayList : ()Ljava/util/ArrayList;
/*      */     //   575: astore #23
/*      */     //   577: invokestatic newArrayList : ()Ljava/util/ArrayList;
/*      */     //   580: astore #24
/*      */     //   582: invokestatic retain : ()Lnet/minecraft/util/math/BlockPos$PooledMutableBlockPos;
/*      */     //   585: astore #25
/*      */     //   587: aload_0
/*      */     //   588: getfield renderInfosEntities : Ljava/util/List;
/*      */     //   591: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   596: astore #27
/*      */     //   598: goto -> 954
/*      */     //   601: aload #27
/*      */     //   603: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   608: astore #26
/*      */     //   610: aload #26
/*      */     //   612: checkcast net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation
/*      */     //   615: astore #28
/*      */     //   617: aload #28
/*      */     //   619: getfield renderChunk : Lnet/minecraft/client/renderer/chunk/RenderChunk;
/*      */     //   622: aload_0
/*      */     //   623: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   626: invokevirtual getChunk : (Lnet/minecraft/world/World;)Lnet/minecraft/world/chunk/Chunk;
/*      */     //   629: astore #29
/*      */     //   631: aload #29
/*      */     //   633: invokevirtual getEntityLists : ()[Lnet/minecraft/util/ClassInheritanceMultiMap;
/*      */     //   636: aload #28
/*      */     //   638: getfield renderChunk : Lnet/minecraft/client/renderer/chunk/RenderChunk;
/*      */     //   641: invokevirtual getPosition : ()Lnet/minecraft/util/math/BlockPos;
/*      */     //   644: invokevirtual getY : ()I
/*      */     //   647: bipush #16
/*      */     //   649: idiv
/*      */     //   650: aaload
/*      */     //   651: astore #30
/*      */     //   653: aload #30
/*      */     //   655: invokevirtual isEmpty : ()Z
/*      */     //   658: ifne -> 954
/*      */     //   661: aload #30
/*      */     //   663: invokevirtual iterator : ()Ljava/util/Iterator;
/*      */     //   666: astore #32
/*      */     //   668: goto -> 944
/*      */     //   671: aload #32
/*      */     //   673: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   678: checkcast net/minecraft/entity/Entity
/*      */     //   681: astore #31
/*      */     //   683: iload #19
/*      */     //   685: ifeq -> 711
/*      */     //   688: aload #31
/*      */     //   690: getstatic optifine/Reflector.ForgeEntity_shouldRenderInPass : Loptifine/ReflectorMethod;
/*      */     //   693: iconst_1
/*      */     //   694: anewarray java/lang/Object
/*      */     //   697: dup
/*      */     //   698: iconst_0
/*      */     //   699: iload #4
/*      */     //   701: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   704: aastore
/*      */     //   705: invokestatic callBoolean : (Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   708: ifeq -> 944
/*      */     //   711: aload_0
/*      */     //   712: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   715: aload #31
/*      */     //   717: aload_2
/*      */     //   718: dload #5
/*      */     //   720: dload #7
/*      */     //   722: dload #9
/*      */     //   724: invokevirtual shouldRender : (Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;DDD)Z
/*      */     //   727: ifne -> 749
/*      */     //   730: aload #31
/*      */     //   732: aload_0
/*      */     //   733: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   736: getfield player : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   739: invokevirtual isRidingOrBeingRiddenBy : (Lnet/minecraft/entity/Entity;)Z
/*      */     //   742: ifne -> 749
/*      */     //   745: iconst_0
/*      */     //   746: goto -> 750
/*      */     //   749: iconst_1
/*      */     //   750: istore #33
/*      */     //   752: iload #33
/*      */     //   754: ifeq -> 944
/*      */     //   757: aload_0
/*      */     //   758: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   761: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   764: instanceof net/minecraft/entity/EntityLivingBase
/*      */     //   767: ifeq -> 786
/*      */     //   770: aload_0
/*      */     //   771: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   774: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   777: checkcast net/minecraft/entity/EntityLivingBase
/*      */     //   780: invokevirtual isPlayerSleeping : ()Z
/*      */     //   783: goto -> 787
/*      */     //   786: iconst_0
/*      */     //   787: istore #34
/*      */     //   789: aload #31
/*      */     //   791: aload_0
/*      */     //   792: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   795: invokevirtual getRenderViewEntity : ()Lnet/minecraft/entity/Entity;
/*      */     //   798: if_acmpne -> 819
/*      */     //   801: aload_0
/*      */     //   802: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   805: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   808: getfield thirdPersonView : I
/*      */     //   811: ifne -> 819
/*      */     //   814: iload #34
/*      */     //   816: ifeq -> 944
/*      */     //   819: aload #31
/*      */     //   821: getfield posY : D
/*      */     //   824: dconst_0
/*      */     //   825: dcmpg
/*      */     //   826: iflt -> 858
/*      */     //   829: aload #31
/*      */     //   831: getfield posY : D
/*      */     //   834: ldc2_w 256.0
/*      */     //   837: dcmpl
/*      */     //   838: ifge -> 858
/*      */     //   841: aload_0
/*      */     //   842: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   845: aload #25
/*      */     //   847: aload #31
/*      */     //   849: invokevirtual setPos : (Lnet/minecraft/entity/Entity;)Lnet/minecraft/util/math/BlockPos$PooledMutableBlockPos;
/*      */     //   852: invokevirtual isBlockLoaded : (Lnet/minecraft/util/math/BlockPos;)Z
/*      */     //   855: ifeq -> 944
/*      */     //   858: aload_0
/*      */     //   859: dup
/*      */     //   860: getfield countEntitiesRendered : I
/*      */     //   863: iconst_1
/*      */     //   864: iadd
/*      */     //   865: putfield countEntitiesRendered : I
/*      */     //   868: aload_0
/*      */     //   869: aload #31
/*      */     //   871: putfield renderedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   874: iload #21
/*      */     //   876: ifeq -> 884
/*      */     //   879: aload #31
/*      */     //   881: invokestatic nextEntity : (Lnet/minecraft/entity/Entity;)V
/*      */     //   884: aload_0
/*      */     //   885: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   888: aload #31
/*      */     //   890: fload_3
/*      */     //   891: iconst_0
/*      */     //   892: invokevirtual renderEntityStatic : (Lnet/minecraft/entity/Entity;FZ)V
/*      */     //   895: aload_0
/*      */     //   896: aconst_null
/*      */     //   897: putfield renderedEntity : Lnet/minecraft/entity/Entity;
/*      */     //   900: aload_0
/*      */     //   901: aload #31
/*      */     //   903: aload #11
/*      */     //   905: aload_2
/*      */     //   906: invokespecial isOutlineActive : (Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/Entity;Lnet/minecraft/client/renderer/culling/ICamera;)Z
/*      */     //   909: ifeq -> 922
/*      */     //   912: aload #23
/*      */     //   914: aload #31
/*      */     //   916: invokeinterface add : (Ljava/lang/Object;)Z
/*      */     //   921: pop
/*      */     //   922: aload_0
/*      */     //   923: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   926: aload #31
/*      */     //   928: invokevirtual isRenderMultipass : (Lnet/minecraft/entity/Entity;)Z
/*      */     //   931: ifeq -> 944
/*      */     //   934: aload #24
/*      */     //   936: aload #31
/*      */     //   938: invokeinterface add : (Ljava/lang/Object;)Z
/*      */     //   943: pop
/*      */     //   944: aload #32
/*      */     //   946: invokeinterface hasNext : ()Z
/*      */     //   951: ifne -> 671
/*      */     //   954: aload #27
/*      */     //   956: invokeinterface hasNext : ()Z
/*      */     //   961: ifne -> 601
/*      */     //   964: aload #25
/*      */     //   966: invokevirtual release : ()V
/*      */     //   969: aload #24
/*      */     //   971: invokeinterface isEmpty : ()Z
/*      */     //   976: ifne -> 1061
/*      */     //   979: aload #24
/*      */     //   981: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   986: astore #27
/*      */     //   988: goto -> 1051
/*      */     //   991: aload #27
/*      */     //   993: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   998: checkcast net/minecraft/entity/Entity
/*      */     //   1001: astore #26
/*      */     //   1003: iload #19
/*      */     //   1005: ifeq -> 1031
/*      */     //   1008: aload #26
/*      */     //   1010: getstatic optifine/Reflector.ForgeEntity_shouldRenderInPass : Loptifine/ReflectorMethod;
/*      */     //   1013: iconst_1
/*      */     //   1014: anewarray java/lang/Object
/*      */     //   1017: dup
/*      */     //   1018: iconst_0
/*      */     //   1019: iload #4
/*      */     //   1021: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1024: aastore
/*      */     //   1025: invokestatic callBoolean : (Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1028: ifeq -> 1051
/*      */     //   1031: iload #21
/*      */     //   1033: ifeq -> 1041
/*      */     //   1036: aload #26
/*      */     //   1038: invokestatic nextEntity : (Lnet/minecraft/entity/Entity;)V
/*      */     //   1041: aload_0
/*      */     //   1042: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1045: aload #26
/*      */     //   1047: fload_3
/*      */     //   1048: invokevirtual renderMultipass : (Lnet/minecraft/entity/Entity;F)V
/*      */     //   1051: aload #27
/*      */     //   1053: invokeinterface hasNext : ()Z
/*      */     //   1058: ifne -> 991
/*      */     //   1061: iload #4
/*      */     //   1063: ifne -> 1313
/*      */     //   1066: aload_0
/*      */     //   1067: invokevirtual isRenderEntityOutlines : ()Z
/*      */     //   1070: ifeq -> 1313
/*      */     //   1073: aload #23
/*      */     //   1075: invokeinterface isEmpty : ()Z
/*      */     //   1080: ifeq -> 1090
/*      */     //   1083: aload_0
/*      */     //   1084: getfield entityOutlinesRendered : Z
/*      */     //   1087: ifeq -> 1313
/*      */     //   1090: aload_0
/*      */     //   1091: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1094: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   1097: ldc_w 'entityOutlines'
/*      */     //   1100: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   1103: aload_0
/*      */     //   1104: getfield entityOutlineFramebuffer : Lnet/minecraft/client/shader/Framebuffer;
/*      */     //   1107: invokevirtual framebufferClear : ()V
/*      */     //   1110: aload_0
/*      */     //   1111: aload #23
/*      */     //   1113: invokeinterface isEmpty : ()Z
/*      */     //   1118: ifeq -> 1125
/*      */     //   1121: iconst_0
/*      */     //   1122: goto -> 1126
/*      */     //   1125: iconst_1
/*      */     //   1126: putfield entityOutlinesRendered : Z
/*      */     //   1129: aload #23
/*      */     //   1131: invokeinterface isEmpty : ()Z
/*      */     //   1136: ifne -> 1302
/*      */     //   1139: sipush #519
/*      */     //   1142: invokestatic depthFunc : (I)V
/*      */     //   1145: invokestatic disableFog : ()V
/*      */     //   1148: aload_0
/*      */     //   1149: getfield entityOutlineFramebuffer : Lnet/minecraft/client/shader/Framebuffer;
/*      */     //   1152: iconst_0
/*      */     //   1153: invokevirtual bindFramebuffer : (Z)V
/*      */     //   1156: invokestatic disableStandardItemLighting : ()V
/*      */     //   1159: aload_0
/*      */     //   1160: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1163: iconst_1
/*      */     //   1164: invokevirtual setRenderOutlines : (Z)V
/*      */     //   1167: iconst_0
/*      */     //   1168: istore #26
/*      */     //   1170: goto -> 1239
/*      */     //   1173: aload #23
/*      */     //   1175: iload #26
/*      */     //   1177: invokeinterface get : (I)Ljava/lang/Object;
/*      */     //   1182: checkcast net/minecraft/entity/Entity
/*      */     //   1185: astore #27
/*      */     //   1187: iload #19
/*      */     //   1189: ifeq -> 1215
/*      */     //   1192: aload #27
/*      */     //   1194: getstatic optifine/Reflector.ForgeEntity_shouldRenderInPass : Loptifine/ReflectorMethod;
/*      */     //   1197: iconst_1
/*      */     //   1198: anewarray java/lang/Object
/*      */     //   1201: dup
/*      */     //   1202: iconst_0
/*      */     //   1203: iload #4
/*      */     //   1205: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1208: aastore
/*      */     //   1209: invokestatic callBoolean : (Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1212: ifeq -> 1236
/*      */     //   1215: iload #21
/*      */     //   1217: ifeq -> 1225
/*      */     //   1220: aload #27
/*      */     //   1222: invokestatic nextEntity : (Lnet/minecraft/entity/Entity;)V
/*      */     //   1225: aload_0
/*      */     //   1226: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1229: aload #27
/*      */     //   1231: fload_3
/*      */     //   1232: iconst_0
/*      */     //   1233: invokevirtual renderEntityStatic : (Lnet/minecraft/entity/Entity;FZ)V
/*      */     //   1236: iinc #26, 1
/*      */     //   1239: iload #26
/*      */     //   1241: aload #23
/*      */     //   1243: invokeinterface size : ()I
/*      */     //   1248: if_icmplt -> 1173
/*      */     //   1251: aload_0
/*      */     //   1252: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1255: iconst_0
/*      */     //   1256: invokevirtual setRenderOutlines : (Z)V
/*      */     //   1259: invokestatic enableStandardItemLighting : ()V
/*      */     //   1262: iconst_0
/*      */     //   1263: invokestatic depthMask : (Z)V
/*      */     //   1266: aload_0
/*      */     //   1267: getfield entityOutlineShader : Lnet/minecraft/client/shader/ShaderGroup;
/*      */     //   1270: fload_3
/*      */     //   1271: invokevirtual loadShaderGroup : (F)V
/*      */     //   1274: invokestatic enableLighting : ()V
/*      */     //   1277: iconst_1
/*      */     //   1278: invokestatic depthMask : (Z)V
/*      */     //   1281: invokestatic enableFog : ()V
/*      */     //   1284: invokestatic enableBlend : ()V
/*      */     //   1287: invokestatic enableColorMaterial : ()V
/*      */     //   1290: sipush #515
/*      */     //   1293: invokestatic depthFunc : (I)V
/*      */     //   1296: invokestatic enableDepth : ()V
/*      */     //   1299: invokestatic enableAlpha : ()V
/*      */     //   1302: aload_0
/*      */     //   1303: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1306: invokevirtual getFramebuffer : ()Lnet/minecraft/client/shader/Framebuffer;
/*      */     //   1309: iconst_0
/*      */     //   1310: invokevirtual bindFramebuffer : (Z)V
/*      */     //   1313: aload_0
/*      */     //   1314: invokevirtual isRenderEntityOutlines : ()Z
/*      */     //   1317: ifne -> 1517
/*      */     //   1320: aload #23
/*      */     //   1322: invokeinterface isEmpty : ()Z
/*      */     //   1327: ifeq -> 1337
/*      */     //   1330: aload_0
/*      */     //   1331: getfield entityOutlinesRendered : Z
/*      */     //   1334: ifeq -> 1517
/*      */     //   1337: aload_0
/*      */     //   1338: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1341: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   1344: ldc_w 'entityOutlines'
/*      */     //   1347: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   1350: aload_0
/*      */     //   1351: aload #23
/*      */     //   1353: invokeinterface isEmpty : ()Z
/*      */     //   1358: ifeq -> 1365
/*      */     //   1361: iconst_0
/*      */     //   1362: goto -> 1366
/*      */     //   1365: iconst_1
/*      */     //   1366: putfield entityOutlinesRendered : Z
/*      */     //   1369: aload #23
/*      */     //   1371: invokeinterface isEmpty : ()Z
/*      */     //   1376: ifne -> 1517
/*      */     //   1379: invokestatic disableFog : ()V
/*      */     //   1382: invokestatic disableDepth : ()V
/*      */     //   1385: aload_0
/*      */     //   1386: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1389: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
/*      */     //   1392: invokevirtual disableLightmap : ()V
/*      */     //   1395: invokestatic disableStandardItemLighting : ()V
/*      */     //   1398: aload_0
/*      */     //   1399: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1402: iconst_1
/*      */     //   1403: invokevirtual setRenderOutlines : (Z)V
/*      */     //   1406: iconst_0
/*      */     //   1407: istore #26
/*      */     //   1409: goto -> 1478
/*      */     //   1412: aload #23
/*      */     //   1414: iload #26
/*      */     //   1416: invokeinterface get : (I)Ljava/lang/Object;
/*      */     //   1421: checkcast net/minecraft/entity/Entity
/*      */     //   1424: astore #27
/*      */     //   1426: iload #19
/*      */     //   1428: ifeq -> 1454
/*      */     //   1431: aload #27
/*      */     //   1433: getstatic optifine/Reflector.ForgeEntity_shouldRenderInPass : Loptifine/ReflectorMethod;
/*      */     //   1436: iconst_1
/*      */     //   1437: anewarray java/lang/Object
/*      */     //   1440: dup
/*      */     //   1441: iconst_0
/*      */     //   1442: iload #4
/*      */     //   1444: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1447: aastore
/*      */     //   1448: invokestatic callBoolean : (Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1451: ifeq -> 1475
/*      */     //   1454: iload #21
/*      */     //   1456: ifeq -> 1464
/*      */     //   1459: aload #27
/*      */     //   1461: invokestatic nextEntity : (Lnet/minecraft/entity/Entity;)V
/*      */     //   1464: aload_0
/*      */     //   1465: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1468: aload #27
/*      */     //   1470: fload_3
/*      */     //   1471: iconst_0
/*      */     //   1472: invokevirtual renderEntityStatic : (Lnet/minecraft/entity/Entity;FZ)V
/*      */     //   1475: iinc #26, 1
/*      */     //   1478: iload #26
/*      */     //   1480: aload #23
/*      */     //   1482: invokeinterface size : ()I
/*      */     //   1487: if_icmplt -> 1412
/*      */     //   1490: aload_0
/*      */     //   1491: getfield renderManager : Lnet/minecraft/client/renderer/entity/RenderManager;
/*      */     //   1494: iconst_0
/*      */     //   1495: invokevirtual setRenderOutlines : (Z)V
/*      */     //   1498: invokestatic enableStandardItemLighting : ()V
/*      */     //   1501: aload_0
/*      */     //   1502: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1505: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
/*      */     //   1508: invokevirtual enableLightmap : ()V
/*      */     //   1511: invokestatic enableDepth : ()V
/*      */     //   1514: invokestatic enableFog : ()V
/*      */     //   1517: aload_0
/*      */     //   1518: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1521: getfield gameSettings : Lnet/minecraft/client/settings/GameSettings;
/*      */     //   1524: iload #22
/*      */     //   1526: putfield fancyGraphics : Z
/*      */     //   1529: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1532: invokevirtual getFontRenderer : ()Lnet/minecraft/client/gui/FontRenderer;
/*      */     //   1535: astore #26
/*      */     //   1537: iload #21
/*      */     //   1539: ifeq -> 1548
/*      */     //   1542: invokestatic endEntities : ()V
/*      */     //   1545: invokestatic beginBlockEntities : ()V
/*      */     //   1548: aload_0
/*      */     //   1549: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   1552: getfield theProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   1555: ldc_w 'blockentities'
/*      */     //   1558: invokevirtual endStartSection : (Ljava/lang/String;)V
/*      */     //   1561: invokestatic enableStandardItemLighting : ()V
/*      */     //   1564: getstatic optifine/Reflector.ForgeTileEntity_hasFastRenderer : Loptifine/ReflectorMethod;
/*      */     //   1567: invokevirtual exists : ()Z
/*      */     //   1570: ifeq -> 1579
/*      */     //   1573: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1576: invokevirtual preDrawBatch : ()V
/*      */     //   1579: aload_0
/*      */     //   1580: getfield renderInfosTileEntities : Ljava/util/List;
/*      */     //   1583: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1588: astore #28
/*      */     //   1590: goto -> 1836
/*      */     //   1593: aload #28
/*      */     //   1595: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1600: astore #27
/*      */     //   1602: aload #27
/*      */     //   1604: checkcast net/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation
/*      */     //   1607: astore #29
/*      */     //   1609: aload #29
/*      */     //   1611: getfield renderChunk : Lnet/minecraft/client/renderer/chunk/RenderChunk;
/*      */     //   1614: invokevirtual getCompiledChunk : ()Lnet/minecraft/client/renderer/chunk/CompiledChunk;
/*      */     //   1617: invokevirtual getTileEntities : ()Ljava/util/List;
/*      */     //   1620: astore #30
/*      */     //   1622: aload #30
/*      */     //   1624: invokeinterface isEmpty : ()Z
/*      */     //   1629: ifne -> 1836
/*      */     //   1632: aload #30
/*      */     //   1634: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1639: astore #31
/*      */     //   1641: aload #31
/*      */     //   1643: invokeinterface hasNext : ()Z
/*      */     //   1648: ifne -> 1654
/*      */     //   1651: goto -> 1836
/*      */     //   1654: aload #31
/*      */     //   1656: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1661: checkcast net/minecraft/tileentity/TileEntity
/*      */     //   1664: astore #32
/*      */     //   1666: iload #20
/*      */     //   1668: ifne -> 1674
/*      */     //   1671: goto -> 1730
/*      */     //   1674: aload #32
/*      */     //   1676: getstatic optifine/Reflector.ForgeTileEntity_shouldRenderInPass : Loptifine/ReflectorMethod;
/*      */     //   1679: iconst_1
/*      */     //   1680: anewarray java/lang/Object
/*      */     //   1683: dup
/*      */     //   1684: iconst_0
/*      */     //   1685: iload #4
/*      */     //   1687: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1690: aastore
/*      */     //   1691: invokestatic callBoolean : (Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1694: ifeq -> 1641
/*      */     //   1697: aload #32
/*      */     //   1699: getstatic optifine/Reflector.ForgeTileEntity_getRenderBoundingBox : Loptifine/ReflectorMethod;
/*      */     //   1702: iconst_0
/*      */     //   1703: anewarray java/lang/Object
/*      */     //   1706: invokestatic call : (Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Ljava/lang/Object;
/*      */     //   1709: checkcast net/minecraft/util/math/AxisAlignedBB
/*      */     //   1712: astore #33
/*      */     //   1714: aload #33
/*      */     //   1716: ifnull -> 1730
/*      */     //   1719: aload_2
/*      */     //   1720: aload #33
/*      */     //   1722: invokeinterface isBoundingBoxInFrustum : (Lnet/minecraft/util/math/AxisAlignedBB;)Z
/*      */     //   1727: ifeq -> 1641
/*      */     //   1730: aload #32
/*      */     //   1732: invokevirtual getClass : ()Ljava/lang/Class;
/*      */     //   1735: astore #33
/*      */     //   1737: aload #33
/*      */     //   1739: ldc_w net/minecraft/tileentity/TileEntitySign
/*      */     //   1742: if_acmpne -> 1797
/*      */     //   1745: getstatic optifine/Config.zoomMode : Z
/*      */     //   1748: ifne -> 1797
/*      */     //   1751: aload_0
/*      */     //   1752: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   1755: getfield player : Lnet/minecraft/client/entity/EntityPlayerSP;
/*      */     //   1758: astore #34
/*      */     //   1760: aload #32
/*      */     //   1762: aload #34
/*      */     //   1764: getfield posX : D
/*      */     //   1767: aload #34
/*      */     //   1769: getfield posY : D
/*      */     //   1772: aload #34
/*      */     //   1774: getfield posZ : D
/*      */     //   1777: invokevirtual getDistanceSq : (DDD)D
/*      */     //   1780: dstore #35
/*      */     //   1782: dload #35
/*      */     //   1784: ldc2_w 256.0
/*      */     //   1787: dcmpl
/*      */     //   1788: ifle -> 1797
/*      */     //   1791: aload #26
/*      */     //   1793: iconst_0
/*      */     //   1794: putfield enabled : Z
/*      */     //   1797: iload #21
/*      */     //   1799: ifeq -> 1807
/*      */     //   1802: aload #32
/*      */     //   1804: invokestatic nextBlockEntity : (Lnet/minecraft/tileentity/TileEntity;)V
/*      */     //   1807: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1810: aload #32
/*      */     //   1812: fload_3
/*      */     //   1813: iconst_m1
/*      */     //   1814: invokevirtual renderTileEntity : (Lnet/minecraft/tileentity/TileEntity;FI)V
/*      */     //   1817: aload_0
/*      */     //   1818: dup
/*      */     //   1819: getfield countTileEntitiesRendered : I
/*      */     //   1822: iconst_1
/*      */     //   1823: iadd
/*      */     //   1824: putfield countTileEntitiesRendered : I
/*      */     //   1827: aload #26
/*      */     //   1829: iconst_1
/*      */     //   1830: putfield enabled : Z
/*      */     //   1833: goto -> 1641
/*      */     //   1836: aload #28
/*      */     //   1838: invokeinterface hasNext : ()Z
/*      */     //   1843: ifne -> 1593
/*      */     //   1846: aload_0
/*      */     //   1847: getfield setTileEntities : Ljava/util/Set;
/*      */     //   1850: dup
/*      */     //   1851: astore #27
/*      */     //   1853: monitorenter
/*      */     //   1854: aload_0
/*      */     //   1855: getfield setTileEntities : Ljava/util/Set;
/*      */     //   1858: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1863: astore #29
/*      */     //   1865: goto -> 1928
/*      */     //   1868: aload #29
/*      */     //   1870: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   1875: checkcast net/minecraft/tileentity/TileEntity
/*      */     //   1878: astore #28
/*      */     //   1880: iload #20
/*      */     //   1882: ifeq -> 1908
/*      */     //   1885: aload #28
/*      */     //   1887: getstatic optifine/Reflector.ForgeTileEntity_shouldRenderInPass : Loptifine/ReflectorMethod;
/*      */     //   1890: iconst_1
/*      */     //   1891: anewarray java/lang/Object
/*      */     //   1894: dup
/*      */     //   1895: iconst_0
/*      */     //   1896: iload #4
/*      */     //   1898: invokestatic valueOf : (I)Ljava/lang/Integer;
/*      */     //   1901: aastore
/*      */     //   1902: invokestatic callBoolean : (Ljava/lang/Object;Loptifine/ReflectorMethod;[Ljava/lang/Object;)Z
/*      */     //   1905: ifeq -> 1928
/*      */     //   1908: iload #21
/*      */     //   1910: ifeq -> 1918
/*      */     //   1913: aload #28
/*      */     //   1915: invokestatic nextBlockEntity : (Lnet/minecraft/tileentity/TileEntity;)V
/*      */     //   1918: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1921: aload #28
/*      */     //   1923: fload_3
/*      */     //   1924: iconst_m1
/*      */     //   1925: invokevirtual renderTileEntity : (Lnet/minecraft/tileentity/TileEntity;FI)V
/*      */     //   1928: aload #29
/*      */     //   1930: invokeinterface hasNext : ()Z
/*      */     //   1935: ifne -> 1868
/*      */     //   1938: aload #27
/*      */     //   1940: monitorexit
/*      */     //   1941: goto -> 1948
/*      */     //   1944: aload #27
/*      */     //   1946: monitorexit
/*      */     //   1947: athrow
/*      */     //   1948: getstatic optifine/Reflector.ForgeTileEntity_hasFastRenderer : Loptifine/ReflectorMethod;
/*      */     //   1951: invokevirtual exists : ()Z
/*      */     //   1954: ifeq -> 1965
/*      */     //   1957: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   1960: iload #4
/*      */     //   1962: invokevirtual drawBatch : (I)V
/*      */     //   1965: aload_0
/*      */     //   1966: iconst_1
/*      */     //   1967: putfield renderOverlayDamaged : Z
/*      */     //   1970: aload_0
/*      */     //   1971: invokespecial preRenderDamagedBlocks : ()V
/*      */     //   1974: aload_0
/*      */     //   1975: getfield damagedBlocks : Ljava/util/Map;
/*      */     //   1978: invokeinterface values : ()Ljava/util/Collection;
/*      */     //   1983: invokeinterface iterator : ()Ljava/util/Iterator;
/*      */     //   1988: astore #28
/*      */     //   1990: goto -> 2169
/*      */     //   1993: aload #28
/*      */     //   1995: invokeinterface next : ()Ljava/lang/Object;
/*      */     //   2000: checkcast net/minecraft/client/renderer/DestroyBlockProgress
/*      */     //   2003: astore #27
/*      */     //   2005: aload #27
/*      */     //   2007: invokevirtual getPosition : ()Lnet/minecraft/util/math/BlockPos;
/*      */     //   2010: astore #29
/*      */     //   2012: aload_0
/*      */     //   2013: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   2016: aload #29
/*      */     //   2018: invokevirtual getBlockState : (Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
/*      */     //   2021: invokeinterface getBlock : ()Lnet/minecraft/block/Block;
/*      */     //   2026: invokevirtual hasTileEntity : ()Z
/*      */     //   2029: ifeq -> 2169
/*      */     //   2032: aload_0
/*      */     //   2033: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   2036: aload #29
/*      */     //   2038: invokevirtual getTileEntity : (Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
/*      */     //   2041: astore #30
/*      */     //   2043: aload #30
/*      */     //   2045: instanceof net/minecraft/tileentity/TileEntityChest
/*      */     //   2048: ifeq -> 2119
/*      */     //   2051: aload #30
/*      */     //   2053: checkcast net/minecraft/tileentity/TileEntityChest
/*      */     //   2056: astore #31
/*      */     //   2058: aload #31
/*      */     //   2060: getfield adjacentChestXNeg : Lnet/minecraft/tileentity/TileEntityChest;
/*      */     //   2063: ifnull -> 2090
/*      */     //   2066: aload #29
/*      */     //   2068: getstatic net/minecraft/util/EnumFacing.WEST : Lnet/minecraft/util/EnumFacing;
/*      */     //   2071: invokevirtual offset : (Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
/*      */     //   2074: astore #29
/*      */     //   2076: aload_0
/*      */     //   2077: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   2080: aload #29
/*      */     //   2082: invokevirtual getTileEntity : (Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
/*      */     //   2085: astore #30
/*      */     //   2087: goto -> 2119
/*      */     //   2090: aload #31
/*      */     //   2092: getfield adjacentChestZNeg : Lnet/minecraft/tileentity/TileEntityChest;
/*      */     //   2095: ifnull -> 2119
/*      */     //   2098: aload #29
/*      */     //   2100: getstatic net/minecraft/util/EnumFacing.NORTH : Lnet/minecraft/util/EnumFacing;
/*      */     //   2103: invokevirtual offset : (Lnet/minecraft/util/EnumFacing;)Lnet/minecraft/util/math/BlockPos;
/*      */     //   2106: astore #29
/*      */     //   2108: aload_0
/*      */     //   2109: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   2112: aload #29
/*      */     //   2114: invokevirtual getTileEntity : (Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/tileentity/TileEntity;
/*      */     //   2117: astore #30
/*      */     //   2119: aload_0
/*      */     //   2120: getfield theWorld : Lnet/minecraft/client/multiplayer/WorldClient;
/*      */     //   2123: aload #29
/*      */     //   2125: invokevirtual getBlockState : (Lnet/minecraft/util/math/BlockPos;)Lnet/minecraft/block/state/IBlockState;
/*      */     //   2128: astore #31
/*      */     //   2130: aload #30
/*      */     //   2132: ifnull -> 2169
/*      */     //   2135: aload #31
/*      */     //   2137: invokeinterface func_191057_i : ()Z
/*      */     //   2142: ifeq -> 2169
/*      */     //   2145: iload #21
/*      */     //   2147: ifeq -> 2155
/*      */     //   2150: aload #30
/*      */     //   2152: invokestatic nextBlockEntity : (Lnet/minecraft/tileentity/TileEntity;)V
/*      */     //   2155: getstatic net/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher.instance : Lnet/minecraft/client/renderer/tileentity/TileEntityRendererDispatcher;
/*      */     //   2158: aload #30
/*      */     //   2160: fload_3
/*      */     //   2161: aload #27
/*      */     //   2163: invokevirtual getPartialBlockDamage : ()I
/*      */     //   2166: invokevirtual renderTileEntity : (Lnet/minecraft/tileentity/TileEntity;FI)V
/*      */     //   2169: aload #28
/*      */     //   2171: invokeinterface hasNext : ()Z
/*      */     //   2176: ifne -> 1993
/*      */     //   2179: aload_0
/*      */     //   2180: invokespecial postRenderDamagedBlocks : ()V
/*      */     //   2183: aload_0
/*      */     //   2184: iconst_0
/*      */     //   2185: putfield renderOverlayDamaged : Z
/*      */     //   2188: aload_0
/*      */     //   2189: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2192: getfield entityRenderer : Lnet/minecraft/client/renderer/EntityRenderer;
/*      */     //   2195: invokevirtual disableLightmap : ()V
/*      */     //   2198: aload_0
/*      */     //   2199: getfield mc : Lnet/minecraft/client/Minecraft;
/*      */     //   2202: getfield mcProfiler : Lnet/minecraft/profiler/Profiler;
/*      */     //   2205: invokevirtual endSection : ()V
/*      */     //   2208: return
/*      */     // Line number table:
/*      */     //   Java source line number -> byte code offset
/*      */     //   #660	-> 0
/*      */     //   #662	-> 3
/*      */     //   #664	-> 12
/*      */     //   #667	-> 24
/*      */     //   #669	-> 31
/*      */     //   #671	-> 36
/*      */     //   #674	-> 37
/*      */     //   #675	-> 47
/*      */     //   #678	-> 50
/*      */     //   #679	-> 69
/*      */     //   #680	-> 88
/*      */     //   #681	-> 107
/*      */     //   #682	-> 120
/*      */     //   #683	-> 159
/*      */     //   #685	-> 199
/*      */     //   #687	-> 204
/*      */     //   #688	-> 209
/*      */     //   #689	-> 214
/*      */     //   #690	-> 219
/*      */     //   #693	-> 224
/*      */     //   #694	-> 233
/*      */     //   #695	-> 255
/*      */     //   #696	-> 277
/*      */     //   #697	-> 299
/*      */     //   #698	-> 304
/*      */     //   #699	-> 309
/*      */     //   #700	-> 314
/*      */     //   #701	-> 327
/*      */     //   #702	-> 337
/*      */     //   #703	-> 350
/*      */     //   #705	-> 359
/*      */     //   #707	-> 364
/*      */     //   #710	-> 375
/*      */     //   #712	-> 394
/*      */     //   #715	-> 397
/*      */     //   #716	-> 405
/*      */     //   #718	-> 413
/*      */     //   #720	-> 419
/*      */     //   #722	-> 438
/*      */     //   #724	-> 466
/*      */     //   #726	-> 476
/*      */     //   #728	-> 490
/*      */     //   #718	-> 501
/*      */     //   #733	-> 521
/*      */     //   #734	-> 534
/*      */     //   #736	-> 539
/*      */     //   #738	-> 544
/*      */     //   #741	-> 547
/*      */     //   #742	-> 559
/*      */     //   #743	-> 572
/*      */     //   #744	-> 577
/*      */     //   #745	-> 582
/*      */     //   #747	-> 587
/*      */     //   #749	-> 610
/*      */     //   #750	-> 617
/*      */     //   #751	-> 631
/*      */     //   #753	-> 653
/*      */     //   #755	-> 661
/*      */     //   #757	-> 683
/*      */     //   #759	-> 711
/*      */     //   #761	-> 752
/*      */     //   #763	-> 757
/*      */     //   #765	-> 789
/*      */     //   #767	-> 858
/*      */     //   #768	-> 868
/*      */     //   #770	-> 874
/*      */     //   #772	-> 879
/*      */     //   #775	-> 884
/*      */     //   #776	-> 895
/*      */     //   #778	-> 900
/*      */     //   #780	-> 912
/*      */     //   #783	-> 922
/*      */     //   #785	-> 934
/*      */     //   #755	-> 944
/*      */     //   #747	-> 954
/*      */     //   #794	-> 964
/*      */     //   #796	-> 969
/*      */     //   #798	-> 979
/*      */     //   #800	-> 1003
/*      */     //   #802	-> 1031
/*      */     //   #804	-> 1036
/*      */     //   #807	-> 1041
/*      */     //   #798	-> 1051
/*      */     //   #812	-> 1061
/*      */     //   #814	-> 1090
/*      */     //   #815	-> 1103
/*      */     //   #816	-> 1110
/*      */     //   #818	-> 1129
/*      */     //   #820	-> 1139
/*      */     //   #821	-> 1145
/*      */     //   #822	-> 1148
/*      */     //   #823	-> 1156
/*      */     //   #824	-> 1159
/*      */     //   #826	-> 1167
/*      */     //   #828	-> 1173
/*      */     //   #830	-> 1187
/*      */     //   #832	-> 1215
/*      */     //   #834	-> 1220
/*      */     //   #837	-> 1225
/*      */     //   #826	-> 1236
/*      */     //   #841	-> 1251
/*      */     //   #842	-> 1259
/*      */     //   #843	-> 1262
/*      */     //   #844	-> 1266
/*      */     //   #845	-> 1274
/*      */     //   #846	-> 1277
/*      */     //   #847	-> 1281
/*      */     //   #848	-> 1284
/*      */     //   #849	-> 1287
/*      */     //   #850	-> 1290
/*      */     //   #851	-> 1296
/*      */     //   #852	-> 1299
/*      */     //   #855	-> 1302
/*      */     //   #858	-> 1313
/*      */     //   #860	-> 1337
/*      */     //   #861	-> 1350
/*      */     //   #863	-> 1369
/*      */     //   #865	-> 1379
/*      */     //   #866	-> 1382
/*      */     //   #867	-> 1385
/*      */     //   #868	-> 1395
/*      */     //   #869	-> 1398
/*      */     //   #871	-> 1406
/*      */     //   #873	-> 1412
/*      */     //   #875	-> 1426
/*      */     //   #877	-> 1454
/*      */     //   #879	-> 1459
/*      */     //   #882	-> 1464
/*      */     //   #871	-> 1475
/*      */     //   #886	-> 1490
/*      */     //   #887	-> 1498
/*      */     //   #888	-> 1501
/*      */     //   #889	-> 1511
/*      */     //   #890	-> 1514
/*      */     //   #894	-> 1517
/*      */     //   #895	-> 1529
/*      */     //   #897	-> 1537
/*      */     //   #899	-> 1542
/*      */     //   #900	-> 1545
/*      */     //   #903	-> 1548
/*      */     //   #904	-> 1561
/*      */     //   #906	-> 1564
/*      */     //   #908	-> 1573
/*      */     //   #913	-> 1579
/*      */     //   #915	-> 1602
/*      */     //   #916	-> 1609
/*      */     //   #918	-> 1622
/*      */     //   #920	-> 1632
/*      */     //   #928	-> 1641
/*      */     //   #930	-> 1651
/*      */     //   #933	-> 1654
/*      */     //   #935	-> 1666
/*      */     //   #937	-> 1671
/*      */     //   #940	-> 1674
/*      */     //   #942	-> 1697
/*      */     //   #944	-> 1714
/*      */     //   #951	-> 1730
/*      */     //   #953	-> 1737
/*      */     //   #955	-> 1751
/*      */     //   #956	-> 1760
/*      */     //   #958	-> 1782
/*      */     //   #960	-> 1791
/*      */     //   #964	-> 1797
/*      */     //   #966	-> 1802
/*      */     //   #969	-> 1807
/*      */     //   #970	-> 1817
/*      */     //   #971	-> 1827
/*      */     //   #922	-> 1833
/*      */     //   #913	-> 1836
/*      */     //   #976	-> 1846
/*      */     //   #978	-> 1854
/*      */     //   #980	-> 1880
/*      */     //   #982	-> 1908
/*      */     //   #984	-> 1913
/*      */     //   #987	-> 1918
/*      */     //   #978	-> 1928
/*      */     //   #976	-> 1938
/*      */     //   #992	-> 1948
/*      */     //   #994	-> 1957
/*      */     //   #997	-> 1965
/*      */     //   #998	-> 1970
/*      */     //   #1000	-> 1974
/*      */     //   #1002	-> 2005
/*      */     //   #1004	-> 2012
/*      */     //   #1006	-> 2032
/*      */     //   #1008	-> 2043
/*      */     //   #1010	-> 2051
/*      */     //   #1012	-> 2058
/*      */     //   #1014	-> 2066
/*      */     //   #1015	-> 2076
/*      */     //   #1016	-> 2087
/*      */     //   #1017	-> 2090
/*      */     //   #1019	-> 2098
/*      */     //   #1020	-> 2108
/*      */     //   #1024	-> 2119
/*      */     //   #1026	-> 2130
/*      */     //   #1028	-> 2145
/*      */     //   #1030	-> 2150
/*      */     //   #1033	-> 2155
/*      */     //   #1000	-> 2169
/*      */     //   #1038	-> 2179
/*      */     //   #1039	-> 2183
/*      */     //   #1040	-> 2188
/*      */     //   #1041	-> 2198
/*      */     //   #1043	-> 2208
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	descriptor
/*      */     //   0	2209	0	this	Lnet/minecraft/client/renderer/RenderGlobal;
/*      */     //   0	2209	1	renderViewEntity	Lnet/minecraft/entity/Entity;
/*      */     //   0	2209	2	camera	Lnet/minecraft/client/renderer/culling/ICamera;
/*      */     //   0	2209	3	partialTicks	F
/*      */     //   3	2206	4	i	I
/*      */     //   69	2139	5	d0	D
/*      */     //   88	2120	7	d1	D
/*      */     //   107	2101	9	d2	D
/*      */     //   233	1975	11	entity	Lnet/minecraft/entity/Entity;
/*      */     //   255	1953	12	d3	D
/*      */     //   277	1931	14	d4	D
/*      */     //   299	1909	16	d5	D
/*      */     //   359	1849	18	list	Ljava/util/List;
/*      */     //   405	1803	19	flag	Z
/*      */     //   413	1795	20	flag1	Z
/*      */     //   416	105	21	j	I
/*      */     //   438	63	22	entity1	Lnet/minecraft/entity/Entity;
/*      */     //   539	1669	21	flag4	Z
/*      */     //   559	1649	22	flag5	Z
/*      */     //   577	1631	23	list1	Ljava/util/List;
/*      */     //   582	1626	24	list2	Ljava/util/List;
/*      */     //   587	1621	25	blockpos$pooledmutableblockpos	Lnet/minecraft/util/math/BlockPos$PooledMutableBlockPos;
/*      */     //   610	344	26	renderglobal$containerlocalrenderinformation0	Ljava/lang/Object;
/*      */     //   617	337	28	renderglobal$containerlocalrenderinformation	Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
/*      */     //   631	323	29	chunk	Lnet/minecraft/world/chunk/Chunk;
/*      */     //   653	301	30	classinheritancemultimap	Lnet/minecraft/util/ClassInheritanceMultiMap;
/*      */     //   683	261	31	entity2	Lnet/minecraft/entity/Entity;
/*      */     //   752	192	33	flag2	Z
/*      */     //   789	155	34	flag3	Z
/*      */     //   1003	48	26	entity3	Lnet/minecraft/entity/Entity;
/*      */     //   1170	81	26	k	I
/*      */     //   1187	49	27	entity4	Lnet/minecraft/entity/Entity;
/*      */     //   1409	81	26	l	I
/*      */     //   1426	49	27	entity5	Lnet/minecraft/entity/Entity;
/*      */     //   1537	671	26	fontrenderer	Lnet/minecraft/client/gui/FontRenderer;
/*      */     //   1602	234	27	renderglobal$containerlocalrenderinformation10	Ljava/lang/Object;
/*      */     //   1609	227	29	renderglobal$containerlocalrenderinformation1	Lnet/minecraft/client/renderer/RenderGlobal$ContainerLocalRenderInformation;
/*      */     //   1622	214	30	list3	Ljava/util/List;
/*      */     //   1641	195	31	iterator	Ljava/util/Iterator;
/*      */     //   1666	167	32	tileentity1	Lnet/minecraft/tileentity/TileEntity;
/*      */     //   1714	16	33	axisalignedbb	Lnet/minecraft/util/math/AxisAlignedBB;
/*      */     //   1737	96	33	oclass	Ljava/lang/Class;
/*      */     //   1760	37	34	entityplayer	Lnet/minecraft/entity/player/EntityPlayer;
/*      */     //   1782	15	35	d6	D
/*      */     //   1880	48	28	tileentity	Lnet/minecraft/tileentity/TileEntity;
/*      */     //   2005	164	27	destroyblockprogress	Lnet/minecraft/client/renderer/DestroyBlockProgress;
/*      */     //   2012	157	29	blockpos	Lnet/minecraft/util/math/BlockPos;
/*      */     //   2043	126	30	tileentity2	Lnet/minecraft/tileentity/TileEntity;
/*      */     //   2058	61	31	tileentitychest	Lnet/minecraft/tileentity/TileEntityChest;
/*      */     //   2130	39	31	iblockstate	Lnet/minecraft/block/state/IBlockState;
/*      */     // Local variable type table:
/*      */     //   start	length	slot	name	signature
/*      */     //   359	1849	18	list	Ljava/util/List<Lnet/minecraft/entity/Entity;>;
/*      */     //   577	1631	23	list1	Ljava/util/List<Lnet/minecraft/entity/Entity;>;
/*      */     //   582	1626	24	list2	Ljava/util/List<Lnet/minecraft/entity/Entity;>;
/*      */     //   653	301	30	classinheritancemultimap	Lnet/minecraft/util/ClassInheritanceMultiMap<Lnet/minecraft/entity/Entity;>;
/*      */     //   1622	214	30	list3	Ljava/util/List<Lnet/minecraft/tileentity/TileEntity;>;
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   1854	1941	1944	finally
/*      */     //   1944	1947	1944	finally
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
/*      */   private boolean isOutlineActive(Entity p_184383_1_, Entity p_184383_2_, ICamera p_184383_3_) {
/* 1047 */     boolean flag = (p_184383_2_ instanceof EntityLivingBase && ((EntityLivingBase)p_184383_2_).isPlayerSleeping());
/*      */     
/* 1049 */     if (p_184383_1_ == p_184383_2_ && this.mc.gameSettings.thirdPersonView == 0 && !flag)
/*      */     {
/* 1051 */       return false;
/*      */     }
/* 1053 */     if (p_184383_1_.isGlowing())
/*      */     {
/* 1055 */       return true;
/*      */     }
/* 1057 */     if (this.mc.player.isSpectator() && this.mc.gameSettings.keyBindSpectatorOutlines.isKeyDown() && p_184383_1_ instanceof EntityPlayer)
/*      */     {
/* 1059 */       return !(!p_184383_1_.ignoreFrustumCheck && !p_184383_3_.isBoundingBoxInFrustum(p_184383_1_.getEntityBoundingBox()) && !p_184383_1_.isRidingOrBeingRiddenBy((Entity)this.mc.player));
/*      */     }
/*      */ 
/*      */     
/* 1063 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDebugInfoRenders() {
/* 1072 */     int i = this.viewFrustum.renderChunks.length;
/* 1073 */     int j = getRenderedChunks();
/* 1074 */     return String.format("C: %d/%d %sD: %d, L: %d, %s", new Object[] { Integer.valueOf(j), Integer.valueOf(i), this.mc.renderChunksMany ? "(s) " : "", Integer.valueOf(this.renderDistanceChunks), Integer.valueOf(this.setLightUpdates.size()), (this.renderDispatcher == null) ? "null" : this.renderDispatcher.getDebugInfo() });
/*      */   }
/*      */ 
/*      */   
/*      */   protected int getRenderedChunks() {
/* 1079 */     int i = 0;
/*      */     
/* 1081 */     for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
/*      */       
/* 1083 */       CompiledChunk compiledchunk = renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk;
/*      */       
/* 1085 */       if (compiledchunk != CompiledChunk.DUMMY && !compiledchunk.isEmpty())
/*      */       {
/* 1087 */         i++;
/*      */       }
/*      */     } 
/*      */     
/* 1091 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDebugInfoEntities() {
/* 1099 */     return "E: " + this.countEntitiesRendered + "/" + this.countEntitiesTotal + ", B: " + this.countEntitiesHidden + ", " + Config.getVersionDebug();
/*      */   }
/*      */   
/*      */   public void setupTerrain(Entity viewEntity, double partialTicks, ICamera camera, int frameCount, boolean playerSpectator) {
/*      */     Frustum frustum;
/* 1104 */     if (this.mc.gameSettings.renderDistanceChunks != this.renderDistanceChunks)
/*      */     {
/* 1106 */       loadRenderers();
/*      */     }
/*      */     
/* 1109 */     this.theWorld.theProfiler.startSection("camera");
/* 1110 */     double d0 = viewEntity.posX - this.frustumUpdatePosX;
/* 1111 */     double d1 = viewEntity.posY - this.frustumUpdatePosY;
/* 1112 */     double d2 = viewEntity.posZ - this.frustumUpdatePosZ;
/*      */     
/* 1114 */     if (this.frustumUpdatePosChunkX != viewEntity.chunkCoordX || this.frustumUpdatePosChunkY != viewEntity.chunkCoordY || this.frustumUpdatePosChunkZ != viewEntity.chunkCoordZ || d0 * d0 + d1 * d1 + d2 * d2 > 16.0D) {
/*      */       
/* 1116 */       this.frustumUpdatePosX = viewEntity.posX;
/* 1117 */       this.frustumUpdatePosY = viewEntity.posY;
/* 1118 */       this.frustumUpdatePosZ = viewEntity.posZ;
/* 1119 */       this.frustumUpdatePosChunkX = viewEntity.chunkCoordX;
/* 1120 */       this.frustumUpdatePosChunkY = viewEntity.chunkCoordY;
/* 1121 */       this.frustumUpdatePosChunkZ = viewEntity.chunkCoordZ;
/* 1122 */       this.viewFrustum.updateChunkPositions(viewEntity.posX, viewEntity.posZ);
/*      */     } 
/*      */     
/* 1125 */     if (Config.isDynamicLights())
/*      */     {
/* 1127 */       DynamicLights.update(this);
/*      */     }
/*      */     
/* 1130 */     this.theWorld.theProfiler.endStartSection("renderlistcamera");
/* 1131 */     double d3 = viewEntity.lastTickPosX + (viewEntity.posX - viewEntity.lastTickPosX) * partialTicks;
/* 1132 */     double d4 = viewEntity.lastTickPosY + (viewEntity.posY - viewEntity.lastTickPosY) * partialTicks;
/* 1133 */     double d5 = viewEntity.lastTickPosZ + (viewEntity.posZ - viewEntity.lastTickPosZ) * partialTicks;
/* 1134 */     this.renderContainer.initialize(d3, d4, d5);
/* 1135 */     this.theWorld.theProfiler.endStartSection("cull");
/*      */     
/* 1137 */     if (this.debugFixedClippingHelper != null) {
/*      */       
/* 1139 */       Frustum frustum1 = new Frustum(this.debugFixedClippingHelper);
/* 1140 */       frustum1.setPosition(this.debugTerrainFrustumPosition.x, this.debugTerrainFrustumPosition.y, this.debugTerrainFrustumPosition.z);
/* 1141 */       frustum = frustum1;
/*      */     } 
/*      */     
/* 1144 */     this.mc.mcProfiler.endStartSection("culling");
/* 1145 */     BlockPos blockpos1 = new BlockPos(d3, d4 + viewEntity.getEyeHeight(), d5);
/* 1146 */     RenderChunk renderchunk = this.viewFrustum.getRenderChunk(blockpos1);
/*      */     
/* 1148 */     this.displayListEntitiesDirty = !(!this.displayListEntitiesDirty && this.chunksToUpdate.isEmpty() && viewEntity.posX == this.lastViewEntityX && viewEntity.posY == this.lastViewEntityY && viewEntity.posZ == this.lastViewEntityZ && viewEntity.rotationPitch == this.lastViewEntityPitch && viewEntity.rotationYaw == this.lastViewEntityYaw);
/* 1149 */     this.lastViewEntityX = viewEntity.posX;
/* 1150 */     this.lastViewEntityY = viewEntity.posY;
/* 1151 */     this.lastViewEntityZ = viewEntity.posZ;
/* 1152 */     this.lastViewEntityPitch = viewEntity.rotationPitch;
/* 1153 */     this.lastViewEntityYaw = viewEntity.rotationYaw;
/* 1154 */     boolean flag = (this.debugFixedClippingHelper != null);
/* 1155 */     this.mc.mcProfiler.endStartSection("update");
/* 1156 */     Lagometer.timerVisibility.start();
/* 1157 */     int i = getCountLoadedChunks();
/*      */     
/* 1159 */     if (i != this.countLoadedChunksPrev) {
/*      */       
/* 1161 */       this.countLoadedChunksPrev = i;
/* 1162 */       this.displayListEntitiesDirty = true;
/*      */     } 
/*      */     
/* 1165 */     if (Shaders.isShadowPass) {
/*      */       
/* 1167 */       this.renderInfos = this.renderInfosShadow;
/* 1168 */       this.renderInfosEntities = this.renderInfosEntitiesShadow;
/* 1169 */       this.renderInfosTileEntities = this.renderInfosTileEntitiesShadow;
/*      */       
/* 1171 */       if (!flag && this.displayListEntitiesDirty) {
/*      */         
/* 1173 */         this.renderInfos.clear();
/* 1174 */         this.renderInfosEntities.clear();
/* 1175 */         this.renderInfosTileEntities.clear();
/* 1176 */         RenderInfoLazy renderinfolazy = new RenderInfoLazy();
/* 1177 */         Iterator<RenderChunk> iterator = ShadowUtils.makeShadowChunkIterator(this.theWorld, partialTicks, viewEntity, this.renderDistanceChunks, this.viewFrustum);
/*      */         
/* 1179 */         while (iterator.hasNext()) {
/*      */           
/* 1181 */           RenderChunk renderchunk1 = iterator.next();
/*      */           
/* 1183 */           if (renderchunk1 != null)
/*      */           {
/* 1185 */             renderinfolazy.setRenderChunk(renderchunk1);
/*      */             
/* 1187 */             if (!renderchunk1.compiledChunk.isEmpty() || renderchunk1.isNeedsUpdate())
/*      */             {
/* 1189 */               this.renderInfos.add(renderinfolazy.getRenderInfo());
/*      */             }
/*      */             
/* 1192 */             BlockPos blockpos = renderchunk1.getPosition();
/*      */             
/* 1194 */             if (ChunkUtils.hasEntities(this.theWorld.getChunkFromBlockCoords(blockpos)))
/*      */             {
/* 1196 */               this.renderInfosEntities.add(renderinfolazy.getRenderInfo());
/*      */             }
/*      */             
/* 1199 */             if (renderchunk1.getCompiledChunk().getTileEntities().size() > 0)
/*      */             {
/* 1201 */               this.renderInfosTileEntities.add(renderinfolazy.getRenderInfo());
/*      */             }
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/*      */     } else {
/*      */       
/* 1209 */       this.renderInfos = this.renderInfosNormal;
/* 1210 */       this.renderInfosEntities = this.renderInfosEntitiesNormal;
/* 1211 */       this.renderInfosTileEntities = this.renderInfosTileEntitiesNormal;
/*      */     } 
/*      */     
/* 1214 */     if (!flag && this.displayListEntitiesDirty && !Shaders.isShadowPass) {
/*      */       
/* 1216 */       this.displayListEntitiesDirty = false;
/*      */       
/* 1218 */       for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1 : this.renderInfos)
/*      */       {
/* 1220 */         freeRenderInformation(renderglobal$containerlocalrenderinformation1);
/*      */       }
/*      */       
/* 1223 */       this.renderInfos.clear();
/* 1224 */       this.renderInfosEntities.clear();
/* 1225 */       this.renderInfosTileEntities.clear();
/* 1226 */       this.visibilityDeque.clear();
/* 1227 */       Deque<ContainerLocalRenderInformation> deque = this.visibilityDeque;
/* 1228 */       Entity.setRenderDistanceWeight(MathHelper.clamp(this.mc.gameSettings.renderDistanceChunks / 8.0D, 1.0D, 2.5D));
/* 1229 */       boolean flag2 = this.mc.renderChunksMany;
/*      */       
/* 1231 */       if (renderchunk != null) {
/*      */         
/* 1233 */         boolean flag3 = false;
/* 1234 */         ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation3 = new ContainerLocalRenderInformation(renderchunk, null, 0);
/* 1235 */         Set set1 = SET_ALL_FACINGS;
/*      */         
/* 1237 */         if (set1.size() == 1) {
/*      */           
/* 1239 */           Vector3f vector3f = getViewVector(viewEntity, partialTicks);
/* 1240 */           EnumFacing enumfacing = EnumFacing.getFacingFromVector(vector3f.x, vector3f.y, vector3f.z).getOpposite();
/* 1241 */           set1.remove(enumfacing);
/*      */         } 
/*      */         
/* 1244 */         if (set1.isEmpty())
/*      */         {
/* 1246 */           flag3 = true;
/*      */         }
/*      */         
/* 1249 */         if (flag3 && !playerSpectator)
/*      */         {
/* 1251 */           this.renderInfos.add(renderglobal$containerlocalrenderinformation3);
/*      */         }
/*      */         else
/*      */         {
/* 1255 */           if (playerSpectator && this.theWorld.getBlockState(blockpos1).isOpaqueCube())
/*      */           {
/* 1257 */             flag2 = false;
/*      */           }
/*      */           
/* 1260 */           renderchunk.setFrameIndex(frameCount);
/* 1261 */           deque.add(renderglobal$containerlocalrenderinformation3);
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1266 */         int i1 = (blockpos1.getY() > 0) ? 248 : 8;
/*      */         
/* 1268 */         for (int j1 = -this.renderDistanceChunks; j1 <= this.renderDistanceChunks; j1++) {
/*      */           
/* 1270 */           for (int j = -this.renderDistanceChunks; j <= this.renderDistanceChunks; j++) {
/*      */             
/* 1272 */             RenderChunk renderchunk2 = this.viewFrustum.getRenderChunk(new BlockPos((j1 << 4) + 8, i1, (j << 4) + 8));
/*      */             
/* 1274 */             if (renderchunk2 != null && frustum.isBoundingBoxInFrustum(renderchunk2.boundingBox)) {
/*      */               
/* 1276 */               renderchunk2.setFrameIndex(frameCount);
/* 1277 */               deque.add(new ContainerLocalRenderInformation(renderchunk2, null, 0));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1283 */       this.mc.mcProfiler.startSection("iteration");
/* 1284 */       EnumFacing[] aenumfacing = EnumFacing.VALUES;
/* 1285 */       int k1 = aenumfacing.length;
/*      */       
/* 1287 */       while (!deque.isEmpty()) {
/*      */         
/* 1289 */         ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation4 = deque.poll();
/* 1290 */         RenderChunk renderchunk5 = renderglobal$containerlocalrenderinformation4.renderChunk;
/* 1291 */         EnumFacing enumfacing2 = renderglobal$containerlocalrenderinformation4.facing;
/* 1292 */         boolean flag1 = false;
/* 1293 */         CompiledChunk compiledchunk = renderchunk5.compiledChunk;
/*      */         
/* 1295 */         if (!compiledchunk.isEmpty() || renderchunk5.isNeedsUpdate()) {
/*      */           
/* 1297 */           this.renderInfos.add(renderglobal$containerlocalrenderinformation4);
/* 1298 */           flag1 = true;
/*      */         } 
/*      */         
/* 1301 */         if (ChunkUtils.hasEntities(renderchunk5.getChunk((World)this.theWorld))) {
/*      */           
/* 1303 */           this.renderInfosEntities.add(renderglobal$containerlocalrenderinformation4);
/* 1304 */           flag1 = true;
/*      */         } 
/*      */         
/* 1307 */         if (compiledchunk.getTileEntities().size() > 0) {
/*      */           
/* 1309 */           this.renderInfosTileEntities.add(renderglobal$containerlocalrenderinformation4);
/* 1310 */           flag1 = true;
/*      */         } 
/*      */         
/* 1313 */         for (int k = 0; k < k1; k++) {
/*      */           
/* 1315 */           EnumFacing enumfacing1 = aenumfacing[k];
/*      */           
/* 1317 */           if ((!flag2 || !renderglobal$containerlocalrenderinformation4.hasDirection(enumfacing1.getOpposite())) && (!flag2 || enumfacing2 == null || compiledchunk.isVisible(enumfacing2.getOpposite(), enumfacing1))) {
/*      */             
/* 1319 */             RenderChunk renderchunk3 = getRenderChunkOffset(blockpos1, renderchunk5, enumfacing1);
/*      */             
/* 1321 */             if (renderchunk3 != null && renderchunk3.setFrameIndex(frameCount) && frustum.isBoundingBoxInFrustum(renderchunk3.boundingBox)) {
/*      */               
/* 1323 */               int l = renderglobal$containerlocalrenderinformation4.setFacing | 1 << enumfacing1.ordinal();
/* 1324 */               ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation = allocateRenderInformation(renderchunk3, enumfacing1, l);
/* 1325 */               deque.add(renderglobal$containerlocalrenderinformation);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/* 1330 */         if (!flag1)
/*      */         {
/* 1332 */           freeRenderInformation(renderglobal$containerlocalrenderinformation4);
/*      */         }
/*      */       } 
/*      */       
/* 1336 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1339 */     this.mc.mcProfiler.endStartSection("captureFrustum");
/*      */     
/* 1341 */     if (this.debugFixTerrainFrustum) {
/*      */       
/* 1343 */       fixTerrainFrustum(d3, d4, d5);
/* 1344 */       this.debugFixTerrainFrustum = false;
/*      */     } 
/*      */     
/* 1347 */     Lagometer.timerVisibility.end();
/*      */     
/* 1349 */     if (Shaders.isShadowPass) {
/*      */       
/* 1351 */       Shaders.mcProfilerEndSection();
/*      */     }
/*      */     else {
/*      */       
/* 1355 */       this.mc.mcProfiler.endStartSection("rebuildNear");
/* 1356 */       Set<RenderChunk> set = this.chunksToUpdate;
/* 1357 */       this.chunksToUpdate = Sets.newLinkedHashSet();
/* 1358 */       Lagometer.timerChunkUpdate.start();
/*      */       
/* 1360 */       for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation2 : this.renderInfos) {
/*      */         
/* 1362 */         RenderChunk renderchunk4 = renderglobal$containerlocalrenderinformation2.renderChunk;
/*      */         
/* 1364 */         if (renderchunk4.isNeedsUpdate() || set.contains(renderchunk4)) {
/*      */           
/* 1366 */           this.displayListEntitiesDirty = true;
/* 1367 */           BlockPos blockpos2 = renderchunk4.getPosition();
/* 1368 */           boolean flag4 = (blockpos1.distanceSq((blockpos2.getX() + 8), (blockpos2.getY() + 8), (blockpos2.getZ() + 8)) < 768.0D);
/*      */           
/* 1370 */           if (!flag4) {
/*      */             
/* 1372 */             this.chunksToUpdate.add(renderchunk4); continue;
/*      */           } 
/* 1374 */           if (!renderchunk4.isPlayerUpdate()) {
/*      */             
/* 1376 */             this.chunksToUpdateForced.add(renderchunk4);
/*      */             
/*      */             continue;
/*      */           } 
/* 1380 */           this.mc.mcProfiler.startSection("build near");
/* 1381 */           this.renderDispatcher.updateChunkNow(renderchunk4);
/* 1382 */           renderchunk4.clearNeedsUpdate();
/* 1383 */           this.mc.mcProfiler.endSection();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1388 */       Lagometer.timerChunkUpdate.end();
/* 1389 */       this.chunksToUpdate.addAll(set);
/* 1390 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Set<EnumFacing> getVisibleFacings(BlockPos pos) {
/* 1396 */     VisGraph visgraph = new VisGraph();
/* 1397 */     BlockPos blockpos = new BlockPos(pos.getX() >> 4 << 4, pos.getY() >> 4 << 4, pos.getZ() >> 4 << 4);
/* 1398 */     Chunk chunk = this.theWorld.getChunkFromBlockCoords(blockpos);
/*      */     
/* 1400 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(blockpos, blockpos.add(15, 15, 15))) {
/*      */       
/* 1402 */       if (chunk.getBlockState((BlockPos)blockpos$mutableblockpos).isOpaqueCube())
/*      */       {
/* 1404 */         visgraph.setOpaqueCube((BlockPos)blockpos$mutableblockpos);
/*      */       }
/*      */     } 
/* 1407 */     return visgraph.getVisibleFacings(pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private RenderChunk getRenderChunkOffset(BlockPos playerPos, RenderChunk renderChunkBase, EnumFacing facing) {
/* 1418 */     BlockPos blockpos = renderChunkBase.getBlockPosOffset16(facing);
/*      */     
/* 1420 */     if (blockpos.getY() >= 0 && blockpos.getY() < 256) {
/*      */       
/* 1422 */       int i = playerPos.getX() - blockpos.getX();
/* 1423 */       int j = playerPos.getZ() - blockpos.getZ();
/*      */       
/* 1425 */       if (Config.isFogOff()) {
/*      */         
/* 1427 */         if (Math.abs(i) > this.renderDistance || Math.abs(j) > this.renderDistance)
/*      */         {
/* 1429 */           return null;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1434 */         int k = i * i + j * j;
/*      */         
/* 1436 */         if (k > this.renderDistanceSq)
/*      */         {
/* 1438 */           return null;
/*      */         }
/*      */       } 
/*      */       
/* 1442 */       return renderChunkBase.getRenderChunkOffset16(this.viewFrustum, facing);
/*      */     } 
/*      */ 
/*      */     
/* 1446 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void fixTerrainFrustum(double x, double y, double z) {
/* 1452 */     this.debugFixedClippingHelper = (ClippingHelper)new ClippingHelperImpl();
/* 1453 */     ((ClippingHelperImpl)this.debugFixedClippingHelper).init();
/* 1454 */     Matrix4f matrix4f = new Matrix4f(this.debugFixedClippingHelper.modelviewMatrix);
/* 1455 */     matrix4f.transpose();
/* 1456 */     Matrix4f matrix4f1 = new Matrix4f(this.debugFixedClippingHelper.projectionMatrix);
/* 1457 */     matrix4f1.transpose();
/* 1458 */     Matrix4f matrix4f2 = new Matrix4f();
/* 1459 */     Matrix4f.mul(matrix4f1, matrix4f, matrix4f2);
/* 1460 */     matrix4f2.invert();
/* 1461 */     this.debugTerrainFrustumPosition.x = x;
/* 1462 */     this.debugTerrainFrustumPosition.y = y;
/* 1463 */     this.debugTerrainFrustumPosition.z = z;
/* 1464 */     this.debugTerrainMatrix[0] = new Vector4f(-1.0F, -1.0F, -1.0F, 1.0F);
/* 1465 */     this.debugTerrainMatrix[1] = new Vector4f(1.0F, -1.0F, -1.0F, 1.0F);
/* 1466 */     this.debugTerrainMatrix[2] = new Vector4f(1.0F, 1.0F, -1.0F, 1.0F);
/* 1467 */     this.debugTerrainMatrix[3] = new Vector4f(-1.0F, 1.0F, -1.0F, 1.0F);
/* 1468 */     this.debugTerrainMatrix[4] = new Vector4f(-1.0F, -1.0F, 1.0F, 1.0F);
/* 1469 */     this.debugTerrainMatrix[5] = new Vector4f(1.0F, -1.0F, 1.0F, 1.0F);
/* 1470 */     this.debugTerrainMatrix[6] = new Vector4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 1471 */     this.debugTerrainMatrix[7] = new Vector4f(-1.0F, 1.0F, 1.0F, 1.0F);
/*      */     
/* 1473 */     for (int i = 0; i < 8; i++) {
/*      */       
/* 1475 */       Matrix4f.transform(matrix4f2, this.debugTerrainMatrix[i], this.debugTerrainMatrix[i]);
/* 1476 */       (this.debugTerrainMatrix[i]).x /= (this.debugTerrainMatrix[i]).w;
/* 1477 */       (this.debugTerrainMatrix[i]).y /= (this.debugTerrainMatrix[i]).w;
/* 1478 */       (this.debugTerrainMatrix[i]).z /= (this.debugTerrainMatrix[i]).w;
/* 1479 */       (this.debugTerrainMatrix[i]).w = 1.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected Vector3f getViewVector(Entity entityIn, double partialTicks) {
/* 1485 */     float f = (float)(entityIn.prevRotationPitch + (entityIn.rotationPitch - entityIn.prevRotationPitch) * partialTicks);
/* 1486 */     float f1 = (float)(entityIn.prevRotationYaw + (entityIn.rotationYaw - entityIn.prevRotationYaw) * partialTicks);
/*      */     
/* 1488 */     if ((Minecraft.getMinecraft()).gameSettings.thirdPersonView == 2)
/*      */     {
/* 1490 */       f += 180.0F;
/*      */     }
/*      */     
/* 1493 */     float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
/* 1494 */     float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
/* 1495 */     float f4 = -MathHelper.cos(-f * 0.017453292F);
/* 1496 */     float f5 = MathHelper.sin(-f * 0.017453292F);
/* 1497 */     return new Vector3f(f3 * f4, f5, f2 * f4);
/*      */   }
/*      */ 
/*      */   
/*      */   public int renderBlockLayer(BlockRenderLayer blockLayerIn, double partialTicks, int pass, Entity entityIn) {
/* 1502 */     RenderHelper.disableStandardItemLighting();
/*      */     
/* 1504 */     if (blockLayerIn == BlockRenderLayer.TRANSLUCENT) {
/*      */       
/* 1506 */       this.mc.mcProfiler.startSection("translucent_sort");
/* 1507 */       double d0 = entityIn.posX - this.prevRenderSortX;
/* 1508 */       double d1 = entityIn.posY - this.prevRenderSortY;
/* 1509 */       double d2 = entityIn.posZ - this.prevRenderSortZ;
/*      */       
/* 1511 */       if (d0 * d0 + d1 * d1 + d2 * d2 > 1.0D) {
/*      */         
/* 1513 */         this.prevRenderSortX = entityIn.posX;
/* 1514 */         this.prevRenderSortY = entityIn.posY;
/* 1515 */         this.prevRenderSortZ = entityIn.posZ;
/* 1516 */         int k = 0;
/* 1517 */         this.chunksToResortTransparency.clear();
/*      */         
/* 1519 */         for (ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation : this.renderInfos) {
/*      */           
/* 1521 */           if (renderglobal$containerlocalrenderinformation.renderChunk.compiledChunk.isLayerStarted(blockLayerIn) && k++ < 15)
/*      */           {
/* 1523 */             this.chunksToResortTransparency.add(renderglobal$containerlocalrenderinformation.renderChunk);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/* 1528 */       this.mc.mcProfiler.endSection();
/*      */     } 
/*      */     
/* 1531 */     this.mc.mcProfiler.startSection("filterempty");
/* 1532 */     int l = 0;
/* 1533 */     boolean flag = (blockLayerIn == BlockRenderLayer.TRANSLUCENT);
/* 1534 */     int i1 = flag ? (this.renderInfos.size() - 1) : 0;
/* 1535 */     int i = flag ? -1 : this.renderInfos.size();
/* 1536 */     int j1 = flag ? -1 : 1;
/*      */     
/* 1538 */     for (int j = i1; j != i; j += j1) {
/*      */       
/* 1540 */       RenderChunk renderchunk = ((ContainerLocalRenderInformation)this.renderInfos.get(j)).renderChunk;
/*      */       
/* 1542 */       if (!renderchunk.getCompiledChunk().isLayerEmpty(blockLayerIn)) {
/*      */         
/* 1544 */         l++;
/* 1545 */         this.renderContainer.addRenderChunk(renderchunk, blockLayerIn);
/*      */       } 
/*      */     } 
/*      */     
/* 1549 */     if (l == 0) {
/*      */       
/* 1551 */       this.mc.mcProfiler.endSection();
/* 1552 */       return l;
/*      */     } 
/*      */ 
/*      */     
/* 1556 */     if (Config.isFogOff() && this.mc.entityRenderer.fogStandard)
/*      */     {
/* 1558 */       GlStateManager.disableFog();
/*      */     }
/*      */     
/* 1561 */     this.mc.mcProfiler.endStartSection("render_" + blockLayerIn);
/* 1562 */     renderBlockLayer(blockLayerIn);
/* 1563 */     this.mc.mcProfiler.endSection();
/* 1564 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void renderBlockLayer(BlockRenderLayer blockLayerIn) {
/* 1570 */     this.mc.entityRenderer.enableLightmap();
/*      */     
/* 1572 */     if (OpenGlHelper.useVbo()) {
/*      */       
/* 1574 */       GlStateManager.glEnableClientState(32884);
/* 1575 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 1576 */       GlStateManager.glEnableClientState(32888);
/* 1577 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
/* 1578 */       GlStateManager.glEnableClientState(32888);
/* 1579 */       OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/* 1580 */       GlStateManager.glEnableClientState(32886);
/*      */     } 
/*      */     
/* 1583 */     if (Config.isShaders())
/*      */     {
/* 1585 */       ShadersRender.preRenderChunkLayer(blockLayerIn);
/*      */     }
/*      */     
/* 1588 */     this.renderContainer.renderChunkLayer(blockLayerIn);
/*      */     
/* 1590 */     if (Config.isShaders())
/*      */     {
/* 1592 */       ShadersRender.postRenderChunkLayer(blockLayerIn);
/*      */     }
/*      */     
/* 1595 */     if (OpenGlHelper.useVbo())
/*      */     {
/* 1597 */       for (VertexFormatElement vertexformatelement : DefaultVertexFormats.BLOCK.getElements()) {
/*      */         
/* 1599 */         VertexFormatElement.EnumUsage vertexformatelement$enumusage = vertexformatelement.getUsage();
/* 1600 */         int k1 = vertexformatelement.getIndex();
/*      */         
/* 1602 */         switch (vertexformatelement$enumusage) {
/*      */           
/*      */           case POSITION:
/* 1605 */             GlStateManager.glDisableClientState(32884);
/*      */ 
/*      */           
/*      */           case UV:
/* 1609 */             OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit + k1);
/* 1610 */             GlStateManager.glDisableClientState(32888);
/* 1611 */             OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
/*      */ 
/*      */           
/*      */           case COLOR:
/* 1615 */             GlStateManager.glDisableClientState(32886);
/* 1616 */             GlStateManager.resetColor();
/*      */         } 
/*      */       
/*      */       } 
/*      */     }
/* 1621 */     this.mc.entityRenderer.disableLightmap();
/*      */   }
/*      */ 
/*      */   
/*      */   private void cleanupDamagedBlocks(Iterator<DestroyBlockProgress> iteratorIn) {
/* 1626 */     while (iteratorIn.hasNext()) {
/*      */       
/* 1628 */       DestroyBlockProgress destroyblockprogress = iteratorIn.next();
/* 1629 */       int k1 = destroyblockprogress.getCreationCloudUpdateTick();
/*      */       
/* 1631 */       if (this.cloudTickCounter - k1 > 400)
/*      */       {
/* 1633 */         iteratorIn.remove();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateClouds() {
/* 1640 */     if (Config.isShaders() && Keyboard.isKeyDown(61) && Keyboard.isKeyDown(19)) {
/*      */       
/* 1642 */       Shaders.uninit();
/* 1643 */       Shaders.loadShaderPack();
/* 1644 */       Reflector.Minecraft_actionKeyF3.setValue(this.mc, Boolean.TRUE);
/*      */     } 
/*      */     
/* 1647 */     this.cloudTickCounter++;
/*      */     
/* 1649 */     if (this.cloudTickCounter % 20 == 0)
/*      */     {
/* 1651 */       cleanupDamagedBlocks(this.damagedBlocks.values().iterator());
/*      */     }
/*      */     
/* 1654 */     if (!this.setLightUpdates.isEmpty() && !this.renderDispatcher.hasNoFreeRenderBuilders() && this.chunksToUpdate.isEmpty()) {
/*      */       
/* 1656 */       Iterator<BlockPos> iterator = this.setLightUpdates.iterator();
/*      */       
/* 1658 */       while (iterator.hasNext()) {
/*      */         
/* 1660 */         BlockPos blockpos = iterator.next();
/* 1661 */         iterator.remove();
/* 1662 */         int k1 = blockpos.getX();
/* 1663 */         int l1 = blockpos.getY();
/* 1664 */         int i2 = blockpos.getZ();
/* 1665 */         markBlocksForUpdate(k1 - 1, l1 - 1, i2 - 1, k1 + 1, l1 + 1, i2 + 1, false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderSkyEnd() {
/* 1672 */     if (Config.isSkyEnabled()) {
/*      */       
/* 1674 */       GlStateManager.disableFog();
/* 1675 */       GlStateManager.disableAlpha();
/* 1676 */       GlStateManager.enableBlend();
/* 1677 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 1678 */       RenderHelper.disableStandardItemLighting();
/* 1679 */       GlStateManager.depthMask(false);
/* 1680 */       this.renderEngine.bindTexture(END_SKY_TEXTURES);
/* 1681 */       Tessellator tessellator = Tessellator.getInstance();
/* 1682 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/*      */       
/* 1684 */       for (int k1 = 0; k1 < 6; k1++) {
/*      */         
/* 1686 */         GlStateManager.pushMatrix();
/*      */         
/* 1688 */         if (k1 == 1)
/*      */         {
/* 1690 */           GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         
/* 1693 */         if (k1 == 2)
/*      */         {
/* 1695 */           GlStateManager.rotate(-90.0F, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         
/* 1698 */         if (k1 == 3)
/*      */         {
/* 1700 */           GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
/*      */         }
/*      */         
/* 1703 */         if (k1 == 4)
/*      */         {
/* 1705 */           GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/*      */         }
/*      */         
/* 1708 */         if (k1 == 5)
/*      */         {
/* 1710 */           GlStateManager.rotate(-90.0F, 0.0F, 0.0F, 1.0F);
/*      */         }
/*      */         
/* 1713 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 1714 */         int l1 = 40;
/* 1715 */         int i2 = 40;
/* 1716 */         int j2 = 40;
/*      */         
/* 1718 */         if (Config.isCustomColors()) {
/*      */           
/* 1720 */           Vec3d vec3d = new Vec3d(l1 / 255.0D, i2 / 255.0D, j2 / 255.0D);
/* 1721 */           vec3d = CustomColors.getWorldSkyColor(vec3d, (World)this.theWorld, this.mc.getRenderViewEntity(), 0.0F);
/* 1722 */           l1 = (int)(vec3d.xCoord * 255.0D);
/* 1723 */           i2 = (int)(vec3d.yCoord * 255.0D);
/* 1724 */           j2 = (int)(vec3d.zCoord * 255.0D);
/*      */         } 
/*      */         
/* 1727 */         bufferbuilder.pos(-100.0D, -100.0D, -100.0D).tex(0.0D, 0.0D).color(l1, i2, j2, 255).endVertex();
/* 1728 */         bufferbuilder.pos(-100.0D, -100.0D, 100.0D).tex(0.0D, 16.0D).color(l1, i2, j2, 255).endVertex();
/* 1729 */         bufferbuilder.pos(100.0D, -100.0D, 100.0D).tex(16.0D, 16.0D).color(l1, i2, j2, 255).endVertex();
/* 1730 */         bufferbuilder.pos(100.0D, -100.0D, -100.0D).tex(16.0D, 0.0D).color(l1, i2, j2, 255).endVertex();
/* 1731 */         tessellator.draw();
/* 1732 */         GlStateManager.popMatrix();
/*      */       } 
/*      */       
/* 1735 */       GlStateManager.depthMask(true);
/* 1736 */       GlStateManager.enableTexture2D();
/* 1737 */       GlStateManager.enableAlpha();
/* 1738 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderSky(float partialTicks, int pass) {
/* 1744 */     if (Reflector.ForgeWorldProvider_getSkyRenderer.exists()) {
/*      */       
/* 1746 */       WorldProvider worldprovider = this.mc.world.provider;
/* 1747 */       Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getSkyRenderer, new Object[0]);
/*      */       
/* 1749 */       if (object != null) {
/*      */         
/* 1751 */         Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.theWorld, this.mc });
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/* 1756 */     if (this.mc.world.provider.getDimensionType() == DimensionType.THE_END) {
/*      */       
/* 1758 */       renderSkyEnd();
/*      */     }
/* 1760 */     else if (this.mc.world.provider.isSurfaceWorld()) {
/*      */       
/* 1762 */       GlStateManager.disableTexture2D();
/* 1763 */       boolean flag1 = Config.isShaders();
/*      */       
/* 1765 */       if (flag1)
/*      */       {
/* 1767 */         Shaders.disableTexture2D();
/*      */       }
/*      */       
/* 1770 */       Vec3d vec3d = this.theWorld.getSkyColor(this.mc.getRenderViewEntity(), partialTicks);
/* 1771 */       vec3d = CustomColors.getSkyColor(vec3d, (IBlockAccess)this.mc.world, (this.mc.getRenderViewEntity()).posX, (this.mc.getRenderViewEntity()).posY + 1.0D, (this.mc.getRenderViewEntity()).posZ);
/*      */       
/* 1773 */       if (flag1)
/*      */       {
/* 1775 */         Shaders.setSkyColor(vec3d);
/*      */       }
/*      */       
/* 1778 */       float f = (float)vec3d.xCoord;
/* 1779 */       float f1 = (float)vec3d.yCoord;
/* 1780 */       float f2 = (float)vec3d.zCoord;
/*      */       
/* 1782 */       if (pass != 2) {
/*      */         
/* 1784 */         float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
/* 1785 */         float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
/* 1786 */         float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
/* 1787 */         f = f3;
/* 1788 */         f1 = f4;
/* 1789 */         f2 = f5;
/*      */       } 
/*      */       
/* 1792 */       GlStateManager.color(f, f1, f2);
/* 1793 */       Tessellator tessellator = Tessellator.getInstance();
/* 1794 */       BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 1795 */       GlStateManager.depthMask(false);
/* 1796 */       GlStateManager.enableFog();
/*      */       
/* 1798 */       if (flag1)
/*      */       {
/* 1800 */         Shaders.enableFog();
/*      */       }
/*      */       
/* 1803 */       GlStateManager.color(f, f1, f2);
/*      */       
/* 1805 */       if (flag1)
/*      */       {
/* 1807 */         Shaders.preSkyList();
/*      */       }
/*      */       
/* 1810 */       if (Config.isSkyEnabled())
/*      */       {
/* 1812 */         if (this.vboEnabled) {
/*      */           
/* 1814 */           this.skyVBO.bindBuffer();
/* 1815 */           GlStateManager.glEnableClientState(32884);
/* 1816 */           GlStateManager.glVertexPointer(3, 5126, 12, 0);
/* 1817 */           this.skyVBO.drawArrays(7);
/* 1818 */           this.skyVBO.unbindBuffer();
/* 1819 */           GlStateManager.glDisableClientState(32884);
/*      */         }
/*      */         else {
/*      */           
/* 1823 */           GlStateManager.callList(this.glSkyList);
/*      */         } 
/*      */       }
/*      */       
/* 1827 */       GlStateManager.disableFog();
/*      */       
/* 1829 */       if (flag1)
/*      */       {
/* 1831 */         Shaders.disableFog();
/*      */       }
/*      */       
/* 1834 */       GlStateManager.disableAlpha();
/* 1835 */       GlStateManager.enableBlend();
/* 1836 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 1837 */       RenderHelper.disableStandardItemLighting();
/* 1838 */       float[] afloat = this.theWorld.provider.calcSunriseSunsetColors(this.theWorld.getCelestialAngle(partialTicks), partialTicks);
/*      */       
/* 1840 */       if (afloat != null && Config.isSunMoonEnabled()) {
/*      */         
/* 1842 */         GlStateManager.disableTexture2D();
/*      */         
/* 1844 */         if (flag1)
/*      */         {
/* 1846 */           Shaders.disableTexture2D();
/*      */         }
/*      */         
/* 1849 */         GlStateManager.shadeModel(7425);
/* 1850 */         GlStateManager.pushMatrix();
/* 1851 */         GlStateManager.rotate(90.0F, 1.0F, 0.0F, 0.0F);
/* 1852 */         GlStateManager.rotate((MathHelper.sin(this.theWorld.getCelestialAngleRadians(partialTicks)) < 0.0F) ? 180.0F : 0.0F, 0.0F, 0.0F, 1.0F);
/* 1853 */         GlStateManager.rotate(90.0F, 0.0F, 0.0F, 1.0F);
/* 1854 */         float f6 = afloat[0];
/* 1855 */         float f7 = afloat[1];
/* 1856 */         float f8 = afloat[2];
/*      */         
/* 1858 */         if (pass != 2) {
/*      */           
/* 1860 */           float f9 = (f6 * 30.0F + f7 * 59.0F + f8 * 11.0F) / 100.0F;
/* 1861 */           float f10 = (f6 * 30.0F + f7 * 70.0F) / 100.0F;
/* 1862 */           float f11 = (f6 * 30.0F + f8 * 70.0F) / 100.0F;
/* 1863 */           f6 = f9;
/* 1864 */           f7 = f10;
/* 1865 */           f8 = f11;
/*      */         } 
/*      */         
/* 1868 */         bufferbuilder.begin(6, DefaultVertexFormats.POSITION_COLOR);
/* 1869 */         bufferbuilder.pos(0.0D, 100.0D, 0.0D).color(f6, f7, f8, afloat[3]).endVertex();
/* 1870 */         int l1 = 16;
/*      */         
/* 1872 */         for (int j2 = 0; j2 <= 16; j2++) {
/*      */           
/* 1874 */           float f18 = j2 * 6.2831855F / 16.0F;
/* 1875 */           float f12 = MathHelper.sin(f18);
/* 1876 */           float f13 = MathHelper.cos(f18);
/* 1877 */           bufferbuilder.pos((f12 * 120.0F), (f13 * 120.0F), (-f13 * 40.0F * afloat[3])).color(afloat[0], afloat[1], afloat[2], 0.0F).endVertex();
/*      */         } 
/*      */         
/* 1880 */         tessellator.draw();
/* 1881 */         GlStateManager.popMatrix();
/* 1882 */         GlStateManager.shadeModel(7424);
/*      */       } 
/*      */       
/* 1885 */       GlStateManager.enableTexture2D();
/*      */       
/* 1887 */       if (flag1)
/*      */       {
/* 1889 */         Shaders.enableTexture2D();
/*      */       }
/*      */       
/* 1892 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 1893 */       GlStateManager.pushMatrix();
/* 1894 */       float f15 = 1.0F - this.theWorld.getRainStrength(partialTicks);
/* 1895 */       GlStateManager.color(1.0F, 1.0F, 1.0F, f15);
/* 1896 */       GlStateManager.rotate(-90.0F, 0.0F, 1.0F, 0.0F);
/* 1897 */       CustomSky.renderSky((World)this.theWorld, this.renderEngine, partialTicks);
/*      */       
/* 1899 */       if (flag1)
/*      */       {
/* 1901 */         Shaders.preCelestialRotate();
/*      */       }
/*      */       
/* 1904 */       GlStateManager.rotate(this.theWorld.getCelestialAngle(partialTicks) * 360.0F, 1.0F, 0.0F, 0.0F);
/*      */       
/* 1906 */       if (flag1)
/*      */       {
/* 1908 */         Shaders.postCelestialRotate();
/*      */       }
/*      */       
/* 1911 */       float f16 = 30.0F;
/*      */       
/* 1913 */       if (Config.isSunTexture()) {
/*      */         
/* 1915 */         this.renderEngine.bindTexture(SUN_TEXTURES);
/* 1916 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1917 */         bufferbuilder.pos(-f16, 100.0D, -f16).tex(0.0D, 0.0D).endVertex();
/* 1918 */         bufferbuilder.pos(f16, 100.0D, -f16).tex(1.0D, 0.0D).endVertex();
/* 1919 */         bufferbuilder.pos(f16, 100.0D, f16).tex(1.0D, 1.0D).endVertex();
/* 1920 */         bufferbuilder.pos(-f16, 100.0D, f16).tex(0.0D, 1.0D).endVertex();
/* 1921 */         tessellator.draw();
/*      */       } 
/*      */       
/* 1924 */       f16 = 20.0F;
/*      */       
/* 1926 */       if (Config.isMoonTexture()) {
/*      */         
/* 1928 */         this.renderEngine.bindTexture(MOON_PHASES_TEXTURES);
/* 1929 */         int k1 = this.theWorld.getMoonPhase();
/* 1930 */         int i2 = k1 % 4;
/* 1931 */         int k2 = k1 / 4 % 2;
/* 1932 */         float f19 = (i2 + 0) / 4.0F;
/* 1933 */         float f21 = (k2 + 0) / 2.0F;
/* 1934 */         float f23 = (i2 + 1) / 4.0F;
/* 1935 */         float f14 = (k2 + 1) / 2.0F;
/* 1936 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 1937 */         bufferbuilder.pos(-f16, -100.0D, f16).tex(f23, f14).endVertex();
/* 1938 */         bufferbuilder.pos(f16, -100.0D, f16).tex(f19, f14).endVertex();
/* 1939 */         bufferbuilder.pos(f16, -100.0D, -f16).tex(f19, f21).endVertex();
/* 1940 */         bufferbuilder.pos(-f16, -100.0D, -f16).tex(f23, f21).endVertex();
/* 1941 */         tessellator.draw();
/*      */       } 
/*      */       
/* 1944 */       GlStateManager.disableTexture2D();
/*      */       
/* 1946 */       if (flag1)
/*      */       {
/* 1948 */         Shaders.disableTexture2D();
/*      */       }
/*      */       
/* 1951 */       float f17 = this.theWorld.getStarBrightness(partialTicks) * f15;
/*      */       
/* 1953 */       if (f17 > 0.0F && Config.isStarsEnabled() && !CustomSky.hasSkyLayers((World)this.theWorld)) {
/*      */         
/* 1955 */         GlStateManager.color(f17, f17, f17, f17);
/*      */         
/* 1957 */         if (this.vboEnabled) {
/*      */           
/* 1959 */           this.starVBO.bindBuffer();
/* 1960 */           GlStateManager.glEnableClientState(32884);
/* 1961 */           GlStateManager.glVertexPointer(3, 5126, 12, 0);
/* 1962 */           this.starVBO.drawArrays(7);
/* 1963 */           this.starVBO.unbindBuffer();
/* 1964 */           GlStateManager.glDisableClientState(32884);
/*      */         }
/*      */         else {
/*      */           
/* 1968 */           GlStateManager.callList(this.starGLCallList);
/*      */         } 
/*      */       } 
/*      */       
/* 1972 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 1973 */       GlStateManager.disableBlend();
/* 1974 */       GlStateManager.enableAlpha();
/* 1975 */       GlStateManager.enableFog();
/*      */       
/* 1977 */       if (flag1)
/*      */       {
/* 1979 */         Shaders.enableFog();
/*      */       }
/*      */       
/* 1982 */       GlStateManager.popMatrix();
/* 1983 */       GlStateManager.disableTexture2D();
/*      */       
/* 1985 */       if (flag1)
/*      */       {
/* 1987 */         Shaders.disableTexture2D();
/*      */       }
/*      */       
/* 1990 */       GlStateManager.color(0.0F, 0.0F, 0.0F);
/* 1991 */       double d3 = (this.mc.player.getPositionEyes(partialTicks)).yCoord - this.theWorld.getHorizon();
/*      */       
/* 1993 */       if (d3 < 0.0D) {
/*      */         
/* 1995 */         GlStateManager.pushMatrix();
/* 1996 */         GlStateManager.translate(0.0F, 12.0F, 0.0F);
/*      */         
/* 1998 */         if (this.vboEnabled) {
/*      */           
/* 2000 */           this.sky2VBO.bindBuffer();
/* 2001 */           GlStateManager.glEnableClientState(32884);
/* 2002 */           GlStateManager.glVertexPointer(3, 5126, 12, 0);
/* 2003 */           this.sky2VBO.drawArrays(7);
/* 2004 */           this.sky2VBO.unbindBuffer();
/* 2005 */           GlStateManager.glDisableClientState(32884);
/*      */         }
/*      */         else {
/*      */           
/* 2009 */           GlStateManager.callList(this.glSkyList2);
/*      */         } 
/*      */         
/* 2012 */         GlStateManager.popMatrix();
/* 2013 */         float f20 = 1.0F;
/* 2014 */         float f22 = -((float)(d3 + 65.0D));
/* 2015 */         float f24 = -1.0F;
/* 2016 */         bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
/* 2017 */         bufferbuilder.pos(-1.0D, f22, 1.0D).color(0, 0, 0, 255).endVertex();
/* 2018 */         bufferbuilder.pos(1.0D, f22, 1.0D).color(0, 0, 0, 255).endVertex();
/* 2019 */         bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 2020 */         bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 2021 */         bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 2022 */         bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 2023 */         bufferbuilder.pos(1.0D, f22, -1.0D).color(0, 0, 0, 255).endVertex();
/* 2024 */         bufferbuilder.pos(-1.0D, f22, -1.0D).color(0, 0, 0, 255).endVertex();
/* 2025 */         bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 2026 */         bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 2027 */         bufferbuilder.pos(1.0D, f22, 1.0D).color(0, 0, 0, 255).endVertex();
/* 2028 */         bufferbuilder.pos(1.0D, f22, -1.0D).color(0, 0, 0, 255).endVertex();
/* 2029 */         bufferbuilder.pos(-1.0D, f22, -1.0D).color(0, 0, 0, 255).endVertex();
/* 2030 */         bufferbuilder.pos(-1.0D, f22, 1.0D).color(0, 0, 0, 255).endVertex();
/* 2031 */         bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 2032 */         bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 2033 */         bufferbuilder.pos(-1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 2034 */         bufferbuilder.pos(-1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 2035 */         bufferbuilder.pos(1.0D, -1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 2036 */         bufferbuilder.pos(1.0D, -1.0D, -1.0D).color(0, 0, 0, 255).endVertex();
/* 2037 */         tessellator.draw();
/*      */       } 
/*      */       
/* 2040 */       if (this.theWorld.provider.isSkyColored()) {
/*      */         
/* 2042 */         GlStateManager.color(f * 0.2F + 0.04F, f1 * 0.2F + 0.04F, f2 * 0.6F + 0.1F);
/*      */       }
/*      */       else {
/*      */         
/* 2046 */         GlStateManager.color(f, f1, f2);
/*      */       } 
/*      */       
/* 2049 */       if (this.mc.gameSettings.renderDistanceChunks <= 4)
/*      */       {
/* 2051 */         GlStateManager.color(this.mc.entityRenderer.fogColorRed, this.mc.entityRenderer.fogColorGreen, this.mc.entityRenderer.fogColorBlue);
/*      */       }
/*      */       
/* 2054 */       GlStateManager.pushMatrix();
/* 2055 */       GlStateManager.translate(0.0F, -((float)(d3 - 16.0D)), 0.0F);
/*      */       
/* 2057 */       if (Config.isSkyEnabled())
/*      */       {
/* 2059 */         GlStateManager.callList(this.glSkyList2);
/*      */       }
/*      */       
/* 2062 */       GlStateManager.popMatrix();
/* 2063 */       GlStateManager.enableTexture2D();
/*      */       
/* 2065 */       if (flag1)
/*      */       {
/* 2067 */         Shaders.enableTexture2D();
/*      */       }
/*      */       
/* 2070 */       GlStateManager.depthMask(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void renderClouds(float partialTicks, int pass, double p_180447_3_, double p_180447_5_, double p_180447_7_) {
/* 2076 */     if (!Config.isCloudsOff()) {
/*      */       
/* 2078 */       if (Reflector.ForgeWorldProvider_getCloudRenderer.exists()) {
/*      */         
/* 2080 */         WorldProvider worldprovider = this.mc.world.provider;
/* 2081 */         Object object = Reflector.call(worldprovider, Reflector.ForgeWorldProvider_getCloudRenderer, new Object[0]);
/*      */         
/* 2083 */         if (object != null) {
/*      */           
/* 2085 */           Reflector.callVoid(object, Reflector.IRenderHandler_render, new Object[] { Float.valueOf(partialTicks), this.theWorld, this.mc });
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/* 2090 */       if (this.mc.world.provider.isSurfaceWorld()) {
/*      */         
/* 2092 */         if (Config.isShaders())
/*      */         {
/* 2094 */           Shaders.beginClouds();
/*      */         }
/*      */         
/* 2097 */         if (Config.isCloudsFancy()) {
/*      */           
/* 2099 */           renderCloudsFancy(partialTicks, pass, p_180447_3_, p_180447_5_, p_180447_7_);
/*      */         }
/*      */         else {
/*      */           
/* 2103 */           float f9 = partialTicks;
/* 2104 */           partialTicks = 0.0F;
/* 2105 */           GlStateManager.disableCull();
/* 2106 */           int l2 = 32;
/* 2107 */           int k1 = 8;
/* 2108 */           Tessellator tessellator = Tessellator.getInstance();
/* 2109 */           BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 2110 */           this.renderEngine.bindTexture(CLOUDS_TEXTURES);
/* 2111 */           GlStateManager.enableBlend();
/* 2112 */           GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 2113 */           Vec3d vec3d = this.theWorld.getCloudColour(partialTicks);
/* 2114 */           float f = (float)vec3d.xCoord;
/* 2115 */           float f1 = (float)vec3d.yCoord;
/* 2116 */           float f2 = (float)vec3d.zCoord;
/* 2117 */           this.cloudRenderer.prepareToRender(false, this.cloudTickCounter, f9, vec3d);
/*      */           
/* 2119 */           if (this.cloudRenderer.shouldUpdateGlList()) {
/*      */             
/* 2121 */             this.cloudRenderer.startUpdateGlList();
/*      */             
/* 2123 */             if (pass != 2) {
/*      */               
/* 2125 */               float f3 = (f * 30.0F + f1 * 59.0F + f2 * 11.0F) / 100.0F;
/* 2126 */               float f4 = (f * 30.0F + f1 * 70.0F) / 100.0F;
/* 2127 */               float f5 = (f * 30.0F + f2 * 70.0F) / 100.0F;
/* 2128 */               f = f3;
/* 2129 */               f1 = f4;
/* 2130 */               f2 = f5;
/*      */             } 
/*      */             
/* 2133 */             float f10 = 4.8828125E-4F;
/* 2134 */             double d5 = (this.cloudTickCounter + partialTicks);
/* 2135 */             double d3 = p_180447_3_ + d5 * 0.029999999329447746D;
/* 2136 */             int l1 = MathHelper.floor(d3 / 2048.0D);
/* 2137 */             int i2 = MathHelper.floor(p_180447_7_ / 2048.0D);
/* 2138 */             d3 -= (l1 * 2048);
/* 2139 */             double d4 = p_180447_7_ - (i2 * 2048);
/* 2140 */             float f6 = this.theWorld.provider.getCloudHeight() - (float)p_180447_5_ + 0.33F;
/* 2141 */             f6 += this.mc.gameSettings.ofCloudsHeight * 128.0F;
/* 2142 */             float f7 = (float)(d3 * 4.8828125E-4D);
/* 2143 */             float f8 = (float)(d4 * 4.8828125E-4D);
/* 2144 */             bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/*      */             
/* 2146 */             for (int j2 = -256; j2 < 256; j2 += 32) {
/*      */               
/* 2148 */               for (int k2 = -256; k2 < 256; k2 += 32) {
/*      */                 
/* 2150 */                 bufferbuilder.pos((j2 + 0), f6, (k2 + 32)).tex(((j2 + 0) * 4.8828125E-4F + f7), ((k2 + 32) * 4.8828125E-4F + f8)).color(f, f1, f2, 0.8F).endVertex();
/* 2151 */                 bufferbuilder.pos((j2 + 32), f6, (k2 + 32)).tex(((j2 + 32) * 4.8828125E-4F + f7), ((k2 + 32) * 4.8828125E-4F + f8)).color(f, f1, f2, 0.8F).endVertex();
/* 2152 */                 bufferbuilder.pos((j2 + 32), f6, (k2 + 0)).tex(((j2 + 32) * 4.8828125E-4F + f7), ((k2 + 0) * 4.8828125E-4F + f8)).color(f, f1, f2, 0.8F).endVertex();
/* 2153 */                 bufferbuilder.pos((j2 + 0), f6, (k2 + 0)).tex(((j2 + 0) * 4.8828125E-4F + f7), ((k2 + 0) * 4.8828125E-4F + f8)).color(f, f1, f2, 0.8F).endVertex();
/*      */               } 
/*      */             } 
/*      */             
/* 2157 */             tessellator.draw();
/* 2158 */             this.cloudRenderer.endUpdateGlList();
/*      */           } 
/*      */           
/* 2161 */           this.cloudRenderer.renderGlList();
/* 2162 */           GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 2163 */           GlStateManager.disableBlend();
/* 2164 */           GlStateManager.enableCull();
/*      */         } 
/*      */         
/* 2167 */         if (Config.isShaders())
/*      */         {
/* 2169 */           Shaders.endClouds();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean hasCloudFog(double x, double y, double z, float partialTicks) {
/* 2180 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   private void renderCloudsFancy(float partialTicks, int pass, double p_180445_3_, double p_180445_5_, double p_180445_7_) {
/* 2185 */     float f251 = 0.0F;
/* 2186 */     GlStateManager.disableCull();
/* 2187 */     Tessellator tessellator = Tessellator.getInstance();
/* 2188 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 2189 */     float f = 12.0F;
/* 2190 */     float f1 = 4.0F;
/* 2191 */     double d3 = (this.cloudTickCounter + f251);
/* 2192 */     double d4 = (p_180445_3_ + d3 * 0.029999999329447746D) / 12.0D;
/* 2193 */     double d5 = p_180445_7_ / 12.0D + 0.33000001311302185D;
/* 2194 */     float f2 = this.theWorld.provider.getCloudHeight() - (float)p_180445_5_ + 0.33F;
/* 2195 */     f2 += this.mc.gameSettings.ofCloudsHeight * 128.0F;
/* 2196 */     int k1 = MathHelper.floor(d4 / 2048.0D);
/* 2197 */     int l1 = MathHelper.floor(d5 / 2048.0D);
/* 2198 */     d4 -= (k1 * 2048);
/* 2199 */     d5 -= (l1 * 2048);
/* 2200 */     this.renderEngine.bindTexture(CLOUDS_TEXTURES);
/* 2201 */     GlStateManager.enableBlend();
/* 2202 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 2203 */     Vec3d vec3d = this.theWorld.getCloudColour(f251);
/* 2204 */     float f3 = (float)vec3d.xCoord;
/* 2205 */     float f4 = (float)vec3d.yCoord;
/* 2206 */     float f5 = (float)vec3d.zCoord;
/* 2207 */     this.cloudRenderer.prepareToRender(true, this.cloudTickCounter, partialTicks, vec3d);
/*      */     
/* 2209 */     if (pass != 2) {
/*      */       
/* 2211 */       float f6 = (f3 * 30.0F + f4 * 59.0F + f5 * 11.0F) / 100.0F;
/* 2212 */       float f7 = (f3 * 30.0F + f4 * 70.0F) / 100.0F;
/* 2213 */       float f8 = (f3 * 30.0F + f5 * 70.0F) / 100.0F;
/* 2214 */       f3 = f6;
/* 2215 */       f4 = f7;
/* 2216 */       f5 = f8;
/*      */     } 
/*      */     
/* 2219 */     float f26 = f3 * 0.9F;
/* 2220 */     float f27 = f4 * 0.9F;
/* 2221 */     float f28 = f5 * 0.9F;
/* 2222 */     float f9 = f3 * 0.7F;
/* 2223 */     float f10 = f4 * 0.7F;
/* 2224 */     float f11 = f5 * 0.7F;
/* 2225 */     float f12 = f3 * 0.8F;
/* 2226 */     float f13 = f4 * 0.8F;
/* 2227 */     float f14 = f5 * 0.8F;
/* 2228 */     float f15 = 0.00390625F;
/* 2229 */     float f16 = MathHelper.floor(d4) * 0.00390625F;
/* 2230 */     float f17 = MathHelper.floor(d5) * 0.00390625F;
/* 2231 */     float f18 = (float)(d4 - MathHelper.floor(d4));
/* 2232 */     float f19 = (float)(d5 - MathHelper.floor(d5));
/* 2233 */     int i2 = 8;
/* 2234 */     int j2 = 4;
/* 2235 */     float f20 = 9.765625E-4F;
/* 2236 */     GlStateManager.scale(12.0F, 1.0F, 12.0F);
/*      */     
/* 2238 */     for (int k2 = 0; k2 < 2; k2++) {
/*      */       
/* 2240 */       if (k2 == 0) {
/*      */         
/* 2242 */         GlStateManager.colorMask(false, false, false, false);
/*      */       }
/*      */       else {
/*      */         
/* 2246 */         switch (pass) {
/*      */           
/*      */           case 0:
/* 2249 */             GlStateManager.colorMask(false, true, true, true);
/*      */             break;
/*      */           
/*      */           case 1:
/* 2253 */             GlStateManager.colorMask(true, false, false, true);
/*      */             break;
/*      */           
/*      */           case 2:
/* 2257 */             GlStateManager.colorMask(true, true, true, true);
/*      */             break;
/*      */         } 
/*      */       } 
/* 2261 */       this.cloudRenderer.renderGlList();
/*      */     } 
/*      */     
/* 2264 */     if (this.cloudRenderer.shouldUpdateGlList()) {
/*      */       
/* 2266 */       this.cloudRenderer.startUpdateGlList();
/*      */       
/* 2268 */       for (int j3 = -3; j3 <= 4; j3++) {
/*      */         
/* 2270 */         for (int l2 = -3; l2 <= 4; l2++) {
/*      */           
/* 2272 */           bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR_NORMAL);
/* 2273 */           float f21 = (j3 * 8);
/* 2274 */           float f22 = (l2 * 8);
/* 2275 */           float f23 = f21 - f18;
/* 2276 */           float f24 = f22 - f19;
/*      */           
/* 2278 */           if (f2 > -5.0F) {
/*      */             
/* 2280 */             bufferbuilder.pos((f23 + 0.0F), (f2 + 0.0F), (f24 + 8.0F)).tex(((f21 + 0.0F) * 0.00390625F + f16), ((f22 + 8.0F) * 0.00390625F + f17)).color(f9, f10, f11, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 2281 */             bufferbuilder.pos((f23 + 8.0F), (f2 + 0.0F), (f24 + 8.0F)).tex(((f21 + 8.0F) * 0.00390625F + f16), ((f22 + 8.0F) * 0.00390625F + f17)).color(f9, f10, f11, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 2282 */             bufferbuilder.pos((f23 + 8.0F), (f2 + 0.0F), (f24 + 0.0F)).tex(((f21 + 8.0F) * 0.00390625F + f16), ((f22 + 0.0F) * 0.00390625F + f17)).color(f9, f10, f11, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/* 2283 */             bufferbuilder.pos((f23 + 0.0F), (f2 + 0.0F), (f24 + 0.0F)).tex(((f21 + 0.0F) * 0.00390625F + f16), ((f22 + 0.0F) * 0.00390625F + f17)).color(f9, f10, f11, 0.8F).normal(0.0F, -1.0F, 0.0F).endVertex();
/*      */           } 
/*      */           
/* 2286 */           if (f2 <= 5.0F) {
/*      */             
/* 2288 */             bufferbuilder.pos((f23 + 0.0F), (f2 + 4.0F - 9.765625E-4F), (f24 + 8.0F)).tex(((f21 + 0.0F) * 0.00390625F + f16), ((f22 + 8.0F) * 0.00390625F + f17)).color(f3, f4, f5, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 2289 */             bufferbuilder.pos((f23 + 8.0F), (f2 + 4.0F - 9.765625E-4F), (f24 + 8.0F)).tex(((f21 + 8.0F) * 0.00390625F + f16), ((f22 + 8.0F) * 0.00390625F + f17)).color(f3, f4, f5, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 2290 */             bufferbuilder.pos((f23 + 8.0F), (f2 + 4.0F - 9.765625E-4F), (f24 + 0.0F)).tex(((f21 + 8.0F) * 0.00390625F + f16), ((f22 + 0.0F) * 0.00390625F + f17)).color(f3, f4, f5, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/* 2291 */             bufferbuilder.pos((f23 + 0.0F), (f2 + 4.0F - 9.765625E-4F), (f24 + 0.0F)).tex(((f21 + 0.0F) * 0.00390625F + f16), ((f22 + 0.0F) * 0.00390625F + f17)).color(f3, f4, f5, 0.8F).normal(0.0F, 1.0F, 0.0F).endVertex();
/*      */           } 
/*      */           
/* 2294 */           if (j3 > -1)
/*      */           {
/* 2296 */             for (int i3 = 0; i3 < 8; i3++) {
/*      */               
/* 2298 */               bufferbuilder.pos((f23 + i3 + 0.0F), (f2 + 0.0F), (f24 + 8.0F)).tex(((f21 + i3 + 0.5F) * 0.00390625F + f16), ((f22 + 8.0F) * 0.00390625F + f17)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 2299 */               bufferbuilder.pos((f23 + i3 + 0.0F), (f2 + 4.0F), (f24 + 8.0F)).tex(((f21 + i3 + 0.5F) * 0.00390625F + f16), ((f22 + 8.0F) * 0.00390625F + f17)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 2300 */               bufferbuilder.pos((f23 + i3 + 0.0F), (f2 + 4.0F), (f24 + 0.0F)).tex(((f21 + i3 + 0.5F) * 0.00390625F + f16), ((f22 + 0.0F) * 0.00390625F + f17)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/* 2301 */               bufferbuilder.pos((f23 + i3 + 0.0F), (f2 + 0.0F), (f24 + 0.0F)).tex(((f21 + i3 + 0.5F) * 0.00390625F + f16), ((f22 + 0.0F) * 0.00390625F + f17)).color(f26, f27, f28, 0.8F).normal(-1.0F, 0.0F, 0.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2305 */           if (j3 <= 1)
/*      */           {
/* 2307 */             for (int k3 = 0; k3 < 8; k3++) {
/*      */               
/* 2309 */               bufferbuilder.pos((f23 + k3 + 1.0F - 9.765625E-4F), (f2 + 0.0F), (f24 + 8.0F)).tex(((f21 + k3 + 0.5F) * 0.00390625F + f16), ((f22 + 8.0F) * 0.00390625F + f17)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 2310 */               bufferbuilder.pos((f23 + k3 + 1.0F - 9.765625E-4F), (f2 + 4.0F), (f24 + 8.0F)).tex(((f21 + k3 + 0.5F) * 0.00390625F + f16), ((f22 + 8.0F) * 0.00390625F + f17)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 2311 */               bufferbuilder.pos((f23 + k3 + 1.0F - 9.765625E-4F), (f2 + 4.0F), (f24 + 0.0F)).tex(((f21 + k3 + 0.5F) * 0.00390625F + f16), ((f22 + 0.0F) * 0.00390625F + f17)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/* 2312 */               bufferbuilder.pos((f23 + k3 + 1.0F - 9.765625E-4F), (f2 + 0.0F), (f24 + 0.0F)).tex(((f21 + k3 + 0.5F) * 0.00390625F + f16), ((f22 + 0.0F) * 0.00390625F + f17)).color(f26, f27, f28, 0.8F).normal(1.0F, 0.0F, 0.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2316 */           if (l2 > -1)
/*      */           {
/* 2318 */             for (int l3 = 0; l3 < 8; l3++) {
/*      */               
/* 2320 */               bufferbuilder.pos((f23 + 0.0F), (f2 + 4.0F), (f24 + l3 + 0.0F)).tex(((f21 + 0.0F) * 0.00390625F + f16), ((f22 + l3 + 0.5F) * 0.00390625F + f17)).color(f12, f13, f14, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 2321 */               bufferbuilder.pos((f23 + 8.0F), (f2 + 4.0F), (f24 + l3 + 0.0F)).tex(((f21 + 8.0F) * 0.00390625F + f16), ((f22 + l3 + 0.5F) * 0.00390625F + f17)).color(f12, f13, f14, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 2322 */               bufferbuilder.pos((f23 + 8.0F), (f2 + 0.0F), (f24 + l3 + 0.0F)).tex(((f21 + 8.0F) * 0.00390625F + f16), ((f22 + l3 + 0.5F) * 0.00390625F + f17)).color(f12, f13, f14, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/* 2323 */               bufferbuilder.pos((f23 + 0.0F), (f2 + 0.0F), (f24 + l3 + 0.0F)).tex(((f21 + 0.0F) * 0.00390625F + f16), ((f22 + l3 + 0.5F) * 0.00390625F + f17)).color(f12, f13, f14, 0.8F).normal(0.0F, 0.0F, -1.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2327 */           if (l2 <= 1)
/*      */           {
/* 2329 */             for (int i4 = 0; i4 < 8; i4++) {
/*      */               
/* 2331 */               bufferbuilder.pos((f23 + 0.0F), (f2 + 4.0F), (f24 + i4 + 1.0F - 9.765625E-4F)).tex(((f21 + 0.0F) * 0.00390625F + f16), ((f22 + i4 + 0.5F) * 0.00390625F + f17)).color(f12, f13, f14, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 2332 */               bufferbuilder.pos((f23 + 8.0F), (f2 + 4.0F), (f24 + i4 + 1.0F - 9.765625E-4F)).tex(((f21 + 8.0F) * 0.00390625F + f16), ((f22 + i4 + 0.5F) * 0.00390625F + f17)).color(f12, f13, f14, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 2333 */               bufferbuilder.pos((f23 + 8.0F), (f2 + 0.0F), (f24 + i4 + 1.0F - 9.765625E-4F)).tex(((f21 + 8.0F) * 0.00390625F + f16), ((f22 + i4 + 0.5F) * 0.00390625F + f17)).color(f12, f13, f14, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/* 2334 */               bufferbuilder.pos((f23 + 0.0F), (f2 + 0.0F), (f24 + i4 + 1.0F - 9.765625E-4F)).tex(((f21 + 0.0F) * 0.00390625F + f16), ((f22 + i4 + 0.5F) * 0.00390625F + f17)).color(f12, f13, f14, 0.8F).normal(0.0F, 0.0F, 1.0F).endVertex();
/*      */             } 
/*      */           }
/*      */           
/* 2338 */           tessellator.draw();
/*      */         } 
/*      */       } 
/*      */       
/* 2342 */       this.cloudRenderer.endUpdateGlList();
/*      */     } 
/*      */     
/* 2345 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 2346 */     GlStateManager.disableBlend();
/* 2347 */     GlStateManager.enableCull();
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateChunks(long finishTimeNano) {
/* 2352 */     finishTimeNano = (long)(finishTimeNano + 1.0E8D);
/* 2353 */     this.displayListEntitiesDirty |= this.renderDispatcher.runChunkUploads(finishTimeNano);
/*      */     
/* 2355 */     if (this.chunksToUpdateForced.size() > 0) {
/*      */       
/* 2357 */       Iterator<RenderChunk> iterator = this.chunksToUpdateForced.iterator();
/*      */       
/* 2359 */       while (iterator.hasNext()) {
/*      */         
/* 2361 */         RenderChunk renderchunk1 = iterator.next();
/*      */         
/* 2363 */         if (!this.renderDispatcher.updateChunkLater(renderchunk1)) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/* 2368 */         renderchunk1.clearNeedsUpdate();
/* 2369 */         iterator.remove();
/* 2370 */         this.chunksToUpdate.remove(renderchunk1);
/* 2371 */         this.chunksToResortTransparency.remove(renderchunk1);
/*      */       } 
/*      */     } 
/*      */     
/* 2375 */     if (this.chunksToResortTransparency.size() > 0) {
/*      */       
/* 2377 */       Iterator<RenderChunk> iterator2 = this.chunksToResortTransparency.iterator();
/*      */       
/* 2379 */       if (iterator2.hasNext()) {
/*      */         
/* 2381 */         RenderChunk renderchunk3 = iterator2.next();
/*      */         
/* 2383 */         if (this.renderDispatcher.updateTransparencyLater(renderchunk3))
/*      */         {
/* 2385 */           iterator2.remove();
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2390 */     int l1 = 0;
/* 2391 */     int i2 = Config.getUpdatesPerFrame();
/* 2392 */     int k1 = i2 * 2;
/*      */     
/* 2394 */     if (!this.chunksToUpdate.isEmpty()) {
/*      */       
/* 2396 */       Iterator<RenderChunk> iterator1 = this.chunksToUpdate.iterator();
/*      */       
/* 2398 */       while (iterator1.hasNext()) {
/*      */         boolean flag1;
/* 2400 */         RenderChunk renderchunk2 = iterator1.next();
/*      */ 
/*      */         
/* 2403 */         if (renderchunk2.isNeedsUpdateCustom()) {
/*      */           
/* 2405 */           flag1 = this.renderDispatcher.updateChunkNow(renderchunk2);
/*      */         }
/*      */         else {
/*      */           
/* 2409 */           flag1 = this.renderDispatcher.updateChunkLater(renderchunk2);
/*      */         } 
/*      */         
/* 2412 */         if (!flag1) {
/*      */           break;
/*      */         }
/*      */ 
/*      */         
/* 2417 */         renderchunk2.clearNeedsUpdate();
/* 2418 */         iterator1.remove();
/*      */         
/* 2420 */         if (renderchunk2.getCompiledChunk().isEmpty() && i2 < k1)
/*      */         {
/* 2422 */           i2++;
/*      */         }
/*      */         
/* 2425 */         l1++;
/*      */         
/* 2427 */         if (l1 >= i2) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void renderWorldBorder(Entity entityIn, float partialTicks) {
/* 2437 */     Tessellator tessellator = Tessellator.getInstance();
/* 2438 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 2439 */     WorldBorder worldborder = this.theWorld.getWorldBorder();
/* 2440 */     double d3 = (this.mc.gameSettings.renderDistanceChunks * 16);
/*      */     
/* 2442 */     if (entityIn.posX >= worldborder.maxX() - d3 || entityIn.posX <= worldborder.minX() + d3 || entityIn.posZ >= worldborder.maxZ() - d3 || entityIn.posZ <= worldborder.minZ() + d3) {
/*      */       
/* 2444 */       double d4 = 1.0D - worldborder.getClosestDistance(entityIn) / d3;
/* 2445 */       d4 = Math.pow(d4, 4.0D);
/* 2446 */       double d5 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 2447 */       double d6 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 2448 */       double d7 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/* 2449 */       GlStateManager.enableBlend();
/* 2450 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 2451 */       this.renderEngine.bindTexture(FORCEFIELD_TEXTURES);
/* 2452 */       GlStateManager.depthMask(false);
/* 2453 */       GlStateManager.pushMatrix();
/* 2454 */       int k1 = worldborder.getStatus().getID();
/* 2455 */       float f = (k1 >> 16 & 0xFF) / 255.0F;
/* 2456 */       float f1 = (k1 >> 8 & 0xFF) / 255.0F;
/* 2457 */       float f2 = (k1 & 0xFF) / 255.0F;
/* 2458 */       GlStateManager.color(f, f1, f2, (float)d4);
/* 2459 */       GlStateManager.doPolygonOffset(-3.0F, -3.0F);
/* 2460 */       GlStateManager.enablePolygonOffset();
/* 2461 */       GlStateManager.alphaFunc(516, 0.1F);
/* 2462 */       GlStateManager.enableAlpha();
/* 2463 */       GlStateManager.disableCull();
/* 2464 */       float f3 = (float)(Minecraft.getSystemTime() % 3000L) / 3000.0F;
/* 2465 */       float f4 = 0.0F;
/* 2466 */       float f5 = 0.0F;
/* 2467 */       float f6 = 128.0F;
/* 2468 */       bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
/* 2469 */       bufferbuilder.setTranslation(-d5, -d6, -d7);
/* 2470 */       double d8 = Math.max(MathHelper.floor(d7 - d3), worldborder.minZ());
/* 2471 */       double d9 = Math.min(MathHelper.ceil(d7 + d3), worldborder.maxZ());
/*      */       
/* 2473 */       if (d5 > worldborder.maxX() - d3) {
/*      */         
/* 2475 */         float f7 = 0.0F;
/*      */         
/* 2477 */         for (double d10 = d8; d10 < d9; f7 += 0.5F) {
/*      */           
/* 2479 */           double d11 = Math.min(1.0D, d9 - d10);
/* 2480 */           float f8 = (float)d11 * 0.5F;
/* 2481 */           bufferbuilder.pos(worldborder.maxX(), 256.0D, d10).tex((f3 + f7), (f3 + 0.0F)).endVertex();
/* 2482 */           bufferbuilder.pos(worldborder.maxX(), 256.0D, d10 + d11).tex((f3 + f8 + f7), (f3 + 0.0F)).endVertex();
/* 2483 */           bufferbuilder.pos(worldborder.maxX(), 0.0D, d10 + d11).tex((f3 + f8 + f7), (f3 + 128.0F)).endVertex();
/* 2484 */           bufferbuilder.pos(worldborder.maxX(), 0.0D, d10).tex((f3 + f7), (f3 + 128.0F)).endVertex();
/* 2485 */           d10++;
/*      */         } 
/*      */       } 
/*      */       
/* 2489 */       if (d5 < worldborder.minX() + d3) {
/*      */         
/* 2491 */         float f9 = 0.0F;
/*      */         
/* 2493 */         for (double d12 = d8; d12 < d9; f9 += 0.5F) {
/*      */           
/* 2495 */           double d15 = Math.min(1.0D, d9 - d12);
/* 2496 */           float f12 = (float)d15 * 0.5F;
/* 2497 */           bufferbuilder.pos(worldborder.minX(), 256.0D, d12).tex((f3 + f9), (f3 + 0.0F)).endVertex();
/* 2498 */           bufferbuilder.pos(worldborder.minX(), 256.0D, d12 + d15).tex((f3 + f12 + f9), (f3 + 0.0F)).endVertex();
/* 2499 */           bufferbuilder.pos(worldborder.minX(), 0.0D, d12 + d15).tex((f3 + f12 + f9), (f3 + 128.0F)).endVertex();
/* 2500 */           bufferbuilder.pos(worldborder.minX(), 0.0D, d12).tex((f3 + f9), (f3 + 128.0F)).endVertex();
/* 2501 */           d12++;
/*      */         } 
/*      */       } 
/*      */       
/* 2505 */       d8 = Math.max(MathHelper.floor(d5 - d3), worldborder.minX());
/* 2506 */       d9 = Math.min(MathHelper.ceil(d5 + d3), worldborder.maxX());
/*      */       
/* 2508 */       if (d7 > worldborder.maxZ() - d3) {
/*      */         
/* 2510 */         float f10 = 0.0F;
/*      */         
/* 2512 */         for (double d13 = d8; d13 < d9; f10 += 0.5F) {
/*      */           
/* 2514 */           double d16 = Math.min(1.0D, d9 - d13);
/* 2515 */           float f13 = (float)d16 * 0.5F;
/* 2516 */           bufferbuilder.pos(d13, 256.0D, worldborder.maxZ()).tex((f3 + f10), (f3 + 0.0F)).endVertex();
/* 2517 */           bufferbuilder.pos(d13 + d16, 256.0D, worldborder.maxZ()).tex((f3 + f13 + f10), (f3 + 0.0F)).endVertex();
/* 2518 */           bufferbuilder.pos(d13 + d16, 0.0D, worldborder.maxZ()).tex((f3 + f13 + f10), (f3 + 128.0F)).endVertex();
/* 2519 */           bufferbuilder.pos(d13, 0.0D, worldborder.maxZ()).tex((f3 + f10), (f3 + 128.0F)).endVertex();
/* 2520 */           d13++;
/*      */         } 
/*      */       } 
/*      */       
/* 2524 */       if (d7 < worldborder.minZ() + d3) {
/*      */         
/* 2526 */         float f11 = 0.0F;
/*      */         
/* 2528 */         for (double d14 = d8; d14 < d9; f11 += 0.5F) {
/*      */           
/* 2530 */           double d17 = Math.min(1.0D, d9 - d14);
/* 2531 */           float f14 = (float)d17 * 0.5F;
/* 2532 */           bufferbuilder.pos(d14, 256.0D, worldborder.minZ()).tex((f3 + f11), (f3 + 0.0F)).endVertex();
/* 2533 */           bufferbuilder.pos(d14 + d17, 256.0D, worldborder.minZ()).tex((f3 + f14 + f11), (f3 + 0.0F)).endVertex();
/* 2534 */           bufferbuilder.pos(d14 + d17, 0.0D, worldborder.minZ()).tex((f3 + f14 + f11), (f3 + 128.0F)).endVertex();
/* 2535 */           bufferbuilder.pos(d14, 0.0D, worldborder.minZ()).tex((f3 + f11), (f3 + 128.0F)).endVertex();
/* 2536 */           d14++;
/*      */         } 
/*      */       } 
/*      */       
/* 2540 */       tessellator.draw();
/* 2541 */       bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
/* 2542 */       GlStateManager.enableCull();
/* 2543 */       GlStateManager.disableAlpha();
/* 2544 */       GlStateManager.doPolygonOffset(0.0F, 0.0F);
/* 2545 */       GlStateManager.disablePolygonOffset();
/* 2546 */       GlStateManager.enableAlpha();
/* 2547 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 2548 */       GlStateManager.disableBlend();
/* 2549 */       GlStateManager.popMatrix();
/* 2550 */       GlStateManager.depthMask(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void preRenderDamagedBlocks() {
/* 2556 */     GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.DST_COLOR, GlStateManager.DestFactor.SRC_COLOR, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 2557 */     GlStateManager.enableBlend();
/* 2558 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
/* 2559 */     GlStateManager.doPolygonOffset(-3.0F, -3.0F);
/* 2560 */     GlStateManager.enablePolygonOffset();
/* 2561 */     GlStateManager.alphaFunc(516, 0.1F);
/* 2562 */     GlStateManager.enableAlpha();
/* 2563 */     GlStateManager.pushMatrix();
/*      */     
/* 2565 */     if (Config.isShaders())
/*      */     {
/* 2567 */       ShadersRender.beginBlockDamage();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void postRenderDamagedBlocks() {
/* 2573 */     GlStateManager.disableAlpha();
/* 2574 */     GlStateManager.doPolygonOffset(0.0F, 0.0F);
/* 2575 */     GlStateManager.disablePolygonOffset();
/* 2576 */     GlStateManager.enableAlpha();
/* 2577 */     GlStateManager.depthMask(true);
/* 2578 */     GlStateManager.popMatrix();
/*      */     
/* 2580 */     if (Config.isShaders())
/*      */     {
/* 2582 */       ShadersRender.endBlockDamage();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void drawBlockDamageTexture(Tessellator tessellatorIn, BufferBuilder worldRendererIn, Entity entityIn, float partialTicks) {
/* 2588 */     double d3 = entityIn.lastTickPosX + (entityIn.posX - entityIn.lastTickPosX) * partialTicks;
/* 2589 */     double d4 = entityIn.lastTickPosY + (entityIn.posY - entityIn.lastTickPosY) * partialTicks;
/* 2590 */     double d5 = entityIn.lastTickPosZ + (entityIn.posZ - entityIn.lastTickPosZ) * partialTicks;
/*      */     
/* 2592 */     if (!this.damagedBlocks.isEmpty()) {
/*      */       
/* 2594 */       this.renderEngine.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
/* 2595 */       preRenderDamagedBlocks();
/* 2596 */       worldRendererIn.begin(7, DefaultVertexFormats.BLOCK);
/* 2597 */       worldRendererIn.setTranslation(-d3, -d4, -d5);
/* 2598 */       worldRendererIn.noColor();
/* 2599 */       Iterator<DestroyBlockProgress> iterator = this.damagedBlocks.values().iterator();
/*      */       
/* 2601 */       while (iterator.hasNext()) {
/*      */         boolean flag1;
/* 2603 */         DestroyBlockProgress destroyblockprogress = iterator.next();
/* 2604 */         BlockPos blockpos = destroyblockprogress.getPosition();
/* 2605 */         double d6 = blockpos.getX() - d3;
/* 2606 */         double d7 = blockpos.getY() - d4;
/* 2607 */         double d8 = blockpos.getZ() - d5;
/* 2608 */         Block block = this.theWorld.getBlockState(blockpos).getBlock();
/*      */ 
/*      */         
/* 2611 */         if (Reflector.ForgeTileEntity_canRenderBreaking.exists()) {
/*      */           
/* 2613 */           boolean flag2 = !(!(block instanceof net.minecraft.block.BlockChest) && !(block instanceof net.minecraft.block.BlockEnderChest) && !(block instanceof net.minecraft.block.BlockSign) && !(block instanceof net.minecraft.block.BlockSkull));
/*      */           
/* 2615 */           if (!flag2) {
/*      */             
/* 2617 */             TileEntity tileentity = this.theWorld.getTileEntity(blockpos);
/*      */             
/* 2619 */             if (tileentity != null)
/*      */             {
/* 2621 */               flag2 = Reflector.callBoolean(tileentity, Reflector.ForgeTileEntity_canRenderBreaking, new Object[0]);
/*      */             }
/*      */           } 
/*      */           
/* 2625 */           flag1 = !flag2;
/*      */         }
/*      */         else {
/*      */           
/* 2629 */           flag1 = (!(block instanceof net.minecraft.block.BlockChest) && !(block instanceof net.minecraft.block.BlockEnderChest) && !(block instanceof net.minecraft.block.BlockSign) && !(block instanceof net.minecraft.block.BlockSkull));
/*      */         } 
/*      */         
/* 2632 */         if (flag1) {
/*      */           
/* 2634 */           if (d6 * d6 + d7 * d7 + d8 * d8 > 1024.0D) {
/*      */             
/* 2636 */             iterator.remove();
/*      */             
/*      */             continue;
/*      */           } 
/* 2640 */           IBlockState iblockstate = this.theWorld.getBlockState(blockpos);
/*      */           
/* 2642 */           if (iblockstate.getMaterial() != Material.AIR) {
/*      */             
/* 2644 */             int k1 = destroyblockprogress.getPartialBlockDamage();
/* 2645 */             TextureAtlasSprite textureatlassprite = this.destroyBlockIcons[k1];
/* 2646 */             BlockRendererDispatcher blockrendererdispatcher = this.mc.getBlockRendererDispatcher();
/* 2647 */             blockrendererdispatcher.renderBlockDamage(iblockstate, blockpos, textureatlassprite, (IBlockAccess)this.theWorld);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 2653 */       tessellatorIn.draw();
/* 2654 */       worldRendererIn.setTranslation(0.0D, 0.0D, 0.0D);
/* 2655 */       postRenderDamagedBlocks();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void drawSelectionBox(EntityPlayer player, RayTraceResult movingObjectPositionIn, int execute, float partialTicks) {
/* 2664 */     if (execute == 0 && movingObjectPositionIn.typeOfHit == RayTraceResult.Type.BLOCK) {
/*      */       
/* 2666 */       GlStateManager.enableBlend();
/* 2667 */       GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
/* 2668 */       GlStateManager.glLineWidth(2.0F);
/* 2669 */       GlStateManager.disableTexture2D();
/*      */       
/* 2671 */       if (Config.isShaders())
/*      */       {
/* 2673 */         Shaders.disableTexture2D();
/*      */       }
/*      */       
/* 2676 */       GlStateManager.depthMask(false);
/* 2677 */       BlockPos blockpos = movingObjectPositionIn.getBlockPos();
/* 2678 */       IBlockState iblockstate = this.theWorld.getBlockState(blockpos);
/*      */       
/* 2680 */       if (iblockstate.getMaterial() != Material.AIR && this.theWorld.getWorldBorder().contains(blockpos)) {
/*      */         
/* 2682 */         double d3 = player.lastTickPosX + (player.posX - player.lastTickPosX) * partialTicks;
/* 2683 */         double d4 = player.lastTickPosY + (player.posY - player.lastTickPosY) * partialTicks;
/* 2684 */         double d5 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * partialTicks;
/* 2685 */         drawSelectionBoundingBox(iblockstate.getSelectedBoundingBox((World)this.theWorld, blockpos).expandXyz(0.0020000000949949026D).offset(-d3, -d4, -d5), 0.0F, 0.0F, 0.0F, 0.4F);
/*      */       } 
/*      */       
/* 2688 */       GlStateManager.depthMask(true);
/* 2689 */       GlStateManager.enableTexture2D();
/*      */       
/* 2691 */       if (Config.isShaders())
/*      */       {
/* 2693 */         Shaders.enableTexture2D();
/*      */       }
/*      */       
/* 2696 */       GlStateManager.disableBlend();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawSelectionBoundingBox(AxisAlignedBB box, float red, float green, float blue, float alpha) {
/* 2702 */     drawBoundingBox(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, red, green, blue, alpha);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha) {
/* 2707 */     Tessellator tessellator = Tessellator.getInstance();
/* 2708 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 2709 */     bufferbuilder.begin(3, DefaultVertexFormats.POSITION_COLOR);
/* 2710 */     drawBoundingBox(bufferbuilder, minX, minY, minZ, maxX, maxY, maxZ, red, green, blue, alpha);
/* 2711 */     tessellator.draw();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void drawBoundingBox(BufferBuilder buffer, double minX, double minY, double minZ, double maxX, double maxY, double maxZ, float red, float green, float blue, float alpha) {
/* 2716 */     buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
/* 2717 */     buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
/* 2718 */     buffer.pos(maxX, minY, maxZ).color(red, green, blue, alpha).endVertex();
/* 2719 */     buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
/* 2720 */     buffer.pos(minX, minY, minZ).color(red, green, blue, alpha).endVertex();
/* 2721 */     buffer.pos(minX, maxY, minZ).color(red, green, blue, alpha).endVertex();
/* 2722 */     buffer.pos(maxX, maxY, minZ).color(red, green, blue, alpha).endVertex();
/* 2723 */     buffer.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
/* 2724 */     buffer.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
/* 2725 */     buffer.pos(minX, maxY, minZ).color(red, green, blue, 0.0F).endVertex();
/* 2726 */     buffer.pos(minX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
/* 2727 */     buffer.pos(minX, minY, maxZ).color(red, green, blue, alpha).endVertex();
/* 2728 */     buffer.pos(maxX, minY, maxZ).color(red, green, blue, 0.0F).endVertex();
/* 2729 */     buffer.pos(maxX, maxY, maxZ).color(red, green, blue, alpha).endVertex();
/* 2730 */     buffer.pos(maxX, maxY, minZ).color(red, green, blue, 0.0F).endVertex();
/* 2731 */     buffer.pos(maxX, minY, minZ).color(red, green, blue, alpha).endVertex();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderFilledBox(AxisAlignedBB p_189696_0_, float p_189696_1_, float p_189696_2_, float p_189696_3_, float p_189696_4_) {
/* 2736 */     renderFilledBox(p_189696_0_.minX, p_189696_0_.minY, p_189696_0_.minZ, p_189696_0_.maxX, p_189696_0_.maxY, p_189696_0_.maxZ, p_189696_1_, p_189696_2_, p_189696_3_, p_189696_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public static void renderFilledBox(double p_189695_0_, double p_189695_2_, double p_189695_4_, double p_189695_6_, double p_189695_8_, double p_189695_10_, float p_189695_12_, float p_189695_13_, float p_189695_14_, float p_189695_15_) {
/* 2741 */     Tessellator tessellator = Tessellator.getInstance();
/* 2742 */     BufferBuilder bufferbuilder = tessellator.getBuffer();
/* 2743 */     bufferbuilder.begin(5, DefaultVertexFormats.POSITION_COLOR);
/* 2744 */     addChainedFilledBoxVertices(bufferbuilder, p_189695_0_, p_189695_2_, p_189695_4_, p_189695_6_, p_189695_8_, p_189695_10_, p_189695_12_, p_189695_13_, p_189695_14_, p_189695_15_);
/* 2745 */     tessellator.draw();
/*      */   }
/*      */ 
/*      */   
/*      */   public static void addChainedFilledBoxVertices(BufferBuilder p_189693_0_, double p_189693_1_, double p_189693_3_, double p_189693_5_, double p_189693_7_, double p_189693_9_, double p_189693_11_, float p_189693_13_, float p_189693_14_, float p_189693_15_, float p_189693_16_) {
/* 2750 */     p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2751 */     p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2752 */     p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2753 */     p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2754 */     p_189693_0_.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2755 */     p_189693_0_.pos(p_189693_1_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2756 */     p_189693_0_.pos(p_189693_1_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2757 */     p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2758 */     p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2759 */     p_189693_0_.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2760 */     p_189693_0_.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2761 */     p_189693_0_.pos(p_189693_7_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2762 */     p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2763 */     p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2764 */     p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2765 */     p_189693_0_.pos(p_189693_7_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2766 */     p_189693_0_.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2767 */     p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2768 */     p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2769 */     p_189693_0_.pos(p_189693_7_, p_189693_3_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2770 */     p_189693_0_.pos(p_189693_1_, p_189693_3_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2771 */     p_189693_0_.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2772 */     p_189693_0_.pos(p_189693_7_, p_189693_3_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2773 */     p_189693_0_.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2774 */     p_189693_0_.pos(p_189693_1_, p_189693_9_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2775 */     p_189693_0_.pos(p_189693_1_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2776 */     p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_5_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2777 */     p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2778 */     p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/* 2779 */     p_189693_0_.pos(p_189693_7_, p_189693_9_, p_189693_11_).color(p_189693_13_, p_189693_14_, p_189693_15_, p_189693_16_).endVertex();
/*      */   }
/*      */ 
/*      */   
/*      */   private void markBlocksForUpdate(int p_184385_1_, int p_184385_2_, int p_184385_3_, int p_184385_4_, int p_184385_5_, int p_184385_6_, boolean p_184385_7_) {
/* 2784 */     this.viewFrustum.markBlocksForUpdate(p_184385_1_, p_184385_2_, p_184385_3_, p_184385_4_, p_184385_5_, p_184385_6_, p_184385_7_);
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyBlockUpdate(World worldIn, BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {
/* 2789 */     int k1 = pos.getX();
/* 2790 */     int l1 = pos.getY();
/* 2791 */     int i2 = pos.getZ();
/* 2792 */     markBlocksForUpdate(k1 - 1, l1 - 1, i2 - 1, k1 + 1, l1 + 1, i2 + 1, ((flags & 0x8) != 0));
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyLightSet(BlockPos pos) {
/* 2797 */     this.setLightUpdates.add(pos.toImmutable());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
/* 2805 */     markBlocksForUpdate(x1 - 1, y1 - 1, z1 - 1, x2 + 1, y2 + 1, z2 + 1, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public void playRecord(@Nullable SoundEvent soundIn, BlockPos pos) {
/* 2810 */     ISound isound = this.mapSoundPositions.get(pos);
/*      */     
/* 2812 */     if (isound != null) {
/*      */       
/* 2814 */       this.mc.getSoundHandler().stopSound(isound);
/* 2815 */       this.mapSoundPositions.remove(pos);
/*      */     } 
/*      */     
/* 2818 */     if (soundIn != null) {
/*      */       
/* 2820 */       ItemRecord itemrecord = ItemRecord.getBySound(soundIn);
/*      */       
/* 2822 */       if (itemrecord != null)
/*      */       {
/* 2824 */         this.mc.ingameGUI.setRecordPlayingMessage(itemrecord.getRecordNameLocal());
/*      */       }
/*      */       
/* 2827 */       PositionedSoundRecord positionedSoundRecord = PositionedSoundRecord.getRecordSoundRecord(soundIn, pos.getX(), pos.getY(), pos.getZ());
/* 2828 */       this.mapSoundPositions.put(pos, positionedSoundRecord);
/* 2829 */       this.mc.getSoundHandler().playSound((ISound)positionedSoundRecord);
/*      */     } 
/*      */     
/* 2832 */     func_193054_a((World)this.theWorld, pos, (soundIn != null));
/*      */   }
/*      */ 
/*      */   
/*      */   private void func_193054_a(World p_193054_1_, BlockPos p_193054_2_, boolean p_193054_3_) {
/* 2837 */     for (EntityLivingBase entitylivingbase : p_193054_1_.getEntitiesWithinAABB(EntityLivingBase.class, (new AxisAlignedBB(p_193054_2_)).expandXyz(3.0D)))
/*      */     {
/* 2839 */       entitylivingbase.func_191987_a(p_193054_2_, p_193054_3_);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSoundToAllNearExcept(@Nullable EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch) {}
/*      */ 
/*      */   
/*      */   public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
/* 2849 */     func_190570_a(particleID, ignoreRange, false, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_190570_a(int p_190570_1_, boolean p_190570_2_, boolean p_190570_3_, final double p_190570_4_, final double p_190570_6_, final double p_190570_8_, double p_190570_10_, double p_190570_12_, double p_190570_14_, int... p_190570_16_) {
/*      */     try {
/* 2856 */       func_190571_b(p_190570_1_, p_190570_2_, p_190570_3_, p_190570_4_, p_190570_6_, p_190570_8_, p_190570_10_, p_190570_12_, p_190570_14_, p_190570_16_);
/*      */     }
/* 2858 */     catch (Throwable throwable) {
/*      */       
/* 2860 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while adding particle");
/* 2861 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Particle being added");
/* 2862 */       crashreportcategory.addCrashSection("ID", Integer.valueOf(p_190570_1_));
/*      */       
/* 2864 */       if (p_190570_16_ != null)
/*      */       {
/* 2866 */         crashreportcategory.addCrashSection("Parameters", p_190570_16_);
/*      */       }
/*      */       
/* 2869 */       crashreportcategory.setDetail("Position", new ICrashReportDetail<String>()
/*      */           {
/*      */             public String call() throws Exception
/*      */             {
/* 2873 */               return CrashReportCategory.getCoordinateInfo(p_190570_4_, p_190570_6_, p_190570_8_);
/*      */             }
/*      */           });
/* 2876 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void spawnParticle(EnumParticleTypes particleIn, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
/* 2882 */     spawnParticle(particleIn.getParticleID(), particleIn.getShouldIgnoreRange(), xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private Particle spawnEntityFX(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
/* 2888 */     return func_190571_b(particleID, ignoreRange, false, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private Particle func_190571_b(int p_190571_1_, boolean p_190571_2_, boolean p_190571_3_, double p_190571_4_, double p_190571_6_, double p_190571_8_, double p_190571_10_, double p_190571_12_, double p_190571_14_, int... p_190571_16_) {
/* 2894 */     Entity entity = this.mc.getRenderViewEntity();
/*      */     
/* 2896 */     if (this.mc != null && entity != null && this.mc.effectRenderer != null) {
/*      */       
/* 2898 */       int k1 = func_190572_a(p_190571_3_);
/* 2899 */       double d3 = entity.posX - p_190571_4_;
/* 2900 */       double d4 = entity.posY - p_190571_6_;
/* 2901 */       double d5 = entity.posZ - p_190571_8_;
/*      */       
/* 2903 */       if (p_190571_1_ == EnumParticleTypes.EXPLOSION_HUGE.getParticleID() && !Config.isAnimatedExplosion())
/*      */       {
/* 2905 */         return null;
/*      */       }
/* 2907 */       if (p_190571_1_ == EnumParticleTypes.EXPLOSION_LARGE.getParticleID() && !Config.isAnimatedExplosion())
/*      */       {
/* 2909 */         return null;
/*      */       }
/* 2911 */       if (p_190571_1_ == EnumParticleTypes.EXPLOSION_NORMAL.getParticleID() && !Config.isAnimatedExplosion())
/*      */       {
/* 2913 */         return null;
/*      */       }
/* 2915 */       if (p_190571_1_ == EnumParticleTypes.SUSPENDED.getParticleID() && !Config.isWaterParticles())
/*      */       {
/* 2917 */         return null;
/*      */       }
/* 2919 */       if (p_190571_1_ == EnumParticleTypes.SUSPENDED_DEPTH.getParticleID() && !Config.isVoidParticles())
/*      */       {
/* 2921 */         return null;
/*      */       }
/* 2923 */       if (p_190571_1_ == EnumParticleTypes.SMOKE_NORMAL.getParticleID() && !Config.isAnimatedSmoke())
/*      */       {
/* 2925 */         return null;
/*      */       }
/* 2927 */       if (p_190571_1_ == EnumParticleTypes.SMOKE_LARGE.getParticleID() && !Config.isAnimatedSmoke())
/*      */       {
/* 2929 */         return null;
/*      */       }
/* 2931 */       if (p_190571_1_ == EnumParticleTypes.SPELL_MOB.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2933 */         return null;
/*      */       }
/* 2935 */       if (p_190571_1_ == EnumParticleTypes.SPELL_MOB_AMBIENT.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2937 */         return null;
/*      */       }
/* 2939 */       if (p_190571_1_ == EnumParticleTypes.SPELL.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2941 */         return null;
/*      */       }
/* 2943 */       if (p_190571_1_ == EnumParticleTypes.SPELL_INSTANT.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2945 */         return null;
/*      */       }
/* 2947 */       if (p_190571_1_ == EnumParticleTypes.SPELL_WITCH.getParticleID() && !Config.isPotionParticles())
/*      */       {
/* 2949 */         return null;
/*      */       }
/* 2951 */       if (p_190571_1_ == EnumParticleTypes.PORTAL.getParticleID() && !Config.isAnimatedPortal())
/*      */       {
/* 2953 */         return null;
/*      */       }
/* 2955 */       if (p_190571_1_ == EnumParticleTypes.FLAME.getParticleID() && !Config.isAnimatedFlame())
/*      */       {
/* 2957 */         return null;
/*      */       }
/* 2959 */       if (p_190571_1_ == EnumParticleTypes.REDSTONE.getParticleID() && !Config.isAnimatedRedstone())
/*      */       {
/* 2961 */         return null;
/*      */       }
/* 2963 */       if (p_190571_1_ == EnumParticleTypes.DRIP_WATER.getParticleID() && !Config.isDrippingWaterLava())
/*      */       {
/* 2965 */         return null;
/*      */       }
/* 2967 */       if (p_190571_1_ == EnumParticleTypes.DRIP_LAVA.getParticleID() && !Config.isDrippingWaterLava())
/*      */       {
/* 2969 */         return null;
/*      */       }
/* 2971 */       if (p_190571_1_ == EnumParticleTypes.FIREWORKS_SPARK.getParticleID() && !Config.isFireworkParticles())
/*      */       {
/* 2973 */         return null;
/*      */       }
/*      */ 
/*      */       
/* 2977 */       if (!p_190571_2_) {
/*      */         
/* 2979 */         double d6 = 1024.0D;
/*      */         
/* 2981 */         if (p_190571_1_ == EnumParticleTypes.CRIT.getParticleID())
/*      */         {
/* 2983 */           d6 = 38416.0D;
/*      */         }
/*      */         
/* 2986 */         if (d3 * d3 + d4 * d4 + d5 * d5 > d6)
/*      */         {
/* 2988 */           return null;
/*      */         }
/*      */         
/* 2991 */         if (k1 > 1)
/*      */         {
/* 2993 */           return null;
/*      */         }
/*      */       } 
/*      */       
/* 2997 */       Particle particle = this.mc.effectRenderer.spawnEffectParticle(p_190571_1_, p_190571_4_, p_190571_6_, p_190571_8_, p_190571_10_, p_190571_12_, p_190571_14_, p_190571_16_);
/*      */       
/* 2999 */       if (p_190571_1_ == EnumParticleTypes.WATER_BUBBLE.getParticleID())
/*      */       {
/* 3001 */         CustomColors.updateWaterFX(particle, (IBlockAccess)this.theWorld, p_190571_4_, p_190571_6_, p_190571_8_, this.renderEnv);
/*      */       }
/*      */       
/* 3004 */       if (p_190571_1_ == EnumParticleTypes.WATER_SPLASH.getParticleID())
/*      */       {
/* 3006 */         CustomColors.updateWaterFX(particle, (IBlockAccess)this.theWorld, p_190571_4_, p_190571_6_, p_190571_8_, this.renderEnv);
/*      */       }
/*      */       
/* 3009 */       if (p_190571_1_ == EnumParticleTypes.WATER_DROP.getParticleID())
/*      */       {
/* 3011 */         CustomColors.updateWaterFX(particle, (IBlockAccess)this.theWorld, p_190571_4_, p_190571_6_, p_190571_8_, this.renderEnv);
/*      */       }
/*      */       
/* 3014 */       if (p_190571_1_ == EnumParticleTypes.TOWN_AURA.getParticleID())
/*      */       {
/* 3016 */         CustomColors.updateMyceliumFX(particle);
/*      */       }
/*      */       
/* 3019 */       if (p_190571_1_ == EnumParticleTypes.PORTAL.getParticleID())
/*      */       {
/* 3021 */         CustomColors.updatePortalFX(particle);
/*      */       }
/*      */       
/* 3024 */       if (p_190571_1_ == EnumParticleTypes.REDSTONE.getParticleID())
/*      */       {
/* 3026 */         CustomColors.updateReddustFX(particle, (IBlockAccess)this.theWorld, p_190571_4_, p_190571_6_, p_190571_8_);
/*      */       }
/*      */       
/* 3029 */       return particle;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 3034 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private int func_190572_a(boolean p_190572_1_) {
/* 3040 */     int k1 = this.mc.gameSettings.particleSetting;
/*      */     
/* 3042 */     if (p_190572_1_ && k1 == 2 && this.theWorld.rand.nextInt(10) == 0)
/*      */     {
/* 3044 */       k1 = 1;
/*      */     }
/*      */     
/* 3047 */     if (k1 == 1 && this.theWorld.rand.nextInt(3) == 0)
/*      */     {
/* 3049 */       k1 = 2;
/*      */     }
/*      */     
/* 3052 */     return k1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityAdded(Entity entityIn) {
/* 3061 */     RandomMobs.entityLoaded(entityIn, (World)this.theWorld);
/*      */     
/* 3063 */     if (Config.isDynamicLights())
/*      */     {
/* 3065 */       DynamicLights.entityAdded(entityIn, this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onEntityRemoved(Entity entityIn) {
/* 3075 */     if (Config.isDynamicLights())
/*      */     {
/* 3077 */       DynamicLights.entityRemoved(entityIn, this);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void deleteAllDisplayLists() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void broadcastSound(int soundID, BlockPos pos, int data) {
/*      */     Entity entity;
/* 3090 */     switch (soundID) {
/*      */       
/*      */       case 1023:
/*      */       case 1028:
/*      */       case 1038:
/* 3095 */         entity = this.mc.getRenderViewEntity();
/*      */         
/* 3097 */         if (entity != null) {
/*      */           
/* 3099 */           double d3 = pos.getX() - entity.posX;
/* 3100 */           double d4 = pos.getY() - entity.posY;
/* 3101 */           double d5 = pos.getZ() - entity.posZ;
/* 3102 */           double d6 = Math.sqrt(d3 * d3 + d4 * d4 + d5 * d5);
/* 3103 */           double d7 = entity.posX;
/* 3104 */           double d8 = entity.posY;
/* 3105 */           double d9 = entity.posZ;
/*      */           
/* 3107 */           if (d6 > 0.0D) {
/*      */             
/* 3109 */             d7 += d3 / d6 * 2.0D;
/* 3110 */             d8 += d4 / d6 * 2.0D;
/* 3111 */             d9 += d5 / d6 * 2.0D;
/*      */           } 
/*      */           
/* 3114 */           if (soundID == 1023) {
/*      */             
/* 3116 */             this.theWorld.playSound(d7, d8, d9, SoundEvents.ENTITY_WITHER_SPAWN, SoundCategory.HOSTILE, 1.0F, 1.0F, false); break;
/*      */           } 
/* 3118 */           if (soundID == 1038) {
/*      */             
/* 3120 */             this.theWorld.playSound(d7, d8, d9, SoundEvents.field_193782_bq, SoundCategory.HOSTILE, 1.0F, 1.0F, false);
/*      */             
/*      */             break;
/*      */           } 
/* 3124 */           this.theWorld.playSound(d7, d8, d9, SoundEvents.ENTITY_ENDERDRAGON_DEATH, SoundCategory.HOSTILE, 5.0F, 1.0F, false);
/*      */         }  break;
/*      */     }  } public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data) { int k1, l1; double d3, d4, d5; int k2; Block block; double d6, d7, d8; int i2;
/*      */     float f5, f, f1;
/*      */     EnumParticleTypes enumparticletypes;
/*      */     int l2;
/*      */     double d9, d11, d13;
/*      */     int j3;
/*      */     double d25;
/*      */     int i3, j2;
/* 3134 */     Random random = this.theWorld.rand;
/*      */     
/* 3136 */     switch (type) {
/*      */       
/*      */       case 1000:
/* 3139 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_DISPENSER_DISPENSE, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1001:
/* 3143 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_DISPENSER_FAIL, SoundCategory.BLOCKS, 1.0F, 1.2F, false);
/*      */         break;
/*      */       
/*      */       case 1002:
/* 3147 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_DISPENSER_LAUNCH, SoundCategory.BLOCKS, 1.0F, 1.2F, false);
/*      */         break;
/*      */       
/*      */       case 1003:
/* 3151 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ENDEREYE_LAUNCH, SoundCategory.NEUTRAL, 1.0F, 1.2F, false);
/*      */         break;
/*      */       
/*      */       case 1004:
/* 3155 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_FIREWORK_SHOOT, SoundCategory.NEUTRAL, 1.0F, 1.2F, false);
/*      */         break;
/*      */       
/*      */       case 1005:
/* 3159 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_IRON_DOOR_OPEN, SoundCategory.BLOCKS, 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1006:
/* 3163 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_WOODEN_DOOR_OPEN, SoundCategory.BLOCKS, 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1007:
/* 3167 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_WOODEN_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1008:
/* 3171 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_FENCE_GATE_OPEN, SoundCategory.BLOCKS, 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1009:
/* 3175 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_FIRE_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (random.nextFloat() - random.nextFloat()) * 0.8F, false);
/*      */         break;
/*      */       
/*      */       case 1010:
/* 3179 */         if (Item.getItemById(data) instanceof ItemRecord) {
/*      */           
/* 3181 */           this.theWorld.playRecord(blockPosIn, ((ItemRecord)Item.getItemById(data)).getSound());
/*      */           
/*      */           break;
/*      */         } 
/* 3185 */         this.theWorld.playRecord(blockPosIn, null);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case 1011:
/* 3191 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_IRON_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1012:
/* 3195 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_WOODEN_DOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1013:
/* 3199 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_WOODEN_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1014:
/* 3203 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_FENCE_GATE_CLOSE, SoundCategory.BLOCKS, 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1015:
/* 3207 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_GHAST_WARN, SoundCategory.HOSTILE, 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1016:
/* 3211 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_GHAST_SHOOT, SoundCategory.HOSTILE, 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1017:
/* 3215 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ENDERDRAGON_SHOOT, SoundCategory.HOSTILE, 10.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1018:
/* 3219 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_BLAZE_SHOOT, SoundCategory.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1019:
/* 3223 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_ATTACK_DOOR_WOOD, SoundCategory.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1020:
/* 3227 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_ATTACK_IRON_DOOR, SoundCategory.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1021:
/* 3231 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_BREAK_DOOR_WOOD, SoundCategory.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1022:
/* 3235 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_WITHER_BREAK_BLOCK, SoundCategory.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1024:
/* 3239 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_WITHER_SHOOT, SoundCategory.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1025:
/* 3243 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_BAT_TAKEOFF, SoundCategory.NEUTRAL, 0.05F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1026:
/* 3247 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_INFECT, SoundCategory.HOSTILE, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1027:
/* 3251 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ZOMBIE_VILLAGER_CONVERTED, SoundCategory.NEUTRAL, 2.0F, (random.nextFloat() - random.nextFloat()) * 0.2F + 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1029:
/* 3255 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_ANVIL_DESTROY, SoundCategory.BLOCKS, 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1030:
/* 3259 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_ANVIL_USE, SoundCategory.BLOCKS, 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1031:
/* 3263 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.BLOCKS, 0.3F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1032:
/* 3267 */         this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.getMasterRecord(SoundEvents.BLOCK_PORTAL_TRAVEL, random.nextFloat() * 0.4F + 0.8F));
/*      */         break;
/*      */       
/*      */       case 1033:
/* 3271 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_CHORUS_FLOWER_GROW, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1034:
/* 3275 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_CHORUS_FLOWER_DEATH, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1035:
/* 3279 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 1.0F, 1.0F, false);
/*      */         break;
/*      */       
/*      */       case 1036:
/* 3283 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_IRON_TRAPDOOR_CLOSE, SoundCategory.BLOCKS, 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 1037:
/* 3287 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_IRON_TRAPDOOR_OPEN, SoundCategory.BLOCKS, 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 2000:
/* 3291 */         k1 = data % 3 - 1;
/* 3292 */         l1 = data / 3 % 3 - 1;
/* 3293 */         d3 = blockPosIn.getX() + k1 * 0.6D + 0.5D;
/* 3294 */         d4 = blockPosIn.getY() + 0.5D;
/* 3295 */         d5 = blockPosIn.getZ() + l1 * 0.6D + 0.5D;
/*      */         
/* 3297 */         for (k2 = 0; k2 < 10; k2++) {
/*      */           
/* 3299 */           double d18 = random.nextDouble() * 0.2D + 0.01D;
/* 3300 */           double d19 = d3 + k1 * 0.01D + (random.nextDouble() - 0.5D) * l1 * 0.5D;
/* 3301 */           double d20 = d4 + (random.nextDouble() - 0.5D) * 0.5D;
/* 3302 */           double d21 = d5 + l1 * 0.01D + (random.nextDouble() - 0.5D) * k1 * 0.5D;
/* 3303 */           double d22 = k1 * d18 + random.nextGaussian() * 0.01D;
/* 3304 */           double d23 = -0.03D + random.nextGaussian() * 0.01D;
/* 3305 */           double d24 = l1 * d18 + random.nextGaussian() * 0.01D;
/* 3306 */           spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d19, d20, d21, d22, d23, d24, new int[0]);
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2001:
/* 3312 */         block = Block.getBlockById(data & 0xFFF);
/*      */         
/* 3314 */         if (block.getDefaultState().getMaterial() != Material.AIR) {
/*      */           
/* 3316 */           SoundType soundtype = block.getSoundType();
/*      */           
/* 3318 */           if (Reflector.ForgeBlock_getSoundType.exists())
/*      */           {
/* 3320 */             soundtype = (SoundType)Reflector.call(block, Reflector.ForgeBlock_getSoundType, new Object[] { Block.getStateById(data), this.theWorld, blockPosIn, null });
/*      */           }
/*      */           
/* 3323 */           this.theWorld.playSound(blockPosIn, soundtype.getBreakSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F, false);
/*      */         } 
/*      */         
/* 3326 */         this.mc.effectRenderer.addBlockDestroyEffects(blockPosIn, block.getStateFromMeta(data >> 12 & 0xFF));
/*      */         break;
/*      */       
/*      */       case 2002:
/*      */       case 2007:
/* 3331 */         d6 = blockPosIn.getX();
/* 3332 */         d7 = blockPosIn.getY();
/* 3333 */         d8 = blockPosIn.getZ();
/*      */         
/* 3335 */         for (i2 = 0; i2 < 8; i2++) {
/*      */           
/* 3337 */           spawnParticle(EnumParticleTypes.ITEM_CRACK, d6, d7, d8, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D, new int[] { Item.getIdFromItem((Item)Items.SPLASH_POTION) });
/*      */         } 
/*      */         
/* 3340 */         f5 = (data >> 16 & 0xFF) / 255.0F;
/* 3341 */         f = (data >> 8 & 0xFF) / 255.0F;
/* 3342 */         f1 = (data >> 0 & 0xFF) / 255.0F;
/* 3343 */         enumparticletypes = (type == 2007) ? EnumParticleTypes.SPELL_INSTANT : EnumParticleTypes.SPELL;
/*      */         
/* 3345 */         for (l2 = 0; l2 < 100; l2++) {
/*      */           
/* 3347 */           double d10 = random.nextDouble() * 4.0D;
/* 3348 */           double d12 = random.nextDouble() * Math.PI * 2.0D;
/* 3349 */           double d14 = Math.cos(d12) * d10;
/* 3350 */           double d27 = 0.01D + random.nextDouble() * 0.5D;
/* 3351 */           double d29 = Math.sin(d12) * d10;
/* 3352 */           Particle particle1 = spawnEntityFX(enumparticletypes.getParticleID(), enumparticletypes.getShouldIgnoreRange(), d6 + d14 * 0.1D, d7 + 0.3D, d8 + d29 * 0.1D, d14, d27, d29, new int[0]);
/*      */           
/* 3354 */           if (particle1 != null) {
/*      */             
/* 3356 */             float f4 = 0.75F + random.nextFloat() * 0.25F;
/* 3357 */             particle1.setRBGColorF(f5 * f4, f * f4, f1 * f4);
/* 3358 */             particle1.multiplyVelocity((float)d10);
/*      */           } 
/*      */         } 
/*      */         
/* 3362 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_SPLASH_POTION_BREAK, SoundCategory.NEUTRAL, 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 2003:
/* 3366 */         d9 = blockPosIn.getX() + 0.5D;
/* 3367 */         d11 = blockPosIn.getY();
/* 3368 */         d13 = blockPosIn.getZ() + 0.5D;
/*      */         
/* 3370 */         for (j3 = 0; j3 < 8; j3++) {
/*      */           
/* 3372 */           spawnParticle(EnumParticleTypes.ITEM_CRACK, d9, d11, d13, random.nextGaussian() * 0.15D, random.nextDouble() * 0.2D, random.nextGaussian() * 0.15D, new int[] { Item.getIdFromItem(Items.ENDER_EYE) });
/*      */         } 
/*      */         
/* 3375 */         for (d25 = 0.0D; d25 < 6.283185307179586D; d25 += 0.15707963267948966D) {
/*      */           
/* 3377 */           spawnParticle(EnumParticleTypes.PORTAL, d9 + Math.cos(d25) * 5.0D, d11 - 0.4D, d13 + Math.sin(d25) * 5.0D, Math.cos(d25) * -5.0D, 0.0D, Math.sin(d25) * -5.0D, new int[0]);
/* 3378 */           spawnParticle(EnumParticleTypes.PORTAL, d9 + Math.cos(d25) * 5.0D, d11 - 0.4D, d13 + Math.sin(d25) * 5.0D, Math.cos(d25) * -7.0D, 0.0D, Math.sin(d25) * -7.0D, new int[0]);
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2004:
/* 3384 */         for (i3 = 0; i3 < 20; i3++) {
/*      */           
/* 3386 */           double d26 = blockPosIn.getX() + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 3387 */           double d28 = blockPosIn.getY() + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 3388 */           double d30 = blockPosIn.getZ() + 0.5D + (this.theWorld.rand.nextFloat() - 0.5D) * 2.0D;
/* 3389 */           this.theWorld.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d26, d28, d30, 0.0D, 0.0D, 0.0D, new int[0]);
/* 3390 */           this.theWorld.spawnParticle(EnumParticleTypes.FLAME, d26, d28, d30, 0.0D, 0.0D, 0.0D, new int[0]);
/*      */         } 
/*      */         return;
/*      */ 
/*      */       
/*      */       case 2005:
/* 3396 */         ItemDye.spawnBonemealParticles((World)this.theWorld, blockPosIn, data);
/*      */         break;
/*      */       
/*      */       case 2006:
/* 3400 */         for (j2 = 0; j2 < 200; j2++) {
/*      */           
/* 3402 */           float f2 = random.nextFloat() * 4.0F;
/* 3403 */           float f3 = random.nextFloat() * 6.2831855F;
/* 3404 */           double d15 = (MathHelper.cos(f3) * f2);
/* 3405 */           double d16 = 0.01D + random.nextDouble() * 0.5D;
/* 3406 */           double d17 = (MathHelper.sin(f3) * f2);
/* 3407 */           Particle particle = spawnEntityFX(EnumParticleTypes.DRAGON_BREATH.getParticleID(), false, blockPosIn.getX() + d15 * 0.1D, blockPosIn.getY() + 0.3D, blockPosIn.getZ() + d17 * 0.1D, d15, d16, d17, new int[0]);
/*      */           
/* 3409 */           if (particle != null)
/*      */           {
/* 3411 */             particle.multiplyVelocity(f2);
/*      */           }
/*      */         } 
/*      */         
/* 3415 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ENDERDRAGON_FIREBALL_EPLD, SoundCategory.HOSTILE, 1.0F, this.theWorld.rand.nextFloat() * 0.1F + 0.9F, false);
/*      */         break;
/*      */       
/*      */       case 3000:
/* 3419 */         this.theWorld.spawnParticle(EnumParticleTypes.EXPLOSION_HUGE, true, blockPosIn.getX() + 0.5D, blockPosIn.getY() + 0.5D, blockPosIn.getZ() + 0.5D, 0.0D, 0.0D, 0.0D, new int[0]);
/* 3420 */         this.theWorld.playSound(blockPosIn, SoundEvents.BLOCK_END_GATEWAY_SPAWN, SoundCategory.BLOCKS, 10.0F, (1.0F + (this.theWorld.rand.nextFloat() - this.theWorld.rand.nextFloat()) * 0.2F) * 0.7F, false);
/*      */         break;
/*      */       
/*      */       case 3001:
/* 3424 */         this.theWorld.playSound(blockPosIn, SoundEvents.ENTITY_ENDERDRAGON_GROWL, SoundCategory.HOSTILE, 64.0F, 0.8F + this.theWorld.rand.nextFloat() * 0.3F, false);
/*      */         break;
/*      */     }  }
/*      */ 
/*      */   
/*      */   public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
/* 3430 */     if (progress >= 0 && progress < 10) {
/*      */       
/* 3432 */       DestroyBlockProgress destroyblockprogress = this.damagedBlocks.get(Integer.valueOf(breakerId));
/*      */       
/* 3434 */       if (destroyblockprogress == null || destroyblockprogress.getPosition().getX() != pos.getX() || destroyblockprogress.getPosition().getY() != pos.getY() || destroyblockprogress.getPosition().getZ() != pos.getZ()) {
/*      */         
/* 3436 */         destroyblockprogress = new DestroyBlockProgress(breakerId, pos);
/* 3437 */         this.damagedBlocks.put(Integer.valueOf(breakerId), destroyblockprogress);
/*      */       } 
/*      */       
/* 3440 */       destroyblockprogress.setPartialBlockDamage(progress);
/* 3441 */       destroyblockprogress.setCloudUpdateTick(this.cloudTickCounter);
/*      */     }
/*      */     else {
/*      */       
/* 3445 */       this.damagedBlocks.remove(Integer.valueOf(breakerId));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasNoChunkUpdates() {
/* 3451 */     return (this.chunksToUpdate.isEmpty() && this.renderDispatcher.hasChunkUpdates());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDisplayListEntitiesDirty() {
/* 3456 */     this.displayListEntitiesDirty = true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void resetClouds() {
/* 3461 */     this.cloudRenderer.reset();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountRenderers() {
/* 3466 */     return this.viewFrustum.renderChunks.length;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountActiveRenderers() {
/* 3471 */     return this.renderInfos.size();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountEntitiesRendered() {
/* 3476 */     return this.countEntitiesRendered;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountTileEntitiesRendered() {
/* 3481 */     return this.countTileEntitiesRendered;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCountLoadedChunks() {
/* 3486 */     if (this.theWorld == null)
/*      */     {
/* 3488 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 3492 */     ChunkProviderClient chunkProviderClient = this.theWorld.getChunkProvider();
/*      */     
/* 3494 */     if (chunkProviderClient == null)
/*      */     {
/* 3496 */       return 0;
/*      */     }
/*      */ 
/*      */     
/* 3500 */     if (chunkProviderClient != this.worldChunkProvider) {
/*      */       
/* 3502 */       this.worldChunkProvider = (IChunkProvider)chunkProviderClient;
/* 3503 */       this.worldChunkProviderMap = (Long2ObjectMap<Chunk>)Reflector.getFieldValue(chunkProviderClient, Reflector.ChunkProviderClient_chunkMapping);
/*      */     } 
/*      */     
/* 3506 */     return (this.worldChunkProviderMap == null) ? 0 : this.worldChunkProviderMap.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCountChunksToUpdate() {
/* 3513 */     return this.chunksToUpdate.size();
/*      */   }
/*      */ 
/*      */   
/*      */   public RenderChunk getRenderChunk(BlockPos p_getRenderChunk_1_) {
/* 3518 */     return this.viewFrustum.getRenderChunk(p_getRenderChunk_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldClient getWorld() {
/* 3523 */     return this.theWorld;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateTileEntities(Collection<TileEntity> tileEntitiesToRemove, Collection<TileEntity> tileEntitiesToAdd) {
/* 3528 */     synchronized (this.setTileEntities) {
/*      */       
/* 3530 */       this.setTileEntities.removeAll(tileEntitiesToRemove);
/* 3531 */       this.setTileEntities.addAll(tileEntitiesToAdd);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private ContainerLocalRenderInformation allocateRenderInformation(RenderChunk p_allocateRenderInformation_1_, EnumFacing p_allocateRenderInformation_2_, int p_allocateRenderInformation_3_) {
/*      */     ContainerLocalRenderInformation renderglobal$containerlocalrenderinformation1;
/* 3539 */     if (renderInfoCache.isEmpty()) {
/*      */       
/* 3541 */       renderglobal$containerlocalrenderinformation1 = new ContainerLocalRenderInformation(p_allocateRenderInformation_1_, p_allocateRenderInformation_2_, p_allocateRenderInformation_3_);
/*      */     }
/*      */     else {
/*      */       
/* 3545 */       renderglobal$containerlocalrenderinformation1 = renderInfoCache.pollLast();
/* 3546 */       renderglobal$containerlocalrenderinformation1.initialize(p_allocateRenderInformation_1_, p_allocateRenderInformation_2_, p_allocateRenderInformation_3_);
/*      */     } 
/*      */     
/* 3549 */     renderglobal$containerlocalrenderinformation1.cacheable = true;
/* 3550 */     return renderglobal$containerlocalrenderinformation1;
/*      */   }
/*      */ 
/*      */   
/*      */   private void freeRenderInformation(ContainerLocalRenderInformation p_freeRenderInformation_1_) {
/* 3555 */     if (p_freeRenderInformation_1_.cacheable)
/*      */     {
/* 3557 */       renderInfoCache.add(p_freeRenderInformation_1_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static class ContainerLocalRenderInformation
/*      */   {
/*      */     RenderChunk renderChunk;
/*      */     EnumFacing facing;
/*      */     int setFacing;
/*      */     boolean cacheable = false;
/*      */     
/*      */     public ContainerLocalRenderInformation(RenderChunk p_i1_1_, EnumFacing p_i1_2_, int p_i1_3_) {
/* 3570 */       this.renderChunk = p_i1_1_;
/* 3571 */       this.facing = p_i1_2_;
/* 3572 */       this.setFacing = p_i1_3_;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setDirection(byte p_189561_1_, EnumFacing p_189561_2_) {
/* 3577 */       this.setFacing = this.setFacing | p_189561_1_ | 1 << p_189561_2_.ordinal();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean hasDirection(EnumFacing p_189560_1_) {
/* 3582 */       return ((this.setFacing & 1 << p_189560_1_.ordinal()) > 0);
/*      */     }
/*      */ 
/*      */     
/*      */     private void initialize(RenderChunk p_initialize_1_, EnumFacing p_initialize_2_, int p_initialize_3_) {
/* 3587 */       this.renderChunk = p_initialize_1_;
/* 3588 */       this.facing = p_initialize_2_;
/* 3589 */       this.setFacing = p_initialize_3_;
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public static void func_181561_a(AxisAlignedBB p_181561_0_) {
/* 3595 */     Tessellator tessellator = Tessellator.getInstance();
/* 3596 */     BufferBuilder worldrenderer = tessellator.getBuffer();
/* 3597 */     worldrenderer.begin(3, DefaultVertexFormats.POSITION);
/* 3598 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 3599 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 3600 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
/* 3601 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
/* 3602 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 3603 */     tessellator.draw();
/* 3604 */     worldrenderer.begin(3, DefaultVertexFormats.POSITION);
/* 3605 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 3606 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 3607 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
/* 3608 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
/* 3609 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 3610 */     tessellator.draw();
/* 3611 */     worldrenderer.begin(1, DefaultVertexFormats.POSITION);
/* 3612 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 3613 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 3614 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.minZ).endVertex();
/* 3615 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.minZ).endVertex();
/* 3616 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
/* 3617 */     worldrenderer.pos(p_181561_0_.maxX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
/* 3618 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.minY, p_181561_0_.maxZ).endVertex();
/* 3619 */     worldrenderer.pos(p_181561_0_.minX, p_181561_0_.maxY, p_181561_0_.maxZ).endVertex();
/* 3620 */     tessellator.draw();
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\RenderGlobal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */