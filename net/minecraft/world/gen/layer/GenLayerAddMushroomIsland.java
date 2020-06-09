/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ import net.minecraft.init.Biomes;
/*    */ import net.minecraft.world.biome.Biome;
/*    */ 
/*    */ public class GenLayerAddMushroomIsland
/*    */   extends GenLayer
/*    */ {
/*    */   public GenLayerAddMushroomIsland(long p_i2120_1_, GenLayer p_i2120_3_) {
/* 10 */     super(p_i2120_1_);
/* 11 */     this.parent = p_i2120_3_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/* 20 */     int i = areaX - 1;
/* 21 */     int j = areaY - 1;
/* 22 */     int k = areaWidth + 2;
/* 23 */     int l = areaHeight + 2;
/* 24 */     int[] aint = this.parent.getInts(i, j, k, l);
/* 25 */     int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);
/*    */     
/* 27 */     for (int i1 = 0; i1 < areaHeight; i1++) {
/*    */       
/* 29 */       for (int j1 = 0; j1 < areaWidth; j1++) {
/*    */         
/* 31 */         int k1 = aint[j1 + 0 + (i1 + 0) * k];
/* 32 */         int l1 = aint[j1 + 2 + (i1 + 0) * k];
/* 33 */         int i2 = aint[j1 + 0 + (i1 + 2) * k];
/* 34 */         int j2 = aint[j1 + 2 + (i1 + 2) * k];
/* 35 */         int k2 = aint[j1 + 1 + (i1 + 1) * k];
/* 36 */         initChunkSeed((j1 + areaX), (i1 + areaY));
/*    */         
/* 38 */         if (k2 == 0 && k1 == 0 && l1 == 0 && i2 == 0 && j2 == 0 && nextInt(100) == 0) {
/*    */           
/* 40 */           aint1[j1 + i1 * areaWidth] = Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND);
/*    */         }
/*    */         else {
/*    */           
/* 44 */           aint1[j1 + i1 * areaWidth] = k2;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 49 */     return aint1;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\layer\GenLayerAddMushroomIsland.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */