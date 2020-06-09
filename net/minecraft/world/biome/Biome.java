/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.block.BlockSand;
/*     */ import net.minecraft.block.BlockTallGrass;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.entity.monster.EntityCreeper;
/*     */ import net.minecraft.entity.monster.EntityEnderman;
/*     */ import net.minecraft.entity.monster.EntitySkeleton;
/*     */ import net.minecraft.entity.monster.EntitySlime;
/*     */ import net.minecraft.entity.monster.EntitySpider;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.entity.monster.EntityZombie;
/*     */ import net.minecraft.entity.monster.EntityZombieVillager;
/*     */ import net.minecraft.entity.passive.EntityBat;
/*     */ import net.minecraft.entity.passive.EntityChicken;
/*     */ import net.minecraft.entity.passive.EntityCow;
/*     */ import net.minecraft.entity.passive.EntityPig;
/*     */ import net.minecraft.entity.passive.EntitySheep;
/*     */ import net.minecraft.entity.passive.EntitySquid;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.ObjectIntIdentityMap;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.registry.RegistryNamespaced;
/*     */ import net.minecraft.world.ColorizerFoliage;
/*     */ import net.minecraft.world.ColorizerGrass;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.NoiseGeneratorPerlin;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenDoublePlant;
/*     */ import net.minecraft.world.gen.feature.WorldGenSwamp;
/*     */ import net.minecraft.world.gen.feature.WorldGenTallGrass;
/*     */ import net.minecraft.world.gen.feature.WorldGenTrees;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public abstract class Biome {
/*  53 */   private static final Logger LOGGER = LogManager.getLogger();
/*  54 */   protected static final IBlockState STONE = Blocks.STONE.getDefaultState();
/*  55 */   protected static final IBlockState AIR = Blocks.AIR.getDefaultState();
/*  56 */   protected static final IBlockState BEDROCK = Blocks.BEDROCK.getDefaultState();
/*  57 */   protected static final IBlockState GRAVEL = Blocks.GRAVEL.getDefaultState();
/*  58 */   protected static final IBlockState RED_SANDSTONE = Blocks.RED_SANDSTONE.getDefaultState();
/*  59 */   protected static final IBlockState SANDSTONE = Blocks.SANDSTONE.getDefaultState();
/*  60 */   protected static final IBlockState ICE = Blocks.ICE.getDefaultState();
/*  61 */   protected static final IBlockState WATER = Blocks.WATER.getDefaultState();
/*  62 */   public static final ObjectIntIdentityMap<Biome> MUTATION_TO_BASE_ID_MAP = new ObjectIntIdentityMap();
/*  63 */   protected static final NoiseGeneratorPerlin TEMPERATURE_NOISE = new NoiseGeneratorPerlin(new Random(1234L), 1);
/*  64 */   protected static final NoiseGeneratorPerlin GRASS_COLOR_NOISE = new NoiseGeneratorPerlin(new Random(2345L), 1);
/*  65 */   protected static final WorldGenDoublePlant DOUBLE_PLANT_GENERATOR = new WorldGenDoublePlant();
/*     */ 
/*     */   
/*  68 */   protected static final WorldGenTrees TREE_FEATURE = new WorldGenTrees(false);
/*     */ 
/*     */   
/*  71 */   protected static final WorldGenBigTree BIG_TREE_FEATURE = new WorldGenBigTree(false);
/*     */ 
/*     */   
/*  74 */   protected static final WorldGenSwamp SWAMP_FEATURE = new WorldGenSwamp();
/*  75 */   public static final RegistryNamespaced<ResourceLocation, Biome> REGISTRY = new RegistryNamespaced();
/*     */ 
/*     */   
/*     */   private final String biomeName;
/*     */ 
/*     */   
/*     */   private final float baseHeight;
/*     */ 
/*     */   
/*     */   private final float heightVariation;
/*     */ 
/*     */   
/*     */   private final float temperature;
/*     */ 
/*     */   
/*     */   private final float rainfall;
/*     */ 
/*     */   
/*     */   private final int waterColor;
/*     */ 
/*     */   
/*     */   private final boolean enableSnow;
/*     */ 
/*     */   
/*     */   private final boolean enableRain;
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   private final String baseBiomeRegName;
/*     */ 
/*     */   
/* 106 */   public IBlockState topBlock = Blocks.GRASS.getDefaultState();
/*     */ 
/*     */   
/* 109 */   public IBlockState fillerBlock = Blocks.DIRT.getDefaultState();
/*     */   
/*     */   public BiomeDecorator theBiomeDecorator;
/*     */   
/* 113 */   protected List<SpawnListEntry> spawnableMonsterList = Lists.newArrayList();
/* 114 */   protected List<SpawnListEntry> spawnableCreatureList = Lists.newArrayList();
/* 115 */   protected List<SpawnListEntry> spawnableWaterCreatureList = Lists.newArrayList();
/* 116 */   protected List<SpawnListEntry> spawnableCaveCreatureList = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public static int getIdForBiome(Biome biome) {
/* 120 */     return REGISTRY.getIDForObject(biome);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Biome getBiomeForId(int id) {
/* 126 */     return (Biome)REGISTRY.getObjectById(id);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Biome getMutationForBiome(Biome biome) {
/* 132 */     return (Biome)MUTATION_TO_BASE_ID_MAP.getByValue(getIdForBiome(biome));
/*     */   }
/*     */ 
/*     */   
/*     */   protected Biome(BiomeProperties properties) {
/* 137 */     this.biomeName = properties.biomeName;
/* 138 */     this.baseHeight = properties.baseHeight;
/* 139 */     this.heightVariation = properties.heightVariation;
/* 140 */     this.temperature = properties.temperature;
/* 141 */     this.rainfall = properties.rainfall;
/* 142 */     this.waterColor = properties.waterColor;
/* 143 */     this.enableSnow = properties.enableSnow;
/* 144 */     this.enableRain = properties.enableRain;
/* 145 */     this.baseBiomeRegName = properties.baseBiomeRegName;
/* 146 */     this.theBiomeDecorator = createBiomeDecorator();
/* 147 */     this.spawnableCreatureList.add(new SpawnListEntry((Class)EntitySheep.class, 12, 4, 4));
/* 148 */     this.spawnableCreatureList.add(new SpawnListEntry((Class)EntityPig.class, 10, 4, 4));
/* 149 */     this.spawnableCreatureList.add(new SpawnListEntry((Class)EntityChicken.class, 10, 4, 4));
/* 150 */     this.spawnableCreatureList.add(new SpawnListEntry((Class)EntityCow.class, 8, 4, 4));
/* 151 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntitySpider.class, 100, 4, 4));
/* 152 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntityZombie.class, 95, 4, 4));
/* 153 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntityZombieVillager.class, 5, 1, 1));
/* 154 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntitySkeleton.class, 100, 4, 4));
/* 155 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntityCreeper.class, 100, 4, 4));
/* 156 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntitySlime.class, 100, 4, 4));
/* 157 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntityEnderman.class, 10, 1, 4));
/* 158 */     this.spawnableMonsterList.add(new SpawnListEntry((Class)EntityWitch.class, 5, 1, 1));
/* 159 */     this.spawnableWaterCreatureList.add(new SpawnListEntry((Class)EntitySquid.class, 10, 4, 4));
/* 160 */     this.spawnableCaveCreatureList.add(new SpawnListEntry((Class)EntityBat.class, 10, 8, 8));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected BiomeDecorator createBiomeDecorator() {
/* 168 */     return new BiomeDecorator();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMutation() {
/* 173 */     return (this.baseBiomeRegName != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 178 */     return (rand.nextInt(10) == 0) ? (WorldGenAbstractTree)BIG_TREE_FEATURE : (WorldGenAbstractTree)TREE_FEATURE;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenerator getRandomWorldGenForGrass(Random rand) {
/* 186 */     return (WorldGenerator)new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
/* 191 */     return (rand.nextInt(3) > 0) ? BlockFlower.EnumFlowerType.DANDELION : BlockFlower.EnumFlowerType.POPPY;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkyColorByTemp(float currentTemperature) {
/* 199 */     currentTemperature /= 3.0F;
/* 200 */     currentTemperature = MathHelper.clamp(currentTemperature, -1.0F, 1.0F);
/* 201 */     return MathHelper.hsvToRGB(0.62222224F - currentTemperature * 0.05F, 0.5F + currentTemperature * 0.1F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<SpawnListEntry> getSpawnableList(EnumCreatureType creatureType) {
/* 206 */     switch (creatureType) {
/*     */       
/*     */       case MONSTER:
/* 209 */         return this.spawnableMonsterList;
/*     */       
/*     */       case CREATURE:
/* 212 */         return this.spawnableCreatureList;
/*     */       
/*     */       case WATER_CREATURE:
/* 215 */         return this.spawnableWaterCreatureList;
/*     */       
/*     */       case null:
/* 218 */         return this.spawnableCaveCreatureList;
/*     */     } 
/*     */     
/* 221 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getEnableSnow() {
/* 230 */     return isSnowyBiome();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRain() {
/* 238 */     return isSnowyBiome() ? false : this.enableRain;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHighHumidity() {
/* 246 */     return (getRainfall() > 0.85F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getSpawningChance() {
/* 254 */     return 0.1F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getFloatTemperature(BlockPos pos) {
/* 262 */     if (pos.getY() > 64) {
/*     */       
/* 264 */       float f = (float)(TEMPERATURE_NOISE.getValue((pos.getX() / 8.0F), (pos.getZ() / 8.0F)) * 4.0D);
/* 265 */       return getTemperature() - (f + pos.getY() - 64.0F) * 0.05F / 30.0F;
/*     */     } 
/*     */ 
/*     */     
/* 269 */     return getTemperature();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 275 */     this.theBiomeDecorator.decorate(worldIn, rand, this, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGrassColorAtPos(BlockPos pos) {
/* 280 */     double d0 = MathHelper.clamp(getFloatTemperature(pos), 0.0F, 1.0F);
/* 281 */     double d1 = MathHelper.clamp(getRainfall(), 0.0F, 1.0F);
/* 282 */     return ColorizerGrass.getGrassColor(d0, d1);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getFoliageColorAtPos(BlockPos pos) {
/* 287 */     double d0 = MathHelper.clamp(getFloatTemperature(pos), 0.0F, 1.0F);
/* 288 */     double d1 = MathHelper.clamp(getRainfall(), 0.0F, 1.0F);
/* 289 */     return ColorizerFoliage.getFoliageColor(d0, d1);
/*     */   }
/*     */ 
/*     */   
/*     */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 294 */     generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*     */   }
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
/*     */   public final void generateBiomeTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 309 */     int i = worldIn.getSeaLevel();
/* 310 */     IBlockState iblockstate = this.topBlock;
/* 311 */     IBlockState iblockstate1 = this.fillerBlock;
/* 312 */     int j = -1;
/* 313 */     int k = (int)(noiseVal / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
/* 314 */     int l = x & 0xF;
/* 315 */     int i1 = z & 0xF;
/* 316 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 318 */     for (int j1 = 255; j1 >= 0; j1--) {
/*     */       
/* 320 */       if (j1 <= rand.nextInt(5)) {
/*     */         
/* 322 */         chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
/*     */       }
/*     */       else {
/*     */         
/* 326 */         IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);
/*     */         
/* 328 */         if (iblockstate2.getMaterial() == Material.AIR) {
/*     */           
/* 330 */           j = -1;
/*     */         }
/* 332 */         else if (iblockstate2.getBlock() == Blocks.STONE) {
/*     */           
/* 334 */           if (j == -1) {
/*     */             
/* 336 */             if (k <= 0) {
/*     */               
/* 338 */               iblockstate = AIR;
/* 339 */               iblockstate1 = STONE;
/*     */             }
/* 341 */             else if (j1 >= i - 4 && j1 <= i + 1) {
/*     */               
/* 343 */               iblockstate = this.topBlock;
/* 344 */               iblockstate1 = this.fillerBlock;
/*     */             } 
/*     */             
/* 347 */             if (j1 < i && (iblockstate == null || iblockstate.getMaterial() == Material.AIR))
/*     */             {
/* 349 */               if (getFloatTemperature((BlockPos)blockpos$mutableblockpos.setPos(x, j1, z)) < 0.15F) {
/*     */                 
/* 351 */                 iblockstate = ICE;
/*     */               }
/*     */               else {
/*     */                 
/* 355 */                 iblockstate = WATER;
/*     */               } 
/*     */             }
/*     */             
/* 359 */             j = k;
/*     */             
/* 361 */             if (j1 >= i - 1)
/*     */             {
/* 363 */               chunkPrimerIn.setBlockState(i1, j1, l, iblockstate);
/*     */             }
/* 365 */             else if (j1 < i - 7 - k)
/*     */             {
/* 367 */               iblockstate = AIR;
/* 368 */               iblockstate1 = STONE;
/* 369 */               chunkPrimerIn.setBlockState(i1, j1, l, GRAVEL);
/*     */             }
/*     */             else
/*     */             {
/* 373 */               chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
/*     */             }
/*     */           
/* 376 */           } else if (j > 0) {
/*     */             
/* 378 */             j--;
/* 379 */             chunkPrimerIn.setBlockState(i1, j1, l, iblockstate1);
/*     */             
/* 381 */             if (j == 0 && iblockstate1.getBlock() == Blocks.SAND && k > 1) {
/*     */               
/* 383 */               j = rand.nextInt(4) + Math.max(0, j1 - 63);
/* 384 */               iblockstate1 = (iblockstate1.getValue((IProperty)BlockSand.VARIANT) == BlockSand.EnumType.RED_SAND) ? RED_SANDSTONE : SANDSTONE;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Class<? extends Biome> getBiomeClass() {
/* 394 */     return (Class)getClass();
/*     */   }
/*     */ 
/*     */   
/*     */   public TempCategory getTempCategory() {
/* 399 */     if (getTemperature() < 0.2D)
/*     */     {
/* 401 */       return TempCategory.COLD;
/*     */     }
/*     */ 
/*     */     
/* 405 */     return (getTemperature() < 1.0D) ? TempCategory.MEDIUM : TempCategory.WARM;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static Biome getBiome(int id) {
/* 416 */     return getBiome(id, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static Biome getBiome(int biomeId, Biome fallback) {
/* 421 */     Biome biome = getBiomeForId(biomeId);
/* 422 */     return (biome == null) ? fallback : biome;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean ignorePlayerSpawnSuitability() {
/* 427 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public final float getBaseHeight() {
/* 432 */     return this.baseHeight;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final float getRainfall() {
/* 440 */     return this.rainfall;
/*     */   }
/*     */ 
/*     */   
/*     */   public final String getBiomeName() {
/* 445 */     return this.biomeName;
/*     */   }
/*     */ 
/*     */   
/*     */   public final float getHeightVariation() {
/* 450 */     return this.heightVariation;
/*     */   }
/*     */ 
/*     */   
/*     */   public final float getTemperature() {
/* 455 */     return this.temperature;
/*     */   }
/*     */ 
/*     */   
/*     */   public final int getWaterColor() {
/* 460 */     return this.waterColor;
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean isSnowyBiome() {
/* 465 */     return this.enableSnow;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerBiomes() {
/* 473 */     registerBiome(0, "ocean", new BiomeOcean((new BiomeProperties("Ocean")).setBaseHeight(-1.0F).setHeightVariation(0.1F)));
/* 474 */     registerBiome(1, "plains", new BiomePlains(false, (new BiomeProperties("Plains")).setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F).setRainfall(0.4F)));
/* 475 */     registerBiome(2, "desert", new BiomeDesert((new BiomeProperties("Desert")).setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled()));
/* 476 */     registerBiome(3, "extreme_hills", new BiomeHills(BiomeHills.Type.NORMAL, (new BiomeProperties("Extreme Hills")).setBaseHeight(1.0F).setHeightVariation(0.5F).setTemperature(0.2F).setRainfall(0.3F)));
/* 477 */     registerBiome(4, "forest", new BiomeForest(BiomeForest.Type.NORMAL, (new BiomeProperties("Forest")).setTemperature(0.7F).setRainfall(0.8F)));
/* 478 */     registerBiome(5, "taiga", new BiomeTaiga(BiomeTaiga.Type.NORMAL, (new BiomeProperties("Taiga")).setBaseHeight(0.2F).setHeightVariation(0.2F).setTemperature(0.25F).setRainfall(0.8F)));
/* 479 */     registerBiome(6, "swampland", new BiomeSwamp((new BiomeProperties("Swampland")).setBaseHeight(-0.2F).setHeightVariation(0.1F).setTemperature(0.8F).setRainfall(0.9F).setWaterColor(14745518)));
/* 480 */     registerBiome(7, "river", new BiomeRiver((new BiomeProperties("River")).setBaseHeight(-0.5F).setHeightVariation(0.0F)));
/* 481 */     registerBiome(8, "hell", new BiomeHell((new BiomeProperties("Hell")).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled()));
/* 482 */     registerBiome(9, "sky", new BiomeEnd((new BiomeProperties("The End")).setRainDisabled()));
/* 483 */     registerBiome(10, "frozen_ocean", new BiomeOcean((new BiomeProperties("FrozenOcean")).setBaseHeight(-1.0F).setHeightVariation(0.1F).setTemperature(0.0F).setRainfall(0.5F).setSnowEnabled()));
/* 484 */     registerBiome(11, "frozen_river", new BiomeRiver((new BiomeProperties("FrozenRiver")).setBaseHeight(-0.5F).setHeightVariation(0.0F).setTemperature(0.0F).setRainfall(0.5F).setSnowEnabled()));
/* 485 */     registerBiome(12, "ice_flats", new BiomeSnow(false, (new BiomeProperties("Ice Plains")).setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.0F).setRainfall(0.5F).setSnowEnabled()));
/* 486 */     registerBiome(13, "ice_mountains", new BiomeSnow(false, (new BiomeProperties("Ice Mountains")).setBaseHeight(0.45F).setHeightVariation(0.3F).setTemperature(0.0F).setRainfall(0.5F).setSnowEnabled()));
/* 487 */     registerBiome(14, "mushroom_island", new BiomeMushroomIsland((new BiomeProperties("MushroomIsland")).setBaseHeight(0.2F).setHeightVariation(0.3F).setTemperature(0.9F).setRainfall(1.0F)));
/* 488 */     registerBiome(15, "mushroom_island_shore", new BiomeMushroomIsland((new BiomeProperties("MushroomIslandShore")).setBaseHeight(0.0F).setHeightVariation(0.025F).setTemperature(0.9F).setRainfall(1.0F)));
/* 489 */     registerBiome(16, "beaches", new BiomeBeach((new BiomeProperties("Beach")).setBaseHeight(0.0F).setHeightVariation(0.025F).setTemperature(0.8F).setRainfall(0.4F)));
/* 490 */     registerBiome(17, "desert_hills", new BiomeDesert((new BiomeProperties("DesertHills")).setBaseHeight(0.45F).setHeightVariation(0.3F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled()));
/* 491 */     registerBiome(18, "forest_hills", new BiomeForest(BiomeForest.Type.NORMAL, (new BiomeProperties("ForestHills")).setBaseHeight(0.45F).setHeightVariation(0.3F).setTemperature(0.7F).setRainfall(0.8F)));
/* 492 */     registerBiome(19, "taiga_hills", new BiomeTaiga(BiomeTaiga.Type.NORMAL, (new BiomeProperties("TaigaHills")).setTemperature(0.25F).setRainfall(0.8F).setBaseHeight(0.45F).setHeightVariation(0.3F)));
/* 493 */     registerBiome(20, "smaller_extreme_hills", new BiomeHills(BiomeHills.Type.EXTRA_TREES, (new BiomeProperties("Extreme Hills Edge")).setBaseHeight(0.8F).setHeightVariation(0.3F).setTemperature(0.2F).setRainfall(0.3F)));
/* 494 */     registerBiome(21, "jungle", new BiomeJungle(false, (new BiomeProperties("Jungle")).setTemperature(0.95F).setRainfall(0.9F)));
/* 495 */     registerBiome(22, "jungle_hills", new BiomeJungle(false, (new BiomeProperties("JungleHills")).setBaseHeight(0.45F).setHeightVariation(0.3F).setTemperature(0.95F).setRainfall(0.9F)));
/* 496 */     registerBiome(23, "jungle_edge", new BiomeJungle(true, (new BiomeProperties("JungleEdge")).setTemperature(0.95F).setRainfall(0.8F)));
/* 497 */     registerBiome(24, "deep_ocean", new BiomeOcean((new BiomeProperties("Deep Ocean")).setBaseHeight(-1.8F).setHeightVariation(0.1F)));
/* 498 */     registerBiome(25, "stone_beach", new BiomeStoneBeach((new BiomeProperties("Stone Beach")).setBaseHeight(0.1F).setHeightVariation(0.8F).setTemperature(0.2F).setRainfall(0.3F)));
/* 499 */     registerBiome(26, "cold_beach", new BiomeBeach((new BiomeProperties("Cold Beach")).setBaseHeight(0.0F).setHeightVariation(0.025F).setTemperature(0.05F).setRainfall(0.3F).setSnowEnabled()));
/* 500 */     registerBiome(27, "birch_forest", new BiomeForest(BiomeForest.Type.BIRCH, (new BiomeProperties("Birch Forest")).setTemperature(0.6F).setRainfall(0.6F)));
/* 501 */     registerBiome(28, "birch_forest_hills", new BiomeForest(BiomeForest.Type.BIRCH, (new BiomeProperties("Birch Forest Hills")).setBaseHeight(0.45F).setHeightVariation(0.3F).setTemperature(0.6F).setRainfall(0.6F)));
/* 502 */     registerBiome(29, "roofed_forest", new BiomeForest(BiomeForest.Type.ROOFED, (new BiomeProperties("Roofed Forest")).setTemperature(0.7F).setRainfall(0.8F)));
/* 503 */     registerBiome(30, "taiga_cold", new BiomeTaiga(BiomeTaiga.Type.NORMAL, (new BiomeProperties("Cold Taiga")).setBaseHeight(0.2F).setHeightVariation(0.2F).setTemperature(-0.5F).setRainfall(0.4F).setSnowEnabled()));
/* 504 */     registerBiome(31, "taiga_cold_hills", new BiomeTaiga(BiomeTaiga.Type.NORMAL, (new BiomeProperties("Cold Taiga Hills")).setBaseHeight(0.45F).setHeightVariation(0.3F).setTemperature(-0.5F).setRainfall(0.4F).setSnowEnabled()));
/* 505 */     registerBiome(32, "redwood_taiga", new BiomeTaiga(BiomeTaiga.Type.MEGA, (new BiomeProperties("Mega Taiga")).setTemperature(0.3F).setRainfall(0.8F).setBaseHeight(0.2F).setHeightVariation(0.2F)));
/* 506 */     registerBiome(33, "redwood_taiga_hills", new BiomeTaiga(BiomeTaiga.Type.MEGA, (new BiomeProperties("Mega Taiga Hills")).setBaseHeight(0.45F).setHeightVariation(0.3F).setTemperature(0.3F).setRainfall(0.8F)));
/* 507 */     registerBiome(34, "extreme_hills_with_trees", new BiomeHills(BiomeHills.Type.EXTRA_TREES, (new BiomeProperties("Extreme Hills+")).setBaseHeight(1.0F).setHeightVariation(0.5F).setTemperature(0.2F).setRainfall(0.3F)));
/* 508 */     registerBiome(35, "savanna", new BiomeSavanna((new BiomeProperties("Savanna")).setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(1.2F).setRainfall(0.0F).setRainDisabled()));
/* 509 */     registerBiome(36, "savanna_rock", new BiomeSavanna((new BiomeProperties("Savanna Plateau")).setBaseHeight(1.5F).setHeightVariation(0.025F).setTemperature(1.0F).setRainfall(0.0F).setRainDisabled()));
/* 510 */     registerBiome(37, "mesa", new BiomeMesa(false, false, (new BiomeProperties("Mesa")).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled()));
/* 511 */     registerBiome(38, "mesa_rock", new BiomeMesa(false, true, (new BiomeProperties("Mesa Plateau F")).setBaseHeight(1.5F).setHeightVariation(0.025F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled()));
/* 512 */     registerBiome(39, "mesa_clear_rock", new BiomeMesa(false, false, (new BiomeProperties("Mesa Plateau")).setBaseHeight(1.5F).setHeightVariation(0.025F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled()));
/* 513 */     registerBiome(127, "void", new BiomeVoid((new BiomeProperties("The Void")).setRainDisabled()));
/* 514 */     registerBiome(129, "mutated_plains", new BiomePlains(true, (new BiomeProperties("Sunflower Plains")).setBaseBiome("plains").setBaseHeight(0.125F).setHeightVariation(0.05F).setTemperature(0.8F).setRainfall(0.4F)));
/* 515 */     registerBiome(130, "mutated_desert", new BiomeDesert((new BiomeProperties("Desert M")).setBaseBiome("desert").setBaseHeight(0.225F).setHeightVariation(0.25F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled()));
/* 516 */     registerBiome(131, "mutated_extreme_hills", new BiomeHills(BiomeHills.Type.MUTATED, (new BiomeProperties("Extreme Hills M")).setBaseBiome("extreme_hills").setBaseHeight(1.0F).setHeightVariation(0.5F).setTemperature(0.2F).setRainfall(0.3F)));
/* 517 */     registerBiome(132, "mutated_forest", new BiomeForest(BiomeForest.Type.FLOWER, (new BiomeProperties("Flower Forest")).setBaseBiome("forest").setHeightVariation(0.4F).setTemperature(0.7F).setRainfall(0.8F)));
/* 518 */     registerBiome(133, "mutated_taiga", new BiomeTaiga(BiomeTaiga.Type.NORMAL, (new BiomeProperties("Taiga M")).setBaseBiome("taiga").setBaseHeight(0.3F).setHeightVariation(0.4F).setTemperature(0.25F).setRainfall(0.8F)));
/* 519 */     registerBiome(134, "mutated_swampland", new BiomeSwamp((new BiomeProperties("Swampland M")).setBaseBiome("swampland").setBaseHeight(-0.1F).setHeightVariation(0.3F).setTemperature(0.8F).setRainfall(0.9F).setWaterColor(14745518)));
/* 520 */     registerBiome(140, "mutated_ice_flats", new BiomeSnow(true, (new BiomeProperties("Ice Plains Spikes")).setBaseBiome("ice_flats").setBaseHeight(0.425F).setHeightVariation(0.45000002F).setTemperature(0.0F).setRainfall(0.5F).setSnowEnabled()));
/* 521 */     registerBiome(149, "mutated_jungle", new BiomeJungle(false, (new BiomeProperties("Jungle M")).setBaseBiome("jungle").setBaseHeight(0.2F).setHeightVariation(0.4F).setTemperature(0.95F).setRainfall(0.9F)));
/* 522 */     registerBiome(151, "mutated_jungle_edge", new BiomeJungle(true, (new BiomeProperties("JungleEdge M")).setBaseBiome("jungle_edge").setBaseHeight(0.2F).setHeightVariation(0.4F).setTemperature(0.95F).setRainfall(0.8F)));
/* 523 */     registerBiome(155, "mutated_birch_forest", new BiomeForestMutated((new BiomeProperties("Birch Forest M")).setBaseBiome("birch_forest").setBaseHeight(0.2F).setHeightVariation(0.4F).setTemperature(0.6F).setRainfall(0.6F)));
/* 524 */     registerBiome(156, "mutated_birch_forest_hills", new BiomeForestMutated((new BiomeProperties("Birch Forest Hills M")).setBaseBiome("birch_forest_hills").setBaseHeight(0.55F).setHeightVariation(0.5F).setTemperature(0.6F).setRainfall(0.6F)));
/* 525 */     registerBiome(157, "mutated_roofed_forest", new BiomeForest(BiomeForest.Type.ROOFED, (new BiomeProperties("Roofed Forest M")).setBaseBiome("roofed_forest").setBaseHeight(0.2F).setHeightVariation(0.4F).setTemperature(0.7F).setRainfall(0.8F)));
/* 526 */     registerBiome(158, "mutated_taiga_cold", new BiomeTaiga(BiomeTaiga.Type.NORMAL, (new BiomeProperties("Cold Taiga M")).setBaseBiome("taiga_cold").setBaseHeight(0.3F).setHeightVariation(0.4F).setTemperature(-0.5F).setRainfall(0.4F).setSnowEnabled()));
/* 527 */     registerBiome(160, "mutated_redwood_taiga", new BiomeTaiga(BiomeTaiga.Type.MEGA_SPRUCE, (new BiomeProperties("Mega Spruce Taiga")).setBaseBiome("redwood_taiga").setBaseHeight(0.2F).setHeightVariation(0.2F).setTemperature(0.25F).setRainfall(0.8F)));
/* 528 */     registerBiome(161, "mutated_redwood_taiga_hills", new BiomeTaiga(BiomeTaiga.Type.MEGA_SPRUCE, (new BiomeProperties("Redwood Taiga Hills M")).setBaseBiome("redwood_taiga_hills").setBaseHeight(0.2F).setHeightVariation(0.2F).setTemperature(0.25F).setRainfall(0.8F)));
/* 529 */     registerBiome(162, "mutated_extreme_hills_with_trees", new BiomeHills(BiomeHills.Type.MUTATED, (new BiomeProperties("Extreme Hills+ M")).setBaseBiome("extreme_hills_with_trees").setBaseHeight(1.0F).setHeightVariation(0.5F).setTemperature(0.2F).setRainfall(0.3F)));
/* 530 */     registerBiome(163, "mutated_savanna", new BiomeSavannaMutated((new BiomeProperties("Savanna M")).setBaseBiome("savanna").setBaseHeight(0.3625F).setHeightVariation(1.225F).setTemperature(1.1F).setRainfall(0.0F).setRainDisabled()));
/* 531 */     registerBiome(164, "mutated_savanna_rock", new BiomeSavannaMutated((new BiomeProperties("Savanna Plateau M")).setBaseBiome("savanna_rock").setBaseHeight(1.05F).setHeightVariation(1.2125001F).setTemperature(1.0F).setRainfall(0.0F).setRainDisabled()));
/* 532 */     registerBiome(165, "mutated_mesa", new BiomeMesa(true, false, (new BiomeProperties("Mesa (Bryce)")).setBaseBiome("mesa").setTemperature(2.0F).setRainfall(0.0F).setRainDisabled()));
/* 533 */     registerBiome(166, "mutated_mesa_rock", new BiomeMesa(false, true, (new BiomeProperties("Mesa Plateau F M")).setBaseBiome("mesa_rock").setBaseHeight(0.45F).setHeightVariation(0.3F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled()));
/* 534 */     registerBiome(167, "mutated_mesa_clear_rock", new BiomeMesa(false, false, (new BiomeProperties("Mesa Plateau M")).setBaseBiome("mesa_clear_rock").setBaseHeight(0.45F).setHeightVariation(0.3F).setTemperature(2.0F).setRainfall(0.0F).setRainDisabled()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void registerBiome(int id, String name, Biome biome) {
/* 542 */     REGISTRY.register(id, new ResourceLocation(name), biome);
/*     */     
/* 544 */     if (biome.isMutation())
/*     */     {
/* 546 */       MUTATION_TO_BASE_ID_MAP.put(biome, getIdForBiome((Biome)REGISTRY.getObject(new ResourceLocation(biome.baseBiomeRegName))));
/*     */     }
/*     */   }
/*     */   
/*     */   public static class BiomeProperties
/*     */   {
/*     */     private final String biomeName;
/* 553 */     private float baseHeight = 0.1F;
/* 554 */     private float heightVariation = 0.2F;
/* 555 */     private float temperature = 0.5F;
/* 556 */     private float rainfall = 0.5F;
/* 557 */     private int waterColor = 16777215;
/*     */     
/*     */     private boolean enableSnow;
/*     */     private boolean enableRain = true;
/*     */     @Nullable
/*     */     private String baseBiomeRegName;
/*     */     
/*     */     public BiomeProperties(String nameIn) {
/* 565 */       this.biomeName = nameIn;
/*     */     }
/*     */ 
/*     */     
/*     */     protected BiomeProperties setTemperature(float temperatureIn) {
/* 570 */       if (temperatureIn > 0.1F && temperatureIn < 0.2F)
/*     */       {
/* 572 */         throw new IllegalArgumentException("Please avoid temperatures in the range 0.1 - 0.2 because of snow");
/*     */       }
/*     */ 
/*     */       
/* 576 */       this.temperature = temperatureIn;
/* 577 */       return this;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected BiomeProperties setRainfall(float rainfallIn) {
/* 583 */       this.rainfall = rainfallIn;
/* 584 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected BiomeProperties setBaseHeight(float baseHeightIn) {
/* 589 */       this.baseHeight = baseHeightIn;
/* 590 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected BiomeProperties setHeightVariation(float heightVariationIn) {
/* 595 */       this.heightVariation = heightVariationIn;
/* 596 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected BiomeProperties setRainDisabled() {
/* 601 */       this.enableRain = false;
/* 602 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected BiomeProperties setSnowEnabled() {
/* 607 */       this.enableSnow = true;
/* 608 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected BiomeProperties setWaterColor(int waterColorIn) {
/* 613 */       this.waterColor = waterColorIn;
/* 614 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected BiomeProperties setBaseBiome(String nameIn) {
/* 619 */       this.baseBiomeRegName = nameIn;
/* 620 */       return this;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class SpawnListEntry
/*     */     extends WeightedRandom.Item
/*     */   {
/*     */     public Class<? extends EntityLiving> entityClass;
/*     */     public int minGroupCount;
/*     */     public int maxGroupCount;
/*     */     
/*     */     public SpawnListEntry(Class<? extends EntityLiving> entityclassIn, int weight, int groupCountMin, int groupCountMax) {
/* 632 */       super(weight);
/* 633 */       this.entityClass = entityclassIn;
/* 634 */       this.minGroupCount = groupCountMin;
/* 635 */       this.maxGroupCount = groupCountMax;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 640 */       return String.valueOf(this.entityClass.getSimpleName()) + "*(" + this.minGroupCount + "-" + this.maxGroupCount + "):" + this.itemWeight;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum TempCategory
/*     */   {
/* 646 */     OCEAN,
/* 647 */     COLD,
/* 648 */     MEDIUM,
/* 649 */     WARM;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\Biome.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */