/*     */ package net.minecraft.init;
/*     */ 
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class Biomes
/*     */ {
/*     */   private static Biome getRegisteredBiome(String id) {
/*  88 */     Biome biome = (Biome)Biome.REGISTRY.getObject(new ResourceLocation(id));
/*     */     
/*  90 */     if (biome == null)
/*     */     {
/*  92 */       throw new IllegalStateException("Invalid Biome requested: " + id);
/*     */     }
/*     */ 
/*     */     
/*  96 */     return biome;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static {
/* 102 */     if (!Bootstrap.isRegistered())
/*     */     {
/* 104 */       throw new RuntimeException("Accessed Biomes before Bootstrap!");
/*     */     }
/*     */   }
/*     */   
/* 108 */   public static final Biome OCEAN = getRegisteredBiome("ocean");
/* 109 */   public static final Biome DEFAULT = OCEAN;
/* 110 */   public static final Biome PLAINS = getRegisteredBiome("plains");
/* 111 */   public static final Biome DESERT = getRegisteredBiome("desert");
/* 112 */   public static final Biome EXTREME_HILLS = getRegisteredBiome("extreme_hills");
/* 113 */   public static final Biome FOREST = getRegisteredBiome("forest");
/* 114 */   public static final Biome TAIGA = getRegisteredBiome("taiga");
/* 115 */   public static final Biome SWAMPLAND = getRegisteredBiome("swampland");
/* 116 */   public static final Biome RIVER = getRegisteredBiome("river");
/* 117 */   public static final Biome HELL = getRegisteredBiome("hell");
/* 118 */   public static final Biome SKY = getRegisteredBiome("sky");
/* 119 */   public static final Biome FROZEN_OCEAN = getRegisteredBiome("frozen_ocean");
/* 120 */   public static final Biome FROZEN_RIVER = getRegisteredBiome("frozen_river");
/* 121 */   public static final Biome ICE_PLAINS = getRegisteredBiome("ice_flats");
/* 122 */   public static final Biome ICE_MOUNTAINS = getRegisteredBiome("ice_mountains");
/* 123 */   public static final Biome MUSHROOM_ISLAND = getRegisteredBiome("mushroom_island");
/* 124 */   public static final Biome MUSHROOM_ISLAND_SHORE = getRegisteredBiome("mushroom_island_shore");
/* 125 */   public static final Biome BEACH = getRegisteredBiome("beaches");
/* 126 */   public static final Biome DESERT_HILLS = getRegisteredBiome("desert_hills");
/* 127 */   public static final Biome FOREST_HILLS = getRegisteredBiome("forest_hills");
/* 128 */   public static final Biome TAIGA_HILLS = getRegisteredBiome("taiga_hills");
/* 129 */   public static final Biome EXTREME_HILLS_EDGE = getRegisteredBiome("smaller_extreme_hills");
/* 130 */   public static final Biome JUNGLE = getRegisteredBiome("jungle");
/* 131 */   public static final Biome JUNGLE_HILLS = getRegisteredBiome("jungle_hills");
/* 132 */   public static final Biome JUNGLE_EDGE = getRegisteredBiome("jungle_edge");
/* 133 */   public static final Biome DEEP_OCEAN = getRegisteredBiome("deep_ocean");
/* 134 */   public static final Biome STONE_BEACH = getRegisteredBiome("stone_beach");
/* 135 */   public static final Biome COLD_BEACH = getRegisteredBiome("cold_beach");
/* 136 */   public static final Biome BIRCH_FOREST = getRegisteredBiome("birch_forest");
/* 137 */   public static final Biome BIRCH_FOREST_HILLS = getRegisteredBiome("birch_forest_hills");
/* 138 */   public static final Biome ROOFED_FOREST = getRegisteredBiome("roofed_forest");
/* 139 */   public static final Biome COLD_TAIGA = getRegisteredBiome("taiga_cold");
/* 140 */   public static final Biome COLD_TAIGA_HILLS = getRegisteredBiome("taiga_cold_hills");
/* 141 */   public static final Biome REDWOOD_TAIGA = getRegisteredBiome("redwood_taiga");
/* 142 */   public static final Biome REDWOOD_TAIGA_HILLS = getRegisteredBiome("redwood_taiga_hills");
/* 143 */   public static final Biome EXTREME_HILLS_WITH_TREES = getRegisteredBiome("extreme_hills_with_trees");
/* 144 */   public static final Biome SAVANNA = getRegisteredBiome("savanna");
/* 145 */   public static final Biome SAVANNA_PLATEAU = getRegisteredBiome("savanna_rock");
/* 146 */   public static final Biome MESA = getRegisteredBiome("mesa");
/* 147 */   public static final Biome MESA_ROCK = getRegisteredBiome("mesa_rock");
/* 148 */   public static final Biome MESA_CLEAR_ROCK = getRegisteredBiome("mesa_clear_rock");
/* 149 */   public static final Biome VOID = getRegisteredBiome("void");
/* 150 */   public static final Biome MUTATED_PLAINS = getRegisteredBiome("mutated_plains");
/* 151 */   public static final Biome MUTATED_DESERT = getRegisteredBiome("mutated_desert");
/* 152 */   public static final Biome MUTATED_EXTREME_HILLS = getRegisteredBiome("mutated_extreme_hills");
/* 153 */   public static final Biome MUTATED_FOREST = getRegisteredBiome("mutated_forest");
/* 154 */   public static final Biome MUTATED_TAIGA = getRegisteredBiome("mutated_taiga");
/* 155 */   public static final Biome MUTATED_SWAMPLAND = getRegisteredBiome("mutated_swampland");
/* 156 */   public static final Biome MUTATED_ICE_FLATS = getRegisteredBiome("mutated_ice_flats");
/* 157 */   public static final Biome MUTATED_JUNGLE = getRegisteredBiome("mutated_jungle");
/* 158 */   public static final Biome MUTATED_JUNGLE_EDGE = getRegisteredBiome("mutated_jungle_edge");
/* 159 */   public static final Biome MUTATED_BIRCH_FOREST = getRegisteredBiome("mutated_birch_forest");
/* 160 */   public static final Biome MUTATED_BIRCH_FOREST_HILLS = getRegisteredBiome("mutated_birch_forest_hills");
/* 161 */   public static final Biome MUTATED_ROOFED_FOREST = getRegisteredBiome("mutated_roofed_forest");
/* 162 */   public static final Biome MUTATED_TAIGA_COLD = getRegisteredBiome("mutated_taiga_cold");
/* 163 */   public static final Biome MUTATED_REDWOOD_TAIGA = getRegisteredBiome("mutated_redwood_taiga");
/* 164 */   public static final Biome MUTATED_REDWOOD_TAIGA_HILLS = getRegisteredBiome("mutated_redwood_taiga_hills");
/* 165 */   public static final Biome MUTATED_EXTREME_HILLS_WITH_TREES = getRegisteredBiome("mutated_extreme_hills_with_trees");
/* 166 */   public static final Biome MUTATED_SAVANNA = getRegisteredBiome("mutated_savanna");
/* 167 */   public static final Biome MUTATED_SAVANNA_ROCK = getRegisteredBiome("mutated_savanna_rock");
/* 168 */   public static final Biome MUTATED_MESA = getRegisteredBiome("mutated_mesa");
/* 169 */   public static final Biome MUTATED_MESA_ROCK = getRegisteredBiome("mutated_mesa_rock");
/* 170 */   public static final Biome MUTATED_MESA_CLEAR_ROCK = getRegisteredBiome("mutated_mesa_clear_rock");
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\init\Biomes.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */