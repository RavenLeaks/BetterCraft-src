/*     */ package net.minecraft.client.util;
/*     */ 
/*     */ import com.google.common.collect.AbstractIterator;
/*     */ import com.google.common.collect.Lists;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntMap;
/*     */ import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.function.Function;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class SearchTree<T>
/*     */   implements ISearchTree<T>
/*     */ {
/*  16 */   protected SuffixArray<T> field_194044_a = new SuffixArray<>();
/*  17 */   protected SuffixArray<T> field_194045_b = new SuffixArray<>();
/*     */   private final Function<T, Iterable<String>> field_194046_c;
/*     */   private final Function<T, Iterable<ResourceLocation>> field_194047_d;
/*  20 */   private final List<T> field_194048_e = Lists.newArrayList();
/*  21 */   private Object2IntMap<T> field_194049_f = (Object2IntMap<T>)new Object2IntOpenHashMap();
/*     */ 
/*     */   
/*     */   public SearchTree(Function<T, Iterable<String>> p_i47612_1_, Function<T, Iterable<ResourceLocation>> p_i47612_2_) {
/*  25 */     this.field_194046_c = p_i47612_1_;
/*  26 */     this.field_194047_d = p_i47612_2_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194040_a() {
/*  31 */     this.field_194044_a = new SuffixArray<>();
/*  32 */     this.field_194045_b = new SuffixArray<>();
/*     */     
/*  34 */     for (T t : this.field_194048_e)
/*     */     {
/*  36 */       func_194042_b(t);
/*     */     }
/*     */     
/*  39 */     this.field_194044_a.func_194058_a();
/*  40 */     this.field_194045_b.func_194058_a();
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_194043_a(T p_194043_1_) {
/*  45 */     this.field_194049_f.put(p_194043_1_, this.field_194048_e.size());
/*  46 */     this.field_194048_e.add(p_194043_1_);
/*  47 */     func_194042_b(p_194043_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_194042_b(T p_194042_1_) {
/*  52 */     ((Iterable)this.field_194047_d.apply(p_194042_1_)).forEach(p_194039_2_ -> this.field_194045_b.func_194057_a((T)paramObject, p_194039_2_.toString().toLowerCase(Locale.ROOT)));
/*     */ 
/*     */ 
/*     */     
/*  56 */     ((Iterable)this.field_194046_c.apply(p_194042_1_)).forEach(p_194041_2_ -> this.field_194044_a.func_194057_a((T)paramObject, p_194041_2_.toLowerCase(Locale.ROOT)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<T> func_194038_a(String p_194038_1_) {
/*  64 */     List<T> list = this.field_194044_a.func_194055_a(p_194038_1_);
/*     */     
/*  66 */     if (p_194038_1_.indexOf(':') < 0)
/*     */     {
/*  68 */       return list;
/*     */     }
/*     */ 
/*     */     
/*  72 */     List<T> list1 = this.field_194045_b.func_194055_a(p_194038_1_);
/*  73 */     return list1.isEmpty() ? list : Lists.newArrayList((Iterator)new MergingIterator<>(list.iterator(), list1.iterator(), this.field_194049_f));
/*     */   }
/*     */ 
/*     */   
/*     */   static class MergingIterator<T>
/*     */     extends AbstractIterator<T>
/*     */   {
/*     */     private final Iterator<T> field_194033_a;
/*     */     private final Iterator<T> field_194034_b;
/*     */     private final Object2IntMap<T> field_194035_c;
/*     */     private T field_194036_d;
/*     */     private T field_194037_e;
/*     */     
/*     */     public MergingIterator(Iterator<T> p_i47606_1_, Iterator<T> p_i47606_2_, Object2IntMap<T> p_i47606_3_) {
/*  87 */       this.field_194033_a = p_i47606_1_;
/*  88 */       this.field_194034_b = p_i47606_2_;
/*  89 */       this.field_194035_c = p_i47606_3_;
/*  90 */       this.field_194036_d = p_i47606_1_.hasNext() ? p_i47606_1_.next() : null;
/*  91 */       this.field_194037_e = p_i47606_2_.hasNext() ? p_i47606_2_.next() : null;
/*     */     }
/*     */     
/*     */     protected T computeNext() {
/*     */       int i;
/*  96 */       if (this.field_194036_d == null && this.field_194037_e == null)
/*     */       {
/*  98 */         return (T)endOfData();
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 104 */       if (this.field_194036_d == this.field_194037_e) {
/*     */         
/* 106 */         i = 0;
/*     */       }
/* 108 */       else if (this.field_194036_d == null) {
/*     */         
/* 110 */         i = 1;
/*     */       }
/* 112 */       else if (this.field_194037_e == null) {
/*     */         
/* 114 */         i = -1;
/*     */       }
/*     */       else {
/*     */         
/* 118 */         i = Integer.compare(this.field_194035_c.getInt(this.field_194036_d), this.field_194035_c.getInt(this.field_194037_e));
/*     */       } 
/*     */       
/* 121 */       T t = (i <= 0) ? this.field_194036_d : this.field_194037_e;
/*     */       
/* 123 */       if (i <= 0)
/*     */       {
/* 125 */         this.field_194036_d = this.field_194033_a.hasNext() ? this.field_194033_a.next() : null;
/*     */       }
/*     */       
/* 128 */       if (i >= 0)
/*     */       {
/* 130 */         this.field_194037_e = this.field_194034_b.hasNext() ? this.field_194034_b.next() : null;
/*     */       }
/*     */       
/* 133 */       return t;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\clien\\util\SearchTree.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */