/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import com.google.common.cache.CacheBuilder;
/*    */ import com.google.common.cache.CacheLoader;
/*    */ import com.google.common.cache.LoadingCache;
/*    */ import com.google.common.collect.ContiguousSet;
/*    */ import com.google.common.collect.DiscreteDomain;
/*    */ import com.google.common.collect.Lists;
/*    */ import com.google.common.collect.Range;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.gen.feature.WorldGenSpikes;
/*    */ 
/*    */ public class BiomeEndDecorator
/*    */   extends BiomeDecorator {
/*    */   public BiomeEndDecorator() {
/* 21 */     this.spikeGen = new WorldGenSpikes();
/*    */   }
/*    */   private static final LoadingCache<Long, WorldGenSpikes.EndSpike[]> SPIKE_CACHE = CacheBuilder.newBuilder().expireAfterWrite(5L, TimeUnit.MINUTES).build(new SpikeCacheLoader(null));
/*    */   protected void genDecorations(Biome biomeIn, World worldIn, Random random) {
/* 25 */     generateOres(worldIn, random);
/* 26 */     WorldGenSpikes.EndSpike[] aworldgenspikes$endspike = getSpikesForWorld(worldIn); byte b; int i;
/*    */     WorldGenSpikes.EndSpike[] arrayOfEndSpike1;
/* 28 */     for (i = (arrayOfEndSpike1 = aworldgenspikes$endspike).length, b = 0; b < i; ) { WorldGenSpikes.EndSpike worldgenspikes$endspike = arrayOfEndSpike1[b];
/*    */       
/* 30 */       if (worldgenspikes$endspike.doesStartInChunk(this.chunkPos)) {
/*    */         
/* 32 */         this.spikeGen.setSpike(worldgenspikes$endspike);
/* 33 */         this.spikeGen.generate(worldIn, random, new BlockPos(worldgenspikes$endspike.getCenterX(), 45, worldgenspikes$endspike.getCenterZ()));
/*    */       } 
/*    */       b++; }
/*    */   
/*    */   }
/*    */   private final WorldGenSpikes spikeGen;
/*    */   public static WorldGenSpikes.EndSpike[] getSpikesForWorld(World p_185426_0_) {
/* 40 */     Random random = new Random(p_185426_0_.getSeed());
/* 41 */     long i = random.nextLong() & 0xFFFFL;
/* 42 */     return (WorldGenSpikes.EndSpike[])SPIKE_CACHE.getUnchecked(Long.valueOf(i));
/*    */   }
/*    */ 
/*    */   
/*    */   static class SpikeCacheLoader
/*    */     extends CacheLoader<Long, WorldGenSpikes.EndSpike[]>
/*    */   {
/*    */     private SpikeCacheLoader() {}
/*    */ 
/*    */     
/*    */     public WorldGenSpikes.EndSpike[] load(Long p_load_1_) throws Exception {
/* 53 */       List<Integer> list = Lists.newArrayList((Iterable)ContiguousSet.create(Range.closedOpen(Integer.valueOf(0), Integer.valueOf(10)), DiscreteDomain.integers()));
/* 54 */       Collections.shuffle(list, new Random(p_load_1_.longValue()));
/* 55 */       WorldGenSpikes.EndSpike[] aworldgenspikes$endspike = new WorldGenSpikes.EndSpike[10];
/*    */       
/* 57 */       for (int i = 0; i < 10; i++) {
/*    */         
/* 59 */         int j = (int)(42.0D * Math.cos(2.0D * (-3.141592653589793D + 0.3141592653589793D * i)));
/* 60 */         int k = (int)(42.0D * Math.sin(2.0D * (-3.141592653589793D + 0.3141592653589793D * i)));
/* 61 */         int l = ((Integer)list.get(i)).intValue();
/* 62 */         int i1 = 2 + l / 3;
/* 63 */         int j1 = 76 + l * 3;
/* 64 */         boolean flag = !(l != 1 && l != 2);
/* 65 */         aworldgenspikes$endspike[i] = new WorldGenSpikes.EndSpike(j, k, i1, j1, flag);
/*    */       } 
/*    */       
/* 68 */       return aworldgenspikes$endspike;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeEndDecorator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */