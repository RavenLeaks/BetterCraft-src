/*    */ package net.minecraft.client.renderer.block.model.multipart;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.state.BlockStateContainer;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ 
/*    */ public interface ICondition
/*    */ {
/* 10 */   public static final ICondition TRUE = new ICondition()
/*    */     {
/*    */       public Predicate<IBlockState> getPredicate(BlockStateContainer blockState)
/*    */       {
/* 14 */         return new Predicate<IBlockState>()
/*    */           {
/*    */             public boolean apply(@Nullable IBlockState p_apply_1_)
/*    */             {
/* 18 */               return true;
/*    */             }
/*    */           };
/*    */       }
/*    */     };
/* 23 */   public static final ICondition FALSE = new ICondition()
/*    */     {
/*    */       public Predicate<IBlockState> getPredicate(BlockStateContainer blockState)
/*    */       {
/* 27 */         return new Predicate<IBlockState>()
/*    */           {
/*    */             public boolean apply(@Nullable IBlockState p_apply_1_)
/*    */             {
/* 31 */               return false;
/*    */             }
/*    */           };
/*    */       }
/*    */     };
/*    */   
/*    */   Predicate<IBlockState> getPredicate(BlockStateContainer paramBlockStateContainer);
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\block\model\multipart\ICondition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */