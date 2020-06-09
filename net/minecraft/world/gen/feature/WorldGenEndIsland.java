/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.util.math.MathHelper;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenEndIsland
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 13 */     float f = (rand.nextInt(3) + 4);
/*    */     
/* 15 */     for (int i = 0; f > 0.5F; i--) {
/*    */       
/* 17 */       for (int j = MathHelper.floor(-f); j <= MathHelper.ceil(f); j++) {
/*    */         
/* 19 */         for (int k = MathHelper.floor(-f); k <= MathHelper.ceil(f); k++) {
/*    */           
/* 21 */           if ((j * j + k * k) <= (f + 1.0F) * (f + 1.0F))
/*    */           {
/* 23 */             setBlockAndNotifyAdequately(worldIn, position.add(j, i, k), Blocks.END_STONE.getDefaultState());
/*    */           }
/*    */         } 
/*    */       } 
/*    */       
/* 28 */       f = (float)(f - rand.nextInt(2) + 0.5D);
/*    */     } 
/*    */     
/* 31 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenEndIsland.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */