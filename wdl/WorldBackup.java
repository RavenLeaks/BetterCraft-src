/*     */ package wdl;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.text.DateFormat;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ 
/*     */ public class WorldBackup
/*     */ {
/*     */   public static interface IBackupProgressMonitor
/*     */   {
/*     */     void setNumberOfFiles(int param1Int);
/*     */     
/*     */     void onNextFile(String param1String);
/*     */   }
/*     */   
/*     */   public enum WorldBackupType {
/*  26 */     NONE(
/*     */ 
/*     */       
/*  29 */       "wdl.backup.none", ""),
/*  30 */     FOLDER(
/*     */ 
/*     */       
/*  33 */       "wdl.backup.folder", "wdl.saveProgress.backingUp.title.folder"),
/*  34 */     ZIP(
/*     */ 
/*     */       
/*  37 */       "wdl.backup.zip", "wdl.saveProgress.backingUp.title.zip");
/*     */ 
/*     */ 
/*     */     
/*     */     public final String descriptionKey;
/*     */ 
/*     */     
/*     */     public final String titleKey;
/*     */ 
/*     */ 
/*     */     
/*     */     WorldBackupType(String descriptionKey, String titleKey) {
/*  49 */       this.descriptionKey = descriptionKey;
/*  50 */       this.titleKey = titleKey;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getDescription() {
/*  58 */       return I18n.format(this.descriptionKey, new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public String getTitle() {
/*  66 */       return I18n.format(this.titleKey, new Object[0]);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public static WorldBackupType match(String name) {
/*     */       byte b;
/*     */       int i;
/*     */       WorldBackupType[] arrayOfWorldBackupType;
/*  77 */       for (i = (arrayOfWorldBackupType = values()).length, b = 0; b < i; ) { WorldBackupType type = arrayOfWorldBackupType[b];
/*  78 */         if (type.name().equalsIgnoreCase(name)) {
/*  79 */           return type;
/*     */         }
/*     */         b++; }
/*     */       
/*  83 */       return NONE;
/*     */     }
/*     */   }
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
/* 108 */   private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
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
/*     */   public static void backupWorld(File worldFolder, String worldName, WorldBackupType type, IBackupProgressMonitor monitor) throws IOException {
/*     */     File destination;
/* 122 */     String newWorldName = String.valueOf(worldName) + "_" + DATE_FORMAT.format(new Date());
/*     */     
/* 124 */     switch (type) {
/*     */       case NONE:
/*     */         return;
/*     */       
/*     */       case null:
/* 129 */         destination = new File(worldFolder.getParentFile(), 
/* 130 */             newWorldName);
/*     */         
/* 132 */         if (destination.exists()) {
/* 133 */           throw new IOException("Backup folder (" + destination + 
/* 134 */               ") already exists!");
/*     */         }
/*     */         
/* 137 */         copyDirectory(worldFolder, destination, monitor);
/*     */         return;
/*     */       
/*     */       case ZIP:
/* 141 */         destination = new File(worldFolder.getParentFile(), 
/* 142 */             String.valueOf(newWorldName) + ".zip");
/*     */         
/* 144 */         if (destination.exists()) {
/* 145 */           throw new IOException("Backup file (" + destination + 
/* 146 */               ") already exists!");
/*     */         }
/*     */         
/* 149 */         zipDirectory(worldFolder, destination, monitor);
/*     */         return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void copyDirectory(File src, File destination, IBackupProgressMonitor monitor) throws IOException {
/* 160 */     monitor.setNumberOfFiles(countFilesInFolder(src));
/*     */     
/* 162 */     copy(src, destination, src.getPath().length() + 1, monitor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void zipDirectory(File src, File destination, IBackupProgressMonitor monitor) throws IOException {
/* 170 */     monitor.setNumberOfFiles(countFilesInFolder(src));
/*     */     
/* 172 */     FileOutputStream outStream = null;
/* 173 */     ZipOutputStream stream = null;
/*     */     try {
/* 175 */       outStream = new FileOutputStream(destination);
/*     */       try {
/* 177 */         stream = new ZipOutputStream(outStream);
/* 178 */         zipFolder(src, stream, src.getPath().length() + 1, monitor);
/*     */       } finally {
/* 180 */         stream.close();
/*     */       } 
/*     */     } finally {
/* 183 */       outStream.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void zipFolder(File folder, ZipOutputStream stream, int pathStartIndex, IBackupProgressMonitor monitor) throws IOException {
/*     */     byte b;
/*     */     int i;
/*     */     File[] arrayOfFile;
/* 199 */     for (i = (arrayOfFile = folder.listFiles()).length, b = 0; b < i; ) { File file = arrayOfFile[b];
/* 200 */       if (file.isFile()) {
/* 201 */         String name = file.getPath().substring(pathStartIndex);
/* 202 */         monitor.onNextFile(name);
/* 203 */         ZipEntry zipEntry = new ZipEntry(name);
/* 204 */         stream.putNextEntry(zipEntry);
/* 205 */         FileInputStream inputStream = new FileInputStream(file);
/*     */         try {
/* 207 */           IOUtils.copy(inputStream, stream);
/*     */         } finally {
/* 209 */           inputStream.close();
/*     */         } 
/* 211 */         stream.closeEntry();
/* 212 */       } else if (file.isDirectory()) {
/* 213 */         zipFolder(file, stream, pathStartIndex, monitor);
/*     */       } 
/*     */       b++; }
/*     */   
/*     */   }
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
/*     */   private static void copy(File from, File to, int pathStartIndex, IBackupProgressMonitor monitor) throws IOException {
/* 229 */     if (from.isDirectory()) {
/* 230 */       if (!to.exists())
/* 231 */         to.mkdir();  byte b;
/*     */       int i;
/*     */       String[] arrayOfString;
/* 234 */       for (i = (arrayOfString = from.list()).length, b = 0; b < i; ) { String fileName = arrayOfString[b];
/* 235 */         copy(new File(from, fileName), 
/* 236 */             new File(to, fileName), pathStartIndex, monitor); b++; }
/*     */     
/*     */     } else {
/* 239 */       monitor.onNextFile(to.getPath().substring(pathStartIndex));
/*     */ 
/*     */       
/* 242 */       FileUtils.copyFile(from, to, true);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int countFilesInFolder(File folder) {
/* 254 */     if (!folder.isDirectory()) {
/* 255 */       return 0;
/*     */     }
/*     */     
/* 258 */     int count = 0; byte b; int i; File[] arrayOfFile;
/* 259 */     for (i = (arrayOfFile = folder.listFiles()).length, b = 0; b < i; ) { File file = arrayOfFile[b];
/* 260 */       if (file.isDirectory()) {
/* 261 */         count += countFilesInFolder(file);
/*     */       } else {
/* 263 */         count++;
/*     */       } 
/*     */       b++; }
/*     */     
/* 267 */     return count;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\WorldBackup.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */