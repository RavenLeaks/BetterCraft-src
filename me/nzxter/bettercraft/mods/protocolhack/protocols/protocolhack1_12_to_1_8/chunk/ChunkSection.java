/*     */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.chunk;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class ChunkSection
/*     */ {
/*     */   public static final int SIZE = 4096;
/*     */   public static final int LIGHT_LENGTH = 2048;
/*  13 */   private List<Integer> palette = new ArrayList<>();
/*  14 */   private Map<Integer, Integer> inversePalette = new HashMap<>();
/*  15 */   private final int[] blocks = new int[4096];
/*  16 */   private NibbleArray blockLight = new NibbleArray(4096);
/*     */   private NibbleArray skyLight;
/*     */   private int nonAirBlocksCount;
/*     */   
/*     */   public ChunkSection() {
/*  21 */     addPaletteEntry(0);
/*     */   }
/*     */   
/*     */   public void setBlock(int x, int y, int z, int type, int data2) {
/*  25 */     setFlatBlock(index(x, y, z), type << 4 | data2 & 0xF);
/*     */   }
/*     */   
/*     */   public void setFlatBlock(int x, int y, int z, int type) {
/*  29 */     setFlatBlock(index(x, y, z), type);
/*     */   }
/*     */   
/*     */   public int getBlockId(int x, int y, int z) {
/*  33 */     return getFlatBlock(x, y, z) >> 4;
/*     */   }
/*     */   
/*     */   public int getBlockData(int x, int y, int z) {
/*  37 */     return getFlatBlock(x, y, z) & 0xF;
/*     */   }
/*     */   
/*     */   public int getFlatBlock(int x, int y, int z) {
/*  41 */     int index2 = this.blocks[index(x, y, z)];
/*  42 */     return ((Integer)this.palette.get(index2)).intValue();
/*     */   }
/*     */   
/*     */   public int getFlatBlock(int idx) {
/*  46 */     int index2 = this.blocks[idx];
/*  47 */     return ((Integer)this.palette.get(index2)).intValue();
/*     */   }
/*     */   
/*     */   public void setBlock(int idx, int type, int data2) {
/*  51 */     setFlatBlock(idx, type << 4 | data2 & 0xF);
/*     */   }
/*     */   
/*     */   public void setPaletteIndex(int idx, int index2) {
/*  55 */     this.blocks[idx] = index2;
/*     */   }
/*     */   
/*     */   public int getPaletteIndex(int idx) {
/*  59 */     return this.blocks[idx];
/*     */   }
/*     */   
/*     */   public int getPaletteSize() {
/*  63 */     return this.palette.size();
/*     */   }
/*     */   
/*     */   public int getPaletteEntry(int index2) {
/*  67 */     if (index2 < 0 || index2 >= this.palette.size()) {
/*  68 */       throw new IndexOutOfBoundsException();
/*     */     }
/*  70 */     return ((Integer)this.palette.get(index2)).intValue();
/*     */   }
/*     */   
/*     */   public void setPaletteEntry(int index2, int id) {
/*  74 */     if (index2 < 0 || index2 >= this.palette.size()) {
/*  75 */       throw new IndexOutOfBoundsException();
/*     */     }
/*  77 */     int oldId = ((Integer)this.palette.set(index2, Integer.valueOf(id))).intValue();
/*  78 */     if (oldId == id) {
/*     */       return;
/*     */     }
/*  81 */     this.inversePalette.put(Integer.valueOf(id), Integer.valueOf(index2));
/*  82 */     if (((Integer)this.inversePalette.get(Integer.valueOf(oldId))).intValue() == index2) {
/*  83 */       this.inversePalette.remove(Integer.valueOf(oldId));
/*  84 */       for (int i = 0; i < this.palette.size(); ) {
/*  85 */         if (((Integer)this.palette.get(i)).intValue() != oldId) { i++; continue; }
/*  86 */          this.inversePalette.put(Integer.valueOf(oldId), Integer.valueOf(i));
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void replacePaletteEntry(int oldId, int newId) {
/*  93 */     Integer index2 = this.inversePalette.remove(Integer.valueOf(oldId));
/*  94 */     if (index2 == null) {
/*     */       return;
/*     */     }
/*  97 */     this.inversePalette.put(Integer.valueOf(newId), index2);
/*  98 */     for (int i = 0; i < this.palette.size(); i++) {
/*  99 */       if (((Integer)this.palette.get(i)).intValue() == oldId)
/* 100 */         this.palette.set(i, Integer.valueOf(newId)); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addPaletteEntry(int id) {
/* 105 */     this.inversePalette.put(Integer.valueOf(id), Integer.valueOf(this.palette.size()));
/* 106 */     this.palette.add(Integer.valueOf(id));
/*     */   }
/*     */   
/*     */   public void clearPalette() {
/* 110 */     this.palette.clear();
/* 111 */     this.inversePalette.clear();
/*     */   }
/*     */   
/*     */   public void setFlatBlock(int idx, int id) {
/* 115 */     Integer index2 = this.inversePalette.get(Integer.valueOf(id));
/* 116 */     if (index2 == null) {
/* 117 */       index2 = Integer.valueOf(this.palette.size());
/* 118 */       this.palette.add(Integer.valueOf(id));
/* 119 */       this.inversePalette.put(Integer.valueOf(id), index2);
/*     */     } 
/* 121 */     this.blocks[idx] = index2.intValue();
/*     */   }
/*     */   
/*     */   public void setBlockLight(byte[] data2) {
/* 125 */     if (data2.length != 2048) {
/* 126 */       throw new IllegalArgumentException("Data length != 2048");
/*     */     }
/* 128 */     if (this.blockLight == null) {
/* 129 */       this.blockLight = new NibbleArray(data2);
/*     */     } else {
/* 131 */       this.blockLight.setHandle(data2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setSkyLight(byte[] data2) {
/* 136 */     if (data2.length != 2048) {
/* 137 */       throw new IllegalArgumentException("Data length != 2048");
/*     */     }
/* 139 */     if (this.skyLight == null) {
/* 140 */       this.skyLight = new NibbleArray(data2);
/*     */     } else {
/* 142 */       this.skyLight.setHandle(data2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte[] getBlockLight() {
/* 147 */     return (this.blockLight == null) ? null : this.blockLight.getHandle();
/*     */   }
/*     */   
/*     */   public byte[] getSkyLight() {
/* 151 */     return (this.skyLight == null) ? null : this.skyLight.getHandle();
/*     */   }
/*     */   
/*     */   public void readBlockLight(ByteBuf input2) {
/* 155 */     if (this.blockLight == null) {
/* 156 */       this.blockLight = new NibbleArray(4096);
/*     */     }
/* 158 */     input2.readBytes(this.blockLight.getHandle());
/*     */   }
/*     */   
/*     */   public void readSkyLight(ByteBuf input2) {
/* 162 */     if (this.skyLight == null) {
/* 163 */       this.skyLight = new NibbleArray(4096);
/*     */     }
/* 165 */     input2.readBytes(this.skyLight.getHandle());
/*     */   }
/*     */   
/*     */   private static int index(int x, int y, int z) {
/* 169 */     return y << 8 | z << 4 | x;
/*     */   }
/*     */   
/*     */   public void writeBlockLight(ByteBuf output) {
/* 173 */     output.writeBytes(this.blockLight.getHandle());
/*     */   }
/*     */   
/*     */   public void writeSkyLight(ByteBuf output) {
/* 177 */     output.writeBytes(this.skyLight.getHandle());
/*     */   }
/*     */   
/*     */   public boolean hasSkyLight() {
/* 181 */     return (this.skyLight != null);
/*     */   }
/*     */   
/*     */   public boolean hasBlockLight() {
/* 185 */     return (this.blockLight != null);
/*     */   }
/*     */   
/*     */   public int getNonAirBlocksCount() {
/* 189 */     return this.nonAirBlocksCount;
/*     */   }
/*     */   
/*     */   public void setNonAirBlocksCount(int nonAirBlocksCount) {
/* 193 */     this.nonAirBlocksCount = nonAirBlocksCount;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_8\chunk\ChunkSection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */