/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.NonNullList;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.text.translation.I18n;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockWall
/*     */   extends Block {
/*  26 */   public static final PropertyBool UP = PropertyBool.create("up");
/*  27 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  28 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  29 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  30 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*  31 */   public static final PropertyEnum<EnumType> VARIANT = PropertyEnum.create("variant", EnumType.class);
/*  32 */   protected static final AxisAlignedBB[] AABB_BY_INDEX = new AxisAlignedBB[] { new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.6875D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 1.0D, 0.875D, 0.6875D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) };
/*  33 */   protected static final AxisAlignedBB[] CLIP_AABB_BY_INDEX = new AxisAlignedBB[] { AABB_BY_INDEX[0].setMaxY(1.5D), AABB_BY_INDEX[1].setMaxY(1.5D), AABB_BY_INDEX[2].setMaxY(1.5D), AABB_BY_INDEX[3].setMaxY(1.5D), AABB_BY_INDEX[4].setMaxY(1.5D), AABB_BY_INDEX[5].setMaxY(1.5D), AABB_BY_INDEX[6].setMaxY(1.5D), AABB_BY_INDEX[7].setMaxY(1.5D), AABB_BY_INDEX[8].setMaxY(1.5D), AABB_BY_INDEX[9].setMaxY(1.5D), AABB_BY_INDEX[10].setMaxY(1.5D), AABB_BY_INDEX[11].setMaxY(1.5D), AABB_BY_INDEX[12].setMaxY(1.5D), AABB_BY_INDEX[13].setMaxY(1.5D), AABB_BY_INDEX[14].setMaxY(1.5D), AABB_BY_INDEX[15].setMaxY(1.5D) };
/*     */ 
/*     */   
/*     */   public BlockWall(Block modelBlock) {
/*  37 */     super(modelBlock.blockMaterial);
/*  38 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)UP, Boolean.valueOf(false)).withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)).withProperty((IProperty)VARIANT, EnumType.NORMAL));
/*  39 */     setHardness(modelBlock.blockHardness);
/*  40 */     setResistance(modelBlock.blockResistance / 3.0F);
/*  41 */     setSoundType(modelBlock.blockSoundType);
/*  42 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  47 */     state = getActualState(state, source, pos);
/*  48 */     return AABB_BY_INDEX[getAABBIndex(state)];
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
/*  53 */     if (!p_185477_7_)
/*     */     {
/*  55 */       state = getActualState(state, (IBlockAccess)worldIn, pos);
/*     */     }
/*     */     
/*  58 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, CLIP_AABB_BY_INDEX[getAABBIndex(state)]);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/*  64 */     blockState = getActualState(blockState, worldIn, pos);
/*  65 */     return CLIP_AABB_BY_INDEX[getAABBIndex(blockState)];
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getAABBIndex(IBlockState state) {
/*  70 */     int i = 0;
/*     */     
/*  72 */     if (((Boolean)state.getValue((IProperty)NORTH)).booleanValue())
/*     */     {
/*  74 */       i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
/*     */     }
/*     */     
/*  77 */     if (((Boolean)state.getValue((IProperty)EAST)).booleanValue())
/*     */     {
/*  79 */       i |= 1 << EnumFacing.EAST.getHorizontalIndex();
/*     */     }
/*     */     
/*  82 */     if (((Boolean)state.getValue((IProperty)SOUTH)).booleanValue())
/*     */     {
/*  84 */       i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
/*     */     }
/*     */     
/*  87 */     if (((Boolean)state.getValue((IProperty)WEST)).booleanValue())
/*     */     {
/*  89 */       i |= 1 << EnumFacing.WEST.getHorizontalIndex();
/*     */     }
/*     */     
/*  92 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getLocalizedName() {
/* 100 */     return I18n.translateToLocal(String.valueOf(getUnlocalizedName()) + "." + EnumType.NORMAL.getUnlocalizedName() + ".name");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 105 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/* 110 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 118 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing p_176253_3_) {
/* 123 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 124 */     Block block = iblockstate.getBlock();
/* 125 */     BlockFaceShape blockfaceshape = iblockstate.func_193401_d(worldIn, pos, p_176253_3_);
/* 126 */     boolean flag = !(blockfaceshape != BlockFaceShape.MIDDLE_POLE_THICK && (blockfaceshape != BlockFaceShape.MIDDLE_POLE || !(block instanceof BlockFenceGate)));
/* 127 */     return !((func_194143_e(block) || blockfaceshape != BlockFaceShape.SOLID) && !flag);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean func_194143_e(Block p_194143_0_) {
/* 132 */     return !(!Block.func_193382_c(p_194143_0_) && p_194143_0_ != Blocks.BARRIER && p_194143_0_ != Blocks.MELON_BLOCK && p_194143_0_ != Blocks.PUMPKIN && p_194143_0_ != Blocks.LIT_PUMPKIN);
/*     */   }
/*     */ 
/*     */   
/*     */   public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> tab) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumType[] arrayOfEnumType;
/* 140 */     for (i = (arrayOfEnumType = EnumType.values()).length, b = 0; b < i; ) { EnumType blockwall$enumtype = arrayOfEnumType[b];
/*     */       
/* 142 */       tab.add(new ItemStack(this, 1, blockwall$enumtype.getMetadata()));
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int damageDropped(IBlockState state) {
/* 152 */     return ((EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 157 */     return (side == EnumFacing.DOWN) ? super.shouldSideBeRendered(blockState, blockAccess, pos, side) : true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 165 */     return getDefaultState().withProperty((IProperty)VARIANT, EnumType.byMetadata(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 173 */     return ((EnumType)state.getValue((IProperty)VARIANT)).getMetadata();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 182 */     boolean flag = canConnectTo(worldIn, pos.north(), EnumFacing.SOUTH);
/* 183 */     boolean flag1 = canConnectTo(worldIn, pos.east(), EnumFacing.WEST);
/* 184 */     boolean flag2 = canConnectTo(worldIn, pos.south(), EnumFacing.NORTH);
/* 185 */     boolean flag3 = canConnectTo(worldIn, pos.west(), EnumFacing.EAST);
/* 186 */     boolean flag4 = !((!flag || flag1 || !flag2 || flag3) && (flag || !flag1 || flag2 || !flag3));
/* 187 */     return state.withProperty((IProperty)UP, Boolean.valueOf(!(flag4 && worldIn.isAirBlock(pos.up())))).withProperty((IProperty)NORTH, Boolean.valueOf(flag)).withProperty((IProperty)EAST, Boolean.valueOf(flag1)).withProperty((IProperty)SOUTH, Boolean.valueOf(flag2)).withProperty((IProperty)WEST, Boolean.valueOf(flag3));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 192 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)UP, (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH, (IProperty)VARIANT });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 197 */     return (p_193383_4_ != EnumFacing.UP && p_193383_4_ != EnumFacing.DOWN) ? BlockFaceShape.MIDDLE_POLE_THICK : BlockFaceShape.CENTER_BIG;
/*     */   }
/*     */   
/*     */   public enum EnumType
/*     */     implements IStringSerializable {
/* 202 */     NORMAL(0, "cobblestone", "normal"),
/* 203 */     MOSSY(1, "mossy_cobblestone", "mossy");
/*     */     
/* 205 */     private static final EnumType[] META_LOOKUP = new EnumType[(values()).length];
/*     */ 
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
/*     */     static {
/*     */       byte b;
/*     */       int i;
/*     */       EnumType[] arrayOfEnumType;
/* 248 */       for (i = (arrayOfEnumType = values()).length, b = 0; b < i; ) { EnumType blockwall$enumtype = arrayOfEnumType[b];
/*     */         
/* 250 */         META_LOOKUP[blockwall$enumtype.getMetadata()] = blockwall$enumtype;
/*     */         b++; }
/*     */     
/*     */     }
/*     */     
/*     */     EnumType(int meta, String name, String unlocalizedName) {
/*     */       this.meta = meta;
/*     */       this.name = name;
/*     */       this.unlocalizedName = unlocalizedName;
/*     */     }
/*     */     
/*     */     public int getMetadata() {
/*     */       return this.meta;
/*     */     }
/*     */     
/*     */     public String toString() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public static EnumType byMetadata(int meta) {
/*     */       if (meta < 0 || meta >= META_LOOKUP.length)
/*     */         meta = 0; 
/*     */       return META_LOOKUP[meta];
/*     */     }
/*     */     
/*     */     public String getName() {
/*     */       return this.name;
/*     */     }
/*     */     
/*     */     public String getUnlocalizedName() {
/*     */       return this.unlocalizedName;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockWall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */