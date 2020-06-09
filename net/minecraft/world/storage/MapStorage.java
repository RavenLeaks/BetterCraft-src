/*     */ package net.minecraft.world.storage;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.DataOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.nbt.CompressedStreamTools;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagShort;
/*     */ 
/*     */ public class MapStorage
/*     */ {
/*     */   private final ISaveHandler saveHandler;
/*  21 */   protected Map<String, WorldSavedData> loadedDataMap = Maps.newHashMap();
/*  22 */   private final List<WorldSavedData> loadedDataList = Lists.newArrayList();
/*  23 */   private final Map<String, Short> idCounts = Maps.newHashMap();
/*     */ 
/*     */   
/*     */   public MapStorage(ISaveHandler saveHandlerIn) {
/*  27 */     this.saveHandler = saveHandlerIn;
/*  28 */     loadIdCounts();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public WorldSavedData getOrLoadData(Class<? extends WorldSavedData> clazz, String dataIdentifier) {
/*  39 */     WorldSavedData worldsaveddata = this.loadedDataMap.get(dataIdentifier);
/*     */     
/*  41 */     if (worldsaveddata != null)
/*     */     {
/*  43 */       return worldsaveddata;
/*     */     }
/*     */ 
/*     */     
/*  47 */     if (this.saveHandler != null) {
/*     */       
/*     */       try {
/*     */         
/*  51 */         File file1 = this.saveHandler.getMapFileFromName(dataIdentifier);
/*     */         
/*  53 */         if (file1 != null && file1.exists())
/*     */         {
/*     */           
/*     */           try {
/*  57 */             worldsaveddata = clazz.getConstructor(new Class[] { String.class }).newInstance(new Object[] { dataIdentifier });
/*     */           }
/*  59 */           catch (Exception exception) {
/*     */             
/*  61 */             throw new RuntimeException("Failed to instantiate " + clazz, exception);
/*     */           } 
/*     */           
/*  64 */           FileInputStream fileinputstream = new FileInputStream(file1);
/*  65 */           NBTTagCompound nbttagcompound = CompressedStreamTools.readCompressed(fileinputstream);
/*  66 */           fileinputstream.close();
/*  67 */           worldsaveddata.readFromNBT(nbttagcompound.getCompoundTag("data"));
/*     */         }
/*     */       
/*  70 */       } catch (Exception exception1) {
/*     */         
/*  72 */         exception1.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  76 */     if (worldsaveddata != null) {
/*     */       
/*  78 */       this.loadedDataMap.put(dataIdentifier, worldsaveddata);
/*  79 */       this.loadedDataList.add(worldsaveddata);
/*     */     } 
/*     */     
/*  82 */     return worldsaveddata;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setData(String dataIdentifier, WorldSavedData data) {
/*  91 */     if (this.loadedDataMap.containsKey(dataIdentifier))
/*     */     {
/*  93 */       this.loadedDataList.remove(this.loadedDataMap.remove(dataIdentifier));
/*     */     }
/*     */     
/*  96 */     this.loadedDataMap.put(dataIdentifier, data);
/*  97 */     this.loadedDataList.add(data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveAllData() {
/* 105 */     for (int i = 0; i < this.loadedDataList.size(); i++) {
/*     */       
/* 107 */       WorldSavedData worldsaveddata = this.loadedDataList.get(i);
/*     */       
/* 109 */       if (worldsaveddata.isDirty()) {
/*     */         
/* 111 */         saveData(worldsaveddata);
/* 112 */         worldsaveddata.setDirty(false);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void saveData(WorldSavedData data) {
/* 122 */     if (this.saveHandler != null) {
/*     */       
/*     */       try {
/*     */         
/* 126 */         File file1 = this.saveHandler.getMapFileFromName(data.mapName);
/*     */         
/* 128 */         if (file1 != null)
/*     */         {
/* 130 */           NBTTagCompound nbttagcompound = new NBTTagCompound();
/* 131 */           nbttagcompound.setTag("data", (NBTBase)data.writeToNBT(new NBTTagCompound()));
/* 132 */           FileOutputStream fileoutputstream = new FileOutputStream(file1);
/* 133 */           CompressedStreamTools.writeCompressed(nbttagcompound, fileoutputstream);
/* 134 */           fileoutputstream.close();
/*     */         }
/*     */       
/* 137 */       } catch (Exception exception) {
/*     */         
/* 139 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadIdCounts() {
/*     */     try {
/* 151 */       this.idCounts.clear();
/*     */       
/* 153 */       if (this.saveHandler == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 158 */       File file1 = this.saveHandler.getMapFileFromName("idcounts");
/*     */       
/* 160 */       if (file1 != null && file1.exists()) {
/*     */         
/* 162 */         DataInputStream datainputstream = new DataInputStream(new FileInputStream(file1));
/* 163 */         NBTTagCompound nbttagcompound = CompressedStreamTools.read(datainputstream);
/* 164 */         datainputstream.close();
/*     */         
/* 166 */         for (String s : nbttagcompound.getKeySet()) {
/*     */           
/* 168 */           NBTBase nbtbase = nbttagcompound.getTag(s);
/*     */           
/* 170 */           if (nbtbase instanceof NBTTagShort)
/*     */           {
/* 172 */             NBTTagShort nbttagshort = (NBTTagShort)nbtbase;
/* 173 */             short short1 = nbttagshort.getShort();
/* 174 */             this.idCounts.put(s, Short.valueOf(short1));
/*     */           }
/*     */         
/*     */         } 
/*     */       } 
/* 179 */     } catch (Exception exception) {
/*     */       
/* 181 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getUniqueDataId(String key) {
/* 190 */     Short oshort = this.idCounts.get(key);
/*     */     
/* 192 */     if (oshort == null) {
/*     */       
/* 194 */       oshort = Short.valueOf((short)0);
/*     */     }
/*     */     else {
/*     */       
/* 198 */       oshort = Short.valueOf((short)(oshort.shortValue() + 1));
/*     */     } 
/*     */     
/* 201 */     this.idCounts.put(key, oshort);
/*     */     
/* 203 */     if (this.saveHandler == null)
/*     */     {
/* 205 */       return oshort.shortValue();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 211 */       File file1 = this.saveHandler.getMapFileFromName("idcounts");
/*     */       
/* 213 */       if (file1 != null)
/*     */       {
/* 215 */         NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */         
/* 217 */         for (String s : this.idCounts.keySet())
/*     */         {
/* 219 */           nbttagcompound.setShort(s, ((Short)this.idCounts.get(s)).shortValue());
/*     */         }
/*     */         
/* 222 */         DataOutputStream dataoutputstream = new DataOutputStream(new FileOutputStream(file1));
/* 223 */         CompressedStreamTools.write(nbttagcompound, dataoutputstream);
/* 224 */         dataoutputstream.close();
/*     */       }
/*     */     
/* 227 */     } catch (Exception exception) {
/*     */       
/* 229 */       exception.printStackTrace();
/*     */     } 
/*     */     
/* 232 */     return oshort.shortValue();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\MapStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */