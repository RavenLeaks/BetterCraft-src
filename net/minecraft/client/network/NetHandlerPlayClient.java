/*      */ package net.minecraft.client.network;
/*      */ 
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.util.concurrent.FutureCallback;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.Field;
/*      */ import java.net.URI;
/*      */ import java.net.URISyntaxException;
/*      */ import java.net.URLDecoder;
/*      */ import java.nio.charset.StandardCharsets;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import javax.annotation.Nullable;
/*      */ import me.nzxter.bettercraft.BetterCraft;
/*      */ import me.nzxter.bettercraft.mods.protocolhack.PacketWrapper;
/*      */ import me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.entity.MetaDataUtils;
/*      */ import net.minecraft.advancements.Advancement;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.client.ClientBrandRetriever;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.audio.GuardianSound;
/*      */ import net.minecraft.client.audio.ISound;
/*      */ import net.minecraft.client.audio.PositionedSoundRecord;
/*      */ import net.minecraft.client.entity.AbstractClientPlayer;
/*      */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.GuiCommandBlock;
/*      */ import net.minecraft.client.gui.GuiDisconnected;
/*      */ import net.minecraft.client.gui.GuiDownloadTerrain;
/*      */ import net.minecraft.client.gui.GuiGameOver;
/*      */ import net.minecraft.client.gui.GuiMainMenu;
/*      */ import net.minecraft.client.gui.GuiMerchant;
/*      */ import net.minecraft.client.gui.GuiMultiplayer;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.GuiScreenBook;
/*      */ import net.minecraft.client.gui.GuiScreenDemo;
/*      */ import net.minecraft.client.gui.GuiScreenRealmsProxy;
/*      */ import net.minecraft.client.gui.GuiWinGame;
/*      */ import net.minecraft.client.gui.GuiYesNo;
/*      */ import net.minecraft.client.gui.GuiYesNoCallback;
/*      */ import net.minecraft.client.gui.IProgressMeter;
/*      */ import net.minecraft.client.gui.MapItemRenderer;
/*      */ import net.minecraft.client.gui.inventory.GuiContainerCreative;
/*      */ import net.minecraft.client.gui.recipebook.GuiRecipeBook;
/*      */ import net.minecraft.client.gui.recipebook.IRecipeShownListener;
/*      */ import net.minecraft.client.gui.recipebook.RecipeList;
/*      */ import net.minecraft.client.gui.toasts.RecipeToast;
/*      */ import net.minecraft.client.multiplayer.ClientAdvancementManager;
/*      */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*      */ import net.minecraft.client.multiplayer.ServerData;
/*      */ import net.minecraft.client.multiplayer.ServerList;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.particle.Particle;
/*      */ import net.minecraft.client.particle.ParticleItemPickup;
/*      */ import net.minecraft.client.player.inventory.ContainerLocalMenu;
/*      */ import net.minecraft.client.player.inventory.LocalBlockIntercommunication;
/*      */ import net.minecraft.client.renderer.debug.DebugRendererNeighborsUpdate;
/*      */ import net.minecraft.client.renderer.debug.DebugRendererPathfinding;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.client.util.RecipeBookClient;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityAreaEffectCloud;
/*      */ import net.minecraft.entity.EntityLeashKnot;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.EntityTracker;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.NpcMerchant;
/*      */ import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.ai.attributes.IAttribute;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*      */ import net.minecraft.entity.effect.EntityLightningBolt;
/*      */ import net.minecraft.entity.item.EntityArmorStand;
/*      */ import net.minecraft.entity.item.EntityBoat;
/*      */ import net.minecraft.entity.item.EntityEnderCrystal;
/*      */ import net.minecraft.entity.item.EntityEnderEye;
/*      */ import net.minecraft.entity.item.EntityEnderPearl;
/*      */ import net.minecraft.entity.item.EntityExpBottle;
/*      */ import net.minecraft.entity.item.EntityFallingBlock;
/*      */ import net.minecraft.entity.item.EntityFireworkRocket;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.item.EntityPainting;
/*      */ import net.minecraft.entity.item.EntityTNTPrimed;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.monster.EntityGuardian;
/*      */ import net.minecraft.entity.passive.AbstractHorse;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.entity.projectile.EntityArrow;
/*      */ import net.minecraft.entity.projectile.EntityDragonFireball;
/*      */ import net.minecraft.entity.projectile.EntityEgg;
/*      */ import net.minecraft.entity.projectile.EntityEvokerFangs;
/*      */ import net.minecraft.entity.projectile.EntityFishHook;
/*      */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*      */ import net.minecraft.entity.projectile.EntityLlamaSpit;
/*      */ import net.minecraft.entity.projectile.EntityPotion;
/*      */ import net.minecraft.entity.projectile.EntityShulkerBullet;
/*      */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*      */ import net.minecraft.entity.projectile.EntitySnowball;
/*      */ import net.minecraft.entity.projectile.EntitySpectralArrow;
/*      */ import net.minecraft.entity.projectile.EntityTippedArrow;
/*      */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.init.SoundEvents;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.ContainerHorseChest;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryBasic;
/*      */ import net.minecraft.item.ItemMap;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.crafting.IRecipe;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.INetHandler;
/*      */ import net.minecraft.network.NetworkManager;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.PacketBuffer;
/*      */ import net.minecraft.network.PacketThreadUtil;
/*      */ import net.minecraft.network.datasync.EntityDataManager;
/*      */ import net.minecraft.network.play.INetHandlerPlayClient;
/*      */ import net.minecraft.network.play.client.CPacketClientStatus;
/*      */ import net.minecraft.network.play.client.CPacketConfirmTeleport;
/*      */ import net.minecraft.network.play.client.CPacketConfirmTransaction;
/*      */ import net.minecraft.network.play.client.CPacketCustomPayload;
/*      */ import net.minecraft.network.play.client.CPacketKeepAlive;
/*      */ import net.minecraft.network.play.client.CPacketPlayer;
/*      */ import net.minecraft.network.play.client.CPacketResourcePackStatus;
/*      */ import net.minecraft.network.play.client.CPacketVehicleMove;
/*      */ import net.minecraft.network.play.server.SPacketAdvancementInfo;
/*      */ import net.minecraft.network.play.server.SPacketAnimation;
/*      */ import net.minecraft.network.play.server.SPacketBlockAction;
/*      */ import net.minecraft.network.play.server.SPacketBlockBreakAnim;
/*      */ import net.minecraft.network.play.server.SPacketBlockChange;
/*      */ import net.minecraft.network.play.server.SPacketCamera;
/*      */ import net.minecraft.network.play.server.SPacketChangeGameState;
/*      */ import net.minecraft.network.play.server.SPacketChat;
/*      */ import net.minecraft.network.play.server.SPacketChunkData;
/*      */ import net.minecraft.network.play.server.SPacketCloseWindow;
/*      */ import net.minecraft.network.play.server.SPacketCollectItem;
/*      */ import net.minecraft.network.play.server.SPacketCombatEvent;
/*      */ import net.minecraft.network.play.server.SPacketConfirmTransaction;
/*      */ import net.minecraft.network.play.server.SPacketCooldown;
/*      */ import net.minecraft.network.play.server.SPacketCustomPayload;
/*      */ import net.minecraft.network.play.server.SPacketCustomSound;
/*      */ import net.minecraft.network.play.server.SPacketDestroyEntities;
/*      */ import net.minecraft.network.play.server.SPacketDisconnect;
/*      */ import net.minecraft.network.play.server.SPacketDisplayObjective;
/*      */ import net.minecraft.network.play.server.SPacketEffect;
/*      */ import net.minecraft.network.play.server.SPacketEntity;
/*      */ import net.minecraft.network.play.server.SPacketEntityAttach;
/*      */ import net.minecraft.network.play.server.SPacketEntityEffect;
/*      */ import net.minecraft.network.play.server.SPacketEntityEquipment;
/*      */ import net.minecraft.network.play.server.SPacketEntityHeadLook;
/*      */ import net.minecraft.network.play.server.SPacketEntityMetadata;
/*      */ import net.minecraft.network.play.server.SPacketEntityProperties;
/*      */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*      */ import net.minecraft.network.play.server.SPacketEntityTeleport;
/*      */ import net.minecraft.network.play.server.SPacketEntityVelocity;
/*      */ import net.minecraft.network.play.server.SPacketExplosion;
/*      */ import net.minecraft.network.play.server.SPacketHeldItemChange;
/*      */ import net.minecraft.network.play.server.SPacketJoinGame;
/*      */ import net.minecraft.network.play.server.SPacketKeepAlive;
/*      */ import net.minecraft.network.play.server.SPacketMaps;
/*      */ import net.minecraft.network.play.server.SPacketMoveVehicle;
/*      */ import net.minecraft.network.play.server.SPacketMultiBlockChange;
/*      */ import net.minecraft.network.play.server.SPacketOpenWindow;
/*      */ import net.minecraft.network.play.server.SPacketParticles;
/*      */ import net.minecraft.network.play.server.SPacketPlaceGhostRecipe;
/*      */ import net.minecraft.network.play.server.SPacketPlayerAbilities;
/*      */ import net.minecraft.network.play.server.SPacketPlayerListHeaderFooter;
/*      */ import net.minecraft.network.play.server.SPacketPlayerListItem;
/*      */ import net.minecraft.network.play.server.SPacketPlayerPosLook;
/*      */ import net.minecraft.network.play.server.SPacketRecipeBook;
/*      */ import net.minecraft.network.play.server.SPacketRemoveEntityEffect;
/*      */ import net.minecraft.network.play.server.SPacketResourcePackSend;
/*      */ import net.minecraft.network.play.server.SPacketRespawn;
/*      */ import net.minecraft.network.play.server.SPacketScoreboardObjective;
/*      */ import net.minecraft.network.play.server.SPacketSelectAdvancementsTab;
/*      */ import net.minecraft.network.play.server.SPacketServerDifficulty;
/*      */ import net.minecraft.network.play.server.SPacketSetExperience;
/*      */ import net.minecraft.network.play.server.SPacketSetPassengers;
/*      */ import net.minecraft.network.play.server.SPacketSetSlot;
/*      */ import net.minecraft.network.play.server.SPacketSignEditorOpen;
/*      */ import net.minecraft.network.play.server.SPacketSoundEffect;
/*      */ import net.minecraft.network.play.server.SPacketSpawnExperienceOrb;
/*      */ import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
/*      */ import net.minecraft.network.play.server.SPacketSpawnMob;
/*      */ import net.minecraft.network.play.server.SPacketSpawnObject;
/*      */ import net.minecraft.network.play.server.SPacketSpawnPainting;
/*      */ import net.minecraft.network.play.server.SPacketSpawnPlayer;
/*      */ import net.minecraft.network.play.server.SPacketSpawnPosition;
/*      */ import net.minecraft.network.play.server.SPacketStatistics;
/*      */ import net.minecraft.network.play.server.SPacketTabComplete;
/*      */ import net.minecraft.network.play.server.SPacketTeams;
/*      */ import net.minecraft.network.play.server.SPacketTimeUpdate;
/*      */ import net.minecraft.network.play.server.SPacketTitle;
/*      */ import net.minecraft.network.play.server.SPacketUnloadChunk;
/*      */ import net.minecraft.network.play.server.SPacketUpdateBossInfo;
/*      */ import net.minecraft.network.play.server.SPacketUpdateHealth;
/*      */ import net.minecraft.network.play.server.SPacketUpdateScore;
/*      */ import net.minecraft.network.play.server.SPacketUpdateTileEntity;
/*      */ import net.minecraft.network.play.server.SPacketUseBed;
/*      */ import net.minecraft.network.play.server.SPacketWindowItems;
/*      */ import net.minecraft.network.play.server.SPacketWindowProperty;
/*      */ import net.minecraft.network.play.server.SPacketWorldBorder;
/*      */ import net.minecraft.pathfinding.Path;
/*      */ import net.minecraft.potion.Potion;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.realms.DisconnectedRealmsScreen;
/*      */ import net.minecraft.scoreboard.IScoreCriteria;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.stats.RecipeBook;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumHand;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.ITabCompleter;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.ResourceLocation;
/*      */ import net.minecraft.util.SoundCategory;
/*      */ import net.minecraft.util.StringUtils;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.TextComponentTranslation;
/*      */ import net.minecraft.util.text.TextFormatting;
/*      */ import net.minecraft.village.MerchantRecipeList;
/*      */ import net.minecraft.world.Explosion;
/*      */ import net.minecraft.world.GameType;
/*      */ import net.minecraft.world.IInteractionObject;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.storage.MapData;
/*      */ import net.minecraft.world.storage.WorldSavedData;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ import wdl.WDLHooks;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class NetHandlerPlayClient
/*      */   implements INetHandlerPlayClient
/*      */ {
/*  270 */   private static final Logger LOGGER = LogManager.getLogger();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final NetworkManager netManager;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final GameProfile profile;
/*      */ 
/*      */ 
/*      */   
/*      */   private final GuiScreen guiScreenServer;
/*      */ 
/*      */ 
/*      */   
/*      */   private Minecraft gameController;
/*      */ 
/*      */ 
/*      */   
/*      */   private WorldClient clientWorldController;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean doneLoadingTerrain;
/*      */ 
/*      */ 
/*      */   
/*  300 */   private final Map<UUID, NetworkPlayerInfo> playerInfoMap = Maps.newHashMap();
/*  301 */   public int currentServerMaxPlayers = 20;
/*      */ 
/*      */   
/*      */   private boolean hasStatistics;
/*      */ 
/*      */   
/*      */   private final ClientAdvancementManager field_191983_k;
/*      */   
/*  309 */   private final Random avRandomizer = new Random();
/*      */ 
/*      */   
/*      */   public NetHandlerPlayClient(Minecraft mcIn, GuiScreen p_i46300_2_, NetworkManager networkManagerIn, GameProfile profileIn) {
/*  313 */     this.gameController = mcIn;
/*  314 */     this.guiScreenServer = p_i46300_2_;
/*  315 */     this.netManager = networkManagerIn;
/*  316 */     this.profile = profileIn;
/*  317 */     this.field_191983_k = new ClientAdvancementManager(mcIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanup() {
/*  325 */     this.clientWorldController = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleJoinGame(SPacketJoinGame packetIn) {
/*  334 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  335 */     this.gameController.playerController = new PlayerControllerMP(this.gameController, this);
/*  336 */     this.clientWorldController = new WorldClient(this, new WorldSettings(0L, packetIn.getGameType(), false, packetIn.isHardcoreMode(), packetIn.getWorldType()), packetIn.getDimension(), packetIn.getDifficulty(), this.gameController.mcProfiler);
/*  337 */     this.gameController.gameSettings.difficulty = packetIn.getDifficulty();
/*  338 */     this.gameController.loadWorld(this.clientWorldController);
/*  339 */     this.gameController.player.dimension = packetIn.getDimension();
/*  340 */     this.gameController.displayGuiScreen((GuiScreen)new GuiDownloadTerrain());
/*  341 */     this.gameController.player.setEntityId(packetIn.getPlayerId());
/*  342 */     this.currentServerMaxPlayers = packetIn.getMaxPlayers();
/*  343 */     this.gameController.player.setReducedDebug(packetIn.isReducedDebugInfo());
/*  344 */     this.gameController.playerController.setGameType(packetIn.getGameType());
/*  345 */     this.gameController.gameSettings.sendSettingsToServer();
/*  346 */     this.netManager.sendPacket((Packet)new CPacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(ClientBrandRetriever.getClientModName())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnObject(SPacketSpawnObject packetIn) {
/*      */     EntityAreaEffectCloud entityAreaEffectCloud;
/*  354 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  355 */     double d0 = packetIn.getX();
/*  356 */     double d1 = packetIn.getY();
/*  357 */     double d2 = packetIn.getZ();
/*  358 */     Entity entity = null;
/*      */     
/*  360 */     if (packetIn.getType() == 10) {
/*      */       
/*  362 */       EntityMinecart entityMinecart = EntityMinecart.create((World)this.clientWorldController, d0, d1, d2, EntityMinecart.Type.getById(packetIn.getData()));
/*      */     }
/*  364 */     else if (packetIn.getType() == 90) {
/*      */       
/*  366 */       Entity entity1 = this.clientWorldController.getEntityByID(packetIn.getData());
/*      */       
/*  368 */       if (entity1 instanceof EntityPlayer)
/*      */       {
/*  370 */         EntityFishHook entityFishHook = new EntityFishHook((World)this.clientWorldController, (EntityPlayer)entity1, d0, d1, d2);
/*      */       }
/*      */       
/*  373 */       packetIn.setData(0);
/*      */     }
/*  375 */     else if (packetIn.getType() == 60) {
/*      */       
/*  377 */       EntityTippedArrow entityTippedArrow = new EntityTippedArrow((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  379 */     else if (packetIn.getType() == 91) {
/*      */       
/*  381 */       EntitySpectralArrow entitySpectralArrow = new EntitySpectralArrow((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  383 */     else if (packetIn.getType() == 61) {
/*      */       
/*  385 */       EntitySnowball entitySnowball = new EntitySnowball((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  387 */     else if (packetIn.getType() == 68) {
/*      */       
/*  389 */       EntityLlamaSpit entityLlamaSpit = new EntityLlamaSpit((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*      */     }
/*  391 */     else if (packetIn.getType() == 71) {
/*      */       
/*  393 */       EntityItemFrame entityItemFrame = new EntityItemFrame((World)this.clientWorldController, new BlockPos(d0, d1, d2), EnumFacing.getHorizontal(packetIn.getData()));
/*  394 */       packetIn.setData(0);
/*      */     }
/*  396 */     else if (packetIn.getType() == 77) {
/*      */       
/*  398 */       EntityLeashKnot entityLeashKnot = new EntityLeashKnot((World)this.clientWorldController, new BlockPos(MathHelper.floor(d0), MathHelper.floor(d1), MathHelper.floor(d2)));
/*  399 */       packetIn.setData(0);
/*      */     }
/*  401 */     else if (packetIn.getType() == 65) {
/*      */       
/*  403 */       EntityEnderPearl entityEnderPearl = new EntityEnderPearl((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  405 */     else if (packetIn.getType() == 72) {
/*      */       
/*  407 */       EntityEnderEye entityEnderEye = new EntityEnderEye((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  409 */     else if (packetIn.getType() == 76) {
/*      */       
/*  411 */       EntityFireworkRocket entityFireworkRocket = new EntityFireworkRocket((World)this.clientWorldController, d0, d1, d2, ItemStack.field_190927_a);
/*      */     }
/*  413 */     else if (packetIn.getType() == 63) {
/*      */       
/*  415 */       EntityLargeFireball entityLargeFireball = new EntityLargeFireball((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*  416 */       packetIn.setData(0);
/*      */     }
/*  418 */     else if (packetIn.getType() == 93) {
/*      */       
/*  420 */       EntityDragonFireball entityDragonFireball = new EntityDragonFireball((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*  421 */       packetIn.setData(0);
/*      */     }
/*  423 */     else if (packetIn.getType() == 64) {
/*      */       
/*  425 */       EntitySmallFireball entitySmallFireball = new EntitySmallFireball((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*  426 */       packetIn.setData(0);
/*      */     }
/*  428 */     else if (packetIn.getType() == 66) {
/*      */       
/*  430 */       EntityWitherSkull entityWitherSkull = new EntityWitherSkull((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*  431 */       packetIn.setData(0);
/*      */     }
/*  433 */     else if (packetIn.getType() == 67) {
/*      */       
/*  435 */       EntityShulkerBullet entityShulkerBullet = new EntityShulkerBullet((World)this.clientWorldController, d0, d1, d2, packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*  436 */       packetIn.setData(0);
/*      */     }
/*  438 */     else if (packetIn.getType() == 62) {
/*      */       
/*  440 */       EntityEgg entityEgg = new EntityEgg((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  442 */     else if (packetIn.getType() == 79) {
/*      */       
/*  444 */       EntityEvokerFangs entityEvokerFangs = new EntityEvokerFangs((World)this.clientWorldController, d0, d1, d2, 0.0F, 0, null);
/*      */     }
/*  446 */     else if (packetIn.getType() == 73) {
/*      */       
/*  448 */       EntityPotion entityPotion = new EntityPotion((World)this.clientWorldController, d0, d1, d2, ItemStack.field_190927_a);
/*  449 */       packetIn.setData(0);
/*      */     }
/*  451 */     else if (packetIn.getType() == 75) {
/*      */       
/*  453 */       EntityExpBottle entityExpBottle = new EntityExpBottle((World)this.clientWorldController, d0, d1, d2);
/*  454 */       packetIn.setData(0);
/*      */     }
/*  456 */     else if (packetIn.getType() == 1) {
/*      */       
/*  458 */       EntityBoat entityBoat = new EntityBoat((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  460 */     else if (packetIn.getType() == 50) {
/*      */       
/*  462 */       EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed((World)this.clientWorldController, d0, d1, d2, null);
/*      */     }
/*  464 */     else if (packetIn.getType() == 78) {
/*      */       
/*  466 */       EntityArmorStand entityArmorStand = new EntityArmorStand((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  468 */     else if (packetIn.getType() == 51) {
/*      */       
/*  470 */       EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  472 */     else if (packetIn.getType() == 2) {
/*      */       
/*  474 */       EntityItem entityItem = new EntityItem((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*  476 */     else if (packetIn.getType() == 70) {
/*      */       
/*  478 */       EntityFallingBlock entityFallingBlock = new EntityFallingBlock((World)this.clientWorldController, d0, d1, d2, Block.getStateById(packetIn.getData() & 0xFFFF));
/*  479 */       packetIn.setData(0);
/*      */     }
/*  481 */     else if (packetIn.getType() == 3) {
/*      */       
/*  483 */       entityAreaEffectCloud = new EntityAreaEffectCloud((World)this.clientWorldController, d0, d1, d2);
/*      */     } 
/*      */ 
/*      */     
/*  487 */     if (entityAreaEffectCloud != null) {
/*  488 */       ((Entity)entityAreaEffectCloud).rotationPitch = (packetIn.getPitch() * 360) / 256.0F;
/*  489 */       ((Entity)entityAreaEffectCloud).rotationYaw = (packetIn.getYaw() * 360) / 256.0F;
/*  490 */       Entity[] aentity = entityAreaEffectCloud.getParts();
/*  491 */       if (aentity != null) {
/*  492 */         int i = packetIn.getEntityID() - entityAreaEffectCloud.getEntityId();
/*      */         Entity[] array;
/*  494 */         for (int length = (array = aentity).length, j = 0; j < length; j++) {
/*  495 */           Entity entity3 = array[j];
/*  496 */           entity3.setEntityId(entity3.getEntityId() + i);
/*      */         } 
/*      */       } 
/*  499 */       entityAreaEffectCloud.setEntityId(packetIn.getEntityID());
/*  500 */       entityAreaEffectCloud.setUniqueId(packetIn.getUniqueId());
/*  501 */       this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityAreaEffectCloud);
/*  502 */       if (packetIn.getData() > 0) {
/*  503 */         if (packetIn.getType() == 60 || packetIn.getType() == 91) {
/*  504 */           Entity entity4 = this.clientWorldController.getEntityByID(packetIn.getData() - 1);
/*  505 */           if (entity4 instanceof EntityLivingBase && entityAreaEffectCloud instanceof EntityArrow) {
/*  506 */             ((EntityArrow)entityAreaEffectCloud).shootingEntity = entity4;
/*      */           }
/*      */         } 
/*  509 */         entityAreaEffectCloud.setVelocity(packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, packetIn.getSpeedZ() / 8000.0D);
/*      */       } 
/*  511 */       PacketWrapper wrapper = BetterCraft.INSTANCE.getCurrentMinecraftVersion().getPacketWrapper();
/*  512 */       if (wrapper != null && BetterCraft.INSTANCE.getCurrentMinecraftVersion().getId() <= 47) {
/*      */         try {
/*  514 */           Field fld = wrapper.getClass().getDeclaredField("metadataMap");
/*  515 */           fld.setAccessible(true);
/*  516 */           Map<Integer, List<EntityDataManager.DataEntry<?>>> metadataMap = (Map<Integer, List<EntityDataManager.DataEntry<?>>>)fld.get(wrapper);
/*  517 */           if (!metadataMap.isEmpty()) {
/*      */             Integer[] array2;
/*  519 */             for (int length2 = (array2 = (Integer[])metadataMap.keySet().toArray((Object[])new Integer[0])).length, k = 0; k < length2; k++) {
/*  520 */               Integer eId = array2[k];
/*  521 */               if (packetIn.getEntityID() == eId.intValue()) {
/*  522 */                 List<EntityDataManager.DataEntry<?>> lst = metadataMap.remove(eId);
/*  523 */                 List<EntityDataManager.DataEntry<?>> out = new ArrayList<>();
/*  524 */                 MetaDataUtils.handleMetadata_47((AbstractClientPlayer)this.gameController.player, (Entity)entityAreaEffectCloud, lst, out);
/*  525 */                 entityAreaEffectCloud.getDataManager().setEntryValues(out);
/*      */               }
/*      */             
/*      */             } 
/*      */           } 
/*  530 */         } catch (Exception e) {
/*  531 */           e.printStackTrace();
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnExperienceOrb(SPacketSpawnExperienceOrb packetIn) {
/*  543 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  544 */     double d0 = packetIn.getX();
/*  545 */     double d1 = packetIn.getY();
/*  546 */     double d2 = packetIn.getZ();
/*  547 */     EntityXPOrb entityXPOrb = new EntityXPOrb((World)this.clientWorldController, d0, d1, d2, packetIn.getXPValue());
/*  548 */     EntityTracker.updateServerPosition((Entity)entityXPOrb, d0, d1, d2);
/*  549 */     ((Entity)entityXPOrb).rotationYaw = 0.0F;
/*  550 */     ((Entity)entityXPOrb).rotationPitch = 0.0F;
/*  551 */     entityXPOrb.setEntityId(packetIn.getEntityID());
/*  552 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityXPOrb);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnGlobalEntity(SPacketSpawnGlobalEntity packetIn) {
/*      */     EntityLightningBolt entityLightningBolt;
/*  560 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  561 */     double d0 = packetIn.getX();
/*  562 */     double d1 = packetIn.getY();
/*  563 */     double d2 = packetIn.getZ();
/*  564 */     Entity entity = null;
/*      */     
/*  566 */     if (packetIn.getType() == 1)
/*      */     {
/*  568 */       entityLightningBolt = new EntityLightningBolt((World)this.clientWorldController, d0, d1, d2, false);
/*      */     }
/*      */     
/*  571 */     if (entityLightningBolt != null) {
/*      */       
/*  573 */       EntityTracker.updateServerPosition((Entity)entityLightningBolt, d0, d1, d2);
/*  574 */       ((Entity)entityLightningBolt).rotationYaw = 0.0F;
/*  575 */       ((Entity)entityLightningBolt).rotationPitch = 0.0F;
/*  576 */       entityLightningBolt.setEntityId(packetIn.getEntityId());
/*  577 */       this.clientWorldController.addWeatherEffect((Entity)entityLightningBolt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnPainting(SPacketSpawnPainting packetIn) {
/*  586 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  587 */     EntityPainting entitypainting = new EntityPainting((World)this.clientWorldController, packetIn.getPosition(), packetIn.getFacing(), packetIn.getTitle());
/*  588 */     entitypainting.setUniqueId(packetIn.getUniqueId());
/*  589 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entitypainting);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityVelocity(SPacketEntityVelocity packetIn) {
/*  597 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  598 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  600 */     if (entity != null)
/*      */     {
/*  602 */       entity.setVelocity(packetIn.getMotionX() / 8000.0D, packetIn.getMotionY() / 8000.0D, packetIn.getMotionZ() / 8000.0D);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityMetadata(SPacketEntityMetadata packetIn) {
/*  612 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  613 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/*  615 */     if (entity != null && packetIn.getDataManagerEntries() != null)
/*      */     {
/*  617 */       entity.getDataManager().setEntryValues(packetIn.getDataManagerEntries());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnPlayer(SPacketSpawnPlayer packetIn) {
/*  626 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  627 */     double d0 = packetIn.getX();
/*  628 */     double d1 = packetIn.getY();
/*  629 */     double d2 = packetIn.getZ();
/*  630 */     float f = (packetIn.getYaw() * 360) / 256.0F;
/*  631 */     float f1 = (packetIn.getPitch() * 360) / 256.0F;
/*  632 */     EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP((World)this.gameController.world, getPlayerInfo(packetIn.getUniqueId()).getGameProfile());
/*  633 */     entityotherplayermp.prevPosX = d0;
/*  634 */     entityotherplayermp.lastTickPosX = d0;
/*  635 */     entityotherplayermp.prevPosY = d1;
/*  636 */     entityotherplayermp.lastTickPosY = d1;
/*  637 */     entityotherplayermp.prevPosZ = d2;
/*  638 */     entityotherplayermp.lastTickPosZ = d2;
/*  639 */     EntityTracker.updateServerPosition((Entity)entityotherplayermp, d0, d1, d2);
/*  640 */     entityotherplayermp.setPositionAndRotation(d0, d1, d2, f, f1);
/*  641 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityotherplayermp);
/*  642 */     List<EntityDataManager.DataEntry<?>> list = packetIn.getDataManagerEntries();
/*      */     
/*  644 */     if (list != null)
/*      */     {
/*  646 */       entityotherplayermp.getDataManager().setEntryValues(list);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityTeleport(SPacketEntityTeleport packetIn) {
/*  655 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  656 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/*  658 */     if (entity != null) {
/*      */       
/*  660 */       double d0 = packetIn.getX();
/*  661 */       double d1 = packetIn.getY();
/*  662 */       double d2 = packetIn.getZ();
/*  663 */       EntityTracker.updateServerPosition(entity, d0, d1, d2);
/*      */       
/*  665 */       if (!entity.canPassengerSteer()) {
/*      */         
/*  667 */         float f = (packetIn.getYaw() * 360) / 256.0F;
/*  668 */         float f1 = (packetIn.getPitch() * 360) / 256.0F;
/*      */         
/*  670 */         if (Math.abs(entity.posX - d0) < 0.03125D && Math.abs(entity.posY - d1) < 0.015625D && Math.abs(entity.posZ - d2) < 0.03125D) {
/*      */           
/*  672 */           entity.setPositionAndRotationDirect(entity.posX, entity.posY, entity.posZ, f, f1, 0, true);
/*      */         }
/*      */         else {
/*      */           
/*  676 */           entity.setPositionAndRotationDirect(d0, d1, d2, f, f1, 3, true);
/*      */         } 
/*      */         
/*  679 */         entity.onGround = packetIn.getOnGround();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleHeldItemChange(SPacketHeldItemChange packetIn) {
/*  689 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  691 */     if (InventoryPlayer.isHotbar(packetIn.getHeldItemHotbarIndex()))
/*      */     {
/*  693 */       this.gameController.player.inventory.currentItem = packetIn.getHeldItemHotbarIndex();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityMovement(SPacketEntity packetIn) {
/*  704 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  705 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/*  707 */     if (entity != null) {
/*      */       
/*  709 */       entity.serverPosX += packetIn.getX();
/*  710 */       entity.serverPosY += packetIn.getY();
/*  711 */       entity.serverPosZ += packetIn.getZ();
/*  712 */       double d0 = entity.serverPosX / 4096.0D;
/*  713 */       double d1 = entity.serverPosY / 4096.0D;
/*  714 */       double d2 = entity.serverPosZ / 4096.0D;
/*      */       
/*  716 */       if (!entity.canPassengerSteer()) {
/*      */         
/*  718 */         float f = packetIn.isRotating() ? ((packetIn.getYaw() * 360) / 256.0F) : entity.rotationYaw;
/*  719 */         float f1 = packetIn.isRotating() ? ((packetIn.getPitch() * 360) / 256.0F) : entity.rotationPitch;
/*  720 */         entity.setPositionAndRotationDirect(d0, d1, d2, f, f1, 3, false);
/*  721 */         entity.onGround = packetIn.getOnGround();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityHeadLook(SPacketEntityHeadLook packetIn) {
/*  732 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  733 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/*  735 */     if (entity != null) {
/*      */       
/*  737 */       float f = (packetIn.getYaw() * 360) / 256.0F;
/*  738 */       entity.setRotationYawHead(f);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDestroyEntities(SPacketDestroyEntities packetIn) {
/*  749 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  751 */     for (int i = 0; i < (packetIn.getEntityIDs()).length; i++)
/*      */     {
/*  753 */       this.clientWorldController.removeEntityFromWorld(packetIn.getEntityIDs()[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlayerPosLook(SPacketPlayerPosLook packetIn) {
/*  759 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  760 */     EntityPlayerSP entityPlayerSP = this.gameController.player;
/*  761 */     double d0 = packetIn.getX();
/*  762 */     double d1 = packetIn.getY();
/*  763 */     double d2 = packetIn.getZ();
/*  764 */     float f = packetIn.getYaw();
/*  765 */     float f1 = packetIn.getPitch();
/*      */     
/*  767 */     if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X)) {
/*      */       
/*  769 */       d0 += ((EntityPlayer)entityPlayerSP).posX;
/*      */     }
/*      */     else {
/*      */       
/*  773 */       ((EntityPlayer)entityPlayerSP).motionX = 0.0D;
/*      */     } 
/*      */     
/*  776 */     if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y)) {
/*      */       
/*  778 */       d1 += ((EntityPlayer)entityPlayerSP).posY;
/*      */     }
/*      */     else {
/*      */       
/*  782 */       ((EntityPlayer)entityPlayerSP).motionY = 0.0D;
/*      */     } 
/*      */     
/*  785 */     if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Z)) {
/*      */       
/*  787 */       d2 += ((EntityPlayer)entityPlayerSP).posZ;
/*      */     }
/*      */     else {
/*      */       
/*  791 */       ((EntityPlayer)entityPlayerSP).motionZ = 0.0D;
/*      */     } 
/*      */     
/*  794 */     if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.X_ROT))
/*      */     {
/*  796 */       f1 += ((EntityPlayer)entityPlayerSP).rotationPitch;
/*      */     }
/*      */     
/*  799 */     if (packetIn.getFlags().contains(SPacketPlayerPosLook.EnumFlags.Y_ROT))
/*      */     {
/*  801 */       f += ((EntityPlayer)entityPlayerSP).rotationYaw;
/*      */     }
/*      */     
/*  804 */     entityPlayerSP.setPositionAndRotation(d0, d1, d2, f, f1);
/*  805 */     this.netManager.sendPacket((Packet)new CPacketConfirmTeleport(packetIn.getTeleportId()));
/*  806 */     this.netManager.sendPacket((Packet)new CPacketPlayer.PositionRotation(((EntityPlayer)entityPlayerSP).posX, (entityPlayerSP.getEntityBoundingBox()).minY, ((EntityPlayer)entityPlayerSP).posZ, ((EntityPlayer)entityPlayerSP).rotationYaw, ((EntityPlayer)entityPlayerSP).rotationPitch, false));
/*      */     
/*  808 */     if (!this.doneLoadingTerrain) {
/*      */       
/*  810 */       this.gameController.player.prevPosX = this.gameController.player.posX;
/*  811 */       this.gameController.player.prevPosY = this.gameController.player.posY;
/*  812 */       this.gameController.player.prevPosZ = this.gameController.player.posZ;
/*  813 */       this.doneLoadingTerrain = true;
/*  814 */       this.gameController.displayGuiScreen(null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMultiBlockChange(SPacketMultiBlockChange packetIn) {
/*  825 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController); byte b; int i;
/*      */     SPacketMultiBlockChange.BlockUpdateData[] arrayOfBlockUpdateData;
/*  827 */     for (i = (arrayOfBlockUpdateData = packetIn.getChangedBlocks()).length, b = 0; b < i; ) { SPacketMultiBlockChange.BlockUpdateData spacketmultiblockchange$blockupdatedata = arrayOfBlockUpdateData[b];
/*      */       
/*  829 */       this.clientWorldController.invalidateRegionAndSetBlock(spacketmultiblockchange$blockupdatedata.getPos(), spacketmultiblockchange$blockupdatedata.getBlockState());
/*      */       b++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleChunkData(SPacketChunkData packetIn) {
/*  838 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  840 */     if (packetIn.doChunkLoad())
/*      */     {
/*  842 */       this.clientWorldController.doPreChunk(packetIn.getChunkX(), packetIn.getChunkZ(), true);
/*      */     }
/*      */     
/*  845 */     this.clientWorldController.invalidateBlockReceiveRegion(packetIn.getChunkX() << 4, 0, packetIn.getChunkZ() << 4, (packetIn.getChunkX() << 4) + 15, 256, (packetIn.getChunkZ() << 4) + 15);
/*  846 */     Chunk chunk = this.clientWorldController.getChunkFromChunkCoords(packetIn.getChunkX(), packetIn.getChunkZ());
/*  847 */     chunk.fillChunk(packetIn.getReadBuffer(), packetIn.getExtractedSize(), packetIn.doChunkLoad());
/*  848 */     this.clientWorldController.markBlockRangeForRenderUpdate(packetIn.getChunkX() << 4, 0, packetIn.getChunkZ() << 4, (packetIn.getChunkX() << 4) + 15, 256, (packetIn.getChunkZ() << 4) + 15);
/*      */     
/*  850 */     if (!packetIn.doChunkLoad() || !(this.clientWorldController.provider instanceof net.minecraft.world.WorldProviderSurface))
/*      */     {
/*  852 */       chunk.resetRelightChecks();
/*      */     }
/*      */     
/*  855 */     for (NBTTagCompound nbttagcompound : packetIn.getTileEntityTags()) {
/*      */       
/*  857 */       BlockPos blockpos = new BlockPos(nbttagcompound.getInteger("x"), nbttagcompound.getInteger("y"), nbttagcompound.getInteger("z"));
/*  858 */       TileEntity tileentity = this.clientWorldController.getTileEntity(blockpos);
/*      */       
/*  860 */       if (tileentity != null)
/*      */       {
/*  862 */         tileentity.readFromNBT(nbttagcompound);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void processChunkUnload(SPacketUnloadChunk packetIn) {
/*  869 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  870 */     this.clientWorldController.doPreChunk(packetIn.getX(), packetIn.getZ(), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBlockChange(SPacketBlockChange packetIn) {
/*  878 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  879 */     this.clientWorldController.invalidateRegionAndSetBlock(packetIn.getBlockPosition(), packetIn.getBlockState());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDisconnect(SPacketDisconnect packetIn) {
/*  887 */     this.netManager.closeChannel(packetIn.getReason());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDisconnect(ITextComponent reason) {
/*  895 */     this.gameController.loadWorld(null);
/*      */     
/*  897 */     if (this.guiScreenServer != null) {
/*      */       
/*  899 */       if (this.guiScreenServer instanceof GuiScreenRealmsProxy)
/*      */       {
/*  901 */         this.gameController.displayGuiScreen((GuiScreen)(new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.guiScreenServer).getProxy(), "disconnect.lost", reason)).getProxy());
/*      */       }
/*      */       else
/*      */       {
/*  905 */         this.gameController.displayGuiScreen((GuiScreen)new GuiDisconnected(this.guiScreenServer, "disconnect.lost", reason));
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  910 */       this.gameController.displayGuiScreen((GuiScreen)new GuiDisconnected((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()), "disconnect.lost", reason));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPacket(Packet<?> packetIn) {
/*  916 */     this.netManager.sendPacket(packetIn);
/*      */   }
/*      */   
/*      */   public void handleCollectItem(SPacketCollectItem packetIn) {
/*      */     EntityPlayerSP entityPlayerSP;
/*  921 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  922 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getCollectedItemEntityID());
/*  923 */     EntityLivingBase entitylivingbase = (EntityLivingBase)this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  925 */     if (entitylivingbase == null)
/*      */     {
/*  927 */       entityPlayerSP = this.gameController.player;
/*      */     }
/*      */     
/*  930 */     if (entity != null) {
/*      */       
/*  932 */       if (entity instanceof EntityXPOrb) {
/*      */         
/*  934 */         this.clientWorldController.playSound(entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.1F, (this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.35F + 0.9F, false);
/*      */       }
/*      */       else {
/*      */         
/*  938 */         this.clientWorldController.playSound(entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, (this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 1.4F + 2.0F, false);
/*      */       } 
/*      */       
/*  941 */       if (entity instanceof EntityItem)
/*      */       {
/*  943 */         ((EntityItem)entity).getEntityItem().func_190920_e(packetIn.func_191208_c());
/*      */       }
/*      */       
/*  946 */       this.gameController.effectRenderer.addEffect((Particle)new ParticleItemPickup((World)this.clientWorldController, entity, (Entity)entityPlayerSP, 0.5F));
/*  947 */       this.clientWorldController.removeEntityFromWorld(packetIn.getCollectedItemEntityID());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleChat(SPacketChat packetIn) {
/*  957 */     WDLHooks.onNHPCHandleChat(this, packetIn);
/*      */ 
/*      */     
/*  960 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  961 */     this.gameController.ingameGUI.func_191742_a(packetIn.func_192590_c(), packetIn.getChatComponent());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleAnimation(SPacketAnimation packetIn) {
/*  970 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  971 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  973 */     if (entity != null)
/*      */     {
/*  975 */       if (packetIn.getAnimationType() == 0) {
/*      */         
/*  977 */         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/*  978 */         entitylivingbase.swingArm(EnumHand.MAIN_HAND);
/*      */       }
/*  980 */       else if (packetIn.getAnimationType() == 3) {
/*      */         
/*  982 */         EntityLivingBase entitylivingbase1 = (EntityLivingBase)entity;
/*  983 */         entitylivingbase1.swingArm(EnumHand.OFF_HAND);
/*      */       }
/*  985 */       else if (packetIn.getAnimationType() == 1) {
/*      */         
/*  987 */         entity.performHurtAnimation();
/*      */       }
/*  989 */       else if (packetIn.getAnimationType() == 2) {
/*      */         
/*  991 */         EntityPlayer entityplayer = (EntityPlayer)entity;
/*  992 */         entityplayer.wakeUpPlayer(false, false, false);
/*      */       }
/*  994 */       else if (packetIn.getAnimationType() == 4) {
/*      */         
/*  996 */         this.gameController.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT);
/*      */       }
/*  998 */       else if (packetIn.getAnimationType() == 5) {
/*      */         
/* 1000 */         this.gameController.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT_MAGIC);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUseBed(SPacketUseBed packetIn) {
/* 1011 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1012 */     packetIn.getPlayer((World)this.clientWorldController).trySleep(packetIn.getBedPosition());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnMob(SPacketSpawnMob packetIn) {
/* 1021 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1022 */     double d0 = packetIn.getX();
/* 1023 */     double d1 = packetIn.getY();
/* 1024 */     double d2 = packetIn.getZ();
/* 1025 */     float f = (packetIn.getYaw() * 360) / 256.0F;
/* 1026 */     float f1 = (packetIn.getPitch() * 360) / 256.0F;
/* 1027 */     EntityLivingBase entitylivingbase = (EntityLivingBase)EntityList.createEntityByID(packetIn.getEntityType(), (World)this.gameController.world);
/*      */     
/* 1029 */     if (entitylivingbase != null) {
/*      */       
/* 1031 */       EntityTracker.updateServerPosition((Entity)entitylivingbase, d0, d1, d2);
/* 1032 */       entitylivingbase.renderYawOffset = (packetIn.getHeadPitch() * 360) / 256.0F;
/* 1033 */       entitylivingbase.rotationYawHead = (packetIn.getHeadPitch() * 360) / 256.0F;
/* 1034 */       Entity[] aentity = entitylivingbase.getParts();
/*      */       
/* 1036 */       if (aentity != null) {
/*      */         
/* 1038 */         int i = packetIn.getEntityID() - entitylivingbase.getEntityId(); byte b; int j;
/*      */         Entity[] arrayOfEntity;
/* 1040 */         for (j = (arrayOfEntity = aentity).length, b = 0; b < j; ) { Entity entity = arrayOfEntity[b];
/*      */           
/* 1042 */           entity.setEntityId(entity.getEntityId() + i);
/*      */           b++; }
/*      */       
/*      */       } 
/* 1046 */       entitylivingbase.setEntityId(packetIn.getEntityID());
/* 1047 */       entitylivingbase.setUniqueId(packetIn.getUniqueId());
/* 1048 */       entitylivingbase.setPositionAndRotation(d0, d1, d2, f, f1);
/* 1049 */       entitylivingbase.motionX = (packetIn.getVelocityX() / 8000.0F);
/* 1050 */       entitylivingbase.motionY = (packetIn.getVelocityY() / 8000.0F);
/* 1051 */       entitylivingbase.motionZ = (packetIn.getVelocityZ() / 8000.0F);
/* 1052 */       this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entitylivingbase);
/* 1053 */       List<EntityDataManager.DataEntry<?>> list = packetIn.getDataManagerEntries();
/*      */       
/* 1055 */       if (list != null)
/*      */       {
/* 1057 */         entitylivingbase.getDataManager().setEntryValues(list);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1062 */       LOGGER.warn("Skipping Entity with id {}", Integer.valueOf(packetIn.getEntityType()));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleTimeUpdate(SPacketTimeUpdate packetIn) {
/* 1068 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1069 */     this.gameController.world.setTotalWorldTime(packetIn.getTotalWorldTime());
/* 1070 */     this.gameController.world.setWorldTime(packetIn.getWorldTime());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSpawnPosition(SPacketSpawnPosition packetIn) {
/* 1075 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1076 */     this.gameController.player.setSpawnPoint(packetIn.getSpawnPos(), true);
/* 1077 */     this.gameController.world.getWorldInfo().setSpawn(packetIn.getSpawnPos());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetPassengers(SPacketSetPassengers packetIn) {
/* 1082 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1083 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 1085 */     if (entity == null) {
/*      */       
/* 1087 */       LOGGER.warn("Received passengers for unknown entity");
/*      */     }
/*      */     else {
/*      */       
/* 1091 */       boolean flag = entity.isRidingOrBeingRiddenBy((Entity)this.gameController.player);
/* 1092 */       entity.removePassengers(); byte b;
/*      */       int i, arrayOfInt[];
/* 1094 */       for (i = (arrayOfInt = packetIn.getPassengerIds()).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/*      */         
/* 1096 */         Entity entity1 = this.clientWorldController.getEntityByID(j);
/*      */         
/* 1098 */         if (entity1 != null) {
/*      */           
/* 1100 */           entity1.startRiding(entity, true);
/*      */           
/* 1102 */           if (entity1 == this.gameController.player && !flag)
/*      */           {
/* 1104 */             this.gameController.ingameGUI.setRecordPlaying(I18n.format("mount.onboard", new Object[] { GameSettings.getKeyDisplayString(this.gameController.gameSettings.keyBindSneak.getKeyCode()) }), false);
/*      */           }
/*      */         } 
/*      */         b++; }
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleEntityAttach(SPacketEntityAttach packetIn) {
/* 1113 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1114 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/* 1115 */     Entity entity1 = this.clientWorldController.getEntityByID(packetIn.getVehicleEntityId());
/*      */     
/* 1117 */     if (entity instanceof EntityLiving)
/*      */     {
/* 1119 */       if (entity1 != null) {
/*      */         
/* 1121 */         ((EntityLiving)entity).setLeashedToEntity(entity1, false);
/*      */       }
/*      */       else {
/*      */         
/* 1125 */         ((EntityLiving)entity).clearLeashed(false, false);
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
/*      */   public void handleEntityStatus(SPacketEntityStatus packetIn) {
/* 1138 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1139 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/* 1141 */     if (entity != null)
/*      */     {
/* 1143 */       if (packetIn.getOpCode() == 21) {
/*      */         
/* 1145 */         this.gameController.getSoundHandler().playSound((ISound)new GuardianSound((EntityGuardian)entity));
/*      */       }
/* 1147 */       else if (packetIn.getOpCode() == 35) {
/*      */         
/* 1149 */         int i = 40;
/* 1150 */         this.gameController.effectRenderer.func_191271_a(entity, EnumParticleTypes.TOTEM, 30);
/* 1151 */         this.clientWorldController.playSound(entity.posX, entity.posY, entity.posZ, SoundEvents.field_191263_gW, entity.getSoundCategory(), 1.0F, 1.0F, false);
/*      */         
/* 1153 */         if (entity == this.gameController.player)
/*      */         {
/* 1155 */           this.gameController.entityRenderer.func_190565_a(new ItemStack(Items.field_190929_cY));
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1160 */         entity.handleStatusUpdate(packetIn.getOpCode());
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleUpdateHealth(SPacketUpdateHealth packetIn) {
/* 1167 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1168 */     this.gameController.player.setPlayerSPHealth(packetIn.getHealth());
/* 1169 */     this.gameController.player.getFoodStats().setFoodLevel(packetIn.getFoodLevel());
/* 1170 */     this.gameController.player.getFoodStats().setFoodSaturationLevel(packetIn.getSaturationLevel());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSetExperience(SPacketSetExperience packetIn) {
/* 1175 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1176 */     this.gameController.player.setXPStats(packetIn.getExperienceBar(), packetIn.getTotalExperience(), packetIn.getLevel());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleRespawn(SPacketRespawn packetIn) {
/* 1181 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1183 */     if (packetIn.getDimensionID() != this.gameController.player.dimension) {
/*      */       
/* 1185 */       this.doneLoadingTerrain = false;
/* 1186 */       Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/* 1187 */       this.clientWorldController = new WorldClient(this, new WorldSettings(0L, packetIn.getGameType(), false, this.gameController.world.getWorldInfo().isHardcoreModeEnabled(), packetIn.getWorldType()), packetIn.getDimensionID(), packetIn.getDifficulty(), this.gameController.mcProfiler);
/* 1188 */       this.clientWorldController.setWorldScoreboard(scoreboard);
/* 1189 */       this.gameController.loadWorld(this.clientWorldController);
/* 1190 */       this.gameController.player.dimension = packetIn.getDimensionID();
/* 1191 */       this.gameController.displayGuiScreen((GuiScreen)new GuiDownloadTerrain());
/*      */     } 
/*      */     
/* 1194 */     this.gameController.setDimensionAndSpawnPlayer(packetIn.getDimensionID());
/* 1195 */     this.gameController.playerController.setGameType(packetIn.getGameType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleExplosion(SPacketExplosion packetIn) {
/* 1204 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1205 */     Explosion explosion = new Explosion((World)this.gameController.world, null, packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getStrength(), packetIn.getAffectedBlockPositions());
/* 1206 */     explosion.doExplosionB(true);
/* 1207 */     this.gameController.player.motionX += packetIn.getMotionX();
/* 1208 */     this.gameController.player.motionY += packetIn.getMotionY();
/* 1209 */     this.gameController.player.motionZ += packetIn.getMotionZ();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleOpenWindow(SPacketOpenWindow packetIn) {
/* 1219 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1220 */     EntityPlayerSP entityplayersp = this.gameController.player;
/*      */     
/* 1222 */     if ("minecraft:container".equals(packetIn.getGuiId())) {
/*      */       
/* 1224 */       entityplayersp.displayGUIChest((IInventory)new InventoryBasic(packetIn.getWindowTitle(), packetIn.getSlotCount()));
/* 1225 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */     }
/* 1227 */     else if ("minecraft:villager".equals(packetIn.getGuiId())) {
/*      */       
/* 1229 */       entityplayersp.displayVillagerTradeGui((IMerchant)new NpcMerchant((EntityPlayer)entityplayersp, packetIn.getWindowTitle()));
/* 1230 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */     }
/* 1232 */     else if ("EntityHorse".equals(packetIn.getGuiId())) {
/*      */       
/* 1234 */       Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */       
/* 1236 */       if (entity instanceof AbstractHorse)
/*      */       {
/* 1238 */         entityplayersp.openGuiHorseInventory((AbstractHorse)entity, (IInventory)new ContainerHorseChest(packetIn.getWindowTitle(), packetIn.getSlotCount()));
/* 1239 */         entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */       }
/*      */     
/* 1242 */     } else if (!packetIn.hasSlots()) {
/*      */       
/* 1244 */       entityplayersp.displayGui((IInteractionObject)new LocalBlockIntercommunication(packetIn.getGuiId(), packetIn.getWindowTitle()));
/* 1245 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */     }
/*      */     else {
/*      */       
/* 1249 */       ContainerLocalMenu containerLocalMenu = new ContainerLocalMenu(packetIn.getGuiId(), packetIn.getWindowTitle(), packetIn.getSlotCount());
/* 1250 */       entityplayersp.displayGUIChest((IInventory)containerLocalMenu);
/* 1251 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSetSlot(SPacketSetSlot packetIn) {
/* 1260 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1261 */     EntityPlayerSP entityPlayerSP = this.gameController.player;
/* 1262 */     ItemStack itemstack = packetIn.getStack();
/* 1263 */     int i = packetIn.getSlot();
/* 1264 */     this.gameController.func_193032_ao().func_193301_a(itemstack);
/*      */     
/* 1266 */     if (packetIn.getWindowId() == -1) {
/*      */       
/* 1268 */       ((EntityPlayer)entityPlayerSP).inventory.setItemStack(itemstack);
/*      */     }
/* 1270 */     else if (packetIn.getWindowId() == -2) {
/*      */       
/* 1272 */       ((EntityPlayer)entityPlayerSP).inventory.setInventorySlotContents(i, itemstack);
/*      */     }
/*      */     else {
/*      */       
/* 1276 */       boolean flag = false;
/*      */       
/* 1278 */       if (this.gameController.currentScreen instanceof GuiContainerCreative) {
/*      */         
/* 1280 */         GuiContainerCreative guicontainercreative = (GuiContainerCreative)this.gameController.currentScreen;
/* 1281 */         flag = (guicontainercreative.getSelectedTabIndex() != CreativeTabs.INVENTORY.getTabIndex());
/*      */       } 
/*      */       
/* 1284 */       if (packetIn.getWindowId() == 0 && packetIn.getSlot() >= 36 && i < 45) {
/*      */         
/* 1286 */         if (!itemstack.func_190926_b()) {
/*      */           
/* 1288 */           ItemStack itemstack1 = ((EntityPlayer)entityPlayerSP).inventoryContainer.getSlot(i).getStack();
/*      */           
/* 1290 */           if (itemstack1.func_190926_b() || itemstack1.func_190916_E() < itemstack.func_190916_E())
/*      */           {
/* 1292 */             itemstack.func_190915_d(5);
/*      */           }
/*      */         } 
/*      */         
/* 1296 */         ((EntityPlayer)entityPlayerSP).inventoryContainer.putStackInSlot(i, itemstack);
/*      */       }
/* 1298 */       else if (packetIn.getWindowId() == ((EntityPlayer)entityPlayerSP).openContainer.windowId && (packetIn.getWindowId() != 0 || !flag)) {
/*      */         
/* 1300 */         ((EntityPlayer)entityPlayerSP).openContainer.putStackInSlot(i, itemstack);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleConfirmTransaction(SPacketConfirmTransaction packetIn) {
/* 1311 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1312 */     Container container = null;
/* 1313 */     EntityPlayerSP entityPlayerSP = this.gameController.player;
/*      */     
/* 1315 */     if (packetIn.getWindowId() == 0) {
/*      */       
/* 1317 */       container = ((EntityPlayer)entityPlayerSP).inventoryContainer;
/*      */     }
/* 1319 */     else if (packetIn.getWindowId() == ((EntityPlayer)entityPlayerSP).openContainer.windowId) {
/*      */       
/* 1321 */       container = ((EntityPlayer)entityPlayerSP).openContainer;
/*      */     } 
/*      */     
/* 1324 */     if (container != null && !packetIn.wasAccepted())
/*      */     {
/* 1326 */       sendPacket((Packet<?>)new CPacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), true));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleWindowItems(SPacketWindowItems packetIn) {
/* 1335 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1336 */     EntityPlayerSP entityPlayerSP = this.gameController.player;
/*      */     
/* 1338 */     if (packetIn.getWindowId() == 0) {
/*      */       
/* 1340 */       ((EntityPlayer)entityPlayerSP).inventoryContainer.func_190896_a(packetIn.getItemStacks());
/*      */     }
/* 1342 */     else if (packetIn.getWindowId() == ((EntityPlayer)entityPlayerSP).openContainer.windowId) {
/*      */       
/* 1344 */       ((EntityPlayer)entityPlayerSP).openContainer.func_190896_a(packetIn.getItemStacks());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSignEditorOpen(SPacketSignEditorOpen packetIn) {
/*      */     TileEntitySign tileEntitySign;
/* 1353 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1354 */     TileEntity tileentity = this.clientWorldController.getTileEntity(packetIn.getSignPosition());
/*      */     
/* 1356 */     if (!(tileentity instanceof TileEntitySign)) {
/*      */       
/* 1358 */       tileEntitySign = new TileEntitySign();
/* 1359 */       tileEntitySign.setWorldObj((World)this.clientWorldController);
/* 1360 */       tileEntitySign.setPos(packetIn.getSignPosition());
/*      */     } 
/*      */     
/* 1363 */     this.gameController.player.openEditSign(tileEntitySign);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUpdateTileEntity(SPacketUpdateTileEntity packetIn) {
/* 1372 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1374 */     if (this.gameController.world.isBlockLoaded(packetIn.getPos())) {
/*      */       
/* 1376 */       TileEntity tileentity = this.gameController.world.getTileEntity(packetIn.getPos());
/* 1377 */       int i = packetIn.getTileEntityType();
/* 1378 */       boolean flag = (i == 2 && tileentity instanceof net.minecraft.tileentity.TileEntityCommandBlock);
/*      */       
/* 1380 */       if ((i == 1 && tileentity instanceof net.minecraft.tileentity.TileEntityMobSpawner) || flag || (i == 3 && tileentity instanceof net.minecraft.tileentity.TileEntityBeacon) || (i == 4 && tileentity instanceof net.minecraft.tileentity.TileEntitySkull) || (i == 5 && tileentity instanceof net.minecraft.tileentity.TileEntityFlowerPot) || (i == 6 && tileentity instanceof net.minecraft.tileentity.TileEntityBanner) || (i == 7 && tileentity instanceof net.minecraft.tileentity.TileEntityStructure) || (i == 8 && tileentity instanceof net.minecraft.tileentity.TileEntityEndGateway) || (i == 9 && tileentity instanceof TileEntitySign) || (i == 10 && tileentity instanceof net.minecraft.tileentity.TileEntityShulkerBox) || (i == 11 && tileentity instanceof net.minecraft.tileentity.TileEntityBed))
/*      */       {
/* 1382 */         tileentity.readFromNBT(packetIn.getNbtCompound());
/*      */       }
/*      */       
/* 1385 */       if (flag && this.gameController.currentScreen instanceof GuiCommandBlock)
/*      */       {
/* 1387 */         ((GuiCommandBlock)this.gameController.currentScreen).updateGui();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleWindowProperty(SPacketWindowProperty packetIn) {
/* 1397 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1398 */     EntityPlayerSP entityPlayerSP = this.gameController.player;
/*      */     
/* 1400 */     if (((EntityPlayer)entityPlayerSP).openContainer != null && ((EntityPlayer)entityPlayerSP).openContainer.windowId == packetIn.getWindowId())
/*      */     {
/* 1402 */       ((EntityPlayer)entityPlayerSP).openContainer.updateProgressBar(packetIn.getProperty(), packetIn.getValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityEquipment(SPacketEntityEquipment packetIn) {
/* 1408 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1409 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/* 1411 */     if (entity != null)
/*      */     {
/* 1413 */       entity.setItemStackToSlot(packetIn.getEquipmentSlot(), packetIn.getItemStack());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleCloseWindow(SPacketCloseWindow packetIn) {
/* 1422 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1423 */     this.gameController.player.closeScreenAndDropStack();
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
/*      */   public void handleBlockAction(SPacketBlockAction packetIn) {
/* 1435 */     WDLHooks.onNHPCHandleBlockAction(this, packetIn);
/*      */ 
/*      */     
/* 1438 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1439 */     this.gameController.world.addBlockEvent(packetIn.getBlockPosition(), packetIn.getBlockType(), packetIn.getData1(), packetIn.getData2());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBlockBreakAnim(SPacketBlockBreakAnim packetIn) {
/* 1447 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1448 */     this.gameController.world.sendBlockBreakProgress(packetIn.getBreakerId(), packetIn.getPosition(), packetIn.getProgress());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleChangeGameState(SPacketChangeGameState packetIn) {
/* 1453 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1454 */     EntityPlayerSP entityPlayerSP = this.gameController.player;
/* 1455 */     int i = packetIn.getGameState();
/* 1456 */     float f = packetIn.getValue();
/* 1457 */     int j = MathHelper.floor(f + 0.5F);
/*      */     
/* 1459 */     if (i >= 0 && i < SPacketChangeGameState.MESSAGE_NAMES.length && SPacketChangeGameState.MESSAGE_NAMES[i] != null)
/*      */     {
/* 1461 */       entityPlayerSP.addChatComponentMessage((ITextComponent)new TextComponentTranslation(SPacketChangeGameState.MESSAGE_NAMES[i], new Object[0]), false);
/*      */     }
/*      */     
/* 1464 */     if (i == 1) {
/*      */       
/* 1466 */       this.clientWorldController.getWorldInfo().setRaining(true);
/* 1467 */       this.clientWorldController.setRainStrength(0.0F);
/*      */     }
/* 1469 */     else if (i == 2) {
/*      */       
/* 1471 */       this.clientWorldController.getWorldInfo().setRaining(false);
/* 1472 */       this.clientWorldController.setRainStrength(1.0F);
/*      */     }
/* 1474 */     else if (i == 3) {
/*      */       
/* 1476 */       this.gameController.playerController.setGameType(GameType.getByID(j));
/*      */     }
/* 1478 */     else if (i == 4) {
/*      */       
/* 1480 */       if (j == 0)
/*      */       {
/* 1482 */         this.gameController.player.connection.sendPacket((Packet<?>)new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN));
/* 1483 */         this.gameController.displayGuiScreen((GuiScreen)new GuiDownloadTerrain());
/*      */       }
/* 1485 */       else if (j == 1)
/*      */       {
/* 1487 */         this.gameController.displayGuiScreen((GuiScreen)new GuiWinGame(true, () -> this.gameController.player.connection.sendPacket((Packet<?>)new CPacketClientStatus(CPacketClientStatus.State.PERFORM_RESPAWN))));
/*      */       
/*      */       }
/*      */ 
/*      */     
/*      */     }
/* 1493 */     else if (i == 5) {
/*      */       
/* 1495 */       GameSettings gamesettings = this.gameController.gameSettings;
/*      */       
/* 1497 */       if (f == 0.0F)
/*      */       {
/* 1499 */         this.gameController.displayGuiScreen((GuiScreen)new GuiScreenDemo());
/*      */       }
/* 1501 */       else if (f == 101.0F)
/*      */       {
/* 1503 */         this.gameController.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentTranslation("demo.help.movement", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(gamesettings.keyBindRight.getKeyCode()) }));
/*      */       }
/* 1505 */       else if (f == 102.0F)
/*      */       {
/* 1507 */         this.gameController.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentTranslation("demo.help.jump", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindJump.getKeyCode()) }));
/*      */       }
/* 1509 */       else if (f == 103.0F)
/*      */       {
/* 1511 */         this.gameController.ingameGUI.getChatGUI().printChatMessage((ITextComponent)new TextComponentTranslation("demo.help.inventory", new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindInventory.getKeyCode()) }));
/*      */       }
/*      */     
/* 1514 */     } else if (i == 6) {
/*      */       
/* 1516 */       this.clientWorldController.playSound((EntityPlayer)entityPlayerSP, ((EntityPlayer)entityPlayerSP).posX, ((EntityPlayer)entityPlayerSP).posY + entityPlayerSP.getEyeHeight(), ((EntityPlayer)entityPlayerSP).posZ, SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 0.18F, 0.45F);
/*      */     }
/* 1518 */     else if (i == 7) {
/*      */       
/* 1520 */       this.clientWorldController.setRainStrength(f);
/*      */     }
/* 1522 */     else if (i == 8) {
/*      */       
/* 1524 */       this.clientWorldController.setThunderStrength(f);
/*      */     }
/* 1526 */     else if (i == 10) {
/*      */       
/* 1528 */       this.clientWorldController.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, ((EntityPlayer)entityPlayerSP).posX, ((EntityPlayer)entityPlayerSP).posY, ((EntityPlayer)entityPlayerSP).posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/* 1529 */       this.clientWorldController.playSound((EntityPlayer)entityPlayerSP, ((EntityPlayer)entityPlayerSP).posX, ((EntityPlayer)entityPlayerSP).posY, ((EntityPlayer)entityPlayerSP).posZ, SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.HOSTILE, 1.0F, 1.0F);
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
/*      */   public void handleMaps(SPacketMaps packetIn) {
/* 1541 */     WDLHooks.onNHPCHandleMaps(this, packetIn);
/*      */ 
/*      */     
/* 1544 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1545 */     MapItemRenderer mapitemrenderer = this.gameController.entityRenderer.getMapItemRenderer();
/* 1546 */     MapData mapdata = ItemMap.loadMapData(packetIn.getMapId(), (World)this.gameController.world);
/*      */     
/* 1548 */     if (mapdata == null) {
/*      */       
/* 1550 */       String s = "map_" + packetIn.getMapId();
/* 1551 */       mapdata = new MapData(s);
/*      */       
/* 1553 */       if (mapitemrenderer.func_191205_a(s) != null) {
/*      */         
/* 1555 */         MapData mapdata1 = mapitemrenderer.func_191207_a(mapitemrenderer.func_191205_a(s));
/*      */         
/* 1557 */         if (mapdata1 != null)
/*      */         {
/* 1559 */           mapdata = mapdata1;
/*      */         }
/*      */       } 
/*      */       
/* 1563 */       this.gameController.world.setItemData(s, (WorldSavedData)mapdata);
/*      */     } 
/*      */     
/* 1566 */     packetIn.setMapdataTo(mapdata);
/* 1567 */     mapitemrenderer.updateMapTexture(mapdata);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEffect(SPacketEffect packetIn) {
/* 1572 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1574 */     if (packetIn.isSoundServerwide()) {
/*      */       
/* 1576 */       this.gameController.world.playBroadcastSound(packetIn.getSoundType(), packetIn.getSoundPos(), packetIn.getSoundData());
/*      */     }
/*      */     else {
/*      */       
/* 1580 */       this.gameController.world.playEvent(packetIn.getSoundType(), packetIn.getSoundPos(), packetIn.getSoundData());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_191981_a(SPacketAdvancementInfo p_191981_1_) {
/* 1586 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)p_191981_1_, (INetHandler)this, (IThreadListener)this.gameController);
/* 1587 */     this.field_191983_k.func_192799_a(p_191981_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_194022_a(SPacketSelectAdvancementsTab p_194022_1_) {
/* 1592 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)p_194022_1_, (INetHandler)this, (IThreadListener)this.gameController);
/* 1593 */     ResourceLocation resourcelocation = p_194022_1_.func_194154_a();
/*      */     
/* 1595 */     if (resourcelocation == null) {
/*      */       
/* 1597 */       this.field_191983_k.func_194230_a(null, false);
/*      */     }
/*      */     else {
/*      */       
/* 1601 */       Advancement advancement = this.field_191983_k.func_194229_a().func_192084_a(resourcelocation);
/* 1602 */       this.field_191983_k.func_194230_a(advancement, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleStatistics(SPacketStatistics packetIn) {
/* 1611 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1613 */     for (Map.Entry<StatBase, Integer> entry : (Iterable<Map.Entry<StatBase, Integer>>)packetIn.getStatisticMap().entrySet()) {
/*      */       
/* 1615 */       StatBase statbase = entry.getKey();
/* 1616 */       int k = ((Integer)entry.getValue()).intValue();
/* 1617 */       this.gameController.player.getStatFileWriter().unlockAchievement((EntityPlayer)this.gameController.player, statbase, k);
/*      */     } 
/*      */     
/* 1620 */     this.hasStatistics = true;
/*      */     
/* 1622 */     if (this.gameController.currentScreen instanceof IProgressMeter)
/*      */     {
/* 1624 */       ((IProgressMeter)this.gameController.currentScreen).func_193026_g();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_191980_a(SPacketRecipeBook p_191980_1_) {
/*      */     Iterator<IRecipe> iterator;
/* 1631 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)p_191980_1_, (INetHandler)this, (IThreadListener)this.gameController);
/* 1632 */     RecipeBook recipebook = this.gameController.player.func_192035_E();
/* 1633 */     recipebook.func_192813_a(p_191980_1_.func_192593_c());
/* 1634 */     recipebook.func_192810_b(p_191980_1_.func_192594_d());
/* 1635 */     SPacketRecipeBook.State spacketrecipebook$state = p_191980_1_.func_194151_e();
/*      */ 
/*      */     
/* 1638 */     switch (spacketrecipebook$state) {
/*      */       
/*      */       case REMOVE:
/* 1641 */         iterator = p_191980_1_.func_192595_a().iterator();
/*      */ 
/*      */ 
/*      */         
/* 1645 */         while (iterator.hasNext()) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/* 1650 */           IRecipe irecipe = iterator.next();
/* 1651 */           recipebook.func_193831_b(irecipe);
/*      */         } 
/*      */         break;
/*      */       case INIT:
/* 1655 */         p_191980_1_.func_192595_a().forEach(recipebook::func_194073_a);
/* 1656 */         p_191980_1_.func_193644_b().forEach(recipebook::func_193825_e);
/*      */         break;
/*      */       
/*      */       case null:
/* 1660 */         p_191980_1_.func_192595_a().forEach(p_194025_2_ -> {
/*      */               paramRecipeBook.func_194073_a(p_194025_2_);
/*      */               paramRecipeBook.func_193825_e(p_194025_2_);
/*      */               RecipeToast.func_193665_a(this.gameController.func_193033_an(), p_194025_2_);
/*      */             });
/*      */         break;
/*      */     } 
/*      */     
/* 1668 */     RecipeBookClient.field_194087_f.forEach(p_194023_1_ -> p_194023_1_.func_194214_a(paramRecipeBook));
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1673 */     if (this.gameController.currentScreen instanceof IRecipeShownListener)
/*      */     {
/* 1675 */       ((IRecipeShownListener)this.gameController.currentScreen).func_192043_J_();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityEffect(SPacketEntityEffect packetIn) {
/* 1681 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1682 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 1684 */     if (entity instanceof EntityLivingBase) {
/*      */       
/* 1686 */       Potion potion = Potion.getPotionById(packetIn.getEffectId());
/*      */       
/* 1688 */       if (potion != null) {
/*      */         
/* 1690 */         PotionEffect potioneffect = new PotionEffect(potion, packetIn.getDuration(), packetIn.getAmplifier(), packetIn.getIsAmbient(), packetIn.doesShowParticles());
/* 1691 */         potioneffect.setPotionDurationMax(packetIn.isMaxDuration());
/* 1692 */         ((EntityLivingBase)entity).addPotionEffect(potioneffect);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleCombatEvent(SPacketCombatEvent packetIn) {
/* 1699 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1701 */     if (packetIn.eventType == SPacketCombatEvent.Event.ENTITY_DIED) {
/*      */       
/* 1703 */       Entity entity = this.clientWorldController.getEntityByID(packetIn.playerId);
/*      */       
/* 1705 */       if (entity == this.gameController.player)
/*      */       {
/* 1707 */         this.gameController.displayGuiScreen((GuiScreen)new GuiGameOver(packetIn.deathMessage));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleServerDifficulty(SPacketServerDifficulty packetIn) {
/* 1714 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1715 */     this.gameController.world.getWorldInfo().setDifficulty(packetIn.getDifficulty());
/* 1716 */     this.gameController.world.getWorldInfo().setDifficultyLocked(packetIn.isDifficultyLocked());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleCamera(SPacketCamera packetIn) {
/* 1721 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1722 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/* 1724 */     if (entity != null)
/*      */     {
/* 1726 */       this.gameController.setRenderViewEntity(entity);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleWorldBorder(SPacketWorldBorder packetIn) {
/* 1732 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1733 */     packetIn.apply(this.clientWorldController.getWorldBorder());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleTitle(SPacketTitle packetIn) {
/* 1739 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1740 */     SPacketTitle.Type spackettitle$type = packetIn.getType();
/* 1741 */     String s = null;
/* 1742 */     String s1 = null;
/* 1743 */     String s2 = (packetIn.getMessage() != null) ? packetIn.getMessage().getFormattedText() : "";
/*      */     
/* 1745 */     switch (spackettitle$type) {
/*      */       
/*      */       case TITLE:
/* 1748 */         s = s2;
/*      */         break;
/*      */       
/*      */       case SUBTITLE:
/* 1752 */         s1 = s2;
/*      */         break;
/*      */       
/*      */       case null:
/* 1756 */         this.gameController.ingameGUI.setRecordPlaying(s2, false);
/*      */         return;
/*      */       
/*      */       case RESET:
/* 1760 */         this.gameController.ingameGUI.displayTitle("", "", -1, -1, -1);
/* 1761 */         this.gameController.ingameGUI.setDefaultTitlesTimes();
/*      */         return;
/*      */     } 
/*      */     
/* 1765 */     this.gameController.ingameGUI.displayTitle(s, s1, packetIn.getFadeInTime(), packetIn.getDisplayTime(), packetIn.getFadeOutTime());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlayerListHeaderFooter(SPacketPlayerListHeaderFooter packetIn) {
/* 1770 */     this.gameController.ingameGUI.getTabList().setHeader(packetIn.getHeader().getFormattedText().isEmpty() ? null : packetIn.getHeader());
/* 1771 */     this.gameController.ingameGUI.getTabList().setFooter(packetIn.getFooter().getFormattedText().isEmpty() ? null : packetIn.getFooter());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleRemoveEntityEffect(SPacketRemoveEntityEffect packetIn) {
/* 1776 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1777 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/* 1779 */     if (entity instanceof EntityLivingBase)
/*      */     {
/* 1781 */       ((EntityLivingBase)entity).removeActivePotionEffect(packetIn.getPotion());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handlePlayerListItem(SPacketPlayerListItem packetIn) {
/* 1788 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1790 */     for (SPacketPlayerListItem.AddPlayerData spacketplayerlistitem$addplayerdata : packetIn.getEntries()) {
/*      */       
/* 1792 */       if (packetIn.getAction() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
/*      */         
/* 1794 */         this.playerInfoMap.remove(spacketplayerlistitem$addplayerdata.getProfile().getId());
/*      */         
/*      */         continue;
/*      */       } 
/* 1798 */       NetworkPlayerInfo networkplayerinfo = this.playerInfoMap.get(spacketplayerlistitem$addplayerdata.getProfile().getId());
/*      */       
/* 1800 */       if (packetIn.getAction() == SPacketPlayerListItem.Action.ADD_PLAYER) {
/*      */         
/* 1802 */         networkplayerinfo = new NetworkPlayerInfo(spacketplayerlistitem$addplayerdata);
/* 1803 */         this.playerInfoMap.put(networkplayerinfo.getGameProfile().getId(), networkplayerinfo);
/*      */       } 
/*      */       
/* 1806 */       if (networkplayerinfo != null)
/*      */       {
/* 1808 */         switch (packetIn.getAction()) {
/*      */           
/*      */           case null:
/* 1811 */             networkplayerinfo.setGameType(spacketplayerlistitem$addplayerdata.getGameMode());
/* 1812 */             networkplayerinfo.setResponseTime(spacketplayerlistitem$addplayerdata.getPing());
/*      */ 
/*      */           
/*      */           case UPDATE_GAME_MODE:
/* 1816 */             networkplayerinfo.setGameType(spacketplayerlistitem$addplayerdata.getGameMode());
/*      */ 
/*      */           
/*      */           case UPDATE_LATENCY:
/* 1820 */             networkplayerinfo.setResponseTime(spacketplayerlistitem$addplayerdata.getPing());
/*      */ 
/*      */           
/*      */           case UPDATE_DISPLAY_NAME:
/* 1824 */             networkplayerinfo.setDisplayName(spacketplayerlistitem$addplayerdata.getDisplayName());
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleKeepAlive(SPacketKeepAlive packetIn) {
/* 1833 */     sendPacket((Packet<?>)new CPacketKeepAlive(packetIn.getId()));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlayerAbilities(SPacketPlayerAbilities packetIn) {
/* 1838 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1839 */     EntityPlayerSP entityPlayerSP = this.gameController.player;
/* 1840 */     ((EntityPlayer)entityPlayerSP).capabilities.isFlying = packetIn.isFlying();
/* 1841 */     ((EntityPlayer)entityPlayerSP).capabilities.isCreativeMode = packetIn.isCreativeMode();
/* 1842 */     ((EntityPlayer)entityPlayerSP).capabilities.disableDamage = packetIn.isInvulnerable();
/* 1843 */     ((EntityPlayer)entityPlayerSP).capabilities.allowFlying = packetIn.isAllowFlying();
/* 1844 */     ((EntityPlayer)entityPlayerSP).capabilities.setFlySpeed(packetIn.getFlySpeed());
/* 1845 */     ((EntityPlayer)entityPlayerSP).capabilities.setPlayerWalkSpeed(packetIn.getWalkSpeed());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleTabComplete(SPacketTabComplete packetIn) {
/* 1853 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1854 */     String[] astring = packetIn.getMatches();
/* 1855 */     Arrays.sort((Object[])astring);
/*      */     
/* 1857 */     if (this.gameController.currentScreen instanceof ITabCompleter)
/*      */     {
/* 1859 */       ((ITabCompleter)this.gameController.currentScreen).setCompletions(astring);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSoundEffect(SPacketSoundEffect packetIn) {
/* 1865 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1866 */     this.gameController.world.playSound((EntityPlayer)this.gameController.player, packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getSound(), packetIn.getCategory(), packetIn.getVolume(), packetIn.getPitch());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleCustomSound(SPacketCustomSound packetIn) {
/* 1871 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1872 */     this.gameController.getSoundHandler().playSound((ISound)new PositionedSoundRecord(new ResourceLocation(packetIn.getSoundName()), packetIn.getCategory(), packetIn.getVolume(), packetIn.getPitch(), false, 0, ISound.AttenuationType.LINEAR, (float)packetIn.getX(), (float)packetIn.getY(), (float)packetIn.getZ()));
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleResourcePack(SPacketResourcePackSend packetIn) {
/* 1877 */     final String s = packetIn.getURL();
/* 1878 */     final String s1 = packetIn.getHash();
/*      */     
/* 1880 */     if (validateResourcePackUrl(s))
/*      */     {
/* 1882 */       if (s.startsWith("level://")) {
/*      */ 
/*      */         
/*      */         try {
/* 1886 */           String s2 = URLDecoder.decode(s.substring("level://".length()), StandardCharsets.UTF_8.toString());
/* 1887 */           File file1 = new File(this.gameController.mcDataDir, "saves");
/* 1888 */           File file2 = new File(file1, s2);
/*      */           
/* 1890 */           if (file2.isFile()) {
/*      */             
/* 1892 */             this.netManager.sendPacket((Packet)new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.ACCEPTED));
/* 1893 */             Futures.addCallback(this.gameController.getResourcePackRepository().setResourcePackInstance(file2), createDownloadCallback());
/*      */             
/*      */             return;
/*      */           } 
/* 1897 */         } catch (UnsupportedEncodingException unsupportedEncodingException) {}
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1902 */         this.netManager.sendPacket((Packet)new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */       }
/*      */       else {
/*      */         
/* 1906 */         ServerData serverdata = this.gameController.getCurrentServerData();
/*      */         
/* 1908 */         if (serverdata != null && serverdata.getResourceMode() == ServerData.ServerResourceMode.ENABLED) {
/*      */           
/* 1910 */           this.netManager.sendPacket((Packet)new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.ACCEPTED));
/* 1911 */           Futures.addCallback(this.gameController.getResourcePackRepository().downloadResourcePack(s, s1), createDownloadCallback());
/*      */         }
/* 1913 */         else if (serverdata != null && serverdata.getResourceMode() != ServerData.ServerResourceMode.PROMPT) {
/*      */           
/* 1915 */           this.netManager.sendPacket((Packet)new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.DECLINED));
/*      */         }
/*      */         else {
/*      */           
/* 1919 */           this.gameController.addScheduledTask(new Runnable()
/*      */               {
/*      */                 public void run()
/*      */                 {
/* 1923 */                   NetHandlerPlayClient.this.gameController.displayGuiScreen((GuiScreen)new GuiYesNo(new GuiYesNoCallback()
/*      */                         {
/*      */                           public void confirmClicked(boolean result, int id)
/*      */                           {
/* 1927 */                             (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController = Minecraft.getMinecraft();
/* 1928 */                             ServerData serverdata1 = (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.getCurrentServerData();
/*      */                             
/* 1930 */                             if (result) {
/*      */                               
/* 1932 */                               if (serverdata1 != null)
/*      */                               {
/* 1934 */                                 serverdata1.setResourceMode(ServerData.ServerResourceMode.ENABLED);
/*      */                               }
/*      */                               
/* 1937 */                               (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).netManager.sendPacket((Packet)new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.ACCEPTED));
/* 1938 */                               Futures.addCallback((NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.getResourcePackRepository().downloadResourcePack(s, s1), NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this).createDownloadCallback());
/*      */                             }
/*      */                             else {
/*      */                               
/* 1942 */                               if (serverdata1 != null)
/*      */                               {
/* 1944 */                                 serverdata1.setResourceMode(ServerData.ServerResourceMode.DISABLED);
/*      */                               }
/*      */                               
/* 1947 */                               (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).netManager.sendPacket((Packet)new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.DECLINED));
/*      */                             } 
/*      */                             
/* 1950 */                             ServerList.saveSingleServer(serverdata1);
/* 1951 */                             (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.displayGuiScreen(null);
/*      */                           }
/* 1953 */                         }I18n.format("multiplayer.texturePrompt.line1", new Object[0]), I18n.format("multiplayer.texturePrompt.line2", new Object[0]), 0));
/*      */                 }
/*      */               });
/*      */         } 
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean validateResourcePackUrl(String p_189688_1_) {
/*      */     try {
/* 1965 */       URI uri = new URI(p_189688_1_);
/* 1966 */       String s = uri.getScheme();
/* 1967 */       boolean flag = "level".equals(s);
/*      */       
/* 1969 */       if (!"http".equals(s) && !"https".equals(s) && !flag)
/*      */       {
/* 1971 */         throw new URISyntaxException(p_189688_1_, "Wrong protocol");
/*      */       }
/* 1973 */       if (!flag || (!p_189688_1_.contains("..") && p_189688_1_.endsWith("/resources.zip")))
/*      */       {
/* 1975 */         return true;
/*      */       }
/*      */ 
/*      */       
/* 1979 */       throw new URISyntaxException(p_189688_1_, "Invalid levelstorage resourcepack path");
/*      */     
/*      */     }
/* 1982 */     catch (URISyntaxException var5) {
/*      */       
/* 1984 */       this.netManager.sendPacket((Packet)new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/* 1985 */       return false;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private FutureCallback<Object> createDownloadCallback() {
/* 1991 */     return new FutureCallback<Object>()
/*      */       {
/*      */         public void onSuccess(@Nullable Object p_onSuccess_1_)
/*      */         {
/* 1995 */           NetHandlerPlayClient.this.netManager.sendPacket((Packet)new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
/*      */         }
/*      */         
/*      */         public void onFailure(Throwable p_onFailure_1_) {
/* 1999 */           NetHandlerPlayClient.this.netManager.sendPacket((Packet)new CPacketResourcePackStatus(CPacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleUpdateEntityNBT(SPacketUpdateBossInfo packetIn) {
/* 2006 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 2007 */     this.gameController.ingameGUI.getBossOverlay().read(packetIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleCooldown(SPacketCooldown packetIn) {
/* 2012 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 2014 */     if (packetIn.getTicks() == 0) {
/*      */       
/* 2016 */       this.gameController.player.getCooldownTracker().removeCooldown(packetIn.getItem());
/*      */     }
/*      */     else {
/*      */       
/* 2020 */       this.gameController.player.getCooldownTracker().setCooldown(packetIn.getItem(), packetIn.getTicks());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleMoveVehicle(SPacketMoveVehicle packetIn) {
/* 2026 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 2027 */     Entity entity = this.gameController.player.getLowestRidingEntity();
/*      */     
/* 2029 */     if (entity != this.gameController.player && entity.canPassengerSteer()) {
/*      */       
/* 2031 */       entity.setPositionAndRotation(packetIn.getX(), packetIn.getY(), packetIn.getZ(), packetIn.getYaw(), packetIn.getPitch());
/* 2032 */       this.netManager.sendPacket((Packet)new CPacketVehicleMove(entity));
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
/*      */   public void handleCustomPayload(SPacketCustomPayload packetIn) {
/* 2046 */     WDLHooks.onNHPCHandleCustomPayload(this, packetIn);
/*      */ 
/*      */     
/* 2049 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 2051 */     if ("MC|TrList".equals(packetIn.getChannelName())) {
/*      */       
/* 2053 */       PacketBuffer packetbuffer = packetIn.getBufferData();
/*      */ 
/*      */       
/*      */       try {
/* 2057 */         int k = packetbuffer.readInt();
/* 2058 */         GuiScreen guiscreen = this.gameController.currentScreen;
/*      */         
/* 2060 */         if (guiscreen != null && guiscreen instanceof GuiMerchant && k == this.gameController.player.openContainer.windowId)
/*      */         {
/* 2062 */           IMerchant imerchant = ((GuiMerchant)guiscreen).getMerchant();
/* 2063 */           MerchantRecipeList merchantrecipelist = MerchantRecipeList.readFromBuf(packetbuffer);
/* 2064 */           imerchant.setRecipes(merchantrecipelist);
/*      */         }
/*      */       
/* 2067 */       } catch (IOException ioexception) {
/*      */         
/* 2069 */         LOGGER.error("Couldn't load trade info", ioexception);
/*      */       }
/*      */       finally {
/*      */         
/* 2073 */         packetbuffer.release();
/*      */       }
/*      */     
/* 2076 */     } else if ("MC|Brand".equals(packetIn.getChannelName())) {
/*      */       
/* 2078 */       this.gameController.player.setServerBrand(packetIn.getBufferData().readStringFromBuffer(32767));
/*      */     }
/* 2080 */     else if ("MC|BOpen".equals(packetIn.getChannelName())) {
/*      */       
/* 2082 */       EnumHand enumhand = (EnumHand)packetIn.getBufferData().readEnumValue(EnumHand.class);
/* 2083 */       ItemStack itemstack = (enumhand == EnumHand.OFF_HAND) ? this.gameController.player.getHeldItemOffhand() : this.gameController.player.getHeldItemMainhand();
/*      */       
/* 2085 */       if (itemstack.getItem() == Items.WRITTEN_BOOK)
/*      */       {
/* 2087 */         this.gameController.displayGuiScreen((GuiScreen)new GuiScreenBook((EntityPlayer)this.gameController.player, itemstack, false));
/*      */       }
/*      */     }
/* 2090 */     else if ("MC|DebugPath".equals(packetIn.getChannelName())) {
/*      */       
/* 2092 */       PacketBuffer packetbuffer1 = packetIn.getBufferData();
/* 2093 */       int l = packetbuffer1.readInt();
/* 2094 */       float f1 = packetbuffer1.readFloat();
/* 2095 */       Path path = Path.read(packetbuffer1);
/* 2096 */       ((DebugRendererPathfinding)this.gameController.debugRenderer.debugRendererPathfinding).addPath(l, path, f1);
/*      */     }
/* 2098 */     else if ("MC|DebugNeighborsUpdate".equals(packetIn.getChannelName())) {
/*      */       
/* 2100 */       PacketBuffer packetbuffer2 = packetIn.getBufferData();
/* 2101 */       long i1 = packetbuffer2.readVarLong();
/* 2102 */       BlockPos blockpos = packetbuffer2.readBlockPos();
/* 2103 */       ((DebugRendererNeighborsUpdate)this.gameController.debugRenderer.field_191557_f).func_191553_a(i1, blockpos);
/*      */     }
/* 2105 */     else if ("MC|StopSound".equals(packetIn.getChannelName())) {
/*      */       
/* 2107 */       PacketBuffer packetbuffer3 = packetIn.getBufferData();
/* 2108 */       String s = packetbuffer3.readStringFromBuffer(32767);
/* 2109 */       String s1 = packetbuffer3.readStringFromBuffer(256);
/* 2110 */       this.gameController.getSoundHandler().stop(s1, SoundCategory.getByName(s));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleScoreboardObjective(SPacketScoreboardObjective packetIn) {
/* 2119 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 2120 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */     
/* 2122 */     if (packetIn.getAction() == 0) {
/*      */       
/* 2124 */       ScoreObjective scoreobjective = scoreboard.addScoreObjective(packetIn.getObjectiveName(), IScoreCriteria.DUMMY);
/* 2125 */       scoreobjective.setDisplayName(packetIn.getObjectiveValue());
/* 2126 */       scoreobjective.setRenderType(packetIn.getRenderType());
/*      */     }
/*      */     else {
/*      */       
/* 2130 */       ScoreObjective scoreobjective1 = scoreboard.getObjective(packetIn.getObjectiveName());
/*      */       
/* 2132 */       if (packetIn.getAction() == 1) {
/*      */         
/* 2134 */         scoreboard.removeObjective(scoreobjective1);
/*      */       }
/* 2136 */       else if (packetIn.getAction() == 2) {
/*      */         
/* 2138 */         scoreobjective1.setDisplayName(packetIn.getObjectiveValue());
/* 2139 */         scoreobjective1.setRenderType(packetIn.getRenderType());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUpdateScore(SPacketUpdateScore packetIn) {
/* 2149 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 2150 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/* 2151 */     ScoreObjective scoreobjective = scoreboard.getObjective(packetIn.getObjectiveName());
/*      */     
/* 2153 */     if (packetIn.getScoreAction() == SPacketUpdateScore.Action.CHANGE) {
/*      */       
/* 2155 */       Score score = scoreboard.getOrCreateScore(packetIn.getPlayerName(), scoreobjective);
/* 2156 */       score.setScorePoints(packetIn.getScoreValue());
/*      */     }
/* 2158 */     else if (packetIn.getScoreAction() == SPacketUpdateScore.Action.REMOVE) {
/*      */       
/* 2160 */       if (StringUtils.isNullOrEmpty(packetIn.getObjectiveName())) {
/*      */         
/* 2162 */         scoreboard.removeObjectiveFromEntity(packetIn.getPlayerName(), null);
/*      */       }
/* 2164 */       else if (scoreobjective != null) {
/*      */         
/* 2166 */         scoreboard.removeObjectiveFromEntity(packetIn.getPlayerName(), scoreobjective);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDisplayObjective(SPacketDisplayObjective packetIn) {
/* 2177 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 2178 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */     
/* 2180 */     if (packetIn.getName().isEmpty()) {
/*      */       
/* 2182 */       scoreboard.setObjectiveInDisplaySlot(packetIn.getPosition(), null);
/*      */     }
/*      */     else {
/*      */       
/* 2186 */       ScoreObjective scoreobjective = scoreboard.getObjective(packetIn.getName());
/* 2187 */       scoreboard.setObjectiveInDisplaySlot(packetIn.getPosition(), scoreobjective);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleTeams(SPacketTeams packetIn) {
/*      */     ScorePlayerTeam scoreplayerteam;
/* 2197 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 2198 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */ 
/*      */     
/* 2201 */     if (packetIn.getAction() == 0) {
/*      */       
/* 2203 */       scoreplayerteam = scoreboard.createTeam(packetIn.getName());
/*      */     }
/*      */     else {
/*      */       
/* 2207 */       scoreplayerteam = scoreboard.getTeam(packetIn.getName());
/*      */     } 
/*      */     
/* 2210 */     if (packetIn.getAction() == 0 || packetIn.getAction() == 2) {
/*      */       
/* 2212 */       scoreplayerteam.setTeamName(packetIn.getDisplayName());
/* 2213 */       scoreplayerteam.setNamePrefix(packetIn.getPrefix());
/* 2214 */       scoreplayerteam.setNameSuffix(packetIn.getSuffix());
/* 2215 */       scoreplayerteam.setChatFormat(TextFormatting.fromColorIndex(packetIn.getColor()));
/* 2216 */       scoreplayerteam.setFriendlyFlags(packetIn.getFriendlyFlags());
/* 2217 */       Team.EnumVisible team$enumvisible = Team.EnumVisible.getByName(packetIn.getNameTagVisibility());
/*      */       
/* 2219 */       if (team$enumvisible != null)
/*      */       {
/* 2221 */         scoreplayerteam.setNameTagVisibility(team$enumvisible);
/*      */       }
/*      */       
/* 2224 */       Team.CollisionRule team$collisionrule = Team.CollisionRule.getByName(packetIn.getCollisionRule());
/*      */       
/* 2226 */       if (team$collisionrule != null)
/*      */       {
/* 2228 */         scoreplayerteam.setCollisionRule(team$collisionrule);
/*      */       }
/*      */     } 
/*      */     
/* 2232 */     if (packetIn.getAction() == 0 || packetIn.getAction() == 3)
/*      */     {
/* 2234 */       for (String s : packetIn.getPlayers())
/*      */       {
/* 2236 */         scoreboard.addPlayerToTeam(s, packetIn.getName());
/*      */       }
/*      */     }
/*      */     
/* 2240 */     if (packetIn.getAction() == 4)
/*      */     {
/* 2242 */       for (String s1 : packetIn.getPlayers())
/*      */       {
/* 2244 */         scoreboard.removePlayerFromTeam(s1, scoreplayerteam);
/*      */       }
/*      */     }
/*      */     
/* 2248 */     if (packetIn.getAction() == 1)
/*      */     {
/* 2250 */       scoreboard.removeTeam(scoreplayerteam);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleParticles(SPacketParticles packetIn) {
/* 2261 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 2262 */     if (packetIn.getParticleCount() == 0) {
/* 2263 */       double d0 = (packetIn.getParticleSpeed() * packetIn.getXOffset());
/* 2264 */       double d2 = (packetIn.getParticleSpeed() * packetIn.getYOffset());
/* 2265 */       double d4 = (packetIn.getParticleSpeed() * packetIn.getZOffset());
/*      */       try {
/* 2267 */         this.clientWorldController.spawnParticle(packetIn.getParticleType(), packetIn.isLongDistance(), packetIn.getXCoordinate(), packetIn.getYCoordinate(), packetIn.getZCoordinate(), d0, d2, d4, packetIn.getParticleArgs());
/*      */       }
/* 2269 */       catch (Throwable var17) {
/* 2270 */         LOGGER.warn("Could not spawn particle effect {}", packetIn.getParticleType());
/*      */       } 
/*      */     } else {
/* 2273 */       for (int k = 0; k < packetIn.getParticleCount(); k++) {
/* 2274 */         double d1 = this.avRandomizer.nextGaussian() * packetIn.getXOffset();
/* 2275 */         double d3 = this.avRandomizer.nextGaussian() * packetIn.getYOffset();
/* 2276 */         double d5 = this.avRandomizer.nextGaussian() * packetIn.getZOffset();
/* 2277 */         double d6 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/* 2278 */         double d7 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/* 2279 */         double d8 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/*      */         try {
/* 2281 */           this.clientWorldController.spawnParticle(packetIn.getParticleType(), packetIn.isLongDistance(), packetIn.getXCoordinate() + d1, packetIn.getYCoordinate() + d3, packetIn.getZCoordinate() + d5, d6, d7, d8, packetIn.getParticleArgs());
/*      */         
/*      */         }
/* 2284 */         catch (Throwable var16) {
/* 2285 */           LOGGER.warn("Could not spawn particle effect {}", packetIn.getParticleType());
/*      */           return;
/*      */         } 
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
/*      */   public void handleEntityProperties(SPacketEntityProperties packetIn) {
/* 2300 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 2301 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 2303 */     if (entity != null) {
/*      */       
/* 2305 */       if (!(entity instanceof EntityLivingBase))
/*      */       {
/* 2307 */         throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + entity + ")");
/*      */       }
/*      */ 
/*      */       
/* 2311 */       AbstractAttributeMap abstractattributemap = ((EntityLivingBase)entity).getAttributeMap();
/*      */       
/* 2313 */       for (SPacketEntityProperties.Snapshot spacketentityproperties$snapshot : packetIn.getSnapshots()) {
/*      */         
/* 2315 */         IAttributeInstance iattributeinstance = abstractattributemap.getAttributeInstanceByName(spacketentityproperties$snapshot.getName());
/*      */         
/* 2317 */         if (iattributeinstance == null)
/*      */         {
/* 2319 */           iattributeinstance = abstractattributemap.registerAttribute((IAttribute)new RangedAttribute(null, spacketentityproperties$snapshot.getName(), 0.0D, 2.2250738585072014E-308D, Double.MAX_VALUE));
/*      */         }
/*      */         
/* 2322 */         iattributeinstance.setBaseValue(spacketentityproperties$snapshot.getBaseValue());
/* 2323 */         iattributeinstance.removeAllModifiers();
/*      */         
/* 2325 */         for (AttributeModifier attributemodifier : spacketentityproperties$snapshot.getModifiers())
/*      */         {
/* 2327 */           iattributeinstance.applyModifier(attributemodifier);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void func_194307_a(SPacketPlaceGhostRecipe p_194307_1_) {
/* 2336 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)p_194307_1_, (INetHandler)this, (IThreadListener)this.gameController);
/* 2337 */     Container container = this.gameController.player.openContainer;
/*      */     
/* 2339 */     if (container.windowId == p_194307_1_.func_194313_b() && container.getCanCraft((EntityPlayer)this.gameController.player))
/*      */     {
/* 2341 */       if (this.gameController.currentScreen instanceof IRecipeShownListener) {
/*      */         
/* 2343 */         GuiRecipeBook guirecipebook = ((IRecipeShownListener)this.gameController.currentScreen).func_194310_f();
/* 2344 */         guirecipebook.func_193951_a(p_194307_1_.func_194311_a(), container.inventorySlots);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NetworkManager getNetworkManager() {
/* 2354 */     return this.netManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public Collection<NetworkPlayerInfo> getPlayerInfoMap() {
/* 2359 */     return this.playerInfoMap.values();
/*      */   }
/*      */ 
/*      */   
/*      */   public NetworkPlayerInfo getPlayerInfo(UUID uniqueId) {
/* 2364 */     return this.playerInfoMap.get(uniqueId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public NetworkPlayerInfo getPlayerInfo(String name) {
/* 2374 */     for (NetworkPlayerInfo networkplayerinfo : this.playerInfoMap.values()) {
/*      */       
/* 2376 */       if (networkplayerinfo.getGameProfile().getName().equals(name))
/*      */       {
/* 2378 */         return networkplayerinfo;
/*      */       }
/*      */     } 
/*      */     
/* 2382 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public GameProfile getGameProfile() {
/* 2387 */     return this.profile;
/*      */   }
/*      */ 
/*      */   
/*      */   public ClientAdvancementManager func_191982_f() {
/* 2392 */     return this.field_191983_k;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\network\NetHandlerPlayClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */