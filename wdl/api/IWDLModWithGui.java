package wdl.api;

import net.minecraft.client.gui.GuiScreen;

public interface IWDLModWithGui extends IWDLMod {
  String getButtonName();
  
  void openGui(GuiScreen paramGuiScreen);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\api\IWDLModWithGui.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */