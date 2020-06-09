/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Random;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MapGenStronghold
/*     */   extends MapGenStructure
/*     */ {
/*  29 */   private ChunkPos[] structureCoords = new ChunkPos[128];
/*  30 */   private double distance = 32.0D;
/*  31 */   private int spread = 3;
/*  32 */   private final List<Biome> allowedBiomes = Lists.newArrayList();
/*     */   public MapGenStronghold() {
/*  34 */     for (Biome biome : Biome.REGISTRY) {
/*     */       
/*  36 */       if (biome != null && biome.getBaseHeight() > 0.0F)
/*     */       {
/*  38 */         this.allowedBiomes.add(biome); } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean ranBiomeCheck;
/*     */   
/*     */   public MapGenStronghold(Map<String, String> p_i2068_1_) {
/*  45 */     this();
/*     */     
/*  47 */     for (Map.Entry<String, String> entry : p_i2068_1_.entrySet()) {
/*     */       
/*  49 */       if (((String)entry.getKey()).equals("distance")) {
/*     */         
/*  51 */         this.distance = MathHelper.getDouble(entry.getValue(), this.distance, 1.0D); continue;
/*     */       } 
/*  53 */       if (((String)entry.getKey()).equals("count")) {
/*     */         
/*  55 */         this.structureCoords = new ChunkPos[MathHelper.getInt((String)entry.getValue(), this.structureCoords.length, 1)]; continue;
/*     */       } 
/*  57 */       if (((String)entry.getKey()).equals("spread"))
/*     */       {
/*  59 */         this.spread = MathHelper.getInt(entry.getValue(), this.spread, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getStructureName() {
/*  66 */     return "Stronghold";
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getClosestStrongholdPos(World worldIn, BlockPos pos, boolean p_180706_3_) {
/*  71 */     if (!this.ranBiomeCheck) {
/*     */       
/*  73 */       generatePositions();
/*  74 */       this.ranBiomeCheck = true;
/*     */     } 
/*     */     
/*  77 */     BlockPos blockpos = null;
/*  78 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(0, 0, 0);
/*  79 */     double d0 = Double.MAX_VALUE; byte b; int i;
/*     */     ChunkPos[] arrayOfChunkPos;
/*  81 */     for (i = (arrayOfChunkPos = this.structureCoords).length, b = 0; b < i; ) { ChunkPos chunkpos = arrayOfChunkPos[b];
/*     */       
/*  83 */       blockpos$mutableblockpos.setPos((chunkpos.chunkXPos << 4) + 8, 32, (chunkpos.chunkZPos << 4) + 8);
/*  84 */       double d1 = blockpos$mutableblockpos.distanceSq((Vec3i)pos);
/*     */       
/*  86 */       if (blockpos == null) {
/*     */         
/*  88 */         blockpos = new BlockPos((Vec3i)blockpos$mutableblockpos);
/*  89 */         d0 = d1;
/*     */       }
/*  91 */       else if (d1 < d0) {
/*     */         
/*  93 */         blockpos = new BlockPos((Vec3i)blockpos$mutableblockpos);
/*  94 */         d0 = d1;
/*     */       } 
/*     */       b++; }
/*     */     
/*  98 */     return blockpos;
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
/* 103 */     if (!this.ranBiomeCheck) {
/*     */       
/* 105 */       generatePositions();
/* 106 */       this.ranBiomeCheck = true;
/*     */     }  byte b; int i;
/*     */     ChunkPos[] arrayOfChunkPos;
/* 109 */     for (i = (arrayOfChunkPos = this.structureCoords).length, b = 0; b < i; ) { ChunkPos chunkpos = arrayOfChunkPos[b];
/*     */       
/* 111 */       if (chunkX == chunkpos.chunkXPos && chunkZ == chunkpos.chunkZPos)
/*     */       {
/* 113 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/* 117 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private void generatePositions() {
/* 122 */     initializeStructureData(this.worldObj);
/* 123 */     int i = 0;
/* 124 */     ObjectIterator lvt_2_1_ = this.structureMap.values().iterator();
/*     */     
/* 126 */     while (lvt_2_1_.hasNext()) {
/*     */       
/* 128 */       StructureStart structurestart = (StructureStart)lvt_2_1_.next();
/*     */       
/* 130 */       if (i < this.structureCoords.length)
/*     */       {
/* 132 */         this.structureCoords[i++] = new ChunkPos(structurestart.getChunkPosX(), structurestart.getChunkPosZ());
/*     */       }
/*     */     } 
/*     */     
/* 136 */     Random random = new Random();
/* 137 */     random.setSeed(this.worldObj.getSeed());
/* 138 */     double d1 = random.nextDouble() * Math.PI * 2.0D;
/* 139 */     int j = 0;
/* 140 */     int k = 0;
/* 141 */     int l = this.structureMap.size();
/*     */     
/* 143 */     if (l < this.structureCoords.length)
/*     */     {
/* 145 */       for (int i1 = 0; i1 < this.structureCoords.length; i1++) {
/*     */         
/* 147 */         double d0 = 4.0D * this.distance + this.distance * j * 6.0D + (random.nextDouble() - 0.5D) * this.distance * 2.5D;
/* 148 */         int j1 = (int)Math.round(Math.cos(d1) * d0);
/* 149 */         int k1 = (int)Math.round(Math.sin(d1) * d0);
/* 150 */         BlockPos blockpos = this.worldObj.getBiomeProvider().findBiomePosition((j1 << 4) + 8, (k1 << 4) + 8, 112, this.allowedBiomes, random);
/*     */         
/* 152 */         if (blockpos != null) {
/*     */           
/* 154 */           j1 = blockpos.getX() >> 4;
/* 155 */           k1 = blockpos.getZ() >> 4;
/*     */         } 
/*     */         
/* 158 */         if (i1 >= l)
/*     */         {
/* 160 */           this.structureCoords[i1] = new ChunkPos(j1, k1);
/*     */         }
/*     */         
/* 163 */         d1 += 6.283185307179586D / this.spread;
/* 164 */         k++;
/*     */         
/* 166 */         if (k == this.spread) {
/*     */           
/* 168 */           j++;
/* 169 */           k = 0;
/* 170 */           this.spread += 2 * this.spread / (j + 1);
/* 171 */           this.spread = Math.min(this.spread, this.structureCoords.length - i1);
/* 172 */           d1 += random.nextDouble() * Math.PI * 2.0D;
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected StructureStart getStructureStart(int chunkX, int chunkZ) {
/* 182 */     for (Start mapgenstronghold$start = new Start(this.worldObj, this.rand, chunkX, chunkZ); mapgenstronghold$start.getComponents().isEmpty() || ((StructureStrongholdPieces.Stairs2)mapgenstronghold$start.getComponents().get(0)).strongholdPortalRoom == null; mapgenstronghold$start = new Start(this.worldObj, this.rand, chunkX, chunkZ));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     return mapgenstronghold$start;
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
/* 198 */       super(chunkX, chunkZ);
/* 199 */       StructureStrongholdPieces.prepareStructurePieces();
/* 200 */       StructureStrongholdPieces.Stairs2 structurestrongholdpieces$stairs2 = new StructureStrongholdPieces.Stairs2(0, random, (chunkX << 4) + 2, (chunkZ << 4) + 2);
/* 201 */       this.components.add(structurestrongholdpieces$stairs2);
/* 202 */       structurestrongholdpieces$stairs2.buildComponent(structurestrongholdpieces$stairs2, this.components, random);
/* 203 */       List<StructureComponent> list = structurestrongholdpieces$stairs2.pendingChildren;
/*     */       
/* 205 */       while (!list.isEmpty()) {
/*     */         
/* 207 */         int i = random.nextInt(list.size());
/* 208 */         StructureComponent structurecomponent = list.remove(i);
/* 209 */         structurecomponent.buildComponent(structurestrongholdpieces$stairs2, this.components, random);
/*     */       } 
/*     */       
/* 212 */       updateBoundingBox();
/* 213 */       markAvailableHeight(worldIn, random, 10);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\MapGenStronghold.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */