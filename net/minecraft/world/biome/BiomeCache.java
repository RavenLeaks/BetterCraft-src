/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BiomeCache
/*     */ {
/*     */   private final BiomeProvider chunkManager;
/*     */   private long lastCleanupTime;
/*  16 */   private final Long2ObjectMap<Block> cacheMap = (Long2ObjectMap<Block>)new Long2ObjectOpenHashMap(4096);
/*  17 */   private final List<Block> cache = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public BiomeCache(BiomeProvider chunkManagerIn) {
/*  21 */     this.chunkManager = chunkManagerIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getBiomeCacheBlock(int x, int z) {
/*  29 */     x >>= 4;
/*  30 */     z >>= 4;
/*  31 */     long i = x & 0xFFFFFFFFL | (z & 0xFFFFFFFFL) << 32L;
/*  32 */     Block biomecache$block = (Block)this.cacheMap.get(i);
/*     */     
/*  34 */     if (biomecache$block == null) {
/*     */       
/*  36 */       biomecache$block = new Block(x, z);
/*  37 */       this.cacheMap.put(i, biomecache$block);
/*  38 */       this.cache.add(biomecache$block);
/*     */     } 
/*     */     
/*  41 */     biomecache$block.lastAccessTime = MinecraftServer.getCurrentTimeMillis();
/*  42 */     return biomecache$block;
/*     */   }
/*     */ 
/*     */   
/*     */   public Biome getBiome(int x, int z, Biome defaultValue) {
/*  47 */     Biome biome = getBiomeCacheBlock(x, z).getBiome(x, z);
/*  48 */     return (biome == null) ? defaultValue : biome;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanupCache() {
/*  56 */     long i = MinecraftServer.getCurrentTimeMillis();
/*  57 */     long j = i - this.lastCleanupTime;
/*     */     
/*  59 */     if (j > 7500L || j < 0L) {
/*     */       
/*  61 */       this.lastCleanupTime = i;
/*     */       
/*  63 */       for (int k = 0; k < this.cache.size(); k++) {
/*     */         
/*  65 */         Block biomecache$block = this.cache.get(k);
/*  66 */         long l = i - biomecache$block.lastAccessTime;
/*     */         
/*  68 */         if (l > 30000L || l < 0L) {
/*     */           
/*  70 */           this.cache.remove(k--);
/*  71 */           long i1 = biomecache$block.xPosition & 0xFFFFFFFFL | (biomecache$block.zPosition & 0xFFFFFFFFL) << 32L;
/*  72 */           this.cacheMap.remove(i1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Biome[] getCachedBiomes(int x, int z) {
/*  83 */     return (getBiomeCacheBlock(x, z)).biomes;
/*     */   }
/*     */   
/*     */   public class Block
/*     */   {
/*  88 */     public Biome[] biomes = new Biome[256];
/*     */     
/*     */     public int xPosition;
/*     */     public int zPosition;
/*     */     public long lastAccessTime;
/*     */     
/*     */     public Block(int x, int z) {
/*  95 */       this.xPosition = x;
/*  96 */       this.zPosition = z;
/*  97 */       BiomeCache.this.chunkManager.getBiomes(this.biomes, x << 4, z << 4, 16, 16, false);
/*     */     }
/*     */ 
/*     */     
/*     */     public Biome getBiome(int x, int z) {
/* 102 */       return this.biomes[x & 0xF | (z & 0xF) << 4];
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */