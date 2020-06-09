/*     */ package net.minecraft.block;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.BlockWorldState;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.block.state.pattern.BlockPattern;
/*     */ import net.minecraft.block.state.pattern.BlockStateMatcher;
/*     */ import net.minecraft.block.state.pattern.FactoryBlockPattern;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockEndPortalFrame extends Block {
/*  34 */   public static final PropertyDirection FACING = BlockHorizontal.FACING;
/*  35 */   public static final PropertyBool EYE = PropertyBool.create("eye");
/*  36 */   protected static final AxisAlignedBB AABB_BLOCK = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.8125D, 1.0D);
/*  37 */   protected static final AxisAlignedBB AABB_EYE = new AxisAlignedBB(0.3125D, 0.8125D, 0.3125D, 0.6875D, 1.0D, 0.6875D);
/*     */   
/*     */   private static BlockPattern portalShape;
/*     */   
/*     */   public BlockEndPortalFrame() {
/*  42 */     super(Material.ROCK, MapColor.GREEN);
/*  43 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)FACING, (Comparable)EnumFacing.NORTH).withProperty((IProperty)EYE, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  51 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  56 */     return AABB_BLOCK;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
/*  61 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BLOCK);
/*     */     
/*  63 */     if (((Boolean)worldIn.getBlockState(pos).getValue((IProperty)EYE)).booleanValue())
/*     */     {
/*  65 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_EYE);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/*  74 */     return Items.field_190931_a;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  83 */     return getDefaultState().withProperty((IProperty)FACING, (Comparable)placer.getHorizontalFacing().getOpposite()).withProperty((IProperty)EYE, Boolean.valueOf(false));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasComparatorInputOverride(IBlockState state) {
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getComparatorInputOverride(IBlockState blockState, World worldIn, BlockPos pos) {
/*  93 */     return ((Boolean)blockState.getValue((IProperty)EYE)).booleanValue() ? 15 : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 101 */     return getDefaultState().withProperty((IProperty)EYE, Boolean.valueOf(((meta & 0x4) != 0))).withProperty((IProperty)FACING, (Comparable)EnumFacing.getHorizontal(meta & 0x3));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 109 */     int i = 0;
/* 110 */     i |= ((EnumFacing)state.getValue((IProperty)FACING)).getHorizontalIndex();
/*     */     
/* 112 */     if (((Boolean)state.getValue((IProperty)EYE)).booleanValue())
/*     */     {
/* 114 */       i |= 0x4;
/*     */     }
/*     */     
/* 117 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 126 */     return state.withProperty((IProperty)FACING, (Comparable)rot.rotate((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 135 */     return state.withRotation(mirrorIn.toRotation((EnumFacing)state.getValue((IProperty)FACING)));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 140 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)FACING, (IProperty)EYE });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public static BlockPattern getOrCreatePortalShape() {
/* 150 */     if (portalShape == null)
/*     */     {
/* 152 */       portalShape = FactoryBlockPattern.start().aisle(new String[] { "?vvv?", ">???<", ">???<", ">???<", "?^^^?" }).where('?', BlockWorldState.hasState(BlockStateMatcher.ANY)).where('^', BlockWorldState.hasState((Predicate)BlockStateMatcher.forBlock(Blocks.END_PORTAL_FRAME).where((IProperty)EYE, Predicates.equalTo(Boolean.valueOf(true))).where((IProperty)FACING, Predicates.equalTo(EnumFacing.SOUTH)))).where('>', BlockWorldState.hasState((Predicate)BlockStateMatcher.forBlock(Blocks.END_PORTAL_FRAME).where((IProperty)EYE, Predicates.equalTo(Boolean.valueOf(true))).where((IProperty)FACING, Predicates.equalTo(EnumFacing.WEST)))).where('v', BlockWorldState.hasState((Predicate)BlockStateMatcher.forBlock(Blocks.END_PORTAL_FRAME).where((IProperty)EYE, Predicates.equalTo(Boolean.valueOf(true))).where((IProperty)FACING, Predicates.equalTo(EnumFacing.NORTH)))).where('<', BlockWorldState.hasState((Predicate)BlockStateMatcher.forBlock(Blocks.END_PORTAL_FRAME).where((IProperty)EYE, Predicates.equalTo(Boolean.valueOf(true))).where((IProperty)FACING, Predicates.equalTo(EnumFacing.EAST)))).build();
/*     */     }
/*     */     
/* 155 */     return portalShape;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 160 */     return (p_193383_4_ == EnumFacing.DOWN) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockEndPortalFrame.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */