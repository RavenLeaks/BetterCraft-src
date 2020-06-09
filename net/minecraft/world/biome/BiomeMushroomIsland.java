/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.entity.passive.EntityMooshroom;
/*    */ import net.minecraft.init.Blocks;
/*    */ 
/*    */ public class BiomeMushroomIsland
/*    */   extends Biome
/*    */ {
/*    */   public BiomeMushroomIsland(Biome.BiomeProperties properties) {
/* 10 */     super(properties);
/* 11 */     this.theBiomeDecorator.treesPerChunk = -100;
/* 12 */     this.theBiomeDecorator.flowersPerChunk = -100;
/* 13 */     this.theBiomeDecorator.grassPerChunk = -100;
/* 14 */     this.theBiomeDecorator.mushroomsPerChunk = 1;
/* 15 */     this.theBiomeDecorator.bigMushroomsPerChunk = 1;
/* 16 */     this.topBlock = Blocks.MYCELIUM.getDefaultState();
/* 17 */     this.spawnableMonsterList.clear();
/* 18 */     this.spawnableCreatureList.clear();
/* 19 */     this.spawnableWaterCreatureList.clear();
/* 20 */     this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityMooshroom.class, 8, 4, 8));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeMushroomIsland.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */