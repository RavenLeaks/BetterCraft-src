/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCocoa
/*     */   extends BlockHorizontal implements IGrowable {
/*  26 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 2);
/*  27 */   protected static final AxisAlignedBB[] COCOA_EAST_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.6875D, 0.4375D, 0.375D, 0.9375D, 0.75D, 0.625D), new AxisAlignedBB(0.5625D, 0.3125D, 0.3125D, 0.9375D, 0.75D, 0.6875D), new AxisAlignedBB(0.4375D, 0.1875D, 0.25D, 0.9375D, 0.75D, 0.75D) };
/*  28 */   protected static final AxisAlignedBB[] COCOA_WEST_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.0625D, 0.4375D, 0.375D, 0.3125D, 0.75D, 0.625D), new AxisAlignedBB(0.0625D, 0.3125D, 0.3125D, 0.4375D, 0.75D, 0.6875D), new AxisAlignedBB(0.0625D, 0.1875D, 0.25D, 0.5625D, 0.75D, 0.75D) };
/*  29 */   protected static final AxisAlignedBB[] COCOA_NORTH_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.375D, 0.4375D, 0.0625D, 0.625D, 0.75D, 0.3125D), new AxisAlignedBB(0.3125D, 0.3125D, 0.0625D, 0.6875D, 0.75D, 0.4375D), new AxisAlignedBB(0.25D, 0.1875D, 0.0625D, 0.75D, 0.75D, 0.5625D) };
/*  30 */   protected static final AxisAlignedBB[] COCOA_SOUTH_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.375D, 0.4375D, 0.6875D, 0.625D, 0.75D, 0.9375D), new AxisAlignedBB(0.3125D, 0.3125D, 0.5625D, 0.6875D, 0.75D, 0.9375D), new AxisAlignedBB(0.25D, 0.1875D, 0.4375D, 0.75D, 0.75D, 0.9375D) };
/*     */ 
/*     */   
/*     */   public BlockCocoa() {
/*  34 */     super(Material.PLANTS);
/*  35 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  36 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  41 */     if (!canBlockStay(worldIn, pos, state)) {
/*     */       
/*  43 */       dropBlock(worldIn, pos, state);
/*     */     }
/*  45 */     else if (worldIn.rand.nextInt(5) == 0) {
/*     */       
/*  47 */       int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */       
/*  49 */       if (i < 2)
/*     */       {
/*  51 */         worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(i + 1)), 2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/*  58 */     pos = pos.offset((EnumFacing)state.getValue((IProperty)FACING));
/*  59 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*  60 */     return (iblockstate.getBlock() == Blocks.LOG && iblockstate.getValue((IProperty)BlockOldLog.VARIANT) == BlockPlanks.EnumType.JUNGLE);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  78 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */     
/*  80 */     switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */       
/*     */       case SOUTH:
/*  83 */         return COCOA_SOUTH_AABB[i];
/*     */ 
/*     */       
/*     */       default:
/*  87 */         return COCOA_NORTH_AABB[i];
/*     */       
/*     */       case WEST:
/*  90 */         return COCOA_WEST_AABB[i];
/*     */       case EAST:
/*     */         break;
/*  93 */     }  return COCOA_EAST_AABB[i];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 103 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 112 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 120 */     EnumFacing enumfacing = EnumFacing.fromAngle(placer.rotationYaw);
/* 121 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)enumfacing), 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 130 */     if (!facing.getAxis().isHorizontal())
/*     */     {
/* 132 */       facing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 135 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)facing.getOpposite()).withProperty((IProperty)AGE, Integer.valueOf(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 145 */     if (!canBlockStay(worldIn, pos, state))
/*     */     {
/* 147 */       dropBlock(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void dropBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 153 */     worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
/* 154 */     dropBlockAsItem(worldIn, pos, state, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 162 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/* 163 */     int j = 1;
/*     */     
/* 165 */     if (i >= 2)
/*     */     {
/* 167 */       j = 3;
/*     */     }
/*     */     
/* 170 */     for (int k = 0; k < j; k++)
/*     */     {
/* 172 */       spawnAsEntity(worldIn, pos, new ItemStack(Items.DYE, 1, EnumDyeColor.BROWN.getDyeDamage()));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 178 */     return new ItemStack(Items.DYE, 1, EnumDyeColor.BROWN.getDyeDamage());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 186 */     return (((Integer)state.getValue((IProperty)AGE)).intValue() < 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 191 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 196 */     worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(((Integer)state.getValue((IProperty)AGE)).intValue() + 1)), 2);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 201 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 209 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta)).withProperty((IProperty)AGE, Integer.valueOf((meta & 0xF) >> 2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 217 */     int i = 0;
/* 218 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/* 219 */     i |= ((Integer)state.getValue((IProperty)AGE)).intValue() << 2;
/* 220 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 225 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)AGE });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 230 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockCocoa.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */