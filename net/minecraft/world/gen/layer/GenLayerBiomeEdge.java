/*     */ package net.minecraft.world.gen.layer;
/*     */ 
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ 
/*     */ public class GenLayerBiomeEdge
/*     */   extends GenLayer
/*     */ {
/*     */   public GenLayerBiomeEdge(long p_i45475_1_, GenLayer p_i45475_3_) {
/*  10 */     super(p_i45475_1_);
/*  11 */     this.parent = p_i45475_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/*  20 */     int[] aint = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
/*  21 */     int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);
/*     */     
/*  23 */     for (int i = 0; i < areaHeight; i++) {
/*     */       
/*  25 */       for (int j = 0; j < areaWidth; j++) {
/*     */         
/*  27 */         initChunkSeed((j + areaX), (i + areaY));
/*  28 */         int k = aint[j + 1 + (i + 1) * (areaWidth + 2)];
/*     */         
/*  30 */         if (!replaceBiomeEdgeIfNecessary(aint, aint1, j, i, areaWidth, k, Biome.getIdForBiome(Biomes.EXTREME_HILLS), Biome.getIdForBiome(Biomes.EXTREME_HILLS_EDGE)) && !replaceBiomeEdge(aint, aint1, j, i, areaWidth, k, Biome.getIdForBiome(Biomes.MESA_ROCK), Biome.getIdForBiome(Biomes.MESA)) && !replaceBiomeEdge(aint, aint1, j, i, areaWidth, k, Biome.getIdForBiome(Biomes.MESA_CLEAR_ROCK), Biome.getIdForBiome(Biomes.MESA)) && !replaceBiomeEdge(aint, aint1, j, i, areaWidth, k, Biome.getIdForBiome(Biomes.REDWOOD_TAIGA), Biome.getIdForBiome(Biomes.TAIGA)))
/*     */         {
/*  32 */           if (k == Biome.getIdForBiome(Biomes.DESERT)) {
/*     */             
/*  34 */             int l1 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
/*  35 */             int i2 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
/*  36 */             int j2 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
/*  37 */             int k2 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
/*     */             
/*  39 */             if (l1 != Biome.getIdForBiome(Biomes.ICE_PLAINS) && i2 != Biome.getIdForBiome(Biomes.ICE_PLAINS) && j2 != Biome.getIdForBiome(Biomes.ICE_PLAINS) && k2 != Biome.getIdForBiome(Biomes.ICE_PLAINS))
/*     */             {
/*  41 */               aint1[j + i * areaWidth] = k;
/*     */             }
/*     */             else
/*     */             {
/*  45 */               aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.EXTREME_HILLS_WITH_TREES);
/*     */             }
/*     */           
/*  48 */           } else if (k == Biome.getIdForBiome(Biomes.SWAMPLAND)) {
/*     */             
/*  50 */             int l = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
/*  51 */             int i1 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
/*  52 */             int j1 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
/*  53 */             int k1 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
/*     */             
/*  55 */             if (l != Biome.getIdForBiome(Biomes.DESERT) && i1 != Biome.getIdForBiome(Biomes.DESERT) && j1 != Biome.getIdForBiome(Biomes.DESERT) && k1 != Biome.getIdForBiome(Biomes.DESERT) && l != Biome.getIdForBiome(Biomes.COLD_TAIGA) && i1 != Biome.getIdForBiome(Biomes.COLD_TAIGA) && j1 != Biome.getIdForBiome(Biomes.COLD_TAIGA) && k1 != Biome.getIdForBiome(Biomes.COLD_TAIGA) && l != Biome.getIdForBiome(Biomes.ICE_PLAINS) && i1 != Biome.getIdForBiome(Biomes.ICE_PLAINS) && j1 != Biome.getIdForBiome(Biomes.ICE_PLAINS) && k1 != Biome.getIdForBiome(Biomes.ICE_PLAINS)) {
/*     */               
/*  57 */               if (l != Biome.getIdForBiome(Biomes.JUNGLE) && k1 != Biome.getIdForBiome(Biomes.JUNGLE) && i1 != Biome.getIdForBiome(Biomes.JUNGLE) && j1 != Biome.getIdForBiome(Biomes.JUNGLE))
/*     */               {
/*  59 */                 aint1[j + i * areaWidth] = k;
/*     */               }
/*     */               else
/*     */               {
/*  63 */                 aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.JUNGLE_EDGE);
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/*  68 */               aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.PLAINS);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/*  73 */             aint1[j + i * areaWidth] = k;
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  79 */     return aint1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean replaceBiomeEdgeIfNecessary(int[] p_151636_1_, int[] p_151636_2_, int p_151636_3_, int p_151636_4_, int p_151636_5_, int p_151636_6_, int p_151636_7_, int p_151636_8_) {
/*  87 */     if (!biomesEqualOrMesaPlateau(p_151636_6_, p_151636_7_))
/*     */     {
/*  89 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  93 */     int i = p_151636_1_[p_151636_3_ + 1 + (p_151636_4_ + 1 - 1) * (p_151636_5_ + 2)];
/*  94 */     int j = p_151636_1_[p_151636_3_ + 1 + 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2)];
/*  95 */     int k = p_151636_1_[p_151636_3_ + 1 - 1 + (p_151636_4_ + 1) * (p_151636_5_ + 2)];
/*  96 */     int l = p_151636_1_[p_151636_3_ + 1 + (p_151636_4_ + 1 + 1) * (p_151636_5_ + 2)];
/*     */     
/*  98 */     if (canBiomesBeNeighbors(i, p_151636_7_) && canBiomesBeNeighbors(j, p_151636_7_) && canBiomesBeNeighbors(k, p_151636_7_) && canBiomesBeNeighbors(l, p_151636_7_)) {
/*     */       
/* 100 */       p_151636_2_[p_151636_3_ + p_151636_4_ * p_151636_5_] = p_151636_6_;
/*     */     }
/*     */     else {
/*     */       
/* 104 */       p_151636_2_[p_151636_3_ + p_151636_4_ * p_151636_5_] = p_151636_8_;
/*     */     } 
/*     */     
/* 107 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean replaceBiomeEdge(int[] p_151635_1_, int[] p_151635_2_, int p_151635_3_, int p_151635_4_, int p_151635_5_, int p_151635_6_, int p_151635_7_, int p_151635_8_) {
/* 116 */     if (p_151635_6_ != p_151635_7_)
/*     */     {
/* 118 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 122 */     int i = p_151635_1_[p_151635_3_ + 1 + (p_151635_4_ + 1 - 1) * (p_151635_5_ + 2)];
/* 123 */     int j = p_151635_1_[p_151635_3_ + 1 + 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2)];
/* 124 */     int k = p_151635_1_[p_151635_3_ + 1 - 1 + (p_151635_4_ + 1) * (p_151635_5_ + 2)];
/* 125 */     int l = p_151635_1_[p_151635_3_ + 1 + (p_151635_4_ + 1 + 1) * (p_151635_5_ + 2)];
/*     */     
/* 127 */     if (biomesEqualOrMesaPlateau(i, p_151635_7_) && biomesEqualOrMesaPlateau(j, p_151635_7_) && biomesEqualOrMesaPlateau(k, p_151635_7_) && biomesEqualOrMesaPlateau(l, p_151635_7_)) {
/*     */       
/* 129 */       p_151635_2_[p_151635_3_ + p_151635_4_ * p_151635_5_] = p_151635_6_;
/*     */     }
/*     */     else {
/*     */       
/* 133 */       p_151635_2_[p_151635_3_ + p_151635_4_ * p_151635_5_] = p_151635_8_;
/*     */     } 
/*     */     
/* 136 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean canBiomesBeNeighbors(int p_151634_1_, int p_151634_2_) {
/* 146 */     if (biomesEqualOrMesaPlateau(p_151634_1_, p_151634_2_))
/*     */     {
/* 148 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 152 */     Biome biome = Biome.getBiome(p_151634_1_);
/* 153 */     Biome biome1 = Biome.getBiome(p_151634_2_);
/*     */     
/* 155 */     if (biome != null && biome1 != null) {
/*     */       
/* 157 */       Biome.TempCategory biome$tempcategory = biome.getTempCategory();
/* 158 */       Biome.TempCategory biome$tempcategory1 = biome1.getTempCategory();
/* 159 */       return !(biome$tempcategory != biome$tempcategory1 && biome$tempcategory != Biome.TempCategory.MEDIUM && biome$tempcategory1 != Biome.TempCategory.MEDIUM);
/*     */     } 
/*     */ 
/*     */     
/* 163 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\layer\GenLayerBiomeEdge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */