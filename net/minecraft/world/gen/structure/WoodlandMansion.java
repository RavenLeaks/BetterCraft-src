/*     */ package net.minecraft.world.gen.structure;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.biome.BiomeProvider;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.ChunkGeneratorOverworld;
/*     */ 
/*     */ public class WoodlandMansion extends MapGenStructure {
/*  19 */   private final int field_191073_b = 80;
/*  20 */   private final int field_191074_d = 20;
/*  21 */   public static final List<Biome> field_191072_a = Arrays.asList(new Biome[] { Biomes.ROOFED_FOREST, Biomes.MUTATED_ROOFED_FOREST });
/*     */   
/*     */   private final ChunkGeneratorOverworld field_191075_h;
/*     */   
/*     */   public WoodlandMansion(ChunkGeneratorOverworld p_i47240_1_) {
/*  26 */     this.field_191075_h = p_i47240_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStructureName() {
/*  31 */     return "Mansion";
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/*  36 */     int i = chunkX;
/*  37 */     int j = chunkZ;
/*     */     
/*  39 */     if (chunkX < 0)
/*     */     {
/*  41 */       i = chunkX - 79;
/*     */     }
/*     */     
/*  44 */     if (chunkZ < 0)
/*     */     {
/*  46 */       j = chunkZ - 79;
/*     */     }
/*     */     
/*  49 */     int k = i / 80;
/*  50 */     int l = j / 80;
/*  51 */     Random random = this.worldObj.setRandomSeed(k, l, 10387319);
/*  52 */     k *= 80;
/*  53 */     l *= 80;
/*  54 */     k += (random.nextInt(60) + random.nextInt(60)) / 2;
/*  55 */     l += (random.nextInt(60) + random.nextInt(60)) / 2;
/*     */     
/*  57 */     if (chunkX == k && chunkZ == l) {
/*     */       
/*  59 */       boolean flag = this.worldObj.getBiomeProvider().areBiomesViable(chunkX * 16 + 8, chunkZ * 16 + 8, 32, field_191072_a);
/*     */       
/*  61 */       if (flag)
/*     */       {
/*  63 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos, boolean p_180706_3_) {
/*  72 */     this.worldObj = worldIn;
/*  73 */     BiomeProvider biomeprovider = worldIn.getBiomeProvider();
/*  74 */     return (biomeprovider.func_190944_c() && biomeprovider.func_190943_d() != Biomes.ROOFED_FOREST) ? null : func_191069_a(worldIn, this, pos, 80, 20, 10387319, true, 100, p_180706_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/*  79 */     return new Start(this.worldObj, this.field_191075_h, this.rand, chunkX, chunkZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Start
/*     */     extends StructureStart
/*     */   {
/*     */     private boolean field_191093_c;
/*     */ 
/*     */     
/*     */     public Start() {}
/*     */     
/*     */     public Start(World p_i47235_1_, ChunkGeneratorOverworld p_i47235_2_, Random p_i47235_3_, int p_i47235_4_, int p_i47235_5_) {
/*  92 */       super(p_i47235_4_, p_i47235_5_);
/*  93 */       func_191092_a(p_i47235_1_, p_i47235_2_, p_i47235_3_, p_i47235_4_, p_i47235_5_);
/*     */     }
/*     */ 
/*     */     
/*     */     private void func_191092_a(World p_191092_1_, ChunkGeneratorOverworld p_191092_2_, Random p_191092_3_, int p_191092_4_, int p_191092_5_) {
/*  98 */       Rotation rotation = Rotation.values()[p_191092_3_.nextInt((Rotation.values()).length)];
/*  99 */       ChunkPrimer chunkprimer = new ChunkPrimer();
/* 100 */       p_191092_2_.setBlocksInChunk(p_191092_4_, p_191092_5_, chunkprimer);
/* 101 */       int i = 5;
/* 102 */       int j = 5;
/*     */       
/* 104 */       if (rotation == Rotation.CLOCKWISE_90) {
/*     */         
/* 106 */         i = -5;
/*     */       }
/* 108 */       else if (rotation == Rotation.CLOCKWISE_180) {
/*     */         
/* 110 */         i = -5;
/* 111 */         j = -5;
/*     */       }
/* 113 */       else if (rotation == Rotation.COUNTERCLOCKWISE_90) {
/*     */         
/* 115 */         j = -5;
/*     */       } 
/*     */       
/* 118 */       int k = chunkprimer.findGroundBlockIdx(7, 7);
/* 119 */       int l = chunkprimer.findGroundBlockIdx(7, 7 + j);
/* 120 */       int i1 = chunkprimer.findGroundBlockIdx(7 + i, 7);
/* 121 */       int j1 = chunkprimer.findGroundBlockIdx(7 + i, 7 + j);
/* 122 */       int k1 = Math.min(Math.min(k, l), Math.min(i1, j1));
/*     */       
/* 124 */       if (k1 < 60) {
/*     */         
/* 126 */         this.field_191093_c = false;
/*     */       }
/*     */       else {
/*     */         
/* 130 */         BlockPos blockpos = new BlockPos(p_191092_4_ * 16 + 8, k1 + 1, p_191092_5_ * 16 + 8);
/* 131 */         List<WoodlandMansionPieces.MansionTemplate> list = Lists.newLinkedList();
/* 132 */         WoodlandMansionPieces.func_191152_a(p_191092_1_.getSaveHandler().getStructureTemplateManager(), blockpos, rotation, list, p_191092_3_);
/* 133 */         this.components.addAll((Collection)list);
/* 134 */         updateBoundingBox();
/* 135 */         this.field_191093_c = true;
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb) {
/* 141 */       super.generateStructure(worldIn, rand, structurebb);
/* 142 */       int i = this.boundingBox.minY;
/*     */       
/* 144 */       for (int j = structurebb.minX; j <= structurebb.maxX; j++) {
/*     */         
/* 146 */         for (int k = structurebb.minZ; k <= structurebb.maxZ; k++) {
/*     */           
/* 148 */           BlockPos blockpos = new BlockPos(j, i, k);
/*     */           
/* 150 */           if (!worldIn.isAirBlock(blockpos) && this.boundingBox.isVecInside((Vec3i)blockpos)) {
/*     */             
/* 152 */             boolean flag = false;
/*     */             
/* 154 */             for (StructureComponent structurecomponent : this.components) {
/*     */               
/* 156 */               if (structurecomponent.boundingBox.isVecInside((Vec3i)blockpos)) {
/*     */                 
/* 158 */                 flag = true;
/*     */                 
/*     */                 break;
/*     */               } 
/*     */             } 
/* 163 */             if (flag)
/*     */             {
/* 165 */               for (int l = i - 1; l > 1; l--) {
/*     */                 
/* 167 */                 BlockPos blockpos1 = new BlockPos(j, l, k);
/*     */                 
/* 169 */                 if (!worldIn.isAirBlock(blockpos1) && !worldIn.getBlockState(blockpos1).getMaterial().isLiquid()) {
/*     */                   break;
/*     */                 }
/*     */ 
/*     */                 
/* 174 */                 worldIn.setBlockState(blockpos1, Blocks.COBBLESTONE.getDefaultState(), 2);
/*     */               } 
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSizeableStructure() {
/* 184 */       return this.field_191093_c;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\WoodlandMansion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */