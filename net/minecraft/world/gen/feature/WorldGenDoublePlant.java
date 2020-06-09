/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockDoublePlant;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenDoublePlant
/*    */   extends WorldGenerator
/*    */ {
/*    */   private BlockDoublePlant.EnumPlantType plantType;
/*    */   
/*    */   public void setPlantType(BlockDoublePlant.EnumPlantType plantTypeIn) {
/* 15 */     this.plantType = plantTypeIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 20 */     boolean flag = false;
/*    */     
/* 22 */     for (int i = 0; i < 64; i++) {
/*    */       
/* 24 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 26 */       if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.getHasNoSky() || blockpos.getY() < 254) && Blocks.DOUBLE_PLANT.canPlaceBlockAt(worldIn, blockpos)) {
/*    */         
/* 28 */         Blocks.DOUBLE_PLANT.placeAt(worldIn, blockpos, this.plantType, 2);
/* 29 */         flag = true;
/*    */       } 
/*    */     } 
/*    */     
/* 33 */     return flag;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenDoublePlant.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */