/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.BlockRenderLayer;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockReed
/*     */   extends Block {
/*  24 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 15);
/*  25 */   protected static final AxisAlignedBB REED_AABB = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 1.0D, 0.875D);
/*     */ 
/*     */   
/*     */   protected BlockReed() {
/*  29 */     super(Material.PLANTS);
/*  30 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)));
/*  31 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  36 */     return REED_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  41 */     if (worldIn.getBlockState(pos.down()).getBlock() == Blocks.REEDS || checkForDrop(worldIn, pos, state))
/*     */     {
/*  43 */       if (worldIn.isAirBlock(pos.up())) {
/*     */         int i;
/*     */ 
/*     */         
/*  47 */         for (i = 1; worldIn.getBlockState(pos.down(i)).getBlock() == this; i++);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  52 */         if (i < 3) {
/*     */           
/*  54 */           int j = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */           
/*  56 */           if (j == 15) {
/*     */             
/*  58 */             worldIn.setBlockState(pos.up(), getDefaultState());
/*  59 */             worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(0)), 4);
/*     */           }
/*     */           else {
/*     */             
/*  63 */             worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(j + 1)), 4);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  72 */     Block block = worldIn.getBlockState(pos.down()).getBlock();
/*     */     
/*  74 */     if (block == this)
/*     */     {
/*  76 */       return true;
/*     */     }
/*  78 */     if (block != Blocks.GRASS && block != Blocks.DIRT && block != Blocks.SAND)
/*     */     {
/*  80 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  84 */     BlockPos blockpos = pos.down();
/*     */     
/*  86 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/*  88 */       IBlockState iblockstate = worldIn.getBlockState(blockpos.offset(enumfacing));
/*     */       
/*  90 */       if (iblockstate.getMaterial() == Material.WATER || iblockstate.getBlock() == Blocks.FROSTED_ICE)
/*     */       {
/*  92 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/* 107 */     checkForDrop(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */   
/*     */   protected final boolean checkForDrop(World worldIn, BlockPos pos, IBlockState state) {
/* 112 */     if (canBlockStay(worldIn, pos))
/*     */     {
/* 114 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 118 */     dropBlockAsItem(worldIn, pos, state, 0);
/* 119 */     worldIn.setBlockToAir(pos);
/* 120 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos) {
/* 126 */     return canPlaceBlockAt(worldIn, pos);
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
/* 132 */     return NULL_AABB;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 140 */     return Items.REEDS;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 153 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 158 */     return new ItemStack(Items.REEDS);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockRenderLayer getBlockLayer() {
/* 163 */     return BlockRenderLayer.CUTOUT;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 171 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 179 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 184 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)AGE });
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/* 189 */     return BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockReed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */