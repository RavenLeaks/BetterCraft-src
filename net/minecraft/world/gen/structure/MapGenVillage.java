/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ 
/*     */ public class MapGenVillage
/*     */   extends MapGenStructure
/*     */ {
/*  17 */   public static final List<Biome> VILLAGE_SPAWN_BIOMES = Arrays.asList(new Biome[] { Biomes.PLAINS, Biomes.DESERT, Biomes.SAVANNA, Biomes.TAIGA });
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int size;
/*     */ 
/*     */ 
/*     */   
/*  26 */   private int distance = 32;
/*  27 */   private final int minTownSeparation = 8;
/*     */   
/*     */   public MapGenVillage() {}
/*     */   
/*     */   public MapGenVillage(Map<String, String> map) {
/*  32 */     this();
/*     */     
/*  34 */     for (Map.Entry<String, String> entry : map.entrySet()) {
/*     */       
/*  36 */       if (((String)entry.getKey()).equals("size")) {
/*     */         
/*  38 */         this.size = MathHelper.getInt(entry.getValue(), this.size, 0); continue;
/*     */       } 
/*  40 */       if (((String)entry.getKey()).equals("distance"))
/*     */       {
/*  42 */         this.distance = MathHelper.getInt(entry.getValue(), this.distance, 9);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStructureName() {
/*  49 */     return "Village";
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/*  54 */     int i = chunkX;
/*  55 */     int j = chunkZ;
/*     */     
/*  57 */     if (chunkX < 0)
/*     */     {
/*  59 */       chunkX -= this.distance - 1;
/*     */     }
/*     */     
/*  62 */     if (chunkZ < 0)
/*     */     {
/*  64 */       chunkZ -= this.distance - 1;
/*     */     }
/*     */     
/*  67 */     int k = chunkX / this.distance;
/*  68 */     int l = chunkZ / this.distance;
/*  69 */     Random random = this.worldObj.setRandomSeed(k, l, 10387312);
/*  70 */     k *= this.distance;
/*  71 */     l *= this.distance;
/*  72 */     k += random.nextInt(this.distance - 8);
/*  73 */     l += random.nextInt(this.distance - 8);
/*     */     
/*  75 */     if (i == k && j == l) {
/*     */       
/*  77 */       boolean flag = this.worldObj.getBiomeProvider().areBiomesViable(i * 16 + 8, j * 16 + 8, 0, VILLAGE_SPAWN_BIOMES);
/*     */       
/*  79 */       if (flag)
/*     */       {
/*  81 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  85 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos, boolean p_180706_3_) {
/*  90 */     this.worldObj = worldIn;
/*  91 */     return func_191069_a(worldIn, this, pos, this.distance, 8, 10387312, false, 100, p_180706_3_);
/*     */   }
/*     */ 
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/*  96 */     return new Start(this.worldObj, this.rand, chunkX, chunkZ, this.size);
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Start
/*     */     extends StructureStart
/*     */   {
/*     */     private boolean hasMoreThanTwoComponents;
/*     */ 
/*     */     
/*     */     public Start() {}
/*     */     
/*     */     public Start(World worldIn, Random rand, int x, int z, int size) {
/* 109 */       super(x, z);
/* 110 */       List<StructureVillagePieces.PieceWeight> list = StructureVillagePieces.getStructureVillageWeightedPieceList(rand, size);
/* 111 */       StructureVillagePieces.Start structurevillagepieces$start = new StructureVillagePieces.Start(worldIn.getBiomeProvider(), 0, rand, (x << 4) + 2, (z << 4) + 2, list, size);
/* 112 */       this.components.add(structurevillagepieces$start);
/* 113 */       structurevillagepieces$start.buildComponent(structurevillagepieces$start, this.components, rand);
/* 114 */       List<StructureComponent> list1 = structurevillagepieces$start.pendingRoads;
/* 115 */       List<StructureComponent> list2 = structurevillagepieces$start.pendingHouses;
/*     */       
/* 117 */       while (!list1.isEmpty() || !list2.isEmpty()) {
/*     */         
/* 119 */         if (list1.isEmpty()) {
/*     */           
/* 121 */           int i = rand.nextInt(list2.size());
/* 122 */           StructureComponent structurecomponent = list2.remove(i);
/* 123 */           structurecomponent.buildComponent(structurevillagepieces$start, this.components, rand);
/*     */           
/*     */           continue;
/*     */         } 
/* 127 */         int j = rand.nextInt(list1.size());
/* 128 */         StructureComponent structurecomponent2 = list1.remove(j);
/* 129 */         structurecomponent2.buildComponent(structurevillagepieces$start, this.components, rand);
/*     */       } 
/*     */ 
/*     */       
/* 133 */       updateBoundingBox();
/* 134 */       int k = 0;
/*     */       
/* 136 */       for (StructureComponent structurecomponent1 : this.components) {
/*     */         
/* 138 */         if (!(structurecomponent1 instanceof StructureVillagePieces.Road))
/*     */         {
/* 140 */           k++;
/*     */         }
/*     */       } 
/*     */       
/* 144 */       this.hasMoreThanTwoComponents = (k > 2);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSizeableStructure() {
/* 149 */       return this.hasMoreThanTwoComponents;
/*     */     }
/*     */ 
/*     */     
/*     */     public void writeToNBT(NBTTagCompound tagCompound) {
/* 154 */       super.writeToNBT(tagCompound);
/* 155 */       tagCompound.setBoolean("Valid", this.hasMoreThanTwoComponents);
/*     */     }
/*     */ 
/*     */     
/*     */     public void readFromNBT(NBTTagCompound tagCompound) {
/* 160 */       super.readFromNBT(tagCompound);
/* 161 */       this.hasMoreThanTwoComponents = tagCompound.getBoolean("Valid");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\MapGenVillage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */