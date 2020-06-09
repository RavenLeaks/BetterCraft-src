/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenClay
/*    */   extends WorldGenerator {
/* 12 */   private final Block block = Blocks.CLAY;
/*    */ 
/*    */   
/*    */   private final int numberOfBlocks;
/*    */ 
/*    */   
/*    */   public WorldGenClay(int p_i2011_1_) {
/* 19 */     this.numberOfBlocks = p_i2011_1_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 24 */     if (worldIn.getBlockState(position).getMaterial() != Material.WATER)
/*    */     {
/* 26 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 30 */     int i = rand.nextInt(this.numberOfBlocks - 2) + 2;
/* 31 */     int j = 1;
/*    */     
/* 33 */     for (int k = position.getX() - i; k <= position.getX() + i; k++) {
/*    */       
/* 35 */       for (int l = position.getZ() - i; l <= position.getZ() + i; l++) {
/*    */         
/* 37 */         int i1 = k - position.getX();
/* 38 */         int j1 = l - position.getZ();
/*    */         
/* 40 */         if (i1 * i1 + j1 * j1 <= i * i)
/*    */         {
/* 42 */           for (int k1 = position.getY() - 1; k1 <= position.getY() + 1; k1++) {
/*    */             
/* 44 */             BlockPos blockpos = new BlockPos(k, k1, l);
/* 45 */             Block block = worldIn.getBlockState(blockpos).getBlock();
/*    */             
/* 47 */             if (block == Blocks.DIRT || block == Blocks.CLAY)
/*    */             {
/* 49 */               worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 56 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenClay.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */