/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockDirt;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.chunk.ChunkPrimer;
/*    */ 
/*    */ public class BiomeSavannaMutated
/*    */   extends BiomeSavanna {
/*    */   public BiomeSavannaMutated(Biome.BiomeProperties properties) {
/* 14 */     super(properties);
/* 15 */     this.theBiomeDecorator.treesPerChunk = 2;
/* 16 */     this.theBiomeDecorator.flowersPerChunk = 2;
/* 17 */     this.theBiomeDecorator.grassPerChunk = 5;
/*    */   }
/*    */ 
/*    */   
/*    */   public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
/* 22 */     this.topBlock = Blocks.GRASS.getDefaultState();
/* 23 */     this.fillerBlock = Blocks.DIRT.getDefaultState();
/*    */     
/* 25 */     if (noiseVal > 1.75D) {
/*    */       
/* 27 */       this.topBlock = Blocks.STONE.getDefaultState();
/* 28 */       this.fillerBlock = Blocks.STONE.getDefaultState();
/*    */     }
/* 30 */     else if (noiseVal > -0.5D) {
/*    */       
/* 32 */       this.topBlock = Blocks.DIRT.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, (Comparable)BlockDirt.DirtType.COARSE_DIRT);
/*    */     } 
/*    */     
/* 35 */     generateBiomeTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
/*    */   }
/*    */ 
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 40 */     this.theBiomeDecorator.decorate(worldIn, rand, this, pos);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeSavannaMutated.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */