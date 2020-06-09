/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockFlower;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenFlowers
/*    */   extends WorldGenerator
/*    */ {
/*    */   private BlockFlower flower;
/*    */   private IBlockState state;
/*    */   
/*    */   public WorldGenFlowers(BlockFlower flowerIn, BlockFlower.EnumFlowerType type) {
/* 16 */     setGeneratedBlock(flowerIn, type);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setGeneratedBlock(BlockFlower flowerIn, BlockFlower.EnumFlowerType typeIn) {
/* 21 */     this.flower = flowerIn;
/* 22 */     this.state = flowerIn.getDefaultState().withProperty(flowerIn.getTypeProperty(), (Comparable)typeIn);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 27 */     for (int i = 0; i < 64; i++) {
/*    */       
/* 29 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 31 */       if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.getHasNoSky() || blockpos.getY() < 255) && this.flower.canBlockStay(worldIn, blockpos, this.state))
/*    */       {
/* 33 */         worldIn.setBlockState(blockpos, this.state, 2);
/*    */       }
/*    */     } 
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenFlowers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */