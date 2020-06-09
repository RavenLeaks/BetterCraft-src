/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.EnumDyeColor;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockConcretePowder
/*     */   extends BlockFalling {
/*  21 */   public static final PropertyEnum<EnumDyeColor> field_192426_a = PropertyEnum.create("color", EnumDyeColor.class);
/*     */ 
/*     */   
/*     */   public BlockConcretePowder() {
/*  25 */     super(Material.SAND);
/*  26 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)field_192426_a, (Comparable)EnumDyeColor.WHITE));
/*  27 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEndFalling(World worldIn, BlockPos pos, IBlockState p_176502_3_, IBlockState p_176502_4_) {
/*  32 */     if (p_176502_4_.getMaterial().isLiquid())
/*     */     {
/*  34 */       worldIn.setBlockState(pos, Blocks.field_192443_dR.getDefaultState().withProperty((IProperty)BlockColored.COLOR, p_176502_3_.getValue((IProperty)field_192426_a)), 3);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean func_192425_e(World p_192425_1_, BlockPos p_192425_2_, IBlockState p_192425_3_) {
/*  40 */     boolean flag = false; byte b; int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/*  42 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/*  44 */       if (enumfacing != EnumFacing.DOWN) {
/*     */         
/*  46 */         BlockPos blockpos = p_192425_2_.offset(enumfacing);
/*     */         
/*  48 */         if (p_192425_1_.getBlockState(blockpos).getMaterial() == Material.WATER) {
/*     */           
/*  50 */           flag = true;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */       b++; }
/*     */     
/*  56 */     if (flag)
/*     */     {
/*  58 */       p_192425_1_.setBlockState(p_192425_2_, Blocks.field_192443_dR.getDefaultState().withProperty((IProperty)BlockColored.COLOR, p_192425_3_.getValue((IProperty)field_192426_a)), 3);
/*     */     }
/*     */     
/*  61 */     return flag;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/*  71 */     if (!func_192425_e(worldIn, pos, state))
/*     */     {
/*  73 */       super.neighborChanged(state, worldIn, pos, blockIn, p_189540_5_);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  82 */     if (!func_192425_e(worldIn, pos, state))
/*     */     {
/*  84 */       super.onBlockAdded(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/*  94 */     return ((EnumDyeColor)state.getValue((IProperty)field_192426_a)).getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumDyeColor[] arrayOfEnumDyeColor;
/* 102 */     for (i = (arrayOfEnumDyeColor = EnumDyeColor.values()).length, b = 0; b < i; ) { EnumDyeColor enumdyecolor = arrayOfEnumDyeColor[b];
/*     */       
/* 104 */       tab.add(new ItemStack(this, 1, enumdyecolor.getMetadata()));
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
/* 113 */     return MapColor.func_193558_a((EnumDyeColor)state.getValue((IProperty)field_192426_a));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 121 */     return getDefaultState().withProperty((IProperty)field_192426_a, (Comparable)EnumDyeColor.byMetadata(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 129 */     return ((EnumDyeColor)state.getValue((IProperty)field_192426_a)).getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 134 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)field_192426_a });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockConcretePowder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */