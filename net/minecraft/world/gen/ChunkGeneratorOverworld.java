/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFalling;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.WorldEntitySpawner;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.feature.WorldGenDungeons;
/*     */ import net.minecraft.world.gen.feature.WorldGenLakes;
/*     */ import net.minecraft.world.gen.structure.MapGenMineshaft;
/*     */ import net.minecraft.world.gen.structure.MapGenScatteredFeature;
/*     */ import net.minecraft.world.gen.structure.MapGenStronghold;
/*     */ import net.minecraft.world.gen.structure.MapGenVillage;
/*     */ import net.minecraft.world.gen.structure.StructureOceanMonument;
/*     */ import net.minecraft.world.gen.structure.WoodlandMansion;
/*     */ 
/*     */ public class ChunkGeneratorOverworld implements IChunkGenerator {
/*  31 */   protected static final IBlockState STONE = Blocks.STONE.getDefaultState();
/*     */   private final Random rand;
/*     */   private final NoiseGeneratorOctaves minLimitPerlinNoise;
/*     */   private final NoiseGeneratorOctaves maxLimitPerlinNoise;
/*     */   private final NoiseGeneratorOctaves mainPerlinNoise;
/*     */   private final NoiseGeneratorPerlin surfaceNoise;
/*     */   public NoiseGeneratorOctaves scaleNoise;
/*     */   public NoiseGeneratorOctaves depthNoise;
/*     */   public NoiseGeneratorOctaves forestNoise;
/*     */   private final World worldObj;
/*     */   private final boolean mapFeaturesEnabled;
/*     */   private final WorldType terrainType;
/*     */   private final double[] heightMap;
/*     */   private final float[] biomeWeights;
/*     */   private ChunkGeneratorSettings settings;
/*  46 */   private IBlockState oceanBlock = Blocks.WATER.getDefaultState();
/*  47 */   private double[] depthBuffer = new double[256];
/*  48 */   private final MapGenBase caveGenerator = new MapGenCaves();
/*  49 */   private final MapGenStronghold strongholdGenerator = new MapGenStronghold();
/*  50 */   private final MapGenVillage villageGenerator = new MapGenVillage();
/*  51 */   private final MapGenMineshaft mineshaftGenerator = new MapGenMineshaft();
/*  52 */   private final MapGenScatteredFeature scatteredFeatureGenerator = new MapGenScatteredFeature();
/*  53 */   private final MapGenBase ravineGenerator = new MapGenRavine();
/*  54 */   private final StructureOceanMonument oceanMonumentGenerator = new StructureOceanMonument();
/*  55 */   private final WoodlandMansion field_191060_C = new WoodlandMansion(this);
/*     */   
/*     */   private Biome[] biomesForGeneration;
/*     */   double[] mainNoiseRegion;
/*     */   double[] minLimitRegion;
/*     */   double[] maxLimitRegion;
/*     */   double[] depthRegion;
/*     */   
/*     */   public ChunkGeneratorOverworld(World worldIn, long seed, boolean mapFeaturesEnabledIn, String p_i46668_5_) {
/*  64 */     this.worldObj = worldIn;
/*  65 */     this.mapFeaturesEnabled = mapFeaturesEnabledIn;
/*  66 */     this.terrainType = worldIn.getWorldInfo().getTerrainType();
/*  67 */     this.rand = new Random(seed);
/*  68 */     this.minLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
/*  69 */     this.maxLimitPerlinNoise = new NoiseGeneratorOctaves(this.rand, 16);
/*  70 */     this.mainPerlinNoise = new NoiseGeneratorOctaves(this.rand, 8);
/*  71 */     this.surfaceNoise = new NoiseGeneratorPerlin(this.rand, 4);
/*  72 */     this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
/*  73 */     this.depthNoise = new NoiseGeneratorOctaves(this.rand, 16);
/*  74 */     this.forestNoise = new NoiseGeneratorOctaves(this.rand, 8);
/*  75 */     this.heightMap = new double[825];
/*  76 */     this.biomeWeights = new float[25];
/*     */     
/*  78 */     for (int i = -2; i <= 2; i++) {
/*     */       
/*  80 */       for (int j = -2; j <= 2; j++) {
/*     */         
/*  82 */         float f = 10.0F / MathHelper.sqrt((i * i + j * j) + 0.2F);
/*  83 */         this.biomeWeights[i + 2 + (j + 2) * 5] = f;
/*     */       } 
/*     */     } 
/*     */     
/*  87 */     if (p_i46668_5_ != null) {
/*     */       
/*  89 */       this.settings = ChunkGeneratorSettings.Factory.jsonToFactory(p_i46668_5_).build();
/*  90 */       this.oceanBlock = this.settings.useLavaOceans ? Blocks.LAVA.getDefaultState() : Blocks.WATER.getDefaultState();
/*  91 */       worldIn.setSeaLevel(this.settings.seaLevel);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
/*  97 */     this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomesForGeneration(this.biomesForGeneration, x * 4 - 2, z * 4 - 2, 10, 10);
/*  98 */     generateHeightmap(x * 4, 0, z * 4);
/*     */     
/* 100 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 102 */       int j = i * 5;
/* 103 */       int k = (i + 1) * 5;
/*     */       
/* 105 */       for (int l = 0; l < 4; l++) {
/*     */         
/* 107 */         int i1 = (j + l) * 33;
/* 108 */         int j1 = (j + l + 1) * 33;
/* 109 */         int k1 = (k + l) * 33;
/* 110 */         int l1 = (k + l + 1) * 33;
/*     */         
/* 112 */         for (int i2 = 0; i2 < 32; i2++) {
/*     */           
/* 114 */           double d0 = 0.125D;
/* 115 */           double d1 = this.heightMap[i1 + i2];
/* 116 */           double d2 = this.heightMap[j1 + i2];
/* 117 */           double d3 = this.heightMap[k1 + i2];
/* 118 */           double d4 = this.heightMap[l1 + i2];
/* 119 */           double d5 = (this.heightMap[i1 + i2 + 1] - d1) * 0.125D;
/* 120 */           double d6 = (this.heightMap[j1 + i2 + 1] - d2) * 0.125D;
/* 121 */           double d7 = (this.heightMap[k1 + i2 + 1] - d3) * 0.125D;
/* 122 */           double d8 = (this.heightMap[l1 + i2 + 1] - d4) * 0.125D;
/*     */           
/* 124 */           for (int j2 = 0; j2 < 8; j2++) {
/*     */             
/* 126 */             double d9 = 0.25D;
/* 127 */             double d10 = d1;
/* 128 */             double d11 = d2;
/* 129 */             double d12 = (d3 - d1) * 0.25D;
/* 130 */             double d13 = (d4 - d2) * 0.25D;
/*     */             
/* 132 */             for (int k2 = 0; k2 < 4; k2++) {
/*     */               
/* 134 */               double d14 = 0.25D;
/* 135 */               double d16 = (d11 - d10) * 0.25D;
/* 136 */               double lvt_45_1_ = d10 - d16;
/*     */               
/* 138 */               for (int l2 = 0; l2 < 4; l2++) {
/*     */                 
/* 140 */                 if ((lvt_45_1_ += d16) > 0.0D) {
/*     */                   
/* 142 */                   primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, STONE);
/*     */                 }
/* 144 */                 else if (i2 * 8 + j2 < this.settings.seaLevel) {
/*     */                   
/* 146 */                   primer.setBlockState(i * 4 + k2, i2 * 8 + j2, l * 4 + l2, this.oceanBlock);
/*     */                 } 
/*     */               } 
/*     */               
/* 150 */               d10 += d12;
/* 151 */               d11 += d13;
/*     */             } 
/*     */             
/* 154 */             d1 += d5;
/* 155 */             d2 += d6;
/* 156 */             d3 += d7;
/* 157 */             d4 += d8;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void replaceBiomeBlocks(int x, int z, ChunkPrimer primer, Biome[] biomesIn) {
/* 166 */     double d0 = 0.03125D;
/* 167 */     this.depthBuffer = this.surfaceNoise.getRegion(this.depthBuffer, (x * 16), (z * 16), 16, 16, 0.0625D, 0.0625D, 1.0D);
/*     */     
/* 169 */     for (int i = 0; i < 16; i++) {
/*     */       
/* 171 */       for (int j = 0; j < 16; j++) {
/*     */         
/* 173 */         Biome biome = biomesIn[j + i * 16];
/* 174 */         biome.genTerrainBlocks(this.worldObj, this.rand, primer, x * 16 + i, z * 16 + j, this.depthBuffer[j + i * 16]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/* 181 */     this.rand.setSeed(x * 341873128712L + z * 132897987541L);
/* 182 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/* 183 */     setBlocksInChunk(x, z, chunkprimer);
/* 184 */     this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
/* 185 */     replaceBiomeBlocks(x, z, chunkprimer, this.biomesForGeneration);
/*     */     
/* 187 */     if (this.settings.useCaves)
/*     */     {
/* 189 */       this.caveGenerator.generate(this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 192 */     if (this.settings.useRavines)
/*     */     {
/* 194 */       this.ravineGenerator.generate(this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 197 */     if (this.mapFeaturesEnabled) {
/*     */       
/* 199 */       if (this.settings.useMineShafts)
/*     */       {
/* 201 */         this.mineshaftGenerator.generate(this.worldObj, x, z, chunkprimer);
/*     */       }
/*     */       
/* 204 */       if (this.settings.useVillages)
/*     */       {
/* 206 */         this.villageGenerator.generate(this.worldObj, x, z, chunkprimer);
/*     */       }
/*     */       
/* 209 */       if (this.settings.useStrongholds)
/*     */       {
/* 211 */         this.strongholdGenerator.generate(this.worldObj, x, z, chunkprimer);
/*     */       }
/*     */       
/* 214 */       if (this.settings.useTemples)
/*     */       {
/* 216 */         this.scatteredFeatureGenerator.generate(this.worldObj, x, z, chunkprimer);
/*     */       }
/*     */       
/* 219 */       if (this.settings.useMonuments)
/*     */       {
/* 221 */         this.oceanMonumentGenerator.generate(this.worldObj, x, z, chunkprimer);
/*     */       }
/*     */       
/* 224 */       if (this.settings.field_191077_z)
/*     */       {
/* 226 */         this.field_191060_C.generate(this.worldObj, x, z, chunkprimer);
/*     */       }
/*     */     } 
/*     */     
/* 230 */     Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
/* 231 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/* 233 */     for (int i = 0; i < abyte.length; i++)
/*     */     {
/* 235 */       abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
/*     */     }
/*     */     
/* 238 */     chunk.generateSkylightMap();
/* 239 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateHeightmap(int p_185978_1_, int p_185978_2_, int p_185978_3_) {
/* 244 */     this.depthRegion = this.depthNoise.generateNoiseOctaves(this.depthRegion, p_185978_1_, p_185978_3_, 5, 5, this.settings.depthNoiseScaleX, this.settings.depthNoiseScaleZ, this.settings.depthNoiseScaleExponent);
/* 245 */     float f = this.settings.coordinateScale;
/* 246 */     float f1 = this.settings.heightScale;
/* 247 */     this.mainNoiseRegion = this.mainPerlinNoise.generateNoiseOctaves(this.mainNoiseRegion, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, (f / this.settings.mainNoiseScaleX), (f1 / this.settings.mainNoiseScaleY), (f / this.settings.mainNoiseScaleZ));
/* 248 */     this.minLimitRegion = this.minLimitPerlinNoise.generateNoiseOctaves(this.minLimitRegion, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, f, f1, f);
/* 249 */     this.maxLimitRegion = this.maxLimitPerlinNoise.generateNoiseOctaves(this.maxLimitRegion, p_185978_1_, p_185978_2_, p_185978_3_, 5, 33, 5, f, f1, f);
/* 250 */     int i = 0;
/* 251 */     int j = 0;
/*     */     
/* 253 */     for (int k = 0; k < 5; k++) {
/*     */       
/* 255 */       for (int l = 0; l < 5; l++) {
/*     */         
/* 257 */         float f2 = 0.0F;
/* 258 */         float f3 = 0.0F;
/* 259 */         float f4 = 0.0F;
/* 260 */         int i1 = 2;
/* 261 */         Biome biome = this.biomesForGeneration[k + 2 + (l + 2) * 10];
/*     */         
/* 263 */         for (int j1 = -2; j1 <= 2; j1++) {
/*     */           
/* 265 */           for (int k1 = -2; k1 <= 2; k1++) {
/*     */             
/* 267 */             Biome biome1 = this.biomesForGeneration[k + j1 + 2 + (l + k1 + 2) * 10];
/* 268 */             float f5 = this.settings.biomeDepthOffSet + biome1.getBaseHeight() * this.settings.biomeDepthWeight;
/* 269 */             float f6 = this.settings.biomeScaleOffset + biome1.getHeightVariation() * this.settings.biomeScaleWeight;
/*     */             
/* 271 */             if (this.terrainType == WorldType.AMPLIFIED && f5 > 0.0F) {
/*     */               
/* 273 */               f5 = 1.0F + f5 * 2.0F;
/* 274 */               f6 = 1.0F + f6 * 4.0F;
/*     */             } 
/*     */             
/* 277 */             float f7 = this.biomeWeights[j1 + 2 + (k1 + 2) * 5] / (f5 + 2.0F);
/*     */             
/* 279 */             if (biome1.getBaseHeight() > biome.getBaseHeight())
/*     */             {
/* 281 */               f7 /= 2.0F;
/*     */             }
/*     */             
/* 284 */             f2 += f6 * f7;
/* 285 */             f3 += f5 * f7;
/* 286 */             f4 += f7;
/*     */           } 
/*     */         } 
/*     */         
/* 290 */         f2 /= f4;
/* 291 */         f3 /= f4;
/* 292 */         f2 = f2 * 0.9F + 0.1F;
/* 293 */         f3 = (f3 * 4.0F - 1.0F) / 8.0F;
/* 294 */         double d7 = this.depthRegion[j] / 8000.0D;
/*     */         
/* 296 */         if (d7 < 0.0D)
/*     */         {
/* 298 */           d7 = -d7 * 0.3D;
/*     */         }
/*     */         
/* 301 */         d7 = d7 * 3.0D - 2.0D;
/*     */         
/* 303 */         if (d7 < 0.0D) {
/*     */           
/* 305 */           d7 /= 2.0D;
/*     */           
/* 307 */           if (d7 < -1.0D)
/*     */           {
/* 309 */             d7 = -1.0D;
/*     */           }
/*     */           
/* 312 */           d7 /= 1.4D;
/* 313 */           d7 /= 2.0D;
/*     */         }
/*     */         else {
/*     */           
/* 317 */           if (d7 > 1.0D)
/*     */           {
/* 319 */             d7 = 1.0D;
/*     */           }
/*     */           
/* 322 */           d7 /= 8.0D;
/*     */         } 
/*     */         
/* 325 */         j++;
/* 326 */         double d8 = f3;
/* 327 */         double d9 = f2;
/* 328 */         d8 += d7 * 0.2D;
/* 329 */         d8 = d8 * this.settings.baseSize / 8.0D;
/* 330 */         double d0 = this.settings.baseSize + d8 * 4.0D;
/*     */         
/* 332 */         for (int l1 = 0; l1 < 33; l1++) {
/*     */           
/* 334 */           double d1 = (l1 - d0) * this.settings.stretchY * 128.0D / 256.0D / d9;
/*     */           
/* 336 */           if (d1 < 0.0D)
/*     */           {
/* 338 */             d1 *= 4.0D;
/*     */           }
/*     */           
/* 341 */           double d2 = this.minLimitRegion[i] / this.settings.lowerLimitScale;
/* 342 */           double d3 = this.maxLimitRegion[i] / this.settings.upperLimitScale;
/* 343 */           double d4 = (this.mainNoiseRegion[i] / 10.0D + 1.0D) / 2.0D;
/* 344 */           double d5 = MathHelper.clampedLerp(d2, d3, d4) - d1;
/*     */           
/* 346 */           if (l1 > 29) {
/*     */             
/* 348 */             double d6 = ((l1 - 29) / 3.0F);
/* 349 */             d5 = d5 * (1.0D - d6) + -10.0D * d6;
/*     */           } 
/*     */           
/* 352 */           this.heightMap[i] = d5;
/* 353 */           i++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void populate(int x, int z) {
/* 361 */     BlockFalling.fallInstantly = true;
/* 362 */     int i = x * 16;
/* 363 */     int j = z * 16;
/* 364 */     BlockPos blockpos = new BlockPos(i, 0, j);
/* 365 */     Biome biome = this.worldObj.getBiome(blockpos.add(16, 0, 16));
/* 366 */     this.rand.setSeed(this.worldObj.getSeed());
/* 367 */     long k = this.rand.nextLong() / 2L * 2L + 1L;
/* 368 */     long l = this.rand.nextLong() / 2L * 2L + 1L;
/* 369 */     this.rand.setSeed(x * k + z * l ^ this.worldObj.getSeed());
/* 370 */     boolean flag = false;
/* 371 */     ChunkPos chunkpos = new ChunkPos(x, z);
/*     */     
/* 373 */     if (this.mapFeaturesEnabled) {
/*     */       
/* 375 */       if (this.settings.useMineShafts)
/*     */       {
/* 377 */         this.mineshaftGenerator.generateStructure(this.worldObj, this.rand, chunkpos);
/*     */       }
/*     */       
/* 380 */       if (this.settings.useVillages)
/*     */       {
/* 382 */         flag = this.villageGenerator.generateStructure(this.worldObj, this.rand, chunkpos);
/*     */       }
/*     */       
/* 385 */       if (this.settings.useStrongholds)
/*     */       {
/* 387 */         this.strongholdGenerator.generateStructure(this.worldObj, this.rand, chunkpos);
/*     */       }
/*     */       
/* 390 */       if (this.settings.useTemples)
/*     */       {
/* 392 */         this.scatteredFeatureGenerator.generateStructure(this.worldObj, this.rand, chunkpos);
/*     */       }
/*     */       
/* 395 */       if (this.settings.useMonuments)
/*     */       {
/* 397 */         this.oceanMonumentGenerator.generateStructure(this.worldObj, this.rand, chunkpos);
/*     */       }
/*     */       
/* 400 */       if (this.settings.field_191077_z)
/*     */       {
/* 402 */         this.field_191060_C.generateStructure(this.worldObj, this.rand, chunkpos);
/*     */       }
/*     */     } 
/*     */     
/* 406 */     if (biome != Biomes.DESERT && biome != Biomes.DESERT_HILLS && this.settings.useWaterLakes && !flag && this.rand.nextInt(this.settings.waterLakeChance) == 0) {
/*     */       
/* 408 */       int i1 = this.rand.nextInt(16) + 8;
/* 409 */       int j1 = this.rand.nextInt(256);
/* 410 */       int k1 = this.rand.nextInt(16) + 8;
/* 411 */       (new WorldGenLakes((Block)Blocks.WATER)).generate(this.worldObj, this.rand, blockpos.add(i1, j1, k1));
/*     */     } 
/*     */     
/* 414 */     if (!flag && this.rand.nextInt(this.settings.lavaLakeChance / 10) == 0 && this.settings.useLavaLakes) {
/*     */       
/* 416 */       int i2 = this.rand.nextInt(16) + 8;
/* 417 */       int l2 = this.rand.nextInt(this.rand.nextInt(248) + 8);
/* 418 */       int k3 = this.rand.nextInt(16) + 8;
/*     */       
/* 420 */       if (l2 < this.worldObj.getSeaLevel() || this.rand.nextInt(this.settings.lavaLakeChance / 8) == 0)
/*     */       {
/* 422 */         (new WorldGenLakes((Block)Blocks.LAVA)).generate(this.worldObj, this.rand, blockpos.add(i2, l2, k3));
/*     */       }
/*     */     } 
/*     */     
/* 426 */     if (this.settings.useDungeons)
/*     */     {
/* 428 */       for (int j2 = 0; j2 < this.settings.dungeonChance; j2++) {
/*     */         
/* 430 */         int i3 = this.rand.nextInt(16) + 8;
/* 431 */         int l3 = this.rand.nextInt(256);
/* 432 */         int l1 = this.rand.nextInt(16) + 8;
/* 433 */         (new WorldGenDungeons()).generate(this.worldObj, this.rand, blockpos.add(i3, l3, l1));
/*     */       } 
/*     */     }
/*     */     
/* 437 */     biome.decorate(this.worldObj, this.rand, new BlockPos(i, 0, j));
/* 438 */     WorldEntitySpawner.performWorldGenSpawning(this.worldObj, biome, i + 8, j + 8, 16, 16, this.rand);
/* 439 */     blockpos = blockpos.add(8, 0, 8);
/*     */     
/* 441 */     for (int k2 = 0; k2 < 16; k2++) {
/*     */       
/* 443 */       for (int j3 = 0; j3 < 16; j3++) {
/*     */         
/* 445 */         BlockPos blockpos1 = this.worldObj.getPrecipitationHeight(blockpos.add(k2, 0, j3));
/* 446 */         BlockPos blockpos2 = blockpos1.down();
/*     */         
/* 448 */         if (this.worldObj.canBlockFreezeWater(blockpos2))
/*     */         {
/* 450 */           this.worldObj.setBlockState(blockpos2, Blocks.ICE.getDefaultState(), 2);
/*     */         }
/*     */         
/* 453 */         if (this.worldObj.canSnowAt(blockpos1, true))
/*     */         {
/* 455 */           this.worldObj.setBlockState(blockpos1, Blocks.SNOW_LAYER.getDefaultState(), 2);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 460 */     BlockFalling.fallInstantly = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generateStructures(Chunk chunkIn, int x, int z) {
/* 465 */     boolean flag = false;
/*     */     
/* 467 */     if (this.settings.useMonuments && this.mapFeaturesEnabled && chunkIn.getInhabitedTime() < 3600L)
/*     */     {
/* 469 */       flag |= this.oceanMonumentGenerator.generateStructure(this.worldObj, this.rand, new ChunkPos(x, z));
/*     */     }
/*     */     
/* 472 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 477 */     Biome biome = this.worldObj.getBiome(pos);
/*     */     
/* 479 */     if (this.mapFeaturesEnabled) {
/*     */       
/* 481 */       if (creatureType == EnumCreatureType.MONSTER && this.scatteredFeatureGenerator.isSwampHut(pos))
/*     */       {
/* 483 */         return this.scatteredFeatureGenerator.getScatteredFeatureSpawnList();
/*     */       }
/*     */       
/* 486 */       if (creatureType == EnumCreatureType.MONSTER && this.settings.useMonuments && this.oceanMonumentGenerator.isPositionInStructure(this.worldObj, pos))
/*     */       {
/* 488 */         return this.oceanMonumentGenerator.getScatteredFeatureSpawnList();
/*     */       }
/*     */     } 
/*     */     
/* 492 */     return biome.getSpawnableList(creatureType);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193414_a(World p_193414_1_, String p_193414_2_, BlockPos p_193414_3_) {
/* 497 */     if (!this.mapFeaturesEnabled)
/*     */     {
/* 499 */       return false;
/*     */     }
/* 501 */     if ("Stronghold".equals(p_193414_2_) && this.strongholdGenerator != null)
/*     */     {
/* 503 */       return this.strongholdGenerator.isInsideStructure(p_193414_3_);
/*     */     }
/* 505 */     if ("Mansion".equals(p_193414_2_) && this.field_191060_C != null)
/*     */     {
/* 507 */       return this.field_191060_C.isInsideStructure(p_193414_3_);
/*     */     }
/* 509 */     if ("Monument".equals(p_193414_2_) && this.oceanMonumentGenerator != null)
/*     */     {
/* 511 */       return this.oceanMonumentGenerator.isInsideStructure(p_193414_3_);
/*     */     }
/* 513 */     if ("Village".equals(p_193414_2_) && this.villageGenerator != null)
/*     */     {
/* 515 */       return this.villageGenerator.isInsideStructure(p_193414_3_);
/*     */     }
/* 517 */     if ("Mineshaft".equals(p_193414_2_) && this.mineshaftGenerator != null)
/*     */     {
/* 519 */       return this.mineshaftGenerator.isInsideStructure(p_193414_3_);
/*     */     }
/*     */ 
/*     */     
/* 523 */     return ("Temple".equals(p_193414_2_) && this.scatteredFeatureGenerator != null) ? this.scatteredFeatureGenerator.isInsideStructure(p_193414_3_) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean p_180513_4_) {
/* 530 */     if (!this.mapFeaturesEnabled)
/*     */     {
/* 532 */       return null;
/*     */     }
/* 534 */     if ("Stronghold".equals(structureName) && this.strongholdGenerator != null)
/*     */     {
/* 536 */       return this.strongholdGenerator.getClosestStrongholdPos(worldIn, position, p_180513_4_);
/*     */     }
/* 538 */     if ("Mansion".equals(structureName) && this.field_191060_C != null)
/*     */     {
/* 540 */       return this.field_191060_C.getClosestStrongholdPos(worldIn, position, p_180513_4_);
/*     */     }
/* 542 */     if ("Monument".equals(structureName) && this.oceanMonumentGenerator != null)
/*     */     {
/* 544 */       return this.oceanMonumentGenerator.getClosestStrongholdPos(worldIn, position, p_180513_4_);
/*     */     }
/* 546 */     if ("Village".equals(structureName) && this.villageGenerator != null)
/*     */     {
/* 548 */       return this.villageGenerator.getClosestStrongholdPos(worldIn, position, p_180513_4_);
/*     */     }
/* 550 */     if ("Mineshaft".equals(structureName) && this.mineshaftGenerator != null)
/*     */     {
/* 552 */       return this.mineshaftGenerator.getClosestStrongholdPos(worldIn, position, p_180513_4_);
/*     */     }
/*     */ 
/*     */     
/* 556 */     return ("Temple".equals(structureName) && this.scatteredFeatureGenerator != null) ? this.scatteredFeatureGenerator.getClosestStrongholdPos(worldIn, position, p_180513_4_) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {
/* 562 */     if (this.mapFeaturesEnabled) {
/*     */       
/* 564 */       if (this.settings.useMineShafts)
/*     */       {
/* 566 */         this.mineshaftGenerator.generate(this.worldObj, x, z, null);
/*     */       }
/*     */       
/* 569 */       if (this.settings.useVillages)
/*     */       {
/* 571 */         this.villageGenerator.generate(this.worldObj, x, z, null);
/*     */       }
/*     */       
/* 574 */       if (this.settings.useStrongholds)
/*     */       {
/* 576 */         this.strongholdGenerator.generate(this.worldObj, x, z, null);
/*     */       }
/*     */       
/* 579 */       if (this.settings.useTemples)
/*     */       {
/* 581 */         this.scatteredFeatureGenerator.generate(this.worldObj, x, z, null);
/*     */       }
/*     */       
/* 584 */       if (this.settings.useMonuments)
/*     */       {
/* 586 */         this.oceanMonumentGenerator.generate(this.worldObj, x, z, null);
/*     */       }
/*     */       
/* 589 */       if (this.settings.field_191077_z)
/*     */       {
/* 591 */         this.field_191060_C.generate(this.worldObj, x, z, null);
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\ChunkGeneratorOverworld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */