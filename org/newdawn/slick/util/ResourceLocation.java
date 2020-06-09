package org.newdawn.slick.util;

import java.io.InputStream;
import java.net.URL;

public interface ResourceLocation {
  InputStream getResourceAsStream(String paramString);
  
  URL getResource(String paramString);
}


/* Location:              C:\Users\emlin\Desktop\BetterCraft.jar!\org\newdawn\slic\\util\ResourceLocation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */