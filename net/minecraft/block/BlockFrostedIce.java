/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.properties.PropertyInteger;
/*     */ import net.minecraft.block.state.BlockStateContainer;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.util.math.MathHelper;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFrostedIce
/*     */   extends BlockIce {
/*  16 */   public static final PropertyInteger AGE = PropertyInteger.create("age", 0, 3);
/*     */ 
/*     */   
/*     */   public BlockFrostedIce() {
/*  20 */     setDefaultState(this.blockState.getBaseState().withProperty((IProperty)AGE, Integer.valueOf(0)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMetaFromState(IBlockState state) {
/*  28 */     return ((Integer)state.getValue((IProperty)AGE)).intValue();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IBlockState getStateFromMeta(int meta) {
/*  36 */     return getDefaultState().withProperty((IProperty)AGE, Integer.valueOf(MathHelper.clamp(meta, 0, 3)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  41 */     if ((rand.nextInt(3) == 0 || countNeighbors(worldIn, pos) < 4) && worldIn.getLightFromNeighbors(pos) > 11 - ((Integer)state.getValue((IProperty)AGE)).intValue() - state.getLightOpacity()) {
/*     */       
/*  43 */       slightlyMelt(worldIn, pos, state, rand, true);
/*     */     }
/*     */     else {
/*     */       
/*  47 */       worldIn.scheduleUpdate(pos, this, MathHelper.getInt(rand, 20, 40));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/*  58 */     if (blockIn == this) {
/*     */       
/*  60 */       int i = countNeighbors(worldIn, pos);
/*     */       
/*  62 */       if (i < 2)
/*     */       {
/*  64 */         turnIntoWater(worldIn, pos);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private int countNeighbors(World p_185680_1_, BlockPos p_185680_2_) {
/*  71 */     int i = 0; byte b; int j;
/*     */     EnumFacing[] arrayOfEnumFacing;
/*  73 */     for (j = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/*  75 */       if (p_185680_1_.getBlockState(p_185680_2_.offset(enumfacing)).getBlock() == this) {
/*     */         
/*  77 */         i++;
/*     */         
/*  79 */         if (i >= 4)
/*     */         {
/*  81 */           return i;
/*     */         }
/*     */       } 
/*     */       b++; }
/*     */     
/*  86 */     return i;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void slightlyMelt(World p_185681_1_, BlockPos p_185681_2_, IBlockState p_185681_3_, Random p_185681_4_, boolean p_185681_5_) {
/*  91 */     int i = ((Integer)p_185681_3_.getValue((IProperty)AGE)).intValue();
/*     */     
/*  93 */     if (i < 3) {
/*     */       
/*  95 */       p_185681_1_.setBlockState(p_185681_2_, p_185681_3_.withProperty((IProperty)AGE, Integer.valueOf(i + 1)), 2);
/*  96 */       p_185681_1_.scheduleUpdate(p_185681_2_, this, MathHelper.getInt(p_185681_4_, 20, 40));
/*     */     }
/*     */     else {
/*     */       
/* 100 */       turnIntoWater(p_185681_1_, p_185681_2_);
/*     */       
/* 102 */       if (p_185681_5_) {
/*     */         byte b; int j; EnumFacing[] arrayOfEnumFacing;
/* 104 */         for (j = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < j; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */           
/* 106 */           BlockPos blockpos = p_185681_2_.offset(enumfacing);
/* 107 */           IBlockState iblockstate = p_185681_1_.getBlockState(blockpos);
/*     */           
/* 109 */           if (iblockstate.getBlock() == this)
/*     */           {
/* 111 */             slightlyMelt(p_185681_1_, blockpos, iblockstate, p_185681_4_, false);
/*     */           }
/*     */           b++; }
/*     */       
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected BlockStateContainer createBlockState() {
/* 120 */     return new BlockStateContainer(this, new IProperty[] { (IProperty)AGE });
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state) {
/* 125 */     return ItemStack.field_190927_a;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockFrostedIce.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */