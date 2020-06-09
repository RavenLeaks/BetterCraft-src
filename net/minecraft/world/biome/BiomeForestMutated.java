/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*    */ 
/*    */ public class BiomeForestMutated
/*    */   extends BiomeForest
/*    */ {
/*    */   public BiomeForestMutated(Biome.BiomeProperties properties) {
/* 10 */     super(BiomeForest.Type.BIRCH, properties);
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 15 */     return rand.nextBoolean() ? (WorldGenAbstractTree)BiomeForest.SUPER_BIRCH_TREE : (WorldGenAbstractTree)BiomeForest.BIRCH_TREE;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeForestMutated.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */