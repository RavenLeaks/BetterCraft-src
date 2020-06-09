/*     */ package net.minecraft.world.biome;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.BlockDoublePlant;
/*     */ import net.minecraft.block.BlockFlower;
/*     */ import net.minecraft.entity.passive.EntityRabbit;
/*     */ import net.minecraft.entity.passive.EntityWolf;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenAbstractTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigMushroom;
/*     */ import net.minecraft.world.gen.feature.WorldGenBirchTree;
/*     */ import net.minecraft.world.gen.feature.WorldGenCanopyTree;
/*     */ 
/*     */ public class BiomeForest
/*     */   extends Biome {
/*  18 */   protected static final WorldGenBirchTree SUPER_BIRCH_TREE = new WorldGenBirchTree(false, true);
/*  19 */   protected static final WorldGenBirchTree BIRCH_TREE = new WorldGenBirchTree(false, false);
/*  20 */   protected static final WorldGenCanopyTree ROOF_TREE = new WorldGenCanopyTree(false);
/*     */   
/*     */   private final Type type;
/*     */   
/*     */   public BiomeForest(Type typeIn, Biome.BiomeProperties properties) {
/*  25 */     super(properties);
/*  26 */     this.type = typeIn;
/*  27 */     this.theBiomeDecorator.treesPerChunk = 10;
/*  28 */     this.theBiomeDecorator.grassPerChunk = 2;
/*     */     
/*  30 */     if (this.type == Type.FLOWER) {
/*     */       
/*  32 */       this.theBiomeDecorator.treesPerChunk = 6;
/*  33 */       this.theBiomeDecorator.flowersPerChunk = 100;
/*  34 */       this.theBiomeDecorator.grassPerChunk = 1;
/*  35 */       this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityRabbit.class, 4, 2, 3));
/*     */     } 
/*     */     
/*  38 */     if (this.type == Type.NORMAL)
/*     */     {
/*  40 */       this.spawnableCreatureList.add(new Biome.SpawnListEntry((Class)EntityWolf.class, 5, 4, 4));
/*     */     }
/*     */     
/*  43 */     if (this.type == Type.ROOFED)
/*     */     {
/*  45 */       this.theBiomeDecorator.treesPerChunk = -999;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldGenAbstractTree genBigTreeChance(Random rand) {
/*  51 */     if (this.type == Type.ROOFED && rand.nextInt(3) > 0)
/*     */     {
/*  53 */       return (WorldGenAbstractTree)ROOF_TREE;
/*     */     }
/*  55 */     if (this.type != Type.BIRCH && rand.nextInt(5) != 0)
/*     */     {
/*  57 */       return (rand.nextInt(10) == 0) ? (WorldGenAbstractTree)BIG_TREE_FEATURE : (WorldGenAbstractTree)TREE_FEATURE;
/*     */     }
/*     */ 
/*     */     
/*  61 */     return (WorldGenAbstractTree)BIRCH_TREE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos) {
/*  67 */     if (this.type == Type.FLOWER) {
/*     */       
/*  69 */       double d0 = MathHelper.clamp((1.0D + GRASS_COLOR_NOISE.getValue(pos.getX() / 48.0D, pos.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
/*  70 */       BlockFlower.EnumFlowerType blockflower$enumflowertype = BlockFlower.EnumFlowerType.values()[(int)(d0 * (BlockFlower.EnumFlowerType.values()).length)];
/*  71 */       return (blockflower$enumflowertype == BlockFlower.EnumFlowerType.BLUE_ORCHID) ? BlockFlower.EnumFlowerType.POPPY : blockflower$enumflowertype;
/*     */     } 
/*     */ 
/*     */     
/*  75 */     return super.pickRandomFlower(rand, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void decorate(World worldIn, Random rand, BlockPos pos) {
/*  81 */     if (this.type == Type.ROOFED)
/*     */     {
/*  83 */       addMushrooms(worldIn, rand, pos);
/*     */     }
/*     */     
/*  86 */     int i = rand.nextInt(5) - 3;
/*     */     
/*  88 */     if (this.type == Type.FLOWER)
/*     */     {
/*  90 */       i += 2;
/*     */     }
/*     */     
/*  93 */     addDoublePlants(worldIn, rand, pos, i);
/*  94 */     super.decorate(worldIn, rand, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addMushrooms(World p_185379_1_, Random p_185379_2_, BlockPos p_185379_3_) {
/*  99 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 101 */       for (int j = 0; j < 4; j++) {
/*     */         
/* 103 */         int k = i * 4 + 1 + 8 + p_185379_2_.nextInt(3);
/* 104 */         int l = j * 4 + 1 + 8 + p_185379_2_.nextInt(3);
/* 105 */         BlockPos blockpos = p_185379_1_.getHeight(p_185379_3_.add(k, 0, l));
/*     */         
/* 107 */         if (p_185379_2_.nextInt(20) == 0) {
/*     */           
/* 109 */           WorldGenBigMushroom worldgenbigmushroom = new WorldGenBigMushroom();
/* 110 */           worldgenbigmushroom.generate(p_185379_1_, p_185379_2_, blockpos);
/*     */         }
/*     */         else {
/*     */           
/* 114 */           WorldGenAbstractTree worldgenabstracttree = genBigTreeChance(p_185379_2_);
/* 115 */           worldgenabstracttree.setDecorationDefaults();
/*     */           
/* 117 */           if (worldgenabstracttree.generate(p_185379_1_, p_185379_2_, blockpos))
/*     */           {
/* 119 */             worldgenabstracttree.generateSaplings(p_185379_1_, p_185379_2_, blockpos);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addDoublePlants(World p_185378_1_, Random p_185378_2_, BlockPos p_185378_3_, int p_185378_4_) {
/* 128 */     for (int i = 0; i < p_185378_4_; i++) {
/*     */       
/* 130 */       int j = p_185378_2_.nextInt(3);
/*     */       
/* 132 */       if (j == 0) {
/*     */         
/* 134 */         DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);
/*     */       }
/* 136 */       else if (j == 1) {
/*     */         
/* 138 */         DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.ROSE);
/*     */       }
/* 140 */       else if (j == 2) {
/*     */         
/* 142 */         DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.PAEONIA);
/*     */       } 
/*     */       
/* 145 */       for (int k = 0; k < 5; k++) {
/*     */         
/* 147 */         int l = p_185378_2_.nextInt(16) + 8;
/* 148 */         int i1 = p_185378_2_.nextInt(16) + 8;
/* 149 */         int j1 = p_185378_2_.nextInt(p_185378_1_.getHeight(p_185378_3_.add(l, 0, i1)).getY() + 32);
/*     */         
/* 151 */         if (DOUBLE_PLANT_GENERATOR.generate(p_185378_1_, p_185378_2_, new BlockPos(p_185378_3_.getX() + l, j1, p_185378_3_.getZ() + i1))) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Class<? extends Biome> getBiomeClass() {
/* 161 */     return (Class)BiomeForest.class;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getGrassColorAtPos(BlockPos pos) {
/* 166 */     int i = super.getGrassColorAtPos(pos);
/* 167 */     return (this.type == Type.ROOFED) ? ((i & 0xFEFEFE) + 2634762 >> 1) : i;
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/* 172 */     NORMAL,
/* 173 */     FLOWER,
/* 174 */     BIRCH,
/* 175 */     ROOFED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeForest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */