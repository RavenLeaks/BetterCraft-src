/*     */ package net.minecraft.world.gen.layer;
/*     */ 
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.gen.ChunkGeneratorSettings;
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
/*     */ public abstract class GenLayer
/*     */ {
/*     */   private long worldGenSeed;
/*     */   protected GenLayer parent;
/*     */   private long chunkSeed;
/*     */   protected long baseSeed;
/*     */   
/*     */   public static GenLayer[] initializeAllBiomeGenerators(long seed, WorldType p_180781_2_, ChunkGeneratorSettings p_180781_3_) {
/*  27 */     GenLayer genlayer = new GenLayerIsland(1L);
/*  28 */     genlayer = new GenLayerFuzzyZoom(2000L, genlayer);
/*  29 */     GenLayer genlayeraddisland = new GenLayerAddIsland(1L, genlayer);
/*  30 */     GenLayer genlayerzoom = new GenLayerZoom(2001L, genlayeraddisland);
/*  31 */     GenLayer genlayeraddisland1 = new GenLayerAddIsland(2L, genlayerzoom);
/*  32 */     genlayeraddisland1 = new GenLayerAddIsland(50L, genlayeraddisland1);
/*  33 */     genlayeraddisland1 = new GenLayerAddIsland(70L, genlayeraddisland1);
/*  34 */     GenLayer genlayerremovetoomuchocean = new GenLayerRemoveTooMuchOcean(2L, genlayeraddisland1);
/*  35 */     GenLayer genlayeraddsnow = new GenLayerAddSnow(2L, genlayerremovetoomuchocean);
/*  36 */     GenLayer genlayeraddisland2 = new GenLayerAddIsland(3L, genlayeraddsnow);
/*  37 */     GenLayer genlayeredge = new GenLayerEdge(2L, genlayeraddisland2, GenLayerEdge.Mode.COOL_WARM);
/*  38 */     genlayeredge = new GenLayerEdge(2L, genlayeredge, GenLayerEdge.Mode.HEAT_ICE);
/*  39 */     genlayeredge = new GenLayerEdge(3L, genlayeredge, GenLayerEdge.Mode.SPECIAL);
/*  40 */     GenLayer genlayerzoom1 = new GenLayerZoom(2002L, genlayeredge);
/*  41 */     genlayerzoom1 = new GenLayerZoom(2003L, genlayerzoom1);
/*  42 */     GenLayer genlayeraddisland3 = new GenLayerAddIsland(4L, genlayerzoom1);
/*  43 */     GenLayer genlayeraddmushroomisland = new GenLayerAddMushroomIsland(5L, genlayeraddisland3);
/*  44 */     GenLayer genlayerdeepocean = new GenLayerDeepOcean(4L, genlayeraddmushroomisland);
/*  45 */     GenLayer genlayer4 = GenLayerZoom.magnify(1000L, genlayerdeepocean, 0);
/*  46 */     int i = 4;
/*  47 */     int j = i;
/*     */     
/*  49 */     if (p_180781_3_ != null) {
/*     */       
/*  51 */       i = p_180781_3_.biomeSize;
/*  52 */       j = p_180781_3_.riverSize;
/*     */     } 
/*     */     
/*  55 */     if (p_180781_2_ == WorldType.LARGE_BIOMES)
/*     */     {
/*  57 */       i = 6;
/*     */     }
/*     */     
/*  60 */     GenLayer lvt_7_1_ = GenLayerZoom.magnify(1000L, genlayer4, 0);
/*  61 */     GenLayer genlayerriverinit = new GenLayerRiverInit(100L, lvt_7_1_);
/*  62 */     GenLayer lvt_8_1_ = new GenLayerBiome(200L, genlayer4, p_180781_2_, p_180781_3_);
/*  63 */     GenLayer genlayer6 = GenLayerZoom.magnify(1000L, lvt_8_1_, 2);
/*  64 */     GenLayer genlayerbiomeedge = new GenLayerBiomeEdge(1000L, genlayer6);
/*  65 */     GenLayer lvt_9_1_ = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
/*  66 */     GenLayer genlayerhills = new GenLayerHills(1000L, genlayerbiomeedge, lvt_9_1_);
/*  67 */     GenLayer genlayer5 = GenLayerZoom.magnify(1000L, genlayerriverinit, 2);
/*  68 */     genlayer5 = GenLayerZoom.magnify(1000L, genlayer5, j);
/*  69 */     GenLayer genlayerriver = new GenLayerRiver(1L, genlayer5);
/*  70 */     GenLayer genlayersmooth = new GenLayerSmooth(1000L, genlayerriver);
/*  71 */     genlayerhills = new GenLayerRareBiome(1001L, genlayerhills);
/*     */     
/*  73 */     for (int k = 0; k < i; k++) {
/*     */       
/*  75 */       genlayerhills = new GenLayerZoom((1000 + k), genlayerhills);
/*     */       
/*  77 */       if (k == 0)
/*     */       {
/*  79 */         genlayerhills = new GenLayerAddIsland(3L, genlayerhills);
/*     */       }
/*     */       
/*  82 */       if (k == 1 || i == 1)
/*     */       {
/*  84 */         genlayerhills = new GenLayerShore(1000L, genlayerhills);
/*     */       }
/*     */     } 
/*     */     
/*  88 */     GenLayer genlayersmooth1 = new GenLayerSmooth(1000L, genlayerhills);
/*  89 */     GenLayer genlayerrivermix = new GenLayerRiverMix(100L, genlayersmooth1, genlayersmooth);
/*  90 */     GenLayer genlayer3 = new GenLayerVoronoiZoom(10L, genlayerrivermix);
/*  91 */     genlayerrivermix.initWorldGenSeed(seed);
/*  92 */     genlayer3.initWorldGenSeed(seed);
/*  93 */     return new GenLayer[] { genlayerrivermix, genlayer3, genlayerrivermix };
/*     */   }
/*     */ 
/*     */   
/*     */   public GenLayer(long p_i2125_1_) {
/*  98 */     this.baseSeed = p_i2125_1_;
/*  99 */     this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
/* 100 */     this.baseSeed += p_i2125_1_;
/* 101 */     this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
/* 102 */     this.baseSeed += p_i2125_1_;
/* 103 */     this.baseSeed *= this.baseSeed * 6364136223846793005L + 1442695040888963407L;
/* 104 */     this.baseSeed += p_i2125_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initWorldGenSeed(long seed) {
/* 113 */     this.worldGenSeed = seed;
/*     */     
/* 115 */     if (this.parent != null)
/*     */     {
/* 117 */       this.parent.initWorldGenSeed(seed);
/*     */     }
/*     */     
/* 120 */     this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
/* 121 */     this.worldGenSeed += this.baseSeed;
/* 122 */     this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
/* 123 */     this.worldGenSeed += this.baseSeed;
/* 124 */     this.worldGenSeed *= this.worldGenSeed * 6364136223846793005L + 1442695040888963407L;
/* 125 */     this.worldGenSeed += this.baseSeed;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initChunkSeed(long p_75903_1_, long p_75903_3_) {
/* 133 */     this.chunkSeed = this.worldGenSeed;
/* 134 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 135 */     this.chunkSeed += p_75903_1_;
/* 136 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 137 */     this.chunkSeed += p_75903_3_;
/* 138 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 139 */     this.chunkSeed += p_75903_1_;
/* 140 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 141 */     this.chunkSeed += p_75903_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int nextInt(int p_75902_1_) {
/* 149 */     int i = (int)((this.chunkSeed >> 24L) % p_75902_1_);
/*     */     
/* 151 */     if (i < 0)
/*     */     {
/* 153 */       i += p_75902_1_;
/*     */     }
/*     */     
/* 156 */     this.chunkSeed *= this.chunkSeed * 6364136223846793005L + 1442695040888963407L;
/* 157 */     this.chunkSeed += this.worldGenSeed;
/* 158 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract int[] getInts(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean biomesEqualOrMesaPlateau(int biomeIDA, int biomeIDB) {
/* 169 */     if (biomeIDA == biomeIDB)
/*     */     {
/* 171 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 175 */     Biome biome = Biome.getBiome(biomeIDA);
/* 176 */     Biome biome1 = Biome.getBiome(biomeIDB);
/*     */     
/* 178 */     if (biome != null && biome1 != null) {
/*     */       
/* 180 */       if (biome != Biomes.MESA_ROCK && biome != Biomes.MESA_CLEAR_ROCK)
/*     */       {
/* 182 */         return !(biome != biome1 && biome.getBiomeClass() != biome1.getBiomeClass());
/*     */       }
/*     */ 
/*     */       
/* 186 */       return !(biome1 != Biomes.MESA_ROCK && biome1 != Biomes.MESA_CLEAR_ROCK);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 191 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isBiomeOceanic(int p_151618_0_) {
/* 201 */     Biome biome = Biome.getBiome(p_151618_0_);
/* 202 */     return !(biome != Biomes.OCEAN && biome != Biomes.DEEP_OCEAN && biome != Biomes.FROZEN_OCEAN);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int selectRandom(int... p_151619_1_) {
/* 210 */     return p_151619_1_[nextInt(p_151619_1_.length)];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int selectModeOrRandom(int p_151617_1_, int p_151617_2_, int p_151617_3_, int p_151617_4_) {
/* 218 */     if (p_151617_2_ == p_151617_3_ && p_151617_3_ == p_151617_4_)
/*     */     {
/* 220 */       return p_151617_2_;
/*     */     }
/* 222 */     if (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_3_)
/*     */     {
/* 224 */       return p_151617_1_;
/*     */     }
/* 226 */     if (p_151617_1_ == p_151617_2_ && p_151617_1_ == p_151617_4_)
/*     */     {
/* 228 */       return p_151617_1_;
/*     */     }
/* 230 */     if (p_151617_1_ == p_151617_3_ && p_151617_1_ == p_151617_4_)
/*     */     {
/* 232 */       return p_151617_1_;
/*     */     }
/* 234 */     if (p_151617_1_ == p_151617_2_ && p_151617_3_ != p_151617_4_)
/*     */     {
/* 236 */       return p_151617_1_;
/*     */     }
/* 238 */     if (p_151617_1_ == p_151617_3_ && p_151617_2_ != p_151617_4_)
/*     */     {
/* 240 */       return p_151617_1_;
/*     */     }
/* 242 */     if (p_151617_1_ == p_151617_4_ && p_151617_2_ != p_151617_3_)
/*     */     {
/* 244 */       return p_151617_1_;
/*     */     }
/* 246 */     if (p_151617_2_ == p_151617_3_ && p_151617_1_ != p_151617_4_)
/*     */     {
/* 248 */       return p_151617_2_;
/*     */     }
/* 250 */     if (p_151617_2_ == p_151617_4_ && p_151617_1_ != p_151617_3_)
/*     */     {
/* 252 */       return p_151617_2_;
/*     */     }
/*     */ 
/*     */     
/* 256 */     return (p_151617_3_ == p_151617_4_ && p_151617_1_ != p_151617_2_) ? p_151617_3_ : selectRandom(new int[] { p_151617_1_, p_151617_2_, p_151617_3_, p_151617_4_ });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\layer\GenLayer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */