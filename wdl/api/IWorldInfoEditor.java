package wdl.api;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.SaveHandler;
import net.minecraft.world.storage.WorldInfo;

public interface IWorldInfoEditor extends IWDLMod {
  void editWorldInfo(WorldClient paramWorldClient, WorldInfo paramWorldInfo, SaveHandler paramSaveHandler, NBTTagCompound paramNBTTagCompound);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\api\IWorldInfoEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */