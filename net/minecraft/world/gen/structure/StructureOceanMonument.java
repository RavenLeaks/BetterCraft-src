/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.monster.EntityGuardian;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ 
/*     */ 
/*     */ public class StructureOceanMonument
/*     */   extends MapGenStructure
/*     */ {
/*  26 */   public static final List<Biome> WATER_BIOMES = Arrays.asList(new Biome[] { Biomes.OCEAN, Biomes.DEEP_OCEAN, Biomes.RIVER, Biomes.FROZEN_OCEAN, Biomes.FROZEN_RIVER });
/*  27 */   public static final List<Biome> SPAWN_BIOMES = Arrays.asList(new Biome[] { Biomes.DEEP_OCEAN });
/*  28 */   private static final List<Biome.SpawnListEntry> MONUMENT_ENEMIES = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */   
/*  32 */   private int spacing = 32;
/*  33 */   private int separation = 5;
/*     */ 
/*     */ 
/*     */   
/*     */   public StructureOceanMonument(Map<String, String> p_i45608_1_) {
/*  38 */     this();
/*     */     
/*  40 */     for (Map.Entry<String, String> entry : p_i45608_1_.entrySet()) {
/*     */       
/*  42 */       if (((String)entry.getKey()).equals("spacing")) {
/*     */         
/*  44 */         this.spacing = MathHelper.getInt(entry.getValue(), this.spacing, 1); continue;
/*     */       } 
/*  46 */       if (((String)entry.getKey()).equals("separation"))
/*     */       {
/*  48 */         this.separation = MathHelper.getInt(entry.getValue(), this.separation, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStructureName() {
/*  55 */     return "Monument";
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/*  60 */     int i = chunkX;
/*  61 */     int j = chunkZ;
/*     */     
/*  63 */     if (chunkX < 0)
/*     */     {
/*  65 */       chunkX -= this.spacing - 1;
/*     */     }
/*     */     
/*  68 */     if (chunkZ < 0)
/*     */     {
/*  70 */       chunkZ -= this.spacing - 1;
/*     */     }
/*     */     
/*  73 */     int k = chunkX / this.spacing;
/*  74 */     int l = chunkZ / this.spacing;
/*  75 */     Random random = this.worldObj.setRandomSeed(k, l, 10387313);
/*  76 */     k *= this.spacing;
/*  77 */     l *= this.spacing;
/*  78 */     k += (random.nextInt(this.spacing - this.separation) + random.nextInt(this.spacing - this.separation)) / 2;
/*  79 */     l += (random.nextInt(this.spacing - this.separation) + random.nextInt(this.spacing - this.separation)) / 2;
/*     */     
/*  81 */     if (i == k && j == l) {
/*     */       
/*  83 */       if (!this.worldObj.getBiomeProvider().areBiomesViable(i * 16 + 8, j * 16 + 8, 16, SPAWN_BIOMES))
/*     */       {
/*  85 */         return false;
/*     */       }
/*     */       
/*  88 */       boolean flag = this.worldObj.getBiomeProvider().areBiomesViable(i * 16 + 8, j * 16 + 8, 29, WATER_BIOMES);
/*     */       
/*  90 */       if (flag)
/*     */       {
/*  92 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos, boolean p_180706_3_) {
/* 101 */     this.worldObj = worldIn;
/* 102 */     return func_191069_a(worldIn, this, pos, this.spacing, this.separation, 10387313, true, 100, p_180706_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/* 107 */     return new StartMonument(this.worldObj, this.rand, chunkX, chunkZ);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Biome.SpawnListEntry> getScatteredFeatureSpawnList() {
/* 112 */     return MONUMENT_ENEMIES;
/*     */   }
/*     */ 
/*     */   
/*     */   static {
/* 117 */     MONUMENT_ENEMIES.add(new Biome.SpawnListEntry(EntityGuardian.class, 1, 2, 4));
/*     */   }
/*     */   
/*     */   public StructureOceanMonument() {}
/*     */   
/* 122 */   public static class StartMonument extends StructureStart { private final Set<ChunkPos> processed = Sets.newHashSet();
/*     */     
/*     */     private boolean wasCreated;
/*     */ 
/*     */     
/*     */     public StartMonument() {}
/*     */ 
/*     */     
/*     */     public StartMonument(World worldIn, Random random, int chunkX, int chunkZ) {
/* 131 */       super(chunkX, chunkZ);
/* 132 */       create(worldIn, random, chunkX, chunkZ);
/*     */     }
/*     */ 
/*     */     
/*     */     private void create(World worldIn, Random random, int chunkX, int chunkZ) {
/* 137 */       random.setSeed(worldIn.getSeed());
/* 138 */       long i = random.nextLong();
/* 139 */       long j = random.nextLong();
/* 140 */       long k = chunkX * i;
/* 141 */       long l = chunkZ * j;
/* 142 */       random.setSeed(k ^ l ^ worldIn.getSeed());
/* 143 */       int i1 = chunkX * 16 + 8 - 29;
/* 144 */       int j1 = chunkZ * 16 + 8 - 29;
/* 145 */       EnumFacing enumfacing = EnumFacing.Plane.HORIZONTAL.random(random);
/* 146 */       this.components.add(new StructureOceanMonumentPieces.MonumentBuilding(random, i1, j1, enumfacing));
/* 147 */       updateBoundingBox();
/* 148 */       this.wasCreated = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public void generateStructure(World worldIn, Random rand, StructureBoundingBox structurebb) {
/* 153 */       if (!this.wasCreated) {
/*     */         
/* 155 */         this.components.clear();
/* 156 */         create(worldIn, rand, getChunkPosX(), getChunkPosZ());
/*     */       } 
/*     */       
/* 159 */       super.generateStructure(worldIn, rand, structurebb);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isValidForPostProcess(ChunkPos pair) {
/* 164 */       return this.processed.contains(pair) ? false : super.isValidForPostProcess(pair);
/*     */     }
/*     */ 
/*     */     
/*     */     public void notifyPostProcessAt(ChunkPos pair) {
/* 169 */       super.notifyPostProcessAt(pair);
/* 170 */       this.processed.add(pair);
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeToNBT(NBTTagCompound tagCompound) {
/* 175 */       super.writeToNBT(tagCompound);
/* 176 */       NBTTagList nbttaglist = new NBTTagList();
/*     */       
/* 178 */       for (ChunkPos chunkpos : this.processed) {
/*     */         
/* 180 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 181 */         nbttagcompound.setInteger("X", chunkpos.chunkXPos);
/* 182 */         nbttagcompound.setInteger("Z", chunkpos.chunkZPos);
/* 183 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */       
/* 186 */       tagCompound.setTag("Processed", (NBTBase)nbttaglist);
/*     */     }
/*     */ 
/*     */     
/*     */     public void readFromNBT(NBTTagCompound tagCompound) {
/* 191 */       super.readFromNBT(tagCompound);
/*     */       
/* 193 */       if (tagCompound.hasKey("Processed", 9)) {
/*     */         
/* 195 */         NBTTagList nbttaglist = tagCompound.getTagList("Processed", 10);
/*     */         
/* 197 */         for (int i = 0; i < nbttaglist.tagCount(); i++) {
/*     */           
/* 199 */           NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(i);
/* 200 */           this.processed.add(new ChunkPos(nbttagcompound.getInteger("X"), nbttagcompound.getInteger("Z")));
/*     */         } 
/*     */       } 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\StructureOceanMonument.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */