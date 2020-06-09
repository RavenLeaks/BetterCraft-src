/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.projectile.EntityArrow;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockButton extends BlockDirectional {
/*  29 */   public static final PropertyBool POWERED = PropertyBool.create("powered");
/*  30 */   protected static final AxisAlignedBB AABB_DOWN_OFF = new AxisAlignedBB(0.3125D, 0.875D, 0.375D, 0.6875D, 1.0D, 0.625D);
/*  31 */   protected static final AxisAlignedBB AABB_UP_OFF = new AxisAlignedBB(0.3125D, 0.0D, 0.375D, 0.6875D, 0.125D, 0.625D);
/*  32 */   protected static final AxisAlignedBB AABB_NORTH_OFF = new AxisAlignedBB(0.3125D, 0.375D, 0.875D, 0.6875D, 0.625D, 1.0D);
/*  33 */   protected static final AxisAlignedBB AABB_SOUTH_OFF = new AxisAlignedBB(0.3125D, 0.375D, 0.0D, 0.6875D, 0.625D, 0.125D);
/*  34 */   protected static final AxisAlignedBB AABB_WEST_OFF = new AxisAlignedBB(0.875D, 0.375D, 0.3125D, 1.0D, 0.625D, 0.6875D);
/*  35 */   protected static final AxisAlignedBB AABB_EAST_OFF = new AxisAlignedBB(0.0D, 0.375D, 0.3125D, 0.125D, 0.625D, 0.6875D);
/*  36 */   protected static final AxisAlignedBB AABB_DOWN_ON = new AxisAlignedBB(0.3125D, 0.9375D, 0.375D, 0.6875D, 1.0D, 0.625D);
/*  37 */   protected static final AxisAlignedBB AABB_UP_ON = new AxisAlignedBB(0.3125D, 0.0D, 0.375D, 0.6875D, 0.0625D, 0.625D);
/*  38 */   protected static final AxisAlignedBB AABB_NORTH_ON = new AxisAlignedBB(0.3125D, 0.375D, 0.9375D, 0.6875D, 0.625D, 1.0D);
/*  39 */   protected static final AxisAlignedBB AABB_SOUTH_ON = new AxisAlignedBB(0.3125D, 0.375D, 0.0D, 0.6875D, 0.625D, 0.0625D);
/*  40 */   protected static final AxisAlignedBB AABB_WEST_ON = new AxisAlignedBB(0.9375D, 0.375D, 0.3125D, 1.0D, 0.625D, 0.6875D);
/*  41 */   protected static final AxisAlignedBB AABB_EAST_ON = new AxisAlignedBB(0.0D, 0.375D, 0.3125D, 0.0625D, 0.625D, 0.6875D);
/*     */   
/*     */   private final boolean wooden;
/*     */   
/*     */   protected BlockButton(boolean wooden) {
/*  46 */     super(Material.CIRCUITS);
/*  47 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/*  48 */     setTickRandomly(true);
/*  49 */     setCreativeTab(CreativeTabs.REDSTONE);
/*  50 */     this.wooden = wooden;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  56 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  64 */     return this.wooden ? 30 : 20;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  77 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/*  85 */     return canPlaceBlock(worldIn, pos, side);
/*     */   } public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/*  90 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/*  92 */       if (canPlaceBlock(worldIn, pos, enumfacing))
/*     */       {
/*  94 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/*  98 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean canPlaceBlock(World worldIn, BlockPos pos, EnumFacing direction) {
/* 106 */     BlockPos blockpos = pos.offset(direction.getOpposite());
/* 107 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 108 */     boolean flag = (iblockstate.func_193401_d((IBlockAccess)worldIn, blockpos, direction) == BlockFaceShape.SOLID);
/* 109 */     Block block = iblockstate.getBlock();
/*     */     
/* 111 */     if (direction == EnumFacing.UP)
/*     */     {
/* 113 */       return !(block != Blocks.HOPPER && (func_193384_b(block) || !flag));
/*     */     }
/*     */ 
/*     */     
/* 117 */     return (!func_193382_c(block) && flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 127 */     return canPlaceBlock(worldIn, pos, facing) ? getDefaultState().withProperty((IProperty)FACING, (Comparable)facing).withProperty((IProperty)POWERED, Boolean.valueOf(false)) : getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.DOWN).withProperty((IProperty)POWERED, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 137 */     if (checkForDrop(worldIn, pos, state) && !canPlaceBlock(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING))) {
/*     */       
/* 139 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 140 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/* 146 */     if (canPlaceBlockAt(worldIn, pos))
/*     */     {
/* 148 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 152 */     dropBlockAsItem(worldIn, pos, state, 0);
/* 153 */     worldIn.setBlockToAir(pos);
/* 154 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/* 160 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 161 */     boolean flag = ((Boolean)state.getValue((IProperty)POWERED)).booleanValue();
/*     */     
/* 163 */     switch (enumfacing) {
/*     */       
/*     */       case EAST:
/* 166 */         return flag ? AABB_EAST_ON : AABB_EAST_OFF;
/*     */       
/*     */       case WEST:
/* 169 */         return flag ? AABB_WEST_ON : AABB_WEST_OFF;
/*     */       
/*     */       case SOUTH:
/* 172 */         return flag ? AABB_SOUTH_ON : AABB_SOUTH_OFF;
/*     */ 
/*     */       
/*     */       default:
/* 176 */         return flag ? AABB_NORTH_ON : AABB_NORTH_OFF;
/*     */       
/*     */       case UP:
/* 179 */         return flag ? AABB_UP_ON : AABB_UP_OFF;
/*     */       case null:
/*     */         break;
/* 182 */     }  return flag ? AABB_DOWN_ON : AABB_DOWN_OFF;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 188 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 190 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 194 */     worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(true)), 3);
/* 195 */     worldIn.markBlockRangeForRenderUpdate(pos, pos);
/* 196 */     playClickSound(playerIn, worldIn, pos);
/* 197 */     notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/* 198 */     worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/* 199 */     return true;
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
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 212 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 214 */       notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/*     */     }
/*     */     
/* 217 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 222 */     return ((Boolean)blockState.getValue((IProperty)POWERED)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 227 */     if (!((Boolean)blockState.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 229 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 233 */     return (blockState.getValue((IProperty)FACING) == side) ? 15 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower(IBlockState state) {
/* 242 */     return true;
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
/* 254 */     if (!worldIn.isRemote)
/*     */     {
/* 256 */       if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */       {
/* 258 */         if (this.wooden) {
/*     */           
/* 260 */           checkPressed(state, worldIn, pos);
/*     */         }
/*     */         else {
/*     */           
/* 264 */           worldIn.setBlockState(pos, state.withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/* 265 */           notifyNeighbors(worldIn, pos, (EnumFacing)state.getValue((IProperty)FACING));
/* 266 */           playReleaseSound(worldIn, pos);
/* 267 */           worldIn.markBlockRangeForRenderUpdate(pos, pos);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 278 */     if (!worldIn.isRemote)
/*     */     {
/* 280 */       if (this.wooden)
/*     */       {
/* 282 */         if (!((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */         {
/* 284 */           checkPressed(state, worldIn, pos);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkPressed(IBlockState p_185616_1_, World p_185616_2_, BlockPos p_185616_3_) {
/* 292 */     List<? extends Entity> list = p_185616_2_.getEntitiesWithinAABB(EntityArrow.class, p_185616_1_.getBoundingBox((IBlockAccess)p_185616_2_, p_185616_3_).offset(p_185616_3_));
/* 293 */     boolean flag = !list.isEmpty();
/* 294 */     boolean flag1 = ((Boolean)p_185616_1_.getValue((IProperty)POWERED)).booleanValue();
/*     */     
/* 296 */     if (flag && !flag1) {
/*     */       
/* 298 */       p_185616_2_.setBlockState(p_185616_3_, p_185616_1_.withProperty((IProperty)POWERED, Boolean.valueOf(true)));
/* 299 */       notifyNeighbors(p_185616_2_, p_185616_3_, (EnumFacing)p_185616_1_.getValue((IProperty)FACING));
/* 300 */       p_185616_2_.markBlockRangeForRenderUpdate(p_185616_3_, p_185616_3_);
/* 301 */       playClickSound((EntityPlayer)null, p_185616_2_, p_185616_3_);
/*     */     } 
/*     */     
/* 304 */     if (!flag && flag1) {
/*     */       
/* 306 */       p_185616_2_.setBlockState(p_185616_3_, p_185616_1_.withProperty((IProperty)POWERED, Boolean.valueOf(false)));
/* 307 */       notifyNeighbors(p_185616_2_, p_185616_3_, (EnumFacing)p_185616_1_.getValue((IProperty)FACING));
/* 308 */       p_185616_2_.markBlockRangeForRenderUpdate(p_185616_3_, p_185616_3_);
/* 309 */       playReleaseSound(p_185616_2_, p_185616_3_);
/*     */     } 
/*     */     
/* 312 */     if (flag)
/*     */     {
/* 314 */       p_185616_2_.scheduleUpdate(new BlockPos((Vec3i)p_185616_3_), this, tickRate(p_185616_2_));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void notifyNeighbors(World worldIn, BlockPos pos, EnumFacing facing) {
/* 320 */     worldIn.notifyNeighborsOfStateChange(pos, this, false);
/* 321 */     worldIn.notifyNeighborsOfStateChange(pos.offset(facing.getOpposite()), this, false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*     */     EnumFacing enumfacing;
/* 331 */     switch (meta & 0x7) {
/*     */       
/*     */       case 0:
/* 334 */         enumfacing = EnumFacing.DOWN;
/*     */         break;
/*     */       
/*     */       case 1:
/* 338 */         enumfacing = EnumFacing.EAST;
/*     */         break;
/*     */       
/*     */       case 2:
/* 342 */         enumfacing = EnumFacing.WEST;
/*     */         break;
/*     */       
/*     */       case 3:
/* 346 */         enumfacing = EnumFacing.SOUTH;
/*     */         break;
/*     */       
/*     */       case 4:
/* 350 */         enumfacing = EnumFacing.NORTH;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 355 */         enumfacing = EnumFacing.UP;
/*     */         break;
/*     */     } 
/* 358 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing).withProperty((IProperty)POWERED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*     */     int i;
/* 368 */     switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */       
/*     */       case EAST:
/* 371 */         i = 1;
/*     */         break;
/*     */       
/*     */       case WEST:
/* 375 */         i = 2;
/*     */         break;
/*     */       
/*     */       case SOUTH:
/* 379 */         i = 3;
/*     */         break;
/*     */       
/*     */       case NORTH:
/* 383 */         i = 4;
/*     */         break;
/*     */ 
/*     */       
/*     */       default:
/* 388 */         i = 5;
/*     */         break;
/*     */       
/*     */       case null:
/* 392 */         i = 0;
/*     */         break;
/*     */     } 
/* 395 */     if (((Boolean)state.getValue((IProperty)POWERED)).booleanValue())
/*     */     {
/* 397 */       i |= 0x8;
/*     */     }
/*     */     
/* 400 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 409 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 418 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 423 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)POWERED });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 428 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */   
/*     */   protected abstract void playClickSound(EntityPlayer paramEntityPlayer, World paramWorld, BlockPos paramBlockPos);
/*     */   
/*     */   protected abstract void playReleaseSound(World paramWorld, BlockPos paramBlockPos);
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */