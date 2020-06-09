/*     */ package me.nzxter.bettercraft.mods.protocolhack.protocols.protocolhack1_12_to_1_8.chunk;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import java.nio.ByteOrder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.BitSet;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChunkWrapper
/*     */ {
/*     */   private static final int GLOBAL_PALETTE = 13;
/*     */   public static final int SECTION_COUNT = 16;
/*     */   private static final int SECTION_SIZE = 16;
/*     */   private static final int BIOME_DATA_LENGTH = 25;
/*     */   
/*     */   public static IChunk readChunk_107(PacketBuffer input2) {
/*  25 */     int chunkX = input2.readInt();
/*  26 */     int chunkZ = input2.readInt();
/*  27 */     boolean groundUp = input2.readBoolean();
/*  28 */     int primaryBitmask = input2.readVarIntFromBuffer();
/*  29 */     int size2 = input2.readVarIntFromBuffer();
/*  30 */     BitSet usedSections = new BitSet(16);
/*  31 */     ChunkSection[] sections = new ChunkSection[16]; int i;
/*  32 */     for (i = 0; i < 16; i++) {
/*  33 */       if ((primaryBitmask & 1 << i) != 0)
/*  34 */         usedSections.set(i); 
/*     */     } 
/*  36 */     for (i = 0; i < 16; i++) {
/*  37 */       if (usedSections.get(i))
/*     */         
/*     */         try {
/*  40 */           ChunkSection section = readChunkSection_107(input2);
/*  41 */           sections[i].readBlockLight((ByteBuf)input2);
/*  42 */           section.readSkyLight((ByteBuf)input2);
/*     */         
/*     */         }
/*  45 */         catch (Throwable t) {
/*  46 */           t.printStackTrace();
/*     */         }  
/*     */     } 
/*  49 */     int[] biomeData = groundUp ? new int[256] : null, arrn = biomeData;
/*  50 */     if (groundUp) {
/*     */       try {
/*  52 */         for (int j = 0; j < 256; j++) {
/*  53 */           biomeData[j] = input2.readByte() & 0xFF;
/*     */         }
/*     */       }
/*  56 */       catch (Throwable throwable) {}
/*     */     }
/*     */ 
/*     */     
/*  60 */     return new BaseChunk(chunkX, chunkZ, groundUp, primaryBitmask, sections, biomeData, new ArrayList<>());
/*     */   }
/*     */ 
/*     */   
/*     */   private static ChunkSection readChunkSection_107(PacketBuffer buffer) throws Exception {
/*  65 */     ChunkSection chunkSection = new ChunkSection();
/*  66 */     int originalBitsPerBlock = buffer.readUnsignedByte(), bitsPerBlock = originalBitsPerBlock;
/*  67 */     long maxEntryValue = (1L << bitsPerBlock) - 1L;
/*  68 */     if (bitsPerBlock == 0) {
/*  69 */       bitsPerBlock = 13;
/*     */     }
/*  71 */     if (bitsPerBlock < 4) {
/*  72 */       bitsPerBlock = 4;
/*     */     }
/*  74 */     if (bitsPerBlock > 8) {
/*  75 */       bitsPerBlock = 13;
/*     */     }
/*  77 */     int paletteLength = buffer.readVarIntFromBuffer();
/*  78 */     chunkSection.clearPalette();
/*  79 */     for (int i = 0; i < paletteLength; i++) {
/*  80 */       if (bitsPerBlock != 13) {
/*  81 */         chunkSection.addPaletteEntry(buffer.readVarIntFromBuffer());
/*     */       } else {
/*     */         
/*  84 */         buffer.readVarIntFromBuffer();
/*     */       } 
/*  86 */     }  long[] blockData = new long[buffer.readVarIntFromBuffer()];
/*  87 */     if (blockData.length > 0) {
/*     */       
/*  89 */       int expectedLength = (int)Math.ceil((4096 * bitsPerBlock) / 64.0D);
/*  90 */       if (blockData.length != expectedLength)
/*  91 */         throw new IllegalStateException("Block data length (" + blockData.length + ") does not match expected length (" + expectedLength + ")! bitsPerBlock=" + bitsPerBlock + ", originalBitsPerBlock=" + originalBitsPerBlock); 
/*     */       int j;
/*  93 */       for (j = 0; j < blockData.length; j++) {
/*  94 */         blockData[j] = buffer.readLong();
/*     */       }
/*  96 */       for (j = 0; j < 4096; j++) {
/*     */         
/*  98 */         int val, bitIndex = j * bitsPerBlock;
/*  99 */         int startIndex = bitIndex / 64;
/* 100 */         int endIndex = ((j + 1) * bitsPerBlock - 1) / 64;
/* 101 */         int startBitSubIndex = bitIndex % 64;
/* 102 */         if (startIndex == endIndex) {
/* 103 */           val = (int)(blockData[startIndex] >>> startBitSubIndex & maxEntryValue);
/*     */         } else {
/* 105 */           int endBitSubIndex = 64 - startBitSubIndex;
/* 106 */           val = (int)((blockData[startIndex] >>> startBitSubIndex | blockData[endIndex] << endBitSubIndex) & maxEntryValue);
/*     */         } 
/* 108 */         if (bitsPerBlock == 13) {
/* 109 */           chunkSection.setBlock(j, val >> 4, val & 0xF);
/*     */         } else {
/*     */           
/* 112 */           chunkSection.setPaletteIndex(j, val);
/*     */         } 
/*     */       } 
/* 115 */     }  return chunkSection;
/*     */   }
/*     */   
/*     */   public static void writeChunk_107(IChunk chunk, PacketBuffer buffer) {
/* 119 */     buffer.writeInt(chunk.getChunkX());
/* 120 */     buffer.writeInt(chunk.getChunkZ());
/* 121 */     buffer.writeBoolean(chunk.isGroundUp());
/* 122 */     buffer.writeVarIntToBuffer(chunk.getBitmask());
/* 123 */     ByteBuf buf = buffer.alloc().buffer();
/*     */     try {
/* 125 */       for (int i = 0; i < 16; i++) {
/* 126 */         ChunkSection section = chunk.getChunkSections()[i];
/* 127 */         if (section != null) {
/* 128 */           writeChunkSection_107(new PacketBuffer(buf), section);
/* 129 */           section.writeBlockLight(buf);
/* 130 */           if (section.hasSkyLight())
/* 131 */             section.writeSkyLight(buf); 
/*     */         } 
/* 133 */       }  buf.readerIndex(0);
/* 134 */       buffer.writeVarIntToBuffer(buf.readableBytes() + (chunk.hasBiomeData() ? 256 : 0));
/* 135 */       buffer.writeBytes(buf);
/*     */     } finally {
/*     */       
/* 138 */       buf.release();
/*     */     } 
/* 140 */     if (chunk.hasBiomeData()) {
/* 141 */       byte b; int i; int[] arrayOfInt; for (i = (arrayOfInt = chunk.getBiomeData()).length, b = 0; b < i; ) { int biome = arrayOfInt[b];
/* 142 */         buffer.writeByte((byte)biome);
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   private static void writeChunkSection_107(PacketBuffer buffer, ChunkSection chunkSection) {
/* 148 */     int bitsPerBlock = 4;
/* 149 */     while (chunkSection.getPaletteSize() > 1 << bitsPerBlock) {
/* 150 */       bitsPerBlock++;
/*     */     }
/* 152 */     if (bitsPerBlock > 8) {
/* 153 */       bitsPerBlock = 13;
/*     */     }
/* 155 */     long maxEntryValue = (1L << bitsPerBlock) - 1L;
/* 156 */     buffer.writeByte(bitsPerBlock);
/* 157 */     if (bitsPerBlock != 13) {
/* 158 */       buffer.writeVarIntToBuffer(chunkSection.getPaletteSize());
/* 159 */       for (int j = 0; j < chunkSection.getPaletteSize(); j++) {
/* 160 */         buffer.writeVarIntToBuffer(chunkSection.getPaletteEntry(j));
/*     */       }
/*     */     } else {
/* 163 */       buffer.writeVarIntToBuffer(0);
/*     */     } 
/* 165 */     int length = (int)Math.ceil((4096 * bitsPerBlock) / 64.0D);
/* 166 */     buffer.writeVarIntToBuffer(length);
/* 167 */     long[] data2 = new long[length];
/* 168 */     for (int index2 = 0; index2 < 4096; index2++) {
/* 169 */       int value2 = (bitsPerBlock == 13) ? chunkSection.getFlatBlock(index2) : chunkSection.getPaletteIndex(index2);
/* 170 */       int bitIndex = index2 * bitsPerBlock;
/* 171 */       int startIndex = bitIndex / 64;
/* 172 */       int endIndex = ((index2 + 1) * bitsPerBlock - 1) / 64;
/* 173 */       int startBitSubIndex = bitIndex % 64;
/* 174 */       data2[startIndex] = data2[startIndex] & (maxEntryValue << startBitSubIndex ^ 0xFFFFFFFFFFFFFFFFL) | (value2 & maxEntryValue) << startBitSubIndex;
/* 175 */       if (startIndex != endIndex) {
/* 176 */         int endBitSubIndex = 64 - startBitSubIndex;
/* 177 */         data2[endIndex] = data2[endIndex] >>> endBitSubIndex << endBitSubIndex | (value2 & maxEntryValue) >> endBitSubIndex;
/*     */       } 
/* 179 */     }  byte b; int i; long[] arrayOfLong1; for (i = (arrayOfLong1 = data2).length, b = 0; b < i; ) { long l = arrayOfLong1[b];
/* 180 */       buffer.writeLong(l);
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   private static IChunk readChunk_110(PacketBuffer input2) throws Exception {
/* 187 */     int chunkX = input2.readInt();
/* 188 */     int chunkZ = input2.readInt();
/* 189 */     boolean groundUp = input2.readBoolean();
/* 190 */     int primaryBitmask = input2.readVarIntFromBuffer();
/* 191 */     BitSet usedSections = new BitSet(16);
/* 192 */     ChunkSection[] sections = new ChunkSection[16]; int i;
/* 193 */     for (i = 0; i < 16; i++) {
/* 194 */       if ((primaryBitmask & 1 << i) != 0)
/* 195 */         usedSections.set(i); 
/*     */     } 
/* 197 */     for (i = 0; i < 16; i++) {
/*     */       
/* 199 */       if (usedSections.get(i)) {
/* 200 */         ChunkSection section = readChunkSection_107(input2);
/* 201 */         sections[i].readBlockLight((ByteBuf)input2);
/* 202 */         section.readSkyLight((ByteBuf)input2);
/*     */       } 
/* 204 */     }  int[] biomeData = groundUp ? new int[256] : null, arrn = biomeData;
/* 205 */     if (groundUp) {
/* 206 */       for (int j = 0; j < 256; j++) {
/* 207 */         biomeData[j] = input2.readByte() & 0xFF;
/*     */       }
/*     */     }
/* 210 */     ArrayList<NBTTagCompound> nbtData = new ArrayList<>();
/* 211 */     int count2 = input2.readVarIntFromBuffer();
/* 212 */     for (int i2 = 0; i2 < count2; i2++) {
/* 213 */       nbtData.add(input2.readNBTTagCompoundFromBuffer());
/*     */     }
/* 215 */     if (input2.readableBytes() > 0) {
/* 216 */       byte[] array = new byte[input2.readableBytes()];
/* 217 */       input2.readBytes(array);
/*     */     } 
/* 219 */     return new BaseChunk(chunkX, chunkZ, groundUp, primaryBitmask, sections, biomeData, nbtData);
/*     */   }
/*     */   
/*     */   public static void writeChunkSection_110(PacketBuffer buffer, ChunkSection chunkSection) throws Exception {
/* 223 */     int bitsPerBlock = 4;
/* 224 */     while (chunkSection.getPaletteSize() > 1 << bitsPerBlock) {
/* 225 */       bitsPerBlock++;
/*     */     }
/* 227 */     if (bitsPerBlock > 8) {
/* 228 */       bitsPerBlock = 13;
/*     */     }
/* 230 */     long maxEntryValue = (1L << bitsPerBlock) - 1L;
/* 231 */     buffer.writeByte(bitsPerBlock);
/* 232 */     if (bitsPerBlock != 13) {
/* 233 */       buffer.writeVarIntToBuffer(chunkSection.getPaletteSize());
/* 234 */       for (int j = 0; j < chunkSection.getPaletteSize(); j++) {
/* 235 */         buffer.writeVarIntToBuffer(chunkSection.getPaletteEntry(j));
/*     */       }
/*     */     } else {
/* 238 */       buffer.writeVarIntToBuffer(0);
/*     */     } 
/* 240 */     int length = (int)Math.ceil((4096 * bitsPerBlock) / 64.0D);
/* 241 */     buffer.writeVarIntToBuffer(length);
/* 242 */     long[] data2 = new long[length];
/* 243 */     for (int index2 = 0; index2 < 4096; index2++) {
/* 244 */       int value2 = (bitsPerBlock == 13) ? chunkSection.getFlatBlock(index2) : chunkSection.getPaletteIndex(index2);
/* 245 */       int bitIndex = index2 * bitsPerBlock;
/* 246 */       int startIndex = bitIndex / 64;
/* 247 */       int endIndex = ((index2 + 1) * bitsPerBlock - 1) / 64;
/* 248 */       int startBitSubIndex = bitIndex % 64;
/* 249 */       data2[startIndex] = data2[startIndex] & (maxEntryValue << startBitSubIndex ^ 0xFFFFFFFFFFFFFFFFL) | (value2 & maxEntryValue) << startBitSubIndex;
/* 250 */       if (startIndex != endIndex) {
/* 251 */         int endBitSubIndex = 64 - startBitSubIndex;
/* 252 */         data2[endIndex] = data2[endIndex] >>> endBitSubIndex << endBitSubIndex | (value2 & maxEntryValue) >> endBitSubIndex;
/*     */       } 
/* 254 */     }  byte b; int i; long[] arrayOfLong1; for (i = (arrayOfLong1 = data2).length, b = 0; b < i; ) { long l = arrayOfLong1[b];
/* 255 */       buffer.writeLong(l);
/*     */       b++; }
/*     */   
/*     */   }
/*     */   public static void writeChunk_110(PacketBuffer output, IChunk chunk) throws Exception {
/* 260 */     output.writeInt(chunk.getChunkX());
/* 261 */     output.writeInt(chunk.getChunkZ());
/* 262 */     output.writeBoolean(chunk.isGroundUp());
/* 263 */     output.writeVarIntToBuffer(chunk.getBitmask());
/* 264 */     PacketBuffer buf = new PacketBuffer(output.alloc().buffer());
/*     */     try {
/* 266 */       for (int j = 0; j < 16; j++) {
/* 267 */         ChunkSection section = chunk.getChunkSections()[j];
/* 268 */         if (section != null) {
/* 269 */           writeChunkSection_110(buf, section);
/* 270 */           section.writeBlockLight((ByteBuf)buf);
/* 271 */           if (section.hasSkyLight())
/* 272 */             section.writeSkyLight((ByteBuf)buf); 
/*     */         } 
/* 274 */       }  buf.readerIndex(0);
/* 275 */       output.writeVarIntToBuffer(buf.readableBytes() + (chunk.hasBiomeData() ? 256 : 0));
/* 276 */       output.writeBytes((ByteBuf)buf);
/*     */     } finally {
/*     */       
/* 279 */       buf.release();
/*     */     } 
/* 281 */     if (chunk.hasBiomeData()) {
/* 282 */       byte b; int j; int[] arrayOfInt; for (j = (arrayOfInt = chunk.getBiomeData()).length, b = 0; b < j; ) { int biome = arrayOfInt[b];
/* 283 */         output.writeByte((byte)biome); b++; }
/*     */     
/*     */     } 
/* 286 */     NBTTagCompound[] tags = chunk.getTileEntities().<NBTTagCompound>toArray(new NBTTagCompound[0]);
/* 287 */     output.writeVarIntToBuffer(tags.length);
/* 288 */     for (int i = 0; i < tags.length; i++) {
/* 289 */       output.writeNBTTagCompoundToBuffer(tags[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   private static ChunkSection readChunkSection_47(PacketBuffer buffer) throws Exception {
/* 294 */     ChunkSection chunkSection = new ChunkSection();
/* 295 */     ByteBuf littleEndianView = buffer.order(ByteOrder.LITTLE_ENDIAN);
/* 296 */     for (int i = 0; i < 4096; i++) {
/* 297 */       short mask = littleEndianView.readShort();
/* 298 */       int type = mask >> 4;
/* 299 */       int data2 = mask & 0xF;
/* 300 */       chunkSection.setBlock(i, type, data2);
/*     */     } 
/* 302 */     return chunkSection;
/*     */   }
/*     */   
/*     */   private static void writeChunkSection_47(PacketBuffer buffer, ChunkSection chunkSection) throws Exception {
/* 306 */     for (int y = 0; y < 16; y++) {
/* 307 */       for (int z = 0; z < 16; z++) {
/* 308 */         for (int x = 0; x < 16; x++) {
/* 309 */           int block = chunkSection.getFlatBlock(x, y, z);
/* 310 */           buffer.writeByte(block);
/* 311 */           buffer.writeByte(block >> 8);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static long toLong(int msw, int lsw) {
/* 318 */     return (msw << 32L) + lsw - -2147483648L;
/*     */   }
/*     */ 
/*     */   
/*     */   public static IChunk readChunk_47(PacketBuffer input2, boolean isBulk) throws Exception {
/* 323 */     int chunkX = input2.readInt();
/* 324 */     int chunkZ = input2.readInt();
/* 325 */     long chunkHash = toLong(chunkX, chunkZ);
/* 326 */     boolean groundUp = (input2.readByte() != 0);
/* 327 */     int bitmask = input2.readUnsignedShort();
/* 328 */     int dataLength = input2.readVarIntFromBuffer();
/* 329 */     BitSet usedSections = new BitSet(16);
/* 330 */     ChunkSection[] sections = new ChunkSection[16];
/* 331 */     int[] biomeData = null;
/* 332 */     for (int i2 = 0; i2 < 16; i2++) {
/* 333 */       if ((bitmask & 1 << i2) != 0)
/* 334 */         usedSections.set(i2); 
/*     */     } 
/* 336 */     int sectionCount = usedSections.cardinality();
/* 337 */     boolean isBulkPacket = isBulk;
/* 338 */     if (sectionCount == 0 && groundUp && !isBulkPacket) {
/* 339 */       return new Chunk_1_8(chunkX, chunkZ);
/*     */     }
/* 341 */     int startIndex = input2.readerIndex(); int i;
/* 342 */     for (i = 0; i < 16; i++) {
/*     */       
/* 344 */       if (usedSections.get(i))
/* 345 */         ChunkSection section = readChunkSection_47(input2); 
/*     */     } 
/* 347 */     for (i = 0; i < 16; i++) {
/* 348 */       if (usedSections.get(i))
/* 349 */         sections[i].readBlockLight((ByteBuf)input2); 
/*     */     } 
/* 351 */     int bytesLeft = dataLength - input2.readerIndex() - startIndex;
/* 352 */     if (bytesLeft >= 2048)
/* 353 */       for (int i3 = 0; i3 < 16; i3++) {
/* 354 */         if (usedSections.get(i3)) {
/* 355 */           sections[i3].readSkyLight((ByteBuf)input2);
/* 356 */           bytesLeft -= 2048;
/*     */         } 
/*     */       }  
/* 359 */     if (bytesLeft >= 25) {
/* 360 */       biomeData = new int[25];
/* 361 */       for (int i4 = 0; i4 < 25; i4++) {
/* 362 */         biomeData[i4] = input2.readByte() & 0xFF;
/*     */       }
/* 364 */       bytesLeft -= 25;
/*     */     } 
/* 366 */     return new Chunk_1_8(chunkX, chunkZ, groundUp, bitmask, sections, biomeData, new ArrayList<>());
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\me\nzxter\bettercraft\mods\protocolhack\protocols\protocolhack1_12_to_1_8\chunk\ChunkWrapper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */