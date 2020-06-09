package net.minecraft.client.gui.spectator;

import java.util.List;
import net.minecraft.util.text.ITextComponent;

public interface ISpectatorMenuView {
  List<ISpectatorMenuObject> getItems();
  
  ITextComponent getPrompt();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\client\gui\spectator\ISpectatorMenuView.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */