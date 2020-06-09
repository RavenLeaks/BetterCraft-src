/*     */ package net.minecraft.world.gen.layer;
/*     */ 
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.gen.ChunkGeneratorSettings;
/*     */ 
/*     */ public class GenLayerBiome
/*     */   extends GenLayer {
/*  10 */   private Biome[] warmBiomes = new Biome[] { Biomes.DESERT, Biomes.DESERT, Biomes.DESERT, Biomes.SAVANNA, Biomes.SAVANNA, Biomes.PLAINS };
/*  11 */   private final Biome[] mediumBiomes = new Biome[] { Biomes.FOREST, Biomes.ROOFED_FOREST, Biomes.EXTREME_HILLS, Biomes.PLAINS, Biomes.BIRCH_FOREST, Biomes.SWAMPLAND };
/*  12 */   private final Biome[] coldBiomes = new Biome[] { Biomes.FOREST, Biomes.EXTREME_HILLS, Biomes.TAIGA, Biomes.PLAINS };
/*  13 */   private final Biome[] iceBiomes = new Biome[] { Biomes.ICE_PLAINS, Biomes.ICE_PLAINS, Biomes.ICE_PLAINS, Biomes.COLD_TAIGA };
/*     */   
/*     */   private final ChunkGeneratorSettings settings;
/*     */   
/*     */   public GenLayerBiome(long p_i45560_1_, GenLayer p_i45560_3_, WorldType p_i45560_4_, ChunkGeneratorSettings p_i45560_5_) {
/*  18 */     super(p_i45560_1_);
/*  19 */     this.parent = p_i45560_3_;
/*     */     
/*  21 */     if (p_i45560_4_ == WorldType.DEFAULT_1_1) {
/*     */       
/*  23 */       this.warmBiomes = new Biome[] { Biomes.DESERT, Biomes.FOREST, Biomes.EXTREME_HILLS, Biomes.SWAMPLAND, Biomes.PLAINS, Biomes.TAIGA };
/*  24 */       this.settings = null;
/*     */     }
/*     */     else {
/*     */       
/*  28 */       this.settings = p_i45560_5_;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getInts(int areaX, int areaY, int areaWidth, int areaHeight) {
/*  38 */     int[] aint = this.parent.getInts(areaX, areaY, areaWidth, areaHeight);
/*  39 */     int[] aint1 = IntCache.getIntCache(areaWidth * areaHeight);
/*     */     
/*  41 */     for (int i = 0; i < areaHeight; i++) {
/*     */       
/*  43 */       for (int j = 0; j < areaWidth; j++) {
/*     */         
/*  45 */         initChunkSeed((j + areaX), (i + areaY));
/*  46 */         int k = aint[j + i * areaWidth];
/*  47 */         int l = (k & 0xF00) >> 8;
/*  48 */         k &= 0xFFFFF0FF;
/*     */         
/*  50 */         if (this.settings != null && this.settings.fixedBiome >= 0) {
/*     */           
/*  52 */           aint1[j + i * areaWidth] = this.settings.fixedBiome;
/*     */         }
/*  54 */         else if (isBiomeOceanic(k)) {
/*     */           
/*  56 */           aint1[j + i * areaWidth] = k;
/*     */         }
/*  58 */         else if (k == Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND)) {
/*     */           
/*  60 */           aint1[j + i * areaWidth] = k;
/*     */         }
/*  62 */         else if (k == 1) {
/*     */           
/*  64 */           if (l > 0) {
/*     */             
/*  66 */             if (nextInt(3) == 0)
/*     */             {
/*  68 */               aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.MESA_CLEAR_ROCK);
/*     */             }
/*     */             else
/*     */             {
/*  72 */               aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.MESA_ROCK);
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/*  77 */             aint1[j + i * areaWidth] = Biome.getIdForBiome(this.warmBiomes[nextInt(this.warmBiomes.length)]);
/*     */           }
/*     */         
/*  80 */         } else if (k == 2) {
/*     */           
/*  82 */           if (l > 0)
/*     */           {
/*  84 */             aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.JUNGLE);
/*     */           }
/*     */           else
/*     */           {
/*  88 */             aint1[j + i * areaWidth] = Biome.getIdForBiome(this.mediumBiomes[nextInt(this.mediumBiomes.length)]);
/*     */           }
/*     */         
/*  91 */         } else if (k == 3) {
/*     */           
/*  93 */           if (l > 0)
/*     */           {
/*  95 */             aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.REDWOOD_TAIGA);
/*     */           }
/*     */           else
/*     */           {
/*  99 */             aint1[j + i * areaWidth] = Biome.getIdForBiome(this.coldBiomes[nextInt(this.coldBiomes.length)]);
/*     */           }
/*     */         
/* 102 */         } else if (k == 4) {
/*     */           
/* 104 */           aint1[j + i * areaWidth] = Biome.getIdForBiome(this.iceBiomes[nextInt(this.iceBiomes.length)]);
/*     */         }
/*     */         else {
/*     */           
/* 108 */           aint1[j + i * areaWidth] = Biome.getIdForBiome(Biomes.MUSHROOM_ISLAND);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     return aint1;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\layer\GenLayerBiome.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */