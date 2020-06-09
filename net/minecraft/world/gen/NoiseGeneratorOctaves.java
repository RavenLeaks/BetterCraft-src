/*    */ package net.minecraft.world.gen;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NoiseGeneratorOctaves
/*    */   extends NoiseGenerator
/*    */ {
/*    */   private final NoiseGeneratorImproved[] generatorCollection;
/*    */   private final int octaves;
/*    */   
/*    */   public NoiseGeneratorOctaves(Random seed, int octavesIn) {
/* 16 */     this.octaves = octavesIn;
/* 17 */     this.generatorCollection = new NoiseGeneratorImproved[octavesIn];
/*    */     
/* 19 */     for (int i = 0; i < octavesIn; i++)
/*    */     {
/* 21 */       this.generatorCollection[i] = new NoiseGeneratorImproved(seed);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] generateNoiseOctaves(double[] noiseArray, int xOffset, int yOffset, int zOffset, int xSize, int ySize, int zSize, double xScale, double yScale, double zScale) {
/* 31 */     if (noiseArray == null) {
/*    */       
/* 33 */       noiseArray = new double[xSize * ySize * zSize];
/*    */     }
/*    */     else {
/*    */       
/* 37 */       for (int i = 0; i < noiseArray.length; i++)
/*    */       {
/* 39 */         noiseArray[i] = 0.0D;
/*    */       }
/*    */     } 
/*    */     
/* 43 */     double d3 = 1.0D;
/*    */     
/* 45 */     for (int j = 0; j < this.octaves; j++) {
/*    */       
/* 47 */       double d0 = xOffset * d3 * xScale;
/* 48 */       double d1 = yOffset * d3 * yScale;
/* 49 */       double d2 = zOffset * d3 * zScale;
/* 50 */       long k = MathHelper.lFloor(d0);
/* 51 */       long l = MathHelper.lFloor(d2);
/* 52 */       d0 -= k;
/* 53 */       d2 -= l;
/* 54 */       k %= 16777216L;
/* 55 */       l %= 16777216L;
/* 56 */       d0 += k;
/* 57 */       d2 += l;
/* 58 */       this.generatorCollection[j].populateNoiseArray(noiseArray, d0, d1, d2, xSize, ySize, zSize, xScale * d3, yScale * d3, zScale * d3, d3);
/* 59 */       d3 /= 2.0D;
/*    */     } 
/*    */     
/* 62 */     return noiseArray;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double[] generateNoiseOctaves(double[] noiseArray, int xOffset, int zOffset, int xSize, int zSize, double xScale, double zScale, double p_76305_10_) {
/* 70 */     return generateNoiseOctaves(noiseArray, xOffset, 10, zOffset, xSize, 1, zSize, xScale, 1.0D, zScale);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\NoiseGeneratorOctaves.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */