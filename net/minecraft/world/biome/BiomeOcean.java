/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ public class BiomeOcean
/*    */   extends Biome
/*    */ {
/*    */   public BiomeOcean(Biome.BiomeProperties properties) {
/*  7 */     super(properties);
/*  8 */     this.spawnableCreatureList.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public Biome.TempCategory getTempCategory() {
/* 13 */     return Biome.TempCategory.OCEAN;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeOcean.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */