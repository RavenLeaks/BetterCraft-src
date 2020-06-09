/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDoor
/*     */   extends Block {
/*  33 */   public static final PropertyDirection FACING = BlockHorizontal.FACING;
/*  34 */   public static final PropertyBool OPEN = PropertyBool.create("open");
/*  35 */   public static final PropertyEnum<EnumHingePosition> HINGE = PropertyEnum.create("hinge", EnumHingePosition.class);
/*  36 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  37 */   public static final PropertyEnum<EnumDoorHalf> HALF = PropertyEnum.create("half", EnumDoorHalf.class);
/*  38 */   protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.1875D);
/*  39 */   protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.8125D, 1.0D, 1.0D, 1.0D);
/*  40 */   protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.8125D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/*  41 */   protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.1875D, 1.0D, 1.0D);
/*     */ 
/*     */   
/*     */   protected BlockDoor(Material materialIn) {
/*  45 */     super(materialIn);
/*  46 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)OPEN, Boolean.valueOf(false)).withProperty((IProperty)HINGE, EnumHingePosition.LEFT).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)HALF, EnumDoorHalf.LOWER));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  51 */     state = state.getActualState(source, pos);
/*  52 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*  53 */     boolean flag = !((Boolean)state.getValue((IProperty)OPEN)).booleanValue();
/*  54 */     boolean flag1 = (state.getValue((IProperty)HINGE) == EnumHingePosition.RIGHT);
/*     */     
/*  56 */     switch (enumfacing) {
/*     */ 
/*     */       
/*     */       default:
/*  60 */         return flag ? EAST_AABB : (flag1 ? NORTH_AABB : SOUTH_AABB);
/*     */       
/*     */       case SOUTH:
/*  63 */         return flag ? SOUTH_AABB : (flag1 ? EAST_AABB : WEST_AABB);
/*     */       
/*     */       case WEST:
/*  66 */         return flag ? WEST_AABB : (flag1 ? SOUTH_AABB : NORTH_AABB);
/*     */       case NORTH:
/*     */         break;
/*  69 */     }  return flag ? NORTH_AABB : (flag1 ? WEST_AABB : EAST_AABB);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/*  78 */     return I18n.translateToLocal((String.valueOf(getUnlocalizedName()) + ".name").replaceAll("tile", "item"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  86 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  91 */     return isOpen(combineMetadata(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getCloseSound() {
/* 101 */     return (this.blockMaterial == Material.IRON) ? 1011 : 1012;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getOpenSound() {
/* 106 */     return (this.blockMaterial == Material.IRON) ? 1005 : 1006;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
/* 114 */     if (state.getBlock() == Blocks.IRON_DOOR)
/*     */     {
/* 116 */       return MapColor.IRON;
/*     */     }
/* 118 */     if (state.getBlock() == Blocks.OAK_DOOR)
/*     */     {
/* 120 */       return BlockPlanks.EnumType.OAK.getMapColor();
/*     */     }
/* 122 */     if (state.getBlock() == Blocks.SPRUCE_DOOR)
/*     */     {
/* 124 */       return BlockPlanks.EnumType.SPRUCE.getMapColor();
/*     */     }
/* 126 */     if (state.getBlock() == Blocks.BIRCH_DOOR)
/*     */     {
/* 128 */       return BlockPlanks.EnumType.BIRCH.getMapColor();
/*     */     }
/* 130 */     if (state.getBlock() == Blocks.JUNGLE_DOOR)
/*     */     {
/* 132 */       return BlockPlanks.EnumType.JUNGLE.getMapColor();
/*     */     }
/* 134 */     if (state.getBlock() == Blocks.ACACIA_DOOR)
/*     */     {
/* 136 */       return BlockPlanks.EnumType.ACACIA.getMapColor();
/*     */     }
/*     */ 
/*     */     
/* 140 */     return (state.getBlock() == Blocks.DARK_OAK_DOOR) ? BlockPlanks.EnumType.DARK_OAK.getMapColor() : super.getMapColor(state, p_180659_2_, p_180659_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 146 */     if (this.blockMaterial == Material.IRON)
/*     */     {
/* 148 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 152 */     BlockPos blockpos = (state.getValue((IProperty)HALF) == EnumDoorHalf.LOWER) ? pos : pos.down();
/* 153 */     IBlockState iblockstate = pos.equals(blockpos) ? state : worldIn.getBlockState(blockpos);
/*     */     
/* 155 */     if (iblockstate.getBlock() != this)
/*     */     {
/* 157 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 161 */     state = iblockstate.cycleProperty((IProperty)OPEN);
/* 162 */     worldIn.setBlockState(blockpos, state, 10);
/* 163 */     worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
/* 164 */     worldIn.playEvent(playerIn, ((Boolean)state.getValue((IProperty)OPEN)).booleanValue() ? getOpenSound() : getCloseSound(), pos, 0);
/* 165 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void toggleDoor(World worldIn, BlockPos pos, boolean open) {
/* 172 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/*     */     
/* 174 */     if (iblockstate.getBlock() == this) {
/*     */       
/* 176 */       BlockPos blockpos = (iblockstate.getValue((IProperty)HALF) == EnumDoorHalf.LOWER) ? pos : pos.down();
/* 177 */       IBlockState iblockstate1 = (pos == blockpos) ? iblockstate : worldIn.getBlockState(blockpos);
/*     */       
/* 179 */       if (iblockstate1.getBlock() == this && ((Boolean)iblockstate1.getValue((IProperty)OPEN)).booleanValue() != open) {
/*     */         
/* 181 */         worldIn.setBlockState(blockpos, iblockstate1.withProperty((IProperty)OPEN, Boolean.valueOf(open)), 10);
/* 182 */         worldIn.markBlockRangeForRenderUpdate(blockpos, pos);
/* 183 */         worldIn.playEvent(null, open ? getOpenSound() : getCloseSound(), pos, 0);
/*     */       } 
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
/* 195 */     if (state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER) {
/*     */       
/* 197 */       BlockPos blockpos = pos.down();
/* 198 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 200 */       if (iblockstate.getBlock() != this)
/*     */       {
/* 202 */         worldIn.setBlockToAir(pos);
/*     */       }
/* 204 */       else if (blockIn != this)
/*     */       {
/* 206 */         iblockstate.neighborChanged(worldIn, blockpos, blockIn, p_189540_5_);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 211 */       boolean flag1 = false;
/* 212 */       BlockPos blockpos1 = pos.up();
/* 213 */       IBlockState iblockstate1 = worldIn.getBlockState(blockpos1);
/*     */       
/* 215 */       if (iblockstate1.getBlock() != this) {
/*     */         
/* 217 */         worldIn.setBlockToAir(pos);
/* 218 */         flag1 = true;
/*     */       } 
/*     */       
/* 221 */       if (!worldIn.getBlockState(pos.down()).isFullyOpaque()) {
/*     */         
/* 223 */         worldIn.setBlockToAir(pos);
/* 224 */         flag1 = true;
/*     */         
/* 226 */         if (iblockstate1.getBlock() == this)
/*     */         {
/* 228 */           worldIn.setBlockToAir(blockpos1);
/*     */         }
/*     */       } 
/*     */       
/* 232 */       if (flag1) {
/*     */         
/* 234 */         if (!worldIn.isRemote)
/*     */         {
/* 236 */           dropBlockAsItem(worldIn, pos, state, 0);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 241 */         boolean flag = !(!worldIn.isBlockPowered(pos) && !worldIn.isBlockPowered(blockpos1));
/*     */         
/* 243 */         if (blockIn != this && (flag || blockIn.getDefaultState().canProvidePower()) && flag != ((Boolean)iblockstate1.getValue((IProperty)POWERED)).booleanValue()) {
/*     */           
/* 245 */           worldIn.setBlockState(blockpos1, iblockstate1.withProperty((IProperty)POWERED, Boolean.valueOf(flag)), 2);
/*     */           
/* 247 */           if (flag != ((Boolean)state.getValue((IProperty)OPEN)).booleanValue()) {
/*     */             
/* 249 */             worldIn.setBlockState(pos, state.withProperty((IProperty)OPEN, Boolean.valueOf(flag)), 2);
/* 250 */             worldIn.markBlockRangeForRenderUpdate(pos, pos);
/* 251 */             worldIn.playEvent(null, flag ? getOpenSound() : getCloseSound(), pos, 0);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 263 */     return (state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER) ? Items.field_190931_a : getItem();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 268 */     if (pos.getY() >= 255)
/*     */     {
/* 270 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 274 */     return (worldIn.getBlockState(pos.down()).isFullyOpaque() && super.canPlaceBlockAt(worldIn, pos) && super.canPlaceBlockAt(worldIn, pos.up()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumPushReaction getMobilityFlag(IBlockState state) {
/* 280 */     return EnumPushReaction.DESTROY;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int combineMetadata(IBlockAccess worldIn, BlockPos pos) {
/* 285 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 286 */     int i = iblockstate.getBlock().getMetaFromState(iblockstate);
/* 287 */     boolean flag = isTop(i);
/* 288 */     IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
/* 289 */     int j = iblockstate1.getBlock().getMetaFromState(iblockstate1);
/* 290 */     int k = flag ? j : i;
/* 291 */     IBlockState iblockstate2 = worldIn.getBlockState(pos.up());
/* 292 */     int l = iblockstate2.getBlock().getMetaFromState(iblockstate2);
/* 293 */     int i1 = flag ? i : l;
/* 294 */     boolean flag1 = ((i1 & 0x1) != 0);
/* 295 */     boolean flag2 = ((i1 & 0x2) != 0);
/* 296 */     return removeHalfBit(k) | (flag ? 8 : 0) | (flag1 ? 16 : 0) | (flag2 ? 32 : 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 301 */     return new ItemStack(getItem());
/*     */   }
/*     */ 
/*     */   
/*     */   private Item getItem() {
/* 306 */     if (this == Blocks.IRON_DOOR)
/*     */     {
/* 308 */       return Items.IRON_DOOR;
/*     */     }
/* 310 */     if (this == Blocks.SPRUCE_DOOR)
/*     */     {
/* 312 */       return Items.SPRUCE_DOOR;
/*     */     }
/* 314 */     if (this == Blocks.BIRCH_DOOR)
/*     */     {
/* 316 */       return Items.BIRCH_DOOR;
/*     */     }
/* 318 */     if (this == Blocks.JUNGLE_DOOR)
/*     */     {
/* 320 */       return Items.JUNGLE_DOOR;
/*     */     }
/* 322 */     if (this == Blocks.ACACIA_DOOR)
/*     */     {
/* 324 */       return Items.ACACIA_DOOR;
/*     */     }
/*     */ 
/*     */     
/* 328 */     return (this == Blocks.DARK_OAK_DOOR) ? Items.DARK_OAK_DOOR : Items.OAK_DOOR;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/* 334 */     BlockPos blockpos = pos.down();
/* 335 */     BlockPos blockpos1 = pos.up();
/*     */     
/* 337 */     if (player.capabilities.isCreativeMode && state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER && worldIn.getBlockState(blockpos).getBlock() == this)
/*     */     {
/* 339 */       worldIn.setBlockToAir(blockpos);
/*     */     }
/*     */     
/* 342 */     if (state.getValue((IProperty)HALF) == EnumDoorHalf.LOWER && worldIn.getBlockState(blockpos1).getBlock() == this) {
/*     */       
/* 344 */       if (player.capabilities.isCreativeMode)
/*     */       {
/* 346 */         worldIn.setBlockToAir(pos);
/*     */       }
/*     */       
/* 349 */       worldIn.setBlockToAir(blockpos1);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 355 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 364 */     if (state.getValue((IProperty)HALF) == EnumDoorHalf.LOWER) {
/*     */       
/* 366 */       IBlockState iblockstate = worldIn.getBlockState(pos.up());
/*     */       
/* 368 */       if (iblockstate.getBlock() == this)
/*     */       {
/* 370 */         state = state.withProperty((IProperty)HINGE, iblockstate.getValue((IProperty)HINGE)).withProperty((IProperty)POWERED, iblockstate.getValue((IProperty)POWERED));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 375 */       IBlockState iblockstate1 = worldIn.getBlockState(pos.down());
/*     */       
/* 377 */       if (iblockstate1.getBlock() == this)
/*     */       {
/* 379 */         state = state.withProperty((IProperty)FACING, iblockstate1.getValue((IProperty)FACING)).withProperty((IProperty)OPEN, iblockstate1.getValue((IProperty)OPEN));
/*     */       }
/*     */     } 
/*     */     
/* 383 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 392 */     return (state.getValue((IProperty)HALF) != EnumDoorHalf.LOWER) ? state : state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 401 */     return (mirrorIn == Mirror.NONE) ? state : state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING))).cycleProperty((IProperty)HINGE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 409 */     return ((meta & 0x8) > 0) ? getDefaultState().withProperty((IProperty)HALF, EnumDoorHalf.UPPER).withProperty((IProperty)HINGE, ((meta & 0x1) > 0) ? EnumHingePosition.RIGHT : EnumHingePosition.LEFT).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x2) > 0))) : getDefaultState().withProperty((IProperty)HALF, EnumDoorHalf.LOWER).withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta & 0x3).rotateYCCW()).withProperty((IProperty)OPEN, Boolean.valueOf(((meta & 0x4) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 417 */     int i = 0;
/*     */     
/* 419 */     if (state.getValue((IProperty)HALF) == EnumDoorHalf.UPPER) {
/*     */       
/* 421 */       i |= 0x8;
/*     */       
/* 423 */       if (state.getValue((IProperty)HINGE) == EnumHingePosition.RIGHT)
/*     */       {
/* 425 */         i |= 0x1;
/*     */       }
/*     */       
/* 428 */       if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */       {
/* 430 */         i |= 0x2;
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 435 */       i |= ((EnumFacing)state.getValue((IProperty)FACING)).rotateY().getHorizontalIndex();
/*     */       
/* 437 */       if (((Boolean)state.getValue((IProperty)OPEN)).booleanValue())
/*     */       {
/* 439 */         i |= 0x4;
/*     */       }
/*     */     } 
/*     */     
/* 443 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static int removeHalfBit(int meta) {
/* 448 */     return meta & 0x7;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isOpen(IBlockAccess worldIn, BlockPos pos) {
/* 453 */     return isOpen(combineMetadata(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(IBlockAccess worldIn, BlockPos pos) {
/* 458 */     return getFacing(combineMetadata(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public static EnumFacing getFacing(int combinedMeta) {
/* 463 */     return EnumFacing.getHorizontal(combinedMeta & 0x3).rotateYCCW();
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean isOpen(int combinedMeta) {
/* 468 */     return ((combinedMeta & 0x4) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean isTop(int meta) {
/* 473 */     return ((meta & 0x8) != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 478 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)HALF, (IProperty)FACING, (IProperty)OPEN, (IProperty)HINGE, (IProperty)POWERED });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 483 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */   
/*     */   public enum EnumDoorHalf
/*     */     implements IStringSerializable {
/* 488 */     UPPER,
/* 489 */     LOWER;
/*     */ 
/*     */     
/*     */     public String toString() {
/* 493 */       return getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 498 */       return (this == UPPER) ? "upper" : "lower";
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumHingePosition
/*     */     implements IStringSerializable {
/* 504 */     LEFT,
/* 505 */     RIGHT;
/*     */ 
/*     */     
/*     */     public String toString() {
/* 509 */       return getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 514 */       return (this == LEFT) ? "left" : "right";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockDoor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */