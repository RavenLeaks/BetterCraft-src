/*    */ package net.minecraft.util.registry;
/*    */ 
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistryDefaulted<K, V>
/*    */   extends RegistrySimple<K, V>
/*    */ {
/*    */   private final V defaultObject;
/*    */   
/*    */   public RegistryDefaulted(V defaultObjectIn) {
/* 15 */     this.defaultObject = defaultObjectIn;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public V getObject(@Nullable K name) {
/* 21 */     V v = super.getObject(name);
/* 22 */     return (v == null) ? this.defaultObject : v;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\registry\RegistryDefaulted.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */