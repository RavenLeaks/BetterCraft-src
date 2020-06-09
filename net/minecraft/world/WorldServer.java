/*      */ package net.minecraft.world;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Sets;
/*      */ import com.google.common.util.concurrent.ListenableFuture;
/*      */ import java.io.File;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.TreeSet;
/*      */ import java.util.UUID;
/*      */ import java.util.stream.Collectors;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.advancements.AdvancementManager;
/*      */ import net.minecraft.advancements.FunctionManager;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockEventData;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.EntityTracker;
/*      */ import net.minecraft.entity.EnumCreatureType;
/*      */ import net.minecraft.entity.effect.EntityLightningBolt;
/*      */ import net.minecraft.entity.passive.EntitySkeletonHorse;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.play.server.SPacketBlockAction;
/*      */ import net.minecraft.network.play.server.SPacketChangeGameState;
/*      */ import net.minecraft.network.play.server.SPacketEntityStatus;
/*      */ import net.minecraft.network.play.server.SPacketExplosion;
/*      */ import net.minecraft.network.play.server.SPacketParticles;
/*      */ import net.minecraft.network.play.server.SPacketSpawnGlobalEntity;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.ScoreboardSaveData;
/*      */ import net.minecraft.scoreboard.ServerScoreboard;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.management.PlayerChunkMap;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.IProgressUpdate;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.WeightedRandom;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.ChunkPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.village.VillageCollection;
/*      */ import net.minecraft.village.VillageSiege;
/*      */ import net.minecraft.world.biome.Biome;
/*      */ import net.minecraft.world.biome.BiomeProvider;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.chunk.IChunkProvider;
/*      */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*      */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*      */ import net.minecraft.world.gen.ChunkProviderServer;
/*      */ import net.minecraft.world.gen.feature.WorldGeneratorBonusChest;
/*      */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*      */ import net.minecraft.world.gen.structure.template.TemplateManager;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.MapStorage;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import net.minecraft.world.storage.WorldSavedData;
/*      */ import net.minecraft.world.storage.WorldSavedDataCallableSave;
/*      */ import net.minecraft.world.storage.loot.LootTableManager;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class WorldServer
/*      */   extends World
/*      */   implements IThreadListener
/*      */ {
/*   85 */   private static final Logger LOGGER = LogManager.getLogger();
/*      */   
/*      */   private final MinecraftServer mcServer;
/*      */   
/*      */   private final EntityTracker theEntityTracker;
/*      */   
/*      */   private final PlayerChunkMap thePlayerManager;
/*      */   
/*   93 */   private final Set<NextTickListEntry> pendingTickListEntriesHashSet = Sets.newHashSet();
/*   94 */   private final TreeSet<NextTickListEntry> pendingTickListEntriesTreeSet = new TreeSet<>();
/*   95 */   private final Map<UUID, Entity> entitiesByUuid = Maps.newHashMap();
/*      */ 
/*      */   
/*      */   public boolean disableLevelSaving;
/*      */ 
/*      */   
/*      */   private boolean allPlayersSleeping;
/*      */ 
/*      */   
/*      */   private int updateEntityTick;
/*      */   
/*      */   private final Teleporter worldTeleporter;
/*      */   
/*  108 */   private final WorldEntitySpawner entitySpawner = new WorldEntitySpawner();
/*  109 */   protected final VillageSiege villageSiege = new VillageSiege(this);
/*  110 */   private final ServerBlockEventList[] blockEventQueue = new ServerBlockEventList[] { new ServerBlockEventList(null), new ServerBlockEventList(null) };
/*      */   private int blockEventCacheIndex;
/*  112 */   private final List<NextTickListEntry> pendingTickListEntriesThisTick = Lists.newArrayList();
/*      */ 
/*      */   
/*      */   public WorldServer(MinecraftServer server, ISaveHandler saveHandlerIn, WorldInfo info, int dimensionId, Profiler profilerIn) {
/*  116 */     super(saveHandlerIn, info, DimensionType.getById(dimensionId).createDimension(), profilerIn, false);
/*  117 */     this.mcServer = server;
/*  118 */     this.theEntityTracker = new EntityTracker(this);
/*  119 */     this.thePlayerManager = new PlayerChunkMap(this);
/*  120 */     this.provider.registerWorld(this);
/*  121 */     this.chunkProvider = createChunkProvider();
/*  122 */     this.worldTeleporter = new Teleporter(this);
/*  123 */     calculateInitialSkylight();
/*  124 */     calculateInitialWeather();
/*  125 */     getWorldBorder().setSize(server.getMaxWorldSize());
/*      */   }
/*      */ 
/*      */   
/*      */   public World init() {
/*  130 */     this.mapStorage = new MapStorage(this.saveHandler);
/*  131 */     String s = VillageCollection.fileNameForProvider(this.provider);
/*  132 */     VillageCollection villagecollection = (VillageCollection)this.mapStorage.getOrLoadData(VillageCollection.class, s);
/*      */     
/*  134 */     if (villagecollection == null) {
/*      */       
/*  136 */       this.villageCollectionObj = new VillageCollection(this);
/*  137 */       this.mapStorage.setData(s, (WorldSavedData)this.villageCollectionObj);
/*      */     }
/*      */     else {
/*      */       
/*  141 */       this.villageCollectionObj = villagecollection;
/*  142 */       this.villageCollectionObj.setWorldsForAll(this);
/*      */     } 
/*      */     
/*  145 */     this.worldScoreboard = (Scoreboard)new ServerScoreboard(this.mcServer);
/*  146 */     ScoreboardSaveData scoreboardsavedata = (ScoreboardSaveData)this.mapStorage.getOrLoadData(ScoreboardSaveData.class, "scoreboard");
/*      */     
/*  148 */     if (scoreboardsavedata == null) {
/*      */       
/*  150 */       scoreboardsavedata = new ScoreboardSaveData();
/*  151 */       this.mapStorage.setData("scoreboard", (WorldSavedData)scoreboardsavedata);
/*      */     } 
/*      */     
/*  154 */     scoreboardsavedata.setScoreboard(this.worldScoreboard);
/*  155 */     ((ServerScoreboard)this.worldScoreboard).addDirtyRunnable((Runnable)new WorldSavedDataCallableSave((WorldSavedData)scoreboardsavedata));
/*  156 */     this.lootTable = new LootTableManager(new File(new File(this.saveHandler.getWorldDirectory(), "data"), "loot_tables"));
/*  157 */     this.field_191951_C = new AdvancementManager(new File(new File(this.saveHandler.getWorldDirectory(), "data"), "advancements"));
/*  158 */     this.field_193036_D = new FunctionManager(new File(new File(this.saveHandler.getWorldDirectory(), "data"), "functions"), this.mcServer);
/*  159 */     getWorldBorder().setCenter(this.worldInfo.getBorderCenterX(), this.worldInfo.getBorderCenterZ());
/*  160 */     getWorldBorder().setDamageAmount(this.worldInfo.getBorderDamagePerBlock());
/*  161 */     getWorldBorder().setDamageBuffer(this.worldInfo.getBorderSafeZone());
/*  162 */     getWorldBorder().setWarningDistance(this.worldInfo.getBorderWarningDistance());
/*  163 */     getWorldBorder().setWarningTime(this.worldInfo.getBorderWarningTime());
/*      */     
/*  165 */     if (this.worldInfo.getBorderLerpTime() > 0L) {
/*      */       
/*  167 */       getWorldBorder().setTransition(this.worldInfo.getBorderSize(), this.worldInfo.getBorderLerpTarget(), this.worldInfo.getBorderLerpTime());
/*      */     }
/*      */     else {
/*      */       
/*  171 */       getWorldBorder().setTransition(this.worldInfo.getBorderSize());
/*      */     } 
/*      */     
/*  174 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick() {
/*  182 */     super.tick();
/*      */     
/*  184 */     if (getWorldInfo().isHardcoreModeEnabled() && getDifficulty() != EnumDifficulty.HARD)
/*      */     {
/*  186 */       getWorldInfo().setDifficulty(EnumDifficulty.HARD);
/*      */     }
/*      */     
/*  189 */     this.provider.getBiomeProvider().cleanupCache();
/*      */     
/*  191 */     if (areAllPlayersAsleep()) {
/*      */       
/*  193 */       if (getGameRules().getBoolean("doDaylightCycle")) {
/*      */         
/*  195 */         long i = this.worldInfo.getWorldTime() + 24000L;
/*  196 */         this.worldInfo.setWorldTime(i - i % 24000L);
/*      */       } 
/*      */       
/*  199 */       wakeAllPlayers();
/*      */     } 
/*      */     
/*  202 */     this.theProfiler.startSection("mobSpawner");
/*      */     
/*  204 */     if (getGameRules().getBoolean("doMobSpawning") && this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD)
/*      */     {
/*  206 */       this.entitySpawner.findChunksForSpawning(this, this.spawnHostileMobs, this.spawnPeacefulMobs, (this.worldInfo.getWorldTotalTime() % 400L == 0L));
/*      */     }
/*      */     
/*  209 */     this.theProfiler.endStartSection("chunkSource");
/*  210 */     this.chunkProvider.unloadQueuedChunks();
/*  211 */     int j = calculateSkylightSubtracted(1.0F);
/*      */     
/*  213 */     if (j != getSkylightSubtracted())
/*      */     {
/*  215 */       setSkylightSubtracted(j);
/*      */     }
/*      */     
/*  218 */     this.worldInfo.setWorldTotalTime(this.worldInfo.getWorldTotalTime() + 1L);
/*      */     
/*  220 */     if (getGameRules().getBoolean("doDaylightCycle"))
/*      */     {
/*  222 */       this.worldInfo.setWorldTime(this.worldInfo.getWorldTime() + 1L);
/*      */     }
/*      */     
/*  225 */     this.theProfiler.endStartSection("tickPending");
/*  226 */     tickUpdates(false);
/*  227 */     this.theProfiler.endStartSection("tickBlocks");
/*  228 */     updateBlocks();
/*  229 */     this.theProfiler.endStartSection("chunkMap");
/*  230 */     this.thePlayerManager.tick();
/*  231 */     this.theProfiler.endStartSection("village");
/*  232 */     this.villageCollectionObj.tick();
/*  233 */     this.villageSiege.tick();
/*  234 */     this.theProfiler.endStartSection("portalForcer");
/*  235 */     this.worldTeleporter.removeStalePortalLocations(getTotalWorldTime());
/*  236 */     this.theProfiler.endSection();
/*  237 */     sendQueuedBlockEvents();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Biome.SpawnListEntry getSpawnListEntryForTypeAt(EnumCreatureType creatureType, BlockPos pos) {
/*  243 */     List<Biome.SpawnListEntry> list = getChunkProvider().getPossibleCreatures(creatureType, pos);
/*  244 */     return (list != null && !list.isEmpty()) ? (Biome.SpawnListEntry)WeightedRandom.getRandomItem(this.rand, list) : null;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canCreatureTypeSpawnHere(EnumCreatureType creatureType, Biome.SpawnListEntry spawnListEntry, BlockPos pos) {
/*  249 */     List<Biome.SpawnListEntry> list = getChunkProvider().getPossibleCreatures(creatureType, pos);
/*  250 */     return (list != null && !list.isEmpty()) ? list.contains(spawnListEntry) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAllPlayersSleepingFlag() {
/*  258 */     this.allPlayersSleeping = false;
/*      */     
/*  260 */     if (!this.playerEntities.isEmpty()) {
/*      */       
/*  262 */       int i = 0;
/*  263 */       int j = 0;
/*      */       
/*  265 */       for (EntityPlayer entityplayer : this.playerEntities) {
/*      */         
/*  267 */         if (entityplayer.isSpectator()) {
/*      */           
/*  269 */           i++; continue;
/*      */         } 
/*  271 */         if (entityplayer.isPlayerSleeping())
/*      */         {
/*  273 */           j++;
/*      */         }
/*      */       } 
/*      */       
/*  277 */       this.allPlayersSleeping = (j > 0 && j >= this.playerEntities.size() - i);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void wakeAllPlayers() {
/*  283 */     this.allPlayersSleeping = false;
/*      */     
/*  285 */     for (EntityPlayer entityplayer : this.playerEntities.stream().filter(EntityPlayer::isPlayerSleeping).collect(Collectors.toList()))
/*      */     {
/*  287 */       entityplayer.wakeUpPlayer(false, false, true);
/*      */     }
/*      */     
/*  290 */     if (getGameRules().getBoolean("doWeatherCycle"))
/*      */     {
/*  292 */       resetRainAndThunder();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void resetRainAndThunder() {
/*  301 */     this.worldInfo.setRainTime(0);
/*  302 */     this.worldInfo.setRaining(false);
/*  303 */     this.worldInfo.setThunderTime(0);
/*  304 */     this.worldInfo.setThundering(false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean areAllPlayersAsleep() {
/*  312 */     if (this.allPlayersSleeping && !this.isRemote) {
/*      */       
/*  314 */       for (EntityPlayer entityplayer : this.playerEntities) {
/*      */         
/*  316 */         if (!entityplayer.isSpectator() && !entityplayer.isPlayerFullyAsleep())
/*      */         {
/*  318 */           return false;
/*      */         }
/*      */       } 
/*      */       
/*  322 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  326 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitialSpawnLocation() {
/*  335 */     if (this.worldInfo.getSpawnY() <= 0)
/*      */     {
/*  337 */       this.worldInfo.setSpawnY(getSeaLevel() + 1);
/*      */     }
/*      */     
/*  340 */     int i = this.worldInfo.getSpawnX();
/*  341 */     int j = this.worldInfo.getSpawnZ();
/*  342 */     int k = 0;
/*      */     
/*  344 */     while (getGroundAboveSeaLevel(new BlockPos(i, 0, j)).getMaterial() == Material.AIR) {
/*      */       
/*  346 */       i += this.rand.nextInt(8) - this.rand.nextInt(8);
/*  347 */       j += this.rand.nextInt(8) - this.rand.nextInt(8);
/*  348 */       k++;
/*      */       
/*  350 */       if (k == 10000) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/*  356 */     this.worldInfo.setSpawnX(i);
/*  357 */     this.worldInfo.setSpawnZ(j);
/*      */   }
/*      */ 
/*      */   
/*      */   protected boolean isChunkLoaded(int x, int z, boolean allowEmpty) {
/*  362 */     return getChunkProvider().chunkExists(x, z);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void playerCheckLight() {
/*  367 */     this.theProfiler.startSection("playerCheckLight");
/*      */     
/*  369 */     if (!this.playerEntities.isEmpty()) {
/*      */       
/*  371 */       int i = this.rand.nextInt(this.playerEntities.size());
/*  372 */       EntityPlayer entityplayer = this.playerEntities.get(i);
/*  373 */       int j = MathHelper.floor(entityplayer.posX) + this.rand.nextInt(11) - 5;
/*  374 */       int k = MathHelper.floor(entityplayer.posY) + this.rand.nextInt(11) - 5;
/*  375 */       int l = MathHelper.floor(entityplayer.posZ) + this.rand.nextInt(11) - 5;
/*  376 */       checkLight(new BlockPos(j, k, l));
/*      */     } 
/*      */     
/*  379 */     this.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void updateBlocks() {
/*  384 */     playerCheckLight();
/*      */     
/*  386 */     if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
/*      */       
/*  388 */       Iterator<Chunk> iterator1 = this.thePlayerManager.getChunkIterator();
/*      */       
/*  390 */       while (iterator1.hasNext())
/*      */       {
/*  392 */         ((Chunk)iterator1.next()).onTick(false);
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  397 */       int i = getGameRules().getInt("randomTickSpeed");
/*  398 */       boolean flag = isRaining();
/*  399 */       boolean flag1 = isThundering();
/*  400 */       this.theProfiler.startSection("pollingChunks");
/*      */       
/*  402 */       for (Iterator<Chunk> iterator = this.thePlayerManager.getChunkIterator(); iterator.hasNext(); this.theProfiler.endSection()) {
/*      */         
/*  404 */         this.theProfiler.startSection("getChunk");
/*  405 */         Chunk chunk = iterator.next();
/*  406 */         int j = chunk.xPosition * 16;
/*  407 */         int k = chunk.zPosition * 16;
/*  408 */         this.theProfiler.endStartSection("checkNextLight");
/*  409 */         chunk.enqueueRelightChecks();
/*  410 */         this.theProfiler.endStartSection("tickChunk");
/*  411 */         chunk.onTick(false);
/*  412 */         this.theProfiler.endStartSection("thunder");
/*      */         
/*  414 */         if (flag && flag1 && this.rand.nextInt(100000) == 0) {
/*      */           
/*  416 */           this.updateLCG = this.updateLCG * 3 + 1013904223;
/*  417 */           int l = this.updateLCG >> 2;
/*  418 */           BlockPos blockpos = adjustPosToNearbyEntity(new BlockPos(j + (l & 0xF), 0, k + (l >> 8 & 0xF)));
/*      */           
/*  420 */           if (isRainingAt(blockpos)) {
/*      */             
/*  422 */             DifficultyInstance difficultyinstance = getDifficultyForLocation(blockpos);
/*      */             
/*  424 */             if (getGameRules().getBoolean("doMobSpawning") && this.rand.nextDouble() < difficultyinstance.getAdditionalDifficulty() * 0.01D) {
/*      */               
/*  426 */               EntitySkeletonHorse entityskeletonhorse = new EntitySkeletonHorse(this);
/*  427 */               entityskeletonhorse.func_190691_p(true);
/*  428 */               entityskeletonhorse.setGrowingAge(0);
/*  429 */               entityskeletonhorse.setPosition(blockpos.getX(), blockpos.getY(), blockpos.getZ());
/*  430 */               spawnEntityInWorld((Entity)entityskeletonhorse);
/*  431 */               addWeatherEffect((Entity)new EntityLightningBolt(this, blockpos.getX(), blockpos.getY(), blockpos.getZ(), true));
/*      */             }
/*      */             else {
/*      */               
/*  435 */               addWeatherEffect((Entity)new EntityLightningBolt(this, blockpos.getX(), blockpos.getY(), blockpos.getZ(), false));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  440 */         this.theProfiler.endStartSection("iceandsnow");
/*      */         
/*  442 */         if (this.rand.nextInt(16) == 0) {
/*      */           
/*  444 */           this.updateLCG = this.updateLCG * 3 + 1013904223;
/*  445 */           int j2 = this.updateLCG >> 2;
/*  446 */           BlockPos blockpos1 = getPrecipitationHeight(new BlockPos(j + (j2 & 0xF), 0, k + (j2 >> 8 & 0xF)));
/*  447 */           BlockPos blockpos2 = blockpos1.down();
/*      */           
/*  449 */           if (canBlockFreezeNoWater(blockpos2))
/*      */           {
/*  451 */             setBlockState(blockpos2, Blocks.ICE.getDefaultState());
/*      */           }
/*      */           
/*  454 */           if (flag && canSnowAt(blockpos1, true))
/*      */           {
/*  456 */             setBlockState(blockpos1, Blocks.SNOW_LAYER.getDefaultState());
/*      */           }
/*      */           
/*  459 */           if (flag && getBiome(blockpos2).canRain())
/*      */           {
/*  461 */             getBlockState(blockpos2).getBlock().fillWithRain(this, blockpos2);
/*      */           }
/*      */         } 
/*      */         
/*  465 */         this.theProfiler.endStartSection("tickBlocks");
/*      */         
/*  467 */         if (i > 0) {
/*      */           byte b; int m; ExtendedBlockStorage[] arrayOfExtendedBlockStorage;
/*  469 */           for (m = (arrayOfExtendedBlockStorage = chunk.getBlockStorageArray()).length, b = 0; b < m; ) { ExtendedBlockStorage extendedblockstorage = arrayOfExtendedBlockStorage[b];
/*      */             
/*  471 */             if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE && extendedblockstorage.getNeedsRandomTick())
/*      */             {
/*  473 */               for (int i1 = 0; i1 < i; i1++) {
/*      */                 
/*  475 */                 this.updateLCG = this.updateLCG * 3 + 1013904223;
/*  476 */                 int j1 = this.updateLCG >> 2;
/*  477 */                 int k1 = j1 & 0xF;
/*  478 */                 int l1 = j1 >> 8 & 0xF;
/*  479 */                 int i2 = j1 >> 16 & 0xF;
/*  480 */                 IBlockState iblockstate = extendedblockstorage.get(k1, i2, l1);
/*  481 */                 Block block = iblockstate.getBlock();
/*  482 */                 this.theProfiler.startSection("randomTick");
/*      */                 
/*  484 */                 if (block.getTickRandomly())
/*      */                 {
/*  486 */                   block.randomTick(this, new BlockPos(k1 + j, i2 + extendedblockstorage.getYLocation(), l1 + k), iblockstate, this.rand);
/*      */                 }
/*      */                 
/*  489 */                 this.theProfiler.endSection();
/*      */               } 
/*      */             }
/*      */             b++; }
/*      */         
/*      */         } 
/*      */       } 
/*  496 */       this.theProfiler.endSection();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected BlockPos adjustPosToNearbyEntity(BlockPos pos) {
/*  502 */     BlockPos blockpos = getPrecipitationHeight(pos);
/*  503 */     AxisAlignedBB axisalignedbb = (new AxisAlignedBB(blockpos, new BlockPos(blockpos.getX(), getHeight(), blockpos.getZ()))).expandXyz(3.0D);
/*  504 */     List<EntityLivingBase> list = getEntitiesWithinAABB(EntityLivingBase.class, axisalignedbb, new Predicate<EntityLivingBase>()
/*      */         {
/*      */           public boolean apply(@Nullable EntityLivingBase p_apply_1_)
/*      */           {
/*  508 */             return (p_apply_1_ != null && p_apply_1_.isEntityAlive() && WorldServer.this.canSeeSky(p_apply_1_.getPosition()));
/*      */           }
/*      */         });
/*      */     
/*  512 */     if (!list.isEmpty())
/*      */     {
/*  514 */       return ((EntityLivingBase)list.get(this.rand.nextInt(list.size()))).getPosition();
/*      */     }
/*      */ 
/*      */     
/*  518 */     if (blockpos.getY() == -1)
/*      */     {
/*  520 */       blockpos = blockpos.up(2);
/*      */     }
/*      */     
/*  523 */     return blockpos;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockTickPending(BlockPos pos, Block blockType) {
/*  529 */     NextTickListEntry nextticklistentry = new NextTickListEntry(pos, blockType);
/*  530 */     return this.pendingTickListEntriesThisTick.contains(nextticklistentry);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUpdateScheduled(BlockPos pos, Block blk) {
/*  538 */     NextTickListEntry nextticklistentry = new NextTickListEntry(pos, blk);
/*  539 */     return this.pendingTickListEntriesHashSet.contains(nextticklistentry);
/*      */   }
/*      */ 
/*      */   
/*      */   public void scheduleUpdate(BlockPos pos, Block blockIn, int delay) {
/*  544 */     updateBlockTick(pos, blockIn, delay, 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateBlockTick(BlockPos pos, Block blockIn, int delay, int priority) {
/*  549 */     Material material = blockIn.getDefaultState().getMaterial();
/*      */     
/*  551 */     if (this.scheduledUpdatesAreImmediate && material != Material.AIR) {
/*      */       
/*  553 */       if (blockIn.requiresUpdates()) {
/*      */         
/*  555 */         if (isAreaLoaded(pos.add(-8, -8, -8), pos.add(8, 8, 8))) {
/*      */           
/*  557 */           IBlockState iblockstate = getBlockState(pos);
/*      */           
/*  559 */           if (iblockstate.getMaterial() != Material.AIR && iblockstate.getBlock() == blockIn)
/*      */           {
/*  561 */             iblockstate.getBlock().updateTick(this, pos, iblockstate, this.rand);
/*      */           }
/*      */         } 
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*  568 */       delay = 1;
/*      */     } 
/*      */     
/*  571 */     NextTickListEntry nextticklistentry = new NextTickListEntry(pos, blockIn);
/*      */     
/*  573 */     if (isBlockLoaded(pos)) {
/*      */       
/*  575 */       if (material != Material.AIR) {
/*      */         
/*  577 */         nextticklistentry.setScheduledTime(delay + this.worldInfo.getWorldTotalTime());
/*  578 */         nextticklistentry.setPriority(priority);
/*      */       } 
/*      */       
/*  581 */       if (!this.pendingTickListEntriesHashSet.contains(nextticklistentry)) {
/*      */         
/*  583 */         this.pendingTickListEntriesHashSet.add(nextticklistentry);
/*  584 */         this.pendingTickListEntriesTreeSet.add(nextticklistentry);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void scheduleBlockUpdate(BlockPos pos, Block blockIn, int delay, int priority) {
/*  591 */     NextTickListEntry nextticklistentry = new NextTickListEntry(pos, blockIn);
/*  592 */     nextticklistentry.setPriority(priority);
/*  593 */     Material material = blockIn.getDefaultState().getMaterial();
/*      */     
/*  595 */     if (material != Material.AIR)
/*      */     {
/*  597 */       nextticklistentry.setScheduledTime(delay + this.worldInfo.getWorldTotalTime());
/*      */     }
/*      */     
/*  600 */     if (!this.pendingTickListEntriesHashSet.contains(nextticklistentry)) {
/*      */       
/*  602 */       this.pendingTickListEntriesHashSet.add(nextticklistentry);
/*  603 */       this.pendingTickListEntriesTreeSet.add(nextticklistentry);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntities() {
/*  612 */     if (this.playerEntities.isEmpty()) {
/*      */       
/*  614 */       if (this.updateEntityTick++ >= 300)
/*      */       {
/*      */         return;
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/*  621 */       resetUpdateEntityTick();
/*      */     } 
/*      */     
/*  624 */     this.provider.onWorldUpdateEntities();
/*  625 */     super.updateEntities();
/*      */   }
/*      */ 
/*      */   
/*      */   protected void tickPlayers() {
/*  630 */     super.tickPlayers();
/*  631 */     this.theProfiler.endStartSection("players");
/*      */     
/*  633 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/*      */       
/*  635 */       Entity entity = (Entity)this.playerEntities.get(i);
/*  636 */       Entity entity1 = entity.getRidingEntity();
/*      */       
/*  638 */       if (entity1 != null) {
/*      */         
/*  640 */         if (!entity1.isDead && entity1.isPassenger(entity)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/*  645 */         entity.dismountRidingEntity();
/*      */       } 
/*      */       
/*  648 */       this.theProfiler.startSection("tick");
/*      */       
/*  650 */       if (!entity.isDead) {
/*      */         
/*      */         try {
/*      */           
/*  654 */           updateEntity(entity);
/*      */         }
/*  656 */         catch (Throwable throwable) {
/*      */           
/*  658 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Ticking player");
/*  659 */           CrashReportCategory crashreportcategory = crashreport.makeCategory("Player being ticked");
/*  660 */           entity.addEntityCrashInfo(crashreportcategory);
/*  661 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */       }
/*      */       
/*  665 */       this.theProfiler.endSection();
/*  666 */       this.theProfiler.startSection("remove");
/*      */       
/*  668 */       if (entity.isDead) {
/*      */         
/*  670 */         int j = entity.chunkCoordX;
/*  671 */         int k = entity.chunkCoordZ;
/*      */         
/*  673 */         if (entity.addedToChunk && isChunkLoaded(j, k, true))
/*      */         {
/*  675 */           getChunkFromChunkCoords(j, k).removeEntity(entity);
/*      */         }
/*      */         
/*  678 */         this.loadedEntityList.remove(entity);
/*  679 */         onEntityRemoved(entity);
/*      */       } 
/*      */       
/*  682 */       this.theProfiler.endSection();
/*      */       continue;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetUpdateEntityTick() {
/*  691 */     this.updateEntityTick = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean tickUpdates(boolean p_72955_1_) {
/*  699 */     if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD)
/*      */     {
/*  701 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  705 */     int i = this.pendingTickListEntriesTreeSet.size();
/*      */     
/*  707 */     if (i != this.pendingTickListEntriesHashSet.size())
/*      */     {
/*  709 */       throw new IllegalStateException("TickNextTick list out of synch");
/*      */     }
/*      */ 
/*      */     
/*  713 */     if (i > 65536)
/*      */     {
/*  715 */       i = 65536;
/*      */     }
/*      */     
/*  718 */     this.theProfiler.startSection("cleaning");
/*      */     
/*  720 */     for (int j = 0; j < i; j++) {
/*      */       
/*  722 */       NextTickListEntry nextticklistentry = this.pendingTickListEntriesTreeSet.first();
/*      */       
/*  724 */       if (!p_72955_1_ && nextticklistentry.scheduledTime > this.worldInfo.getWorldTotalTime()) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  729 */       this.pendingTickListEntriesTreeSet.remove(nextticklistentry);
/*  730 */       this.pendingTickListEntriesHashSet.remove(nextticklistentry);
/*  731 */       this.pendingTickListEntriesThisTick.add(nextticklistentry);
/*      */     } 
/*      */     
/*  734 */     this.theProfiler.endSection();
/*  735 */     this.theProfiler.startSection("ticking");
/*  736 */     Iterator<NextTickListEntry> iterator = this.pendingTickListEntriesThisTick.iterator();
/*      */     
/*  738 */     while (iterator.hasNext()) {
/*      */       
/*  740 */       NextTickListEntry nextticklistentry1 = iterator.next();
/*  741 */       iterator.remove();
/*  742 */       int k = 0;
/*      */       
/*  744 */       if (isAreaLoaded(nextticklistentry1.position.add(0, 0, 0), nextticklistentry1.position.add(0, 0, 0))) {
/*      */         
/*  746 */         IBlockState iblockstate = getBlockState(nextticklistentry1.position);
/*      */         
/*  748 */         if (iblockstate.getMaterial() != Material.AIR && Block.isEqualTo(iblockstate.getBlock(), nextticklistentry1.getBlock())) {
/*      */           
/*      */           try {
/*      */             
/*  752 */             iblockstate.getBlock().updateTick(this, nextticklistentry1.position, iblockstate, this.rand);
/*      */           }
/*  754 */           catch (Throwable throwable) {
/*      */             
/*  756 */             CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while ticking a block");
/*  757 */             CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being ticked");
/*  758 */             CrashReportCategory.addBlockInfo(crashreportcategory, nextticklistentry1.position, iblockstate);
/*  759 */             throw new ReportedException(crashreport);
/*      */           } 
/*      */         }
/*      */         
/*      */         continue;
/*      */       } 
/*  765 */       scheduleUpdate(nextticklistentry1.position, nextticklistentry1.getBlock(), 0);
/*      */     } 
/*      */ 
/*      */     
/*  769 */     this.theProfiler.endSection();
/*  770 */     this.pendingTickListEntriesThisTick.clear();
/*  771 */     return !this.pendingTickListEntriesTreeSet.isEmpty();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public List<NextTickListEntry> getPendingBlockUpdates(Chunk chunkIn, boolean p_72920_2_) {
/*  779 */     ChunkPos chunkpos = chunkIn.getChunkCoordIntPair();
/*  780 */     int i = (chunkpos.chunkXPos << 4) - 2;
/*  781 */     int j = i + 16 + 2;
/*  782 */     int k = (chunkpos.chunkZPos << 4) - 2;
/*  783 */     int l = k + 16 + 2;
/*  784 */     return getPendingBlockUpdates(new StructureBoundingBox(i, 0, k, j, 256, l), p_72920_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public List<NextTickListEntry> getPendingBlockUpdates(StructureBoundingBox structureBB, boolean p_175712_2_) {
/*  790 */     List<NextTickListEntry> list = null;
/*      */     
/*  792 */     for (int i = 0; i < 2; i++) {
/*      */       Iterator<NextTickListEntry> iterator;
/*      */ 
/*      */       
/*  796 */       if (i == 0) {
/*      */         
/*  798 */         iterator = this.pendingTickListEntriesTreeSet.iterator();
/*      */       }
/*      */       else {
/*      */         
/*  802 */         iterator = this.pendingTickListEntriesThisTick.iterator();
/*      */       } 
/*      */       
/*  805 */       while (iterator.hasNext()) {
/*      */         
/*  807 */         NextTickListEntry nextticklistentry = iterator.next();
/*  808 */         BlockPos blockpos = nextticklistentry.position;
/*      */         
/*  810 */         if (blockpos.getX() >= structureBB.minX && blockpos.getX() < structureBB.maxX && blockpos.getZ() >= structureBB.minZ && blockpos.getZ() < structureBB.maxZ) {
/*      */           
/*  812 */           if (p_175712_2_) {
/*      */             
/*  814 */             if (i == 0)
/*      */             {
/*  816 */               this.pendingTickListEntriesHashSet.remove(nextticklistentry);
/*      */             }
/*      */             
/*  819 */             iterator.remove();
/*      */           } 
/*      */           
/*  822 */           if (list == null)
/*      */           {
/*  824 */             list = Lists.newArrayList();
/*      */           }
/*      */           
/*  827 */           list.add(nextticklistentry);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  832 */     return list;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntityWithOptionalForce(Entity entityIn, boolean forceUpdate) {
/*  840 */     if (!canSpawnAnimals() && (entityIn instanceof net.minecraft.entity.passive.EntityAnimal || entityIn instanceof net.minecraft.entity.passive.EntityWaterMob))
/*      */     {
/*  842 */       entityIn.setDead();
/*      */     }
/*      */     
/*  845 */     if (!canSpawnNPCs() && entityIn instanceof net.minecraft.entity.INpc)
/*      */     {
/*  847 */       entityIn.setDead();
/*      */     }
/*      */     
/*  850 */     super.updateEntityWithOptionalForce(entityIn, forceUpdate);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean canSpawnNPCs() {
/*  855 */     return this.mcServer.getCanSpawnNPCs();
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean canSpawnAnimals() {
/*  860 */     return this.mcServer.getCanSpawnAnimals();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected IChunkProvider createChunkProvider() {
/*  868 */     IChunkLoader ichunkloader = this.saveHandler.getChunkLoader(this.provider);
/*  869 */     return (IChunkProvider)new ChunkProviderServer(this, ichunkloader, this.provider.createChunkGenerator());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockModifiable(EntityPlayer player, BlockPos pos) {
/*  874 */     return (!this.mcServer.isBlockProtected(this, pos, player) && getWorldBorder().contains(pos));
/*      */   }
/*      */ 
/*      */   
/*      */   public void initialize(WorldSettings settings) {
/*  879 */     if (!this.worldInfo.isInitialized()) {
/*      */ 
/*      */       
/*      */       try {
/*  883 */         createSpawnPosition(settings);
/*      */         
/*  885 */         if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD)
/*      */         {
/*  887 */           setDebugWorldSettings();
/*      */         }
/*      */         
/*  890 */         super.initialize(settings);
/*      */       }
/*  892 */       catch (Throwable throwable) {
/*      */         
/*  894 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception initializing level");
/*      */ 
/*      */         
/*      */         try {
/*  898 */           addWorldInfoToCrashReport(crashreport);
/*      */         }
/*  900 */         catch (Throwable throwable1) {}
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  905 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/*  908 */       this.worldInfo.setServerInitialized(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void setDebugWorldSettings() {
/*  914 */     this.worldInfo.setMapFeaturesEnabled(false);
/*  915 */     this.worldInfo.setAllowCommands(true);
/*  916 */     this.worldInfo.setRaining(false);
/*  917 */     this.worldInfo.setThundering(false);
/*  918 */     this.worldInfo.setCleanWeatherTime(1000000000);
/*  919 */     this.worldInfo.setWorldTime(6000L);
/*  920 */     this.worldInfo.setGameType(GameType.SPECTATOR);
/*  921 */     this.worldInfo.setHardcore(false);
/*  922 */     this.worldInfo.setDifficulty(EnumDifficulty.PEACEFUL);
/*  923 */     this.worldInfo.setDifficultyLocked(true);
/*  924 */     getGameRules().setOrCreateGameRule("doDaylightCycle", "false");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void createSpawnPosition(WorldSettings settings) {
/*  932 */     if (!this.provider.canRespawnHere()) {
/*      */       
/*  934 */       this.worldInfo.setSpawn(BlockPos.ORIGIN.up(this.provider.getAverageGroundLevel()));
/*      */     }
/*  936 */     else if (this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD) {
/*      */       
/*  938 */       this.worldInfo.setSpawn(BlockPos.ORIGIN.up());
/*      */     }
/*      */     else {
/*      */       
/*  942 */       this.findingSpawnPoint = true;
/*  943 */       BiomeProvider biomeprovider = this.provider.getBiomeProvider();
/*  944 */       List<Biome> list = biomeprovider.getBiomesToSpawnIn();
/*  945 */       Random random = new Random(getSeed());
/*  946 */       BlockPos blockpos = biomeprovider.findBiomePosition(0, 0, 256, list, random);
/*  947 */       int i = 8;
/*  948 */       int j = this.provider.getAverageGroundLevel();
/*  949 */       int k = 8;
/*      */       
/*  951 */       if (blockpos != null) {
/*      */         
/*  953 */         i = blockpos.getX();
/*  954 */         k = blockpos.getZ();
/*      */       }
/*      */       else {
/*      */         
/*  958 */         LOGGER.warn("Unable to find spawn biome");
/*      */       } 
/*      */       
/*  961 */       int l = 0;
/*      */       
/*  963 */       while (!this.provider.canCoordinateBeSpawn(i, k)) {
/*      */         
/*  965 */         i += random.nextInt(64) - random.nextInt(64);
/*  966 */         k += random.nextInt(64) - random.nextInt(64);
/*  967 */         l++;
/*      */         
/*  969 */         if (l == 1000) {
/*      */           break;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  975 */       this.worldInfo.setSpawn(new BlockPos(i, j, k));
/*  976 */       this.findingSpawnPoint = false;
/*      */       
/*  978 */       if (settings.isBonusChestEnabled())
/*      */       {
/*  980 */         createBonusChest();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void createBonusChest() {
/*  990 */     WorldGeneratorBonusChest worldgeneratorbonuschest = new WorldGeneratorBonusChest();
/*      */     
/*  992 */     for (int i = 0; i < 10; i++) {
/*      */       
/*  994 */       int j = this.worldInfo.getSpawnX() + this.rand.nextInt(6) - this.rand.nextInt(6);
/*  995 */       int k = this.worldInfo.getSpawnZ() + this.rand.nextInt(6) - this.rand.nextInt(6);
/*  996 */       BlockPos blockpos = getTopSolidOrLiquidBlock(new BlockPos(j, 0, k)).up();
/*      */       
/*  998 */       if (worldgeneratorbonuschest.generate(this, this.rand, blockpos)) {
/*      */         break;
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
/*      */   @Nullable
/*      */   public BlockPos getSpawnCoordinate() {
/* 1012 */     return this.provider.getSpawnCoordinate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveAllChunks(boolean p_73044_1_, @Nullable IProgressUpdate progressCallback) throws MinecraftException {
/* 1020 */     ChunkProviderServer chunkproviderserver = getChunkProvider();
/*      */     
/* 1022 */     if (chunkproviderserver.canSave()) {
/*      */       
/* 1024 */       if (progressCallback != null)
/*      */       {
/* 1026 */         progressCallback.displaySavingString("Saving level");
/*      */       }
/*      */       
/* 1029 */       saveLevel();
/*      */       
/* 1031 */       if (progressCallback != null)
/*      */       {
/* 1033 */         progressCallback.displayLoadingString("Saving chunks");
/*      */       }
/*      */       
/* 1036 */       chunkproviderserver.saveChunks(p_73044_1_);
/*      */       
/* 1038 */       for (Chunk chunk : Lists.newArrayList(chunkproviderserver.getLoadedChunks())) {
/*      */         
/* 1040 */         if (chunk != null && !this.thePlayerManager.contains(chunk.xPosition, chunk.zPosition))
/*      */         {
/* 1042 */           chunkproviderserver.unload(chunk);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void saveChunkData() {
/* 1053 */     ChunkProviderServer chunkproviderserver = getChunkProvider();
/*      */     
/* 1055 */     if (chunkproviderserver.canSave())
/*      */     {
/* 1057 */       chunkproviderserver.saveExtraData();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void saveLevel() throws MinecraftException {
/* 1066 */     checkSessionLock(); byte b; int i;
/*      */     WorldServer[] arrayOfWorldServer;
/* 1068 */     for (i = (arrayOfWorldServer = this.mcServer.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/*      */       
/* 1070 */       if (worldserver instanceof WorldServerMulti)
/*      */       {
/* 1072 */         ((WorldServerMulti)worldserver).saveAdditionalData();
/*      */       }
/*      */       b++; }
/*      */     
/* 1076 */     this.worldInfo.setBorderSize(getWorldBorder().getDiameter());
/* 1077 */     this.worldInfo.getBorderCenterX(getWorldBorder().getCenterX());
/* 1078 */     this.worldInfo.getBorderCenterZ(getWorldBorder().getCenterZ());
/* 1079 */     this.worldInfo.setBorderSafeZone(getWorldBorder().getDamageBuffer());
/* 1080 */     this.worldInfo.setBorderDamagePerBlock(getWorldBorder().getDamageAmount());
/* 1081 */     this.worldInfo.setBorderWarningDistance(getWorldBorder().getWarningDistance());
/* 1082 */     this.worldInfo.setBorderWarningTime(getWorldBorder().getWarningTime());
/* 1083 */     this.worldInfo.setBorderLerpTarget(getWorldBorder().getTargetSize());
/* 1084 */     this.worldInfo.setBorderLerpTime(getWorldBorder().getTimeUntilTarget());
/* 1085 */     this.saveHandler.saveWorldInfoWithPlayer(this.worldInfo, this.mcServer.getPlayerList().getHostPlayerData());
/* 1086 */     this.mapStorage.saveAllData();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean spawnEntityInWorld(Entity entityIn) {
/* 1094 */     return canAddEntity(entityIn) ? super.spawnEntityInWorld(entityIn) : false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadEntities(Collection<Entity> entityCollection) {
/* 1099 */     for (Entity entity : Lists.newArrayList(entityCollection)) {
/*      */       
/* 1101 */       if (canAddEntity(entity)) {
/*      */         
/* 1103 */         this.loadedEntityList.add(entity);
/* 1104 */         onEntityAdded(entity);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean canAddEntity(Entity entityIn) {
/* 1111 */     if (entityIn.isDead) {
/*      */       
/* 1113 */       LOGGER.warn("Tried to add entity {} but it was marked as removed already", EntityList.func_191301_a(entityIn));
/* 1114 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1118 */     UUID uuid = entityIn.getUniqueID();
/*      */     
/* 1120 */     if (this.entitiesByUuid.containsKey(uuid)) {
/*      */       
/* 1122 */       Entity entity = this.entitiesByUuid.get(uuid);
/*      */       
/* 1124 */       if (this.unloadedEntityList.contains(entity)) {
/*      */         
/* 1126 */         this.unloadedEntityList.remove(entity);
/*      */       }
/*      */       else {
/*      */         
/* 1130 */         if (!(entityIn instanceof EntityPlayer)) {
/*      */           
/* 1132 */           LOGGER.warn("Keeping entity {} that already exists with UUID {}", EntityList.func_191301_a(entity), uuid.toString());
/* 1133 */           return false;
/*      */         } 
/*      */         
/* 1136 */         LOGGER.warn("Force-added player with duplicate UUID {}", uuid.toString());
/*      */       } 
/*      */       
/* 1139 */       removeEntityDangerously(entity);
/*      */     } 
/*      */     
/* 1142 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onEntityAdded(Entity entityIn) {
/* 1148 */     super.onEntityAdded(entityIn);
/* 1149 */     this.entitiesById.addKey(entityIn.getEntityId(), entityIn);
/* 1150 */     this.entitiesByUuid.put(entityIn.getUniqueID(), entityIn);
/* 1151 */     Entity[] aentity = entityIn.getParts();
/*      */     
/* 1153 */     if (aentity != null) {
/*      */       byte b; int i; Entity[] arrayOfEntity;
/* 1155 */       for (i = (arrayOfEntity = aentity).length, b = 0; b < i; ) { Entity entity = arrayOfEntity[b];
/*      */         
/* 1157 */         this.entitiesById.addKey(entity.getEntityId(), entity);
/*      */         b++; }
/*      */     
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void onEntityRemoved(Entity entityIn) {
/* 1164 */     super.onEntityRemoved(entityIn);
/* 1165 */     this.entitiesById.removeObject(entityIn.getEntityId());
/* 1166 */     this.entitiesByUuid.remove(entityIn.getUniqueID());
/* 1167 */     Entity[] aentity = entityIn.getParts();
/*      */     
/* 1169 */     if (aentity != null) {
/*      */       byte b; int i; Entity[] arrayOfEntity;
/* 1171 */       for (i = (arrayOfEntity = aentity).length, b = 0; b < i; ) { Entity entity = arrayOfEntity[b];
/*      */         
/* 1173 */         this.entitiesById.removeObject(entity.getEntityId());
/*      */         b++; }
/*      */     
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addWeatherEffect(Entity entityIn) {
/* 1183 */     if (super.addWeatherEffect(entityIn)) {
/*      */       
/* 1185 */       this.mcServer.getPlayerList().sendToAllNearExcept(null, entityIn.posX, entityIn.posY, entityIn.posZ, 512.0D, this.provider.getDimensionType().getId(), (Packet)new SPacketSpawnGlobalEntity(entityIn));
/* 1186 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 1190 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntityState(Entity entityIn, byte state) {
/* 1199 */     getEntityTracker().sendToTrackingAndSelf(entityIn, (Packet)new SPacketEntityStatus(entityIn, state));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChunkProviderServer getChunkProvider() {
/* 1207 */     return (ChunkProviderServer)super.getChunkProvider();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Explosion newExplosion(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking) {
/* 1215 */     Explosion explosion = new Explosion(this, entityIn, x, y, z, strength, isFlaming, isSmoking);
/* 1216 */     explosion.doExplosionA();
/* 1217 */     explosion.doExplosionB(false);
/*      */     
/* 1219 */     if (!isSmoking)
/*      */     {
/* 1221 */       explosion.clearAffectedBlockPositions();
/*      */     }
/*      */     
/* 1224 */     for (EntityPlayer entityplayer : this.playerEntities) {
/*      */       
/* 1226 */       if (entityplayer.getDistanceSq(x, y, z) < 4096.0D)
/*      */       {
/* 1228 */         ((EntityPlayerMP)entityplayer).connection.sendPacket((Packet)new SPacketExplosion(x, y, z, strength, explosion.getAffectedBlockPositions(), explosion.getPlayerKnockbackMap().get(entityplayer)));
/*      */       }
/*      */     } 
/*      */     
/* 1232 */     return explosion;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam) {
/* 1237 */     BlockEventData blockeventdata = new BlockEventData(pos, blockIn, eventID, eventParam);
/*      */     
/* 1239 */     for (BlockEventData blockeventdata1 : this.blockEventQueue[this.blockEventCacheIndex]) {
/*      */       
/* 1241 */       if (blockeventdata1.equals(blockeventdata)) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1247 */     this.blockEventQueue[this.blockEventCacheIndex].add(blockeventdata);
/*      */   }
/*      */ 
/*      */   
/*      */   private void sendQueuedBlockEvents() {
/* 1252 */     while (!this.blockEventQueue[this.blockEventCacheIndex].isEmpty()) {
/*      */       
/* 1254 */       int i = this.blockEventCacheIndex;
/* 1255 */       this.blockEventCacheIndex ^= 0x1;
/*      */       
/* 1257 */       for (BlockEventData blockeventdata : this.blockEventQueue[i]) {
/*      */         
/* 1259 */         if (fireBlockEvent(blockeventdata))
/*      */         {
/* 1261 */           this.mcServer.getPlayerList().sendToAllNearExcept(null, blockeventdata.getPosition().getX(), blockeventdata.getPosition().getY(), blockeventdata.getPosition().getZ(), 64.0D, this.provider.getDimensionType().getId(), (Packet)new SPacketBlockAction(blockeventdata.getPosition(), blockeventdata.getBlock(), blockeventdata.getEventID(), blockeventdata.getEventParameter()));
/*      */         }
/*      */       } 
/*      */       
/* 1265 */       this.blockEventQueue[i].clear();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean fireBlockEvent(BlockEventData event) {
/* 1271 */     IBlockState iblockstate = getBlockState(event.getPosition());
/* 1272 */     return (iblockstate.getBlock() == event.getBlock()) ? iblockstate.onBlockEventReceived(this, event.getPosition(), event.getEventID(), event.getEventParameter()) : false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void flush() {
/* 1280 */     this.saveHandler.flush();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateWeather() {
/* 1288 */     boolean flag = isRaining();
/* 1289 */     super.updateWeather();
/*      */     
/* 1291 */     if (this.prevRainingStrength != this.rainingStrength)
/*      */     {
/* 1293 */       this.mcServer.getPlayerList().sendPacketToAllPlayersInDimension((Packet)new SPacketChangeGameState(7, this.rainingStrength), this.provider.getDimensionType().getId());
/*      */     }
/*      */     
/* 1296 */     if (this.prevThunderingStrength != this.thunderingStrength)
/*      */     {
/* 1298 */       this.mcServer.getPlayerList().sendPacketToAllPlayersInDimension((Packet)new SPacketChangeGameState(8, this.thunderingStrength), this.provider.getDimensionType().getId());
/*      */     }
/*      */     
/* 1301 */     if (flag != isRaining()) {
/*      */       
/* 1303 */       if (flag) {
/*      */         
/* 1305 */         this.mcServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketChangeGameState(2, 0.0F));
/*      */       }
/*      */       else {
/*      */         
/* 1309 */         this.mcServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketChangeGameState(1, 0.0F));
/*      */       } 
/*      */       
/* 1312 */       this.mcServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketChangeGameState(7, this.rainingStrength));
/* 1313 */       this.mcServer.getPlayerList().sendPacketToAllPlayers((Packet)new SPacketChangeGameState(8, this.thunderingStrength));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public MinecraftServer getMinecraftServer() {
/* 1320 */     return this.mcServer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public EntityTracker getEntityTracker() {
/* 1328 */     return this.theEntityTracker;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PlayerChunkMap getPlayerChunkMap() {
/* 1336 */     return this.thePlayerManager;
/*      */   }
/*      */ 
/*      */   
/*      */   public Teleporter getDefaultTeleporter() {
/* 1341 */     return this.worldTeleporter;
/*      */   }
/*      */ 
/*      */   
/*      */   public TemplateManager getStructureTemplateManager() {
/* 1346 */     return this.saveHandler.getStructureTemplateManager();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, int numberOfParticles, double xOffset, double yOffset, double zOffset, double particleSpeed, int... particleArguments) {
/* 1354 */     spawnParticle(particleType, false, xCoord, yCoord, zCoord, numberOfParticles, xOffset, yOffset, zOffset, particleSpeed, particleArguments);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void spawnParticle(EnumParticleTypes particleType, boolean longDistance, double xCoord, double yCoord, double zCoord, int numberOfParticles, double xOffset, double yOffset, double zOffset, double particleSpeed, int... particleArguments) {
/* 1362 */     SPacketParticles spacketparticles = new SPacketParticles(particleType, longDistance, (float)xCoord, (float)yCoord, (float)zCoord, (float)xOffset, (float)yOffset, (float)zOffset, (float)particleSpeed, numberOfParticles, particleArguments);
/*      */     
/* 1364 */     for (int i = 0; i < this.playerEntities.size(); i++) {
/*      */       
/* 1366 */       EntityPlayerMP entityplayermp = (EntityPlayerMP)this.playerEntities.get(i);
/* 1367 */       sendPacketWithinDistance(entityplayermp, longDistance, xCoord, yCoord, zCoord, (Packet<?>)spacketparticles);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void spawnParticle(EntityPlayerMP player, EnumParticleTypes particle, boolean longDistance, double x, double y, double z, int count, double xOffset, double yOffset, double zOffset, double speed, int... arguments) {
/* 1373 */     SPacketParticles sPacketParticles = new SPacketParticles(particle, longDistance, (float)x, (float)y, (float)z, (float)xOffset, (float)yOffset, (float)zOffset, (float)speed, count, arguments);
/* 1374 */     sendPacketWithinDistance(player, longDistance, x, y, z, (Packet<?>)sPacketParticles);
/*      */   }
/*      */ 
/*      */   
/*      */   private void sendPacketWithinDistance(EntityPlayerMP player, boolean longDistance, double x, double y, double z, Packet<?> packetIn) {
/* 1379 */     BlockPos blockpos = player.getPosition();
/* 1380 */     double d0 = blockpos.distanceSq(x, y, z);
/*      */     
/* 1382 */     if (d0 <= 1024.0D || (longDistance && d0 <= 262144.0D))
/*      */     {
/* 1384 */       player.connection.sendPacket(packetIn);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Entity getEntityFromUuid(UUID uuid) {
/* 1391 */     return this.entitiesByUuid.get(uuid);
/*      */   }
/*      */ 
/*      */   
/*      */   public ListenableFuture<Object> addScheduledTask(Runnable runnableToSchedule) {
/* 1396 */     return this.mcServer.addScheduledTask(runnableToSchedule);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isCallingFromMinecraftThread() {
/* 1401 */     return this.mcServer.isCallingFromMinecraftThread();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public BlockPos func_190528_a(String p_190528_1_, BlockPos p_190528_2_, boolean p_190528_3_) {
/* 1407 */     return getChunkProvider().getStrongholdGen(this, p_190528_1_, p_190528_2_, p_190528_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public AdvancementManager func_191952_z() {
/* 1412 */     return this.field_191951_C;
/*      */   }
/*      */ 
/*      */   
/*      */   public FunctionManager func_193037_A() {
/* 1417 */     return this.field_193036_D;
/*      */   }
/*      */   
/*      */   static class ServerBlockEventList extends ArrayList<BlockEventData> {
/*      */     private ServerBlockEventList() {}
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\WorldServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */