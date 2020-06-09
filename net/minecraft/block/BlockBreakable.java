/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class BlockBreakable
/*    */   extends Block
/*    */ {
/*    */   private final boolean ignoreSimilarity;
/*    */   
/*    */   protected BlockBreakable(Material materialIn, boolean ignoreSimilarityIn) {
/* 17 */     this(materialIn, ignoreSimilarityIn, materialIn.getMaterialMapColor());
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockBreakable(Material materialIn, boolean ignoreSimilarityIn, MapColor mapColorIn) {
/* 22 */     super(materialIn, mapColorIn);
/* 23 */     this.ignoreSimilarity = ignoreSimilarityIn;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isOpaqueCube(IBlockState state) {
/* 31 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 36 */     IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
/* 37 */     Block block = iblockstate.getBlock();
/*    */     
/* 39 */     if (this == Blocks.GLASS || this == Blocks.STAINED_GLASS) {
/*    */       
/* 41 */       if (blockState != iblockstate)
/*    */       {
/* 43 */         return true;
/*    */       }
/*    */       
/* 46 */       if (block == this)
/*    */       {
/* 48 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 52 */     return (!this.ignoreSimilarity && block == this) ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockBreakable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */