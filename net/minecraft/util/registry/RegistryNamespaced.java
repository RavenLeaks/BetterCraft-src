/*    */ package net.minecraft.util.registry;
/*    */ 
/*    */ import com.google.common.collect.BiMap;
/*    */ import com.google.common.collect.HashBiMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.IObjectIntIterable;
/*    */ import net.minecraft.util.IntIdentityHashBiMap;
/*    */ 
/*    */ public class RegistryNamespaced<K, V>
/*    */   extends RegistrySimple<K, V> implements IObjectIntIterable<V> {
/* 13 */   protected final IntIdentityHashBiMap<V> underlyingIntegerMap = new IntIdentityHashBiMap(256);
/*    */   
/*    */   protected final Map<V, K> inverseObjectRegistry;
/*    */   
/*    */   public RegistryNamespaced() {
/* 18 */     this.inverseObjectRegistry = (Map<V, K>)((BiMap)this.registryObjects).inverse();
/*    */   }
/*    */ 
/*    */   
/*    */   public void register(int id, K key, V value) {
/* 23 */     this.underlyingIntegerMap.put(value, id);
/* 24 */     putObject(key, value);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Map<K, V> createUnderlyingMap() {
/* 29 */     return (Map<K, V>)HashBiMap.create();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public V getObject(@Nullable K name) {
/* 35 */     return super.getObject(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public K getNameForObject(V value) {
/* 45 */     return this.inverseObjectRegistry.get(value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean containsKey(K key) {
/* 53 */     return super.containsKey(key);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIDForObject(@Nullable V value) {
/* 61 */     return this.underlyingIntegerMap.getId(value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public V getObjectById(int id) {
/* 71 */     return (V)this.underlyingIntegerMap.get(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<V> iterator() {
/* 76 */     return this.underlyingIntegerMap.iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\registry\RegistryNamespaced.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */