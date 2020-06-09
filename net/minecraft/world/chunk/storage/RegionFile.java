/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.util.List;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import java.util.zip.InflaterInputStream;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ 
/*     */ public class RegionFile
/*     */ {
/*  22 */   private static final byte[] EMPTY_SECTOR = new byte[4096];
/*     */   private final File fileName;
/*     */   private RandomAccessFile dataFile;
/*  25 */   private final int[] offsets = new int[1024];
/*  26 */   private final int[] chunkTimestamps = new int[1024];
/*     */   
/*     */   private List<Boolean> sectorFree;
/*     */   
/*     */   private int sizeDelta;
/*     */   
/*     */   private long lastModified;
/*     */   
/*     */   public RegionFile(File fileNameIn) {
/*  35 */     this.fileName = fileNameIn;
/*  36 */     this.sizeDelta = 0;
/*     */ 
/*     */     
/*     */     try {
/*  40 */       if (fileNameIn.exists())
/*     */       {
/*  42 */         this.lastModified = fileNameIn.lastModified();
/*     */       }
/*     */       
/*  45 */       this.dataFile = new RandomAccessFile(fileNameIn, "rw");
/*     */       
/*  47 */       if (this.dataFile.length() < 4096L) {
/*     */         
/*  49 */         this.dataFile.write(EMPTY_SECTOR);
/*  50 */         this.dataFile.write(EMPTY_SECTOR);
/*  51 */         this.sizeDelta += 8192;
/*     */       } 
/*     */       
/*  54 */       if ((this.dataFile.length() & 0xFFFL) != 0L)
/*     */       {
/*  56 */         for (int i = 0; i < (this.dataFile.length() & 0xFFFL); i++)
/*     */         {
/*  58 */           this.dataFile.write(0);
/*     */         }
/*     */       }
/*     */       
/*  62 */       int i1 = (int)this.dataFile.length() / 4096;
/*  63 */       this.sectorFree = Lists.newArrayListWithCapacity(i1);
/*     */       
/*  65 */       for (int j = 0; j < i1; j++)
/*     */       {
/*  67 */         this.sectorFree.add(Boolean.valueOf(true));
/*     */       }
/*     */       
/*  70 */       this.sectorFree.set(0, Boolean.valueOf(false));
/*  71 */       this.sectorFree.set(1, Boolean.valueOf(false));
/*  72 */       this.dataFile.seek(0L);
/*     */       
/*  74 */       for (int j1 = 0; j1 < 1024; j1++) {
/*     */         
/*  76 */         int k = this.dataFile.readInt();
/*  77 */         this.offsets[j1] = k;
/*     */         
/*  79 */         if (k != 0 && (k >> 8) + (k & 0xFF) <= this.sectorFree.size())
/*     */         {
/*  81 */           for (int l = 0; l < (k & 0xFF); l++)
/*     */           {
/*  83 */             this.sectorFree.set((k >> 8) + l, Boolean.valueOf(false));
/*     */           }
/*     */         }
/*     */       } 
/*     */       
/*  88 */       for (int k1 = 0; k1 < 1024; k1++)
/*     */       {
/*  90 */         int l1 = this.dataFile.readInt();
/*  91 */         this.chunkTimestamps[k1] = l1;
/*     */       }
/*     */     
/*  94 */     } catch (IOException ioexception) {
/*     */       
/*  96 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public synchronized DataInputStream getChunkDataInputStream(int x, int z) {
/* 107 */     if (outOfBounds(x, z))
/*     */     {
/* 109 */       return null;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 115 */       int i = getOffset(x, z);
/*     */       
/* 117 */       if (i == 0)
/*     */       {
/* 119 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 123 */       int j = i >> 8;
/* 124 */       int k = i & 0xFF;
/*     */       
/* 126 */       if (j + k > this.sectorFree.size())
/*     */       {
/* 128 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 132 */       this.dataFile.seek((j * 4096));
/* 133 */       int l = this.dataFile.readInt();
/*     */       
/* 135 */       if (l > 4096 * k)
/*     */       {
/* 137 */         return null;
/*     */       }
/* 139 */       if (l <= 0)
/*     */       {
/* 141 */         return null;
/*     */       }
/*     */ 
/*     */       
/* 145 */       byte b0 = this.dataFile.readByte();
/*     */       
/* 147 */       if (b0 == 1) {
/*     */         
/* 149 */         byte[] abyte1 = new byte[l - 1];
/* 150 */         this.dataFile.read(abyte1);
/* 151 */         return new DataInputStream(new BufferedInputStream(new GZIPInputStream(new ByteArrayInputStream(abyte1))));
/*     */       } 
/* 153 */       if (b0 == 2) {
/*     */         
/* 155 */         byte[] abyte = new byte[l - 1];
/* 156 */         this.dataFile.read(abyte);
/* 157 */         return new DataInputStream(new BufferedInputStream(new InflaterInputStream(new ByteArrayInputStream(abyte))));
/*     */       } 
/*     */ 
/*     */       
/* 161 */       return null;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 167 */     catch (IOException var9) {
/*     */       
/* 169 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public DataOutputStream getChunkDataOutputStream(int x, int z) {
/* 181 */     return outOfBounds(x, z) ? null : new DataOutputStream(new BufferedOutputStream(new DeflaterOutputStream(new ChunkBuffer(x, z))));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void write(int x, int z, byte[] data, int length) {
/*     */     try {
/* 191 */       int i = getOffset(x, z);
/* 192 */       int j = i >> 8;
/* 193 */       int k = i & 0xFF;
/* 194 */       int l = (length + 5) / 4096 + 1;
/*     */       
/* 196 */       if (l >= 256) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 201 */       if (j != 0 && k == l) {
/*     */         
/* 203 */         write(j, data, length);
/*     */       }
/*     */       else {
/*     */         
/* 207 */         for (int i1 = 0; i1 < k; i1++)
/*     */         {
/* 209 */           this.sectorFree.set(j + i1, Boolean.valueOf(true));
/*     */         }
/*     */         
/* 212 */         int l1 = this.sectorFree.indexOf(Boolean.valueOf(true));
/* 213 */         int j1 = 0;
/*     */         
/* 215 */         if (l1 != -1)
/*     */         {
/* 217 */           for (int k1 = l1; k1 < this.sectorFree.size(); k1++) {
/*     */             
/* 219 */             if (j1 != 0) {
/*     */               
/* 221 */               if (((Boolean)this.sectorFree.get(k1)).booleanValue())
/*     */               {
/* 223 */                 j1++;
/*     */               }
/*     */               else
/*     */               {
/* 227 */                 j1 = 0;
/*     */               }
/*     */             
/* 230 */             } else if (((Boolean)this.sectorFree.get(k1)).booleanValue()) {
/*     */               
/* 232 */               l1 = k1;
/* 233 */               j1 = 1;
/*     */             } 
/*     */             
/* 236 */             if (j1 >= l) {
/*     */               break;
/*     */             }
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/* 243 */         if (j1 >= l) {
/*     */           
/* 245 */           j = l1;
/* 246 */           setOffset(x, z, l1 << 8 | l);
/*     */           
/* 248 */           for (int j2 = 0; j2 < l; j2++)
/*     */           {
/* 250 */             this.sectorFree.set(j + j2, Boolean.valueOf(false));
/*     */           }
/*     */           
/* 253 */           write(j, data, length);
/*     */         }
/*     */         else {
/*     */           
/* 257 */           this.dataFile.seek(this.dataFile.length());
/* 258 */           j = this.sectorFree.size();
/*     */           
/* 260 */           for (int i2 = 0; i2 < l; i2++) {
/*     */             
/* 262 */             this.dataFile.write(EMPTY_SECTOR);
/* 263 */             this.sectorFree.add(Boolean.valueOf(false));
/*     */           } 
/*     */           
/* 266 */           this.sizeDelta += 4096 * l;
/* 267 */           write(j, data, length);
/* 268 */           setOffset(x, z, j << 8 | l);
/*     */         } 
/*     */       } 
/*     */       
/* 272 */       setChunkTimestamp(x, z, (int)(MinecraftServer.getCurrentTimeMillis() / 1000L));
/*     */     }
/* 274 */     catch (IOException ioexception) {
/*     */       
/* 276 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void write(int sectorNumber, byte[] data, int length) throws IOException {
/* 285 */     this.dataFile.seek((sectorNumber * 4096));
/* 286 */     this.dataFile.writeInt(length + 1);
/* 287 */     this.dataFile.writeByte(2);
/* 288 */     this.dataFile.write(data, 0, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean outOfBounds(int x, int z) {
/* 296 */     return !(x >= 0 && x < 32 && z >= 0 && z < 32);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int getOffset(int x, int z) {
/* 304 */     return this.offsets[x + z * 32];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isChunkSaved(int x, int z) {
/* 312 */     return (getOffset(x, z) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setOffset(int x, int z, int offset) throws IOException {
/* 320 */     this.offsets[x + z * 32] = offset;
/* 321 */     this.dataFile.seek(((x + z * 32) * 4));
/* 322 */     this.dataFile.writeInt(offset);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setChunkTimestamp(int x, int z, int timestamp) throws IOException {
/* 330 */     this.chunkTimestamps[x + z * 32] = timestamp;
/* 331 */     this.dataFile.seek((4096 + (x + z * 32) * 4));
/* 332 */     this.dataFile.writeInt(timestamp);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() throws IOException {
/* 340 */     if (this.dataFile != null)
/*     */     {
/* 342 */       this.dataFile.close();
/*     */     }
/*     */   }
/*     */   
/*     */   class ChunkBuffer
/*     */     extends ByteArrayOutputStream
/*     */   {
/*     */     private final int chunkX;
/*     */     private final int chunkZ;
/*     */     
/*     */     public ChunkBuffer(int x, int z) {
/* 353 */       super(8096);
/* 354 */       this.chunkX = x;
/* 355 */       this.chunkZ = z;
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 360 */       RegionFile.this.write(this.chunkX, this.chunkZ, this.buf, this.count);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\chunk\storage\RegionFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */