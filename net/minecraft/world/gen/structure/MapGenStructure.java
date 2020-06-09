/*     */ package net.minecraft.world.gen.structure;
/*     */ 
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.util.Iterator;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.ChunkPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.chunk.ChunkPrimer;
/*     */ import net.minecraft.world.gen.MapGenBase;
/*     */ 
/*     */ public abstract class MapGenStructure extends MapGenBase {
/*     */   public MapGenStructure() {
/*  24 */     this.structureMap = (Long2ObjectMap<StructureStart>)new Long2ObjectOpenHashMap(1024);
/*     */   }
/*     */   
/*     */   private MapGenStructureData structureData;
/*     */   protected Long2ObjectMap<StructureStart> structureMap;
/*     */   
/*     */   public abstract String getStructureName();
/*     */   
/*     */   protected final synchronized void recursiveGenerate(World worldIn, final int chunkX, final int chunkZ, int p_180701_4_, int p_180701_5_, ChunkPrimer chunkPrimerIn) {
/*  33 */     initializeStructureData(worldIn);
/*     */     
/*  35 */     if (!this.structureMap.containsKey(ChunkPos.asLong(chunkX, chunkZ))) {
/*     */       
/*  37 */       this.rand.nextInt();
/*     */ 
/*     */       
/*     */       try {
/*  41 */         if (canSpawnStructureAtCoords(chunkX, chunkZ))
/*     */         {
/*  43 */           StructureStart structurestart = getStructureStart(chunkX, chunkZ);
/*  44 */           this.structureMap.put(ChunkPos.asLong(chunkX, chunkZ), structurestart);
/*     */           
/*  46 */           if (structurestart.isSizeableStructure())
/*     */           {
/*  48 */             setStructureStart(chunkX, chunkZ, structurestart);
/*     */           }
/*     */         }
/*     */       
/*  52 */       } catch (Throwable throwable) {
/*     */         
/*  54 */         CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Exception preparing structure feature");
/*  55 */         CrashReportCategory crashreportcategory = crashreport.makeCategory("Feature being prepared");
/*  56 */         crashreportcategory.setDetail("Is feature chunk", new ICrashReportDetail<String>()
/*     */             {
/*     */               public String call() throws Exception
/*     */               {
/*  60 */                 return MapGenStructure.this.canSpawnStructureAtCoords(chunkX, chunkZ) ? "True" : "False";
/*     */               }
/*     */             });
/*  63 */         crashreportcategory.addCrashSection("Chunk location", String.format("%d,%d", new Object[] { Integer.valueOf(chunkX), Integer.valueOf(chunkZ) }));
/*  64 */         crashreportcategory.setDetail("Chunk pos hash", new ICrashReportDetail<String>()
/*     */             {
/*     */               public String call() throws Exception
/*     */               {
/*  68 */                 return String.valueOf(ChunkPos.asLong(chunkX, chunkZ));
/*     */               }
/*     */             });
/*  71 */         crashreportcategory.setDetail("Structure type", new ICrashReportDetail<String>()
/*     */             {
/*     */               public String call() throws Exception
/*     */               {
/*  75 */                 return MapGenStructure.this.getClass().getCanonicalName();
/*     */               }
/*     */             });
/*  78 */         throw new ReportedException(crashreport);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized boolean generateStructure(World worldIn, Random randomIn, ChunkPos chunkCoord) {
/*  85 */     initializeStructureData(worldIn);
/*  86 */     int i = (chunkCoord.chunkXPos << 4) + 8;
/*  87 */     int j = (chunkCoord.chunkZPos << 4) + 8;
/*  88 */     boolean flag = false;
/*  89 */     ObjectIterator objectiterator = this.structureMap.values().iterator();
/*     */     
/*  91 */     while (objectiterator.hasNext()) {
/*     */       
/*  93 */       StructureStart structurestart = (StructureStart)objectiterator.next();
/*     */       
/*  95 */       if (structurestart.isSizeableStructure() && structurestart.isValidForPostProcess(chunkCoord) && structurestart.getBoundingBox().intersectsWith(i, j, i + 15, j + 15)) {
/*     */         
/*  97 */         structurestart.generateStructure(worldIn, randomIn, new StructureBoundingBox(i, j, i + 15, j + 15));
/*  98 */         structurestart.notifyPostProcessAt(chunkCoord);
/*  99 */         flag = true;
/* 100 */         setStructureStart(structurestart.getChunkPosX(), structurestart.getChunkPosZ(), structurestart);
/*     */       } 
/*     */     } 
/*     */     
/* 104 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInsideStructure(BlockPos pos) {
/* 109 */     if (this.worldObj == null)
/*     */     {
/* 111 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 115 */     initializeStructureData(this.worldObj);
/* 116 */     return (getStructureAt(pos) != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected StructureStart getStructureAt(BlockPos pos) {
/* 123 */     ObjectIterator objectiterator = this.structureMap.values().iterator();
/*     */ 
/*     */     
/* 126 */     while (objectiterator.hasNext()) {
/*     */       
/* 128 */       StructureStart structurestart = (StructureStart)objectiterator.next();
/*     */       
/* 130 */       if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().isVecInside((Vec3i)pos)) {
/*     */         
/* 132 */         Iterator<StructureComponent> iterator = structurestart.getComponents().iterator();
/*     */ 
/*     */ 
/*     */         
/* 136 */         while (iterator.hasNext()) {
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 141 */           StructureComponent structurecomponent = iterator.next();
/*     */           
/* 143 */           if (structurecomponent.getBoundingBox().isVecInside((Vec3i)pos))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */             
/* 149 */             return structurestart; } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPositionInStructure(World worldIn, BlockPos pos) {
/* 158 */     initializeStructureData(worldIn);
/* 159 */     ObjectIterator objectiterator = this.structureMap.values().iterator();
/*     */     
/* 161 */     while (objectiterator.hasNext()) {
/*     */       
/* 163 */       StructureStart structurestart = (StructureStart)objectiterator.next();
/*     */       
/* 165 */       if (structurestart.isSizeableStructure() && structurestart.getBoundingBox().isVecInside((Vec3i)pos))
/*     */       {
/* 167 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 171 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public abstract BlockPos getClosestStrongholdPos(World paramWorld, BlockPos paramBlockPos, boolean paramBoolean);
/*     */   
/*     */   protected void initializeStructureData(World worldIn) {
/* 179 */     if (this.structureData == null && worldIn != null) {
/*     */       
/* 181 */       this.structureData = (MapGenStructureData)worldIn.loadItemData(MapGenStructureData.class, getStructureName());
/*     */       
/* 183 */       if (this.structureData == null) {
/*     */         
/* 185 */         this.structureData = new MapGenStructureData(getStructureName());
/* 186 */         worldIn.setItemData(getStructureName(), this.structureData);
/*     */       }
/*     */       else {
/*     */         
/* 190 */         NBTTagCompound nbttagcompound = this.structureData.getTagCompound();
/*     */         
/* 192 */         for (String s : nbttagcompound.getKeySet()) {
/*     */           
/* 194 */           NBTBase nbtbase = nbttagcompound.getTag(s);
/*     */           
/* 196 */           if (nbtbase.getId() == 10) {
/*     */             
/* 198 */             NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbtbase;
/*     */             
/* 200 */             if (nbttagcompound1.hasKey("ChunkX") && nbttagcompound1.hasKey("ChunkZ")) {
/*     */               
/* 202 */               int i = nbttagcompound1.getInteger("ChunkX");
/* 203 */               int j = nbttagcompound1.getInteger("ChunkZ");
/* 204 */               StructureStart structurestart = MapGenStructureIO.getStructureStart(nbttagcompound1, worldIn);
/*     */               
/* 206 */               if (structurestart != null)
/*     */               {
/* 208 */                 this.structureMap.put(ChunkPos.asLong(i, j), structurestart);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void setStructureStart(int chunkX, int chunkZ, StructureStart start) {
/* 219 */     this.structureData.writeInstance(start.writeStructureComponentsToNBT(chunkX, chunkZ), chunkX, chunkZ);
/* 220 */     this.structureData.markDirty();
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract boolean canSpawnStructureAtCoords(int paramInt1, int paramInt2);
/*     */   
/*     */   protected abstract StructureStart getStructureStart(int paramInt1, int paramInt2);
/*     */   
/*     */   protected static BlockPos func_191069_a(World p_191069_0_, MapGenStructure p_191069_1_, BlockPos p_191069_2_, int p_191069_3_, int p_191069_4_, int p_191069_5_, boolean p_191069_6_, int p_191069_7_, boolean p_191069_8_) {
/* 229 */     int i = p_191069_2_.getX() >> 4;
/* 230 */     int j = p_191069_2_.getZ() >> 4;
/* 231 */     int k = 0;
/*     */     
/* 233 */     for (Random random = new Random(); k <= p_191069_7_; k++) {
/*     */       
/* 235 */       for (int l = -k; l <= k; l++) {
/*     */         
/* 237 */         boolean flag = !(l != -k && l != k);
/*     */         
/* 239 */         for (int i1 = -k; i1 <= k; i1++) {
/*     */           
/* 241 */           boolean flag1 = !(i1 != -k && i1 != k);
/*     */           
/* 243 */           if (flag || flag1) {
/*     */             
/* 245 */             int j1 = i + p_191069_3_ * l;
/* 246 */             int k1 = j + p_191069_3_ * i1;
/*     */             
/* 248 */             if (j1 < 0)
/*     */             {
/* 250 */               j1 -= p_191069_3_ - 1;
/*     */             }
/*     */             
/* 253 */             if (k1 < 0)
/*     */             {
/* 255 */               k1 -= p_191069_3_ - 1;
/*     */             }
/*     */             
/* 258 */             int l1 = j1 / p_191069_3_;
/* 259 */             int i2 = k1 / p_191069_3_;
/* 260 */             Random random1 = p_191069_0_.setRandomSeed(l1, i2, p_191069_5_);
/* 261 */             l1 *= p_191069_3_;
/* 262 */             i2 *= p_191069_3_;
/*     */             
/* 264 */             if (p_191069_6_) {
/*     */               
/* 266 */               l1 += (random1.nextInt(p_191069_3_ - p_191069_4_) + random1.nextInt(p_191069_3_ - p_191069_4_)) / 2;
/* 267 */               i2 += (random1.nextInt(p_191069_3_ - p_191069_4_) + random1.nextInt(p_191069_3_ - p_191069_4_)) / 2;
/*     */             }
/*     */             else {
/*     */               
/* 271 */               l1 += random1.nextInt(p_191069_3_ - p_191069_4_);
/* 272 */               i2 += random1.nextInt(p_191069_3_ - p_191069_4_);
/*     */             } 
/*     */             
/* 275 */             MapGenBase.func_191068_a(p_191069_0_.getSeed(), random, l1, i2);
/* 276 */             random.nextInt();
/*     */             
/* 278 */             if (p_191069_1_.canSpawnStructureAtCoords(l1, i2)) {
/*     */               
/* 280 */               if (!p_191069_8_ || !p_191069_0_.func_190526_b(l1, i2))
/*     */               {
/* 282 */                 return new BlockPos((l1 << 4) + 8, 64, (i2 << 4) + 8);
/*     */               }
/*     */             }
/* 285 */             else if (k == 0) {
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 292 */         if (k == 0) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 299 */     return null;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\structure\MapGenStructure.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */