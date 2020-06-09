/*    */ package net.minecraft.client.renderer.block.model.multipart;
/*    */ 
/*    */ import com.google.common.base.Function;
/*    */ import com.google.common.base.MoreObjects;
/*    */ import com.google.common.base.Optional;
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.base.Splitter;
/*    */ import com.google.common.collect.Iterables;
/*    */ import java.util.List;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.BlockStateContainer;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ 
/*    */ public class ConditionPropertyValue
/*    */   implements ICondition {
/* 18 */   private static final Splitter SPLITTER = Splitter.on('|').omitEmptyStrings();
/*    */   
/*    */   private final String key;
/*    */   private final String value;
/*    */   
/*    */   public ConditionPropertyValue(String keyIn, String valueIn) {
/* 24 */     this.key = keyIn;
/* 25 */     this.value = valueIn;
/*    */   }
/*    */   
/*    */   public Predicate<IBlockState> getPredicate(BlockStateContainer blockState) {
/*    */     Predicate<IBlockState> predicate;
/* 30 */     final IProperty<?> iproperty = blockState.getProperty(this.key);
/*    */     
/* 32 */     if (iproperty == null)
/*    */     {
/* 34 */       throw new RuntimeException(String.valueOf(toString()) + ": Definition: " + blockState + " has no property: " + this.key);
/*    */     }
/*    */ 
/*    */     
/* 38 */     String s = this.value;
/* 39 */     boolean flag = (!s.isEmpty() && s.charAt(0) == '!');
/*    */     
/* 41 */     if (flag)
/*    */     {
/* 43 */       s = s.substring(1);
/*    */     }
/*    */     
/* 46 */     List<String> list = SPLITTER.splitToList(s);
/*    */     
/* 48 */     if (list.isEmpty())
/*    */     {
/* 50 */       throw new RuntimeException(String.valueOf(toString()) + ": has an empty value: " + this.value);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 56 */     if (list.size() == 1) {
/*    */       
/* 58 */       predicate = makePredicate(iproperty, s);
/*    */     }
/*    */     else {
/*    */       
/* 62 */       predicate = Predicates.or(Iterables.transform(list, new Function<String, Predicate<IBlockState>>()
/*    */             {
/*    */               @Nullable
/*    */               public Predicate<IBlockState> apply(@Nullable String p_apply_1_)
/*    */               {
/* 67 */                 return ConditionPropertyValue.this.makePredicate(iproperty, p_apply_1_);
/*    */               }
/*    */             }));
/*    */     } 
/*    */     
/* 72 */     return flag ? Predicates.not(predicate) : predicate;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private Predicate<IBlockState> makePredicate(final IProperty<?> property, String valueIn) {
/* 79 */     final Optional<?> optional = property.parseValue(valueIn);
/*    */     
/* 81 */     if (!optional.isPresent())
/*    */     {
/* 83 */       throw new RuntimeException(String.valueOf(toString()) + ": has an unknown value: " + this.value);
/*    */     }
/*    */ 
/*    */     
/* 87 */     return new Predicate<IBlockState>()
/*    */       {
/*    */         public boolean apply(@Nullable IBlockState p_apply_1_)
/*    */         {
/* 91 */           return (p_apply_1_ != null && p_apply_1_.getValue(property).equals(optional.get()));
/*    */         }
/*    */       };
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 99 */     return MoreObjects.toStringHelper(this).add("key", this.key).add("value", this.value).toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\multipart\ConditionPropertyValue.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */