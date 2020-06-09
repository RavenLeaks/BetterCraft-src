package net.minecraft.world.storage;

import java.io.File;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.AnvilConverterException;
import net.minecraft.util.IProgressUpdate;

public interface ISaveFormat {
  String getName();
  
  ISaveHandler getSaveLoader(String paramString, boolean paramBoolean);
  
  List<WorldSummary> getSaveList() throws AnvilConverterException;
  
  void flushCache();
  
  @Nullable
  WorldInfo getWorldInfo(String paramString);
  
  boolean isNewLevelIdAcceptable(String paramString);
  
  boolean deleteWorldDirectory(String paramString);
  
  void renameWorld(String paramString1, String paramString2);
  
  boolean isConvertible(String paramString);
  
  boolean isOldMapFormat(String paramString);
  
  boolean convertMapFormat(String paramString, IProgressUpdate paramIProgressUpdate);
  
  boolean canLoadWorld(String paramString);
  
  File getFile(String paramString1, String paramString2);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\net\minecraft\world\storage\ISaveFormat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */