/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenIcePath
/*    */   extends WorldGenerator {
/* 11 */   private final Block block = Blocks.PACKED_ICE;
/*    */   
/*    */   private final int basePathWidth;
/*    */   
/*    */   public WorldGenIcePath(int basePathWidthIn) {
/* 16 */     this.basePathWidth = basePathWidthIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 21 */     while (worldIn.isAirBlock(position) && position.getY() > 2)
/*    */     {
/* 23 */       position = position.down();
/*    */     }
/*    */     
/* 26 */     if (worldIn.getBlockState(position).getBlock() != Blocks.SNOW)
/*    */     {
/* 28 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 32 */     int i = rand.nextInt(this.basePathWidth - 2) + 2;
/* 33 */     int j = 1;
/*    */     
/* 35 */     for (int k = position.getX() - i; k <= position.getX() + i; k++) {
/*    */       
/* 37 */       for (int l = position.getZ() - i; l <= position.getZ() + i; l++) {
/*    */         
/* 39 */         int i1 = k - position.getX();
/* 40 */         int j1 = l - position.getZ();
/*    */         
/* 42 */         if (i1 * i1 + j1 * j1 <= i * i)
/*    */         {
/* 44 */           for (int k1 = position.getY() - 1; k1 <= position.getY() + 1; k1++) {
/*    */             
/* 46 */             BlockPos blockpos = new BlockPos(k, k1, l);
/* 47 */             Block block = worldIn.getBlockState(blockpos).getBlock();
/*    */             
/* 49 */             if (block == Blocks.DIRT || block == Blocks.SNOW || block == Blocks.ICE)
/*    */             {
/* 51 */               worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 58 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenIcePath.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */