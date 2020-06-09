/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenCactus
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 12 */     for (int i = 0; i < 10; i++) {
/*    */       
/* 14 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 16 */       if (worldIn.isAirBlock(blockpos)) {
/*    */         
/* 18 */         int j = 1 + rand.nextInt(rand.nextInt(3) + 1);
/*    */         
/* 20 */         for (int k = 0; k < j; k++) {
/*    */           
/* 22 */           if (Blocks.CACTUS.canBlockStay(worldIn, blockpos))
/*    */           {
/* 24 */             worldIn.setBlockState(blockpos.up(k), Blocks.CACTUS.getDefaultState(), 2);
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 30 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenCactus.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */