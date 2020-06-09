/*      */ package net.minecraft.world;
/*      */ 
/*      */ import com.TominoCZ.FBP.FBP;
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.MoreObjects;
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Lists;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.advancements.AdvancementManager;
/*      */ import net.minecraft.advancements.FunctionManager;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.BlockLiquid;
/*      */ import net.minecraft.block.BlockObserver;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.properties.IProperty;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.crash.ICrashReportDetail;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.init.Biomes;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.pathfinding.PathWorldListener;
/*      */ import net.minecraft.profiler.Profiler;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.EntitySelectors;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.ITickable;
/*      */ import net.minecraft.util.IntHashMap;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.SoundCategory;
/*      */ import net.minecraft.util.SoundEvent;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.util.math.RayTraceResult;
/*      */ import net.minecraft.util.math.Vec3d;
/*      */ import net.minecraft.util.math.Vec3i;
/*      */ import net.minecraft.village.VillageCollection;
/*      */ import net.minecraft.world.biome.Biome;
/*      */ import net.minecraft.world.biome.BiomeProvider;
/*      */ import net.minecraft.world.border.WorldBorder;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.chunk.IChunkProvider;
/*      */ import net.minecraft.world.gen.structure.StructureBoundingBox;
/*      */ import net.minecraft.world.storage.ISaveHandler;
/*      */ import net.minecraft.world.storage.MapStorage;
/*      */ import net.minecraft.world.storage.WorldInfo;
/*      */ import net.minecraft.world.storage.WorldSavedData;
/*      */ import net.minecraft.world.storage.loot.LootTableManager;
/*      */ 
/*      */ public abstract class World
/*      */   implements IBlockAccess
/*      */ {
/*   68 */   private int seaLevel = 63;
/*      */ 
/*      */   
/*      */   protected boolean scheduledUpdatesAreImmediate;
/*      */ 
/*      */   
/*   74 */   public final List<Entity> loadedEntityList = Lists.newArrayList();
/*   75 */   protected final List<Entity> unloadedEntityList = Lists.newArrayList();
/*   76 */   public final List<TileEntity> loadedTileEntityList = Lists.newArrayList();
/*   77 */   public final List<TileEntity> tickableTileEntities = Lists.newArrayList();
/*   78 */   private final List<TileEntity> addedTileEntityList = Lists.newArrayList();
/*   79 */   private final List<TileEntity> tileEntitiesToBeRemoved = Lists.newArrayList();
/*   80 */   public final List<EntityPlayer> playerEntities = Lists.newArrayList();
/*   81 */   public final List<Entity> weatherEffects = Lists.newArrayList();
/*   82 */   protected final IntHashMap<Entity> entitiesById = new IntHashMap();
/*   83 */   private final long cloudColour = 16777215L;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int skylightSubtracted;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   93 */   protected int updateLCG = (new Random()).nextInt();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   98 */   protected final int DIST_HASH_MAGIC = 1013904223;
/*      */ 
/*      */   
/*      */   protected float prevRainingStrength;
/*      */   
/*      */   protected float rainingStrength;
/*      */   
/*      */   protected float prevThunderingStrength;
/*      */   
/*      */   protected float thunderingStrength;
/*      */   
/*      */   private int lastLightningBolt;
/*      */   
/*  111 */   public final Random rand = new Random();
/*      */   
/*      */   public final WorldProvider provider;
/*      */   
/*  115 */   protected PathWorldListener pathListener = new PathWorldListener();
/*      */ 
/*      */   
/*      */   protected List<IWorldEventListener> eventListeners;
/*      */ 
/*      */   
/*      */   protected IChunkProvider chunkProvider;
/*      */ 
/*      */   
/*      */   protected final ISaveHandler saveHandler;
/*      */ 
/*      */   
/*      */   protected WorldInfo worldInfo;
/*      */ 
/*      */   
/*      */   protected boolean findingSpawnPoint;
/*      */ 
/*      */   
/*      */   protected MapStorage mapStorage;
/*      */ 
/*      */   
/*      */   protected VillageCollection villageCollectionObj;
/*      */ 
/*      */   
/*      */   protected LootTableManager lootTable;
/*      */   
/*      */   protected AdvancementManager field_191951_C;
/*      */   
/*      */   protected FunctionManager field_193036_D;
/*      */   
/*      */   public final Profiler theProfiler;
/*      */   
/*      */   private final Calendar theCalendar;
/*      */   
/*      */   protected Scoreboard worldScoreboard;
/*      */   
/*      */   public final boolean isRemote;
/*      */   
/*      */   protected boolean spawnHostileMobs;
/*      */   
/*      */   protected boolean spawnPeacefulMobs;
/*      */   
/*      */   private boolean processingLoadedTiles;
/*      */   
/*      */   private final WorldBorder worldBorder;
/*      */   
/*      */   int[] lightUpdateBlockList;
/*      */ 
/*      */   
/*      */   protected World(ISaveHandler saveHandlerIn, WorldInfo info, WorldProvider providerIn, Profiler profilerIn, boolean client) {
/*  165 */     this.eventListeners = Lists.newArrayList((Object[])new IWorldEventListener[] { (IWorldEventListener)this.pathListener });
/*  166 */     this.theCalendar = Calendar.getInstance();
/*  167 */     this.worldScoreboard = new Scoreboard();
/*  168 */     this.spawnHostileMobs = true;
/*  169 */     this.spawnPeacefulMobs = true;
/*  170 */     this.lightUpdateBlockList = new int[32768];
/*  171 */     this.saveHandler = saveHandlerIn;
/*  172 */     this.theProfiler = profilerIn;
/*  173 */     this.worldInfo = info;
/*  174 */     this.provider = providerIn;
/*  175 */     this.isRemote = client;
/*  176 */     this.worldBorder = providerIn.createWorldBorder();
/*      */   }
/*      */ 
/*      */   
/*      */   public World init() {
/*  181 */     return this;
/*      */   }
/*      */ 
/*      */   
/*      */   public Biome getBiome(final BlockPos pos) {
/*  186 */     if (isBlockLoaded(pos)) {
/*      */       
/*  188 */       Chunk chunk = getChunkFromBlockCoords(pos);
/*      */ 
/*      */       
/*      */       try {
/*  192 */         return chunk.getBiome(pos, this.provider.getBiomeProvider());
/*      */       }
/*  194 */       catch (Throwable throwable) {
/*      */         
/*  196 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting biome");
/*  197 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Coordinates of biome request");
/*  198 */         crashreportcategory.setDetail("Location", new ICrashReportDetail<String>()
/*      */             {
/*      */               public String call() throws Exception
/*      */               {
/*  202 */                 return CrashReportCategory.getCoordinateInfo(pos);
/*      */               }
/*      */             });
/*  205 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  210 */     return this.provider.getBiomeProvider().getBiome(pos, Biomes.PLAINS);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public BiomeProvider getBiomeProvider() {
/*  216 */     return this.provider.getBiomeProvider();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void initialize(WorldSettings settings) {
/*  226 */     this.worldInfo.setServerInitialized(true);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public MinecraftServer getMinecraftServer() {
/*  232 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitialSpawnLocation() {
/*  240 */     setSpawnPoint(new BlockPos(8, 64, 8));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public IBlockState getGroundAboveSeaLevel(BlockPos pos) {
/*      */     BlockPos blockpos;
/*  247 */     for (blockpos = new BlockPos(pos.getX(), getSeaLevel(), pos.getZ()); !isAirBlock(blockpos.up()); blockpos = blockpos.up());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  252 */     return getBlockState(blockpos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isValid(BlockPos pos) {
/*  260 */     return (!isOutsideBuildHeight(pos) && pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isOutsideBuildHeight(BlockPos pos) {
/*  265 */     return !(pos.getY() >= 0 && pos.getY() < 256);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAirBlock(BlockPos pos) {
/*  274 */     return (getBlockState(pos).getMaterial() == Material.AIR);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockLoaded(BlockPos pos) {
/*  279 */     return isBlockLoaded(pos, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockLoaded(BlockPos pos, boolean allowEmpty) {
/*  284 */     return isChunkLoaded(pos.getX() >> 4, pos.getZ() >> 4, allowEmpty);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos center, int radius) {
/*  289 */     return isAreaLoaded(center, radius, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos center, int radius, boolean allowEmpty) {
/*  294 */     return isAreaLoaded(center.getX() - radius, center.getY() - radius, center.getZ() - radius, center.getX() + radius, center.getY() + radius, center.getZ() + radius, allowEmpty);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos from, BlockPos to) {
/*  299 */     return isAreaLoaded(from, to, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAreaLoaded(BlockPos from, BlockPos to, boolean allowEmpty) {
/*  304 */     return isAreaLoaded(from.getX(), from.getY(), from.getZ(), to.getX(), to.getY(), to.getZ(), allowEmpty);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAreaLoaded(StructureBoundingBox box) {
/*  309 */     return isAreaLoaded(box, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAreaLoaded(StructureBoundingBox box, boolean allowEmpty) {
/*  314 */     return isAreaLoaded(box.minX, box.minY, box.minZ, box.maxX, box.maxY, box.maxZ, allowEmpty);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean isAreaLoaded(int xStart, int yStart, int zStart, int xEnd, int yEnd, int zEnd, boolean allowEmpty) {
/*  319 */     if (yEnd >= 0 && yStart < 256) {
/*      */       
/*  321 */       xStart >>= 4;
/*  322 */       zStart >>= 4;
/*  323 */       xEnd >>= 4;
/*  324 */       zEnd >>= 4;
/*      */       
/*  326 */       for (int i = xStart; i <= xEnd; i++) {
/*      */         
/*  328 */         for (int j = zStart; j <= zEnd; j++) {
/*      */           
/*  330 */           if (!isChunkLoaded(i, j, allowEmpty))
/*      */           {
/*  332 */             return false;
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  337 */       return true;
/*      */     } 
/*      */ 
/*      */     
/*  341 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Chunk getChunkFromBlockCoords(BlockPos pos) {
/*  349 */     return getChunkFromChunkCoords(pos.getX() >> 4, pos.getZ() >> 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Chunk getChunkFromChunkCoords(int chunkX, int chunkZ) {
/*  357 */     return this.chunkProvider.provideChunk(chunkX, chunkZ);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_190526_b(int p_190526_1_, int p_190526_2_) {
/*  362 */     return isChunkLoaded(p_190526_1_, p_190526_2_, false) ? true : this.chunkProvider.func_191062_e(p_190526_1_, p_190526_2_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setBlockState(BlockPos pos, IBlockState newState, int flags) {
/*  372 */     if (isOutsideBuildHeight(pos))
/*      */     {
/*  374 */       return false;
/*      */     }
/*  376 */     if (!this.isRemote && this.worldInfo.getTerrainType() == WorldType.DEBUG_WORLD)
/*      */     {
/*  378 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  382 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  383 */     Block block = newState.getBlock();
/*  384 */     IBlockState iblockstate = chunk.setBlockState(pos, newState);
/*      */     
/*  386 */     if (iblockstate == null)
/*      */     {
/*  388 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  392 */     if (newState.getLightOpacity() != iblockstate.getLightOpacity() || newState.getLightValue() != iblockstate.getLightValue()) {
/*      */       
/*  394 */       this.theProfiler.startSection("checkLight");
/*  395 */       checkLight(pos);
/*  396 */       this.theProfiler.endSection();
/*      */     } 
/*      */     
/*  399 */     if ((flags & 0x2) != 0 && (!this.isRemote || (flags & 0x4) == 0) && chunk.isPopulated())
/*      */     {
/*  401 */       notifyBlockUpdate(pos, iblockstate, newState, flags);
/*      */     }
/*      */     
/*  404 */     if (!this.isRemote && (flags & 0x1) != 0) {
/*      */       
/*  406 */       notifyNeighborsRespectDebug(pos, iblockstate.getBlock(), true);
/*      */       
/*  408 */       if (newState.hasComparatorInputOverride())
/*      */       {
/*  410 */         updateComparatorOutputLevel(pos, block);
/*      */       }
/*      */     }
/*  413 */     else if (!this.isRemote && (flags & 0x10) == 0) {
/*      */       
/*  415 */       func_190522_c(pos, block);
/*      */     } 
/*      */     
/*  418 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setBlockToAir(BlockPos pos) {
/*  425 */     return setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean destroyBlock(BlockPos pos, boolean dropBlock) {
/*  433 */     IBlockState iblockstate = getBlockState(pos);
/*  434 */     Block block = iblockstate.getBlock();
/*      */     
/*  436 */     if (iblockstate.getMaterial() == Material.AIR)
/*      */     {
/*  438 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  442 */     playEvent(2001, pos, Block.getStateId(iblockstate));
/*      */     
/*  444 */     if (dropBlock)
/*      */     {
/*  446 */       block.dropBlockAsItem(this, pos, iblockstate, 0);
/*      */     }
/*      */     
/*  449 */     return setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean setBlockState(BlockPos pos, IBlockState state) {
/*  458 */     return setBlockState(pos, state, 3);
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyBlockUpdate(BlockPos pos, IBlockState oldState, IBlockState newState, int flags) {
/*  463 */     for (int i = 0; i < this.eventListeners.size(); i++)
/*      */     {
/*  465 */       ((IWorldEventListener)this.eventListeners.get(i)).notifyBlockUpdate(this, pos, oldState, newState, flags);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyNeighborsRespectDebug(BlockPos pos, Block blockType, boolean p_175722_3_) {
/*  471 */     if (this.worldInfo.getTerrainType() != WorldType.DEBUG_WORLD)
/*      */     {
/*  473 */       notifyNeighborsOfStateChange(pos, blockType, p_175722_3_);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markBlocksDirtyVertical(int x1, int z1, int x2, int z2) {
/*  482 */     if (x2 > z2) {
/*      */       
/*  484 */       int i = z2;
/*  485 */       z2 = x2;
/*  486 */       x2 = i;
/*      */     } 
/*      */     
/*  489 */     if (this.provider.func_191066_m())
/*      */     {
/*  491 */       for (int j = x2; j <= z2; j++)
/*      */       {
/*  493 */         checkLightFor(EnumSkyBlock.SKY, new BlockPos(x1, j, z1));
/*      */       }
/*      */     }
/*      */     
/*  497 */     markBlockRangeForRenderUpdate(x1, x2, z1, x1, z2, z1);
/*      */   }
/*      */ 
/*      */   
/*      */   public void markBlockRangeForRenderUpdate(BlockPos rangeMin, BlockPos rangeMax) {
/*  502 */     markBlockRangeForRenderUpdate(rangeMin.getX(), rangeMin.getY(), rangeMin.getZ(), rangeMax.getX(), rangeMax.getY(), rangeMax.getZ());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) {
/*  510 */     for (int i = 0; i < this.eventListeners.size(); i++)
/*      */     {
/*  512 */       ((IWorldEventListener)this.eventListeners.get(i)).markBlockRangeForRenderUpdate(x1, y1, z1, x2, y2, z2);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_190522_c(BlockPos p_190522_1_, Block p_190522_2_) {
/*  518 */     func_190529_b(p_190522_1_.west(), p_190522_2_, p_190522_1_);
/*  519 */     func_190529_b(p_190522_1_.east(), p_190522_2_, p_190522_1_);
/*  520 */     func_190529_b(p_190522_1_.down(), p_190522_2_, p_190522_1_);
/*  521 */     func_190529_b(p_190522_1_.up(), p_190522_2_, p_190522_1_);
/*  522 */     func_190529_b(p_190522_1_.north(), p_190522_2_, p_190522_1_);
/*  523 */     func_190529_b(p_190522_1_.south(), p_190522_2_, p_190522_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyNeighborsOfStateChange(BlockPos pos, Block blockType, boolean p_175685_3_) {
/*  528 */     func_190524_a(pos.west(), blockType, pos);
/*  529 */     func_190524_a(pos.east(), blockType, pos);
/*  530 */     func_190524_a(pos.down(), blockType, pos);
/*  531 */     func_190524_a(pos.up(), blockType, pos);
/*  532 */     func_190524_a(pos.north(), blockType, pos);
/*  533 */     func_190524_a(pos.south(), blockType, pos);
/*      */     
/*  535 */     if (p_175685_3_)
/*      */     {
/*  537 */       func_190522_c(pos, blockType);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyNeighborsOfStateExcept(BlockPos pos, Block blockType, EnumFacing skipSide) {
/*  543 */     if (skipSide != EnumFacing.WEST)
/*      */     {
/*  545 */       func_190524_a(pos.west(), blockType, pos);
/*      */     }
/*      */     
/*  548 */     if (skipSide != EnumFacing.EAST)
/*      */     {
/*  550 */       func_190524_a(pos.east(), blockType, pos);
/*      */     }
/*      */     
/*  553 */     if (skipSide != EnumFacing.DOWN)
/*      */     {
/*  555 */       func_190524_a(pos.down(), blockType, pos);
/*      */     }
/*      */     
/*  558 */     if (skipSide != EnumFacing.UP)
/*      */     {
/*  560 */       func_190524_a(pos.up(), blockType, pos);
/*      */     }
/*      */     
/*  563 */     if (skipSide != EnumFacing.NORTH)
/*      */     {
/*  565 */       func_190524_a(pos.north(), blockType, pos);
/*      */     }
/*      */     
/*  568 */     if (skipSide != EnumFacing.SOUTH)
/*      */     {
/*  570 */       func_190524_a(pos.south(), blockType, pos);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_190524_a(BlockPos p_190524_1_, final Block p_190524_2_, BlockPos p_190524_3_) {
/*  576 */     if (!this.isRemote) {
/*      */       
/*  578 */       IBlockState iblockstate = getBlockState(p_190524_1_);
/*      */ 
/*      */       
/*      */       try {
/*  582 */         iblockstate.neighborChanged(this, p_190524_1_, p_190524_2_, p_190524_3_);
/*      */       }
/*  584 */       catch (Throwable throwable) {
/*      */         
/*  586 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while updating neighbours");
/*  587 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being updated");
/*  588 */         crashreportcategory.setDetail("Source block type", new ICrashReportDetail<String>()
/*      */             {
/*      */               
/*      */               public String call() throws Exception
/*      */               {
/*      */                 try {
/*  594 */                   return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(Block.getIdFromBlock(this.val$p_190524_2_)), this.val$p_190524_2_.getUnlocalizedName(), this.val$p_190524_2_.getClass().getCanonicalName() });
/*      */                 }
/*  596 */                 catch (Throwable var2) {
/*      */                   
/*  598 */                   return "ID #" + Block.getIdFromBlock(p_190524_2_);
/*      */                 } 
/*      */               }
/*      */             });
/*  602 */         CrashReportCategory.addBlockInfo(crashreportcategory, p_190524_1_, iblockstate);
/*  603 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_190529_b(BlockPos p_190529_1_, final Block p_190529_2_, BlockPos p_190529_3_) {
/*  610 */     if (!this.isRemote) {
/*      */       
/*  612 */       IBlockState iblockstate = getBlockState(p_190529_1_);
/*      */       
/*  614 */       if (iblockstate.getBlock() == Blocks.field_190976_dk) {
/*      */         
/*      */         try {
/*      */           
/*  618 */           ((BlockObserver)iblockstate.getBlock()).func_190962_b(iblockstate, this, p_190529_1_, p_190529_2_, p_190529_3_);
/*      */         }
/*  620 */         catch (Throwable throwable) {
/*      */           
/*  622 */           CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception while updating neighbours");
/*  623 */           CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being updated");
/*  624 */           crashreportcategory.setDetail("Source block type", new ICrashReportDetail<String>()
/*      */               {
/*      */                 
/*      */                 public String call() throws Exception
/*      */                 {
/*      */                   try {
/*  630 */                     return String.format("ID #%d (%s // %s)", new Object[] { Integer.valueOf(Block.getIdFromBlock(this.val$p_190529_2_)), this.val$p_190529_2_.getUnlocalizedName(), this.val$p_190529_2_.getClass().getCanonicalName() });
/*      */                   }
/*  632 */                   catch (Throwable var2) {
/*      */                     
/*  634 */                     return "ID #" + Block.getIdFromBlock(p_190529_2_);
/*      */                   } 
/*      */                 }
/*      */               });
/*  638 */           CrashReportCategory.addBlockInfo(crashreportcategory, p_190529_1_, iblockstate);
/*  639 */           throw new ReportedException(crashreport);
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockTickPending(BlockPos pos, Block blockType) {
/*  647 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSeeSky(BlockPos pos) {
/*  652 */     return getChunkFromBlockCoords(pos).canSeeSky(pos);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBlockSeeSky(BlockPos pos) {
/*  657 */     if (pos.getY() >= getSeaLevel())
/*      */     {
/*  659 */       return canSeeSky(pos);
/*      */     }
/*      */ 
/*      */     
/*  663 */     BlockPos blockpos = new BlockPos(pos.getX(), getSeaLevel(), pos.getZ());
/*      */     
/*  665 */     if (!canSeeSky(blockpos))
/*      */     {
/*  667 */       return false;
/*      */     }
/*      */ 
/*      */     
/*  671 */     for (BlockPos blockpos1 = blockpos.down(); blockpos1.getY() > pos.getY(); blockpos1 = blockpos1.down()) {
/*      */       
/*  673 */       IBlockState iblockstate = getBlockState(blockpos1);
/*      */       
/*  675 */       if (iblockstate.getLightOpacity() > 0 && !iblockstate.getMaterial().isLiquid())
/*      */       {
/*  677 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  681 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLight(BlockPos pos) {
/*  688 */     if (pos.getY() < 0)
/*      */     {
/*  690 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  694 */     if (pos.getY() >= 256)
/*      */     {
/*  696 */       pos = new BlockPos(pos.getX(), 255, pos.getZ());
/*      */     }
/*      */     
/*  699 */     return getChunkFromBlockCoords(pos).getLightSubtracted(pos, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightFromNeighbors(BlockPos pos) {
/*  705 */     return getLight(pos, true);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLight(BlockPos pos, boolean checkNeighbors) {
/*  710 */     if (pos.getX() >= -30000000 && pos.getZ() >= -30000000 && pos.getX() < 30000000 && pos.getZ() < 30000000) {
/*      */       
/*  712 */       if (checkNeighbors && getBlockState(pos).useNeighborBrightness()) {
/*      */         
/*  714 */         int i1 = getLight(pos.up(), false);
/*  715 */         int i = getLight(pos.east(), false);
/*  716 */         int j = getLight(pos.west(), false);
/*  717 */         int k = getLight(pos.south(), false);
/*  718 */         int l = getLight(pos.north(), false);
/*      */         
/*  720 */         if (i > i1)
/*      */         {
/*  722 */           i1 = i;
/*      */         }
/*      */         
/*  725 */         if (j > i1)
/*      */         {
/*  727 */           i1 = j;
/*      */         }
/*      */         
/*  730 */         if (k > i1)
/*      */         {
/*  732 */           i1 = k;
/*      */         }
/*      */         
/*  735 */         if (l > i1)
/*      */         {
/*  737 */           i1 = l;
/*      */         }
/*      */         
/*  740 */         return i1;
/*      */       } 
/*  742 */       if (pos.getY() < 0)
/*      */       {
/*  744 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*  748 */       if (pos.getY() >= 256)
/*      */       {
/*  750 */         pos = new BlockPos(pos.getX(), 255, pos.getZ());
/*      */       }
/*      */       
/*  753 */       Chunk chunk = getChunkFromBlockCoords(pos);
/*  754 */       return chunk.getLightSubtracted(pos, this.skylightSubtracted);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  759 */     return 15;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getHeight(BlockPos pos) {
/*  768 */     return new BlockPos(pos.getX(), getHeight(pos.getX(), pos.getZ()), pos.getZ());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeight(int x, int z) {
/*      */     int i;
/*  778 */     if (x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000) {
/*      */       
/*  780 */       if (isChunkLoaded(x >> 4, z >> 4, true))
/*      */       {
/*  782 */         i = getChunkFromChunkCoords(x >> 4, z >> 4).getHeightValue(x & 0xF, z & 0xF);
/*      */       }
/*      */       else
/*      */       {
/*  786 */         i = 0;
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  791 */       i = getSeaLevel() + 1;
/*      */     } 
/*      */     
/*  794 */     return i;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public int getChunksLowestHorizon(int x, int z) {
/*  804 */     if (x >= -30000000 && z >= -30000000 && x < 30000000 && z < 30000000) {
/*      */       
/*  806 */       if (!isChunkLoaded(x >> 4, z >> 4, true))
/*      */       {
/*  808 */         return 0;
/*      */       }
/*      */ 
/*      */       
/*  812 */       Chunk chunk = getChunkFromChunkCoords(x >> 4, z >> 4);
/*  813 */       return chunk.getLowestHeight();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  818 */     return getSeaLevel() + 1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightFromNeighborsFor(EnumSkyBlock type, BlockPos pos) {
/*  824 */     if (!this.provider.func_191066_m() && type == EnumSkyBlock.SKY)
/*      */     {
/*  826 */       return 0;
/*      */     }
/*      */ 
/*      */     
/*  830 */     if (pos.getY() < 0)
/*      */     {
/*  832 */       pos = new BlockPos(pos.getX(), 0, pos.getZ());
/*      */     }
/*      */     
/*  835 */     if (!isValid(pos))
/*      */     {
/*  837 */       return type.defaultLightValue;
/*      */     }
/*  839 */     if (!isBlockLoaded(pos))
/*      */     {
/*  841 */       return type.defaultLightValue;
/*      */     }
/*  843 */     if (getBlockState(pos).useNeighborBrightness()) {
/*      */       
/*  845 */       int i1 = getLightFor(type, pos.up());
/*  846 */       int i = getLightFor(type, pos.east());
/*  847 */       int j = getLightFor(type, pos.west());
/*  848 */       int k = getLightFor(type, pos.south());
/*  849 */       int l = getLightFor(type, pos.north());
/*      */       
/*  851 */       if (i > i1)
/*      */       {
/*  853 */         i1 = i;
/*      */       }
/*      */       
/*  856 */       if (j > i1)
/*      */       {
/*  858 */         i1 = j;
/*      */       }
/*      */       
/*  861 */       if (k > i1)
/*      */       {
/*  863 */         i1 = k;
/*      */       }
/*      */       
/*  866 */       if (l > i1)
/*      */       {
/*  868 */         i1 = l;
/*      */       }
/*      */       
/*  871 */       return i1;
/*      */     } 
/*      */ 
/*      */     
/*  875 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  876 */     return chunk.getLightFor(type, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightFor(EnumSkyBlock type, BlockPos pos) {
/*  883 */     if (pos.getY() < 0)
/*      */     {
/*  885 */       pos = new BlockPos(pos.getX(), 0, pos.getZ());
/*      */     }
/*      */     
/*  888 */     if (!isValid(pos))
/*      */     {
/*  890 */       return type.defaultLightValue;
/*      */     }
/*  892 */     if (!isBlockLoaded(pos))
/*      */     {
/*  894 */       return type.defaultLightValue;
/*      */     }
/*      */ 
/*      */     
/*  898 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  899 */     return chunk.getLightFor(type, pos);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLightFor(EnumSkyBlock type, BlockPos pos, int lightValue) {
/*  905 */     if (isValid(pos))
/*      */     {
/*  907 */       if (isBlockLoaded(pos)) {
/*      */         
/*  909 */         Chunk chunk = getChunkFromBlockCoords(pos);
/*  910 */         chunk.setLightFor(type, pos, lightValue);
/*  911 */         notifyLightSet(pos);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void notifyLightSet(BlockPos pos) {
/*  918 */     for (int i = 0; i < this.eventListeners.size(); i++)
/*      */     {
/*  920 */       ((IWorldEventListener)this.eventListeners.get(i)).notifyLightSet(pos);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public int getCombinedLight(BlockPos pos, int lightValue) {
/*  926 */     int i = getLightFromNeighborsFor(EnumSkyBlock.SKY, pos);
/*  927 */     int j = getLightFromNeighborsFor(EnumSkyBlock.BLOCK, pos);
/*      */     
/*  929 */     if (j < lightValue)
/*      */     {
/*  931 */       j = lightValue;
/*      */     }
/*      */     
/*  934 */     return i << 20 | j << 4;
/*      */   }
/*      */ 
/*      */   
/*      */   public float getLightBrightness(BlockPos pos) {
/*  939 */     return this.provider.getLightBrightnessTable()[getLightFromNeighbors(pos)];
/*      */   }
/*      */ 
/*      */   
/*      */   public IBlockState getBlockState(BlockPos pos) {
/*  944 */     if (isOutsideBuildHeight(pos))
/*      */     {
/*  946 */       return Blocks.AIR.getDefaultState();
/*      */     }
/*      */ 
/*      */     
/*  950 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*  951 */     return chunk.getBlockState(pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isDaytime() {
/*  960 */     return (this.skylightSubtracted < 4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public RayTraceResult rayTraceBlocks(Vec3d start, Vec3d end) {
/*  970 */     return rayTraceBlocks(start, end, false, false, false);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public RayTraceResult rayTraceBlocks(Vec3d start, Vec3d end, boolean stopOnLiquid) {
/*  976 */     return rayTraceBlocks(start, end, stopOnLiquid, false, false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public RayTraceResult rayTraceBlocks(Vec3d vec31, Vec3d vec32, boolean stopOnLiquid, boolean ignoreBlockWithoutBoundingBox, boolean returnLastUncollidableBlock) {
/*  987 */     if (!Double.isNaN(vec31.xCoord) && !Double.isNaN(vec31.yCoord) && !Double.isNaN(vec31.zCoord)) {
/*      */       
/*  989 */       if (!Double.isNaN(vec32.xCoord) && !Double.isNaN(vec32.yCoord) && !Double.isNaN(vec32.zCoord)) {
/*      */         
/*  991 */         int i = MathHelper.floor(vec32.xCoord);
/*  992 */         int j = MathHelper.floor(vec32.yCoord);
/*  993 */         int k = MathHelper.floor(vec32.zCoord);
/*  994 */         int l = MathHelper.floor(vec31.xCoord);
/*  995 */         int i1 = MathHelper.floor(vec31.yCoord);
/*  996 */         int j1 = MathHelper.floor(vec31.zCoord);
/*  997 */         BlockPos blockpos = new BlockPos(l, i1, j1);
/*  998 */         IBlockState iblockstate = getBlockState(blockpos);
/*  999 */         Block block = iblockstate.getBlock();
/*      */         
/* 1001 */         if ((!ignoreBlockWithoutBoundingBox || iblockstate.getCollisionBoundingBox(this, blockpos) != Block.NULL_AABB) && block.canCollideCheck(iblockstate, stopOnLiquid)) {
/*      */           
/* 1003 */           RayTraceResult raytraceresult = iblockstate.collisionRayTrace(this, blockpos, vec31, vec32);
/*      */           
/* 1005 */           if (raytraceresult != null)
/*      */           {
/* 1007 */             return raytraceresult;
/*      */           }
/*      */         } 
/*      */         
/* 1011 */         RayTraceResult raytraceresult2 = null;
/* 1012 */         int k1 = 200;
/*      */         
/* 1014 */         while (k1-- >= 0) {
/*      */           EnumFacing enumfacing;
/* 1016 */           if (Double.isNaN(vec31.xCoord) || Double.isNaN(vec31.yCoord) || Double.isNaN(vec31.zCoord))
/*      */           {
/* 1018 */             return null;
/*      */           }
/*      */           
/* 1021 */           if (l == i && i1 == j && j1 == k)
/*      */           {
/* 1023 */             return returnLastUncollidableBlock ? raytraceresult2 : null;
/*      */           }
/*      */           
/* 1026 */           boolean flag2 = true;
/* 1027 */           boolean flag = true;
/* 1028 */           boolean flag1 = true;
/* 1029 */           double d0 = 999.0D;
/* 1030 */           double d1 = 999.0D;
/* 1031 */           double d2 = 999.0D;
/*      */           
/* 1033 */           if (i > l) {
/*      */             
/* 1035 */             d0 = l + 1.0D;
/*      */           }
/* 1037 */           else if (i < l) {
/*      */             
/* 1039 */             d0 = l + 0.0D;
/*      */           }
/*      */           else {
/*      */             
/* 1043 */             flag2 = false;
/*      */           } 
/*      */           
/* 1046 */           if (j > i1) {
/*      */             
/* 1048 */             d1 = i1 + 1.0D;
/*      */           }
/* 1050 */           else if (j < i1) {
/*      */             
/* 1052 */             d1 = i1 + 0.0D;
/*      */           }
/*      */           else {
/*      */             
/* 1056 */             flag = false;
/*      */           } 
/*      */           
/* 1059 */           if (k > j1) {
/*      */             
/* 1061 */             d2 = j1 + 1.0D;
/*      */           }
/* 1063 */           else if (k < j1) {
/*      */             
/* 1065 */             d2 = j1 + 0.0D;
/*      */           }
/*      */           else {
/*      */             
/* 1069 */             flag1 = false;
/*      */           } 
/*      */           
/* 1072 */           double d3 = 999.0D;
/* 1073 */           double d4 = 999.0D;
/* 1074 */           double d5 = 999.0D;
/* 1075 */           double d6 = vec32.xCoord - vec31.xCoord;
/* 1076 */           double d7 = vec32.yCoord - vec31.yCoord;
/* 1077 */           double d8 = vec32.zCoord - vec31.zCoord;
/*      */           
/* 1079 */           if (flag2)
/*      */           {
/* 1081 */             d3 = (d0 - vec31.xCoord) / d6;
/*      */           }
/*      */           
/* 1084 */           if (flag)
/*      */           {
/* 1086 */             d4 = (d1 - vec31.yCoord) / d7;
/*      */           }
/*      */           
/* 1089 */           if (flag1)
/*      */           {
/* 1091 */             d5 = (d2 - vec31.zCoord) / d8;
/*      */           }
/*      */           
/* 1094 */           if (d3 == -0.0D)
/*      */           {
/* 1096 */             d3 = -1.0E-4D;
/*      */           }
/*      */           
/* 1099 */           if (d4 == -0.0D)
/*      */           {
/* 1101 */             d4 = -1.0E-4D;
/*      */           }
/*      */           
/* 1104 */           if (d5 == -0.0D)
/*      */           {
/* 1106 */             d5 = -1.0E-4D;
/*      */           }
/*      */ 
/*      */ 
/*      */           
/* 1111 */           if (d3 < d4 && d3 < d5) {
/*      */             
/* 1113 */             enumfacing = (i > l) ? EnumFacing.WEST : EnumFacing.EAST;
/* 1114 */             vec31 = new Vec3d(d0, vec31.yCoord + d7 * d3, vec31.zCoord + d8 * d3);
/*      */           }
/* 1116 */           else if (d4 < d5) {
/*      */             
/* 1118 */             enumfacing = (j > i1) ? EnumFacing.DOWN : EnumFacing.UP;
/* 1119 */             vec31 = new Vec3d(vec31.xCoord + d6 * d4, d1, vec31.zCoord + d8 * d4);
/*      */           }
/*      */           else {
/*      */             
/* 1123 */             enumfacing = (k > j1) ? EnumFacing.NORTH : EnumFacing.SOUTH;
/* 1124 */             vec31 = new Vec3d(vec31.xCoord + d6 * d5, vec31.yCoord + d7 * d5, d2);
/*      */           } 
/*      */           
/* 1127 */           l = MathHelper.floor(vec31.xCoord) - ((enumfacing == EnumFacing.EAST) ? 1 : 0);
/* 1128 */           i1 = MathHelper.floor(vec31.yCoord) - ((enumfacing == EnumFacing.UP) ? 1 : 0);
/* 1129 */           j1 = MathHelper.floor(vec31.zCoord) - ((enumfacing == EnumFacing.SOUTH) ? 1 : 0);
/* 1130 */           blockpos = new BlockPos(l, i1, j1);
/* 1131 */           IBlockState iblockstate1 = getBlockState(blockpos);
/* 1132 */           Block block1 = iblockstate1.getBlock();
/*      */           
/* 1134 */           if (!ignoreBlockWithoutBoundingBox || iblockstate1.getMaterial() == Material.PORTAL || iblockstate1.getCollisionBoundingBox(this, blockpos) != Block.NULL_AABB) {
/*      */             
/* 1136 */             if (block1.canCollideCheck(iblockstate1, stopOnLiquid)) {
/*      */               
/* 1138 */               RayTraceResult raytraceresult1 = iblockstate1.collisionRayTrace(this, blockpos, vec31, vec32);
/*      */               
/* 1140 */               if (raytraceresult1 != null)
/*      */               {
/* 1142 */                 return raytraceresult1;
/*      */               }
/*      */               
/*      */               continue;
/*      */             } 
/* 1147 */             raytraceresult2 = new RayTraceResult(RayTraceResult.Type.MISS, vec31, enumfacing, blockpos);
/*      */           } 
/*      */         } 
/*      */ 
/*      */         
/* 1152 */         return returnLastUncollidableBlock ? raytraceresult2 : null;
/*      */       } 
/*      */ 
/*      */       
/* 1156 */       return null;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/* 1161 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSound(@Nullable EntityPlayer player, BlockPos pos, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
/* 1170 */     playSound(player, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, soundIn, category, volume, pitch);
/*      */   }
/*      */ 
/*      */   
/*      */   public void playSound(@Nullable EntityPlayer player, double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch) {
/* 1175 */     for (int i = 0; i < this.eventListeners.size(); i++)
/*      */     {
/* 1177 */       ((IWorldEventListener)this.eventListeners.get(i)).playSoundToAllNearExcept(player, soundIn, category, x, y, z, volume, pitch);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void playSound(double x, double y, double z, SoundEvent soundIn, SoundCategory category, float volume, float pitch, boolean distanceDelay) {}
/*      */ 
/*      */   
/*      */   public void playRecord(BlockPos blockPositionIn, @Nullable SoundEvent soundEventIn) {
/* 1187 */     for (int i = 0; i < this.eventListeners.size(); i++)
/*      */     {
/* 1189 */       ((IWorldEventListener)this.eventListeners.get(i)).playRecord(soundEventIn, blockPositionIn);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void spawnParticle(EnumParticleTypes particleType, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
/* 1195 */     spawnParticle(particleType.getParticleID(), particleType.getShouldIgnoreRange(), xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
/*      */   }
/*      */ 
/*      */   
/*      */   public void func_190523_a(int p_190523_1_, double p_190523_2_, double p_190523_4_, double p_190523_6_, double p_190523_8_, double p_190523_10_, double p_190523_12_, int... p_190523_14_) {
/* 1200 */     for (int i = 0; i < this.eventListeners.size(); i++)
/*      */     {
/* 1202 */       ((IWorldEventListener)this.eventListeners.get(i)).func_190570_a(p_190523_1_, false, true, p_190523_2_, p_190523_4_, p_190523_6_, p_190523_8_, p_190523_10_, p_190523_12_, p_190523_14_);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void spawnParticle(EnumParticleTypes particleType, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
/* 1208 */     spawnParticle(particleType.getParticleID(), !(!particleType.getShouldIgnoreRange() && !ignoreRange), xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
/*      */   }
/*      */ 
/*      */   
/*      */   private void spawnParticle(int particleID, boolean ignoreRange, double xCood, double yCoord, double zCoord, double xSpeed, double ySpeed, double zSpeed, int... parameters) {
/* 1213 */     for (int i = 0; i < this.eventListeners.size(); i++)
/*      */     {
/* 1215 */       ((IWorldEventListener)this.eventListeners.get(i)).spawnParticle(particleID, ignoreRange, xCood, yCoord, zCoord, xSpeed, ySpeed, zSpeed, parameters);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean addWeatherEffect(Entity entityIn) {
/* 1224 */     this.weatherEffects.add(entityIn);
/* 1225 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean spawnEntityInWorld(Entity entityIn) {
/* 1233 */     int i = MathHelper.floor(entityIn.posX / 16.0D);
/* 1234 */     int j = MathHelper.floor(entityIn.posZ / 16.0D);
/* 1235 */     boolean flag = entityIn.forceSpawn;
/*      */     
/* 1237 */     if (entityIn instanceof EntityPlayer)
/*      */     {
/* 1239 */       flag = true;
/*      */     }
/*      */     
/* 1242 */     if (!flag && !isChunkLoaded(i, j, false))
/*      */     {
/* 1244 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 1248 */     if (entityIn instanceof EntityPlayer) {
/*      */       
/* 1250 */       EntityPlayer entityplayer = (EntityPlayer)entityIn;
/* 1251 */       this.playerEntities.add(entityplayer);
/* 1252 */       updateAllPlayersSleepingFlag();
/*      */     } 
/*      */     
/* 1255 */     getChunkFromChunkCoords(i, j).addEntity(entityIn);
/* 1256 */     this.loadedEntityList.add(entityIn);
/* 1257 */     onEntityAdded(entityIn);
/*      */ 
/*      */     
/* 1260 */     if (FBP.enabled && this instanceof net.minecraft.client.multiplayer.WorldClient) {
/* 1261 */       FBP.INSTANCE.eventHandler.onEntityJoinWorldEvent(entityIn);
/*      */     }
/*      */     
/* 1264 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void onEntityAdded(Entity entityIn) {
/* 1270 */     for (int i = 0; i < this.eventListeners.size(); i++)
/*      */     {
/* 1272 */       ((IWorldEventListener)this.eventListeners.get(i)).onEntityAdded(entityIn);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void onEntityRemoved(Entity entityIn) {
/* 1278 */     for (int i = 0; i < this.eventListeners.size(); i++)
/*      */     {
/* 1280 */       ((IWorldEventListener)this.eventListeners.get(i)).onEntityRemoved(entityIn);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntity(Entity entityIn) {
/* 1289 */     if (entityIn.isBeingRidden())
/*      */     {
/* 1291 */       entityIn.removePassengers();
/*      */     }
/*      */     
/* 1294 */     if (entityIn.isRiding())
/*      */     {
/* 1296 */       entityIn.dismountRidingEntity();
/*      */     }
/*      */     
/* 1299 */     entityIn.setDead();
/*      */     
/* 1301 */     if (entityIn instanceof EntityPlayer) {
/*      */       
/* 1303 */       this.playerEntities.remove(entityIn);
/* 1304 */       updateAllPlayersSleepingFlag();
/* 1305 */       onEntityRemoved(entityIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntityDangerously(Entity entityIn) {
/* 1314 */     entityIn.setDropItemsWhenDead(false);
/* 1315 */     entityIn.setDead();
/*      */     
/* 1317 */     if (entityIn instanceof EntityPlayer) {
/*      */       
/* 1319 */       this.playerEntities.remove(entityIn);
/* 1320 */       updateAllPlayersSleepingFlag();
/*      */     } 
/*      */     
/* 1323 */     int i = entityIn.chunkCoordX;
/* 1324 */     int j = entityIn.chunkCoordZ;
/*      */     
/* 1326 */     if (entityIn.addedToChunk && isChunkLoaded(i, j, true))
/*      */     {
/* 1328 */       getChunkFromChunkCoords(i, j).removeEntity(entityIn);
/*      */     }
/*      */     
/* 1331 */     this.loadedEntityList.remove(entityIn);
/* 1332 */     onEntityRemoved(entityIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEventListener(IWorldEventListener listener) {
/* 1340 */     this.eventListeners.add(listener);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEventListener(IWorldEventListener listener) {
/* 1348 */     this.eventListeners.remove(listener);
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean func_191504_a(@Nullable Entity p_191504_1_, AxisAlignedBB p_191504_2_, boolean p_191504_3_, @Nullable List<AxisAlignedBB> p_191504_4_) {
/* 1353 */     int i = MathHelper.floor(p_191504_2_.minX) - 1;
/* 1354 */     int j = MathHelper.ceil(p_191504_2_.maxX) + 1;
/* 1355 */     int k = MathHelper.floor(p_191504_2_.minY) - 1;
/* 1356 */     int l = MathHelper.ceil(p_191504_2_.maxY) + 1;
/* 1357 */     int i1 = MathHelper.floor(p_191504_2_.minZ) - 1;
/* 1358 */     int j1 = MathHelper.ceil(p_191504_2_.maxZ) + 1;
/* 1359 */     WorldBorder worldborder = getWorldBorder();
/* 1360 */     boolean flag = (p_191504_1_ != null && p_191504_1_.isOutsideBorder());
/* 1361 */     boolean flag1 = (p_191504_1_ != null && func_191503_g(p_191504_1_));
/* 1362 */     IBlockState iblockstate = Blocks.STONE.getDefaultState();
/* 1363 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/*      */ 
/*      */     
/*      */     try {
/* 1367 */       for (int k1 = i; k1 < j; k1++) {
/*      */         
/* 1369 */         for (int l1 = i1; l1 < j1; l1++) {
/*      */           
/* 1371 */           boolean flag2 = !(k1 != i && k1 != j - 1);
/* 1372 */           boolean flag3 = !(l1 != i1 && l1 != j1 - 1);
/*      */           
/* 1374 */           if ((!flag2 || !flag3) && isBlockLoaded((BlockPos)blockpos$pooledmutableblockpos.setPos(k1, 64, l1)))
/*      */           {
/* 1376 */             for (int i2 = k; i2 < l; i2++) {
/*      */               
/* 1378 */               if ((!flag2 && !flag3) || i2 != l - 1) {
/*      */                 IBlockState iblockstate1;
/* 1380 */                 if (p_191504_3_) {
/*      */                   
/* 1382 */                   if (k1 < -30000000 || k1 >= 30000000 || l1 < -30000000 || l1 >= 30000000)
/*      */                   {
/* 1384 */                     boolean lvt_21_2_ = true;
/* 1385 */                     return lvt_21_2_;
/*      */                   }
/*      */                 
/* 1388 */                 } else if (p_191504_1_ != null && flag == flag1) {
/*      */                   
/* 1390 */                   p_191504_1_.setOutsideBorder(!flag1);
/*      */                 } 
/*      */                 
/* 1393 */                 blockpos$pooledmutableblockpos.setPos(k1, i2, l1);
/*      */ 
/*      */                 
/* 1396 */                 if (!p_191504_3_ && !worldborder.contains((BlockPos)blockpos$pooledmutableblockpos) && flag1) {
/*      */                   
/* 1398 */                   iblockstate1 = iblockstate;
/*      */                 }
/*      */                 else {
/*      */                   
/* 1402 */                   iblockstate1 = getBlockState((BlockPos)blockpos$pooledmutableblockpos);
/*      */                 } 
/*      */                 
/* 1405 */                 iblockstate1.addCollisionBoxToList(this, (BlockPos)blockpos$pooledmutableblockpos, p_191504_2_, p_191504_4_, p_191504_1_, false);
/*      */                 
/* 1407 */                 if (p_191504_3_ && !p_191504_4_.isEmpty())
/*      */                 {
/* 1409 */                   boolean flag5 = true;
/* 1410 */                   return flag5;
/*      */                 }
/*      */               
/*      */               } 
/*      */             } 
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } finally {
/*      */       
/* 1420 */       blockpos$pooledmutableblockpos.release();
/*      */     } 
/*      */     
/* 1423 */     return !p_191504_4_.isEmpty();
/*      */   }
/*      */ 
/*      */   
/*      */   public List<AxisAlignedBB> getCollisionBoxes(@Nullable Entity entityIn, AxisAlignedBB aabb) {
/* 1428 */     List<AxisAlignedBB> list = Lists.newArrayList();
/* 1429 */     func_191504_a(entityIn, aabb, false, list);
/*      */     
/* 1431 */     if (entityIn != null) {
/*      */       
/* 1433 */       List<Entity> list1 = getEntitiesWithinAABBExcludingEntity(entityIn, aabb.expandXyz(0.25D));
/*      */       
/* 1435 */       for (int i = 0; i < list1.size(); i++) {
/*      */         
/* 1437 */         Entity entity = list1.get(i);
/*      */         
/* 1439 */         if (!entityIn.isRidingSameEntity(entity)) {
/*      */           
/* 1441 */           AxisAlignedBB axisalignedbb = entity.getCollisionBoundingBox();
/*      */           
/* 1443 */           if (axisalignedbb != null && axisalignedbb.intersectsWith(aabb))
/*      */           {
/* 1445 */             list.add(axisalignedbb);
/*      */           }
/*      */           
/* 1448 */           axisalignedbb = entityIn.getCollisionBox(entity);
/*      */           
/* 1450 */           if (axisalignedbb != null && axisalignedbb.intersectsWith(aabb))
/*      */           {
/* 1452 */             list.add(axisalignedbb);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 1458 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_191503_g(Entity p_191503_1_) {
/* 1463 */     double d0 = this.worldBorder.minX();
/* 1464 */     double d1 = this.worldBorder.minZ();
/* 1465 */     double d2 = this.worldBorder.maxX();
/* 1466 */     double d3 = this.worldBorder.maxZ();
/*      */     
/* 1468 */     if (p_191503_1_.isOutsideBorder()) {
/*      */       
/* 1470 */       d0++;
/* 1471 */       d1++;
/* 1472 */       d2--;
/* 1473 */       d3--;
/*      */     }
/*      */     else {
/*      */       
/* 1477 */       d0--;
/* 1478 */       d1--;
/* 1479 */       d2++;
/* 1480 */       d3++;
/*      */     } 
/*      */     
/* 1483 */     return (p_191503_1_.posX > d0 && p_191503_1_.posX < d2 && p_191503_1_.posZ > d1 && p_191503_1_.posZ < d3);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean collidesWithAnyBlock(AxisAlignedBB bbox) {
/* 1491 */     return func_191504_a(null, bbox, true, Lists.newArrayList());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int calculateSkylightSubtracted(float partialTicks) {
/* 1499 */     float f = getCelestialAngle(partialTicks);
/* 1500 */     float f1 = 1.0F - MathHelper.cos(f * 6.2831855F) * 2.0F + 0.5F;
/* 1501 */     f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
/* 1502 */     f1 = 1.0F - f1;
/* 1503 */     f1 = (float)(f1 * (1.0D - (getRainStrength(partialTicks) * 5.0F) / 16.0D));
/* 1504 */     f1 = (float)(f1 * (1.0D - (getThunderStrength(partialTicks) * 5.0F) / 16.0D));
/* 1505 */     f1 = 1.0F - f1;
/* 1506 */     return (int)(f1 * 11.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getSunBrightness(float p_72971_1_) {
/* 1514 */     float f = getCelestialAngle(p_72971_1_);
/* 1515 */     float f1 = 1.0F - MathHelper.cos(f * 6.2831855F) * 2.0F + 0.2F;
/* 1516 */     f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
/* 1517 */     f1 = 1.0F - f1;
/* 1518 */     f1 = (float)(f1 * (1.0D - (getRainStrength(p_72971_1_) * 5.0F) / 16.0D));
/* 1519 */     f1 = (float)(f1 * (1.0D - (getThunderStrength(p_72971_1_) * 5.0F) / 16.0D));
/* 1520 */     return f1 * 0.8F + 0.2F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3d getSkyColor(Entity entityIn, float partialTicks) {
/* 1528 */     float f = getCelestialAngle(partialTicks);
/* 1529 */     float f1 = MathHelper.cos(f * 6.2831855F) * 2.0F + 0.5F;
/* 1530 */     f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
/* 1531 */     int i = MathHelper.floor(entityIn.posX);
/* 1532 */     int j = MathHelper.floor(entityIn.posY);
/* 1533 */     int k = MathHelper.floor(entityIn.posZ);
/* 1534 */     BlockPos blockpos = new BlockPos(i, j, k);
/* 1535 */     Biome biome = getBiome(blockpos);
/* 1536 */     float f2 = biome.getFloatTemperature(blockpos);
/* 1537 */     int l = biome.getSkyColorByTemp(f2);
/* 1538 */     float f3 = (l >> 16 & 0xFF) / 255.0F;
/* 1539 */     float f4 = (l >> 8 & 0xFF) / 255.0F;
/* 1540 */     float f5 = (l & 0xFF) / 255.0F;
/* 1541 */     f3 *= f1;
/* 1542 */     f4 *= f1;
/* 1543 */     f5 *= f1;
/* 1544 */     float f6 = getRainStrength(partialTicks);
/*      */     
/* 1546 */     if (f6 > 0.0F) {
/*      */       
/* 1548 */       float f7 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.6F;
/* 1549 */       float f8 = 1.0F - f6 * 0.75F;
/* 1550 */       f3 = f3 * f8 + f7 * (1.0F - f8);
/* 1551 */       f4 = f4 * f8 + f7 * (1.0F - f8);
/* 1552 */       f5 = f5 * f8 + f7 * (1.0F - f8);
/*      */     } 
/*      */     
/* 1555 */     float f10 = getThunderStrength(partialTicks);
/*      */     
/* 1557 */     if (f10 > 0.0F) {
/*      */       
/* 1559 */       float f11 = (f3 * 0.3F + f4 * 0.59F + f5 * 0.11F) * 0.2F;
/* 1560 */       float f9 = 1.0F - f10 * 0.75F;
/* 1561 */       f3 = f3 * f9 + f11 * (1.0F - f9);
/* 1562 */       f4 = f4 * f9 + f11 * (1.0F - f9);
/* 1563 */       f5 = f5 * f9 + f11 * (1.0F - f9);
/*      */     } 
/*      */     
/* 1566 */     if (this.lastLightningBolt > 0) {
/*      */       
/* 1568 */       float f12 = this.lastLightningBolt - partialTicks;
/*      */       
/* 1570 */       if (f12 > 1.0F)
/*      */       {
/* 1572 */         f12 = 1.0F;
/*      */       }
/*      */       
/* 1575 */       f12 *= 0.45F;
/* 1576 */       f3 = f3 * (1.0F - f12) + 0.8F * f12;
/* 1577 */       f4 = f4 * (1.0F - f12) + 0.8F * f12;
/* 1578 */       f5 = f5 * (1.0F - f12) + 1.0F * f12;
/*      */     } 
/*      */     
/* 1581 */     return new Vec3d(f3, f4, f5);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCelestialAngle(float partialTicks) {
/* 1589 */     return this.provider.calculateCelestialAngle(this.worldInfo.getWorldTime(), partialTicks);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMoonPhase() {
/* 1594 */     return this.provider.getMoonPhase(this.worldInfo.getWorldTime());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCurrentMoonPhaseFactor() {
/* 1602 */     return WorldProvider.MOON_PHASE_FACTORS[this.provider.getMoonPhase(this.worldInfo.getWorldTime())];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getCelestialAngleRadians(float partialTicks) {
/* 1610 */     float f = getCelestialAngle(partialTicks);
/* 1611 */     return f * 6.2831855F;
/*      */   }
/*      */ 
/*      */   
/*      */   public Vec3d getCloudColour(float partialTicks) {
/* 1616 */     float f = getCelestialAngle(partialTicks);
/* 1617 */     float f1 = MathHelper.cos(f * 6.2831855F) * 2.0F + 0.5F;
/* 1618 */     f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
/* 1619 */     float f2 = 1.0F;
/* 1620 */     float f3 = 1.0F;
/* 1621 */     float f4 = 1.0F;
/* 1622 */     float f5 = getRainStrength(partialTicks);
/*      */     
/* 1624 */     if (f5 > 0.0F) {
/*      */       
/* 1626 */       float f6 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.6F;
/* 1627 */       float f7 = 1.0F - f5 * 0.95F;
/* 1628 */       f2 = f2 * f7 + f6 * (1.0F - f7);
/* 1629 */       f3 = f3 * f7 + f6 * (1.0F - f7);
/* 1630 */       f4 = f4 * f7 + f6 * (1.0F - f7);
/*      */     } 
/*      */     
/* 1633 */     f2 *= f1 * 0.9F + 0.1F;
/* 1634 */     f3 *= f1 * 0.9F + 0.1F;
/* 1635 */     f4 *= f1 * 0.85F + 0.15F;
/* 1636 */     float f9 = getThunderStrength(partialTicks);
/*      */     
/* 1638 */     if (f9 > 0.0F) {
/*      */       
/* 1640 */       float f10 = (f2 * 0.3F + f3 * 0.59F + f4 * 0.11F) * 0.2F;
/* 1641 */       float f8 = 1.0F - f9 * 0.95F;
/* 1642 */       f2 = f2 * f8 + f10 * (1.0F - f8);
/* 1643 */       f3 = f3 * f8 + f10 * (1.0F - f8);
/* 1644 */       f4 = f4 * f8 + f10 * (1.0F - f8);
/*      */     } 
/*      */     
/* 1647 */     return new Vec3d(f2, f3, f4);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Vec3d getFogColor(float partialTicks) {
/* 1655 */     float f = getCelestialAngle(partialTicks);
/* 1656 */     return this.provider.getFogColor(f, partialTicks);
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockPos getPrecipitationHeight(BlockPos pos) {
/* 1661 */     return getChunkFromBlockCoords(pos).getPrecipitationHeight(pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getTopSolidOrLiquidBlock(BlockPos pos) {
/* 1669 */     Chunk chunk = getChunkFromBlockCoords(pos);
/*      */ 
/*      */ 
/*      */     
/* 1673 */     for (BlockPos blockpos = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ()); blockpos.getY() >= 0; blockpos = blockpos1) {
/*      */       
/* 1675 */       BlockPos blockpos1 = blockpos.down();
/* 1676 */       Material material = chunk.getBlockState(blockpos1).getMaterial();
/*      */       
/* 1678 */       if (material.blocksMovement() && material != Material.LEAVES) {
/*      */         break;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1684 */     return blockpos;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getStarBrightness(float partialTicks) {
/* 1692 */     float f = getCelestialAngle(partialTicks);
/* 1693 */     float f1 = 1.0F - MathHelper.cos(f * 6.2831855F) * 2.0F + 0.25F;
/* 1694 */     f1 = MathHelper.clamp(f1, 0.0F, 1.0F);
/* 1695 */     return f1 * f1 * 0.5F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isUpdateScheduled(BlockPos pos, Block blk) {
/* 1703 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void scheduleUpdate(BlockPos pos, Block blockIn, int delay) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateBlockTick(BlockPos pos, Block blockIn, int delay, int priority) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void scheduleBlockUpdate(BlockPos pos, Block blockIn, int delay, int priority) {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntities() {
/* 1723 */     this.theProfiler.startSection("entities");
/* 1724 */     this.theProfiler.startSection("global");
/*      */     
/* 1726 */     for (int i = 0; i < this.weatherEffects.size(); i++) {
/*      */       
/* 1728 */       Entity entity = this.weatherEffects.get(i);
/*      */ 
/*      */       
/*      */       try {
/* 1732 */         entity.ticksExisted++;
/* 1733 */         entity.onUpdate();
/*      */       }
/* 1735 */       catch (Throwable throwable2) {
/*      */         
/* 1737 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable2, "Ticking entity");
/* 1738 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Entity being ticked");
/*      */         
/* 1740 */         if (entity == null) {
/*      */           
/* 1742 */           crashreportcategory.addCrashSection("Entity", "~~NULL~~");
/*      */         }
/*      */         else {
/*      */           
/* 1746 */           entity.addEntityCrashInfo(crashreportcategory);
/*      */         } 
/*      */         
/* 1749 */         throw new ReportedException(crashreport);
/*      */       } 
/*      */       
/* 1752 */       if (entity.isDead)
/*      */       {
/* 1754 */         this.weatherEffects.remove(i--);
/*      */       }
/*      */     } 
/*      */     
/* 1758 */     this.theProfiler.endStartSection("remove");
/* 1759 */     this.loadedEntityList.removeAll(this.unloadedEntityList);
/*      */     
/* 1761 */     for (int k = 0; k < this.unloadedEntityList.size(); k++) {
/*      */       
/* 1763 */       Entity entity1 = this.unloadedEntityList.get(k);
/* 1764 */       int j = entity1.chunkCoordX;
/* 1765 */       int k1 = entity1.chunkCoordZ;
/*      */       
/* 1767 */       if (entity1.addedToChunk && isChunkLoaded(j, k1, true))
/*      */       {
/* 1769 */         getChunkFromChunkCoords(j, k1).removeEntity(entity1);
/*      */       }
/*      */     } 
/*      */     
/* 1773 */     for (int l = 0; l < this.unloadedEntityList.size(); l++)
/*      */     {
/* 1775 */       onEntityRemoved(this.unloadedEntityList.get(l));
/*      */     }
/*      */     
/* 1778 */     this.unloadedEntityList.clear();
/* 1779 */     tickPlayers();
/* 1780 */     this.theProfiler.endStartSection("regular");
/*      */     
/* 1782 */     for (int i1 = 0; i1 < this.loadedEntityList.size(); i1++) {
/*      */       
/* 1784 */       Entity entity2 = this.loadedEntityList.get(i1);
/* 1785 */       Entity entity3 = entity2.getRidingEntity();
/*      */       
/* 1787 */       if (entity3 != null) {
/*      */         
/* 1789 */         if (!entity3.isDead && entity3.isPassenger(entity2)) {
/*      */           continue;
/*      */         }
/*      */ 
/*      */         
/* 1794 */         entity2.dismountRidingEntity();
/*      */       } 
/*      */       
/* 1797 */       this.theProfiler.startSection("tick");
/*      */       
/* 1799 */       if (!entity2.isDead && !(entity2 instanceof net.minecraft.entity.player.EntityPlayerMP)) {
/*      */         
/*      */         try {
/*      */           
/* 1803 */           updateEntity(entity2);
/*      */         }
/* 1805 */         catch (Throwable throwable1) {
/*      */           
/* 1807 */           CrashReport crashreport1 = CrashReport.makeCrashReport(throwable1, "Ticking entity");
/* 1808 */           CrashReportCategory crashreportcategory1 = crashreport1.makeCategory("Entity being ticked");
/* 1809 */           entity2.addEntityCrashInfo(crashreportcategory1);
/* 1810 */           throw new ReportedException(crashreport1);
/*      */         } 
/*      */       }
/*      */       
/* 1814 */       this.theProfiler.endSection();
/* 1815 */       this.theProfiler.startSection("remove");
/*      */       
/* 1817 */       if (entity2.isDead) {
/*      */         
/* 1819 */         int l1 = entity2.chunkCoordX;
/* 1820 */         int i2 = entity2.chunkCoordZ;
/*      */         
/* 1822 */         if (entity2.addedToChunk && isChunkLoaded(l1, i2, true))
/*      */         {
/* 1824 */           getChunkFromChunkCoords(l1, i2).removeEntity(entity2);
/*      */         }
/*      */         
/* 1827 */         this.loadedEntityList.remove(i1--);
/* 1828 */         onEntityRemoved(entity2);
/*      */       } 
/*      */       
/* 1831 */       this.theProfiler.endSection();
/*      */       continue;
/*      */     } 
/* 1834 */     this.theProfiler.endStartSection("blockEntities");
/*      */     
/* 1836 */     if (!this.tileEntitiesToBeRemoved.isEmpty()) {
/*      */       
/* 1838 */       this.tickableTileEntities.removeAll(this.tileEntitiesToBeRemoved);
/* 1839 */       this.loadedTileEntityList.removeAll(this.tileEntitiesToBeRemoved);
/* 1840 */       this.tileEntitiesToBeRemoved.clear();
/*      */     } 
/*      */     
/* 1843 */     this.processingLoadedTiles = true;
/* 1844 */     Iterator<TileEntity> iterator = this.tickableTileEntities.iterator();
/*      */     
/* 1846 */     while (iterator.hasNext()) {
/*      */       
/* 1848 */       TileEntity tileentity = iterator.next();
/*      */       
/* 1850 */       if (!tileentity.isInvalid() && tileentity.hasWorldObj()) {
/*      */         
/* 1852 */         BlockPos blockpos = tileentity.getPos();
/*      */         
/* 1854 */         if (isBlockLoaded(blockpos) && this.worldBorder.contains(blockpos)) {
/*      */           
/*      */           try {
/*      */             
/* 1858 */             this.theProfiler.func_194340_a(() -> String.valueOf(TileEntity.func_190559_a(paramTileEntity.getClass())));
/*      */ 
/*      */ 
/*      */             
/* 1862 */             ((ITickable)tileentity).update();
/* 1863 */             this.theProfiler.endSection();
/*      */           }
/* 1865 */           catch (Throwable throwable) {
/*      */             
/* 1867 */             CrashReport crashreport2 = CrashReport.makeCrashReport(throwable, "Ticking block entity");
/* 1868 */             CrashReportCategory crashreportcategory2 = crashreport2.makeCategory("Block entity being ticked");
/* 1869 */             tileentity.addInfoToCrashReport(crashreportcategory2);
/* 1870 */             throw new ReportedException(crashreport2);
/*      */           } 
/*      */         }
/*      */       } 
/*      */       
/* 1875 */       if (tileentity.isInvalid()) {
/*      */         
/* 1877 */         iterator.remove();
/* 1878 */         this.loadedTileEntityList.remove(tileentity);
/*      */         
/* 1880 */         if (isBlockLoaded(tileentity.getPos()))
/*      */         {
/* 1882 */           getChunkFromBlockCoords(tileentity.getPos()).removeTileEntity(tileentity.getPos());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1887 */     this.processingLoadedTiles = false;
/* 1888 */     this.theProfiler.endStartSection("pendingBlockEntities");
/*      */     
/* 1890 */     if (!this.addedTileEntityList.isEmpty()) {
/*      */       
/* 1892 */       for (int j1 = 0; j1 < this.addedTileEntityList.size(); j1++) {
/*      */         
/* 1894 */         TileEntity tileentity1 = this.addedTileEntityList.get(j1);
/*      */         
/* 1896 */         if (!tileentity1.isInvalid()) {
/*      */           
/* 1898 */           if (!this.loadedTileEntityList.contains(tileentity1))
/*      */           {
/* 1900 */             addTileEntity(tileentity1);
/*      */           }
/*      */           
/* 1903 */           if (isBlockLoaded(tileentity1.getPos())) {
/*      */             
/* 1905 */             Chunk chunk = getChunkFromBlockCoords(tileentity1.getPos());
/* 1906 */             IBlockState iblockstate = chunk.getBlockState(tileentity1.getPos());
/* 1907 */             chunk.addTileEntity(tileentity1.getPos(), tileentity1);
/* 1908 */             notifyBlockUpdate(tileentity1.getPos(), iblockstate, iblockstate, 3);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1913 */       this.addedTileEntityList.clear();
/*      */     } 
/*      */     
/* 1916 */     this.theProfiler.endSection();
/* 1917 */     this.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void tickPlayers() {}
/*      */ 
/*      */   
/*      */   public boolean addTileEntity(TileEntity tile) {
/* 1926 */     boolean flag = this.loadedTileEntityList.add(tile);
/*      */     
/* 1928 */     if (flag && tile instanceof ITickable)
/*      */     {
/* 1930 */       this.tickableTileEntities.add(tile);
/*      */     }
/*      */     
/* 1933 */     if (this.isRemote) {
/*      */       
/* 1935 */       BlockPos blockpos1 = tile.getPos();
/* 1936 */       IBlockState iblockstate1 = getBlockState(blockpos1);
/* 1937 */       notifyBlockUpdate(blockpos1, iblockstate1, iblockstate1, 2);
/*      */     } 
/*      */     
/* 1940 */     return flag;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addTileEntities(Collection<TileEntity> tileEntityCollection) {
/* 1945 */     if (this.processingLoadedTiles) {
/*      */       
/* 1947 */       this.addedTileEntityList.addAll(tileEntityCollection);
/*      */     }
/*      */     else {
/*      */       
/* 1951 */       for (TileEntity tileentity2 : tileEntityCollection)
/*      */       {
/* 1953 */         addTileEntity(tileentity2);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntity(Entity ent) {
/* 1963 */     updateEntityWithOptionalForce(ent, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateEntityWithOptionalForce(Entity entityIn, boolean forceUpdate) {
/* 1971 */     if (!(entityIn instanceof EntityPlayer)) {
/*      */       
/* 1973 */       int j2 = MathHelper.floor(entityIn.posX);
/* 1974 */       int k2 = MathHelper.floor(entityIn.posZ);
/* 1975 */       int l2 = 32;
/*      */       
/* 1977 */       if (forceUpdate && !isAreaLoaded(j2 - 32, 0, k2 - 32, j2 + 32, 0, k2 + 32, true)) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1983 */     entityIn.lastTickPosX = entityIn.posX;
/* 1984 */     entityIn.lastTickPosY = entityIn.posY;
/* 1985 */     entityIn.lastTickPosZ = entityIn.posZ;
/* 1986 */     entityIn.prevRotationYaw = entityIn.rotationYaw;
/* 1987 */     entityIn.prevRotationPitch = entityIn.rotationPitch;
/*      */     
/* 1989 */     if (forceUpdate && entityIn.addedToChunk) {
/*      */       
/* 1991 */       entityIn.ticksExisted++;
/*      */       
/* 1993 */       if (entityIn.isRiding()) {
/*      */         
/* 1995 */         entityIn.updateRidden();
/*      */       }
/*      */       else {
/*      */         
/* 1999 */         entityIn.onUpdate();
/*      */       } 
/*      */     } 
/*      */     
/* 2003 */     this.theProfiler.startSection("chunkCheck");
/*      */     
/* 2005 */     if (Double.isNaN(entityIn.posX) || Double.isInfinite(entityIn.posX))
/*      */     {
/* 2007 */       entityIn.posX = entityIn.lastTickPosX;
/*      */     }
/*      */     
/* 2010 */     if (Double.isNaN(entityIn.posY) || Double.isInfinite(entityIn.posY))
/*      */     {
/* 2012 */       entityIn.posY = entityIn.lastTickPosY;
/*      */     }
/*      */     
/* 2015 */     if (Double.isNaN(entityIn.posZ) || Double.isInfinite(entityIn.posZ))
/*      */     {
/* 2017 */       entityIn.posZ = entityIn.lastTickPosZ;
/*      */     }
/*      */     
/* 2020 */     if (Double.isNaN(entityIn.rotationPitch) || Double.isInfinite(entityIn.rotationPitch))
/*      */     {
/* 2022 */       entityIn.rotationPitch = entityIn.prevRotationPitch;
/*      */     }
/*      */     
/* 2025 */     if (Double.isNaN(entityIn.rotationYaw) || Double.isInfinite(entityIn.rotationYaw))
/*      */     {
/* 2027 */       entityIn.rotationYaw = entityIn.prevRotationYaw;
/*      */     }
/*      */     
/* 2030 */     int i3 = MathHelper.floor(entityIn.posX / 16.0D);
/* 2031 */     int j3 = MathHelper.floor(entityIn.posY / 16.0D);
/* 2032 */     int k3 = MathHelper.floor(entityIn.posZ / 16.0D);
/*      */     
/* 2034 */     if (!entityIn.addedToChunk || entityIn.chunkCoordX != i3 || entityIn.chunkCoordY != j3 || entityIn.chunkCoordZ != k3) {
/*      */       
/* 2036 */       if (entityIn.addedToChunk && isChunkLoaded(entityIn.chunkCoordX, entityIn.chunkCoordZ, true))
/*      */       {
/* 2038 */         getChunkFromChunkCoords(entityIn.chunkCoordX, entityIn.chunkCoordZ).removeEntityAtIndex(entityIn, entityIn.chunkCoordY);
/*      */       }
/*      */       
/* 2041 */       if (!entityIn.setPositionNonDirty() && !isChunkLoaded(i3, k3, true)) {
/*      */         
/* 2043 */         entityIn.addedToChunk = false;
/*      */       }
/*      */       else {
/*      */         
/* 2047 */         getChunkFromChunkCoords(i3, k3).addEntity(entityIn);
/*      */       } 
/*      */     } 
/*      */     
/* 2051 */     this.theProfiler.endSection();
/*      */     
/* 2053 */     if (forceUpdate && entityIn.addedToChunk)
/*      */     {
/* 2055 */       for (Entity entity4 : entityIn.getPassengers()) {
/*      */         
/* 2057 */         if (!entity4.isDead && entity4.getRidingEntity() == entityIn) {
/*      */           
/* 2059 */           updateEntity(entity4);
/*      */           
/*      */           continue;
/*      */         } 
/* 2063 */         entity4.dismountRidingEntity();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkNoEntityCollision(AxisAlignedBB bb) {
/* 2074 */     return checkNoEntityCollision(bb, null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkNoEntityCollision(AxisAlignedBB bb, @Nullable Entity entityIn) {
/* 2082 */     List<Entity> list = getEntitiesWithinAABBExcludingEntity(null, bb);
/*      */     
/* 2084 */     for (int j2 = 0; j2 < list.size(); j2++) {
/*      */       
/* 2086 */       Entity entity4 = list.get(j2);
/*      */       
/* 2088 */       if (!entity4.isDead && entity4.preventEntitySpawning && entity4 != entityIn && (entityIn == null || entity4.isRidingSameEntity(entityIn)))
/*      */       {
/* 2090 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 2094 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkBlockCollision(AxisAlignedBB bb) {
/* 2102 */     int j2 = MathHelper.floor(bb.minX);
/* 2103 */     int k2 = MathHelper.ceil(bb.maxX);
/* 2104 */     int l2 = MathHelper.floor(bb.minY);
/* 2105 */     int i3 = MathHelper.ceil(bb.maxY);
/* 2106 */     int j3 = MathHelper.floor(bb.minZ);
/* 2107 */     int k3 = MathHelper.ceil(bb.maxZ);
/* 2108 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/*      */     
/* 2110 */     for (int l3 = j2; l3 < k2; l3++) {
/*      */       
/* 2112 */       for (int i4 = l2; i4 < i3; i4++) {
/*      */         
/* 2114 */         for (int j4 = j3; j4 < k3; j4++) {
/*      */           
/* 2116 */           IBlockState iblockstate1 = getBlockState((BlockPos)blockpos$pooledmutableblockpos.setPos(l3, i4, j4));
/*      */           
/* 2118 */           if (iblockstate1.getMaterial() != Material.AIR) {
/*      */             
/* 2120 */             blockpos$pooledmutableblockpos.release();
/* 2121 */             return true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2127 */     blockpos$pooledmutableblockpos.release();
/* 2128 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean containsAnyLiquid(AxisAlignedBB bb) {
/* 2136 */     int j2 = MathHelper.floor(bb.minX);
/* 2137 */     int k2 = MathHelper.ceil(bb.maxX);
/* 2138 */     int l2 = MathHelper.floor(bb.minY);
/* 2139 */     int i3 = MathHelper.ceil(bb.maxY);
/* 2140 */     int j3 = MathHelper.floor(bb.minZ);
/* 2141 */     int k3 = MathHelper.ceil(bb.maxZ);
/* 2142 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/*      */     
/* 2144 */     for (int l3 = j2; l3 < k2; l3++) {
/*      */       
/* 2146 */       for (int i4 = l2; i4 < i3; i4++) {
/*      */         
/* 2148 */         for (int j4 = j3; j4 < k3; j4++) {
/*      */           
/* 2150 */           IBlockState iblockstate1 = getBlockState((BlockPos)blockpos$pooledmutableblockpos.setPos(l3, i4, j4));
/*      */           
/* 2152 */           if (iblockstate1.getMaterial().isLiquid()) {
/*      */             
/* 2154 */             blockpos$pooledmutableblockpos.release();
/* 2155 */             return true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2161 */     blockpos$pooledmutableblockpos.release();
/* 2162 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isFlammableWithin(AxisAlignedBB bb) {
/* 2167 */     int j2 = MathHelper.floor(bb.minX);
/* 2168 */     int k2 = MathHelper.ceil(bb.maxX);
/* 2169 */     int l2 = MathHelper.floor(bb.minY);
/* 2170 */     int i3 = MathHelper.ceil(bb.maxY);
/* 2171 */     int j3 = MathHelper.floor(bb.minZ);
/* 2172 */     int k3 = MathHelper.ceil(bb.maxZ);
/*      */     
/* 2174 */     if (isAreaLoaded(j2, l2, j3, k2, i3, k3, true)) {
/*      */       
/* 2176 */       BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/*      */       
/* 2178 */       for (int l3 = j2; l3 < k2; l3++) {
/*      */         
/* 2180 */         for (int i4 = l2; i4 < i3; i4++) {
/*      */           
/* 2182 */           for (int j4 = j3; j4 < k3; j4++) {
/*      */             
/* 2184 */             Block block = getBlockState((BlockPos)blockpos$pooledmutableblockpos.setPos(l3, i4, j4)).getBlock();
/*      */             
/* 2186 */             if (block == Blocks.FIRE || block == Blocks.FLOWING_LAVA || block == Blocks.LAVA) {
/*      */               
/* 2188 */               blockpos$pooledmutableblockpos.release();
/* 2189 */               return true;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2195 */       blockpos$pooledmutableblockpos.release();
/*      */     } 
/*      */     
/* 2198 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean handleMaterialAcceleration(AxisAlignedBB bb, Material materialIn, Entity entityIn) {
/* 2206 */     int j2 = MathHelper.floor(bb.minX);
/* 2207 */     int k2 = MathHelper.ceil(bb.maxX);
/* 2208 */     int l2 = MathHelper.floor(bb.minY);
/* 2209 */     int i3 = MathHelper.ceil(bb.maxY);
/* 2210 */     int j3 = MathHelper.floor(bb.minZ);
/* 2211 */     int k3 = MathHelper.ceil(bb.maxZ);
/*      */     
/* 2213 */     if (!isAreaLoaded(j2, l2, j3, k2, i3, k3, true))
/*      */     {
/* 2215 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2219 */     boolean flag = false;
/* 2220 */     Vec3d vec3d = Vec3d.ZERO;
/* 2221 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/*      */     
/* 2223 */     for (int l3 = j2; l3 < k2; l3++) {
/*      */       
/* 2225 */       for (int i4 = l2; i4 < i3; i4++) {
/*      */         
/* 2227 */         for (int j4 = j3; j4 < k3; j4++) {
/*      */           
/* 2229 */           blockpos$pooledmutableblockpos.setPos(l3, i4, j4);
/* 2230 */           IBlockState iblockstate1 = getBlockState((BlockPos)blockpos$pooledmutableblockpos);
/* 2231 */           Block block = iblockstate1.getBlock();
/*      */           
/* 2233 */           if (iblockstate1.getMaterial() == materialIn) {
/*      */             
/* 2235 */             double d0 = ((i4 + 1) - BlockLiquid.getLiquidHeightPercent(((Integer)iblockstate1.getValue((IProperty)BlockLiquid.LEVEL)).intValue()));
/*      */             
/* 2237 */             if (i3 >= d0) {
/*      */               
/* 2239 */               flag = true;
/* 2240 */               vec3d = block.modifyAcceleration(this, (BlockPos)blockpos$pooledmutableblockpos, entityIn, vec3d);
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2247 */     blockpos$pooledmutableblockpos.release();
/*      */     
/* 2249 */     if (vec3d.lengthVector() > 0.0D && entityIn.isPushedByWater()) {
/*      */       
/* 2251 */       vec3d = vec3d.normalize();
/* 2252 */       double d1 = 0.014D;
/* 2253 */       entityIn.motionX += vec3d.xCoord * 0.014D;
/* 2254 */       entityIn.motionY += vec3d.yCoord * 0.014D;
/* 2255 */       entityIn.motionZ += vec3d.zCoord * 0.014D;
/*      */     } 
/*      */     
/* 2258 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isMaterialInBB(AxisAlignedBB bb, Material materialIn) {
/* 2267 */     int j2 = MathHelper.floor(bb.minX);
/* 2268 */     int k2 = MathHelper.ceil(bb.maxX);
/* 2269 */     int l2 = MathHelper.floor(bb.minY);
/* 2270 */     int i3 = MathHelper.ceil(bb.maxY);
/* 2271 */     int j3 = MathHelper.floor(bb.minZ);
/* 2272 */     int k3 = MathHelper.ceil(bb.maxZ);
/* 2273 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/*      */     
/* 2275 */     for (int l3 = j2; l3 < k2; l3++) {
/*      */       
/* 2277 */       for (int i4 = l2; i4 < i3; i4++) {
/*      */         
/* 2279 */         for (int j4 = j3; j4 < k3; j4++) {
/*      */           
/* 2281 */           if (getBlockState((BlockPos)blockpos$pooledmutableblockpos.setPos(l3, i4, j4)).getMaterial() == materialIn) {
/*      */             
/* 2283 */             blockpos$pooledmutableblockpos.release();
/* 2284 */             return true;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2290 */     blockpos$pooledmutableblockpos.release();
/* 2291 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Explosion createExplosion(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isSmoking) {
/* 2299 */     return newExplosion(entityIn, x, y, z, strength, false, isSmoking);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Explosion newExplosion(@Nullable Entity entityIn, double x, double y, double z, float strength, boolean isFlaming, boolean isSmoking) {
/* 2307 */     Explosion explosion = new Explosion(this, entityIn, x, y, z, strength, isFlaming, isSmoking);
/* 2308 */     explosion.doExplosionA();
/* 2309 */     explosion.doExplosionB(true);
/* 2310 */     return explosion;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getBlockDensity(Vec3d vec, AxisAlignedBB bb) {
/* 2318 */     double d0 = 1.0D / ((bb.maxX - bb.minX) * 2.0D + 1.0D);
/* 2319 */     double d1 = 1.0D / ((bb.maxY - bb.minY) * 2.0D + 1.0D);
/* 2320 */     double d2 = 1.0D / ((bb.maxZ - bb.minZ) * 2.0D + 1.0D);
/* 2321 */     double d3 = (1.0D - Math.floor(1.0D / d0) * d0) / 2.0D;
/* 2322 */     double d4 = (1.0D - Math.floor(1.0D / d2) * d2) / 2.0D;
/*      */     
/* 2324 */     if (d0 >= 0.0D && d1 >= 0.0D && d2 >= 0.0D) {
/*      */       
/* 2326 */       int j2 = 0;
/* 2327 */       int k2 = 0;
/*      */       
/* 2329 */       for (float f = 0.0F; f <= 1.0F; f = (float)(f + d0)) {
/*      */         
/* 2331 */         for (float f1 = 0.0F; f1 <= 1.0F; f1 = (float)(f1 + d1)) {
/*      */           
/* 2333 */           for (float f2 = 0.0F; f2 <= 1.0F; f2 = (float)(f2 + d2)) {
/*      */             
/* 2335 */             double d5 = bb.minX + (bb.maxX - bb.minX) * f;
/* 2336 */             double d6 = bb.minY + (bb.maxY - bb.minY) * f1;
/* 2337 */             double d7 = bb.minZ + (bb.maxZ - bb.minZ) * f2;
/*      */             
/* 2339 */             if (rayTraceBlocks(new Vec3d(d5 + d3, d6, d7 + d4), vec) == null)
/*      */             {
/* 2341 */               j2++;
/*      */             }
/*      */             
/* 2344 */             k2++;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2349 */       return j2 / k2;
/*      */     } 
/*      */ 
/*      */     
/* 2353 */     return 0.0F;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean extinguishFire(@Nullable EntityPlayer player, BlockPos pos, EnumFacing side) {
/* 2362 */     pos = pos.offset(side);
/*      */     
/* 2364 */     if (getBlockState(pos).getBlock() == Blocks.FIRE) {
/*      */       
/* 2366 */       playEvent(player, 1009, pos, 0);
/* 2367 */       setBlockToAir(pos);
/* 2368 */       return true;
/*      */     } 
/*      */ 
/*      */     
/* 2372 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDebugLoadedEntities() {
/* 2381 */     return "All: " + this.loadedEntityList.size();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getProviderName() {
/* 2389 */     return this.chunkProvider.makeString();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public TileEntity getTileEntity(BlockPos pos) {
/* 2395 */     if (isOutsideBuildHeight(pos))
/*      */     {
/* 2397 */       return null;
/*      */     }
/*      */ 
/*      */     
/* 2401 */     TileEntity tileentity2 = null;
/*      */     
/* 2403 */     if (this.processingLoadedTiles)
/*      */     {
/* 2405 */       tileentity2 = getPendingTileEntityAt(pos);
/*      */     }
/*      */     
/* 2408 */     if (tileentity2 == null)
/*      */     {
/* 2410 */       tileentity2 = getChunkFromBlockCoords(pos).getTileEntity(pos, Chunk.EnumCreateEntityType.IMMEDIATE);
/*      */     }
/*      */     
/* 2413 */     if (tileentity2 == null)
/*      */     {
/* 2415 */       tileentity2 = getPendingTileEntityAt(pos);
/*      */     }
/*      */     
/* 2418 */     return tileentity2;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private TileEntity getPendingTileEntityAt(BlockPos p_189508_1_) {
/* 2425 */     for (int j2 = 0; j2 < this.addedTileEntityList.size(); j2++) {
/*      */       
/* 2427 */       TileEntity tileentity2 = this.addedTileEntityList.get(j2);
/*      */       
/* 2429 */       if (!tileentity2.isInvalid() && tileentity2.getPos().equals(p_189508_1_))
/*      */       {
/* 2431 */         return tileentity2;
/*      */       }
/*      */     } 
/*      */     
/* 2435 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTileEntity(BlockPos pos, @Nullable TileEntity tileEntityIn) {
/* 2440 */     if (!isOutsideBuildHeight(pos))
/*      */     {
/* 2442 */       if (tileEntityIn != null && !tileEntityIn.isInvalid())
/*      */       {
/* 2444 */         if (this.processingLoadedTiles) {
/*      */           
/* 2446 */           tileEntityIn.setPos(pos);
/* 2447 */           Iterator<TileEntity> iterator1 = this.addedTileEntityList.iterator();
/*      */           
/* 2449 */           while (iterator1.hasNext()) {
/*      */             
/* 2451 */             TileEntity tileentity2 = iterator1.next();
/*      */             
/* 2453 */             if (tileentity2.getPos().equals(pos)) {
/*      */               
/* 2455 */               tileentity2.invalidate();
/* 2456 */               iterator1.remove();
/*      */             } 
/*      */           } 
/*      */           
/* 2460 */           this.addedTileEntityList.add(tileEntityIn);
/*      */         }
/*      */         else {
/*      */           
/* 2464 */           getChunkFromBlockCoords(pos).addTileEntity(pos, tileEntityIn);
/* 2465 */           addTileEntity(tileEntityIn);
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeTileEntity(BlockPos pos) {
/* 2473 */     TileEntity tileentity2 = getTileEntity(pos);
/*      */     
/* 2475 */     if (tileentity2 != null && this.processingLoadedTiles) {
/*      */       
/* 2477 */       tileentity2.invalidate();
/* 2478 */       this.addedTileEntityList.remove(tileentity2);
/*      */     }
/*      */     else {
/*      */       
/* 2482 */       if (tileentity2 != null) {
/*      */         
/* 2484 */         this.addedTileEntityList.remove(tileentity2);
/* 2485 */         this.loadedTileEntityList.remove(tileentity2);
/* 2486 */         this.tickableTileEntities.remove(tileentity2);
/*      */       } 
/*      */       
/* 2489 */       getChunkFromBlockCoords(pos).removeTileEntity(pos);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void markTileEntityForRemoval(TileEntity tileEntityIn) {
/* 2498 */     this.tileEntitiesToBeRemoved.add(tileEntityIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockFullCube(BlockPos pos) {
/* 2503 */     AxisAlignedBB axisalignedbb = getBlockState(pos).getCollisionBoundingBox(this, pos);
/* 2504 */     return (axisalignedbb != Block.NULL_AABB && axisalignedbb.getAverageEdgeLength() >= 1.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockNormalCube(BlockPos pos, boolean _default) {
/* 2512 */     if (isOutsideBuildHeight(pos))
/*      */     {
/* 2514 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2518 */     Chunk chunk1 = this.chunkProvider.getLoadedChunk(pos.getX() >> 4, pos.getZ() >> 4);
/*      */     
/* 2520 */     if (chunk1 != null && !chunk1.isEmpty()) {
/*      */       
/* 2522 */       IBlockState iblockstate1 = getBlockState(pos);
/* 2523 */       return (iblockstate1.getMaterial().isOpaque() && iblockstate1.isFullCube());
/*      */     } 
/*      */ 
/*      */     
/* 2527 */     return _default;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void calculateInitialSkylight() {
/* 2537 */     int j2 = calculateSkylightSubtracted(1.0F);
/*      */     
/* 2539 */     if (j2 != this.skylightSubtracted)
/*      */     {
/* 2541 */       this.skylightSubtracted = j2;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowedSpawnTypes(boolean hostile, boolean peaceful) {
/* 2550 */     this.spawnHostileMobs = hostile;
/* 2551 */     this.spawnPeacefulMobs = peaceful;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void tick() {
/* 2559 */     updateWeather();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void calculateInitialWeather() {
/* 2567 */     if (this.worldInfo.isRaining()) {
/*      */       
/* 2569 */       this.rainingStrength = 1.0F;
/*      */       
/* 2571 */       if (this.worldInfo.isThundering())
/*      */       {
/* 2573 */         this.thunderingStrength = 1.0F;
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateWeather() {
/* 2583 */     if (this.provider.func_191066_m())
/*      */     {
/* 2585 */       if (!this.isRemote) {
/*      */         
/* 2587 */         boolean flag = getGameRules().getBoolean("doWeatherCycle");
/*      */         
/* 2589 */         if (flag) {
/*      */           
/* 2591 */           int j2 = this.worldInfo.getCleanWeatherTime();
/*      */           
/* 2593 */           if (j2 > 0) {
/*      */             
/* 2595 */             j2--;
/* 2596 */             this.worldInfo.setCleanWeatherTime(j2);
/* 2597 */             this.worldInfo.setThunderTime(this.worldInfo.isThundering() ? 1 : 2);
/* 2598 */             this.worldInfo.setRainTime(this.worldInfo.isRaining() ? 1 : 2);
/*      */           } 
/*      */           
/* 2601 */           int k2 = this.worldInfo.getThunderTime();
/*      */           
/* 2603 */           if (k2 <= 0) {
/*      */             
/* 2605 */             if (this.worldInfo.isThundering())
/*      */             {
/* 2607 */               this.worldInfo.setThunderTime(this.rand.nextInt(12000) + 3600);
/*      */             }
/*      */             else
/*      */             {
/* 2611 */               this.worldInfo.setThunderTime(this.rand.nextInt(168000) + 12000);
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 2616 */             k2--;
/* 2617 */             this.worldInfo.setThunderTime(k2);
/*      */             
/* 2619 */             if (k2 <= 0)
/*      */             {
/* 2621 */               this.worldInfo.setThundering(!this.worldInfo.isThundering());
/*      */             }
/*      */           } 
/*      */           
/* 2625 */           int l2 = this.worldInfo.getRainTime();
/*      */           
/* 2627 */           if (l2 <= 0) {
/*      */             
/* 2629 */             if (this.worldInfo.isRaining())
/*      */             {
/* 2631 */               this.worldInfo.setRainTime(this.rand.nextInt(12000) + 12000);
/*      */             }
/*      */             else
/*      */             {
/* 2635 */               this.worldInfo.setRainTime(this.rand.nextInt(168000) + 12000);
/*      */             }
/*      */           
/*      */           } else {
/*      */             
/* 2640 */             l2--;
/* 2641 */             this.worldInfo.setRainTime(l2);
/*      */             
/* 2643 */             if (l2 <= 0)
/*      */             {
/* 2645 */               this.worldInfo.setRaining(!this.worldInfo.isRaining());
/*      */             }
/*      */           } 
/*      */         } 
/*      */         
/* 2650 */         this.prevThunderingStrength = this.thunderingStrength;
/*      */         
/* 2652 */         if (this.worldInfo.isThundering()) {
/*      */           
/* 2654 */           this.thunderingStrength = (float)(this.thunderingStrength + 0.01D);
/*      */         }
/*      */         else {
/*      */           
/* 2658 */           this.thunderingStrength = (float)(this.thunderingStrength - 0.01D);
/*      */         } 
/*      */         
/* 2661 */         this.thunderingStrength = MathHelper.clamp(this.thunderingStrength, 0.0F, 1.0F);
/* 2662 */         this.prevRainingStrength = this.rainingStrength;
/*      */         
/* 2664 */         if (this.worldInfo.isRaining()) {
/*      */           
/* 2666 */           this.rainingStrength = (float)(this.rainingStrength + 0.01D);
/*      */         }
/*      */         else {
/*      */           
/* 2670 */           this.rainingStrength = (float)(this.rainingStrength - 0.01D);
/*      */         } 
/*      */         
/* 2673 */         this.rainingStrength = MathHelper.clamp(this.rainingStrength, 0.0F, 1.0F);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   protected void playMoodSoundAndCheckLight(int p_147467_1_, int p_147467_2_, Chunk chunkIn) {
/* 2680 */     chunkIn.enqueueRelightChecks();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void updateBlocks() {}
/*      */ 
/*      */   
/*      */   public void immediateBlockTick(BlockPos pos, IBlockState state, Random random) {
/* 2689 */     this.scheduledUpdatesAreImmediate = true;
/* 2690 */     state.getBlock().updateTick(this, pos, state, random);
/* 2691 */     this.scheduledUpdatesAreImmediate = false;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBlockFreezeWater(BlockPos pos) {
/* 2696 */     return canBlockFreeze(pos, false);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canBlockFreezeNoWater(BlockPos pos) {
/* 2701 */     return canBlockFreeze(pos, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canBlockFreeze(BlockPos pos, boolean noWaterAdj) {
/* 2709 */     Biome biome = getBiome(pos);
/* 2710 */     float f = biome.getFloatTemperature(pos);
/*      */     
/* 2712 */     if (f >= 0.15F)
/*      */     {
/* 2714 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2718 */     if (pos.getY() >= 0 && pos.getY() < 256 && getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
/*      */       
/* 2720 */       IBlockState iblockstate1 = getBlockState(pos);
/* 2721 */       Block block = iblockstate1.getBlock();
/*      */       
/* 2723 */       if ((block == Blocks.WATER || block == Blocks.FLOWING_WATER) && ((Integer)iblockstate1.getValue((IProperty)BlockLiquid.LEVEL)).intValue() == 0) {
/*      */         
/* 2725 */         if (!noWaterAdj)
/*      */         {
/* 2727 */           return true;
/*      */         }
/*      */         
/* 2730 */         boolean flag = (isWater(pos.west()) && isWater(pos.east()) && isWater(pos.north()) && isWater(pos.south()));
/*      */         
/* 2732 */         if (!flag)
/*      */         {
/* 2734 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 2739 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean isWater(BlockPos pos) {
/* 2745 */     return (getBlockState(pos).getMaterial() == Material.WATER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSnowAt(BlockPos pos, boolean checkLight) {
/* 2753 */     Biome biome = getBiome(pos);
/* 2754 */     float f = biome.getFloatTemperature(pos);
/*      */     
/* 2756 */     if (f >= 0.15F)
/*      */     {
/* 2758 */       return false;
/*      */     }
/* 2760 */     if (!checkLight)
/*      */     {
/* 2762 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 2766 */     if (pos.getY() >= 0 && pos.getY() < 256 && getLightFor(EnumSkyBlock.BLOCK, pos) < 10) {
/*      */       
/* 2768 */       IBlockState iblockstate1 = getBlockState(pos);
/*      */       
/* 2770 */       if (iblockstate1.getMaterial() == Material.AIR && Blocks.SNOW_LAYER.canPlaceBlockAt(this, pos))
/*      */       {
/* 2772 */         return true;
/*      */       }
/*      */     } 
/*      */     
/* 2776 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkLight(BlockPos pos) {
/* 2782 */     boolean flag = false;
/*      */     
/* 2784 */     if (this.provider.func_191066_m())
/*      */     {
/* 2786 */       flag |= checkLightFor(EnumSkyBlock.SKY, pos);
/*      */     }
/*      */     
/* 2789 */     flag |= checkLightFor(EnumSkyBlock.BLOCK, pos);
/* 2790 */     return flag;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int getRawLight(BlockPos pos, EnumSkyBlock lightType) {
/* 2798 */     if (lightType == EnumSkyBlock.SKY && canSeeSky(pos))
/*      */     {
/* 2800 */       return 15;
/*      */     }
/*      */ 
/*      */     
/* 2804 */     IBlockState iblockstate1 = getBlockState(pos);
/* 2805 */     int j2 = (lightType == EnumSkyBlock.SKY) ? 0 : iblockstate1.getLightValue();
/* 2806 */     int k2 = iblockstate1.getLightOpacity();
/*      */     
/* 2808 */     if (k2 >= 15 && iblockstate1.getLightValue() > 0)
/*      */     {
/* 2810 */       k2 = 1;
/*      */     }
/*      */     
/* 2813 */     if (k2 < 1)
/*      */     {
/* 2815 */       k2 = 1;
/*      */     }
/*      */     
/* 2818 */     if (k2 >= 15)
/*      */     {
/* 2820 */       return 0;
/*      */     }
/* 2822 */     if (j2 >= 14)
/*      */     {
/* 2824 */       return j2;
/*      */     }
/*      */ 
/*      */     
/* 2828 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(); try {
/*      */       byte b;
/*      */       int i;
/*      */       EnumFacing[] arrayOfEnumFacing;
/* 2832 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*      */         
/* 2834 */         blockpos$pooledmutableblockpos.setPos((Vec3i)pos).move(enumfacing);
/* 2835 */         int l2 = getLightFor(lightType, (BlockPos)blockpos$pooledmutableblockpos) - k2;
/*      */         
/* 2837 */         if (l2 > j2)
/*      */         {
/* 2839 */           j2 = l2;
/*      */         }
/*      */         
/* 2842 */         if (j2 >= 14) {
/*      */           
/* 2844 */           int i3 = j2;
/* 2845 */           return i3;
/*      */         } 
/*      */         b++; }
/*      */       
/* 2849 */       return j2;
/*      */     }
/*      */     finally {
/*      */       
/* 2853 */       blockpos$pooledmutableblockpos.release();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean checkLightFor(EnumSkyBlock lightType, BlockPos pos) {
/* 2861 */     if (!isAreaLoaded(pos, 17, false))
/*      */     {
/* 2863 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 2867 */     int j2 = 0;
/* 2868 */     int k2 = 0;
/* 2869 */     this.theProfiler.startSection("getBrightness");
/* 2870 */     int l2 = getLightFor(lightType, pos);
/* 2871 */     int i3 = getRawLight(pos, lightType);
/* 2872 */     int j3 = pos.getX();
/* 2873 */     int k3 = pos.getY();
/* 2874 */     int l3 = pos.getZ();
/*      */     
/* 2876 */     if (i3 > l2) {
/*      */       
/* 2878 */       this.lightUpdateBlockList[k2++] = 133152;
/*      */     }
/* 2880 */     else if (i3 < l2) {
/*      */       
/* 2882 */       this.lightUpdateBlockList[k2++] = 0x20820 | l2 << 18;
/*      */       
/* 2884 */       while (j2 < k2) {
/*      */         
/* 2886 */         int i4 = this.lightUpdateBlockList[j2++];
/* 2887 */         int j4 = (i4 & 0x3F) - 32 + j3;
/* 2888 */         int k4 = (i4 >> 6 & 0x3F) - 32 + k3;
/* 2889 */         int l4 = (i4 >> 12 & 0x3F) - 32 + l3;
/* 2890 */         int i5 = i4 >> 18 & 0xF;
/* 2891 */         BlockPos blockpos1 = new BlockPos(j4, k4, l4);
/* 2892 */         int j5 = getLightFor(lightType, blockpos1);
/*      */         
/* 2894 */         if (j5 == i5) {
/*      */           
/* 2896 */           setLightFor(lightType, blockpos1, 0);
/*      */           
/* 2898 */           if (i5 > 0) {
/*      */             
/* 2900 */             int k5 = MathHelper.abs(j4 - j3);
/* 2901 */             int l5 = MathHelper.abs(k4 - k3);
/* 2902 */             int i6 = MathHelper.abs(l4 - l3);
/*      */             
/* 2904 */             if (k5 + l5 + i6 < 17) {
/*      */               
/* 2906 */               BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain(); byte b; int i;
/*      */               EnumFacing[] arrayOfEnumFacing;
/* 2908 */               for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*      */                 
/* 2910 */                 int j6 = j4 + enumfacing.getFrontOffsetX();
/* 2911 */                 int k6 = k4 + enumfacing.getFrontOffsetY();
/* 2912 */                 int l6 = l4 + enumfacing.getFrontOffsetZ();
/* 2913 */                 blockpos$pooledmutableblockpos.setPos(j6, k6, l6);
/* 2914 */                 int i7 = Math.max(1, getBlockState((BlockPos)blockpos$pooledmutableblockpos).getLightOpacity());
/* 2915 */                 j5 = getLightFor(lightType, (BlockPos)blockpos$pooledmutableblockpos);
/*      */                 
/* 2917 */                 if (j5 == i5 - i7 && k2 < this.lightUpdateBlockList.length)
/*      */                 {
/* 2919 */                   this.lightUpdateBlockList[k2++] = j6 - j3 + 32 | k6 - k3 + 32 << 6 | l6 - l3 + 32 << 12 | i5 - i7 << 18;
/*      */                 }
/*      */                 b++; }
/*      */               
/* 2923 */               blockpos$pooledmutableblockpos.release();
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 2929 */       j2 = 0;
/*      */     } 
/*      */     
/* 2932 */     this.theProfiler.endSection();
/* 2933 */     this.theProfiler.startSection("checkedPosition < toCheckCount");
/*      */     
/* 2935 */     while (j2 < k2) {
/*      */       
/* 2937 */       int j7 = this.lightUpdateBlockList[j2++];
/* 2938 */       int k7 = (j7 & 0x3F) - 32 + j3;
/* 2939 */       int l7 = (j7 >> 6 & 0x3F) - 32 + k3;
/* 2940 */       int i8 = (j7 >> 12 & 0x3F) - 32 + l3;
/* 2941 */       BlockPos blockpos2 = new BlockPos(k7, l7, i8);
/* 2942 */       int j8 = getLightFor(lightType, blockpos2);
/* 2943 */       int k8 = getRawLight(blockpos2, lightType);
/*      */       
/* 2945 */       if (k8 != j8) {
/*      */         
/* 2947 */         setLightFor(lightType, blockpos2, k8);
/*      */         
/* 2949 */         if (k8 > j8) {
/*      */           
/* 2951 */           int l8 = Math.abs(k7 - j3);
/* 2952 */           int i9 = Math.abs(l7 - k3);
/* 2953 */           int j9 = Math.abs(i8 - l3);
/* 2954 */           boolean flag = (k2 < this.lightUpdateBlockList.length - 6);
/*      */           
/* 2956 */           if (l8 + i9 + j9 < 17 && flag) {
/*      */             
/* 2958 */             if (getLightFor(lightType, blockpos2.west()) < k8)
/*      */             {
/* 2960 */               this.lightUpdateBlockList[k2++] = k7 - 1 - j3 + 32 + (l7 - k3 + 32 << 6) + (i8 - l3 + 32 << 12);
/*      */             }
/*      */             
/* 2963 */             if (getLightFor(lightType, blockpos2.east()) < k8)
/*      */             {
/* 2965 */               this.lightUpdateBlockList[k2++] = k7 + 1 - j3 + 32 + (l7 - k3 + 32 << 6) + (i8 - l3 + 32 << 12);
/*      */             }
/*      */             
/* 2968 */             if (getLightFor(lightType, blockpos2.down()) < k8)
/*      */             {
/* 2970 */               this.lightUpdateBlockList[k2++] = k7 - j3 + 32 + (l7 - 1 - k3 + 32 << 6) + (i8 - l3 + 32 << 12);
/*      */             }
/*      */             
/* 2973 */             if (getLightFor(lightType, blockpos2.up()) < k8)
/*      */             {
/* 2975 */               this.lightUpdateBlockList[k2++] = k7 - j3 + 32 + (l7 + 1 - k3 + 32 << 6) + (i8 - l3 + 32 << 12);
/*      */             }
/*      */             
/* 2978 */             if (getLightFor(lightType, blockpos2.north()) < k8)
/*      */             {
/* 2980 */               this.lightUpdateBlockList[k2++] = k7 - j3 + 32 + (l7 - k3 + 32 << 6) + (i8 - 1 - l3 + 32 << 12);
/*      */             }
/*      */             
/* 2983 */             if (getLightFor(lightType, blockpos2.south()) < k8)
/*      */             {
/* 2985 */               this.lightUpdateBlockList[k2++] = k7 - j3 + 32 + (l7 - k3 + 32 << 6) + (i8 + 1 - l3 + 32 << 12);
/*      */             }
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 2992 */     this.theProfiler.endSection();
/* 2993 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean tickUpdates(boolean p_72955_1_) {
/* 3002 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public List<NextTickListEntry> getPendingBlockUpdates(Chunk chunkIn, boolean p_72920_2_) {
/* 3008 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public List<NextTickListEntry> getPendingBlockUpdates(StructureBoundingBox structureBB, boolean p_175712_2_) {
/* 3014 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Entity> getEntitiesWithinAABBExcludingEntity(@Nullable Entity entityIn, AxisAlignedBB bb) {
/* 3019 */     return getEntitiesInAABBexcluding(entityIn, bb, EntitySelectors.NOT_SPECTATING);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Entity> getEntitiesInAABBexcluding(@Nullable Entity entityIn, AxisAlignedBB boundingBox, @Nullable Predicate<? super Entity> predicate) {
/* 3024 */     List<Entity> list = Lists.newArrayList();
/* 3025 */     int j2 = MathHelper.floor((boundingBox.minX - 2.0D) / 16.0D);
/* 3026 */     int k2 = MathHelper.floor((boundingBox.maxX + 2.0D) / 16.0D);
/* 3027 */     int l2 = MathHelper.floor((boundingBox.minZ - 2.0D) / 16.0D);
/* 3028 */     int i3 = MathHelper.floor((boundingBox.maxZ + 2.0D) / 16.0D);
/*      */     
/* 3030 */     for (int j3 = j2; j3 <= k2; j3++) {
/*      */       
/* 3032 */       for (int k3 = l2; k3 <= i3; k3++) {
/*      */         
/* 3034 */         if (isChunkLoaded(j3, k3, true))
/*      */         {
/* 3036 */           getChunkFromChunkCoords(j3, k3).getEntitiesWithinAABBForEntity(entityIn, boundingBox, list, predicate);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3041 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Entity> List<T> getEntities(Class<? extends T> entityType, Predicate<? super T> filter) {
/* 3046 */     List<T> list = Lists.newArrayList();
/*      */     
/* 3048 */     for (Entity entity4 : this.loadedEntityList) {
/*      */       
/* 3050 */       if (entityType.isAssignableFrom(entity4.getClass()) && filter.apply(entity4))
/*      */       {
/* 3052 */         list.add((T)entity4);
/*      */       }
/*      */     } 
/*      */     
/* 3056 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Entity> List<T> getPlayers(Class<? extends T> playerType, Predicate<? super T> filter) {
/* 3061 */     List<T> list = Lists.newArrayList();
/*      */     
/* 3063 */     for (Entity entity4 : this.playerEntities) {
/*      */       
/* 3065 */       if (playerType.isAssignableFrom(entity4.getClass()) && filter.apply(entity4))
/*      */       {
/* 3067 */         list.add((T)entity4);
/*      */       }
/*      */     } 
/*      */     
/* 3071 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> classEntity, AxisAlignedBB bb) {
/* 3076 */     return getEntitiesWithinAABB(classEntity, bb, EntitySelectors.NOT_SPECTATING);
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Entity> List<T> getEntitiesWithinAABB(Class<? extends T> clazz, AxisAlignedBB aabb, @Nullable Predicate<? super T> filter) {
/* 3081 */     int j2 = MathHelper.floor((aabb.minX - 2.0D) / 16.0D);
/* 3082 */     int k2 = MathHelper.ceil((aabb.maxX + 2.0D) / 16.0D);
/* 3083 */     int l2 = MathHelper.floor((aabb.minZ - 2.0D) / 16.0D);
/* 3084 */     int i3 = MathHelper.ceil((aabb.maxZ + 2.0D) / 16.0D);
/* 3085 */     List<T> list = Lists.newArrayList();
/*      */     
/* 3087 */     for (int j3 = j2; j3 < k2; j3++) {
/*      */       
/* 3089 */       for (int k3 = l2; k3 < i3; k3++) {
/*      */         
/* 3091 */         if (isChunkLoaded(j3, k3, true))
/*      */         {
/* 3093 */           getChunkFromChunkCoords(j3, k3).getEntitiesOfTypeWithinAAAB(clazz, aabb, list, filter);
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3098 */     return list;
/*      */   }
/*      */   
/*      */   @Nullable
/*      */   public <T extends Entity> T findNearestEntityWithinAABB(Class<? extends T> entityType, AxisAlignedBB aabb, T closestTo) {
/*      */     Entity entity;
/* 3104 */     List<T> list = getEntitiesWithinAABB(entityType, aabb);
/* 3105 */     T t = null;
/* 3106 */     double d0 = Double.MAX_VALUE;
/*      */     
/* 3108 */     for (int j2 = 0; j2 < list.size(); j2++) {
/*      */       
/* 3110 */       Entity entity1 = (Entity)list.get(j2);
/*      */       
/* 3112 */       if (entity1 != closestTo && EntitySelectors.NOT_SPECTATING.apply(entity1)) {
/*      */         
/* 3114 */         double d1 = closestTo.getDistanceSqToEntity(entity1);
/*      */         
/* 3116 */         if (d1 <= d0) {
/*      */           
/* 3118 */           entity = entity1;
/* 3119 */           d0 = d1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3124 */     return (T)entity;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public Entity getEntityByID(int id) {
/* 3134 */     return (Entity)this.entitiesById.lookup(id);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<Entity> getLoadedEntityList() {
/* 3139 */     return this.loadedEntityList;
/*      */   }
/*      */ 
/*      */   
/*      */   public void markChunkDirty(BlockPos pos, TileEntity unusedTileEntity) {
/* 3144 */     if (isBlockLoaded(pos))
/*      */     {
/* 3146 */       getChunkFromBlockCoords(pos).setChunkModified();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int countEntities(Class<?> entityType) {
/* 3155 */     int j2 = 0;
/*      */     
/* 3157 */     for (Entity entity4 : this.loadedEntityList) {
/*      */       
/* 3159 */       if ((!(entity4 instanceof EntityLiving) || !((EntityLiving)entity4).isNoDespawnRequired()) && entityType.isAssignableFrom(entity4.getClass()))
/*      */       {
/* 3161 */         j2++;
/*      */       }
/*      */     } 
/*      */     
/* 3165 */     return j2;
/*      */   }
/*      */ 
/*      */   
/*      */   public void loadEntities(Collection<Entity> entityCollection) {
/* 3170 */     this.loadedEntityList.addAll(entityCollection);
/*      */     
/* 3172 */     for (Entity entity4 : entityCollection)
/*      */     {
/* 3174 */       onEntityAdded(entity4);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void unloadEntities(Collection<Entity> entityCollection) {
/* 3180 */     this.unloadedEntityList.addAll(entityCollection);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean func_190527_a(Block p_190527_1_, BlockPos p_190527_2_, boolean p_190527_3_, EnumFacing p_190527_4_, @Nullable Entity p_190527_5_) {
/* 3185 */     IBlockState iblockstate1 = getBlockState(p_190527_2_);
/* 3186 */     AxisAlignedBB axisalignedbb = p_190527_3_ ? null : p_190527_1_.getDefaultState().getCollisionBoundingBox(this, p_190527_2_);
/*      */     
/* 3188 */     if (axisalignedbb != Block.NULL_AABB && !checkNoEntityCollision(axisalignedbb.offset(p_190527_2_), p_190527_5_))
/*      */     {
/* 3190 */       return false;
/*      */     }
/* 3192 */     if (iblockstate1.getMaterial() == Material.CIRCUITS && p_190527_1_ == Blocks.ANVIL)
/*      */     {
/* 3194 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 3198 */     return (iblockstate1.getMaterial().isReplaceable() && p_190527_1_.canPlaceBlockOnSide(this, p_190527_2_, p_190527_4_));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSeaLevel() {
/* 3204 */     return this.seaLevel;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSeaLevel(int seaLevelIn) {
/* 3212 */     this.seaLevel = seaLevelIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getStrongPower(BlockPos pos, EnumFacing direction) {
/* 3217 */     return getBlockState(pos).getStrongPower(this, pos, direction);
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldType getWorldType() {
/* 3222 */     return this.worldInfo.getTerrainType();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getStrongPower(BlockPos pos) {
/* 3230 */     int j2 = 0;
/* 3231 */     j2 = Math.max(j2, getStrongPower(pos.down(), EnumFacing.DOWN));
/*      */     
/* 3233 */     if (j2 >= 15)
/*      */     {
/* 3235 */       return j2;
/*      */     }
/*      */ 
/*      */     
/* 3239 */     j2 = Math.max(j2, getStrongPower(pos.up(), EnumFacing.UP));
/*      */     
/* 3241 */     if (j2 >= 15)
/*      */     {
/* 3243 */       return j2;
/*      */     }
/*      */ 
/*      */     
/* 3247 */     j2 = Math.max(j2, getStrongPower(pos.north(), EnumFacing.NORTH));
/*      */     
/* 3249 */     if (j2 >= 15)
/*      */     {
/* 3251 */       return j2;
/*      */     }
/*      */ 
/*      */     
/* 3255 */     j2 = Math.max(j2, getStrongPower(pos.south(), EnumFacing.SOUTH));
/*      */     
/* 3257 */     if (j2 >= 15)
/*      */     {
/* 3259 */       return j2;
/*      */     }
/*      */ 
/*      */     
/* 3263 */     j2 = Math.max(j2, getStrongPower(pos.west(), EnumFacing.WEST));
/*      */     
/* 3265 */     if (j2 >= 15)
/*      */     {
/* 3267 */       return j2;
/*      */     }
/*      */ 
/*      */     
/* 3271 */     j2 = Math.max(j2, getStrongPower(pos.east(), EnumFacing.EAST));
/* 3272 */     return (j2 >= 15) ? j2 : j2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSidePowered(BlockPos pos, EnumFacing side) {
/* 3282 */     return (getRedstonePower(pos, side) > 0);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getRedstonePower(BlockPos pos, EnumFacing facing) {
/* 3287 */     IBlockState iblockstate1 = getBlockState(pos);
/* 3288 */     return iblockstate1.isNormalCube() ? getStrongPower(pos) : iblockstate1.getWeakPower(this, pos, facing);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockPowered(BlockPos pos) {
/* 3293 */     if (getRedstonePower(pos.down(), EnumFacing.DOWN) > 0)
/*      */     {
/* 3295 */       return true;
/*      */     }
/* 3297 */     if (getRedstonePower(pos.up(), EnumFacing.UP) > 0)
/*      */     {
/* 3299 */       return true;
/*      */     }
/* 3301 */     if (getRedstonePower(pos.north(), EnumFacing.NORTH) > 0)
/*      */     {
/* 3303 */       return true;
/*      */     }
/* 3305 */     if (getRedstonePower(pos.south(), EnumFacing.SOUTH) > 0)
/*      */     {
/* 3307 */       return true;
/*      */     }
/* 3309 */     if (getRedstonePower(pos.west(), EnumFacing.WEST) > 0)
/*      */     {
/* 3311 */       return true;
/*      */     }
/*      */ 
/*      */     
/* 3315 */     return (getRedstonePower(pos.east(), EnumFacing.EAST) > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int isBlockIndirectlyGettingPowered(BlockPos pos) {
/* 3325 */     int j2 = 0; byte b; int i;
/*      */     EnumFacing[] arrayOfEnumFacing;
/* 3327 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*      */       
/* 3329 */       int k2 = getRedstonePower(pos.offset(enumfacing), enumfacing);
/*      */       
/* 3331 */       if (k2 >= 15)
/*      */       {
/* 3333 */         return 15;
/*      */       }
/*      */       
/* 3336 */       if (k2 > j2)
/*      */       {
/* 3338 */         j2 = k2;
/*      */       }
/*      */       b++; }
/*      */     
/* 3342 */     return j2;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityPlayer getClosestPlayerToEntity(Entity entityIn, double distance) {
/* 3352 */     return getClosestPlayer(entityIn.posX, entityIn.posY, entityIn.posZ, distance, false);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityPlayer getNearestPlayerNotCreative(Entity entityIn, double distance) {
/* 3358 */     return getClosestPlayer(entityIn.posX, entityIn.posY, entityIn.posZ, distance, true);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityPlayer getClosestPlayer(double posX, double posY, double posZ, double distance, boolean spectator) {
/* 3364 */     Predicate<Entity> predicate = spectator ? EntitySelectors.CAN_AI_TARGET : EntitySelectors.NOT_SPECTATING;
/* 3365 */     return func_190525_a(posX, posY, posZ, distance, predicate);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityPlayer func_190525_a(double p_190525_1_, double p_190525_3_, double p_190525_5_, double p_190525_7_, Predicate<Entity> p_190525_9_) {
/* 3371 */     double d0 = -1.0D;
/* 3372 */     EntityPlayer entityplayer = null;
/*      */     
/* 3374 */     for (int j2 = 0; j2 < this.playerEntities.size(); j2++) {
/*      */       
/* 3376 */       EntityPlayer entityplayer1 = this.playerEntities.get(j2);
/*      */       
/* 3378 */       if (p_190525_9_.apply(entityplayer1)) {
/*      */         
/* 3380 */         double d1 = entityplayer1.getDistanceSq(p_190525_1_, p_190525_3_, p_190525_5_);
/*      */         
/* 3382 */         if ((p_190525_7_ < 0.0D || d1 < p_190525_7_ * p_190525_7_) && (d0 == -1.0D || d1 < d0)) {
/*      */           
/* 3384 */           d0 = d1;
/* 3385 */           entityplayer = entityplayer1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3390 */     return entityplayer;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isAnyPlayerWithinRangeAt(double x, double y, double z, double range) {
/* 3395 */     for (int j2 = 0; j2 < this.playerEntities.size(); j2++) {
/*      */       
/* 3397 */       EntityPlayer entityplayer = this.playerEntities.get(j2);
/*      */       
/* 3399 */       if (EntitySelectors.NOT_SPECTATING.apply(entityplayer)) {
/*      */         
/* 3401 */         double d0 = entityplayer.getDistanceSq(x, y, z);
/*      */         
/* 3403 */         if (range < 0.0D || d0 < range * range)
/*      */         {
/* 3405 */           return true;
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 3410 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityPlayer getNearestAttackablePlayer(Entity entityIn, double maxXZDistance, double maxYDistance) {
/* 3416 */     return getNearestAttackablePlayer(entityIn.posX, entityIn.posY, entityIn.posZ, maxXZDistance, maxYDistance, null, null);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityPlayer getNearestAttackablePlayer(BlockPos pos, double maxXZDistance, double maxYDistance) {
/* 3422 */     return getNearestAttackablePlayer((pos.getX() + 0.5F), (pos.getY() + 0.5F), (pos.getZ() + 0.5F), maxXZDistance, maxYDistance, null, null);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityPlayer getNearestAttackablePlayer(double posX, double posY, double posZ, double maxXZDistance, double maxYDistance, @Nullable Function<EntityPlayer, Double> playerToDouble, @Nullable Predicate<EntityPlayer> p_184150_12_) {
/* 3428 */     double d0 = -1.0D;
/* 3429 */     EntityPlayer entityplayer = null;
/*      */     
/* 3431 */     for (int j2 = 0; j2 < this.playerEntities.size(); j2++) {
/*      */       
/* 3433 */       EntityPlayer entityplayer1 = this.playerEntities.get(j2);
/*      */       
/* 3435 */       if (!entityplayer1.capabilities.disableDamage && entityplayer1.isEntityAlive() && !entityplayer1.isSpectator() && (p_184150_12_ == null || p_184150_12_.apply(entityplayer1))) {
/*      */         
/* 3437 */         double d1 = entityplayer1.getDistanceSq(posX, entityplayer1.posY, posZ);
/* 3438 */         double d2 = maxXZDistance;
/*      */         
/* 3440 */         if (entityplayer1.isSneaking())
/*      */         {
/* 3442 */           d2 = maxXZDistance * 0.800000011920929D;
/*      */         }
/*      */         
/* 3445 */         if (entityplayer1.isInvisible()) {
/*      */           
/* 3447 */           float f = entityplayer1.getArmorVisibility();
/*      */           
/* 3449 */           if (f < 0.1F)
/*      */           {
/* 3451 */             f = 0.1F;
/*      */           }
/*      */           
/* 3454 */           d2 *= (0.7F * f);
/*      */         } 
/*      */         
/* 3457 */         if (playerToDouble != null)
/*      */         {
/* 3459 */           d2 *= ((Double)MoreObjects.firstNonNull(playerToDouble.apply(entityplayer1), Double.valueOf(1.0D))).doubleValue();
/*      */         }
/*      */         
/* 3462 */         if ((maxYDistance < 0.0D || Math.abs(entityplayer1.posY - posY) < maxYDistance * maxYDistance) && (maxXZDistance < 0.0D || d1 < d2 * d2) && (d0 == -1.0D || d1 < d0)) {
/*      */           
/* 3464 */           d0 = d1;
/* 3465 */           entityplayer = entityplayer1;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/* 3470 */     return entityplayer;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityPlayer getPlayerEntityByName(String name) {
/* 3480 */     for (int j2 = 0; j2 < this.playerEntities.size(); j2++) {
/*      */       
/* 3482 */       EntityPlayer entityplayer = this.playerEntities.get(j2);
/*      */       
/* 3484 */       if (name.equals(entityplayer.getName()))
/*      */       {
/* 3486 */         return entityplayer;
/*      */       }
/*      */     } 
/*      */     
/* 3490 */     return null;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public EntityPlayer getPlayerEntityByUUID(UUID uuid) {
/* 3496 */     for (int j2 = 0; j2 < this.playerEntities.size(); j2++) {
/*      */       
/* 3498 */       EntityPlayer entityplayer = this.playerEntities.get(j2);
/*      */       
/* 3500 */       if (uuid.equals(entityplayer.getUniqueID()))
/*      */       {
/* 3502 */         return entityplayer;
/*      */       }
/*      */     } 
/*      */     
/* 3506 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void sendQuittingDisconnectingPacket() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void checkSessionLock() throws MinecraftException {
/* 3521 */     this.saveHandler.checkSessionLock();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTotalWorldTime(long worldTime) {
/* 3526 */     this.worldInfo.setWorldTotalTime(worldTime);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getSeed() {
/* 3534 */     return this.worldInfo.getSeed();
/*      */   }
/*      */ 
/*      */   
/*      */   public long getTotalWorldTime() {
/* 3539 */     return this.worldInfo.getWorldTotalTime();
/*      */   }
/*      */ 
/*      */   
/*      */   public long getWorldTime() {
/* 3544 */     return this.worldInfo.getWorldTime();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setWorldTime(long time) {
/* 3552 */     this.worldInfo.setWorldTime(time);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public BlockPos getSpawnPoint() {
/* 3560 */     BlockPos blockpos1 = new BlockPos(this.worldInfo.getSpawnX(), this.worldInfo.getSpawnY(), this.worldInfo.getSpawnZ());
/*      */     
/* 3562 */     if (!getWorldBorder().contains(blockpos1))
/*      */     {
/* 3564 */       blockpos1 = getHeight(new BlockPos(getWorldBorder().getCenterX(), 0.0D, getWorldBorder().getCenterZ()));
/*      */     }
/*      */     
/* 3567 */     return blockpos1;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSpawnPoint(BlockPos pos) {
/* 3572 */     this.worldInfo.setSpawn(pos);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void joinEntityInSurroundings(Entity entityIn) {
/* 3580 */     int j2 = MathHelper.floor(entityIn.posX / 16.0D);
/* 3581 */     int k2 = MathHelper.floor(entityIn.posZ / 16.0D);
/* 3582 */     int l2 = 2;
/*      */     
/* 3584 */     for (int i3 = -2; i3 <= 2; i3++) {
/*      */       
/* 3586 */       for (int j3 = -2; j3 <= 2; j3++)
/*      */       {
/* 3588 */         getChunkFromChunkCoords(j2 + i3, k2 + j3);
/*      */       }
/*      */     } 
/*      */     
/* 3592 */     if (!this.loadedEntityList.contains(entityIn))
/*      */     {
/* 3594 */       this.loadedEntityList.add(entityIn);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlockModifiable(EntityPlayer player, BlockPos pos) {
/* 3600 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEntityState(Entity entityIn, byte state) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public IChunkProvider getChunkProvider() {
/* 3615 */     return this.chunkProvider;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addBlockEvent(BlockPos pos, Block blockIn, int eventID, int eventParam) {
/* 3620 */     getBlockState(pos).onBlockEventReceived(this, pos, eventID, eventParam);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ISaveHandler getSaveHandler() {
/* 3628 */     return this.saveHandler;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public WorldInfo getWorldInfo() {
/* 3636 */     return this.worldInfo;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public GameRules getGameRules() {
/* 3644 */     return this.worldInfo.getGameRulesInstance();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void updateAllPlayersSleepingFlag() {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getThunderStrength(float delta) {
/* 3656 */     return (this.prevThunderingStrength + (this.thunderingStrength - this.prevThunderingStrength) * delta) * getRainStrength(delta);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThunderStrength(float strength) {
/* 3664 */     this.prevThunderingStrength = strength;
/* 3665 */     this.thunderingStrength = strength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public float getRainStrength(float delta) {
/* 3673 */     return this.prevRainingStrength + (this.rainingStrength - this.prevRainingStrength) * delta;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRainStrength(float strength) {
/* 3681 */     this.prevRainingStrength = strength;
/* 3682 */     this.rainingStrength = strength;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isThundering() {
/* 3690 */     return (getThunderStrength(1.0F) > 0.9D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRaining() {
/* 3698 */     return (getRainStrength(1.0F) > 0.2D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRainingAt(BlockPos strikePosition) {
/* 3706 */     if (!isRaining())
/*      */     {
/* 3708 */       return false;
/*      */     }
/* 3710 */     if (!canSeeSky(strikePosition))
/*      */     {
/* 3712 */       return false;
/*      */     }
/* 3714 */     if (getPrecipitationHeight(strikePosition).getY() > strikePosition.getY())
/*      */     {
/* 3716 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 3720 */     Biome biome = getBiome(strikePosition);
/*      */     
/* 3722 */     if (biome.getEnableSnow())
/*      */     {
/* 3724 */       return false;
/*      */     }
/*      */ 
/*      */     
/* 3728 */     return canSnowAt(strikePosition, false) ? false : biome.canRain();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isBlockinHighHumidity(BlockPos pos) {
/* 3735 */     Biome biome = getBiome(pos);
/* 3736 */     return biome.isHighHumidity();
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public MapStorage getMapStorage() {
/* 3742 */     return this.mapStorage;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemData(String dataID, WorldSavedData worldSavedDataIn) {
/* 3751 */     this.mapStorage.setData(dataID, worldSavedDataIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public WorldSavedData loadItemData(Class<? extends WorldSavedData> clazz, String dataID) {
/* 3762 */     return this.mapStorage.getOrLoadData(clazz, dataID);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getUniqueDataId(String key) {
/* 3771 */     return this.mapStorage.getUniqueDataId(key);
/*      */   }
/*      */ 
/*      */   
/*      */   public void playBroadcastSound(int id, BlockPos pos, int data) {
/* 3776 */     for (int j2 = 0; j2 < this.eventListeners.size(); j2++)
/*      */     {
/* 3778 */       ((IWorldEventListener)this.eventListeners.get(j2)).broadcastSound(id, pos, data);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void playEvent(int type, BlockPos pos, int data) {
/* 3784 */     playEvent(null, type, pos, data);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void playEvent(@Nullable EntityPlayer player, int type, BlockPos pos, int data) {
/*      */     try {
/* 3791 */       for (int j2 = 0; j2 < this.eventListeners.size(); j2++)
/*      */       {
/* 3793 */         ((IWorldEventListener)this.eventListeners.get(j2)).playEvent(player, type, pos, data);
/*      */       }
/*      */     }
/* 3796 */     catch (Throwable throwable3) {
/*      */       
/* 3798 */       CrashReport crashreport3 = CrashReport.makeCrashReport(throwable3, "Playing level event");
/* 3799 */       CrashReportCategory crashreportcategory3 = crashreport3.makeCategory("Level event being played");
/* 3800 */       crashreportcategory3.addCrashSection("Block coordinates", CrashReportCategory.getCoordinateInfo(pos));
/* 3801 */       crashreportcategory3.addCrashSection("Event source", player);
/* 3802 */       crashreportcategory3.addCrashSection("Event type", Integer.valueOf(type));
/* 3803 */       crashreportcategory3.addCrashSection("Event data", Integer.valueOf(data));
/* 3804 */       throw new ReportedException(crashreport3);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeight() {
/* 3813 */     return 256;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getActualHeight() {
/* 3821 */     return this.provider.getHasNoSky() ? 128 : 256;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Random setRandomSeed(int p_72843_1_, int p_72843_2_, int p_72843_3_) {
/* 3829 */     long j2 = p_72843_1_ * 341873128712L + p_72843_2_ * 132897987541L + getWorldInfo().getSeed() + p_72843_3_;
/* 3830 */     this.rand.setSeed(j2);
/* 3831 */     return this.rand;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public double getHorizon() {
/* 3839 */     return (this.worldInfo.getTerrainType() == WorldType.FLAT) ? 0.0D : 63.0D;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CrashReportCategory addWorldInfoToCrashReport(CrashReport report) {
/* 3847 */     CrashReportCategory crashreportcategory3 = report.makeCategoryDepth("Affected level", 1);
/* 3848 */     crashreportcategory3.addCrashSection("Level name", (this.worldInfo == null) ? "????" : this.worldInfo.getWorldName());
/* 3849 */     crashreportcategory3.setDetail("All players", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 3853 */             return String.valueOf(World.this.playerEntities.size()) + " total; " + World.this.playerEntities;
/*      */           }
/*      */         });
/* 3856 */     crashreportcategory3.setDetail("Chunk stats", new ICrashReportDetail<String>()
/*      */         {
/*      */           public String call()
/*      */           {
/* 3860 */             return World.this.chunkProvider.makeString();
/*      */           }
/*      */         });
/*      */ 
/*      */     
/*      */     try {
/* 3866 */       this.worldInfo.addToCrashReport(crashreportcategory3);
/*      */     }
/* 3868 */     catch (Throwable throwable3) {
/*      */       
/* 3870 */       crashreportcategory3.addCrashSectionThrowable("Level Data Unobtainable", throwable3);
/*      */     } 
/*      */     
/* 3873 */     return crashreportcategory3;
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) {
/* 3878 */     for (int j2 = 0; j2 < this.eventListeners.size(); j2++) {
/*      */       
/* 3880 */       IWorldEventListener iworldeventlistener = this.eventListeners.get(j2);
/* 3881 */       iworldeventlistener.sendBlockBreakProgress(breakerId, pos, progress);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Calendar getCurrentDate() {
/* 3890 */     if (getTotalWorldTime() % 600L == 0L)
/*      */     {
/* 3892 */       this.theCalendar.setTimeInMillis(MinecraftServer.getCurrentTimeMillis());
/*      */     }
/*      */     
/* 3895 */     return this.theCalendar;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void makeFireworks(double x, double y, double z, double motionX, double motionY, double motionZ, @Nullable NBTTagCompound compund) {}
/*      */ 
/*      */   
/*      */   public Scoreboard getScoreboard() {
/* 3904 */     return this.worldScoreboard;
/*      */   }
/*      */ 
/*      */   
/*      */   public void updateComparatorOutputLevel(BlockPos pos, Block blockIn) {
/* 3909 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*      */       
/* 3911 */       BlockPos blockpos1 = pos.offset(enumfacing);
/*      */       
/* 3913 */       if (isBlockLoaded(blockpos1)) {
/*      */         
/* 3915 */         IBlockState iblockstate1 = getBlockState(blockpos1);
/*      */         
/* 3917 */         if (Blocks.UNPOWERED_COMPARATOR.isSameDiode(iblockstate1)) {
/*      */           
/* 3919 */           iblockstate1.neighborChanged(this, blockpos1, blockIn, pos); continue;
/*      */         } 
/* 3921 */         if (iblockstate1.isNormalCube()) {
/*      */           
/* 3923 */           blockpos1 = blockpos1.offset(enumfacing);
/* 3924 */           iblockstate1 = getBlockState(blockpos1);
/*      */           
/* 3926 */           if (Blocks.UNPOWERED_COMPARATOR.isSameDiode(iblockstate1))
/*      */           {
/* 3928 */             iblockstate1.neighborChanged(this, blockpos1, blockIn, pos);
/*      */           }
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public DifficultyInstance getDifficultyForLocation(BlockPos pos) {
/* 3937 */     long j2 = 0L;
/* 3938 */     float f = 0.0F;
/*      */     
/* 3940 */     if (isBlockLoaded(pos)) {
/*      */       
/* 3942 */       f = getCurrentMoonPhaseFactor();
/* 3943 */       j2 = getChunkFromBlockCoords(pos).getInhabitedTime();
/*      */     } 
/*      */     
/* 3946 */     return new DifficultyInstance(getDifficulty(), getWorldTime(), j2, f);
/*      */   }
/*      */ 
/*      */   
/*      */   public EnumDifficulty getDifficulty() {
/* 3951 */     return getWorldInfo().getDifficulty();
/*      */   }
/*      */ 
/*      */   
/*      */   public int getSkylightSubtracted() {
/* 3956 */     return this.skylightSubtracted;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSkylightSubtracted(int newSkylightSubtracted) {
/* 3961 */     this.skylightSubtracted = newSkylightSubtracted;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLastLightningBolt() {
/* 3966 */     return this.lastLightningBolt;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLastLightningBolt(int lastLightningBoltIn) {
/* 3971 */     this.lastLightningBolt = lastLightningBoltIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public VillageCollection getVillageCollection() {
/* 3976 */     return this.villageCollectionObj;
/*      */   }
/*      */ 
/*      */   
/*      */   public WorldBorder getWorldBorder() {
/* 3981 */     return this.worldBorder;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isSpawnChunk(int x, int z) {
/* 3989 */     BlockPos blockpos1 = getSpawnPoint();
/* 3990 */     int j2 = x * 16 + 8 - blockpos1.getX();
/* 3991 */     int k2 = z * 16 + 8 - blockpos1.getZ();
/* 3992 */     int l2 = 128;
/* 3993 */     return (j2 >= -128 && j2 <= 128 && k2 >= -128 && k2 <= 128);
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPacketToServer(Packet<?> packetIn) {
/* 3998 */     throw new UnsupportedOperationException("Can't send packets to server unless you're on the client.");
/*      */   }
/*      */ 
/*      */   
/*      */   public LootTableManager getLootTableManager() {
/* 4003 */     return this.lootTable;
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public BlockPos func_190528_a(String p_190528_1_, BlockPos p_190528_2_, boolean p_190528_3_) {
/* 4009 */     return null;
/*      */   }
/*      */   
/*      */   protected abstract IChunkProvider createChunkProvider();
/*      */   
/*      */   protected abstract boolean isChunkLoaded(int paramInt1, int paramInt2, boolean paramBoolean);
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\World.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */