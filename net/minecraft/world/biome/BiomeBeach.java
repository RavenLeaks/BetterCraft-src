/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class BiomeBeach
/*    */   extends Biome
/*    */ {
/*    */   public BiomeBeach(Biome.BiomeProperties properties) {
/*  9 */     super(properties);
/* 10 */     this.spawnableCreatureList.clear();
/* 11 */     this.topBlock = Blocks.SAND.getDefaultState();
/* 12 */     this.fillerBlock = Blocks.SAND.getDefaultState();
/* 13 */     this.theBiomeDecorator.treesPerChunk = -999;
/* 14 */     this.theBiomeDecorator.deadBushPerChunk = 0;
/* 15 */     this.theBiomeDecorator.reedsPerChunk = 0;
/* 16 */     this.theBiomeDecorator.cactiPerChunk = 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeBeach.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */