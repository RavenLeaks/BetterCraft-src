/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockDirt;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.block.BlockTallGrass;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenBlockBlob;
/*     */ import net.minecraft.world.gen.feature.WorldGenMegaPineTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenTaiga1;
/*     */ import net.minecraft.world.gen.feature.WorldGenTaiga2;
/*     */ import net.minecraft.world.gen.feature.WorldGenTallGrass;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ 
/*     */ public class BiomeTaiga extends Biome {
/*  23 */   private static final WorldGenTaiga1 PINE_GENERATOR = new WorldGenTaiga1();
/*  24 */   private static final WorldGenTaiga2 SPRUCE_GENERATOR = new WorldGenTaiga2(false);
/*  25 */   private static final WorldGenMegaPineTree MEGA_PINE_GENERATOR = new WorldGenMegaPineTree(false, false);
/*  26 */   private static final WorldGenMegaPineTree MEGA_SPRUCE_GENERATOR = new WorldGenMegaPineTree(false, true);
/*  27 */   private static final WorldGenBlockBlob FOREST_ROCK_GENERATOR = new WorldGenBlockBlob(Blocks.MOSSY_COBBLESTONE, 0);
/*     */   
/*     */   private final Type type;
/*     */   
/*     */   public BiomeTaiga(Type typeIn, Biome.BiomeProperties properties) {
/*  32 */     super(properties);
/*  33 */     this.type = typeIn;
/*  34 */     this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityWolf.class, 8, 4, 4));
/*  35 */     this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityRabbit.class, 4, 2, 3));
/*  36 */     this.theBiomeDecorator.treesPerChunk = 10;
/*     */     
/*  38 */     if (typeIn != Type.MEGA && typeIn != Type.MEGA_SPRUCE) {
/*     */       
/*  40 */       this.theBiomeDecorator.grassPerChunk = 1;
/*  41 */       this.theBiomeDecorator.mushroomsPerChunk = 1;
/*     */     }
/*     */     else {
/*     */       
/*  45 */       this.theBiomeDecorator.grassPerChunk = 7;
/*  46 */       this.theBiomeDecorator.deadBushPerChunk = 1;
/*  47 */       this.theBiomeDecorator.mushroomsPerChunk = 3;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/*  53 */     if ((this.type == Type.MEGA || this.type == Type.MEGA_SPRUCE) && rand.nextInt(3) == 0)
/*     */     {
/*  55 */       return (this.type != Type.MEGA_SPRUCE && rand.nextInt(13) != 0) ? (WorldGenAbstractTree)MEGA_PINE_GENERATOR : (WorldGenAbstractTree)MEGA_SPRUCE_GENERATOR;
/*     */     }
/*     */ 
/*     */     
/*  59 */     return (rand.nextInt(3) == 0) ? (WorldGenAbstractTree)PINE_GENERATOR : (WorldGenAbstractTree)SPRUCE_GENERATOR;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WorldGenerator getRandomWorldGenForGrass(Random rand) {
/*  68 */     return (rand.nextInt(5) > 0) ? (WorldGenerator)new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : (WorldGenerator)new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/*  73 */     if (this.type == Type.MEGA || this.type == Type.MEGA_SPRUCE) {
/*     */       
/*  75 */       int i = rand.nextInt(3);
/*     */       
/*  77 */       for (int j = 0; j < i; j++) {
/*     */         
/*  79 */         int k = rand.nextInt(16) + 8;
/*  80 */         int l = rand.nextInt(16) + 8;
/*  81 */         BlockPos blockpos = worldIn.getHeight(pos.add(k, 0, l));
/*  82 */         FOREST_ROCK_GENERATOR.generate(worldIn, rand, blockpos);
/*     */       } 
/*     */     } 
/*     */     
/*  86 */     DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.FERN);
/*     */     
/*  88 */     for (int i1 = 0; i1 < 7; i1++) {
/*     */       
/*  90 */       int j1 = rand.nextInt(16) + 8;
/*  91 */       int k1 = rand.nextInt(16) + 8;
/*  92 */       int l1 = rand.nextInt(worldIn.getHeight(pos.add(j1, 0, k1)).getY() + 32);
/*  93 */       DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j1, l1, k1));
/*     */     } 
/*     */     
/*  96 */     super.decorate(worldIn, rand, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 101 */     if (this.type == Type.MEGA || this.type == Type.MEGA_SPRUCE) {
/*     */       
/* 103 */       this.topBlock = Blocks.GRASS.getDefaultState();
/* 104 */       this.fillerBlock = Blocks.DIRT.getDefaultState();
/*     */       
/* 106 */       if (noiseVal > 1.75D) {
/*     */         
/* 108 */         this.topBlock = Blocks.DIRT.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.COARSE_DIRT);
/*     */       }
/* 110 */       else if (noiseVal > -0.95D) {
/*     */         
/* 112 */         this.topBlock = Blocks.DIRT.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.PODZOL);
/*     */       } 
/*     */     } 
/*     */     
/* 116 */     generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/* 121 */     NORMAL,
/* 122 */     MEGA,
/* 123 */     MEGA_SPRUCE;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeTaiga.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */