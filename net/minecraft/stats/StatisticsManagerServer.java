/*     */ package net.minecraft.stats;
/*     */ 
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParseException;
/*     */ import com.google.gson.JsonParser;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.play.server.SPacketStatistics;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.IJsonSerializable;
/*     */ import net.minecraft.util.TupleIntJsonSerializable;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class StatisticsManagerServer
/*     */   extends StatisticsManager {
/*  27 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */   private final MinecraftServer mcServer;
/*     */   private final File statsFile;
/*  30 */   private final Set<StatBase> dirty = Sets.newHashSet();
/*  31 */   private int lastStatRequest = -300;
/*     */ 
/*     */   
/*     */   public StatisticsManagerServer(MinecraftServer serverIn, File statsFileIn) {
/*  35 */     this.mcServer = serverIn;
/*  36 */     this.statsFile = statsFileIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readStatFile() {
/*  41 */     if (this.statsFile.isFile()) {
/*     */       
/*     */       try {
/*     */         
/*  45 */         this.statsData.clear();
/*  46 */         this.statsData.putAll(parseJson(FileUtils.readFileToString(this.statsFile)));
/*     */       }
/*  48 */       catch (IOException ioexception) {
/*     */         
/*  50 */         LOGGER.error("Couldn't read statistics file {}", this.statsFile, ioexception);
/*     */       }
/*  52 */       catch (JsonParseException jsonparseexception) {
/*     */         
/*  54 */         LOGGER.error("Couldn't parse statistics file {}", this.statsFile, jsonparseexception);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveStatFile() {
/*     */     try {
/*  63 */       FileUtils.writeStringToFile(this.statsFile, dumpJson(this.statsData));
/*     */     }
/*  65 */     catch (IOException ioexception) {
/*     */       
/*  67 */       LOGGER.error("Couldn't save stats", ioexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unlockAchievement(EntityPlayer playerIn, StatBase statIn, int p_150873_3_) {
/*  76 */     super.unlockAchievement(playerIn, statIn, p_150873_3_);
/*  77 */     this.dirty.add(statIn);
/*     */   }
/*     */ 
/*     */   
/*     */   private Set<StatBase> getDirty() {
/*  82 */     Set<StatBase> set = Sets.newHashSet(this.dirty);
/*  83 */     this.dirty.clear();
/*  84 */     return set;
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<StatBase, TupleIntJsonSerializable> parseJson(String p_150881_1_) {
/*  89 */     JsonElement jsonelement = (new JsonParser()).parse(p_150881_1_);
/*     */     
/*  91 */     if (!jsonelement.isJsonObject())
/*     */     {
/*  93 */       return Maps.newHashMap();
/*     */     }
/*     */ 
/*     */     
/*  97 */     JsonObject jsonobject = jsonelement.getAsJsonObject();
/*  98 */     Map<StatBase, TupleIntJsonSerializable> map = Maps.newHashMap();
/*     */     
/* 100 */     for (Map.Entry<String, JsonElement> entry : (Iterable<Map.Entry<String, JsonElement>>)jsonobject.entrySet()) {
/*     */       
/* 102 */       StatBase statbase = StatList.getOneShotStat(entry.getKey());
/*     */       
/* 104 */       if (statbase != null) {
/*     */         
/* 106 */         TupleIntJsonSerializable tupleintjsonserializable = new TupleIntJsonSerializable();
/*     */         
/* 108 */         if (((JsonElement)entry.getValue()).isJsonPrimitive() && ((JsonElement)entry.getValue()).getAsJsonPrimitive().isNumber()) {
/*     */           
/* 110 */           tupleintjsonserializable.setIntegerValue(((JsonElement)entry.getValue()).getAsInt());
/*     */         }
/* 112 */         else if (((JsonElement)entry.getValue()).isJsonObject()) {
/*     */           
/* 114 */           JsonObject jsonobject1 = ((JsonElement)entry.getValue()).getAsJsonObject();
/*     */           
/* 116 */           if (jsonobject1.has("value") && jsonobject1.get("value").isJsonPrimitive() && jsonobject1.get("value").getAsJsonPrimitive().isNumber())
/*     */           {
/* 118 */             tupleintjsonserializable.setIntegerValue(jsonobject1.getAsJsonPrimitive("value").getAsInt());
/*     */           }
/*     */           
/* 121 */           if (jsonobject1.has("progress") && statbase.getSerializableClazz() != null) {
/*     */             
/*     */             try {
/*     */               
/* 125 */               Constructor<? extends IJsonSerializable> constructor = statbase.getSerializableClazz().getConstructor(new Class[0]);
/* 126 */               IJsonSerializable ijsonserializable = constructor.newInstance(new Object[0]);
/* 127 */               ijsonserializable.fromJson(jsonobject1.get("progress"));
/* 128 */               tupleintjsonserializable.setJsonSerializableValue(ijsonserializable);
/*     */             }
/* 130 */             catch (Throwable throwable) {
/*     */               
/* 132 */               LOGGER.warn("Invalid statistic progress in {}", this.statsFile, throwable);
/*     */             } 
/*     */           }
/*     */         } 
/*     */         
/* 137 */         map.put(statbase, tupleintjsonserializable);
/*     */         
/*     */         continue;
/*     */       } 
/* 141 */       LOGGER.warn("Invalid statistic in {}: Don't know what {} is", this.statsFile, entry.getKey());
/*     */     } 
/*     */ 
/*     */     
/* 145 */     return map;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static String dumpJson(Map<StatBase, TupleIntJsonSerializable> p_150880_0_) {
/* 151 */     JsonObject jsonobject = new JsonObject();
/*     */     
/* 153 */     for (Map.Entry<StatBase, TupleIntJsonSerializable> entry : p_150880_0_.entrySet()) {
/*     */       
/* 155 */       if (((TupleIntJsonSerializable)entry.getValue()).getJsonSerializableValue() != null) {
/*     */         
/* 157 */         JsonObject jsonobject1 = new JsonObject();
/* 158 */         jsonobject1.addProperty("value", Integer.valueOf(((TupleIntJsonSerializable)entry.getValue()).getIntegerValue()));
/*     */ 
/*     */         
/*     */         try {
/* 162 */           jsonobject1.add("progress", ((TupleIntJsonSerializable)entry.getValue()).getJsonSerializableValue().getSerializableElement());
/*     */         }
/* 164 */         catch (Throwable throwable) {
/*     */           
/* 166 */           LOGGER.warn("Couldn't save statistic {}: error serializing progress", ((StatBase)entry.getKey()).getStatName(), throwable);
/*     */         } 
/*     */         
/* 169 */         jsonobject.add(((StatBase)entry.getKey()).statId, (JsonElement)jsonobject1);
/*     */         
/*     */         continue;
/*     */       } 
/* 173 */       jsonobject.addProperty(((StatBase)entry.getKey()).statId, Integer.valueOf(((TupleIntJsonSerializable)entry.getValue()).getIntegerValue()));
/*     */     } 
/*     */ 
/*     */     
/* 177 */     return jsonobject.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public void markAllDirty() {
/* 182 */     this.dirty.addAll(this.statsData.keySet());
/*     */   }
/*     */ 
/*     */   
/*     */   public void sendStats(EntityPlayerMP player) {
/* 187 */     int i = this.mcServer.getTickCounter();
/* 188 */     Map<StatBase, Integer> map = Maps.newHashMap();
/*     */     
/* 190 */     if (i - this.lastStatRequest > 300) {
/*     */       
/* 192 */       this.lastStatRequest = i;
/*     */       
/* 194 */       for (StatBase statbase : getDirty())
/*     */       {
/* 196 */         map.put(statbase, Integer.valueOf(readStat(statbase)));
/*     */       }
/*     */     } 
/*     */     
/* 200 */     player.connection.sendPacket((Packet)new SPacketStatistics(map));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\stats\StatisticsManagerServer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */