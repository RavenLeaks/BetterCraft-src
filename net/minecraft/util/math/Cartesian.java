/*     */ package net.minecraft.util.math;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.UnmodifiableIterator;
/*     */ import java.lang.reflect.Array;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import javax.annotation.Nullable;
/*     */ 
/*     */ 
/*     */ public class Cartesian
/*     */ {
/*     */   public static <T> Iterable<T[]> cartesianProduct(Class<T> clazz, Iterable<? extends Iterable<? extends T>> sets) {
/*  19 */     return new Product<>(clazz, toArray(Iterable.class, (Iterable)sets), null);
/*     */   }
/*     */ 
/*     */   
/*     */   public static <T> Iterable<List<T>> cartesianProduct(Iterable<? extends Iterable<? extends T>> sets) {
/*  24 */     return arraysAsLists(cartesianProduct(Object.class, sets));
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> Iterable<List<T>> arraysAsLists(Iterable<Object[]> arrays) {
/*  29 */     return Iterables.transform(arrays, new GetList(null));
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T[] toArray(Class<? super T> clazz, Iterable<? extends T> it) {
/*  34 */     List<T> list = Lists.newArrayList();
/*     */     
/*  36 */     for (T t : it)
/*     */     {
/*  38 */       list.add(t);
/*     */     }
/*     */     
/*  41 */     return list.toArray(createArray(clazz, list.size()));
/*     */   }
/*     */ 
/*     */   
/*     */   private static <T> T[] createArray(Class<? super T> elementType, int length) {
/*  46 */     return (T[])Array.newInstance(elementType, length);
/*     */   }
/*     */ 
/*     */   
/*     */   static class GetList<T>
/*     */     implements Function<Object[], List<T>>
/*     */   {
/*     */     private GetList() {}
/*     */ 
/*     */     
/*     */     public List<T> apply(@Nullable Object[] p_apply_1_) {
/*  57 */       return Arrays.asList((T[])p_apply_1_);
/*     */     }
/*     */   }
/*     */   
/*     */   static class Product<T>
/*     */     implements Iterable<T[]>
/*     */   {
/*     */     private final Class<T> clazz;
/*     */     private final Iterable<? extends T>[] iterables;
/*     */     
/*     */     private Product(Class<T> clazz, Iterable[] iterables) {
/*  68 */       this.clazz = clazz;
/*  69 */       this.iterables = (Iterable<? extends T>[])iterables;
/*     */     }
/*     */ 
/*     */     
/*     */     public Iterator<T[]> iterator() {
/*  74 */       return (this.iterables.length <= 0) ? Collections.<T[]>singletonList(Cartesian.createArray(this.clazz, 0)).iterator() : (Iterator<T[]>)new ProductIterator(this.clazz, (Iterable[])this.iterables, null);
/*     */     }
/*     */     
/*     */     static class ProductIterator<T>
/*     */       extends UnmodifiableIterator<T[]>
/*     */     {
/*     */       private int index;
/*     */       private final Iterable<? extends T>[] iterables;
/*     */       private final Iterator<? extends T>[] iterators;
/*     */       private final T[] results;
/*     */       
/*     */       private ProductIterator(Class<T> clazz, Iterable[] iterables) {
/*  86 */         this.index = -2;
/*  87 */         this.iterables = (Iterable<? extends T>[])iterables;
/*  88 */         this.iterators = (Iterator<? extends T>[])Cartesian.createArray((Class)Iterator.class, this.iterables.length);
/*     */         
/*  90 */         for (int i = 0; i < this.iterables.length; i++)
/*     */         {
/*  92 */           this.iterators[i] = iterables[i].iterator();
/*     */         }
/*     */         
/*  95 */         this.results = Cartesian.createArray(clazz, this.iterators.length);
/*     */       }
/*     */ 
/*     */       
/*     */       private void endOfData() {
/* 100 */         this.index = -1;
/* 101 */         Arrays.fill((Object[])this.iterators, (Object)null);
/* 102 */         Arrays.fill((Object[])this.results, (Object)null);
/*     */       }
/*     */ 
/*     */       
/*     */       public boolean hasNext() {
/* 107 */         if (this.index == -2) {
/*     */           
/* 109 */           this.index = 0; byte b; int i;
/*     */           Iterator<? extends T>[] arrayOfIterator;
/* 111 */           for (i = (arrayOfIterator = this.iterators).length, b = 0; b < i; ) { Iterator<? extends T> iterator1 = arrayOfIterator[b];
/*     */             
/* 113 */             if (!iterator1.hasNext()) {
/*     */               
/* 115 */               endOfData();
/*     */               break;
/*     */             } 
/*     */             b++; }
/*     */           
/* 120 */           return true;
/*     */         } 
/*     */ 
/*     */         
/* 124 */         if (this.index >= this.iterators.length)
/*     */         {
/* 126 */           for (this.index = this.iterators.length - 1; this.index >= 0; this.index--) {
/*     */             
/* 128 */             Iterator<? extends T> iterator = this.iterators[this.index];
/*     */             
/* 130 */             if (iterator.hasNext()) {
/*     */               break;
/*     */             }
/*     */ 
/*     */             
/* 135 */             if (this.index == 0) {
/*     */               
/* 137 */               endOfData();
/*     */               
/*     */               break;
/*     */             } 
/* 141 */             iterator = this.iterables[this.index].iterator();
/* 142 */             this.iterators[this.index] = iterator;
/*     */             
/* 144 */             if (!iterator.hasNext()) {
/*     */               
/* 146 */               endOfData();
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/*     */         }
/* 152 */         return (this.index >= 0);
/*     */       }
/*     */ 
/*     */ 
/*     */       
/*     */       public T[] next() {
/* 158 */         if (!hasNext())
/*     */         {
/* 160 */           throw new NoSuchElementException();
/*     */         }
/*     */ 
/*     */         
/* 164 */         while (this.index < this.iterators.length) {
/*     */           
/* 166 */           this.results[this.index] = this.iterators[this.index].next();
/* 167 */           this.index++;
/*     */         } 
/*     */         
/* 170 */         return (T[])this.results.clone();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\math\Cartesian.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */