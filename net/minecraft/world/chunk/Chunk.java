/*      */ package net.minecraft.world.chunk;
/*      */ 
/*      */ import com.google.common.base.Predicate;
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.collect.Queues;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.concurrent.ConcurrentLinkedQueue;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.block.ITileEntityProvider;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.block.state.IBlockState;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.crash.ICrashReportDetail;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.init.Biomes;
/*      */ import net.minecraft.init.Blocks;
/*      */ import net.minecraft.network.PacketBuffer;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.util.ClassInheritanceMultiMap;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.util.math.AxisAlignedBB;
/*      */ import net.minecraft.util.math.BlockPos;
/*      */ import net.minecraft.util.math.ChunkPos;
/*      */ import net.minecraft.util.math.MathHelper;
/*      */ import net.minecraft.world.EnumSkyBlock;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldType;
/*      */ import net.minecraft.world.biome.Biome;
/*      */ import net.minecraft.world.biome.BiomeProvider;
/*      */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*      */ import net.minecraft.world.gen.ChunkGeneratorDebug;
/*      */ import net.minecraft.world.gen.IChunkGenerator;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class Chunk {
/*   44 */   private static final Logger LOGGER = LogManager.getLogger();
/*   45 */   public static final ExtendedBlockStorage NULL_BLOCK_STORAGE = null;
/*      */ 
/*      */   
/*      */   private final ExtendedBlockStorage[] storageArrays;
/*      */ 
/*      */   
/*      */   private final byte[] blockBiomeArray;
/*      */ 
/*      */   
/*      */   private final int[] precipitationHeightMap;
/*      */ 
/*      */   
/*      */   private final boolean[] updateSkylightColumns;
/*      */ 
/*      */   
/*      */   private boolean isChunkLoaded;
/*      */ 
/*      */   
/*      */   private final World worldObj;
/*      */ 
/*      */   
/*      */   private final int[] heightMap;
/*      */ 
/*      */   
/*      */   public final int xPosition;
/*      */ 
/*      */   
/*      */   public final int zPosition;
/*      */ 
/*      */   
/*      */   private boolean isGapLightingUpdated;
/*      */ 
/*      */   
/*      */   private final Map<BlockPos, TileEntity> chunkTileEntityMap;
/*      */ 
/*      */   
/*      */   private final ClassInheritanceMultiMap<Entity>[] entityLists;
/*      */ 
/*      */   
/*      */   private boolean isTerrainPopulated;
/*      */ 
/*      */   
/*      */   private boolean isLightPopulated;
/*      */ 
/*      */   
/*      */   private boolean chunkTicked;
/*      */ 
/*      */   
/*      */   private boolean isModified;
/*      */ 
/*      */   
/*      */   private boolean hasEntities;
/*      */ 
/*      */   
/*      */   private long lastSaveTime;
/*      */ 
/*      */   
/*      */   private int heightMapMinimum;
/*      */ 
/*      */   
/*      */   private long inhabitedTime;
/*      */   
/*      */   private int queuedLightChecks;
/*      */   
/*      */   private final ConcurrentLinkedQueue<BlockPos> tileEntityPosQueue;
/*      */   
/*      */   public boolean unloaded;
/*      */ 
/*      */   
/*      */   public Chunk(World worldIn, int x, int z) {
/*  115 */     this.storageArrays = new ExtendedBlockStorage[16];
/*  116 */     this.blockBiomeArray = new byte[256];
/*  117 */     this.precipitationHeightMap = new int[256];
/*  118 */     this.updateSkylightColumns = new boolean[256];
/*  119 */     this.chunkTileEntityMap = Maps.newHashMap();
/*  120 */     this.queuedLightChecks = 4096;
/*  121 */     this.tileEntityPosQueue = Queues.newConcurrentLinkedQueue();
/*  122 */     this.entityLists = (ClassInheritanceMultiMap<Entity>[])new ClassInheritanceMultiMap[16];
/*  123 */     this.worldObj = worldIn;
/*  124 */     this.xPosition = x;
/*  125 */     this.zPosition = z;
/*  126 */     this.heightMap = new int[256];
/*      */     
/*  128 */     for (int i = 0; i < this.entityLists.length; i++)
/*      */     {
/*  130 */       this.entityLists[i] = new ClassInheritanceMultiMap(Entity.class);
/*      */     }
/*      */     
/*  133 */     Arrays.fill(this.precipitationHeightMap, -999);
/*  134 */     Arrays.fill(this.blockBiomeArray, (byte)-1);
/*      */   }
/*      */ 
/*      */   
/*      */   public Chunk(World worldIn, ChunkPrimer primer, int x, int z) {
/*  139 */     this(worldIn, x, z);
/*  140 */     int i = 256;
/*  141 */     boolean flag = worldIn.provider.func_191066_m();
/*      */     
/*  143 */     for (int j = 0; j < 16; j++) {
/*      */       
/*  145 */       for (int k = 0; k < 16; k++) {
/*      */         
/*  147 */         for (int l = 0; l < 256; l++) {
/*      */           
/*  149 */           IBlockState iblockstate = primer.getBlockState(j, l, k);
/*      */           
/*  151 */           if (iblockstate.getMaterial() != Material.AIR) {
/*      */             
/*  153 */             int i1 = l >> 4;
/*      */             
/*  155 */             if (this.storageArrays[i1] == NULL_BLOCK_STORAGE)
/*      */             {
/*  157 */               this.storageArrays[i1] = new ExtendedBlockStorage(i1 << 4, flag);
/*      */             }
/*      */             
/*  160 */             this.storageArrays[i1].set(j, l & 0xF, k, iblockstate);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAtLocation(int x, int z) {
/*  172 */     return (x == this.xPosition && z == this.zPosition);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getHeight(BlockPos pos) {
/*  177 */     return getHeightValue(pos.getX() & 0xF, pos.getZ() & 0xF);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getHeightValue(int x, int z) {
/*  185 */     return this.heightMap[z << 4 | x];
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private ExtendedBlockStorage getLastExtendedBlockStorage() {
/*  191 */     for (int i = this.storageArrays.length - 1; i >= 0; i--) {
/*      */       
/*  193 */       if (this.storageArrays[i] != NULL_BLOCK_STORAGE)
/*      */       {
/*  195 */         return this.storageArrays[i];
/*      */       }
/*      */     } 
/*      */     
/*  199 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getTopFilledSegment() {
/*  207 */     ExtendedBlockStorage extendedblockstorage = getLastExtendedBlockStorage();
/*  208 */     return (extendedblockstorage == null) ? 0 : extendedblockstorage.getYLocation();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ExtendedBlockStorage[] getBlockStorageArray() {
/*  216 */     return this.storageArrays;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void generateHeightMap() {
/*  224 */     int i = getTopFilledSegment();
/*  225 */     this.heightMapMinimum = Integer.MAX_VALUE;
/*      */     
/*  227 */     for (int j = 0; j < 16; j++) {
/*      */       
/*  229 */       for (int k = 0; k < 16; k++) {
/*      */         
/*  231 */         this.precipitationHeightMap[j + (k << 4)] = -999;
/*      */         
/*  233 */         for (int l = i + 16; l > 0; l--) {
/*      */           
/*  235 */           IBlockState iblockstate = getBlockState(j, l - 1, k);
/*      */           
/*  237 */           if (iblockstate.getLightOpacity() != 0) {
/*      */             
/*  239 */             this.heightMap[k << 4 | j] = l;
/*      */             
/*  241 */             if (l < this.heightMapMinimum)
/*      */             {
/*  243 */               this.heightMapMinimum = l;
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  252 */     this.isModified = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void generateSkylightMap() {
/*  260 */     int i = getTopFilledSegment();
/*  261 */     this.heightMapMinimum = Integer.MAX_VALUE;
/*      */     
/*  263 */     for (int j = 0; j < 16; j++) {
/*      */       
/*  265 */       for (int k = 0; k < 16; k++) {
/*      */         
/*  267 */         this.precipitationHeightMap[j + (k << 4)] = -999;
/*      */         
/*  269 */         for (int l = i + 16; l > 0; l--) {
/*      */           
/*  271 */           if (getBlockLightOpacity(j, l - 1, k) != 0) {
/*      */             
/*  273 */             this.heightMap[k << 4 | j] = l;
/*      */             
/*  275 */             if (l < this.heightMapMinimum)
/*      */             {
/*  277 */               this.heightMapMinimum = l;
/*      */             }
/*      */             
/*      */             break;
/*      */           } 
/*      */         } 
/*      */         
/*  284 */         if (this.worldObj.provider.func_191066_m()) {
/*      */           
/*  286 */           int k1 = 15;
/*  287 */           int i1 = i + 16 - 1;
/*      */ 
/*      */           
/*      */           do {
/*  291 */             int j1 = getBlockLightOpacity(j, i1, k);
/*      */             
/*  293 */             if (j1 == 0 && k1 != 15)
/*      */             {
/*  295 */               j1 = 1;
/*      */             }
/*      */             
/*  298 */             k1 -= j1;
/*      */             
/*  300 */             if (k1 <= 0)
/*      */               continue; 
/*  302 */             ExtendedBlockStorage extendedblockstorage = this.storageArrays[i1 >> 4];
/*      */             
/*  304 */             if (extendedblockstorage == NULL_BLOCK_STORAGE)
/*      */               continue; 
/*  306 */             extendedblockstorage.setExtSkylightValue(j, i1 & 0xF, k, k1);
/*  307 */             this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + j, i1, (this.zPosition << 4) + k));
/*      */ 
/*      */ 
/*      */             
/*  311 */             --i1;
/*      */           }
/*  313 */           while (i1 > 0 && k1 > 0);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  322 */     this.isModified = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void propagateSkylightOcclusion(int x, int z) {
/*  330 */     this.updateSkylightColumns[x + z * 16] = true;
/*  331 */     this.isGapLightingUpdated = true;
/*      */   }
/*      */ 
/*      */   
/*      */   private void recheckGaps(boolean p_150803_1_) {
/*  336 */     this.worldObj.theProfiler.startSection("recheckGaps");
/*      */     
/*  338 */     if (this.worldObj.isAreaLoaded(new BlockPos(this.xPosition * 16 + 8, 0, this.zPosition * 16 + 8), 16)) {
/*      */       
/*  340 */       for (int i = 0; i < 16; i++) {
/*      */         
/*  342 */         for (int j = 0; j < 16; j++) {
/*      */           
/*  344 */           if (this.updateSkylightColumns[i + j * 16]) {
/*      */             
/*  346 */             this.updateSkylightColumns[i + j * 16] = false;
/*  347 */             int k = getHeightValue(i, j);
/*  348 */             int l = this.xPosition * 16 + i;
/*  349 */             int i1 = this.zPosition * 16 + j;
/*  350 */             int j1 = Integer.MAX_VALUE;
/*      */             
/*  352 */             for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
/*      */             {
/*  354 */               j1 = Math.min(j1, this.worldObj.getChunksLowestHorizon(l + enumfacing.getFrontOffsetX(), i1 + enumfacing.getFrontOffsetZ()));
/*      */             }
/*      */             
/*  357 */             checkSkylightNeighborHeight(l, i1, j1);
/*      */             
/*  359 */             for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
/*      */             {
/*  361 */               checkSkylightNeighborHeight(l + enumfacing1.getFrontOffsetX(), i1 + enumfacing1.getFrontOffsetZ(), k);
/*      */             }
/*      */             
/*  364 */             if (p_150803_1_) {
/*      */               
/*  366 */               this.worldObj.theProfiler.endSection();
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       } 
/*  373 */       this.isGapLightingUpdated = false;
/*      */     } 
/*      */     
/*  376 */     this.worldObj.theProfiler.endSection();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void checkSkylightNeighborHeight(int x, int z, int maxValue) {
/*  384 */     int i = this.worldObj.getHeight(new BlockPos(x, 0, z)).getY();
/*      */     
/*  386 */     if (i > maxValue) {
/*      */       
/*  388 */       updateSkylightNeighborHeight(x, z, maxValue, i + 1);
/*      */     }
/*  390 */     else if (i < maxValue) {
/*      */       
/*  392 */       updateSkylightNeighborHeight(x, z, i, maxValue + 1);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void updateSkylightNeighborHeight(int x, int z, int startY, int endY) {
/*  398 */     if (endY > startY && this.worldObj.isAreaLoaded(new BlockPos(x, 0, z), 16)) {
/*      */       
/*  400 */       for (int i = startY; i < endY; i++)
/*      */       {
/*  402 */         this.worldObj.checkLightFor(EnumSkyBlock.SKY, new BlockPos(x, i, z));
/*      */       }
/*      */       
/*  405 */       this.isModified = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void relightBlock(int x, int y, int z) {
/*  414 */     int i = this.heightMap[z << 4 | x] & 0xFF;
/*  415 */     int j = i;
/*      */     
/*  417 */     if (y > i)
/*      */     {
/*  419 */       j = y;
/*      */     }
/*      */     
/*  422 */     while (j > 0 && getBlockLightOpacity(x, j - 1, z) == 0)
/*      */     {
/*  424 */       j--;
/*      */     }
/*      */     
/*  427 */     if (j != i) {
/*      */       
/*  429 */       this.worldObj.markBlocksDirtyVertical(x + this.xPosition * 16, z + this.zPosition * 16, j, i);
/*  430 */       this.heightMap[z << 4 | x] = j;
/*  431 */       int k = this.xPosition * 16 + x;
/*  432 */       int l = this.zPosition * 16 + z;
/*      */       
/*  434 */       if (this.worldObj.provider.func_191066_m()) {
/*      */         
/*  436 */         if (j < i) {
/*      */           
/*  438 */           for (int j1 = j; j1 < i; j1++) {
/*      */             
/*  440 */             ExtendedBlockStorage extendedblockstorage2 = this.storageArrays[j1 >> 4];
/*      */             
/*  442 */             if (extendedblockstorage2 != NULL_BLOCK_STORAGE)
/*      */             {
/*  444 */               extendedblockstorage2.setExtSkylightValue(x, j1 & 0xF, z, 15);
/*  445 */               this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + x, j1, (this.zPosition << 4) + z));
/*      */             }
/*      */           
/*      */           } 
/*      */         } else {
/*      */           
/*  451 */           for (int i1 = i; i1 < j; i1++) {
/*      */             
/*  453 */             ExtendedBlockStorage extendedblockstorage = this.storageArrays[i1 >> 4];
/*      */             
/*  455 */             if (extendedblockstorage != NULL_BLOCK_STORAGE) {
/*      */               
/*  457 */               extendedblockstorage.setExtSkylightValue(x, i1 & 0xF, z, 0);
/*  458 */               this.worldObj.notifyLightSet(new BlockPos((this.xPosition << 4) + x, i1, (this.zPosition << 4) + z));
/*      */             } 
/*      */           } 
/*      */         } 
/*      */         
/*  463 */         int k1 = 15;
/*      */         
/*  465 */         while (j > 0 && k1 > 0) {
/*      */           
/*  467 */           j--;
/*  468 */           int i2 = getBlockLightOpacity(x, j, z);
/*      */           
/*  470 */           if (i2 == 0)
/*      */           {
/*  472 */             i2 = 1;
/*      */           }
/*      */           
/*  475 */           k1 -= i2;
/*      */           
/*  477 */           if (k1 < 0)
/*      */           {
/*  479 */             k1 = 0;
/*      */           }
/*      */           
/*  482 */           ExtendedBlockStorage extendedblockstorage1 = this.storageArrays[j >> 4];
/*      */           
/*  484 */           if (extendedblockstorage1 != NULL_BLOCK_STORAGE)
/*      */           {
/*  486 */             extendedblockstorage1.setExtSkylightValue(x, j & 0xF, z, k1);
/*      */           }
/*      */         } 
/*      */       } 
/*      */       
/*  491 */       int l1 = this.heightMap[z << 4 | x];
/*  492 */       int j2 = i;
/*  493 */       int k2 = l1;
/*      */       
/*  495 */       if (l1 < i) {
/*      */         
/*  497 */         j2 = l1;
/*  498 */         k2 = i;
/*      */       } 
/*      */       
/*  501 */       if (l1 < this.heightMapMinimum)
/*      */       {
/*  503 */         this.heightMapMinimum = l1;
/*      */       }
/*      */       
/*  506 */       if (this.worldObj.provider.func_191066_m()) {
/*      */         
/*  508 */         for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
/*      */         {
/*  510 */           updateSkylightNeighborHeight(k + enumfacing.getFrontOffsetX(), l + enumfacing.getFrontOffsetZ(), j2, k2);
/*      */         }
/*      */         
/*  513 */         updateSkylightNeighborHeight(k, l, j2, k2);
/*      */       } 
/*      */       
/*  516 */       this.isModified = true;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBlockLightOpacity(BlockPos pos) {
/*  522 */     return getBlockState(pos).getLightOpacity();
/*      */   }
/*      */ 
/*      */   
/*      */   private int getBlockLightOpacity(int x, int y, int z) {
/*  527 */     return getBlockState(x, y, z).getLightOpacity();
/*      */   }
/*      */ 
/*      */   
/*      */   public IBlockState getBlockState(BlockPos pos) {
/*  532 */     return getBlockState(pos.getX(), pos.getY(), pos.getZ());
/*      */   }
/*      */ 
/*      */   
/*      */   public IBlockState getBlockState(final int x, final int y, final int z) {
/*  537 */     if (this.worldObj.getWorldType() == WorldType.DEBUG_WORLD) {
/*      */       
/*  539 */       IBlockState iblockstate = null;
/*      */       
/*  541 */       if (y == 60)
/*      */       {
/*  543 */         iblockstate = Blocks.BARRIER.getDefaultState();
/*      */       }
/*      */       
/*  546 */       if (y == 70)
/*      */       {
/*  548 */         iblockstate = ChunkGeneratorDebug.getBlockStateFor(x, z);
/*      */       }
/*      */       
/*  551 */       return (iblockstate == null) ? Blocks.AIR.getDefaultState() : iblockstate;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  557 */       if (y >= 0 && y >> 4 < this.storageArrays.length) {
/*      */         
/*  559 */         ExtendedBlockStorage extendedblockstorage = this.storageArrays[y >> 4];
/*      */         
/*  561 */         if (extendedblockstorage != NULL_BLOCK_STORAGE)
/*      */         {
/*  563 */           return extendedblockstorage.get(x & 0xF, y & 0xF, z & 0xF);
/*      */         }
/*      */       } 
/*      */       
/*  567 */       return Blocks.AIR.getDefaultState();
/*      */     }
/*  569 */     catch (Throwable throwable) {
/*      */       
/*  571 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting block state");
/*  572 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Block being got");
/*  573 */       crashreportcategory.setDetail("Location", new ICrashReportDetail<String>()
/*      */           {
/*      */             public String call() throws Exception
/*      */             {
/*  577 */               return CrashReportCategory.getCoordinateInfo(x, y, z);
/*      */             }
/*      */           });
/*  580 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public IBlockState setBlockState(BlockPos pos, IBlockState state) {
/*  588 */     int i = pos.getX() & 0xF;
/*  589 */     int j = pos.getY();
/*  590 */     int k = pos.getZ() & 0xF;
/*  591 */     int l = k << 4 | i;
/*      */     
/*  593 */     if (j >= this.precipitationHeightMap[l] - 1)
/*      */     {
/*  595 */       this.precipitationHeightMap[l] = -999;
/*      */     }
/*      */     
/*  598 */     int i1 = this.heightMap[l];
/*  599 */     IBlockState iblockstate = getBlockState(pos);
/*      */     
/*  601 */     if (iblockstate == state)
/*      */     {
/*  603 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  607 */     Block block = state.getBlock();
/*  608 */     Block block1 = iblockstate.getBlock();
/*  609 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*  610 */     boolean flag = false;
/*      */     
/*  612 */     if (extendedblockstorage == NULL_BLOCK_STORAGE) {
/*      */       
/*  614 */       if (block == Blocks.AIR)
/*      */       {
/*  616 */         return null;
/*      */       }
/*      */       
/*  619 */       extendedblockstorage = new ExtendedBlockStorage(j >> 4 << 4, this.worldObj.provider.func_191066_m());
/*  620 */       this.storageArrays[j >> 4] = extendedblockstorage;
/*  621 */       flag = (j >= i1);
/*      */     } 
/*      */     
/*  624 */     extendedblockstorage.set(i, j & 0xF, k, state);
/*      */     
/*  626 */     if (block1 != block)
/*      */     {
/*  628 */       if (!this.worldObj.isRemote) {
/*      */         
/*  630 */         block1.breakBlock(this.worldObj, pos, iblockstate);
/*      */       }
/*  632 */       else if (block1 instanceof ITileEntityProvider) {
/*      */         
/*  634 */         this.worldObj.removeTileEntity(pos);
/*      */       } 
/*      */     }
/*      */     
/*  638 */     if (extendedblockstorage.get(i, j & 0xF, k).getBlock() != block)
/*      */     {
/*  640 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  644 */     if (flag) {
/*      */       
/*  646 */       generateSkylightMap();
/*      */     }
/*      */     else {
/*      */       
/*  650 */       int j1 = state.getLightOpacity();
/*  651 */       int k1 = iblockstate.getLightOpacity();
/*      */       
/*  653 */       if (j1 > 0) {
/*      */         
/*  655 */         if (j >= i1)
/*      */         {
/*  657 */           relightBlock(i, j + 1, k);
/*      */         }
/*      */       }
/*  660 */       else if (j == i1 - 1) {
/*      */         
/*  662 */         relightBlock(i, j, k);
/*      */       } 
/*      */       
/*  665 */       if (j1 != k1 && (j1 < k1 || getLightFor(EnumSkyBlock.SKY, pos) > 0 || getLightFor(EnumSkyBlock.BLOCK, pos) > 0))
/*      */       {
/*  667 */         propagateSkylightOcclusion(i, k);
/*      */       }
/*      */     } 
/*      */     
/*  671 */     if (block1 instanceof ITileEntityProvider) {
/*      */       
/*  673 */       TileEntity tileentity = getTileEntity(pos, EnumCreateEntityType.CHECK);
/*      */       
/*  675 */       if (tileentity != null)
/*      */       {
/*  677 */         tileentity.updateContainingBlockInfo();
/*      */       }
/*      */     } 
/*      */     
/*  681 */     if (!this.worldObj.isRemote && block1 != block)
/*      */     {
/*  683 */       block.onBlockAdded(this.worldObj, pos, state);
/*      */     }
/*      */     
/*  686 */     if (block instanceof ITileEntityProvider) {
/*      */       
/*  688 */       TileEntity tileentity1 = getTileEntity(pos, EnumCreateEntityType.CHECK);
/*      */       
/*  690 */       if (tileentity1 == null) {
/*      */         
/*  692 */         tileentity1 = ((ITileEntityProvider)block).createNewTileEntity(this.worldObj, block.getMetaFromState(state));
/*  693 */         this.worldObj.setTileEntity(pos, tileentity1);
/*      */       } 
/*      */       
/*  696 */       if (tileentity1 != null)
/*      */       {
/*  698 */         tileentity1.updateContainingBlockInfo();
/*      */       }
/*      */     } 
/*      */     
/*  702 */     this.isModified = true;
/*  703 */     return iblockstate;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLightFor(EnumSkyBlock p_177413_1_, BlockPos pos) {
/*  710 */     int i = pos.getX() & 0xF;
/*  711 */     int j = pos.getY();
/*  712 */     int k = pos.getZ() & 0xF;
/*  713 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*      */     
/*  715 */     if (extendedblockstorage == NULL_BLOCK_STORAGE)
/*      */     {
/*  717 */       return canSeeSky(pos) ? p_177413_1_.defaultLightValue : 0;
/*      */     }
/*  719 */     if (p_177413_1_ == EnumSkyBlock.SKY)
/*      */     {
/*  721 */       return !this.worldObj.provider.func_191066_m() ? 0 : extendedblockstorage.getExtSkylightValue(i, j & 0xF, k);
/*      */     }
/*      */ 
/*      */     
/*  725 */     return (p_177413_1_ == EnumSkyBlock.BLOCK) ? extendedblockstorage.getExtBlocklightValue(i, j & 0xF, k) : p_177413_1_.defaultLightValue;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLightFor(EnumSkyBlock p_177431_1_, BlockPos pos, int value) {
/*  731 */     int i = pos.getX() & 0xF;
/*  732 */     int j = pos.getY();
/*  733 */     int k = pos.getZ() & 0xF;
/*  734 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*      */     
/*  736 */     if (extendedblockstorage == NULL_BLOCK_STORAGE) {
/*      */       
/*  738 */       extendedblockstorage = new ExtendedBlockStorage(j >> 4 << 4, this.worldObj.provider.func_191066_m());
/*  739 */       this.storageArrays[j >> 4] = extendedblockstorage;
/*  740 */       generateSkylightMap();
/*      */     } 
/*      */     
/*  743 */     this.isModified = true;
/*      */     
/*  745 */     if (p_177431_1_ == EnumSkyBlock.SKY) {
/*      */       
/*  747 */       if (this.worldObj.provider.func_191066_m())
/*      */       {
/*  749 */         extendedblockstorage.setExtSkylightValue(i, j & 0xF, k, value);
/*      */       }
/*      */     }
/*  752 */     else if (p_177431_1_ == EnumSkyBlock.BLOCK) {
/*      */       
/*  754 */       extendedblockstorage.setExtBlocklightValue(i, j & 0xF, k, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLightSubtracted(BlockPos pos, int amount) {
/*  760 */     int i = pos.getX() & 0xF;
/*  761 */     int j = pos.getY();
/*  762 */     int k = pos.getZ() & 0xF;
/*  763 */     ExtendedBlockStorage extendedblockstorage = this.storageArrays[j >> 4];
/*      */     
/*  765 */     if (extendedblockstorage == NULL_BLOCK_STORAGE)
/*      */     {
/*  767 */       return (this.worldObj.provider.func_191066_m() && amount < EnumSkyBlock.SKY.defaultLightValue) ? (EnumSkyBlock.SKY.defaultLightValue - amount) : 0;
/*      */     }
/*      */ 
/*      */     
/*  771 */     int l = !this.worldObj.provider.func_191066_m() ? 0 : extendedblockstorage.getExtSkylightValue(i, j & 0xF, k);
/*  772 */     l -= amount;
/*  773 */     int i1 = extendedblockstorage.getExtBlocklightValue(i, j & 0xF, k);
/*      */     
/*  775 */     if (i1 > l)
/*      */     {
/*  777 */       l = i1;
/*      */     }
/*      */     
/*  780 */     return l;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addEntity(Entity entityIn) {
/*  789 */     this.hasEntities = true;
/*  790 */     int i = MathHelper.floor(entityIn.posX / 16.0D);
/*  791 */     int j = MathHelper.floor(entityIn.posZ / 16.0D);
/*      */     
/*  793 */     if (i != this.xPosition || j != this.zPosition) {
/*      */       
/*  795 */       LOGGER.warn("Wrong location! ({}, {}) should be ({}, {}), {}", Integer.valueOf(i), Integer.valueOf(j), Integer.valueOf(this.xPosition), Integer.valueOf(this.zPosition), entityIn);
/*  796 */       entityIn.setDead();
/*      */     } 
/*      */     
/*  799 */     int k = MathHelper.floor(entityIn.posY / 16.0D);
/*      */     
/*  801 */     if (k < 0)
/*      */     {
/*  803 */       k = 0;
/*      */     }
/*      */     
/*  806 */     if (k >= this.entityLists.length)
/*      */     {
/*  808 */       k = this.entityLists.length - 1;
/*      */     }
/*      */     
/*  811 */     entityIn.addedToChunk = true;
/*  812 */     entityIn.chunkCoordX = this.xPosition;
/*  813 */     entityIn.chunkCoordY = k;
/*  814 */     entityIn.chunkCoordZ = this.zPosition;
/*  815 */     this.entityLists[k].add(entityIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntity(Entity entityIn) {
/*  823 */     removeEntityAtIndex(entityIn, entityIn.chunkCoordY);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeEntityAtIndex(Entity entityIn, int index) {
/*  831 */     if (index < 0)
/*      */     {
/*  833 */       index = 0;
/*      */     }
/*      */     
/*  836 */     if (index >= this.entityLists.length)
/*      */     {
/*  838 */       index = this.entityLists.length - 1;
/*      */     }
/*      */     
/*  841 */     this.entityLists[index].remove(entityIn);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean canSeeSky(BlockPos pos) {
/*  846 */     int i = pos.getX() & 0xF;
/*  847 */     int j = pos.getY();
/*  848 */     int k = pos.getZ() & 0xF;
/*  849 */     return (j >= this.heightMap[k << 4 | i]);
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   private TileEntity createNewTileEntity(BlockPos pos) {
/*  855 */     IBlockState iblockstate = getBlockState(pos);
/*  856 */     Block block = iblockstate.getBlock();
/*  857 */     return !block.hasTileEntity() ? null : ((ITileEntityProvider)block).createNewTileEntity(this.worldObj, iblockstate.getBlock().getMetaFromState(iblockstate));
/*      */   }
/*      */ 
/*      */   
/*      */   @Nullable
/*      */   public TileEntity getTileEntity(BlockPos pos, EnumCreateEntityType p_177424_2_) {
/*  863 */     TileEntity tileentity = this.chunkTileEntityMap.get(pos);
/*      */     
/*  865 */     if (tileentity == null) {
/*      */       
/*  867 */       if (p_177424_2_ == EnumCreateEntityType.IMMEDIATE)
/*      */       {
/*  869 */         tileentity = createNewTileEntity(pos);
/*  870 */         this.worldObj.setTileEntity(pos, tileentity);
/*      */       }
/*  872 */       else if (p_177424_2_ == EnumCreateEntityType.QUEUED)
/*      */       {
/*  874 */         this.tileEntityPosQueue.add(pos);
/*      */       }
/*      */     
/*  877 */     } else if (tileentity.isInvalid()) {
/*      */       
/*  879 */       this.chunkTileEntityMap.remove(pos);
/*  880 */       return null;
/*      */     } 
/*      */     
/*  883 */     return tileentity;
/*      */   }
/*      */ 
/*      */   
/*      */   public void addTileEntity(TileEntity tileEntityIn) {
/*  888 */     addTileEntity(tileEntityIn.getPos(), tileEntityIn);
/*      */     
/*  890 */     if (this.isChunkLoaded)
/*      */     {
/*  892 */       this.worldObj.addTileEntity(tileEntityIn);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void addTileEntity(BlockPos pos, TileEntity tileEntityIn) {
/*  898 */     tileEntityIn.setWorldObj(this.worldObj);
/*  899 */     tileEntityIn.setPos(pos);
/*      */     
/*  901 */     if (getBlockState(pos).getBlock() instanceof ITileEntityProvider) {
/*      */       
/*  903 */       if (this.chunkTileEntityMap.containsKey(pos))
/*      */       {
/*  905 */         ((TileEntity)this.chunkTileEntityMap.get(pos)).invalidate();
/*      */       }
/*      */       
/*  908 */       tileEntityIn.validate();
/*  909 */       this.chunkTileEntityMap.put(pos, tileEntityIn);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void removeTileEntity(BlockPos pos) {
/*  915 */     if (this.isChunkLoaded) {
/*      */       
/*  917 */       TileEntity tileentity = this.chunkTileEntityMap.remove(pos);
/*      */       
/*  919 */       if (tileentity != null)
/*      */       {
/*  921 */         tileentity.invalidate();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChunkLoad() {
/*  931 */     this.isChunkLoaded = true;
/*  932 */     this.worldObj.addTileEntities(this.chunkTileEntityMap.values()); byte b; int i;
/*      */     ClassInheritanceMultiMap<Entity>[] arrayOfClassInheritanceMultiMap;
/*  934 */     for (i = (arrayOfClassInheritanceMultiMap = this.entityLists).length, b = 0; b < i; ) { ClassInheritanceMultiMap<Entity> classinheritancemultimap = arrayOfClassInheritanceMultiMap[b];
/*      */       
/*  936 */       this.worldObj.loadEntities((Collection)classinheritancemultimap);
/*      */       b++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onChunkUnload() {
/*  945 */     this.isChunkLoaded = false;
/*      */     
/*  947 */     for (TileEntity tileentity : this.chunkTileEntityMap.values())
/*      */     {
/*  949 */       this.worldObj.markTileEntityForRemoval(tileentity); }  byte b;
/*      */     int i;
/*      */     ClassInheritanceMultiMap<Entity>[] arrayOfClassInheritanceMultiMap;
/*  952 */     for (i = (arrayOfClassInheritanceMultiMap = this.entityLists).length, b = 0; b < i; ) { ClassInheritanceMultiMap<Entity> classinheritancemultimap = arrayOfClassInheritanceMultiMap[b];
/*      */       
/*  954 */       this.worldObj.unloadEntities((Collection)classinheritancemultimap);
/*      */       b++; }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setChunkModified() {
/*  963 */     this.isModified = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getEntitiesWithinAABBForEntity(@Nullable Entity entityIn, AxisAlignedBB aabb, List<Entity> listToFill, Predicate<? super Entity> p_177414_4_) {
/*  971 */     int i = MathHelper.floor((aabb.minY - 2.0D) / 16.0D);
/*  972 */     int j = MathHelper.floor((aabb.maxY + 2.0D) / 16.0D);
/*  973 */     i = MathHelper.clamp(i, 0, this.entityLists.length - 1);
/*  974 */     j = MathHelper.clamp(j, 0, this.entityLists.length - 1);
/*      */     
/*  976 */     for (int k = i; k <= j; k++) {
/*      */       
/*  978 */       if (!this.entityLists[k].isEmpty())
/*      */       {
/*  980 */         for (Entity entity : this.entityLists[k]) {
/*      */           
/*  982 */           if (entity.getEntityBoundingBox().intersectsWith(aabb) && entity != entityIn) {
/*      */             
/*  984 */             if (p_177414_4_ == null || p_177414_4_.apply(entity))
/*      */             {
/*  986 */               listToFill.add(entity);
/*      */             }
/*      */             
/*  989 */             Entity[] aentity = entity.getParts();
/*      */             
/*  991 */             if (aentity != null) {
/*      */               byte b; int m; Entity[] arrayOfEntity;
/*  993 */               for (m = (arrayOfEntity = aentity).length, b = 0; b < m; ) { Entity entity1 = arrayOfEntity[b];
/*      */                 
/*  995 */                 if (entity1 != entityIn && entity1.getEntityBoundingBox().intersectsWith(aabb) && (p_177414_4_ == null || p_177414_4_.apply(entity1)))
/*      */                 {
/*  997 */                   listToFill.add(entity1);
/*      */                 }
/*      */                 b++; }
/*      */             
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public <T extends Entity> void getEntitiesOfTypeWithinAAAB(Class<? extends T> entityClass, AxisAlignedBB aabb, List<T> listToFill, Predicate<? super T> filter) {
/* 1009 */     int i = MathHelper.floor((aabb.minY - 2.0D) / 16.0D);
/* 1010 */     int j = MathHelper.floor((aabb.maxY + 2.0D) / 16.0D);
/* 1011 */     i = MathHelper.clamp(i, 0, this.entityLists.length - 1);
/* 1012 */     j = MathHelper.clamp(j, 0, this.entityLists.length - 1);
/*      */     
/* 1014 */     for (int k = i; k <= j; k++) {
/*      */       
/* 1016 */       for (Entity entity : this.entityLists[k].getByClass(entityClass)) {
/*      */         
/* 1018 */         if (entity.getEntityBoundingBox().intersectsWith(aabb) && (filter == null || filter.apply(entity)))
/*      */         {
/* 1020 */           listToFill.add((T)entity);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean needsSaving(boolean p_76601_1_) {
/* 1031 */     if (p_76601_1_) {
/*      */       
/* 1033 */       if ((this.hasEntities && this.worldObj.getTotalWorldTime() != this.lastSaveTime) || this.isModified)
/*      */       {
/* 1035 */         return true;
/*      */       }
/*      */     }
/* 1038 */     else if (this.hasEntities && this.worldObj.getTotalWorldTime() >= this.lastSaveTime + 600L) {
/*      */       
/* 1040 */       return true;
/*      */     } 
/*      */     
/* 1043 */     return this.isModified;
/*      */   }
/*      */ 
/*      */   
/*      */   public Random getRandomWithSeed(long seed) {
/* 1048 */     return new Random(this.worldObj.getSeed() + (this.xPosition * this.xPosition * 4987142) + (this.xPosition * 5947611) + (this.zPosition * this.zPosition) * 4392871L + (this.zPosition * 389711) ^ seed);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/* 1053 */     return false;
/*      */   }
/*      */ 
/*      */   
/*      */   public void populateChunk(IChunkProvider chunkProvider, IChunkGenerator chunkGenrator) {
/* 1058 */     Chunk chunk = chunkProvider.getLoadedChunk(this.xPosition, this.zPosition - 1);
/* 1059 */     Chunk chunk1 = chunkProvider.getLoadedChunk(this.xPosition + 1, this.zPosition);
/* 1060 */     Chunk chunk2 = chunkProvider.getLoadedChunk(this.xPosition, this.zPosition + 1);
/* 1061 */     Chunk chunk3 = chunkProvider.getLoadedChunk(this.xPosition - 1, this.zPosition);
/*      */     
/* 1063 */     if (chunk1 != null && chunk2 != null && chunkProvider.getLoadedChunk(this.xPosition + 1, this.zPosition + 1) != null)
/*      */     {
/* 1065 */       populateChunk(chunkGenrator);
/*      */     }
/*      */     
/* 1068 */     if (chunk3 != null && chunk2 != null && chunkProvider.getLoadedChunk(this.xPosition - 1, this.zPosition + 1) != null)
/*      */     {
/* 1070 */       chunk3.populateChunk(chunkGenrator);
/*      */     }
/*      */     
/* 1073 */     if (chunk != null && chunk1 != null && chunkProvider.getLoadedChunk(this.xPosition + 1, this.zPosition - 1) != null)
/*      */     {
/* 1075 */       chunk.populateChunk(chunkGenrator);
/*      */     }
/*      */     
/* 1078 */     if (chunk != null && chunk3 != null) {
/*      */       
/* 1080 */       Chunk chunk4 = chunkProvider.getLoadedChunk(this.xPosition - 1, this.zPosition - 1);
/*      */       
/* 1082 */       if (chunk4 != null)
/*      */       {
/* 1084 */         chunk4.populateChunk(chunkGenrator);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void populateChunk(IChunkGenerator generator) {
/* 1091 */     if (isTerrainPopulated()) {
/*      */       
/* 1093 */       if (generator.generateStructures(this, this.xPosition, this.zPosition))
/*      */       {
/* 1095 */         setChunkModified();
/*      */       }
/*      */     }
/*      */     else {
/*      */       
/* 1100 */       checkLight();
/* 1101 */       generator.populate(this.xPosition, this.zPosition);
/* 1102 */       setChunkModified();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockPos getPrecipitationHeight(BlockPos pos) {
/* 1108 */     int i = pos.getX() & 0xF;
/* 1109 */     int j = pos.getZ() & 0xF;
/* 1110 */     int k = i | j << 4;
/* 1111 */     BlockPos blockpos = new BlockPos(pos.getX(), this.precipitationHeightMap[k], pos.getZ());
/*      */     
/* 1113 */     if (blockpos.getY() == -999) {
/*      */       
/* 1115 */       int l = getTopFilledSegment() + 15;
/* 1116 */       blockpos = new BlockPos(pos.getX(), l, pos.getZ());
/* 1117 */       int i1 = -1;
/*      */       
/* 1119 */       while (blockpos.getY() > 0 && i1 == -1) {
/*      */         
/* 1121 */         IBlockState iblockstate = getBlockState(blockpos);
/* 1122 */         Material material = iblockstate.getMaterial();
/*      */         
/* 1124 */         if (!material.blocksMovement() && !material.isLiquid()) {
/*      */           
/* 1126 */           blockpos = blockpos.down();
/*      */           
/*      */           continue;
/*      */         } 
/* 1130 */         i1 = blockpos.getY() + 1;
/*      */       } 
/*      */ 
/*      */       
/* 1134 */       this.precipitationHeightMap[k] = i1;
/*      */     } 
/*      */     
/* 1137 */     return new BlockPos(pos.getX(), this.precipitationHeightMap[k], pos.getZ());
/*      */   }
/*      */ 
/*      */   
/*      */   public void onTick(boolean p_150804_1_) {
/* 1142 */     if (this.isGapLightingUpdated && this.worldObj.provider.func_191066_m() && !p_150804_1_)
/*      */     {
/* 1144 */       recheckGaps(this.worldObj.isRemote);
/*      */     }
/*      */     
/* 1147 */     this.chunkTicked = true;
/*      */     
/* 1149 */     if (!this.isLightPopulated && this.isTerrainPopulated)
/*      */     {
/* 1151 */       checkLight();
/*      */     }
/*      */     
/* 1154 */     while (!this.tileEntityPosQueue.isEmpty()) {
/*      */       
/* 1156 */       BlockPos blockpos = this.tileEntityPosQueue.poll();
/*      */       
/* 1158 */       if (getTileEntity(blockpos, EnumCreateEntityType.CHECK) == null && getBlockState(blockpos).getBlock().hasTileEntity()) {
/*      */         
/* 1160 */         TileEntity tileentity = createNewTileEntity(blockpos);
/* 1161 */         this.worldObj.setTileEntity(blockpos, tileentity);
/* 1162 */         this.worldObj.markBlockRangeForRenderUpdate(blockpos, blockpos);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isPopulated() {
/* 1169 */     return (this.chunkTicked && this.isTerrainPopulated && this.isLightPopulated);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isChunkTicked() {
/* 1174 */     return this.chunkTicked;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ChunkPos getChunkCoordIntPair() {
/* 1182 */     return new ChunkPos(this.xPosition, this.zPosition);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAreLevelsEmpty(int startY, int endY) {
/* 1191 */     if (startY < 0)
/*      */     {
/* 1193 */       startY = 0;
/*      */     }
/*      */     
/* 1196 */     if (endY >= 256)
/*      */     {
/* 1198 */       endY = 255;
/*      */     }
/*      */     
/* 1201 */     for (int i = startY; i <= endY; i += 16) {
/*      */       
/* 1203 */       ExtendedBlockStorage extendedblockstorage = this.storageArrays[i >> 4];
/*      */       
/* 1205 */       if (extendedblockstorage != NULL_BLOCK_STORAGE && !extendedblockstorage.isEmpty())
/*      */       {
/* 1207 */         return false;
/*      */       }
/*      */     } 
/*      */     
/* 1211 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setStorageArrays(ExtendedBlockStorage[] newStorageArrays) {
/* 1216 */     if (this.storageArrays.length != newStorageArrays.length) {
/*      */       
/* 1218 */       LOGGER.warn("Could not set level chunk sections, array length is {} instead of {}", Integer.valueOf(newStorageArrays.length), Integer.valueOf(this.storageArrays.length));
/*      */     }
/*      */     else {
/*      */       
/* 1222 */       System.arraycopy(newStorageArrays, 0, this.storageArrays, 0, this.storageArrays.length);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void fillChunk(PacketBuffer buf, int p_186033_2_, boolean p_186033_3_) {
/* 1228 */     boolean flag = this.worldObj.provider.func_191066_m();
/*      */     
/* 1230 */     for (int i = 0; i < this.storageArrays.length; i++) {
/*      */       
/* 1232 */       ExtendedBlockStorage extendedblockstorage = this.storageArrays[i];
/*      */       
/* 1234 */       if ((p_186033_2_ & 1 << i) == 0) {
/*      */         
/* 1236 */         if (p_186033_3_ && extendedblockstorage != NULL_BLOCK_STORAGE)
/*      */         {
/* 1238 */           this.storageArrays[i] = NULL_BLOCK_STORAGE;
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1243 */         if (extendedblockstorage == NULL_BLOCK_STORAGE) {
/*      */           
/* 1245 */           extendedblockstorage = new ExtendedBlockStorage(i << 4, flag);
/* 1246 */           this.storageArrays[i] = extendedblockstorage;
/*      */         } 
/*      */         
/* 1249 */         extendedblockstorage.getData().read(buf);
/* 1250 */         buf.readBytes(extendedblockstorage.getBlocklightArray().getData());
/*      */         
/* 1252 */         if (flag)
/*      */         {
/* 1254 */           buf.readBytes(extendedblockstorage.getSkylightArray().getData());
/*      */         }
/*      */       } 
/*      */     } 
/*      */     
/* 1259 */     if (p_186033_3_)
/*      */     {
/* 1261 */       buf.readBytes(this.blockBiomeArray);
/*      */     }
/*      */     
/* 1264 */     for (int j = 0; j < this.storageArrays.length; j++) {
/*      */       
/* 1266 */       if (this.storageArrays[j] != NULL_BLOCK_STORAGE && (p_186033_2_ & 1 << j) != 0)
/*      */       {
/* 1268 */         this.storageArrays[j].removeInvalidBlocks();
/*      */       }
/*      */     } 
/*      */     
/* 1272 */     this.isLightPopulated = true;
/* 1273 */     this.isTerrainPopulated = true;
/* 1274 */     generateHeightMap();
/*      */     
/* 1276 */     for (TileEntity tileentity : this.chunkTileEntityMap.values())
/*      */     {
/* 1278 */       tileentity.updateContainingBlockInfo();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public Biome getBiome(BlockPos pos, BiomeProvider provider) {
/* 1284 */     int i = pos.getX() & 0xF;
/* 1285 */     int j = pos.getZ() & 0xF;
/* 1286 */     int k = this.blockBiomeArray[j << 4 | i] & 0xFF;
/*      */     
/* 1288 */     if (k == 255) {
/*      */       
/* 1290 */       Biome biome = provider.getBiome(pos, Biomes.PLAINS);
/* 1291 */       k = Biome.getIdForBiome(biome);
/* 1292 */       this.blockBiomeArray[j << 4 | i] = (byte)(k & 0xFF);
/*      */     } 
/*      */     
/* 1295 */     Biome biome1 = Biome.getBiome(k);
/* 1296 */     return (biome1 == null) ? Biomes.PLAINS : biome1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public byte[] getBiomeArray() {
/* 1304 */     return this.blockBiomeArray;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBiomeArray(byte[] biomeArray) {
/* 1313 */     if (this.blockBiomeArray.length != biomeArray.length) {
/*      */       
/* 1315 */       LOGGER.warn("Could not set level chunk biomes, array length is {} instead of {}", Integer.valueOf(biomeArray.length), Integer.valueOf(this.blockBiomeArray.length));
/*      */     }
/*      */     else {
/*      */       
/* 1319 */       System.arraycopy(biomeArray, 0, this.blockBiomeArray, 0, this.blockBiomeArray.length);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void resetRelightChecks() {
/* 1328 */     this.queuedLightChecks = 0;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void enqueueRelightChecks() {
/* 1338 */     if (this.queuedLightChecks < 4096) {
/*      */       
/* 1340 */       BlockPos blockpos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
/*      */       
/* 1342 */       for (int i = 0; i < 8; i++) {
/*      */         
/* 1344 */         if (this.queuedLightChecks >= 4096) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/* 1349 */         int j = this.queuedLightChecks % 16;
/* 1350 */         int k = this.queuedLightChecks / 16 % 16;
/* 1351 */         int l = this.queuedLightChecks / 256;
/* 1352 */         this.queuedLightChecks++;
/*      */         
/* 1354 */         for (int i1 = 0; i1 < 16; i1++) {
/*      */           
/* 1356 */           BlockPos blockpos1 = blockpos.add(k, (j << 4) + i1, l);
/* 1357 */           boolean flag = !(i1 != 0 && i1 != 15 && k != 0 && k != 15 && l != 0 && l != 15);
/*      */           
/* 1359 */           if ((this.storageArrays[j] == NULL_BLOCK_STORAGE && flag) || (this.storageArrays[j] != NULL_BLOCK_STORAGE && this.storageArrays[j].get(k, i1, l).getMaterial() == Material.AIR)) {
/*      */             byte b; int m; EnumFacing[] arrayOfEnumFacing;
/* 1361 */             for (m = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < m; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*      */               
/* 1363 */               BlockPos blockpos2 = blockpos1.offset(enumfacing);
/*      */               
/* 1365 */               if (this.worldObj.getBlockState(blockpos2).getLightValue() > 0)
/*      */               {
/* 1367 */                 this.worldObj.checkLight(blockpos2);
/*      */               }
/*      */               b++; }
/*      */             
/* 1371 */             this.worldObj.checkLight(blockpos1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void checkLight() {
/* 1380 */     this.isTerrainPopulated = true;
/* 1381 */     this.isLightPopulated = true;
/* 1382 */     BlockPos blockpos = new BlockPos(this.xPosition << 4, 0, this.zPosition << 4);
/*      */     
/* 1384 */     if (this.worldObj.provider.func_191066_m())
/*      */     {
/* 1386 */       if (this.worldObj.isAreaLoaded(blockpos.add(-1, 0, -1), blockpos.add(16, this.worldObj.getSeaLevel(), 16))) {
/*      */         int i;
/*      */ 
/*      */         
/* 1390 */         label31: for (i = 0; i < 16; i++) {
/*      */           
/* 1392 */           for (int j = 0; j < 16; j++) {
/*      */             
/* 1394 */             if (!checkLight(i, j)) {
/*      */               
/* 1396 */               this.isLightPopulated = false;
/*      */               
/*      */               break label31;
/*      */             } 
/*      */           } 
/*      */         } 
/* 1402 */         if (this.isLightPopulated)
/*      */         {
/* 1404 */           for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*      */             
/* 1406 */             int k = (enumfacing.getAxisDirection() == EnumFacing.AxisDirection.POSITIVE) ? 16 : 1;
/* 1407 */             this.worldObj.getChunkFromBlockCoords(blockpos.offset(enumfacing, k)).checkLightSide(enumfacing.getOpposite());
/*      */           } 
/*      */           
/* 1410 */           setSkylightUpdated();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1415 */         this.isLightPopulated = false;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void setSkylightUpdated() {
/* 1422 */     for (int i = 0; i < this.updateSkylightColumns.length; i++)
/*      */     {
/* 1424 */       this.updateSkylightColumns[i] = true;
/*      */     }
/*      */     
/* 1427 */     recheckGaps(false);
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkLightSide(EnumFacing facing) {
/* 1432 */     if (this.isTerrainPopulated)
/*      */     {
/* 1434 */       if (facing == EnumFacing.EAST) {
/*      */         
/* 1436 */         for (int i = 0; i < 16; i++)
/*      */         {
/* 1438 */           checkLight(15, i);
/*      */         }
/*      */       }
/* 1441 */       else if (facing == EnumFacing.WEST) {
/*      */         
/* 1443 */         for (int j = 0; j < 16; j++)
/*      */         {
/* 1445 */           checkLight(0, j);
/*      */         }
/*      */       }
/* 1448 */       else if (facing == EnumFacing.SOUTH) {
/*      */         
/* 1450 */         for (int k = 0; k < 16; k++)
/*      */         {
/* 1452 */           checkLight(k, 15);
/*      */         }
/*      */       }
/* 1455 */       else if (facing == EnumFacing.NORTH) {
/*      */         
/* 1457 */         for (int l = 0; l < 16; l++)
/*      */         {
/* 1459 */           checkLight(l, 0);
/*      */         }
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean checkLight(int x, int z) {
/* 1467 */     int i = getTopFilledSegment();
/* 1468 */     boolean flag = false;
/* 1469 */     boolean flag1 = false;
/* 1470 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos((this.xPosition << 4) + x, 0, (this.zPosition << 4) + z);
/*      */     
/* 1472 */     for (int j = i + 16 - 1; j > this.worldObj.getSeaLevel() || (j > 0 && !flag1); j--) {
/*      */       
/* 1474 */       blockpos$mutableblockpos.setPos(blockpos$mutableblockpos.getX(), j, blockpos$mutableblockpos.getZ());
/* 1475 */       int k = getBlockLightOpacity((BlockPos)blockpos$mutableblockpos);
/*      */       
/* 1477 */       if (k == 255 && blockpos$mutableblockpos.getY() < this.worldObj.getSeaLevel())
/*      */       {
/* 1479 */         flag1 = true;
/*      */       }
/*      */       
/* 1482 */       if (!flag && k > 0) {
/*      */         
/* 1484 */         flag = true;
/*      */       }
/* 1486 */       else if (flag && k == 0 && !this.worldObj.checkLight((BlockPos)blockpos$mutableblockpos)) {
/*      */         
/* 1488 */         return false;
/*      */       } 
/*      */     } 
/*      */     
/* 1492 */     for (int l = blockpos$mutableblockpos.getY(); l > 0; l--) {
/*      */       
/* 1494 */       blockpos$mutableblockpos.setPos(blockpos$mutableblockpos.getX(), l, blockpos$mutableblockpos.getZ());
/*      */       
/* 1496 */       if (getBlockState((BlockPos)blockpos$mutableblockpos).getLightValue() > 0)
/*      */       {
/* 1498 */         this.worldObj.checkLight((BlockPos)blockpos$mutableblockpos);
/*      */       }
/*      */     } 
/*      */     
/* 1502 */     return true;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isLoaded() {
/* 1507 */     return this.isChunkLoaded;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setChunkLoaded(boolean loaded) {
/* 1512 */     this.isChunkLoaded = loaded;
/*      */   }
/*      */ 
/*      */   
/*      */   public World getWorld() {
/* 1517 */     return this.worldObj;
/*      */   }
/*      */ 
/*      */   
/*      */   public int[] getHeightMap() {
/* 1522 */     return this.heightMap;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHeightMap(int[] newHeightMap) {
/* 1527 */     if (this.heightMap.length != newHeightMap.length) {
/*      */       
/* 1529 */       LOGGER.warn("Could not set level chunk heightmap, array length is {} instead of {}", Integer.valueOf(newHeightMap.length), Integer.valueOf(this.heightMap.length));
/*      */     }
/*      */     else {
/*      */       
/* 1533 */       System.arraycopy(newHeightMap, 0, this.heightMap, 0, this.heightMap.length);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public Map<BlockPos, TileEntity> getTileEntityMap() {
/* 1539 */     return this.chunkTileEntityMap;
/*      */   }
/*      */ 
/*      */   
/*      */   public ClassInheritanceMultiMap<Entity>[] getEntityLists() {
/* 1544 */     return this.entityLists;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isTerrainPopulated() {
/* 1549 */     return this.isTerrainPopulated;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setTerrainPopulated(boolean terrainPopulated) {
/* 1554 */     this.isTerrainPopulated = terrainPopulated;
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isLightPopulated() {
/* 1559 */     return this.isLightPopulated;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLightPopulated(boolean lightPopulated) {
/* 1564 */     this.isLightPopulated = lightPopulated;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setModified(boolean modified) {
/* 1569 */     this.isModified = modified;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHasEntities(boolean hasEntitiesIn) {
/* 1574 */     this.hasEntities = hasEntitiesIn;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLastSaveTime(long saveTime) {
/* 1579 */     this.lastSaveTime = saveTime;
/*      */   }
/*      */ 
/*      */   
/*      */   public int getLowestHeight() {
/* 1584 */     return this.heightMapMinimum;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getInhabitedTime() {
/* 1589 */     return this.inhabitedTime;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setInhabitedTime(long newInhabitedTime) {
/* 1594 */     this.inhabitedTime = newInhabitedTime;
/*      */   }
/*      */   
/*      */   public enum EnumCreateEntityType
/*      */   {
/* 1599 */     IMMEDIATE,
/* 1600 */     QUEUED,
/* 1601 */     CHECK;
/*      */   }
/*      */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\chunk\Chunk.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */