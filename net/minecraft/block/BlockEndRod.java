/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockEndRod
/*     */   extends BlockDirectional {
/*  25 */   protected static final AxisAlignedBB END_ROD_VERTICAL_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
/*  26 */   protected static final AxisAlignedBB END_ROD_NS_AABB = new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 1.0D);
/*  27 */   protected static final AxisAlignedBB END_ROD_EW_AABB = new AxisAlignedBB(0.0D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);
/*     */ 
/*     */   
/*     */   protected BlockEndRod() {
/*  31 */     super(Material.CIRCUITS);
/*  32 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.UP));
/*  33 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/*  42 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/*  51 */     return state.withProperty((IProperty)FACING, (Comparable)mirrorIn.mirror((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  56 */     switch (((EnumFacing)state.getValue((IProperty)FACING)).getAxis()) {
/*     */ 
/*     */       
/*     */       default:
/*  60 */         return END_ROD_EW_AABB;
/*     */       
/*     */       case Z:
/*  63 */         return END_ROD_NS_AABB;
/*     */       case Y:
/*     */         break;
/*  66 */     }  return END_ROD_VERTICAL_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  80 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  85 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  94 */     IBlockState iblockstate = worldIn.getBlockState(pos.offset(facing.getOpposite()));
/*     */     
/*  96 */     if (iblockstate.getBlock() == Blocks.END_ROD) {
/*     */       
/*  98 */       EnumFacing enumfacing = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/*     */       
/* 100 */       if (enumfacing == facing)
/*     */       {
/* 102 */         return getDefaultState().withProperty((IProperty)FACING, (Comparable)facing.getOpposite());
/*     */       }
/*     */     } 
/*     */     
/* 106 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)facing);
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/* 111 */     EnumFacing enumfacing = (EnumFacing)stateIn.getValue((IProperty)FACING);
/* 112 */     double d0 = pos.getX() + 0.55D - (rand.nextFloat() * 0.1F);
/* 113 */     double d1 = pos.getY() + 0.55D - (rand.nextFloat() * 0.1F);
/* 114 */     double d2 = pos.getZ() + 0.55D - (rand.nextFloat() * 0.1F);
/* 115 */     double d3 = (0.4F - (rand.nextFloat() + rand.nextFloat()) * 0.4F);
/*     */     
/* 117 */     if (rand.nextInt(5) == 0)
/*     */     {
/* 119 */       worldIn.spawnParticle(EnumParticleTypes.END_ROD, d0 + enumfacing.getFrontOffsetX() * d3, d1 + enumfacing.getFrontOffsetY() * d3, d2 + enumfacing.getFrontOffsetZ() * d3, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D, rand.nextGaussian() * 0.005D, new int[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 125 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 133 */     IBlockState iblockstate = getDefaultState();
/* 134 */     iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.getFront(meta));
/* 135 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 143 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 148 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumPushReaction getMobilityFlag(IBlockState state) {
/* 153 */     return EnumPushReaction.NORMAL;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 158 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockEndRod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */