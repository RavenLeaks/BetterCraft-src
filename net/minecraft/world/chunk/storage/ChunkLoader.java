/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.biome.Biome;
/*     */ import net.minecraft.world.biome.BiomeProvider;
/*     */ import net.minecraft.world.chunk.NibbleArray;
/*     */ 
/*     */ public class ChunkLoader
/*     */ {
/*     */   public static AnvilConverterData load(NBTTagCompound nbt) {
/*  15 */     int i = nbt.getInteger("xPos");
/*  16 */     int j = nbt.getInteger("zPos");
/*  17 */     AnvilConverterData chunkloader$anvilconverterdata = new AnvilConverterData(i, j);
/*  18 */     chunkloader$anvilconverterdata.blocks = nbt.getByteArray("Blocks");
/*  19 */     chunkloader$anvilconverterdata.data = new NibbleArrayReader(nbt.getByteArray("Data"), 7);
/*  20 */     chunkloader$anvilconverterdata.skyLight = new NibbleArrayReader(nbt.getByteArray("SkyLight"), 7);
/*  21 */     chunkloader$anvilconverterdata.blockLight = new NibbleArrayReader(nbt.getByteArray("BlockLight"), 7);
/*  22 */     chunkloader$anvilconverterdata.heightmap = nbt.getByteArray("HeightMap");
/*  23 */     chunkloader$anvilconverterdata.terrainPopulated = nbt.getBoolean("TerrainPopulated");
/*  24 */     chunkloader$anvilconverterdata.entities = nbt.getTagList("Entities", 10);
/*  25 */     chunkloader$anvilconverterdata.tileEntities = nbt.getTagList("TileEntities", 10);
/*  26 */     chunkloader$anvilconverterdata.tileTicks = nbt.getTagList("TileTicks", 10);
/*     */ 
/*     */     
/*     */     try {
/*  30 */       chunkloader$anvilconverterdata.lastUpdated = nbt.getLong("LastUpdate");
/*     */     }
/*  32 */     catch (ClassCastException var5) {
/*     */       
/*  34 */       chunkloader$anvilconverterdata.lastUpdated = nbt.getInteger("LastUpdate");
/*     */     } 
/*     */     
/*  37 */     return chunkloader$anvilconverterdata;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void convertToAnvilFormat(AnvilConverterData converterData, NBTTagCompound compound, BiomeProvider provider) {
/*  42 */     compound.setInteger("xPos", converterData.x);
/*  43 */     compound.setInteger("zPos", converterData.z);
/*  44 */     compound.setLong("LastUpdate", converterData.lastUpdated);
/*  45 */     int[] aint = new int[converterData.heightmap.length];
/*     */     
/*  47 */     for (int i = 0; i < converterData.heightmap.length; i++)
/*     */     {
/*  49 */       aint[i] = converterData.heightmap[i];
/*     */     }
/*     */     
/*  52 */     compound.setIntArray("HeightMap", aint);
/*  53 */     compound.setBoolean("TerrainPopulated", converterData.terrainPopulated);
/*  54 */     NBTTagList nbttaglist = new NBTTagList();
/*     */     
/*  56 */     for (int j = 0; j < 8; j++) {
/*     */       
/*  58 */       boolean flag = true;
/*     */       
/*  60 */       for (int k = 0; k < 16 && flag; k++) {
/*     */         
/*  62 */         for (int l = 0; l < 16 && flag; l++) {
/*     */           
/*  64 */           for (int i1 = 0; i1 < 16; i1++) {
/*     */             
/*  66 */             int j1 = k << 11 | i1 << 7 | l + (j << 4);
/*  67 */             int k1 = converterData.blocks[j1];
/*     */             
/*  69 */             if (k1 != 0) {
/*     */               
/*  71 */               flag = false;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*  78 */       if (!flag) {
/*     */         
/*  80 */         byte[] abyte1 = new byte[4096];
/*  81 */         NibbleArray nibblearray = new NibbleArray();
/*  82 */         NibbleArray nibblearray1 = new NibbleArray();
/*  83 */         NibbleArray nibblearray2 = new NibbleArray();
/*     */         
/*  85 */         for (int j3 = 0; j3 < 16; j3++) {
/*     */           
/*  87 */           for (int l1 = 0; l1 < 16; l1++) {
/*     */             
/*  89 */             for (int i2 = 0; i2 < 16; i2++) {
/*     */               
/*  91 */               int j2 = j3 << 11 | i2 << 7 | l1 + (j << 4);
/*  92 */               int k2 = converterData.blocks[j2];
/*  93 */               abyte1[l1 << 8 | i2 << 4 | j3] = (byte)(k2 & 0xFF);
/*  94 */               nibblearray.set(j3, l1, i2, converterData.data.get(j3, l1 + (j << 4), i2));
/*  95 */               nibblearray1.set(j3, l1, i2, converterData.skyLight.get(j3, l1 + (j << 4), i2));
/*  96 */               nibblearray2.set(j3, l1, i2, converterData.blockLight.get(j3, l1 + (j << 4), i2));
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 101 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 102 */         nbttagcompound.setByte("Y", (byte)(j & 0xFF));
/* 103 */         nbttagcompound.setByteArray("Blocks", abyte1);
/* 104 */         nbttagcompound.setByteArray("Data", nibblearray.getData());
/* 105 */         nbttagcompound.setByteArray("SkyLight", nibblearray1.getData());
/* 106 */         nbttagcompound.setByteArray("BlockLight", nibblearray2.getData());
/* 107 */         nbttaglist.appendTag((NBTBase)nbttagcompound);
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     compound.setTag("Sections", (NBTBase)nbttaglist);
/* 112 */     byte[] abyte = new byte[256];
/* 113 */     BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();
/*     */     
/* 115 */     for (int l2 = 0; l2 < 16; l2++) {
/*     */       
/* 117 */       for (int i3 = 0; i3 < 16; i3++) {
/*     */         
/* 119 */         blockpos$mutableblockpos.setPos(converterData.x << 4 | l2, 0, converterData.z << 4 | i3);
/* 120 */         abyte[i3 << 4 | l2] = (byte)(Biome.getIdForBiome(provider.getBiome((BlockPos)blockpos$mutableblockpos, Biomes.DEFAULT)) & 0xFF);
/*     */       } 
/*     */     } 
/*     */     
/* 124 */     compound.setByteArray("Biomes", abyte);
/* 125 */     compound.setTag("Entities", (NBTBase)converterData.entities);
/* 126 */     compound.setTag("TileEntities", (NBTBase)converterData.tileEntities);
/*     */     
/* 128 */     if (converterData.tileTicks != null)
/*     */     {
/* 130 */       compound.setTag("TileTicks", (NBTBase)converterData.tileTicks);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static class AnvilConverterData
/*     */   {
/*     */     public long lastUpdated;
/*     */     public boolean terrainPopulated;
/*     */     public byte[] heightmap;
/*     */     public NibbleArrayReader blockLight;
/*     */     public NibbleArrayReader skyLight;
/*     */     public NibbleArrayReader data;
/*     */     public byte[] blocks;
/*     */     public NBTTagList entities;
/*     */     public NBTTagList tileEntities;
/*     */     public NBTTagList tileTicks;
/*     */     public final int x;
/*     */     public final int z;
/*     */     
/*     */     public AnvilConverterData(int xIn, int zIn) {
/* 151 */       this.x = xIn;
/* 152 */       this.z = zIn;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\chunk\storage\ChunkLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */