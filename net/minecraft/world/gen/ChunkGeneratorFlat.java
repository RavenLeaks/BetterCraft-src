/*     */ package net.minecraft.world.gen;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.feature.WorldGenDungeons;
/*     */ import net.minecraft.world.gen.feature.WorldGenLakes;
/*     */ import net.minecraft.world.gen.structure.MapGenMineshaft;
/*     */ import net.minecraft.world.gen.structure.MapGenScatteredFeature;
/*     */ import net.minecraft.world.gen.structure.MapGenStronghold;
/*     */ import net.minecraft.world.gen.structure.MapGenStructure;
/*     */ import net.minecraft.world.gen.structure.MapGenVillage;
/*     */ import net.minecraft.world.gen.structure.StructureOceanMonument;
/*     */ 
/*     */ public class ChunkGeneratorFlat implements IChunkGenerator {
/*     */   private final World worldObj;
/*     */   private final Random random;
/*  31 */   private final IBlockState[] cachedBlockIDs = new IBlockState[256];
/*     */   private final FlatGeneratorInfo flatWorldGenInfo;
/*  33 */   private final Map<String, MapGenStructure> structureGenerators = new HashMap<>();
/*     */   
/*     */   private final boolean hasDecoration;
/*     */   private final boolean hasDungeons;
/*     */   private WorldGenLakes waterLakeGenerator;
/*     */   private WorldGenLakes lavaLakeGenerator;
/*     */   
/*     */   public ChunkGeneratorFlat(World worldIn, long seed, boolean generateStructures, String flatGeneratorSettings) {
/*  41 */     this.worldObj = worldIn;
/*  42 */     this.random = new Random(seed);
/*  43 */     this.flatWorldGenInfo = FlatGeneratorInfo.createFlatGeneratorFromString(flatGeneratorSettings);
/*     */     
/*  45 */     if (generateStructures) {
/*     */       
/*  47 */       Map<String, Map<String, String>> map = this.flatWorldGenInfo.getWorldFeatures();
/*     */       
/*  49 */       if (map.containsKey("village")) {
/*     */         
/*  51 */         Map<String, String> map1 = map.get("village");
/*     */         
/*  53 */         if (!map1.containsKey("size"))
/*     */         {
/*  55 */           map1.put("size", "1");
/*     */         }
/*     */         
/*  58 */         this.structureGenerators.put("Village", new MapGenVillage(map1));
/*     */       } 
/*     */       
/*  61 */       if (map.containsKey("biome_1"))
/*     */       {
/*  63 */         this.structureGenerators.put("Temple", new MapGenScatteredFeature(map.get("biome_1")));
/*     */       }
/*     */       
/*  66 */       if (map.containsKey("mineshaft"))
/*     */       {
/*  68 */         this.structureGenerators.put("Mineshaft", new MapGenMineshaft(map.get("mineshaft")));
/*     */       }
/*     */       
/*  71 */       if (map.containsKey("stronghold"))
/*     */       {
/*  73 */         this.structureGenerators.put("Stronghold", new MapGenStronghold(map.get("stronghold")));
/*     */       }
/*     */       
/*  76 */       if (map.containsKey("oceanmonument"))
/*     */       {
/*  78 */         this.structureGenerators.put("Monument", new StructureOceanMonument(map.get("oceanmonument")));
/*     */       }
/*     */     } 
/*     */     
/*  82 */     if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lake"))
/*     */     {
/*  84 */       this.waterLakeGenerator = new WorldGenLakes((Block)Blocks.WATER);
/*     */     }
/*     */     
/*  87 */     if (this.flatWorldGenInfo.getWorldFeatures().containsKey("lava_lake"))
/*     */     {
/*  89 */       this.lavaLakeGenerator = new WorldGenLakes((Block)Blocks.LAVA);
/*     */     }
/*     */     
/*  92 */     this.hasDungeons = this.flatWorldGenInfo.getWorldFeatures().containsKey("dungeon");
/*  93 */     int j = 0;
/*  94 */     int k = 0;
/*  95 */     boolean flag = true;
/*     */     
/*  97 */     for (FlatLayerInfo flatlayerinfo : this.flatWorldGenInfo.getFlatLayers()) {
/*     */       
/*  99 */       for (int i = flatlayerinfo.getMinY(); i < flatlayerinfo.getMinY() + flatlayerinfo.getLayerCount(); i++) {
/*     */         
/* 101 */         IBlockState iblockstate = flatlayerinfo.getLayerMaterial();
/*     */         
/* 103 */         if (iblockstate.getBlock() != Blocks.AIR) {
/*     */           
/* 105 */           flag = false;
/* 106 */           this.cachedBlockIDs[i] = iblockstate;
/*     */         } 
/*     */       } 
/*     */       
/* 110 */       if (flatlayerinfo.getLayerMaterial().getBlock() == Blocks.AIR) {
/*     */         
/* 112 */         k += flatlayerinfo.getLayerCount();
/*     */         
/*     */         continue;
/*     */       } 
/* 116 */       j += flatlayerinfo.getLayerCount() + k;
/* 117 */       k = 0;
/*     */     } 
/*     */ 
/*     */     
/* 121 */     worldIn.setSeaLevel(j);
/* 122 */     this.hasDecoration = (flag && this.flatWorldGenInfo.getBiome() != Biome.getIdForBiome(Biomes.VOID)) ? false : this.flatWorldGenInfo.getWorldFeatures().containsKey("decoration");
/*     */   }
/*     */ 
/*     */   
/*     */   public Chunk provideChunk(int x, int z) {
/* 127 */     ChunkPrimer chunkprimer = new ChunkPrimer();
/*     */     
/* 129 */     for (int i = 0; i < this.cachedBlockIDs.length; i++) {
/*     */       
/* 131 */       IBlockState iblockstate = this.cachedBlockIDs[i];
/*     */       
/* 133 */       if (iblockstate != null)
/*     */       {
/* 135 */         for (int j = 0; j < 16; j++) {
/*     */           
/* 137 */           for (int k = 0; k < 16; k++)
/*     */           {
/* 139 */             chunkprimer.setBlockState(j, i, k, iblockstate);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/* 145 */     for (MapGenBase mapgenbase : this.structureGenerators.values())
/*     */     {
/* 147 */       mapgenbase.generate(this.worldObj, x, z, chunkprimer);
/*     */     }
/*     */     
/* 150 */     Chunk chunk = new Chunk(this.worldObj, chunkprimer, x, z);
/* 151 */     Biome[] abiome = this.worldObj.getBiomeProvider().getBiomes(null, x * 16, z * 16, 16, 16);
/* 152 */     byte[] abyte = chunk.getBiomeArray();
/*     */     
/* 154 */     for (int l = 0; l < abyte.length; l++)
/*     */     {
/* 156 */       abyte[l] = (byte)Biome.getIdForBiome(abiome[l]);
/*     */     }
/*     */     
/* 159 */     chunk.generateSkylightMap();
/* 160 */     return chunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public void populate(int x, int z) {
/* 165 */     int i = x * 16;
/* 166 */     int j = z * 16;
/* 167 */     BlockPos blockpos = new BlockPos(i, 0, j);
/* 168 */     Biome biome = this.worldObj.getBiome(new BlockPos(i + 16, 0, j + 16));
/* 169 */     boolean flag = false;
/* 170 */     this.random.setSeed(this.worldObj.getSeed());
/* 171 */     long k = this.random.nextLong() / 2L * 2L + 1L;
/* 172 */     long l = this.random.nextLong() / 2L * 2L + 1L;
/* 173 */     this.random.setSeed(x * k + z * l ^ this.worldObj.getSeed());
/* 174 */     ChunkPos chunkpos = new ChunkPos(x, z);
/*     */     
/* 176 */     for (MapGenStructure mapgenstructure : this.structureGenerators.values()) {
/*     */       
/* 178 */       boolean flag1 = mapgenstructure.generateStructure(this.worldObj, this.random, chunkpos);
/*     */       
/* 180 */       if (mapgenstructure instanceof MapGenVillage)
/*     */       {
/* 182 */         flag |= flag1;
/*     */       }
/*     */     } 
/*     */     
/* 186 */     if (this.waterLakeGenerator != null && !flag && this.random.nextInt(4) == 0)
/*     */     {
/* 188 */       this.waterLakeGenerator.generate(this.worldObj, this.random, blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
/*     */     }
/*     */     
/* 191 */     if (this.lavaLakeGenerator != null && !flag && this.random.nextInt(8) == 0) {
/*     */       
/* 193 */       BlockPos blockpos1 = blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(this.random.nextInt(248) + 8), this.random.nextInt(16) + 8);
/*     */       
/* 195 */       if (blockpos1.getY() < this.worldObj.getSeaLevel() || this.random.nextInt(10) == 0)
/*     */       {
/* 197 */         this.lavaLakeGenerator.generate(this.worldObj, this.random, blockpos1);
/*     */       }
/*     */     } 
/*     */     
/* 201 */     if (this.hasDungeons)
/*     */     {
/* 203 */       for (int i1 = 0; i1 < 8; i1++)
/*     */       {
/* 205 */         (new WorldGenDungeons()).generate(this.worldObj, this.random, blockpos.add(this.random.nextInt(16) + 8, this.random.nextInt(256), this.random.nextInt(16) + 8));
/*     */       }
/*     */     }
/*     */     
/* 209 */     if (this.hasDecoration)
/*     */     {
/* 211 */       biome.decorate(this.worldObj, this.random, blockpos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generateStructures(Chunk chunkIn, int x, int z) {
/* 217 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Biome.SpawnListEntry> getPossibleCreatures(EnumCreatureType creatureType, BlockPos pos) {
/* 222 */     Biome biome = this.worldObj.getBiome(pos);
/* 223 */     return biome.getSpawnableList(creatureType);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public BlockPos getStrongholdGen(World worldIn, String structureName, BlockPos position, boolean p_180513_4_) {
/* 229 */     MapGenStructure mapgenstructure = this.structureGenerators.get(structureName);
/* 230 */     return (mapgenstructure != null) ? mapgenstructure.getClosestStrongholdPos(worldIn, position, p_180513_4_) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193414_a(World p_193414_1_, String p_193414_2_, BlockPos p_193414_3_) {
/* 235 */     MapGenStructure mapgenstructure = this.structureGenerators.get(p_193414_2_);
/* 236 */     return (mapgenstructure != null) ? mapgenstructure.isInsideStructure(p_193414_3_) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void recreateStructures(Chunk chunkIn, int x, int z) {
/* 241 */     for (MapGenStructure mapgenstructure : this.structureGenerators.values())
/*     */     {
/* 243 */       mapgenstructure.generate(this.worldObj, x, z, null);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\ChunkGeneratorFlat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */