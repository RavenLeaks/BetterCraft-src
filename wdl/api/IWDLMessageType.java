package wdl.api;

import net.minecraft.util.text.TextFormatting;

public interface IWDLMessageType {
  TextFormatting getTitleColor();
  
  TextFormatting getTextColor();
  
  String getDisplayName();
  
  String getDescription();
  
  boolean isEnabledByDefault();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\api\IWDLMessageType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */