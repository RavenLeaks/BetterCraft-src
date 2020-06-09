/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockPurpurSlab
/*     */   extends BlockSlab {
/*  20 */   public static final PropertyEnum<Variant> VARIANT = PropertyEnum.create("variant", Variant.class);
/*     */ 
/*     */   
/*     */   public BlockPurpurSlab() {
/*  24 */     super(Material.ROCK, MapColor.MAGENTA);
/*  25 */     IBlockState iblockstate = this.blockState.getBaseState();
/*     */     
/*  27 */     if (!isDouble())
/*     */     {
/*  29 */       iblockstate = iblockstate.withProperty((IProperty)HALF, BlockSlab.EnumBlockHalf.BOTTOM);
/*     */     }
/*     */     
/*  32 */     setDefaultState(iblockstate.withProperty((IProperty)VARIANT, Variant.DEFAULT));
/*  33 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  41 */     return Item.getItemFromBlock(Blocks.PURPUR_SLAB);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/*  46 */     return new ItemStack(Blocks.PURPUR_SLAB);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  54 */     IBlockState iblockstate = getDefaultState().withProperty((IProperty)VARIANT, Variant.DEFAULT);
/*     */     
/*  56 */     if (!isDouble())
/*     */     {
/*  58 */       iblockstate = iblockstate.withProperty((IProperty)HALF, ((meta & 0x8) == 0) ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
/*     */     }
/*     */     
/*  61 */     return iblockstate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*  69 */     int i = 0;
/*     */     
/*  71 */     if (!isDouble() && state.getValue((IProperty)HALF) == BlockSlab.EnumBlockHalf.TOP)
/*     */     {
/*  73 */       i |= 0x8;
/*     */     }
/*     */     
/*  76 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/*  81 */     return isDouble() ? new BlockStateContainer(this, new IProperty[] { (IProperty)VARIANT }) : new BlockStateContainer(this, new IProperty[] { (IProperty)HALF, (IProperty)VARIANT });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUnlocalizedName(int meta) {
/*  89 */     return getUnlocalizedName();
/*     */   }
/*     */ 
/*     */   
/*     */   public IProperty<?> getVariantProperty() {
/*  94 */     return (IProperty<?>)VARIANT;
/*     */   }
/*     */ 
/*     */   
/*     */   public Comparable<?> getTypeForItem(ItemStack stack) {
/*  99 */     return Variant.DEFAULT;
/*     */   }
/*     */   
/*     */   public static class Double
/*     */     extends BlockPurpurSlab
/*     */   {
/*     */     public boolean isDouble() {
/* 106 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class Half
/*     */     extends BlockPurpurSlab
/*     */   {
/*     */     public boolean isDouble() {
/* 114 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   public enum Variant
/*     */     implements IStringSerializable {
/* 120 */     DEFAULT;
/*     */ 
/*     */     
/*     */     public String getName() {
/* 124 */       return "default";
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockPurpurSlab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */