/*    */ package net.minecraft.block.state.pattern;
/*    */ 
/*    */ import com.google.common.base.Predicate;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ 
/*    */ public class BlockMaterialMatcher
/*    */   implements Predicate<IBlockState>
/*    */ {
/*    */   private final Material material;
/*    */   
/*    */   private BlockMaterialMatcher(Material materialIn) {
/* 14 */     this.material = materialIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public static BlockMaterialMatcher forMaterial(Material materialIn) {
/* 19 */     return new BlockMaterialMatcher(materialIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean apply(@Nullable IBlockState p_apply_1_) {
/* 24 */     return (p_apply_1_ != null && p_apply_1_.getMaterial() == this.material);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\state\pattern\BlockMaterialMatcher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */