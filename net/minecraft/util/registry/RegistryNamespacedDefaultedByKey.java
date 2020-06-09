/*    */ package net.minecraft.util.registry;
/*    */ 
/*    */ import java.util.Random;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistryNamespacedDefaultedByKey<K, V>
/*    */   extends RegistryNamespaced<K, V>
/*    */ {
/*    */   private final K defaultValueKey;
/*    */   private V defaultValue;
/*    */   
/*    */   public RegistryNamespacedDefaultedByKey(K defaultValueKeyIn) {
/* 20 */     this.defaultValueKey = defaultValueKeyIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public void register(int id, K key, V value) {
/* 25 */     if (this.defaultValueKey.equals(key))
/*    */     {
/* 27 */       this.defaultValue = value;
/*    */     }
/*    */     
/* 30 */     super.register(id, key, value);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void validateKey() {
/* 38 */     Validate.notNull(this.defaultValue, "Missing default of DefaultedMappedRegistry: " + this.defaultValueKey, new Object[0]);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getIDForObject(V value) {
/* 46 */     int i = super.getIDForObject(value);
/* 47 */     return (i == -1) ? super.getIDForObject(this.defaultValue) : i;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public K getNameForObject(V value) {
/* 57 */     K k = super.getNameForObject(value);
/* 58 */     return (k == null) ? this.defaultValueKey : k;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public V getObject(@Nullable K name) {
/* 64 */     V v = super.getObject(name);
/* 65 */     return (v == null) ? this.defaultValue : v;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public V getObjectById(int id) {
/* 75 */     V v = super.getObjectById(id);
/* 76 */     return (v == null) ? this.defaultValue : v;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public V getRandomObject(Random random) {
/* 82 */     V v = super.getRandomObject(random);
/* 83 */     return (v == null) ? this.defaultValue : v;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\registry\RegistryNamespacedDefaultedByKey.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */