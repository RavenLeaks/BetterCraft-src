/*    */ package net.minecraft.block;
/*    */ 
/*    */ import net.minecraft.block.material.EnumPushReaction;
/*    */ import net.minecraft.block.material.MapColor;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.BlockStateContainer;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.creativetab.CreativeTabs;
/*    */ import net.minecraft.entity.EntityLivingBase;
/*    */ import net.minecraft.item.EnumDyeColor;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.Mirror;
/*    */ import net.minecraft.util.Rotation;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BlockGlazedTerracotta
/*    */   extends BlockHorizontal
/*    */ {
/*    */   public BlockGlazedTerracotta(EnumDyeColor p_i47400_1_) {
/* 22 */     super(Material.ROCK, MapColor.func_193558_a(p_i47400_1_));
/* 23 */     setHardness(1.4F);
/* 24 */     setSoundType(SoundType.STONE);
/* 25 */     String s = p_i47400_1_.getUnlocalizedName();
/*    */     
/* 27 */     if (s.length() > 1) {
/*    */       
/* 29 */       String s1 = String.valueOf(s.substring(0, 1).toUpperCase()) + s.substring(1, s.length());
/* 30 */       setUnlocalizedName("glazedTerracotta" + s1);
/*    */     } 
/*    */     
/* 33 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*    */   }
/*    */ 
/*    */   
/*    */   protected BlockStateContainer createBlockState() {
/* 38 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 47 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 56 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 65 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getMetaFromState(IBlockState state) {
/* 73 */     int i = 0;
/* 74 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/* 75 */     return i;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IBlockState getStateFromMeta(int meta) {
/* 83 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta));
/*    */   }
/*    */ 
/*    */   
/*    */   public EnumPushReaction getMobilityFlag(IBlockState state) {
/* 88 */     return EnumPushReaction.PUSH_ONLY;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockGlazedTerracotta.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */