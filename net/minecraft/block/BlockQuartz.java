/*     */ package net.minecraft.block;
/*     */ 
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockQuartz
/*     */   extends Block {
/*  23 */   public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);
/*     */ 
/*     */   
/*     */   public BlockQuartz() {
/*  27 */     super(Material.ROCK);
/*  28 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)VARIANT, EnumType.DEFAULT));
/*  29 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  38 */     if (meta == EnumType.LINES_Y.getMetadata())
/*     */     {
/*  40 */       switch (facing.getAxis()) {
/*     */         
/*     */         case Z:
/*  43 */           return getDefaultState().withProperty((IProperty)VARIANT, EnumType.LINES_Z);
/*     */         
/*     */         case null:
/*  46 */           return getDefaultState().withProperty((IProperty)VARIANT, EnumType.LINES_X);
/*     */         
/*     */         case Y:
/*  49 */           return getDefaultState().withProperty((IProperty)VARIANT, EnumType.LINES_Y);
/*     */       } 
/*     */     
/*     */     }
/*  53 */     return (meta == EnumType.CHISELED.getMetadata()) ? getDefaultState().withProperty((IProperty)VARIANT, EnumType.CHISELED) : getDefaultState().withProperty((IProperty)VARIANT, EnumType.DEFAULT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/*  62 */     EnumType blockquartz$enumtype = (EnumType)state.getValue((IProperty)VARIANT);
/*  63 */     return (blockquartz$enumtype != EnumType.LINES_X && blockquartz$enumtype != EnumType.LINES_Z) ? blockquartz$enumtype.getMetadata() : EnumType.LINES_Y.getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemStack getSilkTouchDrop(IBlockState state) {
/*  68 */     EnumType blockquartz$enumtype = (EnumType)state.getValue((IProperty)VARIANT);
/*  69 */     return (blockquartz$enumtype != EnumType.LINES_X && blockquartz$enumtype != EnumType.LINES_Z) ? super.getSilkTouchDrop(state) : new ItemStack(Item.getItemFromBlock(this), 1, EnumType.LINES_Y.getMetadata());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/*  77 */     tab.add(new ItemStack(this, 1, EnumType.DEFAULT.getMetadata()));
/*  78 */     tab.add(new ItemStack(this, 1, EnumType.CHISELED.getMetadata()));
/*  79 */     tab.add(new ItemStack(this, 1, EnumType.LINES_Y.getMetadata()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MapColor getMapColor(IBlockState state, IBlockAccess p_180659_2_, BlockPos p_180659_3_) {
/*  87 */     return MapColor.QUARTZ;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  95 */     return getDefaultState().withProperty((IProperty)VARIANT, EnumType.byMetadata(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 103 */     return ((EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 112 */     switch (rot) {
/*     */       
/*     */       case CLOCKWISE_90:
/*     */       case COUNTERCLOCKWISE_90:
/* 116 */         switch ((EnumType)state.getValue((IProperty)VARIANT)) {
/*     */           
/*     */           case LINES_X:
/* 119 */             return state.withProperty((IProperty)VARIANT, EnumType.LINES_Z);
/*     */           
/*     */           case LINES_Z:
/* 122 */             return state.withProperty((IProperty)VARIANT, EnumType.LINES_X);
/*     */         } 
/*     */         
/* 125 */         return state;
/*     */     } 
/*     */ 
/*     */     
/* 129 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 135 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)VARIANT });
/*     */   }
/*     */   
/*     */   public enum EnumType
/*     */     implements IStringSerializable {
/* 140 */     DEFAULT(0, "default", "default"),
/* 141 */     CHISELED(1, "chiseled", "chiseled"),
/* 142 */     LINES_Y(2, "lines_y", "lines"),
/* 143 */     LINES_X(3, "lines_x", "lines"),
/* 144 */     LINES_Z(4, "lines_z", "lines");
/*     */     
/* 146 */     private static final EnumType[] META_LOOKUP = new EnumType[(values()).length];
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final int meta;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private final String serializedName;
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
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumType[] arrayOfEnumType;
/* 184 */       for (i = (arrayOfEnumType = values()).length, b = 0; b < i; ) { EnumType blockquartz$enumtype = arrayOfEnumType[b];
/*     */         
/* 186 */         META_LOOKUP[blockquartz$enumtype.getMetadata()] = blockquartz$enumtype;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     EnumType(int meta, String name, String unlocalizedName) {
/*     */       this.meta = meta;
/*     */       this.serializedName = name;
/*     */       this.unlocalizedName = unlocalizedName;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.unlocalizedName;
/*     */     }
/*     */     
/*     */     public static EnumType byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length)
/*     */         meta = 0; 
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.serializedName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockQuartz.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */