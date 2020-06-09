/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.block.BlockStone;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.ChunkGeneratorSettings;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigMushroom;
/*     */ import net.minecraft.world.gen.feature.WorldGenBush;
/*     */ import net.minecraft.world.gen.feature.WorldGenCactus;
/*     */ import net.minecraft.world.gen.feature.WorldGenClay;
/*     */ import net.minecraft.world.gen.feature.WorldGenDeadBush;
/*     */ import net.minecraft.world.gen.feature.WorldGenFlowers;
/*     */ import net.minecraft.world.gen.feature.WorldGenLiquids;
/*     */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*     */ import net.minecraft.world.gen.feature.WorldGenPumpkin;
/*     */ import net.minecraft.world.gen.feature.WorldGenReed;
/*     */ import net.minecraft.world.gen.feature.WorldGenSand;
/*     */ import net.minecraft.world.gen.feature.WorldGenWaterlily;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ 
/*     */ public class BiomeDecorator
/*     */ {
/*     */   protected boolean decorating;
/*     */   protected BlockPos chunkPos;
/*     */   protected ChunkGeneratorSettings chunkProviderSettings;
/*  33 */   protected WorldGenerator clayGen = (WorldGenerator)new WorldGenClay(4);
/*     */ 
/*     */   
/*  36 */   protected WorldGenerator sandGen = (WorldGenerator)new WorldGenSand((Block)Blocks.SAND, 7);
/*     */ 
/*     */   
/*  39 */   protected WorldGenerator gravelAsSandGen = (WorldGenerator)new WorldGenSand(Blocks.GRAVEL, 6);
/*     */   
/*     */   protected WorldGenerator dirtGen;
/*     */   
/*     */   protected WorldGenerator gravelGen;
/*     */   
/*     */   protected WorldGenerator graniteGen;
/*     */   
/*     */   protected WorldGenerator dioriteGen;
/*     */   
/*     */   protected WorldGenerator andesiteGen;
/*     */   
/*     */   protected WorldGenerator coalGen;
/*     */   protected WorldGenerator ironGen;
/*     */   protected WorldGenerator goldGen;
/*     */   protected WorldGenerator redstoneGen;
/*     */   protected WorldGenerator diamondGen;
/*     */   protected WorldGenerator lapisGen;
/*  57 */   protected WorldGenFlowers yellowFlowerGen = new WorldGenFlowers(Blocks.YELLOW_FLOWER, BlockFlower.EnumFlowerType.DANDELION);
/*     */ 
/*     */   
/*  60 */   protected WorldGenerator mushroomBrownGen = (WorldGenerator)new WorldGenBush(Blocks.BROWN_MUSHROOM);
/*     */ 
/*     */   
/*  63 */   protected WorldGenerator mushroomRedGen = (WorldGenerator)new WorldGenBush(Blocks.RED_MUSHROOM);
/*     */ 
/*     */   
/*  66 */   protected WorldGenerator bigMushroomGen = (WorldGenerator)new WorldGenBigMushroom();
/*     */ 
/*     */   
/*  69 */   protected WorldGenerator reedGen = (WorldGenerator)new WorldGenReed();
/*     */ 
/*     */   
/*  72 */   protected WorldGenerator cactusGen = (WorldGenerator)new WorldGenCactus();
/*     */ 
/*     */   
/*  75 */   protected WorldGenerator waterlilyGen = (WorldGenerator)new WorldGenWaterlily();
/*     */ 
/*     */   
/*     */   protected int waterlilyPerChunk;
/*     */ 
/*     */   
/*     */   protected int treesPerChunk;
/*     */ 
/*     */   
/*  84 */   protected float extraTreeChance = 0.1F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   protected int flowersPerChunk = 2;
/*     */ 
/*     */   
/*  93 */   protected int grassPerChunk = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int deadBushPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int mushroomsPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int reedsPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int cactiPerChunk;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 119 */   protected int sandPerChunk = 1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 125 */   protected int sandPerChunk2 = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 130 */   protected int clayPerChunk = 1;
/*     */ 
/*     */   
/*     */   protected int bigMushroomsPerChunk;
/*     */ 
/*     */   
/*     */   public boolean generateLakes = true;
/*     */ 
/*     */   
/*     */   public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
/* 140 */     if (this.decorating)
/*     */     {
/* 142 */       throw new RuntimeException("Already decorating");
/*     */     }
/*     */ 
/*     */     
/* 146 */     this.chunkProviderSettings = ChunkGeneratorSettings.Factory.jsonToFactory(worldIn.getWorldInfo().getGeneratorOptions()).build();
/* 147 */     this.chunkPos = pos;
/* 148 */     this.dirtGen = (WorldGenerator)new WorldGenMinable(Blocks.DIRT.getDefaultState(), this.chunkProviderSettings.dirtSize);
/* 149 */     this.gravelGen = (WorldGenerator)new WorldGenMinable(Blocks.GRAVEL.getDefaultState(), this.chunkProviderSettings.gravelSize);
/* 150 */     this.graniteGen = (WorldGenerator)new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty((IProperty)BlockStone.VARIANT, (Comparable)BlockStone.EnumType.GRANITE), this.chunkProviderSettings.graniteSize);
/* 151 */     this.dioriteGen = (WorldGenerator)new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty((IProperty)BlockStone.VARIANT, (Comparable)BlockStone.EnumType.DIORITE), this.chunkProviderSettings.dioriteSize);
/* 152 */     this.andesiteGen = (WorldGenerator)new WorldGenMinable(Blocks.STONE.getDefaultState().withProperty((IProperty)BlockStone.VARIANT, (Comparable)BlockStone.EnumType.ANDESITE), this.chunkProviderSettings.andesiteSize);
/* 153 */     this.coalGen = (WorldGenerator)new WorldGenMinable(Blocks.COAL_ORE.getDefaultState(), this.chunkProviderSettings.coalSize);
/* 154 */     this.ironGen = (WorldGenerator)new WorldGenMinable(Blocks.IRON_ORE.getDefaultState(), this.chunkProviderSettings.ironSize);
/* 155 */     this.goldGen = (WorldGenerator)new WorldGenMinable(Blocks.GOLD_ORE.getDefaultState(), this.chunkProviderSettings.goldSize);
/* 156 */     this.redstoneGen = (WorldGenerator)new WorldGenMinable(Blocks.REDSTONE_ORE.getDefaultState(), this.chunkProviderSettings.redstoneSize);
/* 157 */     this.diamondGen = (WorldGenerator)new WorldGenMinable(Blocks.DIAMOND_ORE.getDefaultState(), this.chunkProviderSettings.diamondSize);
/* 158 */     this.lapisGen = (WorldGenerator)new WorldGenMinable(Blocks.LAPIS_ORE.getDefaultState(), this.chunkProviderSettings.lapisSize);
/* 159 */     genDecorations(biome, worldIn, random);
/* 160 */     this.decorating = false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void genDecorations(Biome biomeIn, World worldIn, Random random) {
/* 166 */     generateOres(worldIn, random);
/*     */     
/* 168 */     for (int i = 0; i < this.sandPerChunk2; i++) {
/*     */       
/* 170 */       int j = random.nextInt(16) + 8;
/* 171 */       int k = random.nextInt(16) + 8;
/* 172 */       this.sandGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
/*     */     } 
/*     */     
/* 175 */     for (int i1 = 0; i1 < this.clayPerChunk; i1++) {
/*     */       
/* 177 */       int l1 = random.nextInt(16) + 8;
/* 178 */       int i6 = random.nextInt(16) + 8;
/* 179 */       this.clayGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(l1, 0, i6)));
/*     */     } 
/*     */     
/* 182 */     for (int j1 = 0; j1 < this.sandPerChunk; j1++) {
/*     */       
/* 184 */       int i2 = random.nextInt(16) + 8;
/* 185 */       int j6 = random.nextInt(16) + 8;
/* 186 */       this.gravelAsSandGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(i2, 0, j6)));
/*     */     } 
/*     */     
/* 189 */     int k1 = this.treesPerChunk;
/*     */     
/* 191 */     if (random.nextFloat() < this.extraTreeChance)
/*     */     {
/* 193 */       k1++;
/*     */     }
/*     */     
/* 196 */     for (int j2 = 0; j2 < k1; j2++) {
/*     */       
/* 198 */       int k6 = random.nextInt(16) + 8;
/* 199 */       int l = random.nextInt(16) + 8;
/* 200 */       WorldGenAbstractTree worldgenabstracttree = biomeIn.genBigTreeChance(random);
/* 201 */       worldgenabstracttree.setDecorationDefaults();
/* 202 */       BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(k6, 0, l));
/*     */       
/* 204 */       if (worldgenabstracttree.generate(worldIn, random, blockpos))
/*     */       {
/* 206 */         worldgenabstracttree.generateSaplings(worldIn, random, blockpos);
/*     */       }
/*     */     } 
/*     */     
/* 210 */     for (int k2 = 0; k2 < this.bigMushroomsPerChunk; k2++) {
/*     */       
/* 212 */       int l6 = random.nextInt(16) + 8;
/* 213 */       int k10 = random.nextInt(16) + 8;
/* 214 */       this.bigMushroomGen.generate(worldIn, random, worldIn.getHeight(this.chunkPos.add(l6, 0, k10)));
/*     */     } 
/*     */     
/* 217 */     for (int l2 = 0; l2 < this.flowersPerChunk; l2++) {
/*     */       
/* 219 */       int i7 = random.nextInt(16) + 8;
/* 220 */       int l10 = random.nextInt(16) + 8;
/* 221 */       int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;
/*     */       
/* 223 */       if (j14 > 0) {
/*     */         
/* 225 */         int k17 = random.nextInt(j14);
/* 226 */         BlockPos blockpos1 = this.chunkPos.add(i7, k17, l10);
/* 227 */         BlockFlower.EnumFlowerType blockflower$enumflowertype = biomeIn.pickRandomFlower(random, blockpos1);
/* 228 */         BlockFlower blockflower = blockflower$enumflowertype.getBlockType().getBlock();
/*     */         
/* 230 */         if (blockflower.getDefaultState().getMaterial() != Material.AIR) {
/*     */           
/* 232 */           this.yellowFlowerGen.setGeneratedBlock(blockflower, blockflower$enumflowertype);
/* 233 */           this.yellowFlowerGen.generate(worldIn, random, blockpos1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 238 */     for (int i3 = 0; i3 < this.grassPerChunk; i3++) {
/*     */       
/* 240 */       int j7 = random.nextInt(16) + 8;
/* 241 */       int i11 = random.nextInt(16) + 8;
/* 242 */       int k14 = worldIn.getHeight(this.chunkPos.add(j7, 0, i11)).getY() * 2;
/*     */       
/* 244 */       if (k14 > 0) {
/*     */         
/* 246 */         int l17 = random.nextInt(k14);
/* 247 */         biomeIn.getRandomWorldGenForGrass(random).generate(worldIn, random, this.chunkPos.add(j7, l17, i11));
/*     */       } 
/*     */     } 
/*     */     
/* 251 */     for (int j3 = 0; j3 < this.deadBushPerChunk; j3++) {
/*     */       
/* 253 */       int k7 = random.nextInt(16) + 8;
/* 254 */       int j11 = random.nextInt(16) + 8;
/* 255 */       int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;
/*     */       
/* 257 */       if (l14 > 0) {
/*     */         
/* 259 */         int i18 = random.nextInt(l14);
/* 260 */         (new WorldGenDeadBush()).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
/*     */       } 
/*     */     } 
/*     */     
/* 264 */     for (int k3 = 0; k3 < this.waterlilyPerChunk; k3++) {
/*     */       
/* 266 */       int l7 = random.nextInt(16) + 8;
/* 267 */       int k11 = random.nextInt(16) + 8;
/* 268 */       int i15 = worldIn.getHeight(this.chunkPos.add(l7, 0, k11)).getY() * 2;
/*     */       
/* 270 */       if (i15 > 0) {
/*     */         
/* 272 */         int j18 = random.nextInt(i15);
/*     */         
/*     */         BlockPos blockpos4;
/*     */         
/* 276 */         for (blockpos4 = this.chunkPos.add(l7, j18, k11); blockpos4.getY() > 0; blockpos4 = blockpos7) {
/*     */           
/* 278 */           BlockPos blockpos7 = blockpos4.down();
/*     */           
/* 280 */           if (!worldIn.isAirBlock(blockpos7)) {
/*     */             break;
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 286 */         this.waterlilyGen.generate(worldIn, random, blockpos4);
/*     */       } 
/*     */     } 
/*     */     
/* 290 */     for (int l3 = 0; l3 < this.mushroomsPerChunk; l3++) {
/*     */       
/* 292 */       if (random.nextInt(4) == 0) {
/*     */         
/* 294 */         int i8 = random.nextInt(16) + 8;
/* 295 */         int l11 = random.nextInt(16) + 8;
/* 296 */         BlockPos blockpos2 = worldIn.getHeight(this.chunkPos.add(i8, 0, l11));
/* 297 */         this.mushroomBrownGen.generate(worldIn, random, blockpos2);
/*     */       } 
/*     */       
/* 300 */       if (random.nextInt(8) == 0) {
/*     */         
/* 302 */         int j8 = random.nextInt(16) + 8;
/* 303 */         int i12 = random.nextInt(16) + 8;
/* 304 */         int j15 = worldIn.getHeight(this.chunkPos.add(j8, 0, i12)).getY() * 2;
/*     */         
/* 306 */         if (j15 > 0) {
/*     */           
/* 308 */           int k18 = random.nextInt(j15);
/* 309 */           BlockPos blockpos5 = this.chunkPos.add(j8, k18, i12);
/* 310 */           this.mushroomRedGen.generate(worldIn, random, blockpos5);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 315 */     if (random.nextInt(4) == 0) {
/*     */       
/* 317 */       int i4 = random.nextInt(16) + 8;
/* 318 */       int k8 = random.nextInt(16) + 8;
/* 319 */       int j12 = worldIn.getHeight(this.chunkPos.add(i4, 0, k8)).getY() * 2;
/*     */       
/* 321 */       if (j12 > 0) {
/*     */         
/* 323 */         int k15 = random.nextInt(j12);
/* 324 */         this.mushroomBrownGen.generate(worldIn, random, this.chunkPos.add(i4, k15, k8));
/*     */       } 
/*     */     } 
/*     */     
/* 328 */     if (random.nextInt(8) == 0) {
/*     */       
/* 330 */       int j4 = random.nextInt(16) + 8;
/* 331 */       int l8 = random.nextInt(16) + 8;
/* 332 */       int k12 = worldIn.getHeight(this.chunkPos.add(j4, 0, l8)).getY() * 2;
/*     */       
/* 334 */       if (k12 > 0) {
/*     */         
/* 336 */         int l15 = random.nextInt(k12);
/* 337 */         this.mushroomRedGen.generate(worldIn, random, this.chunkPos.add(j4, l15, l8));
/*     */       } 
/*     */     } 
/*     */     
/* 341 */     for (int k4 = 0; k4 < this.reedsPerChunk; k4++) {
/*     */       
/* 343 */       int i9 = random.nextInt(16) + 8;
/* 344 */       int l12 = random.nextInt(16) + 8;
/* 345 */       int i16 = worldIn.getHeight(this.chunkPos.add(i9, 0, l12)).getY() * 2;
/*     */       
/* 347 */       if (i16 > 0) {
/*     */         
/* 349 */         int l18 = random.nextInt(i16);
/* 350 */         this.reedGen.generate(worldIn, random, this.chunkPos.add(i9, l18, l12));
/*     */       } 
/*     */     } 
/*     */     
/* 354 */     for (int l4 = 0; l4 < 10; l4++) {
/*     */       
/* 356 */       int j9 = random.nextInt(16) + 8;
/* 357 */       int i13 = random.nextInt(16) + 8;
/* 358 */       int j16 = worldIn.getHeight(this.chunkPos.add(j9, 0, i13)).getY() * 2;
/*     */       
/* 360 */       if (j16 > 0) {
/*     */         
/* 362 */         int i19 = random.nextInt(j16);
/* 363 */         this.reedGen.generate(worldIn, random, this.chunkPos.add(j9, i19, i13));
/*     */       } 
/*     */     } 
/*     */     
/* 367 */     if (random.nextInt(32) == 0) {
/*     */       
/* 369 */       int i5 = random.nextInt(16) + 8;
/* 370 */       int k9 = random.nextInt(16) + 8;
/* 371 */       int j13 = worldIn.getHeight(this.chunkPos.add(i5, 0, k9)).getY() * 2;
/*     */       
/* 373 */       if (j13 > 0) {
/*     */         
/* 375 */         int k16 = random.nextInt(j13);
/* 376 */         (new WorldGenPumpkin()).generate(worldIn, random, this.chunkPos.add(i5, k16, k9));
/*     */       } 
/*     */     } 
/*     */     
/* 380 */     for (int j5 = 0; j5 < this.cactiPerChunk; j5++) {
/*     */       
/* 382 */       int l9 = random.nextInt(16) + 8;
/* 383 */       int k13 = random.nextInt(16) + 8;
/* 384 */       int l16 = worldIn.getHeight(this.chunkPos.add(l9, 0, k13)).getY() * 2;
/*     */       
/* 386 */       if (l16 > 0) {
/*     */         
/* 388 */         int j19 = random.nextInt(l16);
/* 389 */         this.cactusGen.generate(worldIn, random, this.chunkPos.add(l9, j19, k13));
/*     */       } 
/*     */     } 
/*     */     
/* 393 */     if (this.generateLakes) {
/*     */       
/* 395 */       for (int k5 = 0; k5 < 50; k5++) {
/*     */         
/* 397 */         int i10 = random.nextInt(16) + 8;
/* 398 */         int l13 = random.nextInt(16) + 8;
/* 399 */         int i17 = random.nextInt(248) + 8;
/*     */         
/* 401 */         if (i17 > 0) {
/*     */           
/* 403 */           int k19 = random.nextInt(i17);
/* 404 */           BlockPos blockpos6 = this.chunkPos.add(i10, k19, l13);
/* 405 */           (new WorldGenLiquids((Block)Blocks.FLOWING_WATER)).generate(worldIn, random, blockpos6);
/*     */         } 
/*     */       } 
/*     */       
/* 409 */       for (int l5 = 0; l5 < 20; l5++) {
/*     */         
/* 411 */         int j10 = random.nextInt(16) + 8;
/* 412 */         int i14 = random.nextInt(16) + 8;
/* 413 */         int j17 = random.nextInt(random.nextInt(random.nextInt(240) + 8) + 8);
/* 414 */         BlockPos blockpos3 = this.chunkPos.add(j10, j17, i14);
/* 415 */         (new WorldGenLiquids((Block)Blocks.FLOWING_LAVA)).generate(worldIn, random, blockpos3);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void generateOres(World worldIn, Random random) {
/* 425 */     genStandardOre1(worldIn, random, this.chunkProviderSettings.dirtCount, this.dirtGen, this.chunkProviderSettings.dirtMinHeight, this.chunkProviderSettings.dirtMaxHeight);
/* 426 */     genStandardOre1(worldIn, random, this.chunkProviderSettings.gravelCount, this.gravelGen, this.chunkProviderSettings.gravelMinHeight, this.chunkProviderSettings.gravelMaxHeight);
/* 427 */     genStandardOre1(worldIn, random, this.chunkProviderSettings.dioriteCount, this.dioriteGen, this.chunkProviderSettings.dioriteMinHeight, this.chunkProviderSettings.dioriteMaxHeight);
/* 428 */     genStandardOre1(worldIn, random, this.chunkProviderSettings.graniteCount, this.graniteGen, this.chunkProviderSettings.graniteMinHeight, this.chunkProviderSettings.graniteMaxHeight);
/* 429 */     genStandardOre1(worldIn, random, this.chunkProviderSettings.andesiteCount, this.andesiteGen, this.chunkProviderSettings.andesiteMinHeight, this.chunkProviderSettings.andesiteMaxHeight);
/* 430 */     genStandardOre1(worldIn, random, this.chunkProviderSettings.coalCount, this.coalGen, this.chunkProviderSettings.coalMinHeight, this.chunkProviderSettings.coalMaxHeight);
/* 431 */     genStandardOre1(worldIn, random, this.chunkProviderSettings.ironCount, this.ironGen, this.chunkProviderSettings.ironMinHeight, this.chunkProviderSettings.ironMaxHeight);
/* 432 */     genStandardOre1(worldIn, random, this.chunkProviderSettings.goldCount, this.goldGen, this.chunkProviderSettings.goldMinHeight, this.chunkProviderSettings.goldMaxHeight);
/* 433 */     genStandardOre1(worldIn, random, this.chunkProviderSettings.redstoneCount, this.redstoneGen, this.chunkProviderSettings.redstoneMinHeight, this.chunkProviderSettings.redstoneMaxHeight);
/* 434 */     genStandardOre1(worldIn, random, this.chunkProviderSettings.diamondCount, this.diamondGen, this.chunkProviderSettings.diamondMinHeight, this.chunkProviderSettings.diamondMaxHeight);
/* 435 */     genStandardOre2(worldIn, random, this.chunkProviderSettings.lapisCount, this.lapisGen, this.chunkProviderSettings.lapisCenterHeight, this.chunkProviderSettings.lapisSpread);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void genStandardOre1(World worldIn, Random random, int blockCount, WorldGenerator generator, int minHeight, int maxHeight) {
/* 445 */     if (maxHeight < minHeight) {
/*     */       
/* 447 */       int i = minHeight;
/* 448 */       minHeight = maxHeight;
/* 449 */       maxHeight = i;
/*     */     }
/* 451 */     else if (maxHeight == minHeight) {
/*     */       
/* 453 */       if (minHeight < 255) {
/*     */         
/* 455 */         maxHeight++;
/*     */       }
/*     */       else {
/*     */         
/* 459 */         minHeight--;
/*     */       } 
/*     */     } 
/*     */     
/* 463 */     for (int j = 0; j < blockCount; j++) {
/*     */       
/* 465 */       BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(maxHeight - minHeight) + minHeight, random.nextInt(16));
/* 466 */       generator.generate(worldIn, random, blockpos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void genStandardOre2(World worldIn, Random random, int blockCount, WorldGenerator generator, int centerHeight, int spread) {
/* 477 */     for (int i = 0; i < blockCount; i++) {
/*     */       
/* 479 */       BlockPos blockpos = this.chunkPos.add(random.nextInt(16), random.nextInt(spread) + random.nextInt(spread) + centerHeight - spread, random.nextInt(16));
/* 480 */       generator.generate(worldIn, random, blockpos);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeDecorator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */