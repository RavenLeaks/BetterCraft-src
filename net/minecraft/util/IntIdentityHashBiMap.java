/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.base.Predicates;
/*     */ import com.google.common.collect.Iterators;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ 
/*     */ public class IntIdentityHashBiMap<K>
/*     */   implements IObjectIntIterable<K> {
/*  12 */   private static final Object EMPTY = null;
/*     */   
/*     */   private K[] values;
/*     */   private int[] intKeys;
/*     */   private K[] byId;
/*     */   private int nextFreeIndex;
/*     */   private int mapSize;
/*     */   
/*     */   public IntIdentityHashBiMap(int initialCapacity) {
/*  21 */     initialCapacity = (int)(initialCapacity / 0.8F);
/*  22 */     this.values = (K[])new Object[initialCapacity];
/*  23 */     this.intKeys = new int[initialCapacity];
/*  24 */     this.byId = (K[])new Object[initialCapacity];
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId(@Nullable K p_186815_1_) {
/*  29 */     return getValue(getIndex(p_186815_1_, hashObject(p_186815_1_)));
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public K get(int idIn) {
/*  35 */     return (idIn >= 0 && idIn < this.byId.length) ? this.byId[idIn] : null;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getValue(int p_186805_1_) {
/*  40 */     return (p_186805_1_ == -1) ? -1 : this.intKeys[p_186805_1_];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int add(K objectIn) {
/*  48 */     int i = nextId();
/*  49 */     put(objectIn, i);
/*  50 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   private int nextId() {
/*  55 */     while (this.nextFreeIndex < this.byId.length && this.byId[this.nextFreeIndex] != null)
/*     */     {
/*  57 */       this.nextFreeIndex++;
/*     */     }
/*     */     
/*  60 */     return this.nextFreeIndex;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void grow(int capacity) {
/*  68 */     K[] arrayOfK = this.values;
/*  69 */     int[] aint = this.intKeys;
/*  70 */     this.values = (K[])new Object[capacity];
/*  71 */     this.intKeys = new int[capacity];
/*  72 */     this.byId = (K[])new Object[capacity];
/*  73 */     this.nextFreeIndex = 0;
/*  74 */     this.mapSize = 0;
/*     */     
/*  76 */     for (int i = 0; i < arrayOfK.length; i++) {
/*     */       
/*  78 */       if (arrayOfK[i] != null)
/*     */       {
/*  80 */         put(arrayOfK[i], aint[i]);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void put(K objectIn, int intKey) {
/*  90 */     int i = Math.max(intKey, this.mapSize + 1);
/*     */     
/*  92 */     if (i >= this.values.length * 0.8F) {
/*     */       int j;
/*     */ 
/*     */       
/*  96 */       for (j = this.values.length << 1; j < intKey; j <<= 1);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 101 */       grow(j);
/*     */     } 
/*     */     
/* 104 */     int k = findEmpty(hashObject(objectIn));
/* 105 */     this.values[k] = objectIn;
/* 106 */     this.intKeys[k] = intKey;
/* 107 */     this.byId[intKey] = objectIn;
/* 108 */     this.mapSize++;
/*     */     
/* 110 */     if (intKey == this.nextFreeIndex)
/*     */     {
/* 112 */       this.nextFreeIndex++;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private int hashObject(@Nullable K obectIn) {
/* 118 */     return (MathHelper.hash(System.identityHashCode(obectIn)) & Integer.MAX_VALUE) % this.values.length;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getIndex(@Nullable K objectIn, int p_186816_2_) {
/* 123 */     for (int i = p_186816_2_; i < this.values.length; i++) {
/*     */       
/* 125 */       if (this.values[i] == objectIn)
/*     */       {
/* 127 */         return i;
/*     */       }
/*     */       
/* 130 */       if (this.values[i] == EMPTY)
/*     */       {
/* 132 */         return -1;
/*     */       }
/*     */     } 
/*     */     
/* 136 */     for (int j = 0; j < p_186816_2_; j++) {
/*     */       
/* 138 */       if (this.values[j] == objectIn)
/*     */       {
/* 140 */         return j;
/*     */       }
/*     */       
/* 143 */       if (this.values[j] == EMPTY)
/*     */       {
/* 145 */         return -1;
/*     */       }
/*     */     } 
/*     */     
/* 149 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   private int findEmpty(int p_186806_1_) {
/* 154 */     for (int i = p_186806_1_; i < this.values.length; i++) {
/*     */       
/* 156 */       if (this.values[i] == EMPTY)
/*     */       {
/* 158 */         return i;
/*     */       }
/*     */     } 
/*     */     
/* 162 */     for (int j = 0; j < p_186806_1_; j++) {
/*     */       
/* 164 */       if (this.values[j] == EMPTY)
/*     */       {
/* 166 */         return j;
/*     */       }
/*     */     } 
/*     */     
/* 170 */     throw new RuntimeException("Overflowed :(");
/*     */   }
/*     */ 
/*     */   
/*     */   public Iterator<K> iterator() {
/* 175 */     return (Iterator<K>)Iterators.filter((Iterator)Iterators.forArray((Object[])this.byId), Predicates.notNull());
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 180 */     Arrays.fill((Object[])this.values, (Object)null);
/* 181 */     Arrays.fill((Object[])this.byId, (Object)null);
/* 182 */     this.nextFreeIndex = 0;
/* 183 */     this.mapSize = 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 188 */     return this.mapSize;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\IntIdentityHashBiMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */