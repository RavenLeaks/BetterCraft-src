/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPistonExtension
/*     */   extends BlockDirectional {
/*  28 */   public static final PropertyEnum<EnumPistonType> TYPE = PropertyEnum.create("type", EnumPistonType.class);
/*  29 */   public static final PropertyBool SHORT = PropertyBool.create("short");
/*  30 */   protected static final AxisAlignedBB PISTON_EXTENSION_EAST_AABB = new AxisAlignedBB(0.75D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/*  31 */   protected static final AxisAlignedBB PISTON_EXTENSION_WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.25D, 1.0D, 1.0D);
/*  32 */   protected static final AxisAlignedBB PISTON_EXTENSION_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.75D, 1.0D, 1.0D, 1.0D);
/*  33 */   protected static final AxisAlignedBB PISTON_EXTENSION_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.25D);
/*  34 */   protected static final AxisAlignedBB PISTON_EXTENSION_UP_AABB = new AxisAlignedBB(0.0D, 0.75D, 0.0D, 1.0D, 1.0D, 1.0D);
/*  35 */   protected static final AxisAlignedBB PISTON_EXTENSION_DOWN_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D);
/*  36 */   protected static final AxisAlignedBB UP_ARM_AABB = new AxisAlignedBB(0.375D, -0.25D, 0.375D, 0.625D, 0.75D, 0.625D);
/*  37 */   protected static final AxisAlignedBB DOWN_ARM_AABB = new AxisAlignedBB(0.375D, 0.25D, 0.375D, 0.625D, 1.25D, 0.625D);
/*  38 */   protected static final AxisAlignedBB SOUTH_ARM_AABB = new AxisAlignedBB(0.375D, 0.375D, -0.25D, 0.625D, 0.625D, 0.75D);
/*  39 */   protected static final AxisAlignedBB NORTH_ARM_AABB = new AxisAlignedBB(0.375D, 0.375D, 0.25D, 0.625D, 0.625D, 1.25D);
/*  40 */   protected static final AxisAlignedBB EAST_ARM_AABB = new AxisAlignedBB(-0.25D, 0.375D, 0.375D, 0.75D, 0.625D, 0.625D);
/*  41 */   protected static final AxisAlignedBB WEST_ARM_AABB = new AxisAlignedBB(0.25D, 0.375D, 0.375D, 1.25D, 0.625D, 0.625D);
/*  42 */   protected static final AxisAlignedBB field_190964_J = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.75D, 0.625D);
/*  43 */   protected static final AxisAlignedBB field_190965_K = new AxisAlignedBB(0.375D, 0.25D, 0.375D, 0.625D, 1.0D, 0.625D);
/*  44 */   protected static final AxisAlignedBB field_190966_L = new AxisAlignedBB(0.375D, 0.375D, 0.0D, 0.625D, 0.625D, 0.75D);
/*  45 */   protected static final AxisAlignedBB field_190967_M = new AxisAlignedBB(0.375D, 0.375D, 0.25D, 0.625D, 0.625D, 1.0D);
/*  46 */   protected static final AxisAlignedBB field_190968_N = new AxisAlignedBB(0.0D, 0.375D, 0.375D, 0.75D, 0.625D, 0.625D);
/*  47 */   protected static final AxisAlignedBB field_190969_O = new AxisAlignedBB(0.25D, 0.375D, 0.375D, 1.0D, 0.625D, 0.625D);
/*     */ 
/*     */   
/*     */   public BlockPistonExtension() {
/*  51 */     super(Material.PISTON);
/*  52 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)TYPE, EnumPistonType.DEFAULT).withProperty((IProperty)SHORT, Boolean.valueOf(false)));
/*  53 */     setSoundType(SoundType.STONE);
/*  54 */     setHardness(0.5F);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  59 */     switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */ 
/*     */       
/*     */       default:
/*  63 */         return PISTON_EXTENSION_DOWN_AABB;
/*     */       
/*     */       case UP:
/*  66 */         return PISTON_EXTENSION_UP_AABB;
/*     */       
/*     */       case NORTH:
/*  69 */         return PISTON_EXTENSION_NORTH_AABB;
/*     */       
/*     */       case SOUTH:
/*  72 */         return PISTON_EXTENSION_SOUTH_AABB;
/*     */       
/*     */       case WEST:
/*  75 */         return PISTON_EXTENSION_WEST_AABB;
/*     */       case EAST:
/*     */         break;
/*  78 */     }  return PISTON_EXTENSION_EAST_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
/*  84 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, state.getBoundingBox((IBlockAccess)worldIn, pos));
/*  85 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, getArmShape(state));
/*     */   }
/*     */ 
/*     */   
/*     */   private AxisAlignedBB getArmShape(IBlockState state) {
/*  90 */     boolean flag = ((Boolean)state.getValue((IProperty)SHORT)).booleanValue();
/*     */     
/*  92 */     switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */ 
/*     */       
/*     */       default:
/*  96 */         return flag ? field_190965_K : DOWN_ARM_AABB;
/*     */       
/*     */       case UP:
/*  99 */         return flag ? field_190964_J : UP_ARM_AABB;
/*     */       
/*     */       case NORTH:
/* 102 */         return flag ? field_190967_M : NORTH_ARM_AABB;
/*     */       
/*     */       case SOUTH:
/* 105 */         return flag ? field_190966_L : SOUTH_ARM_AABB;
/*     */       
/*     */       case WEST:
/* 108 */         return flag ? field_190969_O : WEST_ARM_AABB;
/*     */       case EAST:
/*     */         break;
/* 111 */     }  return flag ? field_190968_N : EAST_ARM_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullyOpaque(IBlockState state) {
/* 120 */     return (state.getValue((IProperty)FACING) == EnumFacing.UP);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 125 */     if (player.capabilities.isCreativeMode) {
/*     */       
/* 127 */       BlockPos blockpos = pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite());
/* 128 */       Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */       
/* 130 */       if (block == Blocks.PISTON || block == Blocks.STICKY_PISTON)
/*     */       {
/* 132 */         worldIn.setBlockToAir(blockpos);
/*     */       }
/*     */     } 
/*     */     
/* 136 */     super.onBlockHarvested(worldIn, pos, state, player);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 144 */     super.breakBlock(worldIn, pos, state);
/* 145 */     EnumFacing enumfacing = ((EnumFacing)state.getValue((IProperty)FACING)).getOpposite();
/* 146 */     pos = pos.offset(enumfacing);
/* 147 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 149 */     if ((iblockstate.getBlock() == Blocks.PISTON || iblockstate.getBlock() == Blocks.STICKY_PISTON) && ((Boolean)iblockstate.getValue((IProperty)BlockPistonBase.EXTENDED)).booleanValue()) {
/*     */       
/* 151 */       iblockstate.getBlock().dropBlockAsItem(worldIn, pos, iblockstate, 0);
/* 152 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 161 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 166 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 171 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/* 179 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 187 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 197 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 198 */     BlockPos blockpos = pos.offset(enumfacing.getOpposite());
/* 199 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */     
/* 201 */     if (iblockstate.getBlock() != Blocks.PISTON && iblockstate.getBlock() != Blocks.STICKY_PISTON) {
/*     */       
/* 203 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */     else {
/*     */       
/* 207 */       iblockstate.neighborChanged(worldIn, blockpos, blockIn, p_189540_5_);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 213 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static EnumFacing getFacing(int meta) {
/* 219 */     int i = meta & 0x7;
/* 220 */     return (i > 5) ? null : EnumFacing.getFront(i);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 225 */     return new ItemStack((state.getValue((IProperty)TYPE) == EnumPistonType.STICKY) ? Blocks.STICKY_PISTON : Blocks.PISTON);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 233 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)TYPE, ((meta & 0x8) > 0) ? EnumPistonType.STICKY : EnumPistonType.DEFAULT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 241 */     int i = 0;
/* 242 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 244 */     if (state.getValue((IProperty)TYPE) == EnumPistonType.STICKY)
/*     */     {
/* 246 */       i |= 0x8;
/*     */     }
/*     */     
/* 249 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 258 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 267 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 272 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)TYPE, (IProperty)SHORT });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 277 */     return (p_193383_4_ == p_193383_2_.getValue((IProperty)FACING)) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
/*     */   }
/*     */   
/*     */   public enum EnumPistonType
/*     */     implements IStringSerializable {
/* 282 */     DEFAULT("normal"),
/* 283 */     STICKY("sticky");
/*     */     
/*     */     private final String VARIANT;
/*     */ 
/*     */     
/*     */     EnumPistonType(String name) {
/* 289 */       this.VARIANT = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 294 */       return this.VARIANT;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 299 */       return this.VARIANT;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockPistonExtension.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */