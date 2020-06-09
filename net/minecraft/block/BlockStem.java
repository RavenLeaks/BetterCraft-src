/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import javax.annotation.Nullable;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyDirection;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.Item;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockStem
/*     */   extends BlockBush
/*     */   implements IGrowable {
/*  25 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 7);
/*  26 */   public static final PropertyDirection FACING = BlockTorch.FACING;
/*     */   private final Block crop;
/*  28 */   protected static final AxisAlignedBB[] STEM_AABB = new AxisAlignedBB[] { new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.125D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.25D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.375D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.5D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.625D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.75D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 0.875D, 0.625D), new AxisAlignedBB(0.375D, 0.0D, 0.375D, 0.625D, 1.0D, 0.625D) };
/*     */ 
/*     */   
/*     */   protected BlockStem(Block crop) {
/*  32 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)).withProperty((IProperty)FACING, (Comparable)EnumFacing.UP));
/*  33 */     this.crop = crop;
/*  34 */     setTickRandomly(true);
/*  35 */     setCreativeTab(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  40 */     return STEM_AABB[((Integer)state.getValue((IProperty)AGE)).intValue()];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
/*  49 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*  50 */     state = state.withProperty((IProperty)FACING, (Comparable)EnumFacing.UP);
/*     */     
/*  52 */     for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */       
/*  54 */       if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this.crop && i == 7) {
/*     */         
/*  56 */         state = state.withProperty((IProperty)FACING, (Comparable)enumfacing);
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/*  61 */     return state;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canSustainBush(IBlockState state) {
/*  69 */     return (state.getBlock() == Blocks.FARMLAND);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  74 */     super.updateTick(worldIn, pos, state, rand);
/*     */     
/*  76 */     if (worldIn.getLightFromNeighbors(pos.up()) >= 9) {
/*     */       
/*  78 */       float f = BlockCrops.getGrowthChance(this, worldIn, pos);
/*     */       
/*  80 */       if (rand.nextInt((int)(25.0F / f) + 1) == 0) {
/*     */         
/*  82 */         int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */         
/*  84 */         if (i < 7) {
/*     */           
/*  86 */           state = state.withProperty((IProperty)AGE, Integer.valueOf(i + 1));
/*  87 */           worldIn.setBlockState(pos, state, 2);
/*     */         }
/*     */         else {
/*     */           
/*  91 */           for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL) {
/*     */             
/*  93 */             if (worldIn.getBlockState(pos.offset(enumfacing)).getBlock() == this.crop) {
/*     */               return;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/*  99 */           pos = pos.offset(EnumFacing.Plane.HORIZONTAL.random(rand));
/* 100 */           Block block = worldIn.getBlockState(pos.down()).getBlock();
/*     */           
/* 102 */           if ((worldIn.getBlockState(pos).getBlock()).blockMaterial == Material.AIR && (block == Blocks.FARMLAND || block == Blocks.DIRT || block == Blocks.GRASS))
/*     */           {
/* 104 */             worldIn.setBlockState(pos, this.crop.getDefaultState());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void growStem(World worldIn, BlockPos pos, IBlockState state) {
/* 113 */     int i = ((Integer)state.getValue((IProperty)AGE)).intValue() + MathHelper.getInt(worldIn.rand, 2, 5);
/* 114 */     worldIn.setBlockState(pos, state.withProperty((IProperty)AGE, Integer.valueOf(Math.min(7, i))), 2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
/* 122 */     super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
/*     */     
/* 124 */     if (!worldIn.isRemote) {
/*     */       
/* 126 */       Item item = getSeedItem();
/*     */       
/* 128 */       if (item != null) {
/*     */         
/* 130 */         int i = ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */         
/* 132 */         for (int j = 0; j < 3; j++) {
/*     */           
/* 134 */           if (worldIn.rand.nextInt(15) <= i)
/*     */           {
/* 136 */             spawnAsEntity(worldIn, pos, new ItemStack(item));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   protected Item getSeedItem() {
/* 146 */     if (this.crop == Blocks.PUMPKIN)
/*     */     {
/* 148 */       return Items.PUMPKIN_SEEDS;
/*     */     }
/*     */ 
/*     */     
/* 152 */     return (this.crop == Blocks.MELON_BLOCK) ? Items.MELON_SEEDS : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Item getItemDropped(IBlockState state, Random rand, int fortune) {
/* 161 */     return Items.field_190931_a;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 166 */     Item item = getSeedItem();
/* 167 */     return (item == null) ? ItemStack.field_190927_a : new ItemStack(item);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 175 */     return (((Integer)state.getValue((IProperty)AGE)).intValue() != 7);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 180 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 185 */     growStem(worldIn, pos, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/* 193 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(meta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/* 201 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 206 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)AGE, (IProperty)FACING });
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockStem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */