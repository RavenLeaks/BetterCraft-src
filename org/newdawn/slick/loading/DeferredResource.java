package org.newdawn.slick.loading;

import java.io.IOException;

public interface DeferredResource {
  void load() throws IOException;
  
  String getDescription();
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slick\loading\DeferredResource.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */