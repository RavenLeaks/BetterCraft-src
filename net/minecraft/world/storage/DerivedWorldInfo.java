/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.DimensionType;
/*     */ import net.minecraft.world.EnumDifficulty;
/*     */ import net.minecraft.world.GameRules;
/*     */ import net.minecraft.world.GameType;
/*     */ import net.minecraft.world.WorldType;
/*     */ 
/*     */ 
/*     */ public class DerivedWorldInfo
/*     */   extends WorldInfo
/*     */ {
/*     */   private final WorldInfo theWorldInfo;
/*     */   
/*     */   public DerivedWorldInfo(WorldInfo worldInfoIn) {
/*  19 */     this.theWorldInfo = worldInfoIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound cloneNBTCompound(@Nullable NBTTagCompound nbt) {
/*  27 */     return this.theWorldInfo.cloneNBTCompound(nbt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getSeed() {
/*  35 */     return this.theWorldInfo.getSeed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpawnX() {
/*  43 */     return this.theWorldInfo.getSpawnX();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpawnY() {
/*  51 */     return this.theWorldInfo.getSpawnY();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSpawnZ() {
/*  59 */     return this.theWorldInfo.getSpawnZ();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getWorldTotalTime() {
/*  64 */     return this.theWorldInfo.getWorldTotalTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getWorldTime() {
/*  72 */     return this.theWorldInfo.getWorldTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getSizeOnDisk() {
/*  77 */     return this.theWorldInfo.getSizeOnDisk();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getPlayerNBTTagCompound() {
/*  85 */     return this.theWorldInfo.getPlayerNBTTagCompound();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getWorldName() {
/*  93 */     return this.theWorldInfo.getWorldName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSaveVersion() {
/* 101 */     return this.theWorldInfo.getSaveVersion();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLastTimePlayed() {
/* 109 */     return this.theWorldInfo.getLastTimePlayed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isThundering() {
/* 117 */     return this.theWorldInfo.isThundering();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getThunderTime() {
/* 125 */     return this.theWorldInfo.getThunderTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRaining() {
/* 133 */     return this.theWorldInfo.isRaining();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRainTime() {
/* 141 */     return this.theWorldInfo.getRainTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameType getGameType() {
/* 149 */     return this.theWorldInfo.getGameType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnX(int x) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnY(int y) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawnZ(int z) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldTotalTime(long time) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldTime(long time) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSpawn(BlockPos spawnPoint) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setWorldName(String worldName) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSaveVersion(int version) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThundering(boolean thunderingIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setThunderTime(int time) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRaining(boolean isRaining) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setRainTime(int time) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMapFeaturesEnabled() {
/* 232 */     return this.theWorldInfo.isMapFeaturesEnabled();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHardcoreModeEnabled() {
/* 240 */     return this.theWorldInfo.isHardcoreModeEnabled();
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldType getTerrainType() {
/* 245 */     return this.theWorldInfo.getTerrainType();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTerrainType(WorldType type) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean areCommandsAllowed() {
/* 257 */     return this.theWorldInfo.areCommandsAllowed();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAllowCommands(boolean allow) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInitialized() {
/* 269 */     return this.theWorldInfo.isInitialized();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setServerInitialized(boolean initializedIn) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GameRules getGameRulesInstance() {
/* 284 */     return this.theWorldInfo.getGameRulesInstance();
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumDifficulty getDifficulty() {
/* 289 */     return this.theWorldInfo.getDifficulty();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDifficulty(EnumDifficulty newDifficulty) {}
/*     */ 
/*     */   
/*     */   public boolean isDifficultyLocked() {
/* 298 */     return this.theWorldInfo.isDifficultyLocked();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDifficultyLocked(boolean locked) {}
/*     */ 
/*     */   
/*     */   public void setDimensionData(DimensionType dimensionIn, NBTTagCompound compound) {
/* 307 */     this.theWorldInfo.setDimensionData(dimensionIn, compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public NBTTagCompound getDimensionData(DimensionType dimensionIn) {
/* 312 */     return this.theWorldInfo.getDimensionData(dimensionIn);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\DerivedWorldInfo.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */