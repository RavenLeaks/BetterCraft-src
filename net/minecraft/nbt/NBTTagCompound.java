/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.crash.CrashReport;
/*     */ import net.minecraft.crash.CrashReportCategory;
/*     */ import net.minecraft.crash.ICrashReportDetail;
/*     */ import net.minecraft.util.ReportedException;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class NBTTagCompound
/*     */   extends NBTBase
/*     */ {
/*     */   public NBTTagCompound() {
/*  28 */     this.tagMap = Maps.newHashMap();
/*     */   }
/*     */ 
/*     */   
/*     */   private static final Logger field_191551_b = LogManager.getLogger();
/*     */   
/*     */   void write(DataOutput output) throws IOException {
/*  35 */     for (String s : this.tagMap.keySet()) {
/*     */       
/*  37 */       NBTBase nbtbase = this.tagMap.get(s);
/*  38 */       writeEntry(s, nbtbase, output);
/*     */     } 
/*     */     
/*  41 */     output.writeByte(0);
/*     */   }
/*     */   private static final Pattern field_193583_c = Pattern.compile("[A-Za-z0-9._+-]+"); private final Map<String, NBTBase> tagMap;
/*     */   
/*     */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/*  46 */     sizeTracker.read(384L);
/*     */     
/*  48 */     if (depth > 512)
/*     */     {
/*  50 */       throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
/*     */     }
/*     */ 
/*     */     
/*  54 */     this.tagMap.clear();
/*     */     
/*     */     byte b0;
/*  57 */     while ((b0 = readType(input, sizeTracker)) != 0) {
/*     */       
/*  59 */       String s = readKey(input, sizeTracker);
/*  60 */       sizeTracker.read((224 + 16 * s.length()));
/*  61 */       NBTBase nbtbase = readNBT(b0, s, input, depth + 1, sizeTracker);
/*     */       
/*  63 */       if (this.tagMap.put(s, nbtbase) != null)
/*     */       {
/*  65 */         sizeTracker.read(288L);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> getKeySet() {
/*  73 */     return this.tagMap.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  81 */     return 10;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/*  86 */     return this.tagMap.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTag(String key, NBTBase value) {
/*  94 */     this.tagMap.put(key, value);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByte(String key, byte value) {
/* 102 */     this.tagMap.put(key, new NBTTagByte(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setShort(String key, short value) {
/* 110 */     this.tagMap.put(key, new NBTTagShort(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setInteger(String key, int value) {
/* 118 */     this.tagMap.put(key, new NBTTagInt(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLong(String key, long value) {
/* 126 */     this.tagMap.put(key, new NBTTagLong(value));
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUniqueId(String key, UUID value) {
/* 131 */     setLong(String.valueOf(key) + "Most", value.getMostSignificantBits());
/* 132 */     setLong(String.valueOf(key) + "Least", value.getLeastSignificantBits());
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public UUID getUniqueId(String key) {
/* 138 */     return new UUID(getLong(String.valueOf(key) + "Most"), getLong(String.valueOf(key) + "Least"));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasUniqueId(String key) {
/* 143 */     return (hasKey(String.valueOf(key) + "Most", 99) && hasKey(String.valueOf(key) + "Least", 99));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFloat(String key, float value) {
/* 151 */     this.tagMap.put(key, new NBTTagFloat(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setDouble(String key, double value) {
/* 159 */     this.tagMap.put(key, new NBTTagDouble(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setString(String key, String value) {
/* 167 */     this.tagMap.put(key, new NBTTagString(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setByteArray(String key, byte[] value) {
/* 175 */     this.tagMap.put(key, new NBTTagByteArray(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIntArray(String key, int[] value) {
/* 183 */     this.tagMap.put(key, new NBTTagIntArray(value));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBoolean(String key, boolean value) {
/* 191 */     setByte(key, (byte)(value ? 1 : 0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase getTag(String key) {
/* 199 */     return this.tagMap.get(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getTagId(String key) {
/* 207 */     NBTBase nbtbase = this.tagMap.get(key);
/* 208 */     return (nbtbase == null) ? 0 : nbtbase.getId();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasKey(String key) {
/* 216 */     return this.tagMap.containsKey(key);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasKey(String key, int type) {
/* 226 */     int i = getTagId(key);
/*     */     
/* 228 */     if (i == type)
/*     */     {
/* 230 */       return true;
/*     */     }
/* 232 */     if (type != 99)
/*     */     {
/* 234 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 238 */     return !(i != 1 && i != 2 && i != 3 && i != 4 && i != 5 && i != 6);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getByte(String key) {
/*     */     try {
/* 249 */       if (hasKey(key, 99))
/*     */       {
/* 251 */         return ((NBTPrimitive)this.tagMap.get(key)).getByte();
/*     */       }
/*     */     }
/* 254 */     catch (ClassCastException classCastException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 259 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public short getShort(String key) {
/*     */     try {
/* 269 */       if (hasKey(key, 99))
/*     */       {
/* 271 */         return ((NBTPrimitive)this.tagMap.get(key)).getShort();
/*     */       }
/*     */     }
/* 274 */     catch (ClassCastException classCastException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getInteger(String key) {
/*     */     try {
/* 289 */       if (hasKey(key, 99))
/*     */       {
/* 291 */         return ((NBTPrimitive)this.tagMap.get(key)).getInt();
/*     */       }
/*     */     }
/* 294 */     catch (ClassCastException classCastException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 299 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getLong(String key) {
/*     */     try {
/* 309 */       if (hasKey(key, 99))
/*     */       {
/* 311 */         return ((NBTPrimitive)this.tagMap.get(key)).getLong();
/*     */       }
/*     */     }
/* 314 */     catch (ClassCastException classCastException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 319 */     return 0L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getFloat(String key) {
/*     */     try {
/* 329 */       if (hasKey(key, 99))
/*     */       {
/* 331 */         return ((NBTPrimitive)this.tagMap.get(key)).getFloat();
/*     */       }
/*     */     }
/* 334 */     catch (ClassCastException classCastException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 339 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDouble(String key) {
/*     */     try {
/* 349 */       if (hasKey(key, 99))
/*     */       {
/* 351 */         return ((NBTPrimitive)this.tagMap.get(key)).getDouble();
/*     */       }
/*     */     }
/* 354 */     catch (ClassCastException classCastException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 359 */     return 0.0D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getString(String key) {
/*     */     try {
/* 369 */       if (hasKey(key, 8))
/*     */       {
/* 371 */         return ((NBTBase)this.tagMap.get(key)).getString();
/*     */       }
/*     */     }
/* 374 */     catch (ClassCastException classCastException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 379 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] getByteArray(String key) {
/*     */     try {
/* 389 */       if (hasKey(key, 7))
/*     */       {
/* 391 */         return ((NBTTagByteArray)this.tagMap.get(key)).getByteArray();
/*     */       }
/*     */     }
/* 394 */     catch (ClassCastException classcastexception) {
/*     */       
/* 396 */       throw new ReportedException(createCrashReport(key, 7, classcastexception));
/*     */     } 
/*     */     
/* 399 */     return new byte[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] getIntArray(String key) {
/*     */     try {
/* 409 */       if (hasKey(key, 11))
/*     */       {
/* 411 */         return ((NBTTagIntArray)this.tagMap.get(key)).getIntArray();
/*     */       }
/*     */     }
/* 414 */     catch (ClassCastException classcastexception) {
/*     */       
/* 416 */       throw new ReportedException(createCrashReport(key, 11, classcastexception));
/*     */     } 
/*     */     
/* 419 */     return new int[0];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getCompoundTag(String key) {
/*     */     try {
/* 430 */       if (hasKey(key, 10))
/*     */       {
/* 432 */         return (NBTTagCompound)this.tagMap.get(key);
/*     */       }
/*     */     }
/* 435 */     catch (ClassCastException classcastexception) {
/*     */       
/* 437 */       throw new ReportedException(createCrashReport(key, 10, classcastexception));
/*     */     } 
/*     */     
/* 440 */     return new NBTTagCompound();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagList getTagList(String key, int type) {
/*     */     try {
/* 450 */       if (getTagId(key) == 9)
/*     */       {
/* 452 */         NBTTagList nbttaglist = (NBTTagList)this.tagMap.get(key);
/*     */         
/* 454 */         if (!nbttaglist.hasNoTags() && nbttaglist.getTagType() != type)
/*     */         {
/* 456 */           return new NBTTagList();
/*     */         }
/*     */         
/* 459 */         return nbttaglist;
/*     */       }
/*     */     
/* 462 */     } catch (ClassCastException classcastexception) {
/*     */       
/* 464 */       throw new ReportedException(createCrashReport(key, 9, classcastexception));
/*     */     } 
/*     */     
/* 467 */     return new NBTTagList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getBoolean(String key) {
/* 476 */     return (getByte(key) != 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeTag(String key) {
/* 484 */     this.tagMap.remove(key);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 489 */     StringBuilder stringbuilder = new StringBuilder("{");
/* 490 */     Collection<String> collection = this.tagMap.keySet();
/*     */     
/* 492 */     if (field_191551_b.isDebugEnabled()) {
/*     */       
/* 494 */       List<String> list = Lists.newArrayList(this.tagMap.keySet());
/* 495 */       Collections.sort(list);
/* 496 */       collection = list;
/*     */     } 
/*     */     
/* 499 */     for (String s : collection) {
/*     */       
/* 501 */       if (stringbuilder.length() != 1)
/*     */       {
/* 503 */         stringbuilder.append(',');
/*     */       }
/*     */       
/* 506 */       stringbuilder.append(func_193582_s(s)).append(':').append(this.tagMap.get(s));
/*     */     } 
/*     */     
/* 509 */     return stringbuilder.append('}').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNoTags() {
/* 517 */     return this.tagMap.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private CrashReport createCrashReport(final String key, final int expectedType, ClassCastException ex) {
/* 525 */     CrashReport crashreport = CrashReport.makeCrashReport(ex, "Reading NBT data");
/* 526 */     CrashReportCategory crashreportcategory = crashreport.makeCategoryDepth("Corrupt NBT tag", 1);
/* 527 */     crashreportcategory.setDetail("Tag type found", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 531 */             return NBTBase.NBT_TYPES[((NBTBase)NBTTagCompound.this.tagMap.get(key)).getId()];
/*     */           }
/*     */         });
/* 534 */     crashreportcategory.setDetail("Tag type expected", new ICrashReportDetail<String>()
/*     */         {
/*     */           public String call() throws Exception
/*     */           {
/* 538 */             return NBTBase.NBT_TYPES[expectedType];
/*     */           }
/*     */         });
/* 541 */     crashreportcategory.addCrashSection("Tag name", key);
/* 542 */     return crashreport;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound copy() {
/* 550 */     NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */     
/* 552 */     for (String s : this.tagMap.keySet())
/*     */     {
/* 554 */       nbttagcompound.setTag(s, ((NBTBase)this.tagMap.get(s)).copy());
/*     */     }
/*     */     
/* 557 */     return nbttagcompound;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 562 */     return (super.equals(p_equals_1_) && Objects.equals(this.tagMap.entrySet(), ((NBTTagCompound)p_equals_1_).tagMap.entrySet()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 567 */     return super.hashCode() ^ this.tagMap.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   private static void writeEntry(String name, NBTBase data, DataOutput output) throws IOException {
/* 572 */     output.writeByte(data.getId());
/*     */     
/* 574 */     if (data.getId() != 0) {
/*     */       
/* 576 */       output.writeUTF(name);
/* 577 */       data.write(output);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static byte readType(DataInput input, NBTSizeTracker sizeTracker) throws IOException {
/* 583 */     return input.readByte();
/*     */   }
/*     */ 
/*     */   
/*     */   private static String readKey(DataInput input, NBTSizeTracker sizeTracker) throws IOException {
/* 588 */     return input.readUTF();
/*     */   }
/*     */ 
/*     */   
/*     */   static NBTBase readNBT(byte id, String key, DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/* 593 */     NBTBase nbtbase = NBTBase.createNewByType(id);
/*     */ 
/*     */     
/*     */     try {
/* 597 */       nbtbase.read(input, depth, sizeTracker);
/* 598 */       return nbtbase;
/*     */     }
/* 600 */     catch (IOException ioexception) {
/*     */       
/* 602 */       CrashReport crashreport = CrashReport.makeCrashReport(ioexception, "Loading NBT data");
/* 603 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("NBT Tag");
/* 604 */       crashreportcategory.addCrashSection("Tag name", key);
/* 605 */       crashreportcategory.addCrashSection("Tag type", Byte.valueOf(id));
/* 606 */       throw new ReportedException(crashreport);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void merge(NBTTagCompound other) {
/* 616 */     for (String s : other.tagMap.keySet()) {
/*     */       
/* 618 */       NBTBase nbtbase = other.tagMap.get(s);
/*     */       
/* 620 */       if (nbtbase.getId() == 10) {
/*     */         
/* 622 */         if (hasKey(s, 10)) {
/*     */           
/* 624 */           NBTTagCompound nbttagcompound = getCompoundTag(s);
/* 625 */           nbttagcompound.merge((NBTTagCompound)nbtbase);
/*     */           
/*     */           continue;
/*     */         } 
/* 629 */         setTag(s, nbtbase.copy());
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/* 634 */       setTag(s, nbtbase.copy());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static String func_193582_s(String p_193582_0_) {
/* 641 */     return field_193583_c.matcher(p_193582_0_).matches() ? p_193582_0_ : NBTTagString.func_193588_a(p_193582_0_);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\nbt\NBTTagCompound.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */