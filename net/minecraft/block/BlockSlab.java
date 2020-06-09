/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.MapColor;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyEnum;
/*     */ import net.minecraft.block.state.BlockFaceShape;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.entity.EntityLivingBase;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IStringSerializable;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public abstract class BlockSlab
/*     */   extends Block {
/*  22 */   public static final PropertyEnum<EnumBlockHalf> HALF = PropertyEnum.create("half", EnumBlockHalf.class);
/*  23 */   protected static final AxisAlignedBB AABB_BOTTOM_HALF = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
/*  24 */   protected static final AxisAlignedBB AABB_TOP_HALF = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
/*     */ 
/*     */   
/*     */   public BlockSlab(Material materialIn) {
/*  28 */     this(materialIn, materialIn.getMaterialMapColor());
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockSlab(Material p_i47249_1_, MapColor p_i47249_2_) {
/*  33 */     super(p_i47249_1_, p_i47249_2_);
/*  34 */     this.fullBlock = isDouble();
/*  35 */     setLightOpacity(255);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean canSilkHarvest() {
/*  40 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  45 */     if (isDouble())
/*     */     {
/*  47 */       return FULL_BLOCK_AABB;
/*     */     }
/*     */ 
/*     */     
/*  51 */     return (state.getValue((IProperty)HALF) == EnumBlockHalf.TOP) ? AABB_TOP_HALF : AABB_BOTTOM_HALF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFullyOpaque(IBlockState state) {
/*  60 */     return !(!((BlockSlab)state.getBlock()).isDouble() && state.getValue((IProperty)HALF) != EnumBlockHalf.TOP);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFaceShape func_193383_a(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
/*  65 */     if (((BlockSlab)p_193383_2_.getBlock()).isDouble())
/*     */     {
/*  67 */       return BlockFaceShape.SOLID;
/*     */     }
/*  69 */     if (p_193383_4_ == EnumFacing.UP && p_193383_2_.getValue((IProperty)HALF) == EnumBlockHalf.TOP)
/*     */     {
/*  71 */       return BlockFaceShape.SOLID;
/*     */     }
/*     */ 
/*     */     
/*  75 */     return (p_193383_4_ == EnumFacing.DOWN && p_193383_2_.getValue((IProperty)HALF) == EnumBlockHalf.BOTTOM) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpaqueCube(IBlockState state) {
/*  84 */     return isDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState onBlockPlaced(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
/*  93 */     IBlockState iblockstate = super.onBlockPlaced(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty((IProperty)HALF, EnumBlockHalf.BOTTOM);
/*     */     
/*  95 */     if (isDouble())
/*     */     {
/*  97 */       return iblockstate;
/*     */     }
/*     */ 
/*     */     
/* 101 */     return (facing != EnumFacing.DOWN && (facing == EnumFacing.UP || hitY <= 0.5D)) ? iblockstate : iblockstate.withProperty((IProperty)HALF, EnumBlockHalf.TOP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int quantityDropped(Random random) {
/* 110 */     return isDouble() ? 2 : 1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullCube(IBlockState state) {
/* 115 */     return isDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
/* 120 */     if (isDouble())
/*     */     {
/* 122 */       return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
/*     */     }
/* 124 */     if (side != EnumFacing.UP && side != EnumFacing.DOWN && !super.shouldSideBeRendered(blockState, blockAccess, pos, side))
/*     */     {
/* 126 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 130 */     IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
/* 131 */     boolean flag = (isHalfSlab(iblockstate) && iblockstate.getValue((IProperty)HALF) == EnumBlockHalf.TOP);
/* 132 */     boolean flag1 = (isHalfSlab(blockState) && blockState.getValue((IProperty)HALF) == EnumBlockHalf.TOP);
/*     */     
/* 134 */     if (flag1) {
/*     */       
/* 136 */       if (side == EnumFacing.DOWN)
/*     */       {
/* 138 */         return true;
/*     */       }
/* 140 */       if (side == EnumFacing.UP && super.shouldSideBeRendered(blockState, blockAccess, pos, side))
/*     */       {
/* 142 */         return true;
/*     */       }
/*     */ 
/*     */       
/* 146 */       return !(isHalfSlab(iblockstate) && flag);
/*     */     } 
/*     */     
/* 149 */     if (side == EnumFacing.UP)
/*     */     {
/* 151 */       return true;
/*     */     }
/* 153 */     if (side == EnumFacing.DOWN && super.shouldSideBeRendered(blockState, blockAccess, pos, side))
/*     */     {
/* 155 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 159 */     return !(isHalfSlab(iblockstate) && !flag);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static boolean isHalfSlab(IBlockState state) {
/* 166 */     Block block = state.getBlock();
/* 167 */     return !(block != Blocks.STONE_SLAB && block != Blocks.WOODEN_SLAB && block != Blocks.STONE_SLAB2 && block != Blocks.PURPUR_SLAB);
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract String getUnlocalizedName(int paramInt);
/*     */ 
/*     */   
/*     */   public abstract boolean isDouble();
/*     */   
/*     */   public abstract IProperty<?> getVariantProperty();
/*     */   
/*     */   public abstract Comparable<?> getTypeForItem(ItemStack paramItemStack);
/*     */   
/*     */   public enum EnumBlockHalf
/*     */     implements IStringSerializable
/*     */   {
/* 183 */     TOP("top"),
/* 184 */     BOTTOM("bottom");
/*     */     
/*     */     private final String name;
/*     */ 
/*     */     
/*     */     EnumBlockHalf(String name) {
/* 190 */       this.name = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String toString() {
/* 195 */       return this.name;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getName() {
/* 200 */       return this.name;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockSlab.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */