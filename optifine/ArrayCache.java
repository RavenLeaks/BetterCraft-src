/*    */ package optifine;
/*    */ 
/*    */ import java.lang.reflect.Array;
/*    */ import java.util.ArrayDeque;
/*    */ 
/*    */ public class ArrayCache
/*    */ {
/*  8 */   private Class elementClass = null;
/*  9 */   private int maxCacheSize = 0;
/* 10 */   private ArrayDeque cache = new ArrayDeque();
/*    */ 
/*    */   
/*    */   public ArrayCache(Class p_i10_1_, int p_i10_2_) {
/* 14 */     this.elementClass = p_i10_1_;
/* 15 */     this.maxCacheSize = p_i10_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized Object allocate(int p_allocate_1_) {
/* 20 */     Object object = this.cache.pollLast();
/*    */     
/* 22 */     if (object == null || Array.getLength(object) < p_allocate_1_)
/*    */     {
/* 24 */       object = Array.newInstance(this.elementClass, p_allocate_1_);
/*    */     }
/*    */     
/* 27 */     return object;
/*    */   }
/*    */ 
/*    */   
/*    */   public synchronized void free(Object p_free_1_) {
/* 32 */     if (p_free_1_ != null) {
/*    */       
/* 34 */       Class<?> oclass = p_free_1_.getClass();
/*    */       
/* 36 */       if (oclass.getComponentType() != this.elementClass)
/*    */       {
/* 38 */         throw new IllegalArgumentException("Wrong component type");
/*    */       }
/* 40 */       if (this.cache.size() < this.maxCacheSize)
/*    */       {
/* 42 */         this.cache.add(p_free_1_);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\optifine\ArrayCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */