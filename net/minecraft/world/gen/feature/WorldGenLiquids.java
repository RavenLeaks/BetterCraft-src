/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.Block;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ 
/*    */ public class WorldGenLiquids
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final Block block;
/*    */   
/*    */   public WorldGenLiquids(Block blockIn) {
/* 17 */     this.block = blockIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 22 */     if (worldIn.getBlockState(position.up()).getBlock() != Blocks.STONE)
/*    */     {
/* 24 */       return false;
/*    */     }
/* 26 */     if (worldIn.getBlockState(position.down()).getBlock() != Blocks.STONE)
/*    */     {
/* 28 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 32 */     IBlockState iblockstate = worldIn.getBlockState(position);
/*    */     
/* 34 */     if (iblockstate.getMaterial() != Material.AIR && iblockstate.getBlock() != Blocks.STONE)
/*    */     {
/* 36 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 40 */     int i = 0;
/*    */     
/* 42 */     if (worldIn.getBlockState(position.west()).getBlock() == Blocks.STONE)
/*    */     {
/* 44 */       i++;
/*    */     }
/*    */     
/* 47 */     if (worldIn.getBlockState(position.east()).getBlock() == Blocks.STONE)
/*    */     {
/* 49 */       i++;
/*    */     }
/*    */     
/* 52 */     if (worldIn.getBlockState(position.north()).getBlock() == Blocks.STONE)
/*    */     {
/* 54 */       i++;
/*    */     }
/*    */     
/* 57 */     if (worldIn.getBlockState(position.south()).getBlock() == Blocks.STONE)
/*    */     {
/* 59 */       i++;
/*    */     }
/*    */     
/* 62 */     int j = 0;
/*    */     
/* 64 */     if (worldIn.isAirBlock(position.west()))
/*    */     {
/* 66 */       j++;
/*    */     }
/*    */     
/* 69 */     if (worldIn.isAirBlock(position.east()))
/*    */     {
/* 71 */       j++;
/*    */     }
/*    */     
/* 74 */     if (worldIn.isAirBlock(position.north()))
/*    */     {
/* 76 */       j++;
/*    */     }
/*    */     
/* 79 */     if (worldIn.isAirBlock(position.south()))
/*    */     {
/* 81 */       j++;
/*    */     }
/*    */     
/* 84 */     if (i == 3 && j == 1) {
/*    */       
/* 86 */       IBlockState iblockstate1 = this.block.getDefaultState();
/* 87 */       worldIn.setBlockState(position, iblockstate1, 2);
/* 88 */       worldIn.immediateBlockTick(position, iblockstate1, rand);
/*    */     } 
/*    */     
/* 91 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenLiquids.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */