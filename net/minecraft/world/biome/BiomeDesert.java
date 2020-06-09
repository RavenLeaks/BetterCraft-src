/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.monster.EntityHusk;
/*    */ import net.minecraft.entity.monster.EntityZombie;
/*    */ import net.minecraft.entity.monster.EntityZombieVillager;
/*    */ import net.minecraft.entity.passive.EntityRabbit;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.gen.feature.WorldGenDesertWells;
/*    */ import net.minecraft.world.gen.feature.WorldGenFossils;
/*    */ 
/*    */ public class BiomeDesert
/*    */   extends Biome
/*    */ {
/*    */   public BiomeDesert(Biome.BiomeProperties properties) {
/* 19 */     super(properties);
/* 20 */     this.spawnableCreatureList.clear();
/* 21 */     this.topBlock = Blocks.SAND.getDefaultState();
/* 22 */     this.fillerBlock = Blocks.SAND.getDefaultState();
/* 23 */     this.theBiomeDecorator.treesPerChunk = -999;
/* 24 */     this.theBiomeDecorator.deadBushPerChunk = 2;
/* 25 */     this.theBiomeDecorator.reedsPerChunk = 50;
/* 26 */     this.theBiomeDecorator.cactiPerChunk = 10;
/* 27 */     this.spawnableCreatureList.clear();
/* 28 */     this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityRabbit.class, 4, 2, 3));
/* 29 */     Iterator<Biome.SpawnListEntry> iterator = this.spawnableMonsterList.iterator();
/*    */     
/* 31 */     while (iterator.hasNext()) {
/*    */       
/* 33 */       Biome.SpawnListEntry biome$spawnlistentry = iterator.next();
/*    */       
/* 35 */       if (biome$spawnlistentry.entityClass == EntityZombie.class || biome$spawnlistentry.entityClass == EntityZombieVillager.class)
/*    */       {
/* 37 */         iterator.remove();
/*    */       }
/*    */     } 
/*    */     
/* 41 */     this.spawnableMonsterList.add(new Biome.SpawnListEntry((Class)EntityZombie.class, 19, 4, 4));
/* 42 */     this.spawnableMonsterList.add(new Biome.SpawnListEntry((Class)EntityZombieVillager.class, 1, 1, 1));
/* 43 */     this.spawnableMonsterList.add(new Biome.SpawnListEntry((Class)EntityHusk.class, 80, 4, 4));
/*    */   }
/*    */ 
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 48 */     super.decorate(worldIn, rand, pos);
/*    */     
/* 50 */     if (rand.nextInt(1000) == 0) {
/*    */       
/* 52 */       int i = rand.nextInt(16) + 8;
/* 53 */       int j = rand.nextInt(16) + 8;
/* 54 */       BlockPos blockpos = worldIn.getHeight(pos.add(i, 0, j)).up();
/* 55 */       (new WorldGenDesertWells()).generate(worldIn, rand, blockpos);
/*    */     } 
/*    */     
/* 58 */     if (rand.nextInt(64) == 0)
/*    */     {
/* 60 */       (new WorldGenFossils()).generate(worldIn, rand, pos);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeDesert.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */