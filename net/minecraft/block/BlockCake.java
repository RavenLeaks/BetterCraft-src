/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.stats.StatList;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockCake
/*     */   extends Block {
/*  25 */   public static final PropertyInteger BITES = PropertyInteger.create("bites", 0, 6);
/*  26 */   protected static final AxisAlignedBB[] CAKE_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.1875D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.3125D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.4375D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.5625D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.6875D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D), new AxisAlignedBB(0.8125D, 0.0D, 0.0625D, 0.9375D, 0.5D, 0.9375D) };
/*     */ 
/*     */   
/*     */   protected BlockCake() {
/*  30 */     super(Material.CAKE);
/*  31 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)BITES, Integer.valueOf(0)));
/*  32 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  37 */     return CAKE_AABB[((Integer)state.getValue((IProperty)BITES)).intValue()];
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/*  42 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  50 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/*  55 */     if (!worldIn.isRemote)
/*     */     {
/*  57 */       return eatCake(worldIn, pos, state, playerIn);
/*     */     }
/*     */ 
/*     */     
/*  61 */     ItemStack itemstack = playerIn.getHeldItem(hand);
/*  62 */     return !(!eatCake(worldIn, pos, state, playerIn) && !itemstack.func_190926_b());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean eatCake(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
/*  68 */     if (!player.canEat(false))
/*     */     {
/*  70 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  74 */     player.addStat(StatList.CAKE_SLICES_EATEN);
/*  75 */     player.getFoodStats().addStats(2, 0.1F);
/*  76 */     int i = ((Integer)state.getValue((IProperty)BITES)).intValue();
/*     */     
/*  78 */     if (i < 6) {
/*     */       
/*  80 */       worldIn.setBlockState(pos, state.withProperty((IProperty)BITES, Integer.valueOf(i + 1)), 3);
/*     */     }
/*     */     else {
/*     */       
/*  84 */       worldIn.setBlockToAir(pos);
/*     */     } 
/*     */     
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  93 */     return super.canPlaceBlockAt(worldIn, pos) ? canBlockStay(worldIn, pos) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 103 */     if (!canBlockStay(worldIn, pos))
/*     */     {
/* 105 */       worldIn.setBlockToAir(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canBlockStay(World worldIn, BlockPos pos) {
/* 111 */     return worldIn.getBlockState(pos.down()).getMaterial().isSolid();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 119 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 127 */     return Items.field_190931_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 132 */     return new ItemStack(Items.CAKE);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 137 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 145 */     return getDefaultState().withProperty((IProperty)BITES, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 153 */     return ((Integer)state.getValue((IProperty)BITES)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 158 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)BITES });
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
/* 163 */     return (7 - ((Integer)blockState.getValue((IProperty)BITES)).intValue()) * 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride(IBlockState state) {
/* 168 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 173 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockCake.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */