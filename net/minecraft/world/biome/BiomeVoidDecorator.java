/*    */ package net.minecraft.world.biome;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.Vec3i;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class BiomeVoidDecorator
/*    */   extends BiomeDecorator {
/*    */   public void decorate(World worldIn, Random random, Biome biome, BlockPos pos) {
/* 12 */     BlockPos blockpos = worldIn.getSpawnPoint();
/* 13 */     int i = 16;
/* 14 */     double d0 = blockpos.distanceSq((Vec3i)pos.add(8, blockpos.getY(), 8));
/*    */     
/* 16 */     if (d0 <= 1024.0D) {
/*    */       
/* 18 */       BlockPos blockpos1 = new BlockPos(blockpos.getX() - 16, blockpos.getY() - 1, blockpos.getZ() - 16);
/* 19 */       BlockPos blockpos2 = new BlockPos(blockpos.getX() + 16, blockpos.getY() - 1, blockpos.getZ() + 16);
/* 20 */       BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos(blockpos1);
/*    */       
/* 22 */       for (int j = pos.getZ(); j < pos.getZ() + 16; j++) {
/*    */         
/* 24 */         for (int k = pos.getX(); k < pos.getX() + 16; k++) {
/*    */           
/* 26 */           if (j >= blockpos1.getZ() && j <= blockpos2.getZ() && k >= blockpos1.getX() && k <= blockpos2.getX()) {
/*    */             
/* 28 */             blockpos$mutableblockpos.setPos(k, blockpos$mutableblockpos.getY(), j);
/*    */             
/* 30 */             if (blockpos.getX() == k && blockpos.getZ() == j) {
/*    */               
/* 32 */               worldIn.setBlockState((BlockPos)blockpos$mutableblockpos, Blocks.COBBLESTONE.getDefaultState(), 2);
/*    */             }
/*    */             else {
/*    */               
/* 36 */               worldIn.setBlockState((BlockPos)blockpos$mutableblockpos, Blocks.STONE.getDefaultState(), 2);
/*    */             } 
/*    */           } 
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\biome\BiomeVoidDecorator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */