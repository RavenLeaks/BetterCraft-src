/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.BlockTallGrass;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.properties.IProperty;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenTallGrass
/*    */   extends WorldGenerator {
/*    */   private final IBlockState tallGrassState;
/*    */   
/*    */   public WorldGenTallGrass(BlockTallGrass.EnumType p_i45629_1_) {
/* 17 */     this.tallGrassState = Blocks.TALLGRASS.getDefaultState().withProperty((IProperty)BlockTallGrass.TYPE, (Comparable)p_i45629_1_);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 22 */     for (IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES) && position.getY() > 0; iblockstate = worldIn.getBlockState(position))
/*    */     {
/* 24 */       position = position.down();
/*    */     }
/*    */     
/* 27 */     for (int i = 0; i < 128; i++) {
/*    */       
/* 29 */       BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
/*    */       
/* 31 */       if (worldIn.isAirBlock(blockpos) && Blocks.TALLGRASS.canBlockStay(worldIn, blockpos, this.tallGrassState))
/*    */       {
/* 33 */         worldIn.setBlockState(blockpos, this.tallGrassState, 2);
/*    */       }
/*    */     } 
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenTallGrass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */