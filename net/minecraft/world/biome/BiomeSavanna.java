/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockDoublePlant;
/*    */ import net.minecraft.entity.passive.EntityDonkey;
/*    */ import net.minecraft.entity.passive.EntityHorse;
/*    */ import net.minecraft.entity.passive.EntityLlama;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*    */ import net.minecraft.world.gen.feature.WorldGenSavannaTree;
/*    */ 
/*    */ public class BiomeSavanna
/*    */   extends Biome {
/* 15 */   private static final WorldGenSavannaTree SAVANNA_TREE = new WorldGenSavannaTree(false);
/*    */ 
/*    */   
/*    */   protected BiomeSavanna(Biome.BiomeProperties properties) {
/* 19 */     super(properties);
/* 20 */     this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityHorse.class, 1, 2, 6));
/* 21 */     this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityDonkey.class, 1, 1, 1));
/*    */     
/* 23 */     if (getBaseHeight() > 1.1F)
/*    */     {
/* 25 */       this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityLlama.class, 8, 4, 4));
/*    */     }
/*    */     
/* 28 */     this.theBiomeDecorator.treesPerChunk = 1;
/* 29 */     this.theBiomeDecorator.flowersPerChunk = 4;
/* 30 */     this.theBiomeDecorator.grassPerChunk = 20;
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 35 */     return (rand.nextInt(5) > 0) ? (WorldGenAbstractTree)SAVANNA_TREE : (WorldGenAbstractTree)TREE_FEATURE;
/*    */   }
/*    */ 
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 40 */     DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);
/*    */     
/* 42 */     for (int i = 0; i < 7; i++) {
/*    */       
/* 44 */       int j = rand.nextInt(16) + 8;
/* 45 */       int k = rand.nextInt(16) + 8;
/* 46 */       int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
/* 47 */       DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
/*    */     } 
/*    */     
/* 50 */     super.decorate(worldIn, rand, pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<? extends Biome> getBiomeClass() {
/* 55 */     return (Class)BiomeSavanna.class;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeSavanna.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */