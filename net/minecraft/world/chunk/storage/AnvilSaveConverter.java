/*     */ package net.minecraft.world.chunk.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FilenameFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.client.AnvilConverterException;
/*     */ import net.minecraft.init.Biomes;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.WorldType;
/*     */ import net.minecraft.world.biome.BiomeProvider;
/*     */ import net.minecraft.world.biome.BiomeProviderSingle;
/*     */ import net.minecraft.world.storage.ISaveHandler;
/*     */ import net.minecraft.world.storage.SaveFormatOld;
/*     */ import net.minecraft.world.storage.WorldInfo;
/*     */ import net.minecraft.world.storage.WorldSummary;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class AnvilSaveConverter extends SaveFormatOld {
/*  32 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   public AnvilSaveConverter(File dir, DataFixer dataFixerIn) {
/*  36 */     super(dir, dataFixerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  44 */     return "Anvil";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<WorldSummary> getSaveList() throws AnvilConverterException {
/*  49 */     if (this.savesDirectory != null && this.savesDirectory.exists() && this.savesDirectory.isDirectory()) {
/*     */       
/*  51 */       List<WorldSummary> list = Lists.newArrayList();
/*  52 */       File[] afile = this.savesDirectory.listFiles(); byte b; int i;
/*     */       File[] arrayOfFile1;
/*  54 */       for (i = (arrayOfFile1 = afile).length, b = 0; b < i; ) { File file1 = arrayOfFile1[b];
/*     */         
/*  56 */         if (file1.isDirectory()) {
/*     */           
/*  58 */           String s = file1.getName();
/*  59 */           WorldInfo worldinfo = getWorldInfo(s);
/*     */           
/*  61 */           if (worldinfo != null && (worldinfo.getSaveVersion() == 19132 || worldinfo.getSaveVersion() == 19133)) {
/*     */             
/*  63 */             boolean flag = (worldinfo.getSaveVersion() != getSaveVersion());
/*  64 */             String s1 = worldinfo.getWorldName();
/*     */             
/*  66 */             if (StringUtils.isEmpty(s1))
/*     */             {
/*  68 */               s1 = s;
/*     */             }
/*     */             
/*  71 */             long l = 0L;
/*  72 */             list.add(new WorldSummary(worldinfo, s, s1, 0L, flag));
/*     */           } 
/*     */         } 
/*     */         b++; }
/*     */       
/*  77 */       return list;
/*     */     } 
/*     */ 
/*     */     
/*  81 */     throw new AnvilConverterException(I18n.translateToLocal("selectWorld.load_folder_access"));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getSaveVersion() {
/*  87 */     return 19133;
/*     */   }
/*     */ 
/*     */   
/*     */   public void flushCache() {
/*  92 */     RegionFileCache.clearRegionFileReferences();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISaveHandler getSaveLoader(String saveName, boolean storePlayerdata) {
/* 100 */     return (ISaveHandler)new AnvilSaveHandler(this.savesDirectory, saveName, storePlayerdata, this.dataFixer);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConvertible(String saveName) {
/* 105 */     WorldInfo worldinfo = getWorldInfo(saveName);
/* 106 */     return (worldinfo != null && worldinfo.getSaveVersion() == 19132);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOldMapFormat(String saveName) {
/* 114 */     WorldInfo worldinfo = getWorldInfo(saveName);
/* 115 */     return (worldinfo != null && worldinfo.getSaveVersion() != getSaveVersion());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean convertMapFormat(String filename, IProgressUpdate progressCallback) {
/*     */     BiomeProvider biomeprovider;
/* 123 */     progressCallback.setLoadingProgress(0);
/* 124 */     List<File> list = Lists.newArrayList();
/* 125 */     List<File> list1 = Lists.newArrayList();
/* 126 */     List<File> list2 = Lists.newArrayList();
/* 127 */     File file1 = new File(this.savesDirectory, filename);
/* 128 */     File file2 = new File(file1, "DIM-1");
/* 129 */     File file3 = new File(file1, "DIM1");
/* 130 */     LOGGER.info("Scanning folders...");
/* 131 */     addRegionFilesToCollection(file1, list);
/*     */     
/* 133 */     if (file2.exists())
/*     */     {
/* 135 */       addRegionFilesToCollection(file2, list1);
/*     */     }
/*     */     
/* 138 */     if (file3.exists())
/*     */     {
/* 140 */       addRegionFilesToCollection(file3, list2);
/*     */     }
/*     */     
/* 143 */     int i = list.size() + list1.size() + list2.size();
/* 144 */     LOGGER.info("Total conversion count is {}", Integer.valueOf(i));
/* 145 */     WorldInfo worldinfo = getWorldInfo(filename);
/*     */ 
/*     */     
/* 148 */     if (worldinfo != null && worldinfo.getTerrainType() == WorldType.FLAT) {
/*     */       
/* 150 */       BiomeProviderSingle biomeProviderSingle = new BiomeProviderSingle(Biomes.PLAINS);
/*     */     }
/*     */     else {
/*     */       
/* 154 */       biomeprovider = new BiomeProvider(worldinfo);
/*     */     } 
/*     */     
/* 157 */     convertFile(new File(file1, "region"), list, biomeprovider, 0, i, progressCallback);
/* 158 */     convertFile(new File(file2, "region"), list1, (BiomeProvider)new BiomeProviderSingle(Biomes.HELL), list.size(), i, progressCallback);
/* 159 */     convertFile(new File(file3, "region"), list2, (BiomeProvider)new BiomeProviderSingle(Biomes.SKY), list.size() + list1.size(), i, progressCallback);
/* 160 */     worldinfo.setSaveVersion(19133);
/*     */     
/* 162 */     if (worldinfo.getTerrainType() == WorldType.DEFAULT_1_1)
/*     */     {
/* 164 */       worldinfo.setTerrainType(WorldType.DEFAULT);
/*     */     }
/*     */     
/* 167 */     createFile(filename);
/* 168 */     ISaveHandler isavehandler = getSaveLoader(filename, false);
/* 169 */     isavehandler.saveWorldInfo(worldinfo);
/* 170 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createFile(String filename) {
/* 178 */     File file1 = new File(this.savesDirectory, filename);
/*     */     
/* 180 */     if (!file1.exists()) {
/*     */       
/* 182 */       LOGGER.warn("Unable to create level.dat_mcr backup");
/*     */     }
/*     */     else {
/*     */       
/* 186 */       File file2 = new File(file1, "level.dat");
/*     */       
/* 188 */       if (!file2.exists()) {
/*     */         
/* 190 */         LOGGER.warn("Unable to create level.dat_mcr backup");
/*     */       }
/*     */       else {
/*     */         
/* 194 */         File file3 = new File(file1, "level.dat_mcr");
/*     */         
/* 196 */         if (!file2.renameTo(file3))
/*     */         {
/* 198 */           LOGGER.warn("Unable to create level.dat_mcr backup");
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void convertFile(File baseFolder, Iterable<File> regionFiles, BiomeProvider p_75813_3_, int p_75813_4_, int p_75813_5_, IProgressUpdate progress) {
/* 206 */     for (File file1 : regionFiles) {
/*     */       
/* 208 */       convertChunks(baseFolder, file1, p_75813_3_, p_75813_4_, p_75813_5_, progress);
/* 209 */       p_75813_4_++;
/* 210 */       int i = (int)Math.round(100.0D * p_75813_4_ / p_75813_5_);
/* 211 */       progress.setLoadingProgress(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertChunks(File baseFolder, File p_75811_2_, BiomeProvider biomeSource, int p_75811_4_, int p_75811_5_, IProgressUpdate progressCallback) {
/*     */     try {
/* 222 */       String s = p_75811_2_.getName();
/* 223 */       RegionFile regionfile = new RegionFile(p_75811_2_);
/* 224 */       RegionFile regionfile1 = new RegionFile(new File(baseFolder, String.valueOf(s.substring(0, s.length() - ".mcr".length())) + ".mca"));
/*     */       
/* 226 */       for (int i = 0; i < 32; i++) {
/*     */         
/* 228 */         for (int j = 0; j < 32; j++) {
/*     */           
/* 230 */           if (regionfile.isChunkSaved(i, j) && !regionfile1.isChunkSaved(i, j)) {
/*     */             
/* 232 */             DataInputStream datainputstream = regionfile.getChunkDataInputStream(i, j);
/*     */             
/* 234 */             if (datainputstream == null) {
/*     */               
/* 236 */               LOGGER.warn("Failed to fetch input stream");
/*     */             }
/*     */             else {
/*     */               
/* 240 */               NBTTagCompound nbttagcompound = CompressedStreamTools.read(datainputstream);
/* 241 */               datainputstream.close();
/* 242 */               NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Level");
/* 243 */               ChunkLoader.AnvilConverterData chunkloader$anvilconverterdata = ChunkLoader.load(nbttagcompound1);
/* 244 */               NBTTagCompound nbttagcompound2 = new NBTTagCompound();
/* 245 */               NBTTagCompound nbttagcompound3 = new NBTTagCompound();
/* 246 */               nbttagcompound2.setTag("Level", (NBTBase)nbttagcompound3);
/* 247 */               ChunkLoader.convertToAnvilFormat(chunkloader$anvilconverterdata, nbttagcompound3, biomeSource);
/* 248 */               DataOutputStream dataoutputstream = regionfile1.getChunkDataOutputStream(i, j);
/* 249 */               CompressedStreamTools.write(nbttagcompound2, dataoutputstream);
/* 250 */               dataoutputstream.close();
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 255 */         int k = (int)Math.round(100.0D * (p_75811_4_ * 1024) / (p_75811_5_ * 1024));
/* 256 */         int l = (int)Math.round(100.0D * ((i + 1) * 32 + p_75811_4_ * 1024) / (p_75811_5_ * 1024));
/*     */         
/* 258 */         if (l > k)
/*     */         {
/* 260 */           progressCallback.setLoadingProgress(l);
/*     */         }
/*     */       } 
/*     */       
/* 264 */       regionfile.close();
/* 265 */       regionfile1.close();
/*     */     }
/* 267 */     catch (IOException ioexception) {
/*     */       
/* 269 */       ioexception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addRegionFilesToCollection(File worldDir, Collection<File> collection) {
/* 278 */     File file1 = new File(worldDir, "region");
/* 279 */     File[] afile = file1.listFiles(new FilenameFilter()
/*     */         {
/*     */           public boolean accept(File p_accept_1_, String p_accept_2_)
/*     */           {
/* 283 */             return p_accept_2_.endsWith(".mcr");
/*     */           }
/*     */         });
/*     */     
/* 287 */     if (afile != null)
/*     */     {
/* 289 */       Collections.addAll(collection, afile);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\chunk\storage\AnvilSaveConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */