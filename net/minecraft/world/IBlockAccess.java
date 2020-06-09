package net.minecraft.world;

import javax.annotation.Nullable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

public interface IBlockAccess {
  @Nullable
  TileEntity getTileEntity(BlockPos paramBlockPos);
  
  int getCombinedLight(BlockPos paramBlockPos, int paramInt);
  
  IBlockState getBlockState(BlockPos paramBlockPos);
  
  boolean isAirBlock(BlockPos paramBlockPos);
  
  Biome getBiome(BlockPos paramBlockPos);
  
  int getStrongPower(BlockPos paramBlockPos, EnumFacing paramEnumFacing);
  
  WorldType getWorldType();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\IBlockAccess.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */