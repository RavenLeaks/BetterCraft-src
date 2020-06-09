/*      */ package optifine;
/*      */ 
/*      */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*      */ import java.io.Reader;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Method;
/*      */ import java.util.Map;
/*      */ import java.util.Optional;
/*      */ import javax.vecmath.Matrix4f;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockProperties;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.gui.GuiMainMenu;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.model.ModelBanner;
/*      */ import net.minecraft.client.model.ModelBase;
/*      */ import net.minecraft.client.model.ModelBat;
/*      */ import net.minecraft.client.model.ModelBlaze;
/*      */ import net.minecraft.client.model.ModelBook;
/*      */ import net.minecraft.client.model.ModelChest;
/*      */ import net.minecraft.client.model.ModelDragon;
/*      */ import net.minecraft.client.model.ModelDragonHead;
/*      */ import net.minecraft.client.model.ModelEnderCrystal;
/*      */ import net.minecraft.client.model.ModelEnderMite;
/*      */ import net.minecraft.client.model.ModelEvokerFangs;
/*      */ import net.minecraft.client.model.ModelGhast;
/*      */ import net.minecraft.client.model.ModelGuardian;
/*      */ import net.minecraft.client.model.ModelHorse;
/*      */ import net.minecraft.client.model.ModelHumanoidHead;
/*      */ import net.minecraft.client.model.ModelLeashKnot;
/*      */ import net.minecraft.client.model.ModelMagmaCube;
/*      */ import net.minecraft.client.model.ModelOcelot;
/*      */ import net.minecraft.client.model.ModelRabbit;
/*      */ import net.minecraft.client.model.ModelRenderer;
/*      */ import net.minecraft.client.model.ModelShulker;
/*      */ import net.minecraft.client.model.ModelShulkerBullet;
/*      */ import net.minecraft.client.model.ModelSign;
/*      */ import net.minecraft.client.model.ModelSilverfish;
/*      */ import net.minecraft.client.model.ModelSkeletonHead;
/*      */ import net.minecraft.client.model.ModelSlime;
/*      */ import net.minecraft.client.model.ModelSquid;
/*      */ import net.minecraft.client.model.ModelVex;
/*      */ import net.minecraft.client.model.ModelWitch;
/*      */ import net.minecraft.client.model.ModelWither;
/*      */ import net.minecraft.client.model.ModelWolf;
/*      */ import net.minecraft.client.multiplayer.ChunkProviderClient;
/*      */ import net.minecraft.client.renderer.EntityRenderer;
/*      */ import net.minecraft.client.renderer.block.model.ItemOverrideList;
/*      */ import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
/*      */ import net.minecraft.client.renderer.block.model.ModelManager;
/*      */ import net.minecraft.client.renderer.block.model.ModelRotation;
/*      */ import net.minecraft.client.renderer.entity.RenderBoat;
/*      */ import net.minecraft.client.renderer.entity.RenderEnderCrystal;
/*      */ import net.minecraft.client.renderer.entity.RenderEvokerFangs;
/*      */ import net.minecraft.client.renderer.entity.RenderItemFrame;
/*      */ import net.minecraft.client.renderer.entity.RenderLeashKnot;
/*      */ import net.minecraft.client.renderer.entity.RenderLivingBase;
/*      */ import net.minecraft.client.renderer.entity.RenderManager;
/*      */ import net.minecraft.client.renderer.entity.RenderMinecart;
/*      */ import net.minecraft.client.renderer.entity.RenderShulkerBullet;
/*      */ import net.minecraft.client.renderer.entity.RenderWitherSkull;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityBannerRenderer;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityEnchantmentTableRenderer;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityEnderChestRenderer;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntityShulkerBoxRenderer;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntitySignRenderer;
/*      */ import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
/*      */ import net.minecraft.client.renderer.vertex.VertexFormatElement;
/*      */ import net.minecraft.client.resources.DefaultResourcePack;
/*      */ import net.minecraft.client.resources.IResourcePack;
/*      */ import net.minecraft.client.resources.LegacyV2Adapter;
/*      */ import net.minecraft.client.settings.KeyBinding;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.EnumCreatureType;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemArmor;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.BlockRenderLayer;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.ChunkPos;
/*      */ import net.minecraft.world.ChunkCache;
/*      */ import net.minecraft.world.IBlockAccess;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldProvider;
/*      */ import net.minecraft.world.biome.Biome;
/*      */ import net.minecraftforge.common.property.IUnlistedProperty;
/*      */ 
/*      */ 
/*      */ public class Reflector
/*      */ {
/*  101 */   private static boolean logForge = logEntry("*** Reflector Forge ***");
/*  102 */   public static ReflectorClass Attributes = new ReflectorClass("net.minecraftforge.client.model.Attributes");
/*  103 */   public static ReflectorField Attributes_DEFAULT_BAKED_FORMAT = new ReflectorField(Attributes, "DEFAULT_BAKED_FORMAT");
/*  104 */   public static ReflectorClass BetterFoliageClient = new ReflectorClass("mods.betterfoliage.client.BetterFoliageClient");
/*  105 */   public static ReflectorClass BlamingTransformer = new ReflectorClass("net.minecraftforge.fml.common.asm.transformers.BlamingTransformer");
/*  106 */   public static ReflectorMethod BlamingTransformer_onCrash = new ReflectorMethod(BlamingTransformer, "onCrash");
/*  107 */   public static ReflectorClass ChunkWatchEvent_UnWatch = new ReflectorClass("net.minecraftforge.event.world.ChunkWatchEvent$UnWatch");
/*  108 */   public static ReflectorConstructor ChunkWatchEvent_UnWatch_Constructor = new ReflectorConstructor(ChunkWatchEvent_UnWatch, new Class[] { ChunkPos.class, EntityPlayerMP.class });
/*  109 */   public static ReflectorClass CoreModManager = new ReflectorClass("net.minecraftforge.fml.relauncher.CoreModManager");
/*  110 */   public static ReflectorMethod CoreModManager_onCrash = new ReflectorMethod(CoreModManager, "onCrash");
/*  111 */   public static ReflectorClass DimensionManager = new ReflectorClass("net.minecraftforge.common.DimensionManager");
/*  112 */   public static ReflectorMethod DimensionManager_createProviderFor = new ReflectorMethod(DimensionManager, "createProviderFor");
/*  113 */   public static ReflectorMethod DimensionManager_getStaticDimensionIDs = new ReflectorMethod(DimensionManager, "getStaticDimensionIDs");
/*  114 */   public static ReflectorClass DrawScreenEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Pre");
/*  115 */   public static ReflectorConstructor DrawScreenEvent_Pre_Constructor = new ReflectorConstructor(DrawScreenEvent_Pre, new Class[] { GuiScreen.class, int.class, int.class, float.class });
/*  116 */   public static ReflectorClass DrawScreenEvent_Post = new ReflectorClass("net.minecraftforge.client.event.GuiScreenEvent$DrawScreenEvent$Post");
/*  117 */   public static ReflectorConstructor DrawScreenEvent_Post_Constructor = new ReflectorConstructor(DrawScreenEvent_Post, new Class[] { GuiScreen.class, int.class, int.class, float.class });
/*  118 */   public static ReflectorClass EntityViewRenderEvent_CameraSetup = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$CameraSetup");
/*  119 */   public static ReflectorConstructor EntityViewRenderEvent_CameraSetup_Constructor = new ReflectorConstructor(EntityViewRenderEvent_CameraSetup, new Class[] { EntityRenderer.class, Entity.class, IBlockState.class, double.class, float.class, float.class, float.class });
/*  120 */   public static ReflectorMethod EntityViewRenderEvent_CameraSetup_getRoll = new ReflectorMethod(EntityViewRenderEvent_CameraSetup, "getRoll");
/*  121 */   public static ReflectorMethod EntityViewRenderEvent_CameraSetup_getPitch = new ReflectorMethod(EntityViewRenderEvent_CameraSetup, "getPitch");
/*  122 */   public static ReflectorMethod EntityViewRenderEvent_CameraSetup_getYaw = new ReflectorMethod(EntityViewRenderEvent_CameraSetup, "getYaw");
/*  123 */   public static ReflectorClass EntityViewRenderEvent_FogColors = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$FogColors");
/*  124 */   public static ReflectorConstructor EntityViewRenderEvent_FogColors_Constructor = new ReflectorConstructor(EntityViewRenderEvent_FogColors, new Class[] { EntityRenderer.class, Entity.class, IBlockState.class, double.class, float.class, float.class, float.class });
/*  125 */   public static ReflectorMethod EntityViewRenderEvent_FogColors_getRed = new ReflectorMethod(EntityViewRenderEvent_FogColors, "getRed");
/*  126 */   public static ReflectorMethod EntityViewRenderEvent_FogColors_getGreen = new ReflectorMethod(EntityViewRenderEvent_FogColors, "getGreen");
/*  127 */   public static ReflectorMethod EntityViewRenderEvent_FogColors_getBlue = new ReflectorMethod(EntityViewRenderEvent_FogColors, "getBlue");
/*  128 */   public static ReflectorClass EntityViewRenderEvent_RenderFogEvent = new ReflectorClass("net.minecraftforge.client.event.EntityViewRenderEvent$RenderFogEvent");
/*  129 */   public static ReflectorConstructor EntityViewRenderEvent_RenderFogEvent_Constructor = new ReflectorConstructor(EntityViewRenderEvent_RenderFogEvent, new Class[] { EntityRenderer.class, Entity.class, IBlockState.class, double.class, int.class, float.class });
/*  130 */   public static ReflectorClass Event = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event");
/*  131 */   public static ReflectorMethod Event_isCanceled = new ReflectorMethod(Event, "isCanceled");
/*  132 */   public static ReflectorClass EventBus = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.EventBus");
/*  133 */   public static ReflectorMethod EventBus_post = new ReflectorMethod(EventBus, "post");
/*  134 */   public static ReflectorClass Event_Result = new ReflectorClass("net.minecraftforge.fml.common.eventhandler.Event$Result");
/*  135 */   public static ReflectorField Event_Result_DENY = new ReflectorField(Event_Result, "DENY");
/*  136 */   public static ReflectorField Event_Result_ALLOW = new ReflectorField(Event_Result, "ALLOW");
/*  137 */   public static ReflectorField Event_Result_DEFAULT = new ReflectorField(Event_Result, "DEFAULT");
/*  138 */   public static ReflectorClass ExtendedBlockState = new ReflectorClass("net.minecraftforge.common.property.ExtendedBlockState");
/*  139 */   public static ReflectorConstructor ExtendedBlockState_Constructor = new ReflectorConstructor(ExtendedBlockState, new Class[] { Block.class, IProperty[].class, IUnlistedProperty[].class });
/*  140 */   public static ReflectorClass FMLClientHandler = new ReflectorClass("net.minecraftforge.fml.client.FMLClientHandler");
/*  141 */   public static ReflectorMethod FMLClientHandler_instance = new ReflectorMethod(FMLClientHandler, "instance");
/*  142 */   public static ReflectorMethod FMLClientHandler_isLoading = new ReflectorMethod(FMLClientHandler, "isLoading");
/*  143 */   public static ReflectorMethod FMLClientHandler_trackBrokenTexture = new ReflectorMethod(FMLClientHandler, "trackBrokenTexture");
/*  144 */   public static ReflectorMethod FMLClientHandler_trackMissingTexture = new ReflectorMethod(FMLClientHandler, "trackMissingTexture");
/*  145 */   public static ReflectorClass FMLCommonHandler = new ReflectorClass("net.minecraftforge.fml.common.FMLCommonHandler");
/*  146 */   public static ReflectorMethod FMLCommonHandler_callFuture = new ReflectorMethod(FMLCommonHandler, "callFuture");
/*  147 */   public static ReflectorMethod FMLCommonHandler_enhanceCrashReport = new ReflectorMethod(FMLCommonHandler, "enhanceCrashReport");
/*  148 */   public static ReflectorMethod FMLCommonHandler_getBrandings = new ReflectorMethod(FMLCommonHandler, "getBrandings");
/*  149 */   public static ReflectorMethod FMLCommonHandler_handleServerAboutToStart = new ReflectorMethod(FMLCommonHandler, "handleServerAboutToStart");
/*  150 */   public static ReflectorMethod FMLCommonHandler_handleServerStarting = new ReflectorMethod(FMLCommonHandler, "handleServerStarting");
/*  151 */   public static ReflectorMethod FMLCommonHandler_instance = new ReflectorMethod(FMLCommonHandler, "instance");
/*  152 */   public static ReflectorClass ForgeBiome = new ReflectorClass(Biome.class);
/*  153 */   public static ReflectorMethod ForgeBiome_getWaterColorMultiplier = new ReflectorMethod(ForgeBiome, "getWaterColorMultiplier");
/*  154 */   public static ReflectorClass ForgeBlock = new ReflectorClass(Block.class);
/*  155 */   public static ReflectorMethod ForgeBlock_addDestroyEffects = new ReflectorMethod(ForgeBlock, "addDestroyEffects");
/*  156 */   public static ReflectorMethod ForgeBlock_addHitEffects = new ReflectorMethod(ForgeBlock, "addHitEffects");
/*  157 */   public static ReflectorMethod ForgeBlock_canCreatureSpawn = new ReflectorMethod(ForgeBlock, "canCreatureSpawn");
/*  158 */   public static ReflectorMethod ForgeBlock_canRenderInLayer = new ReflectorMethod(ForgeBlock, "canRenderInLayer", new Class[] { IBlockState.class, BlockRenderLayer.class });
/*  159 */   public static ReflectorMethod ForgeBlock_doesSideBlockRendering = new ReflectorMethod(ForgeBlock, "doesSideBlockRendering");
/*  160 */   public static ReflectorMethod ForgeBlock_getBedDirection = new ReflectorMethod(ForgeBlock, "getBedDirection");
/*  161 */   public static ReflectorMethod ForgeBlock_getExtendedState = new ReflectorMethod(ForgeBlock, "getExtendedState");
/*  162 */   public static ReflectorMethod ForgeBlock_getFogColor = new ReflectorMethod(ForgeBlock, "getFogColor");
/*  163 */   public static ReflectorMethod ForgeBlock_getLightOpacity = new ReflectorMethod(ForgeBlock, "getLightOpacity", new Class[] { IBlockState.class, IBlockAccess.class, BlockPos.class });
/*  164 */   public static ReflectorMethod ForgeBlock_getLightValue = new ReflectorMethod(ForgeBlock, "getLightValue", new Class[] { IBlockState.class, IBlockAccess.class, BlockPos.class });
/*  165 */   public static ReflectorMethod ForgeBlock_getSoundType = new ReflectorMethod(ForgeBlock, "getSoundType", new Class[] { IBlockState.class, World.class, BlockPos.class, Entity.class });
/*  166 */   public static ReflectorMethod ForgeBlock_hasTileEntity = new ReflectorMethod(ForgeBlock, "hasTileEntity", new Class[] { IBlockState.class });
/*  167 */   public static ReflectorMethod ForgeBlock_isAir = new ReflectorMethod(ForgeBlock, "isAir");
/*  168 */   public static ReflectorMethod ForgeBlock_isBed = new ReflectorMethod(ForgeBlock, "isBed");
/*  169 */   public static ReflectorMethod ForgeBlock_isBedFoot = new ReflectorMethod(ForgeBlock, "isBedFoot");
/*  170 */   public static ReflectorMethod ForgeBlock_isSideSolid = new ReflectorMethod(ForgeBlock, "isSideSolid");
/*  171 */   public static ReflectorClass ForgeIBlockProperties = new ReflectorClass(IBlockProperties.class);
/*  172 */   public static ReflectorMethod ForgeIBlockProperties_getLightValue2 = new ReflectorMethod(ForgeIBlockProperties, "getLightValue", new Class[] { IBlockAccess.class, BlockPos.class });
/*  173 */   public static ReflectorClass ForgeChunkCache = new ReflectorClass(ChunkCache.class);
/*  174 */   public static ReflectorMethod ForgeChunkCache_isSideSolid = new ReflectorMethod(ForgeChunkCache, "isSideSolid");
/*  175 */   public static ReflectorClass ForgeEntity = new ReflectorClass(Entity.class);
/*  176 */   public static ReflectorMethod ForgeEntity_canRiderInteract = new ReflectorMethod(ForgeEntity, "canRiderInteract");
/*  177 */   public static ReflectorField ForgeEntity_captureDrops = new ReflectorField(ForgeEntity, "captureDrops");
/*  178 */   public static ReflectorField ForgeEntity_capturedDrops = new ReflectorField(ForgeEntity, "capturedDrops");
/*  179 */   public static ReflectorMethod ForgeEntity_shouldRenderInPass = new ReflectorMethod(ForgeEntity, "shouldRenderInPass");
/*  180 */   public static ReflectorMethod ForgeEntity_shouldRiderSit = new ReflectorMethod(ForgeEntity, "shouldRiderSit");
/*  181 */   public static ReflectorClass ForgeEventFactory = new ReflectorClass("net.minecraftforge.event.ForgeEventFactory");
/*  182 */   public static ReflectorMethod ForgeEventFactory_canEntityDespawn = new ReflectorMethod(ForgeEventFactory, "canEntityDespawn");
/*  183 */   public static ReflectorMethod ForgeEventFactory_renderBlockOverlay = new ReflectorMethod(ForgeEventFactory, "renderBlockOverlay");
/*  184 */   public static ReflectorMethod ForgeEventFactory_renderFireOverlay = new ReflectorMethod(ForgeEventFactory, "renderFireOverlay");
/*  185 */   public static ReflectorMethod ForgeEventFactory_renderWaterOverlay = new ReflectorMethod(ForgeEventFactory, "renderWaterOverlay");
/*  186 */   public static ReflectorClass ForgeHooks = new ReflectorClass("net.minecraftforge.common.ForgeHooks");
/*  187 */   public static ReflectorMethod ForgeHooks_onLivingAttack = new ReflectorMethod(ForgeHooks, "onLivingAttack");
/*  188 */   public static ReflectorMethod ForgeHooks_onLivingDeath = new ReflectorMethod(ForgeHooks, "onLivingDeath");
/*  189 */   public static ReflectorMethod ForgeHooks_onLivingDrops = new ReflectorMethod(ForgeHooks, "onLivingDrops");
/*  190 */   public static ReflectorMethod ForgeHooks_onLivingFall = new ReflectorMethod(ForgeHooks, "onLivingFall");
/*  191 */   public static ReflectorMethod ForgeHooks_onLivingHurt = new ReflectorMethod(ForgeHooks, "onLivingHurt");
/*  192 */   public static ReflectorMethod ForgeHooks_onLivingJump = new ReflectorMethod(ForgeHooks, "onLivingJump");
/*  193 */   public static ReflectorMethod ForgeHooks_onLivingSetAttackTarget = new ReflectorMethod(ForgeHooks, "onLivingSetAttackTarget");
/*  194 */   public static ReflectorMethod ForgeHooks_onLivingUpdate = new ReflectorMethod(ForgeHooks, "onLivingUpdate");
/*  195 */   public static ReflectorClass ForgeHooksClient = new ReflectorClass("net.minecraftforge.client.ForgeHooksClient");
/*  196 */   public static ReflectorMethod ForgeHooksClient_applyTransform = new ReflectorMethod(ForgeHooksClient, "applyTransform", new Class[] { Matrix4f.class, Optional.class });
/*  197 */   public static ReflectorMethod ForgeHooksClient_applyUVLock = new ReflectorMethod(ForgeHooksClient, "applyUVLock");
/*  198 */   public static ReflectorMethod ForgeHooksClient_dispatchRenderLast = new ReflectorMethod(ForgeHooksClient, "dispatchRenderLast");
/*  199 */   public static ReflectorMethod ForgeHooksClient_drawScreen = new ReflectorMethod(ForgeHooksClient, "drawScreen");
/*  200 */   public static ReflectorMethod ForgeHooksClient_fillNormal = new ReflectorMethod(ForgeHooksClient, "fillNormal");
/*  201 */   public static ReflectorMethod ForgeHooksClient_handleCameraTransforms = new ReflectorMethod(ForgeHooksClient, "handleCameraTransforms");
/*  202 */   public static ReflectorMethod ForgeHooksClient_getArmorModel = new ReflectorMethod(ForgeHooksClient, "getArmorModel");
/*  203 */   public static ReflectorMethod ForgeHooksClient_getArmorTexture = new ReflectorMethod(ForgeHooksClient, "getArmorTexture");
/*  204 */   public static ReflectorMethod ForgeHooksClient_getFogDensity = new ReflectorMethod(ForgeHooksClient, "getFogDensity");
/*  205 */   public static ReflectorMethod ForgeHooksClient_getFOVModifier = new ReflectorMethod(ForgeHooksClient, "getFOVModifier");
/*  206 */   public static ReflectorMethod ForgeHooksClient_getMatrix = new ReflectorMethod(ForgeHooksClient, "getMatrix", new Class[] { ModelRotation.class });
/*  207 */   public static ReflectorMethod ForgeHooksClient_getOffsetFOV = new ReflectorMethod(ForgeHooksClient, "getOffsetFOV");
/*  208 */   public static ReflectorMethod ForgeHooksClient_loadEntityShader = new ReflectorMethod(ForgeHooksClient, "loadEntityShader");
/*  209 */   public static ReflectorMethod ForgeHooksClient_onDrawBlockHighlight = new ReflectorMethod(ForgeHooksClient, "onDrawBlockHighlight");
/*  210 */   public static ReflectorMethod ForgeHooksClient_onFogRender = new ReflectorMethod(ForgeHooksClient, "onFogRender");
/*  211 */   public static ReflectorMethod ForgeHooksClient_onScreenshot = new ReflectorMethod(ForgeHooksClient, "onScreenshot");
/*  212 */   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPre = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPre");
/*  213 */   public static ReflectorMethod ForgeHooksClient_onTextureStitchedPost = new ReflectorMethod(ForgeHooksClient, "onTextureStitchedPost");
/*  214 */   public static ReflectorMethod ForgeHooksClient_orientBedCamera = new ReflectorMethod(ForgeHooksClient, "orientBedCamera");
/*  215 */   public static ReflectorMethod ForgeHooksClient_putQuadColor = new ReflectorMethod(ForgeHooksClient, "putQuadColor");
/*  216 */   public static ReflectorMethod ForgeHooksClient_renderFirstPersonHand = new ReflectorMethod(ForgeHooksClient, "renderFirstPersonHand");
/*  217 */   public static ReflectorMethod ForgeHooksClient_renderMainMenu = new ReflectorMethod(ForgeHooksClient, "renderMainMenu");
/*  218 */   public static ReflectorMethod ForgeHooksClient_renderSpecificFirstPersonHand = new ReflectorMethod(ForgeHooksClient, "renderSpecificFirstPersonHand");
/*  219 */   public static ReflectorMethod ForgeHooksClient_setRenderLayer = new ReflectorMethod(ForgeHooksClient, "setRenderLayer");
/*  220 */   public static ReflectorMethod ForgeHooksClient_setRenderPass = new ReflectorMethod(ForgeHooksClient, "setRenderPass");
/*  221 */   public static ReflectorMethod ForgeHooksClient_shouldCauseReequipAnimation = new ReflectorMethod(ForgeHooksClient, "shouldCauseReequipAnimation");
/*  222 */   public static ReflectorMethod ForgeHooksClient_transform = new ReflectorMethod(ForgeHooksClient, "transform");
/*  223 */   public static ReflectorClass ForgeItem = new ReflectorClass(Item.class);
/*  224 */   public static ReflectorField ForgeItem_delegate = new ReflectorField(ForgeItem, "delegate");
/*  225 */   public static ReflectorMethod ForgeItem_getDurabilityForDisplay = new ReflectorMethod(ForgeItem, "getDurabilityForDisplay");
/*  226 */   public static ReflectorMethod ForgeItem_getRGBDurabilityForDisplay = new ReflectorMethod(ForgeItem, "getRGBDurabilityForDisplay");
/*  227 */   public static ReflectorMethod ForgeItem_isShield = new ReflectorMethod(ForgeItem, "isShield");
/*  228 */   public static ReflectorMethod ForgeItem_onEntitySwing = new ReflectorMethod(ForgeItem, "onEntitySwing");
/*  229 */   public static ReflectorMethod ForgeItem_shouldCauseReequipAnimation = new ReflectorMethod(ForgeItem, "shouldCauseReequipAnimation");
/*  230 */   public static ReflectorMethod ForgeItem_showDurabilityBar = new ReflectorMethod(ForgeItem, "showDurabilityBar");
/*  231 */   public static ReflectorClass ForgeItemOverrideList = new ReflectorClass(ItemOverrideList.class);
/*  232 */   public static ReflectorMethod ForgeItemOverrideList_handleItemState = new ReflectorMethod(ForgeItemOverrideList, "handleItemState");
/*  233 */   public static ReflectorClass ForgeItemArmor = new ReflectorClass(ItemArmor.class);
/*  234 */   public static ReflectorMethod ForgeItemArmor_hasOverlay = new ReflectorMethod(ForgeItemArmor, "hasOverlay");
/*  235 */   public static ReflectorClass ForgeKeyBinding = new ReflectorClass(KeyBinding.class);
/*  236 */   public static ReflectorMethod ForgeKeyBinding_setKeyConflictContext = new ReflectorMethod(ForgeKeyBinding, "setKeyConflictContext");
/*  237 */   public static ReflectorMethod ForgeKeyBinding_setKeyModifierAndCode = new ReflectorMethod(ForgeKeyBinding, "setKeyModifierAndCode");
/*  238 */   public static ReflectorMethod ForgeKeyBinding_getKeyModifier = new ReflectorMethod(ForgeKeyBinding, "getKeyModifier");
/*  239 */   public static ReflectorClass ForgeModContainer = new ReflectorClass("net.minecraftforge.common.ForgeModContainer");
/*  240 */   public static ReflectorField ForgeModContainer_forgeLightPipelineEnabled = new ReflectorField(ForgeModContainer, "forgeLightPipelineEnabled");
/*  241 */   public static ReflectorClass ForgeModelBlockDefinition = new ReflectorClass(ModelBlockDefinition.class);
/*  242 */   public static ReflectorMethod ForgeModelBlockDefinition_parseFromReader2 = new ReflectorMethod(ForgeModelBlockDefinition, "parseFromReader", new Class[] { Reader.class, ResourceLocation.class });
/*  243 */   public static ReflectorClass ForgePotion = new ReflectorClass(Potion.class);
/*  244 */   public static ReflectorMethod ForgePotion_shouldRenderHUD = ForgePotion.makeMethod("shouldRenderHUD");
/*  245 */   public static ReflectorMethod ForgePotion_renderHUDEffect = ForgePotion.makeMethod("renderHUDEffect");
/*  246 */   public static ReflectorClass ForgePotionEffect = new ReflectorClass(PotionEffect.class);
/*  247 */   public static ReflectorMethod ForgePotionEffect_isCurativeItem = new ReflectorMethod(ForgePotionEffect, "isCurativeItem");
/*  248 */   public static ReflectorClass ForgeTileEntity = new ReflectorClass(TileEntity.class);
/*  249 */   public static ReflectorMethod ForgeTileEntity_canRenderBreaking = new ReflectorMethod(ForgeTileEntity, "canRenderBreaking");
/*  250 */   public static ReflectorMethod ForgeTileEntity_getRenderBoundingBox = new ReflectorMethod(ForgeTileEntity, "getRenderBoundingBox");
/*  251 */   public static ReflectorMethod ForgeTileEntity_hasFastRenderer = new ReflectorMethod(ForgeTileEntity, "hasFastRenderer");
/*  252 */   public static ReflectorMethod ForgeTileEntity_shouldRenderInPass = new ReflectorMethod(ForgeTileEntity, "shouldRenderInPass");
/*  253 */   public static ReflectorClass ForgeVertexFormatElementEnumUseage = new ReflectorClass(VertexFormatElement.EnumUsage.class);
/*  254 */   public static ReflectorMethod ForgeVertexFormatElementEnumUseage_preDraw = new ReflectorMethod(ForgeVertexFormatElementEnumUseage, "preDraw");
/*  255 */   public static ReflectorMethod ForgeVertexFormatElementEnumUseage_postDraw = new ReflectorMethod(ForgeVertexFormatElementEnumUseage, "postDraw");
/*  256 */   public static ReflectorClass ForgeWorld = new ReflectorClass(World.class);
/*  257 */   public static ReflectorMethod ForgeWorld_countEntities = new ReflectorMethod(ForgeWorld, "countEntities", new Class[] { EnumCreatureType.class, boolean.class });
/*  258 */   public static ReflectorMethod ForgeWorld_getPerWorldStorage = new ReflectorMethod(ForgeWorld, "getPerWorldStorage");
/*  259 */   public static ReflectorMethod ForgeWorld_initCapabilities = new ReflectorMethod(ForgeWorld, "initCapabilities");
/*  260 */   public static ReflectorClass ForgeWorldProvider = new ReflectorClass(WorldProvider.class);
/*  261 */   public static ReflectorMethod ForgeWorldProvider_getCloudRenderer = new ReflectorMethod(ForgeWorldProvider, "getCloudRenderer");
/*  262 */   public static ReflectorMethod ForgeWorldProvider_getSkyRenderer = new ReflectorMethod(ForgeWorldProvider, "getSkyRenderer");
/*  263 */   public static ReflectorMethod ForgeWorldProvider_getWeatherRenderer = new ReflectorMethod(ForgeWorldProvider, "getWeatherRenderer");
/*  264 */   public static ReflectorMethod ForgeWorldProvider_getLightmapColors = new ReflectorMethod(ForgeWorldProvider, "getLightmapColors");
/*  265 */   public static ReflectorClass GuiModList = new ReflectorClass("net.minecraftforge.fml.client.GuiModList");
/*  266 */   public static ReflectorConstructor GuiModList_Constructor = new ReflectorConstructor(GuiModList, new Class[] { GuiScreen.class });
/*  267 */   public static ReflectorClass IExtendedBlockState = new ReflectorClass("net.minecraftforge.common.property.IExtendedBlockState");
/*  268 */   public static ReflectorMethod IExtendedBlockState_getClean = new ReflectorMethod(IExtendedBlockState, "getClean");
/*  269 */   public static ReflectorClass IModel = new ReflectorClass("net.minecraftforge.client.model.IModel");
/*  270 */   public static ReflectorMethod IModel_getTextures = new ReflectorMethod(IModel, "getTextures");
/*  271 */   public static ReflectorClass IRenderHandler = new ReflectorClass("net.minecraftforge.client.IRenderHandler");
/*  272 */   public static ReflectorMethod IRenderHandler_render = new ReflectorMethod(IRenderHandler, "render");
/*  273 */   public static ReflectorClass ItemModelMesherForge = new ReflectorClass("net.minecraftforge.client.ItemModelMesherForge");
/*  274 */   public static ReflectorConstructor ItemModelMesherForge_Constructor = new ReflectorConstructor(ItemModelMesherForge, new Class[] { ModelManager.class });
/*  275 */   public static ReflectorClass KeyConflictContext = new ReflectorClass("net.minecraftforge.client.settings.KeyConflictContext");
/*  276 */   public static ReflectorField KeyConflictContext_IN_GAME = new ReflectorField(KeyConflictContext, "IN_GAME");
/*  277 */   public static ReflectorClass KeyModifier = new ReflectorClass("net.minecraftforge.client.settings.KeyModifier");
/*  278 */   public static ReflectorMethod KeyModifier_valueFromString = new ReflectorMethod(KeyModifier, "valueFromString");
/*  279 */   public static ReflectorField KeyModifier_NONE = new ReflectorField(KeyModifier, "NONE");
/*  280 */   public static ReflectorClass Launch = new ReflectorClass("net.minecraft.launchwrapper.Launch");
/*  281 */   public static ReflectorField Launch_blackboard = new ReflectorField(Launch, "blackboard");
/*  282 */   public static ReflectorClass LightUtil = new ReflectorClass("net.minecraftforge.client.model.pipeline.LightUtil");
/*  283 */   public static ReflectorField LightUtil_itemConsumer = new ReflectorField(LightUtil, "itemConsumer");
/*  284 */   public static ReflectorMethod LightUtil_putBakedQuad = new ReflectorMethod(LightUtil, "putBakedQuad");
/*  285 */   public static ReflectorMethod LightUtil_renderQuadColor = new ReflectorMethod(LightUtil, "renderQuadColor");
/*  286 */   public static ReflectorField LightUtil_tessellator = new ReflectorField(LightUtil, "tessellator");
/*  287 */   public static ReflectorClass MinecraftForge = new ReflectorClass("net.minecraftforge.common.MinecraftForge");
/*  288 */   public static ReflectorField MinecraftForge_EVENT_BUS = new ReflectorField(MinecraftForge, "EVENT_BUS");
/*  289 */   public static ReflectorClass MinecraftForgeClient = new ReflectorClass("net.minecraftforge.client.MinecraftForgeClient");
/*  290 */   public static ReflectorMethod MinecraftForgeClient_getRenderPass = new ReflectorMethod(MinecraftForgeClient, "getRenderPass");
/*  291 */   public static ReflectorMethod MinecraftForgeClient_onRebuildChunk = new ReflectorMethod(MinecraftForgeClient, "onRebuildChunk");
/*  292 */   public static ReflectorClass ModelLoader = new ReflectorClass("net.minecraftforge.client.model.ModelLoader");
/*  293 */   public static ReflectorField ModelLoader_stateModels = new ReflectorField(ModelLoader, "stateModels");
/*  294 */   public static ReflectorMethod ModelLoader_onRegisterItems = new ReflectorMethod(ModelLoader, "onRegisterItems");
/*  295 */   public static ReflectorMethod ModelLoader_getInventoryVariant = new ReflectorMethod(ModelLoader, "getInventoryVariant");
/*  296 */   public static ReflectorClass ModelLoader_VanillaLoader = new ReflectorClass("net.minecraftforge.client.model.ModelLoader$VanillaLoader", true);
/*  297 */   public static ReflectorField ModelLoader_VanillaLoader_INSTANCE = new ReflectorField(ModelLoader_VanillaLoader, "INSTANCE", true);
/*  298 */   public static ReflectorMethod ModelLoader_VanillaLoader_loadModel = new ReflectorMethod(ModelLoader_VanillaLoader, "loadModel", null, true);
/*  299 */   public static ReflectorClass ModelLoaderRegistry = new ReflectorClass("net.minecraftforge.client.model.ModelLoaderRegistry", true);
/*  300 */   public static ReflectorField ModelLoaderRegistry_textures = new ReflectorField(ModelLoaderRegistry, "textures", true);
/*  301 */   public static ReflectorClass NotificationModUpdateScreen = new ReflectorClass("net.minecraftforge.client.gui.NotificationModUpdateScreen");
/*  302 */   public static ReflectorMethod NotificationModUpdateScreen_init = new ReflectorMethod(NotificationModUpdateScreen, "init");
/*  303 */   public static ReflectorClass RenderBlockOverlayEvent_OverlayType = new ReflectorClass("net.minecraftforge.client.event.RenderBlockOverlayEvent$OverlayType");
/*  304 */   public static ReflectorField RenderBlockOverlayEvent_OverlayType_BLOCK = new ReflectorField(RenderBlockOverlayEvent_OverlayType, "BLOCK");
/*  305 */   public static ReflectorClass RenderingRegistry = new ReflectorClass("net.minecraftforge.fml.client.registry.RenderingRegistry");
/*  306 */   public static ReflectorMethod RenderingRegistry_loadEntityRenderers = new ReflectorMethod(RenderingRegistry, "loadEntityRenderers", new Class[] { RenderManager.class, Map.class });
/*  307 */   public static ReflectorClass RenderItemInFrameEvent = new ReflectorClass("net.minecraftforge.client.event.RenderItemInFrameEvent");
/*  308 */   public static ReflectorConstructor RenderItemInFrameEvent_Constructor = new ReflectorConstructor(RenderItemInFrameEvent, new Class[] { EntityItemFrame.class, RenderItemFrame.class });
/*  309 */   public static ReflectorClass RenderLivingEvent_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Pre");
/*  310 */   public static ReflectorConstructor RenderLivingEvent_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Pre, new Class[] { EntityLivingBase.class, RenderLivingBase.class, float.class, double.class, double.class, double.class });
/*  311 */   public static ReflectorClass RenderLivingEvent_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Post");
/*  312 */   public static ReflectorConstructor RenderLivingEvent_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Post, new Class[] { EntityLivingBase.class, RenderLivingBase.class, float.class, double.class, double.class, double.class });
/*  313 */   public static ReflectorClass RenderLivingEvent_Specials_Pre = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Pre");
/*  314 */   public static ReflectorConstructor RenderLivingEvent_Specials_Pre_Constructor = new ReflectorConstructor(RenderLivingEvent_Specials_Pre, new Class[] { EntityLivingBase.class, RenderLivingBase.class, double.class, double.class, double.class });
/*  315 */   public static ReflectorClass RenderLivingEvent_Specials_Post = new ReflectorClass("net.minecraftforge.client.event.RenderLivingEvent$Specials$Post");
/*  316 */   public static ReflectorConstructor RenderLivingEvent_Specials_Post_Constructor = new ReflectorConstructor(RenderLivingEvent_Specials_Post, new Class[] { EntityLivingBase.class, RenderLivingBase.class, double.class, double.class, double.class });
/*  317 */   public static ReflectorClass ScreenshotEvent = new ReflectorClass("net.minecraftforge.client.event.ScreenshotEvent");
/*  318 */   public static ReflectorMethod ScreenshotEvent_getCancelMessage = new ReflectorMethod(ScreenshotEvent, "getCancelMessage");
/*  319 */   public static ReflectorMethod ScreenshotEvent_getScreenshotFile = new ReflectorMethod(ScreenshotEvent, "getScreenshotFile");
/*  320 */   public static ReflectorMethod ScreenshotEvent_getResultMessage = new ReflectorMethod(ScreenshotEvent, "getResultMessage");
/*  321 */   public static ReflectorClass SplashScreen = new ReflectorClass("net.minecraftforge.fml.client.SplashProgress");
/*  322 */   public static ReflectorClass WorldEvent_Load = new ReflectorClass("net.minecraftforge.event.world.WorldEvent$Load");
/*  323 */   public static ReflectorConstructor WorldEvent_Load_Constructor = new ReflectorConstructor(WorldEvent_Load, new Class[] { World.class });
/*  324 */   private static boolean logVanilla = logEntry("*** Reflector Vanilla ***");
/*  325 */   public static ReflectorClass ChunkProviderClient = new ReflectorClass(ChunkProviderClient.class);
/*  326 */   public static ReflectorField ChunkProviderClient_chunkMapping = new ReflectorField(ChunkProviderClient, Long2ObjectMap.class);
/*  327 */   public static ReflectorClass GuiMainMenu = new ReflectorClass(GuiMainMenu.class);
/*  328 */   public static ReflectorField GuiMainMenu_splashText = new ReflectorField(GuiMainMenu, String.class);
/*  329 */   public static ReflectorClass LegacyV2Adapter = new ReflectorClass(LegacyV2Adapter.class);
/*  330 */   public static ReflectorField LegacyV2Adapter_pack = new ReflectorField(LegacyV2Adapter, IResourcePack.class);
/*  331 */   public static ReflectorClass Minecraft = new ReflectorClass(Minecraft.class);
/*  332 */   public static ReflectorField Minecraft_defaultResourcePack = new ReflectorField(Minecraft, DefaultResourcePack.class);
/*  333 */   public static ReflectorField Minecraft_actionKeyF3 = new ReflectorField(new FieldLocatorActionKeyF3());
/*  334 */   public static ReflectorClass ModelHumanoidHead = new ReflectorClass(ModelHumanoidHead.class);
/*  335 */   public static ReflectorField ModelHumanoidHead_head = new ReflectorField(ModelHumanoidHead, ModelRenderer.class);
/*  336 */   public static ReflectorClass ModelBat = new ReflectorClass(ModelBat.class);
/*  337 */   public static ReflectorFields ModelBat_ModelRenderers = new ReflectorFields(ModelBat, ModelRenderer.class, 6);
/*  338 */   public static ReflectorClass ModelBlaze = new ReflectorClass(ModelBlaze.class);
/*  339 */   public static ReflectorField ModelBlaze_blazeHead = new ReflectorField(ModelBlaze, ModelRenderer.class);
/*  340 */   public static ReflectorField ModelBlaze_blazeSticks = new ReflectorField(ModelBlaze, ModelRenderer[].class);
/*  341 */   public static ReflectorClass ModelDragon = new ReflectorClass(ModelDragon.class);
/*  342 */   public static ReflectorFields ModelDragon_ModelRenderers = new ReflectorFields(ModelDragon, ModelRenderer.class, 12);
/*  343 */   public static ReflectorClass ModelEnderCrystal = new ReflectorClass(ModelEnderCrystal.class);
/*  344 */   public static ReflectorFields ModelEnderCrystal_ModelRenderers = new ReflectorFields(ModelEnderCrystal, ModelRenderer.class, 3);
/*  345 */   public static ReflectorClass RenderEnderCrystal = new ReflectorClass(RenderEnderCrystal.class);
/*  346 */   public static ReflectorField RenderEnderCrystal_modelEnderCrystal = new ReflectorField(RenderEnderCrystal, ModelBase.class, 0);
/*  347 */   public static ReflectorField RenderEnderCrystal_modelEnderCrystalNoBase = new ReflectorField(RenderEnderCrystal, ModelBase.class, 1);
/*  348 */   public static ReflectorClass ModelEnderMite = new ReflectorClass(ModelEnderMite.class);
/*  349 */   public static ReflectorField ModelEnderMite_bodyParts = new ReflectorField(ModelEnderMite, ModelRenderer[].class);
/*  350 */   public static ReflectorClass ModelEvokerFangs = new ReflectorClass(ModelEvokerFangs.class);
/*  351 */   public static ReflectorFields ModelEvokerFangs_ModelRenderers = new ReflectorFields(ModelEvokerFangs, ModelRenderer.class, 3);
/*  352 */   public static ReflectorClass ModelGhast = new ReflectorClass(ModelGhast.class);
/*  353 */   public static ReflectorField ModelGhast_body = new ReflectorField(ModelGhast, ModelRenderer.class);
/*  354 */   public static ReflectorField ModelGhast_tentacles = new ReflectorField(ModelGhast, ModelRenderer[].class);
/*  355 */   public static ReflectorClass ModelGuardian = new ReflectorClass(ModelGuardian.class);
/*  356 */   public static ReflectorField ModelGuardian_body = new ReflectorField(ModelGuardian, ModelRenderer.class, 0);
/*  357 */   public static ReflectorField ModelGuardian_eye = new ReflectorField(ModelGuardian, ModelRenderer.class, 1);
/*  358 */   public static ReflectorField ModelGuardian_spines = new ReflectorField(ModelGuardian, ModelRenderer[].class, 0);
/*  359 */   public static ReflectorField ModelGuardian_tail = new ReflectorField(ModelGuardian, ModelRenderer[].class, 1);
/*  360 */   public static ReflectorClass ModelDragonHead = new ReflectorClass(ModelDragonHead.class);
/*  361 */   public static ReflectorField ModelDragonHead_head = new ReflectorField(ModelDragonHead, ModelRenderer.class, 0);
/*  362 */   public static ReflectorField ModelDragonHead_jaw = new ReflectorField(ModelDragonHead, ModelRenderer.class, 1);
/*  363 */   public static ReflectorClass ModelHorse = new ReflectorClass(ModelHorse.class);
/*  364 */   public static ReflectorFields ModelHorse_ModelRenderers = new ReflectorFields(ModelHorse, ModelRenderer.class, 39);
/*  365 */   public static ReflectorClass RenderLeashKnot = new ReflectorClass(RenderLeashKnot.class);
/*  366 */   public static ReflectorField RenderLeashKnot_leashKnotModel = new ReflectorField(RenderLeashKnot, ModelLeashKnot.class);
/*  367 */   public static ReflectorClass ModelMagmaCube = new ReflectorClass(ModelMagmaCube.class);
/*  368 */   public static ReflectorField ModelMagmaCube_core = new ReflectorField(ModelMagmaCube, ModelRenderer.class);
/*  369 */   public static ReflectorField ModelMagmaCube_segments = new ReflectorField(ModelMagmaCube, ModelRenderer[].class);
/*  370 */   public static ReflectorClass ModelOcelot = new ReflectorClass(ModelOcelot.class);
/*  371 */   public static ReflectorFields ModelOcelot_ModelRenderers = new ReflectorFields(ModelOcelot, ModelRenderer.class, 8);
/*  372 */   public static ReflectorClass ModelRabbit = new ReflectorClass(ModelRabbit.class);
/*  373 */   public static ReflectorFields ModelRabbit_renderers = new ReflectorFields(ModelRabbit, ModelRenderer.class, 12);
/*  374 */   public static ReflectorClass ModelSilverfish = new ReflectorClass(ModelSilverfish.class);
/*  375 */   public static ReflectorField ModelSilverfish_bodyParts = new ReflectorField(ModelSilverfish, ModelRenderer[].class, 0);
/*  376 */   public static ReflectorField ModelSilverfish_wingParts = new ReflectorField(ModelSilverfish, ModelRenderer[].class, 1);
/*  377 */   public static ReflectorClass ModelSlime = new ReflectorClass(ModelSlime.class);
/*  378 */   public static ReflectorFields ModelSlime_ModelRenderers = new ReflectorFields(ModelSlime, ModelRenderer.class, 4);
/*  379 */   public static ReflectorClass ModelSquid = new ReflectorClass(ModelSquid.class);
/*  380 */   public static ReflectorField ModelSquid_body = new ReflectorField(ModelSquid, ModelRenderer.class);
/*  381 */   public static ReflectorField ModelSquid_tentacles = new ReflectorField(ModelSquid, ModelRenderer[].class);
/*  382 */   public static ReflectorClass ModelVex = new ReflectorClass(ModelVex.class);
/*  383 */   public static ReflectorField ModelVex_leftWing = new ReflectorField(ModelVex, ModelRenderer.class, 0);
/*  384 */   public static ReflectorField ModelVex_rightWing = new ReflectorField(ModelVex, ModelRenderer.class, 1);
/*  385 */   public static ReflectorClass ModelWitch = new ReflectorClass(ModelWitch.class);
/*  386 */   public static ReflectorField ModelWitch_mole = new ReflectorField(ModelWitch, ModelRenderer.class, 0);
/*  387 */   public static ReflectorField ModelWitch_hat = new ReflectorField(ModelWitch, ModelRenderer.class, 1);
/*  388 */   public static ReflectorClass ModelWither = new ReflectorClass(ModelWither.class);
/*  389 */   public static ReflectorField ModelWither_bodyParts = new ReflectorField(ModelWither, ModelRenderer[].class, 0);
/*  390 */   public static ReflectorField ModelWither_heads = new ReflectorField(ModelWither, ModelRenderer[].class, 1);
/*  391 */   public static ReflectorClass ModelWolf = new ReflectorClass(ModelWolf.class);
/*  392 */   public static ReflectorField ModelWolf_tail = new ReflectorField(ModelWolf, ModelRenderer.class, 6);
/*  393 */   public static ReflectorField ModelWolf_mane = new ReflectorField(ModelWolf, ModelRenderer.class, 7);
/*  394 */   public static ReflectorClass OptiFineClassTransformer = new ReflectorClass("optifine.OptiFineClassTransformer");
/*  395 */   public static ReflectorField OptiFineClassTransformer_instance = new ReflectorField(OptiFineClassTransformer, "instance");
/*  396 */   public static ReflectorMethod OptiFineClassTransformer_getOptiFineResource = new ReflectorMethod(OptiFineClassTransformer, "getOptiFineResource");
/*  397 */   public static ReflectorClass RenderBoat = new ReflectorClass(RenderBoat.class);
/*  398 */   public static ReflectorField RenderBoat_modelBoat = new ReflectorField(RenderBoat, ModelBase.class);
/*  399 */   public static ReflectorClass RenderEvokerFangs = new ReflectorClass(RenderEvokerFangs.class);
/*  400 */   public static ReflectorField RenderEvokerFangs_model = new ReflectorField(RenderEvokerFangs, ModelEvokerFangs.class);
/*  401 */   public static ReflectorClass RenderMinecart = new ReflectorClass(RenderMinecart.class);
/*  402 */   public static ReflectorField RenderMinecart_modelMinecart = new ReflectorField(RenderMinecart, ModelBase.class);
/*  403 */   public static ReflectorClass RenderShulkerBullet = new ReflectorClass(RenderShulkerBullet.class);
/*  404 */   public static ReflectorField RenderShulkerBullet_model = new ReflectorField(RenderShulkerBullet, ModelShulkerBullet.class);
/*  405 */   public static ReflectorClass RenderWitherSkull = new ReflectorClass(RenderWitherSkull.class);
/*  406 */   public static ReflectorField RenderWitherSkull_model = new ReflectorField(RenderWitherSkull, ModelSkeletonHead.class);
/*  407 */   public static ReflectorClass TileEntityBannerRenderer = new ReflectorClass(TileEntityBannerRenderer.class);
/*  408 */   public static ReflectorField TileEntityBannerRenderer_bannerModel = new ReflectorField(TileEntityBannerRenderer, ModelBanner.class);
/*  409 */   public static ReflectorClass TileEntityChestRenderer = new ReflectorClass(TileEntityChestRenderer.class);
/*  410 */   public static ReflectorField TileEntityChestRenderer_simpleChest = new ReflectorField(TileEntityChestRenderer, ModelChest.class, 0);
/*  411 */   public static ReflectorField TileEntityChestRenderer_largeChest = new ReflectorField(TileEntityChestRenderer, ModelChest.class, 1);
/*  412 */   public static ReflectorClass TileEntityEnchantmentTableRenderer = new ReflectorClass(TileEntityEnchantmentTableRenderer.class);
/*  413 */   public static ReflectorField TileEntityEnchantmentTableRenderer_modelBook = new ReflectorField(TileEntityEnchantmentTableRenderer, ModelBook.class);
/*  414 */   public static ReflectorClass TileEntityEnderChestRenderer = new ReflectorClass(TileEntityEnderChestRenderer.class);
/*  415 */   public static ReflectorField TileEntityEnderChestRenderer_modelChest = new ReflectorField(TileEntityEnderChestRenderer, ModelChest.class);
/*  416 */   public static ReflectorClass TileEntityShulkerBoxRenderer = new ReflectorClass(TileEntityShulkerBoxRenderer.class);
/*  417 */   public static ReflectorField TileEntityShulkerBoxRenderer_model = new ReflectorField(TileEntityShulkerBoxRenderer, ModelShulker.class);
/*  418 */   public static ReflectorClass TileEntitySignRenderer = new ReflectorClass(TileEntitySignRenderer.class);
/*  419 */   public static ReflectorField TileEntitySignRenderer_model = new ReflectorField(TileEntitySignRenderer, ModelSign.class);
/*  420 */   public static ReflectorClass TileEntitySkullRenderer = new ReflectorClass(TileEntitySkullRenderer.class);
/*  421 */   public static ReflectorField TileEntitySkullRenderer_dragonHead = new ReflectorField(TileEntitySkullRenderer, ModelDragonHead.class, 0);
/*  422 */   public static ReflectorField TileEntitySkullRenderer_skeletonHead = new ReflectorField(TileEntitySkullRenderer, ModelSkeletonHead.class, 0);
/*  423 */   public static ReflectorField TileEntitySkullRenderer_humanoidHead = new ReflectorField(TileEntitySkullRenderer, ModelSkeletonHead.class, 1);
/*      */ 
/*      */ 
/*      */   
/*      */   public static void callVoid(ReflectorMethod p_callVoid_0_, Object... p_callVoid_1_) {
/*      */     try {
/*  429 */       Method method = p_callVoid_0_.getTargetMethod();
/*      */       
/*  431 */       if (method == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  436 */       method.invoke(null, p_callVoid_1_);
/*      */     }
/*  438 */     catch (Throwable throwable) {
/*      */       
/*  440 */       handleException(throwable, null, p_callVoid_0_, p_callVoid_1_);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean callBoolean(ReflectorMethod p_callBoolean_0_, Object... p_callBoolean_1_) {
/*      */     try {
/*  448 */       Method method = p_callBoolean_0_.getTargetMethod();
/*      */       
/*  450 */       if (method == null)
/*      */       {
/*  452 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  456 */       Boolean obool = (Boolean)method.invoke(null, p_callBoolean_1_);
/*  457 */       return obool.booleanValue();
/*      */     
/*      */     }
/*  460 */     catch (Throwable throwable) {
/*      */       
/*  462 */       handleException(throwable, null, p_callBoolean_0_, p_callBoolean_1_);
/*  463 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int callInt(ReflectorMethod p_callInt_0_, Object... p_callInt_1_) {
/*      */     try {
/*  471 */       Method method = p_callInt_0_.getTargetMethod();
/*      */       
/*  473 */       if (method == null)
/*      */       {
/*  475 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*  479 */       Integer integer = (Integer)method.invoke(null, p_callInt_1_);
/*  480 */       return integer.intValue();
/*      */     
/*      */     }
/*  483 */     catch (Throwable throwable) {
/*      */       
/*  485 */       handleException(throwable, null, p_callInt_0_, p_callInt_1_);
/*  486 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static float callFloat(ReflectorMethod p_callFloat_0_, Object... p_callFloat_1_) {
/*      */     try {
/*  494 */       Method method = p_callFloat_0_.getTargetMethod();
/*      */       
/*  496 */       if (method == null)
/*      */       {
/*  498 */         return 0.0F;
/*      */       }
/*      */ 
/*      */       
/*  502 */       Float f = (Float)method.invoke(null, p_callFloat_1_);
/*  503 */       return f.floatValue();
/*      */     
/*      */     }
/*  506 */     catch (Throwable throwable) {
/*      */       
/*  508 */       handleException(throwable, null, p_callFloat_0_, p_callFloat_1_);
/*  509 */       return 0.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static double callDouble(ReflectorMethod p_callDouble_0_, Object... p_callDouble_1_) {
/*      */     try {
/*  517 */       Method method = p_callDouble_0_.getTargetMethod();
/*      */       
/*  519 */       if (method == null)
/*      */       {
/*  521 */         return 0.0D;
/*      */       }
/*      */ 
/*      */       
/*  525 */       Double d0 = (Double)method.invoke(null, p_callDouble_1_);
/*  526 */       return d0.doubleValue();
/*      */     
/*      */     }
/*  529 */     catch (Throwable throwable) {
/*      */       
/*  531 */       handleException(throwable, null, p_callDouble_0_, p_callDouble_1_);
/*  532 */       return 0.0D;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String callString(ReflectorMethod p_callString_0_, Object... p_callString_1_) {
/*      */     try {
/*  540 */       Method method = p_callString_0_.getTargetMethod();
/*      */       
/*  542 */       if (method == null)
/*      */       {
/*  544 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  548 */       String s = (String)method.invoke(null, p_callString_1_);
/*  549 */       return s;
/*      */     
/*      */     }
/*  552 */     catch (Throwable throwable) {
/*      */       
/*  554 */       handleException(throwable, null, p_callString_0_, p_callString_1_);
/*  555 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object call(ReflectorMethod p_call_0_, Object... p_call_1_) {
/*      */     try {
/*  563 */       Method method = p_call_0_.getTargetMethod();
/*      */       
/*  565 */       if (method == null)
/*      */       {
/*  567 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  571 */       Object object = method.invoke(null, p_call_1_);
/*  572 */       return object;
/*      */     
/*      */     }
/*  575 */     catch (Throwable throwable) {
/*      */       
/*  577 */       handleException(throwable, null, p_call_0_, p_call_1_);
/*  578 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void callVoid(Object p_callVoid_0_, ReflectorMethod p_callVoid_1_, Object... p_callVoid_2_) {
/*      */     try {
/*  586 */       if (p_callVoid_0_ == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  591 */       Method method = p_callVoid_1_.getTargetMethod();
/*      */       
/*  593 */       if (method == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  598 */       method.invoke(p_callVoid_0_, p_callVoid_2_);
/*      */     }
/*  600 */     catch (Throwable throwable) {
/*      */       
/*  602 */       handleException(throwable, p_callVoid_0_, p_callVoid_1_, p_callVoid_2_);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean callBoolean(Object p_callBoolean_0_, ReflectorMethod p_callBoolean_1_, Object... p_callBoolean_2_) {
/*      */     try {
/*  610 */       Method method = p_callBoolean_1_.getTargetMethod();
/*      */       
/*  612 */       if (method == null)
/*      */       {
/*  614 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  618 */       Boolean obool = (Boolean)method.invoke(p_callBoolean_0_, p_callBoolean_2_);
/*  619 */       return obool.booleanValue();
/*      */     
/*      */     }
/*  622 */     catch (Throwable throwable) {
/*      */       
/*  624 */       handleException(throwable, p_callBoolean_0_, p_callBoolean_1_, p_callBoolean_2_);
/*  625 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static int callInt(Object p_callInt_0_, ReflectorMethod p_callInt_1_, Object... p_callInt_2_) {
/*      */     try {
/*  633 */       Method method = p_callInt_1_.getTargetMethod();
/*      */       
/*  635 */       if (method == null)
/*      */       {
/*  637 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*  641 */       Integer integer = (Integer)method.invoke(p_callInt_0_, p_callInt_2_);
/*  642 */       return integer.intValue();
/*      */     
/*      */     }
/*  645 */     catch (Throwable throwable) {
/*      */       
/*  647 */       handleException(throwable, p_callInt_0_, p_callInt_1_, p_callInt_2_);
/*  648 */       return 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static float callFloat(Object p_callFloat_0_, ReflectorMethod p_callFloat_1_, Object... p_callFloat_2_) {
/*      */     try {
/*  656 */       Method method = p_callFloat_1_.getTargetMethod();
/*      */       
/*  658 */       if (method == null)
/*      */       {
/*  660 */         return 0.0F;
/*      */       }
/*      */ 
/*      */       
/*  664 */       Float f = (Float)method.invoke(p_callFloat_0_, p_callFloat_2_);
/*  665 */       return f.floatValue();
/*      */     
/*      */     }
/*  668 */     catch (Throwable throwable) {
/*      */       
/*  670 */       handleException(throwable, p_callFloat_0_, p_callFloat_1_, p_callFloat_2_);
/*  671 */       return 0.0F;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static double callDouble(Object p_callDouble_0_, ReflectorMethod p_callDouble_1_, Object... p_callDouble_2_) {
/*      */     try {
/*  679 */       Method method = p_callDouble_1_.getTargetMethod();
/*      */       
/*  681 */       if (method == null)
/*      */       {
/*  683 */         return 0.0D;
/*      */       }
/*      */ 
/*      */       
/*  687 */       Double d0 = (Double)method.invoke(p_callDouble_0_, p_callDouble_2_);
/*  688 */       return d0.doubleValue();
/*      */     
/*      */     }
/*  691 */     catch (Throwable throwable) {
/*      */       
/*  693 */       handleException(throwable, p_callDouble_0_, p_callDouble_1_, p_callDouble_2_);
/*  694 */       return 0.0D;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static String callString(Object p_callString_0_, ReflectorMethod p_callString_1_, Object... p_callString_2_) {
/*      */     try {
/*  702 */       Method method = p_callString_1_.getTargetMethod();
/*      */       
/*  704 */       if (method == null)
/*      */       {
/*  706 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  710 */       String s = (String)method.invoke(p_callString_0_, p_callString_2_);
/*  711 */       return s;
/*      */     
/*      */     }
/*  714 */     catch (Throwable throwable) {
/*      */       
/*  716 */       handleException(throwable, p_callString_0_, p_callString_1_, p_callString_2_);
/*  717 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object call(Object p_call_0_, ReflectorMethod p_call_1_, Object... p_call_2_) {
/*      */     try {
/*  725 */       Method method = p_call_1_.getTargetMethod();
/*      */       
/*  727 */       if (method == null)
/*      */       {
/*  729 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  733 */       Object object = method.invoke(p_call_0_, p_call_2_);
/*  734 */       return object;
/*      */     
/*      */     }
/*  737 */     catch (Throwable throwable) {
/*      */       
/*  739 */       handleException(throwable, p_call_0_, p_call_1_, p_call_2_);
/*  740 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object getFieldValue(ReflectorField p_getFieldValue_0_) {
/*  746 */     return getFieldValue((Object)null, p_getFieldValue_0_);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object getFieldValue(Object p_getFieldValue_0_, ReflectorField p_getFieldValue_1_) {
/*      */     try {
/*  753 */       Field field = p_getFieldValue_1_.getTargetField();
/*      */       
/*  755 */       if (field == null)
/*      */       {
/*  757 */         return null;
/*      */       }
/*      */ 
/*      */       
/*  761 */       Object object = field.get(p_getFieldValue_0_);
/*  762 */       return object;
/*      */     
/*      */     }
/*  765 */     catch (Throwable throwable) {
/*      */       
/*  767 */       throwable.printStackTrace();
/*  768 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object getFieldValue(ReflectorFields p_getFieldValue_0_, int p_getFieldValue_1_) {
/*  774 */     ReflectorField reflectorfield = p_getFieldValue_0_.getReflectorField(p_getFieldValue_1_);
/*  775 */     return (reflectorfield == null) ? null : getFieldValue(reflectorfield);
/*      */   }
/*      */ 
/*      */   
/*      */   public static Object getFieldValue(Object p_getFieldValue_0_, ReflectorFields p_getFieldValue_1_, int p_getFieldValue_2_) {
/*  780 */     ReflectorField reflectorfield = p_getFieldValue_1_.getReflectorField(p_getFieldValue_2_);
/*  781 */     return (reflectorfield == null) ? null : getFieldValue(p_getFieldValue_0_, reflectorfield);
/*      */   }
/*      */ 
/*      */   
/*      */   public static float getFieldValueFloat(Object p_getFieldValueFloat_0_, ReflectorField p_getFieldValueFloat_1_, float p_getFieldValueFloat_2_) {
/*  786 */     Object object = getFieldValue(p_getFieldValueFloat_0_, p_getFieldValueFloat_1_);
/*      */     
/*  788 */     if (!(object instanceof Float))
/*      */     {
/*  790 */       return p_getFieldValueFloat_2_;
/*      */     }
/*      */ 
/*      */     
/*  794 */     Float f = (Float)object;
/*  795 */     return f.floatValue();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean setFieldValue(ReflectorField p_setFieldValue_0_, Object p_setFieldValue_1_) {
/*  801 */     return setFieldValue(null, p_setFieldValue_0_, p_setFieldValue_1_);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean setFieldValue(Object p_setFieldValue_0_, ReflectorField p_setFieldValue_1_, Object p_setFieldValue_2_) {
/*      */     try {
/*  808 */       Field field = p_setFieldValue_1_.getTargetField();
/*      */       
/*  810 */       if (field == null)
/*      */       {
/*  812 */         return false;
/*      */       }
/*      */ 
/*      */       
/*  816 */       field.set(p_setFieldValue_0_, p_setFieldValue_2_);
/*  817 */       return true;
/*      */     
/*      */     }
/*  820 */     catch (Throwable throwable) {
/*      */       
/*  822 */       throwable.printStackTrace();
/*  823 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean postForgeBusEvent(ReflectorConstructor p_postForgeBusEvent_0_, Object... p_postForgeBusEvent_1_) {
/*  829 */     Object object = newInstance(p_postForgeBusEvent_0_, p_postForgeBusEvent_1_);
/*  830 */     return (object == null) ? false : postForgeBusEvent(object);
/*      */   }
/*      */ 
/*      */   
/*      */   public static boolean postForgeBusEvent(Object p_postForgeBusEvent_0_) {
/*  835 */     if (p_postForgeBusEvent_0_ == null)
/*      */     {
/*  837 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  841 */     Object object = getFieldValue(MinecraftForge_EVENT_BUS);
/*      */     
/*  843 */     if (object == null)
/*      */     {
/*  845 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  849 */     Object object1 = call(object, EventBus_post, new Object[] { p_postForgeBusEvent_0_ });
/*      */     
/*  851 */     if (!(object1 instanceof Boolean))
/*      */     {
/*  853 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  857 */     Boolean obool = (Boolean)object1;
/*  858 */     return obool.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Object newInstance(ReflectorConstructor p_newInstance_0_, Object... p_newInstance_1_) {
/*  866 */     Constructor constructor = p_newInstance_0_.getTargetConstructor();
/*      */     
/*  868 */     if (constructor == null)
/*      */     {
/*  870 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  876 */       Object object = constructor.newInstance(p_newInstance_1_);
/*  877 */       return object;
/*      */     }
/*  879 */     catch (Throwable throwable) {
/*      */       
/*  881 */       handleException(throwable, p_newInstance_0_, p_newInstance_1_);
/*  882 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean matchesTypes(Class[] p_matchesTypes_0_, Class[] p_matchesTypes_1_) {
/*  889 */     if (p_matchesTypes_0_.length != p_matchesTypes_1_.length)
/*      */     {
/*  891 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  895 */     for (int i = 0; i < p_matchesTypes_1_.length; i++) {
/*      */       
/*  897 */       Class oclass = p_matchesTypes_0_[i];
/*  898 */       Class oclass1 = p_matchesTypes_1_[i];
/*      */       
/*  900 */       if (oclass != oclass1)
/*      */       {
/*  902 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  906 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void dbgCall(boolean p_dbgCall_0_, String p_dbgCall_1_, ReflectorMethod p_dbgCall_2_, Object[] p_dbgCall_3_, Object p_dbgCall_4_) {
/*  912 */     String s = p_dbgCall_2_.getTargetMethod().getDeclaringClass().getName();
/*  913 */     String s1 = p_dbgCall_2_.getTargetMethod().getName();
/*  914 */     String s2 = "";
/*      */     
/*  916 */     if (p_dbgCall_0_)
/*      */     {
/*  918 */       s2 = " static";
/*      */     }
/*      */     
/*  921 */     Config.dbg(String.valueOf(p_dbgCall_1_) + s2 + " " + s + "." + s1 + "(" + Config.arrayToString(p_dbgCall_3_) + ") => " + p_dbgCall_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void dbgCallVoid(boolean p_dbgCallVoid_0_, String p_dbgCallVoid_1_, ReflectorMethod p_dbgCallVoid_2_, Object[] p_dbgCallVoid_3_) {
/*  926 */     String s = p_dbgCallVoid_2_.getTargetMethod().getDeclaringClass().getName();
/*  927 */     String s1 = p_dbgCallVoid_2_.getTargetMethod().getName();
/*  928 */     String s2 = "";
/*      */     
/*  930 */     if (p_dbgCallVoid_0_)
/*      */     {
/*  932 */       s2 = " static";
/*      */     }
/*      */     
/*  935 */     Config.dbg(String.valueOf(p_dbgCallVoid_1_) + s2 + " " + s + "." + s1 + "(" + Config.arrayToString(p_dbgCallVoid_3_) + ")");
/*      */   }
/*      */ 
/*      */   
/*      */   private static void dbgFieldValue(boolean p_dbgFieldValue_0_, String p_dbgFieldValue_1_, ReflectorField p_dbgFieldValue_2_, Object p_dbgFieldValue_3_) {
/*  940 */     String s = p_dbgFieldValue_2_.getTargetField().getDeclaringClass().getName();
/*  941 */     String s1 = p_dbgFieldValue_2_.getTargetField().getName();
/*  942 */     String s2 = "";
/*      */     
/*  944 */     if (p_dbgFieldValue_0_)
/*      */     {
/*  946 */       s2 = " static";
/*      */     }
/*      */     
/*  949 */     Config.dbg(String.valueOf(p_dbgFieldValue_1_) + s2 + " " + s + "." + s1 + " => " + p_dbgFieldValue_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   private static void handleException(Throwable p_handleException_0_, Object p_handleException_1_, ReflectorMethod p_handleException_2_, Object[] p_handleException_3_) {
/*  954 */     if (p_handleException_0_ instanceof java.lang.reflect.InvocationTargetException) {
/*      */       
/*  956 */       Throwable throwable = p_handleException_0_.getCause();
/*      */       
/*  958 */       if (throwable instanceof RuntimeException) {
/*      */         
/*  960 */         RuntimeException runtimeexception = (RuntimeException)throwable;
/*  961 */         throw runtimeexception;
/*      */       } 
/*      */ 
/*      */       
/*  965 */       p_handleException_0_.printStackTrace();
/*      */     
/*      */     }
/*      */     else {
/*      */       
/*  970 */       if (p_handleException_0_ instanceof IllegalArgumentException) {
/*      */         
/*  972 */         Config.warn("*** IllegalArgumentException ***");
/*  973 */         Config.warn("Method: " + p_handleException_2_.getTargetMethod());
/*  974 */         Config.warn("Object: " + p_handleException_1_);
/*  975 */         Config.warn("Parameter classes: " + Config.arrayToString(getClasses(p_handleException_3_)));
/*  976 */         Config.warn("Parameters: " + Config.arrayToString(p_handleException_3_));
/*      */       } 
/*      */       
/*  979 */       Config.warn("*** Exception outside of method ***");
/*  980 */       Config.warn("Method deactivated: " + p_handleException_2_.getTargetMethod());
/*  981 */       p_handleException_2_.deactivate();
/*  982 */       p_handleException_0_.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static void handleException(Throwable p_handleException_0_, ReflectorConstructor p_handleException_1_, Object[] p_handleException_2_) {
/*  988 */     if (p_handleException_0_ instanceof java.lang.reflect.InvocationTargetException) {
/*      */       
/*  990 */       p_handleException_0_.printStackTrace();
/*      */     }
/*      */     else {
/*      */       
/*  994 */       if (p_handleException_0_ instanceof IllegalArgumentException) {
/*      */         
/*  996 */         Config.warn("*** IllegalArgumentException ***");
/*  997 */         Config.warn("Constructor: " + p_handleException_1_.getTargetConstructor());
/*  998 */         Config.warn("Parameter classes: " + Config.arrayToString(getClasses(p_handleException_2_)));
/*  999 */         Config.warn("Parameters: " + Config.arrayToString(p_handleException_2_));
/*      */       } 
/*      */       
/* 1002 */       Config.warn("*** Exception outside of constructor ***");
/* 1003 */       Config.warn("Constructor deactivated: " + p_handleException_1_.getTargetConstructor());
/* 1004 */       p_handleException_1_.deactivate();
/* 1005 */       p_handleException_0_.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static Object[] getClasses(Object[] p_getClasses_0_) {
/* 1011 */     if (p_getClasses_0_ == null)
/*      */     {
/* 1013 */       return (Object[])new Class[0];
/*      */     }
/*      */ 
/*      */     
/* 1017 */     Class[] aclass = new Class[p_getClasses_0_.length];
/*      */     
/* 1019 */     for (int i = 0; i < aclass.length; i++) {
/*      */       
/* 1021 */       Object object = p_getClasses_0_[i];
/*      */       
/* 1023 */       if (object != null)
/*      */       {
/* 1025 */         aclass[i] = object.getClass();
/*      */       }
/*      */     } 
/*      */     
/* 1029 */     return (Object[])aclass;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ReflectorField[] getReflectorFields(ReflectorClass p_getReflectorFields_0_, Class p_getReflectorFields_1_, int p_getReflectorFields_2_) {
/* 1035 */     ReflectorField[] areflectorfield = new ReflectorField[p_getReflectorFields_2_];
/*      */     
/* 1037 */     for (int i = 0; i < areflectorfield.length; i++)
/*      */     {
/* 1039 */       areflectorfield[i] = new ReflectorField(p_getReflectorFields_0_, p_getReflectorFields_1_, i);
/*      */     }
/*      */     
/* 1042 */     return areflectorfield;
/*      */   }
/*      */ 
/*      */   
/*      */   private static boolean logEntry(String p_logEntry_0_) {
/* 1047 */     Config.dbg(p_logEntry_0_);
/* 1048 */     return true;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\Reflector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */