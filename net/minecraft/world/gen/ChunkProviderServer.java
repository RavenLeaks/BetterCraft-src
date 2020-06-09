/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.world.MinecraftException;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldServer;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ChunkProviderServer
/*     */   implements IChunkProvider {
/*  32 */   private static final Logger LOGGER = LogManager.getLogger();
/*  33 */   private final Set<Long> droppedChunksSet = Sets.newHashSet();
/*     */   private final IChunkGenerator chunkGenerator;
/*     */   private final IChunkLoader chunkLoader;
/*  36 */   private final Long2ObjectMap<Chunk> id2ChunkMap = (Long2ObjectMap<Chunk>)new Long2ObjectOpenHashMap(8192);
/*     */   
/*     */   private final WorldServer worldObj;
/*     */   
/*     */   public ChunkProviderServer(WorldServer worldObjIn, IChunkLoader chunkLoaderIn, IChunkGenerator chunkGeneratorIn) {
/*  41 */     this.worldObj = worldObjIn;
/*  42 */     this.chunkLoader = chunkLoaderIn;
/*  43 */     this.chunkGenerator = chunkGeneratorIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Chunk> getLoadedChunks() {
/*  48 */     return (Collection<Chunk>)this.id2ChunkMap.values();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unload(Chunk chunkIn) {
/*  56 */     if (this.worldObj.provider.canDropChunk(chunkIn.xPosition, chunkIn.zPosition)) {
/*     */       
/*  58 */       this.droppedChunksSet.add(Long.valueOf(ChunkPos.asLong(chunkIn.xPosition, chunkIn.zPosition)));
/*  59 */       chunkIn.unloaded = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadAllChunks() {
/*  68 */     ObjectIterator objectiterator = this.id2ChunkMap.values().iterator();
/*     */     
/*  70 */     while (objectiterator.hasNext()) {
/*     */       
/*  72 */       Chunk chunk = (Chunk)objectiterator.next();
/*  73 */       unload(chunk);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Chunk getLoadedChunk(int x, int z) {
/*  80 */     long i = ChunkPos.asLong(x, z);
/*  81 */     Chunk chunk = (Chunk)this.id2ChunkMap.get(i);
/*     */     
/*  83 */     if (chunk != null)
/*     */     {
/*  85 */       chunk.unloaded = false;
/*     */     }
/*     */     
/*  88 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Chunk loadChunk(int x, int z) {
/*  94 */     Chunk chunk = getLoadedChunk(x, z);
/*     */     
/*  96 */     if (chunk == null) {
/*     */       
/*  98 */       chunk = loadChunkFromFile(x, z);
/*     */       
/* 100 */       if (chunk != null) {
/*     */         
/* 102 */         this.id2ChunkMap.put(ChunkPos.asLong(x, z), chunk);
/* 103 */         chunk.onChunkLoad();
/* 104 */         chunk.populateChunk(this, this.chunkGenerator);
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/* 113 */     Chunk chunk = loadChunk(x, z);
/*     */     
/* 115 */     if (chunk == null) {
/*     */       
/* 117 */       long i = ChunkPos.asLong(x, z);
/*     */ 
/*     */       
/*     */       try {
/* 121 */         chunk = this.chunkGenerator.provideChunk(x, z);
/*     */       }
/* 123 */       catch (Throwable throwable) {
/*     */         
/* 125 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception generating new chunk");
/* 126 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Chunk to be generated");
/* 127 */         crashreportcategory.addCrashSection("Location", String.format("%d,%d", new Object[] { Integer.valueOf(x), Integer.valueOf(z) }));
/* 128 */         crashreportcategory.addCrashSection("Position hash", Long.valueOf(i));
/* 129 */         crashreportcategory.addCrashSection("Generator", this.chunkGenerator);
/* 130 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */       
/* 133 */       this.id2ChunkMap.put(i, chunk);
/* 134 */       chunk.onChunkLoad();
/* 135 */       chunk.populateChunk(this, this.chunkGenerator);
/*     */     } 
/*     */     
/* 138 */     return chunk;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private Chunk loadChunkFromFile(int x, int z) {
/*     */     try {
/* 146 */       Chunk chunk = this.chunkLoader.loadChunk((World)this.worldObj, x, z);
/*     */       
/* 148 */       if (chunk != null) {
/*     */         
/* 150 */         chunk.setLastSaveTime(this.worldObj.getTotalWorldTime());
/* 151 */         this.chunkGenerator.recreateStructures(chunk, x, z);
/*     */       } 
/*     */       
/* 154 */       return chunk;
/*     */     }
/* 156 */     catch (Exception exception) {
/*     */       
/* 158 */       LOGGER.error("Couldn't load chunk", exception);
/* 159 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveChunkExtraData(Chunk chunkIn) {
/*     */     try {
/* 167 */       this.chunkLoader.saveExtraChunkData((World)this.worldObj, chunkIn);
/*     */     }
/* 169 */     catch (Exception exception) {
/*     */       
/* 171 */       LOGGER.error("Couldn't save entities", exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveChunkData(Chunk chunkIn) {
/*     */     try {
/* 179 */       chunkIn.setLastSaveTime(this.worldObj.getTotalWorldTime());
/* 180 */       this.chunkLoader.saveChunk((World)this.worldObj, chunkIn);
/*     */     }
/* 182 */     catch (IOException ioexception) {
/*     */       
/* 184 */       LOGGER.error("Couldn't save chunk", ioexception);
/*     */     }
/* 186 */     catch (MinecraftException minecraftexception) {
/*     */       
/* 188 */       LOGGER.error("Couldn't save chunk; already in use by another instance of Minecraft?", (Throwable)minecraftexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean saveChunks(boolean p_186027_1_) {
/* 194 */     int i = 0;
/* 195 */     List<Chunk> list = Lists.newArrayList((Iterable)this.id2ChunkMap.values());
/*     */     
/* 197 */     for (int j = 0; j < list.size(); j++) {
/*     */       
/* 199 */       Chunk chunk = list.get(j);
/*     */       
/* 201 */       if (p_186027_1_)
/*     */       {
/* 203 */         saveChunkExtraData(chunk);
/*     */       }
/*     */       
/* 206 */       if (chunk.needsSaving(p_186027_1_)) {
/*     */         
/* 208 */         saveChunkData(chunk);
/* 209 */         chunk.setModified(false);
/* 210 */         i++;
/*     */         
/* 212 */         if (i == 24 && !p_186027_1_)
/*     */         {
/* 214 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 219 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveExtraData() {
/* 228 */     this.chunkLoader.saveExtraData();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unloadQueuedChunks() {
/* 236 */     if (!this.worldObj.disableLevelSaving) {
/*     */       
/* 238 */       if (!this.droppedChunksSet.isEmpty()) {
/*     */         
/* 240 */         Iterator<Long> iterator = this.droppedChunksSet.iterator();
/*     */         
/* 242 */         for (int i = 0; i < 100 && iterator.hasNext(); iterator.remove()) {
/*     */           
/* 244 */           Long olong = iterator.next();
/* 245 */           Chunk chunk = (Chunk)this.id2ChunkMap.get(olong);
/*     */           
/* 247 */           if (chunk != null && chunk.unloaded) {
/*     */             
/* 249 */             chunk.onChunkUnload();
/* 250 */             saveChunkData(chunk);
/* 251 */             saveChunkExtraData(chunk);
/* 252 */             this.id2ChunkMap.remove(olong);
/* 253 */             i++;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 258 */       this.chunkLoader.chunkTick();
/*     */     } 
/*     */     
/* 261 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSave() {
/* 269 */     return !this.worldObj.disableLevelSaving;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 277 */     return "ServerChunkCache: " + this.id2ChunkMap.size() + " Drop: " + this.droppedChunksSet.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 282 */     return this.chunkGenerator.getPossibleCreatures(creatureType, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean p_180513_4_) {
/* 288 */     return this.chunkGenerator.getStrongholdGen(worldIn, structureName, position, p_180513_4_);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193413_a(World p_193413_1_, String p_193413_2_, BlockPos p_193413_3_) {
/* 293 */     return this.chunkGenerator.func_193414_a(p_193413_1_, p_193413_2_, p_193413_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLoadedChunkCount() {
/* 298 */     return this.id2ChunkMap.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean chunkExists(int x, int z) {
/* 306 */     return this.id2ChunkMap.containsKey(ChunkPos.asLong(x, z));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191062_e(int p_191062_1_, int p_191062_2_) {
/* 311 */     return !(!this.id2ChunkMap.containsKey(ChunkPos.asLong(p_191062_1_, p_191062_2_)) && !this.chunkLoader.func_191063_a(p_191062_1_, p_191062_2_));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\ChunkProviderServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */