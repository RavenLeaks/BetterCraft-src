/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenWaterlily
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 12 */     for (int i = 0; i < 10; i++) {
/*    */       
/* 14 */       int j = position.getX() + rand.nextInt(8) - rand.nextInt(8);
/* 15 */       int k = position.getY() + rand.nextInt(4) - rand.nextInt(4);
/* 16 */       int l = position.getZ() + rand.nextInt(8) - rand.nextInt(8);
/*    */       
/* 18 */       if (worldIn.isAirBlock(new BlockPos(j, k, l)) && Blocks.WATERLILY.canPlaceBlockAt(worldIn, new BlockPos(j, k, l)))
/*    */       {
/* 20 */         worldIn.setBlockState(new BlockPos(j, k, l), Blocks.WATERLILY.getDefaultState(), 2);
/*    */       }
/*    */     } 
/*    */     
/* 24 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenWaterlily.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */