/*     */ package wdl;
/*     */ 
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.network.datasync.EntityDataManager;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.ClassInheritanceMultiMap;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.MinecraftException;
/*     */ import net.minecraft.world.NextTickListEntry;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.NibbleArray;
/*     */ import net.minecraft.world.chunk.storage.AnvilChunkLoader;
/*     */ import net.minecraft.world.chunk.storage.ExtendedBlockStorage;
/*     */ import net.minecraft.world.chunk.storage.RegionFileCache;
/*     */ import net.minecraft.world.storage.SaveHandler;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import wdl.api.IEntityEditor;
/*     */ import wdl.api.ITileEntityEditor;
/*     */ import wdl.api.ITileEntityImportationIdentifier;
/*     */ import wdl.api.WDLApi;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WDLChunkLoader
/*     */   extends AnvilChunkLoader
/*     */ {
/*  61 */   private static Logger logger = LogManager.getLogger();
/*     */   private final File chunkSaveLocation;
/*     */   
/*     */   public static WDLChunkLoader create(SaveHandler handler, WorldProvider provider) {
/*  65 */     return new WDLChunkLoader(getWorldSaveFolder(handler, provider));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static File getWorldSaveFolder(SaveHandler handler, WorldProvider provider) {
/*  74 */     File baseFolder = handler.getWorldDirectory();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/*  82 */       Method forgeGetSaveFolderMethod = provider.getClass().getMethod(
/*  83 */           "getSaveFolder", new Class[0]);
/*     */       
/*  85 */       String name = (String)forgeGetSaveFolderMethod.invoke(provider, new Object[0]);
/*  86 */       if (name != null) {
/*  87 */         File file = new File(baseFolder, name);
/*  88 */         file.mkdirs();
/*  89 */         return file;
/*     */       } 
/*  91 */       return baseFolder;
/*  92 */     } catch (Exception e) {
/*     */ 
/*     */ 
/*     */       
/*  96 */       if (provider instanceof net.minecraft.world.WorldProviderHell) {
/*  97 */         File file = new File(baseFolder, "DIM-1");
/*  98 */         file.mkdirs();
/*  99 */         return file;
/* 100 */       }  if (provider instanceof net.minecraft.world.WorldProviderEnd) {
/* 101 */         File file = new File(baseFolder, "DIM1");
/* 102 */         file.mkdirs();
/* 103 */         return file;
/*     */       } 
/*     */       
/* 106 */       return baseFolder;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WDLChunkLoader(File file) {
/* 113 */     super(file, null);
/* 114 */     this.chunkSaveLocation = file;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveChunk(World world, Chunk chunk) throws MinecraftException, IOException {
/* 126 */     world.checkSessionLock();
/*     */     
/* 128 */     NBTTagCompound levelTag = writeChunkToNBT(chunk, world);
/*     */     
/* 130 */     NBTTagCompound rootTag = new NBTTagCompound();
/* 131 */     rootTag.setTag("Level", (NBTBase)levelTag);
/* 132 */     rootTag.setInteger("DataVersion", 510);
/*     */     
/* 134 */     addChunkToPending(chunk.getChunkCoordIntPair(), rootTag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private NBTTagCompound writeChunkToNBT(Chunk chunk, World world) {
/* 154 */     NBTTagCompound compound = new NBTTagCompound();
/*     */     
/* 156 */     compound.setByte("V", (byte)1);
/* 157 */     compound.setInteger("xPos", chunk.xPosition);
/* 158 */     compound.setInteger("zPos", chunk.zPosition);
/* 159 */     compound.setLong("LastUpdate", world.getTotalWorldTime());
/* 160 */     compound.setIntArray("HeightMap", chunk.getHeightMap());
/* 161 */     compound.setBoolean("TerrainPopulated", chunk.isTerrainPopulated());
/* 162 */     compound.setBoolean("LightPopulated", chunk.isLightPopulated());
/* 163 */     compound.setLong("InhabitedTime", chunk.getInhabitedTime());
/* 164 */     ExtendedBlockStorage[] blockStorageArray = chunk.getBlockStorageArray();
/* 165 */     NBTTagList blockStorageList = new NBTTagList();
/* 166 */     boolean hasNoSky = !world.provider.getHasNoSky(); byte b; int i;
/*     */     ExtendedBlockStorage[] arrayOfExtendedBlockStorage1;
/* 168 */     for (i = (arrayOfExtendedBlockStorage1 = blockStorageArray).length, b = 0; b < i; ) { ExtendedBlockStorage blockStorage = arrayOfExtendedBlockStorage1[b];
/* 169 */       if (blockStorage != null) {
/* 170 */         NBTTagCompound blockData = new NBTTagCompound();
/* 171 */         blockData.setByte("Y", 
/* 172 */             (byte)(blockStorage.getYLocation() >> 4 & 0xFF));
/* 173 */         byte[] buffer = new byte[4096];
/* 174 */         NibbleArray nibblearray = new NibbleArray();
/* 175 */         NibbleArray nibblearray1 = blockStorage.getData()
/* 176 */           .getDataForNBT(buffer, nibblearray);
/* 177 */         blockData.setByteArray("Blocks", buffer);
/* 178 */         blockData.setByteArray("Data", nibblearray.getData());
/*     */         
/* 180 */         if (nibblearray1 != null) {
/* 181 */           blockData.setByteArray("Add", nibblearray1.getData());
/*     */         }
/*     */         
/* 184 */         blockData.setByteArray("BlockLight", blockStorage
/* 185 */             .getBlocklightArray().getData());
/*     */         
/* 187 */         if (hasNoSky) {
/* 188 */           blockData.setByteArray("SkyLight", blockStorage
/* 189 */               .getSkylightArray().getData());
/*     */         } else {
/* 191 */           blockData.setByteArray("SkyLight", 
/* 192 */               new byte[(blockStorage.getBlocklightArray().getData()).length]);
/*     */         } 
/*     */         
/* 195 */         blockStorageList.appendTag((NBTBase)blockData);
/*     */       } 
/*     */       b++; }
/*     */     
/* 199 */     compound.setTag("Sections", (NBTBase)blockStorageList);
/* 200 */     compound.setByteArray("Biomes", chunk.getBiomeArray());
/* 201 */     chunk.setHasEntities(false);
/*     */     
/* 203 */     NBTTagList entityList = getEntityList(chunk);
/* 204 */     compound.setTag("Entities", (NBTBase)entityList);
/*     */     
/* 206 */     NBTTagList tileEntityList = getTileEntityList(chunk);
/* 207 */     compound.setTag("TileEntities", (NBTBase)tileEntityList);
/* 208 */     List<NextTickListEntry> updateList = world.getPendingBlockUpdates(
/* 209 */         chunk, false);
/*     */     
/* 211 */     if (updateList != null) {
/* 212 */       long worldTime = world.getTotalWorldTime();
/* 213 */       NBTTagList entries = new NBTTagList();
/*     */       
/* 215 */       for (NextTickListEntry entry : updateList) {
/* 216 */         NBTTagCompound entryTag = new NBTTagCompound();
/* 217 */         ResourceLocation location = (ResourceLocation)Block.REGISTRY
/* 218 */           .getNameForObject(entry.getBlock());
/* 219 */         entryTag.setString("i", 
/* 220 */             (location == null) ? "" : location.toString());
/* 221 */         entryTag.setInteger("x", entry.position.getX());
/* 222 */         entryTag.setInteger("y", entry.position.getY());
/* 223 */         entryTag.setInteger("z", entry.position.getZ());
/* 224 */         entryTag.setInteger("t", 
/* 225 */             (int)(entry.scheduledTime - worldTime));
/* 226 */         entryTag.setInteger("p", entry.priority);
/* 227 */         entries.appendTag((NBTBase)entryTag);
/*     */       } 
/*     */       
/* 230 */       compound.setTag("TileTicks", (NBTBase)entries);
/*     */     } 
/*     */     
/* 233 */     return compound;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagList getEntityList(Chunk chunk) {
/* 242 */     NBTTagList entityList = new NBTTagList();
/*     */     
/* 244 */     if (!WDLPluginChannels.canSaveEntities(chunk)) {
/* 245 */       return entityList;
/*     */     }
/*     */ 
/*     */     
/* 249 */     List<Entity> entities = new ArrayList<>(); byte b; int i;
/*     */     ClassInheritanceMultiMap[] arrayOfClassInheritanceMultiMap;
/* 251 */     for (i = (arrayOfClassInheritanceMultiMap = chunk.getEntityLists()).length, b = 0; b < i; ) { ClassInheritanceMultiMap<Entity> map = arrayOfClassInheritanceMultiMap[b];
/* 252 */       entities.addAll((Collection<? extends Entity>)map);
/*     */       b++; }
/*     */     
/* 255 */     for (Entity e : WDL.newEntities.get(chunk.getChunkCoordIntPair())) {
/*     */       
/* 257 */       e.isDead = false;
/* 258 */       entities.add(e);
/*     */     } 
/*     */     
/* 261 */     for (Entity entity : entities) {
/* 262 */       if (entity == null) {
/* 263 */         logger.warn("[WDL] Null entity in chunk at " + 
/* 264 */             chunk.getChunkCoordIntPair());
/*     */         
/*     */         continue;
/*     */       } 
/* 268 */       if (!shouldSaveEntity(entity)) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 274 */       Iterator<WDLApi.ModInfo<IEntityEditor>> iterator = WDLApi.getImplementingExtensions(IEntityEditor.class).iterator(); while (iterator.hasNext()) { WDLApi.ModInfo<IEntityEditor> info = iterator.next();
/*     */         try {
/* 276 */           if (((IEntityEditor)info.mod).shouldEdit(entity)) {
/* 277 */             ((IEntityEditor)info.mod).editEntity(entity);
/*     */           }
/* 279 */         } catch (Exception ex) {
/* 280 */           throw new RuntimeException("Failed to edit entity " + 
/* 281 */               entity + " for chunk at " + 
/* 282 */               chunk.getChunkCoordIntPair() + " with extension " + 
/* 283 */               info, ex);
/*     */         }  }
/*     */ 
/*     */       
/* 287 */       NBTTagCompound entityData = new NBTTagCompound();
/*     */       
/*     */       try {
/* 290 */         if (entity.writeToNBTOptional(entityData)) {
/* 291 */           chunk.setHasEntities(true);
/* 292 */           entityList.appendTag((NBTBase)entityData);
/*     */         } 
/* 294 */       } catch (Exception e) {
/* 295 */         WDLMessages.chatMessageTranslated(
/* 296 */             WDLMessageTypes.ERROR, 
/* 297 */             "wdl.messages.generalError.failedToSaveEntity", new Object[] {
/* 298 */               entity, Integer.valueOf(chunk.xPosition), Integer.valueOf(chunk.zPosition), e });
/* 299 */         logger.warn("Compound: " + entityData);
/* 300 */         logger.warn("Entity metadata dump:");
/*     */         try {
/* 302 */           List<EntityDataManager.DataEntry<?>> objects = entity
/* 303 */             .getDataManager().getAll();
/* 304 */           if (objects == null) {
/* 305 */             logger.warn("No entries (getAllWatched() returned null)");
/*     */           } else {
/* 307 */             logger.warn(objects);
/* 308 */             for (EntityDataManager.DataEntry<?> obj : objects) {
/* 309 */               if (obj != null) {
/* 310 */                 logger.warn("DataEntry [getValue()=" + 
/* 311 */                     obj.getValue() + 
/* 312 */                     ", isDirty()=" + 
/* 313 */                     obj.isDirty() + 
/* 314 */                     ", getKey()=" + 
/* 315 */                     "DataParameter [" + 
/* 316 */                     "getId()=" + 
/* 317 */                     obj.getKey().getId() + 
/* 318 */                     ", getSerializer()=" + 
/* 319 */                     obj.getKey().getSerializer() + "]]");
/*     */               }
/*     */             } 
/*     */           } 
/* 323 */         } catch (Exception e2) {
/* 324 */           logger.warn("Failed to complete dump: ", e);
/*     */         } 
/* 326 */         logger.warn("End entity metadata dump");
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 331 */     return entityList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean shouldSaveEntity(Entity e) {
/* 343 */     if (e instanceof net.minecraft.entity.player.EntityPlayer)
/*     */     {
/* 345 */       return false;
/*     */     }
/*     */     
/* 348 */     if (!EntityUtils.isEntityEnabled(e)) {
/* 349 */       WDLMessages.chatMessageTranslated(
/* 350 */           WDLMessageTypes.REMOVE_ENTITY, 
/* 351 */           "wdl.messages.removeEntity.notSavingUserPreference", new Object[] {
/* 352 */             e });
/* 353 */       return false;
/*     */     } 
/*     */     
/* 356 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagList getTileEntityList(Chunk chunk) {
/* 364 */     NBTTagList tileEntityList = new NBTTagList();
/*     */     
/* 366 */     if (!WDLPluginChannels.canSaveTileEntities(chunk)) {
/* 367 */       return tileEntityList;
/*     */     }
/*     */     
/* 370 */     Map<BlockPos, TileEntity> chunkTEMap = chunk.getTileEntityMap();
/* 371 */     Map<BlockPos, NBTTagCompound> oldTEMap = getOldTileEntities(chunk);
/* 372 */     Map<BlockPos, TileEntity> newTEMap = WDL.newTileEntities.get(chunk.getChunkCoordIntPair());
/* 373 */     if (newTEMap == null) {
/* 374 */       newTEMap = new HashMap<>();
/*     */     }
/*     */ 
/*     */     
/* 378 */     Set<BlockPos> allTELocations = new HashSet<>();
/* 379 */     allTELocations.addAll(chunkTEMap.keySet());
/* 380 */     allTELocations.addAll(oldTEMap.keySet());
/* 381 */     allTELocations.addAll(newTEMap.keySet());
/*     */     
/* 383 */     for (BlockPos pos : allTELocations) {
/*     */ 
/*     */       
/* 386 */       if (newTEMap.containsKey(pos)) {
/* 387 */         NBTTagCompound compound = new NBTTagCompound();
/*     */         
/* 389 */         TileEntity te = newTEMap.get(pos);
/*     */         try {
/* 391 */           te.writeToNBT(compound);
/* 392 */         } catch (Exception e) {
/* 393 */           WDLMessages.chatMessageTranslated(
/* 394 */               WDLMessageTypes.ERROR, 
/* 395 */               "wdl.messages.generalError.failedToSaveTE", new Object[] {
/* 396 */                 te, pos, Integer.valueOf(chunk.xPosition), Integer.valueOf(chunk.zPosition), e });
/* 397 */           logger.warn("Compound: " + compound);
/*     */           
/*     */           continue;
/*     */         } 
/* 401 */         String entityType = String.valueOf(compound.getString("id")) + 
/* 402 */           " (" + te.getClass().getCanonicalName() + ")";
/* 403 */         WDLMessages.chatMessageTranslated(
/* 404 */             WDLMessageTypes.LOAD_TILE_ENTITY, 
/* 405 */             "wdl.messages.tileEntity.usingNew", new Object[] {
/* 406 */               entityType, pos
/*     */             });
/* 408 */         editTileEntity(pos, compound, ITileEntityEditor.TileEntityCreationMode.NEW);
/*     */         
/* 410 */         tileEntityList.appendTag((NBTBase)compound); continue;
/* 411 */       }  if (oldTEMap.containsKey(pos)) {
/* 412 */         NBTTagCompound compound = oldTEMap.get(pos);
/* 413 */         String entityType = compound.getString("id");
/* 414 */         WDLMessages.chatMessageTranslated(
/* 415 */             WDLMessageTypes.LOAD_TILE_ENTITY, 
/* 416 */             "wdl.messages.tileEntity.usingOld", new Object[] {
/* 417 */               entityType, pos
/*     */             });
/* 419 */         editTileEntity(pos, compound, ITileEntityEditor.TileEntityCreationMode.IMPORTED);
/*     */         
/* 421 */         tileEntityList.appendTag((NBTBase)compound); continue;
/* 422 */       }  if (chunkTEMap.containsKey(pos)) {
/*     */ 
/*     */         
/* 425 */         TileEntity te = chunkTEMap.get(pos);
/* 426 */         NBTTagCompound compound = new NBTTagCompound();
/*     */         try {
/* 428 */           te.writeToNBT(compound);
/* 429 */         } catch (Exception e) {
/* 430 */           WDLMessages.chatMessageTranslated(
/* 431 */               WDLMessageTypes.ERROR, 
/* 432 */               "wdl.messages.generalError.failedToSaveTE", new Object[] {
/* 433 */                 te, pos, Integer.valueOf(chunk.xPosition), Integer.valueOf(chunk.zPosition), e });
/* 434 */           logger.warn("Compound: " + compound);
/*     */           
/*     */           continue;
/*     */         } 
/* 438 */         editTileEntity(pos, compound, ITileEntityEditor.TileEntityCreationMode.EXISTING);
/*     */         
/* 440 */         tileEntityList.appendTag((NBTBase)compound);
/*     */       } 
/*     */     } 
/*     */     
/* 444 */     return tileEntityList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<BlockPos, NBTTagCompound> getOldTileEntities(Chunk chunk) {
/* 460 */     DataInputStream dis = null;
/* 461 */     Map<BlockPos, NBTTagCompound> returned = new HashMap<>();
/*     */ 
/*     */     
/* 464 */     try { dis = RegionFileCache.getChunkInputStream(this.chunkSaveLocation, 
/* 465 */           chunk.xPosition, chunk.zPosition);
/*     */       
/* 467 */       if (dis == null)
/*     */       {
/*     */         
/* 470 */         return returned;
/*     */       }
/*     */       
/* 473 */       NBTTagCompound chunkNBT = CompressedStreamTools.read(dis);
/* 474 */       NBTTagCompound levelNBT = chunkNBT.getCompoundTag("Level");
/* 475 */       NBTTagList oldList = levelNBT.getTagList("TileEntities", 10);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */        }
/*     */     
/* 499 */     catch (Exception e)
/* 500 */     { WDLMessages.chatMessageTranslated(WDLMessageTypes.ERROR, 
/* 501 */           "wdl.messages.generalError.failedToImportTE", new Object[] {
/* 502 */             Integer.valueOf(chunk.xPosition), Integer.valueOf(chunk.zPosition), e }); }
/*     */     finally
/* 504 */     { if (dis != null)
/*     */         
/* 506 */         try { dis.close(); }
/* 507 */         catch (Exception e)
/* 508 */         { throw new RuntimeException(e); }   }  if (dis != null) try { dis.close(); } catch (Exception e) { throw new RuntimeException(e); }
/*     */     
/*     */ 
/*     */     
/* 512 */     return returned;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldImportTileEntity(String entityID, BlockPos pos, Block block, NBTTagCompound tileEntityNBT, Chunk chunk) {
/* 538 */     if (block instanceof net.minecraft.block.BlockChest && entityID.equals("Chest"))
/* 539 */       return true; 
/* 540 */     if (block instanceof net.minecraft.block.BlockDispenser && entityID.equals("Trap"))
/* 541 */       return true; 
/* 542 */     if (block instanceof net.minecraft.block.BlockDropper && entityID.equals("Dropper"))
/* 543 */       return true; 
/* 544 */     if (block instanceof net.minecraft.block.BlockFurnace && entityID.equals("Furnace"))
/* 545 */       return true; 
/* 546 */     if (block instanceof net.minecraft.block.BlockNote && entityID.equals("Music"))
/* 547 */       return true; 
/* 548 */     if (block instanceof net.minecraft.block.BlockBrewingStand && 
/* 549 */       entityID.equals("Cauldron"))
/* 550 */       return true; 
/* 551 */     if (block instanceof net.minecraft.block.BlockHopper && entityID.equals("Hopper"))
/* 552 */       return true; 
/* 553 */     if (block instanceof net.minecraft.block.BlockBeacon && entityID.equals("Beacon")) {
/* 554 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 558 */     Iterator<WDLApi.ModInfo<ITileEntityImportationIdentifier>> iterator = WDLApi.getImplementingExtensions(ITileEntityImportationIdentifier.class).iterator(); while (iterator.hasNext()) { WDLApi.ModInfo<ITileEntityImportationIdentifier> info = iterator.next();
/* 559 */       if (((ITileEntityImportationIdentifier)info.mod).shouldImportTileEntity(entityID, pos, block, 
/* 560 */           tileEntityNBT, chunk)) {
/* 561 */         return true;
/*     */       } }
/*     */ 
/*     */     
/* 565 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void editTileEntity(BlockPos pos, NBTTagCompound compound, ITileEntityEditor.TileEntityCreationMode creationMode) {
/* 574 */     Iterator<WDLApi.ModInfo<ITileEntityEditor>> iterator = WDLApi.getImplementingExtensions(ITileEntityEditor.class).iterator(); while (iterator.hasNext()) { WDLApi.ModInfo<ITileEntityEditor> info = iterator.next();
/*     */       try {
/* 576 */         if (((ITileEntityEditor)info.mod).shouldEdit(pos, compound, creationMode)) {
/* 577 */           ((ITileEntityEditor)info.mod).editTileEntity(pos, compound, creationMode);
/*     */           
/* 579 */           WDLMessages.chatMessageTranslated(
/* 580 */               WDLMessageTypes.LOAD_TILE_ENTITY, 
/* 581 */               "wdl.messages.tileEntity.edited", new Object[] {
/* 582 */                 pos, info.getDisplayName() });
/*     */         } 
/* 584 */       } catch (Exception ex) {
/* 585 */         throw new RuntimeException("Failed to edit tile entity at " + 
/* 586 */             pos + " with extension " + info + 
/* 587 */             "; NBT is now " + compound + " (this may be the " + 
/* 588 */             "initial value, an edited value, or a partially " + 
/* 589 */             "edited value)", ex);
/*     */       }  }
/*     */   
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\WDLChunkLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */