/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockVine
/*     */   extends Block {
/*  31 */   public static final PropertyBool UP = PropertyBool.create("up");
/*  32 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  33 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  34 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  35 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*  36 */   public static final PropertyBool[] ALL_FACES = new PropertyBool[] { UP, NORTH, SOUTH, WEST, EAST };
/*  37 */   protected static final AxisAlignedBB UP_AABB = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);
/*  38 */   protected static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.0625D, 1.0D, 1.0D);
/*  39 */   protected static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.9375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
/*  40 */   protected static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.0625D);
/*  41 */   protected static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.9375D, 1.0D, 1.0D, 1.0D);
/*     */ 
/*     */   
/*     */   public BlockVine() {
/*  45 */     super(Material.VINE);
/*  46 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)UP, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)));
/*  47 */     setTickRandomly(true);
/*  48 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  54 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  59 */     state = state.getActualState(source, pos);
/*  60 */     int i = 0;
/*  61 */     AxisAlignedBB axisalignedbb = FULL_BLOCK_AABB;
/*     */     
/*  63 */     if (((Boolean)state.getValue((IProperty)UP)).booleanValue()) {
/*     */       
/*  65 */       axisalignedbb = UP_AABB;
/*  66 */       i++;
/*     */     } 
/*     */     
/*  69 */     if (((Boolean)state.getValue((IProperty)NORTH)).booleanValue()) {
/*     */       
/*  71 */       axisalignedbb = NORTH_AABB;
/*  72 */       i++;
/*     */     } 
/*     */     
/*  75 */     if (((Boolean)state.getValue((IProperty)EAST)).booleanValue()) {
/*     */       
/*  77 */       axisalignedbb = EAST_AABB;
/*  78 */       i++;
/*     */     } 
/*     */     
/*  81 */     if (((Boolean)state.getValue((IProperty)SOUTH)).booleanValue()) {
/*     */       
/*  83 */       axisalignedbb = SOUTH_AABB;
/*  84 */       i++;
/*     */     } 
/*     */     
/*  87 */     if (((Boolean)state.getValue((IProperty)WEST)).booleanValue()) {
/*     */       
/*  89 */       axisalignedbb = WEST_AABB;
/*  90 */       i++;
/*     */     } 
/*     */     
/*  93 */     return (i == 1) ? axisalignedbb : FULL_BLOCK_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 102 */     BlockPos blockpos = pos.up();
/* 103 */     return state.withProperty((IProperty)UP, Boolean.valueOf((worldIn.getBlockState(blockpos).func_193401_d(worldIn, blockpos, EnumFacing.DOWN) == BlockFaceShape.SOLID)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 111 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 116 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side) {
/* 132 */     return (side != EnumFacing.DOWN && side != EnumFacing.UP && func_193395_a(worldIn, pos, side));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_193395_a(World p_193395_1_, BlockPos p_193395_2_, EnumFacing p_193395_3_) {
/* 137 */     Block block = p_193395_1_.getBlockState(p_193395_2_.up()).getBlock();
/* 138 */     return (func_193396_c(p_193395_1_, p_193395_2_.offset(p_193395_3_.getOpposite()), p_193395_3_) && (block == Blocks.AIR || block == Blocks.VINE || func_193396_c(p_193395_1_, p_193395_2_.up(), EnumFacing.UP)));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean func_193396_c(World p_193396_1_, BlockPos p_193396_2_, EnumFacing p_193396_3_) {
/* 143 */     IBlockState iblockstate = p_193396_1_.getBlockState(p_193396_2_);
/* 144 */     return (iblockstate.func_193401_d((IBlockAccess)p_193396_1_, p_193396_2_, p_193396_3_) == BlockFaceShape.SOLID && !func_193397_e(iblockstate.getBlock()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean func_193397_e(Block p_193397_0_) {
/* 149 */     return !(!(p_193397_0_ instanceof BlockShulkerBox) && p_193397_0_ != Blocks.BEACON && p_193397_0_ != Blocks.CAULDRON && p_193397_0_ != Blocks.GLASS && p_193397_0_ != Blocks.STAINED_GLASS && p_193397_0_ != Blocks.PISTON && p_193397_0_ != Blocks.STICKY_PISTON && p_193397_0_ != Blocks.PISTON_HEAD && p_193397_0_ != Blocks.TRAPDOOR);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean recheckGrownSides(World worldIn, BlockPos pos, IBlockState state) {
/* 154 */     IBlockState iblockstate = state;
/*     */     
/* 156 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 158 */       PropertyBool propertybool = getPropertyFor(enumfacing);
/*     */       
/* 160 */       if (((Boolean)state.getValue((IProperty)propertybool)).booleanValue() && !func_193395_a(worldIn, pos, enumfacing.getOpposite())) {
/*     */         
/* 162 */         IBlockState iblockstate1 = worldIn.getBlockState(pos.up());
/*     */         
/* 164 */         if (iblockstate1.getBlock() != this || !((Boolean)iblockstate1.getValue((IProperty)propertybool)).booleanValue())
/*     */         {
/* 166 */           state = state.withProperty((IProperty)propertybool, Boolean.valueOf(false));
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 171 */     if (getNumGrownFaces(state) == 0)
/*     */     {
/* 173 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 177 */     if (iblockstate != state)
/*     */     {
/* 179 */       worldIn.setBlockState(pos, state, 2);
/*     */     }
/*     */     
/* 182 */     return true;
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
/* 193 */     if (!worldIn.isRemote && !recheckGrownSides(worldIn, pos, state)) {
/*     */       
/* 195 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 196 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 202 */     if (!worldIn.isRemote)
/*     */     {
/* 204 */       if (worldIn.rand.nextInt(4) == 0) {
/*     */         
/* 206 */         int i = 4;
/* 207 */         int j = 5;
/* 208 */         boolean flag = false;
/*     */         
/*     */         int k;
/* 211 */         label100: for (k = -4; k <= 4; k++) {
/*     */           
/* 213 */           for (int l = -4; l <= 4; l++) {
/*     */             
/* 215 */             for (int i1 = -1; i1 <= 1; i1++) {
/*     */               
/* 217 */               if (worldIn.getBlockState(pos.add(k, i1, l)).getBlock() == this) {
/*     */                 
/* 219 */                 j--;
/*     */                 
/* 221 */                 if (j <= 0) {
/*     */                   
/* 223 */                   flag = true;
/*     */                   
/*     */                   break label100;
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         } 
/* 231 */         EnumFacing enumfacing1 = EnumFacing.random(rand);
/* 232 */         BlockPos blockpos2 = pos.up();
/*     */         
/* 234 */         if (enumfacing1 == EnumFacing.UP && pos.getY() < 255 && worldIn.isAirBlock(blockpos2)) {
/*     */           
/* 236 */           IBlockState iblockstate2 = state;
/*     */           
/* 238 */           for (EnumFacing enumfacing2 : EnumFacing.Plane.HORIZONTAL) {
/*     */             
/* 240 */             if (rand.nextBoolean() && func_193395_a(worldIn, blockpos2, enumfacing2.getOpposite())) {
/*     */               
/* 242 */               iblockstate2 = iblockstate2.withProperty((IProperty)getPropertyFor(enumfacing2), Boolean.valueOf(true));
/*     */               
/*     */               continue;
/*     */             } 
/* 246 */             iblockstate2 = iblockstate2.withProperty((IProperty)getPropertyFor(enumfacing2), Boolean.valueOf(false));
/*     */           } 
/*     */ 
/*     */           
/* 250 */           if (((Boolean)iblockstate2.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate2.getValue((IProperty)WEST)).booleanValue())
/*     */           {
/* 252 */             worldIn.setBlockState(blockpos2, iblockstate2, 2);
/*     */           }
/*     */         }
/* 255 */         else if (enumfacing1.getAxis().isHorizontal() && !((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing1))).booleanValue()) {
/*     */           
/* 257 */           if (!flag)
/*     */           {
/* 259 */             BlockPos blockpos4 = pos.offset(enumfacing1);
/* 260 */             IBlockState iblockstate3 = worldIn.getBlockState(blockpos4);
/* 261 */             Block block1 = iblockstate3.getBlock();
/*     */             
/* 263 */             if (block1.blockMaterial == Material.AIR) {
/*     */               
/* 265 */               EnumFacing enumfacing3 = enumfacing1.rotateY();
/* 266 */               EnumFacing enumfacing4 = enumfacing1.rotateYCCW();
/* 267 */               boolean flag1 = ((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing3))).booleanValue();
/* 268 */               boolean flag2 = ((Boolean)state.getValue((IProperty)getPropertyFor(enumfacing4))).booleanValue();
/* 269 */               BlockPos blockpos = blockpos4.offset(enumfacing3);
/* 270 */               BlockPos blockpos1 = blockpos4.offset(enumfacing4);
/*     */               
/* 272 */               if (flag1 && func_193395_a(worldIn, blockpos.offset(enumfacing3), enumfacing3))
/*     */               {
/* 274 */                 worldIn.setBlockState(blockpos4, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing3), Boolean.valueOf(true)), 2);
/*     */               }
/* 276 */               else if (flag2 && func_193395_a(worldIn, blockpos1.offset(enumfacing4), enumfacing4))
/*     */               {
/* 278 */                 worldIn.setBlockState(blockpos4, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing4), Boolean.valueOf(true)), 2);
/*     */               }
/* 280 */               else if (flag1 && worldIn.isAirBlock(blockpos) && func_193395_a(worldIn, blockpos, enumfacing1))
/*     */               {
/* 282 */                 worldIn.setBlockState(blockpos, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing1.getOpposite()), Boolean.valueOf(true)), 2);
/*     */               }
/* 284 */               else if (flag2 && worldIn.isAirBlock(blockpos1) && func_193395_a(worldIn, blockpos1, enumfacing1))
/*     */               {
/* 286 */                 worldIn.setBlockState(blockpos1, getDefaultState().withProperty((IProperty)getPropertyFor(enumfacing1.getOpposite()), Boolean.valueOf(true)), 2);
/*     */               }
/*     */             
/* 289 */             } else if (iblockstate3.func_193401_d((IBlockAccess)worldIn, blockpos4, enumfacing1) == BlockFaceShape.SOLID) {
/*     */               
/* 291 */               worldIn.setBlockState(pos, state.withProperty((IProperty)getPropertyFor(enumfacing1), Boolean.valueOf(true)), 2);
/*     */             }
/*     */           
/*     */           }
/*     */         
/*     */         }
/* 297 */         else if (pos.getY() > 1) {
/*     */           
/* 299 */           BlockPos blockpos3 = pos.down();
/* 300 */           IBlockState iblockstate = worldIn.getBlockState(blockpos3);
/* 301 */           Block block = iblockstate.getBlock();
/*     */           
/* 303 */           if (block.blockMaterial == Material.AIR) {
/*     */             
/* 305 */             IBlockState iblockstate1 = state;
/*     */             
/* 307 */             for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */               
/* 309 */               if (rand.nextBoolean())
/*     */               {
/* 311 */                 iblockstate1 = iblockstate1.withProperty((IProperty)getPropertyFor(enumfacing), Boolean.valueOf(false));
/*     */               }
/*     */             } 
/*     */             
/* 315 */             if (((Boolean)iblockstate1.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate1.getValue((IProperty)WEST)).booleanValue())
/*     */             {
/* 317 */               worldIn.setBlockState(blockpos3, iblockstate1, 2);
/*     */             }
/*     */           }
/* 320 */           else if (block == this) {
/*     */             
/* 322 */             IBlockState iblockstate4 = iblockstate;
/*     */             
/* 324 */             for (EnumFacing enumfacing5 : EnumFacing.Plane.HORIZONTAL) {
/*     */               
/* 326 */               PropertyBool propertybool = getPropertyFor(enumfacing5);
/*     */               
/* 328 */               if (rand.nextBoolean() && ((Boolean)state.getValue((IProperty)propertybool)).booleanValue())
/*     */               {
/* 330 */                 iblockstate4 = iblockstate4.withProperty((IProperty)propertybool, Boolean.valueOf(true));
/*     */               }
/*     */             } 
/*     */             
/* 334 */             if (((Boolean)iblockstate4.getValue((IProperty)NORTH)).booleanValue() || ((Boolean)iblockstate4.getValue((IProperty)EAST)).booleanValue() || ((Boolean)iblockstate4.getValue((IProperty)SOUTH)).booleanValue() || ((Boolean)iblockstate4.getValue((IProperty)WEST)).booleanValue())
/*     */             {
/* 336 */               worldIn.setBlockState(blockpos3, iblockstate4, 2);
/*     */             }
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
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/* 351 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)UP, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false));
/* 352 */     return facing.getAxis().isHorizontal() ? iblockstate.withProperty((IProperty)getPropertyFor(facing.getOpposite()), Boolean.valueOf(true)) : iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 360 */     return Items.field_190931_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 368 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
/* 373 */     if (!worldIn.isRemote && stack.getItem() == Items.SHEARS) {
/*     */       
/* 375 */       player.addStat(StatList.getBlockStats(this));
/* 376 */       spawnAsEntity(worldIn, pos, new ItemStack(Blocks.VINE, 1, 0));
/*     */     }
/*     */     else {
/*     */       
/* 380 */       super.harvestBlock(worldIn, player, pos, state, te, stack);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 386 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 394 */     return getDefaultState().withProperty((IProperty)SOUTH, Boolean.valueOf(((meta & 0x1) > 0))).withProperty((IProperty)WEST, Boolean.valueOf(((meta & 0x2) > 0))).withProperty((IProperty)NORTH, Boolean.valueOf(((meta & 0x4) > 0))).withProperty((IProperty)EAST, Boolean.valueOf(((meta & 0x8) > 0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 402 */     int i = 0;
/*     */     
/* 404 */     if (((Boolean)state.getValue((IProperty)SOUTH)).booleanValue())
/*     */     {
/* 406 */       i |= 0x1;
/*     */     }
/*     */     
/* 409 */     if (((Boolean)state.getValue((IProperty)WEST)).booleanValue())
/*     */     {
/* 411 */       i |= 0x2;
/*     */     }
/*     */     
/* 414 */     if (((Boolean)state.getValue((IProperty)NORTH)).booleanValue())
/*     */     {
/* 416 */       i |= 0x4;
/*     */     }
/*     */     
/* 419 */     if (((Boolean)state.getValue((IProperty)EAST)).booleanValue())
/*     */     {
/* 421 */       i |= 0x8;
/*     */     }
/*     */     
/* 424 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 429 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)UP, (IProperty)NORTH, (IProperty)EAST, (IProperty)SOUTH, (IProperty)WEST });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 438 */     switch (rot) {
/*     */       
/*     */       case null:
/* 441 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)SOUTH)).withProperty((IProperty)EAST, state.getValue((IProperty)WEST)).withProperty((IProperty)SOUTH, state.getValue((IProperty)NORTH)).withProperty((IProperty)WEST, state.getValue((IProperty)EAST));
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 444 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)EAST)).withProperty((IProperty)EAST, state.getValue((IProperty)SOUTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)WEST)).withProperty((IProperty)WEST, state.getValue((IProperty)NORTH));
/*     */       
/*     */       case CLOCKWISE_90:
/* 447 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)WEST)).withProperty((IProperty)EAST, state.getValue((IProperty)NORTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)EAST)).withProperty((IProperty)WEST, state.getValue((IProperty)SOUTH));
/*     */     } 
/*     */     
/* 450 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 460 */     switch (mirrorIn) {
/*     */       
/*     */       case LEFT_RIGHT:
/* 463 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)SOUTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)NORTH));
/*     */       
/*     */       case null:
/* 466 */         return state.withProperty((IProperty)EAST, state.getValue((IProperty)WEST)).withProperty((IProperty)WEST, state.getValue((IProperty)EAST));
/*     */     } 
/*     */     
/* 469 */     return super.withMirror(state, mirrorIn);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static PropertyBool getPropertyFor(EnumFacing side) {
/* 475 */     switch (side) {
/*     */       
/*     */       case UP:
/* 478 */         return UP;
/*     */       
/*     */       case NORTH:
/* 481 */         return NORTH;
/*     */       
/*     */       case SOUTH:
/* 484 */         return SOUTH;
/*     */       
/*     */       case WEST:
/* 487 */         return WEST;
/*     */       
/*     */       case EAST:
/* 490 */         return EAST;
/*     */     } 
/*     */     
/* 493 */     throw new IllegalArgumentException(side + " is an invalid choice");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static int getNumGrownFaces(IBlockState state) {
/* 499 */     int i = 0; byte b; int j;
/*     */     PropertyBool[] arrayOfPropertyBool;
/* 501 */     for (j = (arrayOfPropertyBool = ALL_FACES).length, b = 0; b < j; ) { PropertyBool propertybool = arrayOfPropertyBool[b];
/*     */       
/* 503 */       if (((Boolean)state.getValue((IProperty)propertybool)).booleanValue())
/*     */       {
/* 505 */         i++;
/*     */       }
/*     */       b++; }
/*     */     
/* 509 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 514 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockVine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */