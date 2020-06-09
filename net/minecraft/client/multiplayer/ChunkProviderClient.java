/*     */ package net.minecraft.client.multiplayer;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.EmptyChunk;
/*     */ import net.minecraft.world.chunk.IChunkProvider;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class ChunkProviderClient
/*     */   implements IChunkProvider {
/*  18 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final Chunk blankChunk;
/*     */ 
/*     */ 
/*     */   
/*  25 */   private final Long2ObjectMap<Chunk> chunkMapping = (Long2ObjectMap<Chunk>)new Long2ObjectOpenHashMap<Chunk>(8192)
/*     */     {
/*     */       protected void rehash(int p_rehash_1_)
/*     */       {
/*  29 */         if (p_rehash_1_ > this.key.length)
/*     */         {
/*  31 */           super.rehash(p_rehash_1_);
/*     */         }
/*     */       }
/*     */     };
/*     */ 
/*     */   
/*     */   private final World worldObj;
/*     */ 
/*     */   
/*     */   public ChunkProviderClient(World worldIn) {
/*  41 */     this.blankChunk = (Chunk)new EmptyChunk(worldIn, 0, 0);
/*  42 */     this.worldObj = worldIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unloadChunk(int x, int z) {
/*  51 */     Chunk chunk = provideChunk(x, z);
/*     */     
/*  53 */     if (!chunk.isEmpty())
/*     */     {
/*  55 */       chunk.onChunkUnload();
/*     */     }
/*     */     
/*  58 */     this.chunkMapping.remove(ChunkPos.asLong(x, z));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public Chunk getLoadedChunk(int x, int z) {
/*  64 */     return (Chunk)this.chunkMapping.get(ChunkPos.asLong(x, z));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Chunk loadChunk(int chunkX, int chunkZ) {
/*  72 */     Chunk chunk = new Chunk(this.worldObj, chunkX, chunkZ);
/*  73 */     this.chunkMapping.put(ChunkPos.asLong(chunkX, chunkZ), chunk);
/*  74 */     chunk.setChunkLoaded(true);
/*  75 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/*  80 */     return (Chunk)MoreObjects.firstNonNull(getLoadedChunk(x, z), this.blankChunk);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean unloadQueuedChunks() {
/*  88 */     long i = System.currentTimeMillis();
/*  89 */     ObjectIterator objectiterator = this.chunkMapping.values().iterator();
/*     */     
/*  91 */     while (objectiterator.hasNext()) {
/*     */       
/*  93 */       Chunk chunk = (Chunk)objectiterator.next();
/*  94 */       chunk.onTick((System.currentTimeMillis() - i > 5L));
/*     */     } 
/*     */     
/*  97 */     if (System.currentTimeMillis() - i > 100L)
/*     */     {
/*  99 */       LOGGER.info("Warning: Clientside chunk ticking took {} ms", Long.valueOf(System.currentTimeMillis() - i));
/*     */     }
/*     */     
/* 102 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String makeString() {
/* 110 */     return "MultiplayerChunkCache: " + this.chunkMapping.size() + ", " + this.chunkMapping.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191062_e(int p_191062_1_, int p_191062_2_) {
/* 115 */     return this.chunkMapping.containsKey(ChunkPos.asLong(p_191062_1_, p_191062_2_));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\multiplayer\ChunkProviderClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */