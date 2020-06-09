/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockColored;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.BlockSand;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.NoiseGeneratorPerlin;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ 
/*     */ public class BiomeMesa extends Biome {
/*  20 */   protected static final IBlockState COARSE_DIRT = Blocks.DIRT.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.COARSE_DIRT);
/*  21 */   protected static final IBlockState GRASS = Blocks.GRASS.getDefaultState();
/*  22 */   protected static final IBlockState HARDENED_CLAY = Blocks.HARDENED_CLAY.getDefaultState();
/*  23 */   protected static final IBlockState STAINED_HARDENED_CLAY = Blocks.STAINED_HARDENED_CLAY.getDefaultState();
/*  24 */   protected static final IBlockState ORANGE_STAINED_HARDENED_CLAY = STAINED_HARDENED_CLAY.withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.ORANGE);
/*  25 */   protected static final IBlockState RED_SAND = Blocks.SAND.getDefaultState().withProperty((IProperty)BlockSand.VARIANT, (Comparable)BlockSand.EnumType.RED_SAND);
/*     */   
/*     */   private IBlockState[] clayBands;
/*     */   private long worldSeed;
/*     */   private NoiseGeneratorPerlin pillarNoise;
/*     */   private NoiseGeneratorPerlin pillarRoofNoise;
/*     */   private NoiseGeneratorPerlin clayBandsOffsetNoise;
/*     */   private final boolean brycePillars;
/*     */   private final boolean hasForest;
/*     */   
/*     */   public BiomeMesa(boolean p_i46704_1_, boolean p_i46704_2_, Biome.BiomeProperties properties) {
/*  36 */     super(properties);
/*  37 */     this.brycePillars = p_i46704_1_;
/*  38 */     this.hasForest = p_i46704_2_;
/*  39 */     this.spawnableCreatureList.clear();
/*  40 */     this.topBlock = RED_SAND;
/*  41 */     this.fillerBlock = STAINED_HARDENED_CLAY;
/*  42 */     this.theBiomeDecorator.treesPerChunk = -999;
/*  43 */     this.theBiomeDecorator.deadBushPerChunk = 20;
/*  44 */     this.theBiomeDecorator.reedsPerChunk = 3;
/*  45 */     this.theBiomeDecorator.cactiPerChunk = 5;
/*  46 */     this.theBiomeDecorator.flowersPerChunk = 0;
/*  47 */     this.spawnableCreatureList.clear();
/*     */     
/*  49 */     if (p_i46704_2_)
/*     */     {
/*  51 */       this.theBiomeDecorator.treesPerChunk = 5;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BiomeDecorator createBiomeDecorator() {
/*  60 */     return new Decorator(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/*  65 */     return (WorldGenAbstractTree)TREE_FEATURE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFoliageColorAtPos(BlockPos pos) {
/*  70 */     return 10387789;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGrassColorAtPos(BlockPos pos) {
/*  75 */     return 9470285;
/*     */   }
/*     */ 
/*     */   
/*     */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/*  80 */     if (this.clayBands == null || this.worldSeed != worldIn.getSeed())
/*     */     {
/*  82 */       generateBands(worldIn.getSeed());
/*     */     }
/*     */     
/*  85 */     if (this.pillarNoise == null || this.pillarRoofNoise == null || this.worldSeed != worldIn.getSeed()) {
/*     */       
/*  87 */       Random random = new Random(this.worldSeed);
/*  88 */       this.pillarNoise = new NoiseGeneratorPerlin(random, 4);
/*  89 */       this.pillarRoofNoise = new NoiseGeneratorPerlin(random, 1);
/*     */     } 
/*     */     
/*  92 */     this.worldSeed = worldIn.getSeed();
/*  93 */     double d4 = 0.0D;
/*     */     
/*  95 */     if (this.brycePillars) {
/*     */       
/*  97 */       int i = (x & 0xFFFFFFF0) + (z & 0xF);
/*  98 */       int j = (z & 0xFFFFFFF0) + (x & 0xF);
/*  99 */       double d0 = Math.min(Math.abs(noiseVal), this.pillarNoise.getValue(i * 0.25D, j * 0.25D));
/*     */       
/* 101 */       if (d0 > 0.0D) {
/*     */         
/* 103 */         double d1 = 0.001953125D;
/* 104 */         double d2 = Math.abs(this.pillarRoofNoise.getValue(i * 0.001953125D, j * 0.001953125D));
/* 105 */         d4 = d0 * d0 * 2.5D;
/* 106 */         double d3 = Math.ceil(d2 * 50.0D) + 14.0D;
/*     */         
/* 108 */         if (d4 > d3)
/*     */         {
/* 110 */           d4 = d3;
/*     */         }
/*     */         
/* 113 */         d4 += 64.0D;
/*     */       } 
/*     */     } 
/*     */     
/* 117 */     int k1 = x & 0xF;
/* 118 */     int l1 = z & 0xF;
/* 119 */     int i2 = worldIn.getSeaLevel();
/* 120 */     IBlockState iblockstate = STAINED_HARDENED_CLAY;
/* 121 */     IBlockState iblockstate3 = this.fillerBlock;
/* 122 */     int k = (int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
/* 123 */     boolean flag = (Math.cos(noiseVal / 3.0D * Math.PI) > 0.0D);
/* 124 */     int l = -1;
/* 125 */     boolean flag1 = false;
/* 126 */     int i1 = 0;
/*     */     
/* 128 */     for (int j1 = 255; j1 >= 0; j1--) {
/*     */       
/* 130 */       if (chunkPrimerIn.getBlockState(l1, j1, k1).getMaterial() == Material.AIR && j1 < (int)d4)
/*     */       {
/* 132 */         chunkPrimerIn.setBlockState(l1, j1, k1, STONE);
/*     */       }
/*     */       
/* 135 */       if (j1 <= rand.nextInt(5)) {
/*     */         
/* 137 */         chunkPrimerIn.setBlockState(l1, j1, k1, BEDROCK);
/*     */       }
/* 139 */       else if (i1 < 15 || this.brycePillars) {
/*     */         
/* 141 */         IBlockState iblockstate1 = chunkPrimerIn.getBlockState(l1, j1, k1);
/*     */         
/* 143 */         if (iblockstate1.getMaterial() == Material.AIR) {
/*     */           
/* 145 */           l = -1;
/*     */         }
/* 147 */         else if (iblockstate1.getBlock() == Blocks.STONE) {
/*     */           
/* 149 */           if (l == -1) {
/*     */             
/* 151 */             flag1 = false;
/*     */             
/* 153 */             if (k <= 0) {
/*     */               
/* 155 */               iblockstate = AIR;
/* 156 */               iblockstate3 = STONE;
/*     */             }
/* 158 */             else if (j1 >= i2 - 4 && j1 <= i2 + 1) {
/*     */               
/* 160 */               iblockstate = STAINED_HARDENED_CLAY;
/* 161 */               iblockstate3 = this.fillerBlock;
/*     */             } 
/*     */             
/* 164 */             if (j1 < i2 && (iblockstate == null || iblockstate.getMaterial() == Material.AIR))
/*     */             {
/* 166 */               iblockstate = WATER;
/*     */             }
/*     */             
/* 169 */             l = k + Math.max(0, j1 - i2);
/*     */             
/* 171 */             if (j1 >= i2 - 1) {
/*     */               
/* 173 */               if (this.hasForest && j1 > 86 + k * 2) {
/*     */                 
/* 175 */                 if (flag)
/*     */                 {
/* 177 */                   chunkPrimerIn.setBlockState(l1, j1, k1, COARSE_DIRT);
/*     */                 }
/*     */                 else
/*     */                 {
/* 181 */                   chunkPrimerIn.setBlockState(l1, j1, k1, GRASS);
/*     */                 }
/*     */               
/* 184 */               } else if (j1 > i2 + 3 + k) {
/*     */                 IBlockState iblockstate2;
/*     */ 
/*     */                 
/* 188 */                 if (j1 >= 64 && j1 <= 127) {
/*     */                   
/* 190 */                   if (flag)
/*     */                   {
/* 192 */                     iblockstate2 = HARDENED_CLAY;
/*     */                   }
/*     */                   else
/*     */                   {
/* 196 */                     iblockstate2 = getBand(x, j1, z);
/*     */                   }
/*     */                 
/*     */                 } else {
/*     */                   
/* 201 */                   iblockstate2 = ORANGE_STAINED_HARDENED_CLAY;
/*     */                 } 
/*     */                 
/* 204 */                 chunkPrimerIn.setBlockState(l1, j1, k1, iblockstate2);
/*     */               }
/*     */               else {
/*     */                 
/* 208 */                 chunkPrimerIn.setBlockState(l1, j1, k1, this.topBlock);
/* 209 */                 flag1 = true;
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 214 */               chunkPrimerIn.setBlockState(l1, j1, k1, iblockstate3);
/*     */               
/* 216 */               if (iblockstate3.getBlock() == Blocks.STAINED_HARDENED_CLAY)
/*     */               {
/* 218 */                 chunkPrimerIn.setBlockState(l1, j1, k1, ORANGE_STAINED_HARDENED_CLAY);
/*     */               }
/*     */             }
/*     */           
/* 222 */           } else if (l > 0) {
/*     */             
/* 224 */             l--;
/*     */             
/* 226 */             if (flag1) {
/*     */               
/* 228 */               chunkPrimerIn.setBlockState(l1, j1, k1, ORANGE_STAINED_HARDENED_CLAY);
/*     */             }
/*     */             else {
/*     */               
/* 232 */               chunkPrimerIn.setBlockState(l1, j1, k1, getBand(x, j1, z));
/*     */             } 
/*     */           } 
/*     */           
/* 236 */           i1++;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void generateBands(long p_150619_1_) {
/* 244 */     this.clayBands = new IBlockState[64];
/* 245 */     Arrays.fill((Object[])this.clayBands, HARDENED_CLAY);
/* 246 */     Random random = new Random(p_150619_1_);
/* 247 */     this.clayBandsOffsetNoise = new NoiseGeneratorPerlin(random, 1);
/*     */     
/* 249 */     for (int l1 = 0; l1 < 64; l1++) {
/*     */       
/* 251 */       l1 += random.nextInt(5) + 1;
/*     */       
/* 253 */       if (l1 < 64)
/*     */       {
/* 255 */         this.clayBands[l1] = ORANGE_STAINED_HARDENED_CLAY;
/*     */       }
/*     */     } 
/*     */     
/* 259 */     int i2 = random.nextInt(4) + 2;
/*     */     
/* 261 */     for (int i = 0; i < i2; i++) {
/*     */       
/* 263 */       int j = random.nextInt(3) + 1;
/* 264 */       int k = random.nextInt(64);
/*     */       
/* 266 */       for (int l = 0; k + l < 64 && l < j; l++)
/*     */       {
/* 268 */         this.clayBands[k + l] = STAINED_HARDENED_CLAY.withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.YELLOW);
/*     */       }
/*     */     } 
/*     */     
/* 272 */     int j2 = random.nextInt(4) + 2;
/*     */     
/* 274 */     for (int k2 = 0; k2 < j2; k2++) {
/*     */       
/* 276 */       int i3 = random.nextInt(3) + 2;
/* 277 */       int l3 = random.nextInt(64);
/*     */       
/* 279 */       for (int i1 = 0; l3 + i1 < 64 && i1 < i3; i1++)
/*     */       {
/* 281 */         this.clayBands[l3 + i1] = STAINED_HARDENED_CLAY.withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.BROWN);
/*     */       }
/*     */     } 
/*     */     
/* 285 */     int l2 = random.nextInt(4) + 2;
/*     */     
/* 287 */     for (int j3 = 0; j3 < l2; j3++) {
/*     */       
/* 289 */       int i4 = random.nextInt(3) + 1;
/* 290 */       int k4 = random.nextInt(64);
/*     */       
/* 292 */       for (int j1 = 0; k4 + j1 < 64 && j1 < i4; j1++)
/*     */       {
/* 294 */         this.clayBands[k4 + j1] = STAINED_HARDENED_CLAY.withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.RED);
/*     */       }
/*     */     } 
/*     */     
/* 298 */     int k3 = random.nextInt(3) + 3;
/* 299 */     int j4 = 0;
/*     */     
/* 301 */     for (int l4 = 0; l4 < k3; l4++) {
/*     */       
/* 303 */       int i5 = 1;
/* 304 */       j4 += random.nextInt(16) + 4;
/*     */       
/* 306 */       for (int k1 = 0; j4 + k1 < 64 && k1 < 1; k1++) {
/*     */         
/* 308 */         this.clayBands[j4 + k1] = STAINED_HARDENED_CLAY.withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.WHITE);
/*     */         
/* 310 */         if (j4 + k1 > 1 && random.nextBoolean())
/*     */         {
/* 312 */           this.clayBands[j4 + k1 - 1] = STAINED_HARDENED_CLAY.withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.SILVER);
/*     */         }
/*     */         
/* 315 */         if (j4 + k1 < 63 && random.nextBoolean())
/*     */         {
/* 317 */           this.clayBands[j4 + k1 + 1] = STAINED_HARDENED_CLAY.withProperty((IProperty)BlockColored.COLOR, (Comparable)EnumDyeColor.SILVER);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private IBlockState getBand(int p_180629_1_, int p_180629_2_, int p_180629_3_) {
/* 325 */     int i = (int)Math.round(this.clayBandsOffsetNoise.getValue(p_180629_1_ / 512.0D, p_180629_1_ / 512.0D) * 2.0D);
/* 326 */     return this.clayBands[(p_180629_2_ + i + 64) % 64];
/*     */   }
/*     */ 
/*     */   
/*     */   class Decorator
/*     */     extends BiomeDecorator
/*     */   {
/*     */     private Decorator() {}
/*     */ 
/*     */     
/*     */     protected void generateOres(World worldIn, Random random) {
/* 337 */       super.generateOres(worldIn, random);
/* 338 */       genStandardOre1(worldIn, random, 20, this.goldGen, 32, 80);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeMesa.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */