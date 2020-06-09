/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockLeaves;
/*    */ import net.minecraft.block.BlockOldLeaf;
/*    */ import net.minecraft.block.BlockOldLog;
/*    */ import net.minecraft.block.BlockPlanks;
/*    */ import net.minecraft.block.BlockTallGrass;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.passive.EntityChicken;
/*    */ import net.minecraft.entity.passive.EntityOcelot;
/*    */ import net.minecraft.entity.passive.EntityParrot;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*    */ import net.minecraft.world.gen.feature.WorldGenMegaJungle;
/*    */ import net.minecraft.world.gen.feature.WorldGenMelon;
/*    */ import net.minecraft.world.gen.feature.WorldGenShrub;
/*    */ import net.minecraft.world.gen.feature.WorldGenTallGrass;
/*    */ import net.minecraft.world.gen.feature.WorldGenTrees;
/*    */ import net.minecraft.world.gen.feature.WorldGenVines;
/*    */ import net.minecraft.world.gen.feature.WorldGenerator;
/*    */ 
/*    */ public class BiomeJungle extends Biome {
/*    */   private final boolean isEdge;
/* 28 */   private static final IBlockState JUNGLE_LOG = Blocks.LOG.getDefaultState().withProperty((IProperty)BlockOldLog.VARIANT, (Comparable)BlockPlanks.EnumType.JUNGLE);
/* 29 */   private static final IBlockState JUNGLE_LEAF = Blocks.LEAVES.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.JUNGLE).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*    */ 
/*    */   
/* 32 */   private static final IBlockState OAK_LEAF = Blocks.LEAVES.getDefaultState().withProperty((IProperty)BlockOldLeaf.VARIANT, (Comparable)BlockPlanks.EnumType.OAK).withProperty((IProperty)BlockLeaves.CHECK_DECAY, Boolean.valueOf(false));
/*    */ 
/*    */   
/*    */   public BiomeJungle(boolean isEdgeIn, Biome.BiomeProperties properties) {
/* 36 */     super(properties);
/* 37 */     this.isEdge = isEdgeIn;
/*    */     
/* 39 */     if (isEdgeIn) {
/*    */       
/* 41 */       this.theBiomeDecorator.treesPerChunk = 2;
/*    */     }
/*    */     else {
/*    */       
/* 45 */       this.theBiomeDecorator.treesPerChunk = 50;
/*    */     } 
/*    */     
/* 48 */     this.theBiomeDecorator.grassPerChunk = 25;
/* 49 */     this.theBiomeDecorator.flowersPerChunk = 4;
/*    */     
/* 51 */     if (!isEdgeIn)
/*    */     {
/* 53 */       this.spawnableMonsterList.add(new Biome.SpawnListEntry((Class)EntityOcelot.class, 2, 1, 1));
/*    */     }
/*    */     
/* 56 */     this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityParrot.class, 40, 1, 2));
/* 57 */     this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityChicken.class, 10, 4, 4));
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 62 */     if (rand.nextInt(10) == 0)
/*    */     {
/* 64 */       return (WorldGenAbstractTree)BIG_TREE_FEATURE;
/*    */     }
/* 66 */     if (rand.nextInt(2) == 0)
/*    */     {
/* 68 */       return (WorldGenAbstractTree)new WorldGenShrub(JUNGLE_LOG, OAK_LEAF);
/*    */     }
/*    */ 
/*    */     
/* 72 */     return (!this.isEdge && rand.nextInt(3) == 0) ? (WorldGenAbstractTree)new WorldGenMegaJungle(false, 10, 20, JUNGLE_LOG, JUNGLE_LEAF) : (WorldGenAbstractTree)new WorldGenTrees(false, 4 + rand.nextInt(7), JUNGLE_LOG, JUNGLE_LEAF, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WorldGenerator getRandomWorldGenForGrass(Random rand) {
/* 81 */     return (rand.nextInt(4) == 0) ? (WorldGenerator)new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : (WorldGenerator)new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 86 */     super.decorate(worldIn, rand, pos);
/* 87 */     int i = rand.nextInt(16) + 8;
/* 88 */     int j = rand.nextInt(16) + 8;
/* 89 */     int k = rand.nextInt(worldIn.getHeight(pos.add(i, 0, j)).getY() * 2);
/* 90 */     (new WorldGenMelon()).generate(worldIn, rand, pos.add(i, k, j));
/* 91 */     WorldGenVines worldgenvines = new WorldGenVines();
/*    */     
/* 93 */     for (int j1 = 0; j1 < 50; j1++) {
/*    */       
/* 95 */       k = rand.nextInt(16) + 8;
/* 96 */       int l = 128;
/* 97 */       int i1 = rand.nextInt(16) + 8;
/* 98 */       worldgenvines.generate(worldIn, rand, pos.add(k, 128, i1));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeJungle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */