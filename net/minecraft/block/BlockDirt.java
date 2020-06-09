/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockDirt
/*     */   extends Block {
/*  21 */   public static final PropertyEnum<DirtType> VARIANT = PropertyEnum.create("variant", DirtType.class);
/*  22 */   public static final PropertyBool SNOWY = PropertyBool.create("snowy");
/*     */ 
/*     */   
/*     */   protected BlockDirt() {
/*  26 */     super(Material.GROUND);
/*  27 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, DirtType.DIRT).withProperty((IProperty)SNOWY, Boolean.valueOf(false)));
/*  28 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
/*  36 */     return ((DirtType)state.getValue((IProperty)VARIANT)).getColor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  45 */     if (state.getValue((IProperty)VARIANT) == DirtType.PODZOL) {
/*     */       
/*  47 */       Block block = worldIn.getBlockState(pos.up()).getBlock();
/*  48 */       state = state.withProperty((IProperty)SNOWY, Boolean.valueOf(!(block != Blocks.SNOW && block != Blocks.SNOW_LAYER)));
/*     */     } 
/*     */     
/*  51 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/*  59 */     tab.add(new ItemStack(this, 1, DirtType.DIRT.getMetadata()));
/*  60 */     tab.add(new ItemStack(this, 1, DirtType.COARSE_DIRT.getMetadata()));
/*  61 */     tab.add(new ItemStack(this, 1, DirtType.PODZOL.getMetadata()));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/*  66 */     return new ItemStack(this, 1, ((DirtType)state.getValue((IProperty)VARIANT)).getMetadata());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  74 */     return getDefaultState().withProperty((IProperty)VARIANT, DirtType.byMetadata(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*  82 */     return ((DirtType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/*  87 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)VARIANT, (IProperty)SNOWY });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/*  96 */     DirtType blockdirt$dirttype = (DirtType)state.getValue((IProperty)VARIANT);
/*     */     
/*  98 */     if (blockdirt$dirttype == DirtType.PODZOL)
/*     */     {
/* 100 */       blockdirt$dirttype = DirtType.DIRT;
/*     */     }
/*     */     
/* 103 */     return blockdirt$dirttype.getMetadata();
/*     */   }
/*     */   
/*     */   public enum DirtType
/*     */     implements IStringSerializable {
/* 108 */     DIRT(0, "dirt", "default", (String)MapColor.DIRT),
/* 109 */     COARSE_DIRT(1, "coarse_dirt", "coarse", (String)MapColor.DIRT),
/* 110 */     PODZOL(2, "podzol", MapColor.OBSIDIAN);
/*     */     
/* 112 */     private static final DirtType[] METADATA_LOOKUP = new DirtType[(values()).length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int metadata;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String name;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String unlocalizedName;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final MapColor color;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       DirtType[] arrayOfDirtType;
/* 167 */       for (i = (arrayOfDirtType = values()).length, b = 0; b < i; ) { DirtType blockdirt$dirttype = arrayOfDirtType[b];
/*     */         
/* 169 */         METADATA_LOOKUP[blockdirt$dirttype.getMetadata()] = blockdirt$dirttype;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     DirtType(int metadataIn, String nameIn, String unlocalizedNameIn, MapColor color) {
/*     */       this.metadata = metadataIn;
/*     */       this.name = nameIn;
/*     */       this.unlocalizedName = unlocalizedNameIn;
/*     */       this.color = color;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.metadata;
/*     */     }
/*     */     
/*     */     public String getUnlocalizedName() {
/*     */       return this.unlocalizedName;
/*     */     }
/*     */     
/*     */     public MapColor getColor() {
/*     */       return this.color;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static DirtType byMetadata(int metadata) {
/*     */       if (metadata < 0 || metadata >= METADATA_LOOKUP.length)
/*     */         metadata = 0; 
/*     */       return METADATA_LOOKUP[metadata];
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockDirt.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */