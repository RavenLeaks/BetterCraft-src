/*     */ package net.minecraft.client.util;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import it.unimi.dsi.fastutil.Arrays;
/*     */ import it.unimi.dsi.fastutil.Swapper;
/*     */ import it.unimi.dsi.fastutil.ints.IntArrayList;
/*     */ import it.unimi.dsi.fastutil.ints.IntComparator;
/*     */ import it.unimi.dsi.fastutil.ints.IntList;
/*     */ import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SuffixArray<T>
/*     */ {
/*     */   public SuffixArray() {
/*  23 */     this.field_194061_a = Lists.newArrayList();
/*  24 */     this.field_194065_e = (IntList)new IntArrayList();
/*  25 */     this.field_194066_f = (IntList)new IntArrayList();
/*  26 */     this.field_194067_g = (IntList)new IntArrayList();
/*  27 */     this.field_194068_h = (IntList)new IntArrayList();
/*     */   }
/*     */   private static final boolean field_194062_b = Boolean.parseBoolean(System.getProperty("SuffixArray.printComparisons", "false")); private static final boolean field_194063_c = Boolean.parseBoolean(System.getProperty("SuffixArray.printArray", "false")); private static final Logger field_194064_d = LogManager.getLogger(); protected final List<T> field_194061_a;
/*     */   
/*     */   public void func_194057_a(T p_194057_1_, String p_194057_2_) {
/*  32 */     this.field_194069_i = Math.max(this.field_194069_i, p_194057_2_.length());
/*  33 */     int i = this.field_194061_a.size();
/*  34 */     this.field_194061_a.add(p_194057_1_);
/*  35 */     this.field_194066_f.add(this.field_194065_e.size());
/*     */     
/*  37 */     for (int j = 0; j < p_194057_2_.length(); j++) {
/*     */       
/*  39 */       this.field_194067_g.add(i);
/*  40 */       this.field_194068_h.add(j);
/*  41 */       this.field_194065_e.add(p_194057_2_.charAt(j));
/*     */     } 
/*     */     
/*  44 */     this.field_194067_g.add(i);
/*  45 */     this.field_194068_h.add(p_194057_2_.length());
/*  46 */     this.field_194065_e.add(-1);
/*     */   }
/*     */   private final IntList field_194065_e; private final IntList field_194066_f; private IntList field_194067_g; private IntList field_194068_h; private int field_194069_i;
/*     */   
/*     */   public void func_194058_a() {
/*  51 */     int i = this.field_194065_e.size();
/*  52 */     int[] aint = new int[i];
/*  53 */     final int[] aint1 = new int[i];
/*  54 */     final int[] aint2 = new int[i];
/*  55 */     int[] aint3 = new int[i];
/*  56 */     IntComparator intcomparator = new IntComparator()
/*     */       {
/*     */         public int compare(int p_compare_1_, int p_compare_2_)
/*     */         {
/*  60 */           return (aint1[p_compare_1_] == aint1[p_compare_2_]) ? Integer.compare(aint2[p_compare_1_], aint2[p_compare_2_]) : Integer.compare(aint1[p_compare_1_], aint1[p_compare_2_]);
/*     */         }
/*     */         
/*     */         public int compare(Integer p_compare_1_, Integer p_compare_2_) {
/*  64 */           return compare(p_compare_1_.intValue(), p_compare_2_.intValue());
/*     */         }
/*     */       };
/*  67 */     Swapper swapper = (p_194054_3_, p_194054_4_) -> {
/*     */         if (p_194054_3_ != p_194054_4_) {
/*     */           int i2 = paramArrayOfint1[p_194054_3_];
/*     */           
/*     */           paramArrayOfint1[p_194054_3_] = paramArrayOfint1[p_194054_4_];
/*     */           
/*     */           paramArrayOfint1[p_194054_4_] = i2;
/*     */           
/*     */           i2 = paramArrayOfint2[p_194054_3_];
/*     */           paramArrayOfint2[p_194054_3_] = paramArrayOfint2[p_194054_4_];
/*     */           paramArrayOfint2[p_194054_4_] = i2;
/*     */           i2 = paramArrayOfint3[p_194054_3_];
/*     */           paramArrayOfint3[p_194054_3_] = paramArrayOfint3[p_194054_4_];
/*     */           paramArrayOfint3[p_194054_4_] = i2;
/*     */         } 
/*     */       };
/*  83 */     for (int j = 0; j < i; j++)
/*     */     {
/*  85 */       aint[j] = this.field_194065_e.getInt(j);
/*     */     }
/*     */     
/*  88 */     int k1 = 1;
/*     */     
/*  90 */     for (int k = Math.min(i, this.field_194069_i); k1 * 2 < k; k1 *= 2) {
/*     */       
/*  92 */       for (int l = 0; l < i; aint3[l] = l++) {
/*     */         
/*  94 */         aint1[l] = aint[l];
/*  95 */         aint2[l] = (l + k1 < i) ? aint[l + k1] : -2;
/*     */       } 
/*     */       
/*  98 */       Arrays.quickSort(0, i, intcomparator, swapper);
/*     */       
/* 100 */       for (int l1 = 0; l1 < i; l1++) {
/*     */         
/* 102 */         if (l1 > 0 && aint1[l1] == aint1[l1 - 1] && aint2[l1] == aint2[l1 - 1]) {
/*     */           
/* 104 */           aint[aint3[l1]] = aint[aint3[l1 - 1]];
/*     */         }
/*     */         else {
/*     */           
/* 108 */           aint[aint3[l1]] = l1;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 113 */     IntList intlist1 = this.field_194067_g;
/* 114 */     IntList intlist = this.field_194068_h;
/* 115 */     this.field_194067_g = (IntList)new IntArrayList(intlist1.size());
/* 116 */     this.field_194068_h = (IntList)new IntArrayList(intlist.size());
/*     */     
/* 118 */     for (int i1 = 0; i1 < i; i1++) {
/*     */       
/* 120 */       int j1 = aint3[i1];
/* 121 */       this.field_194067_g.add(intlist1.getInt(j1));
/* 122 */       this.field_194068_h.add(intlist.getInt(j1));
/*     */     } 
/*     */     
/* 125 */     if (field_194063_c)
/*     */     {
/* 127 */       func_194060_b();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_194060_b() {
/* 133 */     for (int i2 = 0; i2 < this.field_194067_g.size(); i2++)
/*     */     {
/* 135 */       field_194064_d.debug("{} {}", Integer.valueOf(i2), func_194059_a(i2));
/*     */     }
/*     */     
/* 138 */     field_194064_d.debug("");
/*     */   }
/*     */ 
/*     */   
/*     */   private String func_194059_a(int p_194059_1_) {
/* 143 */     int i2 = this.field_194068_h.getInt(p_194059_1_);
/* 144 */     int j2 = this.field_194066_f.getInt(this.field_194067_g.getInt(p_194059_1_));
/* 145 */     StringBuilder stringbuilder = new StringBuilder();
/*     */     
/* 147 */     for (int k2 = 0; j2 + k2 < this.field_194065_e.size(); k2++) {
/*     */       
/* 149 */       if (k2 == i2)
/*     */       {
/* 151 */         stringbuilder.append('^');
/*     */       }
/*     */       
/* 154 */       int l2 = ((Integer)this.field_194065_e.get(j2 + k2)).intValue();
/*     */       
/* 156 */       if (l2 == -1) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/* 161 */       stringbuilder.append((char)l2);
/*     */     } 
/*     */     
/* 164 */     return stringbuilder.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_194056_a(String p_194056_1_, int p_194056_2_) {
/* 169 */     int i2 = this.field_194066_f.getInt(this.field_194067_g.getInt(p_194056_2_));
/* 170 */     int j2 = this.field_194068_h.getInt(p_194056_2_);
/*     */     
/* 172 */     for (int k2 = 0; k2 < p_194056_1_.length(); k2++) {
/*     */       
/* 174 */       int l2 = this.field_194065_e.getInt(i2 + j2 + k2);
/*     */       
/* 176 */       if (l2 == -1)
/*     */       {
/* 178 */         return 1;
/*     */       }
/*     */       
/* 181 */       char c0 = p_194056_1_.charAt(k2);
/* 182 */       char c1 = (char)l2;
/*     */       
/* 184 */       if (c0 < c1)
/*     */       {
/* 186 */         return -1;
/*     */       }
/*     */       
/* 189 */       if (c0 > c1)
/*     */       {
/* 191 */         return 1;
/*     */       }
/*     */     } 
/*     */     
/* 195 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<T> func_194055_a(String p_194055_1_) {
/* 200 */     int i2 = this.field_194067_g.size();
/* 201 */     int j2 = 0;
/* 202 */     int k2 = i2;
/*     */     
/* 204 */     while (j2 < k2) {
/*     */       
/* 206 */       int l2 = j2 + (k2 - j2) / 2;
/* 207 */       int i3 = func_194056_a(p_194055_1_, l2);
/*     */       
/* 209 */       if (field_194062_b)
/*     */       {
/* 211 */         field_194064_d.debug("comparing lower \"{}\" with {} \"{}\": {}", p_194055_1_, Integer.valueOf(l2), func_194059_a(l2), Integer.valueOf(i3));
/*     */       }
/*     */       
/* 214 */       if (i3 > 0) {
/*     */         
/* 216 */         j2 = l2 + 1;
/*     */         
/*     */         continue;
/*     */       } 
/* 220 */       k2 = l2;
/*     */     } 
/*     */ 
/*     */     
/* 224 */     if (j2 >= 0 && j2 < i2) {
/*     */       
/* 226 */       int i4 = j2;
/* 227 */       k2 = i2;
/*     */       
/* 229 */       while (j2 < k2) {
/*     */         
/* 231 */         int j4 = j2 + (k2 - j2) / 2;
/* 232 */         int j3 = func_194056_a(p_194055_1_, j4);
/*     */         
/* 234 */         if (field_194062_b)
/*     */         {
/* 236 */           field_194064_d.debug("comparing upper \"{}\" with {} \"{}\": {}", p_194055_1_, Integer.valueOf(j4), func_194059_a(j4), Integer.valueOf(j3));
/*     */         }
/*     */         
/* 239 */         if (j3 >= 0) {
/*     */           
/* 241 */           j2 = j4 + 1;
/*     */           
/*     */           continue;
/*     */         } 
/* 245 */         k2 = j4;
/*     */       } 
/*     */ 
/*     */       
/* 249 */       int k4 = j2;
/* 250 */       IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
/*     */       
/* 252 */       for (int k3 = i4; k3 < k4; k3++)
/*     */       {
/* 254 */         intOpenHashSet.add(this.field_194067_g.getInt(k3));
/*     */       }
/*     */       
/* 257 */       int[] aint4 = intOpenHashSet.toIntArray();
/* 258 */       Arrays.sort(aint4);
/* 259 */       Set<T> set = Sets.newLinkedHashSet(); byte b;
/*     */       int i, arrayOfInt1[];
/* 261 */       for (i = (arrayOfInt1 = aint4).length, b = 0; b < i; ) { int l3 = arrayOfInt1[b];
/*     */         
/* 263 */         set.add(this.field_194061_a.get(l3));
/*     */         b++; }
/*     */       
/* 266 */       return Lists.newArrayList(set);
/*     */     } 
/*     */ 
/*     */     
/* 270 */     return Collections.emptyList();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\clien\\util\SuffixArray.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */