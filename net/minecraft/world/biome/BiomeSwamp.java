/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockFlower;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.entity.monster.EntitySlime;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.ChunkPrimer;
/*    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*    */ import net.minecraft.world.gen.feature.WorldGenFossils;
/*    */ 
/*    */ public class BiomeSwamp
/*    */   extends Biome {
/* 17 */   protected static final IBlockState WATER_LILY = Blocks.WATERLILY.getDefaultState();
/*    */ 
/*    */   
/*    */   protected BiomeSwamp(Biome.BiomeProperties properties) {
/* 21 */     super(properties);
/* 22 */     this.theBiomeDecorator.treesPerChunk = 2;
/* 23 */     this.theBiomeDecorator.flowersPerChunk = 1;
/* 24 */     this.theBiomeDecorator.deadBushPerChunk = 1;
/* 25 */     this.theBiomeDecorator.mushroomsPerChunk = 8;
/* 26 */     this.theBiomeDecorator.reedsPerChunk = 10;
/* 27 */     this.theBiomeDecorator.clayPerChunk = 1;
/* 28 */     this.theBiomeDecorator.waterlilyPerChunk = 4;
/* 29 */     this.theBiomeDecorator.sandPerChunk2 = 0;
/* 30 */     this.theBiomeDecorator.sandPerChunk = 0;
/* 31 */     this.theBiomeDecorator.grassPerChunk = 5;
/* 32 */     this.spawnableMonsterList.add(new Biome.SpawnListEntry((Class)EntitySlime.class, 1, 1, 1));
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 37 */     return (WorldGenAbstractTree)SWAMP_FEATURE;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getGrassColorAtPos(BlockPos pos) {
/* 42 */     double d0 = GRASS_COLOR_NOISE.getValue(pos.getX() * 0.0225D, pos.getZ() * 0.0225D);
/* 43 */     return (d0 < -0.1D) ? 5011004 : 6975545;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFoliageColorAtPos(BlockPos pos) {
/* 48 */     return 6975545;
/*    */   }
/*    */ 
/*    */   
/*    */   public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
/* 53 */     return BlockFlower.EnumFlowerType.BLUE_ORCHID;
/*    */   }
/*    */ 
/*    */   
/*    */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 58 */     double d0 = GRASS_COLOR_NOISE.getValue(x * 0.25D, z * 0.25D);
/*    */     
/* 60 */     if (d0 > 0.0D) {
/*    */       
/* 62 */       int i = x & 0xF;
/* 63 */       int j = z & 0xF;
/*    */       
/* 65 */       for (int k = 255; k >= 0; k--) {
/*    */         
/* 67 */         if (chunkPrimerIn.getBlockState(j, k, i).getMaterial() != Material.AIR) {
/*    */           
/* 69 */           if (k == 62 && chunkPrimerIn.getBlockState(j, k, i).getBlock() != Blocks.WATER) {
/*    */             
/* 71 */             chunkPrimerIn.setBlockState(j, k, i, WATER);
/*    */             
/* 73 */             if (d0 < 0.12D)
/*    */             {
/* 75 */               chunkPrimerIn.setBlockState(j, k + 1, i, WATER_LILY);
/*    */             }
/*    */           } 
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 84 */     generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 89 */     super.decorate(worldIn, rand, pos);
/*    */     
/* 91 */     if (rand.nextInt(64) == 0)
/*    */     {
/* 93 */       (new WorldGenFossils()).generate(worldIn, rand, pos);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeSwamp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */