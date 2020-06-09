/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.entity.monster.EntityWitch;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ 
/*     */ public class MapGenScatteredFeature
/*     */   extends MapGenStructure
/*     */ {
/*  18 */   private static final List<Biome> BIOMELIST = Arrays.asList(new Biome[] { Biomes.DESERT, Biomes.DESERT_HILLS, Biomes.JUNGLE, Biomes.JUNGLE_HILLS, Biomes.SWAMPLAND, Biomes.ICE_PLAINS, Biomes.COLD_TAIGA });
/*     */ 
/*     */   
/*     */   private final List<Biome.SpawnListEntry> scatteredFeatureSpawnList;
/*     */   
/*     */   private int maxDistanceBetweenScatteredFeatures;
/*     */   
/*     */   private final int minDistanceBetweenScatteredFeatures;
/*     */ 
/*     */   
/*     */   public MapGenScatteredFeature() {
/*  29 */     this.scatteredFeatureSpawnList = Lists.newArrayList();
/*  30 */     this.maxDistanceBetweenScatteredFeatures = 32;
/*  31 */     this.minDistanceBetweenScatteredFeatures = 8;
/*  32 */     this.scatteredFeatureSpawnList.add(new Biome.SpawnListEntry(EntityWitch.class, 1, 1, 1));
/*     */   }
/*     */ 
/*     */   
/*     */   public MapGenScatteredFeature(Map<String, String> p_i2061_1_) {
/*  37 */     this();
/*     */     
/*  39 */     for (Map.Entry<String, String> entry : p_i2061_1_.entrySet()) {
/*     */       
/*  41 */       if (((String)entry.getKey()).equals("distance"))
/*     */       {
/*  43 */         this.maxDistanceBetweenScatteredFeatures = MathHelper.getInt(entry.getValue(), this.maxDistanceBetweenScatteredFeatures, 9);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStructureName() {
/*  50 */     return "Temple";
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/*  55 */     int i = chunkX;
/*  56 */     int j = chunkZ;
/*     */     
/*  58 */     if (chunkX < 0)
/*     */     {
/*  60 */       chunkX -= this.maxDistanceBetweenScatteredFeatures - 1;
/*     */     }
/*     */     
/*  63 */     if (chunkZ < 0)
/*     */     {
/*  65 */       chunkZ -= this.maxDistanceBetweenScatteredFeatures - 1;
/*     */     }
/*     */     
/*  68 */     int k = chunkX / this.maxDistanceBetweenScatteredFeatures;
/*  69 */     int l = chunkZ / this.maxDistanceBetweenScatteredFeatures;
/*  70 */     Random random = this.worldObj.setRandomSeed(k, l, 14357617);
/*  71 */     k *= this.maxDistanceBetweenScatteredFeatures;
/*  72 */     l *= this.maxDistanceBetweenScatteredFeatures;
/*  73 */     k += random.nextInt(this.maxDistanceBetweenScatteredFeatures - 8);
/*  74 */     l += random.nextInt(this.maxDistanceBetweenScatteredFeatures - 8);
/*     */     
/*  76 */     if (i == k && j == l) {
/*     */       
/*  78 */       Biome biome = this.worldObj.getBiomeProvider().getBiome(new BlockPos(i * 16 + 8, 0, j * 16 + 8));
/*     */       
/*  80 */       if (biome == null)
/*     */       {
/*  82 */         return false;
/*     */       }
/*     */       
/*  85 */       for (Biome biome1 : BIOMELIST) {
/*     */         
/*  87 */         if (biome == biome1)
/*     */         {
/*  89 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos, boolean p_180706_3_) {
/*  99 */     this.worldObj = worldIn;
/* 100 */     return func_191069_a(worldIn, this, pos, this.maxDistanceBetweenScatteredFeatures, 8, 14357617, false, 100, p_180706_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/* 105 */     return new Start(this.worldObj, this.rand, chunkX, chunkZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSwampHut(BlockPos p_175798_1_) {
/* 110 */     StructureStart structurestart = getStructureAt(p_175798_1_);
/*     */     
/* 112 */     if (structurestart != null && structurestart instanceof Start && !structurestart.components.isEmpty()) {
/*     */       
/* 114 */       StructureComponent structurecomponent = structurestart.components.get(0);
/* 115 */       return structurecomponent instanceof ComponentScatteredFeaturePieces.SwampHut;
/*     */     } 
/*     */ 
/*     */     
/* 119 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Biome.SpawnListEntry> getScatteredFeatureSpawnList() {
/* 125 */     return this.scatteredFeatureSpawnList;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Start
/*     */     extends StructureStart
/*     */   {
/*     */     public Start() {}
/*     */ 
/*     */     
/*     */     public Start(World worldIn, Random random, int chunkX, int chunkZ) {
/* 136 */       this(worldIn, random, chunkX, chunkZ, worldIn.getBiome(new BlockPos(chunkX * 16 + 8, 0, chunkZ * 16 + 8)));
/*     */     }
/*     */ 
/*     */     
/*     */     public Start(World worldIn, Random random, int chunkX, int chunkZ, Biome biomeIn) {
/* 141 */       super(chunkX, chunkZ);
/*     */       
/* 143 */       if (biomeIn != Biomes.JUNGLE && biomeIn != Biomes.JUNGLE_HILLS) {
/*     */         
/* 145 */         if (biomeIn == Biomes.SWAMPLAND) {
/*     */           
/* 147 */           ComponentScatteredFeaturePieces.SwampHut componentscatteredfeaturepieces$swamphut = new ComponentScatteredFeaturePieces.SwampHut(random, chunkX * 16, chunkZ * 16);
/* 148 */           this.components.add(componentscatteredfeaturepieces$swamphut);
/*     */         }
/* 150 */         else if (biomeIn != Biomes.DESERT && biomeIn != Biomes.DESERT_HILLS) {
/*     */           
/* 152 */           if (biomeIn == Biomes.ICE_PLAINS || biomeIn == Biomes.COLD_TAIGA)
/*     */           {
/* 154 */             ComponentScatteredFeaturePieces.Igloo componentscatteredfeaturepieces$igloo = new ComponentScatteredFeaturePieces.Igloo(random, chunkX * 16, chunkZ * 16);
/* 155 */             this.components.add(componentscatteredfeaturepieces$igloo);
/*     */           }
/*     */         
/*     */         } else {
/*     */           
/* 160 */           ComponentScatteredFeaturePieces.DesertPyramid componentscatteredfeaturepieces$desertpyramid = new ComponentScatteredFeaturePieces.DesertPyramid(random, chunkX * 16, chunkZ * 16);
/* 161 */           this.components.add(componentscatteredfeaturepieces$desertpyramid);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 166 */         ComponentScatteredFeaturePieces.JunglePyramid componentscatteredfeaturepieces$junglepyramid = new ComponentScatteredFeaturePieces.JunglePyramid(random, chunkX * 16, chunkZ * 16);
/* 167 */         this.components.add(componentscatteredfeaturepieces$junglepyramid);
/*     */       } 
/*     */       
/* 170 */       updateBoundingBox();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\MapGenScatteredFeature.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */