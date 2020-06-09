package wdl.api;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Container;
import net.minecraft.util.math.BlockPos;

public interface IGuiHooksListener extends IWDLMod {
  boolean onBlockGuiClosed(WorldClient paramWorldClient, BlockPos paramBlockPos, Container paramContainer);
  
  boolean onEntityGuiClosed(WorldClient paramWorldClient, Entity paramEntity, Container paramContainer);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\api\IGuiHooksListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */