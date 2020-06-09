/*     */ package net.minecraft.world.chunk.storage;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IDataFixer;
/*     */ import net.minecraft.util.datafix.IDataWalker;
/*     */ import net.minecraft.util.datafix.IFixType;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.world.MinecraftException;
/*     */ import net.minecraft.world.NextTickListEntry;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.NibbleArray;
/*     */ import net.minecraft.world.storage.IThreadedFileIO;
/*     */ import net.minecraft.world.storage.ThreadedFileIOBase;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class AnvilChunkLoader implements IChunkLoader, IThreadedFileIO {
/*  39 */   private static final Logger LOGGER = LogManager.getLogger();
/*  40 */   private final Map<ChunkPos, NBTTagCompound> chunksToRemove = Maps.newConcurrentMap();
/*  41 */   private final Set<ChunkPos> field_193415_c = Collections.newSetFromMap(Maps.newConcurrentMap());
/*     */   
/*     */   private final File chunkSaveLocation;
/*     */   
/*     */   private final DataFixer field_193416_e;
/*     */   
/*     */   private boolean savingExtraData;
/*     */   
/*     */   public AnvilChunkLoader(File chunkSaveLocationIn, DataFixer dataFixerIn) {
/*  50 */     this.chunkSaveLocation = chunkSaveLocationIn;
/*  51 */     this.field_193416_e = dataFixerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Chunk loadChunk(World worldIn, int x, int z) throws IOException {
/*  61 */     ChunkPos chunkpos = new ChunkPos(x, z);
/*  62 */     NBTTagCompound nbttagcompound = this.chunksToRemove.get(chunkpos);
/*     */     
/*  64 */     if (nbttagcompound == null) {
/*     */       
/*  66 */       DataInputStream datainputstream = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, x, z);
/*     */       
/*  68 */       if (datainputstream == null)
/*     */       {
/*  70 */         return null;
/*     */       }
/*     */       
/*  73 */       nbttagcompound = this.field_193416_e.process((IFixType)FixTypes.CHUNK, CompressedStreamTools.read(datainputstream));
/*     */     } 
/*     */     
/*  76 */     return checkedReadChunkFromNBT(worldIn, x, z, nbttagcompound);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191063_a(int p_191063_1_, int p_191063_2_) {
/*  81 */     ChunkPos chunkpos = new ChunkPos(p_191063_1_, p_191063_2_);
/*  82 */     NBTTagCompound nbttagcompound = this.chunksToRemove.get(chunkpos);
/*  83 */     return (nbttagcompound != null) ? true : RegionFileCache.func_191064_f(this.chunkSaveLocation, p_191063_1_, p_191063_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Chunk checkedReadChunkFromNBT(World worldIn, int x, int z, NBTTagCompound compound) {
/*  93 */     if (!compound.hasKey("Level", 10)) {
/*     */       
/*  95 */       LOGGER.error("Chunk file at {},{} is missing level data, skipping", Integer.valueOf(x), Integer.valueOf(z));
/*  96 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 100 */     NBTTagCompound nbttagcompound = compound.getCompoundTag("Level");
/*     */     
/* 102 */     if (!nbttagcompound.hasKey("Sections", 9)) {
/*     */       
/* 104 */       LOGGER.error("Chunk file at {},{} is missing block data, skipping", Integer.valueOf(x), Integer.valueOf(z));
/* 105 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 109 */     Chunk chunk = readChunkFromNBT(worldIn, nbttagcompound);
/*     */     
/* 111 */     if (!chunk.isAtLocation(x, z)) {
/*     */       
/* 113 */       LOGGER.error("Chunk file at {},{} is in the wrong location; relocating. (Expected {}, {}, got {}, {})", Integer.valueOf(x), Integer.valueOf(z), Integer.valueOf(x), Integer.valueOf(z), Integer.valueOf(chunk.xPosition), Integer.valueOf(chunk.zPosition));
/* 114 */       nbttagcompound.setInteger("xPos", x);
/* 115 */       nbttagcompound.setInteger("zPos", z);
/* 116 */       chunk = readChunkFromNBT(worldIn, nbttagcompound);
/*     */     } 
/*     */     
/* 119 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveChunk(World worldIn, Chunk chunkIn) throws MinecraftException, IOException {
/* 126 */     worldIn.checkSessionLock();
/*     */ 
/*     */     
/*     */     try {
/* 130 */       NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 131 */       NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 132 */       nbttagcompound.setTag("Level", (NBTBase)nbttagcompound1);
/* 133 */       nbttagcompound.setInteger("DataVersion", 1343);
/* 134 */       writeChunkToNBT(chunkIn, worldIn, nbttagcompound1);
/* 135 */       addChunkToPending(chunkIn.getChunkCoordIntPair(), nbttagcompound);
/*     */     }
/* 137 */     catch (Exception exception) {
/*     */       
/* 139 */       LOGGER.error("Failed to save chunk", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addChunkToPending(ChunkPos pos, NBTTagCompound compound) {
/* 145 */     if (!this.field_193415_c.contains(pos))
/*     */     {
/* 147 */       this.chunksToRemove.put(pos, compound);
/*     */     }
/*     */     
/* 150 */     ThreadedFileIOBase.getThreadedIOInstance().queueIO(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean writeNextIO() {
/*     */     boolean lvt_3_1_;
/* 158 */     if (this.chunksToRemove.isEmpty()) {
/*     */       
/* 160 */       if (this.savingExtraData)
/*     */       {
/* 162 */         LOGGER.info("ThreadedAnvilChunkStorage ({}): All chunks are saved", this.chunkSaveLocation.getName());
/*     */       }
/*     */       
/* 165 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 169 */     ChunkPos chunkpos = this.chunksToRemove.keySet().iterator().next();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 174 */       this.field_193415_c.add(chunkpos);
/* 175 */       NBTTagCompound nbttagcompound = this.chunksToRemove.remove(chunkpos);
/*     */       
/* 177 */       if (nbttagcompound != null) {
/*     */         
/*     */         try {
/*     */           
/* 181 */           writeChunkData(chunkpos, nbttagcompound);
/*     */         }
/* 183 */         catch (Exception exception) {
/*     */           
/* 185 */           LOGGER.error("Failed to save chunk", exception);
/*     */         } 
/*     */       }
/*     */       
/* 189 */       lvt_3_1_ = true;
/*     */     }
/*     */     finally {
/*     */       
/* 193 */       this.field_193415_c.remove(chunkpos);
/*     */     } 
/*     */     
/* 196 */     return lvt_3_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeChunkData(ChunkPos pos, NBTTagCompound compound) throws IOException {
/* 202 */     DataOutputStream dataoutputstream = RegionFileCache.getChunkOutputStream(this.chunkSaveLocation, pos.chunkXPos, pos.chunkZPos);
/* 203 */     CompressedStreamTools.write(compound, dataoutputstream);
/* 204 */     dataoutputstream.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraChunkData(World worldIn, Chunk chunkIn) throws IOException {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void chunkTick() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {
/*     */     try {
/* 230 */       this.savingExtraData = true; do {
/*     */       
/* 232 */       } while (writeNextIO());
/*     */     }
/*     */     finally {
/*     */       
/* 236 */       this.savingExtraData = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void registerFixes(DataFixer fixer) {
/* 242 */     fixer.registerWalker(FixTypes.CHUNK, new IDataWalker()
/*     */         {
/*     */           public NBTTagCompound process(IDataFixer fixer, NBTTagCompound compound, int versionIn)
/*     */           {
/* 246 */             if (compound.hasKey("Level", 10)) {
/*     */               
/* 248 */               NBTTagCompound nbttagcompound = compound.getCompoundTag("Level");
/*     */               
/* 250 */               if (nbttagcompound.hasKey("Entities", 9)) {
/*     */                 
/* 252 */                 NBTTagList nbttaglist = nbttagcompound.getTagList("Entities", 10);
/*     */                 
/* 254 */                 for (int i = 0; i < nbttaglist.tagCount(); i++)
/*     */                 {
/* 256 */                   nbttaglist.set(i, (NBTBase)fixer.process((IFixType)FixTypes.ENTITY, (NBTTagCompound)nbttaglist.get(i), versionIn));
/*     */                 }
/*     */               } 
/*     */               
/* 260 */               if (nbttagcompound.hasKey("TileEntities", 9)) {
/*     */                 
/* 262 */                 NBTTagList nbttaglist1 = nbttagcompound.getTagList("TileEntities", 10);
/*     */                 
/* 264 */                 for (int j = 0; j < nbttaglist1.tagCount(); j++)
/*     */                 {
/* 266 */                   nbttaglist1.set(j, (NBTBase)fixer.process((IFixType)FixTypes.BLOCK_ENTITY, (NBTTagCompound)nbttaglist1.get(j), versionIn));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */             
/* 271 */             return compound;
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void writeChunkToNBT(Chunk chunkIn, World worldIn, NBTTagCompound compound) {
/* 282 */     compound.setInteger("xPos", chunkIn.xPosition);
/* 283 */     compound.setInteger("zPos", chunkIn.zPosition);
/* 284 */     compound.setLong("LastUpdate", worldIn.getTotalWorldTime());
/* 285 */     compound.setIntArray("HeightMap", chunkIn.getHeightMap());
/* 286 */     compound.setBoolean("TerrainPopulated", chunkIn.isTerrainPopulated());
/* 287 */     compound.setBoolean("LightPopulated", chunkIn.isLightPopulated());
/* 288 */     compound.setLong("InhabitedTime", chunkIn.getInhabitedTime());
/* 289 */     ExtendedBlockStorage[] aextendedblockstorage = chunkIn.getBlockStorageArray();
/* 290 */     NBTTagList nbttaglist = new NBTTagList();
/* 291 */     boolean flag = worldIn.provider.func_191066_m(); byte b; int j;
/*     */     ExtendedBlockStorage[] arrayOfExtendedBlockStorage1;
/* 293 */     for (j = (arrayOfExtendedBlockStorage1 = aextendedblockstorage).length, b = 0; b < j; ) { ExtendedBlockStorage extendedblockstorage = arrayOfExtendedBlockStorage1[b];
/*     */       
/* 295 */       if (extendedblockstorage != Chunk.NULL_BLOCK_STORAGE) {
/*     */         
/* 297 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 298 */         nbttagcompound.setByte("Y", (byte)(extendedblockstorage.getYLocation() >> 4 & 0xFF));
/* 299 */         byte[] abyte = new byte[4096];
/* 300 */         NibbleArray nibblearray = new NibbleArray();
/* 301 */         NibbleArray nibblearray1 = extendedblockstorage.getData().getDataForNBT(abyte, nibblearray);
/* 302 */         nbttagcompound.setByteArray("Blocks", abyte);
/* 303 */         nbttagcompound.setByteArray("Data", nibblearray.getData());
/*     */         
/* 305 */         if (nibblearray1 != null)
/*     */         {
/* 307 */           nbttagcompound.setByteArray("Add", nibblearray1.getData());
/*     */         }
/*     */         
/* 310 */         nbttagcompound.setByteArray("BlockLight", extendedblockstorage.getBlocklightArray().getData());
/*     */         
/* 312 */         if (flag) {
/*     */           
/* 314 */           nbttagcompound.setByteArray("SkyLight", extendedblockstorage.getSkylightArray().getData());
/*     */         }
/*     */         else {
/*     */           
/* 318 */           nbttagcompound.setByteArray("SkyLight", new byte[(extendedblockstorage.getBlocklightArray().getData()).length]);
/*     */         } 
/*     */         
/* 321 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */       b++; }
/*     */     
/* 325 */     compound.setTag("Sections", (NBTBase)nbttaglist);
/* 326 */     compound.setByteArray("Biomes", chunkIn.getBiomeArray());
/* 327 */     chunkIn.setHasEntities(false);
/* 328 */     NBTTagList nbttaglist1 = new NBTTagList();
/*     */     
/* 330 */     for (int i = 0; i < (chunkIn.getEntityLists()).length; i++) {
/*     */       
/* 332 */       for (Entity entity : chunkIn.getEntityLists()[i]) {
/*     */         
/* 334 */         NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/*     */         
/* 336 */         if (entity.writeToNBTOptional(nbttagcompound2)) {
/*     */           
/* 338 */           chunkIn.setHasEntities(true);
/* 339 */           nbttaglist1.appendTag((NBTBase)nbttagcompound2);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 344 */     compound.setTag("Entities", (NBTBase)nbttaglist1);
/* 345 */     NBTTagList nbttaglist2 = new NBTTagList();
/*     */     
/* 347 */     for (TileEntity tileentity : chunkIn.getTileEntityMap().values()) {
/*     */       
/* 349 */       NBTTagCompound nbttagcompound3 = tileentity.writeToNBT(new NBTTagCompound());
/* 350 */       nbttaglist2.appendTag((NBTBase)nbttagcompound3);
/*     */     } 
/*     */     
/* 353 */     compound.setTag("TileEntities", (NBTBase)nbttaglist2);
/* 354 */     List<NextTickListEntry> list = worldIn.getPendingBlockUpdates(chunkIn, false);
/*     */     
/* 356 */     if (list != null) {
/*     */       
/* 358 */       long l = worldIn.getTotalWorldTime();
/* 359 */       NBTTagList nbttaglist3 = new NBTTagList();
/*     */       
/* 361 */       for (NextTickListEntry nextticklistentry : list) {
/*     */         
/* 363 */         NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 364 */         ResourceLocation resourcelocation = (ResourceLocation)Block.REGISTRY.getNameForObject(nextticklistentry.getBlock());
/* 365 */         nbttagcompound1.setString("i", (resourcelocation == null) ? "" : resourcelocation.toString());
/* 366 */         nbttagcompound1.setInteger("x", nextticklistentry.position.getX());
/* 367 */         nbttagcompound1.setInteger("y", nextticklistentry.position.getY());
/* 368 */         nbttagcompound1.setInteger("z", nextticklistentry.position.getZ());
/* 369 */         nbttagcompound1.setInteger("t", (int)(nextticklistentry.scheduledTime - l));
/* 370 */         nbttagcompound1.setInteger("p", nextticklistentry.priority);
/* 371 */         nbttaglist3.appendTag((NBTBase)nbttagcompound1);
/*     */       } 
/*     */       
/* 374 */       compound.setTag("TileTicks", (NBTBase)nbttaglist3);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Chunk readChunkFromNBT(World worldIn, NBTTagCompound compound) {
/* 384 */     int i = compound.getInteger("xPos");
/* 385 */     int j = compound.getInteger("zPos");
/* 386 */     Chunk chunk = new Chunk(worldIn, i, j);
/* 387 */     chunk.setHeightMap(compound.getIntArray("HeightMap"));
/* 388 */     chunk.setTerrainPopulated(compound.getBoolean("TerrainPopulated"));
/* 389 */     chunk.setLightPopulated(compound.getBoolean("LightPopulated"));
/* 390 */     chunk.setInhabitedTime(compound.getLong("InhabitedTime"));
/* 391 */     NBTTagList nbttaglist = compound.getTagList("Sections", 10);
/* 392 */     int k = 16;
/* 393 */     ExtendedBlockStorage[] aextendedblockstorage = new ExtendedBlockStorage[16];
/* 394 */     boolean flag = worldIn.provider.func_191066_m();
/*     */     
/* 396 */     for (int l = 0; l < nbttaglist.tagCount(); l++) {
/*     */       
/* 398 */       NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(l);
/* 399 */       int i1 = nbttagcompound.getByte("Y");
/* 400 */       ExtendedBlockStorage extendedblockstorage = new ExtendedBlockStorage(i1 << 4, flag);
/* 401 */       byte[] abyte = nbttagcompound.getByteArray("Blocks");
/* 402 */       NibbleArray nibblearray = new NibbleArray(nbttagcompound.getByteArray("Data"));
/* 403 */       NibbleArray nibblearray1 = nbttagcompound.hasKey("Add", 7) ? new NibbleArray(nbttagcompound.getByteArray("Add")) : null;
/* 404 */       extendedblockstorage.getData().setDataFromNBT(abyte, nibblearray, nibblearray1);
/* 405 */       extendedblockstorage.setBlocklightArray(new NibbleArray(nbttagcompound.getByteArray("BlockLight")));
/*     */       
/* 407 */       if (flag)
/*     */       {
/* 409 */         extendedblockstorage.setSkylightArray(new NibbleArray(nbttagcompound.getByteArray("SkyLight")));
/*     */       }
/*     */       
/* 412 */       extendedblockstorage.removeInvalidBlocks();
/* 413 */       aextendedblockstorage[i1] = extendedblockstorage;
/*     */     } 
/*     */     
/* 416 */     chunk.setStorageArrays(aextendedblockstorage);
/*     */     
/* 418 */     if (compound.hasKey("Biomes", 7))
/*     */     {
/* 420 */       chunk.setBiomeArray(compound.getByteArray("Biomes"));
/*     */     }
/*     */     
/* 423 */     NBTTagList nbttaglist1 = compound.getTagList("Entities", 10);
/*     */     
/* 425 */     for (int j1 = 0; j1 < nbttaglist1.tagCount(); j1++) {
/*     */       
/* 427 */       NBTTagCompound nbttagcompound1 = nbttaglist1.getCompoundTagAt(j1);
/* 428 */       readChunkEntity(nbttagcompound1, worldIn, chunk);
/* 429 */       chunk.setHasEntities(true);
/*     */     } 
/*     */     
/* 432 */     NBTTagList nbttaglist2 = compound.getTagList("TileEntities", 10);
/*     */     
/* 434 */     for (int k1 = 0; k1 < nbttaglist2.tagCount(); k1++) {
/*     */       
/* 436 */       NBTTagCompound nbttagcompound2 = nbttaglist2.getCompoundTagAt(k1);
/* 437 */       TileEntity tileentity = TileEntity.create(worldIn, nbttagcompound2);
/*     */       
/* 439 */       if (tileentity != null)
/*     */       {
/* 441 */         chunk.addTileEntity(tileentity);
/*     */       }
/*     */     } 
/*     */     
/* 445 */     if (compound.hasKey("TileTicks", 9)) {
/*     */       
/* 447 */       NBTTagList nbttaglist3 = compound.getTagList("TileTicks", 10);
/*     */       
/* 449 */       for (int l1 = 0; l1 < nbttaglist3.tagCount(); l1++) {
/*     */         Block block;
/* 451 */         NBTTagCompound nbttagcompound3 = nbttaglist3.getCompoundTagAt(l1);
/*     */ 
/*     */         
/* 454 */         if (nbttagcompound3.hasKey("i", 8)) {
/*     */           
/* 456 */           block = Block.getBlockFromName(nbttagcompound3.getString("i"));
/*     */         }
/*     */         else {
/*     */           
/* 460 */           block = Block.getBlockById(nbttagcompound3.getInteger("i"));
/*     */         } 
/*     */         
/* 463 */         worldIn.scheduleBlockUpdate(new BlockPos(nbttagcompound3.getInteger("x"), nbttagcompound3.getInteger("y"), nbttagcompound3.getInteger("z")), block, nbttagcompound3.getInteger("t"), nbttagcompound3.getInteger("p"));
/*     */       } 
/*     */     } 
/*     */     
/* 467 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Entity readChunkEntity(NBTTagCompound compound, World worldIn, Chunk chunkIn) {
/* 473 */     Entity entity = createEntityFromNBT(compound, worldIn);
/*     */     
/* 475 */     if (entity == null)
/*     */     {
/* 477 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 481 */     chunkIn.addEntity(entity);
/*     */     
/* 483 */     if (compound.hasKey("Passengers", 9)) {
/*     */       
/* 485 */       NBTTagList nbttaglist = compound.getTagList("Passengers", 10);
/*     */       
/* 487 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/* 489 */         Entity entity1 = readChunkEntity(nbttaglist.getCompoundTagAt(i), worldIn, chunkIn);
/*     */         
/* 491 */         if (entity1 != null)
/*     */         {
/* 493 */           entity1.startRiding(entity, true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 498 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Entity readWorldEntityPos(NBTTagCompound compound, World worldIn, double x, double y, double z, boolean attemptSpawn) {
/* 505 */     Entity entity = createEntityFromNBT(compound, worldIn);
/*     */     
/* 507 */     if (entity == null)
/*     */     {
/* 509 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 513 */     entity.setLocationAndAngles(x, y, z, entity.rotationYaw, entity.rotationPitch);
/*     */     
/* 515 */     if (attemptSpawn && !worldIn.spawnEntityInWorld(entity))
/*     */     {
/* 517 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 521 */     if (compound.hasKey("Passengers", 9)) {
/*     */       
/* 523 */       NBTTagList nbttaglist = compound.getTagList("Passengers", 10);
/*     */       
/* 525 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/* 527 */         Entity entity1 = readWorldEntityPos(nbttaglist.getCompoundTagAt(i), worldIn, x, y, z, attemptSpawn);
/*     */         
/* 529 */         if (entity1 != null)
/*     */         {
/* 531 */           entity1.startRiding(entity, true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 536 */     return entity;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected static Entity createEntityFromNBT(NBTTagCompound compound, World worldIn) {
/*     */     try {
/* 546 */       return EntityList.createEntityFromNBT(compound, worldIn);
/*     */     }
/* 548 */     catch (RuntimeException var3) {
/*     */       
/* 550 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void spawnEntity(Entity entityIn, World worldIn) {
/* 556 */     if (worldIn.spawnEntityInWorld(entityIn) && entityIn.isBeingRidden())
/*     */     {
/* 558 */       for (Entity entity : entityIn.getPassengers())
/*     */       {
/* 560 */         spawnEntity(entity, worldIn);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Entity readWorldEntity(NBTTagCompound compound, World worldIn, boolean p_186051_2_) {
/* 568 */     Entity entity = createEntityFromNBT(compound, worldIn);
/*     */     
/* 570 */     if (entity == null)
/*     */     {
/* 572 */       return null;
/*     */     }
/* 574 */     if (p_186051_2_ && !worldIn.spawnEntityInWorld(entity))
/*     */     {
/* 576 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 580 */     if (compound.hasKey("Passengers", 9)) {
/*     */       
/* 582 */       NBTTagList nbttaglist = compound.getTagList("Passengers", 10);
/*     */       
/* 584 */       for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */         
/* 586 */         Entity entity1 = readWorldEntity(nbttaglist.getCompoundTagAt(i), worldIn, p_186051_2_);
/*     */         
/* 588 */         if (entity1 != null)
/*     */         {
/* 590 */           entity1.startRiding(entity, true);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 595 */     return entity;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\chunk\storage\AnvilChunkLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */