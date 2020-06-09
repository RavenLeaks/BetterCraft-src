/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.RayTraceResult;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.world.Explosion;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockStairs
/*     */   extends Block {
/*  35 */   public static final PropertyDirection FACING = BlockHorizontal.FACING;
/*  36 */   public static final PropertyEnum<EnumHalf> HALF = PropertyEnum.create("half", EnumHalf.class);
/*  37 */   public static final PropertyEnum<EnumShape> SHAPE = PropertyEnum.create("shape", EnumShape.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  43 */   protected static final AxisAlignedBB AABB_SLAB_TOP = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  49 */   protected static final AxisAlignedBB AABB_QTR_TOP_WEST = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   protected static final AxisAlignedBB AABB_QTR_TOP_EAST = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   protected static final AxisAlignedBB AABB_QTR_TOP_NORTH = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  67 */   protected static final AxisAlignedBB AABB_QTR_TOP_SOUTH = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  73 */   protected static final AxisAlignedBB AABB_OCT_TOP_NW = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  79 */   protected static final AxisAlignedBB AABB_OCT_TOP_NE = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   protected static final AxisAlignedBB AABB_OCT_TOP_SW = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 0.5D, 1.0D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  91 */   protected static final AxisAlignedBB AABB_OCT_TOP_SE = new AxisAlignedBB(0.5D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  97 */   protected static final AxisAlignedBB AABB_SLAB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 103 */   protected static final AxisAlignedBB AABB_QTR_BOT_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 109 */   protected static final AxisAlignedBB AABB_QTR_BOT_EAST = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 115 */   protected static final AxisAlignedBB AABB_QTR_BOT_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 121 */   protected static final AxisAlignedBB AABB_QTR_BOT_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   protected static final AxisAlignedBB AABB_OCT_BOT_NW = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 133 */   protected static final AxisAlignedBB AABB_OCT_BOT_NE = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 139 */   protected static final AxisAlignedBB AABB_OCT_BOT_SW = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 0.5D, 0.5D, 1.0D);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 145 */   protected static final AxisAlignedBB AABB_OCT_BOT_SE = new AxisAlignedBB(0.5D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
/*     */   
/*     */   private final Block modelBlock;
/*     */   private final IBlockState modelState;
/*     */   
/*     */   protected BlockStairs(IBlockState modelState) {
/* 151 */     super((modelState.getBlock()).blockMaterial);
/* 152 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)HALF, EnumHalf.BOTTOM).withProperty((IProperty)SHAPE, EnumShape.STRAIGHT));
/* 153 */     this.modelBlock = modelState.getBlock();
/* 154 */     this.modelState = modelState;
/* 155 */     setHardness(this.modelBlock.blockHardness);
/* 156 */     setResistance(this.modelBlock.blockResistance / 3.0F);
/* 157 */     setSoundType(this.modelBlock.blockSoundType);
/* 158 */     setLightOpacity(255);
/* 159 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
/* 164 */     if (!p_185477_7_)
/*     */     {
/* 166 */       state = getActualState(state, (IBlockAccess)worldIn, pos);
/*     */     }
/*     */     
/* 169 */     for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state))
/*     */     {
/* 171 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static List<AxisAlignedBB> getCollisionBoxList(IBlockState bstate) {
/* 177 */     List<AxisAlignedBB> list = Lists.newArrayList();
/* 178 */     boolean flag = (bstate.getValue((IProperty)HALF) == EnumHalf.TOP);
/* 179 */     list.add(flag ? AABB_SLAB_TOP : AABB_SLAB_BOTTOM);
/* 180 */     EnumShape blockstairs$enumshape = (EnumShape)bstate.getValue((IProperty)SHAPE);
/*     */     
/* 182 */     if (blockstairs$enumshape == EnumShape.STRAIGHT || blockstairs$enumshape == EnumShape.INNER_LEFT || blockstairs$enumshape == EnumShape.INNER_RIGHT)
/*     */     {
/* 184 */       list.add(getCollQuarterBlock(bstate));
/*     */     }
/*     */     
/* 187 */     if (blockstairs$enumshape != EnumShape.STRAIGHT)
/*     */     {
/* 189 */       list.add(getCollEighthBlock(bstate));
/*     */     }
/*     */     
/* 192 */     return list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AxisAlignedBB getCollQuarterBlock(IBlockState bstate) {
/* 201 */     boolean flag = (bstate.getValue((IProperty)HALF) == EnumHalf.TOP);
/*     */     
/* 203 */     switch ((EnumFacing)bstate.getValue((IProperty)FACING)) {
/*     */ 
/*     */       
/*     */       default:
/* 207 */         return flag ? AABB_QTR_BOT_NORTH : AABB_QTR_TOP_NORTH;
/*     */       
/*     */       case SOUTH:
/* 210 */         return flag ? AABB_QTR_BOT_SOUTH : AABB_QTR_TOP_SOUTH;
/*     */       
/*     */       case WEST:
/* 213 */         return flag ? AABB_QTR_BOT_WEST : AABB_QTR_TOP_WEST;
/*     */       case EAST:
/*     */         break;
/* 216 */     }  return flag ? AABB_QTR_BOT_EAST : AABB_QTR_TOP_EAST;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static AxisAlignedBB getCollEighthBlock(IBlockState bstate) {
/* 227 */     EnumFacing enumfacing1, enumfacing = (EnumFacing)bstate.getValue((IProperty)FACING);
/*     */ 
/*     */     
/* 230 */     switch ((EnumShape)bstate.getValue((IProperty)SHAPE)) {
/*     */ 
/*     */       
/*     */       default:
/* 234 */         enumfacing1 = enumfacing;
/*     */         break;
/*     */       
/*     */       case OUTER_RIGHT:
/* 238 */         enumfacing1 = enumfacing.rotateY();
/*     */         break;
/*     */       
/*     */       case INNER_RIGHT:
/* 242 */         enumfacing1 = enumfacing.getOpposite();
/*     */         break;
/*     */       
/*     */       case null:
/* 246 */         enumfacing1 = enumfacing.rotateYCCW();
/*     */         break;
/*     */     } 
/* 249 */     boolean flag = (bstate.getValue((IProperty)HALF) == EnumHalf.TOP);
/*     */     
/* 251 */     switch (enumfacing1) {
/*     */ 
/*     */       
/*     */       default:
/* 255 */         return flag ? AABB_OCT_BOT_NW : AABB_OCT_TOP_NW;
/*     */       
/*     */       case SOUTH:
/* 258 */         return flag ? AABB_OCT_BOT_SE : AABB_OCT_TOP_SE;
/*     */       
/*     */       case WEST:
/* 261 */         return flag ? AABB_OCT_BOT_SW : AABB_OCT_TOP_SW;
/*     */       case EAST:
/*     */         break;
/* 264 */     }  return flag ? AABB_OCT_BOT_NE : AABB_OCT_TOP_NE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 270 */     p_193383_2_ = getActualState(p_193383_2_, p_193383_1_, p_193383_3_);
/*     */     
/* 272 */     if (p_193383_4_.getAxis() == EnumFacing.Axis.Y)
/*     */     {
/* 274 */       return (((p_193383_4_ == EnumFacing.UP) ? true : false) == ((p_193383_2_.getValue((IProperty)HALF) == EnumHalf.TOP) ? true : false)) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
/*     */     }
/*     */ 
/*     */     
/* 278 */     EnumShape blockstairs$enumshape = (EnumShape)p_193383_2_.getValue((IProperty)SHAPE);
/*     */     
/* 280 */     if (blockstairs$enumshape != EnumShape.OUTER_LEFT && blockstairs$enumshape != EnumShape.OUTER_RIGHT) {
/*     */       
/* 282 */       EnumFacing enumfacing = (EnumFacing)p_193383_2_.getValue((IProperty)FACING);
/*     */       
/* 284 */       switch (blockstairs$enumshape) {
/*     */         
/*     */         case INNER_RIGHT:
/* 287 */           return (enumfacing != p_193383_4_ && enumfacing != p_193383_4_.rotateYCCW()) ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
/*     */         
/*     */         case null:
/* 290 */           return (enumfacing != p_193383_4_ && enumfacing != p_193383_4_.rotateY()) ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
/*     */         
/*     */         case STRAIGHT:
/* 293 */           return (enumfacing == p_193383_4_) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
/*     */       } 
/*     */       
/* 296 */       return BlockFaceShape.UNDEFINED;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 301 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 311 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 316 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/* 321 */     this.modelBlock.randomDisplayTick(stateIn, worldIn, pos, rand);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
/* 326 */     this.modelBlock.onBlockClicked(worldIn, pos, playerIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
/* 334 */     this.modelBlock.onBlockDestroyedByPlayer(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
/* 339 */     return this.modelState.getPackedLightmapCoords(source, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float getExplosionResistance(Entity exploder) {
/* 347 */     return this.modelBlock.getExplosionResistance(exploder);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 352 */     return this.modelBlock.getBlockLayer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/* 360 */     return this.modelBlock.tickRate(worldIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
/* 365 */     return this.modelState.getSelectedBoundingBox(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3d motion) {
/* 370 */     return this.modelBlock.modifyAcceleration(worldIn, pos, entityIn, motion);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCollidable() {
/* 379 */     return this.modelBlock.isCollidable();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
/* 384 */     return this.modelBlock.canCollideCheck(state, hitIfLiquid);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 389 */     return this.modelBlock.canPlaceBlockAt(worldIn, pos);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 397 */     this.modelState.neighborChanged(worldIn, pos, Blocks.AIR, pos);
/* 398 */     this.modelBlock.onBlockAdded(worldIn, pos, this.modelState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 406 */     this.modelBlock.breakBlock(worldIn, pos, this.modelState);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
/* 414 */     this.modelBlock.onEntityWalk(worldIn, pos, entityIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 419 */     this.modelBlock.updateTick(worldIn, pos, state, rand);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 424 */     return this.modelBlock.onBlockActivated(worldIn, pos, this.modelState, playerIn, hand, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
/* 432 */     this.modelBlock.onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullyOpaque(IBlockState state) {
/* 440 */     return (state.getValue((IProperty)HALF) == EnumHalf.TOP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
/* 448 */     return this.modelBlock.getMapColor(this.modelState, p_180659_2_, p_180659_3_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 457 */     IBlockState iblockstate = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
/* 458 */     iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing()).withProperty((IProperty)SHAPE, EnumShape.STRAIGHT);
/* 459 */     return (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || hitY <= 0.5D)) ? iblockstate.withProperty((IProperty)HALF, EnumHalf.BOTTOM) : iblockstate.withProperty((IProperty)HALF, EnumHalf.TOP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
/* 469 */     List<RayTraceResult> list = Lists.newArrayList();
/*     */     
/* 471 */     for (AxisAlignedBB axisalignedbb : getCollisionBoxList(getActualState(blockState, (IBlockAccess)worldIn, pos)))
/*     */     {
/* 473 */       list.add(rayTrace(pos, start, end, axisalignedbb));
/*     */     }
/*     */     
/* 476 */     RayTraceResult raytraceresult1 = null;
/* 477 */     double d1 = 0.0D;
/*     */     
/* 479 */     for (RayTraceResult raytraceresult : list) {
/*     */       
/* 481 */       if (raytraceresult != null) {
/*     */         
/* 483 */         double d0 = raytraceresult.hitVec.squareDistanceTo(end);
/*     */         
/* 485 */         if (d0 > d1) {
/*     */           
/* 487 */           raytraceresult1 = raytraceresult;
/* 488 */           d1 = d0;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 493 */     return raytraceresult1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 501 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)HALF, ((meta & 0x4) > 0) ? EnumHalf.TOP : EnumHalf.BOTTOM);
/* 502 */     iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)EnumFacing.getFront(5 - (meta & 0x3)));
/* 503 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 511 */     int i = 0;
/*     */     
/* 513 */     if (state.getValue((IProperty)HALF) == EnumHalf.TOP)
/*     */     {
/* 515 */       i |= 0x4;
/*     */     }
/*     */     
/* 518 */     i |= 5 - ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/* 519 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 528 */     return state.withProperty((IProperty)SHAPE, getStairsShape(state, worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   private static EnumShape getStairsShape(IBlockState p_185706_0_, IBlockAccess p_185706_1_, BlockPos p_185706_2_) {
/* 533 */     EnumFacing enumfacing = (EnumFacing)p_185706_0_.getValue((IProperty)FACING);
/* 534 */     IBlockState iblockstate = p_185706_1_.getBlockState(p_185706_2_.offset(enumfacing));
/*     */     
/* 536 */     if (isBlockStairs(iblockstate) && p_185706_0_.getValue((IProperty)HALF) == iblockstate.getValue((IProperty)HALF)) {
/*     */       
/* 538 */       EnumFacing enumfacing1 = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/*     */       
/* 540 */       if (enumfacing1.getAxis() != ((EnumFacing)p_185706_0_.getValue((IProperty)FACING)).getAxis() && isDifferentStairs(p_185706_0_, p_185706_1_, p_185706_2_, enumfacing1.getOpposite())) {
/*     */         
/* 542 */         if (enumfacing1 == enumfacing.rotateYCCW())
/*     */         {
/* 544 */           return EnumShape.OUTER_LEFT;
/*     */         }
/*     */         
/* 547 */         return EnumShape.OUTER_RIGHT;
/*     */       } 
/*     */     } 
/*     */     
/* 551 */     IBlockState iblockstate1 = p_185706_1_.getBlockState(p_185706_2_.offset(enumfacing.getOpposite()));
/*     */     
/* 553 */     if (isBlockStairs(iblockstate1) && p_185706_0_.getValue((IProperty)HALF) == iblockstate1.getValue((IProperty)HALF)) {
/*     */       
/* 555 */       EnumFacing enumfacing2 = (EnumFacing)iblockstate1.getValue((IProperty)FACING);
/*     */       
/* 557 */       if (enumfacing2.getAxis() != ((EnumFacing)p_185706_0_.getValue((IProperty)FACING)).getAxis() && isDifferentStairs(p_185706_0_, p_185706_1_, p_185706_2_, enumfacing2)) {
/*     */         
/* 559 */         if (enumfacing2 == enumfacing.rotateYCCW())
/*     */         {
/* 561 */           return EnumShape.INNER_LEFT;
/*     */         }
/*     */         
/* 564 */         return EnumShape.INNER_RIGHT;
/*     */       } 
/*     */     } 
/*     */     
/* 568 */     return EnumShape.STRAIGHT;
/*     */   }
/*     */ 
/*     */   
/*     */   private static boolean isDifferentStairs(IBlockState p_185704_0_, IBlockAccess p_185704_1_, BlockPos p_185704_2_, EnumFacing p_185704_3_) {
/* 573 */     IBlockState iblockstate = p_185704_1_.getBlockState(p_185704_2_.offset(p_185704_3_));
/* 574 */     return !(isBlockStairs(iblockstate) && iblockstate.getValue((IProperty)FACING) == p_185704_0_.getValue((IProperty)FACING) && iblockstate.getValue((IProperty)HALF) == p_185704_0_.getValue((IProperty)HALF));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isBlockStairs(IBlockState state) {
/* 579 */     return state.getBlock() instanceof BlockStairs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 588 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 599 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 600 */     EnumShape blockstairs$enumshape = (EnumShape)state.getValue((IProperty)SHAPE);
/*     */     
/* 602 */     switch (mirrorIn) {
/*     */       
/*     */       case LEFT_RIGHT:
/* 605 */         if (enumfacing.getAxis() == EnumFacing.Axis.Z) {
/*     */           
/* 607 */           switch (blockstairs$enumshape) {
/*     */             
/*     */             case OUTER_LEFT:
/* 610 */               return state.withRotation(Rotation.CLOCKWISE_180).withProperty((IProperty)SHAPE, EnumShape.OUTER_RIGHT);
/*     */             
/*     */             case OUTER_RIGHT:
/* 613 */               return state.withRotation(Rotation.CLOCKWISE_180).withProperty((IProperty)SHAPE, EnumShape.OUTER_LEFT);
/*     */             
/*     */             case INNER_RIGHT:
/* 616 */               return state.withRotation(Rotation.CLOCKWISE_180).withProperty((IProperty)SHAPE, EnumShape.INNER_LEFT);
/*     */             
/*     */             case null:
/* 619 */               return state.withRotation(Rotation.CLOCKWISE_180).withProperty((IProperty)SHAPE, EnumShape.INNER_RIGHT);
/*     */           } 
/*     */           
/* 622 */           return state.withRotation(Rotation.CLOCKWISE_180);
/*     */         } 
/*     */         break;
/*     */ 
/*     */ 
/*     */       
/*     */       case null:
/* 629 */         if (enumfacing.getAxis() == EnumFacing.Axis.X)
/*     */         {
/* 631 */           switch (blockstairs$enumshape) {
/*     */             
/*     */             case OUTER_LEFT:
/* 634 */               return state.withRotation(Rotation.CLOCKWISE_180).withProperty((IProperty)SHAPE, EnumShape.OUTER_RIGHT);
/*     */             
/*     */             case OUTER_RIGHT:
/* 637 */               return state.withRotation(Rotation.CLOCKWISE_180).withProperty((IProperty)SHAPE, EnumShape.OUTER_LEFT);
/*     */             
/*     */             case INNER_RIGHT:
/* 640 */               return state.withRotation(Rotation.CLOCKWISE_180).withProperty((IProperty)SHAPE, EnumShape.INNER_RIGHT);
/*     */             
/*     */             case null:
/* 643 */               return state.withRotation(Rotation.CLOCKWISE_180).withProperty((IProperty)SHAPE, EnumShape.INNER_LEFT);
/*     */             
/*     */             case STRAIGHT:
/* 646 */               return state.withRotation(Rotation.CLOCKWISE_180);
/*     */           } 
/*     */         }
/*     */         break;
/*     */     } 
/* 651 */     return super.withMirror(state, mirrorIn);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 656 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)HALF, (IProperty)SHAPE });
/*     */   }
/*     */   
/*     */   public enum EnumHalf
/*     */     implements IStringSerializable {
/* 661 */     TOP("top"),
/* 662 */     BOTTOM("bottom");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumHalf(String name) {
/* 668 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 673 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 678 */       return this.name;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum EnumShape
/*     */     implements IStringSerializable {
/* 684 */     STRAIGHT("straight"),
/* 685 */     INNER_LEFT("inner_left"),
/* 686 */     INNER_RIGHT("inner_right"),
/* 687 */     OUTER_LEFT("outer_left"),
/* 688 */     OUTER_RIGHT("outer_right");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumShape(String name) {
/* 694 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 699 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 704 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockStairs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */