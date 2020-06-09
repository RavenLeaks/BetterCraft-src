/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyBool;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemLead;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumHand;
/*     */ import net.minecraft.util.Mirror;
/*     */ import net.minecraft.util.Rotation;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFence
/*     */   extends Block
/*     */ {
/*  31 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*     */ 
/*     */   
/*  34 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*     */ 
/*     */   
/*  37 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*     */ 
/*     */   
/*  40 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*  41 */   protected static final AxisAlignedBB[] BOUNDING_BOXES = new AxisAlignedBB[] { new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.625D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.375D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.625D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D) };
/*  42 */   public static final AxisAlignedBB PILLAR_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.5D, 0.625D);
/*  43 */   public static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.625D, 0.625D, 1.5D, 1.0D);
/*  44 */   public static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0.0D, 0.0D, 0.375D, 0.375D, 1.5D, 0.625D);
/*  45 */   public static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0.375D, 0.0D, 0.0D, 0.625D, 1.5D, 0.375D);
/*  46 */   public static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(0.625D, 0.0D, 0.375D, 1.0D, 1.5D, 0.625D);
/*     */ 
/*     */   
/*     */   public BlockFence(Material p_i46395_1_, MapColor p_i46395_2_) {
/*  50 */     super(p_i46395_1_, p_i46395_2_);
/*  51 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)));
/*  52 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
/*  57 */     if (!p_185477_7_)
/*     */     {
/*  59 */       state = state.getActualState((IBlockAccess)worldIn, pos);
/*     */     }
/*     */     
/*  62 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, PILLAR_AABB);
/*     */     
/*  64 */     if (((Boolean)state.getValue((IProperty)NORTH)).booleanValue())
/*     */     {
/*  66 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, NORTH_AABB);
/*     */     }
/*     */     
/*  69 */     if (((Boolean)state.getValue((IProperty)EAST)).booleanValue())
/*     */     {
/*  71 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, EAST_AABB);
/*     */     }
/*     */     
/*  74 */     if (((Boolean)state.getValue((IProperty)SOUTH)).booleanValue())
/*     */     {
/*  76 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, SOUTH_AABB);
/*     */     }
/*     */     
/*  79 */     if (((Boolean)state.getValue((IProperty)WEST)).booleanValue())
/*     */     {
/*  81 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, WEST_AABB);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  87 */     state = getActualState(state, source, pos);
/*  88 */     return BOUNDING_BOXES[getBoundingBoxIdx(state)];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static int getBoundingBoxIdx(IBlockState state) {
/*  96 */     int i = 0;
/*     */     
/*  98 */     if (((Boolean)state.getValue((IProperty)NORTH)).booleanValue())
/*     */     {
/* 100 */       i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
/*     */     }
/*     */     
/* 103 */     if (((Boolean)state.getValue((IProperty)EAST)).booleanValue())
/*     */     {
/* 105 */       i |= 1 << EnumFacing.EAST.getHorizontalIndex();
/*     */     }
/*     */     
/* 108 */     if (((Boolean)state.getValue((IProperty)SOUTH)).booleanValue())
/*     */     {
/* 110 */       i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
/*     */     }
/*     */     
/* 113 */     if (((Boolean)state.getValue((IProperty)WEST)).booleanValue())
/*     */     {
/* 115 */       i |= 1 << EnumFacing.WEST.getHorizontalIndex();
/*     */     }
/*     */     
/* 118 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 126 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 131 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/* 136 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos, EnumFacing p_176524_3_) {
/* 141 */     IBlockState iblockstate = worldIn.getBlockState(pos);
/* 142 */     BlockFaceShape blockfaceshape = iblockstate.func_193401_d(worldIn, pos, p_176524_3_);
/* 143 */     Block block = iblockstate.getBlock();
/* 144 */     boolean flag = (blockfaceshape == BlockFaceShape.MIDDLE_POLE && (iblockstate.getMaterial() == this.blockMaterial || block instanceof BlockFenceGate));
/* 145 */     return !((func_194142_e(block) || blockfaceshape != BlockFaceShape.SOLID) && !flag);
/*     */   }
/*     */ 
/*     */   
/*     */   protected static boolean func_194142_e(Block p_194142_0_) {
/* 150 */     return !(!Block.func_193382_c(p_194142_0_) && p_194142_0_ != Blocks.BARRIER && p_194142_0_ != Blocks.MELON_BLOCK && p_194142_0_ != Blocks.PUMPKIN && p_194142_0_ != Blocks.LIT_PUMPKIN);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 155 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing heldItem, float side, float hitX, float hitY) {
/* 160 */     if (!worldIn.isRemote)
/*     */     {
/* 162 */       return ItemLead.attachToFence(playerIn, worldIn, pos);
/*     */     }
/*     */ 
/*     */     
/* 166 */     ItemStack itemstack = playerIn.getHeldItem(hand);
/* 167 */     return !(itemstack.getItem() != Items.LEAD && !itemstack.func_190926_b());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 176 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/* 185 */     return state.withProperty((IProperty)NORTH, Boolean.valueOf(canConnectTo(worldIn, pos.north(), EnumFacing.SOUTH))).withProperty((IProperty)EAST, Boolean.valueOf(canConnectTo(worldIn, pos.east(), EnumFacing.WEST))).withProperty((IProperty)SOUTH, Boolean.valueOf(canConnectTo(worldIn, pos.south(), EnumFacing.NORTH))).withProperty((IProperty)WEST, Boolean.valueOf(canConnectTo(worldIn, pos.west(), EnumFacing.EAST)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withRotation(IBlockState state, Rotation rot) {
/* 194 */     switch (rot) {
/*     */       
/*     */       case null:
/* 197 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)SOUTH)).withProperty((IProperty)EAST, state.getValue((IProperty)WEST)).withProperty((IProperty)SOUTH, state.getValue((IProperty)NORTH)).withProperty((IProperty)WEST, state.getValue((IProperty)EAST));
/*     */       
/*     */       case COUNTERCLOCKWISE_90:
/* 200 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)EAST)).withProperty((IProperty)EAST, state.getValue((IProperty)SOUTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)WEST)).withProperty((IProperty)WEST, state.getValue((IProperty)NORTH));
/*     */       
/*     */       case CLOCKWISE_90:
/* 203 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)WEST)).withProperty((IProperty)EAST, state.getValue((IProperty)NORTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)EAST)).withProperty((IProperty)WEST, state.getValue((IProperty)SOUTH));
/*     */     } 
/*     */     
/* 206 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
/* 216 */     switch (mirrorIn) {
/*     */       
/*     */       case LEFT_RIGHT:
/* 219 */         return state.withProperty((IProperty)NORTH, state.getValue((IProperty)SOUTH)).withProperty((IProperty)SOUTH, state.getValue((IProperty)NORTH));
/*     */       
/*     */       case null:
/* 222 */         return state.withProperty((IProperty)EAST, state.getValue((IProperty)WEST)).withProperty((IProperty)WEST, state.getValue((IProperty)EAST));
/*     */     } 
/*     */     
/* 225 */     return super.withMirror(state, mirrorIn);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 231 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)NORTH, (IProperty)EAST, (IProperty)WEST, (IProperty)SOUTH });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 236 */     return (p_193383_4_ != EnumFacing.UP && p_193383_4_ != EnumFacing.DOWN) ? BlockFaceShape.MIDDLE_POLE : BlockFaceShape.CENTER;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockFence.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */