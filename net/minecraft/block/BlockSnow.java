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
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockSnow
/*     */   extends Block {
/*  28 */   public static final PropertyInteger LAYERS = PropertyInteger.create("layers", 1, 8);
/*  29 */   protected static final AxisAlignedBB[] SNOW_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.125D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.25D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.625D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.75D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) };
/*     */ 
/*     */   
/*     */   protected BlockSnow() {
/*  33 */     super(Material.SNOW);
/*  34 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)LAYERS, Integer.valueOf(1)));
/*  35 */     setTickRandomly(true);
/*  36 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  41 */     return SNOW_AABB[((Integer)state.getValue((IProperty)LAYERS)).intValue()];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/*  46 */     return (((Integer)worldIn.getBlockState(pos).getValue((IProperty)LAYERS)).intValue() < 5);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullyOpaque(IBlockState state) {
/*  54 */     return (((Integer)state.getValue((IProperty)LAYERS)).intValue() == 8);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/*  59 */     return (p_193383_4_ == EnumFacing.DOWN) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  65 */     int i = ((Integer)blockState.getValue((IProperty)LAYERS)).intValue() - 1;
/*  66 */     float f = 0.125F;
/*  67 */     AxisAlignedBB axisalignedbb = blockState.getBoundingBox(worldIn, pos);
/*  68 */     return new AxisAlignedBB(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ, axisalignedbb.maxX, (i * 0.125F), axisalignedbb.maxZ);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  86 */     IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*  87 */     Block block = iblockstate.getBlock();
/*     */     
/*  89 */     if (block != Blocks.ICE && block != Blocks.PACKED_ICE && block != Blocks.BARRIER) {
/*     */       
/*  91 */       BlockFaceShape blockfaceshape = iblockstate.func_193401_d((IBlockAccess)worldIn, pos.down(), EnumFacing.UP);
/*  92 */       return !(blockfaceshape != BlockFaceShape.SOLID && iblockstate.getMaterial() != Material.LEAVES && (block != this || ((Integer)iblockstate.getValue((IProperty)LAYERS)).intValue() != 8));
/*     */     } 
/*     */ 
/*     */     
/*  96 */     return false;
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
/* 107 */     checkAndDropBlock(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
/* 112 */     if (!canPlaceBlockAt(worldIn, pos)) {
/*     */       
/* 114 */       dropBlockAsItem(worldIn, pos, state, 0);
/* 115 */       worldIn.setBlockToAir(pos);
/* 116 */       return false;
/*     */     } 
/*     */ 
/*     */     
/* 120 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
/* 126 */     spawnAsEntity(worldIn, pos, new ItemStack(Items.SNOWBALL, ((Integer)state.getValue((IProperty)LAYERS)).intValue() + 1, 0));
/* 127 */     worldIn.setBlockToAir(pos);
/* 128 */     player.addStat(StatList.getBlockStats(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 136 */     return Items.SNOWBALL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 144 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 149 */     if (worldIn.getLightFor(EnumSkyBlock.BLOCK, pos) > 11) {
/*     */       
/* 151 */       dropBlockAsItem(worldIn, pos, worldIn.getBlockState(pos), 0);
/* 152 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 158 */     if (side == EnumFacing.UP)
/*     */     {
/* 160 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 164 */     IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
/* 165 */     return (iblockstate.getBlock() == this && ((Integer)iblockstate.getValue((IProperty)LAYERS)).intValue() >= ((Integer)blockState.getValue((IProperty)LAYERS)).intValue()) ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 174 */     return getDefaultState().withProperty((IProperty)LAYERS, Integer.valueOf((meta & 0x7) + 1));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isReplaceable(IBlockAccess worldIn, BlockPos pos) {
/* 182 */     return (((Integer)worldIn.getBlockState(pos).getValue((IProperty)LAYERS)).intValue() == 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 190 */     return ((Integer)state.getValue((IProperty)LAYERS)).intValue() - 1;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 195 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)LAYERS });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockSnow.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */