/*     */ package net.minecraft.block;
/*     */ 
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.passive.EntityOcelot;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.inventory.Container;
/*     */ import net.minecraft.inventory.IInventory;
/*     */ import net.minecraft.inventory.InventoryHelper;
/*     */ import net.minecraft.inventory.InventoryLargeChest;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityChest;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.ILockableContainer;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockChest
/*     */   extends BlockContainer {
/*  37 */   public static final PropertyDirection FACING = BlockHorizontal.FACING;
/*  38 */   protected static final AxisAlignedBB NORTH_CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0D, 0.9375D, 0.875D, 0.9375D);
/*  39 */   protected static final AxisAlignedBB SOUTH_CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 1.0D);
/*  40 */   protected static final AxisAlignedBB WEST_CHEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);
/*  41 */   protected static final AxisAlignedBB EAST_CHEST_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 1.0D, 0.875D, 0.9375D);
/*  42 */   protected static final AxisAlignedBB NOT_CONNECTED_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.875D, 0.9375D);
/*     */ 
/*     */   
/*     */   public final Type chestType;
/*     */ 
/*     */   
/*     */   protected BlockChest(Type chestTypeIn) {
/*  49 */     super(Material.WOOD);
/*  50 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH));
/*  51 */     this.chestType = chestTypeIn;
/*  52 */     setCreativeTab((chestTypeIn == Type.TRAP) ? CreativeTabs.REDSTONE : CreativeTabs.DECORATIONS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_190946_v(IBlockState p_190946_1_) {
/*  70 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/*  79 */     return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  84 */     if (source.getBlockState(pos.north()).getBlock() == this)
/*     */     {
/*  86 */       return NORTH_CHEST_AABB;
/*     */     }
/*  88 */     if (source.getBlockState(pos.south()).getBlock() == this)
/*     */     {
/*  90 */       return SOUTH_CHEST_AABB;
/*     */     }
/*  92 */     if (source.getBlockState(pos.west()).getBlock() == this)
/*     */     {
/*  94 */       return WEST_CHEST_AABB;
/*     */     }
/*     */ 
/*     */     
/*  98 */     return (source.getBlockState(pos.east()).getBlock() == this) ? EAST_CHEST_AABB : NOT_CONNECTED_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 107 */     checkForSurroundingChests(worldIn, pos, state);
/*     */     
/* 109 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 111 */       BlockPos blockpos = pos.offset(enumfacing);
/* 112 */       IBlockState iblockstate = worldIn.getBlockState(blockpos);
/*     */       
/* 114 */       if (iblockstate.getBlock() == this)
/*     */       {
/* 116 */         checkForSurroundingChests(worldIn, blockpos, iblockstate);
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
/* 127 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 135 */     EnumFacing enumfacing = EnumFacing.getHorizontal(MathHelper.floor((placer.rotationYaw * 4.0F / 360.0F) + 0.5D) & 0x3).getOpposite();
/* 136 */     state = state.withProperty((IProperty)FACING, (Comparable)enumfacing);
/* 137 */     BlockPos blockpos = pos.north();
/* 138 */     BlockPos blockpos1 = pos.south();
/* 139 */     BlockPos blockpos2 = pos.west();
/* 140 */     BlockPos blockpos3 = pos.east();
/* 141 */     boolean flag = (this == worldIn.getBlockState(blockpos).getBlock());
/* 142 */     boolean flag1 = (this == worldIn.getBlockState(blockpos1).getBlock());
/* 143 */     boolean flag2 = (this == worldIn.getBlockState(blockpos2).getBlock());
/* 144 */     boolean flag3 = (this == worldIn.getBlockState(blockpos3).getBlock());
/*     */     
/* 146 */     if (!flag && !flag1 && !flag2 && !flag3) {
/*     */       
/* 148 */       worldIn.setBlockState(pos, state, 3);
/*     */     }
/* 150 */     else if (enumfacing.getAxis() != EnumFacing.Axis.X || (!flag && !flag1)) {
/*     */       
/* 152 */       if (enumfacing.getAxis() == EnumFacing.Axis.Z && (flag2 || flag3))
/*     */       {
/* 154 */         if (flag2) {
/*     */           
/* 156 */           worldIn.setBlockState(blockpos2, state, 3);
/*     */         }
/*     */         else {
/*     */           
/* 160 */           worldIn.setBlockState(blockpos3, state, 3);
/*     */         } 
/*     */         
/* 163 */         worldIn.setBlockState(pos, state, 3);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 168 */       if (flag) {
/*     */         
/* 170 */         worldIn.setBlockState(blockpos, state, 3);
/*     */       }
/*     */       else {
/*     */         
/* 174 */         worldIn.setBlockState(blockpos1, state, 3);
/*     */       } 
/*     */       
/* 177 */       worldIn.setBlockState(pos, state, 3);
/*     */     } 
/*     */     
/* 180 */     if (stack.hasDisplayName()) {
/*     */       
/* 182 */       TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */       
/* 184 */       if (tileentity instanceof TileEntityChest)
/*     */       {
/* 186 */         ((TileEntityChest)tileentity).func_190575_a(stack.getDisplayName());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public IBlockState checkForSurroundingChests(World worldIn, BlockPos pos, IBlockState state) {
/* 193 */     if (worldIn.isRemote)
/*     */     {
/* 195 */       return state;
/*     */     }
/*     */ 
/*     */     
/* 199 */     IBlockState iblockstate = worldIn.getBlockState(pos.north());
/* 200 */     IBlockState iblockstate1 = worldIn.getBlockState(pos.south());
/* 201 */     IBlockState iblockstate2 = worldIn.getBlockState(pos.west());
/* 202 */     IBlockState iblockstate3 = worldIn.getBlockState(pos.east());
/* 203 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 205 */     if (iblockstate.getBlock() != this && iblockstate1.getBlock() != this) {
/*     */       
/* 207 */       boolean flag = iblockstate.isFullBlock();
/* 208 */       boolean flag1 = iblockstate1.isFullBlock();
/*     */       
/* 210 */       if (iblockstate2.getBlock() == this || iblockstate3.getBlock() == this) {
/*     */         EnumFacing enumfacing2;
/* 212 */         BlockPos blockpos1 = (iblockstate2.getBlock() == this) ? pos.west() : pos.east();
/* 213 */         IBlockState iblockstate7 = worldIn.getBlockState(blockpos1.north());
/* 214 */         IBlockState iblockstate6 = worldIn.getBlockState(blockpos1.south());
/* 215 */         enumfacing = EnumFacing.SOUTH;
/*     */ 
/*     */         
/* 218 */         if (iblockstate2.getBlock() == this) {
/*     */           
/* 220 */           enumfacing2 = (EnumFacing)iblockstate2.getValue((IProperty)FACING);
/*     */         }
/*     */         else {
/*     */           
/* 224 */           enumfacing2 = (EnumFacing)iblockstate3.getValue((IProperty)FACING);
/*     */         } 
/*     */         
/* 227 */         if (enumfacing2 == EnumFacing.NORTH)
/*     */         {
/* 229 */           enumfacing = EnumFacing.NORTH;
/*     */         }
/*     */         
/* 232 */         if ((flag || iblockstate7.isFullBlock()) && !flag1 && !iblockstate6.isFullBlock())
/*     */         {
/* 234 */           enumfacing = EnumFacing.SOUTH;
/*     */         }
/*     */         
/* 237 */         if ((flag1 || iblockstate6.isFullBlock()) && !flag && !iblockstate7.isFullBlock())
/*     */         {
/* 239 */           enumfacing = EnumFacing.NORTH;
/*     */         }
/*     */       } 
/*     */     } else {
/*     */       EnumFacing enumfacing1;
/*     */       
/* 245 */       BlockPos blockpos = (iblockstate.getBlock() == this) ? pos.north() : pos.south();
/* 246 */       IBlockState iblockstate4 = worldIn.getBlockState(blockpos.west());
/* 247 */       IBlockState iblockstate5 = worldIn.getBlockState(blockpos.east());
/* 248 */       enumfacing = EnumFacing.EAST;
/*     */ 
/*     */       
/* 251 */       if (iblockstate.getBlock() == this) {
/*     */         
/* 253 */         enumfacing1 = (EnumFacing)iblockstate.getValue((IProperty)FACING);
/*     */       }
/*     */       else {
/*     */         
/* 257 */         enumfacing1 = (EnumFacing)iblockstate1.getValue((IProperty)FACING);
/*     */       } 
/*     */       
/* 260 */       if (enumfacing1 == EnumFacing.WEST)
/*     */       {
/* 262 */         enumfacing = EnumFacing.WEST;
/*     */       }
/*     */       
/* 265 */       if ((iblockstate2.isFullBlock() || iblockstate4.isFullBlock()) && !iblockstate3.isFullBlock() && !iblockstate5.isFullBlock())
/*     */       {
/* 267 */         enumfacing = EnumFacing.EAST;
/*     */       }
/*     */       
/* 270 */       if ((iblockstate3.isFullBlock() || iblockstate5.isFullBlock()) && !iblockstate2.isFullBlock() && !iblockstate4.isFullBlock())
/*     */       {
/* 272 */         enumfacing = EnumFacing.WEST;
/*     */       }
/*     */     } 
/*     */     
/* 276 */     state = state.withProperty((IProperty)FACING, (Comparable)enumfacing);
/* 277 */     worldIn.setBlockState(pos, state, 3);
/* 278 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState correctFacing(World worldIn, BlockPos pos, IBlockState state) {
/* 284 */     EnumFacing enumfacing = null;
/*     */     
/* 286 */     for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 288 */       IBlockState iblockstate = worldIn.getBlockState(pos.offset(enumfacing1));
/*     */       
/* 290 */       if (iblockstate.getBlock() == this)
/*     */       {
/* 292 */         return state;
/*     */       }
/*     */       
/* 295 */       if (iblockstate.isFullBlock()) {
/*     */         
/* 297 */         if (enumfacing != null) {
/*     */           
/* 299 */           enumfacing = null;
/*     */           
/*     */           break;
/*     */         } 
/* 303 */         enumfacing = enumfacing1;
/*     */       } 
/*     */     } 
/*     */     
/* 307 */     if (enumfacing != null)
/*     */     {
/* 309 */       return state.withProperty((IProperty)FACING, (Comparable)enumfacing.getOpposite());
/*     */     }
/*     */ 
/*     */     
/* 313 */     EnumFacing enumfacing2 = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 315 */     if (worldIn.getBlockState(pos.offset(enumfacing2)).isFullBlock())
/*     */     {
/* 317 */       enumfacing2 = enumfacing2.getOpposite();
/*     */     }
/*     */     
/* 320 */     if (worldIn.getBlockState(pos.offset(enumfacing2)).isFullBlock())
/*     */     {
/* 322 */       enumfacing2 = enumfacing2.rotateY();
/*     */     }
/*     */     
/* 325 */     if (worldIn.getBlockState(pos.offset(enumfacing2)).isFullBlock())
/*     */     {
/* 327 */       enumfacing2 = enumfacing2.getOpposite();
/*     */     }
/*     */     
/* 330 */     return state.withProperty((IProperty)FACING, (Comparable)enumfacing2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 336 */     int i = 0;
/* 337 */     BlockPos blockpos = pos.west();
/* 338 */     BlockPos blockpos1 = pos.east();
/* 339 */     BlockPos blockpos2 = pos.north();
/* 340 */     BlockPos blockpos3 = pos.south();
/*     */     
/* 342 */     if (worldIn.getBlockState(blockpos).getBlock() == this) {
/*     */       
/* 344 */       if (isDoubleChest(worldIn, blockpos))
/*     */       {
/* 346 */         return false;
/*     */       }
/*     */       
/* 349 */       i++;
/*     */     } 
/*     */     
/* 352 */     if (worldIn.getBlockState(blockpos1).getBlock() == this) {
/*     */       
/* 354 */       if (isDoubleChest(worldIn, blockpos1))
/*     */       {
/* 356 */         return false;
/*     */       }
/*     */       
/* 359 */       i++;
/*     */     } 
/*     */     
/* 362 */     if (worldIn.getBlockState(blockpos2).getBlock() == this) {
/*     */       
/* 364 */       if (isDoubleChest(worldIn, blockpos2))
/*     */       {
/* 366 */         return false;
/*     */       }
/*     */       
/* 369 */       i++;
/*     */     } 
/*     */     
/* 372 */     if (worldIn.getBlockState(blockpos3).getBlock() == this) {
/*     */       
/* 374 */       if (isDoubleChest(worldIn, blockpos3))
/*     */       {
/* 376 */         return false;
/*     */       }
/*     */       
/* 379 */       i++;
/*     */     } 
/*     */     
/* 382 */     return (i <= 1);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isDoubleChest(World worldIn, BlockPos pos) {
/* 387 */     if (worldIn.getBlockState(pos).getBlock() != this)
/*     */     {
/* 389 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 393 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 395 */       if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this)
/*     */       {
/* 397 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 401 */     return false;
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
/* 412 */     super.neighborChanged(state, worldIn, pos, blockIn, p_189540_5_);
/* 413 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 415 */     if (tileentity instanceof TileEntityChest)
/*     */     {
/* 417 */       tileentity.updateContainingBlockInfo();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 426 */     TileEntity tileentity = worldIn.getTileEntity(pos);
/*     */     
/* 428 */     if (tileentity instanceof IInventory) {
/*     */       
/* 430 */       InventoryHelper.dropInventoryItems(worldIn, pos, (IInventory)tileentity);
/* 431 */       worldIn.updateComparatorOutputLevel(pos, this);
/*     */     } 
/*     */     
/* 434 */     super.breakBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 439 */     if (worldIn.isRemote)
/*     */     {
/* 441 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 445 */     ILockableContainer ilockablecontainer = getLockableContainer(worldIn, pos);
/*     */     
/* 447 */     if (ilockablecontainer != null) {
/*     */       
/* 449 */       playerIn.displayGUIChest((IInventory)ilockablecontainer);
/*     */       
/* 451 */       if (this.chestType == Type.BASIC) {
/*     */         
/* 453 */         playerIn.addStat(StatList.CHEST_OPENED);
/*     */       }
/* 455 */       else if (this.chestType == Type.TRAP) {
/*     */         
/* 457 */         playerIn.addStat(StatList.TRAPPED_CHEST_TRIGGERED);
/*     */       } 
/*     */     } 
/*     */     
/* 461 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public ILockableContainer getLockableContainer(World worldIn, BlockPos pos) {
/* 468 */     return getContainer(worldIn, pos, false);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public ILockableContainer getContainer(World p_189418_1_, BlockPos p_189418_2_, boolean p_189418_3_) {
/*     */     InventoryLargeChest inventoryLargeChest;
/* 474 */     TileEntity tileentity = p_189418_1_.getTileEntity(p_189418_2_);
/*     */     
/* 476 */     if (!(tileentity instanceof TileEntityChest))
/*     */     {
/* 478 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 482 */     TileEntityChest tileEntityChest = (TileEntityChest)tileentity;
/*     */     
/* 484 */     if (!p_189418_3_ && isBlocked(p_189418_1_, p_189418_2_))
/*     */     {
/* 486 */       return null;
/*     */     }
/*     */ 
/*     */     
/* 490 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 492 */       BlockPos blockpos = p_189418_2_.offset(enumfacing);
/* 493 */       Block block = p_189418_1_.getBlockState(blockpos).getBlock();
/*     */       
/* 495 */       if (block == this) {
/*     */         
/* 497 */         if (isBlocked(p_189418_1_, blockpos))
/*     */         {
/* 499 */           return null;
/*     */         }
/*     */         
/* 502 */         TileEntity tileentity1 = p_189418_1_.getTileEntity(blockpos);
/*     */         
/* 504 */         if (tileentity1 instanceof TileEntityChest) {
/*     */           
/* 506 */           if (enumfacing != EnumFacing.WEST && enumfacing != EnumFacing.NORTH) {
/*     */             
/* 508 */             inventoryLargeChest = new InventoryLargeChest("container.chestDouble", (ILockableContainer)tileEntityChest, (ILockableContainer)tileentity1);
/*     */             
/*     */             continue;
/*     */           } 
/* 512 */           inventoryLargeChest = new InventoryLargeChest("container.chestDouble", (ILockableContainer)tileentity1, (ILockableContainer)inventoryLargeChest);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 518 */     return (ILockableContainer)inventoryLargeChest;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 528 */     return (TileEntity)new TileEntityChest();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower(IBlockState state) {
/* 536 */     return (this.chestType == Type.TRAP);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 541 */     if (!blockState.canProvidePower())
/*     */     {
/* 543 */       return 0;
/*     */     }
/*     */ 
/*     */     
/* 547 */     int i = 0;
/* 548 */     TileEntity tileentity = blockAccess.getTileEntity(pos);
/*     */     
/* 550 */     if (tileentity instanceof TileEntityChest)
/*     */     {
/* 552 */       i = ((TileEntityChest)tileentity).numPlayersUsing;
/*     */     }
/*     */     
/* 555 */     return MathHelper.clamp(i, 0, 15);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getStrongPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 561 */     return (side == EnumFacing.UP) ? blockState.getWeakPower(blockAccess, pos, side) : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isBlocked(World worldIn, BlockPos pos) {
/* 566 */     return !(!isBelowSolidBlock(worldIn, pos) && !isOcelotSittingOnChest(worldIn, pos));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isBelowSolidBlock(World worldIn, BlockPos pos) {
/* 571 */     return worldIn.getBlockState(pos.up()).isNormalCube();
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isOcelotSittingOnChest(World worldIn, BlockPos pos) {
/* 576 */     for (Entity entity : worldIn.getEntitiesWithinAABB(EntityOcelot.class, new AxisAlignedBB(pos.getX(), (pos.getY() + 1), pos.getZ(), (pos.getX() + 1), (pos.getY() + 2), (pos.getZ() + 1)))) {
/*     */       
/* 578 */       EntityOcelot entityocelot = (EntityOcelot)entity;
/*     */       
/* 580 */       if (entityocelot.isSitting())
/*     */       {
/* 582 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 586 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride(IBlockState state) {
/* 591 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
/* 596 */     return Container.calcRedstoneFromInventory((IInventory)getLockableContainer(worldIn, pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 604 */     EnumFacing enumfacing = EnumFacing.getFront(meta);
/*     */     
/* 606 */     if (enumfacing.getAxis() == EnumFacing.Axis.Y)
/*     */     {
/* 608 */       enumfacing = EnumFacing.NORTH;
/*     */     }
/*     */     
/* 611 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 619 */     return ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 628 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 637 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 642 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 647 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/* 652 */     BASIC,
/* 653 */     TRAP;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */