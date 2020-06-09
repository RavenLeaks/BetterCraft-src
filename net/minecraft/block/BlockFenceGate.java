/*     */ package net.minecraft.block;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFenceGate
/*     */   extends BlockHorizontal {
/*  25 */   public static final PropertyBool OPEN = PropertyBool.create("open");
/*  26 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  27 */   public static final PropertyBool IN_WALL = PropertyBool.create("in_wall");
/*  28 */   protected static final AxisAlignedBB AABB_COLLIDE_ZAXIS = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
/*  29 */   protected static final AxisAlignedBB AABB_COLLIDE_XAXIS = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
/*  30 */   protected static final AxisAlignedBB AABB_COLLIDE_ZAXIS_INWALL = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 0.8125D, 0.625D);
/*  31 */   protected static final AxisAlignedBB AABB_COLLIDE_XAXIS_INWALL = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 0.8125D, 1.0D);
/*  32 */   protected static final AxisAlignedBB AABB_CLOSED_SELECTED_ZAXIS = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.5D, 0.625D);
/*  33 */   protected static final AxisAlignedBB AABB_CLOSED_SELECTED_XAXIS = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.5D, 1.0D);
/*     */ 
/*     */   
/*     */   public BlockFenceGate(BlockPlanks.EnumType p_i46394_1_) {
/*  37 */     super(Material.WOOD, p_i46394_1_.getMapColor());
/*  38 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)OPEN, Boolean.valueOf(false)).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)IN_WALL, Boolean.valueOf(false)));
/*  39 */     setCreativeTab(CreativeTabs.REDSTONE);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  44 */     state = getActualState(state, source, pos);
/*     */     
/*  46 */     if (((Boolean)state.getValue((IProperty)IN_WALL)).booleanValue())
/*     */     {
/*  48 */       return (((EnumFacing)state.getValue((IProperty)FACING)).getAxis() == EnumFacing.Axis.X) ? AABB_COLLIDE_XAXIS_INWALL : AABB_COLLIDE_ZAXIS_INWALL;
/*     */     }
/*     */ 
/*     */     
/*  52 */     return (((EnumFacing)state.getValue((IProperty)FACING)).getAxis() == EnumFacing.Axis.X) ? AABB_COLLIDE_XAXIS : AABB_COLLIDE_ZAXIS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  62 */     EnumFacing.Axis enumfacing$axis = ((EnumFacing)state.getValue((IProperty)FACING)).getAxis();
/*     */     
/*  64 */     if ((enumfacing$axis == EnumFacing.Axis.Z && (worldIn.getBlockState(pos.west()).getBlock() == Blocks.COBBLESTONE_WALL || worldIn.getBlockState(pos.east()).getBlock() == Blocks.COBBLESTONE_WALL)) || (enumfacing$axis == EnumFacing.Axis.X && (worldIn.getBlockState(pos.north()).getBlock() == Blocks.COBBLESTONE_WALL || worldIn.getBlockState(pos.south()).getBlock() == Blocks.COBBLESTONE_WALL)))
/*     */     {
/*  66 */       state = state.withProperty((IProperty)IN_WALL, Boolean.valueOf(true));
/*     */     }
/*     */     
/*  69 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/*  78 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/*  87 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  92 */     return worldIn.getBlockState(pos.down()).getMaterial().isSolid() ? super.canPlaceBlockAt(worldIn, pos) : false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  98 */     if (((Boolean)blockState.getValue((IProperty)OPEN)).booleanValue())
/*     */     {
/* 100 */       return NULL_AABB;
/*     */     }
/*     */ 
/*     */     
/* 104 */     return (((EnumFacing)blockState.getValue((IProperty)FACING)).getAxis() == EnumFacing.Axis.Z) ? AABB_CLOSED_SELECTED_ZAXIS : AABB_CLOSED_SELECTED_XAXIS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 113 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/* 123 */     return ((Boolean)worldIn.getBlockState(pos).getValue((IProperty)OPEN)).booleanValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 132 */     boolean flag = worldIn.isBlockPowered(pos);
/* 133 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing()).withProperty((IProperty)OPEN, Boolean.valueOf(flag)).withProperty((IProperty)POWERED, Boolean.valueOf(flag)).withProperty((IProperty)IN_WALL, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 138 */     if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue()) {
/*     */       
/* 140 */       state = state.withProperty((IProperty)OPEN, Boolean.valueOf(false));
/* 141 */       worldIn.setBlockState(pos, state, 10);
/*     */     }
/*     */     else {
/*     */       
/* 145 */       EnumFacing enumfacing = EnumFacing.fromAngle(playerIn.rotationYaw);
/*     */       
/* 147 */       if (state.getValue((IProperty)FACING) == enumfacing.getOpposite())
/*     */       {
/* 149 */         state = state.withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */       }
/*     */       
/* 152 */       state = state.withProperty((IProperty)OPEN, Boolean.valueOf(true));
/* 153 */       worldIn.setBlockState(pos, state, 10);
/*     */     } 
/*     */     
/* 156 */     worldIn.playEvent(playerIn, ((Boolean)state.getValue((IProperty)OPEN)).booleanValue() ? 1008 : 1014, pos, 0);
/* 157 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 167 */     if (!worldIn.isRemote) {
/*     */       
/* 169 */       boolean flag = worldIn.isBlockPowered(pos);
/*     */       
/* 171 */       if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue() != flag) {
/*     */         
/* 173 */         worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(flag)).withProperty((IProperty)OPEN, Boolean.valueOf(flag)), 2);
/*     */         
/* 175 */         if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue() != flag)
/*     */         {
/* 177 */           worldIn.playEvent(null, flag ? 1008 : 1014, pos, 0);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 185 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 193 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta)).withProperty((IProperty)OPEN, Boolean.valueOf(((meta & 0x4) != 0))).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) != 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 201 */     int i = 0;
/* 202 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 204 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 206 */       i |= 0x8;
/*     */     }
/*     */     
/* 209 */     if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue())
/*     */     {
/* 211 */       i |= 0x4;
/*     */     }
/*     */     
/* 214 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 219 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)OPEN, (IProperty)POWERED, (IProperty)IN_WALL });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 224 */     if (p_193383_4_ != EnumFacing.UP && p_193383_4_ != EnumFacing.DOWN)
/*     */     {
/* 226 */       return (((EnumFacing)p_193383_2_.getValue((IProperty)FACING)).getAxis() == p_193383_4_.rotateY().getAxis()) ? BlockFaceShape.MIDDLE_POLE : BlockFaceShape.UNDEFINED;
/*     */     }
/*     */ 
/*     */     
/* 230 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockFenceGate.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */