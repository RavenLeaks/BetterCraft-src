/*     */ package net.minecraft.world.storage;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.datafix.DataFixer;
/*     */ import net.minecraft.util.datafix.FixTypes;
/*     */ import net.minecraft.util.datafix.IFixType;
/*     */ import net.minecraft.world.MinecraftException;
/*     */ import net.minecraft.world.WorldProvider;
/*     */ import net.minecraft.world.chunk.storage.IChunkLoader;
/*     */ import net.minecraft.world.gen.structure.template.TemplateManager;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class SaveHandler implements ISaveHandler, IPlayerFileData {
/*  25 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */   
/*     */   private final File worldDirectory;
/*     */ 
/*     */   
/*     */   private final File playersDirectory;
/*     */ 
/*     */   
/*     */   private final File mapDataDir;
/*     */ 
/*     */   
/*  37 */   private final long initializationTime = MinecraftServer.getCurrentTimeMillis();
/*     */   
/*     */   private final String saveDirectoryName;
/*     */   
/*     */   private final TemplateManager structureTemplateManager;
/*     */   
/*     */   protected final DataFixer dataFixer;
/*     */   
/*     */   public SaveHandler(File p_i46648_1_, String saveDirectoryNameIn, boolean p_i46648_3_, DataFixer dataFixerIn) {
/*  46 */     this.dataFixer = dataFixerIn;
/*  47 */     this.worldDirectory = new File(p_i46648_1_, saveDirectoryNameIn);
/*  48 */     this.worldDirectory.mkdirs();
/*  49 */     this.playersDirectory = new File(this.worldDirectory, "playerdata");
/*  50 */     this.mapDataDir = new File(this.worldDirectory, "data");
/*  51 */     this.mapDataDir.mkdirs();
/*  52 */     this.saveDirectoryName = saveDirectoryNameIn;
/*     */     
/*  54 */     if (p_i46648_3_) {
/*     */       
/*  56 */       this.playersDirectory.mkdirs();
/*  57 */       this.structureTemplateManager = new TemplateManager((new File(this.worldDirectory, "structures")).toString(), dataFixerIn);
/*     */     }
/*     */     else {
/*     */       
/*  61 */       this.structureTemplateManager = null;
/*     */     } 
/*     */     
/*  64 */     setSessionLock();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setSessionLock() {
/*     */     try {
/*  74 */       File file1 = new File(this.worldDirectory, "session.lock");
/*  75 */       DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
/*     */ 
/*     */       
/*     */       try {
/*  79 */         dataoutputstream.writeLong(this.initializationTime);
/*     */       }
/*     */       finally {
/*     */         
/*  83 */         dataoutputstream.close();
/*     */       }
/*     */     
/*  86 */     } catch (IOException ioexception) {
/*     */       
/*  88 */       ioexception.printStackTrace();
/*  89 */       throw new RuntimeException("Failed to check session lock, aborting");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getWorldDirectory() {
/*  98 */     return this.worldDirectory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void checkSessionLock() throws MinecraftException {
/*     */     try {
/* 108 */       File file1 = new File(this.worldDirectory, "session.lock");
/* 109 */       DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));
/*     */ 
/*     */       
/*     */       try {
/* 113 */         if (datainputstream.readLong() != this.initializationTime)
/*     */         {
/* 115 */           throw new MinecraftException("The save is being accessed from another location, aborting");
/*     */         }
/*     */       }
/*     */       finally {
/*     */         
/* 120 */         datainputstream.close();
/*     */       }
/*     */     
/* 123 */     } catch (IOException var7) {
/*     */       
/* 125 */       throw new MinecraftException("Failed to check session lock, aborting");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IChunkLoader getChunkLoader(WorldProvider provider) {
/* 134 */     throw new RuntimeException("Old Chunk Storage is no longer supported.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WorldInfo loadWorldInfo() {
/* 144 */     File file1 = new File(this.worldDirectory, "level.dat");
/*     */     
/* 146 */     if (file1.exists()) {
/*     */       
/* 148 */       WorldInfo worldinfo = SaveFormatOld.getWorldData(file1, this.dataFixer);
/*     */       
/* 150 */       if (worldinfo != null)
/*     */       {
/* 152 */         return worldinfo;
/*     */       }
/*     */     } 
/*     */     
/* 156 */     file1 = new File(this.worldDirectory, "level.dat_old");
/* 157 */     return file1.exists() ? SaveFormatOld.getWorldData(file1, this.dataFixer) : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveWorldInfoWithPlayer(WorldInfo worldInformation, @Nullable NBTTagCompound tagCompound) {
/* 165 */     NBTTagCompound nbttagcompound = worldInformation.cloneNBTCompound(tagCompound);
/* 166 */     NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 167 */     nbttagcompound1.setTag("Data", (NBTBase)nbttagcompound);
/*     */ 
/*     */     
/*     */     try {
/* 171 */       File file1 = new File(this.worldDirectory, "level.dat_new");
/* 172 */       File file2 = new File(this.worldDirectory, "level.dat_old");
/* 173 */       File file3 = new File(this.worldDirectory, "level.dat");
/* 174 */       CompressedStreamTools.writeCompressed(nbttagcompound1, new FileOutputStream(file1));
/*     */       
/* 176 */       if (file2.exists())
/*     */       {
/* 178 */         file2.delete();
/*     */       }
/*     */       
/* 181 */       file3.renameTo(file2);
/*     */       
/* 183 */       if (file3.exists())
/*     */       {
/* 185 */         file3.delete();
/*     */       }
/*     */       
/* 188 */       file1.renameTo(file3);
/*     */       
/* 190 */       if (file1.exists())
/*     */       {
/* 192 */         file1.delete();
/*     */       }
/*     */     }
/* 195 */     catch (Exception exception) {
/*     */       
/* 197 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveWorldInfo(WorldInfo worldInformation) {
/* 206 */     saveWorldInfoWithPlayer(worldInformation, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writePlayerData(EntityPlayer player) {
/*     */     try {
/* 216 */       NBTTagCompound nbttagcompound = player.writeToNBT(new NBTTagCompound());
/* 217 */       File file1 = new File(this.playersDirectory, String.valueOf(player.getCachedUniqueIdString()) + ".dat.tmp");
/* 218 */       File file2 = new File(this.playersDirectory, String.valueOf(player.getCachedUniqueIdString()) + ".dat");
/* 219 */       CompressedStreamTools.writeCompressed(nbttagcompound, new FileOutputStream(file1));
/*     */       
/* 221 */       if (file2.exists())
/*     */       {
/* 223 */         file2.delete();
/*     */       }
/*     */       
/* 226 */       file1.renameTo(file2);
/*     */     }
/* 228 */     catch (Exception var5) {
/*     */       
/* 230 */       LOGGER.warn("Failed to save player data for {}", player.getName());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public NBTTagCompound readPlayerData(EntityPlayer player) {
/* 241 */     NBTTagCompound nbttagcompound = null;
/*     */ 
/*     */     
/*     */     try {
/* 245 */       File file1 = new File(this.playersDirectory, String.valueOf(player.getCachedUniqueIdString()) + ".dat");
/*     */       
/* 247 */       if (file1.exists() && file1.isFile())
/*     */       {
/* 249 */         nbttagcompound = CompressedStreamTools.readCompressed(new FileInputStream(file1));
/*     */       }
/*     */     }
/* 252 */     catch (Exception var4) {
/*     */       
/* 254 */       LOGGER.warn("Failed to load player data for {}", player.getName());
/*     */     } 
/*     */     
/* 257 */     if (nbttagcompound != null)
/*     */     {
/* 259 */       player.readFromNBT(this.dataFixer.process((IFixType)FixTypes.PLAYER, nbttagcompound));
/*     */     }
/*     */     
/* 262 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public IPlayerFileData getPlayerNBTManager() {
/* 267 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getAvailablePlayerDat() {
/* 275 */     String[] astring = this.playersDirectory.list();
/*     */     
/* 277 */     if (astring == null)
/*     */     {
/* 279 */       astring = new String[0];
/*     */     }
/*     */     
/* 282 */     for (int i = 0; i < astring.length; i++) {
/*     */       
/* 284 */       if (astring[i].endsWith(".dat"))
/*     */       {
/* 286 */         astring[i] = astring[i].substring(0, astring[i].length() - 4);
/*     */       }
/*     */     } 
/*     */     
/* 290 */     return astring;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void flush() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getMapFileFromName(String mapName) {
/* 305 */     return new File(this.mapDataDir, String.valueOf(mapName) + ".dat");
/*     */   }
/*     */ 
/*     */   
/*     */   public TemplateManager getStructureTemplateManager() {
/* 310 */     return this.structureTemplateManager;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\SaveHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */