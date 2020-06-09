/*     */ package net.minecraft.network.datasync;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import io.netty.handler.codec.DecoderException;
/*     */ import io.netty.handler.codec.EncoderException;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import org.apache.commons.lang3.ObjectUtils;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class EntityDataManager
/*     */ {
/*  24 */   private static final Logger LOGGER = LogManager.getLogger();
/*  25 */   private static final Map<Class<? extends Entity>, Integer> NEXT_ID_MAP = Maps.newHashMap();
/*     */   
/*     */   private final Entity entity;
/*     */   
/*  29 */   private final Map<Integer, DataEntry<?>> entries = Maps.newHashMap();
/*  30 */   private final ReadWriteLock lock = new ReentrantReadWriteLock();
/*     */   
/*     */   private boolean empty = true;
/*     */   private boolean dirty;
/*     */   
/*     */   public EntityDataManager(Entity entityIn) {
/*  36 */     this.entity = entityIn;
/*     */   }
/*     */   
/*     */   public static <T> DataParameter<T> createKey(Class<? extends Entity> clazz, DataSerializer<T> serializer) {
/*     */     int j;
/*  41 */     if (LOGGER.isDebugEnabled()) {
/*     */       
/*     */       try {
/*     */         
/*  45 */         Class<?> oclass = Class.forName(Thread.currentThread().getStackTrace()[2].getClassName());
/*     */         
/*  47 */         if (!oclass.equals(clazz))
/*     */         {
/*  49 */           LOGGER.debug("defineId called for: {} from {}", clazz, oclass, new RuntimeException());
/*     */         }
/*     */       }
/*  52 */       catch (ClassNotFoundException classNotFoundException) {}
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  60 */     if (NEXT_ID_MAP.containsKey(clazz)) {
/*     */       
/*  62 */       j = ((Integer)NEXT_ID_MAP.get(clazz)).intValue() + 1;
/*     */     }
/*     */     else {
/*     */       
/*  66 */       int i = 0;
/*  67 */       Class<?> oclass1 = clazz;
/*     */       
/*  69 */       while (oclass1 != Entity.class) {
/*     */         
/*  71 */         oclass1 = oclass1.getSuperclass();
/*     */         
/*  73 */         if (NEXT_ID_MAP.containsKey(oclass1)) {
/*     */           
/*  75 */           i = ((Integer)NEXT_ID_MAP.get(oclass1)).intValue() + 1;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*  80 */       j = i;
/*     */     } 
/*     */     
/*  83 */     if (j > 254)
/*     */     {
/*  85 */       throw new IllegalArgumentException("Data value id is too big with " + j + "! (Max is " + 'þ' + ")");
/*     */     }
/*     */ 
/*     */     
/*  89 */     NEXT_ID_MAP.put(clazz, Integer.valueOf(j));
/*  90 */     return serializer.createKey(j);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> void register(DataParameter<T> key, T value) {
/*  96 */     int i = key.getId();
/*     */     
/*  98 */     if (i > 254)
/*     */     {
/* 100 */       throw new IllegalArgumentException("Data value id is too big with " + i + "! (Max is " + 'þ' + ")");
/*     */     }
/* 102 */     if (this.entries.containsKey(Integer.valueOf(i)))
/*     */     {
/* 104 */       throw new IllegalArgumentException("Duplicate id value for " + i + "!");
/*     */     }
/* 106 */     if (DataSerializers.getSerializerId(key.getSerializer()) < 0)
/*     */     {
/* 108 */       throw new IllegalArgumentException("Unregistered serializer " + key.getSerializer() + " for " + i + "!");
/*     */     }
/*     */ 
/*     */     
/* 112 */     setEntry(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> void setEntry(DataParameter<T> key, T value) {
/* 118 */     DataEntry<T> dataentry = new DataEntry<>(key, value);
/* 119 */     this.lock.writeLock().lock();
/* 120 */     this.entries.put(Integer.valueOf(key.getId()), dataentry);
/* 121 */     this.empty = false;
/* 122 */     this.lock.writeLock().unlock();
/*     */   }
/*     */   
/*     */   private <T> DataEntry<T> getEntry(DataParameter<T> key) {
/*     */     DataEntry<T> dataentry;
/* 127 */     this.lock.readLock().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 132 */       dataentry = (DataEntry<T>)this.entries.get(Integer.valueOf(key.getId()));
/*     */     }
/* 134 */     catch (Throwable throwable) {
/*     */       
/* 136 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Getting synched entity data");
/* 137 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Synched entity data");
/* 138 */       crashreportcategory.addCrashSection("Data ID", key);
/* 139 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */     
/* 142 */     this.lock.readLock().unlock();
/* 143 */     return dataentry;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T get(DataParameter<T> key) {
/* 148 */     return getEntry(key).getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void set(DataParameter<T> key, T value) {
/* 153 */     DataEntry<T> dataentry = getEntry(key);
/*     */     
/* 155 */     if (ObjectUtils.notEqual(value, dataentry.getValue())) {
/*     */       
/* 157 */       dataentry.setValue(value);
/* 158 */       this.entity.notifyDataManagerChange(key);
/* 159 */       dataentry.setDirty(true);
/* 160 */       this.dirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void setDirty(DataParameter<T> key) {
/* 166 */     (getEntry(key)).dirty = true;
/* 167 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDirty() {
/* 172 */     return this.dirty;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void writeEntries(List<DataEntry<?>> entriesIn, PacketBuffer buf) throws IOException {
/* 177 */     if (entriesIn != null) {
/*     */       
/* 179 */       int i = 0;
/*     */       
/* 181 */       for (int j = entriesIn.size(); i < j; i++) {
/*     */         
/* 183 */         DataEntry<?> dataentry = entriesIn.get(i);
/* 184 */         writeEntry(buf, dataentry);
/*     */       } 
/*     */     } 
/*     */     
/* 188 */     buf.writeByte(255);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public List<DataEntry<?>> getDirty() {
/* 194 */     List<DataEntry<?>> list = null;
/*     */     
/* 196 */     if (this.dirty) {
/*     */       
/* 198 */       this.lock.readLock().lock();
/*     */       
/* 200 */       for (DataEntry<?> dataentry : this.entries.values()) {
/*     */         
/* 202 */         if (dataentry.isDirty()) {
/*     */           
/* 204 */           dataentry.setDirty(false);
/*     */           
/* 206 */           if (list == null)
/*     */           {
/* 208 */             list = Lists.newArrayList();
/*     */           }
/*     */           
/* 211 */           list.add(dataentry.func_192735_d());
/*     */         } 
/*     */       } 
/*     */       
/* 215 */       this.lock.readLock().unlock();
/*     */     } 
/*     */     
/* 218 */     this.dirty = false;
/* 219 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeEntries(PacketBuffer buf) throws IOException {
/* 224 */     this.lock.readLock().lock();
/*     */     
/* 226 */     for (DataEntry<?> dataentry : this.entries.values())
/*     */     {
/* 228 */       writeEntry(buf, dataentry);
/*     */     }
/*     */     
/* 231 */     this.lock.readLock().unlock();
/* 232 */     buf.writeByte(255);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public List<DataEntry<?>> getAll() {
/* 238 */     List<DataEntry<?>> list = null;
/* 239 */     this.lock.readLock().lock();
/*     */     
/* 241 */     for (DataEntry<?> dataentry : this.entries.values()) {
/*     */       
/* 243 */       if (list == null)
/*     */       {
/* 245 */         list = Lists.newArrayList();
/*     */       }
/*     */       
/* 248 */       list.add(dataentry.func_192735_d());
/*     */     } 
/*     */     
/* 251 */     this.lock.readLock().unlock();
/* 252 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> void writeEntry(PacketBuffer buf, DataEntry<T> entry) throws IOException {
/* 257 */     DataParameter<T> dataparameter = entry.getKey();
/* 258 */     int i = DataSerializers.getSerializerId(dataparameter.getSerializer());
/*     */     
/* 260 */     if (i < 0)
/*     */     {
/* 262 */       throw new EncoderException("Unknown serializer type " + dataparameter.getSerializer());
/*     */     }
/*     */ 
/*     */     
/* 266 */     buf.writeByte(dataparameter.getId());
/* 267 */     buf.writeVarIntToBuffer(i);
/* 268 */     dataparameter.getSerializer().write(buf, entry.getValue());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static List<DataEntry<?>> readEntries(PacketBuffer buf) throws IOException {
/* 275 */     List<DataEntry<?>> list = null;
/*     */     
/*     */     int i;
/* 278 */     while ((i = buf.readUnsignedByte()) != 255) {
/*     */       
/* 280 */       if (list == null)
/*     */       {
/* 282 */         list = Lists.newArrayList();
/*     */       }
/*     */       
/* 285 */       int j = buf.readVarIntFromBuffer();
/* 286 */       DataSerializer<?> dataserializer = DataSerializers.getSerializer(j);
/*     */       
/* 288 */       if (dataserializer == null)
/*     */       {
/* 290 */         throw new DecoderException("Unknown serializer type " + j);
/*     */       }
/*     */       
/* 293 */       list.add(new DataEntry(dataserializer.createKey(i), dataserializer.read(buf)));
/*     */     } 
/*     */     
/* 296 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEntryValues(List<DataEntry<?>> entriesIn) {
/* 301 */     this.lock.writeLock().lock();
/*     */     
/* 303 */     for (DataEntry<?> dataentry : entriesIn) {
/*     */       
/* 305 */       DataEntry<?> dataentry1 = this.entries.get(Integer.valueOf(dataentry.getKey().getId()));
/*     */       
/* 307 */       if (dataentry1 != null) {
/*     */         
/* 309 */         setEntryValue(dataentry1, dataentry);
/* 310 */         this.entity.notifyDataManagerChange(dataentry.getKey());
/*     */       } 
/*     */     } 
/*     */     
/* 314 */     this.lock.writeLock().unlock();
/* 315 */     this.dirty = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected <T> void setEntryValue(DataEntry<T> target, DataEntry<?> source) {
/* 320 */     target.setValue((T)source.getValue());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isEmpty() {
/* 325 */     return this.empty;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setClean() {
/* 330 */     this.dirty = false;
/* 331 */     this.lock.readLock().lock();
/*     */     
/* 333 */     for (DataEntry<?> dataentry : this.entries.values())
/*     */     {
/* 335 */       dataentry.setDirty(false);
/*     */     }
/*     */     
/* 338 */     this.lock.readLock().unlock();
/*     */   }
/*     */ 
/*     */   
/*     */   public static class DataEntry<T>
/*     */   {
/*     */     private final DataParameter<T> key;
/*     */     private T value;
/*     */     private boolean dirty;
/*     */     
/*     */     public DataEntry(DataParameter<T> keyIn, T valueIn) {
/* 349 */       this.key = keyIn;
/* 350 */       this.value = valueIn;
/* 351 */       this.dirty = true;
/*     */     }
/*     */ 
/*     */     
/*     */     public DataParameter<T> getKey() {
/* 356 */       return this.key;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setValue(T valueIn) {
/* 361 */       this.value = valueIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public T getValue() {
/* 366 */       return this.value;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isDirty() {
/* 371 */       return this.dirty;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setDirty(boolean dirtyIn) {
/* 376 */       this.dirty = dirtyIn;
/*     */     }
/*     */ 
/*     */     
/*     */     public DataEntry<T> func_192735_d() {
/* 381 */       return new DataEntry(this.key, this.key.getSerializer().func_192717_a(this.value));
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\network\datasync\EntityDataManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */