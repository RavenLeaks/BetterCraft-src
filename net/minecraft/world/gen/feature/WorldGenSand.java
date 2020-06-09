/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WorldGenSand
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final Block block;
/*    */   private final int radius;
/*    */   
/*    */   public WorldGenSand(Block p_i45462_1_, int p_i45462_2_) {
/* 19 */     this.block = p_i45462_1_;
/* 20 */     this.radius = p_i45462_2_;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 25 */     if (worldIn.getBlockState(position).getMaterial() != Material.WATER)
/*    */     {
/* 27 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 31 */     int i = rand.nextInt(this.radius - 2) + 2;
/* 32 */     int j = 2;
/*    */     
/* 34 */     for (int k = position.getX() - i; k <= position.getX() + i; k++) {
/*    */       
/* 36 */       for (int l = position.getZ() - i; l <= position.getZ() + i; l++) {
/*    */         
/* 38 */         int i1 = k - position.getX();
/* 39 */         int j1 = l - position.getZ();
/*    */         
/* 41 */         if (i1 * i1 + j1 * j1 <= i * i)
/*    */         {
/* 43 */           for (int k1 = position.getY() - 2; k1 <= position.getY() + 2; k1++) {
/*    */             
/* 45 */             BlockPos blockpos = new BlockPos(k, k1, l);
/* 46 */             Block block = worldIn.getBlockState(blockpos).getBlock();
/*    */             
/* 48 */             if (block == Blocks.DIRT || block == Blocks.GRASS)
/*    */             {
/* 50 */               worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
/*    */             }
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 57 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenSand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */