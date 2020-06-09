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
/*    */ public class WorldGenHellLava
/*    */   extends WorldGenerator
/*    */ {
/*    */   private final Block block;
/*    */   private final boolean insideRock;
/*    */   
/*    */   public WorldGenHellLava(Block blockIn, boolean insideRockIn) {
/* 18 */     this.block = blockIn;
/* 19 */     this.insideRock = insideRockIn;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 24 */     if (worldIn.getBlockState(position.up()).getBlock() != Blocks.NETHERRACK)
/*    */     {
/* 26 */       return false;
/*    */     }
/* 28 */     if (worldIn.getBlockState(position).getMaterial() != Material.AIR && worldIn.getBlockState(position).getBlock() != Blocks.NETHERRACK)
/*    */     {
/* 30 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 34 */     int i = 0;
/*    */     
/* 36 */     if (worldIn.getBlockState(position.west()).getBlock() == Blocks.NETHERRACK)
/*    */     {
/* 38 */       i++;
/*    */     }
/*    */     
/* 41 */     if (worldIn.getBlockState(position.east()).getBlock() == Blocks.NETHERRACK)
/*    */     {
/* 43 */       i++;
/*    */     }
/*    */     
/* 46 */     if (worldIn.getBlockState(position.north()).getBlock() == Blocks.NETHERRACK)
/*    */     {
/* 48 */       i++;
/*    */     }
/*    */     
/* 51 */     if (worldIn.getBlockState(position.south()).getBlock() == Blocks.NETHERRACK)
/*    */     {
/* 53 */       i++;
/*    */     }
/*    */     
/* 56 */     if (worldIn.getBlockState(position.down()).getBlock() == Blocks.NETHERRACK)
/*    */     {
/* 58 */       i++;
/*    */     }
/*    */     
/* 61 */     int j = 0;
/*    */     
/* 63 */     if (worldIn.isAirBlock(position.west()))
/*    */     {
/* 65 */       j++;
/*    */     }
/*    */     
/* 68 */     if (worldIn.isAirBlock(position.east()))
/*    */     {
/* 70 */       j++;
/*    */     }
/*    */     
/* 73 */     if (worldIn.isAirBlock(position.north()))
/*    */     {
/* 75 */       j++;
/*    */     }
/*    */     
/* 78 */     if (worldIn.isAirBlock(position.south()))
/*    */     {
/* 80 */       j++;
/*    */     }
/*    */     
/* 83 */     if (worldIn.isAirBlock(position.down()))
/*    */     {
/* 85 */       j++;
/*    */     }
/*    */     
/* 88 */     if ((!this.insideRock && i == 4 && j == 1) || i == 5) {
/*    */       
/* 90 */       IBlockState iblockstate = this.block.getDefaultState();
/* 91 */       worldIn.setBlockState(position, iblockstate, 2);
/* 92 */       worldIn.immediateBlockTick(position, iblockstate, rand);
/*    */     } 
/*    */     
/* 95 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGenHellLava.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */