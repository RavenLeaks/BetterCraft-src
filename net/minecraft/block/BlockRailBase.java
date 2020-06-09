/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockRailBase
/*     */   extends Block {
/*  23 */   protected static final AxisAlignedBB FLAT_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D);
/*  24 */   protected static final AxisAlignedBB field_190959_b = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
/*     */   
/*     */   protected final boolean isPowered;
/*     */   
/*     */   public static boolean isRailBlock(World worldIn, BlockPos pos) {
/*  29 */     return isRailBlock(worldIn.getBlockState(pos));
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean isRailBlock(IBlockState state) {
/*  34 */     Block block = state.getBlock();
/*  35 */     return !(block != Blocks.RAIL && block != Blocks.GOLDEN_RAIL && block != Blocks.DETECTOR_RAIL && block != Blocks.ACTIVATOR_RAIL);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockRailBase(boolean isPowered) {
/*  40 */     super(Material.CIRCUITS);
/*  41 */     this.isPowered = isPowered;
/*  42 */     setCreativeTab(CreativeTabs.TRANSPORTATION);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  48 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  56 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  61 */     EnumRailDirection blockrailbase$enumraildirection = (state.getBlock() == this) ? (EnumRailDirection)state.getValue(getShapeProperty()) : null;
/*  62 */     return (blockrailbase$enumraildirection != null && blockrailbase$enumraildirection.isAscending()) ? field_190959_b : FLAT_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/*  67 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  72 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  77 */     return worldIn.getBlockState(pos.down()).isFullyOpaque();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  85 */     if (!worldIn.isRemote) {
/*     */       
/*  87 */       state = updateDir(worldIn, pos, state, true);
/*     */       
/*  89 */       if (this.isPowered)
/*     */       {
/*  91 */         state.neighborChanged(worldIn, pos, this, pos);
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
/* 103 */     if (!worldIn.isRemote) {
/*     */       
/* 105 */       EnumRailDirection blockrailbase$enumraildirection = (EnumRailDirection)state.getValue(getShapeProperty());
/* 106 */       boolean flag = false;
/*     */       
/* 108 */       if (!worldIn.getBlockState(pos.down()).isFullyOpaque())
/*     */       {
/* 110 */         flag = true;
/*     */       }
/*     */       
/* 113 */       if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_EAST && !worldIn.getBlockState(pos.east()).isFullyOpaque()) {
/*     */         
/* 115 */         flag = true;
/*     */       }
/* 117 */       else if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_WEST && !worldIn.getBlockState(pos.west()).isFullyOpaque()) {
/*     */         
/* 119 */         flag = true;
/*     */       }
/* 121 */       else if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_NORTH && !worldIn.getBlockState(pos.north()).isFullyOpaque()) {
/*     */         
/* 123 */         flag = true;
/*     */       }
/* 125 */       else if (blockrailbase$enumraildirection == EnumRailDirection.ASCENDING_SOUTH && !worldIn.getBlockState(pos.south()).isFullyOpaque()) {
/*     */         
/* 127 */         flag = true;
/*     */       } 
/*     */       
/* 130 */       if (flag && !worldIn.isAirBlock(pos)) {
/*     */         
/* 132 */         dropBlockAsItem(worldIn, pos, state, 0);
/* 133 */         worldIn.setBlockToAir(pos);
/*     */       }
/*     */       else {
/*     */         
/* 137 */         updateState(state, worldIn, pos, blockIn);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void updateState(IBlockState p_189541_1_, World p_189541_2_, BlockPos p_189541_3_, Block p_189541_4_) {}
/*     */ 
/*     */   
/*     */   protected IBlockState updateDir(World worldIn, BlockPos pos, IBlockState state, boolean p_176564_4_) {
/* 148 */     return worldIn.isRemote ? state : (new Rail(worldIn, pos, state)).place(worldIn.isBlockPowered(pos), p_176564_4_).getBlockState();
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumPushReaction getMobilityFlag(IBlockState state) {
/* 153 */     return EnumPushReaction.NORMAL;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 158 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 166 */     super.breakBlock(worldIn, pos, state);
/*     */     
/* 168 */     if (((EnumRailDirection)state.getValue(getShapeProperty())).isAscending())
/*     */     {
/* 170 */       worldIn.notifyNeighborsOfStateChange(pos.up(), this, false);
/*     */     }
/*     */     
/* 173 */     if (this.isPowered) {
/*     */       
/* 175 */       worldIn.notifyNeighborsOfStateChange(pos, this, false);
/* 176 */       worldIn.notifyNeighborsOfStateChange(pos.down(), this, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   public abstract IProperty<EnumRailDirection> getShapeProperty();
/*     */   
/*     */   public enum EnumRailDirection
/*     */     implements IStringSerializable {
/* 184 */     NORTH_SOUTH(0, "north_south"),
/* 185 */     EAST_WEST(1, "east_west"),
/* 186 */     ASCENDING_EAST(2, "ascending_east"),
/* 187 */     ASCENDING_WEST(3, "ascending_west"),
/* 188 */     ASCENDING_NORTH(4, "ascending_north"),
/* 189 */     ASCENDING_SOUTH(5, "ascending_south"),
/* 190 */     SOUTH_EAST(6, "south_east"),
/* 191 */     SOUTH_WEST(7, "south_west"),
/* 192 */     NORTH_WEST(8, "north_west"),
/* 193 */     NORTH_EAST(9, "north_east");
/*     */     
/* 195 */     private static final EnumRailDirection[] META_LOOKUP = new EnumRailDirection[(values()).length];
/*     */ 
/*     */     
/*     */     private final int meta;
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumRailDirection(int meta, String name) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isAscending() {
/*     */       return !(this != ASCENDING_NORTH && this != ASCENDING_EAST && this != ASCENDING_SOUTH && this != ASCENDING_WEST);
/*     */     }
/*     */ 
/*     */     
/*     */     public static EnumRailDirection byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length) {
/*     */         meta = 0;
/*     */       }
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */ 
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumRailDirection[] arrayOfEnumRailDirection;
/* 236 */       for (i = (arrayOfEnumRailDirection = values()).length, b = 0; b < i; ) { EnumRailDirection blockrailbase$enumraildirection = arrayOfEnumRailDirection[b];
/*     */         
/* 238 */         META_LOOKUP[blockrailbase$enumraildirection.getMetadata()] = blockrailbase$enumraildirection;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     } }
/*     */   
/*     */   public class Rail {
/*     */     private final World world;
/*     */     private final BlockPos pos;
/* 250 */     private final List<BlockPos> connectedRails = Lists.newArrayList();
/*     */     private final BlockRailBase block;
/*     */     
/*     */     public Rail(World worldIn, BlockPos pos, IBlockState state) {
/* 254 */       this.world = worldIn;
/* 255 */       this.pos = pos;
/* 256 */       this.state = state;
/* 257 */       this.block = (BlockRailBase)state.getBlock();
/* 258 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = (BlockRailBase.EnumRailDirection)state.getValue(this.block.getShapeProperty());
/* 259 */       this.isPowered = this.block.isPowered;
/* 260 */       updateConnectedRails(blockrailbase$enumraildirection);
/*     */     }
/*     */     private IBlockState state; private final boolean isPowered;
/*     */     
/*     */     public List<BlockPos> getConnectedRails() {
/* 265 */       return this.connectedRails;
/*     */     }
/*     */ 
/*     */     
/*     */     private void updateConnectedRails(BlockRailBase.EnumRailDirection railDirection) {
/* 270 */       this.connectedRails.clear();
/*     */       
/* 272 */       switch (railDirection) {
/*     */         
/*     */         case NORTH_SOUTH:
/* 275 */           this.connectedRails.add(this.pos.north());
/* 276 */           this.connectedRails.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case EAST_WEST:
/* 280 */           this.connectedRails.add(this.pos.west());
/* 281 */           this.connectedRails.add(this.pos.east());
/*     */           break;
/*     */         
/*     */         case null:
/* 285 */           this.connectedRails.add(this.pos.west());
/* 286 */           this.connectedRails.add(this.pos.east().up());
/*     */           break;
/*     */         
/*     */         case ASCENDING_WEST:
/* 290 */           this.connectedRails.add(this.pos.west().up());
/* 291 */           this.connectedRails.add(this.pos.east());
/*     */           break;
/*     */         
/*     */         case ASCENDING_NORTH:
/* 295 */           this.connectedRails.add(this.pos.north().up());
/* 296 */           this.connectedRails.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case ASCENDING_SOUTH:
/* 300 */           this.connectedRails.add(this.pos.north());
/* 301 */           this.connectedRails.add(this.pos.south().up());
/*     */           break;
/*     */         
/*     */         case SOUTH_EAST:
/* 305 */           this.connectedRails.add(this.pos.east());
/* 306 */           this.connectedRails.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case SOUTH_WEST:
/* 310 */           this.connectedRails.add(this.pos.west());
/* 311 */           this.connectedRails.add(this.pos.south());
/*     */           break;
/*     */         
/*     */         case NORTH_WEST:
/* 315 */           this.connectedRails.add(this.pos.west());
/* 316 */           this.connectedRails.add(this.pos.north());
/*     */           break;
/*     */         
/*     */         case NORTH_EAST:
/* 320 */           this.connectedRails.add(this.pos.east());
/* 321 */           this.connectedRails.add(this.pos.north());
/*     */           break;
/*     */       } 
/*     */     }
/*     */     
/*     */     private void removeSoftConnections() {
/* 327 */       for (int i = 0; i < this.connectedRails.size(); i++) {
/*     */         
/* 329 */         Rail blockrailbase$rail = findRailAt(this.connectedRails.get(i));
/*     */         
/* 331 */         if (blockrailbase$rail != null && blockrailbase$rail.isConnectedToRail(this)) {
/*     */           
/* 333 */           this.connectedRails.set(i, blockrailbase$rail.pos);
/*     */         }
/*     */         else {
/*     */           
/* 337 */           this.connectedRails.remove(i--);
/*     */         } 
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean hasRailAt(BlockPos pos) {
/* 344 */       return !(!BlockRailBase.isRailBlock(this.world, pos) && !BlockRailBase.isRailBlock(this.world, pos.up()) && !BlockRailBase.isRailBlock(this.world, pos.down()));
/*     */     }
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     private Rail findRailAt(BlockPos pos) {
/* 350 */       IBlockState iblockstate = this.world.getBlockState(pos);
/*     */       
/* 352 */       if (BlockRailBase.isRailBlock(iblockstate)) {
/*     */         
/* 354 */         BlockRailBase.this.getClass(); return new Rail(this.world, pos, iblockstate);
/*     */       } 
/*     */ 
/*     */       
/* 358 */       BlockPos lvt_2_1_ = pos.up();
/* 359 */       iblockstate = this.world.getBlockState(lvt_2_1_);
/*     */       
/* 361 */       if (BlockRailBase.isRailBlock(iblockstate)) {
/*     */         
/* 363 */         BlockRailBase.this.getClass(); return new Rail(this.world, lvt_2_1_, iblockstate);
/*     */       } 
/*     */ 
/*     */       
/* 367 */       lvt_2_1_ = pos.down();
/* 368 */       iblockstate = this.world.getBlockState(lvt_2_1_);
/* 369 */       BlockRailBase.this.getClass(); return BlockRailBase.isRailBlock(iblockstate) ? new Rail(this.world, lvt_2_1_, iblockstate) : null;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private boolean isConnectedToRail(Rail rail) {
/* 376 */       return isConnectedTo(rail.pos);
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean isConnectedTo(BlockPos posIn) {
/* 381 */       for (int i = 0; i < this.connectedRails.size(); i++) {
/*     */         
/* 383 */         BlockPos blockpos = this.connectedRails.get(i);
/*     */         
/* 385 */         if (blockpos.getX() == posIn.getX() && blockpos.getZ() == posIn.getZ())
/*     */         {
/* 387 */           return true;
/*     */         }
/*     */       } 
/*     */       
/* 391 */       return false;
/*     */     }
/*     */ 
/*     */     
/*     */     protected int countAdjacentRails() {
/* 396 */       int i = 0;
/*     */       
/* 398 */       for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */         
/* 400 */         if (hasRailAt(this.pos.offset(enumfacing)))
/*     */         {
/* 402 */           i++;
/*     */         }
/*     */       } 
/*     */       
/* 406 */       return i;
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean canConnectTo(Rail rail) {
/* 411 */       return !(!isConnectedToRail(rail) && this.connectedRails.size() == 2);
/*     */     }
/*     */ 
/*     */     
/*     */     private void connectTo(Rail p_150645_1_) {
/* 416 */       this.connectedRails.add(p_150645_1_.pos);
/* 417 */       BlockPos blockpos = this.pos.north();
/* 418 */       BlockPos blockpos1 = this.pos.south();
/* 419 */       BlockPos blockpos2 = this.pos.west();
/* 420 */       BlockPos blockpos3 = this.pos.east();
/* 421 */       boolean flag = isConnectedTo(blockpos);
/* 422 */       boolean flag1 = isConnectedTo(blockpos1);
/* 423 */       boolean flag2 = isConnectedTo(blockpos2);
/* 424 */       boolean flag3 = isConnectedTo(blockpos3);
/* 425 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = null;
/*     */       
/* 427 */       if (flag || flag1)
/*     */       {
/* 429 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 432 */       if (flag2 || flag3)
/*     */       {
/* 434 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */       }
/*     */       
/* 437 */       if (!this.isPowered) {
/*     */         
/* 439 */         if (flag1 && flag3 && !flag && !flag2)
/*     */         {
/* 441 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */         }
/*     */         
/* 444 */         if (flag1 && flag2 && !flag && !flag3)
/*     */         {
/* 446 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */         }
/*     */         
/* 449 */         if (flag && flag2 && !flag1 && !flag3)
/*     */         {
/* 451 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */         }
/*     */         
/* 454 */         if (flag && flag3 && !flag1 && !flag2)
/*     */         {
/* 456 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */         }
/*     */       } 
/*     */       
/* 460 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
/*     */         
/* 462 */         if (BlockRailBase.isRailBlock(this.world, blockpos.up()))
/*     */         {
/* 464 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
/*     */         }
/*     */         
/* 467 */         if (BlockRailBase.isRailBlock(this.world, blockpos1.up()))
/*     */         {
/* 469 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
/*     */         }
/*     */       } 
/*     */       
/* 473 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
/*     */         
/* 475 */         if (BlockRailBase.isRailBlock(this.world, blockpos3.up()))
/*     */         {
/* 477 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
/*     */         }
/*     */         
/* 480 */         if (BlockRailBase.isRailBlock(this.world, blockpos2.up()))
/*     */         {
/* 482 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
/*     */         }
/*     */       } 
/*     */       
/* 486 */       if (blockrailbase$enumraildirection == null)
/*     */       {
/* 488 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 491 */       this.state = this.state.withProperty(this.block.getShapeProperty(), blockrailbase$enumraildirection);
/* 492 */       this.world.setBlockState(this.pos, this.state, 3);
/*     */     }
/*     */ 
/*     */     
/*     */     private boolean hasNeighborRail(BlockPos p_180361_1_) {
/* 497 */       Rail blockrailbase$rail = findRailAt(p_180361_1_);
/*     */       
/* 499 */       if (blockrailbase$rail == null)
/*     */       {
/* 501 */         return false;
/*     */       }
/*     */ 
/*     */       
/* 505 */       blockrailbase$rail.removeSoftConnections();
/* 506 */       return blockrailbase$rail.canConnectTo(this);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Rail place(boolean p_180364_1_, boolean p_180364_2_) {
/* 512 */       BlockPos blockpos = this.pos.north();
/* 513 */       BlockPos blockpos1 = this.pos.south();
/* 514 */       BlockPos blockpos2 = this.pos.west();
/* 515 */       BlockPos blockpos3 = this.pos.east();
/* 516 */       boolean flag = hasNeighborRail(blockpos);
/* 517 */       boolean flag1 = hasNeighborRail(blockpos1);
/* 518 */       boolean flag2 = hasNeighborRail(blockpos2);
/* 519 */       boolean flag3 = hasNeighborRail(blockpos3);
/* 520 */       BlockRailBase.EnumRailDirection blockrailbase$enumraildirection = null;
/*     */       
/* 522 */       if ((flag || flag1) && !flag2 && !flag3)
/*     */       {
/* 524 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 527 */       if ((flag2 || flag3) && !flag && !flag1)
/*     */       {
/* 529 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */       }
/*     */       
/* 532 */       if (!this.isPowered) {
/*     */         
/* 534 */         if (flag1 && flag3 && !flag && !flag2)
/*     */         {
/* 536 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */         }
/*     */         
/* 539 */         if (flag1 && flag2 && !flag && !flag3)
/*     */         {
/* 541 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */         }
/*     */         
/* 544 */         if (flag && flag2 && !flag1 && !flag3)
/*     */         {
/* 546 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */         }
/*     */         
/* 549 */         if (flag && flag3 && !flag1 && !flag2)
/*     */         {
/* 551 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */         }
/*     */       } 
/*     */       
/* 555 */       if (blockrailbase$enumraildirection == null) {
/*     */         
/* 557 */         if (flag || flag1)
/*     */         {
/* 559 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */         }
/*     */         
/* 562 */         if (flag2 || flag3)
/*     */         {
/* 564 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.EAST_WEST;
/*     */         }
/*     */         
/* 567 */         if (!this.isPowered)
/*     */         {
/* 569 */           if (p_180364_1_) {
/*     */             
/* 571 */             if (flag1 && flag3)
/*     */             {
/* 573 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */             }
/*     */             
/* 576 */             if (flag2 && flag1)
/*     */             {
/* 578 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */             }
/*     */             
/* 581 */             if (flag3 && flag)
/*     */             {
/* 583 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */             }
/*     */             
/* 586 */             if (flag && flag2)
/*     */             {
/* 588 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */             }
/*     */           }
/*     */           else {
/*     */             
/* 593 */             if (flag && flag2)
/*     */             {
/* 595 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_WEST;
/*     */             }
/*     */             
/* 598 */             if (flag3 && flag)
/*     */             {
/* 600 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_EAST;
/*     */             }
/*     */             
/* 603 */             if (flag2 && flag1)
/*     */             {
/* 605 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_WEST;
/*     */             }
/*     */             
/* 608 */             if (flag1 && flag3)
/*     */             {
/* 610 */               blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.SOUTH_EAST;
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */       
/* 616 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
/*     */         
/* 618 */         if (BlockRailBase.isRailBlock(this.world, blockpos.up()))
/*     */         {
/* 620 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
/*     */         }
/*     */         
/* 623 */         if (BlockRailBase.isRailBlock(this.world, blockpos1.up()))
/*     */         {
/* 625 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
/*     */         }
/*     */       } 
/*     */       
/* 629 */       if (blockrailbase$enumraildirection == BlockRailBase.EnumRailDirection.EAST_WEST) {
/*     */         
/* 631 */         if (BlockRailBase.isRailBlock(this.world, blockpos3.up()))
/*     */         {
/* 633 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
/*     */         }
/*     */         
/* 636 */         if (BlockRailBase.isRailBlock(this.world, blockpos2.up()))
/*     */         {
/* 638 */           blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
/*     */         }
/*     */       } 
/*     */       
/* 642 */       if (blockrailbase$enumraildirection == null)
/*     */       {
/* 644 */         blockrailbase$enumraildirection = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
/*     */       }
/*     */       
/* 647 */       updateConnectedRails(blockrailbase$enumraildirection);
/* 648 */       this.state = this.state.withProperty(this.block.getShapeProperty(), blockrailbase$enumraildirection);
/*     */       
/* 650 */       if (p_180364_2_ || this.world.getBlockState(this.pos) != this.state) {
/*     */         
/* 652 */         this.world.setBlockState(this.pos, this.state, 3);
/*     */         
/* 654 */         for (int i = 0; i < this.connectedRails.size(); i++) {
/*     */           
/* 656 */           Rail blockrailbase$rail = findRailAt(this.connectedRails.get(i));
/*     */           
/* 658 */           if (blockrailbase$rail != null) {
/*     */             
/* 660 */             blockrailbase$rail.removeSoftConnections();
/*     */             
/* 662 */             if (blockrailbase$rail.canConnectTo(this))
/*     */             {
/* 664 */               blockrailbase$rail.connectTo(this);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 670 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     public IBlockState getBlockState() {
/* 675 */       return this.state;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockRailBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */