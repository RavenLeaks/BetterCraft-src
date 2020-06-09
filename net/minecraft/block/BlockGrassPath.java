/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockGrassPath extends Block {
/*  18 */   protected static final AxisAlignedBB GRASS_PATH_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.9375D, 1.0D);
/*     */ 
/*     */   
/*     */   protected BlockGrassPath() {
/*  22 */     super(Material.GROUND);
/*  23 */     setLightOpacity(255);
/*     */   }
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/*     */     IBlockState iblockstate;
/*     */     Block block;
/*  28 */     switch (side) {
/*     */       
/*     */       case UP:
/*  31 */         return true;
/*     */       
/*     */       case NORTH:
/*     */       case SOUTH:
/*     */       case WEST:
/*     */       case EAST:
/*  37 */         iblockstate = blockAccess.getBlockState(pos.offset(side));
/*  38 */         block = iblockstate.getBlock();
/*  39 */         return (!iblockstate.isOpaqueCube() && block != Blocks.FARMLAND && block != Blocks.GRASS_PATH);
/*     */     } 
/*     */     
/*  42 */     return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  51 */     super.onBlockAdded(worldIn, pos, state);
/*  52 */     func_190971_b(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_190971_b(World p_190971_1_, BlockPos p_190971_2_) {
/*  57 */     if (p_190971_1_.getBlockState(p_190971_2_.up()).getMaterial().isSolid())
/*     */     {
/*  59 */       BlockFarmland.func_190970_b(p_190971_1_, p_190971_2_);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  65 */     return GRASS_PATH_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  73 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  78 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  86 */     return Blocks.DIRT.getItemDropped(Blocks.DIRT.getDefaultState().withProperty((IProperty)BlockDirt.VARIANT, BlockDirt.DirtType.DIRT), rand, fortune);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/*  91 */     return new ItemStack(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 101 */     super.neighborChanged(state, worldIn, pos, blockIn, p_189540_5_);
/* 102 */     func_190971_b(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 107 */     return (p_193383_4_ == EnumFacing.DOWN) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockGrassPath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */