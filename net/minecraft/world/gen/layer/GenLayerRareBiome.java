/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ import net.minecraft.init.Biomes;
/*    */ import net.minecraft.world.biome.Biome;
/*    */ 
/*    */ public class GenLayerRareBiome
/*    */   extends GenLayer
/*    */ {
/*    */   public GenLayerRareBiome(long p_i45478_1_, GenLayer p_i45478_3_) {
/* 10 */     super(p_i45478_1_);
/* 11 */     this.parent = p_i45478_3_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/* 20 */     int[] aint = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
/* 21 */     int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);
/*    */     
/* 23 */     for (int i = 0; i < areaHeight; i++) {
/*    */       
/* 25 */       for (int j = 0; j < areaWidth; j++) {
/*    */         
/* 27 */         initChunkSeed((j + areaX), (i + areaY));
/* 28 */         int k = aint[j + 1 + (i + 1) * (areaWidth + 2)];
/*    */         
/* 30 */         if (nextInt(57) == 0) {
/*    */           
/* 32 */           if (k == Biome.getIdForBiome(Biomes.PLAINS))
/*    */           {
/* 34 */             aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.MUTATED_PLAINS);
/*    */           }
/*    */           else
/*    */           {
/* 38 */             aint1[j + i * areaWidth] = k;
/*    */           }
/*    */         
/*    */         } else {
/*    */           
/* 43 */           aint1[j + i * areaWidth] = k;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     return aint1;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\layer\GenLayerRareBiome.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */