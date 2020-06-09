/*    */ package net.minecraft.util;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.Collection;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class LowerStringMap<V>
/*    */   implements Map<String, V>
/*    */ {
/* 12 */   private final Map<String, V> internalMap = Maps.newLinkedHashMap();
/*    */ 
/*    */   
/*    */   public int size() {
/* 16 */     return this.internalMap.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 21 */     return this.internalMap.isEmpty();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean containsKey(Object p_containsKey_1_) {
/* 26 */     return this.internalMap.containsKey(p_containsKey_1_.toString().toLowerCase(Locale.ROOT));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean containsValue(Object p_containsValue_1_) {
/* 31 */     return this.internalMap.containsKey(p_containsValue_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public V get(Object p_get_1_) {
/* 36 */     return this.internalMap.get(p_get_1_.toString().toLowerCase(Locale.ROOT));
/*    */   }
/*    */ 
/*    */   
/*    */   public V put(String p_put_1_, V p_put_2_) {
/* 41 */     return this.internalMap.put(p_put_1_.toLowerCase(Locale.ROOT), p_put_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public V remove(Object p_remove_1_) {
/* 46 */     return this.internalMap.remove(p_remove_1_.toString().toLowerCase(Locale.ROOT));
/*    */   }
/*    */ 
/*    */   
/*    */   public void putAll(Map<? extends String, ? extends V> p_putAll_1_) {
/* 51 */     for (Map.Entry<? extends String, ? extends V> entry : p_putAll_1_.entrySet())
/*    */     {
/* 53 */       put(entry.getKey(), entry.getValue());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 59 */     this.internalMap.clear();
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<String> keySet() {
/* 64 */     return this.internalMap.keySet();
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<V> values() {
/* 69 */     return this.internalMap.values();
/*    */   }
/*    */ 
/*    */   
/*    */   public Set<Map.Entry<String, V>> entrySet() {
/* 74 */     return this.internalMap.entrySet();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\LowerStringMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */