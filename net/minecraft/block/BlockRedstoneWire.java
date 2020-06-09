/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.EnumSet;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockRedstoneWire
/*     */   extends Block {
/*  35 */   public static final PropertyEnum<EnumAttachPosition> NORTH = PropertyEnum.create("north", EnumAttachPosition.class);
/*  36 */   public static final PropertyEnum<EnumAttachPosition> EAST = PropertyEnum.create("east", EnumAttachPosition.class);
/*  37 */   public static final PropertyEnum<EnumAttachPosition> SOUTH = PropertyEnum.create("south", EnumAttachPosition.class);
/*  38 */   public static final PropertyEnum<EnumAttachPosition> WEST = PropertyEnum.create("west", EnumAttachPosition.class);
/*  39 */   public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
/*  40 */   protected static final AxisAlignedBB[] REDSTONE_WIRE_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.8125D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.1875D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.1875D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 0.8125D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0625D, 1.0D) };
/*     */   private boolean canProvidePower = true;
/*  42 */   private final Set<BlockPos> blocksNeedingUpdate = Sets.newHashSet();
/*     */ 
/*     */   
/*     */   public BlockRedstoneWire() {
/*  46 */     super(Material.CIRCUITS);
/*  47 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)NORTH, EnumAttachPosition.NONE).withProperty((IProperty)EAST, EnumAttachPosition.NONE).withProperty((IProperty)SOUTH, EnumAttachPosition.NONE).withProperty((IProperty)WEST, EnumAttachPosition.NONE).withProperty((IProperty)POWER, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  52 */     return REDSTONE_WIRE_AABB[getAABBIndex(state.getActualState(source, pos))];
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getAABBIndex(IBlockState state) {
/*  57 */     int i = 0;
/*  58 */     boolean flag = (state.getValue((IProperty)NORTH) != EnumAttachPosition.NONE);
/*  59 */     boolean flag1 = (state.getValue((IProperty)EAST) != EnumAttachPosition.NONE);
/*  60 */     boolean flag2 = (state.getValue((IProperty)SOUTH) != EnumAttachPosition.NONE);
/*  61 */     boolean flag3 = (state.getValue((IProperty)WEST) != EnumAttachPosition.NONE);
/*     */     
/*  63 */     if (flag || (flag2 && !flag && !flag1 && !flag3))
/*     */     {
/*  65 */       i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
/*     */     }
/*     */     
/*  68 */     if (flag1 || (flag3 && !flag && !flag1 && !flag2))
/*     */     {
/*  70 */       i |= 1 << EnumFacing.EAST.getHorizontalIndex();
/*     */     }
/*     */     
/*  73 */     if (flag2 || (flag && !flag1 && !flag2 && !flag3))
/*     */     {
/*  75 */       i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
/*     */     }
/*     */     
/*  78 */     if (flag3 || (flag1 && !flag && !flag2 && !flag3))
/*     */     {
/*  80 */       i |= 1 << EnumFacing.WEST.getHorizontalIndex();
/*     */     }
/*     */     
/*  83 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  92 */     state = state.withProperty((IProperty)WEST, getAttachPosition(worldIn, pos, EnumFacing.WEST));
/*  93 */     state = state.withProperty((IProperty)EAST, getAttachPosition(worldIn, pos, EnumFacing.EAST));
/*  94 */     state = state.withProperty((IProperty)NORTH, getAttachPosition(worldIn, pos, EnumFacing.NORTH));
/*  95 */     state = state.withProperty((IProperty)SOUTH, getAttachPosition(worldIn, pos, EnumFacing.SOUTH));
/*  96 */     return state;
/*     */   }
/*     */ 
/*     */   
/*     */   private EnumAttachPosition getAttachPosition(IBlockAccess worldIn, BlockPos pos, EnumFacing direction) {
/* 101 */     BlockPos blockpos = pos.offset(direction);
/* 102 */     IBlockState iblockstate = worldIn.getBlockState(pos.offset(direction));
/*     */     
/* 104 */     if (!canConnectTo(worldIn.getBlockState(blockpos), direction) && (iblockstate.isNormalCube() || !canConnectUpwardsTo(worldIn.getBlockState(blockpos.down())))) {
/*     */       
/* 106 */       IBlockState iblockstate1 = worldIn.getBlockState(pos.up());
/*     */       
/* 108 */       if (!iblockstate1.isNormalCube()) {
/*     */         
/* 110 */         boolean flag = !(!worldIn.getBlockState(blockpos).isFullyOpaque() && worldIn.getBlockState(blockpos).getBlock() != Blocks.GLOWSTONE);
/*     */         
/* 112 */         if (flag && canConnectUpwardsTo(worldIn.getBlockState(blockpos.up()))) {
/*     */           
/* 114 */           if (iblockstate.isBlockNormalCube())
/*     */           {
/* 116 */             return EnumAttachPosition.UP;
/*     */           }
/*     */           
/* 119 */           return EnumAttachPosition.SIDE;
/*     */         } 
/*     */       } 
/*     */       
/* 123 */       return EnumAttachPosition.NONE;
/*     */     } 
/*     */ 
/*     */     
/* 127 */     return EnumAttachPosition.SIDE;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/* 134 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 142 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 147 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 152 */     return !(!worldIn.getBlockState(pos.down()).isFullyOpaque() && worldIn.getBlockState(pos.down()).getBlock() != Blocks.GLOWSTONE);
/*     */   }
/*     */ 
/*     */   
/*     */   private IBlockState updateSurroundingRedstone(World worldIn, BlockPos pos, IBlockState state) {
/* 157 */     state = calculateCurrentChanges(worldIn, pos, pos, state);
/* 158 */     List<BlockPos> list = Lists.newArrayList(this.blocksNeedingUpdate);
/* 159 */     this.blocksNeedingUpdate.clear();
/*     */     
/* 161 */     for (BlockPos blockpos : list)
/*     */     {
/* 163 */       worldIn.notifyNeighborsOfStateChange(blockpos, this, false);
/*     */     }
/*     */     
/* 166 */     return state;
/*     */   }
/*     */ 
/*     */   
/*     */   private IBlockState calculateCurrentChanges(World worldIn, BlockPos pos1, BlockPos pos2, IBlockState state) {
/* 171 */     IBlockState iblockstate = state;
/* 172 */     int i = ((Integer)state.getValue((IProperty)POWER)).intValue();
/* 173 */     int j = 0;
/* 174 */     j = getMaxCurrentStrength(worldIn, pos2, j);
/* 175 */     this.canProvidePower = false;
/* 176 */     int k = worldIn.isBlockIndirectlyGettingPowered(pos1);
/* 177 */     this.canProvidePower = true;
/*     */     
/* 179 */     if (k > 0 && k > j - 1)
/*     */     {
/* 181 */       j = k;
/*     */     }
/*     */     
/* 184 */     int l = 0;
/*     */     
/* 186 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 188 */       BlockPos blockpos = pos1.offset(enumfacing);
/* 189 */       boolean flag = !(blockpos.getX() == pos2.getX() && blockpos.getZ() == pos2.getZ());
/*     */       
/* 191 */       if (flag)
/*     */       {
/* 193 */         l = getMaxCurrentStrength(worldIn, blockpos, l);
/*     */       }
/*     */       
/* 196 */       if (worldIn.getBlockState(blockpos).isNormalCube() && !worldIn.getBlockState(pos1.up()).isNormalCube()) {
/*     */         
/* 198 */         if (flag && pos1.getY() >= pos2.getY())
/*     */         {
/* 200 */           l = getMaxCurrentStrength(worldIn, blockpos.up(), l); } 
/*     */         continue;
/*     */       } 
/* 203 */       if (!worldIn.getBlockState(blockpos).isNormalCube() && flag && pos1.getY() <= pos2.getY())
/*     */       {
/* 205 */         l = getMaxCurrentStrength(worldIn, blockpos.down(), l);
/*     */       }
/*     */     } 
/*     */     
/* 209 */     if (l > j) {
/*     */       
/* 211 */       j = l - 1;
/*     */     }
/* 213 */     else if (j > 0) {
/*     */       
/* 215 */       j--;
/*     */     }
/*     */     else {
/*     */       
/* 219 */       j = 0;
/*     */     } 
/*     */     
/* 222 */     if (k > j - 1)
/*     */     {
/* 224 */       j = k;
/*     */     }
/*     */     
/* 227 */     if (i != j) {
/*     */       
/* 229 */       state = state.withProperty((IProperty)POWER, Integer.valueOf(j));
/*     */       
/* 231 */       if (worldIn.getBlockState(pos1) == iblockstate)
/*     */       {
/* 233 */         worldIn.setBlockState(pos1, state, 2);
/*     */       }
/*     */       
/* 236 */       this.blocksNeedingUpdate.add(pos1); byte b; int m;
/*     */       EnumFacing[] arrayOfEnumFacing;
/* 238 */       for (m = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < m; ) { EnumFacing enumfacing1 = arrayOfEnumFacing[b];
/*     */         
/* 240 */         this.blocksNeedingUpdate.add(pos1.offset(enumfacing1));
/*     */         b++; }
/*     */     
/*     */     } 
/* 244 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void notifyWireNeighborsOfStateChange(World worldIn, BlockPos pos) {
/* 253 */     if (worldIn.getBlockState(pos).getBlock() == this) {
/*     */       
/* 255 */       worldIn.notifyNeighborsOfStateChange(pos, this, false); byte b; int i;
/*     */       EnumFacing[] arrayOfEnumFacing;
/* 257 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/* 259 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 269 */     if (!worldIn.isRemote) {
/*     */       
/* 271 */       updateSurroundingRedstone(worldIn, pos, state);
/*     */       
/* 273 */       for (EnumFacing enumfacing : EnumFacing.Plane.VERTICAL)
/*     */       {
/* 275 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
/*     */       }
/*     */       
/* 278 */       for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
/*     */       {
/* 280 */         notifyWireNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
/*     */       }
/*     */       
/* 283 */       for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL) {
/*     */         
/* 285 */         BlockPos blockpos = pos.offset(enumfacing2);
/*     */         
/* 287 */         if (worldIn.getBlockState(blockpos).isNormalCube()) {
/*     */           
/* 289 */           notifyWireNeighborsOfStateChange(worldIn, blockpos.up());
/*     */           
/*     */           continue;
/*     */         } 
/* 293 */         notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 304 */     super.breakBlock(worldIn, pos, state);
/*     */     
/* 306 */     if (!worldIn.isRemote) {
/*     */       byte b; int i; EnumFacing[] arrayOfEnumFacing;
/* 308 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/* 310 */         worldIn.notifyNeighborsOfStateChange(pos.offset(enumfacing), this, false);
/*     */         b++; }
/*     */       
/* 313 */       updateSurroundingRedstone(worldIn, pos, state);
/*     */       
/* 315 */       for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL)
/*     */       {
/* 317 */         notifyWireNeighborsOfStateChange(worldIn, pos.offset(enumfacing1));
/*     */       }
/*     */       
/* 320 */       for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL) {
/*     */         
/* 322 */         BlockPos blockpos = pos.offset(enumfacing2);
/*     */         
/* 324 */         if (worldIn.getBlockState(blockpos).isNormalCube()) {
/*     */           
/* 326 */           notifyWireNeighborsOfStateChange(worldIn, blockpos.up());
/*     */           
/*     */           continue;
/*     */         } 
/* 330 */         notifyWireNeighborsOfStateChange(worldIn, blockpos.down());
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private int getMaxCurrentStrength(World worldIn, BlockPos pos, int strength) {
/* 338 */     if (worldIn.getBlockState(pos).getBlock() != this)
/*     */     {
/* 340 */       return strength;
/*     */     }
/*     */ 
/*     */     
/* 344 */     int i = ((Integer)worldIn.getBlockState(pos).getValue((IProperty)POWER)).intValue();
/* 345 */     return (i > strength) ? i : strength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 356 */     if (!worldIn.isRemote)
/*     */     {
/* 358 */       if (canPlaceBlockAt(worldIn, pos)) {
/*     */         
/* 360 */         updateSurroundingRedstone(worldIn, pos, state);
/*     */       }
/*     */       else {
/*     */         
/* 364 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 365 */         worldIn.setBlockToAir(pos);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 375 */     return Items.REDSTONE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 380 */     return !this.canProvidePower ? 0 : blockState.getWeakPower(blockAccess, pos, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 385 */     if (!this.canProvidePower)
/*     */     {
/* 387 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 391 */     int i = ((Integer)blockState.getValue((IProperty)POWER)).intValue();
/*     */     
/* 393 */     if (i == 0)
/*     */     {
/* 395 */       return 0;
/*     */     }
/* 397 */     if (side == EnumFacing.UP)
/*     */     {
/* 399 */       return i;
/*     */     }
/*     */ 
/*     */     
/* 403 */     EnumSet<EnumFacing> enumset = EnumSet.noneOf(EnumFacing.class);
/*     */     
/* 405 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 407 */       if (isPowerSourceAt(blockAccess, pos, enumfacing))
/*     */       {
/* 409 */         enumset.add(enumfacing);
/*     */       }
/*     */     } 
/*     */     
/* 413 */     if (side.getAxis().isHorizontal() && enumset.isEmpty())
/*     */     {
/* 415 */       return i;
/*     */     }
/* 417 */     if (enumset.contains(side) && !enumset.contains(side.rotateYCCW()) && !enumset.contains(side.rotateY()))
/*     */     {
/* 419 */       return i;
/*     */     }
/*     */ 
/*     */     
/* 423 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isPowerSourceAt(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 431 */     BlockPos blockpos = pos.offset(side);
/* 432 */     IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 433 */     boolean flag = iblockstate.isNormalCube();
/* 434 */     boolean flag1 = worldIn.getBlockState(pos.up()).isNormalCube();
/*     */     
/* 436 */     if (!flag1 && flag && canConnectUpwardsTo(worldIn, blockpos.up()))
/*     */     {
/* 438 */       return true;
/*     */     }
/* 440 */     if (canConnectTo(iblockstate, side))
/*     */     {
/* 442 */       return true;
/*     */     }
/* 444 */     if (iblockstate.getBlock() == Blocks.POWERED_REPEATER && iblockstate.getValue((IProperty)BlockRedstoneDiode.FACING) == side)
/*     */     {
/* 446 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 450 */     return (!flag && canConnectUpwardsTo(worldIn, blockpos.down()));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean canConnectUpwardsTo(IBlockAccess worldIn, BlockPos pos) {
/* 456 */     return canConnectUpwardsTo(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean canConnectUpwardsTo(IBlockState state) {
/* 461 */     return canConnectTo(state, (EnumFacing)null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean canConnectTo(IBlockState blockState, @Nullable EnumFacing side) {
/* 466 */     Block block = blockState.getBlock();
/*     */     
/* 468 */     if (block == Blocks.REDSTONE_WIRE)
/*     */     {
/* 470 */       return true;
/*     */     }
/* 472 */     if (Blocks.UNPOWERED_REPEATER.isSameDiode(blockState)) {
/*     */       
/* 474 */       EnumFacing enumfacing = (EnumFacing)blockState.getValue((IProperty)BlockRedstoneRepeater.FACING);
/* 475 */       return !(enumfacing != side && enumfacing.getOpposite() != side);
/*     */     } 
/* 477 */     if (Blocks.field_190976_dk == blockState.getBlock())
/*     */     {
/* 479 */       return (side == blockState.getValue((IProperty)BlockObserver.FACING));
/*     */     }
/*     */ 
/*     */     
/* 483 */     return (blockState.canProvidePower() && side != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower(IBlockState state) {
/* 492 */     return this.canProvidePower;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int colorMultiplier(int p_176337_0_) {
/* 497 */     float f = p_176337_0_ / 15.0F;
/* 498 */     float f1 = f * 0.6F + 0.4F;
/*     */     
/* 500 */     if (p_176337_0_ == 0)
/*     */     {
/* 502 */       f1 = 0.3F;
/*     */     }
/*     */     
/* 505 */     float f2 = f * f * 0.7F - 0.5F;
/* 506 */     float f3 = f * f * 0.6F - 0.7F;
/*     */     
/* 508 */     if (f2 < 0.0F)
/*     */     {
/* 510 */       f2 = 0.0F;
/*     */     }
/*     */     
/* 513 */     if (f3 < 0.0F)
/*     */     {
/* 515 */       f3 = 0.0F;
/*     */     }
/*     */     
/* 518 */     int i = MathHelper.clamp((int)(f1 * 255.0F), 0, 255);
/* 519 */     int j = MathHelper.clamp((int)(f2 * 255.0F), 0, 255);
/* 520 */     int k = MathHelper.clamp((int)(f3 * 255.0F), 0, 255);
/* 521 */     return 0xFF000000 | i << 16 | j << 8 | k;
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/* 526 */     int i = ((Integer)stateIn.getValue((IProperty)POWER)).intValue();
/*     */     
/* 528 */     if (i != 0) {
/*     */       
/* 530 */       double d0 = pos.getX() + 0.5D + (rand.nextFloat() - 0.5D) * 0.2D;
/* 531 */       double d1 = (pos.getY() + 0.0625F);
/* 532 */       double d2 = pos.getZ() + 0.5D + (rand.nextFloat() - 0.5D) * 0.2D;
/* 533 */       float f = i / 15.0F;
/* 534 */       float f1 = f * 0.6F + 0.4F;
/* 535 */       float f2 = Math.max(0.0F, f * f * 0.7F - 0.5F);
/* 536 */       float f3 = Math.max(0.0F, f * f * 0.6F - 0.7F);
/* 537 */       worldIn.spawnParticle(EnumParticleTypes.REDSTONE, d0, d1, d2, f1, f2, f3, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 543 */     return new ItemStack(Items.REDSTONE);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 548 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 556 */     return getDefaultState().withProperty((IProperty)POWER, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 564 */     return ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 573 */     switch (rot) {
/*     */       
/*     */       case null:
/* 576 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)SOUTH)).withProperty((IProperty)EAST, state.getValue((IProperty)WEST)).withProperty((IProperty)SOUTH, state.getValue((IProperty)NORTH)).withProperty((IProperty)WEST, state.getValue((IProperty)EAST));
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 579 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)EAST)).withProperty((IProperty)EAST, state.getValue((IProperty)SOUTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)WEST)).withProperty((IProperty)WEST, state.getValue((IProperty)NORTH));
/*     */       
/*     */       case CLOCKWISE_90:
/* 582 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)WEST)).withProperty((IProperty)EAST, state.getValue((IProperty)NORTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)EAST)).withProperty((IProperty)WEST, state.getValue((IProperty)SOUTH));
/*     */     } 
/*     */     
/* 585 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 595 */     switch (mirrorIn) {
/*     */       
/*     */       case LEFT_RIGHT:
/* 598 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)SOUTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)NORTH));
/*     */       
/*     */       case null:
/* 601 */         return state.withProperty((IProperty)EAST, state.getValue((IProperty)WEST)).withProperty((IProperty)WEST, state.getValue((IProperty)EAST));
/*     */     } 
/*     */     
/* 604 */     return super.withMirror(state, mirrorIn);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 610 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)NORTH, (IProperty)EAST, (IProperty)SOUTH, (IProperty)WEST, (IProperty)POWER });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 615 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */   
/*     */   enum EnumAttachPosition
/*     */     implements IStringSerializable {
/* 620 */     UP("up"),
/* 621 */     SIDE("side"),
/* 622 */     NONE("none");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumAttachPosition(String name) {
/* 628 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 633 */       return getName();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 638 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockRedstoneWire.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */