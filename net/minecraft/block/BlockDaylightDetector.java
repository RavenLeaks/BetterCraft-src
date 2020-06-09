/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.tileentity.TileEntityDaylightDetector;
/*     */ import net.minecraft.util.EnumBlockRenderType;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.EnumSkyBlock;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDaylightDetector
/*     */   extends BlockContainer {
/*  30 */   public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
/*  31 */   protected static final AxisAlignedBB DAYLIGHT_DETECTOR_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.375D, 1.0D);
/*     */   
/*     */   private final boolean inverted;
/*     */   
/*     */   public BlockDaylightDetector(boolean inverted) {
/*  36 */     super(Material.WOOD);
/*  37 */     this.inverted = inverted;
/*  38 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)POWER, Integer.valueOf(0)));
/*  39 */     setCreativeTab(CreativeTabs.REDSTONE);
/*  40 */     setHardness(0.2F);
/*  41 */     setSoundType(SoundType.WOOD);
/*  42 */     setUnlocalizedName("daylightDetector");
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  47 */     return DAYLIGHT_DETECTOR_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWeakPower(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/*  52 */     return ((Integer)blockState.getValue((IProperty)POWER)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updatePower(World worldIn, BlockPos pos) {
/*  57 */     if (worldIn.provider.func_191066_m()) {
/*     */       
/*  59 */       IBlockState iblockstate = worldIn.getBlockState(pos);
/*  60 */       int i = worldIn.getLightFor(EnumSkyBlock.SKY, pos) - worldIn.getSkylightSubtracted();
/*  61 */       float f = worldIn.getCelestialAngleRadians(1.0F);
/*     */       
/*  63 */       if (this.inverted)
/*     */       {
/*  65 */         i = 15 - i;
/*     */       }
/*     */       
/*  68 */       if (i > 0 && !this.inverted) {
/*     */         
/*  70 */         float f1 = (f < 3.1415927F) ? 0.0F : 6.2831855F;
/*  71 */         f += (f1 - f) * 0.2F;
/*  72 */         i = Math.round(i * MathHelper.cos(f));
/*     */       } 
/*     */       
/*  75 */       i = MathHelper.clamp(i, 0, 15);
/*     */       
/*  77 */       if (((Integer)iblockstate.getValue((IProperty)POWER)).intValue() != i)
/*     */       {
/*  79 */         worldIn.setBlockState(pos, iblockstate.withProperty((IProperty)POWER, Integer.valueOf(i)), 3);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/*  86 */     if (playerIn.isAllowEdit()) {
/*     */       
/*  88 */       if (worldIn.isRemote)
/*     */       {
/*  90 */         return true;
/*     */       }
/*     */ 
/*     */       
/*  94 */       if (this.inverted) {
/*     */         
/*  96 */         worldIn.setBlockState(pos, Blocks.DAYLIGHT_DETECTOR.getDefaultState().withProperty((IProperty)POWER, state.getValue((IProperty)POWER)), 4);
/*  97 */         Blocks.DAYLIGHT_DETECTOR.updatePower(worldIn, pos);
/*     */       }
/*     */       else {
/*     */         
/* 101 */         worldIn.setBlockState(pos, Blocks.DAYLIGHT_DETECTOR_INVERTED.getDefaultState().withProperty((IProperty)POWER, state.getValue((IProperty)POWER)), 4);
/* 102 */         Blocks.DAYLIGHT_DETECTOR_INVERTED.updatePower(worldIn, pos);
/*     */       } 
/*     */       
/* 105 */       return true;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 110 */     return super.onBlockActivated(worldIn, pos, state, playerIn, hand, heldItem, side, hitX, hitY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 119 */     return Item.getItemFromBlock(Blocks.DAYLIGHT_DETECTOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 124 */     return new ItemStack(Blocks.DAYLIGHT_DETECTOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 137 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EnumBlockRenderType getRenderType(IBlockState state) {
/* 146 */     return EnumBlockRenderType.MODEL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canProvidePower(IBlockState state) {
/* 154 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public TileEntity createNewTileEntity(World worldIn, int meta) {
/* 162 */     return (TileEntity)new TileEntityDaylightDetector();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 170 */     return getDefaultState().withProperty((IProperty)POWER, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 178 */     return ((Integer)state.getValue((IProperty)POWER)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 183 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)POWER });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/* 191 */     if (!this.inverted)
/*     */     {
/* 193 */       super.getSubBlocks(itemIn, tab);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 199 */     return (p_193383_4_ == EnumFacing.DOWN) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockDaylightDetector.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */