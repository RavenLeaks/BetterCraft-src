/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.BlockWorldState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityList;
/*     */ import net.minecraft.entity.monster.EntityPigZombie;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.ItemMonsterPlacer;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPortal
/*     */   extends BlockBreakable {
/*  33 */   public static final PropertyEnum<EnumFacing.Axis> AXIS = PropertyEnum.create("axis", EnumFacing.Axis.class, (Enum[])new EnumFacing.Axis[] { EnumFacing.Axis.X, EnumFacing.Axis.Z });
/*  34 */   protected static final AxisAlignedBB X_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D);
/*  35 */   protected static final AxisAlignedBB Z_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D);
/*  36 */   protected static final AxisAlignedBB Y_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D);
/*     */ 
/*     */   
/*     */   public BlockPortal() {
/*  40 */     super(Material.PORTAL, false);
/*  41 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AXIS, (Comparable)EnumFacing.Axis.X));
/*  42 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  47 */     switch ((EnumFacing.Axis)state.getValue((IProperty)AXIS)) {
/*     */       
/*     */       case null:
/*  50 */         return X_AABB;
/*     */ 
/*     */       
/*     */       default:
/*  54 */         return Y_AABB;
/*     */       case Z:
/*     */         break;
/*  57 */     }  return Z_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  63 */     super.updateTick(worldIn, pos, state, rand);
/*     */     
/*  65 */     if (worldIn.provider.isSurfaceWorld() && worldIn.getGameRules().getBoolean("doMobSpawning") && rand.nextInt(2000) < worldIn.getDifficulty().getDifficultyId()) {
/*     */       
/*  67 */       int i = pos.getY();
/*     */       
/*     */       BlockPos blockpos;
/*  70 */       for (blockpos = pos; !worldIn.getBlockState(blockpos).isFullyOpaque() && blockpos.getY() > 0; blockpos = blockpos.down());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  75 */       if (i > 0 && !worldIn.getBlockState(blockpos.up()).isNormalCube()) {
/*     */         
/*  77 */         Entity entity = ItemMonsterPlacer.spawnCreature(worldIn, EntityList.func_191306_a(EntityPigZombie.class), blockpos.getX() + 0.5D, blockpos.getY() + 1.1D, blockpos.getZ() + 0.5D);
/*     */         
/*  79 */         if (entity != null)
/*     */         {
/*  81 */           entity.timeUntilPortal = entity.getPortalCooldown();
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  90 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int getMetaForAxis(EnumFacing.Axis axis) {
/*  95 */     if (axis == EnumFacing.Axis.X)
/*     */     {
/*  97 */       return 1;
/*     */     }
/*     */ 
/*     */     
/* 101 */     return (axis == EnumFacing.Axis.Z) ? 2 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 107 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean trySpawnPortal(World worldIn, BlockPos pos) {
/* 112 */     Size blockportal$size = new Size(worldIn, pos, EnumFacing.Axis.X);
/*     */     
/* 114 */     if (blockportal$size.isValid() && blockportal$size.portalBlockCount == 0) {
/*     */       
/* 116 */       blockportal$size.placePortalBlocks();
/* 117 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 121 */     Size blockportal$size1 = new Size(worldIn, pos, EnumFacing.Axis.Z);
/*     */     
/* 123 */     if (blockportal$size1.isValid() && blockportal$size1.portalBlockCount == 0) {
/*     */       
/* 125 */       blockportal$size1.placePortalBlocks();
/* 126 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 130 */     return false;
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
/* 142 */     EnumFacing.Axis enumfacing$axis = (EnumFacing.Axis)state.getValue((IProperty)AXIS);
/*     */     
/* 144 */     if (enumfacing$axis == EnumFacing.Axis.X) {
/*     */       
/* 146 */       Size blockportal$size = new Size(worldIn, pos, EnumFacing.Axis.X);
/*     */       
/* 148 */       if (!blockportal$size.isValid() || blockportal$size.portalBlockCount < blockportal$size.width * blockportal$size.height)
/*     */       {
/* 150 */         worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
/*     */       }
/*     */     }
/* 153 */     else if (enumfacing$axis == EnumFacing.Axis.Z) {
/*     */       
/* 155 */       Size blockportal$size1 = new Size(worldIn, pos, EnumFacing.Axis.Z);
/*     */       
/* 157 */       if (!blockportal$size1.isValid() || blockportal$size1.portalBlockCount < blockportal$size1.width * blockportal$size1.height)
/*     */       {
/* 159 */         worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 166 */     pos = pos.offset(side);
/* 167 */     EnumFacing.Axis enumfacing$axis = null;
/*     */     
/* 169 */     if (blockState.getBlock() == this) {
/*     */       
/* 171 */       enumfacing$axis = (EnumFacing.Axis)blockState.getValue((IProperty)AXIS);
/*     */       
/* 173 */       if (enumfacing$axis == null)
/*     */       {
/* 175 */         return false;
/*     */       }
/*     */       
/* 178 */       if (enumfacing$axis == EnumFacing.Axis.Z && side != EnumFacing.EAST && side != EnumFacing.WEST)
/*     */       {
/* 180 */         return false;
/*     */       }
/*     */       
/* 183 */       if (enumfacing$axis == EnumFacing.Axis.X && side != EnumFacing.SOUTH && side != EnumFacing.NORTH)
/*     */       {
/* 185 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 189 */     boolean flag = (blockAccess.getBlockState(pos.west()).getBlock() == this && blockAccess.getBlockState(pos.west(2)).getBlock() != this);
/* 190 */     boolean flag1 = (blockAccess.getBlockState(pos.east()).getBlock() == this && blockAccess.getBlockState(pos.east(2)).getBlock() != this);
/* 191 */     boolean flag2 = (blockAccess.getBlockState(pos.north()).getBlock() == this && blockAccess.getBlockState(pos.north(2)).getBlock() != this);
/* 192 */     boolean flag3 = (blockAccess.getBlockState(pos.south()).getBlock() == this && blockAccess.getBlockState(pos.south(2)).getBlock() != this);
/* 193 */     boolean flag4 = !(!flag && !flag1 && enumfacing$axis != EnumFacing.Axis.X);
/* 194 */     boolean flag5 = !(!flag2 && !flag3 && enumfacing$axis != EnumFacing.Axis.Z);
/*     */     
/* 196 */     if (flag4 && side == EnumFacing.WEST)
/*     */     {
/* 198 */       return true;
/*     */     }
/* 200 */     if (flag4 && side == EnumFacing.EAST)
/*     */     {
/* 202 */       return true;
/*     */     }
/* 204 */     if (flag5 && side == EnumFacing.NORTH)
/*     */     {
/* 206 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 210 */     return (flag5 && side == EnumFacing.SOUTH);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 219 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 224 */     return BlockRenderLayer.TRANSLUCENT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
/* 232 */     if (!entityIn.isRiding() && !entityIn.isBeingRidden() && entityIn.isNonBoss())
/*     */     {
/* 234 */       entityIn.setPortal(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/* 240 */     if (rand.nextInt(100) == 0)
/*     */     {
/* 242 */       worldIn.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, rand.nextFloat() * 0.4F + 0.8F, false);
/*     */     }
/*     */     
/* 245 */     for (int i = 0; i < 4; i++) {
/*     */       
/* 247 */       double d0 = (pos.getX() + rand.nextFloat());
/* 248 */       double d1 = (pos.getY() + rand.nextFloat());
/* 249 */       double d2 = (pos.getZ() + rand.nextFloat());
/* 250 */       double d3 = (rand.nextFloat() - 0.5D) * 0.5D;
/* 251 */       double d4 = (rand.nextFloat() - 0.5D) * 0.5D;
/* 252 */       double d5 = (rand.nextFloat() - 0.5D) * 0.5D;
/* 253 */       int j = rand.nextInt(2) * 2 - 1;
/*     */       
/* 255 */       if (worldIn.getBlockState(pos.west()).getBlock() != this && worldIn.getBlockState(pos.east()).getBlock() != this) {
/*     */         
/* 257 */         d0 = pos.getX() + 0.5D + 0.25D * j;
/* 258 */         d3 = (rand.nextFloat() * 2.0F * j);
/*     */       }
/*     */       else {
/*     */         
/* 262 */         d2 = pos.getZ() + 0.5D + 0.25D * j;
/* 263 */         d5 = (rand.nextFloat() * 2.0F * j);
/*     */       } 
/*     */       
/* 266 */       worldIn.spawnParticle(EnumParticleTypes.PORTAL, d0, d1, d2, d3, d4, d5, new int[0]);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 272 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 280 */     return getDefaultState().withProperty((IProperty)AXIS, ((meta & 0x3) == 2) ? (Comparable)EnumFacing.Axis.Z : (Comparable)EnumFacing.Axis.X);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 288 */     return getMetaForAxis((EnumFacing.Axis)state.getValue((IProperty)AXIS));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 297 */     switch (rot) {
/*     */       
/*     */       case CLOCKWISE_90:
/*     */       case COUNTERCLOCKWISE_90:
/* 301 */         switch ((EnumFacing.Axis)state.getValue((IProperty)AXIS)) {
/*     */           
/*     */           case null:
/* 304 */             return state.withProperty((IProperty)AXIS, (Comparable)EnumFacing.Axis.Z);
/*     */           
/*     */           case Z:
/* 307 */             return state.withProperty((IProperty)AXIS, (Comparable)EnumFacing.Axis.X);
/*     */         } 
/*     */         
/* 310 */         return state;
/*     */     } 
/*     */ 
/*     */     
/* 314 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 320 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)AXIS });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPattern.PatternHelper createPatternHelper(World worldIn, BlockPos p_181089_2_) {
/* 325 */     EnumFacing.Axis enumfacing$axis = EnumFacing.Axis.Z;
/* 326 */     Size blockportal$size = new Size(worldIn, p_181089_2_, EnumFacing.Axis.X);
/* 327 */     LoadingCache<BlockPos, BlockWorldState> loadingcache = BlockPattern.createLoadingCache(worldIn, true);
/*     */     
/* 329 */     if (!blockportal$size.isValid()) {
/*     */       
/* 331 */       enumfacing$axis = EnumFacing.Axis.X;
/* 332 */       blockportal$size = new Size(worldIn, p_181089_2_, EnumFacing.Axis.Z);
/*     */     } 
/*     */     
/* 335 */     if (!blockportal$size.isValid())
/*     */     {
/* 337 */       return new BlockPattern.PatternHelper(p_181089_2_, EnumFacing.NORTH, EnumFacing.UP, loadingcache, 1, 1, 1);
/*     */     }
/*     */ 
/*     */     
/* 341 */     int[] aint = new int[(EnumFacing.AxisDirection.values()).length];
/* 342 */     EnumFacing enumfacing = blockportal$size.rightDir.rotateYCCW();
/* 343 */     BlockPos blockpos = blockportal$size.bottomLeft.up(blockportal$size.getHeight() - 1); byte b; int i;
/*     */     EnumFacing.AxisDirection[] arrayOfAxisDirection1;
/* 345 */     for (i = (arrayOfAxisDirection1 = EnumFacing.AxisDirection.values()).length, b = 0; b < i; ) { EnumFacing.AxisDirection enumfacing$axisdirection = arrayOfAxisDirection1[b];
/*     */       
/* 347 */       BlockPattern.PatternHelper blockpattern$patternhelper = new BlockPattern.PatternHelper((enumfacing.getAxisDirection() == enumfacing$axisdirection) ? blockpos : blockpos.offset(blockportal$size.rightDir, blockportal$size.getWidth() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.getWidth(), blockportal$size.getHeight(), 1);
/*     */       
/* 349 */       for (int k = 0; k < blockportal$size.getWidth(); k++) {
/*     */         
/* 351 */         for (int m = 0; m < blockportal$size.getHeight(); m++) {
/*     */           
/* 353 */           BlockWorldState blockworldstate = blockpattern$patternhelper.translateOffset(k, m, 1);
/*     */           
/* 355 */           if (blockworldstate.getBlockState() != null && blockworldstate.getBlockState().getMaterial() != Material.AIR)
/*     */           {
/* 357 */             aint[enumfacing$axisdirection.ordinal()] = aint[enumfacing$axisdirection.ordinal()] + 1;
/*     */           }
/*     */         } 
/*     */       } 
/*     */       b++; }
/*     */     
/* 363 */     EnumFacing.AxisDirection enumfacing$axisdirection1 = EnumFacing.AxisDirection.POSITIVE;
/*     */     EnumFacing.AxisDirection[] arrayOfAxisDirection2;
/* 365 */     for (int j = (arrayOfAxisDirection2 = EnumFacing.AxisDirection.values()).length; i < j; ) { EnumFacing.AxisDirection enumfacing$axisdirection2 = arrayOfAxisDirection2[i];
/*     */       
/* 367 */       if (aint[enumfacing$axisdirection2.ordinal()] < aint[enumfacing$axisdirection1.ordinal()])
/*     */       {
/* 369 */         enumfacing$axisdirection1 = enumfacing$axisdirection2;
/*     */       }
/*     */       i++; }
/*     */     
/* 373 */     return new BlockPattern.PatternHelper((enumfacing.getAxisDirection() == enumfacing$axisdirection1) ? blockpos : blockpos.offset(blockportal$size.rightDir, blockportal$size.getWidth() - 1), EnumFacing.getFacingFromAxis(enumfacing$axisdirection1, enumfacing$axis), EnumFacing.UP, loadingcache, blockportal$size.getWidth(), blockportal$size.getHeight(), 1);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 379 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ 
/*     */   
/*     */   public static class Size
/*     */   {
/*     */     private final World world;
/*     */     private final EnumFacing.Axis axis;
/*     */     private final EnumFacing rightDir;
/*     */     private final EnumFacing leftDir;
/*     */     private int portalBlockCount;
/*     */     private BlockPos bottomLeft;
/*     */     private int height;
/*     */     private int width;
/*     */     
/*     */     public Size(World worldIn, BlockPos p_i45694_2_, EnumFacing.Axis p_i45694_3_) {
/* 395 */       this.world = worldIn;
/* 396 */       this.axis = p_i45694_3_;
/*     */       
/* 398 */       if (p_i45694_3_ == EnumFacing.Axis.X) {
/*     */         
/* 400 */         this.leftDir = EnumFacing.EAST;
/* 401 */         this.rightDir = EnumFacing.WEST;
/*     */       }
/*     */       else {
/*     */         
/* 405 */         this.leftDir = EnumFacing.NORTH;
/* 406 */         this.rightDir = EnumFacing.SOUTH;
/*     */       } 
/*     */       
/* 409 */       for (BlockPos blockpos = p_i45694_2_; p_i45694_2_.getY() > blockpos.getY() - 21 && p_i45694_2_.getY() > 0 && isEmptyBlock(worldIn.getBlockState(p_i45694_2_.down()).getBlock()); p_i45694_2_ = p_i45694_2_.down());
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 414 */       int i = getDistanceUntilEdge(p_i45694_2_, this.leftDir) - 1;
/*     */       
/* 416 */       if (i >= 0) {
/*     */         
/* 418 */         this.bottomLeft = p_i45694_2_.offset(this.leftDir, i);
/* 419 */         this.width = getDistanceUntilEdge(this.bottomLeft, this.rightDir);
/*     */         
/* 421 */         if (this.width < 2 || this.width > 21) {
/*     */           
/* 423 */           this.bottomLeft = null;
/* 424 */           this.width = 0;
/*     */         } 
/*     */       } 
/*     */       
/* 428 */       if (this.bottomLeft != null)
/*     */       {
/* 430 */         this.height = calculatePortalHeight();
/*     */       }
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected int getDistanceUntilEdge(BlockPos p_180120_1_, EnumFacing p_180120_2_) {
/*     */       int i;
/* 438 */       for (i = 0; i < 22; i++) {
/*     */         
/* 440 */         BlockPos blockpos = p_180120_1_.offset(p_180120_2_, i);
/*     */         
/* 442 */         if (!isEmptyBlock(this.world.getBlockState(blockpos).getBlock()) || this.world.getBlockState(blockpos.down()).getBlock() != Blocks.OBSIDIAN) {
/*     */           break;
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 448 */       Block block = this.world.getBlockState(p_180120_1_.offset(p_180120_2_, i)).getBlock();
/* 449 */       return (block == Blocks.OBSIDIAN) ? i : 0;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getHeight() {
/* 454 */       return this.height;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getWidth() {
/* 459 */       return this.width;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected int calculatePortalHeight() {
/* 466 */       label38: for (this.height = 0; this.height < 21; this.height++) {
/*     */         
/* 468 */         for (int i = 0; i < this.width; i++) {
/*     */           
/* 470 */           BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i).up(this.height);
/* 471 */           Block block = this.world.getBlockState(blockpos).getBlock();
/*     */           
/* 473 */           if (!isEmptyBlock(block)) {
/*     */             break label38;
/*     */           }
/*     */ 
/*     */           
/* 478 */           if (block == Blocks.PORTAL)
/*     */           {
/* 480 */             this.portalBlockCount++;
/*     */           }
/*     */           
/* 483 */           if (i == 0) {
/*     */             
/* 485 */             block = this.world.getBlockState(blockpos.offset(this.leftDir)).getBlock();
/*     */             
/* 487 */             if (block != Blocks.OBSIDIAN)
/*     */             {
/*     */               break label38;
/*     */             }
/*     */           }
/* 492 */           else if (i == this.width - 1) {
/*     */             
/* 494 */             block = this.world.getBlockState(blockpos.offset(this.rightDir)).getBlock();
/*     */             
/* 496 */             if (block != Blocks.OBSIDIAN) {
/*     */               break label38;
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 504 */       for (int j = 0; j < this.width; j++) {
/*     */         
/* 506 */         if (this.world.getBlockState(this.bottomLeft.offset(this.rightDir, j).up(this.height)).getBlock() != Blocks.OBSIDIAN) {
/*     */           
/* 508 */           this.height = 0;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 513 */       if (this.height <= 21 && this.height >= 3)
/*     */       {
/* 515 */         return this.height;
/*     */       }
/*     */ 
/*     */       
/* 519 */       this.bottomLeft = null;
/* 520 */       this.width = 0;
/* 521 */       this.height = 0;
/* 522 */       return 0;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected boolean isEmptyBlock(Block blockIn) {
/* 528 */       return !(blockIn.blockMaterial != Material.AIR && blockIn != Blocks.FIRE && blockIn != Blocks.PORTAL);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isValid() {
/* 533 */       return (this.bottomLeft != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21);
/*     */     }
/*     */ 
/*     */     
/*     */     public void placePortalBlocks() {
/* 538 */       for (int i = 0; i < this.width; i++) {
/*     */         
/* 540 */         BlockPos blockpos = this.bottomLeft.offset(this.rightDir, i);
/*     */         
/* 542 */         for (int j = 0; j < this.height; j++)
/*     */         {
/* 544 */           this.world.setBlockState(blockpos.up(j), Blocks.PORTAL.getDefaultState().withProperty((IProperty)BlockPortal.AXIS, (Comparable)this.axis), 2);
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockPortal.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */