/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.MoreObjects;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockTripWireHook
/*     */   extends Block
/*     */ {
/*  31 */   public static final PropertyDirection FACING = BlockHorizontal.FACING;
/*  32 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  33 */   public static final PropertyBool ATTACHED = PropertyBool.create("attached");
/*  34 */   protected static final AxisAlignedBB HOOK_NORTH_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.625D, 0.6875D, 0.625D, 1.0D);
/*  35 */   protected static final AxisAlignedBB HOOK_SOUTH_AABB = new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.6875D, 0.625D, 0.375D);
/*  36 */   protected static final AxisAlignedBB HOOK_WEST_AABB = new AxisAlignedBB(0.625D, 0.0D, 0.3125D, 1.0D, 0.625D, 0.6875D);
/*  37 */   protected static final AxisAlignedBB HOOK_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 0.375D, 0.625D, 0.6875D);
/*     */ 
/*     */   
/*     */   public BlockTripWireHook() {
/*  41 */     super(Material.CIRCUITS);
/*  42 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)ATTACHED, Boolean.valueOf(false)));
/*  43 */     setCreativeTab(CreativeTabs.REDSTONE);
/*  44 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  49 */     switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */ 
/*     */       
/*     */       default:
/*  53 */         return HOOK_EAST_AABB;
/*     */       
/*     */       case WEST:
/*  56 */         return HOOK_WEST_AABB;
/*     */       
/*     */       case SOUTH:
/*  59 */         return HOOK_SOUTH_AABB;
/*     */       case NORTH:
/*     */         break;
/*  62 */     }  return HOOK_NORTH_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  69 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  82 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  90 */     EnumFacing enumfacing = side.getOpposite();
/*  91 */     BlockPos blockpos = pos.offset(enumfacing);
/*  92 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*  93 */     boolean flag = func_193382_c(iblockstate.getBlock());
/*  94 */     return (!flag && side.getAxis().isHorizontal() && iblockstate.func_193401_d((IBlockAccess)worldIn, blockpos, side) == BlockFaceShape.SOLID && !iblockstate.canProvidePower());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  99 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 101 */       if (canPlaceBlockOnSide(worldIn, pos, enumfacing))
/*     */       {
/* 103 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 116 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)POWERED, Boolean.valueOf(false)).withProperty((IProperty)ATTACHED, Boolean.valueOf(false));
/*     */     
/* 118 */     if (facing.getAxis().isHorizontal())
/*     */     {
/* 120 */       iblockstate = iblockstate.withProperty((IProperty)FACING, (Comparable)facing);
/*     */     }
/*     */     
/* 123 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 131 */     calculateState(worldIn, pos, state, false, false, -1, (IBlockState)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 141 */     if (blockIn != this)
/*     */     {
/* 143 */       if (checkForDrop(worldIn, pos, state)) {
/*     */         
/* 145 */         EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */         
/* 147 */         if (!canPlaceBlockOnSide(worldIn, pos, enumfacing)) {
/*     */           
/* 149 */           dropBlockAsItem(worldIn, pos, state, 0);
/* 150 */           worldIn.setBlockToAir(pos);
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void calculateState(World worldIn, BlockPos pos, IBlockState hookState, boolean p_176260_4_, boolean p_176260_5_, int p_176260_6_, @Nullable IBlockState p_176260_7_) {
/*     */     int m;
/* 158 */     EnumFacing enumfacing = (EnumFacing)hookState.getValue((IProperty)FACING);
/* 159 */     int flag = ((Boolean)hookState.getValue((IProperty)ATTACHED)).booleanValue();
/* 160 */     boolean flag1 = ((Boolean)hookState.getValue((IProperty)POWERED)).booleanValue();
/* 161 */     boolean flag2 = !p_176260_4_;
/* 162 */     boolean flag3 = false;
/* 163 */     int i = 0;
/* 164 */     IBlockState[] aiblockstate = new IBlockState[42];
/*     */     
/* 166 */     for (int j = 1; j < 42; j++) {
/*     */       
/* 168 */       BlockPos blockpos = pos.offset(enumfacing, j);
/* 169 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 171 */       if (iblockstate.getBlock() == Blocks.TRIPWIRE_HOOK) {
/*     */         
/* 173 */         if (iblockstate.getValue((IProperty)FACING) == enumfacing.getOpposite())
/*     */         {
/* 175 */           i = j;
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/* 181 */       if (iblockstate.getBlock() != Blocks.TRIPWIRE && j != p_176260_6_) {
/*     */         
/* 183 */         aiblockstate[j] = null;
/* 184 */         flag2 = false;
/*     */       }
/*     */       else {
/*     */         
/* 188 */         if (j == p_176260_6_)
/*     */         {
/* 190 */           iblockstate = (IBlockState)MoreObjects.firstNonNull(p_176260_7_, iblockstate);
/*     */         }
/*     */         
/* 193 */         boolean flag4 = !((Boolean)iblockstate.getValue((IProperty)BlockTripWire.DISARMED)).booleanValue();
/* 194 */         boolean flag5 = ((Boolean)iblockstate.getValue((IProperty)BlockTripWire.POWERED)).booleanValue();
/* 195 */         m = flag3 | ((flag4 && flag5) ? 1 : 0);
/* 196 */         aiblockstate[j] = iblockstate;
/*     */         
/* 198 */         if (j == p_176260_6_) {
/*     */           
/* 200 */           worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/* 201 */           flag2 &= flag4;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 206 */     int k = flag2 & ((i > 1) ? 1 : 0);
/* 207 */     m &= k;
/* 208 */     IBlockState iblockstate1 = getDefaultState().withProperty((IProperty)ATTACHED, Boolean.valueOf(k)).withProperty((IProperty)POWERED, Boolean.valueOf(m));
/*     */     
/* 210 */     if (i > 0) {
/*     */       
/* 212 */       BlockPos blockpos1 = pos.offset(enumfacing, i);
/* 213 */       EnumFacing enumfacing1 = enumfacing.getOpposite();
/* 214 */       worldIn.setBlockState(blockpos1, iblockstate1.withProperty((IProperty)FACING, (Comparable)enumfacing1), 3);
/* 215 */       notifyNeighbors(worldIn, blockpos1, enumfacing1);
/* 216 */       playSound(worldIn, blockpos1, k, m, flag, flag1);
/*     */     } 
/*     */     
/* 219 */     playSound(worldIn, pos, k, m, flag, flag1);
/*     */     
/* 221 */     if (!p_176260_4_) {
/*     */       
/* 223 */       worldIn.setBlockState(pos, iblockstate1.withProperty((IProperty)FACING, (Comparable)enumfacing), 3);
/*     */       
/* 225 */       if (p_176260_5_)
/*     */       {
/* 227 */         notifyNeighbors(worldIn, pos, enumfacing);
/*     */       }
/*     */     } 
/*     */     
/* 231 */     if (flag != k)
/*     */     {
/* 233 */       for (int n = 1; n < i; n++) {
/*     */         
/* 235 */         BlockPos blockpos2 = pos.offset(enumfacing, n);
/* 236 */         IBlockState iblockstate2 = aiblockstate[n];
/*     */         
/* 238 */         if (iblockstate2 != null && worldIn.getBlockState(blockpos2).getMaterial() != Material.AIR)
/*     */         {
/* 240 */           worldIn.setBlockState(blockpos2, iblockstate2.withProperty((IProperty)ATTACHED, Boolean.valueOf(k)), 3);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void randomTick(World worldIn, BlockPos pos, IBlockState state, Random random) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 255 */     calculateState(worldIn, pos, state, false, true, -1, (IBlockState)null);
/*     */   }
/*     */ 
/*     */   
/*     */   private void playSound(World worldIn, BlockPos pos, boolean p_180694_3_, boolean p_180694_4_, boolean p_180694_5_, boolean p_180694_6_) {
/* 260 */     if (p_180694_4_ && !p_180694_6_) {
/*     */       
/* 262 */       worldIn.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_CLICK_ON, SoundCategory.BLOCKS, 0.4F, 0.6F);
/*     */     }
/* 264 */     else if (!p_180694_4_ && p_180694_6_) {
/*     */       
/* 266 */       worldIn.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_CLICK_OFF, SoundCategory.BLOCKS, 0.4F, 0.5F);
/*     */     }
/* 268 */     else if (p_180694_3_ && !p_180694_5_) {
/*     */       
/* 270 */       worldIn.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_ATTACH, SoundCategory.BLOCKS, 0.4F, 0.7F);
/*     */     }
/* 272 */     else if (!p_180694_3_ && p_180694_5_) {
/*     */       
/* 274 */       worldIn.playSound(null, pos, SoundEvents.BLOCK_TRIPWIRE_DETACH, SoundCategory.BLOCKS, 0.4F, 1.2F / (worldIn.rand.nextFloat() * 0.2F + 0.9F));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing side) {
/* 280 */     worldIn.notifyNeighborsOfStateChange(pos, this, false);
/* 281 */     worldIn.notifyNeighborsOfStateChange(pos.offset(side.getOpposite()), this, false);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/* 286 */     if (!canPlaceBlockAt(worldIn, pos)) {
/*     */       
/* 288 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 289 */       worldIn.setBlockToAir(pos);
/* 290 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 294 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 303 */     boolean flag = ((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue();
/* 304 */     boolean flag1 = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/*     */     
/* 306 */     if (flag || flag1)
/*     */     {
/* 308 */       calculateState(worldIn, pos, state, true, false, -1, (IBlockState)null);
/*     */     }
/*     */     
/* 311 */     if (flag1) {
/*     */       
/* 313 */       worldIn.notifyNeighborsOfStateChange(pos, this, false);
/* 314 */       worldIn.notifyNeighborsOfStateChange(pos.offset(((EnumFacing)state.getValue((IProperty)FACING)).getOpposite()), this, false);
/*     */     } 
/*     */     
/* 317 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 322 */     return ((Boolean)blockState.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 327 */     if (!((Boolean)blockState.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 329 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 333 */     return (blockState.getValue((IProperty)FACING) == side) ? 15 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower(IBlockState state) {
/* 342 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 347 */     return BlockRenderLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 355 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta & 0x3)).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0))).withProperty((IProperty)ATTACHED, Boolean.valueOf(((meta & 0x4) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 363 */     int i = 0;
/* 364 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 366 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 368 */       i |= 0x8;
/*     */     }
/*     */     
/* 371 */     if (((Boolean)state.getValue((IProperty)ATTACHED)).booleanValue())
/*     */     {
/* 373 */       i |= 0x4;
/*     */     }
/*     */     
/* 376 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 385 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 394 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 399 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)POWERED, (IProperty)ATTACHED });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 404 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockTripWireHook.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */