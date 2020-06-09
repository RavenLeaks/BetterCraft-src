package net.minecraft.block;

import javax.annotation.Nullable;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public interface ITileEntityProvider {
  @Nullable
  TileEntity createNewTileEntity(World paramWorld, int paramInt);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\block\ITileEntityProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */