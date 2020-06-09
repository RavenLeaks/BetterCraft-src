/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.client.AnvilConverterException;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.util.IProgressUpdate;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IFixType;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SaveFormatOld implements ISaveFormat {
/*  20 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   protected final File savesDirectory;
/*     */ 
/*     */   
/*     */   protected final DataFixer dataFixer;
/*     */ 
/*     */   
/*     */   public SaveFormatOld(File savesDirectoryIn, DataFixer dataFixerIn) {
/*  30 */     this.dataFixer = dataFixerIn;
/*     */     
/*  32 */     if (!savesDirectoryIn.exists())
/*     */     {
/*  34 */       savesDirectoryIn.mkdirs();
/*     */     }
/*     */     
/*  37 */     this.savesDirectory = savesDirectoryIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/*  45 */     return "Old Format";
/*     */   }
/*     */ 
/*     */   
/*     */   public List<WorldSummary> getSaveList() throws AnvilConverterException {
/*  50 */     List<WorldSummary> list = Lists.newArrayList();
/*     */     
/*  52 */     for (int i = 0; i < 5; i++) {
/*     */       
/*  54 */       String s = "World" + (i + 1);
/*  55 */       WorldInfo worldinfo = getWorldInfo(s);
/*     */       
/*  57 */       if (worldinfo != null)
/*     */       {
/*  59 */         list.add(new WorldSummary(worldinfo, s, "", worldinfo.getSizeOnDisk(), false));
/*     */       }
/*     */     } 
/*     */     
/*  63 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flushCache() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WorldInfo getWorldInfo(String saveName) {
/*  77 */     File file1 = new File(this.savesDirectory, saveName);
/*     */     
/*  79 */     if (!file1.exists())
/*     */     {
/*  81 */       return null;
/*     */     }
/*     */ 
/*     */     
/*  85 */     File file2 = new File(file1, "level.dat");
/*     */     
/*  87 */     if (file2.exists()) {
/*     */       
/*  89 */       WorldInfo worldinfo = getWorldData(file2, this.dataFixer);
/*     */       
/*  91 */       if (worldinfo != null)
/*     */       {
/*  93 */         return worldinfo;
/*     */       }
/*     */     } 
/*     */     
/*  97 */     file2 = new File(file1, "level.dat_old");
/*  98 */     return file2.exists() ? getWorldData(file2, this.dataFixer) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static WorldInfo getWorldData(File p_186353_0_, DataFixer dataFixerIn) {
/*     */     try {
/* 107 */       NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(p_186353_0_));
/* 108 */       NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
/* 109 */       return new WorldInfo(dataFixerIn.process((IFixType)FixTypes.LEVEL, nbttagcompound1));
/*     */     }
/* 111 */     catch (Exception exception) {
/*     */       
/* 113 */       LOGGER.error("Exception reading {}", p_186353_0_, exception);
/* 114 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renameWorld(String dirName, String newName) {
/* 124 */     File file1 = new File(this.savesDirectory, dirName);
/*     */     
/* 126 */     if (file1.exists()) {
/*     */       
/* 128 */       File file2 = new File(file1, "level.dat");
/*     */       
/* 130 */       if (file2.exists()) {
/*     */         
/*     */         try {
/*     */           
/* 134 */           NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file2));
/* 135 */           NBTTagCompound nbttagcompound1 = nbttagcompound.getCompoundTag("Data");
/* 136 */           nbttagcompound1.setString("LevelName", newName);
/* 137 */           CompressedStreamTools.writeCompressed(nbttagcompound, new FileOutputStream(file2));
/*     */         }
/* 139 */         catch (Exception exception) {
/*     */           
/* 141 */           exception.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNewLevelIdAcceptable(String saveName) {
/* 149 */     File file1 = new File(this.savesDirectory, saveName);
/*     */     
/* 151 */     if (file1.exists())
/*     */     {
/* 153 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 159 */       file1.mkdir();
/* 160 */       file1.delete();
/* 161 */       return true;
/*     */     }
/* 163 */     catch (Throwable throwable) {
/*     */       
/* 165 */       LOGGER.warn("Couldn't make new level", throwable);
/* 166 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean deleteWorldDirectory(String saveName) {
/* 176 */     File file1 = new File(this.savesDirectory, saveName);
/*     */     
/* 178 */     if (!file1.exists())
/*     */     {
/* 180 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 184 */     LOGGER.info("Deleting level {}", saveName);
/*     */     
/* 186 */     for (int i = 1; i <= 5; i++) {
/*     */       
/* 188 */       LOGGER.info("Attempt {}...", Integer.valueOf(i));
/*     */       
/* 190 */       if (deleteFiles(file1.listFiles())) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 195 */       LOGGER.warn("Unsuccessful in deleting contents.");
/*     */       
/* 197 */       if (i < 5) {
/*     */         
/*     */         try {
/*     */           
/* 201 */           Thread.sleep(500L);
/*     */         }
/* 203 */         catch (InterruptedException interruptedException) {}
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 210 */     return file1.delete();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean deleteFiles(File[] files) {
/*     */     byte b;
/*     */     int i;
/*     */     File[] arrayOfFile;
/* 219 */     for (i = (arrayOfFile = files).length, b = 0; b < i; ) { File file1 = arrayOfFile[b];
/*     */       
/* 221 */       LOGGER.debug("Deleting {}", file1);
/*     */       
/* 223 */       if (file1.isDirectory() && !deleteFiles(file1.listFiles())) {
/*     */         
/* 225 */         LOGGER.warn("Couldn't delete directory {}", file1);
/* 226 */         return false;
/*     */       } 
/*     */       
/* 229 */       if (!file1.delete()) {
/*     */         
/* 231 */         LOGGER.warn("Couldn't delete file {}", file1);
/* 232 */         return false;
/*     */       } 
/*     */       b++; }
/*     */     
/* 236 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ISaveHandler getSaveLoader(String saveName, boolean storePlayerdata) {
/* 244 */     return new SaveHandler(this.savesDirectory, saveName, storePlayerdata, this.dataFixer);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isConvertible(String saveName) {
/* 249 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOldMapFormat(String saveName) {
/* 257 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean convertMapFormat(String filename, IProgressUpdate progressCallback) {
/* 265 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canLoadWorld(String saveName) {
/* 273 */     File file1 = new File(this.savesDirectory, saveName);
/* 274 */     return file1.isDirectory();
/*     */   }
/*     */ 
/*     */   
/*     */   public File getFile(String p_186352_1_, String p_186352_2_) {
/* 279 */     return new File(new File(this.savesDirectory, p_186352_1_), p_186352_2_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\SaveFormatOld.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */