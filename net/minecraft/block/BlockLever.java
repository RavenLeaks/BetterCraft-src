/*     */ package net.minecraft.block;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockLever
/*     */   extends Block {
/*  28 */   public static final PropertyEnum<EnumOrientation> FACING = PropertyEnum.create("facing", EnumOrientation.class);
/*  29 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  30 */   protected static final AxisAlignedBB LEVER_NORTH_AABB = new AxisAlignedBB(0.3125D, 0.20000000298023224D, 0.625D, 0.6875D, 0.800000011920929D, 1.0D);
/*  31 */   protected static final AxisAlignedBB LEVER_SOUTH_AABB = new AxisAlignedBB(0.3125D, 0.20000000298023224D, 0.0D, 0.6875D, 0.800000011920929D, 0.375D);
/*  32 */   protected static final AxisAlignedBB LEVER_WEST_AABB = new AxisAlignedBB(0.625D, 0.20000000298023224D, 0.3125D, 1.0D, 0.800000011920929D, 0.6875D);
/*  33 */   protected static final AxisAlignedBB LEVER_EAST_AABB = new AxisAlignedBB(0.0D, 0.20000000298023224D, 0.3125D, 0.375D, 0.800000011920929D, 0.6875D);
/*  34 */   protected static final AxisAlignedBB LEVER_UP_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.6000000238418579D, 0.75D);
/*  35 */   protected static final AxisAlignedBB LEVER_DOWN_AABB = new AxisAlignedBB(0.25D, 0.4000000059604645D, 0.25D, 0.75D, 1.0D, 0.75D);
/*     */ 
/*     */   
/*     */   protected BlockLever() {
/*  39 */     super(Material.CIRCUITS);
/*  40 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, EnumOrientation.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/*  41 */     setCreativeTab(CreativeTabs.REDSTONE);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  47 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  55 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  68 */     return canAttachTo(worldIn, pos, side);
/*     */   } public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/*  73 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/*  75 */       if (canAttachTo(worldIn, pos, enumfacing))
/*     */       {
/*  77 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean canAttachTo(World p_181090_0_, BlockPos p_181090_1_, EnumFacing p_181090_2_) {
/*  86 */     return BlockButton.canPlaceBlock(p_181090_0_, p_181090_1_, p_181090_2_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  95 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf(false));
/*     */     
/*  97 */     if (canAttachTo(worldIn, pos, facing))
/*     */     {
/*  99 */       return iblockstate.withProperty((IProperty)FACING, EnumOrientation.forFacings(facing, placer.getHorizontalFacing()));
/*     */     }
/*     */ 
/*     */     
/* 103 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 105 */       if (enumfacing != facing && canAttachTo(worldIn, pos, enumfacing))
/*     */       {
/* 107 */         return iblockstate.withProperty((IProperty)FACING, EnumOrientation.forFacings(enumfacing, placer.getHorizontalFacing()));
/*     */       }
/*     */     } 
/*     */     
/* 111 */     if (worldIn.getBlockState(pos.down()).isFullyOpaque())
/*     */     {
/* 113 */       return iblockstate.withProperty((IProperty)FACING, EnumOrientation.forFacings(EnumFacing.UP, placer.getHorizontalFacing()));
/*     */     }
/*     */ 
/*     */     
/* 117 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 129 */     if (checkCanSurvive(worldIn, pos, state) && !canAttachTo(worldIn, pos, ((EnumOrientation)state.getValue((IProperty)FACING)).getFacing())) {
/*     */       
/* 131 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 132 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkCanSurvive(World p_181091_1_, BlockPos p_181091_2_, IBlockState p_181091_3_) {
/* 138 */     if (canPlaceBlockAt(p_181091_1_, p_181091_2_))
/*     */     {
/* 140 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 144 */     dropBlockAsItem(p_181091_1_, p_181091_2_, p_181091_3_, 0);
/* 145 */     p_181091_1_.setBlockToAir(p_181091_2_);
/* 146 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/* 152 */     switch ((EnumOrientation)state.getValue((IProperty)FACING)) {
/*     */ 
/*     */       
/*     */       default:
/* 156 */         return LEVER_EAST_AABB;
/*     */       
/*     */       case WEST:
/* 159 */         return LEVER_WEST_AABB;
/*     */       
/*     */       case SOUTH:
/* 162 */         return LEVER_SOUTH_AABB;
/*     */       
/*     */       case NORTH:
/* 165 */         return LEVER_NORTH_AABB;
/*     */       
/*     */       case UP_Z:
/*     */       case UP_X:
/* 169 */         return LEVER_UP_AABB;
/*     */       case null:
/*     */       case DOWN_Z:
/*     */         break;
/* 173 */     }  return LEVER_DOWN_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 179 */     if (worldIn.isRemote)
/*     */     {
/* 181 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 185 */     state = state.cycleProperty((IProperty)POWERED);
/* 186 */     worldIn.setBlockState(pos, state, 3);
/* 187 */     float f = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue() ? 0.6F : 0.5F;
/* 188 */     worldIn.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3F, f);
/* 189 */     worldIn.notifyNeighborsOfStateChange(pos, this, false);
/* 190 */     EnumFacing enumfacing = ((EnumOrientation)state.getValue((IProperty)FACING)).getFacing();
/* 191 */     worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing.getOpposite()), this, false);
/* 192 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 201 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue()) {
/*     */       
/* 203 */       worldIn.notifyNeighborsOfStateChange(pos, this, false);
/* 204 */       EnumFacing enumfacing = ((EnumOrientation)state.getValue((IProperty)FACING)).getFacing();
/* 205 */       worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing.getOpposite()), this, false);
/*     */     } 
/*     */     
/* 208 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 213 */     return ((Boolean)blockState.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 218 */     if (!((Boolean)blockState.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 220 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 224 */     return (((EnumOrientation)blockState.getValue((IProperty)FACING)).getFacing() == side) ? 15 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower(IBlockState state) {
/* 233 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 241 */     return getDefaultState().withProperty((IProperty)FACING, EnumOrientation.byMetadata(meta & 0x7)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 249 */     int i = 0;
/* 250 */     i |= ((EnumOrientation)state.getValue((IProperty)FACING)).getMetadata();
/*     */     
/* 252 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
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
/* 266 */     switch (rot) {
/*     */       
/*     */       case null:
/* 269 */         switch ((EnumOrientation)state.getValue((IProperty)FACING)) {
/*     */           
/*     */           case EAST:
/* 272 */             return state.withProperty((IProperty)FACING, EnumOrientation.WEST);
/*     */           
/*     */           case WEST:
/* 275 */             return state.withProperty((IProperty)FACING, EnumOrientation.EAST);
/*     */           
/*     */           case SOUTH:
/* 278 */             return state.withProperty((IProperty)FACING, EnumOrientation.NORTH);
/*     */           
/*     */           case NORTH:
/* 281 */             return state.withProperty((IProperty)FACING, EnumOrientation.SOUTH);
/*     */         } 
/*     */         
/* 284 */         return state;
/*     */ 
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 288 */         switch ((EnumOrientation)state.getValue((IProperty)FACING)) {
/*     */           
/*     */           case EAST:
/* 291 */             return state.withProperty((IProperty)FACING, EnumOrientation.NORTH);
/*     */           
/*     */           case WEST:
/* 294 */             return state.withProperty((IProperty)FACING, EnumOrientation.SOUTH);
/*     */           
/*     */           case SOUTH:
/* 297 */             return state.withProperty((IProperty)FACING, EnumOrientation.EAST);
/*     */           
/*     */           case NORTH:
/* 300 */             return state.withProperty((IProperty)FACING, EnumOrientation.WEST);
/*     */           
/*     */           case UP_Z:
/* 303 */             return state.withProperty((IProperty)FACING, EnumOrientation.UP_X);
/*     */           
/*     */           case UP_X:
/* 306 */             return state.withProperty((IProperty)FACING, EnumOrientation.UP_Z);
/*     */           
/*     */           case null:
/* 309 */             return state.withProperty((IProperty)FACING, EnumOrientation.DOWN_Z);
/*     */           
/*     */           case DOWN_Z:
/* 312 */             return state.withProperty((IProperty)FACING, EnumOrientation.DOWN_X);
/*     */         } 
/*     */       
/*     */       case CLOCKWISE_90:
/* 316 */         switch ((EnumOrientation)state.getValue((IProperty)FACING)) {
/*     */           
/*     */           case EAST:
/* 319 */             return state.withProperty((IProperty)FACING, EnumOrientation.SOUTH);
/*     */           
/*     */           case WEST:
/* 322 */             return state.withProperty((IProperty)FACING, EnumOrientation.NORTH);
/*     */           
/*     */           case SOUTH:
/* 325 */             return state.withProperty((IProperty)FACING, EnumOrientation.WEST);
/*     */           
/*     */           case NORTH:
/* 328 */             return state.withProperty((IProperty)FACING, EnumOrientation.EAST);
/*     */           
/*     */           case UP_Z:
/* 331 */             return state.withProperty((IProperty)FACING, EnumOrientation.UP_X);
/*     */           
/*     */           case UP_X:
/* 334 */             return state.withProperty((IProperty)FACING, EnumOrientation.UP_Z);
/*     */           
/*     */           case null:
/* 337 */             return state.withProperty((IProperty)FACING, EnumOrientation.DOWN_Z);
/*     */           
/*     */           case DOWN_Z:
/* 340 */             return state.withProperty((IProperty)FACING, EnumOrientation.DOWN_X);
/*     */         } 
/*     */         break;
/*     */     } 
/* 344 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 354 */     return state.withRotation(mirrorIn.toRotation(((EnumOrientation)state.getValue((IProperty)FACING)).getFacing()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 359 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)POWERED });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 364 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */   
/*     */   public enum EnumOrientation
/*     */     implements IStringSerializable {
/* 369 */     DOWN_X(0, "down_x", EnumFacing.DOWN),
/* 370 */     EAST(1, "east", EnumFacing.EAST),
/* 371 */     WEST(2, "west", EnumFacing.WEST),
/* 372 */     SOUTH(3, "south", EnumFacing.SOUTH),
/* 373 */     NORTH(4, "north", EnumFacing.NORTH),
/* 374 */     UP_Z(5, "up_z", EnumFacing.UP),
/* 375 */     UP_X(6, "up_x", EnumFacing.UP),
/* 376 */     DOWN_Z(7, "down_z", EnumFacing.DOWN);
/*     */     
/* 378 */     private static final EnumOrientation[] META_LOOKUP = new EnumOrientation[(values()).length];
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
/*     */     private final int meta;
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
/*     */     private final String name;
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
/*     */     private final EnumFacing facing;
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
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumOrientation[] arrayOfEnumOrientation;
/* 468 */       for (i = (arrayOfEnumOrientation = values()).length, b = 0; b < i; ) { EnumOrientation blocklever$enumorientation = arrayOfEnumOrientation[b];
/*     */         
/* 470 */         META_LOOKUP[blocklever$enumorientation.getMetadata()] = blocklever$enumorientation;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     EnumOrientation(int meta, String name, EnumFacing facing) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */       this.facing = facing;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public EnumFacing getFacing() {
/*     */       return this.facing;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static EnumOrientation byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length)
/*     */         meta = 0; 
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */     
/*     */     public static EnumOrientation forFacings(EnumFacing clickedSide, EnumFacing entityFacing) {
/*     */       switch (clickedSide) {
/*     */         case null:
/*     */           switch (entityFacing.getAxis()) {
/*     */             case null:
/*     */               return DOWN_X;
/*     */             case Z:
/*     */               return DOWN_Z;
/*     */           } 
/*     */           throw new IllegalArgumentException("Invalid entityFacing " + entityFacing + " for facing " + clickedSide);
/*     */         case UP:
/*     */           switch (entityFacing.getAxis()) {
/*     */             case null:
/*     */               return UP_X;
/*     */             case Z:
/*     */               return UP_Z;
/*     */           } 
/*     */           throw new IllegalArgumentException("Invalid entityFacing " + entityFacing + " for facing " + clickedSide);
/*     */         case NORTH:
/*     */           return NORTH;
/*     */         case SOUTH:
/*     */           return SOUTH;
/*     */         case WEST:
/*     */           return WEST;
/*     */         case EAST:
/*     */           return EAST;
/*     */       } 
/*     */       throw new IllegalArgumentException("Invalid facing: " + clickedSide);
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockLever.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */