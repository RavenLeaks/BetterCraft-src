/*    */ package net.minecraft.block.properties;
/*    */ 
/*    */ import com.google.common.base.Optional;
/*    */ import com.google.common.collect.ImmutableSet;
/*    */ import java.util.Collection;
/*    */ 
/*    */ public class PropertyBool
/*    */   extends PropertyHelper<Boolean> {
/*  9 */   private final ImmutableSet<Boolean> allowedValues = ImmutableSet.of(Boolean.valueOf(true), Boolean.valueOf(false));
/*    */ 
/*    */   
/*    */   protected PropertyBool(String name) {
/* 13 */     super(name, Boolean.class);
/*    */   }
/*    */ 
/*    */   
/*    */   public Collection<Boolean> getAllowedValues() {
/* 18 */     return (Collection<Boolean>)this.allowedValues;
/*    */   }
/*    */ 
/*    */   
/*    */   public static PropertyBool create(String name) {
/* 23 */     return new PropertyBool(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Boolean> parseValue(String value) {
/* 28 */     return (!"true".equals(value) && !"false".equals(value)) ? Optional.absent() : Optional.of(Boolean.valueOf(value));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getName(Boolean value) {
/* 36 */     return value.toString();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 41 */     if (this == p_equals_1_)
/*    */     {
/* 43 */       return true;
/*    */     }
/* 45 */     if (p_equals_1_ instanceof PropertyBool && super.equals(p_equals_1_)) {
/*    */       
/* 47 */       PropertyBool propertybool = (PropertyBool)p_equals_1_;
/* 48 */       return this.allowedValues.equals(propertybool.allowedValues);
/*    */     } 
/*    */ 
/*    */     
/* 52 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 58 */     return 31 * super.hashCode() + this.allowedValues.hashCode();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\properties\PropertyBool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */