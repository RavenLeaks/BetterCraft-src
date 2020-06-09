/*    */ package net.minecraft.util.registry;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collection;
/*    */ import java.util.Collections;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import java.util.Random;
/*    */ import java.util.Set;
/*    */ import javax.annotation.Nullable;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class RegistrySimple<K, V>
/*    */   implements IRegistry<K, V> {
/* 17 */   private static final Logger LOGGER = LogManager.getLogger();
/* 18 */   protected final Map<K, V> registryObjects = createUnderlyingMap();
/*    */   
/*    */   private Object[] values;
/*    */   
/*    */   protected Map<K, V> createUnderlyingMap() {
/* 23 */     return Maps.newHashMap();
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public V getObject(@Nullable K name) {
/* 29 */     return this.registryObjects.get(name);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void putObject(K key, V value) {
/* 37 */     Validate.notNull(key);
/* 38 */     Validate.notNull(value);
/* 39 */     this.values = null;
/*    */     
/* 41 */     if (this.registryObjects.containsKey(key))
/*    */     {
/* 43 */       LOGGER.debug("Adding duplicate key '{}' to registry", key);
/*    */     }
/*    */     
/* 46 */     this.registryObjects.put(key, value);
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<K> getKeys() {
/* 51 */     return Collections.unmodifiableSet(this.registryObjects.keySet());
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public V getRandomObject(Random random) {
/* 57 */     if (this.values == null) {
/*    */       
/* 59 */       Collection<?> collection = this.registryObjects.values();
/*    */       
/* 61 */       if (collection.isEmpty())
/*    */       {
/* 63 */         return null;
/*    */       }
/*    */       
/* 66 */       this.values = collection.toArray(new Object[collection.size()]);
/*    */     } 
/*    */     
/* 69 */     return (V)this.values[random.nextInt(this.values.length)];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean containsKey(K key) {
/* 77 */     return this.registryObjects.containsKey(key);
/*    */   }
/*    */ 
/*    */   
/*    */   public Iterator<V> iterator() {
/* 82 */     return this.registryObjects.values().iterator();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\registry\RegistrySimple.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */