/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.BlockChorusFlower;
/*     */ import net.minecraft.block.BlockFalling;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityEndGateway;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.feature.WorldGenEndGateway;
/*     */ import net.minecraft.world.gen.feature.WorldGenEndIsland;
/*     */ import net.minecraft.world.gen.structure.MapGenEndCity;
/*     */ 
/*     */ public class ChunkGeneratorEnd
/*     */   implements IChunkGenerator
/*     */ {
/*     */   private final Random rand;
/*  29 */   protected static final IBlockState END_STONE = Blocks.END_STONE.getDefaultState();
/*  30 */   protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
/*     */   
/*     */   private final NoiseGeneratorOctaves lperlinNoise1;
/*     */   
/*     */   private final NoiseGeneratorOctaves lperlinNoise2;
/*     */   
/*     */   private final NoiseGeneratorOctaves perlinNoise1;
/*     */   
/*     */   public NoiseGeneratorOctaves noiseGen5;
/*     */   
/*     */   public NoiseGeneratorOctaves noiseGen6;
/*     */   
/*     */   private final World worldObj;
/*     */   
/*     */   private final boolean mapFeaturesEnabled;
/*     */   
/*     */   private final BlockPos field_191061_n;
/*  47 */   private final MapGenEndCity endCityGen = new MapGenEndCity(this);
/*     */   
/*     */   private final NoiseGeneratorSimplex islandNoise;
/*     */   
/*     */   private double[] buffer;
/*     */   private Biome[] biomesForGeneration;
/*     */   double[] pnr;
/*     */   double[] ar;
/*     */   double[] br;
/*  56 */   private final WorldGenEndIsland endIslands = new WorldGenEndIsland();
/*     */ 
/*     */   
/*     */   public ChunkGeneratorEnd(World p_i47241_1_, boolean p_i47241_2_, long p_i47241_3_, BlockPos p_i47241_5_) {
/*  60 */     this.worldObj = p_i47241_1_;
/*  61 */     this.mapFeaturesEnabled = p_i47241_2_;
/*  62 */     this.field_191061_n = p_i47241_5_;
/*  63 */     this.rand = new Random(p_i47241_3_);
/*  64 */     this.lperlinNoise1 = new NoiseGeneratorOctaves(this.rand, 16);
/*  65 */     this.lperlinNoise2 = new NoiseGeneratorOctaves(this.rand, 16);
/*  66 */     this.perlinNoise1 = new NoiseGeneratorOctaves(this.rand, 8);
/*  67 */     this.noiseGen5 = new NoiseGeneratorOctaves(this.rand, 10);
/*  68 */     this.noiseGen6 = new NoiseGeneratorOctaves(this.rand, 16);
/*  69 */     this.islandNoise = new NoiseGeneratorSimplex(this.rand);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBlocksInChunk(int x, int z, ChunkPrimer primer) {
/*  77 */     int i = 2;
/*  78 */     int j = 3;
/*  79 */     int k = 33;
/*  80 */     int l = 3;
/*  81 */     this.buffer = getHeights(this.buffer, x * 2, 0, z * 2, 3, 33, 3);
/*     */     
/*  83 */     for (int i1 = 0; i1 < 2; i1++) {
/*     */       
/*  85 */       for (int j1 = 0; j1 < 2; j1++) {
/*     */         
/*  87 */         for (int k1 = 0; k1 < 32; k1++) {
/*     */           
/*  89 */           double d0 = 0.25D;
/*  90 */           double d1 = this.buffer[((i1 + 0) * 3 + j1 + 0) * 33 + k1 + 0];
/*  91 */           double d2 = this.buffer[((i1 + 0) * 3 + j1 + 1) * 33 + k1 + 0];
/*  92 */           double d3 = this.buffer[((i1 + 1) * 3 + j1 + 0) * 33 + k1 + 0];
/*  93 */           double d4 = this.buffer[((i1 + 1) * 3 + j1 + 1) * 33 + k1 + 0];
/*  94 */           double d5 = (this.buffer[((i1 + 0) * 3 + j1 + 0) * 33 + k1 + 1] - d1) * 0.25D;
/*  95 */           double d6 = (this.buffer[((i1 + 0) * 3 + j1 + 1) * 33 + k1 + 1] - d2) * 0.25D;
/*  96 */           double d7 = (this.buffer[((i1 + 1) * 3 + j1 + 0) * 33 + k1 + 1] - d3) * 0.25D;
/*  97 */           double d8 = (this.buffer[((i1 + 1) * 3 + j1 + 1) * 33 + k1 + 1] - d4) * 0.25D;
/*     */           
/*  99 */           for (int l1 = 0; l1 < 4; l1++) {
/*     */             
/* 101 */             double d9 = 0.125D;
/* 102 */             double d10 = d1;
/* 103 */             double d11 = d2;
/* 104 */             double d12 = (d3 - d1) * 0.125D;
/* 105 */             double d13 = (d4 - d2) * 0.125D;
/*     */             
/* 107 */             for (int i2 = 0; i2 < 8; i2++) {
/*     */               
/* 109 */               double d14 = 0.125D;
/* 110 */               double d15 = d10;
/* 111 */               double d16 = (d11 - d10) * 0.125D;
/*     */               
/* 113 */               for (int j2 = 0; j2 < 8; j2++) {
/*     */                 
/* 115 */                 IBlockState iblockstate = AIR;
/*     */                 
/* 117 */                 if (d15 > 0.0D)
/*     */                 {
/* 119 */                   iblockstate = END_STONE;
/*     */                 }
/*     */                 
/* 122 */                 int k2 = i2 + i1 * 8;
/* 123 */                 int l2 = l1 + k1 * 4;
/* 124 */                 int i3 = j2 + j1 * 8;
/* 125 */                 primer.setBlockState(k2, l2, i3, iblockstate);
/* 126 */                 d15 += d16;
/*     */               } 
/*     */               
/* 129 */               d10 += d12;
/* 130 */               d11 += d13;
/*     */             } 
/*     */             
/* 133 */             d1 += d5;
/* 134 */             d2 += d6;
/* 135 */             d3 += d7;
/* 136 */             d4 += d8;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void buildSurfaces(ChunkPrimer primer) {
/* 145 */     for (int i = 0; i < 16; i++) {
/*     */       
/* 147 */       for (int j = 0; j < 16; j++) {
/*     */         
/* 149 */         int k = 1;
/* 150 */         int l = -1;
/* 151 */         IBlockState iblockstate = END_STONE;
/* 152 */         IBlockState iblockstate1 = END_STONE;
/*     */         
/* 154 */         for (int i1 = 127; i1 >= 0; i1--) {
/*     */           
/* 156 */           IBlockState iblockstate2 = primer.getBlockState(i, i1, j);
/*     */           
/* 158 */           if (iblockstate2.getMaterial() == Material.AIR) {
/*     */             
/* 160 */             l = -1;
/*     */           }
/* 162 */           else if (iblockstate2.getBlock() == Blocks.STONE) {
/*     */             
/* 164 */             if (l == -1) {
/*     */               
/* 166 */               l = 1;
/*     */               
/* 168 */               if (i1 >= 0)
/*     */               {
/* 170 */                 primer.setBlockState(i, i1, j, iblockstate);
/*     */               }
/*     */               else
/*     */               {
/* 174 */                 primer.setBlockState(i, i1, j, iblockstate1);
/*     */               }
/*     */             
/* 177 */             } else if (l > 0) {
/*     */               
/* 179 */               l--;
/* 180 */               primer.setBlockState(i, i1, j, iblockstate1);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/* 190 */     this.rand.setSeed(x * 341873128712L + z * 132897987541L);
/* 191 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/* 192 */     this.biomesForGeneration = this.worldObj.getBiomeProvider().getBiomes(this.biomesForGeneration, x * 16, z * 16, 16, 16);
/* 193 */     setBlocksInChunk(x, z, chunkprimer);
/* 194 */     buildSurfaces(chunkprimer);
/*     */     
/* 196 */     if (this.mapFeaturesEnabled)
/*     */     {
/* 198 */       this.endCityGen.generate(this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 201 */     Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
/* 202 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/* 204 */     for (int i = 0; i < abyte.length; i++)
/*     */     {
/* 206 */       abyte[i] = (byte)Biome.getIdForBiome(this.biomesForGeneration[i]);
/*     */     }
/*     */     
/* 209 */     chunk.generateSkylightMap();
/* 210 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   private float getIslandHeightValue(int p_185960_1_, int p_185960_2_, int p_185960_3_, int p_185960_4_) {
/* 215 */     float f = (p_185960_1_ * 2 + p_185960_3_);
/* 216 */     float f1 = (p_185960_2_ * 2 + p_185960_4_);
/* 217 */     float f2 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * 8.0F;
/*     */     
/* 219 */     if (f2 > 80.0F)
/*     */     {
/* 221 */       f2 = 80.0F;
/*     */     }
/*     */     
/* 224 */     if (f2 < -100.0F)
/*     */     {
/* 226 */       f2 = -100.0F;
/*     */     }
/*     */     
/* 229 */     for (int i = -12; i <= 12; i++) {
/*     */       
/* 231 */       for (int j = -12; j <= 12; j++) {
/*     */         
/* 233 */         long k = (p_185960_1_ + i);
/* 234 */         long l = (p_185960_2_ + j);
/*     */         
/* 236 */         if (k * k + l * l > 4096L && this.islandNoise.getValue(k, l) < -0.8999999761581421D) {
/*     */           
/* 238 */           float f3 = (MathHelper.abs((float)k) * 3439.0F + MathHelper.abs((float)l) * 147.0F) % 13.0F + 9.0F;
/* 239 */           f = (p_185960_3_ - i * 2);
/* 240 */           f1 = (p_185960_4_ - j * 2);
/* 241 */           float f4 = 100.0F - MathHelper.sqrt(f * f + f1 * f1) * f3;
/*     */           
/* 243 */           if (f4 > 80.0F)
/*     */           {
/* 245 */             f4 = 80.0F;
/*     */           }
/*     */           
/* 248 */           if (f4 < -100.0F)
/*     */           {
/* 250 */             f4 = -100.0F;
/*     */           }
/*     */           
/* 253 */           if (f4 > f2)
/*     */           {
/* 255 */             f2 = f4;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 261 */     return f2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isIslandChunk(int p_185961_1_, int p_185961_2_) {
/* 266 */     return (p_185961_1_ * p_185961_1_ + p_185961_2_ * p_185961_2_ > 4096L && getIslandHeightValue(p_185961_1_, p_185961_2_, 1, 1) >= 0.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private double[] getHeights(double[] p_185963_1_, int p_185963_2_, int p_185963_3_, int p_185963_4_, int p_185963_5_, int p_185963_6_, int p_185963_7_) {
/* 271 */     if (p_185963_1_ == null)
/*     */     {
/* 273 */       p_185963_1_ = new double[p_185963_5_ * p_185963_6_ * p_185963_7_];
/*     */     }
/*     */     
/* 276 */     double d0 = 684.412D;
/* 277 */     double d1 = 684.412D;
/* 278 */     d0 *= 2.0D;
/* 279 */     this.pnr = this.perlinNoise1.generateNoiseOctaves(this.pnr, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0 / 80.0D, 4.277575000000001D, d0 / 80.0D);
/* 280 */     this.ar = this.lperlinNoise1.generateNoiseOctaves(this.ar, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, 684.412D, d0);
/* 281 */     this.br = this.lperlinNoise2.generateNoiseOctaves(this.br, p_185963_2_, p_185963_3_, p_185963_4_, p_185963_5_, p_185963_6_, p_185963_7_, d0, 684.412D, d0);
/* 282 */     int i = p_185963_2_ / 2;
/* 283 */     int j = p_185963_4_ / 2;
/* 284 */     int k = 0;
/*     */     
/* 286 */     for (int l = 0; l < p_185963_5_; l++) {
/*     */       
/* 288 */       for (int i1 = 0; i1 < p_185963_7_; i1++) {
/*     */         
/* 290 */         float f = getIslandHeightValue(i, j, l, i1);
/*     */         
/* 292 */         for (int j1 = 0; j1 < p_185963_6_; j1++) {
/*     */           
/* 294 */           double d4, d2 = this.ar[k] / 512.0D;
/* 295 */           double d3 = this.br[k] / 512.0D;
/* 296 */           double d5 = (this.pnr[k] / 10.0D + 1.0D) / 2.0D;
/*     */ 
/*     */           
/* 299 */           if (d5 < 0.0D) {
/*     */             
/* 301 */             d4 = d2;
/*     */           }
/* 303 */           else if (d5 > 1.0D) {
/*     */             
/* 305 */             d4 = d3;
/*     */           }
/*     */           else {
/*     */             
/* 309 */             d4 = d2 + (d3 - d2) * d5;
/*     */           } 
/*     */           
/* 312 */           d4 -= 8.0D;
/* 313 */           d4 += f;
/* 314 */           int k1 = 2;
/*     */           
/* 316 */           if (j1 > p_185963_6_ / 2 - k1) {
/*     */             
/* 318 */             double d6 = ((j1 - p_185963_6_ / 2 - k1) / 64.0F);
/* 319 */             d6 = MathHelper.clamp(d6, 0.0D, 1.0D);
/* 320 */             d4 = d4 * (1.0D - d6) + -3000.0D * d6;
/*     */           } 
/*     */           
/* 323 */           k1 = 8;
/*     */           
/* 325 */           if (j1 < k1) {
/*     */             
/* 327 */             double d7 = ((k1 - j1) / (k1 - 1.0F));
/* 328 */             d4 = d4 * (1.0D - d7) + -30.0D * d7;
/*     */           } 
/*     */           
/* 331 */           p_185963_1_[k] = d4;
/* 332 */           k++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 337 */     return p_185963_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void populate(int x, int z) {
/* 342 */     BlockFalling.fallInstantly = true;
/* 343 */     BlockPos blockpos = new BlockPos(x * 16, 0, z * 16);
/*     */     
/* 345 */     if (this.mapFeaturesEnabled)
/*     */     {
/* 347 */       this.endCityGen.generateStructure(this.worldObj, this.rand, new ChunkPos(x, z));
/*     */     }
/*     */     
/* 350 */     this.worldObj.getBiome(blockpos.add(16, 0, 16)).decorate(this.worldObj, this.worldObj.rand, blockpos);
/* 351 */     long i = x * x + z * z;
/*     */     
/* 353 */     if (i > 4096L) {
/*     */       
/* 355 */       float f = getIslandHeightValue(x, z, 1, 1);
/*     */       
/* 357 */       if (f < -20.0F && this.rand.nextInt(14) == 0) {
/*     */         
/* 359 */         this.endIslands.generate(this.worldObj, this.rand, blockpos.add(this.rand.nextInt(16) + 8, 55 + this.rand.nextInt(16), this.rand.nextInt(16) + 8));
/*     */         
/* 361 */         if (this.rand.nextInt(4) == 0)
/*     */         {
/* 363 */           this.endIslands.generate(this.worldObj, this.rand, blockpos.add(this.rand.nextInt(16) + 8, 55 + this.rand.nextInt(16), this.rand.nextInt(16) + 8));
/*     */         }
/*     */       } 
/*     */       
/* 367 */       if (getIslandHeightValue(x, z, 1, 1) > 40.0F) {
/*     */         
/* 369 */         int j = this.rand.nextInt(5);
/*     */         
/* 371 */         for (int k = 0; k < j; k++) {
/*     */           
/* 373 */           int l = this.rand.nextInt(16) + 8;
/* 374 */           int i1 = this.rand.nextInt(16) + 8;
/* 375 */           int j1 = this.worldObj.getHeight(blockpos.add(l, 0, i1)).getY();
/*     */           
/* 377 */           if (j1 > 0) {
/*     */             
/* 379 */             int k1 = j1 - 1;
/*     */             
/* 381 */             if (this.worldObj.isAirBlock(blockpos.add(l, k1 + 1, i1)) && this.worldObj.getBlockState(blockpos.add(l, k1, i1)).getBlock() == Blocks.END_STONE)
/*     */             {
/* 383 */               BlockChorusFlower.generatePlant(this.worldObj, blockpos.add(l, k1 + 1, i1), this.rand, 8);
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 388 */         if (this.rand.nextInt(700) == 0) {
/*     */           
/* 390 */           int l1 = this.rand.nextInt(16) + 8;
/* 391 */           int i2 = this.rand.nextInt(16) + 8;
/* 392 */           int j2 = this.worldObj.getHeight(blockpos.add(l1, 0, i2)).getY();
/*     */           
/* 394 */           if (j2 > 0) {
/*     */             
/* 396 */             int k2 = j2 + 3 + this.rand.nextInt(7);
/* 397 */             BlockPos blockpos1 = blockpos.add(l1, k2, i2);
/* 398 */             (new WorldGenEndGateway()).generate(this.worldObj, this.rand, blockpos1);
/* 399 */             TileEntity tileentity = this.worldObj.getTileEntity(blockpos1);
/*     */             
/* 401 */             if (tileentity instanceof TileEntityEndGateway) {
/*     */               
/* 403 */               TileEntityEndGateway tileentityendgateway = (TileEntityEndGateway)tileentity;
/* 404 */               tileentityendgateway.func_190603_b(this.field_191061_n);
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 411 */     BlockFalling.fallInstantly = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generateStructures(Chunk chunkIn, int x, int z) {
/* 416 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 421 */     return this.worldObj.getBiome(pos).getSpawnableList(creatureType);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean p_180513_4_) {
/* 427 */     return ("EndCity".equals(structureName) && this.endCityGen != null) ? this.endCityGen.getClosestStrongholdPos(worldIn, position, p_180513_4_) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193414_a(World p_193414_1_, String p_193414_2_, BlockPos p_193414_3_) {
/* 432 */     return ("EndCity".equals(p_193414_2_) && this.endCityGen != null) ? this.endCityGen.isInsideStructure(p_193414_3_) : false;
/*     */   }
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {}
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\ChunkGeneratorEnd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */