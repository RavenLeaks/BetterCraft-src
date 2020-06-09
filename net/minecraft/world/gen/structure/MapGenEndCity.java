/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.ChunkGeneratorEnd;
/*     */ 
/*     */ public class MapGenEndCity
/*     */   extends MapGenStructure {
/*  12 */   private final int citySpacing = 20;
/*  13 */   private final int minCitySeparation = 11;
/*     */   
/*     */   private final ChunkGeneratorEnd endProvider;
/*     */   
/*     */   public MapGenEndCity(ChunkGeneratorEnd p_i46665_1_) {
/*  18 */     this.endProvider = p_i46665_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStructureName() {
/*  23 */     return "EndCity";
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/*  28 */     int i = chunkX;
/*  29 */     int j = chunkZ;
/*     */     
/*  31 */     if (chunkX < 0)
/*     */     {
/*  33 */       chunkX -= 19;
/*     */     }
/*     */     
/*  36 */     if (chunkZ < 0)
/*     */     {
/*  38 */       chunkZ -= 19;
/*     */     }
/*     */     
/*  41 */     int k = chunkX / 20;
/*  42 */     int l = chunkZ / 20;
/*  43 */     Random random = this.worldObj.setRandomSeed(k, l, 10387313);
/*  44 */     k *= 20;
/*  45 */     l *= 20;
/*  46 */     k += (random.nextInt(9) + random.nextInt(9)) / 2;
/*  47 */     l += (random.nextInt(9) + random.nextInt(9)) / 2;
/*     */     
/*  49 */     if (i == k && j == l && this.endProvider.isIslandChunk(i, j)) {
/*     */       
/*  51 */       int i1 = func_191070_b(i, j, this.endProvider);
/*  52 */       return (i1 >= 60);
/*     */     } 
/*     */ 
/*     */     
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/*  62 */     return new Start(this.worldObj, this.endProvider, this.rand, chunkX, chunkZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos, boolean p_180706_3_) {
/*  67 */     this.worldObj = worldIn;
/*  68 */     return func_191069_a(worldIn, this, pos, 20, 11, 10387313, true, 100, p_180706_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   private static int func_191070_b(int p_191070_0_, int p_191070_1_, ChunkGeneratorEnd p_191070_2_) {
/*  73 */     Random random = new Random((p_191070_0_ + p_191070_1_ * 10387313));
/*  74 */     Rotation rotation = Rotation.values()[random.nextInt((Rotation.values()).length)];
/*  75 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/*  76 */     p_191070_2_.setBlocksInChunk(p_191070_0_, p_191070_1_, chunkprimer);
/*  77 */     int i = 5;
/*  78 */     int j = 5;
/*     */     
/*  80 */     if (rotation == Rotation.CLOCKWISE_90) {
/*     */       
/*  82 */       i = -5;
/*     */     }
/*  84 */     else if (rotation == Rotation.CLOCKWISE_180) {
/*     */       
/*  86 */       i = -5;
/*  87 */       j = -5;
/*     */     }
/*  89 */     else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
/*     */       
/*  91 */       j = -5;
/*     */     } 
/*     */     
/*  94 */     int k = chunkprimer.findGroundBlockIdx(7, 7);
/*  95 */     int l = chunkprimer.findGroundBlockIdx(7, 7 + j);
/*  96 */     int i1 = chunkprimer.findGroundBlockIdx(7 + i, 7);
/*  97 */     int j1 = chunkprimer.findGroundBlockIdx(7 + i, 7 + j);
/*  98 */     int k1 = Math.min(Math.min(k, l), Math.min(i1, j1));
/*  99 */     return k1;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Start
/*     */     extends StructureStart
/*     */   {
/*     */     private boolean isSizeable;
/*     */ 
/*     */     
/*     */     public Start() {}
/*     */     
/*     */     public Start(World worldIn, ChunkGeneratorEnd chunkProvider, Random random, int chunkX, int chunkZ) {
/* 112 */       super(chunkX, chunkZ);
/* 113 */       create(worldIn, chunkProvider, random, chunkX, chunkZ);
/*     */     }
/*     */ 
/*     */     
/*     */     private void create(World worldIn, ChunkGeneratorEnd chunkProvider, Random rnd, int chunkX, int chunkZ) {
/* 118 */       Random random = new Random((chunkX + chunkZ * 10387313));
/* 119 */       Rotation rotation = Rotation.values()[random.nextInt((Rotation.values()).length)];
/* 120 */       int i = MapGenEndCity.func_191070_b(chunkX, chunkZ, chunkProvider);
/*     */       
/* 122 */       if (i < 60) {
/*     */         
/* 124 */         this.isSizeable = false;
/*     */       }
/*     */       else {
/*     */         
/* 128 */         BlockPos blockpos = new BlockPos(chunkX * 16 + 8, i, chunkZ * 16 + 8);
/* 129 */         StructureEndCityPieces.func_191087_a(worldIn.getSaveHandler().getStructureTemplateManager(), blockpos, rotation, this.components, rnd);
/* 130 */         updateBoundingBox();
/* 131 */         this.isSizeable = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSizeableStructure() {
/* 137 */       return this.isSizeable;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\MapGenEndCity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */