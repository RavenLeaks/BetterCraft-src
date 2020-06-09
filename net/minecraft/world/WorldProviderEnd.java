/*     */ package net.minecraft.world;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.biome.BiomeProvider;
/*     */ import net.minecraft.world.biome.BiomeProviderSingle;
/*     */ import net.minecraft.world.end.DragonFightManager;
/*     */ import net.minecraft.world.gen.ChunkGeneratorEnd;
/*     */ import net.minecraft.world.gen.IChunkGenerator;
/*     */ 
/*     */ 
/*     */ public class WorldProviderEnd
/*     */   extends WorldProvider
/*     */ {
/*     */   private DragonFightManager dragonFightManager;
/*     */   
/*     */   public void createBiomeProvider() {
/*  23 */     this.biomeProvider = (BiomeProvider)new BiomeProviderSingle(Biomes.SKY);
/*  24 */     NBTTagCompound nbttagcompound = this.worldObj.getWorldInfo().getDimensionData(DimensionType.THE_END);
/*  25 */     this.dragonFightManager = (this.worldObj instanceof WorldServer) ? new DragonFightManager((WorldServer)this.worldObj, nbttagcompound.getCompoundTag("DragonFight")) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public IChunkGenerator createChunkGenerator() {
/*  30 */     return (IChunkGenerator)new ChunkGeneratorEnd(this.worldObj, this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.worldObj.getSeed(), getSpawnCoordinate());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float calculateCelestialAngle(long worldTime, float partialTicks) {
/*  38 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public float[] calcSunriseSunsetColors(float celestialAngle, float partialTicks) {
/*  48 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
/*  56 */     int i = 10518688;
/*  57 */     float f = MathHelper.cos(p_76562_1_ * 6.2831855F) * 2.0F + 0.5F;
/*  58 */     f = MathHelper.clamp(f, 0.0F, 1.0F);
/*  59 */     float f1 = 0.627451F;
/*  60 */     float f2 = 0.5019608F;
/*  61 */     float f3 = 0.627451F;
/*  62 */     f1 *= f * 0.0F + 0.15F;
/*  63 */     f2 *= f * 0.0F + 0.15F;
/*  64 */     f3 *= f * 0.0F + 0.15F;
/*  65 */     return new Vec3d(f1, f2, f3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSkyColored() {
/*  70 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canRespawnHere() {
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSurfaceWorld() {
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getCloudHeight() {
/*  94 */     return 8.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCoordinateBeSpawn(int x, int z) {
/* 102 */     return this.worldObj.getGroundAboveSeaLevel(new BlockPos(x, 0, z)).getMaterial().blocksMovement();
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getSpawnCoordinate() {
/* 107 */     return new BlockPos(100, 50, 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAverageGroundLevel() {
/* 112 */     return 50;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesXZShowFog(int x, int z) {
/* 120 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public DimensionType getDimensionType() {
/* 125 */     return DimensionType.THE_END;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onWorldSave() {
/* 134 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 136 */     if (this.dragonFightManager != null)
/*     */     {
/* 138 */       nbttagcompound.setTag("DragonFight", (NBTBase)this.dragonFightManager.getCompound());
/*     */     }
/*     */     
/* 141 */     this.worldObj.getWorldInfo().setDimensionData(DimensionType.THE_END, nbttagcompound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onWorldUpdateEntities() {
/* 150 */     if (this.dragonFightManager != null)
/*     */     {
/* 152 */       this.dragonFightManager.tick();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public DragonFightManager getDragonFightManager() {
/* 159 */     return this.dragonFightManager;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\WorldProviderEnd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */