package wdl.api;

import net.minecraft.client.multiplayer.WorldClient;

public interface IPluginChannelListener extends IWDLMod {
  void onPluginChannelPacket(WorldClient paramWorldClient, String paramString, byte[] paramArrayOfbyte);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\api\IPluginChannelListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */