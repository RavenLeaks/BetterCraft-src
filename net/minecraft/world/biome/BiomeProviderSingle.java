/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ 
/*    */ 
/*    */ public class BiomeProviderSingle
/*    */   extends BiomeProvider
/*    */ {
/*    */   private final Biome biome;
/*    */   
/*    */   public BiomeProviderSingle(Biome biomeIn) {
/* 16 */     this.biome = biomeIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Biome getBiome(BlockPos pos) {
/* 24 */     return this.biome;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Biome[] getBiomesForGeneration(Biome[] biomes, int x, int z, int width, int height) {
/* 32 */     if (biomes == null || biomes.length < width * height)
/*    */     {
/* 34 */       biomes = new Biome[width * height];
/*    */     }
/*    */     
/* 37 */     Arrays.fill((Object[])biomes, 0, width * height, this.biome);
/* 38 */     return biomes;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Biome[] getBiomes(@Nullable Biome[] oldBiomeList, int x, int z, int width, int depth) {
/* 47 */     if (oldBiomeList == null || oldBiomeList.length < width * depth)
/*    */     {
/* 49 */       oldBiomeList = new Biome[width * depth];
/*    */     }
/*    */     
/* 52 */     Arrays.fill((Object[])oldBiomeList, 0, width * depth, this.biome);
/* 53 */     return oldBiomeList;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Biome[] getBiomes(@Nullable Biome[] listToReuse, int x, int z, int width, int length, boolean cacheFlag) {
/* 61 */     return getBiomes(listToReuse, x, z, width, length);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public BlockPos findBiomePosition(int x, int z, int range, List<Biome> biomes, Random random) {
/* 67 */     return biomes.contains(this.biome) ? new BlockPos(x - range + random.nextInt(range * 2 + 1), 0, z - range + random.nextInt(range * 2 + 1)) : null;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean areBiomesViable(int x, int z, int radius, List<Biome> allowed) {
/* 75 */     return allowed.contains(this.biome);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean func_190944_c() {
/* 80 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public Biome func_190943_d() {
/* 85 */     return this.biome;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeProviderSingle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */