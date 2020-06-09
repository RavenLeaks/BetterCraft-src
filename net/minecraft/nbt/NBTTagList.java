/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ public class NBTTagList
/*     */   extends NBTBase {
/*  14 */   private static final Logger LOGGER = LogManager.getLogger();
/*  15 */   private List<NBTBase> tagList = Lists.newArrayList();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  20 */   private byte tagType = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutput output) throws IOException {
/*  27 */     if (this.tagList.isEmpty()) {
/*     */       
/*  29 */       this.tagType = 0;
/*     */     }
/*     */     else {
/*     */       
/*  33 */       this.tagType = ((NBTBase)this.tagList.get(0)).getId();
/*     */     } 
/*     */     
/*  36 */     output.writeByte(this.tagType);
/*  37 */     output.writeInt(this.tagList.size());
/*     */     
/*  39 */     for (int i = 0; i < this.tagList.size(); i++)
/*     */     {
/*  41 */       ((NBTBase)this.tagList.get(i)).write(output);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/*  47 */     sizeTracker.read(296L);
/*     */     
/*  49 */     if (depth > 512)
/*     */     {
/*  51 */       throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
/*     */     }
/*     */ 
/*     */     
/*  55 */     this.tagType = input.readByte();
/*  56 */     int i = input.readInt();
/*     */     
/*  58 */     if (this.tagType == 0 && i > 0)
/*     */     {
/*  60 */       throw new RuntimeException("Missing type on ListTag");
/*     */     }
/*     */ 
/*     */     
/*  64 */     sizeTracker.read(32L * i);
/*  65 */     this.tagList = Lists.newArrayListWithCapacity(i);
/*     */     
/*  67 */     for (int j = 0; j < i; j++) {
/*     */       
/*  69 */       NBTBase nbtbase = NBTBase.createNewByType(this.tagType);
/*  70 */       nbtbase.read(input, depth + 1, sizeTracker);
/*  71 */       this.tagList.add(nbtbase);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  82 */     return 9;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  87 */     StringBuilder stringbuilder = new StringBuilder("[");
/*     */     
/*  89 */     for (int i = 0; i < this.tagList.size(); i++) {
/*     */       
/*  91 */       if (i != 0)
/*     */       {
/*  93 */         stringbuilder.append(',');
/*     */       }
/*     */       
/*  96 */       stringbuilder.append(this.tagList.get(i));
/*     */     } 
/*     */     
/*  99 */     return stringbuilder.append(']').toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void appendTag(NBTBase nbt) {
/* 108 */     if (nbt.getId() == 0) {
/*     */       
/* 110 */       LOGGER.warn("Invalid TagEnd added to ListTag");
/*     */     }
/*     */     else {
/*     */       
/* 114 */       if (this.tagType == 0) {
/*     */         
/* 116 */         this.tagType = nbt.getId();
/*     */       }
/* 118 */       else if (this.tagType != nbt.getId()) {
/*     */         
/* 120 */         LOGGER.warn("Adding mismatching tag types to tag list");
/*     */         
/*     */         return;
/*     */       } 
/* 124 */       this.tagList.add(nbt);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void set(int idx, NBTBase nbt) {
/* 133 */     if (nbt.getId() == 0) {
/*     */       
/* 135 */       LOGGER.warn("Invalid TagEnd added to ListTag");
/*     */     }
/* 137 */     else if (idx >= 0 && idx < this.tagList.size()) {
/*     */       
/* 139 */       if (this.tagType == 0) {
/*     */         
/* 141 */         this.tagType = nbt.getId();
/*     */       }
/* 143 */       else if (this.tagType != nbt.getId()) {
/*     */         
/* 145 */         LOGGER.warn("Adding mismatching tag types to tag list");
/*     */         
/*     */         return;
/*     */       } 
/* 149 */       this.tagList.set(idx, nbt);
/*     */     }
/*     */     else {
/*     */       
/* 153 */       LOGGER.warn("index out of bounds to set tag in tag list");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase removeTag(int i) {
/* 162 */     return this.tagList.remove(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNoTags() {
/* 170 */     return this.tagList.isEmpty();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagCompound getCompoundTagAt(int i) {
/* 178 */     if (i >= 0 && i < this.tagList.size()) {
/*     */       
/* 180 */       NBTBase nbtbase = this.tagList.get(i);
/*     */       
/* 182 */       if (nbtbase.getId() == 10)
/*     */       {
/* 184 */         return (NBTTagCompound)nbtbase;
/*     */       }
/*     */     } 
/*     */     
/* 188 */     return new NBTTagCompound();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getIntAt(int p_186858_1_) {
/* 193 */     if (p_186858_1_ >= 0 && p_186858_1_ < this.tagList.size()) {
/*     */       
/* 195 */       NBTBase nbtbase = this.tagList.get(p_186858_1_);
/*     */       
/* 197 */       if (nbtbase.getId() == 3)
/*     */       {
/* 199 */         return ((NBTTagInt)nbtbase).getInt();
/*     */       }
/*     */     } 
/*     */     
/* 203 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getIntArrayAt(int i) {
/* 208 */     if (i >= 0 && i < this.tagList.size()) {
/*     */       
/* 210 */       NBTBase nbtbase = this.tagList.get(i);
/*     */       
/* 212 */       if (nbtbase.getId() == 11)
/*     */       {
/* 214 */         return ((NBTTagIntArray)nbtbase).getIntArray();
/*     */       }
/*     */     } 
/*     */     
/* 218 */     return new int[0];
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDoubleAt(int i) {
/* 223 */     if (i >= 0 && i < this.tagList.size()) {
/*     */       
/* 225 */       NBTBase nbtbase = this.tagList.get(i);
/*     */       
/* 227 */       if (nbtbase.getId() == 6)
/*     */       {
/* 229 */         return ((NBTTagDouble)nbtbase).getDouble();
/*     */       }
/*     */     } 
/*     */     
/* 233 */     return 0.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloatAt(int i) {
/* 238 */     if (i >= 0 && i < this.tagList.size()) {
/*     */       
/* 240 */       NBTBase nbtbase = this.tagList.get(i);
/*     */       
/* 242 */       if (nbtbase.getId() == 5)
/*     */       {
/* 244 */         return ((NBTTagFloat)nbtbase).getFloat();
/*     */       }
/*     */     } 
/*     */     
/* 248 */     return 0.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getStringTagAt(int i) {
/* 256 */     if (i >= 0 && i < this.tagList.size()) {
/*     */       
/* 258 */       NBTBase nbtbase = this.tagList.get(i);
/* 259 */       return (nbtbase.getId() == 8) ? nbtbase.getString() : nbtbase.toString();
/*     */     } 
/*     */ 
/*     */     
/* 263 */     return "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase get(int idx) {
/* 272 */     return (idx >= 0 && idx < this.tagList.size()) ? this.tagList.get(idx) : new NBTTagEnd();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tagCount() {
/* 280 */     return this.tagList.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTTagList copy() {
/* 288 */     NBTTagList nbttaglist = new NBTTagList();
/* 289 */     nbttaglist.tagType = this.tagType;
/*     */     
/* 291 */     for (NBTBase nbtbase : this.tagList) {
/*     */       
/* 293 */       NBTBase nbtbase1 = nbtbase.copy();
/* 294 */       nbttaglist.tagList.add(nbtbase1);
/*     */     } 
/*     */     
/* 297 */     return nbttaglist;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/* 302 */     if (!super.equals(p_equals_1_))
/*     */     {
/* 304 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 308 */     NBTTagList nbttaglist = (NBTTagList)p_equals_1_;
/* 309 */     return (this.tagType == nbttaglist.tagType && Objects.equals(this.tagList, nbttaglist.tagList));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 315 */     return super.hashCode() ^ this.tagList.hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTagType() {
/* 320 */     return this.tagType;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\nbt\NBTTagList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */