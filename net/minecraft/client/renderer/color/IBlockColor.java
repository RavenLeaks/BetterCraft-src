package net.minecraft.client.renderer.color;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public interface IBlockColor {
  int colorMultiplier(IBlockState paramIBlockState, IBlockAccess paramIBlockAccess, BlockPos paramBlockPos, int paramInt);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\renderer\color\IBlockColor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */