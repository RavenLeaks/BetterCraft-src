/*    */ package net.minecraft.block.properties;
/*    */ 
/*    */ import com.google.common.base.Optional;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import com.google.common.collect.Sets;
/*    */ import java.util.Collection;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class PropertyInteger
/*    */   extends PropertyHelper<Integer>
/*    */ {
/*    */   private final ImmutableSet<Integer> allowedValues;
/*    */   
/*    */   protected PropertyInteger(String name, int min, int max) {
/* 15 */     super(name, Integer.class);
/*    */     
/* 17 */     if (min < 0)
/*    */     {
/* 19 */       throw new IllegalArgumentException("Min value of " + name + " must be 0 or greater");
/*    */     }
/* 21 */     if (max <= min)
/*    */     {
/* 23 */       throw new IllegalArgumentException("Max value of " + name + " must be greater than min (" + min + ")");
/*    */     }
/*    */ 
/*    */     
/* 27 */     Set<Integer> set = Sets.newHashSet();
/*    */     
/* 29 */     for (int i = min; i <= max; i++)
/*    */     {
/* 31 */       set.add(Integer.valueOf(i));
/*    */     }
/*    */     
/* 34 */     this.allowedValues = ImmutableSet.copyOf(set);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<Integer> getAllowedValues() {
/* 40 */     return (Collection<Integer>)this.allowedValues;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 45 */     if (this == p_equals_1_)
/*    */     {
/* 47 */       return true;
/*    */     }
/* 49 */     if (p_equals_1_ instanceof PropertyInteger && super.equals(p_equals_1_)) {
/*    */       
/* 51 */       PropertyInteger propertyinteger = (PropertyInteger)p_equals_1_;
/* 52 */       return this.allowedValues.equals(propertyinteger.allowedValues);
/*    */     } 
/*    */ 
/*    */     
/* 56 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 62 */     return 31 * super.hashCode() + this.allowedValues.hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public static PropertyInteger create(String name, int min, int max) {
/* 67 */     return new PropertyInteger(name, min, max);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Optional<Integer> parseValue(String value) {
/*    */     try {
/* 74 */       Integer integer = Integer.valueOf(value);
/* 75 */       return this.allowedValues.contains(integer) ? Optional.of(integer) : Optional.absent();
/*    */     }
/* 77 */     catch (NumberFormatException var3) {
/*    */       
/* 79 */       return Optional.absent();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName(Integer value) {
/* 88 */     return value.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\properties\PropertyInteger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */