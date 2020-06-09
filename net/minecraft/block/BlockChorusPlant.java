/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Random;
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
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockChorusPlant
/*     */   extends Block {
/*  27 */   public static final PropertyBool NORTH = PropertyBool.create("north");
/*  28 */   public static final PropertyBool EAST = PropertyBool.create("east");
/*  29 */   public static final PropertyBool SOUTH = PropertyBool.create("south");
/*  30 */   public static final PropertyBool WEST = PropertyBool.create("west");
/*  31 */   public static final PropertyBool UP = PropertyBool.create("up");
/*  32 */   public static final PropertyBool DOWN = PropertyBool.create("down");
/*     */ 
/*     */   
/*     */   protected BlockChorusPlant() {
/*  36 */     super(Material.PLANTS, MapColor.PURPLE);
/*  37 */     setCreativeTab(CreativeTabs.DECORATIONS);
/*  38 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)NORTH, Boolean.valueOf(false)).withProperty((IProperty)EAST, Boolean.valueOf(false)).withProperty((IProperty)SOUTH, Boolean.valueOf(false)).withProperty((IProperty)WEST, Boolean.valueOf(false)).withProperty((IProperty)UP, Boolean.valueOf(false)).withProperty((IProperty)DOWN, Boolean.valueOf(false)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  47 */     Block block = worldIn.getBlockState(pos.down()).getBlock();
/*  48 */     Block block1 = worldIn.getBlockState(pos.up()).getBlock();
/*  49 */     Block block2 = worldIn.getBlockState(pos.north()).getBlock();
/*  50 */     Block block3 = worldIn.getBlockState(pos.east()).getBlock();
/*  51 */     Block block4 = worldIn.getBlockState(pos.south()).getBlock();
/*  52 */     Block block5 = worldIn.getBlockState(pos.west()).getBlock();
/*  53 */     return state.withProperty((IProperty)DOWN, Boolean.valueOf(!(block != this && block != Blocks.CHORUS_FLOWER && block != Blocks.END_STONE))).withProperty((IProperty)UP, Boolean.valueOf(!(block1 != this && block1 != Blocks.CHORUS_FLOWER))).withProperty((IProperty)NORTH, Boolean.valueOf(!(block2 != this && block2 != Blocks.CHORUS_FLOWER))).withProperty((IProperty)EAST, Boolean.valueOf(!(block3 != this && block3 != Blocks.CHORUS_FLOWER))).withProperty((IProperty)SOUTH, Boolean.valueOf(!(block4 != this && block4 != Blocks.CHORUS_FLOWER))).withProperty((IProperty)WEST, Boolean.valueOf(!(block5 != this && block5 != Blocks.CHORUS_FLOWER)));
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  58 */     state = state.getActualState(source, pos);
/*  59 */     float f = 0.1875F;
/*  60 */     float f1 = ((Boolean)state.getValue((IProperty)WEST)).booleanValue() ? 0.0F : 0.1875F;
/*  61 */     float f2 = ((Boolean)state.getValue((IProperty)DOWN)).booleanValue() ? 0.0F : 0.1875F;
/*  62 */     float f3 = ((Boolean)state.getValue((IProperty)NORTH)).booleanValue() ? 0.0F : 0.1875F;
/*  63 */     float f4 = ((Boolean)state.getValue((IProperty)EAST)).booleanValue() ? 1.0F : 0.8125F;
/*  64 */     float f5 = ((Boolean)state.getValue((IProperty)UP)).booleanValue() ? 1.0F : 0.8125F;
/*  65 */     float f6 = ((Boolean)state.getValue((IProperty)SOUTH)).booleanValue() ? 1.0F : 0.8125F;
/*  66 */     return new AxisAlignedBB(f1, f2, f3, f4, f5, f6);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
/*  71 */     if (!p_185477_7_)
/*     */     {
/*  73 */       state = state.getActualState((IBlockAccess)worldIn, pos);
/*     */     }
/*     */     
/*  76 */     float f = 0.1875F;
/*  77 */     float f1 = 0.8125F;
/*  78 */     addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D));
/*     */     
/*  80 */     if (((Boolean)state.getValue((IProperty)WEST)).booleanValue())
/*     */     {
/*  82 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.0D, 0.1875D, 0.1875D, 0.1875D, 0.8125D, 0.8125D));
/*     */     }
/*     */     
/*  85 */     if (((Boolean)state.getValue((IProperty)EAST)).booleanValue())
/*     */     {
/*  87 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.8125D, 0.1875D, 0.1875D, 1.0D, 0.8125D, 0.8125D));
/*     */     }
/*     */     
/*  90 */     if (((Boolean)state.getValue((IProperty)UP)).booleanValue())
/*     */     {
/*  92 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.8125D, 0.1875D, 0.8125D, 1.0D, 0.8125D));
/*     */     }
/*     */     
/*  95 */     if (((Boolean)state.getValue((IProperty)DOWN)).booleanValue())
/*     */     {
/*  97 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.0D, 0.1875D, 0.8125D, 0.1875D, 0.8125D));
/*     */     }
/*     */     
/* 100 */     if (((Boolean)state.getValue((IProperty)NORTH)).booleanValue())
/*     */     {
/* 102 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.0D, 0.8125D, 0.8125D, 0.1875D));
/*     */     }
/*     */     
/* 105 */     if (((Boolean)state.getValue((IProperty)SOUTH)).booleanValue())
/*     */     {
/* 107 */       addCollisionBoxToList(pos, entityBox, collidingBoxes, new AxisAlignedBB(0.1875D, 0.1875D, 0.8125D, 0.8125D, 0.8125D, 1.0D));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 116 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/* 121 */     if (!canSurviveAt(worldIn, pos))
/*     */     {
/* 123 */       worldIn.destroyBlock(pos, true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 132 */     return Items.CHORUS_FRUIT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 140 */     return random.nextInt(2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 145 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/* 158 */     return super.canPlaceBlockAt(worldIn, pos) ? canSurviveAt(worldIn, pos) : false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 168 */     if (!canSurviveAt(worldIn, pos))
/*     */     {
/* 170 */       worldIn.scheduleUpdate(pos, this, 1);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSurviveAt(World wordIn, BlockPos pos) {
/* 176 */     boolean flag = wordIn.isAirBlock(pos.up());
/* 177 */     boolean flag1 = wordIn.isAirBlock(pos.down());
/*     */     
/* 179 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/* 181 */       BlockPos blockpos = pos.offset(enumfacing);
/* 182 */       Block block = wordIn.getBlockState(blockpos).getBlock();
/*     */       
/* 184 */       if (block == this) {
/*     */         
/* 186 */         if (!flag && !flag1)
/*     */         {
/* 188 */           return false;
/*     */         }
/*     */         
/* 191 */         Block block1 = wordIn.getBlockState(blockpos.down()).getBlock();
/*     */         
/* 193 */         if (block1 == this || block1 == Blocks.END_STONE)
/*     */         {
/* 195 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 200 */     Block block2 = wordIn.getBlockState(pos.down()).getBlock();
/* 201 */     return !(block2 != this && block2 != Blocks.END_STONE);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 206 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 211 */     Block block = blockAccess.getBlockState(pos.offset(side)).getBlock();
/* 212 */     return (block != this && block != Blocks.CHORUS_FLOWER && (side != EnumFacing.DOWN || block != Blocks.END_STONE));
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 217 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)NORTH, (IProperty)EAST, (IProperty)SOUTH, (IProperty)WEST, (IProperty)UP, (IProperty)DOWN });
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
/* 222 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 227 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockChorusPlant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */