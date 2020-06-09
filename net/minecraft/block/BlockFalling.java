/*     */ package net.minecraft.block;
/*     */ 
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.material.Material;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.creativetab.CreativeTabs;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.item.EntityFallingBlock;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.EnumParticleTypes;
/*     */ import net.minecraft.util.math.BlockPos;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class BlockFalling
/*     */   extends Block {
/*     */   public static boolean fallInstantly;
/*     */   
/*     */   public BlockFalling() {
/*  19 */     super(Material.SAND);
/*  20 */     setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockFalling(Material materialIn) {
/*  25 */     super(materialIn);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
/*  33 */     worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void neighborChanged(IBlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos p_189540_5_) {
/*  43 */     worldIn.scheduleUpdate(pos, this, tickRate(worldIn));
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
/*  48 */     if (!worldIn.isRemote)
/*     */     {
/*  50 */       checkFallable(worldIn, pos);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void checkFallable(World worldIn, BlockPos pos) {
/*  56 */     if (canFallThrough(worldIn.getBlockState(pos.down())) && pos.getY() >= 0) {
/*     */       
/*  58 */       int i = 32;
/*     */       
/*  60 */       if (!fallInstantly && worldIn.isAreaLoaded(pos.add(-32, -32, -32), pos.add(32, 32, 32))) {
/*     */         
/*  62 */         if (!worldIn.isRemote)
/*     */         {
/*  64 */           EntityFallingBlock entityfallingblock = new EntityFallingBlock(worldIn, pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, worldIn.getBlockState(pos));
/*  65 */           onStartFalling(entityfallingblock);
/*  66 */           worldIn.spawnEntityInWorld((Entity)entityfallingblock);
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/*  71 */         worldIn.setBlockToAir(pos);
/*     */         
/*     */         BlockPos blockpos;
/*  74 */         for (blockpos = pos.down(); canFallThrough(worldIn.getBlockState(blockpos)) && blockpos.getY() > 0; blockpos = blockpos.down());
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  79 */         if (blockpos.getY() > 0)
/*     */         {
/*  81 */           worldIn.setBlockState(blockpos.up(), getDefaultState());
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onStartFalling(EntityFallingBlock fallingEntity) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int tickRate(World worldIn) {
/*  96 */     return 2;
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canFallThrough(IBlockState state) {
/* 101 */     Block block = state.getBlock();
/* 102 */     Material material = state.getMaterial();
/* 103 */     return !(block != Blocks.FIRE && material != Material.AIR && material != Material.WATER && material != Material.LAVA);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onEndFalling(World worldIn, BlockPos pos, IBlockState p_176502_3_, IBlockState p_176502_4_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_190974_b(World p_190974_1_, BlockPos p_190974_2_) {}
/*     */ 
/*     */   
/*     */   public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
/* 116 */     if (rand.nextInt(16) == 0) {
/*     */       
/* 118 */       BlockPos blockpos = pos.down();
/*     */       
/* 120 */       if (canFallThrough(worldIn.getBlockState(blockpos))) {
/*     */         
/* 122 */         double d0 = (pos.getX() + rand.nextFloat());
/* 123 */         double d1 = pos.getY() - 0.05D;
/* 124 */         double d2 = (pos.getZ() + rand.nextFloat());
/* 125 */         worldIn.spawnParticle(EnumParticleTypes.FALLING_DUST, d0, d1, d2, 0.0D, 0.0D, 0.0D, new int[] { Block.getStateId(stateIn) });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDustColor(IBlockState p_189876_1_) {
/* 132 */     return -16777216;
/*     */   }
/*     */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\BlockFalling.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */