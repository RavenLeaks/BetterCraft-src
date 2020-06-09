/*     */ package net.minecraft.world.gen.layer;
/*     */ 
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class GenLayerHills
/*     */   extends GenLayer {
/*  10 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   
/*     */   private final GenLayer riverLayer;
/*     */   
/*     */   public GenLayerHills(long p_i45479_1_, GenLayer p_i45479_3_, GenLayer p_i45479_4_) {
/*  15 */     super(p_i45479_1_);
/*  16 */     this.parent = p_i45479_3_;
/*  17 */     this.riverLayer = p_i45479_4_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/*  26 */     int[] aint = this.parent.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
/*  27 */     int[] aint1 = this.riverLayer.getInts(areaX - 1, areaY - 1, areaWidth + 2, areaHeight + 2);
/*  28 */     int[] aint2 = IntCache.getIntCache(areaWidth * areaHeight);
/*     */     
/*  30 */     for (int i = 0; i < areaHeight; i++) {
/*     */       
/*  32 */       for (int j = 0; j < areaWidth; j++) {
/*     */         
/*  34 */         initChunkSeed((j + areaX), (i + areaY));
/*  35 */         int k = aint[j + 1 + (i + 1) * (areaWidth + 2)];
/*  36 */         int l = aint1[j + 1 + (i + 1) * (areaWidth + 2)];
/*  37 */         boolean flag = ((l - 2) % 29 == 0);
/*     */         
/*  39 */         if (k > 255)
/*     */         {
/*  41 */           LOGGER.debug("old! {}", Integer.valueOf(k));
/*     */         }
/*     */         
/*  44 */         Biome biome = Biome.getBiomeForId(k);
/*  45 */         boolean flag1 = (biome != null && biome.isMutation());
/*     */         
/*  47 */         if (k != 0 && l >= 2 && (l - 2) % 29 == 1 && !flag1) {
/*     */           
/*  49 */           Biome biome3 = Biome.getMutationForBiome(biome);
/*  50 */           aint2[j + i * areaWidth] = (biome3 == null) ? k : Biome.getIdForBiome(biome3);
/*     */         }
/*  52 */         else if (nextInt(3) != 0 && !flag) {
/*     */           
/*  54 */           aint2[j + i * areaWidth] = k;
/*     */         }
/*     */         else {
/*     */           
/*  58 */           Biome biome1 = biome;
/*     */           
/*  60 */           if (biome == Biomes.DESERT) {
/*     */             
/*  62 */             biome1 = Biomes.DESERT_HILLS;
/*     */           }
/*  64 */           else if (biome == Biomes.FOREST) {
/*     */             
/*  66 */             biome1 = Biomes.FOREST_HILLS;
/*     */           }
/*  68 */           else if (biome == Biomes.BIRCH_FOREST) {
/*     */             
/*  70 */             biome1 = Biomes.BIRCH_FOREST_HILLS;
/*     */           }
/*  72 */           else if (biome == Biomes.ROOFED_FOREST) {
/*     */             
/*  74 */             biome1 = Biomes.PLAINS;
/*     */           }
/*  76 */           else if (biome == Biomes.TAIGA) {
/*     */             
/*  78 */             biome1 = Biomes.TAIGA_HILLS;
/*     */           }
/*  80 */           else if (biome == Biomes.REDWOOD_TAIGA) {
/*     */             
/*  82 */             biome1 = Biomes.REDWOOD_TAIGA_HILLS;
/*     */           }
/*  84 */           else if (biome == Biomes.COLD_TAIGA) {
/*     */             
/*  86 */             biome1 = Biomes.COLD_TAIGA_HILLS;
/*     */           }
/*  88 */           else if (biome == Biomes.PLAINS) {
/*     */             
/*  90 */             if (nextInt(3) == 0)
/*     */             {
/*  92 */               biome1 = Biomes.FOREST_HILLS;
/*     */             }
/*     */             else
/*     */             {
/*  96 */               biome1 = Biomes.FOREST;
/*     */             }
/*     */           
/*  99 */           } else if (biome == Biomes.ICE_PLAINS) {
/*     */             
/* 101 */             biome1 = Biomes.ICE_MOUNTAINS;
/*     */           }
/* 103 */           else if (biome == Biomes.JUNGLE) {
/*     */             
/* 105 */             biome1 = Biomes.JUNGLE_HILLS;
/*     */           }
/* 107 */           else if (biome == Biomes.OCEAN) {
/*     */             
/* 109 */             biome1 = Biomes.DEEP_OCEAN;
/*     */           }
/* 111 */           else if (biome == Biomes.EXTREME_HILLS) {
/*     */             
/* 113 */             biome1 = Biomes.EXTREME_HILLS_WITH_TREES;
/*     */           }
/* 115 */           else if (biome == Biomes.SAVANNA) {
/*     */             
/* 117 */             biome1 = Biomes.SAVANNA_PLATEAU;
/*     */           }
/* 119 */           else if (biomesEqualOrMesaPlateau(k, Biome.getIdForBiome(Biomes.MESA_ROCK))) {
/*     */             
/* 121 */             biome1 = Biomes.MESA;
/*     */           }
/* 123 */           else if (biome == Biomes.DEEP_OCEAN && nextInt(3) == 0) {
/*     */             
/* 125 */             int i1 = nextInt(2);
/*     */             
/* 127 */             if (i1 == 0) {
/*     */               
/* 129 */               biome1 = Biomes.PLAINS;
/*     */             }
/*     */             else {
/*     */               
/* 133 */               biome1 = Biomes.FOREST;
/*     */             } 
/*     */           } 
/*     */           
/* 137 */           int j2 = Biome.getIdForBiome(biome1);
/*     */           
/* 139 */           if (flag && j2 != k) {
/*     */             
/* 141 */             Biome biome2 = Biome.getMutationForBiome(biome1);
/* 142 */             j2 = (biome2 == null) ? k : Biome.getIdForBiome(biome2);
/*     */           } 
/*     */           
/* 145 */           if (j2 == k) {
/*     */             
/* 147 */             aint2[j + i * areaWidth] = k;
/*     */           }
/*     */           else {
/*     */             
/* 151 */             int k2 = aint[j + 1 + (i + 0) * (areaWidth + 2)];
/* 152 */             int j1 = aint[j + 2 + (i + 1) * (areaWidth + 2)];
/* 153 */             int k1 = aint[j + 0 + (i + 1) * (areaWidth + 2)];
/* 154 */             int l1 = aint[j + 1 + (i + 2) * (areaWidth + 2)];
/* 155 */             int i2 = 0;
/*     */             
/* 157 */             if (biomesEqualOrMesaPlateau(k2, k))
/*     */             {
/* 159 */               i2++;
/*     */             }
/*     */             
/* 162 */             if (biomesEqualOrMesaPlateau(j1, k))
/*     */             {
/* 164 */               i2++;
/*     */             }
/*     */             
/* 167 */             if (biomesEqualOrMesaPlateau(k1, k))
/*     */             {
/* 169 */               i2++;
/*     */             }
/*     */             
/* 172 */             if (biomesEqualOrMesaPlateau(l1, k))
/*     */             {
/* 174 */               i2++;
/*     */             }
/*     */             
/* 177 */             if (i2 >= 3) {
/*     */               
/* 179 */               aint2[j + i * areaWidth] = j2;
/*     */             }
/*     */             else {
/*     */               
/* 183 */               aint2[j + i * areaWidth] = k;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 190 */     return aint2;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\layer\GenLayerHills.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */