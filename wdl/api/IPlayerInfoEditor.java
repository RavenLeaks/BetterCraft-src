package wdl.api;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.storage.SaveHandler;

public interface IPlayerInfoEditor extends IWDLMod {
  void editPlayerInfo(EntityPlayerSP paramEntityPlayerSP, SaveHandler paramSaveHandler, NBTTagCompound paramNBTTagCompound);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\api\IPlayerInfoEditor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */