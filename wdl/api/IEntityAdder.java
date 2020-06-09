package wdl.api;

import java.util.List;

public interface IEntityAdder extends IWDLMod {
  List<String> getModEntities();
  
  int getDefaultEntityTrackDistance(String paramString);
  
  String getEntityCategory(String paramString);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\wdl\api\IEntityAdder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */