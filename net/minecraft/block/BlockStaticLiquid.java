/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockStaticLiquid
/*     */   extends BlockLiquid {
/*     */   protected BlockStaticLiquid(Material materialIn) {
/*  15 */     super(materialIn);
/*  16 */     setTickRandomly(false);
/*     */     
/*  18 */     if (materialIn == Material.LAVA)
/*     */     {
/*  20 */       setTickRandomly(true);
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
/*  31 */     if (!checkForMixing(worldIn, pos, state))
/*     */     {
/*  33 */       updateLiquid(worldIn, pos, state);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateLiquid(World worldIn, BlockPos pos, IBlockState state) {
/*  39 */     BlockDynamicLiquid blockdynamicliquid = getFlowingBlock(this.blockMaterial);
/*  40 */     worldIn.setBlockState(pos, blockdynamicliquid.getDefaultState().withProperty((IProperty)LEVEL, state.getValue((IProperty)LEVEL)), 2);
/*  41 */     worldIn.scheduleUpdate(pos, blockdynamicliquid, tickRate(worldIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  46 */     if (this.blockMaterial == Material.LAVA)
/*     */     {
/*  48 */       if (worldIn.getGameRules().getBoolean("doFireTick")) {
/*     */         
/*  50 */         int i = rand.nextInt(3);
/*     */         
/*  52 */         if (i > 0) {
/*     */           
/*  54 */           BlockPos blockpos = pos;
/*     */           
/*  56 */           for (int j = 0; j < i; j++) {
/*     */             
/*  58 */             blockpos = blockpos.add(rand.nextInt(3) - 1, 1, rand.nextInt(3) - 1);
/*     */             
/*  60 */             if (blockpos.getY() >= 0 && blockpos.getY() < 256 && !worldIn.isBlockLoaded(blockpos)) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  65 */             Block block = worldIn.getBlockState(blockpos).getBlock();
/*     */             
/*  67 */             if (block.blockMaterial == Material.AIR) {
/*     */               
/*  69 */               if (isSurroundingBlockFlammable(worldIn, blockpos)) {
/*     */                 
/*  71 */                 worldIn.setBlockState(blockpos, Blocks.FIRE.getDefaultState());
/*     */                 
/*     */                 return;
/*     */               } 
/*  75 */             } else if (block.blockMaterial.blocksMovement()) {
/*     */               
/*     */               return;
/*     */             }
/*     */           
/*     */           } 
/*     */         } else {
/*     */           
/*  83 */           for (int k = 0; k < 3; k++) {
/*     */             
/*  85 */             BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, 0, rand.nextInt(3) - 1);
/*     */             
/*  87 */             if (blockpos1.getY() >= 0 && blockpos1.getY() < 256 && !worldIn.isBlockLoaded(blockpos1)) {
/*     */               return;
/*     */             }
/*     */ 
/*     */             
/*  92 */             if (worldIn.isAirBlock(blockpos1.up()) && getCanBlockBurn(worldIn, blockpos1))
/*     */             {
/*  94 */               worldIn.setBlockState(blockpos1.up(), Blocks.FIRE.getDefaultState()); } 
/*     */           } 
/*     */         } 
/*     */       }  } 
/*     */   }
/*     */   
/*     */   protected boolean isSurroundingBlockFlammable(World worldIn, BlockPos pos) {
/*     */     byte b;
/*     */     int i;
/*     */     EnumFacing[] arrayOfEnumFacing;
/* 104 */     for (i = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < i; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*     */       
/* 106 */       if (getCanBlockBurn(worldIn, pos.offset(enumfacing)))
/*     */       {
/* 108 */         return true;
/*     */       }
/*     */       b++; }
/*     */     
/* 112 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean getCanBlockBurn(World worldIn, BlockPos pos) {
/* 117 */     return (pos.getY() >= 0 && pos.getY() < 256 && !worldIn.isBlockLoaded(pos)) ? false : worldIn.getBlockState(pos).getMaterial().getCanBurn();
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockStaticLiquid.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */