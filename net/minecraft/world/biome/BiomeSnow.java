/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Random;
/*    */ import net.minecraft.entity.monster.EntityPolarBear;
/*    */ import net.minecraft.entity.monster.EntitySkeleton;
/*    */ import net.minecraft.entity.monster.EntityStray;
/*    */ import net.minecraft.entity.passive.EntityRabbit;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*    */ import net.minecraft.world.gen.feature.WorldGenIcePath;
/*    */ import net.minecraft.world.gen.feature.WorldGenIceSpike;
/*    */ import net.minecraft.world.gen.feature.WorldGenTaiga2;
/*    */ 
/*    */ public class BiomeSnow
/*    */   extends Biome {
/*    */   private final boolean superIcy;
/* 20 */   private final WorldGenIceSpike iceSpike = new WorldGenIceSpike();
/* 21 */   private final WorldGenIcePath icePatch = new WorldGenIcePath(4);
/*    */ 
/*    */   
/*    */   public BiomeSnow(boolean superIcyIn, Biome.BiomeProperties properties) {
/* 25 */     super(properties);
/* 26 */     this.superIcy = superIcyIn;
/*    */     
/* 28 */     if (superIcyIn)
/*    */     {
/* 30 */       this.topBlock = Blocks.SNOW.getDefaultState();
/*    */     }
/*    */     
/* 33 */     this.spawnableCreatureList.clear();
/* 34 */     this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityRabbit.class, 10, 2, 3));
/* 35 */     this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityPolarBear.class, 1, 1, 2));
/* 36 */     Iterator<Biome.SpawnListEntry> iterator = this.spawnableMonsterList.iterator();
/*    */     
/* 38 */     while (iterator.hasNext()) {
/*    */       
/* 40 */       Biome.SpawnListEntry biome$spawnlistentry = iterator.next();
/*    */       
/* 42 */       if (biome$spawnlistentry.entityClass == EntitySkeleton.class)
/*    */       {
/* 44 */         iterator.remove();
/*    */       }
/*    */     } 
/*    */     
/* 48 */     this.spawnableMonsterList.add(new Biome.SpawnListEntry((Class)EntitySkeleton.class, 20, 4, 4));
/* 49 */     this.spawnableMonsterList.add(new Biome.SpawnListEntry((Class)EntityStray.class, 80, 4, 4));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float getSpawningChance() {
/* 57 */     return 0.07F;
/*    */   }
/*    */ 
/*    */   
/*    */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/* 62 */     if (this.superIcy) {
/*    */       
/* 64 */       for (int i = 0; i < 3; i++) {
/*    */         
/* 66 */         int j = rand.nextInt(16) + 8;
/* 67 */         int k = rand.nextInt(16) + 8;
/* 68 */         this.iceSpike.generate(worldIn, rand, worldIn.getHeight(pos.add(j, 0, k)));
/*    */       } 
/*    */       
/* 71 */       for (int l = 0; l < 2; l++) {
/*    */         
/* 73 */         int i1 = rand.nextInt(16) + 8;
/* 74 */         int j1 = rand.nextInt(16) + 8;
/* 75 */         this.icePatch.generate(worldIn, rand, worldIn.getHeight(pos.add(i1, 0, j1)));
/*    */       } 
/*    */     } 
/*    */     
/* 79 */     super.decorate(worldIn, rand, pos);
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 84 */     return (WorldGenAbstractTree)new WorldGenTaiga2(false);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */