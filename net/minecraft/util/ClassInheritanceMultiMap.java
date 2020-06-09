/*     */ package net.minecraft.util;
/*     */ 
/*     */ import com.google.common.collect.Iterators;
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Maps;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.AbstractSet;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class ClassInheritanceMultiMap<T>
/*     */   extends AbstractSet<T> {
/*  16 */   private static final Set<Class<?>> ALL_KNOWN = Sets.newHashSet();
/*  17 */   private final Map<Class<?>, List<T>> map = Maps.newHashMap();
/*  18 */   private final Set<Class<?>> knownKeys = Sets.newIdentityHashSet();
/*     */   private final Class<T> baseClass;
/*  20 */   private final List<T> values = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   public ClassInheritanceMultiMap(Class<T> baseClassIn) {
/*  24 */     this.baseClass = baseClassIn;
/*  25 */     this.knownKeys.add(baseClassIn);
/*  26 */     this.map.put(baseClassIn, this.values);
/*     */     
/*  28 */     for (Class<?> oclass : ALL_KNOWN)
/*     */     {
/*  30 */       createLookup(oclass);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createLookup(Class<?> clazz) {
/*  36 */     ALL_KNOWN.add(clazz);
/*     */     
/*  38 */     for (T t : this.values) {
/*     */       
/*  40 */       if (clazz.isAssignableFrom(t.getClass()))
/*     */       {
/*  42 */         addForClass(t, clazz);
/*     */       }
/*     */     } 
/*     */     
/*  46 */     this.knownKeys.add(clazz);
/*     */   }
/*     */ 
/*     */   
/*     */   protected Class<?> initializeClassLookup(Class<?> clazz) {
/*  51 */     if (this.baseClass.isAssignableFrom(clazz)) {
/*     */       
/*  53 */       if (!this.knownKeys.contains(clazz))
/*     */       {
/*  55 */         createLookup(clazz);
/*     */       }
/*     */       
/*  58 */       return clazz;
/*     */     } 
/*     */ 
/*     */     
/*  62 */     throw new IllegalArgumentException("Don't know how to search for " + clazz);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean add(T p_add_1_) {
/*  68 */     for (Class<?> oclass : this.knownKeys) {
/*     */       
/*  70 */       if (oclass.isAssignableFrom(p_add_1_.getClass()))
/*     */       {
/*  72 */         addForClass(p_add_1_, oclass);
/*     */       }
/*     */     } 
/*     */     
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private void addForClass(T value, Class<?> parentClass) {
/*  81 */     List<T> list = this.map.get(parentClass);
/*     */     
/*  83 */     if (list == null) {
/*     */       
/*  85 */       this.map.put(parentClass, Lists.newArrayList(new Object[] { value }));
/*     */     }
/*     */     else {
/*     */       
/*  89 */       list.add(value);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(Object p_remove_1_) {
/*  95 */     T t = (T)p_remove_1_;
/*  96 */     boolean flag = false;
/*     */     
/*  98 */     for (Class<?> oclass : this.knownKeys) {
/*     */       
/* 100 */       if (oclass.isAssignableFrom(t.getClass())) {
/*     */         
/* 102 */         List<T> list = this.map.get(oclass);
/*     */         
/* 104 */         if (list != null && list.remove(t))
/*     */         {
/* 106 */           flag = true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean contains(Object p_contains_1_) {
/* 116 */     return Iterators.contains(getByClass(p_contains_1_.getClass()).iterator(), p_contains_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public <S> Iterable<S> getByClass(final Class<S> clazz) {
/* 121 */     return new Iterable<S>()
/*     */       {
/*     */         public Iterator<S> iterator()
/*     */         {
/* 125 */           List<T> list = (List<T>)ClassInheritanceMultiMap.this.map.get(ClassInheritanceMultiMap.this.initializeClassLookup(clazz));
/*     */           
/* 127 */           if (list == null)
/*     */           {
/* 129 */             return Collections.emptyIterator();
/*     */           }
/*     */ 
/*     */           
/* 133 */           Iterator<T> iterator = list.iterator();
/* 134 */           return (Iterator<S>)Iterators.filter(iterator, clazz);
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Iterator<T> iterator() {
/* 142 */     return this.values.isEmpty() ? Collections.<T>emptyIterator() : (Iterator<T>)Iterators.unmodifiableIterator(this.values.iterator());
/*     */   }
/*     */ 
/*     */   
/*     */   public int size() {
/* 147 */     return this.values.size();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\ClassInheritanceMultiMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */