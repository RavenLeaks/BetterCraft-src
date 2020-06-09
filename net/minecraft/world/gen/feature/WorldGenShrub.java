/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenShrub
/*    */   extends WorldGenTrees
/*    */ {
/*    */   private final IBlockState leavesMetadata;
/*    */   private final IBlockState woodMetadata;
/*    */   
/*    */   public WorldGenShrub(IBlockState p_i46450_1_, IBlockState p_i46450_2_) {
/* 18 */     super(false);
/* 19 */     this.woodMetadata = p_i46450_1_;
/* 20 */     this.leavesMetadata = p_i46450_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 25 */     for (IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES) && position.getY() > 0; iblockstate = worldIn.getBlockState(position))
/*    */     {
/* 27 */       position = position.down();
/*    */     }
/*    */     
/* 30 */     Block block = worldIn.getBlockState(position).getBlock();
/*    */     
/* 32 */     if (block == Blocks.DIRT || block == Blocks.GRASS) {
/*    */       
/* 34 */       position = position.up();
/* 35 */       setBlockAndNotifyAdequately(worldIn, position, this.woodMetadata);
/*    */       
/* 37 */       for (int i = position.getY(); i <= position.getY() + 2; i++) {
/*    */         
/* 39 */         int j = i - position.getY();
/* 40 */         int k = 2 - j;
/*    */         
/* 42 */         for (int l = position.getX() - k; l <= position.getX() + k; l++) {
/*    */           
/* 44 */           int i1 = l - position.getX();
/*    */           
/* 46 */           for (int j1 = position.getZ() - k; j1 <= position.getZ() + k; j1++) {
/*    */             
/* 48 */             int k1 = j1 - position.getZ();
/*    */             
/* 50 */             if (Math.abs(i1) != k || Math.abs(k1) != k || rand.nextInt(2) != 0) {
/*    */               
/* 52 */               BlockPos blockpos = new BlockPos(l, i, j1);
/* 53 */               Material material = worldIn.getBlockState(blockpos).getMaterial();
/*    */               
/* 55 */               if (material == Material.AIR || material == Material.LEAVES)
/*    */               {
/* 57 */                 setBlockAndNotifyAdequately(worldIn, blockpos, this.leavesMetadata);
/*    */               }
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 65 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenShrub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */