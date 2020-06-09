/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.EnumFacing;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenGlowStone1
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 14 */     if (!worldIn.isAirBlock(position))
/*    */     {
/* 16 */       return false;
/*    */     }
/* 18 */     if (worldIn.getBlockState(position.up()).getBlock() != Blocks.NETHERRACK)
/*    */     {
/* 20 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 24 */     worldIn.setBlockState(position, Blocks.GLOWSTONE.getDefaultState(), 2);
/*    */     
/* 26 */     for (int i = 0; i < 1500; i++) {
/*    */       
/* 28 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), -rand.nextInt(12), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 30 */       if (worldIn.getBlockState(blockpos).getMaterial() == Material.AIR) {
/*    */         
/* 32 */         int j = 0; byte b; int k;
/*    */         EnumFacing[] arrayOfEnumFacing;
/* 34 */         for (k = (arrayOfEnumFacing = EnumFacing.values()).length, b = 0; b < k; ) { EnumFacing enumfacing = arrayOfEnumFacing[b];
/*    */           
/* 36 */           if (worldIn.getBlockState(blockpos.offset(enumfacing)).getBlock() == Blocks.GLOWSTONE)
/*    */           {
/* 38 */             j++;
/*    */           }
/*    */           
/* 41 */           if (j > 1) {
/*    */             break;
/*    */           }
/*    */           
/*    */           b++; }
/*    */         
/* 47 */         if (j == 1)
/*    */         {
/* 49 */           worldIn.setBlockState(blockpos, Blocks.GLOWSTONE.getDefaultState(), 2);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 54 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenGlowStone1.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */