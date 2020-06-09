/*    */ package net.minecraft.world.gen.layer;
/*    */ 
/*    */ import net.minecraft.init.Biomes;
/*    */ import net.minecraft.world.biome.Biome;
/*    */ 
/*    */ public class GenLayerRiver
/*    */   extends GenLayer
/*    */ {
/*    */   public GenLayerRiver(long p_i2128_1_, GenLayer p_i2128_3_) {
/* 10 */     super(p_i2128_1_);
/* 11 */     this.parent = p_i2128_3_;
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
/* 31 */         int k1 = riverFilter(aint[j1 + 0 + (i1 + 1) * k]);
/* 32 */         int l1 = riverFilter(aint[j1 + 2 + (i1 + 1) * k]);
/* 33 */         int i2 = riverFilter(aint[j1 + 1 + (i1 + 0) * k]);
/* 34 */         int j2 = riverFilter(aint[j1 + 1 + (i1 + 2) * k]);
/* 35 */         int k2 = riverFilter(aint[j1 + 1 + (i1 + 1) * k]);
/*    */         
/* 37 */         if (k2 == k1 && k2 == i2 && k2 == l1 && k2 == j2) {
/*    */           
/* 39 */           aint1[j1 + i1 * areaWidth] = -1;
/*    */         }
/*    */         else {
/*    */           
/* 43 */           aint1[j1 + i1 * areaWidth] = Biome.getIdForBiome(Biomes.RIVER);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 48 */     return aint1;
/*    */   }
/*    */ 
/*    */   
/*    */   private int riverFilter(int p_151630_1_) {
/* 53 */     return (p_151630_1_ >= 2) ? (2 + (p_151630_1_ & 0x1)) : p_151630_1_;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\layer\GenLayerRiver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */