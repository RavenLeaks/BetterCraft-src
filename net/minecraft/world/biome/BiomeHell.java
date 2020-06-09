/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import net.minecraft.entity.monster.EntityEnderman;
/*    */ import net.minecraft.entity.monster.EntityGhast;
/*    */ import net.minecraft.entity.monster.EntityMagmaCube;
/*    */ import net.minecraft.entity.monster.EntityPigZombie;
/*    */ 
/*    */ public class BiomeHell
/*    */   extends Biome
/*    */ {
/*    */   public BiomeHell(Biome.BiomeProperties properties) {
/* 12 */     super(properties);
/* 13 */     this.spawnableMonsterList.clear();
/* 14 */     this.spawnableCreatureList.clear();
/* 15 */     this.spawnableWaterCreatureList.clear();
/* 16 */     this.spawnableCaveCreatureList.clear();
/* 17 */     this.spawnableMonsterList.add(new Biome.SpawnListEntry((Class)EntityGhast.class, 50, 4, 4));
/* 18 */     this.spawnableMonsterList.add(new Biome.SpawnListEntry((Class)EntityPigZombie.class, 100, 4, 4));
/* 19 */     this.spawnableMonsterList.add(new Biome.SpawnListEntry((Class)EntityMagmaCube.class, 2, 4, 4));
/* 20 */     this.spawnableMonsterList.add(new Biome.SpawnListEntry((Class)EntityEnderman.class, 1, 4, 4));
/* 21 */     this.theBiomeDecorator = new BiomeHellDecorator();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeHell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */