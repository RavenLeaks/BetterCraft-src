/*      */ package net.minecraft.server.management;
/*      */ 
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import java.io.File;
/*      */ import java.net.SocketAddress;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.UUID;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.advancements.CriteriaTriggers;
/*      */ import net.minecraft.advancements.PlayerAdvancements;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.NetHandlerPlayServer;
/*      */ import net.minecraft.network.NetworkManager;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.PacketBuffer;
/*      */ import net.minecraft.network.play.server.SPacketChangeGameState;
/*      */ import net.minecraft.network.play.server.SPacketChat;
/*      */ import net.minecraft.network.play.server.SPacketCustomPayload;
/*      */ import net.minecraft.network.play.server.SPacketEntityEffect;
/*      */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*      */ import net.minecraft.network.play.server.SPacketHeldItemChange;
/*      */ import net.minecraft.network.play.server.SPacketJoinGame;
/*      */ import net.minecraft.network.play.server.SPacketPlayerAbilities;
/*      */ import net.minecraft.network.play.server.SPacketPlayerListItem;
/*      */ import net.minecraft.network.play.server.SPacketRespawn;
/*      */ import net.minecraft.network.play.server.SPacketServerDifficulty;
/*      */ import net.minecraft.network.play.server.SPacketSetExperience;
/*      */ import net.minecraft.network.play.server.SPacketSpawnPosition;
/*      */ import net.minecraft.network.play.server.SPacketTeams;
/*      */ import net.minecraft.network.play.server.SPacketTimeUpdate;
/*      */ import net.minecraft.network.play.server.SPacketWorldBorder;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.ServerScoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.stats.StatList;
/*      */ import net.minecraft.stats.StatisticsManagerServer;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.text.ChatType;
/*      */ import net.minecraft.util.text.ITextComponent;
/*      */ import net.minecraft.util.text.TextComponentTranslation;
/*      */ import net.minecraft.util.text.TextFormatting;
/*      */ import net.minecraft.world.DimensionType;
/*      */ import net.minecraft.world.GameType;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import net.minecraft.world.border.IBorderListener;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import net.minecraft.world.chunk.storage.AnvilChunkLoader;
/*      */ import net.minecraft.world.storage.IPlayerFileData;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public abstract class PlayerList
/*      */ {
/*   70 */   public static final File FILE_PLAYERBANS = new File("banned-players.json");
/*   71 */   public static final File FILE_IPBANS = new File("banned-ips.json");
/*   72 */   public static final File FILE_OPS = new File("ops.json");
/*   73 */   public static final File FILE_WHITELIST = new File("whitelist.json");
/*   74 */   private static final Logger LOG = LogManager.getLogger();
/*   75 */   private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
/*      */   
/*      */   private final MinecraftServer mcServer;
/*      */   
/*   79 */   private final List<EntityPlayerMP> playerEntityList = Lists.newArrayList();
/*   80 */   private final Map<UUID, EntityPlayerMP> uuidToPlayerMap = Maps.newHashMap();
/*      */ 
/*      */   
/*      */   private final UserListBans bannedPlayers;
/*      */ 
/*      */   
/*      */   private final UserListIPBans bannedIPs;
/*      */ 
/*      */   
/*      */   private final UserListOps ops;
/*      */ 
/*      */   
/*      */   private final UserListWhitelist whiteListedPlayers;
/*      */ 
/*      */   
/*      */   private final Map<UUID, StatisticsManagerServer> playerStatFiles;
/*      */   
/*      */   private final Map<UUID, PlayerAdvancements> field_192055_p;
/*      */   
/*      */   private IPlayerFileData playerNBTManagerObj;
/*      */   
/*      */   private boolean whiteListEnforced;
/*      */   
/*      */   protected int maxPlayers;
/*      */   
/*      */   private int viewDistance;
/*      */   
/*      */   private GameType gameType;
/*      */   
/*      */   private boolean commandsAllowedForAll;
/*      */   
/*      */   private int playerPingIndex;
/*      */ 
/*      */   
/*      */   public PlayerList(MinecraftServer server) {
/*  115 */     this.bannedPlayers = new UserListBans(FILE_PLAYERBANS);
/*  116 */     this.bannedIPs = new UserListIPBans(FILE_IPBANS);
/*  117 */     this.ops = new UserListOps(FILE_OPS);
/*  118 */     this.whiteListedPlayers = new UserListWhitelist(FILE_WHITELIST);
/*  119 */     this.playerStatFiles = Maps.newHashMap();
/*  120 */     this.field_192055_p = Maps.newHashMap();
/*  121 */     this.mcServer = server;
/*  122 */     this.bannedPlayers.setLanServer(false);
/*  123 */     this.bannedIPs.setLanServer(false);
/*  124 */     this.maxPlayers = 8;
/*      */   }
/*      */   
/*      */   public void initializeConnectionToPlayer(NetworkManager netManager, EntityPlayerMP playerIn) {
/*      */     TextComponentTranslation textcomponenttranslation;
/*  129 */     GameProfile gameprofile = playerIn.getGameProfile();
/*  130 */     PlayerProfileCache playerprofilecache = this.mcServer.getPlayerProfileCache();
/*  131 */     GameProfile gameprofile1 = playerprofilecache.getProfileByUUID(gameprofile.getId());
/*  132 */     String s = (gameprofile1 == null) ? gameprofile.getName() : gameprofile1.getName();
/*  133 */     playerprofilecache.addEntry(gameprofile);
/*  134 */     NBTTagCompound nbttagcompound = readPlayerDataFromFile(playerIn);
/*  135 */     playerIn.setWorld((World)this.mcServer.worldServerForDimension(playerIn.dimension));
/*  136 */     playerIn.interactionManager.setWorld((WorldServer)playerIn.world);
/*  137 */     String s1 = "local";
/*      */     
/*  139 */     if (netManager.getRemoteAddress() != null)
/*      */     {
/*  141 */       s1 = netManager.getRemoteAddress().toString();
/*      */     }
/*      */     
/*  144 */     LOG.info("{}[{}] logged in with entity id {} at ({}, {}, {})", playerIn.getName(), s1, Integer.valueOf(playerIn.getEntityId()), Double.valueOf(playerIn.posX), Double.valueOf(playerIn.posY), Double.valueOf(playerIn.posZ));
/*  145 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/*  146 */     WorldInfo worldinfo = worldserver.getWorldInfo();
/*  147 */     setPlayerGameTypeBasedOnOther(playerIn, null, (World)worldserver);
/*  148 */     NetHandlerPlayServer nethandlerplayserver = new NetHandlerPlayServer(this.mcServer, netManager, playerIn);
/*  149 */     nethandlerplayserver.sendPacket((Packet)new SPacketJoinGame(playerIn.getEntityId(), playerIn.interactionManager.getGameType(), worldinfo.isHardcoreModeEnabled(), worldserver.provider.getDimensionType().getId(), worldserver.getDifficulty(), getMaxPlayers(), worldinfo.getTerrainType(), worldserver.getGameRules().getBoolean("reducedDebugInfo")));
/*  150 */     nethandlerplayserver.sendPacket((Packet)new SPacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(getServerInstance().getServerModName())));
/*  151 */     nethandlerplayserver.sendPacket((Packet)new SPacketServerDifficulty(worldinfo.getDifficulty(), worldinfo.isDifficultyLocked()));
/*  152 */     nethandlerplayserver.sendPacket((Packet)new SPacketPlayerAbilities(playerIn.capabilities));
/*  153 */     nethandlerplayserver.sendPacket((Packet)new SPacketHeldItemChange(playerIn.inventory.currentItem));
/*  154 */     updatePermissionLevel(playerIn);
/*  155 */     playerIn.getStatFile().markAllDirty();
/*  156 */     playerIn.func_192037_E().func_192826_c(playerIn);
/*  157 */     sendScoreboard((ServerScoreboard)worldserver.getScoreboard(), playerIn);
/*  158 */     this.mcServer.refreshStatusNextTick();
/*      */ 
/*      */     
/*  161 */     if (playerIn.getName().equalsIgnoreCase(s)) {
/*      */       
/*  163 */       textcomponenttranslation = new TextComponentTranslation("multiplayer.player.joined", new Object[] { playerIn.getDisplayName() });
/*      */     }
/*      */     else {
/*      */       
/*  167 */       textcomponenttranslation = new TextComponentTranslation("multiplayer.player.joined.renamed", new Object[] { playerIn.getDisplayName(), s });
/*      */     } 
/*      */     
/*  170 */     textcomponenttranslation.getStyle().setColor(TextFormatting.YELLOW);
/*  171 */     sendChatMsg((ITextComponent)textcomponenttranslation);
/*  172 */     playerLoggedIn(playerIn);
/*  173 */     nethandlerplayserver.setPlayerLocation(playerIn.posX, playerIn.posY, playerIn.posZ, playerIn.rotationYaw, playerIn.rotationPitch);
/*  174 */     updateTimeAndWeatherForPlayer(playerIn, worldserver);
/*      */     
/*  176 */     if (!this.mcServer.getResourcePackUrl().isEmpty())
/*      */     {
/*  178 */       playerIn.loadResourcePack(this.mcServer.getResourcePackUrl(), this.mcServer.getResourcePackHash());
/*      */     }
/*      */     
/*  181 */     for (PotionEffect potioneffect : playerIn.getActivePotionEffects())
/*      */     {
/*  183 */       nethandlerplayserver.sendPacket((Packet)new SPacketEntityEffect(playerIn.getEntityId(), potioneffect));
/*      */     }
/*      */     
/*  186 */     if (nbttagcompound != null && nbttagcompound.hasKey("RootVehicle", 10)) {
/*      */       
/*  188 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("RootVehicle");
/*  189 */       Entity entity1 = AnvilChunkLoader.readWorldEntity(nbttagcompound1.getCompoundTag("Entity"), (World)worldserver, true);
/*      */       
/*  191 */       if (entity1 != null) {
/*      */         
/*  193 */         UUID uuid = nbttagcompound1.getUniqueId("Attach");
/*      */         
/*  195 */         if (entity1.getUniqueID().equals(uuid)) {
/*      */           
/*  197 */           playerIn.startRiding(entity1, true);
/*      */         }
/*      */         else {
/*      */           
/*  201 */           for (Entity entity : entity1.getRecursivePassengers()) {
/*      */             
/*  203 */             if (entity.getUniqueID().equals(uuid)) {
/*      */               
/*  205 */               playerIn.startRiding(entity, true);
/*      */               
/*      */               break;
/*      */             } 
/*      */           } 
/*      */         } 
/*  211 */         if (!playerIn.isRiding()) {
/*      */           
/*  213 */           LOG.warn("Couldn't reattach entity to player");
/*  214 */           worldserver.removeEntityDangerously(entity1);
/*      */           
/*  216 */           for (Entity entity2 : entity1.getRecursivePassengers())
/*      */           {
/*  218 */             worldserver.removeEntityDangerously(entity2);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  224 */     playerIn.addSelfToInternalCraftingInventory();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void sendScoreboard(ServerScoreboard scoreboardIn, EntityPlayerMP playerIn) {
/*  229 */     Set<ScoreObjective> set = Sets.newHashSet();
/*      */     
/*  231 */     for (ScorePlayerTeam scoreplayerteam : scoreboardIn.getTeams())
/*      */     {
/*  233 */       playerIn.connection.sendPacket((Packet)new SPacketTeams(scoreplayerteam, 0));
/*      */     }
/*      */     
/*  236 */     for (int i = 0; i < 19; i++) {
/*      */       
/*  238 */       ScoreObjective scoreobjective = scoreboardIn.getObjectiveInDisplaySlot(i);
/*      */       
/*  240 */       if (scoreobjective != null && !set.contains(scoreobjective)) {
/*      */         
/*  242 */         for (Packet<?> packet : (Iterable<Packet<?>>)scoreboardIn.getCreatePackets(scoreobjective))
/*      */         {
/*  244 */           playerIn.connection.sendPacket(packet);
/*      */         }
/*      */         
/*  247 */         set.add(scoreobjective);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPlayerManager(WorldServer[] worldServers) {
/*  257 */     this.playerNBTManagerObj = worldServers[0].getSaveHandler().getPlayerNBTManager();
/*  258 */     worldServers[0].getWorldBorder().addListener(new IBorderListener()
/*      */         {
/*      */           public void onSizeChanged(WorldBorder border, double newSize)
/*      */           {
/*  262 */             PlayerList.this.sendPacketToAllPlayers((Packet<?>)new SPacketWorldBorder(border, SPacketWorldBorder.Action.SET_SIZE));
/*      */           }
/*      */           
/*      */           public void onTransitionStarted(WorldBorder border, double oldSize, double newSize, long time) {
/*  266 */             PlayerList.this.sendPacketToAllPlayers((Packet<?>)new SPacketWorldBorder(border, SPacketWorldBorder.Action.LERP_SIZE));
/*      */           }
/*      */           
/*      */           public void onCenterChanged(WorldBorder border, double x, double z) {
/*  270 */             PlayerList.this.sendPacketToAllPlayers((Packet<?>)new SPacketWorldBorder(border, SPacketWorldBorder.Action.SET_CENTER));
/*      */           }
/*      */           
/*      */           public void onWarningTimeChanged(WorldBorder border, int newTime) {
/*  274 */             PlayerList.this.sendPacketToAllPlayers((Packet<?>)new SPacketWorldBorder(border, SPacketWorldBorder.Action.SET_WARNING_TIME));
/*      */           }
/*      */           
/*      */           public void onWarningDistanceChanged(WorldBorder border, int newDistance) {
/*  278 */             PlayerList.this.sendPacketToAllPlayers((Packet<?>)new SPacketWorldBorder(border, SPacketWorldBorder.Action.SET_WARNING_BLOCKS));
/*      */           }
/*      */ 
/*      */           
/*      */           public void onDamageAmountChanged(WorldBorder border, double newAmount) {}
/*      */ 
/*      */           
/*      */           public void onDamageBufferChanged(WorldBorder border, double newSize) {}
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   public void preparePlayer(EntityPlayerMP playerIn, @Nullable WorldServer worldIn) {
/*  291 */     WorldServer worldserver = playerIn.getServerWorld();
/*      */     
/*  293 */     if (worldIn != null)
/*      */     {
/*  295 */       worldIn.getPlayerChunkMap().removePlayer(playerIn);
/*      */     }
/*      */     
/*  298 */     worldserver.getPlayerChunkMap().addPlayer(playerIn);
/*  299 */     worldserver.getChunkProvider().provideChunk((int)playerIn.posX >> 4, (int)playerIn.posZ >> 4);
/*      */     
/*  301 */     if (worldIn != null) {
/*      */       
/*  303 */       CriteriaTriggers.field_193134_u.func_193143_a(playerIn, worldIn.provider.getDimensionType(), worldserver.provider.getDimensionType());
/*      */       
/*  305 */       if (worldIn.provider.getDimensionType() == DimensionType.NETHER && playerIn.world.provider.getDimensionType() == DimensionType.OVERWORLD && playerIn.func_193106_Q() != null)
/*      */       {
/*  307 */         CriteriaTriggers.field_193131_B.func_193168_a(playerIn, playerIn.func_193106_Q());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getEntityViewDistance() {
/*  314 */     return PlayerChunkMap.getFurthestViewableBlock(getViewDistance());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public NBTTagCompound readPlayerDataFromFile(EntityPlayerMP playerIn) {
/*  324 */     NBTTagCompound nbttagcompound1, nbttagcompound = this.mcServer.worldServers[0].getWorldInfo().getPlayerNBTTagCompound();
/*      */ 
/*      */     
/*  327 */     if (playerIn.getName().equals(this.mcServer.getServerOwner()) && nbttagcompound != null) {
/*      */       
/*  329 */       nbttagcompound1 = nbttagcompound;
/*  330 */       playerIn.readFromNBT(nbttagcompound);
/*  331 */       LOG.debug("loading single player");
/*      */     }
/*      */     else {
/*      */       
/*  335 */       nbttagcompound1 = this.playerNBTManagerObj.readPlayerData((EntityPlayer)playerIn);
/*      */     } 
/*      */     
/*  338 */     return nbttagcompound1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void writePlayerData(EntityPlayerMP playerIn) {
/*  346 */     this.playerNBTManagerObj.writePlayerData((EntityPlayer)playerIn);
/*  347 */     StatisticsManagerServer statisticsmanagerserver = this.playerStatFiles.get(playerIn.getUniqueID());
/*      */     
/*  349 */     if (statisticsmanagerserver != null)
/*      */     {
/*  351 */       statisticsmanagerserver.saveStatFile();
/*      */     }
/*      */     
/*  354 */     PlayerAdvancements playeradvancements = this.field_192055_p.get(playerIn.getUniqueID());
/*      */     
/*  356 */     if (playeradvancements != null)
/*      */     {
/*  358 */       playeradvancements.func_192749_b();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playerLoggedIn(EntityPlayerMP playerIn) {
/*  367 */     this.playerEntityList.add(playerIn);
/*  368 */     this.uuidToPlayerMap.put(playerIn.getUniqueID(), playerIn);
/*  369 */     sendPacketToAllPlayers((Packet<?>)new SPacketPlayerListItem(SPacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { playerIn }));
/*  370 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/*      */     
/*  372 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */       
/*  374 */       playerIn.connection.sendPacket((Packet)new SPacketPlayerListItem(SPacketPlayerListItem.Action.ADD_PLAYER, new EntityPlayerMP[] { this.playerEntityList.get(i) }));
/*      */     } 
/*      */     
/*  377 */     worldserver.spawnEntityInWorld((Entity)playerIn);
/*  378 */     preparePlayer(playerIn, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void serverUpdateMovingPlayer(EntityPlayerMP playerIn) {
/*  386 */     playerIn.getServerWorld().getPlayerChunkMap().updateMovingPlayer(playerIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playerLoggedOut(EntityPlayerMP playerIn) {
/*  394 */     WorldServer worldserver = playerIn.getServerWorld();
/*  395 */     playerIn.addStat(StatList.LEAVE_GAME);
/*  396 */     writePlayerData(playerIn);
/*      */     
/*  398 */     if (playerIn.isRiding()) {
/*      */       
/*  400 */       Entity entity = playerIn.getLowestRidingEntity();
/*      */       
/*  402 */       if (entity.getRecursivePassengersByType(EntityPlayerMP.class).size() == 1) {
/*      */         
/*  404 */         LOG.debug("Removing player mount");
/*  405 */         playerIn.dismountRidingEntity();
/*  406 */         worldserver.removeEntityDangerously(entity);
/*      */         
/*  408 */         for (Entity entity1 : entity.getRecursivePassengers())
/*      */         {
/*  410 */           worldserver.removeEntityDangerously(entity1);
/*      */         }
/*      */         
/*  413 */         worldserver.getChunkFromChunkCoords(playerIn.chunkCoordX, playerIn.chunkCoordZ).setChunkModified();
/*      */       } 
/*      */     } 
/*      */     
/*  417 */     worldserver.removeEntity((Entity)playerIn);
/*  418 */     worldserver.getPlayerChunkMap().removePlayer(playerIn);
/*  419 */     playerIn.func_192039_O().func_192745_a();
/*  420 */     this.playerEntityList.remove(playerIn);
/*  421 */     UUID uuid = playerIn.getUniqueID();
/*  422 */     EntityPlayerMP entityplayermp = this.uuidToPlayerMap.get(uuid);
/*      */     
/*  424 */     if (entityplayermp == playerIn) {
/*      */       
/*  426 */       this.uuidToPlayerMap.remove(uuid);
/*  427 */       this.playerStatFiles.remove(uuid);
/*  428 */       this.field_192055_p.remove(uuid);
/*      */     } 
/*      */     
/*  431 */     sendPacketToAllPlayers((Packet<?>)new SPacketPlayerListItem(SPacketPlayerListItem.Action.REMOVE_PLAYER, new EntityPlayerMP[] { playerIn }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String allowUserToConnect(SocketAddress address, GameProfile profile) {
/*  439 */     if (this.bannedPlayers.isBanned(profile)) {
/*      */       
/*  441 */       UserListBansEntry userlistbansentry = this.bannedPlayers.getEntry(profile);
/*  442 */       String s1 = "You are banned from this server!\nReason: " + userlistbansentry.getBanReason();
/*      */       
/*  444 */       if (userlistbansentry.getBanEndDate() != null)
/*      */       {
/*  446 */         s1 = String.valueOf(s1) + "\nYour ban will be removed on " + DATE_FORMAT.format(userlistbansentry.getBanEndDate());
/*      */       }
/*      */       
/*  449 */       return s1;
/*      */     } 
/*  451 */     if (!canJoin(profile))
/*      */     {
/*  453 */       return "You are not white-listed on this server!";
/*      */     }
/*  455 */     if (this.bannedIPs.isBanned(address)) {
/*      */       
/*  457 */       UserListIPBansEntry userlistipbansentry = this.bannedIPs.getBanEntry(address);
/*  458 */       String s = "Your IP address is banned from this server!\nReason: " + userlistipbansentry.getBanReason();
/*      */       
/*  460 */       if (userlistipbansentry.getBanEndDate() != null)
/*      */       {
/*  462 */         s = String.valueOf(s) + "\nYour ban will be removed on " + DATE_FORMAT.format(userlistipbansentry.getBanEndDate());
/*      */       }
/*      */       
/*  465 */       return s;
/*      */     } 
/*      */ 
/*      */     
/*  469 */     return (this.playerEntityList.size() >= this.maxPlayers && !bypassesPlayerLimit(profile)) ? "The server is full!" : null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayerMP createPlayerForUser(GameProfile profile) {
/*      */     PlayerInteractionManager playerinteractionmanager;
/*  478 */     UUID uuid = EntityPlayer.getUUID(profile);
/*  479 */     List<EntityPlayerMP> list = Lists.newArrayList();
/*      */     
/*  481 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */       
/*  483 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*      */       
/*  485 */       if (entityplayermp.getUniqueID().equals(uuid))
/*      */       {
/*  487 */         list.add(entityplayermp);
/*      */       }
/*      */     } 
/*      */     
/*  491 */     EntityPlayerMP entityplayermp2 = this.uuidToPlayerMap.get(profile.getId());
/*      */     
/*  493 */     if (entityplayermp2 != null && !list.contains(entityplayermp2))
/*      */     {
/*  495 */       list.add(entityplayermp2);
/*      */     }
/*      */     
/*  498 */     for (EntityPlayerMP entityplayermp1 : list)
/*      */     {
/*  500 */       entityplayermp1.connection.func_194028_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.duplicate_login", new Object[0]));
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  505 */     if (this.mcServer.isDemo()) {
/*      */       
/*  507 */       playerinteractionmanager = new DemoPlayerInteractionManager((World)this.mcServer.worldServerForDimension(0));
/*      */     }
/*      */     else {
/*      */       
/*  511 */       playerinteractionmanager = new PlayerInteractionManager((World)this.mcServer.worldServerForDimension(0));
/*      */     } 
/*      */     
/*  514 */     return new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(0), profile, playerinteractionmanager);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayerMP recreatePlayerEntity(EntityPlayerMP playerIn, int dimension, boolean conqueredEnd) {
/*      */     PlayerInteractionManager playerinteractionmanager;
/*  522 */     playerIn.getServerWorld().getEntityTracker().removePlayerFromTrackers(playerIn);
/*  523 */     playerIn.getServerWorld().getEntityTracker().untrackEntity((Entity)playerIn);
/*  524 */     playerIn.getServerWorld().getPlayerChunkMap().removePlayer(playerIn);
/*  525 */     this.playerEntityList.remove(playerIn);
/*  526 */     this.mcServer.worldServerForDimension(playerIn.dimension).removeEntityDangerously((Entity)playerIn);
/*  527 */     BlockPos blockpos = playerIn.getBedLocation();
/*  528 */     boolean flag = playerIn.isSpawnForced();
/*  529 */     playerIn.dimension = dimension;
/*      */ 
/*      */     
/*  532 */     if (this.mcServer.isDemo()) {
/*      */       
/*  534 */       playerinteractionmanager = new DemoPlayerInteractionManager((World)this.mcServer.worldServerForDimension(playerIn.dimension));
/*      */     }
/*      */     else {
/*      */       
/*  538 */       playerinteractionmanager = new PlayerInteractionManager((World)this.mcServer.worldServerForDimension(playerIn.dimension));
/*      */     } 
/*      */     
/*  541 */     EntityPlayerMP entityplayermp = new EntityPlayerMP(this.mcServer, this.mcServer.worldServerForDimension(playerIn.dimension), playerIn.getGameProfile(), playerinteractionmanager);
/*  542 */     entityplayermp.connection = playerIn.connection;
/*  543 */     entityplayermp.func_193104_a(playerIn, conqueredEnd);
/*  544 */     entityplayermp.setEntityId(playerIn.getEntityId());
/*  545 */     entityplayermp.setCommandStats((Entity)playerIn);
/*  546 */     entityplayermp.setPrimaryHand(playerIn.getPrimaryHand());
/*      */     
/*  548 */     for (String s : playerIn.getTags())
/*      */     {
/*  550 */       entityplayermp.addTag(s);
/*      */     }
/*      */     
/*  553 */     WorldServer worldserver = this.mcServer.worldServerForDimension(playerIn.dimension);
/*  554 */     setPlayerGameTypeBasedOnOther(entityplayermp, playerIn, (World)worldserver);
/*      */     
/*  556 */     if (blockpos != null) {
/*      */       
/*  558 */       BlockPos blockpos1 = EntityPlayer.getBedSpawnLocation((World)this.mcServer.worldServerForDimension(playerIn.dimension), blockpos, flag);
/*      */       
/*  560 */       if (blockpos1 != null) {
/*      */         
/*  562 */         entityplayermp.setLocationAndAngles((blockpos1.getX() + 0.5F), (blockpos1.getY() + 0.1F), (blockpos1.getZ() + 0.5F), 0.0F, 0.0F);
/*  563 */         entityplayermp.setSpawnPoint(blockpos, flag);
/*      */       }
/*      */       else {
/*      */         
/*  567 */         entityplayermp.connection.sendPacket((Packet)new SPacketChangeGameState(0, 0.0F));
/*      */       } 
/*      */     } 
/*      */     
/*  571 */     worldserver.getChunkProvider().provideChunk((int)entityplayermp.posX >> 4, (int)entityplayermp.posZ >> 4);
/*      */     
/*  573 */     while (!worldserver.getCollisionBoxes((Entity)entityplayermp, entityplayermp.getEntityBoundingBox()).isEmpty() && entityplayermp.posY < 256.0D)
/*      */     {
/*  575 */       entityplayermp.setPosition(entityplayermp.posX, entityplayermp.posY + 1.0D, entityplayermp.posZ);
/*      */     }
/*      */     
/*  578 */     entityplayermp.connection.sendPacket((Packet)new SPacketRespawn(entityplayermp.dimension, entityplayermp.world.getDifficulty(), entityplayermp.world.getWorldInfo().getTerrainType(), entityplayermp.interactionManager.getGameType()));
/*  579 */     BlockPos blockpos2 = worldserver.getSpawnPoint();
/*  580 */     entityplayermp.connection.setPlayerLocation(entityplayermp.posX, entityplayermp.posY, entityplayermp.posZ, entityplayermp.rotationYaw, entityplayermp.rotationPitch);
/*  581 */     entityplayermp.connection.sendPacket((Packet)new SPacketSpawnPosition(blockpos2));
/*  582 */     entityplayermp.connection.sendPacket((Packet)new SPacketSetExperience(entityplayermp.experience, entityplayermp.experienceTotal, entityplayermp.experienceLevel));
/*  583 */     updateTimeAndWeatherForPlayer(entityplayermp, worldserver);
/*  584 */     updatePermissionLevel(entityplayermp);
/*  585 */     worldserver.getPlayerChunkMap().addPlayer(entityplayermp);
/*  586 */     worldserver.spawnEntityInWorld((Entity)entityplayermp);
/*  587 */     this.playerEntityList.add(entityplayermp);
/*  588 */     this.uuidToPlayerMap.put(entityplayermp.getUniqueID(), entityplayermp);
/*  589 */     entityplayermp.addSelfToInternalCraftingInventory();
/*  590 */     entityplayermp.setHealth(entityplayermp.getHealth());
/*  591 */     return entityplayermp;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updatePermissionLevel(EntityPlayerMP player) {
/*  596 */     GameProfile gameprofile = player.getGameProfile();
/*  597 */     int i = canSendCommands(gameprofile) ? this.ops.getPermissionLevel(gameprofile) : 0;
/*  598 */     i = (this.mcServer.isSinglePlayer() && this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed()) ? 4 : i;
/*  599 */     i = this.commandsAllowedForAll ? 4 : i;
/*  600 */     sendPlayerPermissionLevel(player, i);
/*      */   }
/*      */ 
/*      */   
/*      */   public void changePlayerDimension(EntityPlayerMP player, int dimensionIn) {
/*  605 */     int i = player.dimension;
/*  606 */     WorldServer worldserver = this.mcServer.worldServerForDimension(player.dimension);
/*  607 */     player.dimension = dimensionIn;
/*  608 */     WorldServer worldserver1 = this.mcServer.worldServerForDimension(player.dimension);
/*  609 */     player.connection.sendPacket((Packet)new SPacketRespawn(player.dimension, player.world.getDifficulty(), player.world.getWorldInfo().getTerrainType(), player.interactionManager.getGameType()));
/*  610 */     updatePermissionLevel(player);
/*  611 */     worldserver.removeEntityDangerously((Entity)player);
/*  612 */     player.isDead = false;
/*  613 */     transferEntityToWorld((Entity)player, i, worldserver, worldserver1);
/*  614 */     preparePlayer(player, worldserver);
/*  615 */     player.connection.setPlayerLocation(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch);
/*  616 */     player.interactionManager.setWorld(worldserver1);
/*  617 */     player.connection.sendPacket((Packet)new SPacketPlayerAbilities(player.capabilities));
/*  618 */     updateTimeAndWeatherForPlayer(player, worldserver1);
/*  619 */     syncPlayerInventory(player);
/*      */     
/*  621 */     for (PotionEffect potioneffect : player.getActivePotionEffects())
/*      */     {
/*  623 */       player.connection.sendPacket((Packet)new SPacketEntityEffect(player.getEntityId(), potioneffect));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void transferEntityToWorld(Entity entityIn, int lastDimension, WorldServer oldWorldIn, WorldServer toWorldIn) {
/*  632 */     double d0 = entityIn.posX;
/*  633 */     double d1 = entityIn.posZ;
/*  634 */     double d2 = 8.0D;
/*  635 */     float f = entityIn.rotationYaw;
/*  636 */     oldWorldIn.theProfiler.startSection("moving");
/*      */     
/*  638 */     if (entityIn.dimension == -1) {
/*      */       
/*  640 */       d0 = MathHelper.clamp(d0 / 8.0D, toWorldIn.getWorldBorder().minX() + 16.0D, toWorldIn.getWorldBorder().maxX() - 16.0D);
/*  641 */       d1 = MathHelper.clamp(d1 / 8.0D, toWorldIn.getWorldBorder().minZ() + 16.0D, toWorldIn.getWorldBorder().maxZ() - 16.0D);
/*  642 */       entityIn.setLocationAndAngles(d0, entityIn.posY, d1, entityIn.rotationYaw, entityIn.rotationPitch);
/*      */       
/*  644 */       if (entityIn.isEntityAlive())
/*      */       {
/*  646 */         oldWorldIn.updateEntityWithOptionalForce(entityIn, false);
/*      */       }
/*      */     }
/*  649 */     else if (entityIn.dimension == 0) {
/*      */       
/*  651 */       d0 = MathHelper.clamp(d0 * 8.0D, toWorldIn.getWorldBorder().minX() + 16.0D, toWorldIn.getWorldBorder().maxX() - 16.0D);
/*  652 */       d1 = MathHelper.clamp(d1 * 8.0D, toWorldIn.getWorldBorder().minZ() + 16.0D, toWorldIn.getWorldBorder().maxZ() - 16.0D);
/*  653 */       entityIn.setLocationAndAngles(d0, entityIn.posY, d1, entityIn.rotationYaw, entityIn.rotationPitch);
/*      */       
/*  655 */       if (entityIn.isEntityAlive())
/*      */       {
/*  657 */         oldWorldIn.updateEntityWithOptionalForce(entityIn, false);
/*      */       }
/*      */     } else {
/*      */       BlockPos blockpos;
/*      */ 
/*      */ 
/*      */       
/*  664 */       if (lastDimension == 1) {
/*      */         
/*  666 */         blockpos = toWorldIn.getSpawnPoint();
/*      */       }
/*      */       else {
/*      */         
/*  670 */         blockpos = toWorldIn.getSpawnCoordinate();
/*      */       } 
/*      */       
/*  673 */       d0 = blockpos.getX();
/*  674 */       entityIn.posY = blockpos.getY();
/*  675 */       d1 = blockpos.getZ();
/*  676 */       entityIn.setLocationAndAngles(d0, entityIn.posY, d1, 90.0F, 0.0F);
/*      */       
/*  678 */       if (entityIn.isEntityAlive())
/*      */       {
/*  680 */         oldWorldIn.updateEntityWithOptionalForce(entityIn, false);
/*      */       }
/*      */     } 
/*      */     
/*  684 */     oldWorldIn.theProfiler.endSection();
/*      */     
/*  686 */     if (lastDimension != 1) {
/*      */       
/*  688 */       oldWorldIn.theProfiler.startSection("placing");
/*  689 */       d0 = MathHelper.clamp((int)d0, -29999872, 29999872);
/*  690 */       d1 = MathHelper.clamp((int)d1, -29999872, 29999872);
/*      */       
/*  692 */       if (entityIn.isEntityAlive()) {
/*      */         
/*  694 */         entityIn.setLocationAndAngles(d0, entityIn.posY, d1, entityIn.rotationYaw, entityIn.rotationPitch);
/*  695 */         toWorldIn.getDefaultTeleporter().placeInPortal(entityIn, f);
/*  696 */         toWorldIn.spawnEntityInWorld(entityIn);
/*  697 */         toWorldIn.updateEntityWithOptionalForce(entityIn, false);
/*      */       } 
/*      */       
/*  700 */       oldWorldIn.theProfiler.endSection();
/*      */     } 
/*      */     
/*  703 */     entityIn.setWorld((World)toWorldIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onTick() {
/*  711 */     if (++this.playerPingIndex > 600) {
/*      */       
/*  713 */       sendPacketToAllPlayers((Packet<?>)new SPacketPlayerListItem(SPacketPlayerListItem.Action.UPDATE_LATENCY, this.playerEntityList));
/*  714 */       this.playerPingIndex = 0;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPacketToAllPlayers(Packet<?> packetIn) {
/*  720 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/*  722 */       ((EntityPlayerMP)this.playerEntityList.get(i)).connection.sendPacket(packetIn);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPacketToAllPlayersInDimension(Packet<?> packetIn, int dimension) {
/*  728 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */       
/*  730 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*      */       
/*  732 */       if (entityplayermp.dimension == dimension)
/*      */       {
/*  734 */         entityplayermp.connection.sendPacket(packetIn);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendMessageToAllTeamMembers(EntityPlayer player, ITextComponent message) {
/*  741 */     Team team = player.getTeam();
/*      */     
/*  743 */     if (team != null)
/*      */     {
/*  745 */       for (String s : team.getMembershipCollection()) {
/*      */         
/*  747 */         EntityPlayerMP entityplayermp = getPlayerByUsername(s);
/*      */         
/*  749 */         if (entityplayermp != null && entityplayermp != player)
/*      */         {
/*  751 */           entityplayermp.addChatMessage(message);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendMessageToTeamOrAllPlayers(EntityPlayer player, ITextComponent message) {
/*  759 */     Team team = player.getTeam();
/*      */     
/*  761 */     if (team == null) {
/*      */       
/*  763 */       sendChatMsg(message);
/*      */     }
/*      */     else {
/*      */       
/*  767 */       for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */         
/*  769 */         EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*      */         
/*  771 */         if (entityplayermp.getTeam() != team)
/*      */         {
/*  773 */           entityplayermp.addChatMessage(message);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getFormattedListOfPlayers(boolean includeUUIDs) {
/*  784 */     String s = "";
/*  785 */     List<EntityPlayerMP> list = Lists.newArrayList(this.playerEntityList);
/*      */     
/*  787 */     for (int i = 0; i < list.size(); i++) {
/*      */       
/*  789 */       if (i > 0)
/*      */       {
/*  791 */         s = String.valueOf(s) + ", ";
/*      */       }
/*      */       
/*  794 */       s = String.valueOf(s) + ((EntityPlayerMP)list.get(i)).getName();
/*      */       
/*  796 */       if (includeUUIDs)
/*      */       {
/*  798 */         s = String.valueOf(s) + " (" + ((EntityPlayerMP)list.get(i)).getCachedUniqueIdString() + ")";
/*      */       }
/*      */     } 
/*      */     
/*  802 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getAllUsernames() {
/*  810 */     String[] astring = new String[this.playerEntityList.size()];
/*      */     
/*  812 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/*  814 */       astring[i] = ((EntityPlayerMP)this.playerEntityList.get(i)).getName();
/*      */     }
/*      */     
/*  817 */     return astring;
/*      */   }
/*      */ 
/*      */   
/*      */   public GameProfile[] getAllProfiles() {
/*  822 */     GameProfile[] agameprofile = new GameProfile[this.playerEntityList.size()];
/*      */     
/*  824 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/*  826 */       agameprofile[i] = ((EntityPlayerMP)this.playerEntityList.get(i)).getGameProfile();
/*      */     }
/*      */     
/*  829 */     return agameprofile;
/*      */   }
/*      */ 
/*      */   
/*      */   public UserListBans getBannedPlayers() {
/*  834 */     return this.bannedPlayers;
/*      */   }
/*      */ 
/*      */   
/*      */   public UserListIPBans getBannedIPs() {
/*  839 */     return this.bannedIPs;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addOp(GameProfile profile) {
/*  844 */     int i = this.mcServer.getOpPermissionLevel();
/*  845 */     this.ops.addEntry(new UserListOpsEntry(profile, this.mcServer.getOpPermissionLevel(), this.ops.bypassesPlayerLimit(profile)));
/*  846 */     sendPlayerPermissionLevel(getPlayerByUUID(profile.getId()), i);
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeOp(GameProfile profile) {
/*  851 */     this.ops.removeEntry(profile);
/*  852 */     sendPlayerPermissionLevel(getPlayerByUUID(profile.getId()), 0);
/*      */   }
/*      */ 
/*      */   
/*      */   private void sendPlayerPermissionLevel(EntityPlayerMP player, int permLevel) {
/*  857 */     if (player != null && player.connection != null) {
/*      */       byte b0;
/*      */ 
/*      */       
/*  861 */       if (permLevel <= 0) {
/*      */         
/*  863 */         b0 = 24;
/*      */       }
/*  865 */       else if (permLevel >= 4) {
/*      */         
/*  867 */         b0 = 28;
/*      */       }
/*      */       else {
/*      */         
/*  871 */         b0 = (byte)(24 + permLevel);
/*      */       } 
/*      */       
/*  874 */       player.connection.sendPacket((Packet)new SPacketEntityStatus((Entity)player, b0));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canJoin(GameProfile profile) {
/*  880 */     return !(this.whiteListEnforced && !this.ops.hasEntry(profile) && !this.whiteListedPlayers.hasEntry(profile));
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSendCommands(GameProfile profile) {
/*  885 */     return !(!this.ops.hasEntry(profile) && (!this.mcServer.isSinglePlayer() || !this.mcServer.worldServers[0].getWorldInfo().areCommandsAllowed() || !this.mcServer.getServerOwner().equalsIgnoreCase(profile.getName())) && !this.commandsAllowedForAll);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityPlayerMP getPlayerByUsername(String username) {
/*  891 */     for (EntityPlayerMP entityplayermp : this.playerEntityList) {
/*      */       
/*  893 */       if (entityplayermp.getName().equalsIgnoreCase(username))
/*      */       {
/*  895 */         return entityplayermp;
/*      */       }
/*      */     } 
/*      */     
/*  899 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendToAllNearExcept(@Nullable EntityPlayer except, double x, double y, double z, double radius, int dimension, Packet<?> packetIn) {
/*  908 */     for (int i = 0; i < this.playerEntityList.size(); i++) {
/*      */       
/*  910 */       EntityPlayerMP entityplayermp = this.playerEntityList.get(i);
/*      */       
/*  912 */       if (entityplayermp != except && entityplayermp.dimension == dimension) {
/*      */         
/*  914 */         double d0 = x - entityplayermp.posX;
/*  915 */         double d1 = y - entityplayermp.posY;
/*  916 */         double d2 = z - entityplayermp.posZ;
/*      */         
/*  918 */         if (d0 * d0 + d1 * d1 + d2 * d2 < radius * radius)
/*      */         {
/*  920 */           entityplayermp.connection.sendPacket(packetIn);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveAllPlayerData() {
/*  931 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/*  933 */       writePlayerData(this.playerEntityList.get(i));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void addWhitelistedPlayer(GameProfile profile) {
/*  939 */     this.whiteListedPlayers.addEntry(new UserListWhitelistEntry(profile));
/*      */   }
/*      */ 
/*      */   
/*      */   public void removePlayerFromWhitelist(GameProfile profile) {
/*  944 */     this.whiteListedPlayers.removeEntry(profile);
/*      */   }
/*      */ 
/*      */   
/*      */   public UserListWhitelist getWhitelistedPlayers() {
/*  949 */     return this.whiteListedPlayers;
/*      */   }
/*      */ 
/*      */   
/*      */   public String[] getWhitelistedPlayerNames() {
/*  954 */     return this.whiteListedPlayers.getKeys();
/*      */   }
/*      */ 
/*      */   
/*      */   public UserListOps getOppedPlayers() {
/*  959 */     return this.ops;
/*      */   }
/*      */ 
/*      */   
/*      */   public String[] getOppedPlayerNames() {
/*  964 */     return this.ops.getKeys();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void reloadWhitelist() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateTimeAndWeatherForPlayer(EntityPlayerMP playerIn, WorldServer worldIn) {
/*  976 */     WorldBorder worldborder = this.mcServer.worldServers[0].getWorldBorder();
/*  977 */     playerIn.connection.sendPacket((Packet)new SPacketWorldBorder(worldborder, SPacketWorldBorder.Action.INITIALIZE));
/*  978 */     playerIn.connection.sendPacket((Packet)new SPacketTimeUpdate(worldIn.getTotalWorldTime(), worldIn.getWorldTime(), worldIn.getGameRules().getBoolean("doDaylightCycle")));
/*  979 */     BlockPos blockpos = worldIn.getSpawnPoint();
/*  980 */     playerIn.connection.sendPacket((Packet)new SPacketSpawnPosition(blockpos));
/*      */     
/*  982 */     if (worldIn.isRaining()) {
/*      */       
/*  984 */       playerIn.connection.sendPacket((Packet)new SPacketChangeGameState(1, 0.0F));
/*  985 */       playerIn.connection.sendPacket((Packet)new SPacketChangeGameState(7, worldIn.getRainStrength(1.0F)));
/*  986 */       playerIn.connection.sendPacket((Packet)new SPacketChangeGameState(8, worldIn.getThunderStrength(1.0F)));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void syncPlayerInventory(EntityPlayerMP playerIn) {
/*  995 */     playerIn.sendContainerToPlayer(playerIn.inventoryContainer);
/*  996 */     playerIn.setPlayerHealthUpdated();
/*  997 */     playerIn.connection.sendPacket((Packet)new SPacketHeldItemChange(playerIn.inventory.currentItem));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCurrentPlayerCount() {
/* 1005 */     return this.playerEntityList.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxPlayers() {
/* 1013 */     return this.maxPlayers;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String[] getAvailablePlayerDat() {
/* 1021 */     return this.mcServer.worldServers[0].getSaveHandler().getPlayerNBTManager().getAvailablePlayerDat();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setWhiteListEnabled(boolean whitelistEnabled) {
/* 1026 */     this.whiteListEnforced = whitelistEnabled;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<EntityPlayerMP> getPlayersMatchingAddress(String address) {
/* 1031 */     List<EntityPlayerMP> list = Lists.newArrayList();
/*      */     
/* 1033 */     for (EntityPlayerMP entityplayermp : this.playerEntityList) {
/*      */       
/* 1035 */       if (entityplayermp.getPlayerIP().equals(address))
/*      */       {
/* 1037 */         list.add(entityplayermp);
/*      */       }
/*      */     } 
/*      */     
/* 1041 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getViewDistance() {
/* 1049 */     return this.viewDistance;
/*      */   }
/*      */ 
/*      */   
/*      */   public MinecraftServer getServerInstance() {
/* 1054 */     return this.mcServer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound getHostPlayerData() {
/* 1062 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setGameType(GameType gameModeIn) {
/* 1067 */     this.gameType = gameModeIn;
/*      */   }
/*      */ 
/*      */   
/*      */   private void setPlayerGameTypeBasedOnOther(EntityPlayerMP target, EntityPlayerMP source, World worldIn) {
/* 1072 */     if (source != null) {
/*      */       
/* 1074 */       target.interactionManager.setGameType(source.interactionManager.getGameType());
/*      */     }
/* 1076 */     else if (this.gameType != null) {
/*      */       
/* 1078 */       target.interactionManager.setGameType(this.gameType);
/*      */     } 
/*      */     
/* 1081 */     target.interactionManager.initializeGameType(worldIn.getWorldInfo().getGameType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCommandsAllowedForAll(boolean p_72387_1_) {
/* 1089 */     this.commandsAllowedForAll = p_72387_1_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAllPlayers() {
/* 1097 */     for (int i = 0; i < this.playerEntityList.size(); i++)
/*      */     {
/* 1099 */       ((EntityPlayerMP)this.playerEntityList.get(i)).connection.func_194028_b((ITextComponent)new TextComponentTranslation("multiplayer.disconnect.server_shutdown", new Object[0]));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendChatMsgImpl(ITextComponent component, boolean isSystem) {
/* 1105 */     this.mcServer.addChatMessage(component);
/* 1106 */     ChatType chattype = isSystem ? ChatType.SYSTEM : ChatType.CHAT;
/* 1107 */     sendPacketToAllPlayers((Packet<?>)new SPacketChat(component, chattype));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendChatMsg(ITextComponent component) {
/* 1115 */     sendChatMsgImpl(component, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public StatisticsManagerServer getPlayerStatsFile(EntityPlayer playerIn) {
/* 1120 */     UUID uuid = playerIn.getUniqueID();
/* 1121 */     StatisticsManagerServer statisticsmanagerserver = (uuid == null) ? null : this.playerStatFiles.get(uuid);
/*      */     
/* 1123 */     if (statisticsmanagerserver == null) {
/*      */       
/* 1125 */       File file1 = new File(this.mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "stats");
/* 1126 */       File file2 = new File(file1, uuid + ".json");
/*      */       
/* 1128 */       if (!file2.exists()) {
/*      */         
/* 1130 */         File file3 = new File(file1, String.valueOf(playerIn.getName()) + ".json");
/*      */         
/* 1132 */         if (file3.exists() && file3.isFile())
/*      */         {
/* 1134 */           file3.renameTo(file2);
/*      */         }
/*      */       } 
/*      */       
/* 1138 */       statisticsmanagerserver = new StatisticsManagerServer(this.mcServer, file2);
/* 1139 */       statisticsmanagerserver.readStatFile();
/* 1140 */       this.playerStatFiles.put(uuid, statisticsmanagerserver);
/*      */     } 
/*      */     
/* 1143 */     return statisticsmanagerserver;
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerAdvancements func_192054_h(EntityPlayerMP p_192054_1_) {
/* 1148 */     UUID uuid = p_192054_1_.getUniqueID();
/* 1149 */     PlayerAdvancements playeradvancements = this.field_192055_p.get(uuid);
/*      */     
/* 1151 */     if (playeradvancements == null) {
/*      */       
/* 1153 */       File file1 = new File(this.mcServer.worldServerForDimension(0).getSaveHandler().getWorldDirectory(), "advancements");
/* 1154 */       File file2 = new File(file1, uuid + ".json");
/* 1155 */       playeradvancements = new PlayerAdvancements(this.mcServer, file2, p_192054_1_);
/* 1156 */       this.field_192055_p.put(uuid, playeradvancements);
/*      */     } 
/*      */     
/* 1159 */     playeradvancements.func_192739_a(p_192054_1_);
/* 1160 */     return playeradvancements;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setViewDistance(int distance) {
/* 1165 */     this.viewDistance = distance;
/*      */     
/* 1167 */     if (this.mcServer.worldServers != null) {
/*      */       byte b; int i; WorldServer[] arrayOfWorldServer;
/* 1169 */       for (i = (arrayOfWorldServer = this.mcServer.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/*      */         
/* 1171 */         if (worldserver != null) {
/*      */           
/* 1173 */           worldserver.getPlayerChunkMap().setPlayerViewRadius(distance);
/* 1174 */           worldserver.getEntityTracker().setViewDistance(distance);
/*      */         } 
/*      */         b++; }
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   public List<EntityPlayerMP> getPlayerList() {
/* 1182 */     return this.playerEntityList;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityPlayerMP getPlayerByUUID(UUID playerUUID) {
/* 1190 */     return this.uuidToPlayerMap.get(playerUUID);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean bypassesPlayerLimit(GameProfile profile) {
/* 1195 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_193244_w() {
/* 1200 */     for (PlayerAdvancements playeradvancements : this.field_192055_p.values())
/*      */     {
/* 1202 */       playeradvancements.func_193766_b();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\server\management\PlayerList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */