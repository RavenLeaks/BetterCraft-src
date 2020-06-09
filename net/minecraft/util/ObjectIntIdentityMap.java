/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Iterators;
/*    */ import com.google.common.collect.Lists;
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ public class ObjectIntIdentityMap<T>
/*    */   implements IObjectIntIterable<T>
/*    */ {
/*    */   private final IdentityHashMap<T, Integer> identityMap;
/*    */   private final List<T> objectList;
/*    */   
/*    */   public ObjectIntIdentityMap() {
/* 18 */     this(512);
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectIntIdentityMap(int expectedSize) {
/* 23 */     this.objectList = Lists.newArrayListWithExpectedSize(expectedSize);
/* 24 */     this.identityMap = new IdentityHashMap<>(expectedSize);
/*    */   }
/*    */ 
/*    */   
/*    */   public void put(T key, int value) {
/* 29 */     this.identityMap.put(key, Integer.valueOf(value));
/*    */     
/* 31 */     while (this.objectList.size() <= value)
/*    */     {
/* 33 */       this.objectList.add(null);
/*    */     }
/*    */     
/* 36 */     this.objectList.set(value, key);
/*    */   }
/*    */ 
/*    */   
/*    */   public int get(T key) {
/* 41 */     Integer integer = this.identityMap.get(key);
/* 42 */     return (integer == null) ? -1 : integer.intValue();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public final T getByValue(int value) {
/* 48 */     return (value >= 0 && value < this.objectList.size()) ? this.objectList.get(value) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<T> iterator() {
/* 53 */     return (Iterator<T>)Iterators.filter(this.objectList.iterator(), Predicates.notNull());
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 58 */     return this.identityMap.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\ObjectIntIdentityMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */