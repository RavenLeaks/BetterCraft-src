/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ import net.minecraft.init.Biomes;
/*    */ import net.minecraft.world.biome.Biome;
/*    */ 
/*    */ public class GenLayerDeepOcean
/*    */   extends GenLayer
/*    */ {
/*    */   public GenLayerDeepOcean(long p_i45472_1_, GenLayer p_i45472_3_) {
/* 10 */     super(p_i45472_1_);
/* 11 */     this.parent = p_i45472_3_;
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
/* 31 */         int k1 = aint[j1 + 1 + (i1 + 1 - 1) * (areaWidth + 2)];
/* 32 */         int l1 = aint[j1 + 1 + 1 + (i1 + 1) * (areaWidth + 2)];
/* 33 */         int i2 = aint[j1 + 1 - 1 + (i1 + 1) * (areaWidth + 2)];
/* 34 */         int j2 = aint[j1 + 1 + (i1 + 1 + 1) * (areaWidth + 2)];
/* 35 */         int k2 = aint[j1 + 1 + (i1 + 1) * k];
/* 36 */         int l2 = 0;
/*    */         
/* 38 */         if (k1 == 0)
/*    */         {
/* 40 */           l2++;
/*    */         }
/*    */         
/* 43 */         if (l1 == 0)
/*    */         {
/* 45 */           l2++;
/*    */         }
/*    */         
/* 48 */         if (i2 == 0)
/*    */         {
/* 50 */           l2++;
/*    */         }
/*    */         
/* 53 */         if (j2 == 0)
/*    */         {
/* 55 */           l2++;
/*    */         }
/*    */         
/* 58 */         if (k2 == 0 && l2 > 3) {
/*    */           
/* 60 */           aint1[j1 + i1 * areaWidth] = Biome.getIdForBiome(Biomes.DEEP_OCEAN);
/*    */         }
/*    */         else {
/*    */           
/* 64 */           aint1[j1 + i1 * areaWidth] = k2;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 69 */     return aint1;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\layer\GenLayerDeepOcean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */