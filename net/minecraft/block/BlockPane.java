/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockPane
/*     */   extends Block {
/*  28 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  29 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  30 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  31 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*  32 */   protected static final AxisAlignedBB[] AABB_BY_INDEX = new AxisAlignedBB[] { new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 0.5625D, 1.0D, 0.5625D), new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 0.5625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 0.5625D, 1.0D, 0.5625D), new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 0.5625D, 1.0D, 1.0D), new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 0.5625D), new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5625D, 1.0D, 0.5625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5625D, 1.0D, 1.0D), new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D), new AxisAlignedBB(0.4375D, 0.0D, 0.4375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 0.5625D), new AxisAlignedBB(0.0D, 0.0D, 0.4375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5625D), new AxisAlignedBB(0.4375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.5625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) };
/*     */   
/*     */   private final boolean canDrop;
/*     */   
/*     */   protected BlockPane(Material materialIn, boolean canDrop) {
/*  37 */     super(materialIn);
/*  38 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)));
/*  39 */     this.canDrop = canDrop;
/*  40 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
/*  45 */     if (!p_185477_7_)
/*     */     {
/*  47 */       state = getActualState(state, (IBlockAccess)worldIn, pos);
/*     */     }
/*     */     
/*  50 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[0]);
/*     */     
/*  52 */     if (((Boolean)state.getValue((IProperty)NORTH)).booleanValue())
/*     */     {
/*  54 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[getBoundingBoxIndex(EnumFacing.NORTH)]);
/*     */     }
/*     */     
/*  57 */     if (((Boolean)state.getValue((IProperty)SOUTH)).booleanValue())
/*     */     {
/*  59 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[getBoundingBoxIndex(EnumFacing.SOUTH)]);
/*     */     }
/*     */     
/*  62 */     if (((Boolean)state.getValue((IProperty)EAST)).booleanValue())
/*     */     {
/*  64 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[getBoundingBoxIndex(EnumFacing.EAST)]);
/*     */     }
/*     */     
/*  67 */     if (((Boolean)state.getValue((IProperty)WEST)).booleanValue())
/*     */     {
/*  69 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_BY_INDEX[getBoundingBoxIndex(EnumFacing.WEST)]);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getBoundingBoxIndex(EnumFacing p_185729_0_) {
/*  75 */     return 1 << p_185729_0_.getHorizontalIndex();
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  80 */     state = getActualState(state, source, pos);
/*  81 */     return AABB_BY_INDEX[getBoundingBoxIndex(state)];
/*     */   }
/*     */ 
/*     */   
/*     */   private static int getBoundingBoxIndex(IBlockState state) {
/*  86 */     int i = 0;
/*     */     
/*  88 */     if (((Boolean)state.getValue((IProperty)NORTH)).booleanValue())
/*     */     {
/*  90 */       i |= getBoundingBoxIndex(EnumFacing.NORTH);
/*     */     }
/*     */     
/*  93 */     if (((Boolean)state.getValue((IProperty)EAST)).booleanValue())
/*     */     {
/*  95 */       i |= getBoundingBoxIndex(EnumFacing.EAST);
/*     */     }
/*     */     
/*  98 */     if (((Boolean)state.getValue((IProperty)SOUTH)).booleanValue())
/*     */     {
/* 100 */       i |= getBoundingBoxIndex(EnumFacing.SOUTH);
/*     */     }
/*     */     
/* 103 */     if (((Boolean)state.getValue((IProperty)WEST)).booleanValue())
/*     */     {
/* 105 */       i |= getBoundingBoxIndex(EnumFacing.WEST);
/*     */     }
/*     */     
/* 108 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 117 */     return state.withProperty((IProperty)NORTH, Boolean.valueOf(func_193393_b(worldIn, worldIn.getBlockState(pos.north()), pos.north(), EnumFacing.SOUTH))).withProperty((IProperty)SOUTH, Boolean.valueOf(func_193393_b(worldIn, worldIn.getBlockState(pos.south()), pos.south(), EnumFacing.NORTH))).withProperty((IProperty)WEST, Boolean.valueOf(func_193393_b(worldIn, worldIn.getBlockState(pos.west()), pos.west(), EnumFacing.EAST))).withProperty((IProperty)EAST, Boolean.valueOf(func_193393_b(worldIn, worldIn.getBlockState(pos.east()), pos.east(), EnumFacing.WEST)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 125 */     return !this.canDrop ? Items.field_190931_a : super.getItemDropped(state, rand, fortune);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 133 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 138 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 143 */     return (blockAccess.getBlockState(pos.offset(side)).getBlock() == this) ? false : super.shouldSideBeRendered(blockState, blockAccess, pos, side);
/*     */   }
/*     */ 
/*     */   
/*     */   public final boolean func_193393_b(IBlockAccess p_193393_1_, IBlockState p_193393_2_, BlockPos p_193393_3_, EnumFacing p_193393_4_) {
/* 148 */     Block block = p_193393_2_.getBlock();
/* 149 */     BlockFaceShape blockfaceshape = p_193393_2_.func_193401_d(p_193393_1_, p_193393_3_, p_193393_4_);
/* 150 */     return !((func_193394_e(block) || blockfaceshape != BlockFaceShape.SOLID) && blockfaceshape != BlockFaceShape.MIDDLE_POLE_THIN);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean func_193394_e(Block p_193394_0_) {
/* 155 */     return !(!(p_193394_0_ instanceof BlockShulkerBox) && !(p_193394_0_ instanceof BlockLeaves) && p_193394_0_ != Blocks.BEACON && p_193394_0_ != Blocks.CAULDRON && p_193394_0_ != Blocks.GLOWSTONE && p_193394_0_ != Blocks.ICE && p_193394_0_ != Blocks.SEA_LANTERN && p_193394_0_ != Blocks.PISTON && p_193394_0_ != Blocks.STICKY_PISTON && p_193394_0_ != Blocks.PISTON_HEAD && p_193394_0_ != Blocks.MELON_BLOCK && p_193394_0_ != Blocks.PUMPKIN && p_193394_0_ != Blocks.LIT_PUMPKIN && p_193394_0_ != Blocks.BARRIER);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSilkHarvest() {
/* 160 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 165 */     return BlockRenderLayer.CUTOUT_MIPPED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 173 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 182 */     switch (rot) {
/*     */       
/*     */       case null:
/* 185 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)SOUTH)).withProperty((IProperty)EAST, state.getValue((IProperty)WEST)).withProperty((IProperty)SOUTH, state.getValue((IProperty)NORTH)).withProperty((IProperty)WEST, state.getValue((IProperty)EAST));
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 188 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)EAST)).withProperty((IProperty)EAST, state.getValue((IProperty)SOUTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)WEST)).withProperty((IProperty)WEST, state.getValue((IProperty)NORTH));
/*     */       
/*     */       case CLOCKWISE_90:
/* 191 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)WEST)).withProperty((IProperty)EAST, state.getValue((IProperty)NORTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)EAST)).withProperty((IProperty)WEST, state.getValue((IProperty)SOUTH));
/*     */     } 
/*     */     
/* 194 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 204 */     switch (mirrorIn) {
/*     */       
/*     */       case LEFT_RIGHT:
/* 207 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)SOUTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)NORTH));
/*     */       
/*     */       case null:
/* 210 */         return state.withProperty((IProperty)EAST, state.getValue((IProperty)WEST)).withProperty((IProperty)WEST, state.getValue((IProperty)EAST));
/*     */     } 
/*     */     
/* 213 */     return super.withMirror(state, mirrorIn);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 219 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 224 */     return (p_193383_4_ != EnumFacing.UP && p_193383_4_ != EnumFacing.DOWN) ? BlockFaceShape.MIDDLE_POLE_THIN : BlockFaceShape.CENTER_SMALL;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockPane.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */