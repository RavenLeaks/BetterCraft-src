/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.init.SoundEvents;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.SoundCategory;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.util.math.Vec3d;
/*     */ import net.minecraft.util.math.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockLiquid
/*     */   extends Block {
/*  31 */   public static final PropertyInteger LEVEL = PropertyInteger.create("level", 0, 15);
/*     */ 
/*     */   
/*     */   protected BlockLiquid(Material materialIn) {
/*  35 */     super(materialIn);
/*  36 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)LEVEL, Integer.valueOf(0)));
/*  37 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  42 */     return FULL_BLOCK_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  48 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  53 */     return (this.blockMaterial != Material.LAVA);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static float getLiquidHeightPercent(int meta) {
/*  61 */     if (meta >= 8)
/*     */     {
/*  63 */       meta = 0;
/*     */     }
/*     */     
/*  66 */     return (meta + 1) / 9.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getDepth(IBlockState p_189542_1_) {
/*  71 */     return (p_189542_1_.getMaterial() == this.blockMaterial) ? ((Integer)p_189542_1_.getValue((IProperty)LEVEL)).intValue() : -1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getRenderedDepth(IBlockState p_189545_1_) {
/*  76 */     int i = getDepth(p_189545_1_);
/*  77 */     return (i >= 8) ? 0 : i;
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
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  90 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
/*  95 */     return (hitIfLiquid && ((Integer)state.getValue((IProperty)LEVEL)).intValue() == 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isBlockSolid(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
/* 103 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 104 */     Block block = iblockstate.getBlock();
/* 105 */     Material material = iblockstate.getMaterial();
/*     */     
/* 107 */     if (material == this.blockMaterial)
/*     */     {
/* 109 */       return false;
/*     */     }
/* 111 */     if (side == EnumFacing.UP)
/*     */     {
/* 113 */       return true;
/*     */     }
/* 115 */     if (material == Material.ICE)
/*     */     {
/* 117 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 121 */     boolean flag = !(!func_193382_c(block) && !(block instanceof BlockStairs));
/* 122 */     return (!flag && iblockstate.func_193401_d(worldIn, pos, side) == BlockFaceShape.SOLID);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 128 */     if (blockAccess.getBlockState(pos.offset(side)).getMaterial() == this.blockMaterial)
/*     */     {
/* 130 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 134 */     return (side == EnumFacing.UP) ? true : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean shouldRenderSides(IBlockAccess blockAccess, BlockPos pos) {
/* 140 */     for (int i = -1; i <= 1; i++) {
/*     */       
/* 142 */       for (int j = -1; j <= 1; j++) {
/*     */         
/* 144 */         IBlockState iblockstate = blockAccess.getBlockState(pos.add(i, 0, j));
/*     */         
/* 146 */         if (iblockstate.getMaterial() != this.blockMaterial && !iblockstate.isFullBlock())
/*     */         {
/* 148 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/* 162 */     return EnumBlockRenderType.LIQUID;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 170 */     return Items.field_190931_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 178 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Vec3d getFlow(IBlockAccess p_189543_1_, BlockPos p_189543_2_, IBlockState p_189543_3_) {
/* 183 */     double d0 = 0.0D;
/* 184 */     double d1 = 0.0D;
/* 185 */     double d2 = 0.0D;
/* 186 */     int i = getRenderedDepth(p_189543_3_);
/* 187 */     BlockPos.PooledMutableBlockPos blockpos$pooledmutableblockpos = BlockPos.PooledMutableBlockPos.retain();
/*     */     
/* 189 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 191 */       blockpos$pooledmutableblockpos.setPos((Vec3i)p_189543_2_).move(enumfacing);
/* 192 */       int j = getRenderedDepth(p_189543_1_.getBlockState((BlockPos)blockpos$pooledmutableblockpos));
/*     */       
/* 194 */       if (j < 0) {
/*     */         
/* 196 */         if (!p_189543_1_.getBlockState((BlockPos)blockpos$pooledmutableblockpos).getMaterial().blocksMovement()) {
/*     */           
/* 198 */           j = getRenderedDepth(p_189543_1_.getBlockState(blockpos$pooledmutableblockpos.down()));
/*     */           
/* 200 */           if (j >= 0) {
/*     */             
/* 202 */             int k = j - i - 8;
/* 203 */             d0 += (enumfacing.getFrontOffsetX() * k);
/* 204 */             d1 += (enumfacing.getFrontOffsetY() * k);
/* 205 */             d2 += (enumfacing.getFrontOffsetZ() * k);
/*     */           } 
/*     */         }  continue;
/*     */       } 
/* 209 */       if (j >= 0) {
/*     */         
/* 211 */         int l = j - i;
/* 212 */         d0 += (enumfacing.getFrontOffsetX() * l);
/* 213 */         d1 += (enumfacing.getFrontOffsetY() * l);
/* 214 */         d2 += (enumfacing.getFrontOffsetZ() * l);
/*     */       } 
/*     */     } 
/*     */     
/* 218 */     Vec3d vec3d = new Vec3d(d0, d1, d2);
/*     */     
/* 220 */     if (((Integer)p_189543_3_.getValue((IProperty)LEVEL)).intValue() >= 8)
/*     */     {
/* 222 */       for (EnumFacing enumfacing1 : EnumFacing.Plane.HORIZONTAL) {
/*     */         
/* 224 */         blockpos$pooledmutableblockpos.setPos((Vec3i)p_189543_2_).move(enumfacing1);
/*     */         
/* 226 */         if (isBlockSolid(p_189543_1_, (BlockPos)blockpos$pooledmutableblockpos, enumfacing1) || isBlockSolid(p_189543_1_, blockpos$pooledmutableblockpos.up(), enumfacing1)) {
/*     */           
/* 228 */           vec3d = vec3d.normalize().addVector(0.0D, -6.0D, 0.0D);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     }
/* 234 */     blockpos$pooledmutableblockpos.release();
/* 235 */     return vec3d.normalize();
/*     */   }
/*     */ 
/*     */   
/*     */   public Vec3d modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3d motion) {
/* 240 */     return motion.add(getFlow((IBlockAccess)worldIn, pos, worldIn.getBlockState(pos)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/* 248 */     if (this.blockMaterial == Material.WATER)
/*     */     {
/* 250 */       return 5;
/*     */     }
/* 252 */     if (this.blockMaterial == Material.LAVA)
/*     */     {
/* 254 */       return worldIn.provider.getHasNoSky() ? 10 : 30;
/*     */     }
/*     */ 
/*     */     
/* 258 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
/* 264 */     int i = source.getCombinedLight(pos, 0);
/* 265 */     int j = source.getCombinedLight(pos.up(), 0);
/* 266 */     int k = i & 0xFF;
/* 267 */     int l = j & 0xFF;
/* 268 */     int i1 = i >> 16 & 0xFF;
/* 269 */     int j1 = j >> 16 & 0xFF;
/* 270 */     return ((k > l) ? k : l) | ((i1 > j1) ? i1 : j1) << 16;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 275 */     return (this.blockMaterial == Material.WATER) ? BlockRenderLayer.TRANSLUCENT : BlockRenderLayer.SOLID;
/*     */   }
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/* 280 */     double d0 = pos.getX();
/* 281 */     double d1 = pos.getY();
/* 282 */     double d2 = pos.getZ();
/*     */     
/* 284 */     if (this.blockMaterial == Material.WATER) {
/*     */       
/* 286 */       int i = ((Integer)stateIn.getValue((IProperty)LEVEL)).intValue();
/*     */       
/* 288 */       if (i > 0 && i < 8) {
/*     */         
/* 290 */         if (rand.nextInt(64) == 0)
/*     */         {
/* 292 */           worldIn.playSound(d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, rand.nextFloat() * 0.25F + 0.75F, rand.nextFloat() + 0.5F, false);
/*     */         }
/*     */       }
/* 295 */       else if (rand.nextInt(10) == 0) {
/*     */         
/* 297 */         worldIn.spawnParticle(EnumParticleTypes.SUSPENDED, d0 + rand.nextFloat(), d1 + rand.nextFloat(), d2 + rand.nextFloat(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */       } 
/*     */     } 
/*     */     
/* 301 */     if (this.blockMaterial == Material.LAVA && worldIn.getBlockState(pos.up()).getMaterial() == Material.AIR && !worldIn.getBlockState(pos.up()).isOpaqueCube()) {
/*     */       
/* 303 */       if (rand.nextInt(100) == 0) {
/*     */         
/* 305 */         double d8 = d0 + rand.nextFloat();
/* 306 */         double d4 = d1 + (stateIn.getBoundingBox((IBlockAccess)worldIn, pos)).maxY;
/* 307 */         double d6 = d2 + rand.nextFloat();
/* 308 */         worldIn.spawnParticle(EnumParticleTypes.LAVA, d8, d4, d6, 0.0D, 0.0D, 0.0D, new int[0]);
/* 309 */         worldIn.playSound(d8, d4, d6, SoundEvents.BLOCK_LAVA_POP, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
/*     */       } 
/*     */       
/* 312 */       if (rand.nextInt(200) == 0)
/*     */       {
/* 314 */         worldIn.playSound(d0, d1, d2, SoundEvents.BLOCK_LAVA_AMBIENT, SoundCategory.BLOCKS, 0.2F + rand.nextFloat() * 0.2F, 0.9F + rand.nextFloat() * 0.15F, false);
/*     */       }
/*     */     } 
/*     */     
/* 318 */     if (rand.nextInt(10) == 0 && worldIn.getBlockState(pos.down()).isFullyOpaque()) {
/*     */       
/* 320 */       Material material = worldIn.getBlockState(pos.down(2)).getMaterial();
/*     */       
/* 322 */       if (!material.blocksMovement() && !material.isLiquid()) {
/*     */         
/* 324 */         double d3 = d0 + rand.nextFloat();
/* 325 */         double d5 = d1 - 1.05D;
/* 326 */         double d7 = d2 + rand.nextFloat();
/*     */         
/* 328 */         if (this.blockMaterial == Material.WATER) {
/*     */           
/* 330 */           worldIn.spawnParticle(EnumParticleTypes.DRIP_WATER, d3, d5, d7, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         }
/*     */         else {
/*     */           
/* 334 */           worldIn.spawnParticle(EnumParticleTypes.DRIP_LAVA, d3, d5, d7, 0.0D, 0.0D, 0.0D, new int[0]);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static float getSlopeAngle(IBlockAccess p_189544_0_, BlockPos p_189544_1_, Material p_189544_2_, IBlockState p_189544_3_) {
/* 342 */     Vec3d vec3d = getFlowingBlock(p_189544_2_).getFlow(p_189544_0_, p_189544_1_, p_189544_3_);
/* 343 */     return (vec3d.xCoord == 0.0D && vec3d.zCoord == 0.0D) ? -1000.0F : ((float)MathHelper.atan2(vec3d.zCoord, vec3d.xCoord) - 1.5707964F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 351 */     checkForMixing(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 361 */     checkForMixing(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkForMixing(World worldIn, BlockPos pos, IBlockState state) {
/* 366 */     if (this.blockMaterial == Material.LAVA) {
/*     */       
/* 368 */       boolean flag = false; byte b; int i;
/*     */       EnumFacing[] arrayOfEnumFacing;
/* 370 */       for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */         
/* 372 */         if (enumfacing != EnumFacing.DOWN && worldIn.getBlockState(pos.offset(enumfacing)).getMaterial() == Material.WATER) {
/*     */           
/* 374 */           flag = true;
/*     */           break;
/*     */         } 
/*     */         b++; }
/*     */       
/* 379 */       if (flag) {
/*     */         
/* 381 */         Integer integer = (Integer)state.getValue((IProperty)LEVEL);
/*     */         
/* 383 */         if (integer.intValue() == 0) {
/*     */           
/* 385 */           worldIn.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState());
/* 386 */           triggerMixEffects(worldIn, pos);
/* 387 */           return true;
/*     */         } 
/*     */         
/* 390 */         if (integer.intValue() <= 4) {
/*     */           
/* 392 */           worldIn.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState());
/* 393 */           triggerMixEffects(worldIn, pos);
/* 394 */           return true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 399 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void triggerMixEffects(World worldIn, BlockPos pos) {
/* 404 */     double d0 = pos.getX();
/* 405 */     double d1 = pos.getY();
/* 406 */     double d2 = pos.getZ();
/* 407 */     worldIn.playSound(null, pos, SoundEvents.BLOCK_LAVA_EXTINGUISH, SoundCategory.BLOCKS, 0.5F, 2.6F + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.8F);
/*     */     
/* 409 */     for (int i = 0; i < 8; i++)
/*     */     {
/* 411 */       worldIn.spawnParticle(EnumParticleTypes.SMOKE_LARGE, d0 + Math.random(), d1 + 1.2D, d2 + Math.random(), 0.0D, 0.0D, 0.0D, new int[0]);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 420 */     return getDefaultState().withProperty((IProperty)LEVEL, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 428 */     return ((Integer)state.getValue((IProperty)LEVEL)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 433 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)LEVEL });
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockDynamicLiquid getFlowingBlock(Material materialIn) {
/* 438 */     if (materialIn == Material.WATER)
/*     */     {
/* 440 */       return Blocks.FLOWING_WATER;
/*     */     }
/* 442 */     if (materialIn == Material.LAVA)
/*     */     {
/* 444 */       return Blocks.FLOWING_LAVA;
/*     */     }
/*     */ 
/*     */     
/* 448 */     throw new IllegalArgumentException("Invalid material");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockStaticLiquid getStaticBlock(Material materialIn) {
/* 454 */     if (materialIn == Material.WATER)
/*     */     {
/* 456 */       return Blocks.WATER;
/*     */     }
/* 458 */     if (materialIn == Material.LAVA)
/*     */     {
/* 460 */       return Blocks.LAVA;
/*     */     }
/*     */ 
/*     */     
/* 464 */     throw new IllegalArgumentException("Invalid material");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static float func_190973_f(IBlockState p_190973_0_, IBlockAccess p_190973_1_, BlockPos p_190973_2_) {
/* 470 */     int i = ((Integer)p_190973_0_.getValue((IProperty)LEVEL)).intValue();
/* 471 */     return ((i & 0x7) == 0 && p_190973_1_.getBlockState(p_190973_2_.up()).getMaterial() == Material.WATER) ? 1.0F : (1.0F - getLiquidHeightPercent(i));
/*     */   }
/*     */ 
/*     */   
/*     */   public static float func_190972_g(IBlockState p_190972_0_, IBlockAccess p_190972_1_, BlockPos p_190972_2_) {
/* 476 */     return p_190972_2_.getY() + func_190973_f(p_190972_0_, p_190972_1_, p_190972_2_);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 481 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */