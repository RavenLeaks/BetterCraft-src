/*     */ package net.minecraft.world;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.biome.BiomeProvider;
/*     */ import net.minecraft.world.biome.BiomeProviderSingle;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ import net.minecraft.world.gen.ChunkGeneratorDebug;
/*     */ import net.minecraft.world.gen.ChunkGeneratorFlat;
/*     */ import net.minecraft.world.gen.ChunkGeneratorOverworld;
/*     */ import net.minecraft.world.gen.FlatGeneratorInfo;
/*     */ import net.minecraft.world.gen.IChunkGenerator;
/*     */ 
/*     */ public abstract class WorldProvider
/*     */ {
/*  22 */   public static final float[] MOON_PHASE_FACTORS = new float[] { 1.0F, 0.75F, 0.5F, 0.25F, 0.0F, 0.25F, 0.5F, 0.75F };
/*     */ 
/*     */   
/*     */   protected World worldObj;
/*     */ 
/*     */   
/*     */   private WorldType terrainType;
/*     */ 
/*     */   
/*     */   private String generatorSettings;
/*     */ 
/*     */   
/*     */   protected BiomeProvider biomeProvider;
/*     */ 
/*     */   
/*     */   protected boolean isHellWorld;
/*     */ 
/*     */   
/*     */   protected boolean hasNoSky;
/*     */   
/*     */   protected boolean field_191067_f;
/*     */   
/*  44 */   protected final float[] lightBrightnessTable = new float[16];
/*     */ 
/*     */   
/*  47 */   private final float[] colorsSunriseSunset = new float[4];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void registerWorld(World worldIn) {
/*  54 */     this.worldObj = worldIn;
/*  55 */     this.terrainType = worldIn.getWorldInfo().getTerrainType();
/*  56 */     this.generatorSettings = worldIn.getWorldInfo().getGeneratorOptions();
/*  57 */     createBiomeProvider();
/*  58 */     generateLightBrightnessTable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void generateLightBrightnessTable() {
/*  66 */     float f = 0.0F;
/*     */     
/*  68 */     for (int i = 0; i <= 15; i++) {
/*     */       
/*  70 */       float f1 = 1.0F - i / 15.0F;
/*  71 */       this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 1.0F + 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createBiomeProvider() {
/*  80 */     this.field_191067_f = true;
/*  81 */     WorldType worldtype = this.worldObj.getWorldInfo().getTerrainType();
/*     */     
/*  83 */     if (worldtype == WorldType.FLAT) {
/*     */       
/*  85 */       FlatGeneratorInfo flatgeneratorinfo = FlatGeneratorInfo.createFlatGeneratorFromString(this.worldObj.getWorldInfo().getGeneratorOptions());
/*  86 */       this.biomeProvider = (BiomeProvider)new BiomeProviderSingle(Biome.getBiome(flatgeneratorinfo.getBiome(), Biomes.DEFAULT));
/*     */     }
/*  88 */     else if (worldtype == WorldType.DEBUG_WORLD) {
/*     */       
/*  90 */       this.biomeProvider = (BiomeProvider)new BiomeProviderSingle(Biomes.PLAINS);
/*     */     }
/*     */     else {
/*     */       
/*  94 */       this.biomeProvider = new BiomeProvider(this.worldObj.getWorldInfo());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IChunkGenerator createChunkGenerator() {
/* 100 */     if (this.terrainType == WorldType.FLAT)
/*     */     {
/* 102 */       return (IChunkGenerator)new ChunkGeneratorFlat(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings);
/*     */     }
/* 104 */     if (this.terrainType == WorldType.DEBUG_WORLD)
/*     */     {
/* 106 */       return (IChunkGenerator)new ChunkGeneratorDebug(this.worldObj);
/*     */     }
/*     */ 
/*     */     
/* 110 */     return (this.terrainType == WorldType.CUSTOMIZED) ? (IChunkGenerator)new ChunkGeneratorOverworld(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings) : (IChunkGenerator)new ChunkGeneratorOverworld(this.worldObj, this.worldObj.getSeed(), this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.generatorSettings);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCoordinateBeSpawn(int x, int z) {
/* 119 */     BlockPos blockpos = new BlockPos(x, 0, z);
/*     */     
/* 121 */     if (this.worldObj.getBiome(blockpos).ignorePlayerSpawnSuitability())
/*     */     {
/* 123 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 127 */     return (this.worldObj.getGroundAboveSeaLevel(blockpos).getBlock() == Blocks.GRASS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float calculateCelestialAngle(long worldTime, float partialTicks) {
/* 136 */     int i = (int)(worldTime % 24000L);
/* 137 */     float f = (i + partialTicks) / 24000.0F - 0.25F;
/*     */     
/* 139 */     if (f < 0.0F)
/*     */     {
/* 141 */       f++;
/*     */     }
/*     */     
/* 144 */     if (f > 1.0F)
/*     */     {
/* 146 */       f--;
/*     */     }
/*     */     
/* 149 */     float f1 = 1.0F - (float)((Math.cos(f * Math.PI) + 1.0D) / 2.0D);
/* 150 */     f += (f1 - f) / 3.0F;
/* 151 */     return f;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMoonPhase(long worldTime) {
/* 156 */     return (int)(worldTime / 24000L % 8L + 8L) % 8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSurfaceWorld() {
/* 164 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
/* 174 */     float f = 0.4F;
/* 175 */     float f1 = MathHelper.cos(celestialAngle * 6.2831855F) - 0.0F;
/* 176 */     float f2 = -0.0F;
/*     */     
/* 178 */     if (f1 >= -0.4F && f1 <= 0.4F) {
/*     */       
/* 180 */       float f3 = (f1 - -0.0F) / 0.4F * 0.5F + 0.5F;
/* 181 */       float f4 = 1.0F - (1.0F - MathHelper.sin(f3 * 3.1415927F)) * 0.99F;
/* 182 */       f4 *= f4;
/* 183 */       this.colorsSunriseSunset[0] = f3 * 0.3F + 0.7F;
/* 184 */       this.colorsSunriseSunset[1] = f3 * f3 * 0.7F + 0.2F;
/* 185 */       this.colorsSunriseSunset[2] = f3 * f3 * 0.0F + 0.2F;
/* 186 */       this.colorsSunriseSunset[3] = f4;
/* 187 */       return this.colorsSunriseSunset;
/*     */     } 
/*     */ 
/*     */     
/* 191 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
/* 200 */     float f = MathHelper.cos(p_76562_1_ * 6.2831855F) * 2.0F + 0.5F;
/* 201 */     f = MathHelper.clamp(f, 0.0F, 1.0F);
/* 202 */     float f1 = 0.7529412F;
/* 203 */     float f2 = 0.84705883F;
/* 204 */     float f3 = 1.0F;
/* 205 */     f1 *= f * 0.94F + 0.06F;
/* 206 */     f2 *= f * 0.94F + 0.06F;
/* 207 */     f3 *= f * 0.91F + 0.09F;
/* 208 */     return new Vec3d(f1, f2, f3);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRespawnHere() {
/* 216 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCloudHeight() {
/* 224 */     return 128.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSkyColored() {
/* 229 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockPos getSpawnCoordinate() {
/* 235 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAverageGroundLevel() {
/* 240 */     return (this.terrainType == WorldType.FLAT) ? 4 : (this.worldObj.getSeaLevel() + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getVoidFogYFactor() {
/* 250 */     return (this.terrainType == WorldType.FLAT) ? 1.0D : 0.03125D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesXZShowFog(int x, int z) {
/* 258 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BiomeProvider getBiomeProvider() {
/* 263 */     return this.biomeProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesWaterVaporize() {
/* 268 */     return this.isHellWorld;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_191066_m() {
/* 273 */     return this.field_191067_f;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getHasNoSky() {
/* 278 */     return this.hasNoSky;
/*     */   }
/*     */ 
/*     */   
/*     */   public float[] getLightBrightnessTable() {
/* 283 */     return this.lightBrightnessTable;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldBorder createWorldBorder() {
/* 288 */     return new WorldBorder();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerAdded(EntityPlayerMP player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onPlayerRemoved(EntityPlayerMP player) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract DimensionType getDimensionType();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onWorldSave() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onWorldUpdateEntities() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canDropChunk(int x, int z) {
/* 329 */     return true;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\WorldProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */