/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.properties.PropertyInteger;
/*    */ import net.minecraft.block.state.BlockStateContainer;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.Mirror;
/*    */ import net.minecraft.util.Rotation;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockStandingSign
/*    */   extends BlockSign {
/* 14 */   public static final PropertyInteger ROTATION = PropertyInteger.create("rotation", 0, 15);
/*    */ 
/*    */   
/*    */   public BlockStandingSign() {
/* 18 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)ROTATION, Integer.valueOf(0)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 28 */     if (!worldIn.getBlockState(pos.down()).getMaterial().isSolid()) {
/*    */       
/* 30 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 31 */       worldIn.setBlockToAir(pos);
/*    */     } 
/*    */     
/* 34 */     super.neighborChanged(state, worldIn, pos, blockIn, p_189540_5_);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getStateFromMeta(int meta) {
/* 42 */     return getDefaultState().withProperty((IProperty)ROTATION, Integer.valueOf(meta));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 50 */     return ((Integer)state.getValue((IProperty)ROTATION)).intValue();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 59 */     return state.withProperty((IProperty)ROTATION, Integer.valueOf(rot.rotate(((Integer)state.getValue((IProperty)ROTATION)).intValue(), 16)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 68 */     return state.withProperty((IProperty)ROTATION, Integer.valueOf(mirrorIn.mirrorRotation(((Integer)state.getValue((IProperty)ROTATION)).intValue(), 16)));
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockStateContainer createBlockState() {
/* 73 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)ROTATION });
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockStandingSign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */