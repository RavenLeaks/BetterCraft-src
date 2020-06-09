/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class RegionFileCache
/*     */ {
/*  12 */   private static final Map<File, RegionFile> REGIONS_BY_FILE = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public static synchronized RegionFile createOrLoadRegionFile(File worldDir, int chunkX, int chunkZ) {
/*  16 */     File file1 = new File(worldDir, "region");
/*  17 */     File file2 = new File(file1, "r." + (chunkX >> 5) + "." + (chunkZ >> 5) + ".mca");
/*  18 */     RegionFile regionfile = REGIONS_BY_FILE.get(file2);
/*     */     
/*  20 */     if (regionfile != null)
/*     */     {
/*  22 */       return regionfile;
/*     */     }
/*     */ 
/*     */     
/*  26 */     if (!file1.exists())
/*     */     {
/*  28 */       file1.mkdirs();
/*     */     }
/*     */     
/*  31 */     if (REGIONS_BY_FILE.size() >= 256)
/*     */     {
/*  33 */       clearRegionFileReferences();
/*     */     }
/*     */     
/*  36 */     RegionFile regionfile1 = new RegionFile(file2);
/*  37 */     REGIONS_BY_FILE.put(file2, regionfile1);
/*  38 */     return regionfile1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized RegionFile func_191065_b(File p_191065_0_, int p_191065_1_, int p_191065_2_) {
/*  44 */     File file1 = new File(p_191065_0_, "region");
/*  45 */     File file2 = new File(file1, "r." + (p_191065_1_ >> 5) + "." + (p_191065_2_ >> 5) + ".mca");
/*  46 */     RegionFile regionfile = REGIONS_BY_FILE.get(file2);
/*     */     
/*  48 */     if (regionfile != null)
/*     */     {
/*  50 */       return regionfile;
/*     */     }
/*  52 */     if (file1.exists() && file2.exists()) {
/*     */       
/*  54 */       if (REGIONS_BY_FILE.size() >= 256)
/*     */       {
/*  56 */         clearRegionFileReferences();
/*     */       }
/*     */       
/*  59 */       RegionFile regionfile1 = new RegionFile(file2);
/*  60 */       REGIONS_BY_FILE.put(file2, regionfile1);
/*  61 */       return regionfile1;
/*     */     } 
/*     */ 
/*     */     
/*  65 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static synchronized void clearRegionFileReferences() {
/*  74 */     for (RegionFile regionfile : REGIONS_BY_FILE.values()) {
/*     */ 
/*     */       
/*     */       try {
/*  78 */         if (regionfile != null)
/*     */         {
/*  80 */           regionfile.close();
/*     */         }
/*     */       }
/*  83 */       catch (IOException ioexception) {
/*     */         
/*  85 */         ioexception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */     
/*  89 */     REGIONS_BY_FILE.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DataInputStream getChunkInputStream(File worldDir, int chunkX, int chunkZ) {
/*  97 */     RegionFile regionfile = createOrLoadRegionFile(worldDir, chunkX, chunkZ);
/*  98 */     return regionfile.getChunkDataInputStream(chunkX & 0x1F, chunkZ & 0x1F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DataOutputStream getChunkOutputStream(File worldDir, int chunkX, int chunkZ) {
/* 106 */     RegionFile regionfile = createOrLoadRegionFile(worldDir, chunkX, chunkZ);
/* 107 */     return regionfile.getChunkDataOutputStream(chunkX & 0x1F, chunkZ & 0x1F);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean func_191064_f(File p_191064_0_, int p_191064_1_, int p_191064_2_) {
/* 112 */     RegionFile regionfile = func_191065_b(p_191064_0_, p_191064_1_, p_191064_2_);
/* 113 */     return (regionfile != null) ? regionfile.isChunkSaved(p_191064_1_ & 0x1F, p_191064_2_ & 0x1F) : false;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\chunk\storage\RegionFileCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */