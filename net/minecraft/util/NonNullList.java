/*    */ package net.minecraft.util;
/*    */ 
/*    */ import java.util.AbstractList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nonnull;
/*    */ import javax.annotation.Nullable;
/*    */ import org.apache.commons.lang3.Validate;
/*    */ 
/*    */ public class NonNullList<E>
/*    */   extends AbstractList<E>
/*    */ {
/*    */   private final List<E> field_191198_a;
/*    */   private final E field_191199_b;
/*    */   
/*    */   public static <E> NonNullList<E> func_191196_a() {
/* 18 */     return new NonNullList<>();
/*    */   }
/*    */ 
/*    */   
/*    */   public static <E> NonNullList<E> func_191197_a(int p_191197_0_, E p_191197_1_) {
/* 23 */     Validate.notNull(p_191197_1_);
/* 24 */     Object[] aobject = new Object[p_191197_0_];
/* 25 */     Arrays.fill(aobject, p_191197_1_);
/* 26 */     return new NonNullList<>(Arrays.asList((E[])aobject), p_191197_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public static <E> NonNullList<E> func_193580_a(E p_193580_0_, Object... p_193580_1_) {
/* 31 */     return new NonNullList<>(Arrays.asList((E[])p_193580_1_), p_193580_0_);
/*    */   }
/*    */ 
/*    */   
/*    */   protected NonNullList() {
/* 36 */     this(new ArrayList<>(), null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected NonNullList(List<E> p_i47327_1_, @Nullable E p_i47327_2_) {
/* 41 */     this.field_191198_a = p_i47327_1_;
/* 42 */     this.field_191199_b = p_i47327_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nonnull
/*    */   public E get(int p_get_1_) {
/* 48 */     return this.field_191198_a.get(p_get_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public E set(int p_set_1_, E p_set_2_) {
/* 53 */     Validate.notNull(p_set_2_);
/* 54 */     return this.field_191198_a.set(p_set_1_, p_set_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(int p_add_1_, E p_add_2_) {
/* 59 */     Validate.notNull(p_add_2_);
/* 60 */     this.field_191198_a.add(p_add_1_, p_add_2_);
/*    */   }
/*    */ 
/*    */   
/*    */   public E remove(int p_remove_1_) {
/* 65 */     return this.field_191198_a.remove(p_remove_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public int size() {
/* 70 */     return this.field_191198_a.size();
/*    */   }
/*    */ 
/*    */   
/*    */   public void clear() {
/* 75 */     if (this.field_191199_b == null) {
/*    */       
/* 77 */       super.clear();
/*    */     }
/*    */     else {
/*    */       
/* 81 */       for (int i = 0; i < size(); i++)
/*    */       {
/* 83 */         set(i, this.field_191199_b);
/*    */       }
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraf\\util\NonNullList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */