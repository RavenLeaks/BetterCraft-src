/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockSilverfish;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.entity.passive.EntityLlama;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.ChunkPrimer;
/*    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*    */ import net.minecraft.world.gen.feature.WorldGenMinable;
/*    */ import net.minecraft.world.gen.feature.WorldGenTaiga2;
/*    */ import net.minecraft.world.gen.feature.WorldGenerator;
/*    */ 
/*    */ public class BiomeHills extends Biome {
/* 17 */   private final WorldGenerator theWorldGenerator = (WorldGenerator)new WorldGenMinable(Blocks.MONSTER_EGG.getDefaultState().withProperty((IProperty)BlockSilverfish.VARIANT, (Comparable)BlockSilverfish.EnumType.STONE), 9);
/* 18 */   private final WorldGenTaiga2 spruceGenerator = new WorldGenTaiga2(false);
/*    */   
/*    */   private final Type type;
/*    */   
/*    */   protected BiomeHills(Type p_i46710_1_, Biome.BiomeProperties properties) {
/* 23 */     super(properties);
/*    */     
/* 25 */     if (p_i46710_1_ == Type.EXTRA_TREES)
/*    */     {
/* 27 */       this.theBiomeDecorator.treesPerChunk = 3;
/*    */     }
/*    */     
/* 30 */     this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityLlama.class, 5, 4, 6));
/* 31 */     this.type = p_i46710_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 36 */     return (rand.nextInt(3) > 0) ? (WorldGenAbstractTree)this.spruceGenerator : super.genBigTreeChance(rand);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 41 */     super.decorate(worldIn, rand, pos);
/* 42 */     int i = 3 + rand.nextInt(6);
/*    */     
/* 44 */     for (int j = 0; j < i; j++) {
/*    */       
/* 46 */       int k = rand.nextInt(16);
/* 47 */       int l = rand.nextInt(28) + 4;
/* 48 */       int i1 = rand.nextInt(16);
/* 49 */       BlockPos blockpos = pos.add(k, l, i1);
/*    */       
/* 51 */       if (worldIn.getBlockState(blockpos).getBlock() == Blocks.STONE)
/*    */       {
/* 53 */         worldIn.setBlockState(blockpos, Blocks.EMERALD_ORE.getDefaultState(), 2);
/*    */       }
/*    */     } 
/*    */     
/* 57 */     for (int j1 = 0; j1 < 7; j1++) {
/*    */       
/* 59 */       int k1 = rand.nextInt(16);
/* 60 */       int l1 = rand.nextInt(64);
/* 61 */       int i2 = rand.nextInt(16);
/* 62 */       this.theWorldGenerator.generate(worldIn, rand, pos.add(k1, l1, i2));
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 68 */     this.topBlock = Blocks.GRASS.getDefaultState();
/* 69 */     this.fillerBlock = Blocks.DIRT.getDefaultState();
/*    */     
/* 71 */     if ((noiseVal < -1.0D || noiseVal > 2.0D) && this.type == Type.MUTATED) {
/*    */       
/* 73 */       this.topBlock = Blocks.GRAVEL.getDefaultState();
/* 74 */       this.fillerBlock = Blocks.GRAVEL.getDefaultState();
/*    */     }
/* 76 */     else if (noiseVal > 1.0D && this.type != Type.EXTRA_TREES) {
/*    */       
/* 78 */       this.topBlock = Blocks.STONE.getDefaultState();
/* 79 */       this.fillerBlock = Blocks.STONE.getDefaultState();
/*    */     } 
/*    */     
/* 82 */     generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*    */   }
/*    */   
/*    */   public enum Type
/*    */   {
/* 87 */     NORMAL,
/* 88 */     EXTRA_TREES,
/* 89 */     MUTATED;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeHills.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */