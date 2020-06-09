/*    */ package net.minecraft.client.renderer.block.model.multipart;
/*    */ 
/*    */ import com.google.common.base.Function;
/*    */ import com.google.common.base.Predicate;
/*    */ import com.google.common.base.Predicates;
/*    */ import com.google.common.collect.Iterables;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.state.BlockStateContainer;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ 
/*    */ public class ConditionAnd
/*    */   implements ICondition
/*    */ {
/*    */   private final Iterable<ICondition> conditions;
/*    */   
/*    */   public ConditionAnd(Iterable<ICondition> conditionsIn) {
/* 17 */     this.conditions = conditionsIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public Predicate<IBlockState> getPredicate(final BlockStateContainer blockState) {
/* 22 */     return Predicates.and(Iterables.transform(this.conditions, new Function<ICondition, Predicate<IBlockState>>()
/*    */           {
/*    */             @Nullable
/*    */             public Predicate<IBlockState> apply(@Nullable ICondition p_apply_1_)
/*    */             {
/* 27 */               return (p_apply_1_ == null) ? null : p_apply_1_.getPredicate(blockState);
/*    */             }
/*    */           }));
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\multipart\ConditionAnd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */