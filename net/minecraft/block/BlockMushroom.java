/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.properties.IProperty;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.math.AxisAlignedBB;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import net.minecraft.world.gen.feature.WorldGenBigMushroom;
/*     */ import net.minecraft.world.gen.feature.WorldGenerator;
/*     */ 
/*     */ public class BlockMushroom extends BlockBush implements IGrowable {
/*  15 */   protected static final AxisAlignedBB MUSHROOM_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.4000000059604645D, 0.699999988079071D);
/*     */ 
/*     */   
/*     */   protected BlockMushroom() {
/*  19 */     setTickRandomly(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
/*  24 */     return MUSHROOM_AABB;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  29 */     if (rand.nextInt(25) == 0) {
/*     */       
/*  31 */       int i = 5;
/*  32 */       int j = 4;
/*     */       
/*  34 */       for (BlockPos blockpos : BlockPos.getAllInBoxMutable(pos.add(-4, -1, -4), pos.add(4, 1, 4))) {
/*     */         
/*  36 */         if (worldIn.getBlockState(blockpos).getBlock() == this) {
/*     */           
/*  38 */           i--;
/*     */           
/*  40 */           if (i <= 0) {
/*     */             return;
/*     */           }
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  47 */       BlockPos blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
/*     */       
/*  49 */       for (int k = 0; k < 4; k++) {
/*     */         
/*  51 */         if (worldIn.isAirBlock(blockpos1) && canBlockStay(worldIn, blockpos1, getDefaultState()))
/*     */         {
/*  53 */           pos = blockpos1;
/*     */         }
/*     */         
/*  56 */         blockpos1 = pos.add(rand.nextInt(3) - 1, rand.nextInt(2) - rand.nextInt(2), rand.nextInt(3) - 1);
/*     */       } 
/*     */       
/*  59 */       if (worldIn.isAirBlock(blockpos1) && canBlockStay(worldIn, blockpos1, getDefaultState()))
/*     */       {
/*  61 */         worldIn.setBlockState(blockpos1, getDefaultState(), 2);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
/*  68 */     return (super.canPlaceBlockAt(worldIn, pos) && canBlockStay(worldIn, pos, getDefaultState()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean canSustainBush(IBlockState state) {
/*  76 */     return state.isFullBlock();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
/*  81 */     if (pos.getY() >= 0 && pos.getY() < 256) {
/*     */       
/*  83 */       IBlockState iblockstate = worldIn.getBlockState(pos.down());
/*     */       
/*  85 */       if (iblockstate.getBlock() == Blocks.MYCELIUM)
/*     */       {
/*  87 */         return true;
/*     */       }
/*  89 */       if (iblockstate.getBlock() == Blocks.DIRT && iblockstate.getValue((IProperty)BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL)
/*     */       {
/*  91 */         return true;
/*     */       }
/*     */ 
/*     */       
/*  95 */       return (worldIn.getLight(pos) < 13 && canSustainBush(iblockstate));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 100 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean generateBigMushroom(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*     */     WorldGenBigMushroom worldGenBigMushroom;
/* 106 */     worldIn.setBlockToAir(pos);
/* 107 */     WorldGenerator worldgenerator = null;
/*     */     
/* 109 */     if (this == Blocks.BROWN_MUSHROOM) {
/*     */       
/* 111 */       worldGenBigMushroom = new WorldGenBigMushroom(Blocks.BROWN_MUSHROOM_BLOCK);
/*     */     }
/* 113 */     else if (this == Blocks.RED_MUSHROOM) {
/*     */       
/* 115 */       worldGenBigMushroom = new WorldGenBigMushroom(Blocks.RED_MUSHROOM_BLOCK);
/*     */     } 
/*     */     
/* 118 */     if (worldGenBigMushroom != null && worldGenBigMushroom.generate(worldIn, rand, pos))
/*     */     {
/* 120 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 124 */     worldIn.setBlockState(pos, state, 3);
/* 125 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
/* 134 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 139 */     return (rand.nextFloat() < 0.4D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
/* 144 */     generateBigMushroom(worldIn, pos, state, rand);
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockMushroom.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */