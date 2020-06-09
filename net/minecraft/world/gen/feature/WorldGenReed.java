/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenReed
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 13 */     for (int i = 0; i < 20; i++) {
/*    */       
/* 15 */       BlockPos blockpos = position.add(rand.nextInt(4) - rand.nextInt(4), 0, rand.nextInt(4) - rand.nextInt(4));
/*    */       
/* 17 */       if (worldIn.isAirBlock(blockpos)) {
/*    */         
/* 19 */         BlockPos blockpos1 = blockpos.down();
/*    */         
/* 21 */         if (worldIn.getBlockState(blockpos1.west()).getMaterial() == Material.WATER || worldIn.getBlockState(blockpos1.east()).getMaterial() == Material.WATER || worldIn.getBlockState(blockpos1.north()).getMaterial() == Material.WATER || worldIn.getBlockState(blockpos1.south()).getMaterial() == Material.WATER) {
/*    */           
/* 23 */           int j = 2 + rand.nextInt(rand.nextInt(3) + 1);
/*    */           
/* 25 */           for (int k = 0; k < j; k++) {
/*    */             
/* 27 */             if (Blocks.REEDS.canBlockStay(worldIn, blockpos))
/*    */             {
/* 29 */               worldIn.setBlockState(blockpos.up(k), Blocks.REEDS.getDefaultState(), 2);
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 36 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenReed.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */