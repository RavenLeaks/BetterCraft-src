/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockObserver
/*     */   extends BlockDirectional {
/*  20 */   public static final PropertyBool field_190963_a = PropertyBool.create("powered");
/*     */ 
/*     */   
/*     */   public BlockObserver() {
/*  24 */     super(Material.ROCK);
/*  25 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.SOUTH).withProperty((IProperty)field_190963_a, Boolean.valueOf(false)));
/*  26 */     setCreativeTab(CreativeTabs.REDSTONE);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/*  31 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)field_190963_a });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/*  40 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/*  49 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  54 */     if (((Boolean)state.getValue((IProperty)field_190963_a)).booleanValue()) {
/*     */       
/*  56 */       worldIn.setBlockState(pos, state.withProperty((IProperty)field_190963_a, Boolean.valueOf(false)), 2);
/*     */     }
/*     */     else {
/*     */       
/*  60 */       worldIn.setBlockState(pos, state.withProperty((IProperty)field_190963_a, Boolean.valueOf(true)), 2);
/*  61 */       worldIn.scheduleUpdate(pos, this, 2);
/*     */     } 
/*     */     
/*  64 */     func_190961_e(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_190962_b(IBlockState p_190962_1_, World p_190962_2_, BlockPos p_190962_3_, Block p_190962_4_, BlockPos p_190962_5_) {
/*  78 */     if (!p_190962_2_.isRemote && p_190962_3_.offset((EnumFacing)p_190962_1_.getValue((IProperty)FACING)).equals(p_190962_5_))
/*     */     {
/*  80 */       func_190960_d(p_190962_1_, p_190962_2_, p_190962_3_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190960_d(IBlockState p_190960_1_, World p_190960_2_, BlockPos p_190960_3_) {
/*  86 */     if (!((Boolean)p_190960_1_.getValue((IProperty)field_190963_a)).booleanValue())
/*     */     {
/*  88 */       if (!p_190960_2_.isUpdateScheduled(p_190960_3_, this))
/*     */       {
/*  90 */         p_190960_2_.scheduleUpdate(p_190960_3_, this, 2);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_190961_e(World p_190961_1_, BlockPos p_190961_2_, IBlockState p_190961_3_) {
/*  97 */     EnumFacing enumfacing = (EnumFacing)p_190961_3_.getValue((IProperty)FACING);
/*  98 */     BlockPos blockpos = p_190961_2_.offset(enumfacing.getOpposite());
/*  99 */     p_190961_1_.func_190524_a(blockpos, this, p_190961_2_);
/* 100 */     p_190961_1_.notifyNeighborsOfStateExcept(blockpos, this, enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower(IBlockState state) {
/* 108 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 113 */     return blockState.getWeakPower(blockAccess, pos, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 118 */     return (((Boolean)blockState.getValue((IProperty)field_190963_a)).booleanValue() && blockState.getValue((IProperty)FACING) == side) ? 15 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 126 */     if (!worldIn.isRemote) {
/*     */       
/* 128 */       if (((Boolean)state.getValue((IProperty)field_190963_a)).booleanValue())
/*     */       {
/* 130 */         updateTick(worldIn, pos, state, worldIn.rand);
/*     */       }
/*     */       
/* 133 */       func_190960_d(state, worldIn, pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 142 */     if (((Boolean)state.getValue((IProperty)field_190963_a)).booleanValue() && worldIn.isUpdateScheduled(pos, this))
/*     */     {
/* 144 */       func_190961_e(worldIn, pos, state.withProperty((IProperty)field_190963_a, Boolean.valueOf(false)));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 154 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.func_190914_a(pos, placer).getOpposite());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 162 */     int i = 0;
/* 163 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 165 */     if (((Boolean)state.getValue((IProperty)field_190963_a)).booleanValue())
/*     */     {
/* 167 */       i |= 0x8;
/*     */     }
/*     */     
/* 170 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 178 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getFront(meta & 0x7));
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockObserver.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */