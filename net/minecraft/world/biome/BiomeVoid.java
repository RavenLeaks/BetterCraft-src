/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ public class BiomeVoid
/*    */   extends Biome
/*    */ {
/*    */   public BiomeVoid(Biome.BiomeProperties properties) {
/*  7 */     super(properties);
/*  8 */     this.spawnableMonsterList.clear();
/*  9 */     this.spawnableCreatureList.clear();
/* 10 */     this.spawnableWaterCreatureList.clear();
/* 11 */     this.spawnableCaveCreatureList.clear();
/* 12 */     this.theBiomeDecorator = new BiomeVoidDecorator();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean ignorePlayerSpawnSuitability() {
/* 17 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeVoid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */