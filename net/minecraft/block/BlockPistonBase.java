/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.EnumPushReaction;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockPistonStructureHelper;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityPiston;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPistonBase
/*     */   extends BlockDirectional
/*     */ {
/*  34 */   public static final PropertyBool EXTENDED = PropertyBool.create("extended");
/*  35 */   protected static final AxisAlignedBB PISTON_BASE_EAST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D);
/*  36 */   protected static final AxisAlignedBB PISTON_BASE_WEST_AABB = new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/*  37 */   protected static final AxisAlignedBB PISTON_BASE_SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D);
/*  38 */   protected static final AxisAlignedBB PISTON_BASE_NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D);
/*  39 */   protected static final AxisAlignedBB PISTON_BASE_UP_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D);
/*  40 */   protected static final AxisAlignedBB PISTON_BASE_DOWN_AABB = new AxisAlignedBB(0.0D, 0.25D, 0.0D, 1.0D, 1.0D, 1.0D);
/*     */ 
/*     */   
/*     */   private final boolean isSticky;
/*     */ 
/*     */   
/*     */   public BlockPistonBase(boolean isSticky) {
/*  47 */     super(Material.PISTON);
/*  48 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)EXTENDED, Boolean.valueOf(false)));
/*  49 */     this.isSticky = isSticky;
/*  50 */     setSoundType(SoundType.STONE);
/*  51 */     setHardness(0.5F);
/*  52 */     setCreativeTab(CreativeTabs.REDSTONE);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean causesSuffocation(IBlockState p_176214_1_) {
/*  57 */     return !((Boolean)p_176214_1_.getValue((IProperty)EXTENDED)).booleanValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  62 */     if (((Boolean)state.getValue((IProperty)EXTENDED)).booleanValue()) {
/*     */       
/*  64 */       switch ((EnumFacing)state.getValue((IProperty)FACING)) {
/*     */         
/*     */         case null:
/*  67 */           return PISTON_BASE_DOWN_AABB;
/*     */ 
/*     */         
/*     */         default:
/*  71 */           return PISTON_BASE_UP_AABB;
/*     */         
/*     */         case NORTH:
/*  74 */           return PISTON_BASE_NORTH_AABB;
/*     */         
/*     */         case SOUTH:
/*  77 */           return PISTON_BASE_SOUTH_AABB;
/*     */         
/*     */         case WEST:
/*  80 */           return PISTON_BASE_WEST_AABB;
/*     */         case EAST:
/*     */           break;
/*  83 */       }  return PISTON_BASE_EAST_AABB;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  88 */     return FULL_BLOCK_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullyOpaque(IBlockState state) {
/*  97 */     return !(((Boolean)state.getValue((IProperty)EXTENDED)).booleanValue() && state.getValue((IProperty)FACING) != EnumFacing.DOWN);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
/* 102 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, state.getBoundingBox((IBlockAccess)worldIn, pos));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
/* 118 */     worldIn.setBlockState(pos, state.withProperty((IProperty)FACING, (Comparable)EnumFacing.func_190914_a(pos, placer)), 2);
/*     */     
/* 120 */     if (!worldIn.isRemote)
/*     */     {
/* 122 */       checkForMove(worldIn, pos, state);
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
/* 133 */     if (!worldIn.isRemote)
/*     */     {
/* 135 */       checkForMove(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 144 */     if (!worldIn.isRemote && worldIn.getTileEntity(pos) == null)
/*     */     {
/* 146 */       checkForMove(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 156 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)EnumFacing.func_190914_a(pos, placer)).withProperty((IProperty)EXTENDED, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkForMove(World worldIn, BlockPos pos, IBlockState state) {
/* 161 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/* 162 */     boolean flag = shouldBeExtended(worldIn, pos, enumfacing);
/*     */     
/* 164 */     if (flag && !((Boolean)state.getValue((IProperty)EXTENDED)).booleanValue()) {
/*     */       
/* 166 */       if ((new BlockPistonStructureHelper(worldIn, pos, enumfacing, true)).canMove())
/*     */       {
/* 168 */         worldIn.addBlockEvent(pos, this, 0, enumfacing.getIndex());
/*     */       }
/*     */     }
/* 171 */     else if (!flag && ((Boolean)state.getValue((IProperty)EXTENDED)).booleanValue()) {
/*     */       
/* 173 */       worldIn.addBlockEvent(pos, this, 1, enumfacing.getIndex());
/*     */     } 
/*     */   } private boolean shouldBeExtended(World worldIn, BlockPos pos, EnumFacing facing) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing1;
/* 179 */     for (i = (arrayOfEnumFacing1 = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing1[b];
/*     */       
/* 181 */       if (enumfacing != facing && worldIn.isSidePowered(pos.offset(enumfacing), enumfacing))
/*     */       {
/* 183 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/* 187 */     if (worldIn.isSidePowered(pos, EnumFacing.DOWN))
/*     */     {
/* 189 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 193 */     BlockPos blockpos = pos.up();
/*     */     EnumFacing[] arrayOfEnumFacing2;
/* 195 */     for (int j = (arrayOfEnumFacing2 = EnumFacing.values()).length; i < j; ) { EnumFacing enumfacing1 = arrayOfEnumFacing2[i];
/*     */       
/* 197 */       if (enumfacing1 != EnumFacing.DOWN && worldIn.isSidePowered(blockpos.offset(enumfacing1), enumfacing1))
/*     */       {
/* 199 */         return true;
/*     */       }
/*     */       i++; }
/*     */     
/* 203 */     return false;
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
/*     */   public boolean eventReceived(IBlockState state, World worldIn, BlockPos pos, int id, int param) {
/* 215 */     EnumFacing enumfacing = (EnumFacing)state.getValue((IProperty)FACING);
/*     */     
/* 217 */     if (!worldIn.isRemote) {
/*     */       
/* 219 */       boolean flag = shouldBeExtended(worldIn, pos, enumfacing);
/*     */       
/* 221 */       if (flag && id == 1) {
/*     */         
/* 223 */         worldIn.setBlockState(pos, state.withProperty((IProperty)EXTENDED, Boolean.valueOf(true)), 2);
/* 224 */         return false;
/*     */       } 
/*     */       
/* 227 */       if (!flag && id == 0)
/*     */       {
/* 229 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 233 */     if (id == 0) {
/*     */       
/* 235 */       if (!doMove(worldIn, pos, enumfacing, true))
/*     */       {
/* 237 */         return false;
/*     */       }
/*     */       
/* 240 */       worldIn.setBlockState(pos, state.withProperty((IProperty)EXTENDED, Boolean.valueOf(true)), 3);
/* 241 */       worldIn.playSound(null, pos, SoundEvents.BLOCK_PISTON_EXTEND, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() * 0.25F + 0.6F);
/*     */     }
/* 243 */     else if (id == 1) {
/*     */       
/* 245 */       TileEntity tileentity1 = worldIn.getTileEntity(pos.offset(enumfacing));
/*     */       
/* 247 */       if (tileentity1 instanceof TileEntityPiston)
/*     */       {
/* 249 */         ((TileEntityPiston)tileentity1).clearPistonTileEntity();
/*     */       }
/*     */       
/* 252 */       worldIn.setBlockState(pos, Blocks.PISTON_EXTENSION.getDefaultState().withProperty((IProperty)BlockPistonMoving.FACING, (Comparable)enumfacing).withProperty((IProperty)BlockPistonMoving.TYPE, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT), 3);
/* 253 */       worldIn.setTileEntity(pos, BlockPistonMoving.createTilePiston(getStateFromMeta(param), enumfacing, false, true));
/*     */       
/* 255 */       if (this.isSticky) {
/*     */         
/* 257 */         BlockPos blockpos = pos.add(enumfacing.getFrontOffsetX() * 2, enumfacing.getFrontOffsetY() * 2, enumfacing.getFrontOffsetZ() * 2);
/* 258 */         IBlockState iblockstate = worldIn.getBlockState(blockpos);
/* 259 */         Block block = iblockstate.getBlock();
/* 260 */         boolean flag1 = false;
/*     */         
/* 262 */         if (block == Blocks.PISTON_EXTENSION) {
/*     */           
/* 264 */           TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*     */           
/* 266 */           if (tileentity instanceof TileEntityPiston) {
/*     */             
/* 268 */             TileEntityPiston tileentitypiston = (TileEntityPiston)tileentity;
/*     */             
/* 270 */             if (tileentitypiston.getFacing() == enumfacing && tileentitypiston.isExtending()) {
/*     */               
/* 272 */               tileentitypiston.clearPistonTileEntity();
/* 273 */               flag1 = true;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 278 */         if (!flag1 && iblockstate.getMaterial() != Material.AIR && canPush(iblockstate, worldIn, blockpos, enumfacing.getOpposite(), false, enumfacing) && (iblockstate.getMobilityFlag() == EnumPushReaction.NORMAL || block == Blocks.PISTON || block == Blocks.STICKY_PISTON))
/*     */         {
/* 280 */           doMove(worldIn, pos, enumfacing, false);
/*     */         }
/*     */       }
/*     */       else {
/*     */         
/* 285 */         worldIn.setBlockToAir(pos.offset(enumfacing));
/*     */       } 
/*     */       
/* 288 */       worldIn.playSound(null, pos, SoundEvents.BLOCK_PISTON_CONTRACT, SoundCategory.BLOCKS, 0.5F, worldIn.rand.nextFloat() * 0.15F + 0.6F);
/*     */     } 
/*     */     
/* 291 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 296 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static EnumFacing getFacing(int meta) {
/* 302 */     int i = meta & 0x7;
/* 303 */     return (i > 5) ? null : EnumFacing.getFront(i);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean canPush(IBlockState blockStateIn, World worldIn, BlockPos pos, EnumFacing facing, boolean destroyBlocks, EnumFacing p_185646_5_) {
/* 311 */     Block block = blockStateIn.getBlock();
/*     */     
/* 313 */     if (block == Blocks.OBSIDIAN)
/*     */     {
/* 315 */       return false;
/*     */     }
/* 317 */     if (!worldIn.getWorldBorder().contains(pos))
/*     */     {
/* 319 */       return false;
/*     */     }
/* 321 */     if (pos.getY() >= 0 && (facing != EnumFacing.DOWN || pos.getY() != 0)) {
/*     */       
/* 323 */       if (pos.getY() <= worldIn.getHeight() - 1 && (facing != EnumFacing.UP || pos.getY() != worldIn.getHeight() - 1)) {
/*     */         
/* 325 */         if (block != Blocks.PISTON && block != Blocks.STICKY_PISTON) {
/*     */           
/* 327 */           if (blockStateIn.getBlockHardness(worldIn, pos) == -1.0F)
/*     */           {
/* 329 */             return false;
/*     */           }
/*     */           
/* 332 */           switch (blockStateIn.getMobilityFlag()) {
/*     */             
/*     */             case null:
/* 335 */               return false;
/*     */             
/*     */             case DESTROY:
/* 338 */               return destroyBlocks;
/*     */             
/*     */             case PUSH_ONLY:
/* 341 */               return (facing == p_185646_5_);
/*     */           } 
/*     */         
/* 344 */         } else if (((Boolean)blockStateIn.getValue((IProperty)EXTENDED)).booleanValue()) {
/*     */           
/* 346 */           return false;
/*     */         } 
/*     */         
/* 349 */         return !block.hasTileEntity();
/*     */       } 
/*     */ 
/*     */       
/* 353 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 358 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean doMove(World worldIn, BlockPos pos, EnumFacing direction, boolean extending) {
/* 364 */     if (!extending)
/*     */     {
/* 366 */       worldIn.setBlockToAir(pos.offset(direction));
/*     */     }
/*     */     
/* 369 */     BlockPistonStructureHelper blockpistonstructurehelper = new BlockPistonStructureHelper(worldIn, pos, direction, extending);
/*     */     
/* 371 */     if (!blockpistonstructurehelper.canMove())
/*     */     {
/* 373 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 377 */     List<BlockPos> list = blockpistonstructurehelper.getBlocksToMove();
/* 378 */     List<IBlockState> list1 = Lists.newArrayList();
/*     */     
/* 380 */     for (int i = 0; i < list.size(); i++) {
/*     */       
/* 382 */       BlockPos blockpos = list.get(i);
/* 383 */       list1.add(worldIn.getBlockState(blockpos).getActualState((IBlockAccess)worldIn, blockpos));
/*     */     } 
/*     */     
/* 386 */     List<BlockPos> list2 = blockpistonstructurehelper.getBlocksToDestroy();
/* 387 */     int k = list.size() + list2.size();
/* 388 */     IBlockState[] aiblockstate = new IBlockState[k];
/* 389 */     EnumFacing enumfacing = extending ? direction : direction.getOpposite();
/*     */     
/* 391 */     for (int j = list2.size() - 1; j >= 0; j--) {
/*     */       
/* 393 */       BlockPos blockpos1 = list2.get(j);
/* 394 */       IBlockState iblockstate = worldIn.getBlockState(blockpos1);
/* 395 */       iblockstate.getBlock().dropBlockAsItem(worldIn, blockpos1, iblockstate, 0);
/* 396 */       worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 4);
/* 397 */       k--;
/* 398 */       aiblockstate[k] = iblockstate;
/*     */     } 
/*     */     
/* 401 */     for (int l = list.size() - 1; l >= 0; l--) {
/*     */       
/* 403 */       BlockPos blockpos3 = list.get(l);
/* 404 */       IBlockState iblockstate2 = worldIn.getBlockState(blockpos3);
/* 405 */       worldIn.setBlockState(blockpos3, Blocks.AIR.getDefaultState(), 2);
/* 406 */       blockpos3 = blockpos3.offset(enumfacing);
/* 407 */       worldIn.setBlockState(blockpos3, Blocks.PISTON_EXTENSION.getDefaultState().withProperty((IProperty)FACING, (Comparable)direction), 4);
/* 408 */       worldIn.setTileEntity(blockpos3, BlockPistonMoving.createTilePiston(list1.get(l), direction, extending, false));
/* 409 */       k--;
/* 410 */       aiblockstate[k] = iblockstate2;
/*     */     } 
/*     */     
/* 413 */     BlockPos blockpos2 = pos.offset(direction);
/*     */     
/* 415 */     if (extending) {
/*     */       
/* 417 */       BlockPistonExtension.EnumPistonType blockpistonextension$enumpistontype = this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT;
/* 418 */       IBlockState iblockstate3 = Blocks.PISTON_HEAD.getDefaultState().withProperty((IProperty)BlockPistonExtension.FACING, (Comparable)direction).withProperty((IProperty)BlockPistonExtension.TYPE, blockpistonextension$enumpistontype);
/* 419 */       IBlockState iblockstate1 = Blocks.PISTON_EXTENSION.getDefaultState().withProperty((IProperty)BlockPistonMoving.FACING, (Comparable)direction).withProperty((IProperty)BlockPistonMoving.TYPE, this.isSticky ? BlockPistonExtension.EnumPistonType.STICKY : BlockPistonExtension.EnumPistonType.DEFAULT);
/* 420 */       worldIn.setBlockState(blockpos2, iblockstate1, 4);
/* 421 */       worldIn.setTileEntity(blockpos2, BlockPistonMoving.createTilePiston(iblockstate3, direction, true, true));
/*     */     } 
/*     */     
/* 424 */     for (int i1 = list2.size() - 1; i1 >= 0; i1--)
/*     */     {
/* 426 */       worldIn.notifyNeighborsOfStateChange(list2.get(i1), aiblockstate[k++].getBlock(), false);
/*     */     }
/*     */     
/* 429 */     for (int j1 = list.size() - 1; j1 >= 0; j1--)
/*     */     {
/* 431 */       worldIn.notifyNeighborsOfStateChange(list.get(j1), aiblockstate[k++].getBlock(), false);
/*     */     }
/*     */     
/* 434 */     if (extending)
/*     */     {
/* 436 */       worldIn.notifyNeighborsOfStateChange(blockpos2, Blocks.PISTON_HEAD, false);
/*     */     }
/*     */     
/* 439 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 448 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)getFacing(meta)).withProperty((IProperty)EXTENDED, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 456 */     int i = 0;
/* 457 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getIndex();
/*     */     
/* 459 */     if (((Boolean)state.getValue((IProperty)EXTENDED)).booleanValue())
/*     */     {
/* 461 */       i |= 0x8;
/*     */     }
/*     */     
/* 464 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 473 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 482 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 487 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)EXTENDED });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 492 */     p_193383_2_ = getActualState(p_193383_2_, p_193383_1_, p_193383_3_);
/* 493 */     return (p_193383_2_.getValue((IProperty)FACING) != p_193383_4_.getOpposite() && ((Boolean)p_193383_2_.getValue((IProperty)EXTENDED)).booleanValue()) ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockPistonBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */