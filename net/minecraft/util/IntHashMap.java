/*     */ package net.minecraft.util;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ public class IntHashMap<V>
/*     */ {
/*   7 */   private transient Entry<V>[] slots = (Entry<V>[])new Entry[16];
/*     */ 
/*     */   
/*     */   private transient int count;
/*     */ 
/*     */   
/*  13 */   private int threshold = 12;
/*     */ 
/*     */   
/*  16 */   private final float growFactor = 0.75F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int computeHash(int integer) {
/*  23 */     integer = integer ^ integer >>> 20 ^ integer >>> 12;
/*  24 */     return integer ^ integer >>> 7 ^ integer >>> 4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getSlotIndex(int hash, int slotCount) {
/*  32 */     return hash & slotCount - 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public V lookup(int hashEntry) {
/*  42 */     int i = computeHash(hashEntry);
/*     */     
/*  44 */     for (Entry<V> entry = this.slots[getSlotIndex(i, this.slots.length)]; entry != null; entry = entry.nextEntry) {
/*     */       
/*  46 */       if (entry.hashEntry == hashEntry)
/*     */       {
/*  48 */         return entry.valueEntry;
/*     */       }
/*     */     } 
/*     */     
/*  52 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean containsItem(int hashEntry) {
/*  60 */     return (lookupEntry(hashEntry) != null);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   final Entry<V> lookupEntry(int hashEntry) {
/*  66 */     int i = computeHash(hashEntry);
/*     */     
/*  68 */     for (Entry<V> entry = this.slots[getSlotIndex(i, this.slots.length)]; entry != null; entry = entry.nextEntry) {
/*     */       
/*  70 */       if (entry.hashEntry == hashEntry)
/*     */       {
/*  72 */         return entry;
/*     */       }
/*     */     } 
/*     */     
/*  76 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addKey(int hashEntry, V valueEntry) {
/*  84 */     int i = computeHash(hashEntry);
/*  85 */     int j = getSlotIndex(i, this.slots.length);
/*     */     
/*  87 */     for (Entry<V> entry = this.slots[j]; entry != null; entry = entry.nextEntry) {
/*     */       
/*  89 */       if (entry.hashEntry == hashEntry) {
/*     */         
/*  91 */         entry.valueEntry = valueEntry;
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/*  96 */     insert(i, hashEntry, valueEntry, j);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void grow(int p_76047_1_) {
/* 104 */     Entry<V>[] arrayOfEntry = this.slots;
/* 105 */     int i = arrayOfEntry.length;
/*     */     
/* 107 */     if (i == 1073741824) {
/*     */       
/* 109 */       this.threshold = Integer.MAX_VALUE;
/*     */     }
/*     */     else {
/*     */       
/* 113 */       Entry[] entry1 = new Entry[p_76047_1_];
/* 114 */       copyTo((Entry<V>[])entry1);
/* 115 */       this.slots = (Entry<V>[])entry1;
/* 116 */       this.threshold = (int)(p_76047_1_ * 0.75F);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void copyTo(Entry[] p_76048_1_) {
/* 125 */     Entry<V>[] arrayOfEntry = this.slots;
/* 126 */     int i = p_76048_1_.length;
/*     */     
/* 128 */     for (int j = 0; j < arrayOfEntry.length; j++) {
/*     */       
/* 130 */       Entry<V> entry1 = arrayOfEntry[j];
/*     */       
/* 132 */       if (entry1 != null) {
/*     */         Entry<V> entry2;
/* 134 */         arrayOfEntry[j] = null;
/*     */ 
/*     */         
/*     */         do {
/* 138 */           entry2 = entry1.nextEntry;
/* 139 */           int k = getSlotIndex(entry1.slotHash, i);
/* 140 */           entry1.nextEntry = p_76048_1_[k];
/* 141 */           p_76048_1_[k] = entry1;
/* 142 */           entry1 = entry2;
/*     */         }
/* 144 */         while (entry2 != null);
/*     */       } 
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
/*     */   @Nullable
/*     */   public V removeObject(int p_76049_1_) {
/* 160 */     Entry<V> entry = removeEntry(p_76049_1_);
/* 161 */     return (entry == null) ? null : entry.valueEntry;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   final Entry<V> removeEntry(int p_76036_1_) {
/* 167 */     int i = computeHash(p_76036_1_);
/* 168 */     int j = getSlotIndex(i, this.slots.length);
/* 169 */     Entry<V> entry = this.slots[j];
/*     */     
/*     */     Entry<V> entry1;
/*     */     
/* 173 */     for (entry1 = entry; entry1 != null; entry1 = entry2) {
/*     */       
/* 175 */       Entry<V> entry2 = entry1.nextEntry;
/*     */       
/* 177 */       if (entry1.hashEntry == p_76036_1_) {
/*     */         
/* 179 */         this.count--;
/*     */         
/* 181 */         if (entry == entry1) {
/*     */           
/* 183 */           this.slots[j] = entry2;
/*     */         }
/*     */         else {
/*     */           
/* 187 */           entry.nextEntry = entry2;
/*     */         } 
/*     */         
/* 190 */         return entry1;
/*     */       } 
/*     */       
/* 193 */       entry = entry1;
/*     */     } 
/*     */     
/* 196 */     return entry1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearMap() {
/* 204 */     Entry<V>[] arrayOfEntry = this.slots;
/*     */     
/* 206 */     for (int i = 0; i < arrayOfEntry.length; i++)
/*     */     {
/* 208 */       arrayOfEntry[i] = null;
/*     */     }
/*     */     
/* 211 */     this.count = 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void insert(int p_76040_1_, int p_76040_2_, V p_76040_3_, int p_76040_4_) {
/* 219 */     Entry<V> entry = this.slots[p_76040_4_];
/* 220 */     this.slots[p_76040_4_] = new Entry<>(p_76040_1_, p_76040_2_, p_76040_3_, entry);
/*     */     
/* 222 */     if (this.count++ >= this.threshold)
/*     */     {
/* 224 */       grow(2 * this.slots.length);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static class Entry<V>
/*     */   {
/*     */     final int hashEntry;
/*     */     V valueEntry;
/*     */     Entry<V> nextEntry;
/*     */     final int slotHash;
/*     */     
/*     */     Entry(int p_i1552_1_, int p_i1552_2_, V p_i1552_3_, Entry<V> p_i1552_4_) {
/* 237 */       this.valueEntry = p_i1552_3_;
/* 238 */       this.nextEntry = p_i1552_4_;
/* 239 */       this.hashEntry = p_i1552_2_;
/* 240 */       this.slotHash = p_i1552_1_;
/*     */     }
/*     */ 
/*     */     
/*     */     public final int getHash() {
/* 245 */       return this.hashEntry;
/*     */     }
/*     */ 
/*     */     
/*     */     public final V getValue() {
/* 250 */       return this.valueEntry;
/*     */     }
/*     */ 
/*     */     
/*     */     public final boolean equals(Object p_equals_1_) {
/* 255 */       if (!(p_equals_1_ instanceof Entry))
/*     */       {
/* 257 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 261 */       Entry<V> entry = (Entry<V>)p_equals_1_;
/*     */       
/* 263 */       if (this.hashEntry == entry.hashEntry) {
/*     */         
/* 265 */         Object object = getValue();
/* 266 */         Object object1 = entry.getValue();
/*     */         
/* 268 */         if (object == object1 || (object != null && object.equals(object1)))
/*     */         {
/* 270 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 274 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public final int hashCode() {
/* 280 */       return IntHashMap.computeHash(this.hashEntry);
/*     */     }
/*     */ 
/*     */     
/*     */     public final String toString() {
/* 285 */       return String.valueOf(getHash()) + "=" + getValue();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\IntHashMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */