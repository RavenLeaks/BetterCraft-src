/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.IBlockAccess;
/*    */ 
/*    */ public class BlockCompressedPowered
/*    */   extends Block
/*    */ {
/*    */   public BlockCompressedPowered(Material materialIn, MapColor color) {
/* 14 */     super(materialIn, color);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean canProvidePower(IBlockState state) {
/* 22 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 27 */     return 15;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockCompressedPowered.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */