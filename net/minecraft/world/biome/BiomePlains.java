/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.entity.passive.EntityDonkey;
/*     */ import net.minecraft.entity.passive.EntityHorse;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ 
/*     */ public class BiomePlains
/*     */   extends Biome
/*     */ {
/*     */   protected boolean sunflowers;
/*     */   
/*     */   protected BiomePlains(boolean p_i46699_1_, Biome.BiomeProperties properties) {
/*  18 */     super(properties);
/*  19 */     this.sunflowers = p_i46699_1_;
/*  20 */     this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityHorse.class, 5, 2, 6));
/*  21 */     this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityDonkey.class, 1, 1, 3));
/*  22 */     this.theBiomeDecorator.treesPerChunk = 0;
/*  23 */     this.theBiomeDecorator.extraTreeChance = 0.05F;
/*  24 */     this.theBiomeDecorator.flowersPerChunk = 4;
/*  25 */     this.theBiomeDecorator.grassPerChunk = 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
/*  30 */     double d0 = GRASS_COLOR_NOISE.getValue(pos.getX() / 200.0D, pos.getZ() / 200.0D);
/*     */     
/*  32 */     if (d0 < -0.8D) {
/*     */       
/*  34 */       int j = rand.nextInt(4);
/*     */       
/*  36 */       switch (j) {
/*     */         
/*     */         case 0:
/*  39 */           return BlockFlower.EnumFlowerType.ORANGE_TULIP;
/*     */         
/*     */         case 1:
/*  42 */           return BlockFlower.EnumFlowerType.RED_TULIP;
/*     */         
/*     */         case 2:
/*  45 */           return BlockFlower.EnumFlowerType.PINK_TULIP;
/*     */       } 
/*     */ 
/*     */       
/*  49 */       return BlockFlower.EnumFlowerType.WHITE_TULIP;
/*     */     } 
/*     */     
/*  52 */     if (rand.nextInt(3) > 0) {
/*     */       
/*  54 */       int i = rand.nextInt(3);
/*     */       
/*  56 */       if (i == 0)
/*     */       {
/*  58 */         return BlockFlower.EnumFlowerType.POPPY;
/*     */       }
/*     */ 
/*     */       
/*  62 */       return (i == 1) ? BlockFlower.EnumFlowerType.HOUSTONIA : BlockFlower.EnumFlowerType.OXEYE_DAISY;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  67 */     return BlockFlower.EnumFlowerType.DANDELION;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/*  73 */     double d0 = GRASS_COLOR_NOISE.getValue((pos.getX() + 8) / 200.0D, (pos.getZ() + 8) / 200.0D);
/*     */     
/*  75 */     if (d0 < -0.8D) {
/*     */       
/*  77 */       this.theBiomeDecorator.flowersPerChunk = 15;
/*  78 */       this.theBiomeDecorator.grassPerChunk = 5;
/*     */     }
/*     */     else {
/*     */       
/*  82 */       this.theBiomeDecorator.flowersPerChunk = 4;
/*  83 */       this.theBiomeDecorator.grassPerChunk = 10;
/*  84 */       DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.GRASS);
/*     */       
/*  86 */       for (int i = 0; i < 7; i++) {
/*     */         
/*  88 */         int j = rand.nextInt(16) + 8;
/*  89 */         int k = rand.nextInt(16) + 8;
/*  90 */         int l = rand.nextInt(worldIn.getHeight(pos.add(j, 0, k)).getY() + 32);
/*  91 */         DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j, l, k));
/*     */       } 
/*     */     } 
/*     */     
/*  95 */     if (this.sunflowers) {
/*     */       
/*  97 */       DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SUNFLOWER);
/*     */       
/*  99 */       for (int i1 = 0; i1 < 10; i1++) {
/*     */         
/* 101 */         int j1 = rand.nextInt(16) + 8;
/* 102 */         int k1 = rand.nextInt(16) + 8;
/* 103 */         int l1 = rand.nextInt(worldIn.getHeight(pos.add(j1, 0, k1)).getY() + 32);
/* 104 */         DOUBLE_PLANT_GENERATOR.generate(worldIn, rand, pos.add(j1, l1, k1));
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     super.decorate(worldIn, rand, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/* 113 */     return (rand.nextInt(3) == 0) ? (WorldGenAbstractTree)BIG_TREE_FEATURE : (WorldGenAbstractTree)TREE_FEATURE;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomePlains.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */