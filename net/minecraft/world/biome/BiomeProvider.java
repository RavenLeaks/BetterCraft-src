/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.gen.ChunkGeneratorSettings;
/*     */ import net.minecraft.world.gen.layer.GenLayer;
/*     */ import net.minecraft.world.gen.layer.IntCache;
/*     */ import net.minecraft.world.storage.WorldInfo;
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
/*     */ public class BiomeProvider
/*     */ {
/*     */   private ChunkGeneratorSettings field_190945_a;
/*     */   private GenLayer genBiomes;
/*     */   private GenLayer biomeIndexLayer;
/*  32 */   private final BiomeCache biomeCache = new BiomeCache(this);
/*  33 */   private final List<Biome> biomesToSpawnIn = Lists.newArrayList((Object[])new Biome[] { Biomes.FOREST, Biomes.PLAINS, Biomes.TAIGA, Biomes.TAIGA_HILLS, Biomes.FOREST_HILLS, Biomes.JUNGLE, Biomes.JUNGLE_HILLS });
/*     */   
/*     */   protected BiomeProvider() {}
/*     */   
/*     */   private BiomeProvider(long seed, WorldType worldTypeIn, String options) {
/*  38 */     this();
/*     */     
/*  40 */     if (worldTypeIn == WorldType.CUSTOMIZED && !options.isEmpty())
/*     */     {
/*  42 */       this.field_190945_a = ChunkGeneratorSettings.Factory.jsonToFactory(options).build();
/*     */     }
/*     */     
/*  45 */     GenLayer[] agenlayer = GenLayer.initializeAllBiomeGenerators(seed, worldTypeIn, this.field_190945_a);
/*  46 */     this.genBiomes = agenlayer[0];
/*  47 */     this.biomeIndexLayer = agenlayer[1];
/*     */   }
/*     */ 
/*     */   
/*     */   public BiomeProvider(WorldInfo info) {
/*  52 */     this(info.getSeed(), info.getTerrainType(), info.getGeneratorOptions());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Biome> getBiomesToSpawnIn() {
/*  57 */     return this.biomesToSpawnIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Biome getBiome(BlockPos pos) {
/*  65 */     return getBiome(pos, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Biome getBiome(BlockPos pos, Biome defaultBiome) {
/*  70 */     return this.biomeCache.getBiome(pos.getX(), pos.getZ(), defaultBiome);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getTemperatureAtHeight(float p_76939_1_, int p_76939_2_) {
/*  78 */     return p_76939_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height) {
/*  86 */     IntCache.resetIntCache();
/*     */     
/*  88 */     if (biomes == null || biomes.length < width * height)
/*     */     {
/*  90 */       biomes = new Biome[width * height];
/*     */     }
/*     */     
/*  93 */     int[] aint = this.genBiomes.getInts(x, z, width, height);
/*     */ 
/*     */     
/*     */     try {
/*  97 */       for (int i = 0; i < width * height; i++)
/*     */       {
/*  99 */         biomes[i] = Biome.getBiome(aint[i], Biomes.DEFAULT);
/*     */       }
/*     */       
/* 102 */       return biomes;
/*     */     }
/* 104 */     catch (Throwable throwable) {
/*     */       
/* 106 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
/* 107 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("RawBiomeBlock");
/* 108 */       crashreportcategory.addCrashSection("biomes[] size", Integer.valueOf(biomes.length));
/* 109 */       crashreportcategory.addCrashSection("x", Integer.valueOf(x));
/* 110 */       crashreportcategory.addCrashSection("z", Integer.valueOf(z));
/* 111 */       crashreportcategory.addCrashSection("w", Integer.valueOf(width));
/* 112 */       crashreportcategory.addCrashSection("h", Integer.valueOf(height));
/* 113 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Biome[] getBiomes(@Nullable Biome[] oldBiomeList, int x, int z, int width, int depth) {
/* 123 */     return getBiomes(oldBiomeList, x, z, width, depth, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
/* 131 */     IntCache.resetIntCache();
/*     */     
/* 133 */     if (listToReuse == null || listToReuse.length < width * length)
/*     */     {
/* 135 */       listToReuse = new Biome[width * length];
/*     */     }
/*     */     
/* 138 */     if (cacheFlag && width == 16 && length == 16 && (x & 0xF) == 0 && (z & 0xF) == 0) {
/*     */       
/* 140 */       Biome[] abiome = this.biomeCache.getCachedBiomes(x, z);
/* 141 */       System.arraycopy(abiome, 0, listToReuse, 0, width * length);
/* 142 */       return listToReuse;
/*     */     } 
/*     */ 
/*     */     
/* 146 */     int[] aint = this.biomeIndexLayer.getInts(x, z, width, length);
/*     */     
/* 148 */     for (int i = 0; i < width * length; i++)
/*     */     {
/* 150 */       listToReuse[i] = Biome.getBiome(aint[i], Biomes.DEFAULT);
/*     */     }
/*     */     
/* 153 */     return listToReuse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed) {
/* 162 */     IntCache.resetIntCache();
/* 163 */     int i = x - radius >> 2;
/* 164 */     int j = z - radius >> 2;
/* 165 */     int k = x + radius >> 2;
/* 166 */     int l = z + radius >> 2;
/* 167 */     int i1 = k - i + 1;
/* 168 */     int j1 = l - j + 1;
/* 169 */     int[] aint = this.genBiomes.getInts(i, j, i1, j1);
/*     */ 
/*     */     
/*     */     try {
/* 173 */       for (int k1 = 0; k1 < i1 * j1; k1++) {
/*     */         
/* 175 */         Biome biome = Biome.getBiome(aint[k1]);
/*     */         
/* 177 */         if (!allowed.contains(biome))
/*     */         {
/* 179 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 183 */       return true;
/*     */     }
/* 185 */     catch (Throwable throwable) {
/*     */       
/* 187 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Invalid Biome id");
/* 188 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Layer");
/* 189 */       crashreportcategory.addCrashSection("Layer", this.genBiomes.toString());
/* 190 */       crashreportcategory.addCrashSection("x", Integer.valueOf(x));
/* 191 */       crashreportcategory.addCrashSection("z", Integer.valueOf(z));
/* 192 */       crashreportcategory.addCrashSection("radius", Integer.valueOf(radius));
/* 193 */       crashreportcategory.addCrashSection("allowed", allowed);
/* 194 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random) {
/* 201 */     IntCache.resetIntCache();
/* 202 */     int i = x - range >> 2;
/* 203 */     int j = z - range >> 2;
/* 204 */     int k = x + range >> 2;
/* 205 */     int l = z + range >> 2;
/* 206 */     int i1 = k - i + 1;
/* 207 */     int j1 = l - j + 1;
/* 208 */     int[] aint = this.genBiomes.getInts(i, j, i1, j1);
/* 209 */     BlockPos blockpos = null;
/* 210 */     int k1 = 0;
/*     */     
/* 212 */     for (int l1 = 0; l1 < i1 * j1; l1++) {
/*     */       
/* 214 */       int i2 = i + l1 % i1 << 2;
/* 215 */       int j2 = j + l1 / i1 << 2;
/* 216 */       Biome biome = Biome.getBiome(aint[l1]);
/*     */       
/* 218 */       if (biomes.contains(biome) && (blockpos == null || random.nextInt(k1 + 1) == 0)) {
/*     */         
/* 220 */         blockpos = new BlockPos(i2, 0, j2);
/* 221 */         k1++;
/*     */       } 
/*     */     } 
/*     */     
/* 225 */     return blockpos;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanupCache() {
/* 233 */     this.biomeCache.cleanupCache();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190944_c() {
/* 238 */     return (this.field_190945_a != null && this.field_190945_a.fixedBiome >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public Biome func_190943_d() {
/* 243 */     return (this.field_190945_a != null && this.field_190945_a.fixedBiome >= 0) ? Biome.getBiomeForId(this.field_190945_a.fixedBiome) : null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */