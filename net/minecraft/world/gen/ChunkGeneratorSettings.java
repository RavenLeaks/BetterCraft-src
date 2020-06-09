/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import com.google.common.annotations.VisibleForTesting;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonDeserializationContext;
/*     */ import com.google.gson.JsonDeserializer;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonSerializationContext;
/*     */ import com.google.gson.JsonSerializer;
/*     */ import java.lang.reflect.Type;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.util.JsonUtils;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ 
/*     */ 
/*     */ public class ChunkGeneratorSettings
/*     */ {
/*     */   public final float coordinateScale;
/*     */   public final float heightScale;
/*     */   public final float upperLimitScale;
/*     */   public final float lowerLimitScale;
/*     */   public final float depthNoiseScaleX;
/*     */   public final float depthNoiseScaleZ;
/*     */   public final float depthNoiseScaleExponent;
/*     */   public final float mainNoiseScaleX;
/*     */   public final float mainNoiseScaleY;
/*     */   public final float mainNoiseScaleZ;
/*     */   public final float baseSize;
/*     */   public final float stretchY;
/*     */   public final float biomeDepthWeight;
/*     */   public final float biomeDepthOffSet;
/*     */   public final float biomeScaleWeight;
/*     */   public final float biomeScaleOffset;
/*     */   public final int seaLevel;
/*     */   public final boolean useCaves;
/*     */   public final boolean useDungeons;
/*     */   public final int dungeonChance;
/*     */   public final boolean useStrongholds;
/*     */   public final boolean useVillages;
/*     */   public final boolean useMineShafts;
/*     */   public final boolean useTemples;
/*     */   public final boolean useMonuments;
/*     */   public final boolean field_191077_z;
/*     */   public final boolean useRavines;
/*     */   public final boolean useWaterLakes;
/*     */   public final int waterLakeChance;
/*     */   public final boolean useLavaLakes;
/*     */   public final int lavaLakeChance;
/*     */   public final boolean useLavaOceans;
/*     */   public final int fixedBiome;
/*     */   public final int biomeSize;
/*     */   public final int riverSize;
/*     */   public final int dirtSize;
/*     */   public final int dirtCount;
/*     */   public final int dirtMinHeight;
/*     */   public final int dirtMaxHeight;
/*     */   public final int gravelSize;
/*     */   public final int gravelCount;
/*     */   public final int gravelMinHeight;
/*     */   public final int gravelMaxHeight;
/*     */   public final int graniteSize;
/*     */   public final int graniteCount;
/*     */   public final int graniteMinHeight;
/*     */   public final int graniteMaxHeight;
/*     */   public final int dioriteSize;
/*     */   public final int dioriteCount;
/*     */   public final int dioriteMinHeight;
/*     */   public final int dioriteMaxHeight;
/*     */   public final int andesiteSize;
/*     */   public final int andesiteCount;
/*     */   public final int andesiteMinHeight;
/*     */   public final int andesiteMaxHeight;
/*     */   public final int coalSize;
/*     */   public final int coalCount;
/*     */   public final int coalMinHeight;
/*     */   public final int coalMaxHeight;
/*     */   public final int ironSize;
/*     */   public final int ironCount;
/*     */   public final int ironMinHeight;
/*     */   public final int ironMaxHeight;
/*     */   public final int goldSize;
/*     */   public final int goldCount;
/*     */   public final int goldMinHeight;
/*     */   public final int goldMaxHeight;
/*     */   public final int redstoneSize;
/*     */   public final int redstoneCount;
/*     */   public final int redstoneMinHeight;
/*     */   public final int redstoneMaxHeight;
/*     */   public final int diamondSize;
/*     */   public final int diamondCount;
/*     */   public final int diamondMinHeight;
/*     */   public final int diamondMaxHeight;
/*     */   public final int lapisSize;
/*     */   public final int lapisCount;
/*     */   public final int lapisCenterHeight;
/*     */   public final int lapisSpread;
/*     */   
/*     */   private ChunkGeneratorSettings(Factory settingsFactory) {
/* 102 */     this.coordinateScale = settingsFactory.coordinateScale;
/* 103 */     this.heightScale = settingsFactory.heightScale;
/* 104 */     this.upperLimitScale = settingsFactory.upperLimitScale;
/* 105 */     this.lowerLimitScale = settingsFactory.lowerLimitScale;
/* 106 */     this.depthNoiseScaleX = settingsFactory.depthNoiseScaleX;
/* 107 */     this.depthNoiseScaleZ = settingsFactory.depthNoiseScaleZ;
/* 108 */     this.depthNoiseScaleExponent = settingsFactory.depthNoiseScaleExponent;
/* 109 */     this.mainNoiseScaleX = settingsFactory.mainNoiseScaleX;
/* 110 */     this.mainNoiseScaleY = settingsFactory.mainNoiseScaleY;
/* 111 */     this.mainNoiseScaleZ = settingsFactory.mainNoiseScaleZ;
/* 112 */     this.baseSize = settingsFactory.baseSize;
/* 113 */     this.stretchY = settingsFactory.stretchY;
/* 114 */     this.biomeDepthWeight = settingsFactory.biomeDepthWeight;
/* 115 */     this.biomeDepthOffSet = settingsFactory.biomeDepthOffset;
/* 116 */     this.biomeScaleWeight = settingsFactory.biomeScaleWeight;
/* 117 */     this.biomeScaleOffset = settingsFactory.biomeScaleOffset;
/* 118 */     this.seaLevel = settingsFactory.seaLevel;
/* 119 */     this.useCaves = settingsFactory.useCaves;
/* 120 */     this.useDungeons = settingsFactory.useDungeons;
/* 121 */     this.dungeonChance = settingsFactory.dungeonChance;
/* 122 */     this.useStrongholds = settingsFactory.useStrongholds;
/* 123 */     this.useVillages = settingsFactory.useVillages;
/* 124 */     this.useMineShafts = settingsFactory.useMineShafts;
/* 125 */     this.useTemples = settingsFactory.useTemples;
/* 126 */     this.useMonuments = settingsFactory.useMonuments;
/* 127 */     this.field_191077_z = settingsFactory.field_191076_A;
/* 128 */     this.useRavines = settingsFactory.useRavines;
/* 129 */     this.useWaterLakes = settingsFactory.useWaterLakes;
/* 130 */     this.waterLakeChance = settingsFactory.waterLakeChance;
/* 131 */     this.useLavaLakes = settingsFactory.useLavaLakes;
/* 132 */     this.lavaLakeChance = settingsFactory.lavaLakeChance;
/* 133 */     this.useLavaOceans = settingsFactory.useLavaOceans;
/* 134 */     this.fixedBiome = settingsFactory.fixedBiome;
/* 135 */     this.biomeSize = settingsFactory.biomeSize;
/* 136 */     this.riverSize = settingsFactory.riverSize;
/* 137 */     this.dirtSize = settingsFactory.dirtSize;
/* 138 */     this.dirtCount = settingsFactory.dirtCount;
/* 139 */     this.dirtMinHeight = settingsFactory.dirtMinHeight;
/* 140 */     this.dirtMaxHeight = settingsFactory.dirtMaxHeight;
/* 141 */     this.gravelSize = settingsFactory.gravelSize;
/* 142 */     this.gravelCount = settingsFactory.gravelCount;
/* 143 */     this.gravelMinHeight = settingsFactory.gravelMinHeight;
/* 144 */     this.gravelMaxHeight = settingsFactory.gravelMaxHeight;
/* 145 */     this.graniteSize = settingsFactory.graniteSize;
/* 146 */     this.graniteCount = settingsFactory.graniteCount;
/* 147 */     this.graniteMinHeight = settingsFactory.graniteMinHeight;
/* 148 */     this.graniteMaxHeight = settingsFactory.graniteMaxHeight;
/* 149 */     this.dioriteSize = settingsFactory.dioriteSize;
/* 150 */     this.dioriteCount = settingsFactory.dioriteCount;
/* 151 */     this.dioriteMinHeight = settingsFactory.dioriteMinHeight;
/* 152 */     this.dioriteMaxHeight = settingsFactory.dioriteMaxHeight;
/* 153 */     this.andesiteSize = settingsFactory.andesiteSize;
/* 154 */     this.andesiteCount = settingsFactory.andesiteCount;
/* 155 */     this.andesiteMinHeight = settingsFactory.andesiteMinHeight;
/* 156 */     this.andesiteMaxHeight = settingsFactory.andesiteMaxHeight;
/* 157 */     this.coalSize = settingsFactory.coalSize;
/* 158 */     this.coalCount = settingsFactory.coalCount;
/* 159 */     this.coalMinHeight = settingsFactory.coalMinHeight;
/* 160 */     this.coalMaxHeight = settingsFactory.coalMaxHeight;
/* 161 */     this.ironSize = settingsFactory.ironSize;
/* 162 */     this.ironCount = settingsFactory.ironCount;
/* 163 */     this.ironMinHeight = settingsFactory.ironMinHeight;
/* 164 */     this.ironMaxHeight = settingsFactory.ironMaxHeight;
/* 165 */     this.goldSize = settingsFactory.goldSize;
/* 166 */     this.goldCount = settingsFactory.goldCount;
/* 167 */     this.goldMinHeight = settingsFactory.goldMinHeight;
/* 168 */     this.goldMaxHeight = settingsFactory.goldMaxHeight;
/* 169 */     this.redstoneSize = settingsFactory.redstoneSize;
/* 170 */     this.redstoneCount = settingsFactory.redstoneCount;
/* 171 */     this.redstoneMinHeight = settingsFactory.redstoneMinHeight;
/* 172 */     this.redstoneMaxHeight = settingsFactory.redstoneMaxHeight;
/* 173 */     this.diamondSize = settingsFactory.diamondSize;
/* 174 */     this.diamondCount = settingsFactory.diamondCount;
/* 175 */     this.diamondMinHeight = settingsFactory.diamondMinHeight;
/* 176 */     this.diamondMaxHeight = settingsFactory.diamondMaxHeight;
/* 177 */     this.lapisSize = settingsFactory.lapisSize;
/* 178 */     this.lapisCount = settingsFactory.lapisCount;
/* 179 */     this.lapisCenterHeight = settingsFactory.lapisCenterHeight;
/* 180 */     this.lapisSpread = settingsFactory.lapisSpread;
/*     */   }
/*     */   
/*     */   public static class Factory
/*     */   {
/*     */     @VisibleForTesting
/* 186 */     static final Gson JSON_ADAPTER = (new GsonBuilder()).registerTypeAdapter(Factory.class, new ChunkGeneratorSettings.Serializer()).create();
/* 187 */     public float coordinateScale = 684.412F;
/* 188 */     public float heightScale = 684.412F;
/* 189 */     public float upperLimitScale = 512.0F;
/* 190 */     public float lowerLimitScale = 512.0F;
/* 191 */     public float depthNoiseScaleX = 200.0F;
/* 192 */     public float depthNoiseScaleZ = 200.0F;
/* 193 */     public float depthNoiseScaleExponent = 0.5F;
/* 194 */     public float mainNoiseScaleX = 80.0F;
/* 195 */     public float mainNoiseScaleY = 160.0F;
/* 196 */     public float mainNoiseScaleZ = 80.0F;
/* 197 */     public float baseSize = 8.5F;
/* 198 */     public float stretchY = 12.0F;
/* 199 */     public float biomeDepthWeight = 1.0F;
/*     */     public float biomeDepthOffset;
/* 201 */     public float biomeScaleWeight = 1.0F;
/*     */     public float biomeScaleOffset;
/* 203 */     public int seaLevel = 63;
/*     */     public boolean useCaves = true;
/*     */     public boolean useDungeons = true;
/* 206 */     public int dungeonChance = 8;
/*     */     public boolean useStrongholds = true;
/*     */     public boolean useVillages = true;
/*     */     public boolean useMineShafts = true;
/*     */     public boolean useTemples = true;
/*     */     public boolean useMonuments = true;
/*     */     public boolean field_191076_A = true;
/*     */     public boolean useRavines = true;
/*     */     public boolean useWaterLakes = true;
/* 215 */     public int waterLakeChance = 4;
/*     */     public boolean useLavaLakes = true;
/* 217 */     public int lavaLakeChance = 80;
/*     */     public boolean useLavaOceans;
/* 219 */     public int fixedBiome = -1;
/* 220 */     public int biomeSize = 4;
/* 221 */     public int riverSize = 4;
/* 222 */     public int dirtSize = 33;
/* 223 */     public int dirtCount = 10;
/*     */     public int dirtMinHeight;
/* 225 */     public int dirtMaxHeight = 256;
/* 226 */     public int gravelSize = 33;
/* 227 */     public int gravelCount = 8;
/*     */     public int gravelMinHeight;
/* 229 */     public int gravelMaxHeight = 256;
/* 230 */     public int graniteSize = 33;
/* 231 */     public int graniteCount = 10;
/*     */     public int graniteMinHeight;
/* 233 */     public int graniteMaxHeight = 80;
/* 234 */     public int dioriteSize = 33;
/* 235 */     public int dioriteCount = 10;
/*     */     public int dioriteMinHeight;
/* 237 */     public int dioriteMaxHeight = 80;
/* 238 */     public int andesiteSize = 33;
/* 239 */     public int andesiteCount = 10;
/*     */     public int andesiteMinHeight;
/* 241 */     public int andesiteMaxHeight = 80;
/* 242 */     public int coalSize = 17;
/* 243 */     public int coalCount = 20;
/*     */     public int coalMinHeight;
/* 245 */     public int coalMaxHeight = 128;
/* 246 */     public int ironSize = 9;
/* 247 */     public int ironCount = 20;
/*     */     public int ironMinHeight;
/* 249 */     public int ironMaxHeight = 64;
/* 250 */     public int goldSize = 9;
/* 251 */     public int goldCount = 2;
/*     */     public int goldMinHeight;
/* 253 */     public int goldMaxHeight = 32;
/* 254 */     public int redstoneSize = 8;
/* 255 */     public int redstoneCount = 8;
/*     */     public int redstoneMinHeight;
/* 257 */     public int redstoneMaxHeight = 16;
/* 258 */     public int diamondSize = 8;
/* 259 */     public int diamondCount = 1;
/*     */     public int diamondMinHeight;
/* 261 */     public int diamondMaxHeight = 16;
/* 262 */     public int lapisSize = 7;
/* 263 */     public int lapisCount = 1;
/* 264 */     public int lapisCenterHeight = 16;
/* 265 */     public int lapisSpread = 16;
/*     */ 
/*     */     
/*     */     public static Factory jsonToFactory(String p_177865_0_) {
/* 269 */       if (p_177865_0_.isEmpty())
/*     */       {
/* 271 */         return new Factory();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 277 */         return (Factory)JsonUtils.gsonDeserialize(JSON_ADAPTER, p_177865_0_, Factory.class);
/*     */       }
/* 279 */       catch (Exception var2) {
/*     */         
/* 281 */         return new Factory();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public String toString() {
/* 288 */       return JSON_ADAPTER.toJson(this);
/*     */     }
/*     */ 
/*     */     
/*     */     public Factory() {
/* 293 */       setDefaults();
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDefaults() {
/* 298 */       this.coordinateScale = 684.412F;
/* 299 */       this.heightScale = 684.412F;
/* 300 */       this.upperLimitScale = 512.0F;
/* 301 */       this.lowerLimitScale = 512.0F;
/* 302 */       this.depthNoiseScaleX = 200.0F;
/* 303 */       this.depthNoiseScaleZ = 200.0F;
/* 304 */       this.depthNoiseScaleExponent = 0.5F;
/* 305 */       this.mainNoiseScaleX = 80.0F;
/* 306 */       this.mainNoiseScaleY = 160.0F;
/* 307 */       this.mainNoiseScaleZ = 80.0F;
/* 308 */       this.baseSize = 8.5F;
/* 309 */       this.stretchY = 12.0F;
/* 310 */       this.biomeDepthWeight = 1.0F;
/* 311 */       this.biomeDepthOffset = 0.0F;
/* 312 */       this.biomeScaleWeight = 1.0F;
/* 313 */       this.biomeScaleOffset = 0.0F;
/* 314 */       this.seaLevel = 63;
/* 315 */       this.useCaves = true;
/* 316 */       this.useDungeons = true;
/* 317 */       this.dungeonChance = 8;
/* 318 */       this.useStrongholds = true;
/* 319 */       this.useVillages = true;
/* 320 */       this.useMineShafts = true;
/* 321 */       this.useTemples = true;
/* 322 */       this.useMonuments = true;
/* 323 */       this.field_191076_A = true;
/* 324 */       this.useRavines = true;
/* 325 */       this.useWaterLakes = true;
/* 326 */       this.waterLakeChance = 4;
/* 327 */       this.useLavaLakes = true;
/* 328 */       this.lavaLakeChance = 80;
/* 329 */       this.useLavaOceans = false;
/* 330 */       this.fixedBiome = -1;
/* 331 */       this.biomeSize = 4;
/* 332 */       this.riverSize = 4;
/* 333 */       this.dirtSize = 33;
/* 334 */       this.dirtCount = 10;
/* 335 */       this.dirtMinHeight = 0;
/* 336 */       this.dirtMaxHeight = 256;
/* 337 */       this.gravelSize = 33;
/* 338 */       this.gravelCount = 8;
/* 339 */       this.gravelMinHeight = 0;
/* 340 */       this.gravelMaxHeight = 256;
/* 341 */       this.graniteSize = 33;
/* 342 */       this.graniteCount = 10;
/* 343 */       this.graniteMinHeight = 0;
/* 344 */       this.graniteMaxHeight = 80;
/* 345 */       this.dioriteSize = 33;
/* 346 */       this.dioriteCount = 10;
/* 347 */       this.dioriteMinHeight = 0;
/* 348 */       this.dioriteMaxHeight = 80;
/* 349 */       this.andesiteSize = 33;
/* 350 */       this.andesiteCount = 10;
/* 351 */       this.andesiteMinHeight = 0;
/* 352 */       this.andesiteMaxHeight = 80;
/* 353 */       this.coalSize = 17;
/* 354 */       this.coalCount = 20;
/* 355 */       this.coalMinHeight = 0;
/* 356 */       this.coalMaxHeight = 128;
/* 357 */       this.ironSize = 9;
/* 358 */       this.ironCount = 20;
/* 359 */       this.ironMinHeight = 0;
/* 360 */       this.ironMaxHeight = 64;
/* 361 */       this.goldSize = 9;
/* 362 */       this.goldCount = 2;
/* 363 */       this.goldMinHeight = 0;
/* 364 */       this.goldMaxHeight = 32;
/* 365 */       this.redstoneSize = 8;
/* 366 */       this.redstoneCount = 8;
/* 367 */       this.redstoneMinHeight = 0;
/* 368 */       this.redstoneMaxHeight = 16;
/* 369 */       this.diamondSize = 8;
/* 370 */       this.diamondCount = 1;
/* 371 */       this.diamondMinHeight = 0;
/* 372 */       this.diamondMaxHeight = 16;
/* 373 */       this.lapisSize = 7;
/* 374 */       this.lapisCount = 1;
/* 375 */       this.lapisCenterHeight = 16;
/* 376 */       this.lapisSpread = 16;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean equals(Object p_equals_1_) {
/* 381 */       if (this == p_equals_1_)
/*     */       {
/* 383 */         return true;
/*     */       }
/* 385 */       if (p_equals_1_ != null && getClass() == p_equals_1_.getClass()) {
/*     */         
/* 387 */         Factory chunkgeneratorsettings$factory = (Factory)p_equals_1_;
/*     */         
/* 389 */         if (this.andesiteCount != chunkgeneratorsettings$factory.andesiteCount)
/*     */         {
/* 391 */           return false;
/*     */         }
/* 393 */         if (this.andesiteMaxHeight != chunkgeneratorsettings$factory.andesiteMaxHeight)
/*     */         {
/* 395 */           return false;
/*     */         }
/* 397 */         if (this.andesiteMinHeight != chunkgeneratorsettings$factory.andesiteMinHeight)
/*     */         {
/* 399 */           return false;
/*     */         }
/* 401 */         if (this.andesiteSize != chunkgeneratorsettings$factory.andesiteSize)
/*     */         {
/* 403 */           return false;
/*     */         }
/* 405 */         if (Float.compare(chunkgeneratorsettings$factory.baseSize, this.baseSize) != 0)
/*     */         {
/* 407 */           return false;
/*     */         }
/* 409 */         if (Float.compare(chunkgeneratorsettings$factory.biomeDepthOffset, this.biomeDepthOffset) != 0)
/*     */         {
/* 411 */           return false;
/*     */         }
/* 413 */         if (Float.compare(chunkgeneratorsettings$factory.biomeDepthWeight, this.biomeDepthWeight) != 0)
/*     */         {
/* 415 */           return false;
/*     */         }
/* 417 */         if (Float.compare(chunkgeneratorsettings$factory.biomeScaleOffset, this.biomeScaleOffset) != 0)
/*     */         {
/* 419 */           return false;
/*     */         }
/* 421 */         if (Float.compare(chunkgeneratorsettings$factory.biomeScaleWeight, this.biomeScaleWeight) != 0)
/*     */         {
/* 423 */           return false;
/*     */         }
/* 425 */         if (this.biomeSize != chunkgeneratorsettings$factory.biomeSize)
/*     */         {
/* 427 */           return false;
/*     */         }
/* 429 */         if (this.coalCount != chunkgeneratorsettings$factory.coalCount)
/*     */         {
/* 431 */           return false;
/*     */         }
/* 433 */         if (this.coalMaxHeight != chunkgeneratorsettings$factory.coalMaxHeight)
/*     */         {
/* 435 */           return false;
/*     */         }
/* 437 */         if (this.coalMinHeight != chunkgeneratorsettings$factory.coalMinHeight)
/*     */         {
/* 439 */           return false;
/*     */         }
/* 441 */         if (this.coalSize != chunkgeneratorsettings$factory.coalSize)
/*     */         {
/* 443 */           return false;
/*     */         }
/* 445 */         if (Float.compare(chunkgeneratorsettings$factory.coordinateScale, this.coordinateScale) != 0)
/*     */         {
/* 447 */           return false;
/*     */         }
/* 449 */         if (Float.compare(chunkgeneratorsettings$factory.depthNoiseScaleExponent, this.depthNoiseScaleExponent) != 0)
/*     */         {
/* 451 */           return false;
/*     */         }
/* 453 */         if (Float.compare(chunkgeneratorsettings$factory.depthNoiseScaleX, this.depthNoiseScaleX) != 0)
/*     */         {
/* 455 */           return false;
/*     */         }
/* 457 */         if (Float.compare(chunkgeneratorsettings$factory.depthNoiseScaleZ, this.depthNoiseScaleZ) != 0)
/*     */         {
/* 459 */           return false;
/*     */         }
/* 461 */         if (this.diamondCount != chunkgeneratorsettings$factory.diamondCount)
/*     */         {
/* 463 */           return false;
/*     */         }
/* 465 */         if (this.diamondMaxHeight != chunkgeneratorsettings$factory.diamondMaxHeight)
/*     */         {
/* 467 */           return false;
/*     */         }
/* 469 */         if (this.diamondMinHeight != chunkgeneratorsettings$factory.diamondMinHeight)
/*     */         {
/* 471 */           return false;
/*     */         }
/* 473 */         if (this.diamondSize != chunkgeneratorsettings$factory.diamondSize)
/*     */         {
/* 475 */           return false;
/*     */         }
/* 477 */         if (this.dioriteCount != chunkgeneratorsettings$factory.dioriteCount)
/*     */         {
/* 479 */           return false;
/*     */         }
/* 481 */         if (this.dioriteMaxHeight != chunkgeneratorsettings$factory.dioriteMaxHeight)
/*     */         {
/* 483 */           return false;
/*     */         }
/* 485 */         if (this.dioriteMinHeight != chunkgeneratorsettings$factory.dioriteMinHeight)
/*     */         {
/* 487 */           return false;
/*     */         }
/* 489 */         if (this.dioriteSize != chunkgeneratorsettings$factory.dioriteSize)
/*     */         {
/* 491 */           return false;
/*     */         }
/* 493 */         if (this.dirtCount != chunkgeneratorsettings$factory.dirtCount)
/*     */         {
/* 495 */           return false;
/*     */         }
/* 497 */         if (this.dirtMaxHeight != chunkgeneratorsettings$factory.dirtMaxHeight)
/*     */         {
/* 499 */           return false;
/*     */         }
/* 501 */         if (this.dirtMinHeight != chunkgeneratorsettings$factory.dirtMinHeight)
/*     */         {
/* 503 */           return false;
/*     */         }
/* 505 */         if (this.dirtSize != chunkgeneratorsettings$factory.dirtSize)
/*     */         {
/* 507 */           return false;
/*     */         }
/* 509 */         if (this.dungeonChance != chunkgeneratorsettings$factory.dungeonChance)
/*     */         {
/* 511 */           return false;
/*     */         }
/* 513 */         if (this.fixedBiome != chunkgeneratorsettings$factory.fixedBiome)
/*     */         {
/* 515 */           return false;
/*     */         }
/* 517 */         if (this.goldCount != chunkgeneratorsettings$factory.goldCount)
/*     */         {
/* 519 */           return false;
/*     */         }
/* 521 */         if (this.goldMaxHeight != chunkgeneratorsettings$factory.goldMaxHeight)
/*     */         {
/* 523 */           return false;
/*     */         }
/* 525 */         if (this.goldMinHeight != chunkgeneratorsettings$factory.goldMinHeight)
/*     */         {
/* 527 */           return false;
/*     */         }
/* 529 */         if (this.goldSize != chunkgeneratorsettings$factory.goldSize)
/*     */         {
/* 531 */           return false;
/*     */         }
/* 533 */         if (this.graniteCount != chunkgeneratorsettings$factory.graniteCount)
/*     */         {
/* 535 */           return false;
/*     */         }
/* 537 */         if (this.graniteMaxHeight != chunkgeneratorsettings$factory.graniteMaxHeight)
/*     */         {
/* 539 */           return false;
/*     */         }
/* 541 */         if (this.graniteMinHeight != chunkgeneratorsettings$factory.graniteMinHeight)
/*     */         {
/* 543 */           return false;
/*     */         }
/* 545 */         if (this.graniteSize != chunkgeneratorsettings$factory.graniteSize)
/*     */         {
/* 547 */           return false;
/*     */         }
/* 549 */         if (this.gravelCount != chunkgeneratorsettings$factory.gravelCount)
/*     */         {
/* 551 */           return false;
/*     */         }
/* 553 */         if (this.gravelMaxHeight != chunkgeneratorsettings$factory.gravelMaxHeight)
/*     */         {
/* 555 */           return false;
/*     */         }
/* 557 */         if (this.gravelMinHeight != chunkgeneratorsettings$factory.gravelMinHeight)
/*     */         {
/* 559 */           return false;
/*     */         }
/* 561 */         if (this.gravelSize != chunkgeneratorsettings$factory.gravelSize)
/*     */         {
/* 563 */           return false;
/*     */         }
/* 565 */         if (Float.compare(chunkgeneratorsettings$factory.heightScale, this.heightScale) != 0)
/*     */         {
/* 567 */           return false;
/*     */         }
/* 569 */         if (this.ironCount != chunkgeneratorsettings$factory.ironCount)
/*     */         {
/* 571 */           return false;
/*     */         }
/* 573 */         if (this.ironMaxHeight != chunkgeneratorsettings$factory.ironMaxHeight)
/*     */         {
/* 575 */           return false;
/*     */         }
/* 577 */         if (this.ironMinHeight != chunkgeneratorsettings$factory.ironMinHeight)
/*     */         {
/* 579 */           return false;
/*     */         }
/* 581 */         if (this.ironSize != chunkgeneratorsettings$factory.ironSize)
/*     */         {
/* 583 */           return false;
/*     */         }
/* 585 */         if (this.lapisCenterHeight != chunkgeneratorsettings$factory.lapisCenterHeight)
/*     */         {
/* 587 */           return false;
/*     */         }
/* 589 */         if (this.lapisCount != chunkgeneratorsettings$factory.lapisCount)
/*     */         {
/* 591 */           return false;
/*     */         }
/* 593 */         if (this.lapisSize != chunkgeneratorsettings$factory.lapisSize)
/*     */         {
/* 595 */           return false;
/*     */         }
/* 597 */         if (this.lapisSpread != chunkgeneratorsettings$factory.lapisSpread)
/*     */         {
/* 599 */           return false;
/*     */         }
/* 601 */         if (this.lavaLakeChance != chunkgeneratorsettings$factory.lavaLakeChance)
/*     */         {
/* 603 */           return false;
/*     */         }
/* 605 */         if (Float.compare(chunkgeneratorsettings$factory.lowerLimitScale, this.lowerLimitScale) != 0)
/*     */         {
/* 607 */           return false;
/*     */         }
/* 609 */         if (Float.compare(chunkgeneratorsettings$factory.mainNoiseScaleX, this.mainNoiseScaleX) != 0)
/*     */         {
/* 611 */           return false;
/*     */         }
/* 613 */         if (Float.compare(chunkgeneratorsettings$factory.mainNoiseScaleY, this.mainNoiseScaleY) != 0)
/*     */         {
/* 615 */           return false;
/*     */         }
/* 617 */         if (Float.compare(chunkgeneratorsettings$factory.mainNoiseScaleZ, this.mainNoiseScaleZ) != 0)
/*     */         {
/* 619 */           return false;
/*     */         }
/* 621 */         if (this.redstoneCount != chunkgeneratorsettings$factory.redstoneCount)
/*     */         {
/* 623 */           return false;
/*     */         }
/* 625 */         if (this.redstoneMaxHeight != chunkgeneratorsettings$factory.redstoneMaxHeight)
/*     */         {
/* 627 */           return false;
/*     */         }
/* 629 */         if (this.redstoneMinHeight != chunkgeneratorsettings$factory.redstoneMinHeight)
/*     */         {
/* 631 */           return false;
/*     */         }
/* 633 */         if (this.redstoneSize != chunkgeneratorsettings$factory.redstoneSize)
/*     */         {
/* 635 */           return false;
/*     */         }
/* 637 */         if (this.riverSize != chunkgeneratorsettings$factory.riverSize)
/*     */         {
/* 639 */           return false;
/*     */         }
/* 641 */         if (this.seaLevel != chunkgeneratorsettings$factory.seaLevel)
/*     */         {
/* 643 */           return false;
/*     */         }
/* 645 */         if (Float.compare(chunkgeneratorsettings$factory.stretchY, this.stretchY) != 0)
/*     */         {
/* 647 */           return false;
/*     */         }
/* 649 */         if (Float.compare(chunkgeneratorsettings$factory.upperLimitScale, this.upperLimitScale) != 0)
/*     */         {
/* 651 */           return false;
/*     */         }
/* 653 */         if (this.useCaves != chunkgeneratorsettings$factory.useCaves)
/*     */         {
/* 655 */           return false;
/*     */         }
/* 657 */         if (this.useDungeons != chunkgeneratorsettings$factory.useDungeons)
/*     */         {
/* 659 */           return false;
/*     */         }
/* 661 */         if (this.useLavaLakes != chunkgeneratorsettings$factory.useLavaLakes)
/*     */         {
/* 663 */           return false;
/*     */         }
/* 665 */         if (this.useLavaOceans != chunkgeneratorsettings$factory.useLavaOceans)
/*     */         {
/* 667 */           return false;
/*     */         }
/* 669 */         if (this.useMineShafts != chunkgeneratorsettings$factory.useMineShafts)
/*     */         {
/* 671 */           return false;
/*     */         }
/* 673 */         if (this.useRavines != chunkgeneratorsettings$factory.useRavines)
/*     */         {
/* 675 */           return false;
/*     */         }
/* 677 */         if (this.useStrongholds != chunkgeneratorsettings$factory.useStrongholds)
/*     */         {
/* 679 */           return false;
/*     */         }
/* 681 */         if (this.useTemples != chunkgeneratorsettings$factory.useTemples)
/*     */         {
/* 683 */           return false;
/*     */         }
/* 685 */         if (this.useMonuments != chunkgeneratorsettings$factory.useMonuments)
/*     */         {
/* 687 */           return false;
/*     */         }
/* 689 */         if (this.field_191076_A != chunkgeneratorsettings$factory.field_191076_A)
/*     */         {
/* 691 */           return false;
/*     */         }
/* 693 */         if (this.useVillages != chunkgeneratorsettings$factory.useVillages)
/*     */         {
/* 695 */           return false;
/*     */         }
/* 697 */         if (this.useWaterLakes != chunkgeneratorsettings$factory.useWaterLakes)
/*     */         {
/* 699 */           return false;
/*     */         }
/*     */ 
/*     */         
/* 703 */         return (this.waterLakeChance == chunkgeneratorsettings$factory.waterLakeChance);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 708 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public int hashCode() {
/* 714 */       int i = (this.coordinateScale == 0.0F) ? 0 : Float.floatToIntBits(this.coordinateScale);
/* 715 */       i = 31 * i + ((this.heightScale == 0.0F) ? 0 : Float.floatToIntBits(this.heightScale));
/* 716 */       i = 31 * i + ((this.upperLimitScale == 0.0F) ? 0 : Float.floatToIntBits(this.upperLimitScale));
/* 717 */       i = 31 * i + ((this.lowerLimitScale == 0.0F) ? 0 : Float.floatToIntBits(this.lowerLimitScale));
/* 718 */       i = 31 * i + ((this.depthNoiseScaleX == 0.0F) ? 0 : Float.floatToIntBits(this.depthNoiseScaleX));
/* 719 */       i = 31 * i + ((this.depthNoiseScaleZ == 0.0F) ? 0 : Float.floatToIntBits(this.depthNoiseScaleZ));
/* 720 */       i = 31 * i + ((this.depthNoiseScaleExponent == 0.0F) ? 0 : Float.floatToIntBits(this.depthNoiseScaleExponent));
/* 721 */       i = 31 * i + ((this.mainNoiseScaleX == 0.0F) ? 0 : Float.floatToIntBits(this.mainNoiseScaleX));
/* 722 */       i = 31 * i + ((this.mainNoiseScaleY == 0.0F) ? 0 : Float.floatToIntBits(this.mainNoiseScaleY));
/* 723 */       i = 31 * i + ((this.mainNoiseScaleZ == 0.0F) ? 0 : Float.floatToIntBits(this.mainNoiseScaleZ));
/* 724 */       i = 31 * i + ((this.baseSize == 0.0F) ? 0 : Float.floatToIntBits(this.baseSize));
/* 725 */       i = 31 * i + ((this.stretchY == 0.0F) ? 0 : Float.floatToIntBits(this.stretchY));
/* 726 */       i = 31 * i + ((this.biomeDepthWeight == 0.0F) ? 0 : Float.floatToIntBits(this.biomeDepthWeight));
/* 727 */       i = 31 * i + ((this.biomeDepthOffset == 0.0F) ? 0 : Float.floatToIntBits(this.biomeDepthOffset));
/* 728 */       i = 31 * i + ((this.biomeScaleWeight == 0.0F) ? 0 : Float.floatToIntBits(this.biomeScaleWeight));
/* 729 */       i = 31 * i + ((this.biomeScaleOffset == 0.0F) ? 0 : Float.floatToIntBits(this.biomeScaleOffset));
/* 730 */       i = 31 * i + this.seaLevel;
/* 731 */       i = 31 * i + (this.useCaves ? 1 : 0);
/* 732 */       i = 31 * i + (this.useDungeons ? 1 : 0);
/* 733 */       i = 31 * i + this.dungeonChance;
/* 734 */       i = 31 * i + (this.useStrongholds ? 1 : 0);
/* 735 */       i = 31 * i + (this.useVillages ? 1 : 0);
/* 736 */       i = 31 * i + (this.useMineShafts ? 1 : 0);
/* 737 */       i = 31 * i + (this.useTemples ? 1 : 0);
/* 738 */       i = 31 * i + (this.useMonuments ? 1 : 0);
/* 739 */       i = 31 * i + (this.field_191076_A ? 1 : 0);
/* 740 */       i = 31 * i + (this.useRavines ? 1 : 0);
/* 741 */       i = 31 * i + (this.useWaterLakes ? 1 : 0);
/* 742 */       i = 31 * i + this.waterLakeChance;
/* 743 */       i = 31 * i + (this.useLavaLakes ? 1 : 0);
/* 744 */       i = 31 * i + this.lavaLakeChance;
/* 745 */       i = 31 * i + (this.useLavaOceans ? 1 : 0);
/* 746 */       i = 31 * i + this.fixedBiome;
/* 747 */       i = 31 * i + this.biomeSize;
/* 748 */       i = 31 * i + this.riverSize;
/* 749 */       i = 31 * i + this.dirtSize;
/* 750 */       i = 31 * i + this.dirtCount;
/* 751 */       i = 31 * i + this.dirtMinHeight;
/* 752 */       i = 31 * i + this.dirtMaxHeight;
/* 753 */       i = 31 * i + this.gravelSize;
/* 754 */       i = 31 * i + this.gravelCount;
/* 755 */       i = 31 * i + this.gravelMinHeight;
/* 756 */       i = 31 * i + this.gravelMaxHeight;
/* 757 */       i = 31 * i + this.graniteSize;
/* 758 */       i = 31 * i + this.graniteCount;
/* 759 */       i = 31 * i + this.graniteMinHeight;
/* 760 */       i = 31 * i + this.graniteMaxHeight;
/* 761 */       i = 31 * i + this.dioriteSize;
/* 762 */       i = 31 * i + this.dioriteCount;
/* 763 */       i = 31 * i + this.dioriteMinHeight;
/* 764 */       i = 31 * i + this.dioriteMaxHeight;
/* 765 */       i = 31 * i + this.andesiteSize;
/* 766 */       i = 31 * i + this.andesiteCount;
/* 767 */       i = 31 * i + this.andesiteMinHeight;
/* 768 */       i = 31 * i + this.andesiteMaxHeight;
/* 769 */       i = 31 * i + this.coalSize;
/* 770 */       i = 31 * i + this.coalCount;
/* 771 */       i = 31 * i + this.coalMinHeight;
/* 772 */       i = 31 * i + this.coalMaxHeight;
/* 773 */       i = 31 * i + this.ironSize;
/* 774 */       i = 31 * i + this.ironCount;
/* 775 */       i = 31 * i + this.ironMinHeight;
/* 776 */       i = 31 * i + this.ironMaxHeight;
/* 777 */       i = 31 * i + this.goldSize;
/* 778 */       i = 31 * i + this.goldCount;
/* 779 */       i = 31 * i + this.goldMinHeight;
/* 780 */       i = 31 * i + this.goldMaxHeight;
/* 781 */       i = 31 * i + this.redstoneSize;
/* 782 */       i = 31 * i + this.redstoneCount;
/* 783 */       i = 31 * i + this.redstoneMinHeight;
/* 784 */       i = 31 * i + this.redstoneMaxHeight;
/* 785 */       i = 31 * i + this.diamondSize;
/* 786 */       i = 31 * i + this.diamondCount;
/* 787 */       i = 31 * i + this.diamondMinHeight;
/* 788 */       i = 31 * i + this.diamondMaxHeight;
/* 789 */       i = 31 * i + this.lapisSize;
/* 790 */       i = 31 * i + this.lapisCount;
/* 791 */       i = 31 * i + this.lapisCenterHeight;
/* 792 */       i = 31 * i + this.lapisSpread;
/* 793 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     public ChunkGeneratorSettings build() {
/* 798 */       return new ChunkGeneratorSettings(this, null);
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Serializer
/*     */     implements JsonDeserializer<Factory>, JsonSerializer<Factory>
/*     */   {
/*     */     public ChunkGeneratorSettings.Factory deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) throws JsonParseException {
/* 806 */       JsonObject jsonobject = p_deserialize_1_.getAsJsonObject();
/* 807 */       ChunkGeneratorSettings.Factory chunkgeneratorsettings$factory = new ChunkGeneratorSettings.Factory();
/*     */ 
/*     */       
/*     */       try {
/* 811 */         chunkgeneratorsettings$factory.coordinateScale = JsonUtils.getFloat(jsonobject, "coordinateScale", chunkgeneratorsettings$factory.coordinateScale);
/* 812 */         chunkgeneratorsettings$factory.heightScale = JsonUtils.getFloat(jsonobject, "heightScale", chunkgeneratorsettings$factory.heightScale);
/* 813 */         chunkgeneratorsettings$factory.lowerLimitScale = JsonUtils.getFloat(jsonobject, "lowerLimitScale", chunkgeneratorsettings$factory.lowerLimitScale);
/* 814 */         chunkgeneratorsettings$factory.upperLimitScale = JsonUtils.getFloat(jsonobject, "upperLimitScale", chunkgeneratorsettings$factory.upperLimitScale);
/* 815 */         chunkgeneratorsettings$factory.depthNoiseScaleX = JsonUtils.getFloat(jsonobject, "depthNoiseScaleX", chunkgeneratorsettings$factory.depthNoiseScaleX);
/* 816 */         chunkgeneratorsettings$factory.depthNoiseScaleZ = JsonUtils.getFloat(jsonobject, "depthNoiseScaleZ", chunkgeneratorsettings$factory.depthNoiseScaleZ);
/* 817 */         chunkgeneratorsettings$factory.depthNoiseScaleExponent = JsonUtils.getFloat(jsonobject, "depthNoiseScaleExponent", chunkgeneratorsettings$factory.depthNoiseScaleExponent);
/* 818 */         chunkgeneratorsettings$factory.mainNoiseScaleX = JsonUtils.getFloat(jsonobject, "mainNoiseScaleX", chunkgeneratorsettings$factory.mainNoiseScaleX);
/* 819 */         chunkgeneratorsettings$factory.mainNoiseScaleY = JsonUtils.getFloat(jsonobject, "mainNoiseScaleY", chunkgeneratorsettings$factory.mainNoiseScaleY);
/* 820 */         chunkgeneratorsettings$factory.mainNoiseScaleZ = JsonUtils.getFloat(jsonobject, "mainNoiseScaleZ", chunkgeneratorsettings$factory.mainNoiseScaleZ);
/* 821 */         chunkgeneratorsettings$factory.baseSize = JsonUtils.getFloat(jsonobject, "baseSize", chunkgeneratorsettings$factory.baseSize);
/* 822 */         chunkgeneratorsettings$factory.stretchY = JsonUtils.getFloat(jsonobject, "stretchY", chunkgeneratorsettings$factory.stretchY);
/* 823 */         chunkgeneratorsettings$factory.biomeDepthWeight = JsonUtils.getFloat(jsonobject, "biomeDepthWeight", chunkgeneratorsettings$factory.biomeDepthWeight);
/* 824 */         chunkgeneratorsettings$factory.biomeDepthOffset = JsonUtils.getFloat(jsonobject, "biomeDepthOffset", chunkgeneratorsettings$factory.biomeDepthOffset);
/* 825 */         chunkgeneratorsettings$factory.biomeScaleWeight = JsonUtils.getFloat(jsonobject, "biomeScaleWeight", chunkgeneratorsettings$factory.biomeScaleWeight);
/* 826 */         chunkgeneratorsettings$factory.biomeScaleOffset = JsonUtils.getFloat(jsonobject, "biomeScaleOffset", chunkgeneratorsettings$factory.biomeScaleOffset);
/* 827 */         chunkgeneratorsettings$factory.seaLevel = JsonUtils.getInt(jsonobject, "seaLevel", chunkgeneratorsettings$factory.seaLevel);
/* 828 */         chunkgeneratorsettings$factory.useCaves = JsonUtils.getBoolean(jsonobject, "useCaves", chunkgeneratorsettings$factory.useCaves);
/* 829 */         chunkgeneratorsettings$factory.useDungeons = JsonUtils.getBoolean(jsonobject, "useDungeons", chunkgeneratorsettings$factory.useDungeons);
/* 830 */         chunkgeneratorsettings$factory.dungeonChance = JsonUtils.getInt(jsonobject, "dungeonChance", chunkgeneratorsettings$factory.dungeonChance);
/* 831 */         chunkgeneratorsettings$factory.useStrongholds = JsonUtils.getBoolean(jsonobject, "useStrongholds", chunkgeneratorsettings$factory.useStrongholds);
/* 832 */         chunkgeneratorsettings$factory.useVillages = JsonUtils.getBoolean(jsonobject, "useVillages", chunkgeneratorsettings$factory.useVillages);
/* 833 */         chunkgeneratorsettings$factory.useMineShafts = JsonUtils.getBoolean(jsonobject, "useMineShafts", chunkgeneratorsettings$factory.useMineShafts);
/* 834 */         chunkgeneratorsettings$factory.useTemples = JsonUtils.getBoolean(jsonobject, "useTemples", chunkgeneratorsettings$factory.useTemples);
/* 835 */         chunkgeneratorsettings$factory.useMonuments = JsonUtils.getBoolean(jsonobject, "useMonuments", chunkgeneratorsettings$factory.useMonuments);
/* 836 */         chunkgeneratorsettings$factory.field_191076_A = JsonUtils.getBoolean(jsonobject, "useMansions", chunkgeneratorsettings$factory.field_191076_A);
/* 837 */         chunkgeneratorsettings$factory.useRavines = JsonUtils.getBoolean(jsonobject, "useRavines", chunkgeneratorsettings$factory.useRavines);
/* 838 */         chunkgeneratorsettings$factory.useWaterLakes = JsonUtils.getBoolean(jsonobject, "useWaterLakes", chunkgeneratorsettings$factory.useWaterLakes);
/* 839 */         chunkgeneratorsettings$factory.waterLakeChance = JsonUtils.getInt(jsonobject, "waterLakeChance", chunkgeneratorsettings$factory.waterLakeChance);
/* 840 */         chunkgeneratorsettings$factory.useLavaLakes = JsonUtils.getBoolean(jsonobject, "useLavaLakes", chunkgeneratorsettings$factory.useLavaLakes);
/* 841 */         chunkgeneratorsettings$factory.lavaLakeChance = JsonUtils.getInt(jsonobject, "lavaLakeChance", chunkgeneratorsettings$factory.lavaLakeChance);
/* 842 */         chunkgeneratorsettings$factory.useLavaOceans = JsonUtils.getBoolean(jsonobject, "useLavaOceans", chunkgeneratorsettings$factory.useLavaOceans);
/* 843 */         chunkgeneratorsettings$factory.fixedBiome = JsonUtils.getInt(jsonobject, "fixedBiome", chunkgeneratorsettings$factory.fixedBiome);
/*     */         
/* 845 */         if (chunkgeneratorsettings$factory.fixedBiome < 38 && chunkgeneratorsettings$factory.fixedBiome >= -1) {
/*     */           
/* 847 */           if (chunkgeneratorsettings$factory.fixedBiome >= Biome.getIdForBiome(Biomes.HELL))
/*     */           {
/* 849 */             chunkgeneratorsettings$factory.fixedBiome += 2;
/*     */           }
/*     */         }
/*     */         else {
/*     */           
/* 854 */           chunkgeneratorsettings$factory.fixedBiome = -1;
/*     */         } 
/*     */         
/* 857 */         chunkgeneratorsettings$factory.biomeSize = JsonUtils.getInt(jsonobject, "biomeSize", chunkgeneratorsettings$factory.biomeSize);
/* 858 */         chunkgeneratorsettings$factory.riverSize = JsonUtils.getInt(jsonobject, "riverSize", chunkgeneratorsettings$factory.riverSize);
/* 859 */         chunkgeneratorsettings$factory.dirtSize = JsonUtils.getInt(jsonobject, "dirtSize", chunkgeneratorsettings$factory.dirtSize);
/* 860 */         chunkgeneratorsettings$factory.dirtCount = JsonUtils.getInt(jsonobject, "dirtCount", chunkgeneratorsettings$factory.dirtCount);
/* 861 */         chunkgeneratorsettings$factory.dirtMinHeight = JsonUtils.getInt(jsonobject, "dirtMinHeight", chunkgeneratorsettings$factory.dirtMinHeight);
/* 862 */         chunkgeneratorsettings$factory.dirtMaxHeight = JsonUtils.getInt(jsonobject, "dirtMaxHeight", chunkgeneratorsettings$factory.dirtMaxHeight);
/* 863 */         chunkgeneratorsettings$factory.gravelSize = JsonUtils.getInt(jsonobject, "gravelSize", chunkgeneratorsettings$factory.gravelSize);
/* 864 */         chunkgeneratorsettings$factory.gravelCount = JsonUtils.getInt(jsonobject, "gravelCount", chunkgeneratorsettings$factory.gravelCount);
/* 865 */         chunkgeneratorsettings$factory.gravelMinHeight = JsonUtils.getInt(jsonobject, "gravelMinHeight", chunkgeneratorsettings$factory.gravelMinHeight);
/* 866 */         chunkgeneratorsettings$factory.gravelMaxHeight = JsonUtils.getInt(jsonobject, "gravelMaxHeight", chunkgeneratorsettings$factory.gravelMaxHeight);
/* 867 */         chunkgeneratorsettings$factory.graniteSize = JsonUtils.getInt(jsonobject, "graniteSize", chunkgeneratorsettings$factory.graniteSize);
/* 868 */         chunkgeneratorsettings$factory.graniteCount = JsonUtils.getInt(jsonobject, "graniteCount", chunkgeneratorsettings$factory.graniteCount);
/* 869 */         chunkgeneratorsettings$factory.graniteMinHeight = JsonUtils.getInt(jsonobject, "graniteMinHeight", chunkgeneratorsettings$factory.graniteMinHeight);
/* 870 */         chunkgeneratorsettings$factory.graniteMaxHeight = JsonUtils.getInt(jsonobject, "graniteMaxHeight", chunkgeneratorsettings$factory.graniteMaxHeight);
/* 871 */         chunkgeneratorsettings$factory.dioriteSize = JsonUtils.getInt(jsonobject, "dioriteSize", chunkgeneratorsettings$factory.dioriteSize);
/* 872 */         chunkgeneratorsettings$factory.dioriteCount = JsonUtils.getInt(jsonobject, "dioriteCount", chunkgeneratorsettings$factory.dioriteCount);
/* 873 */         chunkgeneratorsettings$factory.dioriteMinHeight = JsonUtils.getInt(jsonobject, "dioriteMinHeight", chunkgeneratorsettings$factory.dioriteMinHeight);
/* 874 */         chunkgeneratorsettings$factory.dioriteMaxHeight = JsonUtils.getInt(jsonobject, "dioriteMaxHeight", chunkgeneratorsettings$factory.dioriteMaxHeight);
/* 875 */         chunkgeneratorsettings$factory.andesiteSize = JsonUtils.getInt(jsonobject, "andesiteSize", chunkgeneratorsettings$factory.andesiteSize);
/* 876 */         chunkgeneratorsettings$factory.andesiteCount = JsonUtils.getInt(jsonobject, "andesiteCount", chunkgeneratorsettings$factory.andesiteCount);
/* 877 */         chunkgeneratorsettings$factory.andesiteMinHeight = JsonUtils.getInt(jsonobject, "andesiteMinHeight", chunkgeneratorsettings$factory.andesiteMinHeight);
/* 878 */         chunkgeneratorsettings$factory.andesiteMaxHeight = JsonUtils.getInt(jsonobject, "andesiteMaxHeight", chunkgeneratorsettings$factory.andesiteMaxHeight);
/* 879 */         chunkgeneratorsettings$factory.coalSize = JsonUtils.getInt(jsonobject, "coalSize", chunkgeneratorsettings$factory.coalSize);
/* 880 */         chunkgeneratorsettings$factory.coalCount = JsonUtils.getInt(jsonobject, "coalCount", chunkgeneratorsettings$factory.coalCount);
/* 881 */         chunkgeneratorsettings$factory.coalMinHeight = JsonUtils.getInt(jsonobject, "coalMinHeight", chunkgeneratorsettings$factory.coalMinHeight);
/* 882 */         chunkgeneratorsettings$factory.coalMaxHeight = JsonUtils.getInt(jsonobject, "coalMaxHeight", chunkgeneratorsettings$factory.coalMaxHeight);
/* 883 */         chunkgeneratorsettings$factory.ironSize = JsonUtils.getInt(jsonobject, "ironSize", chunkgeneratorsettings$factory.ironSize);
/* 884 */         chunkgeneratorsettings$factory.ironCount = JsonUtils.getInt(jsonobject, "ironCount", chunkgeneratorsettings$factory.ironCount);
/* 885 */         chunkgeneratorsettings$factory.ironMinHeight = JsonUtils.getInt(jsonobject, "ironMinHeight", chunkgeneratorsettings$factory.ironMinHeight);
/* 886 */         chunkgeneratorsettings$factory.ironMaxHeight = JsonUtils.getInt(jsonobject, "ironMaxHeight", chunkgeneratorsettings$factory.ironMaxHeight);
/* 887 */         chunkgeneratorsettings$factory.goldSize = JsonUtils.getInt(jsonobject, "goldSize", chunkgeneratorsettings$factory.goldSize);
/* 888 */         chunkgeneratorsettings$factory.goldCount = JsonUtils.getInt(jsonobject, "goldCount", chunkgeneratorsettings$factory.goldCount);
/* 889 */         chunkgeneratorsettings$factory.goldMinHeight = JsonUtils.getInt(jsonobject, "goldMinHeight", chunkgeneratorsettings$factory.goldMinHeight);
/* 890 */         chunkgeneratorsettings$factory.goldMaxHeight = JsonUtils.getInt(jsonobject, "goldMaxHeight", chunkgeneratorsettings$factory.goldMaxHeight);
/* 891 */         chunkgeneratorsettings$factory.redstoneSize = JsonUtils.getInt(jsonobject, "redstoneSize", chunkgeneratorsettings$factory.redstoneSize);
/* 892 */         chunkgeneratorsettings$factory.redstoneCount = JsonUtils.getInt(jsonobject, "redstoneCount", chunkgeneratorsettings$factory.redstoneCount);
/* 893 */         chunkgeneratorsettings$factory.redstoneMinHeight = JsonUtils.getInt(jsonobject, "redstoneMinHeight", chunkgeneratorsettings$factory.redstoneMinHeight);
/* 894 */         chunkgeneratorsettings$factory.redstoneMaxHeight = JsonUtils.getInt(jsonobject, "redstoneMaxHeight", chunkgeneratorsettings$factory.redstoneMaxHeight);
/* 895 */         chunkgeneratorsettings$factory.diamondSize = JsonUtils.getInt(jsonobject, "diamondSize", chunkgeneratorsettings$factory.diamondSize);
/* 896 */         chunkgeneratorsettings$factory.diamondCount = JsonUtils.getInt(jsonobject, "diamondCount", chunkgeneratorsettings$factory.diamondCount);
/* 897 */         chunkgeneratorsettings$factory.diamondMinHeight = JsonUtils.getInt(jsonobject, "diamondMinHeight", chunkgeneratorsettings$factory.diamondMinHeight);
/* 898 */         chunkgeneratorsettings$factory.diamondMaxHeight = JsonUtils.getInt(jsonobject, "diamondMaxHeight", chunkgeneratorsettings$factory.diamondMaxHeight);
/* 899 */         chunkgeneratorsettings$factory.lapisSize = JsonUtils.getInt(jsonobject, "lapisSize", chunkgeneratorsettings$factory.lapisSize);
/* 900 */         chunkgeneratorsettings$factory.lapisCount = JsonUtils.getInt(jsonobject, "lapisCount", chunkgeneratorsettings$factory.lapisCount);
/* 901 */         chunkgeneratorsettings$factory.lapisCenterHeight = JsonUtils.getInt(jsonobject, "lapisCenterHeight", chunkgeneratorsettings$factory.lapisCenterHeight);
/* 902 */         chunkgeneratorsettings$factory.lapisSpread = JsonUtils.getInt(jsonobject, "lapisSpread", chunkgeneratorsettings$factory.lapisSpread);
/*     */       }
/* 904 */       catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 909 */       return chunkgeneratorsettings$factory;
/*     */     }
/*     */ 
/*     */     
/*     */     public JsonElement serialize(ChunkGeneratorSettings.Factory p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_) {
/* 914 */       JsonObject jsonobject = new JsonObject();
/* 915 */       jsonobject.addProperty("coordinateScale", Float.valueOf(p_serialize_1_.coordinateScale));
/* 916 */       jsonobject.addProperty("heightScale", Float.valueOf(p_serialize_1_.heightScale));
/* 917 */       jsonobject.addProperty("lowerLimitScale", Float.valueOf(p_serialize_1_.lowerLimitScale));
/* 918 */       jsonobject.addProperty("upperLimitScale", Float.valueOf(p_serialize_1_.upperLimitScale));
/* 919 */       jsonobject.addProperty("depthNoiseScaleX", Float.valueOf(p_serialize_1_.depthNoiseScaleX));
/* 920 */       jsonobject.addProperty("depthNoiseScaleZ", Float.valueOf(p_serialize_1_.depthNoiseScaleZ));
/* 921 */       jsonobject.addProperty("depthNoiseScaleExponent", Float.valueOf(p_serialize_1_.depthNoiseScaleExponent));
/* 922 */       jsonobject.addProperty("mainNoiseScaleX", Float.valueOf(p_serialize_1_.mainNoiseScaleX));
/* 923 */       jsonobject.addProperty("mainNoiseScaleY", Float.valueOf(p_serialize_1_.mainNoiseScaleY));
/* 924 */       jsonobject.addProperty("mainNoiseScaleZ", Float.valueOf(p_serialize_1_.mainNoiseScaleZ));
/* 925 */       jsonobject.addProperty("baseSize", Float.valueOf(p_serialize_1_.baseSize));
/* 926 */       jsonobject.addProperty("stretchY", Float.valueOf(p_serialize_1_.stretchY));
/* 927 */       jsonobject.addProperty("biomeDepthWeight", Float.valueOf(p_serialize_1_.biomeDepthWeight));
/* 928 */       jsonobject.addProperty("biomeDepthOffset", Float.valueOf(p_serialize_1_.biomeDepthOffset));
/* 929 */       jsonobject.addProperty("biomeScaleWeight", Float.valueOf(p_serialize_1_.biomeScaleWeight));
/* 930 */       jsonobject.addProperty("biomeScaleOffset", Float.valueOf(p_serialize_1_.biomeScaleOffset));
/* 931 */       jsonobject.addProperty("seaLevel", Integer.valueOf(p_serialize_1_.seaLevel));
/* 932 */       jsonobject.addProperty("useCaves", Boolean.valueOf(p_serialize_1_.useCaves));
/* 933 */       jsonobject.addProperty("useDungeons", Boolean.valueOf(p_serialize_1_.useDungeons));
/* 934 */       jsonobject.addProperty("dungeonChance", Integer.valueOf(p_serialize_1_.dungeonChance));
/* 935 */       jsonobject.addProperty("useStrongholds", Boolean.valueOf(p_serialize_1_.useStrongholds));
/* 936 */       jsonobject.addProperty("useVillages", Boolean.valueOf(p_serialize_1_.useVillages));
/* 937 */       jsonobject.addProperty("useMineShafts", Boolean.valueOf(p_serialize_1_.useMineShafts));
/* 938 */       jsonobject.addProperty("useTemples", Boolean.valueOf(p_serialize_1_.useTemples));
/* 939 */       jsonobject.addProperty("useMonuments", Boolean.valueOf(p_serialize_1_.useMonuments));
/* 940 */       jsonobject.addProperty("useMansions", Boolean.valueOf(p_serialize_1_.field_191076_A));
/* 941 */       jsonobject.addProperty("useRavines", Boolean.valueOf(p_serialize_1_.useRavines));
/* 942 */       jsonobject.addProperty("useWaterLakes", Boolean.valueOf(p_serialize_1_.useWaterLakes));
/* 943 */       jsonobject.addProperty("waterLakeChance", Integer.valueOf(p_serialize_1_.waterLakeChance));
/* 944 */       jsonobject.addProperty("useLavaLakes", Boolean.valueOf(p_serialize_1_.useLavaLakes));
/* 945 */       jsonobject.addProperty("lavaLakeChance", Integer.valueOf(p_serialize_1_.lavaLakeChance));
/* 946 */       jsonobject.addProperty("useLavaOceans", Boolean.valueOf(p_serialize_1_.useLavaOceans));
/* 947 */       jsonobject.addProperty("fixedBiome", Integer.valueOf(p_serialize_1_.fixedBiome));
/* 948 */       jsonobject.addProperty("biomeSize", Integer.valueOf(p_serialize_1_.biomeSize));
/* 949 */       jsonobject.addProperty("riverSize", Integer.valueOf(p_serialize_1_.riverSize));
/* 950 */       jsonobject.addProperty("dirtSize", Integer.valueOf(p_serialize_1_.dirtSize));
/* 951 */       jsonobject.addProperty("dirtCount", Integer.valueOf(p_serialize_1_.dirtCount));
/* 952 */       jsonobject.addProperty("dirtMinHeight", Integer.valueOf(p_serialize_1_.dirtMinHeight));
/* 953 */       jsonobject.addProperty("dirtMaxHeight", Integer.valueOf(p_serialize_1_.dirtMaxHeight));
/* 954 */       jsonobject.addProperty("gravelSize", Integer.valueOf(p_serialize_1_.gravelSize));
/* 955 */       jsonobject.addProperty("gravelCount", Integer.valueOf(p_serialize_1_.gravelCount));
/* 956 */       jsonobject.addProperty("gravelMinHeight", Integer.valueOf(p_serialize_1_.gravelMinHeight));
/* 957 */       jsonobject.addProperty("gravelMaxHeight", Integer.valueOf(p_serialize_1_.gravelMaxHeight));
/* 958 */       jsonobject.addProperty("graniteSize", Integer.valueOf(p_serialize_1_.graniteSize));
/* 959 */       jsonobject.addProperty("graniteCount", Integer.valueOf(p_serialize_1_.graniteCount));
/* 960 */       jsonobject.addProperty("graniteMinHeight", Integer.valueOf(p_serialize_1_.graniteMinHeight));
/* 961 */       jsonobject.addProperty("graniteMaxHeight", Integer.valueOf(p_serialize_1_.graniteMaxHeight));
/* 962 */       jsonobject.addProperty("dioriteSize", Integer.valueOf(p_serialize_1_.dioriteSize));
/* 963 */       jsonobject.addProperty("dioriteCount", Integer.valueOf(p_serialize_1_.dioriteCount));
/* 964 */       jsonobject.addProperty("dioriteMinHeight", Integer.valueOf(p_serialize_1_.dioriteMinHeight));
/* 965 */       jsonobject.addProperty("dioriteMaxHeight", Integer.valueOf(p_serialize_1_.dioriteMaxHeight));
/* 966 */       jsonobject.addProperty("andesiteSize", Integer.valueOf(p_serialize_1_.andesiteSize));
/* 967 */       jsonobject.addProperty("andesiteCount", Integer.valueOf(p_serialize_1_.andesiteCount));
/* 968 */       jsonobject.addProperty("andesiteMinHeight", Integer.valueOf(p_serialize_1_.andesiteMinHeight));
/* 969 */       jsonobject.addProperty("andesiteMaxHeight", Integer.valueOf(p_serialize_1_.andesiteMaxHeight));
/* 970 */       jsonobject.addProperty("coalSize", Integer.valueOf(p_serialize_1_.coalSize));
/* 971 */       jsonobject.addProperty("coalCount", Integer.valueOf(p_serialize_1_.coalCount));
/* 972 */       jsonobject.addProperty("coalMinHeight", Integer.valueOf(p_serialize_1_.coalMinHeight));
/* 973 */       jsonobject.addProperty("coalMaxHeight", Integer.valueOf(p_serialize_1_.coalMaxHeight));
/* 974 */       jsonobject.addProperty("ironSize", Integer.valueOf(p_serialize_1_.ironSize));
/* 975 */       jsonobject.addProperty("ironCount", Integer.valueOf(p_serialize_1_.ironCount));
/* 976 */       jsonobject.addProperty("ironMinHeight", Integer.valueOf(p_serialize_1_.ironMinHeight));
/* 977 */       jsonobject.addProperty("ironMaxHeight", Integer.valueOf(p_serialize_1_.ironMaxHeight));
/* 978 */       jsonobject.addProperty("goldSize", Integer.valueOf(p_serialize_1_.goldSize));
/* 979 */       jsonobject.addProperty("goldCount", Integer.valueOf(p_serialize_1_.goldCount));
/* 980 */       jsonobject.addProperty("goldMinHeight", Integer.valueOf(p_serialize_1_.goldMinHeight));
/* 981 */       jsonobject.addProperty("goldMaxHeight", Integer.valueOf(p_serialize_1_.goldMaxHeight));
/* 982 */       jsonobject.addProperty("redstoneSize", Integer.valueOf(p_serialize_1_.redstoneSize));
/* 983 */       jsonobject.addProperty("redstoneCount", Integer.valueOf(p_serialize_1_.redstoneCount));
/* 984 */       jsonobject.addProperty("redstoneMinHeight", Integer.valueOf(p_serialize_1_.redstoneMinHeight));
/* 985 */       jsonobject.addProperty("redstoneMaxHeight", Integer.valueOf(p_serialize_1_.redstoneMaxHeight));
/* 986 */       jsonobject.addProperty("diamondSize", Integer.valueOf(p_serialize_1_.diamondSize));
/* 987 */       jsonobject.addProperty("diamondCount", Integer.valueOf(p_serialize_1_.diamondCount));
/* 988 */       jsonobject.addProperty("diamondMinHeight", Integer.valueOf(p_serialize_1_.diamondMinHeight));
/* 989 */       jsonobject.addProperty("diamondMaxHeight", Integer.valueOf(p_serialize_1_.diamondMaxHeight));
/* 990 */       jsonobject.addProperty("lapisSize", Integer.valueOf(p_serialize_1_.lapisSize));
/* 991 */       jsonobject.addProperty("lapisCount", Integer.valueOf(p_serialize_1_.lapisCount));
/* 992 */       jsonobject.addProperty("lapisCenterHeight", Integer.valueOf(p_serialize_1_.lapisCenterHeight));
/* 993 */       jsonobject.addProperty("lapisSpread", Integer.valueOf(p_serialize_1_.lapisSpread));
/* 994 */       return (JsonElement)jsonobject;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\ChunkGeneratorSettings.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */