/*     */ package net.minecraft.block;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTrapDoor
/*     */   extends Block {
/*  28 */   public static final PropertyDirection FACING = BlockHorizontal.FACING;
/*  29 */   public static final PropertyBool OPEN = PropertyBool.create("open");
/*  30 */   public static final PropertyEnum<DoorHalf> HALF = PropertyEnum.create("half", DoorHalf.class);
/*  31 */   protected static final AxisAlignedBB EAST_OPEN_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875D, 1.0D, 1.0D);
/*  32 */   protected static final AxisAlignedBB WEST_OPEN_AABB = new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/*  33 */   protected static final AxisAlignedBB SOUTH_OPEN_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
/*  34 */   protected static final AxisAlignedBB NORTH_OPEN_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);
/*  35 */   protected static final AxisAlignedBB BOTTOM_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.1875D, 1.0D);
/*  36 */   protected static final AxisAlignedBB TOP_AABB = new AxisAlignedBB(0.0D, 0.8125D, 0.0D, 1.0D, 1.0D, 1.0D);
/*     */ 
/*     */   
/*     */   protected BlockTrapDoor(Material materialIn) {
/*  40 */     super(materialIn);
/*  41 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)OPEN, Boolean.valueOf(false)).withProperty((IProperty)HALF, DoorHalf.BOTTOM));
/*  42 */     setCreativeTab(CreativeTabs.REDSTONE);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*     */     AxisAlignedBB axisalignedbb;
/*  49 */     if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue())
/*     */     
/*  51 */     { switch ((EnumFacing)state.getValue((IProperty)FACING))
/*     */       
/*     */       { 
/*     */         default:
/*  55 */           axisalignedbb = NORTH_OPEN_AABB;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  79 */           return axisalignedbb;case SOUTH: axisalignedbb = SOUTH_OPEN_AABB; return axisalignedbb;case WEST: axisalignedbb = WEST_OPEN_AABB; return axisalignedbb;case EAST: break; }  axisalignedbb = EAST_OPEN_AABB; } else if (state.getValue((IProperty)HALF) == DoorHalf.TOP) { axisalignedbb = TOP_AABB; } else { axisalignedbb = BOTTOM_AABB; }  return axisalignedbb;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  87 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  92 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  97 */     return !((Boolean)worldIn.getBlockState(pos).getValue((IProperty)OPEN)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 102 */     if (this.blockMaterial == Material.IRON)
/*     */     {
/* 104 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 108 */     state = state.cycleProperty((IProperty)OPEN);
/* 109 */     worldIn.setBlockState(pos, state, 2);
/* 110 */     playSound(playerIn, worldIn, pos, ((Boolean)state.getValue((IProperty)OPEN)).booleanValue());
/* 111 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void playSound(@Nullable EntityPlayer player, World worldIn, BlockPos pos, boolean p_185731_4_) {
/* 117 */     if (p_185731_4_) {
/*     */       
/* 119 */       int i = (this.blockMaterial == Material.IRON) ? 1037 : 1007;
/* 120 */       worldIn.playEvent(player, i, pos, 0);
/*     */     }
/*     */     else {
/*     */       
/* 124 */       int j = (this.blockMaterial == Material.IRON) ? 1036 : 1013;
/* 125 */       worldIn.playEvent(player, j, pos, 0);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 136 */     if (!worldIn.isRemote) {
/*     */       
/* 138 */       boolean flag = worldIn.isBlockPowered(pos);
/*     */       
/* 140 */       if (flag || blockIn.getDefaultState().canProvidePower()) {
/*     */         
/* 142 */         boolean flag1 = ((Boolean)state.getValue((IProperty)OPEN)).booleanValue();
/*     */         
/* 144 */         if (flag1 != flag) {
/*     */           
/* 146 */           worldIn.setBlockState(pos, state.withProperty((IProperty)OPEN, Boolean.valueOf(flag)), 2);
/* 147 */           playSound((EntityPlayer)null, worldIn, pos, flag);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 159 */     IBlockState iblockstate = getDefaultState();
/*     */     
/* 161 */     if (facing.getAxis().isHorizontal()) {
/*     */       
/* 163 */       iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)facing).withProperty((IProperty)OPEN, Boolean.valueOf(false));
/* 164 */       iblockstate = iblockstate.withProperty((IProperty)HALF, (hitY > 0.5F) ? DoorHalf.TOP : DoorHalf.BOTTOM);
/*     */     }
/*     */     else {
/*     */       
/* 168 */       iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite()).withProperty((IProperty)OPEN, Boolean.valueOf(false));
/* 169 */       iblockstate = iblockstate.withProperty((IProperty)HALF, (facing == EnumFacing.UP) ? DoorHalf.BOTTOM : DoorHalf.TOP);
/*     */     } 
/*     */     
/* 172 */     if (worldIn.isBlockPowered(pos))
/*     */     {
/* 174 */       iblockstate = iblockstate.withProperty((IProperty)OPEN, Boolean.valueOf(true));
/*     */     }
/*     */     
/* 177 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/* 185 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static EnumFacing getFacing(int meta) {
/* 190 */     switch (meta & 0x3) {
/*     */       
/*     */       case 0:
/* 193 */         return EnumFacing.NORTH;
/*     */       
/*     */       case 1:
/* 196 */         return EnumFacing.SOUTH;
/*     */       
/*     */       case 2:
/* 199 */         return EnumFacing.WEST;
/*     */     } 
/*     */ 
/*     */     
/* 203 */     return EnumFacing.EAST;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static int getMetaForFacing(EnumFacing facing) {
/* 209 */     switch (facing) {
/*     */       
/*     */       case NORTH:
/* 212 */         return 0;
/*     */       
/*     */       case SOUTH:
/* 215 */         return 1;
/*     */       
/*     */       case WEST:
/* 218 */         return 2;
/*     */     } 
/*     */ 
/*     */     
/* 222 */     return 3;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 228 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 236 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)OPEN, Boolean.valueOf(((meta & 0x4) != 0))).withProperty((IProperty)HALF, ((meta & 0x8) == 0) ? DoorHalf.BOTTOM : DoorHalf.TOP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 244 */     int i = 0;
/* 245 */     i |= getMetaForFacing((EnumFacing)state.getValue((IProperty)FACING));
/*     */     
/* 247 */     if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue())
/*     */     {
/* 249 */       i |= 0x4;
/*     */     }
/*     */     
/* 252 */     if (state.getValue((IProperty)HALF) == DoorHalf.TOP)
/*     */     {
/* 254 */       i |= 0x8;
/*     */     }
/*     */     
/* 257 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 266 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 275 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 280 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)OPEN, (IProperty)HALF });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 285 */     return (((p_193383_4_ == EnumFacing.UP && p_193383_2_.getValue((IProperty)HALF) == DoorHalf.TOP) || (p_193383_4_ == EnumFacing.DOWN && p_193383_2_.getValue((IProperty)HALF) == DoorHalf.BOTTOM)) && !((Boolean)p_193383_2_.getValue((IProperty)OPEN)).booleanValue()) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
/*     */   }
/*     */   
/*     */   public enum DoorHalf
/*     */     implements IStringSerializable {
/* 290 */     TOP("top"),
/* 291 */     BOTTOM("bottom");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     DoorHalf(String name) {
/* 297 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 302 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 307 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockTrapDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */