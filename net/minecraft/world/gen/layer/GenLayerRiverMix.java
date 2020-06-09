/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ import net.minecraft.init.Biomes;
/*    */ import net.minecraft.world.biome.Biome;
/*    */ 
/*    */ public class GenLayerRiverMix
/*    */   extends GenLayer
/*    */ {
/*    */   private final GenLayer biomePatternGeneratorChain;
/*    */   private final GenLayer riverPatternGeneratorChain;
/*    */   
/*    */   public GenLayerRiverMix(long p_i2129_1_, GenLayer p_i2129_3_, GenLayer p_i2129_4_) {
/* 13 */     super(p_i2129_1_);
/* 14 */     this.biomePatternGeneratorChain = p_i2129_3_;
/* 15 */     this.riverPatternGeneratorChain = p_i2129_4_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void initWorldGenSeed(long seed) {
/* 24 */     this.biomePatternGeneratorChain.initWorldGenSeed(seed);
/* 25 */     this.riverPatternGeneratorChain.initWorldGenSeed(seed);
/* 26 */     super.initWorldGenSeed(seed);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/* 35 */     int[] aint = this.biomePatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
/* 36 */     int[] aint1 = this.riverPatternGeneratorChain.getInts(areaX, areaY, areaWidth, areaHeight);
/* 37 */     int[] aint2 = IntCache.getIntCache(areaWidth * areaHeight);
/*    */     
/* 39 */     for (int i = 0; i < areaWidth * areaHeight; i++) {
/*    */       
/* 41 */       if (aint[i] != Biome.getIdForBiome(Biomes.OCEAN) && aint[i] != Biome.getIdForBiome(Biomes.DEEP_OCEAN)) {
/*    */         
/* 43 */         if (aint1[i] == Biome.getIdForBiome(Biomes.RIVER)) {
/*    */           
/* 45 */           if (aint[i] == Biome.getIdForBiome(Biomes.ICE_PLAINS))
/*    */           {
/* 47 */             aint2[i] = Biome.getIdForBiome(Biomes.FROZEN_RIVER);
/*    */           }
/* 49 */           else if (aint[i] != Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND) && aint[i] != Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND_SHORE))
/*    */           {
/* 51 */             aint2[i] = aint1[i] & 0xFF;
/*    */           }
/*    */           else
/*    */           {
/* 55 */             aint2[i] = Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND_SHORE);
/*    */           }
/*    */         
/*    */         } else {
/*    */           
/* 60 */           aint2[i] = aint[i];
/*    */         }
/*    */       
/*    */       } else {
/*    */         
/* 65 */         aint2[i] = aint[i];
/*    */       } 
/*    */     } 
/*    */     
/* 69 */     return aint2;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\layer\GenLayerRiverMix.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */