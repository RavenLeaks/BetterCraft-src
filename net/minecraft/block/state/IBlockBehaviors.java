package net.minecraft.block.state;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IBlockBehaviors {
  boolean onBlockEventReceived(World paramWorld, BlockPos paramBlockPos, int paramInt1, int paramInt2);
  
  void neighborChanged(World paramWorld, BlockPos paramBlockPos1, Block paramBlock, BlockPos paramBlockPos2);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\state\IBlockBehaviors.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */