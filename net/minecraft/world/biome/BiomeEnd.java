/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class BiomeEnd
/*    */   extends Biome
/*    */ {
/*    */   public BiomeEnd(Biome.BiomeProperties properties) {
/* 10 */     super(properties);
/* 11 */     this.spawnableMonsterList.clear();
/* 12 */     this.spawnableCreatureList.clear();
/* 13 */     this.spawnableWaterCreatureList.clear();
/* 14 */     this.spawnableCaveCreatureList.clear();
/* 15 */     this.spawnableMonsterList.add(new Biome.SpawnListEntry((Class)EntityEnderman.class, 10, 4, 4));
/* 16 */     this.topBlock = Blocks.DIRT.getDefaultState();
/* 17 */     this.fillerBlock = Blocks.DIRT.getDefaultState();
/* 18 */     this.theBiomeDecorator = new BiomeEndDecorator();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getSkyColorByTemp(float currentTemperature) {
/* 26 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeEnd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */