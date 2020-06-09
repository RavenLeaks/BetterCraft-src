/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ 
/*     */ public class BlockFarmland
/*     */   extends Block
/*     */ {
/*  23 */   public static final PropertyInteger MOISTURE = PropertyInteger.create("moisture", 0, 7);
/*  24 */   protected static final AxisAlignedBB FARMLAND_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
/*  25 */   protected static final AxisAlignedBB field_194405_c = new AxisAlignedBB(0.0D, 0.9375D, 0.0D, 1.0D, 1.0D, 1.0D);
/*     */ 
/*     */   
/*     */   protected BlockFarmland() {
/*  29 */     super(Material.GROUND);
/*  30 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)MOISTURE, Integer.valueOf(0)));
/*  31 */     setTickRandomly(true);
/*  32 */     setLightOpacity(255);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  37 */     return FARMLAND_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  45 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  55 */     int i = ((Integer)state.getValue((IProperty)MOISTURE)).intValue();
/*     */     
/*  57 */     if (!hasWater(worldIn, pos) && !worldIn.isRainingAt(pos.up())) {
/*     */       
/*  59 */       if (i > 0)
/*     */       {
/*  61 */         worldIn.setBlockState(pos, state.withProperty((IProperty)MOISTURE, Integer.valueOf(i - 1)), 2);
/*     */       }
/*  63 */       else if (!hasCrops(worldIn, pos))
/*     */       {
/*  65 */         func_190970_b(worldIn, pos);
/*     */       }
/*     */     
/*  68 */     } else if (i < 7) {
/*     */       
/*  70 */       worldIn.setBlockState(pos, state.withProperty((IProperty)MOISTURE, Integer.valueOf(7)), 2);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onFallenUpon(World worldIn, BlockPos pos, Entity entityIn, float fallDistance) {
/*  79 */     if (!worldIn.isRemote && worldIn.rand.nextFloat() < fallDistance - 0.5F && entityIn instanceof net.minecraft.entity.EntityLivingBase && (entityIn instanceof net.minecraft.entity.player.EntityPlayer || worldIn.getGameRules().getBoolean("mobGriefing")) && entityIn.width * entityIn.width * entityIn.height > 0.512F)
/*     */     {
/*  81 */       func_190970_b(worldIn, pos);
/*     */     }
/*     */     
/*  84 */     super.onFallenUpon(worldIn, pos, entityIn, fallDistance);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static void func_190970_b(World p_190970_0_, BlockPos p_190970_1_) {
/*  89 */     p_190970_0_.setBlockState(p_190970_1_, Blocks.DIRT.getDefaultState());
/*  90 */     AxisAlignedBB axisalignedbb = field_194405_c.offset(p_190970_1_);
/*     */     
/*  92 */     for (Entity entity : p_190970_0_.getEntitiesWithinAABBExcludingEntity(null, axisalignedbb)) {
/*     */       
/*  94 */       double d0 = Math.min(axisalignedbb.maxY - axisalignedbb.minY, axisalignedbb.maxY - (entity.getEntityBoundingBox()).minY);
/*  95 */       entity.setPositionAndUpdate(entity.posX, entity.posY + d0 + 0.001D, entity.posZ);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasCrops(World worldIn, BlockPos pos) {
/* 101 */     Block block = worldIn.getBlockState(pos.up()).getBlock();
/* 102 */     return !(!(block instanceof BlockCrops) && !(block instanceof BlockStem));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean hasWater(World worldIn, BlockPos pos) {
/* 107 */     for (BlockPos.MutableBlockPos blockpos$mutableblockpos : BlockPos.getAllInBoxMutable(pos.add(-4, 0, -4), pos.add(4, 1, 4))) {
/*     */       
/* 109 */       if (worldIn.getBlockState((BlockPos)blockpos$mutableblockpos).getMaterial() == Material.WATER)
/*     */       {
/* 111 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 115 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 125 */     super.neighborChanged(state, worldIn, pos, blockIn, p_189540_5_);
/*     */     
/* 127 */     if (worldIn.getBlockState(pos.up()).getMaterial().isSolid())
/*     */     {
/* 129 */       func_190970_b(worldIn, pos);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/* 138 */     super.onBlockAdded(worldIn, pos, state);
/*     */     
/* 140 */     if (worldIn.getBlockState(pos.up()).getMaterial().isSolid())
/*     */     {
/* 142 */       func_190970_b(worldIn, pos); } 
/*     */   }
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/*     */     IBlockState iblockstate;
/*     */     Block block;
/* 148 */     switch (side) {
/*     */       
/*     */       case UP:
/* 151 */         return true;
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/*     */       case WEST:
/*     */       case EAST:
/* 157 */         iblockstate = blockAccess.getBlockState(pos.offset(side));
/* 158 */         block = iblockstate.getBlock();
/* 159 */         return (!iblockstate.isOpaqueCube() && block != Blocks.FARMLAND && block != Blocks.GRASS_PATH);
/*     */     } 
/*     */     
/* 162 */     return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 171 */     return Blocks.DIRT.getItemDropped(Blocks.DIRT.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 179 */     return getDefaultState().withProperty((IProperty)MOISTURE, Integer.valueOf(meta & 0x7));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 187 */     return ((Integer)state.getValue((IProperty)MOISTURE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 192 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)MOISTURE });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 197 */     return (p_193383_4_ == EnumFacing.DOWN) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockFarmland.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */