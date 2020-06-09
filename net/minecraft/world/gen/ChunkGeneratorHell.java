/*     */ package net.minecraft.world.gen;
/*     */ import com.google.common.base.Predicate;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFalling;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockMatcher;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.feature.WorldGenBush;
/*     */ import net.minecraft.world.gen.feature.WorldGenFire;
/*     */ import net.minecraft.world.gen.feature.WorldGenGlowStone1;
/*     */ import net.minecraft.world.gen.feature.WorldGenGlowStone2;
/*     */ import net.minecraft.world.gen.feature.WorldGenHellLava;
/*     */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ import net.minecraft.world.gen.structure.MapGenNetherBridge;
/*     */ 
/*     */ public class ChunkGeneratorHell implements IChunkGenerator {
/*  30 */   protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
/*  31 */   protected static final IBlockState NETHERRACK = Blocks.NETHERRACK.getDefaultState();
/*  32 */   protected static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
/*  33 */   protected static final IBlockState LAVA = Blocks.LAVA.getDefaultState();
/*  34 */   protected static final IBlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
/*  35 */   protected static final IBlockState SOUL_SAND = Blocks.SOUL_SAND.getDefaultState();
/*     */   
/*     */   private final World world;
/*     */   
/*     */   private final boolean generateStructures;
/*     */   
/*     */   private final Random rand;
/*     */   
/*  43 */   private double[] slowsandNoise = new double[256];
/*  44 */   private double[] gravelNoise = new double[256];
/*  45 */   private double[] depthBuffer = new double[256];
/*     */   
/*     */   private double[] buffer;
/*     */   
/*     */   private final NoiseGeneratorOctaves lperlinNoise1;
/*     */   
/*     */   private final NoiseGeneratorOctaves lperlinNoise2;
/*     */   
/*     */   private final NoiseGeneratorOctaves perlinNoise1;
/*     */   
/*     */   private final NoiseGeneratorOctaves slowsandGravelNoiseGen;
/*     */   
/*     */   private final NoiseGeneratorOctaves netherrackExculsivityNoiseGen;
/*     */   public final NoiseGeneratorOctaves scaleNoise;
/*     */   public final NoiseGeneratorOctaves depthNoise;
/*  60 */   private final WorldGenFire fireFeature = new WorldGenFire();
/*  61 */   private final WorldGenGlowStone1 lightGemGen = new WorldGenGlowStone1();
/*  62 */   private final WorldGenGlowStone2 hellPortalGen = new WorldGenGlowStone2();
/*  63 */   private final WorldGenerator quartzGen = (WorldGenerator)new WorldGenMinable(Blocks.QUARTZ_ORE.getDefaultState(), 14, (Predicate)BlockMatcher.forBlock(Blocks.NETHERRACK));
/*  64 */   private final WorldGenerator magmaGen = (WorldGenerator)new WorldGenMinable(Blocks.MAGMA.getDefaultState(), 33, (Predicate)BlockMatcher.forBlock(Blocks.NETHERRACK));
/*  65 */   private final WorldGenHellLava lavaTrapGen = new WorldGenHellLava((Block)Blocks.FLOWING_LAVA, true);
/*  66 */   private final WorldGenHellLava hellSpringGen = new WorldGenHellLava((Block)Blocks.FLOWING_LAVA, false);
/*  67 */   private final WorldGenBush brownMushroomFeature = new WorldGenBush(Blocks.BROWN_MUSHROOM);
/*  68 */   private final WorldGenBush redMushroomFeature = new WorldGenBush(Blocks.RED_MUSHROOM);
/*  69 */   private final MapGenNetherBridge genNetherBridge = new MapGenNetherBridge();
/*  70 */   private final MapGenBase genNetherCaves = new MapGenCavesHell();
/*     */   
/*     */   double[] pnr;
/*     */   double[] ar;
/*     */   double[] br;
/*     */   double[] noiseData4;
/*     */   double[] dr;
/*     */   
/*     */   public ChunkGeneratorHell(World worldIn, boolean p_i45637_2_, long seed) {
/*  79 */     this.world = worldIn;
/*  80 */     this.generateStructures = p_i45637_2_;
/*  81 */     this.rand = new Random(seed);
/*  82 */     this.lperlinNoise1 = new NoiseGeneratorOctaves(this.rand, 16);
/*  83 */     this.lperlinNoise2 = new NoiseGeneratorOctaves(this.rand, 16);
/*  84 */     this.perlinNoise1 = new NoiseGeneratorOctaves(this.rand, 8);
/*  85 */     this.slowsandGravelNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
/*  86 */     this.netherrackExculsivityNoiseGen = new NoiseGeneratorOctaves(this.rand, 4);
/*  87 */     this.scaleNoise = new NoiseGeneratorOctaves(this.rand, 10);
/*  88 */     this.depthNoise = new NoiseGeneratorOctaves(this.rand, 16);
/*  89 */     worldIn.setSeaLevel(63);
/*     */   }
/*     */ 
/*     */   
/*     */   public void prepareHeights(int p_185936_1_, int p_185936_2_, ChunkPrimer primer) {
/*  94 */     int i = 4;
/*  95 */     int j = this.world.getSeaLevel() / 2 + 1;
/*  96 */     int k = 5;
/*  97 */     int l = 17;
/*  98 */     int i1 = 5;
/*  99 */     this.buffer = getHeights(this.buffer, p_185936_1_ * 4, 0, p_185936_2_ * 4, 5, 17, 5);
/*     */     
/* 101 */     for (int j1 = 0; j1 < 4; j1++) {
/*     */       
/* 103 */       for (int k1 = 0; k1 < 4; k1++) {
/*     */         
/* 105 */         for (int l1 = 0; l1 < 16; l1++) {
/*     */           
/* 107 */           double d0 = 0.125D;
/* 108 */           double d1 = this.buffer[((j1 + 0) * 5 + k1 + 0) * 17 + l1 + 0];
/* 109 */           double d2 = this.buffer[((j1 + 0) * 5 + k1 + 1) * 17 + l1 + 0];
/* 110 */           double d3 = this.buffer[((j1 + 1) * 5 + k1 + 0) * 17 + l1 + 0];
/* 111 */           double d4 = this.buffer[((j1 + 1) * 5 + k1 + 1) * 17 + l1 + 0];
/* 112 */           double d5 = (this.buffer[((j1 + 0) * 5 + k1 + 0) * 17 + l1 + 1] - d1) * 0.125D;
/* 113 */           double d6 = (this.buffer[((j1 + 0) * 5 + k1 + 1) * 17 + l1 + 1] - d2) * 0.125D;
/* 114 */           double d7 = (this.buffer[((j1 + 1) * 5 + k1 + 0) * 17 + l1 + 1] - d3) * 0.125D;
/* 115 */           double d8 = (this.buffer[((j1 + 1) * 5 + k1 + 1) * 17 + l1 + 1] - d4) * 0.125D;
/*     */           
/* 117 */           for (int i2 = 0; i2 < 8; i2++) {
/*     */             
/* 119 */             double d9 = 0.25D;
/* 120 */             double d10 = d1;
/* 121 */             double d11 = d2;
/* 122 */             double d12 = (d3 - d1) * 0.25D;
/* 123 */             double d13 = (d4 - d2) * 0.25D;
/*     */             
/* 125 */             for (int j2 = 0; j2 < 4; j2++) {
/*     */               
/* 127 */               double d14 = 0.25D;
/* 128 */               double d15 = d10;
/* 129 */               double d16 = (d11 - d10) * 0.25D;
/*     */               
/* 131 */               for (int k2 = 0; k2 < 4; k2++) {
/*     */                 
/* 133 */                 IBlockState iblockstate = null;
/*     */                 
/* 135 */                 if (l1 * 8 + i2 < j)
/*     */                 {
/* 137 */                   iblockstate = LAVA;
/*     */                 }
/*     */                 
/* 140 */                 if (d15 > 0.0D)
/*     */                 {
/* 142 */                   iblockstate = NETHERRACK;
/*     */                 }
/*     */                 
/* 145 */                 int l2 = j2 + j1 * 4;
/* 146 */                 int i3 = i2 + l1 * 8;
/* 147 */                 int j3 = k2 + k1 * 4;
/* 148 */                 primer.setBlockState(l2, i3, j3, iblockstate);
/* 149 */                 d15 += d16;
/*     */               } 
/*     */               
/* 152 */               d10 += d12;
/* 153 */               d11 += d13;
/*     */             } 
/*     */             
/* 156 */             d1 += d5;
/* 157 */             d2 += d6;
/* 158 */             d3 += d7;
/* 159 */             d4 += d8;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void buildSurfaces(int p_185937_1_, int p_185937_2_, ChunkPrimer primer) {
/* 168 */     int i = this.world.getSeaLevel() + 1;
/* 169 */     double d0 = 0.03125D;
/* 170 */     this.slowsandNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.slowsandNoise, p_185937_1_ * 16, p_185937_2_ * 16, 0, 16, 16, 1, 0.03125D, 0.03125D, 1.0D);
/* 171 */     this.gravelNoise = this.slowsandGravelNoiseGen.generateNoiseOctaves(this.gravelNoise, p_185937_1_ * 16, 109, p_185937_2_ * 16, 16, 1, 16, 0.03125D, 1.0D, 0.03125D);
/* 172 */     this.depthBuffer = this.netherrackExculsivityNoiseGen.generateNoiseOctaves(this.depthBuffer, p_185937_1_ * 16, p_185937_2_ * 16, 0, 16, 16, 1, 0.0625D, 0.0625D, 0.0625D);
/*     */     
/* 174 */     for (int j = 0; j < 16; j++) {
/*     */       
/* 176 */       for (int k = 0; k < 16; k++) {
/*     */         
/* 178 */         boolean flag = (this.slowsandNoise[j + k * 16] + this.rand.nextDouble() * 0.2D > 0.0D);
/* 179 */         boolean flag1 = (this.gravelNoise[j + k * 16] + this.rand.nextDouble() * 0.2D > 0.0D);
/* 180 */         int l = (int)(this.depthBuffer[j + k * 16] / 3.0D + 3.0D + this.rand.nextDouble() * 0.25D);
/* 181 */         int i1 = -1;
/* 182 */         IBlockState iblockstate = NETHERRACK;
/* 183 */         IBlockState iblockstate1 = NETHERRACK;
/*     */         
/* 185 */         for (int j1 = 127; j1 >= 0; j1--) {
/*     */           
/* 187 */           if (j1 < 127 - this.rand.nextInt(5) && j1 > this.rand.nextInt(5)) {
/*     */             
/* 189 */             IBlockState iblockstate2 = primer.getBlockState(k, j1, j);
/*     */             
/* 191 */             if (iblockstate2.getBlock() != null && iblockstate2.getMaterial() != Material.AIR) {
/*     */               
/* 193 */               if (iblockstate2.getBlock() == Blocks.NETHERRACK)
/*     */               {
/* 195 */                 if (i1 == -1) {
/*     */                   
/* 197 */                   if (l <= 0) {
/*     */                     
/* 199 */                     iblockstate = AIR;
/* 200 */                     iblockstate1 = NETHERRACK;
/*     */                   }
/* 202 */                   else if (j1 >= i - 4 && j1 <= i + 1) {
/*     */                     
/* 204 */                     iblockstate = NETHERRACK;
/* 205 */                     iblockstate1 = NETHERRACK;
/*     */                     
/* 207 */                     if (flag1) {
/*     */                       
/* 209 */                       iblockstate = GRAVEL;
/* 210 */                       iblockstate1 = NETHERRACK;
/*     */                     } 
/*     */                     
/* 213 */                     if (flag) {
/*     */                       
/* 215 */                       iblockstate = SOUL_SAND;
/* 216 */                       iblockstate1 = SOUL_SAND;
/*     */                     } 
/*     */                   } 
/*     */                   
/* 220 */                   if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR))
/*     */                   {
/* 222 */                     iblockstate = LAVA;
/*     */                   }
/*     */                   
/* 225 */                   i1 = l;
/*     */                   
/* 227 */                   if (j1 >= i - 1)
/*     */                   {
/* 229 */                     primer.setBlockState(k, j1, j, iblockstate);
/*     */                   }
/*     */                   else
/*     */                   {
/* 233 */                     primer.setBlockState(k, j1, j, iblockstate1);
/*     */                   }
/*     */                 
/* 236 */                 } else if (i1 > 0) {
/*     */                   
/* 238 */                   i1--;
/* 239 */                   primer.setBlockState(k, j1, j, iblockstate1);
/*     */                 }
/*     */               
/*     */               }
/*     */             } else {
/*     */               
/* 245 */               i1 = -1;
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 250 */             primer.setBlockState(k, j1, j, BEDROCK);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/* 259 */     this.rand.setSeed(x * 341873128712L + z * 132897987541L);
/* 260 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/* 261 */     prepareHeights(x, z, chunkprimer);
/* 262 */     buildSurfaces(x, z, chunkprimer);
/* 263 */     this.genNetherCaves.generate(this.world, x, z, chunkprimer);
/*     */     
/* 265 */     if (this.generateStructures)
/*     */     {
/* 267 */       this.genNetherBridge.generate(this.world, x, z, chunkprimer);
/*     */     }
/*     */     
/* 270 */     Chunk chunk = new Chunk(this.world, chunkprimer, x, z);
/* 271 */     Biome[] abiome = this.world.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);
/* 272 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/* 274 */     for (int i = 0; i < abyte.length; i++)
/*     */     {
/* 276 */       abyte[i] = (byte)Biome.getIdForBiome(abiome[i]);
/*     */     }
/*     */     
/* 279 */     chunk.resetRelightChecks();
/* 280 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   private double[] getHeights(double[] p_185938_1_, int p_185938_2_, int p_185938_3_, int p_185938_4_, int p_185938_5_, int p_185938_6_, int p_185938_7_) {
/* 285 */     if (p_185938_1_ == null)
/*     */     {
/* 287 */       p_185938_1_ = new double[p_185938_5_ * p_185938_6_ * p_185938_7_];
/*     */     }
/*     */     
/* 290 */     double d0 = 684.412D;
/* 291 */     double d1 = 2053.236D;
/* 292 */     this.noiseData4 = this.scaleNoise.generateNoiseOctaves(this.noiseData4, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, 1, p_185938_7_, 1.0D, 0.0D, 1.0D);
/* 293 */     this.dr = this.depthNoise.generateNoiseOctaves(this.dr, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, 1, p_185938_7_, 100.0D, 0.0D, 100.0D);
/* 294 */     this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, p_185938_6_, p_185938_7_, 8.555150000000001D, 34.2206D, 8.555150000000001D);
/* 295 */     this.ar = this.lperlinNoise1.generateNoiseOctaves(this.ar, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, p_185938_6_, p_185938_7_, 684.412D, 2053.236D, 684.412D);
/* 296 */     this.br = this.lperlinNoise2.generateNoiseOctaves(this.br, p_185938_2_, p_185938_3_, p_185938_4_, p_185938_5_, p_185938_6_, p_185938_7_, 684.412D, 2053.236D, 684.412D);
/* 297 */     int i = 0;
/* 298 */     double[] adouble = new double[p_185938_6_];
/*     */     
/* 300 */     for (int j = 0; j < p_185938_6_; j++) {
/*     */       
/* 302 */       adouble[j] = Math.cos(j * Math.PI * 6.0D / p_185938_6_) * 2.0D;
/* 303 */       double d2 = j;
/*     */       
/* 305 */       if (j > p_185938_6_ / 2)
/*     */       {
/* 307 */         d2 = (p_185938_6_ - 1 - j);
/*     */       }
/*     */       
/* 310 */       if (d2 < 4.0D) {
/*     */         
/* 312 */         d2 = 4.0D - d2;
/* 313 */         adouble[j] = adouble[j] - d2 * d2 * d2 * 10.0D;
/*     */       } 
/*     */     } 
/*     */     
/* 317 */     for (int l = 0; l < p_185938_5_; l++) {
/*     */       
/* 319 */       for (int i1 = 0; i1 < p_185938_7_; i1++) {
/*     */         
/* 321 */         double d3 = 0.0D;
/*     */         
/* 323 */         for (int k = 0; k < p_185938_6_; k++) {
/*     */           
/* 325 */           double d8, d4 = adouble[k];
/* 326 */           double d5 = this.ar[i] / 512.0D;
/* 327 */           double d6 = this.br[i] / 512.0D;
/* 328 */           double d7 = (this.pnr[i] / 10.0D + 1.0D) / 2.0D;
/*     */ 
/*     */           
/* 331 */           if (d7 < 0.0D) {
/*     */             
/* 333 */             d8 = d5;
/*     */           }
/* 335 */           else if (d7 > 1.0D) {
/*     */             
/* 337 */             d8 = d6;
/*     */           }
/*     */           else {
/*     */             
/* 341 */             d8 = d5 + (d6 - d5) * d7;
/*     */           } 
/*     */           
/* 344 */           d8 -= d4;
/*     */           
/* 346 */           if (k > p_185938_6_ - 4) {
/*     */             
/* 348 */             double d9 = ((k - p_185938_6_ - 4) / 3.0F);
/* 349 */             d8 = d8 * (1.0D - d9) + -10.0D * d9;
/*     */           } 
/*     */           
/* 352 */           if (k < 0.0D) {
/*     */             
/* 354 */             double d10 = (0.0D - k) / 4.0D;
/* 355 */             d10 = MathHelper.clamp(d10, 0.0D, 1.0D);
/* 356 */             d8 = d8 * (1.0D - d10) + -10.0D * d10;
/*     */           } 
/*     */           
/* 359 */           p_185938_1_[i] = d8;
/* 360 */           i++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 365 */     return p_185938_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void populate(int x, int z) {
/* 370 */     BlockFalling.fallInstantly = true;
/* 371 */     int i = x * 16;
/* 372 */     int j = z * 16;
/* 373 */     BlockPos blockpos = new BlockPos(i, 0, j);
/* 374 */     Biome biome = this.world.getBiome(blockpos.add(16, 0, 16));
/* 375 */     ChunkPos chunkpos = new ChunkPos(x, z);
/* 376 */     this.genNetherBridge.generateStructure(this.world, this.rand, chunkpos);
/*     */     
/* 378 */     for (int k = 0; k < 8; k++)
/*     */     {
/* 380 */       this.hellSpringGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
/*     */     }
/*     */     
/* 383 */     for (int i1 = 0; i1 < this.rand.nextInt(this.rand.nextInt(10) + 1) + 1; i1++)
/*     */     {
/* 385 */       this.fireFeature.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
/*     */     }
/*     */     
/* 388 */     for (int j1 = 0; j1 < this.rand.nextInt(this.rand.nextInt(10) + 1); j1++)
/*     */     {
/* 390 */       this.lightGemGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(120) + 4, this.rand.nextInt(16) + 8));
/*     */     }
/*     */     
/* 393 */     for (int k1 = 0; k1 < 10; k1++)
/*     */     {
/* 395 */       this.hellPortalGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(128), this.rand.nextInt(16) + 8));
/*     */     }
/*     */     
/* 398 */     if (this.rand.nextBoolean())
/*     */     {
/* 400 */       this.brownMushroomFeature.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(128), this.rand.nextInt(16) + 8));
/*     */     }
/*     */     
/* 403 */     if (this.rand.nextBoolean())
/*     */     {
/* 405 */       this.redMushroomFeature.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16) + 8, this.rand.nextInt(128), this.rand.nextInt(16) + 8));
/*     */     }
/*     */     
/* 408 */     for (int l1 = 0; l1 < 16; l1++)
/*     */     {
/* 410 */       this.quartzGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16), this.rand.nextInt(108) + 10, this.rand.nextInt(16)));
/*     */     }
/*     */     
/* 413 */     int i2 = this.world.getSeaLevel() / 2 + 1;
/*     */     
/* 415 */     for (int l = 0; l < 4; l++)
/*     */     {
/* 417 */       this.magmaGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16), i2 - 5 + this.rand.nextInt(10), this.rand.nextInt(16)));
/*     */     }
/*     */     
/* 420 */     for (int j2 = 0; j2 < 16; j2++)
/*     */     {
/* 422 */       this.lavaTrapGen.generate(this.world, this.rand, blockpos.add(this.rand.nextInt(16), this.rand.nextInt(108) + 10, this.rand.nextInt(16)));
/*     */     }
/*     */     
/* 425 */     biome.decorate(this.world, this.rand, new BlockPos(i, 0, j));
/* 426 */     BlockFalling.fallInstantly = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generateStructures(Chunk chunkIn, int x, int z) {
/* 431 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 436 */     if (creatureType == EnumCreatureType.MONSTER) {
/*     */       
/* 438 */       if (this.genNetherBridge.isInsideStructure(pos))
/*     */       {
/* 440 */         return this.genNetherBridge.getSpawnList();
/*     */       }
/*     */       
/* 443 */       if (this.genNetherBridge.isPositionInStructure(this.world, pos) && this.world.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICK)
/*     */       {
/* 445 */         return this.genNetherBridge.getSpawnList();
/*     */       }
/*     */     } 
/*     */     
/* 449 */     Biome biome = this.world.getBiome(pos);
/* 450 */     return biome.getSpawnableList(creatureType);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean p_180513_4_) {
/* 456 */     return ("Fortress".equals(structureName) && this.genNetherBridge != null) ? this.genNetherBridge.getClosestStrongholdPos(worldIn, position, p_180513_4_) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193414_a(World p_193414_1_, String p_193414_2_, BlockPos p_193414_3_) {
/* 461 */     return ("Fortress".equals(p_193414_2_) && this.genNetherBridge != null) ? this.genNetherBridge.isInsideStructure(p_193414_3_) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {
/* 466 */     this.genNetherBridge.generate(this.world, x, z, null);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\ChunkGeneratorHell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */