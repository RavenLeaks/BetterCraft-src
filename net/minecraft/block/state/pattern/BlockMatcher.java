/*    */ package net.minecraft.block.state.pattern;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ 
/*    */ public class BlockMatcher
/*    */   implements Predicate<IBlockState>
/*    */ {
/*    */   private final Block block;
/*    */   
/*    */   private BlockMatcher(Block blockType) {
/* 14 */     this.block = blockType;
/*    */   }
/*    */ 
/*    */   
/*    */   public static BlockMatcher forBlock(Block blockType) {
/* 19 */     return new BlockMatcher(blockType);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean apply(@Nullable IBlockState p_apply_1_) {
/* 24 */     return (p_apply_1_ != null && p_apply_1_.getBlock() == this.block);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\state\pattern\BlockMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */