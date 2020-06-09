package wdl.api;

import net.minecraft.block.Block;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.math.BlockPos;

public interface IBlockEventListener extends IWDLMod {
  void onBlockEvent(WorldClient paramWorldClient, BlockPos paramBlockPos, Block paramBlock, int paramInt1, int paramInt2);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\api\IBlockEventListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */