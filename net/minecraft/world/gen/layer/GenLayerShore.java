/*     */ package net.minecraft.world.gen.layer;
/*     */ 
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.biome.BiomeJungle;
/*     */ 
/*     */ 
/*     */ public class GenLayerShore
/*     */   extends GenLayer
/*     */ {
/*     */   public GenLayerShore(long p_i2130_1_, GenLayer p_i2130_3_) {
/*  12 */     super(p_i2130_1_);
/*  13 */     this.parent = p_i2130_3_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/*  22 */     int[] aint = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
/*  23 */     int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);
/*     */     
/*  25 */     for (int i = 0; i < areaHeight; i++) {
/*     */       
/*  27 */       for (int j = 0; j < areaWidth; j++) {
/*     */         
/*  29 */         initChunkSeed((j + areaX), (i + areaY));
/*  30 */         int k = aint[j + 1 + (i + 1) * (areaWidth + 2)];
/*  31 */         Biome biome = Biome.getBiome(k);
/*     */         
/*  33 */         if (k == Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND)) {
/*     */           
/*  35 */           int j2 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
/*  36 */           int i3 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
/*  37 */           int l3 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
/*  38 */           int k4 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
/*     */           
/*  40 */           if (j2 != Biome.getIdForBiome(Biomes.OCEAN) && i3 != Biome.getIdForBiome(Biomes.OCEAN) && l3 != Biome.getIdForBiome(Biomes.OCEAN) && k4 != Biome.getIdForBiome(Biomes.OCEAN))
/*     */           {
/*  42 */             aint1[j + i * areaWidth] = k;
/*     */           }
/*     */           else
/*     */           {
/*  46 */             aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND_SHORE);
/*     */           }
/*     */         
/*  49 */         } else if (biome != null && biome.getBiomeClass() == BiomeJungle.class) {
/*     */           
/*  51 */           int i2 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
/*  52 */           int l2 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
/*  53 */           int k3 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
/*  54 */           int j4 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
/*     */           
/*  56 */           if (isJungleCompatible(i2) && isJungleCompatible(l2) && isJungleCompatible(k3) && isJungleCompatible(j4)) {
/*     */             
/*  58 */             if (!isBiomeOceanic(i2) && !isBiomeOceanic(l2) && !isBiomeOceanic(k3) && !isBiomeOceanic(j4))
/*     */             {
/*  60 */               aint1[j + i * areaWidth] = k;
/*     */             }
/*     */             else
/*     */             {
/*  64 */               aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.BEACH);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/*  69 */             aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.JUNGLE_EDGE);
/*     */           }
/*     */         
/*  72 */         } else if (k != Biome.getIdForBiome(Biomes.EXTREME_HILLS) && k != Biome.getIdForBiome(Biomes.EXTREME_HILLS_WITH_TREES) && k != Biome.getIdForBiome(Biomes.EXTREME_HILLS_EDGE)) {
/*     */           
/*  74 */           if (biome != null && biome.isSnowyBiome()) {
/*     */             
/*  76 */             replaceIfNeighborOcean(aint, aint1, j, i, areaWidth, k, Biome.getIdForBiome(Biomes.COLD_BEACH));
/*     */           }
/*  78 */           else if (k != Biome.getIdForBiome(Biomes.MESA) && k != Biome.getIdForBiome(Biomes.MESA_ROCK)) {
/*     */             
/*  80 */             if (k != Biome.getIdForBiome(Biomes.OCEAN) && k != Biome.getIdForBiome(Biomes.DEEP_OCEAN) && k != Biome.getIdForBiome(Biomes.RIVER) && k != Biome.getIdForBiome(Biomes.SWAMPLAND)) {
/*     */               
/*  82 */               int l1 = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
/*  83 */               int k2 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
/*  84 */               int j3 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
/*  85 */               int i4 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
/*     */               
/*  87 */               if (!isBiomeOceanic(l1) && !isBiomeOceanic(k2) && !isBiomeOceanic(j3) && !isBiomeOceanic(i4))
/*     */               {
/*  89 */                 aint1[j + i * areaWidth] = k;
/*     */               }
/*     */               else
/*     */               {
/*  93 */                 aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.BEACH);
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/*  98 */               aint1[j + i * areaWidth] = k;
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 103 */             int l = aint[j + 1 + (i + 1 - 1) * (areaWidth + 2)];
/* 104 */             int i1 = aint[j + 1 + 1 + (i + 1) * (areaWidth + 2)];
/* 105 */             int j1 = aint[j + 1 - 1 + (i + 1) * (areaWidth + 2)];
/* 106 */             int k1 = aint[j + 1 + (i + 1 + 1) * (areaWidth + 2)];
/*     */             
/* 108 */             if (!isBiomeOceanic(l) && !isBiomeOceanic(i1) && !isBiomeOceanic(j1) && !isBiomeOceanic(k1)) {
/*     */               
/* 110 */               if (isMesa(l) && isMesa(i1) && isMesa(j1) && isMesa(k1))
/*     */               {
/* 112 */                 aint1[j + i * areaWidth] = k;
/*     */               }
/*     */               else
/*     */               {
/* 116 */                 aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.DESERT);
/*     */               }
/*     */             
/*     */             } else {
/*     */               
/* 121 */               aint1[j + i * areaWidth] = k;
/*     */             }
/*     */           
/*     */           } 
/*     */         } else {
/*     */           
/* 127 */           replaceIfNeighborOcean(aint, aint1, j, i, areaWidth, k, Biome.getIdForBiome(Biomes.STONE_BEACH));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     return aint1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void replaceIfNeighborOcean(int[] p_151632_1_, int[] p_151632_2_, int p_151632_3_, int p_151632_4_, int p_151632_5_, int p_151632_6_, int p_151632_7_) {
/* 137 */     if (isBiomeOceanic(p_151632_6_)) {
/*     */       
/* 139 */       p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_6_;
/*     */     }
/*     */     else {
/*     */       
/* 143 */       int i = p_151632_1_[p_151632_3_ + 1 + (p_151632_4_ + 1 - 1) * (p_151632_5_ + 2)];
/* 144 */       int j = p_151632_1_[p_151632_3_ + 1 + 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
/* 145 */       int k = p_151632_1_[p_151632_3_ + 1 - 1 + (p_151632_4_ + 1) * (p_151632_5_ + 2)];
/* 146 */       int l = p_151632_1_[p_151632_3_ + 1 + (p_151632_4_ + 1 + 1) * (p_151632_5_ + 2)];
/*     */       
/* 148 */       if (!isBiomeOceanic(i) && !isBiomeOceanic(j) && !isBiomeOceanic(k) && !isBiomeOceanic(l)) {
/*     */         
/* 150 */         p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_6_;
/*     */       }
/*     */       else {
/*     */         
/* 154 */         p_151632_2_[p_151632_3_ + p_151632_4_ * p_151632_5_] = p_151632_7_;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isJungleCompatible(int p_151631_1_) {
/* 161 */     if (Biome.getBiome(p_151631_1_) != null && Biome.getBiome(p_151631_1_).getBiomeClass() == BiomeJungle.class)
/*     */     {
/* 163 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 167 */     return !(p_151631_1_ != Biome.getIdForBiome(Biomes.JUNGLE_EDGE) && p_151631_1_ != Biome.getIdForBiome(Biomes.JUNGLE) && p_151631_1_ != Biome.getIdForBiome(Biomes.JUNGLE_HILLS) && p_151631_1_ != Biome.getIdForBiome(Biomes.FOREST) && p_151631_1_ != Biome.getIdForBiome(Biomes.TAIGA) && !isBiomeOceanic(p_151631_1_));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isMesa(int p_151633_1_) {
/* 173 */     return Biome.getBiome(p_151633_1_) instanceof net.minecraft.world.biome.BiomeMesa;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\layer\GenLayerShore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */