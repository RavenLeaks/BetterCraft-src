/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockBush;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenBush
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final BlockBush block;
/*    */   
/*    */   public WorldGenBush(BlockBush blockIn) {
/* 14 */     this.block = blockIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 19 */     for (int i = 0; i < 64; i++) {
/*    */       
/* 21 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 23 */       if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.getHasNoSky() || blockpos.getY() < 255) && this.block.canBlockStay(worldIn, blockpos, this.block.getDefaultState()))
/*    */       {
/* 25 */         worldIn.setBlockState(blockpos, this.block.getDefaultState(), 2);
/*    */       }
/*    */     } 
/*    */     
/* 29 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenBush.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */