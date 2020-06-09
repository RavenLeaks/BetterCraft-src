/*     */ package net.minecraft.world;
/*     */ 
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.biome.BiomeProvider;
/*     */ import net.minecraft.world.biome.BiomeProviderSingle;
/*     */ import net.minecraft.world.border.WorldBorder;
/*     */ import net.minecraft.world.gen.ChunkGeneratorHell;
/*     */ import net.minecraft.world.gen.IChunkGenerator;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WorldProviderHell
/*     */   extends WorldProvider
/*     */ {
/*     */   public void createBiomeProvider() {
/*  17 */     this.biomeProvider = (BiomeProvider)new BiomeProviderSingle(Biomes.HELL);
/*  18 */     this.isHellWorld = true;
/*  19 */     this.hasNoSky = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vec3d getFogColor(float p_76562_1_, float p_76562_2_) {
/*  27 */     return new Vec3d(0.20000000298023224D, 0.029999999329447746D, 0.029999999329447746D);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void generateLightBrightnessTable() {
/*  35 */     float f = 0.1F;
/*     */     
/*  37 */     for (int i = 0; i <= 15; i++) {
/*     */       
/*  39 */       float f1 = 1.0F - i / 15.0F;
/*  40 */       this.lightBrightnessTable[i] = (1.0F - f1) / (f1 * 3.0F + 1.0F) * 0.9F + 0.1F;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IChunkGenerator createChunkGenerator() {
/*  46 */     return (IChunkGenerator)new ChunkGeneratorHell(this.worldObj, this.worldObj.getWorldInfo().isMapFeaturesEnabled(), this.worldObj.getSeed());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSurfaceWorld() {
/*  54 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canCoordinateBeSpawn(int x, int z) {
/*  62 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float calculateCelestialAngle(long worldTime, float partialTicks) {
/*  70 */     return 0.5F;
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
/*     */   public boolean doesXZShowFog(int x, int z) {
/*  86 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldBorder createWorldBorder() {
/*  91 */     return new WorldBorder()
/*     */       {
/*     */         public double getCenterX()
/*     */         {
/*  95 */           return super.getCenterX() / 8.0D;
/*     */         }
/*     */         
/*     */         public double getCenterZ() {
/*  99 */           return super.getCenterZ() / 8.0D;
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public DimensionType getDimensionType() {
/* 106 */     return DimensionType.NETHER;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\WorldProviderHell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */