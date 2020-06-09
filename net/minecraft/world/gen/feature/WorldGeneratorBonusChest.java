/*    */ package net.minecraft.world.gen.feature;
/*    */ 
/*    */ import java.util.Random;
/*    */ import net.minecraft.block.material.Material;
/*    */ import net.minecraft.block.state.IBlockState;
/*    */ import net.minecraft.init.Blocks;
/*    */ import net.minecraft.tileentity.TileEntity;
/*    */ import net.minecraft.tileentity.TileEntityChest;
/*    */ import net.minecraft.util.math.BlockPos;
/*    */ import net.minecraft.world.World;
/*    */ import net.minecraft.world.storage.loot.LootTableList;
/*    */ 
/*    */ public class WorldGeneratorBonusChest
/*    */   extends WorldGenerator
/*    */ {
/*    */   public boolean generate(World worldIn, Random rand, BlockPos position) {
/* 17 */     for (IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getMaterial() == Material.AIR || iblockstate.getMaterial() == Material.LEAVES) && position.getY() > 1; iblockstate = worldIn.getBlockState(position))
/*    */     {
/* 19 */       position = position.down();
/*    */     }
/*    */     
/* 22 */     if (position.getY() < 1)
/*    */     {
/* 24 */       return false;
/*    */     }
/*    */ 
/*    */     
/* 28 */     position = position.up();
/*    */     
/* 30 */     for (int i = 0; i < 4; i++) {
/*    */       
/* 32 */       BlockPos blockpos = position.add(rand.nextInt(4) - rand.nextInt(4), rand.nextInt(3) - rand.nextInt(3), rand.nextInt(4) - rand.nextInt(4));
/*    */       
/* 34 */       if (worldIn.isAirBlock(blockpos) && worldIn.getBlockState(blockpos.down()).isFullyOpaque()) {
/*    */         
/* 36 */         worldIn.setBlockState(blockpos, Blocks.CHEST.getDefaultState(), 2);
/* 37 */         TileEntity tileentity = worldIn.getTileEntity(blockpos);
/*    */         
/* 39 */         if (tileentity instanceof TileEntityChest)
/*    */         {
/* 41 */           ((TileEntityChest)tileentity).setLootTable(LootTableList.CHESTS_SPAWN_BONUS_CHEST, rand.nextLong());
/*    */         }
/*    */         
/* 44 */         BlockPos blockpos1 = blockpos.east();
/* 45 */         BlockPos blockpos2 = blockpos.west();
/* 46 */         BlockPos blockpos3 = blockpos.north();
/* 47 */         BlockPos blockpos4 = blockpos.south();
/*    */         
/* 49 */         if (worldIn.isAirBlock(blockpos2) && worldIn.getBlockState(blockpos2.down()).isFullyOpaque())
/*    */         {
/* 51 */           worldIn.setBlockState(blockpos2, Blocks.TORCH.getDefaultState(), 2);
/*    */         }
/*    */         
/* 54 */         if (worldIn.isAirBlock(blockpos1) && worldIn.getBlockState(blockpos1.down()).isFullyOpaque())
/*    */         {
/* 56 */           worldIn.setBlockState(blockpos1, Blocks.TORCH.getDefaultState(), 2);
/*    */         }
/*    */         
/* 59 */         if (worldIn.isAirBlock(blockpos3) && worldIn.getBlockState(blockpos3.down()).isFullyOpaque())
/*    */         {
/* 61 */           worldIn.setBlockState(blockpos3, Blocks.TORCH.getDefaultState(), 2);
/*    */         }
/*    */         
/* 64 */         if (worldIn.isAirBlock(blockpos4) && worldIn.getBlockState(blockpos4.down()).isFullyOpaque())
/*    */         {
/* 66 */           worldIn.setBlockState(blockpos4, Blocks.TORCH.getDefaultState(), 2);
/*    */         }
/*    */         
/* 69 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 73 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\gen\feature\WorldGeneratorBonusChest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */